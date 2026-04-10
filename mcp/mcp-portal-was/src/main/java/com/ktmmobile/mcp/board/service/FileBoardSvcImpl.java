package com.ktmmobile.mcp.board.service;

import com.ktmmobile.mcp.board.dto.FileBoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.board.dao.FileBoardDAO;
import com.ktmmobile.mcp.board.dto.FileDownloadDto;



@Service
public class FileBoardSvcImpl implements FileBoardSvc{

	@Autowired
	FileBoardDAO fileBoardDao;


	@Override
	public FileDownloadDto selectFileDownload(int attrSeq) {
		return fileBoardDao.selectFileDownload(attrSeq);
	}

	@Override
	public FileBoardDTO selectFileInfo(int boardSeq) {
		return fileBoardDao.selectFileInfo(boardSeq) ;
	}
}
