package com.tn.set.service.pojo;

import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.util.client.base.Response;

public class WalletInfo extends Response<JSONObject>{
	public JSONObject getWalletInfo() {
        return getResult();
    }
}