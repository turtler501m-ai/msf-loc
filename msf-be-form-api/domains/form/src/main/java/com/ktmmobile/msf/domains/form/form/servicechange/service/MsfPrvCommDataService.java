
package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.util.List;

import com.ktmmobile.msf.domains.form.common.dto.db.MspCommDatPrvTxnDto;

public interface MsfPrvCommDataService {
    /**
    * @Description : 통신자료 제공내역 요청리스트 가져오기
    */
    public List<MspCommDatPrvTxnDto> pvcCommDataList(MspCommDatPrvTxnDto mspCommDatPrvTxnDto);
    
    /**
    * @Description : 통신자료 제공내역 신청 insert
    */
    public int insertPrvCommData(MspCommDatPrvTxnDto mspCommDatPrvTxnDto);
    
    /**
    * @Description : 통신자료 제공내역상세 출력정보 가져오기
    */
    public List<MspCommDatPrvTxnDto> pvcCommDataDtlList(MspCommDatPrvTxnDto mspCommDatPrvTxnDto);
}
