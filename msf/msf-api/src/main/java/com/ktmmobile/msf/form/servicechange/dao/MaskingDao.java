package com.ktmmobile.msf.form.servicechange.dao;
import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;

public interface MaskingDao {

    int insertMaskingRelease(MaskingDto maskingDto);
    int insertMaskingReleaseHist(MaskingDto maskingDto);

}
