package com.ktmmobile.mcp.appform.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.storeusim.dto.MspSalePlcyMstDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ktmmobile.mcp.appform.dto.AppformReqDto;
import com.ktmmobile.mcp.appform.dto.FrndInviUsimDto;
import com.ktmmobile.mcp.appform.dto.IntmInsrRelDTO;
import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
import com.ktmmobile.mcp.appform.dto.McpRequestDto;
import com.ktmmobile.mcp.appform.mapper.AppformMapper;
import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;

@RestController
public class AppformController {

    private static final Logger logger = LoggerFactory.getLogger(AppformController.class);

    @Autowired
    AppformMapper appformMapper;


    /**
     * 동일명의 회선 90일 이내에에 개통/개통취소 이력이 10회
     * @param AppformReqDto
     * @return int
     */
    @RequestMapping(value = "/appform/checkLimitOpenFormCount", method = RequestMethod.POST)
    public int checkLimitOpenFormCount(@RequestBody AppformReqDto appformReqDto) {
        int checkLimitOpenFormCount = 0;

        try {
            checkLimitOpenFormCount = appformMapper.checkLimitOpenFormCount(appformReqDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return checkLimitOpenFormCount;
    }

    /**
     * 제주항공 요금제 여부 확인
     * @param rateCd
     * @return
     */
    @RequestMapping(value = "/appform/checkJejuCodeCount", method = RequestMethod.POST)
    public int checkJejuCodeCount(@RequestParam("contractNum") String rateCd) {

        int checkJejuCodeCount = 0;

        try {
            checkJejuCodeCount = appformMapper.checkJejuCodeCount(rateCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return checkJejuCodeCount;
    }

    /**
     * 서식지 관련 MSP 코드 조회
     * @param appformReqDto
     * @return
     */
    @RequestMapping(value = "/appform/mspPrdtCode", method = RequestMethod.POST)
    public McpRequestDto mspPrdtCode(@RequestBody AppformReqDto appformReqDto) {

        McpRequestDto mspPrdtCode = null;

        try {
            mspPrdtCode = appformMapper.selectMspPrdtCode(appformReqDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspPrdtCode;
    }

    /**
     * 서식지 등록 데이터 조회
     * @param requestKey
     * @return
     */
    @RequestMapping(value = "/appform/requestJoinDataByRjoinKeyNew", method = RequestMethod.POST)
    public Map<String, String> requestJoinDataByRjoinKeyNew(@RequestBody long requestKey) {

        Map<String, Object> joinKey = null;
        try {
            joinKey = appformMapper.selectRequestJoinDataByRjoinKeyNew(requestKey);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        if (joinKey == null) {
            return null;
        }


        Map<String,String> newMap =new HashMap<String,String>();
        for (Map.Entry<String, Object> entry : joinKey.entrySet()) {
            newMap.put(entry.getKey(), String.valueOf(entry.getValue()));
        }


        return newMap;
    }

    /**
     * 기기변경 고객정보 확인 일반기변 , 우수기변
     * @param juoSubInfoDto
     * @return
     */
    @RequestMapping(value = "/appform/selRMemberAjax", method = RequestMethod.POST)
    public JuoSubInfoDto selRMemberAjax(@RequestBody JuoSubInfoDto juoSubInfoDto) {

        JuoSubInfoDto selRMemberAjax = null;
        try {
            selRMemberAjax = appformMapper.selectSelRMemberAjax(juoSubInfoDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return selRMemberAjax;
    }

    /**
     * 가입계약정보 조회
     * @param juoSubInfoDto
     * @return
     */
    @RequestMapping(value = "/appform/selRMemberAjaxReal", method = RequestMethod.POST)
    public JuoSubInfoDto selRMemberAjaxReal(@RequestBody JuoSubInfoDto juoSubInfoDto) {

        JuoSubInfoDto selRMemberAjaxReal = null;

        try {
            selRMemberAjaxReal = appformMapper.selectSelRMemberAjaxReal(juoSubInfoDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return selRMemberAjaxReal;
    }

    /**
     * 휴대폰 기기 속성 정보 조회
     * @param appformReqDto
     * @return
     */
    @RequestMapping(value = "/appform/selPrdtcolCd", method = RequestMethod.POST)
    public String selPrdtcolCd(@RequestBody AppformReqDto appformReqDto) {

        String selPrdtcolCd = null;

        try {
            selPrdtcolCd = appformMapper.selectSelPrdtcolCd(appformReqDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return selPrdtcolCd;
    }

    /**
     * 오픈마켓 (외부서식지)
     * @param appformReqDto
     * @return
     */
    @RequestMapping(value = "/appform/marketRequest", method = RequestMethod.POST)
    public AppformReqDto marketRequest(@RequestBody AppformReqDto appformReqDto) {

        AppformReqDto marketRequest = null;

        try {
            marketRequest = appformMapper.selectMarketRequest(appformReqDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return marketRequest;
    }

    /**
     * 할부개월
     * @param appformReqDto
     * @return
     */
    @RequestMapping(value = "/appform/modelMonthlyList", method = RequestMethod.POST)
    public List<AppformReqDto> modelMonthlyList(@RequestBody AppformReqDto appformReqDto) {

        List<AppformReqDto> modelMonthlyList = null;

        try {
            modelMonthlyList = appformMapper.selectModelMonthlyList(appformReqDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return modelMonthlyList;
    }

    /**
     * 약정
     * @param appformReqDto
     * @return
     */
    @RequestMapping(value = "/appform/monthlyListMarket", method = RequestMethod.POST)
    public List<AppformReqDto> monthlyListMarket(@RequestBody AppformReqDto appformReqDto) {

        List<AppformReqDto> monthlyListMarket = null;

        try {
            monthlyListMarket = appformMapper.selectMonthlyListMarket(appformReqDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return monthlyListMarket;
    }

    /**
     * 색상정보
     * @param appformReqDto
     * @return
     */
    @RequestMapping(value = "/appform/prdtColorList", method = RequestMethod.POST)
    public List<AppformReqDto> prdtColorList(@RequestBody AppformReqDto appformReqDto) {

        List<AppformReqDto> prdtColorList = null;

        try {
            prdtColorList = appformMapper.selectPrdtColorList(appformReqDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return prdtColorList;
    }

    /**
     * 대리점 코드 패치
     * @param cntpntShopId
     * @return
     */
    @RequestMapping(value = "/appform/agentCode", method = RequestMethod.POST)
    public String agentCode(@RequestBody String cntpntShopId) {

        Map<String, String> agentMap = null;
        String agentCode = null;
        try {
            agentMap = appformMapper.selectAgentCode(cntpntShopId);
            agentCode = agentMap.get("KT_ORG_ID");

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return agentCode;
    }

    /**
     * 대리점 코드 패치
     * @param cntpntShopId
     * @return
     */
    @RequestMapping(value = "/appform/agentInfoOjb", method = RequestMethod.POST)
    public Map<String, String> agentInfoOjb(@RequestBody String cntpntShopId) {

        Map<String, String> agentMap = null;
        try {
            agentMap = appformMapper.selectAgentCode(cntpntShopId);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return agentMap;
    }

    /**
     * 보험 정보 조회
     * @return
     */
    @RequestMapping(value = "/appform/insrCodeList", method = RequestMethod.POST)
    public List<AppformReqDto> insrCodeList() {

        List<AppformReqDto> insrCodeList = null;

        try {
            insrCodeList = appformMapper.selectInsrCodeList();

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return insrCodeList;
    }

    /**
     * 분실파손 보험 조회
     * @param intmInsrRelDTO
     * @return
     */
    @RequestMapping(value = "/appform/insrProdList", method = RequestMethod.POST)
    public List<IntmInsrRelDTO> insrProdList(@RequestBody IntmInsrRelDTO intmInsrRelDTO) {

        List<IntmInsrRelDTO> insrProdList = null;

        try {
            insrProdList = appformMapper.selectInsrProdList(intmInsrRelDTO);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return insrProdList;
    }

    /**
     * 분실파손 보험 조회
     * @param intmInsrRelDTO
     * @return
     */
    @RequestMapping(value = "/appform/selectInsrProdList", method = RequestMethod.POST)
    public List<IntmInsrRelDTO> selectInsrProdList(@RequestBody IntmInsrRelDTO intmInsrRelDTO) {

        List<IntmInsrRelDTO> insrProdList = null;

        try {
            insrProdList = appformMapper.selectInsrProdList(intmInsrRelDTO);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return insrProdList;
    }



    /**
     * 고객CI정보에 대한 개통  정보 추출 [다회선 제한 기능]
     *  1. 셀프개통 여부
     *  2. 신규서식지
     *  3. 개통이 기준 +90일 이내
     * @param appformReqDto
     * @return
     */
    @RequestMapping(value = "/appform/limitForm", method = RequestMethod.POST)
    public AppformReqDto limitForm(@RequestBody AppformReqDto appformReqDto) {

        AppformReqDto limitForm = null;

        try {
            limitForm = appformMapper.selectLimitForm(appformReqDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return limitForm;
    }

    /**
     * 프로모션 관련 부가 서비스 등록 처리 (MCP_REQUEST_ADDITION)
     * @param appformReqDto
     * @return
     */
    @RequestMapping(value = "/appform/mcpRequestAdditionPromotion", method = RequestMethod.POST)
    public int mcpRequestAdditionPromotion(@RequestBody AppformReqDto appformReqDto) {

        int insertCount = 0;

        try {
            insertCount = appformMapper.insertMcpRequestAdditionPromotion(appformReqDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return insertCount;
    }

    /**
     * 유심 유효성 체크
     * @param usimNo 유심번호
     * @return count 데이터가 있으면 사용불가 유심
     */
    @RequestMapping(value = "/appform/checkValidUsimNo", method = RequestMethod.POST)
    public int checkValidUsimNo(@RequestBody String usimNo) {
        int rtnint = 0;
        try {
            rtnint = appformMapper.checkValidUsimNo(usimNo);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return rtnint;
    }

    @PostMapping("/appform/selectStolenIp")
    public int selectStolenIp(@RequestBody String stolenIp) {
        return this.appformMapper.selectStolenIp(stolenIp);
    }

    /**
     * 친구초대 유심 가입자 확인
     * @param frndInviUsimDto
     * @return
     */
    @RequestMapping(value = "/appform/chkCstmrInfo", method = RequestMethod.POST)
    public int chkCstmrInfo(@RequestBody FrndInviUsimDto frndInviUsimDto) {

        int chkCnt = 0;

        try {
            chkCnt = appformMapper.chkCstmrInfo(frndInviUsimDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return chkCnt;
    }

    /**
     * (셀프개통) 직영 프로모션 Id 조회
     * @param disApdParam
     * @return String
     */
    @RequestMapping(value = "/appform/getChrgPrmtId", method = RequestMethod.POST)
    public String getPrmtId(@RequestBody AppformReqDto disApdParam) {

        String prmtId = "";

        try {
            List<String> prmtIdList = appformMapper.getChrgPrmtId(disApdParam);
            if(prmtIdList != null && prmtIdList.size() != 0){
                prmtId = prmtIdList.get(0);
            }

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return prmtId;
    }


    /**
     * 평생할인 프로모션 기적용 테이블 insert
     * @param disApdParam
     * @return int
     */
    @RequestMapping(value = "/appform/insertDisPrmtApd", method = RequestMethod.POST)
    public int chkCstmrInfo(@RequestBody AppformReqDto disApdParam) {

        int reslt = 0;

        try {
            reslt = appformMapper.insertDisPrmtApd(disApdParam);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return reslt;
    }


    /**
     * (오프라인) 평생할인 프로모션ID 조회
     * @param disApdParam
     * @return String
     */
    @RequestMapping(value = "/appform/getDisPrmtId", method = RequestMethod.POST)
    public String getDisPrmtId(@RequestBody AppformReqDto disApdParam) {

        String prmtId = "";

        try {   //가입 유형 조회
                String slsTp = appformMapper.getDisPrmtSlsTp(disApdParam);
                disApdParam.setSlsTp(slsTp);

                // 오프라인 평생할인 프로모션 ID 조회
                List<String> prmtIdList = appformMapper.getDisPrmtId(disApdParam);
                if(prmtIdList == null || prmtIdList.size() == 0) {
                    //MM일때 프로모션 ID가 없다면, 프로모션 ID 한번 더 조회
                    if((!"".equals(disApdParam.getModelId()) || disApdParam.getModelId() != null)&& "MM".equals(disApdParam.getReqBuyType())){
                        String modelId = disApdParam.getModelId();
                        disApdParam.setModelId(null);
                        prmtIdList = appformMapper.getDisPrmtId(disApdParam);
                        disApdParam.setModelId(modelId);
                    }
                }
                if(prmtIdList != null && prmtIdList.size() != 0){
                    prmtId = prmtIdList.get(0);
                }

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return prmtId;
    }

    @RequestMapping(value = "/appform/existsAbuseImei", method = RequestMethod.POST)
    public boolean existsAbuseImei(@RequestBody String imei) {
        boolean exists = false;

        try {
            exists = appformMapper.existsAbuseImei(imei);
        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return exists;
    }
    /**
     * 제휴대리점 정책정보
     * @param appformReqDto
     * @return List<AppformReqDto>
     */
    @RequestMapping(value = "/appform/getSalePlcyInfo", method = RequestMethod.POST)
    public List<MspSalePlcyMstDto> getSalePlcyInfo(@RequestBody AppformReqDto appformReqDto) {

        List<MspSalePlcyMstDto> salePlcyInfoList = null;

        try {
            salePlcyInfoList = appformMapper.getSalePlcyInfo(appformReqDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return salePlcyInfoList;
    }

    /**
     * (대리점)안면인증 가능 접점ID조회
     * @param cntpntShopId
     * @return
     */
    @RequestMapping(value = "/appform/getCpntId", method = RequestMethod.POST)
    public String getCpntId(@RequestBody String cntpntShopId) {

        String cpntId = "";
        try {
            cpntId = appformMapper.getCpntId(cntpntShopId);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return cpntId;
    }
    
}
