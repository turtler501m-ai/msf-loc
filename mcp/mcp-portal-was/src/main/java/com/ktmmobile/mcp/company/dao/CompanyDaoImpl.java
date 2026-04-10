package com.ktmmobile.mcp.company.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.company.dto.ReportDto;

/**
 * @Class Name : CompanyDaoImpl
 * @Description :
 *
 * @author : ant
 * @Create Date : 2015. 12. 31.
 */
@Repository
public class CompanyDaoImpl implements CompanyDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int insertNmcpViolationReport(ReportDto reportDto) {
        return sqlSessionTemplate.insert("CompanyMapper.insertNmcpViolationReport", reportDto);
    }

    @Override
    public ReportDto selectNmcpViolationReport(ReportDto reportDto) {
        return sqlSessionTemplate.selectOne("CompanyMapper.selectNmcpViolationReport", reportDto);
    }

    @Override
    public ReportDto selectNmcpViolationReportView(ReportDto reportDto) {
        return sqlSessionTemplate.selectOne("CompanyMapper.selectNmcpViolationReportView", reportDto);
    }

    @Override
    public int updateNmcpViolationReport(ReportDto reportDto) {
        return (Integer)sqlSessionTemplate.update("CompanyMapper.updateNmcpViolationReport", reportDto);
    }

    @Override
    public List<ReportDto> listNmcpViolationReport(ReportDto reportDto, int skipResult, int maxResult) {
        return sqlSessionTemplate.selectList("CompanyMapper.listNmcpViolationReport", reportDto, new RowBounds(skipResult, maxResult));
    }

    @Override
    public int listNmcpViolationReportCount(ReportDto reportDto) {

        Object resultObj = sqlSessionTemplate.selectOne("CompanyMapper.listNmcpViolationReportCount",reportDto);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "CompanyMapper.listNmcpViolationReportCount"));
        }
    }

    @Override
    public int getTotalCount(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return (Integer)sqlSessionTemplate.selectOne("CompanyMapper.getTotalCount",boardDto);
    }

    @Override
    public List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("CompanyMapper.selectBoardList",boardDto,new RowBounds(skipResult, maxResult));
    }

    @Override
    public List<BoardDto> selectNoticeList(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("CompanyMapper.selectNoticeList",boardDto);
    }

    @Override
    public int getNTotalCount(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return (Integer)sqlSessionTemplate.selectOne("CompanyMapper.getNTotalCount",boardDto);
    }

    @Override
    public BoardDto viewNoticeBoard(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectOne("CompanyMapper.viewNoticeBoard", boardDto);
    }

    @Override
    public BoardDto selectNotiIndex(BoardDto boardDto) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectOne("CompanyMapper.selectNotiIndex",boardDto);
    }



}
