package com.ktmmobile.msf.domains.form.form.servicechange.repository;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MaskingDto;

public interface MaskingDao {

    int insertMaskingRelease(MaskingDto maskingDto);
    int insertMaskingReleaseHist(MaskingDto maskingDto);

}
