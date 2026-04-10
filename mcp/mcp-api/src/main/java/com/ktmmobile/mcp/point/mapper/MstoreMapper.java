package com.ktmmobile.mcp.point.mapper;

import com.ktmmobile.mcp.point.dto.MstoreCanTrgDto;
import com.ktmmobile.mcp.point.dto.MstoreDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MstoreMapper {

    /**
     * Mstore SSO 연동 파라미터 조회 (해지가 아닌 회선 전부 조회)
     * @param mstoreDto
     * @return List<MstoreDto>
     */
    List<MstoreDto> getMspCusInfo(MstoreDto mstoreDto);

    /**
     * MCP 신청정보 조회 (CUSTOMER_ID에 엮인 신청정보 전부 조회)
     * @param customerId
     * @return List<MstoreDto>
     */
    List<MstoreDto> getMcpRequestInfo(String customerId);

    /**
     * 개통 정보의 CI값 조회
     * @param customerId
     * @return String
     */
    String getMspCstmrCI(String customerId);

    /**
     * [AS-IS] MSTORE 연동 대상 조회 (최대 50건)
     * @return List<MstoreCanTrgDto>
     */
    List<MstoreCanTrgDto> selectMstoreCanTrgList();


    /**
     * [TO-BE] MSTORE 탈퇴 연동 대상 조회 (최대 50건)
     * @return List<MstoreCanTrgDto>
     */
    List<MstoreCanTrgDto> selectMstoreCanListNew();

    /**
     * [TO-BE] MSTORE 탈퇴 연동 대상 조회 (최대 50건) - 재시도
     * @return List<MstoreCanTrgDto>
     */
    List<MstoreCanTrgDto> selectMstoreCanListRty();
}
