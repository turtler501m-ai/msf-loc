<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="titleAttr">M마켓</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
        <script src="/resources/js/portal/point/mstoreView.js?version=25-11-13"></script>
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script>
			var reqRateCd = '<c:out value="${rateCd}"/>';
   			var swiper = new Swiper('.c-swiper', {
   				loop: true,
     			pagination: {
       			el: '.swiper-pagination',
       			type: 'fraction',
   				}
      			<c:if test="${!empty bannerInfo && fn:length(bannerInfo) > 1}">
   				,
		      	autoplay: {
		        delay: 3000,
		        disableOnInteraction: false,
		    	},
		      	on: {
		        	init: function() {
		          		var swiper = this;
		          		var autoPlayButton = this.el.querySelector('.swiper-play-button');
		          		if(typeof autoPlayButton != "undefined"){
			          		autoPlayButton.addEventListener('click', function() {
			            		if (autoPlayButton.classList.contains('play')) {
			              			autoPlayButton.classList.remove('play');
			              			autoPlayButton.classList.add('stop');
			              			swiper.autoplay.start();
			            		} else {
			              			autoPlayButton.classList.remove('stop');
			              			autoPlayButton.classList.add('play');
			              			swiper.autoplay.stop();
			            		}
			          		});
		          		}
		        	},
		      	},
		     	</c:if>
		    });
	  	</script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <input type="hidden" id="isMobile" value="${isMobile}">
        <input type="hidden" id="isAuth" name="isAuth">
        <input type="hidden" id="landingUrl" value="${landingUrl}">
        <!-- 본인인증 default -->
        <input type="hidden" id="defaultAuth" name="defaultAuth" value= "C">


        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    M마켓
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>

			<!--  배너시작 -->
			<c:if test="${!empty bannerInfo}">
				<div class="mbership-banner__swiper upper-banner__box">
					<div class="c-swiper swiper-container">
						<div class="swiper-wrapper">
							<c:forEach var="bannerInfo" items="${bannerInfo}">
								<c:set var="target" value="_self " />
								<c:if test="${bannerInfo.linkTarget eq 'Y' }">
									<c:set var="target" value=""/>
								</c:if>
								<c:choose>
									<c:when test="${bannerInfo.mobileLinkUrl eq null || bannerInfo.mobileLinkUrl eq ''}">
										<div class="swiper-slide">
											<div class="c-hidden">
												<p>
													<c:out value="${bannerInfo.bannDesc}" />
												</p>
											</div>
											<img src="<c:out value="${bannerInfo.mobileBannImgNm}"/>" onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'" alt="<c:out value="${bannerInfo.imgDesc}"/>">
										</div>
									</c:when>
									<c:otherwise>
										<div class="swiper-slide">
											<div class="c-hidden">
												<p>
													<c:out value="${bannerInfo.bannDesc}" />
												</p>
											</div>
											<a href="#" onclick="fn_go_banner('<c:out value="${bannerInfo.mobileLinkUrl}"/>','<c:out value="${bannerInfo.bannSeq}"/>','<c:out value="${bannerInfo.bannCtg}"/>','${target}')">
												<img src="${bannerInfo.mobileBannImgNm}" onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'" alt="<c:out value="${bannerInfo.imgDesc}"/>">
											</a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>

						<c:if test="${!empty bannerInfo && fn:length(bannerInfo) > 1}">
							<button class="swiper-play-button stop" type="button"></button>
							<div class="swiper-pagination"></div>
						</c:if>
					</div>
				</div>
			</c:if>
			<!--  배너끝 -->

            <c:choose>
                <c:when test="${loginYn eq 'N'}">
                    <!-- 비로그인 -->
                    <div class="mstore-head">
                        <p class="mstore-head__title">
                            <b><span>M마켓</span></b>은 kt M모바일을 이용하시는 고객님을 위한 멤버십 쇼핑몰입니다.
                        </p>
                        <p class="mstore-head__text">쇼핑몰 이용을 위해서는 로그인이 필요합니다.</p>
                        <div class="mbership-head__btn">
	                        <a href="/m/loginForm.do" title="로그인 페이지 바로가기">로그인하기</a>
	                    </div>
                    </div>
                </c:when>
                <c:when test="${resultCd eq '0003'}">
                    <!-- 본인인증 폼 -->
                    <div class="u-mt--m32">
                        <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                            <jsp:param name="controlYn" value="N"/>
                            <jsp:param name="reqAuth" value="CNAT"/>
                        </jsp:include>
                    </div>
                    <div class="mstore-button-wrap c-hidden">
                        <a class="mstore-button goMstore" title="M마켓 페이지 바로가기">M마켓 바로가기</a>
                    </div>
                </c:when>
                <c:when test="${resultCd ne '0000'}">
                    <!-- 에러메세지 -->
                    <div class="c-row c-row--lg">
                        <div class="u-ta-center">
                            <div class="mbership-head">
                                <p class='mbership-head__title'>${errorMsg}</p>
                            </div>
                            <c:if test="${errorCause eq 'GRADE'}">
                                <div>
                                    <a class="c-button c-button--full c-button--primary" href="/m/mypage/multiPhoneLine.do">정회원 인증 하러가기</a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="mstore-button-wrap">
                        <a class="mstore-button goMstore" title="M마켓 페이지 바로가기">M마켓 바로가기</a>
                    </div>
                </c:otherwise>
            </c:choose>

            <!-- 엠스토어 컨텐츠 -->
            <div class="mstore-content">
                <div class="mstore-list-wrap inq">
                	<div class="mstore-list__item">
                        <div class="mstore-list__img">
                        	<div class="mstore-store-wrap">
	                        	<img src="/resources/images/mobile/product/mstore_store_bg.png">
	                        	<div class="mstore-store">
									<div class="mstore-store__item">
										<a class="mstore-store__anchor" href="http://etbs.me/QAY-I" target="_blank" title="M마켓 이용을 위한 애플 앱스토어 페이지 새창 열림">
											<img src="/resources/images/mobile/product/mstore_store_01.png" alt="M마켓 이용을 위한 스토어 이미지">
										</a>
									</div>
									<div class="mstore-store__item">
										<a class="mstore-store__anchor" href="http://etbs.me/QAY-A" target="_blank" title="M마켓 이용을 위한 구글플레이 페이지 새창 열림">
											<img src="/resources/images/mobile/product/mstore_store_02.png" alt="M마켓 이용을 위한 스토어 이미지">
										</a>
									</div>
									<div class="mstore-store__item">
										<a class="mstore-store__anchor" href="http://etbs.me/QAY-O" target="_blank" title="M마켓 이용을 위한 원스토어 페이지 새창 열림">
											<img src="/resources/images/mobile/product/mstore_store_03.png" alt="M마켓 이용을 위한 스토어 이미지">
										</a>
									</div>
								</div>
							</div>
                        </div>
                        <div class="mstore-list__info-wrap">
                            <div class="mstore-list__info">
                                <h4>M마켓 전용 앱 다운로드</h4>
                                <ul>
                                    <li>
                                        상단의 이미지를 탭하여 전용 앱을 다운 받아보세요.
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="mstore-list__item">
                        <div class="mstore-list__img">
                            <img src="/resources/images/mobile/product/mstore_mo_01.png" alt="M마켓 소개 이미지">
                        </div>
                        <div class="mstore-list__info-wrap">
                            <div class="mstore-list__info">
                                <h4>다양한 특가 기획전</h4>
                                <ul>
                                    <li>
                                        오직 M모바일 고객만을 위한 다양한 특가 기획전을 M마켓에서 만나보세요.
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="mstore-list__item">
                        <div class="mstore-list__img">
                            <img src="/resources/images/mobile/product/mstore_mo_02.png" alt="M마켓 소개 이미지">
                        </div>
                        <div class="mstore-list__info-wrap">
                            <div class="mstore-list__info">
                                <h4>M모바일 고객님께만 드리는<br/>단독 혜택들</h4>
                                <ul>
                                    <li>
                                        친구초대, 사용후기 등을 통해 받으신 포인트를 M마켓에서 사용해 보세요.
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="c-notice-wrap">
                    <hr>
                    <h5 class="c-notice__title">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                        <span>알려드립니다</span>
                    </h5>
                    <ul class="c-notice__list">
                    	<li>M마켓은 kt M모바일을 이용하시는 고객님만 서비스 이용이 가능합니다.</li>
                        <c:set var="mstoreAge" value="${nmcp:getCodeNmDto('MSTOREAGE','AGE')}" />
                        <c:set var="mstoreGradeType" value="${nmcp:getCodeNmDto('MSTORELIMIT','ASSOCIATE')}" />
                        <c:if test="${mstoreGradeType.expnsnStrVal1 eq 'Y'}">
                            <li>M마켓 가입/이용은 만 ${mstoreAge.expnsnStrVal1}세 이상 다이렉트몰 회원만 가입/이용 하실 수 있습니다.</li>
                        </c:if>
                        <c:if test="${mstoreGradeType.expnsnStrVal1 ne 'Y'}">
                            <li>M마켓 가입/이용은 만 ${mstoreAge.expnsnStrVal1}세 이상 다이렉트몰 정회원만 가입/이용 하실 수 있습니다.</li>
                        </c:if>
                        <li>최초 1회 약관동의 및 M마켓 회원가입 후에는 별도 접속 및 로그인을 통한 M마켓 이용이 가능합니다.</li>
                        <li>kt M모바일 회선을 해지하거나 다이렉트몰 회원을 탈퇴하실 경우 M마켓 포인트는 소멸되오니 유의하시기 바랍니다.</li>
                        <li>기타 M마켓 관련 문의는 운영대행사인 e-제너두(주) 고객센터를 통해 가능합니다.</li>
                        <li class="bullet--fyr">고객센터 1899-0522 (평일 09:00 ~ 18:00 / 점심시간 12:00 ~ 13:00, 주말, 공휴일 휴무)</li>
                    </ul>
                </div>
            </div>
        </div>


        <c:if test="${loginYn ne 'N' && (resultCd eq '0000' || resultCd eq '0003')}">
            <!--Mstore 안내 동의 팝업이 필요한 경우만 생성 -->
            <div id="MstoreTermsAgree"></div>
        </c:if>

    </t:putAttribute>
</t:insertDefinition>


