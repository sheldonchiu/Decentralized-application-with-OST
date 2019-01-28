package contract

import (
	"context"
	"fmt"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/ethclient"
	"github.com/ethereum/go-ethereum/event"
	"log"
	"strings"
	"time"
)

const key = `{"version":3,"id":"524aa6fd-5462-4502-b48e-d24eb78dd9b0","address":"2930755577eda50986a0f8c021b4a085bd7a5f42","Crypto":{"ciphertext":"34691a488aa8c0e6fc610ee0cba8c3173931d773e496df6e832969a4ccf0d526","cipherparams":{"iv":"17967e4ad150bd19d4d3dbf3746e0644"},"cipher":"aes-128-ctr","kdf":"scrypt","kdfparams":{"dklen":32,"salt":"76c50beae7d446d6e348a510fed6b7b363c059772dc9672cd9d146b99fd36f4f","n":8192,"r":8,"p":1},"mac":"ad86ae301b90654b83c766cc8f4a8f1db45f2c4f9e72d4eff2570b9bdd532bf7"}}`
const contractAddress = "0xe141597481d90069201AD4AB0f373f448185d162  "
const api = "wss://ropsten.infura.io/ws"

//var ProjectAddress []common.Address

var Project = make(map[common.Address]time.Time)

func Connect()(*ethclient.Client, *Charity){
	conn, err := ethclient.Dial(api)
	if err != nil {
		log.Fatalf("Failed to connect to the Ethereum client: %v", err)
	}
	// Instantiate the contract and display its name
	c, err := NewCharity(common.HexToAddress(contractAddress), conn)
	if err != nil {
		log.Fatalf("Failed to instantiate a Token contract: %v", err)
	}
	return conn, c
}

func GetAuth()(*bind.TransactOpts, error){
	auth, err := bind.NewTransactor(strings.NewReader(key), "isom3000b")
	if err != nil {
		log.Fatalf("Failed to create authorized transactor: %v", err)
	}
	return auth, nil
}

func ListenToProject(c *Charity)(event.Subscription, chan *CharityRecordProject){
	watch := new(bind.WatchOpts)
	watch.Context = context.Background()
	ch := make(chan *CharityRecordProject)
	sub, err := c.WatchRecordProject(nil,ch,nil)
	if err != nil{
		log.Fatalf("Fail to listen to project event: %s", err)
	}
	fmt.Println("Start listening to new project")
	return sub, ch
}

func ListenToEvidence(c *Charity)(event.Subscription, chan *CharityRecordEvidence){
	watch := new(bind.WatchOpts)
	watch.Context = context.Background()
	ch := make(chan *CharityRecordEvidence)
	sub, err := c.WatchRecordEvidence(nil,ch,nil)
	if err != nil{
		log.Fatalf("Fail to listen to project event: %s", err)
	}
	fmt.Println("Start listening to new Evidence")
	return sub, ch
}

func PullWholeBlock(charityContract *Charity , error chan<- error)([]CharityRecordProject){
	list, err := charityContract.FilterRecordProject(nil, nil)
	if err != nil{
		fmt.Println("Error pulling the whole block")
		error <- err
		return nil
	}
	var buffer []CharityRecordProject
	for list.Next(){
		buffer = append(buffer, *list.Event)
	}
	return buffer
}

type Item struct {
	Name, Hash string
}
//key to name
func PullAllEvidence(charityContract *Charity)(map[string][]Item, error){
	ev := make(map[string][]Item)

	for address := range Project{
		filter := []common.Address{address}
		list, err := charityContract.FilterRecordEvidence(nil, filter)
		if err != nil{
			fmt.Println("Error pulling the whole block of evidence")
			return nil, err
		}
		var buf []Item
		for list.Next(){
			buf = append(buf, Item{list.Event.Name, list.Event.Hash})
		}
		ev[address.String()] = buf
	}
	return ev, nil
}

func MonitorEndTime(){

}
