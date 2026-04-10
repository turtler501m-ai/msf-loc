/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ktis.msp.ptnr.ptnrmgmt.mapper;

import java.util.List;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrInsrMemberVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ptnrInsrMemberMapper")
public interface PtnrInsrMemberMapper {

	/**
     * @Description : 보험가입자정보를 조회한다.
     * @Param  : 
     * @Return : List<?>
     * @Author : 권오승
     * @Create Date : 2018. 6. 05.
     */
	List<?> getInsrMemberList(PtnrInsrMemberVO vo);
	
	/**
     * @Description : 보험가입자 상세정보를 조회한다.
     * @Param  : 
     * @Return : List<?>
     * @Author : 권오승
     * @Create Date : 2018. 6. 05.
     */
	List<?>getInsrHistory(PtnrInsrMemberVO vo);
	
    /**
     * @Description : 동부 요금제를 조회한다. SELECT BOX SET
     * @Param  : 
     * @Return : List<?>
     * @Author : 권오승
     * @Create Date : 2018. 6. 05.
     */
    List<?> selectDongbuRate(PtnrInsrMemberVO vo);
    
    

}
