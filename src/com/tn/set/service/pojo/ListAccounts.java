package com.tn.set.service.pojo;

import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.util.client.base.Response;

public class ListAccounts extends Response<JSONObject> {
	public JSONObject listAccounts() {
        return getResult();
    }
}
