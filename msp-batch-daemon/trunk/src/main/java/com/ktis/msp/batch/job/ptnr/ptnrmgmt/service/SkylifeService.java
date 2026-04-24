package com.ktis.msp.batch.job.ptnr.ptnrmgmt.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.mapper.SkylifeMapper;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.SkylifeVO;
import com.ktis.msp.batch.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 스카이라이프 가입자 정보 연동
 */
@Service
public class SkylifeService extends BaseService {
	
	@Autowired
	private SkylifeMapper skylifeMapper;
	
	@Autowired
	private PartnerPointSettleMapper ptnrMapper;
		
	@Autowired
	private EgovPropertyService propertiesService;
	
	/**
	 * 스카이라이프 가입자 정보 생성
	 * @param searchVO
	 * <li>partnerId=제휴사아이디</li>
	 * @return
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveMemberList() throws MvnoServiceException {
		String[] errParam = new String[1];
		errParam[0] = PointConstants.STR_SKYLIFE;
		
		List<SkylifeVO> list = null;
		
		list = skylifeMapper.getRegMemberList();
				
		for(SkylifeVO vo : list) {
			
			if (skylifeMapper.getExistMember(vo.getScrbrNo()) == 0) {
				skylifeMapper.insertMemberMst(vo);
				skylifeMapper.insertMemberIfHst(vo);
			}
		}
		
		list = skylifeMapper.getChgMemberList();
		
		for(SkylifeVO vo : list) {
			
			if (vo.getSeq().equals("1")) {
				skylifeMapper.updateMemberMst(vo);
			}
			skylifeMapper.insertMemberIfHst(vo);
		}
		
		list = skylifeMapper.getSendMemberList();

		try {
			this.savePointFile(list);
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("EPTNR1016", errParam, e);
		}
				
	}
	
	public void savePointFile(List<SkylifeVO> clist) throws MvnoServiceException {
		
		ObjectMapper op = new ObjectMapper();
		// 파일명
		String fileName = "";
		String lDownBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String lUpBaseDir = propertiesService.getString("msp.sftp.local.base.dir");
		String hDownBaseDir = "";
		String hUpBaseDir = "";
		List<?> list = ptnrMapper.getPartnerSubList(PointConstants.SKYLIFE_IF);
		
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
		String lDownDir = lDownBaseDir + PointConstants.STR_SKYLIFE + "/" + hDownBaseDir;
		// 서버 다운로드 경로
		String hDownDir = "";
		StringBuffer buf = new StringBuffer();
		buf.append(PointConstants.STR_SKYLIFE);
		buf.append("/");
		buf.append(hDownBaseDir);
		hDownDir = buf.toString();
		
		// 로컬 업로드 경로
		String lUpDir = lUpBaseDir + PointConstants.STR_SKYLIFE + "/" + hUpBaseDir;
		// 서버 업로드 경로 구하기
		StringBuffer buf1 = new StringBuffer();
		buf1.append(PointConstants.STR_SKYLIFE);
		buf1.append("/");
		buf1.append(hUpBaseDir);
		String hUpDir = buf1.toString();
		
		// 생성날짜 구하기
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentTime = new Date ( );
		String dDate = formatter.format ( currentTime );
		
		StringBuffer buf2 = new StringBuffer();
		buf2.append(PointConstants.SKYLIFE_IF);
		buf2.append("_");
		buf2.append(dDate);
		buf2.append(PointConstants.FILE_EXT);
		
		fileName = buf2.toString();
		
		LOGGER.debug("연동요청 파일명 =" + fileName);
		
		String str = "";
		
		try {
			File desti = new File(lUpDir);
			
			// 디렉토리가 없다면 생성
			if (!desti.exists()) {
				desti.mkdirs();
			}
			
			// BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(lUpDir + fileName), "EUC-KR"));
						
			for (SkylifeVO vo : clist) {
				
				// 문자열만들기
				str = getString(vo);
				
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
		fileVo.setPartnerId(PointConstants.STR_SKYLIFE);
		fileVo.setLocalUpDir(lUpDir);
		fileVo.setHostUpDir(hUpDir);
		fileVo.setLocalDownDir(lDownDir);
		fileVo.setHostDownDir(hDownDir);
		fileVo.setFileNm(fileName);
		ptnrMapper.insertPointFile(fileVo);
		
	}
	
	private String getString(SkylifeVO vo) {
		
		// 파일생성
		String txt1 = StringUtil.NVL(vo.getCi(), "");           	// CI
		String txt2 = StringUtil.NVL(vo.getMblTelNo(), "");         // 회선번호
		String txt3 = StringUtil.NVL(vo.getCustNm(), ""); 			// 고객명
		String txt4 = StringUtil.NVL(vo.getBirthDh(), "");			// 생년월일
		String txt5 = StringUtil.NVL(vo.getSvcOpenDh(), "");		// 서비스개통일시
		String txt6 = StringUtil.NVL(vo.getChgDh(), "");			// 변경일시
		String txt7 = StringUtil.NVL(vo.getChgKindCd(), "");		// 변경종류코드
		String txt8 = StringUtil.NVL(vo.getRscsDh(), "");			// 해지일시
		String txt9 = StringUtil.NVL(vo.getScrbrNo(), "");			// 주계약번호
		String txt10 = StringUtil.NVL(vo.getNsContractNo(), "");	// N-STEP 가입계약번호(x)
		String txt11 = StringUtil.NVL(vo.getPrdtCd(), "");			// 상품코드
		String txt12 = StringUtil.NVL(vo.getPrdtNm(), "");			// 상품명
		String txt13 = StringUtil.NVL(vo.getAgncyNm(), "");			// 기관명
		String txt14 = "";			
		String txt15 = "";
		
		txt2 = getencrypt(txt2, PointConstants.SEED_KEY_SKYLIFE); // CTN 암호화
		txt3 = getencrypt(txt3, PointConstants.SEED_KEY_SKYLIFE); // 고객명 암호화
		
		txt1 = StringUtils.rightPad(txt1, 90);
		txt2 = StringUtils.rightPad(txt2, 40);
		txt3 = StringUtils.rightPad(txt3, 60);
		txt4 = StringUtils.rightPad(txt4, 8);
		txt5 = StringUtils.rightPad(txt5, 14);
		txt6 = StringUtils.rightPad(txt6, 14);
		txt7 = StringUtils.rightPad(txt7, 3);
		txt8 = StringUtils.rightPad(txt8, 14);
		txt9 = StringUtils.rightPad(txt9, 15);
		txt10 = StringUtils.rightPad(txt10, 15);
		txt11 = StringUtils.rightPad(txt11, 10);
		txt12 = StringUtil.rightPad(txt12, 100, " ", "EUC-KR");
		txt13 = StringUtils.rightPad(txt13, 10);
		txt14 = StringUtils.rightPad(txt14, 20);
		txt15 = StringUtils.rightPad(txt15, 20);
		
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
		sbuf.append(txt10);
		sbuf.append(txt11);
		sbuf.append(txt12);
		sbuf.append(txt13);
		sbuf.append(txt14);
		sbuf.append(txt15);
		
		return sbuf.toString();
	}
	
	protected static String getencrypt(String plain, String seedKey) {
		
		SEED seed = new SEED();
		// 평문을 16자리로 맞춰야 함. 16자리가 넘으면 16자리로 잘라서 암호화 해야함.
		String sPlain = StringUtils.rightPad(plain, 16);
		
		//--------------------------------------------------//
		// 암호화 모듈 호출
		//--------------------------------------------------//
		byte[] pbCipher = seed.encrypt(sPlain, seedKey);
		
		StringBuffer sb = new StringBuffer();
		
		for (int i=0; i<pbCipher.length; i++){
			sb.append(seed.appendLPad(Integer.toHexString(0xff&pbCipher[i]),2,"0"));
		}
		
		
		return sb.toString();
		
	}
}
