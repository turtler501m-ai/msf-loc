package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import com.ktmmobile.msf.domains.form.form.newchange.dto.AbuseImeiHistDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FormCommWriteMapper {
    void insertAbuseImeiHist(AbuseImeiHistDto abuseImeiHistDto);

}
