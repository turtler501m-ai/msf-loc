package com.ktmmobile.msf.form.servicechange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktmmobile.msf.form.servicechange.dao.MaskingDao;
import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;


@Service
public class MsfMaskingSvcImpl implements MsfMaskingSvc {

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
