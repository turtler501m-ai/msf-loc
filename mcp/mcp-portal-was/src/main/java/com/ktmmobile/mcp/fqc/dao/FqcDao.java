package com.ktmmobile.mcp.fqc.dao;

import com.ktmmobile.mcp.common.dto.McpIpStatisticDto;
import com.ktmmobile.mcp.common.dto.UserEventTraceDto;
import com.ktmmobile.mcp.fqc.dto.FqcBasDto;
import com.ktmmobile.mcp.fqc.dto.FqcDltDto;
import com.ktmmobile.mcp.fqc.dto.FqcPlcyBasDto;
import com.ktmmobile.mcp.fqc.dto.FqcPlcyBnfDto;

import java.util.List;

public interface FqcDao {

    /**
     * <pre>
     * 설명     : 현재 기간에 활성한 상태인 프리퀀시 정책 조회
     * </pre>
     */
    public List<FqcPlcyBasDto> getFqcPlcyBasList();



    /**
     * <pre>
     * 설명     :  프리퀀시 정책 조회
     * </pre>
     */
    public FqcPlcyBasDto getFqcPlcyBas(String fqcPlcyCd);

    /**
     * <pre>
     * 설명     : 프리퀀시 참여 기본 insert
     * </pre>
     */
    public boolean insertFqcBas(FqcBasDto fqcBasDto) ;

    /**
     * <pre>
     * 설명     : 프리퀀시 참여 정보 조회
     * </pre>
     */
    public FqcBasDto getFqcBas(FqcBasDto fqcBasDto) ;


    /**
     * <pre>
     * 설명     : 프리퀀시 참여 정보 update
     * </pre>
     */
    public boolean updateFqcBas(FqcBasDto fqcBasDto);

    /**
     * <pre>
     * 설명     : 프리퀀시 참여 상세 insert
     * </pre>
     */
    public boolean insertFqcDlt(FqcDltDto fqcDltDto)  ;

    /**
     * <pre>
     * 설명     : 프리퀀시 참여 상세 조외
     * </pre>
     */
    public List<FqcDltDto> getFqcDltList(FqcDltDto fqcDltDto);

    /**
     * <pre>
     * 설명     : 프리퀀시 참여 상세 update
     * </pre>
     */
    public boolean updateFqcDlt(FqcDltDto fqcDltDto) ;


    /**
     * <pre>
     * 설명     : 요금제 정보 존재 여부 확인
     * </pre>
     */
    public int isFqcPlcyPlaCount(String fqcPlcyCd , String socCode) ;

    /**
     * <pre>
     * 설명     : 프리퀀시 참여 정보 조회
     * </pre>
     */
    public String  getPlcyMsnCd(String fqcPlcyCd , String msnTpCd) ;


    /**
     * <pre>
     * 설명     : 프리퀀시 프리퀀프리퀀시 정책 혜택 정보 조회
     * </pre>
     */
    public List<FqcPlcyBnfDto>  getFqcPlcyBnfList(String fqcPlcyCd) ;

}
