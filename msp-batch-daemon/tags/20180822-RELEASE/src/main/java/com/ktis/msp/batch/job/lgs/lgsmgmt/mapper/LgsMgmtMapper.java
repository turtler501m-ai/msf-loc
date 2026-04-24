package com.ktis.msp.batch.job.lgs.lgsmgmt.mapper;

import com.ktis.msp.batch.job.lgs.lgsmgmt.vo.LgsMgmtVO;
import com.ktis.msp.batch.job.lgs.lgsmgmt.vo.LgsPrdtSrlMstVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name  : LgsMgmtMapper.java
 * @Description : LgsMgmtMapper Class
 * @Modification Information
 * @
 * @  수정일	  수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.08.24	 IB	  최초생성
 *
 * @author IB
 * @since 2014. 08.24
 * @version 1.0
 * @see
 *
 */
@Mapper("lgsMgmtMapper")
public interface LgsMgmtMapper {
	
	/**
	 * DB일자조회
	 * @Return : String
	 */
	String getDbSysDate(int day);
	
	/**
	 * 시퀀스조회
	 * @Return : String
	 */
	String getInvtSrl(String str);
	
	/**
	 * 기기단가조회
	 * @Param  : LgsMgmtVO
	 * @Return : LgsMgmtVO
	 */
	LgsMgmtVO getIntmAmtInfo(LgsMgmtVO vo);
	
	/**
	 * 제품시리얼마스터 변경
	 * @Param  : LgsPrdtSrlMstVo
	 * @Return : int
	 */
	int updateLgsPrdtSrlMst(LgsPrdtSrlMstVo invo);
	
	
	/**
	 * 타사단말 제품시리얼마스터 변경
	 * @Param  : LgsPrdtSrlMstVo
	 * @Return : int
	 */
	int updateLgsOtcpSrlMst(LgsPrdtSrlMstVo invo);
	
	/**
	 * 재고수량변경
	 * @param vo
	 * @return
	 */
	int updateInvtrDayMst(LgsMgmtVO vo);
	
	
	/**
	 * 타사재고수량변경
	 * @param vo
	 * @return
	 */
	int mgrOtcpInvMst(LgsMgmtVO vo);
	
	
	/**
	 * 재고수량등록
	 * @param vo
	 * @return
	 */
	int insertInvtrDayMst(LgsMgmtVO vo);
	
	/**
	 * @Description : LGS_PRDT_SRL_MST 테이블 스냅샷
	 * @Param  : 
	 * @Return : void
	 * @Author : 심정보
	 * @Create Date : 2015. 10. 14.
	 */
	void insertPrdtSnapshot(LgsPrdtSrlMstVo vo);
	
}
