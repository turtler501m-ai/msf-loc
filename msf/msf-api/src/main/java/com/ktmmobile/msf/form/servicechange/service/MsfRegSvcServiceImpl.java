
package com.ktmmobile.msf.form.servicechange.service;

import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.NO_EXSIST_RATE;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.NO_ONLINE_CAN_CHANGE_ADD;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ktmmobile.msf.form.servicechange.dao.RegSvcDao;
import com.ktmmobile.msf.form.servicechange.dto.AdditionApplyReqDto;
import com.ktmmobile.msf.form.servicechange.dto.AdditionApplyResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionAvailableResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionMyListResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionReqDto;
import com.ktmmobile.msf.form.servicechange.dto.McpRegServiceDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.vo.MpAddSvcInfoParamDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpMoscRegSvcCanChgInVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.system.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.msf.system.common.service.FCommonSvc;
import com.ktmmobile.msf.system.common.util.StringUtil;

@Service
public class MsfRegSvcServiceImpl implements MsfRegSvcService {

    private static Logger logger = LoggerFactory.getLogger(MsfRegSvcService.class);

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private RegSvcDao regSvcDao;

    @Autowired
    private MsfMypageSvc msfMypageSvc;

    // [ASIS] MsfMypageUserService — TOBE 메서드에서 미사용, 세션 기반 ASIS 전용
    // @Autowired
    // private MsfMypageUserService mypageUserService;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    // =====================================================
    // TOBE 메서드
    // =====================================================

    /**
     * 이용중 부가서비스 목록 (X97 + getMspRateMst)
     * ASIS selectmyAddSvcList() 로직 이식, 파라미터 AdditionReqDto로 교체
     */
    @Override
    public AdditionMyListResVO getMyAddSvcList(AdditionReqDto req) {
        MpAddSvcInfoParamDto vo = new MpAddSvcInfoParamDto();
        try {
            // [1] X97 호출
            vo = mPlatFormService.getAddSvcInfoParamDto(req.getNcn(), req.getCtn(), req.getCustId());
            if (!vo.isSuccess()) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }

            List<MpSocVO> mSocVoList = vo.getList();

            if (mSocVoList != null) {
                for (MpSocVO mSocVo : mSocVoList) {
                    // [3] getMspRateMst → canCmnt, onlineCanYn 세팅
                    MspRateMstDto mspRateMstDto = fCommonSvc.getMspRateMst(mSocVo.getSoc());
                    if (mspRateMstDto != null) {
                        mSocVo.setCanCmnt(StringUtil.NVL(mspRateMstDto.getCanCmnt(), ""));
                        mSocVo.setOnlineCanYn(StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), ""));
                    }
                    // [3] REG_SVC_CD_4 → 강제 onlineCanYn = "N"
                    if (Constants.REG_SVC_CD_4.equals(mSocVo.getSoc())) {
                        mSocVo.setOnlineCanYn("N");
                    }
                }

                // [2] "PL249Q800" 더미 SOC 제거
                mSocVoList.removeIf(item -> "PL249Q800".equals(item.getSoc()));
            }

        } catch (SocketTimeoutException e) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e) {
            throw new McpCommonException(e.getMessage());
        }

        AdditionMyListResVO res = new AdditionMyListResVO();
        res.setList(vo.getList());
        return res;
    }

    /**
     * 가입가능 부가서비스 목록 (X97 + DB)
     * ASIS selectAddSvcInfoDto() X20 → X97 교체
     */
    @Override
    public AdditionAvailableResVO getAvailableAddSvcList(AdditionReqDto req) {
        // [1] X97 호출 → 이용중 SOC 목록 추출
        List<String> useSocList = new ArrayList<>();
        try {
            MpAddSvcInfoParamDto vo = mPlatFormService.getAddSvcInfoParamDto(req.getNcn(), req.getCtn(), req.getCustId());
            if (!vo.isSuccess()) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }
            List<MpSocVO> mSocVoList = vo.getList();
            if (mSocVoList != null) {
                for (MpSocVO mSocVo : mSocVoList) {
                    useSocList.add(mSocVo.getSoc());
                }
            }
        } catch (SocketTimeoutException e) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e) {
            throw new McpCommonException(e.getMessage());
        }

        // [2] DB 부가서비스 관리 목록
        List<McpRegServiceDto> tmpList = msfMypageSvc.selectRegService(req.getNcn());
        List<McpRegServiceDto> list = new ArrayList<>(tmpList);

        List<McpRegServiceDto> listA = new ArrayList<>();
        List<McpRegServiceDto> listC = new ArrayList<>();

        // [4] "PL249Q800" 더미 SOC 필터링
        list.removeIf(item -> "PL249Q800".equals(item.getRateCd()));

        // [3] useSocList 기준 useYn 매핑
        for (McpRegServiceDto item : list) {
            item.setUseYn(useSocList.contains(item.getRateCd()) ? "Y" : "N");

            if ((item.getBaseAmt().equals("0") && item.getSvcRelTp().equals("C"))
                    || item.getSvcRelTp().equals("B")) {
                listC.add(item);
            } else {
                listA.add(item);
            }
        }

        AdditionAvailableResVO res = new AdditionAvailableResVO();
        res.setList(list);
        res.setListA(listA);
        res.setListC(listC);
        return res;
    }

    /**
     * 부가서비스 해지 (getMspRateMst 체크 + X38)
     * ASIS moscRegSvcCanChg() 로직 이식, 파라미터 교체
     */
    @Override
    public AdditionApplyResVO cancelAddSvc(AdditionApplyReqDto req) {
        try {
            // [1] getMspRateMst → onlineCanYn 확인
            MspRateMstDto mspRateMstDto = fCommonSvc.getMspRateMst(req.getSoc());
            if (mspRateMstDto == null) {
                return new AdditionApplyResVO(false, NO_EXSIST_RATE);
            }
            String onlineCanYn = StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "");
            if (!"Y".equals(onlineCanYn)) {
                return new AdditionApplyResVO(false, NO_ONLINE_CAN_CHANGE_ADD);
            }

            // [2] X38 해지
            MpMoscRegSvcCanChgInVO vo;
            if (req.getProdHstSeq() != null && !req.getProdHstSeq().isEmpty()) {
                vo = mPlatFormService.moscRegSvcCanChgSeq(req.getNcn(), req.getCtn(), req.getCustId(),
                        req.getSoc(), req.getProdHstSeq());
            } else {
                vo = mPlatFormService.moscRegSvcCanChg(req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc());
            }

            if (!vo.isSuccess()) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }

        } catch (SocketTimeoutException e) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e) {
            return new AdditionApplyResVO(false, e.getMessage());
        }

        return new AdditionApplyResVO(true);
    }

    /**
     * 부가서비스 신청 (Y25, 선해지 포함)
     * ASIS regSvcChgAjax 로직에서 X21 → Y25 교체, 세션 제거
     */
    @Override
    public AdditionApplyResVO regAddSvc(AdditionApplyReqDto req) {
        try {
            // [1] 선해지 필요한 경우 (flag="Y"이면 변경: 해지 후 신청)
            if ("Y".equals(req.getFlag())) {
                AdditionApplyResVO cancelRes = cancelAddSvc(req);
                if (!cancelRes.isSuccess()) {
                    return cancelRes;
                }
            }

            // [2] Y25 호출 — 부가서비스 신청
            // [ASIS] 인증 STEP 검증 — 공통 미구현 (31번 §1-3)
            // certService.getStepCnt(ncn, ctn);
            // certService.vdlCertInfo(ncn, ctn, custId);

            mPlatFormService.regSvcChgY25(req.getNcn(), req.getCtn(), req.getCustId(),
                    req.getSoc(), req.getFtrNewParam());

            // [ASIS] 포인트 처리 — 포인트 기능 미이관
            // if (Constants.REG_SVC_CD_4.equals(req.getSoc())) {
            //     pointService.editPoint(custId, req.getSoc());
            // }

        } catch (SocketTimeoutException e) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e) {
            return new AdditionApplyResVO(false, e.getMessage());
        }

        return new AdditionApplyResVO(true);
    }

    // =====================================================
    // private 유틸 메서드 (ASIS에서 이관)
    // =====================================================

    private void getMpSocListByDiv(List<MpSocVO> mpSocList, String addDivCd) {
        if (addDivCd == null || "".equals(addDivCd)) return;
        if (mpSocList == null || mpSocList.isEmpty()) return;
        Iterator<MpSocVO> iter = mpSocList.iterator();
        List<String> roamCdList = regSvcDao.getRoamCdList();
        while (iter.hasNext()) {
            MpSocVO mpSoc = iter.next();
            if (chkRemove(mpSoc.getSoc(), addDivCd, roamCdList)) {
                iter.remove();
            }
        }
    }

    private void getMcpRegServiceListByDiv(List<McpRegServiceDto> mcpRegServiceList, String addDivCd) {
        if (addDivCd == null || "".equals(addDivCd)) return;
        Iterator<McpRegServiceDto> iter = mcpRegServiceList.iterator();
        List<String> roamCdList = regSvcDao.getRoamCdList();
        while (iter.hasNext()) {
            McpRegServiceDto mcpRegService = iter.next();
            if (chkRemove(mcpRegService.getRateCd(), addDivCd, roamCdList)) {
                iter.remove();
            }
        }
    }

    private boolean chkRemove(String soc, String addDivCd, List<String> roamCdList) {
        if ("G".equals(addDivCd)) {
            for (String roamCd : roamCdList) {
                if (roamCd.equals(soc)) return true;
            }
            return false;
        } else if ("R".equals(addDivCd)) {
            for (String roamCd : roamCdList) {
                if (roamCd.equals(soc)) return false;
            }
            return true;
        }
        return false;
    }

    // =====================================================
    // [ASIS] 기존 메서드 — TOBE 전환 완료로 주석 처리
    // =====================================================

    // [ASIS] X20 사용 → getAvailableAddSvcList()로 대체
    // @Override
    // public List<String> selectAddSvcInfoDto(String ncn, String ctn, String custId) { ... }

    // [ASIS] X21 사용 → regAddSvc()로 대체
    // @Override
    // public MpRegSvcChgVO regSvcChg(String ncn, String ctn, String custId, String soc, String ftrNewParam) throws SocketTimeoutException { ... }

    // [ASIS] X97 사용, MyPageSearchDto 의존 → getMyAddSvcList()로 대체
    // @Override
    // public MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId) { ... }
    // @Override
    // public MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId, String lstComActvDate) { ... }

    // [ASIS] Map 반환, MyPageSearchDto 의존 → cancelAddSvc()로 대체
    // @Override
    // public Map<String, Object> moscRegSvcCanChg(MyPageSearchDto searchVO, String rateAdsvcCd) throws SocketTimeoutException { ... }
    // @Override
    // public Map<String, Object> moscRegSvcCanChgSeq(MyPageSearchDto searchVO, String rateAdsvcCd, String prodHstSeq) throws SocketTimeoutException { ... }

    // [ASIS] 인터페이스 공개 메서드 → private 내부 메서드로 이동
    // @Override
    // public void getMpSocListByDiv(List<MpSocVO> mpSocList, String addDivCd) { ... }
    // @Override
    // public void getMcpRegServiceListByDiv(List<McpRegServiceDto> mcpRegServiceList, String addDivCd) { ... }
    // @Override
    // public String getUpdateYn(MpSocVO mpSoc) { ... }
    // @Override
    // public String getEndDttmUsePrd(MpSocVO mpSoc, String usePrd) { ... }
    // @Override
    // public String getCtnByNcn(String ncn, boolean flagMasking) { ... }
}
