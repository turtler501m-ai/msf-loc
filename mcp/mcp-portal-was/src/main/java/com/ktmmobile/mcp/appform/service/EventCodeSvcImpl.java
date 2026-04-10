package com.ktmmobile.mcp.appform.service;

import com.ktmmobile.mcp.common.util.DateTimeUtil;

import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.common.util.XmlBindUtil;
import com.ktmmobile.mcp.rate.dto.*;
import com.ktmmobile.mcp.rate.service.RateAdsvcGdncService;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ktmmobile.mcp.etc.dto.GiftPromotionBas;
import java.io.File;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.rate.RateAdsvcGdncUtil.getRateAdsvcGdncPath;

@Service
public class EventCodeSvcImpl implements EventCodeSvc {

    private static final Logger logger = LoggerFactory.getLogger(EventCodeSvcImpl.class);

    private static final String EVENT_CODE_PRMT_LIST = "eventCodePrmtList";

    @Autowired
    private RateAdsvcGdncService rateAdsvcGdncService;

    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    @Value("${SERVER_NAME}")
    private String serverName;

    /** 특정 요금제에 해당하는 이벤트 코드 리스트 조회 */
    @Override
    public List<String> getEvntCdList(String rateCd) {

        List<String> rtnList = new ArrayList<>();
        //List<String> eventCodeList = new ArrayList<>();
        List<String> eventSeqList = new ArrayList<>();

        // 요금제부가서비스안내상품관계 xml 조회
        List<RateAdsvcGdncProdRelXML> prodRelXmlList = rateAdsvcGdncService.getRateAdsvcGdncProdRelXml();
        if(prodRelXmlList == null){
            return rtnList;
        }

        for(RateAdsvcGdncProdRelXML item : prodRelXmlList) {
            if(rateCd.equals(item.getRateAdsvcCd())) {
                // 이벤트 코드 조회
                if(!StringUtil.isEmpty(item.getEcpSeq())){
                    String[] evntCdArr = item.getEcpSeq().split("\\|");
                    eventSeqList = Arrays.asList(evntCdArr);
                }
                break;
            }
        }

        if(eventSeqList.isEmpty()){
            return rtnList;
        }

        // 각 이벤트 코드 기간 조회
        List<EventCodePrmtXML> eventCodePrmtXML = getEventCodePrmtXML(EVENT_CODE_PRMT_LIST);
        if(eventCodePrmtXML == null || eventCodePrmtXML.isEmpty()){
            return rtnList;
        }

        rtnList =   eventSeqList.stream().map (ecpSeq ->
                        eventCodePrmtXML.stream().filter(xml -> ecpSeq.equals(xml.getEcpSeq()))
                                        .findFirst()
                                        .map(EventCodePrmtXML::getEvntCd)
                                        .orElse(null)
                        ).filter(Objects::nonNull).collect(Collectors.toList());

        logger.info("getEvntCdList.rtnList:[]", ObjectUtils.convertObjectToString(rtnList));
        return rtnList;
    }

    /** 설명 : 이벤트 코드 xml 사용가능 여부 판단 */
    private boolean isAvailableEventCodePrmt(List<EventCodePrmtXML> eventCodePrmtXML, String evntCd) {

        EventCodePrmtXML eventCodePrmtDto = eventCodePrmtXML.stream()
            .filter(item -> evntCd.equals(item.getEvntCd()))
            .filter(item -> this.middleTimeChkWithoutEx(item.getPstngStartDate(), item.getPstngEndDate()))
            .findFirst().orElse(null);

        return eventCodePrmtDto != null;
    }

    /** 설명 : 이벤트 코드 xml 조회 */
    @Override
    public List<EventCodePrmtXML> getEventCodePrmtXML(String key) {

        List<EventCodePrmtXML> eventCodePrmtXmlList = null;

        try {

            String fPer = File.separator;
            String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
            String realFilePath = realDir + fPer + "NMCP_RATE_EVENT_CODE_PRMT.xml";

            File file = new File(realFilePath);
            if(file.exists()){
                EventCodeMapWrapper mapWrapper = XmlBindUtil.bindFromXml(EventCodeMapWrapper.class, file);

                Map<String, EventCodeListXmlWrapper> xmlMap = mapWrapper.getItem();
                if (xmlMap == null) return null ;
                EventCodeListXmlWrapper eventCodeListXmlWrapper = xmlMap.get(key);
                if(eventCodeListXmlWrapper != null && eventCodeListXmlWrapper.getItem() != null){
                    eventCodePrmtXmlList = eventCodeListXmlWrapper.getItem().stream()
                                    .filter(item -> middleTimeChkWithoutEx(item.getPstngStartDate(), item.getPstngEndDate()))
                                    .collect(Collectors.toList());
                }
            }
        } catch(JAXBException e) {
            logger.error("getEventCodePrmtMapWrapper JAXBException={}", e.toString());
        } catch(Exception e) {
            logger.error("getEventCodePrmtMapWrapper Exception={}", e.toString());
        }

        return eventCodePrmtXmlList;
    }

    /** 설명 : 이벤트 코드 추천인ID 표출여부 xml 조회 */
    @Override
    public String getEventCodeRecoUseYnXML(String evntCd) {
        String recoUseYn = null;
        try {
            List<EventCodePrmtXML> eventCodePrmtXML = getEventCodePrmtXML(EVENT_CODE_PRMT_LIST);
            for(EventCodePrmtXML item : eventCodePrmtXML) {
                if(evntCd.equals(item.getEcpSeq())) {
                    if(!StringUtil.isEmpty(item.getRecoUseYn())){
                        recoUseYn = item.getRecoUseYn();
                    }
                    break;
                }
            }
        } catch(Exception e) {
            logger.error("getEventCodePrmtMapWrapper Exception={}", e.toString());
        }

        return recoUseYn;
    }

    private boolean middleTimeChkWithoutEx(String start, String end) {

        if(StringUtil.isEmpty(start) || StringUtil.isEmpty(end)){
            return false;
        }

        boolean result = false;

        try{
          result = DateTimeUtil.isMiddleDateTime(start, end);
        }catch(DateTimeParseException | ParseException e){
            logger.error("middleTimeChkWithoutEx ParseException={}", e.getMessage());
            result = false;
        }

        return result;
    }

    @Override
    public GiftPromotionBas getEventVal(String rateCd , String evntCd) {

        /**
         * rateCd 유효여부 확인
         * EcpSeq 추출
         * */
        List<RateAdsvcGdncProdRelXML> prodRelXmlList = rateAdsvcGdncService.getRateAdsvcGdncProdRelXml();
        if(prodRelXmlList == null){
            return null;
        }

        String ecpSeqRaw = null;
        for (RateAdsvcGdncProdRelXML item : prodRelXmlList) {
            if (Objects.equals(rateCd, item.getRateAdsvcCd())) {
                ecpSeqRaw = item.getEcpSeq();
                break;
            }
        }

        if (StringUtil.isEmpty(ecpSeqRaw)) {
            return null;
        }

        Set<String> eventSeqSet = Arrays.stream(ecpSeqRaw.split("\\|"))
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toSet());

        if (eventSeqSet.isEmpty()) {
            return null;
        }


        List<EventCodePrmtXML> eventCodePrmtList = getEventCodePrmtXML(EVENT_CODE_PRMT_LIST);
        if(eventCodePrmtList == null || eventCodePrmtList.isEmpty()){
            return null;
        }

        // 5) 조건 만족하는 첫 번째 찾기
        EventCodePrmtXML eventCodePrmt = eventCodePrmtList.stream()
                .filter(item -> eventSeqSet.contains(item.getEcpSeq()))
                .filter(item -> Objects.equals(evntCd, item.getEvntCd()))
                .findFirst()
                .orElse(null);

        if (eventCodePrmt == null) {
            return null;
        }

        GiftPromotionBas giftPromotionBas = new GiftPromotionBas();
        giftPromotionBas.setExposedText(eventCodePrmt.getExposedText());
        giftPromotionBas.setEcpSeq(eventCodePrmt.getEcpSeq());
        return giftPromotionBas;

    }

    @Override
    public GiftPromotionBas getEventchk(String evntCdPrmt) {

        List<EventCodePrmtXML> eventCodePrmtXML = getEventCodePrmtXML(EVENT_CODE_PRMT_LIST);

        for(EventCodePrmtXML item : eventCodePrmtXML) {
            if(evntCdPrmt.equals(item.getEcpSeq())) {
                    GiftPromotionBas giftPromotionBas = new GiftPromotionBas();
                    giftPromotionBas.setEcpSeq(item.getEcpSeq());
                    return giftPromotionBas;
            }
        }
        return null;
    }
}
