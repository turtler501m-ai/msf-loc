package com.ktmmobile.msf.form.servicechange.dao;

import java.util.List;

public interface RegSvcDao {
    /**
     * @Description: 로밍 부가서비스 요금제코드 조회
     * @param 
     * @return List<String>
     */
    public List<String> getRoamCdList();
}