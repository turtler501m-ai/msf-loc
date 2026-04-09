//package com.ktmmobile.msf.common.mapper;
//
//import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
//import com.ktmmobile.msf.common.dto.ReplaceUsimDto;
//import com.ktmmobile.msf.common.dto.ReplaceUsimSendDto;
//import org.apache.ibatis.annotations.Mapper;
//
//import java.util.List;
//import java.util.Map;
//
//@Mapper
//public interface ReplaceUsimMapper {
//
//	List<String> selectCustomerIdByConnInfo(ReplaceUsimDto replaceUsimDto);
//
//	List<ReplaceUsimDto> selectReplaceUsimSubInfo(String customerId);
//
//	McpUserCntrMngDto selectContractInfoForReplaceUsim(ReplaceUsimDto replaceUsimDto);
//
//	List<ReplaceUsimSendDto> selectReplaceUsimSendList(Map<String, String> paramMap);
//
//	ReplaceUsimDto selectNfcUsimYn(String intmMdlId);
//}
