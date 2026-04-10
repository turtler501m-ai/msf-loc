package com.ktis.msp.rsk.statMgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.rsk.statMgmt.mapper.SaleSttcMgmtMapper;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtAddVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtAgncyVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtChnlVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtPrdtVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtRateVO;
import com.ktis.msp.rsk.statMgmt.vo.SaleSttcMgmtVO;

/**
 * 수정일		수정자		수정내용
 * ---------------------------------------------
 * 2017-12-29	이상직		SRM17122947882(채널별 실적통계)
 *
 */

@Service
public class SaleSttcMgmtService extends BaseService {
	
	@Autowired
	private SaleSttcMgmtMapper saleSttcMapper;
	
	/**
	 * 채널별실적조회
	 * @param vo
	 * @return
	 */
	public List<SaleSttcMgmtChnlVO> getChnlSaleSttcList(SaleSttcMgmtVO vo){
		
		if(vo.getStdrDt() == null || "".equals(vo.getStdrDt())){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		if(vo.getStdrTm() == null || "".equals(vo.getStdrTm())){
			throw new MvnoRunException(-1, "기준시간이 존재하지 않습니다");
		}
		
		List<SaleSttcMgmtChnlVO> list = saleSttcMapper.getChnlSaleSttcList(vo);
		
		return list;
	}
	
	/**
	 * 최초요금제별실적조회
	 * @param vo
	 * @return
	 */
	public List<SaleSttcMgmtRateVO> getFstRateSaleSttcList(SaleSttcMgmtVO vo){
		
		if(vo.getStdrDt() == null || "".equals(vo.getStdrDt())){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		if(vo.getStdrTm() == null || "".equals(vo.getStdrTm())){
			throw new MvnoRunException(-1, "기준시간이 존재하지 않습니다");
		}
		
		List<SaleSttcMgmtRateVO> list = saleSttcMapper.getFstRateSaleSttcList(vo);
		
		return list;
	}
	
	/**
	 * 현재요금제별실적조회
	 * @param vo
	 * @return
	 */
	public List<SaleSttcMgmtRateVO> getLstRateSaleSttcList(SaleSttcMgmtVO vo){
		
		if(vo.getStdrDt() == null || "".equals(vo.getStdrDt())){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		if(vo.getStdrTm() == null || "".equals(vo.getStdrTm())){
			throw new MvnoRunException(-1, "기준시간이 존재하지 않습니다");
		}
		
		List<SaleSttcMgmtRateVO> list = saleSttcMapper.getLstRateSaleSttcList(vo);
		
		return list;
	}
	
	/**
	 * 대리점별실적조회
	 * @param vo
	 * @return
	 */
	public List<SaleSttcMgmtAgncyVO> getAgncySaleSttcList(SaleSttcMgmtVO vo){
		
		if(vo.getStdrDt() == null || "".equals(vo.getStdrDt())){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		if(vo.getStdrTm() == null || "".equals(vo.getStdrTm())){
			throw new MvnoRunException(-1, "기준시간이 존재하지 않습니다");
		}
		
		List<SaleSttcMgmtAgncyVO> list = saleSttcMapper.getAgncySaleSttcList(vo);
		
		return list;
	}
	
	/**
	 * 부가서비스가입자조회
	 * @param vo
	 * @return
	 */
	public List<SaleSttcMgmtAddVO> getAddProdList(SaleSttcMgmtAddVO vo){
		
			if(vo.getSearchGbn() == null || "".equals(vo.getSearchGbn())){
				if("0".equals(vo.getAllDt())){
					if(vo.getStrtDt() == null || "".equals(vo.getStrtDt())){
						throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다");
					}
					if(vo.getEndDt() == null || "".equals(vo.getEndDt())){
						throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다");
					}
				}else{
					throw new MvnoRunException(-1, "전체기간 조회시 검색구분을 설정해야 합니다.");
				}
			}else{
				if(vo.getSearchName() == null || "".equals(vo.getSearchName())){
					throw new MvnoRunException(-1, "검색내용이 존재하지 않습니다");
				}
			}
		
		List<SaleSttcMgmtAddVO> list = saleSttcMapper.getAddProdList(vo);
		
		return list;
	}
	
	/**
	 * 부가서비스가입자조회 엑셀 다운
	 * @param vo
	 * @return
	 */
	public List<SaleSttcMgmtAddVO> getAddProdListExcel(SaleSttcMgmtAddVO vo){
		
		if(vo.getSearchGbn() == null || "".equals(vo.getSearchGbn())){
			if(vo.getStrtDt() == null || "".equals(vo.getStrtDt())){
				throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다");
			}
			if(vo.getEndDt() == null || "".equals(vo.getEndDt())){
				throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다");
			}
		}else{
			if(vo.getSearchName() == null || "".equals(vo.getSearchName())){
				throw new MvnoRunException(-1, "검색내용이 존재하지 않습니다");
			}
		}
		
		
		List<SaleSttcMgmtAddVO> list = saleSttcMapper.getAddProdListExcel(vo);
		
		return list;
	}
	
	/**
	 * 부가서비스가입자상세조회
	 * @param vo
	 * @return
	 */
	public List<SaleSttcMgmtAddVO> getAddProdHistList(SaleSttcMgmtAddVO vo){
		
		if(vo.getContractNum() == null || "".equals(vo.getContractNum())){
			throw new MvnoRunException(-1, "계약번호가 존재하지 않습니다");
		}
		
		List<SaleSttcMgmtAddVO> list = saleSttcMapper.getAddProdHistList(vo);
		
		return list;
	}
	
	/**
	 * 부가서비스실적조회
	 * @param vo
	 * @return
	 */
	public List<SaleSttcMgmtRateVO> getAddProdSttcList(SaleSttcMgmtVO vo){
		
		if(vo.getStdrDt() == null || "".equals(vo.getStdrDt())){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		if(vo.getStdrTm() == null || "".equals(vo.getStdrTm())){
			throw new MvnoRunException(-1, "기준시간이 존재하지 않습니다");
		}
		
		List<SaleSttcMgmtRateVO> list = saleSttcMapper.getAddProdSttcList(vo);
		
		return list;
	}
	
	/**
	 * SRM17122947882
	 * 채널별영업실적 데이터유형 추가
	 * @param vo
	 * @return
	 */
	public List<SaleSttcMgmtChnlVO> getChnlDataSttcList(SaleSttcMgmtVO vo){
		
		if(vo.getStdrDt() == null || "".equals(vo.getStdrDt())){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		if(vo.getStdrTm() == null || "".equals(vo.getStdrTm())){
			throw new MvnoRunException(-1, "기준시간이 존재하지 않습니다");
		}
		
		List<SaleSttcMgmtChnlVO> list = saleSttcMapper.getChnlDataSttcList(vo);
		
		return list;
	}
	
}
