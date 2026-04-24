
package com.ktis.msp.batch.job.dis.dismgmt.mapper;

import com.ktis.msp.batch.job.dis.dismgmt.vo.DisVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface DisTrgMapper {
	
	/**MSP_DIS_TRG_DTL 대상 추출하여 PROC_YN T로 udpate*/
	int updateTrgDtlT(DisVO disVo);
	
	/**MSP_DIS_TRG_DTL PROC_YN이 T인 데이터 중 max 1개를 MSP_DIS_TRG_MST에 insert*/
	int inserTrgMst(DisVO disVo);
	
	/**MSP_DIS_TRG_DTL PROC_YN이 T인 데이터를 Y로 update*/
	int updateTrgDtlY(DisVO disVo);

	/**공통코드 평생할인 기준일자 조회  */
	String getBaseDate();
}