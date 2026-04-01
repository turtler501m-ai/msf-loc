package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpDataSharingResVO;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgShareDataReqDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 데이터쉐어링 서비스 구현.
 * ASIS MyShareDataSvcImpl + MyShareDataController 업무 로직 이식.
 * X69/X70/X71 M플랫폼 연동 처리.
 */
@Service
public class SvcChgShareDataSvcImpl implements SvcChgShareDataSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgShareDataSvcImpl.class);

    @Autowired
    private MplatFormSvc mplatFormSvc;

    /**
     * 데이터쉐어링 Step2 화면 데이터.
     * X71 결합목록 + 개통가능시간 조합.
     * ASIS MyShareDataController.dataSharingStep2() 와 동일.
     */
    @Override
    public Map<String, Object> dataSharingStep2(SvcChgShareDataReqDto req) {
        Map<String, Object> result = new HashMap<>();

        // X71 데이터쉐어링 결합 중인 대상 조회
        List<MpDataSharingResVO.Item> sharingList = null;
        if (!isBlank(req.getCtn())) {
            MpDataSharingResVO x71Vo = mplatFormSvc.mosharingList(req.getCustId(), req.getNcn(), req.getCtn());
            if (x71Vo.isSuccess()) {
                sharingList = x71Vo.getItems();
            }
        }

        // 개통 가능 시간 확인
        Map<String, Object> macTimeMap = isSimpleApplyObj();
        boolean isMacTime = (boolean) macTimeMap.getOrDefault("IsMacTime", false);

        result.put("success",     true);
        result.put("sharingList", sharingList);
        result.put("isMacTime",   isMacTime);
        return result;
    }

    /**
     * 셀프개통 가능 시간대 조회.
     * NAC: 08:00~21:50.
     * ASIS AppformSvcImpl.isSimpleApplyObj() 와 동일.
     */
    @Override
    public Map<String, Object> isSimpleApplyObj() {
        Map<String, Object> result = new HashMap<>();
        boolean isMacTime = true;

        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end   = LocalTime.of(21, 50);

        if (now.isBefore(start) || now.isAfter(end)) {
            isMacTime = false;
        }

        result.put("IsMacTime", isMacTime);
        return result;
    }

    /**
     * 데이터쉐어링 개통요청 뷰 데이터.
     * ncn 으로 회선 정보 반환 (TOBE 는 stateless 로 요청 바디에서 직접 전달).
     * ASIS MyShareDataController.dorReqSharingView() 와 동일.
     */
    @Override
    public Map<String, Object> dorReqSharingView(String ncn) {
        Map<String, Object> result = new HashMap<>();
        if (isBlank(ncn)) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ncn)가 없습니다.");
            return result;
        }
        result.put("success",     true);
        result.put("contractNum", ncn);
        return result;
    }

    /**
     * 데이터쉐어링 개통 신청.
     * X69 사전체크 → X70 가입.
     * ASIS MyShareDataController.doinsertOpenRequestAjax() 와 동일.
     */
    @Override
    public Map<String, Object> doinsertOpenRequestAjax(SvcChgShareDataReqDto req) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        // selfShareYn=Y 인 경우만 X69 사전체크 (셀프개통 사전체크)
        MpDataSharingResVO moscDataSharingChk = new MpDataSharingResVO();

        if (req.getSelfShareYn() != null && "Y".equals(req.getSelfShareYn())) {
            req.setCrprCtn("");  // 셀프개통 사전체크

            // X69 개통 사전체크
            moscDataSharingChk = mplatFormSvc.moscDataSharingChk(
                req.getCustId(), req.getNcn(), req.getCtn(), req.getCrprCtn());
        } else {
            rtnMap.put("RESULT_CODE", "E");
        }

        String shareDataYn = "";

        if (moscDataSharingChk.getItems() != null && moscDataSharingChk.getItems().size() > 0) {
            List<MpDataSharingResVO.Item> sharingList = moscDataSharingChk.getItems();

            for (MpDataSharingResVO.Item dto : sharingList) {
                if ("Y".equals(dto.getRsltInd())) {
                    shareDataYn = "Y";
                    break;
                }
            }
        } else {
            rtnMap.put("RESULT_CODE", "E");
        }

        if ("Y".equals(shareDataYn)) {
            // X70 쉐어링 가입
            boolean ok = mplatFormSvc.moscDataSharingSave(
                req.getCustId(), req.getNcn(), req.getCtn(),
                req.getOpmdSvcNo(), "A");
            rtnMap.put("RESULT_CODE", ok ? "S" : "E");
        } else {
            rtnMap.put("RESULT_CODE", "E");
        }

        logger.info("[doinsertOpenRequestAjax] ctn={}, RESULT_CODE={}", req.getCtn(), rtnMap.get("RESULT_CODE"));
        return rtnMap;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
