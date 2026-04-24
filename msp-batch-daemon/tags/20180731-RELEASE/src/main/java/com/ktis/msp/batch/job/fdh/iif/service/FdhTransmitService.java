package com.ktis.msp.batch.job.fdh.iif.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.ktis.msp.batch.util.SFTPUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class FdhTransmitService extends BaseService {
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private FdhTransmitMapper fdhTransmitMapper;
	
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
