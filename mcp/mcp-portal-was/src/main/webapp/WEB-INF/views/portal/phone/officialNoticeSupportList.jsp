<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">단말 공통지원금 안내</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/portal/phone/officialNoticeSupportList.js"></script>
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
        <input type="hidden" name="recordCount" id="recordCount" value="15" />
        <input type="hidden" name="pageSize" id="pageSize" value="15" />
    </form>
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">단말 공통지원금 안내</h2>
      </div>
      <div class="c-row c-row--lg">
        <div class="c-text c-text--fs17">kt M모바일에서 판매하는 단말기 공통지원금입니다.</div>
        <div class="c-form-wrap u-mt--48">
          <div class="c-form-group" role="group" aira-labelledby="device_group">
            <h3 class="c-group--head" id="device_group">단말구분</h3>
            <div class="c-checktype-row">
<%--              <input class="c-radio" id="radTerm1" type="radio" name="radPrdtIndCd" value="" checked>--%>
<%--              <label class="c-label" for="radTerm1">전체</label>--%>
                <input class="c-radio" id="radTerm1" type="radio" name="radPrdtIndCd" value="45" onclick="rateFilter()" checked>
                <label class="c-label" for="radTerm1">5G</label>
                <input class="c-radio" id="radTerm2" type="radio" name="radPrdtIndCd" value="04" onclick="rateFilter()">
                <label class="c-label" for="radTerm2">LTE</label>
                <input class="c-radio" id="radTerm3" type="radio" name="radPrdtIndCd" value="03" onclick="rateFilter()">
                <label class="c-label" for="radTerm3">3G</label>
            </div>
          </div>
          <div class="c-form-wrap u-mt--4">
            <div class="c-form-row c-col2">
              <div class="c-form-field">
                <div class="c-form__input">
                  <input class="c-input" id="inpBankNum" maxlength="20" name="inpPrdtNm" type="text" placeholder="모델명 입력해주세요.">
                  <label class="c-form__label" for="inpBankNum">모델</label>
                </div>
              </div>
              <div class="c-form-field">
                <div class="c-form__select">
                  <select id="selRateCd" name="selRateCd" class="c-select">
                    <option value="">전체</option>
                    <c:forEach var="rateItem" items="${rateList}">
                        <option value="<c:out value="${rateItem.rateCd}"/>" value2="<c:out value="${rateItem.dataType}"/>"><c:out value="${rateItem.rateNm}"/></option>
                    </c:forEach>
                  </select>
                  <label class="c-form__label" for="selRateCd">요금제</label>
                </div>
              </div>
            </div>
            <div class="c-button-wrap u-mt--40">
              <button id="schbtn" class="c-button c-button-round--md c-button--mint c-button--w460" type="button">검색</button>
            </div>
          </div>
        </div>
        <div id="divSortArea" class="c-sort-line u-mt--64">
          <button type="button" id="sortSpt" data-value="sptH" class="c-button c-button--sort">
            <span class="u-fs-15" ><span id="sortSptText">지원금 높은 순</span><span class="c-hidden">정렬하기</span>
              <i class="c-icon c-icon--sort" aria-hidden="true"></i>
            </span>
          </button>
          <button type="button" id="sortPrc" data-value="prcL" class="c-button c-button--sort">
            <span class="u-fs-15"><span id="sortPrcText">판매가 낮은 순</span><span class="c-hidden">정렬하기</span>
              <i class="c-icon c-icon--sort" aria-hidden="true"></i>
            </span>
          </button>
        </div>
        <div class="c-row c-row--lg c-row--divider-top u-mt--16"></div>
        <!--검색결과 없는 경우 (추후 디자인 완료 시 변경)-->
        <div id="nodataArea" class="c-nodata" style="display: none;">
          <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
          <p class="c-nodata__text">검색 결과가 없습니다.</p>
        </div>
        <!--페이지 리스트 15건 노출-->
        <div id="divTableArea" class="c-table u-mt--48">
          <table>
            <caption>단말구분, 모델명, 요금제, 출고가, 공통지원금, 판매가를 포함하는 표</caption>
            <colgroup>
<%--              <col style="width: 90px">--%>
              <col>
              <col>
              <col style="width: 128px">
              <col style="width: 128px">
              <col style="width: 128px">
              <col style="width: 128px">
            </colgroup>
            <thead>
              <tr>
<%--                <th scope="col">단말구분</th>--%>
                <th scope="col">모델명</th>
                <th scope="col">요금제</th>
                <th scope="col">출고가</th>
                <th scope="col">공통지원금</th>
                <th scope="col">판매가</th>
                <th scope="col">공시일</th>
              </tr>
            </thead>
            <tbody id="officialNoticeSupportTbody">
            </tbody>
          </table>
        </div>
        <nav class="c-paging" aria-label="검색결과">
        </nav>
      </div>
    </div>
    </t:putAttribute>
</t:insertDefinition>
