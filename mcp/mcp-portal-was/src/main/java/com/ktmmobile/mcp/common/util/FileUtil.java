package com.ktmmobile.mcp.common.util;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.DENINED_FILE_TYPE_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.EXCEED_FILE_SIZE_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.FILE_UPLOAD_EXCEPTION;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.mcp.common.exception.McpCommonException;

import CNPECMJava.CNPEncryptModule.crypto.FileHandle;


/**
 * @Class Name : FileUtil
 * @Description : 파일업로드 관련 유틸 클래스
 *
 * @author : ant
 * @Create Date : 2016. 1. 5.
 */
@Component
public class FileUtil {

    /** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    /** 파일업로드 접근가능한 웹경로 */
    @Value("${common.fileupload.web}")
    private String fileuploadWeb;

    /** 첨부 불가능한 파일유형  */
    @Value("${common.fileupload.ext.denined}")
    private String fileUploadExtDenined;

    /** 첨부 불가능한 파일유형  */
    @Value("${common.fileupload.size}")
    private long fileuploadSize;

    /** 유심regi 파일경로  */
    @Value("${common.fileupload.sellusim}")
    private String sellUsimUrl;

    /** 윤리위반 신고 첨부 파일경로  */
    @Value("${common.fileupload.report}")
    private String reportUrl;

    /** 첨부 불가능한 파일유형  */
    private String fileOriginNm;

    /** 첨부 불가능한 파일유형  */
    private int fileSize;

    /** 윤리위반신고 첨부 최대 사이즈  */
    @Value("${common.fileupload.report.size}")
    private long reportFileuploadSize;


    /**
    * @Description :
    *  파일을 web 에서 접근 가능한 경로에 업로드하고 (기본 업로드경로에 업무 경로 추가)
    *  접속가능한 웹경로를 리턴한다.
    * @param multipartFile
    * @param subPath
    * @return 업로드된 파일 접근가능한 웹경로
    * @Author : ant
    * @Create Date : 2016. 1. 5.
    */
    public  String fileTrans(MultipartFile multipartFile,String subPath) {

        synchronized(this) {


          //TODO:파일 사이즈,파일용량 제한 처리
            String end_file_path = "" ;
           ////변경신청서 암호화 이미지 경로
            String end_file_enc_path;
            String lowPath    = "default";

            if (subPath != null) {
                lowPath = subPath;
            }

            String uploadPath   = "";//파일 업로드 경로
            if(null != subPath  && subPath.equals("sellUsim")){
                Date d = new Date();
                SimpleDateFormat date = new SimpleDateFormat("yyyy", Locale.KOREA);
                String yyPath = date.format(d);
                date = new SimpleDateFormat("MM", Locale.KOREA);
                String mmPath = date.format(d);
                uploadPath   = sellUsimUrl + File.separator +yyPath+ File.separator +mmPath;
            } else if (null != subPath && subPath.equals("report")) {
                Date d = new Date();
                SimpleDateFormat date = new SimpleDateFormat("yyyy", Locale.KOREA);
                String yyPath = date.format(d);
                date = new SimpleDateFormat("MM", Locale.KOREA);
                String mmPath = date.format(d);
                uploadPath   = reportUrl + yyPath+ File.separator +mmPath;
            } else {
                uploadPath   = fileuploadBase + File.separator + lowPath;
            }
            String ext          = StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), "."); //파일확장자
            String randomFileNm = RandomStringUtils.randomAlphanumeric(15);                                 //랜덤생성되는 파일명
            long size           = multipartFile.getSize();

            //fileBoard 필수값 세팅 terror terror terror terror terror terror i'm IS
            this.fileOriginNm = multipartFile.getOriginalFilename();
            this.fileSize = (int)size;

            //윤리위반신고 파일 체크
            if (null != subPath && subPath.equals("report")) {
                //윤리위반신고 파일 사이즈 10M
                if (isReportMaxSize(size)) {
                    throw new McpCommonException(EXCEED_FILE_SIZE_EXCEPTION);
                }
            } else {
                //파일 용랑 체크
                if (isMaxSize(size)) {
                    throw new McpCommonException(EXCEED_FILE_SIZE_EXCEPTION);
                }
            }

            //확장자 체크
            if (isDeninedExtension(ext)) {
                throw new McpCommonException(DENINED_FILE_TYPE_EXCEPTION);
            }

            //디렉토리 체크
            File chkDirectory = new File(uploadPath +  File.separator);
            if (!chkDirectory.exists()) {
                chkDirectory.mkdirs();
            }

            //파일업로드 처리시작
            File paramFile = null;
            Date d = new Date();
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
            String yymmddPath = date.format(d);
            long current = System.currentTimeMillis();
            if(null != subPath  && (subPath.equals("sellUsim") || subPath.equals("report"))){
                end_file_path = uploadPath + File.separator +"TEMP_"+yymmddPath +"_" +current + "." + ext ;
                paramFile =new File(end_file_path);
            }else{
                paramFile =new File(uploadPath + File.separator + randomFileNm + "." + ext);
            }

            try {
                multipartFile.transferTo(paramFile);
                if(subPath.equals("sellUsim") || subPath.equals("report")){
                    //암호화 처리
                    CNPECMJava.CNPEncryptModule.crypto.FileHandle fileHandle = new FileHandle();
                    try {
                        //같은 파일명이면 복호화가 안되기때문에 다를파일명으로 저장후 암호화
                        end_file_enc_path = end_file_path.replaceAll("TEMP_", "");
                        fileHandle.Encrypt(end_file_path,end_file_enc_path);
                        if(!new File(end_file_path).isFile()){
                            throw new McpCommonException(FILE_UPLOAD_EXCEPTION);
                        }
                        //암호화 파일생성후 암호화 전 임시파일 삭제
                        if(new File(end_file_enc_path).isFile()){
                            new File(end_file_path).delete();
                        }
                    } catch (Exception e) {
                        throw new McpCommonException(COMMON_EXCEPTION);
                    }


                }

            } catch (IllegalStateException e) {
                throw new McpCommonException(FILE_UPLOAD_EXCEPTION);
            } catch (IOException e) {
                throw new McpCommonException(FILE_UPLOAD_EXCEPTION);
            }

            if(null != subPath  && subPath.equals("sellUsim")){
                return yymmddPath +"_" +current + "." + ext;
            } else if (null != subPath  && subPath.equals("report")) {
                 return uploadPath + File.separator + yymmddPath +"_" +current + "." + ext;
            } else {
                return fileuploadWeb + "/" + subPath +"/"+ randomFileNm + "." + ext;
            }



        }

    }

    /**
    * @Description :
    *  파일을 web 에서 접근 가능한 경로에 업로드하고 (default 경로)
    *  접속가능한 웹경로를 리턴한다.
    * @param multipartFile
    * @return 업로드된 파일 접근가능한 웹경로
    * @Author : ant
    * @Create Date : 2016. 1. 5.
    */
    public String fileTrans(MultipartFile multipartFile) {
        return fileTrans(multipartFile,null);
    }

    /**
    * @Description :
    * 입력받은 학장자가 거부 처리 되있는 확장자 있지 확인해서
    * 일치할경우 true 리턴
    * @param ext
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 5.
    */
    public boolean isDeninedExtension (String ext) {
        if (ext == null || ext.equals("")) {
            return true;
        }
        String[] exts = fileUploadExtDenined.split(",");
        for (String t : exts) {
            if (ext.equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
    * @Description : 해당 업로드 파일 용량이 최대 용량을 넘을 경우
    * true 리턴한다.
    * @param size
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 5.
    */
    private boolean isMaxSize(long size) {
        long maxSize = fileuploadSize * 1024 * 1024;
        if (size > maxSize) {
            return true;
        }
        return false;
    }

    /**
     * @Description : 해당 업로드 파일 용량이 최대 용량을 넘을 경우
     * true 리턴한다.
     * @param size
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 5.
     */
     private boolean isReportMaxSize(long size) {
         long maxSize = reportFileuploadSize * 1024 * 1024;
         if (size > maxSize) {
             return true;
         }
         return false;
     }

    /**
    * @Description : 파일 삭제 처리
    *  fileDelete("test.jpeg","phone");
    * @param fileNm 경로를 포함하지 않은 파일명
    * @param subPath 업무경로
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 8.
    */
    public boolean fileDelete(String fileNm,String subPath) {
        boolean successFlag = false;

        File targetFile = new File(fileuploadBase+File.separator + subPath + File.separator + fileNm);
        successFlag = targetFile.delete();

        return successFlag;
    }

    /**
    * @Description : 파일 삭제 처리(풀페스
    * @param fileFullPath 경로를 포함한 파일명
    * @param subPath 업무경로
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 8.
    */
    public boolean fileDelete(String fileFullPath) {
        boolean successFlag = false;
        String conPath =fileFullPath;
        conPath = conPath.replaceAll("/resources/", "/");
        conPath = conPath.replaceAll("/upload/", "/");

        File targetFile = new File(fileuploadBase+File.separator + conPath);
        successFlag = targetFile.delete();

        return successFlag;
    }

    public String getFileOriginNm() {
        return fileOriginNm;
    }

    public int getFileSize() {
        return fileSize;
    }

}
