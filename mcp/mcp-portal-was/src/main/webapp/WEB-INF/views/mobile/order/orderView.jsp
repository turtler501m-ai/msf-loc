<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="delivery-inquiry-title">가입신청 배송조회</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>

        <div class="c-modal__body overflow-x-hidden">
          <div class="c-flex c-flex--jfy-between u-mt--32">
            <h3 class="c-heading c-heading--type5 u-mt--0 u-mb--0">주문번호: ${view.resNo }</h3>
            <span class="u-co-gray-7"><fmt:parseDate value="${view.sysRdate}" pattern="yyyyMMdd" var="sysRdate"/><fmt:formatDate value="${sysRdate}" pattern="yyyy.MM.dd" type="both"/></span>
          </div>
          <ol class="deli-step c-expand u-mt--16">
            <!-- 상태값 있을 경우, 클래스 .is-active 추가-->
            <li
            <c:if test="${view.requestStateCode eq '00'}">class="is-active"</c:if>
            >
              <i class="c-icon c-icon--accept-ready" aria-hidden="true"></i>
              <span>접수대기</span>
            </li>
            <li
            <c:if test="${view.requestStateCode eq '01' || view.requestStateCode eq '02' || view.requestStateCode eq '09' || view.requestStateCode eq '07' || view.requestStateCode eq '08'}">class="is-active"</c:if>
            >
              <i class="c-icon c-icon--delivery-ready" aria-hidden="true"></i>
              <span>배송대기</span>
            </li>
            <li
            <c:if test="${view.requestStateCode eq '03' || view.requestStateCode eq '04' || view.requestStateCode eq '10'}">class="is-active"</c:if>
            >
              <i class="c-icon c-icon--delivery" aria-hidden="true"></i>
              <span>배송중</span>
            </li>
            <li
            <c:if test="${view.requestStateCode eq '20' || view.requestStateCode eq '11' || view.requestStateCode eq '13'}">class="is-active"</c:if>
            >
              <i class="c-icon c-icon--open-ready" aria-hidden="true"></i>
              <span>개통대기</span>
            </li>
            <li
            <c:if test="${view.requestStateCode eq '21'}">class="is-active"</c:if>
            >
              <i class="c-icon c-icon--open-complete" aria-hidden="true"></i>
              <span>개통완료</span>
            </li>
          </ol>
          <h3 class="c-heading c-heading--type2 u-mb--12">주문 상품정보</h3>
          <div class="c-item c-item--type3 u-mt--0">

            <c:if test="${!empty apdView}">

            <div class="c-item__list">
              <div class="c-item__image">
              <c:if test="${apdView.reqBuyType eq 'UU'}">
                  <img src="/resources/images/mobile/phone/img_product_02.png" alt="">
              </c:if>
              <c:if test="${apdView.reqBuyType eq 'MM'}">
                <img src="${apdView.imgPath}" alt="${apdView.imgPath}" onerror="this.src='/resources/images/mobile/content/img_noimage.png'">
              </c:if>
              </div>
              <div class="c-item__item">
                <span class="c-text c-text--type4 u-co-point-4">
                  <c:if test="${apdView.requestStateCode eq '00'}">접수대기</c:if>
                  <c:if test="${apdView.requestStateCode eq '01' || apdView.requestStateCode eq '02' || apdView.requestStateCode eq '09' || apdView.requestStateCode eq '07' || apdView.requestStateCode eq '08'}">배송대기</c:if>
                  <c:if test="${apdView.requestStateCode eq '03' || apdView.requestStateCode eq '04' || apdView.requestStateCode eq '10'}">배송중</c:if>
                  <c:if test="${apdView.requestStateCode eq '20' || apdView.requestStateCode eq '11' || apdView.requestStateCode eq '13'}">개통대기</c:if>
                  <c:if test="${apdView.requestStateCode eq '21'}">개통완료</c:if>
                  <c:if test="${apdView.requestStateCode eq '30' || apdView.requestStateCode eq '31' }">신청취소</c:if>
                </span>
                <div class="c-item__title">
                  <strong>
                      <c:choose>
                          <c:when test="${apdView.reqBuyType eq 'UU'}">
                              유심
                          </c:when>
                          <c:otherwise>
                              <c:out value="${apdView.prodNm}" default="-" />
                          </c:otherwise>
                    </c:choose>
                  </strong>
                </div>
                <div class="c-item__info">
                <c:if test="${apdView.reqBuyType eq 'MM'}">
                  <span class="c-text c-text--type3 u-co-gray"><c:out value="${apdView.atribValNmOne}" default="-" />/<c:out value="${apdView.atribValNmTwo}" default="-" /></span>
                </c:if>
                <c:if test="${apdView.reqBuyType eq 'UU'}">
                  <span class="c-text c-text--type3 u-co-gray"><c:out value="${apdView.rateNm}" default="-" /></span>
                </c:if>
                  <span class="c-text c-text--type3 u-co-gray">
                     <c:if test="${apdView.reqBuyType eq 'MM'}">
                        <fmt:formatNumber value="${apdView.hndsetSalePrice - apdView.usePoint - apdView.cardDcAmt}" pattern="#,###"/>원<!-- 단말상품가격 -->
                    </c:if>
                    <c:if test="${apdView.reqBuyType eq 'UU' and empty apdView.prodId}">
                        <fmt:formatNumber value="${saleInfo.baseVatAmt}" pattern="#,###"/>원<!-- 중고단말상품가격 -->
                    </c:if>
                  </span>
                </div>
              </div>
              <c:set var="codeList" value="${nmcp:getCodeList('PERCEL')}" />
              <c:forEach items="${codeList }" var="codeList" >
                  <c:if test="${apdView.tbCd eq codeList.dtlCd}">
                      <c:set value="${codeList.dtlCdNm }" var="percelNm"/>
                      <c:set value="${codeList.dtlCdDesc }" var="percelUrl"/>
                  </c:if>
              </c:forEach>

              <c:choose>
                   <c:when test="${apdView.requestStateCode eq '03' || apdView.requestStateCode eq '04' || apdView.requestStateCode eq '10' || apdView.requestStateCode eq '20' || apdView.requestStateCode eq '11' || apdView.requestStateCode eq '13' || apdView.requestStateCode eq '21'}">
                      <c:choose>
                          <c:when test="${platFormCd eq 'A'}">
                              <button type="button" class="c-button c-button--sm c-button--white" onclick="appOutSideOpen('${percelUrl}${apdView.dlvryNo}');" <c:if test="${empty percelUrl || empty view.dlvryNo}">disabled="disabled"</c:if> >배송조회</button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="c-button c-button--sm c-button--white" onclick="openPage('spaLink', '${percelUrl}${apdView.dlvryNo}', '');" <c:if test="${empty percelUrl || empty view.dlvryNo}">disabled="disabled"</c:if> >배송조회</button>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                      <button type="button" class="c-button c-button--sm c-button--white" onclick="openPage('spaLink', '${percelUrl}${apdView.dlvryNo}', '');" disabled="disabled">배송조회</button>
                </c:otherwise>
            </c:choose>
            </div>
            </c:if>

            <div class="c-item__list">
              <div class="c-item__image">
              <c:if test="${view.reqBuyType eq 'UU'}">
                  <img src="/resources/images/mobile/phone/img_product_02.png" alt="">
              </c:if>
              <c:if test="${view.reqBuyType eq 'MM'}">
                <img src="${view.imgPath}" alt="${view.imgPath}" onerror="this.src='/resources/images/mobile/content/img_noimage.png'">
              </c:if>
              </div>
              <div class="c-item__item">
                <span class="c-text c-text--type4 u-co-point-4">
                <c:if test="${view.requestStateCode eq '00'}">접수대기</c:if>
                <c:if test="${view.requestStateCode eq '01' || view.requestStateCode eq '02' || view.requestStateCode eq '09' || view.requestStateCode eq '07' || view.requestStateCode eq '08'}">배송대기</c:if>
                <c:if test="${view.requestStateCode eq '03' || view.requestStateCode eq '04' || view.requestStateCode eq '10'}">배송중</c:if>
                <c:if test="${view.requestStateCode eq '20' || view.requestStateCode eq '11' || view.requestStateCode eq '13'}">개통대기</c:if>
                <c:if test="${view.requestStateCode eq '21'}">개통완료</c:if>
                <c:if test="${view.requestStateCode eq '30' || view.requestStateCode eq '31' }">신청취소</c:if>
                </span>
                <div class="c-item__title">
                  <strong>
                      <c:choose>
                          <c:when test="${view.reqBuyType eq 'UU'}">
                              유심
                          </c:when>
                          <c:otherwise>
                              <c:out value="${view.prodNm}" default="-" />
                          </c:otherwise>
                    </c:choose>
                  </strong>
                </div>
                <div class="c-item__info">
                <c:if test="${view.reqBuyType eq 'MM'}">
                  <span class="c-text c-text--type3 u-co-gray"><c:out value="${view.atribValNmOne}" default="-" />/<c:out value="${view.atribValNmTwo}" default="-" /></span>
                </c:if>
                <c:if test="${view.reqBuyType eq 'UU'}">
                  <span class="c-text c-text--type3 u-co-gray"><c:out value="${view.rateNm}" default="-" /></span>
                </c:if>
                  <span class="c-text c-text--type3 u-co-gray">
                     <c:if test="${view.reqBuyType eq 'MM'}">
                        <fmt:formatNumber value="${view.modelPrice+(view.modelPrice*0.1)}" pattern="#,###"/>원<!-- 단말상품가격 -->
                    </c:if>
                    <c:if test="${view.reqBuyType eq 'UU'}">
                        <fmt:formatNumber value="${saleInfo.baseVatAmt}" pattern="#,###"/>원<!-- 중고단말상품가격 -->
                    </c:if>
                  </span>
                </div>
              </div>
              <c:set var="codeList" value="${nmcp:getCodeList('PERCEL')}" />
              <c:forEach items="${codeList }" var="codeList" >
                  <c:if test="${view.tbCd eq codeList.dtlCd}">
                      <c:set value="${codeList.dtlCdNm }" var="percelNm"/>
                      <c:set value="${codeList.dtlCdDesc }" var="percelUrl"/>
                  </c:if>
              </c:forEach>
              <c:choose>
                <c:when test="${view.requestStateCode eq '03' || view.requestStateCode eq '04' || view.requestStateCode eq '10' || view.requestStateCode eq '20' || view.requestStateCode eq '11' || view.requestStateCode eq '13' || view.requestStateCode eq '21'}">

                      <c:choose>
                          <c:when test="${platFormCd eq 'A'}">
                              <button type="button" class="c-button c-button--sm c-button--white" onclick="appOutSideOpen('${percelUrl}${view.dlvryNo}');" <c:if test="${empty percelUrl || empty view.dlvryNo}">disabled="disabled"</c:if> >배송조회</button>
                        </c:when>
                        <c:otherwise>
                              <button type="button" class="c-button c-button--sm c-button--white" onclick="openPage('spaLink', '${percelUrl}${view.dlvryNo}', '');" <c:if test="${empty percelUrl || empty view.dlvryNo}">disabled="disabled"</c:if> >배송조회</button>
                        </c:otherwise>
                    </c:choose>

                </c:when>
                <c:otherwise>
                      <button type="button" class="c-button c-button--sm c-button--white" onclick="openPage('spaLink', '${percelUrl}${view.dlvryNo}', '');" disabled="disabled">배송조회</button>
                </c:otherwise>
              </c:choose>
            </div>


          </div>



          <h3 class="c-heading c-heading--type2 u-mb--12">주문 상세내역</h3>
          <div class="c-table">
            <table>
              <caption>주문 상세내역</caption>
              <colgroup>
                <col style="width: 32%">
                <col style="width: 68%">
              </colgroup>
              <tbody>
                <tr>
                  <th>신청요금제</th>
                  <td class="u-ta-left">${view.rateNm }</td>
                </tr>
                <tr>
                  <th>요금납부방법</th>
                  <td class="u-ta-left">
                      <c:choose>
                        <c:when test="${view.reqPayType eq 'C'}">
                            신용카드 (${view.reqCardCompany} ${view.reqCardNo})
                        </c:when>
                        <c:when test="${view.reqPayType eq 'D'}">
                            <c:set var="codeList" value="${nmcp:getCodeList('BNK')}" />
                             <c:forEach items="${codeList }" var="codeList" >
                                 <c:if test="${view.reqBank eq codeList.dtlCd}"><c:set value="${codeList.dtlCdNm }" var="bnkNm"/></c:if>
                                </c:forEach>
                                계좌이체 (${bnkNm} ${view.reqAccountNumber})
                        </c:when>
                        <c:when test="${view.reqPayType eq 'VA'}">
                            가상계좌
                        </c:when>
                        <c:when test="${view.reqPayType eq 'AA'}">
                            자동충전
                        </c:when>
                        <c:when test="${view.reqPayType eq 'R'}">
                            지로
                        </c:when>
                       </c:choose>
                  </td>
                </tr>
                <tr>
                  <th>배송지 정보</th>
                  <td class="u-ta-left">
                  <c:set var="dlvryAddr">${view.dlvryAddr} ${view.dlvryAddrDtl}</c:set>
                  ${view.dlvryPost}<br/>
                  ${nmcp:getMaskedByAddressNew2(dlvryAddr)}
                  </td>
                </tr>
                <tr>
                  <th>주문자명</th>
                  <td class="u-ta-left">${nmcp:getMaskedName(view.cstmrName )}<br/>
                                 <c:set value="${view.cstmrMobileFn}-${view.cstmrMobileMn}-${view.cstmrMobileRn}" var="cstmrMobile" />
                </tr>
                <tr>
                  <th>연락처</th>
                  <td class="u-ta-left">${nmcp:getMaskedTelNo(cstmrMobile)}</td>
                </tr>
                <tr>
                  <th>이메일 주소</th>
                  <td class="u-ta-left">${nmcp:getMaskedEmail2(view.cstmrMail)}</td>
                </tr>
              </tbody>
            </table>
          </div>

          <c:if test="${view.reqBuyType eq 'MM' }">
          <hr class="c-hr c-hr--type1 c-expand"><!-- case1 : 주문 상품이 휴대폰인 경우-->
          <h3 class="c-heading c-heading--type5">단말기/월 요금 상세</h3>
          <p class="c-bullet c-bullet--dot u-co-gray">프로모션 대상 요금제의 경우 익월 청구서를 통해 할인내역을 확인하실 수 있습니다.</p>
          <hr class="c-hr c-hr--type2 u-mt--24">
          <div class="c-addition-wrap">
            <span class="c-text-label c-text-label--mint u-m--0 u-mb--20">${view.prodNm}</span>
            <dl class="c-addition c-addition--type1">
              <dt>단말요금(A)</dt>
              <dd class="u-ta-right">
                <b><fmt:formatNumber value="${saleInfo.payMnthAmt}" pattern="#,###"/></b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>단말기 출고가</dt>
              <dd class="u-ta-right"><fmt:formatNumber value="${view.modelPrice+view.modelPriceVat}" pattern="#,###"/>원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>공통지원금</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">-<fmt:formatNumber value="${view.modelDiscount2}" pattern="#,###"/></b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>추가지원금</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- <fmt:formatNumber value="${view.modelDiscount3}" pattern="#,###"/></b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>할부원금</dt>
              <dd class="u-ta-right"><fmt:formatNumber value="${saleInfo.instAmt}" pattern="#,###"/> 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>총할부수수료</dt>
              <dd class="u-ta-right"><fmt:formatNumber value="${saleInfo.totalInstCmsn}" pattern="#,###"/> 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>실구매가</dt>
              <dd class="u-ta-right"><fmt:formatNumber value="${saleInfo.hndstPayAmt}" pattern="#,###"/> 원</dd>
            </dl>
            <hr class="c-hr c-hr--type2">
            <span class="c-text-label c-text-label--mint u-m--0 u-mb--20">${view.rateNm }</span>
            <dl class="c-addition c-addition--type1">
              <dt>월 통신 요금</dt>
              <dd class="u-ta-right">
                <fmt:parseNumber value="${saleInfo.payMnthChargeAmt}" integerOnly="true" var="monthTPay"/>
                <b><fmt:formatNumber value="${monthTPay}" pattern="#,###"/></b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>기본요금</dt>
              <dd class="u-ta-right"><fmt:formatNumber value="${saleInfo.baseVatAmt}" pattern="#,##0"/> 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>기본할인</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- <fmt:formatNumber value="${saleInfo.dcVatAmt}" pattern="#,##0"/></b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>프로모션할인</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- <fmt:formatNumber value="${saleInfo.promotionDcAmt}" pattern="#,##0"/></b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <c:set var="JoinUsimPriceInfoObj" value="${nmcp:getCodeNmDto('AppFormJoinUsimPriceInfo',view.socCode)}" />
            <c:set var="JoinUsimPriceInfoObj2" value="${nmcp:getCodeNmDto(Constants.GROUP_CODE_MARKET_JOIN_USIM_INFO,view.cntpntShopId)}" />
            <hr class="c-hr c-hr--type2">
            <dl class="c-addition c-addition--type1">
              <dt>가입 유심비 별도</dt>
              <dd class="u-ta-right"> </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>가입비(3개월 분납)</dt>
              <dd class="u-ta-right">
                <c:choose>
                      <c:when test="${nmcp:isContainsTrick(view.cntpntShopId) and JoinUsimPriceInfoObj.expnsnStrVal1 ne 'Y'}">
                           <span class="c-text c-text--strike"><fmt:formatNumber value="${view.joinPrice}" pattern="#,###"/>원</span>(무료)
                     </c:when>
                     <c:when test="${JoinUsimPriceInfoObj2.expnsnStrVal1 eq 'N'}">
                           <span class="c-text c-text--strike"><fmt:formatNumber value="${view.joinPrice}" pattern="#,###"/>원</span>(무료)
                       </c:when>
                    <c:otherwise>
                           <span class="c-text c-text--strike"><fmt:formatNumber value="${view.joinPrice}" pattern="#,###"/>원</span>
                    </c:otherwise>
                </c:choose>
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>USIM(최초 1회)</dt>
              <dd class="u-ta-right">
                    <c:choose>
                    <c:when test="${nmcp:isContainsTrick(view.cntpntShopId) and JoinUsimPriceInfoObj.expnsnStrVal2 ne 'Y'}">
                        <span class="c-text c-text--strike"><fmt:formatNumber value="${view.usimPrice}" pattern="#,###"/>원</span>(무료)
                    </c:when>
                    <c:when test="${JoinUsimPriceInfoObj2.expnsnStrVal2 eq 'N'}">
                           <span class="c-text c-text--strike"><fmt:formatNumber value="${view.usimPrice}" pattern="#,###"/>원</span>(무료)
                       </c:when>
                       <c:when test="${view.usimPrice == 0}">
                           <span class="c-text c-text--strike"><fmt:formatNumber value="${view.usimPrice}" pattern="#,###"/>원</span>(무료)
                       </c:when>
                    <c:otherwise>
                        <fmt:formatNumber value="${view.usimPrice}" pattern="#,###"/>원
                    </c:otherwise>
                     </c:choose>
                </dd>
            </dl>
            <c:if test="${view.operType eq 'MNP3'}">
            <dl class="c-addition c-addition--type2">
              <dt>번호이동 수수료</dt>
              <dd class="u-ta-right"><fmt:formatNumber value="${nmcp:getFtranPrice()}" pattern="#,###"/>원</dd>
            </dl>
            </c:if>
            <hr class="c-hr c-hr--type2">
            <dl class="c-addition c-addition--type1 c-addition--sum">
              <dt> 월 납부금액 (A+B)
                <br>(부가세 포함)
              </dt>
              <dd class="u-ta-right">
                <b><fmt:formatNumber value="${saleInfo.payMnthChargeAmt+saleInfo.payMnthAmt}" pattern="#,##0"/></b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>
                <p>${view.prodNm }</p>
                <p>${view.rateNm }</p>
              </dt>
            </dl>
          </div>
          </c:if>
          <!-- case2 : 주문 상품이 유심인 경우-->
          <c:if test="${view.reqBuyType eq 'UU' }">
          <h3 class="c-heading c-heading--type5">월 요금 상세</h3>
          <p class="c-bullet c-bullet--dot u-co-gray">프로모션 대상 요금제의 경우 익월 청구서를 통해 할인내역을 확인하실 수 있습니다.</p>
          <hr class="c-hr c-hr--type2 u-mt--24">
          <div class="c-addition-wrap">
            <span class="c-text-label c-text-label--mint u-m--0 u-mb--20">${view.rateNm }</span>
            <dl class="c-addition c-addition--type1">
              <dt>월 통신 요금</dt>
              <dd class="u-ta-right">
                <fmt:parseNumber value="${saleInfo.payMnthChargeAmt}" integerOnly="true" var="monthTPay"/>
                <b><fmt:formatNumber value="${monthTPay}" pattern="#,###"/></b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>기본요금</dt>
              <dd class="u-ta-right"><fmt:formatNumber value="${saleInfo.baseVatAmt}" pattern="#,##0"/> 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>기본할인</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- <fmt:formatNumber value="${saleInfo.dcVatAmt}" pattern="#,##0"/></b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>프로모션할인</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- <fmt:formatNumber value="${saleInfo.promotionDcAmt}" pattern="#,##0"/></b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <c:if test="${!empty apdView}">
            <hr class="c-hr c-hr--type2">
            <span class="c-text-label c-text-label--mint u-m--0 u-mb--20"><c:out value="${apdView.prodNm}" default="-" /></span>
            <dl class="c-addition c-addition--type1">
              <dt>단말기 금액</dt>
              <dd class="u-ta-right">
                <b><fmt:formatNumber value="${apdView.hndsetSalePrice}" pattern="#,##0"/></b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>포인트</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- <fmt:formatNumber value="${apdView.usePoint}" pattern="#,##0"/></b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>즉시할인금액</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- <fmt:formatNumber value="${apdView.cardDcAmt}" pattern="#,##0"/></b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>결제금액</dt>
              <dd class="u-ta-right"><fmt:formatNumber value="${apdView.hndsetSalePrice - apdView.usePoint - apdView.cardDcAmt}" pattern="#,##0"/> 원</dd>
            </dl>
            </c:if>


            <c:set var="JoinUsimPriceInfoObj" value="${nmcp:getCodeNmDto('AppFormJoinUsimPriceInfo',view.socCode)}" />
            <c:set var="JoinUsimPriceInfoObj2" value="${nmcp:getCodeNmDto(Constants.GROUP_CODE_MARKET_JOIN_USIM_INFO,view.cntpntShopId)}" />
            <hr class="c-hr c-hr--type2">
            <dl class="c-addition c-addition--type1">
              <dt>가입 유심비 별도</dt>
              <dd class="u-ta-right"> </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>가입비(3개월 분납)</dt>
              <dd class="u-ta-right">
                <c:choose>
                      <c:when test="${nmcp:isContainsTrick(view.cntpntShopId) and JoinUsimPriceInfoObj.expnsnStrVal1 ne 'Y'}">
                           <span class="c-text c-text--strike"><fmt:formatNumber value="${view.joinPrice}" pattern="#,###"/>원</span>(무료)
                     </c:when>
                     <c:when test="${JoinUsimPriceInfoObj2.expnsnStrVal1 eq 'N'}">
                           <span class="c-text c-text--strike"><fmt:formatNumber value="${view.joinPrice}" pattern="#,###"/>원</span>(무료)
                       </c:when>
                    <c:otherwise>
                           <span class="c-text c-text--strike"><fmt:formatNumber value="${view.joinPrice}" pattern="#,###"/>원</span>
                    </c:otherwise>
                </c:choose>
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>USIM(최초 1회)</dt>
              <dd class="u-ta-right"><c:choose>
                    <c:when test="${nmcp:isContainsTrick(view.cntpntShopId) and JoinUsimPriceInfoObj.expnsnStrVal2 ne 'Y'}">
                        <span class="c-text c-text--strike"><fmt:formatNumber value="${view.usimPrice}" pattern="#,###"/>원</span>(무료)
                    </c:when>
                    <c:when test="${JoinUsimPriceInfoObj2.expnsnStrVal2 eq 'N'}">
                           <span class="c-text c-text--strike"><fmt:formatNumber value="${view.usimPrice}" pattern="#,###"/>원</span>(무료)
                       </c:when>
                       <c:when test="${view.usimPrice == 0}">
                           <span class="c-text c-text--strike"><fmt:formatNumber value="${view.usimPrice}" pattern="#,###"/>원</span>(무료)
                       </c:when>
                    <c:otherwise>
                        <fmt:formatNumber value="${view.usimPrice}" pattern="#,###"/>원
                    </c:otherwise>
                     </c:choose></dd>
            </dl>
            <c:if test="${view.operType eq 'MNP3'}">
            <dl class="c-addition c-addition--type2">
              <dt>번호이동 수수료</dt>
              <dd class="u-ta-right"><fmt:formatNumber value="${nmcp:getFtranPrice()}" pattern="#,###"/>원</dd>
            </dl>
            </c:if>
            <hr class="c-hr c-hr--type2">
            <dl class="c-addition c-addition--type1 c-addition--sum">
              <dt> 월 납부금액
                <br>(부가세 포함)
              </dt>
              <dd class="u-ta-right">
                <b><fmt:formatNumber value="${saleInfo.payMnthChargeAmt}" pattern="#,##0"/></b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>
                <p>${view.prodNm }</p>
                <p>${view.rateNm }</p>
              </dt>
            </dl>
          </div>
          </c:if>



          <hr class="c-hr c-hr--type2 u-mt--24">
          <p class="c-bullet c-bullet--dot u-co-gray">가입비, 유심비 면제 여부는 가입신청 시의 프로모션 내용을 기준합니다.</p>
        </div>
      </div>



