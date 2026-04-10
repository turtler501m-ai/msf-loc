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
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="titleAttr">가입정보조회</t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/mypage/myinfo/myinfoView.js?version=${jsver}"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <input type="hidden" id="addr" name="addr" value="${addr}">
        <input type="hidden" id="isMcashUser" name="isMcashUser" value="${isMcashUser}">
        <input type="hidden" id="remindBlckYn" name="remindBlckYn" value="${remindBlckYn}">
        <input type="hidden" id="prcsMdlInd" name="prcsMdlInd" value="">

        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                       가입정보
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="myinfo__tit">
               <h3 class="user-info__tit">
                   <b>${searchVO.userName}</b>님 반갑습니다.
               </h3>
               <div class="myinfo__button-wrap">
                      <a href="javascript:;" url="/m/mypage/multiPhoneLine.do" class="link">회선관리<i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i></a>
               </div>
            </div>

            <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>

            <div class="myinfo__tit">
                    <h3>가입정보</h3>
                    <div class="myinfo__button-wrap">
                        <button id="mktArgBtn">
                                정보/광고 수신동의 <i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i>
                        </button>
                    </div>
            </div>
            <div class="join-info__list">
                <dl>
                    <dt>개통일자</dt>
                    <dd>
                        <span>${initActivationDate}</span>
                    </dd>
                </dl>
                <dl>
                    <dt>모델명</dt>
                    <dd>
                        <span>${modelName}</span>
                    </dd>
                </dl>
            </div>
            <div class="myinfo__tit">
                <h3>당월 청구요금</h3>
               </div>
            <div class="month_bill">
                <div class="month_bill c-accordion c-accordion--type2">
                    <div class="c-accordion__box" id="accordion_month_bill">
                        <div class="c-accordion__item">
                            <div class="c-accordion__head" data-acc-header="#month_bill" aria-expanded="false">
                                <button class="c-accordion__button" type="button" aria-expanded="false">
                                    <dl>
                                        <dt>사용기간</dt>
                                        <dd>
                                           <span id="billPeriod"></span>
                                        </dd>
                                    </dl>
                                </button>
                            </div>
                            <div class="c-accordion__panel expand" id="month_bill">
                                <div class="c-accordion__inside">
                                    <ul class="bill-wrap" id="monthBillArea" style="display: none;">
                                        <li class="bill-item">
                                            <div class="bill-tit">
                                                <p id="billMonth">-월 청구요금</p>
                                                <div class="bill-btn-wrap">
                                                    <a href="javascript:;" url="/m/mypage/chargeView01.do" class="link">내역보기</a>
                                                    <a href="javascript:;" url="/m/mypage/unpaidChargeList.do" class="link">즉시납부</a>
                                                </div>
                                            </div>
                                            <div class="bill-price u-co-mint">
                                                <b id="totalDueAmt">-</b><span>원</span>
                                            </div>
                                        </li>
                                        <li class="bill-item">
                                            <div class="bill-tit">
                                                <p>실시간 요금</p>
                                                <div class="bill-btn-wrap">
                                                    <a href="javascript:;" url="/m/mypage/chargeView03.do" class="link">내역보기</a>
                                                </div>
                                            </div>
                                            <div class="bill-price">
                                                <b id="sumAmt">-</b><span>원</span>
                                            </div>
                                        </li>
                                    </ul>
                                    <ul class="bill-wrap" id="loadingMonthBillArea">
                                        <li class="bill-item">
                                            <div class="bill-nodata">조회 중 입니다.</div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="myinfo__tit ">
                   <h3>사용중 요금제</h3>
                   <div class="myinfo__button-wrap">
                       <a href="javascript:;" url="/m/mypage/farPricePlanView.do" class="link"> 요금제 변경하기 <i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i></a>
                   </div>
            </div>
            <div class="myrate remind">
                <div class="myrate__box">
                    <p class="myrate__tit">${prvRateGrpNm}</p>
                    <ul class="myrate__list">
                        <li class="myrate__item">
                            <i class="c-icon c-icon--data-type2" aria-hidden="true"></i>
                            <span>${rateAdsvcLteDesc}</span>
                        </li>
                        <li class="myrate__item">
                            <i class="c-icon c-icon--call-type2" aria-hidden="true"></i>
                            <span>${rateAdsvcCallDesc}</span>
                        </li>
                        <li class="myrate__item">
                            <i class="c-icon c-icon--msg-type2" aria-hidden="true"></i>
                            <span>${rateAdsvcSmsDesc}</span>
                            <a href="/m/mypage/callView01.do" class="link">이용량 조회하기</a>
                        </li>
                    </ul>
                </div>
                <c:if test="${not empty remindBlckYn}">
                <div class="remind-sms">
                    <div>
                              리마인드 문자 수신
                        <button data-tpbox="#remindTp"><i class="c-icon c-icon--tooltip" aria-hidden="true"></i></button>
                        <div class="c-tooltip-box" id="remindTp">
                            <div class="c-tooltip-box__title">리마인드 문자란?</div>
                            <div class="c-tooltip-box__content">
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item">제휴 혜택 등록 안내를 위해 제휴 혜택 미등록 고객 대상으로 주기적으로 발송되는 문자입니다.</li>
                                </ul>
                            </div>
                            <button class="c-tooltip-box__close" data-tpbox-close>
                                <span class="c-hidden">툴팁닫기</span>
                            </button>
                        </div>
                    </div>
                       <div class="remind-switch">
                        <input class="c-checkbox c-checkbox--switch" id="remindSwitch" type="checkbox">
                          <label class="c-label" for="remindSwitch">
                            <span class="c-label__text off">OFF</span>
                            <span class="c-label__text on">ON</span>
                          </label>
                       </div>
                </div>
                </c:if>
            </div>
            <c:if test="${isMcashAuth eq 'Y'}">
                <div class="myinfo__tit">
                    <h3>M쇼핑할인</h3>
                    <div class="mcash-info">
                        <div class="myinfo__tit myinfo__tit--right">
                            <c:if test="${isMcashUser eq 'Y'}">
                                <button data-dialog-trigger="#mcashServiceChg" onclick="openMcashCntrChgPop();">
                                    회선 변경
                                    <i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i>
                                </button>
                                <button id="mcashSvcCan">
                                    서비스 해지
                                    <i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i>
                                </button>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="charge_info">
                    <table>
                        <caption>서비스 가입일자, 내 캐시로 구성된 M쇼핑할인 가입정보</caption>
                        <colgroup>
                            <col style="width: 7.5rem;">
                            <col>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>서비스 가입일자</th>
                            <td>
                                <c:if test="${isMcashUser eq 'Y'}">${mcashStrtDttm}</c:if>
                                <c:if test="${isMcashUser ne 'Y'}">M쇼핑할인 가입 회선이 아닙니다.</c:if>
                            </td>
                        </tr>
                        <tr>
                            <th>내 캐시</th>
                            <td>
                                <c:if test="${isMcashUser eq 'Y'}"><span id="remainMcash">-</span><a href="/m/mcash/mcashView.do" class="link">캐시 적립하기</a></c:if>
                                <c:if test="${isMcashUser eq 'N' && isMcashJoinCnt eq 'N'}"><a href="/m/mcash/mcashView.do" style="float: left">M쇼핑할인 가입하기</a></c:if>
                                <c:if test="${isMcashJoinCnt eq 'Y'}">-</c:if>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
            <div class="myinfo__tit">
                   <h3>부가서비스</h3>
                  <div class="myinfo__button-wrap">
                    <a href="javascript:;" url="/m/mypage/roamingView.do" class="link"> 로밍 신청/해지 <i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i></a>
                    <a href="javascript:;" url="/m/mypage/regServiceView.do" class="link"> 부가서비스 신청/해지 <i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i></a>
                </div>
            </div>
            <div class="my-addservice">
                <dl>
                    <dt>유료 부가서비스</dt>
                    <dd>
                        <span id="payCtn">-</span> 건
                    </dd>
                </dl>
                <dl>
                    <dt>무료 부가서비스</dt>
                    <dd>
                        <span id="freeCtn">-</span> 건
                    </dd>
                </dl>
            </div>
            <div class="myinfo__tit">
                <h3>납부정보</h3>
                <a id="virtualAccountButton" href="javascript:;" onclick="openVirtualAccountListPop()">납부 가상계좌 조회<i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i></a>
           </div>
            <div class="charge_info">
                <table>
                    <caption>청구/납부 정보 테이블</caption>
                    <colgroup>
                        <col style="width: 7.5rem;">
                        <col>
                    </colgroup>
                    <tbody id="payArea" style="display: none;"></tbody>
                    <tbody id="loadingPayArea">
                    <tr>
                        <td colspan="2" class="u-ta-center">조회 중 입니다.</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="myinfo-banner">
                  <a class="myinfo-banner__item" href="/m/mypage/couponList.do" title="My쿠폰 바로가기">
                      <p class="myinfo-banner__title">My쿠폰<span></span></p>
                      <p class="myinfo-banner__text">발급받은 쿠폰은 쿠폰함에서<br/>확인하세요.</p>
                    <img src="/resources/images/mobile/mypage/myinfo_banner_bg_01.png">
                </a>
                  <a class="myinfo-banner__item" href="/m/mypage/reCommendState.do" title="친구초대현황 바로가기">
                      <p class="myinfo-banner__title">친구초대현황<span></span></p>
                      <p class="myinfo-banner__text">내 초대 내역을<br/>확인하세요.</p>
                       <img src="/resources/images/mobile/mypage/myinfo_banner_bg_02.png">
                </a>
            </div>

            <div class="myinfo__tit">
                <h3>유용한 정보</h3>
               </div>
            <div class="useful-info-list">
                <a href="/m/cs/serviceGuide.do" title="고객센터 안내 이동하기" class="loding"> 고객센터 안내 </a>
                <a href="/m/cs/csInquiryInt.do" title="1:1 상담문의 이동하기" class="loding"> 1:1 상담문의 </a>
                <a href="/m/cs/privacyBoardList.do" title="이용자 피해예방 가이드 이동하기" class="loding"> 이용자 피해예방 가이드 </a>
                <a href="/m/cs/mDeviceAsView.do" title="단말기 A/S 안내 이동하기" class="loding"> 단말기 A/S 안내 </a>
            </div>
        </div>

        <!-- 리마인드 OFF 변경 -->
        <div class="c-modal" id="remindOffModal" role="dialog" tabindex="-1" aria-describedby="remindOffModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                  <div class="c-modal__body">
                    <div class="complete u-pb--20 u-pt--0">
                        <div class="c-icon c-icon--notice" aria-hidden="true" style="width: 2.5rem; height: 2.5rem;">
                          <span></span>
                        </div>
                    </div>
                    <div class="c-notice-wrap u-mt--0">
                        <ul class="c-notice__list u-mt--0">
                            <li>고객님이 가입하신 요금제는 제휴 혜택 등록이 필요한 요금제로, 제휴 혜택 미등록 고객을 대상으로 주기적으로 리마인드 문자를 발송 중입니다.</li>
                            <li>수신을 희망하지 않는 고객님께서는 수신 상태 변경을 부탁드립니다.</li>
                            <li>수신 상태 변경 시 익일부터 리마인드 문자 발송이 중단됩니다.</li>
                        </ul>
                    </div>
                    <div class="complete u-pt--16">
                          <p class="complete__heading u-fs-14 u-mt--0">
                          <b>수신 상태를 변경하시겠습니까?</b>
                        </p>
                    </div>
                  </div>
                  <div class="c-modal__footer">
                    <div class="c-button-wrap u-mt--0">
                        <button class="c-button c-button--gray c-button--full" data-dialog-close onclick="remindCancelBtn()">취소</button>
                        <button class="c-button c-button--primary c-button--full" data-dialog-close onclick="remindSucBtn(`Y`)">동의</button>
                    </div>
                   </div>
                 </div>
             </div>
        </div>

        <!-- 리마인드 ON 변경 -->
        <div class="c-modal" id="remindOnModal" role="dialog" tabindex="-1" aria-describedby="remindOnModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                  <div class="c-modal__body">
                    <div class="complete u-pb--20 u-pt--0">
                        <div class="c-icon c-icon--notice" aria-hidden="true" style="width: 2.5rem; height: 2.5rem;">
                          <span></span>
                        </div>
                    </div>
                    <div class="c-notice-wrap u-mt--0">
                        <ul class="c-notice__list u-mt--0">
                            <li>고객님이 가입하신 요금제는 제휴 혜택 등록이 필요한 요금제로, 리마인드 문자 수신 동의 시 제휴 혜택 미등록 고객을 대상으로 주기적으로 리마인드 문자가 발송될 수 있습니다.</li>
                            <li>수신을 희망하시는 고객님께서는 수신 동의를 부탁드립니다.</li>
                        </ul>
                    </div>
                    <div class="complete u-pt--16">
                          <p class="complete__heading u-fs-14 u-mt--0">
                          <b>유의사항을 확인하였으며,<br />제휴 혜택 등록 리마인드 문자 수신에 동의합니다.</b>
                        </p>
                    </div>
                  </div>
                  <div class="c-modal__footer">
                    <div class="c-button-wrap u-mt--0">
                        <button class="c-button c-button--gray c-button--full" data-dialog-close onclick="remindCancelBtn()">취소</button>
                        <button class="c-button c-button--primary c-button--full" data-dialog-close onclick="remindSucBtn(`N`)">동의</button>
                    </div>
                   </div>
                 </div>
             </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>