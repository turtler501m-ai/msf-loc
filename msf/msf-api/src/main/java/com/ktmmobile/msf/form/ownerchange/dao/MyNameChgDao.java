/**
 *
 */
package com.ktmmobile.msf.form.servicechange.dao;

import com.ktmmobile.msf.form.servicechange.dto.MyNameChgReqDto;

/**
 * @author ANT_FX700_02
 *
 */
public interface MyNameChgDao {
    /**
     * @Description : NMCP_CUST_REQUEST_MST insert
     */
    public int insertNmcpCustReqMst(MyNameChgReqDto myNameChgReqDto);

    /**
     * @Description : NMCP_CUST_REQUEST_NAME_CHG insert
     */
    public int insertNmcpCustReqNameChg(MyNameChgReqDto myNameChgReqDto);

    /**
     * @Description : NMCP_CUST_REQ_NAME_CHG_AGENT insert
     */
    public int insertNmcpCustReqNameChgAgent(MyNameChgReqDto myNameChgReqDto);
    /**
     * @Description : NMCP_CUST_REQUEST_MST insert
     */
    public int updateNmcpCustReqMst(MyNameChgReqDto myNameChgReqDto);

    /**
     * @Description : NMCP_CUST_REQUEST_NAME_CHG insert
     */
    public int updateNmcpCustReqNameChg(MyNameChgReqDto myNameChgReqDto);

}
