package com.tn.set.service.pojo;

import com.tn.set.service.util.client.base.Response;

public class Account extends Response<Double>{
	public Double getAccount() {
        return getResult();
    }
}
