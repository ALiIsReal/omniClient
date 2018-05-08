package com.tn.set.service.pojo;

import com.tn.set.service.util.client.base.Response;

public class ImportAddress extends Response<String> {
	
	public String importAddress(){
		return getResult();
	}
}
