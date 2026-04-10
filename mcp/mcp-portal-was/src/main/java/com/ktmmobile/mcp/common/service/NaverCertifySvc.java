package com.ktmmobile.mcp.common.service;

import com.ktmmobile.mcp.common.dto.NaverDto;


/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : NaverCertifySvc.java
 * 날짜     : 2021. 6. 17. 오전 10:48:31
 * 작성자   : kim
 * 설명     : Naver 관련 서비스
 * </pre>
 */
public interface NaverCertifySvc {

    public String naverAuthSession(NaverDto naverDto);

    public String naverGetResult(NaverDto naverDto);

    public boolean updateMcpAuthData(NaverDto NaverDto);

    public NaverDto getMcpAuthData(NaverDto NaverDto);

    public String naverDeleteAccessToken(NaverDto naverDto);

    public String naverLoginCallBack(NaverDto naverDto);




}
