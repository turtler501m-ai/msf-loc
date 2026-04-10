package com.ktmmobile.mcp.cmmn.util;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NmcpServiceUtils {

	private static Logger logger = LoggerFactory.getLogger(NmcpServiceUtils.class);

    /**
     * <pre>
     * 설명     : 년도가 있는 전체 주민번호
     * @param custNo
     * @return
     * </pre>
     */
    public static String getSsnDate(String custNo) {

        String rtnStr = custNo;

        if (custNo.equals("") || custNo.length() < 7) {
            return rtnStr;
        } else {
            // 년도 붙이기
            /*
             * 5 : 1900년대6: 1900 년대
                7, 8 :2000년대
             */
            if ( Integer.parseInt(custNo.substring(6, 7)) == 1 || Integer.parseInt(custNo.substring(6, 7)) == 2 || Integer.parseInt(custNo.substring(6, 7)) == 5 || Integer.parseInt(custNo.substring(6, 7)) == 6 ) {
                rtnStr = "19" + custNo.substring(0, 6);  //
            } else if (Integer.parseInt(custNo.substring(6, 7)) == 3 || Integer.parseInt(custNo.substring(6, 7)) == 4 || Integer.parseInt(custNo.substring(6, 7)) == 7 || Integer.parseInt(custNo.substring(6, 7)) == 8 ) {
                rtnStr = "20" + custNo.substring(0, 6);
            } else {
                rtnStr = "19" + custNo.substring(0, 6);  //
            }
        }

        return rtnStr;
    }
    
    public static String getPropertiesVal(String id) {
    	String resource = "/mcp/properties/common.properties";
        Properties properties = new Properties();
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            properties.load(reader);
        } catch (IOException e) {
            return null;
        } finally {
        	if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        return properties.getProperty(id);
        
    }

}
