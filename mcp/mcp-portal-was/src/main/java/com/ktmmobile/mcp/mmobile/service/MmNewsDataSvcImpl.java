package com.ktmmobile.mcp.mmobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.mmobile.dao.MmNewsDataDao;
import com.ktmmobile.mcp.mmobile.dto.NewsDataBasDto;
import com.ktmmobile.mcp.mmobile.dto.NewsDataLinkDto;

@Service
public class MmNewsDataSvcImpl implements MmNewsDataSvc {

	@Autowired
	MmNewsDataDao mmNewsDataDao;

	@Override
	public int countNewsDataList(NewsDataBasDto newsDataBasDto) {
		// TODO Auto-generated method stub
		return mmNewsDataDao.countNewsDataList(newsDataBasDto);
	}

	@Override
	public List<NewsDataBasDto> newsNotiList(NewsDataBasDto newsDataBasDto) {
		return mmNewsDataDao.newsNotiList(newsDataBasDto);
	}

	@Override
	public List<NewsDataBasDto> newsDataList(NewsDataBasDto newsDataBasDto, int skipResult, int maxResult) {
		// TODO Auto-generated method stub
		return mmNewsDataDao.newsDataList(newsDataBasDto, skipResult, maxResult);
	}

	@Override
	public NewsDataBasDto newsDetailNotiSelect(NewsDataBasDto newsDataBasDto) {
		mmNewsDataDao.updateHit(newsDataBasDto);	//카운터 증가
		return mmNewsDataDao.newsDetailNotiSelect(newsDataBasDto);
	}

	@Override
	public List<NewsDataLinkDto> linkListSelect(int newsDatSeq) {
		return mmNewsDataDao.linkListSelect(newsDatSeq);
	}

	@Override
	public NewsDataBasDto newsDetailSelect(NewsDataBasDto newsDataBasDto) {
		mmNewsDataDao.updateHit(newsDataBasDto);	//카운터 증가
		return mmNewsDataDao.newsDetailSelect(newsDataBasDto);
	}

}
