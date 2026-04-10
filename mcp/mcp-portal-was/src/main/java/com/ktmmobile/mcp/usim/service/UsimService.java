package com.ktmmobile.mcp.usim.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.db.McpRequestSelfDlvryDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspPlcyOperTypeDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleAgrmMst;
import com.ktmmobile.mcp.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.mcp.rate.dto.MapWrapper;
import com.ktmmobile.mcp.rate.dto.RateAdsvcGdncBasDTO;
import com.ktmmobile.mcp.usim.dto.KtRcgDto;
import com.ktmmobile.mcp.usim.dto.UsimBasDto;
import com.ktmmobile.mcp.usim.dto.UsimMspPlcyDto;
import com.ktmmobile.mcp.usim.dto.UsimMspRateDto;

public interface UsimService {
    /**
    * @Description : NMCP_USIM_BAS 의 USIM 흥보 글 조회
    * @param usimBasDto
    * @return NMCP_USIM_BAS 테이블에 정보를 조회한다.
    * @Author : ant
    * @Create Date : 2016. 01. 18.
    */
    public UsimBasDto selectUsimBasDto(UsimBasDto usimBasDto);

    /**
    * @Description : USIM 판매정책 리스트 정보 조회 only msp db링크를 통한 조회 한다.
    * @param
    * @return MSP_SALE_PLCY_MST@DL_MSP테이블에 정보를 조회한다.
    * @Author : ant
    * @Create Date : 2016. 01. 18.
    */
    public List<UsimMspPlcyDto> listUsimMspPlcyDto();

    /**
     * 잔액조회 하기전에 KT의 SEQ 조회
     * @method	: selectRcgInternalSolution
     * @date	: 2014. 5. 28.
     * @author	: dev
     * @history	:
     *
     * @comment :
     *	KTBILL.P_REMAINS_QUERY_B2C
     *	(
     *		I_REQ_TYPE				IN		VARCHAR2,      --요청구분(잔액조회: WEB_REMAINS_VIEW)
     *		I_SUBSCRIBER_NO		IN		VARCHAR2,      --CTN
     *		I_REQ_IP					IN		VARCHAR2,      -- 요청IP
     *		O_RCG_SEQ					OUT	VARCHAR2,
     *		O_RET_CODE				OUT	VARCHAR2,
     *		O_RET_MSG				OUT	VARCHAR2
     *	)
     *
     * @param rcgNo
     * @return
     */
    public KtRcgDto selectKtRcgSeq(KtRcgDto ktRcgDto);

    /**
     * 잔액조회
     * @method	: selectRcg_InternalSolution
     * @date	: 2014. 5. 28.
     * @author	: dev
     * @history	:
     *
     * @comment :
     *	KTBILL.P_RCG_QUERY_B2C
     *	(
     *		I_RCG_SEQ				IN		VARCHAR2,      -- KT_IN_REQ 시퀀스
     *		O_RES_CODE			OUT	VARCHAR2,      --KT응답>>KT 응답 코드 (000=성공, XXXX = 실패, ), Application 데몬 에러코드 (-1 ~ xxx)
     *		O_AMOUNT				OUT	VARCHAR2,      --KT응답>충전금액
     *		O_OLD_REMAINS		OUT	VARCHAR2,      -- KT응답>>충전 전 잔액
     *		O_REMAINS				OUT	VARCHAR2,      -- KT응답>>충전 후 잔액 === 또는 현재 잔액
     *		O_OLD_EXPIRE			OUT	VARCHAR2,      -- KT응답>>충전 전 유효기간
     *		O_EXPIRE				OUT	VARCHAR2,      -- KT응답>>충전 후 유효기간, 또는 현재 유효기간
     *		O_RET_CODE			OUT	VARCHAR2,
     *		O_RET_MSG			OUT	VARCHAR2
     *	)
     *
     * @param rcgNo
     * @return
     */
    public Map<String, Object> selectRcg(Map<String, Object> map);



    /**
    * @Description : USIM 판매정책코드 가져오기
    * @param usimMspDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 01. 18.
    */
    public List<UsimBasDto> selectNmcpUsimBasShopList(UsimBasDto usimBasDto);

    /**
    * @Description : USIM 가입유형 조회
    * @param usimMspDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 01. 18.
    */
    public List<UsimBasDto> selectPlcyOperTypeList(UsimBasDto usimBasDto);

    /**
    * @Description : USIM 상품 요금제 조회
    * @param usimMspDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 01. 18.
    */
    public List<UsimMspRateDto> selectRateList(UsimBasDto usimBasDto);


    /**
    * @Description : USIM 상품 통합조회(판매정책,가입유형,상품요금제)
    * @param HashMap
    * @return
    * @Author : ant
    * @Create Date : 2016. 01. 18.
    */
    public HashMap<String, Object> selectUsimBasShopListAll(UsimBasDto usimBasDto);


    /**
    * @Description : USIM 상품 통합조회(판매정책,가입유형,상품요금제)
    * @param HashMap
    * @return
    * @Author : ant
    * @Create Date : 2016. 01. 18.
    */
    public List<UsimBasDto> selectModelList(UsimBasDto usimBasDto);


    /**
    * @Description : USIM 상품 가입비 조회
    * @param usimMspDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 01. 18.
    */
    public List<UsimMspRateDto> selectJoinUsimPriceNew(UsimBasDto usimBasDto);


    /**
     * @Description : USIM 상품 가입비 조회
     * 접점, 가입유형 , eSIM 경우 에 따라
     * 조건이 다른게 조회 처리
     * @param usimMspDto
     * @return
     * @Author : ant
     * @Create Date : 2016. 01. 18.
     */
     public Map<String, String> getSimInfo(UsimBasDto usimBasDto);

    /**
    * @Description : USIM 상품 약정없는 할인율 조회
    * @param usimMspDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 01. 18.
    */
    public UsimMspRateDto  selectUsimDcamt(String rateCd);


    /**
    * @Description : 핸드폰번호와 이름으로 사용 요금제 조회
    * @param AuthSmsDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 02. 02.
    */
    public AuthSmsDto selectUserChargeInfo(AuthSmsDto authSmsDto);


    /**
    * @Description : USIM 약정할인 조회
    * @param UsimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 02. 02.
    */
    public List<UsimMspRateDto> selectAgreeDcAmt(UsimBasDto usimBasDto);

    /**
    * @Description : 관리자에서 등록한 조건에 맞는
    * 한건의 유심정보를 가져온다.
    * @param dataType	데이터 type (LTE,3G)
    * @param payClCd	선불,후불구분코드 (PP:선불 , P0:후불)
    * @return 유심정보
    * @Author : ant
    * @Create Date : 2016. 2. 11.
    */
    public UsimBasDto findUsimBas(String dataType,String payClCd);


    /**
    * @Description : 유효정책에 속하는 상품정보 가져오기
    * @param UsimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 29.
    */
    public List<UsimBasDto> selectUsimPrdtList(UsimBasDto usimBasDto);


    /**
    * @Description : 상품아이디로 유효정책 가져오기
    * @param UsimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 29.
    */
    public List<UsimBasDto> selectUsimsalePlcyCdToPrdtList(UsimBasDto usimBasDto);


    /**
    * @Description : 유효정책으로 요금제 들고오기
    * @param UsimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 29.
    */
    public List<UsimBasDto> selectUsimNewRateList(UsimBasDto usimBasDto);

    /**
    * @Description : 유효정책으로 약정할인
    * @param UsimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 29.
    */
    public List<MspSaleAgrmMst> listMspSaleAgrmMst(String salePlcyCd);


    /**
    * @Description : Usimbas 테이블조회
    * @param UsimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 29.
    */
    public List<UsimBasDto> selectUsimBasList(UsimBasDto usimBasDto);


    /**
    * @Description : MSP 정책정보조회
    * @param UsimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 29.
    */
    public List<MspSalePlcyMstDto> selectUsimSalePlcyCdList(MspSalePlcyMstDto mspSalePlcyMstDto);


    /**
    * @Description : MSP 요금제 조회
    * @param UsimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 29.
    */
    public List<MspRateMstDto> selectMspRateMstList(MspSalePlcyMstDto mspSalePlcyMstDto);


    /**
    * @Description : MSP 가입유형조회
    * @param UsimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 29.
    */
    public List<MspPlcyOperTypeDto> selectPlcyOperTypeList(MspSalePlcyMstDto mspSalePlcyMstDto);



    /**
    * @Description : 배너용 정책조회
    * @param UsimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 29.
    */
    public List<MspSalePlcyMstDto> selectUsimSalePlcyCdBannerList(MspSalePlcyMstDto mspSalePlcyMstDto);



    /**
    * @Description : 약관조회
    * @param FormDtlDTO
    * @return
    * @Author : ant
    * @Create Date : 2016. 3. 17.
    */
    public FormDtlDTO selectTermsData();


    /**
    * @Description : 약정조회 다정책반영
    * @param usimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 4. 29.
    */
    public List<UsimBasDto> listMspSaleAgrmMstMoreTwoRows(UsimBasDto usimBasDto);


    /**
    * @Description : 요금제조회 다정책반영
    * @param usimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 4. 29.
    */
    public List<UsimMspRateDto> selectRateListMoreTwoRows(UsimBasDto usimBasDto);


    /**
     * 고객이 신청한 유심정보 조회하기
     * @return
     */
    public List<McpRequestSelfDlvryDto> selectUserUsimList(McpRequestSelfDlvryDto dupInfo);

    /**
     * 상품에 대한 요금제 정렬정보
     * @return
     */
    public List<UsimBasDto> selectMcpUsimProdSortList(UsimBasDto usimBasDto);


    public int insertMcpUsimProdSort(UsimBasDto usimBasDto);

    public List<MspSaleSubsdMstDto> listChargeInfoUsimXml(List<MspSaleSubsdMstDto> chargeList, String searchType);

    /**
     * 불량 유심 조회
     * @param iccId
     * @return
     */
    int selectFailUsims(String iccId);

    void updateFailUsims(JuoSubInfoDto juoSubInfo);
}
