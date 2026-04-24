package com.ktis.msp.batch.job.ptnr.ptnrmgmt.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.DongbuInsrMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.PartnerPointSettleMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.DongbuPointVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 동부화재 정산 서비스
 * ---------- ------- ----------------------------------------------
 * 2018.08.06 이상직  SRM18072675707  선택형 5종 단체보험 출시 관련 전산 프로세스 구축
 *                    동부화재 서비스 분리
 */
@Service
public class DongbuSettleService extends BaseService {
	
	@Autowired
	private DongbuInsrMapper insrMapper;
	
	@Autowired
	private PartnerPointSettleMapper ptnrMapper;
	
	@Autowired
	private PartnerCryptoService cryptoService;
	
	@Autowired
	private EgovPropertyService propertiesService;
	
	/**
	 * 동부화재 보험가입자 정보 생성
	 * @param searchVO
	 * <li>partnerId=제휴사아이디</li>
	 * @return
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveInsrMemberList() throws MvnoServiceException {
		ObjectMapper om = new ObjectMapper();
		
		String[] errParam = new String[1];
		errParam[0] = PointConstants.STR_DONGBU;
		
		List<?> list = null;
		// 전일자 이력조회
		int dayCnt = 1;
		
		// 해지대상 조회
		list = insrMapper.getCanInsrMemberList(dayCnt);
		
		LOGGER.info("해지대상조회 list.size()=" + list.size());
		
		for(Object fromValue: list) {
			DongbuPointVO vo = om.convertValue(fromValue, DongbuPointVO.class);
			
			LOGGER.debug("해지 vo=" + vo.toString());
			
			insrMapper.updateCanInsrMember(vo);
		}
		
		LOGGER.info("---- 보험가입자 생성대상 조회 시작 ----- ");
		// 보험가입자 생성대상 조회
		list = insrMapper.getRegInsrMemberList(dayCnt);
		
		LOGGER.info("가입자생성대상 list.size()=" + list.size());
		
		for(Object fromValue: list) {
			DongbuPointVO vo = om.convertValue(fromValue, DongbuPointVO.class);
			
			LOGGER.debug("가입 vo=" + vo.toString());
			
			// 가입정보 체크
			LOGGER.info("---- 가입자정보체크 ----- ");
			DongbuPointVO chkVO = insrMapper.getExistInsrMember(vo);
			if(chkVO != null) {
				insrMapper.updateCanInsrMember(chkVO);
			}
			insrMapper.insertInsrMemberMst(vo);
		}
		
//		// 연동대상생성 및 조회(테스트용)
//		insrMapper.deletePartnerDongbu01();
		LOGGER.info("---- insertPartnerDongbu01 ----- ");
		insrMapper.insertPartnerDongbu01();
		
		LOGGER.info("---- getInsrMemberSendList ----- ");
		list = insrMapper.getInsrMemberSendList();
		
		if(!list.isEmpty()) {
			try {
				LOGGER.info("---- 제휴사 정산 파일저장 ----- ");
				this.savePointFile(list);
			} catch(MvnoServiceException e) {
				throw e;
			} catch(Exception e) {
				throw new MvnoServiceException("EPTNR1016", errParam, e);
			}
		}
		
		LOGGER.debug("제휴사 정산 파일 저장 끝..................................");
		
	}
	
	public void savePointFile(List<?> clist) throws MvnoServiceException {
		
		LOGGER.debug("제휴사 정산 파일 쓰기 시작..................................");
		ObjectMapper op = new ObjectMapper();
		// 파일명
		String fileName = "";
		String lDownBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String lUpBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String hDownBaseDir = "";
		String hUpBaseDir = "";
		List<?> list = ptnrMapper.getPartnerSubList(PointConstants.DONGBU_IF_NO);
		
		if(list.isEmpty()) {
			throw new MvnoServiceException("EPTNR1012");
		}
		
		StringBuffer buf3 = new StringBuffer();
		
		for (Object fromValue : list) {
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
		String lDownDir = lDownBaseDir + PointConstants.STR_DONGBU + "/" + hDownBaseDir;
		// 서버 다운로드 경로
		String hDownDir = "";
		StringBuffer buf = new StringBuffer();
		buf.append(PointConstants.STR_DONGBU);
		buf.append("/");
		buf.append(hDownBaseDir);
		hDownDir = buf.toString();
		
		// 로컬 업로드 경로
		String lUpDir = lUpBaseDir + PointConstants.STR_DONGBU + "/" + hUpBaseDir;
		// 서버 업로드 경로 구하기
		StringBuffer buf1 = new StringBuffer();
		buf1.append(PointConstants.STR_DONGBU);
		buf1.append("/");
		buf1.append(hUpBaseDir);
		String hUpDir = buf1.toString();
		
		// 생성날짜 구하기
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		StringBuffer buf2 = new StringBuffer();
		buf2.append(PointConstants.DONGBU_IF_NO);
		buf2.append("_");
		buf2.append(dDate);
		buf2.append(PointConstants.FILE_EXT);
		
		fileName = buf2.toString();
		
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
			
			for (Object fromValue : clist) {
				DongbuPointVO vo = op.convertValue(fromValue, DongbuPointVO.class);
				
				// 문자열만들기
				str = getStringDongbu(vo);
				
				fw.write(str);
				fw.newLine();
			}
			
			fw.flush();
			
			//객체 닫기
			fw.close(); 
			
		} catch (Exception e) {
			throw new MvnoServiceException("EPTNR1013", e);
		}
		
		// 파일생성 후 파일정보를 디비에 저장
		PointFileVO fileVo = new PointFileVO();
		fileVo.setPartnerId(PointConstants.STR_DONGBU);
		fileVo.setLocalUpDir(lUpDir);
		fileVo.setHostUpDir(hUpDir);
		fileVo.setLocalDownDir(lDownDir);
		fileVo.setHostDownDir(hDownDir);
		fileVo.setFileNm(fileName);
		//insrMapper.deletePointFile(fileVo);
		ptnrMapper.insertPointFile(fileVo);
		
		LOGGER.debug("제휴사 정산 파일 쓰기 끝..................................");
	}
	
	private String getStringDongbu(DongbuPointVO vo) {
		
		// 고객명
		String txt1 = cryptoService.encrypt(vo.getCustNm(), PointConstants.SEED_KEY_DONGBU);
		// 전화번호
		String txt2 = cryptoService.encrypt(vo.getCtn(), PointConstants.SEED_KEY_DONGBU);
		// 법정대리인
		String txt3 = (vo.getMinorAgentNm() != null && !"".equals(vo.getMinorAgentNm())) ? cryptoService.encrypt(vo.getMinorAgentNm(), PointConstants.SEED_KEY_DONGBU) : "";
		// 법정대리인전화번호
		String txt4 = (vo.getMinorAgentTel() != null && !"".equals(vo.getMinorAgentTel())) ? cryptoService.encrypt(vo.getMinorAgentTel(), PointConstants.SEED_KEY_DONGBU) : "";
		// 계약상태
		String txt5 = vo.getSubStatus();
		// 가입일
		String txt6 = vo.getJoinDt(); 
		// 계약변경일
		String txt7 = (vo.getChangeDt() != null && !"".equals(vo.getChangeDt())) ? vo.getChangeDt() : "";
		// 유효시작일
		String txt8 = vo.getEfctStrtDt();
		// 요금제, 2017-04-28 암호화 추가요청
		String txt9 = (vo.getRateNm() != null && !"".equals(vo.getRateNm())) ? vo.getRateNm() : "";
		// 개통대리점
		String txt10 = (vo.getOpenAgntNm() != null && !"".equals(vo.getOpenAgntNm())) ? vo.getOpenAgntNm() : "";
		// 수납여부
		String txt11 = vo.getPymnYn();
		// 생년월일
		String txt12 = vo.getBirthDt();
		// 보험효력종료일
		String txt13 = (vo.getEfctEndDt() != null && !"".equals(vo.getEfctEndDt())) ? vo.getEfctEndDt() : "";
		// 보험코드
		String txt14 = vo.getInsrCd();
		
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
		sbuf.append("_").append(txt13);
		sbuf.append("_").append(txt14);
		
		return sbuf.toString();
	}
}
