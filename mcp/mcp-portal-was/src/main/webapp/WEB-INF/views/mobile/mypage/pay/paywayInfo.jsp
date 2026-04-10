<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/mypage/pay/paywayInfo.js?version=${jsver}"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    납부방법 변경
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
            <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>
            <div class="c-form__title flex-type">
                <h4 class="c-heading u-fw-normal u-fs-20">이용중인 납부방법</h4>
                <a id="virtualAccountButton" class="c-button c-button--sm u-co-mint c-expand" onclick="openVirtualAccountListPop()">납부 가상계좌 조회<i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i></a>
            </div>
            <div class="info-box payway">
                <c:choose>
                    <c:when test="${payMethod eq '자동이체'}">
                        <dl>
                            <dt>납부방법</dt>
                            <dd>${payMethod}</dd>
                        </dl>
                        <dl>
                            <dt>납부일자</dt>
                            <dd>${billCycleDueDay}</dd>
                        </dl>
                        <dl>
                            <dt>납부계정정보</dt>
                            <dd>${blBankAcctNo}</dd>
                        </dl>
                        <dl>
                            <dt>명세서유형</dt>
                            <dd>${reqType}</dd>
                        </dl>
                        <c:if test="${billTypeCd ne 'MB'}">
                            <dl>
                                <dt>${reqTypeNm}</dt>
                                <dd>${blaAddr}</dd>
                            </dl>
                        </c:if>
                    </c:when>

                    <c:when test="${payMethod eq '지로'}">
                        <dl>
                            <dt>납부방법</dt>
                            <dd>${payMethod}</dd>
                        </dl>
                        <dl>
                            <dt>납부일자</dt>
                            <dd>${billCycleDueDay}</dd>
                        </dl>
                        <dl>
                            <dt>명세서유형</dt>
                            <dd>${reqType}</dd>
                        </dl>
                        <dl>
                            <dt>${reqTypeNm}</dt>
                            <dd>${blaAddr}</dd>
                        </dl>
                    </c:when>

                    <c:when test="${payMethod eq '신용카드'}">
                        <dl>
                            <dt>납부방법</dt>
                            <dd>${payMethod}</dd>
                        </dl>
                        <dl>
                            <dt>납부기준일</dt>
                            <dd>${payTmsCd}</dd>
                        </dl>
                        <dl>
                            <dt>카드번호</dt>
                            <dd>${prevCardNo}</dd>
                        </dl>
                        <dl>
                            <dt>명세서유형</dt>
                            <dd>${reqType}</dd>
                        </dl>
                        <c:if test="${billTypeCd ne 'MB'}">
                            <dl>
                                <dt>${reqTypeNm}</dt>
                                <dd>${blaAddr}</dd>
                            </dl>
                        </c:if>
                    </c:when>

                    <c:when test="${payMethod eq '간편결제'}">
                        <dl>
                            <dt>납부방법</dt>
                            <dd>${payMethod}</dd>
                        </dl>
                        <dl>
                            <dt>납부기준일</dt>
                            <dd>지정일</dd>
                        </dl>
                        <dl>
                            <dt>납부계정정보</dt>
                            <dd>${billCycleDueDay}</dd>
                        </dl>
                        <dl>
                            <dt>명세서유형</dt>
                            <dd>${reqType}</dd>
                        </dl>
                        <dl>
                            <dt>${reqTypeNm}</dt>
                            <dd>${blaAddr}</dd>
                        </dl>
                    </c:when>
                </c:choose>
            </div>
            <div class="c-button-wrap u-mt--36">
                <button id="btnMovePage" class="c-button c-button--full c-button--mint"   title="납부방법 변경 페이지 이동">납부방법 변경</button>
            </div>

            <div class="c-accordion c-accordion--type1 payway u-mt--32 u-mb--48">
                <ul class="c-accordion__box" id="accordionPayway">
                    <li class="c-accordion__item">
                        <div class="c-accordion__head" data-acc-header>
                            <button class="c-accordion__button u-fw-normal" type="button" aria-expanded="false">변경 가능한 납부방법</button>
                        </div>
                        <div class="c-accordion__panel expand">
                            <div class="c-accordion__inside">
                                <div class="c-table">
                                    <table>
                                      <caption>가능 납부방법, 선택 가능 납부일, 입금 확인일로 구성된 변경가능한 납부방법</caption>
                                      <colgroup>
                                        <col>
                                        <col>
                                        <col>
                                      </colgroup>
                                      <thead>
                                        <tr>
                                          <th scope="col">가능 납부방법</th>
                                          <th scope="col">선택 가능 납부일</th>
                                          <th scope="col">입금 확인일</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <tr>
                                          <td>은행계좌</td>
                                          <td>매월<br/>18일, 21일, 25일</td>
                                          <td>1~3일 후</td>
                                        </tr>
                                        <tr>
                                          <td>간편결제<br/>(카카오/페이코)</td>
                                          <td>1차(21일),<br/>2차(25일)</td>
                                          <td>1일 후</td>
                                        </tr>
                                        <tr>
                                          <td>
                                              신용/체크카드<br />
                                              <button class="c-text-link--bluegreen-type2 u-co-mint u-ml--0" onclick="javascript:openPage('pullPopup', '/event/cprtCardBnfitLayer.do?cprtCardCtgCd=PCARD00011', '', 0);">제휴카드 혜택 안내</button>
                                          </td>
                                          <td>1차(11일경),<br/>2차(20일경)</td>
                                          <td>2~4일 후</td>
                                        </tr>
                                      </tbody>
                                    </table>
                                </div>
                                <ul class="c-text-list c-bullet c-bullet--dot u-fs-14 u-mt--16">
                                    <li class="c-text-list__item u-co-gray">상품 가입 명의자의 계좌, 간편결제(카카오페이/페이코) 또는 신용/체크카드로만 변경 가능합니다.</li>
                                    <li class="c-text-list__item u-co-gray">타인명의의 계좌 또는 신용/체크카드로 변경 하시려면, 고객센터 전화상담을 통해 가능하며, 이 경우에는 납부자(예금주) 신분증, 명의자 신분증, 납부자(예금자)가 서명한 동의서 원본 등 구비서류가 필요합니다.</li>
                                </ul>
                            </div>
                        </div>
                    </li>
                    <li class="c-accordion__item">
                        <div class="c-accordion__head" data-acc-header>
                            <button class="c-accordion__button u-fw-normal" type="button" aria-expanded="false">납부방법 변경 적용 시점</button>
                        </div>
                        <div class="c-accordion__panel expand">
                            <div class="c-accordion__inside">
                                <div class="c-table">
                                    <table>
                                        <caption>납부방법 변경 적용 시점</caption>
                                        <colgroup>
                                            <col>
                                            <col>
                                            <col>
                                        </colgroup>
                                        <thead></thead>
                                        <tr>
                                            <th scope="col">구분</th>
                                            <th scope="col">이번달 적용</th>
                                            <th scope="col">다음달 적용</th>
                                        </tr>
                                        <tbody>
                                            <tr>
                                                <td>납부 기준일만<br/>변경하는 경우</td>
                                                <td>매월 3일<br/>까지 신청</td>
                                                <td>매월 3일<br/>이후 신청</td>
                                            </tr>
                                            <tr>
                                                <td>동일한 수단으로<br/>변경하는 경우<br/>(예)카드<->카드
                                                </td>
                                                <td>변경하는 납부기준일<br/>로 부터 2일 이전까지<br/>신청 시<br/>(영업일 기준)</td>
                                                <td>변경하는 납부기준일<br/>로 부터 1일 이하로<br/>남아있을 경우<br/>(영업일 기준)</td>
                                            </tr>
                                            <tr>
                                                <td>다른 수단으로<br/>변경하는 경우<br/>(예)카드<->은행</td>
                                                <td>이번달 미적용<br/>(기존 납부<br/>방법으로 출금)</td>
                                                <td>다음달부터<br/>변경된 납부방법으로<br/>적용</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <b class="c-text c-text--type3">
                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
            </b>
            <ul class="c-text-list c-bullet c-bullet--dot u-mt--16 u-mb--32">
                <li class="c-text-list__item u-co-gray">자동납부(계좌/카드)를 이용하시는 경우, 당월 청구금액 납부 시 이중 납부가 될 수 있으니납부일 및 계좌/카드 거래내역 확인 후 이용하시기 바랍니다.</li>
                <li class="c-text-list__item u-co-gray">입금확인 일자는 은행,신용카드 등 에서 kt M모바일에 납부사실을 통보한 일자로, 납부하신 날짜와 입금확인 일자가 다소 차이가 날 수 있습니다.</li>
                <li class="c-text-list__item u-co-gray">납부사실 통보에 소요되는 시간이 각 은행과 신용카드 기관별로 차이가 있기 때문에, 입금확인 일자가 서로 다를 수 있습니다.</li>
                <li class="c-text-list__item u-co-gray">미납 요금계에 마이너스(-)로 표기된 경우는 지난달 과납 등으로 발생된 금액입니다.</li>
                <li class="c-text-list__item u-co-gray">상세내역은 최근 6개월까지 요금내역만 제공됩니다.</li>
                <li class="c-text-list__item u-co-gray">미납요금을 납부한 고객일지라도, 명세서 상에 미납요금이 표기되어 있을 수 있습니다. (명세서 상 미납요금은 청구시점의 미납요금이 표기된 것으로 현 시점의 미납요금과 상이할 수 있음)</li>
                <li class="c-text-list__item u-co-gray">납부변경 적용일 동일한 납부방법으로 변경 시 : 최소 3일 전까지 변경시 당월적용(변경 요청 시점이 은행, 카드사로 당월 납부요청이 지난 후 에는 변경한 납부방법이 다음달로 예약)</li>
                <li class="c-text-list__item u-co-gray">다른 납부방법으로 변경 시 : 익월적용</li>
            </ul>
        </div>
    </t:putAttribute>
</t:insertDefinition>