package com.ktis.msp.batch.job.org.orgmgmt.service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.orgmgmt.mapper.PartnerUsimRegMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PartnerUsimRegService extends BaseService {

	@Autowired
	private PartnerUsimRegMapper partnerUsimRegMapper;

	/**
	 * 제휴유심 등록 프로시저 호출
	 * @throws MvnoServiceException
	 */
	public void callPartnerUsimData(Map<String, Object> param) throws MvnoServiceException {

		try {

			String workDt = (String) param.get("i_DATE");

			// 1. 배송조직아이디와 접점 매핑
			// 1-1. 매핑 정보 초기화
			partnerUsimRegMapper.deleteUsimOrgMatch(workDt);

			// 1-2. 대리점 유심-조직 매핑
			partnerUsimRegMapper.insertAgentMatch(workDt);

			// 1-3. 판매점 유심-조직 매핑
			partnerUsimRegMapper.insertShopMatch(workDt);

			// 2. 제휴유심 등록 프로시저 호출
			LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
			LOGGER.info("☆☆☆☆☆☆☆☆callPartnerUsimData 프로시저시작☆☆☆☆☆☆☆☆");
			LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
			partnerUsimRegMapper.callPartnerUsimData(param);

		} catch(Exception e) {
			String[] errParam = {"P_MDS_INTM_DLVR_TXN"};
			throw new MvnoServiceException("EORG1007", errParam, e);
		}
	}

}
