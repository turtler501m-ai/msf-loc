package com.ktis.msp.batch.job.fdh.iif.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.fdh.FdhConstants;
import com.ktis.msp.batch.job.fdh.iif.mapper.FdhTransmitMapper;
import com.ktis.msp.batch.job.fdh.iif.vo.FdhTransmitVO;
import com.ktis.msp.batch.manager.common.file.FileDataListResultHandler;
import com.ktis.msp.batch.util.SFTPUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class FdhTransmitService extends BaseService {
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FdhTransmitMapper fdhTransmitMapper;
	
	public void getFdhArpuSttcProc(Map<String, Object> param) throws MvnoServiceException {
      LOGGER.debug("ARPU 집계 Proc 시작");
      LOGGER.debug("param=" + param);
      
      try {
         fdhTransmitMapper.callArpuSttcProc(param);
      } catch(Exception e) {
         throw new MvnoServiceException("EPTNR1001", e);
      }
      
      LOGGER.debug("ARPU 집계 Proc 종료");
	}
	
	public void getFdhArpuSttc(Map<String, Object> param) throws MvnoServiceException {
      StopWatch sw = new StopWatch();
      sw.start();
      
      LOGGER.info("ARPU 집계 연동 START!!!!!");
      
      List<String> resultList = new ArrayList<String>();
      // DB조회
      String fullFileName = "";
      String fullEndFileName = "";
      try {
         String baseDir = propertiesService.getString("fdh.sftp.local.base.dir");
         
         // 파일생성경로   
         StringBuffer dir = new StringBuffer();
         dir.append(baseDir);
         dir.append(FdhConstants.SAVE_DIR_15);
         String uploadDir = dir.toString();
         // 디렉토리가 없다면 생성
         File file = new File(uploadDir);
         if (!file.exists()) {
            file.mkdirs();
         }
         
         StringBuffer nameBuffer = new StringBuffer();
         nameBuffer.append(FdhConstants.FILE_PREFIX_15);
         nameBuffer.append("_");
         nameBuffer.append(String.valueOf(param.get("USG_YM")));
         nameBuffer.append(FdhConstants.FILE_EXT);

         String fileName = nameBuffer.toString();
         
         dir.append("/");
         dir.append(fileName);
         
         fullFileName = dir.toString();
         
         //resultList = fdhTransmitMapper.getFdhArpuSttc();
         FdhTransmitVO searchVO = new FdhTransmitVO();
         searchVO.setUsgYm(String.valueOf(param.get("USG_YM")));
         
         LOGGER.info("ARPU 집계 연동 SELECT쿼리 실행 START");
         
         fdhTransmitMapper.getFdhArpuSttc(searchVO, new FileDataListResultHandler(fullFileName));
         
         LOGGER.info("ARPU 집계 연동 SELECT쿼리 실행 END");
         
         // 파일생성경로   
         StringBuffer endDir = new StringBuffer();
         endDir.append(baseDir);
         endDir.append(FdhConstants.SAVE_DIR_15);
         String uploadEndDir = endDir.toString();
         // 디렉토리가 없다면 생성
         File endFile = new File(uploadEndDir);
         if (!endFile.exists()) {
            endFile.mkdirs();
         }
         StringBuffer nameEndBuffer = new StringBuffer();
         nameEndBuffer.append(FdhConstants.FILE_PREFIX_15);
         nameEndBuffer.append("_");
         nameEndBuffer.append(String.valueOf(param.get("USG_YM")));
         nameEndBuffer.append(FdhConstants.END_FILE_EXT);
         
         String endFileName = nameEndBuffer.toString();
         
         endDir.append("/");
         endDir.append(endFileName);
         
         fullEndFileName = endDir.toString();
         
         BufferedWriter fw = new BufferedWriter(new FileWriter(fullEndFileName, true));
         fw.close(); 
         
         LOGGER.info("ARPU 집계 연동 DATA COUNT : {}", resultList.size());
      } catch(Exception e) {
         String[] errParam = new String[1];
         errParam[0] = "ARPU 집계 연동";
         throw new MvnoServiceException("EMSP1002", errParam, e);
      }
      
      // 파일 생성
      //String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_1, FdhConstants.FILE_PREFIX_ARPU_STTC, FdhConstants.FILE_EXT);
      
      // SFTP전송 
      transmissionFile(fullFileName, FdhConstants.SAVE_DIR_15);
      
      // END 파일 생성
      //String endFileName = makeFile(null, FdhConstants.SAVE_DIR_1, FdhConstants.FILE_PREFIX_ARPU_STTC, FdhConstants.END_FILE_EXT);
      
      // SFTP전송 - END파일
      transmissionFile(fullEndFileName, FdhConstants.SAVE_DIR_15);
      
      // 파일삭제
      deleteFile(fullFileName);
      deleteFile(fullEndFileName);
      
      sw.stop();
      LOGGER.info("ARPU 집계 연동 END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
   }
	
	public void getFdhInf001() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("계약정보현행화 1차(FDH_INF_001) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf001();
			LOGGER.info("계약정보현행화 1차(FDH_INF_001) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "계약정보현행화 1차(FDH_INF_001)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_1, FdhConstants.FILE_PREFIX_1, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_1);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_1, FdhConstants.FILE_PREFIX_1, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_1);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("계약정보현행화 1차(FDH_INF_001) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf002() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("계약현행화이력정보(FDH_INF_002) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf002();
			LOGGER.info("계약현행화이력정보(FDH_INF_002) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "계약현행화이력정보(FDH_INF_002)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_2, FdhConstants.FILE_PREFIX_2, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_2);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_2, FdhConstants.FILE_PREFIX_2, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_2);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("계약현행화이력정보(FDH_INF_002) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf003() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("상품현행화정보(FDH_INF_003) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf003();
			LOGGER.info("상품현행화정보(FDH_INF_003) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "상품현행화정보(FDH_INF_003)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_3, FdhConstants.FILE_PREFIX_3, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_3);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_3, FdhConstants.FILE_PREFIX_3, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_3);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("상품현행화정보(FDH_INF_003) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf004() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("단말현행화정보(FDH_INF_004) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf004();
			LOGGER.info("단말현행화정보(FDH_INF_004) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "단말현행화정보(FDH_INF_004)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_4, FdhConstants.FILE_PREFIX_4, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_4);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_4, FdhConstants.FILE_PREFIX_4, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_4);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("단말현행화정보(FDH_INF_004) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf005() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("MVNO추가정보(FDH_INF_005) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf005();
			LOGGER.info("MVNO추가정보(FDH_INF_005) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "MVNO추가정보(FDH_INF_005)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_5, FdhConstants.FILE_PREFIX_5, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_5);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_5, FdhConstants.FILE_PREFIX_5, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_5);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("MVNO추가정보(FDH_INF_005) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf006() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("요금제(FDH_INF_006) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf006();
			LOGGER.info("요금제(FDH_INF_006) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "요금제(FDH_INF_006)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_6, FdhConstants.FILE_PREFIX_6, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_6);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_6, FdhConstants.FILE_PREFIX_6, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_6);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("요금제(FDH_INF_006) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf007() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("요금제스펙(FDH_INF_007) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf007();
			LOGGER.info("요금제스펙(FDH_INF_007) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "요금제스펙(FDH_INF_007)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_7, FdhConstants.FILE_PREFIX_7, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_7);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_7, FdhConstants.FILE_PREFIX_7, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_7);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("요금제스펙(FDH_INF_007) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf008() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("조직정보(FDH_INF_008) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf008();
			LOGGER.info("조직정보(FDH_INF_008) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "조직정보(FDH_INF_008)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_8, FdhConstants.FILE_PREFIX_8, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_8);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_8, FdhConstants.FILE_PREFIX_8, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_8);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("조직정보(FDH_INF_008) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf009() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("제품관리(FDH_INF_009) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf009();
			LOGGER.info("제품관리(FDH_INF_009) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "제품관리(FDH_INF_009)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_9, FdhConstants.FILE_PREFIX_9, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_9);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_9, FdhConstants.FILE_PREFIX_9, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_9);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("제품관리(FDH_INF_009) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf010() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("단말기단가(FDH_INF_010) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf010();
			LOGGER.info("단말기단가(FDH_INF_010) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "단말기단가(FDH_INF_010)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_10, FdhConstants.FILE_PREFIX_10, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_10);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_10, FdhConstants.FILE_PREFIX_10, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_10);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("단말기단가(FDH_INF_010) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf011() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("계약정보현행화 2차(FDH_INF_011) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf011();
			LOGGER.info("계약정보현행화 2차(FDH_INF_011) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "계약정보현행화 2차(FDH_INF_011)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_11, FdhConstants.FILE_PREFIX_11, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_11);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_11, FdhConstants.FILE_PREFIX_11, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_11);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("계약정보현행화 2차(FDH_INF_011) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf012() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("조직정보이력(FDH_INF_012) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf012();
			LOGGER.info("조직정보이력(FDH_INF_012) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "조직정보이력(FDH_INF_012)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_12, FdhConstants.FILE_PREFIX_12, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_12);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_12, FdhConstants.FILE_PREFIX_12, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_12);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("조직정보이력(FDH_INF_012) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf013() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("판매정책정보(FDH_INF_013) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf013();
			LOGGER.info("판매정책정보(FDH_INF_013) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "판매정책정보(FDH_INF_013)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_13, FdhConstants.FILE_PREFIX_13, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_13);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_13, FdhConstants.FILE_PREFIX_13, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_13);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("판매정책정보(FDH_INF_013) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	public void getFdhInf014() throws MvnoServiceException {
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("계약해지정보(FDH_INF_014) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf014();
			LOGGER.info("계약해지정보(FDH_INF_014) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "계약해지정보(FDH_INF_014)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_14, FdhConstants.FILE_PREFIX_14, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_14);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_14, FdhConstants.FILE_PREFIX_14, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_14);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("판매정책정보(FDH_INF_014) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	
	//선불국제전화사용량(FDH_INF_016)
	public void getFdhInf016() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("선불국제전화사용량(FDH_INF_016) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf016();
			LOGGER.info("선불국제전화사용량(FDH_INF_016) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "선불국제전화사용량(FDH_INF_016)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_16, FdhConstants.FILE_PREFIX_16, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_16);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_16, FdhConstants.FILE_PREFIX_16, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_16);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("선불국제전화사용량(FDH_INF_016) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}

	//선불전화사용량(FDH_INF_017)
	public void getFdhInf017() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("선불전화사용량(FDH_INF_017) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf017();
			LOGGER.info("선불전화사용량(FDH_INF_017) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "선불전화사용량(FDH_INF_017)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_17, FdhConstants.FILE_PREFIX_17, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_17);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_17, FdhConstants.FILE_PREFIX_17, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_17);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("선불전화사용량(FDH_INF_017) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//해지, 개통 데이터(FDH_INF_018)
	public void getFdhInf018() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("해지, 개통 데이터(FDH_INF_018) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf018();
			LOGGER.info("해지, 개통 데이터(FDH_INF_018) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "해지, 개통 데이터(FDH_INF_018)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_18, FdhConstants.FILE_PREFIX_18, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_18);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_18, FdhConstants.FILE_PREFIX_18, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_18);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("해지, 개통 데이터(FDH_INF_018) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//가상계좌입금내역(FDH_INF_019)
	public void getFdhInf019() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("가상계좌입금내역(FDH_INF_019) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf019();
			LOGGER.info("가상계좌입금내역(FDH_INF_019) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "가상계좌입금내역(FDH_INF_019)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_19, FdhConstants.FILE_PREFIX_19, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_19);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_19, FdhConstants.FILE_PREFIX_19, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_19);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("가상계좌입금내역(FDH_INF_019) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//예치금사용내역(FDH_INF_020)
	public void getFdhInf020() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("예치금사용내역(FDH_INF_020) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf020();
			LOGGER.info("예치금사용내역(FDH_INF_020) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "예치금사용내역(FDH_INF_020)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_20, FdhConstants.FILE_PREFIX_20, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_20);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_20, FdhConstants.FILE_PREFIX_20, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_20);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("예치금사용내역(FDH_INF_020) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//실시간출금내역(FDH_INF_021)
	public void getFdhInf021() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("실시간출금내역(FDH_INF_021) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf021();
			LOGGER.info("실시간출금내역(FDH_INF_021) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "실시간출금내역(FDH_INF_021)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_21, FdhConstants.FILE_PREFIX_21, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_21);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_21, FdhConstants.FILE_PREFIX_21, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_21);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("실시간출금내역(FDH_INF_021) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//POS 충전내역(FDH_INF_022)
	public void getFdhInf022() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("POS 충전내역(FDH_INF_022) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf022();
			LOGGER.info("POS 충전내역(FDH_INF_022) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "POS 충전내역(FDH_INF_022)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_22, FdhConstants.FILE_PREFIX_22, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_22);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_21, FdhConstants.FILE_PREFIX_22, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_22);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("POS 충전내역(FDH_INF_022) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//선불카드사용내역(FDH_INF_023)
	public void getFdhInf023() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("선불카드사용내역(FDH_INF_023) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf023();
			LOGGER.info("선불카드사용내역(FDH_INF_023) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "선불카드사용내역(FDH_INF_023)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_23, FdhConstants.FILE_PREFIX_23, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_23);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_23, FdhConstants.FILE_PREFIX_23, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_23);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("선불카드사용내역(FDH_INF_023) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}	
	
	//물류정보(FDH_INF_024)
	public void getFdhInf024() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("물류정보(FDH_INF_024) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf024();
			LOGGER.info("물류정보(FDH_INF_024) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "물류정보(FDH_INF_024)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_24, FdhConstants.FILE_PREFIX_24, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_24);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_24, FdhConstants.FILE_PREFIX_24, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_24);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("물류정보(FDH_INF_024) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}	

	//입출고관리(FDH_INF_025)
	public void getFdhInf025() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("입출고관리(FDH_INF_025) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf025();
			LOGGER.info("입출고관리(FDH_INF_025) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "입출고관리(FDH_INF_025)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_25, FdhConstants.FILE_PREFIX_25, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_25);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_25, FdhConstants.FILE_PREFIX_25, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_25);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("입출고관리(FDH_INF_025) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//실시간선불정산대상(FDH_INF_026)
	public void getFdhInf026() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("실시간선불정산대상(FDH_INF_026) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf026();
			LOGGER.info("실시간선불정산대상(FDH_INF_026) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "실시간선불정산대상(FDH_INF_026)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_26, FdhConstants.FILE_PREFIX_26, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_26);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_26, FdhConstants.FILE_PREFIX_26, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_26);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("실시간선불정산대상(FDH_INF_026) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//개통관리 이미지(getFdhInf027)
	public void getFdhInf027() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("개통관리 이미지(getFdhInf027) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf027();
			LOGGER.info("개통관리 이미지(getFdhInf027) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "개통관리 이미지(getFdhInf027)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_27, FdhConstants.FILE_PREFIX_27, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_27);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_27, FdhConstants.FILE_PREFIX_27, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_27);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("개통관리 이미지(getFdhInf027) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//기본수수료 (getFdhInf028)
	public void getFdhInf028() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("기본수수료 (getFdhInf028) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf028();
			LOGGER.info("기본수수료 (getFdhInf028) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "기본수수료 (getFdhInf028)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_28, FdhConstants.FILE_PREFIX_28, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_28);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_28, FdhConstants.FILE_PREFIX_28, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_28);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("기본수수료 (getFdhInf028) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//기본수수료2 (getFdhInf029)
	public void getFdhInf029() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("기본수수료2 (getFdhInf029) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf029();
			LOGGER.info("기본수수료2 (getFdhInf029) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "기본수수료2 (getFdhInf029)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_29, FdhConstants.FILE_PREFIX_29, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_29);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_29, FdhConstants.FILE_PREFIX_27, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_29);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("기본수수료2 (getFdhInf029) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//명변수수료 (getFdhInf030)
	public void getFdhInf030() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("명변수수료 (getFdhInf030) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf030();
			LOGGER.info("명변수수료 (getFdhInf030) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "명변수수료 (getFdhInf030)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_30, FdhConstants.FILE_PREFIX_30, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_30);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_30, FdhConstants.FILE_PREFIX_30, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_30);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("명변수수료 (getFdhInf030) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//자동이체수수료 (getFdhInf031)
	public void getFdhInf031() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("자동이체수수료 (getFdhInf031) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf031();
			LOGGER.info("자동이체수수료 (getFdhInf031) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "자동이체수수료 (getFdhInf031)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_31, FdhConstants.FILE_PREFIX_31, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_31);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_31, FdhConstants.FILE_PREFIX_31, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_31);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("자동이체수수료 (getFdhInf031) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//재충전수수료 (getFdhInf032)
	public void getFdhInf032() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("재충전수수료 (getFdhInf032) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf032();
			LOGGER.info("재충전수수료 (getFdhInf032) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "재충전수수료 (getFdhInf032)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_32, FdhConstants.FILE_PREFIX_32, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_32);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_32, FdhConstants.FILE_PREFIX_32, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_32);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("재충전수수료 (getFdhInf032) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}

	//제휴포인트 (getFdhInf033)
	public void getFdhInf033() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("제휴포인트 (getFdhInf033) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf033();
			LOGGER.info("제휴포인트 (getFdhInf033) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "제휴포인트 (getFdhInf033)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_33, FdhConstants.FILE_PREFIX_33, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_33);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_33, FdhConstants.FILE_PREFIX_33, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_33);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("제휴포인트 (getFdhInf033) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//제휴포인트2 (getFdhInf034)
	public void getFdhInf034() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("제휴포인트2 (getFdhInf034) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf034();
			LOGGER.info("제휴포인트2 (getFdhInf034) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "제휴포인트2 (getFdhInf034)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_34, FdhConstants.FILE_PREFIX_34, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_34);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_34, FdhConstants.FILE_PREFIX_34, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_34);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("제휴포인트2 (getFdhInf034) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//매각채권수납 (getFdhInf035)
	public void getFdhInf035() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("매각채권수납 (getFdhInf035) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf035();
			LOGGER.info("매각채권수납 (getFdhInf035) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "매각채권수납 (getFdhInf035)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_35, FdhConstants.FILE_PREFIX_35, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_35);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_35, FdhConstants.FILE_PREFIX_35, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_35);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("매각채권수납 (getFdhInf035) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//매각채권수납2 (getFdhInf036)
	public void getFdhInf036() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("매각채권수납2 (getFdhInf036) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf036();
			LOGGER.info("매각채권수납2 (getFdhInf036) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "매각채권수납2 (getFdhInf036)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_36, FdhConstants.FILE_PREFIX_36, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_36);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_36, FdhConstants.FILE_PREFIX_36, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_36);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("매각채권수납2 (getFdhInf036) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//월정산내역 (getFdhInf037)
	public void getFdhInf037() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("월정산내역 (getFdhInf037) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf037();
			LOGGER.info("월정산내역 (getFdhInf037) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "월정산내역 (getFdhInf037)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_37, FdhConstants.FILE_PREFIX_37, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_37);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_37, FdhConstants.FILE_PREFIX_37, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_37);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("월정산내역 (getFdhInf037) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//월정산내역2 (getFdhInf038)
	public void getFdhInf038() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("월정산내역2 (getFdhInf038) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf038();
			LOGGER.info("월정산내역2 (getFdhInf038) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "월정산내역2 (getFdhInf038)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_38, FdhConstants.FILE_PREFIX_38, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_38);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_38, FdhConstants.FILE_PREFIX_38, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_38);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("월정산내역2 (getFdhInf038) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}	
	
	//월정산내역3 (getFdhInf039)
	public void getFdhInf039() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("월정산내역3 (getFdhInf039) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf039();
			LOGGER.info("월정산내역3 (getFdhInf039) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "월정산내역3 (getFdhInf039)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_39, FdhConstants.FILE_PREFIX_39, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_39);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_39, FdhConstants.FILE_PREFIX_39, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_39);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("월정산내역3 (getFdhInf039) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//부가서비스가입내역 (getFdhInf040)
	public void getFdhInf040() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("부가서비스가입내역 (getFdhInf040) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf040();
			LOGGER.info("부가서비스가입내역 (getFdhInf040) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "부가서비스가입내역 (getFdhInf040)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_40, FdhConstants.FILE_PREFIX_40, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_40);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_40, FdhConstants.FILE_PREFIX_40, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_40);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("부가서비스가입내역 (getFdhInf040) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//부가서비스가입내역2 (getFdhInf041)
	public void getFdhInf041() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("부가서비스가입내역2 (getFdhInf041) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = fdhTransmitMapper.getFdhInf041();
			LOGGER.info("부가서비스가입내역2 (getFdhInf041) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "부가서비스가입내역2 (getFdhInf041)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, FdhConstants.SAVE_DIR_41, FdhConstants.FILE_PREFIX_41, FdhConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, FdhConstants.SAVE_DIR_41);
		
		// END 파일 생성
		String endFileName = makeFile(null, FdhConstants.SAVE_DIR_41, FdhConstants.FILE_PREFIX_41, FdhConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, FdhConstants.SAVE_DIR_41);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("부가서비스가입내역2 (getFdhInf041) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	/**
	 * 파일생성
	 * @param resultList - 파일에 작성할 내용
	 * @param saveDir - 저장 디렉토리
	 * @param filePrefix - 파일접두사
	 * @param fileExt - 파일확장자
	 * @throws MvnoServiceException
	 */
	private String makeFile(List<String> resultList, String saveDir, String filePrefix, String fileExt) throws MvnoServiceException {
		
		String baseDir = propertiesService.getString("fdh.sftp.local.base.dir");
		// 파일생성경로	
		StringBuffer dir = new StringBuffer();
		dir.append(baseDir);
		dir.append(saveDir);
		String uploadDir = dir.toString();
		
		File file = new File(uploadDir);
		
		// 디렉토리가 없다면 생성
		if (!file.exists()) {
			file.mkdirs();
		}
		
		StringBuffer nameBuffer = new StringBuffer();
		String curDt = KtisUtil.toStr(new Date(), "yyyyMMdd");
		
		nameBuffer.append(filePrefix);
		nameBuffer.append("_");
		nameBuffer.append(curDt);
		nameBuffer.append(fileExt);
		String fileName = nameBuffer.toString();
		
		dir.append("/");
		dir.append(fileName);
		
		String fullFileName = dir.toString();
		
		LOGGER.info("파일작성 START!!!!! [{}]", fullFileName);
		
		// BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(fullFileName, false));
			
			if(resultList != null && resultList.size() > 0) {
				for (String strVal : resultList) {
					fw.write(strVal);
					fw.newLine();
				}
			}
			fw.flush();
			
			//객체 닫기
			fw.close(); 
			
		} catch (IOException e) {
			String[] errParam = new String[1];
			errParam[0] = fullFileName;
			throw new MvnoServiceException("EMSP1001", errParam, e);
		}
		
		LOGGER.info("파일작성 END!!!!! [{}]", fullFileName);
		
		return fullFileName;
	}
	
	/**
	 * SFTP 파일전송
	 * @param fullFileName
	 * @param saveDir
	 * @throws MvnoServiceException
	 */
	private void transmissionFile(String fullFileName, String saveDir) throws MvnoServiceException {
		
		// FTP로 파일 전송
		
		// ftp 루트
		String baseDir = propertiesService.getString("fdh.sftp.base.dir");
		
		StringBuffer dirBuffer = new StringBuffer();
		
		dirBuffer.append(baseDir);
		dirBuffer.append(saveDir);
		
		String sftpDir = dirBuffer.toString();
		
		LOGGER.info("파일전송 START!!!!! [{} ==SFTP==> {}]",fullFileName, sftpDir);
		
		File file = new File(fullFileName);
		
		// FTP 접속정보
		SFTPUtil ftp = new SFTPUtil();
		String host = propertiesService.getString("ifrs.sftp.host");
		int port = Integer.parseInt(propertiesService.getString("ifrs.sftp.port"));
		String userName = propertiesService.getString("ifrs.sftp.username");
		String password = propertiesService.getString("ifrs.sftp.pwd");
		
		try {
			ftp.init(host, userName, password, port);
		} catch (JSchException e) {
			String[] errParam = new String[4];
			errParam[0] = host;
			errParam[1] = String.valueOf(port);
			errParam[2] = userName;
			errParam[3] = password;
			throw new MvnoServiceException("EMSP1003", errParam, e);
		}
		
		String[] errParam = new String[1];
		errParam[0] = fullFileName;
		
		try {
			ftp.upload(sftpDir, file);
		} catch (FileNotFoundException e) {
			throw new MvnoServiceException("EMSP1004", errParam, e);
		} catch (SftpException e) {
			throw new MvnoServiceException("EMSP1005", errParam, e);
		} catch (IOException e) {
			throw new MvnoServiceException("EMSP1006", errParam, e);
		}
		
		ftp.disconnection();
		
		LOGGER.info("파일전송 END!!!!! [{} ({} byte)==SFTP==> {}]",fullFileName, file.length(), sftpDir);
	}
	
	/**
	 * 로컬파일삭제
	 * @param fullFileName
	 */
	private void deleteFile(String fullFileName) {
		File file = new File(fullFileName);
		file.delete();
		LOGGER.info("파일삭제 END!!!!! [{}]", fullFileName);
	}
}
