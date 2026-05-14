package com.ktmmobile.msf.domains.form.form.termination.repository.smartform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.DetailDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessStatusDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessUpdateDto;

@Mapper
public interface CanCustMgmtMapper {

    int selectAppFormListCount(ListReqDto req);

    List<DetailDto> selectAppFormList(ListReqDto req);

    DetailDto selectCanCustDetail(Long requestKey);

    DetailDto selectApplicationDetail(Long requestKey);

    ProcessStatusDto selectApplicationStatus(Long requestKey);

    String selectProcCd(Long requestKey);

    @AutoAuditing
    int updateCanCustProcCd(ProcessUpdateDto req);
}
