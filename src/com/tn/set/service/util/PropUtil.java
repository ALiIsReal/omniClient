package com.tn.set.service.util;

import java.io.InputStream;
import java.util.Properties;

public class PropUtil {

	private static Properties props = null;
	
	static {
		readProperties("omni.properties");
	}
	
	private static void readProperties(String fileName) {    
        try {    
            props = new Properties();
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			props.load(inputStream);
			inputStream.close();
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    } 
	
	public static Properties getProps() {
		return props;
	}
	
}
