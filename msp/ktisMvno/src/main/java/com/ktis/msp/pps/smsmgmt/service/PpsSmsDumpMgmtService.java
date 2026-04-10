package com.ktis.msp.pps.smsmgmt.service;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.MsgQueueReqVO;
import com.ktis.msp.org.common.vo.FileInfoVO;
import com.ktis.msp.pps.rcgmgmt.mapper.TestHandler;
import com.ktis.msp.pps.smsmgmt.mapper.PpsSmsDumpMgmtMapper;
import com.ktis.msp.pps.smsmgmt.vo.PpsSmsDumpGrpVo;
import com.ktis.msp.util.SFTPUtil;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @param <PpssmsDumpMgmtMapper>
 * @Class Name : PpsHdofcCardMgmtService
 * @Description : 선불
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */

@Service
public class PpsSmsDumpMgmtService extends ExcelAwareService {

	
	
	@Autowired
	protected EgovPropertyService propertyService;

	@Autowired
	private PpsSmsDumpMgmtMapper smsDumpMgmtMapper;
	
	@Autowired
    private FileDownService  fileDownService;
	
	@Autowired
	private MaskingMapper maskingMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;

	
	
	/**
	 *  문자관리 단문문자전송
	 * @param pRequestParamMap
	 * @return
	 * @throws InterruptedException 
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsSmsDumpShortProc(Map<String, Object> pRequestParamMap) throws InterruptedException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Pattern phonePattern = Pattern.compile("^010\\d{8}$");
		
		try{
			String[] calledNumberArr = pRequestParamMap.get("calledNumber").toString().split(",");
			
			if(calledNumberArr.length > 10){
				resultMap.put("oRetCd", "9999");
				resultMap.put("oRetMsg", "10개 이하만 전송가능합니다.");
				return resultMap;
			}
			
			for(int i=0; i<calledNumberArr.length; i++){
				String calledNumber = calledNumberArr[i];
				
				
				if(("").equals(calledNumber) || calledNumber != null){
					Matcher matcher = phonePattern.matcher(calledNumber);

			        if (!matcher.matches()) {
			        	resultMap.put("oRetMsg", "전화번호 형식이 맞지 않는 데이터가 있습니다 : " + calledNumber);
			        	return resultMap;
			        }
					
					KtMsgQueueReqVO vo = new KtMsgQueueReqVO();
					vo.setMsgType("2");
					vo.setMessage(pRequestParamMap.get("smsMsg").toString());
					vo.setSubject(pRequestParamMap.get("smsTitle").toString());
					vo.setCallbackNum(pRequestParamMap.get("callingNumber").toString());
					vo.setRcptData(calledNumber);
					vo.setReserved01("MSP");
					vo.setReserved02("PPS3000000");
					vo.setReserved03(pRequestParamMap.get("adminId").toString());
					
					smsMgmtMapper.insertKtMsgQueue(vo);
				}
			}
			resultMap.put("oRetCd", "0000");
		} catch(Exception e) {
			resultMap.put("oRetMsg", "SMS발송 중 오류가 발생하였습니다.");
		}
		
		return resultMap;
	}

	public String getMsgQueueSeq() throws EgovBizException {
		return smsDumpMgmtMapper.getMsgQueueSeq();
	}
	
	/**
     * JUICE(112.175.98.115) 장비로 파일 업로드
     */
	public void uploadFileJuice(Map<String, Object> pRequestParamMap, FileInfoVO fileInfoVO) throws EgovBizException {
		logger.info("JUICE 장비로 파일 업로드 시작");

		if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_0()))
		{
			smsDumpMgmtMapper.updateFile(fileInfoVO);
		}

		if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_1()))
		{
			smsDumpMgmtMapper.updateFile1(fileInfoVO);
		}

		if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_2()))
		{
			smsDumpMgmtMapper.updateFile2(fileInfoVO);
		}

		if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_3()))
		{
			smsDumpMgmtMapper.updateFile3(fileInfoVO);
		}

		if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_4()))
		{
			smsDumpMgmtMapper.updateFile4(fileInfoVO);
		}
		
		// 업로드할 파일 목록 가져오기
		List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();
		fileList = smsDumpMgmtMapper.getMmsFileList(fileInfoVO);
		
		logger.debug("list.size() = [ " + fileList.size() + " ]");
		
		// 전송할 파일이 없음
		if(fileList.isEmpty()) {
			throw new EgovBizException("파일전송 : 전송할 파일이 없습니다.");
		}
		
		try {
		
			// FTP로 파일 전송
			// FTP 접속정보		
			SFTPUtil ftp = new SFTPUtil();
			String host = propertyService.getString("juice.sftp.host");
			int port = Integer.parseInt(propertyService.getString("juice.sftp.port"));
			String userName = propertyService.getString("juice.sftp.username");
			String password = propertyService.getString("juice.sftp.pwd");
			String upDir = propertyService.getString("juice.sftp.up.dir");
			
			ftp.init(host, userName, password, port);
			
			for (int i=0; i<fileList.size(); i++) {
				String fileDir = fileList.get(i).get("attRout") + fileList.get(i).get("fileNm");
				
				File file = new File(fileDir);
				
				try {
					ftp.upload(upDir, file);
					logger.info("업로드 성공 : [" + upDir + "], file = [" + file + "]");
				} catch (SftpException e1) {
					logger.error("FTP 업로드 에러           -- 업로드경로 : [" + upDir + "], file = [" + file + "]");
				} catch (FileNotFoundException e2) {
					logger.error("업로드할 파일이 없습니다 -- 업로드경로 : [" + upDir + "], file = [" + file + "]");
				} catch (IOException e3) {
					logger.error("파일 닫기 실패               -- 업로드경로 : [" + upDir + "], file = [" + file + "]");
				} catch (Exception e) {
					logger.error("에러로그 확인 바람         -- 업로드경로 : [" + upDir + "], file = [" + file + "]");
				}
				
			}
			
			ftp.disconnection();
		
		} catch (JSchException e) {
			throw new EgovBizException("에러 : mms 파일 업로드 중 에러 발생");
		}
		
		logger.debug("완료 : mms 파일 업로드 [끝]");
	}
	
	/**
     * 파일 업로드(신규)
     */
	public void uploadFileJuiceNew(Map<String, Object> pRequestParamMap, FileInfoVO fileInfoVO) throws EgovBizException {

		if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_0()))
		{
			smsDumpMgmtMapper.updateFile(fileInfoVO);
		}

		if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_1()))
		{
			smsDumpMgmtMapper.updateFile1(fileInfoVO);
		}

		if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_2()))
		{
			smsDumpMgmtMapper.updateFile2(fileInfoVO);
		}
		
		logger.debug("완료 : mms 파일 업로드 [끝]");
	}    	
	
	/**
	 *  문자관리 MMS전송
	 */
//	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> sendMms(Map<String, Object> pRequestParamMap, FileInfoVO fileInfoVO) throws EgovBizException {
		logger.info("mms 전송 시작 <<<<<  ");
		Map<String, Object> resultMap = new HashMap<String, Object>();
				
		List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();
		fileList = smsDumpMgmtMapper.getMmsFileList(fileInfoVO);

		pRequestParamMap.put("fileCnt", fileList.size());
		pRequestParamMap.put("fileLoc1", "");
		pRequestParamMap.put("fileLoc2", "");
		pRequestParamMap.put("fileLoc3", "");
		pRequestParamMap.put("fileLoc4", "");
		pRequestParamMap.put("fileLoc5", "");
		
		logger.info(" fileList.size() <<<<<  " + fileList.size());
		
		if(fileList.size() > 0){
			
			long fileSize = 0;
			int extResult = 0; 
			int hangulChk = 0; 
			
			if(fileList.size() == 1){
				if(this.fileExtChk(fileList, 0)){
					pRequestParamMap.put("fileLoc1", fileList.get(0).get("fileLoc"));					
				} else {
					extResult = 1;
				}
				
				if(this.fileNameHangulChk(fileList, 0)){
					hangulChk = 1;
				}
				fileSize = this.getFileSize(fileList, 0);
			}
			if(fileList.size() == 2){
				if(this.fileExtChk(fileList, 0) && this.fileExtChk(fileList, 1)){
					pRequestParamMap.put("fileLoc1", fileList.get(0).get("fileLoc"));	
					pRequestParamMap.put("fileLoc2", fileList.get(1).get("fileLoc"));					
				} else {
					extResult = 1;
				}

				if(this.fileNameHangulChk(fileList, 0) || this.fileNameHangulChk(fileList, 1)){
					hangulChk = 1;
				}
				fileSize = this.getFileSize(fileList, 0) + this.getFileSize(fileList, 1);
			}
			if(fileList.size() == 3){
				if(this.fileExtChk(fileList, 0) && this.fileExtChk(fileList, 1) && this.fileExtChk(fileList, 2)){
					pRequestParamMap.put("fileLoc1", fileList.get(0).get("fileLoc"));	
					pRequestParamMap.put("fileLoc2", fileList.get(1).get("fileLoc"));
					pRequestParamMap.put("fileLoc3", fileList.get(2).get("fileLoc"));					
				} else {
					extResult = 1;
				}

				if(this.fileNameHangulChk(fileList, 0) || this.fileNameHangulChk(fileList, 1) || this.fileNameHangulChk(fileList, 2)){
					hangulChk = 1;
				}
				fileSize = this.getFileSize(fileList, 0) + this.getFileSize(fileList, 1) + this.getFileSize(fileList, 2);
			}
			if(fileList.size() == 4){
				if(this.fileExtChk(fileList, 0) && this.fileExtChk(fileList, 1) && this.fileExtChk(fileList, 2)
						 && this.fileExtChk(fileList, 3)){
					pRequestParamMap.put("fileLoc1", fileList.get(0).get("fileLoc"));	
					pRequestParamMap.put("fileLoc2", fileList.get(1).get("fileLoc"));
					pRequestParamMap.put("fileLoc3", fileList.get(2).get("fileLoc"));
					pRequestParamMap.put("fileLoc4", fileList.get(3).get("fileLoc"));						
				} else {
					extResult = 1;
				}

				if(this.fileNameHangulChk(fileList, 0) || this.fileNameHangulChk(fileList, 1) || this.fileNameHangulChk(fileList, 2)
						 || this.fileNameHangulChk(fileList, 3)){
					hangulChk = 1;
				}
				fileSize = this.getFileSize(fileList, 0) + this.getFileSize(fileList, 1) + this.getFileSize(fileList, 2) 
							+ this.getFileSize(fileList, 3);			
			}
			if(fileList.size() == 5){
				if(this.fileExtChk(fileList, 0) && this.fileExtChk(fileList, 1) && this.fileExtChk(fileList, 2)
						 && this.fileExtChk(fileList, 3) && this.fileExtChk(fileList, 4)){
					pRequestParamMap.put("fileLoc1", fileList.get(0).get("fileLoc"));	
					pRequestParamMap.put("fileLoc2", fileList.get(1).get("fileLoc"));
					pRequestParamMap.put("fileLoc3", fileList.get(2).get("fileLoc"));
					pRequestParamMap.put("fileLoc4", fileList.get(3).get("fileLoc"));	
					pRequestParamMap.put("fileLoc5", fileList.get(4).get("fileLoc"));						
				} else {
					extResult = 1;
				}

				if(this.fileNameHangulChk(fileList, 0) || this.fileNameHangulChk(fileList, 1) || this.fileNameHangulChk(fileList, 2)
						 || this.fileNameHangulChk(fileList, 3) || this.fileNameHangulChk(fileList, 4)){
					hangulChk = 1;
				}
				fileSize = this.getFileSize(fileList, 0) + this.getFileSize(fileList, 1) + this.getFileSize(fileList, 2) 
							+ this.getFileSize(fileList, 3) + this.getFileSize(fileList, 4);				
			}
			logger.info("총 파일 사이즈 >>> " + fileSize + " KByte");
			
			if(extResult == 1){
				resultMap.put("oRetCd", "9996");
				resultMap.put("oRetMsg", "파일 확장자 오류] jpg, jpeg 파일만 전송 가능합니다.");
				return resultMap;
			}
			
			if(hangulChk == 1){
				resultMap.put("oRetCd", "9997");
				resultMap.put("oRetMsg", "파일명 오류] 한글명 파일은 전송되지 않습니다.");
				return resultMap;
			}
			
			if(fileSize > 1000){
				resultMap.put("oRetCd", "9998");
				resultMap.put("oRetMsg", "파일 사이즈 오류] 총 파일 최대 사이즈는 1000KByte 입니다.");
				return resultMap;
			}
			
		}
		
		String[] calledNumberArr = pRequestParamMap.get("calledNumber").toString().split(",");

		String msgType = "3";
		
		/*pRequestParamMap.put("mseq", fileInfoVO.getOrgnId());*/
		MsgQueueReqVO msgVO = new MsgQueueReqVO();
		msgVO.setMsgType(msgType);
		msgVO.setSubject(pRequestParamMap.get("mmsTitle").toString());

		logger.info("calledNumberArr length >>> " + calledNumberArr.length);
		if(calledNumberArr.length > 50){
			resultMap.put("oRetCd", "9999");
			resultMap.put("oRetMsg", "50개 이하만 전송가능합니다.");
			return resultMap;
		}

		logger.info(" insertMsgQueueMms 시작 <<<<<  " );
		for(int i=0; i<calledNumberArr.length; i++){
			String calledNumber = calledNumberArr[i];
			
			try{
				if(("").equals(calledNumber) || calledNumber != null){
					msgVO.setDstaddr(calledNumber);
					msgVO.setCallback(pRequestParamMap.get("callingNumber").toString());
					msgVO.setSubject(pRequestParamMap.get("mmsTitle").toString());
					msgVO.setText(pRequestParamMap.get("mmsMsg").toString());
					msgVO.setOptId("MMS");
					msgVO.setFileCnt(pRequestParamMap.get("fileCnt").toString());
					msgVO.setFileLoc1(pRequestParamMap.get("fileLoc1").toString());
					msgVO.setFileLoc2(pRequestParamMap.get("fileLoc2").toString());
					msgVO.setFileLoc3(pRequestParamMap.get("fileLoc3").toString());
					msgVO.setFileLoc4(pRequestParamMap.get("fileLoc4").toString());
					msgVO.setFileLoc5(pRequestParamMap.get("fileLoc5").toString());
					
					smsMgmtMapper.insertMsgQueue(msgVO);
					
					
					pRequestParamMap.put("oRetCd", "0000");
					pRequestParamMap.put("oRetMsg", "SUCCESS");
					
				}
			}catch (Exception e) {
				break;
			}
		}

		resultMap.put("oRetCd", pRequestParamMap.get("oRetCd"));
		resultMap.put("oRetMsg", pRequestParamMap.get("oRetMsg"));

		logger.info("mms 전송 종료 <<<<<  ");
		return resultMap;
	}
	
	/**
	 *  문자관리 MMS전송(신규)
	 */
//	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> sendMmsNew(Map<String, Object> pRequestParamMap, FileInfoVO fileInfoVO) throws EgovBizException {
		logger.info("mms 전송 시작 <<<<<  ");
		Map<String, Object> resultMap = new HashMap<String, Object>();
				
		List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();
		fileList = smsDumpMgmtMapper.getMmsFileList(fileInfoVO);

		pRequestParamMap.put("fileCnt", fileList.size());
		pRequestParamMap.put("fileName1", "");
		pRequestParamMap.put("fileName2", "");
		pRequestParamMap.put("fileName3", "");
		
		logger.info(" fileList.size() <<<<<  " + fileList.size());
		
		if(fileList.size() > 3){
			resultMap.put("oRetCd", "9995");
			resultMap.put("oRetMsg", "[파일 갯수 초과] 파일 최대 갯수는 3개를 초과할 수 없습니다.");
			return resultMap;
		}else if(fileList.size() > 0){
			
			long fileSize = 0;
			int extResult = 0; 
			int hangulChk = 0; 
			
			int fileCount = fileList.size();
			boolean isValidExt = true;
			boolean isHangulFile = false;
			int totalFileSize = 0;
			
			for (int i = 0; i < fileCount; i++) {
			    if (!this.fileExtChk(fileList, i)) {
			        isValidExt = false;
			    }

			    if (this.fileNameHangulChk(fileList, i)) {
			        isHangulFile = true;
			    }

			    String fileLoc = fileList.get(i).get("fileLoc"); // 파일 경로 가져오기
			    int lastSlashIndex = fileLoc.lastIndexOf("/"); // 마지막 '/' 위치 찾기
			    String fileName = fileLoc.substring(lastSlashIndex + 1); // 파일명 추출
			    pRequestParamMap.put("fileName" + (i + 1), fileName); // 맵에 저장

			    totalFileSize += this.getFileSize(fileList, i);
			}

			// 확장자가 하나라도 유효하지 않으면 extResult = 1
			if (!isValidExt) {
			    extResult = 1;
			}

			// 한글 파일명이 있으면 hangulChk = 1
			if (isHangulFile) {
			    hangulChk = 1;
			}

			// 파일 크기 저장
			fileSize = totalFileSize;
			
			logger.info("총 파일 사이즈 >>> " + fileSize + " KByte");
			
			if(extResult == 1){
				resultMap.put("oRetCd", "9996");
				resultMap.put("oRetMsg", "[파일 확장자 오류] jpg, jpeg 파일만 전송 가능합니다.");
				return resultMap;
			}
			
			if(hangulChk == 1){
				resultMap.put("oRetCd", "9997");
				resultMap.put("oRetMsg", "[파일명 오류] 한글명 파일은 전송되지 않습니다.");
				return resultMap;
			}
			
			if(fileSize > 1000){
				resultMap.put("oRetCd", "9998");
				resultMap.put("oRetMsg", "[파일 사이즈 오류] 총 파일 최대 사이즈는 1000KByte 입니다.");
				return resultMap;
			}
			
		}else{
			resultMap.put("oRetCd", "9999");
			resultMap.put("oRetMsg", "[파일 미첨부 오류] MMS 전송은 파일첨부가 없을경우 전송이 불가능 합니다.");
			return resultMap;
		}
				
		
		String[] calledNumberArr = pRequestParamMap.get("calledNumber").toString().split(",");

		String msgType = "3";
		
		/*pRequestParamMap.put("mseq", fileInfoVO.getOrgnId());*/
		KtMsgQueueReqVO msgVO = new KtMsgQueueReqVO();
		Pattern phonePattern = Pattern.compile("^010\\d{8}$");
		msgVO.setMsgType(msgType);
		msgVO.setSubject(pRequestParamMap.get("mmsTitle").toString());

		logger.info("calledNumberArr length >>> " + calledNumberArr.length);
		if(calledNumberArr.length > 50){
			resultMap.put("oRetCd", "9999");
			resultMap.put("oRetMsg", "50개 이하만 전송가능합니다.");
			return resultMap;
		}

		logger.info(" insertMsgQueueMms 시작 <<<<<  " );
		for(int i=0; i<calledNumberArr.length; i++){
			String calledNumber = calledNumberArr[i];
			
			try{
				if(("").equals(calledNumber) || calledNumber != null){
					Matcher matcher = phonePattern.matcher(calledNumber);

			        if (!matcher.matches()) {
			        	resultMap.put("oRetMsg", "전화번호 형식이 맞지 않는 데이터가 있습니다 : " + calledNumber);
			        	return resultMap;
			        }
					
					msgVO.setRcptData(calledNumber);
					msgVO.setCallbackNum(pRequestParamMap.get("callingNumber").toString());
					msgVO.setSubject(pRequestParamMap.get("mmsTitle").toString());
					msgVO.setMessage(pRequestParamMap.get("mmsMsg").toString());
					msgVO.setReserved01("MSP");
					msgVO.setReserved02("PPS3000010");
					msgVO.setReserved03(pRequestParamMap.get("adminId").toString());
					msgVO.setFileCount(pRequestParamMap.get("fileCnt").toString());
					for (int j = 0; j < fileList.size(); j++) {
					    String key = "fileName" + (j + 1); // "fileName1", "fileName2", "fileName3" 생성
					    if (pRequestParamMap.containsKey(key)) { // 해당 키가 존재하면
					        String fileName = pRequestParamMap.get(key).toString();
					        switch (j) {
					            case 0:
					                msgVO.setFileName1(fileName);
					                break;
					            case 1:
					                msgVO.setFileName2(fileName);
					                break;
					            case 2:
					                msgVO.setFileName3(fileName);
					                break;
                                default:
                            }
					    }
					}
					
					smsMgmtMapper.insertKtMsgQueue(msgVO);
					
					
					pRequestParamMap.put("oRetCd", "0000");
					pRequestParamMap.put("oRetMsg", "SUCCESS");
					
				}
			}catch (Exception e) {
				break;
			}
		}

		resultMap.put("oRetCd", pRequestParamMap.get("oRetCd"));
		resultMap.put("oRetMsg", pRequestParamMap.get("oRetMsg"));

		logger.info("mms 전송 종료 <<<<<  ");
		return resultMap;
	}	
	
	
	/**
	 *  sms전송 excel파일 읽기
	 * @param pRequestParamMap
	 * @return
	 * @throws InterruptedException 
	 * @
	 */
	@Transactional(rollbackFor=Exception.class)
	public  List<?>  getPpsSmsFileRead(String sFileNm ) throws MvnoRunException {
	     String filePath    =  sFileNm;
	    HSSFWorkbook workBook  = null;
	    HSSFSheet sheet    =  null;
	    HSSFRow row     =  null;
	    HSSFCell cell    =  null;
	    String sCellValue ="";
		FileInputStream fInput = null;
		int nUpdateCnt = 0;
		
		List<EgovMap> resultList = new ArrayList();
		
		try{ 

			fInput = new FileInputStream(new File(filePath));
		   // excel  =  new POIFSFileSystem(new FileInputStream(filePath));
		    workBook  =  new HSSFWorkbook(fInput);
		    workBook.getNumberOfSheets();
			// 1 .sheet
	          sheet     =  workBook.getSheetAt(0);
	          int rows    =  sheet.getPhysicalNumberOfRows();
	          
    		// insert lgs Ord Prdt 
	          for(int r=0;r<rows;r++){
	        	  
	        	   //row전체가 비어있는경우 건너띄기 위해
	        	   if(sheet.getRow(r) == null && rows -1 != r){
		        	   rows++;
		        	   continue;
		           }
	        	   
		           if(r== 0){
			            continue;
			       }

	        	   row     =  sheet.getRow(r);
		           
		           EgovMap em = new EgovMap();
		           for(int c=0;c<4;c++){
			            cell   =  row.getCell(c);		            
			            if(cell== null){
			            	continue;
			            }
			            
			            switch(cell.getCellType()){
                            case HSSFCell.CELL_TYPE_FORMULA :
				            	 sCellValue = ""+cell.getStringCellValue();
                            break;
                            case HSSFCell.CELL_TYPE_NUMERIC :
				            	 sCellValue = ""+cell.getNumericCellValue();
                            break;
                            case HSSFCell.CELL_TYPE_STRING :
				            	 sCellValue = ""+cell.getStringCellValue();
                            break;
                            case HSSFCell.CELL_TYPE_BLANK :
				            	 sCellValue = ""+cell.getStringCellValue();

                            break;
                            case HSSFCell.CELL_TYPE_BOOLEAN :
				            	 sCellValue = ""+cell.getStringCellValue();
                            break;
                            case HSSFCell.CELL_TYPE_ERROR :
			            	     sCellValue = ""+cell.getErrorCellValue();
                            break;				            
				             default:
				            	 logger.debug("default");
			            }
						/*
						   1. 발신번호/수신번호/LMS제목/전송내용
						 */
			            
			            
			            
			            if(c == 0){
			            	em.put("callingNumber", sCellValue);
			            }else if(c == 1){
			            	em.put("calledNumber", sCellValue);
			            }else if(c == 2){
			            	em.put("lmsTitle", sCellValue);
			            }else if(c == 3){
			            	em.put("smsMsg", sCellValue);
			            }

		           }//cell
		           
		           resultList.add(em);
		           
		           nUpdateCnt++;
			      
			       if(nUpdateCnt > 1000  )   { throw new MvnoRunException(-1,"1000건이상은  처리 불가 합니다 ");}
	          }//row
	         
			}
		    catch(Exception e){
              throw new MvnoRunException(-1,"");
		    }
		finally {
			if (fInput != null) {
				try { fInput.close(); } catch (IOException e) {  throw new MvnoRunException(-1,e.getMessage()); }
			}
		}
		
		return resultList;

	}
	
	/**
	 *  문자관리 Excel파일 grid 문자전송
	 * @param pRequestParamMap
	 * @return
	 * @throws InterruptedException 
	 * @
	 */
	//@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsSmsDumpExcelProc(Map<String, Object> pRequestParamMap, JSONArray iJsonArray) throws MvnoServiceException, InterruptedException  {
		
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    	    
	    int dumpSeq = smsDumpMgmtMapper.getDumpSeq();

	    for(int inx = 0; inx<iJsonArray.size(); inx++) {
	    	String smsType = "";
			paramMap = (Map<String, Object>) iJsonArray.get(inx);
			paramMap.put("dumpSeq", String.valueOf(dumpSeq));
		    paramMap.put("opCode", "REG");
		    paramMap.put("adminId", pRequestParamMap.get("adminId"));
		    paramMap.put("smsTitle", pRequestParamMap.get("smsTitle"));
		    			
			int smsMsgByte = getByte(paramMap.get("smsMsg").toString());
			
			if(smsMsgByte > 80){
				smsType = "L";
			}else{
				smsType = "S";
			}
			
			paramMap.put("smsType", smsType);
			
			smsDumpMgmtMapper.getPpsSmsDumpShortProc(paramMap);

			if(!paramMap.get("oRetCd").equals("0000")){
				break;
			}
			
		}
	    
	    if(paramMap.get("oRetCd").equals("0000")){
			paramMap.put("opCode", "REQ");
			smsDumpMgmtMapper.getPpsSmsDumpShortProc(paramMap);
		}

	    resultMap.put("oRetCd", paramMap.get("oRetCd"));
		resultMap.put("oRetMsg", paramMap.get("oRetMsg"));
	
		return resultMap;
	}
	
	/**
	 * 문자전송내역 샘플 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @
	 */
	
	public String getSmsDumpMgmtExcelSample(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) {
		
		String[] strHead = {"발신번호", "수신번호", "LMS제목", "전송내용"};
		int[] intWidth = {5000, 5000, 5000, 5000};
		
		HSSFWorkbook xlsWb = new HSSFWorkbook(); // Excel 2007 이전 버전 
//        Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상 
		
        // Sheet 생성 
        Sheet sheet1 = xlsWb.createSheet("샘플"); 
        
        
        
        for (int i = 0; i < intWidth.length; i++) {
        	sheet1.setColumnWidth(i, intWidth[i]);
		}
          
        // Cell 스타일 생성 
        CellStyle cellStyleHead = xlsWb.createCellStyle(); 
        CellStyle cellStyleMid = xlsWb.createCellStyle(); 
        CellStyle cellStyleInt = xlsWb.createCellStyle(); 
          
        // 줄 바꿈 
        cellStyleHead.setWrapText(true); 
          
        HSSFFont fontHead = xlsWb.createFont();
        fontHead.setBoldweight((short) 1);
        fontHead.setFontName("맑은 고딕");
        fontHead.setColor(HSSFColor.BLACK.index);
        fontHead.setFontHeightInPoints((short) 12);
        
        HSSFFont fontMid = xlsWb.createFont();
        fontMid.setBoldweight((short) 1);
        fontMid.setFontName("맑은 고딕");
        fontMid.setColor(HSSFColor.BLACK.index);
        fontMid.setFontHeightInPoints((short) 10);
        
        //제목
//        cellStyle.setFillForegroundColor(HSSFColor.LIME.index); 
        cellStyleHead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyleHead.setAlignment((short) 2);
        cellStyleHead.setFillPattern((short) 1);
        cellStyleHead.setFillPattern((short) 1);
        cellStyleHead.setBorderRight((short) 1);
//        cellStyleHead.setBorderLeft((short) 1);
        cellStyleHead.setBorderTop((short) 1);
        cellStyleHead.setBorderBottom((short) 1);
        cellStyleHead.setFont(fontHead);
        
        //내용
        cellStyleMid.setAlignment((short) 2);
        cellStyleMid.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        cellStyleMid.setFillPattern((short) 1);
        cellStyleMid.setBorderRight((short) 1);
        cellStyleMid.setBorderLeft((short) 1);
        cellStyleMid.setBorderTop((short) 1);
        cellStyleMid.setBorderBottom((short) 1);
        cellStyleMid.setFont(fontMid);
        
        //숫자용
        HSSFDataFormat format = xlsWb.createDataFormat();
        
        cellStyleInt.setAlignment((short) 3);
        cellStyleInt.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        cellStyleInt.setFillPattern((short) 1);
        cellStyleInt.setBorderRight((short) 1);
        cellStyleInt.setBorderLeft((short) 1);
        cellStyleInt.setBorderTop((short) 1);
        cellStyleInt.setBorderBottom((short) 1);
        cellStyleInt.setFont(fontMid);
        cellStyleInt.setDataFormat(format.getFormat("#,##0"));
        
        Row row = null; 
        Cell cell = null; 

        // 첫 번째 줄 제목 
        row = sheet1.createRow(0); 
        
        for (int i = 0; i < strHead.length; i++) {
        	cell = row.createCell(i); 
        	cell.setCellValue(strHead[i]);
        	cell.setCellStyle(cellStyleHead);
		}
        
        row = sheet1.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("0211111111");
		cell.setCellStyle(cellStyleMid);
		cell = row.createCell(1);
        cell.setCellValue("01011111111");
		cell.setCellStyle(cellStyleMid);
		cell = row.createCell(2);
        cell.setCellValue("9월 이벤트");
		cell.setCellStyle(cellStyleMid);
		cell = row.createCell(3);
        cell.setCellValue("내용 테스트입니다.");
		cell.setCellStyle(cellStyleMid);
		
        row = sheet1.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("0211111111");
		cell.setCellStyle(cellStyleMid);
		cell = row.createCell(1);
        cell.setCellValue("01022222222");
		cell.setCellStyle(cellStyleMid);
		cell = row.createCell(2);
        cell.setCellValue("9월 이벤트2");
		cell.setCellStyle(cellStyleMid);
		cell = row.createCell(3);
        cell.setCellValue("내용 테스트입니다.2");
		cell.setCellStyle(cellStyleMid);

		StringBuilder sbFileName = new StringBuilder();
		
        Date toDay = new Date();
        
        String serverInfo = propertyService.getString("excelPath");
        
        String strFileName = serverInfo + "smsExcelSample";
        String strToDay = KtisUtil.toStr(toDay, KtisUtil.DATETIME_SHORT);
        
        sbFileName.append(strFileName);
        sbFileName.append(strToDay);
        sbFileName.append(".xls");
        
        String encodingFileName = "";

		int serverInfoLen = Integer.parseInt(propertyService.getString("excelPathLen"));
		
        try {
        	encodingFileName = URLEncoder.encode(sbFileName.toString().substring(serverInfoLen), "UTF-8");
        } catch (UnsupportedEncodingException uee) {
        	encodingFileName = sbFileName.toString();
        }

        response.setHeader("Cache-Control", "");
        response.setHeader("Pragma", "");

        response.setContentType("Content-type:application/octet-stream;");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        
        File tempFile = new File(sbFileName.toString());
        
        try {
        	bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			
			xlsWb.write(bos);
			bos.flush();
			bos.close();
			      
			bis = new BufferedInputStream(new FileInputStream(tempFile));
			bos = new BufferedOutputStream(response.getOutputStream());
		        
			byte[] buf = new byte[2048];
			int read = 0;
			while ((read = bis.read(buf)) != -1) {
				bos.write(buf, 0, read);
			}
			bos.flush();
			        
        } catch (IOException e) {
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
        } finally {
        	if (bis != null){
        		try {
        			bis.close();
        		} catch (Exception e) {
        			logger.info(e.getMessage());
        		}
        	}
        	
        	if (bos != null) {
        		try {
        			bos.close();
        		} catch (Exception e) {
        			logger.info(e.getMessage());
        		}
        	}
        }
        
        return sbFileName.toString();
	}
	
	/**
	 * 문자관리 문자전송내역 
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getSmsDumpMgmtList(Map<String, Object> pRequestParamMap){
		if(pRequestParamMap.get("searchStartDate") == null || "".equals(pRequestParamMap.get("searchStartDate"))
				|| pRequestParamMap.get("searchEndDate") == null || "".equals(pRequestParamMap.get("searchEndDate"))){
			throw new MvnoRunException(-1, "발송요청일자 누락");
		}
		
		List<?> resultList = new ArrayList<PpsSmsDumpGrpVo>();
		
		pRequestParamMap.put("searchStartDate", pRequestParamMap.get("searchStartDate")+"000000");
		pRequestParamMap.put("searchEndDate", pRequestParamMap.get("searchEndDate")+"235959");
		
		resultList = smsDumpMgmtMapper.getSmsDumpMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 * 문자전송 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public void getSmsDumpMgmtListExcel(HttpServletResponse response, HttpServletRequest request, Map<String, Object> pRequestParamMap, String path) throws IOException {
		if(pRequestParamMap.get("searchStartDate") == null || "".equals(pRequestParamMap.get("searchStartDate"))
				|| pRequestParamMap.get("searchEndDate") == null || "".equals(pRequestParamMap.get("searchEndDate"))){
			throw new MvnoRunException(-1, "발송요청일자 누락");
		}
		
		pRequestParamMap.put("searchStartDate", pRequestParamMap.get("searchStartDate")+"000000");
		pRequestParamMap.put("searchEndDate", pRequestParamMap.get("searchEndDate")+"235959");
		
		
		ExcelParam param = new ExcelParam();
		param.setStrHead("메세지유형", "전송일자", "제목", "관리부서", "건수", "처리시스템");
		param.setStrValue("MSG_TYPE","SCHEDULE_TIME","SUBJECT","ORGN_NM","CNT","SYSTEM_NM");
		param.setIntWidth(6000, 10000, 15000, 10000, 6000, 8000);
		param.setIntLen(0, 0, 0, 0, 0, 0);
		param.setSheetName("문자전송내역");
		param.setExcelPath(path);
		param.setFileName("문자전송내역");
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pRequestParamMap);
		
		param.setCmnMaskGrpList(lCmnMskGrp);
		
		TestHandler handler  = new TestHandler();
		
		File f = makeBigDataExcel("com.ktis.msp.pps.smsmgmt.mapper.PpsSmsDumpMgmtMapper.getSmsDumpMgmtListExcel", pRequestParamMap, param, handler);
		String fileName = "문자전송내역_"+pRequestParamMap.get("SESSION_USER_ID")+getCurrentTimes()+".xlsx";
		
		//=======파일다운로드사유 로그 START==========================================================
        if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
            
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getHeader("REMOTE_ADDR");
           
            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getRemoteAddr();
            
            pRequestParamMap.put("FILE_NM"   ,fileName);              //파일명
            pRequestParamMap.put("FILE_ROUT" ,f.getPath());              //파일경로
            pRequestParamMap.put("DUTY_NM"   ,"PPS");                       //업무명
            pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
            pRequestParamMap.put("FILE_SIZE" ,(int) f.length());         //파일크기
            pRequestParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
            pRequestParamMap.put("DATA_CNT", 0);                            //자료건수
            
            fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
        }
        //=======파일다운로드사유 로그 END==========================================================
        
		try {
			doDownload(response, f, URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}
		
	}
	
	/**
	 * 회신번호 목록을 가져옴
	 * @param pRequestParamMap
	 * @return
	 */
	public List<?> getSelectPpsCallingNumList(Map<String, Object> pRequestParamMap){
		
		List<?> resultList = new ArrayList<Map<String, Object>>();
		resultList = smsDumpMgmtMapper.getSelectPpsCallingNumList();
		
		return resultList;
	}
	
	

	public String getCurrentTimes()
	{
		String currDateTime = "";
		
		Date currtentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
		currDateTime = "_"+sdfFileName.format(currtentDate);
		
		
		return currDateTime;
	}
	
	public int getByte(String value){
		int retByte = 0;
		for(int i=0; i<value.length(); i++){
			int charByte = 0;
			charByte = String.valueOf(value.charAt(i)).getBytes().length;
			if(charByte == 3){
				charByte = 2;
			}
			
			retByte = retByte + charByte;
		}
		return retByte;
	}
	
	public boolean fileExtChk(List<Map<String, String>> fileList, int i){
		boolean result = true;

		int pos = fileList.get(i).get("fileNm").lastIndexOf( "." );
		String ext = fileList.get(i).get("fileNm").substring( pos + 1 );
		logger.info("확장자 >>>> " + ext);
		
		if(!(ext.equals("jpg") || ext.equals("jpeg") || ext.equals("JPG") || ext.equals("JPEG"))){
			result = false;
		}
		return result;
	}
	
	public long getFileSize(List<Map<String, String>> fileList, int i){
		long fileSize = 0;
		
		File file = new File(fileList.get(i).get("attRout") + fileList.get(i).get("fileNm"));
		if(file.exists()){
			fileSize = file.length() / 1024;
			logger.info("파일사이즈 >>>> " + fileSize + " KByte");
		}
		return fileSize;		
	}
	
	// 파일명 한글 체크
	public boolean fileNameHangulChk(List<Map<String, String>> fileList, int i){

		boolean result = false;
		
		String fileName = fileList.get(i).get("fileNm");

		logger.info("파일이름 한글 체크 >>>> " + fileName);
		if(fileName.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
			result = true;
		}

		return result;
	}
}
