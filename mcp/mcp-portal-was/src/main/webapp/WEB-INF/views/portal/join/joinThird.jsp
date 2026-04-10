<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">회원가입</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.placeholder.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                window.onpageshow = function (evt){
                    if(evt.persisted || window.performance.navigation.type == 2){
                        location.href = '/loginForm.do';
                        /*
                        ajaxCommon.createForm({
                            method:'post'
                            ,action:'/loginForm.do'
                         });
                        ajaxCommon.formSubmit();
                        */

                    }
                }

                $('input, text').placeholder();

                $(".btn_complete").on("click", function() {
                    location.href="${pageContext.request.contextPath}/m/loginForm.do";
                });

                if($('#aleadyMbrYn').val() == 'Y'){
                    $('.case1').hide();
                    $('.case2').show();
                    $('.case3').show();
                }else{
                    $('.case1').show();
                    $('.case2').hide();
                    $('.case3').show();
                    if($('#mbrSnsAddYn').val() == 'Y'){
                        $('.case1_1').show();
                        $('.case1_2').show();
                    }else{
                        $('.case1_1').show();
                        $('.case1_2').hide();

                    }
                }

                $('#snsAddBtn').on('click', function (){
                    if($.trim($('#snsCd').val()) != '' && $.trim($('#snsId').val())){
                        var varData = ajaxCommon.getSerializedData({
                           snsCd : $('#snsCd').val()
                           , snsId : $('#snsId').val()
                           , encUserId : $('#encUserId').val()
                           , name : $('#userName').val()
                           , clausePriAdFlag : $('#clausePriAdFlag').val()
                        });

                        ajaxCommon.getItem({
                            id:'snsAdd'
                            ,cache:false
                            ,url:'/join/snsAdd.do'
                            ,data: varData
                            ,dataType:"json"
                        }
                        ,function(jsonObj){
                            var resultCode = jsonObj.resltCd;
                            if(resultCode=="0000"){
                                $('.case1').show();
                                $('.case2').hide();
                                $('.case3').show();
                                $('.case1_1').hide();
                                $('.case1_2').show();
                            } else {
                                MCP.alert("처리중 오류가 발생하였습니다. 잠시후 다시 이용해주세요.");
                                return false;
                            }
                        });

                    }else{
                        MCP.alert('유효하지 않은 접근입니다. SNS 인증을 다시 받아주세요.');
                        location.href = '/loginForm.do';
                    }
                });
                if($("#regularYn").val() == 'Y'){
                    MCP.alert("고객님은 정회원으로 확인되었습니다.");
                }
            });
        </script>
        <!-- 카카오 광고 분석  -->
        <script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
        <script type="text/javascript">
              kakaoPixel('5981604067138488988').pageView();
              kakaoPixel('5981604067138488988').signUp();
        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <input type="hidden" name="mbrSnsAddYn" id="mbrSnsAddYn" value="${mbrSnsAddYn}" />
            <input type="hidden" name="clausePriAdFlag" id="clausePriAdFlag" value="${getIpinInfo.clausePriAdFlag}" />
            <input type="hidden" name="encUserId" id="encUserId" value="${getIpinInfo.encUserId}"/>
            <input type="hidden" name="snsCd" id="snsCd" value="${getIpinInfo.snsCd}"/>
            <input type="hidden" name="snsId" id="snsId" value="${getIpinInfo.snsId}"/>
            <input type="hidden" name="userName" id="userName" value="${getIpinInfo.name}"/>
            <input type="hidden" name="aleadyMbrYn" id="aleadyMbrYn" value="${aleadyMbrYn}" />
            <input type="hidden" name="regularYn" id="regularYn" value="${regularYn}" />

            <div class="ly-page--title">
                <h2 class="c-page--title">회원가입</h2>
            </div>
            <!--[2021-01-05] divider 수정-->
            <div class="c-row c-row--xlg c-row--divider-top"></div>
            <div class="c-row c-row--sm">
                <div class="complete u-mt--80 u-ta-center case1" style="display: none;">
                    <div class="c-icon c-icon--complete" aria-hidden="true">
                        <span></span>
                    </div>
                    <p class="c-heading--fs22 u-mt--32">${getIpinInfo.name}님!</p>
                    <!-- 일반회원 가입완료-->
                    <p class="c-text--fs20 u-co-gray-8 u-mt--16 case1_1">kt M모바일 회원가입이 완료되었습니다.</p>
                    <p class="c-text--fs20 u-co-gray-8 u-mt--16 case1_2">SNS 아이디 추가가 완료되었습니다.</p>
                    <div class="complete__deoco u-mt--32">
                        <img src="/resources/images/portal/content/img_complete_deco.png" alt="">
                    </div>
                    <p class="c-text--fs16 u-mt--32">
                        국내 1위 알뜰폰 kt M모바일의 요금제를 <br>지금 바로 만나보세요!
                    </p>
                    <div class="c-button-wrap u-mt--56">
                        <button class="c-button c-button--full c-button--primary" onclick="javascript:location.href='/loginForm.do';">로그인</button>
                    </div>
                </div>

                <div class="c-row c-row--sm case2" style="display: none;">
                    <div class="complete u-mt--80 u-ta-center">
                        <div class="c-icon c-icon--complete" aria-hidden="true">
                            <span></span>
                        </div>
                        <p class="c-heading--fs22 u-mt--32">${getIpinInfo.name}님!</p>
                        <!-- 소셜 아이디 연동-->
                        <p class="c-text--fs20 u-co-gray-8 u-mt--16">이미 가입된 아이디가 있습니다.</p>
                        <div class="complete__deoco u-mt--32">
                            <img src="/resources/images/portal/content/img_complete_deco.png" alt="">
                        </div>
                        <p class="c-text--fs28 u-fw--medium u-mt--32">${getIpinInfo.userId}</p>
                        <p class="c-text--fs16">@${getIpinInfo.snsCd}로 연결하여 간편하게 로그인하세요.</p>
                        <div class="c-button-wrap u-mt--56 case3">
                            <button class="c-button c-button--full c-button--primary" onclick="javascript:location.href='/loginForm.do';">로그인</button>
                            <c:choose>
                                <c:when test="${getIpinInfo.snsCd eq 'KAKAO'}">
                                    <button class="c-button c-button--full c-button--kakao" id="snsAddBtn">카카오로 연결</button>
                                </c:when>
                                <c:when test="${getIpinInfo.snsCd eq 'NAVER'}">
                                    <button class="c-button c-button--full c-button--naver" id="snsAddBtn">네이버로 연결</button>
                                </c:when>
                                <c:when test="${getIpinInfo.snsCd eq 'APPLE'}">
                                    <button class="c-button c-button--full c-button--primary" id="snsAddBtn">APPLE로 연결</button>
                                </c:when>
                                <c:otherwise>
                                    <button class="c-button c-button--full c-button--facebook" id="snsAddBtn">페이스북으로 연결</button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>