pragma solidity ^0.4.24;

contract CharityMain{
    
    event recordProject(address indexed projectAddress, string hash);
    event recordEvidence(address indexed projectAddress,string name, string hash);
    
    function addProject(address _projectAddress, string _hash) public{
        emit recordProject(_projectAddress, _hash);
    }
    
    function addEvidence(address _projectAddress, string name, string _hash) public{
        emit recordEvidence(_projectAddress,name, _hash);
    }
}