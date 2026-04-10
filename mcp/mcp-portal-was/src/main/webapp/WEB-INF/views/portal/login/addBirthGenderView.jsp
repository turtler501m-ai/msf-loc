<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">추가정보 입력</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/login/addBirthGenderView.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">추가정보 입력</h2>
            </div>
            <!--[2021-01-05] divider 수정-->
            <div class="c-row c-row--xlg c-row--divider-top"></div>
            <div class="c-row c-row--sm">
                <h3 class="c-heading--fs22 c-heading--bold u-mt--56">정보입력</h3>
                <p class="u-co-gray-8 u-mt--16">원활한 서비스 이용을 위해 추가정보 입력이 필요합니다.</p>

                <form name="frm" id="frm" method="post">
                    <input type="hidden" name="userId" id="userId" value="${extraInfo.userId}"/>
                    <input type="hidden" name="passWord" id="passWord" value="${extraInfo.passWord}"/>
                    <input type="hidden" name="loginType" id="loginType" value="${extraInfo.loginType}"/>

                    <div class="c-form u-mt--48">
                        <label class="c-label" for="inpCalA">생년월일</label>
                        <div class="c-input-wrap">
                            <input class="c-input flatpickr" id="birthday" name="birthday" type="text" placeholder="YYYYMMDD">
                            <span class="c-button c-button--icon c-button--calendar">
                                <span class="c-hidden">날짜선택</span>
                                <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                            </span>
                        </div>
                    </div>
                </form>

                <div class="c-button-wrap u-mt--56">
                    <button class="c-button c-button--full c-button--primary" type="button" onclick="finalSubmit();">확인</button>
                </div>
            </div>
            <script src="/resources/js/portal/vendor/flatpickr.min.js"></script>
            <script>
                // 인풋에 데이터 자동으로 바인딩 됨
                document.addEventListener('UILoaded', function() {
                  KTM.datePicker('.flatpickr',{
                      maxDate: new Date().format("yyyy-MM-dd")
                  });
                });
            </script>
        </div>
    </t:putAttribute>
</t:insertDefinition>
