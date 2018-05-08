package com.tn.set.service.util.client;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.pojo.Account;
import com.tn.set.service.pojo.Balance;
import com.tn.set.service.pojo.DumpPrivkey;
import com.tn.set.service.pojo.ImportAddress;
import com.tn.set.service.pojo.ImportPrivkey;
import com.tn.set.service.pojo.ListAccounts;
import com.tn.set.service.pojo.NewAddress;
import com.tn.set.service.pojo.SendToAddress;
import com.tn.set.service.pojo.WalletInfo;
import com.tn.set.service.util.client.base.Request;

public interface Wallet {
	Request<?, WalletInfo> walletInfo();

	Request<?, Balance> balance();

	Request<?, NewAddress> newAddress();

	Request<?, Account> account(String bitcoinaddress);

	Request<?, SendToAddress> sendToAddress(String bitcoinaddress, Double amount);

	Request<?, ListAccounts> listAccounts();
	Map<String, Object> createNewAddress();

	Request<?, ImportAddress> importAddress(String address);

	Request<?, ImportPrivkey> importPrivkey(String privkey);

	Request<?, DumpPrivkey> dumpPrivkey(String address);

	
}
