package com.ktis.msp.voc.vocMgmt.mapper;

import com.ktis.msp.voc.vocMgmt.vo.AcenVocVo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

@Mapper("AcenVocMapper")
public interface AcenVocMapper {

	List<EgovMap> getAcenVocList(AcenVocVo searchVO);

	List<EgovMap> getAcenVocListExcel(AcenVocVo searchVO);

	List<EgovMap> getAcenVocHistList(AcenVocVo searchVO);

	List<EgovMap> getAcenVocHistListExcel(AcenVocVo searchVO);

	AcenVocVo getAcenVocInfo(AcenVocVo searchVO);

	int updateAcenVocInfo(AcenVocVo searchVO);

	int insertAcenVocHist(String reqSeq);

}
