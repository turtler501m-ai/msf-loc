package com.ktmmobile.mcp.content.controller;

import javax.servlet.http.HttpServletRequest;

import com.ktmmobile.mcp.board.dto.FileBoardDTO;
import com.ktmmobile.mcp.board.service.FileBoardSvc;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.util.SessionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ComContentController {

    private static final Logger logger = LoggerFactory.getLogger(ComContentController.class);

    @Autowired
    FileBoardSvc fileBoardSvc;

    @RequestMapping(value = {"/content/insrView.do","/m/content/insrView.do"})
    public String insrView(HttpServletRequest request, Model model) {

        String returnUrl = "/portal/content/insrView";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl="/mobile/content/insrView";
        }

        // 로그인 여부 추가 (20230524)
        String loginYn= "Y";
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) loginYn= "N";

        model.addAttribute("loginYn", loginYn);

        return returnUrl;
    }

    /**
     * 자급제 보상 서비스 소개(안내) 페이지
     * @author hsy
     * @Date : 2023.05.24
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/content/rwdView.do","/m/content/rwdView.do"})
    public String rwdView(HttpServletRequest request, Model model) {

        // 1. 리턴페이지 설정
        String jspPageName = "/portal/content/rwdView";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            jspPageName = "/mobile/content/rwdView";
        }

        // 2. 로그인 여부 확인
        String loginYn= "Y";
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) loginYn= "N";

        model.addAttribute("loginYn", loginYn);

        return jspPageName;
    }

    /**
     * 파일 다운로드 시퀀스 조회
     * @param request
     * @param fileBoardDTO
     * @return Map<String, Object>
     */
    @RequestMapping(value="/content/fileInfo.do")
    @ResponseBody
    public Map<String, Object> fileInfo(HttpServletRequest request, @ModelAttribute FileBoardDTO fileBoardDTO) {

        HashMap<String, Object> fileInfo = new HashMap<String, Object>();

        int boardSeq = fileBoardDTO.getBoardSeq();
        FileBoardDTO selectFile = null;
        try {
            selectFile = fileBoardSvc.selectFileInfo(boardSeq);
        } catch(DataAccessException e) {
            logger.debug("fileInfo DataAccessException occured");
        } catch (Exception e){
            logger.debug("fileInfo Exception occured");
        }

        fileInfo.put("fileInfo",selectFile);
        return fileInfo;
    }
}


