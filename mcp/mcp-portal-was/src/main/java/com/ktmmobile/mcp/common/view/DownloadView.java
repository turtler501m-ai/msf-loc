package com.ktmmobile.mcp.common.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @Class Name : DownloadView
 * @Description : 파일 다운로드 처리를 위한 View
 * 파일객체와 파일 원본명을 받아서 response 응답을 출력한다.
 * @author : ant
 * @Create Date : 2016. 1. 15.
 */
@Component
public class DownloadView extends AbstractView {
    /** LOGGER */
	private static final Logger logger = LoggerFactory.getLogger(DownloadView.class);

    /** 다운로드할 File 의 keyName */
    public static final String DOWNLOAD_FILE 	  = "targetFile";

    /** 다운로드할  원본 파일 Name */
    public static final String ORIGINAL_FILE_NAME = "originalNm";

    /** viewResolve 에서 바로는 클래스 view 명  */
    public static final String DOWNLOAD_VIEW 	  = "downloadView";

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        File file = (File) model.get(DOWNLOAD_FILE);
        String originalFileName = (String) model.get(ORIGINAL_FILE_NAME);
        originalFileName = URLEncoder.encode(originalFileName, "utf-8");

        //response header 정보 set
        //response.setContentType("application/octet-stream; charset=utf-8");
        response.setContentType("application/download; charset=utf-8");
        response.setContentLength((int)file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + originalFileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
            out.flush();
        } finally {
        	
        	//윤리위반 신고 첨부파일 다운로드 후 복호화 파일 삭제
        	Object obj = model.get("VIOLATION_REPORT");
        	if (obj != null) {
          		//복호화 된 파일 삭제처리
            	if(new File(file.getPath()).isFile()) {
                    new File(file.getPath()).delete();
                }
        	}
        	        	
            if(fis != null) {
                try {
                    fis.close();
                } catch(IOException ioe) {
                    logger.error("파일 다운로드 처리중 오류가 발생하였습니다.");
                    throw ioe;
                }
            }

            if(out != null) {
                try {
                    out.close();
                } catch(IOException ioe) {
                    logger.error("파일 다운로드 처리중 오류가 발생하였습니다.[out]");
                    throw ioe;
                }
            }
        }


    }
}
