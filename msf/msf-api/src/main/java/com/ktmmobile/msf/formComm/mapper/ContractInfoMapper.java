package com.ktmmobile.msf.formComm.mapper;

import com.ktmmobile.msf.formComm.dto.ContractInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * M전산 계약정보 조회 Mapper. ContractInfoMapper.xml 참조.
 * ASIS MypageMapper.xml selectContractNum 과 동일 목적.
 */
@Mapper
public interface ContractInfoMapper {

    /**
     * 고객명 + 전화번호로 계약번호(ncn), 고객ID(custId) 조회.
     * MSP_JUO_SUB_INFO 테이블 (M전산 DB).
     */
    ContractInfoDto selectContractInfo(@Param("name") String name, @Param("ctn") String ctn);

    /**
     * 계약번호(ncn)로 청구계정ID(BAN) 조회.
     * ASIS: MypageMapper.xml selectBanSel (SELECT BAN FROM MSP_JUO_SUB_INFO WHERE CONTRACT_NUM).
     */
    String selectBanByContractNum(@Param("contractNum") String contractNum);

}
