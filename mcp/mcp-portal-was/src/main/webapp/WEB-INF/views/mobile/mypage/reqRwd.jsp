<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="titleAttr">자급제 보상 서비스 신청하기</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">

        <style>
            .ratingInfo th, .ratingInfo td{font-size: 0.875rem !important;}
            .tb-border{border-top:  0.01rem solid #e8e8e8;}
        </style>
        <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/mypage/reqRwd.js?version=23-11-24"></script>
        <script>
            history.pushState(null, null, "/m/mypage/reqRwd.do");
            window.onpopstate = function (event){
                history.go("/m/mypage/reqRwd.do");
            }
        </script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <%-- 본인인증 / 핸드폰인증 관련 --%>
        <input type="hidden" id="isAuth" name="isAuth"/>
        <input type="hidden" id="isAuthRwd" name="isAuthRwd"/>
        <%-- 자급제 보상서비스 관련--%>
        <form name="frm" id="frm" action="/m/mypage/reqRwd.do" method="post">
            <input type="hidden" name="ncn" value="" >
        </form>
        <input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">


        <!--main-content-->
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">자급제 보상 서비스 신청하기<button class="header-clone__gnb"></button>
                </h2>
            </div>

            <!--content body-->
            <div class="chgName-info">
                <div class="chgName-info__tit">
                    <h3>
                        kt M mobile에서 간편하게<br/>자급제 보상 서비스에 가입하세요!
                    </h3>
                    <p class="u-mt--8">
                        최신 스마트폰을 구매하고 18개월 뒤 kt M mobile 회선을 유지하면서 새 휴대폰을 구매할 경우 중고폰 매입 가격을 보장해주는 서비스 입니다.
                    </p>
                </div>

                <div class="u-mt--10">
                    <h3 class="c-form__title">자급제 보상 서비스 신청 주요 안내사항</h3>
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item u-co-gray">
                            kt M mobile “자급제 보상 서비스” 는 위니아에이드가 제공하는 서비스입니다.
                            최신 자급제 스마트폰을 구매한 고객이 kt M mobile 이동전화서비스 가입 및 본 서비스 가입 18개월 후 새 단말기를 구매하여 kt M mobile 이동전화를 유지하고
                            보상기간 중 사용하던 기존 단말[kt M mobile 자급제보상서비스 가입시 등록한 스마트폰]을 반납하는 경우, 위니아에이드가 개월차별 책정된 보상률(최대50%)에 따라 중고폰 매입가격을
                            현금으로 보장해 드립니다.
                        </li>
                        <li class="c-text-list__item u-co-gray">
                            kt M mobile “자급제 보상 서비스” 에 대한 자세한 내용 및 약관은
                            서비스 홈페이지(<a class="c-text--line u-co-sub-4" href="http://mobile.winiaaid.com/" title="위니아에이드(새창)" target="_blank">http://mobile.winiaaid.com/</a>)를 통해 확인 가능합니다.
                        </li>
                    </ul>
                </div>

                <div class="c-form__title">
                    <h3>자급제 보상 서비스 신청 절차 안내</h3>
                    <ul class="process-wrap u-mt--8">
                        <li class="process-list">
                            <strong><span class="process-list__step">1</span>고객님</strong>
                            <p>자급제 보상 서비스 신청</p>
                        </li>
                        <li class="process-list">
                            <strong><span class="process-list__step">2</span>위니아에이드</strong>
                            <p>서비스 가입 심사</p>
                        </li>

                        <li class="process-list--tree">
                            <div class="tree-list">
                                <strong class="tree-arrow"><i aria-hidden="true"></i></strong>
                            </div>
                            <div class="process-list__item">
                                <strong><span class="c-sticker--type3 u-fs-13">적격</span></strong>
                                <p>가입 승인 문자 발송 및 서비스 개시</p>
                            </div>
                            <div class="process-list__item">
                                <strong><span class="c-sticker--type2 u-fs-13">부적격</span></strong>
                                <p>가입 미승인 문자 발송 및 가입신청 자동 취소</p>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>

            <hr class="c-hr c-hr--type2">
            <h3 class="c-heading u-fw--regular fs-18">조회할 회선을 선택해 주세요.</h3>
            <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>
            <hr class="c-hr c-hr--type2">


            <!--impossible register rwd msgbox-->
            <div id="notRegisterForm" class="callhistory-info" style="display: none;">
                <div class="callhistory-info__list c-box c-box--type2" style="text-align:center;">
                    <h3><span class="u-co-red">고객님께서는 고객센터(114/무료)를 통해 자급제 보상 서비스 가입 가능 여부를 확인 부탁드립니다.</span></h3><br/></br>
                </div>
                </br>
            </div>
            <!--//impossible register rwd msgbox-->

            <!--register rwd form-->
            <div id="registerForm" style="display: none;">
                <!-- 기본형-->
                <div class="c-form">
                    <div class="c-form__title u-mt--0" id="inpNameCertiTitle">실명 및 본인인증</div>
                    <div class="c-form__input">
                        <input class="c-input" id="cstmrName" name="cstmrName" type="text"  placeholder="이름 입력" value=""  maxlength="60">
                        <label class="c-form__label" for="cstmrName">이름</label>
                    </div>
                </div>
                <div class="c-form-field" >
                    <div class="c-form__input-group">
                        <div class="c-form__input c-form__input-division">
                            <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" type="text" autocomplete="false" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');" >
                            <span>-</span>
                            <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" autocomplete="new-password" type="password" maxlength="7" value="" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
                            <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                        </div>
                    </div>
                </div>

                <!-- 본인인증 방법 선택 -->
                <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                    <jsp:param name="controlYn" value="N"/>
                    <jsp:param name="reqAuth" value="CNKAT"/>
                </jsp:include>

                <!-- register rwd form > after certification -->
                <div id="registerFormAfter" style="display: none;">
                    <hr class="c-hr c-hr--type2">
                    <h3 class="c-form__title">자급제 보상 서비스 선택</h3>

                    <div class="c-card-col c-card-col--3" id="rwdProdList">
                        <!-- 자급제 보상서비스 상품 리스트 표출 -->
                    </div>

                    <div class="c-form">
                        <h4 class="c-form__title">자급제 단말기 구입가<span class="c-form__sub">(단위: 원)</span></h4>
                        <div class="c-form__input" style="margin-top: 0;">
                            <input class="c-input numOnly" id="rwdBuyPric" name="rwdBuyPric" type="number" placeholder="구입가격 입력" maxlength="15">
                            <label class="c-form__label" for="rwdBuyPric">구입가격</label>
                        </div>
                    </div>
                    <!--//required sub info > price-->

                    <!--required sub info > imei-->
                    <div class="c-form">
                        <h4 class="c-form__title">단말기 정보값<span class="c-form__sub">(IMEI)</span></h4>
                        <div class="c-form__input" style="margin-top: 0;">
                            <input class="c-input" id="rwdImei1" name="rwdImei1" type="text" placeholder="IMEI1" readOnly>
                            <label class="c-form__label" for="rwdImei1">IMEI1</label>
                        </div>
                        <div class="c-form__input">
                            <input class="c-input" id="rwdImei2" name="rwdImei2" type="text" placeholder="IMEI2" readOnly>
                            <label class="c-form__label" for="rwdImei2">IMEI2</label>
                        </div>
                        <div class="c-box c-box--type1 u-pad--12 u-mt--8">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">자급제 보상 서비스 가입을 위해서는 IMEI 등록이 선행되어야 하며, IMEI 캡쳐화면 이미지 업로드가 필요합니다.</li>
                                <li class="c-text-list__item u-co-gray u-pb--8">이미지 업로드 시 자동으로 입력됩니다.<a class="c-text-link--bluegreen" data-dialog-trigger="#uploadguide-dialog">이미지 가이드 보기</a></li>
                            </ul>
                        </div>
                        <div class="c-button-wrap u-mt--8">
                            <label for="rwdImeiImage" class="c-button c-button--full c-button--mint">이미지 등록</label>
                            <input type="file" class="c-hidden" id="rwdImeiImage" accept="image/jpg, image/png, image/jpeg, image/tif, image/tiff, image/bmp" />
                        </div>
                    </div>
                    <!--//required sub info > imei-->

                    <!--file-->
                    <div class="c-form" id="fileContainer">
                        <span class="c-form__title" id="inpFileTitle">구매영수증 첨부하기<span class="u-co-gray">(필수)</span></span>

                        <div class="c-form__group" role="group" aria-labelledby="inpFileTitle">
                            <div class="upload-image">
                                <label class="upload-image__label">
                                    <input class="upload-image__hidden" name="rwdFile1" type="file" id="rwdFile1" accept=".jpg, .gif, .png" onchange="setThumb();">
                                    <i class="c-icon c-icon--photo" aria-hidden="true"></i>
                                    <span class="u-co-gray c-label__sub" id="attachLabel">첨부파일 0/1</span>
                                </label>
                                <div class="staged_img-list">
                                    <!-- 등록된 파일 영역 -->
                                </div>
                            </div>
                        </div>
                    </div>
                    <p class="c-bullet c-bullet--fyr u-co-gray">2MB 이내의 jpg, gif, png 형식으로 등록가능</p>
                    <!--//file-->

                    <!--agree terms-->
                    <div class="c-form">
                        <span class="c-form__title">약관동의</span>
                        <div class="c-agree u-mt--0">
                            <input class="c-checkbox" id="clauseRwdFlag" type="checkbox">
                            <label class="c-label" for="clauseRwdFlag">이용약관 및 개인정보 수집/이용에 모두 동의합니다.</label>


                            <!-- 약관 1 -->
                            <div class="c-agree__item">
                                <input id="clauseRwdRegFlag" code="${Constants.CLAUSE_RWD_REG_FLAG}"  docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _rwdAgree">
                                <label class="c-label" for="clauseRwdRegFlag">상품설명(이용료) 및 서비스 가입 동의<span class="u-co-gray">(필수)</span>
                                </label>
                                <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdRegFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_REG_FLAG}',0);" >
                                    <span class="c-hidden">상세보기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                </button>
                            </div>

                            <!--약관 2-->
                            <div class="c-agree__item">
                                <input id="clauseRwdPaymentFlag" code="${Constants.CLAUSE_RWD_PAYMENT_FLAG}" cdGroupId1=FORMREQUIRED  docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _rwdAgree">
                                <label class="c-label" for="clauseRwdPaymentFlag">만기 시 보장율/보장금액 지급기준 이행 동의<span class="u-co-gray">(필수)</span>
                                </label>
                                <button class="c-button c-button--xsm"  onclick="fnSetEventId('clauseRwdPaymentFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_PAYMENT_FLAG}',0);" >
                                    <span class="c-hidden">만기 시 보장율/보장금액 지급기준 이행 동의 상세 팝업 열기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                </button>
                            </div>

                            <!--약관 3-->
                            <div class="c-agree__item">
                                <input id="clauseRwdRatingFlag" code="S${Constants.CLAUSE_RWD_RATING_FLAG}" cdGroupId1=FORMREQUIRED  docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _rwdAgree">
                                <label class="c-label" for="clauseRwdRatingFlag">서비스 단말 반납 조건, 등급산정 및 평가 기준 동의<span class="u-co-gray">(필수)</span>
                                </label>
                                <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdRatingFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_RATING_FLAG}');" >
                                    <span class="c-hidden">상세보기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                </button>
                            </div>

                            <div class="c-agree__item">
                                <input id="clauseRwdPrivacyInfoFlag" code="S${Constants.CLAUSE_RWD_PRIVACY_INFO_FLAG}" cdGroupId1=FORMREQUIRED  docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _rwdAgree">
                                <label class="c-label" for="clauseRwdPrivacyInfoFlag">개인정보 수집ㆍ이용에 대한 동의 상세<span class="u-co-gray">(필수)</span>
                                </label>
                                <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdPrivacyInfoFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_PRIVACY_INFO_FLAG}');" >
                                    <span class="c-hidden">상세보기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                </button>
                            </div>



                            <div class="c-agree__item">
                                <input id="clauseRwdTrustFlag" code="${Constants.CLAUSE_RWD_TRUST_FLAG}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 hidden _rwdAgree">
                                <label class="c-label" for="clauseRwdTrustFlag">서비스 위탁사 개인정보 수집ㆍ이용ㆍ제공ㆍ위탁 동의서 <span class="u-co-gray">(필수)</span>
                                </label>
                                <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseRwdTrustFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_RWD_TRUST_FLAG}',0);" >
                                    <span class="c-hidden">상세보기</span>
                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    <!--//agree terms-->

                    <!--phone auth-->
                    <div class="c-button-wrap u-mt--40">
                        <button class="c-button c-button--full c-button--mint" id="btnSmsAuthRwd">휴대폰 인증</button>
                    </div>

                </div>   <!-- // registerFormAfter -->



                <!--//phone auth-->
                <div class="c-button-wrap u-mt--40">
                    <button class="c-button c-button--lg c-button--primary c-button--full is-disabled" onclick="applyRwd();" id="btnApplyRwd">자급제 보상 서비스 신청하기</button>
                </div>
            </div>
            <!--//register rwd form-->
        </div>
        <!--// main-content-->


        <!-- imei image register popup -->
        <div class="c-modal" id="imei-check-modal" role="dialog" aria-labelledby="imei-check-modal__title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">

                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="imei-check-modal__title">IMEI 사용 슬롯 확인</h2>
                        <button class="c-button" data-dialog-close id="popClose">
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>

                    <div class="c-modal__body">
                        <div class="esim-slot-box">
                            <ul class="esim-slot-box__list">
                                <li class="u-co-red">휴대폰 정보가 올바른지 반드시 확인하시기 바랍니다.</li>
                                <li>이미지에서 추출된 숫자가 정확하지 않으면 <span>다시 촬영해서 업로드</span> 시도 해 주시거나 고객님께서 <span>직접 해당 번호를 입력</span> 해 주십시오.</li>
                            </ul>
                        </div>
                        <div class="c-table">
                            <table>
                                <caption>구분, IMEI 정보</caption>
                                <colgroup>
                                    <col width="30%">
                                    <col>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th scope="col">구분</th>
                                    <th scope="col">IMEI</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>IMEI1</td>
                                    <td>
                                        <div class="c-form">
                                            <input type="text" class="c-input numOnly" id="imei1Txt" name="imei1Txt" value="" maxlength="15">
                                            <label class="c-form__label c-hidden" for="imei1Txt">imei1Txt</label>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>IMEI2</td>
                                    <td>
                                        <div class="c-form">
                                            <input type="text" class="c-input numOnly" id="imei2Txt" name="imei2Txt" value="" maxlength="15">
                                            <label class="c-form__label c-hidden" for="imei2Txt">imei2Txt</label>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <!-- imei 이미지 에러 메세지 효출 영역 -->
                        <div class="esim-slot-box__err" id="imeiErrTxt" style="display:none;"></div>
                    </div>

                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--gray" id="imeiReSetBtn" data-dialog-close>다시 등록하기</button>
                            <button class="c-button c-button--full c-button--primary" id="imeiSetBtn">확인</button>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <!-- // imei image register popup -->

        <!-- 가이드 -->
        <div class="c-modal" id="uploadguide-dialog" role="dialog" tabindex="-1" aria-describedby="#uploadguide-title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="uploadguide-title">이미지 업로드 가이드</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="esim-guide-box__pop">
                            <div class="esim-guide__list">
                                <span class="Number-label">1</span> 통화창에 *#06# 입력
                            </div>
                            <img src="/resources/images/mobile/esim/eSIM_guide_01.png" alt="1.통화창에 *#06#입력 가이드 이미지">
                        </div>
                        <div class="esim-guide-space__pop">
                            <i class="c-icon c-icon--triangle_bluegreen-bottom" aria-hidden="true"></i>
                        </div>
                        <div class="esim-guide-box__pop u-mt--0">
                            <div class="esim-guide__list">
                                <span class="Number-label">2</span> 이미지 캡쳐본 업로드
                            </div>
                            <img src="/resources/images/mobile/esim/eSIM_guide_02.png" alt="2.화면 캡쳐 정보의 가이드 이미지">
                        </div>
                        <div class="c-box c-box--type1 u-pa--16 u-mt--24">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray u-mt--0">이미지 업로드 시 각 항목이 자동으로 입력됩니다.</li>
                            </ul>
                        </div>
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- 가이드 끝-->

    </t:putAttribute>
</t:insertDefinition>