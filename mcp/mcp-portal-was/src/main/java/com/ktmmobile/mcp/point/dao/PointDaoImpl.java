package com.ktmmobile.mcp.point.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.point.dto.CustPointTxnDtlDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;
import com.ktmmobile.mcp.point.dto.CustPointDto;
import com.ktmmobile.mcp.point.dto.CustPointGiveTgtBasDto;


@Repository
public class PointDaoImpl implements PointDao{
	
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
	
	public int updateCustPointGiveBaseBas(CustPointTxnDto pointTxnDto) {
		return sqlSessionTemplate.update("PointMapper.updateCustPointGiveBaseBas",pointTxnDto);
	}
	
	public int insertCustPointTxn(CustPointTxnDto pointTxnDto) {
		sqlSessionTemplate.insert("PointMapper.insertCustPointTxn",pointTxnDto);
		return pointTxnDto.getPointTxnSeq();
	}
	
	public List<CustPointTxnDtlDto> selectPsblCustPointTxnDtlList(CustPointTxnDto pointTxnDto) {
		 return sqlSessionTemplate.selectList("PointMapper.selectPsblCustPointTxnDtlList",pointTxnDto); 
	}
	
	public int insertCustPointTxnDtl(CustPointTxnDtlDto useCustPointTxnDtlDto){
		return sqlSessionTemplate.insert("PointMapper.insertCustPointTxnDtl",useCustPointTxnDtlDto);
	}
	
	public int updateCustPointTxnDtl(CustPointTxnDtlDto cstPointTxnDtlDto) {
		return sqlSessionTemplate.update("PointMapper.updateCustPointTxnDtl",cstPointTxnDtlDto);
	}
	
	public int insertCustPointAcuTxnDtl(CustPointTxnDto pointTxnDto) {
		return sqlSessionTemplate.insert("PointMapper.insertCustPointAcuTxnDtl",pointTxnDto);
	}
	
	public CustPointDto selectCustPointGiveBaseBas(CustPointDto custPointDto) {
		return sqlSessionTemplate.selectOne("PointMapper.selectCustPointGiveBaseBas", custPointDto);
	}
	
	public int insertCustPointGiveBaseBas(CustPointDto custPointDto) {
		return sqlSessionTemplate.insert("PointMapper.insertCustPointGiveBaseBas",custPointDto);
	}
	
	public int insertCustPointTxnSUE(CustPointTxnDto custPointTxnDto) {
		return sqlSessionTemplate.insert("PointMapper.insertCustPointTxnSUE",custPointTxnDto);
	}
	public int insertCustPointGiveTgtBas(CustPointGiveTgtBasDto custPointGiveTgtBasDto) {
		return sqlSessionTemplate.insert("PointMapper.insertCustPointGiveTgtBas",custPointGiveTgtBasDto);
	}	
}
