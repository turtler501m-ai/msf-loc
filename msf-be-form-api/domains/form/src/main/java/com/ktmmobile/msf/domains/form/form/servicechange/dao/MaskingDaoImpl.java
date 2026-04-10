package com.ktmmobile.msf.domains.form.form.servicechange.dao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MaskingDto;

@Repository
public class MaskingDaoImpl implements MaskingDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;


    @Override
    public int insertMaskingRelease(MaskingDto maskingDto) {
          int abc = sqlSessionTemplate.insert("MaskingMapper.insertMaskingRelease",maskingDto);
          return 1;
    }

    @Override
    public int insertMaskingReleaseHist(MaskingDto maskingDto) {
          sqlSessionTemplate.insert("MaskingMapper.insertMaskingReleaseHist",maskingDto);
          return 1;
    }



}
