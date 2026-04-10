package com.ktis.msp.rcp.courtMgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.payinfo.vo.PayInfoDtlVO;
import com.ktis.msp.cmn.payinfo.vo.PayInfoNonBindVO;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.common.vo.FileInfoVO;
import com.ktis.msp.rcp.courtMgmt.service.RegstCrCstmrService;
import com.ktis.msp.rcp.courtMgmt.vo.RegstCrCstmrVO;

import CNPECMJava.CNPEncryptModule.crypto.FileHandle;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import imageResizer.imageResizer;

/**
 * @Class Name : RegstCrCstmrController
 * @Description : 법정관리고객 등록
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2023.02.24 김동혁 최초생성
 * @
 * @author : 김동혁
 * @Create Date : 2023. 02. 24.
 */
@Controller
public class RegstCrCstmrController extends BaseController {

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Autowired
	private FileUpload2Service  fileUp2Service;

    @Autowired
    private OrgCommonService orgCommonService;

    /** courtLcMgmtService */
    @Autowired
    private RegstCrCstmrService regstCrCstmrService;

    /** propertiesService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    private MenuAuthService menuAuthService;

    @Autowired
    private FileDownService fileDownService;

    @Autowired
    private LoginService loginService;

    /** Constructor */
    public RegstCrCstmrController() {
        setLogPrefix("[RegstCrCstmrController] ");
    }

	private void FileDelete(File file){
		synchronized(this){
			file.delete();
		}
	}

    @RequestMapping(value = "/rcp/courtMgmt/regstCrCstmr.do")
    public ModelAndView RegstCrCstmr(@ModelAttribute("searchVO") RegstCrCstmrVO searchVO,
                                     ModelMap model,
                                     HttpServletRequest pRequest,
                                     HttpServletResponse pResponse,
                                     @RequestParam Map<String, Object> pRequestParamMap
    )
    {
        ModelAndView modelAndView = new ModelAndView();

        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(searchVO);
            String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

            // 본사 화면인 경우
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

            model.addAttribute("maskingYn", maskingYn);

            //----------------------------------------------------------------
            // jsp 지정
            //----------------------------------------------------------------
            modelAndView.setViewName("/rcp/courtMgmt/msp_court_1003");

            return modelAndView;
        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }

    @RequestMapping(value="/rcp/courtMgmt/chkCrCstmrRrn.json")
    public String chkCrCstmrRrn(@ModelAttribute("searchVO") RegstCrCstmrVO searchVO,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam Map<String, Object> pRequestParamMap,
                                      ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("법정관리고객 등록 식별번호 조회 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        int chkCstmrRrn = 0;

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            chkCstmrRrn = regstCrCstmrService.chkCrCstmrRrn(pRequestParamMap);

            resultMap.put("chkCstmrRrn", chkCstmrRrn);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    @RequestMapping("/rcp/courtMgmt/regstCrCstmr.json")
    public String regstCrCstmr(RegstCrCstmrVO regstCrCstmrVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("법정관리고객 등록 START."));
        logger.info(generateLogMsg("================================================================="));


        Map<String, Object> resultMap = new HashMap<String, Object>();
        int crSeq = 0;

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(regstCrCstmrVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            crSeq = regstCrCstmrService.regstCrCstmr(regstCrCstmrVo);

            resultMap.put("crSeq", crSeq);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping("/rcp/courtMgmt/insertLcInfo.json")
    public String insertLcInfo(RegstCrCstmrVO regstCrCstmrVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

    	logger.info(generateLogMsg("================================================================="));
    	logger.info(generateLogMsg("부채증명서 기준 선등록 START."));
    	logger.info(generateLogMsg("================================================================="));


    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	String chkIsLc = "";

    	try {
    		// Login check
    		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    		loginInfo.putSessionToVo(regstCrCstmrVo);
    		loginInfo.putSessionToParameterMap(pRequestParamMap);

    		if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}

    		chkIsLc = regstCrCstmrService.insertLcInfo(regstCrCstmrVo);

            resultMap.put("chkIsLc", chkIsLc);
    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
    		resultMap.put("msg", "");

    	} catch (Exception e) {
    		resultMap.clear();
    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
    		resultMap.put("msg", "");
    		throw new MvnoErrorException(e);
    	}

    	model.addAttribute("result", resultMap);

    	return "jsonView";
    }

    @RequestMapping("/rcp/courtMgmt/modifyCrCstmr.json")
    public String modifyCrCstmr(RegstCrCstmrVO regstCrCstmrVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("법정관리고객 수정 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(regstCrCstmrVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			if(!"Y".equals(maskingYn) && !"1".equals(maskingYn)) {
	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	            resultMap.put("msg", "수정권한이 없습니다.");
			} else {
				regstCrCstmrService.modifyCrCstmr(regstCrCstmrVo);

	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	            resultMap.put("msg", "");
			}

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping(value="/rcp/courtMgmt/getCrCstmr.json")
    public String getCrCstmr(@ModelAttribute("searchVO") RegstCrCstmrVO searchVO,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam Map<String, Object> pRequestParamMap,
                                      ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("법정관리고객 조회 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<?> resultList = regstCrCstmrService.getCrCstmr(pRequestParamMap);

            resultMap.put("data", resultList.get(0));
	        resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	        resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.clear();
            if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
            {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    @RequestMapping("/rcp/courtMgmt/insertCrBond.json")
    public String insertCrBond(RegstCrCstmrVO regstCrCstmrVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("채권내역 등록 START."));
        logger.info(generateLogMsg("================================================================="));


        Map<String, Object> resultMap = new HashMap<String, Object>();
        int chkBondNum = 0;

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(regstCrCstmrVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 합계 : 미납요금+단말할부금
            try{
            	regstCrCstmrVo.setSumPrc(Integer.toString(Integer.parseInt(regstCrCstmrVo.getUnpdPrc())+Integer.parseInt(regstCrCstmrVo.getInstPrc())));
            }
            catch (NumberFormatException ex){
            	// 20250523 취약점 소스 코드 수정
				logger.error("Connection Exception occurred");
            }

            regstCrCstmrVo.setMobileNo(regstCrCstmrVo.getTelFn()+ regstCrCstmrVo.getTelMn()+ regstCrCstmrVo.getTelRn());

            chkBondNum = regstCrCstmrService.insertCrBond(regstCrCstmrVo);

            if(chkBondNum > 0) {
                resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
                resultMap.put("msg", "이미 존재하는 채권번호입니다.");
            } else {
	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	            resultMap.put("msg", "");
            }

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping("/rcp/courtMgmt/modifyCrBond.json")
    public String modifyCrBond(RegstCrCstmrVO regstCrCstmrVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("채권내역 수정 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        int chkBondNum = 0;

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(regstCrCstmrVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			if(!"Y".equals(maskingYn) && !"1".equals(maskingYn)) {
	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	            resultMap.put("msg", "수정권한이 없습니다.");
			} else {
	            try{
	            	regstCrCstmrVo.setSumPrc(Integer.toString(Integer.parseInt(regstCrCstmrVo.getUnpdPrc())+Integer.parseInt(regstCrCstmrVo.getInstPrc())));
	            }
	            catch (NumberFormatException ex){
	            	// 20250523 취약점 소스 코드 수정
					logger.error("Connection Exception occurred");
	            }
	            regstCrCstmrVo.setMobileNo(regstCrCstmrVo.getTelFn()+ regstCrCstmrVo.getTelMn()+ regstCrCstmrVo.getTelRn());

				chkBondNum = regstCrCstmrService.modifyCrBond(regstCrCstmrVo);

	            if(chkBondNum > 0) {
	                resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	                resultMap.put("msg", "이미 존재하는 채권번호입니다.");
	            } else {
		            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		            resultMap.put("msg", "");
	            }
			}

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping(value="/rcp/courtMgmt/deleteCrBond.json")
    public String deleteCrBond(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , @ModelAttribute RegstCrCstmrVO regstCrCstmrVo
            , ModelMap modelMap
            , @RequestParam Map<String, Object> paramMap) {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("채권내역 삭제 START."));
        logger.info(generateLogMsg("================================================================="));


        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(regstCrCstmrVo);
            loginInfo.putSessionToParameterMap(paramMap);

            //본사화면일경우
            if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            regstCrCstmrService.deleteCrBond(regstCrCstmrVo);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
            resultMap.put("msg", "재전송에 실패 했습니다.");
            throw new MvnoErrorException(e);
        }

        modelMap.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping(value="/rcp/courtMgmt/getCrBondList.json")
    public String getCrBondList(@ModelAttribute("searchVO") RegstCrCstmrVO searchVO,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam Map<String, Object> pRequestParamMap,
                                  ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("채권내역 목록 조회 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<?> resultList = regstCrCstmrService.getCrBondList(pRequestParamMap);

            resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
            {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";

    }

    @RequestMapping("/rcp/courtMgmt/insertCrRciv.json")
    public String insertCrRciv(RegstCrCstmrVO regstCrCstmrVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("접수이력 등록 START."));
        logger.info(generateLogMsg("================================================================="));


        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(regstCrCstmrVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            regstCrCstmrService.insertCrRciv(regstCrCstmrVo);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping("/rcp/courtMgmt/modifyCrRciv.json")
    public String modifyCrRciv(RegstCrCstmrVO regstCrCstmrVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("접수이력 수정 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(regstCrCstmrVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			if(!"Y".equals(maskingYn) && !"1".equals(maskingYn)) {
	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	            resultMap.put("msg", "수정권한이 없습니다.");
			} else {
				regstCrCstmrService.modifyCrRciv(regstCrCstmrVo);

	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	            resultMap.put("msg", "");
			}

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping(value="/rcp/courtMgmt/deleteCrRciv.json")
    public String deleteCrRciv(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , @ModelAttribute RegstCrCstmrVO regstCrCstmrVo
            , ModelMap modelMap
            , @RequestParam Map<String, Object> paramMap) {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("접수이력 삭제 START."));
        logger.info(generateLogMsg("================================================================="));


        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(regstCrCstmrVo);
            loginInfo.putSessionToParameterMap(paramMap);

            //본사화면일경우
            if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            regstCrCstmrService.deleteCrRciv(regstCrCstmrVo);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
            resultMap.put("msg", "재전송에 실패 했습니다.");
            throw new MvnoErrorException(e);
        }

        modelMap.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping(value="/rcp/courtMgmt/getCrRcivList.json")
    public String getCrRcivList(@ModelAttribute("searchVO") RegstCrCstmrVO searchVO,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam Map<String, Object> pRequestParamMap,
                                  ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("접수이력 목록 조회 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<?> resultList = regstCrCstmrService.getCrRcivList(pRequestParamMap);

            resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
            {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    @SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/rcp/courtMgmt/fileUpLoad.do")
	public String fileUpUsingService(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

		logger.info("===================================================================");
		logger.info("====================  RegstCrCstmrController  =====================");
		logger.info("===================================================================");
		logger.info(">>>>model.toString() : " + model);
		logger.info(">>>>pRequest.toString() : " + pRequest);
		logger.info(">>>>pResponse.toString() : " + pResponse);
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap);
		logger.info("===================================================================");


		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInfoVO fileInfoVO = new FileInfoVO();

		HttpSession session = pRequest.getSession();

		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
        loginInfo.putSessionToVo(fileInfoVO);

        fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
        fileInfoVO.setRvisnId(loginInfo.getUserId());

		String strUploadFileNm = "";      // 업로드한 파일 풀 경로
		String strSaveFileNm = "";        // 저장될 파일명(중복이 발생하면 (2)를 붙이는 작업까지 완료)
		String strExt = "";               // 확장자
		String strSaveFullPath = "";      // 저장될 파일 경로
		String strSaveDir = propertyService.getString("courtRsvPath");
		Integer filesize = 0;
		File chkDir = new File(strSaveDir);

		try{
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);

	  	    //--------------------------------
	        // upload base directory를 구함
	  	    // 존재하지 않으면 directory 생성
	        //--------------------------------
			if ( !chkDir.exists()) {
				chkDir.mkdirs();
			}

			for (FileItem item : items) {
				//--------------------------------
		        // 파일 타입이 아니면 skip
		        //--------------------------------
				if (item.isFormField())
					continue;

				//--------------------------------
		        // 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
		        //--------------------------------
				if ( item.getSize() == 0 ||  item.getSize()  >  Integer.parseInt(propertyService.getString("fileUploadSizeLimit")) ) {
					continue;
				}

				try {
					//----------------------------------------------------------------
			    	// 파일 이름관련 변수 선언 및 초기화
			    	//----------------------------------------------------------------
					strUploadFileNm = "";
					strSaveFileNm = "";
					strExt = "";
					strSaveFullPath = "";
					filesize = 0;

					//--------------------------------
			        // 모듈별 directory가 존재하지 않으면 생성
			        //--------------------------------
					//오늘날짜구하기
					Calendar c = Calendar.getInstance();
					//v2018.11 PMD 적용 소스 수정
					//String ntime = new String();
					String ntime = "";
					String yyyy = String.valueOf(c.get(Calendar.YEAR));

					int mm = Integer.parseInt(String.valueOf(c.get(Calendar.MONTH)+1));
					int dd = Integer.parseInt(String.valueOf(c.get(Calendar.DATE)));
					ntime = yyyy + "/" + String.format("%02d", mm) + "/" + String.format("%02d", dd);
					//v2018.11 PMD 적용 소스 수정
					//strSaveDir = strSaveDir + ntime + "/";
					StringBuilder strBld = new StringBuilder(strSaveDir);
		            strBld.append(ntime);
		            strBld.append("/");
		            strSaveDir = strBld.toString();

					File lDir = new File(strSaveDir );
					if ( !lDir.exists()) {
						lDir.mkdirs();
					}
					fileInfoVO.setAttRout(strSaveDir);

					//----------------------------------------------------------------
			    	// upload 이름 구함
			    	//----------------------------------------------------------------
					String fieldname = item.getFieldName();
					if ( fieldname.equals("file")) {
						//----------------------------------------------------------------
				    	// 파일 이름에 붙일 4자리 시퀀스 조회
				    	//----------------------------------------------------------------
						String fileNmSeq = regstCrCstmrService.getFileNmSeq();

						//----------------------------------------------------------------
				    	// 파일 이름 구함
				    	//----------------------------------------------------------------
						strUploadFileNm = FilenameUtils.getName(item.getName());

						int pos = strUploadFileNm.lastIndexOf(".");
						strExt = strUploadFileNm.substring( pos + 1); //확장자
						strSaveFileNm = "KIS" + fileNmSeq + "." + strExt; //파일명구하기 ex)KIS201607140001.jpg
						strSaveFileNm = fileUp2Service.getAlternativeFileName(strSaveDir, strSaveFileNm);
						strSaveFullPath = strSaveDir + strSaveFileNm;

						logger.info(generateLogMsg("strUploadFileNm : " + strUploadFileNm));
				    	logger.info(generateLogMsg("strSaveFileNm : " + strSaveFileNm));
				    	logger.info(generateLogMsg("strExt : " + strExt));
				    	logger.info(generateLogMsg("strSaveFullPath : " + strSaveFullPath));

				    	session.setAttribute("realFilePath", strSaveDir);  //파일경로
				    	session.setAttribute("realFileNm", strSaveFileNm); //파일명
				    	session.setAttribute("ext", strExt);               //파일확장자
						//----------------------------------------------------------------
				    	// 파일 write
				    	//----------------------------------------------------------------
				    	InputStream filecontent = item.getInputStream();
						File f=new File(strSaveFullPath);

						OutputStream fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;
						try {
							while((len=filecontent.read(buf))>0) {
								fout.write(buf,0,len);
								filesize+=len;
							}
						} catch (Exception e1) {
							throw new MvnoErrorException(e1);
						} finally {
					        if (fout != null) {
					        	try {
					        		fout.close();
					        	} catch (Exception e) {
					        		throw new MvnoErrorException(e);
					        	}
					        }
							if (filecontent != null) {
								try {
									filecontent.close();
								} catch (Exception e) {

								    throw new MvnoErrorException(e);
								}
							}
						}
						/**
						 * ********************************************************
						 * 파일 암호화 로직.
						 */
						CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
						String strTempFileMN = strSaveDir + strSaveFileNm + ".ENC";

						fileHandle.Encrypt(strSaveFullPath, strTempFileMN);
						File dirFile = new File(strSaveFullPath);
						if(dirFile.exists()) {
							//dirFile.delete();
							FileDelete(dirFile);
						}
						File targetFile = new File(strTempFileMN);
						if(targetFile.exists()) {
							targetFile.renameTo(dirFile);
						}
						/**
						 * ********************************************************
						 */
					}

				}catch (Exception e) {
					throw new MvnoErrorException(e);
				}

		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
    	} catch (Exception e) {
    		resultMap.put("state", false);
    		resultMap.put("name", strUploadFileNm.replace("'","\\'"));
    		resultMap.put("size",  111);

    		model.addAttribute("result", resultMap);

			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", ""))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}
    	    return "jsonViewArray";
    	}

		//파일 테이블에 데이터 저장하고.
		fileInfoVO.setFileId(generationUUID().replaceAll("-",""));
		fileInfoVO.setFileNm(strSaveFileNm);
		fileInfoVO.setFileSeq("9999999999");//초기값 조직ID
		fileInfoVO.setAttSctn("RCP");

		//파일 테이블에 등록
		//orgCommonService.insertFile(fileInfoVO);
		//regstCrCstmrService.updateRcivFile(pRequestParamMap);

		session.setAttribute("fileId", fileInfoVO.getFileId()); //파일ID

		resultMap.put("state", true);
		resultMap.put("name", fileInfoVO.getFileId());
		resultMap.put("size", "" + 111);

		model.addAttribute("result", resultMap);
	    return "jsonViewArray";
    }

	public static String generationUUID(){
		  return UUID.randomUUID().toString();
	}

	@RequestMapping("/rcp/courtMgmt/getFileUploadChk.json")
	public String getFileUploadChk(HttpServletRequest request, HttpServletResponse response, RegstCrCstmrVO searchVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			logger.info("=========================================================");
			logger.info("파일 업로드 체크 시작.");
			logger.info("=========================================================");
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			HttpSession session = request.getSession();
			String realFilePath = "";
	    	String realFileNm = "";
	    	String ext = "";
	    	if (session.getAttribute("realFilePath") != null && !"".equals(session.getAttribute("realFilePath"))) {
	    		realFilePath = (String) session.getAttribute("realFilePath"); //파일경로
	    	} else {
    			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "파일을 첨부해주세요.");
				model.addAttribute("result", resultMap);
	    		return "jsonView";
	    	}
	    	if (session.getAttribute("realFileNm") != null) {
	    		realFileNm = (String) session.getAttribute("realFileNm"); //파일명
	    	}
	    	if (session.getAttribute("ext") != null) {
	    		ext = (String) session.getAttribute("ext"); //파일확장자
	    		if(ext != null) {
	    			ext = ext.toUpperCase();
	    		}
	    	}
	    	boolean bExtChk = false;
	    	String strEvidenceExtErrorMsg = "";
	    	List<String> listEvidenceFileExts = regstCrCstmrService.getEvidenceFileExtList();
	    	for(int idx = 0; idx < listEvidenceFileExts.size(); idx++) {
	    		String strTempExt = listEvidenceFileExts.get(idx);

	    		if("".equals(strEvidenceExtErrorMsg)) {
	    			strEvidenceExtErrorMsg = strTempExt;
	    		} else {
	    		   //v2018.11 PMD 적용 소스 수정
	    		   //strEvidenceExtErrorMsg = strEvidenceExtErrorMsg + ", " + strTempExt;
	    		   StringBuilder strBld = new StringBuilder(strEvidenceExtErrorMsg);
	    		   strBld.append(", ");
	    		   strBld.append(strTempExt);
	    		   strEvidenceExtErrorMsg = strBld.toString();
	    		}

	    		if(ext.equalsIgnoreCase(strTempExt)) {
	    			bExtChk = true;
	    		}
	    	}
	    	//v2018.11 PMD 적용 소스 수정
	    	//strEvidenceExtErrorMsg = strEvidenceExtErrorMsg + " 확장만 허용합니다.";
	    	StringBuilder strBld = new StringBuilder(strEvidenceExtErrorMsg);
	    	strBld.append(" 확장만 허용합니다.");
	    	strEvidenceExtErrorMsg = strBld.toString();

	    	if(!bExtChk) {
    			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", strEvidenceExtErrorMsg);
				model.addAttribute("result", resultMap);
	    		return "jsonView";
	    	}

	    	File file = new File(realFilePath + realFileNm);
	    	if(file.exists()) {
	    		long lFileSize = file.length();
		    	long lFileGSize = 1048576;
	    		if("N".equals(regstCrCstmrService.getEvidenceFileSizeTargetChk(ext))) {
			    	if(lFileSize >= lFileGSize) {
			    		String strTempSizeMsg = "1MByte";
			    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
						resultMap.put("msg", strTempSizeMsg + "이하 용량의 파일만 허용합니다.");
						model.addAttribute("result", resultMap);
			    		return "jsonView";
			    	}
	    		} else {
	    			// 복호화
	    			CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
                    //1.TO-BE 암호화 여부 확인
                    if (rosis.crypt.module.eSonicCrypt.isEncryptLea(realFilePath + realFileNm)) {
                        //1.1 TO-BE 암호화 모듈로 복호화
                        int decInt = rosis.crypt.module.eSonicCrypt.RS_DecryptLea(realFilePath + realFileNm, realFilePath + realFileNm + ".DEC", 24, 0);
                    } else {
                        //2. AS-IS 암호화 모듈로 복호화
                        fileHandle.Decrypt(realFilePath + realFileNm, realFilePath + realFileNm + ".DEC");
                    }
		        	File del1File = new File(realFilePath + realFileNm);
					if(del1File.exists()) {
						//del1File.delete();
						FileDelete(del1File);
					}
					File cFile = new File(realFilePath + realFileNm + ".DEC");
					if(cFile.exists()) {
						cFile.renameTo(new File(realFilePath + realFileNm));
					}
					File dirFile = new File(realFilePath + realFileNm + ".DEC");
					if(dirFile.exists()) {
						//dirFile.delete();
						FileDelete(dirFile);
					}

	    			// 리사이징
	    			imageResizer imageresizer = new imageResizer();
	    			String strResizeMsg = imageresizer.imgResizer(lFileGSize
	    					, 1024
	    					, 1024
	    					, regstCrCstmrService.getEvidenceFileResizeExtList("Y")
	    					, 512
	    					, 512
	    					, realFilePath
	    					, realFileNm
	    					, realFilePath
	    					, realFileNm + ".RESIZE");
	    			logger.info("이미지 리사이징 결과 : " + strResizeMsg);
	    			Matcher mReq = Pattern.compile("true").matcher(strResizeMsg);
	    			// 암호화 작업
					if(mReq.find()) {
						logger.info("리사이징 이미지로 암호화");
						fileHandle.Encrypt(realFilePath + realFileNm + ".RESIZE", realFilePath + realFileNm + ".ENC");
					} else {
						logger.info("용량을 초과하지 않아서 원본으로 암호화");
						fileHandle.Encrypt(realFilePath + realFileNm, realFilePath + realFileNm + ".ENC");
					}
					File del2File = new File(realFilePath + realFileNm);
					if(del2File.exists()) {
						//del2File.delete();
						FileDelete(del2File);
					}

					File targetFile = new File(realFilePath + realFileNm + ".ENC");
					if(targetFile.exists()) {
						targetFile.renameTo(new File(realFilePath + realFileNm));
					}
					File dFile = new File(realFilePath + realFileNm + ".ENC");
					if(dFile.exists()) {
						//dFile.delete();
						FileDelete(dFile);
					}
					File rFile = new File(realFilePath + realFileNm + ".RESIZE");
					if(rFile.exists()) {
						//rFile.delete();
						FileDelete(rFile);
					}
					/**
					 * ********************************************************
					 */
//						String strTempFileMN = realFilePath + realFileNm + ".ENC";
//
//						fileHandle.Encrypt(realFilePath + realFileNm, strTempFileMN);
//						File eFile = new File(realFilePath + realFileNm);
//						if(eFile.exists()) {
//							eFile.delete();
//						}
//						File etFile = new File(realFilePath + realFileNm + ".ENC");
//						if(etFile.exists()) {
//							etFile.renameTo(new File(realFilePath + realFileNm));
//						}
	    		}
	    	} else {
	    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "존재하지 않는 데이터 입니다");
				model.addAttribute("result", resultMap);
	    		return "jsonView";
	    	}


			resultMap.put("fileTotalCnt", 1);
			logger.info("request.getSession().getAttribute : " + request.getSession().getAttribute("realFileNm"));
			if(request.getSession().getAttribute("realFileNm") != null && !"".equals(request.getSession().getAttribute("realFileNm"))) {
				resultMap.put("chkFileUpload", "Y");
			} else {
				resultMap.put("chkFileUpload", "N");
			}
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

//			resultMap =  makeResultMultiRow(searchVO, resultList);
		} catch(Exception e) {

			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", ""))
			{
                throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

   @RequestMapping("/rcp/courtMgmt/updateRcivFile.json")
   public String updateRcivFile(HttpServletRequest request, HttpServletResponse response, RegstCrCstmrVO regstCrCstmrVo, Model model){

       logger.info(generateLogMsg("================================================================="));
       logger.info(generateLogMsg(" updateRcivFile 등록 "));
       logger.info(generateLogMsg("================================================================="));
       logger.info(">>>> regstCrCstmrVo : " + regstCrCstmrVo);
       logger.info("===================================================================");

       //--------------------------------------
       // return JSON 변수 선언
       //--------------------------------------
       Map<String, Object> resultMap = new HashMap<String, Object>();

       HttpSession session = request.getSession();

       String realFilePath = "";
       String realFileNm = "";
       String ext = "";
       String fileId = "";

       if (session.getAttribute("realFilePath") != null) {
           realFilePath = (String) session.getAttribute("realFilePath"); //파일경로
           if(!"".equals(realFilePath)) {
        	   regstCrCstmrVo.setRealFileDir(realFilePath);
           }
       }

       if (session.getAttribute("realFileNm") != null) {
           realFileNm = (String) session.getAttribute("realFileNm"); //파일명
           if(!"".equals(realFileNm)) {
        	   regstCrCstmrVo.setRealFileName(realFileNm);
           }
       }

       if (session.getAttribute("fileId") != null) {
    	   fileId = (String) session.getAttribute("fileId"); //파일명
    	   if(!"".equals(fileId)) {
    		   regstCrCstmrVo.setFileId(fileId);
    	   }
       }

       if (session.getAttribute("ext") != null) {
    	   ext = (String) session.getAttribute("ext"); //파일확장자
    	   if(ext != null) {
    		   ext = ext.toUpperCase();
    	   }
       }

       logger.info("############################## ");
       logger.info("파일경로 === " + realFilePath);
       logger.info("파일명 === " + realFileNm);
       logger.info("확장자 === " + ext);
       logger.info("파일ID === " + fileId);

	       if ( realFileNm != null)
	       logger.info("realFileNm.length() ===" + realFileNm.length());

       //----------------------------------------------------------------
       //  Login check
       //----------------------------------------------------------------
       LoginInfo loginInfo = new LoginInfo(request, response);
       loginInfo.putSessionToVo(regstCrCstmrVo);

       try {
    	   int returnCnt = regstCrCstmrService.updateRcivFile(regstCrCstmrVo);
           logger.debug("returnCnt" + returnCnt);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
       } catch (Exception e) {
    	   resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
    	   if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1008003", "법정관리고객 등록 접수이력"))
    	   {
    		   throw new MvnoErrorException(e);
    	   }
       } finally {
           //session 정보 초기화
           session.setAttribute("realFilePath", ""); //파일경로
           session.setAttribute("realFileNm", ""); //파일명
           session.setAttribute("ext", ""); //파일확장자
       }

       //----------------------------------------------------------------
       // return json
       //----------------------------------------------------------------
       model.addAttribute("result", resultMap);

       return "jsonView";
   }

    @RequestMapping("/rcp/courtMgmt/downFileDtl.json")
    public String downFileDtl( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("법정관리고객 접수이력 - 파일다운로드 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
//	    String returnMsg = null;

        try {

            String strFileName = regstCrCstmrService.getFileNmDtl(pReqParamMap.get("fileId").toString());

            /**
			 * ********************************************************
			 * JPG 파일 복호화 로직.
			 */
        	int S = 0;
			if(strFileName.startsWith("\\")) {
				S = strFileName.lastIndexOf("\\");
			} else {
				S = strFileName.lastIndexOf("/");
			}
			int M = strFileName.lastIndexOf(".");
			int E = strFileName.length();

			String filename = strFileName.substring(S+1, M);
			String extname = strFileName.substring(M+1, E);
			String strDecFileName = "";

			StringBuffer sb = new StringBuffer();

			//v2018.11 PMD 적용 소스 수정
			//sb.append(strFileName + "." );
			sb.append(strFileName);
			sb.append(".");
			sb.append(extname);

        	CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
            //1.TO-BE 암호화 여부 확인
            if (rosis.crypt.module.eSonicCrypt.isEncryptLea(strFileName)) {
                //1.1 TO-BE 암호화 모듈로 복호화
                int decInt = rosis.crypt.module.eSonicCrypt.RS_DecryptLea(strFileName, sb.toString(), 24, 0);
            } else {
                //2. AS-IS 암호화 모듈로 복호화
                fileHandle.Decrypt(strFileName, sb.toString());
            }

        	strDecFileName = sb.toString();

			if("".equals(strDecFileName)) {
				strDecFileName = strFileName;
			}
        	/**
        	 * ********************************************************
        	 */

			logger.info(generateLogMsg("filename = " + filename));

            file = new File(strDecFileName);

            response.setContentType("applicaton/download");
            response.setContentLength((int) file.length());

            String encodingFileName = "";

            int excelPathLen2 = Integer.parseInt(propertyService.getString("courtRsvPathLen"));

            try {
              encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8").replace("+", "%20");
            } catch (UnsupportedEncodingException uee) {
              encodingFileName = strFileName;
            }

            response.setHeader("Cache-Control", "");
            response.setHeader("Pragma", "");

            response.setContentType("Content-type:application/octet-stream;");

            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            in = new FileInputStream(file);

            out = response.getOutputStream();

            int temp = -1;
            while((temp = in.read()) != -1){
                out.write(temp);
            }
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
    	    /**
  			 * ********************************************************
  			 * JPG 파일 복호화 로직.
  			 */
        	File delFile = new File(strDecFileName);
        	if(delFile.exists()) {
        		//delFile.delete();
        		FileDelete(delFile);
        	}

    	    /**
    	     * ********************************************************
    	     */
             resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
             resultMap.put("msg", "다운로드성공");

         } catch (Exception e) {
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
         //----------------------------------------------------------------
         // return json
         //----------------------------------------------------------------
         model.addAttribute("result", resultMap);
         return "jsonView";
    }

	@RequestMapping("/rcp/courtMgmt/deleteFileDtl.json")
	public String deleteFile( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("파일 삭제 START."));
		logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToParameterMap(pReqParamMap);

		HttpSession session = request.getSession();

        File file = null;
        String returnMsg = null;
        String realFilePath = null;
        String realFileNm = null;

		try {

            if (session.getAttribute("realFilePath") != null) {
                realFilePath = (String) session.getAttribute("realFilePath"); //파일경로
            }

            if (session.getAttribute("realFileNm") != null) {
                realFileNm = (String) session.getAttribute("realFileNm"); //파일명
            }

            file = new File(realFilePath + realFileNm );

            file.delete();

			int delcnt = regstCrCstmrService.deleteFile(pReqParamMap.get("fileId").toString());
			logger.info(generateLogMsg("삭제건수 = " + delcnt));

 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "파일변경시작");

 		} catch (Exception e) {
 			resultMap.put("fileTotCnt", "0");
			resultMap.put("deleteCnt", "0");

			//session 정보 초기화
            session.setAttribute("realFilePath", ""); //파일경로
            session.setAttribute("realFileNm", ""); //파일명
            session.setAttribute("ext", ""); //파일확장자
            session.setAttribute("fileId", ""); //파일아이디

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					returnMsg, "MSP1008003", "법정관리고객 접수이력"))
			{
            //v2018.11 PMD 적용 소스 수정
				throw new MvnoErrorException(e);
			}
		}
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
	}

    @RequestMapping("/rcp/courtMgmt/deleteFile2.json")
    public String deleteFile2( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일 삭제2 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        File file = null;
        String returnMsg = null;
        String realFilePath = null;
        String realFileNm = null;

        HttpSession session = request.getSession();

        try {

            if (session.getAttribute("realFilePath") != null) {
                realFilePath = (String) session.getAttribute("realFilePath"); //파일경로
            }

            if (session.getAttribute("realFileNm") != null) {
                realFileNm = (String) session.getAttribute("realFileNm"); //파일명
            }

            file = new File(realFilePath + realFileNm );

            file.delete();

             resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
             resultMap.put("msg", "파일삭제성공");

         } catch (Exception e) {
        	 resultMap.put("fileTotCnt", "0");
        	 resultMap.put("deleteCnt", "0");

        	 if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
        			 messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
        			 returnMsg, "", ""))
        	 {
             //v2018.11 PMD 적용 소스 수정
        		 throw new MvnoErrorException(e);
        	 }
        } finally {
            //session 정보 초기화
            session.setAttribute("realFilePath", ""); //파일경로
            session.setAttribute("realFileNm", ""); //파일명
            session.setAttribute("ext", ""); //파일확장자
            session.setAttribute("fileId", ""); //파일아이디
        }
         //----------------------------------------------------------------
         // return json
         //----------------------------------------------------------------
         model.addAttribute("result", resultMap);
         return "jsonView";
    }

	@RequestMapping("/rcp/courtMgmt/manageVac.json")
	public String manageVac(@ModelAttribute("regstCrCstmrVo") RegstCrCstmrVO regstCrCstmrVo, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(regstCrCstmrVo);

            // 본사 권한
            if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

			regstCrCstmrService.manageVac(regstCrCstmrVo);

			List<?> resultList = regstCrCstmrService.getCrInoutTotal(pRequestParamMap);

            if(resultList != null && !resultList.isEmpty()) {
            	resultMap.put("data", resultList.get(0));
            }

    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
    		resultMap.put("msg", "");

    	} catch (Exception e) {
    		resultMap.clear();
    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
    		resultMap.put("msg", "");
    		throw new MvnoErrorException(e);
    	}
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

    @RequestMapping("/rcp/courtMgmt/updateDclrDate.json")
    public String updateDclrDate(RegstCrCstmrVO regstCrCstmrVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("가상계좌 신고일자 UPDATE START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String dclrDate = "";

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(regstCrCstmrVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			dclrDate = regstCrCstmrService.updateDclrDate(regstCrCstmrVo);

			resultMap.put("dclrYear", dclrDate.substring(0, 4));
			resultMap.put("dclrMonth", dclrDate.substring(4, 6));
			resultMap.put("dclrDay", dclrDate.substring(6));
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping("/rcp/courtMgmt/chkMaskingAcct.json")
    public String chkMaskingAcct(RegstCrCstmrVO regstCrCstmrVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("계좌번호 신고서 마스킹권한 체크 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(regstCrCstmrVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			if(!"Y".equals(maskingYn) && !"1".equals(maskingYn)) {
	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	            resultMap.put("msg", "권한이 없어 계좌번호 신고서를 출력할 수 없습니다.");
			} else {
	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	            resultMap.put("msg", "");
			}

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    @RequestMapping("/rcp/courtMgmt/insertCrInout.json")
    public String insertCrInout(RegstCrCstmrVO regstCrCstmrVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

    	logger.info(generateLogMsg("================================================================="));
    	logger.info(generateLogMsg("입출금정보 등록 START."));
    	logger.info(generateLogMsg("================================================================="));


    	Map<String, Object> resultMap = new HashMap<String, Object>();

    	try {
    		// Login check
    		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    		loginInfo.putSessionToVo(regstCrCstmrVo);
    		loginInfo.putSessionToParameterMap(pRequestParamMap);

    		if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}

    		regstCrCstmrService.insertCrInout(regstCrCstmrVo);

            List<?> resultList = regstCrCstmrService.getCrInoutTotal(pRequestParamMap);

            if(resultList != null && !resultList.isEmpty()) {
            	resultMap.put("data", resultList.get(0));
            }

    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
    		resultMap.put("msg", "");

    	} catch (Exception e) {
    		resultMap.clear();
    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
    		resultMap.put("msg", "");
    		throw new MvnoErrorException(e);
    	}

    	model.addAttribute("result", resultMap);

    	return "jsonView";
    }

    @RequestMapping("/rcp/courtMgmt/modifyCrInout.json")
    public String modifyCrInout(RegstCrCstmrVO regstCrCstmrVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model){

    	logger.info(generateLogMsg("================================================================="));
    	logger.info(generateLogMsg("입출금정보 수정 START."));
    	logger.info(generateLogMsg("================================================================="));

    	Map<String, Object> resultMap = new HashMap<String, Object>();

    	try {
    		// Login check
    		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    		loginInfo.putSessionToVo(regstCrCstmrVo);
    		loginInfo.putSessionToParameterMap(pRequestParamMap);

    		if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			if(!"Y".equals(maskingYn) && !"1".equals(maskingYn)) {
	            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
	            resultMap.put("msg", "수정권한이 없습니다.");
			} else {
	    		regstCrCstmrService.modifyCrInout(regstCrCstmrVo);

	            List<?> resultList = regstCrCstmrService.getCrInoutTotal(pRequestParamMap);

	            if(resultList != null && !resultList.isEmpty()) {
	            	resultMap.put("data", resultList.get(0));
	            }

	    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    		resultMap.put("msg", "");
			}

    	} catch (Exception e) {
    		resultMap.clear();
    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
    		resultMap.put("msg", "");
    		throw new MvnoErrorException(e);
    	}

    	model.addAttribute("result", resultMap);

    	return "jsonView";
    }

    @RequestMapping(value="/rcp/courtMgmt/deleteCrInout.json")
    public String deleteCrInout(HttpServletRequest pRequest
    		, HttpServletResponse pResponse
    		, @ModelAttribute RegstCrCstmrVO regstCrCstmrVo
    		, ModelMap modelMap
    		, @RequestParam Map<String, Object> pRequestParamMap) {

    	logger.info(generateLogMsg("================================================================="));
    	logger.info(generateLogMsg("입출금정보 삭제 START."));
    	logger.info(generateLogMsg("================================================================="));


    	Map<String, Object> resultMap = new HashMap<String, Object>();

    	try {

    		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    		loginInfo.putSessionToVo(regstCrCstmrVo);
    		loginInfo.putSessionToParameterMap(pRequestParamMap);

    		//본사화면일경우
    		if(!"10".equals(regstCrCstmrVo.getSessionUserOrgnTypeCd())){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}

    		regstCrCstmrService.deleteCrInout(regstCrCstmrVo);

            List<?> resultList = regstCrCstmrService.getCrInoutTotal(pRequestParamMap);

            if(resultList != null && !resultList.isEmpty()) {
            	resultMap.put("data", resultList.get(0));
            }

    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
    		resultMap.put("msg", "");

    	} catch (Exception e) {
    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
    		resultMap.put("msg", "재전송에 실패 했습니다.");
    		throw new MvnoErrorException(e);
    	}

    	modelMap.addAttribute("result", resultMap);

    	return "jsonView";
    }

    @RequestMapping(value="/rcp/courtMgmt/getCrInoutList.json")
    public String getCrInoutList(@ModelAttribute("searchVO") RegstCrCstmrVO searchVO,
    		HttpServletRequest request,
    		HttpServletResponse response,
    		@RequestParam Map<String, Object> pRequestParamMap,
    		ModelMap model)
    {

    	logger.info(generateLogMsg("================================================================="));
    	logger.info(generateLogMsg("입출금정보 목록 조회 START."));
    	logger.info(generateLogMsg("================================================================="));

    	Map<String, Object> resultMap = new HashMap<String, Object>();

    	try {
    		LoginInfo loginInfo = new LoginInfo(request, response);
    		loginInfo.putSessionToParameterMap(pRequestParamMap);
    		loginInfo.putSessionToVo(searchVO);

    		// 본사 권한
    		if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}

    		List<?> resultList = regstCrCstmrService.getCrInoutList(pRequestParamMap);

    		resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

    	} catch (Exception e) {
    		resultMap.clear();
    		if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
    		{
    			throw new MvnoErrorException(e);
    		}
    	}

    	model.addAttribute("result", resultMap);
    	return "jsonView";
    }

    @RequestMapping(value="/rcp/courtMgmt/getCrInoutTotal.json")
    public String getCrInoutTotal(@ModelAttribute("searchVO") RegstCrCstmrVO searchVO,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam Map<String, Object> pRequestParamMap,
                                      ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("입출금정보 합계 조회 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<?> resultList = regstCrCstmrService.getCrInoutTotal(pRequestParamMap);

            if(resultList != null && !resultList.isEmpty()) {
            	resultMap.put("data", resultList.get(0));
            }

	        resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	        resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.clear();
            if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
            {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }
}