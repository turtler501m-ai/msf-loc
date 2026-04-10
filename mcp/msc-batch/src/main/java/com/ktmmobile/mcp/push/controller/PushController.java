package com.ktmmobile.mcp.push.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.push.service.PushService;


@RestController
@EnableScheduling
@EnableAsync
public class PushController {

    private static final Logger logger = LoggerFactory.getLogger(PushController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private PushService pushService;


    //http://localhost:9080/pushsend
    @RequestMapping("/pushsend")
    public String pushsend()throws ExecutionException, InterruptedException {

        sendPushAll();

        return "push send:"+serverName;
    }


    //	08 - 19시 사이에만 실행(단건)
    //@Scheduled(cron = "0 0/10 8-19 * * *")
    public void sendPushNew() throws ExecutionException, InterruptedException {
        this.pushService.newSendPushSchedule();
    }

    //대량 발송
    @Scheduled(cron = "0 0/10 8-19 * * *")
    public void sendPushAll(){
        this.pushService.sendPushAll();
    }

    //어드민 푸시발송 등록 건 대기 처리 및 대상 데이터 insert
    @Scheduled(cron = "0 0/10 8-17 * * *")
    public void findSendPushInfo(){
        LocalDateTime nowDay = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

        String pushSndDd = nowDay.format(formatter);

        this.pushService.findSendPushInfo(pushSndDd);
    }

}
