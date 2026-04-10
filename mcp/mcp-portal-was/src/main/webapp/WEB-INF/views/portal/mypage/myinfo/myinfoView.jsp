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
    <t:putAttribute name="titleAttr">가입정보조회</t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/portal/mypage/myinfo/myinfoView.js?version=${jsver}"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <input type="hidden" id="addr" name="addr" value="${addr}">
        <input type="hidden" id="isMcashUser" name="isMcashUser" value="${isMcashUser}">
        <input type="hidden" id="remindBlckYn" name="remindBlckYn" value="${remindBlckYn}">
        <input type="hidden" id="prcsMdlInd" name="prcsMdlInd" value="">

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">가입정보</h2>
            </div>
            <div class="myinfo-wrap">
                <h3 class="user-info__tit">
                    <b>${searchVO.userName}</b> 님 반갑습니다.
                </h3>
                <%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>
                <div class="join-info">
                    <ul class="join-info__list">
                        <li class="join-info__item">
                            <i class="c-icon c-icon--calendar-black"></i>
                            <span class="join-info__tit">개통일자</span>
                            <span class="join-info__text">${initActivationDate}</span>
                        </li>
                        <li class="join-info__item">
                            <i class="c-icon c-icon--my_phone"></i>
                            <span class="join-info__tit">모델명</span>
                            <span class="join-info__text">${modelName}</span>
                        </li>
                    </ul>
                    <div class="join-info__etc">
                        <button id="mktArgBtn">
                            <span>정보/광고 수신동의</span>
                        </button>
                        <button id="joinCert">
                            <span>가입증명원 인쇄</span>
                        </button>
                        <button id="myInfoPrint">
                            <span>가입정보 인쇄</span>
                        </button>
                    </div>
                </div>

                <ul class="myinfo-list">
                    <li class="myinfo__item">
                        <div class="myinfo__tit">
                            <h3>당월 청구요금</h3>
                            <span id="billPeriod"></span>
                        </div>
                        <div class="myinfo__info">
                            <div class="month_bill" id="monthBillArea" style="display: none;">
                                <div class="month_bill__item">
                                    <div class="month_bill__info">
                                        <p id="billMonth">-월 청구요금</p>
                                        <b id="totalDueAmt">-</b><span>원</span>
                                    </div>
                                    <div class="month_bill__btn">
                                        <a href="javascript:;" url="/mypage/chargeView01.do" title="당월 청구요금 내역보기 이동하기" class="link is-active">내역보기</a>
                                        <a href="javascript:;" url="/mypage/unpaidChargeList.do" title="당월 청구요금 즉시납부 이동하기" class="link">즉시납부</a>
                                    </div>
                                </div>
                                <div class="month_bill__item">
                                    <div class="month_bill__info">
                                        <p>실시간 요금</p>
                                        <b class="u-co-blue-2" id="sumAmt">-</b><span class="u-co-blue-2">원</span>
                                    </div>
                                    <div class="month_bill__btn">
                                        <a href="javascript:;" url="/mypage/chargeView03.do" title="실시간 요금 내역보기 이동하기" class="link">내역보기</a>
                                    </div>
                                </div>
                            </div>
                            <div class="month_bill" id="loadingMonthBillArea">
                                <div class="month_bill__nodata">
                                    <p>조회 중 입니다.</p>
                                </div>
                            </div>
                        </div>
                    </li>

                    <li class="myinfo__item">
                        <div class="myinfo__tit">
                            <h3>사용중 요금제</h3>
                            <div class="myinfo__button-wrap u-mt--m4">
                                <a href="javascript:;" url="/mypage/farPricePlanView.do" title="요금제 변경 이동하기" class="link is-active">요금제 변경 <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i></a>
                                <a href="javascript:;" url="/mypage/callView01.do" title="이용량 조회 이동하기" class="link"> 이용량 조회 <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i></a>
                            </div>
                        </div>
                        <div class="myinfo__info">
                            <div class="myrate remind">
                                <div class="myrate__title ">${prvRateGrpNm}</div>
                                <ul class="myrate__list ">
                                    <li class="myrate__item">
                                        <i class="c-icon c-icon--data-type2" aria-hidden="true"></i>
                                        <span class="c-hidden">데이터</span>
                                        <span>${rateAdsvcLteDesc}</span>
                                    </li>
                                    <li class="myrate__item remind-wrap">
                                        <div>
                                            <i class="c-icon c-icon--call-type2" aria-hidden="true"></i>
                                            <span class="c-hidden">음성</span>
                                            <span>${rateAdsvcCallDesc}</span>
                                        </div>
                                        <div>
                                            <i class="c-icon c-icon--msg-type2" aria-hidden="true"></i>
                                            <span class="c-hidden">문자</span>
                                            <span>${rateAdsvcSmsDesc}</span>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                         <c:if test="${not empty remindBlckYn}">
                            <div class="remind-sms">
                                <div>
                                             리마인드 문자 수신
                                    <button data-tpbox="#remindTp" aria-describedby="#remindTp__title">
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </button>
                                    <div class="c-tooltip-box" id="remindTp" role="tooltip">
                                        <div class="c-tooltip-box__title c-hidden" id="remindTp__title">리마인드 문자란?</div>
                                        <div class="c-tooltip-box__title">리마인드 문자란?</div>
                                        <div class="c-tooltip-box__content">
                                            <ul class="c-text-list c-bullet c-bullet--dot">
                                                <li class="c-text-list__item">제휴 혜택 등록 안내를 위해 제휴 혜택 미등록 고객 대상으로 주기적으로 발송되는 문자입니다.</li>
                                            </ul>
                                        </div>
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
                    </li>
                    <li class="myinfo__item">
                        <c:if test="${isMcashAuth eq 'Y'}">
                            <div class="myinfo__tit">
                                <h3>M쇼핑할인</h3>
                                <div class="myinfo__button-wrap">
                                    <div class="myinfo__button-wrap">
                                        <c:if test="${isMcashUser eq 'Y'}">
                                            <button data-dialog-trigger="#mcashServiceChg" onclick="openMcashCntrChgPop();">
                                                회선 변경
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                            <button class="u-ml--8" id="mcashSvcCan">
                                                서비스 해지
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <div class="myinfo__info">
                                <div class="charge_info">
                                    <table>
                                        <caption>서비스 가입일자, 내 캐시로 구성된 M쇼핑할인 가입정보</caption>
                                        <colgroup>
                                            <col style="width: 10rem;">
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
                                            <td id="remainMcash">-</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </c:if>
                        <div class="${isMcashAuth eq 'Y' ? 'myinfo__tit u-mt--64' : 'myinfo__tit'}">
                            <h3>부가서비스</h3>
                            <div class="myinfo__button-wrap">
                                <a href="javascript:;" url="/mypage/roamingView.do" title="해외 로밍 신청/해지 이동하기" class="link">로밍 신청/해지 <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i></a>
                                <a href="javascript:;" url="/mypage/regServiceView.do" title="부가서비스 신청/해지 이동하기" class="link">부가서비스 신청/해지 <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i></a>
                            </div>
                        </div>
                        <div class="myinfo__info">
                            <ul class="my-addservice">
                                <li class="my-addservice__item">
                                    <p>유료 부가서비스</p>
                                    <p>
                                        <b id="payCtn">-</b>건
                                    </p>
                                </li>
                                <li class="my-addservice__item">
                                    <p>무료 부가서비스</p>
                                    <p>
                                        <b id="freeCtn">-</b>건
                                    </p>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <li class="myinfo__item">
                        <div class="myinfo__tit">
                            <h3>납부정보</h3>
                            <a id="virtualAccountButton" href="javascript:;" title="납부 가상계좌 조회" onclick="openVirtualAccountListPop();">납부 가상계좌 조회<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i></a>
                        </div>
                        <div class="myinfo__info">
                            <div class="charge_info">
                                <table>
                                    <caption>납부방법, 납부계정정보, 명세서유형, 납부기준일로 구성된 납부방법 정보</caption>
                                    <colgroup>
                                        <col style="width: 10rem;">
                                        <col>
                                    </colgroup>
                                    <tbody id="payArea" style="display: none;"></tbody>
                                    <tbody id="loadingPayArea">
                                        <tr>
                                            <td colspan="2" class="u-ta-center u-height--80">조회 중 입니다.</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </li>
                </ul>

                <div class="myinfo-banner">
                    <a class="myinfo-banner__item" href="/mypage/couponList.do" title="My쿠폰 바로가기">
                        <p class="myinfo-banner__title">My쿠폰<span></span></p>
                        <p class="myinfo-banner__text">발급받은 쿠폰은 쿠폰함에서 확인하세요.</p>
                        <img src="/resources/images/portal/mypage/myinfo_banner_bg_01.png" alt="">
                    </a>
                    <a class="myinfo-banner__item" href="/mypage/reCommendState.do" title="친구초대현황 바로가기">
                        <p class="myinfo-banner__title">친구초대현황<span></span></p>
                        <p class="myinfo-banner__text">내 초대 내역을 확인하세요.</p>
                        <img src="/resources/images/portal/mypage/myinfo_banner_bg_02.png" alt="">
                    </a>
                </div>

                <div class="myinfo__tit">
                    <h3>유용한 정보</h3>
                </div>
                <div class="useful-info-list">
                    <a href="/cs/serviceGuide.do" title="고객센터 안내 이동하기" class="loding"> 고객센터 안내 </a>
                    <a href="/cs/csInquiryInt.do" title="1:1 상담문의 이동하기" class="loding"> 1:1 상담문의 </a>
                    <a href="/cs/privacyBoardList.do" title="이용자 피해예방 가이드 이동하기" class="u-fs-15 loding"><span>이용자 피해예방 가이드</span></a>
                    <a href="/cs/deviceAsInfo.do" title="단말기 A/S 안내 이동하기" class="loding"> 단말기 A/S 안내 </a>
                </div>
            </div>
        </div>

        <!-- 리마인드 OFF 변경 -->
        <div class="c-modal c-modal--lg" id="remindOffModal" role="dialog" tabindex="-1" aria-describedby="remindOffModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="complete u-mt--16 u-ta-center">
                            <div class="c-icon c-icon--notice" aria-hidden="true"></div>
                        </div>
                        <div class="c-notice-wrap">
                            <ul class="c-notice__list u-fs-16">
                                <li>고객님이 가입하신 요금제는 제휴 혜택 등록이 필요한 요금제로, 제휴 혜택 미등록 고객을 대상으로 주기적으로 리마인드 문자를 발송 중입니다.</li>
                                <li>수신을 희망하지 않는 고객님께서는 수신 상태 변경을 부탁드립니다.</li>
                                <li>수신 상태 변경 시 익일부터 리마인드 문자 발송이 중단됩니다.</li>
                            </ul>
                        </div>
                        <div class="complete u-ta-center">
                            <h3 class="c-heading c-heading--fs16 c-heading--regular u-mt--48">
                              <b>수신 상태를 변경하시겠습니까?</b>
                            </h3>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--gray u-width--220" data-dialog-close onclick="remindCancelBtn()">취소</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close onclick="remindSucBtn(`Y`)">동의</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 리마인드 ON 변경 -->
        <div class="c-modal c-modal--lg" id="remindOnModal" role="dialog" tabindex="-1" aria-describedby="remindOnModalTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="complete u-mt--16 u-ta-center">
                            <div class="c-icon c-icon--notice" aria-hidden="true"></div>
                        </div>
                        <div class="c-notice-wrap">
                            <ul class="c-notice__list u-fs-16">
                                <li>고객님이 가입하신 요금제는 제휴 혜택 등록이 필요한 요금제로, 리마인드 문자 수신 동의 시 제휴 혜택 미등록 고객을 대상으로 주기적으로 리마인드 문자가 발송될 수 있습니다.</li>
                                <li>수신을 희망하시는 고객님께서는 수신 동의를 부탁드립니다.</li>
                            </ul>
                        </div>
                        <div class="complete u-ta-center">
                            <h3 class="c-heading c-heading--fs16 c-heading--regular u-mt--48">
                              <b>유의사항을 확인하였으며,<br />제휴 혜택 등록 리마인드 문자 수신에 동의합니다.</b>
                            </h3>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--gray u-width--220" data-dialog-close onclick="remindCancelBtn()">취소</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close onclick="remindSucBtn(`N`)">동의</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>