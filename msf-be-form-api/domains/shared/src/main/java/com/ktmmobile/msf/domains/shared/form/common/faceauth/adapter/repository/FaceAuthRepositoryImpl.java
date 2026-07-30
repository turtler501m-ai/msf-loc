package com.ktmmobile.msf.domains.shared.form.common.faceauth.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.shared.form.common.faceauth.adapter.repository.mybatis.msp.mapper.FaceAuthMcpMapper;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.adapter.repository.mybatis.smartform.mapper.FaceAuthMapper;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.fieldmapper.MsfRequestOsstMapper;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.port.out.FaceAuthRepository;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.McpFathResultPush;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.McpRequestOsst;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.MsfFathResultPush;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.MsfFathSelfUrl;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.MsfRequestOsst;

@RequiredArgsConstructor
@Repository
public class FaceAuthRepositoryImpl implements FaceAuthRepository {

    private final FaceAuthMapper faceAuthMapper;
    private final FaceAuthMcpMapper faceAuthMcpMapper;

    @Override public Integer registMcpRequestOsst(McpRequestOsst mcpRequestOsst) {
        return faceAuthMcpMapper.insertMcpRequestOsst(mcpRequestOsst);
    }

    @Override public Integer copyMcpRequestOsstToMsf(String appEventCd, String resNo) {
        McpRequestOsst mcpOsst = faceAuthMcpMapper.selectMcpRequestOsst(appEventCd, resNo);
        if (mcpOsst != null) {
            MsfRequestOsst msfOsst = MsfRequestOsstMapper.INSTANCE.toMsfRequestOsst(mcpOsst);
            return faceAuthMapper.insertMsfRequestOsst(msfOsst);
        }
        return 0;
    }

    @Override public Integer registFathSelfUrl(MsfFathSelfUrl msfFathSelfUrl) {
        return faceAuthMapper.insertMsfFathSelfUrl(msfFathSelfUrl);
    }

    @Override public McpFathResultPush getMspFathResultPush(String transactionId) {
        return faceAuthMcpMapper.selectMcpFathResultPush(transactionId);
    }

    @Override public MsfFathResultPush getMsfFathResultPush(String resNo) {
        return faceAuthMapper.selectMsfFathResultPush(resNo);
    }

    @Override public Integer registMsfFathResultPushByMsp(McpFathResultPush mcpPush) {
        MsfFathResultPush msfPush = MsfFathResultPush.builder()
            .fathTransacId(mcpPush.getFathTransacId())
            .seq(mcpPush.getSeq())
            .slsCmpcCd(mcpPush.getSlsCmpcCd())
            .identityCd(mcpPush.getRetvCdVal())
            .custNm(mcpPush.getCustNm())
            .custIdfyNo(mcpPush.getCustIdfyNo())
            .issueDate(mcpPush.getIssDateVal())
            .driveLicnsNo(mcpPush.getDriveLicnsNo())
            .idcardPhotoImgNm(mcpPush.getIdcardPhotoImg())
            .idcardCopiesImgNm(mcpPush.getIdcardCopiesImg())
            .mblIdcardQrImgNm(mcpPush.getMblIdcardQrImg())
            .idcardConfWayCd(mcpPush.getIdcardConfWay())
            .distRsrtnYn(mcpPush.getDistRsrtnYn())
            .fathProgrStepCd(mcpPush.getFathProgrStepCd())
            .fathCmpltNtfyDate(mcpPush.getFathCmpltNtfyDt())
            .fathUrlRqtDate(mcpPush.getFathUrlRqtDt())
            .fathResltCd(mcpPush.getFathResltCd())
            .fathResltSbst(mcpPush.getFathResltSbst())
            .fathRqtrId(mcpPush.getFathRqtrId())
            .skipPsblYn(mcpPush.getSkipPsblYn())
            .smsRcvTelNo(mcpPush.getSmsRcvTelNo())
            .build();
        return faceAuthMapper.insertMsfFathResultPush(msfPush);
    }

    @Override public Integer registMsfFathResultPush(MsfFathResultPush msfPush) {
        return faceAuthMapper.insertMsfFathResultPush(msfPush);
    }

}
