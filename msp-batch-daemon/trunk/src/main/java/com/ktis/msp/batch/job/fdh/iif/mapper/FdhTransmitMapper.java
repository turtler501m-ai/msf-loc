package com.ktis.msp.batch.job.fdh.iif.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.fdh.iif.vo.FdhTransmitVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("fdhTransmitMapper")
public interface FdhTransmitMapper {
	// 계약정보현행화 1차
	List<String> getFdhInf001();
	
	// 계약현행화이력
	List<String> getFdhInf002();
	
	// 상품현행화정보
	List<String> getFdhInf003();
	
	// 단말현행화정보
	List<String> getFdhInf004();
	
	// MVNO추가정보
	List<String> getFdhInf005();
	
	// 요금제
	List<String> getFdhInf006();
	
	// 요금제스펙
	List<String> getFdhInf007();
	
	// 조직
	List<String> getFdhInf008();
	
	// 제품관리
	List<String> getFdhInf009();
	
	// 모델단가
	List<String> getFdhInf010();
	
	// 계약정보현행화 2차
	List<String> getFdhInf011();
	
	// 조직정보이력
	List<String> getFdhInf012();
	
	// 판매정책정보
	List<String> getFdhInf013();
	
	// 계약해지정보
	List<String> getFdhInf014();
	
	// ARPU 집계 연동
	void getFdhArpuSttc(FdhTransmitVO searchVO, ResultHandler resultHandler);
	
	// ARPU 집계 연동 Proc
	void callArpuSttcProc(Map<String, Object> param);
	
	// 선불국제전화사용량
	List<String> getFdhInf016();
	
	// 선불전화사용량
	List<String> getFdhInf017();
	
	// 해지, 개통 데이터
	List<String> getFdhInf018();
	
	// 가상계좌입금내역
	List<String> getFdhInf019();
	
	// 예치금사용내역
	List<String> getFdhInf020();
	
	// 실시간출금내역
	List<String> getFdhInf021();
	
	// POS 충전내역
	List<String> getFdhInf022();
	
	// 선불카드사용내역
	List<String> getFdhInf023();
	
	// 물류정보
	List<String> getFdhInf024();
	
	// 입출고관리
	List<String> getFdhInf025();
	
	// 실시간선불정산대상
	List<String> getFdhInf026();
	
	// 개통관리 이미지
	List<String> getFdhInf027();
	
	// 기본수수료 1
	List<String> getFdhInf028();	

	// 기본수수료 2
	List<String> getFdhInf029();
	
	// 명변수수료
	List<String> getFdhInf030();
	
	// 자동이체수수료
	List<String> getFdhInf031();
	
	// 재충전수수료
	List<String> getFdhInf032();
	
	// 제휴포인트
	List<String> getFdhInf033();
	
	// 제휴포인트2
	List<String> getFdhInf034();

	// 매각채권수납
	List<String> getFdhInf035();
	
	// 매각채권수납2
	List<String> getFdhInf036();
	
	// 월정산내역
	List<String> getFdhInf037();
	
	// 월정산내역2
	List<String> getFdhInf038();
	
	// 월정산내역3
	List<String> getFdhInf039();
	
	// 부가서비스가입내역
	List<String> getFdhInf040();
	
	// 부가서비스가입내역2
	List<String> getFdhInf041();
}