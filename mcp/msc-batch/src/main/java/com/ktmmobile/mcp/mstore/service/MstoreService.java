package com.ktmmobile.mcp.mstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.common.util.HttpClientUtil;
import com.ktmmobile.mcp.mstore.dao.MstoreDao;
import com.ktmmobile.mcp.mstore.dto.*;
import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MstoreService {
    private static final Logger logger = LoggerFactory.getLogger(MstoreService.class);
    public static final String DEVC_TP_CD_PC = "20";
    public static final String DEVC_TP_CD_MOBILE = "30";

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Value("${inf.url}")
    private String infUrl;

    @Value("${ext.url}")
    private String extUrl;

    @Autowired
    private MstoreDao mstoreDao;

    /**
     * 설명 : [AS-IS] MSTORE 연동 대상 리스트 추출 (최대 50건)
     * @Date : 2023.08.04
    */
    public MstoreCanTrgDto[] getMstoreCanTrg() {
        RestTemplate restTemplate = new RestTemplate();
        MstoreCanTrgDto[] rtnResult= restTemplate.postForObject(apiInterfaceServer + "/mstore/selectMstoreCanList", "", MstoreCanTrgDto[].class);
        return rtnResult;
    }

    /**
     * 설명 : [TO-BE] MSTORE 연동 대상 리스트 추출 (최대 50건)
     * @Date : 2023.12.05
     */
    public MstoreCanTrgDto[] getMstoreCanTrgNew() {
        RestTemplate restTemplate = new RestTemplate();
        MstoreCanTrgDto[] rtnResult= restTemplate.postForObject(apiInterfaceServer + "/mstore/selectMstoreCanListNew", "", MstoreCanTrgDto[].class);
        return rtnResult;
    }

    /**
     * 설명 : [TO-BE] MSTORE 연동 대상 리스트 추출- 재시도 (최대 50건)
     * @Date : 2023.12.05
     */
    public MstoreCanTrgDto[] getMstoreCanTrgRty() {
        RestTemplate restTemplate = new RestTemplate();
        MstoreCanTrgDto[] rtnResult= restTemplate.postForObject(apiInterfaceServer + "/mstore/selectMstoreCanListRty", "", MstoreCanTrgDto[].class);
        return rtnResult;
    }

    /**
     * 설명 : MSTORE 활성회원 추출
     * @Date : 2023.08.04
     */
    public List<MstoreApiDto> getMsoreActiveUsers(List<MstoreCanTrgDto> mstoreCanTrgList){

        List<MstoreApiDto> users = new ArrayList<>();
        Map<String, Object> userMap = new HashMap<>();

        // MSTORE 연동 대상 각각 MSTORE 회원 상태 조회
        for(MstoreCanTrgDto mstoreCanTrgDto : mstoreCanTrgList){

            userMap = new HashMap<>(); // 초기화

            // 1) 필수 파라미터 체크 (cid, name)
            if(mstoreCanTrgDto.getSelfCstmrCi() == null || "".equals(mstoreCanTrgDto.getSelfCstmrCi())
               || mstoreCanTrgDto.getUserName() == null || "".equals(mstoreCanTrgDto.getUserName())){

                userMap.put("resultCode", "90");
                userMap.put("message", "PARAMETER ERROR [encSelfCstmrCi : " + mstoreCanTrgDto.getSelfCstmrCi() +"]");
                userMap.put("canTrgSeq", mstoreCanTrgDto.getCanTrgSeq());
                updateNotMstoreCanTrg(userMap);
                continue;
            }

            // 2) MSTORE 회원 조회 연동 (이제너두 가입 여부 확인)
            MstoreDto mstoreDto = new MstoreDto();
            mstoreDto.setEmpen(mstoreCanTrgDto.getSelfCstmrCi());
            mstoreDto.setSearchType("EID");

            String userStatus = getMstoreUserInfo(mstoreDto);

            // 3) 연동 응답값 Map으로 변경
            userMap = changeApiResultToMap(userStatus);
            userMap.put("canTrgSeq",mstoreCanTrgDto.getCanTrgSeq());

            // 4) MSTORE 활성회원이 아닌 경우
            if(!"00".equals(userMap.get("resultCode"))){
                updateNotMstoreCanTrg(userMap);
                continue;
            }else{
                if(!"10".equals(userMap.get("sreTpCd"))){
                    updateNotMstoreCanTrg(userMap);
                    // 연동 성공 건에 대해서 고객정보 삭제(퇴직고객, 탈퇴고객, 정보없음 고객 등..) 활성회원은 제외
                    this.deleteMstoreSsoInfo(mstoreCanTrgDto.getCanTrgSeq(), "canTrgSeq");
                    continue;
                }
            }


            // 5) MSTORE 활성회원인 경우
            MstoreApiDto mstoreApiDto = new MstoreApiDto();
            mstoreApiDto.setEmpen(mstoreCanTrgDto.getSelfCstmrCi());
            mstoreApiDto.setMbrNm(mstoreCanTrgDto.getUserName());
            mstoreApiDto.setProcDivStat("Z");

            users.add(mstoreApiDto);

        } // end of for----------------------------

        return users;
    }


    /**
     * 설명 : MSTORE 회원 조회 연동 (이제너두 가입 여부 확인)
     * @Date : 2023.08.04
     */
    private String getMstoreUserInfo(MstoreDto mstoreDto) {

        String result = null;

        // prx 데이터 세팅
        NameValuePair[] data = {
                new NameValuePair("searchType",mstoreDto.getSearchType()),
                new NameValuePair("empen", mstoreDto.getEmpen())  // 암호화된 CI값
        };

        try{
            // prx 연동
            result = HttpClientUtil.post(extUrl+"/getMstoreUserInfo.do", data, "UTF-8");
        }catch(SocketTimeoutException e) {
            result = null;
            logger.error("=========== getMstoreUserInfo() SocketTimeoutException = {}", e.getMessage());
        }catch (Exception e) {
            result = null;
            logger.error("=========== getMstoreUserInfo() Exception = {}", e.getMessage());
        }

        return result;
    }

    /**
     * 설명 : Mstore 연동 결과 update (MSTORE 활성회원이 아닌 경우)
     * @Date : 2023.08.04
     */
    private void updateNotMstoreCanTrg (Map<String, Object> userStatusMap){
        mstoreDao.updateNotMstoreCanTrg(userStatusMap);
    }

    /**
     * 설명 : MSTORE 탈퇴 연동
     * @Date : 2023.08.04
     */
    public String updateMstoreUserInfo(List<MstoreApiDto> users) throws JsonProcessingException {

        String result= null;  // prx 연동 결과

        // 필수 파라미터 체크 (users)
        if(users == null || users.size() == 0) return null;

        // prx 데이터 세팅
        ObjectMapper objectMapper = new ObjectMapper();
        String userStr = objectMapper.writeValueAsString(users);

        NameValuePair[] data = {
                new NameValuePair("userStr",userStr)
        };

        try{
            // prx 연동
            result = HttpClientUtil.post(extUrl+"/updateMstoreUserInfo.do", data, "UTF-8");
        }catch(SocketTimeoutException e) {
            result = null;
            logger.error("=========== updateMstoreUserInfo() SocketTimeoutException = {}", e.getMessage());
        }catch (Exception e) {
            result = null;
            logger.error("=========== updateMstoreUserInfo() Exception = {}", e.getMessage());
        }

        return result;
    }

    /**
     * 설명 : API 응답 결과 objectMap으로 변환
     * @Date : 2023.08.04
     */
    public Map<String, Object> changeApiResultToMap(String canResult) {

        Map<String,Object> rtnMap = new HashMap<>();

        // 1) 필수 파라미터 체크
        if (canResult == null || "".equals(canResult)){
            rtnMap.put("resultCode","70");
            rtnMap.put("message","PRX ERROR");
            return rtnMap;
        }

        try{
            // 2) objectMap으로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(canResult);
            if(jsonNode.get("resultCode") != null) rtnMap.put("resultCode",jsonNode.get("resultCode").asText()); // API 호출결과 코드
            if(jsonNode.get("message") != null) {
                if((jsonNode.get("message").asText().length())>1000){
                    rtnMap.put("message",jsonNode.get("message").asText().substring(0,1000));// API 호출결과 메시지
                }else{
                    rtnMap.put("message",jsonNode.get("message").asText());
                }
            }
            if(jsonNode.get("result") != null) rtnMap.put("result",jsonNode.get("result").asText());            // 처리결과 내역
            if(jsonNode.get("resultDtl") != null) rtnMap.put("resultDtl",jsonNode.get("resultDtl").asText());  // 처리결과 상세 (포인트지급처리 대상 사번 목록)
            if(jsonNode.get("sreTpCd") != null) rtnMap.put("sreTpCd",jsonNode.get("sreTpCd").asText());        // 회원 상태 코드 (활성회원 : 10 / 퇴직회원 : 20)
        }catch(JsonProcessingException e){
            rtnMap.put("resultCode","71");
            rtnMap.put("message","JSON ERROR");
        }

        return rtnMap;
    }

    /**
     * 설명 : MSTORE 인사정보 탈퇴 연동 응답 로그 insert
     * @Date : 2023.08.04
     */
    public String insertMstoreProcHist(Map<String, Object> objectMap) {
        mstoreDao.insertMstoreProcHist(objectMap);
        return (String) objectMap.get("histSeq");
    }

    /**
     * 설명 : Mstore 연동 결과 update (MSTORE 활성회원인 경우)
     * @Date : 2023.08.04
     */
    public void updateMstoreCanTrg(Map<String, String> seqMap) {
        int updateCnt = mstoreDao.updateMstoreCanTrg(seqMap);
        logger.debug("=========== updateMstoreCanTrg() update count ["+updateCnt+"]");
    }

    /**
     * 설명 : M스토어 탈퇴 연동 성공 건에 대해서 고객정보 삭제
     * @param updSeq
     * @param type
    */
    public void deleteMstoreSsoInfo(String updSeq, String type) {

        Map<String, String> paraMap= new HashMap<>();
        paraMap.put(type,updSeq);
        int deleteCnt = mstoreDao.deleteMstoreSsoInfo(paraMap);
        logger.debug("=========== deleteMstoreSsoInfo() delete count ["+deleteCnt+"]");
    }

    /**
     * 설명 : Mstore 연동 결과 재처리 update (MSTORE 활성회원인 경우)
     * @Date : 2023.12.04
     */
    public void updateMstoreRtyCanTrg(Map<String, String> seqMap) {
        int updateCnt = mstoreDao.updateMstoreRtyCanTrg(seqMap);
        logger.debug("=========== updateMstoreRtyCanTrg() update count ["+updateCnt+"]");
    }

    public void saveTodaySalesList(String userId) {
        this.saveTodaySalesListByDevcTpCd(DEVC_TP_CD_PC, userId);
        this.saveTodaySalesListByDevcTpCd(DEVC_TP_CD_MOBILE, userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveTodaySalesListByDevcTpCd(String devcTpCd, String userId) {
        String responseJson = this.getTodaySalesListJson(devcTpCd);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TodaySalesResponse todaySalesResponse = objectMapper.readValue(responseJson, TodaySalesResponse.class);

            if (!"0".equals(todaySalesResponse.getResultCode())) {
                String failMessage = "[" + todaySalesResponse.getResultCode() + "]" + todaySalesResponse.getFailMessage();
                this.insertMstoreContentNotSuccess(devcTpCd, "FAIL", failMessage, userId);
                return;
            }

            String jsonHash = EncryptUtil.sha256Hash(responseJson);
            long sameContentSeq = this.getSameContentSeq(devcTpCd, jsonHash);
            if (sameContentSeq > 0) {
                this.insertMstoreContentNotSuccess(devcTpCd, "DUPLICATE", "CONTENT_SEQ : " + sameContentSeq, userId);
                return;
            }

            long contentSeq = this.insertMstoreContentSuccess(devcTpCd, jsonHash, userId);

            List<MstoreContentItemDto> itemList = extractContentItemList(todaySalesResponse, contentSeq, userId);
            itemList.forEach(item ->
                    mstoreDao.insertMstoreContentItem(item));

        } catch (JsonProcessingException e) {
            logger.error("=========== saveTodaySalesList() JsonProcessingException = {}", e.getMessage());
            this.insertMstoreContentNotSuccess(devcTpCd, "FAIL", e.getMessage(), userId);
        }
    }

    private String getTodaySalesListJson(String devcTpCd) {
        HttpEntity<String> entity = new HttpEntity<>(devcTpCd);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                extUrl + "/mstore/getTodaySales.do",
                HttpMethod.POST,
                entity,
                String.class
        );
        return response.getBody();
    }

    private long getSameContentSeq(String devcTpCd, String jsonHash) {
        MstoreContentDto contentDto = new MstoreContentDto();
        contentDto.setDevcTpCd(devcTpCd);
        contentDto.setJsonHash(jsonHash);
        Long contentSeq = mstoreDao.getContentSeqByHash(contentDto);
        return (contentSeq != null) ? contentSeq : 0L;
    }

    private void insertMstoreContentNotSuccess(String devcTpCd, String status, String failMessage, String userId) {
        MstoreContentDto contentDto = new MstoreContentDto();
        contentDto.setDevcTpCd(devcTpCd);
        contentDto.setStatus(status);
        contentDto.setFailMessage(failMessage);
        contentDto.setUserId(userId);
        mstoreDao.insertMstoreContent(contentDto);
    }

    private long insertMstoreContentSuccess(String devcTpCd, String jsonHash, String userId) {
        MstoreContentDto contentDto = new MstoreContentDto();
        contentDto.setDevcTpCd(devcTpCd);
        contentDto.setStatus("SUCCESS");
        contentDto.setJsonHash(jsonHash);
        contentDto.setUserId(userId);
        mstoreDao.insertMstoreContent(contentDto);

        return contentDto.getContentSeq();
    }

    private static List<MstoreContentItemDto> extractContentItemList(TodaySalesResponse todaySalesResponse, long contentSeq, String userId) {
        List<MstoreContentItemDto> itemList = new ArrayList<>();

        TodaySalesMessage todaySalesMessage = todaySalesResponse.getMessage();

        List<TodaySalesDto> todaySalesList = todaySalesMessage.getTodaySales();
        todaySalesList.forEach(todaySales ->
                itemList.add(getContentItemFrom(todaySales, contentSeq, userId)));

        List<PBannerDto> pBannerList = todaySalesMessage.getPBannerList();
        pBannerList.forEach(pBanner ->
                itemList.add(getContentItemFrom(pBanner, contentSeq, userId)));

        return itemList;
    }

    private static MstoreContentItemDto getContentItemFrom(TodaySalesDto todaySales, long contentSeq, String userId) {
        MstoreContentItemDto itemDto = new MstoreContentItemDto();
        itemDto.setContentSeq(contentSeq);
        itemDto.setItemType("PRODUCT");
        itemDto.setName(todaySales.getProdNm());
        itemDto.setUrl(todaySales.getProdUrl());
        itemDto.setImgUrlPc(todaySales.getProdImgUrl());
        itemDto.setImgUrlMo(todaySales.getProdImgUrl());
        itemDto.setOriginalPrice(todaySales.getProdCmprc());
        itemDto.setDiscountPrice(todaySales.getProdSeprc());
        itemDto.setUserId(userId);
        return itemDto;
    }

    private static MstoreContentItemDto getContentItemFrom(PBannerDto pBanner, long contentSeq, String userId) {
        MstoreContentItemDto itemDto = new MstoreContentItemDto();
        itemDto.setContentSeq(contentSeq);
        itemDto.setItemType("BANNER");
        itemDto.setName(pBanner.getCntsNm());
        itemDto.setUrl(pBanner.getCntsCnctUrl());
        itemDto.setImgUrlPc(pBanner.getPcImageUrl());
        itemDto.setImgUrlMo(pBanner.getMoImageUrl());
        itemDto.setUserId(userId);
        return itemDto;
    }
}