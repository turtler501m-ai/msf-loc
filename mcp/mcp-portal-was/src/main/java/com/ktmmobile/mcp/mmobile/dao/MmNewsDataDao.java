package com.ktmmobile.mcp.mmobile.dao;

import java.util.List;

import com.ktmmobile.mcp.mmobile.dto.NewsDataBasDto;
import com.ktmmobile.mcp.mmobile.dto.NewsDataLinkDto;

public interface MmNewsDataDao {

	public int countNewsDataList(NewsDataBasDto newsDataBasDto);

	public List<NewsDataBasDto> newsNotiList(NewsDataBasDto newsDataBasDto);

	public List<NewsDataBasDto> newsDataList(NewsDataBasDto newsDataBasDto, int skipResult, int maxResult);

	public NewsDataBasDto newsDetailNotiSelect(NewsDataBasDto newsDataBasDto);

	public NewsDataBasDto newsDetailSelect(NewsDataBasDto newsDataBasDto);

	public List<NewsDataLinkDto> linkListSelect(int newsDatSeq);

	public int updateHit(NewsDataBasDto newsDataBasDto);
}
