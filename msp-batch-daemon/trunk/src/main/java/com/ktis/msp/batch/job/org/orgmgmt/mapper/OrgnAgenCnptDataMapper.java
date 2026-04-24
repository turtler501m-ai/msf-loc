package com.ktis.msp.batch.job.org.orgmgmt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rsk.statmgmt.vo.StatMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("orgnAgenCnptMapper")
public interface OrgnAgenCnptDataMapper {
	
	/**
	 * 대리점 연동 프로시저 호출
	 * @return
	 */
	public void callOrgnAgenData(Map<String, Object> param);
	
	/**
	 * 판매점 연동 및 선불대리점 프로시저 호출
	 * @return
	 */
	public void callOrgnCnptData(Map<String, Object> param);
	
	/**
	 * 대리점 테이블 삭제
	 * @return
	 */
	public void deleteOrgAgenData(Map<String, Object> param);
	
	/**
	 * 판매점 테이블 삭제
	 * @return
	 */
	public void deleteOrgCpntData(Map<String, Object> param);
	
	/**
	 * 접점코드 테이블 삭제
	 * @return
	 */
	public void deleteOrgRelData(Map<String, Object> param);
	
}
