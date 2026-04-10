package com.ktis.msp.org.csanalyticmgmt.vo;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.mvc.BaseVo;

import java.io.Serializable;

public class AcenHistVO extends BaseVo implements Serializable {

  private static final long serialVersionUID = 1L;

  // 연동이력 관련 파라미터
  private String resNo;
  private String rsltDttm;
  private String evntTypeNm;
  private String chnlCdNm;
  private String chnlCd;
  private String rsltCd;
  private String rsltMsg;
  private String procCd;
  private String errCd;
  private String errMsg;

  // 페이징 처리
  public int TOTAL_COUNT;
  public String ROW_NUM;
  public String LINENUM;

  public String getResNo() {
    return resNo;
  }

  public void setResNo(String resNo) {
    this.resNo = resNo;
  }

  public String getRsltDttm() {
    return rsltDttm;
  }

  public void setRsltDttm(String rsltDttm) {
    this.rsltDttm = rsltDttm;
  }

  public String getEvntTypeNm() {
    return evntTypeNm;
  }

  public void setEvntTypeNm(String evntTypeNm) {
    this.evntTypeNm = evntTypeNm;
  }

  public String getChnlCdNm() {
    return chnlCdNm;
  }

  public void setChnlCdNm(String chnlCdNm) {
    this.chnlCdNm = chnlCdNm;
  }

  public String getChnlCd() {
    return chnlCd;
  }

  public void setChnlCd(String chnlCd) {
    this.chnlCd = chnlCd;
  }

  public String getRsltCd() {
    return rsltCd;
  }

  public void setRsltCd(String rsltCd) {
    this.rsltCd = rsltCd;
  }

  public String getRsltMsg() {
    return rsltMsg;
  }

  public void setRsltMsg(String rsltMsg) {
    this.rsltMsg = rsltMsg;
  }

  public String getProcCd() {
    return procCd;
  }

  public void setProcCd(String procCd) {
    this.procCd = procCd;
  }

  public String getErrCd() {
    return errCd;
  }

  public void setErrCd(String errCd) {
    this.errCd = errCd;
  }

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

  public int getTOTAL_COUNT() {
    return TOTAL_COUNT;
  }

  public void setTOTAL_COUNT(int TOTAL_COUNT) {
    this.TOTAL_COUNT = TOTAL_COUNT;
  }

  public String getROW_NUM() {
    return ROW_NUM;
  }

  public void setROW_NUM(String ROW_NUM) {
    this.ROW_NUM = ROW_NUM;
  }

  public String getLINENUM() {
    return LINENUM;
  }

  public void setLINENUM(String LINENUM) {
    this.LINENUM = LINENUM;
  }

  // 화면에 표출하는 연동결과
  public String getvRsltMsg() {
    return isHistSuccess() ? "성공" : "실패";
  }

  // 화면에 효출하는 연동실패상세
  public String getvErrMsg() {

    String errMsg = "";

    if(!isHistSuccess()){
      errMsg = ("01".equals(chnlCd)) ? this.errMsg : rsltMsg;
      if(KtisUtil.isEmpty(errMsg)){
        errMsg = "기타오류";
      }
    }

    return errMsg;
  }

  /** 연동 성공여부 */
  private boolean isHistSuccess(){

    // 해피콜 요청(01) : rsltCd가 1이면서 procCd가 0000인 경우 성공
    // 해피콜 응답(02) : rsltCd가 1인 경우 성공

    boolean result;

    if("01".equals(chnlCd)){

      if("1".equals(rsltCd) && "0000".equals(procCd)){
        result = true;
      }else{
        result = false;
      }

    }else{
      if("1".equals(rsltCd)){
        result = true;
      }else{
        result = false;
      }
    }

    return result;
  }

}
