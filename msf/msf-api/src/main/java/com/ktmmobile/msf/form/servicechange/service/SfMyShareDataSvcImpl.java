package com.ktmmobile.msf.form.servicechange.service;

import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;
import java.net.SocketTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscDataSharingResDto;
import com.ktmmobile.msf.form.servicechange.dto.MyShareDataReqDto;

@Service
public class SfMyShareDataSvcImpl implements SfMyShareDataSvc {

	private static final Logger logger = LoggerFactory.getLogger(SfMyShareDataSvcImpl.class);

	@Autowired
	private MplatFormService mplatFormService;

	/**
	 * x69 데이터 쉐어링 사전체크
	 */
	@Override
	public MoscDataSharingResDto moscDataSharingChk(MyShareDataReqDto myShareDataReqDto) {

		MoscDataSharingResDto moscDataSharingResDto = new MoscDataSharingResDto();
		String custId = "";
		String ncn = "";
		String ctn = "";
		String crprCtn = "";

		try {
			custId = myShareDataReqDto.getCustId();
			ncn = myShareDataReqDto.getNcn();
			ctn = myShareDataReqDto.getCtn();
			crprCtn = myShareDataReqDto.getOpmdSvcNo();

			moscDataSharingResDto = mplatFormService.moscDataSharingChk(custId, ncn, ctn, crprCtn);

		} catch (SocketTimeoutException e1) {
			throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
		} catch (SelfServiceException e1) {
			throw new McpCommonException(e1.getMessage());
		}

		return moscDataSharingResDto;
	}

	/**
	 * x70 데이터 쉐어링 저장 & 해지
	 */
	@Override
	public void moscDataSharingSave(MyShareDataReqDto myShareDataReqDto) {

		String custId = "";
		String ncn = "";
		String ctn = "";
		String opmdSvcNo = "";
		String opmdWorkDivCd = "";

		try {
			custId = myShareDataReqDto.getCustId();
			ncn = myShareDataReqDto.getNcn();
			ctn = myShareDataReqDto.getCtn();
			opmdSvcNo = myShareDataReqDto.getOpmdSvcNo();
			opmdWorkDivCd = myShareDataReqDto.getOpmdWorkDivCd();

			mplatFormService.moscDataSharingSave(custId, ncn, ctn, opmdSvcNo, opmdWorkDivCd);

		} catch (SocketTimeoutException e1) {
			throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
		} catch (SelfServiceException e1) {
			throw new McpCommonException(e1.getMessage());
		}
	}

	/**
	 * x71 데이터쉐어링 결합중인 대상 조회
	 */
	@Override
	public MoscDataSharingResDto mosharingList(MyShareDataReqDto myShareDataReqDto) {

		MoscDataSharingResDto res = new MoscDataSharingResDto();
		String custId = "";
		String ncn = "";
		String ctn = "";

		try {
			custId = myShareDataReqDto.getCustId();
			ncn = myShareDataReqDto.getNcn();
			ctn = myShareDataReqDto.getCtn();

			res = mplatFormService.mosharingList(custId, ncn, ctn);

		} catch (SocketTimeoutException e1) {
			throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
		} catch (SelfServiceException e1) {
			throw new McpCommonException(e1.getMessage());
		}

		return res;
	}

}
