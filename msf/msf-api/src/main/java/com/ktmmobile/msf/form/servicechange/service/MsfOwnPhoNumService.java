
package com.ktmmobile.msf.form.servicechange.service;

import java.util.List;

import com.ktmmobile.msf.form.servicechange.dto.OwnPhoNumDto;

public interface MsfOwnPhoNumService {
    /**
    * @Description : 고객이 가입한 모든 회선정보를 가져온다.
    */
	public List<OwnPhoNumDto> selectOwnPhoNumList(OwnPhoNumDto ownPhoNumDto);
	
    /**
    * @Description : 가입번호 조회이력을 저장한다.
    */
	public int insertOwnPhoNumChkHst(OwnPhoNumDto ownPhoNumDto);
}
