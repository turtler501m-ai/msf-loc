//package com.ktmmobile.msf.common.controller;
//
//import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import com.ktmmobile.msf.common.dto.PhoneSntyBasDto;
//import org.apache.ibatis.session.RowBounds;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
//import com.ktmmobile.msf.common.dto.CommonSearchDto;
//import com.ktmmobile.msf.common.dto.PhoneMspDto;
//import com.ktmmobile.msf.common.dto.PhoneProdBasDto;
//import com.ktmmobile.msf.common.dto.PhoneSesplsDto;
//import com.ktmmobile.msf.common.mapper.PhoneMapper;
//
//@RestController
//public class PhoneController {
//
//	private static final Logger logger = LoggerFactory.getLogger(PhoneController.class);
//
//	@Autowired
//	PhoneMapper phoneMapper;
//
//	/**
//	 * 정책 판매중인 핸드폰 리스트 정보 조회 only msp db링크를 통한 조회만 한다.
//	 * @param CommonSearchDto param
//	 * @return
//	 */
//	@RequestMapping(value = "/phone/phoneMspDto", method = RequestMethod.POST)
//	public List<PhoneMspDto> pHoneMspDto(@RequestBody CommonSearchDto commonSearchDto) {
//
//		List<PhoneMspDto> result = null;
//
//		try {
//			// Database 에서 조회함.
//			result = phoneMapper.listPhoneMspDto(commonSearchDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return result;
//	}
//	
//	/**
//	 * 휴대폰 검색 개수 조회
//	 * @param CommonSearchDto param
//	 * @return
//	 */
//	@RequestMapping(value = "/phone/phoneProdsCount", method = RequestMethod.POST)
//	public int phoneProdsCount(@RequestBody CommonSearchDto commonSearchDto) {
//
//		int result = 0;
//
//		try {
//			// Database 에서 조회함.
//			result = phoneMapper.listPhoneProdsCount(commonSearchDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return result;
//	}
//	
//	/**
//	 * 핸드폰 상품관리 리스트 조회
//	 * @param CommonSearchDto param
//	 * @return
//	 */
//	@RequestMapping(value = "/phone/phoneProd", method = RequestMethod.POST)
//	public List<PhoneProdBasDto> phoneProd(@RequestBody CommonSearchDto commonSearchDto) {
//
//		// skipResult, maxResult 값은 dto 에 포함
//		int skipResult = commonSearchDto.getApiParam1();
//		int maxResult = commonSearchDto.getApiParam2();
//
//		List<PhoneProdBasDto> result = null;
//
//		try {
//			// Database 에서 조회함.
//			result = phoneMapper.listphoneProd(commonSearchDto , new RowBounds(skipResult, maxResult));
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return result;
//	}
//	
//	/**
//	 * 핸드폰 상품관리 상세 조회.
//	 * @param CommonSearchDto param
//	 * @return
//	 */
//	@RequestMapping(value = "/phone/nmcpProdBas", method = RequestMethod.POST)
//	public PhoneProdBasDto nmcpProdBas(@RequestBody PhoneProdBasDto phoneProdBasDto) {
//
//		PhoneProdBasDto result = null;
//
//		try {
//			// Database 에서 조회함.
//			result = phoneMapper.findNmcpProdBas(phoneProdBasDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return result;
//	}
//
//	/**
//	 * 핸드폰 상품순서관리
//	 * @param CommonSearchDto param
//	 * @return
//	 */
//	@RequestMapping(value = "/phone/phoneProdForSort", method = RequestMethod.POST)
//	public List<PhoneProdBasDto> phoneProdForSort(@RequestBody CommonSearchDto commonSearchDto) {
//
//		List<PhoneProdBasDto> result = null;
//
//		try {
//			// Database 에서 조회함.
//			result = phoneMapper.listphoneProdForSort(commonSearchDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return result;
//	}
//
//	/**
//	 * 핸드폰 상품순서관리
//	 * @param CommonSearchDto param
//	 * @return
//	 */
//	@RequestMapping(value = "/phone/phoneProdBasForFront", method = RequestMethod.POST)
//	public List<PhoneProdBasDto> phoneProdBasForFront(@RequestBody CommonSearchDto commonSearchDto) {
//
//		List<PhoneProdBasDto> result = null;
//
//		try {
//			// Database 에서 조회함.
//			result = phoneMapper.listPhoneProdBasForFront(commonSearchDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return result;
//	}
//
//	/**
//	 * 핸드폰 상품순서관리
//	 * @param CommonSearchDto param
//	 * @return
//	 */
//	@RequestMapping(value = "/phone/phoneProdBasForFrontOneQuery", method = RequestMethod.POST)
//	public List<PhoneProdBasDto> phoneProdBasForFrontOneQuery(@RequestBody CommonSearchDto commonSearchDto) {
//
//		ObjectMapper om = new ObjectMapper();
//
//		List<PhoneProdBasDto> result = null;
//
//		String sesplsYn = commonSearchDto.getSesplsYn();
//		if(sesplsYn == null) {
//			sesplsYn = "N";
//		}
//		try {
//			// Database 에서 조회함.
//			
//			if("Y".equals(sesplsYn)) {
//				// 자급제인 경우
//				result = phoneMapper.listPhoneProdBasForFrontOneQuery2(commonSearchDto);
//			} else {
//				// 휴대폰, 중고폰인 경우
//				result = phoneMapper.listPhoneProdBasForFrontOneQuery(commonSearchDto);				
//			}
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return result;
//	}
//
//	/**
//	 * 핸드폰 상품순서관리
//	 * @param CommonSearchDto param
//	 * @return
//	 */
//	@RequestMapping(value = "/phone/lte3GphoneCount", method = RequestMethod.POST)
//	public Map<String, Integer> lte3GphoneCount(@RequestBody CommonSearchDto commonSearchDto) {
//
//
//		Map<String, Integer> result = null;
//
//		try {
//			// Database 에서 조회함.
//			result = phoneMapper.findLte3GphoneCount(commonSearchDto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return result;
//	}
//
//	/**
//	 * 자급제 입고가
//	 * @param dto
//	 * @return
//	 */
//	@RequestMapping(value = "/phone/inUnitPric", method = RequestMethod.POST)
//	public PhoneSesplsDto selectInUnitPric(@RequestBody CommonSearchDto dto) {
//
//		PhoneSesplsDto result = null;
//
//		try {
//			// Database 에서 조회함.
//			result = phoneMapper.selectInUnitPricQuery(dto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return result;
//	}
//
//
//	/**
//	 * 자급제 출고가
//	 * @param dto
//	 * @return
//	 */
//	@RequestMapping(value = "/phone/outUnitPric", method = RequestMethod.POST)
//	public PhoneSesplsDto selectOutUnitPric(@RequestBody CommonSearchDto dto) {
//
//		PhoneSesplsDto result = null;
//
//		try {
//			// Database 에서 조회함.
//			result = phoneMapper.selectOutUnitPricQuery(dto);
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return result;
//	}
//
//	@RequestMapping(value = "/phone/planPhoneProd", method = RequestMethod.POST)
//	public List<PhoneProdBasDto> selectRepModelIdList(@RequestBody CommonSearchDto commonSearchDto) {
//
//		// skipResult, maxResult 값은 dto 에 포함
//		int skipResult = commonSearchDto.getApiParam1();
//		int maxResult = commonSearchDto.getApiParam2();
//
//		List<PhoneProdBasDto> result = null;
//
//		try {
//			// Database 에서 조회함.
//			result = phoneMapper.selectRepModelIdList(commonSearchDto , new RowBounds(skipResult, maxResult));
//
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return result;
//	}
//
//	@RequestMapping(value = "/phone/selectPhonePordSnty", method = RequestMethod.POST)
//	public List<PhoneSntyBasDto> selectPhonePordSnty(@RequestBody PhoneSntyBasDto phoneProdBasDto) {
//		List<PhoneSntyBasDto> phoneProdSntyDtos = new ArrayList<>();
//		try {
//			phoneProdSntyDtos = phoneMapper.selectPhonePordSnty(phoneProdBasDto);
//		} catch (Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//		return phoneProdSntyDtos;
//	}
//}
