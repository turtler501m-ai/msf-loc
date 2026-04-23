package com.ktmmobile.msf.domains.shared.common.address.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressCondition;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressResponse;
import com.ktmmobile.msf.domains.shared.common.address.application.port.in.AddressReader;

@RestController
@RequestMapping("/api/shared/common/address")
@RequiredArgsConstructor
public class SearchAddressController {

    private final AddressReader addressReader;

    @PostMapping("/list")
    public CommonResponse<SearchAddressResponse> getListAddress(@RequestBody @Valid SearchAddressCondition condition) {
        return ResponseUtils.ok(addressReader.getListAddress(condition));
    }
}
