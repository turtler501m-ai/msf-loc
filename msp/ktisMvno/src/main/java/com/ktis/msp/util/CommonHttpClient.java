package com.ktis.msp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CommonHttpClient {
    protected Logger log = LogManager.getLogger(getClass());
    private final String url;
    public CommonHttpClient(String url) {
        super();
        this.url = url;
    }

    public String post(NameValuePair[] data) throws SocketTimeoutException {
        String result = "";

        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.useragent", "MSP_client");
        client.getParams().setParameter("http.protocol.expect-continue", false);
        client.getParams().setParameter("http.connection.timeout", 30000);
        client.getParams().setParameter("http.socket.timeout", 30000);



        BufferedReader br = null;
        PostMethod method = new PostMethod(url);
        method.setRequestBody(data);
        try{
            int returnCode = client.executeMethod(method);
            if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
                // still consume the response body
                method.getResponseBodyAsString();
            } else {
                br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
                String readLine;
                while(((readLine = br.readLine()) != null)) {
                    result = result.concat(readLine);
                }
            }
        } catch (SocketTimeoutException e){
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
        } finally {
            method.releaseConnection();
            if(br != null) try { br.close(); } catch (Exception fe) {log.error(fe);}
        }

        return result;
    }

    public String post(NameValuePair[] data, String encode) throws SocketTimeoutException  {
        String result = "";

        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.useragent", "MSP_client");
        client.getParams().setParameter("http.protocol.expect-continue", false);
        client.getParams().setParameter("http.connection.timeout", 30000);
        client.getParams().setParameter("http.socket.timeout", 30000);


        BufferedReader br = null;
        PostMethod method = new PostMethod(url);
        method.setRequestBody(data);
        try{
            int returnCode = client.executeMethod(method);
            if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
                method.getResponseBodyAsString();
            } else {
                StringBuffer sb = new StringBuffer();

                br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), encode ));
                String readLine;
                while(((readLine = br.readLine()) != null)) {
                    sb.append(readLine);
                }
                result = sb.toString();

                log.error("####### ==== " + result);
            }
        } catch (SocketTimeoutException e){
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
        } finally {

            method.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
            if(br != null) try { br.close(); } catch (Exception fe) {log.error(fe);}
        }

        return result;
    }

    public String get() throws IOException ,SocketTimeoutException {
        String result = "";

        // Create an instance of HttpClient.
        HttpClient client = new HttpClient();
        // Create a method instance.
        client.getParams().setParameter("http.protocol.expect-continue", false);
        client.getParams().setParameter("http.connection.timeout", 30000);
        client.getParams().setParameter("http.socket.timeout", 30000);


        GetMethod method = new GetMethod(url);

        // Provide custom retry handler is necessary
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,  new DefaultHttpMethodRetryHandler(3, false));

        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                log.debug("Method failed: " + method.getStatusLine());
            }

            // Read the response body.
            byte[] responseBody = method.getResponseBody();

            // Deal with the response.
            // Use caution: ensure correct character encoding and is not binary data
            result = new String(responseBody, "UTF-8");
        } catch (HttpException e) {
            log.error(e);
            throw e;
        } catch (SocketTimeoutException e){
            log.error(e);
            throw e;
        } catch (IOException e) {
            log.error(e);
            throw e;
        }  finally {
            client.getHttpConnectionManager().closeIdleConnections(0);
            method.releaseConnection();
        }

        return result;
    }
    public static String Mplatpost(String url ,NameValuePair[] data, String encode , int timeout) throws SocketTimeoutException  {
        String result = "";

        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.useragent", "MSP_client");
        client.getParams().setParameter("http.protocol.expect-continue", false);
        client.getParams().setParameter("http.connection.timeout", timeout);
        client.getParams().setParameter("http.socket.timeout", timeout);


        BufferedReader br = null;
        PostMethod method = new PostMethod(url);
        method.setRequestBody(data);
        try{
            int returnCode = client.executeMethod(method);
            if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
                method.getResponseBodyAsString();
            } else {
                StringBuffer sb = new StringBuffer();

                br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), encode ));
                String readLine;
                while(((readLine = br.readLine()) != null)) {
                    sb.append(readLine);
                }
                result = sb.toString();
            }
        } catch (SocketTimeoutException e){
            throw e;
        } catch (Exception e) {
            e.getStackTrace();
        } finally {

            method.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
            client = null;
            if(br != null) try { br.close(); } catch (Exception fe) {fe.getStackTrace();}
        }

        return result;
    }
}
