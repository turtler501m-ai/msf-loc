<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un"
	uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="contentAttr">

    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">1:1 상담문의<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <div class="c-tabs c-tabs--type2">
        <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
          <button class="c-tabs__button" data-tab-header="#tabB1-panel">1:1 문의작성</button>
          <button class="c-tabs__button" data-tab-header="#tabB2-panel">내 문의 확인하기</button>
        </div>
        <div class="c-tabs__panel" id="tabB1-panel">
          
        </div>
        <div class="c-tabs__panel" id="tabB2-panel"></div>
      </div>
    </div>
	</t:putAttribute>
</t:insertDefinition>