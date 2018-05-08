package com.tn.set.service.util.client;

import com.tn.set.service.pojo.OmniGetAllBalancesForAddress;
import com.tn.set.service.pojo.OmniGetBalance;
import com.tn.set.service.pojo.OmniGetGrants;
import com.tn.set.service.pojo.OmniGetInfo;
import com.tn.set.service.pojo.OmniListProperties;
import com.tn.set.service.pojo.Omni_GetAllBalancesForId;
import com.tn.set.service.pojo.Omni_GetTrade;
import com.tn.set.service.pojo.Omni_Send;
import com.tn.set.service.util.client.base.Request;

public interface OmniLayer {

	Request<?, OmniGetBalance> omni_GetBalance(String address,
			Integer propertyid);

	Request<?, OmniGetInfo> omni_GetInfo();

	Request<?, OmniGetGrants> omni_GetGrants(Integer propertyid);

	Request<?, Omni_GetTrade> omni_GetTrade(String txid);

	Request<?, OmniListProperties> omni_ListProperties();

	Request<?, Omni_GetAllBalancesForId> omni_GetAllBalancesForId(
			Integer propertyid);

	Request<?, Omni_Send> omni_Send(String fromAddress, String toAddress,
			Integer propertyid, String amount);

	Request<?, OmniGetAllBalancesForAddress> omniGetAllBalancesForAddress(
			String address);

}
