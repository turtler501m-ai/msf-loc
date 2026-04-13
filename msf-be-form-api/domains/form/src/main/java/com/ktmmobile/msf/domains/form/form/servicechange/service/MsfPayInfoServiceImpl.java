// TOBESKIP: TOBE 미구현 — 결제정보 이미지 생성 기능 전체 미구현.
// ASIS 원본 로직은 삭제 금지. 메서드 시그니처 보존 후 본문만 stub 처리한다.
package com.ktmmobile.msf.domains.form.form.servicechange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.payinfo.dto.PayInfoDto;

// [ASIS] import com.ktmmobile.msf.payinfo.dao.PayInfoDao; — TOBE 미구현 (PayInfoDao 미존재)
// [ASIS] import com.ktmmobile.msf.payinfo.dto.EvidenceDto; — TOBE 미구현 (EvidenceDto 미존재)
// [ASIS] import com.ktmmobile.msf.payinfo.dto.PayInfoFormDto; — TOBE 미구현 (PayInfoFormDto 미존재)
// [ASIS] import CNPECMJava.CNPEncryptModule.crypto.FileHandle; — TOBE 미구현 (외부 암호화 라이브러리 미존재)
// [ASIS] import com.ktds.crypto.exception.CryptoException; — TOBE 미구현 (crypto 라이브러리 미존재)
// [ASIS] import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
// [ASIS] import java.awt.*; 등 이미지 생성 관련 import — TOBE 미구현

@Service
public class MsfPayInfoServiceImpl implements MsfPayInfoService {
    private final Logger logger = LoggerFactory.getLogger(MsfPayInfoServiceImpl.class);

    // [ASIS] @Value("${payInfo.org.imgPath}") private String orgImgPath; — TOBE 미구현
    // [ASIS] @Value("${payInfo.org.fontPath}") private String orgFontPath; — TOBE 미구현
    // [ASIS] @Value("${payInfo.end.imgPath}") private String endImgPath; — TOBE 미구현
    // [ASIS] @Autowired PayInfoDao dao; — TOBE 미구현 (PayInfoDao 미존재)

    // TOBESKIP: TOBE 미구현 — 결제정보 이미지 생성 (변경신청서 이미지 신규 생성)
    // [ASIS 원본 로직] 시퀀스 채번 → 암호화 → DB 저장 → 이미지 생성 → 이미지 암호화 → MSP 등록
    @Override
    public void makePayInfoImage(PayInfoDto dto) {
        // TOBESKIP: TOBE 미구현
        logger.debug("makePayInfoImage: TOBESKIP - TOBE 미구현");
    }

    // TOBESKIP: TOBE 미구현 — 결제정보 이미지 재생성 (기존 이미지 없을 때 재시도)
    @Override
    public String remakePayInfoImage(PayInfoDto dto) {
        // TOBESKIP: TOBE 미구현
        logger.debug("remakePayInfoImage: TOBESKIP - TOBE 미구현");
        return null;
    }

    // TOBESKIP: TOBE 미구현 — payInfo DB 직접 저장
    @Override
    public void insetPayInfo(PayInfoDto dto) {
        // TOBESKIP: TOBE 미구현
        logger.debug("insetPayInfo: TOBESKIP - TOBE 미구현");
    }

    // TOBESKIP: TOBE 미구현 — payInfo 상태 저장 (CREATEYN 값에 따라 저장/업데이트/이미지 생성 분기)
    // 참조처: MsfPaywayServiceImpl.farChgWayChg — 계좌이체 납부방법 변경 시 호출됨
    @Override
    public PayInfoDto savePayInfoHist(PayInfoDto payInfoDto) {
        // TOBESKIP: TOBE 미구현
        logger.debug("savePayInfoHist: TOBESKIP - TOBE 미구현");
        PayInfoDto result = new PayInfoDto();
        result.setResult(false);
        return result;
    }

}
