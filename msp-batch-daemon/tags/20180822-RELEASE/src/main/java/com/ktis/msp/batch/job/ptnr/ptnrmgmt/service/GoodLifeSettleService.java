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
import java.util.regex.Pattern;

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
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.GoodLifeSettleMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.PartnerPointSettleMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.GoodLifeMemberVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.GoodLifeReceiveVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.GoodLifeVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;
import com.ktis.msp.batch.util.SFTPUtil;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 좋은라이프 제휴 연동 서비스
 * 1. SMS 발송
 *      매 10분마다 실행되어 가입자정보(MSP_GOODLIFE_MEMBER_MST) 생성 및 이벤트별 SMS발송 처리
 * 2. 가입자정보전송 ( GOODLIFE_001 )
 *      매일 0시 5분에 실행되어 전일 가입자정보를 전송
 *      이때, 상조상품 가입여부 체크하여 서비스계약번호별 할인이력을 생성(MSP_GOODLIFE_POINT_MST)
 * 3. 좋은라이프 가입자정보 수신 ( GOODLIFE_002 )
 *      매월 초 실행되어 ( 파일체크 )
 *      좋은라이프 가입자정보 수신하여 정보를 업데이트 처리한다. ( MSP_GOODLIFE_MEMBER_MST )
 * 4. 좋은라이프 정산정보 송신 ( GOODLIFE_003 )
 *      매월 5일 실행되어 고객 수납정보를 확인하여 정보를 송신한다.(CLS_BILL_PYMN_DATA, MSP_GOODLIFE_POINT_MST)
 *      MSP_GOODLIFE_MEMBER_MST 의 유효한 고객 전부를 대상으로 하여 할인차수, 할인금액 등을 송신한다.
 *      이때, 고객정보(MSP_GOODLIFE_MEMBER_MST)의 할인차수, 할인금액을 변경한다.
 * 5. 좋은라이프 정산정보 수신 ( GOODLIFE_004 )
 *      매월 20일경 실행되어 고객 정산정보를 수신한다. ( 파일체크 )
 *      수신시 고객할인이력 정보를 변경한다. ( MSP_GOODLIFE_POINT_MST )
 */
@Service
public class GoodLifeSettleService extends BaseService {
	
	@Autowired
	private GoodLifeSettleMapper mapper;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	@Autowired
	private PartnerPointSettleMapper partnerPointMapper;
	
	@Autowired
	private PartnerPointSettleService partnerService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	private final static String PARTNER_ID = "goodlife";
	
	/**
	 * 라이프요금제 sms 전송
	 * 
	 * TEMPLATE_ID
	 * NAC : 25 : 개통
	 * RSP : 26 : 정지복구
	 * SCH : 27 : 요금제변경 가입
	 * SCH : 28 : 요금제변경 해지
	 * RCL : 29 : 해지복구
	 * MCN : 30 : 명의변경
	 * 제휴요금제 : KISPRDL01(M 라이프 275), KISLIFE36(M라이프 유심36(음성))
	 * 부가서비스 : KISPRDLDC(M 상조제휴할인 25)
	 * 
	 * 신규가입, 요금제변경가입시 가입정보 생성
	 * 정지/정지복구시 가입정보 및 부가서비스에 대한 변동 처리 없음.
	 * 해지/요금제변경해지시 가입정보 종료일시 변경
	 * 명의변경시 인계자 가입정보 종료일시 변경 및 인수자 가입정보 생성
	 */
	public void setGoodLifeSmsSend() throws MvnoServiceException {
		
		try {
			
			List<Map<String, Object>> list = mapper.getGoodLifeSmsSendList(PARTNER_ID);
			
			for (Map<String, Object> map : list){
				
				LOGGER.debug("map=" + map);
				
				String templateId = "";
				
				GoodLifeMemberVO vo = new GoodLifeMemberVO();
				vo.setEvntCd((String) map.get("EVNT_CD"));
				vo.setContractNum((String) map.get("CONTRACT_NUM"));
				vo.setSvcCntrNo((String) map.get("SVC_CNTR_NO"));
				vo.setCustomerId((String) map.get("CUSTOMER_ID"));
				vo.setSubscriberNo((String) map.get("SUBSCRIBER_NO"));
				vo.setRateCd((String) map.get("RATE_CD"));
				vo.setMinorAgentNm((String) map.get("MINOR_AGENT_NAME"));
				vo.setMinorAgentTel((String) map.get("MINOR_AGENT_TEL"));
				
				if("NAC".equals(vo.getEvntCd())){
					/**
					 * 신규가입 처리
					 * 가입자 정보 생성후 고객 문자 발송
					 * 부가서비스 가입은 상조상품 가입 및 납부 확인이 되어야 가입처리한다.
					 * 부가서비스 가입 후 할인정보 생성(ADD_SVC_YN, ADD_SVC_DT, DC_CNT, DC_STRT_YM)
					 * 해지 후 재가입자는 할인차수를 유지하여야 한다.(해지후에도 할인차수는 매월 증가 처리해야함)
					 */
					templateId = "25";
					
					// 고객ID 이용하여 해지후 재가입여부 확인
					// CTN 은 번호변경 등의 사유로 부적합
					if(mapper.checkGoodLifeMember(vo) > 0){
						vo.setRmk("해지후재가입");
						mapper.insertGoodLifeMemberMst2(vo);
					}else{
						vo.setRmk("신규가입");
						mapper.insertGoodLifeMemberMst(vo);
					}
				}
				else if("RSP".equals(vo.getEvntCd())){
					/**
					 * 정지복구
					 * 정지시 가입자정보 변동 없음.
					 * 정지복구시에도 고객정보 변동없으며 고객에게 문자발송
					 * 부가서비스가 해지된 상태이면 가입처리한다.
					 */
					// 정지복구시 문자발생
					templateId = "26";
				}
				else if("SCH_IN".equals(vo.getEvntCd())){
					/**
					 * 요금제변경
					 * 타요금제 -> 제휴요금제
					 *     고객정보 생성하고 고객에게 문자발송(NAC 와 동일한 처리)
					 * 제휴요금제 -> 제휴요금제
					 *     요금제코드만 변경처리한다.
					 * 제휴요금제 -> 타요금제 -> 제휴요금제인 경우
					 *     고객정보 체크 후 기존 정보의 상태코드, 종료일시를 변경한다.
					 */
					templateId = "27";
					
					if(mapper.checkGoodLifeMember(vo) > 0){
						vo.setRmk("요금제변경재가입");
						// 기존 정보의 종료일시 상태코드를 업데이트한다.
						mapper.updateGoodLifeMemberMst(vo);
					}else{
						vo.setRmk("요금제변경가입");
						mapper.insertGoodLifeMemberMst(vo);
					}
					
				}
				else if("SCH_OUT".equals(vo.getEvntCd())){
					templateId = "28";
					
					vo.setRmk("요금제변경해지");
					// 보험가입 효력정지
					mapper.deleteGoodLifeMemberMst(vo);
				}
				else if("CAN".equals(vo.getEvntCd())){
					/**
					 * 고객정보 해지처리
					 * 종료일시, 상태코드, 부가서비스가입여부, 부가서비스가입일자
					 */
					vo.setRmk("계약해지");
					mapper.deleteGoodLifeMemberMst(vo);
				}
				else if("RCL".equals(vo.getEvntCd())){
					/**
					 * 해지복구
					 * 종료일시, 상태코드 변경
					 * 부가서비스는 재가입처리함.
					 */
					templateId = "29";
					
					vo.setRmk("해지복구");
					mapper.updateGoodLifeMemberMst(vo);
				}
				else if("MCN".equals(vo.getEvntCd())){
					/**
					 * 명의변경
					 * 인계자 고객정보 종료일시, 계약상태 등 해지
					 * 인수자 고객정보 생성, 인수자에게 문자발송
					 */
					templateId = "30";
					
					// 인계자의 계약정보 종료일시, 상태 변경
					GoodLifeMemberVO vo2 = new GoodLifeMemberVO();
					vo2.setContractNum(vo.getContractNum());
					vo2.setRmk("명의변경해지");
					mapper.deleteGoodLifeMemberMst(vo2);
					
					// 신규 계약정보 생성
					vo.setRmk("명의변경가입");
					mapper.insertGoodLifeMemberMst(vo);
				}
				
				// 문자발송
				// 템플릿ID 가 있고, 발송요청시간이 있는 경우 문자 발송 처리
				if(!"".equals(templateId) && !"X".equals((String) map.get("REQ_SEND_DATE"))){
					SmsCommonVO smsVO = smsCommonMapper.getTemplateText(templateId);
					// 요금제명 치환
					smsVO.setSendMessage((smsVO.getTemplateText()).replaceAll(Pattern.quote("#{rateNm}"), String.valueOf(map.get("RATE_NM")) ));
					smsVO.setTemplateId(templateId);
					smsVO.setMobileNo((String) map.get("SUBSCRIBER_NO"));
					smsVO.setRequestTime((String) map.get("REQ_SEND_DATE"));
					
					smsCommonMapper.insertTemplateMsgQueue(smsVO);
				}
			}
			
			// 부가서비스 가입/해지 수정
			mapper.updateAddSvcYn();
			
		}catch(Exception e){
			throw new MvnoServiceException("EPTNR1021", e);
		}
	}
	
	/**
	 * 좋은라이프 수신파일 처리 (GOODLIFE_002, GOODLIFE_004)
	 * @param param
	 * @return
	 * @throws MvnoServiceException
	 */
	public String checkGoodlifeFile(Map<String, Object> param) throws MvnoServiceException {
		
		String rmk = "";
		
		// PointFileVO 생성
		PointFileVO fileVO = makeFileVo(param);
		
		fileVO = getFileVO(fileVO);
		
		// 이미 처리한 내용이면 종료.
		if("S".equals(fileVO.getSendResult())) {
			LOGGER.debug("GoodLifeFileService [{}] 완료된 파일.", fileVO.getFileNm());
			rmk = fileVO.getFileNm() + " 완료된 파일.";
		} else {
			// FTP 정보 생성
			SFTPUtil ftp = ftpInit();
			
			// 파일이 있는지 확인
			if(fileCheck(ftp, fileVO)) {
				
				// 파일다운로드 
				fileDown(ftp, fileVO);
				
				// 파일 읽고 DB에 저장
				readFile(fileVO);
				
			// 파일이 없으면 종료.
			} else {
				LOGGER.debug("GoodLifeFileService [{}] 파일 없음.", fileVO.getFileNm());
				rmk = fileVO.getFileNm() + " 파일 없음.";
			}
		}
		
		return rmk;
	}
	
	/**
	 * PointFileVO 초기생성.
	 * @param param
	 * @return
	 */
	private PointFileVO makeFileVo(Map<String, Object> param) {
		// 생성월
		String nowYm = (String) param.get("ifYm");
		String ifNo = (String) param.get("ifNo");
		
		LOGGER.debug("[GoodLifeFileService] {}:{} START!!!!!!!!!!!!!!!!!!!!!!!!!!", ifNo, nowYm);
		
		// 파일명
		StringBuffer fileNmSb = new StringBuffer();
		fileNmSb.append(ifNo);
		fileNmSb.append("_");
		fileNmSb.append(nowYm);
		fileNmSb.append(PointConstants.FILE_EXT);
		
		String fileNm = fileNmSb.toString();
		
		PointFileVO fileVO = new PointFileVO();
		fileVO.setPartnerId(PARTNER_ID);
		fileVO.setFileNm(fileNm);
		fileVO.setIfNo(ifNo);
		fileVO.setIfYm(nowYm);
		
		return fileVO;
	}
	
	/**
	 * 최종 작업할 PointFileVO 를 얻는다.
	 * @param fileVO
	 * @return
	 * @throws MvnoServiceException 
	 */
	private PointFileVO getFileVO(PointFileVO fileVO) throws MvnoServiceException {
		
		PointFileVO vo = fileVO;
		
		try {
			
			// MSP_PARTNER_POINT_FILE 데이터 확인
			List<PointFileVO> fileList = mapper.getGoodlifeFileDownList(vo);
			
			// 입력된 값 없을때 신규 입력.
			if(fileList.isEmpty()) {
				
				List<PointFileVO> subList = mapper.getPartnerSubList(vo);
				
				if(subList.isEmpty()) {
					LOGGER.error("인터페이스 정보가 없음.. [{} / {}]", vo.getPartnerId(), vo.getIfNo());
					throw new MvnoServiceException("EPTNR1012");
				}
				
				PointFileVO subVO = subList.get(0);
				String lDownBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
				String lUpBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
				String hDownBaseDir = dirInit(subVO.getDownDir());
				String hUpBaseDir = dirInit(subVO.getUpDir());
				
				// 로컬 업로드 경로
				String lUpDir = lUpBaseDir + PointConstants.STR_GOODLIFE + "/" + hUpBaseDir;
				// 로컬 다운로드 경로
				String lDownDir = lDownBaseDir + PointConstants.STR_GOODLIFE + "/" + hDownBaseDir;
				// 서버 업로드 경로 구하기
				StringBuffer buf1 = new StringBuffer();
				buf1.append(PointConstants.STR_GOODLIFE);
				buf1.append("/");
				buf1.append(hUpBaseDir);
				String hUpDir = buf1.toString();
				// 서버 다운로드 경로
				StringBuffer buf = new StringBuffer();
				buf.append(PointConstants.STR_GOODLIFE);
				buf.append("/");
				buf.append(hDownBaseDir);
				String hDownDir = buf.toString();
				
				vo.setLocalUpDir(lUpDir);
				vo.setHostUpDir(hUpDir);
				vo.setLocalDownDir(lDownDir);
				vo.setHostDownDir(hDownDir);
				partnerPointMapper.insertPointFile(vo);
			} else {
				vo = fileList.get(0);
			}
		} catch (MvnoServiceException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("PointFileVO 생성 실패.. [{}]", e.getMessage());
			throw new MvnoServiceException("EPTNR1026", e);
		}
		
		return vo;
	}
	
	/**
	 * 디렉토리 정보 정리
	 * @param dir
	 * @return
	 */
	private String dirInit(String dir) {
		
		String str = dir;
		
		// 첫번째 문자열에 / 가 있으면 빼준다.
		if(str.charAt(0) == '/') {
			str = str.substring(1);
		}
		
		// 마지막 문자열에 / 가 없으면 추가해준다.
		if(str.charAt(str.length()-1) != '/') {
			StringBuffer sb = new StringBuffer();
			sb.append(str);
			sb.append("/");
			str = sb.toString();
		}
		
		return str;
	}
	
	/**
	 * FTP 접속
	 * @return
	 * @throws MvnoServiceException
	 */
	private SFTPUtil ftpInit() throws MvnoServiceException {
		LOGGER.debug("[GoodLifeFileService] FTP 정보 생성 .");
		// FTP 접속정보
		String host = propertiesService.getString("msp.sftp.host");
		int port = Integer.parseInt(propertiesService.getString("msp.sftp.port"));
		String userName = propertiesService.getString("msp.sftp.username");
		String password = propertiesService.getString("msp.sftp.pwd");
		
		SFTPUtil ftp = new SFTPUtil();
		try {
			ftp.init(host, userName, password, port);
		} catch (JSchException e3) {
			throw new MvnoServiceException("EPTNR1023");
		}
		
		return ftp;
	}
	
	/**
	 * FTP File 체크. 있으면 true, 없으면 false
	 * @param ftp
	 * @param vo
	 * @return
	 */
	private boolean fileCheck(SFTPUtil ftp, PointFileVO vo) {
		LOGGER.debug("[GoodLifeFileService] {} 파일이 있는지 확인 시작!!!!!!!!!!!!!!!!!!!", vo.getFileNm());
		return ftp.fileStat(vo.getHostDownDir(), vo.getFileNm());
	}
	
	/**
	 * FTP에서 파일 다운로드
	 * @param ftp
	 * @param vo
	 */
	private void fileDown(SFTPUtil ftp, PointFileVO vo) {
		LOGGER.debug("[GoodLifeFileService] FTP에서 파일({}) 다운로드 시작! ", vo.getFileNm());
		String hDnDir = vo.getHostDownDir();	// ftp 파일 경로
		String lDnDir = vo.getLocalDownDir();	// 다운로드 경로
		String dnFileName = vo.getFileNm();		// 다운할 파일명
			
		File desti = new File(lDnDir);
		
		// 디렉토리가 없다면 생성
		if(!desti.exists()) {
			desti.mkdirs();
		}
		
		try {
			ftp.download(hDnDir, dnFileName, lDnDir + dnFileName);
			vo.setSendResult("D");
		} catch (SftpException e1) {
			LOGGER.error("파일 다운로드중 에러 발생1(SftpException)-- 다운로드경로 : [{}], file = [{}]", hDnDir, dnFileName);
			vo.setSendResult("F");
		} catch (IOException e2) {
			LOGGER.error("파일 다운로드중 에러 발생2(IOException)-- 다운로드경로 : [{}], file = [{}]", hDnDir, dnFileName);
			vo.setSendResult("F");
		} catch (Exception e) {
			LOGGER.error("파일 다운로드중 에러 발생3(Exception)-- 다운로드경로 : [{}], file = [{}]", hDnDir, dnFileName);
			vo.setSendResult("F");
		}
		
		partnerPointMapper.updatePointFileDown(vo);
		
		ftp.disconnection();
	}
	
	/**
	 * 다운로드 받은 파일을 파싱하여 DB에 저장.
	 * @param vo
	 * @throws MvnoServiceException
	 */
	private void readFile(PointFileVO vo) throws MvnoServiceException {
		
		LOGGER.debug("[GoodLifeFileService] 파일({}) 읽기 시작!", vo.getFileNm());
		
		String downloadFileName = vo.getLocalDownDir() + vo.getFileNm();
		File f = new File(downloadFileName);
		
		String[] errParam = new String[1];
		errParam[0] = vo.getPartnerId() + " / " + vo.getFileNm();
		
		// 파일이 존재하는지 확인
		if(!f.isFile()) {
			throw new MvnoServiceException("EPTNR1011", errParam);
		}
		
		// 파일 읽기
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(downloadFileName));
		} catch (FileNotFoundException e) {
			throw new MvnoServiceException("EPTNR1011", errParam);
		}
		
		try {
			String s = "";
			
			while ((s = in.readLine()) != null) {
				String[] lineString = s.split("\\|", -1);
				
				LOGGER.debug("lineString.length ==> [{}]" , lineString.length);
				
				GoodLifeReceiveVO receiveVO = new GoodLifeReceiveVO();
				receiveVO.setIfNo(vo.getIfNo());
				receiveVO.setIfYmd(vo.getIfYm());
				
				// GOODLIFE_002 일때.
				if((PointConstants.GOODLIFE_002).equals(vo.getIfNo())) {
					receiveVO.setIfId("GDLIFE_002");
					LOGGER.debug("lineString[0] ==> [{}]" , lineString[0]);
					receiveVO.setSvcCntrNo(lineString[0]);	// 서비스계약번호
					//receiveVO.setCtn(decrypt(lineString[1], PointConstants.SEED_KEY_GOODLIFE));
					LOGGER.debug("lineString[1] ==> [{}]" , lineString[1]);
					receiveVO.setStatYn(lineString[1]);		// 가입여부
					LOGGER.debug("lineString[2] ==> [{}]" , lineString[2]);
					receiveVO.setPymnYn(lineString[2]);		// 수납여부
					LOGGER.debug("lineString[3] ==> [{}]" , lineString[3]);
					if(lineString[3] != null && !"".equals(lineString[3])) {
						receiveVO.setGoodlifeId(lineString[3]);	// 좋은라이프 식별번호
					}
					LOGGER.debug("lineString[4] ==> [{}]" , lineString[4]);
					if(lineString[4] != null && !"".equals(lineString[4])) {
						receiveVO.setPayCnt(Integer.parseInt(lineString[4]));	// 좋은상조 수납차수
					}
					
					// 좋은라이프 가입자정보 수정 
					// (PARTNER_YN:가입여부, PARTNER_PYMN_YN:수납여부, GOODLIFE_ID:좋은라이프식별번호, PAY_CNT:좋은라이프수납차수)
					mapper.updateGoodlifeMember(receiveVO);
					
					/* KOS 부가서비스 가입 확인 후 정산테이블 생성으로 변경
					// 정산테이블 확인 (정산테이블에 값이 없으면 'Y')
					String flag = mapper.checkGoodlifePoint(receiveVO);
					
					// 정산테이블에 값이 없고 좋은상조수납차수가 1일때 정산테이블 36개월 생성
					if("Y".equals(flag) && receiveVO.getPayCnt() == 1) {
						mapper.insertAllCnt(receiveVO);
					}
					
					// 수납여부 UPDATE
					mapper.updateGoodlifePymnYm(receiveVO);
					*/
					
				} else if((PointConstants.GOODLIFE_004).equals(vo.getIfNo())) {
					
					receiveVO.setIfId("GDLIFE_004");
					LOGGER.debug("lineString[0] ==> [{}]" , lineString[0]);
					receiveVO.setSvcCntrNo(lineString[0]);	// 서비스계약번호
					LOGGER.debug("lineString[1] ==> [{}]" , lineString[1]);
					receiveVO.setUsgYm(lineString[1]);		// 사용월
					LOGGER.debug("lineString[2] ==> [{}]" , lineString[2]);
					receiveVO.setBillYm(lineString[2]);		// 청구월
					LOGGER.debug("lineString[3] ==> [{}]" , lineString[3]);
					receiveVO.setSttlYm(lineString[3]);		// 정산월
					LOGGER.debug("lineString[4] ==> [{}]" , lineString[4]);
					if(lineString[4] != null && !"".equals(lineString[4])) {
						receiveVO.setDcAmt(Integer.parseInt(lineString[4]));	// 엠모바일 할인금액
					}
					LOGGER.debug("lineString[5] ==> [{}]" , lineString[5]);
					if(lineString[5] != null && !"".equals(lineString[5])) {
						receiveVO.setCbAmt(Integer.parseInt(lineString[5]));	// 좋은라이프 할인금액
					}
					
					LOGGER.debug("lineString[6] ==> [{}]" , lineString[6]);
					if(lineString[6] != null && !"".equals(lineString[6])) {
						receiveVO.setDcCnt(Integer.parseInt(lineString[6]));	// 엠모바일 할인차수
					}
					
					LOGGER.debug("lineString[7] ==> [{}]" , lineString[7]);
					if(lineString[7] != null && !"".equals(lineString[7])) {
						receiveVO.setPayCnt(Integer.parseInt(lineString[7]));	// 좋은라이프 수납차수
					}
					
					// 정산테이블에 Update
					mapper.updateGoodlifePoint(receiveVO);
					
				}
				
				// 연동정보 - 동일한 내용이 있을때 삭제 후 입력
				mapper.deleteGoodlifeInterface(receiveVO);
				mapper.insertGoodlifeInterface(receiveVO);
				
			}
			in.close();
			
		} catch (NumberFormatException e) {
			throw new MvnoServiceException("EPTNR1024", errParam);
		} catch (IOException e) {
			throw new MvnoServiceException("EPTNR1025", errParam);
		}
		
		// 제휴사 정산 파일 전송결과 관리 - 다운로드 상태를 성공으로 변경
		vo.setSendResult("S");
		partnerPointMapper.updatePointFileDown(vo);
		
		LOGGER.debug("[GoodLifeFileService] 파일({}) 읽기 종료!", vo.getFileNm());
		
	}
	
	/**
	 * 정산내역 생성
	 * 매일 0시 가입자정보 전송 전 실행되어 처리
	 * @throws MvnoServiceException
	 */
	@SuppressWarnings("unchecked")
	public void saveGoodLifePointMst() throws MvnoServiceException {
		List<EgovMap> list = mapper.getCheckAddSvcList();
		
		for(EgovMap map : list){
			LOGGER.debug("map=", map);
			
			// MSP_GOODLIFE_POINT_MST 생성
			// 상조상품 가입되면 부가서비스 미가입 상태여도 할인이력 생성
			mapper.insertAllCnt(map);
		}
	}
	
	/**
	 * 좋은라이프 정산 파일 생성
	 * @param searchVO
	 * <li>partnerId=제휴사아이디</li>
	 * @return
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	public void saveGoodLifeSettleFile(String partnerId, String ifNo) throws MvnoServiceException {
		LOGGER.info("제휴사 정산 파일 저장 시작..................................");
		
		List<?> clist = null;
		
		// 제휴사 아이디가 있는지 확인
		if(StringUtils.isEmpty(partnerId) || !(PointConstants.STR_GOODLIFE).equals(partnerId)) {
			throw new MvnoServiceException("EPTNR1006");
		}
		
		String[] errParam = new String[2];
		errParam[0] = partnerId;
		errParam[1] = ifNo;
		
		GoodLifeMemberVO vo = new GoodLifeMemberVO();
		vo.setPartnerId(partnerId);
		
		// 좋은라이프 가입자정보
		if((PointConstants.GOODLIFE_001).equals(ifNo)){
			try{
				LOGGER.info("좋은라이프 가입자정보 조회...........................");
				
				clist = mapper.getGoodLifeMemberList();
				
			} catch(Exception e) {
				throw new MvnoServiceException("EPTNR1014", errParam, e);
			}
		}
		// 정산정보
		else if((PointConstants.GOODLIFE_003).equals(ifNo)){
			try{
				LOGGER.info("좋은라이프 정산정보 조회...........................");
				
				clist = mapper.getGoofLifeSettleAmntList();
				
			} catch(Exception e) {
				throw new MvnoServiceException("EPTNR1014", errParam, e);
			}
		}
		
		if(!clist.isEmpty()) {
			try {
				saveGoodLifeSettleFile(clist, partnerId, ifNo);
			} catch(MvnoServiceException e) {
				throw e;
			} catch(Exception e) {
				throw new MvnoServiceException("EPTNR1016", errParam, e);
			}
		}
		
		LOGGER.info("제휴사 정산 파일 저장 끝..................................");
		
	}
	
	/**
	 * 좋은라이프 파일처리
	 * @param list
	 * @param partnerId
	 * @param ifNo
	 * @throws MvnoServiceException
	 */
	private void saveGoodLifeSettleFile(List<?> list, String partnerId, String ifNo) throws MvnoServiceException {
		LOGGER.info("제휴사 정산 파일 쓰기 시작..................................");
		
		ObjectMapper op = new ObjectMapper();
		// 파일명
		String fileName = "";
		
		String lDownBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String lUpBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String hDownBaseDir = "";
		String hUpBaseDir = "";
		List<?> clist = null;
		
		// 디렉토리 가져오기
		if((PointConstants.STR_GOODLIFE).equals(partnerId)) {
			clist = partnerPointMapper.getPartnerSubList(ifNo);
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
			}
		}
		
		// 로컬 다운로드 경로
		String lDownDir = lDownBaseDir + partnerId + "/" + hDownBaseDir;
		// 서버 다운로드 경로
		String hDownDir = "";
		StringBuffer buf = new StringBuffer();
		buf.append(partnerId);
		buf.append("/");
		buf.append(hDownBaseDir);
		hDownDir = buf.toString();
		
		// 로컬 업로드 경로
		String lUpDir = lUpBaseDir + partnerId + "/" + hUpBaseDir;
		// 서버 업로드 경로 구하기
		StringBuffer buf1 = new StringBuffer();
		buf1.append(partnerId);
		buf1.append("/");
		buf1.append(hUpBaseDir);
		String hUpDir = buf1.toString();
		
		// 파일명 생성
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		if(!(PointConstants.GOODLIFE_001).equals(ifNo)){
			formatter = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
		}
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		StringBuffer buf2 = new StringBuffer();
		//sbuf.append(lUpBaseDir);
		if((PointConstants.STR_GOODLIFE).equals(partnerId)) {
			buf2.append(ifNo);
		}
		
		buf2.append("_");
		buf2.append(dDate);
		buf2.append(PointConstants.FILE_EXT);
		fileName = buf2.toString();
		
		LOGGER.info("좋은라이프 파일명 =" + fileName);
		
		String str = "";
		
		try {
			File desti = new File(lUpDir);
			
			// 디렉토리가 없다면 생성
			if (!desti.exists()) {
				desti.mkdirs();
			}
			
			// BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
			BufferedWriter fw = new BufferedWriter(new FileWriter(lUpDir + fileName, false));
			
			// 연동이력 삭제
			GoodLifeReceiveVO dVO = new GoodLifeReceiveVO();
			dVO.setIfNo(ifNo);
			dVO.setIfYmd(dDate);
			mapper.deleteGoodlifeInterface(dVO);
			
			// 파일안에 문자열 쓰기
			for (Object fromValue : list) {
				GoodLifeMemberVO vo = op.convertValue(fromValue, GoodLifeMemberVO.class);
				
				// 문자열만들기
				str = getStringGoodLife(vo, ifNo);
				
				// 연동이력 생성
				GoodLifeVO ifVO = new GoodLifeVO();
				ifVO.setIfNo(ifNo);
				ifVO.setIfYmd(dDate);
				ifVO.setSvcCntrNo(vo.getSvcCntrNo());
				ifVO.setSubscriberNo(vo.getSubscriberNo());
				ifVO.setAddSvcYn(vo.getAddSvcYn());
				ifVO.setAddSvcDt(vo.getAddSvcDt());
				ifVO.setUsgYm(vo.getUsgYm());
				ifVO.setBillYm(vo.getBillYm());
				ifVO.setSttlYm(vo.getSttlYm());
				ifVO.setDcCnt(vo.getDcCnt());
				ifVO.setPayCnt(vo.getPayCnt());
				ifVO.setBaseAmt(vo.getBaseAmt());
				ifVO.setDcAmt(vo.getDcAmt());
				ifVO.setPartnerPymnYn(vo.getPartnerPymnYn());
				
				LOGGER.debug("ifVO=" + ifVO);
				
				mapper.insertPartnerGoodLife(ifVO);
				
				// 정산결과 UPDATE
				if((PointConstants.GOODLIFE_003).equals(ifNo)){
					// MSP_GOODLIFE_POINT_MST UPDATE
					mapper.updateGoodLifePointSettle(ifVO);
					
					// 할인차수 UPDATE
					mapper.updateMemberDcInfo(ifVO);
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
		
		// 파일생성 후 파일내용을 디비에 저장
		PointFileVO fileVo = new PointFileVO();
		fileVo.setPartnerId(PARTNER_ID);
		fileVo.setLocalUpDir(lUpDir);
		fileVo.setHostUpDir(hUpDir);
		fileVo.setLocalDownDir(lDownDir);
		fileVo.setHostDownDir(hDownDir);
		fileVo.setFileNm(fileName);
		
		partnerPointMapper.deletePointFile(fileVo);
		partnerPointMapper.insertPointFile(fileVo);
		
		LOGGER.info("제휴사 정산 파일 쓰기 끝..................................");
		
		LOGGER.info("제휴사 정산 파일 FTP 업로드 ..................................");
		
		try{
			this.uploadPointFile(fileName);
		}catch(Exception e){
			throw new MvnoServiceException("EPTNR1017", e);
		}
	}
	
	/**
	 * 송신 파일내용 암호화
	 * @param vo
	 * @param ifNo
	 * @return
	 */
	private String getStringGoodLife(GoodLifeMemberVO vo, String ifNo) {
		
		StringBuffer sb = new StringBuffer();
		
		// 가입자정보 송신
		if((PointConstants.GOODLIFE_001).equals(ifNo)){
			String txt1 = vo.getSvcCntrNo();                                                               /* 서비스계약번호 */
			String txt2 = partnerService.encrypt(vo.getCustNm(), PointConstants.SEED_KEY_GOODLIFE);        /* 고객명 */
			String txt3 = partnerService.encrypt(vo.getSubscriberNo(), PointConstants.SEED_KEY_GOODLIFE);  /* 전화번호 */
			String txt4 = partnerService.encrypt(vo.getRateNm(), PointConstants.SEED_KEY_GOODLIFE);        /* 요금제, 2017-04-28 요금제명 암호화 요청 */
			String txt5 = vo.getStrtDttm();                                                                /* 요금제가입일 */
			String txt6 = vo.getMinorAgentNm() != null && !"".equals(vo.getMinorAgentNm()) ? partnerService.encrypt(vo.getMinorAgentNm(), PointConstants.SEED_KEY_GOODLIFE) : "";
			String txt7 = vo.getMinorAgentTel() != null && !"".equals(vo.getMinorAgentTel()) ? partnerService.encrypt(vo.getMinorAgentTel(), PointConstants.SEED_KEY_GOODLIFE) : "";
			// 2017-05-24, 연동항목 추가
			String txt8 = partnerService.encrypt(vo.getOpenDt(), PointConstants.SEED_KEY_GOODLIFE);
			String txt9 = partnerService.encrypt(vo.getOrgnNm(), PointConstants.SEED_KEY_GOODLIFE);
			String txt10 = partnerService.encrypt(vo.getBirthDt(), PointConstants.SEED_KEY_GOODLIFE);
			String txt11 = partnerService.encrypt(vo.getGender(), PointConstants.SEED_KEY_GOODLIFE);
			
			sb.append(txt1);
			sb.append("|").append(txt2);
			sb.append("|").append(txt3);
			sb.append("|").append(txt4);
			sb.append("|").append(txt5);
			sb.append("|").append(txt6);
			sb.append("|").append(txt7);
			sb.append("|").append(txt8);
			sb.append("|").append(txt9);
			sb.append("|").append(txt10);
			sb.append("|").append(txt11);
			
		}
		// 정산내역 송신
		else if((PointConstants.GOODLIFE_003).equals(ifNo)){
			String txt1 = vo.getSvcCntrNo();
			String txt2 = vo.getUsgYm();
			String txt3 = vo.getBillYm();
			String txt4 = vo.getSttlYm();
			String txt5 = vo.getBaseAmt();
			String txt6 = vo.getDcAmt();
			String txt7 = vo.getDcCnt();
			String txt8 = vo.getPayCnt();
			
			sb.append(txt1);
			sb.append("|").append(txt2);
			sb.append("|").append(txt3);
			sb.append("|").append(txt4);
			sb.append("|").append(txt5);
			sb.append("|").append(txt6);
			sb.append("|").append(txt7);
			sb.append("|").append(txt8);
			
		}
		
		return sb.toString();
	}
	
	/**
	 * 파일업로드
	 */
	public void uploadPointFile(String fileNm) throws MvnoServiceException {
		LOGGER.info("제휴사 정산 파일 업로드 시작..................................");
		
		List<PointFileVO> fileList = new ArrayList<PointFileVO>();
		
		// 제휴사 아이디가 있는지 확인 (나중에 제휴사별로 업로드하도록 하자.
		// 현재는 전송할 파일 전부 다 가져오도록 하자)
		if(StringUtils.isEmpty(PARTNER_ID)) {
			throw new MvnoServiceException("EPTNR1004");
		}
		
		PointFileVO pvo = new PointFileVO();
		pvo.setPartnerId(PARTNER_ID);
		pvo.setFileNm(fileNm);
		
		LOGGER.info("pvo=" + pvo);
		
		// 업로드할 파일 목록 가져오기
		List<?> list = partnerPointMapper.getPointFileUpList(pvo);
		
		ObjectMapper op = new ObjectMapper();
		LOGGER.info("list.size() = {}", list.size());
		
		String[] errParam = new String[1];
		errParam[0] = fileNm;
		
		// 전송할 파일이 없음
		if(list.isEmpty()) {
			throw new MvnoServiceException("EPTNR1017", errParam);
		}
		
		for (Object fromValue : list) {
			PointFileVO vo = op.convertValue(fromValue, PointFileVO.class);
			
			LOGGER.info("업로드할 제휴사아이디 = [{}], filename = [{}]", vo.getPartnerId(), vo.getFileNm());
			
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
				
				partnerPointMapper.updatePointFileUp(vo);
			}
			
			ftp.disconnection();
		
		} catch (JSchException e) {
			throw new MvnoServiceException("EPTNR1018", errParam, e);
		}
		
		LOGGER.info("파일 업로드 끝..................................");
	}
}
