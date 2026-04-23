package com.ktis.mcpif.common.http;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
public class CommonHttpClient {
	private final String url;
	protected Logger logger = LogManager.getLogger(getClass());
	public CommonHttpClient(String url) {
		super();
		this.url = url;
	}
	
	public String get() throws HttpException, IOException{
		String result = "";
		
		// Create an instance of HttpClient.
		HttpClient client = new HttpClient();
		// Create a method instance.
		GetMethod method = new GetMethod(url);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,  new DefaultHttpMethodRetryHandler(3, false));

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				logger.debug("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary data
			result = new String(responseBody, "UTF-8");
		} catch (HttpException e) {
			logger.error(e);
			throw e;
		} catch (IOException e) {
			logger.error(e);
			throw e;
		} finally {
			method.releaseConnection();
		}

		return result;
	}
	
	public String get(String encode) throws HttpException, IOException{
		String result = "";
		
		// Create an instance of HttpClient.
		HttpClient client = new HttpClient();
		// Create a method instance.
		GetMethod method = new GetMethod(url);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,  new DefaultHttpMethodRetryHandler(3, false));

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				logger.debug("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary data
			result = new String(responseBody, encode);
		} catch (HttpException e) {
			logger.error(e);
			throw e;
		} catch (IOException e) {
			logger.error(e);
			throw e;
		} finally {
			method.releaseConnection();
		}

		return result;
	}
}
