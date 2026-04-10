package com.ktis.msp.org.unpdmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.unpdmgmt.service.UnpdMgmtService;
import com.ktis.msp.org.unpdmgmt.vo.UnpdMgmtVo;

import egovframework.rte.fdl.property.EgovPropertyService;



/**
 * @Class Name : UnpdMgmtController
 * @Description : 미납요금 관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.07.21 김연우 최초생성
 * @
 * @author : 김연우
 * @Create Date : 2015. 7. 21.
 */
@Controller
public class UnpdMgmtController extends BaseController {

	@Autowired
	private UnpdMgmtService unpdMgmtService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private OrgCommonService  orgCommonService;
	
	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	public UnpdMgmtController() {
		setLogPrefix("[UnpdMgmtController] ");
	}	

	/**
	 * @Description : 미납요금 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 김연우
	 * @Create Date : 2015. 7. 21.
	 */
	@RequestMapping(value = "/org/unpdmgmt/msp_org_unpd.do", method={POST, GET})
	public ModelAndView UnpdMgmtGrid(@ModelAttribute("searchVO") UnpdMgmtVo searchVO,
									HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("미납요금 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			model.addAttribute("currentMonth",orgCommonService.getLastMonth());
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
		
			modelAndView.setViewName("/org/rqstmgmt/msp_org_bs_1050");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}

	/**
	 * @Description : 미납요금 리스트 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 * @Author : 김연우
	 * @Create Date : 2015. 7. 21.
	 */
	@RequestMapping("/org/unpdmgmt/getUnpdMgmtList.json")
	public String selectUnpdMgmtList(HttpServletRequest request
								, HttpServletResponse response
								, @ModelAttribute("searchVO") UnpdMgmtVo searchVO
								, ModelMap model
								, @RequestParam Map<String, Object> pRequestParamMap){
	
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = unpdMgmtService.getUnpdMgmtList(searchVO, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
	
		return "jsonView"; 
	}

	/**
	 * @Description : 미납요금 내역 엑셀다운로드기능 (나중에 UI 확정되어서 나오면 작업하도록 함. 현재는 필드랑 맞지 않음)
	 * @Param  : unpdMgmtVo
	 * @Return : String
	 * @Author : 김연우
	 * @Create Date : 2015. 7. 21. (나중에 다시 작업해야 함.)
	 */
	@RequestMapping("/org/unpdmgmt/getUnpdMgmtListExcel.json")
	public String getUnpdMgmtListExcel(@ModelAttribute("searchVO") UnpdMgmtVo searchVO,
									HttpServletRequest request,
									HttpServletResponse response,
									@RequestParam Map<String, Object> paramMap,
									ModelMap model){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = unpdMgmtService.getUnpdMgmtListExcel(searchVO, paramMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "미납자료_";//파일명
			String strSheetname = "미납자료";//시트명

			String [] strHead = {"청구월","작성일자","계약번호","서비스계약번호","고객명","전화번호","계약상태","개통일자","해지일자","청구항목","미납금액","개통대리점","명변여부"};
			String [] strValue = {"billYm", "workDt", "contractNum", "svcCntrNum", "custNm", "svcTelNum", "subStatusNm", "openDt", "tmntDt", "billItemNm", "unpayAmnt", "openAgntNm", "mcnYn"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 8000, 5000, 8000, 5000 };
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);

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

				paramMap.put("FILE_NM"   ,file.getName());              //파일명
				paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   ,"MSP");                       //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID

				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
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

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}
}