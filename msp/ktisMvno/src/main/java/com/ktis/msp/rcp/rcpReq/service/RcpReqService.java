/**
 * 
 */
package com.ktis.msp.rcp.rcpReq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.rcp.rcpReq.mapper.RcpReqMapper;

@Service
public class RcpReqService extends BaseService {
	
	@Autowired
	private RcpReqMapper rcpReqMapper;
	
    public List<?> getRcpReqInfo(String srlNum) {
    	return rcpReqMapper.getRcpReqInfo(srlNum);
    }

   
}
