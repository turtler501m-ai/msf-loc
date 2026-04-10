package com.ktmmobile.mcp.mstory.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ktmmobile.mcp.mstory.dao.MstoryDao;
import com.ktmmobile.mcp.mstory.dto.MstoryDto;
import com.ktmmobile.mcp.mstory.dto.MstoryListDto;

@Service
public class MstorySvcImpl implements MstorySvc{

	@Autowired
	MstoryDao mstoryDao;

	@Override
	public List<MstoryDto> getMonthlyUsimList(String YY) {
		String yy = YY;
		if(Optional.ofNullable(yy).isEmpty()) {
			LocalDate now = LocalDate.now();
			int year = now.getYear();
			yy = Integer.toString(year).substring(2,4);
		}
		
		return mstoryDao.getMonthlyUsimList(yy);
	}

	@Override
	public List<String> getMonthlyUsimYearList() {

		return mstoryDao.getMonthlyUsimYearList();
	}

	@Override
	public MstoryListDto getMonthlyUsimDetail(MstoryDto mstoryDto) {
		MstoryListDto mstoryListDto = new MstoryListDto();
		mstoryListDto.setMstoryDto(mstoryDao.getMonthlyUsimDetail(mstoryDto));
		mstoryListDto.setMstoryAttDtoList(mstoryDao.getMonthlyUsimAttList(mstoryDto));
		mstoryListDto.setMstoryDetailList(mstoryDao.getMonthlyUsimDetailList(mstoryDto));
		mstoryDao.modifyMonthlyUsimHit(mstoryDto);
		return mstoryListDto;
	}

	@Override
	public List<MstoryDto> getSnsList(MstoryDto mstoryDto) {

		return mstoryDao.getSnsList(mstoryDto);
	}
	
}
