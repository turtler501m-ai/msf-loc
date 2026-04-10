<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">부가서비스 신청/해지</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript"  src="/resources/js/portal/mypage/regServiceView.js?version=23-04-18"></script>
        <script type="text/javascript" src="/resources/js/jquery.number.min.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="underAge" value="${underAge}">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">부가서비스 신청/해지</h2>
            </div>
            <div class="c-tabs c-tabs--type1">
                <div class="c-tabs__list" role="tablist">
                    <button class="c-tabs__button" id="tab1" role="tab" aria-controls="tabB1panel" aria-selected="true">조회/변경</button>
                    <button class="c-tabs__button" id="tab2" role="tab" aria-controls="tabB2panel" aria-selected="false">신청</button>
                </div>
            </div>

            <div class="c-row c-row--lg">
                <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">조회할 회선을 선택해 주세요.</h3>
                <%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>
                <hr class="c-hr c-hr--title">
            </div>

            <div class="c-tabs__panel" id="tabB1panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
                <div class="c-row c-row--lg">
                    <div class="c-nodata noAddSvcList" style="display: none;">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-nodata__text">이용중인 부가서비스가 없습니다.</p>
                    </div>
                    <div class="c-table" id="addSvcData">
                        <table>
                            <caption>부가서비스명, 이용요금 (VAT포함), 해지로 구성된 현재 사용중인 부가서비스 정보</caption>
                            <colgroup>
                                <col style="width: 33.33%">
                                <col style="width: 33.33%">
                                <col>
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">부가서비스명</th>
                                    <th scope="col">이용요금 (VAT포함)</th>
                                    <th scope="col">해지</th>
                                </tr>
                            </thead>
                            <tbody id="addSvcList">

                            </tbody>
                        </table>
                    </div>
                    <b class="c-flex c-text c-text--fs15 u-mt--48">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                        <span class="u-ml--4">알려드립니다</span>
                    </b>
                    <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                        <li class="c-text-list__item u-co-gray">월 중 부가서비스 가입 및 해지 시 이용요금 및 제공 혜택은 이용시간 동안 사용한 날만큼 계산됩니다.</li>
                        <li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
                    </ul>
                </div>
            </div>
            <div class="c-tabs__panel" id="tabB2panel" role="tabpanel" aria-labelledby="tab2" tabindex="-1">
                <div class="c-row c-row--lg">
                        <div class="c-accordion c-accordion--type4 extra-service u-mb--48" data-ui-accordion="">
                            <ul class="c-accordion__box">
                                <li class="c-accordion__item">
                                    <div class="c-accordion__head">
                                        <div class="c-accordion__check">
                                            <input class="c-radio" id="callWaiting0" type="checkbox" name="radServ">
                                            <label class="c-label" for="callWaiting0">통화중대기 설정/해제 (무료)</label>
                                        </div>
                                        <button class="runtime-data-insert c-accordion__button" id="acc_headerC0" type="button" aria-expanded="false" aria-controls="acc_contentC0">
                                            <span class="c-hidden">통화중대기 설정/해제 (무료)</span>
                                        </button>
                                    </div>
                                    <div class="c-accordion__panel expand" id="acc_contentC0" aria-labelledby="acc_headerC0">
                                        <div class="c-accordion__inside u-pa--0" id="callWaiting"></div>
                                    </div>
                                </li>
                            </ul>
                        </div>

                    <h4 class="c-heading c-heading--fs20">
                        <b>가입 가능한 무료 부가서비스</b>
                    </h4>
                    <div class="c-nodata noDataFree" style="display: none;">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-nodata__text">신청 가능한 부가서비스가 없습니다.</p>
                    </div>
                    <div class="c-accordion c-accordion--type4 extra-service u-mt--24 freeDataYn" data-ui-accordion>
                        <ul class="c-accordion__box freeDataList">
                        </ul>
                    </div>
                    <h4 class="c-heading c-heading--fs20 u-mt--64">
                        <b>가입 가능한 유료 부가서비스</b>
                    </h4>
                    <div class="c-nodata noDataFreeView" style="display: none;">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-nodata__text">신청 가능한 부가서비스가 없습니다.</p>
                    </div>
                    <div class="c-accordion c-accordion--type4 extra-service u-mt--24 noFreeDataYn" data-ui-accordion>
                        <ul class="c-accordion__box noFreeDataList">
                        </ul>
                    </div>
                    <div class="c-button-wrap u-mt--56">
                        <button class="c-button c-button--lg c-button--primary u-width--460" id="btn_regSvc" type="button" onclick="btn_regSvc();">부가서비스 신청</button>
                    </div>
                    <b class="c-flex c-text c-text--fs15 u-mt--48">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                        <span class="u-ml--4">알려드립니다</span>
                    </b>
                    <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                        <li class="c-text-list__item u-co-gray">당일 신청하신 서비스는 당일 해지 하실 수 없습니다.</li>
                        <li class="c-text-list__item u-co-gray">부가서비스 월중 신청/해지 시 해당월 월정액 및 무료제공 혜택은 각각 일할 적용됩니다.</li>
                        <li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
                    </ul>
                </div>
            </div>
        </div>
    </t:putAttribute>

    <t:putAttribute name="popLayerAttr">


        <!-- 부가서비스 해지  -->
        <div class="c-modal c-modal--lg850" id="divRegSvcCan" role="dialog" aria-labelledby="divRegSvcCanTitle">
            <div class="c-modal__dialog" role="document">

                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <div class="c-modal__title" id="divRegSvcCanTitle">부가서비스 해지</div>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">



                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--gray u-width--220"  data-dialog-close>취소</button>
                            <button id="btnPrdSvcCan" class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close>해지</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    <div class="c-modal c-modal--md" id="regServiceModal" role="dialog" aria-labelledby="regServiceModalTitle">
        <div class="c-modal__dialog" role="document">
          <div class="c-modal__content">
            <div class="c-modal__header">
              <h2 class="c-modal__title" id="regServiceModalTitle">통화중대기 설정/해제 (무료)</h2>
              <button class="c-button" data-dialog-close>
                <i class="c-icon c-icon--close" aria-hidden="true"></i>
                <span class="c-hidden">팝업닫기</span>
              </button>
            </div>
            <div class="c-modal__body">
                <ul class="c-text-list c-bullet c-bullet--dot">
                    <li class="c-text-list__item u-fs-16 u-mt--0">통화중대기 서비스를 정상적으로 이용 및 해지하려면 아래의 절차를 진행해주세요.<br /><span class="u-co-red">(절차가 완료되지 않으면 서비스가 정상적으로 반영되지 않습니다)</span></li>
                    <li class="c-text-list__item u-fs-16 u-mt--24">
                            서비스 신청방법
                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                            <li class="c-text-list__item u-co-gray u-fs-16">설정: *+40+통화→ “서비스가 정상적으로 설정되었습니다” → 종료</li>
                            <li class="c-text-list__item u-co-gray u-fs-16">해제: *+400+통화→ “서비스가 정상적으로 해제되었습니다” → 종료</li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="c-modal__footer">
              <div class="c-button-wrap">
                <button class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close>확인</button>
              </div>
            </div>
          </div>
        </div>
    </div>


    </t:putAttribute>


</t:insertDefinition>
