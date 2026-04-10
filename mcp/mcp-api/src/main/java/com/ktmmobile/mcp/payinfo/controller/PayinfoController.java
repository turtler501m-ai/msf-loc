package com.ktmmobile.mcp.payinfo.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.payinfo.dto.EvidenceDto;
import com.ktmmobile.mcp.payinfo.dto.PayInfoDto;
import com.ktmmobile.mcp.payinfo.mapper.PayInfoMapper;
@RestController
public class PayinfoController {

	@Autowired
	PayInfoMapper payInfoMapper;

	private final Logger logger = LoggerFactory.getLogger(PayinfoController.class);

	/**
	 * 페이인포 시퀀스 채번
	 * @return
	 */
	@RequestMapping(value = "/payinfo/seq", method = RequestMethod.POST)
	public String seq() {

		String seq = null;
		try {
			seq = payInfoMapper.selectSeq();
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return seq;
	}

	/**
	 * 전화번호(개통, 일시정지) 정보 조회
	 * @param payInfoDto
	 * @return
	 */
	@RequestMapping(value = "/payinfo/mspJuoSubinfo", method = RequestMethod.POST)
	public EvidenceDto mspJuoSubinfo(PayInfoDto payInfoDto) {

		EvidenceDto mspJuoSubinfo = null;

		try {
			mspJuoSubinfo = payInfoMapper.selectMspJuoSubinfo(payInfoDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return mspJuoSubinfo;
	}

	/**
	 * CMN_KFTC_EVIDENCE 테이블 내 서비스계약번호 갯수 조회
	 * @param evidenceDto
	 * @return
	 */
	@RequestMapping(value = "/payinfo/checkEvidenceCount", method = RequestMethod.POST)
	public int checkEvidenceCount(EvidenceDto evidenceDto) {

		int checkEvidenceCount = 0;

		try {
			checkEvidenceCount = payInfoMapper.selectCheckEvidenceCount(evidenceDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return checkEvidenceCount;
	}

	/**
	 * CMN_KFTC_EVIDENCE 테이블 내 서비스계약정보 변경
	 * @param evidenceDto
	 * @return
	 */
	@RequestMapping(value = "/payinfo/modifyEvidence", method = RequestMethod.POST)
	public int modifyEvidence(EvidenceDto evidenceDto) {

		int updateCount = 0;

		try {
			updateCount = payInfoMapper.updateEvidence(evidenceDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return updateCount;
	}

	/**
	 * CMN_KFTC_EVIDENCE 테이블 데이터 등록
	 * @param evidenceDto
	 * @return
	 */
	@RequestMapping(value = "/payinfo/addEvidence", method = RequestMethod.POST)
	public int addEvidence(EvidenceDto evidenceDto) {

		int insertCount = 0;

		try {
			insertCount = payInfoMapper.insertEvidence(evidenceDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return insertCount;
	}

	/**
	 * MSP_PAYINFO_IMG 호출
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/payinfo/mspPayinfoImg", method = RequestMethod.POST)
	public Map mspPayinfoImg(Map<String, String> map) {

		Map mspPayinfoImg = new HashMap();

		try {
			mspPayinfoImg = payInfoMapper.callMspPayinfoImg(map);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return mspPayinfoImg;
	}

}
