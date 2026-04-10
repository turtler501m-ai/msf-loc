<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<!-- <script type="text/javascript" src="/resources/js/portal/mypage/myinfo/joinCertPop.js"></script> -->

<div class="c-modal__content">
    <div class="c-modal__header">
        <h2 class="c-modal__title" id="modalJoinInfoTitle">가입 정보 인쇄</h2>
        <button class="c-button no-print" data-dialog-close="">
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
        </button>
    </div>
    <div class="c-modal__body">
        <div class="certi-box">
            <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--0">고객정보</h3>
            <div class="c-button-wrap c-button-wrap--right print-btn">
                <button class="c-button c-button--sm c-button--white c-button-round--sm" onclick="window.print(); return false;">
                    <i class="c-icon c-icon--print" aria-hidden="true"></i>
                    <span>인쇄</span>
                </button>
            </div>
            <div class="c-table u-mt--32 u-mb--16">
                <table>
                    <caption>고객명, 휴대폰번호, 휴대폰모델명을 포함한 고객정보</caption>
                    <colgroup>
                        <col style="width: 45%">
                        <col>
                    </colgroup>
                    <tbody>
                        <tr>
                            <th class="u-ta-left" scope="row">고객명</th>
                            <td class="u-ta-left">${searchVO.userName}</td>
                        </tr>
                        <tr>
                            <th class="u-ta-left" scope="row">휴대폰번호</th>
                            <td class="u-ta-left">${perMyktfInfo.homeTel}</td>
                        </tr>
                        <tr>
                            <th class="u-ta-left" scope="row">휴대폰모델명</th>
                            <td class="u-ta-left">${searchVO.modelName}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--32">모바일
                가입정보</h3>
            <div class="c-table u-mt--32 u-mb--16">
                <table>
                    <caption>구분, 상품명, 안내사항, 요금제, 유료부가서비스, 기본제공 부가서비스, 납부방법,
                        요금명세서를 포함한 모바일 가입정보</caption>
                    <colgroup>
                        <col style="width: 28%">
                        <col style="width: 30%">
                        <col>
                    </colgroup>
                    <thead>
                        <tr>
                            <th scope="col">구분</th>
                            <th scope="col">상품명</th>
                            <th scope="col">안내사항</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th class="u-ta-center" scope="row">요금제</th>
                            <td class="u-ta-center">${mcpUserCntrMngDto.rateNm}</td>
                            <td class="u-ta-left">기본료 : ${mcpUserCntrMngDto.baseAmt} / 약정할인: ${mcpUserCntrMngDto.enggMnthCnt eq 0 ? "없음":mcpUserCntrMngDto.enggMnthCnt} / ${mcpUserCntrMngDto.rmk}</td>
                        </tr>
                        <tr>
                            <th class="u-ta-center" scope="row">유료부가서비스</th>
                            <td class="u-ta-center">기타서비스</td>
                            <td class="u-ta-left">
                                <c:forEach items="${getAddSvcInfo.list}" var="item">
                                    <c:if test="${item.socRateValue ne 'Free'}">
                                        ${item.socDescription}(<fmt:formatNumber value="${item.socRateValue}" type="number"/>원),
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                        <tr>
                            <th class="u-ta-center" scope="row">기본제공 <br> 부가서비스</th>
                            <td class="u-ta-center">기타서비스</td>
                            <td class="u-ta-left">
                                <c:forEach items="${getAddSvcInfo.list}" var="item">
                                    <c:if test="${item.socRateValue eq 'Free'}">
                                    ${item.socDescription}(${item.socRateValue}),
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                        <tr>
                            <th class="u-ta-center" scope="row">납부방법</th>
                            <td class="u-ta-center">${changeInfo.payMethod}</td>
                            <td class="u-ta-left">
                                <c:choose>
                                    <c:when test="${ changeInfo.payMethod eq '자동이체' }">
                                            납부계좌 : ${ changeInfo.blBankAcctNo  } 납부일자 :
                                            <c:set value="${changeInfo.billCycleDueDay}" var="billCycleDueDay"/>
                                            <c:choose>
                                                <c:when test="${billCycleDueDay eq '99'}">말일 </c:when>
                                                <c:when test="${billCycleDueDay ne' 99'}">${billCycleDueDay}일 </c:when>
                                            </c:choose>
                                    </c:when>
                                    <c:when test="${ changeInfo.payMethod eq '지로' }">
                                            납부일자 :
                                            <c:set value="${changeInfo.billCycleDueDay}" var="billCycleDueDay"/>
                                            <c:choose>
                                                <c:when test="${billCycleDueDay eq '99'}">말일 </c:when>
                                                <c:when test="${billCycleDueDay ne' 99'}">${billCycleDueDay}일 </c:when>
                                            </c:choose>
                                    </c:when>
                                    <c:when test="${ changeInfo.payMethod eq '신용카드' }">
                                            카드번호 : </b>${ changeInfo.prevCardNo   } 납부일자 :
                                            <c:set value="${changeInfo.billCycleDueDay}" var="billCycleDueDay"/>
                                            <c:choose>
                                                <c:when test="${billCycleDueDay eq '99'}">말일 </c:when>
                                                <c:when test="${billCycleDueDay ne' 99'}">${billCycleDueDay}일 </c:when>
                                            </c:choose>
                                            카드만료기간 :
                                            <c:set value="${changeInfo.prevExpirDt}" var="prevExpirDt"/>
                                            ${fn:substring(prevExpirDt,0,4)}-${fn:substring(prevExpirDt,4,6)}-${fn:substring(prevExpirDt,6,8)}
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th class="u-ta-center" scope="row">요금명세서</th>
                            <td class="u-ta-center">
                                <c:choose>
                                    <c:when test="${moscBilEmailInfo.billTypeCd eq 'CB'}">
                                        <c:set var="reqType" value="이메일 명세서" />
                                    </c:when>
                                    <c:when test="${moscBilEmailInfo.billTypeCd eq 'LX'}">
                                        <c:set var="reqType" value="우편 명세서" />
                                    </c:when>
                                    <c:when test="${moscBilEmailInfo.billTypeCd eq 'MB'}">
                                        <c:set var="reqType" value="모바일 명세서(MMS)" />
                                    </c:when>
                               </c:choose>
                               <c:choose>
                                       <c:when test="${changeInfo.payMethod eq '지로' }">
                                        <c:set var="reqTypeNm" value="청구지" />
                                        <c:set var="blaAddr" value="${changeInfo.blAddr }" />
                                    </c:when>
                                    <c:when test="${moscBilEmailInfo.billTypeCd eq 'CB' }">
                                        <c:set var="reqTypeNm" value="메일주소" />
                                         <c:choose>
                                            <c:when test="${maskingSession eq 'Y'}">
                                                  <c:set var="blaAddr" value="${moscBilEmailInfo.email}" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="blaAddr" value="${moscBilEmailInfo.maskedEmail}" />
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:when test="${moscBilEmailInfo.billTypeCd eq 'MB'}">
                                        <c:set var="reqTypeNm" value="휴대폰 번호" />
                                        <c:set var="blaAddr" value="${moscBilEmailInfo.ctn}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="reqTypeNm" value="청구지" />
                                        <c:choose>
                                            <c:when test="${maskingSession eq 'Y'}">
                                                <c:set var="blaAddr" value="${perMyktfInfo.addr}" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="blaAddr" value="${perMyktfInfo.addr} ********" />
                                            </c:otherwise>
                                       </c:choose>
                                    </c:otherwise>
                                    </c:choose>
                                ${reqType}
                            </td>
                            <td class="u-ta-left">
                                청구수령인 : ${searchVO.userName}
                                <c:if test="${changeInfo.payMethod eq '지로' || moscBilEmailInfo.billTypeCd ne 'MB'}">
                                    <span style="padding-left:10px">${reqTypeNm} : ${blaAddr}</span>
                                </c:if>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="certi-box--bottom u-ta-center">
                <p class="c-text c-text--fs16">가입한 상품 관련 사항을 위와 같이 안내 드립니다.</p>
                <p class="c-text c-text--fs14 u-co-gray u-mt--8">${today}</p>
                <p class="c-text c-text--fs16 u-mt--12">
                    <b>주식회사 케이티엠모바일</b>
                </p>
                <i class="certi-box__image" aria-hidden="true"> <img
                    src="/resources/images/portal/content/img_stamp.png" alt="">
                </i>
            </div>
        </div>
    </div>
</div>