package com.ktis.msp.rcp.rcpMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryChangeVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("UsimDlvryChangeMapper")
public interface UsimDlvryChangeMapper {

    /** 신청정보(유심교체) 조회 */
    List<EgovMap> selectUsimChangeMstList(UsimDlvryChangeVO searchVO);

    /** 신청정보(유심교체) 엑셀다운로드 */
    List<EgovMap> selectUsimChangeMstListExcel(UsimDlvryChangeVO searchVO);
    
    /** 신청정보(유심교체) 상세조회 */
    List<EgovMap> selectUsimChangeDtlList(String seq);
    
    /** 신청정보(유심교체) 상세엑셀다운로드 */
    List<EgovMap> selectUsimChangeDtlListExcel(UsimDlvryChangeVO searchVO);

    /** 일련번호/송장업로드 엑셀업로드양식 다운로드 */
    List<EgovMap> selectUsimChgDlvryExcelTemp(UsimDlvryChangeVO searchVO);

  
    
    /** 존재하는 주문건인지 조회 */
    UsimDlvryChangeVO selectUsimChangeMstBySeq(String seq);

    /** 택배사 이름으로 택배사 코드 조회 */
    String getDlvryTbCd(String tbNm);
    
    /** 신청경로 명으로 신청경로 코드 조회 */
    String getChnlCd(String chnlNm);
    /** 처리여부 명으로 처리여부 코드 조회 */
    String getProcYn(String procYnNm);
    
    /** 엑셀업로드 배송정보 업데이트 */
    int updateDlvryInfoExcel(UsimDlvryChangeVO searchVO);
    
    /** 신청정보(유심교체) DTL 테이블 유심정보 업데이트 */
    int updateUsimInfo(UsimDlvryChangeVO searchVO);

    /** 신청정보(유심교체) MST 테이블 상태 업데이트 */
    int updateStatusMst(UsimDlvryChangeVO searchVO);

    /** 신청정보(유심교체) DTL 테이블 상태 업데이트 */
    int updateStatusDtl(UsimDlvryChangeVO searchVO);

    /** 신청정보(유심교체) MST 테이블 처리/메모 업데이트 */
    int updateUsimChgProcExcel(UsimDlvryChangeVO searchVO);

    /** CUSTOMER_ID 조회(유효한 계약번호인지 여부 확인)*/
    String selectMspJuoSubInfoByContractNum(String contractNum);

    /** 신청정보(유심교체) MST 테이블 주문번호 조회 */
    String selectSeqByContractNum(String contractNum);

    /** 엑셀 내 중복확인 */
    int dupChkByContractNum(String contractNum);

    /** 신청정보(유심교체) 임시테이블 INSERT */
    int insertUsimChangeChk(UsimDlvryChangeVO searchVO);

    /** 임시테이블에서 고객번호 리스트 조회 */
    List<String> selectCustomerIdList();

    /** 신청정보(유심교체) 임시테이블 조회 */
    List<UsimDlvryChangeVO> selectUsimChangeChk(String customerId);

    /** 신청정보(유심교체) 주문번호(SEQ) 생성 */
    String selectUsimChangeSeq();

    /** 신청정보(유심교체) MST 테이블 생성 */
    int insertUsimChangeMst(UsimDlvryChangeVO searchVO);
    
    /** 신청정보(유심교체) DTL 테이블 생성 */
    int insertUsimChangeDtl(UsimDlvryChangeVO searchVO);

    /** 임시테이블 데이터 삭제 */
    int deleteUsimChangeChk();

    /** 엑셀 업로드 후 SMS(배송안내) 발송 대상 조회 */
    List<UsimDlvryChangeVO> selectSmsSendList();

    /** 직접등록 고객정보 조회 */
    List<UsimDlvryChangeVO> selectCustomerInfo(String subscriberNo);

    /** 신청정보(유심교체) 연동번호(OSST_NO) 생성 */
    String selectOsstNo();

    /** OSST 연동이력 생성 */
    void insertOsstHist(Map<String, Object> param);
    
    /** 신청정보(유심교체) 직접등록 상세조회 */
    List<EgovMap> selectUsimChgInfo(String seq);

    /** 직접등록(수정) 업데이트 */
    int updateUsimChg(UsimDlvryChangeVO searchVO);
    
    /** 유심모델 NFC여부 조회 */
    Map<String, Object> selectUsimModelData(String intmMdlId);
}
