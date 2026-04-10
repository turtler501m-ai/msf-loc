package com.ktis.msp.cmn.cdmgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.cmn.cdmgmt.mapper.CmnCdMgmtMapper;

@Service
public class DbServiceCmn
{
	
	@Autowired
	CmnCdMgmtMapper dbServiceMapper;
	
	/* *********************************************************************** */
    /*  CMN_GRP_MST 테이블 Manipulation (Retrieval, Insert, Delete)              */
    /* *********************************************************************** */  
	public List<Map<String, String>> getCmnGrpMst(HashMap<String, String> map)
	{
		return(dbServiceMapper.getCmnGrpMst(map));
	}
	
	public List<Map<String, String>> getFormMapping()
	{
		return dbServiceMapper.getFormMapping();
	}
	
	public List<Map<String, String>> getAllTables(String username) 
	{
		return dbServiceMapper.getAllTables(username);
	}
	
	public List<Map<String, String>> getAllComments()
	{
		return dbServiceMapper.getAllComments();
	}
}
