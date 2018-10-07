package com.beardcode.imaguru.demo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.core.methods.response.EthAccounts;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private Web3jController web3jController;

    @Before
    public void setup() {
        web3jController = new Web3jController();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void getBlockNumber() throws ExecutionException, InterruptedException {
        web3jController.getBlockNumber();
    }

    @Test
    public void getEthAccounts() throws ExecutionException, InterruptedException {
        CompletableFuture<EthAccounts> ethAccounts = web3jController.getEthAccounts();
        Assert.assertNotNull(ethAccounts);
    }

}
