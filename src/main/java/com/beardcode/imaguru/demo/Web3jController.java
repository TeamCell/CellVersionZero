package com.beardcode.imaguru.demo;

import com.beardcode.imaguru.web3.contract.CellContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import javax.ws.rs.QueryParam;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class Web3jController {

    public static final String buyer_address = "0x0dCbD7ba10e35B3f9B6d7F89642B2f3036694d3B";
    public static final String seller_address = "0x3dd390fEa6c411f6185F6eA9B30287d7f6f337A4";
    public static final String oracle_key = "684f8249f33d0a4f896fb194a18fad03e3bdc4b5800570da445dc922742e2d3b";



    private Web3j web3j = Web3j.build(new HttpService("http://localhost:7545"));

    private Logger l = LoggerFactory.getLogger(getClass());

    private CellContract contract;

    @GetMapping("/deploy")
    public void deploy() throws Exception {

        deployContract();


    }

    @GetMapping("/deposit")
    public void deposit(@RequestParam("price")String price) throws Exception {

        Credentials buyer_key = Credentials.create("d5adcb65cb94ec9adf971a3e3ad33ae992a9db1b06e7a2b24d55b7c05cdc638a");

        CellContract cellContract = CellContract.load(contract.getContractAddress(), web3j, buyer_key, BigInteger.valueOf(2000000000), BigInteger.valueOf(6721975));

        cellContract.deposit(new BigInteger("1000000000000000000").multiply(new BigInteger(price))).send();


    }

    @GetMapping("/accept")
    public void accept() throws Exception {

        contract.accept().send();


    }

    @GetMapping("/cancel")
    public void cancel() throws Exception {

        contract.cancel().send();


    }
    @GetMapping("/ticket")
    public void ticket(@RequestParam("filename") String filename) throws Exception {
        Random random = new Random();


        if(filename.contains("ok")){
            accept();
            l.info("------------VALIDO------------");
        }else{
            cancel();
            l.info("------------INVALIDO------------");
        }
    }

    private void deployContract() throws Exception{

        Credentials credentials =
                Credentials.create(oracle_key);

        contract = CellContract.deploy(
                web3j, credentials,
                BigInteger.valueOf(2000000000), BigInteger.valueOf(6721975),
                buyer_address,
                seller_address,BigInteger.valueOf(1000)).send();

        String contractAddress = contract.getContractAddress();
        l.debug("Smart contract deployed to address "+ contractAddress);


    }
    public CompletableFuture<EthBlockNumber> getBlockNumber() throws ExecutionException, InterruptedException {
        EthBlockNumber result;
        result = this.web3j.ethBlockNumber()
                .sendAsync()
                .get();
        return CompletableFuture.completedFuture(result);
    }

    public CompletableFuture<EthAccounts> getEthAccounts() throws ExecutionException, InterruptedException {
        EthAccounts result;
        result = this.web3j.ethAccounts()
                .sendAsync()
                .get();
        return CompletableFuture.completedFuture(result);
    }
}
