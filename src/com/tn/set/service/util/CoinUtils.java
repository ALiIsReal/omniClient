package com.tn.set.service.util;

import java.io.IOException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.util.client.base.Omni;

public class CoinUtils {
	static Omni omni = OmniClient.getOmni();
	private static JSONArray listproperty;
	
	public static  String getPropertyidName(Integer propertyid){
		try {
			listproperty = omni.omni_ListProperties().send().omni_ListProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject param ;
		for(int i = 0 ; i < listproperty.size();i++){
			param = listproperty.getJSONObject(i);
			if(param.getInteger("propertyid") == propertyid){
				return param.getString("name");
			}
		}
		return "";
	}
}
