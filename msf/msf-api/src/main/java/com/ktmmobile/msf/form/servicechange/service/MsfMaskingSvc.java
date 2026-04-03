package com.ktmmobile.msf.form.servicechange.service;

import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;


public interface MsfMaskingSvc {

    int insertMaskingRelease(MaskingDto maskingDto);
    int insertMaskingReleaseHist(MaskingDto maskingDto);

}
