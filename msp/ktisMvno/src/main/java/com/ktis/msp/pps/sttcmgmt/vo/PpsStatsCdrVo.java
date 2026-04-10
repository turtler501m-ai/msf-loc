package com.ktis.msp.pps.sttcmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsStatsCdrVo")
public class PpsStatsCdrVo extends BaseVo  implements Serializable {
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359254L;
	
	/*
	 * 통계월
	 */
	private String statMonth;
	
	/*
	 * 계약번호
	 */
	private String contractNum;
	
	private String contractNumStr;
	
	/**
	 * 계약자
	 */
	private String subLinkName;
	
	/**
	 * 국적코드
	 */
	private String adNation;
	
	/**
	 * 국적코드명
	 */
	private String adNationNm;
	
	/**
	 * 요금제코드
	 */
	private String soc;
	/**
	 * 요금제명
	 */
	private String serviceNm;
	
	/**
	 * 최초요금제코드
	 */
	private String firstsoc;
	
	/**
	 * 최초요금제명
	 */
	private String firstServiceNm;
	
	/**
	 * 회원상태
	 */
	private String subStatus;
	
	/**
	 * 회원상태명 
	 */
	private String subStatusNm;
	
	/**
	 * 개통일자
	 */
	private String enterDate;
	/**
	 * 정지일자
	 */
	private String statusStopDt;
	
	/**
	 * 취소일자
	 */
	private String statusCancelDt;
	
	/**
	 * 잔액
	 */
	private double basicRemains;

	private String strTotPktDur;
	private String strBasicRemains;
	
	/**
	 * 만료일자
	 */
	private String basicExpire;
	
	/**
	 *모델명 
	 */
	private String modelName;
	
	/**
	 * 사용기간
	 */
	private int useTerm;
	
	/**
	 * 최종충전일
	 */
	private String lastBasicChgDt;
	
	/**
	 * 개통대리점
	 */
	private String agentId;
	
	/**
	 * 개통대리점명
	 */
	private String agentNm;
	
	
	/**
	 * 판매점
	 * 
	 */
	private String agentSaleId;

	/**
	 * 판매점명
	 */
	private String agentSaleNm;
	
	/**
	 * 총충전시도수
	 */
	private int tryRcgCnt;
	
	/**
	 * 총충전시도금액
	 */
	private int tryRcgCharge;
	
	/**
	 * 조정금액
	 */
	private int rcgEditCharge;
	
	/**
	 * 무료충전액
	 */
	private int rcgFreeCharge;
	/**
	 * 유료충전액
	 */
	private int rcgPayCharge;
	
	/**
	 * 무료취소액
	 */
	private int rcgFreeCancelCharge;
	/**
	 * 유료취소액
	 */
	private int rcgPayCancelCharge;
	
	/**
	 * 총충전액(성공만)
	 */
	private int totRcgCharge;
	
	/**
	 * 선불카드충전
	 */
	private int rcgCardCharge;
	
	/**
	 * CMS충전
	 */
	private int rcgCmsCharge;

	/**
	 * 가상계좌충전
	 */
	private int rcgVacCharge;
	
	/**
	 * 편의점충전
	 */
	private int rcgPosCharge;
	
	/**
	 * 기타 충전
	 */
	private int rcgEtcCharge;
	
	/**
	 * 사용횟수
	 */
	private int useCnt;
	/**
	 * 사용분
	 */
	private double useDur;
	
	private String strUseDur;
	
	/**
	 * 사용액
	 */
	private double useCharge;
	
	private String strUseCharge;
	
	
	/**
	 * 사용데이터
	 */
    private double usePkt;
    
	private String strUsePkt;
    
    /**
     * 국내음성(분)
     */
    private double vocNDur;
    
    private String strVocNDur;
    
	/**
	 * 국내음성통화료
	 */
	private double vocNCharge;
	
	private String strVocNCharge;
	
	
	/**
	 * 국제음성(분)
	 */
	private double vocIDur;
	
	private String strVocIDur;
	
	
	/**
	 * 국제음성통화료
	 */
	private double vocICharge;
	
	private String strVocICharge;
	
	/**
	 * 국내영상통화(분)
	 */
	private double vidNDur;
	
	private String strVidNDur; 
	
	
	/**
	 * 국내영상통화료	 
	 */
	private double vidNCharge;
	
	private String strVidNCharge;
	
	
	/**
	 * 국제영상통화(분)
	 */
	private double vidIDur;
	
	private String strVidIDur;
	
	/**
	 * 국제영상통화료
	 */
	private double vidICharge;
	private String strVidICharge;
	
	
	
	/**
	 * 국내문자건수
	 */
	private int smsNCnt;
	
	
	
	/**
	 * 국내문자료
	 */
	private double smsNCharge;
	private String strSmsNCharge;
	
	
	/**
	 * 국제문자건수
	 */
	private int smsICnt;
	
	/**
	 * 국제문자료
	 */
	private double smsICharge;
	private String strSmsICharge;
	
	
	/**
	 * 국내데이터사용량(M)
	 */
	private double pktNDur;
	private String strPktNDur;
	
	/**
	 * 국내데이터료
	 */
	private double pktNCharge;
	private String strPktNCharge;
	
	
	/**
	 * 국제데이터사용량(M)
	 */
	private double pktIDur;
	private String strPktIDur;
	/**
	 * 국제데이터료
	 */
	private double pktICharge;
	private String strPktICharge;
	
		
	/**
	 * 일차감건수
	 */
	private int dayCnt;
	
	/**
	 * 일차감액
	 */
	private double dayCharge;
	private String strDayCharge;
	
	
	/**
	 * 기타건수
	 */
	private int etcCnt;
	
	/**
	 * 기타사용시간(분)
	 */
	private double etcDur;
	private String strEtcDur;
	
	
	
	/**
	 * 기타사용데이터량
	 */
	private double etcPkt;
	private String strEtcPkt;
	
	
	/**
	 * 기타사용금액
	 */
	private double etcCharge;
	private String strEtcCharge;
	
	
	private String recordDate;
	
	
	
	
	/**
	 * 데이터 금액(합계)
	 */
	private double totPktCharge;
	private String strTotPktCharge;
	
	
	/**
	 * 음성사용액(합계)
	 */
	private double totVocCharge;
	private String strTotVocCharge;
		
		
	
	/**
	 * 음성사용분(합계)
	 */
	private double totVocDur;
	private String strTotVocDur;
	
	
	/**
	 * 영상사용액(합계)
	 */
	private double totVidCharge;
	private String strTotVidCharge;
		
	
	
	/**
	 * 영상사용분(합계)
	 */
	private double totVidDur;
	private String strTotVidDur;
	
	/**
	 * 문자횟수(합계)
	 */
	private int totSmsCnt;
	
	/**
	 * 문자사용금액(합계)
	 */
	private double totSmsCharge;
	private String strTotSmsCharge;
	
	/**
	 * 데이터 사용분(합계)
	 */
	private double totPktDur;
	/**
	 * @return the totSmsCnt
	 */
	public int getTotSmsCnt() {
		return totSmsCnt;
	}









	/**
	 * @param totSmsCnt the totSmsCnt to set
	 */
	public void setTotSmsCnt(int totSmsCnt) {
		this.totSmsCnt = totSmsCnt;
	}









	/**
	 * @return the totSmsCharge
	 */
	public double getTotSmsCharge() {
		return totSmsCharge;
	}









	/**
	 * @param totSmsCharge the totSmsCharge to set
	 */
	public void setTotSmsCharge(double totSmsCharge) {
		this.totSmsCharge = totSmsCharge;
	}









	/**
	 * @return the strTotSmsCharge
	 */
	public String getStrTotSmsCharge() {
		return strTotSmsCharge;
	}









	/**
	 * @param strTotSmsCharge the strTotSmsCharge to set
	 */
	public void setStrTotSmsCharge(String strTotSmsCharge) {
		this.strTotSmsCharge = strTotSmsCharge;
	}









	/**
	 * @return the totPktDur
	 */
	public double getTotPktDur() {
		return totPktDur;
	}









	/**
	 * @param totPktDur the totPktDur to set
	 */
	public void setTotPktDur(double totPktDur) {
		this.totPktDur = totPktDur;
	}









	/**
	 * @return the strTotPktDur
	 */
	public String getStrTotPktDur() {
		return strTotPktDur;
	}









	/**
	 * @param strTotPktDur the strTotPktDur to set
	 */
	public void setStrTotPktDur(String strTotPktDur) {
		this.strTotPktDur = strTotPktDur;
	}









	
	
	
	
	
	/**
	 * @return the statMonth
	 */
	public String getStatMonth() {
		return statMonth;
	}









	/**
	 * @param statMonth the statMonth to set
	 */
	public void setStatMonth(String statMonth) {
		this.statMonth = statMonth;
	}









	/**
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}









	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}









	/**
	 * @return the subLinkName
	 */
	public String getSubLinkName() {
		return subLinkName;
	}









	/**
	 * @param subLinkName the subLinkName to set
	 */
	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}









	/**
	 * @return the adNation
	 */
	public String getAdNation() {
		return adNation;
	}









	/**
	 * @param adNation the adNation to set
	 */
	public void setAdNation(String adNation) {
		this.adNation = adNation;
	}









	/**
	 * @return the adNationNm
	 */
	public String getAdNationNm() {
		return adNationNm;
	}









	/**
	 * @param adNationNm the adNationNm to set
	 */
	public void setAdNationNm(String adNationNm) {
		this.adNationNm = adNationNm;
	}









	/**
	 * @return the soc
	 */
	public String getSoc() {
		return soc;
	}









	/**
	 * @param soc the soc to set
	 */
	public void setSoc(String soc) {
		this.soc = soc;
	}









	/**
	 * @return the serviceNm
	 */
	public String getServiceNm() {
		return serviceNm;
	}









	/**
	 * @param serviceNm the serviceNm to set
	 */
	public void setServiceNm(String serviceNm) {
		this.serviceNm = serviceNm;
	}









	/**
	 * @return the firstsoc
	 */
	public String getFirstsoc() {
		return firstsoc;
	}









	/**
	 * @param firstsoc the firstsoc to set
	 */
	public void setFirstsoc(String firstsoc) {
		this.firstsoc = firstsoc;
	}









	/**
	 * @return the firstServiceNm
	 */
	public String getFirstServiceNm() {
		return firstServiceNm;
	}









	/**
	 * @param firstServiceNm the firstServiceNm to set
	 */
	public void setFirstServiceNm(String firstServiceNm) {
		this.firstServiceNm = firstServiceNm;
	}









	/**
	 * @return the subStatus
	 */
	public String getSubStatus() {
		return subStatus;
	}









	/**
	 * @param subStatus the subStatus to set
	 */
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}









	/**
	 * @return the subStatusNm
	 */
	public String getSubStatusNm() {
		return subStatusNm;
	}









	/**
	 * @param subStatusNm the subStatusNm to set
	 */
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}









	/**
	 * @return the enterDate
	 */
	public String getEnterDate() {
		return enterDate;
	}









	/**
	 * @param enterDate the enterDate to set
	 */
	public void setEnterDate(String enterDate) {
		this.enterDate = enterDate;
	}









	/**
	 * @return the statusStopDt
	 */
	public String getStatusStopDt() {
		return statusStopDt;
	}









	/**
	 * @param statusStopDt the statusStopDt to set
	 */
	public void setStatusStopDt(String statusStopDt) {
		this.statusStopDt = statusStopDt;
	}









	/**
	 * @return the statusCancelDt
	 */
	public String getStatusCancelDt() {
		return statusCancelDt;
	}









	/**
	 * @param statusCancelDt the statusCancelDt to set
	 */
	public void setStatusCancelDt(String statusCancelDt) {
		this.statusCancelDt = statusCancelDt;
	}









	/**
	 * @return the basicRemains
	 */
	public double getBasicRemains() {
		return basicRemains;
	}









	/**
	 * @param basicRemains the basicRemains to set
	 */
	public void setBasicRemains(double basicRemains) {
		this.basicRemains = basicRemains;
	}









	/**
	 * @return the strBasicRemains
	 */
	public String getStrBasicRemains() {
		return strBasicRemains;
	}









	/**
	 * @param strBasicRemains the strBasicRemains to set
	 */
	public void setStrBasicRemains(String strBasicRemains) {
		this.strBasicRemains = strBasicRemains;
	}









	/**
	 * @return the basicExpire
	 */
	public String getBasicExpire() {
		return basicExpire;
	}









	/**
	 * @param basicExpire the basicExpire to set
	 */
	public void setBasicExpire(String basicExpire) {
		this.basicExpire = basicExpire;
	}









	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}









	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}









	/**
	 * @return the useTerm
	 */
	public int getUseTerm() {
		return useTerm;
	}









	/**
	 * @param useTerm the useTerm to set
	 */
	public void setUseTerm(int useTerm) {
		this.useTerm = useTerm;
	}









	/**
	 * @return the lastBasicChgDt
	 */
	public String getLastBasicChgDt() {
		return lastBasicChgDt;
	}









	/**
	 * @param lastBasicChgDt the lastBasicChgDt to set
	 */
	public void setLastBasicChgDt(String lastBasicChgDt) {
		this.lastBasicChgDt = lastBasicChgDt;
	}









	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}









	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}









	/**
	 * @return the agentNm
	 */
	public String getAgentNm() {
		return agentNm;
	}









	/**
	 * @param agentNm the agentNm to set
	 */
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}









	/**
	 * @return the agentSaleId
	 */
	public String getAgentSaleId() {
		return agentSaleId;
	}









	/**
	 * @param agentSaleId the agentSaleId to set
	 */
	public void setAgentSaleId(String agentSaleId) {
		this.agentSaleId = agentSaleId;
	}









	/**
	 * @return the agentSaleNm
	 */
	public String getAgentSaleNm() {
		return agentSaleNm;
	}









	/**
	 * @param agentSaleIdNm the agentSaleNm to set
	 */
	public void setAgentSaleNm(String agentSaleNm) {
		this.agentSaleNm = agentSaleNm;
	}









	/**
	 * @return the tryRcgCnt
	 */
	public int getTryRcgCnt() {
		return tryRcgCnt;
	}









	/**
	 * @param tryRcgCnt the tryRcgCnt to set
	 */
	public void setTryRcgCnt(int tryRcgCnt) {
		this.tryRcgCnt = tryRcgCnt;
	}









	/**
	 * @return the tryRcgCharge
	 */
	public int getTryRcgCharge() {
		return tryRcgCharge;
	}









	/**
	 * @param tryRcgCharge the tryRcgCharge to set
	 */
	public void setTryRcgCharge(int tryRcgCharge) {
		this.tryRcgCharge = tryRcgCharge;
	}









	/**
	 * @return the rcgEditCharge
	 */
	public int getRcgEditCharge() {
		return rcgEditCharge;
	}









	/**
	 * @param rcgEditCharge the rcgEditCharge to set
	 */
	public void setRcgEditCharge(int rcgEditCharge) {
		this.rcgEditCharge = rcgEditCharge;
	}









	/**
	 * @return the rcgFreeCharge
	 */
	public int getRcgFreeCharge() {
		return rcgFreeCharge;
	}









	/**
	 * @param rcgFreeCharge the rcgFreeCharge to set
	 */
	public void setRcgFreeCharge(int rcgFreeCharge) {
		this.rcgFreeCharge = rcgFreeCharge;
	}









	/**
	 * @return the rcgFreeCancelCharge
	 */
	public int getRcgFreeCancelCharge() {
		return rcgFreeCancelCharge;
	}









	/**
	 * @param rcgFreeCancelCharge the rcgFreeCancelCharge to set
	 */
	public void setRcgFreeCancelCharge(int rcgFreeCancelCharge) {
		this.rcgFreeCancelCharge = rcgFreeCancelCharge;
	}









	/**
	 * @return the rcgPayCancelCharge
	 */
	public int getRcgPayCancelCharge() {
		return rcgPayCancelCharge;
	}









	/**
	 * @param rcgPayCancelCharge the rcgPayCancelCharge to set
	 */
	public void setRcgPayCancelCharge(int rcgPayCancelCharge) {
		this.rcgPayCancelCharge = rcgPayCancelCharge;
	}









	/**
	 * @return the totRcgCharge
	 */
	public int getTotRcgCharge() {
		return totRcgCharge;
	}









	/**
	 * @param totRcgCharge the totRcgCharge to set
	 */
	public void setTotRcgCharge(int totRcgCharge) {
		this.totRcgCharge = totRcgCharge;
	}









	/**
	 * @return the rcgCardCharge
	 */
	public int getRcgCardCharge() {
		return rcgCardCharge;
	}









	/**
	 * @param rcgCardCharge the rcgCardCharge to set
	 */
	public void setRcgCardCharge(int rcgCardCharge) {
		this.rcgCardCharge = rcgCardCharge;
	}









	/**
	 * @return the rcgCmsCharge
	 */
	public int getRcgCmsCharge() {
		return rcgCmsCharge;
	}









	/**
	 * @param rcgCmsCharge the rcgCmsCharge to set
	 */
	public void setRcgCmsCharge(int rcgCmsCharge) {
		this.rcgCmsCharge = rcgCmsCharge;
	}









	/**
	 * @return the rcgVacCharge
	 */
	public int getRcgVacCharge() {
		return rcgVacCharge;
	}









	/**
	 * @param rcgVacCharge the rcgVacCharge to set
	 */
	public void setRcgVacCharge(int rcgVacCharge) {
		this.rcgVacCharge = rcgVacCharge;
	}









	/**
	 * @return the rcgPosCharge
	 */
	public int getRcgPosCharge() {
		return rcgPosCharge;
	}









	/**
	 * @param rcgPosCharge the rcgPosCharge to set
	 */
	public void setRcgPosCharge(int rcgPosCharge) {
		this.rcgPosCharge = rcgPosCharge;
	}









	/**
	 * @return the rcgEtcCharge
	 */
	public int getRcgEtcCharge() {
		return rcgEtcCharge;
	}









	/**
	 * @param rcgEtcCharge the rcgEtcCharge to set
	 */
	public void setRcgEtcCharge(int rcgEtcCharge) {
		this.rcgEtcCharge = rcgEtcCharge;
	}









	/**
	 * @return the useCnt
	 */
	public int getUseCnt() {
		return useCnt;
	}









	/**
	 * @param useCnt the useCnt to set
	 */
	public void setUseCnt(int useCnt) {
		this.useCnt = useCnt;
	}









	/**
	 * @return the useDur
	 */
	public double getUseDur() {
		return useDur;
	}









	/**
	 * @param useDur the useDur to set
	 */
	public void setUseDur(double useDur) {
		this.useDur = useDur;
	}









	/**
	 * @return the strUseDur
	 */
	public String getStrUseDur() {
		return strUseDur;
	}









	/**
	 * @param strUseDur the strUseDur to set
	 */
	public void setStrUseDur(String strUseDur) {
		this.strUseDur = strUseDur;
	}









	/**
	 * @return the useCharge
	 */
	public double getUseCharge() {
		return useCharge;
	}









	/**
	 * @param useCharge the useCharge to set
	 */
	public void setUseCharge(double useCharge) {
		this.useCharge = useCharge;
	}









	/**
	 * @return the strUseCharge
	 */
	public String getStrUseCharge() {
		return strUseCharge;
	}









	/**
	 * @param strUseCharge the strUseCharge to set
	 */
	public void setStrUseCharge(String strUseCharge) {
		this.strUseCharge = strUseCharge;
	}









	/**
	 * @return the usePkt
	 */
	public double getUsePkt() {
		return usePkt;
	}









	/**
	 * @param usePkt the usePkt to set
	 */
	public void setUsePkt(double usePkt) {
		this.usePkt = usePkt;
	}









	/**
	 * @return the strUsePkt
	 */
	public String getStrUsePkt() {
		return strUsePkt;
	}









	/**
	 * @param strUsePkt the strUsePkt to set
	 */
	public void setStrUsePkt(String strUsePkt) {
		this.strUsePkt = strUsePkt;
	}









	/**
	 * @return the vocNDur
	 */
	public double getVocNDur() {
		return vocNDur;
	}









	/**
	 * @param vocNDur the vocNDur to set
	 */
	public void setVocNDur(double vocNDur) {
		this.vocNDur = vocNDur;
	}









	/**
	 * @return the strVocNDur
	 */
	public String getStrVocNDur() {
		return strVocNDur;
	}









	/**
	 * @param strVocNDur the strVocNDur to set
	 */
	public void setStrVocNDur(String strVocNDur) {
		this.strVocNDur = strVocNDur;
	}









	/**
	 * @return the vocNCharge
	 */
	public double getVocNCharge() {
		return vocNCharge;
	}









	/**
	 * @param vocNCharge the vocNCharge to set
	 */
	public void setVocNCharge(double vocNCharge) {
		this.vocNCharge = vocNCharge;
	}









	/**
	 * @return the strVocNCharge
	 */
	public String getStrVocNCharge() {
		return strVocNCharge;
	}









	/**
	 * @param strVocNCharge the strVocNCharge to set
	 */
	public void setStrVocNCharge(String strVocNCharge) {
		this.strVocNCharge = strVocNCharge;
	}









	/**
	 * @return the vocIDur
	 */
	public double getVocIDur() {
		return vocIDur;
	}









	/**
	 * @param vocIDur the vocIDur to set
	 */
	public void setVocIDur(double vocIDur) {
		this.vocIDur = vocIDur;
	}









	/**
	 * @return the strVocIDur
	 */
	public String getStrVocIDur() {
		return strVocIDur;
	}









	/**
	 * @param strVocIDur the strVocIDur to set
	 */
	public void setStrVocIDur(String strVocIDur) {
		this.strVocIDur = strVocIDur;
	}









	/**
	 * @return the vocICharge
	 */
	public double getVocICharge() {
		return vocICharge;
	}









	/**
	 * @param vocICharge the vocICharge to set
	 */
	public void setVocICharge(double vocICharge) {
		this.vocICharge = vocICharge;
	}









	/**
	 * @return the strVocICharge
	 */
	public String getStrVocICharge() {
		return strVocICharge;
	}









	/**
	 * @param strVocICharge the strVocICharge to set
	 */
	public void setStrVocICharge(String strVocICharge) {
		this.strVocICharge = strVocICharge;
	}









	/**
	 * @return the vidNDur
	 */
	public double getVidNDur() {
		return vidNDur;
	}









	/**
	 * @param vidNDur the vidNDur to set
	 */
	public void setVidNDur(double vidNDur) {
		this.vidNDur = vidNDur;
	}









	/**
	 * @return the strVidNDur
	 */
	public String getStrVidNDur() {
		return strVidNDur;
	}









	/**
	 * @param strVidNDur the strVidNDur to set
	 */
	public void setStrVidNDur(String strVidNDur) {
		this.strVidNDur = strVidNDur;
	}









	/**
	 * @return the vidNCharge
	 */
	public double getVidNCharge() {
		return vidNCharge;
	}









	/**
	 * @param vidNCharge the vidNCharge to set
	 */
	public void setVidNCharge(double vidNCharge) {
		this.vidNCharge = vidNCharge;
	}









	/**
	 * @return the strVidNCharge
	 */
	public String getStrVidNCharge() {
		return strVidNCharge;
	}









	/**
	 * @param strVidNCharge the strVidNCharge to set
	 */
	public void setStrVidNCharge(String strVidNCharge) {
		this.strVidNCharge = strVidNCharge;
	}









	/**
	 * @return the vidIDur
	 */
	public double getVidIDur() {
		return vidIDur;
	}









	/**
	 * @param vidIDur the vidIDur to set
	 */
	public void setVidIDur(double vidIDur) {
		this.vidIDur = vidIDur;
	}









	/**
	 * @return the strVidIDur
	 */
	public String getStrVidIDur() {
		return strVidIDur;
	}









	/**
	 * @param strVidIDur the strVidIDur to set
	 */
	public void setStrVidIDur(String strVidIDur) {
		this.strVidIDur = strVidIDur;
	}









	/**
	 * @return the vidICharge
	 */
	public double getVidICharge() {
		return vidICharge;
	}









	/**
	 * @param vidICharge the vidICharge to set
	 */
	public void setVidICharge(double vidICharge) {
		this.vidICharge = vidICharge;
	}









	/**
	 * @return the strVidICharge
	 */
	public String getStrVidICharge() {
		return strVidICharge;
	}









	/**
	 * @param strVidICharge the strVidICharge to set
	 */
	public void setStrVidICharge(String strVidICharge) {
		this.strVidICharge = strVidICharge;
	}









	/**
	 * @return the smsNCnt
	 */
	public int getSmsNCnt() {
		return smsNCnt;
	}









	/**
	 * @param smsNCnt the smsNCnt to set
	 */
	public void setSmsNCnt(int smsNCnt) {
		this.smsNCnt = smsNCnt;
	}









	/**
	 * @return the smsNCharge
	 */
	public double getSmsNCharge() {
		return smsNCharge;
	}









	/**
	 * @param smsNCharge the smsNCharge to set
	 */
	public void setSmsNCharge(double smsNCharge) {
		this.smsNCharge = smsNCharge;
	}









	/**
	 * @return the strSmsNCharge
	 */
	public String getStrSmsNCharge() {
		return strSmsNCharge;
	}









	/**
	 * @param strSmsNCharge the strSmsNCharge to set
	 */
	public void setStrSmsNCharge(String strSmsNCharge) {
		this.strSmsNCharge = strSmsNCharge;
	}









	/**
	 * @return the smsICnt
	 */
	public int getSmsICnt() {
		return smsICnt;
	}









	/**
	 * @param smsICnt the smsICnt to set
	 */
	public void setSmsICnt(int smsICnt) {
		this.smsICnt = smsICnt;
	}









	/**
	 * @return the smsICharge
	 */
	public double getSmsICharge() {
		return smsICharge;
	}









	/**
	 * @param smsICharge the smsICharge to set
	 */
	public void setSmsICharge(double smsICharge) {
		this.smsICharge = smsICharge;
	}









	/**
	 * @return the strSmsICharge
	 */
	public String getStrSmsICharge() {
		return strSmsICharge;
	}









	/**
	 * @param strSmsICharge the strSmsICharge to set
	 */
	public void setStrSmsICharge(String strSmsICharge) {
		this.strSmsICharge = strSmsICharge;
	}









	/**
	 * @return the pktNDur
	 */
	public double getPktNDur() {
		return pktNDur;
	}









	/**
	 * @param pktNDur the pktNDur to set
	 */
	public void setPktNDur(double pktNDur) {
		this.pktNDur = pktNDur;
	}









	/**
	 * @return the strPktNDur
	 */
	public String getStrPktNDur() {
		return strPktNDur;
	}









	/**
	 * @param strPktNDur the strPktNDur to set
	 */
	public void setStrPktNDur(String strPktNDur) {
		this.strPktNDur = strPktNDur;
	}









	/**
	 * @return the pktNCharge
	 */
	public double getPktNCharge() {
		return pktNCharge;
	}









	/**
	 * @param pktNCharge the pktNCharge to set
	 */
	public void setPktNCharge(double pktNCharge) {
		this.pktNCharge = pktNCharge;
	}









	/**
	 * @return the strPktNCharge
	 */
	public String getStrPktNCharge() {
		return strPktNCharge;
	}









	/**
	 * @param strPktNCharge the strPktNCharge to set
	 */
	public void setStrPktNCharge(String strPktNCharge) {
		this.strPktNCharge = strPktNCharge;
	}









	/**
	 * @return the pktIDur
	 */
	public double getPktIDur() {
		return pktIDur;
	}









	/**
	 * @param pktIDur the pktIDur to set
	 */
	public void setPktIDur(double pktIDur) {
		this.pktIDur = pktIDur;
	}









	/**
	 * @return the strPktIDur
	 */
	public String getStrPktIDur() {
		return strPktIDur;
	}









	/**
	 * @param strPktIDur the strPktIDur to set
	 */
	public void setStrPktIDur(String strPktIDur) {
		this.strPktIDur = strPktIDur;
	}









	/**
	 * @return the pktICharge
	 */
	public double getPktICharge() {
		return pktICharge;
	}









	/**
	 * @param pktICharge the pktICharge to set
	 */
	public void setPktICharge(double pktICharge) {
		this.pktICharge = pktICharge;
	}









	/**
	 * @return the strPktICharge
	 */
	public String getStrPktICharge() {
		return strPktICharge;
	}









	/**
	 * @param strPktICharge the strPktICharge to set
	 */
	public void setStrPktICharge(String strPktICharge) {
		this.strPktICharge = strPktICharge;
	}









	/**
	 * @return the dayCnt
	 */
	public int getDayCnt() {
		return dayCnt;
	}









	/**
	 * @param dayCnt the dayCnt to set
	 */
	public void setDayCnt(int dayCnt) {
		this.dayCnt = dayCnt;
	}









	/**
	 * @return the dayCharge
	 */
	public double getDayCharge() {
		return dayCharge;
	}









	/**
	 * @param dayCharge the dayCharge to set
	 */
	public void setDayCharge(double dayCharge) {
		this.dayCharge = dayCharge;
	}









	/**
	 * @return the strDayCharge
	 */
	public String getStrDayCharge() {
		return strDayCharge;
	}









	/**
	 * @param strDayCharge the strDayCharge to set
	 */
	public void setStrDayCharge(String strDayCharge) {
		this.strDayCharge = strDayCharge;
	}









	/**
	 * @return the etcCnt
	 */
	public int getEtcCnt() {
		return etcCnt;
	}









	/**
	 * @param etcCnt the etcCnt to set
	 */
	public void setEtcCnt(int etcCnt) {
		this.etcCnt = etcCnt;
	}









	/**
	 * @return the etcDur
	 */
	public double getEtcDur() {
		return etcDur;
	}









	/**
	 * @param etcDur the etcDur to set
	 */
	public void setEtcDur(double etcDur) {
		this.etcDur = etcDur;
	}









	/**
	 * @return the strEtcDur
	 */
	public String getStrEtcDur() {
		return strEtcDur;
	}









	/**
	 * @param strEtcDur the strEtcDur to set
	 */
	public void setStrEtcDur(String strEtcDur) {
		this.strEtcDur = strEtcDur;
	}









	/**
	 * @return the etcPkt
	 */
	public double getEtcPkt() {
		return etcPkt;
	}









	/**
	 * @param etcPkt the etcPkt to set
	 */
	public void setEtcPkt(double etcPkt) {
		this.etcPkt = etcPkt;
	}









	/**
	 * @return the strEtcPkt
	 */
	public String getStrEtcPkt() {
		return strEtcPkt;
	}









	/**
	 * @param strEtcPkt the strEtcPkt to set
	 */
	public void setStrEtcPkt(String strEtcPkt) {
		this.strEtcPkt = strEtcPkt;
	}









	/**
	 * @return the etcCharge
	 */
	public double getEtcCharge() {
		return etcCharge;
	}









	/**
	 * @param etcCharge the etcCharge to set
	 */
	public void setEtcCharge(double etcCharge) {
		this.etcCharge = etcCharge;
	}









	/**
	 * @return the strEtcCharge
	 */
	public String getStrEtcCharge() {
		return strEtcCharge;
	}









	/**
	 * @param strEtcCharge the strEtcCharge to set
	 */
	public void setStrEtcCharge(String strEtcCharge) {
		this.strEtcCharge = strEtcCharge;
	}









	/**
	 * @return the recordDate
	 */
	public String getRecordDate() {
		return recordDate;
	}









	/**
	 * @param recordDate the recordDate to set
	 */
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}









	/**
	 * @return the totPktCharge
	 */
	public double getTotPktCharge() {
		return totPktCharge;
	}









	/**
	 * @param totPktCharge the totPktCharge to set
	 */
	public void setTotPktCharge(double totPktCharge) {
		this.totPktCharge = totPktCharge;
	}









	/**
	 * @return the strTotPktCharge
	 */
	public String getStrTotPktCharge() {
		return strTotPktCharge;
	}









	/**
	 * @param strTotPktCharge the strTotPktCharge to set
	 */
	public void setStrTotPktCharge(String strTotPktCharge) {
		this.strTotPktCharge = strTotPktCharge;
	}









	/**
	 * @return the totVocCharge
	 */
	public double getTotVocCharge() {
		return totVocCharge;
	}









	/**
	 * @param totVocCharge the totVocCharge to set
	 */
	public void setTotVocCharge(double totVocCharge) {
		this.totVocCharge = totVocCharge;
	}









	/**
	 * @return the strTotVocCharge
	 */
	public String getStrTotVocCharge() {
		return strTotVocCharge;
	}









	/**
	 * @param strTotVocCharge the strTotVocCharge to set
	 */
	public void setStrTotVocCharge(String strTotVocCharge) {
		this.strTotVocCharge = strTotVocCharge;
	}









	/**
	 * @return the totVocDur
	 */
	public double getTotVocDur() {
		return totVocDur;
	}









	/**
	 * @param totVocDur the totVocDur to set
	 */
	public void setTotVocDur(double totVocDur) {
		this.totVocDur = totVocDur;
	}









	/**
	 * @return the strTotVocDur
	 */
	public String getStrTotVocDur() {
		return strTotVocDur;
	}









	/**
	 * @param strTotVocDur the strTotVocDur to set
	 */
	public void setStrTotVocDur(String strTotVocDur) {
		this.strTotVocDur = strTotVocDur;
	}









	/**
	 * @return the totVidCharge
	 */
	public double getTotVidCharge() {
		return totVidCharge;
	}









	/**
	 * @param totVidCharge the totVidCharge to set
	 */
	public void setTotVidCharge(double totVidCharge) {
		this.totVidCharge = totVidCharge;
	}









	/**
	 * @return the strTotVidCharge
	 */
	public String getStrTotVidCharge() {
		return strTotVidCharge;
	}









	/**
	 * @param strTotVidCharge the strTotVidCharge to set
	 */
	public void setStrTotVidCharge(String strTotVidCharge) {
		this.strTotVidCharge = strTotVidCharge;
	}









	/**
	 * @return the totVidDur
	 */
	public double getTotVidDur() {
		return totVidDur;
	}









	/**
	 * @param totVidDur the totVidDur to set
	 */
	public void setTotVidDur(double totVidDur) {
		this.totVidDur = totVidDur;
	}









	/**
	 * @return the strTotVidDur
	 */
	public String getStrTotVidDur() {
		return strTotVidDur;
	}









	/**
	 * @param strTotVidDur the strTotVidDur to set
	 */
	public void setStrTotVidDur(String strTotVidDur) {
		this.strTotVidDur = strTotVidDur;
	}


	/**
	 * @return the contractNumStr
	 */
	public String getContractNumStr() {
		return contractNumStr;
	}









	/**
	 * @param contractNumStr the contractNumStr to set
	 */
	public void setContractNumStr(String contractNumStr) {
		this.contractNumStr = contractNumStr;
	}







	/**
	 * @return the rcgPayCharge
	 */
	public int getRcgPayCharge() {
		return rcgPayCharge;
	}









	/**
	 * @param rcgPayCharge the rcgPayCharge to set
	 */
	public void setRcgPayCharge(int rcgPayCharge) {
		this.rcgPayCharge = rcgPayCharge;
	}









	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}


}
