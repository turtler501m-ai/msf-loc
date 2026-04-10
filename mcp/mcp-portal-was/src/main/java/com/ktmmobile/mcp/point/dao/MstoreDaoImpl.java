package com.ktmmobile.mcp.point.dao;

import com.ktmmobile.mcp.point.dto.MstoreCanTrgDto;
import com.ktmmobile.mcp.point.dto.MstoreContentItemDto;
import com.ktmmobile.mcp.point.dto.MstoreDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository

public class MstoreDaoImpl implements MstoreDao {


    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<MstoreDto> getMspCusInfo(String userId, String contractNum) {

        MstoreDto mstoreDto= new MstoreDto();
        mstoreDto.setUserId(userId);
        mstoreDto.setContractNum(contractNum);

        RestTemplate restTemplate = new RestTemplate();
        MstoreDto[] cusInfoArr= restTemplate.postForObject(apiInterfaceServer + "/mstore/getMspCusInfo", mstoreDto, MstoreDto[].class);
        List<MstoreDto> cusInfoList = Arrays.asList(cusInfoArr);
        return cusInfoList;
    }

    @Override
    public Map<String, String> getMstoreTermsAgreeInfo(String userId) {
        return sqlSessionTemplate.selectOne("MstoreMapper.getMstoreTermsAgreeInfo",userId);
    }

    @Override
    public int updateMstoreTermsAgreeInfo(String userId) {
        return sqlSessionTemplate.update("MstoreMapper.updateMstoreTermsAgreeInfo", userId);
    }

    @Override
    public void insertMstoreTermsAgreeInfo(MstoreDto mstoreDto) {
        sqlSessionTemplate.insert("MstoreMapper.insertMstoreTermsAgreeInfo", mstoreDto);
    }

    @Override
    public int insertMstoreCanTrg(MstoreCanTrgDto mstoreCanTrgDto) {
        return sqlSessionTemplate.insert("MstoreMapper.insertMstoreCanTrg", mstoreCanTrgDto);
    }

    @Override
    public MstoreDto getMcpRequestInfo(String customerId) {

        MstoreDto mcpRequestInfo= null;

        RestTemplate restTemplate = new RestTemplate();
        MstoreDto[] mcpInfoArr= restTemplate.postForObject(apiInterfaceServer + "/mstore/getMcpRequestInfo", customerId, MstoreDto[].class);
        if(mcpInfoArr != null && mcpInfoArr.length > 0){
            mcpRequestInfo= mcpInfoArr[0];
        }

        return mcpRequestInfo;
    }

    @Override
    public String getMstoreTmpEmpen(String customerId) {

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mstore/getMstoreTmpEmpen", customerId, String.class);
    }

    @Override
    public MstoreDto getMstoreSSOInfo(String userId) {
        return sqlSessionTemplate.selectOne("MstoreMapper.getMstoreSSOInfo", userId);
    }

    @Override
    public int mergeMstoreSSOInfo(Map<String,String> ssoInfoMap) {
        return sqlSessionTemplate.update("MstoreMapper.mergeMstoreSSOInfo", ssoInfoMap);
    }

    @Override
    public String getMcpUserCntrMngInfo(String userId) {
        return sqlSessionTemplate.selectOne("MstoreMapper.getMcpUserCntrMngInfo", userId);
    }

    @Override
    public List<MstoreContentItemDto> getMstoreContentItemListPc() {
        return sqlSessionTemplate.selectList("MstoreMapper.getMstoreContentItemListPc");
    }

    @Override
    public List<MstoreContentItemDto> getMstoreContentItemListMobile() {
        return sqlSessionTemplate.selectList("MstoreMapper.getMstoreContentItemListMobile");
    }

}