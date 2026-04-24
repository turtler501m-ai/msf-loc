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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.IntmInsrMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.PartnerPointSettleMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.IntmInsrVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.MplatformOpenService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.mapper.VacCommonMapper;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsSendVO;
import com.ktis.msp.batch.manager.vo.VacCommonVO;
import com.ktis.msp.batch.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 단말보험 가입자연동
 * ---------- ------- ----------------------------------------------
 * 
 */
@Service
public class IntmInsrService extends BaseService {
	
	@Autowired
	private IntmInsrMapper intmInsrMapper;
	
	@Autowired
	private PartnerPointSettleMapper ptnrMapper;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	@Autowired
	private VacCommonMapper vacCommonMapper;
	
	@Autowired
	private PartnerCryptoService cryptoService;
	
	@Autowired
	private EgovPropertyService propertiesService;
	
	/**
	 * 단말보험 오더 생성
	 */
//	@Transactional(rollbackFor=Exception.class)
//	public int setIntmInsrOrderList() throws MvnoServiceException {
//		// 단말보험 부가서비스 가입자 조회
//		List<IntmInsrVO> list = intmInsrMapper.getIntmInsrTrgtList();
//		
//		// 전체건수
//		int allCnt = list.size();
//		// 가입대상
//		int insCnt = 0;
//		// 해지대상
//		int delCnt = 0;
//		
//		for(IntmInsrVO vo : list) {
//			
//			IntmInsrVO orderVO = intmInsrMapper.getIntmInsrOrder(vo);
//			// 동일한 오더 정보가 존재하는 지 확인하여 없는 경우만 처리
//			if (orderVO == null) {
//
//				if("I".equals(vo.getWorkCd())) {
//					// 가입처리
//					insCnt += this.insertIntmInsrOrder(vo);
//				}
//				else if("C".equals(vo.getWorkCd())){
//					// 해지처리
//					delCnt += this.deleteIntmInsrOrder(vo);
//				}
//			}
//			
//		}
//		
//		LOGGER.debug("전체건수=" + allCnt);
//		LOGGER.debug("가입대상=" + insCnt);
//		LOGGER.debug("삭제대상=" + delCnt);
//		
//		return allCnt;
//	}
	
	/**
	 * 단말보험 오더 생성
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setIntmInsrOrderListDaily() throws MvnoServiceException {
		
		// 단말보험 부가서비스 처리 대상자 생성
		intmInsrMapper.insertIntmInsrReady();
		
		List<IntmInsrVO> list = intmInsrMapper.getIntmInsrTrgtListDaily();
		
		// 전체건수
		int allCnt = list.size();
		// 가입대상
		int insCnt = 0;
		// 해지대상
		int delCnt = 0;
		
		for(IntmInsrVO vo : list) {
			
			IntmInsrVO orderVO = intmInsrMapper.getIntmInsrOrder(vo);
			// 동일한 오더 정보가 존재하는 지 확인하여 없는 경우만 처리
			if (orderVO == null) {

				if("I".equals(vo.getWorkCd())) {
					// 가입처리
					insCnt += this.insertIntmInsrOrder(vo);
				}
				else if("C".equals(vo.getWorkCd())){
					// 해지처리
					delCnt += this.deleteIntmInsrOrder(vo);
				}
			}
		}
		
		LOGGER.info("전체건수:"+allCnt+" (신청:"+insCnt+", 삭제:"+delCnt+")");
	}

	@Transactional(rollbackFor=Exception.class)
	public int insertIntmInsrOrder(IntmInsrVO vo) throws MvnoServiceException {
		int i = 0;
		
		vo.setInsrStatCd("1");
		
		// 단말구매 온라인 채널인 경우도 검증 하는걸로 [2019.04.08 15:30] 
//		if("MM".equals(vo.getReqBuyType()) && "2".equals(vo.getChnCd())) {
//			// 단말구매 온라인 채널인 경우 승인 상태 
//			vo.setIfTrgtCd("Y");		// 연동대상
//		} else {
//			vo.setVrfyRsltCd("REG");	// RCP0200
//			vo.setIfTrgtCd("N");		// 연동비대상(검증필요)
//		}
		
		vo.setVrfyRsltCd("REG");	// RCP0200
		vo.setIfTrgtCd("N");		// 연동비대상(검증필요)
		
		// 단말구매인 경우 단말정보 조회
		if("MM".equals(vo.getReqBuyType())) {
			IntmInsrVO mdlVO = null;
			if(vo.getDvcChgDt() != null && !"".equals(vo.getDvcChgDt())) {
				// 기기변경 정보 조회
				mdlVO = intmInsrMapper.getDvcChgModelInfo(vo);
			} else {
				// 개통정보 조회
				mdlVO = intmInsrMapper.getOpenModelInfo(vo);
			}
			vo.setModelNm(mdlVO.getModelNm());
			vo.setModelId(mdlVO.getModelId());
			vo.setIntmSrlNo(mdlVO.getIntmSrlNo());
		}
		
		if("02".equals(vo.getInsrTypeCd())) {
			vo.setImgChkYn("N");	// USIM형 요금제인 경우 단말 이미지 체크필요
		}

		if(vo.getDvcChgDt() == null || "".equals(vo.getDvcChgDt())) {
			vo.setInsrEvntCd("1");	// 신규개통
		} else {
			vo.setInsrEvntCd("2");	// 기기변경
		}
		
		i = intmInsrMapper.insertIntmInsrOrder(vo);
		
		// 단말보험대상자정보 가입처리완료
	    intmInsrMapper.updateIntmInsrReady(vo);
		
		return i;
	}
	
	/**
	 * 보험가입자 정보 전송 전 해지
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteIntmInsrOrder(IntmInsrVO vo) throws MvnoServiceException {
		int i = 0;
				
		if(intmInsrMapper.getIntmInsrMember(vo) == null) {
			// 보험가입자 원부 미존재인 경우 연동대상 테이블에 존재
			i = intmInsrMapper.deleteIntmInsrOrder(vo);
		}
		
		// 단말보험대상자정보 해지처리완료
		intmInsrMapper.updateIntmInsrReady(vo);
		
		return i;
	}
	
	
	/**
	 * 보험가입자 전송 대상자 조회
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setIntmInsrMemberList() throws MvnoServiceException {
				
		int cnt = 0;
		//단말보험 해지 전송 대상자 조회
		cnt += this.deleteIntmInsrMember();
		
		//개인정보변경 대상자 조회
		cnt += this.changeIntmInsrMember();
		
		//보험가입자 조회
		cnt += this.insertIntmInsrMember();
		
		LOGGER.info("가입자정보 연동건수:" + cnt);
		
		//연동 파일 생성
		this.getIntmInsrIfOut();
		//if (this.getIntmInsrIfOut() != cnt) throw new MvnoServiceException("EMSP1001", "건수상이");
		
	}
	
	/**
	 * 단말보험 부가서비스 해지 대상자 조회
	 */
	@Transactional(rollbackFor=Exception.class)
	public void expireIntmInsrOrder() throws MvnoServiceException {	
		//단말보험 부가서비스 처리 대상자 기간만료(+3일) 해지 처리
		intmInsrMapper.insertIntmInsrCanInfoReady();
		intmInsrMapper.updateIntmInsrReadyExp();
		
		//검증기간(+6일) 초과 부가서비스 해지 대상자 등록
		intmInsrMapper.insertIntmInsrCanInfoOvr();
		//검증기간(+6일) 초과 만료 처리
		intmInsrMapper.expireIntmInsrOrder();
		//자기부담금 미만 잔액한도 해지 처리
		intmInsrMapper.insertIntmInsrCanInfoLtl();
		
		
		// 단말보험 부가서비스 해지자조회
		List<IntmInsrVO> list = intmInsrMapper.getIntmInsrCanInfoList();
		//mplatform 전송 및 SMS전송 예약
		this.canIntmInsrAddSvc(list);
	}
	
	/**
	 * 단말보험 기간만료 해지 대상자 조회
	 */
	@Transactional(rollbackFor=Exception.class)
	public void expireIntmInsrMember() throws MvnoServiceException {		
		// 단말보험 부가서비스 해지자조회
		List<IntmInsrVO> list = intmInsrMapper.getExpireIntmInsrMemberList();
		
		for(IntmInsrVO vo : list) {
			vo.setCanRsltCd("EXP");
			this.canInfoSendSms(vo);
			intmInsrMapper.deleteIntmInsrMember(vo);
		}
		
	}

	/**
	 * 보험가입자 해지 대상자 조회
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteIntmInsrMember() throws MvnoServiceException {
		List<IntmInsrVO> delList = intmInsrMapper.getIntmInsrDeleteTrgtList();
		
		for(IntmInsrVO vo : delList) {
			intmInsrMapper.deleteIntmInsrMember(vo);
			vo.setInsrStatCd("2");
			intmInsrMapper.insertIntmInsrIfOut(vo);
		}
		
		LOGGER.debug("해지자건수=" + delList.size());
		
		return delList.size();
	}
	
	/**
	 * 보험가입자 개인정보변경 대상자 조회
	 */
	@Transactional(rollbackFor=Exception.class)
	public int changeIntmInsrMember() throws MvnoServiceException {
		int mcnCnt = 0;
		int chgCnt = 0;
		
		//명의변경 대상 조회
		List<IntmInsrVO> mcnList = intmInsrMapper.getIntmInsrMcnTrgtList();
		mcnCnt = mcnList.size();
		
		for(IntmInsrVO vo : mcnList) {
			intmInsrMapper.updateIntmInsrMember(vo);
			vo.setInsrStatCd("7");
			intmInsrMapper.insertIntmInsrIfOut(vo);
		}
		
		LOGGER.debug("명의변경 건수=" + mcnCnt);
		
		
		//개인정보 수정 대상 조회
		List<IntmInsrVO> chgList = intmInsrMapper.getIntmInsrChgTrgtList();
		
		for(IntmInsrVO vo : chgList) {
			boolean dup = false;
			for(IntmInsrVO mcnVo : mcnList) {
				if (mcnVo.getInsrMemberNum().equals(vo.getInsrMemberNum())) {
					dup = true;
					break;
				}
			}
			
			if (!dup) {
				intmInsrMapper.updateIntmInsrMember(vo);
				vo.setInsrStatCd("3");
				intmInsrMapper.insertIntmInsrIfOut(vo);
				chgCnt++;
			}
		}
		LOGGER.debug("정보수정 건수=" + chgCnt);
		LOGGER.debug("변경건수=" + (mcnCnt + chgCnt));
				
		return mcnCnt + chgCnt;
	}
	
	/**
	 * 보험가입자 조회
	 */
	@Transactional(rollbackFor=Exception.class)
	public int insertIntmInsrMember() throws MvnoServiceException {
		
		List<IntmInsrVO> list = intmInsrMapper.getIntmInsrJoinTrgtList();
		
		for(IntmInsrVO vo : list) {
			vo.setInsrMemberNum(intmInsrMapper.getIntmInsrMemberSeq());
			 
			if (intmInsrMapper.insertIntmInsrMember(vo) <= 0) 
				throw new MvnoServiceException("EMSP1002", "INTM_INSR_MEMBER");
			
			if (intmInsrMapper.insertIntmInsrIfOut(vo) <= 0)
				 throw new MvnoServiceException("EMSP1002", "INTM_INSR_IF_OUT");
			
			intmInsrMapper.completeIntmInsrOrder(vo);
						
			if (vo.getTemplateId() != null && !"".equals(vo.getTemplateId())) {
				this.welcomeSendSms(vo);
			}
			
		}
		
		LOGGER.debug("가입자 건수=" + list.size());
		
		return list.size();
		
	}

	/**
	 * 연동 대상자 조회
	 */
	@Transactional(rollbackFor=Exception.class)
	public int getIntmInsrIfOut() throws MvnoServiceException {
		
		String[] errParam = new String[2];
		errParam[0] = PointConstants.STR_MOBINS;
		errParam[1] = PointConstants.MOBINS_001;
		
		List<IntmInsrVO> list = intmInsrMapper.getIntmInsrIfOut();
		
		try {
			this.saveSendFile(list, PointConstants.STR_MOBINS, PointConstants.MOBINS_001, PointConstants.SEED_KEY_MOBINS);
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("EPTNR1022", errParam, e);
		}
		
		List<IntmInsrVO> dbList = intmInsrMapper.getIntmInsrIfOutDB();
		
		try {
			errParam[0] = PointConstants.STR_DBINS;
			errParam[1] = PointConstants.DBINS_001;
			this.saveSendFile(dbList, PointConstants.STR_DBINS, PointConstants.DBINS_001, PointConstants.SEED_KEY_DBINS);
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("EPTNR1022", errParam, e);
		}
		
		for(IntmInsrVO vo : list) {
			intmInsrMapper.updateIntmInsrIfOut(vo);
		}
		
		LOGGER.debug("연동 건수=" + list.size());
		
		return list.size();
	}
	
	/**
	 * 가입신청자 및 부적격 연동 파일 생성 대상 조회
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setIntmInsrOrderList() throws MvnoServiceException {
		// 가입정보 전송대상
		int orderCnt = 0;
		// 부적격정보 전송대상
//		int nokCnt = 0;
		
		String[] errParam = new String[2];
				
		try {
			errParam[0] = PointConstants.STR_MOBINS;
			errParam[1] = PointConstants.MOBINS_004;
			
			List<IntmInsrVO> orderList = intmInsrMapper.getIntmInsrOrderList();
			orderCnt = orderList.size();
			
			this.saveSendFile(orderList, PointConstants.STR_MOBINS, PointConstants.MOBINS_004, PointConstants.SEED_KEY_MOBINS);
			
//부적격 리스트는 연동 제외 2019-07-04
//			errParam[1] = PointConstants.MOBINS_005;
//			
//			List<IntmInsrVO> failList = intmInsrMapper.getIntmInsrOrderFailList();
//			nokCnt = failList.size();
//			
//			this.saveSendFile(failList, PointConstants.STR_MOBINS, PointConstants.MOBINS_005, PointConstants.SEED_KEY_MOBINS);
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("EPTNR1022", errParam, e);
		}
		
//		LOGGER.info("신청전송건수:"+orderCnt+", 부적격전송건수:"+nokCnt);
		LOGGER.info("신청전송건수:"+orderCnt);
	}
	
	public void saveSendFile(List<?> clist, String pId, String ifNo, String seedKey) throws MvnoServiceException {
		
		PointFileVO fileVo = this.regMovinsFileInfo(pId, ifNo, "req");
		ObjectMapper op = new ObjectMapper();
		
		String str = "";
		
		try {
			LOGGER.debug("보험사 연동 파일 쓰기 시작..................................");
			
			File desti = new File(fileVo.getLocalUpDir());
			
			// 디렉토리가 없다면 생성
			if (!desti.exists()) {
				desti.mkdirs();
			}
			
			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileVo.getLocalUpDir() + fileVo.getFileNm()), "EUC-KR"));
			
			for (Object fromValue : clist) {
				IntmInsrVO vo = op.convertValue(fromValue, IntmInsrVO.class);
				
				// 문자열만들기
				str = getStringReq(vo, seedKey, ifNo);
				
				fw.write(str);
				fw.newLine();
			}
			
			fw.flush();
			
			//객체 닫기
			fw.close(); 
			
		} catch (Exception e) {
			throw new MvnoServiceException("EPTNR1013", e);
		}
		
		LOGGER.debug("보험사 연동 파일 쓰기 끝..................................");
	}
	
	@Transactional(rollbackFor=Exception.class)
	public PointFileVO regMovinsFileInfo(String pId, String ifNo, String gubun) throws MvnoServiceException {
      
		LOGGER.debug("제휴사 파일 정보 생성 시작");
  
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
		 
		LOGGER.debug("제휴사 파일 정보 생성 끝");
		  
		return fileVo;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void readFile(String fileName, String gubun) throws MvnoServiceException {
		
		int cnt = 0;
		PointFileVO pointFileVO = new PointFileVO();
		 
		pointFileVO.setPartnerId(PointConstants.STR_MOBINS);
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
						
						String str[] = s.split("\\^", -1);
												
						IntmInsrVO vo = new IntmInsrVO();
						
						if(gubun.equals("2")) {
							vo.setIfCd("A");
							vo.setInsrMngmNo(str[0]);
							vo.setContractNum(str[1]);
							vo.setInsrCmpnCd(str[2]);
							vo.setAcdntNo(str[3]);
							vo.setCustNm(cryptoService.decrypt(str[4], PointConstants.SEED_KEY_MOBINS));
							vo.setCtn(cryptoService.decrypt(str[5], PointConstants.SEED_KEY_MOBINS));
							vo.setOpenDt(str[6]);
							vo.setJoinDt(str[7]);
							vo.setInsrProdCd(str[8]);
							vo.setAcdntRegDt(str[9]);
							vo.setAcdntDt(str[10]);
							vo.setCmpnCnfmDt(str[11]);
							vo.setModelNm(str[12]);
							vo.setIntmSrlNo(str[13]);
							vo.setOutUnitPric(str[14]);
							vo.setCmpnLmtAmt(str[15]);
							vo.setRmnCmpnAmt(str[16]);
							vo.setCustBrdnAmt(str[17]);
							vo.setOverAmt(str[18]);
							vo.setUsimAmt(str[19]);
							vo.setCmpnModelNm(str[20]);
							vo.setCmpnModelColor(str[21]);
							vo.setCmpnCtn(cryptoService.decrypt(str[22], PointConstants.SEED_KEY_MOBINS));
							vo.setPayType(str[23]);
						} else if(gubun.equals("3")) {
							vo.setIfCd("P");
							vo.setInsrMngmNo(str[0]);
							vo.setContractNum(str[1]);
							vo.setInsrCmpnCd(str[2]);
							vo.setAcdntNo(str[3]);
							vo.setCustNm(cryptoService.decrypt(str[4], PointConstants.SEED_KEY_MOBINS));
							vo.setCtn(cryptoService.decrypt(str[5], PointConstants.SEED_KEY_MOBINS));
							vo.setOpenDt(str[6]);
							vo.setJoinDt(str[7]);
							vo.setInsrProdCd(str[8]);
							vo.setAcdntRegDt(str[9]);
							vo.setAcdntDt(str[10]);
							vo.setCmpnCnfmDt(str[11]);
							vo.setModelNm(str[12]);
							vo.setIntmSrlNo(str[13]);
							vo.setOutUnitPric(str[14]);
							vo.setAcdntTp(str[15]);
							vo.setCmpnLmtAmt(str[16]);
							vo.setCmpnAcmlAmt(str[17]);
							vo.setRmnCmpnAmt(str[18]);
							vo.setRcptAmt(str[19]);
							vo.setCustBrdnAmt(str[20]);
							vo.setRealCmpnAmt(str[21]);
							vo.setRmnCmpnLmtAmt(str[22]);
							if (vo.getRmnCmpnLmtAmt() != null && Integer.parseInt(vo.getRmnCmpnLmtAmt()) <= 30000) vo.setLtlYn("Y");
							vo.setBankNm(cryptoService.decrypt(str[23], PointConstants.SEED_KEY_MOBINS));
							vo.setAcntNo(cryptoService.decrypt(str[24], PointConstants.SEED_KEY_MOBINS));
							vo.setDpstNm(cryptoService.decrypt(str[25], PointConstants.SEED_KEY_MOBINS));
						}
						vo.setInsrCmpnNum(intmInsrMapper.getIntmInsrCmpnSeq());
						intmInsrMapper.insertIntmInsrCmpnMstIf(vo);
						intmInsrMapper.updateIntmInsrCmpnMember(vo);
						
						if (gubun.equals("2")) this.totalLossProc(vo);
						
						cnt++;
					}
   
					fVo.setSendResult("S");
					ptnrMapper.updatePointFileDown(fVo);
					in.close();
				} catch (Exception e1) {
					LOGGER.error("파일읽기에 실패했습니다. 파일명 = [{}]", downloadFileName);
					continue;
				}
			}
			
		} catch (Exception e) {
		     throw new MvnoServiceException("EPTNR1011", errParam, e);
		}
		
		LOGGER.info("보상정보 연동건수:"+cnt);
	}
	
	private String getStringReq(IntmInsrVO vo, String seedKey, String gubun) { 
		
		StringBuffer sbuf = new StringBuffer();
		
		if (gubun.equals(PointConstants.MOBINS_001) || gubun.equals(PointConstants.DBINS_001)) {
			// 처리일자
			String txt1 = StringUtil.NVL(vo.getOpenDt(), "");
			// 보험가입일자
			String txt2 = StringUtil.NVL(vo.getStrtDttm(), "");
			// 보험상태
			String txt3 = StringUtil.NVL(vo.getInsrStatCd(), "");
			// 전화번호
			String txt4 = cryptoService.encrypt(vo.getCtn(), seedKey);
			// 고객명
			String txt5 = cryptoService.encrypt(vo.getCustNm(), seedKey);
			// 생년월일
			String txt6 = cryptoService.encrypt(vo.getBirthDt(), seedKey);
			// 대리인
			String txt7 = (vo.getMinorAgentName() != null && !"".equals(vo.getMinorAgentName())) ? cryptoService.encrypt(vo.getMinorAgentName(), seedKey) : "";
			// 단말기모델명
			String txt8 = StringUtil.NVL(vo.getModelNm(), "");
			// 단말기일련번호
			String txt9 = StringUtil.NVL(vo.getIntmSrlNo(), "");
			// 주소
			String txt10 = StringUtil.NVL(vo.getBanAddr(), "");
			// 보험구분
			String txt11 = StringUtil.NVL(vo.getInsrProdCd(), "");
			// 보험사구분
			String txt12 = StringUtil.NVL(vo.getInsrCmpnCd(), "");
			// 신규/기변
			String txt13 = StringUtil.NVL(vo.getInsrEvntCd(), "");
			// 계약번호
			String txt14 = StringUtil.NVL(vo.getContractNum(), "");
			// 중고폰여부
			String txt15 = StringUtil.NVL(vo.getOldYn(), "");
			// 단말기출시일
			String txt16 = StringUtil.NVL(vo.getPrdtLnchDt(), "");
			// 단말기출고가
			String txt17 = StringUtil.NVL(vo.getOutUnitPric(), "");
			// 보험상품확인인증
			String txt18 = StringUtil.NVL(vo.getClauseFlag(), "");
			// 가입채널
			String txt19 = StringUtil.NVL(vo.getChnCd(), "");
			// 고객구분
			String txt20 = StringUtil.NVL(vo.getCustType(), "");

			sbuf.append(txt1);
			sbuf.append("^").append(txt2);
			sbuf.append("^").append(txt3);
			sbuf.append("^").append(txt4);
			sbuf.append("^").append(txt5);
			sbuf.append("^").append(txt6);
			sbuf.append("^").append(txt7);
			sbuf.append("^").append(txt8);
			sbuf.append("^").append(txt9);
			sbuf.append("^").append(txt10);
			sbuf.append("^").append(txt11);
			sbuf.append("^").append(txt12);
			sbuf.append("^").append(txt13);
			sbuf.append("^").append(txt14);
			sbuf.append("^").append(txt15);
			sbuf.append("^").append(txt16);
			sbuf.append("^").append(txt17);
			sbuf.append("^").append(txt18);
			sbuf.append("^").append(txt19);
			sbuf.append("^").append(txt20);
			
		} else if (gubun.equals(PointConstants.MOBINS_004)) {
			// 처리일자
			String txt1 = StringUtil.NVL(vo.getOpenDt(), "");
			// 보험가입일자
			String txt2 = StringUtil.NVL(vo.getStrtDttm(), "");
			// 전화번호
			String txt3 = cryptoService.encrypt(vo.getCtn(), seedKey);
			// 고객명
			String txt4 = cryptoService.encrypt(vo.getCustNm(), seedKey);
			// 생년월일
			String txt5 = cryptoService.encrypt(vo.getBirthDt(), seedKey);
			// 대리인
			String txt6 = (vo.getMinorAgentName() != null && !"".equals(vo.getMinorAgentName())) ? cryptoService.encrypt(vo.getMinorAgentName(), seedKey) : "";
			// 단말기모델명
			String txt7 = StringUtil.NVL(vo.getModelNm(), "");
			// 단말기일련번호
			String txt8 = StringUtil.NVL(vo.getIntmSrlNo(), "");
			// 주소
			String txt9 = StringUtil.NVL(vo.getBanAddr(), "");
			// 보험구분
			String txt10 = StringUtil.NVL(vo.getInsrProdCd(), "");
			// 보험사구분
			String txt11 = StringUtil.NVL(vo.getInsrCmpnCd(), "");
			// 신규/기변
			String txt12 = StringUtil.NVL(vo.getInsrEvntCd(), "");
			// 계약번호
			String txt13 = StringUtil.NVL(vo.getContractNum(), "");
			// 가입채널
			String txt14 = StringUtil.NVL(vo.getChnCd(), "");
				
			sbuf.append(txt1);
			sbuf.append("^").append(txt2);
			sbuf.append("^").append(txt3);
			sbuf.append("^").append(txt4);
			sbuf.append("^").append(txt5);
			sbuf.append("^").append(txt6);
			sbuf.append("^").append(txt7);
			sbuf.append("^").append(txt8);
			sbuf.append("^").append(txt9);
			sbuf.append("^").append(txt10);
			sbuf.append("^").append(txt11);
			sbuf.append("^").append(txt12);
			sbuf.append("^").append(txt13);
			sbuf.append("^").append(txt14);
			
		} else if (gubun.equals(PointConstants.MOBINS_005)) {
			// 전화번호
			String txt1 = cryptoService.encrypt(vo.getCtn(), seedKey);
			// 고객명
			String txt2 = cryptoService.encrypt(vo.getCustNm(), seedKey);
			// 생년월일
			String txt3 = cryptoService.encrypt(vo.getBirthDt(), seedKey);
			// 대리인
			String txt4 = (vo.getMinorAgentName() != null && !"".equals(vo.getMinorAgentName())) ? cryptoService.encrypt(vo.getMinorAgentName(), seedKey) : "";
			// 계약번호
			String txt5 = StringUtil.NVL(vo.getContractNum(), "");
			// 부적격처리시간
			String txt6 = StringUtil.NVL(vo.getFailDt(), "");
			// 부적격사유
			String txt7 = StringUtil.NVL(vo.getCanRsltCd(), "");
				
			sbuf.append(txt1);
			sbuf.append("^").append(txt2);
			sbuf.append("^").append(txt3);
			sbuf.append("^").append(txt4);
			sbuf.append("^").append(txt5);
			sbuf.append("^").append(txt6);
			sbuf.append("^").append(txt7);
			
		}
				
		return sbuf.toString();
	}
	
	/**
	 * 단말 보험 부가서비스 해지처리
	 * @param searchVO
	 * @return
	 */
	public int canIntmInsrAddSvc(List<IntmInsrVO> list) throws MvnoServiceException {
		int procCnt = 0;
				
		try{
			
			MplatformOpenService mp = new MplatformOpenService();
			
			for(IntmInsrVO vo : list) {
				HashMap<String, String> paramMap = new HashMap<String,String>();
				paramMap.put("mdlInd",		"BAT");
				paramMap.put("ncn",			vo.getNcn());
				paramMap.put("ctn",			vo.getCtn());
				paramMap.put("custId",		vo.getCustId());
				paramMap.put("appEventCd",	"X38");
				paramMap.put("soc",			vo.getInsrProdCd());
				
				// 셀프케어 호출				
				paramMap = mp.SelfCareCallService(paramMap, propertiesService.getString("mplatform.selfcare.url"));
				
				if("SUCCESS".equals((String) paramMap.get("result"))){
					if("N".equals((String) paramMap.get("resultType"))){
						vo.setRsltCd("0000");
						//SMS 전송
						this.canInfoSendSms(vo);
					}else{
						vo.setRsltCd("9999");
						vo.setRsltMsg((String) paramMap.get("resultMsg"));
					}
				}else{
					vo.setRsltCd("9999");
					vo.setRsltMsg((String) paramMap.get("result"));
				}
				
				intmInsrMapper.completeIntmInsrCanInfo(vo);
				
				// 3초간 대기
				mp.sleep(3);
				
				procCnt++;
			}
		}catch(Exception e){
			throw new MvnoServiceException("EMSP1007", e);
		}
		
		return procCnt;
	}
	
	public void canInfoSendSms(IntmInsrVO vo) {
		
		Calendar cal = Calendar.getInstance();
		String templateId = null;
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int yesterday = cal.get(Calendar.DATE)-1;
		
		//SMS 전송
		KtSmsCommonVO smsVO = null;
		
		//부적격 해지, 등록기간 만료 해지
		if(vo.getCanRsltCd().equals("UNQ") || vo.getCanRsltCd().equals("OVR") || vo.getCanRsltCd().equals("RDY")) {
			templateId = "87";
			smsVO = smsCommonMapper.getKtTemplateText(templateId);

			smsVO.setMessage((smsVO.getTemplateText())
									.replaceAll(Pattern.quote("#{insrProdNm}"), vo.getInsrProdNm())
									.replaceAll(Pattern.quote("#{year}"), String.valueOf(year))
									.replaceAll(Pattern.quote("#{month}"), String.valueOf(month))
									.replaceAll(Pattern.quote("#{day}"), String.valueOf(yesterday)));
			
			smsVO.setRcptData(vo.getCtn());
			smsVO.setTemplateId(templateId);
		//최소한도잔액 해지
		} else if (vo.getCanRsltCd().equals("LTL")) {
			templateId = "88";
			smsVO = smsCommonMapper.getKtTemplateText(templateId);

			smsVO.setMessage((smsVO.getTemplateText())
									.replaceAll(Pattern.quote("#{insrProdNm}"), vo.getInsrProdNm())
									.replaceAll(Pattern.quote("#{year}"), String.valueOf(year))
									.replaceAll(Pattern.quote("#{month}"), String.valueOf(month))
									.replaceAll(Pattern.quote("#{day}"), String.valueOf(yesterday)));
			
			smsVO.setRcptData(vo.getCtn());
			smsVO.setTemplateId(templateId);
		//보험기간만료 해지
		} else if (vo.getCanRsltCd().equals("EXP")) {
			templateId = "89";
			smsVO = smsCommonMapper.getKtTemplateText(templateId);

			smsVO.setMessage((smsVO.getTemplateText())
									.replaceAll(Pattern.quote("#{insrProdNm}"), vo.getInsrProdNm())
									.replaceAll(Pattern.quote("#{year}"), String.valueOf(year))
									.replaceAll(Pattern.quote("#{month}"), String.valueOf(month))
									.replaceAll(Pattern.quote("#{day}"), String.valueOf(yesterday)));
			
			smsVO.setRcptData(vo.getCtn());
			smsVO.setTemplateId(templateId);
		}
		
		smsVO.setReserved01("BATCH");
		smsVO.setReserved02(templateId);
		smsVO.setReserved03("IntmInsrCanSchedule");
		smsCommonMapper.insertKtMsgTmpQueue(smsVO);
		
		SmsSendVO sendVO = new SmsSendVO();
		sendVO.setTemplateId(templateId);
		sendVO.setMsgId(smsVO.getMsgId());
		sendVO.setSendReqDttm(smsVO.getScheduleTime());
		sendVO.setReqId(BatchConstants.BATCH_USER_ID);
		
		// SMS 발송내역 등록
		smsCommonMapper.insertKtSmsSendMst(sendVO);
		
	}
	
	public void totalLossProc(IntmInsrVO vo) throws MvnoServiceException{
		
		IntmInsrVO addVo = intmInsrMapper.getIntmInsrMember(vo);
		vo.setInsrMemberNum(addVo.getInsrMemberNum());
		vo.setInsrProdNm(addVo.getInsrProdNm());
		
		//고객부담금계산
		int payAmt = Integer.parseInt(vo.getCustBrdnAmt()) + Integer.parseInt(vo.getOverAmt()) + Integer.parseInt(vo.getUsimAmt());
		vo.setPayAmt(String.valueOf(payAmt));
		
		if (vo.getPayType().equals("A")) {
			//가상계좌번호가져오기
			VacCommonVO vacVo = new VacCommonVO();
			vacVo.setOpCode("REG");
			vacVo.setGubun("INSR");
			vacVo.setOrderKey(vo.getInsrCmpnNum());
			vacVo.setAmount(vo.getPayAmt());
			vacVo.setExpireDt("30");
			vacVo.setAdminId("BATCH");
			
			vacCommonMapper.callVacOnceInfo(vacVo);
			if (vacVo != null && vacVo.getRetCd().equals("0000")) {
				vo.setVacBankCd(vacVo.getVacBankCd());
				vo.setVacId(vacVo.getVacId());
				
				//계좌번호 및 금액 정보 SMS전송 예약
				String templateId = "92";
				
				Date toDay = new Date();
				String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATE_SHORT);
				
				//SMS 전송
				KtSmsCommonVO smsVO = null;
				
				smsVO = smsCommonMapper.getKtTemplateText(templateId);
				
				smsVO.setMessage((smsVO.getTemplateText())
						.replaceAll(Pattern.quote("#{insrProdNm}"), vo.getInsrProdNm())
						.replaceAll(Pattern.quote("#{vacBankNm}"), vacCommonMapper.selectVacBankNm(vo.getVacBankCd()))
						.replaceAll(Pattern.quote("#{vacId}"), vo.getVacId())
						.replaceAll(Pattern.quote("#{payAmt}"), vo.getPayAmt()));
				
				smsVO.setRcptData(vo.getCmpnCtn());
				smsVO.setScheduleTime(strToDay+"090000");
				smsVO.setReserved01("BATCH");
				smsVO.setReserved02(templateId);
				smsVO.setReserved03("IntmInsrCmpnSchedule");
				smsVO.setTemplateId(templateId);
				
				smsCommonMapper.insertKtMsgTmpQueue(smsVO);
				
				SmsSendVO sendVO = new SmsSendVO();
				//MSP_SMS_SEND_MST 테이블 UPDATE
				sendVO.setTemplateId(templateId);
				sendVO.setMsgId(smsVO.getMsgId());
				sendVO.setSendReqDttm(smsVO.getScheduleTime());
				sendVO.setReqId(BatchConstants.BATCH_USER_ID);
				
				// SMS 발송내역 등록
				smsCommonMapper.insertKtSmsSendMst(sendVO);
			} else {
				throw new MvnoServiceException("EVAC1001", vacVo.getRetMsg());
			}
		}
		
		intmInsrMapper.insertIntmInsrCmpnDtl(vo);
	}
	
	
	public void welcomeSendSms(IntmInsrVO vo) {
		
		String templateId = vo.getTemplateId();
		
		Date toDay = new Date();
		String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATE_SHORT);
		
		//SMS 전송
		KtSmsCommonVO smsVO = null;
		
		smsVO = smsCommonMapper.getKtTemplateText(templateId);
				
		smsVO.setRcptData(vo.getCtn());
		smsVO.setScheduleTime(strToDay+"090000");
		smsVO.setReserved01("BATCH");
		smsVO.setReserved02(templateId);
		smsVO.setReserved03("IntmInsrMemberSchedule");
		smsVO.setTemplateId(templateId);
		smsCommonMapper.insertKtMsgTmpQueue(smsVO);
		
		SmsSendVO sendVO = new SmsSendVO();
		sendVO.setTemplateId(templateId);
		sendVO.setMsgId(smsVO.getMsgId());
		sendVO.setSendReqDttm(smsVO.getScheduleTime());
		sendVO.setReqId(BatchConstants.BATCH_USER_ID);
		// SMS 발송내역 등록
		smsCommonMapper.insertKtSmsSendMst(sendVO);
		
	}

	/**
	 * 단말보험 부가서비스 해지 실패 대상자 임시 재처리
	 */
	@Transactional(rollbackFor=Exception.class)
	public void expireIntmInsrOrderRetry() throws MvnoServiceException {
		// 단말보험 부가서비스 해지자조회
		List<IntmInsrVO> list = intmInsrMapper.getIntmInsrCanRetryList();
		//mplatform 전송 및 SMS전송 예약
		this.canIntmInsrAddSvcRetry(list);
	}


	/**
	 * 단말 보험 부가서비스 해지 임시 재처리
	 * @param searchVO
	 * @return
	 */
	public int canIntmInsrAddSvcRetry(List<IntmInsrVO> list) throws MvnoServiceException {
		int procCnt = 0;

		try{

			MplatformOpenService mp = new MplatformOpenService();

			for(IntmInsrVO vo : list) {
				HashMap<String, String> paramMap = new HashMap<String,String>();
				paramMap.put("mdlInd",		"BAT");
				paramMap.put("ncn",			vo.getNcn());
				paramMap.put("ctn",			vo.getCtn());
				paramMap.put("custId",		vo.getCustId());
				paramMap.put("appEventCd",	"X38");
				paramMap.put("soc",			vo.getInsrProdCd());

				// 셀프케어 호출
				paramMap = mp.SelfCareCallService(paramMap, propertiesService.getString("mplatform.selfcare.url"));

				if("SUCCESS".equals((String) paramMap.get("result"))){
					if("N".equals((String) paramMap.get("resultType"))){
						vo.setRsltCd("0000");
						//SMS 전송
						this.canInfoSendSms(vo);
					}else{
						vo.setRsltCd("9999");
						vo.setRsltMsg((String) paramMap.get("resultMsg"));
					}
				}else{
					vo.setRsltCd("9999");
					vo.setRsltMsg((String) paramMap.get("result"));
				}

				intmInsrMapper.completeIntmInsrCanRetry(vo);

				// 3초간 대기
				mp.sleep(3);

				procCnt++;
			}
		}catch(Exception e){
			throw new MvnoServiceException("EMSP1007", e);
		}

		return procCnt;
	}

}
