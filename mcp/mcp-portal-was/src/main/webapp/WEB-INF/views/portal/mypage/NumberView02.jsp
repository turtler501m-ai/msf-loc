<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">번호변경</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/mypage/numberView02.js"></script>
        <script type="text/javascript">
            history.pushState(null, null,location.href);
            window.onpopstate = function (event){
            location.href = "mypage/numberView01.do";
         }
        </script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <input type="hidden" name="resvHkCtn" id="resvHkCtn" value="" />
        <input type="hidden" name="resvHkSCtn" id="resvHkSCtn" value="" />
        <input type="hidden" name="resvHkMarketGubun" id="resvHkMarketGubun" value="" />
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">번호변경</h2>
            </div>
            <div class="c-row c-row--lg">
                <h3 class="c-heading c-heading--fs20 c-heading--regular" id="inpJoinInfoHead">희망번호 선택</h3>
                <hr class="c-hr c-hr--title">
                <div class="c-form-field u-width--460">
                    <div class="c-form__select">
                        <select class="c-select" id="ctn" name="ctn" disabled="disabled">
                            <option value="${ncn}" selected="selected">${maskCtn}</option>
                        </select>
                        <label class="c-form__label" for="selBeforeNum">변경전 번호</label>
                    </div>
                </div>

                <div class="c-form-wrap reservation">
                    <div class="c-form-group u-mt--48" role="group" aira-labelledby="inpHopeNumHead">
                        <div class="c-flex c-flex--jfy-between">
                            <h4 class="c-group--head" id="inpHopeNumHead">희망번호 입력</h4>
                            <span class="u-co-point-4 u-fs-15">조회 가능 횟수 : <b id="searchCnt">${searchCnt}회</b>	</span>
                        </div>
                        <!-- 번호 선택 전-->
                        <div id="preSelPhoneDivArea" class="c-box c-box--type1">
                            <span class="reservation__text">010 - **** -</span>
                            <input class="c-input" id="hopeNum" type="number" name="hopeNum" maxlength="4" pattern="[0-9]" placeholder="" title="핸드폰 번호 마지막 네 자리">
                            <button type="button" class="c-button c-button--xsm c-button--mint c-button-round--xsm" data-dialog-trigger="#modalNumInquiry" id="btnSearch" disabled="disabled">번호조회</button>
                        </div>
                        <!-- 번호 선택 후-->
                        <div id="postSelPhoneDivArea" class="c-box c-box--type1" style="display: none;">
                            <span class="reservation__text u-co-gray-7">선택번호</span>
                            <span id="selPhoneNumSpanArea" class="reservation__text u-co-black"></span>
                            <button type="button" class="c-button c-button--xsm c-button--mint c-button-round--xsm" onclick="selPhoneNumCancel();">번호취소</button>
                        </div>
                        <p class="c-bullet c-bullet--dot u-co-gray u-mt--16">조회 가능 횟수를 초과할 경우 신청서를 재작성 해야 합니다.</p>
                    </div>
                </div>

                <div class="c-button-wrap u-mt--40">
                    <button type="button" class="c-button c-button--lg c-button--primary c-button--w460" onclick="goPhoneNumChange();">변경</button>
                </div>

                <b class="c-flex c-text c-text--fs15 u-mt--48">
                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                    <span class="u-ml--4">알려드립니다</span>
                </b>
                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                    <li class="c-text-list__item u-co-gray">휴대폰 번호변경은 3개월내 2회만 가능하오니 신중하게 선택 해주세요. (희망번호 검색은 일 최대 50회까지 조회가능)
                        <br>끝자리 4자리 번호를 선택/입력하여 검색 후 변경하실 수 있습니다.
                        <p class="c-bullet c-bullet--fyr u-co-sub-4">번호변경 가능한 시간은 평일 오전10시~오후8시까지 가능합니다. (주말 공휴일은 변경불가)</p>
                    </li>
                </ul>
            </div>
        </div>

        <div class="c-modal c-modal--md" id="modalNumInquiry" role="dialog" tabindex="-1" aria-labelledby="#modalNumInquiryTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="modalNumInquiryTitle">휴대폰번호 검색</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="c-form-wrap">
                            <div class="c-form-group" role="group" aira-labelledby="radNumGroupHead">
                                <h3 class="c-group--head u-fs-20 u-fw--regular" id="radNumGroupHead">전화번호 선택</h3>
                                <div class="c-checktype-row c-col2 u-mt--32" id="selNumDivArea">
                                </div>
                            </div>
                        </div>
                        <div class="c-button-wrap u-mt--32">
                            <button class="c-button c-button--lg c-button--gray u-width--220" data-dialog-close>취소</button>
                            <button id="selPhoneNumCfrmBtn" type="button" class="c-button c-button--lg c-button--primary u-width--220" onclick="selPhoneNum();" disabled="disabled">확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
