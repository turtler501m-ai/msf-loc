package com.ktis.msp.batch.job.ptnr.ptnrmgmt.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.PartnerPointSettleMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.util.SFTPUtil;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * 제휴사 정산파일 업/다운로드 처리를 위한 서비스 분리
 * @author SJLEE
 */
@Service
public class PointFileFTPService extends BaseService {
	
	@Autowired
	private PartnerPointSettleMapper mapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/**
	 * 제휴사 정산파일 업로드
	 * @param 
	 * <li>partnerId=제휴사아이디</li>
	 * <li>fileNm=파일명</li>
	 * @return
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	public void uploadPointFile(String partnerId, String fileNm) throws MvnoServiceException {
		LOGGER.debug("제휴사 정산 파일 업로드 시작..................................");
		
		List<PointFileVO> fileList = new ArrayList<PointFileVO>();
		
		// 제휴사 아이디가 있는지 확인 (나중에 제휴사별로 업로드하도록 하자.
		// 현재는 전송할 파일 전부 다 가져오도록 하자)
		if(StringUtils.isEmpty(partnerId)) {
			throw new MvnoServiceException("EPTNR1004");
		}
		
		PointFileVO pvo = new PointFileVO();
		pvo.setPartnerId(partnerId);
		pvo.setFileNm(fileNm);
		
		// 업로드할 파일 목록 가져오기
		List<?> list = mapper.getPointFileUpList(pvo);
		
		ObjectMapper op = new ObjectMapper();
		LOGGER.debug("list.size() = {}", list.size());
		
		String[] errParam = new String[1];
		errParam[0] = partnerId;
		
		// 전송할 파일이 없음
		if(list.isEmpty()) {
			throw new MvnoServiceException("EPTNR1017", errParam);
		}
		
		for (Object fromValue : list) {
			PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
			
			LOGGER.debug("업로드할 제휴사아이디 = [{}], filename = [{}]", vo.getPartnerId(), vo.getFileNm());
			
			// 제휴사별로 정산결과 가져오기
			fileList.add(vo);
		}
		
		try {
			// FTP로 파일 전송
			// FTP 접속정보
			
			String upDir = "";
			// ftp 루트 임. 나중에 변경될 수 있음.
			
			SFTPUtil ftp = new SFTPUtil();
			String host = propertiesService.getString("msp.sftp.host");
			int port = Integer.parseInt(propertiesService.getString("msp.sftp.port"));
			String userName = propertiesService.getString("msp.sftp.username");
			String password = propertiesService.getString("msp.sftp.pwd");
			ftp.init(host, userName, password, port);
			
			for (PointFileVO vo : fileList) {
				File file = new File(vo.getLocalUpDir() + vo.getFileNm());
				upDir = vo.getHostUpDir();
				try {
					ftp.upload(upDir, file);
					vo.setSendFlag("S");
				} catch (SftpException e1) {
					LOGGER.error("파일 업로드중 에러 발생-- 업로드경로 : [{}], file = [{}]", upDir, file);
					vo.setSendFlag("F");
					vo.setSendResult("FTP 업로드 에러");
				} catch (FileNotFoundException e2) {
					LOGGER.error("파일 업로드중 에러 발생-- 업로드경로 : [{}], file = [{}]", upDir, file);
					vo.setSendFlag("F");
					vo.setSendResult("업로드할 파일이 없습니다");
				} catch (IOException e3) {
					LOGGER.error("파일 업로드중 에러 발생-- 업로드경로 : [{}], file = [{}]", upDir, file);
					vo.setSendFlag("F");
					vo.setSendResult("파일 닫기 실패");
				} catch (Exception e) {
					LOGGER.error("파일 업로드중 에러 발생-- 업로드경로 : [{}], file = [{}]", upDir, file);
					vo.setSendFlag("F");
					vo.setSendResult("파일 업로드중 에러 발생. 에러로그 확인 바람");
				}
				
				mapper.updatePointFileUp(vo);
			}
			
			ftp.disconnection();
		
		} catch (JSchException e) {
			throw new MvnoServiceException("EPTNR1018", errParam, e);
		}
		
		LOGGER.debug("제휴사 정산 파일 업로드 끝..................................");
	}

	/**
	 * 제휴사 정산결과 파일 다운로드
	 * @param 
	 * <li>partnerId=제휴사아이디</li>
	 * <li>fileNm=파일명</li>
	 * @return
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	public void downloadPointFile(String partnerId, String fileNm) throws MvnoServiceException {
		LOGGER.debug("제휴사 정산결과 파일 다운로드 시작..................................");
		
		List<PointFileVO> fileList = new ArrayList<PointFileVO>();
		
		// 제휴사 아이디가 있는지 확인 (나중에 제휴사별로 업로드하도록 하자.
		// 현재는 전송할 파일 전부 다 가져오도록 하자)
		if(StringUtils.isEmpty(partnerId)) {
			throw new MvnoServiceException("EPTNR1004");
		}
		
		PointFileVO pvo = new PointFileVO();
		pvo.setPartnerId(partnerId);
		pvo.setFileNm(fileNm);
		List<?> list = null;
		
		String[] errParam = new String[1];
		errParam[0] = partnerId;
		
		try {
			// 다운로드할 제휴사 및 파일명 가져오기
			list = mapper.getPointFileDownList(pvo);
		} catch(Exception e) {
			throw new MvnoServiceException("EPTNR1009", errParam, e);
		}

		ObjectMapper op = new ObjectMapper();
		LOGGER.debug("list.size() = {}", list.size());
		
		// 다운로드할 파일이 없음
		if(list.isEmpty()) {
			throw new MvnoServiceException("EPTNR1010", errParam);
		}
		
		try {
			
			for (Object fromValue : list) {
				PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
				
				LOGGER.debug("다운로드할 제휴사아이디 = [{}], filename = [{}]", vo.getPartnerId(), vo.getFileNm());
				
				// 제휴사별로 정산결과 가져오기
				fileList.add(vo);
			}
			
			
			// FTP로 파일 전송
			// FTP 접속정보
			String host = propertiesService.getString("msp.sftp.host");
			int port = Integer.parseInt(propertiesService.getString("msp.sftp.port"));
			String userName = propertiesService.getString("msp.sftp.username");
			String password = propertiesService.getString("msp.sftp.pwd");
			
			// ftp 루트 임. 나중에 변경될 수 있음.
			String hDnDir = "";
			String lDnDir = "";
			String dnFileName = "";
			
			SFTPUtil ftp = new SFTPUtil();
			ftp.init(host, userName, password, port);
			
			for (PointFileVO vo : fileList) {
				hDnDir = vo.getHostDownDir();
				if("lpoint".equals(vo.getPartnerId())){
					dnFileName = vo.getFileNm();
				}else{
					dnFileName = PointConstants.RESULT_PREFIX + vo.getFileNm();
				}
				lDnDir = vo.getLocalDownDir();
				
				File desti = new File(lDnDir);
				
				// 디렉토리가 없다면 생성
				if(!desti.exists()) {
					desti.mkdirs();
				}
				
				try {
					ftp.download(hDnDir, dnFileName, lDnDir + dnFileName);
					// 파일처리를 위한 플래그 변경
					vo.setSendResult("D");
				} catch (SftpException e1) {
					LOGGER.error("파일 다운로드중 에러 발생1-- 다운로드경로 : [{}], file = [{}]", hDnDir, dnFileName);
					vo.setSendResult("F");
				} catch (IOException e2) {
					LOGGER.error("파일 다운로드중 에러 발생2-- 다운로드경로 : [{}], file = [{}]", hDnDir, dnFileName);
					vo.setSendResult("F");
				} catch (Exception e) {
					LOGGER.error("파일 다운로드중 에러 발생3-- 다운로드경로 : [{}], file = [{}]", hDnDir, dnFileName);
					vo.setSendResult("F");
				}
				
				mapper.updatePointFileDown(vo);
			}
			
			ftp.disconnection();
			
		} catch (JSchException e) {
			throw new MvnoServiceException("EPTNR1005", errParam, e);
		}
		
		LOGGER.debug("제휴사 정산결과 파일 다운로드 끝..................................");
	}
	
}
