<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>

        <script type="text/javascript">
            $("#menuType").val("sharing");

            function btn_ok(){
                if($("#certifyYn").val() != 'Y'){
                    MCP.alert("인증을 받아주세요.");
                    return;
                }else{
                    ajaxCommon.createForm({
                        method:"post"
                        ,action:"/m/content/dataSharingStep2.do"
                    });
                    ajaxCommon.attachHiddenElement("userDivisionYn",$("#userDivisionYn").val());
                    ajaxCommon.attachHiddenElement("phoneNum",$("#phoneNum").val());
                    ajaxCommon.attachHiddenElement("menuType",$("#menuType").val());
                    ajaxCommon.formSubmit();
                }
            }

            function btnRegDtl(param){
                openPage('termsInfoPop','/termsPop.do',param);
            }


            $(document).ready(function (){
                // 자주 묻는 질문 및 유의사항
                ajaxCommon.getItemNoLoading({
                    id:'getNotice'
                    ,cache:false
                    ,url:'/termsPop.do'
                    ,data: "cdGroupId1=TERMSSHA&cdGroupId2=faqImportantInfoM"
                    ,dataType:"json"
                } ,function(jsonObj){
                    var inHtml = unescapeHtml(jsonObj.docContent);
                    $('.data-sharing-notice').html(inHtml);
                    KTM.initialize();
                });
            })

        </script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">

            <form name="frm" id="frm" method="post" action="/m/loginForm.do">
                <input type="hidden" name="uri" id="uri" value="/m/content/dataSharingStep1.do"/>
                <input id="certifyYn" type="hidden" value="N" />
                <input type="hidden" id="userDivisionYn" name="userDivisionYn" value=""/>
            </form>

            <%@ include file="/WEB-INF/views/mobile/content/dataSharingInfo.jsp"%>

            <div class="data-sharing-form u-mt--48">
                <p class="u-fs-16 u-fw--bold u-ta-center">정보 확인을 위해 인증 방법을 선택 후<br />데이터쉐어링 가입을 신청하세요.</p>
                <div class="c-button-wrap u-mt--32">
                    <!-- 로그인 및 휴대폰 인증 스크립트 필요 / 가족결합+ combineKt.js 참고 -->
                    <a id="btnLogin" class="c-button c-button--full c-button--primary" href="javascript:void(0);" onclick="$('#frm').submit();" title="로그인 페이지 바로가기">로그인 하기</a>
                    <button type="button" class="c-button c-button--full c-button--white" title="휴대폰인증하기" data-dialog-trigger="#divCertifySms">휴대폰 인증하기</button>
                </div>
            </div>
            <div class="data-sharing-notice">

            </div>
        </div>

        <!-- 휴대폰 인증 팝업 -->
        <div class="c-modal" id="divCertifySms" role="dialog" tabindex="-1" aria-describedby="authPhoneTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="authPhoneTitle">휴대폰 인증</h1>
                        <button class="c-button" data-dialog-close="">
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <input type="hidden" name="ncType" id="ncType" value="0"/>
                        <%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
                        <div class="c-notice-wrap">
                            <hr>
                            <h5 class="c-notice__title">
                                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                <span>알려드립니다</span>
                            </h5>
                            <ul class="c-notice__list">
                                <li>SMS(단문메시지)는 시스템 사정에 따라 다소 지연될 수 있습니다.</li>
                                <li>인증번호는 1899-5000로 발송되오니 해당 번호를 단말기 스팸 설정 여부 및 스팸편지함을 확인 해 주세요.</li>
                            </ul>
                        </div>
                        <div class="btn-usercheck">
                            <button type="button" class="c-button is-disabled" href="javascript:void(0);"  id="btnOk"  onclick="btn_ok();" >가입정보 확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>




    </t:putAttribute>
</t:insertDefinition>