package com.ktmmobile.mcp.mstory.dao;

import java.util.List;

import com.ktmmobile.mcp.mstory.dto.MstoryAttDto;
import com.ktmmobile.mcp.mstory.dto.MstoryDto;

public interface MstoryDao {

	List<MstoryDto> getMonthlyUsimList(String yY);

	List<String> getMonthlyUsimYearList();

	MstoryDto getMonthlyUsimDetail(MstoryDto mstoryDto);

	List<MstoryAttDto> getMonthlyUsimAttList(MstoryDto mstoryDto);

	List<MstoryDto> getMonthlyUsimDetailList(MstoryDto mstoryDto);

	void modifyMonthlyUsimHit(MstoryDto mstoryDto);

	List<MstoryDto> getSnsList(MstoryDto mstoryDto);

}
