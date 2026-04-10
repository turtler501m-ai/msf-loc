package com.ktmmobile.mcp.common.cti;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import com.ktmmobile.mcp.cs.dto.BookingUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;

import com.ktmmobile.mcp.common.util.StringUtil;

@Service
public class CtiService {
    private static Logger logger = LoggerFactory.getLogger(CtiService.class);

    @Autowired
    private CtiServerAdapter adapter;

    @Value("${SERVER_NAME}")
    private String serverLocation;

    /**
     * 1. 1대1문의 하기
     * @param String qnaSeq : NMCP_QNA_BOARD_BAS.QNA_SEQ
     * @param String qnaContent : 문의내역
     * @param String serviceCode : 'INF_APP_004' 고정
     * @param String qnaNm : 작성자명
     * @param String qnaWriterId : 작성자ID
     * @param String mobileNo : 핸드폰 번호
     * @param String qnaSubject : 제목
     * @param String qnaSmsSendYn : SMS 수신여부
     * @param String qnaCtg : QNA  유형 [FAB06,FAB01,FAB02,FAB03,FAB04,FAB05]
     * @return
     * @exception
     */

    public boolean ctiCsInquiry(int qnaSeq, String qnaContent, String serviceCode, String qnaNm, String qnaWriterId, String mobileNo
    		                                       ,String qnaSubject, String qnaSmsSendYn, String qnaCtg,String userDivision){

    	HashMap<String, String> param = new HashMap<String, String>();
    	param.put("qnaSeq", Integer.toString(qnaSeq));
    	param.put("qnaContent", StringUtil.NVL(qnaContent,""));
    	param.put("serviceCode", StringUtil.NVL(serviceCode,""));
    	param.put("qnaNm", StringUtil.NVL(qnaNm,""));
    	param.put("qnaWriterId", StringUtil.NVL(qnaWriterId,""));
    	param.put("mobileNo", StringUtil.NVL(mobileNo,""));
    	param.put("qnaSubject", StringUtil.NVL(qnaSubject,""));
    	param.put("qnaSmsSendYn", StringUtil.NVL(qnaSmsSendYn,""));
    	param.put("qnaCtg", StringUtil.NVL(qnaCtg,""));
    	param.put("userDivision", StringUtil.NVL(userDivision,""));

    	boolean result = true;
    	try {

    		result = adapter.callService(param);

        	if(!result){
        		throw new McpCommonException(COMMON_EXCEPTION);
        	}
      }  catch (Exception e) {
            result = false;
        }

    	return result;

    }

    /**
     * 2. 간편가입신청
     * @param String qnaSeq : '0' - CTI 미사용
     * @param String qnaContent : 구매 예정 지역 / 연령대 / 성별
     * @param String serviceCode : 'INF_APP_004'
     * @param String qnaNm : 작성자명
     * @param String qnaWriterId : ''
     * @param String mobileNo : 연락 받을 전화번호
     * @param String qnaSubject : '전화상담'
     * @param String qnaSmsSendYn : SMS 수신여부 'N'
     * @param String qnaCtg : 신청상품  유형 [GAA01,GAA02]
     * @return
     * @exception
     */

    public int ctiTelCounsel(int qnaSeq, String qnaContent, String serviceCode
    		                                           , String qnaNm, String qnaWriterId, String mobileNo
    		                                           , String qnaSubject, String qnaSmsSendYn, String qnaCtg,String userDivision) throws SelfServiceException,SocketTimeoutException{
    	// ,String juridicalNumber,String agentNm

    	HashMap<String, String> param = new HashMap<String, String>();
    	param.put("qnaSeq", Integer.toString(qnaSeq));
    	param.put("qnaContent", StringUtil.NVL(qnaContent,""));
    	param.put("serviceCode", StringUtil.NVL(serviceCode,""));
    	param.put("qnaNm", StringUtil.NVL(qnaNm,""));
    	param.put("qnaWriterId", StringUtil.NVL(qnaWriterId,""));
    	param.put("mobileNo", StringUtil.NVL(mobileNo,""));
    	param.put("qnaSubject", StringUtil.NVL(qnaSubject,""));
    	param.put("qnaSmsSendYn", StringUtil.NVL(qnaSmsSendYn,""));
    	param.put("qnaCtg", StringUtil.NVL(qnaCtg,""));
    	param.put("userDivision", StringUtil.NVL(userDivision,""));
    	//param.put("juridicalNumber", StringUtil.NVL(juridicalNumber,""));
    	//param.put("agentNm", StringUtil.NVL(agentNm,""));

    	

    	boolean result = true;
    	result = adapter.callService(param);

    	int rtn = 0;
    	if(result){
    		rtn = 1;
    	}else{
    		throw new McpCommonException(COMMON_EXCEPTION);
    	}
    	return rtn;
    }


	/**
	 * 3. 예약상담 문의하기
	 * @param String qnaSeq : SQ_CS_RES_CSTMR.NEXTVAL
	 * @param String qnaContent : 문의 내용
	 * @param String serviceCode : 'INF_APP_004'
	 * @param String qnaNm : 작성자명
	 * @param String qnaWriterId : 작성자 아이디
	 * @param String mobileNo : 연락 받을 전화번호
	 * @param String qnaSubject : 문의 제목
	 * @param String qnaSmsSendYn : SMS 수신여부 'N'
	 * @param String userDivision : userDivision
	 * @param String csResDt : 예약날짜
	 * @param String csResTm : 예약시간
	 * @param String regstDt : 작성날짜
	 * @param String csResSource : APP/WEB
	 * @param String csResYn : "Y"
	 * @return
	 * @exception
	 */
	public boolean ctiCsBooking(BookingUserDto bookingUserDto) {

		HashMap<String, String> param = new HashMap<String, String>();

		param.put("qnaSeq", StringUtil.NVL(bookingUserDto.getCsResSeq(),""));
		param.put("qnaContent", StringUtil.NVL(bookingUserDto.getCsContent(),""));
		param.put("serviceCode", "INF_APP_004");
		param.put("qnaNm",  StringUtil.NVL(bookingUserDto.getUsrNm(),""));
		param.put("qnaWriterId", StringUtil.NVL(bookingUserDto.getUsrId(),""));
		param.put("mobileNo", StringUtil.NVL(bookingUserDto.getUnSvcNo(),""));
		param.put("qnaSubject", StringUtil.NVL(bookingUserDto.getCsSubject(),""));
		param.put("qnaSmsSendYn","N");
		param.put("qnaCtg", StringUtil.NVL(bookingUserDto.getVocDtl(),""));
		param.put("userDivision", bookingUserDto.getUserDivision());
		param.put("csResDt", StringUtil.NVL(bookingUserDto.getCsResDt(),""));
		param.put("csResTm", StringUtil.NVL(bookingUserDto.getCsResTm(),""));
		param.put("regstDt", StringUtil.NVL(bookingUserDto.getRegstDt(),""));
		param.put("csResSource", StringUtil.NVL(bookingUserDto.getVocSec(),""));
		param.put("csResYn", "Y");

		boolean result = true;
		try {

			result = adapter.callService(param);

			if(!result){
				throw new McpCommonException(COMMON_EXCEPTION);
			}
		} catch (SocketTimeoutException e) {
			result = false;
		} catch (Exception e) {
			result = false;
		}

		return result;


	}
}