package com.ktmmobile.msf.form.servicechange.service;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import com.ktmmobile.msf.form.servicechange.dto.AdditionApplyReqDto;
import com.ktmmobile.msf.form.servicechange.dto.AdditionApplyResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionAvailableResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionMyListResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionReqDto;
import com.ktmmobile.msf.form.servicechange.dto.McpRegServiceDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpAddSvcInfoParamDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpSocVO;

public interface MsfRegSvcService {

    // =====================================================
    // TOBE 메서드
    // =====================================================

    // 이용중 부가서비스 목록 (X97 + getMspRateMst)
    AdditionMyListResVO getMyAddSvcList(AdditionReqDto req);

    // 가입가능 부가서비스 목록 (X97 + DB)
    AdditionAvailableResVO getAvailableAddSvcList(AdditionReqDto req);

    // 부가서비스 해지 (getMspRateMst 체크 + X38)
    AdditionApplyResVO cancelAddSvc(AdditionApplyReqDto req);

    // 부가서비스 신청 (Y25, 선해지 포함)
    AdditionApplyResVO regAddSvc(AdditionApplyReqDto req);

    // =====================================================
    // [ASIS] 기존 메서드 — 변환 완료 후 주석 처리
    // =====================================================

    // [ASIS] X20 사용 → getAvailableAddSvcList()로 대체
    // List<String> selectAddSvcInfoDto(String ncn, String ctn, String custId);

    // [ASIS] X21 사용 → regAddSvc()로 대체
    // MpRegSvcChgVO regSvcChg(String ncn, String ctn, String custId, String soc, String ftrNewParam) throws SocketTimeoutException;

    // [ASIS] X97 사용, MyPageSearchDto 의존 → getMyAddSvcList()로 대체
    // MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId);
    // MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId, String lstComActvDate);

    // [ASIS] Map 반환, MyPageSearchDto 의존 → cancelAddSvc()로 대체
    // Map<String, Object> moscRegSvcCanChg(MyPageSearchDto searchVO, String rateAdsvcCd) throws SocketTimeoutException;
    // Map<String, Object> moscRegSvcCanChgSeq(MyPageSearchDto searchVO, String rateAdsvcCd, String prodHstSeq) throws SocketTimeoutException;

    // [ASIS] 내부 유틸 메서드 — ServiceImpl 내부로 이동
    // void getMpSocListByDiv(List<MpSocVO> mpSocList, String addDivCd);
    // void getMcpRegServiceListByDiv(List<McpRegServiceDto> mcpRegServiceList, String addDivCd);
    // String getUpdateYn(MpSocVO mpSoc);
    // String getEndDttmUsePrd(MpSocVO mpSoc, String usePrd);
    // String getCtnByNcn(String ncn, boolean flagMasking);
}
