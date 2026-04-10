package com.ktis.msp.org.bpamgmt.controller;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.bpamgmt.service.BpaMgmtService;
import com.ktis.msp.org.bpamgmt.vo.BpaMgmtExcelReqVO;
import com.ktis.msp.org.bpamgmt.vo.BpaMgmtFileReqVO;
import com.ktis.msp.org.bpamgmt.vo.BpaMgmtReqVO;
import com.ktis.msp.org.bpamgmt.vo.BpaMgmtUploadInfoVO;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.util.ExcelFilesUploadUtil;
import com.ktis.msp.util.StringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class BpaMgmtController extends BaseController {

    @Autowired
    private BpaMgmtService bpaMgmtService;

    @Autowired
    private MenuAuthService menuAuthService;

    @Autowired
    private FileDownService fileDownService;

    @Autowired
    private UserInfoMgmtService userInfoMgmtService;

    @Autowired
    private BtchMgmtService btchMgmtService;

    /**
     * propertiesService
     */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    public BpaMgmtController() {
        setLogPrefix("[BpaMgmtController] ");
    }

    /**
     * @Description : [정산 데이터 다운로드] 화면
     * @Param : pRequestParamMap
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 02. 24.
     */
    @RequestMapping(value = "/org/bpaMgmt/bpaView.do", method = {POST, GET})
    public String bpaMgmt(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , ModelMap model
            , @RequestParam Map<String, Object> pRequestParamMap) {

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 권한
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // admin/DEV 권한 체크
            boolean isAdminDev = userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId())
                    || userInfoMgmtService.isDevGroupUser(loginInfo.getUserId());

            model.addAttribute("isAdminDev", isAdminDev ? "Y" : "N");
            model.addAttribute("loginInfo", loginInfo);
            model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

            return "/org/bpamgmt/bpaView";

        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }

    /**
     * @Description : [정산 데이터] 업무 목록 조회
     * @Param : pRequestParamMap
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 02. 24.
     */
    @RequestMapping(value = "/org/bpaMgmt/getDataTaskList.json")
    public String getDataTaskList(HttpServletRequest pRequest
            , @ModelAttribute("searchVO") BpaMgmtReqVO searchVO
            , HttpServletResponse pResponse
            , ModelMap model
            , @RequestParam Map<String, Object> pRequestParamMap) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 권한
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // admin/DEV 권한 체크
            boolean isAdminDev = userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId())
                    || userInfoMgmtService.isDevGroupUser(loginInfo.getUserId());
            searchVO.setIsAdminDev(isAdminDev ? "Y" : "N");

            List<?> resultList = bpaMgmtService.getDataTaskList(searchVO);
            resultMap = makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP2002401", "정산 데이터 다운로드")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * @Description : [정산 데이터] 업무 등록
     * @Param : bpaMgmtReqVo
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 02. 24.
     */
    @RequestMapping(value = "/org/bpaMgmt/insertDataTask.do", method = {POST, GET})
    public String insertDataTask(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , ModelMap model
            , @ModelAttribute("searchVO") BpaMgmtReqVO searchVO) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // LoginCheck
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 필수값 체크
            if(KtisUtil.isEmpty(searchVO.getBpaId())){
                throw new MvnoServiceException("필수값이 누락되었습니다.");
            }

            bpaMgmtService.insertDataTask(searchVO);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
        } catch (Exception e) {
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(),
                    messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
                    e.getMessage(), "MSP2002401", "정산 데이터 다운로드")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * @Description : [정산 데이터] 업무 수정
     * @Param : bpaMgmtReqVo
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 02. 24.
     */
    @RequestMapping(value = "/org/bpaMgmt/updateDataTask.do", method = {POST, GET})
    public String updateDataTask(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , ModelMap model
            , @ModelAttribute("searchVO") BpaMgmtReqVO searchVO) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // LoginCheck
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 필수값 체크
            if(KtisUtil.isEmpty(searchVO.getBpaId())){
                throw new MvnoServiceException("필수값이 누락되었습니다.");
            }

            bpaMgmtService.updateDataTask(searchVO);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
        } catch (Exception e) {
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(),
                    messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
                    e.getMessage(), "MSP2002401", "정산 데이터 다운로드")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }
    
    /**
     * @Description : [정산 데이터] 엑셀 업로드 양식 다운로드
     * @Param : pRequestParamMap
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 03. 03
     */
    @RequestMapping(value = "/org/bpaMgmt/getDataTaskExcelTempDown.json")
    public String getDataTaskExcelTempDown(@RequestParam Map<String, Object> pRequestParamMap
            , @RequestParam String bpaId
            , HttpServletRequest request
            , HttpServletResponse response
            , ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String returnMsg = null;
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        String strFileName = null;

        try {
            // loginCheck
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 화면인 경우
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // bpaId에 따른 분기 처리 확인
            if ("J000002".equals(bpaId)) {
                strFileName = bpaMgmtService.getCntrChannelExcelTempDown(request, response);
            }

            // 필수값 체크
            if(KtisUtil.isEmpty(strFileName)) {
                returnMsg = "등록되지 않은 업무입니다.";
                throw new MvnoServiceException("등록되지 않은 업무입니다.");
            }

            file = new File(strFileName);

            response.setContentType("applicaton/download");
            response.setContentLength((int) file.length());

            in = new FileInputStream(file);
            out = response.getOutputStream();

            int temp = -1;
            while ((temp = in.read()) != -1) {
                out.write(temp);
            }

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
            resultMap.put("msg", "다운로드성공");
        } catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
            resultMap.put("msg", returnMsg);
            throw new MvnoErrorException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new MvnoErrorException(e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    throw new MvnoErrorException(e);
                }
            }
        }
        file.delete();

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * @Description : [정산 데이터] 엑셀업로드 > 업로드할 데이터 리스트 세팅
     * @Param : paramMap
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 03. 04
     */
    @RequestMapping(value = "/org/bpaMgmt/getDataTaskExcelTempList.json")
    public String getDataTaskExcelTempList(HttpServletRequest request
            , HttpServletResponse response
            , @ModelAttribute("searchVO") BpaMgmtUploadInfoVO searchVO
            , ModelMap model
            , @RequestParam Map<String, Object> pRequestParamMap) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // loginCheck
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 권한 체크
            if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 파일 경로 생성
            String baseDir = propertiesService.getString("fileUploadBaseDirectory");
            String sOpenFileName = baseDir + "/CMN/" + searchVO.getUploadFileNm();

            // 엑셀 컬럼 매핑
            String[] arrCell = {"svcCntrNo"};

            // 파일 존재 체크
            File file = new File(sOpenFileName);
            if (!file.exists()) {
                throw new MvnoServiceException("업로드 파일이 존재하지 않습니다.");
            }

            // 파일 읽기
            List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.org.bpamgmt.vo.BpaMgmtUploadInfoVO", sOpenFileName, arrCell);
            resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, uploadList, uploadList.size());
            model.addAttribute("result", resultMap);

        } catch (Exception e) {
            resultMap.clear();
            throw new MvnoErrorException(e);
        }

        return "jsonView";
    }

    /**
     * @Description : [정산 데이터] 엑셀업로드 > 업로드된 데이터 등록
     * @Param : pRequestParamMap, data, searchVO
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 03. 04
     */
    @RequestMapping("/org/bpaMgmt/regDataTaskExcelTempComplete.json")
    public String regDataTaskExcelTempComplete(@ModelAttribute("searchVO") BpaMgmtUploadInfoVO searchVO,
                                               @RequestBody String data,
                                               @RequestParam Map<String, Object> pRequestParamMap,
                                               HttpServletRequest request,
                                               HttpServletResponse response,
                                               ModelMap model) {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("[정산 데이터] 엑셀업로드 > 업로드된 데이터 등록 START"));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // loginCheck
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 권한체크
            if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            BpaMgmtUploadInfoVO vo = new ObjectMapper().readValue(data, BpaMgmtUploadInfoVO.class);

            bpaMgmtService.regDataTaskExcelTempComplete(vo.getItems(), searchVO);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
                    messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
                    e.getMessage(), "MSP2002401", "정산 데이터 다운로드")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * @Description : [정산 데이터] 엑셀업로드 > 서비스계약번호 초기화 (사용여부 N 변경)
     * @Param : pRequestParamMap
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 03. 04.
     */
    @RequestMapping(value = "/org/bpaMgmt/getDataExcelListUseYn.do")
    public String updateDataExcelListUseYn(ModelMap model,
                                           HttpServletRequest pRequest,
                                           HttpServletResponse pResponse,
                                           @ModelAttribute("searchVO") BpaMgmtUploadInfoVO searchVO) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // LoginCheck
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 필수값 체크
            if(KtisUtil.isEmpty(searchVO.getBpaId())){
                throw new MvnoServiceException("필수값이 누락되었습니다.");
            }

            // update
            bpaMgmtService.updateDataExcelListUseYn(searchVO);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(),
                    messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
                    e.getMessage(), "MSP2002401", "정산 데이터 다운로드")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * @Description : [정산 데이터] 파일관리 > 파일 목록 조회
     * @Param : pRequestParamMap
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 02. 27.
     */
    @RequestMapping("/org/bpaMgmt/getDataTaskFileList.json")
    public String getDataTaskFileList(HttpServletRequest pRequest
            , @ModelAttribute("searchVO") BpaMgmtFileReqVO searchVO
            , HttpServletResponse pResponse
            , ModelMap model
            , @RequestParam Map<String, Object> pRequestParamMap) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 권한
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // admin/DEV 권한 체크
            boolean isAdminDev = userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId())
                    || userInfoMgmtService.isDevGroupUser(loginInfo.getUserId());
            searchVO.setIsAdminDev(isAdminDev ? "Y" : "N");

            List<?> resultList = bpaMgmtService.getDataTaskFileList(searchVO);
            resultMap = makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(),
                    messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
                    e.getMessage(), "MSP2002401", "정산 데이터 다운로드")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * @Description : [정산 데이터] 파일관리 > 파일 등록
     * @Param : bpaMgmtFileReqVO
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 02. 28.
     */
    @RequestMapping("/org/bpaMgmt/insertDataTaskFile.do")
    public String insertDataTaskFile(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , ModelMap model
            , @ModelAttribute("searchVO") BpaMgmtFileReqVO searchVO) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // LoginCheck
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 필수값 체크
            if(KtisUtil.isEmpty(searchVO.getBpaId()) || KtisUtil.isEmpty(searchVO.getBatchJobId())){
                throw new MvnoServiceException("필수값이 누락되었습니다.");
            }

            bpaMgmtService.insertDataTaskFile(searchVO);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
        } catch (Exception e) {
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(),
                    messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
                    e.getMessage(), "MSP2002401", "정산 데이터 업무 파일 등록")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * @Description : [정산 데이터] 파일관리 > 파일 수정
     * @Param : bpaMgmtFileReqVO
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 03. 02.
     */
    @RequestMapping("/org/bpaMgmt/updateDataTaskFile.do")
    public String updateDataTaskFile(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , ModelMap model
            , @ModelAttribute("searchVO") BpaMgmtFileReqVO searchVO) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // LoginCheck
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if (!"10".equals(searchVO.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 필수값 체크
            if(KtisUtil.isEmpty(searchVO.getBpaId()) || KtisUtil.isEmpty(searchVO.getFileId())){
                throw new MvnoServiceException("필수값이 누락되었습니다.");
            }

            // update
            bpaMgmtService.updateDataTaskFile(searchVO);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
        } catch (Exception e) {
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(),
                    messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
                    e.getMessage(), "MSP2002401", "정산 데이터 업무 파일 수정")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * @Description : [정산 데이터] 자료생성 (배치요청)
     * @Param :
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 03. 03
     */
    @RequestMapping("/org/bpaMgmt/getDataTaskFileExcelDown.json")
    public String getDataTaskFileExcelDown(@ModelAttribute("searchVO") BpaMgmtExcelReqVO searchVO
            , Model model
            , @RequestParam Map<String, Object> pRequestParamMap
            , HttpServletRequest request
            , HttpServletResponse response) {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("[정산 데이터] 자료생성 (배치요청) START"));
        logger.info(generateLogMsg("Return Vo [BpaMgmtExcelReqVO] = " + searchVO.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        int cnt = 0;

        try {

            // loginCheck
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 권한
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 날짜유형이 01 이면 baseDt 필수 / 02 startDt, endDt 필수
            if ("01".equals(searchVO.getPeriodCd()) && KtisUtil.isEmpty(searchVO.getStartDt())) {
                throw new MvnoServiceException("필수값이 누락되었습니다.");
            }
            if ("02".equals(searchVO.getPeriodCd()) && (KtisUtil.isEmpty(searchVO.getStartDt()) || StringUtil.isBlank(searchVO.getEndDt()))) {
                throw new MvnoServiceException("필수값이 누락되었습니다.");
            }

            // 엑셀 파일 업로드 하지 않은 경우
            if ("Y".equals(searchVO.getExcelYn())) {
                cnt = bpaMgmtService.selectBpaUploadInfoCnt(searchVO.getBpaId());
                if (cnt <= 0) {
                    throw new MvnoServiceException("업로드된 데이터가 없습니다.");
                }
            }

            Map<String, Object> excelMap = new HashMap<String, Object>();
            BatchJobVO vo = new BatchJobVO();
            Map<String, Object> paramMap = new HashMap<String, Object>();


            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
            if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getHeader("REMOTE_ADDR");
            if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getRemoteAddr();

            // 엑셀 다운로드 HISTORY 저장
            int exclDnldId = fileDownService.getSqCmnExclDnldHst();

            excelMap.put("EXCL_DNLD_ID", exclDnldId);
            excelMap.put("MENU_ID", request.getParameter("menuId"));
            excelMap.put("USR_ID", loginInfo.getUserId());
            excelMap.put("USR_NM", loginInfo.getUserName());
            excelMap.put("ORGN_ID", loginInfo.getUserOrgnId());
            excelMap.put("DUTY_NM", "ORG");
            excelMap.put("MENU_NM", "정산 데이터 다운로드");

            // 엑셀 요청값
            vo.setExecTypeCd("REQ");
            vo.setBatchJobId(searchVO.getBatchJobId());
            vo.setSessionUserId(loginInfo.getUserId());
            vo.setExclDnldId(exclDnldId);

            if (!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId())
                    && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId()))
                vo.setExclDnldYn(vo.getExclDnldYn() + 1);

            paramMap.put("bpaId", searchVO.getBpaId());
            paramMap.put("userId", loginInfo.getUserId());
            paramMap.put("dwnldRsn", request.getParameter("DWNLD_RSN"));
            paramMap.put("ipAddr", ipAddr);
            paramMap.put("menuId", request.getParameter("menuId"));
            paramMap.put("exclDnldId", exclDnldId);

            // 배치코드 분기
            String batchJobId = searchVO.getBatchJobId();

            // 기준일자
            if("BATCH00267".equals(batchJobId)
                    || "BATCH00268".equals(batchJobId)) {
                bpaMgmtService.initBpaExcelParameter(excelMap, paramMap, searchVO.getStartDt());
            }
            // 기준일자 + 요금제코드
            else if ("BATCH00269".equals(batchJobId)) {
                bpaMgmtService.initBpaExcelParameter(excelMap, paramMap, searchVO.getStartDt(), searchVO.getEtc1());
            }
            // 시작일자-종료일자
            else if ("BATCH00271".equals(batchJobId) || "BATCH00270".equals(batchJobId)) {
                bpaMgmtService.initBpaExcelParameterPeriod(excelMap, paramMap, searchVO.getStartDt(), searchVO.getEndDt());
            }
            else {
                throw new MvnoServiceException("등록되지 않은 배치코드 입니다.");
            }

            // 이력 저장
            fileDownService.insertCmnExclDnldHst(excelMap);
            excelMap.clear();

            ObjectMapper mapper = new ObjectMapper();
            String searchParam = mapper.writeValueAsString(paramMap);

            vo.setExecParam(searchParam);
            btchMgmtService.insertBatchRequest(vo);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
            resultMap.put("msg", "다운로드성공");

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, resultMap))
                throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * @Description : [정산 데이터] 엑셀업로드 > 업로드된 서비스계약번호 리스트 조회
     * @Param : pRequestParamMap
     * @Return : String
     * @Author : yhk
     * @CreateDate : 2026. 03. 04.
     */
    @RequestMapping(value = "/org/bpaMgmt/getDataExcelList.json")
    public String getDataExcelList(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , ModelMap model
            , @RequestParam Map<String, Object> pRequestParamMap
            , @ModelAttribute("searchVO") BpaMgmtUploadInfoVO searchVO) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 권한
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 필수값 조회
            if(KtisUtil.isEmpty(searchVO.getBpaId())){
                throw new MvnoServiceException("필수값이 누락되었습니다.");
            }

            List<?> resultList = bpaMgmtService.getDataExcelList(searchVO);
            resultMap = makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(),
                    messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
                    e.getMessage(), "MSP2002401", "정산 데이터 업무 파일 수정")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

}
