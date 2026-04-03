package com.ktmmobile.msf.system.common.mplatform.vo;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.system.common.util.XmlParse;

public class MoscRetvIntmMdlSpecInfoVO extends com.ktmmobile.msf.system.common.mplatform.vo.CommonXmlNoSelfServiceException {

	private List<SpecSbstDto> specSbstList;

	private String intmMdlId; // 단말기모델아이디
	private String clrCd; // 색상코드
	private String clrGrpCd; // 색상그룹코드
	private String cmncTypeCd; // 통신유형코드
	private String frgnHndsetIndCd; // 외국단말기구분코드
	private String intmAsgnNotTrgtYn; // 기기할당비대상여부
	private String intmKindCd; // 기기종류코드
	private String intmMdlCmnId; // 기기모델공통 ID
	private String intmMdlLvlCd; // 기기모델레벨코드
	private String intmMdlMkngStrtYy; // 기기모델제조시작년도
	private String intmMdlNm; // 기기모델명
	private String intmWght; // 기기중량
	private String mkngVndrId; // 제조업체 ID
	private String mncCd; // MNC코드
	private String moveCmncGnrtIndCd; // 이동통신세대구분코드
	private String moveTlcmIndCd; // 이동텔레콤구분코드
	private String ordRstrYn; // 주문제한여부
	private String prprgInvtGradCd; // 부진제고등급코드
	private String rfrnSbst; // 참고내용
	private String rmngSvcIndCd; // 로밍서비스구분코드
	private String stndClrGrpCd; // 표준색상그룹코드

	@Override
	public void parse()  {

		this.intmMdlId = XmlParse.getChildValue(this.body, "intmMdlId");
		this.clrCd = XmlParse.getChildValue(this.body, "clrCd");
		this.clrGrpCd = XmlParse.getChildValue(this.body, "clrGrpCd");
		this.cmncTypeCd = XmlParse.getChildValue(this.body, "cmncTypeCd");
		this.frgnHndsetIndCd = XmlParse.getChildValue(this.body, "frgnHndsetIndCd");
		this.intmAsgnNotTrgtYn = XmlParse.getChildValue(this.body, "intmAsgnNotTrgtYn");
		this.intmKindCd = XmlParse.getChildValue(this.body, "intmKindCd");
		this.intmMdlCmnId = XmlParse.getChildValue(this.body, "intmMdlCmnId");
		this.intmMdlLvlCd = XmlParse.getChildValue(this.body, "intmMdlLvlCd");
		this.intmMdlMkngStrtYy = XmlParse.getChildValue(this.body, "intmMdlMkngStrtYy");
		this.intmMdlNm = XmlParse.getChildValue(this.body, "intmMdlNm");
		this.intmWght = XmlParse.getChildValue(this.body, "intmWght");
		this.mkngVndrId = XmlParse.getChildValue(this.body, "mkngVndrId");
		this.mncCd = XmlParse.getChildValue(this.body, "mncCd");
		this.moveCmncGnrtIndCd = XmlParse.getChildValue(this.body, "moveCmncGnrtIndCd");
		this.moveTlcmIndCd = XmlParse.getChildValue(this.body, "moveTlcmIndCd");
		this.ordRstrYn = XmlParse.getChildValue(this.body, "ordRstrYn");
		this.prprgInvtGradCd = XmlParse.getChildValue(this.body, "prprgInvtGradCd");
		this.rfrnSbst = XmlParse.getChildValue(this.body, "rfrnSbst");
		this.rmngSvcIndCd = XmlParse.getChildValue(this.body, "rmngSvcIndCd");
		this.stndClrGrpCd = XmlParse.getChildValue(this.body, "stndClrGrpCd");

		List<Element> specList = XmlParse.getChildElementList(this.body, "specSbstList");
		if(specList !=null && specList.size() > 0) {
			specSbstList = new ArrayList<MoscRetvIntmMdlSpecInfoVO.SpecSbstDto>();
			for(Element ele : specList){
				SpecSbstDto vo = new SpecSbstDto();
				vo.setIntmSpecSbst(XmlParse.getChildValue(ele, "intmSpecSbst"));
				vo.setIntmSpecTypeCd(XmlParse.getChildValue(ele, "intmSpecTypeCd"));
				specSbstList.add(vo);
			}
		}
	}

	public String getIntmMdlId() {
		return intmMdlId;
	}

	public void setIntmMdlId(String intmMdlId) {
		this.intmMdlId = intmMdlId;
	}

	public String getClrCd() {
		return clrCd;
	}

	public void setClrCd(String clrCd) {
		this.clrCd = clrCd;
	}

	public String getClrGrpCd() {
		return clrGrpCd;
	}

	public void setClrGrpCd(String clrGrpCd) {
		this.clrGrpCd = clrGrpCd;
	}

	public String getCmncTypeCd() {
		return cmncTypeCd;
	}

	public void setCmncTypeCd(String cmncTypeCd) {
		this.cmncTypeCd = cmncTypeCd;
	}

	public String getFrgnHndsetIndCd() {
		return frgnHndsetIndCd;
	}

	public void setFrgnHndsetIndCd(String frgnHndsetIndCd) {
		this.frgnHndsetIndCd = frgnHndsetIndCd;
	}

	public String getIntmAsgnNotTrgtYn() {
		return intmAsgnNotTrgtYn;
	}

	public void setIntmAsgnNotTrgtYn(String intmAsgnNotTrgtYn) {
		this.intmAsgnNotTrgtYn = intmAsgnNotTrgtYn;
	}

	public String getIntmKindCd() {
		return intmKindCd;
	}

	public void setIntmKindCd(String intmKindCd) {
		this.intmKindCd = intmKindCd;
	}

	public String getIntmMdlCmnId() {
		return intmMdlCmnId;
	}

	public void setIntmMdlCmnId(String intmMdlCmnId) {
		this.intmMdlCmnId = intmMdlCmnId;
	}

	public String getIntmMdlLvlCd() {
		return intmMdlLvlCd;
	}

	public void setIntmMdlLvlCd(String intmMdlLvlCd) {
		this.intmMdlLvlCd = intmMdlLvlCd;
	}

	public String getIntmMdlMkngStrtYy() {
		return intmMdlMkngStrtYy;
	}

	public void setIntmMdlMkngStrtYy(String intmMdlMkngStrtYy) {
		this.intmMdlMkngStrtYy = intmMdlMkngStrtYy;
	}

	public String getIntmMdlNm() {
		return intmMdlNm;
	}

	public void setIntmMdlNm(String intmMdlNm) {
		this.intmMdlNm = intmMdlNm;
	}

	public String getIntmWght() {
		return intmWght;
	}

	public void setIntmWght(String intmWght) {
		this.intmWght = intmWght;
	}

	public String getMkngVndrId() {
		return mkngVndrId;
	}

	public void setMkngVndrId(String mkngVndrId) {
		this.mkngVndrId = mkngVndrId;
	}

	public String getMncCd() {
		return mncCd;
	}

	public void setMncCd(String mncCd) {
		this.mncCd = mncCd;
	}

	public String getMoveCmncGnrtIndCd() {
		return moveCmncGnrtIndCd;
	}

	public void setMoveCmncGnrtIndCd(String moveCmncGnrtIndCd) {
		this.moveCmncGnrtIndCd = moveCmncGnrtIndCd;
	}

	public String getMoveTlcmIndCd() {
		return moveTlcmIndCd;
	}

	public void setMoveTlcmIndCd(String moveTlcmIndCd) {
		this.moveTlcmIndCd = moveTlcmIndCd;
	}

	public String getOrdRstrYn() {
		return ordRstrYn;
	}

	public void setOrdRstrYn(String ordRstrYn) {
		this.ordRstrYn = ordRstrYn;
	}

	public String getPrprgInvtGradCd() {
		return prprgInvtGradCd;
	}

	public void setPrprgInvtGradCd(String prprgInvtGradCd) {
		this.prprgInvtGradCd = prprgInvtGradCd;
	}

	public String getRfrnSbst() {
		return rfrnSbst;
	}

	public void setRfrnSbst(String rfrnSbst) {
		this.rfrnSbst = rfrnSbst;
	}

	public String getRmngSvcIndCd() {
		return rmngSvcIndCd;
	}

	public void setRmngSvcIndCd(String rmngSvcIndCd) {
		this.rmngSvcIndCd = rmngSvcIndCd;
	}

	public String getStndClrGrpCd() {
		return stndClrGrpCd;
	}

	public void setStndClrGrpCd(String stndClrGrpCd) {
		this.stndClrGrpCd = stndClrGrpCd;
	}




	public List<SpecSbstDto> getSpecSbstList() {
		return specSbstList;
	}

	public void setSpecSbstList(List<SpecSbstDto> specSbstList) {
		this.specSbstList = specSbstList;
	}




	public class SpecSbstDto {

        private String intmSpecSbst;
        private String intmSpecTypeCd;

		public String getIntmSpecSbst() {
			return intmSpecSbst;
		}
		public void setIntmSpecSbst(String intmSpecSbst) {
			this.intmSpecSbst = intmSpecSbst;
		}
		public String getIntmSpecTypeCd() {
			return intmSpecTypeCd;
		}
		public void setIntmSpecTypeCd(String intmSpecTypeCd) {
			this.intmSpecTypeCd = intmSpecTypeCd;
		}
    }




}
