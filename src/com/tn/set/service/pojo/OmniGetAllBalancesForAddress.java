package com.tn.set.service.pojo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.util.CoinUtils;
import com.tn.set.service.util.client.base.Response;

public class OmniGetAllBalancesForAddress extends Response<JSONArray> {
	
	public JSONArray omniGetAllBalancesForAddress(){
		JSONArray params = getResult();
		JSONArray temps = new JSONArray();
		JSONObject temp = null;
		for(int i = 0 ; i < params.size();i++){
			String pname = CoinUtils.getPropertyidName(params.getJSONObject(i).getInteger("propertyid"));
			temp = params.getJSONObject(i);
			temp.put("pname", pname);
			temps.add(temp);
		}
		return temps;
	}
}
