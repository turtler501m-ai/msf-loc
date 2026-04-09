package com.ktmmobile.msf.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientUtil {

    public static String post(String url ,NameValuePair[] data, String encode) throws SocketTimeoutException  {
        return post(url,data,encode,10000);
    }


    public static String post(String url ,NameValuePair[] data, String encode , int timeout) throws SocketTimeoutException  {
        String result = "";

        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.useragent", "MCP Client");
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
