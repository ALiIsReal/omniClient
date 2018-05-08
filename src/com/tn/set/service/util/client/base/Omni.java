package com.tn.set.service.util.client.base;

import com.tn.set.service.pojo.OmniGetBalance;
import com.tn.set.service.util.client.BlockChain;
import com.tn.set.service.util.client.Control;
import com.tn.set.service.util.client.JsonRpc2_0Omni;
import com.tn.set.service.util.client.OmniLayer;
import com.tn.set.service.util.client.Rawtransactions;
import com.tn.set.service.util.client.Wallet;






public interface Omni extends BlockChain ,Wallet,Rawtransactions,Control,OmniLayer{
	static Omni build(OmniService omniService) {
        return new JsonRpc2_0Omni(omniService);
    }


	

	
}
