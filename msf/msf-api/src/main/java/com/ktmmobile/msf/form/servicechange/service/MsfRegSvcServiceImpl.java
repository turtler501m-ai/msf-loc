
package com.ktmmobile.msf.form.servicechange.service;

import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ktmmobile.msf.form.servicechange.dao.RegSvcDao;
import com.ktmmobile.msf.form.servicechange.dto.McpRegServiceDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpAddSvcInfoParamDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpMoscRegSvcCanChgInVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.system.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.msf.system.common.service.FCommonSvc;
import com.ktmmobile.msf.system.common.util.DateTimeUtil;
import com.ktmmobile.msf.system.common.util.StringMakerUtil;
import com.ktmmobile.msf.system.common.util.StringUtil;

@Service
public class MsfRegSvcServiceImpl implements MsfRegSvcService{

    private static Logger logger = LoggerFactory.getLogger(MsfRegSvcService.class);

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private FCommonSvc fCommonSvc;

//    @Autowired
//    private RateAdsvcGdncService rateAdsvcGdncService;

    @Autowired
    private RegSvcDao regSvcDao;

    @Autowired
    private MsfMypageUserService mypageUserService;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /**
     * 부가서비스 신청 목록 조회
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     */

    @Override
    public List<String> selectAddSvcInfoDto(String ncn, String ctn, String custId) {
        //이용중인 부가서비스 조회
        List<String> useSocList = new ArrayList<String>(); //사용중인 부가서비스 SOC 리스트
        try {
            MpAddSvcInfoDto vo = mPlatFormService.getAddSvcInfoDto(ncn, ctn, custId);
            if (!vo.isSuccess()) {//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                throw new McpCommonException(COMMON_EXCEPTION);
            }

            if (vo != null) {
                List<MpSocVO> mSocvoList = vo.getList();

                if (mSocvoList != null ) {
                    for (MpSocVO mSocvo : mSocvoList) {
                        useSocList.add(mSocvo.getSoc());
                    }
                }
            }
        } catch (SocketTimeoutException e1) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e1) {
            throw new McpCommonException(e1.getMessage());
        }

        return useSocList;
    }
   
    /**
     * 이용중인 부가서비스 조회 상세설명
     * @author bsj
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @return
     */

//    @Override
//    public RateAdsvcGdncBasXML selectAddSvcDtl(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO) {
//
//        List<RateAdsvcGdncProdRelXML> rtnXmlList = rateAdsvcGdncService.getRateAdsvcGdncProdRelXml();
//
//        RateAdsvcGdncBasXML rateAdsvcGdncBasXML = new RateAdsvcGdncBasXML();
//        RateAdsvcCtgBasDTO dto = new RateAdsvcCtgBasDTO();
//
//        if(rtnXmlList != null && rtnXmlList.size() > 0) {
//            for(RateAdsvcGdncProdRelXML rtnList : rtnXmlList) {
//
//                if(rtnList.getRateAdsvcCd().equals(rateAdsvcCtgBasDTO.getRateAdsvcCd())) {
//                    int rateAdsvcGdncSeq = rtnList.getRateAdsvcGdncSeq();
//                    dto.setRateAdsvcGdncSeq(rateAdsvcGdncSeq);
//                    break;
//                }
//            }
//            rateAdsvcGdncBasXML = rateAdsvcGdncService.getRateAdsvcGdncBasXml(dto);
//        }
//
//        return rateAdsvcGdncBasXML;
//    }

    /**
     * 부가서비스 신청
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @param soc
     * @param ftrNewParam
     * @return
     * @throws SocketTimeoutException
     */

    @Override
    public MpRegSvcChgVO regSvcChg(String ncn, String ctn, String custId, String soc, String ftrNewParam) throws SocketTimeoutException {
        MpRegSvcChgVO res = new MpRegSvcChgVO();

        //x21 부가서비스 신청
        res = mPlatFormService.regSvcChg(ncn,ctn, custId,soc, ftrNewParam);
        return res;
    }

    /**
     * 이용중인 부가서비스 목록 조회
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     */
    /**
     * X20 -> X97로 변경
     * MpAddSvcInfoDto -> MpAddSvcInfoParamDto 변경
     * @author 김동혁
     * @Date : 2023.07.11
     */

    @Override
    public MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId) {
        return selectAddSvcList(ncn,ctn,custId,"");
    }

    @Override
    public MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId, String lstComActvDate) {
        return selectAddSvcList(ncn,ctn,custId,lstComActvDate);
    }

    private MpAddSvcInfoParamDto selectAddSvcList(String ncn, String ctn, String custId, String lstComActvDate) {

            MpAddSvcInfoParamDto vo = new MpAddSvcInfoParamDto();
            try {
                vo = mPlatFormService.getAddSvcInfoParamDto(ncn, ctn, custId);
                if (!vo.isSuccess()) {//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                    throw new McpCommonException(COMMON_EXCEPTION);
                }

                //부가서비스 해지 가능 여부 및 해지 문구 조회 - MSP_RATE_MST@DL_MSP -S-
                List<MpSocVO> mSocVoList = vo.getList();
                MspRateMstDto mspRateMstDto = null;

                if ( mSocVoList != null ) {
                    for (MpSocVO mSocVo : mSocVoList) {
                        mspRateMstDto = fCommonSvc.getMspRateMst(mSocVo.getSoc());
                        if (mspRateMstDto != null) {
                            mSocVo.setCanCmnt(StringUtil.NVL(mspRateMstDto.getCanCmnt(), "")); //해지 안내 문구
                          if(lstComActvDate.isEmpty()) {
                              mSocVo.setOnlineCanYn(StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "")); //해지 가능 여부
                          }else {
                              boolean isBlocked = DateTimeUtil.isBlocked(lstComActvDate, mspRateMstDto.getOnlineCanDay());
                              if (isBlocked) {
                                  mSocVo.setOnlineCanYn("N"); //해지 가능 여부
                              }else {
                                  mSocVo.setOnlineCanYn(StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "")); //해지 가능 여부
                              }
                          }
                        }
                        if(Constants.REG_SVC_CD_4.equals(mSocVo.getSoc())) { //요금할인 해지금지
                            mSocVo.setOnlineCanYn("N"); //해지 가능 여부
                        }
                    }
                }
                //부가서비스 해지 가능 여부 및 해지 문구 조회 - MSP_RATE_MST@DL_MSP -E-

            } catch (SocketTimeoutException e1) {
                throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
            } catch (SelfServiceException e1) {
                throw new McpCommonException(e1.getMessage());
            }
            return vo;
    }


    /**
     * 부가서비스 해지
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @param rateAdsvcCd
     * @return
     * @throws SocketTimeoutException
     */

    @Override
    public Map<String, Object> moscRegSvcCanChg(MyPageSearchDto searchVO, String rateAdsvcCd) throws SocketTimeoutException {
        Map<String, Object> rtnMap = new HashMap<String, Object>();

        try {
              //해지 신청 부가서비스 SOC 체크
            MspRateMstDto mspRateMstDto = fCommonSvc.getMspRateMst(rateAdsvcCd);

            if (mspRateMstDto != null) {
                String onlineCanYn = StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "");
                if (!onlineCanYn.equals("Y")) {
                    rtnMap.put("RESULT_CODE", "E");
                    rtnMap.put("message", ExceptionMsgConstant.NO_ONLINE_CAN_CHANGE_ADD);
                    return rtnMap;
                }
            } else {
                 rtnMap.put("RESULT_CODE", "E");
                 rtnMap.put("message", ExceptionMsgConstant.NO_EXSIST_RATE);
                 return rtnMap;
            }

            //부가서비스 해지
            //x38
            MpMoscRegSvcCanChgInVO vo = new MpMoscRegSvcCanChgInVO();
            vo =  mPlatFormService.moscRegSvcCanChg(searchVO.getNcn(),searchVO.getCtn(), searchVO.getCustId(),rateAdsvcCd);

            if (!vo.isSuccess()) {//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                throw new McpCommonException(COMMON_EXCEPTION);
            }

            rtnMap.put("RESULT_CODE", "S");


        } catch (SelfServiceException e1) {
            rtnMap.put("RESULT_CODE", "E");
            rtnMap.put("message", e1.getMessage());
            return rtnMap;
        }
        return rtnMap;
    }

    /**
     * 부가서비스 해지(prodHstSeq 포함)
     * @author 김동혁
     * @Date : 2023.08.02
     * @param searchVO
     * @param rateAdsvcCd
     * @return
     * @throws SocketTimeoutException
     */

    @Override
    public Map<String, Object> moscRegSvcCanChgSeq(MyPageSearchDto searchVO, String rateAdsvcCd, String prodHstSeq) throws SocketTimeoutException {
        Map<String, Object> rtnMap = new HashMap<String, Object>();

        try {
            //해지 신청 부가서비스 SOC 체크
            MspRateMstDto mspRateMstDto = fCommonSvc.getMspRateMst(rateAdsvcCd);

            if (mspRateMstDto != null) {
                String onlineCanYn = StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "");
                if (!onlineCanYn.equals("Y")) {
                    rtnMap.put("RESULT_CODE", "E");
                    rtnMap.put("message", ExceptionMsgConstant.NO_ONLINE_CAN_CHANGE_ADD);
                    return rtnMap;
                }
            } else {
                rtnMap.put("RESULT_CODE", "E");
                rtnMap.put("message", ExceptionMsgConstant.NO_EXSIST_RATE);
                return rtnMap;
            }

            //부가서비스 해지
            //x38
            MpMoscRegSvcCanChgInVO vo = new MpMoscRegSvcCanChgInVO();
            vo =  mPlatFormService.moscRegSvcCanChgSeq(searchVO.getNcn(),searchVO.getCtn(), searchVO.getCustId(),rateAdsvcCd, prodHstSeq);

            if (!vo.isSuccess()) {//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                throw new McpCommonException(COMMON_EXCEPTION);
            }

            rtnMap.put("RESULT_CODE", "S");


        } catch (SelfServiceException e1) {
            rtnMap.put("RESULT_CODE", "E");
            rtnMap.put("message", e1.getMessage());
            return rtnMap;
        }
        return rtnMap;
    }

    /**
       * 부가서비스 조회 구분(addDivCd : G = 일반, R = 로밍)
       * @author 김동혁
     * @Date : 2023.06.21
     * @param mpSocList
     * @param addDivCd
     * @return
     */
    public void getMpSocListByDiv(List<MpSocVO> mpSocList, String addDivCd) {
        if("".equals(addDivCd) || addDivCd == null) {
            return;
        }
        if(mpSocList == null || mpSocList.isEmpty()) {
            return;
        }
        Iterator<MpSocVO> iter = mpSocList.iterator();
        List<String> roamCdList = regSvcDao.getRoamCdList();

        while(iter.hasNext()) {
            MpSocVO mpSoc = iter.next();

            if(chkRemove(mpSoc.getSoc(), addDivCd, roamCdList)) {
                iter.remove();
            }
        }
    }

    /**
       * 부가서비스 조회 구분(addDivCd : G = 일반, R = 로밍)
       * @author 김동혁
     * @Date : 2023.06.21
     * @param mcpRegServiceList
     * @param addDivCd
     * @return
     */
    public void getMcpRegServiceListByDiv(List<McpRegServiceDto> mcpRegServiceList, String addDivCd) {
        if("".equals(addDivCd) || null == addDivCd) {
            return;
        }
        Iterator<McpRegServiceDto> iter = mcpRegServiceList.iterator();
        List<String> roamCdList = regSvcDao.getRoamCdList();

        while(iter.hasNext()) {
            McpRegServiceDto mcpRegService = iter.next();

            if(chkRemove(mcpRegService.getRateCd(), addDivCd, roamCdList)) {
                iter.remove();
            }
        }
    }

    /**
       * 부가서비스 리스트에서 제거할지 체크(addDivCd : G = 일반, R = 로밍)
       * @author 김동혁
     * @Date : 2023.06.21
     * @param soc
     * @param addDivCd
     * @param roamCdList
     * @return boolean
     */
    public boolean chkRemove(String soc, String addDivCd, List<String> roamCdList) {
        if("G".equals(addDivCd)) {
            for(String roamCd : roamCdList) {
                if(roamCd.equals(soc)) {
                    return true;
                }
            }
            return false;
        } else if("R".equals(addDivCd)) {
            for(String roamCd : roamCdList) {
                if(roamCd.equals(soc)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

//     /**
//      * 로밍 부가서비스 조회
//      * @author 김동혁
//      * @Date : 2023.06.21
//      * @param
//      * @return List<RateAdsvcCtgBasDTO>
//      */
//     public List<RateAdsvcCtgBasDTO> getRoamList(){
//            List<RateAdsvcCtgBasDTO> rtnList = new ArrayList<RateAdsvcCtgBasDTO>();
//            MapWrapper mapWrapper = rateAdsvcGdncService.getMapWrapper(); //
//
//            Map<String, ListXmlWrapper> prodMapXml = mapWrapper.getItem();
//            ListXmlWrapper prodListXml = prodMapXml.get("proList");
//            List<RateAdsvcGdncProdXML> prodList = prodListXml.getItem();
//
//            for(RateAdsvcGdncProdXML item : prodList) {
//                String addDivCd = item.getAddDivCd();
//
//                try {
//                    if(!"R".equals(addDivCd)) {
//                        continue;
//                    }
//
//                    RateAdsvcCtgBasDTO rateAdsvcCtgBas = new RateAdsvcCtgBasDTO();
//
//                    rateAdsvcCtgBas.setRateAdsvcCtgCd(item.getRateAdsvcCtgCd());
//                    rateAdsvcCtgBas.setRateAdsvcGdncSeq(item.getRateAdsvcGdncSeq());
//                    rateAdsvcCtgBas.setSortOdrg(item.getSortOdrg());
//                    rateAdsvcCtgBas.setUseYn(item.getUseYn());
//                    rateAdsvcCtgBas.setRateAdsvcImgNm(item.getRateAdsvcImgNm());
//                    rateAdsvcCtgBas.setRateAdsvcCtgImgNm(item.getRateAdsvcCtgImgNm());
//                    rateAdsvcCtgBas.setPstngStartDate(item.getPstngStartDate());
//                    rateAdsvcCtgBas.setPstngEndDate(item.getPstngEndDate());
//                    rateAdsvcCtgBas.setRateAdsvcCtgNm(item.getRateAdsvcCtgNm());
//                    rateAdsvcCtgBas.setRateAdsvcCtgBasDesc(item.getRateAdsvcCtgBasDesc());
//                    rateAdsvcCtgBas.setRateAdsvcCtgDtlDesc(item.getRateAdsvcCtgDtlDesc());
//                    rateAdsvcCtgBas.setDepthKey(item.getDepthKey());
//                    rateAdsvcCtgBas.setUpRateAdsvcCtgCd(item.getUpRateAdsvcCtgCd());
//                    rateAdsvcCtgBas.setRateAdsvcNm(item.getRateAdsvcNm());
//                    rateAdsvcCtgBas.setMmBasAmtDesc(item.getMmBasAmtDesc());
//                    rateAdsvcCtgBas.setMmBasAmtVatDesc(item.getMmBasAmtVatDesc());
//                    rateAdsvcCtgBas.setPromotionAmtDesc(item.getPromotionAmtDesc());
//                    rateAdsvcCtgBas.setPromotionAmtVatDesc(item.getPromotionAmtVatDesc());
//                    rateAdsvcCtgBas.setRelCnt(item.getRelCnt());
//                    rateAdsvcCtgBas.setAddDivCd(item.getAddDivCd());
//                    rateAdsvcCtgBas.setSelfYn(item.getSelfYn());
//                    rateAdsvcCtgBas.setFreeYn(item.getFreeYn());
//                    rateAdsvcCtgBas.setDateType(item.getDateType());
//                    rateAdsvcCtgBas.setUsePrd(item.getUsePrd());
//                    rateAdsvcCtgBas.setLineType(item.getLineType());
//                    rateAdsvcCtgBas.setLineCnt(item.getLineCnt());
//
//                    RateAdsvcGdncProdRelXML prodRel = rateAdsvcGdncService.getRateAdsvcGdncProdRel(rateAdsvcCtgBas);
//                    rateAdsvcCtgBas.setRateAdsvcCd(prodRel.getRateAdsvcCd());
//
//                    rtnList.add(rateAdsvcCtgBas);
//                } catch (Exception e) {
//                    logger.error("Exception e : {}", e.getMessage());
//                }
//            }
//
//            return rtnList;
//     }

     /**
      * 이용중인 상품 변경가능여부 판단
      * @author 김동혁
      * @Date : 2023.07.20
      * @param mpSocVo
      * @return String
      */
     public String getUpdateYn(MpSocVO mpSoc) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
            String today = formatter.format(new Date());
            int compare = mpSoc.getEffectiveDate().compareTo(today);
            if(compare > 0 && "Y".equals(mpSoc.getOnlineCanYn())) {
                return "Y"; //변경가능여부
            } else {
                return "N"; //변경가능여부
            }
     }

     /**
      * 시작일, 이용가능기간으로 종료일 계산
      * @author 김동혁
      * @Date : 2023.07.20
      * @param mpSoc
      * @param usePrd
      * @return String
      */
     public String getEndDttmUsePrd(MpSocVO mpSoc, String usePrd) {
         String endDttm = "";

         try {
             SimpleDateFormat dateForm = new SimpleDateFormat("yyyyMMddHHmmss");
             Calendar cal = Calendar.getInstance();
             cal.setTime(dateForm.parse(mpSoc.getStrtDt()));
             cal.add(Calendar.DATE, Integer.parseInt(usePrd) - 1);
             endDttm = dateForm.format(cal.getTime());
         } catch(ParseException e) {
             logger.error("ParseException e : {}", e.getMessage());
         }

         return endDttm;
     }

     /**
      * 계약번호로 회선번호 가져오기
      * flagMasking == True, 가운데 4자리 ****로 마스킹
      * flagMasking == False, 마스킹 X
      * @author 김동혁
      * @Date : 2023.07.24
      * @param ncn
      * @return String
      */
     public String getCtnByNcn(String ncn, boolean flagMasking) {

         if(StringUtil.isEmpty(ncn)){
             return null;
         }

         McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
         userCntrMngDto.setSvcCntrNo(ncn);

         RestTemplate restTemplate = new RestTemplate();
         McpUserCntrMngDto userDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);

         String ctn = (userDto == null) ? null : userDto.getCntrMobileNo();

         if(!StringUtil.isEmpty(ctn) && flagMasking){
             ctn = StringMakerUtil.getPhoneNum(ctn);
         }

         return ctn;
     }
}
