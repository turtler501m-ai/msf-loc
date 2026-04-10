package com.ktmmobile.mcp.common.dto;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AuthSmsDto  implements Serializable  {
    private static final long serialVersionUID = 1L;


    private String phoneNum;
    private String authNum;
    private String startDate;
    private String endDate;
    private String message;
    private String smsNo;
    private boolean result = false;
    private boolean check = false;
    private boolean delete = false;
    private String menu;
    private String sendTime;
    private int duration;
    private String memberName;
    private String rateCd;
    private String rateNm;
    private String isReal;	// loacal,dev,stg : N, prd : Y
    private String reserved02; //발송목적
    private String reserved03; //발송자

    private String ctn;
    private String custId;
    private String subLinkName;
    private String svcCntrNo;
    private String birthday;

    //결합 관련
    private String svcNoTypeCd ;    //    svcNoTypeCd	회선구분코드	 인터넷:IT  모바일:MB    MVNO회선일 경우 MB만 가능
    private String sexCd	;        //성별코드	1	C	"(KT회선일경우 필수 )    1: 남성, 2: 여성"
    private String homeCombTerm;    //홈 결합(인터넷 + MVNO 무선) 할인 기간   무약정: N , 1년 :1 2년: 2 3년: 3
    private String unSSn;
    private String jobGubun	   ;    //       작업구분코드	1	M	N:신규결합신청, A:회선추가, D: 회선삭제
    private String sameCustYn;      //    동일명의여부

    private String contractNum;
    private String limitMin;




    /** 요금납부방법
     *  자동이체 : D
     *  신용카드 : C
     *  가상계좌 : VA
     *  자동충전(계좌이체) : AA
     * */
    private String reqPayType;





    /*
    validator.config['svcIdfyNo'] = 'isDate'; birthday
    validator.config['combUserNm'] = 'isNonEmpty'; subLinkName
    validator.config['ctn'] = 'isPhone'; ctn
    SvcCntrNo
    */


    /**
     * @return the phoneNum
     */
    public String getPhoneNum() {
        return phoneNum;
    }
    /**
     * @param phoneNum the phoneNum to set
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    /**
     * @return the authNum
     */
    public String getAuthNum() {
        if (authNum == null) {
            return "";
        }
        return authNum;
    }
    /**
     * @param authNum the authNum to set
     */
    public void setAuthNum(String authNum) {
        this.authNum = authNum;
    }
    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }
    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @return the result
     */
    public boolean isResult() {
        return result;
    }
    /**
     * @param result the result to set
     */
    public void setResult(boolean result) {
        this.result = result;
    }
    /**
     * @return the check
     */
    public boolean isCheck() {
        return check;
    }
    /**
     * @param check the check to set
     */
    public void setCheck(boolean check) {
        this.check = check;
    }
    /**
     * @return the menu
     */
    public String getMenu() {
        return menu;
    }
    /**
     * @param menu the menu to set
     */
    public void setMenu(String menu) {
        this.menu = menu;
    }
    public String getSmsNo() {
        return smsNo;
    }
    public void setSmsNo(String smsNo) {
        this.smsNo = smsNo;
    }
    public String getSendTime() {
        return sendTime;
    }
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * @return the delete
     */
    public boolean isDelete() {
        return delete;
    }
    /**
     * @param delete the delete to set
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    public String getRateCd() {
        return rateCd;
    }
    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }
    public String getRateNm() {
        return rateNm;
    }
    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }
    public String getCtn() {
        return ctn;
    }
    public void setCtn(String ctn) {
        this.ctn = ctn;
    }
    public String getCustId() {
        return custId;
    }
    public void setCustId(String custId) {
        this.custId = custId;
    }
    public String getSubLinkName() {
        return subLinkName;
    }
    public void setSubLinkName(String subLinkName) {
        this.subLinkName = subLinkName;
    }
    public String getSvcCntrNo() {
        return svcCntrNo;
    }
    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSvcNoTypeCd() {
        return svcNoTypeCd;
    }
    public void setSvcNoTypeCd(String svcNoTypeCd) {
        this.svcNoTypeCd = svcNoTypeCd;
    }
    public String getSexCd() {
        return sexCd;
    }
    public void setSexCd(String sexCd) {
        this.sexCd = sexCd;
    }


    public String getHomeCombTerm() {
        return homeCombTerm;
    }
    public void setHomeCombTerm(String homeCombTerm) {
        this.homeCombTerm = homeCombTerm;
    }
    public String getUnSSn() {
        return unSSn;
    }
    public void setUnSSn(String unSSn) {
        this.unSSn = unSSn;
    }


    public String getJobGubun() {
        return jobGubun;
    }
    public void setJobGubun(String jobGubun) {
        this.jobGubun = jobGubun;
    }


    public String getReqPayType() {
        return reqPayType;
    }

    public void setReqPayType(String reqPayType) {
        this.reqPayType = reqPayType;
    }


    public String getContractNum() {
        if (contractNum == null) {
            return "";
        }
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    //주민등록 번호로 생년 월일
    public String getBirthdayOfYYYY() {
        String rtnBirthDay = "";
        if(unSSn != null && unSSn.length() > 6) {
            rtnBirthDay = unSSn.substring(0, 6);
        } else {
            return "";
        }
        // 년도 붙이기
        if ("1".equals(unSSn.substring(6, 7)) || "2".equals(unSSn.substring(6, 7)) || "5".equals(unSSn.substring(6, 7)) || "6".equals(unSSn.substring(6, 7)) || "*".equals(unSSn.substring(6, 7))) {
            return  "19" + rtnBirthDay;
        } else if ("3".equals(unSSn.substring(6, 7)) || "4".equals(unSSn.substring(6, 7)) || "7".equals(unSSn.substring(6, 7)) || "8".equals(unSSn.substring(6, 7))) {
            return  "20" + rtnBirthDay;
        } else {
            return rtnBirthDay;
        }
    }

    //주민등록 번호 로 성별 구분 처리
    public String getSexCdOfSSn() {
        String rtnSexCd = "";
        if(unSSn == null || unSSn.length() < 6) {
            return "";
        }

        // 년도 붙이기
        if ("1".equals(unSSn.substring(6, 7)) || "3".equals(unSSn.substring(6, 7)) || "5".equals(unSSn.substring(6, 7)) || "7".equals(unSSn.substring(6, 7)) || "*".equals(unSSn.substring(6, 7)) )  {
            rtnSexCd = "1" ; //남자
        } else if ("2".equals(unSSn.substring(6, 7)) || "4".equals(unSSn.substring(6, 7)) || "6".equals(unSSn.substring(6, 7)) || "8".equals(unSSn.substring(6, 7))) {
            rtnSexCd = "2" ; //여자
        } else {
            return "";
        }

        return rtnSexCd;
    }

    //주민등록 번호 로 고객구분
    public String getCstmrType() {
        String rtnCstmrType = "";
        if(unSSn == null || unSSn.length() < 6) {
            return "";
        }

        //나이 확인
        int age = NmcpServiceUtils.getAge(unSSn, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));

        //외국인도????
        String diviVal = unSSn.substring(6, 7);

        if ("|5|6|7|8".indexOf(diviVal) > -1) {
            if (19 > age ) {
                rtnCstmrType ="";  //????? 있을까?????  외국인 청소년.. 구분 할수 없음 .. 없을것 같음
            } else {
                rtnCstmrType =Constants.CSTMR_TYPE_FN;
            }
        } else {
            if (19 > age ) {
                rtnCstmrType =Constants.CSTMR_TYPE_NM;
            } else {
                rtnCstmrType =Constants.CSTMR_TYPE_NA;
            }
        }

        return rtnCstmrType;
    }






    /*
     * if(userType != null && userType.length() == 13) { userType =
     * userType.substring(6,7); if("5".equals(userType) || "6".equals(userType)) {
     * userType = "Y"; searchVO.setUserType(userType); } }
     */

    /**
     * <pre>
     * 설명     : 인증 완료 여부
     * @param authSmsDto
     * </pre>
     */
    public boolean checkAuthTime(int chekTime) {

        String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");

        int btw = 0;
        try {
            btw = DateTimeUtil.minsBetween(startDate, today, "yyyyMMddHHmmss");
        } catch (ParseException e) {
            return false;
        }
        if(btw < chekTime) {
            return true;
        } else {
            return false;
        }

    }

    public void setLimitMin(String limitMin) {
        this.limitMin = limitMin;
    }

    public String getLimitMin() {
        return limitMin;
    }
    public String getIsReal() {
        return isReal;
    }
    public void setIsReal(String isReal) {
        this.isReal = isReal;
    }
    public String getReserved02() {
        return reserved02;
    }
    public void setReserved02(String reserved02) {
        this.reserved02 = reserved02;
    }
    public String getReserved03() {
        return reserved03;
    }
    public void setReserved03(String reserved03) {
        this.reserved03 = reserved03;
    }
    public String getSameCustYn() {
        return sameCustYn;
    }
    public void setSameCustYn(String sameCustYn) {
        this.sameCustYn = sameCustYn;
    }
}
