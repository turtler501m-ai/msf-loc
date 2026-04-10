package com.ktis.msp.rcp.courtMgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.courtMgmt.service.CourtRcvCstmrService;
import com.ktis.msp.rcp.courtMgmt.vo.CourtRcvCstmrVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : CourtRcvCstmrController
 * @Description : 법정관리고객 목록
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2023.02.24 김동혁 최초생성
 * @
 * @author : 김동혁
 * @Create Date : 2023. 02. 24.
 */
@Controller
public class CourtRcvCstmrController extends BaseController {

    @Autowired
    private OrgCommonService orgCommonService;

    /** courtLcMgmtService */
    @Autowired
    private CourtRcvCstmrService courtRcvCstmrService;

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
    public CourtRcvCstmrController() {
        setLogPrefix("[CourtRcvCstmrController] ");
    }

    @RequestMapping(value = "/rcp/courtMgmt/courtRcvCstmr.do")
    public ModelAndView CourtRcvCstmr(@ModelAttribute("searchVO") CourtRcvCstmrVO searchVO,
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

            model.addAttribute("strtDt",orgCommonService.getWantDay(-7));
            model.addAttribute("endDt",orgCommonService.getToDay());
            model.addAttribute("maskingYn", maskingYn);

            //----------------------------------------------------------------
            // jsp 지정
            //----------------------------------------------------------------
            modelAndView.setViewName("/rcp/courtMgmt/msp_court_1002");

            return modelAndView;
        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }
    
    @RequestMapping(value="/rcp/courtMgmt/getCrCstmrList.json")
    public String getCrCstmrList(@ModelAttribute("searchVO") CourtRcvCstmrVO searchVO,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam Map<String, Object> pRequestParamMap,
                                      ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("법정관리고객 목록 조회 START."));
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

            List<?> resultList = courtRcvCstmrService.getCrCstmrList(pRequestParamMap);

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
    
    @RequestMapping("/rcp/courtMgmt/getCrCstmrListByExcel.json")
    public String getCrCstmrListByExcel(@ModelAttribute("searchVO") CourtRcvCstmrVO searchVO,
                                             HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestParam Map<String, Object> pRequestParamMap,
                                             ModelMap model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("법정관리고객 목록 엑셀 다운 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String returnMsg = null;
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;

        try {
            /* 로그인조직정보 및 권한체크 */
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            List<?> resultList = courtRcvCstmrService.getCrCstmrListByExcel(pRequestParamMap);

            String serverInfo = propertiesService.getString("excelPath");
            String strFilename = serverInfo  + "법정관리고객목록_";//파일명
            String strSheetname = "법정관리고객목록";//시트명

            String [] strHead = {
            		"최초생성일","최종변경일","최종입금일","이름","식별번호","법정관리유형","관할법원","판결번호","재판부","가상계좌은행","가상계좌","계좌신고일","미납총액(원)","변제예정액(원)","입금총액(원)","진행상태","종결여부","변제개시일","변제율","변제기간","최초생성자","수정자","수정일시"};

            String [] strValue = {
            		"firstDt","lastDt","lastInoutDt","cstmrName","cstmrRrn","crTp","crComp","noJudg","judgPan","vacBankCd","vacId","acRegDt","totalPric","rbEsti","inoutTotal","tpJinh","endYn","rbStrtDt","rbRate","rbPrid","regstId","rvisnId","rvisnDttm"};

            //엑셀 컬럼 사이즈
            int[] intWidth = {
                    4500, 4500, 4500, 2500, 5000,
                    5000, 5000, 5000, 4500, 5000,
                    5500, 4500, 4500, 4500, 4500,
                    5000, 2500, 4500, 3500, 3500,
                    4000, 2500, 5000
            };
            int[] intLen = {
                    0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0, 1, 1, 1,
                    0, 0, 0, 0, 0,
                    0, 0, 0
            };

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
			file = new File(strFileName);
			
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = response.getOutputStream();
			
			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
				
				pRequestParamMap.put("FILE_NM"	,file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());				//파일경로
				pRequestParamMap.put("DUTY_NM"	,"RCP");						//업무명
				pRequestParamMap.put("IP_INFO"	,ipAddr);						//IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());			//파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId"));	//메뉴ID
				pRequestParamMap.put("DATA_CNT", resultList.size());			//자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId());	//사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
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
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
}