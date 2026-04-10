package com.ktis.msp.org.csanalyticmgmt.mapper;

import com.ktis.msp.org.csanalyticmgmt.vo.AcenVocAgntVO;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

@Mapper("AcenVocAgntMapper")
public interface AcenVocAgntMapper {

	List<EgovMap> getAcenVocAgntList(AcenVocAgntVO searchVO);

	List<EgovMap> getAcenVocAgntListExcel(AcenVocAgntVO searchVO);

	int getOrgnIdCnt(String orgnId);

	int getUserIdCnt(String usrId);

	int getAcenVocAgntDupCnt(AcenVocAgntVO searchVO);

	int insertAcenVocAgnt(AcenVocAgntVO searchVO);

	int updateAcenVocAgnt(AcenVocAgntVO searchVO);

	List<UserInfoMgmtVo> getAcenVocAgntListByOrgnId(String orgnId);

}
