package com.ktis.mcpif.mPlatform.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.NoSuchElementException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktis.mcpif.mPlatform.event.EventRequest;
import com.ktis.mcpif.mPlatform.event.EventRequestFactory;
import com.ktis.mcpif.mPlatform.service.*;
import com.ktis.mcpif.mPlatform.vo.SelfCareLogVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.ktis.mcpif.mPlatform.vo.MPlatformReqVO;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;


@Controller
public class MplatformController {
	protected Logger logger = LogManager.getLogger(getClass());

	@Resource(name="mPlatformService")
	private MplatformService mPlatformService;

	@Resource(name="mPlatformOsstService")
	private MplatformOsstService mPlatformOsstService;

	@Resource(name="simpleOpenService")
	private SimpleOpenService simpleOpenService;

    @Autowired
    private InternalApiService internalApiService;

	@Autowired
	private EventRequestFactory eventRequestFactory;

	@Autowired
	private MplatformJsonService mplatformJsonService;

	/**
	 * 셀프케어용
	 * @param request
	 * @param model
	 * @param getURL
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 * @throws SOAPException
	 */
	@RequestMapping("/mPlatform/serviceCall.do")
	public @ResponseBody String selfcareController(HttpServletRequest request
												, ModelMap model
												, @RequestParam(required=true) String getURL
			) throws UnsupportedEncodingException, MalformedURLException, SOAPException {
		String decodeURL = URLDecoder.decode(getURL, "UTF-8");

		// 샘플
		// mdlInd=WEB&appEventCd=X25&ncn=541166507&url=/mypage/requestView.do&ip=10.226.84.42&userid=youngtomo
		logger.error("***** selfcare ***** " + decodeURL);
		String resultMessage = mPlatformService.mPlatformServiceWebServiceCall(decodeURL);

		return resultMessage;
	}

	/**
	 * 셀프케어용
	 * @param request
	 * @param model
	 * @param requestJson
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
     */
	@RequestMapping("/mPlatform/serviceCallJson.do")
	public @ResponseBody String serviceCallJson(HttpServletRequest request
			, @RequestBody String requestJson
	) throws IOException {
		EventRequest eventRequest = eventRequestFactory.createFromJson(requestJson);
		String resultMessage = mplatformJsonService.call(eventRequest);

		ObjectMapper mapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
		SelfCareLogVO selfCareLogVO = mapper.readValue(requestJson, SelfCareLogVO.class);
		internalApiService.logSelfCare(selfCareLogVO);
		return resultMessage;
	}

	/**
	 * CP0(직권해지용)
	 * EP0(해지용)
	 * @param request
	 * @param model
	 * @param getURL
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 * @throws SOAPException
	 */
	@RequestMapping("/mPlatform/osstServiceCall.do")
	public @ResponseBody String osstController(HttpServletRequest request
											, ModelMap model
											, @RequestParam(required=true) String getURL
			) throws UnsupportedEncodingException, MalformedURLException, SOAPException {
		String decodeURL = URLDecoder.decode(getURL, "UTF-8");

		logger.error("***** osst ***** " + decodeURL);
		String resultMessage = mPlatformOsstService.mPlatformServiceWebServiceCall(decodeURL);

		return resultMessage;
	}

	/**
	 * 개통간소화용
	 * @param request
	 * @param model
	 * @param getURL
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 * @throws SOAPException
	 */
	@RequestMapping("/mPlatform/simpleOpenServiceCall.do")
	public @ResponseBody String simpleOpenServiceController(HttpServletRequest request
												, HttpServletResponse response
												, ModelMap model
												, @ModelAttribute("MPlatformReqVO") MPlatformReqVO searchVO
			) throws SOAPException {

		String resultMessage = "";

		try{
			if (searchVO.getCstmrName() != null) {
				searchVO.setCstmrName(URLDecoder.decode(searchVO.getCstmrName(), "UTF-8"));
				logger.error("tlphNo=" + searchVO.getCstmrName());
			}

			logger.error("appEventCd=" + searchVO.getAppEventCd());
			logger.error("resNo=" + searchVO.getResNo());
			logger.error("tlphNo=" + searchVO.getTlphNo());

			resultMessage = simpleOpenService.SimpleOpenServiceCall(searchVO);

		}catch(SOAPException e){
			logger.error("simpleOpenServiceController::SOAPException={}, resNo={}", e.getMessage(), searchVO.getResNo());

			// 실패 이력 insert
			simpleOpenService.insertOsstErrLog(searchVO, e);

			throw e; // soap 에러는 던지기

		}catch(MalformedURLException e){
			logger.error("simpleOpenServiceController::MalformedURLException={}, resNo={}", e.getMessage(), searchVO.getResNo());

			// 실패 이력 insert
			simpleOpenService.insertOsstErrLog(searchVO, e);

		}catch(NoSuchElementException e){
			logger.error("simpleOpenServiceController::NoSuchElementException={}, resNo={}", e.getMessage(), searchVO.getResNo());

			// 실패 이력 insert
			simpleOpenService.insertOsstErrLog(searchVO, e);

		}catch(Exception e){
			logger.error("simpleOpenServiceController::Exception={}, resNo={}", e.getMessage(), searchVO.getResNo());

			// 실패 이력 insert
			simpleOpenService.insertOsstErrLog(searchVO, e);

		}

		return resultMessage;
	}

}
