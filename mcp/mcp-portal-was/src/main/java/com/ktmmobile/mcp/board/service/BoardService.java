package com.ktmmobile.mcp.board.service;

import java.util.List;

import org.apache.commons.collections.map.HashedMap;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.common.dto.db.NmcpBoardBasDto;

public interface BoardService {

    int getTotalCount(BoardDto boardDto);

    List<BoardDto> selectBoardList(BoardDto boardDto);

    BoardDto selectBoardArticle(BoardDto boardDto);

    int selectInsert(BoardDto boardDto);

    int deleteBoard(BoardDto boardDto);

    int updateBoard(BoardDto boardDto);

    List<HashedMap> selectGroupCode(String gId);

    List<BoardDto> selectNotiesList(BoardDto boardDto);

    List<BoardDto> selectFileboardList(BoardDto boardDto);

    int updateFileAtt(BoardDto boardDto);

    /**
     * <pre>
     * 설명     : BoardBas COUNT
     * @param NmcpBoardBasDto
     * @return
     * @return: int
     * </pre>
     */
    public int countBylistBoardBas(NmcpBoardBasDto NmcpBoardBasDto) ;


    /**
     * <pre>
     * 설명     : BoardBas 리스트 조회
     * @param NmcpBoardBasDto
     * @return
     * @return: List<NmcpBoardBasDto>
     * </pre>
     */
    public List<NmcpBoardBasDto> listBoardBas(NmcpBoardBasDto NmcpBoardBasDto,int skipResult, int maxResult) ;


    /**
     * <pre>
     * 설명     : 파일 다운로드 count
     * @param boardSeq :
     * @return
     * </pre>
     */
    public int getDownloadCount(int boardSeq) ;

}
