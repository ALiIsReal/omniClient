package com.tn.set.service.pojo;

import com.tn.set.service.util.client.base.Response;

public class DumpPrivkey extends Response<String> {
	
	public String dumpPrivkey(){
		return getResult();
	}
}
