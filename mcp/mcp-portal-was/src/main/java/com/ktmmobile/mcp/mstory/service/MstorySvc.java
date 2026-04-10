package com.ktmmobile.mcp.mstory.service;

import java.lang.reflect.Array;
import java.util.List;

import org.springframework.ui.Model;

import com.ktmmobile.mcp.mstory.dto.MstoryDto;
import com.ktmmobile.mcp.mstory.dto.MstoryListDto;

public interface MstorySvc {

	List<MstoryDto> getMonthlyUsimList(String yY);

	List<String> getMonthlyUsimYearList();

	MstoryListDto getMonthlyUsimDetail(MstoryDto mstoryDto);

	List<MstoryDto> getSnsList(MstoryDto mstoryDto);
	
	

}
