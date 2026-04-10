/**
 *
 */
package com.ktmmobile.msf.domains.form.form.servicechange.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ktmmobile.msf.domains.form.common.dto.db.McpMrktHistDto;
import com.ktmmobile.msf.domains.form.common.dto.db.McpRequestAgrmDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.BillWayChgDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpRateChgDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpRetvRststnDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.NmcpProdImgDtlDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.SuspenChgTmlDto;

/**
 * @author ANT_FX700_02
 *
 */
public interface MypageDao {



    /**
    * @Description : 휴대폰 모델 ID 에 따른 이미지 경로를 가지고 온다.
    * @param modelId
    * @return NmcpProdImgDtlDto
    * @Author : ant
    * @Create Date : 2016. 1. 22.
    */
    public NmcpProdImgDtlDto selectHpImgPath(String modelId);

    /**
     * @Description : 접속한 횟수를 가져온다
     * @param modelId
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    public McpRetvRststnDto retvRstrtn(HashMap<String, String> map);

    /**
     * @Description : 접속한 횟수를 설정한다
     * @param modelId
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    public void retvRstrtnInsert(HashMap<String, String> map);

    /**
     * @Description : 접속제한 횟수감소
     * @param modelId
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    public void retvRstrtnUpCnt(HashMap<String, String> map);

    /**
     * @Description : 접속한 횟수, 접속일자 현재일자로 초기화
     * @param modelId
     * @return int
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    public void retvRstrtnUpSysDate(HashMap<String, String> map);


    /**
     * @Description : 제주항공 회원 아이디 가지고 오기
     * @param String contractNum
     * @return String
     * @Author : ant
     * @Create Date : 2016. 2. 20.
     */
    public String selectJejuid(String contractNum);

    /**
     * @Description : 제주항공 회원 아이디 기간 종료 업데이트
     * @param HashMap<String, Stirng> map
     * @return void
     * @Author : ant
     * @Create Date : 2016. 2. 20.
     */
    public void updatePointMgmt(Map<String,String> map);

    /**
     * @Description : 제주항공 회원 아이디 신규 인서트
     * @param HashMap<String, Stirng> map
     * @return void
     * @Author : ant
     * @Create Date : 2016. 2. 20.
     */
    public void insertPointMgmt(Map<String,String> map);

    /**
     * @Description : 원부증명서 가지고 오기
     * @param String userId
     * @return Map<String, String>
     * @Author : ant
     * @Create Date : 2016. 2. 22.
     */
    public Map<String, Object> selectCertHist(String userId);

    /**
     * @Description : 원부증명서 신규 인서트
     * @param HashMap<String, Stirng> map
     * @return void
     * @Author : ant
     * @Create Date : 2016. 2. 22.
     */
    public void insertCertHist(Map<String,String> map);

    /**
     * @Description : 단말 할부개월, 단말 할부원금 가지고 오기
     * @param String ncn
     * @return Map<String, String>
     * @Author : ant
     * @Create Date : 2016. 2. 22.
     */
    public Map<String, BigDecimal> selectModelSaleInfo(String ncn);



    /**
     * @Description : 요금제 변경시 처리상태 저장
     * @param mcpRateChgDto
     * @return McpRateChgDto
     */
    public McpRateChgDto inertNmcpRateChg(McpRateChgDto mcpRateChgDto);

    /**
     * @Description : 금융제휴요금제변경이력 업데이트
     * @param mcpRateChgDto
     * @return int
     */
    public int updateNmcpRateChg(McpRateChgDto mcpRateChgDto);

    /**
     * @Description : 금융제휴요금제변경이력 조회
     * @param mcpRateChgDto
     * @return int
     */
    public McpRateChgDto selectNmcpRateChg(McpRateChgDto mcpRateChgDto);

    /**
     * @Description : 금융제휴요금제변경 서식지 합본 프로시져 호출
     * @param mcpRateChgDto
     * @return int
     */
    public HashMap<Object, Object> callMcpRateChgImg(McpRateChgDto mcpRateChgDto);

    /**
     * @Description : 금융제휴요금제변경이력 목록 조회
     * @param mcpRateChgDto
     * @return int
     */
    public List<McpRateChgDto> selectNmcpRateChgList(McpRateChgDto mcpRateChgDto);

    /**
     * @Description : 사용량 조회 서비스 2시간동안 조회 횟수 조회
     * @param mcpRateChgDto
     * @return int
     */
    public int selectCallSvcLimitCount(HashMap<String, String> hm);

    /**
     * @Description : 사용량 조회 로그 저장
     * @param mcpRateChgDto
     * @return int
     */
    public void insertMcpSelfcareStatistic(HashMap<String, String> hm);


    /**
     * @Description : 재약정 요청 정보
     * @param McpRequestAgrmDto
     * @return void
     * @Author : papier
     * @Create Date : 2019. 10. 02.
     */
    public boolean insertMcpRequestArm(McpRequestAgrmDto mcpRequestAgrmDto);


    /**
     * @Description : 추천 아이디 조회
     * @param
     * @return  COMMEND_ID_
     * @Author : papier
     * @Create Date : 2020. 07. 17.
     */
    public String getCommendId(String contractNum);


     public List<SuspenChgTmlDto> selectSuspenChgTmp(SuspenChgTmlDto suspenChgTmlDto);
     public List<SuspenChgTmlDto> selectSuspenChgTmpList();
     public int suspenChgUpdate(SuspenChgTmlDto reqDto);


     /**
      * @Description : 서비스 변경 이력 저장 처리
      * @param
      * @return boolean
      * @Author : papier
      * @Create Date : 2021. 11. 05.
      */
     public boolean insertServiceAlterTrace(McpServiceAlterTraceDto serviceAlterTrace) ;

     public void insertMcpBillwayResend(BillWayChgDto billWayChgDto);

    /**
     * @Description : 최근 60분 내 요금제 변경 성공 이력 확인
     * @param
     * @return int
     * @Author : papier
     * @Create Date : 2023. 05. 08.
     */
    public int checkAllreadPlanchgCount(McpServiceAlterTraceDto serviceAlterTrace) ;

    List<McpMrktHistDto> selectExistingConsent(String userId);

    /**
     * @Description : 동의 정보
     * @param
     * @return boolean
     * @Author : papier
     * @Create Date : 2025. 03. 31.
     */
    public McpMrktHistDto getMrktHistInfo(McpMrktHistDto mrktHist);

    /*
     * 평생할인 정상화 진행 요청 건
     */
    public List<SuspenChgTmlDto> selectChrgPrmtCheckTmp();
    public int updateChrgPrmtCheckTmp(SuspenChgTmlDto suspenChgTmlDto);
    public int insertSuspenChgTmp(SuspenChgTmlDto suspenChgTmlDto);


    /**
     * @Description : QoS 제공 여부 확인
     * @param
     * @return String
     * @Author : papier
     * @Create Date : 2025. 12. 10.
     */
    public Map<String, String> selectRateMst(String rateCd) ;
}
