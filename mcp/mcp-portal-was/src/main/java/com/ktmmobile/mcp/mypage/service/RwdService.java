package com.ktmmobile.mcp.mypage.service;

import com.ktmmobile.mcp.mypage.dto.RwdDto;
import com.ktmmobile.mcp.mypage.dto.RwdFailHistDto;
import com.ktmmobile.mcp.mypage.dto.RwdOrderDto;

import java.util.HashMap;

public interface RwdService {

    /**
     * 자급제 보상서비스 가입 상태 조회
     * @author hsy
     * @Date : 2023.03.02
     * @param ncn
     * @return String
     */
    String selectRwdInfoByCntrNum(String ncn);

    /**
     * 자급제 보상 서비스 리스트 가져오기
     * @author hsy
     * @Date : 2023.03.02
     * @param rwdProdCd
     * @return RwdDTO[]
     */
    RwdDto[] selectRwdProdList(String rwdProdCd);

    /**
     * 2023.03.10 hsy
     * 자급제 보상서비스 가입 시 해당 imei 사용가능 여부 체크
     * @param imei
     * @param imeiTwo
     * @return String
     */
    String checkImeiForRwd(String imei, String imeiTwo);

    /**
     * 계약번호로 신청정보 조회
     * @author hsy
     * @Date : 2023.02.28
     * @param contractNum
     * @return RwdOrderDto[]
     */

    RwdOrderDto[]  selectMcpRequestInfoByCntrNum(String contractNum);

    /**
     * 자급제 보상서비스 등록 (테이블 insert와 파일업로드)
     * @author hsy
     * @Date : 2023.02.28
     * @param rwdOrderDto
     * @return HashMap<String, String>
     */
    HashMap<String, String> saveRwdOrder(RwdOrderDto rwdOrderDto);

    /**
     * 2023.03.10 hsy
     * 자급제 보상서비스  물리 파일명 추출
     * @return String
     */
    String selectRwdNewFileNm();

    /**
     * 2023.05.18 hsy
     * 자급제 보상서비스 서식지 생성
     * @param rwdSeq
     * @param regstId
     */
    void prodSendScan(String rwdSeq, String regstId);

    /**
     * 2023.05.23 hsy
     * 자급제 보상서비스 가입 실패 로그 기록
     * @param RwdFailHistDto
     */
    void insertFailRwd(RwdFailHistDto RwdFailHistDto);
}
