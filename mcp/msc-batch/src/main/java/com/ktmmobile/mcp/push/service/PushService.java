package com.ktmmobile.mcp.push.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.google.common.util.concurrent.RateLimiter;
import com.ktmmobile.mcp.push.dao.PushSendDao;
import com.ktmmobile.mcp.push.dto.FcmResDto;
import com.ktmmobile.mcp.push.dto.PushSendDataDto;
import com.ktmmobile.mcp.push.dto.PushSndProcTrgtDto;


@Service
public class PushService {

    private static final Logger logger = LoggerFactory.getLogger(PushService.class);

    @Value("${push.interface.server}")
    private String pushprxServer;

    @Value("${fcm.multi.android.server}")
    private String fcmMultiAndroidServer;

    @Value("${fcm.multi.ios.server}")
    private String fcmMultiIosServer;

    @Autowired
    private PushSendDao pushSendDao;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    private static final int TOKEN_SIZE = 200;


    public void newSendPushSchedule() throws ExecutionException, InterruptedException{
        logger.info("appPush start");
        PushSndProcTrgtDto scopeCnt = this.pushSendDao.selectScope(4999);

        if(!ObjectUtils.isEmpty(scopeCnt)){

            logger.info("appPush target scope " + scopeCnt.getMinCnt() + " ~ " + scopeCnt.getMaxCnt());
            List<PushSndProcTrgtDto> pushSndProcTrgtDtos = this.pushSendDao.selectPushTargets(scopeCnt);

            if(!CollectionUtils.isEmpty(pushSndProcTrgtDtos)){

                logger.info("appPush target size " + pushSndProcTrgtDtos.size());
                this.pushSendDao.updatePushStanding(scopeCnt);
                logger.info("appPush update completion");

                for(PushSndProcTrgtDto push : pushSndProcTrgtDtos){
                    push.setSndCnt(StringUtils.isEmpty(push.getSndProcDt()) || push.getSndCnt() >= 5 ? push.getSndCnt() : push.getSndCnt() + 1);
                    push.setProcSt("I".equals(push.getOs()) || "A".equals(push.getOs()) ? push.getProcSt() : "N");
                    push.setProcSt(push.getSndCnt() > 5 ? "X" : push.getProcSt());

                    // 보낼 앱푸시 DTO
                    PushSendDataDto pushSendDataDto = new PushSendDataDto();
                    pushSendDataDto.setPushSeq(String.valueOf(push.getSndCnt()));
                    pushSendDataDto.setPushToken(push.getUdid());
                    pushSendDataDto.setPushTitle(push.getSndTitle());
                    pushSendDataDto.setPushMsg(push.getSndMsg());
                    pushSendDataDto.setPushImage(push.getPushImgUrl());
                    pushSendDataDto.setPushLink(push.getLinkUrl());

                    String pushSendData = null;
                    if ("A".equals(push.getOs())) {
                        pushSendData = makeMessage(pushSendDataDto);
                    }else if("I".equals(push.getOs()) ) {
                         pushSendData = makeIosMessage(pushSendDataDto);
                    }
                    else {
                        push.setProcSt("N");
                    }

                    this.newSendPushFuture(pushSendData, push);
                }
            }
        }
    }

    private void newSendPushFuture(String pushSendData,PushSndProcTrgtDto push) throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(() -> {
            if (StringUtils.isNotEmpty(pushSendData)) {
                String result = null;
                try {
                    if("Y".equals(push.getSendYn())) {
                        RestTemplate restTemplate = new RestTemplate();
                        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
                        result = restTemplate.postForObject(pushprxServer, pushSendData, String.class);
                    }

                } catch (Exception e) {
                    logger.info("Push Service Error " + e.getCause());

                } finally {
                    push.setProcSt("0000".equals(result) ? "Y" : "N");
                    push.setCurrentStat("0000".equals(result) ? "S" : "E");
                    this.pushSendDao.updateSendPushHist(push);
                }
            }
        });
    }

    private String makeMessage(PushSendDataDto pushSendDataDto){

        String sendBody = "{\r\n"
                + "\"message\": {\r\n"
                + "        \"token\": \""
                + pushSendDataDto.getPushToken()
                + "\",\r\n"
                + "    \"data\": {\r\n"
                + "        \"title\": \""
                + pushSendDataDto.getPushTitle()
                + "\",\r\n"
                + "        \"body\": \""
                + pushSendDataDto.getPushMsg()
                + "\",\r\n"
                + "        \"link\": \""
                + pushSendDataDto.getPushLink()
                + "\""
                + "    	\r\n}\r\n"
                + "    }\r\n"
                + "}";

        return sendBody;
    }

    private String makeIosMessage(PushSendDataDto pushSendDataDto){

        String sendBody2 = "{\r\n"
                + "\"message\": {\r\n"
                + "        \"token\": \""
                + pushSendDataDto.getPushToken()
                + "\",\r\n"
                + "    \"notification\": {\r\n"
                + "        \"title\": \""
                + pushSendDataDto.getPushTitle()
                + "\",\r\n"
                + "        \"body\": \""
                + pushSendDataDto.getPushMsg()
                + "\""
                + "    },\r\n"
                + "    \"data\": {\r\n"
                + "        \"link\": \""
                + pushSendDataDto.getPushLink()
                + "\""
                + "    	\r\n}\r\n"
                + "    }\r\n"
                + "}";

        return sendBody2;
    }

    @Async
    public void sendPushAll() {
        try {
            //대량 발송의 경우 CURRENT_STAT 값 M으로 insert 및 select
            int totalCount = this.pushSendDao.selectPushAllCount();

            if(totalCount > 0){
                logger.info("appPushAll start");
                List<PushSndProcTrgtDto> pushSndProcTrgtDtos = this.pushSendDao.selectPushAllTargets();

                if(!CollectionUtils.isEmpty(pushSndProcTrgtDtos)){

                    logger.info("appPushAll target size " + pushSndProcTrgtDtos.size());
                    pushSendDao.updatePushAllStanding();
                    logger.info("appPushAll update completion");

                    //이력 테이블 insert
                    pushSendDao.insertPushSndTrgtHist(pushSndProcTrgtDtos.get(0).getPushSndProcSno());

                    List<String> androidTokens = new ArrayList<String>();
                    List<String> iosTokens = new ArrayList<String>();

                    String title = pushSndProcTrgtDtos.get(0).getSndTitle();
                    String body = pushSndProcTrgtDtos.get(0).getSndMsg();
                    String link = pushSndProcTrgtDtos.get(0).getLinkUrl();
                    String pushSndDd = pushSndProcTrgtDtos.get(0).getPushSndProcSno();

                    for(PushSndProcTrgtDto pushSndProcTrgtDto : pushSndProcTrgtDtos){
                        if("A".equals(pushSndProcTrgtDto.getAppOsTp())) {
                            androidTokens.add(pushSndProcTrgtDto.getUdid());
                        }else if("I".equals(pushSndProcTrgtDto.getAppOsTp())) {
                            iosTokens.add(pushSndProcTrgtDto.getUdid());
                        }
                    }

                    this.sendPushNotifications(androidTokens, iosTokens, title, body, link, pushSndDd);

                }
            }
        } catch (Exception e) {
            logger.error("appPushAll error : " + e.getMessage(), e);
        }
    }

    public void sendPushNotifications(List<String> androidTokens, List<String> iosTokens, String title, String body, String link, String pushSndDd) throws InterruptedException  {
        List<CompletableFuture<FcmResDto>> futures = new ArrayList<>();
        Executor executor = Executors.newFixedThreadPool(4); // 고정된 스레드 풀 생성

        try {
            List<List<String>> androidTokenList = createTokens(androidTokens, TOKEN_SIZE);
            List<List<String>> iosTokenList = createTokens(iosTokens, TOKEN_SIZE);

            //초당 n개의 요청 제한
            RateLimiter rateLimiter = RateLimiter.create(5);

            //안드로이드 발송(sendEach)
            for (List<String> androidSendTokens : androidTokenList) {
                rateLimiter.acquire();
                futures.add(CompletableFuture.supplyAsync(() -> sendAndroidPush(androidSendTokens, title, body, link), executor));
            }

            //IOS 발송(sendEachForMulticast)
            for (List<String> iosSendTokens : iosTokenList) {
                rateLimiter.acquire();
                futures.add(CompletableFuture.supplyAsync(() -> sendIosPush(iosSendTokens, title, body, link), executor));
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            // 성공 및 실패 카운트 집계
            long totalSuccessCount = 0;
            long totalFailureCount = 0;

            for (CompletableFuture<FcmResDto> future : futures) {
                try {
                    FcmResDto result = future.get();
                    totalSuccessCount += result.getSuccessCount();
                    totalFailureCount += result.getFailureCount();
                } catch (ExecutionException e) {
                    logger.error("Error retrieving result from CompletableFuture", e);
                }
            }

            // 어드민 푸시발송 완료 처리 및 성공,실패 카운트 업데이트
            logger.info("appPush complete");
            logger.info("Total Success Count: {}", totalSuccessCount);
            logger.info("Total Failure Count: {}", totalFailureCount);
            PushSndProcTrgtDto pushSndProcTrgtDto = new PushSndProcTrgtDto();
            pushSndProcTrgtDto.setPushSndDd(pushSndDd);
            pushSndProcTrgtDto.setSuccessCount(totalSuccessCount);
            pushSndProcTrgtDto.setFailCount(totalFailureCount);

            pushSendDao.completeSendPushInfo(pushSndProcTrgtDto);
            logger.info("appPush completeSendPushInfo complete");

        } catch (Exception e) {
            logger.error("Error in sending push notifications", e);
        }

    }

    private List<List<String>> createTokens(List<String> tokens, int tokenSize) {
        List<List<String>> tokenList = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i += tokenSize) {
            tokenList.add(tokens.subList(i, Math.min(i + tokenSize, tokens.size())));
        }
        return tokenList;
    }

    private FcmResDto sendAndroidPush(List<String> tokens, String title, String body, String link) {
        PushSndProcTrgtDto pushSndProcTrgtDto = new PushSndProcTrgtDto();
        pushSndProcTrgtDto.setSndTitle(title);
        pushSndProcTrgtDto.setSndMsg(body);
        pushSndProcTrgtDto.setLinkUrl(link);
        pushSndProcTrgtDto.setTokens(tokens);

        try {
            logger.info("Android appPushAll start");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            return restTemplate.postForObject(fcmMultiAndroidServer, pushSndProcTrgtDto, FcmResDto.class);
        } catch (Exception e) {
            logger.error("Android Send Error: {}", e.getMessage(), e);
            return new FcmResDto(0, tokens.size());
        }
    }

    private FcmResDto sendIosPush(List<String> tokens, String title, String body, String link) {
        PushSndProcTrgtDto pushSndProcTrgtDto = new PushSndProcTrgtDto();
        pushSndProcTrgtDto.setSndTitle(title);
        pushSndProcTrgtDto.setSndMsg(body);
        pushSndProcTrgtDto.setLinkUrl(link);
        pushSndProcTrgtDto.setTokens(tokens);

        try {
            logger.info("iOS appPushAll start");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            return restTemplate.postForObject(fcmMultiIosServer, pushSndProcTrgtDto, FcmResDto.class);
        } catch (Exception e) {
            logger.error("iOS Send Error: {}", e.getMessage(), e);
            return new FcmResDto(0, tokens.size());
        }
    }

    public void findSendPushInfo(String pushSndDd) {
        try {
            PushSndProcTrgtDto sendPushInfo = pushSendDao.selectSendPushInfo(pushSndDd);

            if(sendPushInfo != null) {
                //어드민 푸쉬 발송 상태값 업데이트 (등록R -> 대기W)
                int updateResult = pushSendDao.updateSendPushInfo(pushSndDd);
                if(updateResult < 1) {
                    logger.error("updateSendPushInfo error");
                }

                //기존 발송 대상 리스트 삭제
                pushSendDao.deleteSendPushAll();

                //신규 발송 대상 insert
                RestTemplate restTemplate = new RestTemplate();
                int insertResult = restTemplate.postForObject(apiInterfaceServer + "/push/insertSendPushAll", sendPushInfo, Integer.class);
                if(insertResult < 1){
                    logger.error("insertSendPushAll error");
                }
            }

        } catch (Exception e) {
            logger.error("findSendPushInfo error : " + e.getMessage(), e);
        }
    }

}
