package com.ktmmobile.mcp.appform.mapper;

import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.storeusim.dto.MspSalePlcyMstDto;
import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.appform.dto.AppformReqDto;
import com.ktmmobile.mcp.appform.dto.FrndInviUsimDto;
import com.ktmmobile.mcp.appform.dto.IntmInsrRelDTO;
import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
import com.ktmmobile.mcp.appform.dto.McpRequestDto;

@Mapper
public interface AppformMapper {

    int checkLimitOpenFormCount( AppformReqDto appformReqDto);
    /**
     * 제주항공 요금제 여부 확인
     * @param rateCd
     * @return
     * @throws Exception
     */
    int checkJejuCodeCount(String rateCd);

    /**
     * 서식지 관련 MSP 코드 조회
     * @param appformReqDto
     * @return
     * @throws Exception
     */
    McpRequestDto selectMspPrdtCode(AppformReqDto appformReqDto);

    /**
     *
     * @param requestKey
     * @return
     * @throws Exception
     */
    Map<String,Object> selectRequestJoinDataByRjoinKeyNew(long requestKey);

    /**
     * 기기변경 고객정보 확인 일반기변 , 우수기변
     * @param juoSubInfoDto
     * @return
     * @throws Exception
     */
    JuoSubInfoDto selectSelRMemberAjax(JuoSubInfoDto juoSubInfoDto);

    /**
     *
     * @param juoSubInfoDto
     * @return
     * @throws Exception
     */
    JuoSubInfoDto selectSelRMemberAjaxReal(JuoSubInfoDto juoSubInfoDto);

    /**
     *
     * @param appformReqDto
     * @return
     * @throws Exception
     */
    String selectSelPrdtcolCd(AppformReqDto appformReqDto);

    /**
     * 오픈마켓 (외부서식지)
     * @param appformReqDto
     * @return
     * @throws Exception
     */
    AppformReqDto selectMarketRequest(AppformReqDto appformReqDto);

    /**
     * 할부개월
     * @param appformReqDto
     * @return
     * @throws Exception
     */
    List<AppformReqDto> selectModelMonthlyList(AppformReqDto appformReqDto);

    /**
     * 약정
     * @param appformReqDto
     * @return
     * @throws Exception
     */
    List<AppformReqDto> selectMonthlyListMarket(AppformReqDto appformReqDto);

    /**
     * 색상정보
     * @param appformReqDto
     * @return
     * @throws Exception
     */
    List<AppformReqDto> selectPrdtColorList(AppformReqDto appformReqDto);

    /**
     * 대리점 코드 패치
     * @param cntpntShopId
     * @return
     * @throws Exception
     */
    Map<String,String> selectAgentCode(String cntpntShopId);

    /**
     *
     * @return
     * @throws Exception
     */
    List<AppformReqDto> selectInsrCodeList();

    /**
     * 분실파손 보험 조회
     * @param intmInsrRelDTO
     * @return
     * @throws Exception
     */
    List<IntmInsrRelDTO> selectInsrProdList(IntmInsrRelDTO intmInsrRelDTO);

    /**
     * 고객CI정보에 대한 개통  정보 추출 [다회선 제한 기능]
     *  1. 셀프개통 여부
     *  2. 신규서식지
     *  3. 개통이 기준 +90일 이내
     * @param appformReqDto
     * @return
     * @throws Exception
     */
    AppformReqDto selectLimitForm(AppformReqDto appformReqDto);

    /**
     * 프로모션 관련 부가 서비스 등록 처리 (MCP_REQUEST_ADDITION)
     * @param appformReqDto
     * @return
     * @throws Exception
     */
    int insertMcpRequestAdditionPromotion(AppformReqDto appformReqDto);


    /**
     * 유심 유효성 체크
     * @param usimNo 유심번호
     * @return count 데이터가 있으면 사용불가 유심
     */
    int checkValidUsimNo(String usimNo);

    int selectStolenIp(String stolenIp);

    int chkCstmrInfo(FrndInviUsimDto frndInviUsimDto);

    /**
     * 셀프개통 프로모션 아이디 가져오기
     * @param disApdParam
     * @return List<String>
     */
    List<String> getChrgPrmtId(AppformReqDto disApdParam);

    /**
     * 평생할인 프로모션 기적용 insert
     * @param disApdParam
     * @return int
     */
    int insertDisPrmtApd(AppformReqDto disApdParam);

    /**
     * 오프라인 평생할인 프로모션ID 조회
     * @param disApdParam
     * @return List<String>
     */
    List<String> getDisPrmtId(AppformReqDto disApdParam);

    /**
     * 가입 유형 조회
     * @param disApdParam
     * @return List<String>
     */
    String getDisPrmtSlsTp(AppformReqDto disApdParam);

    boolean existsAbuseImei(String imei);

    List<MspSalePlcyMstDto> getSalePlcyInfo(AppformReqDto appformReqDto);

    /**
     * (대리점)안면인증 가능 접점ID조회
     * @param cntpntShopId
     * @return
     * @throws Exception
     */
    String getCpntId(String cntpntShopId);
    
}
