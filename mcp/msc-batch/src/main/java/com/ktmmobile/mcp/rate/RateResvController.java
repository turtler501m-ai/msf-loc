//package com.ktmmobile.mcp.rate;
//
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import com.ktmmobile.mcp.common.util.DateTimeUtil;
//import com.ktmmobile.mcp.log.dto.BathHistDto;
//import com.ktmmobile.mcp.log.service.BathHistService;
//import com.ktmmobile.mcp.rate.dto.McpIpStatisticDto;
//import com.ktmmobile.mcp.temp.service.TempSvc;
//
//@RestController
//@EnableScheduling
//public class RateResvController {
//
//    private static final Logger logger = LoggerFactory.getLogger(RateResvController.class);
//
//    @Value("${SERVER_NAME}")
//    private String serverName;
//
//    @Value("${rate.url}")
//    private String rateUrl;
//
//    @Value("${mcp.was.server}")
//    private String mcpWasServer;
//
//    @Autowired
//    private TempSvc tempSvc;
//
//    @Autowired
//    private BathHistService bathHistService;
//
//    @RequestMapping("/rateResv")
//    public String rateResv() {
//
//        rateResvBatch();
//
//        return "rateResv send:"+serverName;
//    }
//
//    /**
//     * 요금제 예약변경 후속 처리
//     * @Scheduled
//     *  @Scheduled(cron="0 0 02 2,20 * ?") //매월 2일,20일 새벽2시에 실행
//     *  0 0 05 1 * *
//     */
//    @Scheduled(cron="0 0 05 1 * *") // 매월 1일 // 초 분 시 일 월 주/년
//    @Async
//    public void rateResvBatch() {
//
//
//        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
//        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
//        int batchExeCascnt = 1;
//        String msgDtl = "";
//        int failCascnt = 0;
//        int successCascnt = 0;
//        String batchCd = "";
//        String exeMthd = "";
//
//        try {
//            String[] mcpServer = mcpWasServer.split(",");
//            if (mcpServer.length < 4) {
//                return ;
//            } else {
//                RestTemplate restTemplate = new RestTemplate();
//                String reqUrl = "http://"+mcpServer[0]+"/batch/resChangeAddPrdAjax.do?modPara=0";
//                String resultStr = restTemplate.postForObject(reqUrl, null, String.class);
//                successCascnt += Integer.parseInt(resultStr, 10);
//
//            }
//        } catch(Exception e) {
//            msgDtl = e.getMessage();
//            failCascnt++;
//        } finally {
//            try {
//                batchCd = "0500";
//                exeMthd = "rateResvBatch0";
//                BathHistDto bathHistDto = new BathHistDto();
//                bathHistDto.setBatchCd(batchCd);
//                bathHistDto.setExeMthd(exeMthd);
//                bathHistDto.setBatchExeDate(batchExeDate);
//                bathHistDto.setBatchExeStDate(batchExeStDate);
//                bathHistDto.setBatchExeCascnt(batchExeCascnt);
//                bathHistDto.setSuccessCascnt(successCascnt);
//                bathHistDto.setFailCascnt(failCascnt);
//                bathHistDto.setMsgDtl(msgDtl);
//                bathHistService.insertHist(bathHistDto);
//            }catch(Exception e) {
//                logger.debug("rateResvBatch error");
//            }
//        }
//    }
//
//
//    /**
//     * 요금제 예약변경 후속 처리
//     * @Scheduled
//     *  @Scheduled(cron="0 0 02 2,20 * ?") //매월 2일,20일 새벽2시에 실행
//     *  0 0 05 1 * *
//     */
//    @Scheduled(cron="0 1 05 1 * *") // 매월 1일 // 초 분 시 일 월 주/년
//    @Async
//    public void rateResvBatch01() {
//
//
//        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
//        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
//        int batchExeCascnt = 1;
//        String msgDtl = "";
//        int failCascnt = 0;
//        int successCascnt = 0;
//        String batchCd = "";
//        String exeMthd = "";
//
//        try {
//            String[] mcpServer = mcpWasServer.split(",");
//            if (mcpServer.length < 4) {
//                return ;
//            } else {
//
//                 RestTemplate restTemplate = new RestTemplate();
//                 String reqUrl = "http://"+mcpServer[1]+"/batch/resChangeAddPrdAjax.do?modPara=1";
//                 String resultStr = restTemplate.postForObject(reqUrl, null, String.class);
//                 successCascnt += Integer.parseInt(resultStr, 10);
//            }
//        } catch(Exception e) {
//            msgDtl = e.getMessage();
//            failCascnt++;
//        } finally {
//            try {
//                batchCd = "0501";
//                exeMthd = "rateResvBatch";
//                BathHistDto bathHistDto = new BathHistDto();
//                bathHistDto.setBatchCd(batchCd);
//                bathHistDto.setExeMthd(exeMthd);
//                bathHistDto.setBatchExeDate(batchExeDate);
//                bathHistDto.setBatchExeStDate(batchExeStDate);
//                bathHistDto.setBatchExeCascnt(batchExeCascnt);
//                bathHistDto.setSuccessCascnt(successCascnt);
//                bathHistDto.setFailCascnt(failCascnt);
//                bathHistDto.setMsgDtl(msgDtl);
//                bathHistService.insertHist(bathHistDto);
//            }catch(Exception e) {
//                logger.debug("rateResvBatch error");
//            }
//        }
//    }
//
//
//    /**
//     * 요금제 예약변경 후속 처리
//     * @Scheduled
//     *  @Scheduled(cron="0 0 02 2,20 * ?") //매월 2일,20일 새벽2시에 실행
//     *  0 0 05 1 * *
//     */
//    @Scheduled(cron="0 2 05 1 * *") // 매월 1일 // 초 분 시 일 월 주/년
//    @Async
//    public void rateResvBatch02() {
//
//
//        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
//        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
//        int batchExeCascnt = 1;
//        String msgDtl = "";
//        int failCascnt = 0;
//        int successCascnt = 0;
//        String batchCd = "";
//        String exeMthd = "";
//
//        try {
//            String[] mcpServer = mcpWasServer.split(",");
//
//            if (mcpServer.length < 4) {
//                return ;
//            } else {
//
//                 RestTemplate restTemplate = new RestTemplate();
//                 String reqUrl = "http://"+mcpServer[2]+"/batch/resChangeAddPrdAjax.do?modPara=2";
//                 String resultStr = restTemplate.postForObject(reqUrl, null, String.class);
//                 successCascnt += Integer.parseInt(resultStr, 10);
//            }
//        } catch(Exception e) {
//            msgDtl = e.getMessage();
//            failCascnt++;
//        } finally {
//            try {
//                batchCd = "0502";
//                exeMthd = "rateResvBatch";
//                BathHistDto bathHistDto = new BathHistDto();
//                bathHistDto.setBatchCd(batchCd);
//                bathHistDto.setExeMthd(exeMthd);
//                bathHistDto.setBatchExeDate(batchExeDate);
//                bathHistDto.setBatchExeStDate(batchExeStDate);
//                bathHistDto.setBatchExeCascnt(batchExeCascnt);
//                bathHistDto.setSuccessCascnt(successCascnt);
//                bathHistDto.setFailCascnt(failCascnt);
//                bathHistDto.setMsgDtl(msgDtl);
//                bathHistService.insertHist(bathHistDto);
//            }catch(Exception e) {
//                logger.debug("rateResvBatch error");
//            }
//        }
//    }
//
//    /**
//     * 요금제 예약변경 후속 처리
//     * @Scheduled
//     *  @Scheduled(cron="0 0 02 2,20 * ?") //매월 2일,20일 새벽2시에 실행
//     *  0 0 05 1 * *
//     */
//    @Scheduled(cron="0 3 05 1 * *") // 매월 1일 // 초 분 시 일 월 주/년
//    @Async
//    public void rateResvBatch03() {
//
//
//        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
//        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
//        int batchExeCascnt = 1;
//        String msgDtl = "";
//        int failCascnt = 0;
//        int successCascnt = 0;
//        String batchCd = "";
//        String exeMthd = "";
//
//        try {
//            String[] mcpServer = mcpWasServer.split(",");
//
//
//
//            if (mcpServer.length < 4) {
//                return ;
//            } else {
//
//                 RestTemplate restTemplate = new RestTemplate();
//                 String reqUrl = "http://"+mcpServer[3]+"/batch/resChangeAddPrdAjax.do?modPara=3";
//                 String resultStr = restTemplate.postForObject(reqUrl, null, String.class);
//                 successCascnt += Integer.parseInt(resultStr, 10);
//            }
//        } catch(Exception e) {
//            msgDtl = e.getMessage();
//            failCascnt++;
//        } finally {
//            try {
//                batchCd = "0503";
//                exeMthd = "rateResvBatch";
//                BathHistDto bathHistDto = new BathHistDto();
//                bathHistDto.setBatchCd(batchCd);
//                bathHistDto.setExeMthd(exeMthd);
//                bathHistDto.setBatchExeDate(batchExeDate);
//                bathHistDto.setBatchExeStDate(batchExeStDate);
//                bathHistDto.setBatchExeCascnt(batchExeCascnt);
//                bathHistDto.setSuccessCascnt(successCascnt);
//                bathHistDto.setFailCascnt(failCascnt);
//                bathHistDto.setMsgDtl(msgDtl);
//                bathHistService.insertHist(bathHistDto);
//            }catch(Exception e) {
//                logger.debug("rateResvBatch error");
//            }
//        }
//    }
//
//
//    /**
//     * 요금제 예약변경 후속 처리
//     * @Scheduled
//     *  @Scheduled(cron="0 0 02 2,20 * ?") //매월 2일,20일 새벽2시에 실행
//     *  @Scheduled(cron="0 0 07 1 * *") // 매월 1일 // 초 분 시 일 월 주/년
//     * @Async
//     */
//    @Scheduled(cron="0 0 07 1 * *") // 매월 1일 // 초 분 시 일 월 주/년
//    public void rateResvBatchLoopCheck() {
//
//
//
//        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
//        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
//        int batchExeCascnt = 1;
//        String msgDtl = "";
//        int failCascnt = 0;
//        int successCascnt = 0;
//        String batchCd = "";
//        String exeMthd = "";
//
//
//        McpIpStatisticDto ipStatistic = new McpIpStatisticDto();
//        ipStatistic.setResChgApyDate(batchExeDate);  //실행 일자
//        //"yyyyMMdd"
//
//
//        //1.예약정보 조회
//        List<McpIpStatisticDto> changeAddPrdList = tempSvc.getRateResChgList(ipStatistic);
//
//        try {
//
//            String[] mcpServer = mcpWasServer.split(",");
//
//            for (McpIpStatisticDto ipStatisticItem : changeAddPrdList) {
//                int rateResChgSeq = Integer.parseInt(ipStatisticItem.getRateResChgSeq(), 10);
//                int modInt = rateResChgSeq % 4 ;
//
//                RestTemplate restTemplate = new RestTemplate();
//                String reqUrl = "http://"+mcpServer[modInt]+"/batch/resChangeAddPrdAjax.do?rateResChgSeq="+rateResChgSeq;
//                String resultStr =  restTemplate.postForObject(reqUrl, null, String.class);
//                successCascnt += Integer.parseInt(resultStr, 10);
//            }
//
//        } catch(Exception e) {
//            msgDtl = e.getMessage();
//            failCascnt++;
//        } finally {
//            try {
//                batchCd = "0504";
//                exeMthd = "rateResvBatch";
//                BathHistDto bathHistDto = new BathHistDto();
//                bathHistDto.setBatchCd(batchCd);
//                bathHistDto.setExeMthd(exeMthd);
//                bathHistDto.setBatchExeDate(batchExeDate);
//                bathHistDto.setBatchExeStDate(batchExeStDate);
//                bathHistDto.setBatchExeCascnt(batchExeCascnt);
//                bathHistDto.setSuccessCascnt(successCascnt);
//                bathHistDto.setFailCascnt(failCascnt);
//                bathHistDto.setMsgDtl(msgDtl);
//                bathHistService.insertHist(bathHistDto);
//            }catch(Exception e) {
//                logger.debug("rateResvBatch error");
//            }
//        }
//    }
//
//
//
//    /**
//     * 요금제 예약변경 후속 처리
//     * 99999 다시 한번더
//     */
//    @Scheduled(cron = "0 0 8 1 * *") // 매월 1일 // 초 분 시 일 월 주/년
//    public void rateResvBatchCheck() {
//
//        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
//        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
//        int batchExeCascnt = 1;
//        String msgDtl = "";
//        int failCascnt = 0;
//        int successCascnt = 0;
//        String batchCd = "";
//        String exeMthd = "";
//
//        try {
//
//            String[] mcpServer = mcpWasServer.split(",");
//
//            if (mcpServer.length < 4) {
//                return ;
//            } else {
//
//                RestTemplate restTemplate = new RestTemplate();
//                String reqUrl = "http://"+mcpServer[0]+"/batch/resChangeAddPrdAjax.do?batchRsltCd=99999";  //batchRsltCd
//                String resultStr =  restTemplate.postForObject(reqUrl, null, String.class);
//
//                successCascnt =+ Integer.parseInt(resultStr, 10);
//            }
//
//
//        } catch(Exception e) {
//            msgDtl = e.getMessage();
//            failCascnt++;
//        } finally {
//            try {
//                batchCd = "0505";
//                exeMthd = "rateResvBatchCheck";
//                BathHistDto bathHistDto = new BathHistDto();
//                bathHistDto.setBatchCd(batchCd);
//                bathHistDto.setExeMthd(exeMthd);
//                bathHistDto.setBatchExeDate(batchExeDate);
//                bathHistDto.setBatchExeStDate(batchExeStDate);
//                bathHistDto.setBatchExeCascnt(batchExeCascnt);
//                bathHistDto.setSuccessCascnt(successCascnt);
//                bathHistDto.setFailCascnt(failCascnt);
//                bathHistDto.setMsgDtl(msgDtl);
//                bathHistService.insertHist(bathHistDto);
//            }catch(Exception e) {
//                logger.debug("rateResvBatchCheck error");
//            }
//        }
//    }
//
//}
