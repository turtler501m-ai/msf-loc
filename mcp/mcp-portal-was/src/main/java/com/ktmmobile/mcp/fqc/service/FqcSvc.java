package com.ktmmobile.mcp.fqc.service;

import com.ktmmobile.mcp.fqc.dto.FqcBasDto;
import com.ktmmobile.mcp.fqc.dto.FqcDltDto;
import com.ktmmobile.mcp.fqc.dto.FqcPlcyBasDto;
import com.ktmmobile.mcp.fqc.dto.FqcPlcyBnfDto;

import java.util.List;
import java.util.Map;

public interface FqcSvc  {

    /**
     * <pre>
     * 설명     : 현재 기간에 활성한 상태인 프리퀀시 정책 조회
     * </pre>
     */
    public List<FqcPlcyBasDto> getFqcPlcyBasList();

    /**
     * <pre>
     * 설명     :  프리퀀시 정책 및 진행 상태 조회
     * @param fqcBas contractNum 계약번호,openDate 개통일, socCode 요금제 코드
     * </pre>
     */
    public FqcBasDto syncParticipantMissions(FqcBasDto fqcBas) ;

    /**
     * <pre>
     * 설명     :  프리퀀시 미션 등록
     * @param
     * </pre>
     */
    public boolean setFqcDlt(FqcBasDto fqcBas ,FqcDltDto fqcDlt);

    /**
     * <pre>
     * 설명     : 프리퀀시 프리퀀프리퀀시 정책 혜택 정보 조회
     * </pre>
     */
    public List<FqcPlcyBnfDto>  getFqcPlcyBnfList(String fqcPlcyCd) ;


}
