<%------------------------------------------------------------------------------
 FILE NAME : INIsecurestart.jsp
 AUTHOR : ts@inicis.com
 DATE : 2007/08
 USE WITH : config.jsp, INIpay50.jar
 
 이니페이 플러그인을 이용하여 지불을 요청한다.
 
 Copyright 2007 Inicis, Co. All rights reserved.
------------------------------------------------------------------------------%>

<%@ page language = "java" contentType = "text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<html>
<head>
<title>INIpay50 결제페이지 데모</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/group.css" type="text/css">
<style>
body, tr, td {font-size:10pt; font-family:굴림,verdana; color:#433F37; line-height:19px;}
table, img {border:none}

/* Padding ******/ 
.pl_01 {padding:1 10 0 10; line-height:19px;}
.pl_03 {font-size:20pt; font-family:굴림,verdana; color:#FFFFFF; line-height:29px;}

/* Link ******/ 
.a:link  {font-size:9pt; color:#333333; text-decoration:none}
.a:visited { font-size:9pt; color:#333333; text-decoration:none}
.a:hover  {font-size:9pt; color:#0174CD; text-decoration:underline}

.txt_03a:link  {font-size: 8pt;line-height:18px;color:#333333; text-decoration:none}
.txt_03a:visited {font-size: 8pt;line-height:18px;color:#333333; text-decoration:none}
.txt_03a:hover  {font-size: 8pt;line-height:18px;color:#EC5900; text-decoration:underline}
</style>

<script>
	var openwin=window.open("./childwin.html","childwin","width=299,height=149");
	openwin.close();
	
	function show_receipt(tid) // 영수증 출력
	{
		if('${iniDto.resultCode}%>' == "00")
		{
			var receiptUrl = "https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/mCmReceipt_head.jsp?noTid=${iniDto.tid}&noMethod=1";
			window.open(receiptUrl,"receipt","width=430,height=700");
		}
		else
		{
			alert("해당하는 결제내역이 없습니다");
		}
	}
		  
	function errhelp() // 상세 에러내역 출력 
	{
		var errhelpUrl = "http://www.inicis.com/ErrCode/Error.jsp?result_err_code=${iniDto.resultErrorCode}&mid=${iniDto.mid}";
		window.open(errhelpUrl,"errhelp","width=520,height=150, scrollbars=yes,resizable=yes");
	}
	
</script>

<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#242424" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0> 
<table width="632" border="0" cellspacing="0" cellpadding="0">
  <tr> 


<c:choose>
	<c:when test="${iniDto.paymethod eq 'Card' || iniDto.paymethod eq 'VCard'}">
	<td height="85" background="${pageContext.request.contextPath}/resources/images/inicis/card.gif" style="padding:0 0 0 64">
	</c:when>
	<c:when test="${iniDto.paymethod eq 'DirectBank'}">
	<td height="85" background="${pageContext.request.contextPath}/resources/images/inicis/bank.gif" style="padding:0 0 0 64">
	</c:when>
</c:choose>
    				
<!-------------------------------------------------------------------------------------------------------
 *
 *  아래 부분은 모든 결제수단의 공통적인 결과메세지 출력 부분입니다.
 *
 *  1. inipay.GetResult("ResultCode")  (결 과 코 드)
 *  2. inipay.GetResult("ResultMsg")   (결과 메세지)
 *  3. inipay.GetResult("paymethod")   (결 제 수 단)
 *  4. inipay.GetResult("TID")         (거 래 번 호)
 *  5. inipay.GetResult("MOID")        (주 문 번 호)
 -------------------------------------------------------------------------------------------------------->
 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="3%" valign="top"><img src="${pageContext.request.contextPath}/resources/images/inicis/title_01.gif" width="8" height="27" vspace="5" alt=""></td>
          <td width="97%" height="40" class="pl_03"><span color="#FFFFFF"><b>결제결과</b></span></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td align="center" bgcolor="6095BC">
      <table width="620" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td bgcolor="#FFFFFF" style="padding:0 0 0 56">
		  <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="7"><img src="${pageContext.request.contextPath}/resources/images/inicis/life.gif" width="7" height="30" alt=""></td>
                <td background="${pageContext.request.contextPath}/resources/images/inicis/center.gif" alt=""><img src="${pageContext.request.contextPath}/resources/images/inicis/icon03.gif" width="12" height="10" alt="">
                
                <!-------------------------------------------------------------------------------------------------------
                 * 1. inipay.GetResult("ResultCode") 										*	
                 *       가. 결 과 코 드: "00" 인 경우 결제 성공[무통장입금인 경우 - 고객님의 무통장입금 요청이 완료]	*
                 *       나. 결 과 코 드: "00"외의 값인 경우 결제 실패  						*
                 --------------------------------------------------------------------------------------------------------> 
                 
                  <b> 
					<c:choose>
						<c:when test="${iniDto.resultCode eq '00' }">
						고객님의 결제요청이 성공되었습니다.
						</c:when>
						<c:otherwise>
						고객님의 결제요청이 실패되었습니다.
						</c:otherwise>
					</c:choose>
					</b>
				</td>
                <td width="8"><img src="${pageContext.request.contextPath}/resources/images/inicis/right.gif" width="8" height="30" alt=""></td>
              </tr>
            </table>
            <br>
            <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="407"  style="padding:0 0 0 9"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon.gif" width="10" height="11" alt=""> 
                  <strong><span color="433F37">결제내역</span></strong></td>
                <td width="103">&nbsp;</td>                
              </tr>
              <tr> 
                <td colspan="2"  style="padding:0 0 0 23">
		  <table width="470" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    
                <!-------------------------------------------------------------------------------------------------------
                 * 2. inipay.GetResult("paymethod")
                 *       가. 결제 방법에 대한 값
                 *       	1. 신용카드 	- 	Card
                 *       	2. ISP		-	VCard
                 *       	3. 은행계좌	-	DirectBank
                 *       	4. 무통장입금	-	VBank
                 *       	5. 휴대폰	- 	HPP
                 *       	6. 전화결제 (ars전화 결제)	-	Ars1588Bill
                 *       	7. 전화결제 (받는전화결제)	-	PhoneBill
                 *       	8. OK CASH BAG POINT		-	OCBPoint
                 *       	9. 문화상품권			-	Culture
                 *       	10. 틴캐시 결제 		- 	TEEN
                 *       	11. 스마트문상 		-	DGCL
                 *       	12. 도서문화 상품권 		-	BCSH
                 *-------------------------------------------------------------------------------------------------------->
                      <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                      <td width="109" height="25">결 제 방 법</td>
                      <td width="343">${iniDto.paymethod}</td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                      <td width="109" height="26">결 과 코 드</td>
                      <td width="343"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td>${iniDto.resultCode}</td>
                            <td width='142' align='right'>
                          
                <!-------------------------------------------------------------------------------------------------------
                 * 3. inipay.GetResult("ResultCode") 값에 따라 "영수증 보기" 또는 "실패 내역 자세히 보기" 버튼 출력		*
                 *       가. 결제 코드의 값이 "00"인 경우에는 "영수증 보기" 버튼 출력					*
                 *       나. 결제 코드의 값이 "00" 외의 값인 경우에는 "실패 내역 자세히 보기" 버튼 출력			*
                 -------------------------------------------------------------------------------------------------------->
		<!-- 실패결과 상세 내역 버튼 출력 --> 
		
							<c:choose> 
								<c:when test="${iniDto.resultCode eq '00'}">
									<a href='javascript:show_receipt();'><img src='${pageContext.request.contextPath}/resources/images/inicis/button_02.gif' width='94' height='24' border='0' alt=""></a>
								</c:when>
								<c:otherwise>
									<a href='javascript:errhelp();'><img src='${pageContext.request.contextPath}/resources/images/inicis/button_01.gif' width='142' height='24' border='0' alt=""></a>
								</c:otherwise>
							</c:choose>
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                
                <!-------------------------------------------------------------------------------------------------------
                 * 4. inipay.GetResult("ResultMsg") 										*
                 *    - 결과 내용을 보여 준다 실패시에는 "[에러코드] 실패 메세지" 형태로 보여 준다.                     *
                 *		예> [9121]서명확인오류									*
                 -------------------------------------------------------------------------------------------------------->
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                      <td width="109" height="25">결 과 내 용</td>
                      <td width="343">${iniDto.resultMsg}</td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    </tr>
                    
                <!-------------------------------------------------------------------------------------------------------
                 * 5. inipay.GetResult("tid")											*
                 *    - 이니시스가 부여한 거래 번호 -모든 거래를 구분할 수 있는 키가 되는 값			        *
                 -------------------------------------------------------------------------------------------------------->
                    <tr> 
                      <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                      <td width="109" height="25">거 래 번 호</td>
                      <td width="343">${iniDto.tid}</td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    </tr>
                    
                <!-------------------------------------------------------------------------------------------------------
                 * 6. inipay.GetResult("MOID")											*
                 *    - 상점에서 할당한 주문번호 									*
                 -------------------------------------------------------------------------------------------------------->
                    <tr> 
                      <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                      <td width="109" height="25">주 문 번 호</td>
                      <td width="343">${iniDto.oid}</td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    </tr>
                    
                <!-------------------------------------------------------------------------------------------------------
                 * 7. inipay.GetResult("TotPrice")										*
                 *    - 결제완료 금액                  									*
	 			 *																					*
	 			 * 결제 되는 금액 =>원상품가격과  결제결과금액과 비교하여 금액이 동일하지 않다면  *
	 			 * 결제 금액의 위변조가 의심됨으로 정상적인 처리가 되지않도록 처리 바랍니다. (해당 거래 취소 처리) *
	 			 *																									*
                 -------------------------------------------------------------------------------------------------------->
                     
                    <tr> 
                      <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                      <td width="109" height="25">결제완료금액</td>
                      <td width="343">${iniDto.totPrice} 원</td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    </tr>

	<!-- 아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  1.  신용카드 (OK CASH BAG POINT 복합 결제 내역 )	 -->
   <c:if test="${iniDto.paymethod eq 'Card' || iniDto.paymethod eq 'paymethod'}">
						<tr> 
                    		  <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                    		  <td width="109" height="25">신용카드번호</td>
                    		  <td width="343">${iniDto.CARD_Num}
           </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    		</tr>
				<tr> 
                                  <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                                  <td width="109" height="25">승 인 날 짜</td>
                                  <td width="343">${iniDto.applDate}</td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                                  <td width="109" height="25">승 인 시 각</td>
                                  <td width="343">${iniDto.applTime}</td> 
                                </tr>                	    
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                    		  <td width="109" height="25">승 인 번 호</td>
                    		  <td width="343">${iniDto.applNum}
              </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                    		  <td width="109" height="25">할 부 기 간</td>  
                    		  <td width="343">${iniDto.cardQuota}개월&nbsp;<b>
                    		  <span color=red>
                    		   <c:choose>
									<c:when test="${iniDto.cardInterest eq '1' }">
										무이자
									</c:when>
									<c:when test="${iniDto.eventCode eq '1' }">
										무이자 (이니시스&카드사부담 일반 무이자 할부 이벤트)
									</c:when>
									<c:when test="${iniDto.eventCode eq '12' }">
										카드사부담 일반 무이자 + 상점 일반 할인 이벤트
									</c:when>
									<c:when test="${iniDto.eventCode eq '14' }">
										카드사부담 일반 무이자 + 카드번호별 할인 이벤트
									</c:when>
									<c:when test="${iniDto.eventCode eq '24' }">
										카드사부담 일반 무이자 + 카드 Prefix별 할인 이벤트
									</c:when>
									<c:when test="${iniDto.eventCode eq 'A1' }">
										상점부담 일반 무이자 할부 이벤트
									</c:when>
									<c:when test="${iniDto.eventCode eq 'A2' }">
										상점 일반 할인 이벤트
									</c:when>
									<c:when test="${iniDto.eventCode eq 'A3' }">
										상점 무이자 + 상점 일반 할인 이벤트
									</c:when>
									<c:when test="${iniDto.eventCode eq 'A4' }">
										상점 무이자 + 카드번호별 할인 이벤트
									</c:when>
									<c:when test="${iniDto.eventCode eq 'A5' }">
										카드번호별 할인 이벤트
									</c:when>
									<c:when test="${iniDto.eventCode eq 'B4' }">
										상점 무이자 + 카드 Prefix별 할인 이벤트
									</c:when>
									<c:when test="${iniDto.eventCode eq 'B5' }">
										카드 Prefix별 할인 이벤트
									</c:when>
									<c:when test="${iniDto.eventCode eq 'C0' }">
										당사&카드사부담 특별 무이자 할부 이벤트
									</c:when>
									<c:when test="${iniDto.eventCode eq 'C1' }">
										상점부담 특별 무이자 할부 이벤트
									</c:when>
									<c:otherwise>
										일반
									</c:otherwise>
                    		   </c:choose>
                    		</span></b></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                    		  <td width="109" height="25">카 드 종 류</td>
                    		  <td width="343">${iniDto.cardcode}  
              </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                    		  <td width="109" height="25">카드발급사</td>
                    		  <td width="343">${iniDto.cardBankcode}
              </td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3">&nbsp;</td>
                    		</tr>
                    		
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                    		</tr>
          </c:if>        
          
		<!-- 아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
		 *											 		*
		 *  2.  은행계좌결제 결과 출력	 -->
	   <c:if test="${iniDto.paymethod eq 'DirectBank' }">  		


          			<tr> 
                                  <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                                  <td width="109" height="25">승 인 날 짜</td>
                                  <td width="343">${iniDto.applDate}
               </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                                  <td width="109" height="25">승 인 시 각</td>
                                  <td width="343">${iniDto.applTime}
               </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                                  <td width="109" height="25">은 행 코 드</td>
                                  <td width="343">${iniDto.acctBankcode}
                </td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                                  <td width="109" height="25">현금영수증<br>발급결과코드</td>
                                  <td width="343">${iniDto.cshrResultcode}
                </td>
                                </tr>
                                <tr>  
                                  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                                </tr>
				<tr>
                                  <td width="18" align="center"><img src="${pageContext.request.contextPath}/resources/images/inicis/icon02.gif" width="7" height="7" alt=""></td>
                                  <td width="109" height="25">현금영수증<br>발급구분코드</td>
                                  <td width="343">${iniDto.cshrType}
                                    <span color=red><b>(0 - 소득공제용, 1 - 지출증빙용)</b></span></td>
                                </tr>
                                <tr>
                                  <td height="1" colspan="3" align="center"  background="${pageContext.request.contextPath}/resources/images/inicis/line.gif"></td>
                                </tr>
       </c:if>                          
          		
            <!-- 이용안내 끝 -->
            
      </table></td>
  </tr>
  <tr> 
    <td><img src="${pageContext.request.contextPath}/resources/images/inicis/bottom01.gif" width="632" height="13" alt=""></td>
  </tr>
</table>
</body>
</html>             

