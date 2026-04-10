package com.ktmmobile.mcp.content.service;

import com.ktmmobile.mcp.common.mplatform.dto.MoscOtpSvcInfoRes;
import com.ktmmobile.mcp.common.mplatform.dto.RegSvcChgRes;
import com.ktmmobile.mcp.common.mplatform.vo.MoscCombStatMgmtInfoOutVO;
import com.ktmmobile.mcp.content.dto.ReShareDataReqDto;

public interface ReShareDataSvc {

	/**
	 * x86 결합내역조회
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param reShareDataReqDto
	 * @return
	 */
	
	public MoscCombStatMgmtInfoOutVO selectMyShareDataList(ReShareDataReqDto reShareDataReqDto);

	/**
	 * 21. 부가서비스신청
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @param retvGubunCd
	 * @param otpNo
	 * @return
	 */
	
	public RegSvcChgRes regSvcChgNe(String ncn, String ctn, String custId,  String retvGubunCd, String otpNo);
	
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
	
	public MoscOtpSvcInfoRes moscOtpSvcInfo(String ncn, String ctn, String custId, String svcNo, String retvGubunCd);
}
