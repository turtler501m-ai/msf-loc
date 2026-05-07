package com.ktmmobile.msf.domains.form.form.servicechange.controller;


import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChangInfoViewResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfChangPageSvc;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfCustRequestScanService;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMaskingSvc;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMypageSvc;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.DB_EXCEPTION;

@RestController
public class MsfChangePageController {

    private static final Logger logger = LoggerFactory.getLogger(MsfChangePageController.class);


    @Autowired
    private MsfChangPageSvc msfChangPageSvc;

    @Autowired
    private MsfMypageSvc msfMypageSvc;

    @Autowired
    private MsfMaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private MsfCustRequestScanService custRequestScanService;


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
            cntrListNoLogin = msfChangPageSvc.selectCntrListNoLogin(param);

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
        return ResponseUtils.ok(msfChangPageSvc.getChangInfoView(request, searchVO));
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
            cntrList = msfChangPageSvc.selectCntrList(userId);
        } catch(DataAccessException e){
            throw new McpCommonJsonException(DB_EXCEPTION);
        }  catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return cntrList;
    }



}
