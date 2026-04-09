package com.ktmmobile.msf.common.service;

import com.ktmmobile.msf.common.dto.db.BannAccessTxnDto;

/**
 * 배너 통계 서비스 인터페이스 스텁
 */
public interface BannerStatService {

    void insertBannerStat(String bannerId, String menuCode, String ip);

    void insertBannAccessTxn(BannAccessTxnDto bannAccessTxnDto);
}
