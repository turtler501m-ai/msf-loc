package com.ktmmobile.mcp.mypage.dto;

import java.io.Serializable;

public class CommendStateDto implements Serializable {

    private static final long serialVersionUID = 1L;



    /** 최초 개통일자 yyyyMM    */
    private String  lstComActvDate ;

    /** 구매유형 단말
     * 구매:MM
     * USIM(유심)단독 구매:UU
     * */
    private String  reqBuyType ;

    /** 추천 아이디     * */
    private String  commendId ;

    private int  sumCount ;

    /** 가입계약번호 */
    private String contractNum ;

    public String getLstComActvDate() {
        return lstComActvDate;
    }

    public void setLstComActvDate(String lstComActvDate) {
        this.lstComActvDate = lstComActvDate;
    }

    public String getReqBuyType() {
        return reqBuyType;
    }

    public void setReqBuyType(String reqBuyType) {
        this.reqBuyType = reqBuyType;
    }

    public String getCommendId() {
        return commendId;
    }

    public void setCommendId(String commendId) {
        this.commendId = commendId;
    }

    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }


    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }






}
