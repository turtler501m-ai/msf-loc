package com.ktmmobile.msf.domains.form.form.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.form.common.dto.MaskingDto;
import com.ktmmobile.msf.domains.form.form.common.repository.CommonMaskingDaoImpl;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaskingService {

    private final CommonMaskingDaoImpl maskingDao;

    public int insertMaskingRelease(MaskingDto maskingDto) {
        try {
            return maskingDao.insertMaskingRelease(maskingDto);
        } catch (Exception e) {
            log.warn("Failed to insert masking release.", e);
            return 0;
        }
    }

    public int insertMaskingReleaseHist(MaskingDto maskingDto) {
        try {
            return maskingDao.insertMaskingReleaseHist(maskingDto);
        } catch (Exception e) {
            log.warn("Failed to insert masking release history.", e);
            return 0;
        }
    }
}
