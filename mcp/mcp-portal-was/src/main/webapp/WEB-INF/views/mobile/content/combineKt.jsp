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

    <t:putAttribute name="titleAttr">아무나 가족 결합+</t:putAttribute>


    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/validator.js?version=22.02.22"></script>
        <script type="text/javascript" src="/resources/js/mobile/content/combineKt.js?version=23.12.12"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" name="ncType" id="ncType" value="0"/>
        <input type="hidden" id="prcsMdlInd" name="prcsMdlInd" value="<fmt:formatDate value="${nmcp:getDateToCurrent(0)}" pattern="yyMMddHHmmss" />" >
        <div class="ly-content" id="main-content">

            <div class="ly-page-sticky">
                <h2 class="ly-page__head">아무나 가족결합+<button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="kt-combine-banner _regForm">
                <div class="kt-combine-banner__wrap">
                    <strong>가족이든 혼자든<br/><span>KT와 함께하면 할인</span>도 데이터도 “+”</strong>
                    <button class="c-button c-button--sm upper-banner__anchor" data-scroll-top=".combine-self|100">결합 신청 바로가기<i class="c-icon c-icon--anchor-white" aria-hidden="true"></i></button>

                </div>
                <div class="kt-combine-banner__image">
                    <img src="/resources/images/mobile/product/kt_combine_banner_mo.png" alt="">
                </div>
            </div>
            <div class="kt-combine-content _regForm u-mt--34">
                <div class="kt-combine-head">
                  <p class="kt-combine__title">아무나 가족결합+ 란?</p>
                  <p class="kt-combine__text">kt M모바일을 사용하는 고객이 KT인터넷 또는 KT모바일 상품과 결합하여 추가혜택(무료 데이터 및 요금할인)을 받는 서비스입니다.</p>
                  <p class="kt-combine__subtext">단, KT상품과 M모바일의 회선 명의가 같거나 가족관계인 경우에만 결합 할 수 있습니다.</p>
                </div>
                <div class="kt-combine-wrap">
                    <h4 class="kt-combine-title"><i class="c-icon c-icon--home" aria-hidden="true"></i>결합 대상</h4>
                    <p class="kt-combine-text">Kt M모바일 가입자와 KT상품 신규 가입 고객(가입 후 익월 말까지 결합 완료 시 혜택 제공)</p>
                    <ul class="c-text-list c-bullet c-bullet--dot u-fs-16">
                        <li class="c-text-list__item u-co-gray">명의가 같다면 즉시 결합 가능, 명의가 다르면 가족관계증명서 등 서류 제출 필요</li>
                    </ul>
                </div>
                <div class="kt-combine-wrap">
                    <h4 class="kt-combine-title"><i class="c-icon c-icon--home" aria-hidden="true"></i>결합 혜택</h4>
                    <ul class="kt-combine-product-box">
                        <li class="kt-combine-product-box__item">
                            <p class="kt-combine-product-box__title">kt M모바일 혜택</p>
                            <ul class="c-text-list c-bullet c-bullet--dot u-fs-14">
                                <li class="c-text-list__item">요금제에 따라 <span>매월 무료 데이터 제공</span></li>
                            </ul>
                            <p class="kt-combine-product-box__text">
                                <i class="c-icon c-icon--bang--gray2 u-mr--4 u-mt--m2" aria-hidden="true"></i>일부 요금제는 무료 데이터 제공 없음
                            </p>
                            <div class="c-button-wrap u-mt--24">
                                <button type="button" class="c-button" onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${Constants.COMB_FAM_LINK_CD}');" title="대상 요금제 확인">대상 요금제 확인</button>
                            </div>
                        </li>
                        <li class="kt-combine-product-box__item">
                            <p class="kt-combine-product-box__title">KT모바일 · 인터넷 혜택</p>
                            <ul class="c-text-list c-bullet c-bullet--dot u-fs-14">
                                <li class="c-text-list__item"><span>월 요금 할인 적용</span></li>
                            </ul>
                            <p class="kt-combine-product-box__text">
                                <i class="c-icon c-icon--bang--gray2 u-mr--4 u-mt--m2" aria-hidden="true"></i>일부 요금제는 무료 데이터 제공 없음
                            </p>
                            <div class="c-button-wrap u-mt--24">
                                <button type="button" class="c-button" onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=COMB00018');" title="결합할인 자세히보기">결합할인 자세히보기</button>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="kt-combine-wrap">
                  <h4 class="kt-combine-title"><i class="c-icon c-icon--home" aria-hidden="true"></i>자주 묻는 질문</h4>
                  <div class="c-accordion kt-combine" data-ui-accordion>
                    <div class="c-accordion__box">
                      <div class="c-accordion__item">
                        <div class="c-accordion__head">
                          <div class="kt-combine-adv-box" id="acc_ktCombine_header">
                            <div class="kt-combine-adv__item">
                              <div class="kt-combine-adv__question">
                                <span>Q</span>
                                <p>KT인터넷 신규 상품만 결합되나요? 기존 사용자나 재약정은 안되나요?</p>
                              </div>
                              <div class="kt-combine-adv__answer">
                                <span>A</span>
                                <p>네, 안타깝게도 신규 상품만 결합 대상으로 적용됩니다.</p>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="c-accordion__panel expand" id="acc_ktCombine_content" aria-labelledby="acc_ktCombine_header">
                          <div class="c-accordion__inside">
                            <div class="kt-combine-adv-box">
                              <div class="kt-combine-adv__item">
                                 <div class="kt-combine-adv__question">
                                   <span>Q</span>
                                   <p>결합을 하려면 MVNO 정보제공동의가 필요하다는데 MVNO 정보제공동의가 무엇인가요?</p>
                                 </div>
                                 <div class="kt-combine-adv__answer">
                                   <span>A</span>
                                   <p>KT고객의 정보를 M모바일(MVNO)로 제공함을 동의하는 절차입니다.<br />해당 동의가 있어야만 결합 서비스 신청이 가능하며, 미동의 시 결합 서비스 신청이 불가합니다. 정보제공동의 방법은 신청절차를 참고해 주세요.</p>
                                 </div>
                              </div>
                              <div class="kt-combine-adv__item">
                                 <div class="kt-combine-adv__question">
                                   <span>Q</span>
                                   <p>KT인터넷+KT TV+KT모바일 가족명의결합 고객이 M모바일 회선과 결합을 희망하는 경우 MVNO정보제공동의는 가족명의 모두 받아야 하나요?</p>
                                 </div>
                                 <div class="kt-combine-adv__answer">
                                   <span>A</span>
                                   <p>네, 정보제공동의는 KT결합 구성원 모두 받아야 M모바일 회선과 결합이 가능합니다.</p>
                                 </div>
                              </div>
                              <div class="kt-combine-adv__item">
                                 <div class="kt-combine-adv__question">
                                   <span>Q</span>
                                   <p>인터넷과 TV결합 시 각각 MVNO 정보 제공 동의가 필요하다고 하는데 필요한 이유가 무엇인가요?</p>
                                 </div>
                                 <div class="kt-combine-adv__answer">
                                   <span>A</span>
                                   <p>인터넷과 TV상품이 같은 상품 군으로 구분되지 않아 저희가 정보를 조회하기 위해서는 각각의 동의 절차가 필요합니다.</p>
                                 </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <button class="c-accordion__button" type="button" aria-expanded="false" aria-controls="acc_ktCombine_content">
                          <span>더 알아보기<i class="c-icon c-icon--triangle-bottom" aria-hidden="true"></i></span>
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="kt-combine-wrap">
                  <h4 class="kt-combine-title">
                    <i class="c-icon c-icon--home" aria-hidden="true"></i>신청 절차
                    <c:if test="${combineVideo ne null}">
                      <div class="kt-combine-title__button">
                        <button type="button" class="c-button _btnVideo" data-iframe-src="${combineVideo.pcLinkUrl}" data-title="${combineVideo.ntcartTitle}"><i class="c-icon c-icon--play-type2" aria-hidden="true"></i>동영상으로 보기</button>
                      </div>
                    </c:if>
                  </h4>
                  <p class="c-notice--red">
                    <span><i class="c-icon c-icon--bang--red" aria-hidden="true"></i></span>
                    <span>결합을 위해서는 정보제공동의가 필요합니다.<button type="button" class="c-text-link--red" data-dialog-trigger="#combinInfoAgree">정보제공동의란?</button></span>
                  </p>
                  <ul class="kt-combine-step">
                    <li class="kt-combine-step__item">
                      <div class="kt-combine-step__title">
                        <div class="kt-combine-step__icon">
                          <i class="c-icon c-icon--man" aria-hidden="true"></i>
                        </div>
                        <div>
	                        <b>본인 명의</b>
	                        <p>(즉시 결합 가능)</p>
                        </div>
                      </div>
                      <div class="kt-combine-step__desc">
                        <ul class="c-text-list c-bullet c-bullet--dot-number">
                          <li class="c-text-list__item">kt M모바일 홈페이지 로그인 또는 휴대폰 인증</li>
                          <li class="c-text-list__item">‘아무나 가족 결합+’ 신청 버튼 클릭</li>
                          <li class="c-text-list__item">즉시 결합 완료</li>
                        </ul>
                      </div>
                    </li>
                    <li class="kt-combine-step__item">
                      <div class="kt-combine-step__title">
                        <div class="kt-combine-step__icon">
                            <i class="c-icon c-icon--family" aria-hidden="true"></i>
                        </div>
                        <div>
	                        <b>가족 명의</b>
	                        <p>(추가 절차 필요)</p>
                        </div>
                      </div>
                      <div class="kt-combine-step__desc">
                        <ul class="c-text-list c-bullet c-bullet--dot-number">
                          <li class="c-text-list__item">kt M모바일 홈페이지 로그인 또는 휴대폰 인증</li>
                          <li class="c-text-list__item">결합 대상 가족 정보 입력(가족명의 인터넷 결합 시 ID입력 필요)<br/><span>※</span><button type="button" class="c-text c-text--underline" data-dialog-trigger="#ktInternetIdModal">가족명의 KT인터넷ID 확인 방법</button></li>
                          <li class="c-text-list__item">‘아무나 가족 결합+’ 신청 버튼 클릭</li>
                          <li class="c-text-list__item">3일 이내 구비 서류 제출(이메일 ktm.vit@kt.com/팩스 070-4275-2483)<br/><span>※</span><button type="button" class="c-text c-text--underline" data-dialog-trigger="#documentModal">구비서류 확인하기</button></li>
                          <li class="c-text-list__item">서류 검토 후 문자로 결과 안내(제출일 기준 3일 이내)</li>
                        </ul>
                      </div>
                    </li>
                  </ul>
                </div>
                <div class="combine-self u-mt--60">
                    <!-- 본인인증 방법 선택 -->
                    <div id="divStep1" <c:if test="${nmcp:hasLoginUserSessionBean()}">style="display: none"</c:if> >
                        <p class="u-fw--bold u-fs-22 u-ta-center">결합 신청하기</p>
                        <p class="u-fs-13 u-ta-center u-mt--8">정보 확인을 위해 인증 방법을 선택 후 결합을 신청하세요.</p>
                        <div class="c-button-wrap c-button-wrap--column u-mt--42">
                            <a id="btnLogin" class="c-button c-button--lg c-button--primary" href="javascript:void(0);"  title="로그인 페이지 바로가기">로그인 하기</a>
                            <button type="button" class="c-button c-button--lg c-button--white"  title="휴대폰인증하기" data-dialog-trigger="#divCertifySms">휴대폰 인증하기</button>
                        </div>
                    </div>
                </div>

                <!-- 결합 정보 영역 -->
                <div id="divStep2" class="combine-info" <c:if test="${!nmcp:hasLoginUserSessionBean()}">style="display: none"</c:if>  >
                    <!-- 상단 체크 영역 -->
                    <div class="combine-checklist">
                        <p>※ 신청 전 필수 확인 사항</p>
                        <ul class="combine-checklist__list">
                            <li>KT인터넷은 신규 가입고객만 kt M모바일 회선과 결합이 가능하여, KT인터넷 가입일 기준 익월 말 이내에 아무나 가족 결합+ 신청을 하셔야 KT인터넷에 요금할인이 적용됩니다.<br/>(ex. 3월 1일에 KT상품을 가입한 고객은 4월 30일까지 결합 신청을 해야 함)</li>
                            <li>KT인터넷과 kt M모바일 회선 결합 시, KT인터넷 서비스 ID 정보가 필요하므로 마이케이티APP 및 KT고객센터를 통해서 『인터넷서비스ID』확인 바랍니다.</li>
                            <li>KT모바일은 신규,기존 고객 모두 kt M모바일 회선과 결합이 가능하나, KT모바일 가입일 기준 익월 말 이내에 아무나 가족 결합+ 신청을 하셔야 KT모바일에 요금할인이 적용됩니다.</li>
                            <li>KT인터넷 또는 KT모바일과 kt M모바일 회선과 결합 시, KT고객은 KT고객센터를 통해 『MVNO정보제공동의』가 필요합니다.&ensp;<button type="button" class="c-text-link--bluegreen" data-dialog-trigger="#combinInfoAgree">자세히 보기</button></li>
                            <li>결합 신청 완료 후, 가족명의 결합의 경우 구비서류(결합신청자 신분증, 결합대상자 신분증, 가족관계증명서 ※미성년자의 경우 법정대리인 신분증 제출)를 이메일(ktm.vit@kt.com) 또는 팩스(070-4275-2483)로 신청일 기준 3일이내로 전달 주셔야 합니다. ※신분증은 주민번호 뒷자리 마스킹필수</li>
                            <li>결합 신청 시 지정한 결합 기간(1~3년) 만료 전에 KT인터넷 상품 해지 시 위약금이 발생됩니다.</li>
                        </ul>
                        <div>
                            <input type="checkbox" class="c-checkbox c-checkbox--type3" id="chkPreAgree" name="chkPreAgree" value="Y">
                            <label class="c-label" for="chkPreAgree">신청 전 필수 확인 사항을 확인 했습니다.</label>
                        </div>
                    </div>

                    <!-- 내정보 영역 -->
                    <c:if test="${empty maskingSession}">
                        <div class="masking-badge-wrap">
                            <div class="masking-badge" style="top: -2.5rem;">
                                <a class="masking-badge__button" href="/m/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
                                    <i class="masking-badge__icon" aria-hidden="true"></i>
                                    <p>가려진(*) 정보보기</p>
                                </a>
                            </div>
                        </div>
                    </c:if>
                    <div class="combine-myinfo">
                        <div class="combine-myinfo__tit u-fs-20">
                            내 정보
                            <button id="btnCombiSvcList" type="button" class="c-button" >결합내역 조회<i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i></button>
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
                                <input type="text" class="c-input" id="rateNm" name="rateNm" value="" readonly>
                                <label class="c-form__label" for="rateNm">요금제</label>
                            </div>
                        </div>
                        <div class="combine-myinfo__btn">
                            <button id="btnCheckCombine"  type="button" class="c-button is-disabled">조회</button>
                        </div>
                    </div>

                    <!-- 결합 여부 출력 영역 -->
                    <div class="combine-result" style="display: none">
                        <!-- <div class="combine-result-possible">
                            <b>가능한 회선입니다.</b>
                            <img src="/resources/images/mobile/product/combine_possible_mo.png" alt="">
                        </div>
                        <div class="combine-result-impossible">
                            <p>Kt M모바일</p>
                            <b>고객만 결합이 가능합니다.</b>
                            <img src="/resources/images/mobile/product/combine_impossible_mo.png" alt="">
                        </div> -->
                    </div>

                    <!-- 결합 가능할때 출력 영역 -->
                    <div id="divChild" class="combine-form" style="display: none" >



                        <div class="c-form">
                            <span class="c-form__title--type2">1. 결합 명의</span>
                            <div class="c-check-wrap c-check-wrap--type3" role="group" aria-labelledby="sameCustInfo">

                                <div class="c-chk-wrap">
                                    <input type="radio" class="c-radio c-radio--button" id="sameCustYn01" name="sameCustYn" value="Y" >
                                    <label class="c-label u-ta-center" for="sameCustYn01">본인명의</label>
                                </div>
                                <div class="c-chk-wrap">
                                    <input type="radio" class="c-radio c-radio--button" id="sameCustYn02" name="sameCustYn" value="N">
                                    <label class="c-label u-ta-center" for="sameCustYn02">가족명의</label>
                                </div>
                            </div>
                        </div>


                        <div class="c-form">
                            <span class="c-form__title--type2">2. 결합 상품 선택</span>
                            <p class="c-notice--red">
                                <span>
                                    <i class="c-icon c-icon--bang--red" aria-hidden="true"></i>
                                </span>
                                <span>결합을 위해서는 정보제공동의가 필요합니다.&ensp;<button type="button" class="c-text-link--red" data-dialog-trigger="#combinInfoAgree">정보제공동의란?</button></span>
                            </p>
                            <div class="c-check-wrap c-check-wrap--type3" role="group" aria-labelledby="sameCustInfo">
                                <div class="c-check-wrap" role="group" aria-labelledby="radRegistType">
                                    <div class="c-chk-wrap">
                                        <input type="radio" class="c-radio c-radio--button" id="svcNoTypeCd01" name="svcNoTypeCd" value="MB" >
                                        <label class="c-label u-ta-center" for="svcNoTypeCd01">KT모바일결합</label>
                                    </div>
                                    <div class="c-chk-wrap">
                                        <input type="radio" class="c-radio c-radio--button" id="svcNoTypeCd02" name="svcNoTypeCd" value="IT">
                                        <label class="c-label u-ta-center" for="svcNoTypeCd02">KT인터넷결합</label>
                                    </div>
                                </div>
                            </div>

                            <!-- 본인 명의일때 - 회선 리스트 -->
                            <div class="c-row c-row--lg" id="ktList">


                            </div>

                            <!-- 가족 명의일때 -->
                            <!-- KT모바일결합 선택 시 출력 영역 -->
                            <div id="svcNoTypeCd_MB" class="_svcNoTypeCd" style="display: none">
                                <div class="c-form__group" role="group">
                                    <div class="c-form__input">
                                        <input type="text" class="c-input" id="combUserNm" name="combUserNm" maxlength="50"  placeholder="이름 입력" aria-invalid="true">
                                        <label class="c-form__label" for="combUserNm">이름</label>
                                    </div>
                                    <div class="c-form__input">
                                        <input type="number" class="c-input numOnly" id="svcIdfyNo" name="svcIdfyNo" placeholder="ex) 19821120" maxlength="8"  aria-invalid="true">
                                        <label class="c-form__label" for="svcIdfyNo">생년월일</label>
                                    </div>
                                    <p class="c-form__text" style="display: none" >생년월일이 올바르지 않습니다.</p>
                                    <div class="c-form__select has-value">
                                        <select class="c-select" id="sexCd" name="sexCd">
                                            <option value="1" label="남자">남자</option>
                                            <option value="2" label="여자" >여자</option>
                                        </select>
                                        <label class="c-form__label" for="sexCd">성별</label>
                                    </div>
                                    <div class="c-form__input">
                                        <input type="number" class="c-input numOnly" id="ctn" name="ctn" value="" placeholder="휴대폰 번호 입력" maxlength="11" aria-invalid="true">
                                        <label class="c-form__label" for="ctn">휴대폰 번호</label>
                                    </div>
                                    <p class="c-form__text"  style="display: none">휴대폰 번호가 올바르지 않습니다.</p>

                                    <div class="c-form__input _authNum" style="display: none">
                                        <input type="number" class="c-input" id="authNum" name="authNum" maxlength="6"  placeholder="인증번호" title="인증번호 입력">
                                        <label class="c-form__label" for="authNum">인증번호</label>
                                    </div>
                                    <!-- case1 : 인증번호 확인 전-->
                                    <p class="c-form__text" id="countdown2"  style="display: none">휴대폰으로 전송된 인증번호를 5분안에 입력해 주세요.</p><!-- case2 : 인증번호 확인 후 -->
                                    <p class="c-form__text" id="countdownTime2" style="display: none">남은시간<span class="u-co-red u-ml--8" id="timer2"></span>
                                        <button class="c-button c-button--xsm c-button--underline u-ml--8 u-co-mint" id="btnMoreTime">시간연장</button>
                                    </p>
                                    <!-- case3 : 인증시간 초과-->
                                    <p class="c-form__text" id="timeover2" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
                                    <input type="hidden"    id="timeOverYn2"  name="timeOverYn2" value="N"/>
                                    <input id="isSendSms"   type="hidden"     value="" />
                                    <input id="isVerify"    type="hidden"     value="" />
                                    <div class="combine-check">
                                        <button id="btnChildCertify" type="button" class="c-button">조회</button>
                                    </div>

                                    <div class="combine-check _authNum" style="display: none">
                                        <button id="bthAuthSms"  type="button" class="c-button">인증번호 확인</button>
                                    </div>



                                </div>

                            </div>

                            <!-- KT인터넷결합 선택 시 출력 영역 -->
                            <div id="svcNoTypeCd_IT" class="_svcNoTypeCd" style="display: none">
                                <div class="c-form__group" role="group">
                                    <div class="c-form__input">
                                        <input type="text" class="c-input" id="combUserNm2" name="combUserNm2" placeholder="이름 입력" aria-invalid="true" maxlength="50">
                                        <label class="c-form__label" for="combUserNm2">이름</label>
                                    </div>
                                    <div class="c-form__input">
                                        <input type="number" class="c-input" id="svcIdfyNo2" name="svcIdfyNo2" placeholder="ex) 19821120" aria-invalid="true" maxlength="8">
                                        <label class="c-form__label" for="svcIdfyNo2">생년월일</label>
                                    </div>
                                    <p class="c-form__text" style="display: none" >생년월일이 올바르지 않습니다.</p>
                                    <div class="c-form__select has-value">
                                        <select class="c-select" id="sexCd2" name="sexCd2">
                                            <option value="1" label="남자">남자</option>
                                            <option value="2" label="여자" >여자</option>
                                        </select>
                                        <label class="c-form__label" for="sexCd2">성별</label>
                                    </div>
                                    <div class="c-form__input">
                                        <input type="text" class="c-input" id="ctn2" name="ctn2" placeholder="인터넷  서비스 ID" aria-invalid="true">
                                        <label class="c-form__label" for="ctn2">인터넷 서비스 ID</label>
                                    </div>
                                    <p class="combine-target__txt" data-dialog-trigger="#serviceInfo">
                                        <span>※ 인터넷 서비스 ID 란</span>
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </p>
                                    <div class="c-form__select has-value">
                                        <select class="c-select" id="homeCombTerm" name="homeCombTerm">
                                            <option value="3"  label="3년" >3년</option>
                                            <option value="2"  label="2년" >2년</option>
                                            <option value="1"  label="1년" >1년</option>
                                        </select>
                                        <label class="c-form__label" for="homeCombTerm">결합기간</label>
                                    </div>
                                    <div class="combine-check">
                                        <button id="btnChildCertify2"  type="button" class="c-button">조회</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- 가족명의 결합일 경우 해당 주의사항 내용 노출 영역 -->
                        <div id="divCheck" class="combine-checklist u-mt--40" style="display: none">
                            <p class="combine-checklist__tit u-co-red">
                                <i class="c-icon c-icon--bang--red" aria-hidden="true"></i>
                                <span>주의사항</span>
                            </p>
                            <ul class="combine-checklist__item">
                                <li>해당 페이지에서 결합 신청완료 한 후, 아래 서류를 신청 후 3일 이내에 이메일 (ktm.vit@kt.com) 이나 팩스(070-4275-2483)로 전달 주셔야 합니다.</li>
                                <li>결합 신청일 기준 5일이내로 서류가 도착하지 않는 경우 해당 결합 신청이 취소되며, 취소 후에 결합을 원하실 경우  재신청 바랍니다.</li>
                            </ul>
                            <p class="combine-checklist__subtit u-co-red">
                                <span>※필요(제출) 서류</span>
                            </p>
                            <ul class="combine-checklist__item no-bullet">
                                <li><b>① 주민등록등본, 건강보험증, 가족관계증명서, 혼인관계증명서, 입양관계증명서, 친양자입양관계증명서 중 택 1</b></li>
                                <li><b>② 결합신청인, 결합대상인의 신분증(주민등록증, 여권, 운전면허증, 공무원증 중 택 1)</b></li>
                                <li>
                                    ※ 주민번호 뒷 번호가 보이지 않게 마스킹 처리 바랍니다. 미처리 시 해당 자료는 폐기됩니다.<br />
                                    ※ 미성년자의 경우 법정대리인 신분증으로 제출 바랍니다.
                                </li>
                            </ul>
                        </div>

                        <!-- 약관동의 영역 -->
                        <div class="c-accordion c-accordion--type5 acc-agree">
                            <span class="c-form__title--type2">3. 약관동의</span>
                            <div class="c-accordion__box">
                                <div class="c-accordion__item">
                                    <div class="c-accordion__head">
                                        <div class="c-accordion__check">
                                            <input class="c-checkbox" id="btnStplAllCheck" type="checkbox">
                                            <label class="c-label" for="btnStplAllCheck">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.<br /><span class="u-co-red _allcheck">※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span></label>
                                        </div>
                                        <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#acc_agreeA1">
                                            <div class="c-accordion__trigger"> </div>
                                        </button>
                                    </div>
                                    <div class="c-accordion__panel expand" id="acc_agreeA1">
                                        <div class="c-accordion__inside">

                                            <div class="c-agree__item">
                                                <input type="checkbox" class="c-checkbox c-checkbox--type2 _agree" id="chkAgree" name="chkAgree" value="">
                                                <label class="c-label" for="chkAgree">결합을 위한 필수 확인사항 동의<span class="u-co-gray">(필수)</span>
                                                </label>
                                                <button class="c-button c-button--xsm" onclick="fnSetEventId('chkAgree');openPage('termsPop','/termsPop.do','cdGroupId1=TERMSCOM&cdGroupId2=TERMSCOM02',0);">
                                                    <span class="c-hidden">상세보기</span>
                                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                </button>
                                            </div>

                                            <div class="c-agree__item">
                                                <input type="checkbox" class="c-checkbox c-checkbox--type2 _agree" id="chkAgree2" name="chkAgree2" value="">
                                                <label class="c-label" for="chkAgree2">개인정보 수집 및 이용에 관한 동의<span class="u-co-gray">(필수)</span>
                                                </label>
                                                <button class="c-button c-button--xsm" onclick="fnSetEventId('chkAgree2');openPage('termsPop','/termsPop.do','cdGroupId1=TERMSCOM&cdGroupId2=TERMSCOM03',0);">
                                                    <span class="c-hidden">상세보기</span>
                                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                </button>
                                            </div>

                                            <div class="c-agree__item">
                                                <input type="checkbox" class="c-checkbox c-checkbox--type2 _agree" id="chkAgree3" name="chkAgree3" value="">
                                                <label class="c-label" for="chkAgree3">개인정보 제 3자 정보제공 동의<span class="u-co-gray">(필수)</span>
                                                </label>
                                                <button class="c-button c-button--xsm" onclick="fnSetEventId('chkAgree3');openPage('termsPop','/termsPop.do','cdGroupId1=TERMSCOM&cdGroupId2=TERMSCOM04',0);">
                                                    <span class="c-hidden">상세보기</span>
                                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                </button>
                                            </div>

                                            <div class="c-agree__item">
                                                <input type="checkbox" class="c-checkbox c-checkbox--type2 _agree" id="chkAgree4" name="chkAgree4" value="">
                                                <label class="c-label" for="chkAgree4">KT이용약관 확인 동의<span class="u-co-gray">(필수)</span>
                                                </label>
                                                <button class="c-button c-button--xsm" onclick="fnSetEventId('chkAgree4');openPage('termsPop','/termsPop.do','cdGroupId1=TERMSCOM&cdGroupId2=TERMSCOM05',0);">
                                                    <span class="c-hidden">상세보기</span>
                                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <p class="combine-sign__txt">※결합신청 버튼을 누른 후 결합완료까지 시간이 다소 걸릴 수 있습니다.</p>
                    </div>

                    <!-- 결합 신청 버튼 영역 -->
                    <div class="combine-sign u-mt--16" style="display: none" >
                        <button id="btnRegCombin" type="button" class="c-button is-disabled">결합 신청</button>
                    </div>
                </div>

                <!-- 알려드립니다 영역 -->
                <div class="combine-notice">


                </div>

            </div>





            <!-- KT유무선 결합 완료(가족결합 제외) 노출 영역 -->
            <div id="divResult_01" class="combine-complete _completeForm" style="display: none">
                <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                <p class="combine-complete__txt">
                    <span class="u-co-mint">결합이 완료 되었습니다.</span><br />kt M모바일 회선에 무료결합데이터(결합 데이득)이 제공되었습니다.
                </p>
                <p class="combine-complete__subtxt">※ 결합 완료 시 SMS가 발송됩니다. SMS를 수신하지 못하신 고객님은 고객센터(114)로 연락 바랍니다.</p>
                <p class="combine-complete__subtxt">※ 요금제 변경 및 해지 시 고객센터를 통해 신청바랍니다.</p>
            </div>

            <!-- KT유무선 결합(가족결합) 신청완료 노출 영역 -->
            <div id="divResult_02"  style="display: none" >
                <div class="combine-complete">
                    <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                    <p class="combine-complete__txt">
                        <span class="u-co-mint">결합 신청이 완료 되었습니다.</span><br/>가족관계검증을 위해 관련 서류를 보내주시기 바랍니다.<br/>서류검증 후 결합처리가 완료됩니다.
                    </p>
                    <p class="combine-complete__subtxt">※결합 신청 후 SMS가 발송됩니다. SMS를 수신하지 못하신 고객님은 고객센터(114)로 연락 바랍니다.</p>
                </div>
                <div class="combine-checklist u-mt--48">
                    <p class="combine-checklist__tit u-co-red">
                        <i class="c-icon c-icon--bang--red" aria-hidden="true"></i>
                        <span>주의사항</span>
                    </p>
                    <ul class="combine-checklist__item">
                        <li>해당 페이지에서 결합 신청완료 한 후, 아래 서류를 신청 후 3일 이내에 이메일 (ktm.vit@kt.com) 이나 팩스(070-4275-2483)로 전달 주셔야 합니다.</li>
                        <li>결합 신청일 기준 5일이내로 서류가 도착하지 않는 경우 해당 결합 신청이 취소되며, 취소 후에 결합을 원하실 경우  재신청 바랍니다.</li>
                    </ul>
                    <p class="combine-checklist__subtit u-co-red">
                        <span>※필요(제출) 서류</span>
                    </p>
                    <ul class="combine-checklist__item no-bullet">
                        <li><b>① 주민등록등본, 건강보험증, 가족관계증명서, 혼인관계증명서, 입양관계증명서, 친양자입양관계증명서 중 택 1</b></li>
                        <li><b>② 결합신청인, 결합대상인의 신분증(주민등록증, 여권, 운전면허증, 공무원증 중 택 1)</b></li>
                        <li>
                            ※ 주민번호 뒷 번호가 보이지 않게 마스킹 처리 바랍니다. 미처리 시 해당 자료는 폐기됩니다.<br />
                            ※ 미성년자의 경우 법정대리인 신분증으로 제출 바랍니다.
                        </li>
                    </ul>
                </div>
            </div>


            <div class="combine-complete__list _completeForm" style="display: none">
                <h4 class="c-heading c-heading--type5">신청 내역</h4>
                <hr class="c-hr c-hr--type2">
                <h3 class="u-mt--0">내 정보</h3>
                <div class="c-table">
                    <table>
                        <caption>전화번호, 무료결합데이터, 요금제 정보를 포함한 결합내역 정보</caption>
                        <colgroup>
                            <col style="width: 33%">
                            <col>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row">휴대폰 번호</th>
                            <td class="_ctn" ></td>
                        </tr>
                        <tr>
                            <th scope="row">요금제</th>
                            <td class="_rate"></td>
                        </tr>
                        <tr>
                            <th scope="row">무료결합데이터</th>
                            <td class="u-ta-left progress-info _serviceNm" ></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <h3>결합 회선 정보</h3>
                <div class="c-table">
                    <table>
                        <caption>요금제 정보를 포함한 결합내역 정보</caption>
                        <colgroup>
                            <col style="width: 33%">
                            <col>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row">KT</th>
                            <td id="tdKtInfo"></td>
                        </tr>
                        </tbody>
                    </table>
                    <p class="combine-complete__listsubtxt">※ 해당 KT 상품의 상세 내용 확인은 KT 고객센터로 문의 해주시기 바랍니다.</p>
                </div>
            </div>
            <div class="combine-complete__confirm _completeForm" style="display: none">
                <button type="button" class="c-button" onclick="location.href='/content/combineKt.do' ">확인</button>
            </div>







        </div>



    </t:putAttribute>


    <t:putAttribute name="popLayerAttr">

        <!-- 결합내역 조회 팝업 -->
        <div class="c-modal" id="combinHistory" role="dialog" tabindex="-1" aria-describedby="combinHistoryTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="combinHistoryTitle">결합내역 조회</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <h3 class="combin-history__tit">결합 중인 회선</h3>
                        <div class="combin-history__myinfo c-table">
                            <table>
                                <caption>조회회선, 무료결합데이터를 포함한 결합 중인 회선 정보</caption>
                                <colgroup>
                                    <col style="width: 32%">
                                    <col>
                                </colgroup>
                                <tbody>
                                <tr>
                                    <th scope="row">조회 회선</th>
                                    <td>
                                        <p class="_ctn"><!--010-12**-*123--></p>
                                        <p class="rate-info _rate"><!--데이터 맘껏 15GB+/300분(기프티쇼 5000P)--></p>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">무료결합데이터</th>
                                    <td class="progress-info" id="pServiceNm">-</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div id="divCombinHistory"  class="combin-history__list c-table" style="display: none">
                            <table>
                                <caption>kt M모바일, KT 인터넷, KT 모바일을 포함한 결합 중인 회선 정보</caption>
                                <colgroup>
                                    <col style="width: 32%">
                                    <col>
                                </colgroup>
                                <tbody>
                                    <%-- <tr>
                                        <th scope="row">kt M모바일</th>
                                        <td>
                                            <p>010-12**-*123</p>
                                            <p class="rate-info">데이터 맘껏 15GB+/300분</p>
                                            <p class="date-info">(결합일 : 2022.12.31)</p>
                                        </td>
                                    </tr> --%>
                                </tbody>
                            </table>
                        </div>
                        <div id="divCombinHistory2"  class="combin-history__progress" style="display: none">
                            <h3>결합 진행 현황</h3>
                            <p class="combin-history__subtit">심사가 완료되면 상단의 결합중인 회선에 결합 내역이 노출됩니다.</p>
                            <div class="combin-history__proginfo c-table">
                                <table>
                                    <caption>결합 대상 회선, 진행현황을 포함한 결합 진행 현황 정보</caption>
                                    <colgroup>
                                        <col style="width: 32%">
                                        <col>
                                    </colgroup>
                                    <tbody>
                                        <%-- <tr>
                                            <th scope="row">kt 인터넷</th>
                                            <td>
                                                <p>z!1234****</p>
                                                <span class="progress-info">승인대기</span>
                                            </td>
                                        </tr>
                                        --%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div id="divCombinHistoryNodata" class="combin-nodata c-nodata" style="display: none">
                            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                            <p class="c-noadat__text">현재 결합 중인 회선이 없습니다.</p>
                        </div>

                        <div class="c-notice-wrap">
                            <hr>
                            <h5 class="c-notice__title">
                                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                <span>알려드립니다</span>
                            </h5>
                            <ul class="c-notice__list">
                                <li>결합된 회선의 요금제 변경 및 해지 시 kt M모바일 고객은 kt M모바일 고객센터(114, 1899-5000), KT 고객은 KT 고객센터를 통해 가능합니다.</li>
                            </ul>
                        </div>


                    </div>
                </div>
            </div>
        </div>



        <!-- 정보제공 동의 팝업 -->
        <div class="c-modal" id="combinInfoAgree" role="dialog" tabindex="-1" aria-describedby="combinInfoAgreeTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="combinInfoAgreeTitle">정보제공동의</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">

                        <div class="combin-Info-Agree agree-type2">
                            <h3>정보제공 동의란?</h3>
                            <p>KT 상품을 가입한 고객이 KT 고객센터를 통해 자신의 정보가 kt M모바일에 제공되도록 <span class="u-co-mint">정보제공 동의</span>를 하는 절차입니다.</p>
                            <div class="combin-Info-Agree__wrap">
                                <ul class="process-wrap">
                                    <li class="process-list">
                                        <div class="process-list-item">
                                            <span class="process-list__step">1</span>
			                                      <div>
			                                          <strong>KT 고객센터 전화하기</strong>
		                                            <p class="u-mt--4">KT 모바일 사용자 : 114번</p>
		                                            <p>KT 인터넷 가입자 : 100번</p>
			                                      </div>
                                        </div>
                                        <div class="process-list-item">
                                            <span class="process-list__step">2</span>
                                            <div>
                                                <strong>상담원 연결</strong>
                                            </div>
                                        </div>
                                        <div class="process-list-item">
                                            <span class="process-list__step">3</span>
                                            <div>
                                                <strong>kt M모바일과 결합 위해<br/>「MVNO정보제공동의」요청</strong>
                                            </div>
                                        </div>
                                        <div class="process-list-item">
                                            <span class="process-list__step">4</span>
                                            <div>
                                                <strong>본인인증</strong>
                                            </div>
                                        </div>
                                        <div class="process-list-item">
                                            <span class="process-list__step">5</span>
                                            <div>
                                                <strong>완료</strong>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <!-- KT인터넷 ID 확인 방법 팝업 -->
        <div class="c-modal" id="ktInternetIdModal" role="dialog" tabindex="-1" aria-describedby="ktInternetIdModalTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="ktInternetIdModalTitle">KT인터넷 ID 확인 방법</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                      <div class="acc-guide-wrap">
                        <ul class="acc-guide__list--wrap">
													<li>
													    <div class="acc-guide__list--group">
													        <div class="acc-guide__list__item">
													            <span class="Number-label--type2">
													                <em>1</em>
													                  KT 홈페이지 로그인 후 “마이”선택
													            </span>
													        </div>
													        <div class="acc-guide__list__item">
													            <span class="Number-label--type2">
													                <em>2</em>
													                  인터넷 선택
													            </span>
													        </div>
													    </div>
													    <div class="acc-guide__list--group">
													        <div class="acc-guide__list__item">
													            <img src="/resources/images/public/ktdc_guide_01.png" alt="KT 홈페이지 로그인 후 “마이”선택">
													        </div>
													        <div class="acc-guide__list__item">
													            <img src="/resources/images/public/ktdc_guide_02.png" alt="인터넷 선택">
													        </div>
													    </div>
													</li>
													<li>
													    <div class="acc-guide__list--group">
													        <div class="acc-guide__list__item">
													            <span class="Number-label--type2">
													                <em>3</em>
													                  자물쇠를 선택하여 *로 가려진 정보 보기 후 인터넷ID 확인
													            </span>
													        </div>
													        <div class="acc-guide__list__item">
													            <!-- <span class="Number-label--type2">
													                <em>4</em>
													                   페어링 후 사용자 설정 진행<br/>손목 선택, 이용약관 동의 등 진행
													            </span> -->
													        </div>
													    </div>
													    <div class="acc-guide__list--group">
													        <div class="acc-guide__list__item">
													            <img src="/resources/images/public/ktdc_guide_03.png" alt="자물쇠를 선택하여 *로 가려진 정보 보기 후 인터넷ID 확인">
													        </div>
													        <div class="acc-guide__list__item">
													            <!-- <img src="/resources/images/common/apple_watch_guide_04.png"> -->
													        </div>
													    </div>
													</li>
											  </ul>
                      </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 구비서류 확인하기 팝업 -->
        <div class="c-modal" id="documentModal" role="dialog" tabindex="-1" aria-describedby="documentModalTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="documentModalTitle">구비서류 안내</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                      <h3 class="c-heading">① 결합 신청자 및 결합 대상자의 신분증</h3>
	                    <p class="u-fs-15 u-co-gray u-ml--18">(단, 미성년자의 경우 법정대리인 신분증으로 대체 제출)</p>
	                    <div class="u-mt--8 u-pa--20 c-box--type1">
	                      <div class="u-mt--0">
	                        <ul class="combine-notice__list u-mt--0">
	                          <li>
	                            <p class="combine-notice__list-tit u-mt--0">인정 가능 신분증</p>
	                            <ul class="combine-notice__bullet">
	                              <li>주민등록증</li>
	                              <li>여권</li>
	                              <li>운전면허증</li>
	                              <li>공무원증</li>
	                            </ul>
	                            <p class="u-fs-14 u-fw--regular u-co-sub-4 u-mt--8">※ 신분증 사본 제출 시 주민번호 뒷자리는 스티커 등을 붙여서 보이지 않도록 하거나 그림판과 같은 프로그램을 이용하여 검게 마스킹(가림처리) 후 제출해 주셔야합니다.</p>
	                          </li>
	                        </ul>
	                      </div>
	                    </div>
	                    <h3 class="c-heading u-mt--24">② 가족관계 증빙 서류</h3>
	                    <div class="u-mt--8 u-pa--20 c-box--type1">
		                    <div class="u-mt--0">
		                      <ul class="combine-notice__list u-mt--0">
		                        <li>
		                          <p class="combine-notice__list-tit u-mt--0">인정 가능 서류</p>
		                          <ul class="combine-notice__bullet">
		                            <li>주민등록등본</li>
		                            <li>가족관계증명서</li>
		                            <li>건강보험증</li>
		                            <li>혼인관계증명서</li>
		                            <li>입양관계증명서</li>
		                            <li>친자입양관계증명서</li>
		                          </ul>
		                        </li>
		                      </ul>
		                    </div>
                    </div>
                </div>
            </div>
          </div>
        </div>

        <!-- 서비스 ID 팝업 -->
        <div class="c-modal" id="serviceInfo" role="dialog" tabindex="-1" aria-describedby="serviceInfoTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="serviceInfoTitle">인터넷 서비스 ID</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <p class="c-text c-text--type3">
                            인터넷 서비스 ID란  KT 유선 서비스에 가입된 고객에게 부여되며 마이케이티APP 및 KT 고객센터에서 문의 후 입력해 주시기 바랍니다.
                        </p>
                    </div>
                    <div class="c-modal__footer">
                        <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                    </div>
                </div>
            </div>
        </div>

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

        <%--결합 할인 팝업--%>
        <div class="c-modal" id="combineSale" role="dialog" tabindex="-1" aria-describedby="combine_sale_title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="combine_sale_title">결합 할인</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                    </div>
                </div>
            </div>
        </div>
        <%--결합 할인 팝업--%>

        <%--데이터 제공 요금제 팝업--%>
        <div class="c-modal" id="provideSoc" role="dialog" tabindex="-1" aria-describedby="provide_soc_title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="provide_soc_title">데이터 제공 요금제</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                    </div>
                </div>
            </div>
        </div>
        <%--데이터 제공 요금제 팝업--%>

        <%--신청 절차 팝업--%>
        <div class="c-modal" id="applyProcedure" role="dialog" tabindex="-1" aria-describedby="apply_procedure_title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="apply_procedure_title">신청 절차</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                    </div>
                </div>
            </div>
        </div>
        <%--신청 절차 팝업--%>

        <!-- 동영상 보기 팝업 -->
        <div class="c-modal quickguide" id="modalContent" role="dialog" tabindex="-1" aria-describedby="modalContentTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="quickguide-box">
                            <i class="c-icon c-icon--reset" aria-hidden="true" data-dialog-close></i>
                            <iframe id="youTubeIframe" src="about:blank" frameborder="0" allowfullscreen="1" style="width: 100%; height: 250px;" allow="autoplay; encrypted-media"></iframe>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>


</t:insertDefinition>