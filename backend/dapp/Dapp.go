//go:generate abigen --sol contract.sol --pkg contract --out charity1.go
package main

import (
	"fmt"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/ethclient"
	"github.com/gorilla/mux"
	"github.com/user/contract"
	"github.com/user/db"
	"log"
	"net/http"
)

var(
	Conn            *ethclient.Client
	err             error
	CharityContract *contract.Charity
	auth            *bind.TransactOpts
	tx              *types.Transaction
	errorChan       chan error
	check bool
)

func main() {
	//Conn, CharityContract = contract.Connect()
	db.Init(Conn, db.ShopDb)
	//sub, projectCh := contract.ListenToProject(CharityContract)
	//sub2, evidenceCh := contract.ListenToEvidence(CharityContract)
	//timeChan := time.NewTicker(time.Minute * 10 ).C
	//projectChan := make(chan []contract.CharityRecordProject)
	router := mux.NewRouter()
	router.HandleFunc("/transaction", db.CheckTransaction).Methods("POST")
	router.HandleFunc("/projects", db.GetAll).Methods("GET")
	router.HandleFunc("/{id}", db.GetUserName).Methods("GET")
	router.HandleFunc("/projects/reg", db.RegProj).Methods("POST")
	router.HandleFunc("/projects", db.AddUser).Methods("POST")
	//go func(){
	//	for {
	//		select {
	//		case input := <-projectCh:
	//			db.AddOne(db.ProjectColl, input)
	//			fmt.Println("New project recorded")
	//		case err = <-sub.Err():
	//			log.Fatal(err)
	//		case ev := <- evidenceCh:
	//			db.UpdateEvidence(ev.ProjectAddress.String(),[]contract.Item{{Name: ev.Name, Hash: ev.Hash}})
	//			fmt.Println("New Evidence recorded")
	//		case err = <-sub2.Err():
	//			log.Fatal(err)
	//		case projects:= <-projectChan:
	//			fmt.Println("Resetting db")
	//			db.Reset(db.ProjectColl, projects)
	//			result, e:= contract.PullAllEvidence(CharityContract)
	//			if e ==  nil{
	//				for k := range result {
	//					db.UpdateEvidence(k,result[k])}}
	//			fmt.Println("Sync done")
	//		case <- timeChan:
	//			check = true
	//		}
	//		if check{
	//			go func(){
	//				fmt.Println("Server check start")
	//				projects := contract.PullWholeBlock(CharityContract, errorChan)
	//				if db.CheckEqual(db.ProjectColl, projects) {
	//					fmt.Println("Server check finish without error")
	//				} else{projectChan <- projects}
	//			}()
	//			check = false
	//		}
	//	}
	//}()
	fmt.Println("Server start running")
	log.Fatal(http.ListenAndServe(":8000", router))
}