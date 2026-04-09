package com.ktmmobile.msf.form.servicechange.service;

import com.ktmmobile.msf.common.dto.MoscPymnReqDto;
import com.ktmmobile.msf.common.mplatform.vo.MpFarPaymentInfoVO;
import com.ktmmobile.msf.common.mplatform.vo.PaymentInfoVO;

import java.net.SocketTimeoutException;

public interface MsfRealTimePayService {

	/**
	 * x92 당월요금+미납요금 조회(X92)
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @return
	 */

	public PaymentInfoVO moscCurrMthNpayInfo(String ncn, String ctn, String custId) ;

	/**
	 * x67 납부/미납요금 조회 연동 규격
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @return
	 */

	public PaymentInfoVO farPaymentInfo(String ncn, String ctn, String custId) ;

	/**
	 * x22 납부/미납요금 조회 연동 규격
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @return
	 */

	public MpFarPaymentInfoVO farPaymentInfoList(String ncn, String ctn, String custId,String startDate,String endDate) throws SocketTimeoutException;

	/**
	 * x68 미납요금 즉시수납처리
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param moscPymnReqDto
	 * @return
	 */

	public String moscPymnTreat(MoscPymnReqDto moscPymnReqDto);

	/**
	 * X93 당월요금+미납요금 납부(X93)
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param moscPymnReqDto
	 * @return
	 */

	public String moscCurrMthNpayTreat(MoscPymnReqDto moscPymnReqDto);

}
