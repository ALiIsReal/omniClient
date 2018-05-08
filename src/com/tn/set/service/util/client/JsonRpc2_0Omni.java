package com.tn.set.service.util.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;

import com.tn.set.service.pojo.Account;
import com.tn.set.service.pojo.Balance;
import com.tn.set.service.pojo.Block;
import com.tn.set.service.pojo.BlockChainInfo;
import com.tn.set.service.pojo.BlockCount;
import com.tn.set.service.pojo.BlockHash;
import com.tn.set.service.pojo.DumpPrivkey;
import com.tn.set.service.pojo.ImportAddress;
import com.tn.set.service.pojo.ImportPrivkey;
import com.tn.set.service.pojo.ListAccounts;
import com.tn.set.service.pojo.NewAddress;
import com.tn.set.service.pojo.OmniGetAllBalancesForAddress;
import com.tn.set.service.pojo.OmniGetBalance;
import com.tn.set.service.pojo.OmniGetGrants;
import com.tn.set.service.pojo.OmniGetInfo;
import com.tn.set.service.pojo.OmniListProperties;
import com.tn.set.service.pojo.Omni_GetAllBalancesForId;
import com.tn.set.service.pojo.Omni_GetTrade;
import com.tn.set.service.pojo.Omni_Send;
import com.tn.set.service.pojo.SendToAddress;
import com.tn.set.service.pojo.WalletInfo;
import com.tn.set.service.util.client.base.Async;
import com.tn.set.service.util.client.base.Omni;
import com.tn.set.service.util.client.base.OmniService;
import com.tn.set.service.util.client.base.Request;

public class JsonRpc2_0Omni implements Omni{

	public static final int DEFAULT_BLOCK_TIME = 15 * 1000;

	protected final OmniService omniService;
	// private final JsonRpc2_0Rx web3jRx;
	private final long blockTime;

	public JsonRpc2_0Omni(OmniService omniService) {
		this(omniService, DEFAULT_BLOCK_TIME, Async.defaultExecutorService());
	}

	public JsonRpc2_0Omni(OmniService omniService, long pollingInterval,
			ScheduledExecutorService scheduledExecutorService) {
		this.omniService = omniService;
		// this.web3jRx = new JsonRpc2_0Rx(this, scheduledExecutorService);
		this.blockTime = pollingInterval;
	}

	@Override
	public Request<?, BlockChainInfo> blockChainInfo() {
		return new Request<>("getblockchaininfo",
				Collections.<String> emptyList(), omniService,
				BlockChainInfo.class);
	}

	@Override
	public Request<?, WalletInfo> walletInfo() {
		return new Request<>("getwalletinfo", Collections.<String> emptyList(),
				omniService, WalletInfo.class);
	}

	@Override
	public Request<?, Balance> balance() {
		return new Request<>("getbalance", Collections.<String> emptyList(),
				omniService, Balance.class);
	}

	@Override
	public Request<?, NewAddress> newAddress() {
		return new Request<>("getnewaddress", Collections.<String> emptyList(),
				omniService, NewAddress.class);
	}

	@Override
	public Request<?, BlockCount> blockCount() {
		return new Request<>("getblockcount", Collections.<String> emptyList(),
				omniService, BlockCount.class);
	}

	@Override
	public Request<?, Account> account(String bitcoinaddress) {
		return new Request<>("getaccount", Arrays.asList(bitcoinaddress),
				omniService, Account.class);
	}

	@Override
	public Request<?, SendToAddress> sendToAddress(String bitcoinaddress,
			Double amount) {
		return new Request<>("sendtoaddress", Arrays.asList(bitcoinaddress,
				amount), omniService, SendToAddress.class);
	}

	@Override
	public Request<?, BlockHash> getBlockHash(Integer blockNum) {
		return new Request<>("getblockhash", 
				Arrays.asList(blockNum),
				omniService, 
				BlockHash.class);
	}
	@Override
	public Request<?, Block> getBlock(String blockHash) {
		return new Request<>("getblock", 
				Arrays.asList(blockHash),
				omniService, 
				Block.class);
	}
	@Override
	public Request<?, ListAccounts> listAccounts() {
		return new Request<>("listaccounts", Collections.<String> emptyList(),
				omniService, ListAccounts.class);
	}
	@Override
	public Request<?, OmniGetBalance> omni_GetBalance(String address,Integer propertyid) {
		return new Request<>("omni_getbalance", 
				Arrays.asList(address,propertyid),
				omniService, 
				OmniGetBalance.class);
	}
	
	@Override
	public Request<?, OmniGetInfo> omni_GetInfo() {
		return new Request<>("omni_getinfo", 
				Collections.<String> emptyList(),
				omniService, 
				OmniGetInfo.class);
	}
	//TODO false
	@Override
	public Request<?, OmniGetGrants> omni_GetGrants(Integer propertyid) {
		return new Request<>("omni_getgrants", 
				Arrays.asList(propertyid),
				omniService, 
				OmniGetGrants.class);
	}
	
	@Override
	public Request<?, Omni_GetTrade> omni_GetTrade(String txid) {
		return new Request<>("omni_gettrade", 
				Arrays.asList(txid),
				omniService, 
				Omni_GetTrade.class);
	}
	
	@Override
	public Request<?, OmniListProperties> omni_ListProperties() {
		return new Request<>("omni_listproperties", Collections.<String> emptyList(),
				omniService, OmniListProperties.class);
	}
	
	@Override
	public Request<?, Omni_GetAllBalancesForId> omni_GetAllBalancesForId(Integer propertyid) {
		return new Request<>("omni_getallbalancesforid", 
				Arrays.asList(propertyid),
				omniService, 
				Omni_GetAllBalancesForId.class);
	}
	@Override
	public Request<?, Omni_Send> omni_Send(String fromAddress,String toAddress,Integer propertyid,String amount) {
		return new Request<>("omni_getallbalancesforid", 
				Arrays.asList(fromAddress,toAddress,propertyid,amount),
				omniService, 
				Omni_Send.class);
	}
	
	@Override
	public Request<?, OmniGetAllBalancesForAddress> omniGetAllBalancesForAddress(String address) {
		return new Request<>("omni_getallbalancesforaddress", 
				Arrays.asList(address),
				omniService, 
				OmniGetAllBalancesForAddress.class);
	}

	@Override
	public Request<?, ImportAddress> importAddress(String address) {
		return new Request<>("importaddress", 
				Arrays.asList(address),
				omniService, 
				ImportAddress.class);
	}
	
	@Override
	public Request<?, ImportPrivkey> importPrivkey(String privkey) {
		return new Request<>("importprivkey", 
				Arrays.asList(privkey),
				omniService, 
				ImportPrivkey.class);
	}
	
	@Override
	public Request<?, DumpPrivkey> dumpPrivkey(String address) {
		return new Request<>("dumpprivkey", 
				Arrays.asList(address),
				omniService, 
				DumpPrivkey.class);
	}
	
	
	
	@Override
	public Map<String,Object> createNewAddress() {
		NetworkParameters params = MainNetParams.get();
		ECKey key = new ECKey();
		//System.out.println("We create a new key:\n "+key);
		Address addressFromKey = key.toAddress(params);
		//System.out.println("Public Address generated: "+addressFromKey);
		
		//System.out.println("Private key is: "+key.getPrivateKeyAsWiF(params).toString());
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("address", addressFromKey);
		result.put("privateKey", key.getPrivateKeyAsWiF(params));
		return result;
	}

	
}
