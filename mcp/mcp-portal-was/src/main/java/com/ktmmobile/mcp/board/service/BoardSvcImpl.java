package com.ktmmobile.mcp.board.service;

import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.mcp.board.dao.BoardDao;
import com.ktmmobile.mcp.board.dao.FileBoardDAO;
import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.board.dto.FileBoardDTO;
import com.ktmmobile.mcp.common.dto.db.NmcpBoardBasDto;

@Service
public class BoardSvcImpl implements BoardService{

    @Autowired
    BoardDao boardDao;

    @Autowired
    FileBoardDAO fileBoardDao;

    private static Logger logger = LoggerFactory.getLogger(BoardSvcImpl.class);

    @Override
    public int getTotalCount(BoardDto boardDto) {

        int getTotalCount = boardDao.getTotalCount(boardDto);

        return getTotalCount;
    }

    @Override
    public List<BoardDto> selectBoardList(BoardDto boardDto) {


        List<BoardDto> selectBoardList = boardDao.selectBoardList(boardDto);

        return selectBoardList;
    }

    @Override
    public int selectInsert(BoardDto boardDto) {

        int selectInsert = boardDao.selectInsert(boardDto);

        return selectInsert;
    }

    @Override
    public BoardDto selectBoardArticle(BoardDto boardDto) {
        return boardDao.selectBoardArticle(boardDto);
    }

    @Override
    public int updateBoard(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return boardDao.updateBoard(boardDto);
    }

    @Override
    public int deleteBoard(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return boardDao.deleteBoard(boardDto);
    }

    @Override
    public List<HashedMap> selectGroupCode(String gId) {
        // TODO Auto-generated method stub
        return boardDao.selectGroupCode(gId);
    }

    @Override
    public List<BoardDto> selectNotiesList(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return boardDao.selectNotiesList(boardDto);
    }

    @Override
    public List<BoardDto> selectFileboardList(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return boardDao.selectFileboardList(boardDto);
    }

    @Override
    @Transactional
    public int updateFileAtt(BoardDto boardDto) {
        // TODO Auto-generated method stub
        int rst = 0;

        String[] editorPhotoSeqArr = boardDto.getEditorPhotoSeqArr();

        FileBoardDTO fileBoardDTO = new FileBoardDTO();
        fileBoardDTO.setBoardSeq(boardDto.getBoardSeq());
        fileBoardDTO.setBoardCtgSeq(boardDto.getBoardCtgSeq());

        if(editorPhotoSeqArr==null){
            return rst;
        }

        for(int i=0; i < editorPhotoSeqArr.length; i++){
            if(null==editorPhotoSeqArr[i] || "".equals(editorPhotoSeqArr[i])) continue;

            fileBoardDTO.setAttSeq(Integer.parseInt(editorPhotoSeqArr[i]));
            //fileBoardDao.updateFileBoardSeq(fileBoardDTO);
        }

        return rst;
    }

    @Override
    public int getDownloadCount(int boardSeq) {
        return boardDao.getDownloadCount(boardSeq);
    }

    @Override
    public int countBylistBoardBas(NmcpBoardBasDto NmcpBoardBasDto) {
        return boardDao.countBylistBoardBas(NmcpBoardBasDto);
    }


    @Override
    public List<NmcpBoardBasDto> listBoardBas(NmcpBoardBasDto NmcpBoardBasDto,int skipResult, int maxResult) {
        return boardDao.listBoardBas(NmcpBoardBasDto,skipResult,maxResult);
    }

}
