/**
 *
 */
package com.ktmmobile.mcp.mypage.service;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.ktmmobile.mcp.common.dto.MoscCombReqDto;
import com.ktmmobile.mcp.common.dto.NiceLogDto;
import com.ktmmobile.mcp.common.dto.db.McpMrktHistDto;
import com.ktmmobile.mcp.common.mplatform.dto.MoscCombInfoResDTO;
import com.ktmmobile.mcp.common.mplatform.dto.MoscMvnoComInfo;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.content.service.MyCombinationSvc;
import com.ktmmobile.mcp.fqc.dto.FqcDltDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.McpRequestAgrmDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.mspservice.dao.MspDao;
//import com.ktmmobile.mcp.common.service.FormImageMakeSvc;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.mypage.dao.CustRequestDao;
import com.ktmmobile.mcp.mypage.dao.MypageDao;
import com.ktmmobile.mcp.mypage.dto.BillWayChgDto;
import com.ktmmobile.mcp.mypage.dto.CommendStateDto;
import com.ktmmobile.mcp.mypage.dto.JehuDto;
import com.ktmmobile.mcp.mypage.dto.JuoFeatureDto;
import com.ktmmobile.mcp.mypage.dto.McpFarPriceDto;
import com.ktmmobile.mcp.mypage.dto.McpRateChgDto;
import com.ktmmobile.mcp.mypage.dto.McpRegServiceDto;
import com.ktmmobile.mcp.mypage.dto.McpRetvRststnDto;
import com.ktmmobile.mcp.mypage.dto.McpServiceAlterTraceDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MspJuoAddInfoDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.dto.NmcpProdImgDtlDto;
import com.ktmmobile.mcp.mypage.dto.SuspenChgTmlDto;

/**
 * @author ANT_FX700_02
 *
 */
@Service
public class MypageServiceImpl implements MypageService {

    private static final Logger logger = LoggerFactory.getLogger(MypageServiceImpl.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    IpStatisticService ipstatisticService;

    @Autowired
    MypageDao mypageDao;

    @Autowired
    MspDao mspDao;

    @Autowired
    NicePinService nicePinService;

    @Autowired
    CustRequestDao custRequestDao;

    @Autowired
    MyCombinationSvc myCombinationSvc;

    /*
    @Autowired
    FormImageMakeSvc formImageMakeSvc;
    */

    /**
     * @param mcpUserCntrMngDto
     * @return
     * @Description : MCP 휴대폰 회선관리 리스트를 가지고 온다.
     * @Author : ant
     * @Create Date : 2016. 1. 12.
     */
    public List<McpUserCntrMngDto> selectCntrList(String userId) {
        //---- API 호출 S ----//
        //MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        UserSessionDto userSessionDto1 = SessionUtils.getUserCookieBean();
        if (userSessionDto1 != null) {
            params.put("customerId", userSessionDto1.getCustomerId());
        }

        RestTemplate restTemplate = new RestTemplate();
        //McpUserCntrMngDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrList", userId, McpUserCntrMngDto[].class);
        McpUserCntrMngDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrList", params, McpUserCntrMngDto[].class);

        UserSessionDto userSessionDto2 = SessionUtils.getUserCookieBean();

        List<McpUserCntrMngDto> list = null;
        if (Optional.ofNullable(resultList).isPresent() && resultList.length != 0) {
            list = Arrays.asList(resultList);

            // UC0 유심셀프변경 화면에서 미성년자 체크를 위해 추가
            for (McpUserCntrMngDto mcpUserCntrMngDto : list) {
                String strUnUserSSn = mcpUserCntrMngDto.getUnUserSSn();
                mcpUserCntrMngDto.setAge(Integer.toString(getAge(strUnUserSSn)));
                if (strUnUserSSn != null && strUnUserSSn.length() > 5) {
                    mcpUserCntrMngDto.setBirth(strUnUserSSn.substring(0, 6));
                } else if (strUnUserSSn != null) {
                    mcpUserCntrMngDto.setBirth(strUnUserSSn);
                }

            }
        }


        //---- API 호출 E ----//
        return list;
    }


    public static int getAge(String idNum) {

        String today = "";
        String birthday = "";
        int myAge = 0;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);

        today = formatter.format(new Date());

        if (idNum != null && idNum.trim().length() == 13) {

            if (idNum.charAt(6) == '1' || idNum.charAt(6) == '2' || idNum.charAt(6) == '5' || idNum.charAt(6) == '6') {
                birthday = "19" + idNum.substring(0, 6);
            } else if (idNum.charAt(6) == '*') {
                return -1;
            } else {
                birthday = "20" + idNum.substring(0, 6);
            }
        } else {
            return 0;
        }

        myAge = Integer.parseInt(today.substring(0, 4)) - Integer.parseInt(birthday.substring(0, 4));
        // 생일 전이면 1 을 뺀다
        if (Integer.parseInt(today.substring(4)) < Integer.parseInt(birthday.substring(4))) myAge = myAge - 1;

        return myAge;
    }

    public String selectCustomerType(String custId) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        String customerType = restTemplate.postForObject(apiInterfaceServer + "/mypage/customerType", custId, String.class);
        if (customerType == null) {
            return "";
        }
        return customerType;
    }


    /**
     * @param svcCntrNo
     * @return McpUserCntrMngDto
     * @Description : 휴대폰 회선에 따른 요금제 정보를 가지고 온다.
     * @Author : ant
     * @Create Date : 2016. 1. 12.
     */
    public McpUserCntrMngDto selectSocDesc(String svcCntrNo) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        McpUserCntrMngDto mcpUserCntrMngDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/socDesc", svcCntrNo, McpUserCntrMngDto.class);
        return mcpUserCntrMngDto;
    }

    /**
     * @param svcCntrNo
     * @return MspJuoAddInfoDto
     * @Description : 휴대폰 회선에 따른 단말기할인 정보를 가지고 온다.
     * @Author : ant
     * @Create Date : 2016. 1. 12.
     */
    public MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        MspJuoAddInfoDto mspAddInfo = restTemplate.postForObject(apiInterfaceServer + "/mypage/mspAddInfo", svcCntrNo, MspJuoAddInfoDto.class);
        return mspAddInfo;
    }

    /**
     * @param modelId
     * @return NmcpProdImgDtlDto
     * @Description : 휴대폰 모델 ID 에 따른 이미지 경로를 가지고 온다.
     * @Author : ant
     * @Create Date : 2016. 1. 22.
     */
    public NmcpProdImgDtlDto selectHpImgPath(String modelId) {
        return mypageDao.selectHpImgPath(modelId);
    }

    /**
     * @param modelId
     * @return int
     * @Description : 접속한 횟수를 가져온다
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    @Override
    public McpRetvRststnDto retvRstrtn(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        return mypageDao.retvRstrtn(map);
    }

    /**
     * @param modelId
     * @return int
     * @Description : 접속한 횟수를 설정한다
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    @Override
    public void retvRstrtnInsert(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        mypageDao.retvRstrtnInsert(map);

    }

    /**
     * @param modelId
     * @return int
     * @Description : 접속제한 횟수감소
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    @Override
    public void retvRstrtnUpCnt(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        mypageDao.retvRstrtnUpCnt(map);
    }

    /**
     * @param modelId
     * @return int
     * @Description : 접속한 횟수, 접속일자 현재일자로 초기화
     * @Author : ant
     * @Create Date : 2016. 1. 29.
     */
    @Override
    public void retvRstrtnUpSysDate(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        mypageDao.retvRstrtnUpSysDate(map);
    }

    /**
     * 부가서비스 조회
     */
    @Override
    public List<McpRegServiceDto> selectRegService(String ncn) {

        if (StringUtils.isBlank(ncn)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        RestTemplate restTemplate = new RestTemplate();
        McpRegServiceDto[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/mypage/regService", ncn, McpRegServiceDto[].class);
        List<McpRegServiceDto> list = Arrays.asList(rtnList);
        return list;
    }

    //현재 요금제 조회 svcCntrNo
    @Override
    public McpFarPriceDto selectFarPricePlan(String ncn) {
        // TODO Auto-generated method stub

        if (StringUtils.isBlank(ncn)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        McpFarPriceDto mcpFarPriceDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/farPricePlan", ncn, McpFarPriceDto.class);
        //---- API 호출 E ----//

        return mcpFarPriceDto;

    }

    @Override
    public List<McpFarPriceDto> selectFarPricePlanList(String rateCd) {
        //as-is return mypageDao.selectFarPricePlanList(rateCd, skipResult,maxResult);

        RestTemplate restTemplate = new RestTemplate();
        McpFarPriceDto[] list = restTemplate.postForObject(apiInterfaceServer + "/mypage/farPricePlanList", rateCd, McpFarPriceDto[].class);
        List<McpFarPriceDto> farPricePlanList = Arrays.asList(list);
        return farPricePlanList;
    }


    /**
     * @param String contractNum
     * @return int
     * @Description : 제주항공 회원 아이디 가지고 오기
     * @Author : ant
     * @Create Date : 2016. 2. 20.
     */
    @Override
    public String selectJejuid(String contractNum) {
        return mypageDao.selectJejuid(contractNum);
    }

    /**
     * @param Map<String, Stirng> map
     * @return void
     * @Description : 제주항공 회원 아이디 기간 종료 업데이트
     * @Author : ant
     * @Create Date : 2016. 2. 20.
     */
    @Override
    public void updatePointMgmt(Map<String, String> map) {
        mypageDao.updatePointMgmt(map);
    }

    /**
     * @param Map<String, Stirng> map
     * @return void
     * @Description : 제주항공 회원 아이디 신규 인서트
     * @Author : ant
     * @Create Date : 2016. 2. 20.
     */
    @Override
    public void insertPointMgmt(Map<String, String> map) {
        mypageDao.insertPointMgmt(map);
    }

    /**
     * @param Map<String, Stirng> map
     * @return void
     * @Description : 제주항공 회원 아이디 설정
     * @Author : ant
     * @Create Date : 2016. 2. 20.
     */
    @Override
    @Transactional
    public void setJejuPointMgmt(Map<String, String> map) {
        String pointId = StringUtil.NVL(map.get("pointId"), "");
        pointId = pointId.trim();
        String contractNum = map.get("contractNum");
        String oldPointId = StringUtil.NVL(mypageDao.selectJejuid(contractNum), "");


        if (!pointId.equals(oldPointId)) {
            map.put("memo", "홈페이지 회원정보수정");
            mypageDao.updatePointMgmt(map);

            if (!pointId.equals("")) {
                mypageDao.insertPointMgmt(map);
            }
        }

    }

    /**
     * @param String userId
     * @return Map<String, String>
     * @Description : 원부증명서 가지고 오기
     * @Author : ant
     * @Create Date : 2016. 2. 22.
     */
    @Override
    public Map<String, Object> selectCertHist(String userId) {
        return mypageDao.selectCertHist(userId);
    }

    /**
     * @param HashMap<String, Stirng> map
     * @return void
     * @Description : 원부증명서 신규 인서트
     * @Author : ant
     * @Create Date : 2016. 2. 22.
     */
    @Override
    public void insertCertHist(Map<String, String> map) {

        String outputDivCd = map.get("outputDivCd");
        if (outputDivCd == null || outputDivCd.equals("")) {
            map.put("outputDivCd", "01");
        }
        mypageDao.insertCertHist(map);
    }

    /**
     * @param String ncn
     * @return Map<String, String>
     * @Description : 단말 할부개월, 단말 할부원금 가지고 오기
     * @Author : ant
     * @Create Date : 2016. 2. 22.
     */
    public Map<String, BigDecimal> selectModelSaleInfo(String ncn) {
        return mypageDao.selectModelSaleInfo(ncn);
    }

    /**
     * @param String ncn
     * @return Map<String, String>
     * @Description : 요금제 변경 정보 select
     * @Author : ant
     * @Create Date : 2016. 3.21
     */
    @Override
    public String selectFarPriceAddInfo(Map<String, String> map) {
        // TODO Auto-generated method stub
        //as-is return mypageDao.selectFarPriceAddInfo(map);

        RestTemplate restTemplate = new RestTemplate();
        String farPriceAddInfo = "";
        farPriceAddInfo = restTemplate.postForObject(apiInterfaceServer + "/mypage/farPriceAddInfo", map, String.class);
        return farPriceAddInfo;
    }

    /**
     * @param String contractNum , int skipResult , int maxResult
     * @return List<JehuDto>
     * @Description : 제주항공 포인트 리스트
     * @Author : ntki1741
     * @Create Date : 2016. 4.22
     */
    @Override
    public List<JehuDto> selectJehuList(String contractNum, int skipResult, int maxResult) {
        // TODO Auto-generated method stub

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("contractNum", contractNum);
        map.put("skipResult", skipResult);
        map.put("maxResult", maxResult);
        RestTemplate restTemplate = new RestTemplate();
        JehuDto[] JehuList = restTemplate.postForObject(apiInterfaceServer + "/mypage/selectJehuList", map, JehuDto[].class);
        List<JehuDto> selectJehuList = Arrays.asList(JehuList);
        return selectJehuList;
    }


    /**
     * @param String contractNum
     * @return int
     * @Description : 제주항공 포인트 리스트갯수
     * @Author : ntki1741
     * @Create Date : 2016. 4.22
     */
    @Override
    public int selectJehuListCnt(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        int JehuListCnt = restTemplate.postForObject(apiInterfaceServer + "/mypage/jehuListCount", contractNum, int.class);
        return JehuListCnt;
    }

    @Override
    public List<JehuDto> selectJehuListAll(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        JehuDto[] JehuList = restTemplate.postForObject(apiInterfaceServer + "/mypage/jehuListAll", contractNum, JehuDto[].class);
        List<JehuDto> selectJehuListAll = Arrays.asList(JehuList);
        return selectJehuListAll;
    }

    @Override
    public McpRateChgDto saveNmcpRateChgHist(McpRateChgDto mcpRateChgDto) {

        String soc = StringUtil.NVL(mcpRateChgDto.getRateCd(), ""); //변경 요금제 코드
        boolean isFinaceRate = false;

        //금융제휴요금제 코드 여부
        List<NmcpCdDtlDto> clauseFinanceRatesCDList = NmcpServiceUtils.getCodeList("ClauseFinanceRatesCD");
        if (clauseFinanceRatesCDList != null) {
            for (NmcpCdDtlDto nmcpCdDtlDto : clauseFinanceRatesCDList) {
                if (soc.equals(nmcpCdDtlDto.getDtlCd())) {
                    isFinaceRate = true;
                    break;
                }
            }
        }

        //금융 제휴 요금제일경우
        if (isFinaceRate) {

            String createYn = mcpRateChgDto.getCreateYn();
            if (createYn.equals(Constants.MCPRATE_CHG_HIST_CREATEYN_REQ)) { //요청

                NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();

                //본인 인증 정보 유효성 체크
                String cStmrName = StringUtil.NVL(mcpRateChgDto.getCstmrName(), "");
                //String birthDate = StringUtil.NVL(mcpRateChgDto.getBirthDate(), "");

                //MSP에서 생년월일 조회
                String birthDate = "";
                try {
                    //birthDate = mspDao.getCustomerSsn(mcpRateChgDto.getContractNum()) ;
                    RestTemplate restTemplate = new RestTemplate();
                    birthDate = restTemplate.postForObject(apiInterfaceServer + "/msp/customerSsn", mcpRateChgDto.getContractNum(), String.class);
                    birthDate = EncryptUtil.ace256Dec(birthDate);
                    birthDate = birthDate.substring(0, 6);
                } catch (CryptoException e) {
                    //생년월일 복호화 실패
                    mcpRateChgDto.setResultFlag(false);
                    mcpRateChgDto.setResultMsg(ExceptionMsgConstant.ACE_256_DECRYPT_EXCEPTION);
                    return mcpRateChgDto;
                }

                //인증 정보 체크
                if (sessNiceRes != null) {

                    if (cStmrName.equalsIgnoreCase(sessNiceRes.getName()) && birthDate.equals(sessNiceRes.getBirthDate().substring(2, 8))) {

                        String resNo = "ReqNo:".concat(sessNiceRes.getReqSeq()).concat(", ResNo:").concat(sessNiceRes.getResSeq());
                        mcpRateChgDto.setResNo(resNo);
                        mypageDao.inertNmcpRateChg(mcpRateChgDto); // NMCP_RATE_CHG	 저장

                    } else {
                        //로그인 정보와 인증정보가 다름
                        mcpRateChgDto.setResultFlag(false);
                        mcpRateChgDto.setResultMsg(ExceptionMsgConstant.NICE_CERT_EXCEPTION);
                    }

                } else {
                    //인증 정보 없음
                    mcpRateChgDto.setResultFlag(false);
                    mcpRateChgDto.setResultMsg(ExceptionMsgConstant.F_BIND_EXCEPTION);
                }

            } else if (createYn.equals(Constants.MCPRATE_CHG_HIST_CREATEYN_SUCESS)) { //응답 성공

                int updateCnt = mypageDao.updateNmcpRateChg(mcpRateChgDto); //NMCP_RATE_CHG 업데이트 createYn : S

//                   if (updateCnt > 0) {
//
//                       //데이터 조회
//                       McpRateChgDto selectMcpRateChgDto = mypageDao.selectNmcpRateChg(mcpRateChgDto);
//
//                       //서식지 생성 및 이미지 암호화
//                       McpRateChgDto resutlMakeImageDto = formImageMakeSvc.makeFormImage(selectMcpRateChgDto);
//
//                       //서식지 이미지 생성 및 암호화 성공
//                       if (resutlMakeImageDto.isResultFlag()) {
//
//                           mcpRateChgDto.setFileNm(resutlMakeImageDto.getFileNm());
//                           mcpRateChgDto.setFilePath(resutlMakeImageDto.getFilePath());
//                           mcpRateChgDto.setExt(resutlMakeImageDto.getExt());
//
//                           updateCnt = mypageDao.updateNmcpRateChg(mcpRateChgDto); //NMCP_RATE_CHG 생성 이미지 정보 업데이트
//
//                           if (updateCnt > 0) {
//                               //서식지 합본 프로시져 호출
//                               mypageDao.callMcpRateChgImg(mcpRateChgDto);
//                           }
//                       }
//                   }

            } else if (createYn.equals(Constants.MCPRATE_CHG_HIST_CREATEYN_FAIL)
                    || createYn.equals(Constants.MCPRATE_CHG_HIST_CREATEYN_TIMEOUT)) { //타임 아웃이거나 오류일때

                mypageDao.updateNmcpRateChg(mcpRateChgDto); //NMCP_RATE_CHG 상태값 및 오류 내용 업데이트
            }

        } else {
            //일반 요금제로 변경
            mcpRateChgDto.setResultFlag(true);
        }

        return mcpRateChgDto;
    }

    @Override
    public McpRateChgDto selectNmcpRateChg(McpRateChgDto mcpRateChgDto) {
        return mypageDao.selectNmcpRateChg(mcpRateChgDto);
    }

    @Override
    public McpRateChgDto reMakeFinanceTemplate(McpRateChgDto mcpRateChgDto) {

        //데이터 조회
        McpRateChgDto selectMcpRateChgDto = mypageDao.selectNmcpRateChg(mcpRateChgDto);

        //타임아웃 일때
//        if (selectMcpRateChgDto.getCreateYn().equals(Constants.MCPRATE_CHG_HIST_CREATEYN_TIMEOUT)
//                || selectMcpRateChgDto.getCreateYn().equals(Constants.MCPRATE_CHG_HIST_CREATEYN_SUCESS)) {
//
//            //서식지 생성 및 이미지 암호화
//            McpRateChgDto resutlMakeImageDto = formImageMakeSvc.makeFormImage(selectMcpRateChgDto);
//
//            //서식지 이미지 생성 및 암호화 성공
//            if (resutlMakeImageDto.isResultFlag()) {
//
//                mcpRateChgDto.setFileNm(resutlMakeImageDto.getFileNm());
//                mcpRateChgDto.setFilePath(resutlMakeImageDto.getFilePath());
//                mcpRateChgDto.setExt(resutlMakeImageDto.getExt());
//                mcpRateChgDto.setRvisnId("ADMIN");
//                mcpRateChgDto.setCreateYn(Constants.MCPRATE_CHG_HIST_CREATEYN_SUCESS);
//
//                int updateCnt = mypageDao.updateNmcpRateChg(mcpRateChgDto); //NMCP_RATE_CHG 생성 이미지 정보 업데이트
//
//                if (updateCnt > 0) {
//                    //서식지 합본 프로시져 호출
//                    HashMap<Object, Object> rsHm = mypageDao.callMcpRateChgImg(mcpRateChgDto);
//
//                }
//            }
//
//        }

        return mcpRateChgDto;
    }

    @Override
    public int selectCallSvcLimitCount(HashMap<String, String> hm) {
        int cnt = mypageDao.selectCallSvcLimitCount(hm);
        return cnt;
    }

    @Override
    public void insertMcpSelfcareStatistic(HashMap<String, String> hm) {
        mypageDao.insertMcpSelfcareStatistic(hm);
    }

    @Override
    public boolean insertMcpRequestArm(McpRequestAgrmDto mcpRequestAgrmDto) {
        return mypageDao.insertMcpRequestArm(mcpRequestAgrmDto);
    }


    @Override
    public String getCommendId(String contractNum) {
        return mypageDao.getCommendId(contractNum);
    }


    /**
     * @param svcCntrNo
     * @return McpUserCntrMngDto
     * @Description : 정회원 아닌 사용자 요금제 조회
     * @Author : ant
     * @Create Date : 2016. 1. 12.
     */
    public McpUserCntrMngDto selectSocDescNoLogin(String svcCntrNo) {
        RestTemplate restTemplate = new RestTemplate();
        McpUserCntrMngDto mcpUserCntrMngDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/socDescNoLogin", svcCntrNo, McpUserCntrMngDto.class);
        return mcpUserCntrMngDto;
    }

    /**
     * @param mcpUserCntrMngDto
     * @return
     * @Description : 휴대폰 회선정보 가지고 온다
     * @Author : ant
     * @Create Date : 2016. 1. 12.
     */
    public McpUserCntrMngDto selectCntrListNoLogin(String contractNum) {
        if (contractNum == null || "".equals(contractNum)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
        userCntrMngDto.setSvcCntrNo(contractNum);
//        userCntrMngDto.setCntrMobileNo(contractNum);
        return selectCntrListNoLogin(userCntrMngDto);
    }


      public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {

          if ( userCntrMngDto.getSvcCntrNo() == null && userCntrMngDto.getCntrMobileNo() == null) {
              throw new McpCommonException(F_BIND_EXCEPTION);
          }

          //---- API 호출 S ----//
            RestTemplate restTemplate = new RestTemplate();
            McpUserCntrMngDto mcpUserCntrMngDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);
            //---- API 호출 E ----//
//          return mypageDao.selectCntrListNoLogin(userCntrMngDto);
          return mcpUserCntrMngDto;
      }



      public List<SuspenChgTmlDto> selectSuspenChgTmp(SuspenChgTmlDto suspenChgTmlDto){
          return mypageDao.selectSuspenChgTmp(suspenChgTmlDto);
      }

      public List<SuspenChgTmlDto> selectSuspenChgTmpList(){
          return mypageDao.selectSuspenChgTmpList();
      }
      public int suspenChgUpdate(SuspenChgTmlDto reqDto){
          return mypageDao.suspenChgUpdate(reqDto);
      }

      public JuoFeatureDto getJuoFeature(JuoFeatureDto juoFeatureDto) {
          RestTemplate restTemplate = new RestTemplate();
          JuoFeatureDto juoDto = new JuoFeatureDto();
          juoDto = juoFeatureDto;
          juoDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/getJuoFeature", juoDto, JuoFeatureDto.class);
          return juoDto;
      }

    @Override
    public List<CommendStateDto> getCommendStateList(String commendId) {
        RestTemplate restTemplate = new RestTemplate();

        //임시 테스트
        //Object A1234 = "A1234";
        // commendId = "A1234";

        CommendStateDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/commendStateList", commendId, CommendStateDto[].class);

        List<CommendStateDto> commendStateList = Arrays.asList(resultList);
        return commendStateList;
    }


    @Override
    public List<CommendStateDto> getCommendStateList(String commendId, MyPageSearchDto searchVO) {

        String prcsMdlInd = DateTimeUtil.getFormatString("yyyyMMddHHmmss");

        // MP연동 X77
        // 각각 결합여부 확인
        MoscCombReqDto moscCombReqDto = new MoscCombReqDto();
        moscCombReqDto.setCustId(searchVO.getCustId());
        moscCombReqDto.setNcn(searchVO.getNcn());
        moscCombReqDto.setCtn(searchVO.getCtn());
        moscCombReqDto.setCombSvcNoCd("M");  //결합 모회선 사업자 구분 코드	1	O	M: MVNO회선, K:KT 회선
        moscCombReqDto.setCombSrchId(searchVO.getCtn()); // 결합 모회선 조회값	10	O	"MVNO회선은 전화번호        KT 회선은 이름"
        moscCombReqDto.setSameCustKtRetvYn("Y"); // 동일명의 KT회선 조회여부 = 'Y'로 조회 시 미동의 회선의 정보 응답

        MoscCombInfoResDTO moscCombInfoResDTO = myCombinationSvc.selectCombiMgmtListLog(moscCombReqDto, prcsMdlInd,searchVO.getContractNum());
        String internetId = "";

        if (moscCombInfoResDTO != null && moscCombInfoResDTO.isSuccess() && moscCombInfoResDTO.getMoscSrchCombInfoList() != null && moscCombInfoResDTO.getMoscSrchCombInfoList().size() > 0 ) {
            List<MoscMvnoComInfo> moscSrchCombInfoList = moscCombInfoResDTO.getMoscSrchCombInfoList();
            for(MoscMvnoComInfo item : moscSrchCombInfoList){
                String svcDivCd = item.getSvcDivCd();
                String corrNm = item.getCorrNm();
                /** 결합대상 회선 조회(X77)
                 * svcDivCd 서비스 종류 : 인터넷
                 * corrNm 모집경로  :  MVNOKIS
                 * svcDivCd:인터넷,svcNo:z!63213713416,svcContOpnDt:20241129,corrNm:MVNOKIS
                 * */
                if ("인터넷".equals(svcDivCd) && "MVNOKIS".equals(corrNm)){
                    internetId = item.getSvcNo() == null ? "" : item.getSvcNo();
                    break;
                }
            }
        }


        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("commendId", commendId);
        params.put("internetId", internetId);
        CommendStateDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/selectCommendAllStateList", params, CommendStateDto[].class);
        List<CommendStateDto> commendStateList = Optional.ofNullable(resultList).map(Arrays::asList).orElseGet(Collections::emptyList);// Arrays.asList(resultList);

        return commendStateList;
    }

    /**
     * 약정정보
     */
    @Override
    public MspJuoAddInfoDto getEnggInfo(String contractNum) {

        RestTemplate restTemplate = new RestTemplate();
        MspJuoAddInfoDto mspJuoAddInfoDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/enggInfo1", contractNum, MspJuoAddInfoDto.class);
        return mspJuoAddInfoDto;
    }


    @Override
    public MspJuoAddInfoDto getChannelInfo(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        MspJuoAddInfoDto mspJuoAddInfoDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/channelInfo", contractNum, MspJuoAddInfoDto.class);
        return mspJuoAddInfoDto;
    }

     /**
     * @Description : 해지 해야 할 부가 서비스 조회
     * @param  ctn : 전화번호
     * @return List<McpRequestSaleinfoDto>
     * @Author : papier
     * @Create Date : 2021. 11. 05.
     */

    @Override
    public List<McpUserCntrMngDto> getCloseSubList(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();

        McpUserCntrMngDto[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/mypage/closeSubList", contractNum, McpUserCntrMngDto[].class);
        List<McpUserCntrMngDto> list = Arrays.asList(rtnList);

        return list;
    }

     @Override
     public boolean insertServiceAlterTrace(McpServiceAlterTraceDto serviceAlterTrace) {
         HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
         serviceAlterTrace.setAccessIp(ipstatisticService.getClientIp());
         UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
         if(userSession !=null){
             serviceAlterTrace.setUserId(userSession.getUserId());
         }
         serviceAlterTrace.setAccessUrl(request.getRequestURI());

         return  mypageDao.insertServiceAlterTrace(serviceAlterTrace) ;
     }

     /**
     * 요금제셀프변경실패 INSERT
     * @param contractNum
     * @return
     */
    @Override
    public boolean insertSocfailProcMst(McpServiceAlterTraceDto serviceAlterTrace) {
        RestTemplate restTemplate = new RestTemplate();

        if (StringUtils.isBlank(serviceAlterTrace.getChgType())) {
            serviceAlterTrace.setChgType("I"); //-- (즉시 : I , 예약 : R) Default I
         }

        boolean isSucssesYn = restTemplate.postForObject(apiInterfaceServer + "/mypage/insertSocfailProcMst", serviceAlterTrace, boolean.class);

        return isSucssesYn;
    }

    /**
     * @Description : 가입 해야 할 부가 서비스 조회
     * @param
     * @return List<McpRequestSaleinfoDto>
     * @Author : papier
     * @Create Date : 2021. 11. 05.
     */
    @Override
    public List<McpUserCntrMngDto> getromotionDcList(String toSocCode) {

        RestTemplate restTemplate = new RestTemplate();
        McpUserCntrMngDto[] list = restTemplate.postForObject(apiInterfaceServer + "/mypage/romotionDcList", toSocCode, McpUserCntrMngDto[].class);
        List<McpUserCntrMngDto> rtnList = Arrays.asList(list);
        return rtnList;
    }

    @Override
    public int countFarPricePlanList(String rateCd) {
        RestTemplate restTemplate = new RestTemplate();
        int count = restTemplate.postForObject(apiInterfaceServer + "/mypage/countFarPricePlanList", rateCd, int.class);
        return count;
    }

    /**
     * 쉐어링 개통시 청구계정 정보조회
     */
    @Override
    public String selectBanSel(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();
        String  billAcntNo = restTemplate.postForObject(apiInterfaceServer + "/mypage/selectBanSel", contractNum, String.class);
        return billAcntNo;
    }

    /**
     * 명세서 재발송을 위한 msp 정보 조회
     */
     public BillWayChgDto getMspData(String contractNum) {

         RestTemplate restTemplate = new RestTemplate();
         BillWayChgDto resDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/billWayReSend", contractNum, BillWayChgDto.class);
         return resDto;
     }

     public void insertMcpBillwayResend(BillWayChgDto billWayChgDto) {
         mypageDao.insertMcpBillwayResend(billWayChgDto);
     }

     // 회선정보
     public boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList, UserSessionDto userSession) {

         String UserDivision = userSession.getUserDivision();

         if( !StringUtil.equals(UserDivision, "01") ){
             return false;
         }

         if(cntrList == null || cntrList.isEmpty() ) {
             return false;
         }

         boolean isOwn = false;

         String paramNcn = StringUtil.NVL(searchVO.getNcn(),"");
         String sesNcn = StringUtil.NVL(SessionUtils.getCurrPhoneNcn(),"");
         String selNcn = StringUtil.NVL(paramNcn,sesNcn);
         int moCtn = cntrList.size();
         searchVO.setMoCtn(moCtn);
         if( StringUtil.isEmpty(selNcn) ){
             searchVO.setNcn(cntrList.get(0).getSvcCntrNo());
             searchVO.setCtn(cntrList.get(0).getCntrMobileNo());
             searchVO.setCustId(cntrList.get(0).getCustId());
             searchVO.setModelName(cntrList.get(0).getModelName());
             searchVO.setContractNum(cntrList.get(0).getContractNum());
             searchVO.setSoc(cntrList.get(0).getSoc());
             SessionUtils.setCurrPhoneNcn(cntrList.get(0).getSvcCntrNo());
             isOwn = true;
         }

         for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
             String ctn = mcpUserCntrMngDto.getCntrMobileNo();
             String ncn = mcpUserCntrMngDto.getSvcCntrNo();
             String custId = mcpUserCntrMngDto.getCustId();
             String modelName = mcpUserCntrMngDto.getModelName();
             String contractNum = mcpUserCntrMngDto.getContractNum();
             String soc =  mcpUserCntrMngDto.getSoc();

             mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
             mcpUserCntrMngDto.setSvcCntrNo(ncn);
             mcpUserCntrMngDto.setCustId(custId);
             mcpUserCntrMngDto.setModelName(modelName);
             mcpUserCntrMngDto.setContractNum(contractNum);

             if(StringUtil.equals(selNcn, String.valueOf(ncn))){
                 searchVO.setNcn(ncn);
                 searchVO.setCtn(ctn);
                 searchVO.setCustId(custId);
                 searchVO.setModelName(modelName);
                 searchVO.setContractNum(contractNum);
                 searchVO.setSoc(soc);
                 SessionUtils.setCurrPhoneNcn(ncn);
                 isOwn = true;
             }
         }

         return isOwn;
     }


    /**
     * 후불 고객건 조회
     */
    @Override
    public List<McpUserCntrMngDto> selectPoList(List<McpUserCntrMngDto> cntrList) {

        List<McpUserCntrMngDto> poList = new ArrayList<>();

        if (cntrList == null || cntrList.isEmpty()) {
            return cntrList;
        }

        for (int i = 0; i < cntrList.size(); i++) {
            if ("PO".equals(cntrList.get(i).getPppo())) {
                poList.add(cntrList.get(i));
            }
        }
        return poList;
    }


    @Override
    public int checkAllreadPlanchgCount(McpServiceAlterTraceDto serviceAlterTrace) {
        return mypageDao.checkAllreadPlanchgCount(serviceAlterTrace);
    }

    @Override
    public List<McpMrktHistDto> selectExistingConsent(String userId) { //수정
        List<McpMrktHistDto> mrktList = this.mypageDao.selectExistingConsent(userId);
        return CollectionUtils.isNotEmpty(mrktList) ? mrktList : null;
    }

    @Override
    public int insertDisApd(McpUserCntrMngDto apdDto) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/insertDisApd", apdDto, int.class);
    }


    @Override
    public String getChrgPrmtIdSocChg(String rateCd) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/getChrgPrmtIdSocChg", rateCd, String.class);
    }

    /**
     * 계약번호로 정회원 정보를 가져온다.
     */
    @Override
    public McpUserCntrMngDto getCntrInfoByContNum(String contractNum) {
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = this.selectCntrList(userSession.getUserId());
        McpUserCntrMngDto cntrMngDto = null;
        if (cntrList != null && cntrList.size() > 0) {
            for (McpUserCntrMngDto dto : cntrList) {
                if (contractNum.equals(dto.getContractNum()) || contractNum.equals(dto.getSvcCntrNo())) {
                    cntrMngDto = dto;
                    break;
                }
            }
        }
        return cntrMngDto;
    }

    /**
     * 2024-12-17 인가된 사용자 체크
     * 비로그인 - 패스
     * 로그인
     *  유심구매 - 입력한 이름이 로그인세션의 이름과 일치하는지 확인
     *  준회원 - 입력한 이름이 로그인세션의 이름과 일지하는지 확인
     *         입력한 주민번호로 DI 확인, 로그인세션의 DI 값과 일치하는지 확인
     *  정회원 - 입력한 이름, 주민번호가 로그인세션의 이름, 주민번호와 일치하는지 확인
     */
    @Override
    public Map<String, String> checkAuthUser(String name, String userSsn) {
        Map<String, String> rtnMap = new HashMap<>();
        rtnMap.put("returnCode", "0000");

        UserSessionDto session = SessionUtils.getUserCookieBean();
        if (session != null) {
            // 공백제거
            String paramName = StringUtil.NVL(name, "").replaceAll(" ", ""); // 입력한 이름
            String sessionName = session.getName().replaceAll(" ", ""); // 세션 이름

            // url 찾기
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String referer = StringUtil.NVL(request.getHeader("REFERER"), "");
            // 유심 구매 - 이름만 체크
            if (referer.indexOf("/reqSelfDlvry.do") > -1 || referer.indexOf("/replaceUsimView.do") > -1) {
                if (StringUtils.isBlank(paramName)) {
                    rtnMap.put("returnCode", "0010");
                    rtnMap.put("returnMsg", "이름이 입력되지 않았습니다.<br/>새로고침 후 다시 시도해주세요.");
                } else {
                    if (!paramName.equalsIgnoreCase(sessionName)) {
                        rtnMap.put("returnCode", "0010");
                        rtnMap.put("returnMsg", "회원정보와 본인인증 정보가 일치하지 않습니다.<br/>다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.");
                    }
                }
                return rtnMap;
            }

            // 그 외 이름, 주민번호 필수
            if (StringUtils.isBlank(paramName) || StringUtils.isBlank(userSsn)) {
                rtnMap.put("returnCode", "0010");
                rtnMap.put("returnMsg", "이름, 주민번호가 입력되지 않았습니다.<br/>새로고침 후 다시 시도해주세요.");
            } else {
                if ("00".equals(session.getUserDivision())) {
                    // 준회원
                    // 이름 체크
                    if (!paramName.equalsIgnoreCase(sessionName)) {
                        rtnMap.put("returnCode", "0010");
                        rtnMap.put("returnMsg", "회원정보와 본인인증 정보가 일치하지 않습니다.<br/>다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.");
                        return rtnMap;
                    }
                    NiceLogDto niceLogDto = new NiceLogDto();
                    niceLogDto.setName(name);
                    niceLogDto.setBirthDate(userSsn);
                    // nicepin di 가져오기
                    Map<String, String> nicePinRtn = nicePinService.getNicePinDi(niceLogDto);
                    if ("0000".equals(nicePinRtn.get("returnCode"))) {
                        // nicepin 연동 성공 - DI 체크
                        if (!session.getPin().equals(nicePinRtn.get("dupInfo"))) {
                            rtnMap.put("returnCode", "0010");
                            rtnMap.put("returnMsg", "회원정보와 본인인증 정보가 일치하지 않습니다.<br/>다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.");
                        }
                    } else {
                        // nicepin 연동 실패
                        rtnMap.put("returnCode", "9999");
                        rtnMap.put("returnMsg", nicePinRtn.get("returnMsg"));
                    }
                } else if ("01".equals(session.getUserDivision())) {
                    // 정회원
                    List<McpUserCntrMngDto> cntrList = this.selectCntrList(session.getUserId());
                    if (cntrList != null && cntrList.size() > 0) {
                        McpUserCntrMngDto cntrMngDto = cntrList.get(0);
                        // 이름, 주민번호 체크
                        String cntrName = cntrMngDto.getUserName().replaceAll(" ", "");
                        if (!paramName.equalsIgnoreCase(cntrName) || !userSsn.equals(cntrMngDto.getUnUserSSn())) {
                            rtnMap.put("returnCode", "0010");
                            rtnMap.put("returnMsg", "회원정보와 본인인증 정보가 일치하지 않습니다.<br/>다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.");
                        }
                    } else {
                        rtnMap.put("returnCode", "0010");
                        rtnMap.put("returnMsg", "회원정보가 확인되지 않습니다.<br/>로그아웃 후 이용 해 주시기 바랍니다.");
                    }
                } else {
                    rtnMap.put("returnCode", "0010");
                    rtnMap.put("returnMsg", "회원정보가 확인되지 않습니다.<br/>로그아웃 후 이용 해 주시기 바랍니다.");
                }
            }
        }
        return rtnMap;
    }

    @Override
    public List<SuspenChgTmlDto> selectChrgPrmtCheckTmp(){
        return mypageDao.selectChrgPrmtCheckTmp();
    }

    @Override
    public int updateChrgPrmtCheckTmp(SuspenChgTmlDto suspenChgTmlDto) {
        return mypageDao.updateChrgPrmtCheckTmp(suspenChgTmlDto);
    }

    @Override
    public int insertSuspenChgTmp(SuspenChgTmlDto suspenChgTmlDto) {
        return mypageDao.insertSuspenChgTmp(suspenChgTmlDto);
    }


    @Override
    public Map<String, String> selectRateMst(String rateCd) {
        return mypageDao.selectRateMst(rateCd);
    }

    public String getChangeOfNameData(long custReqSeq, String reqType) {

        String result = "SUCCESS";

        Map<String,String> requestData = new HashMap<String,String>();

        if("CL".equals(reqType)) { //통화내역열람
            requestData = custRequestDao.getReqCallListData(custReqSeq);
        }else if("NC".equals(reqType)) { //명의변경
            requestData = custRequestDao.getReqNmChgData(custReqSeq);
        }else if("IS".equals(reqType)) { //안심보험신청
            requestData = custRequestDao.getReqInsrData(custReqSeq);
        }else if("CC".equals(reqType)) { //해지상담
            requestData = custRequestDao.getCancelConsultData(custReqSeq);
        }

        if(requestData == null) {
            result ="FAIL";
        }

        return result;
    }

}