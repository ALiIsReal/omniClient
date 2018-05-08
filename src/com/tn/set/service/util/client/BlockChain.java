package com.tn.set.service.util.client;

import com.tn.set.service.pojo.Block;
import com.tn.set.service.pojo.BlockChainInfo;
import com.tn.set.service.pojo.BlockCount;
import com.tn.set.service.pojo.BlockHash;
import com.tn.set.service.pojo.WalletInfo;
import com.tn.set.service.util.client.base.Request;

public interface BlockChain {
	Request<?, BlockChainInfo> blockChainInfo();

	Request<?, BlockCount> blockCount();

	Request<?, BlockHash> getBlockHash(Integer blockNum);

	Request<?, Block> getBlock(String blockHash);

	
}
