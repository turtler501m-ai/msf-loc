package com.ktis.msp.cmn.prgmmgmt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.prgmmgmt.mapper.PrgmMgmtMapper;
import com.ktis.msp.cmn.prgmmgmt.vo.PrgmMgmtVO;

@Service
public class PrgmMgmtService extends BaseService {
	
	@Autowired
	private PrgmMgmtMapper prgmMgmtMapper;

	/**
	 * JUICE BATCH Log 조회
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getPrgmMgmtHst(PrgmMgmtVO vo){
		return prgmMgmtMapper.getPrgmMgmtHst(vo);
	}
}
