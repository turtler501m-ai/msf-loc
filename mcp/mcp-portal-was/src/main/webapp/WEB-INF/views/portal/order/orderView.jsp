<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

      <div class="c-modal__content">
        <div class="c-modal__header">
          <h2 class="c-modal__title" id="modalDeliveryTitle">가입신청 배송조회</h2>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <div class="c-heading-wrap">
            <h3 class="c-heading c-heading--fs20">주문번호 ${view.resNo }</h3>
            <div class="c-heading-wrap__sub">
              <span class="c-hidden">주문일자</span><fmt:parseDate value="${view.sysRdate}" pattern="yyyyMMdd" var="sysRdate"/><fmt:formatDate value="${sysRdate}" pattern="yyyy.MM.dd" type="both"/>
            </div>
          </div>
          <h4 class="c-hidden">배송상태</h4>
          <ol class="c-stepper c-stepper--type3 u-mt--24 c-expand--pop">
            <!-- 현재 상태인 경우-->
            <li class="c-stepper__item <c:if test="${view.requestStateCode eq '00'}">is-active</c:if>">
              <i class="c-icon c-icon--delivery-type1" aria-hidden="true"></i>
              <span class="c-hidden">현재상태</span>
              <span class="c-stepper__title">접수대기</span>
            </li><!-- //-->
            <li class="c-stepper__item <c:if test="${view.requestStateCode eq '01' || view.requestStateCode eq '02' || view.requestStateCode eq '09' || view.requestStateCode eq '07' || view.requestStateCode eq '08'}">is-active</c:if>">
              <i class="c-icon c-icon--delivery-type2" aria-hidden="true"></i>
              <span class="c-stepper__title">배송대기</span>
            </li>
            <li class="c-stepper__item <c:if test="${view.requestStateCode eq '03' || view.requestStateCode eq '04' || view.requestStateCode eq '10'}">is-active</c:if>">
              <i class="c-icon c-icon--delivery-type3" aria-hidden="true"></i>
              <span class="c-stepper__title">배송중</span>
            </li>
            <li class="c-stepper__item <c:if test="${view.requestStateCode eq '20' || view.requestStateCode eq '11' || view.requestStateCode eq '13'}">is-active</c:if>">
              <i class="c-icon c-icon--delivery-type4" aria-hidden="true"></i>
              <span class="c-stepper__title">개통대기</span>
            </li>
            <li class="c-stepper__item <c:if test="${view.requestStateCode eq '21'}">is-active</c:if>">
              <i class="c-icon c-icon--delivery-type5" aria-hidden="true"></i>
              <span class="c-stepper__title">개통완료</span>
            </li>
          </ol>
          <h4 class="c-heading c-heading--fs16 u-mt--48 u-mb--16">주문 상품정보</h4>

          <c:if test="${!empty apdView}">

              <div class="detail">
            <!-- [2022-02-04] 이미지 마크업 수정 -->
            <div class="detail__image">

            <c:if test="${apdView.reqBuyType eq 'UU'}">
                  <img src="/resources/images/portal/content/temp_usim.png" alt="유심 실물 사진" aria-hidden="true">
            </c:if>
            <c:if test="${apdView.reqBuyType eq 'MM'}">
                <img src="${apdView.imgPath}" alt="<c:out value="${apdView.prodNm}" default="-" /> 실물 사진" onerror="this.src='/resources/images/portal/content/img_phone_noimage.png'" aria-hidden="true">
            </c:if>

            </div><!-- [$2022-02-04] 이미지 마크업 수정 -->

            <div class="detail__title">
              <span class="detail__sub-title c-hidden">상품명/금액</span>
              <b>
               <c:if test="${apdView.reqBuyType eq 'UU'}">
                       유심
               </c:if>
               <c:if test="${apdView.reqBuyType eq 'MM'}">
                       <c:out value="${apdView.prodNm}" default="-" />
               </c:if>
               </b>
              <c:if test="${apdView.reqBuyType eq 'MM'}">
              <span class="detail__sub"><c:out value="${apdView.atribValNmOne}" default="-" />/<c:out value="${apdView.atribValNmTwo}" default="-" /></span>
              </c:if>
              <c:if test="${apdView.reqBuyType eq 'UU'}">
              <span class="detail__sub"><c:out value="${apdView.rateNm}" default="-" /></span>
              </c:if>
              <b>
              <c:if test="${apdView.reqBuyType eq 'MM'}">
                  <fmt:formatNumber value="${apdView.hndsetSalePrice - apdView.usePoint - apdView.cardDcAmt}" pattern="#,###"/>원<!-- 단말상품가격 -->
              </c:if>
              <c:if test="${apdView.reqBuyType eq 'UU' and empty apdView.prodId}">
                   <fmt:formatNumber value="${saleInfo.baseVatAmt}" pattern="#,###"/>원<!-- 중고단말상품가격 -->
              </c:if>
              </b>
            </div>
            <div class="detail__item">
              <span class="detail__sub-title c-hidden">배송상태</span>
              <b class="u-co-point-4">
              <c:if test="${apdView.requestStateCode eq '00'}">접수대기</c:if>
              <c:if test="${apdView.requestStateCode eq '01' || apdView.requestStateCode eq '02' || apdView.requestStateCode eq '09' || apdView.requestStateCode eq '07' || apdView.requestStateCode eq '08'}">배송대기</c:if>
              <c:if test="${apdView.requestStateCode eq '03' || apdView.requestStateCode eq '04' || apdView.requestStateCode eq '10'}">배송중</c:if>
              <c:if test="${apdView.requestStateCode eq '20' || apdView.requestStateCode eq '11' || apdView.requestStateCode eq '13'}">개통대기</c:if>
              <c:if test="${apdView.requestStateCode eq '21'}">개통완료</c:if>
              <c:if test="${apdView.requestStateCode eq '30' || apdView.requestStateCode eq '31' }">신청취소</c:if>
              </b>
            </div>
            <div class="c-button-wrap">
            <c:set var="codeList" value="${nmcp:getCodeList('PERCEL')}" />
              <c:forEach items="${codeList }" var="codeList" >
                  <c:if test="${apdView.tbCd eq codeList.dtlCd}">
                      <c:set value="${codeList.dtlCdNm }" var="percelNm"/>
                      <c:set value="${codeList.dtlCdDesc }" var="percelUrl"/>
                  </c:if>
              </c:forEach>
            <c:choose>
            <c:when test="${apdView.requestStateCode eq '03' || apdView.requestStateCode eq '04' || apdView.requestStateCode eq '10' || apdView.requestStateCode eq '20' || apdView.requestStateCode eq '11' || apdView.requestStateCode eq '13' || apdView.requestStateCode eq '21'}">
              <button type="button" class="c-button c-button-round--sm c-button--white"  onclick="goDlvPage('${percelUrl}${view.dlvryNo}');" <c:if test="${empty percelUrl || empty view.dlvryNo}">disabled="disabled"</c:if>>배송조회</button>
            </c:when>
            <c:otherwise>
              <button type="button" class="c-button c-button-round--sm c-button--white"  onclick="goDlvPage('${percelUrl}${view.dlvryNo}');" disabled="disabled">배송조회</button>
            </c:otherwise>
            </c:choose>
            </div>
          </div>

          </c:if>

          <div class="detail">
            <!-- [2022-02-04] 이미지 마크업 수정 -->
            <div class="detail__image">
            <c:if test="${view.reqBuyType eq 'UU'}">
                  <img src="/resources/images/portal/content/temp_usim.png" alt="유심 실물 사진" aria-hidden="true">
            </c:if>
            <c:if test="${view.reqBuyType eq 'MM'}">
                <img src="${view.imgPath}" alt="<c:out value="${view.prodNm} 실물 사진" default="-" />" onerror="this.src='/resources/images/portal/content/img_phone_noimage.png'" aria-hidden="true">
            </c:if>
            </div><!-- [$2022-02-04] 이미지 마크업 수정 -->

            <div class="detail__title">
              <span class="detail__sub-title c-hidden">상품명/금액</span>
              <b>
               <c:if test="${view.reqBuyType eq 'UU'}">
                       유심
               </c:if>
               <c:if test="${view.reqBuyType eq 'MM'}">
                       <c:out value="${view.prodNm}" default="-" />
               </c:if>
               </b>
              <c:if test="${view.reqBuyType eq 'MM'}">
              <span class="detail__sub"><c:out value="${view.atribValNmOne}" default="-" />/<c:out value="${view.atribValNmTwo}" default="-" /></span>
              </c:if>
              <c:if test="${view.reqBuyType eq 'UU'}">
              <span class="detail__sub"><c:out value="${view.rateNm}" default="-" /></span>
              </c:if>
              <b>
              <c:if test="${view.reqBuyType eq 'MM'}">
                  <fmt:formatNumber value="${view.modelPrice+(view.modelPrice*0.1)}" pattern="#,###"/>원<!-- 단말상품가격 -->
              </c:if>
              <c:if test="${view.reqBuyType eq 'UU'}">
                   <fmt:formatNumber value="${saleInfo.baseVatAmt}" pattern="#,###"/>원<!-- 중고단말상품가격 -->
              </c:if>
              </b>
            </div>
            <div class="detail__item">
              <span class="detail__sub-title c-hidden">배송상태</span>
              <b class="u-co-point-4">
              <c:if test="${view.requestStateCode eq '00'}">접수대기</c:if>
              <c:if test="${view.requestStateCode eq '01' || view.requestStateCode eq '02' || view.requestStateCode eq '09' || view.requestStateCode eq '07' || view.requestStateCode eq '08'}">배송대기</c:if>
              <c:if test="${view.requestStateCode eq '03' || view.requestStateCode eq '04' || view.requestStateCode eq '10'}">배송중</c:if>
              <c:if test="${view.requestStateCode eq '20' || view.requestStateCode eq '11' || view.requestStateCode eq '13'}">개통대기</c:if>
              <c:if test="${view.requestStateCode eq '21'}">개통완료</c:if>
              <c:if test="${view.requestStateCode eq '30' || view.requestStateCode eq '31' }">신청취소</c:if>
              </b>
            </div>
            <div class="c-button-wrap">
            <c:set var="codeList" value="${nmcp:getCodeList('PERCEL')}" />
              <c:forEach items="${codeList }" var="codeList" >
                  <c:if test="${view.tbCd eq codeList.dtlCd}">
                      <c:set value="${codeList.dtlCdNm }" var="percelNm"/>
                      <c:set value="${codeList.dtlCdDesc }" var="percelUrl"/>
                  </c:if>
              </c:forEach>
            <c:choose>
            <c:when test="${view.requestStateCode eq '03' || view.requestStateCode eq '04'  || view.requestStateCode eq '10' || view.requestStateCode eq '20' || view.requestStateCode eq '11' || view.requestStateCode eq '13' || view.requestStateCode eq '21'}">
              <button type="button" class="c-button c-button-round--sm c-button--white"  onclick="goDlvPage('${percelUrl}${view.dlvryNo}');" <c:if test="${empty percelUrl || empty view.dlvryNo}">disabled="disabled"</c:if> >배송조회</button>
            </c:when>
            <c:otherwise>
              <button type="button" class="c-button c-button-round--sm c-button--white"  onclick="goDlvPage('${percelUrl}${view.dlvryNo}');" disabled="disabled">배송조회</button>
            </c:otherwise>
            </c:choose>
            </div>
          </div>

          <h4 class="c-heading c-heading--fs16 u-mt--24">주문 상세내역</h4>
          <div class="c-table u-mt--16">
            <table>
              <caption>신청요금제, 요금납부방법, 배송지 정보, 주문자명, 연락처, 이메일 주소에 대한 주문 상세내역 표입니다.</caption>
              <colgroup>
                <col style="width: 33%">
                <col>
              </colgroup>
              <tbody class="c-table__left">
                <tr>
                  <th scope="row">신청요금제</th>
                  <td>${view.rateNm }</td>
                </tr>
                <tr>
                  <th scope="row">요금납부방법</th>
                  <td><c:choose>
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
                       </c:choose></td>
                </tr>
                <tr>
                  <th scope="row">배송지 정보</th>
                  <td><c:set var="dlvryAddr">${view.dlvryAddr} ${view.dlvryAddrDtl}</c:set>
                  ${view.dlvryPost}<br/>
                  ${nmcp:getMaskedByAddressNew2(dlvryAddr)}
                  </td>
                </tr>
                <tr>
                  <th scope="row">주문자명</th>
                  <td>${nmcp:getMaskedName(view.cstmrName)}</td>
                </tr>
                <tr>
                  <th scope="row">연락처</th>
                  <c:set value="${view.cstmrMobileFn}-${view.cstmrMobileMn}-${view.cstmrMobileRn}" var="cstmrMobile" />
                  <td>${nmcp:getMaskedTelNo(cstmrMobile)}</td>
                </tr>
                <tr>
                  <th scope="row">이메일 주소</th>
                  <td>${nmcp:getMaskedEmail2(view.cstmrMail)}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <c:choose>
          <c:when test="${view.reqBuyType eq 'UU'}">
          <h4 class="c-heading c-heading--fs20 u-mt--64">월 요금 상세</h4>
                   </c:when>
                   <c:otherwise>
          <h4 class="c-heading c-heading--fs20 u-mt--64">단말기/월 요금 상세</h4>
                   </c:otherwise>
               </c:choose>
          <p class="c-text c-text--fs17 u-co-gray u-mt--12">프로모션 대상 요금제의 경우 익월 청구서를 통해 할인내역을 확인하실 수 있습니다.</p>
          <div class="price-detail">

          <div class="c-addition-wrap">
          <c:if test="${view.reqBuyType eq 'MM'}">
              <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
              <dl class="c-addition c-addition--type1">
                <dt class="c-hidden">단말기명</dt>
                <dd>
                  <span class="c-text-label c-text-label--type2 c-text-label--mint">${view.prodNm }</span>
                </dd>
              </dl>
              <dl class="c-addition c-addition--type1">
                <dt>월 단말요금(A)</dt>
                <dd class="u-ta-right">
                  <b><fmt:formatNumber value="${saleInfo.payMnthAmt}" pattern="#,###"/></b>원
                </dd>
              </dl>
              <dl class="c-addition c-addition--type2">
                <dt>단말기 출고가</dt>
                <dd class="u-ta-right"><fmt:formatNumber value="${view.modelPrice+view.modelPriceVat}" pattern="#,###"/> 원</dd>
              </dl>
              <dl class="c-addition c-addition--type2">
                <dt>공통지원금</dt>
                <dd class="u-ta-right">
                  <b>
                    <em>- <fmt:formatNumber value="${view.modelDiscount2}" pattern="#,###"/> 원</em>
                  </b>
                </dd>
              </dl>
              <dl class="c-addition c-addition--type2">
                <dt>추가지원금</dt>
                <dd class="u-ta-right">
                  <b>
                    <em>- <fmt:formatNumber value="${view.modelDiscount3}" pattern="#,###"/> 원</em>
                  </b>
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
              </c:if>

              <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
              <dl class="c-addition c-addition--type1">
                <dt class="c-hidden">요금제명</dt>
                <dd>
                  <span class="c-text-label c-text-label--type2 c-text-label--mint">${view.rateNm }</span>
                </dd>
              </dl>
              <dl class="c-addition c-addition--type1">
                <dt>월 통신요금</dt>
                <dd class="u-ta-right">
                  <b><fmt:parseNumber value="${saleInfo.payMnthChargeAmt}" integerOnly="true" var="monthTPay"/>
                       <fmt:formatNumber value="${monthTPay}" pattern="#,###"/></b>원
                </dd>
              </dl>
              <dl class="c-addition c-addition--type2">
                <dt>기본요금</dt>
                <dd class="u-ta-right"><fmt:formatNumber value="${saleInfo.baseVatAmt}" pattern="#,##0"/> 원</dd>
              </dl>
              <dl class="c-addition c-addition--type2">
                <dt>기본할인</dt>
                <dd class="u-ta-right">
                  <b>
                    <em>-<fmt:formatNumber value="${saleInfo.dcVatAmt}" pattern="#,##0"/> 원</em>
                  </b>
                </dd>
              </dl>
              <dl class="c-addition c-addition--type2">
                <dt>프로모션할인</dt>
                <dd class="u-ta-right">
                  <b>
                    <em>-<fmt:formatNumber value="${saleInfo.promotionDcAmt}" pattern="#,##0"/> 원</em>
                  </b>
                </dd>
              </dl>

              <c:if test="${!empty apdView}">
                  <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
                  <dl class="c-addition c-addition--type1">
                    <dt class="c-hidden">자급제상품명</dt>
                    <dd>
                      <span class="c-text-label c-text-label--type2 c-text-label--mint"><c:out value="${apdView.prodNm}" default="-" /></span>
                    </dd>
                  </dl>
                  <dl class="c-addition c-addition--type1">
                    <dt>단말기 금액</dt>
                    <dd class="u-ta-right">
                      <b><fmt:formatNumber value="${apdView.hndsetSalePrice}" pattern="#,##0"/></b>원
                    </dd>
                  </dl>
                  <dl class="c-addition c-addition--type2">
                    <dt>포인트</dt>
                    <dd class="u-ta-right">
                      <b>
                        <em>-<fmt:formatNumber value="${apdView.usePoint}" pattern="#,##0"/> 원</em>
                      </b>
                    </dd>
                  </dl>
                  <dl class="c-addition c-addition--type2">
                    <dt>즉시할인금액</dt>
                    <dd class="u-ta-right">
                      <b>
                        <em>-<fmt:formatNumber value="${apdView.cardDcAmt}" pattern="#,##0"/> 원</em>
                      </b>
                    </dd>
                  </dl>
                  <dl class="c-addition c-addition--type2">
                    <dt>결제금액</dt>
                    <dd class="u-ta-right"><fmt:formatNumber value="${apdView.hndsetSalePrice - apdView.usePoint - apdView.cardDcAmt}" pattern="#,##0"/> 원</dd>
                  </dl>
              </c:if>

              <c:set var="JoinUsimPriceInfoObj" value="${nmcp:getCodeNmDto('AppFormJoinUsimPriceInfo',view.socCode)}" />
              <c:set var="JoinUsimPriceInfoObj2" value="${nmcp:getCodeNmDto(Constants.GROUP_CODE_MARKET_JOIN_USIM_INFO,view.cntpntShopId)}" />
              <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
              <dl class="c-addition c-addition--type1">
                <dt>가입 유심비 별도</dt>
              </dl>
              <dl class="c-addition c-addition--type2">
                <dt>가입비(3개월 분납)</dt>
                <dd class="u-ta-right">
                <c:choose>
                      <c:when test="${nmcp:isContainsTrick(view.cntpntShopId) and JoinUsimPriceInfoObj.expnsnStrVal1 ne 'Y'}">
                           <span class="c-text u-td-line-through"><fmt:formatNumber value="${view.joinPrice}" pattern="#,###"/>원</span>(무료)
                     </c:when>
                     <c:when test="${JoinUsimPriceInfoObj2.expnsnStrVal1 eq 'N'}">
                           <span class="c-text u-td-line-through"><fmt:formatNumber value="${view.joinPrice}" pattern="#,###"/>원</span>(무료)
                       </c:when>
                    <c:otherwise>
                           <span class="c-text u-td-line-through"><fmt:formatNumber value="${view.joinPrice}" pattern="#,###"/>원</span>
                    </c:otherwise>
                </c:choose>
                </dd>
              </dl>
              <dl class="c-addition c-addition--type2">
                <dt>USIM(최초 1회)</dt>
                <dd class="u-ta-right">
                <c:choose>
                <c:when test="${nmcp:isContainsTrick(view.cntpntShopId) and JoinUsimPriceInfoObj.expnsnStrVal2 ne 'Y'}">
                    <span class="c-text u-td-line-through"><fmt:formatNumber value="${view.usimPrice}" pattern="#,###"/>원</span>(무료)
                </c:when>
                <c:when test="${JoinUsimPriceInfoObj2.expnsnStrVal2 eq 'N'}">
                       <span class="c-text u-td-line-through"><fmt:formatNumber value="${view.usimPrice}" pattern="#,###"/>원</span>(무료)
                   </c:when>
                   <c:when test="${view.usimPrice == 0}">
                       <span class="c-text u-td-line-through"><fmt:formatNumber value="${view.usimPrice}" pattern="#,###"/>원</span>(무료)
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
                <dd class="u-ta-right"><fmt:formatNumber value="${nmcp:getFtranPrice()}" pattern="#,###"/> 원</dd>
              </dl>
              </c:if>


            </div>
            <div class="c-box c-box--type1 u-mt--32">
              <div class="c-addition-wrap">
                <dl class="c-addition c-addition--type1 c-addition--sum">
                  <c:choose>
                  <c:when test="${view.reqBuyType eq 'UU'}">
                    <dt>
                    <b>월 납부금액</b>
                    <span class="c-text c-text--fs14 u-co-gray u-fw--regular">(부가세 포함)</span>
                  </dt>
                  <dd class="u-ml--auto u-ta-right">
                    <b><fmt:formatNumber value="${saleInfo.payMnthChargeAmt}" pattern="#,##0"/></b>원
                  </dd>
                  </c:when>
                  <c:otherwise>
                    <dt>
                    <b>월 납부금액 (A+B)</b>
                    <span class="c-text c-text--fs14 u-co-gray u-fw--regular">(부가세 포함)</span>
                  </dt>
                  <dd class="u-ml--auto u-ta-right">
                    <b><fmt:formatNumber value="${saleInfo.payMnthChargeAmt+saleInfo.payMnthAmt}" pattern="#,##0"/></b>원
                  </dd>
                  </c:otherwise>
               </c:choose>



                </dl>
                <dl class="c-addition c-addition--type2">
                  <dt class="c-hidden">주문상품정보</dt>
                  <dd class="u-co-gray">${view.prodNm }
                    <br>${view.rateNm }
                  </dd>
                </dl>
                <hr class="c-hr c-hr--basic u-mt--24 u-mb--32">
                <ul class="c-text-list c-bullet c-bullet--dot">
                  <li class="c-text-list__item u-co-gray">가입비, 유심비 면제 여부는 가입신청 시의 프로모션 내용을 기준합니다.</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>




