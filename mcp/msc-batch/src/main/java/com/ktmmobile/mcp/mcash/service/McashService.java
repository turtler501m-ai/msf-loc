package com.ktmmobile.mcp.mcash.service;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.mcash.dao.McashDao;
import com.ktmmobile.mcp.mcash.dto.McashApiReqDto;
import com.ktmmobile.mcp.mcash.dto.McashApiResDto;
import com.ktmmobile.mcp.mcash.dto.McashDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class McashService {

    private static final Logger logger = LoggerFactory.getLogger(com.ktmmobile.mcp.mcash.service.McashService.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Value("${ext.url}")
    private String extUrl;

    @Autowired
    private McashDao mcashDao;

    /**
     * 설명 :   MCASH 해지연동 공통코드 기준일자
     */
    public Integer getBaseDate(){
        return mcashDao.getBaseDate();
    }

    /**
     * 설명 :   MCASH 전체 대상 리스트 처리중 처리 (T)
     */
    public int updateAllMcashCanTrg(int baseDate){
       return mcashDao.updateAllMcashCanTrg(baseDate);
    }

    /**
     * 설명 :   MCASH 미연동 대상 리스트 미대상처리 (X)
     */
    public int updateMcashNotCanTrg(){
       return mcashDao.updateMcashNotCanTrg();
    }

    /**
     * 설명 :   MCASH USER 테이블 정보로 해지테이블 업데이트
     */
    public int updateMcashCanTrgInfo(){
        return mcashDao.updateMcashCanTrgInfo();
    }

    /**
     * 설명 :   MCASH 연동 대상 리스트 추출
     */
    public List<McashDto> getMcashCanTrg() {
        List<McashDto> mcashCanTrgList = mcashDao.getMcashCanTrg();
        return mcashCanTrgList;
    }

    /**
     * 설명 :   MCASH 탈퇴 연동
     * @Date : 2024.07.30
     */
    public boolean syncUserInfo(McashDto mcashDto) {

        boolean rst = false;

        McashApiResDto mcashApiResDto = new McashApiResDto();
        Map<String,Object> paramMap = new HashMap<>();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(1000);
        factory.setReadTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(factory);

        // 필수 파라미터 체크 mcashDto
        if(mcashDto == null) return rst;

        McashApiReqDto mcashApiReqDto = new McashApiReqDto();

        // 연동이력 필수값
        mcashApiReqDto.setUserid(mcashDto.getUserid());
        mcashApiReqDto.setCustomerId(mcashDto.getCustomerId());
        mcashApiReqDto.setContractNum(mcashDto.getContractNum());
        mcashApiReqDto.setSvcCntrNo(mcashDto.getSvcCntrNo());
        mcashApiReqDto.setEvntType("CANCEL");
        mcashApiReqDto.setEvntTypeDtl(mcashDto.getEvntCd());

        // API 필수값
        // mcashApiReqDto.setReqApiNo("1500");                 // 요청전문번호 (ext 내 set )
        mcashApiReqDto.setPotalId(mcashDto.getUserid());       // 유저아이디
        mcashApiReqDto.setSvcContId(mcashDto.getSvcCntrNo());  // 서비스계약번호
        mcashApiReqDto.setWdYn("Y");                           // 탈회여부
        mcashApiReqDto.setWdDate(mcashDto.getEvntDt());        // 탈회일자

        try {
            mcashApiResDto = restTemplate.postForObject(extUrl + "/mcash/syncUserInfo.do", mcashApiReqDto, McashApiResDto.class);
            mcashApiResDto.setEvntCd(mcashDto.getEvntCd());          // 업무유형(전문 CAN, MCN, RCL, MCC)

            // 연동결과 확인 및 업데이트
            paramMap.put("canTrgSeq", mcashDto.getCanTrgSeq());
            paramMap.put("mcashSeq", mcashApiResDto.getMcashSeq());
            paramMap.put("rsltCd", mcashApiResDto.getRspCode());
            paramMap.put("rsltMsg", mcashApiResDto.getRspMsg());

            // 정상응답이 아닌경우
            if(!"200001".equals(mcashApiResDto.getRspCode())){
                paramMap.put("procYn", "E");   // 연동처리여부 Y(처리완료), N(미처리), T(처리중), E(연동에러)
                this.updateMcashCanTrg(paramMap);
            }
            // 정상응답의 경우
            else{
                paramMap.put("procYn", "Y");   // 연동처리여부 Y(처리완료), N(미처리), T(처리중), E(연동에러)
                this.updateMcashCanTrg(paramMap);

                paramMap.put("userid", mcashDto.getUserid());
                // M캐시 회원정보 변경
                paramMap.put("status", "C");
                paramMap.put("envtType", "CANCEL");
                paramMap.put("envtTypeDtl", mcashApiResDto.getEvntCd());
                this.updateMcashUserInfo(paramMap);

                // MCASH 회원정보이력 INSERT
                this.insertMcashUserHist(paramMap);
                rst = true;
            }

        } catch (Exception e) {
            paramMap.put("canTrgSeq", mcashDto.getCanTrgSeq());
            paramMap.put("procYn", "E");
            paramMap.put("rsltCd", "999999");
            paramMap.put("rsltMsg", e.getMessage());
            this.updateMcashCanTrg(paramMap);
            logger.error("=========== syncUserInfo() Exception = {}", e.getMessage());
            throw new McpCommonException(e.getMessage());
        }
        return rst ;
    }

    //M캐시 해지연동대상 변경
    public void updateMcashCanTrg(Map<String, Object> paramMap) {
        mcashDao.updateMcashCanTrg(paramMap);
    }

    //M캐시 회원정보 변경
    public void updateMcashUserInfo(Map<String, Object> paramMap) {
        mcashDao.updateMcashUserInfo(paramMap);
    }

    //MCASH 회원정보이력 INSERT
    public void insertMcashUserHist(Map<String, Object> paramMap) {
        mcashDao.insertMcashUserHist(paramMap);
    }


    /**
     * 설명 :   MCASH 해지연동 공통코드 기준일자
     */
    public Integer getRetryDate(){
        return mcashDao.getRetryDate();
    }

    /**
     * 설명 :   MCASH 전체 대상 리스트 처리중 재 처리 (T)
     */
    public int updateAllMcashCanRetryTrg(int baseDate){
        return mcashDao.updateAllMcashCanRetryTrg(baseDate);
    }

}
