<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
	  <%--
		: 신청상태가 '접수, 운행, 픽업, 완료, 대기' 인 경우만 진행상태 노출
		: 신청상태가 '접수취소, 배송취소' 인 경우만 '신청취소' 노출
		: 접수(접수대기), 운행(배송중), 픽업(배송대기), 완료(배송완료), 대기(접수대기)
		: 코드값 명칭
		01      접수(접수대기)
		02      운행(배송중)
		03      픽업(배송대기)
		04      완료(배송완료)
		
		05      접수취소
		
		06      대기(접수대기)
		07      배송취소
	  --%>
      <input type="hidden" id="dlvryStateCode" name="dlvryStateCode" value="${selfDlvryData.dlvryStateCode}" />
      <c:set var="usimProdObj" value="${nmcp:getCodeNmDto(Constants.USIM_PROD_ID_GROP_CODE,selfDlvryData.usimProdId)}" /> 
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h2 class="c-modal__title" id="modalUsimDeliveryTitle">유심구매 배송조회</h2>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <div class="c-heading-wrap">
            <h3 class="c-heading c-heading--fs20">주문번호 ${selfDlvryData.selfDlvryIdx}</h3>
            <div class="c-heading-wrap__sub">
              <span class="c-hidden">주문일자</span><fmt:formatDate value="${selfDlvryData.sysRdate}" pattern="yyyy.MM.dd" type="both"/>
            </div>
          </div>
          <h4 class="c-hidden">배송상태</h4>
          <ol class="c-stepper c-stepper--type3 u-mt--24 c-expand--pop">
          	<c:if test="${selfDlvryData.dlvryKind eq '01'}">
            <!-- 현재 상태인 경우-->
            <li class="c-stepper__item <c:if test="${selfDlvryData.dlvryStateCode eq '01' && selfDlvryData.selfStateCode eq '01'}">is-active</c:if>">
              <i class="c-icon c-icon--delivery-type1" aria-hidden="true"></i>
              <c:if test="${selfDlvryData.dlvryStateCode eq '01' && selfDlvryData.selfStateCode eq '01'}"><span class="c-hidden">현재상태</span></c:if>
              <span class="c-stepper__title">접수대기</span>
            </li><!-- //-->      
            <li class="c-stepper__item">
              <i class="c-icon c-icon--delivery-type2" aria-hidden="true"></i>              
              <span class="c-stepper__title">배송대기</span>
            </li>      
            <li class="c-stepper__item <c:if test="${selfDlvryData.dlvryStateCode eq '02' && selfDlvryData.selfStateCode eq '01'}">is-active</c:if>">
              <i class="c-icon c-icon--delivery-type3" aria-hidden="true"></i>
              <c:if test="${selfDlvryData.dlvryStateCode eq '02' && selfDlvryData.selfStateCode eq '01'}"><span class="c-hidden">현재상태</span></c:if>
              <span class="c-stepper__title">배송중</span>
            </li>
            <li class="c-stepper__item <c:if test="${selfDlvryData.dlvryStateCode eq '03' || selfDlvryData.dlvryStateCode eq '04' && selfDlvryData.selfStateCode eq '01'}">is-active</c:if>">
              <!-- [2022-02-08] 아이콘 변경-->
              <i class="c-icon c-icon--delivery-type6" aria-hidden="true"></i>
              <c:if test="${selfDlvryData.dlvryStateCode eq '03' || selfDlvryData.dlvryStateCode eq '04' && selfDlvryData.selfStateCode eq '01'}"><span class="c-hidden">현재상태</span></c:if>
              <span class="c-stepper__title">배송완료</span>
            </li>
            </c:if>
            
            <c:if test="${selfDlvryData.dlvryKind eq '02'}">
            <!-- 현재 상태인 경우-->
            <li class="c-stepper__item <c:if test="${selfDlvryData.dlvryStateCode eq '01' || selfDlvryData.dlvryStateCode eq '06'}">is-active</c:if>">
              <i class="c-icon c-icon--delivery-type1" aria-hidden="true"></i>
              <c:if test="${selfDlvryData.dlvryStateCode eq '01' || selfDlvryData.dlvryStateCode eq '06'}"><span class="c-hidden">현재상태</span></c:if>
              <span class="c-stepper__title">접수대기</span>
            </li><!-- //-->
            <li class="c-stepper__item <c:if test="${selfDlvryData.dlvryStateCode eq '03'}">is-active</c:if>">
              <i class="c-icon c-icon--delivery-type2" aria-hidden="true"></i>
              <c:if test="${selfDlvryData.dlvryStateCode eq '03'}"><span class="c-hidden">현재상태</span></c:if>
              <span class="c-stepper__title">배송대기</span>
            </li>
            <li class="c-stepper__item <c:if test="${selfDlvryData.dlvryStateCode eq '02'}">is-active</c:if>">
              <i class="c-icon c-icon--delivery-type3" aria-hidden="true"></i>
              <c:if test="${selfDlvryData.dlvryStateCode eq '02'}"><span class="c-hidden">현재상태</span></c:if>
              <span class="c-stepper__title">배송중</span>
            </li>
            <li class="c-stepper__item <c:if test="${selfDlvryData.dlvryStateCode eq '04'}">is-active</c:if>">
              <!-- [2022-02-08] 아이콘 변경-->
              <i class="c-icon c-icon--delivery-type6" aria-hidden="true"></i>
              <c:if test="${selfDlvryData.dlvryStateCode eq '04'}"><span class="c-hidden">현재상태</span></c:if>
              <span class="c-stepper__title">배송완료</span>
            </li>	
            </c:if>
            
          </ol>
          
          <h4 class="c-heading c-heading--fs16 u-mt--48 u-mb--16">주문 상품정보</h4>
          <div class="detail">
            <!-- [2022-02-04] 이미지 마크업 수정 -->
            <div class="detail__image">
              <img src="../../resources/images/portal/content/temp_usim.png" alt="유심 실물 사진" aria-hidden="true">
            </div><!-- [$2022-02-04] 이미지 마크업 수정 -->
            <div class="detail__title">
              <span class="detail__sub-title c-hidden">상품명/금액</span>
              <b>${usimProdObj.dtlCdNm}</b>
              <b><fmt:formatNumber value="${selfDlvryData.usimPrice}" pattern="#,###"/>원</b>
            </div>
            <div class="detail__item">
              <span class="detail__sub-title c-hidden">배송상태</span>
              <b class="u-co-point-4">
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
                  <c:set var="dlvryStateObj" value="${nmcp:getCodeNmDto(Constants.USIM_PROD_STATE_CODE,selfDlvryData.dlvryStateCode)}" />
              </b>
            </div>
            <div class="c-button-wrap">
              
                 <c:set var="dlvryObj" value="${nmcp:getCodeNmDto(Constants.DLVRY_TB_CODE,selfDlvryData.tbCd)}" />
                 <c:set var="dlvryNoTmp" value="${fn:replace(selfDlvryData.dlvryNo, '-', '')}"/>
              
              <c:if test="${selfDlvryData.dlvryKind eq '01'}">
              <c:choose>
              <c:when test="${selfDlvryData.dlvryKind eq '01' && '02' eq selfDlvryData.dlvryStateCode || '03' eq selfDlvryData.dlvryStateCode || '04' eq selfDlvryData.dlvryStateCode}">   
                 <button class="c-button c-button-round--sm c-button--white" type="button" onclick="openPage('outLink', '${dlvryObj.dtlCdDesc}${dlvryNoTmp}', '');" <c:if test="${empty percelUrl || empty view.dlvryNo}">disabled="disabled"</c:if>>배송조회</button>
              </c:when>
              <c:otherwise>
              	 <button class="c-button c-button-round--sm c-button--white" type="button" onclick="openPage('outLink', '${dlvryObj.dtlCdDesc}${dlvryNoTmp}', '');" disabled="disabled">배송조회</button>
              </c:otherwise>
              </c:choose>   
              </c:if>
              
              <c:if test="${selfDlvryData.dlvryKind eq '02'}">
              <c:choose>
              <c:when test="${'02' eq selfDlvryData.dlvryStateCode || '04' eq selfDlvryData.dlvryStateCode}">   
                 <button class="c-button c-button-round--sm c-button--white" type="button"  onclick="openPage('outLink', '${dlvryObj.dtlCdDesc}${dlvryNoTmp}', '');" <c:if test="${empty percelUrl || empty view.dlvryNo}">disabled="disabled"</c:if>>배송조회</button>
              </c:when>
              <c:otherwise>
              	 <button class="c-button c-button-round--sm c-button--white" type="button"  onclick="openPage('outLink', '${dlvryObj.dtlCdDesc}${dlvryNoTmp}', '');" disabled="disabled">배송조회</button>
              </c:otherwise>
              </c:choose>   
              </c:if>
               	
            </div>
          </div>
          <h4 class="c-heading c-heading--fs16 u-mt--24">주문 상세내역</h4>
          <div class="c-table u-mt--16">
            <table>
              <caption>주문자명, 배송지 정보, 수령인 정보에 대한 주문 상세내역 표입니다.</caption>
              <colgroup>
                <col style="width: 33%">
                <col>
              </colgroup>
              <tbody class="c-table__left">
                <tr>
                  <th scope="row">주문자명</th>
                  <td>${nmcp:getMaskedName(selfDlvryData.cstmrName )}</td>
                </tr>
                <tr>
                  <th scope="row">배송지 정보</th>
                  <td><c:set var="dlvryAddr">${selfDlvryData.dlvryAddr} ${selfDlvryData.dlvryAddrDtl}</c:set>
                        ${selfDlvryData.dlvryPost}<br/>
                        ${nmcp:getMaskedByAddressNew2(dlvryAddr)}
                  </td>
                </tr>
                <tr>
                  <th scope="row">수령인 정보</th>
                  <td>수령인 명 : ${nmcp:getMaskedName(selfDlvryData.dlvryName )}
                  <c:set value="${selfDlvryData.dlvryTelFn}-${selfDlvryData.dlvryTelMn}-${selfDlvryData.dlvryTelRn}" var="cstmrMobile" /> 
                    <br>연락처 : ${nmcp:getMaskedTelNo(cstmrMobile)}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
          