package com.ktis.msp.rntl.rntlMgmt.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rntl.rntlMgmt.mapper.RntlMgmtMapper;
import com.ktis.msp.rntl.rntlMgmt.vo.RntlMgmtByMnthCalcListVO;
import com.ktis.msp.rntl.rntlMgmt.vo.RntlMgmtByPurchSaleListVO;
import com.ktis.msp.rntl.rntlMgmt.vo.RntlMgmtByPurchStandVO;
import com.ktis.msp.rntl.rntlMgmt.vo.RntlMgmtBySaleOpenMgmtVO;

@Service
public class RntlMgmtService extends BaseService {

	@Autowired
	private RntlMgmtMapper rntlMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	final static private String END_YM = "999912";
	
	final static private String RTRN_PROC_N = "N";
	
	final static private String RNTL_RTRN_DEL = "DEL";
	
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regId",		"CUST_NAME");
		maskFields.put("rvisnId",	"CUST_NAME");
		return maskFields;
	}
	
	public List<RntlMgmtByPurchStandVO> getPurchStandList(RntlMgmtByPurchStandVO vo){
		
		if (vo.getBaseDt() == null || "".equals(vo.getBaseDt())) {
			throw new MvnoRunException(-1, "적용월이 존재 하지 않습니다.");
		}
		
		List<RntlMgmtByPurchStandVO> result = rntlMgmtMapper.getPurchStandList(vo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(result, getMaskFields(), paramMap);
		
		return result;
	}
	
	public List<RntlMgmtByPurchStandVO> getPurchStandListExcel(RntlMgmtByPurchStandVO vo){
		
		if (vo.getBaseDt() == null || "".equals(vo.getBaseDt())) {
			throw new MvnoRunException(-1, "적용월이 존재 하지 않습니다.");
		}
		
		List<RntlMgmtByPurchStandVO> result = rntlMgmtMapper.getPurchStandListExcel(vo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(result, getMaskFields(), paramMap);
		
		return result;
	}
	
	public List<RntlMgmtByPurchStandVO> getPurchStandDtlList(RntlMgmtByPurchStandVO vo){
		
		if (vo.getBaseDt() == null || "".equals(vo.getBaseDt())) {
			throw new MvnoRunException(-1, "적용월이 존재 하지 않습니다.");
		}
		
		if (vo.getPrdtId() == null || "".equals(vo.getPrdtId())) {
			throw new MvnoRunException(-1, "단말모델ID가 존재 하지 않습니다.");
		}
		
		List<RntlMgmtByPurchStandVO> result = rntlMgmtMapper.getPurchStandDtlList(vo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(result, getMaskFields(), paramMap);
		
		return result;
	}
	
	public int savPurchStandByInfo(RntlMgmtByPurchStandVO vo){
		
		if (vo.getOrgnId() == null || "".equals(vo.getOrgnId())) {
			throw new MvnoRunException(-1, "대리점ID가 존재 하지 않습니다.");
		}
		
		if (vo.getBaseDt() == null || "".equals(vo.getBaseDt())) {
			throw new MvnoRunException(-1, "적용월이 존재 하지 않습니다.");
		}
		
		if (vo.getPrdtId() == null || "".equals(vo.getPrdtId())) {
			throw new MvnoRunException(-1, "단말모델ID가 존재 하지 않습니다.");
		}
		
		if (vo.getDeplrictCost() == null || "".equals(vo.getDeplrictCost())) {
			throw new MvnoRunException(-1, "물류처리비가 존재 하지 않습니다.");
		}
		
		if (vo.getBuyAmnt() == null || "".equals(vo.getBuyAmnt())) {
			throw new MvnoRunException(-1, "매입가가 존재 하지 않습니다.");
		}
		
		if (vo.getRentalCost() == null || "".equals(vo.getRentalCost())) {
			throw new MvnoRunException(-1, "월렌탈비 존재 하지 않습니다.");
		}
		
		if (vo.getVirfyCost() == null || "".equals(vo.getVirfyCost())) {
			throw new MvnoRunException(-1, "검수비가 존재 하지 않습니다.");
		}
		
		if (vo.getGrntAmnt() == null || "".equals(vo.getGrntAmnt())) {
			throw new MvnoRunException(-1, "매입보장가가 존재 하지 않습니다.");
		}
		
		int nRslt = 0;
		
		List<RntlMgmtByPurchStandVO> rnltBaseInfo = rntlMgmtMapper.getMspRntlBaseInfo(vo);
		
		if(rnltBaseInfo.size() > 0){
			
			if(vo.getBaseDt().equals(rnltBaseInfo.get(0).getStrtYm())){
				
				vo.setStrtYm(vo.getBaseDt());
				vo.setEndYm(rnltBaseInfo.get(0).getEndYm());
				vo.setRvisnId(vo.getSessionUserId());
				
				nRslt = rntlMgmtMapper.updPurchStandByInfo(vo);
			}else{
				
				RntlMgmtByPurchStandVO updVo = new RntlMgmtByPurchStandVO();
				
				String updEndYm = "";
				
				try{
					updEndYm = getAddMonth(vo.getBaseDt(), -1);
				}catch(Exception e){
					throw new MvnoRunException(-1, "날짜 처리 오류 입니다.");
				}
				
				updVo.setStrtYm(rnltBaseInfo.get(0).getStrtYm());
				updVo.setEndYm(updEndYm);
				updVo.setOrgnId(vo.getOrgnId());
				updVo.setPrdtId(vo.getPrdtId());
				updVo.setRvisnId(vo.getSessionUserId());
				
				rntlMgmtMapper.updPurchStandByInfo(updVo);
				
				vo.setStrtYm(vo.getBaseDt());
				vo.setEndYm(rnltBaseInfo.get(0).getEndYm());
				vo.setRegId(vo.getSessionUserId());
				
				nRslt = rntlMgmtMapper.regPurchStandByInfo(vo);
				
			}
		}else{
			
			vo.setStrtYm(vo.getBaseDt());
			vo.setEndYm(END_YM);
			vo.setRegId(vo.getSessionUserId());
			
			nRslt = rntlMgmtMapper.regPurchStandByInfo(vo);
		}
		
		return nRslt;
	}
	
	public String getAddMonth(String dateMonth, int month) throws ParseException {
		String formatRstl = "";
		
		SimpleDateFormat  dateFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
		
		Date date = dateFormat.parse(dateMonth);
			
		Calendar calendar = Calendar.getInstance();
			
		calendar.setTime(date);
			
		calendar.add(Calendar.MONTH, month);
			
		formatRstl = dateFormat.format(calendar.getTime());
		
		return formatRstl;
	}
	
	public List<RntlMgmtBySaleOpenMgmtVO> getSaleOpenList(RntlMgmtBySaleOpenMgmtVO vo, Map<String, Object> paramMap){
		
		if(vo.getSearchGbn() == null || "".equals(vo.getSearchGbn())){
			if (vo.getSearchStartDt() == null || "".equals(vo.getSearchStartDt()) ||
				vo.getSearchEndDt() == null || "".equals(vo.getSearchEndDt())){
				throw new MvnoRunException(-1, "개통일자가 존재 하지 않습니다.");
			}
		}else{
			if(vo.getSearchName() == null || "".equals(vo.getSearchName())){
				throw new MvnoRunException(-1, "검색어가 존재 하지 않습니다.");
			}
		}
		List<RntlMgmtBySaleOpenMgmtVO> rsltList = rntlMgmtMapper.getSaleOpenList(vo);
		
		maskingService.setMask(rsltList, maskingService.getMaskFields(), paramMap);
		
		return rsltList;
	}
	
	public List<RntlMgmtBySaleOpenMgmtVO> getSaleOpenListExcel(RntlMgmtBySaleOpenMgmtVO vo, Map<String, Object> paramMap){
		
		if(vo.getSearchGbn() == null || "".equals(vo.getSearchGbn())){
			if (vo.getSearchStartDt() == null || "".equals(vo.getSearchStartDt()) ||
				vo.getSearchEndDt() == null || "".equals(vo.getSearchEndDt())){
				throw new MvnoRunException(-1, "개통일자가 존재 하지 않습니다.");
			}
		}else{
			if(vo.getSearchName() == null || "".equals(vo.getSearchName())){
				throw new MvnoRunException(-1, "검색어가 존재 하지 않습니다.");
			}
		}
		
		List<RntlMgmtBySaleOpenMgmtVO> rsltList = rntlMgmtMapper.getSaleOpenListExcel(vo);
		
		maskingService.setMask(rsltList, maskingService.getMaskFields(), paramMap);
		
		return rsltList;
	}
	
	public int saveReturnMgmtByInfo(RntlMgmtBySaleOpenMgmtVO vo){
		
		if (vo.getOrgnId() == null || "".equals(vo.getOrgnId())) {
			throw new MvnoRunException(-1, "대리점ID가 존재 하지 않습니다.");
		}
		
		if (vo.getContractNum() == null || "".equals(vo.getContractNum())) {
			throw new MvnoRunException(-1, "계약번호가 존재 하지 않습니다.");
		}
		
		if (vo.getPrdtId() == null || "".equals(vo.getPrdtId())) {
			throw new MvnoRunException(-1, "단말모델ID가 존재 하지 않습니다.");
		}
		
		if (vo.getRtrnDt() == null || "".equals(vo.getRtrnDt())) {
			throw new MvnoRunException(-1, "반납일자가 존재 하지 않습니다.");
		}
		
		int nRslt = 0;
		
		List<RntlMgmtBySaleOpenMgmtVO> rnltRtrnInfo = rntlMgmtMapper.getMspRntlRtrnInfo(vo);
		
		if(RNTL_RTRN_DEL.equals(vo.getRtrnTrtmTypeCd()) && rnltRtrnInfo.size() < 1){
			throw new MvnoRunException(-1, "반납 취소 대상이 없습니다.");
		}
		
		if(rnltRtrnInfo.size() > 0){
			
			String rtrnProcYn = rnltRtrnInfo.get(0).getRtrnProcYn();
			
			if("Y".equals(rtrnProcYn)){
				throw new MvnoRunException(-1, "정산 처리 되어 수정 할 수 없습니다.");
			}
			
			if(RNTL_RTRN_DEL.equals(vo.getRtrnTrtmTypeCd())){
				nRslt = rntlMgmtMapper.delReturnDt(vo);
			}else{
				vo.setRtrnProcYn(RTRN_PROC_N);
				vo.setRtrnProcId(vo.getSessionUserId());
				
				nRslt = rntlMgmtMapper.updReturnDt(vo);
			}
			
		}else{
			
			vo.setRtrnProcYn(RTRN_PROC_N);
			vo.setRtrnProcId(vo.getSessionUserId());
			
			nRslt = rntlMgmtMapper.regReturnDt(vo);
		}
		
		return nRslt;
	}
	
	public List<RntlMgmtByPurchSaleListVO> getPurchList(RntlMgmtByPurchSaleListVO vo){
		
		if (vo.getBaseDt() == null || "".equals(vo.getBaseDt())) {
			throw new MvnoRunException(-1, "정산월이 존재 하지 않습니다.");
		}
		
		return rntlMgmtMapper.getPurchList(vo);
	}
	
	public List<RntlMgmtByPurchSaleListVO> getSaleList(RntlMgmtByPurchSaleListVO vo){
		
		if (vo.getBaseDt() == null || "".equals(vo.getBaseDt())) {
			throw new MvnoRunException(-1, "정산월이 존재 하지 않습니다.");
		}
		
		return rntlMgmtMapper.getSaleList(vo);
	}
	
	public List<RntlMgmtByMnthCalcListVO> getMnthCalcList(RntlMgmtByMnthCalcListVO vo){
		
		if(vo.getSearchGbn() == null || "".equals(vo.getSearchGbn())){
			if (vo.getBaseDt() == null || "".equals(vo.getBaseDt())) {
				throw new MvnoRunException(-1, "정산월이 존재 하지 않습니다.");
			}
		}else{
			if(vo.getSearchName() == null || "".equals(vo.getSearchName())){
				throw new MvnoRunException(-1, "검색어가 존재 하지 않습니다.");
			}
		}
		
		return rntlMgmtMapper.getMnthCalcList(vo);
	}
	
	public List<RntlMgmtByMnthCalcListVO> getMnthCalcListExcel(RntlMgmtByMnthCalcListVO vo){
		
		if(vo.getSearchGbn() == null || "".equals(vo.getSearchGbn())){
			if (vo.getBaseDt() == null || "".equals(vo.getBaseDt())) {
				throw new MvnoRunException(-1, "정산월이 존재 하지 않습니다.");
			}
		}else{
			if(vo.getSearchName() == null || "".equals(vo.getSearchName())){
				throw new MvnoRunException(-1, "검색어가 존재 하지 않습니다.");
			}
		}
		
		return rntlMgmtMapper.getMnthCalcListExcel(vo);
	}
	
	public List<RntlMgmtByMnthCalcListVO> getMnthCalcDtlList(RntlMgmtByMnthCalcListVO vo){
		
		if (vo.getPrvdYm() == null || "".equals(vo.getPrvdYm())) {
			throw new MvnoRunException(-1, "정산월이 존재 하지 않습니다.");
		}
		
		if (vo.getContractNum() == null || "".equals(vo.getContractNum())) {
			throw new MvnoRunException(-1, "계약번호가 존재 하지 않습니다.");
		}
		
		return rntlMgmtMapper.getMnthCalcDtlList(vo);
	}
}
