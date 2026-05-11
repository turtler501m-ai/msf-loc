package com.ktmmobile.msf.domains.form.form.servicechange.controller;


import java.net.SocketTimeoutException;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChangInfoViewResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ServiceChangeCompleteReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ServiceChangeCompleteResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfSvcChgPageServiceImpl;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.DB_EXCEPTION;

@RestController
public class MsfSvcChgPageController {

    private static final Logger logger = LoggerFactory.getLogger(MsfSvcChgPageController.class);


    @Autowired
    private MsfSvcChgPageServiceImpl msfSvcChgPageServiceImpl;

    /**
     * 휴대폰 회선정보 조회
     * @param McpUserCntrMngDto
     * @return McpUserCntrMngDto
     */
    @RequestMapping(value = "/changePage/cntrListNoLogin", method = RequestMethod.POST)
    public McpUserCntrMngDto cntrListNoLogin(@RequestBody McpUserCntrMngDto param) {

        McpUserCntrMngDto cntrListNoLogin = null;

        try {

            // Database 에서 조회함.
            cntrListNoLogin = msfSvcChgPageServiceImpl.selectCntrListNoLogin(param);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return cntrListNoLogin;
    }


    /**
     * 서비스변경 화면에서 사용할 가입정보 데이터를 조회한다.
     */
    @PostMapping(value = "/api/msf/formServiceChange/changinfo/view")
    public CommonResponse<FormResponse<ChangInfoViewResDto>> getChangInfoView(
            HttpServletRequest request,
            @RequestBody MyPageSearchDto searchVO
    ) {
        return ResponseUtils.ok(msfSvcChgPageServiceImpl.getChangInfoView(request, searchVO));
    }

    /**
     * MCP 휴대폰 회선관리 리스트를 가지고 온다.
     * @param userId
     * @return List<McpUserCntrMngDto>
     */
    @RequestMapping(value = "/changePage/cntrList", method = {RequestMethod.POST,RequestMethod.GET})
    public List<McpUserCntrMngDto> cntrList(@RequestBody String userId) {

        List<McpUserCntrMngDto> cntrList = null;
        try {
            // Database 에서 조회함.
            cntrList = msfSvcChgPageServiceImpl.selectCntrList(userId);
        } catch(DataAccessException e){
            throw new McpCommonJsonException(DB_EXCEPTION);
        }  catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return cntrList;
    }

    /**
     * 서비스변경 신청서 최종 저장
     *
     * 선택된 서비스 변경 항목(요금제·부가서비스·번호변경 등)을
     * MSF DB(MSF_REQUEST_SVC_CHG / MSF_REQUEST_CSTMR / MSF_REQUEST_SVC_CHG_DTL)에 저장.
     *
     * ASIS: 별도 저장 로직 없음 (M포탈 자체 처리)
     * TOBE: POST /api/msf/formServiceChange/{applicationKey}/complete
     *
     * @param applicationKey 신청서 키 (PathVariable)
     * @param req 서비스변경 신청 전체 정보
     * @return 저장 결과 (success/applicationKey)
     */
    @PostMapping("/api/msf/formServiceChange/{applicationKey}/complete")
    public CommonResponse<FormResponse<ServiceChangeCompleteResVO>> complete(
        @PathVariable("applicationKey") String applicationKey,
        @RequestBody ServiceChangeCompleteReqDto req
    ) throws SocketTimeoutException {
        return ResponseUtils.ok(msfSvcChgPageServiceImpl.complete(applicationKey, req));
    }


}
