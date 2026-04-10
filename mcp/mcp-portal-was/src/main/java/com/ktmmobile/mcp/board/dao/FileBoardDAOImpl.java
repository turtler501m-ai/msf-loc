package com.ktmmobile.mcp.board.dao;

import com.ktmmobile.mcp.board.dto.FileBoardDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.board.dto.FileDownloadDto;

@Repository
public class FileBoardDAOImpl implements FileBoardDAO{

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;


	@Override
	public FileDownloadDto selectFileDownload(int attrSeq) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("FileBoardMapper.selectFileDownload", attrSeq);
	}

	@Override
	public FileBoardDTO selectFileInfo(int boardSeq) {
		return sqlSessionTemplate.selectOne("FileBoardMapper.selectFileInfo", boardSeq);
	}
}
