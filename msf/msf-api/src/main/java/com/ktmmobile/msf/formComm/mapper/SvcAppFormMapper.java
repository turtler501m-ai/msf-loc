package com.ktmmobile.msf.formComm.mapper;

import com.ktmmobile.msf.formComm.dto.AgencyShopResVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 업무공통 서식지 Mapper. SvcAppFormMapper.xml 참조.
 * ASIS: AppformMapper.xml (mcp-portal-was) 동일 목적.
 * - selectAgencyList         : 대리점/판매점 목록 (MSF_AGENT_SHOP_INFO)
 * - selectAgentCode          : 접점코드로 대리점 코드 조회 (ORG_ORGN_INFO_MST@DL_MSP)
 * - getAppFormPointList      : 서식지 위치정보 (MSF_APPLICATION_FORM_INFO)
 * - getAppFormGroupPointList : 그룹별 서식지 위치정보 (MSF_APP_GROUP_MST/INFO/FORM_INFO)
 * - getAppFormPageList       : 페이지별 서식지 좌표값 (MSF_APP_FORM_INFO)
 * - getRequestJoinUserData   : 신청서 가입자 기본정보 조회 (MSF_REQUEST, MSF_REQUEST_CSTMR)
 * - insertMcpRequestAgent    : 법정대리인 정보 저장 (MSF_REQUEST_AGENT)
 * - insertMcpRequestState    : 신청 진행상태 등록 (MSF_REQUEST_STATE)
 * - updateMcpRequestState    : 신청 진행상태 수정 (MSF_REQUEST_STATE)
 * - insertMcpRequestCommend  : 추천 정보 저장 (MSF_REQUEST_COMMEND)
 * - insertMcpRequestPayment  : 납부 정보 저장 (MSF_REQUEST_PAYMENT)
 * - insertMcpRequestReq      : 청구 요청 정보 저장 (MSF_REQUEST_BILL_REQ)
 * - selectActivationDate     : N7003 개통일자 조회 (MSP_JUO_SUB_INFO@DL_MSP)
 */
@Mapper
public interface SvcAppFormMapper {

    /**
     * 대리점/판매점 목록 조회.
     * 테이블: MSF_AGENT_SHOP_INFO (MSF 신규)
     */
    List<AgencyShopResVO> selectAgencyList();

    /**
     * N7002 — 접점코드(ORGN_ID)로 대리점 코드·정보 조회.
     * ASIS: AppformMapper.xml selectAgentCode
     * 운영: ORG_ORGN_INFO_MST@DL_MSP
     */
    Map<String, Object> selectAgentCode(@Param("cntpntShopId") String cntpntShopId);

    /**
     * 서식지 위치정보 전체 조회.
     * ASIS: AppFormMapper.xml getAppFormPointList (MCP_APPLICATION_FORM_INFO)
     */
    List<Map<String, Object>> getAppFormPointList();

    /**
     * 그룹코드별 서식지 위치정보 조회.
     * ASIS: AppFormMapper.xml getAppFormGroupPointList (NMCP_APP_GROUP_MST/INFO, NMCP_APP_FORM_INFO)
     */
    List<Map<String, Object>> getAppFormGroupPointList(@Param("groupCode") String groupCode);

    /**
     * 페이지코드별 서식지 좌표값 조회 (스캔이미지 페이지별).
     * ASIS: AppFormMapper.xml getAppFormPageList (NMCP_APP_FORM_INFO)
     */
    List<Map<String, Object>> getAppFormPageList(@Param("pageCode") String pageCode);

    /**
     * 신청서 가입자 기본정보 조회 (고객명, 예약번호, 대리점코드, 접점매장ID).
     * ASIS: AppFormMapper.xml getRequestJoinUserData (MCP_REQUEST, MCP_REQUEST_CSTMR)
     */
    Map<String, Object> getRequestJoinUserData(@Param("requestKey") Long requestKey);

    /**
     * 법정대리인 정보 저장.
     * ASIS: AppFormMapper.xml insertMcpRequestAgent (MCP_REQUEST_AGENT)
     */
    void insertMcpRequestAgent(Map<String, Object> params);

    /**
     * 신청 진행상태 등록.
     * ASIS: AppFormMapper.xml insertMcpRequestState (MCP_REQUEST_STATE, SQ_RCP_REQUEST_STATE_01.NEXTVAL)
     */
    void insertMcpRequestState(Map<String, Object> params);

    /**
     * 신청 진행상태 수정.
     * ASIS: AppFormMapper.xml updateMcpRequestState (MCP_REQUEST_STATE)
     */
    void updateMcpRequestState(Map<String, Object> params);

    /**
     * 추천 정보 저장 (INSERT SELECT from MSF_COMMEND_ID_MNG).
     * ASIS: AppFormMapper.xml insertMcpRequestCommend (MCP_REQUEST_COMMEND, MCP_COMMEND_ID_MNG)
     */
    void insertMcpRequestCommend(Map<String, Object> params);

    /**
     * 납부 정보 저장.
     * ASIS: AppFormMapper.xml insertMcpRequestPayment (MCP_REQUEST_PAYMENT)
     */
    void insertMcpRequestPayment(Map<String, Object> params);

    /**
     * 청구 요청 정보 저장 (계좌/카드/무선 납부정보).
     * ASIS: AppFormMapper.xml insertMcpRequestReq (MCP_REQUEST_REQ)
     * TOBE 테이블: MSF_REQUEST_BILL_REQ (D2 매핑: MCP_REQUEST_REQ → MSF_REQUEST_BILL_REQ)
     */
    void insertMcpRequestReq(Map<String, Object> params);

    /**
     * N7003 — 계약번호(ncn)로 개통일자(LST_COM_ACTV_DATE) 조회.
     * ASIS: N7003 ITO분석 — MSP_JUO_SUB_INFO@DL_MSP.LST_COM_ACTV_DATE
     */
    String selectActivationDate(@Param("contractNum") String contractNum);
}
