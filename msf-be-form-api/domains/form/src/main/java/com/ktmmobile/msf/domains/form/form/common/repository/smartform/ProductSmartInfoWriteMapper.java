package com.ktmmobile.msf.domains.form.form.common.repository.smartform;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.newchange.dto.ProductInventoryRequest;

@AutoAuditing
@Mapper
public interface ProductSmartInfoWriteMapper {

    //매장 재고 상태변경
    void updateMsfProdStorInventoryTxn(ProductInventoryRequest request);
}
