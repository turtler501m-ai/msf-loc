package com.ktis.msp.pps.sucmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.pps.sucmgmt.vo.SucVo;
import com.ktis.msp.rcp.custMgmt.vo.CustListVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("SucMgmtMapper")
public interface SucMgmtMapper {
	public abstract List<?> getSucList(SucVo sucVo);
	public abstract SucVo getSucInfo(SucVo sucVo);
	public abstract void updateSsate(SucVo sucVo);
	public abstract int deleteSellUsim(SucVo sucVo);
	public abstract List<?> getExcelList(SucVo sucVo);
	void procPpsOpenUsimDone(Map<String, Object> resultMap);
	public abstract List<?> getSucStatisticsListAjax(SucVo sucVo);
}
