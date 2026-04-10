package com.ktmmobile.mcp.common.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.StringUtil;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@Controller
public class OcrController {

    private static final Logger logger = LoggerFactory.getLogger(OcrController.class);

    @Value("${OCR.server1}")
    private String OCRserver1;

    @Value("${OCR.server2}")
    private String OCRserver2;

    @Value("${OCREID.server1}")
    private String OCREidserver1;

    @Value("${OCREID.server2}")
    private String OCREidserver2;

    @Value("${OCR.license}")
    private String OCRlicense;

    @Value("${OCREID.license}")
    private String OCREidlicense;

    String ocrServer1List = "172.27.1.0,172.27.1.9,172.27.0.212";

    @Value("${OCRIMG.server}")
    private String OCRIMGserver;

    /**
     * 설명 : 키 생성 요청
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/m/getOcrReadyAjax.do"})
    @ResponseBody
    public JsonReturnDto getOcrReadyAjax(HttpServletRequest request, Model model){

        String OCRserver = "";
        try {
            String sIP = InetAddress.getLocalHost().getHostAddress();

            // dev : 172.27.1.0
            // stg was1 : 172.27.1.9
            // prd was1 : 172.27.0.212

            String[] server = ocrServer1List.split(",");

            boolean ocrServer1on = false;
            for(String callserver : server) {
                if(callserver.equals(sIP)) {
                    ocrServer1on = true;
                    break;
                }
            }

            if(ocrServer1on) {
                OCRserver = OCRserver1;
            } else {
                OCRserver = OCRserver2;
            }

        } catch (UnknownHostException e1) {
            logger.error(e1.getMessage());
        }

        JsonReturnDto result = new JsonReturnDto();
        String retCode = "99";
        String retMsg = "오류가 발생했습니다. 다시 시도해 주세요.";


        String type = StringUtil.NVL(request.getParameter("type"),"");

        if ("social".equals(type)) {
            type = "jumin";
        }

        CloseableHttpClient httpClient = null;
        try {
            // Http client 생성
            httpClient = HttpClientBuilder.create().build();

            // 전송방식 POST
            HttpPost httpPost = new HttpPost();
            httpPost.addHeader("content-type", "application/json");

            URI uri = new URIBuilder(OCRserver.concat("/idocr/api/v3/ocr/start"))
                    .addParameter("cardType", type)
                    .addParameter("license", OCRlicense)
                    .build();


            httpPost.setURI(uri);

            // 응답 처리
            HttpResponse httpResponse = httpClient.execute(httpPost);

            // 결과값
            String responseStrJson = EntityUtils.toString(httpResponse.getEntity());

            retCode = "0000";
            retMsg = responseStrJson;



        } catch(IOException e){
            logger.error(e.getMessage());
        } catch(Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        result.setReturnCode(retCode);
        result.setMessage(retMsg);

        return result;
    }


    /**
     * 설명 : 키를 전단하고 결과값을 요청한다.
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/m/getOcrResultAjax.do"})
    @ResponseBody
    public JsonReturnDto getOcrResultAjax(HttpServletRequest request, Model model){

        String OCRserver = "";
        try {
            String sIP = InetAddress.getLocalHost().getHostAddress();

            // dev : 172.27.1.0
            // stg was1 : 172.27.1.9
            // prd was1 : 172.27.0.212

            String[] server = ocrServer1List.split(",");

            boolean ocrServer1on = false;
            for(String callserver : server) {
                if(callserver.equals(sIP)) {
                    ocrServer1on = true;
                    break;
                }
            }

            if(ocrServer1on) {
                OCRserver = OCRserver1;
            } else {
                OCRserver = OCRserver2;
            }

        } catch (UnknownHostException e1) {
            logger.error(e1.getMessage());
        }

        JsonReturnDto result = new JsonReturnDto();
        String retCode = "99";
        String retMsg = "오류가 발생했습니다. 다시 시도해 주세요.";


        String token = StringUtil.NVL(request.getParameter("token"),"");

        CloseableHttpClient httpClient = null;
        try {
            // Http client 생성
            httpClient = HttpClientBuilder.create().build();

            // 전송방식 GET
            //HttpGet httpGet = new HttpGet();
            //httpGet.addHeader("content-type", "application/json");

            // 전송방식 POST
            HttpPost httpPost = new HttpPost();
            httpPost.addHeader("content-type", "application/json");

            URI uri = new URIBuilder(OCRserver.concat("/idocr/api/v3/ocr/result"))
                    .addParameter("token", token)
                    .build();

            httpPost.setURI(uri);
            // 응답 처리
            HttpResponse httpResponse = httpClient.execute(httpPost);

            // 결과값
            String responseStrJson = EntityUtils.toString(httpResponse.getEntity());

            retCode = "0000";
            retMsg = responseStrJson;

        } catch(IOException e){
            logger.error(e.getMessage());
        } catch(Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        result.setReturnCode(retCode);
        result.setMessage(retMsg);

        return result;
    }


//	/**
//	 * 설명 : imgOCR  요청
//	 * @Author : 김일환
//	 * @Date : 2022.08.17
//	 * @param request
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value={"/m/getOcrImgReadyAjax.do","/getOcrImgReadyAjax.do"})
//	@ResponseBody
//	public Map<String,Object> getOcrImgReadyAjax(MultipartHttpServletRequest fileRequest, Model model){
//
//		Map<String,Object> hm = new HashMap<String, Object>();
//        String retCode = "99";
//        String retMsg = "오류가 발생했습니다. 다시 시도해 주세요.";
//        String url = OCRIMGserver.concat("/api/v3/imei/ocr");
//        // 1. 파일관련되서 로직 추가
//        MultipartFile file = fileRequest.getFile("image");
//        if(file ==null ) {
//        	retCode = "99";
//        	retMsg = "이미지파일을 확인해 주세요.";
//        	hm.put("retCode", retCode);
//        	hm.put("retMsg", retMsg);
//    		return hm;
//        }
//        String originalFilename = file.getOriginalFilename();
//        String responseStrJson = "";
//        int status = -1;
//		String message = "";
//		String eid = "";
//		String imei1 = ""; // 안드로이드
//		String imei2 = "";
////		String meid = "";
//		int statusCode = 0;
//		int uploadPhoneSrlNo = 0;
//
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		try {
//
//			HttpPost httpPost = new HttpPost(url);
//
//			HttpEntity httpEntity = MultipartEntityBuilder.create()
//					.addBinaryBody("image", file.getBytes(), ContentType.MULTIPART_FORM_DATA, originalFilename)
//					.build();
//
//			httpPost.setEntity(httpEntity);
//			CloseableHttpResponse response = httpClient.execute(httpPost);
//
//			responseStrJson = EntityUtils.toString(response.getEntity());
//			statusCode = response.getStatusLine().getStatusCode();
//			httpClient.close();
//		}catch(Exception e) {
//		}finally {
//			try {
//				httpClient.close();
//			} catch (IOException e) {
//			}
//		}
//
//
//        // 3.데이터 검증
//        try {
//        	logger.info("responseStrJson==>"+ObjectUtils.convertObjectToString(responseStrJson));
//
//        	JSONObject jObject = new JSONObject(responseStrJson);
//        	if(statusCode==200) {
//        		retCode = "0000";
//        		retMsg = "";
//
//        		try {
//        			status = jObject.getInt("status");
//        		}catch(Exception e) {
//        			logger.info("status error");
//        		}
//
//        		try {
//        			message = jObject.getString("message");
//        		}catch(Exception e) {
//        			logger.info("message error");
//        		}
//
//    			JSONObject phoneDataObject = null;
//
//    			try {
//    				phoneDataObject = (JSONObject) jObject.get("result");
//        		}catch(Exception e) {
//        			logger.info("result error");
//        		}
//
//    			if(phoneDataObject !=null){
//    				try {
//    					eid = StringUtil.NVL(phoneDataObject.getString("eid"),"");
//            		}catch(Exception e) {
//            			logger.info("eid error");
//            		}
//
//    				try {
//    					imei1 = StringUtil.NVL(phoneDataObject.getString("imei1"),"");
//            		}catch(Exception e) {
//            			logger.info("imei1 error");
//            		}
//    				try {
//    					imei2 = StringUtil.NVL(phoneDataObject.getString("imei2"),"");
//            		}catch(Exception e) {
//            			logger.info("imei2 error");
//            		}
////    				meid = phoneDataObject.getString("meid");
//    			}
//
//        	} else {
//        		retCode = "0002";
//        		message = jObject.getString("message");
//        		retMsg = "eSIM 개통을 위해서는 EID 및 IMEI2값이 필요합니다.<br/>eSIM 지원 기기 여부를 확인 바랍니다.";
//        	}
//
//        }catch(Exception e) {
//        	retMsg = e.getMessage();
//        	retMsg = "이미지 파일을 확인 해 주세요.";
//        	retCode = "99";
//        }
//
//        hm.put("retCode", retCode);
//    	hm.put("retMsg", retMsg);
//    	hm.put("eid", eid);
//    	hm.put("imei1", imei1);
//    	hm.put("imei2", imei2);
//    	hm.put("uploadPhoneSrlNo", uploadPhoneSrlNo);
//		return hm;
//	}



    /**
     * 설명 : imgOCR  요청
     * @Author : 김일환
     * @Date : 2022.08.17
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/m/getOcrImgReadyAjax.do","/getOcrImgReadyAjax.do"})
    @ResponseBody
    public Map<String,Object> getOcrImgReadyAjax(MultipartHttpServletRequest fileRequest, Model model){

        Map<String,Object> hm = new HashMap<String, Object>();
        String retCode = "99";
        String retMsg = "오류가 발생했습니다. 다시 시도해 주세요.";
        String url = OCRIMGserver.concat("/api/v3/imei/ocr");
        // 1. 파일관련되서 로직 추가
        MultipartFile file = fileRequest.getFile("image");
        if(file ==null ) {
            retCode = "99";
            retMsg = "이미지파일을 확인해 주세요.";
            hm.put("retCode", retCode);
            hm.put("retMsg", retMsg);
            return hm;
        }
        String originalFilename = file.getOriginalFilename();
        JSONObject jObject = null;
        int status = -1;
        String message = "";
        String eid = "";
        String imei1 = ""; // 안드로이드
        String imei2 = "";
//		String meid = "";
        int statusCode = 0;
        int uploadPhoneSrlNo = 0;

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Response response = null;
        try {

            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("image", originalFilename, RequestBody.create(MultipartBody.FORM, file.getBytes()))
                    .build();
            Request request = new Request.Builder().url(url)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .build();
            response = client.newCall(request).execute();

            if(response !=null) {
                if(response.isSuccessful()) {
                    statusCode = response.code();
                    jObject = new JSONObject(response.body().string());
                }
                response.body().close();
            }


        } catch(IOException e){
            logger.error("Exception 111 e : {}", e.getMessage());
        } catch(Exception e) {
            logger.error("Exception 2222 e : {}", e.getMessage());
        } finally {
            if (response != null ) {
                response.close();
            }
        }

        // 3.데이터 검증
        try {
            logger.info("responseStrJson==>"+ObjectUtils.convertObjectToString(jObject));
            logger.info("statusCode==>"+statusCode);

//        	JSONObject jObject = new JSONObject(response.body().string());

            if(statusCode==200) {
                retCode = "0000";
                retMsg = "";

                try {
                    status = jObject.getInt("status");
                } catch(JSONException e){
                    logger.info("status JSONException");
                } catch(Exception e) {
                    logger.info("status error");
                }

                try {
                    message = jObject.getString("message");
                } catch(JSONException e){
                    logger.info("message JSONException");
                } catch(Exception e) {
                    logger.info("message error");
                }

                JSONObject phoneDataObject = null;

                try {
                    phoneDataObject = (JSONObject) jObject.get("result");
                } catch(JSONException e){
                    logger.info("result JSONException");
                } catch(Exception e) {
                    logger.info("result error");
                }

                if(phoneDataObject !=null){
                    try {
                        eid = StringUtil.onlyNum(phoneDataObject.getString("eid"));
                    } catch(JSONException e){
                        logger.info("eid JSONException");
                    } catch(Exception e) {
                        logger.info("eid error");
                    }

                    try {
                        imei1 = StringUtil.NVL(phoneDataObject.getString("imei1"),"");
                    } catch(JSONException e){
                        logger.info("imei1 JSONException");
                    } catch(Exception e) {
                        logger.info("imei1 error");
                    }
                    try {
                        imei2 = StringUtil.NVL(phoneDataObject.getString("imei2"),"");
                    } catch(JSONException e){
                        logger.info("imei2 JSONException");
                    } catch(Exception e) {
                        logger.info("imei2 error");
                    }
//    				meid = phoneDataObject.getString("meid");
                }

            } else {
                retCode = "0002";
                message = jObject.getString("message");
                retMsg = "eSIM 개통을 위해서는 EID 및 IMEI2값이 필요합니다.<br/>eSIM 지원 기기 여부를 확인 바랍니다.";
            }

        } catch(JSONException e){
            retMsg = e.getMessage();
            retMsg = "이미지 파일을 확인 해 주세요.";
            retCode = "98";
        } catch(Exception e) {
            retMsg = e.getMessage();
            retMsg = "이미지 파일을 확인 해 주세요.";
            retCode = "99";
        }

        hm.put("retCode", retCode);
        hm.put("retMsg", retMsg);
        hm.put("eid", eid);
        hm.put("imei1", imei1);
        hm.put("imei2", imei2);
        hm.put("uploadPhoneSrlNo", uploadPhoneSrlNo);
        return hm;
    }




    /**
     * 설명 : 키 생성 요청
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/m/getOcrEidReadyAjax.do"})
    @ResponseBody
    public Map<String,Object> getOcrEidReadyAjax(HttpServletRequest request, Model model){

        Map<String,Object> hm = new HashMap<String, Object>();

        String OCRserver = "";
        try {
            String sIP = InetAddress.getLocalHost().getHostAddress();

            // dev : 172.27.1.0
            // stg was1 : 172.27.1.9
            // prd was1 : 172.27.0.212

            String[] server = ocrServer1List.split(",");

            boolean ocrServer1on = false;
            for(String callserver : server) {
                if(callserver.equals(sIP)) {
                    ocrServer1on = true;
                    break;
                }
            }

            if(ocrServer1on) {
                OCRserver = OCREidserver1;
            } else {
                OCRserver = OCREidserver2;
            }

        } catch (UnknownHostException e1) {
            logger.error(e1.getMessage());
        }

        JsonReturnDto result = new JsonReturnDto();

        String retCode = "99";
        String retMsg = "오류가 발생했습니다. 다시 시도해 주세요.";

        CloseableHttpClient httpClient = null;
        String responseStrJson = "";
        try {
            // Http client 생성
            httpClient = HttpClientBuilder.create().build();

            // 전송방식 POST
            HttpPost httpPost = new HttpPost();
            httpPost.addHeader("content-type", "application/json");

            URI uri = new URIBuilder(OCRserver.concat("/api/v3/init"))
                    .addParameter("type", "imei")
                    .addParameter("license", OCREidlicense)
                    .build();




            httpPost.setURI(uri);

            // 응답 처리
            HttpResponse httpResponse = httpClient.execute(httpPost);

            // 결과값
            responseStrJson = EntityUtils.toString(httpResponse.getEntity());

        } catch(IOException e) {
            logger.error(e.getMessage());
        } catch(Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }


        int status = 0;
        String token = "";
        String url = "";
        String message = "";

        try {
            JSONObject jObject = new JSONObject(responseStrJson);

            try {
                status = jObject.getInt("status");
            } catch(JSONException e) {
                logger.info("status JSONException");
            }  catch(Exception e) {
                logger.info("status error");
            }

            try {
                token = jObject.getString("token");
            } catch(JSONException e) {
                logger.info("token JSONException");
            }  catch(Exception e) {
                logger.info("token error");
            }

            try {
                url = jObject.getString("url");
            } catch(JSONException e) {
                logger.info("url JSONException");
            } catch(Exception e) {
                logger.info("url error");
            }

            try {
                message = jObject.getString("message");
            } catch(JSONException e) {
                logger.info("url JSONException");
            } catch(Exception e) {
                logger.info("url error");
            }


        } catch(JSONException e) {
            logger.info("JSONException");
        } catch(Exception e) {
            logger.info("error");
        }

        hm.put("status",status);
        hm.put("token",token);
        hm.put("url",url);
        hm.put("message",message);

        return hm;
    }


    /**
     * 설명 : 키를 전단하고 결과값을 요청한다.
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/m/getOcrEidResultAjax.do"})
    @ResponseBody
    public Map<String,Object> getOcrEidResultAjax(HttpServletRequest request, Model model){
        Map<String,Object> hm = new HashMap<String, Object>();

        String OCRserver = "";
        try {
            String sIP = InetAddress.getLocalHost().getHostAddress();

            // dev : 172.27.1.0
            // stg was1 : 172.27.1.9
            // prd was1 : 172.27.0.212

            String[] server = ocrServer1List.split(",");

            boolean ocrServer1on = false;
            for(String callserver : server) {
                if(callserver.equals(sIP)) {
                    ocrServer1on = true;
                    break;
                }
            }

            if(ocrServer1on) {
                OCRserver = OCREidserver1;
            } else {
                OCRserver = OCREidserver2;
            }

        } catch (UnknownHostException e1) {
            logger.error(e1.getMessage());
        }


        String retCode = "99";
        String retMsg = "오류가 발생했습니다. 다시 시도해 주세요.";


        String token = StringUtil.NVL(request.getParameter("token"),"");

        CloseableHttpClient httpClient = null;
        String responseStrJson = "";
        try {
            // Http client 생성
            httpClient = HttpClientBuilder.create().build();

            // 전송방식 GET
            HttpGet httpGet = new HttpGet();
            httpGet.addHeader("content-type", "application/json");

            URI uri = new URIBuilder(OCRserver.concat("/api/v3/result"))
                    .addParameter("token", token)
                    .build();

            httpGet.setURI(uri);

            // 응답 처리
            HttpResponse httpResponse = httpClient.execute(httpGet);

            // 결과값
            responseStrJson = EntityUtils.toString(httpResponse.getEntity());
//    		retCode = "0000";
//    		retMsg = responseStrJson;

        } catch(IOException e){
            logger.error(e.getMessage());
        } catch(Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch(IOException e) {
                logger.error(e.getMessage());
            }  catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        int status = -1;
        String message = "";
        String eid = "";
        String imei1 = "";
        String imei2 = "";
        int uploadPhoneSrlNo = 0;
        // 3.데이터 검증
        try {

            JSONObject jObject = new JSONObject(responseStrJson);
            if(responseStrJson!=null && !"".equals(responseStrJson)) {

                retMsg = "";

                try {
                    status = jObject.getInt("status");
                } catch(JSONException e) {
                    logger.info("status JSONException");
                }  catch(Exception e) {
                    logger.info("status error");
                }

                if(status==0) {
                    retCode = "0000";
                }

                try {
                    message = jObject.getString("message");
                } catch(JSONException e) {
                    logger.info("message error JSONException");
                }  catch(Exception e) {
                    logger.info("message error");
                }

                JSONObject phoneDataObject = null;

                try {
//    				phoneDataObject = (JSONObject) jObject.get("original");
                    phoneDataObject = (JSONObject) jObject.get("modified");
                } catch(JSONException e) {
                    logger.info("result error JSONException");
                }  catch(Exception e) {
                    logger.info("result error");
                }

                if(phoneDataObject !=null){
                    try {
                        eid = StringUtil.NVL(phoneDataObject.getString("eid"),"");
                    } catch(JSONException e) {
                        logger.info("imei1 error JSONException");
                    }  catch(Exception e) {
                        logger.info("eid error");
                    }

                    try {
                        imei1 = StringUtil.NVL(phoneDataObject.getString("imei1"),"");
                    } catch(JSONException e) {
                        logger.info("imei1 error JSONException");
                    }  catch(Exception e) {
                        logger.info("imei1 error");
                    }
                    try {
                        imei2 = StringUtil.NVL(phoneDataObject.getString("imei2"),"");
                    } catch(JSONException e) {
                        logger.info("imei2 error JSONException");
                    }  catch(Exception e) {
                        logger.info("imei2 error");
                    }
//    				meid = phoneDataObject.getString("meid");
                }

            } else {
                retCode = "0002";
                message = jObject.getString("message");
                retMsg = "eSIM 개통을 위해서는 EID 및 IMEI2값이 필요합니다.<br/>eSIM 지원 기기 여부를 확인 바랍니다.";
            }

        } catch(JSONException e) {
            logger.info("result JSONException");
        } catch(Exception e) {
            retMsg = e.getMessage();
            retMsg = "이미지 파일을 확인 해 주세요.";
            retCode = "99";
        }

        hm.put("retCode", retCode);
        hm.put("retMsg", retMsg);
        hm.put("eid", eid);
        hm.put("imei1", imei1);
        hm.put("imei2", imei2);
        hm.put("uploadPhoneSrlNo", uploadPhoneSrlNo);

        return hm;
    }


}
