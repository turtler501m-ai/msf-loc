package com.ktmmobile.msf.domains.form.form.termination.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.DetailDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessReqDto;
import com.ktmmobile.msf.domains.form.form.termination.repository.smartform.CanCustMgmtMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CanCustMgmtRepositoryImpl {

    private final CanCustMgmtMapper canCustMgmtMapper;

    public int selectAppFormListCount(ListReqDto req) {
        return canCustMgmtMapper.selectAppFormListCount(req);
    }

    public List<DetailDto> selectAppFormList(ListReqDto req) {
        return canCustMgmtMapper.selectAppFormList(req);
    }

    public DetailDto selectCanCustDetail(Long requestKey) {
        return canCustMgmtMapper.selectCanCustDetail(requestKey);
    }

    public String selectProcCd(Long requestKey) {
        return canCustMgmtMapper.selectProcCd(requestKey);
    }

    public int updateCanCustProcCd(ProcessReqDto req) {
        return canCustMgmtMapper.updateCanCustProcCd(req);
    }
}
