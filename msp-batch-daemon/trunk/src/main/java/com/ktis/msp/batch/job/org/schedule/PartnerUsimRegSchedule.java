package com.ktis.msp.batch.job.org.schedule;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.orgmgmt.service.PartnerUsimRegService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class PartnerUsimRegSchedule extends BaseSchedule {

	@Autowired
	private PartnerUsimRegService partnerUsimRegService;

	/**
	 * Job 실행
	 * 제휴유심 등록 업무자동화
	 * @throws MvnoServiceException
	 */
	@Scheduled(cron="${partnerUsimRegSchedule}")
	public void schedule() throws MvnoServiceException {
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
	}

	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {

		String result = BatchConstants.BATCH_RESULT_OK;

		try {
			batchStart(batchCommonVO);

			Map<String, Object> param = new HashMap<String, Object>();

			if(batchCommonVO.getExecParam() != null && !batchCommonVO.getExecParam().isEmpty()){
				param = JacksonUtil.makeMapFromJson(batchCommonVO.getExecParam());
			} else {
				// default 하루 전 날짜
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -1);
				String date = sdf.format(cal.getTime());

				param.put("i_DATE", date);
			}

			batchCommonVO.setExecParam(JacksonUtil.makeJsonFromMap(param));

			// 제휴유심 등록 서비스 호출
			partnerUsimRegService.callPartnerUsimData(param);

			String resultCd = (String) param.get("O_CODE");
			String resultMsg = (String) param.get("O_MSG");

			if(!"1000".equals(resultCd)){
				throw new MvnoServiceException(resultCd, resultMsg);
			}

			int execCount = Integer.parseInt((String) param.get("O_TOTAL"));
			batchCommonVO.setExecCount(execCount);
			batchCommonVO.setRemrk(resultMsg);

			batchEnd(batchCommonVO);

		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}

		return result;
	}

}
