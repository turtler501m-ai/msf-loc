package com.ktis.mcpif.mPlatform.vo;

import java.io.Serializable;

import com.ktis.mcpif.common.KisaSeedUtil;

public class MPlatformResVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String mvnoOrdNo;                        /* MVNO오더번호(RES_NO) */
    private String slsCmpnCd;                        /* 사업자코드(KIS) */
    private String custTypeCd;                        /* 고객유형코드 */
    private String custIdntNoIndCd;                    /* 고객식별번호구분코드 */
    private String custIdntNo;                        /* 고객식별번호 */
    private String crprNo;                            /* 법인번호 */
    private String custNm;                            /* 고객명 */
    private String cntrUseCd;                        /* 계약용도코드(R:일반, I:선불)*/
    private String instYn;                            /* 할부여부 */
    private String scnhndPhonInstYn;                /* 중고폰할부여부 */
    private String myslAgreYn;                        /* 본인동의여부 */
    private String nativeRlnamAthnEvdnPprCd;        /* 내국인실명인증증빙서류코드 */
    private String athnRqstcustCntplcNo;            /* 인증요청고객연락번호 */
    private String rsdcrtIssuDate;                    /* 주민증발급일자 */
    private String lcnsNo;                            /* 운전면허번호 */
    private String lcnsRgnCd;                        /* 운전면허지역코드 */
    private String mrtrPrsnNo;                        /* 유공자번호 */
    private String nationalityCd;                    /* 국적코드(외국인) */
    private String fornBrthDate;                    /* 외국인생년월일 */
    private String crdtInfoAgreYn = "Y";            /* 신용정보동의(Y) */
    private String indvInfoInerPrcuseAgreYn = "Y";    /* 개인정보내부활용동의(Y) */
    private String cnsgInfoAdvrRcvAgreYn;            /* 위탁정보광고수신동의 */
    private String othcmpInfoAdvrRcvAgreYn = "N";    /* 타사정보광고수신동의 */
    private String othcmpInfoAdvrCnsgAgreYn = "N";    /* 타사정보광고위탁동의 */
    private String grpAgntBindSvcSbscAgreYn = "N";    /* 그룹사결합서비스가입동의 */
    private String cardInsrPrdcAgreYn = "N";        /* 카드보험상품동의 */
    private String olngDscnHynmtrAgreYn = "N";        /* 주유할인현대차동의 */
    private String wlfrDscnAplyAgreYn = "N";        /* 복지할인신청동의 */
    private String spamPrvdAgreYn = "N";            /* 스팸제공동의 */
    private String prttlpStlmUseAgreYn = "N";        /* 이동전화결제이용동의 */
    private String prttlpStlmPwdUseAgreYn = "N";    /* 이동전화결제비밀번호이용동의 */
    private String wrlnTlphNo;                        /* 유선전화번호 */
    private String tlphNo;                            /* 이동전화번호 */
    private String rprsPrsnNm;                        /* 대표자 */
    private String upjnCd;                            /* 업종코드 */
    private String bcuSbst;                            /* 업태코드 */
    private String zipNo;                            /* 우편번호 */
    private String fndtCntplcSbst;                    /* 기본주소 */
    private String mntCntplcSbst;                    /* 상세주소 */
    private String brthDate;                        /* 생년월일 */
    private String brthNnpIndCd;                    /* 음력양력구분 */
    private String jobCd;                            /* 직업코드 */
    private String emlAdrsNm;                        /* 이메일주소 */
    private String lstdIndCd;                        /* 상장구분코드 */
    private String emplCnt;                            /* 사원수 */
    private String slngAmt;                            /* 매출액 */
    private String cptlAmnt;                        /* 자본금액 */
    private String crprUpjnCd;                        /* 법인업종코드 */
    private String crprBcuSbst;                        /* 법인업태내용 */
    private String crprZipNo;                        /* 법인우편번호 */
    private String crprFndtCntplcSbst;                /* 법인기본주소 */
    private String crprMntCntplcSbst;                /* 법인상세주소 */
    private String custInfoChngYn;                    /* 고객정보변경여부 */
    private String m2mHndsetYn;                        /* M2M단말여부 */
    private String orderTypeCd;                        /* 오더유형 NAC:신규, MNP:번이 */
    private String npTlphNo;                        /* 번이전화번호 */
    private String indvBizrYn;                        /* 개인사업자여부 */
    private String bchngNpCommCmpnCd;                /* 변경전번이사업자코드 */
    private String athnItemCd;                        /* 인증항목코드 */
    private String athnSbst;                        /* 인증항목값 */
    private String npRstrtnContYn;                    /* 번호이동제한예외여부 */
    private String ytrpaySoffAgreYn;                /* 해지미환급금상계동의 */
    private String osstOrdNo;                        /* OSST오더번호 */
    private String asgnAgncId;                        /* 할당대리점ID */
    private String asgnAgncYn;                        /* 할당번호조회여부 */
    private String cntryCd = "KOR";                    /* 국가코드(KOR) */
    private String custNo;                            /* 고객번호 */
    private String inqrBase = "1";                    /* 조회페이지 */
    private String inqrCascnt = "10";                /* 조회카운트 */
    private String nowSvcIndCd = "03";                /* 2G/3G구분(03 고정) */
    private String searchGubun;                        /* 조회구분 */
    private String arPrGubun;                        /* 예약선호번호구분 */
    private String tlphNoChrcCd = "GEN";            /* 전화번호특성코드(GEN 고정) */
    private String tlphNoIndCd = "01";                /* 전화번호구분코드(01 고정) */
    private String tlphNoPtrn;                        /* 번호조회패턴 */
    private String tlphNoUseCd;                        /* 번호사용용도 */
    private String tlphNoUseMntCd;                    /* 번호사용상세(선불인 경우 I 입력) */
    private String gubun;                            /* 업무구분코드 */
    private String tlphNoStatChngRsnCd;                /* 전화번호상태변경사유(RSV:예약, RRS:예약취소) */
    private String tlphNoStatCd;                    /* 전화번호상태(AR:예약, AA:취소) */
    private String mnpIndCd;                        /* 번호이동구분코드(번호이동 필수, I) */
    private String encdTlphNo;                        /* 암호화전화번호(NU1 결과값) */
    private String mpngTlphNoYn;                    /* 매핑전화번호여부 */
    private String billAcntNo;                        /* 청구계정번호 */
    private String rqsshtPprfrmCd;                    /* 청구서양식코드 */
    private String rqsshtTlphNo;                    /* 청구서수신전화번호 */
    private String rqsshtEmlAdrsNm;                    /* 청구서이메일주소 */
    private String billZipNo;                        /* 청구지우편번호 */
    private String billFndtCntplcSbst;                /* 청구지기본주소 */
    private String billMntCntplcSbst;                /* 청구지상세주소 */
    private String blpymMthdCd;                        /* 납부방법코드 */
    private String duedatDateIndCd;                    /* 납기일자구분 */
    private String crdtCardExprDate;                /* 신용카드만기일자 */
    private String crdtCardKindCd;                    /* 신용카드종류 */
    private String bankCd;                            /* 은행코드 */
    private String blpymMthdIdntNo;                    /* 납부방법식별번호(카드번호/계좌번호) */
    private String blpymCustNm;                        /* 납부고객명 */
    private String blpymCustIdntNo;                    /* 납부고객식별번호 */
    private String blpymMthdIdntNoHideYn;            /* 납부방법식별번호숨김여부(Y:숨김,N:노출) */
    private String bankSkipYn;                        /* 은행건너띄기여부(Y:건너띄기,N:건너띄지않음) */
    private String agreIndCd;                        /* 동의자료코드(01:서면, 02:공인인증, 03:일반인증, 04:녹취, 05:ARS) */
    private String myslAthnTypeCd;                    /* 본인인증타입코드(일반인증인 경우, 01:SMS, 02:IPIN, 03:신용카드, 04:범용공인인증) */
    private String billAtchExclYn;                    /* 청구첨부제외여부(Y:동봉물제외, N:동봉물포함) */
    private String rqsshtTlphNoHideYn;                /* 청구서전화번호숨김여부(Y:숨김, N:노출) */
    private String rqsshtDsptYn;                    /* 청구서발송여부(Y:발송, N:미발송) */
    private String enclBillTrmnYn;                    /* 동봉청구해지여부(Y:동봉청구해지, N:해지안함) */
    private String realUseCustNm;                    /* 실사용고객명(법인사업자인 경우) */
    private String mngmAgncId;                        /* 관리대리점ID(AGENT_CODE) */
    private String cntpntCd;                        /* 접점코드(CNTPNT_SHOP_ID) */
    private String slsPrsnId;                        /* 판매자ID(SHOP_USM_ID) */
    private String iccId;                            /* ICC ID(유심번호) */
    private String intmMdlId;                        /* 기기모델ID */
    private String intmSrlNo;                        /* 기기일련번호 */
    private String usimOpenYn;                        /* 유심개통여부(Y:USIM단독, N:단말개통) */
    private String spclSlsNo;                        /* 특별판매코드 */
    private String agntRltnCd;                        /* 대리인관계코드(법인필수, 04:위탁대리인, 06:일회성대리인) */
    private String agntBrthDate;                    /* 대리인생일일자(법인필수) */
    private String agntCustNm;                        /* 대리인명 */
    private String homeTlphNo;                        /* 자택전화번호 */
    private String wrkplcTlphNo;                    /* 직장전화번호 */
    private String prttlpNo;                        /* 이동전화번호 */
    private String prdcCd;                            /* 상품코드 */
    private String prdcTypeCd;                        /* 상품타입코드 */
    private String spnsDscnTypeCd;                    /* 스폰서할인유형코드(KD,PM) */
    private String agncSupotAmnt;                    /* 대리점지원금액 */
    private String enggMnthCnt;                        /* 약정개월수 */
    private String hndsetInstAmnt;                    /* 단말기분납금액(단말할부시 필수) */
    private String hndsetPrpyAmnt;                    /* 단말기선납금액 */
    private String instMnthCnt;                        /* 분납개월수 */
    private String usimPymnMthdCd;                    /* 유심수납방법(R:즉납, B:후청구, N:비구매) */
    private String sbscstPymnMthdCd;                /* 가입비수납방법(R:즉납, I:할부, P:면제) */
    private String sbscstImpsExmpRsnCd;                /* 가입비면제사유 */
    private String bondPrsrFeePymnMthdCd;            /* 채권보전료수납방법(R:즉납, B:익월청구) */
    private String sbscPrtlstRcvEmlAdrsNm;            /* 가입내역서수신이메일주소 */
    private String npFeePayMethCd;                    /* 번호이동수수료수납방법(N:즉납, Y:후청구) */
    private String npBillMethCd;                    /* 타사미청구금액청구방법(N:즉납, Y:후청구, X:전사업자후청구) */
    private String npHndsetInstaDuratDivCd;            /* 번호이동단말기분납지속구분, 번호이동필수(1:완납, 2:분납지속(LMS미청구), 3:분납지속(LMS청구)) */
    private String rfundNpBankCd;                    /* 번호이동환불은행 */
    private String rfundBankBnkacnNo;                /* 번호이동환불계좌번호 */
    private String npTotRmnyAmt;                    /* 번호이동총수납금액 */
    private String npCashRmnyAmt;                    /* 번호이동현금수납금액 */
    private String npCcardRmnyAmt;                    /* 번호이동카드수납액 */
    private String npRmnyMethCd;                    /* 번호이동수납방법코드 */
    private String npHndsetInstaLmsTlphNo;            /* 번호이동단말기분납문자명세서전화번호, 번호이동 가입시 단말기분납지속(LMS청구)인 경우 필수 */
    private String npCcardNo;                        /* 번호이동카드번호, 수납방법 카드인 경우 필수 */
    private String npCcardExpirYm;                    /* 번호이동카드유효기간, 수납방법 카드인 경우 필수 */
    private String npInslMonsNum;                    /* 번호이동할부개월수, 수납방법 카드인 경우 필수 */
    private String npCcardSnctTypeCd;                /* 번호이동결제유형코드, 수납방법 카드인 경우 필수 */
    private String npCcardOwnrIdfyNo;                /* 번호이동카드명의인식별번호, 수납방법 카드인 경우 필수 */
    private String npSgntInfo;                        /* 번호이동서명정보, 수납방법 카드인 경우 필수 */
    private String payAsertDt;                        /* 납부주장일자 */
    private String payAsertAmt;                        /* 납부주장금액 */
    private String payMethCd;                        /* 납부방법코드 */
    private String usimKindsCd;                        /* 유심종류 09 eSIM */
    private String esimPhoneId;                        /* ESIM_PHONE_ID */
    private String esimUiccId;                      /* EID	eSIM용. eSIM사용==Y일 경우 필수 */
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
    private String wantTlphNo;        // 희망번호
    //부가서비스처리용
    private String requestKey;
    private String additionKey;
    // KOS 사용자ID
    private String nstepUserId;

    private String svcContId; // 서비스계약번호
    private String oderTypeCd; // 오더유형코드
    private String usimChgRsnCd; // 유심변경 사유코드

    private String prntsCtn; //eSIM watch 모회선 전화번호

    private String myslfAthnYn;         // 본인인증 여부
    private String ipinCi;              // 본인인증 후 취득한 CI값 (청소년인 경우 대리인 CI)
    private String onlineAthnDivCd;     // 본인인증 수단

    private String fnncDealAgreeYn;     // 금융거래시 KT 고객 추가 금융 혜택을 위한 정보 제공 동의

    private boolean isEncrypt = false; //암호화 여부

    /*사전승낙제(K-Note) 연동 param 시작*/
    private String retvStrtDt;       //조회시작일시
    private String retvEndDt;        //조회
    private String svcApyTrtStatCd;  //처리상태코드
    private String frmpapId;         //서식지아이디
    private String frmpapStatCd;     //서식지상태변경코드
    private String oderTrtOrgId;     //오더 처리 조직아이디

    private String retvStDt;         //yyyyMMdd(현재일시보다 큰 경우 불가)
    private String retvFnsDt;        //yyyyMMdd(현재일시보다 큰 경우 불가) *조회기간 최대 30일
    private String oderTrtId;        //오더 처리자 아이디 ( = KOS ID)
    private String retvSeq;          //조회시작번호(ex. 0) List 에 나오는 최초 Row의 번호 미 입력시 0
    private String retvCascnt;       //조회건수    (ex. 30) => Max 30  List 에 나오는 데이터 Row 수 미 입력시 30
    private String itgFrmpapSeq;     //무서식지 오더 데이터 조회(FS3)를 통해 취득 ex)20240323102606X667387804
    private String scanDt;           //YYYYMMDD(현재일 보다 큰 경우 불가)   신분증 스캔일자
    private String onlineOfflnDivCd; //온라인오프라인구분코드 (ONLINE:온라인 채널(비대면), OFFLINE:오프라인 채널(대면))
    private String orgId;            //대리점ID
    private String cpntId;           //접점ID
    private String retvCdVal;        //조회코드값 신분증유형코드 (REGID:주민등록증, DRIVE:운전면허증, HENDI:장애인등록증, MERIT:국가유공자증, NAMEC:대한민국여권)
    private String smsRcvTelNo;      //SMS수신전화번호 (비대면일때 SMS로 URL을 받을 전화번호)
    private String fathSbscDivCd;    //안면인증 가입 구분 코드 (1:신규가입, 2:번호이동, 3:명의변경, 4:기기변경, 5:고객정보변경)
    private String fathTransacId;    //안면인증트랜잭션아이디
    private String retvDivCd;        //조회구분코드 (1 : 안면인증 트랜잭션 아이디로 조회, 2 : 서식지아이디의 최종 안면인증 내역 조회)

    /*명의변경관련*/
    private String mcnStatRsnCd;            //명변사유코드
    private String usimSuccYn;              //USIM 승계 여부
    private String ftrNewParam;             //상품파람
    private String realUseCustBrthDate;     //실사용자 생년월일
    private String cstmrType;               //고객타입(NA,NM,FN,JP)
    private String agntCustIdfyNoType;      //법정대리인 식별번호 종류
    private String agntIdfyNoVal;           //법정대리인 고객식별번호
    private String agntPersonSexDiv;        //법정대리인 성별
    private String agntAgreYn;              //법정대리인 정보조회 동의여부
    private String agntTelAthn;             //법정대리인 연락처 종류
    private String agntTelNo;               //대리인 연락처
    private String agntTypeCd;              //법정대리인 유형
    private String agntNationalityCd;       //법정대리인 국적코드
    private String agntRlnamAthnEvdnPprCd;  //법정대리인실명인증증빙서류코드
    private String agntLicnsRgnCd;          //법정대리인 면허지역코드
    private String agntLicnsNo;             //법정대리인 면허번호
    private String agntRsdcrtIssuDate;      //법정대리인 식별번호 발급일자
    private String photoAthnTxnSeq;         //사진인증내역일련번호
    private String rcvCustNo;               //양수인고객번호
    private String rcvBillAcntNo;           //양수인청구계정번호
    private String docConfirmYn;            //증빙서류 확인 여부
    private String followupYn;              //사후점검 이행 여부
    private String chgRqsshtEmlAdrsNm;      //청구서이메일주소명

    private String indvLoInfoPrvAgreeYn;    //개인위치정보제공동의여부

    public String getMvnoOrdNo() {
        if (mvnoOrdNo == null) {
            return "";
        }
        return mvnoOrdNo;
    }

    public void setMvnoOrdNo(String mvnoOrdNo) {
        this.mvnoOrdNo = mvnoOrdNo;
    }

    public String getSlsCmpnCd() {
        if (slsCmpnCd == null) {
            return "";
        }
        return slsCmpnCd;
    }

    public void setSlsCmpnCd(String slsCmpnCd) {
        this.slsCmpnCd = slsCmpnCd;
    }

    public String getCustTypeCd() {
        if (custTypeCd == null) {
            return "";
        }
        return custTypeCd;
    }

    public void setCustTypeCd(String custTypeCd) {
        this.custTypeCd = custTypeCd;
    }

    public String getCustIdntNoIndCd() {
        if (custIdntNoIndCd == null) {
            return "";
        }
        return custIdntNoIndCd;
    }

    public void setCustIdntNoIndCd(String custIdntNoIndCd) {
        this.custIdntNoIndCd = custIdntNoIndCd;
    }

    public String getCustIdntNo() {
        if (custIdntNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(custIdntNo);
        }

        return custIdntNo;
    }

    public void setCustIdntNo(String custIdntNo) {
        this.custIdntNo = custIdntNo;
    }

    public String getCrprNo() {
        if (crprNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(crprNo);
        }

        return crprNo;
    }

    public void setCrprNo(String crprNo) {
        this.crprNo = crprNo;
    }

    public String getCustNm() {
        if (custNm == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(custNm);
        }

        return custNm;
    }

    public void setCustNm(String custNm) {
        this.custNm = custNm;
    }

    public String getCntrUseCd() {
        if (cntrUseCd == null) {
            return "";
        }
        return cntrUseCd;
    }

    public void setCntrUseCd(String cntrUseCd) {
        this.cntrUseCd = cntrUseCd;
    }

    public String getInstYn() {
        if (instYn == null) {
            return "";
        }
        return instYn;
    }

    public void setInstYn(String instYn) {
        this.instYn = instYn;
    }

    public String getScnhndPhonInstYn() {
        if (scnhndPhonInstYn == null) {
            return "";
        }
        return scnhndPhonInstYn;
    }

    public void setScnhndPhonInstYn(String scnhndPhonInstYn) {
        this.scnhndPhonInstYn = scnhndPhonInstYn;
    }

    public String getMyslAgreYn() {
        if (myslAgreYn == null) {
            return "";
        }
        return myslAgreYn;
    }

    public void setMyslAgreYn(String myslAgreYn) {
        this.myslAgreYn = myslAgreYn;
    }

    public String getNativeRlnamAthnEvdnPprCd() {
        if (nativeRlnamAthnEvdnPprCd == null) {
            return "";
        }
        return nativeRlnamAthnEvdnPprCd;
    }

    public void setNativeRlnamAthnEvdnPprCd(String nativeRlnamAthnEvdnPprCd) {
        this.nativeRlnamAthnEvdnPprCd = nativeRlnamAthnEvdnPprCd;
    }

    public String getAthnRqstcustCntplcNo() {
        if (athnRqstcustCntplcNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(athnRqstcustCntplcNo);
        }

        return athnRqstcustCntplcNo;
    }

    public void setAthnRqstcustCntplcNo(String athnRqstcustCntplcNo) {
        this.athnRqstcustCntplcNo = athnRqstcustCntplcNo;
    }

    public String getRsdcrtIssuDate() {
        if (rsdcrtIssuDate == null) {
            return "";
        }
        return rsdcrtIssuDate;
    }

    public void setRsdcrtIssuDate(String rsdcrtIssuDate) {
        this.rsdcrtIssuDate = rsdcrtIssuDate;
    }

    public String getLcnsNo() {
        if (lcnsNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(lcnsNo);
        }

        return lcnsNo;
    }

    public void setLcnsNo(String lcnsNo) {
        this.lcnsNo = lcnsNo;
    }

    public String getLcnsRgnCd() {
        if (lcnsRgnCd == null) {
            return "";
        }
        return lcnsRgnCd;
    }

    public void setLcnsRgnCd(String lcnsRgnCd) {
        this.lcnsRgnCd = lcnsRgnCd;
    }

    public String getMrtrPrsnNo() {
        if (mrtrPrsnNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(mrtrPrsnNo);
        }

        return mrtrPrsnNo;
    }

    public void setMrtrPrsnNo(String mrtrPrsnNo) {
        this.mrtrPrsnNo = mrtrPrsnNo;
    }

    public String getNationalityCd() {
        if (nationalityCd == null) {
            return "";
        }
        return nationalityCd;
    }

    public void setNationalityCd(String nationalityCd) {
        this.nationalityCd = nationalityCd;
    }

    public String getFornBrthDate() {
        if (fornBrthDate == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(fornBrthDate);
        }

        return fornBrthDate;
    }

    public void setFornBrthDate(String fornBrthDate) {
        this.fornBrthDate = fornBrthDate;
    }

    public String getCrdtInfoAgreYn() {
        if (crdtInfoAgreYn == null) {
            return "";
        }
        return crdtInfoAgreYn;
    }

    public void setCrdtInfoAgreYn(String crdtInfoAgreYn) {
        this.crdtInfoAgreYn = crdtInfoAgreYn;
    }

    public String getIndvInfoInerPrcuseAgreYn() {
        if (indvInfoInerPrcuseAgreYn == null) {
            return "";
        }
        return indvInfoInerPrcuseAgreYn;
    }

    public void setIndvInfoInerPrcuseAgreYn(String indvInfoInerPrcuseAgreYn) {
        this.indvInfoInerPrcuseAgreYn = indvInfoInerPrcuseAgreYn;
    }

    public String getCnsgInfoAdvrRcvAgreYn() {
        if (cnsgInfoAdvrRcvAgreYn == null) {
            return "";
        }
        return cnsgInfoAdvrRcvAgreYn;
    }

    public void setCnsgInfoAdvrRcvAgreYn(String cnsgInfoAdvrRcvAgreYn) {
        this.cnsgInfoAdvrRcvAgreYn = cnsgInfoAdvrRcvAgreYn;
    }

    public String getOthcmpInfoAdvrRcvAgreYn() {
        if (othcmpInfoAdvrRcvAgreYn == null) {
            return "";
        }
        return othcmpInfoAdvrRcvAgreYn;
    }

    public void setOthcmpInfoAdvrRcvAgreYn(String othcmpInfoAdvrRcvAgreYn) {
        this.othcmpInfoAdvrRcvAgreYn = othcmpInfoAdvrRcvAgreYn;
    }

    public String getOthcmpInfoAdvrCnsgAgreYn() {
        if (othcmpInfoAdvrCnsgAgreYn == null) {
            return "";
        }
        return othcmpInfoAdvrCnsgAgreYn;
    }

    public void setOthcmpInfoAdvrCnsgAgreYn(String othcmpInfoAdvrCnsgAgreYn) {
        this.othcmpInfoAdvrCnsgAgreYn = othcmpInfoAdvrCnsgAgreYn;
    }

    public String getGrpAgntBindSvcSbscAgreYn() {
        if (grpAgntBindSvcSbscAgreYn == null) {
            return "";
        }
        return grpAgntBindSvcSbscAgreYn;
    }

    public void setGrpAgntBindSvcSbscAgreYn(String grpAgntBindSvcSbscAgreYn) {
        this.grpAgntBindSvcSbscAgreYn = grpAgntBindSvcSbscAgreYn;
    }

    public String getCardInsrPrdcAgreYn() {
        if (cardInsrPrdcAgreYn == null) {
            return "";
        }
        return cardInsrPrdcAgreYn;
    }

    public void setCardInsrPrdcAgreYn(String cardInsrPrdcAgreYn) {
        this.cardInsrPrdcAgreYn = cardInsrPrdcAgreYn;
    }

    public String getOlngDscnHynmtrAgreYn() {
        if (olngDscnHynmtrAgreYn == null) {
            return "";
        }
        return olngDscnHynmtrAgreYn;
    }

    public void setOlngDscnHynmtrAgreYn(String olngDscnHynmtrAgreYn) {
        this.olngDscnHynmtrAgreYn = olngDscnHynmtrAgreYn;
    }

    public String getWlfrDscnAplyAgreYn() {
        if (wlfrDscnAplyAgreYn == null) {
            return "";
        }
        return wlfrDscnAplyAgreYn;
    }

    public void setWlfrDscnAplyAgreYn(String wlfrDscnAplyAgreYn) {
        this.wlfrDscnAplyAgreYn = wlfrDscnAplyAgreYn;
    }

    public String getSpamPrvdAgreYn() {
        if (spamPrvdAgreYn == null) {
            return "";
        }
        return spamPrvdAgreYn;
    }

    public void setSpamPrvdAgreYn(String spamPrvdAgreYn) {
        this.spamPrvdAgreYn = spamPrvdAgreYn;
    }

    public String getPrttlpStlmUseAgreYn() {
        if (prttlpStlmUseAgreYn == null) {
            return "";
        }
        return prttlpStlmUseAgreYn;
    }

    public void setPrttlpStlmUseAgreYn(String prttlpStlmUseAgreYn) {
        this.prttlpStlmUseAgreYn = prttlpStlmUseAgreYn;
    }

    public String getPrttlpStlmPwdUseAgreYn() {
        if (prttlpStlmPwdUseAgreYn == null) {
            return "";
        }
        return prttlpStlmPwdUseAgreYn;
    }

    public void setPrttlpStlmPwdUseAgreYn(String prttlpStlmPwdUseAgreYn) {
        this.prttlpStlmPwdUseAgreYn = prttlpStlmPwdUseAgreYn;
    }

    public String getWrlnTlphNo() {
        if (wrlnTlphNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(wrlnTlphNo);
        }

        return wrlnTlphNo;
    }

    public void setWrlnTlphNo(String wrlnTlphNo) {
        this.wrlnTlphNo = wrlnTlphNo;
    }

    public String getTlphNo() {
        if (tlphNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(tlphNo);
        }

        return tlphNo;
    }

    public void setTlphNo(String tlphNo) {
        this.tlphNo = tlphNo;
    }

    public String getRprsPrsnNm() {
        if (rprsPrsnNm == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(rprsPrsnNm);
        }

        return rprsPrsnNm;
    }

    public void setRprsPrsnNm(String rprsPrsnNm) {
        this.rprsPrsnNm = rprsPrsnNm;
    }

    public String getUpjnCd() {
        if (upjnCd == null) {
            return "";
        }
        return upjnCd;
    }

    public void setUpjnCd(String upjnCd) {
        this.upjnCd = upjnCd;
    }

    public String getBcuSbst() {
        if (bcuSbst == null) {
            return "";
        }
        return bcuSbst;
    }

    public void setBcuSbst(String bcuSbst) {
        this.bcuSbst = bcuSbst;
    }

    public String getZipNo() {
        if (zipNo == null) {
            return "";
        }
        return zipNo;
    }

    public void setZipNo(String zipNo) {
        this.zipNo = zipNo;
    }

    public String getFndtCntplcSbst() {
        if (fndtCntplcSbst == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(fndtCntplcSbst);
        }

        return fndtCntplcSbst;
    }

    public void setFndtCntplcSbst(String fndtCntplcSbst) {
        this.fndtCntplcSbst = fndtCntplcSbst;
    }

    public String getMntCntplcSbst() {
        if (mntCntplcSbst == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(mntCntplcSbst);
        }

        return mntCntplcSbst;
    }

    public void setMntCntplcSbst(String mntCntplcSbst) {
        this.mntCntplcSbst = mntCntplcSbst;
    }

    public String getBrthDate() {
        if (brthDate == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(brthDate);
        }

        return brthDate;
    }

    public void setBrthDate(String brthDate) {
        this.brthDate = brthDate;
    }

    public String getBrthNnpIndCd() {
        if (brthNnpIndCd == null) {
            return "";
        }
        return brthNnpIndCd;
    }

    public void setBrthNnpIndCd(String brthNnpIndCd) {
        this.brthNnpIndCd = brthNnpIndCd;
    }

    public String getJobCd() {

        if (jobCd == null) {
            return "";
        }
        return jobCd;
    }

    public void setJobCd(String jobCd) {
        this.jobCd = jobCd;
    }

    public String getEmlAdrsNm() {
        if (emlAdrsNm == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(emlAdrsNm);
        }

        return emlAdrsNm;
    }

    public void setEmlAdrsNm(String emlAdrsNm) {
        this.emlAdrsNm = emlAdrsNm;
    }

    public String getLstdIndCd() {

        if (lstdIndCd == null) {
            return "";
        }
        return lstdIndCd;
    }

    public void setLstdIndCd(String lstdIndCd) {
        this.lstdIndCd = lstdIndCd;
    }

    public String getEmplCnt() {

        if (emplCnt == null) {
            return "";
        }
        return emplCnt;
    }

    public void setEmplCnt(String emplCnt) {
        this.emplCnt = emplCnt;
    }

    public String getSlngAmt() {

        if (slngAmt == null) {
            return "";
        }
        return slngAmt;
    }

    public void setSlngAmt(String slngAmt) {
        this.slngAmt = slngAmt;
    }

    public String getCptlAmnt() {

        if (cptlAmnt == null) {
            return "";
        }
        return cptlAmnt;
    }

    public void setCptlAmnt(String cptlAmnt) {
        this.cptlAmnt = cptlAmnt;
    }

    public String getCrprUpjnCd() {

        if (crprUpjnCd == null) {
            return "";
        }
        return crprUpjnCd;
    }

    public void setCrprUpjnCd(String crprUpjnCd) {
        this.crprUpjnCd = crprUpjnCd;
    }

    public String getCrprBcuSbst() {

        if (crprBcuSbst == null) {
            return "";
        }
        return crprBcuSbst;
    }

    public void setCrprBcuSbst(String crprBcuSbst) {
        this.crprBcuSbst = crprBcuSbst;
    }

    public String getCrprZipNo() {

        if (crprZipNo == null) {
            return "";
        }
        return crprZipNo;
    }

    public void setCrprZipNo(String crprZipNo) {
        this.crprZipNo = crprZipNo;
    }

    public String getCrprFndtCntplcSbst() {
        if (crprFndtCntplcSbst == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(crprFndtCntplcSbst);
        }

        return crprFndtCntplcSbst;
    }

    public void setCrprFndtCntplcSbst(String crprFndtCntplcSbst) {
        this.crprFndtCntplcSbst = crprFndtCntplcSbst;
    }

    public String getCrprMntCntplcSbst() {
        if (crprMntCntplcSbst == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(crprMntCntplcSbst);
        }

        return crprMntCntplcSbst;
    }

    public void setCrprMntCntplcSbst(String crprMntCntplcSbst) {
        this.crprMntCntplcSbst = crprMntCntplcSbst;
    }

    public String getCustInfoChngYn() {

        if (custInfoChngYn == null) {
            return "";
        }
        return custInfoChngYn;
    }

    public void setCustInfoChngYn(String custInfoChngYn) {
        this.custInfoChngYn = custInfoChngYn;
    }

    public String getM2mHndsetYn() {

        if (m2mHndsetYn == null) {
            return "";
        }
        return m2mHndsetYn;
    }

    public void setM2mHndsetYn(String m2mHndsetYn) {
        this.m2mHndsetYn = m2mHndsetYn;
    }

    public String getOrderTypeCd() {

        if (orderTypeCd == null) {
            return "";
        }
        return orderTypeCd;
    }

    public void setOrderTypeCd(String orderTypeCd) {
        this.orderTypeCd = orderTypeCd;
    }

    public String getNpTlphNo() {
        if (npTlphNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(npTlphNo);
        }

        return npTlphNo;
    }

    public void setNpTlphNo(String npTlphNo) {
        this.npTlphNo = npTlphNo;
    }

    public String getIndvBizrYn() {

        if (indvBizrYn == null) {
            return "";
        }
        return indvBizrYn;
    }

    public void setIndvBizrYn(String indvBizrYn) {
        this.indvBizrYn = indvBizrYn;
    }

    public String getBchngNpCommCmpnCd() {

        if (bchngNpCommCmpnCd == null) {
            return "";
        }
        return bchngNpCommCmpnCd;
    }

    public void setBchngNpCommCmpnCd(String bchngNpCommCmpnCd) {
        this.bchngNpCommCmpnCd = bchngNpCommCmpnCd;
    }

    public String getAthnItemCd() {

        if (athnItemCd == null) {
            return "";
        }
        return athnItemCd;
    }

    public void setAthnItemCd(String athnItemCd) {
        this.athnItemCd = athnItemCd;
    }

    public String getAthnSbst() {

        if (athnSbst == null) {
            return "";
        }
        return athnSbst;
    }

    public void setAthnSbst(String athnSbst) {
        this.athnSbst = athnSbst;
    }

    public String getNpRstrtnContYn() {

        if (npRstrtnContYn == null) {
            return "";
        }
        return npRstrtnContYn;
    }

    public void setNpRstrtnContYn(String npRstrtnContYn) {
        this.npRstrtnContYn = npRstrtnContYn;
    }

    public String getYtrpaySoffAgreYn() {

        if (ytrpaySoffAgreYn == null) {
            return "";
        }
        return ytrpaySoffAgreYn;
    }

    public void setYtrpaySoffAgreYn(String ytrpaySoffAgreYn) {
        this.ytrpaySoffAgreYn = ytrpaySoffAgreYn;
    }

    public String getOsstOrdNo() {

        if (osstOrdNo == null) {
            return "";
        }
        return osstOrdNo;
    }

    public void setOsstOrdNo(String osstOrdNo) {
        this.osstOrdNo = osstOrdNo;
    }

    public String getAsgnAgncId() {

        if (asgnAgncId == null) {
            return "";
        }
        return asgnAgncId;
    }

    public void setAsgnAgncId(String asgnAgncId) {
        this.asgnAgncId = asgnAgncId;
    }

    public String getAsgnAgncYn() {

        if (asgnAgncYn == null) {
            return "";
        }
        return asgnAgncYn;
    }

    public void setAsgnAgncYn(String asgnAgncYn) {
        this.asgnAgncYn = asgnAgncYn;
    }

    public String getCntryCd() {

        if (cntryCd == null) {
            return "";
        }
        return cntryCd;
    }

    public void setCntryCd(String cntryCd) {
        this.cntryCd = cntryCd;
    }

    public String getCustNo() {

        if (custNo == null) {
            return "";
        }
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getInqrBase() {

        if (inqrBase == null) {
            return "";
        }
        return inqrBase;
    }

    public void setInqrBase(String inqrBase) {
        this.inqrBase = inqrBase;
    }

    public String getInqrCascnt() {

        if (inqrCascnt == null) {
            return "";
        }
        return inqrCascnt;
    }

    public void setInqrCascnt(String inqrCascnt) {
        this.inqrCascnt = inqrCascnt;
    }

    public String getNowSvcIndCd() {

        if (nowSvcIndCd == null) {
            return "";
        }
        return nowSvcIndCd;
    }

    public void setNowSvcIndCd(String nowSvcIndCd) {
        this.nowSvcIndCd = nowSvcIndCd;
    }

    public String getSearchGubun() {

        if (searchGubun == null) {
            return "";
        }
        return searchGubun;
    }

    public void setSearchGubun(String searchGubun) {
        this.searchGubun = searchGubun;
    }

    public String getArPrGubun() {

        if (arPrGubun == null) {
            return "";
        }
        return arPrGubun;
    }

    public void setArPrGubun(String arPrGubun) {
        this.arPrGubun = arPrGubun;
    }

    public String getTlphNoChrcCd() {

        if (tlphNoChrcCd == null) {
            return "";
        }
        return tlphNoChrcCd;
    }

    public void setTlphNoChrcCd(String tlphNoChrcCd) {
        this.tlphNoChrcCd = tlphNoChrcCd;
    }

    public String getTlphNoIndCd() {


        if (tlphNoIndCd == null) {
            return "";
        }
        return tlphNoIndCd;

    }

    public void setTlphNoIndCd(String tlphNoIndCd) {
        this.tlphNoIndCd = tlphNoIndCd;
    }

    public String getTlphNoPtrn() {

        if (tlphNoPtrn == null) {
            return "";
        }
        return tlphNoPtrn;
    }

    public void setTlphNoPtrn(String tlphNoPtrn) {
        this.tlphNoPtrn = tlphNoPtrn;
    }

    public String getTlphNoUseCd() {

        if (tlphNoUseCd == null) {
            return "";
        }
        return tlphNoUseCd;
    }

    public void setTlphNoUseCd(String tlphNoUseCd) {
        this.tlphNoUseCd = tlphNoUseCd;
    }

    public String getTlphNoUseMntCd() {

        if (tlphNoUseMntCd == null) {
            return "";
        }
        return tlphNoUseMntCd;
    }

    public void setTlphNoUseMntCd(String tlphNoUseMntCd) {
        this.tlphNoUseMntCd = tlphNoUseMntCd;
    }

    public String getGubun() {

        if (gubun == null) {
            return "";
        }
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;

    }

    public String getTlphNoStatChngRsnCd() {

        if (tlphNoStatChngRsnCd == null) {
            return "";
        }
        return tlphNoStatChngRsnCd;
    }

    public void setTlphNoStatChngRsnCd(String tlphNoStatChngRsnCd) {
        this.tlphNoStatChngRsnCd = tlphNoStatChngRsnCd;
    }

    public String getTlphNoStatCd() {

        if (tlphNoStatCd == null) {
            return "";
        }
        return tlphNoStatCd;
    }

    public void setTlphNoStatCd(String tlphNoStatCd) {
        this.tlphNoStatCd = tlphNoStatCd;
    }

    public String getMnpIndCd() {

        if (mnpIndCd == null) {
            return "";
        }
        return mnpIndCd;
    }

    public void setMnpIndCd(String mnpIndCd) {
        this.mnpIndCd = mnpIndCd;
    }

    public String getEncdTlphNo() {

        if (encdTlphNo == null) {
            return "";
        }
        return encdTlphNo;
    }

    public void setEncdTlphNo(String encdTlphNo) {
        this.encdTlphNo = encdTlphNo;
    }

    public String getMpngTlphNoYn() {

        if (mpngTlphNoYn == null) {
            return "";
        }
        return mpngTlphNoYn;
    }

    public void setMpngTlphNoYn(String mpngTlphNoYn) {
        this.mpngTlphNoYn = mpngTlphNoYn;
    }

    public String getBillAcntNo() {

        if (billAcntNo == null) {
            return "";
        }
        return billAcntNo;
    }

    public void setBillAcntNo(String billAcntNo) {
        this.billAcntNo = billAcntNo;
    }

    public String getRqsshtPprfrmCd() {

        if (rqsshtPprfrmCd == null) {
            return "";
        }
        return rqsshtPprfrmCd;
    }

    public void setRqsshtPprfrmCd(String rqsshtPprfrmCd) {
        this.rqsshtPprfrmCd = rqsshtPprfrmCd;
    }

    public String getRqsshtTlphNo() {
        if (rqsshtTlphNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(rqsshtTlphNo);
        }

        return rqsshtTlphNo;
    }

    public void setRqsshtTlphNo(String rqsshtTlphNo) {
        this.rqsshtTlphNo = rqsshtTlphNo;
    }

    public String getRqsshtEmlAdrsNm() {
        if (rqsshtEmlAdrsNm == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(rqsshtEmlAdrsNm);
        }

        return rqsshtEmlAdrsNm;
    }

    public void setRqsshtEmlAdrsNm(String rqsshtEmlAdrsNm) {
        this.rqsshtEmlAdrsNm = rqsshtEmlAdrsNm;
    }

    public String getBillZipNo() {

        if (billZipNo == null) {
            return "";
        }
        return billZipNo;
    }

    public void setBillZipNo(String billZipNo) {
        this.billZipNo = billZipNo;
    }

    public String getBillFndtCntplcSbst() {
        if (billFndtCntplcSbst == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(billFndtCntplcSbst);
        }

        return billFndtCntplcSbst;
    }

    public void setBillFndtCntplcSbst(String billFndtCntplcSbst) {
        this.billFndtCntplcSbst = billFndtCntplcSbst;
    }

    public String getBillMntCntplcSbst() {
        if (billMntCntplcSbst == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(billMntCntplcSbst);
        }

        return billMntCntplcSbst;
    }

    public void setBillMntCntplcSbst(String billMntCntplcSbst) {
        this.billMntCntplcSbst = billMntCntplcSbst;
    }

    public String getBlpymMthdCd() {

        if (blpymMthdCd == null) {
            return "";
        }
        return blpymMthdCd;
    }

    public void setBlpymMthdCd(String blpymMthdCd) {
        this.blpymMthdCd = blpymMthdCd;
    }

    public String getDuedatDateIndCd() {

        if (duedatDateIndCd == null) {
            return "";
        }
        return duedatDateIndCd;
    }

    public void setDuedatDateIndCd(String duedatDateIndCd) {
        this.duedatDateIndCd = duedatDateIndCd;
    }

    public String getCrdtCardExprDate() {

        if (crdtCardExprDate == null) {
            return "";
        }
        return crdtCardExprDate;
    }

    public void setCrdtCardExprDate(String crdtCardExprDate) {
        this.crdtCardExprDate = crdtCardExprDate;
    }

    public String getCrdtCardKindCd() {

        if (crdtCardKindCd == null) {
            return "";
        }
        return crdtCardKindCd;
    }

    public void setCrdtCardKindCd(String crdtCardKindCd) {
        this.crdtCardKindCd = crdtCardKindCd;
    }

    public String getBankCd() {

        if (bankCd == null) {
            return "";
        }
        return bankCd;
    }

    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    public String getBlpymMthdIdntNo() {
        if (blpymMthdIdntNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(blpymMthdIdntNo);
        }

        return blpymMthdIdntNo;
    }

    public void setBlpymMthdIdntNo(String blpymMthdIdntNo) {
        this.blpymMthdIdntNo = blpymMthdIdntNo;
    }

    public String getBlpymCustNm() {
        if (blpymCustNm == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(blpymCustNm);
        }

        return blpymCustNm;
    }

    public void setBlpymCustNm(String blpymCustNm) {
        this.blpymCustNm = blpymCustNm;
    }

    public String getBlpymCustIdntNo() {
        if (blpymCustIdntNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(blpymCustIdntNo);
        }

        return blpymCustIdntNo;
    }

    public void setBlpymCustIdntNo(String blpymCustIdntNo) {
        this.blpymCustIdntNo = blpymCustIdntNo;
    }

    public String getBlpymMthdIdntNoHideYn() {

        if (blpymMthdIdntNoHideYn == null) {
            return "";
        }
        return blpymMthdIdntNoHideYn;
    }

    public void setBlpymMthdIdntNoHideYn(String blpymMthdIdntNoHideYn) {
        this.blpymMthdIdntNoHideYn = blpymMthdIdntNoHideYn;
    }

    public String getBankSkipYn() {

        if (bankSkipYn == null) {
            return "";
        }
        return bankSkipYn;
    }

    public void setBankSkipYn(String bankSkipYn) {
        this.bankSkipYn = bankSkipYn;
    }

    public String getAgreIndCd() {

        if (agreIndCd == null) {
            return "";
        }
        return agreIndCd;
    }

    public void setAgreIndCd(String agreIndCd) {
        this.agreIndCd = agreIndCd;
    }

    public String getMyslAthnTypeCd() {

        if (myslAthnTypeCd == null) {
            return "";
        }
        return myslAthnTypeCd;
    }

    public void setMyslAthnTypeCd(String myslAthnTypeCd) {
        this.myslAthnTypeCd = myslAthnTypeCd;
    }

    public String getBillAtchExclYn() {

        if (billAtchExclYn == null) {
            return "";
        }
        return billAtchExclYn;
    }

    public void setBillAtchExclYn(String billAtchExclYn) {
        this.billAtchExclYn = billAtchExclYn;
    }

    public String getRqsshtTlphNoHideYn() {

        if (rqsshtTlphNoHideYn == null) {
            return "";
        }
        return rqsshtTlphNoHideYn;
    }

    public void setRqsshtTlphNoHideYn(String rqsshtTlphNoHideYn) {
        this.rqsshtTlphNoHideYn = rqsshtTlphNoHideYn;
    }

    public String getRqsshtDsptYn() {

        if (rqsshtDsptYn == null) {
            return "";
        }
        return rqsshtDsptYn;
    }

    public void setRqsshtDsptYn(String rqsshtDsptYn) {
        this.rqsshtDsptYn = rqsshtDsptYn;
    }

    public String getEnclBillTrmnYn() {

        if (enclBillTrmnYn == null) {
            return "";
        }
        return enclBillTrmnYn;
    }

    public void setEnclBillTrmnYn(String enclBillTrmnYn) {
        this.enclBillTrmnYn = enclBillTrmnYn;
    }

    public String getRealUseCustNm() {
        if (realUseCustNm == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(realUseCustNm);
        }

        return realUseCustNm;
    }

    public void setRealUseCustNm(String realUseCustNm) {
        this.realUseCustNm = realUseCustNm;
    }

    public String getMngmAgncId() {

        if (mngmAgncId == null) {
            return "";
        }
        return mngmAgncId;
    }

    public void setMngmAgncId(String mngmAgncId) {
        this.mngmAgncId = mngmAgncId;
    }

    public String getCntpntCd() {

        if (cntpntCd == null) {
            return "";
        }
        return cntpntCd;
    }

    public void setCntpntCd(String cntpntCd) {
        this.cntpntCd = cntpntCd;
    }

    public String getSlsPrsnId() {

        if (slsPrsnId == null) {
            return "";
        }
        return slsPrsnId;
    }

    public void setSlsPrsnId(String slsPrsnId) {
        this.slsPrsnId = slsPrsnId;
    }

    public String getIccId() {
        if (iccId == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(iccId);
        }

        return iccId;
    }

    public void setIccId(String iccId) {
        this.iccId = iccId;
    }

    public String getIntmMdlId() {

        if (intmMdlId == null) {
            return "";
        }
        return intmMdlId;
    }

    public void setIntmMdlId(String intmMdlId) {
        this.intmMdlId = intmMdlId;
    }

    public String getIntmSrlNo() {
        if (intmSrlNo == null) {
            return "";
        }
        return intmSrlNo;
    }

    public void setIntmSrlNo(String intmSrlNo) {
        this.intmSrlNo = intmSrlNo;
    }

    public String getUsimOpenYn() {

        if (usimOpenYn == null) {
            return "";
        }
        return usimOpenYn;
    }

    public void setUsimOpenYn(String usimOpenYn) {
        this.usimOpenYn = usimOpenYn;
    }

    public String getSpclSlsNo() {

        if (spclSlsNo == null) {
            return "";
        }
        return spclSlsNo;
    }

    public void setSpclSlsNo(String spclSlsNo) {
        this.spclSlsNo = spclSlsNo;
    }

    public String getAgntRltnCd() {

        if (agntRltnCd == null) {
            return "";
        }
        return agntRltnCd;
    }

    public void setAgntRltnCd(String agntRltnCd) {
        this.agntRltnCd = agntRltnCd;
    }

    public String getAgntBrthDate() {
        if (agntBrthDate == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(agntBrthDate);
        }

        return agntBrthDate;
    }

    public void setAgntBrthDate(String agntBrthDate) {
        this.agntBrthDate = agntBrthDate;
    }

    public String getAgntCustNm() {
        if (agntCustNm == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(agntCustNm);
        }

        return agntCustNm;
    }

    public void setAgntCustNm(String agntCustNm) {
        this.agntCustNm = agntCustNm;
    }

    public String getHomeTlphNo() {
        if (homeTlphNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(homeTlphNo);
        }

        return homeTlphNo;
    }

    public void setHomeTlphNo(String homeTlphNo) {
        this.homeTlphNo = homeTlphNo;
    }

    public String getWrkplcTlphNo() {
        if (wrkplcTlphNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(wrkplcTlphNo);
        }

        return wrkplcTlphNo;
    }

    public void setWrkplcTlphNo(String wrkplcTlphNo) {
        this.wrkplcTlphNo = wrkplcTlphNo;
    }

    public String getPrttlpNo() {
        if (prttlpNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(prttlpNo);
        }

        return prttlpNo;
    }

    public void setPrttlpNo(String prttlpNo) {
        this.prttlpNo = prttlpNo;
    }

    public String getPrdcCd() {

        if (prdcCd == null) {
            return "";
        }
        return prdcCd;
    }

    public void setPrdcCd(String prdcCd) {
        this.prdcCd = prdcCd;
    }

    public String getPrdcTypeCd() {

        if (prdcTypeCd == null) {
            return "";
        }
        return prdcTypeCd;
    }

    public void setPrdcTypeCd(String prdcTypeCd) {
        this.prdcTypeCd = prdcTypeCd;
    }

    public String getSpnsDscnTypeCd() {

        if (spnsDscnTypeCd == null) {
            return "";
        }
        return spnsDscnTypeCd;
    }

    public void setSpnsDscnTypeCd(String spnsDscnTypeCd) {
        this.spnsDscnTypeCd = spnsDscnTypeCd;
    }

    public String getAgncSupotAmnt() {

        if (agncSupotAmnt == null) {
            return "";
        }
        return agncSupotAmnt;
    }

    public void setAgncSupotAmnt(String agncSupotAmnt) {
        this.agncSupotAmnt = agncSupotAmnt;
    }

    public String getEnggMnthCnt() {

        if (enggMnthCnt == null) {
            return "";
        }
        return enggMnthCnt;
    }

    public void setEnggMnthCnt(String enggMnthCnt) {
        this.enggMnthCnt = enggMnthCnt;
    }

    public String getHndsetInstAmnt() {

        if (hndsetInstAmnt == null) {
            return "";
        }
        return hndsetInstAmnt;
    }

    public void setHndsetInstAmnt(String hndsetInstAmnt) {
        this.hndsetInstAmnt = hndsetInstAmnt;
    }

    public String getHndsetPrpyAmnt() {

        if (hndsetPrpyAmnt == null) {
            return "";
        }
        return hndsetPrpyAmnt;
    }

    public void setHndsetPrpyAmnt(String hndsetPrpyAmnt) {
        this.hndsetPrpyAmnt = hndsetPrpyAmnt;
    }

    public String getInstMnthCnt() {

        if (instMnthCnt == null) {
            return "";
        }
        return instMnthCnt;
    }

    public void setInstMnthCnt(String instMnthCnt) {
        this.instMnthCnt = instMnthCnt;
    }

    public String getUsimPymnMthdCd() {

        if (usimPymnMthdCd == null) {
            return "";
        }
        return usimPymnMthdCd;
    }

    public void setUsimPymnMthdCd(String usimPymnMthdCd) {
        this.usimPymnMthdCd = usimPymnMthdCd;
    }

    public String getSbscstPymnMthdCd() {

        if (sbscstPymnMthdCd == null) {
            return "";
        }
        return sbscstPymnMthdCd;
    }

    public void setSbscstPymnMthdCd(String sbscstPymnMthdCd) {
        this.sbscstPymnMthdCd = sbscstPymnMthdCd;
    }

    public String getSbscstImpsExmpRsnCd() {

        if (sbscstImpsExmpRsnCd == null) {
            return "";
        }
        return sbscstImpsExmpRsnCd;
    }

    public void setSbscstImpsExmpRsnCd(String sbscstImpsExmpRsnCd) {
        this.sbscstImpsExmpRsnCd = sbscstImpsExmpRsnCd;
    }

    public String getBondPrsrFeePymnMthdCd() {

        if (bondPrsrFeePymnMthdCd == null) {
            return "";
        }
        return bondPrsrFeePymnMthdCd;
    }

    public void setBondPrsrFeePymnMthdCd(String bondPrsrFeePymnMthdCd) {
        this.bondPrsrFeePymnMthdCd = bondPrsrFeePymnMthdCd;
    }

    public String getSbscPrtlstRcvEmlAdrsNm() {
        if (sbscPrtlstRcvEmlAdrsNm == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(sbscPrtlstRcvEmlAdrsNm);
        }

        return sbscPrtlstRcvEmlAdrsNm;
    }

    public void setSbscPrtlstRcvEmlAdrsNm(String sbscPrtlstRcvEmlAdrsNm) {
        this.sbscPrtlstRcvEmlAdrsNm = sbscPrtlstRcvEmlAdrsNm;
    }

    public String getNpFeePayMethCd() {

        if (npFeePayMethCd == null) {
            return "";
        }
        return npFeePayMethCd;
    }

    public void setNpFeePayMethCd(String npFeePayMethCd) {
        this.npFeePayMethCd = npFeePayMethCd;
    }

    public String getNpBillMethCd() {

        if (npBillMethCd == null) {
            return "";
        }
        return npBillMethCd;
    }

    public void setNpBillMethCd(String npBillMethCd) {
        this.npBillMethCd = npBillMethCd;
    }

    public String getNpHndsetInstaDuratDivCd() {

        if (npHndsetInstaDuratDivCd == null) {
            return "";
        }
        return npHndsetInstaDuratDivCd;
    }

    public void setNpHndsetInstaDuratDivCd(String npHndsetInstaDuratDivCd) {
        this.npHndsetInstaDuratDivCd = npHndsetInstaDuratDivCd;
    }

    public String getRfundNpBankCd() {

        if (rfundNpBankCd == null) {
            return "";
        }
        return rfundNpBankCd;
    }

    public void setRfundNpBankCd(String rfundNpBankCd) {
        this.rfundNpBankCd = rfundNpBankCd;
    }

    public String getRfundBankBnkacnNo() {
        if (rfundBankBnkacnNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(rfundBankBnkacnNo);
        }

        return rfundBankBnkacnNo;
    }

    public void setRfundBankBnkacnNo(String rfundBankBnkacnNo) {
        this.rfundBankBnkacnNo = rfundBankBnkacnNo;
    }

    public String getNpTotRmnyAmt() {

        if (npTotRmnyAmt == null) {
            return "";
        }
        return npTotRmnyAmt;
    }

    public void setNpTotRmnyAmt(String npTotRmnyAmt) {
        this.npTotRmnyAmt = npTotRmnyAmt;
    }

    public String getNpCashRmnyAmt() {

        if (npCashRmnyAmt == null) {
            return "";
        }
        return npCashRmnyAmt;
    }

    public void setNpCashRmnyAmt(String npCashRmnyAmt) {
        this.npCashRmnyAmt = npCashRmnyAmt;
    }

    public String getNpCcardRmnyAmt() {

        if (npCcardRmnyAmt == null) {
            return "";
        }
        return npCcardRmnyAmt;
    }

    public void setNpCcardRmnyAmt(String npCcardRmnyAmt) {
        this.npCcardRmnyAmt = npCcardRmnyAmt;
    }

    public String getNpRmnyMethCd() {

        if (npRmnyMethCd == null) {
            return "";
        }
        return npRmnyMethCd;
    }

    public void setNpRmnyMethCd(String npRmnyMethCd) {
        this.npRmnyMethCd = npRmnyMethCd;
    }

    public String getNpHndsetInstaLmsTlphNo() {
        if (npHndsetInstaLmsTlphNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(npHndsetInstaLmsTlphNo);
        }

        return npHndsetInstaLmsTlphNo;
    }

    public void setNpHndsetInstaLmsTlphNo(String npHndsetInstaLmsTlphNo) {
        this.npHndsetInstaLmsTlphNo = npHndsetInstaLmsTlphNo;
    }

    public String getNpCcardNo() {
        if (npCcardNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(npCcardNo);
        }

        return npCcardNo;
    }

    public void setNpCcardNo(String npCcardNo) {
        this.npCcardNo = npCcardNo;
    }

    public String getNpCcardExpirYm() {

        if (npCcardExpirYm == null) {
            return "";
        }
        return npCcardExpirYm;
    }

    public void setNpCcardExpirYm(String npCcardExpirYm) {
        this.npCcardExpirYm = npCcardExpirYm;
    }

    public String getNpInslMonsNum() {

        if (npInslMonsNum == null) {
            return "";
        }
        return npInslMonsNum;
    }

    public void setNpInslMonsNum(String npInslMonsNum) {
        this.npInslMonsNum = npInslMonsNum;
    }

    public String getNpCcardSnctTypeCd() {

        if (npCcardSnctTypeCd == null) {
            return "";
        }
        return npCcardSnctTypeCd;
    }

    public void setNpCcardSnctTypeCd(String npCcardSnctTypeCd) {
        this.npCcardSnctTypeCd = npCcardSnctTypeCd;
    }

    public String getNpCcardOwnrIdfyNo() {
        if (npCcardOwnrIdfyNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(npCcardOwnrIdfyNo);
        }

        return npCcardOwnrIdfyNo;
    }

    public void setNpCcardOwnrIdfyNo(String npCcardOwnrIdfyNo) {
        this.npCcardOwnrIdfyNo = npCcardOwnrIdfyNo;
    }

    public String getNpSgntInfo() {

        if (npSgntInfo == null) {
            return "";
        }
        return npSgntInfo;
    }

    public void setNpSgntInfo(String npSgntInfo) {
        this.npSgntInfo = npSgntInfo;
    }

    public String getPayAsertDt() {

        if (payAsertDt == null) {
            return "";
        }
        return payAsertDt;
    }

    public void setPayAsertDt(String payAsertDt) {
        this.payAsertDt = payAsertDt;
    }

    public String getPayAsertAmt() {

        if (payAsertAmt == null) {
            return "";
        }
        return payAsertAmt;
    }

    public void setPayAsertAmt(String payAsertAmt) {
        this.payAsertAmt = payAsertAmt;
    }

    public String getPayMethCd() {

        if (payMethCd == null) {
            return "";
        }
        return payMethCd;
    }

    public void setPayMethCd(String payMethCd) {
        this.payMethCd = payMethCd;
    }

    public String getCustId() {
        if (custId == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(custId);
        }

        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getNcn() {
        if (ncn == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(ncn);
        }

        return ncn;
    }

    public void setNcn(String ncn) {
        this.ncn = ncn;
    }

    public String getCtn() {
        if (ctn == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(ctn);
        }

        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public String getCntplcNo() {
        if (cntplcNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(cntplcNo);
        }

        return cntplcNo;
    }

    public void setCntplcNo(String cntplcNo) {
        this.cntplcNo = cntplcNo;
    }

    public String getItgOderWhyCd() {

        if (itgOderWhyCd == null) {
            return "";
        }
        return itgOderWhyCd;
    }

    public void setItgOderWhyCd(String itgOderWhyCd) {
        this.itgOderWhyCd = itgOderWhyCd;
    }

    public String getAftmnIncInCd() {

        if (aftmnIncInCd == null) {
            return "";
        }
        return aftmnIncInCd;
    }

    public void setAftmnIncInCd(String aftmnIncInCd) {
        this.aftmnIncInCd = aftmnIncInCd;
    }

    public String getApyRelTypeCd() {

        if (apyRelTypeCd == null) {
            return "";
        }
        return apyRelTypeCd;
    }

    public void setApyRelTypeCd(String apyRelTypeCd) {
        this.apyRelTypeCd = apyRelTypeCd;
    }

    public String getCustTchMediCd() {

        if (custTchMediCd == null) {
            return "";
        }
        return custTchMediCd;
    }

    public void setCustTchMediCd(String custTchMediCd) {
        this.custTchMediCd = custTchMediCd;
    }

    public String getSmsRcvYn() {

        if (smsRcvYn == null) {
            return "";
        }
        return smsRcvYn;
    }

    public void setSmsRcvYn(String smsRcvYn) {
        this.smsRcvYn = smsRcvYn;
    }

    public String getAppEventCd() {

        if (appEventCd == null) {
            return "";
        }
        return appEventCd;
    }

    public void setAppEventCd(String appEventCd) {
        this.appEventCd = appEventCd;
    }

    public String getServiceName() {

        if (serviceName == null) {
            return "";
        }
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getOperationName() {

        if (operationName == null) {
            return "";
        }
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getVoName() {

        if (voName == null) {
            return "";
        }
        return voName;
    }

    public void setVoName(String voName) {
        this.voName = voName;
    }

    public String getResNo() {

        if (resNo == null) {
            return "";
        }
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getWantTlphNo() {

        if (wantTlphNo == null) {
            return "";
        }
        return wantTlphNo;
    }

    public void setWantTlphNo(String wantTlphNo) {
        this.wantTlphNo = wantTlphNo;
    }

    public String getRequestKey() {

        if (requestKey == null) {
            return "";
        }
        return requestKey;
    }

    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    public String getAdditionKey() {

        if (additionKey == null) {
            return "";
        }
        return additionKey;
    }

    public void setAdditionKey(String additionKey) {
        this.additionKey = additionKey;
    }

    public String getNstepUserId() {

        if (nstepUserId == null) {
            return "";
        }
        return nstepUserId;
    }

    public void setNstepUserId(String nstepUserId) {
        this.nstepUserId = nstepUserId;
    }

    public String getSvcContId() {
        return svcContId;
    }

    public void setSvcContId(String svcContId) {
        this.svcContId = svcContId;
    }

    public String getOderTypeCd() {
        return oderTypeCd;
    }

    public void setOderTypeCd(String oderTypeCd) {
        this.oderTypeCd = oderTypeCd;
    }

    public String getUsimChgRsnCd() {
        return usimChgRsnCd;
    }

    public void setUsimChgRsnCd(String usimChgRsnCd) {
        this.usimChgRsnCd = usimChgRsnCd;
    }

    public String getUsimKindsCd() {
        if (usimKindsCd == null) {
            return "";
        }
        return usimKindsCd;
    }

    public void setUsimKindsCd(String usimKindsCd) {
        this.usimKindsCd = usimKindsCd;
    }

    public String getEsimPhoneId() {
        if (esimPhoneId == null) {
            return "";
        }
        return esimPhoneId;
    }

    public void setEsimPhoneId(String esimPhoneId) {
        this.esimPhoneId = esimPhoneId;
    }

    public String getEsimUiccId() {
        if (esimUiccId == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(esimUiccId);
        }

        return esimUiccId;
    }


    public void setEsimUiccId(String esimUiccId) {
        this.esimUiccId = esimUiccId;
    }

    public String getPrntsCtn() {
        if (prntsCtn == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(prntsCtn);
        }

        return prntsCtn;
    }

    public void setPrntsCtn(String prntsCtn) {
        this.prntsCtn = prntsCtn;
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public void setEncrypt(boolean isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public String getRetvStrtDt() {
        return retvStrtDt;
    }

    public void setRetvStrtDt(String retvStrtDt) {
        this.retvStrtDt = retvStrtDt;
    }

    public String getRetvEndDt() {
        return retvEndDt;
    }

    public void setRetvEndDt(String retvEndDt) {
        this.retvEndDt = retvEndDt;
    }

    public String getSvcApyTrtStatCd() {
        if (svcApyTrtStatCd == null) {
            return "";
        }
        return svcApyTrtStatCd;
    }

    public void setSvcApyTrtStatCd(String svcApyTrtStatCd) {
        this.svcApyTrtStatCd = svcApyTrtStatCd;
    }

    public String getFrmpapId() {
        if (frmpapId == null) {
            return "";
        }
        return frmpapId;
    }

    public void setFrmpapId(String frmpapId) {
        this.frmpapId = frmpapId;
    }

    public String getFrmpapStatCd() {
        return frmpapStatCd;
    }

    public void setFrmpapStatCd(String frmpapStatCd) {
        this.frmpapStatCd = frmpapStatCd;
    }

    public String getMyslfAthnYn() {
        return myslfAthnYn;
    }

    public void setMyslfAthnYn(String myslfAthnYn) {
        this.myslfAthnYn = myslfAthnYn;
    }

    public String getIpinCi() {

        if (ipinCi == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(ipinCi);
        }

        return ipinCi;
    }

    public void setIpinCi(String ipinCi) {
        this.ipinCi = ipinCi;
    }

    public String getOnlineAthnDivCd() {
        return onlineAthnDivCd;
    }

    public void setOnlineAthnDivCd(String onlineAthnDivCd) {
        this.onlineAthnDivCd = onlineAthnDivCd;
    }

    public String getFnncDealAgreeYn() {
        return fnncDealAgreeYn;
    }

    public void setFnncDealAgreeYn(String fnncDealAgreeYn) {
        this.fnncDealAgreeYn = fnncDealAgreeYn;
    }

    public String getOderTrtOrgId() {
        return oderTrtOrgId;
    }

    public void setOderTrtOrgId(String oderTrtOrgId) {
        this.oderTrtOrgId = oderTrtOrgId;
    }

    public String getOderTrtId() {
        return oderTrtId;
    }

    public void setOderTrtId(String oderTrtId) {
        this.oderTrtId = oderTrtId;
    }

    public String getRetvSeq() {
        if (retvSeq == null) {
            return "";
        }
        return retvSeq;
    }


    public void setRetvSeq(String retvSeq) {
        this.retvSeq = retvSeq;
    }

    public String getRetvCascnt() {
        if (retvCascnt == null) {
            return "";
        }
        return retvCascnt;
    }

    public void setRetvCascnt(String retvCascnt) {
        this.retvCascnt = retvCascnt;
    }

    public String getRetvStDt() {
        return retvStDt;
    }

    public void setRetvStDt(String retvStDt) {
        this.retvStDt = retvStDt;
    }

    public String getRetvFnsDt() {
        return retvFnsDt;
    }

    public void setRetvFnsDt(String retvFnsDt) {
        this.retvFnsDt = retvFnsDt;
    }

    public String getItgFrmpapSeq() {
        return itgFrmpapSeq;
    }

    public void setItgFrmpapSeq(String itgFrmpapSeq) {
        this.itgFrmpapSeq = itgFrmpapSeq;
    }

    public String getScanDt() {
        return scanDt;
    }

    public void setScanDt(String scanDt) {
        this.scanDt = scanDt;
    }

    public String getOnlineOfflnDivCd() {
        return onlineOfflnDivCd;
    }

    public void setOnlineOfflnDivCd(String onlineOfflnDivCd) {
        this.onlineOfflnDivCd = onlineOfflnDivCd;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCpntId() {
        return cpntId;
    }

    public void setCpntId(String cpntId) {
        this.cpntId = cpntId;
    }

    public String getRetvCdVal() {
        return retvCdVal;
    }

    public void setRetvCdVal(String retvCdVal) {
        this.retvCdVal = retvCdVal;
    }

    public String getSmsRcvTelNo() {

        if (smsRcvTelNo == null) {
            return "";
        }
        if(isEncrypt) {
            return KisaSeedUtil.encrypt(smsRcvTelNo);
        }
        
        return smsRcvTelNo;
    }

    public void setSmsRcvTelNo(String smsRcvTelNo) {
        this.smsRcvTelNo = smsRcvTelNo;
    }

    public String getFathSbscDivCd() {
        return fathSbscDivCd;
    }

    public void setFathSbscDivCd(String fathSbscDivCd) {
        this.fathSbscDivCd = fathSbscDivCd;
    }

    public String getFathTransacId() {
        return fathTransacId;
    }

    public void setFathTransacId(String fathTransacId) {
        this.fathTransacId = fathTransacId;
    }

    public String getRetvDivCd() {
        return retvDivCd;
    }

    public void setRetvDivCd(String retvDivCd) {
        this.retvDivCd = retvDivCd;
    }

    public String getMcnStatRsnCd() {
        return mcnStatRsnCd;
    }

    public void setMcnStatRsnCd(String mcnStatRsnCd) {
        this.mcnStatRsnCd = mcnStatRsnCd;
    }

    public String getUsimSuccYn() {
        return usimSuccYn;
    }

    public void setUsimSuccYn(String usimSuccYn) {
        this.usimSuccYn = usimSuccYn;
    }

    public String getFtrNewParam() {
        return ftrNewParam;
    }

    public void setFtrNewParam(String ftrNewParam) {
        this.ftrNewParam = ftrNewParam;
    }

    public String getRealUseCustBrthDate() {
        return realUseCustBrthDate;
    }

    public void setRealUseCustBrthDate(String realUseCustBrthDate) {
        this.realUseCustBrthDate = realUseCustBrthDate;
    }

    public String getCstmrType() {
        return cstmrType;
    }

    public void setCstmrType(String cstmrType) {
        this.cstmrType = cstmrType;
    }

    public String getAgntCustIdfyNoType() {
        return agntCustIdfyNoType;
    }

    public void setAgntCustIdfyNoType(String agntCustIdfyNoType) {
        this.agntCustIdfyNoType = agntCustIdfyNoType;
    }

    public String getAgntIdfyNoVal() {
        if (agntIdfyNoVal == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(agntIdfyNoVal);
        }

        return agntIdfyNoVal;
    }

    public void setAgntIdfyNoVal(String agntIdfyNoVal) {
        this.agntIdfyNoVal = agntIdfyNoVal;
    }

    public String getAgntPersonSexDiv() {
        return agntPersonSexDiv;
    }

    public void setAgntPersonSexDiv(String agntPersonSexDiv) {
        this.agntPersonSexDiv = agntPersonSexDiv;
    }

    public String getAgntAgreYn() {
        return agntAgreYn;
    }

    public void setAgntAgreYn(String agntAgreYn) {
        this.agntAgreYn = agntAgreYn;
    }

    public String getAgntTelAthn() {
        return agntTelAthn;
    }

    public void setAgntTelAthn(String agntTelAthn) {
        this.agntTelAthn = agntTelAthn;
    }

    public String getAgntTelNo() {
        if (agntTelNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(agntTelNo);
        }

        return agntTelNo;
    }

    public void setAgntTelNo(String agntTelNo) {
        this.agntTelNo = agntTelNo;
    }

    public String getAgntTypeCd() {
        return agntTypeCd;
    }

    public void setAgntTypeCd(String agntTypeCd) {
        this.agntTypeCd = agntTypeCd;
    }

    public String getAgntNationalityCd() {
        return agntNationalityCd;
    }

    public void setAgntNationalityCd(String agntNationalityCd) {
        this.agntNationalityCd = agntNationalityCd;
    }

    public String getAgntRsdcrtIssuDate() {
        return agntRsdcrtIssuDate;
    }

    public void setAgntRsdcrtIssuDate(String agntRsdcrtIssuDate) {
        this.agntRsdcrtIssuDate = agntRsdcrtIssuDate;
    }

    public String getPhotoAthnTxnSeq() {
        return photoAthnTxnSeq;
    }

    public void setPhotoAthnTxnSeq(String photoAthnTxnSeq) {
        this.photoAthnTxnSeq = photoAthnTxnSeq;
    }

    public String getAgntRlnamAthnEvdnPprCd() {
        return agntRlnamAthnEvdnPprCd;
    }

    public void setAgntRlnamAthnEvdnPprCd(String agntRlnamAthnEvdnPprCd) {
        this.agntRlnamAthnEvdnPprCd = agntRlnamAthnEvdnPprCd;
    }

    public String getAgntLicnsRgnCd() {
        return agntLicnsRgnCd;
    }

    public void setAgntLicnsRgnCd(String agntLicnsRgnCd) {
        this.agntLicnsRgnCd = agntLicnsRgnCd;
    }

    public String getAgntLicnsNo() {
        if (agntLicnsNo == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(agntLicnsNo);
        }

        return agntLicnsNo;
    }

    public void setAgntLicnsNo(String agntLicnsNo) {
        this.agntLicnsNo = agntLicnsNo;
    }

    public String getRcvCustNo() {
        return rcvCustNo;
    }

    public void setRcvCustNo(String rcvCustNo) {
        this.rcvCustNo = rcvCustNo;
    }

    public String getRcvBillAcntNo() {
        return rcvBillAcntNo;
    }

    public void setRcvBillAcntNo(String rcvBillAcntNo) {
        this.rcvBillAcntNo = rcvBillAcntNo;
    }

    public String getDocConfirmYn() {
        return docConfirmYn;
    }

    public void setDocConfirmYn(String docConfirmYn) {
        this.docConfirmYn = docConfirmYn;
    }

    public String getFollowupYn() {
        return followupYn;
    }

    public void setFollowupYn(String followupYn) {
        this.followupYn = followupYn;
    }

    public String getChgRqsshtEmlAdrsNm() {
        if (chgRqsshtEmlAdrsNm == null) {
            return "";
        }

        if(isEncrypt) {
            return KisaSeedUtil.encrypt(chgRqsshtEmlAdrsNm);
        }

        return chgRqsshtEmlAdrsNm;
    }

    public void setChgRqsshtEmlAdrsNm(String chgRqsshtEmlAdrsNm) {
        this.chgRqsshtEmlAdrsNm = chgRqsshtEmlAdrsNm;
    }

    public String getIndvLoInfoPrvAgreeYn() {
        return indvLoInfoPrvAgreeYn;
    }

    public void setIndvLoInfoPrvAgreeYn(String indvLoInfoPrvAgreeYn) {
        this.indvLoInfoPrvAgreeYn = indvLoInfoPrvAgreeYn;
    }
}