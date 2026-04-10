<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/resources/js/jqueryCommon.js"></script>
<script type="text/javascript" src="/resources/js/nmcpCommon.js"></script>
<script type="text/javascript" src="/resources/js/portal/ktm.ui.js"></script>
<script type="text/javascript" src="/resources/js/portal/nmcpCommonComponent.js"></script>


<script>
	let MCP= new NmcpCommonComponent();
</script>

<script type="text/javascript" src="/resources/js/slick.min.js"></script>
<script type="text/javascript" src="/resources/js/portal/popup/passLogin.js"></script>

<link rel="stylesheet" type="text/css" href="/resources/css/portal/styles.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/front/niceAuth/common.css">
<link rel="stylesheet" type="text/css" href="/resources/css/front/niceAuth/slick.css">

<head>
	<title>PASS 인증서</title>
</head>

<body style="margin:0;padding:0;border:0;">

	<input type="hidden" name="passAlram" value="" />
	<input type="hidden" name="autoSlickAction" value="" />

	<div class="ly-wrap"></div>

	<div class="wrap" id="passLoginWrap">
		<!-- content -->
		<div id="content">
			<div class="container">
				<div class="page_title">
					<div class="thum"><img src="/resources/images/front/niceAuth/ci_ktMmobile_70_70.png" alt="로고"></div>
					<div class="inner">
						<h2>간편인증</h2>
						<p>이름/휴대폰번호를 입력하세요.</p>
					</div>
				</div>
				<div class="login_section">
					<div class="inner">
						<!-- <h2 class="tit">이름/휴대폰번호를 입력하세요.</h2> -->
						<p class="txt">입력 후, <span>PASS앱</span>을 통해 로그인합니다.</p>

						<div class="input_row focus"><!-- 오류시 error 추가 -->
							<div class="col"><span><input type="text" id="name" class="form_control" placeholder="이름" autofocus autocomplete="off"></span></div>
							<p class="error_txt" id="error_name">이름을 바르게 입력해주세요.</p>
						</div>

						<div class="input_row"><!-- 오류시 error 추가 -->
							<div class="col">
								<span>
									<select name="" id="phone_prefix">
										<option value="010">010</option>
										<option value="011">011</option>
										<option value="016">016</option>
										<option value="017">017</option>
										<option value="018">018</option>
										<option value="019">019</option>
									</select>
								</span>
								<span>
									<input type="tel" id="phone_num" class="form_control onlyNum"  maxlength="8" placeholder="휴대폰 번호" autocomplete="off">
								</span>
							</div>
							<p class="error_txt"><span>휴대폰번호를 정확히 입력해주세요.</span></p>
						</div>

						<div class="agree">
							<div class="is_chkbox">
								<label for="chk1">
									<input type="checkbox" id="chk1">
									<span class="checkmark"></span>
									개인정보 제 3자 제공 동의(필수)
								</label>
								<a href="#" id="showJoinAgree">내용보기</a>
							</div>
						</div>
						<div class="agree">
							<div class="is_chkbox">
								<label for="chk2">
									<input type="checkbox" id="chk2">
									<span class="checkmark"></span>
									 개인정보 수집 이용 동의(필수)
								</label>
								<a href="#" id="showUseAgree">내용보기</a>
							</div>
						</div>

						<div class="auth_faq">
							<div class="item">
								<div class="q"><p><strong>인증서</strong> 사용에 어려움이 있으신가요?</p> <button type="button"><span>열고닫기</span></button></div>
								<div class="a">
									<div class="subtit"><span class="num">①</span> PASS 앱 설치 혹은 인증서 발급을 하셨나요?</div>
									<p>PASS인증서 이용을 위해선 PASS 앱 설치 및 PASS 인증서 발급 후 이용하실 수 있습니다.</p>

									<div class="subtit"><span class="num">②</span> 인증 요청을 했는데 오류가 발생한다고요?</div>
									<p>PASS 인증서 <a href="#" id="faq">자주묻는질문</a> 을 참고해주세요.</p>
								</div>
							</div>
						</div>

						<div class="pass_install"><span id="passInstallBtn">PASS 설치하러 가기</span></div>

						<div class="btnG">
							<div><button type="button" class="ok" id="passNext"><span>다음</span></button></div>
						</div>
					</div>
				</div><!-- // login_section -->
			</div>
		</div>
		<!-- // content -->
	</div>

	<!-- join_agree -->
	<div class="wrap" id="passAgreeWrap" style="display:none;">
		<!-- HEADER -->
		<div class="header"></div>
		<!-- //HEADER -->
		<!-- CONTENT -->
		<div id="content">
			<div class="container">
				<div class="sub_title">
					<div class="inner">
						<h3>개인정보 제3자 제공 동의</h3>
					</div>
				</div>

				<div class="inner">

					<div class="private_wrap">
						<div class="private">
						    주식회사 케이티엠모바일(이하 “회사”라 한다)은 PASS 인증서의 원활한 진행을 위해 다음과 같은 정보를 주식회사 아톤, SK텔레콤 주식회사, 주식회사 케이티, 주식회사 LG유플러스, 나이스평가정보주식회사에 제공합니다. 취득한 개인정보는 “통신비밀보호법”, “전자통신사업법”, "전자서명법" 및 “개인정보 보호법” 등 정보통신서비스 제공자가 준수하여야 할 관련 법령 상의 개인정보 보호 규정을 준수합니다.<br /><br />

							- 제공받는자 : (주)아톤, SK텔레콤(주), (주)KT, (주)LG유플러스, 나이스평가정보(주)<br /><br />

							- 제공 목적 : 본인 확인을 통한 부정이용 방지(인증서 발급/재발급/삭제, 서비스 제공) 및 민원확인용<br /><br />

							- 제공하는 개인정보 : 전화번호, 이름<br /><br />

							- 보유기간 : 본 서비스 해지 시 까지 또는 관계 법령에 따른 보관의무 기간 동안 보관<br /><br />

							“서비스” 제공을 위해 필요한 최소한의 개인정보이므로 동의를 해주셔야 “서비스” 이용이 가능합니다.
						</div>

						<button type="button" class="btn_close close_agree"><img src="/resources/images/front/niceAuth/btn_close.png" alt="닫기" /></button>
					</div>

					<div class="btnG">
						<div><button type="button" class="ok close_agree"><span>닫기</span></button></div>
					</div>

				</div>
			</div>
		</div>
		<!-- //CONTENT -->
	</div>
	<!-- // join_agree -->

	<!-- use_agree -->
	<div class="wrap" id="passAgree2Wrap" style="display:none;">
		<!-- HEADER -->
		<div class="header"></div>
		<!-- //HEADER -->
		<!-- CONTENT -->
		<div id="content">
			<div class="container">
				<div class="sub_title">
					<div class="inner">
						<h3>개인정보 수집 이용 동의</h3>
					</div>
				</div>

				<div class="inner">

					<div class="private_wrap">
						<div class="private">

						    이용자는 주식회사 나이스평가정보(이하 “NICE”) 및 주식회사 아톤(이하 “아톤”)의 PASS인증서 서비스를 제공받기 위하여 다음의 내용을 확인하고 “NICE”와 “아톤”이 본인의 개인정보를 다음과 같이 수집∙이용하는 것에 동의합니다.<br /><br />

							“NICE”와 “아톤”은 “서비스”와 관련하여 인증서 발급/재발급/삭제 또는 서비스 이용 시 이용자로부터 아래와 같은 개인정보를 수집하고 있습니다<br /><br />

							(1) 수집항목<br />

							- 이동전화번호, 성명, 생년월일, 성별, 내외국인, 통신사정보, 본인확인값(CI, TI)<br />
							- 단말기 정보(Android IMEI, iOS UUID, OS정보 등)<br />
						    - 이용기관/대행사, 인증요청내용(제목/내용), 이동전화번호,성명, 생년월일,성별, 인증요청일시/인증일시 등<br />
						    - 로그기록, 접속지 정보 등<br /><br />

						    (2) 수집 및 이용 목적<br />
							- “서비스” 이용을 위한 이용자 식별(본인 및 연령 확인 포함), 서비스 관리, 인증서 발급/삭제 및 검증관리, 회원관리, 상담관리 목적<br />
							- 부정 이용방지를 위한 확인 및 인증서 검증관리 목적<br />
							- “서비스” 이용 이력 관리, 상담관리 목적<br />
							- 서비스 이용 및 부정거래 기록 확인<br /><br />

							(3) 보유 및 이용기간<br />
							개인정보는 본 "서비스"를 제공하는 기간 동안 보유 및 이용되고, 이용자의 "서비스" 해지 시 해당 이용자의 개인정보가 열람 또는 이용될 수 없도록 파기 처리함.<br />
							단 관계 법령의 규정에 의하여 보존할 필요가 있는 경우 (3) 개인정보 보유 및 이용기관 조항에서와 같이 관계법령에서 정한 일정한 기간 동안 이용자 정보를 보관<br /><br />

							이용자의 개인정보는 원칙적으로 개인정보의 수집 및 이용목적이 달성되면 지체 없이 파기합니다.
							다만, “NICE”와 “아톤”은 관련법령의 규정에 의하여 개인정보를 보유할 필요가 있는 경우, 해당 법령에서 정한 바에 의하여 아래와 같이 개인정보를 보유할 수 있습니다.
							법령에 의하여 수집, 이용되는 이용자의 정보는 아래와 같은 수집, 이용목적으로 보관합니다. <br /><br />

							(1) 전자서명법<br />
							- 인증서 발급/삭제 및 인증업무 이용에 관한 기록 (인증서 발급/삭제 및 인증업무 이용에 관한 기록) : 5년<br /><br />

							(2) 통신비밀보호법<br />
							- 통신사실확인자료 제공 (로그기록, 접속지 정보 등) : 3개월<br />
							- 통신사실확인자료 제공 (그 외 통신사실 확인 자료) : 12개월<br /><br />

							(3) 전상거래 등에서의 소비자 보호에 관한 법률<br />
							- 표시, 광고에 관한 기록 (표시, 광고 기록) : 6개월<br />
							- 대금결제 및 재화 등의 공급에 관한 기록 (대금결제/재화 등의 공급 기록) : 5년<br />
							- 계약 또는 청약철회 등에 관한 기록 (소비자 식별정보 계약/청약철회 기록) : 5년<br />
							- 소비자 불만 또는 분쟁처리에 관한 기록 (소비자 식별정보 분쟁처리 기록) : 3년<br /><br />

							이용자는 동의를 거부할 권리가 있으나, 동의 거부 시 PASS인증서 서비스 이용이 불가능합니다.<br /><br />
						</div>

						<button type="button" class="btn_close close_agree"><img src="/resources/images/front/niceAuth/btn_close.png" alt="닫기" /></button>
					</div>

					<div class="btnG">
						<div><button type="button" class="ok close_agree"><span>닫기</span></button></div>
					</div>

				</div>
			</div>
		</div>
		<!-- //CONTENT -->
	</div>
	<!-- // use_agree -->


	<!-- pass_install -->
	<div class="wrap" id="passInstallWrap" style="display:none;">
		<!-- HEADER -->
		<div class="header"></div>
		<!-- //HEADER -->
		<!-- CONTENT -->
		<div id="content">
			<div class="container">
				<div class="sub_title">
					<div class="inner">
						<h3>PASS 설치하러 가기</h3>
					</div>
				</div>

				<div class="inner">
					<div class="private_wrap">
						<div class="private">
				          <table class="install_pass">
				          	<caption>PASS 설치 페이지</caption>
				            <colgroup>
				              <col style="width: 33.3%">
				              <col style="width: 33.3%">
				              <col>
				            </colgroup>
				            <thead>
				              <tr>
				                <th scope="col">OS</th>
				                <th scope="col">통신사</th>
				                <th scope="col">PASS 인증서</th>
				              </tr>
				            </thead>
				            <tbody>
				              <tr>
				                <td>ANDROID</td>
				                <td>KT</td>
				                <td>
				                  <a class="link_text" href="https://play.google.com/store/apps/details?id=com.kt.ktauth" title="KT PASS 바로가기(새창)" target="_blank">PASS 설치</a>
				                </td>
				              </tr>
				              <tr>
				                <td>IOS</td>
				                <td>KT</td>
				                <td>
				                  <a class="link_text" href="https://apps.apple.com/kr/app/pass-by-kt-%EA%B5%AC-kt-%EC%9D%B8%EC%A6%9D/id1134371550" title="KT PASS 바로가기(새창)" target="_blank">PASS 설치</a>
				                </td>
				              </tr>
				              <tr>
				                <td>ANDROID</td>
				                <td>SKT</td>
				                <td>
				                  <a class="link_text" href="https://play.google.com/store/apps/details?id=com.sktelecom.tauth" title="SKT PASS 바로가기(새창)" target="_blank">PASS 설치</a>
				                </td>
				              </tr>
				              <tr>
				                <td>IOS</td>
				                <td>SKT</td>
				                <td>
				                  <a class="link_text" href="https://apps.apple.com/kr/app/pass-by-sk-telecom-%EA%B5%AC-t%EC%9D%B8%EC%A6%9D/id1141258007" title="SKT PASS 바로가기(새창)" target="_blank">PASS 설치</a>
				                </td>
				              </tr>
				               <tr>
				                <td>ANDROID</td>
				                <td>LGU+</td>
				                <td>
				                  <a class="link_text" href="https://play.google.com/store/apps/details?id=com.lguplus.smartotp" title="LGU+ PASS 바로가기(새창)" target="_blank">PASS 설치</a>
				                </td>
				              </tr>
				              <tr>
				                <td>IOS</td>
				                <td>LGU+</td>
				                <td>
				                  <a class="link_text" href="https://apps.apple.com/kr/app/pass-by-u-u-%EC%9D%B8%EC%A6%9D/id1147394645" title="LGU+ PASS 바로가기(새창)" target="_blank">PASS 설치</a>
				                </td>
				              </tr>
				            </tbody>
				          </table>
				      </div>
				      <button type="button" class="btn_close close_agree"><img src="/resources/images/front/niceAuth/btn_close.png" alt="닫기" /></button>
				    </div>
				<div class="btnG">
					<div><button type="button" class="ok close_agree"><span>닫기</span></button></div>
				</div>
			  </div>
			</div>
		</div>
		<!-- //CONTENT -->
	</div>
	<!-- // pass_install  -->


	<!-- waiting -->
	<div class="wrap" id="passWaitingWrap" style="display:none;">
		<!-- content -->
		<div id="content">
			<div class="container">

				<div class="page_title">
					<div class="thum"><img src="/resources/images/front/niceAuth/ci_ktMmobile_70_70.png" alt="로고"></div>
					<div class="inner">
						<h2>간편인증</h2>
						<p>PASS 앱에서 인증을 완료해주세요.</p>
					</div>
				</div>

				<!-- <div class="page_title">
					<div class="inner">
						<h2><em class="blind">PASS 인증서</em></h2>
						<p>휴대폰 PASS앱에서 인증을 완료해주세요.</p>
					</div>
				</div> -->

				<div class="join_section">

					<div class="timeout">
						유효시간
						<span id="remain_time">10:00</span>
						<div class="loading_bar1">
							<span></span>
							<span></span>
							<span></span>
							<span></span>
							<span></span>
						</div>
					</div>

					<div class="inner">

						<div class="slide_wrap">
							<div class="step_slide">
								<div class="item">
									<ol class="join_step">
										<li><span>STEP1.</span>내 휴대폰번호로 발송된 PUSH 선택 <img src="/resources/images/front/niceAuth/img_step1.png" alt="PASS 본인인증 STEP1"></li>
										<li><span>STEP2.</span>PASS에 등록한 PIN또는 생체인증<img src="/resources/images/front/niceAuth/img_step2.png" alt="PASS 본인인증 STEP2"></li>
										<li><span>STEP3.</span>인증 완료 후, 아래 <strong>인증 완료</strong>버튼 클릭</li>
									</ol>
									<div class="join_step_btm">
										<p>PUSH가 오지 않은 경우,<br>스마트폰에서 PASS를 실행해주세요.</p>
									</div>
								</div>
								<div class="item">
									<div class="howto">
										<div class="tit"><strong>인증요청(알림)이 휴대폰으로 오지 않는다면?</strong></div>
										<div class="txt">
											<p>① [PASS 앱> 홈 화면 또는 인증서 메뉴] 에서 인증 요청 내용을 확인할 수 있습니다.</p>
											<p>② PASS 앱 설치 확인 및 알림 수신동의 되어 있는지 확인해 주세요.</p>
											<p>③ 문제가 계속된다면 [PASS인증서 고객센터 : 1800-4273] 로 연락해 주세요.</p>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="btnG">
							<div><button type="button" class="again" onclick="javascript:self.close();"><span>닫기</span></button></div>
							<div><button type="button" class="ok" id="passConfirm"><span>인증 완료</span></button></div>
						</div>
					</div>
				</div><!-- // join_section -->
			</div>
		</div>
		<!-- // content -->

		<!-- footer -->
		<div class="footer">
		</div>
		<!-- //footer -->
	</div>

	<span class="deem" style="display:none;"></span>

	<!-- LAYER POP -->
	<div id="passTimeoutlayer" class="layer" style="display:none;">
	    <div class="layer_body">
	        <p class="message timeout"><!-- timeout, error, transient -->
	            인증 시간을 초과하였습니다.<br>요청을 종료합니다.
	        </p>
	    </div>
	    <div class="layer_btnwrap">
	        <a href="#">확인</a>
	    </div>
	</div>

	<!-- // waiting -->

</body>
