package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

@Data
@XmlRootElement(name = "inDto")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "osstOrdNo", "custNo",
    "rqsshtPprfrmCd", "rqsshtTlphNo", "rqsshtEmlAdrsNm",
    "billZipNo", "billFndtCntplcSbst", "billMntCntplcSbst",
    "blpymMthdCd", "duedatDateIndCd", "crdtCardExprDate", "crdtCardKindCd", "bankCd",
    "blpymMthdIdntNo", "blpymCustNm", "blpymCustIdntNo", "blpymMthdIdntNoHideYn",
    "agreIndCd", "myslAthnTypeCd", "billAtchExclYn", "rqsshtTlphNoHideYn",
    "mngmAgncId", "cntpntCd",
    "iccId", "eSimOpenYn", "intmMdlId", "intmSrlNo", "usimOpenYn",
    "spclSlsNo", "spnsDscnTypeCd", "agncSupotAmnt", "enggMnthCnt",
    "hndsetInstAmnt", "hndsetPrpyAmnt", "instMnthCnt",
    "usimPymnMthdCd", "sbscstPymnMthdCd", "sbscstImpsExmpRsnCd",
    "bondPrsrFeePymnMthdCd", "tlphNo", "sbscPrtlstRcvEmlAdrsNm", "billAcntNo"
})
public class MplatFormOP0InDtoRequest {

    private String osstOrdNo;           // OSST 오더번호 (PC2 콜백)
    private String custNo;              // 고객번호 (PC2 콜백 custId)
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String tlphNo;              // 개통 전화번호 (NU2 예약번호)
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String iccId;               // USIM ICC ID
    private String eSimOpenYn;          // eSIM 개통여부 (일반 USIM은 N)
    private String usimOpenYn;          // USIM 개통여부 (Y)
    private String mngmAgncId;          // 관리대리점ID (agentCode)
    private String cntpntCd;            // 접점코드

    // 납부방법
    private String blpymMthdCd;         // 납부방법코드 (C:신용카드, D:자동이체)

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String blpymMthdIdntNo;     // 납부방법식별번호 (카드번호 or 계좌번호, 평문 세팅 후 KISA암호화)

    private String blpymCustNm;         // 납부고객명 (타인납부시)

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String blpymCustIdntNo;     // 납부고객식별번호 (타인납부시)

    private String blpymMthdIdntNoHideYn; // 납부방법식별번호숨김여부
    private String bankCd;              // 은행코드 (자동이체시)
    private String crdtCardKindCd;      // 신용카드종류코드 (카드납부시)
    private String crdtCardExprDate;    // 신용카드만기일자 (YYMM, 카드납부시)
    private String duedatDateIndCd;     // 납기일자구분코드 (99:카드, 21:자동이체)

    // 청구서 수신
    private String rqsshtPprfrmCd;      // 청구서수신방법 (CSTMR_BILL_SEND_CODE)
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String rqsshtTlphNo;        // 청구수신번호 (rqsshtPprfrmCd=MB이면 개통번호)
    private String rqsshtTlphNoHideYn;  // 청구서전화번호숨김여부 (Y)

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String rqsshtEmlAdrsNm;     // 청구이메일주소명

    private String billZipNo;           // 청구우편번호

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String billFndtCntplcSbst;  // 청구기본주소

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String billMntCntplcSbst;   // 청구상세주소

    // 동의 / 인증
    private String agreIndCd;           // 동의자료코드 (03:오프라인, 01:온라인)
    private String myslAthnTypeCd;      // 본인인증타입코드
    private String billAtchExclYn;      // 청구첨부제외여부

    // 단말/USIM 정보
    private String intmMdlId;           // 기기모델ID (데이터쉐어링: 공백)
    private String intmSrlNo;           // 기기일련번호 (데이터쉐어링: 공백)

    // 판매 / 약정 정보
    private String spclSlsNo;           // 특별판매번호 (spcCode)
    private String spnsDscnTypeCd;      // 스폰서할인유형코드 (sprtTp)
    private String agncSupotAmnt;       // 대리점지원금액 (modelDiscount3)
    private String enggMnthCnt;         // 약정개월수
    private String hndsetInstAmnt;      // 단말할부금 (데이터쉐어링: 0)
    private String hndsetPrpyAmnt;      // 단말선납금 (0)
    private String instMnthCnt;         // 분납개월수 (데이터쉐어링: 0)
    private String usimPymnMthdCd;      // USIM수납방법
    private String sbscstPymnMthdCd;    // 가입비수납방법
    private String sbscstImpsExmpRsnCd; // 가입비면제사유코드 (면제시 '37')
    private String bondPrsrFeePymnMthdCd; // 채권보전료수납방법

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String sbscPrtlstRcvEmlAdrsNm; // 가입내역서수신이메일주소

    // 기타
    private String billAcntNo;          // 통합청구에 사용할 모회선 청구계정번호

}
