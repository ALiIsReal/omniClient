package com.tn.set.setvice.cointest;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.coin.CoinBtcService;

public class CoinBtcServiceTest {
	private CoinBtcService btcUtils = new CoinBtcService();

	@Test
	public void getBlockChainInfo() throws Exception {
		JSONObject result = btcUtils.getBlockChainInfo();
		System.out.println(result);

	}
	@Test
	public void getInfo() throws Exception {
		JSONObject result = btcUtils.getInfo();
		System.out.println(result);

	}

	@Test
	public void getBalance() {
		long result = btcUtils.getBalance();
		System.out.println(result);
		//btcUtils.getBlockCount();
	}
	
	@Test
	public void getBlockCount(){
		int result = btcUtils.getBlockCount();
		System.out.println(result);
	}
	
	@Test
	public void getNewAddress(){
		String result = btcUtils.getNewAddress();
		System.out.println(result);
	}
	
	@Test
	public void getHelp() throws Exception{
		JSONObject result = btcUtils.getHelp();
		System.out.println(result);
	}
	
	@Test
	public void send(){
		String result = btcUtils.send("myYjwTjbANrAQ2uYNryTgro3Z7i56pMVaz", 100l);
		System.out.println(result);
	}
	
	@Test
	public void getBlock(){
		JSONObject result = btcUtils.getBlock(345603);
		System.out.println(result);
	}
}
