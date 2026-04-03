package com.ktmmobile.msf.system.common.util;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.Map;

public class CommonHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(CommonHttpClient.class);

    private final String url;
    public CommonHttpClient(String url) {
        super();
        this.url = url;
    }

    public String post(NameValuePair[] data) throws SocketTimeoutException {
        String result = "";

        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.useragent", "MCP Client");
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
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            method.releaseConnection();
            if(br != null) try { br.close(); } catch (Exception fe) {logger.error(fe.getMessage());}
        }

        return result;
    }

    public String post(NameValuePair[] data, String encode) throws SocketTimeoutException  {
        String result = "";

        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.useragent", "MCP Client");
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

            }
        } catch (SocketTimeoutException e){
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            method.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
            if(br != null) try { br.close(); } catch (Exception fe) {logger.error(fe.getMessage());}
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
                logger.debug("Method failed: " + method.getStatusLine());
            }

            // Read the response body.
            byte[] responseBody = method.getResponseBody();

            // Deal with the response.
            // Use caution: ensure correct character encoding and is not binary data
            result = new String(responseBody, "UTF-8");
        } catch (HttpException e) {
            throw e;
        } catch (SocketTimeoutException e){
            logger.error(e.getMessage());
            throw e;
        } catch (IOException e) {
            throw e;
        }  finally {
            client.getHttpConnectionManager().closeIdleConnections(0);
            method.releaseConnection();
        }

        return result;
    }

    public String postUtf8(NameValuePair[] data) throws SocketTimeoutException {
        String result = "";

        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.useragent", "MCP Client");
        client.getParams().setParameter("http.protocol.expect-continue", false);
        client.getParams().setParameter("http.connection.timeout", 60000);
        client.getParams().setParameter("http.socket.timeout", 60000);



        BufferedReader br = null;
        PostMethod method = new PostMethod(url);
        method.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");

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
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            method.releaseConnection();
            if(br != null) try { br.close(); } catch (Exception fe) {logger.error(fe.getMessage());}
        }

        return result;
    }

    public String post(NameValuePair[] data, String encode, Map<String, String> addHead) throws SocketTimeoutException  {
        String result = "";

        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.useragent", "MCP Client");
        client.getParams().setParameter("http.protocol.expect-continue", false);
        client.getParams().setParameter("http.connection.timeout", 30000);
        client.getParams().setParameter("http.socket.timeout", 30000);


        BufferedReader br = null;
        PostMethod method = new PostMethod(url);
        if (addHead != null) {
            for (Map.Entry<String, String> entry : addHead.entrySet()) {
                method.setRequestHeader(entry.getKey(), entry.getValue());
            }
        }

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
            logger.error(e.getMessage());
        } finally {
            method.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
            if(br != null) try { br.close(); } catch (Exception fe) {logger.error(fe.getMessage());}
        }

        return result;
    }

}
