var Contracts = artifacts.require("./CellContract.sol");

module.exports = function(deployer) {
  deployer.deploy(Contracts,"0x0dCbD7ba10e35B3f9B6d7F89642B2f3036694d3B", "0x3dd390fEa6c411f6185F6eA9B30287d7f6f337A4", 1000);
};
