package com.ktmmobile.mcp.content.dao;
import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ktmmobile.mcp.content.dto.KtDcDto;

@Repository
public class KtDcDaoImpl implements KtDcDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<KtDcDto> selectRateNmList(KtDcDto ktDcDto) {
        return sqlSessionTemplate.selectList("ktDcMapper.selectRateNmList", ktDcDto);
    }

    @Override
    public KtDcDto selectKtDc(KtDcDto ktDcDto) {
        return sqlSessionTemplate.selectOne("ktDcMapper.selectKtDc", ktDcDto);
    }

    @Override
    public int insertKtDc(KtDcDto ktDcDto) {
        sqlSessionTemplate.insert("ktDcMapper.insertKtDc",ktDcDto);
        return 1;
    }

}
