package com.ktmmobile.mcp.point.service;


import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.mplatform.vo.UserVO;
import com.ktmmobile.mcp.point.dto.MstoreContentItemDto;

import java.util.List;
import java.util.Map;

public interface MstoreService {

    /**
     * Mstore 이용 가능 여부 체크
     * @return Map<String, String>
     */
    Map<String, String> chkMstoreUseLimit();

    /**
     * Mstore 이용을 위한 본인인증 정보 확인
     * @param niceResDto
     * @return boolean
     */
    boolean identityChk(NiceResDto niceResDto);

    /**
     * mstore 이용약관 동의 여부 확인
     * @param userId
     * @return boolean
     */
    boolean getMstoreTermsAgreeYn(String userId);

    /**
     * mstore 이용약관 이력 기록
     * @param agreeYn
     * @return void
     */
    void chgMstoreTermsAgreeHist(String agreeYn);

    /**
     * mstore 탈퇴연동 대상 insert
     * @param userVO
     * @return void
     */
    void insertMstoreCanTrg(UserVO userVO);

    /**
     * Mstore SSO 연동 이력 저장/수정
     * @param ssoInfoMap
     * @return void
     */
    void mergeMstoreSSOInfo(Map<String, String> ssoInfoMap);

    List<MstoreContentItemDto> getMstoreContents();
}
