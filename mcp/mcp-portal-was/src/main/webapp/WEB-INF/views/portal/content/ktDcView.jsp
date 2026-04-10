<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<t:insertDefinition name="layoutGnbDefault">
    <t:putAttribute name="titleAttr">KT인터넷 트리플할인</t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/portal/content/ktDcView.js?version=${jsver}"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
          <input type="hidden" id="prvRateCode" value="${prvRateCode}">
          <input type="hidden" id="custId" value="${custId}">
          <input type="hidden" id="myCtn" value="${ctn}">
          <input type="hidden" id="prcsMdlInd" value="${prcsMdlInd}">
          <input type="hidden" id="soc" value="${soc}">
          <input type="hidden" id="contractNum" value="${contractNum}">
          <input type="hidden" id="baseAmt" value="">
           <input type="hidden" id="doneReturn" value="${doneReturn}">
           <input type="hidden" id=additionCd value="${additionCd}">
           <input type="hidden" id=combYn value="">
          <div class="ly-content" id="main-content">
              <div class="ly-page--title">
                 <h2 class="c-page--title">트리플할인</h2>
              </div>
             <div class="c-row c-row--lg">
                <h3 class="c-heading c-heading--fs20 c-heading--regular">조회할 회선을 선택해 주세요.</h3>
                <div class="c-select-search">
                    <div class="phone-line">
                        <span class="phone-line__cnt">${searchVO.moCtn}</span>
                    </div>
                    <div class="c-form--line">
                        <label class="c-label c-hidden" for="ctn">회선 선택</label>
                        <select class="c-select" name="ctn" id="ctn" >
                            <c:forEach items="${cntrList}" var="item">
                               <c:choose>
                                    <c:when test="${maskingSession eq 'Y'}">
                                        <option value="${item.svcCntrNo}" ${item.svcCntrNo eq searchVO.ncn?'selected':''} >${item.formatUnSvcNo}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${item.svcCntrNo}" ${item.svcCntrNo eq searchVO.ncn?'selected':''} >${item.cntrMobileNo}</option>
                                    </c:otherwise>
                               </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                    <button class="c-form--line__btn" id="btnSelectCTN" onclick="select();">조회</button>
                </div>
               <div id="prvRateId">
                    <hr class="c-hr c-hr--title">
                    <strong class="u-block c-heading c-heading--fs24 c-heading--regular u-ta-center">
                                 이용중인 요금제는<br /><b>${prvRateGrpNm}</b> 입니다.</strong>
                    <hr class="c-hr c-hr--title u-mt--24">
                </div>
                <!-- 신청 영역 -->
                <div id="ktDcStart" class="u-mt--64">
                    <div class="ktdc-title">
                        <h4>KT인터넷 트리플할인이란?</h4>
                        <p>상품 페이지에서 “KT인터넷 상담신청”을 통해 KT인터넷을 가입한 고객 대상 kt M모바일 회선에 추가 할인을 제공해 드리는 서비스 입니다.
                            <a class="c-text-link--bluegreen" href="/content/ktDcInfo.do" title="KT인터넷 트리플할인 안내페이지 이동">안내페이지 바로가기</a>
                        </p>
                    </div>
                    <div class="c-button-wrap">
                      <button id="discountSearch" class="c-button c-button-round--md c-button--mint u-width--460 u-mt--56" onclick="discountSearch()">할인 가능 조회</button>
                    </div>

                    <!-- 트리플할인 신청 가능 -->
                    <div id="tdc-target" style="display: none">
                        <div class="tdc-target-box">
                            트리플할인을 신청하실 경우<br /><b>“<span id=addServiceName> </span>”</b>이 적용됩니다.
                            <p>신청하실 인터넷 ID를 선택 후 할인을 받아보세요.</p>
                        </div>
                        <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--56">신청 가능 인터넷 상품 선택</h4>
                        <div class="c-table u-mt--20">
                            <table>
                                <caption>인터넷 상품명, 인터넷 가입일 정보를 포함한 신청 가능 인터넷 상품 정보</caption>
                                <colgroup>
                                    <col style="width: 5rem">
                                    <col>
                                    <col style="width: 19.625rem">
                                </colgroup>
                                <thead>
                                  <tr>
                                    <th scope="col">선택</th>
                                    <th scope="col">상품명</th>
                                    <th scope="col">가입일</th>
                                  </tr>
                                </thead>
                                <tbody id="tripleDcAcc">

                                </tbody>
                            </table>
                        </div>
                        <p class="c-bullet c-bullet--fyr u-fs-15 u-co-gray u-mt--18">트리플할인은 kt인터넷 ID 당 1회만 신청 가능하며, 요금제 변경 등으로 혜택이 중단 될 경우 재 지급이 불가합니다.</p>
                        <div class="c-button-wrap u-mt--48">
                          <button class="c-button c-button--lg c-button--gray u-width--220" onclick="discountSearch()">다시 조회</button>
                          <button class="c-button c-button--lg c-button--primary u-width--220" onclick="accApplication()">신청 접수</button>
                        </div>
                    </div>

                    <!-- 트리플할인 신청 불가능 / 트리플할인 미대상/아무나가족결합 대상 -->
                    <div id="tdc-notarget" style="display: none">
                        <div class="tdc-notarget-box">
                            현재 사용하시는 요금제는 <b>트리플할인 대상 요금제가 아닙니다.</b>
                            <p>트리플할인은 불가하지만 “아무나 가족결합” 시 데이터 제공 및 인터넷 사용 요금의 할인이 가능한 요금제입니다.<br />아무나 가족결합에 대해 알아보세요.</p>
                        </div>
                        <div class="c-button-wrap u-mt--56">
                          <a class="c-button c-button--lg c-button--primary u-width--460" href="/content/ktDcInfo.do" title="KT인터넷 트리플할인 페이지 이동">트리플할인 안내 바로가기</a>
                          <a class="c-button c-button--lg c-button--white u-width--460" href="/content/combineKt.do" title="아무나가족결합 페이지 이동">아무나가족결합 바로가기</a>
                        </div>
                    </div>

                    <!-- 트리플할인 신청 불가능 / 트리플할인 미대상/아무나가족결합 미대상 -->
                    <div id="tdc-allNotarget" style="display: none">
                        <div class="tdc-notarget-box">
                               현재 사용하시는 요금제는 <b>트리플할인 대상 요금제가 아닙니다.</b>
                            <p>트리플할인을 원하실 경우 대상 요금제로 변경 후 다시 시도 해 주세요.</p>
                        </div>
                        <div class="c-button-wrap u-mt--56">
                          <a class="c-button c-button--lg c-button--primary u-width--460" href="/content/ktDcInfo.do" title="KT인터넷 트리플할인 페이지 이동">트리플할인 안내 바로가기</a>
                          <a class="c-button c-button--lg c-button--white u-width--460" href="/mypage/farPricePlanView.do" title="요금제 변경 페이지 이동">요금제 변경 바로가기</a>
                        </div>
                    </div>

                    <div class="c-notice-wrap">
                      <h5 class="c-notice__title">
                          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                          <span>알려드립니다</span>
                      </h5>
                      <ul class="c-notice__list">
                          <li>KT인터넷 가입일부터 익월말까지 트리플할인을 신청한 고객 대상 신청월부터 kt M모바일 요금할인이 적용됩니다.</li>
                          <li>KT인터넷 트리플할인의 kt M모바일 요금할인 혜택은 다이렉트몰을 통해서 가입한 고객에게만 적용됩니다.</li>
                          <li>KT인터넷 신규 가입자와 kt M모바일 가입자의 명의가 동일할 경우에만 혜택이 제공되며 다회선 가입 시 요금할인은 개통 순으로 1회에 한해 적용됩니다.</li>
                          <li>KT인터넷 트리플할인 대상 요금제 및 할인금액은 월 진행되는 프로모션에 따라 변동 될 수 있습니다.</li>
                          <li>혜택 제공 후 M모바일 가입 요금제의 변경, 해지, 일시정지 및 KT인터넷의 해지 시 요금할인의 혜택은 종료됩니다.</li>
                          <li>요금할인 혜택은 제공 후 익월 명세서에서 확인이 가능합니다.</li>
                      </ul>
                    </div>
                </div>
                <!-- 신청 영역 끝 -->

                <!-- 신청 완료 영역 -->
                <div id="KtDcDone" class="u-mt--64" style="display: none">
                    <div class="combine-complete__wrap">
                        <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                        <p class="combine-complete__txt">
                            <span class="u-co-mint">트리플할인이 신청되었습니다.</span>
                        </p>
                        <p class="combine-complete__subtxt">적용된 내역은 익월 명세서를 통해 확인 가능합니다.</p>
                        <div class="c-box c-box--type1 u-mt--48 u-pa--32">
                            <p class="combine-complete__txt u-lh--inherit u-mt--0">
                                인터넷/모바일 결합하고<br /><span class="u-co-red">추가 요금할인</span>을 받으시는 건 어떠세요?
                            </p>
                            <p class="combine-complete__txt u-fw--regular u-co-gray u-fs-20 u-lh--inherit u-mt--24">
                                사용하시는 인터넷과 M모바일 결합 시 인터넷에서 추가할인을 받으실 수 있습니다.
                            </p>
                            <p class="combine-complete__subtxt">※ 결합 할인 금액 등은 결합 신청 페이지를 참조 바랍니다.</p>
                        </div>
                    </div>
                    <div class="c-button-wrap u-mt--56">
                        <button class="c-button c-button--lg c-button--gray u-width--220" onclick="goBack()">처음으로</button>
                        <a class="c-button c-button--lg c-button--primary u-width--220" href="/content/combineKt.do" title="아무나가족결합 페이지 이동">결합 신청</a>
                    </div>
                </div>
                <!-- 신청 완료 영역 끝 -->

                 <!-- 신청 완료 영역 2번째 경우 -->
                <div id="KtDcDone2" class="u-mt--64" style="display: none">
                    <div class="combine-complete__wrap">
                        <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                        <p class="combine-complete__txt">
                            <span class="u-co-mint">트리플할인이 신청되었습니다.</span>
                        </p>
                        <p class="combine-complete__subtxt">적용된 내역은 익월 명세서를 통해 확인 가능합니다.</p>
                    </div>
                    <div class="c-button-wrap u-mt--56">
                        <button class="c-button c-button--lg c-button--primary u-width--220" onclick="goBack()">처음으로</button>
                    </div>
                </div>
                <!-- 신청 완료 영역 2번째 경우 끝 -->
            </div>

        </div>
    </t:putAttribute>
</t:insertDefinition>