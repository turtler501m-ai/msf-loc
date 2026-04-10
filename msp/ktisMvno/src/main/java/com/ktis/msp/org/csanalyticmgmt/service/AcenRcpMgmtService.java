package com.ktis.msp.org.csanalyticmgmt.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.cdmgmt.service.CmnCdMgmtService;
import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.csanalyticmgmt.mapper.AcenRcpMgmtMapper;
import com.ktis.msp.org.csanalyticmgmt.vo.AcenHistVO;
import com.ktis.msp.org.csanalyticmgmt.vo.AcenRcpMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpNewMgmtMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AcenRcpMgmtService extends BaseService {

  @Autowired
  private RcpNewMgmtMapper rcpNewMgmtMapper;

  @Autowired
  private MaskingService maskingService;

  @Autowired
  private AcenRcpMgmtMapper acenRcpMgmtMapper;

  @Autowired
  private CmnCdMgmtService cmnCdMgmtService;

  /** A'Cen 신청 리스트 조회 */
  public List<EgovMap> getAcenRcpMgmtList(AcenRcpMgmtVO searchVO, Map<String, Object> paramMap) {

    // 검색구분이 계약번호인 경우 : 서비스계약번호로 계약번호 추출
    if("2".equals(searchVO.getpSearchGbn())){
      String pContractNum = rcpNewMgmtMapper.getpContractNum(searchVO.getpSearchName());

      if(!KtisUtil.isEmpty(pContractNum)){
        searchVO.setpSearchName(pContractNum);
      }
    }

    List<EgovMap> list = acenRcpMgmtMapper.getAcenRcpMgmtList(searchVO);
    HashMap<String, String> maskFields = getMaskFields();
    maskingService.setMask(list, maskFields, paramMap);
    return list;
  }

  /**  A'Cen 연동이력 조회 */
  public List<AcenHistVO> getAcenHist(AcenRcpMgmtVO searchVO) {

    List<AcenHistVO> list = acenRcpMgmtMapper.getAcenHist(searchVO);
    return list;
  }

  /**  A'Cen 실패이력 조회 */
  public List<EgovMap> getAcenFailHist(AcenRcpMgmtVO searchVO, Map<String, Object> paramMap) {

    List<EgovMap> list = acenRcpMgmtMapper.getAcenFailHist(searchVO);
    HashMap<String, String> maskFields = getMaskFields();
    maskingService.setMask(list, maskFields, paramMap);
    return list;
  }

  /**  A'Cen 재실행이력 조회 */
  public List<EgovMap> getAcenRetryHist(AcenRcpMgmtVO searchVO, Map<String, Object> paramMap) {

    List<EgovMap> list = acenRcpMgmtMapper.getAcenRetryHist(searchVO);
    HashMap<String, String> maskFields = getMaskFields();
    maskingService.setMask(list, maskFields, paramMap);
    return list;
  }

  /**  A'Cen 재실행 가능여부 */
  public Map<String, String> acenRtyPreChk(AcenRcpMgmtVO searchVO) {

    Map<String, String> resultMap = new HashMap<String, String>();
    resultMap.put("resultCode", "9999");

    // A'Cen 재실행 가능여부 판단 데이터 조회
    Map<String, String> acenStatusMap = acenRcpMgmtMapper.getAcenProcStatus(searchVO.getRequestKey());

    if(acenStatusMap == null){
      resultMap.put("resultMsg", "신청정보가 존재하지 않습니다.");
      return resultMap;
    }

    String evntType = acenStatusMap.get("evntType");                  // 시나리오 유형
    String endStatus = acenStatusMap.get("endStatus");                // 종결여부
    String succYn = acenStatusMap.get("succYn");                      // 성공여부
    String openYn = acenStatusMap.get("openYn");                      // (acen) 개통여부
    String pstate = acenStatusMap.get("pstate");                      // 신청서상태
    String requestStateCode = acenStatusMap.get("requestStateCode");  // 진행상태
    String reqUsimSn = acenStatusMap.get("reqUsimSn");                // 유심번호
    String dlvryType = acenStatusMap.get("dlvryType");                // 배송유형

    // 재실행 불가 케이스
    // 1) 해피콜 프로세스 미종결
    if(!"Y".equals(endStatus)){
      resultMap.put("resultMsg", "재실행 요청 처리가 불가합니다.<br/><br/>(A'Cen 해피콜 진행중인 신청건)");
      return resultMap;
    }

    // 2) 해피콜 성공
    if(!"N".equals(succYn)){
      resultMap.put("resultMsg", "재실행 요청 처리가 불가합니다.<br/><br/>(A'Cen 해피콜 정상처리된 신청건)");
      return resultMap;
    }

    // 3) 신청상태 : 자동화실패 또는 정상인 경우만 처리
    if(!"50".equals(pstate) && !"00".equals(pstate)){
      resultMap.put("resultMsg", "재실행 요청 처리가 불가합니다.<br/><br/>(정상 또는 자동화실패 상태인 신청건만 가능)");
      return resultMap;
    }

    // 4) 상담사 개입으로 개통완료된 경우
    if(!"SLF_CST_IDT".equals(evntType) && !"Y".equals(openYn) && "21".equals(requestStateCode)){
      resultMap.put("resultMsg", "재실행 요청 처리가 불가합니다.<br/><br/>(상담사 개통완료건)");
      return resultMap;
    }

    // 5) 재실행 불가 해피콜 시나리오
    if("NEW_NOSIM_02".equals(evntType)){
      resultMap.put("resultMsg", "재실행 요청 처리가 불가합니다.<br/><br/>(A'Cen 해피콜 재실행 불가 시나리오)");
      return resultMap;
    }

    // [긴급] 전날 사전체크 성공 후 당일 사전체크 재실행 시 실패떨어짐. 재실행 불가 해피콜 시나리오 추가 제한
    boolean isRtyAcenCall = false;
    if( (!"02".equals(dlvryType) && "NEW_NOSIM_01".equals(evntType))
        || "MNP_NOSIM_01".equals(evntType)
        || "MNP_NOSIM_02".equals(evntType)
        || "MNP_SIM_01".equals(evntType)
        || "MNP_SIM_02".equals(evntType)
        || "SLF_CST_IDT".equals(evntType)){
      isRtyAcenCall = true;
    }

    if(!isRtyAcenCall){
      resultMap.put("resultMsg", "재실행 요청 처리가 불가합니다.<br/><br/>(A'Cen 해피콜 재실행 불가 시나리오)");
      return resultMap;
    }

    // 재실행 될 해피콜 유형과 상태 세팅
    String nextPState = "00";          // 변경될 신청서상태
    String nextRequestStateCode = "";  // 변경될 진행상태
    String nextEvntType = evntType;    // 재실행될 시나리오
    String mnpPreChkYn = "N";          // MNP 사전인증 확인 필요 여부

    if("NEW_NOSIM_01".equals(evntType)){ // 신규가입 유심없음 1차해피콜

      if("02".equals(dlvryType) && !KtisUtil.isEmpty(reqUsimSn)){
        nextRequestStateCode = "13";   // 배송완료(유심등록완료)
      }else{
        nextRequestStateCode = "00";   // 접수대기
      }

    }else if("NEW_NOSIM_03".equals(evntType)){ // 신규가입 유심없음 3차해피콜

      if("21".equals(requestStateCode)){
        nextRequestStateCode = "21";   // 개통완료
      }else if("02".equals(dlvryType)){
        nextRequestStateCode = "13";   // 배송완료(유심등록완료)
        nextEvntType = "NEW_NOSIM_01";
      }else if(!KtisUtil.isEmpty(reqUsimSn)){
        nextRequestStateCode = "20";   // 개통대기
      }else{
        nextRequestStateCode = "09";   // 배송대기(택배)
      }

    }else if("NEW_SIM_01".equals(evntType)){ // 신규가입 유심있음 1차해피콜

      nextRequestStateCode = "00";     // 접수대기

    }else if("NEW_SIM_02".equals(evntType)){ // 신규가입 유심있음 2차해피콜

      if("21".equals(requestStateCode)){
        nextRequestStateCode = "21";   // 개통완료
      }else{
        nextRequestStateCode = "00";   // 접수대기
        nextEvntType = "NEW_SIM_01";
      }

    }else if("MNP_NOSIM_01".equals(evntType)){ // 번호이동 유심없음 1차해피콜

      if("02".equals(dlvryType) && !KtisUtil.isEmpty(reqUsimSn)){
        nextRequestStateCode = "13";  // 배송완료(유심등록완료)
      }else{
        nextRequestStateCode = "00";  // 접수대기
      }

    }else if("MNP_NOSIM_02".equals(evntType)){ // 번호이동 유심없음 2차해피콜

      if("02".equals(dlvryType)){
        nextRequestStateCode = "41";  // 사전인증
      }else if(!KtisUtil.isEmpty(reqUsimSn)){
        nextRequestStateCode = "11".equals(requestStateCode) || "40".equals(requestStateCode) ? "11" : "10";  // 배송완료 또는 배송중
      }else{
        nextRequestStateCode = "09";  // 배송대기(택배)
      }

    }else if("MNP_NOSIM_03".equals(evntType)){  // 번호이동 유심없음 3차해피콜

      if("21".equals(requestStateCode)){
        nextRequestStateCode = "21";   // 개통완료
      }else{
        nextRequestStateCode = "20";   // 개통대기
        mnpPreChkYn = "Y";
      }

    }else if("MNP_SIM_01".equals(evntType)){ // 번호이동 유심있음 1차해피콜

      nextRequestStateCode = "00";    // 접수대기

    }else if("MNP_SIM_02".equals(evntType)){ // 번호이동 유심있음 2차해피콜

      nextRequestStateCode = "41";    // 사전인증

    }else if("MNP_SIM_03".equals(evntType)){ // 번호이동 유심있음 3차해피콜

      if("21".equals(requestStateCode)){
        nextRequestStateCode = "21";   // 개통완료
      }else{
        nextRequestStateCode = "20";   // 개통대기
        mnpPreChkYn = "Y";
      }

    }else{ // 셀프개통 본인인증 해피콜

      nextRequestStateCode = "21";    // 개통완료
    }


    resultMap.put("resultCode", "0000");
    resultMap.put("resultMsg", "재실행 가능");
    resultMap.put("rtyEvntType", nextEvntType);
    resultMap.put("pstate", nextPState);
    resultMap.put("requestStateCode", nextRequestStateCode);
    resultMap.put("mnpPreChkYn", mnpPreChkYn);

    // alert를 위한 공통코드명 조회
    HashMap<String, String> cdParam = new HashMap<String, String>();
    cdParam.put("grpId", "RCP0006");
    cdParam.put("cdVal", nextRequestStateCode);
    Map<String,String> cdResult = cmnCdMgmtService.getCmnGrpCdMst(cdParam);
    resultMap.put("requestStateCodeNm", cdResult.get("CD_DSC"));

    cdParam.put("grpId", "RCP0005");
    cdParam.put("cdVal", nextPState);
    cdResult = cmnCdMgmtService.getCmnGrpCdMst(cdParam);
    resultMap.put("pstatNm", cdResult.get("CD_DSC"));

    CmnCdMgmtVo cmnCdMgmtVo = new CmnCdMgmtVo();
    cmnCdMgmtVo.setGrpId("AcenEvntType");
    cmnCdMgmtVo.setCdVal(nextEvntType);
    cdResult = cmnCdMgmtService.getMcpCommCdDtl(cmnCdMgmtVo);
    resultMap.put("rtyEvntTypeNm", cdResult.get("DTL_CD_NM"));

    // 재실행으로 변경되는 상태와 현재 상태 비교
    String isSameStatus = "N";
    if(nextRequestStateCode.equals(requestStateCode) && nextPState.equals(pstate)){
      isSameStatus = "Y";
    }

    resultMap.put("isSameStatus", isSameStatus);
    return resultMap;
  }

  /**  A'Cen 재실행 처리 */
  @Transactional(rollbackFor=Exception.class)
  public int updateAcenWithRty(Map<String, String> param) {

    int updCnt = 0;

    // acen 재실행 상태 update
    String preCheckYn = null;
    if (!"SLF_CST_IDT".equals(param.get("rtyEvntType")) && "21".equals(param.get("requestStateCode"))) {
      preCheckYn = "Y";
    }

    param.put("preCheckYn", preCheckYn);
    updCnt += acenRcpMgmtMapper.updateAcenWithRty(param);

    // 신청서 상태 update
    if(!"Y".equals(param.get("isSameStatus"))){
      updCnt += acenRcpMgmtMapper.updateMcpRequestState(param);
    }

    // 재실행 이력 insert
    updCnt += acenRcpMgmtMapper.insertAcenRtyHist(param);

    return updCnt;
  }

  private HashMap<String, String> getMaskFields() {
    HashMap<String, String> maskFields = new HashMap<String, String>();
    maskFields.put("cstmrNm", "CUST_NAME");
    maskFields.put("regstNm", "CUST_NAME");
    maskFields.put("rvisnNm", "CUST_NAME");

    return maskFields;
  }


}
