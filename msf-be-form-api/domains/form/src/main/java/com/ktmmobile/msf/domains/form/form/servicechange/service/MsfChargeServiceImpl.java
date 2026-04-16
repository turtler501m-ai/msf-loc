package com.ktmmobile.msf.domains.form.form.servicechange.service;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarMonBillingInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarMonDetailInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarPriceInfoDetailItemVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarRealtimePayInfoVO;
import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

/**
 * 요금조회+실시간요금조회
 * @author bsj
 * @Date : 2021.12.30
 */
@Service
public class MsfChargeServiceImpl implements MsfChargeService {

	private static Logger logger = LoggerFactory.getLogger(MsfChargeServiceImpl.class);

	@Autowired
	private MsfMplatFormService mPlatFormService;

	/**
	 * x15 요금조회
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @param productionDate
	 * @return
	 */

	@Override
	public MpFarMonBillingInfoDto farMonBillingInfoDto(String ncn, String ctn, String custId, String productionDate) {
		MpFarMonBillingInfoDto billInfo = new MpFarMonBillingInfoDto();
		try {

			billInfo = mPlatFormService.farMonBillingInfoDto(ncn, ctn, custId, DateTimeUtil.getFormatString("yyyyMM"));
			if (!billInfo.isSuccess()) {// mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
				throw new McpCommonException(COMMON_EXCEPTION);
			}
			if (StringUtil.isNotEmpty(billInfo.getResultMessage())) {
				throw new SelfServiceException(billInfo.getResultMessage());
			}
		} catch (SelfServiceException e) {
			throw new McpCommonException(e.getMessage());
		} catch (SocketTimeoutException e) {
			throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
		}

		return billInfo;
	}

	/**
	 * X16 요금조회 상세조회
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @param billSeqNo
	 * @param billDueDateList
	 * @param billMonth
	 * @param billStartDate
	 * @param billEndDate
	 * @return
	 */

	@Override
	public MpFarMonDetailInfoDto farMonDetailInfoDto(String ncn, String ctn, String custId, String billSeqNo,
			String billDueDateList, String billMonth, String billStartDate, String billEndDate) {
		MpFarMonDetailInfoDto detailInfo = null;

		try {

            detailInfo = mPlatFormService.farMonDetailInfoDto(
            		ncn
                    , ctn
                    , custId
                    , billSeqNo
                    , billDueDateList
                    , billMonth
                    , billStartDate
                    , billEndDate
            );
            if(!detailInfo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                throw new McpCommonException(COMMON_EXCEPTION);
            }
		}catch(SelfServiceException e){
			throw new McpCommonException(e.getMessage());
		}catch(SocketTimeoutException e){
			throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
		}

		return detailInfo;
	}

	/**
	 * x17 요금 항목별 조회 연동
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @param billSeqNo
	 * @param billMonth
	 * @param messageLine
	 * @return
	 */

	@Override
	public MpFarPriceInfoDetailItemVO farPriceInfoDetailItem(String ncn, String ctn, String custId, String billSeqNo,
			String billMonth, String messageLine) {

		MpFarPriceInfoDetailItemVO item = null;

		 /*
         * KOS 변경 사항 수정
         * billSeqNo 의미 없으 "0"  으로 설정
         * DateTimeUtil.getFormatString("yyyyMM") ==>   X15에서 조회한 BillMonth 로 설정
         * 인자값 billSeqNo이 X15에서 조회한 BillMonth
         */
		try {
	        item = mPlatFormService.farPriceInfoDetailItem(ncn, ctn, custId,
	                 "0", billMonth
	                 , messageLine );


		 } catch (SelfServiceException e) {
			throw new McpCommonException(e.getMessage());
		 } catch (SocketTimeoutException e){
			throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
		 }

		return item;
	}

	/**
	 * x18 실시간 요금조회
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @return
	 * @throws ParseException
	 */

	@Override
	public MpFarRealtimePayInfoVO farRealtimePayInfo(String ncn, String ctn, String custId) throws ParseException {
		MpFarRealtimePayInfoVO vo  = null;
		logger.debug("X18 farRealtimePayInfo request. ncn={}, ctn={}, custId={}", ncn, ctn, custId);

		try {
			//x18 실시간요금조회
			vo = mPlatFormService.farRealtimePayInfo( ncn,  ctn,  custId);

			//조회기준일자 형식변형 Start 0105~0112 > 2016.01.01 ~ 2016.01.12
	        if(!vo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
	            throw new McpCommonException(COMMON_EXCEPTION);
	        }

	        StringBuffer tmp = new StringBuffer();
			tmp.append(vo.getSearchDay().substring(0, 4));
			tmp.append(vo.getSearchTime().substring(0, 4));

			String yyyyMmDd = tmp.toString();

			String mmDd = vo.getSearchDay().substring(0, 4) + vo.getSearchTime().substring(vo.getSearchTime().indexOf("~") + 1);

			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
			Date yyyyMmDdDate = formatter.parse(yyyyMmDd);
			Date mmDdDate = formatter.parse(mmDd);

			formatter = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
			yyyyMmDd = formatter.format(yyyyMmDdDate);
			mmDd = formatter.format(mmDdDate);
			vo.setSearchTime(yyyyMmDd + " ~ " +  mmDd);
			int listSize = vo.getList() == null ? 0 : vo.getList().size();
			logger.debug("X18 farRealtimePayInfo response. success={}, searchDay={}, searchTime={}, sumAmt={}, listSize={}",
				vo.isSuccess(), vo.getSearchDay(), vo.getSearchTime(), vo.getSumAmt(), listSize);

		 } catch (SelfServiceException e) {
			logger.error("X18 Exception e : {}", e.getMessage());
		}  catch (Exception e) {
			logger.info("X18 error");
		 }

		return vo;
	}

}
