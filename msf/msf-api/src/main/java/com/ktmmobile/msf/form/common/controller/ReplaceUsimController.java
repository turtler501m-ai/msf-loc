//package com.ktmmobile.msf.common.controller;
//
//import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
//import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
//import com.ktmmobile.msf.common.dto.ReplaceUsimDto;
//import com.ktmmobile.msf.common.dto.ReplaceUsimSendDto;
//import com.ktmmobile.msf.common.mapper.ReplaceUsimMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//
//import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
//
//@RestController
//public class ReplaceUsimController {
//
//	private static final Logger logger = LoggerFactory.getLogger(ReplaceUsimController.class);
//
//	@Autowired
//	private ReplaceUsimMapper replaceUsimMapper;
//
//	@RequestMapping(value = "/usim/selectCustomerIdByConnInfo", method = RequestMethod.POST)
//	public List<String> selectCustomerIdByConnInfo(@RequestBody ReplaceUsimDto replaceUsimDto) {
//
//		List<String> customerIds = null;
//
//		try {
//			customerIds = replaceUsimMapper.selectCustomerIdByConnInfo(replaceUsimDto);
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return customerIds;
//	}
//
//	@RequestMapping(value = "/usim/selectReplaceUsimSubInfo", method = RequestMethod.POST)
//	public List<ReplaceUsimDto> selectReplaceUsimSubInfo(@RequestBody String customerId) {
//
//		List<ReplaceUsimDto> replaceUsimDtos = null;
//
//		try {
//			replaceUsimDtos = replaceUsimMapper.selectReplaceUsimSubInfo(customerId);
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return replaceUsimDtos;
//	}
//
//	@RequestMapping(value = "/usim/selectContractInfoForReplaceUsim", method = RequestMethod.POST)
//	public McpUserCntrMngDto selectContractInfoForReplaceUsim(@RequestBody ReplaceUsimDto replaceUsimDto) {
//
//		McpUserCntrMngDto mcpUserCntrMngDto = null;
//
//		try {
//			mcpUserCntrMngDto = replaceUsimMapper.selectContractInfoForReplaceUsim(replaceUsimDto);
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return mcpUserCntrMngDto;
//	}
//
//	@RequestMapping(value = "/usim/selectReplaceUsimSendList", method = RequestMethod.POST)
//	public List<ReplaceUsimSendDto> selectReplaceUsimSendList(@RequestBody Map<String, String> paramMap) {
//
//		List<ReplaceUsimSendDto> replaceUsimSendDtos = null;
//
//		try {
//			replaceUsimSendDtos = replaceUsimMapper.selectReplaceUsimSendList(paramMap);
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return replaceUsimSendDtos;
//	}
//
//	@RequestMapping(value = "/usim/selectNfcUsimYn", method = RequestMethod.POST)
//	public ReplaceUsimDto selectNfcUsimYn(@RequestBody String intmMdlId) {
//
//		ReplaceUsimDto replaceUsimDto = null;
//
//		try {
//			replaceUsimDto = replaceUsimMapper.selectNfcUsimYn(intmMdlId);
//		} catch(Exception e) {
//			throw new McpCommonJsonException(COMMON_EXCEPTION);
//		}
//
//		return replaceUsimDto;
//	}
//
//}
