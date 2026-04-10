package com.ktmmobile.mcp.join.service;

import static com.ktmmobile.mcp.common.constants.Constants.KAKAO_SENDER_KEY;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.db.McpMrktHistDto;
import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.join.dao.JoinDao;
import com.ktmmobile.mcp.join.dto.JoinDto;
import com.ktmmobile.mcp.join.dto.UserAgentDto;
import com.ktmmobile.mcp.mypage.dao.MypageUserDao;
import com.ktmmobile.mcp.mypage.service.MypageService;

@Service
public class JoinSvcImpl implements JoinSvc {

    private static final Logger logger = LoggerFactory.getLogger(JoinSvcImpl.class);

    @Autowired
    JoinDao joinDao;

    @Autowired
    MypageUserDao mypageUserDao;

    @Autowired
    MypageService mypageService;

    @Autowired
    FCommonSvc fCommonSvc;

    @Autowired
    SmsSvc smsSvc;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /**
     * id 중복 체크
     */
    @Override
    public int idCheck(String checkId) {
        return joinDao.idCheck(checkId);
    }

    /**
     * 회원가입 실행
     */
    @Override
    public void insertMemberInfo(JoinDto joinDto) {
        String pw = joinDto.getPassword();
        pw = EncryptUtil.sha512HexEnc(pw);
        joinDto.setPassword(pw);

        joinDao.insertMemberInfo(joinDto);

        /*if (0 < result) {
            //마케팅 동의 변경 내역 저장
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.postForObject(apiInterfaceServer + "/mypage/callMcpMrktAgrNew", joinDto, Integer.class); // mypageUserDao.callMcpMrktAgrNew
            }  catch (RestClientException e) {

                logger.error("callMcpMrktAgrNew Exception = {}", e.getMessage());
            }    catch(Exception e) {
                logger.error("callMcpMrktAgrNew Exception = {}", e.getMessage());
            }
        }*/
    }

    /**
     * 중복가입 확인
     */
    @Override
    public int dupleChk(String pin) {
        return joinDao.dupleChk(pin);
    }

    @Override
    public boolean insetUserAgent(UserAgentDto userAgent) {
        if( null!=userAgent.getMinorAgentRrn() && !userAgent.getMinorAgentRrn().trim().equals("") ){
            userAgent.setMinorAgentRrn(EncryptUtil.ace256Enc(userAgent.getMinorAgentRrn()));//생년월일
        }
        return joinDao.insetUserAgent(userAgent);
    }

    @Override
    public void insertSnsInfo(JoinDto joinDto) {
        try {
            joinDto.setSnsId(EncryptUtil.ace256Dec(joinDto.getSnsId()));
            joinDao.insertSnsInfo(joinDto);
        } catch (CryptoException e) {
            logger.debug(e.getMessage());
        }
    }

    @Override
    public JoinDto getPinToUserInfo(String pin) {
        try {
            String param = EncryptUtil.ace256Dec(pin);
            return joinDao.getPinToUserInfo(param);
        } catch (CryptoException e) {
            logger.debug(e.getMessage());
        }
        return null;
    }

    @Override
    public JoinDto getUserToPinInfo(String userId) {
        return joinDao.getUserToPinInfo(userId);
    }

    @Override
    public int dormancyDupleChk(String pin) {
        return joinDao.dormancyDupleChk(pin);
    }

    @Override
    public int updateClausePriAdFlag(JoinDto joinDto) {
        return joinDao.updateClausePriAdFlag(joinDto);

    }

    @Override
    public void insertUpdateMrktHist(JoinDto joinDto) {
//		mrktHist 조회
//		03 04 05 있는지
// 		있으면 기존값이랑 비교하여 바뀌면 현재 시간으로 변경 아니면 기존값 그대로
//		있으면 업데이트 없으면 새로 생성
//		update시 end_DTTM도 변경시간대로
//		insert시 99991231235959로

        //as-is : 데이터 존재 체크 후 없으면 insert, 있으면 update
        //to-be : 기존 데이터 먼저 업데이트 하고 insert
        List<McpMrktHistDto> mrktHistList = this.mypageService.selectExistingConsent(joinDto.getUserId());
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowTimeStr = simpleDateFormat.format(now);

        Map<String, String> gubunMap = Map.of(
            "03", joinDto.getPersonalInfoCollectAgree(),
            "04", joinDto.getClausePriAdFlag(),
            "05", joinDto.getOthersTrnsAgree()
        );

        boolean isSendKakaoNoti = false;

        if (mrktHistList != null && !mrktHistList.isEmpty()) {
            for (McpMrktHistDto mrktHist : mrktHistList) {
                String newAgrYn = gubunMap.get(mrktHist.getGubun());
                if ("04".equals(mrktHist.getGubun())) {
                    if (newAgrYn != null && !newAgrYn.equals(mrktHist.getAgrYn())) {
                        isSendKakaoNoti = true;
                    }
                }

                if (newAgrYn != null) {
                    mrktHist.setNewAgrYn(newAgrYn);
                    mrktHist.setNewStrtDttm(nowTimeStr);
                    mrktHist.setNewEndDttm(nowTimeStr);
                    mrktHist.setMtkAgrReferer(joinDto.getMtkAgrReferer());
                    this.joinDao.updateMrktHist(mrktHist);
                }
            }
        }else {
            if (joinDto.getClausePriAdFlag() != null) {
                isSendKakaoNoti = true;
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> joinMap = objectMapper.convertValue(joinDto, Map.class);
        String endDttmTime = "99991231235959";

        Map<String, String> gubunMapping = Map.of(
            "personalInfoCollectAgree", "03",
            "clausePriAdFlag", "04",
            "othersTrnsAgree", "05"
        );

        joinMap.entrySet().stream()
                .filter(entry -> gubunMapping.containsKey(entry.getKey()))
                .forEach(entry -> {
                    String gubun = gubunMapping.get(entry.getKey());
                    if (gubun != null) {
                        McpMrktHistDto mcpMrktHistDto = new McpMrktHistDto();
                        mcpMrktHistDto.setUserid(joinDto.getUserId());
                        mcpMrktHistDto.setAgrYn(entry.getValue());
                        mcpMrktHistDto.setStrtDttm(nowTimeStr);
                        mcpMrktHistDto.setEndDttm(endDttmTime);
                        mcpMrktHistDto.setGubun(gubun);
                        mcpMrktHistDto.setMtkAgrReferer(joinDto.getMtkAgrReferer());

                        this.joinDao.insertMrktHist(mcpMrktHistDto);
                    }
                });

        /*
         * 기존 callMcpMrktAgr,callMcpMrktAgrNew 프로시저 호출 -> 자바 로직 처리
         * 04 clausePriAdFlag 값이 바뀔 경우, 신규 회원가입 시 카카오톡 발송
         */
        if(isSendKakaoNoti) {
            MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.MKRT_AGR_TEMPLATE_ID);

            if (mspSmsTemplateMstDto != null) {

                String clausePriAdFlagSatus = "Y".equals(joinDto.getClausePriAdFlag()) ? "수신" : "거부";
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                String agrDt = simpleDateFormat2.format(new Date());

                String smsMsg = mspSmsTemplateMstDto.getText();
                smsMsg = smsMsg.replace("#{clausePriAdFlagYn}", clausePriAdFlagSatus).replace("#{agrDt}", agrDt);

                mspSmsTemplateMstDto.setText(smsMsg);

                smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), joinDto.getMobileNo(), mspSmsTemplateMstDto.getText(),
                        mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                        KAKAO_SENDER_KEY, String.valueOf(Constants.MKRT_AGR_TEMPLATE_ID));
            }
        }

    }

}

