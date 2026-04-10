package com.ktmmobile.mcp.storeusim.controller;

import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.storeusim.dto.KtRcgDto;
import com.ktmmobile.mcp.storeusim.dto.MspPlcyOperTypeDto;
import com.ktmmobile.mcp.storeusim.dto.MspSalePlcyMstDto;
import com.ktmmobile.mcp.storeusim.dto.UsimMspRateDto;
import com.ktmmobile.mcp.storeusim.mapper.StoreUsimMapper;
import com.ktmmobile.mcp.usim.dto.UsimBasDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

@RestController
public class StoreUsimController {


	@Autowired
	StoreUsimMapper storeUsimMapper;

	/**
	 * 잔액조회 하기전에 KT의 SEQ 조회
	 * @param KtRcgDto
	 * @return KtRcgDto
	 */
	@RequestMapping(value = "/storeUsim/ktRcgSeq", method = RequestMethod.POST)
	public KtRcgDto ktRcgSeq(KtRcgDto param) {

		KtRcgDto ktRcgSeq = null;

		try {

			// Database 에서 조회함.
			ktRcgSeq = storeUsimMapper.selectKtRcgSeq(param);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return ktRcgSeq;
	}

	/**
	 * 잔액조회
	 * @param Map<String, Object>
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/storeUsim/ktRcg", method = RequestMethod.POST)
	public Map<String, Object> ktRcg(Map<String, Object> param) {

		Map<String, Object> ktRcg = null;

		try {

			// Database 에서 조회함.
			ktRcg = storeUsimMapper.selectRcg(param);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return ktRcg;
	}

	/**
	 * USIM 상품 모델조회
	 * @param UsimBasDto
	 * @return List<UsimBasDto>
	 */
	@RequestMapping(value = "/storeUsim/modelList", method = RequestMethod.POST)
	public List<UsimBasDto> modelList(@RequestBody UsimBasDto param) {

		List<UsimBasDto> modelList = null;

		try {

			// Database 에서 조회함.
			modelList = storeUsimMapper.selectModelList(param);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return modelList;
	}

	/**
	 * USIM 상품 가입비 조회
	 * @param UsimBasDto
	 * @return List<UsimBasDto>
	 */
	@RequestMapping(value = "/storeUsim/joinUsimPriceNew", method = RequestMethod.POST)
	public List<UsimMspRateDto> joinUsimPriceNew(@RequestBody String gubun) {

		List<UsimMspRateDto> joinUsimPriceNew = null;

		try {

			// Database 에서 조회함.
			joinUsimPriceNew = storeUsimMapper.selectJoinUsimPriceNew(gubun);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return joinUsimPriceNew;
	}

	/**
	 * USIM 상품 약정없는 할인율 조회
	 * @param UsimBasDto
	 * @return List<UsimBasDto>
	 */
	@RequestMapping(value = "/storeUsim/usimDcamt", method = RequestMethod.POST)
	public UsimMspRateDto usimDcamt(String rateCd) {

		UsimMspRateDto usimDcamt = null;

		try {

			// Database 에서 조회함.
			usimDcamt = storeUsimMapper.selectUsimDcamt(rateCd);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return usimDcamt;
	}

	/**
	 * USIM 약정할인 조회
	 * @param UsimBasDto
	 * @return List<UsimMspRateDto>
	 * 사용 여부 확인 필요
	 */
	@RequestMapping(value = "/storeUsim/agreeDcAmt", method = RequestMethod.POST)
	public List<UsimMspRateDto> agreeDcAmt(UsimBasDto param) {

		List<UsimMspRateDto> agreeDcAmt = null;

		try {

			// Database 에서 조회함.
			agreeDcAmt = storeUsimMapper.selectAgreeDcAmt(param);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return agreeDcAmt;
	}

	/**
	 * 핸드폰번호와 이름으로 사용 요금제 조회
	 * @param AuthSmsDto
	 * @return AuthSmsDto
	 * 사용 여부 확인 필요
	 */
	@RequestMapping(value = "/storeUsim/userChargeInfo", method = RequestMethod.POST)
	public AuthSmsDto userChargeInfo(AuthSmsDto param) {

		AuthSmsDto userChargeInfo = null;

		try {

			// Database 에서 조회함.
			userChargeInfo = storeUsimMapper.selectUserChargeInfo(param);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return userChargeInfo;
	}

	/**
	 * MSP 정책정보조회
	 * @param MspSalePlcyMstDto
	 * @return List<MspSalePlcyMstDto>
	 */
	@RequestMapping(value = "/storeUsim/usimSalePlcyCdList", method = RequestMethod.POST)
	public List<MspSalePlcyMstDto> usimSalePlcyCdList(@RequestBody MspSalePlcyMstDto param) {

		List<MspSalePlcyMstDto> usimSalePlcyCdList = null;

		try {

			// Database 에서 조회함.
			usimSalePlcyCdList = storeUsimMapper.selectUsimSalePlcyCdList(param);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return usimSalePlcyCdList;
	}

	/**
	 * MSP 정책정보조회
	 * @param UsimBasDto
	 * @return List<MspSalePlcyMstDto>
	 */
	@RequestMapping(value = "/storeUsim/plcyOperTypeList", method = RequestMethod.POST)
	public List<MspPlcyOperTypeDto> plcyOperTypeList(@RequestBody MspSalePlcyMstDto param) {

		List<MspPlcyOperTypeDto> plcyOperTypeList = null;

		try {

			// Database 에서 조회함.
			plcyOperTypeList = storeUsimMapper.selectPlcyOperTypeList(param);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return plcyOperTypeList;
	}

	/**
	 * 요금조회  다정책반영
	 * @param UsimBasDto
	 * @return List<UsimMspRateDto>
	 */
	@RequestMapping(value = "/storeUsim/rateListMoreTwoRows", method = RequestMethod.POST)
	public List<UsimMspRateDto> rateListMoreTwoRows(@RequestBody UsimBasDto param) {

		List<UsimMspRateDto> rateListMoreTwoRows = null;

		try {

			// Database 에서 조회함.
			rateListMoreTwoRows = storeUsimMapper.selectRateListMoreTwoRows(param);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return rateListMoreTwoRows;
	}

    /**
    * @Description : Usimbas 테이블조회
    * @param UsimBasDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 29.
    */
	@RequestMapping(value = "/storeUsim/usimBasList", method = RequestMethod.POST)
	public List<UsimBasDto> usimBasList(@RequestBody UsimBasDto param) {
		List<UsimBasDto> usimBasList = null;

		try {

			// Database 에서 조회함.
			usimBasList = storeUsimMapper.selectUsimBasList(param);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return usimBasList;

	}


	@RequestMapping(value ="/storeUsim/failUsim", method = RequestMethod.POST)
	public int selectFailUsims(@RequestBody String iccId) {
		int failUsim;
		try {
   			failUsim = this.storeUsimMapper.selectFailUsims(iccId);

		} catch (Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		return failUsim;
	}

	@RequestMapping(value = "/storeUsim/updateFailUsim", method = RequestMethod.POST)
	public int updateFailUsim(@RequestBody JuoSubInfoDto juoSubInfoDto) {
		int upCnt;
		try {
			upCnt = this.storeUsimMapper.updateFailUsim(juoSubInfoDto);

		} catch (Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		return upCnt;
	}


}
