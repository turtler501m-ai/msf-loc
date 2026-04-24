package com.ktis.msp.batch.job.voc.vocMgmt.mapper;

import java.util.List;

import com.ktis.msp.batch.job.voc.vocMgmt.vo.GiftPayStatVO;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.GiftVocVO;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.VocListMgmtVO;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.VocListNotEgovMapVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.session.ResultHandler;


@Mapper("vocListMgmtMapper")
public interface VocListMgmtMapper {

	public List<VocListNotEgovMapVO> selectVocListExcel(VocListMgmtVO vocListMgmtVO);

	public void getGiftVocListExcel(GiftVocVO searchVO, ResultHandler resultHandler);

	public void getGiftPayStatExcel(GiftPayStatVO searchVO, ResultHandler resultHandler);
}
