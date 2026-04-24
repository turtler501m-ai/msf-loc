package com.ktis.msp.batch.job.cmn.smsmgmt.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.smsmgmt.mapper.OldExcelDownFileMapper;

@Service
public class OldExcelDownFileClearService extends BaseService {
	@Autowired
	private OldExcelDownFileMapper oldExcelDownFileMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public String OldExcelDownFileClear() throws MvnoServiceException {
		String strReMark = "";
		int iDel = 0;
		int iFalse = 0;
		try {
			LOGGER.info("전일 생성 엑셀 파일 삭제 시작");
			// 삭제할 데이터 조회
			List<String> list = new ArrayList<String>();
			list = oldExcelDownFileMapper.getOldExcelDownFileList();
			
			// 삭제
			for(String strFileFullPath : list) {
				try {
					File dFile = new File(strFileFullPath);
					
					if(deleteDirectory(dFile)) {
						iDel++;
					} else {
						iFalse++;
					}
				} catch(Exception ex) {
					iFalse++;
				}
			}
			strReMark = "전일 생성 엑셀 파일 삭제[성공 : " + iDel + " / 실패 : " + iFalse + "]";
			LOGGER.info("전일 생성 엑셀 파일 삭제 종료");
		} catch(Exception e) {
			throw new MvnoServiceException("ECMN2002", e);
		}
		return strReMark;
	}
	
	public static boolean deleteDirectory(File path) {
		if(!path.exists()) {
			return false;
		}
		
//		File[] files = path.listFiles();
//		for(File file : files) {
//			if(file.isDirectory()) {
//				deleteDirectory(file);
//			} else {
//				file.delete();
//			}
//		}
		
		return path.delete();
	}
}
