package com.ktis.msp.batch.job.voc.vocMgmt.mapper;

import java.util.List;

import com.ktis.msp.batch.job.voc.vocMgmt.vo.VocListMgmtVO;
import com.ktis.msp.batch.job.voc.vocMgmt.vo.VocListNotEgovMapVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


@Mapper("vocListMgmtMapper")
public interface VocListMgmtMapper {

	public List<VocListNotEgovMapVO> selectVocListExcel(VocListMgmtVO vocListMgmtVO);
}
