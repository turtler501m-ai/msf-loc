package com.ktis.msp.batch.job.dis.dismgmt.service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.batch.job.dis.dismgmt.mapper.DisPrcMapper;
import com.ktis.msp.batch.job.dis.dismgmt.vo.DisPrcVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.MplatformOpenService;
import com.ktis.msp.batch.util.StringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DisPrcService extends BaseService {

    @Autowired
    private DisPrcMapper disPrcMapper;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /**
     * 평생할인 프로모션 적용
     * @param trgFnlSeq
     * @return int
     */
    public int addDisPrcSoc(String trgFnlSeq){

        int procCnt = 0;
        Map<String, String> paraMap= new HashMap<String,String>();
        paraMap.put("trgFnlSeq", trgFnlSeq);

        // 1. 프로모션 부가서비스 가입 대상자 정보 조회
        DisPrcVO prcCstmrInfo = disPrcMapper.getPrcCstmr(trgFnlSeq);
        if(prcCstmrInfo == null){
            // 1-1. MSP_DIS_TRG_FNL 저리자, 처리일자 UPDATE
            disPrcMapper.updateTrgFnlProc(paraMap);
            return 0;
        }

        // 2. 프로모션 부가서비스 목록 조회
        List<String> prcSocList = disPrcMapper.getPrcSocList(prcCstmrInfo.getPrmtId());
        if(prcSocList == null || prcSocList.isEmpty()){
            // 2-1. MSP_DIS_TRG_FNL 저리자, 처리일자 UPDATE
            disPrcMapper.updateTrgFnlProc(paraMap);
            return 0;
        }

        // 3. 마스터 시퀀스 생성
        String prcMstSeq = disPrcMapper.getPrcMstSeq();

        // 4. 부가서비스 가입 처리
        int succCnt = 0;
        int failCnt = 0;

        // 4-1. 부가서비스 가입 연동하는 동안 변하지 않는 값 세팅
        DisPrcVO prcDtlVo = new DisPrcVO();
        BeanUtils.copyProperties(prcCstmrInfo, prcDtlVo);
        prcDtlVo.setPrcMstSeq(prcMstSeq);

        for(String prmtSoc : prcSocList) {

            // 4-2. DTL 시퀀스 생성
            String prcDtlSeq = disPrcMapper.getPrcDtlSeq();

            // 4-3. X21 연동 (단건)
            prcDtlVo.setSoc(prmtSoc);
            prcDtlVo.setPrcDtlSeq(prcDtlSeq);
            boolean mpResult = this.applyPrmtSoc(prcDtlVo);

            if(mpResult) succCnt++;
            else failCnt++;
        }

        // 5. 부가서비스 가입 이력 성공 개수 조회
        LOGGER.info("#### 평생할인 프로모션 부가서비스 SUCC CNT >> {}", succCnt);
        LOGGER.info("#### 평생할인 프로모션 부가서비스 FAIL CNT >> {}", failCnt);
        LOGGER.info("#### 평생할인 프로모션 부가서비스 SOC SIZE >> {}", prcSocList.size());

        String succYn = "N";
        if(prcSocList.size() == succCnt) succYn = "Y";

        // 6. MST 테이블 INSERT (조회해왔던 고객정보에 MST 처리결과 추가)
        prcCstmrInfo.setSuccYn(succYn);
        prcCstmrInfo.setPrcMstSeq(prcMstSeq);
        procCnt += disPrcMapper.insertPrmtAutoAddMst(prcCstmrInfo);

        // 7. MSP_DIS_TRG_FNL 저리상태 UPDATE
        paraMap.put("procYn", "Y");
        disPrcMapper.updateTrgFnlProc(paraMap);

        return procCnt;
    }

    /**
     * 평생할인 프로모션 부가서비스 가입연동  (X21 단건)
     * @param disPrcVO
     * @return boolean
     */
    private boolean applyPrmtSoc(DisPrcVO disPrcVO){

        int successCnt= 0;

        try{
            MplatformOpenService mp = new MplatformOpenService();

            // 1. 연동 파라미터 세팅
            HashMap<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("mdlInd",		"BAT");
            paramMap.put("custId",		disPrcVO.getCustomerId());
            paramMap.put("ncn",			disPrcVO.getNcn());
            paramMap.put("ctn",			disPrcVO.getSubscriberNo());
            paramMap.put("soc",			disPrcVO.getSoc());
            paramMap.put("appEventCd",	"X21");

            LOGGER.info("#### 평생할인 프로모션 부가서비스 가입연동 파라미터: " + paramMap.toString());

            // 2. 연동결과 맵 선언
            HashMap<String, String> resultMap = null;

            // 3. x21 연동 (MP연동 오류시 한번 더 실행)
            for(int reTryCount = 0; reTryCount < 2; reTryCount++){

                if(resultMap == null){
                    resultMap = mp.SelfCareCallService(paramMap, propertiesService.getString("mplatform.selfcare.url"));
                    /*
                        MP 호출 결과 케이스
                        CASE 1) responseXml이 비어있는 경우: resultMap != paramMap
                                - resultMap.get("result") = "response message is null."
                        CASE 2) responseXml이 비어있지 않은 경우:  resultMap == paramMap
                                - resultMap.get("result") = "SUCCESS"
                                - resultMap.get("resultKey") = "연동결과값"
                                - resultMap.get("resultType") = "연동결과값(N이면 성공)"
                                - resultMap.get("resultCd") = "연동결과값" > resultType이 N이면 공백
                                - resultMap.get("resultMsg") = "연동결과값" > resultType이 N이면 공백
                     */
                }
                else if ("ITL_SYS_E0001".equals(resultMap.get("resultCd"))){
                    // 3-1. MP 연동 오류인 경우 3초 뒤 실패 상세이력 저장 후 연동 재시도
                    Thread.sleep(3000);

                    // 3-2. 연동 실패 상세 이력 INSERT
                    disPrcVO.setGlobalNo(resultMap.get("resultKey"));
                    disPrcVO.setRsltCd(resultMap.get("resultCd"));
                    disPrcVO.setPrcsSbst(resultMap.get("resultMsg"));
                    disPrcMapper.insertPrmtAutoAddDtl(disPrcVO);

                    // 3-3. 연동 재시도
                    resultMap = mp.SelfCareCallService(paramMap, propertiesService.getString("mplatform.selfcare.url"));
                }
            } // end of for-------------------------------------------

            // 4. 성공/실패 상세 이력 저장
            if("N".equals(resultMap.get("resultType"))){
                // 성공인 경우
                successCnt++;
                disPrcVO.setGlobalNo(resultMap.get("resultKey"));
                disPrcVO.setRsltCd("0000");
                disPrcVO.setPrcsSbst("성공");
            }else{
                // 실패인 경우 (responseXml이 비어있는 경우 또는 연동결과값 실패인 경우)
                disPrcVO.setGlobalNo(StringUtil.NVL(resultMap.get("resultKey"),""));
                disPrcVO.setRsltCd(StringUtil.NVL(resultMap.get("resultCd"), "9998"));
                disPrcVO.setPrcsSbst(StringUtil.NVL(resultMap.get("resultMsg"),"서버(M-Platform) 점검중 입니다. 잠시후 다시 시도 하기기 바랍니다.(XML EMPTY)"));
            }

            disPrcMapper.insertPrmtAutoAddDtl(disPrcVO);

        }catch(Exception e){  // PRX 연동오류 발생

            // 5. 성공/실패 상세 이력 저장 - 연동오류
            disPrcVO.setGlobalNo("");
            disPrcVO.setRsltCd("9999");

            if("".equals(StringUtil.NVL(e.getMessage(), ""))) disPrcVO.setPrcsSbst("PRX 연동 오류");
            else disPrcVO.setPrcsSbst("PRX 연동 오류 ["+e.getMessage() + ("]"));
            disPrcMapper.insertPrmtAutoAddDtl(disPrcVO);
        }

        // 6. 연동결과 리턴
        return (successCnt > 0) ? true : false;
    }

}


