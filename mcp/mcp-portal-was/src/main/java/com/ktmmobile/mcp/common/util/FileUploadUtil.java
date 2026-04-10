/*
 * 파일업로드 공통
 * 작성자 최민욱
 * 작성일 20151215
 */
package com.ktmmobile.mcp.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.mcp.common.dto.FileBoardDTO;


public class FileUploadUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

    //파일 업로드 필요 변수
    private final String fPer = File.separator;
    private final String uploadTime = new SimpleDateFormat("yyyyMMddHHmmssSSSS", Locale.KOREA).format(new Date());
    private String rootPath = "";
    private String originalFileName = "";
    private String fileName = "";

    private String realDir = "";
    private String pExtName = "";
    private long   size =0;
    private String fileuploadWeb = "";
    private final FileBoardDTO fileBoardDto = new FileBoardDTO();

    //mintype 체크할것(default)
    private final String[] validMimeTypes = {"png","jpg","jpeg","gif", "application/pdf" , "application/x-tika-ooxml" ,"application/vnd.ms-excel", "application/x-tika-msoffice" ,"application/x-tika-ooxml"};

    private final static String EXT_DENINED = "jsp,java,class,html,htm,js,php,php3,php4,asp,inc,pl,cgi,exe";
    //원하는 파일경로를 넘긴다
    public FileUploadUtil(String filePath,String fileuploadWeb){
        rootPath = filePath;
        this.fileuploadWeb = fileuploadWeb;
        //기본경로 지정 data/dq
        if(rootPath == null || "".equals(rootPath)){
            rootPath="data"+fPer+"dq";
        }
    }
    //업로드
    public FileBoardDTO upload(MultipartFile file,HttpServletRequest req,String fileDir) {
        try {
            int extPointIndex = 0;
            realDir = rootPath+fPer+fileDir;
            originalFileName = file.getOriginalFilename(); // 파일명 추출

            extPointIndex = originalFileName.lastIndexOf(".");
            if(extPointIndex > 0){
                pExtName = originalFileName.substring(extPointIndex, originalFileName.length());
            }
            fileName = originalFileName.replace(pExtName, "");
            size     = file.getSize();
            fileBoardDto.setFilePathNM((fileuploadWeb+fPer+fileDir+fPer+uploadTime+pExtName).replace("\\", "/"));
            //파일을 하드디스크에 저장한다
            if(!"".equals(originalFileName) && size > 0){
                if(fileSave(file)==2){
                    fileBoardDto.setRst(2);
                }else{
                	fileSave(file);
                    fileBoardDto.setRst(1);
                }
            }

        }catch ( Exception e ){
            logger.error(e.getMessage());
        }

        return fileBoardDto;
    }

   //업로드
    /*
     * HttpServletRequest req   불필요 한듯.. 삭제 처리
     */
    public FileBoardDTO upload(MultipartFile file,String fileDir) {
        try {
            int extPointIndex = 0;
            realDir = rootPath+fPer+fileDir;
            originalFileName = file.getOriginalFilename(); // 파일명 추출

            extPointIndex = originalFileName.lastIndexOf(".");
            if(extPointIndex > 0){
                pExtName = originalFileName.substring(extPointIndex, originalFileName.length());
            }
            fileName = originalFileName.replace(pExtName, "");
            size     = file.getSize();
            fileBoardDto.setFilePathNM((fileuploadWeb+fPer+fileDir+fPer+uploadTime+pExtName).replace("\\", "/"));
            //파일을 하드디스크에 저장한다
            if(!"".equals(originalFileName) && size > 0){
                if(fileSave(file)==2){
                    fileBoardDto.setRst(2);
                }else{
                    fileBoardDto.setRst(1);
                }
            }

        }catch ( Exception e ){
            logger.error(e.getMessage());
        }

        return fileBoardDto;
    }

    //업로드
    /*
     * 파일명을 uploadTime으로 하지 않고 따로 입력받는 경우
     */
    public FileBoardDTO upload(MultipartFile file,String fileDir, String uploadFileNm) {
        try {
            int extPointIndex = 0;
            realDir = rootPath+fPer+fileDir;
            originalFileName = file.getOriginalFilename(); // 파일명 추출

            extPointIndex = originalFileName.lastIndexOf(".");
            if(extPointIndex > 0){
                pExtName = originalFileName.substring(extPointIndex, originalFileName.length());
            }
            fileName = originalFileName.replace(pExtName, "");
            size     = file.getSize();
            fileBoardDto.setFilePathNM((uploadFileNm+pExtName).replace("\\", "/"));
            //파일을 하드디스크에 저장한다
            if(!"".equals(originalFileName) && size > 0){
                if(fileSaveWithFileNm(file, uploadFileNm)==2){
                    fileBoardDto.setRst(2);
                }else{
                    fileBoardDto.setRst(1);
                }
            }
        } catch(DataAccessException e) {
            logger.error(e.getMessage());
        } catch( Exception e ){
            logger.error(e.getMessage());
        }

        return fileBoardDto;
    }


    //파일 삭제
    public boolean fileDelete(String realFilePath){
        synchronized(this) {
            File file= new File(realFilePath);
            if(file.exists()) {
                return  file.delete();
            }else {
                return false;
            }
        }

    }

    //파일을 저장한다
    public int fileSave(MultipartFile file){
        int rst=1;
        try {
            //폴더 없다면 생성
            File targetDir = new File(realDir); // 수정 필요
            //경로가 없다면 생성한다.
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

            //페스생성
            String realFilePath = realDir +fPer+uploadTime+pExtName;
            File targetFile = new File(realFilePath);
            //파일이름 중복처리
            //targetFile = fileNameChanger(targetFile);

            //파일저장
            fileBoardDto.setFileCapa(""+file.getSize());
            fileBoardDto.setFileType(originalFileName.replaceAll(" ", "_"));

            file.transferTo(targetFile);
            if(isDeninedExtension(pExtName)){
                fileDelete(realFilePath);
                return 2;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return rst;
    }


    //파일을 저장한다
    public int fileSaveWithFileNm(MultipartFile file, String uploadFileNm){
        int rst=1;
        try {
            //폴더 없다면 생성
            File targetDir = new File(realDir); // 수정 필요
            //경로가 없다면 생성한다.
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

            //페스생성
            String realFilePath = realDir +fPer+uploadFileNm+pExtName;
            File targetFile = new File(realFilePath);
            //파일이름 중복처리
            //targetFile = fileNameChanger(targetFile);

            //파일저장
            file.transferTo(targetFile);
            if(isDeninedExtension(pExtName)){
                fileDelete(realFilePath);
                return 2;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return rst;
    }

    //파일 이름이 중복일 경우 수정 한다.
    public File fileNameChanger(File targetFileInput){
        File targetFile = targetFileInput;
        //같은 물리적경로 라면 파일명을 윈도우 형태 20150302153360.pdf -> 20150302153360(1).pdf -> 20150302153360(2).pdf 로 해당 경로의 파일 리스트 확인
        if(targetFile.exists()) {
            //중복파일이 있다면 중복이름이 없을때 까지 fileName 변경 10000번 까지만
            for(int i=1; i<=10000; i++){
                int pIndex = fileName.lastIndexOf(".");
                String fFileName="";
                if(pIndex<1)fFileName=fileName;
                else fFileName = fileName.substring(0, pIndex);

                String fileGubun = "("+i+")";//괄호

                //fFileName 에 (1)이 있는지 검사.
                if(fFileName.lastIndexOf(")") == fFileName.length()-1 && fFileName.lastIndexOf("(") > 0 )
                {
                    fFileName = fFileName.substring(0, fFileName.lastIndexOf("("));
                }
                //새로운 파일이름을 저장.
                fileName = fFileName+fileGubun;
                targetFile = new File(realDir +fPer+ fileName); //수정필요

                if(!targetFile.exists()) {
                    break;
                }
            }
        }
        return targetFile;
    }


    //파일 타입 체크
    public boolean checkMineType(File f){
        Tika tika = new Tika();
        Boolean rst=false;
        try {
            String mediaType = tika.detect(f);
            rst = isPermittedMimeType(tika.detect(f));
        } catch(IOException e) {
            logger.error(e.getMessage());
        }

        return rst;
    }

    private boolean isPermittedMimeType(String mimeType) {
        for (String validMimeType : validMimeTypes) {
            if (mimeType.contains(validMimeType)) {
                return true;
            }
        }
        return false;
    }




    public String getFileuploadWeb() {
        return fileuploadWeb;
    }
    public void setFileuploadWeb(String fileuploadWeb) {
        this.fileuploadWeb = fileuploadWeb;
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
        String[] exts = EXT_DENINED.split(",");
        for (String t : exts) {
            if (ext.equals(t)) {
                return true;
            }
        }
        return false;
    }
}
