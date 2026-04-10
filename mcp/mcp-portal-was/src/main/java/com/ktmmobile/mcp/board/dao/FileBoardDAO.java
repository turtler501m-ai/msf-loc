package com.ktmmobile.mcp.board.dao;

import com.ktmmobile.mcp.board.dto.FileBoardDTO;
import com.ktmmobile.mcp.board.dto.FileDownloadDto;


public interface FileBoardDAO {
	
	public FileDownloadDto selectFileDownload(int attrSeq);

	FileBoardDTO selectFileInfo(int boardSeq);
}
