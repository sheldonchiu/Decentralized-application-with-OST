package main

import (
	"bufio"
	"fmt"
	"github.com/ethereum/go-ethereum/common"
	"github.com/user/contract"
	"github.com/user/db"
	"log"
	"os"
	"strings"
)

//0x38ef7A1D05425d8a02aF0d64e61ec28F15a04F57
//QmWaar8NQTYM1G1HeYdJ1FnVFeuFHxoi2BmGnY9uo8BZk9
//QmRepn19djbih1zLh22r3LFo6VV32jR5kYjXAR5zRS7b5W
func sendP(CharityContract  *contract.Charity, address string, hash string){
	auth, _ := contract.GetAuth()

	tx, err := CharityContract.AddProject(auth,common.HexToAddress(address), hash)
	if err != nil {
		log.Fatalf("Failed to request token transfer: %v", err)
	}
	fmt.Printf("Transfer pending: 0x%x\n", tx.Hash())
}

func sendE(CharityContract  *contract.Charity, address string, name string, hash string){
	auth, _ := contract.GetAuth()

	tx, err := CharityContract.AddEvidence(auth,common.HexToAddress(address),name, hash)
	if err != nil {
		log.Fatalf("Failed to request token transfer: %v", err)
	}
	fmt.Printf("Transfer pending: 0x%x\n", tx.Hash())
}

func main(){
	Conn , CharityContract := contract.Connect()
	db.Init(Conn, db.ShopDb)
	scanner := bufio.NewScanner(os.Stdin)
	fmt.Println("Please enter an action")
	for scanner.Scan() {
		input := scanner.Text()
		action := strings.Fields(input)
		if len(action) == 0{
			fmt.Println("No input is find")
			continue
		}
		switch action[0] {
		case "addP":
			if len(action) != 3{
				fmt.Println("Not enough information to unpack")
				continue
			}
			address := action[1]
			hash := action[2]
			sendP(CharityContract, address, hash)
		case "addE":
			if len(action) != 4{
				fmt.Println("Not enough information to unpack")
				continue
			}
			address := action[1]
			name := action[2]
			hash := action[3]
			sendE(CharityContract, address, name, hash)
		default:
			fmt.Println("No such command")
		}
		fmt.Println("Please enter an action")
	}

	if scanner.Err() != nil {
		// handle error.
	}
}
