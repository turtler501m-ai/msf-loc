package com.ktmmobile.mcp.storeusim.mapper;

import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.storeusim.dto.KtRcgDto;
import com.ktmmobile.mcp.storeusim.dto.MspPlcyOperTypeDto;
import com.ktmmobile.mcp.storeusim.dto.MspSalePlcyMstDto;
import com.ktmmobile.mcp.storeusim.dto.UsimMspRateDto;
import com.ktmmobile.mcp.usim.dto.UsimBasDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StoreUsimMapper {

    /**
     * 잔액조회 하기전에 KT의 SEQ 조회
     * @param KtRcgDto
     * @return KtRcgDto
     * @throws Exception
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
     * @param Map<String, Object>
     * @return Map<String, Object>
     * @throws Exception
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
     * USIM 상품 모델조회
     * @param UsimBasDto
     * @return List<UsimBasDto>
     * @throws Exception
     */
	public List<UsimBasDto> selectModelList(UsimBasDto usimBasDto);

    /**
    * USIM 상품 가입비 조회
    * @param gubun
    * @return List<UsimMspRateDto>
    * @throws Exception
    */
	public List<UsimMspRateDto> selectJoinUsimPriceNew(String gubun);

    /**
    * USIM 상품 약정없는 할인율 조회
    * @param rateCd
    * @return UsimMspRateDto
    * @throws Exception
    */
	public UsimMspRateDto selectUsimDcamt(String rateCd);

    /**
    * USIM 약정할인 조회
    * @param UsimBasDto
    * @return List<UsimMspRateDto>
    * @throws Exception
    */
	public List<UsimMspRateDto> selectAgreeDcAmt(UsimBasDto usimBasDto);

    /**
    * 핸드폰번호와 이름으로 사용 요금제 조회
    * @param AuthSmsDto
    * @return AuthSmsDto
    */
	public AuthSmsDto selectUserChargeInfo(AuthSmsDto authSmsDto);

    /**
    * MSP 정책정보조회
    * @param MspSalePlcyMstDto
    * @return List<MspSalePlcyMstDto>
    */
	public List<MspSalePlcyMstDto> selectUsimSalePlcyCdList(MspSalePlcyMstDto mspSalePlcyMstDto);

    /**
    * USIM 가입유형 조회
    * @param UsimBasDto
    * @return List<UsimBasDto>
    */
	public List<MspPlcyOperTypeDto> selectPlcyOperTypeList(MspSalePlcyMstDto mspSalePlcyMstDto);

    /**
    * 요금조회  다정책반영
    * @param UsimBasDto
    * @return List<UsimMspRateDto>
    */
	public List<UsimMspRateDto> selectRateListMoreTwoRows(UsimBasDto usimBasDto);

    /**
     * Usimbas 테이블조회
     * @param UsimBasDto
     * @return List<UsimBasDto>
     */
	public List<UsimBasDto> selectUsimBasList(UsimBasDto usimBasDto);

    int selectFailUsims(String iccId);

    int updateFailUsim(JuoSubInfoDto juoSubInfoDto);
}
