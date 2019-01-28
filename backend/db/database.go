package db

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/ethclient"
	"github.com/gorilla/mux"
	"github.com/mongodb/mongo-go-driver/bson"
	"github.com/mongodb/mongo-go-driver/bson/objectid"
	"github.com/mongodb/mongo-go-driver/mongo"
	"github.com/user/contract"
	"io"
	"io/ioutil"
	"log"
	"math/big"
	"net/http"
	"strconv"
	"time"
)

const connectionString = "mongodb://localhost:27017"
const ProjectColl= "projectRecord"
const ProjectID = "IDs"
const ShopDb = "Shop"
const ipfsAddress string = "http://localhost:8080/ipfs/"

type ProjectId struct{
	ID         objectid.ObjectID `bson:"_id,omitempty" json:"_id,omitempty"`
	WalletId string `bson:"id" json:"id"`
	ProjectAddress string	`bson:"projectAddress" json:"projectAddress"`
}

type Project struct {
	ID         objectid.ObjectID `bson:"_id,omitempty" json:"_id,omitempty"`
	CharityName       string	 `bson:"charityName" json:"charityName"`
	ProjectName string	`bson:"projectName" json:"projectName"`
	ProjectAddress string	`bson:"projectAddress" json:"projectAddress"`
	ProjectDescription string `bson:"projectDescription" json:"projectDescription"`
	Hash string	`bson:"hash" json:"hash"`
	ProjectCategory string	`bson:"category" json:"category"`
	TargetAmount string	`bson:"target" json:"target"`
	WalletId string `bson:"id" json:"id"`
	Time time.Time `bson:"timestamp" json:"timestamp"`
	Duration string `bson:"duration" json:"duration"`
	Evidence [][]string `bson:"evidence" json:"evidence"`
	DonationStatus bool `bson:"donationStatus" json:"donationStatus"`
	Amount int `bson:"amount" json:"amount"`
}

type User struct{
	ID objectid.ObjectID `bson:"_id,omitempty" json:"_id,omitempty"`
	WalletId string `bson:"id" json:"id"`
	Address string	`bson:"address" json:"address"`
	Name string	`bson:"name" json:"name"`
	Email string	`bson:"email" json:"email"`
}

type Transaction struct{
	ID objectid.ObjectID `bson:"_id,omitempty" json:"_id,omitempty"`
	ProjectAddress string `bson:"address" json:"address"`
	Amount int `bson:"amount" json:"amount"`
}

var(
	Conn *ethclient.Client
	client *mongo.Client
	db *mongo.Database
)

func Connect(){
	var err error
	client, err = mongo.NewClient(connectionString)
	if err != nil {
		log.Fatal(err)
	}
	err = client.Connect(context.Background())
	if err != nil {
		log.Fatal(err)
	}
}

func Init(_Conn *ethclient.Client, dbName string){
	Connect()
	Conn = _Conn
	db = client.Database(dbName)
	GetAllAddress()
}

func AddUser(w http.ResponseWriter, r *http.Request)(){
	log.
	var user = User{}
	body, err := ioutil.ReadAll(io.LimitReader(r.Body, 1048576))
	if err != nil {
		panic(err)
	}
	if err := r.Body.Close(); err != nil {
		panic(err)
	}
	if err := json.Unmarshal(body, &user); err == nil{
		//_ = json.NewDecoder(r.Body).Decode(&user)
		AddOne("user", user)
		json.NewEncoder(w).Encode(user)
	}
}

func GetUserName(w http.ResponseWriter, r *http.Request){
	var user User
	params := mux.Vars(r)
	projectDb := db.Collection("user")
	filter := bson.NewDocument(bson.EC.String("id", params["id"]))
	err := projectDb.FindOne(nil, filter).Decode(&user)
	if err != nil {fmt.Errorf("updateTask: couldn't decode task from db: %v", err)}
	json.NewEncoder(w).Encode(user.Name)
}

func  RegProj(w http.ResponseWriter, r *http.Request) {
	var proj = ProjectId{}
	projectDb := db.Collection(ProjectID)
	body, err := ioutil.ReadAll(io.LimitReader(r.Body, 1048576))
	if err != nil {panic(err)}
	if err := r.Body.Close(); err != nil {panic(err)}
	if err := json.Unmarshal(body, &proj); err == nil{
		_, err = projectDb.InsertOne(nil, proj)
		if err != nil {
			fmt.Println("Error adding Project to mongodb")
		}
		json.NewEncoder(w).Encode(true)
	}
}

func GetAllAddress(){
	projectDb := db.Collection(ProjectColl)
	result , err := projectDb.Find(nil,nil)
	if err != nil{fmt.Errorf("fail to retrieve project address")}
	for result.Next(nil){
		item := Project{}
		if result.Decode(&item) != nil {
			log.Fatal("Decode error ", err)
		}
		if item.DonationStatus{
			duration, _ := strconv.Atoi(item.Duration)
			contract.Project[common.HexToAddress(item.ProjectAddress)] = item.Time.AddDate(0,duration,0)
		}
		//contract.ProjectAddress = append(contract.ProjectAddress, common.HexToAddress(item.ProjectAddress))
	}
}

func GetAll(w http.ResponseWriter, r *http.Request){
	projects, err:= ReadWholeCollection(ProjectColl)
	if err != nil{
		log.Fatal(err)
	}
	json.NewEncoder(w).Encode(projects)
}

func CheckTransaction(w http.ResponseWriter, r *http.Request){
	trans := Transaction{}
	projectDb := db.Collection(ProjectColl)
	body, err := ioutil.ReadAll(io.LimitReader(r.Body, 1048576))
	if err != nil {panic(err)}
	if err := r.Body.Close(); err != nil {panic(err)}
	if err := json.Unmarshal(body, &trans); err == nil{
		if _, ok := contract.Project[common.HexToAddress(trans.ProjectAddress)]; ok {
			if err != nil{fmt.Errorf("fail to find doc")}
			filter := bson.M{"projectAddress" : trans.ProjectAddress}
			update := bson.M{"inc": bson.M{"amount": trans.Amount}}
			_, e := projectDb.UpdateOne(nil, filter,update)
			if e != nil{fmt.Errorf("fail to update amount")}
			json.NewEncoder(w).Encode(true)
			return
		}
	}
	json.NewEncoder(w).Encode(false)
}

func AddOne(name string, input interface{})(error){
	projectDb := db.Collection(name)
	switch i := input.(type) {
		case *contract.CharityRecordProject:
			err := addProject(i,projectDb)
			if err != nil{return err}
		case User:
			i.ID = objectid.New()
			_, err := projectDb.InsertOne(nil, i)
			if err != nil {
				fmt.Println("Error adding Project to mongodb")
				return err
			}
	}
	return nil
}

func addProject(i *contract.CharityRecordProject, projectDb *mongo.Collection) error{
	newItem := Project{ID: objectid.New(), ProjectAddress:i.ProjectAddress.String()}
	o, err := http.Get(ipfsAddress + i.Hash)
	_ = json.NewDecoder(o.Body).Decode(&newItem)
	newItem.Hash = i.Hash
	newItem.WalletId = findId(newItem.ProjectAddress)
	newItem.Amount = 0
	newItem.DonationStatus = true
	var block *types.Header
	block, err = Conn.HeaderByNumber(context.Background(), new(big.Int).SetUint64(i.Raw.BlockNumber))
	if err != nil{
		fmt.Println("Failed to retrieve timestamp of block")
	} else {newItem.Time = time.Unix(block.Time.Int64(), 0)}
	newItem.Evidence = make([][]string,0)
	duration, _ := strconv.Atoi(newItem.Duration)
	endDate := newItem.Time.AddDate(0,duration,0)
	if time.Now().Unix() < endDate.Unix(){
		contract.Project[common.HexToAddress(newItem.ProjectAddress)] = endDate
		newItem.DonationStatus = true
	} else{newItem.DonationStatus = false}
	_, err = projectDb.InsertOne(nil, newItem)
	if err != nil {
		fmt.Println("Error adding Project to mongodb")
		return err
	}
	//contract.ProjectAddress = append(contract.ProjectAddress,common.HexToAddress(newItem.ProjectAddress))
	return nil
}

func CloneProjects(name string, input []contract.CharityRecordProject)(error){
	projectDb := db.Collection(name)
	for _, i := range input{
		err := addProject(&i,projectDb)
		if err != nil{return err}
	}
	return nil
}

func Reset(name string, projects []contract.CharityRecordProject){
	projectDb := db.Collection(name)
	fmt.Println("Data not sync, pulling whole block")
	projectDb.Drop(nil)
	CloneProjects(ProjectColl, projects)
}

func CheckEqual(name string, projects []contract.CharityRecordProject)(bool){
	projectDb := db.Collection(name)
	count, err:= projectDb.CountDocuments(nil,nil)
	//dbItems, err := ReadWholeCollection(projectDb)
	if err != nil{
		log.Fatal("Error reading whole collection")
	}
	if int(count) != len(projects){
		return false
		//fmt.Println("Data not sync, pulling whole block")
		//projectDb.Drop(nil)
		//GetAll(ProjectColl, projects)
	}
	return true
}

func ReadWholeCollection(name string) ([]Project, error){
	c := db.Collection(name)
	cur , err := c.Find(nil, nil)
	if err != nil {
		log.Fatal("error ", err)
	}
	defer cur.Close(context.Background())

	var items []Project

	for cur.Next(nil) {
		item := Project{}
		err := cur.Decode(&item)
		if err != nil {
			log.Fatal("Decode error ", err)
		}
		items = append(items, item)
	}

	if err := cur.Err(); err != nil {
		log.Fatal("Cursor error ", err)
	}
	return items, nil
}

func findId(address string)(id string){
	var proj ProjectId
	projectDb := db.Collection(ProjectID)
	filter := bson.M{"projectAddress" : address}
	err := projectDb.FindOne(nil, filter).Decode(&proj)
	if err != nil {fmt.Errorf("updateTask: couldn't decode task from db: %v", err)}
	return proj.WalletId
}

// map of name to hash
func UpdateEvidence(address string, item []contract.Item){
	projectDb := db.Collection(ProjectColl)
	filter := bson.M{"projectAddress" : address}
	var dataArray [][]string
	for _, ele := range item{
		dataArray = append(dataArray, []string{ele.Name, ele.Hash})
	}
	update := bson.M{"$addToSet": bson.M{"evidence": bson.M{"$each": dataArray}}}
	_ ,err := projectDb.UpdateOne(nil, filter,update)
	if err == nil {
		fmt.Errorf("no doc find")
	}
}


