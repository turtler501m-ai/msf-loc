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
      <input type="hidden" id="dlvryStateCode" name="dlvryStateCode" value="${selfDlvryData.dlvryStateCode}" />
      <c:set var="usimProdObj" value="${nmcp:getCodeNmDto(Constants.USIM_PROD_ID_GROP_CODE,selfDlvryData.usimProdId)}" />
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="delivery-inquiry2-title">유심구매 배송조회</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body overflow-x-hidden">
          <div class="c-flex c-flex--jfy-between u-mt--32">
            <h3 class="c-heading c-heading--type5 u-mt--0 u-mb--0">주문번호 ${selfDlvryData.selfDlvryIdx}</h3>
            <span class="u-co-gray-7"><fmt:formatDate value="${selfDlvryData.sysRdate}" pattern="yyyy.MM.dd" type="both"/></span>
          </div>
          <ol class="deli-step c-expand u-mt--16">
          	<c:if test="${selfDlvryData.dlvryKind eq '01'}">
          	<!-- 상태값 있을 경우, 클래스 .is-active 추가-->
            <li <c:if test="${selfDlvryData.dlvryStateCode eq '01'  && selfDlvryData.selfStateCode eq '01'}"> class="is-active" </c:if>>
              <i class="c-icon c-icon--accept-ready" aria-hidden="true"></i>
              <span>접수대기</span>
            </li>
            <li>
              <i class="c-icon c-icon--delivery-ready" aria-hidden="true"></i>
              <span>배송대기</span>
            </li>
            <li <c:if test="${selfDlvryData.dlvryStateCode eq '02'  && selfDlvryData.selfStateCode eq '01'}">class="is-active"</c:if>>
              <i class="c-icon c-icon--delivery" aria-hidden="true"></i>
              <span>배송중</span>
            </li>
            <li <c:if test="${selfDlvryData.dlvryStateCode eq '03' || selfDlvryData.dlvryStateCode eq '04'  && selfDlvryData.selfStateCode eq '01'}">class="is-active"</c:if>>
              <i class="c-icon c-icon--delivery-complete" aria-hidden="true"></i>
              <span>배송완료</span>
            </li>
          	</c:if>
            <c:if test="${selfDlvryData.dlvryKind eq '02'}">
            <!-- 상태값 있을 경우, 클래스 .is-active 추가-->
            <li <c:if test="${selfDlvryData.dlvryStateCode eq '01' || selfDlvryData.dlvryStateCode eq '06'}"> class="is-active" </c:if>>
              <i class="c-icon c-icon--accept-ready" aria-hidden="true"></i>
              <span>접수대기</span>
            </li>
            <li <c:if test="${selfDlvryData.dlvryStateCode eq '03'}">class="is-active"</c:if>>
              <i class="c-icon c-icon--delivery-ready" aria-hidden="true"></i>
              <span>배송대기</span>
            </li>
            <li <c:if test="${selfDlvryData.dlvryStateCode eq '02'}">class="is-active"</c:if>>
              <i class="c-icon c-icon--delivery" aria-hidden="true"></i>
              <span>배송중</span>
            </li>
            <li <c:if test="${selfDlvryData.dlvryStateCode eq '04'}">class="is-active"</c:if>>
              <i class="c-icon c-icon--delivery-complete" aria-hidden="true"></i>
              <span>배송완료</span>
            </li>    
            </c:if>        
          </ol>
          <h3 class="c-heading c-heading--type2 u-mb--12">주문 상품정보</h3>
          <div class="c-item c-item--type3 u-mt--0">
            <div class="c-item__list">
              <div class="c-item__image">
                <!--[2022-01-28] 이미지 변경-->
                <img src="../../resources/images/mobile/phone/img_product_02.png" alt="">
              </div>
              <div class="c-item__item">
                <span class="c-text c-text--type4 u-co-point-4">
				<c:if test="${selfDlvryData.dlvryKind eq '01'}">
				<c:choose>
	              	<c:when test="${selfDlvryData.selfStateCode eq '01'}">	
	              	  <c:if test="${selfDlvryData.dlvryStateCode eq '01'}">
	                      접수대기
	                  </c:if>
	                  <c:if test="${selfDlvryData.dlvryStateCode eq '02'}">
	                      배송중
	                  </c:if>
	                  <c:if test="${selfDlvryData.dlvryStateCode eq '03' || selfDlvryData.dlvryStateCode eq '04'}">
	                      배송완료
	                  </c:if> 
	                </c:when>
	                <c:otherwise>
	                  		신청취소
	                </c:otherwise>
                </c:choose>	                  
              </c:if>
              <c:if test="${selfDlvryData.dlvryKind eq '02'}">
                 
                  <c:if test="${selfDlvryData.dlvryStateCode eq '01' || selfDlvryData.dlvryStateCode eq '06'}">
                      접수대기
                  </c:if>
                  <c:if test="${selfDlvryData.dlvryStateCode eq '03'}">
                      배송대기
                  </c:if>
                  <c:if test="${selfDlvryData.dlvryStateCode eq '02'}">
                      배송중
                  </c:if>
                  <c:if test="${selfDlvryData.dlvryStateCode eq '04'}">
                      배송완료
                  </c:if>
                  <c:if test="${selfDlvryData.dlvryStateCode eq '05' || selfDlvryData.dlvryStateCode eq '07'}">
                      신청취소
                  </c:if>
	                  
               </c:if>       
				</span>
                <div class="c-item__title">
                  <strong>${usimProdObj.dtlCdNm}</strong>
                </div>
                <div class="c-item__info">
                  <span class="c-text c-text--type3 u-co-gray"><fmt:formatNumber value="${selfDlvryData.usimPrice}" pattern="#,###"/>원</span>
                </div>
              </div>
              
                 <c:set var="dlvryObj" value="${nmcp:getCodeNmDto(Constants.DLVRY_TB_CODE,selfDlvryData.tbCd)}" />
                 <c:set var="dlvryNoTmp" value="${fn:replace(selfDlvryData.dlvryNo, '-', '')}"/>
              
              <c:if test="${selfDlvryData.dlvryKind eq '01'}">
              <c:choose> 
              <c:when test="${selfDlvryData.dlvryKind eq '01' && '02' eq selfDlvryData.dlvryStateCode || '03' eq selfDlvryData.dlvryStateCode || '04' eq selfDlvryData.dlvryStateCode}">   
                 <c:choose>  
              		<c:when test="${platFormCd eq 'A'}">
              			<button type="button" class="c-button c-button--sm c-button--white" onclick="appOutSideOpen('${dlvryObj.dtlCdDesc}${dlvryNoTmp}');" <c:if test="${empty percelUrl || empty view.dlvryNo}">disabled="disabled"</c:if>>배송조회</button>
            		</c:when>
            		<c:otherwise>
                 		<button type="button" class="c-button c-button--sm c-button--white" onclick="openPage('spaLink', '${dlvryObj.dtlCdDesc}${dlvryNoTmp}', '');" <c:if test="${empty percelUrl || empty view.dlvryNo}">disabled="disabled"</c:if>>배송조회</button>
            		</c:otherwise>
           		</c:choose>
                 
              </c:when>
              <c:otherwise>
                 <button type="button" class="c-button c-button--sm c-button--white" onclick="openPage('spaLink', '${dlvryObj.dtlCdDesc}${dlvryNoTmp}', '');" disabled="disabled">배송조회</button>
              </c:otherwise>
              </c:choose>
              </c:if> 
              <c:if test="${selfDlvryData.dlvryKind eq '02'}">
              <c:choose> 
              <c:when test="${'02' eq selfDlvryData.dlvryStateCode || '04' eq selfDlvryData.dlvryStateCode}">   
                 <c:choose>  
              		<c:when test="${platFormCd eq 'A'}">
              			<button type="button" class="c-button c-button--sm c-button--white" onclick="appOutSideOpen('${dlvryObj.dtlCdDesc}${dlvryNoTmp}');" <c:if test="${empty percelUrl || empty view.dlvryNo}">disabled="disabled"</c:if>>배송조회</button>
            		</c:when>
            		<c:otherwise>
                 		<button type="button" class="c-button c-button--sm c-button--white" onclick="openPage('spaLink', '${dlvryObj.dtlCdDesc}${dlvryNoTmp}', '');" <c:if test="${empty percelUrl || empty view.dlvryNo}">disabled="disabled"</c:if>>배송조회</button>
            		</c:otherwise>
           		</c:choose>
              </c:when>
              <c:otherwise>
                 <button type="button" class="c-button c-button--sm c-button--white" onclick="openPage('spaLink', '${dlvryObj.dtlCdDesc}${dlvryNoTmp}', '');" disabled="disabled">배송조회</button>
              </c:otherwise>
              </c:choose>
              </c:if> 
            </div>
          </div><!-- case1 : 주문 상품이 유심인 경우 table-->
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
                  <th>주문자명</th>
                  <td class="u-ta-left">${nmcp:getMaskedName(selfDlvryData.cstmrName)}</td>
                </tr>
                <tr>
                  <th>배송지 정보</th>
                  <td class="u-ta-left"> 
                  		<c:set var="dlvryAddr">${selfDlvryData.dlvryAddr} ${selfDlvryData.dlvryAddrDtl}</c:set>
                        ${selfDlvryData.dlvryPost}<br/>
                        ${nmcp:getMaskedByAddressNew2(dlvryAddr)}
                  </td>
                </tr>
                <tr>
                  <th>수령인명</th>
                  <td class="u-ta-left">${nmcp:getMaskedName(selfDlvryData.dlvryName)}</td>
                </tr>
                <tr>
                  <th>연락처</th>
                  <td class="u-ta-left"><c:set value="${selfDlvryData.dlvryTelFn}-${selfDlvryData.dlvryTelMn}-${selfDlvryData.dlvryTelRn}" var="cstmrMobile" />               
                                 연락처  : ${nmcp:getMaskedTelNo(cstmrMobile)}</td>
                </tr>
              </tbody>
            </table>
          </div>          
        </div>
      </div>
 

     
          