package com.ktmmobile.msf.main.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.board.dto.BoardDto;
import com.ktmmobile.msf.main.dto.InquiryBoardDto;

@Repository
public class CsReqFormDaoImpl implements CsReqFormDao {
    private final Logger logger  = LoggerFactory.getLogger(CsReqFormDaoImpl.class);

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
	public List<BoardDto> selectNoticeBoard(BoardDto boardDto) {
    	return sqlSessionTemplate.selectList("CsReqFormMapper.selectNoticeBoard",boardDto);
	}

	@Override
	public List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult) {
		return sqlSessionTemplate.selectList("CsReqFormMapper.selectBoardList", boardDto, new RowBounds(skipResult, maxResult));
	}

	@Override
    public int getTotalCount(BoardDto boardDto) {
        return (Integer)sqlSessionTemplate.selectOne("CsReqFormMapper.getTotalCount",boardDto);
    }

    @Override
    public void reqFormDownloadCountUpdate(BoardDto boardDto) {

        int updateCnt = 0;
        if ("STPLT".equals(boardDto.getFileDownFlag())) {
            updateCnt = sqlSessionTemplate.update("CsReqFormMapper.reqFormDownloadCountUpdateStplt",boardDto);
        } else {
            updateCnt = sqlSessionTemplate.update("CsReqFormMapper.reqFormDownloadCountUpdate",boardDto);
        }

    }

}
