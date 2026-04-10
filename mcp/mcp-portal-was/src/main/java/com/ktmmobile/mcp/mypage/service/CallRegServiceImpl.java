package com.ktmmobile.mcp.mypage.service;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpSocVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpTelTotalUseTimeDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpTelVO;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class CallRegServiceImpl implements CallRegService{

    private static Logger logger = LoggerFactory.getLogger(CallRegService.class);

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private MypageService mypageService;

   /**
    * 12. 총 통화 시간 조회
    * @author bsj
    * @Date : 2021.12.30
    * @param ncn
    * @param ctn
    * @param custId
    * @param useMonth
    * @return
    */

    @Override
    public MpTelTotalUseTimeDto telTotalUseTimeDto(String ncn, String ctn, String custId, String useMonth) {
        MpTelTotalUseTimeDto vo = new MpTelTotalUseTimeDto();

        boolean useTimeSvcLimit = false; //사용량 조회 횟수 제한 여부
        HashMap<String, String> hm= new HashMap<String, String>();

        try {
              //사용량 조회 2시간동안 조회 횟수 카운트
            hm.put("contractNum", ncn);
            hm.put("eventCd", "X12");
            int limitCnt = mypageService.selectCallSvcLimitCount(hm);

            if (Constants.CALL_USETIME_SVC_LIMIT > limitCnt) {

                vo = mPlatFormService.telTotalUseTimeDto(ncn,ctn, custId, useMonth);
                if(!vo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                    throw new McpCommonException(COMMON_EXCEPTION);
                }

                //사용량 조회 횟수 카운트를 위한 로그 저장
                mypageService.insertMcpSelfcareStatistic(hm);

            } else {
                logger.debug("사용량 조회 2시간 20회 제한");
                useTimeSvcLimit = true;
            }

            List<MpTelVO> itemTelList = vo.getItemTelList();
            List<MpTelVO> itemTelVOListVoice = new ArrayList<MpTelVO>();
            List<MpTelVO> itemTelVOListEtc = new ArrayList<MpTelVO>();
            List<MpTelVO> itemTelVOListData = new ArrayList<MpTelVO>();
            List<MpTelVO> itemTelVOListSms = new ArrayList<MpTelVO>();
            List<MpTelVO> itemTelVOListRoaming = new ArrayList<MpTelVO>();

            if(itemTelList!=null && itemTelList.size()>0){
                for(int i=0;i<itemTelList.size();i++){
                    MpTelVO itemTelVO;
                    itemTelVO = itemTelList.get(i);

                    if(itemTelList.get(i).getStrSvcName().indexOf("국제") != -1) {
                        itemTelVOListRoaming.add(itemTelVO);
                    }else if(itemTelList.get(i).getStrSvcName().indexOf("데이터") != -1){
                        itemTelVOListData.add(itemTelVO);
                    }else if(itemTelList.get(i).getStrSvcName().indexOf("SMS")!=-1 || itemTelList.get(i).getStrSvcName().indexOf("MMS")!=-1 || itemTelList.get(i).getStrSvcName().indexOf("문자")!=-1 ){
                        itemTelVOListSms.add(itemTelVO);
                    }else if(itemTelList.get(i).getStrSvcName().indexOf("음성") != -1 || itemTelList.get(i).getStrSvcName().indexOf("영상") != -1 || itemTelList.get(i).getStrSvcName().indexOf("통화") != -1){
                        itemTelVOListVoice.add(itemTelVO);
                    }else{
                        itemTelVOListEtc.add(itemTelVO);
                    }
                }
            }

//		    if (false) {
                // 데이터
                List<MpTelVO> dataList = new ArrayList<MpTelVO>();
                if(itemTelVOListData !=null && !itemTelVOListData.isEmpty()) {

                    String remainBunGunNmValue = ""; // 남은데이터 단위
                    String minUsebunGunNmValue = ""; // 사용한 데이터 단위
                    String strFreeMinRemain = "";  //잔여
                    String strFreeMinCur = "";	// 당월무료통화
                    String strFreeMinUse = "";

                    // 그래프
                    double strFreeMinRemainInt = 0;
                    double doublePercent = 0; // 그래프 %
                    double strFreeMinTotalInt = 0;
                    String percent ="";

                    String bunGunNm = "";
                    String freeMinRemainValue = ""; // 화면
                    String freeMinUseValue = ""; // 화면
                    String strSvcName = ""; // 화면

                    for(MpTelVO dto : itemTelVOListData) {
                        doublePercent = 0; // 그래프 %
                        strFreeMinRemain = StringUtil.NVL(dto.getStrFreeMinRemain(),"");
                        strFreeMinCur = StringUtil.NVL(dto.getStrFreeMinCur(),"");  //당월 무료
                        strFreeMinUse = StringUtil.NVL(dto.getStrFreeMinUse(),"");

                        bunGunNm = StringUtil.NVL(dto.getBunGunNm(),""); // 단위
                        strFreeMinRemainInt = dto.getStrFreeMinRemainInt();
                        strFreeMinTotalInt = dto.getStrFreeMinTotalInt();

                        if(strFreeMinTotalInt > 0) {
                            if("999999999".equals(strFreeMinCur)) {
                                doublePercent = 100;
                            }else{
                                doublePercent = (strFreeMinRemainInt/strFreeMinTotalInt)*100;
                            }
                        }
                        percent = String.format("%.0f", doublePercent);
                        strSvcName = dto.getStrSvcName();

                        if("알".contentEquals(bunGunNm)) {
                            remainBunGunNmValue = "알";
                            minUsebunGunNmValue = "알";
                        } else {


                            if("999999999".equals(strFreeMinCur)) {  // 기본 제공 표현을 위해 항목 변경을 해야 함. 당월 무료 케이스
                                //당월 무료일 경우 이나 QOS 경우는 항상 999로 내려 오기 때문에 별도 분기점을 넣어야 한다.
                                remainBunGunNmValue = "";
                                freeMinRemainValue ="기본제공";


                            } else {
                                double intStrFreeMinRemain = Double.parseDouble(strFreeMinRemain); // 잔여
                                if(intStrFreeMinRemain >=1000){
                                    DecimalFormat decFormat = new DecimalFormat("#,###.##");
                                    double doubleFreeMinRemainValue = 0;
                                    remainBunGunNmValue = "GB";
                                    doubleFreeMinRemainValue = Math.round((intStrFreeMinRemain/1024)*100)/100.0;
                                    freeMinRemainValue = decFormat.format(doubleFreeMinRemainValue)+remainBunGunNmValue;
                                } else {
                                    remainBunGunNmValue = "MB";
                                    freeMinRemainValue = intStrFreeMinRemain+remainBunGunNmValue;
                                }
                            }

                            if("999999999".equals(strFreeMinUse)) { // 사용한 데이터가 99999999? 이상함..
                                minUsebunGunNmValue = "";
                                freeMinUseValue = "기본제공";
                            } else {
                                double intStrFreeMinUse = Double.parseDouble(strFreeMinUse);
                                if(intStrFreeMinUse >=1000){
                                    DecimalFormat decFormat = new DecimalFormat("#,###.##");
                                    double doublefreeMinUseValue = 0;
                                    minUsebunGunNmValue = "GB";
                                    doublefreeMinUseValue = Math.round((intStrFreeMinUse/1024)*100)/100.0;
                                    freeMinUseValue = decFormat.format(doublefreeMinUseValue)+minUsebunGunNmValue;
                                } else {
                                    minUsebunGunNmValue = "MB";
                                    freeMinUseValue = intStrFreeMinUse+minUsebunGunNmValue;
                                }
                            }

                        }

                        MpTelVO data = new MpTelVO();
                        data.setStrFreeMinRemain(freeMinRemainValue);
                        data.setStrFreeMinUse(freeMinUseValue);
                        data.setStrSvcName(strSvcName);
                        data.setPercent(percent);
                        dataList.add(data);
                    }
                }

                // 보이스
                List<MpTelVO> voiceList = new ArrayList<MpTelVO>();
                if(itemTelVOListVoice !=null && !itemTelVOListVoice.isEmpty()) {
                    String strFreeMinRemain = "";
                    String strFreeMinUse = "";

                    // 그래프
                    double strFreeMinRemainInt = 0;
                    double doublePercent = 0; // 그래프 %
                    double strFreeMinTotalInt = 0;
                    String percent ="";

                    String bunGunNm = "";
                    String freeMinRemainValue = ""; // 화면
                    String freeMinUseValue = ""; // 화면
                    String strSvcName = ""; // 화면

                    for(MpTelVO dto : itemTelVOListVoice) {
                        doublePercent = 0; // 그래프 %
                        strFreeMinRemain = StringUtil.NVL(dto.getStrFreeMinRemain(),"");
                        strFreeMinUse = StringUtil.NVL(dto.getStrFreeMinUse(),"");

                        bunGunNm = dto.getBunGunNm();
                        strFreeMinRemainInt = dto.getStrFreeMinRemainInt();
                        strFreeMinTotalInt = dto.getStrFreeMinTotalInt();

                        if(strFreeMinTotalInt > 0) {
                            doublePercent = (strFreeMinRemainInt/strFreeMinTotalInt)*100;
                        }
                        percent = String.format("%.0f", doublePercent);
                        strSvcName = dto.getStrSvcName();

                        if("999999999".equals(strFreeMinRemain) || strFreeMinTotalInt==999999999) {
                            freeMinRemainValue = "기본제공";
                        } else {
                            freeMinRemainValue = strFreeMinRemain+bunGunNm;
                        }

                        if("999999999".equals(strFreeMinUse)) {
                            freeMinUseValue = "기본제공";
                        } else {
                            freeMinUseValue = strFreeMinUse+bunGunNm;
                        }

                        MpTelVO voice = new MpTelVO();
                        voice.setStrFreeMinRemain(freeMinRemainValue);
                        voice.setStrFreeMinUse(freeMinUseValue);
                        voice.setStrSvcName(strSvcName);
                        voice.setPercent(percent);
                        voiceList.add(voice);
                    }
                }

                // sms
                List<MpTelVO> smsList = new ArrayList<MpTelVO>();
                if(itemTelVOListSms !=null && !itemTelVOListSms.isEmpty()) {
                    String strFreeMinRemain = "";
                    String strFreeMinUse = "";

                    // 그래프
                    double strFreeMinRemainInt = 0;
                    double doublePercent = 0; // 그래프 %
                    double strFreeMinTotalInt = 0;
                    String percent ="";

                    String bunGunNm = "";
                    String freeMinRemainValue = ""; // 화면
                    String freeMinUseValue = ""; // 화면
                    String strSvcName = ""; // 화면

                    for(MpTelVO dto : itemTelVOListSms) {
                        doublePercent = 0; // 그래프 %
                        strFreeMinRemain = StringUtil.NVL(dto.getStrFreeMinRemain(),"");
                        strFreeMinUse = StringUtil.NVL(dto.getStrFreeMinUse(),"");

                        bunGunNm = dto.getBunGunNm();
                        strFreeMinRemainInt = dto.getStrFreeMinRemainInt();
                        strFreeMinTotalInt = dto.getStrFreeMinTotalInt();
                        if(strFreeMinTotalInt > 0) {
                            doublePercent = (strFreeMinRemainInt/strFreeMinTotalInt)*100;
                        }
                        percent = String.format("%.0f", doublePercent);
                        strSvcName = dto.getStrSvcName();

                        if("999999999".equals(strFreeMinRemain) || strFreeMinTotalInt==999999999) {
                            freeMinRemainValue = "기본제공";
                        } else {
                            freeMinRemainValue = strFreeMinRemain+bunGunNm;
                        }

                        if("999999999".equals(strFreeMinUse)) {
                            freeMinUseValue = "기본제공";
                        } else {
                            freeMinUseValue = strFreeMinUse+bunGunNm;
                        }

                        MpTelVO sms = new MpTelVO();
                        sms.setStrFreeMinRemain(freeMinRemainValue);
                        sms.setStrFreeMinUse(freeMinUseValue);
                        sms.setStrSvcName(strSvcName);
                        sms.setPercent(percent);
                        smsList.add(sms);
                    }
                }

                //roaming
                List<MpTelVO> roamingList = new ArrayList<MpTelVO>();
                if(itemTelVOListRoaming !=null && !itemTelVOListRoaming.isEmpty()) {
                    String strCtnSecs = "";
                    String strSecsToRate = "";
                    String bunGunNm = "";
                    String ctnSecsValue = "";
                    String secsToRateValue = "";
                    String strSvcName = "";

                    for(MpTelVO dto : itemTelVOListRoaming) {
                        strCtnSecs = StringUtil.NVL(dto.getStrCtnSecs(),"");
                        strSecsToRate = StringUtil.NVL(dto.getStrSecsToRate(),"");

                        bunGunNm = dto.getBunGunNm();
                        strSvcName = dto.getStrSvcName();
                        ctnSecsValue = strCtnSecs+bunGunNm;
                        secsToRateValue = strSecsToRate+bunGunNm;

                        MpTelVO roaming = new MpTelVO();
                        roaming.setStrCtnSecs(ctnSecsValue);
                        roaming.setStrSecsToRate(secsToRateValue);
                        roaming.setStrSvcName(strSvcName);
                        roamingList.add(roaming);
                    }
                }

                vo.setItemTelVOListData(dataList);
                vo.setItemTelVOListVoice(voiceList);
                vo.setItemTelVOListSms(smsList);
                vo.setItemTelVOListEtc(itemTelVOListEtc);
                vo.setItemTelVOListRoaming(roamingList);
//		    } else {
//		    	vo.setItemTelVOListData(itemTelVOListData);
//			    vo.setItemTelVOListVoice(itemTelVOListVoice);
//			    vo.setItemTelVOListSms(itemTelVOListSms);
//			    vo.setItemTelVOListEtc(itemTelVOListEtc);
//		    }

            vo.setUseTimeSvcLimit(useTimeSvcLimit);
        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        } catch (Exception e) {
            logger.debug("이용량조회 : x12 error ");
        }
        return vo;
    }



    @Override
    public Map<String, Object> isOverUsageChargeAjax(String ncn, String contractNum,  String ctn, String custId) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            // 4-1. 이용량 조회 (당월)
            MpTelTotalUseTimeDto vo = new MpTelTotalUseTimeDto();
            vo = mPlatFormService.telTotalUseTimeDto(ncn, ctn, custId, null);
            List<MpTelVO> itemsList = Optional.ofNullable(vo).map(MpTelTotalUseTimeDto::getItemTelList).orElse(Collections.emptyList());

            boolean isOverUsageCharge = false ;
            boolean isOverUsageVoiCharge = false ;
            boolean isOverUsageDatCharge = false ;
            boolean isOverUsageSmsCharge = false ;

            //현재 요금제 조회
            McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(contractNum);
            if(mcpUserCntrMngDto ==null || StringUtil.isEmpty(mcpUserCntrMngDto.getSoc())){
                throw new McpCommonJsonException("0003", "요금제 정보를 확인 할수 없습니다. ");
            }
            Map<String, String> rateMstMap = mypageService.selectRateMst(mcpUserCntrMngDto.getSoc());

            /**
             * 음성 초과 여부 확인
             */
            String valCallCnt = Optional.ofNullable(rateMstMap)
                    .map(m -> m.get("CALL_CNT"))
                    .map(String::trim)
                    .orElse("");
            logger.debug("valCallCnt={}", valCallCnt);

            /**
             * 음성이 기본제공       경우 영상/부가 항목의 잔여량으로 초과요금 발생 대상을 구분해주시고
             * 음성이 기본제공 아닌  경우 음성/영상 항목의 잔여량으로 초과요금 발생 대상을 구분해주시고,
             */
            String targetSvcName = "기본제공".equals(valCallCnt) ? "영상/부가" : "음성/영상";
            MpTelVO sumVideoVo = itemsList.stream()
                    .filter(item -> targetSvcName.equals(item.getStrSvcName()))
                    .findFirst()
                    .orElse(null);

            if (sumVideoVo != null && sumVideoVo.getStrFreeMinRemainInt() < 1) {
                isOverUsageCharge = true;
                isOverUsageVoiCharge = true;
            }


            /**
             * DATA 확인 초과 여부 확인
             */
            MpTelVO sumDataVo = itemsList.stream()
                    .filter(item -> "데이터-합계".equals(item.getStrSvcName()))
                    .findFirst()
                    .orElse(null);

            /**
             * 잔여량이 0 이면 과금 ...
             */
            if (sumDataVo != null && sumDataVo.getStrFreeMinRemainInt() < 1 ) {
                /**
                 * QoS 제공 여부 확인
                 */
                String qosDataCntDesc = Optional.ofNullable(rateMstMap)
                        .map(m -> m.get("QOS_DATA_CNT_DESC"))
                        .orElse(null);

                /** 정보가 없다
                 * QoS 아닌다...
                 * */
                int qosDataCntInt = NumberUtils.toInt(qosDataCntDesc, 0);
                logger.debug("qosDataCntInt={}", qosDataCntInt);

                if (qosDataCntInt < 1) {
                    isOverUsageCharge = true ;
                    isOverUsageDatCharge = true;
                }
            }

            /**
             * 문자 확인 초과 여부 확인
             */
            MpTelVO sumSmsVo = itemsList.stream()
                    .filter(item -> "SMS/MMS".equals(item.getStrSvcName()))
                    .findFirst()
                    .orElse(null);

            if (sumSmsVo != null && sumSmsVo.getStrFreeMinRemain().equals("0") ) {
                isOverUsageCharge = true ;
                isOverUsageSmsCharge = true ;
            }
            rtnMap.put("isOverUsageCharge",isOverUsageCharge);
            rtnMap.put("isOverUsageVoiCharge",isOverUsageVoiCharge);
            rtnMap.put("isOverUsageDatCharge",isOverUsageDatCharge);
            rtnMap.put("isOverUsageSmsCharge",isOverUsageSmsCharge);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_MSG", "정상응답");

        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
            rtnMap.put("RESULT_CODE", "0005");
            rtnMap.put("RESULT_MSG", e.getMessage());
        } catch (Exception e) {
            logger.error("Exception e : {}", e.getMessage());
            rtnMap.put("RESULT_CODE", "0006");
            rtnMap.put("RESULT_MSG", e.getMessage());
        }
        return rtnMap;
    }

    /**
     * 당겨쓰기 조회
     * @author bsj
     * @Date : 2021.12.30
     * @param mcpUserCntrMngDto
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     * @throws SocketTimeoutException
     * @throws SelfServiceException
     */
    @Override
    public HashMap<String, Object> totalUseTimeList(McpUserCntrMngDto mcpUserCntrMngDto, String ncn, String ctn,
            String custId) throws SocketTimeoutException, SelfServiceException {
        boolean pass = true;
        HashMap<String, Object>  resultMap = new HashMap<String, Object>();

        String returnUrl = "/main.do";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/m/main.do";
        }

        if( !"ASFRKIS32".equalsIgnoreCase(mcpUserCntrMngDto.getSoc())   //M 데이터선택 300MB
                    && !"ASFRKIS40".equalsIgnoreCase(mcpUserCntrMngDto.getSoc())    //M 데이터선택 1GB
                    && !"KTMVUL425".equalsIgnoreCase(mcpUserCntrMngDto.getSoc())    //M 데이터선택 USIM 1GB
                    && !"LTEKISU23".equalsIgnoreCase(mcpUserCntrMngDto.getSoc())    //M 데이터선택 USIM 300MB
                    && !"KTMVUUS25".equalsIgnoreCase(mcpUserCntrMngDto.getSoc())    //M 데이터선택 USIM 1GB
                    && !"KTMVUUS29".equalsIgnoreCase(mcpUserCntrMngDto.getSoc())    //M 데이터선택 USIM 2GB
                    && !"KTMVUUS34".equalsIgnoreCase(mcpUserCntrMngDto.getSoc())    //M 데이터선택 USIM 3GB
                    && !"KISKUS379".equalsIgnoreCase(mcpUserCntrMngDto.getSoc())    //M 데이터선택 USIM 6GB
                        ) {
                    pass = false;
                    resultMap.put("checkPass", false);
         }
          MpTelVO tmpItemTelVO = null;
          String useStr = "0";
          String svcNm = "";
          boolean use100 = false;
          boolean use500 = false;
          boolean use1G = false;

          boolean useTimeSvcLimit = false; //사용량 조회 횟수 제한 여부
          HashMap<String, String> hm= new HashMap<String, String>();
          hm.put("contractNum", ncn);
          hm.put("eventCd", "X12");
          int limitCnt = mypageService.selectCallSvcLimitCount(hm);

          if(pass) {
                    resultMap.put("checkPass", true);

                    if (Constants.CALL_USETIME_SVC_LIMIT > limitCnt) {
                          useTimeSvcLimit = false;
                   } else {
                          useTimeSvcLimit = true;
                   }

                  //x12
                  MpTelTotalUseTimeDto vo = mPlatFormService.telTotalUseTimeDto(ncn, ctn, custId, "");

                  if(!vo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                         throw new McpCommonException(COMMON_EXCEPTION,returnUrl);
                  }
                  svcNm = vo.getStrSvcNameSms();
                  for(MpTelVO itemTel : vo.getItemTelList()) {

                      if(itemTel.getStrSvcName().equals("데이터") || itemTel.getStrSvcName().indexOf("데이터-합계") > -1) {
                          tmpItemTelVO = itemTel;
                      }
                  }
                  //이용중인 부가서비스 조회
                  MpAddSvcInfoDto getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(ncn, ctn, custId);
                  if(!getAddSvcInfo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                         throw new McpCommonException(COMMON_EXCEPTION,returnUrl);
                  }

                  float useSum = 0;
                  for(MpSocVO item : getAddSvcInfo.getList()) {
                      if("PLLDAT100".equalsIgnoreCase(item.getSoc()) || "PLLDAT500".equalsIgnoreCase(item.getSoc()) || "PLLDAT01G".equalsIgnoreCase(item.getSoc())) {
                          try {
                              String start = DateTimeUtil.changeFormat(item.getEffectiveDate(), "yyyyMMddHHmmss","yyyy.MM.dd (HH:mm)");
                              String end = DateTimeUtil.changeFormat(DateTimeUtil.lastDayOfMonth(DateTimeUtil.changeFormat(item.getEffectiveDate(), "yyyyMMddHHmmss", "yyyyMMdd")), "yyyyMMdd", "yyyy.MM.dd");
                              item.setEffectiveDate(start);
                              item.setEffectiveEndDate(end);
                              if("PLLDAT100".equalsIgnoreCase(item.getSoc())) {
                                  useSum = useSum + 100;
                                  item.setMemo("100MB");
                                  use100 = true;
                              } else if("PLLDAT500".equalsIgnoreCase(item.getSoc())) {
                                  useSum = useSum + 500;
                                  item.setMemo("500MB");
                                  use500 = true;
                              } else if("PLLDAT01G".equalsIgnoreCase(item.getSoc())) {
                                  useSum = useSum + 1000;
                                  item.setMemo("1GB");
                                  use1G = true;
                              }
                          } catch (ParseException e) {
                              throw new McpCommonException(COMMON_EXCEPTION,returnUrl);
                          }
                      }
                  }

                  if(useSum >= 1000) {
                      useStr = String.format("%.1fGB", (useSum / 1000));
                  } else {
                      useStr = String.format("%.1fMB", useSum);
                  }

                  //사용량 조회 횟수 카운트를 위한 로그 저장
                  mypageService.insertMcpSelfcareStatistic(hm);

                  Object tmpItemTelVOReult = tmpItemTelVO;

                  JsonReturnDto result = new JsonReturnDto();
                  result.setResult(tmpItemTelVOReult);

                  String today = DateTimeUtil.getFormatString("yyyy.MM.dd");
                  String month = DateTimeUtil.getFormatString("M월");

                  resultMap.put("itemTelVO",result);
                  resultMap.put("today",today);
                  resultMap.put("month",month);
                  resultMap.put("useStr",useStr);
                  resultMap.put("svcNm",svcNm);
                  resultMap.put("use100",use100);
                  resultMap.put("use500",use500);
                  resultMap.put("use1G",use1G);
                  resultMap.put("use1G",use1G);
                  resultMap.put("useTimeSvcLimit",useTimeSvcLimit);
              }

          return resultMap;
       }

    /**
     * 당겨쓰기 내역 조회
     * @author bsj
     * @Date : 2021.12.30
     * @param mcpUserCntrMngDto
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     * @throws SocketTimeoutException
     * @throws SelfServiceException
     */
    @Override
    public HashMap<String, Object> totalUseTimeHist(McpUserCntrMngDto mcpUserCntrMngDto, String ncn, String ctn,
            String custId) throws SocketTimeoutException, SelfServiceException {

        HashMap<String, Object>  resultMap = new HashMap<String, Object>();
        String returnUrl = "/main.do";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/m/main.do";
        }
        List<MpSocVO> tmpList = new ArrayList<MpSocVO>();
        //이용중인 부가서비스 조회
        MpAddSvcInfoDto getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(ncn, ctn, custId);
        if(!getAddSvcInfo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
               throw new McpCommonException(COMMON_EXCEPTION,returnUrl);
        }

        float useSum = 0;
        for(MpSocVO item : getAddSvcInfo.getList()) {
            if("KSLTEMSTD".equalsIgnoreCase(item.getSoc()) || "PLLDAT100".equalsIgnoreCase(item.getSoc()) || "PLLDAT500".equalsIgnoreCase(item.getSoc()) || "PLLDAT01G".equalsIgnoreCase(item.getSoc())) {
                try {
                    String start = DateTimeUtil.changeFormat(item.getEffectiveDate(), "yyyyMMddHHmmss","yyyy.MM.dd (HH:mm)");
                    String end = DateTimeUtil.changeFormat(DateTimeUtil.lastDayOfMonth(DateTimeUtil.changeFormat(item.getEffectiveDate(), "yyyyMMddHHmmss", "yyyyMMdd")), "yyyyMMdd", "yyyy.MM.dd");
                    item.setEffectiveDate(start);
                    item.setEffectiveEndDate(end);
                    if("PLLDAT100".equalsIgnoreCase(item.getSoc())) {
                        useSum = useSum + 100;
                        item.setMemo("100MB");
                    } else if("PLLDAT500".equalsIgnoreCase(item.getSoc())) {
                        useSum = useSum + 500;
                        item.setMemo("500MB");
                    } else if("PLLDAT01G".equalsIgnoreCase(item.getSoc())) {
                        useSum = useSum + 1000;
                        item.setMemo("1GB");
                    }
                    tmpList.add(item);
                } catch (ParseException e) {
                    throw new McpCommonException(COMMON_EXCEPTION,returnUrl);
                }
            }
        }

        String today = DateTimeUtil.getFormatString("yyyy.MM.dd");
        Object tmpListReult = tmpList;
        JsonReturnDto tmpListReultDto = new JsonReturnDto();
        tmpListReultDto.setResult(tmpListReult);
        resultMap.put("itemVOList",tmpListReultDto);
        resultMap.put("today",today);

        return resultMap;
    }

}
