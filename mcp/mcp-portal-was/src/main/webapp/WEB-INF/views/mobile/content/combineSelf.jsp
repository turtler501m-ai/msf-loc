<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="titleAttr">아무나 SOLO 결합</t:putAttribute>


    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/content/combineSelf.js?version=25-03-12"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">아무나 SOLO 결합<button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="combine-self-banner">
                <div class="combine-self-banner__wrap">
                    <strong>혼자 사용해도 최대 20GB</strong>
                    <p>혼자서도 고민할 필요 없이 결합 가능!</p>
                    <a data-scroll-top="#combineSelf|100">
                            결합신청
                       <i class="c-icon" aria-hidden="true"></i>
                    </a>
                </div>
                <div class="combine-self-banner__image">
                    <img src="/resources/images/mobile/product/combine_self_banner.png" alt="">
                </div>
            </div>
            <div class="combine-head">
                <div class="combine-self-service__wrap">
                    <ul class="combine-self-service__list">
                        <li class="combine-self-service__item">
                            <div class="combine-self-service__img">
                                <img src="/resources/images/mobile/product/combine_self_service_01.png">
                            </div>
                            <div class="combine-self-service__text">
                                <p>결합할 사람을<br />못 찾으셨나요?</p>
                            </div>
                        </li>
                        <li class="combine-self-service__item">
                            <div class="combine-self-service__img">
                                <img src="/resources/images/mobile/product/combine_self_service_02.png">
                            </div>
                            <div class="combine-self-service__text">
                                <p>아무나 찾지 않아도<br />혼자 결합 가능!</p>
                            </div>
                        </li>
                        <li class="combine-self-service__item">
                            <div class="combine-self-service__img">
                                <img src="/resources/images/mobile/product/combine_self_service_03.png">
                            </div>
                            <div class="combine-self-service__text">
                                <p>쉽게 결합하고<br />최대 20GB 받으세요</p>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="combine-self-offer">
                <div class="combine-self-offer__wrap">
                    <p>아무나 SOLO 결합 대상 요금제를 사용하고 있다면</p>
                    <p class="combine-self-offer__title">
                        <b>최대 <span>20GB</span>까지</b><br />데이터를 추가로 제공해드려요
                    </p>
                    <img src="/resources/images/mobile/product/combine_self_guide_offer.png">
                </div>
            </div>
            <div class="combine-head">
                <p class="combine-head__title"><b>아무나 SOLO 결합</b><br />대상 요금제</p>
                <p class="combine-head__text">아무나 SOLO 결합시<br /> 요금제별 제공 혜택을 확인 해 보세요</p>

                <div class="combine-head-anyone" id="combineSelfTable">

                </div>
            </div>
            <div class="self-guide-wrap--type2 c-expand" id="combineSelfGuide">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                        <div class="swiper-slide">
                            <img src="/resources/images/mobile/product/combine_self_guide_01.png" alt="아무나 SOLO 결합 신청 방법 결합된 내역은 마이페이지 결합 내역 조회에서 확인이 가능합니다.">
                        </div>
                        <div class="swiper-slide">
                            <img src="/resources/images/mobile/product/combine_self_guide_02.png" alt="1. 가입정보확인, 로그인 또는 휴대폰 인증을 통해 결합 가능 여부를 확인 해 보세요. 다이렉트몰 아이디로 로그인하고 간편하게 결합하세요.">
                        </div>
                        <div class="swiper-slide">
                            <img src="/resources/images/mobile/product/combine_self_guide_03.png" alt="2. 결합 신청, 결합 가능 여부를 확인 후 결합 신청 버튼을 클릭 해 주세요.">
                        </div>
                        <div class="swiper-slide">
                            <img src="/resources/images/mobile/product/combine_self_guide_04.png" alt="3. 결합 확인, 결합 신청 후 결합 내역 조회를 통해 결합 완료를 확인 해 주세요.">
                        </div>
                    </div>
                    <div class="swiper-pagination u-mb--4"></div>
                </div>
            </div>

                   <!-- 유튜브 영상 영역 -->
                <div id="combineSolo">

                </div>

            <div class="combine-self" id="combineSelf">
                <!-- 본인인증 방법 선택 -->
                <div id="divCheckSession" <c:if test="${nmcp:hasLoginUserSessionBean()}">style="display: none"</c:if> >
                    <p class="u-fw--bold u-ta-center">정보 확인을 위해 인증 방법을 선택 후<br />결합을 신청하세요.</p>
                    <div class="c-button-wrap u-mt--32">
                        <a id="btnLogin" class="c-button c-button--full c-button--primary" href="javascript:void(0);"title="로그인 페이지 바로가기">로그인 하기</a>
                        <button type="button" class="c-button c-button--full c-button--primary" data-dialog-trigger="#divCertifySms">휴대폰 인증하기</button>
                    </div>
                </div>

                <!-- 본인인증 후 -->
                <div id="divResult" <c:if test="${!nmcp:hasLoginUserSessionBean()}">style="display: none"</c:if>>
                    <c:if test="${empty maskingSession}">
                      <div class="masking-badge-wrap">
                          <div class="masking-badge" style="top: -2.125rem;">
                                 <a class="masking-badge__button" href="/m/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
                                <i class="masking-badge__icon" aria-hidden="true"></i>
                                   <p>가려진(*) 정보보기</p>
                              </a>
                          </div>
                      </div>
                    </c:if>
                    <div class="combine-myinfo">
                        <div class="combine-myinfo__tit">
                               조회할 회선을 선택해 주세요.
                        </div>
                        <div class="c-form__group" role="group">
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
                            <div class="c-form__input has-value">
                                <input type="text" class="c-input" id="rateNm" name="rateNm"   placeholder="사용중인 요금제" readonly>
                                <label class="c-form__label" for="rateNm">요금제</label>
                            </div>
                        </div>
                        <%--<div class="combine-myinfo__btn">
                            <button type="button" id="btnCheckCombine" class="c-button">조회</button>
                        </div>--%>
                    </div>


                    <div id="divResultInfo">
                        <!-- 아무나 SOLO 결합이 되어있는 경우  -->
                        <div>
                            <div class="c-text-box c-text-box--red u-mt--40 u-pa--24 u-ta-center">
                                <p class="combine-complete__txt u-co-red u-fs-18 u-mt--0">
                                       이미 신청 이력이 존재합니다.
                                </p>
                                <p class="combine-complete__subtxt u-fs-14 u-ta-center">결합 내역은 마이페이지에서 확인이 가능합니다.</p>
                            </div>
                            <div class="c-button-wrap">
                                <a class="c-button c-button--full c-button--primary" href="/mypage/myCombineList.do" title="결합 내역 조회 페이지 바로가기">결합 내역 조회</a>
                            </div>
                        </div>

                    </div>

                    <div id="divReg"  style="display: none">
                        <div class="combine-agree c-agree">
                            <div class="c-agree__item">
                                <input type="checkbox" class="c-checkbox" id="chkAgree" name="chkAgree" value="">
                                <label class="c-label" for="chkAgree">결합을 위한 필수 확인사항 동의 (필수)</label>
                                <button type="button" class="c-button c-button--xsm" onclick="fnSetEventId('chkAgree');openPage('termsPop','/termsPop.do','cdGroupId1=TERMSCOM&cdGroupId2=TERMSCOM06',0);" >
                                    <span class="c-hidden">결합을 위한 필수 확인사항 동의  (필수) 상세보기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                </button>
                            </div>
                        </div>
                        <div class="combine-sign">
                            <button type="button" class="c-button is-disabled" id="btnRegCombin">결합 신청</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="swiper-container swiper-event-banner friend-banner c-expand" id="swiperCombineSelf">
                <div class="swiper-wrapper">
                </div>
            </div>

            <h4 class="c-heading c-heading--type5 u-mt--48">유의사항</h4>
            <hr class="c-hr c-hr--type3">
            <div class="c-accordion c-accordion--type1">
                <ul class="c-accordion__box" id="accordionB">
                    <li class="c-accordion__item">
                        <div class="c-accordion__head" data-acc-header>
                            <button class="c-accordion__button" type="button" aria-expanded="false">결합 해지 안내</button>
                        </div>
                        <div class="c-accordion__panel expand c-expand">
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
                        <div class="c-accordion__head" data-acc-header>
                            <button class="c-accordion__button" type="button" aria-expanded="false">결합 혜택 안내</button>
                        </div>
                        <div class="c-accordion__panel expand c-expand">
                            <div class="c-accordion__inside">
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-gray">아무나 결합 또는 아무나 가족결합+ 에 가입되어 있으실 경우 결합 혜택은 중복 제공되지 않습니다.</li>
                                    <li class="c-text-list__item u-co-gray">결합 시 첫 데이터는 별도의 신청 없이 즉시 제공되며, 이후에는 매월 1일에 데이터가 제공됩니다.</li>
                                    <li class="c-text-list__item u-co-gray">결합 해지 시 매월 제공되던 데이터는 중단됩니다. (단, 아무나 결합 또는 아무나 가족결합+ 가입 및 결합 혜택 제공 요금제 가입 상태일 경우 무료 결합 데이터 혜택 제공은 유지됩니다.)</li>
                                    <li class="c-text-list__item u-co-gray">결합 시 제공되는 데이터는 테더링으로 사용 가능하며, 함께쓰기, 데이터쉐어링, 로밍으로는 사용 불가합니다.</li>
                                    <li class="c-text-list__item u-co-gray">미사용 데이터는 이월 불가합니다.</li>
                                </ul>
                            </div>
                        </div>
                    </li>
                    <li class="c-accordion__item">
                        <div class="c-accordion__head" data-acc-header>
                            <button class="c-accordion__button" type="button" aria-expanded="false">결합 불가 안내</button>
                        </div>
                        <div class="c-accordion__panel expand c-expand">
                            <div class="c-accordion__inside">
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-gray">개인사업자, 법인은 결합이 불가합니다.</li>
                                    <li class="c-text-list__item u-co-gray">정지 상태의 회선은 결합이 불가합니다.</li>
                                    <li class="c-text-list__item u-co-gray">아무나 SOLO 결합 대상 요금제 외 상품은 가입 불가합니다.</li>
                                    <li class="c-text-list__item u-co-gray">당월 내 아무나 SOLO 결합 가입 이력이 있을 경우 결합 신청이 불가합니다.</li>
                                </ul>
                            </div>
                        </div>
                    </li>
                    <li class="c-accordion__item">
                        <div class="c-accordion__head" data-acc-header>
                            <button class="c-accordion__button" type="button" aria-expanded="false">결합 가능 요금제</button>
                        </div>
                        <div class="c-accordion__panel expand c-expand">
                            <div class="c-accordion__inside" id="combineSelfPlan">

                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>



    </t:putAttribute>


    <t:putAttribute name="popLayerAttr">

        <!-- 휴대폰 인증 팝업 -->
        <div class="c-modal" id="divCertifySms" role="dialog" tabindex="-1" aria-describedby="authPhoneTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="authPhoneTitle">휴대폰 인증</h1>
                        <button class="c-button" data-dialog-close="">
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <input type="hidden" name="ncType" id="ncType" value="0"/>
                        <%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
                        <div class="c-notice-wrap">
                            <hr>
                            <h5 class="c-notice__title">
                                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                <span>알려드립니다</span>
                            </h5>
                            <ul class="c-notice__list">
                                <li>SMS(단문메시지)는 시스템 사정에 따라 다소 지연될 수 있습니다.</li>
                                <li>인증번호는 1899-5000로 발송되오니 해당 번호를 단말기 스팸 설정 여부 및 스팸편지함을 확인 해 주세요.</li>
                            </ul>
                        </div>
                        <div class="btn-usercheck">
                            <button type="button" class="c-button is-disabled" href="javascript:void(0);"  id="btnOk" >가입정보 확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 결합 완료 팝업 -->
        <div class="c-modal" id="combinComplete" role="dialog" tabindex="-1" aria-describedby="combinCompleteTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="combinCompleteTitle">결합 완료</h1>

                    </div>
                    <div class="c-modal__body">
                        <div class="combine-complete__wrap u-ta-center">
                            <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                            <p class="combine-complete__txt">
                                <span class="u-co-mint">결합이 완료되었습니다.</span>
                            </p>
                        </div>
                        <ul class="c-text-list c-bullet c-bullet--fyr">
                            <li class="c-text-list__item u-co-gray">결합 완료 시 마이페이지 ‘결합 내역 조회’를 통해 결합 완료를 확인 해 주세요.</li>
                            <li class="c-text-list__item u-co-gray">결합 해지를 원하실 경우 고객센터(114)를 통해 신청 바랍니다.</li>
                        </ul>
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--gray _complete" data-dialog-close>확인</button>
                            <a class="c-button c-button--full c-button--primary" href="/mypage/myCombineList.do" title="결합 내역 조회 페이지 바로가기">결합 내역 조회</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 결합 완료 트리플 팝업 -->
        <div class="c-modal" id="combinCompleteTriple" role="dialog" tabindex="-1" aria-describedby="combinCompleteTripleTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="combinCompleteTripleTitle">결합 완료</h1>

                    </div>
                    <div class="c-modal__body">
                        <div class="combine-complete__wrap u-ta-center">
                            <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                            <p class="combine-complete__txt">
                                <span class="u-co-mint">결합이 완료되었습니다.</span>
                            </p>
                            <div class="c-box c-box--type1 u-mt--24 u-pa--24">
                                <p class="combine-complete__txt u-fs-18 u-mt--0">
                                    KT인터넷을 신규로 가입하고<br><span class="u-co-red">추가 요금할인</span>을 받으시는 건 어떠세요?
                                </p>
                                <p class="combine-complete__txt u-co-mint u-fs-16 u-mt--24">
                                    KT인터넷을 신규로 가입하고<br />M모바일 결합 시 인터넷에서 추가할인을<br />받으실 수 있습니다.
                                </p>
                                <p class="combine-complete__subtxt u-fs-14 u-ta-center">※ 결합 할인 금액 등은 트리플할인 안내 페이지를 참고 바랍니다.</p>
                            </div>
                        </div>
                        <ul class="c-text-list c-bullet c-bullet--fyr u-mt--8">
                            <li class="c-text-list__item u-co-gray">결합 완료 시 마이페이지 ‘결합 내역 조회’를 통해 결합 완료를 확인 해 주세요.</li>
                            <li class="c-text-list__item u-co-gray">결합 해지를 원하실 경우 고객센터(114)를 통해 신청 바랍니다.</li>
                        </ul>
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--gray _complete" data-dialog-close>확인</button>
                            <a class="c-button c-button--full c-button--primary" href="/m/content/ktDcInfo.do" title="트리플할인 안내 페이지 바로가기">트리플할인 안내</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>


</t:insertDefinition>