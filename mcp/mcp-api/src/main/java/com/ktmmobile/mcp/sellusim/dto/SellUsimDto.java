package com.ktmmobile.mcp.sellusim.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


/**
 * @Class Name : PhoneProdBasDto
 * @Description : MCP 상품 기본 Dto
 *
 * @author : ant
 * @Create Date : 2016. 1. 5.
 */
public class SellUsimDto implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** 개통유심_키 */
    private int sellUsimKey;

    /** 유심_일련번호 */
    private String usimNo;

    /** 외국인_명 */
    private String frgnrName;

    /** 휴대폰_앞자리 */
    private String mobileFn;

    /** 휴대폰_중간자리 */
    private String mobileMn;

    /** 휴대폰_뒷자리 */
    private String mobileRn;

    /** 메일 */
    private String mail;

    /** 외국인_등록번호 */
    private String frgnrRn;

    /** 외국인_여권번호 */
    private String frgnrPn;

    /** 체류기간_시작일자 */
    private String vdateStart;

    /** 체류기간_종료일자 */
    private String vdateEnd;

    /** 여권이미지_명 */
    private String fileName;

    /** 여권이미지_MASK */
    private String fileMask;

    /** 언어타입 */
    private String langType;

    /** 글쓰기상태 00=대기중, 10=등록요청, 20=처리완료 */
    private String pState;

    /** 진행상태 */
    private String sState;

    /** 등록자아이피 */
    private String rip;

    /** 등록일시 */
    private String sysRdate;

    /** 수정자아이피 */
    private String uip;

    /**수정일시 */
    private String sysUdate;

    /**  */
    private String agentId;

    /** 기타이미지 */
    private String etcFileName;

    /** 기타이미지_MASK */
    private String etcFileMask;

    private MultipartFile file;

    private MultipartFile fileM;

    private int limitDay = 0;


    public int getSellUsimKey() {
        return sellUsimKey;
    }
    public void setSellUsimKey(int sellUsimKey) {
        this.sellUsimKey = sellUsimKey;
    }
    public String getUsimNo() {
        return usimNo;
    }
    public void setUsimNo(String usimNo) {
        this.usimNo = usimNo;
    }
    public String getFrgnrName() {
        return frgnrName;
    }
    public void setFrgnrName(String frgnrName) {
        this.frgnrName = frgnrName;
    }
    public String getMobileFn() {
        return mobileFn;
    }
    public void setMobileFn(String mobileFn) {
        this.mobileFn = mobileFn;
    }
    public String getMobileMn() {
        return mobileMn;
    }
    public void setMobileMn(String mobileMn) {
        this.mobileMn = mobileMn;
    }
    public String getMobileRn() {
        return mobileRn;
    }
    public void setMobileRn(String mobileRn) {
        this.mobileRn = mobileRn;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getFrgnrRn() {
        return frgnrRn;
    }
    public void setFrgnrRn(String frgnrRn) {
        this.frgnrRn = frgnrRn;
    }
    public String getFrgnrPn() {
        return frgnrPn;
    }
    public void setFrgnrPn(String frgnrPn) {
        this.frgnrPn = frgnrPn;
    }
    public String getVdateStart() {
        return vdateStart;
    }
    public void setVdateStart(String vdateStart) {
        this.vdateStart = vdateStart;
    }
    public String getVdateEnd() {
        return vdateEnd;
    }
    public void setVdateEnd(String vdateEnd) {
        this.vdateEnd = vdateEnd;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileMask() {
        return fileMask;
    }
    public void setFileMask(String fileMask) {
        this.fileMask = fileMask;
    }
    public String getLangType() {
        return langType;
    }
    public void setLangType(String langType) {
        this.langType = langType;
    }
    public String getpState() {
        return pState;
    }
    public void setpState(String pState) {
        this.pState = pState;
    }
    public String getsState() {
        return sState;
    }
    public void setsState(String sState) {
        this.sState = sState;
    }
    public String getRip() {
        return rip;
    }
    public void setRip(String rip) {
        this.rip = rip;
    }
    public String getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(String sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getUip() {
        return uip;
    }
    public void setUip(String uip) {
        this.uip = uip;
    }
    public String getSysUdate() {
        return sysUdate;
    }
    public void setSysUdate(String sysUdate) {
        this.sysUdate = sysUdate;
    }
    public String getAgentId() {
        return agentId;
    }
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
    public String getEtcFileName() {
        return etcFileName;
    }
    public void setEtcFileName(String etcFileName) {
        this.etcFileName = etcFileName;
    }
    public String getEtcFileMask() {
        return etcFileMask;
    }
    public void setEtcFileMask(String etcFileMask) {
        this.etcFileMask = etcFileMask;
    }
    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    public MultipartFile getFileM() {
        return fileM;
    }
    public void setFileM(MultipartFile fileM) {
        this.fileM = fileM;
    }
    public int getLimitDay() {
        return limitDay;
    }
    public void setLimitDay(int limitDay) {
        this.limitDay = limitDay;
    }


}
