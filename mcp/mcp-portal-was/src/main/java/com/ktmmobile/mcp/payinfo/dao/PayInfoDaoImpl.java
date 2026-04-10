package com.ktmmobile.mcp.payinfo.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.payinfo.dto.EvidenceDto;
import com.ktmmobile.mcp.payinfo.dto.PayInfoDto;
import com.ktmmobile.mcp.payinfo.dto.PayInfoFormDto;

@Repository
public class PayInfoDaoImpl implements PayInfoDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public String getSeq(){
    	RestTemplate restTemplate = new RestTemplate();
    	return restTemplate.postForObject(apiInterfaceServer + "/payinfo/seq", null, String.class); // PayInfoMapper.getSeq
    }

    @Override
    public List<PayInfoFormDto> getPositionList(){
        return sqlSessionTemplate.selectList("PayInfoMapper.getPositionList");
    }

    @Override
    public int insetPayInfo(PayInfoDto dto){
        return sqlSessionTemplate.insert("PayInfoMapper.insetPayInfo", dto);
    }

    public int updateEvidence(PayInfoDto dto){
    	RestTemplate restTemplate = new RestTemplate();
    	return restTemplate.postForObject(apiInterfaceServer + "/payinfo/modifyEvidence", dto, Integer.class); // PayInfoMapper.updateEvidence
    }

    @Override
    public int updatePayInto(PayInfoDto dto){
        return sqlSessionTemplate.insert("PayInfoMapper.updatePayInto", dto);
    }

    @Override
    public EvidenceDto selectMspJuoSubinfo(PayInfoDto dto){
    	RestTemplate restTemplate = new RestTemplate();
    	return restTemplate.postForObject(apiInterfaceServer + "/payinfo/mspJuoSubinfo", dto, EvidenceDto.class); //PayInfoMapper.selectMspJuoSubinfo
    }

    @Override
    public int insetEvidence(EvidenceDto dto){
    	RestTemplate restTemplate = new RestTemplate();
    	return restTemplate.postForObject(apiInterfaceServer + "/payinfo/addEvidence", dto, Integer.class); // PayInfoMapper.insetEvidence
    }

    @Override
    public int insertOrUpdatePayInfo(EvidenceDto dto) {
        if (checkEvidenceCount(dto) > 0) {
            return updateEvidence(dto);
        } else {
            return insetEvidence(dto);
        }
    }

    public int checkEvidenceCount(EvidenceDto dto) {
    	RestTemplate restTemplate = new RestTemplate();
    	Object resultObj = restTemplate.postForObject(apiInterfaceServer + "/payinfo/checkEvidenceCount", dto, Integer.class); // PayInfoMapper.checkEvidenceCount

        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "PayInfoMapper.checkEvidenceCount"));
        }
    }

    public int updateEvidence(EvidenceDto dto){
        return sqlSessionTemplate.update("PayInfoMapper.updateEvidence", dto);
    }

    @Override
    public Map callMspPayinfoImg(String contractNum , String reqBank , String reqAccountNumber , String ext , String result){
    	RestTemplate restTemplate = new RestTemplate();

    	MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
    	params.add("contractNum", contractNum);
    	params.add("reqBank", reqBank);
    	params.add("reqAccountNumber", reqAccountNumber);
    	params.add("ext",ext);
    	params.add("result", result);

        return restTemplate.postForObject(apiInterfaceServer + "/payinfo/mspPayinfoImg", params, Map.class); // PayInfoMapper.callMspPayinfoImg
    }

    @Override
    public PayInfoDto selectPayInfo(PayInfoDto dto){
    	return sqlSessionTemplate.selectOne("PayInfoMapper.selectPayInfo", dto);
    }

}
