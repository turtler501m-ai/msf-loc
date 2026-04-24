package com.ktis.msp.batch.job.dis.dismgmt.service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.dis.dismgmt.mapper.DisTrgMapper;
import com.ktis.msp.batch.job.dis.dismgmt.vo.DisVO;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 *
 */
@Service
public class DisUtilService extends BaseService {

	@Autowired
	private DisTrgMapper disTrgMapper;

	/**
	 * @Description : 기준일자 가져오기(오늘날짜 or 파라미터로 받은 날짜)
	 * @Param : BatchCommonVO
	 * @Return : String
	 * @Author : wooki
	 * @CreateDate : 2024.02
	 */
	public String getBaseDate(BatchCommonVO batchCommonVO) throws MvnoServiceException {

		//1.오늘날짜로 기본 set
		String baseDate = KtisUtil.toStr(new Date(), "yyyyMMdd");

		//2.파라미터로 받은 날짜가 있으면 그 날짜로 set
		if(!StringUtils.isBlank(batchCommonVO.getExecParam())) {
			try {
				Map<String, Object> param = JacksonUtil.makeMapFromJson(batchCommonVO.getExecParam());
				String inputDate = (String) param.get("WORK_DT");

				if(!StringUtils.isBlank(inputDate)) { //inputDate가 있으면 자릿수체크, yyyymmdd인지 체크
					if(8 == inputDate.length() && inputDate.matches("^\\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$")) {
						baseDate = inputDate;
					}
				}
			}catch(Exception e) {
				LOGGER.error("[DisUtilService.getStanDate.err] : " + e.getMessage());
			}
		}

		return baseDate;
	}

	/**
	 * @Description : 시작일자 가져오기(오늘날짜 or 파라미터로 받은 날짜에서 공통코드(CMN0271) 날짜만큼 이전)
	 * @Param : BatchCommonVO
	 * @Return : String
	 * @Author : 박민건
	 * @CreateDate : 2024.02
	 */
	public String getStrDate(BatchCommonVO batchCommonVO) throws MvnoServiceException {

		String baseDate = this.getBaseDate(batchCommonVO); //기준일자
		String getDay = "7"; //공통코드에서 가져오는 날짜
		int baseDay = 0; //getDay의 음수
		String strDate = baseDate; //리턴되는 날짜

		//1.공통코드 가져오기
		try {
			getDay = disTrgMapper.getBaseDate();
		}catch(Exception e) {
			LOGGER.error("[DisUtilService.getStrDate.err1] : " + e.getMessage());
		}

		if(!StringUtils.isNumeric(getDay)) {
			getDay = "7"; //공통코드에서 가져온 데이터가 숫자가 아니면
		}
		baseDay = Integer.parseInt(getDay)*-1; //getDay를 음수로 만듦

		//2.날짜 계산하기
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		Date dt = null;
		try {
			dt = dtFormat.parse(baseDate);
			cal.setTime(dt);
			cal.add(Calendar.DATE, baseDay);
			strDate = dtFormat.format(cal.getTime());
		}catch(Exception e) {
			LOGGER.error("[DisUtilService.getStrDate.err2] : " + e.getMessage());
		}
		
		return strDate;
	}

	/**
	 * @Description : 기준일(endDt), 시작일(strDt) 가져오기
	 * @Param : BatchCommonVO
	 * @Return : disVO
	 * @Author : 박민건
	 * @CreateDate : 2024.02
	 */
	public DisVO getStrEndDate(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String baseDate = this.getBaseDate(batchCommonVO);   //기준일자
		String strDate = this.getStrDate(batchCommonVO);  	 //시작일자
		
		DisVO disVO = new DisVO();
		disVO.setEndDt(baseDate);
		disVO.setStrDt(strDate);

		return disVO;
	}
}
