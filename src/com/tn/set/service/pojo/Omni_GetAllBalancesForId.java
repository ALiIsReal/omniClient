package com.tn.set.service.pojo;

import com.alibaba.fastjson.JSONArray;
import com.tn.set.service.util.client.base.Response;

public class Omni_GetAllBalancesForId extends Response<JSONArray>{
	public JSONArray omni_GetAllBalancesForId(){
		return getResult();
	}
}
