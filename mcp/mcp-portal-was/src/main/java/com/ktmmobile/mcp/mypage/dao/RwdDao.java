package com.ktmmobile.mcp.mypage.dao;

import com.ktmmobile.mcp.mypage.dto.RwdFailHistDto;
import com.ktmmobile.mcp.mypage.dto.RwdOrderDto;

import java.util.List;

public interface RwdDao {

    /**
     * @Description: 자급제 보상서비스 가입 실패 로그 기록
     * @param rwdFailHistDto
     */
    void insertFailRwd(RwdFailHistDto rwdFailHistDto);

    /**
     * @Description: 자급제 보상서비스 시 서식지 생성 후 EMV_SCAN_MST의 WORK_ID, DOC_TITLE update
     *               (서식지 자동생성은 WORK_ID가 무조건 S0001로 들어가기 때문에 S0012로 바꿔줘야함)
     * @param scanId
     * @return boolean
     */
    public boolean updateEmvScanMstRwd(String scanId);

    /**
     * @Description: 자급제 보상서비스 시 서식지 생성 후 EMV_SCAN_FILE의 DOC_ID update
     *               (서식지 자동생성은 DOC_ID가 무조건 E0001로 들어가기 때문에 E0022로 바꿔줘야함)
     * @param scanId
     * @return boolean
     */
    public boolean updateEmvScanFileRwd(String scanId);

    /**
     * @Description: 서식지합본처리-maxFileNum 가져오기
     * @param orgScanId
     * @return int
     */
    public int getMaxFileNum(String orgScanId);

    /**
     * @Description: 자급제 보상 서비스 스캔 파일 리스트 추출
     * @param scanId
     * @return boolean
     */
    public List<RwdOrderDto> getEmvScanFileList(String scanId);

    /**
     * @Description: 서식지합본처리-EMV_SACN_FILE에 insert
     * @param rwdOrderDto
     * @return boolean
     */
    public boolean insertEmvScanFile(RwdOrderDto rwdOrderDto);


}
