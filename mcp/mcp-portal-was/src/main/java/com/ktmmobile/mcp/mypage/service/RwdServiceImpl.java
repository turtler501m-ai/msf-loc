package com.ktmmobile.mcp.mypage.service;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.appform.dao.AppformDao;
import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.dto.FileBoardDTO;
import com.ktmmobile.mcp.common.dto.McpIpStatisticDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.mypage.dao.RwdDao;
import com.ktmmobile.mcp.mypage.dto.RwdDto;
import com.ktmmobile.mcp.mypage.dto.RwdFailHistDto;
import com.ktmmobile.mcp.mypage.dto.RwdOrderDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Repository
public class RwdServiceImpl implements RwdService {

    private static Logger logger = LoggerFactory.getLogger(RwdServiceImpl.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /** 파일업로드 경로 */
    @Value("${common.fileupload.msp.base}")
    private String fileuploadBase;

    @Value("${scan.app.path}")
    private String scanPath;

    @Value("${scan.url}")
    private String scanUrl;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private SmsSvc smsSvc;

    @Autowired
    private CertService certService;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    AppformDao appformDao;

    @Autowired
    private RwdDao rwdDao;


    /**
     * 자급제 보상서비스 가입 상태 조회
     * @author hsy
     * @Date : 2023.03.02
     * @param ncn
     * @return String
     */
    @Override
    public String selectRwdInfoByCntrNum(String ncn) {

        /*
            resultCd
            CALL > 상담사 문의
            EXIST > 이미 가입된 상태
            IMPOSSIBLE > 가입 불가
            POSSIBLE > 가입 가능
            ING > 가입 심사 중
        */

        // default 상태 세팅
        String resultCd= "CALL";

        RestTemplate restTemplate = new RestTemplate();
        RwdOrderDto rwdInfo= restTemplate.postForObject(apiInterfaceServer + "/mypage/selectRwdInfoByCntrNum", ncn, RwdOrderDto.class);

        // 1. 계약번호 미존재
        if(rwdInfo == null) return resultCd;

        // 2. member(가입자) 테이블에 존재
        if(rwdInfo.getRwdSeq() > 0){
            if("1".equals(rwdInfo.getRwdStatCd())) resultCd= "EXIST"; // 2-1. 정상적으로 부가서비스 가입된 상태 (가입)
            else if("0".equals(rwdInfo.getRwdStatCd())) resultCd="ING"; // 2-2. 서비스 적격이나 아직 부가서비스가 신청되지 않은 상태 (처리중)
            else resultCd= "POSSIBLE";  // 2-3. 해지된 상태_재가입가능 (만료해지, 중도해지 모두 재가입 가능)
        }
        // 3. order(주문자) 테이블에 존재
        else if(!"".equals(StringUtil.NVL(rwdInfo.getRwdProdCd(),""))){
            if("Y".equals(rwdInfo.getIfTrgtCd())) resultCd= "ING";  // 3-1. 연동대상 (처리중 상태)
            else if("S".equals(rwdInfo.getIfTrgtCd())){ // 3-2. 연동완료_심사결과 추가 확인
                if("N".equals(rwdInfo.getVrfyRsltCd())) resultCd= "POSSIBLE";   // 3-2-1. 심사결과 부적격_재가입 가능
                else resultCd= "ING";  // 3-2-2. 심사결과 적격 또는 심사중(Y,Z)
            }
            else resultCd= "POSSIBLE";  // 3-3. 연동 전 취소(C)_재가입가능
        }
        // 4. 자급제 보상서비스 신청 이력 없음
        else{
            resultCd= "POSSIBLE";
        }

        // 5. 가입 가능한 경우 단말 구매유형 확인
        if("POSSIBLE".equals(resultCd)){
            String reqBuyType= rwdInfo.getReqBuyType();
            if("UU".equals(reqBuyType)) resultCd= "POSSIBLE";  // 5-1. 유심개통 고객 가입 가능
            else if("MM".equals(reqBuyType)) resultCd= "IMPOSSIBLE"; // 5-2. 단말개통 고객 가입 불가능
            else resultCd= "CALL"; // 5-3. 구매유형이 없는 경우 상담사 문의
        }

        return resultCd;
    }

    /**
     * 2023.03.02 hsy
     * 자급제 보상 서비스 리스트 가져오기
     * @param rwdProdCd
     * @return RwdDTO[]
     */
    @Override
    public RwdDto[] selectRwdProdList(String rwdProdCd) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/selectRwdProdList", rwdProdCd, RwdDto[].class);
    }

    /**
     * 2023.03.10 hsy
     * 자급제 보상서비스 가입 시 해당 imei 사용가능 여부 체크
     * @param imei
     * @param imeiTwo
     * @return String
     */
    @Override
    public String checkImeiForRwd(String imei, String imeiTwo) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String,Object>();
        params.add("imei", imei);
        params.add("imeiTwo", imeiTwo);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/checkImeiForRwd", params, String.class);
    }

    /**
     * 계약번호로 신청정보 조회
     * @author hsy
     * @Date : 2023.02.28
     * @param contractNum
     * @return RwdOrderDto[]
     */
    @Override
    public RwdOrderDto[]  selectMcpRequestInfoByCntrNum(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/selectMcpRequestInfoByCntrNum", contractNum, RwdOrderDto[].class);
    }

    /**
     * 자급제 보상서비스 등록 (파일업로드와 테이블 insert 후 서식지 생성)
     * @author hsy
     * @Date : 2023.02.28
     * @param rwdOrderDto
     * @return HashMap<String, String>
     */
    @Override
    @Transactional
    public HashMap<String, String> saveRwdOrder(RwdOrderDto rwdOrderDto) {

        HashMap<String, String> rtnMap= new HashMap<>();

        // ============ STEP START ============
        // 1. 최소 스텝개수 확인
        if(certService.getStepCnt() < 7 ){
            throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
        }

        // 2. 최종 정보 검증
        // step종료 여부, 이름, 생년월일, 계약번호, 본인인증유형, CI
        String[] certKey= {"urlType", "stepEndYn", "name", "birthDate", "contractNum"
                         , "authType", "connInfo"};
        String[] certValue= {"saveReqRwdForm", "Y", rwdOrderDto.getAuthCstmrName(), rwdOrderDto.getAuthBirthDate(), rwdOrderDto.getContractNum()
                           , rwdOrderDto.getOnlineAuthType(), rwdOrderDto.getSelfCstmrCi()};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
        }
        // ============ STEP END ============

        // 자급제 보상 서비스 가입 실패 로그 기록용
        RwdFailHistDto rwdFailHistDto= new RwdFailHistDto();
        rwdFailHistDto.setRequestKey(rwdOrderDto.getRequestKey());
        rwdFailHistDto.setResNo(rwdOrderDto.getResNo());
        rwdFailHistDto.setContractNum(rwdOrderDto.getContractNum());
        rwdFailHistDto.setUserId(rwdOrderDto.getRegstId());

        // 1. 파일 upload
        MultipartFile[] arrFile = rwdOrderDto.getFile();

        // 1-1. 파일 개수 체크 (1개)
        if(arrFile == null || arrFile.length != 1) {
            throw new McpCommonJsonException("FAIL1", "1개의 증빙서류만 첨부하여 주시기 바랍니다.");
        }

        // 1-2. 파일 업로드 시작
        // 실제 서버에 저장될 물리 파일명 추출
        String rwdFileName= this.selectRwdNewFileNm();
        if(rwdFileName == null){
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("RWD SERVER FILE NAME IS NULL");
            this.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("FAIL2", "파일명 추출 과정에서 오류가 발생했습니다.");
        }

        FileBoardDTO fileBoardDTO = new FileBoardDTO();
        String rwdFileDir = "RWD";
        MultipartFile file = arrFile[0];
        FileUploadUtil fileUploadUtill = new FileUploadUtil(fileuploadBase, "");

        try{
            fileBoardDTO = fileUploadUtill.upload(file, rwdFileDir, rwdFileName); // 파일업로드

            int fileUploadRes= fileBoardDTO.getRst();
            if(fileUploadRes==2){ // 파일 업로드 거절 (지원하지 않는 확장자)
                throw new McpCommonJsonException("FAIL3", "파일 등록에 실패했습니다.");
            }

            // 자급제 보상서비스 주문 정보에 insert할 데이터 세팅
            String filePathNm = fileBoardDTO.getFilePathNM(); // "물리적파일명.확장자"

            String fPer = File.separator;
            int extPointIndex= filePathNm.lastIndexOf(".");

            String fileId= filePathNm.substring(0, extPointIndex);
            String fileExt= filePathNm.substring(extPointIndex+1);
            String fileDir= fileuploadBase+fPer+rwdFileDir+fPer+filePathNm;

            rwdOrderDto.setFileId(fileId);   // 물리적 파일명
            rwdOrderDto.setFileExt(fileExt); // 확장자
            rwdOrderDto.setFileDir((fileDir).replace("\\", "/")); // 파일이 저장된 물리적 경로

        } catch(DataAccessException e) {
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("EXCEPTION OCCURED DURING RWD FILE UPLOAD");
            this.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("FAIL4", "파일 등록에 실패했습니다.");
        } catch(Exception e){ // 파일 등록 실패
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("EXCEPTION OCCURED DURING RWD FILE UPLOAD");
            this.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("FAIL4", "파일 등록에 실패했습니다.");
        }

        RestTemplate restTemplate = new RestTemplate();
        rwdOrderDto.setFile(null); // api 통신을 위해 file은 넘기지 않음

        // 2. 중복신청 방지를 위해 자급제 보상 서비스 신청 이력 조회
        String existYn= restTemplate.postForObject(apiInterfaceServer + "/mypage/selectRwdInfoByResNo", rwdOrderDto, String.class);
        if("Y".equals(existYn) && "2".equals(rwdOrderDto.getChnCd())){
            // 3. 기신청건으로 update 처리 (update건은 셀프케어에선 존재x 따라서 서식지 생성 안함)
            rtnMap= restTemplate.postForObject(apiInterfaceServer + "/mypage/updateRwdOrder", rwdOrderDto, HashMap.class);
        }else{
            // 4. 신규가입으로 자급제 보상서비스 order 테이블 insert
            rtnMap= restTemplate.postForObject(apiInterfaceServer + "/mypage/insertRwdOrder", rwdOrderDto, HashMap.class);
        }

        if(AJAX_SUCCESS.equals(rtnMap.get("RESULT_CODE"))){

            String rwdSeq= rtnMap.get("RWD_SEQ");
            // 5. 자급제 보상 서비스 서식지 생성 (셀프케어인 경우만 생성)
            if("4".equals(rwdOrderDto.getChnCd()) && !"".equals(StringUtil.NVL(rwdSeq, ""))){

                try{
                    this.prodSendScan(rwdSeq, rwdOrderDto.getRegstId());

                    //로그 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("RWD_SCAN");
                    mcpIpStatisticDto.setPrcsSbst("REQUEST_KET["+ rwdSeq +"]");
                    mcpIpStatisticDto.setParameter(rwdSeq);
                    mcpIpStatisticDto.setTrtmRsltSmst("SUCCESS");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    rtnMap.put("RESULT_MSG", "자급제 보상서비스 신청이 완료되었습니다.");
                }catch (McpCommonJsonException e){

                    //로그 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("RWD_SCAN");
                    mcpIpStatisticDto.setPrcsSbst("REQUEST_KET["+ rwdSeq +"]");
                    mcpIpStatisticDto.setParameter(rwdSeq);
                    mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                    rtnMap.put("RESULT_CODE", "FAIL");
                    rtnMap.put("RESULT_MSG", "자급제 보상서비스 신청에 실패했습니다.");
                    logger.error(e.getMessage());
                }catch (Exception e) {

                    //로그 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("RWD_SCAN");
                    mcpIpStatisticDto.setPrcsSbst("REQUEST_KET["+ rwdSeq +"]");
                    mcpIpStatisticDto.setParameter(rwdSeq);
                    mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                    rtnMap.put("RESULT_CODE", "FAIL");
                    rtnMap.put("RESULT_MSG", "자급제 보상서비스 신청에 실패했습니다.");
                    logger.error(e.getMessage());
                }
            }
        }else{
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("EXCEPTION OCCURED DURING RWD ORDER INSERT");
            this.insertFailRwd(rwdFailHistDto);
        }

        return rtnMap;
    }



    /**
     * 2023.03.13 hsy
     * 자급제 보상서비스 물리 파일명 추출
     * @return String
     */
    @Override
    public String selectRwdNewFileNm() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/selectRwdNewFileNm", null, String.class);
    }


    /**
     * 2023.05.23
     * 자급제 보상서비스 가입 실패 로그 기록
     */
    @Override
    public void insertFailRwd(RwdFailHistDto rwdFailHistDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        rwdFailHistDto.setAccessIp(ipstatisticService.getClientIp());
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if(userSession !=null){
            rwdFailHistDto.setUserId(userSession.getUserId());
        }
        rwdFailHistDto.setAccessUrl(request.getRequestURI());

        rwdDao.insertFailRwd(rwdFailHistDto);
    }


    /**
     * 2023.05.18 hsy
     * 자급제 보상서비스 서식지 생성
     * @param rwdSeq
     * @param regstId
     */
    @Override
    public void prodSendScan(String rwdSeq, String regstId) {

        // 1. 서식지 정보 조회
        Map<String,String> requestData = this.getRwdOrderData(rwdSeq);
        if (requestData == null || requestData.size() == 0){
            throw new McpCommonJsonException("FAIL1" , "자급제 보상 서비스 신청 정보가 없습니다.");
        }

        // 2. 복호화 처리
        try {
            requestData.put("CSTMR_NATIVE_RRN", EncryptUtil.ace256Dec(requestData.get("CSTMR_NATIVE_RRN")));
        }catch (CryptoException e) {
            throw new McpCommonJsonException("FAIL2" ,ACE_256_DECRYPT_EXCEPTION);
        }

        //3. 주민번호 분석 값 설정
        settingSsnData(requestData);

        // 4. XML 파일 생성
        createXml(rwdSeq, regstId , requestData);

        // 5. XML 파일 전송 및 삭제 처리
        String returnScanId = xmlFileSend(rwdSeq);

        logger.error("=======rwd prodSendScan returnScanId====>" + returnScanId);

        // 6. 스캔 관련 테이블 update 처리 (EMV_SCAN_MST, EMV_SCAN_FILE)
        if(StringUtil.isNotNull(returnScanId)) {

            rwdDao.updateEmvScanMstRwd(returnScanId);
            rwdDao.updateEmvScanFileRwd(returnScanId);

            // 7. 서식지 합본
            String orgScanId = null;
            int maxFileNum = 0;

            // 7-1. 계약번호 SCAN_ID 추출
            orgScanId = this.getOrgScanId(requestData.get("CONTRACT_NUM"));

            if(StringUtil.isNotNull(orgScanId)) {
                // 7-2. MAX FILE_NUMBER 추출
                maxFileNum = rwdDao.getMaxFileNum(orgScanId);
                if(maxFileNum >= 0) {

                    // 7-3. 합본 처리 (자급제 보상 서비스는 이미지파일이 4개이므로 파일 select 후 하나씩 insert 처리)
                    // 신규 생성된 파일 리스트 조회
                    List<RwdOrderDto> scanFileList= rwdDao.getEmvScanFileList(returnScanId);

                    if(scanFileList != null && scanFileList.size() > 0){

                        RwdOrderDto rwdOrderDto = new RwdOrderDto();
                        rwdOrderDto.setOrgScanId(orgScanId);
                        rwdOrderDto.setScanId(returnScanId);
                        rwdOrderDto.setRegstId(regstId);

                        for(int i=0; i< scanFileList.size(); i++){
                            maxFileNum++;  // FILE_NUMBER 증가
                            rwdOrderDto.setMaxFileNum(maxFileNum);
                            rwdOrderDto.setScanFileId(scanFileList.get(i).getScanFileId());
                            rwdDao.insertEmvScanFile(rwdOrderDto);  // 하나씩 합본 처리
                        }
                    }
                }
            }
        }
    }

    /**
     * @Description : org 스캔아이디 조회(API호출)
     */
    private String getOrgScanId(String contractNum) {

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/getOrgScanId", contractNum, String.class);
    }

    /**
     * 2023.05.19
     * 자급제 보상서비스 서식지 정보 조회
     * @return Map<String, String>
     */
    private Map<String, String> getRwdOrderData(String rwdSeq) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/getRwdOrderData", rwdSeq, Map.class);
    }


    /**
     * 주민번호 분석후 값을 설정 처리 (자급제 보상 서비스에 맞게 간소화)
     */
    public void settingSsnData(Map<String,String> reqMapData) {

        if (reqMapData.containsKey("CSTMR_NATIVE_RRN")
                && !StringUtils.isBlank(reqMapData.get("CSTMR_NATIVE_RRN"))
                && reqMapData.get("CSTMR_NATIVE_RRN").length() > 7 ) {

            reqMapData.put("CSTMR_NATIVE_RRN", reqMapData.get("CSTMR_NATIVE_RRN").substring(0, 6));  // 주민번호 앞자리(생년월일)
        }
    }

    /**
     * xml 파일 생성 (자급제 보상 서비스에 맞게 간소화)
     */
    public void createXml(String rwdSeq, String regstId , Map<String,String> requestData){

        File folderPath = new File(scanPath);
        if(!folderPath.exists()) folderPath.mkdirs();

        String tempPageCode = "";
        int totalPage = 0; // 서식지 갯수
        boolean xmlCreate = true;

        StringBuffer inputDataXml = new StringBuffer();

        String xmlFileName = "rwd_request" + "_" + rwdSeq + ".xml";

        // 1. 자급제 보상 서비스 서식지 그룹
        String groupCode = "F001";

        // 2. 그룹별 서식지 위치정보 가져오기 (각 페이지 별 위치정보)
        List<HashMap<String,String>> appPointInfoList = appformDao.getAppFormPointList(groupCode);

        // 3. XML 생성
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

            // 자급제 보상 서비스 약관 동의 여부 (상품설명(이용료) 및 서비스 가입 동의)
            if(deleteColumnYn.equals("CLAUSE_RWD_REG_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RWD_REG_FLAG"));
                if(!"Y".equals(deleteTempValue)) continue;  // Y일때만 서명 입력
            }

            // 자급제 보상 서비스 약관 동의 여부 (만기 시 보장율/보장금액 지급기준 이행 동의)
            if(deleteColumnYn.equals("CLAUSE_RWD_PAYMENT_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RWD_PAYMENT_FLAG"));
                if(!"Y".equals(deleteTempValue)) continue;  // Y일때만 서명 입력
            }

            // 자급제 보상 서비스 약관 동의 여부 (서비스 단말 반납 조건, 등급산정 및 평가 기준 동의)
            if(deleteColumnYn.equals("CLAUSE_RWD_RATING_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RWD_RATING_FLAG"));
                if(!"Y".equals(deleteTempValue)) continue;  // Y일때만 서명 입력
            }

            // 자급제 보상 서비스 약관 동의 여부 (만기 시 보장율/보장금액 지급기준 이행 동의 + 서비스 단말 반납 조건, 등급산정 및 평가 기준 동의)
            if(deleteColumnYn.equals("CLAUSE_RWD_USE_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RWD_USE_FLAG"));
                if(!"Y".equals(deleteTempValue)) continue;  // Y일때만 서명 입력
            }

            // 자급제 보상 서비스 약관 동의 여부 (개인정보 수집ㆍ이용에 대한 동의)
            if(deleteColumnYn.equals("CLAUSE_RWD_PRIVACY_INFO_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RWD_PRIVACY_INFO_FLAG"));
                if(!"Y".equals(deleteTempValue)) continue;  // Y일때만 서명 입력
            }

            // 자급제 보상 서비스 약관 동의 여부 (서비스 위탁사 개인정보 수집ㆍ이용ㆍ제공ㆍ위탁 동의서)
            if(deleteColumnYn.equals("CLAUSE_RWD_TRUST_FLAG")) {
                String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RWD_TRUST_FLAG"));
                if(!"Y".equals(deleteTempValue)) continue;  // Y일때만 서명 입력
            }

            // 메타 데이터 여부 (데이터와 메타 정보가 동일한 경우 V[체크표시]로 표시해준다)
            if(codeDataYn.equals("Y")) {
                if(codeData.equals(appFormInsertData)) {
                    xmlCreate = true;
                    appFormInsertData = "V";
                }else{
                    xmlCreate = false;
                }
            }else {
                xmlCreate = true;
            }

            // 데이터 생성 여부
            if(xmlCreate) {
                if (StringUtil.isNotNull(appFormInsertData)) {
                    inputDataXml.append("<DATA XPosition='".concat(String.valueOf(metaLine)).concat("' YPosition='").concat(String.valueOf(metaRow)).concat("'><![CDATA[").concat(appFormInsertData).concat("]]></DATA>"));
                }
            }
        }

        inputDataXml.append("</INPUT_DATA>");
        inputDataXml.append("</ONLINE_INFORMATION>");

        // REGISTER_DATA XML 생성
        StringBuffer registerDataXml = new StringBuffer(InitializaionXml(regstId, totalPage, requestData));

        //REGISTER_DATA XML + INPUT XML
        String xml = registerDataXml.append(inputDataXml).toString();

        saveXML(xml, xmlFileName);
    }


    /**
     * 초기 XML 생성
     */
    public String  InitializaionXml(String regstId, int totalPage, Map<String,String> requestData)  {

        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version='1.0' encoding='utf-8' ?>"
                +     "<ONLINE_INFORMATION>"
                +     "<REGISTER_DATA>");

        String cstmrName =  String.valueOf( StringUtil.NVL(requestData.get("CSTMR_NAME"), ""));
        String agencyId = String.valueOf( StringUtil.NVL(requestData.get("CNTPNT_SHOP_ID"), ""));
        String companyId = String.valueOf( StringUtil.NVL(requestData.get("CNTPNT_SHOP_ID"), ""));
        String rgstPrsnId = String.valueOf( StringUtil.NVL(regstId, ""));
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
     * 서식지 XML 임시 저장
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
            throw new McpCommonJsonException("0004" ,SCAN_XML_SAVE_EXCEPTION);
        } catch (Exception e) {
            throw new McpCommonJsonException("0003" ,SCAN_XML_SAVE_EXCEPTION);
        } finally {
            if(fileOutputStream != null) try{fileOutputStream.close();} catch(IOException e){ logger.error(e.getMessage());}
        }
    }

    /**
     * 저장된 XML 전달
     * @param rwdSeq
     */
    public String xmlFileSend(String rwdSeq) {

        String xmlFileName = "rwd_request" + "_" + rwdSeq + ".xml";

        // 스캔 아이디
        String returnScanId = "";

        //요청 Url
        String requestUrl = scanUrl;

        //인코딩
        String charSet = "UTF-8";

        // 임시 저장된 파일 불러오기
        String filePath = scanPath + xmlFileName;

        File xmlFile = new File(filePath);
        try {
            // 스캔파일 전송
            MultipartUtility multipart = new MultipartUtility(requestUrl, charSet);
            multipart.addFilePart("TransferFile", xmlFile);
            List<String> responseList = multipart.finish();

            // SCAN_ID 추출
            for (int i = 0; i < responseList.size(); i++) {
                String listStr = responseList.get(i);
                if(listStr.contains("<SCAN_ID>")) {
                    listStr = listStr.replace("<SCAN_ID>", "");
                    listStr = listStr.replace("</SCAN_ID>", "");
                    returnScanId = listStr;
                    break;
                }
            }
        } catch(IOException e) {
            throw new McpCommonJsonException("0003" ,SCAN_SERVER_SEND_EXCEPTION);
        }catch (Exception e) {
            throw new McpCommonJsonException("0002" ,SCAN_SERVER_SEND_EXCEPTION);
        } finally {
            if (xmlFile != null) xmlFile.delete(); // 임시 저장된 파일 지우기
        }

        return returnScanId;
    }




}
