package com.ktmmobile.mcp.app.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.app.dto.AppInfoDTO;

/**
 * @Class Name : AppDaoImpl
 * @Description : 사용자 단말 App 정보 Dao 구현클래스
 *
 * @author :
 * @Create Date :
 */
@Repository
public class AppDaoImpl implements AppDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /**
     * @Description : 사용자 단말기 APP 정보를 가지고 온다.
     * @param String uuid
     * @return Map<String, String>
     * @Author :
     * @Create Date :
     */
    @Override
    public List<AppInfoDTO> selectUsrAppDetail(AppInfoDTO appInfoDTO){
        return sqlSessionTemplate.selectList("AppMapper.selectUsrAppDetail", appInfoDTO);
    }
    /**
     * @Description : 사용자 단말기 APP 정보를 수정한다.
     * @param Map<String, String>
     * @return
     * @Author :
     * @Create Date :
     */
    @Override
    public int updateUsrAppInfo(AppInfoDTO appInfoDTO) {
        return (Integer)sqlSessionTemplate.update("AppMapper.updateUsrAppInfo", appInfoDTO);
    }

    /**
     * @Description : 사용자 단말기 APP 정보를 입력/수정한다.
     * @param Map<String, String>
     * @return
     * @Author :
     * @Create Date :
     */
    @Override
    public int mergeUsrAppInfo(AppInfoDTO appInfoDTO) {
        return (Integer)sqlSessionTemplate.update("AppMapper.mergeUsrAppInfo", appInfoDTO);
    }

    /**
     * @Description : 사용자 요금제 정보를 가지고 온다.
     * @param String ncn
     * @return Map<String, String>
     * @Author :
     * @Create Date :
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> selectUsrRateInfo(String ncn) {

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("ncn", ncn);
        return restTemplate.postForObject(apiInterfaceServer + "/app/usrRateInfo", params, Map.class); // AppMapper.selectUsrRateInfo
    }

    /**
     * @Description : OS 종류에 따른 APP 버전 정보를 가지고 온다.
     * @param String os
     * @return Map<String, String>
     * @Author :
     * @Create Date :
     */
    @Override
    public Map<String, String> selectAppVersion(String os){
        return sqlSessionTemplate.selectOne("AppMapper.selectAppVersion", os);
    }

    /**
     * @Description : 다회선 대표번호  정보를 수정한다.
     * @param Map<String, String>
     * @return
     * @Author : ant
     * @Create Date : 2016. 2. 27.
     */
    @Override
    public int userRepChange(Map<String, String> map)  {
        return (Integer)sqlSessionTemplate.update("AppMapper.userRepChange", map);
    }

    /**
     * 설명 : 사용자 ID 로 앱 정보 조회
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param appInfoDTO
     */
    @Override
    public void updateUsrAppInfoById(AppInfoDTO appInfoDTO) {
        sqlSessionTemplate.update("AppMapper.updateUsrAppInfoById", appInfoDTO);
    }

    /**
     * 설명 : 사용자 단말기 APP 정보를 입력한다.
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param appInfoDTO
     */
    public void insertUsrAppInfo(AppInfoDTO appInfoDTO) {
        sqlSessionTemplate.insert("AppMapper.insertUsrAppInfo", appInfoDTO);
    }
    /**
     * 설명 : 사용자 단말기 APP 정보 이력 저장
     * @Author : 박민건
     * @Date : 2025.04.01
     * @param appInfoDTO
     */
    public void insertUsrAppInfoHist(AppInfoDTO appInfoDTO) {
        sqlSessionTemplate.insert("AppMapper.insertUsrAppInfoHist", appInfoDTO);
    }
}
