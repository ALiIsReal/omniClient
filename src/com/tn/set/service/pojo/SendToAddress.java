package com.tn.set.service.pojo;

import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.util.client.base.Response;

public class SendToAddress extends Response<JSONObject>{
	public JSONObject SendToAddress() {
        return getResult();
    }
}