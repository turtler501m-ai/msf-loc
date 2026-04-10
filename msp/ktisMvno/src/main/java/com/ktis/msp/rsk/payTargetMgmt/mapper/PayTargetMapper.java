package com.ktis.msp.rsk.payTargetMgmt.mapper;
import java.util.List;
import com.ktis.msp.rsk.payTargetMgmt.vo.PayTargetVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("payTargetMapper")
public interface PayTargetMapper {

	/**
	 * @Description : 납부대상 리스트 조회
	 * @Param  : payTargetVO
	 * @Return : List<?>
	 * @Author : 박준성
	 * @Create Date : 2016.10.14
	 */
    List<?> payTargetMgmtList(PayTargetVO payTargetVO);
    
	/**
	 * @Description : 납부대상리스트를 조회한다. 엑셀용
	 * @Param  : payTargetVO
	 * @Return : List<?>
	 * @Author : 박준성
	 * @Create Date : 2016.10.14
	 */
    List<?> payTargetMgmtEx(PayTargetVO payTargetVO);    
}
