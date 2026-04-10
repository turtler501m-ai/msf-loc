<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/resources/js/jqueryCommon.js"></script>


<style type="text/css">
    input {
        width: 300px;
        padding: 3px 1%;
    }

    li {
        padding-bottom:10px;
    }
</style>

<header>
<script type="text/javascript">
    $(function(){
        $('#appEventCd').change(function(){
            $('div.inDto').hide();
            $('div.inDto input').val("");
            $('div.inDto input').attr("disabled", true);
            $('#'+this.value+' input').removeAttr("disabled");
            $('#'+this.value).show();
        });

        $('#b_init').click(function(){
            $('#appEventCd').val("");
            $('div.inDto').hide();
            $('input').val("");
            $('#button').show();
            $('#reslutBox').val("");
            $('div.inDto input').attr("disabled", true);
        });

        $('#b_send').click(function(){
        	$('#button').hide();
        	$('#reslutBox').val("요청중입니다. 잠시만 기다려 주세요");

            $.ajax({
                type : "POST",
                url : "/mypage/mpSendTo.do",
                data : $('#frm').serialize(),
                dataType : "json",
                contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                success : function(data) {
                    $('#reslutBox').val(data.result);
                    $('#button').show();
                }
            });
        });

        $('#b_search').click(function(){

            $.ajax({
                type : "POST",
                url : "/mypage/searchUserInfo.do",
                data : $('#frm').serialize(),
                dataType : "json",
                contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                success : function(data) {
                    if (data.result == "OK") {
                    	$('#ctn').val(data.resultMap.ctn);
                    	$('#ncn').val(data.resultMap.ncn);
                    	$('#custId').val(data.resultMap.custId);
                    } else {
                    	alert(data.result);
                    }
                }
            });
        });
    });

    function paramTest() {
    	alert($("#paramValue").val());
    	  ajaxCommon.getItem({
              id:'paramTest'
              ,cache:false
              ,url:'/mypage/paramTest.do'
              ,data: $("#paramValue").val()
              ,dataType:"json"
          }
          ,function(jsonObj){

          });
    }

</script>
</header>
<body>
    <form id="frm" name="frm" method="post">
        <div>
            <caption><span><h1>M Platform TEST PAGE</h1></span></caption>
            <table border="0">
            	<tr>
            		<td>Request Type :</td>
            		<td colspan="2">
            			<select id="reqType" name="reqType" style="width: 300px">
			                <option value="">-- 선택 --</option>
			                <option value="selfcare">selfcare</option>
			                <option value="osst">개통간소화(osst)</option>
			            </select>
            		</td>
            	</tr>
            	<tr>
            		<td>Request Service :</td>
            		<td colspan="2">
            			<select id="appEventCd" name="appEventCd" style="width: 300px">
			                <option value="">-- 선택 --</option>
			                <option value="X01">1.가입정보조회</option>
			                <option value="X02">2.청구지주소변경</option>
			                <option value="X03">3.e-mail청구서조회</option>
			                <option value="X04">4.e-mail청구서변경</option>
			                <option value="X05">5.e-mail청구서보기</option>
			                <option value="X06">6.e-mail청구서보기상세</option>
			                <option value="X07">7.e-mail청구서발송결과조회</option>
			                <option value="X08">8.e-mail청구서재발송조회</option>
			                <option value="X09">9.e-mail청구서재발송처리</option>
			                <option value="X10">10.종이청구서조회</option>
			                <option value="X11">11.종이청구서재발행신청</option>
			                <option value="X12">12.총통화시간조회</option>
			                <option value="X13">13.통화내역조회</option>
			                <option value="X14">14.통화내역조회상세</option>
			                <option value="X15">15.요금조회</option>
			                <option value="X16">16.요금상세조회</option>
			                <option value="X17">17.요금항목별조회</option>
			                <option value="X18">18.실시간요금조회</option>
			                <option value="X19">19.요금상품변경</option>
			                <option value="X20">20.가입중인부가서비스조회</option>
			                <option value="X21">21.부가서비스신청</option>
			                <option value="X22">22.납부/미납요금조회</option>
			                <option value="X23">23.납부방법조회</option>
			                <option value="X24">24.납부이력조회</option>
			                <option value="X25">25.납부방법변경</option>
			                <option value="X26">26.일시정지이력조회</option>
			                <option value="X27">27.일시정지가능여부조회</option>
			                <option value="X28">28.일시정지해제가능여부조회</option>
			                <option value="X29">29.일시정지신청</option>
			                <option value="X30">30.일시정지해제신청</option>
			                <option value="X31">31.번호목록조회</option>
			                <option value="X32">32.번호변경</option>
			                <option value="X33">33.분실신고가능여부조회</option>
			                <option value="X34">34.분실신고신청</option>
			                <option value="X35">35.분실신고취소신청</option>
			                <option value="X36">36.청소년 요금제 사용량 조회</option>
			                <option value="X38">38.부가서비스해지</option>
			                <option value="X49">49.청구서조회</option>
			                <option value="X50">50.명세서유형변경</option>
			                <option value="X54">54.스폰서 조회</option>
			                <option value="X55">55.JUICE현행화전문 재전송요청</option>
			                <option value="X59">59.심플할인 사전체크</option>
			                <option value="X60">60.심플할인 가입</option>
			                <option value="X61">61.심플할인 해지</option>
			                <option value="X62">62.심플할인 정보조회</option>
			                <option value="CP0">지능망직권해지</option>
			                <option value="X69">69.데이터쉐어링 사전체크 및 가입 가능 대상 조회</option>
			                <option value="X70">70.데이터쉐어링 가입/해지 처리</option>
			                <option value="X71">71.데이터쉐어링 결합 중인 대상 조회</option>
			                <option value="X74">74.쿠폰 정보조회</option>
			                <option value="X75">75.쿠폰 사용</option>
			                <option value="X76">76.사용완료 쿠폰 목록 조회</option>
			                <option value="X80">80.OTP인증서비스</option>
			                <option value="X82">82.KAKAOPAY 가입 SMS 전송</option>
			                <option value="X83">83.회선 총사용기간 조회</option>
			                <option value="D01">D01.배달 가능 지역 조회</option>
			                <option value="D02">D02.배달주문 접수</option>
			                <option value="D03">D03.배달주문 변경/취소</option>
			                <option value="D04">D04.배달주문 조회</option>
			                <option value="X86">X86.함께쓰기조회</option>
			                <option value="X51">X51.청구서발송결과조회</option>
			                <option value="X52">X52.청구서재발송조회</option>
			                <option value="X53">X53.청구서재발송처리</option>
			            </select>
            		</td>
            	</tr>
            	<tr>
            		<td>Request User Id :</td>
            		<td>
            			<input type="text" id="usrId" name="usrId" style="width: 300px">
            		</td>
            		<td><button id="b_search" type="button">조회</button></td>
            	</tr>
            	<tr>
            		<td>Request User CTN :</td>
            		<td colspan="2">
            			<input type="text" id="ctn" name="ctn" style="width: 300px">
            		</td>
            	</tr>
            	<tr>
            		<td>Request User NCN :</td>
            		<td colspan="2">
            			<input type="text" id="ncn" name="ncn" style="width: 300px">
            		</td>
            	</tr>
            	<tr>
            		<td>Request User CustID :</td>
            		<td colspan="2">
            			<input type="text" id="custId" name="custId" style="width: 300px">
            		</td>
            	</tr>
            </table>

            <br>
            -- InDto --
            <ul type="disc" style="margin:0 10px 15px 30px;">
                <div class="inDto" id="X01" style="display: none;"></div>
                <div class="inDto" id="X02" style="display: none;">
                    <li>우편번호 : <input type="text" name="addrZip"></li>
                    <li>기본주소 : <input type="text" name="adrPrimaryLn"></li>
                    <li>상세주소 : <input type="text" name="adrSecondaryLn"></li>
                    <li>청구서 전화번호 숨김표시 : <input type="text" name="bilCtnDisplay"></li>
                </div>
                <div class="inDto" id="X03" style="display: none;"></div>
                <div class="inDto" id="X04" style="display: none;">
                    <li>기능구분코드 : <input type="text" name="status"></li>
                    <li>이메일주소 : <input type="text" name="email"></li>
                    <li>보안메일여부 : <input type="text" name="securMailYn"></li>
                    <li>이벤트수신동의여부 : <input type="text" name="ecRcvAgreYn"></li>
                    <li>청구서발송여부 : <input type="text" name="sendGubun"></li>
                </div>
                <div class="inDto" id="X05" style="display: none;"></div>
                <div class="inDto" id="X06" style="display: none;">
                    <li>청구서년월 : <input type="text" name="payYyMm"></li>
                </div>
                <div class="inDto" id="X07" style="display: none;"></div>
                <div class="inDto" id="X08" style="display: none;"></div>
                <div class="inDto" id="X09" style="display: none;">
                    <li>재발송년도 : <input type="text" name="year"></li>
                    <li>재발송월 : <input type="text" name="month"></li>
                    <li>보안메일여부 : <input type="text" name="securMailYn"></li>
                    <li>재발송사유코드 : <input type="text" name="causeCode"></li>
                    <li>발송이메일 : <input type="text" name="email"></li>
                </div>
                <div class="inDto" id="X10" style="display: none;"></div>
                <div class="inDto" id="X11" style="display: none;">
                    <li>수신주소1 : <input type="text" name="pAddr"></li>
                    <li>수신주소2 : <input type="text" name="sAddr"></li>
                    <li>청구소우편번호 : <input type="text" name="zipCode"></li>
                    <li>주소변경유형코드 : <input type="text" name=addrChgType></li>
                    <li>앞에6자리는청구시작월 뒤에는현재월 : <input type="text" name="billDate"></li>
                    <li>발송방법코드 : <input type="text" name="sendGubun"></li>
                    <li>재발행사유코드 : <input type="text" name="requestReason"></li>
                </div>
                <div class="inDto" id="X12" style="display: none;"></div>
                <div class="inDto" id="X13" style="display: none;">
                    <li>통화종류 : <input type="text" name="w2kind"></li>
                </div>
                <div class="inDto" id="X14" style="display: none;">
                    <li>통화종류 : <input type="text" name="w2kind"></li>
                    <li>분류코드 : <input type="text" name="svcGroup"></li>
                    <li>조회시작일 : <input type="text" name="startDate"></li>
                    <li>조회종료일 : <input type="text" name=endDate></li>
                    <li>조회페이지번호 : <input type="text" name="dstPageNo"></li>
                </div>
                <div class="inDto" id="X15" style="display: none;">
                    <li>번호별조회청구년월 : <input type="text" name="productionDate"></li>
                </div>
                <div class="inDto" id="X16" style="display: none;">
                    <li>청구일련번호 : <input type="text" name="billSeqNo"></li>
                    <li>전체청구년월 : <input type="text" name="billDueDateList"></li>
                    <li>청구년월 : <input type="text" name="billMonth"></li>
                    <li>사용시작일 : <input type="text" name=billStartDate></li>
                    <li>사용종료일 : <input type="text" name="billEndDate"></li>
                </div>
                <div class="inDto" id="X17" style="display: none;">
                    <li>청구번호 : <input type="text" name="billSeqNo"></li>
                    <li>청구월 : <input type="text" name="billMonth"></li>
                    <li>청구항목코드 : <input type="text" name="messageLine"></li>
                </div>
                <div class="inDto" id="X18" style="display: none;"></div>
                <div class="inDto" id="X19" style="display: none;">
                    <li>SOC코드 : <input type="text" name="soc"></li>
                </div>
                <div class="inDto" id="X20" style="display: none;"></div>
                <div class="inDto" id="X21" style="display: none;">
                    <li>SOC코드 : <input type="text" name="soc"></li>
                    <li>부가정보가있는경우 부가정보(option) : <input type="text" name="ftrNewParam" value=""></li>
                </div>
                <div class="inDto" id="X22" style="display: none;"></div>
                <div class="inDto" id="X23" style="display: none;"></div>
                <div class="inDto" id="X24" style="display: none;"></div>
                <div class="inDto" id="X25" style="display: none;">
                    <li>다음납부방법(공통) : <input type="text" name="nextBlMethod"></li>
                    <li>자동이체납부일,납기일자코드(공통) : <input type="text" name="nextCycleDueDay"></li>
                    <li>다음납부방법은행코드(계좌이체시) : <input type="text" name="nextBlBankCode"></li>
                    <li>다음납부방법계좌번호(계좌이체시) : <input type="text" name="nextBankAcctNo"></li>
                    <li>카드유효기간년월(카드) : <input type="text" name="nextCardExpirDate"></li>
                    <li>다음납부방법신용커드번호(카드) : <input type="text" name="nextCreditCardNo"></li>
                    <li>다음납부방법신용카드종류(카드) : <input type="text" name="nextCardCode"></li>
                    <li>신용카드회차정보(카드) : <input type="text" name="blpymTmsIndCd"></li>
                    <li>우편번호(지로) : <input type="text" name="adrZip"></li>
                    <li>기본주소(지로) : <input type="text" name="adrPrimaryLn"></li>
                    <li>상세주소(지로) : <input type="text" name="adrSecondaryLn"></li>
                    <li>agreIndCd 동의자료코드 (계좌이체) : <input type="text" name="agreIndCd"></li>
                </div>
                <div class="inDto" id="X26" style="display: none;">
                    <li>조회기간 : <input type="text" name="termGubun"></li>
                </div>
                <div class="inDto" id="X27" style="display: none;">
                    <li>일시정지사유코드 : <input type="text" name="stopRsnCd"></li>
                </div>
                <div class="inDto" id="X28" style="display: none;"></div>
                <div class="inDto" id="X29" style="display: none;">
                    <li>발착신상태코드 : <input type="text" name="reasonCode"></li>
                    <li>사용자메모 : <input type="text" name="userMemo"></li>
                    <li>일시정지기간여부 : <input type="text" name="cpDateYn"></li>
                    <li>일시정지만료일자 : <input type="text" name="cpEndDt"></li>
                    <li>일시정지시작일자 : <input type="text" name="cpStartDt"></li>
                    <li>일시정지비밀번호 : <input type="text" name="cpPwdInsert"></li>
                </div>
                <div class="inDto" id="X30" style="display: none;">
                    <li>비밀번호타입 : <input type="text" name="pwdType"></li>
                    <li>일시정지비밀번호 : <input type="text" name="strPwdNumInsert"></li>
                </div>
                <div class="inDto" id="X31" style="display: none;">
                    <li>번호채번조회할 마지막4자리번호 : <input type="text" name="chkCtn"></li>
                </div>
                <div class="inDto" id="X32" style="display: none;">
                    <li>채번한번호 : <input type="text" name="resvHkCtn"></li>
                    <li>채번한번호암호화 : <input type="text" name="resvHkSCtn"></li>
                    <li>채번한번호KT/KTF구분 : <input type="text" name="resvHkMarketGubun"></li>
                </div>
                <div class="inDto" id="X33" style="display: none;"></div>
                <div class="inDto" id="X34" style="display: none;">
                    <li>분실유형코드 : <input type="text" name="loseType"></li>
                    <li>고객요청사항 : <input type="text" name="guideYn"></li>
                    <li>연락받을번호 : <input type="text" name="cntcTlphNo"></li>
                    <li>분실내용 : <input type="text" name="loseCont"></li>
                    <li>분실지역 : <input type="text" name="loseLocation"></li>
                    <li>일시정지패스워드 : <input type="text" name="strPwdInsert"></li>
                </div>
                <div class="inDto" id="X35" style="display: none;">
                    <li>일시정지패스워드 : <input type="text" name="strPwdNumInsert"></li>
                    <li>패스워드유형 : <input type="text" name="pwdType"></li>
                </div>
                <div class="inDto" id="X36" style="display: none;"></div>
                <div class="inDto" id="X38" style="display: none;">
                    <li>SOC코드 : <input type="text" name="soc"></li>
                </div>
                <div class="inDto" id="X50" style="display: none;">
                    <li>청구서 타입 (1 이메일, 2 MMS ): <input type="text" name="billTypeCd"></li>
                    <li>청구서구분 (1:신청, 9:변경, 0:해지): <input type="text" name="status"></li>
                    <li>발송정보 (청구서발송유형이 1일 경우 이메일 주소, 2일 경우 전화번호): <input type="text" name="billAdInfo"></li>
                    <li>보안메일여부 Y/N : <input type="text" name="securMailYn"></li>
                    <li>이벤트수신동의여부 Y/N : <input type="text" name="ecRcvAgreYn"></li>
                    <li>명세서 발송여부 1:발송, 2:발송제외, 3:해지 시 :<input type="text" name="sendGubun"></li>
                </div>
                <div class="inDto" id="X55" style="display: none;">
                    <li>전문SEQ : <input type="text" name="mvnoseq"></li>
                    <li>판매회사코드 : <input type="text" name="slsCmpnCd"></li>
                </div>
                <div class="inDto" id="X54" style="display: none;">
                </div>
                <div class="inDto" id="X60" style="display: none;">
                    <li>약정개월수 : <input type="text" name="engtPerd"></li>
                </div>
                <div class="inDto" id="CP0" style="display: none;"></div>
                <div class="inDto" id="X70" style="display: none;">
                    <li >데이터 쉐어링 대상 전화번호 : <input type="text" name="opmdSvcNo"></li>

                    <li >처리 구분코드
                        <select id="opmdWorkDivCd" name=opmdWorkDivCd style="width: 300px">
                            <option value="A">결함</option>
                            <option value="C">해지</option>
                        </select>
                     </li>
                </div>
                <div class="inDto" id="X74" style="display: none;">

                    <li >처리 구분코드
                        <select id="searchTypeCd" name=searchTypeCd style="width: 300px">
                            <option value="01">쿠폰번호 조회</option>
                            <option value="02">고객에게 배포된 쿠폰번호 조회</option>
                        </select>
                     </li>
                     <li >쿠폰 일련 번호 : <input type="text" name="coupSerialNo"></li>
                     <li >문자 수신 고객의 전화번호 : <input type="text" name="coupSerialNo"></li>
                     <li >쿠폰 상태 코드
                        <select id="coupStatCd" name=coupStatCd style="width: 300px">
                            <option value="=">-</option>
                            <option value="SSRD">생성승인대기</option>
							<option value="SSCO">생성완료</option>
							<option value="SSCA">생성취소</option>
							<option value="BHRD">발행승인대기</option>
							<option value="BHCO">발행완료</option>
							<option value="BHCA">발행취소</option>
							<option value="BPRD">배포대기</option>
							<option value="BPCO">배포완료</option>
							<option value="BPCA">배포취소</option>
							<option value="DRCO">등록완료</option>
							<option value="SYCO">사용완료</option>
							<option value="UNON">미사용</option>
							<option value="UCAN">사용취소</option>
							<option value="UEXP">사용만료</option>
							<option value="UREJ">승인반려</option>
							<option value="SSST">생성정지</option>
							<option value="URCA">승인요청취소</option>
							<option value="SYRV">사용예약</option>
							<option value="CPST">쿠폰폐기</option>
                        </select>
                     </li>
                     <li >서비스유형코드
                        <select id="svcTypeCd" name=svcTypeCd style="width: 300px">
                            <option value="ND"> Not Defined (모든 서비스유형)</option>
							<option value="MB">Mobile</option>
							<option value="IT">Internet</option>
							<option value="WB">Wibro</option>
							<option value="TV">IPTV</option>
							<option value="SO">VOIP</option>
							<option value="PS">PSTN</option>
							<option value="OLS">올레샵</option>
							<option value="OLM">올레마켓</option>
							<option value="OMS">OMSS</option>
							<option value="NT">NonTelco</option>
							<option value="BD">번들</option>
							<option value="TM">TV금액권</option>
							<option value="KTK">K-TALK(기간제) 쿠폰</option>
							<option value="OTM">OTM 컨텐츠 이용권</option>
							<option value="OTV">OTV 월정액 이용권</option>
							<option value="MM">OTM금액권</option>
                        </select>
                     </li>
                     <li >쿠폰유형코드
                        <select id="coupTypeCd" name=coupTypeCd style="width: 300px">
                            <option value="01">요금할인쿠폰</option>
							<option value="02">서비스이용쿠폰</option>
							<option value="05">컨텐츠/상품쿠폰</option>
							<option value="06">마켓쿠폰</option>
							<option value="08">올레마켓 포인트 쿠폰</option>
							<option value="09">TV가입 적립쿠폰</option>
							<option value="10">TV배포 적립쿠폰</option>
                        </select>
                     </li>
                     <li >페이지번호 : <input type="text" id="pageNo" name="pageNo" value="1"></li>
                </div>
                <div class="inDto" id="X75" style="display: none;">

                    <li >사용유형코드
                        <select id="useTypeCd" name=useTypeCd style="width: 300px">
	                        <option value="CRU">등록사용(등록동시사용)</option>
							<option value="URA">사용예약</option>
							<option value="UCR">사용예약취소</option>
						</select>
                    </li>
                    <li >쿠폰 일련 번호 : <input type="text" name="coupSerialNo"></li>
                    <li >서비스유형코드
                        <select id="svcTypeCd" name=svcTypeCd style="width: 300px">
                            <option value="ND"> Not Defined (모든 서비스유형)</option>
                            <option value="MB">Mobile</option>
                            <option value="IT">Internet</option>
                            <option value="WB">Wibro</option>
                            <option value="TV">IPTV</option>
                            <option value="SO">VOIP</option>
                            <option value="PS">PSTN</option>
                            <option value="OLS">올레샵</option>
                            <option value="OLM">올레마켓</option>
                            <option value="OMS">OMSS</option>
                            <option value="NT">NonTelco</option>
                            <option value="BD">번들</option>
                            <option value="TM">TV금액권</option>
                            <option value="KTK">K-TALK(기간제) 쿠폰</option>
                            <option value="OTM">OTM 컨텐츠 이용권</option>
                            <option value="OTV">OTV 월정액 이용권</option>
                            <option value="MM">OTM금액권</option>
                        </select>
                     </li>
                     <li >사용예약일자 : <input type="text" id="useRsvDt" name="useRsvDt" value=""></li>
                     <li >사용예약취소 사유 : <input type="text" id="useCanReason" name="useCanReason" value=""></li>
                </div>
                <div class="inDto" id="X76" style="display: none;">

                    <li >쿠폰 일련 번호 : <input type="text" name="coupSerialNo"></li>
                    <li >서비스유형코드
                        <select id="svcTypeCd" name=svcTypeCd style="width: 300px">
                            <option value="ND"> Not Defined (모든 서비스유형)</option>
                            <option value="MB">Mobile</option>
                            <option value="IT">Internet</option>
                            <option value="WB">Wibro</option>
                            <option value="TV">IPTV</option>
                            <option value="SO">VOIP</option>
                            <option value="PS">PSTN</option>
                            <option value="OLS">올레샵</option>
                            <option value="OLM">올레마켓</option>
                            <option value="OMS">OMSS</option>
                            <option value="NT">NonTelco</option>
                            <option value="BD">번들</option>
                            <option value="TM">TV금액권</option>
                            <option value="KTK">K-TALK(기간제) 쿠폰</option>
                            <option value="OTM">OTM 컨텐츠 이용권</option>
                            <option value="OTV">OTV 월정액 이용권</option>
                            <option value="MM">OTM금액권</option>
                        </select>
                     </li>
                     <li >사용시작일자 : <input type="text" id="useDateFrom" name="useDateFrom" value=""></li>
                     <li >사용끝일자 : <input type="text" id="useDateTo" name="useDateTo" value=""></li>
                     <li >페이지번호 : <input type="text" id="pageNo" name="pageNo" value="1"></li>
                </div>
                <div class="inDto" id="X80" style="display: none;">

                    <li >OTP 수신 전화번호 : <input type="text" id="svcNo" name="svcNo" value=""></li>
                    <li >업무구분코드 값 : <input type="text" id="chkInd" name="chkInd" value=""></li>
                    <li >체크 data1 : <input type="text" id="dataVal1" name="dataVal1" value=""></li>
                </div>

                <div class="inDto" id="D01" style="display: none;">
                    <li >우편번호 : <input type="text" id="zipNo" name="zipNo" value=""></li>
                    <li >기본주소 : <input type="text" id="targetAddr1" name="targetAddr1" value=""></li>
                    <li >상세주소 : <input type="text" id="targetAddr2" name="targetAddr2" value=""></li>
                    <li >주소 유형 : <input type="text" id="addrTypeCd" name="addrTypeCd" value=""></li>
                    <li >배달 업체 코드 : <input type="text" id="bizOrgCd" name="bizOrgCd" value=""></li>
                    <li >위도 : <input type="text" id="targetAddrLat" name="targetAddrLat" value=""></li>
                    <li >경도 : <input type="text" id="targetAddrLng" name="targetAddrLng" value=""></li>
                </div>

                <div class="inDto" id="D02" style="display: none;">
                    <li >수령고객연락처 : <input type="text" id="orderRcvTlphNo" name="orderRcvTlphNo" value=""></li>
                    <li >우편번호 : <input type="text" id="zipNo" name="zipNo" value=""></li>
                    <li >주소 유형 : <input type="text" id="addrTypeCd" name="addrTypeCd" value=""></li>
                    <li >기본주소 : <input type="text" id="targetAddr1" name="targetAddr1" value=""></li>
                    <li >상세주소 : <input type="text" id="targetAddr2" name="targetAddr2" value=""></li>
                    <li >개인정보제공동의 여부: <input type="text" id="custInfoAgreeYn" name="custInfoAgreeYn" value=""></li>
                    <li >배달예약여부 : <input type="text" id="rsvOrderYn" name="rsvOrderYn" value=""></li>
                    <li >배달희망시간 : <input type="text" id="rsvOrderDt" name="rsvOrderDt" value=""></li>
                    <li >배달요청메세지 : <input type="text" id="orderReqMsg" name="orderReqMsg" value=""></li>
                    <li >접수가능시간 : <input type="text" id="acceptTime" name="acceptTime" value=""></li>
                    <li >유심금액 : <input type="text" id="usimAmt" name="usimAmt" value=""></li>
                    <li >배달 업체 코드 : <input type="text" id="bizOrgCd" name="bizOrgCd" value=""></li>
                    <li >위도 : <input type="text" id="targetAddrLat" name="targetAddrLat" value=""></li>
                    <li >경도 : <input type="text" id="targetAddrLng" name="targetAddrLng" value=""></li>
                </div>

                <div class="inDto" id="D03" style="display: none;">
                    <li >작업구분 : <input type="text" id="jobGubun" name="jobGubun" value=""></li>
                    <li >KT 오더 ID : <input type="text" id="ktOrderId" name="ktOrderId" value=""></li>
                    <li >수령고객연락처 : <input type="text" id="orderRcvTlphNo" name="orderRcvTlphNo" value=""></li>
                    <li >우편번호 : <input type="text" id="zipNo" name="zipNo" value=""></li>
                    <li >주소 유형 : <input type="text" id="addrTypeCd" name="addrTypeCd" value=""></li>
                    <li >기본주소 : <input type="text" id="targetAddr1" name="targetAddr1" value=""></li>
                    <li >상세주소 : <input type="text" id="targetAddr2" name="targetAddr2" value=""></li>
                    <li >개인정보제공동의 여부 : <input type="text" id="custInfoAgreeYn" name="custInfoAgreeYn" value=""></li>
                    <li >배달예약여부 : <input type="text" id="rsvOrderYn" name="rsvOrderYn" value=""></li>
                    <li >배달희망시간 : <input type="text" id="rsvOrderDt" name="rsvOrderDt" value=""></li>
                    <li >배달요청메세지 : <input type="text" id="orderReqMsg" name="orderReqMsg" value=""></li>
                    <li >접수가능시간 : <input type="text" id="acceptTime" name="acceptTime" value=""></li>
                    <li >유심금액 : <input type="text" id="usimAmt" name="usimAmt" value=""></li>
                </div>

                <div class="inDto" id="D04" style="display: none;">
                    <li >KT 오더 ID : <input type="text" id="ktOrderId" name="ktOrderId" value=""></li>
                </div>

                <div class="inDto" id="X86" style="display: none;">
                    <li >주기회선(G),받기회선(R) : <input type="text" id="retvGubunCd" name="retvGubunCd" value=""></li>
                </div>

                <div class="inDto" id="X51" style="display: none;"></div>
                <div class="inDto" id="X52" style="display: none;"></div>
                <div class="inDto" id="X53" style="display: none;">
                	<li >year : <input type="text" id="year" name="year" value=""></li>
                	<li >month : <input type="text" id="month" name="month" value=""></li>
                	<li >billTypeCd : <input type="text" id="billTypeCd" name="billTypeCd" value=""></li>
                	<li >securMailYn : <input type="text" id="securMailYn" name="securMailYn" value=""></li>
                	<li >causeCode : <input type="text" id="causeCode" name="causeCode" value=""></li>
                	<li >billAdInfo : <input type="text" id="billAdInfo" name="billAdInfo" value=""></li>
                </div>


            </ul>

            <table>
                <tr>
                    <td><div id="button"><button id="b_send" type="button">전송</button></div></td>
                    <td> | </td>
                    <td><button id="b_init" type="button">초기화</button></td>
                </tr>
            </table>


            <br>
            -- Result Message --
            <br>
            <textarea id="reslutBox" rows="25" cols="150"></textarea>
        </div>
    </form>

 	<br>파라미터 테스트<br>
 	<input type="text" id="paramValue" value="operType=MNP3&requestKey=0&onlineAuthType=C&serviceType=PO&birthDate=810228&onOffType=0&cntpntShopId=1100011741&reqModelName=SM-G925KW&prodId=1203&bannerCd=&sntyColorCd=03&sntyCapacCd=01&pstate=00&cstmrName=주태강&cstmrNativeRrn=8102281051511&desCstmrNativeRrn=8102281051511&cstmrType=NA&cstmrTelFn=02&cstmrTelMn=1111&cstmrTelRn=2222&cstmrMobileFn=010&cstmrMobileMn=3333&cstmrMobileRn=4444&cstmrPost=06088&cstmrAddr=서울특별시 강남구 학동로 402&cstmrAddrDtl=(삼성동) 33&cstmrBillSendCode=CB&cstmrMail=test@nate.com&cstmrMailReceiveFlag=Y&cstmrForeignerNation=&cstmrForeignerPn=&cstmrForeignerRrn=&cstmrForeignerSdate=&cstmrForeignerEdate=&cstmrJejuId=&recommendFlagCd=&recommendInfo=&dlvryName=주태강&dlvryTelFn=02&dlvryTelMn=1111&dlvryTelRn=2222&dlvryMobileFn=010&dlvryMobileMn=3333&dlvryMobileRn=4444&dlvryPost=06088&dlvryAddr=서울특별시 강남구 학동로 402&dlvryAddrDtl=(삼성동) 33&dlvryMemo=개발자 테스트&moveCompany=INL&moveMobileFn=010&moveMobileMn=1111&moveMobileRn=2222&moveAuthType=2&moveAuthNumber=1234&moveThismonthPayType=NM&moveAllotmentStat=AD&moveRefundAgreeFlag=Y&minorAgentName=&minorAgentRrn=&minorAgentTelFn=&minorAgentTelMn=&minorAgentTelRn=&minorAgentRelation=&reqBuyType=MM&reqWantNumber=&reqWantNumber2=&reqWantNumber3=&reqWireType=AN&reqGuideFlag=&reqGuideFn=&reqGuideRn=&reqGuideMn=&reqPayType=C&reqBank=&reqAccountName=&reqAccountRrn=&reqAccountRelation=&reqAccountNumber=&reqCardRrn=8102281051511&reqCardCompany=삼성카드&reqCardNo=5361489001011656&reqCardYy=2022&reqCardMm=01&sprtTp=KD&maxDiscount3=37200&dcAmt=0&addDcAmt=0&modelId=1184&modelMonthly=24&enggMnthCnt=24&modelInstallment=593700&modelSalePolicyCode=D2017122804554&modelPriceVat=79900&modelDiscount2=248000&modelDiscount3=37200&modelPrice=799000&socCode=KSLTEMSTD&realMdlInstamt=878900&recycleYn=N&clausePriCollectFlag=Y&clausePriOfferFlag=Y&clauseEssCollectFlag=Y&clausePriTrustFlag=Y&clausePriAdFlag=Y&clauseConfidenceFlag=Y&clauseJehuFlag=Y&clauseFinanceFlag=Y&nwBlckAgrmYn=&appBlckAgrmYn=&usimPrice=0&usimPayMthdCd=3&reqUsimSn=&reqAcType=&reqAc01Balance=0&reqAc01Amount=0&reqAc02Day=&reqAc02Amount=0&reqAddition=스팸차단서비스,발신번호표시,통합사서함,정보제공사업자번호차단&reqAdditionPrice=0&additionKeyList=12,29,30,31&clauseCodeList=01,02,03,04,05,S01,S02,SfinanceFlag&clauseDocverList=3,1,1,1,1,3,1,1&goodname=&buyername=&buyeremail=&buyertel=&currency=&oid=&sessionkey=&cardcode=&encrypted=&paymethod=&uid=&rn=&price=&enctype=&pReqUrl=&pStatus=&pRmesg1=&pTid=&pNoti=&settlWayCd=&settlAmt=0&ownPersonalCode=&prdtSctnCd=LTE&rprsPrdtId=1165&selfCertType=04&selfInqryAgrmYnFlag=&selfIssuExprDt=20180111&selfIssuNum=22&minorSelfCertType=&minorSelfInqryAgrmYnFlag=&minorSelfIssuExprDt=&minorSelfIssuNum=&atribValCd=&openMarketReferer=&agentCode=&joinPrice=0&clauseRentalService=N&clauseRentalModelCp=N&clauseRentalModelCpPr=N&clauseMpps35Flag=&ohvalue=&selfInqryAgrmYn=Y&reqCardName=주태강&rand=455572100000"/>
 	<a href="javascript:paramTest()">전송</a>
</body>
