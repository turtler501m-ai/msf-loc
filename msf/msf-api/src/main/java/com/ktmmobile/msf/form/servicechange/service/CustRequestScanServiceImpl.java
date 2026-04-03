package com.ktmmobile.msf.form.servicechange.service;

import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.ACE_256_DECRYPT_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.SCAN_SERVER_SEND_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.SCAN_XML_SAVE_EXCEPTION;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.msf.form.newchange.dao.AppformDao;
import com.ktmmobile.msf.form.ownerchange.dto.MyNameChgReqDto;
import com.ktmmobile.msf.form.servicechange.dao.CustRequestDao;
import com.ktmmobile.msf.form.servicechange.dto.CustRequestDto;
import com.ktmmobile.msf.form.termination.dto.CancelConsultDto;
import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.system.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.util.EncryptUtil;
import com.ktmmobile.msf.system.common.util.MultipartUtility;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;

@Service
public class CustRequestScanServiceImpl implements CustRequestScanService {

    private static Logger logger = LoggerFactory.getLogger(CustRequestScanServiceImpl.class);

    @Autowired
    AppformDao appformDao ;

    @Autowired
    CustRequestDao custRequestDao;

    @Autowired
    private CustRequestService custRequestService;

    @Value("${scan.app.path}")
    private String scanPath;

    @Value("${scan.url}")
    private String scanUrl;

    @Value("${scan.V25.url}")
    private String scanUrlV25;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Override
    public void prodSendScan(long custReqKey, String cretId, String reqType){

        Map<String,String> requestData = new HashMap<String,String>();

        //1. 서식지에 들어갈 정보를 조회한다
        if("CL".equals(reqType)) { //통화내역열람
            requestData = custRequestDao.getReqCallListData(custReqKey);
        }else if("NC".equals(reqType)) { //명의변경
            requestData = custRequestDao.getReqNmChgData(custReqKey);
        }else if("IS".equals(reqType)) { //안심보험신청
            requestData = custRequestDao.getReqInsrData(custReqKey);
        }else if("CC".equals(reqType)) { //해지상담신청
            requestData = custRequestDao.getCancelConsultData(custReqKey);
        }

        //2. 복호화 처리
        try {
            requestData.put("CSTMR_FOREIGNER_PN",EncryptUtil.ace256Dec(requestData.get("CSTMR_FOREIGNER_PN")) ) ;
            requestData.put("CSTMR_FOREIGNER_RRN",EncryptUtil.ace256Dec(requestData.get("CSTMR_FOREIGNER_RRN")) ) ;
            requestData.put("CSTMR_NATIVE_RRN", EncryptUtil.ace256Dec(requestData.get("CSTMR_NATIVE_RRN")) ) ;
            requestData.put("MINOR_AGENT_RRN", EncryptUtil.ace256Dec(requestData.get("MINOR_AGENT_RRN")) ) ;
            requestData.put("GR_CSTMR_NATIVE_RRN",EncryptUtil.ace256Dec(requestData.get("GR_CSTMR_NATIVE_RRN"))); //양도인 주민번호(명의변경)
            requestData.put("GR_MINOR_AGENT_RRN", EncryptUtil.ace256Dec(requestData.get("GR_MINOR_AGENT_RRN")) ) ; //양도인 법정대리인 주민번호(명의변경)
            requestData.put("REQ_ACCOUNT_NUMBER", EncryptUtil.ace256Dec(requestData.get("REQ_ACCOUNT_NUMBER")) ) ;
            requestData.put("REQ_CARD_NO", EncryptUtil.ace256Dec(requestData.get("REQ_CARD_NO")) ) ;

        } catch (CryptoException e) {
            throw new McpCommonJsonException("0001" ,ACE_256_DECRYPT_EXCEPTION);
        }

        //3. 주민번호 분석 값 설정
        settingSsnData(requestData);

        //4. 각종 설정 값 설정
        settingData(requestData) ;

        //5. XML 파일 생성
        createXml(custReqKey, cretId, requestData);

        //6. XML 파일 전송 및 삭제 처리
        String returnScanId = xmlFileSend(custReqKey);

        //7. 작업종류에 따라 table UPDATE 처리
        if(StringUtil.isNotNull(returnScanId)) {
            //통화내역열람신청
            if("CL".equals(requestData.get("REQ_TYPE"))) {
                CustRequestDto custRequestDto = new CustRequestDto();
                custRequestDto.setScanId(returnScanId);
                custRequestDto.setCustReqSeq(custReqKey);

                custRequestDao.updateCustRequestCallListScanId(custRequestDto);
                custRequestDao.updateEmvScanMstCL(returnScanId);
                custRequestDao.updateEmvScanFileCL(returnScanId);
            //명의변경신청
            } else if ("NC".equals(requestData.get("REQ_TYPE"))) {
                MyNameChgReqDto myNameChgReqDto = new MyNameChgReqDto();
                myNameChgReqDto.setScanId(returnScanId);
                myNameChgReqDto.setCustReqSeq(String.valueOf(custReqKey));

                custRequestDao.updateReqNmChgScanId(myNameChgReqDto);
                custRequestDao.updateEmvScanMstNC(returnScanId);
                custRequestDao.updateEmvScanFileNC(returnScanId);
            //휴대폰안심보험신청
            }else if("IS".equals(requestData.get("REQ_TYPE"))) {
                CustRequestDto custRequestDto = new CustRequestDto();
                custRequestDto.setReqType(requestData.get("REQ_TYPE"));
                custRequestDto.setScanId(returnScanId);
                custRequestDto.setCustReqSeq(custReqKey);
                custRequestDto.setContractNum(requestData.get("CONTRACT_NUM"));
                custRequestDto.setUserId(cretId);

                custRequestDao.updateEmvScanMst(custRequestDto);
                custRequestDao.updateEmvScanFile(custRequestDto);

                //*************************** 합본 처리 start
                String orgScanId = null;
                int maxFileNum = 0;
                // 1. 계약번호로 MSP_JUO_SUB_INFO에서 scan_id 를 찾아온다. //API 호출
                orgScanId = custRequestService.getOrgScanId(requestData.get("CONTRACT_NUM"));

                if(StringUtil.isNotNull(orgScanId)) {
                    // 2. EMV_SCAN_FILE MAX FILE_NUMBER 값을 찾아온다.
                    maxFileNum = custRequestDao.getMaxFileNum(orgScanId);

                    if(maxFileNum > 0) {
                        custRequestDto.setScanIdOrg(orgScanId);
                        custRequestDto.setMaxFileNum(maxFileNum);
                        // 3. EMV_SCAN_FILE에 insert해서 합본처리
                        custRequestDao.insertEmvScanFile(custRequestDto);
                    }
                }
                //*************************** 합본 처리 end
              //해지상담신청
            }else if("CC".equals(requestData.get("REQ_TYPE"))) {
                CancelConsultDto cancelConsultDto = new CancelConsultDto();
                cancelConsultDto.setScanId(returnScanId);
                cancelConsultDto.setCustReqSeq(String.valueOf(custReqKey));

                custRequestDao.updateCancelReqScanId(cancelConsultDto);
                custRequestDao.updateEmvScanMstCC(returnScanId);
                custRequestDao.updateEmvScanFileCC(returnScanId);
            }
        }
    }

    /** 주민번호 분석후 값을 설정 처리
     * @param
     */
    public void settingSsnData(Map<String,String> reqMapData) {
        String custSSN = "";

        if ( reqMapData.containsKey("CSTMR_NATIVE_RRN")
                && !StringUtils.isBlank(reqMapData.get("CSTMR_NATIVE_RRN"))
                && reqMapData.get("CSTMR_NATIVE_RRN").length() > 7 ) {

            custSSN = reqMapData.get("CSTMR_NATIVE_RRN");
            String tempVal = reqMapData.get("CSTMR_NATIVE_RRN").substring(6, 7);
            reqMapData.put("CSTMR_NATIVE_RRN" ,reqMapData.get("CSTMR_NATIVE_RRN").substring(0, 6) );

            if("1".equals(tempVal)) {
                reqMapData.put("CSTMR_NATIVE_RRN_M", "V");
            } else if("2".equals(tempVal)) {
                reqMapData.put("CSTMR_NATIVE_RRN_W", "V");
            } else if("3".equals(tempVal)) {
                reqMapData.put("CSTMR_NATIVE_RRN_M", "V");
            } else if("4".equals(tempVal) ) {
                reqMapData.put("CSTMR_NATIVE_RRN_W", "V");
            }
        }

        //양도인 주민번호(명의변경)
        if ( reqMapData.containsKey("GR_CSTMR_NATIVE_RRN")
                && !StringUtils.isBlank(reqMapData.get("GR_CSTMR_NATIVE_RRN"))
                && reqMapData.get("GR_CSTMR_NATIVE_RRN").length() > 7 ) {

            custSSN = reqMapData.get("GR_CSTMR_NATIVE_RRN");
            String tempVal = reqMapData.get("GR_CSTMR_NATIVE_RRN").substring(6, 7);
            reqMapData.put("GR_CSTMR_NATIVE_RRN" ,reqMapData.get("GR_CSTMR_NATIVE_RRN").substring(0, 6) );

            if("1".equals(tempVal)) {
                reqMapData.put("GR_CSTMR_NATIVE_RRN_M", "V");
            } else if("2".equals(tempVal)) {
                reqMapData.put("GR_CSTMR_NATIVE_RRN_W", "V");
            } else if("3".equals(tempVal)) {
                reqMapData.put("GR_CSTMR_NATIVE_RRN_M", "V");
            } else if("4".equals(tempVal) ) {
                reqMapData.put("GR_CSTMR_NATIVE_RRN_W", "V");
            }
        }

        if ( reqMapData.containsKey("MINOR_AGENT_RRN")
                && !StringUtils.isBlank(reqMapData.get("MINOR_AGENT_RRN"))
                && reqMapData.get("MINOR_AGENT_RRN").length() > 7 ) {

            custSSN = reqMapData.get("MINOR_AGENT_RRN");
            String tempVal = reqMapData.get("MINOR_AGENT_RRN").substring(6, 7);
            reqMapData.put("MINOR_AGENT_RRN" ,reqMapData.get("MINOR_AGENT_RRN").substring(0, 6) );

            if("1".equals(tempVal)) {
                reqMapData.put("MINOR_AGENT_M", "V");
            } else if("2".equals(tempVal)) {
                reqMapData.put("MINOR_AGENT_W", "V");
            } else if("3".equals(tempVal)) {
                reqMapData.put("MINOR_AGENT_M", "V");
            } else if("4".equals(tempVal) ) {
                reqMapData.put("MINOR_AGENT_W", "V");
            }
        }

        if ( reqMapData.containsKey("ENTRUST_RES_RRN")
                && !StringUtils.isBlank(reqMapData.get("ENTRUST_RES_RRN"))
                && reqMapData.get("ENTRUST_RES_RRN").length() > 7 ) {

            String tempVal = reqMapData.get("ENTRUST_RES_RRN").substring(6, 7);
            reqMapData.put("ENTRUST_RES_RRN" ,reqMapData.get("ENTRUST_RES_RRN").substring(0, 6) );

            if("1".equals(tempVal)) {
                reqMapData.put("ENTRUST_RES_M", "V");
            } else if("2".equals(tempVal)) {
                reqMapData.put("ENTRUST_RES_W", "V");
            } else if("3".equals(tempVal)) {
                reqMapData.put("ENTRUST_RES_M", "V");
            } else if("4".equals(tempVal) ) {
                reqMapData.put("ENTRUST_RES_W", "V");
            }
        }

        if ( reqMapData.containsKey("NAME_CHANGE_RRN")
                && !StringUtils.isBlank(reqMapData.get("NAME_CHANGE_RRN"))
                && reqMapData.get("NAME_CHANGE_RRN").length() > 7 ) {

            String tempVal = reqMapData.get("NAME_CHANGE_RRN").substring(6, 7);
            reqMapData.put("NAME_CHANGE_RRN" ,reqMapData.get("NAME_CHANGE_RRN").substring(0, 6) );

            if("1".equals(tempVal)) {
                reqMapData.put("NAME_CHANGE_RRN_M", "V");
            } else if("2".equals(tempVal)) {
                reqMapData.put("NAME_CHANGE_RRN_W", "V");
            } else if("3".equals(tempVal)) {
                reqMapData.put("NAME_CHANGE_RRN_M", "V");
            } else if("4".equals(tempVal) ) {
                reqMapData.put("NAME_CHANGE_RRN_W", "V");
            }
        }

        if ( reqMapData.containsKey("OTHERS_PAYMENT_RRN")
                && !StringUtils.isBlank(reqMapData.get("OTHERS_PAYMENT_RRN"))
                && reqMapData.get("OTHERS_PAYMENT_RRN").length() > 6 ) {
            reqMapData.put("OTHERS_PAYMENT_RRN" ,reqMapData.get("OTHERS_PAYMENT_RRN").substring(0, 6) );
        }

        if ( reqMapData.containsKey("CSTMR_FOREIGNER_RRN")
                && !StringUtils.isBlank(reqMapData.get("CSTMR_FOREIGNER_RRN"))
                && reqMapData.get("CSTMR_FOREIGNER_RRN").length() > 7 ) {
            custSSN = reqMapData.get("CSTMR_FOREIGNER_RRN");
            String tempVal = reqMapData.get("CSTMR_FOREIGNER_RRN").substring(6, 7);
            reqMapData.put("CSTMR_FOREIGNER_RRN" ,reqMapData.get("CSTMR_FOREIGNER_RRN").substring(0, 6) );

            if("5".equals(tempVal)) {
                reqMapData.put("CSTMR_NATIVE_RRN_M", "V");
            } else if("6".equals(tempVal)) {
                reqMapData.put("CSTMR_NATIVE_RRN_W", "V");
            } else if("7".equals(tempVal)) {
                reqMapData.put("CSTMR_NATIVE_RRN_M", "V");
            } else if("8".equals(tempVal)) {
                reqMapData.put("CSTMR_NATIVE_RRN_W", "V");
            }
        }

        //주민등록번호로 신청인 생년월일 만들기
        custSSN = StringUtil.NVL(custSSN,"");
        if(!"".equals(custSSN)){
             int birYear = Integer.parseInt(custSSN.substring(0, 2)); // 생일 년

             if (Integer.parseInt(custSSN.substring(6, 7)) == 1 || Integer.parseInt(custSSN.substring(6, 7)) == 2 || Integer.parseInt(custSSN.substring(6, 7)) == 5 || Integer.parseInt(custSSN.substring(6, 7)) == 6) {
                 birYear = 1900 + birYear;
             } else if (Integer.parseInt(custSSN.substring(6, 7)) == 3 || Integer.parseInt(custSSN.substring(6, 7)) == 4 || Integer.parseInt(custSSN.substring(6, 7)) == 7 || Integer.parseInt(custSSN.substring(6, 7)) == 8) {
                 birYear = 2000 + birYear;
             }else{
                 birYear = 1900 + birYear;
             }

             reqMapData.put("CUST_BIRTH_YEAR", String.valueOf(birYear));
             reqMapData.put("CUST_BIRTH_MONTH", custSSN.substring(2, 4));
             reqMapData.put("CUST_BIRTH_DAY", custSSN.substring(4, 6));
        }
    }


    /** 주민번호 분석후 값을 설정 처리
     * @param
     */
    public void settingData(Map<String,String> reqMapData) {

        //납부방법
        if ( reqMapData.containsKey("REQ_PAY_TYPE")
                && !StringUtils.isBlank(reqMapData.get("REQ_PAY_TYPE"))  ) {
            String tempVal = reqMapData.get("REQ_PAY_TYPE");

            if ("D".equals(tempVal) || "AA".equals(tempVal) ) {//자동이체 = 계좌번호
                reqMapData.remove("REQ_CARD_COMPANY");
                reqMapData.remove("REQ_CARD_NO");
                reqMapData.remove("REQ_CARD_YY");
                reqMapData.remove("REQ_CARD_MM");
            }else if("C".equals(tempVal)) {// 신용카드 = 카드번호
                reqMapData.remove("REQ_BANK");
                reqMapData.remove("REQ_ACCOUNT_NUMBER");
            }
        }

        //구매유형
        if ( reqMapData.containsKey("REQ_BUY_TYPE")
                && !StringUtils.isBlank(reqMapData.get("REQ_BUY_TYPE"))  ) {
            String tempVal = reqMapData.get("REQ_BUY_TYPE");

            if ("UU".equals(tempVal)) {//유심 구매면 핸드폰 대금 체크 안함
                reqMapData.remove("MODEL_MONTHLY_TYPE");
                reqMapData.remove("REQ_MODEL_NAME");
                reqMapData.remove("REQ_MODEL_COLOR");
            }else {
                String modelName = StringUtil.NVL(reqMapData.get("REQ_MODEL_NAME"),"");
                String modelColor = StringUtil.NVL(reqMapData.get("REQ_MODEL_COLOR"),"");
                if(!modelName.equals("")) {
                    reqMapData.remove("REQ_MODEL_NAME");
                    reqMapData.remove("REQ_MODEL_COLOR");
                    String tempName = modelName + "( " + modelColor + " )";
                    reqMapData.put("REQ_MODEL_NAME", tempName);
                }
            }
        }

        //고객구분
        if ( reqMapData.containsKey("CSTMR_TYPE_ORG")
                && !StringUtils.isBlank(reqMapData.get("CSTMR_TYPE_ORG")) ) {
            String tempVal = reqMapData.get("CSTMR_TYPE_ORG");

            if(!"NM".equals(tempVal)){
                reqMapData.remove("NW_BLCK_AGRM_YN");
                reqMapData.remove("APP_BLCK_AGRM_YN");
                reqMapData.remove("APP_CD");
                reqMapData.remove("APP_CD_NAME");
            }
            if(!"FN".equals(tempVal)){
                //외국인이 아니면 등록번호 여권번호 국적을 제외한다
                reqMapData.remove("CSTMR_FOREIGNER_RRN");
                reqMapData.remove("CSTMR_FOREIGNER_NATION");
                reqMapData.remove("CSTMR_FOREIGNER_PN");
            }
        }

        //APP구분코드
        //청소년 유해차단관련 올레 자녀 안심폰서비스 , 스마트보안관 선택시 기타는 표시 안한다
        if ( reqMapData.containsKey("APP_CD")
                && !StringUtils.isBlank(reqMapData.get("APP_CD")) ) {
            String tempVal = reqMapData.get("APP_CD");

            if("1".equals(tempVal) || "3".equals(tempVal)){
                reqMapData.remove("APP_CD_NAME");
            }
        }
        // 혜택 제공을 위한 제3자 제공 동의(엠모바일), 혜택 제공을 위한 제3자 제공 동의(KT)
        // 둘 다 동의라면 전체 동의만 체크
        reqMapData.put("OTHERS_TRNS_ALL_AGREE", "N");
        if (reqMapData.containsKey("OTHERS_TRNS_AGREE") && !StringUtils.isBlank(reqMapData.get("OTHERS_TRNS_AGREE"))
                && reqMapData.containsKey("OTHERS_TRNS_KT_AGREE") && !StringUtils.isBlank(reqMapData.get("OTHERS_TRNS_KT_AGREE"))) {
            if ("Y".equals(reqMapData.get("OTHERS_TRNS_AGREE")) && "Y".equals(reqMapData.get("OTHERS_TRNS_KT_AGREE"))) {
                reqMapData.put("OTHERS_TRNS_AGREE", "N");
                reqMapData.put("OTHERS_TRNS_KT_AGREE", "N");
                reqMapData.put("OTHERS_TRNS_ALL_AGREE", "Y");
            }
        }
        //logger.error("++++++++++++++++++settingData reqMapData.toString() : " + reqMapData.toString());
    }

    public void createXml(long custReqSeq, String userId , Map<String,String> requestData){

        /*
        //createXml 기존 소스 많이 생략했음. 필요시 ScanSvcImpl 참조
        */

        File folderPath = new File(scanPath);
        if(!folderPath.exists()){
            folderPath.mkdirs();
        }

        String tempPageCode = "";
        int totalPage = 0; //서식지 갯수
        boolean xmlCreate = true;

        StringBuffer inputDataXml = new StringBuffer();

        //파일네임 생성
        String xmlFileName = "cust_request" + "_" + custReqSeq + ".xml";

        //서식지 그룹 코드 가져오기
        String groupCode = this.getGroupCode(requestData);

        //그룹별 서식지 위치정보 가져오기
        List<HashMap<String,String>> appPointInfoList = custRequestDao.getAppFormPointList(groupCode);

        for (int i = 0; i < appPointInfoList.size(); i++) {
            HashMap<String,String> appPointInfo = appPointInfoList.get(i);

            String appFormColunmName = appPointInfo.get("COLUMN_NAME"); //데이터 저장 칼럼명
            int metaRow  = Integer.parseInt(appPointInfo.get("METAROW"));
            int metaLine = Integer.parseInt(appPointInfo.get("METALINE"));
            String codeDataYn = String.valueOf( StringUtil.NVL(appPointInfo.get("CODEDATA_YN"), ""));
            String codeData = String.valueOf(StringUtil.NVL(appPointInfo.get("CODEDATA"), ""));
            String pageCode = String.valueOf(StringUtil.NVL(appPointInfo.get("PAGE_CODE"), ""));
            String deleteColumnYn = String.valueOf(StringUtil.NVL(appPointInfo.get("DELETECOLUMNYN"), ""));
            String appFormInsertData = String.valueOf(requestData.get(appFormColunmName));

            if (i == 0) {
                inputDataXml.append("<INPUT_DATA pagecode='".concat(pageCode).concat("'>"));
                tempPageCode = pageCode;
                totalPage++;
            }

            if(!pageCode.equals(tempPageCode)) {
                inputDataXml.append("</INPUT_DATA>");
                inputDataXml.append("<INPUT_DATA pagecode='".concat(pageCode).concat("'>"));
                tempPageCode = pageCode;
                totalPage++;
            }

            //마켓팅 제공 동의
            if(deleteColumnYn.equals("CLAUSE_PRI_AD_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_PRI_AD_FLAG"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }
            }

            //2015-12-23제휴관련 추가
            if(deleteColumnYn.equals("CLAUSE_JEHU_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_JEHU_FLAG"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }
            }

            //20160601 렌탈 단말배상금 안내사항 동의여부
            if(deleteColumnYn.equals("CLAUSE_RENTAL_MODEL_CP")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RENTAL_MODEL_CP"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }

            }
            //20160601 렌탈 단말배상금(부분파손) 안내사항 동의여부
            if(deleteColumnYn.equals("CLAUSE_RENTAL_MODEL_CP_PR")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RENTAL_MODEL_CP_PR"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }
            }
            //20160601 중고렌탈 프로그램 서비스 이용에 대한 동의서 동의여부
            if(deleteColumnYn.equals("CLAUSE_RENTAL_SERVICE")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RENTAL_SERVICE"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }
            }
            //20170110 MPPS35선불요금제 제약사항 동의 여부
            if(deleteColumnYn.equals("CLAUSE_MPPS35_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_MPPS35_FLAG"));
                if( !deleteTempValue.equals("Y")) { //Y일때만 서명 입력
                    continue;
                }
            }

            //201703 금융제휴 요금제 동의 여부
            if (deleteColumnYn.equals("CLAUSE_FINANCE_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_FINANCE_FLAG"));
                if (deleteTempValue.equals("Y")) { //Y일때만 서명 입력
                    //법정대리인 서명 여부
                    if ("MINOR".equals(codeData)) {
                           if (StringUtil.isEmpty(requestData.get("MINOR_AGENT_NAME")) || StringUtil.isEmpty(requestData.get("MINOR_AGENT_RRN"))) {
                               continue;
                        }
                    }
                } else {
                    continue;
                }
            }

            //단체보험동의
            if(deleteColumnYn.equals("CLAUSE_INSURANCE_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_INSURANCE_FLAG"));

                if (deleteTempValue.equals("Y")) { //Y일때만 서명 입력
                    //법정대리인 서명 여부
                    if ("MINOR".equals(codeData)) {
                           if (StringUtil.isEmpty(requestData.get("MINOR_AGENT_NAME")) || StringUtil.isEmpty(requestData.get("MINOR_AGENT_RRN"))) {
                               continue;
                        }
                    }
                } else {
                    continue;
                }
            }

            if(deleteColumnYn.equals("CLAUSE_5G_COVERAGE_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_5G_COVERAGE_FLAG"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }
            }

            if(deleteColumnYn.equals("PERSONAL_INFO_COLLECT_AGREE")) {
                String deleteTempValue = String.valueOf(requestData.get("PERSONAL_INFO_COLLECT_AGREE"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }
            }

            if(deleteColumnYn.equals("OTHERS_TRNS_AGREE")) {
                String deleteTempValue = String.valueOf(requestData.get("OTHERS_TRNS_AGREE"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }
            }

            if(deleteColumnYn.equals("CLAUSE_PARTNER_OFFER_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_PARTNER_OFFER_FLAG"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }
            }

            if(deleteColumnYn.equals("OTHERS_TRNS_KT_AGREE")) {
                String deleteTempValue = String.valueOf(requestData.get("OTHERS_TRNS_KT_AGREE"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }
            }

            if(deleteColumnYn.equals("OTHERS_TRNS_ALL_AGREE")) {
                String deleteTempValue = String.valueOf(requestData.get("OTHERS_TRNS_ALL_AGREE"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }
            }

            if(deleteColumnYn.equals("OTHERS_AD_RECEIVE_AGREE")) {
                String deleteTempValue = String.valueOf(requestData.get("OTHERS_AD_RECEIVE_AGREE"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }
            }

            if(deleteColumnYn.equals("INDV_LOCA_PRV_AGREE")) {
                String deleteTempValue = String.valueOf(requestData.get("INDV_LOCA_PRV_AGREE"));
                if(deleteTempValue.equals("N")) {
                    continue;
                }
            }

            //메타 데이터 여부
            if(codeDataYn.equals("Y")) {
                //데이터와 메타 정보가 동일 한지 확인한다.
                if(appFormInsertData.equals(codeData)) { // 메타데이터를 V로 변환한다.
                    xmlCreate = true;
                    if(appFormColunmName.equals("PROD_TYPE")){//20160608 렌탈일때만 출고가 밑에 텍스트 넣어준다
                        appFormInsertData = "(분실파손 단말배상금)";
                    }else{
                        appFormInsertData = "V";
                    }
                }else {
                    xmlCreate = false;
                }
            }else {
                xmlCreate = true;
            }

            //데이터 생성 여부
            if(xmlCreate) {
                if (StringUtil.isNotNull(appFormInsertData)) {
                    inputDataXml.append("<DATA XPosition='".concat(String.valueOf(metaLine)).concat("' YPosition='")
                            .concat(String.valueOf(metaRow)).concat("'><![CDATA[").concat(appFormInsertData).concat("]]></DATA>"));
                }
            }

        }
        inputDataXml.append("</INPUT_DATA>");

        inputDataXml.append("</ONLINE_INFORMATION>");

        //REGISTER_DATA XML 생성
        StringBuffer registerDataXml = new StringBuffer(InitializaionXml(custReqSeq, userId, totalPage, requestData));

        //REGISTER_DATA XML + INPUT XML
        String xml = registerDataXml.append(inputDataXml).toString();

        //logger.error("+++++++++++++++++++++inputDataXml.toString() : "+ inputDataXml.toString());
        //logger.error("+++++++++++++++++++++xml.toString() : "+ xml.toString());

        saveXML(xml, xmlFileName);
    }

    /**
     * 초기 XML 생성
     * @param InitializaionXml
     * @return
     */
    public String  InitializaionXml(long custReqSeq, String userId, int totalPage, Map<String,String> requestData)  {
        // --------------------------------------------------------------------
        // Initializaion.
        // --------------------------------------------------------------------
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version='1.0' encoding='utf-8' ?> "
                +     "<ONLINE_INFORMATION>"
                +     "<REGISTER_DATA>");

        String cstmrName =  String.valueOf( StringUtil.NVL(requestData.get("CSTMR_NAME"), ""));
        String agencyId = String.valueOf( StringUtil.NVL(requestData.get("CNTPNT_SHOP_ID"), ""));
        String companyId = String.valueOf( StringUtil.NVL(requestData.get("CNTPNT_SHOP_ID"), ""));
        String rgstPrsnId = String.valueOf( StringUtil.NVL(userId, ""));
        String resNo = String.valueOf( StringUtil.NVL(requestData.get("CONTRACT_NUM"), ""));

        xml.append("<AGENCY_ID>".concat(agencyId).concat("</AGENCY_ID>"));
        xml.append("<COMPANY_ID>".concat(companyId).concat("</COMPANY_ID>"));
        xml.append("<CUST_NM>".concat(cstmrName).concat("</CUST_NM>"));
        xml.append("<RGST_PRSN_ID>".concat(rgstPrsnId).concat("</RGST_PRSN_ID>"));
        xml.append("<RES_NO>".concat(resNo).concat("</RES_NO>"));
        xml.append("<TOTAL_PAGE>".concat(String.valueOf(totalPage)).concat("</TOTAL_PAGE>"));
        xml.append("</REGISTER_DATA>");

        return  xml.toString();
    }

    /**
     * 서식지 XML 생성 함수
     * @param xml
     */
    public void saveXML(String xml, String xmlFileName) {

        FileOutputStream fileOutputStream = null;

        try {

            String xmlSavePath = scanPath;

            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // XXE 방어 설정
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(xml)));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);  // 취약성 transformer 상속

            DOMSource source = new DOMSource(document);
            fileOutputStream = new FileOutputStream(new File(xmlSavePath + xmlFileName));
            StreamResult result = new StreamResult(fileOutputStream);
            transformer.transform(source, result);

        } catch(TransformerException e) {
            throw new McpCommonJsonException("0003" ,SCAN_XML_SAVE_EXCEPTION);
        }catch (Exception e) {
            throw new McpCommonJsonException("0003" ,SCAN_XML_SAVE_EXCEPTION);
        } finally {
            if(fileOutputStream != null) {
                try{
                    fileOutputStream.close();
                } catch(IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 저장된 XML 전달
     * @param xmlFileName
     * @throws IOException
     */
    public String xmlFileSend(long custReqSeq) {

        String xmlFileName = "cust_request" + "_" + custReqSeq + ".xml";

        // --------------------------------------------------------------------
        // Initializaion.
        // --------------------------------------------------------------------
        //요청 Url
        String requestUrl = scanUrl;
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        boolean isV25 = false;

        if (userSessionDto != null) {
            //테스트 계정 To_BE 호출
            String isExceptionId = NmcpServiceUtils.getCodeNm(Constants.ID_GROUP_EXCEPTION , userSessionDto.getUserId());
            if (isExceptionId != null && "Y".equals(isExceptionId) ) {
                requestUrl = scanUrlV25;
                isV25 = true;

                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("SEND_SCAN_v25");
                mcpIpStatisticDto.setPrcsSbst("CUST_REQ_SEQ[" + custReqSeq + "]CALL_URL["+requestUrl+"]");
                mcpIpStatisticDto.setParameter(custReqSeq + "");
                mcpIpStatisticDto.setTrtmRsltSmst("INIT");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            }
        }

        //인코딩
        String charSet = "UTF-8";
        // 파일 불러오는 경로
        String filePath = scanPath + xmlFileName;

        // --------------------------------------------------------------------
        // Service Process.
        // --------------------------------------------------------------------
        File xmlFile = new File(filePath);
        try {
            MultipartUtility multipart = new MultipartUtility(requestUrl, charSet);
            multipart.addFilePart("TransferFile", xmlFile);
            List<String> responseList = multipart.finish();

            if (isV25) {
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("SEND_SCAN_v25");
                String prcsSbst = responseList.toString();
                if (prcsSbst != null && prcsSbst.length() > 1000) {
                    prcsSbst = prcsSbst.substring(0, 1000);
                }
                mcpIpStatisticDto.setPrcsSbst(prcsSbst);
                mcpIpStatisticDto.setParameter(custReqSeq + "");
                mcpIpStatisticDto.setTrtmRsltSmst("RESULT");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            }

            //return된 scan_id 추출
            String returnScanId = "";

            for (int i = 0; i < responseList.size(); i++) {
                String listStr = responseList.get(i);
                if(listStr.contains("<SCAN_ID>")) {
                    listStr = listStr.replace("<SCAN_ID>", "");
                    listStr = listStr.replace("</SCAN_ID>", "");
                    returnScanId = listStr;
                }
            }
            //logger.error("+++++++++++++++++++++++++++++++++++ responseList.toString() : " + responseList.toString());

            return returnScanId;

        } catch(IOException e) {
            throw new McpCommonJsonException("0003" ,SCAN_SERVER_SEND_EXCEPTION);
        } catch (Exception e) {
           throw new McpCommonJsonException("0002" ,SCAN_SERVER_SEND_EXCEPTION);
        } finally {
            // DELETE FILE
            if (xmlFile != null) {
                xmlFile.delete();
            }
        }
    }

    //서식지 그룹 코드 return
    private String getGroupCode(Map<String,String> requestData) {

        String groupCode = ""; //서식지그룹코드구분
        String reqType = requestData.get("REQ_TYPE"); //요청타입

        if("CL".equals(reqType)) {
            groupCode = "D001"; //통화내역열람
        }else if("NC".equals(reqType)) {
            groupCode = "E001"; //명의변경
        }else if("IS".equals(reqType)) {
            //자급제전용 보험
            if ("|PL214L310|PL214L312|PL214L316|PL214L317|PL214L319|PL245L235|PL245L236|PL245L237|PL245L233|PL245L234".indexOf(requestData.get("INSR_PROD_CD")) > 0) {
                /*
                     기존
                      부가상품 / 휴대폰안심보험 안드로이드 플래티넘 / PL214L310
                      부가상품 / 휴대폰안심보험 안드로이드 프리미엄 / PL214L312
                      부가상품 / 휴대폰안심보험 아이폰 프리미엄 / PL214L316
                      부가상품 / 휴대폰안심보험 아이폰 베이직 / PL214L317
                      부가상품 / 휴대폰안심보험 아이폰 플래티넘 / PL214L319

                      신규 24년 7월 02일
                      i-분실파손 150  PL245L235  685
                      i-분실파손 90   PL245L236  1045
                      i-분실파손 50   PL245L237  1435
                      중고 파손 100   PL245L233  1800
                      중고 파손 40    PL245L234  2185

                    */
                groupCode = "C002";
            }
            //그외
            else {
                groupCode = "C001";
            }
        }else if("CC".equals(reqType)) {
            groupCode = "G001"; //해지상담
        }

        return groupCode;
    }
}
