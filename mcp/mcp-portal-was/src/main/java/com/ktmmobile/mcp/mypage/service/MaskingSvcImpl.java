package com.ktmmobile.mcp.mypage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktmmobile.mcp.mypage.dao.MaskingDao;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;


@Service
public class MaskingSvcImpl implements MaskingSvc {

    @Autowired
    private MaskingDao maskingDao;

	@Override
	public int insertMaskingRelease(MaskingDto maskingDto) {
        return maskingDao.insertMaskingRelease(maskingDto);
	}

	@Override
	public int insertMaskingReleaseHist(MaskingDto maskingDto) {
        return maskingDao.insertMaskingReleaseHist(maskingDto);
	}

}
