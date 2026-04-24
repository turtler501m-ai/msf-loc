package com.ktis.msp.batch.job.msp.ifrs.service;

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
import com.ktis.msp.batch.job.msp.IfrsConstants;
import com.ktis.msp.batch.job.msp.ifrs.mapper.IfrsTransmitMapper;
import com.ktis.msp.batch.util.SFTPUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @author TREXSHIN
 *
 */
@Service
public class IfrsTransmitService extends BaseService {
	
	@Autowired
	private IfrsTransmitMapper ifrsTransmitMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	//계약정보현행화 1차(IFRS_INF_001)
	public void getMspJuoSubInfoFirst() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("계약정보현행화 1차(IFRS_INF_001) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getMspJuoSubInfoFirst();
			LOGGER.info("계약정보현행화 1차(IFRS_INF_001) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "계약정보현행화 1차(IFRS_INF_001)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_1, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_1);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_1, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_1);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("계약정보현행화 1차(IFRS_INF_001) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//계약현행화이력정보(IFRS_INF_002)
	public void getMspJuoSubInfoHist() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("계약현행화이력정보(IFRS_INF_002) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getMspJuoSubInfoHist();
			LOGGER.info("계약현행화이력정보(IFRS_INF_002) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "계약현행화이력정보(IFRS_INF_002)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_2, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_1);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_2, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_1);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("계약현행화이력정보(IFRS_INF_002) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//상품현행화정보(IFRS_INF_003)
	public void getMspJuoFeatureInfo() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("상품현행화정보(IFRS_INF_003) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getMspJuoFeatureInfo();
			LOGGER.info("상품현행화정보(IFRS_INF_003) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "상품현행화정보(IFRS_INF_003)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_3, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_1);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_3, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_1);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("상품현행화정보(IFRS_INF_003) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//단말현행화정보(IFRS_INF_004)
	public void getMspJuoModelInfo() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("단말현행화정보(IFRS_INF_004) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getMspJuoModelInfo();
			LOGGER.info("단말현행화정보(IFRS_INF_004) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "단말현행화정보(IFRS_INF_004)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_4, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_1);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_4, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_1);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("단말현행화정보(IFRS_INF_004) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//MVNO추가정보(IFRS_INF_005)
	public void getMspJuoAddInfo() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("MVNO추가정보(IFRS_INF_005) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getMspJuoAddInfo();
			LOGGER.info("MVNO추가정보(IFRS_INF_005) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "MVNO추가정보(IFRS_INF_005)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_5, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_1);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_5, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_1);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("MVNO추가정보(IFRS_INF_005) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
		
	}
	
	//요금제(IFRS_INF_006)
	public void getMspRateMst() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("요금제(IFRS_INF_006) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getMspRateMst();
			LOGGER.info("요금제(IFRS_INF_006) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "요금제(IFRS_INF_006)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_6, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_1);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_6, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_1);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("요금제(IFRS_INF_006) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
		
	}
	
	//요금제스펙(IFRS_INF_007)
	public void getMspRateSpec() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("요금제스펙(IFRS_INF_007) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getMspRateSpec();
			LOGGER.info("요금제스펙(IFRS_INF_007) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "요금제스펙(IFRS_INF_007)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_7, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_1);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_7, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_1);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("요금제스펙(IFRS_INF_007) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//계약정보현행화 2차(IFRS_INF_008)
	public void getMspJuoSubInfoSecond() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("계약정보현행화 2차(IFRS_INF_008) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getMspJuoSubInfoSecond();
			LOGGER.info("계약정보현행화 2차(IFRS_INF_008) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "계약정보현행화 2차(IFRS_INF_008)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_2, IfrsConstants.FILE_PREFIX_8, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_2);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_2, IfrsConstants.FILE_PREFIX_8, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_2);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("계약정보현행화 2차(IFRS_INF_008) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//수수료집계(IFRS_INF_009)
	public void getCmsPrvdTrgtAgncyCttt() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("수수료집계(IFRS_INF_009) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getCmsPrvdTrgtAgncyCttt();
			LOGGER.info("수수료집계(IFRS_INF_009) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "수수료집계(IFRS_INF_009)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_3, IfrsConstants.FILE_PREFIX_9, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_3);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_3, IfrsConstants.FILE_PREFIX_9, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_3);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("수수료집계(IFRS_INF_009) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//조직정보(IFRS_INF_010)
	public void getOrgOrgnInfoMst() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("조직정보(IFRS_INF_010) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getOrgOrgnInfoMst();
			LOGGER.info("조직정보(IFRS_INF_010) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "조직정보(IFRS_INF_010)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_4, IfrsConstants.FILE_PREFIX_10, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_4);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_4, IfrsConstants.FILE_PREFIX_10, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_4);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("조직정보(IFRS_INF_010) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//제품기기정보(IFRS_INF_011)
	public void getLgsPrdtSrlMst() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("제품기기정보(IFRS_INF_011) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getLgsPrdtSrlMst();
			LOGGER.info("제품기기정보(IFRS_INF_011) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "제품기기정보(IFRS_INF_011)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_5, IfrsConstants.FILE_PREFIX_11, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_5);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_5, IfrsConstants.FILE_PREFIX_11, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_5);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("제품기기정보(IFRS_INF_011) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//제품관리(IFRS_INF_012)
	public void getCmnIntmMdl() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("제품관리(IFRS_INF_012) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getCmnIntmMdl();
			LOGGER.info("제품관리(IFRS_INF_012) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "제품관리(IFRS_INF_012)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_5, IfrsConstants.FILE_PREFIX_12, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_5);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_5, IfrsConstants.FILE_PREFIX_12, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_5);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("제품관리(IFRS_INF_012) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//청구수납자료(IFRS_INF_013)
	public void getBillPymnData() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("청구수납자료(IFRS_INF_013) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getBillPymnData();
			LOGGER.info("청구수납자료(IFRS_INF_013) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "청구수납자료(IFRS_INF_013)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_6, IfrsConstants.FILE_PREFIX_13, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_6);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_6, IfrsConstants.FILE_PREFIX_13, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_6);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("청구수납자료(IFRS_INF_013) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//단말기단가(IFRS_INF_014)
	public void getCmnHndstAmt() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("단말기단가(IFRS_INF_014) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getCmnHndstAmt();
			LOGGER.info("단말기단가(IFRS_INF_014) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "단말기단가(IFRS_INF_014)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_14, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_1);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_14, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_1);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("단말기단가(IFRS_INF_014) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//공시지원금(IFRS_INF_015)
	public void getOfclSubsdMst() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("공시지원금(IFRS_INF_015) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getOfclSubsdMst();
			LOGGER.info("공시지원금(IFRS_INF_015) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "공시지원금(IFRS_INF_015)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_15, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_1);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_15, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_1);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("공시지원금(IFRS_INF_015) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//신청정보(IFRS_INF_016)
	public void getMspRequest() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("신청정보(IFRS_INF_016) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getMspRequest();
			LOGGER.info("신청정보(IFRS_INF_016) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "신청정보(IFRS_INF_016)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_16, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_1);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_16, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_1);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("신청정보(IFRS_INF_016) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
	}
	
	//기변정보(IFRS_INF_017)
	public void getMspDvcChgAgntHst() throws MvnoServiceException {
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		LOGGER.info("기변정보(IFRS_INF_017) START!!!!!");
		
		List<String> resultList = new ArrayList<String>();
		// DB조회
		try {
			resultList = ifrsTransmitMapper.getMspDvcChgAgntHst();
			LOGGER.info("기변정보(IFRS_INF_017) DATA COUNT : {}", resultList.size());
		} catch(Exception e) {
			String[] errParam = new String[1];
			errParam[0] = "기변정보(IFRS_INF_017)";
			throw new MvnoServiceException("EMSP1002", errParam, e);
		}
		
		// 파일 생성
		String fullFileName = makeFile(resultList, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_17, IfrsConstants.FILE_EXT);
		
		// SFTP전송 
		transmissionFile(fullFileName, IfrsConstants.SAVE_DIR_1);
		
		// END 파일 생성
		String endFileName = makeFile(null, IfrsConstants.SAVE_DIR_1, IfrsConstants.FILE_PREFIX_17, IfrsConstants.END_FILE_EXT);
		
		// SFTP전송 - END파일
		transmissionFile(endFileName, IfrsConstants.SAVE_DIR_1);
		
		// 파일삭제
		deleteFile(fullFileName);
		deleteFile(endFileName);
		
		sw.stop();
		LOGGER.info("기변정보(IFRS_INF_017) END!!!!! total[{} sec]", sw.getTotalTimeSeconds());
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
		
		String baseDir = propertiesService.getString("ifrs.sftp.local.base.dir");
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
		String baseDir = propertiesService.getString("ifrs.sftp.base.dir");
		
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
