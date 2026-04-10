package com.ktmmobile.mcp.mplatform.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.cmmn.constants.Constants;
import com.ktmmobile.mcp.cmmn.util.StringUtil;
import com.ktmmobile.mcp.mplatform.dto.MPlatformErrVO;
import com.ktmmobile.mcp.mplatform.dto.MPlatformNstepVO;
import com.ktmmobile.mcp.msp.dto.CmnGrpCdMst;
import com.ktmmobile.mcp.msp.mapper.MspMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.mplatform.dto.MPlatformReqVO;
import com.ktmmobile.mcp.mplatform.dto.MPlatformResVO;
import com.ktmmobile.mcp.mplatform.mapper.MPlatformMapper;
import com.ktmmobile.mcp.phone.dto.CommonSearchDto;

@RestController
public class MPlatformController {

    private static final Logger logger = LoggerFactory.getLogger(MPlatformController.class);

    @Autowired
    MPlatformMapper mPlatformMapper;

    @Autowired
    MspMapper mspMapper;

    
    /**
     * 커넥션 타임 기본 정보 조회
     * @param CommonSearchDto
     * @return
     */
    @RequestMapping(value = "/mPlatform/getNstepConnectTimeout", method = RequestMethod.POST)
    public int getNstepConnectTimeout(@RequestBody CommonSearchDto commonSearchDto) {

        int result = 0;

        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getNstepConnectTimeout();

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    
    /**
     * 신청서 등록 정보 조회
     * @param MPlatformReqVO
     * @return
     */
    @RequestMapping(value = "/mPlatform/getRequestInfo", method = RequestMethod.POST)
    public MPlatformResVO getRequestInfo(@RequestBody MPlatformReqVO vo) {

        MPlatformResVO result = null;

        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getRequestInfo(vo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 유심 셀프 변경 정보
     * @param MPlatformReqVO
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageUC0", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessageUC0(@RequestBody MPlatformReqVO vo) {

        MPlatformResVO result = null;

        logger.error("+++++++++++++++++++++getXmlMessageUC0 START++++++++++++++++");
        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessageUC0(vo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        logger.error("+++++++++++++++++++++getXmlMessageUC0 END++++++++++++++++");

        return result;
    }
    
    /**
     * m플랫폼 PC0 사전체크 호출 INPUT 정보 조회
     * @param String
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessagePC0", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessagePC0(@RequestBody String resNo) {

        MPlatformResVO result = null;

        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessagePC0(resNo);

        } catch(Exception e) {
            //throw new McpCommonJsonException(COMMON_EXCEPTION);
        	return null;
        }

        return result;
    }

    
    /**
     * m플랫폼 NU1 번호조회 호출 INPUT 정보 조회
     * @param MPlatformResVO
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageNU1", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessageNU1(@RequestBody MPlatformResVO vo) {

        MPlatformResVO result = null;

        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessageNU1(vo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    
    /**
     * m플랫폼 NU2 번호예약 호출 INPUT 정보 조회
     * @param MPlatformResVO
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageNU2", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessageNU2(@RequestBody MPlatformResVO vo) {

        MPlatformResVO result = null;

        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessageNU2(vo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    
    /**
     * m플랫폼 OP0 개통처리 호출 INPUT 정보 조회
     * @param String
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageOP0", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessageOP0(@RequestBody String resNo) {

        MPlatformResVO result = null;
        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessageOP0(resNo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    
    /**
     * m플랫폼 OSST 상태조회
     * @param String
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageST1", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessageST1(@RequestBody String resNo) {

        MPlatformResVO result = null;

        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessageST1(resNo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    
    /**
     * m플랫폼 메시지 조회
     * @param String
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageEP0", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessageEP0(@RequestBody String resNo) {

        MPlatformResVO result = null;

        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessageEP0(resNo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    
    /**
     * m플랫폼 메시지 조회
     * @param String
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageMP0", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessageMP0(@RequestBody String resNo) {

        MPlatformResVO result = null;

        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessageMP0(resNo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    
    /**
     * m플랫폼 NP1 번호이동사전승인 조회
     * @param MPlatformReqVO
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageNP1", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessageNP1(@RequestBody MPlatformReqVO vo) {

        MPlatformResVO result = null;

        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessageNP1(vo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }




    
    /**
     * m플랫폼 NP2 번호이동납부주장 조회
     * @param String
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageNP2", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessageNP2(@RequestBody String resNo) {

        MPlatformResVO result = null;

        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessageNP2(resNo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * m플랫폼 사전동의 결과조회(NP3)
     * @param MPlatformReqVO
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageNP3", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessageNP3(@RequestBody MPlatformReqVO vo) {

        MPlatformResVO result = null;

        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessageNP3(vo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    
    /**
     * m플랫폼 OP0 요금제조회
     * @param String
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageOP0Prod", method = RequestMethod.POST)
    public List<MPlatformResVO> getXmlMessageOP0Prod(@RequestBody String resNo) {

        List<MPlatformResVO> result = null;

        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessageOP0Prod(resNo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * m플랫폼 FS5 온라인 서식지 등록 호출 INPUT 정보 조회
     * @param String
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageFS5", method = RequestMethod.POST)
    public MPlatformNstepVO getXmlMessageFS5(@RequestBody String resNo) {

        MPlatformNstepVO result = null;
        try {
            // Database 에서 조회함.
            result = mPlatformMapper.getXmlMessageFS5(resNo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }
    
    /**
     * m플랫폼 OP0 연동결과 등록
     * @param MPlatformReqVO
     * @return
     */
    @RequestMapping(value = "/mPlatform/insertRequestOsst", method = RequestMethod.POST)
    public void insertRequestOsst(@RequestBody MPlatformReqVO vo) {

        try {

            if(Constants.EVENT_CODE_PRE_SCH.equals(vo.getAppEventCd())){

                CmnGrpCdMst cmnGrpCdMst= new CmnGrpCdMst();
                cmnGrpCdMst.setGrpId("RCP2040");
                cmnGrpCdMst.setCdVal(vo.getPrgrStatCd());

                cmnGrpCdMst= mspMapper.findCmnGrpCdMst(cmnGrpCdMst);

                StringBuilder sb= new StringBuilder();
                if(cmnGrpCdMst != null){
                    sb.append(cmnGrpCdMst.getCdDsc());
                    sb.append("-");
                }
                sb.append(StringUtil.NVL(vo.getRsltMsg(), ""));

                vo.setRsltMsg(sb.toString());
            }

            // Database 에서 조회함.
            mPlatformMapper.insertRequestOsst(vo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

    }

    
    /**
     * 부가서비스가입진행 현행화
     * @param MPlatformReqVO
     * @return
     */
    @RequestMapping(value = "/mPlatform/updateRequestAddition", method = RequestMethod.POST)
    public void updateRequestAddition(@RequestBody MPlatformResVO vo) {

        try {
            // Database 에서 조회함.
            mPlatformMapper.updateRequestAddition(vo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
    }

    
    /**
     * 셀프케어로그생성
     * @param Map<String, String>
     * @return
     */
    @RequestMapping(value = "/mPlatform/insertSelfCareLog", method = RequestMethod.POST)
    public void insertSelfCareLog(@RequestBody Map<String, String> param) {

        try {
            // Database 에서 조회함.
            mPlatformMapper.insertSelfCareLog(param);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
    }

    /**
     * MplatForm 호출 로그 수
     * @param param
     * @return
     */
    @RequestMapping(value = "/mPlatform/checkMpCallCount", method = RequestMethod.POST)
    public int checkMpCallCount(@RequestBody Map<String, String> param) {
        int cnt = 0;
        try {
            cnt = this.mPlatformMapper.selectCheckMpCallCount(param);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return cnt;
    }

    /**
     * MplatForm 암복호화 여부 확인
     * @param param
     * @return
     */
    @RequestMapping(value = "/mPlatform/getMplatformCryptionYn", method = RequestMethod.POST)
    public String getMplatformCryptYn(@RequestBody String eventCd) {
        String result = "N";
        try {
        	result = this.mPlatformMapper.getMplatformCryptionYn(eventCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * OSST exception 이력 저장
     * @param mPlatformErrVO
     */
    @RequestMapping(value = "/mPlatform/insertOsstErrLog", method = RequestMethod.POST)
    public void insertOsstErrLog(@RequestBody MPlatformErrVO mPlatformErrVO) {

        try {
            mPlatformMapper.insertOsstErrLog(mPlatformErrVO);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
    }

    /**
     * m플랫폼 MC0 명의변경 사전체크 호출 INPUT 정보 조회
     * @param String
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageMC0", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessageMC0(@RequestBody MPlatformReqVO vo) {

        MPlatformResVO result = null;

        try {
            if("NM".equals(vo.getCstmrType())) { //양수자가 미성년자
                result = mPlatformMapper.getXmlMessageMC0Nm(vo);
            }else { //양수자가 미성년자가 아닐 때
                result = mPlatformMapper.getXmlMessageMC0(vo);
            }
        } catch(Exception e) {
            return null;
        }

        return result;
    }

    /**
     * m플랫폼 MP0 명의변경처리 호출 INPUT 정보 조회
     * @param String
     * @return
     */
    @RequestMapping(value = "/mPlatform/getXmlMessageMcnMP0", method = RequestMethod.POST)
    public MPlatformResVO getXmlMessageMcnMP0(@RequestBody MPlatformReqVO vo) {

        MPlatformResVO result = null;
        try {
            result = mPlatformMapper.getXmlMessageMcnMP0(vo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

}
