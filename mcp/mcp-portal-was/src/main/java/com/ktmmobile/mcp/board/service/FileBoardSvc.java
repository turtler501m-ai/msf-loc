package com.ktmmobile.mcp.board.service;

import com.ktmmobile.mcp.board.dto.FileBoardDTO;
import com.ktmmobile.mcp.board.dto.FileDownloadDto;


public interface FileBoardSvc {

	public FileDownloadDto selectFileDownload(int attrSeq);

	FileBoardDTO selectFileInfo(int boardSeq);
}
