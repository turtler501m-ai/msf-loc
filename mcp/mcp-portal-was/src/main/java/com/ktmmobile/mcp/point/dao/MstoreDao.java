package com.ktmmobile.mcp.point.dao;

import com.ktmmobile.mcp.point.dto.MstoreCanTrgDto;
import com.ktmmobile.mcp.point.dto.MstoreContentItemDto;
import com.ktmmobile.mcp.point.dto.MstoreDto;

import java.util.List;
import java.util.Map;

public interface MstoreDao {

    /**
     * msp 개통 고객정보 조회
     * @param userId
     * @param contractNum
     * @return List<MstoreDto>
     */
    List<MstoreDto> getMspCusInfo(String userId, String contractNum);

    /**
     * mstore 이용약관 동의 여부 확인
     * @param userId
     * @return Map<String, String>
     */
    Map<String, String> getMstoreTermsAgreeInfo(String userId);

    /**
     * mstore 이용약관 동의 update
     * @param userId
     * @return int
     */
    int updateMstoreTermsAgreeInfo(String userId);

    /**
     * mstore 이용약관 동의 insert
     * @param mstoreDto
     * @return void
     */
    void insertMstoreTermsAgreeInfo(MstoreDto mstoreDto);

    /**
     * mstore 탈퇴연동 대상 insert
     * @param mstoreCanTrgDto
     * @return int
     */
    int insertMstoreCanTrg(MstoreCanTrgDto mstoreCanTrgDto);

    /**
     * MCP 신청정보 조회
     * @param customerId
     * @return MstoreDto
     */
    MstoreDto getMcpRequestInfo(String customerId);

    /**
     * 탈퇴연동을 위한 CI값 조회 (20231205 이전 M스토어 사용자)
     * @param customerId
     * @return String
     */
    String getMstoreTmpEmpen(String customerId);

    /**
     * MSTORE SSO 연동 정보 조회
     * @param userId
     * @return MstoreDto
     */
    MstoreDto getMstoreSSOInfo(String userId);

    /**
     * Mstore SSO 연동 이력 저장/수정
     * @param ssoInfoMap
     * @return int
     */
    int mergeMstoreSSOInfo(Map<String,String> ssoInfoMap);

    /**
     * 정회원 CONTRACT_NUM 가져오기
     * @param userId
     * @return
     */
    String getMcpUserCntrMngInfo(String userId);

    List<MstoreContentItemDto> getMstoreContentItemListPc();

    List<MstoreContentItemDto> getMstoreContentItemListMobile();
}