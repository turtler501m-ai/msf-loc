package com.ktmmobile.msf.domains.form.form.servicechange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktmmobile.msf.domains.form.form.servicechange.dao.MaskingDao;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MaskingDto;


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
