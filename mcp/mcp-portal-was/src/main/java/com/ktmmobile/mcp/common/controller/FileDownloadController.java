package com.ktmmobile.mcp.common.controller;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.BIDING_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.NOTFOUND_FILE_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.NO_SESSION_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.mcp.common.view.DownloadView.DOWNLOAD_FILE;
import static com.ktmmobile.mcp.common.view.DownloadView.DOWNLOAD_VIEW;
import static com.ktmmobile.mcp.common.view.DownloadView.ORIGINAL_FILE_NAME;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ktmmobile.mcp.board.dto.FileDownloadDto;

import com.ktmmobile.mcp.board.service.FileBoardSvc;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.policy.service.PolicyOfUseSvc;




/**
 * @Class Name : FileDownloadController
 * @Description : 파일다운로드 SAMPLE
 *
 * @author : ant
 * @Create Date : 2016. 1. 15.
 */
@Controller
public class FileDownloadController {

    private static Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

    /** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;
    /** 파일 웹경로*/
    @Value("${common.fileupload.web}")
    private String fileuploadWebBase;

    @Autowired
    PolicyOfUseSvc policyOfUseSvc;

    @Autowired
    FileBoardSvc fileBoardSvc;


    /** 디비 종류 */
    private final static int NMCP_BOARD_ATT_DTL_FLAG =1;
    private final static int NMCP_STPLT_DTL_FLAG =2;

    @RequestMapping(value="/fileDownload.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String phoneMcpProductPoupup(@ModelAttribute FileDownloadDto inputFileDownloadDto,Model model,  BindingResult br) {

        //중복요청 체크 제외 처리 ( 안드로이드 특정폰에서 두번 호출 되는 오류 있음)
        /*ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/fileDownload.do?flag="+inputFileDownloadDto.getFlag()+"&fileSeq=" + inputFileDownloadDto.getFileSeq() );


        if (SessionUtils.overlapRequestCheck(checkOverlapDto,2)) {
            throw new McpCommonJsonException("001",TIME_OVERLAP_EXCEPTION);
        }*/

        if (br.hasErrors()) {
              throw new McpCommonException(BIDING_EXCEPTION);
        }

        //파일 경로나 이름이 안들어올경우 처리
        if(inputFileDownloadDto.getFileSeq()!= 0 && "".equals(inputFileDownloadDto.getFlag()) && null == inputFileDownloadDto.getFlag()){
            throw new McpCommonException(NOTFOUND_FILE_EXCEPTION);
        }

        FileDownloadDto fileDownloadDto = new FileDownloadDto();
        switch (Integer.parseInt(inputFileDownloadDto.getFlag())) {
            case NMCP_BOARD_ATT_DTL_FLAG:
                fileDownloadDto=fileBoardSvc.selectFileDownload(inputFileDownloadDto.getFileSeq());
            break;

            case NMCP_STPLT_DTL_FLAG:
                fileDownloadDto=policyOfUseSvc.getPolicyFile(inputFileDownloadDto.getFileSeq());
                break;

            default:
                break;
        }

        //웹 경로 전용인 /resources/upload 를 제거하여 경로를 맞춘다.
        String convertFilePath =  java.util.regex.Matcher.quoteReplacement( java.util.regex.Matcher.quoteReplacement(fileDownloadDto.getFilePath()));

        convertFilePath = convertFilePath.replaceAll(fileuploadWebBase, "/");
        convertFilePath = convertFilePath.replaceAll("/resources/", "/");
        convertFilePath = convertFilePath.replaceAll("/upload/", "/");
        convertFilePath = convertFilePath.replaceAll("upload/", "/");

        File f = new File(fileuploadBase + convertFilePath);		//리네임된 파일명을 포함한 실제 물리 저장소로 파일객체 생성
        model.addAttribute(DOWNLOAD_FILE, f);		//File 객체 setting
        model.addAttribute(ORIGINAL_FILE_NAME, fileDownloadDto.getFileNm());	//실제 원본 파일명 (확장자를 포함한)
        return DOWNLOAD_VIEW;
    }


}
