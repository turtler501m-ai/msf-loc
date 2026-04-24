package com.ktis.msp.batch.job.rcp.scanmgmt.service;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.scanmgmt.mapper.ScanMgmtMapper;
import com.ktis.msp.batch.job.rcp.scanmgmt.vo.ScanMgmtVO;

@Service
public class ScanMgmtService extends BaseService {
	
	@Autowired
	private ScanMgmtMapper scanMgmtMapper;
	
	/**
	 * 직권해지처리
	 * @param param
	 * @return
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public int procScanMgmt() throws MvnoServiceException{
		int procCnt = 0;
		
		try {
			scanMgmtMapper.setScanMgmtList();
							
			List<ScanMgmtVO> list = scanMgmtMapper.getScanMgmtList();
			
			if (list.size() > 0) {
				Calendar today = Calendar.getInstance();
				String year = String.valueOf(today.get(Calendar.YEAR));
				String month = String.valueOf(today.get(Calendar.MONTH)+1);
				String day = String.valueOf(today.get(Calendar.DATE));
				
				String orgFileName = "";
				String movFilePath = "";
				String movFileName = "";
				
				File orgFile = null;
				File movFile = null;
				File movDir = null;
				
				for(ScanMgmtVO vo : list) {
					orgFileName = vo.getOriginDir();
					movFileName = vo.getFileNm();
					orgFile = new File(orgFileName);
					
					
					if(orgFile.exists()) {
						movFilePath = "/jboss/data/webscan/backup/image";
						
						movFilePath = movFilePath+"/"+vo.getDocId();
						movDir = new File(movFilePath);
						movDir.mkdir();
						
						movFilePath = movFilePath+"/"+year;
						movDir = new File(movFilePath);
						movDir.mkdir();
						
						movFilePath = movFilePath+"/"+month;
						movDir = new File(movFilePath);
						movDir.mkdir();
						
						movFilePath = movFilePath+"/"+day;
						movDir = new File(movFilePath);
						movDir.mkdir();
						
						movFile = new File(movDir, movFileName);
						orgFile.renameTo(movFile);
						
						if (movFile.exists()) {
							vo.setProcRsltCd("00");
							vo.setProcStatus("S");
							vo.setBackupDir(movFilePath);
						} else {
							vo.setProcRsltCd("99");
							vo.setProcStatus("E");
						}
						

					} else {
						vo.setProcRsltCd("01");
						vo.setProcStatus("S");
					}
									
					scanMgmtMapper.setScanMgmt(vo);
					
					procCnt++;
				}
			}
			
		} catch(Exception e) {
			throw new MvnoServiceException("ERCP1003", e);
		}
		
		return procCnt;
		
	}
	
}
