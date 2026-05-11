package com.ktmmobile.msf.domains.cache.agency.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

public interface AgencyRepository {

    List<Agency> findAllActiveAgencies();
}
