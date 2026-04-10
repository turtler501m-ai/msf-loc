package com.ktmmobile.mcp.content.service;
import com.ktmmobile.mcp.common.mplatform.vo.MoscCombStatMgmtInfoOutVO.OutWireDto;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.INVALID_REFERER_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.common.dto.McpIpStatisticDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.dto.MoscOtpSvcInfoRes;
import com.ktmmobile.mcp.common.mplatform.dto.RegSvcChgRes;
import com.ktmmobile.mcp.common.mplatform.vo.MoscCombStatMgmtInfoOutVO;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.content.dto.ReShareDataReqDto;

@Service
public class ReShareDataSvcImpl implements ReShareDataSvc{

	private static final Logger logger = LoggerFactory.getLogger(ReShareDataSvcImpl.class);

	@Autowired
	private MplatFormService mplatFormService;

    @Autowired
    private IpStatisticService ipstatisticService ;

    /**
     * x86 결합내역조회
     * @author bsj
     * @Date : 2021.12.30
     * @param reShareDataReqDto
     * @return
     */
    
	@Override
	public MoscCombStatMgmtInfoOutVO selectMyShareDataList(ReShareDataReqDto reShareDataReqDto) {
		MoscCombStatMgmtInfoOutVO moscCombStatMgmtInfoOutVO = new MoscCombStatMgmtInfoOutVO();
		
		try {
			
			String custId= reShareDataReqDto.getCustId();
			String ncn = reShareDataReqDto.getNcn();
			String ctn = reShareDataReqDto.getCtn();
			String retvGubunCd = reShareDataReqDto.getRetvGubunCd();

			List<MoscCombStatMgmtInfoOutVO.OutGiveListDto>  outGiveDtoList = new ArrayList<MoscCombStatMgmtInfoOutVO.OutGiveListDto>(); // 주기회선으로 조회시
			
			  
			List<MoscCombStatMgmtInfoOutVO.OutRcvListDto>  outRcvDtoList = new ArrayList<MoscCombStatMgmtInfoOutVO.OutRcvListDto>(); // 받기회선으로 조회시
			OutWireDto outWireDtoVo = new OutWireDto();
			MoscCombStatMgmtInfoOutVO resDto = mplatFormService.moscCombStatMgmtInfo(ncn,ctn,custId,retvGubunCd); // ncn, ctn, custid ==> 1건결합

			 if(resDto !=null){

	               outGiveDtoList = resDto.getOutGiveDtoList();
	               outWireDtoVo = resDto.getOutWireDtoVo();
	               if( outGiveDtoList !=null && !outGiveDtoList.isEmpty()){
	                   for(int i=0; i<outGiveDtoList.size(); i++){
	                       String rcvSvcNo = StringUtil.NVL(outGiveDtoList.get(i).getRcvSvcNo(),"");
	                       rcvSvcNo = StringMakerUtil.getPhoneNum(rcvSvcNo);
	                       outGiveDtoList.get(i).setRcvSvcNo(rcvSvcNo);
	                   }
	               }
	
	               outRcvDtoList = resDto.getOutRcvDtoList();
	               if( outRcvDtoList !=null && !outRcvDtoList.isEmpty()){
	                   for(int i=0; i<outRcvDtoList.size(); i++){
	                       String rcvSvcNo = StringUtil.NVL(outRcvDtoList.get(i).getRcvSvcNo(),"");
	                       String giveSvcNo = StringUtil.NVL(outRcvDtoList.get(i).getGiveSvcNo(),"");
	                       rcvSvcNo = StringMakerUtil.getPhoneNum(rcvSvcNo);
	                       giveSvcNo = StringMakerUtil.getPhoneNum(giveSvcNo);
	                       outRcvDtoList.get(i).setRcvSvcNo(rcvSvcNo);
	                       outRcvDtoList.get(i).setGiveSvcNo(giveSvcNo);
	                   }
	               }
	               moscCombStatMgmtInfoOutVO.setOutWireDtoVo(outWireDtoVo);
	               moscCombStatMgmtInfoOutVO.setOutGiveDtoList(outGiveDtoList);
	               moscCombStatMgmtInfoOutVO.setOutRcvDtoList(outRcvDtoList);
	               
	           }
		} catch(SocketTimeoutException e) {
			throw new McpCommonException(COMMON_EXCEPTION);
		} catch (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
		}
		return moscCombStatMgmtInfoOutVO;
	}

	/**
	 * x21 부가서비스 신청
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @param retvGubunCd
	 * @param otpNo
	 * @return
	 */
	
	@Override 
	public RegSvcChgRes regSvcChgNe(String ncn, String ctn, String custId,  String retvGubunCd, String otpNo) {
		RegSvcChgRes regSvcChgSelfVO = new RegSvcChgRes();
	
	   String soc = "";
       try {
    	   if( "G".equals(retvGubunCd) ){
    		   soc = "PL208J938";
    	   } else if( "R".equals(retvGubunCd) ){
    		   soc = "PL208J939";
    	   } else {
    		   throw new McpCommonJsonException("0002" , INVALID_REFERER_EXCEPTION);
    	   }
           regSvcChgSelfVO = mplatFormService.regSvcChgNe(ncn, ctn, custId, soc, otpNo);
       } catch (SelfServiceException e) {
           throw new McpCommonJsonException("0003" , e.getMessage());
	   } catch (SocketTimeoutException e){
	      throw new McpCommonJsonException("0004" , SOCKET_TIMEOUT_EXCEPTION);
	   }
		
       return regSvcChgSelfVO;
	}

	/**
	 * x80 otp 인증 서비스 
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @param svcNo
	 * @param retvGubunCd
	 * @return
	 */
	@Override
	public MoscOtpSvcInfoRes moscOtpSvcInfo(String ncn, String ctn, String custId, String svcNo,  String retvGubunCd) {
	    MoscOtpSvcInfoRes otpSvc = new MoscOtpSvcInfoRes();

		try {
			   String dataVal1="";
	
	    	   if( "G".equals(retvGubunCd) ){
	    		   dataVal1 = "PL208J938";
	    	   } else if( "R".equals(retvGubunCd) ){
	    		   dataVal1 = "PL208J939";
	    	   } else {
	    		   throw new McpCommonJsonException("0002" , INVALID_REFERER_EXCEPTION);
	    	   }
	
	    	   // svcNo : keyIn 정보, searchVO : selectBox 정보
	    	   otpSvc = mplatFormService.moscOtpSvcInfo(ncn, ctn, custId,svcNo,dataVal1) ;
	    	   //로그 저장 처리
	    	   McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
	    	   mcpIpStatisticDto.setPrcsMdlInd("REQUEST_SHAER_DATA");
	    	   mcpIpStatisticDto.setParameter("NCN[" +ncn +"]CTN["+ ctn+"]SVC_NO["+ svcNo+"]RESLT_CD["+ otpSvc.getResltCd()+"]RESLT_MSG["+ otpSvc.getResltMsgSbst()+"]OPT_NO["+ otpSvc.getOtpNo()+"]");
	    	   mcpIpStatisticDto.setPrcsSbst(ncn+"");

	    	   if(otpSvc.isSuccess()) {
	    		   mcpIpStatisticDto.setTrtmRsltSmst("OTP_CALL");
	    		   ipstatisticService.insertAccessTrace(mcpIpStatisticDto);
	    	   } else {
	    		   mcpIpStatisticDto.setTrtmRsltSmst("OTP_CALL_FAIL");
	    		   ipstatisticService.insertAccessTrace(mcpIpStatisticDto);
	               throw new McpCommonJsonException("99",COMMON_EXCEPTION);

	    	   }
		 } catch (SelfServiceException e) {
	           throw new McpCommonJsonException("0003" , e.getMessage());
	     } catch (SocketTimeoutException e){
	           throw new McpCommonJsonException("0004" , SOCKET_TIMEOUT_EXCEPTION);
	     }
		return otpSvc;
	}
}
