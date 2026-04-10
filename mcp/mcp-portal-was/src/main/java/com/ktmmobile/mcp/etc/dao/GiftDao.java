package com.ktmmobile.mcp.etc.dao;

import java.util.List;

public interface GiftDao {

	/** 프로모션 아이디에 해당하는 요금제 혜택요약 시퀀스 조회 */
	List<Long> selectRateGiftPrmtSeqByIdList(List<String> prmtIdList);

}
