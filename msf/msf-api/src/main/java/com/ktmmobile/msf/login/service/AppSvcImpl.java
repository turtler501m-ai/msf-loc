package com.ktmmobile.msf.login.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.login.dao.AppDao;
import com.ktmmobile.msf.login.dto.AppInfoDTO;
import com.ktmmobile.msf.system.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;

/**
 * @Class Name : AppSvcImpl
 * @Description : 사용자 단말 App 정보 Dao 구현클래스
 *
 * @author : ant
 * @Create Date : 2016. 2. 27.
 */
@Repository
public class AppSvcImpl implements AppSvc {

    private static final Logger logger = LoggerFactory.getLogger(AppSvcImpl.class);

    @Autowired
    private AppDao appDao;

    /**
    * @Description : 사용자 단말기 APP 정보를 가지고 온다.
    * @param String uuid
    * @return Map<String, String>
    * @Author : ant
    * @Create Date : 2016. 2. 27.
    */
    @Override
    public AppInfoDTO selectUsrAppDetail(AppInfoDTO appInfoDTO){
        AppInfoDTO appInfoDTOResult = new AppInfoDTO();

        List<AppInfoDTO> list = appDao.selectUsrAppDetail(appInfoDTO);
        if(list != null && list.size() > 0) {
            appInfoDTOResult = list.get(0);
        } else {
            return null;
        }

        return appInfoDTOResult;
    }
    /**
    * @Description : 사용자 단말기 APP 정보를 수정한다.
    * @param Map<String, String>
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 27.
    */
    @Override
    public int updateUsrAppInfo(AppInfoDTO appInfoDTO) {

        int count = appDao.updateUsrAppInfo(appInfoDTO);
        if(count > 0) appDao.insertUsrAppInfoHist(appInfoDTO);
        return count;
    }

    /**
    * @Description : 사용자 단말기 APP 정보를 입력/수정한다.
    * @param Map<String, String>
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 27.
    */
    @Override
    public int mergeUsrAppInfo(AppInfoDTO appInfoDTO) {

        //최초 등록시 위젯호출 시간 설정
        //uuid 의 기존 userId 와 입력 userId 와 다른 경우 바이오/간편인증 초기화
        AppInfoDTO appInfoDTOResult = new AppInfoDTO();
        String oldUserId = "";
        if(!"".equals(StringUtil.NVL(appInfoDTO.getUuid(),""))) {
            //위젯호출 주기(기본값)
            List<NmcpCdDtlDto> wdgtCallCyclListDefault = NmcpServiceUtils.getCodeList("SY0026");
//            String defaultUserWdgtSet = StringUtil.NVL(wdgtCallCyclListDefault.get(0).getDtlCd(),""); // 기존로직
            String defaultUserWdgtSet = wdgtCallCyclListDefault != null ? StringUtil.NVL(wdgtCallCyclListDefault.get(0).getDtlCd(),"") : ""; // 취약성 230

            AppInfoDTO appInfoDTO1 = new AppInfoDTO();
            appInfoDTO1.setUuid(appInfoDTO.getUuid());
            List<AppInfoDTO> list = appDao.selectUsrAppDetail(appInfoDTO1);
            if(list != null && list.size() > 0) {
                appInfoDTOResult = list.get(0);
            } else {
                appInfoDTO.setWdgtUseQntyCallCyclCd(defaultUserWdgtSet);
            }
            oldUserId = StringUtil.NVL(appInfoDTOResult.getUserId(),"");

            String curWdgtSet = StringUtil.NVL(appInfoDTOResult.getWdgtUseQntyCallCyclCd(),"");
            if("".equals(curWdgtSet)) {
                appInfoDTO.setWdgtUseQntyCallCyclCd(defaultUserWdgtSet);
            }

            String curUserID = StringUtil.NVL(appInfoDTO.getUserId(), "");
            if(!"".equals(oldUserId) && !"".equals(curUserID)) {
                if(!oldUserId.equals(curUserID)) {
                    appInfoDTO.setSimpleLoginYn("N");
                    appInfoDTO.setBioLoginYn("N");

                    //기존 ID 의 바이오/간편인증 초기화
                    AppInfoDTO appInfoDTO2 = new AppInfoDTO();
                    appInfoDTO2.setUuid(oldUserId);
                    appDao.updateUsrAppInfoById(appInfoDTO2);
                    appDao.insertUsrAppInfoHist(appInfoDTO2);
                }
            }

            if(list != null && list.size() > 0) {
                appDao.updateUsrAppInfo(appInfoDTO);
                if(!StringUtil.isEmpty(appInfoDTO.getSendYn())) {
                    appDao.insertUsrAppInfoHist(appInfoDTO);
                }
            } else {
                if("Y".equals(appInfoDTO.getUpdateFlag())) {
                    appDao.insertUsrAppInfo(appInfoDTO);
                    appDao.insertUsrAppInfoHist(appInfoDTO);
                }
            }

            //appDao.mergeUsrAppInfo(appInfoDTO);
        }
        return 1;
    }

    /**
    * @Description : 사용자 요금제 정보를 가지고 온다.
    * @param String ncn
    * @return Map<String, String>
    * @Author : ant
    * @Create Date : 2016. 2. 27.
    */
    @Override
    public Map<String, String> selectUsrRateInfo(String ncn){
        return appDao.selectUsrRateInfo(ncn);
    }

    /**
    * @Description : OS 종류에 따른 APP 버전 정보를 가지고 온다.
    * @param String os
    * @return Map<String, String>
    * @Author : ant
    * @Create Date : 2016. 2. 29.
    */
    public Map<String, String> selectAppVersion(String os){
        return appDao.selectAppVersion(os);
    }

    /**
    * @Description : 다회선 대표번호  정보를 수정한다.
    * @param Map<String, String>
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 27.
    */
    public int userRepChange(Map<String, String> map)  {
        return appDao.userRepChange(map);
    }

}
