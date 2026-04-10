package com.ktis.mcpif.juso.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ktis.mcpif.common.http.CommonHttpClient;

@Service("jusoService")
public class JusoService {
	public final static String URL = "http://www.juso.go.kr/";
	
	protected Log log = LogFactory.getLog(this.getClass());

	public String loadJuso(String url){
		CommonHttpClient client = new CommonHttpClient(url);
		try {
			return client.get();
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	public String getAreaCode(String url, String param){
		CommonHttpClient client = new CommonHttpClient(URL + url + "?" + param);
		try {
			return client.get();
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public String getAddrLink(String url, String param){
		CommonHttpClient client = new CommonHttpClient(URL + url + "?" + param);
		try {
			return client.get();
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
}
