package com.tn.set.service.pojo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.util.client.base.Response;

public class OmniListProperties extends Response<JSONArray>{
	
	public JSONArray omni_ListProperties(){
		return getResult();
	}
}
