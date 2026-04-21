package com.ktmmobile.msf.domains.form.form.servicechange.service;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;
import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.MyCombinationDao;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpReqCombineDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyCombinationReqDto;
import com.ktmmobile.msf.domains.form.common.dto.MyCombinationResDto;
import com.ktmmobile.msf.domains.form.system.cert.dto.CertDto;
import com.ktmmobile.msf.domains.form.system.cert.service.CertService;
import com.ktmmobile.msf.domains.form.common.dto.AuthSmsDto;
import com.ktmmobile.msf.domains.form.common.dto.MoscCombChkReqDto;
import com.ktmmobile.msf.domains.form.common.dto.MoscCombReqDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombChkRes;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombChkResDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombChkResDto.MoscCombPreChkListOutDTO;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombDtlListOutDTO;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombDtlResDTO;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombInfoResDTO;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscMvnoComInfo;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscSubMstCombChgRes;
import com.ktmmobile.msf.domains.form.common.util.ObjectUtils;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringMakerUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

@Service
public class MsfMyCombinationSvclmpl implements MsfMyCombinationSvc {

    private static final Logger logger = LoggerFactory.getLogger(MsfMyCombinationSvclmpl.class);

    @Lazy
    @Autowired
    private MsfMplatFormService mplatFormService;

    @Autowired
    private MyCombinationDao myCombinationDao;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    CertService certService;

    /**
     * x87 mvno 결합 서비스 계약조회
     *
     * @param combinationReqDto
     * @return
     * @author bsj
     * @Date : 2021.12.30
     */

    @Override
    public MoscCombDtlResDTO selectCombiSvcList(MyCombinationReqDto combinationReqDto) {

        MoscCombDtlResDTO combSvcInfo = new MoscCombDtlResDTO();
        try {
            String custId = combinationReqDto.getCustId();
            String ncn = combinationReqDto.getNcn();
            String ctn = combinationReqDto.getCtn();
            combSvcInfo = mplatFormService.moscCombSvcInfoList(custId, ncn, ctn);
        } catch (SelfServiceException e) {
            throw new McpCommonException(e.getMessage());
        } catch (SocketTimeoutException e) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        }

        return combSvcInfo;
    }

    /**
     * 결합 가능 여부 체크
     *
     * @param
     * @param
     * @return
     * @author bsj
     * @Date : 2021.12.30
     */

    @Override
    public MyCombinationResDto selectMspCombRateMapp(String pRateCd) {

        logger.info("pRateCd==>" + pRateCd);

        //pRateCd = "KSLTEMSTD";
        RestTemplate restTemplate = new RestTemplate();
        MyCombinationResDto myCombinationResDto = restTemplate.postForObject(apiInterfaceServer + "/msp/mspCombRateMapp", pRateCd, MyCombinationResDto.class);

        if (myCombinationResDto != null) {
            return myCombinationResDto;
        } else {
            return null;
        }


    }

    /**
     * x77 mvno 개별서비스 조회
     *
     * @param moscCombReqDto
     * @return
     * @author bsj
     * @Date : 2021.12.30
     */

    @Override
    public MoscCombInfoResDTO selectCombiMgmtList(MoscCombReqDto moscCombReqDto) {

        MoscCombInfoResDTO moscCombInfoResDTO = null;

        try {
            moscCombInfoResDTO = mplatFormService.moscCombSvcInfo(moscCombReqDto);
        } catch(SocketTimeoutException e) {
            moscCombInfoResDTO = null;
        } catch (Exception e) {
            moscCombInfoResDTO = null;
        }

        return moscCombInfoResDTO;

    }

    @Override
    public MoscCombInfoResDTO selectCombiMgmtListLog(MoscCombReqDto moscCombReqDto,String prcsMdlInd , String contractNum) {
        MoscCombInfoResDTO moscCombInfoResDTO = null;
        try {
            moscCombInfoResDTO = mplatFormService.moscCombSvcInfoLog(moscCombReqDto,prcsMdlInd , contractNum);
        } catch(McpMplatFormException e) {
            moscCombInfoResDTO = null;
        } catch (Exception e) {
            moscCombInfoResDTO = null;
        }

        return moscCombInfoResDTO;

    }

    /**
     * x79 결합저장
     *
     * @param moscCombChkReqDto
     * @return
     * @author bsj
     * @Date : 2021.12.30
     */

    @Override
    public MoscCombChkResDto insertCombSaveInfo(MoscCombChkReqDto moscCombChkReqDto) {
        MoscCombChkResDto moscCombChkResDto = new MoscCombChkResDto();

        try {

            //x77 사전체크
            moscCombChkResDto = this.insertCombChkInfo(moscCombChkReqDto);

            if (!moscCombChkResDto.isSuccess()) {
                List<MoscCombPreChkListOutDTO> chkList = moscCombChkResDto.getMoscCombChkList();

                for (MoscCombPreChkListOutDTO outDto : chkList) {
                    if ("N".equals(outDto.getSbscYn())) {
                        moscCombChkResDto.setSbscYn("N");
                        break;
                    }
                }
            }

            //x79 결합저장
            moscCombChkResDto = new MoscCombChkResDto();
            moscCombChkResDto = mplatFormService.moscCombSaveInfo(moscCombChkReqDto);

        } catch (SelfServiceException e) {
            throw new McpCommonException(e.getMessage());
        } catch (SocketTimeoutException e) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        }
        return moscCombChkResDto;
    }


    /**
     * x79 결합저장
     *
     * @param moscCombChkReqDto
     * @return
     * @author bsj
     * @Date : 2021.12.30
     */

    @Override
    public MoscCombChkResDto moscCombSaveInfo(MoscCombChkReqDto moscCombChkReqDto) {
        MoscCombChkResDto moscCombChkResDto = new MoscCombChkResDto();

        try {
            //x79 결합저장
            moscCombChkResDto = new MoscCombChkResDto();
            moscCombChkResDto = mplatFormService.moscCombSaveInfo(moscCombChkReqDto);

        } catch (SocketTimeoutException e) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        }
        return moscCombChkResDto;
    }


    @Override
    public MoscCombChkRes  moscCombSaveInfoLog(MoscCombChkReqDto moscCombChkReqDto, String prcsMdlInd , String contractNum) throws SocketTimeoutException {
        MoscCombChkRes moscCombChkRes = new MoscCombChkRes();

        moscCombChkRes = mplatFormService.moscCombSaveInfoLog(moscCombChkReqDto,prcsMdlInd, contractNum);
        return moscCombChkRes;
    }

    /**
     * x78 결합사전체크
     *
     * @param moscCombChkReqDto
     * @return
     * @author bsj
     * @Date : 2021.12.30
     */

    @Override
    public MoscCombChkResDto insertCombChkInfo(MoscCombChkReqDto moscCombChkReqDto) {
        MoscCombChkResDto moscCombChkResDto = new MoscCombChkResDto();

        try {
            moscCombChkResDto = mplatFormService.moscCombChkInfo(moscCombChkReqDto);
        } catch (SocketTimeoutException e) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        }

        return moscCombChkResDto;
    }

    /**
     * x78 결합사전체크
     *
     * @param moscCombChkReqDto
     * @return
     * @author bsj
     * @Date : 2021.12.30
     */

    @Override
    public MoscCombChkRes insertCombChkInfoLog(MoscCombChkReqDto moscCombChkReqDto,String prcsMdlInd, String contractNum) throws SocketTimeoutException{
        MoscCombChkRes moscCombChkRes = new MoscCombChkRes();

        moscCombChkRes = mplatFormService.moscCombChkInfoLog(moscCombChkReqDto, prcsMdlInd , contractNum);
        return moscCombChkRes;
    }

    @Override
    public boolean insertMcpReqCombine(McpReqCombineDto mcpReqCombineDto) {
        return myCombinationDao.insertMcpReqCombine(mcpReqCombineDto);
    }

    @Override
    public List<McpReqCombineDto> selectMcpReqCombine(McpReqCombineDto mcpReqCombineDto) {
        return myCombinationDao.selectMcpReqCombine(mcpReqCombineDto);
    }

    @Override
    public MoscSubMstCombChgRes moscSubMstCombChg(String ncn, String ctn, String custId, String mstSvcContId)  {
        MoscSubMstCombChgRes moscSubMstCombChgRes = null;
        try {
            moscSubMstCombChgRes = mplatFormService.moscSubMstCombChg(ncn,ctn , custId ,mstSvcContId);
        } catch(McpMplatFormException e) {
            moscSubMstCombChgRes = null;
        } catch (Exception e) {
            moscSubMstCombChgRes = null;
        }

        return moscSubMstCombChgRes;
    }

    @Override
    public Map<String, Object> selectCombMgmtList(AuthSmsDto prntDto, AuthSmsDto childDto, String prcsMdlInd) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //MP연동 X77
        MoscCombReqDto moscCombReqDto = new MoscCombReqDto();
        moscCombReqDto.setCustId(prntDto.getCustId());
        moscCombReqDto.setNcn(prntDto.getSvcCntrNo());
        moscCombReqDto.setCtn(prntDto.getPhoneNum());
        moscCombReqDto.setCombSvcNoCd("K");  //결합 모회선 사업자 구분 코드	1	O	M: MVNO회선, K:KT 회선
        moscCombReqDto.setCombSrchId(childDto.getSubLinkName()); // 결합 모회선 조회값	10	O	"MVNO회선은 전화번호        KT 회선은 이름"
        moscCombReqDto.setSvcIdfyNo(childDto.getBirthday()); // 생년월일 ex) 19821120 (KT회선일 경우 필수)
        moscCombReqDto.setSexCd(childDto.getSexCd()); // 성별코드	1	C	1: 남성, 2: 여성 (KT 회선일 경우 필수)
        moscCombReqDto.setCmbStndSvcNo(childDto.getCtn());  // 모바일 회선: 전화번호        인터넷: ID "
        moscCombReqDto.setSameCustKtRetvYn(childDto.getSameCustYn()); // 동일명의 여부

        MoscCombInfoResDTO moscCombInfoResDTO = this.selectCombiMgmtListLog(moscCombReqDto, prcsMdlInd , prntDto.getContractNum());
        MoscMvnoComInfo parentObj = null;
        String childCobun = "N"; //아래 회선 결합 여부

        if (moscCombInfoResDTO != null && moscCombInfoResDTO.isSuccess()) {
            parentObj = moscCombInfoResDTO.getMoscMvnoComInfo();
            if ("Y".equals(parentObj.getCombYn())) {
                rtnMap.put("RESULT_CODE", "0006");
                rtnMap.put("RESULT_MSG", "kt m 모바일 회선이 이미 결합된 회선입니다. ");
                return rtnMap;
            }

            // 본인명의 회선 선택시
            if ("Y".equals(childDto.getSameCustYn())) {
                String indvinfoAgree = parentObj.getIndvInfoAgreeMsgSbst();
                String containsStr = "IT".equals(childDto.getSvcNoTypeCd()) ? "인터넷" : "모바일";
                //MVNO 정보제공동의 체크
                if (!"".equals(indvinfoAgree)) {
                    boolean containsInternet = indvinfoAgree.contains(containsStr);
                    if (containsInternet) { // KT 정보 제공 동의 - 미동의 체크
                        rtnMap.put("RESULT_CODE", "1002");
                        rtnMap.put("RESULT_MSG", "KT고객센터로 전화하여<br>MVNO사업자에 정보 제공 동의 후<br>조회가 가능합니다.");
                        return rtnMap;
                    }
                }

                //KT 회선 체크
                List<MoscMvnoComInfo> combList = new ArrayList<MoscMvnoComInfo>();
                if (moscCombInfoResDTO.getMoscSrchCombInfoList() != null) {
                    int idx = 0;
                    for (MoscMvnoComInfo item : moscCombInfoResDTO.getMoscSrchCombInfoList()) {
                        if (containsStr.equals(item.getSvcDivCd())) {
                            if ("IT".equals(childDto.getSvcNoTypeCd())) { // 인터넷일때는 가입일이 전월인 항목만 노출
                                String contOpnDt = item.getSvcContOpnDt(); //가입일
                                String svcNo = item.getSvcNo(); //인터넷 등 유선 상품인 경우 인터넷ID
                                LocalDate today = LocalDate.now(); // 현재 날짜

                                // contOpnDt를 LocalDate 객체로 변환
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                                LocalDate openDate = LocalDate.parse(contOpnDt, formatter);

                                // 오늘 날짜의 연도와 월
                                int currentYear = today.getYear();
                                int currentMonth = today.getMonthValue();

                                // 비교할 날짜의 연도와 월
                                int openYear = openDate.getYear();
                                int openMonth = openDate.getMonthValue();

                                // 전월 또는 이번달 인지 체크
                                boolean isCurrentOrPreviousMonth = (currentYear == openYear && currentMonth == openMonth) ||
                                        (currentYear == openYear && currentMonth == openMonth + 1) ||
                                        (currentYear == openYear + 1 && currentMonth == 1 && openMonth == 12);

                                if (isCurrentOrPreviousMonth) {
                                    item.setMskSvcNo(StringMakerUtil.getUserId(svcNo));
                                    combList.add(item);
                                }

                            } else {
                                item.setMskSvcNo(StringMakerUtil.getPhoneNum(item.getSvcNo()));
                                combList.add(item);
                            }
                            idx++;
                            item.setSeq(childDto.getSvcNoTypeCd() + idx);
                            String openDtArr [] = StringUtil.getDateSplit(item.getSvcContOpnDt());
                            item.setSvcContOpnDt(openDtArr[0]+"-"+openDtArr[1]+"-"+openDtArr[2]);
                        }
                    }
                }

                // 회선리스트 임시 저장
                SessionUtils.saveCombineList(combList);
                rtnMap.put("combList", combList);
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                return rtnMap;
            } else {
                // 가족회선 선택시
                if (moscCombInfoResDTO.getMoscSrchCombInfoList() != null) {
                    for (MoscMvnoComInfo item : moscCombInfoResDTO.getMoscSrchCombInfoList()) {
                        logger.info("item==>" + ObjectUtils.convertObjectToString(item));
                        //TO_DO 검증필요
                        /*
                         * 모회선이  결합이 안되 있고
                         * 자회선이 결합이면... ?????
                         * 자회선이 결합 이면.. 결합 할 수 없음..
                         * 자회선... getCombYn  결합 할 수 없음....
                         */
                        if (childDto.getCtn().equals(item.getSvcNo()) && "Y".equals(item.getCombYn())) {
                            childCobun = "Y";  //child 회선 결합 상태
                        }
                    }
                }
                rtnMap.put("childCobun", childCobun);
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                return rtnMap;
            }
        } else {
            String strMsg = "개별 서비스 조회(X77) 실패 하였습니다. ";
            if (moscCombInfoResDTO != null) {
                strMsg = moscCombInfoResDTO.getSvcMsg();
            }
            rtnMap.put("RESULT_CODE", "1001");
            rtnMap.put("RESULT_MSG", strMsg);
            return rtnMap;
        }
    }

    @Override
    public Map<String, Object> getCombChkInfo(AuthSmsDto prntDto, AuthSmsDto childDto, String prcsMdlInd, String childCobun) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        //X78 사전체크
        MoscCombChkReqDto moscCombChkReqDto = new MoscCombChkReqDto();
        moscCombChkReqDto.setCustId(prntDto.getCustId());
        moscCombChkReqDto.setNcn(prntDto.getSvcCntrNo());
        moscCombChkReqDto.setCtn(prntDto.getPhoneNum());
        if ("Y".equals(childCobun)) {
            moscCombChkReqDto.setJobGubun("A");  //jobGubun	작업구분코드	1	M	N:신규결합신청, A:회선추가, D:회선 삭제
        } else {
            moscCombChkReqDto.setJobGubun("N");  //jobGubun	작업구분코드	1	M	N:신규결합신청, A:회선추가, D:회선 삭제
        }
        moscCombChkReqDto.setSvcNoCd("K");   //M: MVNO회선, K:KT 회선
        moscCombChkReqDto.setSvcNoTypeCd(childDto.getSvcNoTypeCd()); // "인터넷:IT  모바일:MB         MVNO회선일 경우 MB만 가능"
        moscCombChkReqDto.setCmbStndSvcNo(childDto.getCtn());
        moscCombChkReqDto.setCustNm(childDto.getSubLinkName());
        moscCombChkReqDto.setSvcIdfyTypeCd("1");  //(KT회선일경우 필수) KT 회선, 회선추가, KT모바일일 경우 아래의 코드 중 선택하여 연동 생년월일:1, 법인번호:3, 사업자번호:7,여권번호:2, 기타:99 그 외의 경우 생년월일코드 1으로 연동
        moscCombChkReqDto.setSvcIdfyNo(childDto.getBirthday());
        moscCombChkReqDto.setSexCd(childDto.getSexCd());
        moscCombChkReqDto.setAplyMethCd("04"); // 창구방문 04

        if (!"Y".equals(childDto.getSameCustYn())) {
            // 가족명의 선택후 본인정보 입력시 리턴
            if (prntDto.getBirthdayOfYYYY().equals(childDto.getBirthday()) && prntDto.getSubLinkName().equals(childDto.getSubLinkName()) && prntDto.getSexCdOfSSn().equals(childDto.getSexCd())) {
//            rtnMap.put("RESULT_IS_OWN", "01");
                rtnMap.put("RESULT_CODE", "0100");
                rtnMap.put("RESULT_MSG", "본인명의의 경우<br>“본인명의”를 선택하여 진행 바랍니다.");
                return rtnMap;
            }
            moscCombChkReqDto.setAplyRelatnCd("11"); //신청관계 01 본인        11 대리인
        } else {
            moscCombChkReqDto.setAplyRelatnCd("01"); //신청관계 01 본인        11 대리인
//            rtnMap.put("RESULT_IS_OWN", "00");
        }

        moscCombChkReqDto.setAplyNm(prntDto.getSubLinkName());
        if (!StringUtils.isBlank(childDto.getHomeCombTerm())) {
            moscCombChkReqDto.setHomeCombTerm(childDto.getHomeCombTerm());
        }

        MoscCombChkRes moscCombChkRes = null;
        try {
            moscCombChkRes = this.insertCombChkInfoLog(moscCombChkReqDto, prcsMdlInd , prntDto.getContractNum());
        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "0007");
            if ("ITL_SFC_E110".equals(e.getResultCode())) {
                rtnMap.put("RESULT_MSG", "결합중인 회선입니다. 결합신청이 불가합니다.");
            }else if("ITL_SYS_E00001".equals(e.getResultCode())) {
                rtnMap.put("RESULT_MSG", "결합 중인 회선이 많아<br>추가 결합이 되지 않습니다.");
            } else {
                rtnMap.put("RESULT_MSG", e.getMessageNe());
            }
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "0008");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            return rtnMap;
        }

        /*
        성공여부
        리스트에 N이 한개라도 있으면 결합이 안되는 것으로 판단 하시면 됩니다  .M-Platform운영 2022-12-16 (금) 17:39:22
         */
        boolean isRegYn = false;
        String resltMsg = "";

        if (moscCombChkRes != null && moscCombChkRes.isSuccess()) {

            List<MoscCombChkRes.MoscCombPreChkListOut> moscCombPreChkList = moscCombChkRes.getMoscCombChkList();
            if (moscCombPreChkList == null || moscCombPreChkList.size() < 1) {
                isRegYn = true;
            } else {
                isRegYn = true;
                for (MoscCombChkRes.MoscCombPreChkListOut item : moscCombPreChkList) {
                    if ("N".equals(item.getSbscYn())) {
                        resltMsg = item.getResltMsg();
                        isRegYn = false;
                        break;
                    }
                }
            }

            /*하위에 msg 없으며  상위 msg 담기*/
            if ("".equals(resltMsg)) {
                resltMsg = moscCombChkRes.getResltMsg();
            }

            if (!"Y".equals(moscCombChkRes.getSbscYn())) {
                isRegYn = false;
            }

            if (isRegYn) {
                // 사전체크 성공
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                return rtnMap;
            } else {
                rtnMap.put("RESULT_CODE", "0009");
                rtnMap.put("RESULT_MSG", resltMsg);
                return rtnMap;
            }
        } else {
            rtnMap.put("RESULT_CODE", "0010");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            return rtnMap;
        }
    }

    @Override
    public Map<String, Object> regCombineKt(String prcsMdlInd) {
        AuthSmsDto pareSession = new AuthSmsDto();
        pareSession.setMenu("combine");
        pareSession = SessionUtils.getAuthSmsBean(pareSession);

        //모회선 확인
        if (pareSession == null || StringUtils.isBlank(pareSession.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        AuthSmsDto childAutSession = new AuthSmsDto();
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        childAutSession.setMenu("combineChild");
        childAutSession = SessionUtils.getAuthSmsBean(childAutSession);


        //자회선 확인
        if (childAutSession == null || StringUtils.isBlank(childAutSession.getSvcCntrNo())) {
            throw new McpCommonJsonException("0002", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        if (!childAutSession.isCheck()) {
            throw new McpCommonJsonException("0003", "결합인증 정보를 확인 할 수 없습니다.");
        }

        //가족결합일때만 스탭 체크
        if (!"Y".equals(childAutSession.getSameCustYn())) {
            // ============ 자회선 STEP START ============
            // 결합대상 구분값, 핸드폰번호, 이름, 생년월일
            String[] certKey = {"urlType", "ncType", "mobileNo", "name", "birthDate"};
            String[] certValue = {"regChildCombineKt", "1", childAutSession.getPhoneNum(), childAutSession.getSubLinkName(), childAutSession.getBirthday()};
            Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                rtnMap.put("RESULT_CODE", "STEP01");
                rtnMap.put("RESULT_MSG", vldReslt.get("RESULT_DESC"));
                return rtnMap;
            }
            //============ 자회선 STEP END ============
        }

        // ============ 모회선 STEP START ============
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            // 결합대상 구분값, 이름, 핸드폰번호, 계약번호
            String[] certKey = new String[]{"urlType", "ncType", "name", "mobileNo", "contractNum"};
            String[] certValue = new String[]{"regCombineKt", "0", pareSession.getSubLinkName(), pareSession.getPhoneNum(), pareSession.getContractNum()};
            Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                rtnMap.put("RESULT_CODE", "STEP02");
                rtnMap.put("RESULT_MSG", vldReslt.get("RESULT_DESC"));
                return rtnMap;
            }
        }
        // ============ 모회선 STEP END ==============

        //M전산 해당 요금제에 대한 결합 가능 여부 확인
        MyCombinationResDto myCombination = this.selectMspCombRateMapp(pareSession.getRateCd());
        if (myCombination == null) {
            rtnMap.put("RESULT_CODE", "0009");
            rtnMap.put("RESULT_MSG", "해당 상품은 결합이 불가합니다. ");
            return rtnMap;
        }


        McpReqCombineDto reqCombine = new McpReqCombineDto();

        //결합 신청(X79)
        MoscCombChkReqDto moscCombChkReqDto = new MoscCombChkReqDto();
        moscCombChkReqDto.setCustId(pareSession.getCustId());
        moscCombChkReqDto.setNcn(pareSession.getSvcCntrNo());
        moscCombChkReqDto.setCtn(pareSession.getPhoneNum());
        moscCombChkReqDto.setJobGubun(childAutSession.getJobGubun());  //jobGubun	작업구분코드	1	M	N:신규결합신청, A:회선추가, D:회선 삭제
        moscCombChkReqDto.setSvcNoCd("K");   //M: MVNO회선, K:KT 회선
        moscCombChkReqDto.setSvcNoTypeCd(childAutSession.getSvcNoTypeCd()); // "인터넷:IT  모바일:MB         MVNO회선일 경우 MB만 가능"
        moscCombChkReqDto.setCmbStndSvcNo(childAutSession.getCtn());
        moscCombChkReqDto.setCustNm(childAutSession.getSubLinkName());
        moscCombChkReqDto.setSvcIdfyTypeCd("1");  //(KT회선일경우 필수) KT 회선, 회선추가, KT모바일일 경우 아래의 코드 중 선택하여 연동 생년월일:1, 법인번호:3, 사업자번호:7,여권번호:2, 기타:99 그 외의 경우 생년월일코드 1으로 연동
        moscCombChkReqDto.setSvcIdfyNo(childAutSession.getBirthday());
        moscCombChkReqDto.setSexCd(childAutSession.getSexCd());
        moscCombChkReqDto.setAplyMethCd("04"); //  창구방문 04
        moscCombChkReqDto.setAplyNm(pareSession.getSubLinkName());


        if (pareSession.getBirthdayOfYYYY().equals(childAutSession.getBirthday()) && pareSession.getSubLinkName().equals(childAutSession.getSubLinkName()) && pareSession.getSexCdOfSSn().equals(childAutSession.getSexCd())) {
            moscCombChkReqDto.setAplyRelatnCd("01"); //????  신청관계 01 본인        11 대리인
            reqCombine.setCombTgtTypeCd("01");     // '결합대상 (01: 본인, 02: 가족, 03: 타인)';
            reqCombine.setRsltCd("S");            // '승인여부, R:승인대기, N:미제출, S:승인완료, B:승인반려, C:신청취소,, H:임의보류  ';  <=== 확인 필요
            rtnMap.put("APLY_RELATN_CD", "01");//????  신청관계 01 본인        11 대리인


            /*
             * X79
             * 엠모바일 + KT 결합 본인 결합 신청 시
             * MVNO 결합 신청(X79) 호출
             */
            if (!StringUtils.isBlank(childAutSession.getHomeCombTerm())) {
                moscCombChkReqDto.setHomeCombTerm(childAutSession.getHomeCombTerm());
            }
            MoscCombChkRes moscCombChkRes = null;
            try {
                moscCombChkRes = this.moscCombSaveInfoLog(moscCombChkReqDto, prcsMdlInd, pareSession.getContractNum());
            } catch (SocketTimeoutException e) {
                logger.info("X79 [CALL][CALL][CALL]SocketTimeoutException==>");
                rtnMap.put("RESULT_CODE", "1999");
                rtnMap.put("RESULT_MSG", ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
                return rtnMap;
            } catch (Exception e) {
                rtnMap.put("RESULT_CODE", "0008");
                rtnMap.put("RESULT_MSG", ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
                return rtnMap;
            }

            if (!moscCombChkRes.isSuccess()) {
                rtnMap.put("RESULT_CODE", "0009");
                rtnMap.put("RESULT_MSG", moscCombChkRes.getSvcMsg());

                //성공여부
                boolean isRegYn = false;
                /**
                 * SocketTimeoutException 이후
                 * 다시 시도 할 때.. 결합 성공 여부 체크 로직 ..... .추가
                 * 결합 여부 확인
                 */
                // 결합 서비스 계약 조회 X87
                MoscCombDtlResDTO combSvcInfo = new MoscCombDtlResDTO();
                try {
                    combSvcInfo = mplatFormService.moscCombSvcInfoList(pareSession.getCustId(), pareSession.getSvcCntrNo(), pareSession.getCtn());
                } catch (SocketTimeoutException e) {
                    logger.info("X87 조회 오류[SocketTimeoutException]");
                } catch (Exception e) {
                    logger.info("X87 조회 오류");
                }

                if (combSvcInfo != null && combSvcInfo.isSuccess()) {
                    List<MoscCombDtlListOutDTO> combDtlList = combSvcInfo.getCombList();  //결합 리스트
                    for (MoscCombDtlListOutDTO subItem : combDtlList) {
                        String svcContDivNm = subItem.getSvcContDivNm();
                        if (subItem.getSvcNo().equals(childAutSession.getCtn())) { //결합 해야 할 회선 존재....
                            //모회선 , 자회선 두개로 결합이 완성.......한 경우...
                            //성공으로 ...
                            logger.info("X79 [WOO][WOO][WOO]isRegYn truetruetruetrue ==>");
                            isRegYn = true;
                            break;
                        }
                    }
                }

                if (!isRegYn) {
                    return rtnMap;
                }

            } else {

                //성공여부
                boolean isRegYn = false;
                String resltMsg = "";

                //리스트에 N이 한개라도 있으면 결합이 안되는 것으로 판단 하시면 됩니다  .M-Platform운영 2022-12-16 (금) 17:39:22
                List<MoscCombChkRes.MoscCombPreChkListOut> moscCombPreChkList = moscCombChkRes.getMoscCombChkList();

                if (moscCombPreChkList == null || moscCombPreChkList.size() < 1) {
                    isRegYn = true;
                } else {
                    isRegYn = true;
                    for (MoscCombChkRes.MoscCombPreChkListOut item : moscCombPreChkList) {
                        if ("N".equals(item.getSbscYn())) {
                            isRegYn = false;
                            resltMsg = item.getResltMsg();
                            break;
                        }
                    }
                }

                /*하위에 msg 없으며  상위 msg 담기*/
                if ("".equals(resltMsg)) {
                    resltMsg = moscCombChkRes.getResltMsg();
                }

                if (!"Y".equals(moscCombChkRes.getSbscYn())) {
                    isRegYn = false;
                }

                if (!isRegYn) {
                    rtnMap.put("RESULT_CODE", "0009");
                    rtnMap.put("RESULT_MSG", resltMsg);
                    return rtnMap;
                }
            }
            /*
             * X79  호출 끝~!
             */


        } else {
            moscCombChkReqDto.setAplyRelatnCd("01"); //????  신청관계 01 본인        11 대리인
            reqCombine.setCombTgtTypeCd("02");     // '결합대상 (01: 본인, 02: 가족, 03: 타인)'; <=== 확인 필요
            reqCombine.setRsltCd("R");            // '승인여부, R:승인대기, N:미제출, S:승인완료, B:승인반려, C:신청취소,, H:임의보류  '; <=== 확인 필요
            rtnMap.put("APLY_RELATN_CD", "11"); //????  신청관계 01 본인        11 대리인
        }

        //결과 DB 저장 처리
        if ("MB".equals(childAutSession.getSvcNoTypeCd())) {
            reqCombine.setCombTypeCd("02");   //결합유형 (01: ktM+ktM, 02: ktM+kt무선, 03:* ktM+kt유선)
            //reqCombine.setCombDtlTypeCd("02");     // '유무선결합 유형 (01: ktM, 02: kt무선, 03: kt유선)';
        } else if ("IT".equals(childAutSession.getSvcNoTypeCd())) {
            reqCombine.setCombTypeCd("03");   //결합유형 (01: ktM+ktM, 02: ktM+kt무선, 03:* ktM+kt유선)
            //reqCombine.setCombDtlTypeCd("03");     // '유무선결합 유형 (01: ktM, 02: kt무선, 03: kt유선)';
        } else {
            //오류 처리 ???
            reqCombine.setCombTypeCd("00");   //결합유형 (01: ktM+ktM, 02: ktM+kt무선, 03:* ktM+kt유선)
            //reqCombine.setCombDtlTypeCd("00");     // '유무선결합 유형 (01: ktM, 02: kt무선, 03: kt유선)';
        }
        reqCombine.setmCtn(pareSession.getPhoneNum()); //엠모바일 회선번호
        reqCombine.setmCustNm(pareSession.getSubLinkName()); //엠모바일 고객이름
        reqCombine.setmCustBirth(pareSession.getBirthdayOfYYYY()); //  '엠모바일 생년월일';
        reqCombine.setmSexCd("0" + pareSession.getSexCdOfSSn()); //     엠모바일 성별 (01: 남자, 02: 여자)';
        reqCombine.setmSvcCntrNo(pareSession.getContractNum()); //엠모바일 회선번호  pareSession.getContractNum()  getSvcCntrNo
        reqCombine.setmRateCd(pareSession.getRateCd()); //엠모바일 상품코드  M_RATE_CD
        reqCombine.setmRateNm(pareSession.getRateNm()); //엠모바일 상품명
        reqCombine.setmRateAdsvcCd(myCombination.getrRateCd()); //엠모바일 부가코드
        reqCombine.setmRateAdsvcNm(myCombination.getrRateNm()); //엠모바일 부가서비스명
        reqCombine.setCombSvcNo(childAutSession.getCtn());         // '결합 회선번호 (모바일번호 or 인터넷서비스번호)';
        //reqCombine.setCombSvcCntrNo();     // '무무 자회선 엠모바일 계약번호';
        reqCombine.setCombCustNm(childAutSession.getSubLinkName());        // '결합자 이름';
        reqCombine.setCombBirth(childAutSession.getBirthday()); //'결합자 생년월일';
        reqCombine.setCombSexCd("0" + childAutSession.getSexCd());         // '결합자 성별 (01: 남자, 02: 여자)';
        //reqCombine.setCombSocCd();         // '결합회선 상품코드 무무결합인경우만 해당';
        //reqCombine.setCombSocNm();         // '결합회선 상품명 무무결합인경우만 해당';
        //reqCombine.setCombRateAdsvcCd();   // '결합회선 부가코드 무무결합인경우만 해당';
        //reqCombine.setCombRateAdsvcNm();   // '결합회선 부가서비스명 무무결합인경우만 해당';

        if (this.insertMcpReqCombine(reqCombine)) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("REQ_SEQ", reqCombine.getReqSeq());

            // ======================= STEP START =======================
            CertDto certDto = new CertDto();
            certDto.setUrlType("saveCombineKtSeq");
            certDto.setReqSeq(reqCombine.getReqSeq());
            certDto.setCompType("C");
            certService.getCertInfo(certDto);
            // ======================= STEP END =======================

        } else {
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.DB_EXCEPTION);
        }
        return rtnMap;
    }


}
