package com.tn.set.setvice.cointest;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.coin.CoinUsdtService;

public class CoinUsdtServiceTest {
	private CoinUsdtService coinUtils = new CoinUsdtService();
	
	@Test
	public void getInfo() throws Exception{
		JSONObject result = coinUtils.getInfo();
		System.out.println(result);
	}
	
	@Test
	public void getNewAddress(){
		String result = coinUtils.getNewAddress();
		System.out.println(result);
	}
	
	@Test
	public void getBalance(){
		long result = coinUtils.getBalance();
		System.out.println(result);
	}
}
