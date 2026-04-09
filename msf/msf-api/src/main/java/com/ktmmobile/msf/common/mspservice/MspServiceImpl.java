package com.ktmmobile.msf.common.mspservice;

import static com.ktmmobile.msf.form.common.constant.PhoneConstant.PHONE_FOR_MSP;
import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION;
import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.NO_EXSIST_SALE_PRDT;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ktmmobile.msf.form.common.constant.PhoneConstant;
import com.ktmmobile.msf.form.common.dto.PhoneMspDto;
import com.ktmmobile.msf.common.exception.McpCommonException;
import com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.common.mspservice.dao.MspDao;
import com.ktmmobile.msf.common.mspservice.dto.CmnGrpCdMst;
import com.ktmmobile.msf.common.mspservice.dto.MspNoticSupportMstDto;
import com.ktmmobile.msf.common.mspservice.dto.MspOrgDto;
import com.ktmmobile.msf.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.msf.common.mspservice.dto.MspSaleAgrmMst;
import com.ktmmobile.msf.common.mspservice.dto.MspSaleDto;
import com.ktmmobile.msf.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.common.mspservice.dto.MspSalePrdtMstDto;
import com.ktmmobile.msf.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.msf.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.common.util.ObjectUtils;


/**
 * @Class Name : MspServiceImpl
 * @Description : Msp 서비스 조회 구현체
 *
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
@Service
public class MspServiceImpl implements MspService {

    private static final Logger logger = LoggerFactory.getLogger(MspServiceImpl.class);

    /**자급제 조직 코드  */
    @Value("${sale.sesplsOrgnId}")
    private String sesplsOrgnId;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    MspDao mspDao;

     /* (non-Javadoc)
     * @see com.ktmmobile.mcp.phone.service.PhoneService#findMspOrgList(com.ktmmobile.mcp.phone.dto.MspOrgDto)
     */
    @Override
    public List<MspOrgDto> findMspOrgList(MspOrgDto mspOrgDto) {
        RestTemplate restTemplate = new RestTemplate();
        logger.debug("apiInterfaceServer:{}:findMspOrgList, mspOrgDto:{}", apiInterfaceServer, ObjectUtils.convertObjectToString(mspOrgDto));
        MspOrgDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/msp/orgnMnfctMst", mspOrgDto, MspOrgDto[].class);
        List<MspOrgDto> retList = Arrays.asList(resultList);
        return retList ;
    }

    @Override
    public List<MspOrgDto> findMspOrgListRe(String prodType) {
        RestTemplate restTemplate = new RestTemplate();
        MspOrgDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/msp/orgnMnfctMstRe", prodType, MspOrgDto[].class);
        List<MspOrgDto> retList = Arrays.asList(resultList);
        if(retList !=null && !retList.isEmpty()) {
            for(MspOrgDto dto : retList ) {
                String mnfctNm = dto.getMnfctNm();
                mnfctNm = mnfctNm.replaceAll("&amp;", "&");
                dto.setMnfctNm(mnfctNm);
            }
        }
        return retList ;
    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.phone.service.PhoneService#findMspSalePlcyMst(com.ktmmobile.mcp.phone.dto.msp.MspSalePlcyMstDto)
     */
    @Override
    public List<MspSalePlcyMstDto> findMspSalePlcyMst(MspSalePlcyMstDto mspSalePlcyMstDto) {
        List<MspSalePlcyMstDto> plcyMstList =  mspDao.findMspSalePlcyMst(mspSalePlcyMstDto);
        if (plcyMstList == null) {
            throw new McpCommonException(NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
        }
        return plcyMstList;
    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.phone.service.PhoneService#getMspSale(java.lang.String, com.ktmmobile.mcp.phone.dto.msp.MspSalePlcyMstDto)
     */
    @Override
    public MspSaleDto getMspSale(String prdtId,
            MspSalePlcyMstDto mspSalePlcyMstDto) throws McpCommonException {

        //return 할 msp 판매정책정보및 상품정보 set
        MspSaleDto mspSaleDtoRtn = new MspSaleDto();

        mspSalePlcyMstDto.setPrdtId(prdtId);
        List<MspSalePlcyMstDto> resultSalePlcyMst = findMspSalePlcyMst(mspSalePlcyMstDto);

        if (resultSalePlcyMst == null || resultSalePlcyMst.size() == 0) {
            throw new McpCommonException(NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
        }

        String salePlcyCd = ""; //정책코드
        String orgnId2 = mspSalePlcyMstDto.getOrgnId();//기관코드

        if (resultSalePlcyMst.size() > 1) {	//정책코드가 2개 이상일경우에는 단말할인을 기본값으로 선택해서 정책정보를 가져온다.
            for (MspSalePlcyMstDto baket: resultSalePlcyMst) {

                if(orgnId2.contentEquals(sesplsOrgnId)) {
                    // 자급제인 경우 강제로 변환 처리
                    salePlcyCd = baket.getSalePlcyCd();
                    mspSaleDtoRtn.setMspSalePlcyMstDtoSimbol(baket); // 1개 정책 세팅
                    break;

                } else {
                    // 휴대폰/중고폰인 경우

                    if (PhoneConstant.PHONE_DISCOUNT_FOR_MSP.equals(baket.getSprtTp())) {
                        salePlcyCd = baket.getSalePlcyCd();
                        mspSaleDtoRtn.setMspSalePlcyMstDtoSimbol(baket);		//2개이상의 정책이 조회 되었기떄문에 대표정책을 할당한다.(단품할인)
                        break;
                    }
                }
            }
        } else {
            salePlcyCd = resultSalePlcyMst.get(0).getSalePlcyCd();
            mspSaleDtoRtn.setMspSalePlcyMstDtoSimbol(resultSalePlcyMst.get(0));	//대표정책 할당
        }


        //검색조건 생성 정책코드와,단품(nrds) 코드값을 set
        MspSalePrdtMstDto searchParam = new MspSalePrdtMstDto();
        searchParam.setPrdtId(prdtId);
        searchParam.setSalePlcyCd(salePlcyCd);
        searchParam.setOrgnId(orgnId2);

        //MSP 판매 상품및 수수료 정보 조회
        MspSalePrdtMstDto resultSalePrdMst = mspDao.findMspSalePrdMst(searchParam);

        //판매상품정보가 msp 정보에 존재하지 않는다.
        if (resultSalePrdMst == null) {
            throw new McpCommonException(NO_EXSIST_SALE_PRDT);
        }

        mspSaleDtoRtn.setMspSalePlcyMstDto(resultSalePlcyMst);
        mspSaleDtoRtn.setMspSalePrdMstDto(resultSalePrdMst);

        return mspSaleDtoRtn;
    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.mspservice.service.MspService#listMspSaleAgrmMst(java.lang.String)
     */
    @Override
    public List<MspSaleAgrmMst> listMspSaleAgrmMst(String salePlcyCd) {
        List<MspSaleAgrmMst> saleList = mspDao.listMspSaleAgrmMst(salePlcyCd);
        if (saleList == null || saleList.size() == 0) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_INST_NOM);
        }
        return saleList;
    }



    @Override
    public List<MspSaleAgrmMst> listMspSaleAgrmMst2(MspSaleAgrmMst mspSaleAgrmMst) {
        RestTemplate restTemplate = new RestTemplate();

        MspSaleAgrmMst[] resultList = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSaleAgrmMst2", mspSaleAgrmMst, MspSaleAgrmMst[].class);
        List<MspSaleAgrmMst> saleList = Arrays.asList(resultList);
        if (saleList == null || saleList.size() == 0) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_INST_NOM);
        }
        return saleList;
    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.mspservice.service.MspService#listMspSaleSubsdMst(com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto)
     */
    public List<MspSaleSubsdMstDto> listMspSaleSubsdMst(
            MspSaleSubsdMstDto mspSaleSubsdMstDto) {

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        MspSaleSubsdMstDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSaleSubsdMstList", mspSaleSubsdMstDto, MspSaleSubsdMstDto[].class);
        List<MspSaleSubsdMstDto> list = Arrays.asList(resultList);
        //---- API 호출 E ----//
        return list;
    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.mspservice.service.MspService#findCmnGrpCdMst(java.lang.String, java.lang.String)
     */
    @Override
    public CmnGrpCdMst findCmnGrpCdMst(String grpCd, String cdVal) {
        CmnGrpCdMst cmnGrpCdMst = new CmnGrpCdMst();
        cmnGrpCdMst.setGrpId(grpCd);
        cmnGrpCdMst.setCdVal(cdVal);
        return mspDao.findCmnGrpCdMst(cmnGrpCdMst);
    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.mspservice.service.MspService#listChargeSort(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<MspSaleSubsdMstDto> listChargeInfo(String prdtId,
            MspSaleDto mspSaleDto, String oldYn, String orgnId,String operType,String instNom,String rateCd,String noArgmYn) {

        //약정 개월 , 단말기 할부 개월 동일 하게 처리   8
        return listChargeInfo(prdtId,mspSaleDto, oldYn,orgnId,operType,instNom,instNom,rateCd, noArgmYn) ;
    }

    @Override
    public List<MspSaleSubsdMstDto> listChargeInfo(String prdtId,
            MspSaleDto mspSaleDto, String oldYn, String orgnId,String operType,String instNom,String modelMonthly,String rateCd,String noArgmYn ) {
      //약정 개월 , 단말기 할부 개월 동일 하게 처리    9
        return listChargeInfo(prdtId,mspSaleDto, oldYn,orgnId,operType,instNom,modelMonthly,rateCd, noArgmYn,"") ;

    }

    @Override
    public List<MspSaleSubsdMstDto> listChargeInfo(String prdtId,
            MspSaleDto mspSaleDto, String oldYn, String orgnId,String operType,String instNom,String modelMonthly,String rateCd,String noArgmYn ,String onOffType) {
        //모집경로 추가    10
        CmnGrpCdMst cdInfo = findCmnGrpCdMst("CMN0051", "20");	//할부수수료 default 값
        BigDecimal defaultInstRate = new BigDecimal(cdInfo.getEtc1());

        List<MspSaleSubsdMstDto> mergeList = new ArrayList<MspSaleSubsdMstDto>();

        MspSaleSubsdMstDto mspSaleSubsdMstDto = new MspSaleSubsdMstDto();
        mspSaleSubsdMstDto.setPrdtId(prdtId);
        mspSaleSubsdMstDto.setOldYn(oldYn);
        mspSaleSubsdMstDto.setOrgnId(orgnId);
        mspSaleSubsdMstDto.setOperType(operType);//가입유형
        mspSaleSubsdMstDto.setAgrmTrm(instNom);//입력받은 할부기간을 약정기간으로 세팅한다.
        mspSaleSubsdMstDto.setRateCd(rateCd);//요금제코드
        mspSaleSubsdMstDto.setNoArgmYn(noArgmYn);//요금제코드
        mspSaleSubsdMstDto.setOnOffType(onOffType);

        /*
         * 비교하기 페이지에서만 요금제 리스트 호출시(RMK )필드를 조회하고 ,
         * 상품상세에서는 RMK 필드를 조회하지 않는다.(성능 개선을 위한 처리) RMK 필드는 비고란은 한글 문자열이 많이 들어가있으나
         * 상품상세에서는 사용하지 않는다.
         */
        if (mspSaleDto.getForCompareYn() != null && mspSaleDto.getForCompareYn().equals("Y")) {
            mspSaleSubsdMstDto.setForFrontFastYn("C");
        }

        //for문을 돌면서 가져온 모든 정책정보에 해당하는 요금제 정보를 list 에 담는다.
        for (MspSalePlcyMstDto mspSalePlcyMstDto : mspSaleDto.getMspSalePlcyMstDto()) {
            mspSaleSubsdMstDto.setPlcySctnCd(PHONE_FOR_MSP);
            mspSaleSubsdMstDto.setSalePlcyCd(mspSalePlcyMstDto.getSalePlcyCd());

            BigDecimal instRate = mspSalePlcyMstDto.getInstRate();						//정책정보의 할부수수료값
            List<MspSaleSubsdMstDto> list = listMspSaleSubsdMst(mspSaleSubsdMstDto);	//요금제 리스트 조회
            for (MspSaleSubsdMstDto bakset : list) {	//요즘제 정보에 정책할부수수료와,기본수수료값을 세팅한다.
                bakset.setInstRate(instRate);
                bakset.setDefaultInstRate(defaultInstRate);
                if (instNom != null) {
                    bakset.setModelMonthly(Integer.parseInt(modelMonthly));	//입력받은 할부기간
                }
            }
            mergeList.addAll(list);	//요금제 merge
        }
        return mergeList;
    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.mspservice.service.MspService#listRateByOrgnInfos(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<MspRateMstDto> listRateByOrgnInfos(String orgnId,
            String sprtTp, String plcySctnCd, String prdtSctnCd,
            String plcyTypeCd) {

        MspSalePlcyMstDto mspSalePlcyMstDto = new MspSalePlcyMstDto();
        mspSalePlcyMstDto.setOrgnId(orgnId);
        mspSalePlcyMstDto.setSprtTp(sprtTp);
        mspSalePlcyMstDto.setPlcySctnCd(plcySctnCd);
        mspSalePlcyMstDto.setPrdtSctnCd(prdtSctnCd);
        mspSalePlcyMstDto.setPlcyTypeCd(plcyTypeCd);

        return mspDao.listRateByOrgnInfos(mspSalePlcyMstDto);
    }


    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.mspservice.service.MspService#listChargeSort(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     * Usim 상품에서 사용 차후 바뀔경우 수정
     */
    @Override
    public List<MspSaleSubsdMstDto> listChargeInfoUsim(MspSaleSubsdMstDto inputMspSaleSubsdMstDto) {

        MspSaleSubsdMstDto mspSaleSubsdMstDto = inputMspSaleSubsdMstDto;

        List<MspSaleSubsdMstDto> list = listMspSaleSubsdMst(mspSaleSubsdMstDto);

        return list;
    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.mspservice.service.MspService#findMspSalePlcyInfoByOnlyOrgn(com.ktmmobile.mcp.mspservice.dto.MspSalePlcyMstDto)
     */
    @Override
    public List<MspSalePlcyMstDto> listMspSalePlcyInfoByOnlyOrgn(
            MspSalePlcyMstDto mspSalePlcyMstDto) {
        return mspDao.listMspSalePlcyInfoByOnlyOrgn(mspSalePlcyMstDto);
    }

    @Override
    public MspSaleSubsdMstDto getLowPriceChargeInfoByProdList(String prdtId
            ,MspSalePlcyMstDto mspSalePlcyMstDto
            , String oldYn
            , String orgnId
            , String operType
            , String instNom
            , String rateCd
            , String noArgmYn
            , CmnGrpCdMst cmnGrpCdMst ) {

       if (mspSalePlcyMstDto == null) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
        }


        //할부수수료 default 값
        BigDecimal defaultInstRate = new BigDecimal(cmnGrpCdMst.getEtc1());

       MspSaleSubsdMstDto mspSaleSubsdMstDto = new MspSaleSubsdMstDto();
       mspSaleSubsdMstDto.setPrdtId(prdtId);
       mspSaleSubsdMstDto.setSalePlcyCd(mspSalePlcyMstDto.getSalePlcyCd());
       mspSaleSubsdMstDto.setOldYn(oldYn);
       mspSaleSubsdMstDto.setOrgnId(orgnId);
       mspSaleSubsdMstDto.setOperType(operType);//가입유형
       mspSaleSubsdMstDto.setAgrmTrm(instNom);//입력받은 할부기간을 약정기간으로 세팅한다.
       mspSaleSubsdMstDto.setRateCd(rateCd);//요금제코드
       mspSaleSubsdMstDto.setNoArgmYn(noArgmYn);
       mspSaleSubsdMstDto.setForFrontFastYn(""); //성능 개선을 위한 최소 컬럼을 호출하기 위한 처리 SQL 호출을 위한 flag

       BigDecimal instRate = mspSalePlcyMstDto.getInstRate();       //정책정보의 할부수수료값
       MspSaleSubsdMstDto mspSaleSubsdMst = mspDao.getMspSaleSubsdMst(mspSaleSubsdMstDto);

       if (mspSaleSubsdMst != null) {
           mspSaleSubsdMst.setInstRate(instRate);
           mspSaleSubsdMst.setDefaultInstRate(defaultInstRate);
           mspSaleSubsdMst.setModelMonthly(Integer.parseInt(instNom));
       }

       return mspSaleSubsdMst;
    }

    /* (non-Javadoc)
     * @see com.ktmmobile.mcp.mspservice.service.MspService#findMspPhoneInfo(java.lang.String)
     */
    @Override
    public PhoneMspDto findMspPhoneInfo(String prdtId) {
        // TODO Auto-generated method stub
        return mspDao.findMspPhoneInfo(prdtId);
    }

    @Override
    public List<MspNoticSupportMstDto> listMspOfficialSupportRateNm() {
        return mspDao.listMspOfficialSupportRateNm();
    }

    @Override
    public List<MspNoticSupportMstDto> listMspOfficialNoticeSupport(MspNoticSupportMstDto mspNoticSupportMstDto, int skipResult, int maxResult) {
        return mspDao.listMspOfficialNoticeSupport(mspNoticSupportMstDto, skipResult, maxResult);
    }

    @Override
    public int listMspOfficialNoticeSupportCount(MspNoticSupportMstDto mspNoticSupportMstDto) {
        return mspDao.listMspOfficialNoticeSupportCount(mspNoticSupportMstDto);
    }

    @Override
    public boolean checkKid(String contractNum) {
        String customerSsn = mspDao.getCustomerSsn(contractNum) ;

// PNB_확인필요        
//        try {
//            customerSsn = EncryptUtil.ace256Dec(customerSsn);
//        } catch (CryptoException e) {
//            throw new McpCommonException(ACE_256_DECRYPT_EXCEPTION);
//        }

        // 개발계 DB 데이터 없을 경우
        if ("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName) ) {
            if(customerSsn.equals("")) {
                customerSsn = "7001011234567";
            }
        }

        if ("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName) ) {
            /*개발계 DB에서 주민번호가 700101******* 표현 으로 오류 발생 */
            customerSsn = customerSsn.replaceAll("[*]", "1");
        }

        //나이 확인
        int age = NmcpServiceUtils.getAge(customerSsn, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));

        if (19 <= age) {
            return false;
        } else {
            return true;
        }
    }

     @Override
     public MspRateMstDto getMspRateMst(String rateCd) {
         return mspDao.getMspRateMst(rateCd);
     }

     @Override
     public List<MspSaleSubsdMstDto> listMspSaleMst(
             MspSaleSubsdMstDto mspSaleSubsdMstDto) {
         return mspDao.listMspSaleMst(mspSaleSubsdMstDto);
     }

    @Override
    public List<MspSaleAgrmMst> mspSaleAgrmMstSing(String salePlcyCd) {
        List<MspSaleAgrmMst> mspSaleAgrmMsts= this.mspDao.findMspSaleMnth(salePlcyCd);
        return mspSaleAgrmMsts;
    }

    @Override
    public MspSaleSubsdMstDto getLowPriceChargeInfoByProdList(String prdtId,
                                                              MspSalePlcyMstDto mspSalePlcyMstDto,
                                                              String oldYn,
                                                              String orgnId,
                                                              String operType,
                                                              String instNom,
                                                              String rateCd,
                                                              String noArgmYn,
                                                              CmnGrpCdMst cmnGrpCdMst,
                                                              String onOffType) {

        return getLowPriceChargeInfoByProdList(prdtId,mspSalePlcyMstDto,oldYn,orgnId,operType,instNom,rateCd,noArgmYn,cmnGrpCdMst,onOffType,instNom) ;
    }

    @Override
    public MspSaleSubsdMstDto getLowPriceChargeInfoByProdList(String prdtId,
                                                              MspSalePlcyMstDto mspSalePlcyMstDto,
                                                              String oldYn,
                                                              String orgnId,
                                                              String operType,
                                                              String instNom,
                                                              String rateCd,
                                                              String noArgmYn,
                                                              CmnGrpCdMst cmnGrpCdMst
                                                              , String onOffType
                                                              , String  modelMonthly   ) {
        if (mspSalePlcyMstDto == null) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
        }


        //할부수수료 default 값
        BigDecimal defaultInstRate = new BigDecimal(cmnGrpCdMst.getEtc1());

        MspSaleSubsdMstDto mspSaleSubsdMstDto = new MspSaleSubsdMstDto();
        mspSaleSubsdMstDto.setPrdtId(prdtId);
        mspSaleSubsdMstDto.setSalePlcyCd(mspSalePlcyMstDto.getSalePlcyCd());
        mspSaleSubsdMstDto.setPlcySctnCd(mspSalePlcyMstDto.getPlcySctnCd());
        mspSaleSubsdMstDto.setOldYn(oldYn);
        mspSaleSubsdMstDto.setOrgnId(orgnId);
        mspSaleSubsdMstDto.setOperType(operType);//가입유형
        mspSaleSubsdMstDto.setAgrmTrm(instNom);//입력받은 할부기간을 약정기간으로 세팅한다.
        mspSaleSubsdMstDto.setRateCd(rateCd);//요금제코드
        mspSaleSubsdMstDto.setNoArgmYn(noArgmYn);
        mspSaleSubsdMstDto.setForFrontFastYn(""); //성능 개선을 위한 최소 컬럼을 호출하기 위한 처리 SQL 호출을 위한 flag
        mspSaleSubsdMstDto.setOnOffType(StringUtils.isNotEmpty(onOffType) ? onOffType : ""); //프로모션 조회 필요 컬럼

        BigDecimal instRate = mspSalePlcyMstDto.getInstRate();       //정책정보의 할부수수료값
        MspSaleSubsdMstDto mspSaleSubsdMst = mspDao.getMspSaleSubsdMst(mspSaleSubsdMstDto);

        if (mspSaleSubsdMst != null) {
            mspSaleSubsdMst.setInstRate(instRate);
            mspSaleSubsdMst.setDefaultInstRate(defaultInstRate);
            mspSaleSubsdMst.setModelMonthly(Integer.parseInt(modelMonthly));
        }

        return mspSaleSubsdMst;
    }

    public String getMspSubStatus(String contractNum) {
        return mspDao.getMspSubStatus(contractNum);
    }
}
