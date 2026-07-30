package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeMpHC0Response;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeMpPC0Response;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;

@Mapper
public interface NewChangeMpReadMapper {

    //개통전 사전체크 - PC0 및 FPC0
    NewChangeMpPC0Response selectMsfRequestPC0Request(NewChangeRequest request);

    //개통전 사전체크 - HC0 및 FHC0
    NewChangeMpHC0Response selectMsfRequestHC0InfoRequest(NewChangeRequest request);

    //개통전 사전체크 - 부가서비스
    //MsfRequestAdditionVo selectMsfRequestAdditionVo(Long requestKey);
    List<MsfRequestAdditionVo> selectMsfRequestAdditionVo(Long requestKey);

    //개통전 사전체크 (기기변경) 을 위해 신청서 데이타 조회
    NewChangeRequest selectMsfPreCheckInfoRequest(Long requestKey);

}
