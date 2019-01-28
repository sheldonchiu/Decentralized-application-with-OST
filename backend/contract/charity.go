// Code generated - DO NOT EDIT.
// This file is a generated binding and any manual changes will be lost.

package contract

import (
	"math/big"
	"strings"

	ethereum "github.com/ethereum/go-ethereum"
	"github.com/ethereum/go-ethereum/accounts/abi"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/event"
)

// Reference imports to suppress errors if they are not otherwise used.
var (
	_ = big.NewInt
	_ = strings.NewReader
	_ = ethereum.NotFound
	_ = abi.U256
	_ = bind.Bind
	_ = common.Big1
	_ = types.BloomLookup
	_ = event.NewSubscription
)

// CharityABI is the input ABI used to generate the binding from.
const CharityABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"_projectAddress\",\"type\":\"address\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"_hash\",\"type\":\"string\"}],\"name\":\"addEvidence\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_projectAddress\",\"type\":\"address\"},{\"name\":\"_hash\",\"type\":\"string\"}],\"name\":\"addProject\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"projectAddress\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"hash\",\"type\":\"string\"}],\"name\":\"recordProject\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"projectAddress\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"name\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"hash\",\"type\":\"string\"}],\"name\":\"recordEvidence\",\"type\":\"event\"}]"

// CharityBin is the compiled bytecode used for deploying new contracts.
const CharityBin = `608060405234801561001057600080fd5b506103b1806100206000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806369291ed0146100515780638a06d20b14610120575b600080fd5b34801561005d57600080fd5b5061011e600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506101a9565b005b34801561012c57600080fd5b506101a7600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506102ce565b005b8273ffffffffffffffffffffffffffffffffffffffff167f5550f185f175f0985a6aea2f1d1058d0e1a9fff32f24e5f3a8296d2a538b72c98383604051808060200180602001838103835285818151815260200191508051906020019080838360005b8381101561022757808201518184015260208101905061020c565b50505050905090810190601f1680156102545780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b8381101561028d578082015181840152602081019050610272565b50505050905090810190601f1680156102ba5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a2505050565b8173ffffffffffffffffffffffffffffffffffffffff167fc11800322feb343d008f31ba4442c90e638645f365f7d1db9985b5d50dac916f826040518080602001828103825283818151815260200191508051906020019080838360005b8381101561034757808201518184015260208101905061032c565b50505050905090810190601f1680156103745780820380516001836020036101000a031916815260200191505b509250505060405180910390a250505600a165627a7a7230582067639cce334cf9da1a0197827d14f02700e8dff156dd257e1084593f201f5c860029`

// DeployCharity deploys a new Ethereum contract, binding an instance of Charity to it.
func DeployCharity(auth *bind.TransactOpts, backend bind.ContractBackend) (common.Address, *types.Transaction, *Charity, error) {
	parsed, err := abi.JSON(strings.NewReader(CharityABI))
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	address, tx, contract, err := bind.DeployContract(auth, parsed, common.FromHex(CharityBin), backend)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &Charity{CharityCaller: CharityCaller{contract: contract}, CharityTransactor: CharityTransactor{contract: contract}, CharityFilterer: CharityFilterer{contract: contract}}, nil
}

// Charity is an auto generated Go binding around an Ethereum contract.
type Charity struct {
	CharityCaller     // Read-only binding to the contract
	CharityTransactor // Write-only binding to the contract
	CharityFilterer   // Log filterer for contract events
}

// CharityCaller is an auto generated read-only Go binding around an Ethereum contract.
type CharityCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// CharityTransactor is an auto generated write-only Go binding around an Ethereum contract.
type CharityTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// CharityFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type CharityFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// CharitySession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type CharitySession struct {
	Contract     *Charity          // Generic contract binding to set the session for
	CallOpts     bind.CallOpts     // Call options to use throughout this session
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// CharityCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type CharityCallerSession struct {
	Contract *CharityCaller // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts  // Call options to use throughout this session
}

// CharityTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type CharityTransactorSession struct {
	Contract     *CharityTransactor // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts  // Transaction auth options to use throughout this session
}

// CharityRaw is an auto generated low-level Go binding around an Ethereum contract.
type CharityRaw struct {
	Contract *Charity // Generic contract binding to access the raw methods on
}

// CharityCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type CharityCallerRaw struct {
	Contract *CharityCaller // Generic read-only contract binding to access the raw methods on
}

// CharityTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type CharityTransactorRaw struct {
	Contract *CharityTransactor // Generic write-only contract binding to access the raw methods on
}

// NewCharity creates a new instance of Charity, bound to a specific deployed contract.
func NewCharity(address common.Address, backend bind.ContractBackend) (*Charity, error) {
	contract, err := bindCharity(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &Charity{CharityCaller: CharityCaller{contract: contract}, CharityTransactor: CharityTransactor{contract: contract}, CharityFilterer: CharityFilterer{contract: contract}}, nil
}

// NewCharityCaller creates a new read-only instance of Charity, bound to a specific deployed contract.
func NewCharityCaller(address common.Address, caller bind.ContractCaller) (*CharityCaller, error) {
	contract, err := bindCharity(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &CharityCaller{contract: contract}, nil
}

// NewCharityTransactor creates a new write-only instance of Charity, bound to a specific deployed contract.
func NewCharityTransactor(address common.Address, transactor bind.ContractTransactor) (*CharityTransactor, error) {
	contract, err := bindCharity(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &CharityTransactor{contract: contract}, nil
}

// NewCharityFilterer creates a new log filterer instance of Charity, bound to a specific deployed contract.
func NewCharityFilterer(address common.Address, filterer bind.ContractFilterer) (*CharityFilterer, error) {
	contract, err := bindCharity(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &CharityFilterer{contract: contract}, nil
}

// bindCharity binds a generic wrapper to an already deployed contract.
func bindCharity(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(CharityABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_Charity *CharityRaw) Call(opts *bind.CallOpts, result interface{}, method string, params ...interface{}) error {
	return _Charity.Contract.CharityCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_Charity *CharityRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _Charity.Contract.CharityTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_Charity *CharityRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _Charity.Contract.CharityTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_Charity *CharityCallerRaw) Call(opts *bind.CallOpts, result interface{}, method string, params ...interface{}) error {
	return _Charity.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_Charity *CharityTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _Charity.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_Charity *CharityTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _Charity.Contract.contract.Transact(opts, method, params...)
}

// AddEvidence is a paid mutator transaction binding the contract method 0x69291ed0.
//
// Solidity: function addEvidence(_projectAddress address, name string, _hash string) returns()
func (_Charity *CharityTransactor) AddEvidence(opts *bind.TransactOpts, _projectAddress common.Address, name string, _hash string) (*types.Transaction, error) {
	return _Charity.contract.Transact(opts, "addEvidence", _projectAddress, name, _hash)
}

// AddEvidence is a paid mutator transaction binding the contract method 0x69291ed0.
//
// Solidity: function addEvidence(_projectAddress address, name string, _hash string) returns()
func (_Charity *CharitySession) AddEvidence(_projectAddress common.Address, name string, _hash string) (*types.Transaction, error) {
	return _Charity.Contract.AddEvidence(&_Charity.TransactOpts, _projectAddress, name, _hash)
}

// AddEvidence is a paid mutator transaction binding the contract method 0x69291ed0.
//
// Solidity: function addEvidence(_projectAddress address, name string, _hash string) returns()
func (_Charity *CharityTransactorSession) AddEvidence(_projectAddress common.Address, name string, _hash string) (*types.Transaction, error) {
	return _Charity.Contract.AddEvidence(&_Charity.TransactOpts, _projectAddress, name, _hash)
}

// AddProject is a paid mutator transaction binding the contract method 0x8a06d20b.
//
// Solidity: function addProject(_projectAddress address, _hash string) returns()
func (_Charity *CharityTransactor) AddProject(opts *bind.TransactOpts, _projectAddress common.Address, _hash string) (*types.Transaction, error) {
	return _Charity.contract.Transact(opts, "addProject", _projectAddress, _hash)
}

// AddProject is a paid mutator transaction binding the contract method 0x8a06d20b.
//
// Solidity: function addProject(_projectAddress address, _hash string) returns()
func (_Charity *CharitySession) AddProject(_projectAddress common.Address, _hash string) (*types.Transaction, error) {
	return _Charity.Contract.AddProject(&_Charity.TransactOpts, _projectAddress, _hash)
}

// AddProject is a paid mutator transaction binding the contract method 0x8a06d20b.
//
// Solidity: function addProject(_projectAddress address, _hash string) returns()
func (_Charity *CharityTransactorSession) AddProject(_projectAddress common.Address, _hash string) (*types.Transaction, error) {
	return _Charity.Contract.AddProject(&_Charity.TransactOpts, _projectAddress, _hash)
}

// CharityRecordEvidenceIterator is returned from FilterRecordEvidence and is used to iterate over the raw logs and unpacked data for RecordEvidence events raised by the Charity contract.
type CharityRecordEvidenceIterator struct {
	Event *CharityRecordEvidence // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *CharityRecordEvidenceIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(CharityRecordEvidence)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(CharityRecordEvidence)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *CharityRecordEvidenceIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *CharityRecordEvidenceIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// CharityRecordEvidence represents a RecordEvidence event raised by the Charity contract.
type CharityRecordEvidence struct {
	ProjectAddress common.Address
	Name           string
	Hash           string
	Raw            types.Log // Blockchain specific contextual infos
}

// FilterRecordEvidence is a free log retrieval operation binding the contract event 0x5550f185f175f0985a6aea2f1d1058d0e1a9fff32f24e5f3a8296d2a538b72c9.
//
// Solidity: e recordEvidence(projectAddress indexed address, name string, hash string)
func (_Charity *CharityFilterer) FilterRecordEvidence(opts *bind.FilterOpts, projectAddress []common.Address) (*CharityRecordEvidenceIterator, error) {

	var projectAddressRule []interface{}
	for _, projectAddressItem := range projectAddress {
		projectAddressRule = append(projectAddressRule, projectAddressItem)
	}

	logs, sub, err := _Charity.contract.FilterLogs(opts, "recordEvidence", projectAddressRule)
	if err != nil {
		return nil, err
	}
	return &CharityRecordEvidenceIterator{contract: _Charity.contract, event: "recordEvidence", logs: logs, sub: sub}, nil
}

// WatchRecordEvidence is a free log subscription operation binding the contract event 0x5550f185f175f0985a6aea2f1d1058d0e1a9fff32f24e5f3a8296d2a538b72c9.
//
// Solidity: e recordEvidence(projectAddress indexed address, name string, hash string)
func (_Charity *CharityFilterer) WatchRecordEvidence(opts *bind.WatchOpts, sink chan<- *CharityRecordEvidence, projectAddress []common.Address) (event.Subscription, error) {

	var projectAddressRule []interface{}
	for _, projectAddressItem := range projectAddress {
		projectAddressRule = append(projectAddressRule, projectAddressItem)
	}

	logs, sub, err := _Charity.contract.WatchLogs(opts, "recordEvidence", projectAddressRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(CharityRecordEvidence)
				if err := _Charity.contract.UnpackLog(event, "recordEvidence", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// CharityRecordProjectIterator is returned from FilterRecordProject and is used to iterate over the raw logs and unpacked data for RecordProject events raised by the Charity contract.
type CharityRecordProjectIterator struct {
	Event *CharityRecordProject // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *CharityRecordProjectIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(CharityRecordProject)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(CharityRecordProject)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *CharityRecordProjectIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *CharityRecordProjectIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// CharityRecordProject represents a RecordProject event raised by the Charity contract.
type CharityRecordProject struct {
	ProjectAddress common.Address
	Hash           string
	Raw            types.Log // Blockchain specific contextual infos
}

// FilterRecordProject is a free log retrieval operation binding the contract event 0xc11800322feb343d008f31ba4442c90e638645f365f7d1db9985b5d50dac916f.
//
// Solidity: e recordProject(projectAddress indexed address, hash string)
func (_Charity *CharityFilterer) FilterRecordProject(opts *bind.FilterOpts, projectAddress []common.Address) (*CharityRecordProjectIterator, error) {

	var projectAddressRule []interface{}
	for _, projectAddressItem := range projectAddress {
		projectAddressRule = append(projectAddressRule, projectAddressItem)
	}

	logs, sub, err := _Charity.contract.FilterLogs(opts, "recordProject", projectAddressRule)
	if err != nil {
		return nil, err
	}
	return &CharityRecordProjectIterator{contract: _Charity.contract, event: "recordProject", logs: logs, sub: sub}, nil
}

// WatchRecordProject is a free log subscription operation binding the contract event 0xc11800322feb343d008f31ba4442c90e638645f365f7d1db9985b5d50dac916f.
//
// Solidity: e recordProject(projectAddress indexed address, hash string)
func (_Charity *CharityFilterer) WatchRecordProject(opts *bind.WatchOpts, sink chan<- *CharityRecordProject, projectAddress []common.Address) (event.Subscription, error) {

	var projectAddressRule []interface{}
	for _, projectAddressItem := range projectAddress {
		projectAddressRule = append(projectAddressRule, projectAddressItem)
	}

	logs, sub, err := _Charity.contract.WatchLogs(opts, "recordProject", projectAddressRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(CharityRecordProject)
				if err := _Charity.contract.UnpackLog(event, "recordProject", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}
