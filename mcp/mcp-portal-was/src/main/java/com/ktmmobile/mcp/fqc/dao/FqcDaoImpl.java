package com.ktmmobile.mcp.fqc.dao;


import com.ktmmobile.mcp.fqc.dto.FqcBasDto;
import com.ktmmobile.mcp.fqc.dto.FqcDltDto;
import com.ktmmobile.mcp.fqc.dto.FqcPlcyBnfDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.ktmmobile.mcp.fqc.dto.FqcPlcyBasDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FqcDaoImpl implements FqcDao {


    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<FqcPlcyBasDto> getFqcPlcyBasList() {
        return sqlSessionTemplate.selectList("FqcServiceMapper.getFqcPlcyBasList");
    }

    @Override
    public FqcPlcyBasDto getFqcPlcyBas(String fqcPlcyCd){
        return sqlSessionTemplate.selectOne("FqcServiceMapper.getFqcPlcyBas", fqcPlcyCd);
    }


    @Override
    public boolean insertFqcBas(FqcBasDto fqcBasDto)  {
        return 0 <  sqlSessionTemplate.insert("FqcServiceMapper.insertFqcBas", fqcBasDto);
    }

    @Override
    public FqcBasDto getFqcBas(FqcBasDto fqcBasDto)  {
        return sqlSessionTemplate.selectOne("FqcServiceMapper.getFqcBas", fqcBasDto);
    }

    @Override
    public boolean updateFqcBas(FqcBasDto fqcBasDto) {
        return 0 < sqlSessionTemplate.update("FqcServiceMapper.updateFqcBas",fqcBasDto);
    }


    @Override
    public boolean insertFqcDlt(FqcDltDto fqcDltDto)  {
        return 0 <  sqlSessionTemplate.insert("FqcServiceMapper.insertFqcDlt", fqcDltDto);
    }


    @Override
    public List<FqcDltDto> getFqcDltList(FqcDltDto fqcDltDto){
        return sqlSessionTemplate.selectList("FqcServiceMapper.getFqcDltList",fqcDltDto);
    }

    @Override
    public boolean updateFqcDlt(FqcDltDto fqcDltDto) {
        return 0 < sqlSessionTemplate.update("FqcServiceMapper.updateFqcDlt",fqcDltDto);
    }


    @Override
    public int isFqcPlcyPlaCount(String fqcPlcyCd , String socCode) {
        HashMap<String, String> hm =new HashMap<String, String>();
        hm.put("fqcPlcyCd", fqcPlcyCd);
        hm.put("socCode", socCode);

        Object resultObj = sqlSessionTemplate.selectOne("FqcServiceMapper.isFqcPlcyPlaCount",hm);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "FqcServiceMapper.isFqcPlcyPlaCount"));
        }
    }

    @Override
    public String  getPlcyMsnCd(String fqcPlcyCd , String msnTpCd) {
        HashMap<String, String> hm =new HashMap<String, String>();
        hm.put("fqcPlcyCd", fqcPlcyCd);
        hm.put("msnTpCd", msnTpCd);
        return sqlSessionTemplate.selectOne("FqcServiceMapper.getPlcyMsnCd", hm);
    }

    @Override
    public List<FqcPlcyBnfDto>  getFqcPlcyBnfList(String fqcPlcyCd) {
        return sqlSessionTemplate.selectList("FqcServiceMapper.getFqcPlcyBnfList",fqcPlcyCd);
    }


}
