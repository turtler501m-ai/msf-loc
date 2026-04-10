package com.ktis.msp.rsk.statMgmt.mapper;

import java.util.List;

import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtAddVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtAgncyVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtChnlVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtPrdtVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtRateVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 수정일		수정자		수정내용
 * ---------------------------------------------
 * 2017-12-29	이상직		SRM17122947882(채널별 실적통계)
 *
 */
@Mapper("saleSttcMapper")
public interface SaleSttcMgmtMapper {
	
	public List<SaleSttcMgmtChnlVO> getChnlSaleSttcList(SaleSttcMgmtVO vo);
	
	public List<SaleSttcMgmtRateVO> getFstRateSaleSttcList(SaleSttcMgmtVO vo);
	
	public List<SaleSttcMgmtRateVO> getLstRateSaleSttcList(SaleSttcMgmtVO vo);
	
	public List<SaleSttcMgmtAgncyVO> getAgncySaleSttcList(SaleSttcMgmtVO vo);
	
	// 부가상품가입자조회
	public List<SaleSttcMgmtAddVO> getAddProdList(SaleSttcMgmtAddVO vo);
	
	// 부가상품가입자엑셀다운로드
	public List<SaleSttcMgmtAddVO> getAddProdListExcel(SaleSttcMgmtAddVO vo);
	
	// 부가상품가입이력조회
	public List<SaleSttcMgmtAddVO> getAddProdHistList(SaleSttcMgmtAddVO vo);
	
	// 부가서비스실적조회
	public List<SaleSttcMgmtRateVO> getAddProdSttcList(SaleSttcMgmtVO vo);
	
	// SRM17122947882 채널별영업실적(데이터유형추가)
	public List<SaleSttcMgmtChnlVO> getChnlDataSttcList(SaleSttcMgmtVO vo);
}
