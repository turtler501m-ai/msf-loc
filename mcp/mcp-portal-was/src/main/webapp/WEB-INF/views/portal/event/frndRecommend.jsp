<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="layoutMainDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js?version=26.01.28"></script>
        <script type="text/javascript" src="/resources/js/portal/event/frndRecommend.js?version=25-12-30"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">친구초대</h2>
            </div>
            <div class="friend-banner friend-banner--new">
	          <div class="friend-banner__wrap">
	              <h3>친구초대하면?<br /><span>친구도 나도 1+1 혜택!</span></h3>
	              <div class="friend-banner__button">
	                  <a href="/mypage/reCommendState.do" title="내 초대 내역 확인 페이지 바로가기">
	                      <span>내 초대 내역 확인하기</span>
	                      <i class="c-icon c-icon--anchor-purple" aria-hidden="true"></i>
	                  </a>
	              </div>
	              <img src="/resources/images/portal/event/friend_banner_2.png" alt="친구초대 1+1 혜택" aria-hidden="true">
	          </div>
	        </div>
	        <div class="friend-invite-content">
	        	<div class="friend-wrap">
		    		<div class="friend-recom-point">
		                <img src="/resources/images/portal/event/friend_recommend_point.png" alt="">
		                <div class="friend-recom-point__info">
		                    <div class="friend-recom-point__info-desc">
			                    <p>초대하고 싶은 가족/친구가 있다면,</p>
			                    <p><b>추천 링크를 만들어 친구에게 공유</b>하세요!</p>
		                    </div>
		                    <div class="friend-recom-point__info-point">
			                    <p>친구가 내ID 입력 후 가입하면 M마켓 포인트 증정</p>
		                    </div>
		                </div>
		            </div>
		      	</div>
		      	<input type="hidden" id="userDivision" value="${userDivision}"/>
		      	<div class="friend-wrap-wide">
		      		<div class="friend-wrap">
		   		    	<h4 class="friend__title u-mb--m18">친구초대 추천 링크 만들기</h4>
		   		    	<c:if test="${empty maskingSession}">
	                        <div class="masking-badge-wrap">
	                            <div class="masking-badge">
	                                <a class="masking-badge__button" href="/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
	                                    <i class="masking-badge__icon" aria-hidden="true"></i>
	                                    <p>가려진(*)<br />정보보기</p>
	                                </a>
	                            </div>
	                        </div>
	                    </c:if>
	                    <%@ include file="/WEB-INF/views/portal/mypage/sendCertSms.jsp"%>
	                    <p class="c-text c-text--fs18 u-mt--48">친구초대 ID 발급을 위해서는 개인정보 제공을 위한 동의 및 가입 여부의 확인이 필요하니, 동의하여 주시기 바랍니다.</p>
		   		    	<div class="c-form-wrap u-mt--27">
		                    <div class="c-agree">
		                        <div class="c-agree__item">
		                            <div class="c-agree__inner">
		                                <input class="c-checkbox" id="chkAgreeA" type="checkbox" name="chkAgreeA">
		                                <label class="c-label" for="chkAgreeA">
		                                	해당 개인정보 이용 및 책임에 대한 내용을 확인하였으며, 이에 동의합니다.
		                                	<p class="u-co-sub-4">※친구초대 ID 발급 및 혜택 적용을 위하여 고객님의 개인정보가 이용될 수 있으며 초대ID 활용에 대한 책임은 고객에게 있습니다.</p>
		                                </label>
		                            </div>
		                        </div>
		                    </div>
		                    <div class="c-agree u-mt--13">
		                        <div class="c-agree__item">
		                            <button type="button" class="c-button c-button--xsm" onclick="fnSetEventId('chkMstoreAgree'); openPage('termsPop','/termsPop.do','cdGroupId1=FormEtcCla&amp;cdGroupId2=FrndMstoreAgree1', '0');">
		                                <span class="c-hidden">결합을 위한 필수 확인 사항 동의 상세보기</span>
		                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
		                            </button>
		                            <div class="c-agree__inner">
		                                <input class="c-checkbox" id="chkMstoreAgree" type="checkbox" name="chkMstoreAgree">
		                                <label class="c-label" for="chkMstoreAgree">개인정보 제 3자 제공 동의(필수)</label>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		                <h4 class="friend__title-sub">추천 링크 만들기</h4>
		                <div class="c-form-wrap">
		                    <div class="c-form-group" role="group">
		                        <div class="c-checktype-row c-col2 u-mt--26">
		                            <input class="c-radio c-radio--button c-radio--button--type1" name="linkTypeCd" value="01" id="linkTypeCd_01" type="radio" >
		                            <label class="c-label" for="linkTypeCd_01"><span>기본 추천 링크 만들기</span></label>

		                            <input class="c-radio c-radio--button c-radio--button--type1" name="linkTypeCd" value="99" id="linkTypeCd_02" type="radio" checked>
		                            <label class="c-label" for="linkTypeCd_02">
		                            	<span class="friend-invite__lable">
		                            		<em class="friend-invite__text">올인원 추천 링크 만들기</em>
		                            		<button class="friend-invite__tp-btn" role="button" data-tpbox="#friendInviteTp1" aria-describedby="#friendInviteTp1__title">
		                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
		                                        <span class="sr-only">올인원 추천 링크 만들기 툴팁 열기</span>
		                                    </button>
		                                    <div class="c-tooltip-box u-ta-left" id="friendInviteTp1" role="tooltip">
									            <div class="c-tooltip-box__title" id="friendInviteTp1__title">올인원 추천 링크란?</div>
									            <div class="c-tooltip-box__content">
									            	복잡한 설명은 이제 그만!<br /><b>이 링크 하나면 추천 정보·가입·혜택이 전부 연결돼요.</b><br />친구에게 보내면, 알아서 고객님의 추천 정보가 쏙 들어갑니다!
									            </div>
									        </div>
		                            		<p class="u-fs-14">(이 링크 하나면 초대가 쉬워져요!)</p>
		                            	</span>
		                           	</label>
		                        </div>
		                    </div>
		                </div>
		                <div id="divLinkType_02" style="display:block;">
	                        <div class="c-form-wrap">
	                            <h4 class="friend__title-link">
		                        	<span class="friend__title-link-circle">1</span><b>친구에게 추천해 주고 싶은 개통 방법은 무엇인가요? <em>(유심구매/eSIM)</em></b>
		                       	</h4>
	                            <div class="c-form-group" role="group">
		                            <div class="c-checktype-row c-col2 u-mt--19">
		                                <input class="c-radio c-radio--button c-radio--button--type1" id="openMthdCd_01" type="radio" name="openMthdCd" value="01">
		                                <label class="c-label" for="openMthdCd_01">
		                                    <span class="friend-invite__lable">
			                            		<em>바로배송 유심 구매</em>
			                            		<p class="u-fs-14">(당일 퀵)</p>
			                            	</span>
		                                </label>
		                                <input class="c-radio c-radio--button c-radio--button--type1" id="openMthdCd_02" type="radio" name="openMthdCd" value="02">
		                                <label class="c-label" for="openMthdCd_02">
		                                    <span class="friend-invite__lable">
			                            		<em>편의점 유심 구매</em>
			                            		<p class="u-fs-14">(CU/GS25 등)</p>
			                            	</span>
		                                </label>
		                                <input class="c-radio c-radio--button c-radio--button--type1" id="openMthdCd_03" type="radio" name="openMthdCd" value="03">
		                                <label class="c-label" for="openMthdCd_03">
		                                    <span class="friend-invite__lable">
			                            		<em>오픈마켓 유심 구매</em>
			                            		<p class="u-fs-14">(쿠팡/네이버 등)</p>
			                            	</span>
		                                </label>
		                                <input class="c-radio c-radio--button c-radio--button--type1" id="openMthdCd_04" type="radio" name="openMthdCd" value="04">
		                                <label class="c-label" for="openMthdCd_04">
		                                    <span class="friend-invite__lable">
			                            		<em>eSIM 셀프개통</em>
			                            		<p class="u-fs-14">(실물유심 없이 즉시개통)</p>
			                            	</span>
		                                </label>
		                            </div>
		                        </div>
	                        </div>
	                        <div class="c-form-wrap u-mt--48">
	                            <h4 class="friend__title-link">
		                        	<span class="friend__title-link-circle">2</span><b>친구에게 맞는 요금제 3개를 선택해 주세요! <em>(데이터 / 통화량 필터를 통해 검색하면 쉬워요!)</em></b>
		                       	</h4>
	                            <div class="rate-comp-total-wrap u-mt--20">
	                          		  <h4 class="rate-comp-total">총 <span id="spCnt">-</span>개 요금제</h4>
			                          <div class="rate-comp-total__button">
			                              <button class="friend-invite__tp-btn" role="button" data-tpbox="#friendInviteTp2" aria-describedby="#friendInviteTp2__title">
			                                  <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
			                                  <span class="sr-only">올인원 요금제 추천 링크 만들기 툴팁 열기</span>
			                              </button>
			                              <div class="c-tooltip-box u-ta-left" id="friendInviteTp2" role="tooltip">
								            <div class="c-tooltip-box__title" id="friendInviteTp2__title">친구의 평소 요금제 스타일은 뭔가요?<br />필터에서 골라보세요!</div>
								            <div class="c-tooltip-box__content">
								            	 <b>*데이터 사용량</b><br />
								            	 거의 와이파이만 쓴다면 : ~3GB<br />
												웹서핑과 카톡만 한다면 : 3GB~7GB<br />
												출퇴근길에 음악을 듣는다면 : 7GB~<br />
												매일 1시간 이상 영상을 본다면 : 무제한<br /><br />
												<b>*통화량</b><br />
												통화량이 많다면 : 무제한<br />
												통화량이 적다면 : 통화량 제한<br />
												(300분 이하)
								            </div>
								        </div>
			                            <button id="btnFilter"><i class="c-icon c-icon--sort" aria-hidden="true"></i>필터</button>
			                            <button id="btnReset"><i class="c-icon c-icon--refresh-type2" aria-hidden="true"></i>재설정</button>
			                          </div>
			                      </div>
			                      <ul class="rate-comp-select">
		                            <li class="rate-comp-select__item">
		                                <div class="c-form">
		                                    <label class="c-label c-hidden" for="rateComparison1">요금제 선택</label>
		                                    <select class="c-select" id="rateComparison1" name="rateComparison1">
		                                        <option value="">요금제를 선택해주세요</option>
		                                    </select>
		                                </div>
		                            </li>
		                            <li class="rate-comp-select__item">
		                                <div class="c-form">
		                                    <label class="c-label c-hidden" for="rateComparison2">요금제 선택</label>
		                                    <select class="c-select" id="rateComparison2" name="rateComparison2">
		                                        <option value="">요금제를 선택해주세요</option>
		                                    </select>
		                                </div>
		                            </li>
		                            <li class="rate-comp-select__item">
		                                <div class="c-form">
		                                    <label class="c-label c-hidden" for="rateComparison3">요금제 선택</label>
		                                    <select class="c-select" id="rateComparison3" name="rateComparison3">
		                                        <option value="">요금제를 선택해주세요</option>
		                                    </select>
		                                </div>
		                            </li>
		                        </ul>
                            </div>
	                    </div>
	                    <div id="divRecommend" style="display:none" >
	                        <h3 class="c-group--head u-mb--0 u-mt--37">나의 친구초대 추천 URL</h3>
	                        <div class="c-input-wrap">
	                            <input class="c-input" id="recommend" name="recommend" type="text"  readonly >
	                            <label class="c-label c-hidden" for="recommend">나의 친구초대 추천 URL</label>
	                        </div>
	                        <ul class="c-text-list u-fs-15 u-co-sub-4 u-mt--17">
					            <li class="c-text-list__item">※전달한 친구초대 URL을 통해 접속하셨을 경우 신규 가입 신청서에 추천인ID가 신청서에 자동 기입됩니다.</li>
					            <li class="c-text-list__item u-mt--5">※전용 URL을 사용하지않을 경우 발급받으신 친구초대ID를 피추천인이 신청서에 직접 입력하셔야 혜택을 받으실 수 있습니다.</li>
					        </ul>
	                    </div>
	                    <div class="c-button-wrap u-mt--52">
	                    	<button class="c-button c-button-round--md c-button--primary u-width--220" id="btnfrndUpdate">
	                            <span>내 초대 링크 저장하기</span>
	                        </button>
	                        <button class="c-button c-button-round--md c-button--kakao u-width--220" id="btnfrndKakaoShare">
	                            <i class="c-icon c-icon--message" aria-hidden="true"></i>
	                            <span>카카오톡 공유</span>
	                        </button>
	                        <button class="c-button c-button-round--md c-button--share u-width--220" id="btnLinkCopy" data-page="form">
	                            <i class="c-icon c-icon--share" aria-hidden="true"></i>
	                            <span>추천 링크 복사</span>
	                        </button>
	                    </div>
			        </div>
		      	</div>
		      	<div class="friend-wrap">
		      		<div class="friend-guide">
		              <h4 class="friend__title">친구초대 혜택 받는 법!</h4>
		              <div class="friend-guide-box">
		                  <ul class="friend-guide__list">
		                      <li class="friend-guide__item">
		                          <span class="friend-guide__label">초대하는 사람 </span>
		                          <p>내 추천 링크를 친구에게 공유!</p>
		                      </li>
		                      <li class="friend-guide__item">
		                          <span class="friend-guide__label--point">초대받은 친구 </span>
		                          <p>공유 받은 링크 접속 후 가입신청서 작성</p>
		                      </li>
		                  </ul>
		                  <img src="/resources/images/portal/event/friend_guide_2.png" alt="1. 친구를 초대하는 사람은 내 추천 링크를 친구에게 공유. 2. 초대받은 친구는 공유 받은 링크 접속 후가입신청서 작성(친구초대 추천인 ID 입력 확인)">
		              </div>
		        	</div>
		        	<!-- 친구초대 배너 -->
	                <ul class="friend-banner-list">
	                </ul>
	                <h5 class="c-heading c-heading--fs20 c-heading--regular u-fw--medium u-mt--77">유의사항</h5>
            		<hr class="c-hr c-hr--title u-mb--0">
            		<div>
						<!-- 관리자 관리 HTML START-->
						${noticeContent}
						<!-- 관리자 관리 HTML END-->
					</div>
		      	</div>
	        </div>

            <script src="/resources/js/portal/vendor/swiper.min.js"></script>

        </div>

    </t:putAttribute>

    <t:putAttribute name="popLayerAttr">
        <!-- 필터 팝업 -->
        <div class="c-modal c-modal--lg c-modal--rate-comparison" id="rateComparisonModal" role="dialog" tabindex="-1" aria-labelledby="rateComparisonModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="rateComparisonModalTitle">필터 <i class="c-icon c-icon--sort" aria-hidden="true"></i></h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="review-filter">
                        <div class="c-filter">
                            <div class="c-filter__inner">
                                <button class="c-button" data-target="section1">요금제</button>
                                <button class="c-button" data-target="section2">데이터</button>
                                <button class="c-button" data-target="section3">통화</button>
                                <button class="c-button" data-target="section4">문자</button>
                                <button class="c-button" data-target="section5">가격</button>
                                <button class="c-button" data-target="section6">추가혜택</button>
                            </div>
                        </div>
                    </div>
                    <div class="c-modal__body">
                        <section  class="section1" id="section1">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="rateComparisonRateGroupTitle">
                                    <h3 class="c-group--head" id="rateComparisonRateGroupTitle">요금제</h3>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonRate1" value="A" type="checkbox" name="rateComparisonRateGroup" checked>
                                        <label class="c-label" for="rateComparisonRate1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonRate2" value="4"  type="checkbox" name="rateComparisonRateGroup">
                                        <label class="c-label" for="rateComparisonRate2">LTE</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonRate3" value="5"  type="checkbox" name="rateComparisonRateGroup">
                                        <label class="c-label" for="rateComparisonRate3">5G</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonRate4" value="2"  type="checkbox" name="rateComparisonRateGroup">
                                        <label class="c-label" for="rateComparisonRate4">제휴요금제</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonRate5" value="99" data-rate-ctg="05"   type="checkbox" name="rateComparisonRateGroup">
                                        <label class="c-label" for="rateComparisonRate5">연령특화</label>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section class="section2" id="section2">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="rateComparisonDataGroupTitle">
                                    <h3 class="c-group--head" id="rateComparisonDataGroupTitle">데이터</h3>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonData1" value="" type="radio" name="rateComparisonDataGroup" checked>
                                        <label class="c-label" for="rateComparisonData1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonData2" value="2" data-min-val="0" data-max-val="3" type="radio" name="rateComparisonDataGroup">
                                        <label class="c-label" for="rateComparisonData2">~3GB</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonData3" value="3" data-min-val="3" data-max-val="7" type="radio" name="rateComparisonDataGroup">
                                        <label class="c-label" for="rateComparisonData3">3GB~7GB</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonData4" value="4" data-min-val="7" data-max-val="999" type="radio" name="rateComparisonDataGroup">
                                        <label class="c-label" for="rateComparisonData4">7GB~</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonData5" value="1" data-min-val="999" data-max-val="999" type="radio" name="rateComparisonDataGroup">
                                        <label class="c-label" for="rateComparisonData5">무제한</label>
                                    </div>
                                </div>
                                <!-- 무제한 선택 시 나오는 영역 -->
                                <div id="divDataSlider" style="display:none;">
                                    <div class="c-range c-range--type2 multi c-range--step6 u-mt--16">
                                        <div class="c-hidden">데이터 선택(0GB,7GB,10GB,15GB,100GB,제한없음)</div>
                                        <div class="c-range__slider" data-range-slider data-rsliders-block-step="20" data-rsliders-a11y="0GB|7GB|10GB|15GB|100GB|제한없음">
                                            <input type="range" min="0" max="100" value="0" step="20" title="데이터 최소값">
                                            <input type="range" min="0" max="100" value="100" step="20" title="데이터 최대값">
                                        </div>
                                        <div class="c-range__ruler">
                                            <span class="ruler-mark">0GB</span>
                                            <span class="ruler-mark">7GB</span>
                                            <span class="ruler-mark">10GB</span>
                                            <span class="ruler-mark">15GB</span>
                                            <span class="ruler-mark">100GB</span>
                                            <span class="ruler-mark">제한없음</span>
                                        </div>
                                    </div>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonInfinityData1" value="0" data-min-val="0" data-max-val="100"  type="radio" name="rateComparisonInfinityDataGroup" checked>
                                        <label class="c-label" for="rateComparisonInfinityData1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonInfinityData2" value="1" data-min-val="0" data-max-val="20" type="radio" name="rateComparisonInfinityDataGroup">
                                        <label class="c-label" for="rateComparisonInfinityData2">~7GB</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonInfinityData3" value="2" data-min-val="20" data-max-val="40"  type="radio" name="rateComparisonInfinityDataGroup">
                                        <label class="c-label" for="rateComparisonInfinityData3">7GB~10GB</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonInfinityData4" value="3" data-min-val="40" data-max-val="60" type="radio" name="rateComparisonInfinityDataGroup">
                                        <label class="c-label" for="rateComparisonInfinityData4">10GB~15GB</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonInfinityData5" value="4" data-min-val="60" data-max-val="80" type="radio" name="rateComparisonInfinityDataGroup">
                                        <label class="c-label" for="rateComparisonInfinityData5">15GB~100GB</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonInfinityData6" value="5" data-min-val="80" data-max-val="100" type="radio" name="rateComparisonInfinityDataGroup">
                                        <label class="c-label" for="rateComparisonInfinityData6">100GB~</label>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section class="section3" id="section3">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="rateComparisonCallGroupTitle">
                                    <h3 class="c-group--head" id="rateComparisonCallGroupTitle">통화</h3>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonCall1" value=""  type="radio" name="rateComparisonCallGroup" checked>
                                        <label class="c-label" for="rateComparisonCall1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonCall2" value="1" data-min-val="0" data-max-val="100" type="radio" name="rateComparisonCallGroup">
                                        <label class="c-label" for="rateComparisonCall2">~100분</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonCall3" value="2" data-min-val="100" data-max-val="200" type="radio" name="rateComparisonCallGroup">
                                        <label class="c-label" for="rateComparisonCall3">100분~200분</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonCall4" value="3" data-min-val="200" data-max-val="300" type="radio" name="rateComparisonCallGroup">
                                        <label class="c-label" for="rateComparisonCall4">200분~300분</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonCall5" value="4" data-min-val="999" data-max-val="999" type="radio" name="rateComparisonCallGroup">
                                        <label class="c-label" for="rateComparisonCall5">무제한</label>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section class="section4" id="section4">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="rateComparisonSmsGroupTitle">
                                    <h3 class="c-group--head" id="rateComparisonSmsGroupTitle">문자</h3>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonSms1" value=""  type="radio" name="rateComparisonSmsGroup" checked>
                                        <label class="c-label" for="rateComparisonSms1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonSms2" value="1" data-min-val="0" data-max-val="50" type="radio" name="rateComparisonSmsGroup">
                                        <label class="c-label" for="rateComparisonSms2">~50건</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonSms3" value="2" data-min-val="50" data-max-val="100" type="radio" name="rateComparisonSmsGroup">
                                        <label class="c-label" for="rateComparisonSms3">50건~100건</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonSms4" value="3" data-min-val="100" data-max-val="300" type="radio" name="rateComparisonSmsGroup">
                                        <label class="c-label" for="rateComparisonSms4">100건~300건</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonSms5" value="4" data-min-val="999" data-max-val="999" type="radio" name="rateComparisonSmsGroup">
                                        <label class="c-label" for="rateComparisonSms5">무제한</label>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section class="section5" id="section5">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="rateComparisonPriceGroupTitle">
                                    <h3 class="c-group--head" id="rateComparisonPriceGroupTitle">가격</h3>
                                    <div class="c-range c-range--type2 multi c-range--step6 u-mt--16 rate-comparison-Price">
                                        <div class="c-hidden">가격 선택(0원, 5,000원, 10,000원, 20,000원, 30,000원,제한없음)</div>
                                        <div class="c-range__slider" data-range-slider data-rsliders-block-step="20" data-rsliders-a11y="0원|5,000원|10,000원|20,000원|30,000원|제한없음">
                                            <input type="range" min="0" max="100" value="0" step="20" title="가격 최소값">
                                            <input type="range" min="0" max="100" value="100" step="20" title="가격 최대값">
                                        </div>
                                        <div class="c-range__ruler">
                                            <span class="ruler-mark">0원</span>
                                            <span class="ruler-mark">5,000원</span>
                                            <span class="ruler-mark">10,000원</span>
                                            <span class="ruler-mark">20,000원</span>
                                            <span class="ruler-mark">30,000원</span>
                                            <span class="ruler-mark">제한없음</span>
                                        </div>
                                    </div>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonPrice1" type="radio" value="0" data-min-val="0" data-max-val="100" name="rateComparisonPriceGroup" checked>
                                        <label class="c-label" for="rateComparisonPrice1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonPrice2" type="radio" value="1" data-min-val="0" data-max-val="20" name="rateComparisonPriceGroup">
                                        <label class="c-label" for="rateComparisonPrice2">~5,000원</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonPrice3" type="radio" value="2" data-min-val="20" data-max-val="40" name="rateComparisonPriceGroup">
                                        <label class="c-label" for="rateComparisonPrice3">5,000원~10,000원</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonPrice4" type="radio" value="3" data-min-val="40" data-max-val="60" name="rateComparisonPriceGroup">
                                        <label class="c-label" for="rateComparisonPrice4">10,000원~20,000원</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonPrice5" type="radio" value="4" data-min-val="60" data-max-val="80" name="rateComparisonPriceGroup">
                                        <label class="c-label" for="rateComparisonPrice5">20,000원~30,000원</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonPrice6" type="radio" value="5" data-min-val="80" data-max-val="100" name="rateComparisonPriceGroup">
                                        <label class="c-label" for="rateComparisonPrice6">30,000원~</label>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section class="section6" id="section6">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="rateComparisonGiftGroupTitle">
                                    <h3 class="c-group--head" id="rateComparisonGiftGroupTitle">추가혜택</h3>
                                    <div class="c-checktype-row">
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift1" value="A" type="checkbox" name="rateComparisonGiftGroup" checked>
                                        <label class="c-label" for="rateComparisonGift1">전체</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift2" value="01"  type="checkbox" name="rateComparisonGiftGroup">
                                        <label class="c-label" for="rateComparisonGift2">아무나 SOLO 결합</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift3" value="06"  type="checkbox" name="rateComparisonGiftGroup">
                                        <label class="c-label" for="rateComparisonGift3">데이터 쉐어링</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift4" value="07"  type="checkbox" name="rateComparisonGiftGroup">
                                        <label class="c-label" for="rateComparisonGift4">데이터 함께쓰기</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift5" value="05"  type="checkbox" name="rateComparisonGiftGroup">
                                        <label class="c-label" for="rateComparisonGift5">트리플 할인</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift6" value="02"  type="checkbox" name="rateComparisonGiftGroup">
                                        <label class="c-label" for="rateComparisonGift6">데이터 쿠폰</label>
                                        <input class="c-radio c-radio--button c-radio--button--type3" id="rateComparisonGift7" value="09"  type="checkbox" name="rateComparisonGiftGroup">
                                        <label class="c-label" for="rateComparisonGift7">KT WiFi</label>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                    <div class="c-modal__footer u-flex-center">
                        <div class="u-box--w460 c-button-wrap">
                                <%--<button class="c-button c-button--full c-button--gray"><i class="c-icon c-icon--refresh-type2" aria-hidden="true"></i>재설정</button>--%>
                            <button id="btnSetList" class="c-button c-button--full c-button--mint u-co-white">총 <span>-</span>건 적용</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>

</t:insertDefinition>