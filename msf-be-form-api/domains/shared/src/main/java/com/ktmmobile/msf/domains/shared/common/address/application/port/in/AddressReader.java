package com.ktmmobile.msf.domains.shared.common.address.application.port.in;

import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressCondition;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressResponse;

public interface AddressReader {

    SearchAddressResponse getListAddress(SearchAddressCondition condition);
}
