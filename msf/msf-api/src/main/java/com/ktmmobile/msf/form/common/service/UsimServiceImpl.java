package com.ktmmobile.msf.usim.service;

import static com.ktmmobile.msf.form.common.constants.Constants.DTL_CD_OBJ_BASE;
import static com.ktmmobile.msf.form.common.constants.Constants.DTL_CD_USIM_NFC;
import static com.ktmmobile.msf.form.common.constants.Constants.GROUP_CODE_DIRECT_USIM_PRICE;
import static com.ktmmobile.msf.form.common.constants.Constants.GROUP_CODE_MARKET_JOIN_USIM_INFO;
import static com.ktmmobile.msf.form.common.constants.Constants.GROUP_CODE_USIM_PRICE_INFO;
import static com.ktmmobile.msf.form.common.exception.msg.ExceptionMsgConstant.BIDING_EXCEPTION;
import static com.ktmmobile.msf.form.common.constant.PhoneConstant.*;
import static com.ktmmobile.msf.rate.RateAdsvcGdncUtil.getRateAdsvcGdncPath;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.form.newchange.dto.JuoSubInfoDto;
import com.ktmmobile.msf.form.common.util.*;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.msf.form.newchange.dto.FormDtlDTO;
import com.ktmmobile.msf.form.common.dto.AuthSmsDto;
import com.ktmmobile.msf.form.common.dto.db.McpRequestSelfDlvryDto;
import com.ktmmobile.msf.form.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.form.common.exception.McpCommonException;
import com.ktmmobile.msf.form.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.form.common.mspservice.dto.MspPlcyOperTypeDto;
import com.ktmmobile.msf.form.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.msf.form.common.mspservice.dto.MspSaleAgrmMst;
import com.ktmmobile.msf.form.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.form.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.msf.rate.dto.RateAdsvcBnfitGdncDtlXML;
import com.ktmmobile.msf.rate.dto.RateAdsvcGdncBasXML;
import com.ktmmobile.msf.rate.dto.RateAdsvcGdncProdRelXML;
import com.ktmmobile.msf.rate.service.RateAdsvcGdncService;
import com.ktmmobile.msf.usim.dao.UsimDao;
import com.ktmmobile.msf.usim.dto.KtRcgDto;
import com.ktmmobile.msf.usim.dto.UsimBasDto;
import com.ktmmobile.msf.usim.dto.UsimMspPlcyDto;
import com.ktmmobile.msf.usim.dto.UsimMspRateDto;

/**
 * @Class Name : UsimServiceImpl
 * @Description : 유심상품서비스 구현체AZA
 *
 * @author : ant
 * @Create Date : 2016. 1. 18.
 */
@Service
public class UsimServiceImpl implements UsimService {

    /**조직 코드  */
    @Value("${sale.orgnId}")
    private String orgnId;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private RateAdsvcGdncService rateAdsvcGdncService;

    @Autowired
    UsimDao usimDao;

    private static Logger logger = LoggerFactory.getLogger(UsimServiceImpl.class);

    @Override
    public UsimBasDto selectUsimBasDto(UsimBasDto usimBasDto) {
        return usimDao.selectUsimBasDto(usimBasDto);
    }

    @Override
    public List<UsimMspPlcyDto> listUsimMspPlcyDto() {
        return usimDao.listUsimMspPlcyDto();
    }


    @Transactional(readOnly = true)
    public KtRcgDto selectKtRcgSeq(KtRcgDto ktRcgDto){

        //usimDao.selectKtRcgSeq(ktRcgDto);
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(apiInterfaceServer + "/storeUsim/ktRcgSeq", ktRcgDto, KtRcgDto.class);
        //---- API 호출 E ----//

        return ktRcgDto;
    }

    @Override
    public Map<String, Object> selectRcg(Map<String, Object> map) {

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(apiInterfaceServer + "/storeUsim/ktRcg", map, Map.class);
        //---- API 호출 E ----//

        return map;
    }

    @Override
    public List<UsimBasDto> selectNmcpUsimBasShopList(UsimBasDto usimBasDto) {
        return usimDao.selectNmcpUsimBasShopList(usimBasDto);
    }

    @Override
    public List<UsimBasDto> selectPlcyOperTypeList(UsimBasDto usimBasDto) {
        return usimDao.selectPlcyOperTypeList(usimBasDto);
    }

    @Override
    public List<UsimMspRateDto> selectRateList(UsimBasDto usimBasDto) {
        usimBasDto.setOrgnId(orgnId);
        return usimDao.selectRateList(usimBasDto);
    }

    @Override
    public HashMap<String, Object> selectUsimBasShopListAll(UsimBasDto usimBasDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        usimBasDto.setOrgnId(orgnId);

        //판매정책 조회
        List<UsimBasDto> selectNmcpUsimBasShopList = usimDao.selectNmcpUsimBasShopList(usimBasDto);
        if(selectNmcpUsimBasShopList==null || selectNmcpUsimBasShopList.size()<1){
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
        }
        rtnMap.put("selectNmcpUsimBasShopList", selectNmcpUsimBasShopList);

        UsimBasDto inputUsimBasDto = usimBasDto;

        //가입 유형 조회
        if(null == usimBasDto.getSalePlcyCd() || "".equals(usimBasDto.getSalePlcyCd())){
            inputUsimBasDto.setSalePlcyCd(selectNmcpUsimBasShopList.get(0).getSalePlcyCd());
        }

        List<UsimBasDto> selectPlcyOperTypeList = usimDao.selectPlcyOperTypeList(inputUsimBasDto);
        rtnMap.put("selectPlcyOperTypeList", selectPlcyOperTypeList);


        //요금제 조회
        List<UsimMspRateDto> selectRateList = usimDao.selectRateList(inputUsimBasDto);
        rtnMap.put("selectRateList", selectRateList);

        //유심모델 조회
          //---- API 호출 S ----//
          RestTemplate restTemplate = new RestTemplate();
          UsimBasDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/api/storeUsim/modelList", inputUsimBasDto, UsimBasDto[].class); // usimDao.selectModelList
          List<UsimBasDto> selectModelList = Arrays.asList(resultList);
          rtnMap.put("selectModelList", selectModelList);
          //---- API 호출 E ----//


        //약정 개월수 조회
        /**
        if(null!= usimBasDto.getPayClCd() && "PO".equals(usimBasDto.getPayClCd())){
            if(null==usimBasDto.getRateCd() && "".equals(usimBasDto.getRateCd()) && selectRateList!=null &&
                    selectRateList.size()>0){
                inputUsimBasDto.setRateCd(selectRateList.get(0).getRateCd());
                rtnMap.put("selectAgreeDcAmt", usimDao.selectAgreeDcAmt(usimBasDto));
            }
        }
        */


        return rtnMap;
    }

    @Override
    public List<UsimBasDto> selectModelList(UsimBasDto usimBasDto) {
        return usimDao.selectModelList(usimBasDto);
    }

    @Override
    public List<UsimMspRateDto> selectJoinUsimPriceNew(UsimBasDto usimBasDto) {
        String param = "";
        String temp=usimBasDto.getDataType();

        param = usimBasDto.getOperType()+temp;

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        UsimMspRateDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/storeUsim/joinUsimPriceNew", param, UsimMspRateDto[].class); // usimDao.selectJoinUsimPriceNew

       if (resultList== null || resultList.length == 0) {
           return null;
       }
        List<UsimMspRateDto> list = Arrays.asList(resultList);
        //---- API 호출 E ----//

        return list;
    }

    @Override
    public Map<String, String> getSimInfo(UsimBasDto usimBasDto) {
        if (StringUtils.isBlank(usimBasDto.getOperType()) || StringUtils.isBlank(usimBasDto.getDataType())) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }


        if (StringUtils.isBlank(usimBasDto.getOrgnId()) ) {
            usimBasDto.setOrgnId(orgnId);
        }

        //1. 심비 , 가입비 확인
        String joinPrice = "0";
        String simPrice = "0";


        //eSIM
        if ("10".equals(usimBasDto.getPrdtIndCd()) || "11".equals(usimBasDto.getPrdtIndCd())  ) {
            usimBasDto.setDataType("ESIM");
        }

        //가입비 , SIM 조회
        List<UsimMspRateDto> usimPriceList= this.selectJoinUsimPriceNew(usimBasDto);

        if (usimPriceList != null && usimPriceList.size() > 0) {
            joinPrice = usimPriceList.get(0).getJoinPrice();
            simPrice = usimPriceList.get(0).getUsimPrice();
        }

        //NFC 경우 값이 없음..
//        else {
//            throw new McpCommonJsonException("0004" ,USIM_PRICE_EXCEPTION);
//        }

        //단말구매로 인입시 제외, portal DB로 유심비 한번더 체크
        if (!"MM".equals(usimBasDto.getReqBuyType())) {

            //eSIM 아닌고  LTE일때.. 공통 코드에서 조회
            if (!"10".equals(usimBasDto.getPrdtIndCd()) && LTE_FOR_MSP.equals(usimBasDto.getDataType()) ) {
                simPrice = NmcpServiceUtils.getCodeNm(GROUP_CODE_DIRECT_USIM_PRICE,DTL_CD_OBJ_BASE);
            } else if (!"10".equals(usimBasDto.getPrdtIndCd()) && FIVE_G_FOR_MSP.equals(usimBasDto.getDataType()) ) {   // 5G도 공통 코드에서 조회 처리
                simPrice = NmcpServiceUtils.getCodeNm(GROUP_CODE_DIRECT_USIM_PRICE,DTL_CD_OBJ_BASE);
            }  else if (NFC_FOR_MSP.equals(usimBasDto.getDataType())) {
                simPrice = NmcpServiceUtils.getCodeNm(GROUP_CODE_DIRECT_USIM_PRICE,DTL_CD_USIM_NFC);
            }
        }


        //2. 판매 요금제 별 가입비/유심비 면제 여부 조회
        String joinIsPay = "N";
        String simIsPay = "N";
        String nfcSimIsPay = "N";

        //eSIM
        if ("10".equals(usimBasDto.getPrdtIndCd()) || "11".equals(usimBasDto.getPrdtIndCd()) ) {
            //공통코드에서 확인
            joinIsPay = NmcpServiceUtils.getCodeNm("Constant","eSimJoinIsPay");
            if ("Y".equals(joinIsPay) ) {
                joinIsPay = "Y" ;
            } else {
                joinIsPay = "N" ;
            }

            simIsPay = NmcpServiceUtils.getCodeNm("Constant","eSimIsPay");
            if ("Y".equals(simIsPay) ) {
                simIsPay = "Y" ;
            }else {
                simIsPay = "N" ;
            }
        } else if (orgnId.equals(usimBasDto.getOrgnId())) {
            //직영접점
            NmcpCdDtlDto simPriceObj = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_USIM_PRICE_INFO,usimBasDto.getRateCd());
            if (simPriceObj != null) {
                if ("Y".equals(simPriceObj.getExpnsnStrVal1())) {
                    joinIsPay = "Y" ;
                } else {
                    joinIsPay = "N" ;
                }

                if ("Y".equals(simPriceObj.getExpnsnStrVal2())) {
                    simIsPay = "Y" ;
                } else {
                    simIsPay = "N" ;
                }

                if ("Y".equals(simPriceObj.getExpnsnStrVal3())) {
                    nfcSimIsPay = "Y" ;
                } else {
                    nfcSimIsPay = "N" ;
                }

            }
        } else  {

            //기본값이.. 납부... 로 변경....
            joinIsPay = "Y";
            simIsPay = "Y";
            nfcSimIsPay = "Y";

            NmcpCdDtlDto simPriceObj = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_MARKET_JOIN_USIM_INFO,usimBasDto.getOrgnId());
            if (simPriceObj != null) {
                if ("N".equals(simPriceObj.getExpnsnStrVal1())) {
                    joinIsPay = "N" ;
                } else {
                    joinIsPay = "N" ;
                }

                if ("N".equals(simPriceObj.getExpnsnStrVal2())) {
                    simIsPay = "N" ;
                } else {
                    simIsPay = "N" ;
                }

                if ("N".equals(simPriceObj.getExpnsnStrVal3())) {
                    nfcSimIsPay = "N" ;
                } else {
                    nfcSimIsPay = "N" ;
                }
            }
        }

        HashMap<String, String> rtnMap = new HashMap<String,String>();
        rtnMap.put("SIM_PRICE",simPrice);
        rtnMap.put("JOIN_PRICE",joinPrice);
        if (NFC_FOR_MSP.equals(usimBasDto.getDataType())) {
            rtnMap.put("SIM_IS_PAY",nfcSimIsPay);
        } else {
            rtnMap.put("SIM_IS_PAY",simIsPay);
        }
        rtnMap.put("JOIN_IS_PAY",joinIsPay);
        rtnMap.put("NFC_SIM_IS_PAY",nfcSimIsPay);

        return rtnMap;
    }

    @Override
    public UsimMspRateDto selectUsimDcamt(String rateCd) {

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        UsimMspRateDto selectUsimDcamt = restTemplate.postForObject(apiInterfaceServer + "/storeUsim/usimDcamt", rateCd, UsimMspRateDto.class); // usimDao.selectUsimDcamt
        //---- API 호출 E ----//
        return selectUsimDcamt;

    }

    @Override
    public AuthSmsDto selectUserChargeInfo(AuthSmsDto authSmsDto) {

        //---- API 호출 S ----//
        AuthSmsDto sessionAuthSmsDto = SessionUtils.getAuthSmsBean(authSmsDto);
        RestTemplate restTemplate = new RestTemplate();
        AuthSmsDto userInfo = restTemplate.postForObject(apiInterfaceServer + "/api/storeUsim/userChargeInfo", sessionAuthSmsDto, AuthSmsDto.class); // usimDao.selectUserChargeInfo

        if(userInfo!=null){
            userInfo.setMemberName(MaskingUtil.getMaskedName(sessionAuthSmsDto.getMemberName()));
        }
        //---- API 호출 E ----//

        return userInfo;
    }

    @Override
    public List<UsimMspRateDto> selectAgreeDcAmt(UsimBasDto usimBasDto) {
        usimBasDto.setOrgnId(orgnId);
        return usimDao.selectAgreeDcAmt(usimBasDto);
    }

    @Override
    public UsimBasDto findUsimBas(String dataType, String payClCd) {
        return usimDao.findUsimBas(dataType,payClCd);
    }

    @Override
    public List<UsimBasDto> selectUsimPrdtList(UsimBasDto usimBasDto) {
        usimBasDto.setOrgnId(orgnId);
        return usimDao.selectUsimPrdtList(usimBasDto);
    }

    @Override
    public List<UsimBasDto> selectUsimsalePlcyCdToPrdtList(UsimBasDto usimBasDto) {
        usimBasDto.setOrgnId(orgnId);
        return usimDao.selectUsimsalePlcyCdToPrdtList(usimBasDto);
    }

    @Override
    public List<UsimBasDto> selectUsimNewRateList(UsimBasDto usimBasDto) {
        return usimDao.selectUsimNewRateList(usimBasDto);
    }

    @Override
    public List<MspSaleAgrmMst> listMspSaleAgrmMst(String salePlcyCd) {
        return usimDao.listMspSaleAgrmMst(salePlcyCd);
    }

    @Override
    public List<UsimBasDto> selectUsimBasList(UsimBasDto usimBasDto) {

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        UsimBasDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/storeUsim/usimBasList", usimBasDto, UsimBasDto[].class); // usimDao.selectUsimBasList
        List<UsimBasDto> selectUsimBasList = Arrays.asList(resultList);
        //---- API 호출 E ----//

        return selectUsimBasList;
    }

    @Override
    public List<MspSalePlcyMstDto> selectUsimSalePlcyCdList(
            MspSalePlcyMstDto mspSalePlcyMstDto) {
        if (StringUtils.isBlank(mspSalePlcyMstDto.getOrgnId()) ) {
            mspSalePlcyMstDto.setOrgnId(orgnId);
        }

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        MspSalePlcyMstDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/storeUsim/usimSalePlcyCdList", mspSalePlcyMstDto, MspSalePlcyMstDto[].class); // usimDao.selectUsimSalePlcyCdList
        List<MspSalePlcyMstDto> selectUsimSalePlcyCdList = Arrays.asList(resultList);
        //---- API 호출 E ----//

        return selectUsimSalePlcyCdList;

    }

    @Override
    public List<MspRateMstDto> selectMspRateMstList(MspSalePlcyMstDto mspSalePlcyMstDto) {
        return usimDao.selectMspRateMstList(mspSalePlcyMstDto);
    }

    @Override
    public List<MspPlcyOperTypeDto> selectPlcyOperTypeList(
            MspSalePlcyMstDto mspSalePlcyMstDto) {

        if(mspSalePlcyMstDto.getOrgnId() == null) {
            mspSalePlcyMstDto.setOrgnId(orgnId);
        }

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        MspPlcyOperTypeDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/storeUsim/plcyOperTypeList", mspSalePlcyMstDto, MspPlcyOperTypeDto[].class); // usimDao.selectPlcyOperTypeList
        List<MspPlcyOperTypeDto> list = Arrays.asList(resultList);
        //---- API 호출 E ----//

        return list;
    }

    @Override
    public List<MspSalePlcyMstDto> selectUsimSalePlcyCdBannerList(
            MspSalePlcyMstDto mspSalePlcyMstDto) {
        mspSalePlcyMstDto.setOrgnId(orgnId);
        return usimDao.selectUsimSalePlcyCdBannerList(mspSalePlcyMstDto);
    }

    @Override
    public FormDtlDTO selectTermsData() {
        // TODO Auto-generated method stub
        return usimDao.selectTermsData();
    }

    @Override
    public List<UsimBasDto> listMspSaleAgrmMstMoreTwoRows(UsimBasDto usimBasDto) {
        // TODO Auto-generated method stub
        return usimDao.listMspSaleAgrmMstMoreTwoRows(usimBasDto);
    }

    @Override
    public List<UsimMspRateDto> selectRateListMoreTwoRows(UsimBasDto usimBasDto) {
        // TODO Auto-generated method stub
        return usimDao.selectRateListMoreTwoRows(usimBasDto);
    }

    @Override
    public List<McpRequestSelfDlvryDto> selectUserUsimList(McpRequestSelfDlvryDto dupInfo) {
        return usimDao.selectUserUsimList(dupInfo);
    }

    @Override
    public List<UsimBasDto> selectMcpUsimProdSortList(UsimBasDto usimBasDto) {
        return usimDao.selectMcpUsimProdSortList(usimBasDto);
    }

    @Override
    public int insertMcpUsimProdSort(UsimBasDto usimBasDto) {

        boolean isSucc = true;
        int result = 0;

        try{
            result = usimDao.deleteMcpUsimProdSort(usimBasDto);

            List<UsimBasDto> reqDtoList = new ArrayList<UsimBasDto>();
            if(usimBasDto !=null && usimBasDto.getUsimSeq() !=0){
                int usimSeq = usimBasDto.getUsimSeq();
                String prdtNm = usimBasDto.getPrdtNm();
                String prdtId = usimBasDto.getPrdtId();
                String amdId = usimBasDto.getAmdId();


                String[] arrPaySort = usimBasDto.getArrPaySort();
                String[] arrRateCd = usimBasDto.getArrRateCd();
                String[] arrRateNm = usimBasDto.getArrRateNm();
                String[] arrSalePlcyCd = usimBasDto.getArrSalePlcyCd();

                if( arrPaySort !=null && arrPaySort.length > 0 && arrRateCd !=null && arrRateCd.length > 0 && arrRateNm !=null && arrRateNm.length > 0 && arrSalePlcyCd !=null && arrSalePlcyCd.length > 0 ){
                    for(int i=0; i<arrPaySort.length; i++){
                        String paySort = arrPaySort[i];
                        String rateCd = arrRateCd[i];
                        String rateNm = arrRateNm[i];
                        String salePlcyCd= arrSalePlcyCd[i];
                        UsimBasDto reqDto = new UsimBasDto();
                        reqDto.setUsimSeq(usimSeq);
                        reqDto.setPrdtNm(prdtNm);
                        reqDto.setPrdtId(prdtId);
                        reqDto.setAmdId(amdId);
                        reqDto.setPaySort(paySort);
                        reqDto.setRateCd(rateCd);
                        reqDto.setRateNm(rateNm);
                        reqDto.setSalePlcyCd(salePlcyCd);
                        reqDtoList.add(reqDto);
                    }
                    result = usimDao.insertMcpUsimProdSort(reqDtoList);
                }

            }
        }catch(Exception e){
            isSucc = false;
        }
        if(isSucc){
            result = 1;
        }
        return result;
    }

    @Override
    public List<MspSaleSubsdMstDto> listChargeInfoUsimXml(List<MspSaleSubsdMstDto> chargeList, String searchType) {

        List<MspSaleSubsdMstDto> result = new ArrayList<MspSaleSubsdMstDto>();
        String fPer = File.separator;
        String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);

        try {
            String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_PROD_REL.xml";
            /* List 경우일때*/
            File file1 = new File(realFilePath);
            if(file1.exists()) {
                List<RateAdsvcGdncProdRelXML> rateAdsvcGdncProdRelList = XmlBindUtil.bindListFromXml(RateAdsvcGdncProdRelXML.class, file1);

                for(MspSaleSubsdMstDto chargeInfo : chargeList) {

                    boolean isMapping = false;

                    for(RateAdsvcGdncProdRelXML item : rateAdsvcGdncProdRelList) {
                        if(chargeInfo.getRateCd().equals(item.getRateAdsvcCd())) {
                            realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_BAS_" + item.getRateAdsvcGdncSeq() + ".xml";
                            file1 = new File(realFilePath);

                            if(file1.exists()) {
                                RateAdsvcGdncBasXML rateAdsvcGdncBasXML = XmlBindUtil.bindFromXml(RateAdsvcGdncBasXML.class, file1);

                                if (rateAdsvcGdncBasXML != null && searchType.equals(rateAdsvcGdncBasXML.getRateDivCd())) {
                                    this.setDetail(file1 ,rateAdsvcGdncBasXML ,  chargeInfo , item ) ;
                                    isMapping = true;
                                    break;
                                }
                            }
                        }
                    }

                    //구분코드 내 요금제코드가 없을 경우
                    //다시 매핑
                    if (!isMapping) {
                        for(RateAdsvcGdncProdRelXML item : rateAdsvcGdncProdRelList) {
                            if(chargeInfo.getRateCd().equals(item.getRateAdsvcCd())) {
                                realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_BAS_" + item.getRateAdsvcGdncSeq() + ".xml";
                                file1 = new File(realFilePath);

                                if(file1.exists()) {
                                    RateAdsvcGdncBasXML rateAdsvcGdncBasXML = XmlBindUtil.bindFromXml(RateAdsvcGdncBasXML.class, file1);

                                    if (rateAdsvcGdncBasXML != null ) {
                                        this.setDetail(file1 ,rateAdsvcGdncBasXML ,  chargeInfo , item ) ;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if(chargeInfo != null) {
                        result.add(chargeInfo);
                    }

                }
            }

        } catch(JAXBException e) {
            logger.error(e.toString());
        } catch(Exception e) {
            logger.error(e.toString());
        }
        return result;
    }

    /**
     * 불량 유심 조회
     * @param iccId
     * @return
     */
    @Override
    public int selectFailUsims(String iccId) {
        RestTemplate restTemplate = new RestTemplate();
         int failUsimCntt = restTemplate.postForObject(apiInterfaceServer + "/storeUsim/failUsim", iccId, Integer.class);
        return failUsimCntt;
    }

    /**
     * 불량 유심 조회한 사용자 Update
     * @param juoSubInfo
     */
    @Override
    public void updateFailUsims(JuoSubInfoDto juoSubInfo) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(apiInterfaceServer + "/storeUsim/updateFailUsim", juoSubInfo, Integer.class);
    }


    public void setDetail(File file1 , RateAdsvcGdncBasXML rateAdsvcGdncBasXML ,MspSaleSubsdMstDto chargeInfo , RateAdsvcGdncProdRelXML item ) {

        String fPer = File.separator;
        String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);

        try {
            if(file1.exists()) {
                if(chargeInfo.getMspRateMstDto() == null) {
                    MspRateMstDto mrmd = new MspRateMstDto();
                    chargeInfo.setMspRateMstDto(mrmd);
                    int mmBasAmtVat = 0;
                    int promotionAmtVat = 0;
                    if(rateAdsvcGdncBasXML.getMmBasAmtVatDesc() != null && !"".equals(rateAdsvcGdncBasXML.getMmBasAmtVatDesc())) {
                        mmBasAmtVat = Integer.parseInt(rateAdsvcGdncBasXML.getMmBasAmtVatDesc().trim().replaceAll(",", ""));
                    }
                    if(rateAdsvcGdncBasXML.getPromotionAmtVatDesc() != null && !"".equals(rateAdsvcGdncBasXML.getPromotionAmtVatDesc())) {
                        promotionAmtVat = Integer.parseInt(rateAdsvcGdncBasXML.getPromotionAmtVatDesc().trim().replaceAll(",", ""));
                    }

                    if(promotionAmtVat != 0) {
                        chargeInfo.setXmlPayMnthAmt(promotionAmtVat);
                    }else {
                        chargeInfo.setXmlPayMnthAmt(mmBasAmtVat);
                    }
                    chargeInfo.getMspRateMstDto().setRateNm(chargeInfo.getRateNm());
                    chargeInfo.getMspRateMstDto().setFreeCallCnt(chargeInfo.getFreeCallCnt());
                    chargeInfo.getMspRateMstDto().setFreeDataCnt(chargeInfo.getFreeDataCnt());
                    chargeInfo.getMspRateMstDto().setFreeSmsCnt(chargeInfo.getFreeSmsCnt());
                }
                chargeInfo.setRateCd(chargeInfo.getRateCd());
                chargeInfo.setMmBasAmtVatDesc(rateAdsvcGdncBasXML.getMmBasAmtVatDesc());
                chargeInfo.setPromotionAmtVatDesc(rateAdsvcGdncBasXML.getPromotionAmtVatDesc());
                chargeInfo.setRateAdsvcBasDesc(rateAdsvcGdncBasXML.getRateAdsvcBasDesc());
                chargeInfo.setRateAdsvcNm(rateAdsvcGdncBasXML.getRateAdsvcNm());
                chargeInfo.getMspRateMstDto().setXmlCallCnt("0");
                chargeInfo.getMspRateMstDto().setXmlDataCnt("0");
                chargeInfo.getMspRateMstDto().setXmlQosCnt("0");
                chargeInfo.setRateAdsvcGdncSeq(item.getRateAdsvcGdncSeq());
                chargeInfo.setExpnsnStrVal1(rateAdsvcGdncBasXML.getRateCtg());
                chargeInfo.setExpnsnStrVal2(rateAdsvcGdncBasXML.getStickerCtg());

                String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_BNFIT_GDNC_DTL_" + item.getRateAdsvcGdncSeq() + ".xml";
                File file2 = new File(realFilePath);

                if(file2.exists()) {
                    List<RateAdsvcBnfitGdncDtlXML> rateAdsvcBnfitGdncDtlList = XmlBindUtil.bindListFromXml(RateAdsvcBnfitGdncDtlXML.class, file2);
                    String nowDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
                    long date = Long.parseLong(nowDate);

                    for(RateAdsvcBnfitGdncDtlXML bnfit : rateAdsvcBnfitGdncDtlList) {
                        bnfit.setPstngStartDate(StringUtil.NVL(bnfit.getPstngStartDate(), "0"));
                        bnfit.setPstngEndDate(StringUtil.NVL(bnfit.getPstngEndDate(), "0"));

                        bnfit.setPstngStartDate(bnfit.getPstngStartDate().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", ""));
                        bnfit.setPstngEndDate(bnfit.getPstngEndDate().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", ""));
                        if("RATEBE01".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //요금제제공량(데이터(노출문구))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                chargeInfo.setRateAdsvcData(bnfit.getRateAdsvcItemDesc());
                            }
                        }else if("RATEBE02".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //요금제제공량(음성(노출문구))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                chargeInfo.setRateAdsvcCall(bnfit.getRateAdsvcItemDesc());
                            }
                        }else if("RATEBE03".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //요금제제공량(문자(노출문구))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                chargeInfo.setRateAdsvcSms(bnfit.getRateAdsvcItemDesc());
                            }
                        }else if("RATEBE31".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //프로모션제공량(데이터(노출문구))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                chargeInfo.setRateAdsvcPromData(bnfit.getRateAdsvcItemDesc());
                            }
                        }else if("RATEBE32".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //프로모션제공량(음성(노출문구))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                chargeInfo.setRateAdsvcPromCall(bnfit.getRateAdsvcItemDesc());
                            }
                        }else if("RATEBE33".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //프로모션제공량(문자(노출문구))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                chargeInfo.setRateAdsvcPromSms(bnfit.getRateAdsvcItemDesc());
                            }
                        }else if("RATEBE11".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //제휴혜택안내
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                chargeInfo.setRateAdsvcAllianceBnfit(bnfit.getRateAdsvcItemDesc());
                            }
                        }else if("RATEBE12".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //혜택안내
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                chargeInfo.setRateAdsvcBnfit(bnfit.getRateAdsvcItemDesc());
                            }
                        }else if("RATEBE94".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //휴대폰 구매 시 노출문구
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                chargeInfo.setPhoneBuyPhrase(bnfit.getRateAdsvcItemDesc());
                            }
                        }else if("RATEBE95".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //최대 데이터제공량(데이터(노출문구))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                chargeInfo.setMaxDataDelivery(bnfit.getRateAdsvcItemDesc());
                            }
                        }
                        else if("RATEBE21".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //요금제제공량(데이터(KB))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                bnfit.setRateAdsvcItemDesc(StringUtil.NVL(bnfit.getRateAdsvcItemDesc(), "0"));
                                bnfit.setRateAdsvcItemDesc(bnfit.getRateAdsvcItemDesc().trim().replaceAll(",", ""));
                                chargeInfo.getMspRateMstDto().setXmlDataCnt((Integer.parseInt(StringUtil.NVL(chargeInfo.getMspRateMstDto().getXmlDataCnt(), "0")) + Integer.parseInt(StringUtil.NVL((String.valueOf(Math.round(Double.parseDouble(bnfit.getRateAdsvcItemDesc())))), "0")))+"");
                                if(Integer.parseInt(chargeInfo.getMspRateMstDto().getXmlDataCnt()) > 999999999) {
                                    chargeInfo.getMspRateMstDto().setXmlDataCnt("999999999");
                                }
                            }
                        }else if("RATEBE25".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //요금제제공량(데이터Qos(Kbps))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                bnfit.setRateAdsvcItemDesc(StringUtil.NVL(bnfit.getRateAdsvcItemDesc(), "0"));
                                bnfit.setRateAdsvcItemDesc(bnfit.getRateAdsvcItemDesc().trim().replaceAll(",", ""));
                                chargeInfo.getMspRateMstDto().setXmlQosCnt((Integer.parseInt(StringUtil.NVL(chargeInfo.getMspRateMstDto().getXmlQosCnt(), "0")) + Integer.parseInt(StringUtil.NVL((String.valueOf(Math.round(Double.parseDouble(bnfit.getRateAdsvcItemDesc())))), "0")))+"");
                                if(Integer.parseInt(chargeInfo.getMspRateMstDto().getXmlQosCnt()) > 999999999) {
                                    chargeInfo.getMspRateMstDto().setXmlQosCnt("999999999");
                                }
                            }
                        }else if("RATEBE22".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //요금제제공량(음성(초))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                bnfit.setRateAdsvcItemDesc(StringUtil.NVL(bnfit.getRateAdsvcItemDesc(), "0"));
                                bnfit.setRateAdsvcItemDesc(bnfit.getRateAdsvcItemDesc().trim().replaceAll(",", ""));
                                chargeInfo.getMspRateMstDto().setXmlCallCnt((Integer.parseInt(StringUtil.NVL(chargeInfo.getMspRateMstDto().getXmlCallCnt(), "0")) + Integer.parseInt(StringUtil.NVL((String.valueOf(Math.round(Double.parseDouble(bnfit.getRateAdsvcItemDesc())))), "0")))+"");
                                if(Integer.parseInt(chargeInfo.getMspRateMstDto().getXmlCallCnt()) > 999999999) {
                                    chargeInfo.getMspRateMstDto().setXmlCallCnt("999999999");
                                }
                            }
                        }else if("RATEBE41".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //프로모션제공량(데이터(KB))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                bnfit.setRateAdsvcItemDesc(StringUtil.NVL(bnfit.getRateAdsvcItemDesc(), "0"));
                                bnfit.setRateAdsvcItemDesc(bnfit.getRateAdsvcItemDesc().trim().replaceAll(",", ""));
                                chargeInfo.getMspRateMstDto().setXmlDataCnt((Integer.parseInt(StringUtil.NVL(chargeInfo.getMspRateMstDto().getXmlDataCnt(), "0")) + Integer.parseInt(StringUtil.NVL((String.valueOf(Math.round(Double.parseDouble(bnfit.getRateAdsvcItemDesc())))), "0")))+"");
                                if(Integer.parseInt(chargeInfo.getMspRateMstDto().getXmlDataCnt()) > 999999999) {
                                    chargeInfo.getMspRateMstDto().setXmlDataCnt("999999999");
                                }
                            }
                        }else if("RATEBE45".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //프로모션제공량(데이터Qos(Kbps))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                bnfit.setRateAdsvcItemDesc(StringUtil.NVL(bnfit.getRateAdsvcItemDesc(), "0"));
                                bnfit.setRateAdsvcItemDesc(bnfit.getRateAdsvcItemDesc().trim().replaceAll(",", ""));
                                chargeInfo.getMspRateMstDto().setXmlQosCnt((Integer.parseInt(StringUtil.NVL(chargeInfo.getMspRateMstDto().getXmlQosCnt(), "0")) + Integer.parseInt(StringUtil.NVL((String.valueOf(Math.round(Double.parseDouble(bnfit.getRateAdsvcItemDesc())))), "0")))+"");
                                if(Integer.parseInt(chargeInfo.getMspRateMstDto().getXmlQosCnt()) > 999999999) {
                                    chargeInfo.getMspRateMstDto().setXmlQosCnt("999999999");
                                }
                            }
                        }else if("RATEBE42".equals(bnfit.getRateAdsvcBnfitItemCd())) {
                            //프로모션제공량(음성(초))
                            if(date >= Long.parseLong(bnfit.getPstngStartDate()) && date <= Long.parseLong(bnfit.getPstngEndDate())) {
                                bnfit.setRateAdsvcItemDesc(StringUtil.NVL(bnfit.getRateAdsvcItemDesc(), "0"));
                                bnfit.setRateAdsvcItemDesc(bnfit.getRateAdsvcItemDesc().trim().replaceAll(",", ""));
                                chargeInfo.getMspRateMstDto().setXmlCallCnt((Integer.parseInt(StringUtil.NVL(chargeInfo.getMspRateMstDto().getXmlCallCnt(), "0")) + Integer.parseInt(StringUtil.NVL((String.valueOf(Math.round(Double.parseDouble(bnfit.getRateAdsvcItemDesc())))), "0")))+"");
                                if(Integer.parseInt(chargeInfo.getMspRateMstDto().getXmlCallCnt()) > 999999999) {
                                    chargeInfo.getMspRateMstDto().setXmlCallCnt("999999999");
                                }
                            }
                        }
                    }

                }


            }
        } catch(SecurityException e) {
            logger.error(e.toString());
        } catch(Exception e) {
            logger.error(e.toString());
        }

    }
}
