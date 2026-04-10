<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">유심구매</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript">
            history.pushState(null, null,location.href);
                window.onpopstate = function (event){
                    var newForm = $('<form></form>');
                    newForm.attr('name','newForm');
                    newForm.attr('method','post');
                    newForm.attr('action','/appForm/reqSelfDlvry.do');
                    newForm.appendTo('body');
                    newForm.submit();
            }

            window.onpageshow = function (evt){
                if(evt.persisted || window.performance.navigation.type == 2){
                    location.href = '/appForm/reqSelfDlvry.do';
                }
            }
        </script>
    </t:putAttribute>
    <t:putAttribute name="bottomScript">
        <!-- naver DA script  통계  전환페이지 설정 -->
        <script type="text/javascript">
            if(window.wcs){
                // (5) 결제완료 전환 이벤트 전송
                var _conv = {}; // 이벤트 정보를 담을 object 생성
                _conv.type = "custom002";  // object에 구매(purchase) 이벤트 세팅
                wcs.trans(_conv); // 이벤트 정보를 담은 object를 서버에 전송
            }
        </script>
        <!-- naver DA script  통계  전환페이지 end -->
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">유심구매</h2>
            </div>
            <!--[2021-01-05] divider 수정-->
            <div class="c-row c-row--xlg c-row--divider-top"></div>
            <div class="c-row c-row--md">
                <div class="complete u-mt--80 u-ta-center">
                    <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                    <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
                        <b>유심 구매</b>가 정상적으로 <br> <b>완료</b>되었습니다
                    </h3>
                    <p class="c-text c-text--fs20 u-co-gray u-mt--16">내 소비패턴에 맞춘 실속 있는 다양한 요금제를 찾아보세요.</p>
                    <div class="c-button-wrap u-mt--56">
                        <!-- 비활성화 시 .is-disabled 클래스 추가-->
                        <a class="c-button c-button--lg c-button--gray u-width--220" href="/order/orderList.do">신청조회</a>
                        <a class="c-button c-button--lg c-button--primary u-width--220" href="/rate/rateList.do">요금제 알아보기</a>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
