package com.ktmmobile.mcp.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.company.dao.CompanyDao;
import com.ktmmobile.mcp.company.dto.ReportDto;






/**
 * @Class Name : CompanyServiceImpl
 * @Description :
 *
 * @author : ant
 * @Create Date : 2018. 4
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyDao companyDao;

    @Override
    public int insertNmcpViolationReport(ReportDto reportDto) {
        return companyDao.insertNmcpViolationReport(reportDto);
    }

    @Override
    public ReportDto selectNmcpViolationReport(ReportDto reportDto) {
        return companyDao.selectNmcpViolationReport(reportDto);
    }

    @Override
    public ReportDto selectNmcpViolationReportView(ReportDto reportDto) {
        return companyDao.selectNmcpViolationReportView(reportDto);
    }

    @Override
    public int updateNmcpViolationReport(ReportDto reportDto) {
        return companyDao.updateNmcpViolationReport(reportDto);
    }

    @Override
    public List<ReportDto> listNmcpViolationReport(ReportDto reportDto, int skipResult, int maxResult) {
        return companyDao.listNmcpViolationReport(reportDto,skipResult,maxResult);
    }

    @Override
    public int listNmcpViolationReportCount(ReportDto reportDto) {
        return companyDao.listNmcpViolationReportCount(reportDto);
    }

    @Override
    public int getTotalCount(BoardDto boardDto) {
        int getTotalCount = companyDao.getTotalCount(boardDto);
        return getTotalCount;
    }

    @Override
    public List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult) {
        return companyDao.selectBoardList(boardDto, skipResult, maxResult);
    }

    @Override
    public List<BoardDto> selectNoticeList(BoardDto boardDto, int i, int j) {
        List<BoardDto> selectNoticeList = companyDao.selectNoticeList(boardDto);
        return selectNoticeList;
    }

    @Override
    public int getNTotalCount(BoardDto boardDto) {
        // TODO Auto-generated method stub
        int getNTotalCount = companyDao.getNTotalCount(boardDto);
        return getNTotalCount;
    }

    @Override
    public BoardDto viewNoticeBoard(BoardDto boardDto) {
        BoardDto viewNoticeBoard = companyDao.viewNoticeBoard(boardDto);
        return viewNoticeBoard;
    }

    @Override
    public BoardDto selectNotiIndex(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return companyDao.selectNotiIndex(boardDto);
    }



}
