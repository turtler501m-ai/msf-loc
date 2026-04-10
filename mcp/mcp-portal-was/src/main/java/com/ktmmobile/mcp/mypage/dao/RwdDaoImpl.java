package com.ktmmobile.mcp.mypage.dao;

import com.ktmmobile.mcp.mypage.dto.RwdFailHistDto;
import com.ktmmobile.mcp.mypage.dto.RwdOrderDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RwdDaoImpl implements RwdDao{


    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void insertFailRwd(RwdFailHistDto rwdFailHistDto) {
        sqlSessionTemplate.insert("RwdMapper.insertFailRwd", rwdFailHistDto);
    }

    @Override
    public boolean updateEmvScanMstRwd(String scanId) {
        return 0 < sqlSessionTemplate.update("RwdMapper.updateEmvScanMstRwd", scanId);
    }

    @Override
    public boolean updateEmvScanFileRwd(String scanId) {
        return 0 < sqlSessionTemplate.update("RwdMapper.updateEmvScanFileRwd", scanId);
    }

    @Override
    public int getMaxFileNum(String orgScanId) {
        return sqlSessionTemplate.selectOne("RwdMapper.getMaxFileNum", orgScanId);
    }

    @Override
    public List<RwdOrderDto> getEmvScanFileList(String scanId) {
        return sqlSessionTemplate.selectList("RwdMapper.getEmvScanFileList", scanId);
    }

    @Override
    public boolean insertEmvScanFile(RwdOrderDto rwdOrderDto) {
        return 0 < sqlSessionTemplate.insert("RwdMapper.insertEmvScanFile", rwdOrderDto);

    }

}
