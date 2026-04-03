/**
 *
 */
package com.ktmmobile.msf.login.service;

import java.util.Map;

import com.ktmmobile.msf.login.dto.AppInfoDTO;

/**
 * @author ANT_FX700_02
 *
 */
public interface AppSvc {
    /**
    * @Description : 사용자 단말기 APP 정보를 가지고 온다.
    * @param String uuid
    * @return Map<String, String>
    * @Author : ant
    * @Create Date : 2016. 2. 27.
    */
    public AppInfoDTO selectUsrAppDetail(AppInfoDTO appInfoDTO);

    /**
    * @Description : 사용자 단말기 APP 정보를 수정한다.
    * @param Map<String, String>
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 27.
    */
    public int updateUsrAppInfo(AppInfoDTO appInfoDTO);

    /**
    * @Description : 사용자 단말기 APP 정보를 입력/수정한다.
    * @param Map<String, String>
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 27.
    */
    public int mergeUsrAppInfo(AppInfoDTO appInfoDTO);

    /**
    * @Description : 사용자 요금제 정보를 가지고 온다.
    * @param String ncn
    * @return Map<String, String>
    * @Author : ant
    * @Create Date : 2016. 2. 27.
    */
    public Map<String, String> selectUsrRateInfo(String ncn);

    /**
    * @Description : OS 종류에 따른 APP 버전 정보를 가지고 온다.
    * @param String os
    * @return Map<String, String>
    * @Author : ant
    * @Create Date : 2016. 2. 29.
    */
    public Map<String, String> selectAppVersion(String os);

    /**
    * @Description : 다회선 대표번호  정보를 수정한다.
    * @param Map<String, String>
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 27.
    */
    public int userRepChange(Map<String, String> map);

}
