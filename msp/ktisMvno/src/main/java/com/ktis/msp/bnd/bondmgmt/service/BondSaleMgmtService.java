package com.ktis.msp.bnd.bondmgmt.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.bnd.bondmgmt.mapper.BondSaleMgmtMapper;
import com.ktis.msp.bnd.bondmgmt.vo.BondSaleMgmtVO;
import com.ktis.msp.cmn.masking.service.MaskingService;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Service
public class BondSaleMgmtService extends BaseService {
	
	@Autowired
	private BondSaleMgmtMapper bondSaleMapper;
	
   @Autowired
   private MaskingService maskingService;
   
   //private BigDecimal bigExpBondAmtSum = null;
	
	public List<?> selectSoldReceiptByMonth(BondSaleMgmtVO vo){
		if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
			throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
		}
		
		if(vo.getBillYm() == null || "".equals(vo.getBillYm())){
			throw new MvnoRunException(-1, "기준월 정보가 없습니다");
		}
		
		List<?> list = bondSaleMapper.selectSoldReceiptByMonth(vo);
		
		return list;
	}
	
	public List<?> selectSoldReceiptByMonthSum(BondSaleMgmtVO vo){
		if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
			throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
		}
		
		if(vo.getBillYm() == null || "".equals(vo.getBillYm())){
			throw new MvnoRunException(-1, "기준월 정보가 없습니다");
		}
		
		List<?> list = bondSaleMapper.selectSoldReceiptByMonthSum(vo);
		
		return list;
	}
	
   public List<?> getBondSaleObjectLstInfo(BondSaleMgmtVO vo, Map<String, Object> paramMap) throws EgovBizException{
      
      if((vo.getTrgtStrtDt() == null || "".equals(vo.getTrgtStrtDt())) ||
         (vo.getTrgtEndDt() == null || "".equals(vo.getTrgtEndDt())) ){
         throw new MvnoRunException(-1, "매입일자 정보가 없습니다");
      }
      
      HashMap<String, String> maskFields = getMaskFields();
      List<?> result = bondSaleMapper.getBondSaleObjectLstInfo(vo);
      
      maskingService.setMask(result, maskFields, paramMap);
      
      return result;
   }
   
   public List<?> getBondSaleObjectHap(BondSaleMgmtVO vo){
      
      if((vo.getTrgtStrtDt() == null || "".equals(vo.getTrgtStrtDt())) ||
            (vo.getTrgtEndDt() == null || "".equals(vo.getTrgtEndDt())) ){
            throw new MvnoRunException(-1, "매입일자 정보가 없습니다");
      }
      
      List<?> list = bondSaleMapper.getBondSaleObjectHap(vo);
      
      return list;
   }
   
   /* 
    * 채권판매생성
    * IBSHEET -> DHX 전환 처리
    * 저장성 업무는 제외 처리 하여 기존 IBSHEET 원본 소스 주석 처리
   public void inserBondSaleInfo(Map<String, Object> commandMap) throws EgovBizException {
      
      bondSaleMapper.callCreateBondSale(commandMap);
   }
   */
   
   public List<?> getBondSaleInfo(BondSaleMgmtVO vo) throws EgovBizException{
      
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
         throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
      }
      
      List<?> result = bondSaleMapper.getBondSaleInfo(vo);
      
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
      
      HashMap<String, String> maskFields = getMaskFields();
      
      maskingService.setMask(result, maskFields, paramMap);
      
      return result;
   }
   
   public List<?> getBondSaleCntrList(BondSaleMgmtVO vo, Map<String, Object> paramMap) throws EgovBizException{
      
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
         throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
      }
      
      if( (vo.getpSearchGbn() != null && !"".equals(vo.getpSearchGbn())) &&
          (vo.getpSearchName() == null || "".equals(vo.getpSearchName()))){
         throw new MvnoRunException(-1, "검색 내용이 없습니다");
      }
      
      HashMap<String, String> maskFields = getMaskFields();
      List<?> result = bondSaleMapper.getBondSaleCntrList(vo);
      
      maskingService.setMask(result, maskFields, paramMap);
      
      return result;
   }
   
   public List<?> getBondSaleAssetDtlInfo(BondSaleMgmtVO vo) throws EgovBizException{
      
      return bondSaleMapper.getBondSaleAssetDtlInfo(vo);
   }
   
   /* 
    * 판매제외 처리
    * IBSHEET -> DHX 전환 처리
    * 저장성 업무는 제외 처리 하여 기존 IBSHEET 원본 소스 주석 처리
   public void deleteBondSale(Map<String, Object> commandMap) throws EgovBizException{
      
      bondSaleMapper.deleteBondSaleDtl(commandMap);
   }
   */
   
   /* 
    * 판매처리
    * IBSHEET -> DHX 전환 처리
    * 저장성 업무는 제외 처리 하여 기존 IBSHEET 원본 소스 주석 처리
   public void saleBondCfm(Map<String, Object> commandMap) throws EgovBizException{
      
      bondSaleMapper.updateBondSaleInfo(commandMap);
   }
   */
   
   /* 
    * 판매회수처리
    * IBSHEET -> DHX 전환 처리
    * 저장성 업무는 제외 처리 하여 기존 IBSHEET 원본 소스 주석 처리
   public void bckSoldBond(Map<String, Object> commandMap) throws EgovBizException{
      
      // 1. MSP_BOND_SALE_DTL 에 업데이트
      // 2. MSP_BOND_ASSET 에서 BOND_SEQ_NO를 NULL
      
      // 판매채권 회수처리 업데이트
      bondSaleMapper.updateBckBondSaleDtl(commandMap);
      bondSaleMapper.updateBckBondAsset(commandMap);
      bondSaleMapper.updateBckBondSaleSttc(commandMap);
   }
   */
   
   public List<?> selectSoldAssetList(BondSaleMgmtVO vo, Map<String, Object> paramMap) throws EgovBizException{
      
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
         throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
      }
      
      if(vo.getBillYm() == null || "".equals(vo.getBillYm())){
         throw new MvnoRunException(-1, "기준월 정보가 없습니다");
      }
      
      HashMap<String, String> maskFields = getMaskFields();
      List<?> result = bondSaleMapper.selectSoldAssetList(vo);
      
      maskingService.setMask(result, maskFields, paramMap);
      
      return result;
   }
   
   public List<?> selectSoldAssetListBySum(BondSaleMgmtVO vo){
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
         throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
      }
      
      if(vo.getBillYm() == null || "".equals(vo.getBillYm())){
         throw new MvnoRunException(-1, "기준월 정보가 없습니다");
      }
      
      List<?> list = bondSaleMapper.selectSoldAssetListBySum(vo);
      
      return list;
   }
   
   public List<?> selectSoldAssetDtlInfo(BondSaleMgmtVO vo) throws EgovBizException{
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
         throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
      }
      
      if(vo.getGrntInsrMngmNo() == null || "".equals(vo.getGrntInsrMngmNo())){
         throw new MvnoRunException(-1, "보증보험 정보가 없습니다");
      }
      
      return bondSaleMapper.selectSoldAssetDtlInfo(vo);
   }
   
   public List<?> selectSoldBondStaticInfoList(BondSaleMgmtVO vo) throws EgovBizException{
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
         throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
      }
      
      return bondSaleMapper.selectSoldBondStaticInfoList(vo);
   }
   
   public List<?> selectSaleStaticDtl(BondSaleMgmtVO vo){
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
         throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
      }
      
      if(vo.getBillYm() == null || "".equals(vo.getBillYm())){
         throw new MvnoRunException(-1, "수납월 정보가 없습니다");
      }
      
      List<?> list = bondSaleMapper.selectSaleStaticDtl(vo);
      
      return list;
   }
   
   public void confirmReceipt(BondSaleMgmtVO vo){
      if(vo.getRunDt() == null || "".equals(vo.getRunDt())){
         throw new MvnoRunException(-1, "회계일자 정보가 없습니다");
      }
      
      bondSaleMapper.confirmReceipt(vo);
   }
   
   public List<?> selectSaleBckInfoList(BondSaleMgmtVO vo, Map<String, Object> paramMap) throws EgovBizException{
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
         throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
      }
      
      HashMap<String, String> maskFields = getMaskFields();
      List<?> result = bondSaleMapper.selectSaleBckInfoList(vo);
      
      maskingService.setMask(result, maskFields, paramMap);
      
      return result;
   }
   
   public List<?> selectSaleBckInfoListBySum(BondSaleMgmtVO vo) throws EgovBizException{
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
         throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
      }
      
      return bondSaleMapper.selectSaleBckInfoListBySum(vo);
   }
   
   @SuppressWarnings("unchecked")
   public List<?> selectScheduledCfList(BondSaleMgmtVO vo) throws EgovBizException{
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
         throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
      }
      
      List<?> aryRslt = bondSaleMapper.selectScheduledCfList(vo);
      
      //v2018.11 PMD 적용 소스 수정
      //bigExpBondAmtSum = new BigDecimal("0");
      BigDecimal bigExpBondAmtSum = new BigDecimal(BigInteger.ZERO);
      
      for(int idx = 0; idx < aryRslt.size(); idx++) {
         bigExpBondAmtSum = bigExpBondAmtSum.add(((Map<String, BigDecimal>) aryRslt.get(idx)).get("expBondAmt"));
      }
      
      return aryRslt;
   }
   
   public BigDecimal getExpBondAmtSum(BondSaleMgmtVO vo) {
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
          throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
       }
       
       List<?> aryRslt = bondSaleMapper.selectScheduledCfList(vo);
       
       //v2018.11 PMD 적용 소스 수정
       //bigExpBondAmtSum = new BigDecimal("0");
       BigDecimal bigExpBondAmtSum = new BigDecimal(BigInteger.ZERO);
       
       for(int idx = 0; idx < aryRslt.size(); idx++) {
          bigExpBondAmtSum = bigExpBondAmtSum.add(((Map<String, BigDecimal>) aryRslt.get(idx)).get("expBondAmt"));
       }
       
       return bigExpBondAmtSum;
   }
   
   public List<?> selectSoldAssetList2(BondSaleMgmtVO vo, Map<String, Object> paramMap) throws EgovBizException{
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
         throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
      }
      
      HashMap<String, String> maskFields = getMaskFields();
      List<?> result = bondSaleMapper.selectSoldAssetList2(vo);
      
      maskingService.setMask(result, maskFields, paramMap);
      
      return result;
   }
   
   public List<?> selectSoldAssetList2BySum(BondSaleMgmtVO vo) throws EgovBizException{
      if(vo.getBondSeqNo() == null || "".equals(vo.getBondSeqNo())){
         throw new MvnoRunException(-1, "판매회차 정보가 없습니다");
      }
      
      return bondSaleMapper.selectSoldAssetList2BySum(vo);
   }
   
   private HashMap<String, String> getMaskFields() {
      HashMap<String, String> maskFields = new HashMap<String, String>();
      
      maskFields.put("subLinkName","CUST_NAME");
      maskFields.put("subscriberNo","MOBILE_PHO");
      maskFields.put("subAdrPrimaryLn","ADDR");
      
      maskFields.put("rtnId", "CUST_NAME"); //처리자명
      maskFields.put("saleCfmId",	"CUST_NAME"); //처리자명
      maskFields.put("saleCnfmId",	"CUST_NAME"); //판매확정자      
      
      return maskFields;
   }
   
}
	
