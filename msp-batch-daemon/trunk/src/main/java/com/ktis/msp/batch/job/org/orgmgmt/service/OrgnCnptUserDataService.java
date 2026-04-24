package com.ktis.msp.batch.job.org.orgmgmt.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.orgmgmt.mapper.OrgnCnptUserDataMapper;
import com.ktis.msp.batch.job.org.orgmgmt.vo.CpntUserVo;
import com.ktis.msp.batch.util.EncryptUtil;

@Service
public class OrgnCnptUserDataService extends BaseService {
	
	@Autowired
	private OrgnCnptUserDataMapper orgnCnptUserDataMapper;	
	
	/**
	 * M유통 판매점 사용자연동
	 * @return
	 * @throws MvnoServiceException
	 */

	public int callCnptUserDataProc() throws MvnoServiceException {
		int procCnt = 0, m2mCnt = 0, bizChkCnt = 0,idChkCnt = 0, idUserCnt = 0, usrChgCnt = 0, delRsn = 0;
		
		LOGGER.info("=========================================================================");
		LOGGER.info("===================M유통 판매점 사용자연동 START=========================");
		LOGGER.info("=========================================================================");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, -1); //전일

		String todatDate = sdf.format(cal.getTime()).substring(0,8); // 수행기준일자 생성
		
		//대리점경영자이력
		String mngrFileNm = "KIS_OG_AGNCY_MNGR_HST_"+todatDate+".DAT";
		//대리점 정보생성
		String agntFileNm = "KIS_OG_AGNCY_BAS_"+todatDate+".DAT";
		
		// 0.오늘 들어온 조직 데이터가 현행화 되어 있는지 체크하여 없으면 종료 있으면 진행시킨다.
		int fileCnt = orgnCnptUserDataMapper.getCnptUserDataFileYn(mngrFileNm);

		// 1. 조직 현행화 대상 리스트를 가져온다 
		List<CpntUserVo> list = orgnCnptUserDataMapper.getCnptUserOrgnList();

		if(fileCnt == 0) {
			LOGGER.info("조회할 파일이 없습니다. 파일명 : " + mngrFileNm);
			LOGGER.info("======================= STOP =======================");
			return fileCnt;
		}else {
			// 2. MDS_AGNCY_MNGR_MST 테이블을 전체 삭제
			orgnCnptUserDataMapper.deleteMdsAgncyMngrMst();
			// 3. MDS_AGNCY_MNGR_MST 테이블을 오늘자 수행파일 데이터로 현행화
			orgnCnptUserDataMapper.insertMdsAgncyMngrMst(mngrFileNm);		
		}
		
		try{
			for(CpntUserVo vo : list){
				// 4. 조회한 판매점으로 데이터를 조회 하여 계정 분기처리 
				// 4-0. 조직명의 특수문자 제거"<",">"
				if(vo.getOrgnNm().indexOf('>') > -1 || vo.getOrgnNm().indexOf('<') > -1) {
					vo.setOrgnNm(vo.getOrgnNm().replaceAll("<", ""));
					vo.setOrgnNm(vo.getOrgnNm().replaceAll(">", ""));
					vo.setOrgnSubstrNm(vo.getOrgnSubstrNm().replaceAll("<", ""));
					vo.setOrgnSubstrNm(vo.getOrgnSubstrNm().replaceAll(">", ""));
				}
				
				// 4-1. MDS_AGNCY_MNGR_MST 테이블에서 OrgnId로 전화번호를 조회 
				vo.setMngrMphonNo(orgnCnptUserDataMapper.getCnptUserMngrMphonNo(vo));
				vo.setAgntFileNm(agntFileNm);

				// 4-2. M2M 유통망인지 조회
				m2mCnt = orgnCnptUserDataMapper.getCnptUserDataM2MYn(vo);
				
				if(m2mCnt > 0 ) {

					// 4-3.M2M 권한명 설정
					vo.setGrpId("G-PSCM2M");
					// 4-4. M2M의 사업자 번호를 가져온다.
					vo.setBizRegNum(orgnCnptUserDataMapper.getCnptUserBizRegNum(vo));
					// 최초UserId는 사업자 번호로 세팅 
					vo.setUserId(orgnCnptUserDataMapper.getCnptUserBizRegNum(vo));
					
					// 4-5. 사업자 번호로 된 판매점 M전산 계정이 있는지 확인
					bizChkCnt = orgnCnptUserDataMapper.getCnptUserDataBizRegNumYn(vo);
					// 사업자번호로 된 ID가 있으면 한번더 확인한다. 
					if(bizChkCnt > 0) {
						// CMN_USR_MST 테이블에 M2M 사업자번호로 된 아이디, 조직코드가 동일한 계정이 있는지 한번더 확인
						idUserCnt = orgnCnptUserDataMapper.getCnptUserDataBizRegNumOrgIdYn(vo);
						if(idUserCnt == 0) {
							//사업자로 된 UserId가 있으면서 조직코드가 다르면 조직코드로 ID를 세팅한다.
							vo.setUserId(vo.getOrgnId());
						}
					}
					
					// 4.6 세팅된 userId로 계정이 있는지 조회 ****
					idChkCnt = orgnCnptUserDataMapper.getCnptUserDataIdUserCnt(vo);
					
					// 세팅된 userId로 된 계정이 없으면 생성, 없으면 업데이트 한다.
					if(idChkCnt == 0) {
						LOGGER.info("userId 로 신규 계정 생성 ID : " + vo.getUserId());
						procCnt++;
						// 4-7. M2M 계정이 없는  경우만 계정 생성 처리
						
						// 4-7-0. 생성계정 password 초기화
						vo.setPass(getDefaultPass());
						//password 암호화
						vo.setPass(EncryptUtil.sha512HexEnc(vo.getPass()));
						
						// 4-7-1. CMN_USR_MST에 INSERT 하여 계정 생성
						orgnCnptUserDataMapper.insertCnptUserData(vo);
						// 4-7-2. CMN_USR_HST에 INSERT 하여 계정 이력 생성
						orgnCnptUserDataMapper.insertCnptUserDataHst(vo);
						// 4-7-3. CMN_USG_GRP_ASGN_MST에 INSERT 하여 권한 생성
						orgnCnptUserDataMapper.insertCnptUserDataAsgnMSt(vo);
						// 4-7-4. CMN_USG_GRP_ASGN_HST에 INSERT 하여 권한이력 생성
						orgnCnptUserDataMapper.insertCnptUserDataAsgnHSt(vo);
					
					}else if(idChkCnt > 0){
						LOGGER.info("userId 로 된 기존 계정 있어 미생성, UPDATE 유무 확인");
						// 4-8. 계정이 사용자명,전화번호,userId(사업자 번호) 로 존재하는지 확인
						usrChgCnt = orgnCnptUserDataMapper.getCnptUserDataMphonNumUsrNm(vo);
						
						//userId(사업자 번호) 별 전화번호 or 사용자명 변경된 경우에 업데이트
						if(usrChgCnt == 0){
							orgnCnptUserDataMapper.updateCnptUserDataMphonNumUsrNm(vo);
						}
					}
				}
			//list 반복 종료	
			}
			
		}catch(Exception e){
			throw new MvnoServiceException("EORG1006", e);
		}
		LOGGER.info("M유통 판매점 사용자연동 End");
		return procCnt;
	}	
	
	//초기 pw 생성
	String getDefaultPass() {
		String pass = "";
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
		String year = sdf.format(now);

		pass = "new"+year+"!";
		
		return pass;
	}

	
	/**
	 * @throws MvnoServiceException 
	 * M유통 판매점 사용자연동 프로시저 호출
	 * @param vo
	 * @throws 
	 */
//	@Transactional(rollbackFor=Exception.class)
	public void callCnptUserData(Map<String, Object> param) throws MvnoServiceException{
		try{
				LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				LOGGER.info("☆☆☆☆☆☆☆☆callCnptUserData 프로시저시작☆☆☆☆☆☆☆☆ : ");
				LOGGER.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				orgnCnptUserDataMapper.callCnptUserData(param);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
