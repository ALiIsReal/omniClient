package com.tn.set.setvice.cointest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.util.CoinUtils;
import com.tn.set.service.util.OmniClient;
import com.tn.set.service.util.client.base.Omni;

public class OmniClientTest {
	static Omni omni = OmniClient.getOmni();
	
	public static void main(String[] args) throws IOException {
		//JSONObject result = omni.blockChainInfo().send().getBlockChainInfo();
		//System.out.println(result);
		//JSONObject result = omni.walletInfo().send().getWalletInfo();
		//System.out.println(result);
		//Double result = omni.balance().send().getBalance();
		//System.out.println(result1);
		//String result2 = omni.newAddress().send().getNewAddress();
		//System.out.println(result2);
		//Integer result = omni.blockCount().send().getBlockCount();
		//System.out.println(result);
		//Account string = omni.account("1ButGR5dxjZrEf6gUEx28uWHgn9265oVHC").send();
		//string.getResult();
		//string.getError();
		//Double result = omni.account("1EXoDusjGwvnjZUyKkxZ4UHEf77z6A5S4P").send().getAccount();
		//System.out.println(result);
		//JSONObject result = omni.sendToAddress("1EXoDusjGwvnjZUyKkxZ4UHEf77z6A5S4P", 0.0).send().SendToAddress();
		//System.out.println(result);
		//JSONObject result = omni.getBlock(omni.getBlockHash(omni.blockCount().send().getBlockCount()).send().getBlockHash()).send().getBlock();
		//System.out.println(result);
		//JSONObject result = omni.listAccounts().send().listAccounts();
		//JSONObject result = omni.omni_GetBalance("1BVZdXzzomb5Nq2odsiKVRQe1PaCkLqpbz",31).send().omni_GetBalance();
		//JSONObject result = omni.omni_GetInfo().send().omni_GetInfo();
		//JSONObject result = omni.omni_GetGrants(6).send().omni_getGrants();
		//JSONObject result = omni.omni_GetTrade("1700e207f18238ae64d498aa6d2149e95f2c6c6a87ad6ac1ac2e0f551a0f8742").send().omni_GetTrade();
		//JSONObject result = omni.omni_GetBalance("1EXoDusjGwvnjZUyKkxZ4UHEf77z6A5S4P", 1).send().omni_GetBalance();
		//JSONArray result = omni.omni_ListProperties().send().omni_ListProperties();
		//JSONArray result = omni.omni_GetAllBalancesForId(31).send().omni_GetAllBalancesForId();
		//JSONObject result = omni.omni_Send("1BVZdXzzomb5Nq2odsiKVRQe1PaCkLqpbz", "1PVEDwKbb3odjFubMrRsfcmpTFM61ekxpt", 31, "0.00001").send().omniSend();
		JSONArray result = omni.omniGetAllBalancesForAddress("1EXoDusjGwvnjZUyKkxZ4UHEf77z6A5S4P").send().omniGetAllBalancesForAddress();//1EXoDusjGwvnjZUyKkxZ4UHEf77z6A5S4P
		//Map<String, Object> result =  omni.createNewAddress();  1NpScs5QhqmSeV6FZUdGUFvVVfQGbuBV3Y
		//String result = omni.importAddress("1QBoiGauj2eGJRpYiiUuSWuhRKzF8faqqC").send();
		//String result = omni.dumpPrivkey("18ENGbLmM3xerSKTFh1SYRoDHMGSYk7Ch8").send().dumpPrivkey();
		
		
		
		
		
		
		System.out.println(result);
		
		//omni.importAddress("1FoWyxwPXuj4C6abqwhjDWdz6D4PZgYRjA").send();
		//omni.importPrivkey("1QBoiGauj2eGJRpYiiUuSWuhRKzF8faqqC").send();
	}
}
