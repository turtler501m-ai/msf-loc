package com.ktis.msp.batch.job.org.orgmgmt.mapper;

import com.ktis.msp.batch.job.org.orgmgmt.vo.AcenCnsltSrVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.session.ResultHandler;

@Mapper
public interface AcenCnsltSrMapper {

  void selectAcenCnsltConSrListExcel(AcenCnsltSrVO searchVO, ResultHandler resultHandler);
  void selectAcenCnsltSrListExcel(AcenCnsltSrVO searchVO, ResultHandler resultHandler);
  void selectAcenCnsltSrStatsListExcel(AcenCnsltSrVO searchVO, ResultHandler resultHandler);

}
