package com.ktis.msp.batch.job.cmn.sellusim.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.sellusim.mapper.SellUsimMapper;

@Service
public class SellUsimService extends BaseService {
	
	@Autowired
	private SellUsimMapper sellUsimMapper;

	public void sellUsimImgDel() throws MvnoServiceException {
		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("SELL USIM 이미지 삭제 START"));
		LOGGER.info(generateLogMsg("================================================================="));

		int iFileCnt = 0;
		int iSuccCnt = 0;
		int iFailCnt = 0;
		
		List<Map<String, Object>> list = sellUsimMapper.selectSellUsimList();
		
		for(Map<String, Object> rtMap : list) {

			iFileCnt++;

//			String sellUsimKey = (String) rtMap.get("SELL_USIM_KEY");
			String fileNm = (String) rtMap.get("FILE_NM");
			String filePath = (String) rtMap.get("FILE_PATH");

			File file = new File(filePath);
			
			if(file.exists()) {
				try{					
					if(file.delete()){
						LOGGER.info( "SUCCESS DELETE] 파일 삭제 성공 --> " + filePath );	
						
						try{
							if(sellUsimMapper.updateSellUsim(fileNm) > 0){
								iSuccCnt++;
							} else {
								LOGGER.info( "파일 삭제는 됬으나 DB 업데이트 실패 --> " + filePath );									
							}
						} catch(Exception e) {
							iFailCnt++;
							e.printStackTrace();
							LOGGER.info( "파일 삭제는 됬으나 DB 업데이트 실패 --> " + filePath );									
						}
					} else {
						iFailCnt++;
						LOGGER.info( "FAIL DELETE] 파일 삭제 실패 --> " + filePath );	
					}
				} catch(Exception e) {
					iFailCnt++;
					e.printStackTrace();
					LOGGER.info( "FAIL DELETE] 파일 삭제 실패 --> " + filePath );	
				}
			} else {
				iFailCnt++;
				LOGGER.info( "FAIL] 파일 없음 --> " + filePath );
//				LOGGER.info( "FAIL] 파일 없음 --> " + sellUsimKey + ", filePath : " + filePath );
			}	
		}

		LOGGER.info("총 파일 갯수 : " + iFileCnt);
		LOGGER.info("성공한 갯수 : " + iSuccCnt);
		LOGGER.info("실패한 갯수 : " + iFailCnt);
	}
}
