package com.ktmmobile.mcp.point.service;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.mplatform.vo.UserVO;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.point.dao.MstoreDao;
import com.ktmmobile.mcp.point.dto.MstoreCanTrgDto;
import com.ktmmobile.mcp.point.dto.MstoreContentItemDto;
import com.ktmmobile.mcp.point.dto.MstoreDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.NO_FRONT_SESSION_EXCEPTION;


@Service
public class MstoreServiceImpl implements MstoreService {

    private static Logger logger = LoggerFactory.getLogger(MstoreServiceImpl.class);

    @Autowired
    private MstoreDao mstoreDao;


    /**
     * Mstore 이용 가능 여부 체크
     * @return Map<String, String>
     */

    public Map<String, String> chkMstoreUseLimit(){

        /*
            ======== errorCause: LOGIN ========
            1) resultCd[0001] : 비로그인

            ======== errorCause: GRADE ========
            1) resultCd[0001] : 이용불가 회원등급

            ======== errorCause: PARAM ========
            1) resultCd[0001] : 정회원 정보 보정 필요
            2) resultCd[0002] : 고객유형 미충족
            3) resultCd[0003] : 본인인증 필요
            4) resultCd[0004] : 나이 조건 미충족
            5) resultCd[0000] : 이용가능
        */

        Map<String, String> rtnMap= new HashMap<>();

        // 1. 로그인 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())){
            rtnMap.put("resultCd", "0001");
            rtnMap.put("errorCause", "LOGIN");
            return rtnMap;
        }

        // 2. Mstore 이용 가능여부 체크 - 회원등급
        String memberGrade = Constants.MSTORE_JOIN_LIMIT_CD.get("ASSOCIATE");
        if("01".equals(userSession.getUserDivision())){ // 정회원
            memberGrade = Constants.MSTORE_JOIN_LIMIT_CD.get("REGULAR");
        }

        Map<String,String> memberGradeChk = this.checkMemberGrade(memberGrade);

        if(!"0000".equals(memberGradeChk.get("resultCd"))){
            rtnMap.put("resultCd", memberGradeChk.get("resultCd"));
            rtnMap.put("errorCause", "GRADE");
            return rtnMap;
        }

        // 3. Mstore 이용 가능여부 체크 - 사용자 정보 체크 (나이, 고객유형, CI)
        Map<String,String> userInfoChk =  this.checkUserInfoForMstore(userSession);
        rtnMap.put("resultCd", userInfoChk.get("resultCd"));
        rtnMap.put("errorCause", "PARAM");
        rtnMap.put("userId", userSession.getUserId());
        rtnMap.put("mbrGrdGrpCd", memberGrade);
        rtnMap.put("mbrGrdCd", memberGradeChk.get("mbrGrdCd"));  // 현행화된 회원등급코드 (M스토어 연동값)

        if(!StringUtil.isEmpty(userInfoChk.get("mbrGrdCd"))) rtnMap.put("mbrGrdCd", userInfoChk.get("mbrGrdCd"));  // 기존 회원등급
        if(!StringUtil.isEmpty(userInfoChk.get("lstComActvDate"))) rtnMap.put("lstComActvDate",userInfoChk.get("lstComActvDate")); // 개통일자 (M스토어 연동값) : 준회원은 null

        // 4. M스토어 이용 가능 대상- 필수데이터 세팅
        if("0000".equals(userInfoChk.get("resultCd"))){
            rtnMap.put("memId",userInfoChk.get("memId"));                   // 회원CI (M스토어 연동값)
        }

        return rtnMap;
    }



    /**
     * Mstore 이용가능 여부 체크 - 회원등급
     * @param memberGrade
     * @return Map<String, String>
     */
    private Map<String, String> checkMemberGrade(String memberGrade) {

        /*
            1) resultCd[0001] : mstore 이용불가 회원등급
            2) resultCd[0002] : mstore 이용가능 회원등급
        */

        Map<String, String> resultCdMap = new HashMap<String, String>();
        NmcpCdDtlDto resCodeVo = NmcpServiceUtils.getCodeNmDto("MSTORELIMIT", memberGrade);

        // 이용 불가 회원등급
        if(resCodeVo== null || !"Y".equals(resCodeVo.getExpnsnStrVal1())){
            resultCdMap.put("resultCd","0001");
        }else{
            resultCdMap.put("resultCd","0000");
            resultCdMap.put("mbrGrdCd", resCodeVo.getExpnsnStrVal2());
        }

        return resultCdMap;
    }


    /**
     * Mstore 이용가능 여부 체크 - 사용자 정보 체크 (나이, 고객유형, CI)
     * @param userSession
     * @return Map<String, String>
     */

    private Map<String, String> checkUserInfoForMstore(UserSessionDto userSession) {

        /*
            1) resultCd[0001] : 정회원 정보 보정 필요
            2) resultCd[0002] : 고객유형 미충족
            3) resultCd[0003] : 본인인증 필요
            4) resultCd[0004] : 나이 조건 미충족
            5) resultCd[0000] : 이용가능
        */

        Map<String, String> resultMap = new HashMap<String, String>();

        // 1. SSO 연동 이력 확인 (2023.12.05 신규 생성 테이블: NMCP_MSTORE_SSO_INFO)
        MstoreDto mstoreDto= mstoreDao.getMstoreSSOInfo(userSession.getUserId());
        if(mstoreDto != null){

            resultMap.put("resultCd", "0000"); // 이용가능
            resultMap.put("lstComActvDate", mstoreDto.getLstComActvDate());
            resultMap.put("memId", mstoreDto.getEmpen());
            resultMap.put("mbrGrdCd", mstoreDto.getMbrGrdCd());

            if(StringUtil.isEmpty(mstoreDto.getEmpen())){
                resultMap.put("resultCd", "0003"); // 본인인증 필요
            }

            return resultMap;
        }

        // 2. 회원정보로 나이조건 확인 (MCP_USER)
        NmcpCdDtlDto resCodeVo= NmcpServiceUtils.getCodeNmDto("MSTOREAGE", "AGE");
        int limitAge= (resCodeVo == null) ? 14 : Integer.parseInt(resCodeVo.getExpnsnStrVal1());

        int userAge= 0;
        if(userSession.getBirthday() != null){
            userAge= NmcpServiceUtils.getBirthDateToAge(userSession.getBirthday(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
        }

        if(limitAge > userAge){
            resultMap.put("resultCd", "0004"); // 나이 조건 미충족
            return resultMap;
        }

        // 3. 정회원인 경우, 고객유형+CI값 존재여부 확인
        if("01".equals(userSession.getUserDivision())){
            List<MstoreDto> cusInfoList= mstoreDao.getMspCusInfo(userSession.getUserId(), null);

            if(cusInfoList == null || cusInfoList.isEmpty()){
                resultMap.put("resultCd", "0001"); // 정회원 정보 보정 필요
                return resultMap;
            }
            mstoreDto= cusInfoList.get(0); // 개통일자가 가장 빠른 개통건

            // 3-1. 고객유형 확인 (개인회선만 이용 가능)
            if(!"I".equals(mstoreDto.getCustomerType())) {
                resultMap.put("resultCd", "0002"); // 고객유형 미충족
                return resultMap;
            }

            // 3-2. 개통일자 set
            resultMap.put("lstComActvDate", mstoreDto.getLstComActvDate());

            // 3-3. 개통정보 내 CI값 확인
            if(!StringUtils.isEmpty(mstoreDto.getEmpen())){

                resultMap.put("resultCd", "0000"); // 이용가능
                resultMap.put("memId", mstoreDto.getEmpen());
                return resultMap;
            }

            // 3-4. 개통정보 내 CI값이 없는경우, 신청정보 내 CI값 확인
            MstoreDto mcpRequestInfo = mstoreDao.getMcpRequestInfo(mstoreDto.getCustomerId());

            // 3-4-1. 신청정보 내 CI값 존재시, 해당 CI값 사용
            if(mcpRequestInfo != null && !StringUtils.isEmpty(mcpRequestInfo.getEmpen())){

                resultMap.put("resultCd", "0000"); // 이용가능
                resultMap.put("memId", mcpRequestInfo.getEmpen());
                return resultMap;
            }
        } // end of 3----------------------------------

        // 4. 본인인증 폼 표출
        resultMap.put("resultCd", "0003"); // 본인인증 필요
        return resultMap;
    }

    /**
     * Mstore 이용을 위한 본인인증 정보 확인
     * @param niceResDto
     * @return boolean
     */
    @Override
    public boolean identityChk(NiceResDto niceResDto){

        // 1. userSession 조회
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession== null || StringUtils.isEmpty(userSession.getUserId())){
            throw new McpCommonJsonException("0001" ,NO_FRONT_SESSION_EXCEPTION);
        }

        // 2. 본인인증 정보 비교 (MCP_USER 회원정보와 비교)
        // [참고] NiceResDto의 getName 사용 시 이름을 대문자로 변경해주고 있음
        String niceSessNm= niceResDto.getName().replaceAll(" ", "");
        String compareNm= StringUtil.NVL(userSession.getName(), "").toUpperCase().replaceAll(" ", "");
        String compareBirth= StringUtil.NVL(userSession.getBirthday(), "");
        compareBirth= (compareBirth.length() > 6) ? compareBirth.substring(2) : compareBirth;  // yymmdd

        if(niceSessNm.equals(compareNm) && niceResDto.getBirthDate().indexOf(compareBirth) >= 0){
            return true;
        }

        return false;
    }

    /**
     * mstore 이용약관 동의 여부 확인
     * @param userId
     * @return boolean
     */
    @Override
    public boolean getMstoreTermsAgreeYn(String userId) {

        boolean agreeFlag= false;

        // M스토어 약관 동의 여부 조회
        Map<String, String> agreeTermsHist= mstoreDao.getMstoreTermsAgreeInfo(userId);

        if (agreeTermsHist == null || !"Y".equals(StringUtil.NVL(agreeTermsHist.get("AGR_YN"), "N"))) agreeFlag= false;
        else agreeFlag= true;

        return agreeFlag;
    }


    /**
     * mstore 이용약관 이력 기록
     * @param agreeYn
     * @return void
     */
    @Override
    public void chgMstoreTermsAgreeHist(String agreeYn) {

        // 1. userSession 조회
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession== null || StringUtils.isEmpty(userSession.getUserId())){
            throw new McpCommonJsonException("0001" ,NO_FRONT_SESSION_EXCEPTION);
        }

        Map<String, String> agreeTermsHist= mstoreDao.getMstoreTermsAgreeInfo(userSession.getUserId());

        // 2. 기존 유효한 이력 존재 시, 기존 이력 만료 처리
        if(agreeTermsHist != null && !agreeTermsHist.isEmpty()){
            int updateCnt= mstoreDao.updateMstoreTermsAgreeInfo(userSession.getUserId());
            if(updateCnt <= 0) return;
        }

        // 3. 신규이력 생성
        MstoreDto mstoreDto= new MstoreDto();
        mstoreDto.setUserId(userSession.getUserId());
        mstoreDto.setTermsAgreeYn(agreeYn);
        mstoreDao.insertMstoreTermsAgreeInfo(mstoreDto);

        /*
            20231205 M스토어 이용가능 대상이 준회원으로 확장됨에 따라,
            이력 테이블 NMCP_MSTORE_TERM_HIST.CUSTOMER_ID 컬럼은 사용하지 않음
            (기존 이력 동의 고객 처리를 위해 해당 컬럼은 유지)
        */
    }


    /**
     * mstore 탈퇴연동 대상 insert
     * @param userVO
     * @return void
     */
    @Override
    public void insertMstoreCanTrg(UserVO userVO) {

        // 1. 필수 파라미터 체크
        if(StringUtil.isEmpty(userVO.getUserid())) return;

        // 2. m스토어 탈퇴연동 대상 체크 (동의이력 확인)
        Map<String, String> agreeTermsHist= mstoreDao.getMstoreTermsAgreeInfo(userVO.getUserid());
        if(agreeTermsHist == null || agreeTermsHist.isEmpty()) return;

        // 3. Mstore 개인정보 동의 이력 만료처리
        mstoreDao.updateMstoreTermsAgreeInfo(userVO.getUserid());

        // 3-1. Y가 아니면 리턴
        if(!"Y".equals(agreeTermsHist.get("AGR_YN"))) return;

        // 4. 필요 시 NMCP_MSTORE_SSO_INFO INSERT
        // M스토어 이용 가능 대상이 정회원에서 준회원(M모바일 이용고객이 아니어도 포함)까지 확대됨에 따라,
        // 기존 M스토어를 이용하던 정회원은 NMCP_MSTORE_SSO_INFO 테이블에 정보가 없음.
        // 탈퇴연동을 위해 NMCP_MSTORE_SSO_INFO 테이블에 정보 임시로 INSERT (MBR_GRD_CD, LST_COM_ACTV_DATE는 INSERT하지 않음)

        if("01".equals(userVO.getUserDivision()) && !StringUtil.isEmpty(userVO.getContractNum())){

            // 4-1. NMCP_MSTORE_SSO_INFO 테이블 존재 여부 확인
            MstoreDto mstoreDto= mstoreDao.getMstoreSSOInfo(userVO.getUserid());

            // 4-2. NMCP_MSTORE_SSO_INFO에 존재하지 않는 경우(기존 M스토어 사용자)
            if(mstoreDto == null){

                // 기존사용자는 NMCP_MSTORE_TERM_HIST 테이블에 CUSTOMER_ID 존재
                String empen= mstoreDao.getMstoreTmpEmpen(agreeTermsHist.get("CUSTOMER_ID"));
                Map<String, String> paraMap= new HashMap<>();
                paraMap.put("memId", empen);
                paraMap.put("userId", userVO.getUserid());
                mstoreDao.mergeMstoreSSOInfo(paraMap);
            }
        }

        // 5. M스토어 탈퇴연동 대상 insert
        MstoreCanTrgDto mstoreCanTrgDto = new MstoreCanTrgDto();
        mstoreCanTrgDto.setUserId(userVO.getUserid());
        mstoreCanTrgDto.setChnCd("01"); // 포탈 탈퇴
        mstoreCanTrgDto.setProcYn("N");
        mstoreDao.insertMstoreCanTrg(mstoreCanTrgDto);

        /*
          20231205 M스토어 이용가능 대상이 준회원으로 확장됨에 따라,
          탈퇴연동 대상 테이블 NMCP_MSTORE_CAN_TRG.CONTRACT_NUM,CUSTOMER_ID 컬럼은 사용하지 않음. (기존 사용 고객 처리를 위해 해당 컬럼은 유지)
          NMCP_MSTORE_CAN_TRG.USERID 신규 컬럼 추가
    `   */
    }

    /**
     * Mstore SSO 연동 이력 저장/수정
     * @param ssoInfoMap
     * @return void
     */
    @Override
    public void mergeMstoreSSOInfo(Map<String, String> ssoInfoMap) {

        // 로그인 세션에 저장된 회원등급 (00 : ASSOCIATE, 01: REGULAR)
        String mbrGrdGrpCd= ssoInfoMap.get("mbrGrdGrpCd");
        // mstore SSO연동에 사용되고 있는 회원등급 코드 (준회원: 110, 정회원: 100)
        String asMbrGrdCd= ssoInfoMap.get("mbrGrdCd");

        NmcpCdDtlDto resCodeVo = NmcpServiceUtils.getCodeNmDto("MSTORELIMIT", mbrGrdGrpCd);
        String toMbrGrdCd= resCodeVo.getExpnsnStrVal2();

        // 1. 회원 등급이 변경된 경우, 변경된 값으로 연동
        if(!asMbrGrdCd.equals(toMbrGrdCd)){

            if(Constants.MSTORE_JOIN_LIMIT_CD.get("ASSOCIATE").equals(mbrGrdGrpCd)){
                // 1-1. 정회원에서 준회원으로 변경된 경우
                ssoInfoMap.put("mbrGrdCd", toMbrGrdCd);
                ssoInfoMap.put("lstComActvDate", null);
            }else{
                // 1-2. 준회원에서 정회원으로 변경된 경우
                List<MstoreDto> cusInfoList= mstoreDao.getMspCusInfo(ssoInfoMap.get("userId"), null);
                if(cusInfoList != null && !cusInfoList.isEmpty()){
                    ssoInfoMap.put("mbrGrdCd", toMbrGrdCd);
                    ssoInfoMap.put("lstComActvDate", cusInfoList.get(0).getLstComActvDate());
                }
            }
        }

        mstoreDao.mergeMstoreSSOInfo(ssoInfoMap);

        /*
            [예시]
            1. 정회원 당시 m스토어 sso 연동을 했지만, 현재 준회원으로 상태가 변경된 경우
               (로그인 세션의 등급코드가 00이므로 mbrGrdGrpCd는 ASSOCIATE)
               - asMbrGrdCd: 100
               - toMbrGrdCd: 110
            2. 준회원 당시 m스토어 sso 연동을 했지만, 현재 정회원으로 상태가 변경된 경우
               (로그인 세션의 등급코드가 01이므로 mbrGrdGrpCd는 REGULAR)
               - asMbrGrdCd: 110
               - toMbrGrdCd: 100
           3. 이외의 경우 asMbrGrdCd값과 toMbrGrdCd값은 동일
        */
    }

    @Override
    public List<MstoreContentItemDto> getMstoreContents() {
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return mstoreDao.getMstoreContentItemListMobile();
        } else {
            return mstoreDao.getMstoreContentItemListPc();
        }
    }
}