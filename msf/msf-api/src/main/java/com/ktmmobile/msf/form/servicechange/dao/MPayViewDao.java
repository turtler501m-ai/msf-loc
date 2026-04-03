/**
 *
 */
package com.ktmmobile.msf.form.servicechange.dao;

import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.form.servicechange.dto.MPayViewDto;


public interface MPayViewDao {

	public List<MPayViewDto> selectMPayList(MPayViewDto mPayViewDto);

	public List<Map<String, String>> getDateListFromOpening();

	public String getMpayAgree(MPayViewDto mPayViewDto);

	public String getMpayAdjAmt(MPayViewDto mPayViewDto);

	public String getLastTmonLmtAmt(MPayViewDto mPayViewDto);


}
