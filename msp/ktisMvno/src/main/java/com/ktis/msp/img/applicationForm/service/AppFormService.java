package com.ktis.msp.img.applicationForm.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.img.applicationForm.vo.ScanFileVO;
import com.ktis.msp.img.applicationForm.vo.ScanLogVO;
import com.ktis.msp.img.applicationForm.vo.ScanRequestVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.img.applicationForm.mapper.AppFormMapper;

@Service("appFormService")
public class AppFormService extends BaseService {
	
	private static final Log LOGGER = LogFactory.getLog(AppFormService.class);
    private static final String BIZ_TYPE_DELETE = "DELETE";
    private static final String BIZ_TYPE_MERGE = "MERGE";

    @Autowired
	private AppFormMapper appFormMapper;
	
	public HashMap<String, String> getAppFormData(String requestKey) {
		
		HashMap<String, String> requestData = new HashMap<String, String>();
		
		/*try {
			
			//가입 예약 번호(전달 받을 값)
			String queryId = "applicationForm.getRequestJoinDataByRjoinKey";
			
			//가입신청 정보 가져오기
			//requestData = dao.select(queryId, requestKey);
			
		} catch (Exception e) {
			LOGGER.error(e);
		}*/
		
		return requestData;
	}
	
	public  HashMap<String, String> getScnViewerInfo(HashMap<String, String> params) {
		
		 HashMap<String, String> resultMap = new HashMap<String, String>();
		 String type = params.get("callType");
		 String scanVersion = "";
		 String rgstDt = "";
		 try {
			 if(type.equals("S")) {
				 resultMap =  appFormMapper.getScnViewerInfoTypeS(params); 	
			 }else {
				 resultMap =  appFormMapper.getScnViewerInfo(params);
			 }
			 
			 //2015-10-12 서식지보기에서 등록일자 보이기 
			 if(resultMap!=null){
				 if(type.equals("S")) {
					 rgstDt =   appFormMapper.getScnViewerRgstDtTypeS(resultMap);
				 }else{
					 rgstDt =   appFormMapper.getScnViewerRgstDt(resultMap);
				 }
				 resultMap.put("rgstDt", rgstDt);
			 }
			 
			 scanVersion =  appFormMapper.getScanViewerVersion();
			 
			 resultMap.put("version", scanVersion);
			 
		 } catch (Exception e) {
			 LOGGER.error(e);
		}
		
		 return resultMap;
	}
	
	public String getScanViewerVersion() {
		String version = "";
		
		try {
			version = appFormMapper.getScanViewerVersion();
		} catch (Exception e) {
			LOGGER.error(e);
		}
			
		return version;
	}
	
	/**
	 * 파일 아이디로 해당 이미지 삭제
	 * @param fileId
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteScanImage(Map<String, Object> paramMap) {
		
		String fileId = String.valueOf(paramMap.get("fileId"));
		String callType = String.valueOf(paramMap.get("callType"));
		
		try {
			
			if(callType.equals("C")) {
				appFormMapper.deleteScanImageCallTypeByC(fileId);
			}else {
				appFormMapper.deleteScanImageCallTypeByS(fileId);
			}
			
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
	
	/**
	 * 스캔 아이디 생성
	 * @return
	 */
    public String getUUID()
    {
       String returnString = "";

       UUID uuid = UUID.randomUUID();

       returnString = uuid.toString();
       returnString = returnString.replaceAll("-", "");
       returnString = returnString.trim();

       return returnString;
    }

    public void deleteScanFileByFileId(ScanRequestVO scanRequestVO) {
        // 파일 경로 조회
        ScanFileVO scanFileVO = appFormMapper.getScanFile(scanRequestVO.getFileId());
        if (scanFileVO == null) {
            return;
        }

        // 파일 삭제
        File file = new File(scanFileVO.getOriginDir());
        if (file.exists()) {
            file.delete();
        }

        // EMV_SCAN_FILE 데이터 삭제
        appFormMapper.deleteScanFileByFileId(scanFileVO.getFileId());

        // EMV_SCAN_LOG_TXN 이력 저장
        ScanLogVO scanLogVO = new ScanLogVO();
        scanLogVO.setDocId(scanFileVO.getDocId());
        scanLogVO.setBizType(BIZ_TYPE_DELETE);
        scanLogVO.setScanId(scanFileVO.getScanId());
        scanLogVO.setFileId(scanFileVO.getFileId());
        scanLogVO.setPrcsSbst(scanRequestVO.getPrcsSbst());
        scanLogVO.setProcPrsnId(scanRequestVO.getUserId());
        appFormMapper.insertScanLogTxn(scanLogVO);
    }

    public void insertScanFileToOrigin(ScanRequestVO scanRequestVO) {
        if (scanRequestVO == null) {
            return;
        }

        ScanFileVO scanFileVO = appFormMapper.getScanFile(scanRequestVO.getFileId());
        if (scanFileVO == null) {
            return;
        }

        scanFileVO.setOriginScanId(scanRequestVO.getOriginScanId());
        scanFileVO.setUserId(scanRequestVO.getUserId());
        appFormMapper.insertOriginScanFileFrom(scanFileVO);

        ScanLogVO scanLogVO = new ScanLogVO();
        scanLogVO.setDocId(scanFileVO.getDocId());
        scanLogVO.setBizType(BIZ_TYPE_MERGE);
        scanLogVO.setScanId(scanFileVO.getOriginScanId());
        scanLogVO.setFileId(scanFileVO.getFileId());
        scanLogVO.setPrcsSbst(scanRequestVO.getPrcsSbst());
        scanLogVO.setProcPrsnId(scanRequestVO.getUserId());
        appFormMapper.insertScanLogTxn(scanLogVO);
    }
}
