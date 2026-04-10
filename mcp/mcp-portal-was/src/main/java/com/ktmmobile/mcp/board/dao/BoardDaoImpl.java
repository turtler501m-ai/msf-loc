package com.ktmmobile.mcp.board.dao;

import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.common.dto.db.NmcpBoardBasDto;

@Repository
public class BoardDaoImpl implements BoardDao {

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int getTotalCount(BoardDto boardDto) {

        return (Integer)sqlSessionTemplate.selectOne("boardMapper.getTotalCount",boardDto);
    }

    @Override
    public List<BoardDto> selectBoardList(BoardDto boardDto) {

        return sqlSessionTemplate.selectList("boardMapper.selectBoardList",boardDto);
    }

    @Override
    public int selectInsert(BoardDto boardDto) {

        sqlSessionTemplate.insert("boardMapper.selectInsert",boardDto);
        return (Integer) boardDto.getBoardSeq();
    }

    @Override
    public BoardDto selectBoardArticle(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectOne("boardMapper.selectBoard",boardDto);
    }

    @Override
    public int updateBoard(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return (Integer)sqlSessionTemplate.update("boardMapper.updateBoard",boardDto);
    }

    @Override
    public int deleteBoard(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return (Integer)sqlSessionTemplate.delete("boardMapper.deleteBoard",boardDto);
    }

    @Override
    public List<HashedMap> selectGroupCode(String gId) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("boardMapper.selectGroupCode",gId);
    }

    @Override
    public List<BoardDto> selectNotiesList(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("boardMapper.selectNotiesList",boardDto);
    }

    @Override
    public List<BoardDto> selectFileboardList(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("boardMapper.selectFileboardList",boardDto);
    }


    @Override
    public int getDownloadCount(int boardSeq) {

        Object resultObj = sqlSessionTemplate.selectOne("boardMapper.getDownloadCount",boardSeq);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "boardMapper.getDownloadCount"));
        }
    }


    @Override
    public int countBylistBoardBas(NmcpBoardBasDto NmcpBoardBasDto) {

        Object resultObj = sqlSessionTemplate.selectOne("boardMapper.countBylistBoardBas",NmcpBoardBasDto);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "boardMapper.countBylistBoardBas"));
        }
    }


    @Override
    public List<NmcpBoardBasDto> listBoardBas(NmcpBoardBasDto NmcpBoardBasDto, int skipResult, int maxResult) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("boardMapper.listBoardBas",NmcpBoardBasDto,new RowBounds(skipResult, maxResult));
    }

    

}
