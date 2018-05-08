package com.tn.set.service.util;

import com.tn.set.service.util.client.base.HttpService;
import com.tn.set.service.util.client.base.Omni;

public class OmniClient {
	private static String ip = PropUtil.getProps().getProperty("URL");

	private OmniClient() {
	}

	private volatile static Omni omni;
	public static Omni getOmni() {
		if (omni == null) {
			synchronized (OmniClient.class) {
				if (omni == null) {
					omni = Omni.build(new HttpService(ip));
				}
			}
		}
		return omni;
	}
}
