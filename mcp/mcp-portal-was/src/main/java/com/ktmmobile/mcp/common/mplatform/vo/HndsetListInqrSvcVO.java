package com.ktmmobile.mcp.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.mcp.common.util.XmlParse;

public class HndsetListInqrSvcVO extends com.ktmmobile.mcp.common.mplatform.vo.CommonXmlVO{

	private List<HndsetListInqrSvc> hndsetListInqrSvcList;

	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {

		List<Element> hndsetListInqrSvc = XmlParse.getChildElementList(this.body, "outDto");
		hndsetListInqrSvcList = new ArrayList<HndsetListInqrSvcVO.HndsetListInqrSvc>();

		for(Element item : hndsetListInqrSvc){
			HndsetListInqrSvc  hndsetListInqrSvcDto = new HndsetListInqrSvc();
			String intmMdlId = XmlParse.getChildValue(item,"intmMdlId"); // 단말기 아이디
			String intmMdlNm = XmlParse.getChildValue(item,"intmMdlNm"); // 단말기 명
			String intmAliasNm = XmlParse.getChildValue(item,"intmAliasNm"); // 팻네임
			String mvnoTlcmIndCd = XmlParse.getChildValue(item,"mvnoTlcmIndCd"); // 이통사 코드 SKT:S ,LG:L , 기타:O

			hndsetListInqrSvcDto.setIntmMdlId(intmMdlId);
			hndsetListInqrSvcDto.setIntmMdlNm(intmMdlNm);
			hndsetListInqrSvcDto.setIntmAliasNm(intmAliasNm);
			hndsetListInqrSvcDto.setMvnoTlcmIndCd(mvnoTlcmIndCd);
			hndsetListInqrSvcList.add(hndsetListInqrSvcDto);
		}
	}


	public static class HndsetListInqrSvc {

		private String intmMdlId;
		private String intmMdlNm;
		private String intmAliasNm;
		private String mvnoTlcmIndCd;
		public String getIntmMdlId() {
			return intmMdlId;
		}
		public void setIntmMdlId(String intmMdlId) {
			this.intmMdlId = intmMdlId;
		}
		public String getIntmMdlNm() {
			return intmMdlNm;
		}
		public void setIntmMdlNm(String intmMdlNm) {
			this.intmMdlNm = intmMdlNm;
		}
		public String getIntmAliasNm() {
			return intmAliasNm;
		}
		public void setIntmAliasNm(String intmAliasNm) {
			this.intmAliasNm = intmAliasNm;
		}
		public String getMvnoTlcmIndCd() {
			return mvnoTlcmIndCd;
		}
		public void setMvnoTlcmIndCd(String mvnoTlcmIndCd) {
			this.mvnoTlcmIndCd = mvnoTlcmIndCd;
		}
	}

	public List<HndsetListInqrSvc> getHndsetListInqrSvcList() {
		return hndsetListInqrSvcList;
	}

	public void setHndsetListInqrSvcList(List<HndsetListInqrSvc> hndsetListInqrSvcList) {
		this.hndsetListInqrSvcList = hndsetListInqrSvcList;
	}
}
