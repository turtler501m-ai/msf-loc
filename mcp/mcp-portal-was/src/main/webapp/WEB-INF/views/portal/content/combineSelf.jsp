<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">아무나 SOLO 결합</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/validator.js?version=22.08.18"></script>
        <script type="text/javascript" src="/resources/js/portal/content/combineSelf.js?version=25-03-12"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">아무나 SOLO 결합</h2>
            </div>
            <div class="combine-self-banner">
                <div class="combine-self-banner__wrap">
                    <h3>혼자 사용해도 최대 20GB</h3>
                    <p>혼자서도 고민할 필요 없이 결합 가능!</p>
                    <div class="combine-self-banner__button">
                        <a data-scroll-top="#combineSelf|140">
                            <span>결합신청</span>
                            <span class="c-hidden">결합 신청 바로가기</span>
                            <i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
                        </a>
                    </div>
                    <img src="/resources/images/portal/product/combine_self_banner.png" alt="">
                </div>
            </div>
            <div class="c-row c-row--lg">
                <div class="combine-head">
                    <div class="combine-self-service__wrap">
                        <ul class="combine-self-service__list">
                            <li class="combine-self-service__item">
                                <div class="combine-self-service__img">
                                    <img src="/resources/images/portal/product/combine_self_service_01.png" alt="">
                                </div>
                                <div class="combine-self-service__text">
                                    <p>결합할 사람을<br />못 찾으셨나요?</p>
                                </div>
                            </li>
                            <li class="combine-self-service__item">
                                <div class="combine-self-service__img">
                                    <img src="/resources/images/portal/product/combine_self_service_02.png" alt="">
                                </div>
                                <div class="combine-self-service__text">
                                    <p>아무나 찾지 않아도<br />혼자 결합 가능!</p>
                                </div>
                            </li>
                            <li class="combine-self-service__item">
                                <div class="combine-self-service__img">
                                    <img src="/resources/images/portal/product/combine_self_service_03.png" alt="">
                                </div>
                                <div class="combine-self-service__text">
                                    <p>쉽게 결합하고<br />최대 20GB 받으세요</p>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="combine-self-offer">
                <div class="combine-self-offer__wrap">
                    <p>아무나 SOLO 결합 대상 요금제를 사용하고 있다면</p>
                    <p class="combine-self-offer__title">
                        <b>최대 <span>20GB</span>까지</b><br />데이터를 추가로 제공해드려요
                    </p>
                    <img src="/resources/images/portal/product/combine_self_guide_offer.png" alt="">
                </div>
            </div>
            <div class="c-row c-row--lg">
                <div class="combine-head">
                    <p class="combine-head__title">아무나 SOLO 결합 대상 요금제</p>
                    <p class="combine-head__text">아무나 SOLO 결합시 요금제별 제공 혜택을 확인 해 보세요</p>
                    <div class="combine-head-anyone" id="combineSelfTable">

                    </div>
                </div>
            </div>
            <div class="ly-content--wrap self-open-bk--light-purple-type2">
                <div class="swiper-banner" id="combineSelfGuide">
                    <div class="swiper-container">
                        <div class="swiper-wrapper">
                            <div class="swiper-slide">
                                <img src="/resources/images/portal/product/combine_self_guide_01.png" alt="아무나 SOLO 결합 신청 방법 결합된 내역은 마이페이지 결합 내역 조회에서 확인이 가능합니다.">
                            </div>
                            <div class="swiper-slide">
                                <img src="/resources/images/portal/product/combine_self_guide_02.png" alt="1. 가입정보확인, 로그인 또는 휴대폰 인증을 통해 결합 가능 여부를 확인 해 보세요. 다이렉트몰 아이디로 로그인하고 간편하게 결합하세요.">
                            </div>
                            <div class="swiper-slide">
                                <img src="/resources/images/portal/product/combine_self_guide_03.png" alt="2. 결합 신청, 결합 가능 여부를 확인 후 결합 신청 버튼을 클릭 해 주세요.">
                            </div>
                            <div class="swiper-slide">
                                <img src="/resources/images/portal/product/combine_self_guide_04.png" alt="3. 결합 확인, 결합 신청 후 결합 내역 조회를 통해 결합 완료를 확인 해 주세요.">
                            </div>
                        </div>
                    </div>
                    <button class="swiper-banner-button--next swiper-button-next" type="button"></button>
                    <button class="swiper-banner-button--prev swiper-button-prev" type="button"></button>
                    <div class="swiper-controls-wrap u-box--w1100">
                        <div class="swiper-controls">
                            <div class="swiper-pagination" aria-hidden="true"></div>
                        </div>
                    </div>
                </div>
            </div>

               <!-- 유튜브 영상 영역 -->
                <div id="combineSolo">

                </div>

            <div class="c-row c-row--lg">
                <div class="combine-self" id="combineSelf">
                    <!-- 본인인증 방법 선택 -->
                    <div id="divCheckSession" <c:if test="${nmcp:hasLoginUserSessionBean()}">style="display: none"</c:if> >
                        <p class="u-fw--bold u-fs-20 u-ta-center">정보 확인을 위해 인증 방법을 선택 후 결합을 신청하세요.</p>
                        <div class="c-button-wrap u-mt--32">
                            <a id="btnLogin" class="c-button c-button--lg c-button--primary c-button--w460" href="javascript:void(0);"  title="로그인 페이지 바로가기">로그인 하기</a>
                            <button type="button" class="c-button c-button--lg c-button--primary c-button--w460"  title="휴대폰인증하기" data-dialog-trigger="#divCertifySms">휴대폰 인증하기</button>
                        </div>
                    </div>

                    <!-- 본인인증 후 -->
                    <div id="divResult" <c:if test="${!nmcp:hasLoginUserSessionBean()}">style="display: none"</c:if> >
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
                        <div class="c-form-wrap">
                            <div class="c-form-group" role="group" aria-labelledby="combineMyinfo">
                                <div class="c-group--head u-fs-18">
                                    <h4 id="combineMyinfo">조회할 회선을 선택해 주세요.</h4>
                                </div>
                                <div class="combine-myinfo">
                                    <div class="c-form-row c-col2">
                                        <div class="c-form-field">
                                            <div class="c-form__select has-value">
                                                <select class="c-select" id="ncn" name="ncn">
                                                    <c:forEach items="${cntrList}" var="item">
                                                        <c:choose>
                                                            <c:when test="${maskingSession eq 'Y'}">
                                                                <option value="${item.svcCntrNo }" ${item.svcCntrNo eq searchVO.ncn?'selected':''}>${item.formatUnSvcNo }</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${item.svcCntrNo }" ${item.svcCntrNo eq searchVO.ncn?'selected':''}>${item.cntrMobileNo }</option>
                                                            </c:otherwise>
                                                       </c:choose>
                                                    </c:forEach>
                                                </select>
                                                <label class="c-form__label" for="ncn">휴대폰 번호</label>
                                            </div>
                                        </div>
                                        <div class="c-form-field">
                                            <div class="c-form__input has-value">
                                                <input type="text" class="c-input" id="rateNm" name="rateNm"  value="" readonly>
                                                <label class="c-form__label" for="rateNm">요금제</label>
                                            </div>
                                        </div>
                                        <button type="button" id="btnSelectCTN" class="combine-check">조회</button>
                                    </div>
                                </div>
                            </div>
                        </div>



                        <div id="divResultInfo">

                        </div>

                        <div id="divReg"  style="display: none">
                            <div class="c-agree u-mt--48">
                                <div class="c-agree__item">
                                    <button type="button" class="c-button c-button--xsm" onclick="fnSetEventId('chkAgree');openPage('termsPop','/termsPop.do','cdGroupId1=TERMSCOM&cdGroupId2=TERMSCOM06',0);">
                                        <span class="c-hidden">결합을 위한 필수 확인사항 동의 (필수) 상세보기</span>
                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                    </button>
                                    <div class="c-agree__inner">
                                        <input type="checkbox" class="c-checkbox" id="chkAgree" name="chkAgree">
                                        <label class="c-label" for="chkAgree">결합을 위한 필수 확인사항 동의 (필수)</label>
                                    </div>
                                </div>
                            </div>
                            <div class="combine-sign u-mt--56">
                                <button type="button" class="is-disabled" id="btnRegCombin">결합 신청</button>
                            </div>
                        </div>

                    </div>
                </div>

                <div class="swiper-banner u-mt--64" id="swiperCombineSelf">
                    <div class="swiper-container">
                        <div class="swiper-wrapper" id="swiperArea"></div>
                    </div>
                    <button class="swiper-banner-button--next swiper-button-next" type="button"></button>
                    <button class="swiper-banner-button--prev swiper-button-prev"  type="button"></button>
                </div>

                <h5 class="c-heading c-heading--fs20 c-heading--regular u-mt--96">유의사항</h5>
                <hr class="c-hr c-hr--title u-mb--0">
                <div class="c-accordion c-accordion--type3" data-ui-accordion>
                    <ul class="c-accordion__box">
                        <li class="c-accordion__item">
                            <div class="c-accordion__head">
                                <strong class="c-accordion__title">결합 해지 안내</strong>
                                <button class="runtime-data-insert c-accordion__button" id="acc_header_a1" type="button" aria-expanded="false" aria-controls="acc_content_a1"></button>
                            </div>
                            <div class="c-accordion__panel expand" id="acc_content_a1" aria-labelledby="acc_header_a1">
                                <div class="c-accordion__inside">
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                        <li class="c-text-list__item u-co-gray">요금제 변경 이후의 요금제에 따라 결합 데이터의 제공량이 달라지거나, 결합이 해지 될 수 있습니다.</li>
                                        <li class="c-text-list__item u-co-gray">결합 약정 기간은 없으며, 해지 시 위약금은 없습니다.</li>
                                        <li class="c-text-list__item u-co-gray">결합 해지를 원하실 경우 고객센터를 (114)통해 가능합니다.<br /></li>
                                    </ul>
                                </div>
                            </div>
                        </li>
                        <li class="c-accordion__item">
                            <div class="c-accordion__head">
                                <strong class="c-accordion__title">결합 혜택 안내</strong>
                                <button class="runtime-data-insert c-accordion__button" id="acc_header_a2" type="button" aria-expanded="false" aria-controls="acc_content_a2"></button>
                            </div>
                            <div class="c-accordion__panel expand" id="acc_content_a2" aria-labelledby="acc_header_a2">
                                <div class="c-accordion__inside">
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                        <li class="c-text-list__item u-co-gray">아무나 결합 또는 아무나 가족결합+ 에 가입되어 있으실 경우 결합 혜택은 중복 제공되지 않습니다.</li>
                                        <li class="c-text-list__item u-co-gray">결합 시 첫 데이터는 별도의 신청 없이 즉시 제공되며, 이후에는 매월 1일에 데이터가 제공됩니다.</li>
                                        <li class="c-text-list__item u-co-gray">결합 해지 시 매월 제공되던 데이터는 중단됩니다. (단, 아무나 결합 또는 아무나 가족결합+ 가입 및 결합 혜택 제공 요금제 가입 상태일 경우 무료 결합 데이터 혜택 제공은 유지됩니다.)</li>
                                        <li class="c-text-list__item u-co-gray">결합 시 제공되는 데이터는 테더링으로 사용 가능하며, 함께쓰기, 데이터쉐어링, 로밍으로는 사용 불가합니다.</li>
                                        <li class="c-text-list__item u-co-gray">당월 내 아무나 SOLO 결합 가입 이력이 있을 경우 결합 신청이 불가합니다.</li>
                                    </ul>
                                </div>
                            </div>
                        </li>
                        <li class="c-accordion__item">
                            <div class="c-accordion__head">
                                <strong class="c-accordion__title">결합 불가 안내</strong>
                                <button class="runtime-data-insert c-accordion__button" id="acc_header_a3" type="button" aria-expanded="false" aria-controls="acc_content_a3"></button>
                            </div>
                            <div class="c-accordion__panel expand" id="acc_content_a3" aria-labelledby="acc_header_a3">
                                <div class="c-accordion__inside">
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                        <li class="c-text-list__item u-co-gray">개인사업자, 법인은 결합이 불가합니다.</li>
                                        <li class="c-text-list__item u-co-gray">정지 상태의 회선은 결합이 불가합니다.</li>
                                        <li class="c-text-list__item u-co-gray">아무나 SOLO 결합 대상 요금제 외 상품은 가입 불가합니다.</li>
                                        <li class="c-text-list__item u-co-gray">결합 해지 후 해지한 당월에는 재가입이 불가합니다.</li>
                                    </ul>
                                </div>
                            </div>
                        </li>
                        <li class="c-accordion__item">
                            <div class="c-accordion__head">
                                <strong class="c-accordion__title">결합 가능 요금제</strong>
                                <button class="runtime-data-insert c-accordion__button" id="acc_header_a4" type="button" aria-expanded="false" aria-controls="acc_content_a4"></button>
                            </div>
                            <div class="c-accordion__panel expand" id="acc_content_a4" aria-labelledby="acc_header_a4">
                                <div class="c-accordion__inside" id="combineSelfPlan">

                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

    </t:putAttribute>

    <t:putAttribute name="popLayerAttr">

        <!-- 결합 완료 팝업 -->
        <div class="c-modal c-modal--lg" id="combinComplete" role="dialog" aria-labelledby="combinCompleteTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="combine-complete__wrap">
                            <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                            <p class="combine-complete__txt">
                                <span class="u-co-mint">결합이 완료되었습니다.</span>
                            </p>
                        </div>
                        <ul class="c-text-list c-bullet c-bullet--fyr u-fs-15">
                            <li class="c-text-list__item u-co-gray">결합 완료 시 마이페이지 ‘결합 내역 조회’를 통해 결합 완료를 확인 해 주세요.</li>
                            <li class="c-text-list__item u-co-gray">결합 해지를 원하실 경우 고객센터(114)를 통해 신청 바랍니다.</li>
                        </ul>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--gray u-width--220 _complete" data-dialog-close>확인</button>
                            <a class="c-button c-button--lg c-button--primary u-width--220" href="/mypage/myCombineList.do" title="결합 내역 조회 페이지 바로가기">결합 내역 조회</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 결합 완료 트리플 팝업 -->
        <div class="c-modal c-modal--lg" id="combinCompleteTriple" role="dialog" aria-labelledby="combinCompleteTripleTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="combine-complete__wrap">
                            <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                            <p class="combine-complete__txt">
                                <span class="u-co-mint">결합이 완료되었습니다.</span>
                            </p>
                            <div class="c-text-box c-box--type1 u-mt--24 u-pa--32">
                                <p class="combine-complete__txt u-lh--inherit u-mt--0">
                                    KT인터넷을 신규로 가입하고<br /><span class="u-co-red">추가 요금할인</span>을 받으시는 건 어떠세요?
                                </p>
                                <p class="combine-complete__txt u-co-mint u-fs-20 u-lh--inherit u-mt--24">
                                    KT인터넷을 신규로 가입하고 M모바일 결합 시<br />인터넷에서 추가할인을 받으실 수 있습니다.
                                </p>
                                <p class="combine-complete__subtxt">※ 결합 할인 금액 등은 트리플할인 안내 페이지를 참고 바랍니다.</p>
                            </div>
                        </div>
                        <ul class="c-text-list c-bullet c-bullet--fyr u-fs-15">
                            <li class="c-text-list__item u-co-gray">결합 완료 시 마이페이지 ‘결합 내역 조회’를 통해 결합 완료를 확인 해 주세요.</li>
                            <li class="c-text-list__item u-co-gray">결합 해지를 원하실 경우 고객센터(114)를 통해 신청 바랍니다.</li>
                        </ul>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--gray u-width--220 _complete" data-dialog-close>확인</button>
                            <a class="c-button c-button--lg c-button--primary u-width--220" href="/content/ktDcInfo.do" title="트리플할인 안내 페이지 바로가기">트리플할인 안내</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 휴대폰 인증하기  팝업 -->
        <div class="c-modal c-modal--xlg" id="divCertifySms" role="dialog" aria-labelledby="divCertifySmsTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="divCertifySmsTitle">휴대폰 인증</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <%@ include file="/WEB-INF/views/portal/mypage/sendCertSms.jsp"%>
                        <div class="c-notice-wrap">
                            <h5 class="c-notice__title">
                                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                <span>알려드립니다</span>
                            </h5>
                            <ul class="c-notice__list">
                                <li>SMS(단문메시지)는 시스템 사정에 따라 다소 지연될 수 있습니다.</li>
                                <li>인증번호는 1899-5000로 발송되오니 해당 번호를 단말기 스팸 설정 여부 및 스팸편지함을 확인 해 주세요.</li>
                            </ul>
                        </div>
                        <div class="c-button-wrap u-mt--48">
                            <button type="button" class="c-button c-button--lg c-button--primary c-button--w460 is-disabled" href="javascript:void(0);" id="btnOk">가입정보 확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>

</t:insertDefinition>

