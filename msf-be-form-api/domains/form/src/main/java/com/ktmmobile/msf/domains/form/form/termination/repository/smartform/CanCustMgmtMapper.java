package com.ktmmobile.msf.domains.form.form.termination.repository.smartform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.DetailDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessReqDto;

@Mapper
public interface CanCustMgmtMapper {

    int selectAppFormListCount(ListReqDto req);

    List<DetailDto> selectAppFormList(ListReqDto req);

    DetailDto selectCanCustDetail(Long requestKey);

    String selectProcCd(Long requestKey);

    int updateCanCustProcCd(ProcessReqDto req);
}
