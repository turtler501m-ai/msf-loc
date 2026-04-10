package com.ktmmobile.mcp.point.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.point.service.PointSvc;
import com.ktmmobile.mcp.point.dto.CustPointGiveTgtBasDto;
import com.ktmmobile.mcp.common.dto.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.point.dto.CustPointGiveBaseBasDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDtlDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;
import com.ktmmobile.mcp.point.dto.McpUserCntrMngDto;

@Controller
@EnableAsync
@EnableScheduling
public class PointController {

    private static final Logger logger = LoggerFactory.getLogger(PointController.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    
	@Autowired
    private PointSvc pointSvc;
	
	
	@RequestMapping("/expirePoint")
	public String expirePointExcute() {

		expirePoint();
		
		return "pointController";
	}
	

	@RequestMapping("/savePoint")
	public String savePointExcute() {

		savePoint();
		
		return "pointController";
	}
	
	
	
	/**
	 1. 호출 주기
	 - 일 1회

	 2. 포인트 소멸 처리
	  1) 소멸 포인트 대상 조회 : D+1 기준으로 사용기간이 경과한 포인트 대상 추출
         - NMCP_CUST_POINT_ACU_TXN_DTL(고객포인트적립내역상세) > 잔여포인트 0 && NMCP_CUST_POINT_TXN(고객포인트내역) 포인트사용가능종료일자 < 현재일자
      2) 포인트 SUM 정보 현행화
         NMCP_CUST_POINT_ACU_TXN_DTL(고객포인트적립내역상세)  - 차감정보 UPDATE 
         NMCP_CUST_POINT_TXN(고객포인트내역) - 차감 INSERT
         NMCP_CUST_POINT_TXN_DTL(고객포인트내역상세) - 차감정보 INSERT
         NMCP_CUST_POINT_GIVE_BASE_BAS(고객포인트지급기준기본) - SUM 정보 UPDATE
	 */

	// 일 1회	 00:10:00
	//@Scheduled(cron = "0 10 0 * * *")
	public void expirePoint() {

		try {
			
			String curIp = getIp();
			
			// 포인트 소멸 대상 조회
			List<CustPointTxnDtlDto> expireCustPointList = pointSvc.selectExpireCustPoint();
			
			// 포인트 소멸 처리
			if(expireCustPointList != null && expireCustPointList.size() > 0) {
				
				for(CustPointTxnDtlDto item : expireCustPointList) {
					
					//적립상세 잔여포인트 update
					CustPointTxnDtlDto expireCustPointTxnDtlDto = new CustPointTxnDtlDto();
					expireCustPointTxnDtlDto.setPointTxnSeq(item.getPointTxnSeq());
					expireCustPointTxnDtlDto.setRemainPoint(item.getRemainPoint());
					expireCustPointTxnDtlDto.setPoint(item.getRemainPoint());
					expireCustPointTxnDtlDto.setAmdIp(curIp);
					expireCustPointTxnDtlDto.setAmdId("BATCH");					
					pointSvc.updateCustPointAcuTxnDtl(expireCustPointTxnDtlDto);

					//고객포인트내역 insert
					CustPointTxnDto expireCustPointTxnDto = new CustPointTxnDto();					
					expireCustPointTxnDto.setPointTxnSeq(item.getPointTxnSeq());		// 포인트내역일련번호
					expireCustPointTxnDto.setPointCustSeq(item.getPointCustSeq());	// 포인트고객일련번호
					expireCustPointTxnDto.setPointDivCd("MP");				// 포인트분류코드
					expireCustPointTxnDto.setPointTrtCd("E");				// 포인트처리코드
					expireCustPointTxnDto.setPointGiveBaseSeq(0);    // 포인트지급기준일련번호
					expireCustPointTxnDto.setPoint(item.getRemainPoint());						// 포인트
					expireCustPointTxnDto.setTotRemainPoint(item.getRemainPoint());             // 총잔여포인트
					expireCustPointTxnDto.setPointUsePosblStDate(getFormatString("yyyyMMddHHmmss"));		// 포인트사용가능시작일자
					expireCustPointTxnDto.setPointUsePosblEndDate(getFormatString("yyyyMMddHHmmss"));	// 포인트사용가능종료일자
					expireCustPointTxnDto.setPointTxnRsnCd("E21");			// 포인트처리사유코드
					expireCustPointTxnDto.setPointTxnDtlRsnDesc("사용기간만료");		// 포인트처리상세사유설명
					expireCustPointTxnDto.setPointAllGiveSeq(0);		// 포인트일괄지급일련번호
					expireCustPointTxnDto.setUserPointTrtSeq(0);		// 고객포인트처리일련번호
					expireCustPointTxnDto.setRequestKey(0);				// 가입신청_키
					expireCustPointTxnDto.setPointTrtMemo("사용기간만료");			// 포인트처리메모
					expireCustPointTxnDto.setCretIp(curIp);					// 생성IP
					expireCustPointTxnDto.setCretId("BATCH");						// 생성자ID
					expireCustPointTxnDto.setAmdIp(curIp);
					expireCustPointTxnDto.setAmdId("BATCH");
					
					int pointTxnSeq = pointSvc.insertCustPointTxn(expireCustPointTxnDto);
					
					//고객포인트내역 insert
					CustPointTxnDtlDto custPointTxnDtlDto = new CustPointTxnDtlDto();
					custPointTxnDtlDto.setPointTxnSeq(pointTxnSeq);
					custPointTxnDtlDto.setAcuPointTxnSeq(item.getPointTxnSeq());
					custPointTxnDtlDto.setRemainPoint(0);
					custPointTxnDtlDto.setUsePoint(item.getRemainPoint());
					custPointTxnDtlDto.setCretIp(curIp);					// 생성IP
					custPointTxnDtlDto.setCretId("BATCH");						// 생성자ID
					custPointTxnDtlDto.setAmdIp(curIp);
					custPointTxnDtlDto.setAmdId("BATCH");
					
					pointSvc.insertCustPointTxnDtl(custPointTxnDtlDto);
					
					//고객포인트지급기준기본 update
					pointSvc.updateCustPointGiveBaseBas(expireCustPointTxnDto);

				}
			}
			
		}catch(Exception e) {
			logger.error("expirePoint() Exception = {}", e.getMessage());
			
		}
	}


	/**
	 1. 호출 주기
	 - 일 1회

	 2. 포인트 적립 처리
	  1) 적립 대상 추출
         - NMCP_CUST_POINT_GIVE_TGT_BAS(고객포인트지급대상기본) 포인트지급일자에 해당하는 지급대상 추출
           NMCP_POINT_GIVE_BASE_BAS(포인트지급기준기본) 포인트지급기준일련번호에 해당하는 보조 정보 추출
         - 포인트지급기준일련번호에 해당하는 추가조건 조회
           서식지 계약번호 등
      2) 포인트 적립 대상 고객 유효성 체크
         회선 계약번호 존재여부
         - 서비스계약번호만 있는 경우 : 회선 존재여부 체크
         - 회선 사용중 여부 : 일시정지, 해지 회선인 경우 적립 불가
      3) 포인트 적립 처리
         - 고객포인트정보 없는 경우 : NMCP_CUST_POINT_GIVE_BASE_BAS(고객포인트지급기준기본)
            NMCP_CUST_POINT_GIVE_BASE_BAS(고객포인트지급기준기본) - 고객/포인트 INSERT
            NMCP_CUST_POINT_TXN(고객포인트내역) - 적립 INSERT
            NMCP_CUST_POINT_ACU_TXN_DTL(고객포인트적립내역상세)  - 적립 INSERT
            NMCP_CUST_POINT_GIVE_TGT_BAS(고객포인트지급대상기본) - 포인트고객일련번호 및 지급완료일자 UPDATE 
         - 고객포인트정보 기존재하는 경우 : NMCP_CUST_POINT_GIVE_BASE_BAS(고객포인트지급기준기본)
            NMCP_CUST_POINT_GIVE_BASE_BAS(고객포인트지급기준기본) - SUM 정보 UPDATE
            NMCP_CUST_POINT_TXN(고객포인트내역) - 적립 INSERT
            NMCP_CUST_POINT_ACU_TXN_DTL(고객포인트적립내역상세)  - 적립 INSERT
            NMCP_CUST_POINT_GIVE_TGT_BAS(고객포인트지급대상기본) - 포인트고객일련번호 및 지급완료일자 UPDATE 
     3. 추후 적용
        - 요금제 및 월정액 기준으로 제공하는 정책 사용시 사용 - NMCP_POINT_GIVE_BASE_BAS 
	 */

	// 일 1회 00:20:00
	//@Scheduled(cron = "0 20 0 * * *")
	public void savePoint() {

		try {
			String curIp = getIp();
			
			// 포인트 적립대상 추출
			List<CustPointGiveTgtBasDto> custPointGiveTgtBasList = pointSvc.selectCustPointGiveTgtList();
			
			// 포인트 적립 처리
			if(custPointGiveTgtBasList != null && custPointGiveTgtBasList.size() > 0) {
				
				for(CustPointGiveTgtBasDto item : custPointGiveTgtBasList) {
					
					// 포인트 적립 대상 고객 유효성 체크
			         if("".contentEquals(item.getSvcCntrNo())) {
			        	 // 서비스계약번호 없는 경우 적립 SKIP
			        	 
			        	 logger.debug("서비스계약번호 없는 경우 적립 SKIP");
			        	 
			         } else {
			        	 // 서비스계약번호 있는 경우
			        	 // 회선정보 조회
			     		 McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
			     	  	 userCntrMngDto.setSvcCntrNo(item.getSvcCntrNo());

			     	  	 
			     		 //---- API 호출 S ----//
			       		 RestTemplate restTemplate = new RestTemplate();
			     		 McpUserCntrMngDto mcpUserCntrMngDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);


			     		 // 적립인 경우
			     		 if(item.getPointTrtCd().equals("S")) {
			     			 
			     			 if(mcpUserCntrMngDto == null) {
			     				 // 서비스계약번호 없는 경우 적립 SKIP
			     				 
			     				logger.debug("서비스계약번호 없는 경우 적립 SKIP");
			     				 
			     			 } if(!mcpUserCntrMngDto.getSubStatus().equals("A")) {
			     				 // 사용중(개통중)이 아닌 경우 적립 SKIP
			     				 
			     				logger.debug("사용중(개통중)이 아닌 경우 적립 SKIP");
			     				 
			     			 } else {
				     			// 포인트 적립 처리
			     				 
				    	        // 포인트 회선일련번호 조회
				     			CustPointGiveBaseBasDto targetCustIn = new CustPointGiveBaseBasDto();
				     			targetCustIn.setSvcCntrNo(item.getSvcCntrNo());			    	        
				    	        CustPointGiveBaseBasDto targetCust = pointSvc.selectCustPointGiveBaseBas(targetCustIn);
				    	        
				    	        if(targetCust == null) {
				    	        	// 포인트 신규 고객 생성 + 포인트 적립
				    	        	
				    	    		//고객포인트지급기준기본 insert
				    	        	CustPointGiveBaseBasDto custPointGiveBaseBasDto = new CustPointGiveBaseBasDto();
				    	        	custPointGiveBaseBasDto.setUserId("");
				    	            custPointGiveBaseBasDto.setSvcCntrNo(targetCustIn.getSvcCntrNo());
				                    custPointGiveBaseBasDto.setTotAcuPoint(item.getPoint());
				                    custPointGiveBaseBasDto.setTotUsePoint(0);
					                custPointGiveBaseBasDto.setTotRemainPoint(item.getPoint());
				                    custPointGiveBaseBasDto.setTotExtinctionPoint(0);
		                            custPointGiveBaseBasDto.setCretIp(curIp);
	                                custPointGiveBaseBasDto.setCretId("BATCH");
	                                custPointGiveBaseBasDto.setAmdIp(curIp);
	                                custPointGiveBaseBasDto.setAmdId("BATCH");	    	        	
				    	        	
				    	        	int pointCustSeq = pointSvc.insertCustPointGiveBaseBas(custPointGiveBaseBasDto);
				    	        	
				    	        	CustPointGiveBaseBasDto targetCust2 = pointSvc.selectCustPointGiveBaseBas(targetCustIn);
				    	        	
				    	        	targetCust = new CustPointGiveBaseBasDto();
				    	        	targetCust.setPointCustSeq(targetCust2.getPointCustSeq());
				    	        	targetCust.setSvcCntrNo(targetCust2.getSvcCntrNo());
				    	        	
				    	        } else {
				    	        	// 포인트 기존 고객 정보에 포인트 적립

				    	        	//고객포인트지급기준기본 update
				    	        	CustPointTxnDto custPointDto = new CustPointTxnDto();
				    	        	custPointDto.setPointTrtCd("S");
				    	        	custPointDto.setPointCustSeq(targetCust.getPointCustSeq());	// 포인트고객일련번호
				    	        	custPointDto.setPoint(item.getPoint());	// 포인트
				    	        	custPointDto.setAmdIp(curIp);
				    	        	custPointDto.setAmdId("BATCH");
									
				    	        	pointSvc.updateCustPointGiveBaseBas(custPointDto);
				    	        }
				    	        
								//고객포인트내역 insert
								CustPointTxnDto custPointTxnDto = new CustPointTxnDto();					
								custPointTxnDto.setPointCustSeq(targetCust.getPointCustSeq());	// 포인트고객일련번호
								custPointTxnDto.setPointDivCd(item.getPointDivCd());				// 포인트분류코드
								custPointTxnDto.setPointTrtCd(item.getPointTrtCd());				// 포인트처리코드
								custPointTxnDto.setPointGiveBaseSeq(item.getPointGiveBaseSeq());    // 포인트지급기준일련번호
								custPointTxnDto.setPoint(item.getPoint());						// 포인트
								custPointTxnDto.setTotRemainPoint(0);             // 총잔여포인트
								custPointTxnDto.setPointUsePosblStDate(item.getPointUsePosblStDate());		// 포인트사용가능시작일자
								custPointTxnDto.setPointUsePosblEndDate(item.getPointUsePosblEndDate());	// 포인트사용가능종료일자
								custPointTxnDto.setPointTxnRsnCd(item.getPointTxnRsnCd());			// 포인트처리사유코드
								custPointTxnDto.setPointTxnDtlRsnDesc(item.getPointTxnDtlRsnDesc());		// 포인트처리상세사유설명
								custPointTxnDto.setPointAllGiveSeq(item.getPointAllGiveSeq());		// 포인트일괄지급일련번호
								custPointTxnDto.setUserPointTrtSeq(item.getCustPointGiveTgtSeq());		// 고객포인트처리일련번호
								custPointTxnDto.setRequestKey(item.getOrdrNo());				// 가입신청_키
								custPointTxnDto.setPointTrtMemo("BATCH");			// 포인트처리메모
								custPointTxnDto.setCretIp(curIp);					// 생성IP
								custPointTxnDto.setCretId("BATCH");						// 생성자ID
								custPointTxnDto.setAmdIp(curIp);
								custPointTxnDto.setAmdId("BATCH");

								int pointTxnSeq = pointSvc.insertCustPointTxn(custPointTxnDto);
								
								//고객포인트적립내역상세 insert
								CustPointTxnDtlDto custPointTxnDtlDto = new CustPointTxnDtlDto();
								custPointTxnDtlDto.setRemainPoint(item.getPoint());
								custPointTxnDtlDto.setCretIp(curIp);		// 생성IP
								custPointTxnDtlDto.setCretId("BATCH");		// 생성자ID
								custPointTxnDtlDto.setAmdIp(curIp);
								custPointTxnDtlDto.setAmdId("BATCH");
								
								int resultCnt = pointSvc.insertCustPointAcuTxnDtl(custPointTxnDtlDto);
								

								// 처리결과 현행화
								CustPointGiveTgtBasDto tgtResult = new CustPointGiveTgtBasDto();
								tgtResult.setCustPointGiveTgtSeq(item.getCustPointGiveTgtSeq());
								tgtResult.setPointCustSeq(targetCust.getPointCustSeq());	// 포인트고객일련번호
								if(resultCnt == 1) {
									tgtResult.setPointGiveCpltYn("Y");	// 포인트지급완료여부
									tgtResult.setPointGiveCpltDate(getFormatString("yyyyMMddHHmmss"));	// 포인트지급완료일자
								} else {
									tgtResult.setPointGiveCpltYn("N");	// 포인트지급완료여부
								}
								tgtResult.setAmdIp(curIp);
								tgtResult.setAmdId("BATCH");
								
								int resultCntdtl = pointSvc.updateCustPointGiveTgtList(tgtResult);
								
				    	    //} else {
				    	    	// 사용, 소멸 --> 요청시 추가

				    	    
				    	    }

			     			
			     		}
			     		
			         }
			        	 
			    }
			         
		    }
			
		}catch(Exception e) {
			logger.error("expirePoint() Exception = {}", e.getMessage());
			
		}
	}
	
	

	private String getIp() {

		InetAddress local = null;
		try {
			local = InetAddress.getLocalHost();
		}
		catch ( UnknownHostException e ) {
			e.printStackTrace();
		}

		if( local == null ) {
			return "";
		}
		else {
			String ip = local.getHostAddress();
			return ip;
		}

	}
	
	
    public static String getFormatString(String pattern) {
        SimpleDateFormat formatter =
                new SimpleDateFormat(pattern, Locale.KOREA);
        String dateString = formatter.format(new Date());
        return dateString;
    }	
	
	
		
}
