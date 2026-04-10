package com.ktmmobile.mcp.rate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.mcp.rate.dao.RateDao;
import com.ktmmobile.mcp.rate.dto.RateDTO;

@Service
public class RateSvcImpl implements  RateSvc{
	@Autowired
	RateDao  dao;
	@Override
	public List<RateDTO> selectRateList(RateDTO dto) {
		// TODO Auto-generated method stub
		return dao.selectRateList(dto);
	}
	
	
	@Override
	@Transactional
	public void insertRate(RateDTO dto) {
		// TODO Auto-generated method stub
		dao.insertRate(dto);
	}


	@Override
	public RateDTO selectRate(RateDTO dto) {
		// TODO Auto-generated method stub
		return dao.selectRate(dto);
	}


	@Override
	@Transactional
	public void deleteRate(RateDTO dto) {
		// TODO Auto-generated method stub
		dao.deleteRate(dto);
	}


	@Override
	@Transactional
	public void updateRate(RateDTO dto) {
		// TODO Auto-generated method stub
		dao.updateRate(dto);
	}


	@Override
	public RateDTO getDetail(RateDTO dto) {
		// TODO Auto-generated method stub
		return dao.getDetail(dto);
	}
	
	
	
	
	
}
