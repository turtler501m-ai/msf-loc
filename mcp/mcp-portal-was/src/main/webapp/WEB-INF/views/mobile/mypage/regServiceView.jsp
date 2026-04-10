<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/mypage/regServiceView.js?version=23-04-19"></script>
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
</t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="underAge" value="${underAge}">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    부가서비스 신청/해지
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="c-tabs c-tabs--type2" data-tab-active="0">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button" id="tab1" data-tab-header="#tabB1-panel" onclick="btn_tab('1')">조회/변경</button>
                    <button class="c-tabs__button" id="tab2" data-tab-header="#tabB2-panel" onclick="btn_tab('2')">신청</button>
                </div>
                <hr class="c-hr c-hr--type3 c-expand">
                <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
                <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>
                <!--   부가서비스 이용/해지  -->
                <div class="c-tabs__panel border-none" id="tabB1-panel">
                    <div class="c-nodata regSvcYn" style="display: none;">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-noadat__text">변경/해지 가능한 부가서비스가 없습니다.</p>
                    </div>
                    <div class="c-table c-table--plr-8 regDataYn">
                        <table>
                            <caption>부가서비스 정보</caption>
                            <colgroup>
                                <col style="width: 44%">
                                <col style="width: 28%">
                                <col style="width: 28%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">부가서비스명</th>
                                    <th scope="col">
                                        <p>이용요금</p>
                                        <p>(VAT포함)</p>
                                    </th>
                                    <th scope="col">해지</th>
                                </tr>
                            </thead>
                            <tbody id="addSvcList">

                            </tbody>
                        </table>
                        <hr class="c-hr c-hr--type2">
                        <b class="c-text c-text--type3">
                            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
                        </b>
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item u-co-gray">월 중 부가서비스 가입 및 해지 시 이용요금 및 제공 혜택은 이용시간 동안 사용한 날만큼 계산됩니다.</li>
                            <li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
                        </ul>
                    </div>
                </div>

                <div class="c-tabs__panel border-none" id="tabB2-panel">
                            <div class="c-accordion c-accordion--type3">
                                  <ul class="c-accordion__box acco-border">
                                      <li class="c-accordion__item">
                                          <div class="c-accordion__head">
                                              <div class="c-accordion__check">
                                                  <input class="c-radio" id="callWaiting0" type="checkbox" name="chkAS">
                                                  <label class="c-label" for="callWaiting0">통화중대기 설정/해제 (무료)</label>
                                              </div>
                                              <button class="c-accordion__button u-ta-right" type="button" data-acc-header="#acc_contentC0" aria-expanded="false">
                                                  <div class="c-accordion__trigger"></div>
                                              </button>
                                          </div>
                                          <div class="c-accordion__panel c-expand expand" id="acc_contentC0">
                                              <div class="c-accordion__inside">
                                                  <div class="c-text c-text--type3" id="callWaiting"></div>
                                              </div>
                                          </div>
                                      </li>
                                  </ul>
                              </div>

                    <h4 class="c-heading c-heading--type3 u-mt--40 u-mb--8">가입 가능한 무료 부가서비스</h4>
                    <div class="c-nodata noDataFree" style="display: none;">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-noadat__text">신청 가능한 부가서비스가 없습니다.</p>
                    </div>
                    <div class="c-accordion c-accordion--type3 freeDataYn">
                        <ul data-acc-observer class="c-accordion__box acco-border chargeAccordion freeDataList">	</ul>
                    </div>
                    <h4 class="c-heading c-heading--type3 u-mt--40 u-mb--8">가입 가능한 유료 부가서비스</h4>
                    <div class="c-accordion c-accordion--type3 u-mb--40 noFreeDataYn">
                        <ul data-acc-observer class="c-accordion__box acco-border noFreeDataList"></ul>
                    </div>
                    <div class="c-nodata noDataFreeView" style="display: none;">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-noadat__text">신청 가능한 부가서비스가 없습니다.</p>
                    </div>
                    <div class="c-button-wrap u-mt--48 u-mb--40">
                        <button class="c-button c-button--full c-button--primary" id="btn_regSvc" onclick="btn_regSvc();">부가서비스 신청</button>
                    </div>
                    <hr class="c-hr c-hr--type2">
                    <b class="c-text c-text--type3">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
                    </b>
                    <ul class="c-text-list c-bullet c-bullet--dot">
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
        <div class="c-modal c-modal--lg850" id="divRegSvcCan" role="dialog" tabindex="-1" aria-labelledby="divRegSvcCanTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
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
                            <button class="c-button c-button--gray c-button--lg u-width--120"  data-dialog-close>취소</button>
                            <button id="btnPrdSvcCan" class="c-button c-button--full c-button--primary" data-dialog-close>해지</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


     <div class="c-modal" id="regServiceModal" role="dialog" tabindex="-1" aria-describedby="#regServiceModalTitle">
        <div class="c-modal__dialog c-modal__dialog--full" role="document">
          <div class="c-modal__content">
            <div class="c-modal__header">
              <h1 class="c-modal__title" id="regServiceModalTitle">통화중대기 설정/해제 (무료)</h1>
              <button class="c-button" data-dialog-close>
                <i class="c-icon c-icon--close" aria-hidden="true"></i>
                <span class="c-hidden">팝업닫기</span>
              </button>
            </div>
            <div class="c-modal__body">
                <ul class="c-text-list c-bullet c-bullet--dot">
                    <li class="c-text-list__item u-fs-14">통화중대기 서비스를 정상적으로 이용 및 해지하려면 아래의 절차를 진행해주세요.<br /><span class="u-co-red">(절차가 완료되지 않으면 서비스가 정상적으로 반영되지 않습니다)</span></li>
                    <li class="c-text-list__item u-mt--16 u-fs-14">
                            서비스 신청방법
                        <ul class="c-text-list c-bullet c-bullet--hyphen">
                            <li class="c-text-list__item u-co-gray u-fs-14">설정: *+40+통화→ “서비스가 정상적으로 설정되었습니다” → 종료</li>
                            <li class="c-text-list__item u-co-gray u-fs-14">해제: *+400+통화→ “서비스가 정상적으로 해제되었습니다” → 종료</li>
                        </ul>
                    </li>
                </ul>
            </div>
             <div class="c-modal__footer">
                <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
              </div>
          </div>
        </div>
    </div>

    </t:putAttribute>
</t:insertDefinition>