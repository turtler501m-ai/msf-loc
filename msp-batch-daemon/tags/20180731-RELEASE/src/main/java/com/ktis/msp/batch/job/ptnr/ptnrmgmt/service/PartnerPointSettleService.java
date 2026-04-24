package com.ktis.msp.batch.job.ptnr.ptnrmgmt.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
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
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.example.SEED;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.PartnerPointSettleMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.DongbuPointVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.GiftiPointVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.JejuairPointVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.LPointVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.TmoneyPointVO;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;

import crypto.AesFileDecrypto;
import crypto.AesFileEncrypto;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class PartnerPointSettleService extends BaseService {
	
	@Autowired
	private PartnerPointSettleMapper mapper;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
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
	public void callPartnerUsgPoint(Map<String, Object> param) throws MvnoServiceException {
		LOGGER.debug("제휴사 사용량 및 포인트 계산하기 시작..................................");
		LOGGER.debug("param=" + param);
		
		// 제휴사 아이디가 있는지 확인
		/*if(StringUtils.isEmpty(partnerId)) {
			LOGGER.error("정산파일생성 : 제휴사 아이디가 없습니다.");
			return;
		}*/
		
		// 사용량으로 포인트 계산하기
//		PointFileVO  vo = new PointFileVO();
//		vo.setUsgYm((String)param.get("usgYm"));
		try {
			mapper.callPartnerUsgPoint(param);
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
	public void callPartnerPointPymnCheck(Map<String, Object> param) throws MvnoServiceException {
		LOGGER.debug("제휴사 청구/수납 확인 시작..................................");
		LOGGER.debug("param=" + param);
		
//		// 제휴사 청구/수납 확인
//		PointFileVO  vo = new PointFileVO();
//		vo.setBillYm((String)param.get("billYm"));
		
		try {
			mapper.callPartnerPointPymnCheck(param);
		} catch(Exception e) {
			throw new MvnoServiceException("EPTNR1002", e);
		}
		
		LOGGER.debug("제휴사 청구/수납 확인 끝..................................");
		
	}
	
	/**
	 * 제휴사 정산 파일 생성
	 * @param searchVO
	 * <li>partnerId=제휴사아이디</li>
	 * @return
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
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
		}
		// 엠하우스 정산파일 저장
		else if((PointConstants.STR_GIFTI).equals(partnerId)) {
			GiftiPointVO vo = new GiftiPointVO();
			vo.setPartnerId(partnerId);
			
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
		} 
		// 티머니 정산파일 저장
		else if((PointConstants.STR_TMONEY).equals(partnerId)) {
			TmoneyPointVO vo = new TmoneyPointVO();
			vo.setPartnerId(partnerId);
			
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
		// 동부화재 가입자정보 
		else if((PointConstants.STR_DONGBU).equals(partnerId)) {
			DongbuPointVO vo = new DongbuPointVO();
			vo.setPartnerId(partnerId);
			
			try {
				LOGGER.debug("동부화재 가입자정보 조회...........................");

				LOGGER.info("============== 동부화재 연동 데이터 출력 시작 ==============");
				List<?> tmp = mapper.selectPartnerDongbuHist(vo);
				if(!tmp.isEmpty()) {
					for(int i=0; i<tmp.size(); i++) {
						EgovMap map = new EgovMap();
						map = (EgovMap)tmp.get(i);
						if(!map.isEmpty()) {
							LOGGER.info("data = {}", map.toString());
						} else {
							LOGGER.info("data is null");
						}
						map = null;
					}
				}
				LOGGER.info("============== 동부화재 연동 데이터 출력 종료 ==============");
				
				// 동부화재 가입자정보 연동 이력 생성
				mapper.insertPartnerDongbuHist(vo);
				
				// 동부화재 가입자정보 연동 목록 조회
				clist = mapper.getInsrMemberMstList(vo);
			} catch(Exception e) {
				throw new MvnoServiceException("EPTNR1014", errParam, e);
			}
		}
		// 롯데멤버스 제휴
		else if((PointConstants.STR_LPOINT).equals(partnerId)) {
			LPointVO vo = new LPointVO();
			vo.setPartnerId(partnerId);
			
			try {
				// 롯데멤버스 정산내역 가져오기
				clist = mapper.getLPointList(vo);
				if(!clist.isEmpty()) {
					// 롯데멤버스 연동내역 insert
					mapper.insertLPointList(vo);
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
	@SuppressWarnings("static-access")
	@Transactional(rollbackFor=Exception.class)
	private void savePointFile(List<?> list, String partnerId) throws MvnoServiceException {
		
		LOGGER.debug("제휴사 정산 파일 쓰기 시작..................................");
		ObjectMapper op = new ObjectMapper();
		// 파일명
		String fileName = "";
		// 롯데멤버스 
		String sFileName = "";
//		String partnerId = pointVO.getPartnerId();
		
		String lDownBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String lUpBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String hDownBaseDir = "";
		String hUpBaseDir = "";
		List<?> clist = null;
		
		// 제주항공 디렉토리 가져오기
		if((PointConstants.STR_JEJUAIR).equals(partnerId)) {
			clist = mapper.getPartnerSubList(PointConstants.JEJUAIR_IF_NO);
		}
		// 엠하우스 디렉토리 가져오기
		else if((PointConstants.STR_GIFTI).equals(partnerId)) {
			clist = mapper.getPartnerSubList(PointConstants.GIFTI_IF_NO);
		}
		// 티머니 디렉토리 가져오기
		else if((PointConstants.STR_TMONEY).equals(partnerId)) {
			clist = mapper.getPartnerSubList(PointConstants.TMONEY_IF_NO);
		} 
		// 동부화재 디렉토리 가져오기
		else if((PointConstants.STR_DONGBU).equals(partnerId)) {
			clist = mapper.getPartnerSubList(PointConstants.DONGBU_IF_NO);
		}
		// 롯데멤버스 디렉토리 가져오기
		else if((PointConstants.STR_LPOINT).equals(partnerId)) {
			clist = mapper.getPartnerSubList(PointConstants.LPOINT_IF_NO);
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
		} else if((PointConstants.STR_DONGBU).equals(partnerId)) {
			// 동부화재 디렉토리 가져오기
			buf2.append(PointConstants.DONGBU_IF_NO);
		}
		
		buf2.append("_");
		buf2.append(dDate);
		buf2.append(PointConstants.FILE_EXT);
		
		if(PointConstants.STR_LPOINT.equals(partnerId)){
			// 롯데멤버스 파일명 규칙 적용( FTP.D.XXXX.XX.O940.20171017.01 )
			
			fileName = PointConstants.LPOINT_SND_FILE_NM + dDate;
			sFileName = PointConstants.LPOINT_SND_FILE_NM + dDate + PointConstants.LPOINT_FILE_EXT;
		}else{
			fileName = buf2.toString();
		}
		
		LOGGER.debug("정산요청 파일명 =" + fileName);
		
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
			// 제주항공 파일 생성
			if((PointConstants.STR_JEJUAIR).equals(partnerId)) {
				for (Object fromValue : list) {
					JejuairPointVO vo = op.convertValue(fromValue, JejuairPointVO.class);
					
					// 문자열만들기
					str = getStringJeju(vo);
					
					fw.write(str);
					fw.newLine();
				}
			}
			// 엠하우스 파일 생성
			else if ((PointConstants.STR_GIFTI).equals(partnerId)) {
				for (Object fromValue : list) {
					GiftiPointVO vo = op.convertValue(fromValue, GiftiPointVO.class);
					
					// 문자열만들기
					str = getStringGifti(vo);
					
					fw.write(str);
					fw.newLine();
				}
			}
			// 티머니 파일 생성
			else if ((PointConstants.STR_TMONEY).equals(partnerId)) {
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
			// 동부화재 파일 생성
			else if ((PointConstants.STR_DONGBU).equals(partnerId)) {
				for (Object fromValue : list) {
					DongbuPointVO vo = op.convertValue(fromValue, DongbuPointVO.class);
					
					// 문자열만들기
					str = getStringDongbu(vo);
					
					fw.write(str);
					fw.newLine();
				}
			}
			// 롯데멤버스 파일 생성
			// 롯데멤버스는 파일 암호화를 이용하므로 평문을 이용하여 파일을 생성한다.
			else if ((PointConstants.STR_LPOINT).equals(partnerId)) {
				// 롯데멤버스 연동내역 생성
				String ptnrCd = propertiesService.getString("msp.lpoint.partnerId");
				mapper.insertLpointInterface(ptnrCd);
				
				// header
				List<?> lList = mapper.getLPointReqHeader();
				Map map = (Map) lList.get(0);
				
				LOGGER.debug("HEADER=" + map.get("HEADER_STR"));
				
				fw.write(map.get("HEADER_STR").toString());
				fw.newLine();
				
				// body
				lList = mapper.getLPointReqBody();
				for(int i=0; i<lList.size(); i++){
					map = (Map) lList.get(i);
					fw.write(map.get("BODY_STR").toString());
					fw.newLine();
				}
				
				// footer
				lList = mapper.getLPointReqTrailer();
				map = (Map) lList.get(0);
				
				fw.write(map.get("TRAILER_STR").toString());
			}
			
			fw.flush();
			
			//객체 닫기
			fw.close(); 
			
		} catch (Exception e) {
			throw new MvnoServiceException("EPTNR1013", e);
		}
		
		// 2017-11-17
		// 롯데멤버스 파일암호화 처리
		// Add External JARs 로 암/복호화 JAR 를 라이브러리에 추가하여 사용.
		try {
			AesFileEncrypto enc = new AesFileEncrypto();
			
			String[] encParam = new String[3];
			
			encParam[0] = (String) propertiesService.getString("msp.lpoint.keyfile");
			encParam[1] = lUpDir + fileName;	// 원본파일
			encParam[2] = lUpDir + sFileName;	// 암호화파일(파일명 미입력시 .enc 추가됨)
			
			enc.main(encParam);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 파일생성 후 파일정보를 디비에 저장
		PointFileVO fileVo = new PointFileVO();
		fileVo.setPartnerId(partnerId);
		fileVo.setLocalUpDir(lUpDir);
		fileVo.setHostUpDir(hUpDir);
		fileVo.setLocalDownDir(lDownDir);
		fileVo.setHostDownDir(hDownDir);
		// 롯데멤버스 연동인 경우 암호화파일명으로 변경
		if((PointConstants.STR_LPOINT).equals(partnerId)){
			fileVo.setFileNm(sFileName);
		}else{
			fileVo.setFileNm(fileName);
		}
		
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
			} else if ((PointConstants.STR_LPOINT).equals(partnerId)) {
				// 롯데멤버스 파일 읽기
				readFileLPoint(pointFileVO);
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
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("파일 읽기에 실패했습니다");
		}
		
		
		LOGGER.debug("제휴사 정산결과 파일 읽기 끝..................................");
	}
	
	/**
	 * 
	 * @param pointFileVO
	 */
	public void readFileLPoint(PointFileVO pointFileVO) {
		
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
			
			LOGGER.debug("읽어야할 제휴사아이디 = [{}], path=[{}], filename = [{}]", vo.getPartnerId(), vo.getLocalDownDir(), vo.getFileNm());
			
			File f = new File(vo.getLocalDownDir() + vo.getFileNm());
			
			// 파일이 존재하는지 확인
			if(f.isFile()) {
				fileList.add(vo);
			}
		}
		
		LOGGER.debug("fileList.size() = {}", fileList.size());
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		String downloadFileName = "";
		String readFileName = "";
		
		// 파일 읽기
		try {
			
			for (PointFileVO vo : fileList) {
				downloadFileName = vo.getLocalDownDir() + vo.getFileNm();
				readFileName = vo.getLocalDownDir() + PointConstants.LPOINT_RCV_FILE_NM + dDate;
				
//				LOGGER.debug("downloadFileName=" + downloadFileName);
//				LOGGER.debug("readFileName=" + readFileName);
				
				try {
					// 복호화 처리
					AesFileDecrypto dec = new AesFileDecrypto();
					
					String[] decParam = new String[3];
					decParam[0] = (String) propertiesService.getString("msp.lpoint.keyfile");
					decParam[1] = downloadFileName;	// 원본파일
					decParam[2] = readFileName;	// 복호화파일(파일명 미입력시 .dec 추가됨)
					
					dec.main(decParam);
					
					// 롯데멤버스 EUC-KR 사용
					BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(readFileName),"EUC-KR"));
					String s;
					
					
					File f = new File(readFileName);
					LOGGER.debug("파일유무=" + f.exists());
					
					while ((s = in.readLine()) != null) {
						// byte 단위로 읽기
						//String s = "가나123다55";
						byte[] b = s.getBytes();
						String data01 = new String(b, 0, 2);	// 자료구분
//						String data02 = new String(b, 2, 1);	// 승인요청방식
//						String data03 = new String(b, 3, 1);	// 고객식별구분코드
//						String data04 = new String(b, 4, 37);	// 카드번호
//						String data05 = new String(b, 41, 20);	// 고객식별값
//						String data06 = new String(b, 61, 10);	// 가맹점번호
						String data07 = new String(b, 71, 19);	// 승인번호, 고객ID
//						String data08 = new String(b, 90, 8);	// 거래일자
//						String data09 = new String(b, 98, 6);	// 거래시간
//						String data10 = new String(b, 104, 2);	// 거래구분
//						String data11 = new String(b, 106, 3);	// 거래사유
//						String data12 = new String(b, 109, 1);	// 적립구분
//						String data13 = new String(b, 110, 1);	// 거래요청방식
//						String data14 = new String(b, 111, 1);	// 포인트적립구분
//						String data15 = new String(b, 112, 12);	// 총매출금액
//						String data16 = new String(b, 124, 12);	// 포인트대상금액
//						String data17 = new String(b, 136, 12);	// 현금매출금액
//						String data18 = new String(b, 148, 12);	// 신용카드매출금액
//						String data19 = new String(b, 160, 12);	// 상품권매출금액
//						String data20 = new String(b, 172, 12);	// 포인트매출금액
//						String data21 = new String(b, 184, 12);	// 기타매출금액
//						String data22 = new String(b, 196, 9);	// 총생성포인트
//						String data23 = new String(b, 205, 9);	// 생성포인트(현금)
//						String data24 = new String(b, 214, 9);	// 생성포인트(카드)
//						String data25 = new String(b, 223, 9);	// 생성포인트(상품권)
//						String data26 = new String(b, 232, 9);	// 생성포인트(포인트)
//						String data27 = new String(b, 241, 9);	// 생성포인트(기타)
//						String data28 = new String(b, 250, 4);	// 쿠폰번호
//						String data29 = new String(b, 254, 1);	// 반품구분
//						String data30 = new String(b, 255, 1);	// 원거래정보유무
//						String data31 = new String(b, 256, 1);	// 원거래정보구분
//						String data32 = new String(b, 257, 19);	// 원거래정보승인번호
//						String data33 = new String(b, 276, 8);	// 원거래일자
//						String data34 = new String(b, 284, 40);	// 거래요청정보
//						String data35 = new String(b, 324, 20);	// 결제카드번호1
//						String data36 = new String(b, 344, 12);	// 카드매출금액1
//						String data37 = new String(b, 356, 20);	// 결제카드번호2
//						String data38 = new String(b, 376, 12);	// 카드매출금액2
//						String data39 = new String(b, 388, 20);	// 결제카드번호3
//						String data40 = new String(b, 408, 12);	// 카드매출금액3
//						String data41 = new String(b, 420, 20);	// 카드매출금액4
//						String data42 = new String(b, 440, 12);	// 카드매출금액4
//						String data43 = new String(b, 452, 20);	// 카드매출금액5
//						String data44 = new String(b, 472, 12);	// 카드매출금액5
//						String data45 = new String(b, 484, 20);	// 카드매출금액6
//						String data46 = new String(b, 504, 12);	// 카드매출금액6
//						String data47 = new String(b, 516, 20);	// 카드매출금액7
//						String data48 = new String(b, 536, 12);	// 카드매출금액7
//						String data49 = new String(b, 548, 20);	// 카드매출금액8
//						String data50 = new String(b, 568, 12);	// 카드매출금액8
//						String data51 = new String(b, 580, 20);	// 카드매출금액9
//						String data52 = new String(b, 600, 12);	// 카드매출금액9
//						String data53 = new String(b, 612, 10);	// 이벤트코드1
//						String data54 = new String(b, 622, 100);// 이벤트명1
//						String data55 = new String(b, 722, 12);	// 매출금액1
//						String data56 = new String(b, 734, 9);	// 생성포인트1
//						String data57 = new String(b, 743, 2);	// 유효기간1
//						String data58 = new String(b, 745, 10);	// 이벤트코드2
//						String data59 = new String(b, 755, 100);// 이벤트명2
//						String data60 = new String(b, 855, 12);	// 매출금액2
//						String data61 = new String(b, 867, 9);	// 생성포인트2
//						String data62 = new String(b, 876, 2);	// 유효기간2
						String data63 = new String(b, 878, 10);	// 고객번호
						String data64 = new String(b, 888, 9);	// 승인번호
						String data65 = new String(b, 897, 8);	// 승인일자
						String data66 = new String(b, 905, 6);	// 승인시간
						String data67 = new String(b, 911, 9);	// 생성포인트
						String data68 = new String(b, 920, 9);	// 운영사포인트
						String data69 = new String(b, 929, 2);	// 응답코드
						String data70 = new String(b, 931, 64);	// 응답메시지
//						String data71 = new String(b, 995, 88);	// CI
//						String data72 = new String(b, 1083, 12);// FILLER
						
						// 자료구분 "BD"인 경우 데이터 처리
						if(data01.equals("BD")){
							
							// 지급결과 DB 업데이트
							LPointVO vo1 = new LPointVO();
							vo1.setLinkYm(dDate.substring(0, 6));
							vo1.setCustomerId(data07.trim());
							vo1.setResCustNo(data63.trim());
							vo1.setResAprvNo(data64.trim());
							vo1.setResAprvDt(data65.trim());
							vo1.setResAprvTm(data66.trim());
							vo1.setResAprvPoint(data67.trim());
							vo1.setResEvntPoint(data68.trim());
							vo1.setResCd(data69.trim());
							vo1.setResMsg(data70.trim());
							vo1.setPartnerId("lpoint");
							if("00".equals(data69)) {
								vo1.setPayResult("S");
							} else {
								vo1.setPayResult("F");
							}
							
							// MSP_LPOINT_INTERFACE UPDATE
							mapper.updateLPointInterface(vo1);
							
							// MSP_PARTNER_LPOINT_01 UPDATE
							mapper.updatePartnerLPoint(vo1);
							
							// MSP_PARTNER_POINT_MST UPDATE
							mapper.updateLPointPointMst(vo1);
						}
					}
					vo.setSendResult("S");
					mapper.updatePointFileDown(vo);
					in.close();
				} catch (Exception e1) {
					LOGGER.error("e=" + e1);
					LOGGER.error("파일읽기에 실패했습니다. 파일명 = [{}]", readFileName);
					continue;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("파일 읽기에 실패했습니다");
		}
		
		LOGGER.debug("제휴사 정산결과 파일 읽기 끝..................................");
	}

	private String getStringJeju(JejuairPointVO pointVO) {
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
		
		LOGGER.debug("sbuf.toString() = [{}]", sbuf.toString());
		LOGGER.debug("sbuf.toString().length() = [{}]", sbuf.toString().length());
		
		return sbuf.toString();
	}
	
	// 객체를 받아서 한줄짜리 문자열을 만든다.
	// 바이트단위 전송을 위한 것임. 
	// 엠하우스 연동파일 만들기
	// 전화번호 암호화 필요함.
	// 연동규격서 나오면 다시 작업해야함.
	private String getStringGifti(GiftiPointVO pointVO) {
		
		// 파일생성
		String txt1 = pointVO.getContractNum();           // 계약번호
		String txt2 = pointVO.getCtn();                   // CTN
		String txt3 = String.valueOf(pointVO.getPoint()); // 지급포인트
		String txt4 = pointVO.getUsgYm();                 // 사용월
		
		txt2 = encrypt(txt2, PointConstants.SEED_KEY_GIFTI); // CTN 암호화
		
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
		String txt1 = pointVO.getUicc();                  // 유심일련번호
		String txt2 = pointVO.getCtn();                   // CTN
		String txt3 = pointVO.getContractNum();           // 계약번호
		String txt4 = String.valueOf(pointVO.getPoint()); // 지급포인트
		String txt5 = pointVO.getPointType();             // 포인트유형
		String txt6 = pointVO.getBillYm();                // 청구월
		
		txt2 = encrypt(txt2, PointConstants.SEED_KEY_TMONEY); // CTN 암호화

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
	
	// 객체를 받아서 한줄짜리 문자열을 만든다.
	// 바이트단위 전송을 위한 것임. 
	// 엠하우스 연동파일 만들기
	// 전화번호 암호화 필요함.
	// 연동규격서 나오면 다시 작업해야함.
	private String getStringDongbu(DongbuPointVO vo) {
		
		// 파일생성
		String txt1 = vo.getCustNm();         // 고객명
		String txt2 = vo.getCtn();            // CTN
		String txt3 = vo.getMinorAgentNm();   // 법정대리인명
		String txt4 = vo.getMinorAgentTel();  // 법정대리인연락처
		String txt5 = vo.getSubStatus();      // 계약상태
		String txt6 = vo.getJoinDt();         // 요금제가입일
		String txt7 = vo.getChangeDt();       // 계약변경일
		String txt8 = vo.getEfctStrtDt();     // 유효시작일
		String txt9 = vo.getRateNm();         // 요금제
		String txt10 = vo.getOpenAgntNm();    // 개통대리점
		String txt11 = vo.getPymnYn();        // 수납여부
		String txt12 = vo.getBirthDt();       // 생년월일
		
		txt1 = encrypt(txt1, PointConstants.SEED_KEY_DONGBU);                       // 고객명 암호화 
		txt2 = encrypt(txt2, PointConstants.SEED_KEY_DONGBU);                       // CTN 암호화
		if(!" ".equals(txt3)) txt3 = encrypt(txt3, PointConstants.SEED_KEY_DONGBU); // 대리인명 암호화
		if(!" ".equals(txt4)) txt4 = encrypt(txt4, PointConstants.SEED_KEY_DONGBU); // 대리인연락처 암호화
		txt9 = encrypt(txt9, PointConstants.SEED_KEY_DONGBU);                       // 요금제명 암호화, 2017-04-28 암호화 추가요청
		
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(txt1);
		sbuf.append("_").append(txt2);
		sbuf.append("_").append(txt3);
		sbuf.append("_").append(txt4);
		sbuf.append("_").append(txt5);
		sbuf.append("_").append(txt6);
		sbuf.append("_").append(txt7);
		sbuf.append("_").append(txt8);
		sbuf.append("_").append(txt9);
		sbuf.append("_").append(txt10);
		sbuf.append("_").append(txt11);
		sbuf.append("_").append(txt12);
		
		return sbuf.toString();
	}
	
	/**
	 * SEED 암호화
	 * @param plain
	 * @param seedKey
	 * @return
	 */
	protected String encrypt(String plain, String seedKey) {
		
		LOGGER.debug("-------------------- 암호화 시작 ----------------------");
		LOGGER.debug("평문 = [{}]- len[{}]", plain, plain.length());
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
		
		LOGGER.debug("암호화=[{}]- len[{}]",sb.toString(), sb.toString().length());
		
		LOGGER.debug("-------------------- 암호화 끝 ----------------------");
		
		return sb.toString();
		
	}
	
	/**
	 * SEED 복호화
	 * @param sHexCipher
	 * @param seedKey
	 * @return
	 */
	protected String decrypt(String sHexCipher, String seedKey) {
		
		LOGGER.debug("-------------------- 복호화 시작 ----------------------");
		LOGGER.debug("암호화 = [{}]- len[{}]",sHexCipher, sHexCipher.length());
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
		LOGGER.debug("복호화 [{}]- len[{}]", temp3, temp3.length());
		
		LOGGER.debug("-------------------- 복호화 끝 ----------------------");
		
		return temp3.trim();
		
	}
	
	/**
	 * 동부화재 sms 전송
	 * 
	 * TEMPLATE_ID
	 * NAC : 19 : 안심요금제 개통
	 * RSP : 20 : 정지복구
	 * SCH : 21 : 요금제변경 가입( 타요금제 -> 안심요금제 )
	 * RCL : 22 : 해지복구
	 * MCN : 23 : 명의변경
	 * SCH : 24 : 요금제변경 해지( 안심요금제 -> 타요금제 )
	 */
	public void setDonguSmsSend() throws MvnoServiceException {
		String partnerId = "dongbu";
		
		try {
			
			List<Map<String, Object>> list = mapper.getDongbuSmsSend(partnerId);
			
			for (Map<String, Object> rtMap : list){
				
				LOGGER.debug("map=" + rtMap);
				
				String templateId = "";
				
				DongbuPointVO vo = new DongbuPointVO();
				vo.setPartnerId(partnerId);
				vo.setEvntCd((String) rtMap.get("EVNT_CD"));
				vo.setContractNum((String) rtMap.get("CONTRACT_NUM"));
				vo.setEndDttm((String) rtMap.get("END_DTTM"));
				vo.setStrtDttm((String) rtMap.get("STRT_DTTM"));
				vo.setCustomerId((String) rtMap.get("CUSTOMER_ID"));
				vo.setRateCd((String) rtMap.get("RATE_CD"));
				vo.setStatCd((String) rtMap.get("STAT_CD"));
				
				if("NAC".equals(vo.getEvntCd())){
					templateId = "19";
					// 보험가입 효력개시
					mapper.insertInsrMemberMst(vo);
				}
				else if("SUS".equals(vo.getEvntCd())){
					// 보험가입 효력정지
					mapper.updateInsrMemberMst(vo);
				}
				else if("RSP".equals(vo.getEvntCd())){
					templateId = "20";
					
					// 보험가입 효력개시
					mapper.insertInsrMemberMst(vo);
				}
				else if("SCH_IN".equals(vo.getEvntCd())){
					templateId = "21";
					
					// 보험가입 효력처리
					if(mapper.checkInsrMemberMst(vo) > 0){
						mapper.updateInsrMemberMst(vo);
					}else{
						mapper.insertInsrMemberMst(vo);
					}
				}
				else if("CAN".equals(vo.getEvntCd())){
					// 보험가입 효력정지
					mapper.updateInsrMemberMst(vo);
				}
				else if("RCL".equals(vo.getEvntCd())){
					templateId = "22";
					
					// 보험가입 효력개시
					mapper.insertInsrMemberMst(vo);
				}
				else if("MCN".equals(vo.getEvntCd())){
					templateId = "23";
					
					// 기존 계약정보 수정
					DongbuPointVO vo2 = new DongbuPointVO();
					
					vo2.setContractNum(vo.getContractNum());
					vo2.setEndDttm(vo.getStrtDttm());
					vo2.setStatCd("C"); // 기존 고객의 가입정보는 해지로 변경
					
					mapper.updateInsrMemberMst(vo2);
					
					// 신규 계약정보 생성
					mapper.insertInsrMemberMst(vo);
				}
				else if("SCH_OUT".equals(vo.getEvntCd())){
					templateId = "24";
					
					// 보험가입 효력정지
					mapper.updateInsrMemberMst(vo);
				}
				
				// 문자발송
				// 템플릿ID 가 있고, 발송요청시간이 있는 경우 문자 발송 처리
				if(!"".equals(templateId) && !"X".equals((String) rtMap.get("REQ_SEND_DATE"))){
					SmsCommonVO smsVO = smsCommonMapper.getTemplateText(templateId);
					// 요금제명 치환
					smsVO.setSendMessage((smsVO.getTemplateText()).replaceAll(Pattern.quote("#{rateNm}"), String.valueOf(rtMap.get("RATE_NM")) ));
					smsVO.setTemplateId(templateId);
					smsVO.setMobileNo((String) rtMap.get("SUBSCRIBER_NO"));
					smsVO.setRequestTime((String) rtMap.get("REQ_SEND_DATE"));
					
					smsCommonMapper.insertTemplateMsgQueue(smsVO);
				}
			}
		}catch(Exception e){
			throw new MvnoServiceException("EPTNR1019", e);
		}
	}
	
	
}
