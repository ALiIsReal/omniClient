package com.tn.set.service.pojo;

import com.tn.set.service.util.client.base.Response;

public class Balance extends Response<Double>{
	public Double getBalance() {
        return getResult();
    }
}
