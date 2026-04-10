package com.ktmmobile.msf.domains.form.form.servicechange.service;

import com.ktmmobile.msf.domains.form.form.servicechange.dto.MaskingDto;


public interface MsfMaskingSvc {

    int insertMaskingRelease(MaskingDto maskingDto);
    int insertMaskingReleaseHist(MaskingDto maskingDto);

}
