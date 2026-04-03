package com.ktmmobile.msf.form.servicechange.service;

import static com.ktmmobile.msf.rate.RateAdsvcGdncUtil.getRateAdsvcGdncPath;
import static com.ktmmobile.msf.system.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_EXCEPTION_LIST_SOC_CODE;
import static com.ktmmobile.msf.system.common.constants.Constants.KAKAO_SENDER_KEY;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ktmmobile.msf.form.servicechange.RateAdsvcGdncService;
import com.ktmmobile.msf.form.servicechange.dto.FarPricePlanResDto;
import com.ktmmobile.msf.form.servicechange.dto.MapWrapper;
import com.ktmmobile.msf.form.servicechange.dto.McpFarPriceDto;
import com.ktmmobile.msf.form.servicechange.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.form.servicechange.dto.RateAdsvcCtgBasDTO;
import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.system.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.system.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.msf.system.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscFarPriceChgDto;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscFarPriceResDto;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscWireUseTimeInfoRes;
import com.ktmmobile.msf.system.common.mplatform.dto.RegSvcChgRes;
import com.ktmmobile.msf.system.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpCommonXmlVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.system.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.msf.system.common.service.FCommonSvc;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.service.SmsSvc;
import com.ktmmobile.msf.system.common.util.DateTimeUtil;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;
import com.ktmmobile.msf.system.common.util.XmlBindUtil;

@Service
public class FarPricePlanServiceImpl implements FarPricePlanService{

    private static Logger logger = LoggerFactory.getLogger(FarPricePlanService.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    /** 파일업로드 경로*/
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    /** 파일업로드 접근가능한 웹경로 */
    @Value("${common.fileupload.web}")
    private String fileuploadWeb;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private RateAdsvcGdncService rateAdsvcGdncService;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private SfMypageSvc mypageService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private IpStatisticService ipStatisticService;

    @Autowired
    private SmsSvc smsSvc;

    /**
     * x20 요금제 불가능 체크
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     */

    @Override
    public String selectFarPricePlanYn(String ncn, String ctn, String custId) {

     //요금제 변경 불가능 socCode
       StringBuilder exceptionSocList = new StringBuilder("|");
       List<NmcpCdDtlDto> socCodeList = NmcpServiceUtils.getCodeList(GROUP_CODE_EXCEPTION_LIST_SOC_CODE);
       if (socCodeList != null) {
           for (NmcpCdDtlDto socCode : socCodeList) {
                   exceptionSocList.append(socCode.getDtlCd());
                   exceptionSocList.append( "|");
           }
       }

       String isPriceChange = "TRUE";

       //이용중인 부가서비스 조회
       MpAddSvcInfoDto getAddSvcInfo = new MpAddSvcInfoDto();
       try {
          getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(ncn, ctn, custId);
       } catch (SocketTimeoutException e) {
          throw new McpCommonException( COMMON_EXCEPTION);
       } catch (SelfServiceException e) {
          throw new McpCommonException( COMMON_EXCEPTION);
       }

       McpFarPriceDto mcpFarPriceDto = new McpFarPriceDto();

       if(getAddSvcInfo != null) {
           mcpFarPriceDto.setPromotionDcAmt(-getAddSvcInfo.getDiscountRate());
           //요금제 변경 불가능 socCode 확인
           for (MpSocVO socVo: getAddSvcInfo.getList()) {
               if (exceptionSocList.indexOf(socVo.getSoc()) > 0) {
                   isPriceChange = "FALSE";//요금제 변경 불가
                   break;
               }
           }
       }

       return isPriceChange;
    }

    /**
     * 요금제 안내 문구 xml 조회해서 안내문구
     * @author bsj
     * @Date : 2021.12.30
     * @param mcpFarPriceDto
     * @return
     */

    @Override
    public FarPricePlanResDto getFarPricePlanWrapper(McpFarPriceDto mcpFarPriceDto) {
        String fPer = File.separator;
        String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
        FarPricePlanResDto farPricePlanResDto = new FarPricePlanResDto();

        try {

            String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_PROD_REL.xml";
            File file1 = new File(realFilePath);

            List<RateAdsvcGdncProdRelXML> rateAdsvcGdncProdRelList = XmlBindUtil.bindListFromXml(RateAdsvcGdncProdRelXML.class, file1);

            int rateAdsvcGdncSeq = 0;
            for(RateAdsvcGdncProdRelXML item : rateAdsvcGdncProdRelList) {
                  if(item.getRateAdsvcGdncSeq() != 0) {
                      if(mcpFarPriceDto.getPrvRateCd() != null) {
                          if(mcpFarPriceDto.getPrvRateCd().equals(item.getRateAdsvcCd())) {
                              rateAdsvcGdncSeq  =item.getRateAdsvcGdncSeq();
                              break;
                          }
                      }
                  }
            }

            // 요금제부가서비스안내일련번호 세팅 (일련번호 없으면 0)
            farPricePlanResDto.setRateAdsvcGdncSeq(rateAdsvcGdncSeq);

            if(rateAdsvcGdncSeq != 0) {
                String realFilePath2 = realDir + fPer + "NMCP_RATE_ADSVC_BNFIT_GDNC_DTL_" + rateAdsvcGdncSeq + ".xml";
                File file2 = new File(realFilePath2);
                List<RateAdsvcBnfitGdncDtlXML> rateAdsvcBnfitGdncDtlXML = XmlBindUtil.bindListFromXml(RateAdsvcBnfitGdncDtlXML.class, file2);

                if( rateAdsvcBnfitGdncDtlXML != null) {
                    String itemCd = "";
                    String itemDesc = "";
                    for(RateAdsvcBnfitGdncDtlXML item2 : rateAdsvcBnfitGdncDtlXML) {
                        itemCd = StringUtil.NVL(item2.getRateAdsvcBnfitItemCd(),"");
                        itemDesc = StringUtil.NVL(item2.getRateAdsvcItemDesc(), "");
                        if("RATEBE01".equals(itemCd)){      //요금제제공량(데이터(노출문구))
                            farPricePlanResDto.setRateAdsvcLteDesc(itemDesc);
                        }else if("RATEBE02".equals(itemCd)){      //요금제제공량(음성(노출문구))
                            farPricePlanResDto.setRateAdsvcCallDesc(itemDesc);
                        }else if("RATEBE03".equals(itemCd)){      //sms음성제공
                            farPricePlanResDto.setRateAdsvcSmsDesc(itemDesc);
                        }
                    }
                }
            }

        } catch(JAXBException e) {
            logger.debug(e.toString());
        } catch(Exception e) {
            logger.debug(e.toString());
        }


        return farPricePlanResDto;
    }

    /**
     * X89 요금상품예약 변경조회
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @return
     */

    @Override
    public MoscFarPriceResDto doFarPricePlanRsrvSearch(MyPageSearchDto searchVO) {

        MoscFarPriceResDto moscFarPriceResDto = new MoscFarPriceResDto();

        try {
             moscFarPriceResDto = mPlatFormService.doFarPricePlanRsrvSearch(searchVO.getCustId(), searchVO.getNcn(), searchVO.getCtn());
        } catch (SelfServiceException e) {
            moscFarPriceResDto.setResultCode("E001");
            //throw new McpCommonException(e.getMessage());
        } catch (SocketTimeoutException e){
            moscFarPriceResDto.setResultCode("E002");
            //throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        }

        return moscFarPriceResDto;
    }

    /**
     * x90 요금제 예약 취소
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @return
     */

    @Override
    public MpCommonXmlVO doFarPricePlanRsrvCancel(MyPageSearchDto searchVO) {

        MpCommonXmlVO vo = new MpCommonXmlVO();

        try {

            McpIpStatisticDto res = new McpIpStatisticDto();
            res.setSvcCntrNo(searchVO.getNcn());
            res.setMobileNo(searchVO.getCtn());

            //x90
            vo = mPlatFormService.doFarPricePlanRsrvCancel(searchVO.getCustId(), searchVO.getNcn(),searchVO.getCtn());
            if(vo.isSuccess()) {
                String rateResChgSeq = ipStatisticService.selectRateResChgAccessTrace(res);
                if(rateResChgSeq != null) {
                    ipStatisticService.deleteRateResChgAccessTrace(rateResChgSeq);
                }
            }
        } catch (SelfServiceException e) {
            throw new McpCommonException(e.getMessage());
        } catch (SocketTimeoutException e){
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        }

        return vo;
    }

    /**
     * x88 요금상품 예약 변경
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @param soc
     * @param ftrNewParam
     * @param ip
     * @param userId
     * @return
     */

    @Override
    public MoscFarPriceChgDto doFarPricePlanRsrvChg(MyPageSearchDto  searchVO
            ,String soc, String ftrNewParam, String ip, String userId , String befChgRateCd , String befChgRateAmnt)  {

        MoscFarPriceChgDto res = new MoscFarPriceChgDto();

        try {

            //x88
            res = mPlatFormService.doFarPricePlanRsrvChg(searchVO.getCustId(), searchVO.getNcn(), searchVO.getCtn()
                    ,soc,ftrNewParam);

            String prcsMdlInd = "GC" + DateTimeUtil.getFormatString("yyMMddHHmmss");
            String today = DateTimeUtil.getShortDateString().replaceAll("-", "");
            String chgapyDate =DateTimeUtil.addMonths(today, +1);
            String chgDate = chgapyDate.substring(0,6);

            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setSvcCntrNo(searchVO.getNcn());//서비스계약번호
            mcpIpStatisticDto.setMobileNo(searchVO.getCtn());
            mcpIpStatisticDto.setUserid(userId);
            mcpIpStatisticDto.setEventCode("X88");
            mcpIpStatisticDto.setResChgRateCd(soc);
            mcpIpStatisticDto.setResChgDate(today);
            mcpIpStatisticDto.setResChgApyDate(chgDate+"01");
            mcpIpStatisticDto.setCretIp(ip);
            mcpIpStatisticDto.setGlobalNo(res.getGlobalNo());
            mcpIpStatisticDto.setTrtMdlDiv(prcsMdlInd);
            mcpIpStatisticDto.setParam("");
            mcpIpStatisticDto.setBatchRsltCd("");
            mcpIpStatisticDto.setBefChgRateCd(befChgRateCd);
            int befChgRateAmntInt = 0;
            try {
                befChgRateAmntInt = Integer.parseInt(befChgRateAmnt);
            } catch(NumberFormatException e) {
                befChgRateAmntInt = 0;
            } catch (Exception e) {
                befChgRateAmntInt = 0;
            }
            mcpIpStatisticDto.setBefChgRateAmnt(befChgRateAmntInt);

            if(res.isSuccess() && "Y".equals(res.getRsltYn()) ) {

                logger.error("[요금제 예약변경 신청 로그]:"  + mcpIpStatisticDto.getSvcCntrNo()+"_"
                                                           + mcpIpStatisticDto.getMobileNo()+"_"
                                                           + mcpIpStatisticDto.getUserid()+"_"
                                                           + mcpIpStatisticDto.getBefChgRateCd()+"_"
                                                           + mcpIpStatisticDto.getResChgRateCd()+"_"
                                                           + DateTimeUtil.getFormatString("yyyyMMddHHmmss"));

                //요금제 예약변경 이력저장
                ipStatisticService.insertRateResChgAccessTrace(mcpIpStatisticDto);
            } else {
                McpIpStatisticDto mcpIpStatisticDto2 =  new McpIpStatisticDto();
                mcpIpStatisticDto2.setPrcsMdlInd("X88 ERROR");
                mcpIpStatisticDto2.setTrtmRsltSmst(searchVO.getNcn());
                mcpIpStatisticDto2.setParameter("NCN["+searchVO.getNcn()+"]CTN[" +searchVO.getCtn() +"]USERID["+ userId+"]ResChgRateCd["+ soc+"]");
                mcpIpStatisticDto2.setPrcsSbst("결과 실패 : RsltYn[" +res.getRsltYn() +"]Success[" +res.isSuccess() +"]");

                ipStatisticService.insertAdminAccessTrace(mcpIpStatisticDto2);
            }

        } catch (SocketTimeoutException e){
            McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("X88 ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(searchVO.getNcn());
            mcpIpStatisticDto.setParameter("NCN["+searchVO.getNcn()+"]CTN[" +searchVO.getCtn() +"]USERID["+ userId+"]ResChgRateCd["+ soc+"]");
            mcpIpStatisticDto.setPrcsSbst(e.getMessage());

            ipStatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (ParseException e) {
            McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("X88 ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(searchVO.getNcn());
            mcpIpStatisticDto.setParameter("NCN["+searchVO.getNcn()+"]CTN[" +searchVO.getCtn() +"]USERID["+ userId+"]ResChgRateCd["+ soc+"]");
            mcpIpStatisticDto.setPrcsSbst(e.getMessage());
            ipStatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);

        } catch (Exception e) {
            McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("X88 ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(searchVO.getNcn());
            mcpIpStatisticDto.setParameter("NCN["+searchVO.getNcn()+"]CTN[" +searchVO.getCtn() +"]USERID["+ userId+"]ResChgRateCd["+ soc+"]");
            mcpIpStatisticDto.setPrcsSbst(e.getMessage());
            ipStatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            throw new McpCommonException(COMMON_EXCEPTION);
        }

        return res;
    }

    /**
     * 요금제 변경 첫번째 카테고리 조회
     * @author bsj
     * @Date : 2021.12.30
     * @return
     */

    @Override
    public MapWrapper getCtgMapWrapper() {
        String fPer = File.separator;
        String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
        MapWrapper mapWrapper = null;

        try {
            String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_CTG_BAS.xml";
            mapWrapper = XmlBindUtil.bindFromXml(MapWrapper.class, realFilePath);
        } catch(JAXBException e) {
            throw new McpCommonException(e.getMessage());
        } catch(Exception e) {
            throw new McpCommonException(e.getMessage());
        }

        return mapWrapper;
    }

    /**
     * 83.회선 사용기간조회
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @return
     */

    @Override
    public MoscWireUseTimeInfoRes moscWireUseTimeInfo(String ncn, String ctn, String custId) {

        MoscWireUseTimeInfoRes moscWireUseTimeInfoRes = new MoscWireUseTimeInfoRes();
        try {
            moscWireUseTimeInfoRes = mPlatFormService.moscWireUseTimeInfo(ncn, ctn, custId);
        } catch (SocketTimeoutException e) {
            throw new McpCommonJsonException("9997",SOCKET_TIMEOUT_EXCEPTION);
        }
        return moscWireUseTimeInfoRes;
    }

    /**
     * m전산 요금제목록가입할수 있는 요금제 목록 조회
     * @author bsj
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @param rateCd
     * @return
     * @throws JAXBException
     */

    @Override
    public List<RateAdsvcCtgBasDTO> selectFarPricePlanList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO, String rateCd) throws JAXBException, IOException {

        List<RateAdsvcCtgBasDTO> prodList = new ArrayList<RateAdsvcCtgBasDTO>();

        //요금제 상세코드 조회
        String fPer = File.separator;
        String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
        String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_PROD_REL.xml";
        File file1 = new File(realFilePath);

        List<RateAdsvcGdncProdRelXML> rateAdsvcGdncProdRelList = XmlBindUtil.bindListFromXml(RateAdsvcGdncProdRelXML.class, file1);

        // m전산 요금제 목록
        List<McpFarPriceDto> farPricePlanList = mypageService.selectFarPricePlanList(rateCd);
        List<RateAdsvcCtgBasDTO> prodXmlList = rateAdsvcGdncService.getProdXmlList(rateAdsvcCtgBasDTO);

        if(!prodXmlList.isEmpty()) {
            for(RateAdsvcGdncProdRelXML rateProdList : rateAdsvcGdncProdRelList) { //어드민에 요금제 상세코드
                    if(rateProdList.getRateAdsvcGdncSeq() != 0) {
                        break;
                    }
              }

             for( RateAdsvcCtgBasDTO item : prodXmlList) {
                 for(RateAdsvcGdncProdRelXML rtn : rateAdsvcGdncProdRelList) {
                     if(rtn.getRateAdsvcGdncSeq() == item.getRateAdsvcGdncSeq()) {
                         for(McpFarPriceDto dto : farPricePlanList ) {
                             if(dto.getNxtRateCd().equals(rtn.getRateAdsvcCd())) {
                                 RateAdsvcCtgBasDTO rateAdsvcCtgBas = new RateAdsvcCtgBasDTO();

                                 if("LOCAL".equals(serverName)) {
                                    //테스트
                                    rtn.setRateAdsvcCd("PL213M180");

                                 }
                                 //할인 금액 조회
                                 MspSaleSubsdMstDto rtnRateInfo = getRateInfo(rtn.getRateAdsvcCd());


                                 int promotionDcAmt = 0;
                                 if (rtnRateInfo != null) {
                                     promotionDcAmt = rtnRateInfo.getPromotionDcAmt();
                                 }

                                 if (promotionDcAmt > 0) {
                                     int payMnthChargeAmt = rtnRateInfo.getPayMnthChargeAmt();  //getPayMnthChargeAmt
                                     DecimalFormat decFormat = new DecimalFormat("###,###");

                                     String strPayMnthChargeAmt = decFormat.format(payMnthChargeAmt);

                                     rateAdsvcCtgBas.setPromotionAmtVatDesc(strPayMnthChargeAmt);  //프로모션 요금
                                 } else {
                                     rateAdsvcCtgBas.setPromotionAmtVatDesc("");
                                 }

                                 rateAdsvcCtgBas.setRateAdsvcCtgCd(item.getRateAdsvcCtgCd());
                                 rateAdsvcCtgBas.setRateAdsvcGdncSeq(item.getRateAdsvcGdncSeq());
                                 rateAdsvcCtgBas.setSortOdrg(item.getSortOdrg());
                                 rateAdsvcCtgBas.setUseYn(item.getUseYn());
                                 rateAdsvcCtgBas.setPstngStartDate(item.getPstngStartDate());
                                 rateAdsvcCtgBas.setPstngEndDate(item.getPstngEndDate());
                                 rateAdsvcCtgBas.setRateAdsvcCtgNm(item.getRateAdsvcCtgNm());
                                 rateAdsvcCtgBas.setRateAdsvcCtgBasDesc(item.getRateAdsvcCtgBasDesc());
                                 rateAdsvcCtgBas.setRateAdsvcCtgDtlDesc(item.getRateAdsvcCtgDtlDesc());
                                 rateAdsvcCtgBas.setDepthKey(item.getDepthKey());
                                 rateAdsvcCtgBas.setUpRateAdsvcCtgCd(item.getUpRateAdsvcCtgCd());
                                 rateAdsvcCtgBas.setRateAdsvcNm(item.getRateAdsvcNm());
                                 rateAdsvcCtgBas.setMmBasAmtDesc(item.getMmBasAmtDesc());
                                 rateAdsvcCtgBas.setMmBasAmtVatDesc(item.getMmBasAmtVatDesc());
                                 rateAdsvcCtgBas.setPromotionAmtDesc(item.getPromotionAmtDesc());
                                 rateAdsvcCtgBas.setRelCnt(item.getRelCnt());
                                 rateAdsvcCtgBas.setRateAdsvcCd(rtn.getRateAdsvcCd());
                                 prodList.add(rateAdsvcCtgBas);
                             }
                         }
                     }
                 }
             }
        }

        return prodList;
    }

    /**
     * 이용중인 부가서비스 조회 체크로직
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @param toSocCode
     * @param nowPriceSocCode
     * @param paraAlterTrace
     * @return
     * @throws InterruptedException
     */

    @Override
    public Map<String, Object> getAddSvcInfoDto(MyPageSearchDto searchVO, String  toSocCode , String  nowPriceSocCode
            ,McpServiceAlterTraceDto paraAlterTrace) throws InterruptedException {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //이용중인 부가서비스 조회
       MpAddSvcInfoDto getAddSvcInfo = new MpAddSvcInfoDto();
       try {
           getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
       } catch (SocketTimeoutException e) {
           rtnMap.put("RESULT_CODE", "-1");
           rtnMap.put("RESULT_MSG", SOCKET_TIMEOUT_EXCEPTION);
       } catch (SelfServiceException e) {
           rtnMap.put("RESULT_CODE", "-2");
           rtnMap.put("RESULT_MSG", ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
       }
       String prcsMdlInd = "GC" + DateTimeUtil.getFormatString("yyMMddHHmmss");

       List<McpUserCntrMngDto> closeSubList = null ;
       closeSubList = mypageService.getCloseSubList(searchVO.getContractNum());

       McpServiceAlterTraceDto serviceAlterTrace = new McpServiceAlterTraceDto();
       serviceAlterTrace.setNcn(searchVO.getNcn());
       serviceAlterTrace.setContractNum(searchVO.getContractNum());
       serviceAlterTrace.setSubscriberNo(searchVO.getCtn());
       serviceAlterTrace.setPrcsMdlInd(prcsMdlInd);
       serviceAlterTrace.settSocCode(toSocCode);
       serviceAlterTrace.setaSocCode(nowPriceSocCode);
       serviceAlterTrace.settSocAmnt(paraAlterTrace.gettSocAmnt());
       serviceAlterTrace.setaSocAmnt(paraAlterTrace.getaSocAmnt());


        /**
         *  방어 로직
         *  최근 60분 내 요금제 변경 성공 이력 확인
        */
        if (0 < mypageService.checkAllreadPlanchgCount(serviceAlterTrace) ) {
            rtnMap.put("RESULT_MSG", "요금제 변경이 되어 있습니다. <br/>잠시 후에 요금제 확인 하시기 바랍니다.[001]");
            return rtnMap;
        }


       McpServiceAlterTraceDto serviceAlterTraceSub = new McpServiceAlterTraceDto();
       serviceAlterTraceSub.setNcn(searchVO.getNcn());
       serviceAlterTraceSub.setContractNum(searchVO.getContractNum());
       serviceAlterTraceSub.setSubscriberNo(searchVO.getCtn());
       serviceAlterTraceSub.setPrcsMdlInd(prcsMdlInd);


       for (McpUserCntrMngDto closeSubInfo : closeSubList) {
           // 부가 서비스 해지 처리
           //MSP_SOCFAIL_PROC_MST(요금제셀프변경실패관리TABLE) 저장후 종료 처리
           for (MpSocVO socVo: getAddSvcInfo.getList()) {
               //부가 서비스 가입여부 확인
               if (socVo.getSoc().equals(closeSubInfo.getSocCode())) {
                   RegSvcChgRes regSvcCanChgNe = null;

                   /*
                    * 1. 재처리는 ESB연동오류 (ITL_SYS_E0001) 에 한정한다.
                      2. 재처리는 1회 한정한다
                      3. 재처리에는 3초 gap time을 둔다.
                      4. 재처리에 대한 이력은 이력테이블에 신규로 insert 하며, 기존 이력에update 하지는않는다.
                   */
                   String strParameter =  "["+closeSubInfo.getSocNm()+"]";
                   for (int reTryCount = 0; reTryCount < 2 ; reTryCount++) {
                       if (regSvcCanChgNe == null  ) {
                           regSvcCanChgNe = mPlatFormService.moscRegSvcCanChgNeTrace(searchVO, closeSubInfo.getSocCode()  );
                       } else if ("ITL_SYS_E0001".equals(regSvcCanChgNe.getResultCode())) {
                           Thread.sleep(3000);
                           //이력 저장
                           serviceAlterTraceSub.setEventCode("X38");
                           serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
                           serviceAlterTraceSub.settSocCode(closeSubInfo.getSocCode());
                           serviceAlterTraceSub.setaSocCode("");
                           serviceAlterTraceSub.setParameter(strParameter);
                           serviceAlterTraceSub.setGlobalNo(regSvcCanChgNe.getGlobalNo());
                           serviceAlterTraceSub.setRsltCd(regSvcCanChgNe.getResultCode());
                           serviceAlterTraceSub.setPrcsSbst(regSvcCanChgNe.getSvcMsg());
                           mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
                           regSvcCanChgNe = mPlatFormService.moscRegSvcCanChgNeTrace(searchVO, closeSubInfo.getSocCode()  );
                       }
                   }

                   if(!regSvcCanChgNe.isSuccess()) {
                       rtnMap.put("RESULT_CODE", regSvcCanChgNe.getResultCode());
                       rtnMap.put("RESULT_MSG", regSvcCanChgNe.getSvcMsg());

                       MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID);
                       //smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), searchVO.getCtn(), mspSmsTemplateMstDto.getText(),mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), KAKAO_SENDER_KEY);
                       smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), searchVO.getCtn(), mspSmsTemplateMstDto.getText(),
                               mspSmsTemplateMstDto.getCallback(),mspSmsTemplateMstDto.getkTemplateCode(),
                               KAKAO_SENDER_KEY, String.valueOf(Constants.SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID));

                       //이력 저장
                       serviceAlterTraceSub.setEventCode("X38");
                       serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
                       serviceAlterTraceSub.settSocCode(closeSubInfo.getSocCode());
                       serviceAlterTraceSub.setaSocCode("");
                       serviceAlterTraceSub.setParameter(strParameter);
                       serviceAlterTraceSub.setGlobalNo(regSvcCanChgNe.getGlobalNo());
                       serviceAlterTraceSub.setRsltCd(regSvcCanChgNe.getResultCode());
                       serviceAlterTraceSub.setPrcsSbst(regSvcCanChgNe.getSvcMsg());
                       mypageService.insertServiceAlterTrace(serviceAlterTraceSub);

                       //결과 이력 저장
                       serviceAlterTrace.setEventCode("FIN");
                       serviceAlterTrace.setTrtmRsltSmst("FAIL");
                       serviceAlterTrace.setParameter("부가서비스 해지 실패");
                       mypageService.insertServiceAlterTrace(serviceAlterTrace);

                       //M 전산 이력 저장
                       serviceAlterTrace.setSuccYn("N");
                       mypageService.insertSocfailProcMst(serviceAlterTrace);

                       return rtnMap;
                   } else {
                       //이력 저장
                       serviceAlterTraceSub.setEventCode("X38");
                       serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
                       serviceAlterTraceSub.settSocCode(closeSubInfo.getSocCode());
                       serviceAlterTraceSub.setaSocCode("");
                       serviceAlterTraceSub.setParameter(strParameter);
                       serviceAlterTraceSub.setGlobalNo(regSvcCanChgNe.getGlobalNo());
                       serviceAlterTraceSub.setRsltCd("0000");
                       serviceAlterTraceSub.setPrcsSbst(regSvcCanChgNe.getSvcMsg());
                       mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
                   }
                   break;
               }
           }
       }

       //2.요금제 변경 처리
       RegSvcChgRes regSvcChgSelf = null;
       for (int reTryCount = 0; reTryCount < 2 ; reTryCount++) {
           if (regSvcChgSelf == null  ) {
               regSvcChgSelf = mPlatFormService.farPricePlanChgNeTrace( searchVO, toSocCode );
           } else if ("ITL_SYS_E0001".equals(regSvcChgSelf.getResultCode())) {
               Thread.sleep(3000);
               //이력 저장
               serviceAlterTraceSub.setEventCode("X19");
               serviceAlterTraceSub.setTrtmRsltSmst("요금제변경");
               serviceAlterTraceSub.settSocCode(toSocCode);
               serviceAlterTraceSub.setaSocCode(nowPriceSocCode);
               serviceAlterTraceSub.setGlobalNo(regSvcChgSelf.getGlobalNo());
               serviceAlterTraceSub.setRsltCd(regSvcChgSelf.getResultCode());
               serviceAlterTraceSub.setPrcsSbst(regSvcChgSelf.getSvcMsg());
               mypageService.insertServiceAlterTrace(serviceAlterTraceSub);

               regSvcChgSelf = mPlatFormService.farPricePlanChgNeTrace( searchVO, toSocCode );
           }
       }

       if(regSvcChgSelf.isSuccess()) {
           rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

           //이력 저장
           serviceAlterTraceSub.setEventCode("X19");
           serviceAlterTraceSub.setTrtmRsltSmst("요금제변경");
           serviceAlterTraceSub.settSocCode(toSocCode);
           serviceAlterTraceSub.setaSocCode(nowPriceSocCode);
           serviceAlterTraceSub.setGlobalNo(regSvcChgSelf.getGlobalNo());
           serviceAlterTraceSub.setRsltCd("0000");
           serviceAlterTraceSub.setPrcsSbst(regSvcChgSelf.getSvcMsg());
           mypageService.insertServiceAlterTrace(serviceAlterTraceSub);

           //202312 wooki - MSP_DIS_APD(평생할인 부가서비스 기적용 대상) insert START
           String prmtId = mypageService.getChrgPrmtIdSocChg(toSocCode); //프로모션아이디 가져오기
           McpUserCntrMngDto apdDto = new McpUserCntrMngDto();
           apdDto.setPrmtId(prmtId); //위에서 조회한 prmtId set - prmtId는 있을수도 있고 없을수도 있음
           apdDto.setSocCode(toSocCode);
           apdDto.setContractNum(searchVO.getContractNum());
           mypageService.insertDisApd(apdDto); //MSP_DIS_APD insert
           //MSP_DIS_APD insert END

       } else {
           rtnMap.put("RESULT_CODE", regSvcChgSelf.getResultCode());
           rtnMap.put("RESULT_MSG", regSvcChgSelf.getSvcMsg());

           MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID);
           //smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), searchVO.getCtn(), mspSmsTemplateMstDto.getText(),mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), KAKAO_SENDER_KEY);
           smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), searchVO.getCtn(),mspSmsTemplateMstDto.getText(),
                   mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                   KAKAO_SENDER_KEY, String.valueOf(Constants.SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID));

           //이력 저장
           serviceAlterTraceSub.setEventCode("X19");
           serviceAlterTraceSub.setTrtmRsltSmst("요금제변경");
           serviceAlterTraceSub.settSocCode(toSocCode);
           serviceAlterTraceSub.setaSocCode(nowPriceSocCode);
           serviceAlterTraceSub.setGlobalNo(regSvcChgSelf.getGlobalNo());
           serviceAlterTraceSub.setRsltCd(regSvcChgSelf.getResultCode());
           serviceAlterTraceSub.setPrcsSbst(regSvcChgSelf.getSvcMsg());
           mypageService.insertServiceAlterTrace(serviceAlterTraceSub);

           //결과 이력 저장
           serviceAlterTrace.setEventCode("FIN");
           serviceAlterTrace.setTrtmRsltSmst("FAIL");
           serviceAlterTrace.setParameter("19. 요금 상품 변경 실패");
           mypageService.insertServiceAlterTrace(serviceAlterTrace);


           //M 전산 이력 저장
           serviceAlterTrace.setSuccYn("N");

           if ("ITL_SFC_E033".equals(regSvcChgSelf.getResultCode())) {
               //SRM23042026311 요금제 셀프변경 처리기준 변경 요청
               serviceAlterTrace.setProcMemo("가입중인 상품으로 요금제를 변경 하실 수 없습니다.");
               serviceAlterTrace.setProcYn("Y");
               serviceAlterTrace.setProcId("SYSTEM");
               Timestamp procDate = new Timestamp(System.currentTimeMillis()) ;
               serviceAlterTrace.setProcDate(procDate);

               //serviceAlterTraceSub.setRsltCd("0000");
               rtnMap.put("RESULT_MSG", "요금제 변경이 되어 있습니다. <br/>잠시 후에 요금제 확인 하시기 바랍니다.[002]");
           }

           mypageService.insertSocfailProcMst(serviceAlterTrace);

           return rtnMap;
       }

       //3.부가 서비스 가입 처리
       //3-1. 부가 서비스 가입 처리 해야 할 리스트 조회
       List<McpUserCntrMngDto> serviceInfoList = mypageService.getromotionDcList(toSocCode);
       int successCnt = 0 ;
       int failCnt = 0 ;


       for (McpUserCntrMngDto serviceInfo : serviceInfoList) {
           //3-2. 부가 서비스 가입
           //실패시.. ???  중간에 실패해도 계속 진행 해야 함...
           RegSvcChgRes regSvcInsert = null;
           String strParameter =  "["+serviceInfo.getSocNm()+"]["+serviceInfo.getSocPrice()+"]";

           for (int reTryCount = 0; reTryCount < 2 ; reTryCount++) {
               if (regSvcInsert == null  ) {
                   regSvcInsert = mPlatFormService.regSvcChgNeTrace(searchVO, serviceInfo.getSocCode(), "");
               } else if ("ITL_SYS_E0001".equals(regSvcInsert.getResultCode())) {
                   Thread.sleep(3000);
                   //이력 저장
                   serviceAlterTraceSub.setEventCode("X21");
                   serviceAlterTraceSub.setTrtmRsltSmst("부가서비스신청");
                   serviceAlterTraceSub.settSocCode(serviceInfo.getSocCode());
                   serviceAlterTraceSub.setaSocCode("");
                   serviceAlterTraceSub.setParameter(strParameter);
                   serviceAlterTraceSub.setGlobalNo(regSvcInsert.getGlobalNo());
                   serviceAlterTraceSub.setRsltCd(regSvcInsert.getResultCode());
                   serviceAlterTraceSub.setPrcsSbst(regSvcInsert.getSvcMsg());
                   mypageService.insertServiceAlterTrace(serviceAlterTraceSub);

                   regSvcInsert = mPlatFormService.regSvcChgNeTrace(searchVO, serviceInfo.getSocCode(), "");
               }
           }

           //이력 저장
           serviceAlterTraceSub.setEventCode("X21");
           serviceAlterTraceSub.setTrtmRsltSmst("부가서비스신청");
           serviceAlterTraceSub.settSocCode(serviceInfo.getSocCode());
           serviceAlterTraceSub.setaSocCode("");
           serviceAlterTraceSub.setParameter(strParameter);
           serviceAlterTraceSub.setGlobalNo(regSvcInsert.getGlobalNo());
           serviceAlterTraceSub.setRsltCd(regSvcInsert.getResultCode());
           serviceAlterTraceSub.setPrcsSbst(regSvcInsert.getSvcMsg());

           if(regSvcInsert.isSuccess()) {
               serviceAlterTraceSub.setRsltCd("0000");
               successCnt++ ;
           } else {
               failCnt++;
           }
           mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
       }

       if (successCnt == serviceInfoList.size()) {
           serviceAlterTrace.setEventCode("FIN");
           serviceAlterTrace.setTrtmRsltSmst("SUCCESS");
           serviceAlterTrace.setParameter("SCNT["+successCnt + "]FCNT["+failCnt+"]");
           mypageService.insertServiceAlterTrace(serviceAlterTrace);
           serviceAlterTrace.setSuccYn("Y");
           mypageService.insertSocfailProcMst(serviceAlterTrace);
       } else {
           serviceAlterTrace.setEventCode("FIN");
           serviceAlterTrace.setTrtmRsltSmst("FAIL");
           serviceAlterTrace.setParameter("SCNT["+successCnt + "]FCNT["+failCnt+"]");
           mypageService.insertServiceAlterTrace(serviceAlterTrace);

           //실패 이력 테이블 저장
           serviceAlterTrace.setSuccYn("N");
           mypageService.insertSocfailProcMst(serviceAlterTrace);
       }

       return rtnMap;
   }

    /**
     * 자회선 부가서비스 가입
     * x21 부가서비스 가입 함께쓰기
     * @author bsj
     * @Date : 2021.12.30
     * @param ncn
     * @param ctn
     * @param custId
     * @param soc
     * @param otpNo
     * @return
     * @throws SocketTimeoutException
     */

    @Override
    public RegSvcChgRes regSvcChgNe(String ncn, String ctn, String custId, String soc, String otpNo) throws SocketTimeoutException {
        RegSvcChgRes regSvcChgNe = null;
        try {
            regSvcChgNe = mPlatFormService.regSvcChgNe(ncn, ctn, custId, soc, otpNo);
        } catch (SelfServiceException e) {
            throw new McpCommonException(e.getMessage());
        }

        return regSvcChgNe;
    }

    /**
     * 가입가능한 요금제 M전산 + 어드민 XML 정리
     * @author bsj
     * @Date : 2021.12.30
     * @param outDto
     * @param list
     * @param rateCd
     * @return
     * @throws JAXBException
     * @throws IOException
     */

    @Override
    public List<RateAdsvcCtgBasDTO> getStrCtgXmlList(RateAdsvcCtgBasDTO outDto , List<RateAdsvcCtgBasDTO> list, String rateCd) throws JAXBException, IOException {

        String fPer = File.separator;
        String realDir = getRateAdsvcGdncPath(fileuploadBase, serverName);
        String realFilePath = realDir + fPer + "NMCP_RATE_ADSVC_GDNC_PROD_REL.xml";
        File file1 = new File(realFilePath);
        List<RateAdsvcCtgBasDTO> rtnList = new ArrayList<RateAdsvcCtgBasDTO>();

        List<RateAdsvcGdncProdRelXML> rateAdsvcGdncProdRelList = XmlBindUtil.bindListFromXml(RateAdsvcGdncProdRelXML.class, file1);
        //m전산 요금조회
        List<McpFarPriceDto> mcpFarPriceDtoList = mypageService.selectFarPricePlanList(rateCd);

        List<RateAdsvcCtgBasDTO> rtnList1 = new ArrayList<RateAdsvcCtgBasDTO>();

        for(RateAdsvcGdncProdRelXML item : rateAdsvcGdncProdRelList) {
            for(McpFarPriceDto dto : mcpFarPriceDtoList) {
                if(item.getRateAdsvcCd().equals(dto.getNxtRateCd())){
                    RateAdsvcCtgBasDTO rateAdsvcCtgBas1 = new RateAdsvcCtgBasDTO();
                    rateAdsvcCtgBas1.setRateAdsvcGdncSeq(item.getRateAdsvcGdncSeq());
                    rtnList1.add(rateAdsvcCtgBas1);
                }
            }
        }
        List<RateAdsvcCtgBasDTO> prodList = this.thisProdList(outDto);

        if(!rtnList1.isEmpty()) {
            for(RateAdsvcCtgBasDTO listBas : rtnList1) {
                for(RateAdsvcCtgBasDTO ctgList : prodList) {

                    if(listBas.getRateAdsvcGdncSeq() == ctgList.getRateAdsvcGdncSeq()) {
                        RateAdsvcCtgBasDTO rateAdsvcCtgBas = new RateAdsvcCtgBasDTO();

                        for(RateAdsvcCtgBasDTO sortList : list) {
                            if(ctgList.getRateAdsvcCtgCd().equals(sortList.getRateAdsvcCtgCd())){
                                rateAdsvcCtgBas.setRateAdsvcCtgCd(ctgList.getRateAdsvcCtgCd());
                                rateAdsvcCtgBas.setSortOdrg(ctgList.getSortOdrg());
                                rateAdsvcCtgBas.setUseYn(ctgList.getUseYn());
                                rateAdsvcCtgBas.setPstngStartDate(ctgList.getPstngStartDate());
                                rateAdsvcCtgBas.setPstngEndDate(ctgList.getPstngEndDate());
                                rateAdsvcCtgBas.setRateAdsvcCtgNm(ctgList.getRateAdsvcCtgNm());
                                rateAdsvcCtgBas.setRateAdsvcCtgBasDesc(ctgList.getRateAdsvcCtgBasDesc());
                                rateAdsvcCtgBas.setRateAdsvcCtgDtlDesc(ctgList.getRateAdsvcCtgDtlDesc());
                                rateAdsvcCtgBas.setRateAdsvcCtgImgNm(ctgList.getRateAdsvcCtgImgNm());
                                rateAdsvcCtgBas.setDepthKey(ctgList.getDepthKey());
                                rateAdsvcCtgBas.setRateAdsvcDivCd(ctgList.getRateAdsvcDivCd());
                                rateAdsvcCtgBas.setRelCnt(ctgList.getRelCnt());
                                rateAdsvcCtgBas.setSortOdrg2(sortList.getSortOdrg());
                                rtnList.add(rateAdsvcCtgBas);
                            }
                        }
                    }
                }
            }
        }

        // SortOdrg 값 기준으로 오름차순 정렬
        rtnList.sort((o1, o2) -> {
            int sortOdrg1 = 0;
            int sortOdrg2 = 0;

            sortOdrg1 = Integer.parseInt(o1.getSortOdrg2());
            sortOdrg2 = Integer.parseInt(o2.getSortOdrg2());

            return Integer.compare(sortOdrg1, sortOdrg2);
        });

        return rtnList;
    }

    /**
     * 가입가능한 요금제 + 어드민 요금제 체크
     * @author bsj
     * @Date : 2021.12.30
     * @param rateAdsvcCtgBasDTO
     * @param rateCd
     * @return
     */

    public List<RateAdsvcCtgBasDTO> thisProdList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO){
        List<RateAdsvcCtgBasDTO> rtnList = new ArrayList<RateAdsvcCtgBasDTO>();

        MapWrapper mapWrapper = rateAdsvcGdncService.getMapWrapper();

        Map<String, ListXmlWrapper> ctgMapXml = mapWrapper.getItem();
        ListXmlWrapper ctgListXml = ctgMapXml.get("proList");
        List<RateAdsvcGdncProdXML> ctgList = ctgListXml.getItem();

        for(RateAdsvcGdncProdXML item : ctgList) {
            int depthKey = item.getDepthKey();
            String upRateAdsvcDivCd = item.getUpRateAdsvcCtgCd();

            try {
                if( depthKey == rateAdsvcCtgBasDTO.getDepthKey()
                        && upRateAdsvcDivCd.equals(rateAdsvcCtgBasDTO.getRateAdsvcCtgCd())
                        && DateTimeUtil.isMiddleDateTime(item.getPstngStartDate(), item.getPstngEndDate())
                        ) {

                    RateAdsvcCtgBasDTO rateAdsvcCtgBas = new RateAdsvcCtgBasDTO();
                    rateAdsvcCtgBas.setRateAdsvcGdncSeq(item.getRateAdsvcGdncSeq());
                    rateAdsvcCtgBas.setRateAdsvcCtgCd(item.getRateAdsvcCtgCd());
                    rateAdsvcCtgBas.setSortOdrg(item.getSortOdrg());
                    rateAdsvcCtgBas.setUseYn(item.getUseYn());
                    rateAdsvcCtgBas.setPstngStartDate(item.getPstngStartDate());
                    rateAdsvcCtgBas.setPstngEndDate(item.getPstngEndDate());
                    rateAdsvcCtgBas.setRateAdsvcCtgNm(item.getRateAdsvcCtgNm());
                    rateAdsvcCtgBas.setRateAdsvcCtgBasDesc(item.getRateAdsvcCtgBasDesc());
                    rateAdsvcCtgBas.setRateAdsvcCtgDtlDesc(item.getRateAdsvcCtgDtlDesc());
                    rateAdsvcCtgBas.setRateAdsvcCtgImgNm(item.getRateAdsvcCtgImgNm());
                    rateAdsvcCtgBas.setDepthKey(item.getDepthKey());
                    rateAdsvcCtgBas.setRateAdsvcDivCd(item.getRateAdsvcDivCd());
                    rateAdsvcCtgBas.setRelCnt(item.getRelCnt());
                    rtnList.add(rateAdsvcCtgBas);
                }

            } catch (ParseException e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        return rtnList;
    }

    /**
     *  요금제 예약 변경 부가서비스 일괄 처리
     *  1.해당 회선이 실제 요금제 예약변경 대상 요금으로 요금제 변경 처리 되었는지 체크
     *  2.요금제별 배타관계 부가서비스 해지 처리??? (추가 제공하는 부가 서비스 해지)
     *  3.부가 서비스 가입 처리
     *  3-1. 부가 서비스 가입 처리 해야 할 리스트 조회(할인 금액)
     * @author bsj
     * @Date : 2021.12.30
     * @param ipStatistic
     * @return
     * @throws InterruptedException
     */

    @Override
    public HashMap<String, Object> batchResChangeAddPrdCall(McpIpStatisticDto ipStatistic , int instMnthAmtPric) throws InterruptedException {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 회선정보 조회
        McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
        userCntrMngDto.setSvcCntrNo(ipStatistic.getSvcCntrNo());

        RestTemplate restTemplate = new RestTemplate();
        McpUserCntrMngDto mcpUserCntrMngDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);

        MyPageSearchDto searchVO = new MyPageSearchDto();
        searchVO.setNcn(ipStatistic.getSvcCntrNo());
        searchVO.setCtn(ipStatistic.getMobileNo());
        searchVO.setCustId(mcpUserCntrMngDto.getCustId());

        //2.요금제별 배타관계 부가서비스 해지 처리??? (추가 제공하는 부가 서비스 해지)
        //이용중인 부가서비스 조회
        MpAddSvcInfoDto getAddSvcInfo = new MpAddSvcInfoDto();
        try {
            getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "-2");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
        }
        String prcsMdlInd = "BA_" + ipStatistic.getRateResChgSeq();

        List<McpUserCntrMngDto> closeSubList = null ;
        closeSubList = mypageService.getCloseSubList(ipStatistic.getSvcCntrNo());

        McpServiceAlterTraceDto serviceAlterTrace = new McpServiceAlterTraceDto();
        serviceAlterTrace.setNcn(ipStatistic.getSvcCntrNo());
        serviceAlterTrace.setContractNum(ipStatistic.getSvcCntrNo());
        serviceAlterTrace.setSubscriberNo(ipStatistic.getMobileNo());
        serviceAlterTrace.setPrcsMdlInd(prcsMdlInd);
        serviceAlterTrace.settSocCode(ipStatistic.getResChgRateCd());
        serviceAlterTrace.settSocAmnt(instMnthAmtPric);
        serviceAlterTrace.setaSocCode(ipStatistic.getBefChgRateCd());
        serviceAlterTrace.setaSocAmnt(ipStatistic.getBefChgRateAmnt());


        McpServiceAlterTraceDto serviceAlterTraceSub = new McpServiceAlterTraceDto();
        serviceAlterTraceSub.setNcn(ipStatistic.getSvcCntrNo());
        serviceAlterTraceSub.setContractNum(ipStatistic.getSvcCntrNo());
        serviceAlterTraceSub.setSubscriberNo(ipStatistic.getMobileNo());
        serviceAlterTraceSub.setPrcsMdlInd(prcsMdlInd);

        for (McpUserCntrMngDto closeSubInfo : closeSubList) {
            // 부가 서비스 해지 처리
            //MSP_SOCFAIL_PROC_MST(요금제셀프변경실패관리TABLE) 저장후 종료 처리
            for (MpSocVO socVo: getAddSvcInfo.getList()) {
                //부가 서비스 가입여부 확인
                if (socVo.getSoc().equals(closeSubInfo.getSocCode())) {
                    RegSvcChgRes regSvcCanChgNe = null;

                    /*
                     * 1. 재처리는 ESB연동오류 (ITL_SYS_E0001) 에 한정한다.
                       2. 재처리는 1회 한정한다
                       3. 재처리에는 3초 gap time을 둔다.
                       4. 재처리에 대한 이력은 이력테이블에 신규로 insert 하며, 기존 이력에update 하지는않는다.
                    */
                    String strParameter =  "["+closeSubInfo.getSocNm()+"]";
                    for (int reTryCount = 0; reTryCount < 2 ; reTryCount++) {
                        if (regSvcCanChgNe == null  ) {
                            regSvcCanChgNe = mPlatFormService.moscRegSvcCanChgNeTrace(searchVO, closeSubInfo.getSocCode()  );
                        } else if ("ITL_SYS_E0001".equals(regSvcCanChgNe.getResultCode())) {
                            Thread.sleep(3000);
                            //이력 저장
                            serviceAlterTraceSub.setEventCode("X38");
                            serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
                            serviceAlterTraceSub.settSocCode(closeSubInfo.getSocCode());
                            serviceAlterTraceSub.setaSocCode("");
                            serviceAlterTraceSub.setParameter(strParameter);
                            serviceAlterTraceSub.setGlobalNo(regSvcCanChgNe.getGlobalNo());
                            serviceAlterTraceSub.setRsltCd(regSvcCanChgNe.getResultCode());
                            serviceAlterTraceSub.setPrcsSbst(regSvcCanChgNe.getSvcMsg());
                            mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
                            regSvcCanChgNe = mPlatFormService.moscRegSvcCanChgNeTrace(searchVO, closeSubInfo.getSocCode()  );
                        }
                    }

                    if(!regSvcCanChgNe.isSuccess()) {
                        rtnMap.put("RESULT_CODE", "20000");
                        rtnMap.put("RESULT_MSG", regSvcCanChgNe.getSvcMsg());
                        //이력 저장
                        serviceAlterTraceSub.setEventCode("X38");
                        serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
                        serviceAlterTraceSub.settSocCode(closeSubInfo.getSocCode());
                        serviceAlterTraceSub.setaSocCode("");
                        serviceAlterTraceSub.setParameter(strParameter);
                        serviceAlterTraceSub.setGlobalNo(regSvcCanChgNe.getGlobalNo());
                        serviceAlterTraceSub.setRsltCd(regSvcCanChgNe.getResultCode());
                        serviceAlterTraceSub.setPrcsSbst(regSvcCanChgNe.getSvcMsg());
                        mypageService.insertServiceAlterTrace(serviceAlterTraceSub);

                        //결과 이력 저장
                        serviceAlterTrace.setEventCode("FIN");
                        serviceAlterTrace.setTrtmRsltSmst("FAIL");
                        serviceAlterTrace.setParameter("부가서비스 해지 실패");
                        mypageService.insertServiceAlterTrace(serviceAlterTrace);

                        serviceAlterTrace.setChgType("R");  //-- (즉시 : I , 예약 : R)
                        serviceAlterTrace.setSuccYn("N");
                        mypageService.insertSocfailProcMst(serviceAlterTrace);


                        return rtnMap;
                    } else {
                        //이력 저장
                        serviceAlterTraceSub.setEventCode("X38");
                        serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
                        serviceAlterTraceSub.settSocCode(closeSubInfo.getSocCode());
                        serviceAlterTraceSub.setaSocCode("");
                        serviceAlterTraceSub.setParameter(strParameter);
                        serviceAlterTraceSub.setGlobalNo(regSvcCanChgNe.getGlobalNo());
                        serviceAlterTraceSub.setRsltCd("20000");
                        serviceAlterTraceSub.setPrcsSbst(regSvcCanChgNe.getSvcMsg());
                        mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
                    }
                    break;
                }
            }
        }


        //3.부가 서비스 가입 처리
        //3-1. 부가 서비스 가입 처리 해야 할 리스트 조회(할인 금액)
        //3.부가 서비스 가입 처리
        //3-1. 부가 서비스 가입 처리 해야 할 리스트 조회
        List<McpUserCntrMngDto> serviceInfoList = mypageService.getromotionDcList(ipStatistic.getResChgRateCd());
        int successCnt = 0 ;
        int failCnt = 0 ;


        for (McpUserCntrMngDto serviceInfo : serviceInfoList) {
            //3-2. 부가 서비스 가입
            //실패시.. ???  중간에 실패해도 계속 진행 해야 함...
            RegSvcChgRes regSvcInsert = null;
            String strParameter =  "["+serviceInfo.getSocNm()+"]["+serviceInfo.getSocPrice()+"]";

            for (int reTryCount = 0; reTryCount < 2 ; reTryCount++) {
                if (regSvcInsert == null  ) {
                    regSvcInsert = mPlatFormService.regSvcChgNeTrace(searchVO, serviceInfo.getSocCode(), "");
                } else if ("ITL_SYS_E0001".equals(regSvcInsert.getResultCode())) {
                    Thread.sleep(3000);
                    //이력 저장
                    serviceAlterTraceSub.setEventCode("X21");
                    serviceAlterTraceSub.setTrtmRsltSmst("부가서비스신청");
                    serviceAlterTraceSub.settSocCode(serviceInfo.getSocCode());
                    serviceAlterTraceSub.setaSocCode("");
                    serviceAlterTraceSub.setParameter(strParameter);
                    serviceAlterTraceSub.setGlobalNo(regSvcInsert.getGlobalNo());
                    serviceAlterTraceSub.setRsltCd(regSvcInsert.getResultCode());
                    serviceAlterTraceSub.setPrcsSbst(regSvcInsert.getSvcMsg());
                    mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
                    regSvcInsert = mPlatFormService.regSvcChgNeTrace(searchVO, serviceInfo.getSocCode(), "");
                }
            }

            //이력 저장
            serviceAlterTraceSub.setEventCode("X21");
            serviceAlterTraceSub.setTrtmRsltSmst("부가서비스신청");
            serviceAlterTraceSub.settSocCode(serviceInfo.getSocCode());
            serviceAlterTraceSub.setaSocCode("");
            serviceAlterTraceSub.setParameter(strParameter);
            serviceAlterTraceSub.setGlobalNo(regSvcInsert.getGlobalNo());
            serviceAlterTraceSub.setRsltCd(regSvcInsert.getResultCode());
            serviceAlterTraceSub.setPrcsSbst(regSvcInsert.getSvcMsg());

            if(regSvcInsert.isSuccess()) {
                serviceAlterTraceSub.setRsltCd("0000");
                successCnt++ ;
            } else {
                failCnt++;
            }

            mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
        }

        if (successCnt == serviceInfoList.size()) {
            serviceAlterTrace.setEventCode("FIN");
            serviceAlterTrace.setTrtmRsltSmst("SUCCESS");
            serviceAlterTrace.setParameter("SCNT["+successCnt + "]FCNT["+failCnt+"]");
            mypageService.insertServiceAlterTrace(serviceAlterTrace);
            rtnMap.put("RESULT_CODE", "00000");
            rtnMap.put("RESULT_MSG", "성공");

            serviceAlterTrace.setChgType("R");  //-- (즉시 : I , 예약 : R)
            serviceAlterTrace.setSuccYn("Y");
            mypageService.insertSocfailProcMst(serviceAlterTrace);

        } else {
            serviceAlterTrace.setEventCode("FIN");
            serviceAlterTrace.setTrtmRsltSmst("FAIL");
            serviceAlterTrace.setParameter("SCNT["+successCnt + "]FCNT["+failCnt+"]");
            mypageService.insertServiceAlterTrace(serviceAlterTrace);
            rtnMap.put("RESULT_CODE", "30000");
            rtnMap.put("RESULT_MSG", "부가서비스 가입 실패");

            serviceAlterTrace.setChgType("R");  //-- (즉시 : I , 예약 : R)
            serviceAlterTrace.setSuccYn("N");
            mypageService.insertSocfailProcMst(serviceAlterTrace);
        }

        return rtnMap;
    }


    @Override
    public int getPromotionDcAmt(String rateCd) {
        RestTemplate restTemplate = new RestTemplate();
        int promotionDcAmt = restTemplate.postForObject(apiInterfaceServer + "/mypage/getPromotionDcSumAmt", rateCd, Integer.class);
        return promotionDcAmt;
    }

    @Override
    public MspSaleSubsdMstDto getRateInfo(String rateCd) {
        MspSaleSubsdMstDto rtnRateInfo = null;
        RestTemplate restTemplate = new RestTemplate();
        rtnRateInfo = restTemplate.postForObject(apiInterfaceServer + "/mypage/getRateInfo", rateCd, MspSaleSubsdMstDto.class);

        return rtnRateInfo;
    }

}