package com.ktis.msp.batch.job.cls.crdtloanmgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("crdtLoanMgmtMapper")
public interface CrdtLoanMgmtMapper {
	
	/**
	 * 대리점정보조회
	 * @param param
	 * <ul>대리점 정보를 조회한다.</ul>
	 * <li>ORGN_ID=조직ID</li>
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOrgnInfo(Map<String, Object> param);
	
	/**
	 * 여신잔액조회
	 * @param param
	 * <ul>대리점 여신잔액을 조회한다</ul>
	 * <li>ORGN_ID=조직ID</li>
	 * <li>CRDT_LOAN_TYPE_CD=여신유형코드(10:담보여신, 20:개통여신)</li>
	 * @return
	 * @throws Exception
	 */
	public Long getCrdtLoanBal(Map<String, Object> param);
	
	/**
	 * 여신사용이력 등록
	 * @param param
	 * <ul>여신사용이력을 등록한다</ul>
	 * <li>ORGN_ID=조직ID</li>
	 * <li>CRDT_LOAN_TYPE_CD=여신유형코드(10:담보, 20:개통)</li>
	 * <li>CRDT_LOAN_USG_TYPE=여신사용유형(CLS0017)</li>
	 * <li>CRDT_LOAN_USG_DT=여신사용일자</li>
	 * <li>CRDT_LOAN_USG_AMT=여신사용금액</li>
	 * <li>ORG_NUM=주문번호</li>
	 * <li>INOUT_NUM=입출고번호</li>
	 * <li>ORG_QNTY=주문수량</li>
	 * <li>CUST_ID=고객ID</li>
	 * <li>SRVC_CNTR_NUM=서비스계약번호</li>
	 * <li>SRVC_TELNUM=서비스전화번호</li>
	 * <li>REGST_ID=등록자ID</li>
	 * <li>RVISN_ID=수정자ID</li>
	 * @throws Exception
	 */
	public void insertCrdtLoanUsgHst(Map<String, Object> param);
	
	/**
	 * 여신정보(사용) 수정
	 * @param param
	 * <ul>여신사용에 따른 여신잔액을 수정한다.</ul>
	 * <li>ORGN_ID=조직ID</li>
	 * <li>CRDT_LOAN_TYPE_CD=여신유형코드(10:담보, 20:개통)</li>
	 * <li>CRDT_LOAN_AMT=여신금액</li>
	 * <li>RVISN_ID=수정자ID</li>
	 * @throws Exception
	 */
	public void updateCrdtLoanInfoUsg(Map<String, Object> param);
	
	
	/**
	 * 여신부활이력 등록
	 * @param param
	 * <ul>여신부활이력을 등록한다</ul>
	 * <li>ORGN_ID=조직ID</li>
	 * <li>CRDT_LOAN_TYPE_CD=여신유형코드(10:담보, 20:개통)</li>
	 * <li>CRDT_LOAN_RVL_TYPE=여신부활유형(CLS0018)</li>
	 * <li>CRDT_LOAN_RVL_DT=여신부활일자</li>
	 * <li>CRDT_LOAN_RVL_AMT=여신부활금액</li>
	 * <li>ORD_NUM=주문번호</li>
	 * <li>PRDT_SRL_NUM=제품일련번호</li>
	 * <li>CUST_ID=고객ID</li>
	 * <li>SRVC_CNTR_NUM=서비스계약번호</li>
	 * <li>OUT_AMT=출고금액</li>
	 * <li>INST_AMT=할부금액</li>
	 * <li>DPST_PROC_DT=입금처리일자</li>
	 * <li>DPST_SRL_NUM=입금일련번호</li>
	 * <li>SRL_MGMT_NUM=전문관리번호</li>
	 * <li>REGST_ID=등록자ID</li>
	 * <li>RVISN_ID=수정자ID</li>
	 * @throws Exception
	 */
	public void insertCrdtLoanRvlHst(Map<String, Object> param);
	
	/**
	 * 여신정보(부활) 수정
	 * @param param
	 * <ul>여신부활에 따른 여신잔액을 수정한다.</ul>
	 * <li>ORGN_ID=조직ID</li>
	 * <li>CRDT_LOAN_TYPE_CD=여신유형코드(10:담보, 20:개통)</li>
	 * <li>CRDT_LOAN_USG_AMT=여신금액</li>
	 * <li>RVISN_ID=수정자ID</li>
	 * @return 
	 * @throws Exception
	 */
	public int updateCrdtLoanInfoRvl(Map<String, Object> param);
	
	/**
	 * 여신정보(Map) 등록
	 * @param param
	 * <ul>여신정보를 등록한다</ul>
	 * <li>ORGN_ID=조직ID</li>
	 * <li>CRDT_LOAN_TYPE_CD=여신유형코드(10:담보, 20:개통)</li>
	 * <li>GNRL_CRDT_LOAN_AMT=일반여신금액</li>
	 * <li>SPCL_CRDT_LOAN_AMT=특별여신금액</li>
	 * <li>CRDT_LOAN_USG_AMT=여신사용금액</li>
	 * <li>CRDT_LOAN_RVL_AMT=여신부활금액</li>
	 * <li>CRDT_LOAN_BAL=여신잔액</li>
	 * <li>REGST_ID=사용자ID</li>
	 * <li>RVISN_ID=수정자ID</li>
	 * @throws Exception
	 */
	public void insertCrdtLoanInfoMap(Map<String, Object> param);
	
	/**
	 * 여신부활대상계약조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getCrdtLoanRvlTrgtCntrList(Map<String, Object> param);
	
	/**
	 * 종료된 여신상세정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getEndCrdtAgncyLoanDtlList(Map<String, Object> param);
	
	/**
	 * 여신초기화
	 * @param param
	 * @throws Exception
	 */
	public void setCrdtLoanInit(Map<String, Object> param);
	
	/**
	 * 유심수납 여신부활 대상 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getCrdtLoanRvlUsimPymList(Map<String, Object> param);
	
	/**
	 * 여신부활대상계약조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getCrdtLoanRvlRtnList(Map<String, Object> param);
	
	// 개통부활목록조회
	public List<Map<String, Object>> getOpenRvlList(Map<String, Object> param);
	
	// 개통취소목록조회
	public List<Map<String, Object>> getOpenCnclList(Map<String, Object> param);
	
	// 기기변경 여신부활 대상
	public List<Map<String, Object>> getDvcChgRvlList(Map<String, Object> param);
	
	// 기기변경 취소대상
	public List<Map<String, Object>> getDvcChgCnclList(Map<String, Object> param);
	
}
