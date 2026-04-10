package com.ktis.msp.org.prmtmgmt.service;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.prmtmgmt.mapper.DisPrmtMgmtMapper;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtVO;
import com.ktis.msp.org.prmtmgmt.vo.DisPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.vo.DisPrmtMgmtVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class DisPrmtMgmtService extends BaseService {
    @Autowired
    private DisPrmtMgmtMapper disPrmtMgmtMapper;

	@Autowired
	private MaskingService maskingService;

    /** propertiesService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** Constructor */
    public DisPrmtMgmtService() {
        setLogPrefix("[DisPrmtMgmtService] ");
    }
    
    private HashMap<String, String> getMaskFields() {
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		
		maskFields.put("subLinkName", "CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("deSubLinkName", "CUST_NAME");
		maskFields.put("deSubscriberNo","MOBILE_PHO");
		
		return maskFields;
	}

	/**
	 * @Description : 평생할인 정책 목록 조회
	 * @Param  : DisPrmtMgmtVO
	 * @Return : List<?>
	 * @Author : 김동혁
	 * @Create Date : 2023.09.27.
	 */
    public List<DisPrmtMgmtVO> getDisPrmtList(DisPrmtMgmtVO vo) {
    	
    	if(KtisUtil.isEmpty(vo.getSearchBaseDate())){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다.");
		}
        List<DisPrmtMgmtVO> resultList = disPrmtMgmtMapper.getDisPrmtList(vo);
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
        
		maskingService.setMask(resultList, maskingService.getMaskFields(), paramMap);
       
        for(int i=0; i<resultList.size(); i++) {
        	resultList.get(i).parseEngg();
        }
        return resultList;
    }
    
    // 평생할인 조직 목록 조회
    public List<DisPrmtMgmtSubVO> getDisPrmtOrgnList(DisPrmtMgmtSubVO disPrmtMgmtSubVO) {
		
		return disPrmtMgmtMapper.getDisPrmtOrgnList(disPrmtMgmtSubVO);
	}
    
    // 평생할인 요금제 목록 조회
	public List<DisPrmtMgmtSubVO> getDisPrmtSocList(DisPrmtMgmtSubVO disPrmtMgmtSubVO) {
		
		return disPrmtMgmtMapper.getDisPrmtSocList(disPrmtMgmtSubVO);
	}
	
	// 평생할인 부가서비스 목록 조회
	public List<DisPrmtMgmtSubVO> getDisPrmtAddList(DisPrmtMgmtSubVO disPrmtMgmtSubVO) {
		
		return disPrmtMgmtMapper.getDisPrmtAddList(disPrmtMgmtSubVO);
	}
	
	 // 평생할인 상세 조직 목록 조회
    public List<DisPrmtMgmtSubVO> getDisPrmtDtlOrgnList(DisPrmtMgmtSubVO disPrmtMgmtSubVO) {
		
    	//1. 프로모션 ID 체크
    	if(KtisUtil.isEmpty(disPrmtMgmtSubVO.getPrmtId())){
			throw new MvnoRunException(-1, "프로모션 ID가 존재하지 않습니다.");
		}
    	
    	List <DisPrmtMgmtSubVO> result = null;
    	if("MOD".equals(disPrmtMgmtSubVO.getSearchType())) {
    		result = disPrmtMgmtMapper.getDisPrmtDtlOrgnList(disPrmtMgmtSubVO);
    	}
    	else {
    		result = disPrmtMgmtMapper.getDisPrmtDtlOrgnCopyList(disPrmtMgmtSubVO);
    	}
        	
		return result;
	}
    
    // 평생할인 상세 요금제 목록 조회
	public List<DisPrmtMgmtSubVO> getDisPrmtDtlSocList(DisPrmtMgmtSubVO disPrmtMgmtSubVO) {
		//1. 프로모션 ID 체크
    	if(KtisUtil.isEmpty(disPrmtMgmtSubVO.getPrmtId())){
			throw new MvnoRunException(-1, "프로모션 ID가 존재하지 않습니다.");
		}
    	
    	List <DisPrmtMgmtSubVO> result = null;
    	if("MOD".equals(disPrmtMgmtSubVO.getSearchType())) {
    		result = disPrmtMgmtMapper.getDisPrmtDtlSocList(disPrmtMgmtSubVO);
    	}
    	else {
    		result = disPrmtMgmtMapper.getDisPrmtDtlSocCopyList(disPrmtMgmtSubVO);
    	}
        	
		return result;
		
	}
	
	// 평생할인 상세 부가서비스 목록 조회
	public List<DisPrmtMgmtSubVO> getDisPrmtDtlAddList(DisPrmtMgmtSubVO disPrmtMgmtSubVO) {
		//1. 프로모션 ID 체크
    	if(KtisUtil.isEmpty(disPrmtMgmtSubVO.getPrmtId())){
			throw new MvnoRunException(-1, "프로모션 ID가 존재하지 않습니다.");
		}
    	
		List <DisPrmtMgmtSubVO> result = null;
    	if("MOD".equals(disPrmtMgmtSubVO.getSearchType())) {
    		result = disPrmtMgmtMapper.getDisPrmtDtlAddList(disPrmtMgmtSubVO);
    	}
    	else {
    		result = disPrmtMgmtMapper.getDisPrmtDtlAddCopyList(disPrmtMgmtSubVO);
    	}
        	
		return result;
		
	}
    
	/**
	 * @Description : 평생할인 정책 상세 조회
	 * @Param  : DisPrmtMgmtVO
	 * @Return : DisPrmtMgmtVO
	 * @Author : 김동혁
	 * @Create Date : 2023.09.27.
	 */
    public DisPrmtMgmtVO getDisPrmtDtlInfo(DisPrmtMgmtVO vo){
    	
    	if(KtisUtil.isEmpty(vo.getPrmtId())){
			throw new MvnoRunException(-1,"프로모션ID가 존재하지 않습니다");
		}
    	
    	DisPrmtMgmtVO resultVo = disPrmtMgmtMapper.getDisPrmtDtlInfo(vo);

		if(resultVo == null)  resultVo= new DisPrmtMgmtVO();
		else{
			resultVo.parseEngg();
			if(!"Y".equals(resultVo.getUsgYn())){
				String prmtId = resultVo.getPrmtId();
				resultVo = new DisPrmtMgmtVO();  // 프로모션 아이디만 set해서 화면으로 전달
				resultVo.setPrmtId(prmtId);
			}
		}

        return resultVo;
    }
    
    /**
	 * @Description : 평생할인 프로모션 등록
	 * @Author : 김동혁
	 * @Create Date : 2023.10.06.
	 */

	public void regDisPrmtInfo(DisPrmtMgmtVO disPrmtMgmtVO) {
		
		Map <String, String> chkDataMap = chkDisDataValidate(disPrmtMgmtVO, "");
		if(!"0000".equals(chkDataMap.get("code"))) {
			throw new MvnoRunException(-1, "["+chkDataMap.get("errMsg")+"]이/가 유효하지 않습니다");
		}
		if(disPrmtMgmtVO.getOrgnList().size() < 1){
			throw new MvnoRunException(-1, "조직 정보가 존재하지 않습니다");
		}
		
		if(disPrmtMgmtVO.getSocList().size() < 1){
			throw new MvnoRunException(-1, "요금제 정보가 존재하지 않습니다");
		}
		
		if(disPrmtMgmtVO.getAddList().size() < 1){
			throw new MvnoRunException(-1, "부가서비스 정보가 존재하지 않습니다");
		}
		
		try {
			duplicateDisPrmtChk(disPrmtMgmtVO, "MAIN");
		}catch (MvnoRunException e){
			throw new MvnoRunException(-1, "이미 등록된 프로모션["+ e.getMessage() +"]이 존재합니다.");
		}
		
		try {
			// 평생할인 마스터 추가
			disPrmtMgmtMapper.insertDisPrmtMst(disPrmtMgmtVO);
			
			
			// 평생할인 조직 추가
			for(String orgnId : disPrmtMgmtVO.getOrgnList()){
				
				disPrmtMgmtVO.setOrgnId(orgnId);
				disPrmtMgmtMapper.insertDisPrmtOrg(disPrmtMgmtVO);
			}
			// 평생할인 요금제 추가
			for(String rateCd : disPrmtMgmtVO.getSocList()) {
				disPrmtMgmtVO.setRateCd(rateCd);
				disPrmtMgmtMapper.insertDisPrmtSoc(disPrmtMgmtVO);
			}
			// 평생할인 부가서비스 추가
			for(String soc : disPrmtMgmtVO.getAddList()) {
				disPrmtMgmtVO.setSoc(soc);
				disPrmtMgmtMapper.insertDisPrmtAdd(disPrmtMgmtVO);
			}
		} catch(Exception e) {
			throw new MvnoRunException(-1,"저장 처리 중 오류가 발생하였습니다.");
		}
	}
	
	//대리점 추가
	public void setDisPrmtOrgAdd(DisPrmtMgmtVO disPrmtMgmtVO) {
				
		List<String> prmtIdList = disPrmtMgmtVO.getPrmtIdList();
		List<String> orgnIdList = disPrmtMgmtVO.getOrgnList();
		
		if(KtisUtil.isEmpty(prmtIdList) || KtisUtil.isEmpty(orgnIdList)){
			throw new MvnoRunException(-1, "선택된 데이터가 없습니다.<br>데이터를 선택하고 처리해주시기 바랍니다.");
		}
		
		for(int i=0; i<prmtIdList.size(); i++) {
			DisPrmtMgmtSubVO disPrmtMgmtSubVO = new DisPrmtMgmtSubVO();
			disPrmtMgmtSubVO.setPrmtId(prmtIdList.get(i));
			disPrmtMgmtVO.setPrmtId(prmtIdList.get(i));
			
			List<DisPrmtMgmtSubVO> orgnList=disPrmtMgmtMapper.getDisPrmtDtlOrgnList(disPrmtMgmtSubVO);
			
			for(int idx1=0; idx1<orgnIdList.size(); idx1++) {
				for(int idx2=0; idx2<orgnList.size(); idx2++){
					if(orgnList.get(idx2).getOrgnId().equals(orgnIdList.get(idx1))) {
						throw new MvnoRunException(-1, "등록하려는 [" +prmtIdList.get(i)+ "] 프로모션에 <br> "
								+ " 이미 [" +orgnList.get(idx2).getOrgnNm()+ "] 조직이 존재하여 대리점추가를 할수 없습니다.");
					}
				}
				disPrmtMgmtVO.setOrgnId(orgnIdList.get(idx1));
				disPrmtMgmtMapper.insertDisPrmtOrg(disPrmtMgmtVO);
			}
		
			DisPrmtMgmtVO resultVo = disPrmtMgmtMapper.getDisPrmtDtlInfo(disPrmtMgmtVO);
			if("N".equals(resultVo.getUsgYn())){
				throw new MvnoRunException (-1, "프로모션 ID [" +prmtIdList.get(i)+ "] 프로모션은 이미 삭제된 프로모션으로 대리점을 추가 할 수 없습니다.");
			}
			
			String endDttm = resultVo.getEndDttm();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date dateNow = new Date();
			String stringNow = formatter.format(dateNow);
			int result = endDttm.compareTo(stringNow);
			if(result < 0){
				throw new MvnoRunException(-1 ,"프로모션 ID [" +prmtIdList.get(i)+ "] 프로모션은 이미 종료된 프로모션으로 대리점을 추가 할 수 없습니다.");
			}
			resultVo.parseEngg();   // 1. enggSrl -> enggCnt 파싱   Y , N 으로
			resultVo.changeEngg();	// 2. enggCnt 값 "Y or N" -> "00 or 12 ...." 숫자로 변환 (중복검사를 위하여)
			
			
			
			//중복검사
			List<DisPrmtMgmtVO> aryDupChkList = disPrmtMgmtMapper.getDisPrmtDupByInfo(resultVo);
			if(aryDupChkList.size() > 0){
				throw new MvnoRunException(-1 ,"[" +prmtIdList.get(i)+ "] 프로모션에 대리점 추가시<br> "
											+ "["+ aryDupChkList.get(0).getPrmtId() +"]프로모션과 중복되어 추가 할 수 없습니다.");
			}
		}
	}
	
	//종료일시 일괄변경 
	public void updDisPrmtEndDttm(DisPrmtMgmtVO disPrmtMgmtVO) {
		
		List<String> prmtIdList = disPrmtMgmtVO.getPrmtIdList();
		
		if(KtisUtil.isEmpty(prmtIdList)){
			throw new MvnoRunException(-1, "프로모션 ID가 존재하지 않습니다.");
		}
		if(KtisUtil.isEmpty(disPrmtMgmtVO.getEndDttmMod())){
			throw new MvnoRunException(-1, "종료일시가 존재하지 않습니다.");
		}
		for(String prmtId : prmtIdList) {
			disPrmtMgmtVO.setPrmtId(prmtId);
			
			disPrmtMgmtVO.setTypeCd("U");
			this.updDisPrmtByInfo(disPrmtMgmtVO);
		}
	}
	
	//종료일 변경 , 삭제 
	public void updDisPrmtByInfo(DisPrmtMgmtVO disPrmtMgmtVO) {
		
		if(KtisUtil.isEmpty(disPrmtMgmtVO.getPrmtId())){
			throw new MvnoRunException(-1, "프로모션 ID가 존재하지 않습니다..");
		}
		
		DisPrmtMgmtVO resultVo = disPrmtMgmtMapper.getDisPrmtDtlInfo(disPrmtMgmtVO);
		if("N".equals(resultVo.getUsgYn())){
			throw new MvnoRunException (-1, "프로모션 ID [" +resultVo.getPrmtId()+ "] 프로모션은 이미 삭제된 프로모션으로 종료일시를 변경 할 수 없습니다.");
		}
		resultVo.setSessionUserId(disPrmtMgmtVO.getSessionUserId());
		resultVo.setTypeCd(disPrmtMgmtVO.getTypeCd());
		
		if("U".equals(disPrmtMgmtVO.getTypeCd())){
			resultVo.parseEngg();   // 1. enggSrl -> enggCnt 파싱   Y , N 으로
			resultVo.changeEngg();	// 2. enggCnt 값 "Y or N" -> "00 or 12 ...." 숫자로 변환 (중복검사를 위하여)
			resultVo.setEndDttmMod(disPrmtMgmtVO.getEndDttmMod());
	
			//날짜 유효성 체크
			try {
				boolean ChkFlag = chkDateDisValidate(resultVo, resultVo.getTypeCd());
				if(!ChkFlag){
					throw new MvnoRunException(-1, "올바른 날짜를 입력해주세요.");
				}
			} catch (Exception e) {
				throw new MvnoRunException(-1, "올바른 날짜를 입력해주세요.");
			}
			
			List<DisPrmtMgmtVO> aryDupChkList = disPrmtMgmtMapper.getDisPrmtDupByInfo(resultVo);
			if(aryDupChkList.size() > 0){
				throw new MvnoRunException(-1 ,"[" +resultVo.getPrmtId()+ "] 프로모션 종료일자 변경 시 프로모션["+ aryDupChkList.get(0).getPrmtId() +"]과 중복됩니다.");
			}
		}
		disPrmtMgmtMapper.updDisPrmtByInfo(resultVo);
	}

	//프로모션 정책 엑셀 업로드 날짜(Date) 유효성 검사
	private boolean chkDateDisValidate(DisPrmtMgmtVO disPrmtMgmtVO, String typeCd) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		formatter.setLenient(false);
		Date now = new Date();		
		try {
			Date strtDttm = formatter.parse(disPrmtMgmtVO.getStrtDttm());
			Date endDttm = formatter.parse(disPrmtMgmtVO.getEndDttm());

			//날짜 형식 자리수 체크
			if(disPrmtMgmtVO.getStrtDttm().length() < 14 || disPrmtMgmtVO.getEndDttm().length() < 14 ){
				return false;
			}
			
			if("U".equals(typeCd)) {
				if( disPrmtMgmtVO.getEndDttmMod().length() < 14) {
					return false;
				}

				// 1.변경하려는 종료일시가 존재 하는지 확인
				Date endDttmMod = formatter.parse(disPrmtMgmtVO.getEndDttmMod());
				
				// 2.프로모션의 종료일시가 현재보다 이전인 경우 (이미 종료된 프로모션)
				if(endDttm.before(now)) {
					return false;
				}
				// 3.변경종료일시가 프로모션의 시작일시보다 이전인 경우
				if(endDttmMod.before(strtDttm)) {
					return false;
				}
				// 4.변경종료일시가 현재 이전인 경우
				if(endDttmMod.before(now)) {
					return false;
				}
			}
			else if("I".equals(typeCd)) {
				
				// 1.시작일시가 현재보다 이전인 경우
				if(strtDttm.before(now)) {
					return false;
				}
				// 2.시작일시가 종료일시보다 이후 인 경우
				if(strtDttm.after(endDttm)) {
					return false;
				}
			}
		}
		catch(Exception e){
			throw new MvnoRunException(-1, "올바른 날짜를 입력해주세요");
		}
		
		return true;
	}

	//프로모션 정책 엑셀 업로드 자료(Data) 유효성 검사
	public Map<String, String> chkDisDataValidate(DisPrmtMgmtVO vo, String regType){
		Map<String, String> chkDataMap = new HashMap<String, String>();
		chkDataMap.put("code","0001");
		
		if(KtisUtil.isEmpty(vo.getPrmtNm())){
			chkDataMap.put("errMsg","프로모션명");
			return chkDataMap;
		}
	
		if("EXCEL".equals(regType)) {
			if(KtisUtil.isEmpty(vo.getStrtDt()) || KtisUtil.isEmpty(vo.getStrtTm())){
				
				chkDataMap.put("errMsg","시작일자/시작시간");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getEndDt()) || KtisUtil.isEmpty(vo.getEndTm())){
				chkDataMap.put("errMsg","종료일자/종료시간");
				return chkDataMap;
			}
			
			vo.setStrtDttm(vo.getStrtDt()+vo.getStrtTm());
			vo.setEndDttm(vo.getEndDt()+vo.getEndTm());
			
			if(KtisUtil.isEmpty(vo.getOrgnId())){
				chkDataMap.put("errMsg","조직정보");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getRateCd())){
				chkDataMap.put("errMsg","요금제정보");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getSoc())){
				chkDataMap.put("errMsg","부가서비스정보");
				return chkDataMap;
			}
		}	
		//날짜 유효성 체크
		vo.setTypeCd("I");
		try {
			boolean isChecked = chkDateDisValidate(vo, vo.getTypeCd());
			if(!isChecked) {
				throw new MvnoRunException(-1, "올바른 날짜를 입력해주세요.");
			}
		} catch (Exception e) {
			chkDataMap.put("errMsg","시작일/종료일");
			return chkDataMap;
		}
		
		if(KtisUtil.isEmpty(vo.getChnlTp())){
			chkDataMap.put("errMsg","채널유형");
			return chkDataMap;
		}
		if(KtisUtil.isEmpty(vo.getEvntCd())){
			chkDataMap.put("errMsg","업무구분");
			return chkDataMap;
		} 
		if(KtisUtil.isEmpty(vo.getSlsTp())){
			chkDataMap.put("errMsg","가입유형");
			return chkDataMap;
		}
		if(KtisUtil.isEmpty(vo.getIntmYn())){
			chkDataMap.put("errMsg","개통단말");
			return chkDataMap;
		}
		if("Y".equals(vo.getIntmYn()) && (KtisUtil.isEmpty(vo.getModelId()) || "0".equals(vo.getModelId()))) {
			chkDataMap.put("errMsg","대표모델ID");
			return chkDataMap;
		}
		
		vo.changeEngg();
		if(!("00".equals(vo.getEnggCnt0())  ||
		   "12".equals(vo.getEnggCnt12()) || 
		   "18".equals(vo.getEnggCnt18()) || 
		   "24".equals(vo.getEnggCnt24()) || 
		   "30".equals(vo.getEnggCnt30()) || 
		   "36".equals(vo.getEnggCnt36()))){
			chkDataMap.put("errMsg","약정기간");
			return chkDataMap;
		}
			
		chkDataMap.put("code","0000");
		return chkDataMap;
	}
	
	
	// 상세정보 List
	public List<DisPrmtMgmtVO> getDisPrmtDtlList(DisPrmtMgmtVO disPrmtMgmtVO) {
		
		if(KtisUtil.isEmpty(disPrmtMgmtVO.getPrmtId())){
			throw new MvnoRunException(-1,"프로모션ID가 존재하지 않습니다.");
		}
		return disPrmtMgmtMapper.getDisPrmtDtlList(disPrmtMgmtVO);
	}
	
	// 평생할인 상세정보 엑셀 다운로드
	public List<DisPrmtMgmtVO> getDisPrmtDtlListExcel(DisPrmtMgmtVO disPrmtMgmtVO, Map<String, Object> paramMap){
		List<DisPrmtMgmtVO> rsltAryListVo = disPrmtMgmtMapper.getDisPrmtDtlListExcel(disPrmtMgmtVO);
					
		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(rsltAryListVo, maskFields, paramMap);

		return rsltAryListVo;
	}
	
	/**
	 * @Description : 평생할인 정책 엑셀 등록
	 * @Param  : DisPrmtMgmtVO
	 * @Author : 김동혁
	 * @Create Date : 2023.10.06.
	 */
    public int regDisPrmtInfoExcel(DisPrmtMgmtVO vo) {
    	int insertCnt = 0;
    	int chkCnt = disPrmtMgmtMapper.getDisMstChk();
		if(chkCnt>0) {
			throw new MvnoRunException(-1,"이미 진행중인 업로드가 있어 업로드가 불가합니다. 이전 업로드가 완료된후 다시 시도해주세요");
		}
    	
		List<DisPrmtMgmtVO> itemList = vo.getItems();
		if(KtisUtil.isEmpty(itemList)){
			throw new MvnoRunException(-1,"등록할 데이터가 없습니다.");
		}
		
		Map <String, String> chkDataMap = null; 
		String lastInsertRegNo="";
		DisPrmtMgmtVO insertTrgVo = new DisPrmtMgmtVO();
		
		Set<String> orgnSetExl = new HashSet<String>();
		Set<String> socSetExl = new HashSet<String>();
		Set<String> addSetExl = new HashSet<String>();
		
		for(int i = 0; i < itemList.size(); i++) {
			DisPrmtMgmtVO item = itemList.get(i);
			chkDataMap = chkDisDataValidate(item, "EXCEL");
			
			if(!"0000".equals(chkDataMap.get("code"))) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 ["+chkDataMap.get("errMsg")+"]이/가 유효하지 않습니다");
			}
			// 새로운 프로모션의 시작여부
			if( i == 0 || !item.getRegNum().equals(itemList.get(i-1).getRegNum())) {
				if(i != 0) {
					List<String> insertOrgnList = new ArrayList<String>(orgnSetExl);
					List<String> insertSocList = new ArrayList<String>(socSetExl);
					List<String> insertAddList = new ArrayList<String>(addSetExl);
				
					insertTrgVo.setOrgnList(insertOrgnList);
					insertTrgVo.setSocList(insertSocList);
					insertTrgVo.setAddList(insertAddList);
					
					try {
						duplicateDisPrmtChk(insertTrgVo, "EXCEL");
					}catch (MvnoRunException e){
						if("exlDup".equals(e.getMessage().substring(0,6))) {
							throw new MvnoRunException(1,"엑셀데이터 내 번호["+ insertTrgVo.getRegNum() +"]  은 번호["+ e.getMessage().substring(6) +"] 와 중복되어 업로드 할수 없습니다.");
						}
						else {
							throw new MvnoRunException(-1,"엑셀데이터 번호["+ insertTrgVo.getRegNum() +"] 는 "+"이미 등록된 프로모션["+ e.getMessage() +"]이 존재합니다.");
						}
					}
					// **********************Chk 테이블로 INSERT **********************
		    		// 엑셀데이터 평생할인 마스터 Chk테이블에 추가
			    	try {
			    		lastInsertRegNo = insertTrgVo.getRegNum();
			    		
						disPrmtMgmtMapper.insertDisPrmtMstChk(insertTrgVo);
						// 엑셀데이터 평생할인 조직 Chk테이블에 추가
						for(int idx=0; idx<insertTrgVo.getOrgnList().size(); idx++){
								insertTrgVo.setOrgnId(insertTrgVo.getOrgnList().get(idx));
								disPrmtMgmtMapper.insertDisPrmtOrgChk(insertTrgVo);
						}
						// 엑셀데이터 평생할인 요금제 Chk테이블에 추가
						for(int idx=0; idx<insertTrgVo.getSocList().size(); idx++){
							insertTrgVo.setRateCd(insertTrgVo.getSocList().get(idx));
							disPrmtMgmtMapper.insertDisPrmtSocChk(insertTrgVo);
						}
						// 엑셀데이터 평생할인 부가서비스 Chk테이블에 추가
						for(int idx=0; idx<insertTrgVo.getAddList().size(); idx++){
							insertTrgVo.setSoc(insertTrgVo.getAddList().get(idx));
							disPrmtMgmtMapper.insertDisPrmtAddChk(insertTrgVo);
						}
					} catch (Exception e) {	
						throw new MvnoRunException(-1,"엑셀업로드 중 오류가 발생했습니다.");
					}
			    	
			    	insertCnt++;
			    	orgnSetExl.clear();
		    		socSetExl.clear();
					addSetExl.clear();
				}
				insertTrgVo = new DisPrmtMgmtVO();
				insertTrgVo.setSessionUserId(vo.getSessionUserId());
				insertTrgVo.setRegNum(item.getRegNum());
				insertTrgVo.setPrmtNm(item.getPrmtNm());
				insertTrgVo.setStrtDttm(item.getStrtDttm());
				insertTrgVo.setEndDttm(item.getEndDttm());
				insertTrgVo.setChnlTp(item.getChnlTp());
				insertTrgVo.setEvntCd(item.getEvntCd());
				insertTrgVo.setSlsTp(item.getSlsTp());
				insertTrgVo.setIntmYn(item.getIntmYn());
				insertTrgVo.setModelId(item.getModelId());
	    		insertTrgVo.setEnggCnt0(item.getEnggCnt0());
	    		insertTrgVo.setEnggCnt12(item.getEnggCnt12());
	    		insertTrgVo.setEnggCnt18(item.getEnggCnt18());
	    		insertTrgVo.setEnggCnt24(item.getEnggCnt24());
	    		insertTrgVo.setEnggCnt30(item.getEnggCnt30());
	    		insertTrgVo.setEnggCnt36(item.getEnggCnt36());	
			}
    		orgnSetExl.add(item.getOrgnId());
			socSetExl.add(item.getRateCd());
			addSetExl.add(item.getSoc());
		}
		//End OF FOR ---------------------------
		if(!lastInsertRegNo.equals(itemList.get(itemList.size()-1).getRegNum())){
			List<String> insertOrgnList = new ArrayList<String>(orgnSetExl);
			List<String> insertSocList = new ArrayList<String>(socSetExl);
			List<String> insertAddList = new ArrayList<String>(addSetExl);
			
			insertTrgVo.setOrgnList(insertOrgnList);
			insertTrgVo.setSocList(insertSocList);
			insertTrgVo.setAddList(insertAddList);
			
			try {
				duplicateDisPrmtChk(insertTrgVo, "EXCEL");
			}catch (MvnoRunException e){
				if("exlDup".equals(e.getMessage().substring(0,6))) {
					throw new MvnoRunException(1,"엑셀데이터 내 번호["+ insertTrgVo.getRegNum() +"] 은 번호["+ e.getMessage().substring(6) +"] 와 중복되어 업로드 할수 없습니다.");
				}
				else {
					throw new MvnoRunException(-1,"엑셀데이터 번호["+ insertTrgVo.getRegNum() +"] 는 "+"이미 등록된 프로모션["+ e.getMessage() +"]이 존재합니다.");
				}
				
			}
			// **********************Chk 테이블로 INSERT **********************
    		// 엑셀데이터 평생할인 마스터 Chk테이블에 추가
	    	try {
				disPrmtMgmtMapper.insertDisPrmtMstChk(insertTrgVo);
				// 엑셀데이터 평생할인 조직 Chk테이블에 추가
				for(int idx=0; idx<insertTrgVo.getOrgnList().size(); idx++){
						insertTrgVo.setOrgnId(insertTrgVo.getOrgnList().get(idx));
						disPrmtMgmtMapper.insertDisPrmtOrgChk(insertTrgVo);
				}
				// 엑셀데이터 평생할인 요금제 Chk테이블에 추가
				for(int idx=0; idx<insertTrgVo.getSocList().size(); idx++){
					insertTrgVo.setRateCd(insertTrgVo.getSocList().get(idx));
					disPrmtMgmtMapper.insertDisPrmtSocChk(insertTrgVo);
				}
				// 엑셀데이터 평생할인 부가서비스 Chk테이블에 추가
				for(int idx=0; idx<insertTrgVo.getAddList().size(); idx++){
					insertTrgVo.setSoc(insertTrgVo.getAddList().get(idx));
					disPrmtMgmtMapper.insertDisPrmtAddChk(insertTrgVo);
				}
			} catch (Exception e) {	
				throw new MvnoRunException(-1,"엑셀업로드 중 오류가 발생했습니다.");
			}
	    	insertCnt++;
		}
		// **********************실 테이블로 INSERT **********************
		
		disPrmtMgmtMapper.insertDisPrmtMstExl();    // Chk데이터 마스터 실테이블에 추가
		disPrmtMgmtMapper.insertDisPrmtOrgExl(); 	 // Chk데이터 조직 실테이블에 추가
		disPrmtMgmtMapper.insertDisPrmtSocExl();	 // Chk데이터 요금제 실테이블에 추가
		disPrmtMgmtMapper.insertDisPrmtAddExl();	 // Chk데이터 부가서비스 실테이블에 추가
		
		// **********************Chk 테이블 DELETE **********************
		
		disPrmtMgmtMapper.deleteDisPrmtMstChk();  	 // 마스터 Chk테이블 DELETE
		disPrmtMgmtMapper.deleteDisPrmtOrgChk(); 	 // 조직 Chk테이블 DELETE
		disPrmtMgmtMapper.deleteDisPrmtSocChk(); 	 // 요금제 Chk테이블 DELETE
		disPrmtMgmtMapper.deleteDisPrmtAddChk(); 	 // 부가서비스 Chk테이블 DELETE
		
		return insertCnt;
    }
    
    // 프로모션 정책 엑셀 업로드 중복 검사
    private void duplicateDisPrmtChk(DisPrmtMgmtVO disPrmtMgmtVO, String typeCd) {

		List<DisPrmtMgmtVO> aryDupChkList = new ArrayList<DisPrmtMgmtVO>();
		if("EXCEL".equals(typeCd)){
			aryDupChkList = disPrmtMgmtMapper.getDisPrmtDupByInfoExl(disPrmtMgmtVO);
		}else{
			aryDupChkList = disPrmtMgmtMapper.getDisPrmtDupByInfo(disPrmtMgmtVO);
		}

		if(KtisUtil.isNotEmpty(aryDupChkList)){
			// 중복된 조직, 요금제가 존재하는 지 확인
			for(int idx1 = 0; idx1 < disPrmtMgmtVO.getOrgnList().size(); idx1++){
				for(int idx2 = 0; idx2 < disPrmtMgmtVO.getSocList().size(); idx2++){
					for(int idx3 = 0; idx3 < aryDupChkList.size(); idx3++){
						if(aryDupChkList.get(idx3).getOrgnId().equals(disPrmtMgmtVO.getOrgnList().get(idx1))
								&& aryDupChkList.get(idx3).getRateCd().equals(disPrmtMgmtVO.getSocList().get(idx2))){
							String strChkPrmtId = aryDupChkList.get(idx3).getPrmtId();
							String exlChkRegNum = aryDupChkList.get(idx3).getRegNum();
							if("2".equals(aryDupChkList.get(idx3).getTypeCd())){
								throw new MvnoRunException(-1, "exlDup"+exlChkRegNum);
							}
							else {
								throw new MvnoRunException(-1, strChkPrmtId );
							}
							
						}
					}
				}
			}
		}
		disPrmtMgmtVO.serialEngg(); //enggCnt -> enggSrl 변환(합치기) "00 ,12 " -> "0012"
    }
    // 평생할인 정책 목록 콤보 조회
    public List<EgovMap> getDisPrmtListCombo(DisPrmtMgmtVO vo) {
    	if(KtisUtil.isEmpty(vo.getOperType()) || KtisUtil.isEmpty(vo.getSlsTp()) || KtisUtil.isEmpty(vo.getReqBuyType()) ||
    	   KtisUtil.isEmpty(vo.getEnggSrl()) ||  KtisUtil.isEmpty(vo.getOrgnId()) || KtisUtil.isEmpty(vo.getRateCd())){
    		throw new MvnoRunException(-1, "필수 데이터가 없습니다" );
    	    	}

        return  disPrmtMgmtMapper.getDisPrmtListCombo(vo);
    }
    
    public List<DisPrmtMgmtVO> getDisChoChrgPrmtListExcelDown(DisPrmtMgmtVO searchVO) throws EgovBizException {
		List<DisPrmtMgmtVO> prmtExcelList = disPrmtMgmtMapper.getDisChoChrgPrmtListExcelDown(searchVO);
		return prmtExcelList;
	}
}
