package com.ktmmobile.msf.domains.form.form.common.repository.smartform;

import com.ktmmobile.msf.domains.form.form.common.vo.MsfUploadPhoneInfoVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MsfWriteMapper {

    int insertMsfUploadPhoneInfo(MsfUploadPhoneInfoVo msfUploadPhoneInfoVo);
}
