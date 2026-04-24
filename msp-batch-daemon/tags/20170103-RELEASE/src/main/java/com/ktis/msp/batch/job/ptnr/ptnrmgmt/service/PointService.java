package com.ktis.msp.batch.job.ptnr.ptnrmgmt.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.example.SEED;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.PointChkScheduleMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.GiftiPointVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.JejuairPointVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.TmoneyPointVO;
import com.ktis.msp.batch.util.SFTPUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class PointService extends BaseService {
	
	@Autowired
	private PointChkScheduleMapper mapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/**
	 * 제휴사 사용량 및 포인트 계산하기
	 * @param searchVO
	 * <li>billYm=정산월</li>
	 * @return
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	public void callUsgPoint(Map<String, Object> param) throws MvnoServiceException {
		LOGGER.debug("제휴사 사용량 및 포인트 계산하기 시작..................................");
		
		// 제휴사 아이디가 있는지 확인
		/*if(StringUtils.isEmpty(partnerId)) {
			LOGGER.error("정산파일생성 : 제휴사 아이디가 없습니다.");
			return;
		}*/
		
		// 사용량으로 포인트 계산하기
		PointFileVO  vo = new PointFileVO();
		vo.setBillYm((String)param.get("billYm"));
		try {
			mapper.callUsgPoint(vo);
		} catch(Exception e) {
			throw new MvnoServiceException("EPTNR1001", e);
		}
		
		LOGGER.debug("제휴사 사용량 및 포인트 계산하기 끝..................................");
		
	}
	
	/**
	 * 제휴사 청구/수납 확인
	 * @param searchVO
	 * <li>billYm=정산월</li>
	 * @return
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	public void callPointJeju(Map<String, Object> param) throws MvnoServiceException {
		LOGGER.debug("제휴사 청구/수납 확인 시작..................................");
		
		// 제휴사 청구/수납 확인
		PointFileVO  vo = new PointFileVO();
		vo.setBillYm((String)param.get("billYm"));
		
		try {
			mapper.callPointJeju(vo);
		} catch(Exception e) {
			throw new MvnoServiceException("EPTNR1002", e);
		}
		
		LOGGER.debug("제휴사 청구/수납 확인 끝..................................");
		
	}
	
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
				dnFileName = PointConstants.RESULT_PREFIX + vo.getFileNm();
				lDnDir = vo.getLocalDownDir();
				
				File desti = new File(lDnDir);
				
				// 디렉토리가 없다면 생성
				if(!desti.exists()) {
					desti.mkdirs();
				}
				
				try {
					ftp.download(hDnDir, dnFileName, lDnDir + dnFileName);
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


	/**
	 * 제휴사 정산 파일 생성
	 * @param searchVO
	 * <li>partnerId=제휴사아이디</li>
	 * @return
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	public void savePointFile(String partnerId) throws MvnoServiceException {
		LOGGER.debug("제휴사 정산 파일 저장 시작..................................");
		
		List<?> clist = null;
		// 제휴사 아이디가 있는지 확인
		if(StringUtils.isEmpty(partnerId)) {
			throw new MvnoServiceException("EPTNR1006");
		}
		
		String[] errParam = new String[1];
		errParam[0] = partnerId;
		
		// 제주항공 정산파일 저장
		if((PointConstants.STR_JEJUAIR).equals(partnerId)) {
			// 제주항공 포인트 가져오기
			JejuairPointVO vo = new JejuairPointVO();
			vo.setPartnerId(partnerId);
			LOGGER.debug("JejuairPointVO.getPartnerId() = {}", vo.getPartnerId());
			
			try {
				// 제주항공 정산내역 가져오기
				clist = mapper.getJejuPointList(vo);
				if(!clist.isEmpty()) {
					// 제주항공 정산내역 insert
					mapper.insertJejuPointList(vo);
				}
			} catch(Exception e) {
				throw new MvnoServiceException("EPTNR1014", errParam, e);
			}
		} else if((PointConstants.STR_GIFTI).equals(partnerId)) {
			// 엠하우스 정산파일 저장
			GiftiPointVO vo = new GiftiPointVO();
			vo.setPartnerId(partnerId);
			LOGGER.debug("GiftiPointVO.getPartnerId() = {}", vo.getPartnerId());
			
			try {
				// 엠하우스 정산내역 가져오기
				clist = mapper.getGiftiPointList(vo);
				if(!clist.isEmpty()) {
					// 엠하우스 정산내역 insert
					mapper.insertGiftiPointList(vo);
				}
			} catch(Exception e) {
				throw new MvnoServiceException("EPTNR1014", errParam, e);
			}
		} else if((PointConstants.STR_TMONEY).equals(partnerId)) {
			// 티머니 정산파일 저장
			TmoneyPointVO vo = new TmoneyPointVO();
			vo.setPartnerId(partnerId);
			LOGGER.debug("TmoneyPointVO.getPartnerId() = {}", vo.getPartnerId());
			
			try {
				// 티머니 정산내역 가져오기
				clist = mapper.getTmoneyPointList(vo);
				if(!clist.isEmpty()) {
					// 티머니 정산내역 insert
					mapper.insertTmoneyPointList(vo);
				}
			} catch(Exception e) {
				throw new MvnoServiceException("EPTNR1014", errParam, e);
			}
		}
		
		if(!clist.isEmpty()) {
			try {
				savePointFile(clist, partnerId);
			} catch(MvnoServiceException e) {
				throw e;
			} catch(Exception e) {
				throw new MvnoServiceException("EPTNR1016", errParam, e);
			}
		}
		
		LOGGER.debug("제휴사 정산 파일 저장 끝..................................");
		
	}
	
	/**
	 * 제휴사 포인트 정산 파일 저장하기
	 * @param List<?>
	 * <li>list=포인트정산내용</li>
	 * <li>partnerId=제휴사아이디</li>
	 * @return
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	private void savePointFile(List<?> list, String partnerId) throws MvnoServiceException {
		
		LOGGER.debug("제휴사 정산 파일 쓰기 시작..................................");
		ObjectMapper op = new ObjectMapper();
		// 파일명
		String fileName = "";
//		String partnerId = pointVO.getPartnerId();
		
		String lDownBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String lUpBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String hDownBaseDir = "";
		String hUpBaseDir = "";
		List<?> clist = null;
		
		if((PointConstants.STR_JEJUAIR).equals(partnerId)) {
			// 제주항공 디렉토리 가져오기
			clist = mapper.getPartnerSubList(PointConstants.JEJUAIR_IF_NO);
		} else if((PointConstants.STR_GIFTI).equals(partnerId)) {
			// 엠하우스 디렉토리 가져오기
			clist = mapper.getPartnerSubList(PointConstants.GIFTI_IF_NO);
		} else if((PointConstants.STR_TMONEY).equals(partnerId)) {
			// 티머니 디렉토리 가져오기
			clist = mapper.getPartnerSubList(PointConstants.TMONEY_IF_NO);
		}
		
		if(clist.isEmpty()) {
			throw new MvnoServiceException("EPTNR1012");
		}
		
		StringBuffer buf3 = new StringBuffer();
		
		for (Object fromValue : clist) {
			PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
			
			hDownBaseDir = vo.getDownDir();
			hUpBaseDir = vo.getUpDir();
			
			// 첫번째 문자열에 / 가 있으면 빼준다.
			if(hDownBaseDir.charAt(0) == '/') {
				hDownBaseDir = hDownBaseDir.substring(1);
			}
			
			// 첫번째 문자열에 / 가 있으면 빼준다.
			if(hUpBaseDir.charAt(0) == '/') {
				hUpBaseDir = hUpBaseDir.substring(1);
			}
			
			// 마지막 문자열에 / 가 없으면 추가해준다.
			if(hDownBaseDir.charAt(hDownBaseDir.length()-1) != '/') {
				buf3.append(hDownBaseDir);
				buf3.append("/");
				hDownBaseDir = buf3.toString();
			}
			
			buf3.setLength(0);
			if(hUpBaseDir.charAt(hUpBaseDir.length()-1) != '/') {
				buf3.append(hUpBaseDir);
				buf3.append("/");
				hUpBaseDir = buf3.toString();
//				hUpBaseDir += "/";
			}
		}
		
		// 로컬 다운로드 경로
		String lDownDir = lDownBaseDir + partnerId + "/" + hDownBaseDir;
		// 서버 다운로드 경로
		String hDownDir = "";
		StringBuffer buf = new StringBuffer();
//		buf.append(ROOT_DIR);
		buf.append(partnerId);
		buf.append("/");
		buf.append(hDownBaseDir);
		hDownDir = buf.toString();
		
		// 로컬 업로드 경로
		String lUpDir = lUpBaseDir + partnerId + "/" + hUpBaseDir;
		// 서버 업로드 경로 구하기
		StringBuffer buf1 = new StringBuffer();
//		buf1.append(ROOT_DIR);
		buf1.append(partnerId);
		buf1.append("/");
		buf1.append(hUpBaseDir);
		String hUpDir = buf1.toString();
		
		// 생성날짜 구하기
//		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		//System.out.println ( dDate ); 
		
		StringBuffer buf2 = new StringBuffer();
		//sbuf.append(lUpBaseDir);
		if((PointConstants.STR_JEJUAIR).equals(partnerId)) {
			// 제주항공 디렉토리 가져오기
			buf2.append(PointConstants.JEJUAIR_IF_NO);
		} else if((PointConstants.STR_GIFTI).equals(partnerId)) {
			// 엠하우스 디렉토리 가져오기
			buf2.append(PointConstants.GIFTI_IF_NO);
		} else if((PointConstants.STR_TMONEY).equals(partnerId)) {
			// 티머니 디렉토리 가져오기
			buf2.append(PointConstants.TMONEY_IF_NO);
		}
		
		buf2.append("_");
		buf2.append(dDate);
		buf2.append(PointConstants.FILE_EXT);
		fileName = buf2.toString();
		
		String str = "";
		
		try {
			File desti = new File(lUpDir);
			
			// 디렉토리가 없다면 생성
			if (!desti.exists()) {
				desti.mkdirs();
			}
			
			// BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
			BufferedWriter fw = new BufferedWriter(new FileWriter(lUpDir + fileName, false));
			
			// 파일안에 문자열 쓰기
			if((PointConstants.STR_JEJUAIR).equals(partnerId)) {
				// 제주항공 파일 생성
				for (Object fromValue : list) {
					JejuairPointVO vo = op.convertValue(fromValue, JejuairPointVO.class);
					
					// 문자열만들기
					str = getStringJeju(vo);
					
					fw.write(str);
					fw.newLine();
				}
			} else if ((PointConstants.STR_GIFTI).equals(partnerId)) {
				// 엠하우스 파일 생성
				for (Object fromValue : list) {
					GiftiPointVO vo = op.convertValue(fromValue, GiftiPointVO.class);
					
					// 문자열만들기
					str = getStringGifti(vo);
					
					fw.write(str);
					fw.newLine();
				}
			} else if ((PointConstants.STR_TMONEY).equals(partnerId)) {
				// 티머니 파일 생성
				// 헤더 생성 (티머니할때 사용) YYYYMMDD(8자리) + 건수(8자리)
				str = StringUtils.rightPad(dDate, 8);
				fw.write(str);
				fw.newLine();
				str = String.valueOf(list.size());
				str = StringUtils.rightPad(str, 8);
				fw.write(str);
				fw.newLine();
				for (Object fromValue : list) {
					TmoneyPointVO vo = op.convertValue(fromValue, TmoneyPointVO.class);
					
					// 문자열만들기
					str = getStringTmoney(vo);
					
					fw.write(str);
					fw.newLine();
				}
			}
			
			fw.flush();
			
			//객체 닫기
			fw.close(); 
			
		} catch (Exception e) {
			throw new MvnoServiceException("EPTNR1013", e);
		}
		
		// 파일생성 후 파일내용을 디비에 저장
		PointFileVO fileVo = new PointFileVO();
		fileVo.setPartnerId(partnerId);
		fileVo.setLocalUpDir(lUpDir);
		fileVo.setHostUpDir(hUpDir);
		fileVo.setLocalDownDir(lDownDir);
		fileVo.setHostDownDir(hDownDir);
		fileVo.setFileNm(fileName);
		mapper.insertPointFile(fileVo);
		
		LOGGER.debug("제휴사 정산 파일 쓰기 끝..................................");
	}
	
	/**
	 * 파일 읽기
	 * @param List<?>
	 * <li>partnerId=제휴사아이디</li>
	 * @return
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	public void readFile(String partnerId, String fileNm) throws MvnoServiceException {
		
		LOGGER.debug("제휴사 정산결과 파일 읽기 시작..................................");
		
		if(StringUtils.isEmpty(partnerId)) {
			throw new MvnoServiceException("EPTNR1007");
		}
		
		PointFileVO pointFileVO = new PointFileVO();
		
		pointFileVO.setPartnerId(partnerId);
		pointFileVO.setFileNm(fileNm);
		
		String[] errParam = new String[1];
		errParam[0] = partnerId;
		
		try {
			if((PointConstants.STR_JEJUAIR).equals(partnerId)) {
				// 제주항공 파일 읽기
				readFileJeju(pointFileVO);
			} else if ((PointConstants.STR_GIFTI).equals(partnerId)) {
				// 엠하우스 파일 읽기
				readFileGifti(pointFileVO);
			} else if ((PointConstants.STR_TMONEY).equals(partnerId)) {
				// 티머니 파일 읽기
				readFileTmoney(pointFileVO);
			}
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("EPTNR1003", errParam, e);
		}
		LOGGER.debug("제휴사 정산결과 파일 읽기 끝..................................");
	}
	
	/**
	 * 제주항공 정산 결과 파일 읽기
	 * @param PointFileVO
	 * @return
	 * @throws MvnoServiceException
	 */
	public void readFileJeju(PointFileVO pointFileVO) throws MvnoServiceException {
		
		LOGGER.debug("제휴사 정산결과 파일 읽기 시작..................................");
		List<PointFileVO> fileList = new ArrayList<PointFileVO>();
		
		// 로컬에서 읽을 파일 경로 가져오기
//		String lDownBaseDir = propertiesService.getString("msp.sftp.local.download.dir");

		// 읽어야 할 제휴사 및 파일명 가져오기
		List<?> list = mapper.getPointFileReadList(pointFileVO);

		ObjectMapper op = new ObjectMapper();
		LOGGER.debug("list.size() = {}", list.size());
		
		String[] errParam = new String[1];
		errParam[0] = pointFileVO.getPartnerId();
		
		// 읽을 파일이 없음
		if(list.isEmpty()) {
			throw new MvnoServiceException("EPTNR1008", errParam);
		}
		
		for (Object fromValue : list) {
			PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
			
			LOGGER.debug("읽어야할 제휴사아이디 = [{}], filename = [{}]", vo.getPartnerId(), vo.getFileNm());
			
			File f = new File(vo.getLocalDownDir() + PointConstants.RESULT_PREFIX + vo.getFileNm());
			
			// 파일이 존재하는지 확인
			if(f.isFile()) {
				fileList.add(vo);
			}
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		String downloadFileName = "";
		
		// 파일 읽기
		try {
			
			for (PointFileVO vo : fileList) {
				downloadFileName = vo.getLocalDownDir() + PointConstants.RESULT_PREFIX + vo.getFileNm();
				
				try {
					BufferedReader in = new BufferedReader(new FileReader(downloadFileName));
					String s;
					
					while ((s = in.readLine()) != null) {
						// byte 단위로 읽기
						//String s = "가나123다55";
						byte[] b = s.getBytes();
						String memberCi = new String(b, 0, 88);
						String tradeType = new String(b, 88, 3);
						String pointType = new String(b, 91, 3);
						String point = new String(b, 94, 12);
						String channelId = new String(b, 106, 2);
						String partnerCd = new String(b, 108, 3);
						String orderNo = new String(b, 111, 18);
						String amount = new String(b, 129, 12);
						String responseCode = new String(b, 141, 4);
						
//						LOGGER.debug("memberCi,responseCode = [" + memberCi+ "],["+responseCode+ "]");
						
						// 지급결과 DB 업데이트
						JejuairPointVO vo1 = new JejuairPointVO();
						vo1.setPartnerId(vo.getPartnerId().trim());
						vo1.setMemberCi(memberCi.trim());
						vo1.setTradeType(tradeType.trim());
						vo1.setPointType(pointType.trim());
						vo1.setPoint(Integer.parseInt(point.trim()));
						vo1.setChannelId(channelId.trim());
						vo1.setPartnerCd(partnerCd.trim());
						vo1.setOrderNo(orderNo.trim());
						vo1.setAmount(Integer.parseInt(amount.trim()));
						vo1.setResponseCode(responseCode.trim());
						if("0000".equals(responseCode.trim())) {
							vo1.setPayResult("S");
						} else {
							vo1.setPayResult("F");
						}
						vo1.setBillYm(dDate);
						mapper.updateJejuPointMst(vo1);
						mapper.updatePartnerJejuair(vo1);

					}
					vo.setSendResult("S");
					mapper.updatePointFileDown(vo);
					in.close();
				} catch (Exception e1) {
					LOGGER.error("파일읽기에 실패했습니다. 파일명 = [{}]", downloadFileName);
					continue;
				}
			}
			////////////////////////////////////////////////////////////////
			  ////////////////////////////////////////////////////////////////
		} catch (Exception e) {
			throw new MvnoServiceException("EPTNR1011", errParam, e);
		}
		
		
		LOGGER.debug("제휴사 정산결과 파일 읽기 끝..................................");
	}
	
	/**
	 * 엠하우스 정산결과 파일 읽기
	 * @param PointFileVO
	 * @return
	 * @throws Exception
	 */
	public void readFileGifti(PointFileVO pointFileVO) {
		
		LOGGER.debug("제휴사 정산결과 파일 읽기 시작..................................");
		List<PointFileVO> fileList = new ArrayList<PointFileVO>();
		
		// 읽어야 할 제휴사 및 파일명 가져오기
		List<?> list = mapper.getPointFileReadList(pointFileVO);

		ObjectMapper op = new ObjectMapper();
		LOGGER.debug("list.size() = {}", list.size());
		
		// 읽을 파일이 없음
		if(list.isEmpty()) {
			LOGGER.error("파일 읽기 : 읽을 파일이 있는 제휴사가 없습니다.");
			return;
		}
		
		for (Object fromValue : list) {
			PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
			
			LOGGER.debug("읽어야할 제휴사아이디 = [{}], filename = [{}]", vo.getPartnerId(), vo.getFileNm());
			
			File f = new File(vo.getLocalDownDir() + PointConstants.RESULT_PREFIX + vo.getFileNm());
			
			// 파일이 존재하는지 확인
			if(f.isFile()) {
				fileList.add(vo);
			}
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		String downloadFileName = "";
		
		// 파일 읽기
		try {
			
			for (PointFileVO vo : fileList) {
				downloadFileName = vo.getLocalDownDir() + PointConstants.RESULT_PREFIX + vo.getFileNm();
				
				try {
					BufferedReader in = new BufferedReader(new FileReader(downloadFileName));
					String s;
					
					while ((s = in.readLine()) != null) {
						// byte 단위로 읽기
						//String s = "가나123다55";
						byte[] b = s.getBytes();
						String contractNum = new String(b, 0, 10);
						String ctn = new String(b, 10, 32);
						String point = new String(b, 42, 12);
						String usgYm = new String(b,54, 6);
						String responseCode = new String(b, 60, 4);
						
						// 전화번호 복호화
						ctn = decrypt(ctn.trim(), PointConstants.SEED_KEY_GIFTI);
						
//						LOGGER.debug("contractNum,responseCode = [" + contractNum+ "],["+responseCode+ "]");
						
						// 지급결과 DB 업데이트
						GiftiPointVO vo1 = new GiftiPointVO();
						vo1.setPartnerId(vo.getPartnerId().trim());
						vo1.setContractNum(contractNum.trim());
						vo1.setCtn(ctn.trim());
						vo1.setPoint(Integer.parseInt(point.trim()));
						vo1.setUsgYm(usgYm.trim());
						vo1.setResponseCode(responseCode.trim());
						if("1000".equals(responseCode.trim())) {
							vo1.setPayResult("S");
						} else {
							vo1.setPayResult("F");
						}
						vo1.setLinkYm(dDate);
						mapper.updateGiftiPointMst(vo1);
						mapper.updatePartnerGifti(vo1);
						
					}
					vo.setSendResult("S");
					mapper.updatePointFileDown(vo);
					in.close();
				} catch (Exception e1) {
					LOGGER.error("파일읽기에 실패했습니다. 파일명 = [{}]", downloadFileName);
					continue;
				}
			}
			////////////////////////////////////////////////////////////////
			  ////////////////////////////////////////////////////////////////
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("파일 읽기에 실패했습니다");
		}
		
		
		LOGGER.debug("제휴사 정산결과 파일 읽기 끝..................................");
	}
	
	/**
	 * 티머니 정산결과 파일 읽기
	 * @param PointFileVO
	 * @return
	 * @throws Exception
	 */
	public void readFileTmoney(PointFileVO pointFileVO) {
		
		LOGGER.debug("티머니 정산결과 파일 읽기 시작..................................");
		List<PointFileVO> fileList = new ArrayList<PointFileVO>();
		
		// 읽어야 할 제휴사 및 파일명 가져오기
		List<?> list = mapper.getPointFileReadList(pointFileVO);

		ObjectMapper op = new ObjectMapper();
		LOGGER.debug("list.size() = {}", list.size());
		
		// 읽을 파일이 없음
		if(list.isEmpty()) {
			LOGGER.error("티머니 파일 읽기 : 읽을 파일이 있는 제휴사가 없습니다.");
			return;
		}
		
		for (Object fromValue : list) {
			PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
			
			LOGGER.debug("읽어야할 제휴사아이디 = [{}], filename = [{}]", vo.getPartnerId(), vo.getFileNm());
			
			File f = new File(vo.getLocalDownDir() + PointConstants.RESULT_PREFIX + vo.getFileNm());
			
			// 파일이 존재하는지 확인
			if(f.isFile()) {
				fileList.add(vo);
			}
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		String downloadFileName = "";
		
		// 파일 읽기
		try {
			
			for (PointFileVO vo : fileList) {
				downloadFileName = vo.getLocalDownDir() + PointConstants.RESULT_PREFIX + vo.getFileNm();
				
				try {
					BufferedReader in = new BufferedReader(new FileReader(downloadFileName));
					String s;
					int count = 0;
					
					while ((s = in.readLine()) != null) {
						// 헤더부분 건너뛰기
						if(count < 2) {
							count++;
							continue;
						}
						// byte 단위로 읽기
						//String s = "가나123다55";
						byte[] b = s.getBytes();
						String uicc = new String(b, 0, 40);
						String ctn = new String(b, 40, 32);
						String contractNum = new String(b, 72, 10);
						String point = new String(b, 82, 15);
						String pointType = new String(b, 97, 2);
						String billYm = new String(b, 99, 6);
						String responseCode = new String(b, 105, 2);
						
						// 전화번호 복호화
						ctn = decrypt(ctn.trim(), PointConstants.SEED_KEY_TMONEY);
						
//						logger.debug("contractNum,responseCode = [" + contractNum+ "],["+responseCode+ "]");
						
						// 지급결과 DB 업데이트
						TmoneyPointVO vo1 = new TmoneyPointVO();
						vo1.setPartnerId(vo.getPartnerId().trim());
						vo1.setUicc(uicc.trim());
						vo1.setCtn(ctn.trim());
						vo1.setContractNum(contractNum.trim());
						vo1.setPoint(Integer.parseInt(point.trim()));
						vo1.setPointType(pointType.trim());
						vo1.setBillYm(billYm);
						vo1.setResponseCode(responseCode.trim());
						if("00".equals(responseCode.trim())) {
							vo1.setPayResult("S");
						} else {
							vo1.setPayResult("F");
						}
						vo1.setLinkYm(dDate);
						mapper.updateTmoneyPointMst(vo1);
						mapper.updatePartnerTmoney(vo1);
						
					}
					vo.setSendResult("S");
					mapper.updatePointFileDown(vo);
					in.close();
				} catch (Exception e1) {
					LOGGER.error("파일읽기에 실패했습니다. 파일명 = [{}]", downloadFileName);
					continue;
				}
			}
			////////////////////////////////////////////////////////////////
			  ////////////////////////////////////////////////////////////////
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("파일 읽기에 실패했습니다");
		}
		
		
		LOGGER.debug("제휴사 정산결과 파일 읽기 끝..................................");
	}
	
	private String getStringJeju(JejuairPointVO pointVO) {
//		String strResult;
		
		// 파일생성
		String txt1 = pointVO.getMemberCi();
		String txt2 = pointVO.getTradeType();
		String txt3 = pointVO.getPointType();
		String txt4 = String.valueOf(pointVO.getPoint());
		String txt5 = pointVO.getChannelId();
		String txt6 = pointVO.getPartnerCd();
		String txt7 = pointVO.getOrderNo();
		String txt8 = String.valueOf(pointVO.getAmount());

		txt1 = StringUtils.rightPad(txt1, 88);
		txt2 = StringUtils.rightPad(txt2, 3);
		txt3 = StringUtils.rightPad(txt3, 3);
		txt4 = StringUtils.rightPad(txt4, 12);
		txt5 = StringUtils.rightPad(txt5, 2);
		txt6 = StringUtils.rightPad(txt6, 3);
		txt7 = StringUtils.rightPad(txt7, 18);
		txt8 = StringUtils.rightPad(txt8, 12);
		
/*		LOGGER.debug("rightPad txt1 =[" + txt1 + "]");
		LOGGER.debug("rightPad txt2 =[" + txt2 + "]");
		LOGGER.debug("rightPad txt3 =[" + txt3 + "]");
		LOGGER.debug("rightPad txt4 =[" + txt4 + "]");
		LOGGER.debug("rightPad txt5 =[" + txt5 + "]");
		LOGGER.debug("rightPad txt6 =[" + txt6 + "]");
		LOGGER.debug("rightPad txt7 =[" + txt7 + "]");
		LOGGER.debug("rightPad txt8 =[" + txt8 + "]");
*/		
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(txt1);
		sbuf.append(txt2);
		sbuf.append(txt3);
		sbuf.append(txt4);
		sbuf.append(txt5);
		sbuf.append(txt6);
		sbuf.append(txt7);
		sbuf.append(txt8);
		
		LOGGER.debug("sbuf.toString() = [{}]", sbuf.toString());
		LOGGER.debug("sbuf.toString().length() = [{}]", sbuf.toString().length());
		
//		LOGGER.debug("암호화 before pointVO.toString() = [" + pointVO.toString() + "]");
//		JejuairPointVO pointVO1 = encSvc.encryptMhows(pointVO);
//		LOGGER.debug("암호화 after pointVO1.toString() = [" + pointVO1.toString() + "]");
//		JejuairPointVO pointVO2 = encSvc.decryptMhows(pointVO1);
//		LOGGER.debug("복호화 after pointVO2.toString() = [" + pointVO2.toString() + "]");
		
//		String str = encrypt(txt1);
//		LOGGER.debug("str = [{}]", str);
//		LOGGER.debug("str.length() = [{}]", str.length());
//
//		String str1 = decrypt(str);
//		LOGGER.debug("str1 = [{}]", str1);
//		LOGGER.debug("str1.length() = [{}]", str1.length());
		

		return sbuf.toString();
	}
	
	// 객체를 받아서 한줄짜리 문자열을 만든다.
	// 바이트단위 전송을 위한 것임. 
	// 엠하우스 연동파일 만들기
	// 전화번호 암호화 필요함.
	// 연동규격서 나오면 다시 작업해야함.
	private String getStringGifti(GiftiPointVO pointVO) {
		
		// 파일생성
		String txt1 = pointVO.getContractNum();
		String txt2 = pointVO.getCtn();
		String txt3 = String.valueOf(pointVO.getPoint());
		String txt4 = pointVO.getUsgYm();
		
		txt2 = encrypt(txt2, PointConstants.SEED_KEY_GIFTI);
		
		txt1 = StringUtils.rightPad(txt1, 10);
		txt2 = StringUtils.rightPad(txt2, 32);
		txt3 = StringUtils.rightPad(txt3, 12);
		txt4 = StringUtils.rightPad(txt4, 6);
		
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(txt1);
		sbuf.append(txt2);
		sbuf.append(txt3);
		sbuf.append(txt4);
		
		return sbuf.toString();
	}
	
	// 티머니 연동파일 만들기
	// 전화번호 암호화 필요함.
	private String getStringTmoney(TmoneyPointVO pointVO) {
		
		// 파일생성
		String txt1 = pointVO.getUicc();
		String txt2 = pointVO.getCtn();
		String txt3 = pointVO.getContractNum();
		String txt4 = String.valueOf(pointVO.getPoint());
		String txt5 = pointVO.getPointType();
		String txt6 = pointVO.getBillYm();
		
		txt2 = encrypt(txt2, PointConstants.SEED_KEY_TMONEY);

		txt1 = StringUtils.rightPad(txt1, 40);
		txt2 = StringUtils.rightPad(txt2, 32);
		txt3 = StringUtils.rightPad(txt3, 10);
		txt4 = StringUtils.rightPad(txt4, 15);
		txt5 = StringUtils.rightPad(txt5, 2);
		txt6 = StringUtils.rightPad(txt6, 6);
		
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(txt1);
		sbuf.append(txt2);
		sbuf.append(txt3);
		sbuf.append(txt4);
		sbuf.append(txt5);
		sbuf.append(txt6);

		return sbuf.toString();
	}
	
	
	
	/**
	 * 제휴사 다운로드한 파일 옮기기. 사용하지 않음.
	 * @param 
	 * <li>partnerId=제휴사아이디</li>
	 * <li>fileNm=파일명</li>
	 * @return
	 * @throws Exception
	 */
	public void movePointFile(String partnerId, String fileNm) {
		LOGGER.debug("제휴사 정산결과 파일 다운로드 시작..................................");
		
		List<PointFileVO> fileList = new ArrayList<PointFileVO>();
		
		// 제휴사 아이디가 있는지 확인 (나중에 제휴사별로 업로드하도록 하자.
		// 현재는 전송할 파일 전부 다 가져오도록 하자)
		if(StringUtils.isEmpty(partnerId)) {
			LOGGER.debug("정산파일업로드 : 제휴사 아이디가 없습니다.");
			return;
		}
		
		PointFileVO pvo = new PointFileVO();
		pvo.setPartnerId(partnerId);
		pvo.setFileNm(fileNm);
		
		// 다운로드할 제휴사 및 파일명 가져오기
		List<?> list = mapper.getPointFileDownList(pvo);

		ObjectMapper op = new ObjectMapper();
		LOGGER.debug("list.size() = {}", list.size());
		
		// 다운로드할 파일이 없음
		if(list.isEmpty()) {
			LOGGER.error("파일다운로드 : 다운로드할 파일이 없음.");
			return;
		}
		
		
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
		
		try {
		
			SFTPUtil ftp = new SFTPUtil();
			ftp.init(host, userName, password, port);
			
			for (PointFileVO vo : fileList) {
				hDnDir = vo.getHostDownDir();
				dnFileName = vo.getFileNm();
				lDnDir = vo.getLocalDownDir();

				File desti = new File(lDnDir);
				
				// 디렉토리가 없다면 생성
				if(!desti.exists()) {
					desti.mkdirs(); 
				}
				
				try {
					ftp.download(hDnDir, dnFileName, lDnDir + dnFileName);
					vo.setSendResult("S");
					ftp.move(hDnDir, dnFileName, "bak/"+dnFileName);
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
			e.printStackTrace();
			LOGGER.error("포인트 정산 파일 다운로드 중 에러 발생");
		}
		
		LOGGER.debug("제휴사 정산결과 파일 다운로드 끝..................................");
	}
	
	
	private String encrypt(String plain, String seedKey) {
		
		LOGGER.debug("-------------------- 암호화 시작 ----------------------");
		LOGGER.debug("평문 전화번호 = [{}]- len[{}]", plain, plain.length());
		SEED seed = new SEED();
		String sPlain = StringUtils.rightPad(plain, 16);		// 평문을 16자리로 맞춰야 함. 16자리가 넘으면 16자리로 잘라서 암호화 해야함.
		
		//--------------------------------------------------//
		// 암호화 모듈 호출  //
		byte[] pbCipher = seed.encrypt(sPlain, seedKey);		//
		//--------------------------------------------------//
		
		StringBuffer sb = new StringBuffer();
		
		for (int i=0; i<pbCipher.length; i++){
			sb.append(seed.appendLPad(Integer.toHexString(0xff&pbCipher[i]),2,"0"));
		}

		LOGGER.debug("16진수 암호화 전화번호 =[{}]- len[{}]",sb.toString(), sb.toString().length());

		LOGGER.debug("-------------------- 암호화 끝 ----------------------");
		
		return sb.toString();
		
	}

	private String decrypt(String sHexCipher, String seedKey) {
		
		LOGGER.debug("-------------------- 복호화 시작 ----------------------");
		LOGGER.debug("암호화 전화번호 = [{}]- len[{}]",sHexCipher, sHexCipher.length());
		SEED seed = new SEED();

		byte[] bCipher = seed.getStringHextoByte(sHexCipher);
		
		//--------------------------------------------------//
		// 복호화 모듈 호출  //
		byte[] pbPlain2 = seed.decrypt(bCipher, seedKey);	//
		//--------------------------------------------------//
		
		for (int i=0; i<pbPlain2.length; i++){
			seed.appendLPad(Integer.toHexString(0xff&pbPlain2[i]),2,"0");
		}
		
		String temp3 = new String(pbPlain2);
		LOGGER.debug("복호화 전화번호.  [{}]- len[{}]", temp3, temp3.length());
		
		LOGGER.debug("-------------------- 복호화 끝 ----------------------");
		
		return temp3.trim();
		
	}
	
	
	
}
