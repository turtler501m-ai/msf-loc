package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;

/**
 * X91 신용카드 인증조회 응답 VO.
 * ASIS MoscCrdtCardAthnInDto 와 동일 필드 구조.
 * 파라미터: crdtCardNo, crdtCardTermDay(YYMM), brthDate(YYYYMMDD), custNm
 * 응답: trtResult(Y=성공/N=실패), trtMsg, crdtCardKindCd, crdtCardNm
 */
public class MpCrdtCardAthnVO extends CommonXmlVO {

    private String trtResult;       // 처리결과 Y/N
    private String trtMsg;          // 처리메시지 (실패 시 사유)
    private String crdtCardKindCd;  // 신용카드종류코드 (예: GM, DY)
    private String crdtCardNm;      // 신용카드명

    @Override
    protected void parse() throws Exception {
        this.trtResult      = XmlParseUtil.getChildValue(this.body, "trtResult");
        this.trtMsg         = XmlParseUtil.getChildValue(this.body, "trtMsg");
        this.crdtCardKindCd = XmlParseUtil.getChildValue(this.body, "crdtCardKindCd");
        this.crdtCardNm     = XmlParseUtil.getChildValue(this.body, "crdtCardNm");
    }

    public String getTrtResult()      { return trtResult; }
    public String getTrtMsg()         { return trtMsg; }
    public String getCrdtCardKindCd() { return crdtCardKindCd; }
    public String getCrdtCardNm()     { return crdtCardNm; }
}
