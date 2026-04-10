package com.ktmmobile.mcp.cont.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.cont.dto.ContDTO;
import com.ktmmobile.mcp.cont.dto.NationRateDto;
import com.ktmmobile.mcp.cont.dto.WireCounselHistoryReqDto;
import com.ktmmobile.mcp.cont.dto.WireCounselReqDto;
import com.ktmmobile.mcp.cont.dto.WireProductDTO;

@Repository
public class ContDaoImpl implements ContDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int selectContRateListCNT(ContDTO dto) {
        // TODO Auto-generated method stub
        return (Integer)sqlSessionTemplate.selectOne("ContMapper.selectContRateListCNT", dto);
    }

    @Override
    public List<ContDTO> selectContRateList(ContDTO dto){
        return sqlSessionTemplate.selectList("ContMapper.selectContRateList", dto);
    }

    @Override
    public void insertContRate(ContDTO dto){
        sqlSessionTemplate.insert("ContMapper.insertCont", dto);
    }

    @Override
    public ContDTO selectView(ContDTO dto){
        return sqlSessionTemplate.selectOne("ContMapper.selectView", dto);
    }

    @Override
    public void updateContRate(ContDTO dto){
        sqlSessionTemplate.update("ContMapper.updateCont", dto);
    }

    @Override
    public void deleteContRate(ContDTO dto){
        sqlSessionTemplate.delete("ContMapper.deleteCont", dto);
    }

    /**
     * 모바일 부가서비스 페이징 총 개수
     */
    @Override
    public int getTotalCount(ContDTO dto) {
        return (Integer)sqlSessionTemplate.selectOne("ContMapper.getTotalCount", dto);
    }

    /**
     * 모바일 부가서비스 리스트 가져오기
     */
    @Override
    public List<ContDTO> getList(ContDTO dto, int skipResult, int maxResult) {
        return sqlSessionTemplate.selectList("ContMapper.getList", dto, new RowBounds(skipResult, maxResult));
    }

    @Override
    public List<ContDTO> selectAddList(ContDTO dto, int skipResult, int maxResult) {
        return sqlSessionTemplate.selectList("ContMapper.selectAddList", dto, new RowBounds(skipResult, maxResult));
    }

    @Override
    public List<NationRateDto> selectNationRateList() {
        return sqlSessionTemplate.selectList("ContMapper.selectNationRateList", null);
    }

    @Override
    public NationRateDto nationRateAjax(NationRateDto nationRateDto) {
        return sqlSessionTemplate.selectOne("ContMapper.nationRateAjax", nationRateDto);
    }

    @Override
    public int getAddTotalCount(ContDTO dto) {
        // TODO Auto-generated method stub
        return (Integer)sqlSessionTemplate.selectOne("ContMapper.getAddTotalCount",dto);
    }

    @Override
    public void insertWireProductCont(WireProductDTO wireProductDTO) {
        sqlSessionTemplate.insert("ContMapper.insertWireProductCont", wireProductDTO);
    }

    @Override
    public WireProductDTO selectWireProductCont(String wireProdCd) {
        return sqlSessionTemplate.selectOne("ContMapper.selectWireProductCont", wireProdCd);
    }

    @Override
    public List<WireProductDTO> selectWireProductContList() {
        return sqlSessionTemplate.selectList("ContMapper.selectWireProductContList");
    }

    @Override
    public void updateWireProductCont(WireProductDTO wireProductDTO) {
        sqlSessionTemplate.update("ContMapper.updateWireProductCont", wireProductDTO);
    }

    @Override
    public void deleteWireProductCont(String wireProdCd) {
        sqlSessionTemplate.delete("ContMapper.deleteWireProductCont", wireProdCd);
    }

    @Override
    public void insertWireProdDtl(WireProductDTO wireProductDTO) {
        sqlSessionTemplate.insert("ContMapper.insertWireProdDtl", wireProductDTO);
    }

    @Override
    public void updateWireProductDtl(WireProductDTO wireProductDTO) {
        sqlSessionTemplate.update("ContMapper.updateWireProductDtl", wireProductDTO);
    }

    @Override
    public WireProductDTO selectWireProductDtl(String wireProdDtlSeq) {
        return sqlSessionTemplate.selectOne("ContMapper.selectWireProductDtl", wireProdDtlSeq);
    }

    @Override
    public void deleteWireProductDtl(String wireProdDtlSeq) {
        sqlSessionTemplate.delete("ContMapper.deleteWireProductDtl", wireProdDtlSeq);
    }

    @Override
    public int selectWireProductDtlCnt(WireProductDTO wireProductDTO) {
        return (Integer)sqlSessionTemplate.selectOne("ContMapper.selectWireProductDtlCnt", wireProductDTO);
    }

    @Override
    public List<WireProductDTO> selectWireProductDtlList(WireProductDTO wireProductDTO, int skipResult, int maxResult) {
        return sqlSessionTemplate.selectList("ContMapper.selectWireProductDtlList", wireProductDTO, new RowBounds(skipResult, maxResult));
    }

    @Override
    public int countWireCounselList(WireCounselReqDto wireCounselReqDto){
        Object resultObj = sqlSessionTemplate.selectOne("ContMapper.countWireCounselList",wireCounselReqDto);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "ContMapper.countWireCounselList"));
        }
    }

    @Override
    public List<WireCounselReqDto> getWireCounselList(WireCounselReqDto wireCounselReqDto, int skipResult, int maxResult) {
        return sqlSessionTemplate.selectList("ContMapper.getWireCounselList", wireCounselReqDto, new RowBounds(skipResult, maxResult));
    }


    @Override
    public WireCounselReqDto getWireCounsel(WireCounselReqDto wireCounselReqDto) {
        return sqlSessionTemplate.selectOne("ContMapper.getWireCounsel", wireCounselReqDto);
    }


    @Override
    public boolean updateCounsel(WireCounselReqDto wireCounselReqDto) {
        return  0 < sqlSessionTemplate.update("ContMapper.updateCounsel", wireCounselReqDto);
    }


    @Override
    public boolean deleteCounsel(WireCounselReqDto wireCounselReqDto) {
        return  0 <  sqlSessionTemplate.delete("ContMapper.deleteCounsel", wireCounselReqDto);
    }

    @Override
    public boolean insertCounselHistory(WireCounselHistoryReqDto wireCounselHistoryReqDto) {
        return  0 < sqlSessionTemplate.insert("ContMapper.insertCounselHistory", wireCounselHistoryReqDto);
    }

    @Override
    public List<WireCounselHistoryReqDto> getCounselHistoryList(WireCounselHistoryReqDto wireCounselHistoryReqDto) {
        return sqlSessionTemplate.selectList("ContMapper.getCounselHistoryList", wireCounselHistoryReqDto);
    }



}
