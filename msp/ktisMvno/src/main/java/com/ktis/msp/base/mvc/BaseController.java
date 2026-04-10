package com.ktis.msp.base.mvc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
//import com.ktis.msp.base.exception.MvnoAuthException;
//import com.ktis.msp.base.exception.MvnoParamException;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;

public class BaseController extends BaseObject {
	
	//
	// 만일 USE_HTML_OBJECT = true 인 경우 html내 <div>내에 <object>를 지정하지 않을 
	// 경우에는 브라우저 전체화면으로 해당 오브젝트가 View 됨. (단 Password 존재시 Load 실패)
	//
//	private static boolean USE_HTML_OBJECT = false; //  true: <object> </object>
													// false: 단순 다운로드
	
	/** 다운로드 버퍼 크기 */
//	private static final int BUFFER_SIZE = 8192; // 8kb

	/** 문자 인코딩 */
//	private static final String CHARSET = "UTF-8";


	// 조효제 추가 2014.08.14 문의 바람
    @Autowired  
    protected MessageSource messageSource; 
    
	@Resource(name="txManager")
    public DataSourceTransactionManager txManager;
//	
	private DefaultTransactionDefinition mTxDefinition = null;
	private TransactionStatus mTxStatus = null;
//	private DataSourceTransactionManager txManager = null;
	
	protected boolean IsNullOrEmpty(String str) {
		if ((str == null) || (str.length() < 1))
			return(true);
		return(false);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName: isMobileWeb
	 * 2. ClassName : MvnoUtil
	 * 3. Commnet   : URL 호출자의 유형을 파악
	 *                   true: 모바일(안드로이드폰, 아이폰)
	 *                  false: 데스크탑(윈도우, 리눅스 등)
	 * 4. 작성자    : 장재명
	 * 5. 작성일    : 2013. 12. 6. 오전 9:59:18
	 * </PRE>
	 * 		@return void
	 * 		@param request
	 */
//	protected boolean isMobileWeb(HttpServletRequest request, String validationmobileweb) {
//		String os = WhatOS(request).toLowerCase();
//		if (validationmobileweb != null)
//		{
//			return os.matches(validationmobileweb);
//		} else if (os.equals("android") == true || os.equals("iphone") == true)
//		{
//			return(true);
//		}
//		return(false);
//	}

//	protected HashMap<String, String> toHashMap(boolean cmdtable, Map<String, String[]> requestParams) {
//		
//		HashMap<String, String> outmap = new HashMap<String, String>();
// 
//        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) 
//        {
//            String key = entry.getKey();         // parameter name
//            String[] value = entry.getValue();   // parameter values as array of String
//            String valueString = "";
//            
//            // in case of checkbox input, value may be array of length greater than one
//            if (value.length > 1) {
//                for (int i = 0; i < value.length; i++) {
//                    valueString += value[i] + " ";
//                }
//            } else {
//                valueString = value[0];
//            }
//
//        	if (key.equalsIgnoreCase("_cmd_") || key.equalsIgnoreCase("_table_"))
//        	{
//                if (cmdtable == true)
//                    outmap.put(key,  valueString);
//                continue;
//            }
//            outmap.put(key,  valueString);
//        }
//        return(outmap);
//	}
	
	/**
	 * <PRE>
	 * 1. MethodName: finalWebPage
	 * 2. ClassName : eBillController
	 * 3. Commnet   : PC용/모바일용 최종 웹페이지를 구분 
	 * 4. 작성자    : 장재명
	 * 5. 작성일    : 2013. 12. 6. 오전 10:00:32
	 * </PRE>
	 * 		@return String
	 * 		@param webpagename
	 * 		@return
	 */
//	protected String finalWebPage(HttpServletRequest request, String webpagename, String validationmobileweb) {
//		boolean mobileweb = MvnoUtil.isMobileWeb(request, validationmobileweb);
//		if (mobileweb == true)
//			return "m_" + webpagename;
//		else
//			return webpagename;
//	}

//	protected String ReadHttpParam(HttpServletRequest request, String paramname) throws Exception {
//		String paramvalue = (String) request.getParameter(paramname);
//
//		if (paramvalue == null || paramvalue.length() < 1)
//			throw new MvnoParamException(-2000, String.format("'%s' value is empty", paramname));
//
//		return paramvalue;
//	}

//	protected String REQ(HttpServletRequest request) {
//		if (request.getMethod().equals("GET"))
//		{
//			String qry = request.getQueryString();
//			if ((qry == null) || (qry.length() < 1))
//				return(request.getRequestURL().toString());
//			else
//				return(request.getRequestURL().toString() + "?" + request.getQueryString());
//		}
//		
//		StringBuilder sb = new StringBuilder(); 
//		Enumeration en = request.getParameterNames();
//		// enumerate through the keys and extract the values from the keys! 
//		while (en.hasMoreElements()) {
//		    String parameterName = (String) en.nextElement();
//		    String parameterValue = request.getParameter(parameterName);
//		    if (0 < sb.length())
//		    	sb.append("&");
//		    if (parameterValue.length() < 256)
//		    	sb.append(parameterName + "=" + parameterValue);
//		}
//		return(request.getRequestURL() + "?" + sb.toString());
//	}
	
//	protected String REQLOG(HttpServletRequest request) {
//		String orgurl = REQ(request);
//		return("▶ " + orgurl);
//		
//		/*
//		Map<String, String[]> m = request.getParameterMap();
//		String param = null;
//		if (m != null)
//		{
//			Set s = m.entrySet();
//			Iterator it = s.iterator();
//			while(it.hasNext())
//			{
//				Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();
//				String key             = entry.getKey();
//				String[] value         = entry.getValue();
//	
//				if (param == null)
//					param = key + "=";
//				else
//					param += key + "=";
//
//				if (value != null)
//				{
//					for (int i = 0; i < value.length; i++)
//						param += value[i] + " ";
//				}
//			}
//		}
//            
//		String ret = null;
//		if (param != null)
//			ret = "▶ " + request.getRequestURI() + "?" + param;
//		else
//			ret = "▶ " + request.getRequestURI();
//		return(ret);
//		*/
//	}
	
//	protected void CheckAuth(HttpServletRequest request) throws MvnoAuthException {
//		HttpSession session = request.getSession(false);
//		if (session == null)
//			throw new MvnoAuthException(401, "관리자 인증이 필요합니다.");
//
//		Object obj = session.getAttribute("mgrlogin");
//		String mgrlogin = (String) obj;
//		if ((mgrlogin == null) || (mgrlogin.length() < 1))
//			throw new MvnoAuthException(401, "관리자 인증이 필요합니다.");
//	}
	
//	protected int ReadHttpParamInt(HttpServletRequest request, String paramname) throws Exception {
//		String paramvalue = (String) request.getParameter(paramname);
//
//		if (paramvalue == null || paramvalue.length() < 1)
//			throw new MvnoParamException(-2000, String.format("'%s' value is empty", paramname));
//
//		return Integer.parseInt(paramvalue);
//	}
	
//	protected String ReadHttpUtf8Param(HttpServletRequest request, String paramname) throws Exception {
//		String paramvalue = (String) request.getParameter(paramname);
//
//		if (paramvalue == null || paramvalue.length() < 1)
//			throw new MvnoParamException(-2000, String.format("'%s' value is empty", paramname));
//
//		return new String(paramvalue.getBytes("8859_1"), "UTF-8");
//	}

//	protected String ReadSessionParam(HttpSession session, String paramname) {
//		Object obj = session.getAttribute(paramname);
//
//		if (obj != null)
//			return (String)obj;
//		return null;
//	}

//	protected String BuildError(ModelMap model, String errmsg) {
//		model.addAttribute("errormsg", errmsg);
//		return "error";
//	}
//
//	protected boolean IsAvailEmail(String inp_email) {
//		if (inp_email == null || inp_email.length() < 1)
//			return false;
//		String regex = "(\\w+\\.)*\\w+@(\\w+\\.)+[A-Za-z]+";
//		Pattern pattern = Pattern.compile(regex);
//		return(pattern.matcher(inp_email).matches());
//	}
//
//	protected boolean IsAvailMobile(String inp_mobile) {
//		if (inp_mobile == null || inp_mobile.length() < 1)
//			return false;
//		String regex = "01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}";
//		Pattern pattern = Pattern.compile(regex);
//		return(pattern.matcher(inp_mobile).matches());
//	}
//
//	protected String Utf8UrlEncode(String data) {
//		try
//		{
//			String encdata = URLEncoder.encode(data, "UTF-8");
//			return encdata;
//		} catch (Exception e)
//		{
//			return data;
//		}
//	}
//
//	protected String Utf8UrlDecode(String encdata) {
//		try
//		{
//			String decdata = URLDecoder.decode(encdata, "UTF-8");
//			return decdata;
//		} catch (Exception e)
//		{
//			return encdata;
//		}
//	}
//
//	protected String WhatOS(HttpServletRequest request) {
//		String  userAgent  = request.getHeader("User-Agent");
//		if ((userAgent == null) || (userAgent.length() < 1))
//				return "Windows";
//		String  ua         = userAgent.toLowerCase();
//		String  os         = "Windows";
//
//		//=================OS=======================
//		if (ua.indexOf("windows") >= 0 )
//		{
//			os = "Windows";
//		}
//		else if(ua.indexOf("mac") >= 0)
//		{
//			os = "Mac";
//		}
//		else if(ua.indexOf("x11") >= 0)
//		{
//			os = "Unix";
//		}else if(ua.indexOf("android") >= 0)
//		{
//			os = "Android";
//		}
//		else if(ua.indexOf("iphone") >= 0)
//		{
//			os = "IPhone";
//		}else{
//			os = "UnKnown, More-Info: "+userAgent;
//		}
//		return os;
//	}
//
//	protected String WhatBrowser(HttpServletRequest request) {
//		String  userAgent  = request.getHeader("User-Agent");
//		String  user       = userAgent.toLowerCase();
//		String  browser    = null;
//
//		//===============Browser===========================
//		if (user.contains("msie"))
//		{
//			String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
//			browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
//		}
//		else if (user.contains("safari") && user.contains("version"))
//		{
//			browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
//		}
//		else if ( user.contains("opr") || user.contains("opera"))
//		{
//			if(user.contains("opera"))
//				browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
//			else if(user.contains("opr"))
//				browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
//		}
//		else if (user.contains("chrome"))
//		{
//			browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
//		}
//		else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
//		{
//			//browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
//			browser = "Netscape-?";
//
//		}
//		else if (user.contains("firefox"))
//		{
//			browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
//		}
//		else
//		{
//			browser = "UnKnown, More-Info: "+userAgent;
//		}
//		return browser;
//	}

//	protected void Waiting(int millsec) {
//		try
//		{
//			Thread.sleep(millsec);
//		} catch (Exception e)
//		{
//		}
//	}

	/**
	 * 랜덤한 문자열을 원하는 길이만큼 반환합니다.
	 * 
	 * @pureint true: 숫자,  false: 숫자+문자
	 * @param length 문자열 길이
	 * @return 랜덤문자열
	 */
	/*
	 * 이메일 인증시 비밀번호 규칙입니다.
         비밀 번호 생성 규칙

-       영문/숫자 조합시 10자리 이상, 영문/숫자/특수문자 조합 시 8자리 이상
        통상적으로 이메일 인증키는 12자리를 사용
	 */

//	protected String GenerateActKey(boolean pureint, int length) {
//		StringBuffer buffer = new StringBuffer();
//		Random random = new Random();
//
//		String base_alpha[] = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");
//		String base_num[] = "0,1,2,3,4,5,6,7,8,9".split(",");
//
//		if (pureint == true)
//		{
//			for (int i=0; i<length; i++)
//				buffer.append(base_num[random.nextInt(base_num.length)]);
//		} else
//		{
//			for (int i=0; i<length; i++)
//				buffer.append(base_alpha[random.nextInt(base_alpha.length)]);
//		}
//		return buffer.toString();
//	}

	/**
	 * Inputstream을 파일로 저장
	 * 
	 * @param inputStream
	 * @param filename to save
	 */	  
	protected void StreamToFile(InputStream inputStream, String filename) {
		try 
		{
			// 출력할 파일명을 지정한다.
			File file = new File(filename);
			//if (file.isFile())
			//  file.delete();
			OutputStream outStream = null;
			
			try {
				outStream = new FileOutputStream(file);
				
				// 읽어들일 버퍼크기를 메모리에 생성
				byte[] buf = new byte[1024];
				int len = 0;
				// 끝까지 읽어들이면서 File 객체에 내용들을 쓴다
				while ((len = inputStream.read(buf)) > 0){
					outStream.write(buf, 0, len);
				}
			} catch (Exception e) {
			    throw new MvnoErrorException(e);
			} finally {
				// Stream 객체를 모두 닫는다.
				if(outStream != null){
					outStream.close();
				}
				
				if(inputStream != null){
					inputStream.close();
				}				
			}
		} catch (Exception e) {
		    throw new MvnoErrorException(e);
		}
	}

//	protected String FileToString(String file) {
//		String result = null;
//		DataInputStream in = null;
//
//		try {
//			File f = new File(file);
//			byte[] buffer = new byte[(int) f.length()];
//			in = new DataInputStream(new FileInputStream(f));
//			in.readFully(buffer);
//			result = new String(buffer);
//		} catch (IOException e) {
//			throw new RuntimeException("IO problem in fileToString", e);
//		} finally {
//			try {
//				in.close();
//			} catch (IOException e) { /* ignore it */
//				dummyCatch();
//			}
//		}
//		return result;
//	}

	/**
	 * 파일을 클라이언트로 응답
	 * 
	 * @param request
	 * @param response 
	 * @param filename to read
	 */	  
//	protected void FileToClient(HttpServletRequest request, HttpServletResponse response, String filename) {
//		try 
//		{
//			// 출력할 파일명과 읽어들일 파일명을지정한다.
//			File file = new File(filename);
//			FileToClientEx(request, response, file);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 지정된 파일을 다운로드 한다.
	 * 
	 * @param request
	 * @param response
	 * @param file
	 *            다운로드할 파일
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
//	protected void FileToClientEx(HttpServletRequest request, HttpServletResponse response, File file) 
//			throws ServletException, IOException {
//
//		String mimetype = request.getSession().getServletContext().getMimeType(file.getName());
//
//		if (file == null || !file.exists() || file.length() <= 0 || file.isDirectory()) 
//		{
//			throw new IOException("파일 객체가 Null 혹은 존재하지 않거나 길이가 0, 혹은 파일이 아닌 디렉토리이다.");
//		}
//
//		InputStream is = null;
//
//		try 
//		{
//			is = new FileInputStream(file);
//			flushresponse(request, response, is, file.getName(), file.length(), mimetype);
//		} finally 
//		{
//			try {
//				is.close();
//			} catch (Exception ex) {
//			}
//		}
//			}

	/**
	 * 해당 입력 스트림으로부터 오는 데이터를 다운로드 한다.
	 * 
	 * @param request
	 * @param response
	 * @param is
	 *            입력 스트림
	 * @param filename
	 *            파일 이름
	 * @param filesize
	 *            파일 크기
	 * @param mimetype
	 *            MIME 타입 지정
	 * @throws ServletException
	 * @throws IOException
	 */
//	protected boolean flushresponse(HttpServletRequest request, HttpServletResponse response, InputStream is,
//			String filename, long filesize, String mimetype) throws ServletException, IOException {
//		String mime = mimetype;
//
//		if (mimetype == null || mimetype.length() == 0) {
//			mime = "application/octet-stream;";
//		}
//
//		byte[] buffer = new byte[BUFFER_SIZE];
//
//		//response.setContentType(mime);
//		response.setContentType(mime + "; charset=" + CHARSET);
//
//		String userAgent = request.getHeader("User-Agent");
//		if (userAgent != null && (0 < userAgent.length()))
//		{
//			// 아래 부분에서 euc-kr 을 utf-8 로 바꾸거나 URLEncoding을 안하거나 등의 테스트를
//			// 해서 한글이 정상적으로 다운로드 되는 것으로 지정한다.
//			
//			// attachment; 가 붙으면 IE의 경우 무조건 다운로드창이 뜬다. 상황에 따라 써야한다.
//			if (-1 < userAgent.indexOf("MSIE 5.5")) { // MS IE 5.5 이하
//				response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(filename, "UTF-8") + ";");
//			} else if (-1 < userAgent.indexOf("MSIE")) { // MS IE (보통은 6.x 이상 가정)
//				if (USE_HTML_OBJECT == true)
//					response.setHeader("Content-Disposition", "inline; filename=" + java.net.URLEncoder.encode(filename, "UTF-8") + ";");
//				else
//					response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8") + ";");
//		
//				response.setHeader("Cache-Control", "must-revalidate");
//				response.setHeader("Pragma", "public");
//			} else { // 모질라나 오페라
//				if (USE_HTML_OBJECT == true)
//					response.setHeader("Content-Disposition", "inline; filename="+ new String(filename.getBytes(CHARSET), "latin1") + ";");
//				else
//					response.setHeader("Content-Disposition", "attachment; filename="+ new String(filename.getBytes(CHARSET), "latin1") + ";");
//			}
//		}
//		
//		response.setHeader("Content-Transfer-Encoding", "binary");
//
//		// 파일 사이즈가 정확하지 않을때는 아예 지정하지 않는다.
//		response.setHeader("Content-Length", String.valueOf(filesize));
//		//response.setContentLength((int)filesize);
//
//		OutputStream outstream = null;
//		int total = 0;
//		try {
//			int read = 0;
//			outstream = response.getOutputStream();
//			while ((read = is.read(buffer)) != -1) {
//				total += read;
//				outstream.write(buffer, 0, read);
//			}
//		} catch (IOException ex) {
//			//
//		} finally {
//			if (outstream != null)
//				outstream.close();
//		} // end of try/catch
//		
//		if (filesize == total)
//			return true;
//		return false;
//	}

//	protected String flushTextData(HttpServletResponse response, String body, boolean xmltag)
//			throws ServletException, IOException {
//		OutputStream outstream = null;
//		try 
//		{
//			response.setContentType("text/xml");   //set response header
//			outstream = response.getOutputStream();
//			byte[] buffer = body.getBytes();
//			if (xmltag == true)
//			{
//				String head = "<?xml version='1.0' encoding='UTF-8'?>\n";
//				byte[] headbyte = head.getBytes();
//				outstream.write(headbyte, 0, headbyte.length);
//			}
//
//			outstream.write(buffer, 0, buffer.length);
//		} catch (IOException e) {
//			throw new ServletException(e.getMessage());
//		} finally {
//			if (outstream != null)
//				outstream.close();
//		}
//		return null;
//	}

//	protected void flushHtmlData(HttpServletResponse response, String body)	
//			throws ServletException, IOException {
//		OutputStream outstream = null;
//		try 
//		{
//			response.setContentType("text/html");   //set response header
//			outstream = response.getOutputStream();
//			byte[] buffer = body.getBytes();
//			outstream.write(buffer, 0, buffer.length);
//		} catch (IOException e) {
//			throw new ServletException(e.getMessage());
//		} finally {
//			if (outstream != null)
//				outstream.close();
//		}
//	}
	
	/**
	 * 해당 입력 스트림으로부터 오는 데이터를 다운로드 한다.
	 * 
	 * @param request
	 * @param response
	 * @param is
	 *            입력 스트림
	 * @param filename
	 *            파일 이름
	 * @param filesize
	 *            파일 크기
	 * @param mimetype
	 *            MIME 타입 지정
	 * @throws ServletException
	 * @throws IOException
	 */
//	public static void downloadusingbuffer(HttpServletRequest request, HttpServletResponse response, InputStream is,
//			String filename, long filesize, String mimetype) throws ServletException, IOException {
//		String mime = mimetype;
//
//		if (mimetype == null || mimetype.length() == 0) {
//			mime = "application/octet-stream;";
//		}
//
//		byte[] buffer = new byte[BUFFER_SIZE];
//
//		response.setContentType(mime + "; charset=" + CHARSET);
//
//		// 아래 부분에서 euc-kr 을 utf-8 로 바꾸거나 URLEncoding을 안하거나 등의 테스트를
//		// 해서 한글이 정상적으로 다운로드 되는 것으로 지정한다.
//		String userAgent = request.getHeader("User-Agent");
//		if (userAgent == null || userAgent.length() < 1)
//			throw new IOException("알 수 없는 User Agent입니다.");
//
//		// attachment; 가 붙으면 IE의 경우 무조건 다운로드창이 뜬다. 상황에 따라 써야한다.
//		if (userAgent != null && userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5 이하
//			response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(filename, "UTF-8") + ";");
//		} else if (userAgent != null && userAgent.indexOf("MSIE") > -1) { // MS IE (보통은 6.x 이상 가정)
//			response.setHeader("Content-Disposition", "attachment; filename="
//					+ java.net.URLEncoder.encode(filename, "UTF-8") + ";");
//		} else { // 모질라나 오페라
//			response.setHeader("Content-Disposition", "attachment; filename="
//					+ new String(filename.getBytes(CHARSET), "latin1") + ";");
//		}
//
//		// 파일 사이즈가 정확하지 않을때는 아예 지정하지 않는다.
//		if (filesize > 0) {
//			response.setHeader("Content-Length", "" + filesize);
//		}
//		response.setHeader("Content-Transfer-Encoding", "binary");
//
//		BufferedInputStream fin = null;
//		BufferedOutputStream outs = null;
//
//		try {
//			fin = new BufferedInputStream(is);
//			outs = new BufferedOutputStream(response.getOutputStream());
//			int read = 0;
//
//			while ((read = fin.read(buffer)) != -1) {
//				outs.write(buffer, 0, read);
//			}
//		} catch (IOException ex) {
//			// Tomcat ClientAbortException을 잡아서 무시하도록 처리해주는게 좋다.
//		} finally {
//			try {
//				outs.close();
//			} catch (Exception ex1) {
//			}
//
//			try {
//				fin.close();
//			} catch (Exception ex2) {
//
//			}
//		} // end of try/catch
//	}

//	protected void downloadbyte(HttpServletRequest request, HttpServletResponse response, String objname, byte[] content)
//			throws ServletException, IOException 
//			{
//		String mimetype = request.getSession().getServletContext().getMimeType(objname);
//
//		if (content == null || content.length <= 0) {
//			throw new IOException("Byte array Null 혹은 길이가 0 입니다.");
//		}
//
//		try 
//		{
//			downloadbyte(request, response, content, objname, content.length, mimetype);
//		} finally 
//		{
//		}
//			}

//	protected void downloadbyte(HttpServletRequest request, HttpServletResponse response, byte[] buffer,
//			String filename, long filesize, String mimetype) throws ServletException, IOException 
//			{
//		String mime = mimetype;
//
//		if (mimetype == null || mimetype.length() == 0) {
//			mime = "application/octet-stream;";
//		}
//
//		response.setContentType(mime + "; charset=" + CHARSET);
//
//		// 아래 부분에서 euc-kr 을 utf-8 로 바꾸거나 URLEncoding을 안하거나 등의 테스트를
//		// 해서 한글이 정상적으로 다운로드 되는 것으로 지정한다.
//		String userAgent = request.getHeader("User-Agent");
//		if (userAgent == null || userAgent.length() < 1)
//			throw new IOException("알 수 없는 User Agent입니다.");
//
//		// attachment; 가 붙으면 IE의 경우 무조건 다운로드창이 뜬다. 상황에 따라 써야한다.
//		if (userAgent != null && userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5 이하
//			response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(filename, "UTF-8") + ";");
//		} else if (userAgent != null && userAgent.indexOf("MSIE") > -1) { // MS IE (보통은 6.x 이상 가정)
//			response.setHeader("Content-Disposition", "attachment; filename="
//					+ java.net.URLEncoder.encode(filename, "UTF-8") + ";");
//		} else { // 모질라나 오페라
//			response.setHeader("Content-Disposition", "attachment; filename="
//					+ new String(filename.getBytes(CHARSET), "latin1") + ";");
//		}
//
//		// 파일 사이즈가 정확하지 않을때는 아예 지정하지 않는다.
//		if (filesize > 0) {
//			response.setHeader("Content-Length", "" + filesize);
//		}
//		response.setHeader("Content-Transfer-Encoding", "binary");
//
//		BufferedOutputStream outs = null;
//
//		try {
//			outs = new BufferedOutputStream(response.getOutputStream());
//			int wlen = 0;
//			int limit = buffer.length;
//			int slice = 4096;
//			while (wlen < limit)
//			{
//				outs.write(buffer, wlen, slice);
//				wlen += slice;
//				slice = limit - wlen;
//				if (4096 < slice)
//					slice = 4096;
//			}
//		} catch (IOException ex) {
//			// Tomcat ClientAbortException을 잡아서 무시하도록 처리해주는게 좋다.
//		} finally {
//			try {
//				outs.close();
//			} catch (Exception ex1) {
//			}
//		} // end of try/catch
//	}
	
	
	// 조효제 추가 2014.08.14 문의 바람
    
	//---------------------------------
	// transaction
	//---------------------------------



	protected void startTx()
	{
		
		mTxDefinition = new DefaultTransactionDefinition();    
		mTxDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);    

		mTxStatus = txManager.getTransaction(mTxDefinition);
	}
    
    protected void commit(){
    	
    	try{
    		txManager.commit(mTxStatus);
    	}catch ( org.springframework.transaction.IllegalTransactionStateException e)
    	{
    	    return;
    	}    	
    }
    
    protected void rollback(){
    	
    	try{
    		txManager.rollback(mTxStatus);
    	}catch ( org.springframework.transaction.IllegalTransactionStateException e)
    	{
    	    return;
    	}    	
    }


	public static void printRequest(HttpServletRequest pReq )
	{
//		System.out.println("===========================================");
//		System.out.println("======  BaseController.printRequest() ==");
//		System.out.println("===========================================");


		Enumeration<?> eParam = pReq.getParameterNames();
		while (eParam.hasMoreElements()) {
			String pName = (String)eParam.nextElement();
			String pValue = pReq.getParameter(pName);
			
			loggerStatic.debug(pName + " : " + pValue);
		}

	}
	
	public Map<String, Object> makeResultMultiRowNotEgovMap(Map<String, Object>pRequestParamMap,  List<?> pResultList, int total)
	{
    	//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    Map<String, Object> dataMap = new HashMap<String, Object>();
	    
	    logger.debug("==============================================" );
	    
        resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
        resultMap.put("msg", "");
        
        dataMap.put("pageIndex", pRequestParamMap.get("pageIndex"));
        dataMap.put("pageSize", pRequestParamMap.get("pageSize"));
        dataMap.put("total",  total);
        dataMap.put("rows", pResultList);
        
		resultMap.put("data", dataMap);
		
		return resultMap;

	}
	
	public Map<String, Object> makeResultMultiRow2(Map<String, Object>pRequestParamMap,  List<?> pResultList)
	{
    	//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    Map<String, Object> dataMap = new HashMap<String, Object>();
	    
	    logger.debug("========  result  (makeResultMultiRow(Map<String, Object>p_requestParamMap,  List<?> p_resultList))===" );
	    logger.debug("===" + pResultList );
//	    HashMap l_egovMap=(HashMap)p_resultList.get(0);
//	    
//	    Iterator itr =  l_egovMap.keySet().iterator();
//		while (itr.hasNext()) {
//			String l_maskFieldsKey = (String)itr.next();
//			logger.debug( ">>>>>>>>>>>>>l_maskFieldsKey : " + l_maskFieldsKey);
//		}
		
	    logger.debug("==============================================" );
	    
        resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
        resultMap.put("msg", "");
        
        dataMap.put("pageIndex", pRequestParamMap.get("pageIndex"));
        dataMap.put("pageSize", pRequestParamMap.get("pageSize"));
        dataMap.put("total",  pResultList.size() == 0 ?  "0" : "" + ((Map) pResultList.get(0)).get("TOTAL_COUNT"));
        dataMap.put("rows", pResultList);
        
		resultMap.put("data", dataMap);
		
		return resultMap;

	}
	
	public Map<String, Object> makeResultMultiRow(Map<String, Object>pRequestParamMap,  List<?> pResultList)
	{
    	//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    Map<String, Object> dataMap = new HashMap<String, Object>();
	    
        resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
        resultMap.put("msg", "");
        
        dataMap.put("pageIndex", pRequestParamMap.get("pageIndex"));
        dataMap.put("pageSize", pRequestParamMap.get("pageSize"));
        

        
        if ( pResultList.size() == 0 )
        {
        	dataMap.put("total",   "0" ); 
        }else
        {
        	if (  ((Map) pResultList.get(0)).containsKey("totalCount") ) 
        	{
        		dataMap.put("total",  "" + ((Map) pResultList.get(0)).get("totalCount") );  
        	}else
        	{
        		dataMap.put("total",  "" + ((Map) pResultList.get(0)).get("TOTAL_COUNT") );  
        	}
        }
        	
        dataMap.put("rows", pResultList);
        
		resultMap.put("data", dataMap);
		
		return resultMap;

	}
	
	public Map<String, Object> makeResultMultiRow(BaseVo pBaseVo,  List<?> pResultList)
	{
    	//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
	    
	    logger.debug("========  result (makeResultMultiRow(BaseVo p_baseVo,  List<?> p_resultList)) ===" );
	    logger.debug("===" + pResultList );
	    logger.debug("==============================================" );

	    
		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		resultMap.put("msg", "");
		      
		dataMap.put("pageIndex", pBaseVo.getPageIndex());
		dataMap.put("pageSize",   pBaseVo.getPageSize());
		dataMap.put("total",  pResultList.size() == 0 ?  "0" : "" + ((Map) pResultList.get(0)).get("totalCount"));
		dataMap.put("rows", pResultList);
		
		      
		resultMap.put("data", dataMap);
		
		return resultMap;

	}
	
	/**
	* @Description : 트리 그리드에서 사용 
	* @Param  : 
	* @Return : Map<String,Object>
	* @Author : 고은정
	* @Create Date : 2014. 10. 13.
	 */
	public Map<String, Object> makeResultTreeRow(Map<String, Object>pRequestParamMap,  List<?> pResultList, String id)
    {
        //--------------------------------------
        // return JSON 변수 선언
        //--------------------------------------
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();

        logger.debug("========  result  (makeResultTreeRow(Map<String, Object>p_requestParamMap,  List<?> p_resultList))===" );
        logger.debug("===" + pResultList );
        logger.debug("==============================================" );
        
        resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
        resultMap.put("msg", "");
        
        dataMap.put("rows", pResultList);
        
        if(KtisUtil.isEmpty(id)){
            dataMap.put("parent","0");    
        } else {
            dataMap.put("parent",id);
        }
        
        resultMap.put("data", dataMap);
        
        return resultMap;

    }
	
	public Map<String, Object> makeResultSingleRow(BaseVo pBaseVo,  BaseVo pResultVo)
	{
    	//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

	    logger.debug("========  result (makeResultSingleRow(BaseVo p_baseVo,  BaseVo p_resultVo)) ===" );
	    logger.debug("===" + pResultVo );
	    logger.debug("==============================================" );

		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		resultMap.put("msg", "");
      
		resultMap.put("data", pResultVo);

		
		return resultMap;

	}
	

	public Map<String, Object> makeResultSingleRow(Map<String, Object>pRequestParamMap,  List<?> pResultList)
	{
    	//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

	    logger.debug("========  result (makeResultSingleRow(Map<String, Object>pRequestParamMap,  List<?> p_resultList)) ===" );
	    logger.debug("===" + pResultList );
	    logger.debug("==============================================" );

		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		resultMap.put("msg", "");

		resultMap.put("data", pResultList.get(0));

		
		return resultMap;

	}




}
