<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/login/addBirthGenderView.js?version=22-10-12"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <form name="frm" id="frm" method="post">
                <div class="ly-page-sticky">
                    <h2 class="ly-page__head">
                        추가정보 입력
                        <button class="header-clone__gnb"></button>
                    </h2>
                </div>

                <h3 class="c-heading c-heading--type1">정보를 입력해주세요</h3>
                <p class="c-text c-text--type2 u-co-gray">
                    원활한 서비스 이용을 위해 <br>추가정보 입력이 필요합니다.
                </p>


                <div class="c-form c-form--datepicker">
                    <span class="c-form__title" id="radB">생년월일</span>
                    <div class="c-input-wrap u-mt--0">
                        <input type="hidden" name="birthday" id="birthday"/>
                        <input class="c-input" type="date" placeholder="생년월일" title="생년월일 입력" id="birthdayTxt" name="birthdayTxt" max="9999-12-31" value="">
                        <button class="c-button c-button--calendar">
                            <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                        </button>
                    </div>
                </div>

                <div class="c-button-wrap" onclick="return false;">
                    <button class="c-button c-button--full c-button--gray" onclick="javascript:location.href='/m/loginForm.do';">취소</button>
                    <button class="c-button c-button--full c-button--primary" onclick="finalSubmit();">확인</button>
                </div>
                <input type="hidden" name="userId" id="userId" value="${extraInfo.userId}"/>
                <input type="hidden" name="passWord" id="passWord" value="${extraInfo.passWord}"/>
                <input type="hidden" name="loginType" id="loginType" value="${extraInfo.loginType}"/>
            </form>
        </div>
    </t:putAttribute>
</t:insertDefinition>