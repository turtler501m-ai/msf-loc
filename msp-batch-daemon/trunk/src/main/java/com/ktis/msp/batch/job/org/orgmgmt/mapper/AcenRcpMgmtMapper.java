package com.ktis.msp.batch.job.org.orgmgmt.mapper;

import com.ktis.msp.batch.job.org.orgmgmt.vo.AcenRcpMgmtVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.session.ResultHandler;

@Mapper
public interface AcenRcpMgmtMapper {

  void selectAcenRcpMgmtListExcel(AcenRcpMgmtVO searchVO, ResultHandler resultHandler);

  void selectAcenRcpMgmtFailListExcel(AcenRcpMgmtVO searchVO, ResultHandler resultHandler);

}
