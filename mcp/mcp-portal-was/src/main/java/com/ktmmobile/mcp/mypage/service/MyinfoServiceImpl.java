package com.ktmmobile.mcp.mypage.service;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.dto.CoupInfoDto;
import com.ktmmobile.mcp.common.mplatform.dto.InqrCoupInfoRes;
import com.ktmmobile.mcp.common.mplatform.vo.*;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class MyinfoServiceImpl implements MyinfoService {

    private static Logger logger = LoggerFactory.getLogger(MyinfoServiceImpl.class);


    @Autowired
    private MplatFormService mPlatFormService;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    // 요금조회(X15)
    @Override
    public Map<String,Object> farMonBillingInfo(String ncn, String ctn, String custId, String sysDate) {

        Map<String,Object> hm = new HashMap<String, Object>();

        MpMonthPayMentDto mMonthpaymentdto = null;
//		MpFarMonDetailInfoDto mFarmondetailinfodto = null;
        List<MpMonthPayMentDto> monthList = null;
        try {
            MpFarMonBillingInfoDto billInfo = mPlatFormService.farMonBillingInfoDto(ncn, ctn, custId, sysDate);
            if(billInfo !=null) {
                monthList = billInfo.getMonthList();
            }
            if(monthList !=null && !monthList.isEmpty()) {
                for(MpMonthPayMentDto dto : monthList) {
                    String serviceBillMonth = dto.getBillMonth();
                    if(sysDate.equals(serviceBillMonth)) {
                        mMonthpaymentdto = dto;
                        break;
                    }
                }
                if(mMonthpaymentdto==null) {
                    mMonthpaymentdto = monthList.get(0);
                }
            }

//			if(mMonthpaymentdto !=null) {
//				String billSeqNo = mMonthpaymentdto.getBillSeqNo();
//				String billDueDateList = mMonthpaymentdto.getBillDueDateList();
//				String billMonth = mMonthpaymentdto.getBillMonth();
//				String billStartDate = mMonthpaymentdto.getBillStartDate();
//				String billEndDate = mMonthpaymentdto.getBillEndDate();
//				mFarmondetailinfodto = mPlatFormService.farMonDetailInfoDto(ncn,ctn,custId,billSeqNo,billDueDateList, billMonth, billStartDate, billEndDate);
//			}

//			hm.put("monthList", monthList);
            hm.put("mMonthpaymentdto", mMonthpaymentdto);
//			hm.put("mFarmondetailinfodto", mFarmondetailinfodto);

        } catch (SelfServiceException e) {
            logger.info("Exception e : {}", e.getMessage());
        }  catch(Exception e) {
            logger.debug("error");
        }

        return hm;

    }

    @Override
    public MpPerMyktfInfoVO perMyktfInfo(String ncn, String ctn, String custId) {

        MpPerMyktfInfoVO perMyktfInfo = null;
        String initActivationDate = "-";
        String addr = "";
        try {
            perMyktfInfo = mPlatFormService.perMyktfInfo(ncn, ctn, custId);
            if( perMyktfInfo !=null ) {
                initActivationDate = StringUtil.NVL(perMyktfInfo.getInitActivationDate(),"-");
                addr = StringUtil.NVL(perMyktfInfo.getAddr(),"-");
                if(!"-".equals(initActivationDate)){
                    initActivationDate = initActivationDate.substring(0, 8);
                }
                initActivationDate = DateTimeUtil.changeFormat(initActivationDate,"yyyyMMdd","yyyy.MM.dd");
                perMyktfInfo.setInitActivationDate(initActivationDate);
                perMyktfInfo.setAddr(addr);
            }
        } catch (SelfServiceException e) {
            logger.info("Exception e : {}", e.getMessage());
        } catch(Exception e) {
            logger.debug("X01 조회 에러");
        }

        return perMyktfInfo;
    }

    @Override
    public MpFarChangewayInfoVO farChangewayInfo(String ncn, String ctn, String custId) {

        MpFarChangewayInfoVO mFarchangewayinfovo = null;

        try {
            mFarchangewayinfovo = mPlatFormService.farChangewayInfo(ncn, ctn, custId);
        } catch (SelfServiceException e) {
            logger.info("Exception e : {}", e.getMessage());
        }  catch(Exception e) {
            logger.debug("X23 조회 에러");
        }
        return mFarchangewayinfovo;
    }

    @Override
    public MpMoscBilEmailInfoInVO kosMoscBillInfo(String ncn, String ctn, String custId) {

        MpMoscBilEmailInfoInVO mMoscbilemailinfoinvo = null;
        try {
            mMoscbilemailinfoinvo = mPlatFormService.kosMoscBillInfo(ncn, ctn, custId);
        } catch (SelfServiceException e) {
            logger.info("Exception e : {}", e.getMessage());
        }  catch(Exception e) {
            logger.debug("X49 조회 에러");
        }

        return mMoscbilemailinfoinvo;
    }


    @Override
    public int moscCombStatMgmtInfo(String ncn, String ctn, String custId, String soc) {

        MoscCombStatMgmtInfoOutVO moscCombStatMgmtInfoOutVO = null;
        String retvGubunCd = "";

        String shareChildNm = StringUtil.NVL(NmcpServiceUtils.getCodeNm(Constants.SHARE_RATE_CHILD_LIST,soc),"");
        String shareParentNm = StringUtil.NVL(NmcpServiceUtils.getCodeNm(Constants.SHARE_RATE_PARENT_LIST,soc),"");
        if( !"".equals(shareChildNm) ){ // 받기회선
            retvGubunCd = "R";
        }
        if( !"".equals(shareParentNm) ){ // 주기회선
            retvGubunCd = "G";
        }

        MoscCombStatMgmtInfoOutVO.OutWireDto outWireDtoVo = new MoscCombStatMgmtInfoOutVO.OutWireDto(); // 공통항목
        List<MoscCombStatMgmtInfoOutVO.OutGiveListDto>  outGiveDtoList = new ArrayList<MoscCombStatMgmtInfoOutVO.OutGiveListDto>(); // 주기회선으로 조회시
        List<MoscCombStatMgmtInfoOutVO.OutRcvListDto>  outRcvDtoList = new ArrayList<MoscCombStatMgmtInfoOutVO.OutRcvListDto>(); // 받기회선으로 조회시
        int intSbscBindNowCnt = 0;
        try {
            if(!"".equals(retvGubunCd)) {
                moscCombStatMgmtInfoOutVO = mPlatFormService.moscCombStatMgmtInfo(ncn,ctn,custId,retvGubunCd);
                if(moscCombStatMgmtInfoOutVO !=null ) {

                    if("G".equals(retvGubunCd)){ // 주기회선인경우

                           outWireDtoVo = moscCombStatMgmtInfoOutVO.getOutWireDtoVo(); //  공통항목
                           outGiveDtoList = moscCombStatMgmtInfoOutVO.getOutGiveDtoList();

                           String sbscBindNowCnt = "";

                           if(outWireDtoVo !=null){
                               sbscBindNowCnt = StringUtil.NVL(outWireDtoVo.getSbscBindNowCnt(),"0");
                               intSbscBindNowCnt = Integer.parseInt(sbscBindNowCnt); // 현재결합된 회선수
                           }

                       } else if("R".equals(retvGubunCd)){ // 받기회선인경우

                           outRcvDtoList = moscCombStatMgmtInfoOutVO.getOutRcvDtoList();
                           if(outRcvDtoList !=null && !outRcvDtoList.isEmpty()){
                               String bindStatus = "";
                               for(int i=0; i<outRcvDtoList.size(); i++){
                                   bindStatus = StringUtil.NVL(outRcvDtoList.get(i).getBindStatus(),"");
                                   if("결합".equals(bindStatus)){
                                       intSbscBindNowCnt = 1;
                                   }
                               }
                           }
                       }
                }
            }

        } catch (SocketTimeoutException e) {
            logger.debug("X86 조회 에러");
        }

        return intSbscBindNowCnt;

    }

    @Override
    public MpAddSvcInfoDto getAddSvcInfoDto(String ncn, String ctn, String custId) {
        MpAddSvcInfoDto mAddsvcinfodto = null;
        try {
            mAddsvcinfodto = mPlatFormService.getAddSvcInfoDto(ncn, ctn, custId);
        } catch (SelfServiceException e) {
            logger.info("Exception e : {}", e.getMessage());
        }  catch(Exception e) {
            logger.debug("X20 조회 에러");
        }
        return mAddsvcinfodto;
    }

    // 쿠폰정보 조회(X74)
    @Override
    public List<CoupInfoDto> inqrCoupInfo(String ncn, String ctn, String custId) {

        List<CoupInfoDto> coupInfoList = null;
        InqrCoupInfoRes inqrCoupInfoRes = null;
        CoupInfoDto coupInfoDto = new CoupInfoDto();

        coupInfoDto.setCtn(ctn);
        coupInfoDto.setCustId(custId);
        coupInfoDto.setCoupSerialNo("");
        coupInfoDto.setCoupTypeCd("");
        coupInfoDto.setCoupStatCd("BPCO");
        coupInfoDto.setSearchTypeCd("02");
        coupInfoDto.setSvcTypeCd("");
        coupInfoDto.setPageNo(1);
        coupInfoDto.setNcn(ncn);

        try {
            inqrCoupInfoRes = mPlatFormService.inqrCoupInfo(coupInfoDto);
            if(inqrCoupInfoRes !=null) {
                coupInfoList = inqrCoupInfoRes.getCoupInfoList();
            }

        }catch (SelfServiceException e) {
            logger.info("Exception e : {}", e.getMessage());
        }  catch(Exception e) {
            logger.debug("");
        }


        return coupInfoList;
    }

    // X18 실시간 요금조회
     @Override
     public MpFarRealtimePayInfoVO farRealtimePayInfo(String ncn, String ctn, String custId) {

         MpFarRealtimePayInfoVO mpFarRealtimePayInfoVO = null;

         try {
             mpFarRealtimePayInfoVO = mPlatFormService.farRealtimePayInfo( ncn,  ctn,  custId);
         } catch (SelfServiceException e) {
            logger.info("Exception e : {}", e.getMessage());
        }  catch(Exception e) {
             logger.info("X18Error");
         }
         return mpFarRealtimePayInfoVO;
     }

    @Override
    public Map<String, Object> combinePayData(MpFarChangewayInfoVO farChgWayInfo, MpMoscBilEmailInfoInVO bilEmailInfo) {

        Map<String, Object> rtnMap= new HashMap<>();

        Map<String, String> payData = new HashMap<>();   // 납부방법 정보
        Map<String, String> billData = new HashMap<>();  // 명세서 정보

        // 납부방법 정보 확인
        if(farChgWayInfo == null){
            rtnMap.put("payData", null);
            rtnMap.put("billData", null);
            return rtnMap;
        }

        // 납부방법 정보 세팅
        String payMethod = StringUtil.NVL(farChgWayInfo.getPayMethod(),"-");                // 납부방법
        String blAddr = StringUtil.NVL(farChgWayInfo.getBlAddr(),"-");                      // 청구지 주소
        String blBankAcctNo = StringUtil.NVL(farChgWayInfo.getBlBankAcctNo(),"-");          // 계좌번호
        String prevCardNo = StringUtil.NVL(farChgWayInfo.getPrevCardNo(),"-");              // 카드번호
        String prevExpirDt = StringUtil.NVL(farChgWayInfo.getPrevExpirDt(),"-");            // 카드만료기간
        String billCycleDueDay = StringUtil.NVL(farChgWayInfo.getBillCycleDueDay(),"-");    // 납부일
        String payTmsCd = StringUtil.NVL(farChgWayInfo.getPayTmsCd(),"-");    // 납부일

        boolean isGiro = "지로".equals(payMethod);

        if ("99".equals(billCycleDueDay)) {
            billCycleDueDay = "말일";
        } else if(!"-".equals(billCycleDueDay)) {
            billCycleDueDay += "일";
        }

        if("01".equals(payTmsCd)) {
            payTmsCd = "1회차(11일경)";
        }else {
            payTmsCd = "2회차(20일경)";
        }


        if(7 < prevExpirDt.length()) {
            prevExpirDt = prevExpirDt.substring(0, 4)+"-"+prevExpirDt.substring(4, 6)+"-"+prevExpirDt.substring(6, 8);
        }

        payData.put("payMethod", payMethod);
        payData.put("blBankAcctNo", blBankAcctNo);
        payData.put("billCycleDueDay", billCycleDueDay);
        payData.put("prevCardNo", prevCardNo);
        payData.put("prevExpirDt", prevExpirDt);
        payData.put("payTmsCd", payTmsCd);

        // 명세서 정보 확인
        if(bilEmailInfo == null){
            rtnMap.put("payData", payData);
            rtnMap.put("billData", null);
            return rtnMap;
        }

        // 명세서 정보 세팅
        String billTypeCd = StringUtil.NVL(bilEmailInfo.getBillTypeCd(),""); // 명세서 유형 (key)
        String reqType = "-";    // 명세서 유형 (value)
        String reqTypeNm = "";   // 명세서 유형 상세 (key)
        String blaAddr = "-";    // 명세서 유형 상세 값 (value)

        if ("CB".equals(billTypeCd)) {
            reqType = "이메일 명세서";
        } else if("LX".equals(billTypeCd)) {
            reqType = "우편 명세서";
        } else if("MB".equals(billTypeCd)) {
            reqType = "모바일 명세서(MMS)";
        }

        if(!isGiro) {
            if ("CB".equals(billTypeCd)) {
                reqTypeNm = "메일주소";
                blaAddr = StringUtil.NVL(bilEmailInfo.getMaskedEmail(),"-");
            } else if("MB".equals(billTypeCd)) {
                reqTypeNm = "휴대폰 번호";
                blaAddr = StringUtil.NVL(bilEmailInfo.getCtn(),"-");
            } else {
                // 지로가 아니면서 명세서 유형이 우편 명세서인 경우, blaAddr로 X01(가입정보조회)의 주소를 표출
                // X01의 주소값은 화면에 존재
                reqTypeNm = "청구지";
            }
        }else{
            reqTypeNm = "청구지";
            blaAddr = blAddr;  // 납부방법(X23) 정보의 청구지 사용
        }

        billData.put("reqType", reqType);
        billData.put("reqTypeNm", reqTypeNm);
        billData.put("blaAddr", blaAddr);
        billData.put("billTypeCd", billTypeCd);

        rtnMap.put("payData", payData);
        rtnMap.put("billData", billData);
        return rtnMap;
    }

}
