package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSocVO;
import java.util.List;

/**
 * 이용중 부가서비스 목록 응답 VO
 *
 * POST /api/v1/addition/my-list 응답
 *
 * M플랫폼 X97(가입중인 부가서비스 조회) 결과에
 * MSP_RATE_MST@DL_MSP 해지 가능 여부(onlineCanYn)와
 * 해지 안내 문구(canCmnt)를 세팅하여 반환.
 *
 * ASIS에서는 Map<String,Object>에 "outList" 키로 반환하던 것을 명시적 VO로 교체.
 *
 * @see MpSocVO 각 SOC의 상세 정보 (soc, socNm, strtDt, endDttm, onlineCanYn, canCmnt 등)
 */
public class AdditionMyListResVO {

    /**
     * 이용중 부가서비스 SOC 목록
     * - soc          : SOC 코드
     * - socNm        : 부가서비스명
     * - strtDt       : 서비스 시작일
     * - endDttm      : 서비스 종료일
     * - onlineCanYn  : 온라인 해지 가능 여부 ("Y"/"N") — MSP_RATE_MST 기반
     * - canCmnt      : 해지 안내 문구 — MSP_RATE_MST 기반
     * "PL249Q800"(아무나SOLO 더미) 제거 후 반환
     */
    private List<MpSocVO> list;

    public List<MpSocVO> getList() { return list; }
    public void setList(List<MpSocVO> list) { this.list = list; }
}
