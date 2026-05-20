package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 충전 내역조회 테이블 (MSP_MNG.PPS_RCG_TES)
 */
@Getter
@Setter
@NoArgsConstructor
public class MspPpsRcgTesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long tesSeq;          // PK (PPS_RCG_TES_SEQ)
    private String reqType;       // pps_rcg_type 테이블참조
    private String contractNum;   // 계약번호
    private String rechargeAgent; // 충전대리점
    private long recharge;        // 충전금액
    private String retCode;       // 충전결과코드
    private String retMsg;        // 충전결과메세지
    private long rcgSeq;          // KT_IN_RES_LOG SEQ
    private int oAmount;          // 충전된금액
    private String oTesChargeMax; // 최대충전가능금액
    private String oTesBaser;     // 기본알
    private String oTesChgr;      // 충전알
    private String oTesMagicr;    // 데이터알
    private String oTesFsmsr;     // 문자알
    private String oTesVideor;    // 영상알
    private String oTesIpvasr;    // 정보료전용알
    private String oTesIpmaxr;    // 알캡상한알
    private String oTesSmsm;      // 문자건수
    private String oTesDataplusv; // 데이타계정 (단위:Byte)
    private String rechargeIp;    // 요청IP
    private Date reqDate;         // 요청일자
    private String adminId;       // 충전요청한 로그인아이디

    // 결과가 정상(0000)이면 충전금액, 실패면 0 반환
    public long getRechargeResult() {
        if ("0000".equals(retCode)) {
            return recharge;
        } else {
            return 0;
        }
    }

    // 성공이고 메시지 없으면 "성공" 반환
    public String getRetMsg() {
        if ("0000".equals(retCode) && (retMsg == null || "".equals(retMsg))) {
            return "성공";
        } else {
            return retMsg;
        }
    }

    // o 접두사 필드들 비표준 getter/setter 유지
    public int getoAmount() { return oAmount; }
    public void setoAmount(int oAmount) { this.oAmount = oAmount; }

    public String getoTesChargeMax() {
        if (oTesChargeMax == null || oTesChargeMax.equals("")) return "0";
        return oTesChargeMax;
    }
    public void setoTesChargeMax(String oTesChargeMax) { this.oTesChargeMax = oTesChargeMax; }

    public String getoTesBaser() {
        if (oTesBaser == null || oTesBaser.equals("")) return "0";
        return oTesBaser;
    }
    public void setoTesBaser(String oTesBaser) { this.oTesBaser = oTesBaser; }

    public String getoTesChgr() {
        if (oTesChgr == null || oTesChgr.equals("")) return "0";
        return oTesChgr;
    }
    public void setoTesChgr(String oTesChgr) { this.oTesChgr = oTesChgr; }

    public String getoTesMagicr() {
        if (oTesMagicr == null || oTesMagicr.equals("")) return "0";
        return oTesMagicr;
    }
    public void setoTesMagicr(String oTesMagicr) { this.oTesMagicr = oTesMagicr; }

    public String getoTesFsmsr() {
        if (oTesFsmsr == null || oTesFsmsr.equals("")) return "0";
        return oTesFsmsr;
    }
    public void setoTesFsmsr(String oTesFsmsr) { this.oTesFsmsr = oTesFsmsr; }

    public String getoTesVideor() { return oTesVideor; }
    public void setoTesVideor(String oTesVideor) { this.oTesVideor = oTesVideor; }

    public String getoTesIpvasr() { return oTesIpvasr; }
    public void setoTesIpvasr(String oTesIpvasr) { this.oTesIpvasr = oTesIpvasr; }

    public String getoTesIpmaxr() { return oTesIpmaxr; }
    public void setoTesIpmaxr(String oTesIpmaxr) { this.oTesIpmaxr = oTesIpmaxr; }

    public String getoTesSmsm() { return oTesSmsm; }
    public void setoTesSmsm(String oTesSmsm) { this.oTesSmsm = oTesSmsm; }

    public String getoTesDataplusv() { return oTesDataplusv; }
    public void setoTesDataplusv(String oTesDataplusv) { this.oTesDataplusv = oTesDataplusv; }

}
