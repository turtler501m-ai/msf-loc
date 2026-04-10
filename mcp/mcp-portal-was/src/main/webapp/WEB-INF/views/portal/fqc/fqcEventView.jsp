<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />
<t:insertDefinition name="layoutGnbDefault">
    <t:putAttribute name="metaTagAttr">
        <meta property="fb:app_id" content="1694564987490429">
        <meta property="og:type" content="website" />
        <meta property="og:title" content="M프리퀀시 이벤트 | kt M모바일 공식 다이렉트몰" id="meta_og_title" />
        <meta property="og:url" content="https://www.ktmmobile.com/fqc/fqcEventView.doo" id="meta_og_url" />
        <meta property="og:description" content="" id="meta_og_description" />
        <meta property="og:image" content="https://${header['host']}/ktMmobile_og.png" />
    </t:putAttribute>

    <t:putAttribute name="titleAttr">M프리퀀시 이벤트</t:putAttribute>


    <t:putAttribute name="scriptHeaderAttr">
        <script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.4/kakao.min.js"
                integrity="sha384-DKYJZ8NLiK8MN4/C5P2dtSmLQ4KwPaoqAfyA/DfmEc1VDxu4yyC7wy6K1Hs90nka" crossorigin="anonymous"></script>
        <script type="text/javascript" src="/resources/js/portal/fqc/fqcEventView.js?version=25-05-12"></script>
    </t:putAttribute>


    <t:putAttribute name="contentAttr">
        <c:set var="fqcPlcyMsnList" value="${nmcp:getCodeList(Constants.FQC_PLCY_MSN_CODE)}" />
        <c:set var="fqcEventSeq" value="${nmcp:getCodeNmDto('Constant','fqcEventSeq')}" />

        <input type="hidden" id="userName" name="userName" value="${userName}"/>
        <input type="hidden" id="eventImg" name="eventImg" value="${fqcEventSeq.dtlCdDesc}"/>
        <input type="hidden" id="eventSeq" name="eventSeq" value="${fqcEventSeq.dtlCdNm}"/>
        <input type="hidden" id="eventSubject" name="eventSubject" value="${fqcEventSeq.expnsnStrVal1}"/>




        <div class="ly-content fixed fill" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">M프리퀀시 이벤트</h2>
            </div>
            <div class="freq-content">
                <div class="freq-head">
                    <p>나의 M탬프 현황</p>
                    <button id="btnReload"><i class="c-icon c-icon--refresh-type3" aria-hidden="true"></i>새로고침</button>
                </div>
                <div class="freq-status">
                    <div class="freq-progress-wrap" id="divProgress" >
                        <p>현재  <span>0</span> M탬프 달성 </p>
                        <div class="freq-progress">
                            <div class="freq-progress-bar">
                                <div class="c-indicator">
                                    <span class="mark" style="width:0%">
		                				<ul class="freq-progress-stamp">
										  <li></li>
										  <li></li>
										  <li></li>
										  <li></li>
										  <li></li>
										  <li></li>
										  <li></li>
										</ul>
                                    </span>
                                </div>
                            </div>
                            <div class="freq-progress-status">
                                <span>0</span>/<span>7</span>
                            </div>
                        </div>
                    </div>
                    <div class="freq-stamp-wrap">
                        <div class="freq-stamp-title">
                            <div class="freq-stamp-title__icon">
                                <i class="c-icon c-icon--star-type2" aria-hidden="true"></i>
                                <i class="c-icon c-icon--star-type2" aria-hidden="true"></i>
                                <i class="c-icon c-icon--star-type2" aria-hidden="true"></i>
                            </div>
                            <p><b><span>${userName}</span>님,</b> 스탬프 모으고 특별 혜택 받아가세요!</p>
                        </div>

                        <ul class="freq-stamp-status">
                            <c:forEach var="fqcPlcyMs" items="${fqcPlcyMsnList}" varStatus="status">
                                <c:if test="${status.index eq 4}">
                        </ul>
                        <ul class="freq-stamp-status">
                                </c:if>
                            <li class="freq-stamp__item">
                                <button type="button" id="btnFqcMsnTp${fqcPlcyMs.dtlCd}" class="freq-stamp__item-button" data-msn-tp-cd="${fqcPlcyMs.dtlCd}" >
                                    <div class="freq-stamp__item-icon">
                                        <i class="c-icon ${fqcPlcyMs.expnsnStrVal1}" aria-hidden="true"></i>
                                    </div>
                                    <p class="freq-stamp__item-title">${fqcPlcyMs.dtlCdNm}</p>
                                    <div class="freq-stamp__item-get">받기</div>
                                </button>
                            </li>
                            </c:forEach>
                        </ul>



                    </div>
                    <div class="freq-complete-wrap">
                        <!-- 완료 시 is-completed 클래스 추가 -->
                        <div id="divComplete" class="freq-complete">완성!</div>
                    </div>

                </div>
                <div id="divBanner" class="freq-banner">
                    <div class="freq-banner-complete">
                        <p><span>${userName}</span>님, <b>0</b>개 스탬프를 달성했어요</p>
                    </div>
                    <p class="freq-banner-gift"></p>
                </div>
                <div class="freq-tip">
                    <div class="freq-tip__title">
                        <p>M탬프 모으고 선물 받는 꿀팁</p>
                        <button data-dialog-trigger="#freqTipModal"><i class="c-icon c-icon--arrow-right-bold" aria-hidden="true"></i><span class="c-hidden">꿀팁 상세 팝업 열기</span></button>
                    </div>
                    <div class="freq-tip__list">
                        <ul class="freq-tip__list-wrap">
                            <li class="freq-tip__item">
                                <img src="/resources/images/portal/frequency/freq_gift_01.png" alt="M템프 4개 달성 시 마켓  포인트 5,000원">
                                <p>M탬프 4개 달성시 </p>
                            </li>
                            <li class="freq-tip__item">
                                <img src="/resources/images/portal/frequency/freq_gift_02.png" alt="M템프 5개 달성 시 마켓  포인트 20,000원">
                                <p>M탬프 5개 달성시 </p>
                            </li>
                            <li class="freq-tip__item">
                                <img src="/resources/images/portal/frequency/freq_gift_03.png" alt="M템프 6개 달성 시 마켓  포인트 50,000원">
                                <p>M탬프 6개 달성시 </p>
                            </li>
                            <li class="freq-tip__item">
                                <img src="/resources/images/portal/frequency/freq_gift_04.png" alt="M템프 7개 달성 시 네이버페이  포인트 20만원">
                                <p>M탬프 7개 달성시 </p>
                            </li>
                        </ul>
                        <ul class="c-text-list c-bullet c-bullet--dot-number">
                            <li class="c-text-list__item">회원 로그인 및 요금제 가입하기</li>
                            <li class="c-text-list__item">마이페이지에서 정회원 인증하기</li>
                            <li class="c-text-list__item">친구초대, KT 인터넷 가입 등 특별 미션 완수하기</li>
                        </ul>
                    </div>
                </div>
                <div class="c-notice-wrap">
				          <hr>
				          <h5 class="c-notice__title">
				              <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
				              <span>유의사항</span>
				          </h5>
				          <ul class="c-notice__list freq-notice">
				              <li>본 M프리퀀시 이벤트는 kt M모바일 회원가입을 완료한 고객님이시라면 누구나 참여 가능합니다.</li>
				              <li>각 미션 별 적립은 스탬프 ‘받기‘ 버튼을 클릭해야 스탬프 적립 여부가 확인되며, ‘받기’버튼 클릭하지 않으면 스탬프는 적립되지 않습니다.</li>
				              <li>스탬프는 ‘받기’ 클릭 후 미션 성공 시 실시간 (1~2시간 내) 적립되며, 회원 탈퇴 시 소멸되어 원복되지 않습니다.<br />※ 트리플할인 성공 스탬프는 예외적으로 스탬프 적립에 최소 하루의 시간이 소요됩니다.</li>
				              <li>
				                    각각의 미션은 해당 기준으로 성공 인정되니 꼭 대상 조건에 맞게 미션을 완수해 주세요. 미션은 순서에 상관없이 원하는 대로 진행하시면 됩니다.
				                <span class="Number-label--type2">
				                    <em>1</em><b>개통완료 :</b> kt M모바일 홈페이지에서 가입한 개통 회선이 있으면 적립 / 단 정회원 인증 상태여야 스탬프 적립<br/>(프리퀀시 기간 전에 개통했더라도 스탬프 적립 / 프리퀀시 기간 내 신규 개통해도 스탬프 적립)
				                </span>
				                <span class="Number-label--type2">
				                    <em>2</em><b>마케팅 수신 동의 :</b> kt M모바일 홈페이지 내 [회원정보관리] 에서 정보수정(조회) 클릭 후 '정보/광고 수신동의' 체크
				                </span>
				                <span class="Number-label--type2">
				                    <em>3</em><b>이벤트 공유 :</b> 스탬프 모으기 페이지에서 이벤트 공유 스탬프 받기 클릭 후 카카오톡 공유 시 적립 (나에게 공유하기 시 미적립, 친구에게 공유해야 적립)
				                </span>
				                <span class="Number-label--type2">
				                    <em>4</em><b>요금제 리뷰 작성 :</b> 프리퀀시 기간 내 사용후기 최초 작성 완료 시 적립
				                </span>
				                <span class="Number-label--type2">
				                    <em>5</em><b>친구초대 :</b> 프리퀀시 기간 내 자신 외 타인 초대 완료 (세부 조건은 친구초대 이벤트와 동일)
				                </span>
				                <span class="Number-label--type2">
				                    <em>6</em><b>트리플할인 성공 :</b> 고객이 프리퀀시 기간 내 트리플할인 페이지에서 KT인터넷 개통신청 OR 셀프주문 후 인터넷 설치 완료/사용 중일 때 적립
				                </span>
				              </li>
				              <li>개통완료/요금제 리뷰 작성/친구초대/트리플할인 성공 스탬프는 개통 회선 정지/해지 시 스탬프가 소멸되니 참고 부탁드립니다.</li>
				              <li>이벤트 마감 시점 시 달성한 스탬프 개수에 따라 혜택이 증정되며 누적으로 지급되지 않습니다. (EX- 4개 달성 시 5천원 / 5개 달성 시 2만원일 때 5개 달성하면 2만원 수혜)</li>
				              <li>해당 이벤트는 여러 회선으로 중복 참여가 불가능하며, 대표회선으로 설정되어있는 회선 기준으로 참여 가능합니다.</li>
				              <li>M프리퀀시 이벤트 기간이 종료되면 스탬프 달성 건은 모두 소멸되어 추후 새로운 프리퀀시가 오픈하더라도 이어서 참여가 불가합니다.</li>
				          </ul>
				        </div>
            </div>
        </div>



    </t:putAttribute>


    <t:putAttribute name="popLayerAttr">

        <div class="c-modal c-modal--md" id="simpleDialog" role="dialog" aria-labelledby="#simpleTitle">
            <div class="c-modal__dialog" role="document">

                <div class="c-modal__content">
                    <div class="c-modal__body u-ta-center _detail">

                    </div>
                    <div class="c-modal__footer">
                        <div id="divMSN_01_RCODE_00"  class="c-button-wrap _simpleDialogButton">
                            <button onclick="location.href='/mypage/multiPhoneLine.do'" class="c-button c-button--lg c-button--gray u-width--220" >마이페이지 바로가기</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220 " data-dialog-close >닫기</button>
                        </div>
                        <div id="divMSN_01_RCODE_01"  class="c-button-wrap _simpleDialogButton">
                            <button onclick="location.href='/appForm/appSimpleInfo.do'" class="c-button c-button--lg c-button--gray u-width--220" >셀프개통하기</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220 " data-dialog-close >닫기</button>
                        </div>
                        <div id="divMSN_02_RCODE_02"  class="c-button-wrap _simpleDialogButton">
                            <button id="btnKakaoShare" class="c-button c-button--lg c-button--gray u-width--220" >카카오톡 공유하기</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220 " data-dialog-close >닫기</button>
                        </div>
                        <div id="divMSN_03_RCODE_01"  class="c-button-wrap _simpleDialogButton">
                            <button onclick="location.href='/mypage/updateForm.do'" class="c-button c-button--lg c-button--gray u-width--220" >변경 바로가기</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220 " data-dialog-close >닫기</button>
                        </div>
                        <div id="divMSN_04_RCODE_01"  class="c-button-wrap _simpleDialogButton">
                            <button onclick="location.href='/requestReView/requestReviewForm.do'" class="c-button c-button--lg c-button--gray u-width--220" >리뷰 작성하기</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220 " data-dialog-close >닫기</button>
                        </div>
                        <div id="divMSN_05_RCODE_01"  class="c-button-wrap _simpleDialogButton">
                            <button onclick="location.href='/event/frndRecommendReqView.do'" class="c-button c-button--lg c-button--gray u-width--220" >친구 초대하기</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220 " data-dialog-close >닫기</button>
                        </div>
                        <div id="divMSN_07_RCODE_01"  class="c-button-wrap _simpleDialogButton">
                            <button onclick="location.href='/event/eventDetail.do?ntcartSeq=1490'" class="c-button c-button--lg c-button--gray u-width--220" >이벤트 바로가기</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220 " data-dialog-close >닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="c-modal c-modal--md" id="notiDialog" role="dialog" aria-labelledby="#notiTitle">
            <div class="c-modal__dialog" role="document">

                <div class="c-modal__content">
                    <div class="c-modal__body u-ta-center _detail">
                        <b class='u-fs-22'>고객님께서는 현재 M프리퀀시 이벤트<br/>참여가 제한되어 있습니다.<br/>참여를 원하시면 <br/>1:1 상담문의로 내용을 남겨주세요.</b><br/><br/>서비스 이용에 불편을 드려 죄송합니다.
                    </div>
                    <div class="c-modal__footer">
                        <div   class="c-button-wrap">
                            <button onclick="location.href='/cs/csInquiryInt.do'" class="c-button c-button--lg c-button--primary u-width--220" >1:1 문의 바로가기</button>
                        </div>

                    </div>
                </div>
            </div>
        </div>



        <!-- 꿀팁 상세 팝업 -->
        <div class="c-modal c-modal--xlg" id="freqTipModal" role="dialog" aria-labelledby="freqTipModal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header u-pb--32">
                        <h2 class="c-modal__title" id="freqTipModal__title">M프리퀀시 꿀팁</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body u-p--0">
                        <img src="/resources/images/portal/frequency/freq_tip_detail_pc.png" title="M프리퀀시 꿀팁 상세정보">
                        <div class="sr-only">
                            <strong>M프리퀀시 꿀팁 상세정보</strong>
                            <p>스탬프 다 모으고 20만원 받자</p>
                            <p>딱 2달간만 M탬프 미션로드 오픈</p>
                            <p>스탬프 달성 개수 별 혜택</p>
                            <p>M탬프 4개 달성 시 M마켓 5천원</p>
                            <p>M탬프 5개 달성 시 M마켓 2만원</p>
                            <p>M탬프 6개 달성 시 M마켓 5만원</p>
                            <p>M탬프 7개 달성 시 네이버페이 20만원</p>
                            <p>각 달성 미션 개수 별 혜택은 중복 지급되지 않습니다</p>
                            <p>4 달성 시 5천원/ 5개 달성 시 2만원 / 6개 달성 시 5만원/ 7개 달성 시 20만원</p>
                            <p>STEP 1 기본 미션 2개 달성</p>
                            <p>회원 가입은 했는데, 요금제 가입은 안했다면?</p>
                            <p>1. 이벤트 페이지 친구에게 공유하기</p>
                            <p>스탬프 모으기 페이지에서"이벤트 공유'스탬프 받기 클릭</p>
                            <p>카카오톡으로 친구에게 공유만 하면 스탬프 1개!</p>
                            <p>나에게 공유 시 스탬프느 지급되지 않아요!</p>
                            <p>2. 마케팅 수신 동의하기</p>
                            <p>마이페이지 → 정보/광고 수신동의 → 수신동의 체크!</p>
                            <p>간단히 클릭 한 번으로 스탬프 추가!</p>
                            <p>STEP 2 가입/리뷰 미션 2개 달성</p>
                            <p>3. M모바일 홈페이지에서 5,000원 이상 요금제 가입하기</p>
                            <p>마이페이지에서 정회원 인증하기</p>
                            <p>정회원 인증을 꼭! 해야 개통 스탬프 적립 가능</p>
                            <p>4. 요금제 리뷰 작성하기</p>
                            <p>이벤트 → 사용후기 → 후기 작성하기</p>
                            <p>짧은 후기만 써도 스탬프 GET!</p>
                            <p>이렇게 쉽게 4개 달성완료! 5천원 혜탠</p>
                            <p>STEP 3 스페셜 미션 달성</p>
                            <p>요금제 가입완료하고 리뷰까지 썼다면?</p>
                            <p>5. 친구초대 2명 완료하기!</p>
                            <p>친구초대 ID발급 후 친구 공유 및 가입 시 ID 입력 요청!</p>
                            <p>친구 1명 가입 시 스탬프 1개</p>
                            <p>친구 2명 이상 가입 시 스탬프 2개</p>
                            <p>친구초대 1명, 스탬프 5개 > 2만원</p>
                            <p>친구초대 2명, 스탬프 6개 > 5만원</p>
                            <p>STEP 4 스페셜 미션 달성</p>
                            <p>친구초대 2명까지 완료했다면?</p>
                            <p>6. KT 인터넷 상담 신청 후 가입하기</p>
                            <p>트리플할인 페이지에서 KT인터넷 상담신청 OR 셀프주문 후 인터넷 가입하기</p>
                            <p>KT인터넷 트리플할인</p>
                            <p>KT인터넷+TV+M모바일 요금제를 월 1만원 이하로!</p>
                            <p>5월 한정 M모바일 신규 가입 시 최대 8만원 추가 증정!</p>
                            <p>마지막 스탬프 적립완료!</p>
                            <p>네이버페이 20만원 혜택 증정</p>
                        </div>
                        <div class="c-button-wrap u-mt--64">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" data-dialog-close>스탬프 모으러 가기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>

</t:insertDefinition>
