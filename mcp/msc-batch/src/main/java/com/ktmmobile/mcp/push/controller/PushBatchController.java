package com.ktmmobile.mcp.push.controller;

import java.net.http.HttpRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktmmobile.mcp.push.dto.PushBatchVO;
import com.ktmmobile.mcp.push.dto.PushResultDto;
import com.ktmmobile.mcp.push.dto.PushSendDto;
import com.ktmmobile.mcp.push.service.PushBatchService;

@RestController
@EnableScheduling
public class PushBatchController {

	private static final Logger logger = LoggerFactory.getLogger(PushController.class);

	@Value("${SERVER_NAME}")
	private String serverName;

	@Autowired
	private PushBatchService pushBatchService;

	
	@RequestMapping("/pushBatch")
	public String pushBatch() {

		execute();

		return "push send:"+serverName;
	}
	
	/**
	 * push 1시간마다 수행
	 * @param args
	 */
	//1시간 마다 실행 ex) 01:00, 02:00, 03:00....
	//@Scheduled(cron = "0 0/5 * * * *")
	//@Scheduled(cron = "0 0 0/1 * * *")
	public void execute() {
	   String targetDate = this.getYesterday();
       String pushSndResult = null;

        Timestamp batchStDtime = new Timestamp(System.currentTimeMillis());
        PushResultDto pushResultDto = new PushResultDto();
        pushResultDto.setBatchDiv("P01");                     // 배치구분
        pushResultDto.setBatchStDtime(batchStDtime);          // 배치시작일시
        pushResultDto.setBatchNm("P01:푸쉬 발송");        // 배치명

        // 배치 결과 생성
       // insertBatchResult(pushResultDto);

        try {
        	//스케줄조회
            List<PushBatchVO> pushBatchVOList = null;
            PushBatchVO vo = new PushBatchVO();
            vo.setApproveYn("Y");
            vo.setSndProcDiv("0");


            pushBatchVOList = pushBatchService.getPushScheduleList(vo);
            

            String sndCntDiv = null;
            List<String> pushSndProcSnoList = null;
            List<String> pushSndProcSnoList1 = new ArrayList<String>();
            List<String> pushSndProcSnoList2 = new ArrayList<String>();
            PushSendDto dto = new PushSendDto();
            
            // 대상자 생성
            if (pushBatchVOList != null) {

                pushSndProcSnoList = new ArrayList<String>();
                for (PushBatchVO pushBatchVO : pushBatchVOList) {

                    if (pushBatchVO != null) {
                        sndCntDiv = pushBatchVO.getSndCntDiv();
                        if (sndCntDiv != null && "0".equals(sndCntDiv)) { // 반복
                            repeatSend(pushSndProcSnoList, pushBatchVO);
                        }  else { // 1회
                            oneTimeSend(pushSndProcSnoList, pushBatchVO);
                        }
                    }
                }
            }


        }catch (Exception e) {
        	pushResultDto.setProcSt("실패");  // 배치상태
        	pushResultDto.setErrMsg(e.getMessage());   // 에러메시지
            e.printStackTrace();
        }
	}


    /**
     * 반목 발송에 대한 푸쉬 대상자 생성 - 일단위, 주단위, 월단위 분기
     * @param pushBatchVO
     */
    private void repeatSend(List<String> pushSndProcSnoList, PushBatchVO pushBatchVO){

        String sndDtDiv = pushBatchVO.getSndDtDiv();
        String[] weekend = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        String[] selectWeekend = null;
        

        if("D".equals(sndDtDiv)){ // 일단위
            // 일단위 푸쉬 발송 대상자 생성
            findPushTargetUser(pushSndProcSnoList, pushBatchVO);
        }else if("W".equals(sndDtDiv)){ // 주단위
            if(pushBatchVO.getSndDowLst() != null){
                // 주단위 푸쉬 발송 대상자 생성
                selectWeekend = makeSplit(pushBatchVO.getSndDowLst(), "^");
                for(int i=0; i < selectWeekend.length; i++){
                    for(int j=0; j < weekend.length; j++){
                        if(weekend[j].equals(selectWeekend[i]) && pushBatchVO.getSndDow().equals(j+1+"")){
                            findPushTargetUser(pushSndProcSnoList, pushBatchVO);
                        }
                    }
                }
            }
        }else if("M".equals(sndDtDiv)){ // 월단위
            if(pushBatchVO.getSndDays() != null){
                // 월단위 푸쉬 발송 대상자 생성
                findPushTargetUser(pushSndProcSnoList, pushBatchVO);
            }
        }
    }

    /**
     * 푸쉬 발송 대상자 조회
     * @param pushBatchVO
     */
    private void findPushTargetUser(List<String> pushSndProcSnoList, PushBatchVO pushBatchVO){

      //  String aes128Key = appProperties.getProperty("encrypt.aes.key");
       // String aes128Iv = appProperties.getProperty("encrypt.aes.iv");


    	makeVo(pushBatchVO);


        // 푸쉬 발송 처리 결과 생성
        pushBatchService.insertPushSndResult(pushBatchVO);
        

        if("C".equals(pushBatchVO.getSndTargetExtrDiv())) { // 발송 대상자 미 추출
        	pushBatchService.insertPushTargetUserFind(pushBatchVO);
        }else { // 발송 대상자 기 추출
        	pushBatchService.insertPushTargetUserGet(pushBatchVO);
        }

    }

    
    public String getYesterday() {
        Calendar today = Calendar.getInstance();

        today.add(5, -1);

        String smonth = "0" + Integer.toString(today.get(2) + 1);
        smonth = smonth.substring(smonth.length() - 2);

        String sday = "0" + Integer.toString(today.get(5));
        sday = sday.substring(sday.length() - 2);

        String sdate = Integer.toString(today.get(1)) + smonth + sday;

        return sdate;
     }

    
	 /**
     * 푸쉬 대상자 생성을 위한 VO 생성
     * @param pushBatchVO
     * @return
     */
    private void makeVo(PushBatchVO pushBatchVO){

        List<String> list = null;

        pushBatchVO.setSysRegIp("");
        pushBatchVO.setAppInstlYn("Y"); // APP 설치여부
        pushBatchVO.setAppPushAgreeYn("Y"); // APP 푸쉬동의여부
        pushBatchVO.setSysRegId("pushBatchApi"); // 시스템등록아이디
        pushBatchVO.setSysModId("pushBatchApi"); // 시스템수정아이디
        pushBatchVO.setProcSt(""); // 처리상태
        pushBatchVO.setServerColctYn("N"); // 서버수집여부
        pushBatchVO.setAppColctYn("N"); // 앱수집여부
        pushBatchVO.setSndCnt(0); // 발송횟수
        pushBatchVO.setSndTestYn("N"); //발송테스트여부



        // OS 타입
        if(!"O".equals(pushBatchVO.getAppOsTp())) { // OS 타입 전체가 아닌경우
            // 안드로이드
            if("A".equals(pushBatchVO.getAppOsTp()) || "Z".equals(pushBatchVO.getAppOsTp())){ // 안드로이드
                if(pushBatchVO.getAndOsVerLst() != null){ // OS 버전
                    pushBatchVO.setAndOsVerList(osVersionConvert(makeSplit(pushBatchVO.getAndOsVerLst(), "^")));
                    pushBatchVO.setAndOsVerLst2(pushBatchVO.getAndOsVerLst().replaceAll("\\^", "','"));
                }
                if(pushBatchVO.getAndInstlVerLst() != null){ // APP 버전
                    pushBatchVO.setAndInstlVerList(makeSplit(pushBatchVO.getAndInstlVerLst(), "^"));
                    pushBatchVO.setAndInstlVerLst2(pushBatchVO.getAndInstlVerLst().replaceAll("\\^", "','"));
                }

                if(pushBatchVO.getAndOsVerList() != null && pushBatchVO.getAndInstlVerList() != null){
                    list = new ArrayList<String>();
                    int cnt = pushBatchVO.getAndOsVerList().length;
                    int cnt2 = pushBatchVO.getAndInstlVerList().length;
                    for(int i = 0; i < cnt; i++){
                        for(int j = 0; j < cnt2; j++){
                            list.add("('A','"+pushBatchVO.getAndOsVerList()[i]+"','"+pushBatchVO.getAndInstlVerList()[j]+"')");
                        }
                    }
                    pushBatchVO.setAndVerList(list);
                }
            }

            // IOS
            if("I".equals(pushBatchVO.getAppOsTp()) || "Z".equals(pushBatchVO.getAppOsTp())){ // IOS
                if(pushBatchVO.getIosOsVerLst() != null){ // OS 버전
                    pushBatchVO.setIosOsVerList(osVersionConvert(makeSplit(pushBatchVO.getIosOsVerLst(), "^")));
                    pushBatchVO.setIosOsVerLst2(pushBatchVO.getIosOsVerLst().replaceAll("\\^", "','"));
                }
                if(pushBatchVO.getIosInstlVerLst() != null){ // APP 버전
                    pushBatchVO.setIosInstlVerList(makeSplit(pushBatchVO.getIosInstlVerLst(), "^"));
                    pushBatchVO.setIosInstlVerLst2(pushBatchVO.getIosInstlVerLst().replaceAll("\\^", "','"));
                }

                if(pushBatchVO.getIosOsVerList() != null && pushBatchVO.getIosInstlVerList() != null){
                    list = new ArrayList<String>();
                    int cnt = pushBatchVO.getIosOsVerList().length;
                    int cnt2 = pushBatchVO.getIosInstlVerList().length;
                    for(int i = 0; i < cnt; i++){
                        for(int j = 0; j < cnt2; j++){
                            list.add("('I','"+pushBatchVO.getIosOsVerList()[i]+"','"+pushBatchVO.getIosInstlVerList()[j]+"')");
                        }
                    }
                    pushBatchVO.setIosVerList(list);
                }
            }
        }

        // 고객 등급
        if(!"O".equals(pushBatchVO.getCustTp())){ // 고객 등급 전체가 아닌경우
            if(pushBatchVO.getCustDivLst() != null){ // 회원 구분
                pushBatchVO.setCustDivList(makeSplit(pushBatchVO.getCustDivLst(), "^"));
            }
            if(pushBatchVO.getCustGradeLst() != null){ // 정회원 등급 구분
                pushBatchVO.setCustGradeList(makeSplit(pushBatchVO.getCustGradeLst(), "^"));
            }
            if(pushBatchVO.getCustDtlGradeLst() != null){ // 정회원 등급 구분 상세
                pushBatchVO.setCustDtlGradeList(makeSplit(pushBatchVO.getCustDtlGradeLst(), "^"));
            }
        }

        // 고객 소속
        if(!"O".equals(pushBatchVO.getMbrPositTp())){ // 고객 소속 전체가 아닌경우
            if(pushBatchVO.getMbrPositDtlLst() != null){ // 소속
                pushBatchVO.setMbrPositDtlList(makeSplit(pushBatchVO.getMbrPositDtlLst(), "^"));
            }
        }

    }


    /**
     *
     * @param voValue : String("a^b^c,....")
     * @param splitStr : 구분자("^")
     * @return String[]
     */
    private String[] makeSplit(String voValueParam, String splitStr){
    	String voValue = voValueParam;
        String[] values = null;
        if(voValue != null && !"".equals(voValue)) {
            if (voValue.indexOf(splitStr) > -1) {
                voValue = voValue.replace(splitStr , ",");
                values = voValue.split(",");
            }else{
                values = new String[1];
                values[0] = voValue;
            }
        }
        return values;
    }

    /**
     * 마이너 버전(ex] 9.0.1)인경우 정식 버전(ex] 9.0)으로 변환
     * @param strArr String 배열(정수 or 실수)
     * @return String
     */
    private String[] osVersionConvert(String[] strArr) {

        if (strArr != null) {
            for(int i=0; i < strArr.length; i++) {
                if (strArr[i].lastIndexOf(".") > 2) {
                    int index = strArr[i].indexOf(".");
                    strArr[i] = strArr[i].substring(0, strArr[i].indexOf(".", index + 1));
                }

                if (strArr[i].indexOf(".") == -1) {
                    strArr[i] = String.valueOf(Double.parseDouble(strArr[i]));
                }
            }
        }

        return strArr;
    }

    /**
     * 1회 발송에 대한 푸쉬 대상자 생성 - 1회발송 분기
     * @param pushBatchVO
     */
    public void oneTimeSend(List<String> pushSndProcSnoList, PushBatchVO pushBatchVO){

        String sndDtDiv = pushBatchVO.getSndDtDiv();
        String sndDt = pushBatchVO.getSndDt();

        if("S".equals(sndDtDiv) && sndDt != null){ // 1회
            // 1회 푸쉬 발송 대상자 생성
            findPushTargetUser(pushSndProcSnoList, pushBatchVO);
        }
    }
}
