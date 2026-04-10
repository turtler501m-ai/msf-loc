package com.ktmmobile.mcp.company.dao;

import java.util.List;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.company.dto.ReportDto;

/**
 * @Class Name : CompanyDao
 * @Description :
  * @author : ant
 * @Create Date : 2018. 04.
 */
public interface CompanyDao {

    /**
    * @Description : 윤리 위반 신고 등록
    * @param param
    * @return
    * @Author : ant
    * @Create Date : 2018. 4
    */
    public int insertNmcpViolationReport(ReportDto reportDto);


    /**
     * @Description : 윤리 위반 신고 조회
     * @param param
     * @return
     * @Author : ant
     * @Create Date : 2018. 4
     */
    public ReportDto selectNmcpViolationReport(ReportDto reportDto);


    /**
     * @Description : 관리자 윤리 위반 신고 상세보기
     * @param param
     * @return
     * @Author : ant
     * @Create Date : 2018. 4
     */
    public ReportDto selectNmcpViolationReportView(ReportDto reportDto);

    /**
     * @Description : 관리자 윤리 위반 신고 상태 업데이트
     * @param param
     * @return
     * @Author : ant
     * @Create Date : 2018. 4
     */
    public int updateNmcpViolationReport(ReportDto reportDto);

    /**
     * <pre>
     * 설명     : 윤리위반신고 리스트 조회
     * @param ReportDto :
     * @return
     * </pre>
     */
    public List<ReportDto> listNmcpViolationReport(ReportDto reportDto, int skipResult, int maxResult);

    /**
     * <pre>
     * 설명     : 윤리위반신고 리스트 카운트
     * @param ReportDto :
     * @return
     * </pre>
     */
    public int listNmcpViolationReportCount(ReportDto reportDto);


    public int getTotalCount(BoardDto boardDto);


    public List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult);


    public List<BoardDto> selectNoticeList(BoardDto boardDto);


    public int getNTotalCount(BoardDto boardDto);


    public BoardDto viewNoticeBoard(BoardDto boardDto);


    public BoardDto selectNotiIndex(BoardDto boardDto);


}
