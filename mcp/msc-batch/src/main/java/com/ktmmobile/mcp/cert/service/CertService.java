package com.ktmmobile.mcp.cert.service;

import com.ktmmobile.mcp.cert.dao.CertDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertService {
    
    @Autowired
    private CertDao certDao;

    
    public String getCertDelDay() {
    	return certDao.getCertDelDay();
    }
    
    public int deleteCertFailData(int delDay) {
    	return certDao.deleteCertFailData(delDay);
    }
}