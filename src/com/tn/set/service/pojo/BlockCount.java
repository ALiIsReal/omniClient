package com.tn.set.service.pojo;

import com.tn.set.service.util.client.base.Response;

public class BlockCount extends Response<Integer>{
	public Integer getBlockCount() {
        return getResult();
    }
}
