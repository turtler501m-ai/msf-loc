package com.ktmmobile.msf.domains.shared.form.common.application.port.in;

import com.ktmmobile.msf.domains.shared.form.common.application.dto.SearchAddressCondition;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.SearchAddressResponse;

public interface AddressReader {

    SearchAddressResponse getListAddress(SearchAddressCondition condition);
}
