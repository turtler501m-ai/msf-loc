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
        <script type="text/javascript" src="/resources/js/portal/mypage/numberView01.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">번호변경</h2>
            </div>
            <div class="c-row c-row--lg">
                <h3 class="c-heading c-heading--fs20 c-heading--regular">조회할 회선을 선택해 주세요.</h3>
                <%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>
                <hr class="c-hr c-hr--title">
                <p class="c-text c-text--fs24 u-fw--medium u-co-blue">kt M모바일에서 편하게 번호변경하세요!</p>
                <p class="c-text c-text--fs17 u-mt--8">사용중인 번호를 간편하고 빠르게 다른 번호로 변경할 수 있는 서비스입니다.</p>
                <div class="c-form-wrap u-mt--48">
                    <h4 class="c-form__title">휴대폰 번호 변경 본인인증 동의</h4>
                    <div class="c-agree">
                        <div class="c-agree__item">
                            <div class="c-agree__inner">
                                <input class="c-checkbox" id="chkCertiAgree" type="checkbox" name="chkCertiAgree">
                                <label class="c-label" for="chkCertiAgree">본인인증 절차 동의</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="c-box c-box--type1 u-mt--20">
                    <p class="c-bullet c-bullet--dot u-co-gray">안전한 서비스 이용 및 고객님의 개인정보 보호를 위해 본인 인증을 받으신 고객분만 이용이 가능합니다. 서비스 이용을 위해서 본인확인 절차를 진행해주세요.</p>
                </div>
                <div class="c-button-wrap u-mt--56">
                    <c:choose>
                        <c:when test="${prePaymentFlag}">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" onclick="MCP.alert('선불 요금제 가입자는 번호변경이 불가능 합니다.')">SMS 인증</button>
                        </c:when>
                        <c:otherwise>
                            <button class="c-button c-button--lg c-button--primary c-button--w460" onclick="authSms();">SMS 본인인증</button>
                        </c:otherwise>
                    </c:choose>
                </div>
                <h4 class="c-heading c-heading--fs16 u-mt--48">번호변경 불가능한 고객</h4>
                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                    <li class="c-text-list__item u-co-gray">3개월 내 휴대폰 번호를 2회이상 변경한 경우</li>
                    <li class="c-text-list__item u-co-gray">미납 정지 고객, 일시 정지 고객</li>
                    <li class="c-text-list__item u-co-gray">선불 요금제 가입자</li>
                    <li class="c-text-list__item u-co-gray">미납 요금이 있는 경우</li>
                    <li class="c-text-list__item u-co-gray">특정 부가서비스 사용 고객(010번호 연결서비스, 스팸차단서비스 등)</li>
                </ul>

                <h5 class="combine-notice__tit u-mt--48">
                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                    <span>알려드립니다</span>
                </h5>
                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                    <li class="c-text-list__item u-co-gray">번호변경은 평일 오전 10시~오후 5시까지 가능합니다.(주말, 공휴일 변경 불가)</li>
                    <li class="c-text-list__item u-co-gray">휴대폰 번호변경은 3개월 2회까지 가능하며, 번호변경 취소도 횟수에 포함됩니다.</li>
                    <li class="c-text-list__item u-co-gray">번호변경 취소는 당일에 한해 고객센터를 통해 취소 가능합니다.<br/>
                        - 고객센터 : 1899-5000(무료 kt M모바일 114) 운영시간 : 09~12시/13~18시</li>
                    <li class="c-text-list__item u-co-gray">번호변경 시 번호 재사용이 전면 중단됩니다.</li>
                    <li class="c-text-list__item u-co-gray">0000, 1234, 7777 (선호번호) 및 1200, 4299, 0016, 1166, 1167, 1168, 1169 (7개 특수번호)와 같은 특정번호는 선택이 불가합니다.<br/>
                        (일부 조회되더라도 선택 불가함)</li>
                    <li class="c-text-list__item u-co-gray">번호변경 후 연속으로 휴대폰 전원을 2~3회 껐다 켜주셔야 변경된 번호로 설정이 완료됩니다.</li>
                    <li class="c-text-list__item u-co-gray">번호변경 후에는 회원정보관리 정보수정에서 변경된 휴대폰 번호를 등록하셔야 ‘홈페이지 회원ID 찾기’ 서비스 이용이 가능합니다.</li>
                </ul>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
