package com.ktis.msp.batch.job.ptnr.ptnrmgmt.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.PartnerPointSettleMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.RwdMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.RwdVO;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsSendVO;
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
	
	@Autowired
	private RwdMapper rwdMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
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
			
			LOGGER.debug("업로드할 제휴사아이디 = [{}], 파일명 = [{}]", vo.getPartnerId(), vo.getFileNm());
			
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
	 * 위니아 전용 보상서비스 이미지파일 업로드
	 * @param 
	 * @return
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	public void uploadImageFile() throws MvnoServiceException {
		LOGGER.debug("===보상서비스 이미지파일 업로드 시작===");
		
		List<RwdVO> fileList = new ArrayList<RwdVO>();
		
		// 업로드할 파일 목록 가져오기
		List<?> list = rwdMapper.getImageFileUpList();
		
		ObjectMapper op = new ObjectMapper();
		LOGGER.debug("list.size() = {}", list.size());
		
		// 전송할 파일이 없음
		
		for (Object fromValue : list) {
			RwdVO vo = op.convertValue(fromValue, RwdVO.class);
			
			LOGGER.debug("업로드할 파일아이디 = [{}], filedir = [{}]", vo.getFileId(), vo.getFileDir());
			
			// 파일정보
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
			
			for (RwdVO vo : fileList) {
				File file = new File(vo.getFileDir());
				// 업로드 경로 공통코드에서
				upDir = vo.getHostUpDir();
				try {
					ftp.upload(upDir, file);
					vo.setProcYn("Y");
				} catch (SftpException e1) {
					LOGGER.error("파일 업로드중 에러 발생-- 업로드경로 : [{}], file = [{}]", upDir, file);
					vo.setProcYn("F");
				} catch (FileNotFoundException e2) {
					LOGGER.error("파일 업로드중 에러 발생-- 업로드경로 : [{}], file = [{}]", upDir, file);
					vo.setProcYn("F");
				} catch (IOException e3) {
					LOGGER.error("파일 업로드중 에러 발생-- 업로드경로 : [{}], file = [{}]", upDir, file);
					vo.setProcYn("F");
				} catch (Exception e) {
					LOGGER.error("파일 업로드중 에러 발생-- 업로드경로 : [{}], file = [{}]", upDir, file);
					vo.setProcYn("F");
				}
				
				int returnCnt = 0;
				// 수정
				returnCnt = rwdMapper.uptImageFileUpList(vo);
				LOGGER.debug("업로드 이미지 건수 =" + returnCnt);

				// 처리상태 업데이트(MSP_RWD_INFO_OUT)
				returnCnt = rwdMapper.uptRwdOutUpList(vo);
				LOGGER.debug("MSP_RWD_INFO_OUT 처리상태 변경 =" + returnCnt);

				// 연동상태 업데이트(MSP_RWD_ORDER)
				returnCnt = rwdMapper.uptRwdUpList(vo);
				LOGGER.debug("MSP_RWD_ORDER 연동상태 변경 =" + returnCnt);
				
				// 문자 발송
				String templateId = "254";				
				SmsCommonVO smsVO = smsCommonMapper.getTemplateText(templateId);				
				smsVO.setSendMessage((smsVO.getTemplateText())
						                         .replaceAll(Pattern.quote("#{rwdProdNm}"), vo.getRwdProdNm()));

				// MSG 테이블 시퀀스 가져오기
				smsVO.setTemplateId(templateId);
				smsVO.setMobileNo(vo.getCtn());
				
				Date toDay = new Date();
				String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATE_SHORT);
				smsVO.setRequestTime(strToDay+"090000");
		        
				// SMS 발송 등록
				int iLmsSendCnt = 0;
				iLmsSendCnt = smsCommonMapper.insertTemplateKakaoMsgQueue(smsVO);

				if(iLmsSendCnt > 0){ // 문자 정상 발송시
					smsVO.setTemplateId(templateId);
					smsVO.setReqId(BatchConstants.BATCH_USER_ID);
					
					SmsSendVO sendVO = new SmsSendVO();
					sendVO.setSendDivision("MSP");
					sendVO.setTemplateId(templateId);
					sendVO.setMseq(smsVO.getMseq());
					sendVO.setRequestTime(smsVO.getRequestTime());
					sendVO.setReqId(BatchConstants.BATCH_USER_ID);
					sendVO.setKtSmsYn("Y");
					// SMS발송내역 등록
					smsCommonMapper.insertSmsSendMst(sendVO);
					
				} else {
			    	LOGGER.info(generateLogMsg("[ SMS 발송 실패 ] " + "전화번호 : " + smsVO.getMobileNo()));						
				}
			}
			
			ftp.disconnection();
		
		} catch (JSchException e) {
			throw new MvnoServiceException("EPTNR1018", e);
		}
		
		LOGGER.debug("===보상서비스 이미지 파일 업로드 끝===");
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
				if("winiaaid".equals(vo.getPartnerId()) || "lpoint".equals(vo.getPartnerId()) || PointConstants.STR_LPOINT_PRM.equals(vo.getPartnerId()) || PointConstants.STR_MIRAE.equals(vo.getPartnerId()) || PointConstants.STR_MOBINS.equals(vo.getPartnerId()) ){
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
