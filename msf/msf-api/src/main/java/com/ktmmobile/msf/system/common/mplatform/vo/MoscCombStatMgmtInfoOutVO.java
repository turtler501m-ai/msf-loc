package com.ktmmobile.msf.system.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.msf.system.common.util.StringMakerUtil;
import com.ktmmobile.msf.system.common.util.XmlParse;

public class MoscCombStatMgmtInfoOutVO extends com.ktmmobile.msf.system.common.mplatform.vo.CommonXmlVO {

	@Deprecated
	private static Logger logger = LoggerFactory.getLogger(MoscCombStatMgmtInfoOutVO.class);

	private OutWireDto outWireDtoVo;
	private List<OutGiveListDto> outGiveDtoList;
	private List<OutRcvListDto> outRcvDtoList;

	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {

		// 공통
		Element outWireDto = XmlParse.getChildElement(this.body, "outWireDto");
		outWireDtoVo = new MoscCombStatMgmtInfoOutVO.OutWireDto();
		if( outWireDto !=null ){
			outWireDtoVo.setSbscPsblTotCnt(XmlParse.getChildValue(outWireDto, "sbscPsblTotCnt"));
			outWireDtoVo.setSbscBindNowCnt(XmlParse.getChildValue(outWireDto, "sbscBindNowCnt"));
			outWireDtoVo.setSbscBindRemdCnt(XmlParse.getChildValue(outWireDto, "sbscBindRemdCnt"));
		}

		// 주기회선으로 조회시 output
		List<Element> outGiveListDto = XmlParse.getChildElementList(this.body, "outGiveListDto");
		outGiveDtoList = new ArrayList<MoscCombStatMgmtInfoOutVO.OutGiveListDto>();
		for(Element item : outGiveListDto){
			OutGiveListDto  outGiveDto = new OutGiveListDto();
			String custNm = XmlParse.getChildValue(item,"custNm");
			String rcvSvcNo = XmlParse.getChildValue(item,"rcvSvcNo");
			String tgtCustNm = XmlParse.getChildValue(item,"tgtCustNm");
			String rcvProdNm = XmlParse.getChildValue(item,"rcvProdNm");
			String efctStDt = XmlParse.getChildValue(item,"efctStDt");
			String efctFnsDt = XmlParse.getChildValue(item,"efctFnsDt");
			String bindStatus = XmlParse.getChildValue(item,"bindStatus");

			// 회선번호 마스킹 적용 2022.10.05
			rcvSvcNo= StringMakerUtil.getPhoneNum(rcvSvcNo);
			
			outGiveDto.setCustNm(custNm);
			outGiveDto.setRcvSvcNo(rcvSvcNo);
			outGiveDto.setTgtCustNm(tgtCustNm);
			outGiveDto.setRcvProdNm(rcvProdNm);
			outGiveDto.setEfctStDt(efctStDt);
			outGiveDto.setEfctFnsDt(efctFnsDt);
			outGiveDto.setBindStatus(bindStatus);
			outGiveDtoList.add(outGiveDto);
		}

		// 받기회선으로 조회시 output
		List<Element> outRcvListDto = XmlParse.getChildElementList(this.body, "outRcvListDto");
		outRcvDtoList = new ArrayList<MoscCombStatMgmtInfoOutVO.OutRcvListDto>();
		for(Element item : outRcvListDto){
			OutRcvListDto  outRcvDto = new OutRcvListDto();
			String giveCustNm = XmlParse.getChildValue(item,"giveCustNm");
			String giveProdNm = XmlParse.getChildValue(item,"giveProdNm");
			String giveSvcNo = XmlParse.getChildValue(item,"giveSvcNo");
			String rcvCustNm = XmlParse.getChildValue(item,"rcvCustNm");
			String rcvProdNm = XmlParse.getChildValue(item,"rcvProdNm");
			String rcvSvcNo = XmlParse.getChildValue(item,"rcvSvcNo");
			
			// 회선번호 마스킹 적용 2022.10.05
			rcvSvcNo= StringMakerUtil.getPhoneNum(rcvSvcNo);
			giveSvcNo= StringMakerUtil.getPhoneNum(giveSvcNo);
			
			String efctStDt = XmlParse.getChildValue(item,"efctStDt");
			String efctFnsDt = XmlParse.getChildValue(item,"efctFnsDt");
			String bindStatus = XmlParse.getChildValue(item,"bindStatus");
			outRcvDto.setGiveCustNm(giveCustNm);
			outRcvDto.setGiveProdNm(giveProdNm);
			outRcvDto.setGiveSvcNo(giveSvcNo);
			outRcvDto.setRcvCustNm(rcvCustNm);
			outRcvDto.setRcvProdNm(rcvProdNm);
			outRcvDto.setRcvSvcNo(rcvSvcNo);
			outRcvDto.setEfctStDt(efctStDt);
			outRcvDto.setEfctFnsDt(efctFnsDt);
			outRcvDto.setBindStatus(bindStatus);
			outRcvDtoList.add(outRcvDto);
		}

	}

	public OutWireDto getOutWireDtoVo() {
		return outWireDtoVo;
	}

	public void setOutWireDtoVo(OutWireDto outWireDtoVo) {
		this.outWireDtoVo = outWireDtoVo;
	}

	public List<OutRcvListDto> getOutRcvDtoList() {
		return outRcvDtoList;
	}


	public void setOutRcvDtoList(List<OutRcvListDto> outRcvDtoList) {
		this.outRcvDtoList = outRcvDtoList;
	}


	public List<OutGiveListDto> getOutGiveDtoList() {
		return outGiveDtoList;
	}


	public void setOutGiveDtoList(List<OutGiveListDto> outGiveDtoList) {
		this.outGiveDtoList = outGiveDtoList;
	}

	// 공통
	public static class OutWireDto{
		private String sbscPsblTotCnt; // 결합가능한 총회선수 받기회선 조회시 0 출력
		private String sbscBindNowCnt; // 현재결합된 회선수 받기회선 조회시 0 출력
		private String sbscBindRemdCnt; // 가입가능한 회선수 받기회선 조회시 0 출력

		public String getSbscPsblTotCnt() {
			return sbscPsblTotCnt;
		}
		public void setSbscPsblTotCnt(String sbscPsblTotCnt) {
			this.sbscPsblTotCnt = sbscPsblTotCnt;
		}
		public String getSbscBindNowCnt() {
			return sbscBindNowCnt;
		}
		public void setSbscBindNowCnt(String sbscBindNowCnt) {
			this.sbscBindNowCnt = sbscBindNowCnt;
		}
		public String getSbscBindRemdCnt() {
			return sbscBindRemdCnt;
		}
		public void setSbscBindRemdCnt(String sbscBindRemdCnt) {
			this.sbscBindRemdCnt = sbscBindRemdCnt;
		}
	}

	// 받기회선 조회
	public static class OutGiveListDto {
		private String custNm; // 고객명
		private String rcvSvcNo; // 받기요금제번호(결합회선)
		private String tgtCustNm; // 받기회선 고객명
		private String rcvProdNm; // 받기회선 요금제명
		private String efctStDt; // 	결합일시
		private String efctFnsDt; // 해지일시
		private String bindStatus; // 결합상태

		public String getCustNm() {
			return custNm;
		}
		public void setCustNm(String custNm) {
			this.custNm = custNm;
		}
		public String getRcvSvcNo() {
			return rcvSvcNo;
		}
		public void setRcvSvcNo(String rcvSvcNo) {
			this.rcvSvcNo = rcvSvcNo;
		}
		public String getTgtCustNm() {
			return tgtCustNm;
		}
		public void setTgtCustNm(String tgtCustNm) {
			this.tgtCustNm = tgtCustNm;
		}
		public String getRcvProdNm() {
			return rcvProdNm;
		}
		public void setRcvProdNm(String rcvProdNm) {
			this.rcvProdNm = rcvProdNm;
		}
		public String getEfctStDt() {
			return efctStDt;
		}
		public void setEfctStDt(String efctStDt) {
			this.efctStDt = efctStDt;
		}
		public String getEfctFnsDt() {
			return efctFnsDt;
		}
		public void setEfctFnsDt(String efctFnsDt) {
			this.efctFnsDt = efctFnsDt;
		}
		public String getBindStatus() {
			return bindStatus;
		}
		public void setBindStatus(String bindStatus) {
			this.bindStatus = bindStatus;
		}
    }

	// 주기회선 조회
	public static class OutRcvListDto {
		private String giveCustNm; // 주기회선 고객명
		private String giveProdNm; // 주기회선 요금제명
		private String giveSvcNo; // 주기회선 번호
		private String rcvCustNm; // 받기회선 고객명
		private String rcvProdNm; // 	받기회선 요금제명
		private String rcvSvcNo; // 받기회선 번호
		private String efctStDt; // 결합일시
		private String efctFnsDt; // 해지일시
		private String bindStatus; // 결합상태

		public String getGiveCustNm() {
			return giveCustNm;
		}
		public void setGiveCustNm(String giveCustNm) {
			this.giveCustNm = giveCustNm;
		}
		public String getGiveProdNm() {
			return giveProdNm;
		}
		public void setGiveProdNm(String giveProdNm) {
			this.giveProdNm = giveProdNm;
		}
		public String getGiveSvcNo() {
			return giveSvcNo;
		}
		public void setGiveSvcNo(String giveSvcNo) {
			this.giveSvcNo = giveSvcNo;
		}
		public String getRcvCustNm() {
			return rcvCustNm;
		}
		public void setRcvCustNm(String rcvCustNm) {
			this.rcvCustNm = rcvCustNm;
		}
		public String getRcvProdNm() {
			return rcvProdNm;
		}
		public void setRcvProdNm(String rcvProdNm) {
			this.rcvProdNm = rcvProdNm;
		}
		public String getRcvSvcNo() {
			return rcvSvcNo;
		}
		public void setRcvSvcNo(String rcvSvcNo) {
			this.rcvSvcNo = rcvSvcNo;
		}
		public String getEfctStDt() {
			return efctStDt;
		}
		public void setEfctStDt(String efctStDt) {
			this.efctStDt = efctStDt;
		}
		public String getEfctFnsDt() {
			return efctFnsDt;
		}
		public void setEfctFnsDt(String efctFnsDt) {
			this.efctFnsDt = efctFnsDt;
		}
		public String getBindStatus() {
			return bindStatus;
		}
		public void setBindStatus(String bindStatus) {
			this.bindStatus = bindStatus;
		}
	}

}
