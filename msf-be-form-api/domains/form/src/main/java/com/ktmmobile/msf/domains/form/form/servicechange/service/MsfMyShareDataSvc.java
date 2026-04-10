package com.ktmmobile.msf.domains.form.form.servicechange.service;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscDataSharingResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyShareDataReqDto;

public interface MsfMyShareDataSvc {

	/**
	 * x69 쉐어링 사전체크
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param myShareDataReqDto
	 * @return
	 */
	public MoscDataSharingResDto moscDataSharingChk(MyShareDataReqDto myShareDataReqDto);

	/**
	 * x70 쉐어링 가입&해지
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param myShareDataReqDto
	 */
	public void moscDataSharingSave(MyShareDataReqDto myShareDataReqDto);

	/**
	 * x71 데이터쉐어링 결합 중인 대상 조회
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param myShareDataReqDto
	 * @return
	 */
	public MoscDataSharingResDto mosharingList(MyShareDataReqDto myShareDataReqDto);

}
