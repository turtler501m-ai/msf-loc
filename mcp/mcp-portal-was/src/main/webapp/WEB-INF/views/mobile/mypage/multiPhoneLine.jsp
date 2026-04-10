
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un"
	uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<un:useConstants var="Constants"
	className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript"
			src="/resources/js/mobile/mypage/multiPhoneLine.js?ver=${jsver}"></script>
	</t:putAttribute>
	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					<span id="mid-title">
						다회선인증
					</span>
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-table c-table--plr-8 u-mt--32">
				<p class="c-table-title u-co-black fs-16 u-fw--medium">내 회선 정보</p>
				<table>
					<caption>내 회선 정보</caption>
					<colgroup>
						<col style="width: 34%">
						<col style="width: 32%">
						<col style="width: 34%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">휴대폰 번호</th>
							<th scope="col">단말기정보</th>
							<th scope="col">대표번호선택</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
			<div class="c-button-wrap u-mt--48">
				<a class="c-button c-button--full c-button--primary" id="mainBut">다회선 추가</a>
			</div>
			<div class="c-notice-wrap">
                <hr>
                <h5 class="c-notice__title">
                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                    <span>알려드립니다</span>
                </h5>
                <ul class="c-notice__list">
                    <li>다회선 중 대표번호를 선택(변경) 시 회원정보의 휴대폰 번호가 선택한 대표회선 번호로 변경됩니다.</li>
                	<li>대표회선과 다른 번호로 변경을 원하실 경우 회원정보를 수정해주시기 바랍니다.</li>
                </ul>
            </div>
		</div>

		<!-- [START]-->




	</t:putAttribute>
</t:insertDefinition>