package com.ktis.msp.bnd.bondmgmt.controller;

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
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.bnd.bondmgmt.service.BondSaleMgmtService;
import com.ktis.msp.bnd.bondmgmt.vo.BondSaleMgmtVO;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class BondSaleMgmtController extends BaseController {
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private BtchMgmtService btchMgmtService;
	
	@Autowired
	private BondSaleMgmtService bondSaleService;
	
   @Autowired
   private OrgCommonService orgCommonService;
	
   //v2018.11 PMD 적용 소스 수정
   @Resource(name = "propertiesService")
   protected EgovPropertyService propertiesService;
   
	public BondSaleMgmtController() {
		setLogPrefix("[BondSaleMgmtController] ");
	}	
	
	/**
	 * 매각채권수납내역서 화면 
	 */
	@RequestMapping(value = "/bond/bondsalemgmt/soldReceiptByMonth.do")
	public ModelAndView soldReceiptByMonth(HttpServletRequest request
											, HttpServletResponse response
											, @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
											, ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
	         //메뉴명 가져오기
	         modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
	         
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			
			modelAndView.setViewName("/bnd/bondsalemgmt/msp_bnd_sale_1014");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoErrorException(e);
		}
	}
	
	/**
	 * 매각채권수납내역 합계조회
	 */
	@RequestMapping("/bond/bondsalemgmt/selectSoldReceiptByMonthSum.json")
	public String selectSoldReceiptByMonthSum(HttpServletRequest request
										, HttpServletResponse response
										, @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
										, @RequestParam Map<String, Object> pRequestParamMap
										, ModelMap model) {
		
		logger.debug(generateLogMsg("================================================================="));
		logger.debug(generateLogMsg("매각채권수납내역 조회 START."));
		logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
		logger.debug(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = bondSaleService.selectSoldReceiptByMonthSum(searchVO);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	
	/**
	 * 매각채권수납내역 조회
	 */
	@RequestMapping("/bond/bondsalemgmt/selectSoldReceiptByMonth.json")
	public String selectSoldReceiptByMonth(HttpServletRequest request
			, HttpServletResponse response
			, @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
			, @RequestParam Map<String, Object> pRequestParamMap
			, ModelMap model) {
		
		logger.debug(generateLogMsg("================================================================="));
		logger.debug(generateLogMsg("매각채권수납내역 조회 START."));
		logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
		logger.debug(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = bondSaleService.selectSoldReceiptByMonth(searchVO);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	
	/**
	 * 매각채권수납내역 조회
	 */
	@RequestMapping("/bond/bondsalemgmt/selectSoldReceiptByMonthExcel.json")
	public String selectSoldReceiptByMonthExcel(HttpServletRequest request
										, HttpServletResponse response
										, @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
										, @RequestParam Map<String, Object> pRequestParamMap
										, ModelMap model) {
		
		logger.debug(generateLogMsg("================================================================="));
		logger.debug(generateLogMsg("매각채권수납내역 엑셀다운 START."));
		logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
		logger.debug(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			
			Map<String, Object> excelMap = new HashMap<String, Object>();
			
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","BND");
			excelMap.put("MENU_NM","매각채권수납내역");
			String searchVal = "판매회자:"+searchVO.getBondSeqNo()+
							"|기준월:["+searchVO.getBillYm()
						;
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			BatchJobVO vo = new BatchJobVO();
			
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00107");
			vo.setSessionUserId(loginInfo.getUserId());
			
			String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
		   
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
			
			vo.setExecParam("{\"bondSeqNo\":" + "\"" + searchVO.getBondSeqNo() + "\""
						+ ",\"billYm\":" + "\"" + searchVO.getBillYm() + "\"" 
						+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\"" 
						+ ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\"" 
						+ ",\"ipAddr\":" + "\"" + ipAddr + "\"" 
						+ ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\"" 
						+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}" 
						
					);
			
			btchMgmtService.insertBatchRequest(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------	
		model.addAttribute("result", resultMap);
		return "jsonView"; 
	}
	
   /**
    * 채권판매대상조회 화면 
    */
   @RequestMapping(value = "/bond/bondsalemgmt/bondSaleObjectInit.do")
   public ModelAndView bondSaleObjectInit(HttpServletRequest request
                                 , HttpServletResponse response
                                 , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
                                 , ModelMap model) {
      
      ModelAndView modelAndView = new ModelAndView();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         //메뉴명 가져오기
         modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
         
         modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
         
         model.addAttribute("trgtStrtDt",orgCommonService.getLastMonthDay());
         model.addAttribute("trgtEndDt",orgCommonService.getToDay());
         
         modelAndView.setViewName("/bnd/bondsalemgmt/msp_bnd_sale_1001");
         
         return modelAndView;
      } catch(Exception e) {
         throw new MvnoErrorException(e);
      }
   }
   
   /**
    * 채권판매대상조회 조회
    */
   @RequestMapping("/bond/bondsalemgmt/getBondSaleObjectLstInfo.json")
   public String getBondSaleObjectLstInfo(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("채권판매대상조회 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         loginInfo.putSessionToParameterMap(pRequestParamMap);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.getBondSaleObjectLstInfo(searchVO, pRequestParamMap);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * 채권판매대상건수 및 총금액 조회
    */
   @RequestMapping("/bond/bondsalemgmt/getBondSaleObjectHap.json")
   public String getBondSaleObjectHap(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("채권판매대상건수 및 총금액 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.getBondSaleObjectHap(searchVO);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /*
    * 채권판매생성
    * IBSHEET -> DHX 전환 처리
    * 저장성 업무는 제외 처리 하여 기존 IBSHEET 원본 소스 주석 처리
    * 
   @RequestMapping(value="/bond/bondsalemgmt/inserBondSaleInfo.json")
   public @ResponseBody AjaxReturnVO inserBondSaleInfo(HttpServletRequest paramReq,
                                           HttpServletResponse paramRes,
                                           @RequestBody Map<String, Object> commandMap) throws EgovBizException {
      
      
      AjaxReturnVO returnVO = new AjaxReturnVO();
      
      
      //본사화면일경우
      if(!"10".equals(paramReq.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
         throw new EgovBizException("권한이 없습니다.");
      }
      try {
         LOGGER.info(commandMap.toString());
         commandMap.put("sessionUserId", paramReq.getSession().getAttribute("SESSION_USER_ID"));
         //dataVo = (BondSaleObjectVO) getBeanFromJson(commandMap, "frmMain", BondSaleObjectVO.class);
         
         LOGGER.info("dddd=== " + commandMap.toString());
         
         //LOGGER.info(dataVo.toString());
         
         bondSaleService.inserBondSaleInfo(commandMap);
         
         returnVO.setCode("OK");
         returnVO.setCodeMessage("");
      }
      catch( Exception e ) {
         
         throw new EgovBizException("처리 중 오류가 발생하였습니다\n관리자에게 문의바랍니다.");
      }
      
      return returnVO;
   }
    * 
    * 
    */
   
   /**
    * 채권판매관리 화면 
    */
   @RequestMapping(value = "/bond/bondsalemgmt/bondSaleMgmtInit.do")
   public ModelAndView bondSaleMgmtInit(HttpServletRequest request
                                 , HttpServletResponse response
                                 , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
                                 , ModelMap model) {
      
      ModelAndView modelAndView = new ModelAndView();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         //메뉴명 가져오기
         modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
         
         modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
         
         modelAndView.setViewName("/bnd/bondsalemgmt/msp_bnd_sale_1002");
         
         return modelAndView;
      } catch(Exception e) {
         throw new MvnoErrorException(e);
      }
   }
   
   /**
    * 채권판매내역 조회
    */
   @RequestMapping("/bond/bondsalemgmt/getBondSaleInfo.json")
   public String getBondSaleInfo(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("채권판매내역 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.getBondSaleInfo(searchVO);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * 채권판매상세내역 조회
    */
   @RequestMapping("/bond/bondsalemgmt/getBondSaleCntrList.json")
   public String getBondSaleCntrList(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("채권판매상세내역 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         loginInfo.putSessionToParameterMap(pRequestParamMap);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.getBondSaleCntrList(searchVO, pRequestParamMap);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * 채권상세정보 조회
    */
   @RequestMapping("/bond/bondsalemgmt/getBondSaleAssetDtlInfo.json")
   public String getBondSaleAssetDtlInfo(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("채권상세정보 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.getBondSaleAssetDtlInfo(searchVO);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   
   /*
    * 판매제외처리
    * IBSHEET -> DHX 전환 처리
    * 저장성 업무는 제외 처리 하여 기존 IBSHEET 원본 소스 주석 처리
    * 
    * 
   @RequestMapping(value="/bond/bondsalemgmt/soldBondCan.json")
   public @ResponseBody AjaxReturnVO soldBondCan(HttpServletRequest paramReq,
                                      HttpServletResponse paramRes,
                                      @RequestBody Map<String, Object> commandMap) throws EgovBizException {
      
      
      AjaxReturnVO returnVO = new AjaxReturnVO();
      
      //본사화면일경우
      if(!"10".equals(paramReq.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
         throw new EgovBizException("권한이 없습니다.");
      }
      try {
         LOGGER.info(commandMap.toString());
         commandMap.put("sessionUserId", paramReq.getSession().getAttribute("SESSION_USER_ID"));
         //dataVo = (BondSaleObjectVO) getBeanFromJson(commandMap, "frmMain", BondSaleObjectVO.class);
         
         //LOGGER.info(dataVo.toString());
         
         bondSaleService.deleteBondSale(commandMap);
         
         returnVO.setCode("OK");
         returnVO.setCodeMessage("");
      }
      catch( Exception e ) {
         
         throw new EgovBizException("처리 중 오류가 발생하였습니다\n관리자에게 문의바랍니다.");
      }
      
      return returnVO;
   }
    */
   
   /*
    * 판매처리
    * IBSHEET -> DHX 전환 처리
    * 저장성 업무는 제외 처리 하여 기존 IBSHEET 원본 소스 주석 처리
    * 
    * 
   @RequestMapping(value="/bond/bondsalemgmt/saleBondCfm.json")
   public @ResponseBody AjaxReturnVO saleBondCfm(HttpServletRequest paramReq,
                                      HttpServletResponse paramRes,
                                      @RequestBody Map<String, Object> commandMap) throws EgovBizException {
      
      
      AjaxReturnVO returnVO = new AjaxReturnVO();
      
//    LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
      
      //본사화면일경우
      if(!"10".equals(paramReq.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
         throw new EgovBizException("권한이 없습니다.");
      }
      try {
         LOGGER.info(commandMap.toString());
         commandMap.put("sessionUserId", paramReq.getSession().getAttribute("SESSION_USER_ID"));
         //dataVo = (BondSaleObjectVO) getBeanFromJson(commandMap, "frmMain", BondSaleObjectVO.class);
         
         //LOGGER.info(dataVo.toString());
         
         bondSaleService.saleBondCfm(commandMap);
         
         returnVO.setCode("OK");
         returnVO.setCodeMessage("");
      }
      catch( Exception e ) {
         
         throw new EgovBizException("처리 중 오류가 발생하였습니다\n관리자에게 문의바랍니다.");
      }
      
      return returnVO;
   }
    */
   
   /*
    * 판매회수처리
    * IBSHEET -> DHX 전환 처리
    * 저장성 업무는 제외 처리 하여 기존 IBSHEET 원본 소스 주석 처리
    * 
    * 
   @RequestMapping(value="/bond/bondsalemgmt/bckSoldBond.json")
   public @ResponseBody AjaxReturnVO bckSoldBond(HttpServletRequest paramReq,
                                      HttpServletResponse paramRes,
                                      @RequestBody Map<String, Object> commandMap) throws EgovBizException {
      
      
      AjaxReturnVO returnVO = new AjaxReturnVO();
      
      //본사화면일경우
      if(!"10".equals(paramReq.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
         throw new EgovBizException("권한이 없습니다.");
      }
      try {
         LOGGER.info(commandMap.toString());
         commandMap.put("sessionUserId", paramReq.getSession().getAttribute("SESSION_USER_ID"));
         //dataVo = (BondSaleObjectVO) getBeanFromJson(commandMap, "frmMain", BondSaleObjectVO.class);
         
         //LOGGER.info(dataVo.toString());
         
         bondSaleService.bckSoldBond(commandMap);
         
         returnVO.setCode("OK");
         returnVO.setCodeMessage("");
      }
      catch( EgovBizException e ) {
         LOGGER.debug(e.getMessage());
         throw e;
      }
      catch( Exception e ) {
         
         throw new EgovBizException("처리 중 오류가 발생하였습니다\n관리자에게 문의바랍니다.");
      }
      
      return returnVO;
   }
    */
   
   /**
    * 판매채권자산명세서 화면 
    */
   @RequestMapping(value = "/bond/bondsalemgmt/soldAssetList.do")
   public ModelAndView soldAssetList(HttpServletRequest request
                                 , HttpServletResponse response
                                 , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
                                 , ModelMap model) {
      
      ModelAndView modelAndView = new ModelAndView();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         //메뉴명 가져오기
         modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
         
         modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
         
         modelAndView.setViewName("/bnd/bondsalemgmt/msp_bnd_sale_1003");
         
         return modelAndView;
      } catch(Exception e) {
         throw new MvnoErrorException(e);
      }
   }
   
   /**
    * 판매채권자산명세서 조회
    */
   @RequestMapping("/bond/bondsalemgmt/soldAssetList.json")
   public String soldAssetList(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("판매채권자산명세서 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         loginInfo.putSessionToParameterMap(pRequestParamMap);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectSoldAssetList(searchVO, pRequestParamMap);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * 판매채권자산명세서 합계 조회
    */
   @RequestMapping("/bond/bondsalemgmt/soldAssetListBySum.json")
   public String soldAssetListBySum(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("판매채권자산명세서 합계 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectSoldAssetListBySum(searchVO);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * 판매채권자산명세서 상세 조회
    */
   @RequestMapping("/bond/bondsalemgmt/soldAssetDtlInfo.json")
   public String soldAssetDtlInfo(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("판매채권자산명세서 상세 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectSoldAssetDtlInfo(searchVO);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * STATIC DATA 화면 
    */
   @RequestMapping(value = "/bond/bondsalemgmt/soldStaticData.do")
   public ModelAndView soldStaticData(HttpServletRequest request
                                 , HttpServletResponse response
                                 , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
                                 , ModelMap model) {
      
      ModelAndView modelAndView = new ModelAndView();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         //메뉴명 가져오기
         modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
         
         modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
         
         modelAndView.setViewName("/bnd/bondsalemgmt/msp_bnd_sale_1004");
         
         return modelAndView;
      } catch(Exception e) {
         throw new MvnoErrorException(e);
      }
   }
   
   /**
    * STATIC DATA 조회
    */
   @RequestMapping("/bond/bondsalemgmt/getSoldBondStaticInfoList.json")
   public String getSoldBondStaticInfoList(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("STATIC DATA 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectSoldBondStaticInfoList(searchVO);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * STATIC DATA 엑셀 다운로드
    */
   @RequestMapping("/bond/bondsalemgmt/getSoldBondStaticInfoListByExcel.json")
   public String getParcelServiceLstInfoByExcel(@ModelAttribute("bondSaleMgmtVO") BondSaleMgmtVO bondSaleMgmtVO,
                                    HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestParam Map<String, Object> paramMap,
                                    ModelMap model) {
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      String returnMsg = null;
      FileInputStream in = null;
      OutputStream out = null;
      File file = null;
      
      try {
         /* 로그인조직정보 및 권한체크 */
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(bondSaleMgmtVO);
         
         // 본사 화면인 경우
         if(!"10".equals(bondSaleMgmtVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectSoldBondStaticInfoList(bondSaleMgmtVO);
         
         String serverInfo = propertiesService.getString("excelPath");
         String strFilename = serverInfo  + "STATIC DATA_";//파일명
         String strSheetname = "STATIC DATA";//시트명
         
         String [] strHead = {"판매회차","청구월","월초채권잔액","월초잔존건수","정상청구","연체청구","정상입금","연체입금","조기상환(완납)","조기상환(부분납)","수수료/기타연체 가산금","보증보험 청구건수","보증보험 청구금액","보증보험 수납금액","보증보험차액","보증환급이자","하자담보(채권회수)","하자담보(조정금액)","월말채권잔액","월말건수","0개월","1개월","2개월","3개월","4개월","5개월","6개월","7개월","8개월","9개월","10개월","11개월","12개월이상"};
         String [] strValue = {"saleNo","billYm","mnthFstAmt","bondCnt","billAmnt","dlyBillAmt","pymnAmnt","unpayAmnt","nrFullPrfpayAmnt","nrMidPrfpayAmnt","arrsFeeAmnt","insrBillCnt","insrBillAmnt","insrPymnAmnt","insrMtgAmt","insrBckIntAmt","nrRtnAmnt","adjsAmnt","yetPymnAmnt","mnthLstCnt","yetPymnAmnt00","yetPymnAmnt01","yetPymnAmnt02","yetPymnAmnt03","yetPymnAmnt04","yetPymnAmnt05","yetPymnAmnt06","yetPymnAmnt07","yetPymnAmnt08","yetPymnAmnt09","yetPymnAmnt10","yetPymnAmnt11","yetPymnAmnt12"};
         
         //엑셀 컬럼 사이즈
         int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 8000, 8000, 8000, 8000, 5000, 5000, 8000, 8000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
         int[] intLen = {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
         
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
            
            paramMap.put("FILE_NM"   ,file.getName());              //파일명
            paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
            paramMap.put("DUTY_NM"   ,"BND");                       //업무명
            paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
            paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
            paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
            paramMap.put("DATA_CNT", 0);                            //자료건수
            paramMap.put("SESSION_USER_ID", loginInfo.getUserId());  //사용자ID
            
            fileDownService.insertCmnFileDnldMgmtMst(paramMap);
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
   
   /**
    * 수납내역서 화면 
    */
   @RequestMapping(value = "/bond/bondsalemgmt/soldReceipt.do")
   public ModelAndView soldReceipt(HttpServletRequest request
                                 , HttpServletResponse response
                                 , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
                                 , ModelMap model) {
      
      ModelAndView modelAndView = new ModelAndView();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         //메뉴명 가져오기
         modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
         
         modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
         
         modelAndView.setViewName("/bnd/bondsalemgmt/msp_bnd_sale_1005");
         
         return modelAndView;
      } catch(Exception e) {
         throw new MvnoErrorException(e);
      }
   }
   
   /**
    * 수납내역서 조회
    */
   @RequestMapping("/bond/bondsalemgmt/selectSoldReceipt.json")
   public String selectSoldReceipt(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("수납내역서 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectSaleStaticDtl(searchVO);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * 수납내역서 확정
    */
   @RequestMapping("/bond/bondsalemgmt/confirmReceipt.json")
   public String confirmReceipt(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("수납내역서 확정 START"));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         bondSaleService.confirmReceipt(searchVO);
         
         resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
         resultMap.put("msg", "");
         
      } catch (Exception e) {
         resultMap.clear();
         
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * 회수채권관리 화면 
    */
   @RequestMapping(value = "/bond/bondsalemgmt/canBondMgmt.do")
   public ModelAndView canBondMgmt(HttpServletRequest request
                                 , HttpServletResponse response
                                 , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
                                 , ModelMap model) {
      
      ModelAndView modelAndView = new ModelAndView();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         //메뉴명 가져오기
         modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
         
         modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
         
         modelAndView.setViewName("/bnd/bondsalemgmt/msp_bnd_sale_1006");
         
         return modelAndView;
      } catch(Exception e) {
         throw new MvnoErrorException(e);
      }
   }
   
   /**
    * 회수채권 정보 조회
    */
   @RequestMapping("/bond/bondsalemgmt/getCanBondList.json")
   public String getCanBondList(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("회수채권 정보 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         loginInfo.putSessionToParameterMap(pRequestParamMap);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectSaleBckInfoList(searchVO, pRequestParamMap);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * 회수채권 정보 합계 조회
    */
   @RequestMapping("/bond/bondsalemgmt/getCanBondListBySum.json")
   public String getCanBondListBySum(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("회수채권 정보 합계 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectSaleBckInfoListBySum(searchVO);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * 회수채권 정보 엑셀 다운로드
    */
   @RequestMapping("/bond/bondsalemgmt/getCanBondListByExcel.json")
   public String getCanBondListByExcel(@ModelAttribute("bondSaleMgmtVO") BondSaleMgmtVO bondSaleMgmtVO,
                                    HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestParam Map<String, Object> paramMap,
                                    ModelMap model) {
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      String returnMsg = null;
      FileInputStream in = null;
      OutputStream out = null;
      File file = null;
      
      try {
         /* 로그인조직정보 및 권한체크 */
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(bondSaleMgmtVO);
         loginInfo.putSessionToParameterMap(paramMap);
         
         // 본사 화면인 경우
         if(!"10".equals(bondSaleMgmtVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectSaleBckInfoList(bondSaleMgmtVO, paramMap);
         
         String serverInfo = propertiesService.getString("excelPath");
         String strFilename = serverInfo  + "회수채권_";//파일명
         String strSheetname = "회수채권";//시트명
         
         String [] strHead = {"판매회차","회수월","계약번호","보증보험번호","전화번호","회수금액","회수처리자","회수이유"};
         String [] strValue = {"saleNo","rtnYm","contractNum","grntInsrMngmNo","subscriberNo","rtnAmt","rtnId","rtnRsn"};
         
         //엑셀 컬럼 사이즈
         int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
         int[] intLen = {0, 0, 0, 0, 0, 1, 0, 0};
         
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
            
            paramMap.put("FILE_NM"   ,file.getName());              //파일명
            paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
            paramMap.put("DUTY_NM"   ,"BND");                       //업무명
            paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
            paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
            paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
            paramMap.put("DATA_CNT", 0);                            //자료건수
            paramMap.put("SESSION_USER_ID", loginInfo.getUserId());  //사용자ID
            
            fileDownService.insertCmnFileDnldMgmtMst(paramMap);
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
   
   /**
    * Scheduled CF 화면 
    */
   @RequestMapping(value = "/bond/bondsalemgmt/scheduledCf.do")
   public ModelAndView scheduledCf(HttpServletRequest request
                                 , HttpServletResponse response
                                 , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
                                 , ModelMap model) {
      
      ModelAndView modelAndView = new ModelAndView();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         //메뉴명 가져오기
         modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
         
         modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
         
         modelAndView.setViewName("/bnd/bondsalemgmt/msp_bnd_sale_1007");
         
         return modelAndView;
      } catch(Exception e) {
         throw new MvnoErrorException(e);
      }
   }
   
   /**
    * Scheduled CF 조회
    */
   @RequestMapping("/bond/bondsalemgmt/selectScheduledCfList.json")
   public String selectScheduledCfList(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("Scheduled CF 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectScheduledCfList(searchVO);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
         resultMap.put("expBondAmtSum", bondSaleService.getExpBondAmtSum(searchVO));
         
      } catch (Exception e) {
         resultMap.clear();
         
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * Scheduled CF 엑셀 다운로드
    */
   @RequestMapping("/bond/bondsalemgmt/selectScheduledCfListByExcel.json")
   public String selectScheduledCfListByExcel(@ModelAttribute("bondSaleMgmtVO") BondSaleMgmtVO bondSaleMgmtVO,
                                    HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestParam Map<String, Object> paramMap,
                                    ModelMap model) {
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      String returnMsg = null;
      FileInputStream in = null;
      OutputStream out = null;
      File file = null;
      
      try {
         /* 로그인조직정보 및 권한체크 */
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(bondSaleMgmtVO);
         
         // 본사 화면인 경우
         if(!"10".equals(bondSaleMgmtVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectScheduledCfList(bondSaleMgmtVO);
         
         String serverInfo = propertiesService.getString("excelPath");
         String strFilename = serverInfo  + "Scheduled CF_";//파일명
         String strSheetname = "Scheduled CF";//시트명
         
         String [] strHead = {"납입월","예상납입금액"};
         String [] strValue = {"saleYm","expBondAmt"};
         
         //엑셀 컬럼 사이즈
         int[] intWidth = {5000, 5000};
         int[] intLen = {0, 1};
         
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
            
            paramMap.put("FILE_NM"   ,file.getName());              //파일명
            paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
            paramMap.put("DUTY_NM"   ,"BND");                       //업무명
            paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
            paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
            paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
            paramMap.put("DATA_CNT", 0);                            //자료건수
            paramMap.put("SESSION_USER_ID", loginInfo.getUserId());  //사용자ID
            
            fileDownService.insertCmnFileDnldMgmtMst(paramMap);
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
   
   /**
    * 공시용자산명세서 화면 
    */
   @RequestMapping(value = "/bond/bondsalemgmt/soldAssetList2.do")
   public ModelAndView soldAssetList2(HttpServletRequest request
                                 , HttpServletResponse response
                                 , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
                                 , ModelMap model) {
      
      ModelAndView modelAndView = new ModelAndView();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         //메뉴명 가져오기
         modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
         
         modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
         
         modelAndView.setViewName("/bnd/bondsalemgmt/msp_bnd_sale_1008");
         
         return modelAndView;
      } catch(Exception e) {
         throw new MvnoErrorException(e);
      }
   }
   
   /**
    * 공시용자산명세서 조회
    */
   @RequestMapping("/bond/bondsalemgmt/soldAssetList2.json")
   public String soldAssetList2(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("공시용자산명세서 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         loginInfo.putSessionToParameterMap(pRequestParamMap);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectSoldAssetList2(searchVO, pRequestParamMap);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
   
   /**
    * 공시용자산명세서 합계 조회
    */
   @RequestMapping("/bond/bondsalemgmt/soldAssetList2BySum.json")
   public String soldAssetList2BySum(HttpServletRequest request
         , HttpServletResponse response
         , @ModelAttribute("searchVO") BondSaleMgmtVO searchVO
         , @RequestParam Map<String, Object> pRequestParamMap
         , ModelMap model) {
      
      logger.debug(generateLogMsg("================================================================="));
      logger.debug(generateLogMsg("공시용자산명세서 합계 조회 START."));
      logger.debug(generateLogMsg("BondSaleMgmtVO = " + searchVO.toString()));
      logger.debug(generateLogMsg("================================================================="));
      
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      try {
         //----------------------------------------------------------------
         // Login check
         //----------------------------------------------------------------
         LoginInfo loginInfo = new LoginInfo(request, response);
         loginInfo.putSessionToVo(searchVO);
         
         // 본사 화면인 경우
         if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
            throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
         }
         
         List<?> resultList = bondSaleService.selectSoldAssetList2BySum(searchVO);
         
         resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
         
      } catch (Exception e) {
         resultMap.clear();
         if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
      }
      
      model.addAttribute("result", resultMap);
      
      return "jsonView"; 
   }
}
