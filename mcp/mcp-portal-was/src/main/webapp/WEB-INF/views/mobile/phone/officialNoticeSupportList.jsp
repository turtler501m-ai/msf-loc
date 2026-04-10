<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
    	<script type="text/javascript" src="/resources/js/mobile/phone/officialNoticeSupportList.js"></script>
        <script>
        </script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
    <form name="form" id="form" method="post">
    	<input type="hidden" name="pageNo" id="pageNo" value="1" />
		<input type="hidden" name="currentPage" id="currentPage" value="1" />
		<input type="hidden" name="countPerPage" id="countPerPage" value="10" />
		<input type="hidden" name="keyword" id="keyword" value="" />
		<input type="hidden" name="prdtIndCd" id="prdtIndCd" value="" />
		<input type="hidden" name="rateCd" id="rateCd" value="" />
		<input type="hidden" name="prdtNm" id="prdtNm" value="" />
		<input type="hidden" name="sortType" id="sortType" value="sptHprcL" />
	</form>
		<div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">단말 공통지원금 안내<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <p class="c-text c-text--type2 u-mt--24 u-co-gray">kt M모바일에서 판매하는 단말기
        <br>공통지원금입니다.
      </p>
      <div class="c-form u-mt--16">
        <span class="c-form__title" id="radTerm">단말구분</span>
        <div class="c-check-wrap" role="group" aria-labelledby="radTerm">
          <input class="c-radio c-radio--button" id="radTerm1" type="radio" type="radio" name="radPrdtIndCd"  onclick="rateFilter()" value="45" checked>
          <label class="c-label" for="radTerm1">5G</label>
          <input class="c-radio c-radio--button" id="radTerm2" type="radio" name="radPrdtIndCd"  onclick="rateFilter()" value="04">
          <label class="c-label" for="radTerm2">LTE</label>
          <input class="c-radio c-radio--button" id="radTerm3" type="radio" name="radPrdtIndCd"  onclick="rateFilter()" value="03">
          <label class="c-label" for="radTerm3">3G</label>
        </div>
        <div class="c-form__input u-mt--40">
          <input class="c-input" id="inpBankNum" type="text" name="inpPrdtNm" placeholder="모델명 입력" maxlength="20">
          <label class="c-form__label" for="inpBankNum">모델</label>
        </div>
        <div class="c-form__select">
          <select id="selRateCd" name="selRateCd" class="c-select">
            <option value="">전체</option>
              <c:forEach var="rateItem" items="${rateList}">
                  <option value="<c:out value="${rateItem.rateCd}"/>" value2="<c:out value="${rateItem.dataType}"/>"><c:out value="${rateItem.rateNm}"/></option>
              </c:forEach>
          </select>
          <label class="c-form__label">요금제</label>
        </div>
      </div>
      <div class="c-button-wrap u-pb--24">
        <button type="button" id="schbtn" class="c-button c-button--full c-button--mint">검색</button>
      </div>
      <div class="payment-result c-expand u-mb--m32 u-pt--0 u-pb--64 u-bg--gray-2">
        <div class="c-button-wrap c-expand">
          <button type="button" id="sortSpt" data-value="sptH" class="c-button c-button--sort c-button--full">
            <span class="fs-md"><span id="sortSptText">지원금 높은 순</span><!-- <i class="c-icon c-icon--sort" aria-hidden="true"></i> -->
            </span>
          </button>
          <span class="line"></span>
          <button type="button" id="sortPrc" data-value="prcL" class="c-button c-button--sort c-button--full">
            <span class="fs-md"><span id="sortPrcText">판매가 낮은 순</span><!-- <i class="c-icon c-icon--sort" aria-hidden="true"></i> -->
            </span>
          </button>
        </div>
        <!--[2021-12-16] nodata ( 테이블과 페이징 없이 c-nodata만 나오도록 처라 )-->
        <div class="c-nodata" style="display: none;">
          <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
          <p class="c-nodata__text">검색 결과가 없습니다.</p>
        </div>
        <div id= "officialNoticeSupportTbody">
		</div>
        <!--[$2021-12-02] .c-table--plr-8 클래스 추가, scope추가, 정렬클래스 추가(디자인 변경 적용) 끝=====-->
        <nav class="c-paging" aria-label="검색결과">
        </nav>
      </div>
    </div>
    </t:putAttribute>
</t:insertDefinition>