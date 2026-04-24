package com.ktis.msp.batch.job.org.prmtmgmt.mapper;

import com.ktis.msp.batch.job.org.orgmgmt.vo.AcenCnsltMstCanVO;
import com.ktis.msp.batch.job.org.prmtmgmt.vo.EventCodePrmtVO;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.GiftVocVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.session.ResultHandler;

import java.util.List;


@Mapper("EventCodePrmtMapper")
public interface EventCodePrmtMapper {
    public void getEventCodePrmtListExcel(EventCodePrmtVO searchVO, ResultHandler resultHandler);

}
