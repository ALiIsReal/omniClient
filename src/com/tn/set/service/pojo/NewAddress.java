package com.tn.set.service.pojo;

import com.tn.set.service.util.client.base.Response;

public class NewAddress extends Response<String>{
	public String getNewAddress() {
        return getResult();
    }
}
