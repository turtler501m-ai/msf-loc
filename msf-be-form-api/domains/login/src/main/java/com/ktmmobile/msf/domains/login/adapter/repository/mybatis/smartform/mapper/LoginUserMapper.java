package com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.row.FormLoginUserInfoRow;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.row.FormLoginUserRow;

@AutoAuditing
@Mapper
public interface LoginUserMapper {

    /**
     * Form 사용자 ID 기준 로그인 사용자 조회
     *
     * @param userId 사용자 ID
     * @return Form 로그인 사용자 Row
     */
    FormLoginUserRow selectFormByUserId(String userId);

    /**
     * Form 사용자 ID와 단말 UUID 기준 로그인 사용자 조회
     *
     * @param userId 사용자 ID
     * @param deviceUuid 단말 UUID
     * @return Form 로그인 사용자 Row
     */
    FormLoginUserRow selectFormByUserIdAndDeviceUuid(
        @Param("userId") String userId,
        @Param("deviceUuid") String deviceUuid
    );

    /**
     * 승인 단말 소유 사용자 ID 목록 조회
     *
     * @param deviceUuid 단말 UUID
     * @return 사용자 ID 목록
     */
    List<String> selectApprovedDeviceOwnerUserIds(String deviceUuid);

    /**
     * 승인된 생체인증 가능 단말 존재 여부 조회
     *
     * @param deviceUuid 단말 UUID
     * @return 존재 여부
     */
    boolean existsApprovedBiometricDevice(String deviceUuid);

    /**
     * 인증 사용자의 승인된 생체인증 가능 단말 존재 여부 조회
     *
     * @param userId 사용자 ID
     * @param deviceUuid 단말 UUID
     * @return 존재 여부
     */
    boolean existsApprovedBiometricDeviceByUserId(
        @Param("userId") String userId,
        @Param("deviceUuid") String deviceUuid
    );

    /**
     * 승인된 생체인증 등록 사용자 ID 조회
     *
     * @param deviceUuid 단말 UUID
     * @param bioKey 생체인증 키
     * @return 사용자 ID
     */
    String selectApprovedBiometricCredentialUserId(
        @Param("deviceUuid") String deviceUuid,
        @Param("bioKey") String bioKey
    );

    /**
     * 인증 사용자의 승인된 생체인증 등록 정보 존재 여부 조회
     *
     * @param userId 사용자 ID
     * @param deviceUuid 단말 UUID
     * @param bioKey 생체인증 키
     * @return 존재 여부
     */
    boolean existsApprovedBiometricCredential(
        @Param("userId") String userId,
        @Param("deviceUuid") String deviceUuid,
        @Param("bioKey") String bioKey
    );

    /**
     * Form 사용자 ID 기준 사용자 정보 조회
     *
     * @param userId 사용자 ID
     * @return Form 사용자 정보 Row
     */
    FormLoginUserInfoRow selectFormUserInfoByUserId(String userId);

    /**
     * Form 사용자 ID와 단말 UUID 기준 사용자 정보 조회
     *
     * @param userId 사용자 ID
     * @param deviceUuid 단말 UUID
     * @return Form 사용자 정보 Row
     */
    FormLoginUserInfoRow selectFormUserInfoByUserIdAndDeviceUuid(
        @Param("userId") String userId,
        @Param("deviceUuid") String deviceUuid
    );

    /**
     * Form 로그인 성공 정보 갱신
     *
     * @param userId 사용자 ID
     * @return 갱신 건수
     */
    int updateFormLoginSuccess(String userId);

    /**
     * Form 로그인 실패 정보 갱신
     *
     * @param userId 사용자 ID
     * @param userStatusCode 사용자 상태 코드
     * @return 갱신 건수
     */
    int updateFormLoginFailure(
        @Param("userId") String userId,
        @Param("userStatusCode") String userStatusCode
    );

    /**
     * Form 사용자 이력 등록
     *
     * @param userId 사용자 ID
     * @return 등록 건수
     */
    int insertFormUserHistory(String userId);

    /**
     * Form 단말 로그인 성공 정보 갱신
     *
     * @param userId 사용자 ID
     * @param deviceUuid 단말 UUID
     * @return 갱신 건수
     */
    int updateFormDeviceLoginSuccess(
        @Param("userId") String userId,
        @Param("deviceUuid") String deviceUuid
    );

}
