package com.ktmmobile.mcp.direct.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.direct.dto.StoreDto;
import com.ktmmobile.mcp.common.dto.ComCdDto;

@Repository
public class StoreDaoImpl implements StoreDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;


    @Override
    public List<ComCdDto> storeComCd() {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("Store_Query.getStoreComCdList", null);
    }

    @Override
    public int countStoreList(StoreDto storeDto) {
        // TODO Auto-generated method stub
        return (Integer)sqlSessionTemplate.selectOne("Store_Query.countStoreList", storeDto);
    }

    @Override
    public List<StoreDto> storeListSelect(StoreDto storeDto, int skipResult, int maxResult) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("Store_Query.storeListSelect", storeDto, new RowBounds(skipResult, maxResult));
    }
	
	@Override
	public StoreDto storeDetail(int storSeq) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("Store_Query.storeDetail", storSeq);
	}
	@Override
	public List<StoreDto> selectSubAddr(StoreDto storeDto){
		String a = "";
		return sqlSessionTemplate.selectList("Store_Query.selectSubAddr", storeDto);
	}

}
