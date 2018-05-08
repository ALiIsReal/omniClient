package com.tn.set.service.pojo;

import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.util.client.base.Response;

public class OmniGetGrants extends Response<JSONObject> {
	
	public JSONObject omni_getGrants(){
		return getResult();
	}
}
