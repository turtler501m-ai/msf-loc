<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="titleAttr">KT인터넷 트리플할인</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/content/ktDcView.js"></script>
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
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
        <div class="ly-page-sticky">
            <h2 class="ly-page__head">트리플할인<button class="header-clone__gnb"></button></h2>
        </div>
        <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
        <div class="phone-line-wrap">
            <div class="phone-line">
                <span class="phone-line__cnt">${searchVO.moCtn}</span>
            </div>
            <select name="ctn" id="ctn">
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

        <!-- 신청 영역 -->
        <div id="ktDcStart" class="u-mt--48">
            <div id="prvRateId" class="banner-balloon u-mt--40 u-mb--24">
                <p>
                  <span class="c-text c-text--type5">이용중인 요금제</span>
                  <br>
                  <b class="c-text c-text--type7">${prvRateGrpNm}</b>
                  <img class="deco-img" src="/resources/images/mobile/content/img_bolloon_banner_03.png" alt="">
                </p>
            </div>
            <div class="ktdc-title">
                <h3>KT인터넷 트리플할인이란?</h3>
                <p>상품 페이지에서 “KT인터넷 상담신청”을 통해 KT인터넷을 가입한 고객 대상 kt M모바일 회선에 추가 할인을 제공해 드리는 서비스 입니다.
                    <a class="c-text-link--bluegreen" href="/m/content/ktDcInfo.do" title="KT인터넷 트리플할인 페이지 이동">안내페이지 바로가기</a>
                </p>
            </div>
            <div class="c-button-wrap u-mt--0">
                <button id="discountSearch" class="c-button c-button--full c-button--mint u-mt--45" onclick="discountSearch()">할인 가능 조회</button>
            </div>

            <!-- 트리플할인 신청 가능 -->
            <div id="tdc-target" style="display: none">
                <div class="tdc-target-box">
                       트리플할인을 신청하실 경우<br /><b> “<span id=addServiceName></span>”</b><br />이 적용됩니다.
                    <p>신청하실 인터넷 ID를 선택 후 할인을 받아보세요.</p>
                </div>
                <h3 class="c-heading c-heading--type2 u-mb--16">신청 가능 인터넷 상품 선택</h3>
                <div class="c-table">
                    <table>
                        <caption>인터넷 상품명, 인터넷 가입일 정보를 포함한 신청 가능 인터넷 상품 정보</caption>
                        <colgroup>
                            <col style="width: 12%">
                            <col>
                            <col style="width: 26%">
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
                <p class="c-bullet c-bullet--fyr u-fs-12 u-co-gray u-mt--12">트리플할인은 kt인터넷 ID 당 1회만 신청 가능하며, 요금제 변경 등으로 혜택이 중단 될 경우 재 지급이 불가합니다.</p>
                <div class="c-button-wrap u-mt--45">
                    <button class="c-button c-button--full c-button--gray" onclick="discountSearch()">다시 조회</button>
                    <button class="c-button c-button--full c-button--primary" onclick="accApplication()">신청 접수</button>
                </div>
            </div>

            <!-- 트리플할인 신청 불가능 / 트리플할인 미대상/아무나가족결합 대상 -->
            <div id="tdc-notarget" style="display: none">
                <div class="tdc-notarget-box">
                       현재 사용하시는 요금제는<br /><b>트리플할인 대상 요금제가 아닙니다.</b>
                    <p>트리플할인은 불가하지만 “아무나 가족결합” 시<br />데이터 제공 및 인터넷 사용 요금의 할인이 가능한 요금제입니다.<br />아무나 가족결합에 대해 알아보세요.</p>
                </div>
                <div class="c-button-wrap c-button-wrap--column">
                    <a class="c-button c-button--full c-button--primary" href="/m/content/ktDcInfo.do" title="KT인터넷 트리플할인 페이지 이동">트리플할인 안내 바로가기</a>
                    <a class="c-button c-button--full c-button--gray" href="/m/content/combineKt.do" title="아무나가족결합 페이지 이동">아무나가족결합 바로가기</a>
                </div>
            </div>

            <!-- 트리플할인 신청 불가능 / 트리플할인 미대상/아무나가족결합 미대상 -->
            <div id="tdc-allNotarget" style="display: none">
                <div class="tdc-notarget-box">
                       현재 사용하시는 요금제는<br /><b>트리플할인 대상 요금제가 아닙니다.</b>
                    <p>트리플할인을 원하실 경우<br />대상 요금제로 변경 후 다시 시도 해 주세요.</p>
                </div>
                <div class="c-button-wrap c-button-wrap--column">
                    <a class="c-button c-button--full c-button--primary" href="/m/content/ktDcInfo.do" title="KT인터넷 트리플할인 페이지 이동">트리플할인 안내 바로가기</a>
                    <a class="c-button c-button--full c-button--gray" href="/m/mypage/farPricePlanView.do" title="요금제 변경 페이지 이동">요금제 변경 바로가기</a>
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
        <div id="KtDcDone"  class="u-mt--48 u-ta-center" style="display: none">
            <div class="combine-complete__wrap">
                <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                <p class="combine-complete__txt">
                    <span class="u-co-mint">트리플할인이 신청되었습니다.</span>
                </p>
                <p class="combine-complete__subtxt u-ta-center">적용된 내역은 익월 명세서를 통해 확인 가능합니다.</p>
                <div class="c-box c-box--type1 u-mt--40 u-pa--24">
                    <p class="combine-complete__txt u-lh--inherit u-fs-18 u-mt--0">
                        인터넷/모바일 결합하고<br><span class="u-co-red">추가 요금할인</span>을 받으시는 건 어떠세요?
                    </p>
                    <p class="combine-complete__txt u-fw--regular u-co-gray u-fs-16 u-lh--inherit u-mt--12">
                        사용하시는 인터넷과 M모바일 결합 시<br>인터넷에서 추가할인을 받으실 수 있습니다.
                    </p>
                    <p class="combine-complete__subtxt u-fw--regular u-fs-14 u-ta-center">※ 결합 할인 금액 등은 결합 신청 페이지를 참조 바랍니다.</p>
                </div>
            </div>
            <div class="c-button-wrap c-button-wrap--column">
                <a class="c-button c-button--full c-button--primary" href="/m/content/combineKt.do" title="아무나가족결합 페이지 이동">결합 신청</a>
                <button class="c-button c-button--full c-button--gray" onclick="goBack()">처음으로</button>
            </div>
        </div>
        <!-- 신청 완료 영역 끝 -->

          <!-- 신청 완료 영역 2번째 경우-->
        <div id="KtDcDone2"  class="u-mt--48 u-ta-center" style="display: none">
            <div class="combine-complete__wrap">
                <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                <p class="combine-complete__txt">
                    <span class="u-co-mint">트리플할인이 신청되었습니다.</span>
                </p>
                <p class="combine-complete__subtxt u-ta-center">적용된 내역은 익월 명세서를 통해 확인 가능합니다.</p>
            </div>
            <div class="c-button-wrap c-button-wrap--column">
                <button class="c-button c-button--full c-button--primary" onclick="goBack()">처음으로</button>
            </div>
        </div>
        <!-- 신청 완료 영역 2번째 경우 끝 -->


    </div>

    </t:putAttribute>
</t:insertDefinition>