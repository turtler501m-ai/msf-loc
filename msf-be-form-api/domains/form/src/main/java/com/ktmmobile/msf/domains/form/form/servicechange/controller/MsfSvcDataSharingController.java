package com.ktmmobile.msf.domains.form.form.servicechange.controller;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.OPER_TYPE_NEW;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.CONTPNT_SHOP_ID_MSHOP;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.AppformReqDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ServiceChangeCompleteReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfSvcDataSharingSvcImpl;

@Slf4j
@RestController
public class MsfSvcDataSharingController {

    @Autowired
    private MsfSvcDataSharingSvcImpl msfSvcDataSharingSvcImpl;

    @PostMapping("/api/msf/formServiceChange/dataSharing/save")
    public CommonResponse<FormResponse<Map<String, Object>>> saveDataSharingSimple(
        @RequestBody ServiceChangeCompleteReqDto req
    ) {
        ServiceChangeCompleteReqDto.DataSharing ds = req != null ? req.getDataSharing() : null;
        log.info("[dataSharingSave] request: ncn={}, ctn={}, shareUsimNum={}, sharePhoneNum={}",
            req != null ? req.getNcn() : null,
            req != null ? req.getCtn() : null,
            ds != null ? ds.getShareUsimNum() : null,
            ds != null ? ds.getSharePhoneNum() : null);
        AppformReqDto dataSharingReqDto = toAppformReqDto(req);
        FormResponse<Map<String, Object>> result = msfSvcDataSharingSvcImpl.saveDataSharingSimple(dataSharingReqDto);
        log.info("[dataSharingSave] result: resCode={}, resMessage={}, tlphNo={}",
            result != null ? result.resCode() : null,
            result != null ? result.resMessage() : null,
            result != null && result.resData() != null ? result.resData().get("tlphNo") : null);
        if (result != null && !"0000".equals(result.resCode())) {
            return CommonResponse.of(result.resCode(), result.resMessage(), result);
        }
        return ResponseUtils.ok(result);
    }

    private AppformReqDto toAppformReqDto(ServiceChangeCompleteReqDto req) {
        if (req == null || req.getDataSharing() == null) {
            throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
        }

        ServiceChangeCompleteReqDto.DataSharing dataSharing = req.getDataSharing();
        AppformReqDto dataSharingReqDto = new AppformReqDto();
        dataSharingReqDto.setContractNum(req.getNcn());
        dataSharingReqDto.setReqUsimSn(dataSharing.getShareUsimNum());
        String mobileNo = StringUtil.NVL(req.getCtn(), "");
        if (!StringUtils.hasText(mobileNo)) {
            mobileNo = StringUtil.NVL(req.getMobileNo1(), "")
                + StringUtil.NVL(req.getMobileNo2(), "")
                + StringUtil.NVL(req.getMobileNo3(), "");
        }
        dataSharingReqDto.setMobileNo(mobileNo);
        dataSharingReqDto.setCstmrType(req.getCstmrTypeCd());
        dataSharingReqDto.setCstmrMobileFn(req.getMobileNo1());
        dataSharingReqDto.setCstmrMobileMn(req.getMobileNo2());
        dataSharingReqDto.setCstmrMobileRn(req.getMobileNo3());
        // 데이터쉐어링 신청서는 AS-IS NICE 세션을 직접 조회하지 않고 서식지 요청의 인증 결과를 전달한다.
        dataSharingReqDto.setOnlineAuthType(req.getOnlineAuthType());
        dataSharingReqDto.setOnlineAuthInfo(req.getOnlineAuthInfo());
        dataSharingReqDto.setSelfCstmrCi(req.getSelfCstmrCi());
        // 데이터쉐어링 PC0는 AS-IS와 달리 MP 사전체크를 직접 호출하므로 신분증 인증값을 전달한다.
        dataSharingReqDto.setSelfCertType(req.getSelfCertType());
        dataSharingReqDto.setSelfIssuExprDt(req.getSelfIssuExprDt());
        dataSharingReqDto.setSelfIssuNum(req.getSelfIssuNum());
        dataSharingReqDto.setOnOffType("Y".equals(NmcpServiceUtils.isMobile()) ? "7" : "5");
        dataSharingReqDto.setCntpntShopId(StringUtil.NVL(req.getCntpntShopCd(), CONTPNT_SHOP_ID_MSHOP));
        dataSharingReqDto.setAgentCode(req.getAgentCd());
        dataSharingReqDto.setManagerCode(req.getManagerCd());
        dataSharingReqDto.setCpntId(req.getCpntId());
        dataSharingReqDto.setPrdtSctnCd("LTE");
        dataSharingReqDto.setOperType(OPER_TYPE_NEW);
        String reqWantNumber = StringUtil.NVL(dataSharing.getSharePhoneNum(), "");
        if (!StringUtils.hasText(reqWantNumber)) {
            reqWantNumber = mobileNo;
        }
        dataSharingReqDto.setReqWantNumber(reqWantNumber.length() >= 4 ? reqWantNumber.substring(reqWantNumber.length() - 4) : reqWantNumber);
        return dataSharingReqDto;
    }
}
