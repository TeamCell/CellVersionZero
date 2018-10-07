pragma solidity ^0.4.24;
contract CellContract {
    uint balance;
    address public buyer;
    address public seller;
    address private celloracle;
    uint agreed_price;
    uint fee;

    constructor(address buyer_address, address seller_address, uint cell_fee) public {
        // this is the constructor function that runs ONCE upon initialization
        buyer = buyer_address;
        seller = seller_address;
        celloracle = msg.sender;
        fee = cell_fee;
    }

    /*
        This method is called when celloracle detects the ticket has been uploaded
    */
    function accept() public {
        if (msg.sender == celloracle){
            payBalance(seller);
        }
    }

    /*
        This method is called when celloracle detects a fraudulent transaction for the sell
    */
    function cancel() public {
        if (msg.sender == celloracle){
            fee = 0;
            payBalance(buyer);
        }
    }

    /*
        Transfers the balance to a given address keeping a 1% fee for Cell
    */
    function payBalance(address participant) private {
        celloracle.transfer(fee);
        participant.transfer(address(this).balance - fee);
        balance = 0;
    }

    /*
        Tops up the contract with a given amount
    */
    function deposit() public payable {
        if (msg.sender == buyer) {
            balance += msg.value;
        }
    }

    function kill() public constant {
        if (msg.sender == celloracle) {
            selfdestruct(seller);
        }
    }
}