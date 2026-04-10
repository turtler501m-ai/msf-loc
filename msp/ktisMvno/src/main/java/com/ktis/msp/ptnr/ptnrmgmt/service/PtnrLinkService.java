package com.ktis.msp.ptnr.ptnrmgmt.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.ktis.msp.base.example.SEED;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.ptnr.ptnrmgmt.mapper.PtnrLinkMapper;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrLPointInterfaceVO;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrLinkInfoVO;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrRetryFileVO;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrRetryPointVO;
import com.ktis.msp.util.SFTPUtil;

import crypto.AesFileDecrypto;
import crypto.AesFileEncrypto;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class PtnrLinkService extends BaseService {
	
	@Autowired
	private PtnrLinkMapper ptnrLinkMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	private final static String STR_JEJUAIR = "jejuair";
	private final static String STR_GIFTI = "gifti";
	private final static String STR_TMONEY = "tmoney";
	private final static String STR_GOOGLEPLAY = "googleplay";
	private final static String JEJUAIR_IF_NO = "IF_WS_0009";		// 제주항공 제휴요금제 관련 인터페이스 ID
	private final static String GIFTI_IF_NO = "IF_GT_0001";			// 엠하우스 제휴관련 인터페이스 ID
	private final static String TMONEY_IF_NO = "KTMMKSCC_001";		// 티머니 제휴관련 인터페이스 ID
	private final static String GOOGLEPLAY_IF_NO = "KTMMGPLAY_001";	// 구글Play 제휴관련 인터페이스 ID
	private final static String FILE_EXT = ".DAT";
	private final static String SEED_KEY_GIFTI = "KTMHOWS2016!owm*";		// 엠하우스 시드키. 16자리여야 함.
	private final static String SEED_KEY_TMONEY = "KSCC2016!wer9sm*";		// 티머니 시드키. 16자리여야 함.
	private final static String SEED_KEY_GOOGLEPLAY = "G55GLEPLAY2!2*11";	// 구글Play 시드키. 16자리여야 함.
	private final static String RESULT_PREFIX = "RESULT_";
	
	// 롯데멤버스 제휴
	private final static String STR_LPOINT = "lpoint";
	private final static String LPOINT_IF_NO = "IF_LPOINT_01";
	private final static String LPOINT_FILE_NM = "FTP.M.KTMF.W3.O940.";
	private final static String LPOINT_RCV_FILE_NM = "FTP.M.KTMF.W3.O941.";
	private final static String LPOINT_FILE_EXT = ".01";
	
	public List<PtnrLinkInfoVO> getPtnrLinkLstInfo(PtnrLinkInfoVO vo) throws EgovBizException {
		
		if(vo.getBaseDate() == null || "".equals(vo.getBaseDate())) {
			throw new EgovBizException("연동년월이 존재하지 않습니다.");
		}
		
		return ptnrLinkMapper.getPtnrLinkLstInfo(vo);
	}
	
	public void savePointFile(PtnrRetryPointVO paramVO) throws EgovBizException {
		
		logger.debug("STEP1. 제휴사 정산 파일 저장 [시작]");
		
		List<PtnrRetryPointVO> clist = null;
		// 제휴사 아이디가 있는지 확인
		if(StringUtils.isEmpty(paramVO.getPartnerId())) {
			throw new EgovBizException("정산파일생성 : 제휴사 아이디가 없습니다.");
		}
		
		logger.debug("PtnrRetryPointVO.getPartnerId() = " + paramVO.getPartnerId());
		
		// 제주항공 정산파일 저장
		if(STR_JEJUAIR.equals(paramVO.getPartnerId())) {
			
			// 제주항공 정산내역 가져오기
			clist = ptnrLinkMapper.getJejuPointList(paramVO);
			
			if(!clist.isEmpty()) {
				
				// 제주항공 정산내역 delete
				ptnrLinkMapper.deleteJejuPointList();
				
				// 제주항공 정산내역 insert
				ptnrLinkMapper.insertJejuPointList(paramVO);
			}
			
		// 엠하우스 정산파일 저장
		} else if(STR_GIFTI.equals(paramVO.getPartnerId())) {
			
			// 엠하우스 정산내역 가져오기
			clist = ptnrLinkMapper.getGiftiPointList(paramVO);
			
			if(!clist.isEmpty()) {
				// 엠하우스 정산내역 delete
				ptnrLinkMapper.deleteGiftiPointList();
				
				// 엠하우스 정산내역 insert
				ptnrLinkMapper.insertGiftiPointList(paramVO);
			}
			
		// 티머니 정산파일 저장
		} else if(STR_TMONEY.equals(paramVO.getPartnerId())) {
			
			// 티머니 정산내역 가져오기
			clist = ptnrLinkMapper.getTmoneyPointList(paramVO);
			
			if(!clist.isEmpty()) {
				// 티머니 정산내역 delete
				ptnrLinkMapper.deleteTmoneyPointList();
				
				// 티머니 정산내역 insert
				ptnrLinkMapper.insertTmoneyPointList(paramVO);
			}

		// 구글Play 정산파일 저장
		} else if(STR_GOOGLEPLAY.equals(paramVO.getPartnerId())) {
			
			// 구글Play 정산내역 가져오기
			clist = ptnrLinkMapper.getGooglePlayPointList(paramVO);
			
			if(!clist.isEmpty()) {
				// 구글Play 정산내역 delete
				// insert 문장에서 처리 20201230 송정섭
				//ptnrLinkMapper.deleteGooglePlayPointList();
				
				// 구글Play 정산내역 insert
				ptnrLinkMapper.insertGooglePlayPointList(paramVO);
			}
			
		// 롯데멤버스
		} else if(STR_LPOINT.equals(paramVO.getPartnerId())) {
			
			// 롯데멤버스 정산내역 가져오기
			clist = ptnrLinkMapper.getLPointList(paramVO);
			
			if(!clist.isEmpty()) {
				// 롯데멤버스 정산내역 delete
				ptnrLinkMapper.deleteLPointList();
				
				// 롯데멤버스 정산내역 insert
				ptnrLinkMapper.insertLPointList(paramVO);
			}
		}
		
		if(!clist.isEmpty()) {
			savePointFile(clist, paramVO);
		}else if(STR_GOOGLEPLAY.equals(paramVO.getPartnerId())) {//데이터가 없어도 파일 생성 위한 조치
			
			saveEmptyPointFile(paramVO);
		}
		
		logger.debug("STEP1. 제휴사 정산 파일 저장 [끝]");
		
	}
	
	private void savePointFile(List<PtnrRetryPointVO> list, PtnrRetryPointVO paramVO) throws EgovBizException {
		
		logger.debug("STEP2. 제휴사 정산 파일 쓰기 [시작]");
		
		// 파일명
		String fileName = "";
		
		// 롯데멤버스 
		String sFileName = "";
		
		String lDownBaseDir = propertyService.getString("msp.sftp.local.base.dir");
		String lUpBaseDir = propertyService.getString("msp.sftp.local.base.dir");
		String hDownBaseDir = "";
		String hUpBaseDir = "";
		List<PtnrRetryFileVO> clist = null;
		
		if(STR_JEJUAIR.equals(paramVO.getPartnerId())) {
			// 제주항공 디렉토리 가져오기
			clist = ptnrLinkMapper.getPartnerSubList(JEJUAIR_IF_NO);
		} else if(STR_GIFTI.equals(paramVO.getPartnerId())) {
			// 엠하우스 디렉토리 가져오기
			clist = ptnrLinkMapper.getPartnerSubList(GIFTI_IF_NO);
		} else if(STR_TMONEY.equals(paramVO.getPartnerId())) {
			// 티머니 디렉토리 가져오기
			clist = ptnrLinkMapper.getPartnerSubList(TMONEY_IF_NO);
		} else if(STR_GOOGLEPLAY.equals(paramVO.getPartnerId())) {
			// 구글Play 디렉토리 가져오기
			clist = ptnrLinkMapper.getPartnerSubList(GOOGLEPLAY_IF_NO);
		} else if(STR_LPOINT.equals(paramVO.getPartnerId())) {
			// 롯데멤버스 디렉토리 가져오기
			clist = ptnrLinkMapper.getPartnerSubList(LPOINT_IF_NO);
		}
		
		if(clist.isEmpty()) {
			throw new EgovBizException("인터페이스 정보가 없습니다");
		}
		
		StringBuffer buf3 = new StringBuffer();
		
		for (PtnrRetryFileVO vo : clist) {
			
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
		String lDownDir = lDownBaseDir + paramVO.getPartnerId() + "/" + hDownBaseDir;
		// 서버 다운로드 경로
		String hDownDir = "";
		StringBuffer buf = new StringBuffer();
		buf.append(paramVO.getPartnerId());
		buf.append("/");
		buf.append(hDownBaseDir);
		hDownDir = buf.toString();
		
		// 로컬 업로드 경로
		String lUpDir = lUpBaseDir + paramVO.getPartnerId() + "/" + hUpBaseDir;
		// 서버 업로드 경로 구하기
		StringBuffer buf1 = new StringBuffer();
		buf1.append(paramVO.getPartnerId());
		buf1.append("/");
		buf1.append(hUpBaseDir);
		String hUpDir = buf1.toString();
		
		// 생성날짜 구하기
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		StringBuffer buf2 = new StringBuffer();
		
		if(STR_JEJUAIR.equals(paramVO.getPartnerId())) {
			// 제주항공 디렉토리 가져오기
			buf2.append(JEJUAIR_IF_NO);
		} else if(STR_GIFTI.equals(paramVO.getPartnerId())) {
			// 엠하우스 디렉토리 가져오기
			buf2.append(GIFTI_IF_NO);
		} else if(STR_TMONEY.equals(paramVO.getPartnerId())) {
			// 엠하우스 디렉토리 가져오기
			buf2.append(TMONEY_IF_NO);
		} else if(STR_GOOGLEPLAY.equals(paramVO.getPartnerId())) {
			// 구글Play IF_NO 가져오기
			buf2.append(GOOGLEPLAY_IF_NO);
		}
		
		buf2.append("_");
		buf2.append(dDate);
		buf2.append(FILE_EXT);
		
		if(paramVO.getFileNm() == null || "".equals(paramVO.getFileNm())) {
			if(STR_LPOINT.equals(paramVO.getPartnerId())){
				// 롯데멤버스 파일명 규칙 적용( FTP.D.XXXX.XX.O940.20171017.01 )
				fileName = LPOINT_FILE_NM + dDate;
				sFileName = LPOINT_FILE_NM + dDate + LPOINT_FILE_EXT;
			}else{
				fileName = buf2.toString();
			}
		} else {
			// 롯데멤버스 재처리시 암호화 전 대상 파일이 있어야 하며, 업로드하는 파일명은 원본파일명 유지
			if(STR_LPOINT.equals(paramVO.getPartnerId())){
				fileName = LPOINT_FILE_NM + dDate;
				sFileName = paramVO.getFileNm();
			}else{
				fileName = paramVO.getFileNm();
			}
		}
		
		String str = "";
		
		try{
			File desti = new File(lUpDir);
			
			// 디렉토리가 없다면 생성
			if(!desti.exists()) {
				desti.mkdirs(); 
			}
			
			// BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
			BufferedWriter fw = new BufferedWriter(new FileWriter(lUpDir + fileName, false));
			
			// 롯데멤버스 파일 생성
			if (STR_LPOINT.equals(paramVO.getPartnerId())) {
				
				ptnrLinkMapper.deleteLPointInterface();
				
				String ptnrCd = propertyService.getString("msp.lpoint.partnerId");
				ptnrLinkMapper.insertLpointInterface(ptnrCd);
				
				// header
				String sHeader = ptnrLinkMapper.getLPointReqHeader();
				fw.write(sHeader);
				fw.newLine();
				
				// body
				List<HashMap<String, Object>> lList = ptnrLinkMapper.getLPointReqBody();
				HashMap<String, Object> map = null;
				for(int i=0; i<lList.size(); i++){
					map = (HashMap<String, Object>) lList.get(i);
					fw.write(map.get("BODY_STR").toString());
					fw.newLine();
				}
				
				// footer
				String sFooter = ptnrLinkMapper.getLPointReqTrailer();
				fw.write(sFooter);
				
			}else{
				// 티머니 헤더 생성 (티머니할때 사용) YYYYMMDD(8자리) + 건수(8자리)
				if (STR_TMONEY.equals(paramVO.getPartnerId()) ||
						STR_GOOGLEPLAY.equals(paramVO.getPartnerId())) {
					str = StringUtils.rightPad(dDate, 8);
					fw.write(str);
					fw.newLine();
					str = String.valueOf(list.size());
					str = StringUtils.rightPad(str, 8);
					fw.write(str);
					fw.newLine();
				}
				
				for (PtnrRetryPointVO vo : list) {
					
					vo.setPartnerId(paramVO.getPartnerId());
					
					if(STR_JEJUAIR.equals(paramVO.getPartnerId())) {
						
						// 제주항공 문자열만들기
						str = getStringJeju(vo);
						
					} else if (STR_GIFTI.equals(paramVO.getPartnerId())) {
						
						// 엠하우스 문자열만들기
						str = getStringGifti(vo);
						
					} else if (STR_TMONEY.equals(paramVO.getPartnerId())) {
						
						// 티머니 문자열만들기
						str = getStringTmoney(vo);
						
					} else if (STR_GOOGLEPLAY.equals(paramVO.getPartnerId())) {
						
						// 구글Play 문자열만들기
						vo.setPointType("01");//GOOGLE PLAY
						str = getStringGooglePlay(vo);
					}
					
					fw.write(str);
					fw.newLine();
				}
			}
			fw.flush();
			
			//객체 닫기
			fw.close(); 
			
		} catch (Exception e) {
			throw new EgovBizException("파일 생성에 실패했습니다");
		}
		
		// 롯데멤버스 파일암호화 처리
		// Add External JARs 로 암/복호화 JAR 를 라이브러리에 추가하여 사용.
		try {
			AesFileEncrypto enc = new AesFileEncrypto();
			
			String[] encParam = new String[3];
			
			encParam[0] = (String) propertyService.getString("msp.lpoint.keyfile");
			encParam[1] = lUpDir + fileName;	// 원본파일
			encParam[2] = lUpDir + sFileName;	// 암호화파일(파일명 미입력시 .enc 추가됨)
			
			enc.main(encParam);
			
		} catch (Exception e) {
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}
		
		// 파일생성 후 파일내용을 디비에 저장
		PtnrRetryFileVO fileVo = new PtnrRetryFileVO();
		fileVo.setPartnerId(paramVO.getPartnerId());
		fileVo.setLocalUpDir(lUpDir);
		fileVo.setHostUpDir(hUpDir);
		fileVo.setLocalDownDir(lDownDir);
		fileVo.setHostDownDir(hDownDir);
		if (STR_LPOINT.equals(paramVO.getPartnerId())) {
			fileVo.setFileNm(sFileName);
		}else{
			fileVo.setFileNm(fileName);
		}
		
		if(paramVO.getFileNm() == null || "".equals(paramVO.getFileNm())) {
			ptnrLinkMapper.insertPointFile(fileVo);
		} else {
			ptnrLinkMapper.updatePointFile(fileVo);
		}
		
		logger.debug("STEP2. 제휴사 정산 파일 쓰기 [끝]");
		
	}

	private void saveEmptyPointFile(PtnrRetryPointVO paramVO) throws EgovBizException {
		
		logger.debug("STEP2. 제휴사 정산 파일 쓰기 [시작]");
		
		// 파일명
		String fileName = "";
		
		// 롯데멤버스 
		String sFileName = "";
		
		String lDownBaseDir = propertyService.getString("msp.sftp.local.base.dir");
		String lUpBaseDir = propertyService.getString("msp.sftp.local.base.dir");
		String hDownBaseDir = "";
		String hUpBaseDir = "";
		List<PtnrRetryFileVO> clist = null;
		
		if(STR_GOOGLEPLAY.equals(paramVO.getPartnerId())) {
			// 구글Play 디렉토리 가져오기
			clist = ptnrLinkMapper.getPartnerSubList(GOOGLEPLAY_IF_NO);
		}
		
		if(clist.isEmpty()) {
			throw new EgovBizException("인터페이스 정보가 없습니다");
		}
		
		StringBuffer buf3 = new StringBuffer();
		
		for (PtnrRetryFileVO vo : clist) {
			
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
		String lDownDir = lDownBaseDir + paramVO.getPartnerId() + "/" + hDownBaseDir;
		// 서버 다운로드 경로
		String hDownDir = "";
		StringBuffer buf = new StringBuffer();
		buf.append(paramVO.getPartnerId());
		buf.append("/");
		buf.append(hDownBaseDir);
		hDownDir = buf.toString();
		
		// 로컬 업로드 경로
		String lUpDir = lUpBaseDir + paramVO.getPartnerId() + "/" + hUpBaseDir;
		// 서버 업로드 경로 구하기
		StringBuffer buf1 = new StringBuffer();
		buf1.append(paramVO.getPartnerId());
		buf1.append("/");
		buf1.append(hUpBaseDir);
		String hUpDir = buf1.toString();
		
		// 생성날짜 구하기
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		StringBuffer buf2 = new StringBuffer();
		
		if(STR_GOOGLEPLAY.equals(paramVO.getPartnerId())) {
			// 구글Play IF_NO 가져오기
			buf2.append(GOOGLEPLAY_IF_NO);
		}
		
		buf2.append("_");
		buf2.append(dDate);
		buf2.append(FILE_EXT);
		
		if(paramVO.getFileNm() == null || "".equals(paramVO.getFileNm())) {
			fileName = buf2.toString();
			
		} else {
			fileName = paramVO.getFileNm();
			
		}
		
		String str = "";
		
		try{
			File desti = new File(lUpDir);
			
			// 디렉토리가 없다면 생성
			if(!desti.exists()) {
				desti.mkdirs(); 
			}
			
			// BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
			BufferedWriter fw = new BufferedWriter(new FileWriter(lUpDir + fileName, false));
			
			
			// 티머니 헤더 생성 (티머니할때 사용) YYYYMMDD(8자리) + 건수(8자리)
			if (STR_GOOGLEPLAY.equals(paramVO.getPartnerId())) {
				str = StringUtils.rightPad(dDate, 8);
				fw.write(str);
				fw.newLine();
				str = String.valueOf(0);
				str = StringUtils.rightPad(str, 8);
				fw.write(str);
				fw.newLine();
			}
			
			fw.flush();
			
			//객체 닫기
			fw.close(); 
			
		} catch (Exception e) {
			throw new EgovBizException("파일 생성에 실패했습니다");
		}
		
		// 파일생성 후 파일내용을 디비에 저장
		PtnrRetryFileVO fileVo = new PtnrRetryFileVO();
		fileVo.setPartnerId(paramVO.getPartnerId());
		fileVo.setLocalUpDir(lUpDir);
		fileVo.setHostUpDir(hUpDir);
		fileVo.setLocalDownDir(lDownDir);
		fileVo.setHostDownDir(hDownDir);
		
		fileVo.setFileNm(fileName);
		
		
		if(paramVO.getFileNm() == null || "".equals(paramVO.getFileNm())) {
			ptnrLinkMapper.insertPointFile(fileVo);
		} else {
			ptnrLinkMapper.updatePointFile(fileVo);
		}
		
		logger.debug("STEP2. 제휴사 정산 파일 쓰기 [끝]");
		
	}
	
	private String getStringJeju(PtnrRetryPointVO pointVO) {
		
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
		
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(txt1);
		sbuf.append(txt2);
		sbuf.append(txt3);
		sbuf.append(txt4);
		sbuf.append(txt5);
		sbuf.append(txt6);
		sbuf.append(txt7);
		sbuf.append(txt8);
		
		logger.debug("sbuf.toString() = [" + sbuf.toString() + "]");
		logger.debug("sbuf.toString().length() = [" + sbuf.toString().length() + "]");
		
		return sbuf.toString();
		
	}
	
	// 객체를 받아서 한줄짜리 문자열을 만든다.
	// 바이트단위 전송을 위한 것임. 
	// 엠하우스 연동파일 만들기
	// 전화번호 암호화 필요함.
	// 연동규격서 나오면 다시 작업해야함.
	private String getStringGifti(PtnrRetryPointVO pointVO) {
		
		// 파일생성
		String txt1 = pointVO.getContractNum();
		String txt2 = pointVO.getCtn();
		String txt3 = String.valueOf(pointVO.getPoint());
		String txt4 = pointVO.getUsgYm();
		
		txt2 = encrypt(txt2, SEED_KEY_GIFTI);
		
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
	private String getStringTmoney(PtnrRetryPointVO pointVO) {
		
		// 파일생성
		String txt1 = pointVO.getUicc();
		String txt2 = pointVO.getCtn();
		String txt3 = pointVO.getContractNum();
		String txt4 = String.valueOf(pointVO.getPoint());
		String txt5 = pointVO.getPointType();
		String txt6 = pointVO.getBillYm();
		
		txt2 = encrypt(txt2, SEED_KEY_TMONEY);
		
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

	// 구글Play 연동파일 만들기
	// 전화번호 암호화 필요함.
	private String getStringGooglePlay(PtnrRetryPointVO pointVO) {
		
		// 파일생성
		String txt1 = pointVO.getUicc();
		String txt2 = pointVO.getCtn();
		String txt3 = pointVO.getContractNum();
		String txt4 = String.valueOf(pointVO.getPoint());
		String txt5 = pointVO.getPointType();
		String txt6 = pointVO.getBillYm();
		String txt7 = pointVO.getSvcType();               // 서비스구분(1)   -->(1:부가서비스,2:프로모션,3:일회성구매)
		String txt8 = pointVO.getSvcCd();                 // 서비스코드(10)  -->부가서비스코드,프로모션코드,구매일련번호
		String txt9 = pointVO.getLinkYm();               // 정산월(구매일 + 1: 처리하는 당월)
		
		txt2 = encrypt(txt2, SEED_KEY_GOOGLEPLAY);
		
		txt1 = StringUtils.rightPad(txt1, 40);
		txt2 = StringUtils.rightPad(txt2, 32);
		txt3 = StringUtils.rightPad(txt3, 10);
		txt4 = StringUtils.rightPad(txt4, 15);
		txt5 = StringUtils.rightPad(txt5, 2);
		txt6 = StringUtils.rightPad(txt6, 6);
		txt7 = StringUtils.rightPad(txt7, 1);
		txt8 = StringUtils.rightPad(txt8, 10);
		txt9 = StringUtils.rightPad(txt9, 6);
		
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(txt1);
		sbuf.append(txt2);
		sbuf.append(txt3);
		sbuf.append(txt4);
		sbuf.append(txt5);
		sbuf.append(txt6);
		sbuf.append(txt7);
		sbuf.append(txt8);
		sbuf.append(txt9);
		
		return sbuf.toString();
		
	}
	
	private String encrypt(String plain, String seedKey) {
		
		logger.debug("암호화 시작");
		logger.debug("평문 = [" + plain+"]- len[" + plain.length() + "]");
		SEED seed = new SEED();
		String sPlain = StringUtils.rightPad(plain, 16);		// 평문을 16자리로 맞춰야 함. 16자리가 넘으면 16자리로 잘라서 암호화 해야함.
		
		//--------------------------------------------------//
		// 암호화 모듈 호출  //
		byte[] pbCipher = seed.Encrypt(sPlain, seedKey);		//
		//--------------------------------------------------//
		
		StringBuffer sb = new StringBuffer();
		
		for (int i=0; i<pbCipher.length; i++){
			sb.append(seed.appendLPad(Integer.toHexString(0xff&pbCipher[i]),2,"0"));
		}
		
		logger.debug("16진수 암호화 =["+sb.toString()+"]- len[" + sb.toString().length() +"]");
		logger.debug("암호화 끝");
		
		return sb.toString();
		
	}
	
	private String decrypt(String sHexCipher, String seedKey) {
		
		logger.debug("복호화 시작");
		logger.debug("암호화 = [" + sHexCipher+"]- len[" + sHexCipher.length() + "]");
		SEED seed = new SEED();
		
		byte[] bCipher = seed.GetStringHextoByte(sHexCipher);
		
		//--------------------------------------------------//
		// 복호화 모듈 호출  //
		byte[] pbPlain2 = seed.Decrypt(bCipher, seedKey);	//
		//--------------------------------------------------//
		
		for (int i=0; i<pbPlain2.length; i++){
			seed.appendLPad(Integer.toHexString(0xff&pbPlain2[i]),2,"0");
		}
		
		String temp3 = new String(pbPlain2);
		logger.debug("복호화 = [" + temp3+"]- len["+temp3.length() + "]");
		
		logger.debug("복호화 끝");
		
		return temp3.trim();
		
	}
	
	public void uploadPointFile(PtnrRetryPointVO paramVO) throws EgovBizException {
		logger.debug("STEP3. 제휴사 정산 파일 업로드 [시작]");
		
		// 제휴사 아이디가 있는지 확인 (나중에 제휴사별로 업로드하도록 하자.
		// 현재는 전송할 파일 전부 다 가져오도록 하자)
		if(StringUtils.isEmpty(paramVO.getPartnerId())) {
			throw new EgovBizException("정산파일업로드 : 제휴사 아이디가 없습니다.");
		}
		
		// 업로드할 파일 목록 가져오기
		List<PtnrRetryFileVO> list = ptnrLinkMapper.getPointFileUpList(paramVO);
		
		logger.debug("list.size() = [ " + list.size() + " ]");
		
		// 전송할 파일이 없음
		if(list.isEmpty()) {
			throw new EgovBizException("파일전송 : 전송할 파일이 없습니다.");
		}
		
		try {
		
			// FTP로 파일 전송
			// FTP 접속정보
			
			String upDir = "";
			// ftp 루트 임. 나중에 변경될 수 있음.
			
			SFTPUtil ftp = new SFTPUtil();
			String host = propertyService.getString("msp.sftp.host");
			int port = Integer.parseInt(propertyService.getString("msp.sftp.port"));
			String userName = propertyService.getString("msp.sftp.username");
			String password = propertyService.getString("msp.sftp.pwd");
			ftp.init(host, userName, password, port);
			
			for (PtnrRetryFileVO vo : list) {
				File file = new File(vo.getLocalUpDir() + vo.getFileNm());
				upDir = vo.getHostUpDir();
				
				try {
					ftp.upload(upDir, file);
					vo.setSendFlag("S");
				} catch (SftpException e1) {
					logger.error("파일 업로드중 에러 발생-- 업로드경로 : [" + upDir + "], file = [" + file + "]");
					vo.setSendFlag("F");
					vo.setSendResult("FTP 업로드 에러");
				} catch (FileNotFoundException e2) {
					logger.error("파일 업로드중 에러 발생-- 업로드경로 : [" + upDir + "], file = [" + file + "]");
					vo.setSendFlag("F");
					vo.setSendResult("업로드할 파일이 없습니다");
				} catch (IOException e3) {
					logger.error("파일 업로드중 에러 발생-- 업로드경로 : [" + upDir + "], file = [" + file + "]");
					vo.setSendFlag("F");
					vo.setSendResult("파일 닫기 실패");
				} catch (Exception e) {
					logger.error("파일 업로드중 에러 발생-- 업로드경로 : [" + upDir + "], file = [" + file + "]");
					vo.setSendFlag("F");
					vo.setSendResult("파일 업로드중 에러 발생. 에러로그 확인 바람");
				}
				
				ptnrLinkMapper.updatePointFileUp(vo);
			}
			
			ftp.disconnection();
		
		} catch (JSchException e) {
			throw new EgovBizException("포인트 정산 파일 업로드 중 에러 발생");
		}
		
		logger.debug("STEP3. 제휴사 정산 파일 업로드 [끝]");
	}
	
	public void downloadPointFile(PtnrRetryPointVO paramVO) throws EgovBizException {
		
		logger.debug("STEP1. 제휴사 정산결과 파일 다운로드 [시작]");
		
		// 제휴사 아이디가 있는지 확인 (나중에 제휴사별로 업로드하도록 하자.
		// 현재는 전송할 파일 전부 다 가져오도록 하자)
		if(StringUtils.isEmpty(paramVO.getPartnerId())) {
			throw new EgovBizException("정산파일업로드 : 제휴사 아이디가 없습니다.");
		}
		
		// 다운로드할 제휴사 및 파일명 가져오기
		List<PtnrRetryFileVO> list = ptnrLinkMapper.getPointFileDownList(paramVO);

		logger.debug("list.size() = " + list.size());
		
		// 다운로드할 파일이 없음
		if(list.isEmpty()) {
			throw new EgovBizException("파일다운로드 : 다운로드할 파일이 없음.");
		}
		
		// FTP로 파일 전송
		// FTP 접속정보
		String host = propertyService.getString("msp.sftp.host");
		int port = Integer.parseInt(propertyService.getString("msp.sftp.port"));
		String userName = propertyService.getString("msp.sftp.username");
		String password = propertyService.getString("msp.sftp.pwd");
		
		// ftp 루트 임. 나중에 변경될 수 있음.
		String hDnDir = "";
		String lDnDir = "";
		String dnFileName = "";
		
		try {
		
			SFTPUtil ftp = new SFTPUtil();
			ftp.init(host, userName, password, port);
			
			for (PtnrRetryFileVO vo : list) {
				hDnDir = vo.getHostDownDir();
				
				if(STR_LPOINT.equals(vo.getPartnerId())){
					dnFileName = vo.getFileNm();
				}else{
					dnFileName = RESULT_PREFIX + vo.getFileNm();
				}
				
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
					logger.error("파일 다운로드중 에러 발생1-- 다운로드경로 : [" + hDnDir + "], file = [" + dnFileName + "]");
					vo.setSendResult("F");
				} catch (IOException e2) {
					logger.error("파일 다운로드중 에러 발생2-- 다운로드경로 : [" + hDnDir + "], file = [" + dnFileName + "]");
					vo.setSendResult("F");
				} catch (Exception e) {
					logger.error("파일 다운로드중 에러 발생3-- 다운로드경로 : [" + hDnDir + "], file = [" + dnFileName + "]");
					vo.setSendResult("F");
				}
				

				ptnrLinkMapper.updatePointFileDown(vo);
			}
			
			ftp.disconnection();
			
		} catch (JSchException e) {
			throw new EgovBizException("포인트 정산 파일 다운로드 중 에러 발생");
		}
		
		logger.debug("STEP1. 제휴사 정산결과 파일 다운로드 [끝]");
		
	}
	
	public void readFile(PtnrRetryPointVO paramVO) throws EgovBizException {
		
		logger.debug("STEP2. 제휴사 정산결과 파일 읽기 [시작]");
		
		if(StringUtils.isEmpty(paramVO.getPartnerId())) {
			throw new EgovBizException("정산결과파일읽기 : 제휴사 아이디가 없습니다.");
		}
		
		// 읽어야 할 제휴사 및 파일명 가져오기
		List<PtnrRetryFileVO> list = ptnrLinkMapper.getPointFileReadList(paramVO);

		logger.debug("list.size() = " + list.size());
		
		// 읽을 파일이 없음
		if(list.isEmpty()) {
			throw new EgovBizException("파일읽기 : 읽을 파일이 없습니다. [" + paramVO.getPartnerId() + " , " + paramVO.getFileNm() + "]");
		}
		
		for (PtnrRetryFileVO vo : list) {
			
			logger.debug("읽어야할 제휴사아이디 = [" + vo.getPartnerId() + "], filename = [" + vo.getFileNm() + "]");
			
			String filePath = "";
			
			if(STR_LPOINT.equals(vo.getPartnerId())){
				filePath = vo.getLocalDownDir() + vo.getFileNm();
			}else{
				filePath = vo.getLocalDownDir() + RESULT_PREFIX + vo.getFileNm();
			}
			
			File f = new File(filePath);
			
			// 파일이 존재하는지 확인
			if(!f.isFile()) {
				throw new EgovBizException("파일이 존재하지 않습니다. [" + paramVO.getPartnerId() + " , " + filePath + "]");
			}
		}
		
		if(STR_JEJUAIR.equals(paramVO.getPartnerId())) {
			// 제주항공 파일 읽기
			readFileJeju(list);
		} else if (STR_GIFTI.equals(paramVO.getPartnerId())) {
			// 엠하우스 파일 읽기
			readFileGifti(list);
		} else if (STR_TMONEY.equals(paramVO.getPartnerId())) {
			// 티머니 파일 읽기
			readFileTmoney(list);
		} else if (STR_GOOGLEPLAY.equals(paramVO.getPartnerId())) {
			// 구글Play 파일 읽기
			readFileGooglePlay(list);
		} else if (STR_LPOINT.equals(paramVO.getPartnerId())) {
			// 롯데멤버스 파일 읽기
			readFileLpoint(list);
		}
		
		logger.debug("STEP2. 제휴사 정산결과 파일 읽기 [끝]");
	}
	
	private void readFileJeju(List<PtnrRetryFileVO> list) throws EgovBizException {
		
		logger.debug("STEP3-1. 제주항공 정산결과 파일 읽기 [시작]");
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		String downloadFileName = "";
		
		// 파일 읽기
		try {
			
			for (PtnrRetryFileVO vo : list) {
				downloadFileName = vo.getLocalDownDir() + RESULT_PREFIX + vo.getFileNm();
				
				try {
					BufferedReader in = new BufferedReader(new FileReader(downloadFileName));
					String s;
					
					while ((s = in.readLine()) != null) {
						// byte 단위로 읽기
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
						
						// 지급결과 DB 업데이트
						PtnrRetryPointVO vo1 = new PtnrRetryPointVO();
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
						ptnrLinkMapper.updateJejuPointMst(vo1);
						ptnrLinkMapper.updatePartnerJejuair(vo1);
						
					}
					vo.setSendResult("S");
					ptnrLinkMapper.updatePointFileDown(vo);
					in.close();
				} catch (Exception e1) {
					logger.error("제주항공 파일읽기에 실패했습니다. 파일명 = [" + downloadFileName + "]");
					continue;
				}
			}
			
		} catch (Exception e) {
			throw new EgovBizException("제주항공 파일읽기에 실패했습니다. 파일명 = [" + downloadFileName + "]");
		}
		
		logger.debug("STEP3-1. 제주항공 정산결과 파일 읽기 [끝]");
		
	}
	
	private void readFileGifti(List<PtnrRetryFileVO> list) throws EgovBizException {
		
		logger.debug("STEP3-2. 엠하우스 정산결과 파일 읽기 [시작]");
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		String downloadFileName = "";
		
		// 파일 읽기
		try {
			
			for (PtnrRetryFileVO vo : list) {
				
				downloadFileName = vo.getLocalDownDir() + RESULT_PREFIX + vo.getFileNm();
				
				try {
					BufferedReader in = new BufferedReader(new FileReader(downloadFileName));
					String s;
					
					while ((s = in.readLine()) != null) {
						// byte 단위로 읽기
						byte[] b = s.getBytes();
						String contractNum = new String(b, 0, 10);
						String ctn = new String(b, 10, 32);
						String point = new String(b, 42, 12);
						String usgYm = new String(b,54, 6);
						String responseCode = new String(b, 60, 4);
						
						// 전화번호 복호화
						ctn = decrypt(ctn.trim(), SEED_KEY_GIFTI);
						
						// 지급결과 DB 업데이트
						PtnrRetryPointVO vo1 = new PtnrRetryPointVO();
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
						ptnrLinkMapper.updateGiftiPointMst(vo1);
						ptnrLinkMapper.updatePartnerGifti(vo1);
						
					}
					vo.setSendResult("S");
					ptnrLinkMapper.updatePointFileDown(vo);
					in.close();
				} catch (Exception e1) {
					logger.error("엠하우스 파일읽기에 실패했습니다. 파일명 = [" + downloadFileName + "]");
					continue;
				}
			}
		} catch (Exception e) {
			throw new EgovBizException("엠하우스 파일읽기에 실패했습니다. 파일명 = [" + downloadFileName + "]");
		}
		
		logger.debug("STEP3-2. 엠하우스 정산결과 파일 읽기 [끝]");
	}
	
	private void readFileTmoney(List<PtnrRetryFileVO> list) throws EgovBizException {
		
		logger.debug("STEP3-3. 티머니 정산결과 파일 읽기 [시작]");
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		String downloadFileName = "";
		
		// 파일 읽기
		try {
			
			for (PtnrRetryFileVO vo : list) {
				downloadFileName = vo.getLocalDownDir() + RESULT_PREFIX + vo.getFileNm();
				
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
						byte[] b = s.getBytes();
						String uicc = new String(b, 0, 40);
						String ctn = new String(b, 40, 32);
						String contractNum = new String(b, 72, 10);
						String point = new String(b, 82, 15);
						String pointType = new String(b, 97, 2);
						String billYm = new String(b, 99, 6);
						String responseCode = new String(b, 105, 2);
						
						// 전화번호 복호화
						ctn = decrypt(ctn.trim(), SEED_KEY_TMONEY);
						
						// 지급결과 DB 업데이트
						PtnrRetryPointVO vo1 = new PtnrRetryPointVO();
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
						ptnrLinkMapper.updateTmoneyPointMst(vo1);
						ptnrLinkMapper.updatePartnerTmoney(vo1);
						
					}
					vo.setSendResult("S");
					ptnrLinkMapper.updatePointFileDown(vo);
					in.close();
				} catch (Exception e1) {
					logger.error("티머니 파일읽기에 실패했습니다. 파일명 = [" + downloadFileName + "]");
					continue;
				}
			}
		} catch (Exception e) {
			throw new EgovBizException("티머니 파일읽기에 실패했습니다. 파일명 = [" + downloadFileName + "]");
		}
		
		logger.debug("STEP3-3. 티머니 정산결과 파일 읽기 [끝]");
	}

	private void readFileGooglePlay(List<PtnrRetryFileVO> list) throws EgovBizException {
		
		logger.debug("STEP3-3. 구글Play 정산결과 파일 읽기 [시작]");
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		String downloadFileName = "";
		
		// 파일 읽기
		try {
			
			for (PtnrRetryFileVO vo : list) {
				downloadFileName = vo.getLocalDownDir() + RESULT_PREFIX + vo.getFileNm();
				
				try {
					BufferedReader in = new BufferedReader(new FileReader(downloadFileName));
					String s;
					int count = 0;
					String linkYm = "";

					while ((s = in.readLine()) != null) {
						// 헤더부분 건너뛰기
						if(count == 0) {
							byte[] b = s.getBytes();
							linkYm = new String(b, 0, 6);
						}
						if(count < 2) {
							count++;
							continue;
						}
						// byte 단위로 읽기
						byte[] b = s.getBytes();
						String uicc = new String(b, 0, 40);
						String ctn = new String(b, 40, 32);
						String contractNum = new String(b, 72, 10);
						String point = new String(b, 82, 15);
						String pointType = new String(b, 97, 2);
						String billYm = new String(b, 99, 6);
						String svcType = new String(b, 105, 1);
						String svcCd = new String(b, 106, 10);
						String responseCode = new String(b, 116, 2);
						String dealNum = new String(b, 118, 20);
						
						// 전화번호 복호화
						ctn = decrypt(ctn.trim(), SEED_KEY_GOOGLEPLAY);
						
						// 지급결과 DB 업데이트
						PtnrRetryPointVO vo1 = new PtnrRetryPointVO();
						vo1.setPartnerId(vo.getPartnerId().trim());
						vo1.setUicc(uicc.trim());
						vo1.setCtn(ctn.trim());
						vo1.setContractNum(contractNum.trim());
						vo1.setPoint(Integer.parseInt(point.trim()));
						vo1.setPointType(pointType.trim());
						vo1.setBillYm(billYm);
						vo1.setResponseCode(responseCode.trim());
						vo1.setSvcType(svcType.trim());
						vo1.setSvcCd(svcCd.trim());
						vo1.setDealNum(dealNum.trim());

						if("00".equals(responseCode.trim())) {
							vo1.setPayResult("S");
						} else {
							vo1.setPayResult("F");
						}
						vo1.setLinkYm(linkYm.trim()); //재처리는 당일만 가능이므로  dDate는 오늘일자임
						ptnrLinkMapper.updateGooglePlayPointMst(vo1);
						ptnrLinkMapper.updatePartnerGooglePlay(vo1);
						
					}
					vo.setSendResult("S");
					ptnrLinkMapper.updatePointFileDown(vo);
					in.close();
				} catch (Exception e1) {
					logger.error("구글Play 파일읽기에 실패했습니다. 파일명 = [" + downloadFileName + "]");
					continue;
				}
			}
		} catch (Exception e) {
			throw new EgovBizException("구글Play 파일읽기에 실패했습니다. 파일명 = [" + downloadFileName + "]");
		}
		
		logger.debug("STEP3-3. 구글Play 정산결과 파일 읽기 [끝]");
	}
	
	private void readFileLpoint(List<PtnrRetryFileVO> list) throws EgovBizException {
		
		logger.debug("STEP3-4. 롯데멤버스 정산결과 파일 읽기 [시작]");
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		String downloadFileName = "";
		String readFileName = "";
		
		// 파일 읽기
		try {
			
			for (PtnrRetryFileVO vo : list) {
				downloadFileName = vo.getLocalDownDir() + vo.getFileNm();
				readFileName = vo.getLocalDownDir() + LPOINT_RCV_FILE_NM + dDate;
				
				try {
					// 복호화 처리
					AesFileDecrypto dec = new AesFileDecrypto();
					
					String[] decParam = new String[3];
					decParam[0] = (String) propertyService.getString("msp.lpoint.keyfile");
					decParam[1] = downloadFileName;	// 원본파일
					decParam[2] = readFileName;		// 복호화파일(파일명 미입력시 .dec 추가됨)
					
					dec.main(decParam);
					
					BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(readFileName),"EUC-KR"));
					String s;
					
					while ((s = in.readLine()) != null) {
						// byte 단위로 읽기
						//String s = "가나123다55";
						byte[] b = s.getBytes();
						String data01 = new String(b, 0, 2);
						String data07 = new String(b, 71, 19);	// 승인번호, 고객ID
						String data63 = new String(b, 878, 10);	// 고객번호
						String data64 = new String(b, 888, 9);	// 승인번호
						String data65 = new String(b, 897, 8);	// 승인일자
						String data66 = new String(b, 905, 6);	// 승인시간
						String data67 = new String(b, 911, 9);	// 생성포인트
						String data68 = new String(b, 920, 9);	// 운영사포인트
						String data69 = new String(b, 929, 2);	// 응답코드
						String data70 = new String(b, 931, 64);	// 응답메시지
						
						// 자료구분 "BD"인 경우 데이터 처리
						if(data01.equals("BD")){
							
							// 지급결과 DB 업데이트
							PtnrLPointInterfaceVO lPoint = new PtnrLPointInterfaceVO();
							
							lPoint.setLinkYm(dDate.substring(0, 6));
							lPoint.setCustomerId(data07.trim());
							lPoint.setResCustNo(data63.trim());
							lPoint.setResAprvNo(data64.trim());
							lPoint.setResAprvDt(data65.trim());
							lPoint.setResAprvTm(data66.trim());
							lPoint.setResAprvPoint(data67.trim());
							lPoint.setResEvntPoint(data68.trim());
							lPoint.setResCd(data69.trim());
							lPoint.setResMsg(data70.trim());
							lPoint.setPartnerId(STR_LPOINT);
							
							if("00".equals(data69)) {
								lPoint.setPayResult("S");
							} else {
								lPoint.setPayResult("F");
							}
							
							ptnrLinkMapper.updateLPointInterface(lPoint);
							
							ptnrLinkMapper.updatePartnerLPoint(lPoint);
							
							ptnrLinkMapper.updateLPointPointMst(lPoint);
						}
					}
					vo.setSendResult("S");
					ptnrLinkMapper.updatePointFileDown(vo);
					in.close();
				} catch (Exception e1) {
					logger.error("파일읽기에 실패했습니다. 파일명 = [{}]", readFileName);
					continue;
				}
			}
			
		} catch (Exception e) {
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
			logger.error("파일 읽기에 실패했습니다");
		}
		
		logger.debug("STEP3-4. 롯데멤버스 정산결과 파일 읽기 [끝]");
	}
}
