package com.ktmmobile.msf.form.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.ktmmobile.msf.form.common.dto.UsimMspPlcyDto;
import com.ktmmobile.msf.form.common.dto.UsimMspOperTypeDto;
import com.ktmmobile.msf.form.common.dto.UsimMspRateDto;

/**
 * @Class Name : PhoneProdBasDto
 * @Description : MCP 상품 기본 Dto
 *
 * @author : ant
 * @Create Date : 2016. 1. 5.
 */
public class UsimBasDto implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 6585519884371461810L;

    /** 단품정보 */
    List<UsimMspPlcyDto> usimMspPlcyDtoList;

    /** 가입유형 */
    List<UsimMspOperTypeDto> usimMspOperTypeDtoList;

    /** 요금제정보 */
    List<UsimMspRateDto> usimMspRateDtoList;

    /** 유심아이디 */
    private String orgnId;

    /** 유심아이디 */
    private int usimSeq;

    /** 유심설명 */
    private String usimDesc;

    /** 정책코드 */
    private String salePlcyCd;

    /** 정책명 */
    private String salePlcyNm;

    /** 상품코드 */
    private String rateCd;

    /** 상품명 */
    private String rateNm;

    /** 상품종류 */
    private String prdtSctnCd;

    /** 노출여부 */
    private String viewYn;

    /** 이미지 경로 */
    private String imgPath;

    /** 생성자 id*/
    private String cretId;

    /** 수정자 id */
    private String amdId;

    /** 생성일시 */
    private String cretDt;

    /** 수정일시 */
    private String amdDt;

    /** 파일 */
    private MultipartFile file;

    /** 선불 후불구분 */
    private String payClCd;

    /** 글번호 */
    private String rowNum;

    /** 데이터 타입 */
    private String dataType;

    private String prdtCode;

    private String prdtNm;

    private String prdtId;

    private String operType;

    private String agrmTrm;

    /** 추천 요금제 */
    private String bestRate;

    /** 리스트를 한개만 추출 */
    private String selectOne ;

    /** 유심 유형_ eSIM 외 의미 없음
     * 01 나노유심
     * 02 LTE일반유심
     * 03 3G일반유심
     * 04 3G나노유심
     * 05 LTE보급유심
     * 06 미발송
     * 07 5G유심
     * 08 NFC유심
     * 10 eSIM
    */
    private String prdtIndCd ;

    /** 다정책반영 */
    private ArrayList<String> salePlcyCdList;

    /** 정렬순서 */
    private String paySort;

    private String[] arrPaySort;
    private String[] arrRateCd;
    private String[] arrRateNm;
    private String[] arrSalePlcyCd;



    public String[] getArrSalePlcyCd() {
        return arrSalePlcyCd;
    }

    public void setArrSalePlcyCd(String[] arrSalePlcyCd) {
        this.arrSalePlcyCd = arrSalePlcyCd;
    }

    public String[] getArrPaySort() {
        return arrPaySort;
    }

    public void setArrPaySort(String[] arrPaySort) {
        this.arrPaySort = arrPaySort;
    }

    public String[] getArrRateCd() {
        return arrRateCd;
    }

    public void setArrRateCd(String[] arrRateCd) {
        this.arrRateCd = arrRateCd;
    }

    public String[] getArrRateNm() {
        return arrRateNm;
    }

    public void setArrRateNm(String[] arrRateNm) {
        this.arrRateNm = arrRateNm;
    }

    public String getPaySort() {
        return paySort;
    }

    public void setPaySort(String paySort) {
        this.paySort = paySort;
    }

    public List<UsimMspPlcyDto> getUsimMspPlcyDtoList() {
        return usimMspPlcyDtoList;
    }

    public void setUsimMspPlcyDtoList(List<UsimMspPlcyDto> usimMspPlcyDtoList) {
        this.usimMspPlcyDtoList = usimMspPlcyDtoList;
    }

    public int getUsimSeq() {
        return usimSeq;
    }

    public void setUsimSeq(int usimSeq) {
        this.usimSeq = usimSeq;
    }

    public String getUsimDesc() {
        return usimDesc;
    }

    public void setUsimDesc(String usimDesc) {
        this.usimDesc = usimDesc;
    }

    public String getSalePlcyCd() {
        return salePlcyCd;
    }

    public void setSalePlcyCd(String salePlcyCd) {
        this.salePlcyCd = salePlcyCd;
    }

    public String getSalePlcyNm() {
        return salePlcyNm;
    }

    public void setSalePlcyNm(String salePlcyNm) {
        this.salePlcyNm = salePlcyNm;
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

    public String getViewYn() {
        return viewYn;
    }

    public void setViewYn(String viewYn) {
        this.viewYn = viewYn;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getAmdId() {
        return amdId;
    }

    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }

    public String getCretDt() {
        return cretDt;
    }

    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }

    public String getAmdDt() {
        return amdDt;
    }

    public void setAmdDt(String amdDt) {
        this.amdDt = amdDt;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getPrdtSctnCd() {
        return prdtSctnCd;
    }

    public void setPrdtSctnCd(String prdtSctnCd) {
        this.prdtSctnCd = prdtSctnCd;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getPayClCd() {
        return payClCd;
    }

    public void setPayClCd(String payClCd) {
        this.payClCd = payClCd;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<UsimMspOperTypeDto> getUsimMspOperTypeDtoList() {
        return usimMspOperTypeDtoList;
    }

    public void setUsimMspOperTypeDtoList(
            List<UsimMspOperTypeDto> usimMspOperTypeDtoList) {
        this.usimMspOperTypeDtoList = usimMspOperTypeDtoList;
    }

    public List<UsimMspRateDto> getUsimMspRateDtoList() {
        return usimMspRateDtoList;
    }

    public void setUsimMspRateDtoList(List<UsimMspRateDto> usimMspRateDtoList) {
        this.usimMspRateDtoList = usimMspRateDtoList;
    }

    public String getOrgnId() {
        return orgnId;
    }

    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }

    public String getPrdtCode() {
        return prdtCode;
    }

    public void setPrdtCode(String prdtCode) {
        this.prdtCode = prdtCode;
    }

    public String getPrdtNm() {
        return prdtNm;
    }

    public void setPrdtNm(String prdtNm) {
        this.prdtNm = prdtNm;
    }

    public String getPrdtId() {
        return prdtId;
    }

    public void setPrdtId(String prdtId) {
        this.prdtId = prdtId;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getBestRate() {
        return bestRate;
    }

    public void setBestRate(String bestRate) {
        this.bestRate = bestRate;
    }

    public String getSelectOne() {
        return selectOne;
    }

    public void setSelectOne(String selectOne) {
        this.selectOne = selectOne;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getAgrmTrm() {
        return agrmTrm;
    }

    public void setAgrmTrm(String agrmTrm) {
        this.agrmTrm = agrmTrm;
    }

    public ArrayList<String> getSalePlcyCdList() {
        return salePlcyCdList;
    }

    public void setSalePlcyCdList(ArrayList<String> al) {
        this.salePlcyCdList = al;
    }

    public String getPrdtIndCd() {
        return prdtIndCd;
    }

    public void setPrdtIndCd(String prdtIndCd) {
        this.prdtIndCd = prdtIndCd;
    }


}
