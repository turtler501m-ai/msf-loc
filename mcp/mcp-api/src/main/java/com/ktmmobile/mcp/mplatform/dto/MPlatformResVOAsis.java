package com.ktmmobile.mcp.mplatform.dto;

import java.io.Serializable;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.cmmn.util.EncryptUtil;

public class MPlatformResVOAsis implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String mvnoOrdNo;						/* MVNO오더번호(RES_NO) */
	private String slsCmpnCd;						/* 사업자코드(KIS) */
	private String custTypeCd;						/* 고객유형코드 */
	private String custIdntNoIndCd;					/* 고객식별번호구분코드 */
	private String custIdntNo;						/* 고객식별번호 */
	private String crprNo;							/* 법인번호 */
	private String custNm;							/* 고객명 */
	private String cntrUseCd;						/* 계약용도코드(R:일반, I:선불)*/
	private String instYn;							/* 할부여부 */
	private String scnhndPhonInstYn;				/* 중고폰할부여부 */
	private String myslAgreYn;						/* 본인동의여부 */
	private String nativeRlnamAthnEvdnPprCd;		/* 내국인실명인증증빙서류코드 */
	private String athnRqstcustCntplcNo;			/* 인증요청고객연락번호 */
	private String rsdcrtIssuDate;					/* 주민증발급일자 */
	private String lcnsNo;							/* 운전면허번호 */
	private String lcnsRgnCd;						/* 운전면허지역코드 */
	private String mrtrPrsnNo;						/* 유공자번호 */
	private String nationalityCd;					/* 국적코드(외국인) */
	private String fornBrthDate;					/* 외국인생년월일 */
	private String crdtInfoAgreYn = "Y";			/* 신용정보동의(Y) */
	private String indvInfoInerPrcuseAgreYn = "Y";	/* 개인정보내부활용동의(Y) */
	private String cnsgInfoAdvrRcvAgreYn;			/* 위탁정보광고수신동의 */
	private String othcmpInfoAdvrRcvAgreYn = "N";	/* 타사정보광고수신동의 */
	private String othcmpInfoAdvrCnsgAgreYn = "N";	/* 타사정보광고위탁동의 */
	private String grpAgntBindSvcSbscAgreYn = "N";	/* 그룹사결합서비스가입동의 */
	private String cardInsrPrdcAgreYn = "N";		/* 카드보험상품동의 */
	private String olhRailCprtAgreYn = "N";			/* 올레철도제휴동의 */
	private String olhShckWibroRlfAgreYn = "N";		/* 올레쇼킹와이브로안심동의 */
	private String olngDscnHynmtrAgreYn = "N";		/* 주유할인현대차동의 */
	private String olhCprtPntAgreYn = "N";			/* 올레제휴포인트동의 */
	private String dwoCprtPntAgreYn = "N";			/* 대우제휴포인트동의 */
	private String wlfrDscnAplyAgreYn = "N";		/* 복지할인신청동의 */
	private String spamPrvdAgreYn = "N";			/* 스팸제공동의 */
	private String prttlpStlmUseAgreYn = "N";		/* 이동전화결제이용동의 */
	private String prttlpStlmPwdUseAgreYn = "N";	/* 이동전화결제비밀번호이용동의 */
	private String wrlnTlphNo;						/* 유선전화번호 */
	private String tlphNo;							/* 이동전화번호 */
	private String rprsPrsnNm;						/* 대표자 */
	private String upjnCd;							/* 업종코드 */
	private String bcuSbst;							/* 업태코드 */
	private String zipNo;							/* 우편번호 */
	private String fndtCntplcSbst;					/* 기본주소 */
	private String mntCntplcSbst;					/* 상세주소 */
	private String brthDate;						/* 생년월일 */
	private String brthNnpIndCd;					/* 음력양력구분 */
	private String jobCd;							/* 직업코드 */
	private String emlAdrsNm;						/* 이메일주소 */
	private String lstdIndCd;						/* 상장구분코드 */
	private String emplCnt;							/* 사원수 */
	private String slngAmt;							/* 매출액 */
	private String cptlAmnt;						/* 자본금액 */
	private String crprUpjnCd;						/* 법인업종코드 */
	private String crprBcuSbst;						/* 법인업태내용 */
	private String crprZipNo;						/* 법인우편번호 */
	private String crprFndtCntplcSbst;				/* 법인기본주소 */
	private String crprMntCntplcSbst;				/* 법인상세주소 */
	private String custInfoChngYn;					/* 고객정보변경여부 */
	private String m2mHndsetYn;						/* M2M단말여부 */
	private String orderTypeCd;						/* 오더유형 NAC:신규, MNP:번이 */
	private String npTlphNo;						/* 번이전화번호 */
	private String indvBizrYn;						/* 개인사업자여부 */
	private String bchngNpCommCmpnCd;				/* 변경전번이사업자코드 */
	private String athnItemCd;						/* 인증항목코드 */
	private String athnSbst;						/* 인증항목값 */
	private String npRstrtnContYn;					/* 번호이동제한예외여부 */
	private String ytrpaySoffAgreYn;				/* 해지미환급금상계동의 */
	private String osstOrdNo;						/* OSST오더번호 */
	private String asgnAgncId;						/* 할당대리점ID */
	private String asgnAgncYn;						/* 할당번호조회여부 */
	private String cntryCd = "KOR";					/* 국가코드(KOR) */
	private String custNo;							/* 고객번호 */
	private String inqrBase = "1";					/* 조회페이지 */
	private String inqrCascnt = "10";				/* 조회카운트 */
	private String nowSvcIndCd = "03";				/* 2G/3G구분(03 고정) */
	private String searchGubun;						/* 조회구분 */
	private String arPrGubun;						/* 예약선호번호구분 */
	private String tlphNoChrcCd = "GEN";			/* 전화번호특성코드(GEN 고정) */
	private String tlphNoIndCd = "01";				/* 전화번호구분코드(01 고정) */
	private String tlphNoPtrn;						/* 번호조회패턴 */
	private String tlphNoUseCd;						/* 번호사용용도 */
	private String tlphNoUseMntCd;					/* 번호사용상세(선불인 경우 I 입력) */
	private String gubun;							/* 업무구분코드 */
	private String tlphNoStatChngRsnCd;				/* 전화번호상태변경사유(RSV:예약, RRS:예약취소) */
	private String tlphNoStatCd;					/* 전화번호상태(AR:예약, AA:취소) */
	private String mnpIndCd;						/* 번호이동구분코드(번호이동 필수, I) */
	private String encdTlphNo;						/* 암호화전화번호(NU1 결과값) */
	private String mpngTlphNoYn;					/* 매핑전화번호여부 */
	private String billAcntNo;						/* 청구계정번호 */
	private String rqsshtPprfrmCd;					/* 청구서양식코드 */
	private String rqsshtTlphNo;					/* 청구서수신전화번호 */
	private String rqsshtEmlAdrsNm;					/* 청구서이메일주소 */
	private String billZipNo;						/* 청구지우편번호 */
	private String billFndtCntplcSbst;				/* 청구지기본주소 */
	private String billMntCntplcSbst;				/* 청구지상세주소 */
	private String blpymMthdCd;						/* 납부방법코드 */
	private String duedatDateIndCd;					/* 납기일자구분 */
	private String crdtCardExprDate;				/* 신용카드만기일자 */
	private String crdtCardKindCd;					/* 신용카드종류 */
	private String bankCd;							/* 은행코드 */
	private String blpymMthdIdntNo;					/* 납부방법식별번호(카드번호/계좌번호) */
	private String blpymCustNm;						/* 납부고객명 */
	private String blpymCustIdntNo;					/* 납부고객식별번호 */
	private String blpymMthdIdntNoHideYn;			/* 납부방법식별번호숨김여부(Y:숨김,N:노출) */
	private String bankSkipYn;						/* 은행건너띄기여부(Y:건너띄기,N:건너띄지않음) */
	private String agreIndCd;						/* 동의자료코드(01:서면, 02:공인인증, 03:일반인증, 04:녹취, 05:ARS) */
	private String myslAthnTypeCd;					/* 본인인증타입코드(일반인증인 경우, 01:SMS, 02:IPIN, 03:신용카드, 04:범용공인인증) */
	private String billAtchExclYn;					/* 청구첨부제외여부(Y:동봉물제외, N:동봉물포함) */
	private String rqsshtTlphNoHideYn;				/* 청구서전화번호숨김여부(Y:숨김, N:노출) */
	private String rqsshtDsptYn;					/* 청구서발송여부(Y:발송, N:미발송) */
	private String enclBillTrmnYn;					/* 동봉청구해지여부(Y:동봉청구해지, N:해지안함) */
	private String realUseCustNm;					/* 실사용고객명(법인사업자인 경우) */
	private String mngmAgncId;						/* 관리대리점ID(AGENT_CODE) */
	private String cntpntCd;						/* 접점코드(CNTPNT_SHOP_ID) */
	private String slsPrsnId;						/* 판매자ID(SHOP_USM_ID) */
	private String iccId;							/* ICC ID(유심번호) */
	private String intmMdlId;						/* 기기모델ID */
	private String intmSrlNo;						/* 기기일련번호 */
	private String usimOpenYn;						/* 유심개통여부(Y:USIM단독, N:단말개통) */
	private String spclSlsNo;						/* 특별판매코드 */
	private String agntRltnCd;						/* 대리인관계코드(법인필수, 04:위탁대리인, 06:일회성대리인) */
	private String agntBrthDate;					/* 대리인생일일자(법인필수) */
	private String agntCustNm;						/* 대리인명 */
	private String homeTlphNo;						/* 자택전화번호 */
	private String wrkplcTlphNo;					/* 직장전화번호 */
	private String prttlpNo;						/* 이동전화번호 */
	private String prdcCd;							/* 상품코드 */
	private String prdcTypeCd;						/* 상품타입코드 */
	private String spnsDscnTypeCd;					/* 스폰서할인유형코드(KD,PM) */
	private String agncSupotAmnt;					/* 대리점지원금액 */
	private String enggMnthCnt;						/* 약정개월수 */
	private String hndsetInstAmnt;					/* 단말기분납금액(단말할부시 필수) */
	private String hndsetPrpyAmnt;					/* 단말기선납금액 */
	private String instMnthCnt;						/* 분납개월수 */
	private String usimPymnMthdCd;					/* 유심수납방법(R:즉납, B:후청구, N:비구매) */
	private String sbscstPymnMthdCd;				/* 가입비수납방법(R:즉납, I:할부, P:면제) */
	private String sbscstImpsExmpRsnCd;				/* 가입비면제사유 */
	private String bondPrsrFeePymnMthdCd;			/* 채권보전료수납방법(R:즉납, B:익월청구) */
	private String sbscPrtlstRcvEmlAdrsNm;			/* 가입내역서수신이메일주소 */
	private String npFeePayMethCd;					/* 번호이동수수료수납방법(N:즉납, Y:후청구) */
	private String npBillMethCd;					/* 타사미청구금액청구방법(N:즉납, Y:후청구, X:전사업자후청구) */
	private String npHndsetInstaDuratDivCd;			/* 번호이동단말기분납지속구분, 번호이동필수(1:완납, 2:분납지속(LMS미청구), 3:분납지속(LMS청구)) */
	private String rfundNpBankCd;					/* 번호이동환불은행 */
	private String rfundBankBnkacnNo;				/* 번호이동환불계좌번호 */
	private String npTotRmnyAmt;					/* 번호이동총수납금액 */
	private String npCashRmnyAmt;					/* 번호이동현금수납금액 */
	private String npCcardRmnyAmt;					/* 번호이동카드수납액 */
	private String npRmnyMethCd;					/* 번호이동수납방법코드 */
	private String npHndsetInstaLmsTlphNo;			/* 번호이동단말기분납문자명세서전화번호, 번호이동 가입시 단말기분납지속(LMS청구)인 경우 필수 */
	private String npCcardNo;						/* 번호이동카드번호, 수납방법 카드인 경우 필수 */
	private String npCcardExpirYm;					/* 번호이동카드유효기간, 수납방법 카드인 경우 필수 */
	private String npInslMonsNum;					/* 번호이동할부개월수, 수납방법 카드인 경우 필수 */
	private String npCcardSnctTypeCd;				/* 번호이동결제유형코드, 수납방법 카드인 경우 필수 */
	private String npCcardOwnrIdfyNo;				/* 번호이동카드명의인식별번호, 수납방법 카드인 경우 필수 */
	private String npSgntInfo;						/* 번호이동서명정보, 수납방법 카드인 경우 필수 */
	private String payAsertDt;						/* 납부주장일자 */
	private String payAsertAmt;						/* 납부주장금액 */
	private String payMethCd;						/* 납부방법코드 */
	private String custId;
	private String ncn;
	private String ctn;
	private String cntplcNo;
	private String itgOderWhyCd;
	private String aftmnIncInCd;
	private String apyRelTypeCd;
	private String custTchMediCd;
	private String smsRcvYn;
	private String appEventCd;
	private String serviceName;
	private String operationName;
	private String voName;
	private String resNo;
	private String wantTlphNo;		// 희망번호
	//부가서비스처리용
	private String requestKey;
	private String additionKey;
	// KOS 사용자ID
	private String nstepUserId;

	private String cstmrType;

	private String cstmrForeignerRrn;

	private String cstmrNativeRrn;

	private String cstmrJuridicalRrn;

	private String etc2;

	private String selfIssuNum;

	private String reqCardNo;

	private String reqAccountNumber;

	private String reqCardRrn;

	private String reqAccountRrn;

	private String reqPayOtherFlag;

	public String getReqPayOtherFlag() {
		return reqPayOtherFlag;
	}
	public void setReqPayOtherFlag(String reqPayOtherFlag) {
		this.reqPayOtherFlag = reqPayOtherFlag;
	}
	public String getReqCardRrn() {
		return reqCardRrn;
	}
	public void setReqCardRrn(String reqCardRrn) {
		this.reqCardRrn = reqCardRrn;
	}
	public String getReqAccountRrn() {
		return reqAccountRrn;
	}
	public void setReqAccountRrn(String reqAccountRrn) {
		this.reqAccountRrn = reqAccountRrn;
	}
	public String getReqCardNo() {
		return reqCardNo;
	}
	public void setReqCardNo(String reqCardNo) {
		this.reqCardNo = reqCardNo;
	}
	public String getReqAccountNumber() {
		return reqAccountNumber;
	}
	public void setReqAccountNumber(String reqAccountNumber) {
		this.reqAccountNumber = reqAccountNumber;
	}
	public String getSelfIssuNum() {
		return selfIssuNum;
	}
	public void setSelfIssuNum(String selfIssuNum) {
		this.selfIssuNum = selfIssuNum;
	}
	public String getEtc2() {
		return etc2;
	}
	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}
	public String getCstmrJuridicalRrn() {
		return cstmrJuridicalRrn;
	}
	public void setCstmrJuridicalRrn(String cstmrJuridicalRrn) {
		this.cstmrJuridicalRrn = cstmrJuridicalRrn;
	}
	public String getCstmrForeignerRrn() {
		return cstmrForeignerRrn;
	}
	public void setCstmrForeignerRrn(String cstmrForeignerRrn) {
		this.cstmrForeignerRrn = cstmrForeignerRrn;
	}
	public String getCstmrNativeRrn() {
		return cstmrNativeRrn;
	}
	public void setCstmrNativeRrn(String cstmrNativeRrn) {
		this.cstmrNativeRrn = cstmrNativeRrn;
	}
	public String getCstmrType() {
		return cstmrType;
	}
	public void setCstmrType(String cstmrType) {
		this.cstmrType = cstmrType;
	}
	public String getMvnoOrdNo() {
		return mvnoOrdNo;
	}
	public void setMvnoOrdNo(String mvnoOrdNo) {
		this.mvnoOrdNo = mvnoOrdNo;
	}
	public String getSlsCmpnCd() {
		return slsCmpnCd;
	}
	public void setSlsCmpnCd(String slsCmpnCd) {
		this.slsCmpnCd = slsCmpnCd;
	}
	public String getCustTypeCd() {
		return custTypeCd;
	}
	public void setCustTypeCd(String custTypeCd) {
		this.custTypeCd = custTypeCd;
	}
	public String getCustIdntNoIndCd() {
		return custIdntNoIndCd;
	}
	public void setCustIdntNoIndCd(String custIdntNoIndCd) {
		this.custIdntNoIndCd = custIdntNoIndCd;
	}

	public String getCustIdntNo() {
		String rtnVal = "";
		try {
			if("FN".equals(getCstmrType())) {
				rtnVal = EncryptUtil.ace256Dec(getCstmrForeignerRrn().trim());
			}else {
				rtnVal = EncryptUtil.ace256Dec(getCstmrNativeRrn().trim());
			}
        } catch (CryptoException e) {
            return "";
        } catch (Exception e) {
            return "";
        }

		return rtnVal;

	}
	public void setCustIdntNo(String custIdntNo) {
		this.custIdntNo = custIdntNo;
	}
	public String getCrprNo() {
		try {
			return EncryptUtil.ace256Dec(getCstmrJuridicalRrn().trim());
        } catch (CryptoException e) {
            return "";
        } catch (Exception e) {
            return "";
        }
	}
	public void setCrprNo(String crprNo) {
		this.crprNo = crprNo;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getCntrUseCd() {
		return cntrUseCd;
	}
	public void setCntrUseCd(String cntrUseCd) {
		this.cntrUseCd = cntrUseCd;
	}
	public String getInstYn() {
		return instYn;
	}
	public void setInstYn(String instYn) {
		this.instYn = instYn;
	}
	public String getScnhndPhonInstYn() {
		return scnhndPhonInstYn;
	}
	public void setScnhndPhonInstYn(String scnhndPhonInstYn) {
		this.scnhndPhonInstYn = scnhndPhonInstYn;
	}
	public String getMyslAgreYn() {
		return myslAgreYn;
	}
	public void setMyslAgreYn(String myslAgreYn) {
		this.myslAgreYn = myslAgreYn;
	}
	public String getNativeRlnamAthnEvdnPprCd() {
		return nativeRlnamAthnEvdnPprCd;
	}
	public void setNativeRlnamAthnEvdnPprCd(String nativeRlnamAthnEvdnPprCd) {
		this.nativeRlnamAthnEvdnPprCd = nativeRlnamAthnEvdnPprCd;
	}
	public String getAthnRqstcustCntplcNo() {
		return athnRqstcustCntplcNo;
	}
	public void setAthnRqstcustCntplcNo(String athnRqstcustCntplcNo) {
		this.athnRqstcustCntplcNo = athnRqstcustCntplcNo;
	}
	public String getRsdcrtIssuDate() {
		return rsdcrtIssuDate;
	}
	public void setRsdcrtIssuDate(String rsdcrtIssuDate) {
		this.rsdcrtIssuDate = rsdcrtIssuDate;
	}
	public String getLcnsNo() {

		String rtnVal = "";
		try {
			if("DRIVE".equals(getEtc2())) {
				if(getSelfIssuNum() != null && !"".equals(getSelfIssuNum()) && getSelfIssuNum().length() > 3) {
					rtnVal = EncryptUtil.ace256Dec(getSelfIssuNum()).substring(getSelfIssuNum().length()-3,getSelfIssuNum().length());
				}
			}
		} catch (CryptoException e) {
            return "";
        } catch (Exception e) {
            return "";
        }

		return rtnVal;

	}
	public void setLcnsNo(String lcnsNo) {
		this.lcnsNo = lcnsNo;
	}
	public String getLcnsRgnCd() {

		String rtnVal = "";
		try {
			if("DRIVE".equals(getEtc2())) {
				if(getSelfIssuNum() != null && !"".equals(getSelfIssuNum()) && getSelfIssuNum().length() > 2) {
					rtnVal = EncryptUtil.ace256Dec(getSelfIssuNum()).substring(2);
				}
			}
		} catch (CryptoException e) {
            return "";
        } catch (Exception e) {
            return "";
        }

		return rtnVal;

	}
	public void setLcnsRgnCd(String lcnsRgnCd) {
		this.lcnsRgnCd = lcnsRgnCd;
	}
	public String getMrtrPrsnNo() {

		String rtnVal = "";
		try {
			if("MERIT".equals(getEtc2())) {
				if(getSelfIssuNum() != null && !"".equals(getSelfIssuNum())) {
					rtnVal = EncryptUtil.ace256Dec(getSelfIssuNum());
				}
			}
		} catch (CryptoException e) {
            return "";
        } catch (Exception e) {
            return "";
        }

		return rtnVal;

	}
	public void setMrtrPrsnNo(String mrtrPrsnNo) {
		this.mrtrPrsnNo = mrtrPrsnNo;
	}
	public String getNationalityCd() {
		return nationalityCd;
	}
	public void setNationalityCd(String nationalityCd) {
		this.nationalityCd = nationalityCd;
	}
	public String getFornBrthDate() {
		return fornBrthDate;
	}
	public void setFornBrthDate(String fornBrthDate) {
		this.fornBrthDate = fornBrthDate;
	}
	public String getCrdtInfoAgreYn() {
		return crdtInfoAgreYn;
	}
	public void setCrdtInfoAgreYn(String crdtInfoAgreYn) {
		this.crdtInfoAgreYn = crdtInfoAgreYn;
	}
	public String getIndvInfoInerPrcuseAgreYn() {
		return indvInfoInerPrcuseAgreYn;
	}
	public void setIndvInfoInerPrcuseAgreYn(String indvInfoInerPrcuseAgreYn) {
		this.indvInfoInerPrcuseAgreYn = indvInfoInerPrcuseAgreYn;
	}
	public String getCnsgInfoAdvrRcvAgreYn() {
		return cnsgInfoAdvrRcvAgreYn;
	}
	public void setCnsgInfoAdvrRcvAgreYn(String cnsgInfoAdvrRcvAgreYn) {
		this.cnsgInfoAdvrRcvAgreYn = cnsgInfoAdvrRcvAgreYn;
	}
	public String getOthcmpInfoAdvrRcvAgreYn() {
		return othcmpInfoAdvrRcvAgreYn;
	}
	public void setOthcmpInfoAdvrRcvAgreYn(String othcmpInfoAdvrRcvAgreYn) {
		this.othcmpInfoAdvrRcvAgreYn = othcmpInfoAdvrRcvAgreYn;
	}
	public String getOthcmpInfoAdvrCnsgAgreYn() {
		return othcmpInfoAdvrCnsgAgreYn;
	}
	public void setOthcmpInfoAdvrCnsgAgreYn(String othcmpInfoAdvrCnsgAgreYn) {
		this.othcmpInfoAdvrCnsgAgreYn = othcmpInfoAdvrCnsgAgreYn;
	}
	public String getGrpAgntBindSvcSbscAgreYn() {
		return grpAgntBindSvcSbscAgreYn;
	}
	public void setGrpAgntBindSvcSbscAgreYn(String grpAgntBindSvcSbscAgreYn) {
		this.grpAgntBindSvcSbscAgreYn = grpAgntBindSvcSbscAgreYn;
	}
	public String getCardInsrPrdcAgreYn() {
		return cardInsrPrdcAgreYn;
	}
	public void setCardInsrPrdcAgreYn(String cardInsrPrdcAgreYn) {
		this.cardInsrPrdcAgreYn = cardInsrPrdcAgreYn;
	}
	public String getOlhRailCprtAgreYn() {
		return olhRailCprtAgreYn;
	}
	public void setOlhRailCprtAgreYn(String olhRailCprtAgreYn) {
		this.olhRailCprtAgreYn = olhRailCprtAgreYn;
	}
	public String getOlhShckWibroRlfAgreYn() {
		return olhShckWibroRlfAgreYn;
	}
	public void setOlhShckWibroRlfAgreYn(String olhShckWibroRlfAgreYn) {
		this.olhShckWibroRlfAgreYn = olhShckWibroRlfAgreYn;
	}
	public String getOlngDscnHynmtrAgreYn() {
		return olngDscnHynmtrAgreYn;
	}
	public void setOlngDscnHynmtrAgreYn(String olngDscnHynmtrAgreYn) {
		this.olngDscnHynmtrAgreYn = olngDscnHynmtrAgreYn;
	}
	public String getOlhCprtPntAgreYn() {
		return olhCprtPntAgreYn;
	}
	public void setOlhCprtPntAgreYn(String olhCprtPntAgreYn) {
		this.olhCprtPntAgreYn = olhCprtPntAgreYn;
	}
	public String getDwoCprtPntAgreYn() {
		return dwoCprtPntAgreYn;
	}
	public void setDwoCprtPntAgreYn(String dwoCprtPntAgreYn) {
		this.dwoCprtPntAgreYn = dwoCprtPntAgreYn;
	}
	public String getWlfrDscnAplyAgreYn() {
		return wlfrDscnAplyAgreYn;
	}
	public void setWlfrDscnAplyAgreYn(String wlfrDscnAplyAgreYn) {
		this.wlfrDscnAplyAgreYn = wlfrDscnAplyAgreYn;
	}
	public String getSpamPrvdAgreYn() {
		return spamPrvdAgreYn;
	}
	public void setSpamPrvdAgreYn(String spamPrvdAgreYn) {
		this.spamPrvdAgreYn = spamPrvdAgreYn;
	}
	public String getPrttlpStlmUseAgreYn() {
		return prttlpStlmUseAgreYn;
	}
	public void setPrttlpStlmUseAgreYn(String prttlpStlmUseAgreYn) {
		this.prttlpStlmUseAgreYn = prttlpStlmUseAgreYn;
	}
	public String getPrttlpStlmPwdUseAgreYn() {
		return prttlpStlmPwdUseAgreYn;
	}
	public void setPrttlpStlmPwdUseAgreYn(String prttlpStlmPwdUseAgreYn) {
		this.prttlpStlmPwdUseAgreYn = prttlpStlmPwdUseAgreYn;
	}
	public String getWrlnTlphNo() {
		return wrlnTlphNo;
	}
	public void setWrlnTlphNo(String wrlnTlphNo) {
		this.wrlnTlphNo = wrlnTlphNo;
	}
	public String getTlphNo() {
		return tlphNo;
	}
	public void setTlphNo(String tlphNo) {
		this.tlphNo = tlphNo;
	}
	public String getRprsPrsnNm() {
		return rprsPrsnNm;
	}
	public void setRprsPrsnNm(String rprsPrsnNm) {
		this.rprsPrsnNm = rprsPrsnNm;
	}
	public String getUpjnCd() {
		return upjnCd;
	}
	public void setUpjnCd(String upjnCd) {
		this.upjnCd = upjnCd;
	}
	public String getBcuSbst() {
		return bcuSbst;
	}
	public void setBcuSbst(String bcuSbst) {
		this.bcuSbst = bcuSbst;
	}
	public String getZipNo() {
		return zipNo;
	}
	public void setZipNo(String zipNo) {
		this.zipNo = zipNo;
	}
	public String getFndtCntplcSbst() {
		return fndtCntplcSbst;
	}
	public void setFndtCntplcSbst(String fndtCntplcSbst) {
		this.fndtCntplcSbst = fndtCntplcSbst;
	}
	public String getMntCntplcSbst() {
		return mntCntplcSbst;
	}
	public void setMntCntplcSbst(String mntCntplcSbst) {
		this.mntCntplcSbst = mntCntplcSbst;
	}
	public String getBrthDate() {
		return brthDate;
	}
	public void setBrthDate(String brthDate) {
		this.brthDate = brthDate;
	}
	public String getBrthNnpIndCd() {
		return brthNnpIndCd;
	}
	public void setBrthNnpIndCd(String brthNnpIndCd) {
		this.brthNnpIndCd = brthNnpIndCd;
	}
	public String getJobCd() {
		return jobCd;
	}
	public void setJobCd(String jobCd) {
		this.jobCd = jobCd;
	}
	public String getEmlAdrsNm() {
		return emlAdrsNm;
	}
	public void setEmlAdrsNm(String emlAdrsNm) {
		this.emlAdrsNm = emlAdrsNm;
	}
	public String getLstdIndCd() {
		return lstdIndCd;
	}
	public void setLstdIndCd(String lstdIndCd) {
		this.lstdIndCd = lstdIndCd;
	}
	public String getEmplCnt() {
		return emplCnt;
	}
	public void setEmplCnt(String emplCnt) {
		this.emplCnt = emplCnt;
	}
	public String getSlngAmt() {
		return slngAmt;
	}
	public void setSlngAmt(String slngAmt) {
		this.slngAmt = slngAmt;
	}
	public String getCptlAmnt() {
		return cptlAmnt;
	}
	public void setCptlAmnt(String cptlAmnt) {
		this.cptlAmnt = cptlAmnt;
	}
	public String getCrprUpjnCd() {
		return crprUpjnCd;
	}
	public void setCrprUpjnCd(String crprUpjnCd) {
		this.crprUpjnCd = crprUpjnCd;
	}
	public String getCrprBcuSbst() {
		return crprBcuSbst;
	}
	public void setCrprBcuSbst(String crprBcuSbst) {
		this.crprBcuSbst = crprBcuSbst;
	}
	public String getCrprZipNo() {
		return crprZipNo;
	}
	public void setCrprZipNo(String crprZipNo) {
		this.crprZipNo = crprZipNo;
	}
	public String getCrprFndtCntplcSbst() {
		return crprFndtCntplcSbst;
	}
	public void setCrprFndtCntplcSbst(String crprFndtCntplcSbst) {
		this.crprFndtCntplcSbst = crprFndtCntplcSbst;
	}
	public String getCrprMntCntplcSbst() {
		return crprMntCntplcSbst;
	}
	public void setCrprMntCntplcSbst(String crprMntCntplcSbst) {
		this.crprMntCntplcSbst = crprMntCntplcSbst;
	}
	public String getCustInfoChngYn() {
		return custInfoChngYn;
	}
	public void setCustInfoChngYn(String custInfoChngYn) {
		this.custInfoChngYn = custInfoChngYn;
	}
	public String getM2mHndsetYn() {
		return m2mHndsetYn;
	}
	public void setM2mHndsetYn(String m2mHndsetYn) {
		this.m2mHndsetYn = m2mHndsetYn;
	}
	public String getOrderTypeCd() {
		return orderTypeCd;
	}
	public void setOrderTypeCd(String orderTypeCd) {
		this.orderTypeCd = orderTypeCd;
	}
	public String getNpTlphNo() {
		return npTlphNo;
	}
	public void setNpTlphNo(String npTlphNo) {
		this.npTlphNo = npTlphNo;
	}
	public String getIndvBizrYn() {
		return indvBizrYn;
	}
	public void setIndvBizrYn(String indvBizrYn) {
		this.indvBizrYn = indvBizrYn;
	}
	public String getBchngNpCommCmpnCd() {
		return bchngNpCommCmpnCd;
	}
	public void setBchngNpCommCmpnCd(String bchngNpCommCmpnCd) {
		this.bchngNpCommCmpnCd = bchngNpCommCmpnCd;
	}
	public String getAthnItemCd() {
		return athnItemCd;
	}
	public void setAthnItemCd(String athnItemCd) {
		this.athnItemCd = athnItemCd;
	}
	public String getAthnSbst() {
		return athnSbst;
	}
	public void setAthnSbst(String athnSbst) {
		this.athnSbst = athnSbst;
	}
	public String getNpRstrtnContYn() {
		return npRstrtnContYn;
	}
	public void setNpRstrtnContYn(String npRstrtnContYn) {
		this.npRstrtnContYn = npRstrtnContYn;
	}
	public String getYtrpaySoffAgreYn() {
		return ytrpaySoffAgreYn;
	}
	public void setYtrpaySoffAgreYn(String ytrpaySoffAgreYn) {
		this.ytrpaySoffAgreYn = ytrpaySoffAgreYn;
	}
	public String getOsstOrdNo() {
		return osstOrdNo;
	}
	public void setOsstOrdNo(String osstOrdNo) {
		this.osstOrdNo = osstOrdNo;
	}
	public String getAsgnAgncId() {
		return asgnAgncId;
	}
	public void setAsgnAgncId(String asgnAgncId) {
		this.asgnAgncId = asgnAgncId;
	}
	public String getAsgnAgncYn() {
		return asgnAgncYn;
	}
	public void setAsgnAgncYn(String asgnAgncYn) {
		this.asgnAgncYn = asgnAgncYn;
	}
	public String getCntryCd() {
		return cntryCd;
	}
	public void setCntryCd(String cntryCd) {
		this.cntryCd = cntryCd;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getInqrBase() {
		return inqrBase;
	}
	public void setInqrBase(String inqrBase) {
		this.inqrBase = inqrBase;
	}
	public String getInqrCascnt() {
		return inqrCascnt;
	}
	public void setInqrCascnt(String inqrCascnt) {
		this.inqrCascnt = inqrCascnt;
	}
	public String getNowSvcIndCd() {
		return nowSvcIndCd;
	}
	public void setNowSvcIndCd(String nowSvcIndCd) {
		this.nowSvcIndCd = nowSvcIndCd;
	}
	public String getSearchGubun() {
		return searchGubun;
	}
	public void setSearchGubun(String searchGubun) {
		this.searchGubun = searchGubun;
	}
	public String getArPrGubun() {
		return arPrGubun;
	}
	public void setArPrGubun(String arPrGubun) {
		this.arPrGubun = arPrGubun;
	}
	public String getTlphNoChrcCd() {
		return tlphNoChrcCd;
	}
	public void setTlphNoChrcCd(String tlphNoChrcCd) {
		this.tlphNoChrcCd = tlphNoChrcCd;
	}
	public String getTlphNoIndCd() {
		return tlphNoIndCd;
	}
	public void setTlphNoIndCd(String tlphNoIndCd) {
		this.tlphNoIndCd = tlphNoIndCd;
	}
	public String getTlphNoPtrn() {
		return tlphNoPtrn;
	}
	public void setTlphNoPtrn(String tlphNoPtrn) {
		this.tlphNoPtrn = tlphNoPtrn;
	}
	public String getTlphNoUseCd() {
		return tlphNoUseCd;
	}
	public void setTlphNoUseCd(String tlphNoUseCd) {
		this.tlphNoUseCd = tlphNoUseCd;
	}
	public String getTlphNoUseMntCd() {
		return tlphNoUseMntCd;
	}
	public void setTlphNoUseMntCd(String tlphNoUseMntCd) {
		this.tlphNoUseMntCd = tlphNoUseMntCd;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getTlphNoStatChngRsnCd() {
		return tlphNoStatChngRsnCd;
	}
	public void setTlphNoStatChngRsnCd(String tlphNoStatChngRsnCd) {
		this.tlphNoStatChngRsnCd = tlphNoStatChngRsnCd;
	}
	public String getTlphNoStatCd() {
		return tlphNoStatCd;
	}
	public void setTlphNoStatCd(String tlphNoStatCd) {
		this.tlphNoStatCd = tlphNoStatCd;
	}
	public String getMnpIndCd() {
		return mnpIndCd;
	}
	public void setMnpIndCd(String mnpIndCd) {
		this.mnpIndCd = mnpIndCd;
	}
	public String getEncdTlphNo() {
		return encdTlphNo;
	}
	public void setEncdTlphNo(String encdTlphNo) {
		this.encdTlphNo = encdTlphNo;
	}
	public String getMpngTlphNoYn() {
		return mpngTlphNoYn;
	}
	public void setMpngTlphNoYn(String mpngTlphNoYn) {
		this.mpngTlphNoYn = mpngTlphNoYn;
	}
	public String getBillAcntNo() {
		return billAcntNo;
	}
	public void setBillAcntNo(String billAcntNo) {
		this.billAcntNo = billAcntNo;
	}
	public String getRqsshtPprfrmCd() {
		return rqsshtPprfrmCd;
	}
	public void setRqsshtPprfrmCd(String rqsshtPprfrmCd) {
		this.rqsshtPprfrmCd = rqsshtPprfrmCd;
	}
	public String getRqsshtTlphNo() {
		return rqsshtTlphNo;
	}
	public void setRqsshtTlphNo(String rqsshtTlphNo) {
		this.rqsshtTlphNo = rqsshtTlphNo;
	}
	public String getRqsshtEmlAdrsNm() {
		return rqsshtEmlAdrsNm;
	}
	public void setRqsshtEmlAdrsNm(String rqsshtEmlAdrsNm) {
		this.rqsshtEmlAdrsNm = rqsshtEmlAdrsNm;
	}
	public String getBillZipNo() {
		return billZipNo;
	}
	public void setBillZipNo(String billZipNo) {
		this.billZipNo = billZipNo;
	}
	public String getBillFndtCntplcSbst() {
		return billFndtCntplcSbst;
	}
	public void setBillFndtCntplcSbst(String billFndtCntplcSbst) {
		this.billFndtCntplcSbst = billFndtCntplcSbst;
	}
	public String getBillMntCntplcSbst() {
		return billMntCntplcSbst;
	}
	public void setBillMntCntplcSbst(String billMntCntplcSbst) {
		this.billMntCntplcSbst = billMntCntplcSbst;
	}
	public String getBlpymMthdCd() {
		return blpymMthdCd;
	}
	public void setBlpymMthdCd(String blpymMthdCd) {
		this.blpymMthdCd = blpymMthdCd;
	}
	public String getDuedatDateIndCd() {
		return duedatDateIndCd;
	}
	public void setDuedatDateIndCd(String duedatDateIndCd) {
		this.duedatDateIndCd = duedatDateIndCd;
	}
	public String getCrdtCardExprDate() {
		return crdtCardExprDate;
	}
	public void setCrdtCardExprDate(String crdtCardExprDate) {
		this.crdtCardExprDate = crdtCardExprDate;
	}
	public String getCrdtCardKindCd() {
		return crdtCardKindCd;
	}
	public void setCrdtCardKindCd(String crdtCardKindCd) {
		this.crdtCardKindCd = crdtCardKindCd;
	}
	public String getBankCd() {
		return bankCd;
	}
	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}
	public String getBlpymMthdIdntNo() {

		String rtnVal = "";
		try {
			if("C".equals(getBlpymMthdCd())) {
				rtnVal = EncryptUtil.ace256Dec(getReqCardNo());
			}else if("D".equals(getBlpymMthdCd())) {
				rtnVal = EncryptUtil.ace256Dec(getReqAccountNumber());
			}
		} catch (CryptoException e) {
            return "";
        } catch (Exception e) {
            return "";
        }

		return rtnVal;

	}

	public void setBlpymMthdIdntNo(String blpymMthdIdntNo) {
		this.blpymMthdIdntNo = blpymMthdIdntNo;
	}
	public String getBlpymCustNm() {
		return blpymCustNm;
	}
	public void setBlpymCustNm(String blpymCustNm) {
		this.blpymCustNm = blpymCustNm;
	}
	public String getBlpymCustIdntNo() {

		String rtnVal = "";
		try {
			if("Y".equals(getReqPayOtherFlag()) && "C".equals(getBlpymMthdCd())) {
				rtnVal = EncryptUtil.ace256Dec(getReqCardRrn());
			}else if("Y".equals(getReqPayOtherFlag()) && "D".equals(getBlpymMthdCd())) {
				rtnVal = EncryptUtil.ace256Dec(getReqAccountRrn());
			}
		} catch (CryptoException e) {
            return "";
        } catch (Exception e) {
            return "";
        }

		return rtnVal;

	}
	public void setBlpymCustIdntNo(String blpymCustIdntNo) {
		this.blpymCustIdntNo = blpymCustIdntNo;
	}
	public String getBlpymMthdIdntNoHideYn() {
		return blpymMthdIdntNoHideYn;
	}
	public void setBlpymMthdIdntNoHideYn(String blpymMthdIdntNoHideYn) {
		this.blpymMthdIdntNoHideYn = blpymMthdIdntNoHideYn;
	}
	public String getBankSkipYn() {
		return bankSkipYn;
	}
	public void setBankSkipYn(String bankSkipYn) {
		this.bankSkipYn = bankSkipYn;
	}
	public String getAgreIndCd() {
		return agreIndCd;
	}
	public void setAgreIndCd(String agreIndCd) {
		this.agreIndCd = agreIndCd;
	}
	public String getMyslAthnTypeCd() {
		return myslAthnTypeCd;
	}
	public void setMyslAthnTypeCd(String myslAthnTypeCd) {
		this.myslAthnTypeCd = myslAthnTypeCd;
	}
	public String getBillAtchExclYn() {
		return billAtchExclYn;
	}
	public void setBillAtchExclYn(String billAtchExclYn) {
		this.billAtchExclYn = billAtchExclYn;
	}
	public String getRqsshtTlphNoHideYn() {
		return rqsshtTlphNoHideYn;
	}
	public void setRqsshtTlphNoHideYn(String rqsshtTlphNoHideYn) {
		this.rqsshtTlphNoHideYn = rqsshtTlphNoHideYn;
	}
	public String getRqsshtDsptYn() {
		return rqsshtDsptYn;
	}
	public void setRqsshtDsptYn(String rqsshtDsptYn) {
		this.rqsshtDsptYn = rqsshtDsptYn;
	}
	public String getEnclBillTrmnYn() {
		return enclBillTrmnYn;
	}
	public void setEnclBillTrmnYn(String enclBillTrmnYn) {
		this.enclBillTrmnYn = enclBillTrmnYn;
	}
	public String getRealUseCustNm() {
		return realUseCustNm;
	}
	public void setRealUseCustNm(String realUseCustNm) {
		this.realUseCustNm = realUseCustNm;
	}
	public String getMngmAgncId() {
		return mngmAgncId;
	}
	public void setMngmAgncId(String mngmAgncId) {
		this.mngmAgncId = mngmAgncId;
	}
	public String getCntpntCd() {
		return cntpntCd;
	}
	public void setCntpntCd(String cntpntCd) {
		this.cntpntCd = cntpntCd;
	}
	public String getSlsPrsnId() {
		return slsPrsnId;
	}
	public void setSlsPrsnId(String slsPrsnId) {
		this.slsPrsnId = slsPrsnId;
	}
	public String getIccId() {
		return iccId;
	}
	public void setIccId(String iccId) {
		this.iccId = iccId;
	}
	public String getIntmMdlId() {
		return intmMdlId;
	}
	public void setIntmMdlId(String intmMdlId) {
		this.intmMdlId = intmMdlId;
	}
	public String getIntmSrlNo() {
		return intmSrlNo;
	}
	public void setIntmSrlNo(String intmSrlNo) {
		this.intmSrlNo = intmSrlNo;
	}
	public String getUsimOpenYn() {
		return usimOpenYn;
	}
	public void setUsimOpenYn(String usimOpenYn) {
		this.usimOpenYn = usimOpenYn;
	}
	public String getSpclSlsNo() {
		return spclSlsNo;
	}
	public void setSpclSlsNo(String spclSlsNo) {
		this.spclSlsNo = spclSlsNo;
	}
	public String getAgntRltnCd() {
		return agntRltnCd;
	}
	public void setAgntRltnCd(String agntRltnCd) {
		this.agntRltnCd = agntRltnCd;
	}
	public String getAgntBrthDate() {
		return agntBrthDate;
	}
	public void setAgntBrthDate(String agntBrthDate) {
		this.agntBrthDate = agntBrthDate;
	}
	public String getAgntCustNm() {
		return agntCustNm;
	}
	public void setAgntCustNm(String agntCustNm) {
		this.agntCustNm = agntCustNm;
	}
	public String getHomeTlphNo() {
		return homeTlphNo;
	}
	public void setHomeTlphNo(String homeTlphNo) {
		this.homeTlphNo = homeTlphNo;
	}
	public String getWrkplcTlphNo() {
		return wrkplcTlphNo;
	}
	public void setWrkplcTlphNo(String wrkplcTlphNo) {
		this.wrkplcTlphNo = wrkplcTlphNo;
	}
	public String getPrttlpNo() {
		return prttlpNo;
	}
	public void setPrttlpNo(String prttlpNo) {
		this.prttlpNo = prttlpNo;
	}
	public String getPrdcCd() {
		return prdcCd;
	}
	public void setPrdcCd(String prdcCd) {
		this.prdcCd = prdcCd;
	}
	public String getPrdcTypeCd() {
		return prdcTypeCd;
	}
	public void setPrdcTypeCd(String prdcTypeCd) {
		this.prdcTypeCd = prdcTypeCd;
	}
	public String getSpnsDscnTypeCd() {
		return spnsDscnTypeCd;
	}
	public void setSpnsDscnTypeCd(String spnsDscnTypeCd) {
		this.spnsDscnTypeCd = spnsDscnTypeCd;
	}
	public String getAgncSupotAmnt() {
		return agncSupotAmnt;
	}
	public void setAgncSupotAmnt(String agncSupotAmnt) {
		this.agncSupotAmnt = agncSupotAmnt;
	}
	public String getEnggMnthCnt() {
		return enggMnthCnt;
	}
	public void setEnggMnthCnt(String enggMnthCnt) {
		this.enggMnthCnt = enggMnthCnt;
	}
	public String getHndsetInstAmnt() {
		return hndsetInstAmnt;
	}
	public void setHndsetInstAmnt(String hndsetInstAmnt) {
		this.hndsetInstAmnt = hndsetInstAmnt;
	}
	public String getHndsetPrpyAmnt() {
		return hndsetPrpyAmnt;
	}
	public void setHndsetPrpyAmnt(String hndsetPrpyAmnt) {
		this.hndsetPrpyAmnt = hndsetPrpyAmnt;
	}
	public String getInstMnthCnt() {
		return instMnthCnt;
	}
	public void setInstMnthCnt(String instMnthCnt) {
		this.instMnthCnt = instMnthCnt;
	}
	public String getUsimPymnMthdCd() {
		return usimPymnMthdCd;
	}
	public void setUsimPymnMthdCd(String usimPymnMthdCd) {
		this.usimPymnMthdCd = usimPymnMthdCd;
	}
	public String getSbscstPymnMthdCd() {
		return sbscstPymnMthdCd;
	}
	public void setSbscstPymnMthdCd(String sbscstPymnMthdCd) {
		this.sbscstPymnMthdCd = sbscstPymnMthdCd;
	}
	public String getSbscstImpsExmpRsnCd() {
		return sbscstImpsExmpRsnCd;
	}
	public void setSbscstImpsExmpRsnCd(String sbscstImpsExmpRsnCd) {
		this.sbscstImpsExmpRsnCd = sbscstImpsExmpRsnCd;
	}
	public String getBondPrsrFeePymnMthdCd() {
		return bondPrsrFeePymnMthdCd;
	}
	public void setBondPrsrFeePymnMthdCd(String bondPrsrFeePymnMthdCd) {
		this.bondPrsrFeePymnMthdCd = bondPrsrFeePymnMthdCd;
	}
	public String getSbscPrtlstRcvEmlAdrsNm() {
		return sbscPrtlstRcvEmlAdrsNm;
	}
	public void setSbscPrtlstRcvEmlAdrsNm(String sbscPrtlstRcvEmlAdrsNm) {
		this.sbscPrtlstRcvEmlAdrsNm = sbscPrtlstRcvEmlAdrsNm;
	}
	public String getNpFeePayMethCd() {
		return npFeePayMethCd;
	}
	public void setNpFeePayMethCd(String npFeePayMethCd) {
		this.npFeePayMethCd = npFeePayMethCd;
	}
	public String getNpBillMethCd() {
		return npBillMethCd;
	}
	public void setNpBillMethCd(String npBillMethCd) {
		this.npBillMethCd = npBillMethCd;
	}
	public String getNpHndsetInstaDuratDivCd() {
		return npHndsetInstaDuratDivCd;
	}
	public void setNpHndsetInstaDuratDivCd(String npHndsetInstaDuratDivCd) {
		this.npHndsetInstaDuratDivCd = npHndsetInstaDuratDivCd;
	}
	public String getRfundNpBankCd() {
		return rfundNpBankCd;
	}
	public void setRfundNpBankCd(String rfundNpBankCd) {
		this.rfundNpBankCd = rfundNpBankCd;
	}
	public String getRfundBankBnkacnNo() {
		return rfundBankBnkacnNo;
	}
	public void setRfundBankBnkacnNo(String rfundBankBnkacnNo) {
		this.rfundBankBnkacnNo = rfundBankBnkacnNo;
	}
	public String getNpTotRmnyAmt() {
		return npTotRmnyAmt;
	}
	public void setNpTotRmnyAmt(String npTotRmnyAmt) {
		this.npTotRmnyAmt = npTotRmnyAmt;
	}
	public String getNpCashRmnyAmt() {
		return npCashRmnyAmt;
	}
	public void setNpCashRmnyAmt(String npCashRmnyAmt) {
		this.npCashRmnyAmt = npCashRmnyAmt;
	}
	public String getNpCcardRmnyAmt() {
		return npCcardRmnyAmt;
	}
	public void setNpCcardRmnyAmt(String npCcardRmnyAmt) {
		this.npCcardRmnyAmt = npCcardRmnyAmt;
	}
	public String getNpRmnyMethCd() {
		return npRmnyMethCd;
	}
	public void setNpRmnyMethCd(String npRmnyMethCd) {
		this.npRmnyMethCd = npRmnyMethCd;
	}
	public String getNpHndsetInstaLmsTlphNo() {
		return npHndsetInstaLmsTlphNo;
	}
	public void setNpHndsetInstaLmsTlphNo(String npHndsetInstaLmsTlphNo) {
		this.npHndsetInstaLmsTlphNo = npHndsetInstaLmsTlphNo;
	}
	public String getNpCcardNo() {
		return npCcardNo;
	}
	public void setNpCcardNo(String npCcardNo) {
		this.npCcardNo = npCcardNo;
	}
	public String getNpCcardExpirYm() {
		return npCcardExpirYm;
	}
	public void setNpCcardExpirYm(String npCcardExpirYm) {
		this.npCcardExpirYm = npCcardExpirYm;
	}
	public String getNpInslMonsNum() {
		return npInslMonsNum;
	}
	public void setNpInslMonsNum(String npInslMonsNum) {
		this.npInslMonsNum = npInslMonsNum;
	}
	public String getNpCcardSnctTypeCd() {
		return npCcardSnctTypeCd;
	}
	public void setNpCcardSnctTypeCd(String npCcardSnctTypeCd) {
		this.npCcardSnctTypeCd = npCcardSnctTypeCd;
	}
	public String getNpCcardOwnrIdfyNo() {
		return npCcardOwnrIdfyNo;
	}
	public void setNpCcardOwnrIdfyNo(String npCcardOwnrIdfyNo) {
		this.npCcardOwnrIdfyNo = npCcardOwnrIdfyNo;
	}
	public String getNpSgntInfo() {
		return npSgntInfo;
	}
	public void setNpSgntInfo(String npSgntInfo) {
		this.npSgntInfo = npSgntInfo;
	}
	public String getPayAsertDt() {
		return payAsertDt;
	}
	public void setPayAsertDt(String payAsertDt) {
		this.payAsertDt = payAsertDt;
	}
	public String getPayAsertAmt() {
		return payAsertAmt;
	}
	public void setPayAsertAmt(String payAsertAmt) {
		this.payAsertAmt = payAsertAmt;
	}
	public String getPayMethCd() {
		return payMethCd;
	}
	public void setPayMethCd(String payMethCd) {
		this.payMethCd = payMethCd;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getNcn() {
		return ncn;
	}
	public void setNcn(String ncn) {
		this.ncn = ncn;
	}
	public String getCtn() {
		return ctn;
	}
	public void setCtn(String ctn) {
		this.ctn = ctn;
	}
	public String getCntplcNo() {
		return cntplcNo;
	}
	public void setCntplcNo(String cntplcNo) {
		this.cntplcNo = cntplcNo;
	}
	public String getItgOderWhyCd() {
		return itgOderWhyCd;
	}
	public void setItgOderWhyCd(String itgOderWhyCd) {
		this.itgOderWhyCd = itgOderWhyCd;
	}
	public String getAftmnIncInCd() {
		return aftmnIncInCd;
	}
	public void setAftmnIncInCd(String aftmnIncInCd) {
		this.aftmnIncInCd = aftmnIncInCd;
	}
	public String getApyRelTypeCd() {
		return apyRelTypeCd;
	}
	public void setApyRelTypeCd(String apyRelTypeCd) {
		this.apyRelTypeCd = apyRelTypeCd;
	}
	public String getCustTchMediCd() {
		return custTchMediCd;
	}
	public void setCustTchMediCd(String custTchMediCd) {
		this.custTchMediCd = custTchMediCd;
	}
	public String getSmsRcvYn() {
		return smsRcvYn;
	}
	public void setSmsRcvYn(String smsRcvYn) {
		this.smsRcvYn = smsRcvYn;
	}
	public String getAppEventCd() {
		return appEventCd;
	}
	public void setAppEventCd(String appEventCd) {
		this.appEventCd = appEventCd;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getVoName() {
		return voName;
	}
	public void setVoName(String voName) {
		this.voName = voName;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getWantTlphNo() {
		return wantTlphNo;
	}
	public void setWantTlphNo(String wantTlphNo) {
		this.wantTlphNo = wantTlphNo;
	}
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getAdditionKey() {
		return additionKey;
	}
	public void setAdditionKey(String additionKey) {
		this.additionKey = additionKey;
	}
	public String getNstepUserId() {
		return nstepUserId;
	}
	public void setNstepUserId(String nstepUserId) {
		this.nstepUserId = nstepUserId;
	}


}
