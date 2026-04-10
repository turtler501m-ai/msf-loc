package com.ktmmobile.mcp.mypage.controller;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.appform.dto.AppformReqDto;
import com.ktmmobile.mcp.appform.service.AppformSvc;
import com.ktmmobile.mcp.common.dto.McpIpStatisticDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.*;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.RwdService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class RwdController {

    private static final Logger logger = LoggerFactory.getLogger(RwdController.class);

    @Autowired
    private RwdService rwdService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private AppformSvc appformSvc;

    /**
     * 2023.02.22 hsy
     * 자급제 보상 서비스 신청 페이지
     * @param request
     * @param searchVO
     * @param model
     * @return string(pageUrl)
     */
    @RequestMapping(value= {"/mypage/reqRwd.do","/m/mypage/reqRwd.do"})
    public String reqRwd(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, ModelMap model) {

        // 1. 리턴페이지 설정
        String jspPageName = "/portal/mypage/reqRwd";
        String thisPageName ="/mypage/reqRwd.do";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            jspPageName = "/mobile/mypage/reqRwd";
            thisPageName ="/m/mypage/reqRwd.do";
        }

        // 2. 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(thisPageName);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        // 3. 로그인 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";

        // 4. 정회원 체크
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            // 4-1. 정회원이 아닌 경우 정회원 인증 페이지로 이동
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("cntrList", cntrList);

        // 5. 세션 초기화
        SessionUtils.saveNiceRes(null);
        SessionUtils.saveNiceRwdRes(null);

        return jspPageName;
    }

    /**
     * 2023.03.02 hsy
     * 자급제 보상 서비스 신청 상태 조회
     * @param rwdOrderDto
     * @return Map<String,String>
     */
    @RequestMapping(value = "/mypage/selectRwdRegStatus.do")
    @ResponseBody
    public Map<String,String> selectRwdRegStatus(@ModelAttribute RwdOrderDto rwdOrderDto) {

        Map<String,String> rtnMap= new HashMap<>();
        rtnMap.put("rwdRegStatusCd", "CALL"); // default 세팅

        if(StringUtil.isNotNull(rwdOrderDto.getContractNum())){
            String rwdRegStatusCd= rwdService.selectRwdInfoByCntrNum(rwdOrderDto.getContractNum());
            rtnMap.put("rwdRegStatusCd", rwdRegStatusCd);
        }

        return rtnMap;
    }

    /**
     * 2023.03.02 hsy
     * 자급제 보상 서비스 리스트 가져오기
     * @return RwdDTO[]
     */
    @RequestMapping(value = "/mypage/selectRwdProdListAjax.do")
    @ResponseBody
    public RwdDto[] selectRwdProdList() {

        String rwdProdCd= "ALL";
        RwdDto[] rwdProdList= rwdService.selectRwdProdList(rwdProdCd);
        return rwdProdList;
    }

    /**
     * 2023.03.10 hsy
     * 자급제 보상서비스 가입 시 해당 imei 사용가능 여부 체크
     * @param rwdOrderDto
     * @return Map<String,String>
     */
    @RequestMapping(value = "/mypage/checkImeiForRwd.do")
    @ResponseBody
    public Map<String,String> checkImeiForRwd(@ModelAttribute RwdOrderDto rwdOrderDto) {

        Map<String,String> rtnMap= new HashMap<>();

        // imei, imeiTwo (둘 중 하나는 필수)
        String imei= StringUtil.NVL(rwdOrderDto.getImei(), "");
        String imeiTwo= StringUtil.NVL(rwdOrderDto.getImeiTwo(), "");

        if("".equals(imei) && "".equals(imeiTwo)){
            throw new McpCommonJsonException("FAIL", "올바른 단말기 정보값을 입력해주세요.");
        }

        if( (!"".equals(imei) && imei.length() != 15) || (!"".equals(imeiTwo) && imeiTwo.length() != 15) ){
            throw new McpCommonJsonException("FAIL", "올바른 단말기 정보값을 입력해주세요.");
        }

        // 사용 가능여부 체크
        String possibleYn= rwdService.checkImeiForRwd(imei, imeiTwo);
        rtnMap.put("RESULT_CODE", "SUCCESS");
        rtnMap.put("possibleYn", possibleYn);
        return rtnMap;
    }

    /**
     * 2023.02.27 hsy
     * 자급제 보상 서비스 신청 ajax
     * @param request
     * @param rwdOrderDto
     * @return Map<String, Object>
     */

    @RequestMapping(value = {"/mypage/rwdRegAjax.do", "/m/mypage/rwdRegAjax.do"})
    @ResponseBody
    public Map<String, String> reviewRegAjax(HttpServletRequest request	, @ModelAttribute RwdOrderDto rwdOrderDto){

        HashMap<String, String> rtnMap;

        // 자급제 보상 서비스 가입 실패 로그 기록용
        RwdFailHistDto rwdFailHistDto= new RwdFailHistDto();

        // 1. 로그인 여부 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("LOGIN SESSION IS NULL");
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0001", NO_FRONT_SESSION_EXCEPTION);
        }

        rwdOrderDto.setRegstId(userSession.getUserId());
        rwdFailHistDto.setUserId(userSession.getUserId());

        // 2. 엠모바일 회선과 로그인한 고객 일치 여부 체크
        boolean customerCntrChek= false;
        String contractNum=  StringUtil.NVL(rwdOrderDto.getContractNum(), "");
        rwdFailHistDto.setContractNum(contractNum);

        String contractUserNm= "";         // 사용자명 세팅
        String contractUserBirthday= "";   // 사용자 생년월일

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        if(Optional.ofNullable(cntrList).isPresent() && !"".equals(contractNum)) {
            for(McpUserCntrMngDto dto : cntrList){
                if(contractNum.equals(dto.getContractNum())){
                    customerCntrChek = true;
                    rwdOrderDto.setSmsRcvNo(dto.getCntrMobileNo()); // 문자 전송 번호 세팅
                    contractUserNm= dto.getUserName().toUpperCase().replaceAll(" ", "");
                    try{
                        contractUserBirthday=  EncryptUtil.ace256Dec(dto.getUnUserSSn());
                    }catch(CryptoException e){
                        contractUserBirthday= "";
                    }
                    break;
                }
            }
        }

        if(!customerCntrChek){
            // RWD 신청 실패 로그 기록
            if("".equals(contractNum)) rwdFailHistDto.setFailRsn("CONTRACT_NUM IS NULL");
            else rwdFailHistDto.setFailRsn("CONTRACT_NUM IS NOT MATCHED WITH LOGIN USER");
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0002", "로그인 정보가 다릅니다.");
        }

        // 3. 계약번호로 requestKey와 resNo 값 가져오기
        RwdOrderDto[] mcpData= rwdService.selectMcpRequestInfoByCntrNum(contractNum);
        if(mcpData == null || mcpData.length < 1 || mcpData[0].getRequestKey() == 0){
            // 신청상이로 requestKey와 resNo 존재하지 않음
            rwdOrderDto.setRequestKey(0);
            rwdOrderDto.setResNo(null);
        }else{
            rwdOrderDto.setRequestKey(mcpData[0].getRequestKey());
            rwdOrderDto.setResNo(mcpData[0].getResNo());
            rwdFailHistDto.setRequestKey(rwdOrderDto.getRequestKey());
            rwdFailHistDto.setResNo(rwdOrderDto.getResNo());
        }

        // 4.자급제 보상서비스 가입 가능여부 체크
        String rwdRegStatusCd= rwdService.selectRwdInfoByCntrNum(contractNum);
        if(!"POSSIBLE".equals(rwdRegStatusCd)){
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("RWD STATUS IS NOT POSSIBLE : [STATUS] " + rwdRegStatusCd);
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0003", "보상서비스 가입 불가 상태입니다.");
        }

        // 5. 본인인증 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        String dtoAuthType = StringUtil.NVL(rwdOrderDto.getOnlineAuthType(), "");
        contractUserBirthday= (contractUserBirthday == null || contractUserBirthday.length() < 6) ? "" : contractUserBirthday.substring(0,6);

        String inpName= (rwdOrderDto.getAuthCstmrName() == null) ? "" : rwdOrderDto.getAuthCstmrName().toUpperCase().replaceAll(" ", "");

        if (sessNiceRes == null
            || sessNiceRes.getConnInfo() == null
            || !sessNiceRes.getAuthType().equals(dtoAuthType)
            || !sessNiceRes.getName().replaceAll(" ", "").equals(inpName)
            || sessNiceRes.getBirthDate().indexOf(rwdOrderDto.getAuthBirthDate()) < 0
            || !sessNiceRes.getName().equals(contractUserNm)
            || sessNiceRes.getBirthDate().indexOf(contractUserBirthday) < 0) {

            // 이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("FORM_AUTH_ERROR");
            if (sessNiceRes == null) {
                mcpIpStatisticDto.setPrcsSbst("sessNiceRes is NULL");
            } else {
                mcpIpStatisticDto.setPrcsSbst("AuthType[" + sessNiceRes.getAuthType() + "] Name[" + sessNiceRes.getName() + "] BirthDate[" + sessNiceRes.getBirthDate() + "] ");
            }

            mcpIpStatisticDto.setParameter("AuthType[" + dtoAuthType + "] Name[" + rwdOrderDto.getAuthCstmrName() + "] BirthDate[" + rwdOrderDto.getAuthBirthDate() + "] ");

            // 5-1. 본인인증한 정보는 올바르나, 해당 계약번호의 사용자 정보가 일치하지 않는 경우
//            if(sessNiceRes != null && (!sessNiceRes.getName().equals(contractUserNm) ||  sessNiceRes.getBirthDate().indexOf(contractUserBirthday) < 0)){ // 기존로직
            if(sessNiceRes != null && (!sessNiceRes.getName().equals(contractUserNm) || !sessNiceRes.getBirthDate().contains(contractUserBirthday))) {  //취약성 388 394 395
                mcpIpStatisticDto.setParameter("AuthType[" + dtoAuthType + "] Name[" + contractUserNm + "] BirthDate[" + contractUserBirthday + "] ");
            }

            mcpIpStatisticDto.setTrtmRsltSmst("saveRwdformDbAjax");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("SESSION NICE RES INFO IS WRONG");
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0004", NICE_CERT_EXCEPTION);
        }

        // 6. 선택한 자급제 보상서비스 존재여부 체크
        String rwdProdCd= StringUtil.NVL(rwdOrderDto.getRwdProdCd(), "");
        RwdDto[] rwdProdList = rwdService.selectRwdProdList(rwdProdCd);

        if("".equals(rwdProdCd) || rwdProdList== null || rwdProdList.length == 0){

            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("RWD PROD CD IS WRONG : [CD] " + rwdProdCd);
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0005", "올바른 자급제 보상서비스를 선택하여 주시기 바랍니다.");
        }

        rwdOrderDto.setRwdProdNm(rwdProdList[0].getRwdProdNm());

        // 7. 구입가 입력 여부 (0원 입력 가능)
        String buyPric= rwdOrderDto.getBuyPric();

        try{
            long numBuyPric= Long.parseLong(buyPric);
            if(numBuyPric < 0){
                // RWD 신청 실패 로그 기록
                rwdFailHistDto.setFailRsn("BUY PRICE HAS MINUS VALUE : [PRICE] " + buyPric);
                rwdService.insertFailRwd(rwdFailHistDto);
                throw new McpCommonJsonException("0006", "올바른 자급제 핸드폰 구입가를 입력하여 주시기 바랍니다.");
            }
        }catch (NumberFormatException e){
            rwdFailHistDto.setFailRsn("BUY PRICE IS NOT NUMBER : [PRICE] " + buyPric);
            rwdService.insertFailRwd(rwdFailHistDto);
            throw new McpCommonJsonException("0006", "올바른 자급제 핸드폰 구입가를 입력하여 주시기 바랍니다.");
        }

        // 8. imei, imeiTwo 입력 여부 (둘 중 하나는 필수)
        String imei= StringUtil.NVL(rwdOrderDto.getImei(), "");
        String imeiTwo= StringUtil.NVL(rwdOrderDto.getImeiTwo(), "");

        if("".equals(imei) && "".equals(imeiTwo)){

            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("IMEI IS NULL");
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0007", "단말기 정보값을 입력하여 주시기 바랍니다.");
        }

        if( (!"".equals(imei) && imei.length() != 15) || (!"".equals(imeiTwo) && imeiTwo.length() != 15) ){
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("IMEI VALUE IS WRONG : [IMEI] " + imei + " , [IMEITWO] " + imeiTwo);
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0007", "올바른 단말기 정보값을 입력하여 주시기 바랍니다.");
        }

        // imei 사용 가능 여부
        String possibleYn= rwdService.checkImeiForRwd(imei, imeiTwo);
        if(!"Y".equals(possibleYn)){
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("IMEI IS ALREADY USED : [IMEI] " + imei + " , [IMEITWO] " + imeiTwo);
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0007", "사용불가한 단말기 정보값입니다. 다른 단말기 정보값을 입력 바랍니다.");
        }

        // 9. 증빙서류 첨부 여부
        if(!Optional.ofNullable(rwdOrderDto.getFile()).isPresent()) {
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("RWD FILE IS NULL");
            rwdService.insertFailRwd(rwdFailHistDto);

            // 파일이 존재하지 않음
            throw new McpCommonJsonException("0008", "구매영수증을 첨부하여 주시기 바랍니다.");
        }

        // 10. 증빙서류 개수 체크
        MultipartFile[] arrFile = rwdOrderDto.getFile();
        if(arrFile == null || arrFile.length != 1){
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("RWD FILE COUNT IS MORE THAN ONE");
            rwdService.insertFailRwd(rwdFailHistDto);

            // 첨부파일 개수 불일치
            throw new McpCommonJsonException("0009", "1개의 증빙서류만 첨부하여 주시기 바랍니다.");
        }

        // 11. 증빙서류 용량과 확장자 체크
        try{
            boolean exeFlag = false;
            boolean sizeFlag = false;
            for(int i=0; i<arrFile.length; i++){
                MultipartFile file = arrFile[i];
                long maxSize = 2*1024*1024; // 2*1024*1024 ==> 2MB
                long size = file.getSize();
                String fileNm = file.getOriginalFilename();

                // 확장자체크
                String filenameExt = fileNm.substring(fileNm.lastIndexOf(".")+1);
                filenameExt = filenameExt.toLowerCase();

                String[] allowFile = {"jpg","jpeg","png","gif"};
                exeFlag= Arrays.asList(allowFile).contains(filenameExt);

                // 용량체크
                sizeFlag= (size > maxSize) ? false : true;

                if(!exeFlag || !sizeFlag){
                    break;
                }
            }

            if(!exeFlag || !sizeFlag){
                // RWD 신청 실패 로그 기록
                rwdFailHistDto.setFailRsn("RWD FILE SIZE/EXE IS NOT ACCEPTED");
                rwdService.insertFailRwd(rwdFailHistDto);

                // 파일 형식 또는 용량 불일치
                throw new McpCommonJsonException("0010", "증빙서류는 2MB 이내의 jpg, gif, png 형식으로만 등록 가능합니다.");
            }
        } catch(IndexOutOfBoundsException e) {
            throw new McpCommonJsonException("0010", "IndexOutOfBoundsException error 발생 ");
        } catch(Exception e){
            throw new McpCommonJsonException("0010", "증빙서류는 2MB 이내의 jpg, gif, png 형식으로만 등록 가능합니다.");
        }

        // 12. 필수약관 동의 여부
        if(!"Y".equals(rwdOrderDto.getClauseRwdRegFlag())
                || !"Y".equals(rwdOrderDto.getClauseRwdPaymentFlag())
                || !"Y".equals(rwdOrderDto.getClauseRwdRatingFlag())
                || !"Y".equals(rwdOrderDto.getClauseRwdPrivacyInfoFlag())
                || !"Y".equals(rwdOrderDto.getClauseRwdTrustFlag())
        ){
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("USER DOES NOT AGREE WITH RWD TERMS");
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0011", "필수약관에 동의하여 주시기 바랍니다.");
        }

        // 13. 휴대폰 인증 여부
        NiceResDto sessNiceRwdRes = SessionUtils.getNiceRwdResCookieBean();
        if(sessNiceRwdRes == null || !sessNiceRes.getConnInfo().equals(sessNiceRwdRes.getConnInfo())){
            // 휴대폰 인증하지 않거나, 본인인증에 사용된 정보와 휴대폰인증에 사용된 정보가 다른경우 이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("FORM_AUTH_ERROR");
            if (sessNiceRwdRes == null) {
                mcpIpStatisticDto.setPrcsSbst("sessNiceRwdRes is NULL");
            } else {
                mcpIpStatisticDto.setPrcsSbst("ConnInfo[" + sessNiceRwdRes.getConnInfo() + "] Name[" + sessNiceRwdRes.getName() + "] ");
            }

            mcpIpStatisticDto.setParameter("ConnInfo[" + sessNiceRes.getConnInfo() + "]");
            mcpIpStatisticDto.setTrtmRsltSmst("saveRwdformDbAjax");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("SESSION RWD RES INFO IS WRONG");
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0012", NICE_CERT_EXCEPTION_RWD);
        }

        rwdOrderDto.setSelfCstmrCi(sessNiceRes.getConnInfo());
        rwdOrderDto.setRwdAuthInfo("ReqNo:" + sessNiceRwdRes.getReqSeq() + ", ResNo:" + sessNiceRwdRes.getResSeq()); // 휴대폰 본인인증 정보 세팅

        // 14. 자급제 보상서비스 order insert
        rwdOrderDto.setChnCd("4");    // 14-1. 가입채널코드 세팅 (4: 셀프케어)
        rwdOrderDto.setIfTrgtCd("Y"); // 14-2. 연동상태 코드 (셀프케어인 경우 Y, 이외에는 N)

        // 14-2. 주문자 테이블에 insert와 파일 업로드 진행
        rtnMap= rwdService.saveRwdOrder(rwdOrderDto);
        return rtnMap;
    }

    /**
     * 2023.05.22 hsy
     * 자급제 보상 서비스 신청 ajax (개통)
     * @param request
     * @param rwdOrderDto
     * @return Map<String, Object>
     */

    @RequestMapping(value = {"/mypage/rwdRegNonMemberAjax.do", "/m/mypage/rwdRegNonMemberAjax.do"})
    @ResponseBody
    public Map<String, String> rwdRegNonMemberAjax(HttpServletRequest request, @ModelAttribute RwdOrderDto rwdOrderDto){

        HashMap<String, String> rtnMap;
        // 자급제 보상 서비스 가입 실패 로그 기록용
        RwdFailHistDto rwdFailHistDto= new RwdFailHistDto();

        // 1. 로그인 여부 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession != null && !StringUtils.isEmpty(userSession.getUserId())) {
            rwdOrderDto.setRegstId(userSession.getUserId());
            rwdFailHistDto.setUserId(userSession.getUserId());
        }

        // 2. 신청서 정보 확인
        AppformReqDto appformReqDto= new AppformReqDto();
        appformReqDto.setRequestKey(rwdOrderDto.getRequestKey());
        AppformReqDto appformReq = appformSvc.getAppForm(appformReqDto);

        rwdFailHistDto.setRequestKey(appformReq.getRequestKey());

        if(appformReq == null){
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("APPFORM INFO IS NULL");
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0001", "개통 신청 정보가 없습니다.");
        }else if(!"Y".equals(appformReq.getClauseRwdFlag())
                || "".equals(StringUtil.NVL(appformReq.getRwdAuthInfo(), ""))
                || "".equals(StringUtil.NVL(appformReq.getRwdProdCd(), ""))){

            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setResNo(appformReq.getResNo());
            rwdFailHistDto.setFailRsn("RWD SERVICE IS NOT APPLIED");
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0002", "자급제 보상 서비스를 신청하지 않으셨습니다.");
        }

        rwdOrderDto.setResNo(appformReq.getResNo());
        rwdOrderDto.setRwdProdCd(appformReq.getRwdProdCd());
        rwdOrderDto.setRwdAuthInfo(appformReq.getRwdAuthInfo());
        rwdFailHistDto.setResNo(appformReq.getResNo());

        // 3. 선택한 자급제 보상서비스 존재여부 체크
        RwdDto[] rwdProdList = rwdService.selectRwdProdList(rwdOrderDto.getRwdProdCd());
        if(rwdProdList== null || rwdProdList.length == 0){

            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("RWD PROD CD IS WRONG : [CD] " + rwdOrderDto.getRwdProdCd());
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0003", "선택한 자급제 보상 서비스는 존재하지 않습니다.");
        }
        rwdOrderDto.setRwdProdNm(rwdProdList[0].getRwdProdNm());

        // 4. 구입가 입력 여부 (0원 입력 가능)
        String buyPric= rwdOrderDto.getBuyPric();

        try{
            long numBuyPric= Long.parseLong(buyPric);
            if(numBuyPric < 0){
                // RWD 신청 실패 로그 기록
                rwdFailHistDto.setFailRsn("BUY PRICE HAS MINUS VALUE : [PRICE] " + buyPric);
                rwdService.insertFailRwd(rwdFailHistDto);
                throw new McpCommonJsonException("0004", "자급제 핸드폰 구입가가 마이너스 값입니다.");
            }
        }catch (NumberFormatException e){
            rwdFailHistDto.setFailRsn("BUY PRICE IS NOT NUMBER : [PRICE] " + buyPric);
            rwdService.insertFailRwd(rwdFailHistDto);
            throw new McpCommonJsonException("0006", "올바른 자급제 핸드폰 구입가를 입력하여 주시기 바랍니다.");
        }

        // 5. imei, imeiTwo 입력 여부 (둘 중 하나는 필수)
        String imei= StringUtil.NVL(rwdOrderDto.getImei(), "");
        String imeiTwo= StringUtil.NVL(rwdOrderDto.getImeiTwo(), "");

        if("".equals(imei) && "".equals(imeiTwo)){
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("IMEI IS NULL");
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0005", "단말기 정보값이 없습니다.");
        }

        if( (!"".equals(imei) && imei.length() != 15) || (!"".equals(imeiTwo) && imeiTwo.length() != 15) ){
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("IMEI VALUE IS WRONG : [IMEI] " + imei + " , [IMEITWO] " + imeiTwo);
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0006", "단말기 정보값이 올바르지 않습니다.");
        }

        // imei 사용 가능 여부
        String possibleYn= rwdService.checkImeiForRwd(imei, imeiTwo);
        if(!"Y".equals(possibleYn)){
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("IMEI IS ALREADY USED : [IMEI] " + imei + " , [IMEITWO] " + imeiTwo);
            rwdService.insertFailRwd(rwdFailHistDto);

            throw new McpCommonJsonException("0007", "사용불가한 단말기 정보값입니다.");
        }

        // 6. 증빙서류 첨부 여부
        if(!Optional.ofNullable(rwdOrderDto.getFile()).isPresent()) {
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("RWD FILE IS NULL");
            rwdService.insertFailRwd(rwdFailHistDto);

            // 파일이 존재하지 않음
            throw new McpCommonJsonException("0008", "구매영수증이 없습니다.");
        }

        // 7. 증빙서류 개수 체크
        MultipartFile[] arrFile = rwdOrderDto.getFile();
        if(arrFile == null || arrFile.length != 1){
            // RWD 신청 실패 로그 기록
            rwdFailHistDto.setFailRsn("RWD FILE COUNT IS MORE THAN ONE");
            rwdService.insertFailRwd(rwdFailHistDto);

            // 첨부파일 개수 불일치
            throw new McpCommonJsonException("0009", "2개 이상의 증빙서류는 첨부할 수 없습니다.");
        }

        // 8. 증빙서류 용량과 확장자 체크
        try{
            boolean exeFlag = false;
            boolean sizeFlag = false;
            for(int i=0; i<arrFile.length; i++){
                MultipartFile file = arrFile[i];
                long maxSize = 2*1024*1024; // 2*1024*1024 ==> 2MB
                long size = file.getSize();
                String fileNm = file.getOriginalFilename();

                // 확장자체크
                String filenameExt = fileNm.substring(fileNm.lastIndexOf(".")+1);
                filenameExt = filenameExt.toLowerCase();

                String[] allowFile = {"jpg","jpeg","png","gif"};
                exeFlag= Arrays.asList(allowFile).contains(filenameExt);

                // 용량체크
                sizeFlag= (size > maxSize) ? false : true;

                if(!exeFlag || !sizeFlag){
                    break;
                }
            }

            if(!exeFlag || !sizeFlag){ // 파일 형식 또는 용량 불일치
                // RWD 신청 실패 로그 기록
                rwdFailHistDto.setFailRsn("RWD FILE SIZE/EXE IS NOT ACCEPTED");
                rwdService.insertFailRwd(rwdFailHistDto);

                throw new McpCommonJsonException("0010", "증빙서류는 2MB 이내의 jpg, gif, png 형식으로만 등록 가능합니다.");
            }
        } catch(IndexOutOfBoundsException e) {
            throw new McpCommonJsonException("0009", "IndexOutOfBoundsException error");
        } catch(Exception e){
            throw new McpCommonJsonException("0009", "증빙서류는 2MB 이내의 jpg, gif, png 형식으로만 등록 가능합니다.");
        }

        // 9. 자급제 보상서비스 order insert
        rwdOrderDto.setChnCd("2");  // 가입채널코드 세팅 (2: 홈페이지 온라인)
        rwdOrderDto.setIfTrgtCd("N");  // 연동상태 코드 (셀프케어인 경우 Y, 이외에는 N)

        // 10. 주문자 테이블에 insert와 파일 업로드 진행
        rtnMap= rwdService.saveRwdOrder(rwdOrderDto);
        return rtnMap;
    }

    private ResponseSuccessDto getMessageBox() {
        ResponseSuccessDto mbox = new ResponseSuccessDto();

        String url = "/mypage/updateForm.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            url = "/m/mypage/updateForm.do";
        }
        mbox.setRedirectUrl(url);
        mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
        return mbox;
    }

}
