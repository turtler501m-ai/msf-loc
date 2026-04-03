package com.ktmmobile.msf.form.servicechange.service;

import com.ktmmobile.msf.system.common.dto.AuthSmsDto;
import com.ktmmobile.msf.system.common.dto.MoscCombChkReqDto;
import com.ktmmobile.msf.system.common.dto.MoscCombReqDto;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.dto.*;
import com.ktmmobile.msf.form.servicechange.dto.McpReqCombineDto;
import com.ktmmobile.msf.form.servicechange.dto.MyCombinationReqDto;
import com.ktmmobile.msf.form.servicechange.dto.MyCombinationResDto;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;

public interface MsfMyCombinationSvc {

    /**
     * x87
     * MVNO 결합서비스 계약 조회
     * @author bsj
     * @Date : 2021.12.30
     * @param combinationReqDto
     * @return
     */

    public  MoscCombDtlResDTO selectCombiSvcList(MyCombinationReqDto combinationReqDto);

    /**
     * x77 MVNO 결합 상태조회
     * @author bsj
     * @Date : 2021.12.30
     * @param moscCombReqDto
     * @return
     */

    public MoscCombInfoResDTO selectCombiMgmtList(MoscCombReqDto moscCombReqDto);

    /**
     * x77 MVNO 결합 상태조회
     * @author bsj
     * @Date : 2021.12.30
     * @param moscCombReqDto
     * @param prcsMdlInd  : 로그저장 그룹 번호
     * @return
     */

    public MoscCombInfoResDTO selectCombiMgmtListLog(MoscCombReqDto moscCombReqDto,String prcsMdlInd , String contractNum);

    /**
     * x79 결합 저장
     * @author bsj
     * @Date : 2021.12.30
     * @param moscCombChkReqDto
     * @return
     */

    public MoscCombChkResDto  insertCombSaveInfo(MoscCombChkReqDto moscCombChkReqDto);



    /**
     * x79 결합 저장
     * @author bsj
     * @Date : 2021.12.30
     * @param moscCombChkReqDto
     * @return
     */

    public MoscCombChkResDto  moscCombSaveInfo(MoscCombChkReqDto moscCombChkReqDto);


    /**
     * x79 결합 저장
     * @author bsj
     * @Date : 2021.12.30
     * @param moscCombChkReqDto
     * @param prcsMdlInd 로그 그룹핑 코드
     * @return
     */

    public MoscCombChkRes  moscCombSaveInfoLog(MoscCombChkReqDto moscCombChkReqDto ,String prcsMdlInd , String contractNum) throws SocketTimeoutException;

    /**
     * x78
     * 결합사전체크
     * @author bsj
     * @Date : 2021.12.30
     * @param moscCombChkReqDto
     * @return
     */

    public MoscCombChkResDto insertCombChkInfo(MoscCombChkReqDto moscCombChkReqDto);


    /**
     * x78
     * 결합사전체크
     * @author bsj
     * @Date : 2021.12.30
     * @param moscCombChkReqDto
     * @param prcsMdlInd 로그 그룹핑 코드
     * @return
     */

    public MoscCombChkRes insertCombChkInfoLog(MoscCombChkReqDto moscCombChkReqDto,String prcsMdlInd , String contractNum) throws SocketTimeoutException;

    /**
     * 결합 가능 여부 구분자 조회
     * @author bsj
     * @Date : 2021.12.30
     * @param contractNum
     * @param userId
     * @return
     */

    public MyCombinationResDto selectMspCombRateMapp(String pRateCd);


    public boolean insertMcpReqCombine(McpReqCombineDto mcpReqCombineDto);


    public List<McpReqCombineDto> selectMcpReqCombine(McpReqCombineDto mcpReqCombineDto);

    /**
     * 서브회선 마스터 결합 신청(Y44)
     * @author papier
     * @Date : 2024.11.21
     * @param mstSvcContId 법인회선 모회선 서비스계약번호
     */
    public MoscSubMstCombChgRes moscSubMstCombChg(String ncn, String ctn, String custId, String mstSvcContId) throws SelfServiceException, SocketTimeoutException ;

    public Map<String, Object> selectCombMgmtList(AuthSmsDto prntDto, AuthSmsDto childDto, String prcsMdlInd);

    public Map<String, Object> getCombChkInfo(AuthSmsDto prntDto, AuthSmsDto childDto, String prcsMdlInd, String childCobun);

    public Map<String, Object> regCombineKt(String prcsMdlInd);
}
