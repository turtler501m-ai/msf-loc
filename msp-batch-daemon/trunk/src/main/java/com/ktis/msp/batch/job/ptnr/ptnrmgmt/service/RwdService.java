package com.ktis.msp.batch.job.ptnr.ptnrmgmt.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.PartnerPointSettleMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.RwdMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.RwdCmpnVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.RwdVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.MplatformOpenService;
import com.ktis.msp.batch.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 보상서비스 신청서 정보 연동
 */
@Service
public class RwdService extends BaseService {
	
	@Autowired
	private RwdMapper rwdMapper;
	
	@Autowired
	private PartnerPointSettleMapper ptnrMapper;
	
	@Autowired
	private PartnerCryptoService cryptoService;
	
	@Autowired
	private EgovPropertyService propertiesService;
	
	/**
	 * 보상서비스 신청 연동 대상자 생성
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setRwdMemberList() throws MvnoServiceException {
		

		int cnt = 0;
		
		//보상서비스 신청 연동 대상자 생성
		cnt = rwdMapper.insertRwdMember();
		
		//대상 있을 경우 이미지 파일 데이터 생성
		if(cnt > 0) {
			rwdMapper.insertRwdImg();
		}
			
		//연동 파일 생성
		this.getRwdIfOut();
		LOGGER.info("======보상서비스 신청 연동건수======" + cnt);
	}
	
	/**
	 * 보상서비스 가입자 생성
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setRwdSubList() throws MvnoServiceException {
				
		int cnt = 0;
		
		//보상서비스 가입자 생성
		cnt = rwdMapper.insertRwdSub();
		LOGGER.info("======보상서비스 가입자 생성======" + cnt);
	}
	
	/**
	 * 보상서비스 신청 연동 대상자 조회
	 */
	@Transactional(rollbackFor=Exception.class)
	public int getRwdIfOut() throws MvnoServiceException {
		
		String[] errParam = new String[2];
		errParam[0] = PointConstants.STR_WINIA;
		errParam[1] = PointConstants.WINIA_001;
		
		List<RwdVO> list = rwdMapper.getRwdIfOut();
		try {
			this.saveSendFile(list, PointConstants.STR_WINIA, PointConstants.WINIA_001, PointConstants.SEED_KEY_WINIA);
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("EPTNR1022", errParam, e);
		}
		
		return list.size();
	}
	
	/**
	 * 보상서비스 신청 정보 파일 생성
	 */
	public void saveSendFile(List<?> clist, String pId, String ifNo, String seedKey) throws MvnoServiceException {
		
		PointFileVO fileVo = this.regWiniaFileInfo(pId, ifNo, "req");
		ObjectMapper op = new ObjectMapper();
		
		String str = "";
		try {
			LOGGER.debug("======보상서비스 연동 파일 쓰기 시작======");
			
			File desti = new File(fileVo.getLocalUpDir());
			
			// 디렉토리가 없다면 생성
			if (!desti.exists()) {
				desti.mkdirs();
			}
			
			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileVo.getLocalUpDir() + fileVo.getFileNm()), "UTF8"));
			
			for (Object fromValue : clist) {
				RwdVO vo = op.convertValue(fromValue, RwdVO.class);
				
				if ("WINIA_001".equals(ifNo)) {
					// 보상서비스 신청 정보 문자열만들기
					str = getStringReq(vo, seedKey, ifNo);
				} else if ("WINIA_002".equals(ifNo)){
					// 보상서비스 가입/해지/변경 정보 문자열만들기
					str = getStringAdd(vo, seedKey, ifNo);
				} else if ("WINIA_003".equals(ifNo)){
					// 보상서비스 청구/수납 정보 문자열만들기
					str = getStringBill(vo, seedKey, ifNo);
				}
				
				fw.write(str);
				fw.newLine();
			}
			
			fw.flush();
			
			//객체 닫기
			fw.close(); 
			
		} catch (Exception e) {
			throw new MvnoServiceException("EPTNR1013", e);
		}
		LOGGER.debug("======보상서비스 연동 파일 쓰기 끝======");
	}
	
	/**
	 * 문자열 생성
	 */
	private String getStringReq(RwdVO vo, String seedKey, String gubun) { 
		
		StringBuffer sbuf = new StringBuffer();
		
		String txt1 = StringUtil.rightPad(vo.getRwdSeq(), 15, " ", "UTF8");// 보상서비스 시퀀스
		String txt2 = StringUtil.rightPad(vo.getResNo(), 10, " ", "UTF8");// 예약번호
		String txt3 = StringUtil.rightPad(vo.getRequestKey(), 15, " ", "UTF8");// 요청키
		String txt4 = StringUtil.rightPad(vo.getContractNum(), 11, " ", "UTF8");// 계약번호
		String txt5 = StringUtil.rightPad(vo.getRwdProdCd(), 11, " ", "UTF8");// 보상서비스상품코드
		String txt6 = StringUtil.rightPad(vo.getRwdProdNm(), 50, " ", "UTF8");// 보상서비스명
		String txt7 = StringUtil.rightPad(vo.getRwdPric(), 7, " ", "UTF8");// 보상서비스금액
		String txt8 = StringUtil.rightPad(vo.getRwdPrd(), 4, " ", "UTF8");// 보상서비스기간
		String txt9 = StringUtil.rightPad(vo.getOpenDt(), 10, " ", "UTF8");// 서비스가입시 단말기 개통일자
		String txt10 = StringUtil.rightPad(vo.getDvcChgDt(), 10, " ", "UTF8");// 기변일자
		String txt11 = StringUtil.rightPad(vo.getRegstDttm(), 10, " ", "UTF8");// 보상서비스 신청일자
		String txt12 = StringUtil.rightPad(vo.getIfTrgtCd(), 3, " ", "UTF8");// 연동대상 상태코드
		String txt13 = StringUtil.rightPad(vo.getOpenAgentCd(), 15, " ", "UTF8");// 개통대리점코드
		String txt14 = StringUtil.rightPad(vo.getOpenAgent(), 50, " ", "UTF8");// 개통대리점
		String txt15 = cryptoService.encrypt(StringUtil.rightPad(vo.getCtn(), 34, " ", "UTF8"), seedKey);// 전화번호
		String txt16 = StringUtil.rightPad(vo.getCustType(), 4, " ", "UTF8");// 고객구분
		String txt17 = cryptoService.encrypt(StringUtil.rightPad(vo.getCustNm(), 34, " ", "UTF8"), seedKey);// 고객명
		String txt18 = cryptoService.encrypt(StringUtil.rightPad(vo.getBirthDt(), 34, " ", "UTF8"), seedKey);// 생년월일
		String txt19 = StringUtil.rightPad(vo.getReqBuyType(), 4, " ", "UTF8");// 구매유형
		String txt20 = StringUtil.rightPad(vo.getModelId(), 20, " ", "UTF8");// 단말기모델ID
		String txt21 = StringUtil.rightPad(vo.getModelNm(), 30, " ", "UTF8");// 단말기모델명
		String txt22 = StringUtil.rightPad(vo.getIntmSrlNo(), 20, " ", "UTF8");// 단말기일련번호
		String txt23 = StringUtil.rightPad(vo.getImei(), 20, " ", "UTF8");// 단말고유식별번호
		String txt24 = StringUtil.rightPad(vo.getBanAddr(), 150, " ", "UTF8");// 주소
		String txt25 = StringUtil.rightPad(vo.getPrdtLnchDt(), 10, " ", "UTF8");// 단말기출시일
		String txt26 = StringUtil.rightPad(vo.getOutUnitPric(), 10, " ", "UTF8");// 단말기출고가
		String txt27 = StringUtil.rightPad(vo.getBuyPric(), 10, " ", "UTF8");// 단말기구입가
		String txt28 = StringUtil.rightPad(vo.getChnCd(), 3, " ", "UTF8");// 가입채널코드
		String txt29 = StringUtil.rightPad(vo.getFileId(), 20, " ", "UTF8");// 이미지 파일 아이디
		String txt30 = StringUtil.rightPad(vo.getFileExt(), 5, " ", "UTF8");// 이미지 파일 확장자

		sbuf.append(txt1);
		sbuf.append(txt2);
		sbuf.append(txt3);
		sbuf.append(txt4);
		sbuf.append(txt5);
		sbuf.append(txt6);
		sbuf.append(txt7);
		sbuf.append(txt8);
		sbuf.append(txt9);
		sbuf.append(txt10);
		sbuf.append(txt11);
		sbuf.append(txt12);
		sbuf.append(txt13);
		sbuf.append(txt14);
		sbuf.append(txt15);
		sbuf.append("  ");
		sbuf.append(txt16);
		sbuf.append(txt17);
		sbuf.append("  ");
		sbuf.append(txt18);
		sbuf.append("  ");
		sbuf.append(txt19);
		sbuf.append(txt20);
		sbuf.append(txt21);
		sbuf.append(txt22);
		sbuf.append(txt23);
		sbuf.append(txt24);
		sbuf.append(txt25);
		sbuf.append(txt26);
		sbuf.append(txt27);
		sbuf.append(txt28);
		sbuf.append(txt29);
		sbuf.append(txt30);
				
		return sbuf.toString();
	}
	
	@Transactional(rollbackFor=Exception.class)
	public PointFileVO regWiniaFileInfo(String pId, String ifNo, String gubun) throws MvnoServiceException {
      
		LOGGER.debug("======WINIA 파일 정보 생성 시작======");
  
		String lDownBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String lUpBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String hDownBaseDir = "";
		String hUpBaseDir = "";
 
		List<?> clist = null;
		 
		// 파일명 가져옴
		clist = ptnrMapper.getPartnerSubList(ifNo);

		if(clist.isEmpty()) {
			throw new MvnoServiceException("EPTNR1012");
		}
  
		ObjectMapper op = new ObjectMapper();

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
			}
		}
      
		// 로컬 다운로드 경로
		String lDownDir = lDownBaseDir + pId + "/" + hDownBaseDir;
		// 서버 다운로드 경로
		String hDownDir = "";
		StringBuffer buf = new StringBuffer();
		buf.append(pId);
		buf.append("/");
		buf.append(hDownBaseDir);
		hDownDir = buf.toString();
      
		// 로컬 업로드 경로
		String lUpDir = lUpBaseDir + pId + "/" + hUpBaseDir;
		// 서버 업로드 경로 구하기
		StringBuffer buf1 = new StringBuffer();
		buf1.append(pId);
		buf1.append("/");
		buf1.append(hUpBaseDir);
		String hUpDir = buf1.toString();
		  
		String fileName = "";
      
		// 생성날짜 구하기
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		StringBuffer buf2 = new StringBuffer();
		
		buf2.append(ifNo);
		buf2.append("_");
		buf2.append(dDate);
		buf2.append(PointConstants.FILE_EXT);
		
		fileName = buf2.toString();
      
		PointFileVO fileVo = new PointFileVO();
		  
		fileVo.setPartnerId(pId);
		fileVo.setLocalUpDir(lUpDir);
		fileVo.setHostUpDir(hUpDir);
		fileVo.setLocalDownDir(lDownDir);
		fileVo.setHostDownDir(hDownDir);
		fileVo.setFileNm(fileName);
		
		if(!gubun.equals("req")) {
			fileVo.setSendFlag("S");
		}
		  
		ptnrMapper.deletePointFile(fileVo);
		ptnrMapper.insertPointFile(fileVo);
		 
		LOGGER.debug("======보상서비스 파일 정보 생성 끝======");
		  
		return fileVo;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public PointFileVO regRwdFileInfo(String pId, String ifNo, String gubun) throws MvnoServiceException {
      
		LOGGER.debug("보상서비스 파일 정보 생성 시작");
  
		String lDownBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String lUpBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String hDownBaseDir = "";
		String hUpBaseDir = "";
 
		List<?> clist = null;
		 
		clist = ptnrMapper.getPartnerSubList(ifNo);

		if(clist.isEmpty()) {
			throw new MvnoServiceException("EPTNR1012");
		}
  
		ObjectMapper op = new ObjectMapper();

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
			}
		}
      
		// 로컬 다운로드 경로
		String lDownDir = lDownBaseDir + pId + "/" + hDownBaseDir;
		// 서버 다운로드 경로
		String hDownDir = "";
		StringBuffer buf = new StringBuffer();
		buf.append(pId);
		buf.append("/");
		buf.append(hDownBaseDir);
		hDownDir = buf.toString();
      
		// 로컬 업로드 경로
		String lUpDir = lUpBaseDir + pId + "/" + hUpBaseDir;
		// 서버 업로드 경로 구하기
		StringBuffer buf1 = new StringBuffer();
		buf1.append(pId);
		buf1.append("/");
		buf1.append(hUpBaseDir);
		String hUpDir = buf1.toString();
		  
		String fileName = "";
      
		// 생성날짜 구하기
		
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		
		calendar.add(Calendar.DATE, -1);		
		String dDate = formatter.format(calendar.getTime());		
		
		StringBuffer buf2 = new StringBuffer();
		
		buf2.append(ifNo);
		buf2.append("_");
		buf2.append(dDate);
		buf2.append(PointConstants.FILE_EXT);
		
		fileName = buf2.toString();
      
		PointFileVO fileVo = new PointFileVO();
		  
		fileVo.setPartnerId(pId);
		fileVo.setLocalUpDir(lUpDir);
		fileVo.setHostUpDir(hUpDir);
		fileVo.setLocalDownDir(lDownDir);
		fileVo.setHostDownDir(hDownDir);
		fileVo.setFileNm(fileName);
		
		if(!gubun.equals("req")) {
			fileVo.setSendFlag("S");
		}
		  
		ptnrMapper.deletePointFile(fileVo);
		ptnrMapper.insertPointFile(fileVo);
		 
		LOGGER.debug("보상서비스 파일 정보 생성 끝");
		  
		return fileVo;
	}
	
	/** 신청가입 결과 정보 파일 읽어서 처리 */
	@Transactional(rollbackFor=Exception.class)
	public void rwdReadFile(String fileName, String gubun) throws MvnoServiceException {
		
		LOGGER.debug("==========위니아 신청파일 읽기 시작==========" );
		
		int cnt = 0;
		PointFileVO pointFileVO = new PointFileVO();
		 
		pointFileVO.setPartnerId(PointConstants.STR_WINIA);
		pointFileVO.setFileNm(fileName);
		 
		List<?> list = ptnrMapper.getPointFileReadList(pointFileVO);
		  
		ObjectMapper op = new ObjectMapper();
		  
		String[] errParam = new String[1];
		errParam[0] = pointFileVO.getPartnerId();
		  
		// 읽을 파일이 없음
		if(list.isEmpty()) {
			throw new MvnoServiceException("EPTNR1008", errParam);
		}
  
		List<PointFileVO> fileList = new ArrayList<PointFileVO>();
  
		for (Object fromValue : list) {
			PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
      
			File f = new File(vo.getLocalDownDir() + vo.getFileNm());
 
			// 파일이 존재하는지 확인
			if(f.isFile()) {
				fileList.add(vo);
			}
		}
  
		String downloadFileName = "";
		
		// 파일 읽기
		try {
			for (PointFileVO fVo : fileList) {
				
				downloadFileName = fVo.getLocalDownDir() + fVo.getFileNm();
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(downloadFileName), "EUC-KR"));
					String s;
					
					while ((s = in.readLine()) != null) {
						if (s.length() > 1000) {

							RwdVO vo = new RwdVO();
							vo.setRwdSeq(s.substring(0, 12));
							vo.setVrfyRsltCd(s.substring(15, 17));
							vo.setVrfyDttm(s.substring(18, 27));
							vo.setVrfyId(s.substring(28, 47));
							vo.setRmk(s.substring(48, 1047));

							// 해당값으로 업데이트 해야함.
							rwdMapper.updateResult(vo);

							if (s.length() >= 1058) {
								String buyPric = s.substring(1048, 1058).trim();
								if (!buyPric.isEmpty()) {
									vo.setBuyPric(buyPric);
									// 위니아에서 수정한 단말기구입가 update
									rwdMapper.updateBuyPric(vo);
								}
							}
							
						}

						
						cnt++;
					}
   
					fVo.setSendResult("S");
					ptnrMapper.updatePointFileDown(fVo);
					in.close();
				} catch (Exception e1) {
					LOGGER.error("파일읽기에 실패했습니다. 에러 = [{}]", e1);
					LOGGER.error("파일읽기에 실패했습니다. 파일명 = [{}]", downloadFileName);
					continue;
				}
			}
			
		} catch (Exception e) {
		     throw new MvnoServiceException("EPTNR1011", errParam, e);
		}
		
		LOGGER.info("보상정보 연동건수:"+cnt);
	}
	
	/** 권리실행접수현황 결과 정보 파일 읽어서 처리 */
	@Transactional(rollbackFor=Exception.class)
	public void rwdStaReadFile(String fileName, String gubun) throws MvnoServiceException {
		
		LOGGER.debug("==========권리실행접수현황 결과 읽기 시작==========" );
		
		int cnt = 0;
		PointFileVO pointFileVO = new PointFileVO();
		 
		pointFileVO.setPartnerId(PointConstants.STR_WINIA);
		pointFileVO.setFileNm(fileName);
		 
		List<?> list = ptnrMapper.getPointFileReadList(pointFileVO);
		  
		ObjectMapper op = new ObjectMapper();
		  
		String[] errParam = new String[1];
		errParam[0] = pointFileVO.getPartnerId();
		  
		// 읽을 파일이 없음
		if(list.isEmpty()) {
			throw new MvnoServiceException("EPTNR1008", errParam);
		}
  
		List<PointFileVO> fileList = new ArrayList<PointFileVO>();
  
		for (Object fromValue : list) {
			PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
      
			File f = new File(vo.getLocalDownDir() + vo.getFileNm());
 
			// 파일이 존재하는지 확인
			if(f.isFile()) {
				fileList.add(vo);
			}
		}
  
		String downloadFileName = "";
		
		// 파일 읽기
		try {
			for (PointFileVO fVo : fileList) {
				
				downloadFileName = fVo.getLocalDownDir() + fVo.getFileNm();
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(downloadFileName), "EUC-KR"));
					String s;
					
					while ((s = in.readLine()) != null) {
						if (s.length() > 100) {

							RwdCmpnVO vo = new RwdCmpnVO();
							vo.setRwdSeq(s.substring(0, 12).trim());
							vo.setRwdCmpnSeq(s.substring(15, 29).trim());
							vo.setCmpnCt(s.substring(30, 79).trim());
							vo.setCmpnDt(s.substring(80, 89).trim());
							vo.setCmpnId(s.substring(90, 104).trim());
							vo.setCmpnStatCd(s.substring(105, 107).trim());

							// 권리실행접수현황 등록
							rwdMapper.insertRwdCmpnRes(vo);
							
							// 권리실행 원장 테이블 조회 후 insert or update 처리
							int returnCnt = rwdMapper.selectRwdCmpnRes(vo);
							
							if(returnCnt > 0) {
								//update
								rwdMapper.uptRwdCmpnRes(vo);
							} else {
								//insert
								rwdMapper.intRwdCmpnRes(vo);
							}
							
						}
						cnt++;
					}
   
					fVo.setSendResult("S");
					ptnrMapper.updatePointFileDown(fVo);
					in.close();
				} catch (Exception e1) {
					LOGGER.error("파일읽기에 실패했습니다. 에러 = [{}]", e1);
					LOGGER.error("파일읽기에 실패했습니다. 파일명 = [{}]", downloadFileName);
					continue;
				}
			}
			
		} catch (Exception e) {
		     throw new MvnoServiceException("EPTNR1011", errParam, e);
		}
		
		LOGGER.info("권리실행 접수현황 등록 건수 :"+cnt);
	}
	
	
	/** 보상처리현황 결과 정보 파일 읽어서 처리 */
	@Transactional(rollbackFor=Exception.class)
	public void rwdCmpReadFile(String fileName, String gubun) throws MvnoServiceException {
		
		LOGGER.debug("==========보상처리현황 결과 읽기 시작==========" );
		
		int cnt = 0;
		PointFileVO pointFileVO = new PointFileVO();
		 
		pointFileVO.setPartnerId(PointConstants.STR_WINIA);
		pointFileVO.setFileNm(fileName);
		 
		List<?> list = ptnrMapper.getPointFileReadList(pointFileVO);
		  
		ObjectMapper op = new ObjectMapper();
		  
		String[] errParam = new String[1];
		errParam[0] = pointFileVO.getPartnerId();
		  
		// 읽을 파일이 없음
		if(list.isEmpty()) {
			throw new MvnoServiceException("EPTNR1008", errParam);
		}
  
		List<PointFileVO> fileList = new ArrayList<PointFileVO>();
  
		for (Object fromValue : list) {
			PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
      
			File f = new File(vo.getLocalDownDir() + vo.getFileNm());
 
			// 파일이 존재하는지 확인
			if(f.isFile()) {
				fileList.add(vo);
			}
		}
  
		String downloadFileName = "";
		
		// 파일 읽기
		try {
			for (PointFileVO fVo : fileList) {
				
				downloadFileName = fVo.getLocalDownDir() + fVo.getFileNm();
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(downloadFileName), "EUC-KR"));
					String s;
					
					while ((s = in.readLine()) != null) {
						if (s.length() > 1000) {

							RwdCmpnVO vo = new RwdCmpnVO();
							vo.setRwdSeq(s.substring(0, 14).trim()); //보상서비스 시퀀스 15자리
							vo.setRwdCmpnSeq(s.substring(15, 29).trim()); //권리실행 접수번호 15자리
							vo.setCmpnCt(s.substring(30, 79).trim()); //권리실행 접수센터 50자리
							vo.setCmpnDt(s.substring(80, 89).trim()); //권리실행 접수일자 10자리
							vo.setCmpnId(s.substring(90, 104).trim()); //권리실행 접수담당자 15자리
							vo.setCmpnStatCd(s.substring(105, 107).trim()); //권리실행 접수 상태코드 3자리
							vo.setCtInDt(s.substring(108, 117).trim()); //보상센터 입고일자 10자리
							vo.setCtInId(s.substring(118, 132).trim()); //보상센터 입고담당자 15자리
							vo.setCtOutDt(s.substring(133, 142).trim()); //보상센터 출고일자 10자리
							vo.setCtOutId(s.substring(143, 157).trim()); //보상센터 출고담당자 15자리
							vo.setBuyPric(s.substring(158, 167).trim()); //단말기구입가 10자리
							vo.setRwdRt(s.substring(168, 177).trim()); //적용비율 10자리
							vo.setFtrPric(s.substring(178, 187).trim()); //미래가격 10자리
							vo.setOstPric(s.substring(188, 197).trim()); //차감금액 10자리
							vo.setAsrPric(s.substring(198, 207).trim()); //보장금액 10자리
							vo.setOstResn(s.substring(208, 1207).trim()); //차감사유 1000자리
							vo.setCnfmLv(s.substring(1208, 1210).trim()); //판정 등급 3자리
							vo.setCnfmDt(s.substring(1211, 1225).trim()); //등급판정 일자 15자리
							vo.setCnfmId(s.substring(1226, 1235).trim()); //등급판정 담당자 10자리
							vo.setPayPlanDttm(s.substring(1236, 1245).trim()); //보상금 지급예정일자 10자리
							vo.setObDt(s.substring(1246, 1255).trim()); //OB 결과 확정 일자 10자리
							vo.setObId(s.substring(1256, 1270).trim()); //OB 결과 확정 상담 담당자 15자리
							vo.setBankNm(s.substring(1271, 1290).trim()); //은행명 20자리
							vo.setAcntNo(s.substring(1291, 1310).trim()); //입금계좌번호 20자리
							vo.setDpstNm(s.substring(1311, 1330).trim()); //예금주 20자리
							vo.setRetnDt(s.substring(1331, 1340).trim()); //반송 출고일자 10자리
							vo.setRetnId(s.substring(1341, 1360).trim()); //반송 출고담당자 20자리
							vo.setRetnIv(s.substring(1361, 1380).trim()); //반송 출고 송장번호 20자리

							// 보상처리현황 등록
							rwdMapper.insertRwdCmpnProc(vo);
							
							// 보상처리현황 원장 테이블 조회 후 insert or update 처리
							int returnCnt = rwdMapper.selectRwdCmpnRes(vo);
							
							if(returnCnt > 0) {
								//update
								rwdMapper.uptRwdCmpnProc(vo);
							} else {
								//insert
								rwdMapper.intRwdCmpnProc(vo);
							}
							
						}
						cnt++;
					}
   
					fVo.setSendResult("S");
					ptnrMapper.updatePointFileDown(fVo);
					in.close();
				} catch (Exception e1) {
					LOGGER.error("파일읽기에 실패했습니다. 에러 = [{}]", e1);
					LOGGER.error("파일읽기에 실패했습니다. 파일명 = [{}]", downloadFileName);
					continue;
				}
			}
			
		} catch (Exception e) {
		     throw new MvnoServiceException("EPTNR1011", errParam, e);
		}
		
		LOGGER.info("보상처리현황 등록 건수 :"+cnt);
	}
	
	/**
	 * 보상 부가서비스 가입 대상자 조회
	 */
	@Transactional(rollbackFor=Exception.class)
	public void subRwdOrder() throws MvnoServiceException {		
		
		// 보상 부가서비스 가입자 조회
		List<RwdVO> list = rwdMapper.selectRwdOrder();
		
		//MP 전송
		this.subRwdOrderAddSvc(list);
	}
	
	/**
	 * 보상 부가서비스 가입 처리
	 * @param searchVO
	 * @return
	 */
	public int subRwdOrderAddSvc(List<RwdVO> list) throws MvnoServiceException {
		int procCnt = 0;
				
		try{
			
			MplatformOpenService mp = new MplatformOpenService();
			
			for(RwdVO vo : list) {
				HashMap<String, String> paramMap = new HashMap<String,String>();
				paramMap.put("mdlInd",		"BAT");
				paramMap.put("ncn",			vo.getNcn());
				paramMap.put("ctn",			vo.getCtn());
				paramMap.put("custId",		vo.getCustId());
				paramMap.put("appEventCd",	"X21");
				paramMap.put("soc",			vo.getRwdProdCd());
				
				// 부가서비스 가입 성공 할때까지 재시도 (최대3번)
				int roof = 3;
				for (int i=0; i<roof; i++) {
					// 셀프케어 호출
					paramMap = mp.SelfCareCallService(paramMap, propertiesService.getString("mplatform.selfcare.url"));
					
					if("SUCCESS".equals((String) paramMap.get("result"))){
						if("N".equals((String) paramMap.get("resultType"))){
							LOGGER.info("SUCCESS] 보상 부가서비스 가입 성공 > " + vo.getContractNum());
							vo.setRsltCd("0000");
							vo.setRsltMsg("");
							break;
						}else{
							LOGGER.error("FAIL] 보상 부가서비스 가입 실패 {} > {} ",i+1,vo.getContractNum());
							vo.setRsltCd("9999");
							vo.setRsltMsg((String) paramMap.get("resultMsg"));
						}
					}else{
						LOGGER.error("FAIL] 보상 부가서비스 가입 실패 {} > {} ",i+1,vo.getContractNum());
						vo.setRsltCd("9999");
						vo.setRsltMsg((String) paramMap.get("result"));
					}
					// 3초간 대기
					mp.sleep(3);
				}
				// 3초간 대기
				mp.sleep(3);
				
				// 부가서비스 처리 결과 업데이트
				rwdMapper.updateRwdAddSvc(vo);
				
				procCnt++;
			}
		}catch(Exception e){
			throw new MvnoServiceException("EMSP1007", e);
		}
		return procCnt;
	}
	
	// SMS 전송
//	public void rwdInfoSendSms(RwdVO vo) {
//		
//		Calendar cal = Calendar.getInstance();
//		String templateId = null;
//		int year = cal.get(Calendar.YEAR);
//		int month = cal.get(Calendar.MONTH)+1;
//		int yesterday = cal.get(Calendar.DATE)-1;
//		
//		//SMS 전송
//		SmsCommonVO smsVO = null;
//		
//		// 부가서비스 가입 안내
//		if(vo.getCanRsltCd().equals("UNQ") || vo.getCanRsltCd().equals("OVR")) {
//			templateId = "87";
//			smsVO = smsCommonMapper.getTemplateText(templateId);
//
//			smsVO.setSendMessage((smsVO.getTemplateText())
//									.replaceAll(Pattern.quote("#{insrProdNm}"), vo.getRwdProdNm())
//									.replaceAll(Pattern.quote("#{year}"), String.valueOf(year))
//									.replaceAll(Pattern.quote("#{month}"), String.valueOf(month))
//									.replaceAll(Pattern.quote("#{day}"), String.valueOf(yesterday)));
//			
//			smsVO.setMobileNo(vo.getCtn());
//			smsVO.setTemplateId(templateId);
//		// 부가서비스 결과 안내
//		} else if (vo.getCanRsltCd().equals("LTL")) {
//			templateId = "88";
//			smsVO = smsCommonMapper.getTemplateText(templateId);
//
//			smsVO.setSendMessage((smsVO.getTemplateText())
//									.replaceAll(Pattern.quote("#{insrProdNm}"), vo.getRwdProdNm())
//									.replaceAll(Pattern.quote("#{year}"), String.valueOf(year))
//									.replaceAll(Pattern.quote("#{month}"), String.valueOf(month))
//									.replaceAll(Pattern.quote("#{day}"), String.valueOf(yesterday)));
//			
//			smsVO.setMobileNo(vo.getCtn());
//			smsVO.setTemplateId(templateId);
//		// 부가서비스 해지 안내
//		} else if (vo.getCanRsltCd().equals("EXP")) {
//			templateId = "89";
//			smsVO = smsCommonMapper.getTemplateText(templateId);
//
//			smsVO.setSendMessage((smsVO.getTemplateText())
//									.replaceAll(Pattern.quote("#{insrProdNm}"), vo.getRwdProdNm())
//									.replaceAll(Pattern.quote("#{year}"), String.valueOf(year))
//									.replaceAll(Pattern.quote("#{month}"), String.valueOf(month))
//									.replaceAll(Pattern.quote("#{day}"), String.valueOf(yesterday)));
//			
//			smsVO.setMobileNo(vo.getCtn());
//			smsVO.setTemplateId(templateId);
//		}
//		
//		smsCommonMapper.insertTemplateMsgQueue(smsVO);
//		
//		smsVO.setSendDivision("MSP");
//		smsVO.setContractNum((vo.getContractNum()));
//		smsVO.setReqId("BATCH");
//		
//		// SMS 발송내역 등록
//		smsCommonMapper.insertSmsSendMst(smsVO);
//		
//	}
	
	/**
	 * 전일자 기준 부가서비스 가입/해지 고객 찾기
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setRwdAddSvcList() throws MvnoServiceException {

		LOGGER.info("======전일자 기준 부가서비스 가입/해지/변경 연동 시작======");
		// 전일자 기준 부가서비스 가입 정보 업데이트
		List<RwdVO> addList = rwdMapper.selectAddSub();
		for (int i = 0; i < addList.size(); i++) {
			addList.get(i).setInfoOutType("2"); // 가입
			addList.get(i).setRwdStatCd("1"); // 가입

			// MSP_RWD_INFO_OUT 연동테이블 업데이트
			rwdMapper.updateRwdSvc(addList.get(i));
			
			// MSP_RWD_MEMBER 테이블 상태값 변경
			rwdMapper.updateMemberStat(addList.get(i));
		}
		// 전일자 기준 부가서비스 해지 정보 업데이트
		List<RwdVO> canList = rwdMapper.selectAddCan();
		for (int i = 0; i < canList.size(); i++) {
			canList.get(i).setInfoOutType("4"); // 해지
			canList.get(i).setRwdStatCd("2"); // 해지
			canList.get(i).setCanDttm(canList.get(i).getExpirationDate()); // 해지
			
			// MSP_RWD_INFO_OUT 연동테이블 업데이트
			rwdMapper.updateRwdSvc(canList.get(i));
			
			// MSP_RWD_MEMBER 테이블 상태값 변경 ==> 변경/해지 상유 넣어줘야함. CAN_RSLT_CD EXP:보험기간만료, OVR:검증기간초과, REQ:고객중도해지, TOL:권리실행해지, UNQ:부적격해지
			rwdMapper.updateMemberStat(canList.get(i));
		}
		// 전일자 기준 부가서비스 변경 정보 업데이트
// 2023-09-12 부가서비스 변경 처리 로직 제외
//		List<RwdVO> chgList = rwdMapper.selectAddChg();
//		for (int i = 0; i < chgList.size(); i++) {
//			chgList.get(i).setInfoOutType("3"); // 변경
//			chgList.get(i).setRwdStatCd("D"); // 변경
//
//			// MSP_RWD_INFO_OUT 연동테이블 업데이트
//			rwdMapper.updateRwdSvc(chgList.get(i));
//			
//			// MSP_RWD_MEMBER 테이블 상태값 변경
//			rwdMapper.updateMemberStat(chgList.get(i));
//		}
		LOGGER.info("======전일자 기준 부가서비스 가입/해지/변경 연동 종료======");
		
		// 파일 생성
		this.getRwdIfOutAdd();
	}
	
	/**
	 * 부가서비스 가입/해지/변경 연동 파일 생성
	 */
	@Transactional(rollbackFor=Exception.class)
	public int getRwdIfOutAdd() throws MvnoServiceException {
		
		String[] errParam = new String[2];
		errParam[0] = PointConstants.STR_WINIA;
		errParam[1] = PointConstants.WINIA_002;
		
		List<RwdVO> list = rwdMapper.getRwdIfOutAdd();
		try {
			this.saveSendFile(list, PointConstants.STR_WINIA, PointConstants.WINIA_002, PointConstants.SEED_KEY_WINIA);
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("EPTNR1022", errParam, e);
		}
		
		return list.size();
	}
	
	
	/**
	 * 문자열 생성
	 */
	private String getStringAdd(RwdVO vo, String seedKey, String gubun) { 
		
		LOGGER.debug("======문자열 생성 시작======");
		StringBuffer sbuf = new StringBuffer();
		
		String txt1 = StringUtil.rightPad(vo.getRwdSeq(), 15, " ", "UTF8");// 보상서비스 시퀀스
		String txt2 = StringUtil.rightPad(vo.getResNo(), 10, " ", "UTF8");// 예약번호
		String txt3 = StringUtil.rightPad(vo.getRequestKey(), 15, " ", "UTF8");// 요청키
		String txt4 = StringUtil.rightPad(vo.getContractNum(), 11, " ", "UTF8");// 계약번호
		String txt5 = StringUtil.rightPad(vo.getRwdProdCd(), 11, " ", "UTF8");// 보상서비스상품코드
		String txt6 = StringUtil.rightPad(vo.getRwdProdNm(), 50, " ", "UTF8");// 보상서비스명
		String txt7 = StringUtil.rightPad(vo.getRwdPric(), 7, " ", "UTF8");// 보상서비스금액
		String txt8 = StringUtil.rightPad(vo.getRwdPrd(), 4, " ", "UTF8");// 보상서비스기간
		String txt9 = StringUtil.rightPad(vo.getOpenDt(), 10, " ", "UTF8");// 서비스가입시 단말기 개통일자
		String txt10 = StringUtil.rightPad(vo.getDvcChgDt(), 10, " ", "UTF8");// 기변일자
		String txt11 = StringUtil.rightPad(vo.getReqDttm(), 10, " ", "UTF8");// 보상서비스 신청일자
		String txt12 = StringUtil.rightPad(vo.getCanDttm(), 10, " ", "UTF8");// 보상서비스 해지일자
		String txt13 = StringUtil.rightPad(vo.getStrtDttm(), 10, " ", "UTF8");// 보상서비스 시작일자
		String txt14 = StringUtil.rightPad(vo.getEndDttm(), 10, " ", "UTF8");// 보상서비스 종료일자
		String txt15 = StringUtil.rightPad(vo.getRwdStatCd(), 3, " ", "UTF8");// 보상서비스 상태코드
		String txt16 = cryptoService.encrypt(StringUtil.rightPad(vo.getCtn(), 34, " ", "UTF8"), seedKey);// 전화번호
		String txt17 = StringUtil.rightPad(vo.getCustType(), 4, " ", "UTF8");// 고객구분
		String txt18 = cryptoService.encrypt(StringUtil.rightPad(vo.getCustNm(), 34, " ", "UTF8"), seedKey);// 고객명
		String txt19 = cryptoService.encrypt(StringUtil.rightPad(vo.getBirthDt(), 34, " ", "UTF8"), seedKey);// 생년월일
		String txt20 = StringUtil.rightPad(vo.getReqBuyType(), 4, " ", "UTF8");// 구매유형
		String txt21 = StringUtil.rightPad(vo.getModelId(), 20, " ", "UTF8");// 단말기모델ID
		String txt22 = StringUtil.rightPad(vo.getModelNm(), 30, " ", "UTF8");// 단말기모델명
		String txt23 = StringUtil.rightPad(vo.getIntmSrlNo(), 20, " ", "UTF8");// 단말기일련번호
		String txt24 = StringUtil.rightPad(vo.getImei(), 20, " ", "UTF8");// 단말고유식별번호
		String txt25 = StringUtil.rightPad(vo.getBanAddr(), 150, " ", "UTF8");// 주소
		String txt26 = StringUtil.rightPad(vo.getPrdtLnchDt(), 10, " ", "UTF8");// 단말기출시일
		String txt27 = StringUtil.rightPad(vo.getOutUnitPric(), 10, " ", "UTF8");// 단말기출고가
		String txt28 = StringUtil.rightPad(vo.getBuyPric(), 10, " ", "UTF8");// 단말기구입가
		String txt29 = StringUtil.rightPad(vo.getChnCd(), 3, " ", "UTF8");// 가입채널코드
		String txt30 = StringUtil.rightPad(vo.getCanRsltCd(), 5, " ", "UTF8");// 해지사유
		
		sbuf.append(txt1);
		sbuf.append(txt2);
		sbuf.append(txt3);
		sbuf.append(txt4);
		sbuf.append(txt5);
		sbuf.append(txt6);
		sbuf.append(txt7);
		sbuf.append(txt8);
		sbuf.append(txt9);
		sbuf.append(txt10);
		sbuf.append(txt11);
		sbuf.append(txt12);
		sbuf.append(txt13);
		sbuf.append(txt14);
		sbuf.append(txt15);
		sbuf.append(txt16);
		sbuf.append("  ");
		sbuf.append(txt17);
		sbuf.append(txt18);
		sbuf.append("  ");
		sbuf.append(txt19);
		sbuf.append("  ");
		sbuf.append(txt20);
		sbuf.append(txt21);
		sbuf.append(txt22);
		sbuf.append(txt23);
		sbuf.append(txt24);
		sbuf.append(txt25);
		sbuf.append(txt26);
		sbuf.append(txt27);
		sbuf.append(txt28);
		sbuf.append(txt29);
		sbuf.append(txt30);

		LOGGER.debug("======문자열 생성 시작 sbuf.toString()======" + sbuf.toString());
		return sbuf.toString();
	}
	
	/**
	 * 보상서비스 청구/수납자료 데이터 생성
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setRwdBillPymnList() throws MvnoServiceException {
		
		//보상서비스 청구/수납자료 생성
		rwdMapper.insertRwdBillPymn();
		
		// 연동 파일 생성
		this.getBillPymn();
	}
	
	/**
	 * 청구/수납 자료 연동 대상자 조회
	 */
	@Transactional(rollbackFor=Exception.class)
	public int getBillPymn() throws MvnoServiceException {
		
		String[] errParam = new String[2];
		errParam[0] = PointConstants.STR_WINIA;
		errParam[1] = PointConstants.WINIA_003;
		
		List<RwdVO> list = rwdMapper.getBillPymn();
		try {
			this.saveSendFile(list, PointConstants.STR_WINIA, PointConstants.WINIA_003, PointConstants.SEED_KEY_WINIA);
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("EPTNR1022", errParam, e);
		}
		
		return list.size();
	}
	/**
	 * 문자열 생성
	 */
	private String getStringBill(RwdVO vo, String seedKey, String gubun) { 
		
		LOGGER.debug("======문자열 생성 시작======");
		StringBuffer sbuf = new StringBuffer();
		
		String txt1 = StringUtil.rightPad(vo.getRwdSeq(), 15, " ", "UTF8");// 보상서비스 시퀀스
		String txt2 = StringUtil.rightPad(vo.getBillYm(), 8, " ", "UTF8");// 청구월
		String txt3 = StringUtil.rightPad(vo.getBlpymDate(), 10, " ", "UTF8");// 실수납일자
		String txt4 = StringUtil.rightPad(vo.getContractNum(), 11, " ", "UTF8");// 계약번호
		String txt5 = StringUtil.rightPad(vo.getOpenDt(), 10, " ", "UTF8");// 서비스가입시 단말기 개통일자
		String txt6 = StringUtil.rightPad(vo.getStrtDttm(), 10, " ", "UTF8");// 보상서비스 시작일자
		String txt7 = StringUtil.rightPad(vo.getEndDttm(), 10, " ", "UTF8");// 보상서비스 종료일자
		String txt8 = StringUtil.rightPad(vo.getBillAcntNo(), 15, " ", "UTF8");// 청구계정번호
		String txt9 = StringUtil.rightPad(vo.getBillItemCd(), 12, " ", "UTF8");// 청구항목코드
		String txt10 = StringUtil.rightPad(vo.getItemCdNm(), 100, " ", "UTF8");// 청구항목코드
		String txt11 = StringUtil.rightPad(vo.getPymnCd(), 10, " ", "UTF8");// 수납코드
		String txt12 = StringUtil.rightPad(vo.getPymnAmnt(), 10, " ", "UTF8");// 수납금액
		String txt13 = StringUtil.rightPad(vo.getVatAmnt(), 10, " ", "UTF8");// 단말기모델명
		String txt14 = StringUtil.rightPad(vo.getPymnMthdCd(), 4, " ", "UTF8");// 납부방법코드
		String txt15 = StringUtil.rightPad(vo.getPymnMthdNm(), 50, " ", "UTF8");// 납부방법명
		String txt16 = StringUtil.rightPad(vo.getAdjsRsnCd(), 10, " ", "UTF8");// 조정사유코드
		String txt17 = StringUtil.rightPad(vo.getAdjsRsnNm(), 50, " ", "UTF8");// 조정사유명
		
		sbuf.append(txt1);
		sbuf.append(txt2);
		sbuf.append(txt3);
		sbuf.append(txt4);
		sbuf.append(txt5);
		sbuf.append(txt6);
		sbuf.append(txt7);
		sbuf.append(txt8);
		sbuf.append(txt9);
		sbuf.append(txt10);
		sbuf.append(txt11);
		sbuf.append(txt12);
		sbuf.append(txt13);
		sbuf.append(txt14);
		sbuf.append(txt15);
		sbuf.append(txt16);
		sbuf.append(txt17);

		LOGGER.debug("======문자열 생성 시작 sbuf.toString()======" + sbuf.toString());
		return sbuf.toString();
	}
	
	/** 보상지급현황 결과 정보 파일 읽어서 처리 */
	@Transactional(rollbackFor=Exception.class)
	public void rwdPayReadFile(String fileName, String gubun) throws MvnoServiceException {
		
		LOGGER.debug("==========보상지급현황 결과 읽기 시작==========" );
		
		int cnt = 0;
		PointFileVO pointFileVO = new PointFileVO();
		 
		pointFileVO.setPartnerId(PointConstants.STR_WINIA);
		pointFileVO.setFileNm(fileName);
		 
		List<?> list = ptnrMapper.getPointFileReadList(pointFileVO);
		  
		ObjectMapper op = new ObjectMapper();
		  
		String[] errParam = new String[1];
		errParam[0] = pointFileVO.getPartnerId();
		  
		// 읽을 파일이 없음
		if(list.isEmpty()) {
			throw new MvnoServiceException("EPTNR1008", errParam);
		}
  
		List<PointFileVO> fileList = new ArrayList<PointFileVO>();
  
		for (Object fromValue : list) {
			PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
      
			File f = new File(vo.getLocalDownDir() + vo.getFileNm());
 
			// 파일이 존재하는지 확인
			if(f.isFile()) {
				fileList.add(vo);
			}
		}
  
		String downloadFileName = "";
		
		// 파일 읽기
		try {
			for (PointFileVO fVo : fileList) {
				
				downloadFileName = fVo.getLocalDownDir() + fVo.getFileNm();
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(downloadFileName), "EUC-KR"));
					String s;
					
					while ((s = in.readLine()) != null) {
						if (s.length() > 40) {

							RwdCmpnVO vo = new RwdCmpnVO();
							vo.setRwdSeq(s.substring(0, 14).trim()); //보상서비스 시퀀스 15자리
							vo.setRwdCmpnSeq(s.substring(15, 29).trim()); //권리실행 접수번호 15자리
							vo.setPayDttm(s.substring(30, 39).trim()); //보상금 지급 완료일자 10자리
							vo.setRealCmpnAmt(s.substring(40, 49).trim()); //실보상금액 10자리

							// 보상지급현황 등록
							rwdMapper.insertRwdCmpnPay(vo);
							
							// 보상지급현황 원장 테이블 조회 후 insert or update 처리
							int returnCnt = rwdMapper.selectRwdCmpnRes(vo);
							
							if(returnCnt > 0) {
								//update
								rwdMapper.uptRwdCmpnPay(vo);
							} else {
								//insert
								rwdMapper.intRwdCmpnPay(vo);
							}
							
						}
						cnt++;
					}
   
					fVo.setSendResult("S");
					ptnrMapper.updatePointFileDown(fVo);
					in.close();
				} catch (Exception e1) {
					LOGGER.error("파일읽기에 실패했습니다. 에러 = [{}]", e1);
					LOGGER.error("파일읽기에 실패했습니다. 파일명 = [{}]", downloadFileName);
					continue;
				}
			}
			
		} catch (Exception e) {
		     throw new MvnoServiceException("EPTNR1011", errParam, e);
		}
		
		LOGGER.info("보상처리현황 등록 건수 :"+cnt);
	}
}
