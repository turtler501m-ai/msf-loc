<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<% response.setStatus(HttpServletResponse.SC_OK); %>

<t:insertDefinition name="layoutGnbDefault">

	<t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
		<div class="c-row c-row--lg">
			<div class="error u-ta-center">
				<i aria-hidden="true"> <!-- [2022-02-08] 이미지확장자 변경-->
					<img src="/resources/images/portal/common/img_505_error.png" alt="">
				</i>
				<strong class="c-heading c-heading--fs24 u-block u-mt--48"> <b>죄송합니다. <br>요청하신 페이지를 찾을 수 없습니다.</b>
				</strong>
				<p class="c-text c-text--fs17 u-co-gray u-mt--16">
					접속하시려는 페이지의 주소(URL)가 잘못 입력되거나, 페이지의 주소가 변경 또는 삭제되어 해당 페이지에 접속 할 수 없습니다. <br>
					입력하신 주소(URL)를 다시 한번 확인해주시기 바라며, 궁금한 사항은 언제든지 고객센터로 문의하여 주시기 바랍니다.
				</p>
			</div>
			<div class="c-button-wrap u-mt--56">
				<a class="c-button c-button--lg c-button--primary c-button--w460" href="/main.do">메인화면으로 이동</a>
			</div>
		</div>
	</div>
	</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script language="javascript">
			function noEvent() {
				if (event.keyCode == 116) {
					event.keyCode = 2;
					return false;
				} else if (event.ctrlKey
						&& (event.keyCode == 78 || event.keyCode == 82)) {
					return false;
				}
			}
			document.onkeydown = noEvent;
		</script>
	</t:putAttribute>
</t:insertDefinition>