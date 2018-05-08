package com.tn.set.service.pojo;

import com.tn.set.service.util.client.base.Response;

public class BlockHash extends Response<String>{
	public String getBlockHash(){
		return getResult();
	}
}
