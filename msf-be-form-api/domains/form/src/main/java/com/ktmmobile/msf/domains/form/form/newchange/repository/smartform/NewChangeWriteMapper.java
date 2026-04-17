package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;


@Mapper
public interface NewChangeWriteMapper {

    void insertMsfRequest(NewChangeInfoRequest request);

    void insertMsfRequestAgent(NewChangeInfoRequest request);

    void insertMsfRequestCstmr(NewChangeInfoRequest request);

    void insertMsfRequestSale(NewChangeInfoRequest request);

    void insertMsfRequestBillReq(NewChangeInfoRequest request);

    void updateMsfRequest(NewChangeInfoRequest request);

    void updateMsfRequestAgent(NewChangeInfoRequest request);

    void updateMsfRequestCstmr(NewChangeInfoRequest request);

    void updateMsfRequestSale(NewChangeInfoRequest request);

    void updateMsfRequestBillReq(NewChangeInfoRequest request);

}
