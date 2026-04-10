package com.ktmmobile.mcp.point.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.mcp.point.dto.CustPointDto;
import com.ktmmobile.mcp.point.dto.CustPointGiveTgtBasDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;

public interface PointService {
		/**
	    * @Description : 포인트 조정
	    * @param PointStdDto
	    * @return List(PointStdDto)
	    * @Author : ant08
	    * @Create Date : 2021. 11. 03.
	    */
		@Transactional
	    public Map<String, Object> editPoint(CustPointTxnDto pointTxnDto);
		
		public Map<String, Object> editPointSUE(CustPointTxnDto pointTxnDto);
		
		public Map<String, Object> insertPointTgtBas(CustPointGiveTgtBasDto custPointGiveTgtBasDto);


}
