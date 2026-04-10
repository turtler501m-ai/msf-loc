package com.ktis.msp.org.common.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.common.vo.FileInfoVO;
import com.ktis.msp.rcp.custMgmt.service.CustMgmtService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @Class Name : FileMgmtController
 * @Description : 파일 관리 프로그램
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 10. 13.
 */
@Controller
public class FileMgmtController extends BaseController {

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

	@Autowired
	private FileUpload2Service  fileUp2Service;

	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
    private MenuAuthService  menuAuthService;

	@Autowired
	private RcpMgmtService rcpMgmtService;

	@Autowired
	private CustMgmtService custMgmtService;

	/** Constructor */
	public FileMgmtController() {
		setLogPrefix("[FileMgmtController] ");
	}


	/**
	 * @Description :  파일업로드
	 * @Param  : model
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 10. 15.
	 */
    @SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/org/common/fileUpLoad.do")
    public String fileUpUsingService(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInfoVO fileInfoVO = new FileInfoVO();

		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
        loginInfo.putSessionToVo(fileInfoVO);

        fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
        fileInfoVO.setRvisnId(loginInfo.getUserId());

		String lSaveFileName = "";
		String lFileNamePc = "";

		Integer filesize = 0;

		try{
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);

	  	    //--------------------------------
	        // upload base directory를 구함
	  	    // 존재하지 않으면 exception 발생
	        //--------------------------------
	  	    String lBaseDir = propertyService.getString("orgPath");
			if ( !new File(lBaseDir ).exists())
			{
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
			}

			for (FileItem item : items) {

				//--------------------------------
		        // 파일 타입이 아니면 skip
		        //--------------------------------
				if (item.isFormField())
					continue;

				//--------------------------------
		        // 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
		        //--------------------------------
				if ( item.getSize() == 0 ||  item.getSize()  >  Integer.parseInt(propertyService.getString("fileUploadSizeLimit")) )
					continue;

		    	/** 20230518 PMD 조치 */
		    	InputStream filecontent = null;
				File f = null;
				OutputStream fout = null;
				try
				{
					//--------------------------------
			        // 모듈별 directory가 존재하지 않으면 생성
			        //--------------------------------
					File lDir = new File(lBaseDir );
					if ( !lDir.exists())
					{
						lDir.mkdirs();
					}

					//----------------------------------------------------------------
			    	// upload 이름 구함
			    	//----------------------------------------------------------------
					String fieldname = item.getFieldName();
					if ( fieldname.equals("file"))
					{
						//----------------------------------------------------------------
				    	// 파일 이름 구함
				    	//----------------------------------------------------------------
						lFileNamePc = FilenameUtils.getName(item.getName());

						fileInfoVO.setAttRout(lBaseDir);

//						System.out.println("UUID -> " + generationUUID().replaceAll("-",""));

				    	lSaveFileName = fileUp2Service.getAlternativeFileName(  lBaseDir   ,  lFileNamePc);
				    	String lTransferTargetFileName = lBaseDir  + "/" + lSaveFileName;

						//----------------------------------------------------------------
				    	// 파일 write
				    	//----------------------------------------------------------------
						filecontent = item.getInputStream();
						f=new File(lTransferTargetFileName);
						fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;
						while((len=filecontent.read(buf))>0) {
							fout.write(buf,0,len);
							filesize+=len;
						}
					}
				}catch (Exception e) {
					//e.printStackTrace();
				    //throw e;
				    throw new MvnoErrorException(e);
				}finally{					
			        if (fout != null) {
			        	try {
			        		fout.close();
			        	} catch (Exception e) {
				            throw new MvnoErrorException(e);
			        	}
			        }
			        if (filecontent != null) {
			        	try {
			        		filecontent.close();
			        	} catch (Exception e) {
				            throw new MvnoErrorException(e);
			        	}
			        }
				}
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
    	} catch (Exception e) {
    		resultMap.put("state", false);
    		resultMap.put("name", lFileNamePc.replace("'","\\'"));
    		resultMap.put("size",  111);

    		model.addAttribute("result", resultMap);
    	    return "jsonViewArray";
    	}

		//파일 테이블에 데이터 저장하고.
		fileInfoVO.setFileId(generationUUID().replaceAll("-",""));
		fileInfoVO.setFileNm(lSaveFileName);
		fileInfoVO.setFileSeq("9999999999");//초기값 조직ID
		fileInfoVO.setAttSctn("ORG");//조직 파일첨부

		//파일 테이블 insert
		orgCommonService.insertFile(fileInfoVO);

		resultMap.put("state", true);
		resultMap.put("name", fileInfoVO.getFileId());
		resultMap.put("size", "" + 111);

		model.addAttribute("result", resultMap);
	    return "jsonViewArray";
    }

	/**
	 * @Description :  파일업로드
	 * @Param  : model
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 10. 15.
	 */
    @SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/org/common/fileUpLoad2.do")
    public String fileUpUsingService2(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInfoVO fileInfoVO = new FileInfoVO();

		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
        loginInfo.putSessionToVo(fileInfoVO);

        fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
        fileInfoVO.setRvisnId(loginInfo.getUserId());

		String lSaveFileName = "";
		String lFileNamePc = "";

		Integer filesize = 0;

		try{
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);

	  	    //--------------------------------
	        // upload base directory를 구함
	  	    // 존재하지 않으면 exception 발생
	        //--------------------------------
	  	    String lBaseDir = propertyService.getString("orgPath");
			if ( !new File(lBaseDir ).exists())
			{
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
			}

			for (FileItem item : items) {

				//--------------------------------
		        // 파일 타입이 아니면 skip
		        //--------------------------------
				if (item.isFormField())
					continue;

				//--------------------------------
		        // 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
		        //--------------------------------
				if ( item.getSize() == 0 ||  item.getSize()  >  Integer.parseInt(propertyService.getString("fileUploadSizeLimit")) )
					continue;

		    	/** 20230518 PMD 조치 */
		    	InputStream filecontent = null;
				File f = null;
				OutputStream fout = null;
				try
				{
					//--------------------------------
			        // 모듈별 directory가 존재하지 않으면 생성
			        //--------------------------------
					File lDir = new File(lBaseDir );
					if ( !lDir.exists())
					{
						lDir.mkdirs();
					}

					//----------------------------------------------------------------
			    	// upload 이름 구함
			    	//----------------------------------------------------------------
					String fieldname = item.getFieldName();
					if ( fieldname.equals("file"))
					{
						//----------------------------------------------------------------
				    	// 파일 이름 구함
				    	//----------------------------------------------------------------
						lFileNamePc = FilenameUtils.getName(item.getName());

						fileInfoVO.setAttRout(lBaseDir);

//						System.out.println("UUID -> " + generationUUID().replaceAll("-",""));

				    	lSaveFileName = fileUp2Service.getAlternativeFileName(  lBaseDir   ,  lFileNamePc);
				    	String lTransferTargetFileName = lBaseDir  + "/" + lSaveFileName;

						//----------------------------------------------------------------
				    	// 파일 write
				    	//----------------------------------------------------------------
				    	filecontent = item.getInputStream();
						f=new File(lTransferTargetFileName);
						fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;

						while((len=filecontent.read(buf))>0) {
							fout.write(buf,0,len);
							filesize+=len;
						}

					}
				}catch (Exception e) {
					//e.printStackTrace();
				    //throw e;
				    throw new MvnoErrorException(e);
				}finally{

			        if (fout != null) {
			        	try {
			        		fout.close();
			        	} catch (Exception e) {
				            throw new MvnoErrorException(e);
			        	}
			        }
			        if (filecontent != null) {
			        	try {
			        		filecontent.close();
			        	} catch (Exception e) {
				            throw new MvnoErrorException(e);
			        	}
			        }					
				}

		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
    	} catch (Exception e) {
    		resultMap.put("state", false);
    		resultMap.put("name", lFileNamePc.replace("'","\\'"));
    		resultMap.put("size",  111);

    		model.addAttribute("result", resultMap);
    	    return "jsonViewArray";
    	}
		logger.debug("파일사이즈===" + filesize);
		logger.debug("lSaveFileName" + lSaveFileName);


		//파일 테이블에 데이터 저장하고.
		fileInfoVO.setFileId(generationUUID().replaceAll("-",""));
		fileInfoVO.setFileNm(lSaveFileName);
		fileInfoVO.setFileSeq("9999999999");//초기값 조직ID
		fileInfoVO.setAttSctn("MRT");//담보내역

		//파일 테이블 insert
		orgCommonService.insertFile(fileInfoVO);

		resultMap.put("state", true);
		resultMap.put("name", fileInfoVO.getFileId());
		resultMap.put("size", "" + 111);

		model.addAttribute("result", resultMap);
	    return "jsonViewArray";
    }


	/**
	 * @Description :  파일업로드
	 * @Param  : model
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 10. 15.
	 */
    @SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/org/common/fileUpLoad3.do")
    public String fileUpUsingService3(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInfoVO fileInfoVO = new FileInfoVO();

		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
        loginInfo.putSessionToVo(fileInfoVO);

        fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
        fileInfoVO.setRvisnId(loginInfo.getUserId());

		String lSaveFileName = "";
		String lFileNamePc = "";

		Integer filesize = 0;

		try{
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);

	  	    //--------------------------------
	        // upload base directory를 구함
	  	    // 존재하지 않으면 exception 발생
	        //--------------------------------
	  	    String lBaseDir = propertyService.getString("orgPath");
			if ( !new File(lBaseDir ).exists())
			{
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
			}

			for (FileItem item : items) {

				//--------------------------------
		        // 파일 타입이 아니면 skip
		        //--------------------------------
				if (item.isFormField())
					continue;

				//--------------------------------
		        // 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
		        //--------------------------------
				if ( item.getSize() == 0 ||  item.getSize()  >  Integer.parseInt(propertyService.getString("fileUploadSizeLimit")) )
					continue;

		    	/** 20230518 PMD 조치 */
		    	InputStream filecontent = null;
				File f = null;
				OutputStream fout = null;
				try
				{
					//--------------------------------
			        // 모듈별 directory가 존재하지 않으면 생성
			        //--------------------------------
					File lDir = new File(lBaseDir );
					if ( !lDir.exists())
					{
						lDir.mkdirs();
					}

					//----------------------------------------------------------------
			    	// upload 이름 구함
			    	//----------------------------------------------------------------
					String fieldname = item.getFieldName();
					if ( fieldname.equals("file"))
					{
						//----------------------------------------------------------------
				    	// 파일 이름 구함
				    	//----------------------------------------------------------------
						lFileNamePc = FilenameUtils.getName(item.getName());

						fileInfoVO.setAttRout(lBaseDir);

						//System.out.println("UUID -> " + generationUUID().replaceAll("-",""));

				    	lSaveFileName = fileUp2Service.getAlternativeFileName(  lBaseDir   ,  lFileNamePc);
				    	String lTransferTargetFileName = lBaseDir  + "/" + lSaveFileName;

						//----------------------------------------------------------------
				    	// 파일 write
				    	//----------------------------------------------------------------
						filecontent = item.getInputStream();
						f=new File(lTransferTargetFileName);
						fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;
						while((len=filecontent.read(buf))>0) {
							fout.write(buf,0,len);
							filesize+=len;
						}

					}
				}catch (Exception e) {
					//e.printStackTrace();
				    //throw e;
				    throw new MvnoErrorException(e);
				}finally{
			        if (fout != null) {
			        	try {
			        		fout.close();
			        	} catch (Exception e) {
			        	    throw new MvnoErrorException(e);
			        	}
			        }
			        if (filecontent != null) {
			        	try {
			        		filecontent.close();
			        	} catch (Exception e) {
			        	    throw new MvnoErrorException(e);
			        	}
			        }					
				}

		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
    	} catch (Exception e) {
    		resultMap.put("state", false);
    		resultMap.put("name", lFileNamePc.replace("'","\\'"));
    		resultMap.put("size",  111);

    		model.addAttribute("result", resultMap);
    	    return "jsonViewArray";
    	}

		//파일 테이블에 데이터 저장하고.
		fileInfoVO.setFileId(generationUUID().replaceAll("-",""));
		fileInfoVO.setFileNm(lSaveFileName);
		fileInfoVO.setFileSeq("9999999999");//초기값 조직ID
		fileInfoVO.setAttSctn("CRD"); //신용등급

		//파일 테이블에 등록
		orgCommonService.insertFile(fileInfoVO);

		resultMap.put("state", true);
		resultMap.put("name", fileInfoVO.getFileId());
		resultMap.put("size", "" + 111);

		model.addAttribute("result", resultMap);
	    return "jsonViewArray";
    }

	/**
	 * @Description : UUID 생성
	 * @Param  :
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
    public static String generationUUID(){
    	  return UUID.randomUUID().toString();
    	 }

	/**
	 * @Description : 파일 관리 프로그램에서 확인 버튼 누를시 조직에 UUID컬럼과 파일첨부의 UUID를 엮어준다.
	 * @Param  : FileInfoVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping("/org/common/insertMgmt.json")
	public String insertOrgMgmt(FileInfoVO fileInfoVO, HttpServletRequest request, HttpServletResponse response, ModelMap model)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("파일 등록 START."));
		logger.info(generateLogMsg("fileInfoVO=" + fileInfoVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToVo(fileInfoVO);

        fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
        fileInfoVO.setRvisnId(loginInfo.getUserId());

        int returnCnt = 0;

		try {

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_0()))
			{
				returnCnt = orgCommonService.updateFile(fileInfoVO);
			}

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_1()))
			{
				returnCnt = orgCommonService.updateFile1(fileInfoVO);
			}

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_2()))
			{
				returnCnt = orgCommonService.updateFile2(fileInfoVO);
			}

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload2_s_0()))
			{
				returnCnt = orgCommonService.updateFile3(fileInfoVO);
			}

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload2_s_1()))
			{
				returnCnt = orgCommonService.updateFile4(fileInfoVO);
			}

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload2_s_2()))
			{
				returnCnt = orgCommonService.updateFile5(fileInfoVO);
			}

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload3_s_0()))
			{
				returnCnt = orgCommonService.updateFile6(fileInfoVO);
			}

			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload4_s_0()))
			{
				returnCnt = orgCommonService.updateFile7(fileInfoVO);

				List<?> fileInfoVOs = new ArrayList<FileInfoVO>();

				fileInfoVOs  = orgCommonService.getFile4(fileInfoVO);

	        	for (int i = 0; i < fileInfoVOs.size(); i++) {
	        		EgovMap tempMap = (EgovMap)fileInfoVOs.get(i);

	        		if(i==0)
	        		{
	        			resultMap.put("fileId", tempMap.get("fileId"));
	        		}
				}
			}

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.info(generateLogMsg("수정 건수 = " + returnCnt));

		} catch (Exception e) {
		    resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
            if ( ! getErrReturn(e, resultMap))
            {
                throw new MvnoErrorException(e);
            }
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}


	/**
	 * @Description : 첨부된 파일의 명을 찾아온다.
	 * @Param  : HndstAmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/org/common/getFile1.json")
    public String getFile1(FileInfoVO fileInfoVO, Model model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("첨부 파일 명 찾기 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

        	List<?> resultList = orgCommonService.getFile1(fileInfoVO);

        	for (int i = 0; i < resultList.size(); i++) {
        		EgovMap tempMap = (EgovMap)resultList.get(i);

        		if(i==0)
        		{
        			resultMap.put("fileNm", tempMap.get("fileNm"));
        			resultMap.put("fileId", tempMap.get("fileId"));
        		}

        		if(i==1)
        		{
        			resultMap.put("fileNm1", tempMap.get("fileNm"));
        			resultMap.put("fileId1", tempMap.get("fileId"));
        		}

        		if(i==2)
        		{
        			resultMap.put("fileNm2", tempMap.get("fileNm"));
        			resultMap.put("fileId2", tempMap.get("fileId"));
        		}
			}
        	resultMap.put("fileTotalCnt", String.valueOf(resultList.size()));
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

            logger.debug("result = " + resultMap);
        } catch (Exception e) {

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

	/**
	 * @Description : 첨부된 파일의 명을 찾아온다.
	 * @Param  : HndstAmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/org/common/getFile2.json")
    public String getFile2(FileInfoVO fileInfoVO, Model model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("첨부 파일 명 찾기 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

        	List<?> resultList = orgCommonService.getFile2(fileInfoVO);

        	for (int i = 0; i < resultList.size(); i++) {
        		EgovMap tempMap = (EgovMap)resultList.get(i);

        		if(i==0)
        		{
        			resultMap.put("fileNm3", tempMap.get("fileNm"));
        			resultMap.put("fileId3", tempMap.get("fileId"));
        		}

        		if(i==1)
        		{
        			resultMap.put("fileNm4", tempMap.get("fileNm"));
        			resultMap.put("fileId4", tempMap.get("fileId"));
        		}

        		if(i==2)
        		{
        			resultMap.put("fileNm5", tempMap.get("fileNm"));
        			resultMap.put("fileId5", tempMap.get("fileId"));
        		}
			}
        	resultMap.put("fileTotalCnt", String.valueOf(resultList.size()));
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

            logger.debug("result = " + resultMap);
        } catch (Exception e) {

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }


	/**
	 * @Description : 첨부된 파일의 명을 찾아온다.
	 * @Param  : HndstAmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/org/common/getFile3.json")
    public String getFile3(FileInfoVO fileInfoVO, Model model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("첨부 파일 명 찾기 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

        	List<?> resultList = orgCommonService.getFile3(fileInfoVO);

        	for (int i = 0; i < resultList.size(); i++) {
        		EgovMap tempMap = (EgovMap)resultList.get(i);

        		if(i==0)
        		{
        			resultMap.put("fileNm6", tempMap.get("fileNm"));
        			resultMap.put("fileId6", tempMap.get("fileId"));
        		}
			}
        	resultMap.put("fileTotalCnt", String.valueOf(resultList.size()));
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

            logger.debug("result = " + resultMap);
        } catch (Exception e) {

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    /**
     * @Description : 파일 다운로드 기능
     * @Param  : SalePlcyMgmtVo
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping("/org/common/downFile.json")
    public String downFile( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일다운로드 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        String returnMsg = null;

        try {

        	String strFileName = orgCommonService.getFileNm(pReqParamMap.get("fileId").toString());

            file = new File(strFileName);

            response.setContentType("applicaton/download");
            response.setContentLength((int) file.length());

            String encodingFileName = "";

    		int excelPathLen2 = Integer.parseInt(propertyService.getString("orgPathLen2"));

            try {
              encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8").replace("+", "%20");
            } catch (UnsupportedEncodingException uee) {
              encodingFileName = strFileName;
            }

            response.setHeader("Cache-Control", "");
            response.setHeader("Pragma", "");

            response.setContentType("Content-type:application/octet-stream;");

            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            in = new FileInputStream(file);

            out = response.getOutputStream();

            int temp = -1;
            while((temp = in.read()) != -1){
            	out.write(temp);
            }

 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");

 		} catch (Exception e) {

 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
 			resultMap.put("msg", returnMsg);
 			throw new MvnoErrorException(e);
	    } finally {
	        if (in != null) {
	          try {
	        	  in.close();
	          } catch (Exception e) {
	            throw new MvnoErrorException(e);
	          }
	        }
	        if (out != null) {
	          try {
	        	  out.close();
	          } catch (Exception e) {
	        	  throw new MvnoErrorException(e);
	          }
	        }
	    }
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
    }

    /**
     * @Description : 파일 삭제 기능
     * @Param  : SalePlcyMgmtVo
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping("/org/common/deleteFile.json")
    public String deleteFile( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일 삭제 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        File file = null;
        String returnMsg = null;

        try {

        	String strFileName = orgCommonService.getFileNm(pReqParamMap.get("fileId").toString());

            file = new File(strFileName);

            file.delete();

            int delcnt = orgCommonService.deleteFile(pReqParamMap.get("fileId").toString());
            logger.info(generateLogMsg("삭제건수 = " + delcnt));

            FileInfoVO fileInfoVO = new FileInfoVO();
            fileInfoVO.setOrgnId(pReqParamMap.get("orgnId").toString());
            fileInfoVO.setAttSctn(pReqParamMap.get("attSctn").toString());
            int fileCnt = orgCommonService.getFileCnt(fileInfoVO);

            resultMap.put("fileTotCnt", String.valueOf(fileCnt));
            resultMap.put("deleteCnt", String.valueOf(delcnt));

 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "파일삭제성공");

 		} catch (Exception e) {
 			resultMap.put("fileTotCnt", "0");
 			resultMap.put("deleteCnt", "0");
 			
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					returnMsg, "", ""))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}  			
			throw new MvnoErrorException(e);
	    }
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
    }

    /**
     * @Description : 파일 삭제 기능 2
     * @Param  : SalePlcyMgmtVo
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping("/org/common/deleteFile2.json")
    public String deleteFile2( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일 삭제 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        File file = null;
        String returnMsg = null;

        try {

        	String strFileName = orgCommonService.getFileNm(pReqParamMap.get("fileKey").toString());

            file = new File(strFileName);

            file.delete();

            int recYnCnt = 0;

            if( pReqParamMap.get("orgnId") != null)
            {
                recYnCnt = orgCommonService.getRequestKeyCnt(pReqParamMap.get("orgnId").toString());
            }
            logger.error("녹취여부 건수" + recYnCnt);



            if( recYnCnt == 1)
            {
            	int updateCnt = orgCommonService.getRequestKeyUpdate(pReqParamMap.get("orgnId").toString());
            	logger.error("녹취여부 UPDATE" + updateCnt);
            }

            int delcnt = orgCommonService.deleteFile(pReqParamMap.get("fileKey").toString());
            logger.info(generateLogMsg("삭제건수 = " + delcnt));


//            if(!StringUtils.isEmpty(pReqParamMap.get("orgnId").toString()))
//            {
//            	FileInfoVO fileInfoVO = new FileInfoVO();
//                fileInfoVO.setOrgnId(pReqParamMap.get("orgnId").toString());
//                fileInfoVO.setAttSctn(pReqParamMap.get("attSctn").toString());
//                int fileCnt = orgCommonService.getFileCnt(fileInfoVO);
//
//                resultMap.put("fileTotCnt", String.valueOf(fileCnt));
//                resultMap.put("deleteCnt", String.valueOf(delcnt));
//            }


 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "삭제성공");

 		} catch (Exception e) {

 			resultMap.put("fileTotCnt", "0");
            resultMap.put("deleteCnt", "0");
 			
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					returnMsg, "", ""))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}	 			
			throw new MvnoErrorException(e);
	    }
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
    }


	/**
	 * @Description : 고객 신청 관리 녹취 파일 업로드
	 * @Param  : model
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 10. 15.
	 */
	@RequestMapping("/org/common/fileUpLoad4.do")
    public String fileUpUsingService4(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInfoVO fileInfoVO = new FileInfoVO();

		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
        loginInfo.putSessionToVo(fileInfoVO);

        fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
        fileInfoVO.setRvisnId(loginInfo.getUserId());

		String lSaveFileName = "";
		String lFileNamePc = "";

		Integer filesize = 0;

		String newFileNm = "";
		StringBuffer tmpFileNm = new StringBuffer();

		try{
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);

	  	    //--------------------------------
	        // upload base directory를 구함
	  	    // 존재하지 않으면 exception 발생
	        //--------------------------------
	  	    String lBaseDir = propertyService.getString("orgPath2");
			if ( !new File(lBaseDir ).exists())
			{
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { lBaseDir }  , Locale.getDefault()));
			}

			for (FileItem item : items) {

				//--------------------------------
		        // 파일 타입이 아니면 skip
		        //--------------------------------
				if (item.isFormField())
					continue;

				//--------------------------------
		        // 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
		        //--------------------------------
				if ( item.getSize() == 0 ||  item.getSize()  >  Integer.parseInt(propertyService.getString("fileUploadSizeLimit")) )
					continue;

		    	/** 20230518 PMD 조치 */
		    	InputStream filecontent = null;
				File f = null;
				OutputStream fout = null;
				try
				{
					//--------------------------------
			        // 모듈별 directory가 존재하지 않으면 생성
			        //--------------------------------
					File lDir = new File(lBaseDir );
					if ( !lDir.exists())
					{
						lDir.mkdirs();
					}

					//----------------------------------------------------------------
			    	// upload 이름 구함
			    	//----------------------------------------------------------------
					String fieldname = item.getFieldName();
					if ( fieldname.equals("file"))
					{
						//----------------------------------------------------------------
				    	// 파일 이름 구함
				    	//----------------------------------------------------------------
						lFileNamePc = FilenameUtils.getName(item.getName());

						String[] result = lFileNamePc.split("\\.");
						String fileType = "";


						for (int i = 0; i < result.length; i++) {
							logger.debug("i=====" + result[i]);
							fileType = result[i];
						}

						fileInfoVO.setAttRout(lBaseDir);//경로 셋팅

						//파일명을 시퀸스로 바꾼다.
						newFileNm = orgCommonService.getRecSeq(lFileNamePc);



						tmpFileNm.append(newFileNm);
						tmpFileNm.append(".");
						tmpFileNm.append(fileType);


//						newFileNm = newFileNm + "." + fileType;


				    	lSaveFileName = fileUp2Service.getAlternativeFileName(lBaseDir, tmpFileNm.toString());

				    	String lTransferTargetFileName = lBaseDir  + "/" + lSaveFileName;

						//----------------------------------------------------------------
				    	// 파일 write
				    	//----------------------------------------------------------------
						filecontent = item.getInputStream();
						f=new File(lTransferTargetFileName);
						
						fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;
						while((len=filecontent.read(buf))>0) {
							fout.write(buf,0,len);
							filesize+=len;
						}
					}
				}catch (Exception e) {
					//e.printStackTrace();
				    //throw e;
				    throw new MvnoErrorException(e);
				}finally{
			        if (fout != null) {
			        	try {
			        		fout.close();
			        	} catch (Exception e) {
				            throw new MvnoErrorException(e);
			        	}
			        }
			        if (filecontent != null) {
			        	try {
			        		filecontent.close();
			        	} catch (Exception e) {
				            throw new MvnoErrorException(e);
			        	}
			        }					
				}

		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
    	} catch (Exception e) {
    		resultMap.put("state", false);
    		resultMap.put("name", lFileNamePc.replace("'","\\'"));
    		resultMap.put("size",  111);

    		model.addAttribute("result", resultMap);
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", ""))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}     		
    	    return "jsonViewArray";
    	}

		//파일 테이블에 데이터 저장하고.
		fileInfoVO.setFileId(generationUUID().replaceAll("-",""));
		fileInfoVO.setFileNm(lFileNamePc);
		fileInfoVO.setFileSeq("9999999999");//초기값 조직ID
		fileInfoVO.setAttSctn("REC"); //녹취파일
		fileInfoVO.setAttDsc(tmpFileNm.toString());//녹취파일 시퀸스

		//파일 테이블에 등록
		orgCommonService.insertFile(fileInfoVO);

		resultMap.put("state", true);
		resultMap.put("name", fileInfoVO.getFileId());
		resultMap.put("size", "" + 111);

		model.addAttribute("result", resultMap);
	    return "jsonViewArray";
    }

	/**
	 * @Description : 녹취 파일의 명을 찾아온다.
	 * @Param  : HndstAmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/org/common/getFile4.json")
    public String getFile4(FileInfoVO fileInfoVO, Model model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("첨부 파일 명 찾기 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

        	List<?> resultList = orgCommonService.getFile4(fileInfoVO);

        	for (int i = 0; i < resultList.size(); i++) {
        		EgovMap tempMap = (EgovMap)resultList.get(i);

        		if(i==0)
        		{
        			resultMap.put("fileNm6", tempMap.get("fileNm"));
        			resultMap.put("fileId6", tempMap.get("fileId"));
        		}
			}
        	resultMap.put("fileTotalCnt", String.valueOf(resultList.size()));
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

            logger.debug("result = " + resultMap);
        } catch (Exception e) {

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }


	/**
	 * @Description : 녹취 파일의 명을 찾아온다.
	 * @Param  : HndstAmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/org/common/getFile7.json")
    public String getFile7(@RequestParam Map<String, Object> pRequestParamMap, Model model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("첨부 파일 명 찾기 START."));
        logger.info(generateLogMsg("================================================================="));

        logger.debug("orgnId=========" + pRequestParamMap.get("orgnId").toString());

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

        	FileInfoVO fileInfoVO = new FileInfoVO();
        	fileInfoVO.setOrgnId(pRequestParamMap.get("orgnId").toString());

        	List<?> resultList = orgCommonService.getFile7(fileInfoVO);

        	for (int i = 0; i < resultList.size(); i++) {
        		EgovMap tempMap = (EgovMap)resultList.get(i);

        		if(i==0)
        		{
        			resultMap.put("fileNm6", tempMap.get("fileNm"));
        			resultMap.put("fileId6", tempMap.get("fileId"));
        		}
			}
        	resultMap.put("fileTotalCnt", String.valueOf(resultList.size()));
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

            logger.debug("result = " + resultMap);
        } catch (Exception e) {

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

	/**
	 * @Description : 녹취 파일의 count확인
	 * @Param  : FileInfoVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/org/common/getFile6.json")
    public String getFile5(@RequestParam Map<String, Object> pRequestParamMap, Model model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("녹취 파일 명 찾기 START."));
        logger.info(generateLogMsg("================================================================="));

        logger.debug("orgnId=========" + pRequestParamMap.get("orgnId").toString());

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

        	FileInfoVO fileInfoVO = new FileInfoVO();
        	fileInfoVO.setOrgnId(pRequestParamMap.get("orgnId").toString());

        	int resultCnt = orgCommonService.getFile6(fileInfoVO);

        	if( resultCnt == 1)
        	{
        		List<?> resultList = orgCommonService.getFile7(fileInfoVO);

            	for (int i = 0; i < resultList.size(); i++) {
            		EgovMap tempMap = (EgovMap)resultList.get(i);

            		if(i==0)
            		{
//            			tempMap.get("attRout") +
            			String lBaseDir = propertyService.getString("orgPath2");
//            			String lfileNm = lBaseDir + tempMap.get("attDsc").toString();
            			String encodingFileName = tempMap.get("attDsc").toString();
            			String encodingFileName2 = tempMap.get("fileNm").toString();
            			String chEncodingFileName = "";
            			String chEncodingFileName2 = "";
            			try{
            				chEncodingFileName = URLEncoder.encode(encodingFileName, "UTF-8").replace("+", "%20");
            				chEncodingFileName2 = URLEncoder.encode(encodingFileName2, "UTF-8").replace("+", "%20");
	                    } catch (UnsupportedEncodingException uee) {
	                    	chEncodingFileName = encodingFileName;
	                    	chEncodingFileName2 = encodingFileName2;
	                    }

//            			lfileNm = lBaseDir + encodingFileName;

//                        logger.info(generateLogMsg("lfileNm = " + lfileNm));
                        logger.info(generateLogMsg("lfileNm = " + encodingFileName));
                        logger.info(generateLogMsg("lfileNm = " + chEncodingFileName));

            			resultMap.put("fileNm", chEncodingFileName);
            			resultMap.put("fileNm2", chEncodingFileName2);
            			resultMap.put("filePath", lBaseDir);
            		}
    			}

        	}

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            resultMap.put("resultCnt", resultCnt);

            logger.info(generateLogMsg("resultCnt = " + resultCnt));

        } catch (Exception e) {
            resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
            if ( ! getErrReturn(e, resultMap))
            {
                throw new MvnoErrorException(e);
            }
        }

        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    /**
     * @Description : 녹취 파일 다운로드 기능
     * @Param  : SalePlcyMgmtVo
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    @RequestMapping("/org/common/downFile2.json")
    public String downFile2( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일다운로드 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pReqParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        String returnMsg = null;

        try {

        	String strFileName = orgCommonService.getFileNm8(pReqParamMap.get("fileId").toString());

            file = new File(strFileName);

            response.setContentType("applicaton/download");
            response.setContentLength((int) file.length());

            String encodingFileName = "";

    		int excelPathLen2 = Integer.parseInt(propertyService.getString("orgPathLen2"));

            try {
              encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8").replace("+", "%20");
            } catch (UnsupportedEncodingException uee) {
              encodingFileName = strFileName;
            }

            response.setHeader("Cache-Control", "");
            response.setHeader("Pragma", "");

            response.setContentType("Content-type:application/octet-stream;");

            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            in = new FileInputStream(file);

            out = response.getOutputStream();

            int temp = -1;
            while((temp = in.read()) != -1){
            	out.write(temp);
            }

 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");

 		} catch (Exception e) {

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					returnMsg, "", ""))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			} 
			throw new MvnoErrorException(e);
	    } finally {
	        if (in != null) {
	          try {
	        	  in.close();
	          } catch (Exception e) {
	            throw new MvnoErrorException(e);
	          }
	        }
	        if (out != null) {
	          try {
	        	  out.close();
	          } catch (Exception e) {
	        	  throw new MvnoErrorException(e);
	          }
	        }
	    }
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
    }

//    public String player() {
//
//
//    	return "page"
//    }


    /*
     * 녹취 청취 위한 jsp 호출
     */
    @RequestMapping(value = "/org/common/downFile3.do", method={POST, GET})
    @ResponseBody
    public FileSystemResource  recProcess(HttpServletRequest request, HttpServletResponse response){

    	response.setContentType("audio/mpeg");
    	String fileName = request.getParameter("fileNm");
    	String filePath = request.getParameter("filePath");
    	//20200513 소스코드점검 으로 추가
    	fileName = fileName.replaceAll("<","&lt;");
    	fileName = fileName.replaceAll(">","&gt;");
    	filePath = filePath.replaceAll("<","&lt;");
    	filePath = filePath.replaceAll(">","&gt;");
    	
    	File file = new File(filePath + fileName);
        response.setHeader("Content-Disposition", ("attachment; filename=" + file.getName()).replaceAll("\r", "").replaceAll("\n", ""));

        logger.debug("파일경로 " + file.getPath());


        /** 보통 아래와 같이 구현했었다. 이것도 간단하긴 한데...
        try {
            FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            log.info("Error writing file to output stream. Filename was '" + file.getName() + "'");
            throw new RuntimeException("IOError writing file to output stream");
        }
        */

        /* 그냥 객체 하나만 던져주면 된다. */

        return new FileSystemResource(file);
    }

    /*
     * 녹취 청취 위한 jsp 호출
     */
    @RequestMapping(value = "/org/common/play.do", method={POST, GET})
    public String player(HttpServletRequest request, HttpServletResponse response) {

        return "rcp/custMgmt/rec_popup";
    }


	/**
	 * @Description : 녹취 파일 저장 처리
	 * @Param  : FileInfoVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping("/org/common/insertMgmt2.json")
	public String insertOrgMgmt2(FileInfoVO fileInfoVO, HttpServletRequest request, HttpServletResponse response, ModelMap model)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("파일 등록 START."));
		logger.info(generateLogMsg("fileInfoVO=" + fileInfoVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToVo(fileInfoVO);

        fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
        fileInfoVO.setRvisnId(loginInfo.getUserId());
        fileInfoVO.setOrgnId(fileInfoVO.getStrRequestKey());

        RcpDetailVO rcpDetailVO = new RcpDetailVO();

        int returnCnt = 0;

		try {

			//녹취파일 1
			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_0()))
			{
				returnCnt = orgCommonService.updateFile(fileInfoVO);
				resultMap.put("fileId1", fileInfoVO.getFile_upload1_s_0());

				if(returnCnt > 0)
				{
					rcpDetailVO.setRequestKey(fileInfoVO.getStrRequestKey());
					rcpDetailVO.setRecYn("Y");
					int returnUpdateCnt = rcpMgmtService.updateRcpRequestRecYn(rcpDetailVO);
					if(returnUpdateCnt > 0)
					{
						custMgmtService.updateRecYn(fileInfoVO.getStrRequestKey());
					}
				}
			}

			//녹취파일 2
			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_1()))
			{
				returnCnt = orgCommonService.updateFile1(fileInfoVO);
				resultMap.put("fileId2", fileInfoVO.getFile_upload1_s_1());

				if(returnCnt > 0)
				{
					rcpDetailVO.setRequestKey(fileInfoVO.getStrRequestKey());
					rcpDetailVO.setRecYn("Y");
					int returnUpdateCnt = rcpMgmtService.updateRcpRequestRecYn(rcpDetailVO);
					if(returnUpdateCnt > 0)
					{
						custMgmtService.updateRecYn(fileInfoVO.getStrRequestKey());
					}
				}
			}

			//녹취파일 3
			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_2()))
			{
				returnCnt = orgCommonService.updateFile2(fileInfoVO);
				resultMap.put("fileId3", fileInfoVO.getFile_upload1_s_2());

				if(returnCnt > 0)
				{
					rcpDetailVO.setRequestKey(fileInfoVO.getStrRequestKey());
					rcpDetailVO.setRecYn("Y");
					int returnUpdateCnt = rcpMgmtService.updateRcpRequestRecYn(rcpDetailVO);
					if(returnUpdateCnt > 0)
					{
						custMgmtService.updateRecYn(fileInfoVO.getStrRequestKey());
					}
				}
			}

			//녹취파일 4
			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_3()))
			{
				returnCnt = orgCommonService.updateFile23(fileInfoVO);
				resultMap.put("fileId4", fileInfoVO.getFile_upload1_s_3());

				if(returnCnt > 0)
				{
					rcpDetailVO.setRequestKey(fileInfoVO.getStrRequestKey());
					rcpDetailVO.setRecYn("Y");
					int returnUpdateCnt = rcpMgmtService.updateRcpRequestRecYn(rcpDetailVO);
					if(returnUpdateCnt > 0)
					{
						custMgmtService.updateRecYn(fileInfoVO.getStrRequestKey());
					}
				}
			}

			//녹취파일 5
			if(!StringUtils.isEmpty(fileInfoVO.getFile_upload1_s_4()))
			{
				returnCnt = orgCommonService.updateFile24(fileInfoVO);
				resultMap.put("fileId5", fileInfoVO.getFile_upload1_s_4());

				if(returnCnt > 0)
				{
					rcpDetailVO.setRequestKey(fileInfoVO.getStrRequestKey());
					rcpDetailVO.setRecYn("Y");
					int returnUpdateCnt = rcpMgmtService.updateRcpRequestRecYn(rcpDetailVO);
					if(returnUpdateCnt > 0)
					{
						custMgmtService.updateRecYn(fileInfoVO.getStrRequestKey());
					}
				}
			}
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.info(generateLogMsg("수정 건수 = " + returnCnt));

		} catch (Exception e) {
		    resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", ""))
			{
                throw new MvnoErrorException(e);
			}
		}
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}


	/**
	 * @Description : 녹취된 파일의 명을 찾아온다.
	 * @Param  : HndstAmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/org/common/getRequestKey.json")
    public String getRequestKey(FileInfoVO fileInfoVO, Model model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("첨부 파일 명 찾기 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {

        	List<?> resultList = orgCommonService.getRequestKey(fileInfoVO);

        	for (int i = 0; i < resultList.size(); i++) {
        		EgovMap tempMap = (EgovMap)resultList.get(i);

        		if(i==0)
        		{
        			resultMap.put("fileNm", tempMap.get("fileNm"));
        			resultMap.put("fileId", tempMap.get("fileId"));
        		}

        		if(i==1)
        		{
        			resultMap.put("fileNm1", tempMap.get("fileNm"));
        			resultMap.put("fileId1", tempMap.get("fileId"));
        		}

        		if(i==2)
        		{
        			resultMap.put("fileNm2", tempMap.get("fileNm"));
        			resultMap.put("fileId2", tempMap.get("fileId"));
        		}

        		if(i==3)
        		{
        			resultMap.put("fileNm3", tempMap.get("fileNm"));
        			resultMap.put("fileId3", tempMap.get("fileId"));
        		}

        		if(i==4)
        		{
        			resultMap.put("fileNm4", tempMap.get("fileNm"));
        			resultMap.put("fileId4", tempMap.get("fileId"));
        		}
			}
        	resultMap.put("fileTotalCnt", String.valueOf(resultList.size()));
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

            logger.debug("result = " + resultMap);
        } catch (Exception e) {

			if (!getErrReturn(e, (Map<String, Object>) resultMap, "/org/common/getRequestKey.json", 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					"", "MSP1000014", "신청등록"))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}
			throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }
}