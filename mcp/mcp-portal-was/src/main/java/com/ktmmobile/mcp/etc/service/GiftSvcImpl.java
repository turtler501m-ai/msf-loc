package com.ktmmobile.mcp.etc.service;

import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ktmmobile.mcp.appform.dto.AppformReqDto;
import com.ktmmobile.mcp.etc.dao.GiftDao;
import com.ktmmobile.mcp.rate.dto.RateGiftPrmtListDTO;
import com.ktmmobile.mcp.rate.dto.RateGiftPrmtXML;
import com.ktmmobile.mcp.rate.service.RateAdsvcGdncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.ktmmobile.mcp.etc.dto.ConsentGiftDto;
import com.ktmmobile.mcp.etc.dto.GiftPromotionBas;
import com.ktmmobile.mcp.etc.dto.GiftPromotionDto;

@Service
public class GiftSvcImpl implements GiftSvc {

    private static Logger logger = LoggerFactory.getLogger(GiftSvcImpl.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

      @Autowired
       private GiftDao giftDao;

      @Autowired
      private RateAdsvcGdncService rateAdsvcGdncService;

    @Override
    public List<GiftPromotionBas> giftBasList(GiftPromotionBas giftPromotionBas) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        GiftPromotionBas[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/appForm/getPrmtBasList", giftPromotionBas, GiftPromotionBas[].class);
        List<GiftPromotionBas> result  = Arrays.asList(rtnList);
        //---- API 호출 E ----//
        return result;
    }


    @Override
    public List<GiftPromotionBas> giftBasList(GiftPromotionBas giftPromotionBas ,String defaultOrgnId) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        GiftPromotionBas[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/appForm/getPrmtBasList", giftPromotionBas, GiftPromotionBas[].class);

        //기본 접점이 아닌면 한번 더 조회
        if ((rtnList == null || rtnList.length == 0) && !defaultOrgnId.equals(giftPromotionBas.getOrgnId())) {
            giftPromotionBas.setOrgnId(defaultOrgnId);
            rtnList = restTemplate.postForObject(apiInterfaceServer + "/appForm/getPrmtBasList", giftPromotionBas, GiftPromotionBas[].class);
        }
        List<GiftPromotionBas> result  = Arrays.asList(rtnList);
        //---- API 호출 E ----//
        return result;
    }

    @Override
    public String[] getPrmtId(GiftPromotionDto giftPromotionDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        String[] result = restTemplate.postForObject(apiInterfaceServer + "/appForm/getPrmtId", giftPromotionDto, String[].class);
        //---- API 호출 E ----//
        return result;
    }

    @Override
    public List<GiftPromotionDto> getGiftArrList(String[] prmtIdArr) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        GiftPromotionDto[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/appForm/getGiftArrList", prmtIdArr, GiftPromotionDto[].class);
        List<GiftPromotionDto> result  = Arrays.asList(rtnList);
        //---- API 호출 E ----//
        return result;

    }

    @Override
    public GiftPromotionDto getPrmtChk(GiftPromotionDto giftPromotionDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        GiftPromotionDto result = restTemplate.postForObject(apiInterfaceServer + "/appForm/getPrmtChk", giftPromotionDto, GiftPromotionDto.class);
        //---- API 호출 E ----//
        return result;
    }

    @Override
    public List<GiftPromotionDto> getGiftList(GiftPromotionDto giftPromotionDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        GiftPromotionDto[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/appForm/getGiftList", giftPromotionDto, GiftPromotionDto[].class);
        List<GiftPromotionDto> result  = Arrays.asList(rtnList);
        //---- API 호출 E ----//
        return result;
    }

    @Override
    public GiftPromotionDto getMspJuoSubInfoData(GiftPromotionDto giftPromotionDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        GiftPromotionDto result = restTemplate.postForObject(apiInterfaceServer + "/appForm/getMspJuoSubInfoData", giftPromotionDto, GiftPromotionDto.class);
        //---- API 호출 E ----//
        return result;
    }

    @Override
    public List<GiftPromotionDto> getChkMspGiftPrmt(GiftPromotionDto giftPromotionDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        GiftPromotionDto[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/appForm/getChkMspGiftPrmt", giftPromotionDto, GiftPromotionDto[].class);
        List<GiftPromotionDto> result  = Arrays.asList(rtnList);
        //---- API 호출 E ----//
        return result;

    }

    @Override
    public GiftPromotionDto getMspGiftPrmtCustomer(GiftPromotionDto giftPromotionDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        GiftPromotionDto result = restTemplate.postForObject(apiInterfaceServer + "/appForm/getMspGiftPrmtCustomer", giftPromotionDto, GiftPromotionDto.class);
        //---- API 호출 E ----//
        return result;
    }

    @Override
    public boolean updateMspGiftPrmtCustomer(GiftPromotionDto giftPromotionDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        boolean result = restTemplate.postForObject(apiInterfaceServer + "/appForm/updateMspGiftPrmtCustomer", giftPromotionDto, Boolean.class);
        //---- API 호출 E ----//
        return result;
    }

    @Override
    @Transactional
    public boolean insertMspGiftPrmtResult(GiftPromotionDto giftPromotionDto) {

        // 디비링크로 insert all 쿼리 사용못함...
        // 원격 데이터베이스에 DDL 작업이 허용되지 않습니다
        // for 로 등록해야됨.........

        boolean result = true;
        int ctn = 0;
        int len = 0;
        String[] prdtIdArry = giftPromotionDto.getPrdtIdArry();
        if(prdtIdArry !=null && prdtIdArry.length > 0){
            len = prdtIdArry.length;
            for(int i=0; i<len; i++){
                String prdtId = prdtIdArry[i];
                giftPromotionDto.setPrdtId(prdtId);

                //---- API 호출 S ----//
                RestTemplate restTemplate = new RestTemplate();
                int cnt = restTemplate.postForObject(apiInterfaceServer + "/appForm/insertMspGiftPrmtResult", giftPromotionDto, Integer.class);
                //---- API 호출 E ----//
                ctn += cnt;
            }
        }

        if(ctn > 0 ){
            if( ctn != len ){
                result = false;
            }
        } else {
            result = false;
        }

        return result;
    }

    @Override
    public List<GiftPromotionDto> getSaveGiftList(GiftPromotionDto giftPromotionDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        GiftPromotionDto[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/appForm/getSaveGiftList", giftPromotionDto, GiftPromotionDto[].class);
        List<GiftPromotionDto> result  = Arrays.asList(rtnList);
        //---- API 호출 E ----//
        return result;
    }


    @Override
    public ConsentGiftDto addYnChk(ConsentGiftDto consentGiftDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        ConsentGiftDto result = restTemplate.postForObject(apiInterfaceServer + "/appForm/addYnChk", consentGiftDto, ConsentGiftDto.class);
        //---- API 호출 E ----//
        return result;
    }

    @Override
    public ConsentGiftDto answerYnChk(ConsentGiftDto consentGiftDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        ConsentGiftDto result = restTemplate.postForObject(apiInterfaceServer + "/appForm/answerYnChk", consentGiftDto, ConsentGiftDto.class);
        //---- API 호출 E ----//
        return result;
    }

    @Override
    public ConsentGiftDto getConMspJuoSubInfoData(ConsentGiftDto consentGiftDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        ConsentGiftDto result = restTemplate.postForObject(apiInterfaceServer + "/appForm/getConMspJuoSubInfoData", consentGiftDto, ConsentGiftDto.class);
        //---- API 호출 E ----//
        return result;
    }

    @Override
    public boolean updateReplyDate(ConsentGiftDto consentGiftDto) {
        //---- API 호출 S ---//
        RestTemplate restTemplate = new RestTemplate();
        boolean result = restTemplate.postForObject(apiInterfaceServer + "/appForm/updateReplyDate", consentGiftDto, Boolean.class);
        //---- API 호출 E ----//
        return result;
    }

    /** 사은품 프로모션에 해당하는 혜택요약 조회 */
    @Override
    public RateGiftPrmtListDTO selectRateGiftPrmtByIdList(List<String> prmtIdList) {
            return selectRateGiftPrmtByIdListInternal(prmtIdList, "");
    }

    @Override
    public RateGiftPrmtListDTO selectRateGiftPrmtByIdList(List<String> prmtIdList,String pageUri) {
        return selectRateGiftPrmtByIdListInternal(prmtIdList, pageUri);
    }

    private RateGiftPrmtListDTO selectRateGiftPrmtByIdListInternal(List<String> prmtIdList, String pageUri) {

        RateGiftPrmtListDTO rateGiftPrmtListDTO = new RateGiftPrmtListDTO();

        // 요금제 혜택요약 seq 조회
        List<Long> seqList = giftDao.selectRateGiftPrmtSeqByIdList(prmtIdList);
        if(seqList == null || seqList.isEmpty()){
            return rateGiftPrmtListDTO;
        }

        // 요금제 혜택요약 xml 조회
        List<RateGiftPrmtXML> xmlList = rateAdsvcGdncService.getRateGiftPrmtXmlList();
        if(xmlList == null || xmlList.isEmpty()){
            return rateGiftPrmtListDTO;
        }

        // 요금제 혜택요약 정보 세팅
        String giftPrmtSeqs = seqList.stream().map(String::valueOf).collect(Collectors.joining("|"));
        rateGiftPrmtListDTO = rateAdsvcGdncService.initRateGiftPrmtInfo(xmlList, giftPrmtSeqs, "",pageUri);
        return rateGiftPrmtListDTO;

    }



}
