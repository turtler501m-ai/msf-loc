package com.ktmmobile.msf.domains.form.system.cert.dao;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ktmmobile.msf.domains.form.system.cert.dto.CertDto;

@Repository
public class CertDaoImpl implements CertDao {
	
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

	@Override
	public long insertCert(CertDto certDto) {
		sqlSessionTemplate.insert("CertMapper.insertCert", certDto);
        return certDto.getRtnCrtSeq();
	}
	
	@Override
	public int getStepCnt(long crtSeq) {
    	Object resultObj = sqlSessionTemplate.selectOne("CertMapper.getStepCnt", crtSeq);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "boardSql.countBylistBoardBas"));
        }
    }

	@Override
	public List<CertDto> getCompareList(CertDto certDto) {
		return sqlSessionTemplate.selectList("CertMapper.getCompareList", certDto);
	}

	@Override
	public List<String> getContractNumByUserId(String userId) {
		return sqlSessionTemplate.selectList("CertMapper.getContractNumByUserId", userId);
	}

	@Override
	public int deleteCert(CertDto certDto) {
		return sqlSessionTemplate.delete("CertMapper.deleteCert", certDto);
	}

	@Override
	public int mergeCert(CertDto certDto) {
		return sqlSessionTemplate.update("CertMapper.mergeCert", certDto);
	}

	@Override
	public int getDataEsimSeq(long crtSeq) {
		Object resultObj = sqlSessionTemplate.selectOne("CertMapper.getDataEsimSeq", crtSeq);
		if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "boardSql.countBylistBoardBas"));
        }
	}

	@Override
	public int updateEsimSeq(CertDto certDto) {
		return sqlSessionTemplate.update("CertMapper.updateEsimSeq", certDto);
	}

	@Override
	public int delStepInfo(CertDto certDto) {
		return sqlSessionTemplate.delete("CertMapper.delStepInfo", certDto);
	}

	@Override
	public int updateNicePin(CertDto certDto) {
		return sqlSessionTemplate.update("CertMapper.updateNicePin", certDto);
	}

	@Override
	public int updateCrtReferer(CertDto certDto) {
		return sqlSessionTemplate.update("CertMapper.updateCrtReferer", certDto);
	}

	@Override
	public int getModuTypeStepCnt(CertDto certDto) {
		Object resultObj = sqlSessionTemplate.selectOne("CertMapper.getModuTypeStepCnt", certDto);
		if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "boardSql.countBylistBoardBas"));
        }
	}

	@Override
	public String getCertReferer(long crtSeq) {
		return sqlSessionTemplate.selectOne("CertMapper.getCertReferer", crtSeq);
	}

	@Override
	public int getDataMobileNo(long crtSeq) {
		return sqlSessionTemplate.selectOne("CertMapper.getDataMobileNo", crtSeq);
	}

	@Override
	public int updateMobileNo(CertDto certDto) {
		return sqlSessionTemplate.update("CertMapper.updateMobileNo", certDto);
	}

}
