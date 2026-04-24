package com.ktis.msp.batch.job.dis.dismgmt.service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.batch.job.dis.dismgmt.mapper.DisTrgFnlMapper;
import com.ktis.msp.batch.job.dis.dismgmt.vo.DisTrgFnlVO;

import com.ktis.msp.batch.job.dis.dismgmt.vo.DisVO;
import com.ktis.msp.batch.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DisTrgFnlService extends BaseService {

    @Autowired
    private DisTrgFnlMapper disTrgFnlMapper;

    private static final String SUCC_MSG= "성공";
    private static final String ERR0001_MSG= "비활성 회원(정지/해지)";
    private static final String ERR0002_MSG= "M포탈 기처리(요금제변경)";
    private static final String ERR0003_MSG= "M포탈 기처리(개통)";
    private static final String ERR0004_MSG= "추가 전문 발생 대상";
    private static final String ERR0005_MSG= "심플할인 가입 취소 대상";
    private static final String ERR0006_MSG= "우수기변 취소 대상";
    private static final String ERR0007_MSG= "프로모션 조회 조건 누락/미충족";
    private static final String ERR0008_MSG= "프로모션 미존재";
    private static final String ERR0009_MSG= "프로모션 다건 조회";
    private static final String ERR0010_MSG=  "부가서비스 중복 가입";
    private static final String ERR0011_MSG = "오류발생";

    /**
     * 프로모션 적용대상 상태 update (T: 진행중)
     * @return int
     */
    public int updateDisTrgMstStatusIng(DisVO disVo) {

        // 1. 추출대상 상태값 UPDATE (T)
        disVo.setProcYn("T");

        int updCnt= disTrgFnlMapper.updateDisTrgMst(disVo);
        LOGGER.info("#### [updateDisTrgMstStatusIng] 프로모션 적용대상 T상태 update cnt >> {}", updCnt);

        return updCnt;
    }

    /**
     * 평생할인 프로모션 처리
     * @return int
     */
    public int procDisTrgFnl(DisVO disVo) {

        int procCnt = 0;  // 평생할인 프로모션 처리대상 insert cnt (MSP_DIS_TRG_FNL)
        int procSubCnt = 0; // 평생할인 프로모션 처리대상 상세 update cnt (MSP_DIS_TRG_FNL_INFO) - 로그 확인용

        // 1. 기적용 테이블의 계약번호 UPDATE(계약번호가 없는 대상)
        int apdUpdCnt= disTrgFnlMapper.updateDisApdCntrNum(disVo);
        LOGGER.info("#### 기적용(MSP_DIS_APD) 계약번호 UPDATE CNT >> {}", apdUpdCnt);

        // 2. MSP_DIS_TRG_MST 처리상태가 T인 대상 전체 MSP_DIS_TRG_FNL_INF INSERT
        disTrgFnlMapper.insertDisTrgFnlInfo(disVo);

        // 3. MSP_DIS_TRG_MST 추출대상 전체 처리상태 Y로 변경
        disVo.setProcYn("Y");
        disTrgFnlMapper.updateDisTrgMst(disVo);

        // 4. 프로모션 가입대상 1차 제외 (통합 처리) -> MSP_DIS_TRG_FNL_INF 처리상태 변경
        HashMap<String, String> paramMap = new HashMap<String, String>();

        // 4-1. 비활성 회원[0001]
        paramMap.put("rsltCd", "0001");
        paramMap.put("prcsSbst", ERR0001_MSG);
        procSubCnt+= disTrgFnlMapper.updateDisTrgFnlInfo(paramMap);

        // 4-2. M포탈 요금제변경 기처리 회원[0002]
        paramMap.put("rsltCd", "0002");
        paramMap.put("prcsSbst", ERR0002_MSG);
        procSubCnt+= disTrgFnlMapper.updateDisTrgFnlInfo(paramMap);

        // 4-3. M포탈 셀프개통 기처리 회원[0003]
        paramMap.put("rsltCd", "0003");
        paramMap.put("prcsSbst", ERR0003_MSG);
        procSubCnt+= disTrgFnlMapper.updateDisTrgFnlInfo(paramMap);

        // 4-4. 프로모션 최종 전문이 아닌 경우[0004]
        paramMap.put("rsltCd", "0004");
        paramMap.put("prcsSbst", ERR0004_MSG);
        procSubCnt+= disTrgFnlMapper.updateDisTrgFnlInfo(paramMap);

        // 4-5. 전문이 SIM이지만 배치 실행 시점에서 심플약정 해지한 경우[0005]
        paramMap.put("rsltCd", "0005");
        paramMap.put("prcsSbst", ERR0005_MSG);
        procSubCnt+= disTrgFnlMapper.updateDisTrgFnlInfo(paramMap);

        // 4-6. 전문이 C07 우수기변이지만 배치 실행 시점에서 우수기변 취소한 경우[0006]
        paramMap.put("rsltCd", "0006");
        paramMap.put("prcsSbst", ERR0006_MSG);
        procSubCnt+= disTrgFnlMapper.updateDisTrgFnlInfo(paramMap);

        LOGGER.info("#### 평생할인 프로모션 1차 제외 대상 CNT >> {}", procSubCnt);

        // 5. 프로모션 가입대상 2차 제외 (각각 처리)
        // 5-1. 고객들의 프로모션 상세조건 조회
        List<DisTrgFnlVO> disTrgFnlInfoList= disTrgFnlMapper.getProcDisTrgFnlInfo();
        if(disTrgFnlInfoList==null || disTrgFnlInfoList.isEmpty()) return 0;

        for(DisTrgFnlVO disTrgFnlInfoVO : disTrgFnlInfoList){

            try{

                disTrgFnlInfoVO.setBaseDate(disVo.getEndDt()); // 배치 기준날짜 세팅

                // 5-2. 각 반복 시점 기준으로 최종 이벤트 확인
                boolean dateCondition= this.compareDateWithApd(disTrgFnlInfoVO);
                if(!dateCondition){ // 프로모션 가입 조건 미충족
                    disTrgFnlInfoVO.setRsltCd("0002");
                    disTrgFnlInfoVO.setPrcsSbst(ERR0002_MSG);
                    procSubCnt+= disTrgFnlMapper.updateEachDisTrgFnlInfo(disTrgFnlInfoVO);
                    continue;
                }

                // 5-3. 프로모션 조회 조건 검사 : 조건 미충족 시 프로모션 가입대상 제외
                boolean paramCondition= this.checkParamCondition(disTrgFnlInfoVO);
                if(!paramCondition){ // 프로모션 가입 조건 미충족
                    disTrgFnlInfoVO.setRsltCd("0007");
                    disTrgFnlInfoVO.setPrcsSbst(ERR0007_MSG);
                    procSubCnt+= disTrgFnlMapper.updateEachDisTrgFnlInfo(disTrgFnlInfoVO);
                    continue;
                }

                // 5-4. 프로모션 찾기
                String prmtId= this.findDisPrmtId(disTrgFnlInfoVO);

                if("".equals(StringUtil.NVL(prmtId, ""))){ // 프로모션 미존재
                    disTrgFnlInfoVO.setRsltCd("0008");
                    disTrgFnlInfoVO.setPrcsSbst(ERR0008_MSG);
                }else if(prmtId.split(",").length > 1){ // 프로모션 다건 존재 (콤마로 구분)
                    disTrgFnlInfoVO.setRsltCd("0009");
                    disTrgFnlInfoVO.setPrcsSbst(ERR0009_MSG+" [" + prmtId +"]");
                }else{
                    disTrgFnlInfoVO.setRsltCd("0000");
                    disTrgFnlInfoVO.setPrcsSbst(SUCC_MSG);
                    disTrgFnlInfoVO.setPrmtId(prmtId);
                }

                // 5-5. 프로모션 찾기 결과 상세정보에 UPDATE
                procSubCnt+= disTrgFnlMapper.updateEachDisTrgFnlInfo(disTrgFnlInfoVO);

                if(!"0000".equals(disTrgFnlInfoVO.getRsltCd())) continue; // 찾기 결과가 성공이 아닌 경우 continue

                // 5-6. 부가서비스 중복검사 (프로모션 부가서비스를 하나라도 가지고 있는지 체크)
                boolean socCondition= this.checkDuplicateSoc(disTrgFnlInfoVO);
                if(!socCondition){ // 프로모션 가입 조건 미충족
                    disTrgFnlInfoVO.setRsltCd("0010");
                    disTrgFnlInfoVO.setPrcsSbst(ERR0010_MSG);
                    procSubCnt+= disTrgFnlMapper.updateEachDisTrgFnlInfo(disTrgFnlInfoVO);
                    continue;
                }

                // 5-7. 프로모션 가입 대상에 INSERT(MSP_DIS_TRG_FNL)
                DisTrgFnlVO disTrgFnlVO= new DisTrgFnlVO();
                BeanUtils.copyProperties(disTrgFnlInfoVO, disTrgFnlVO, "procYn", "regstId");
                procCnt+= disTrgFnlMapper.insertDisTrgFnl(disTrgFnlVO);

                // 5-8. 평생할인 부가서비스 가입 배치 호출 (CMN_BATCH_REQUEST 테이블 INSERT)
                String batchExecParam = "{\"trgFnlSeq\":" + "\"" + disTrgFnlVO.getTrgFnlSeq() + "\"}";
                LOGGER.info("#### 평생할인 부가서비스 가입 배치 호출 파라미터 >> {}", batchExecParam);
                DisTrgFnlVO batchTwoVO = new DisTrgFnlVO();
                batchTwoVO.setBatchId("BATCH00215");
                batchTwoVO.setExecParam(batchExecParam);
                disTrgFnlMapper.insertBatchRequest(batchTwoVO);

                Thread.sleep(1000);

            }catch(Exception e) {
                LOGGER.error("#### 평생할인 부가서비스 가입 대상 처리 오류. 상세 이력 시퀀스 = [{}]", disTrgFnlInfoVO.getTrgFnlInfSeq());

                disTrgFnlInfoVO.setRsltCd("0011");
                disTrgFnlInfoVO.setPrcsSbst(ERR0011_MSG);
                procSubCnt+= disTrgFnlMapper.updateEachDisTrgFnlInfo(disTrgFnlInfoVO);
            }

        } // end of for-------------------------------------

        LOGGER.info("#### 평생할인 프로모션 FNL INSERT CNT >> {}", procCnt);
        LOGGER.info("#### 평생할인 프로모션 FNL_INF UPDATE CNT >> {}", procSubCnt);

        return procCnt;
    }

    /**
     * 프로모션 찾기 위한 조건 검사
     * @param disTrgFnlVO
     * @return boolean
     */
    private boolean checkParamCondition(DisTrgFnlVO disTrgFnlVO) {

        // 프로모션 가입 가능인 경우 true, 불가능인 경우 false
        // 1. 업무구분, 이벤트 발생일, 요금제, 프로모션 처리 대리점 필수존재
        if(disTrgFnlVO == null
           || StringUtil.isEmpty(disTrgFnlVO.getEvntCd())
           || StringUtil.isEmpty(disTrgFnlVO.getEffectiveDate())
           || StringUtil.isEmpty(disTrgFnlVO.getRateCd())
           || StringUtil.isEmpty(disTrgFnlVO.getPrmtAgntCd())){
            return false;
        }

        // 2. 가입유형 확인 > SLS_TP
        // 2-1. 가입유형이 UU2인 경우(심플할인): 약정기간 필수존재
        // 2-2. 가입유형이 MM1, MM2인 경우: 약정기간 종료여부 확인, 약정기간이 남은 경우 약정기간 필수존재
        // 2-3. 가입유형이 UU1인 경우: 무약정 처리
        String slsTp= disTrgFnlVO.getSlsTp();

        if("UU1".equals(slsTp)){
            disTrgFnlVO.setEnggMnthCnt("00"); // 약정기간 00으로 세팅
        }else if("UU2".equals(slsTp)){
            if("".equals(StringUtil.NVL(disTrgFnlVO.getEnggMnthCnt(), ""))) return false;            
        }else if("MM1".equals(slsTp)) {
    		// 약정기간 종료 여부 확인
            if(disTrgFnlMapper.checkEnggDt(disTrgFnlVO) <= 0){ // 약정기간 종료여부 확인
                disTrgFnlVO.setSlsTp("UU1");
                disTrgFnlVO.setEnggMnthCnt("00");
            }else {
                if("".equals(StringUtil.NVL(disTrgFnlVO.getEnggMnthCnt(), ""))) return false;
            }
        }else if("MM2".equals(slsTp)) { //20240220 MM1과 MM2 분리 - MM2는 BL_DC_TYPE가 없어서 VW_CNTR_ENGG_SELF로 조회 불가능
        	//NAC가 아닐때에만 체크(NAC는 MM2 그대로 사용)
        	if(!"NAC".equals(disTrgFnlVO.getEvntCd())) {        		
        		if(StringUtil.isBlank(disTrgFnlVO.getEnggMnthCnt())) { //약정개월이 없으면 할부가 끝난 것으로 UU1으로 set
        			disTrgFnlVO.setSlsTp("UU1");
                    disTrgFnlVO.setEnggMnthCnt("00");
        		}else { //약정 개월이 있으면 약정이 끝났는지 계산
        			if(disTrgFnlMapper.checkEnggDt(disTrgFnlVO) <= 0) { //약정이 끝났으면 UU1으로 set
        				disTrgFnlVO.setSlsTp("UU1");
                        disTrgFnlVO.setEnggMnthCnt("00");
        			}
        		}        		
        	}        	
        }else {
            return false;
        }

        return true;
    }

    /**
     * 프로모션 조회
     * @param disTrgFnlInfoVO
     * @return String
     */
    private String findDisPrmtId(DisTrgFnlVO disTrgFnlInfoVO) {

        List<String> findPrmtList=  disTrgFnlMapper.findDisPrmtId(disTrgFnlInfoVO);;

        // 프로모션 조회 결과 없음
        if(findPrmtList == null || findPrmtList.isEmpty()){

            // 특정단말로 조회한 결과 프로모션이 없는경우 > 전체단말로 재조회
            if(("MM1".equals(disTrgFnlInfoVO.getSlsTp()) || "MM2".equals(disTrgFnlInfoVO.getSlsTp()))
               && !"".equals(StringUtil.NVL(disTrgFnlInfoVO.getModelId(), ""))){

                String modelIdTemp= disTrgFnlInfoVO.getModelId();
                disTrgFnlInfoVO.setModelId(null);
                findPrmtList= disTrgFnlMapper.findDisPrmtId(disTrgFnlInfoVO);
                disTrgFnlInfoVO.setModelId(modelIdTemp);

                if(findPrmtList == null || findPrmtList.isEmpty()) return null;
            }else{
                return null;
            }
        }

        StringBuilder sb= new StringBuilder();
        for(int i=0;i<findPrmtList.size();i++){
            if(i > 0) sb.append(",");
            sb.append(findPrmtList.get(i));
        }

        return sb.toString();
    }

    /**
     * 기처리 이력과 평생할인 프로모션 타켓의 날짜 비교
     * @param disTrgFnlInfoVO
     * @return boolean
     */
    private boolean compareDateWithApd(DisTrgFnlVO disTrgFnlInfoVO){

        // 프로모션 가입 가능인 경우 true, 불가능인 경우 false

        // 전문 내 EFFDT와 최근 기적용 발생일 날짜 비교
        int result= disTrgFnlMapper.compareDateWithApd(disTrgFnlInfoVO);
        return (result > 0) ? false : true;
    }

    /**
     * 부가서비스 중복 검사 (프로모션 부가서비스를 가지고 있는지 확인)
     * @param disTrgFnlInfoVO
     * @return boolean
     */
    private boolean checkDuplicateSoc(DisTrgFnlVO disTrgFnlInfoVO) {

        int result= disTrgFnlMapper.checkDuplicateSoc(disTrgFnlInfoVO);
        return (result > 0) ? false : true;
    }

    /**
     * 평생할인 정책 적용된 고객정보 insert 배치호출 (BATCH4)
     * @param DisVO
     */
    public void insertBatchCus(DisVO disVo) {
    	
        DisTrgFnlVO batchFourVO = new DisTrgFnlVO();
        batchFourVO.setBatchId("BATCH00214");
        batchFourVO.setExecParam("{\"WORK_DT\":" + "\"" + disVo.getEndDt() + "\"}");
        disTrgFnlMapper.insertBatchRequest(batchFourVO);
        
    }
}
