<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<div class="c-select-search">
    <div class="phone-line">
        <span class="phone-line__cnt">${searchVO.moCtn}</span>
    </div>
    <div class="c-form--line">
       <label class="c-label c-hidden" for="ctn">회선 선택</label>
       <select class="c-select" name="ctn" id="ctn" >
       <c:forEach items="${cntrList}" var="item">
            <c:choose>
                <c:when test="${maskingSession eq 'Y'}">
                    <option value="${item.svcCntrNo}" ${item.svcCntrNo eq searchVO.ncn?'selected':''} >${item.formatUnSvcNo}</option>
                </c:when>
                <c:otherwise>
                    <option value="${item.svcCntrNo}" ${item.svcCntrNo eq searchVO.ncn?'selected':''} >${item.cntrMobileNo}</option>
                </c:otherwise>
           </c:choose>
       </c:forEach>
       </select>
    </div>
    <button class="c-form--line__btn" id="btnSelectCTN" onclick="select();">조회</button>
    <button class="c-form--phoneline__btn" type="button" onclick="location.href='/mypage/multiPhoneLine.do' ">회선관리</button>
</div>