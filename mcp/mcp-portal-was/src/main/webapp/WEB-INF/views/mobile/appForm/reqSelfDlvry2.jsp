<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefaultTitle">
    <t:putAttribute name="titleAttr">유심칩 구매 </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=${today}"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js?version=${today}"></script>
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=${today}"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js?version=${today}"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/reqSelfDlvry2.js?version=${today}"></script>
        <script src="${smartroScriptUrl}?version=${today}"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">

        <form id="targetForm" name="targetForm" method="post" action="/appForm/selfDlvryComplete.do">
            <input type="hidden" id="selfDlvryIdx" name="selfDlvryIdx"/>
        </form>
        <form id="tranMgr" name="tranMgr" method="post">
            <!-- 각 값들을 가맹점에 맞게 설정해 주세요. -->
            <input type="hidden" name="PayMethod"	id="PayMethod"	value="CARD"/>
            <input type="hidden" name="GoodsCnt"	id="GoodsCnt"	value=""/>
            <input type="hidden" name="GoodsName"	id="GoodsName"	value=""/> <!-- 거래 상품명 -->
            <input type="hidden" name="Amt"			id="Amt"		value=""/> <!-- 설정한 Amt -->
            <input type="hidden" name="Moid"		id="Moid"		value=""/> <!-- 주문번호 특수문자 포함 불가 -->
            <input type="hidden" name="Mid"			id="Mid"		value=""/> <!-- 설정한 Mid -->
            <input type="hidden" name="ReturnUrl"	id="ReturnUrl"	value=""> <!-- 가맹점 ReturnUrl 설정 -->
            <input type="hidden" name="StopUrl"		id="StopUrl"	value=""/> <!-- 결제중단 URL -->
            <input type="hidden" name="BuyerName"	id="BuyerName"	value=""/> <!-- 구매자명 -->
            <input type="hidden" name="BuyerTel"	id="BuyerTel"	value=""/> <!-- 전화번호 -->
            <input type="hidden" name="BuyerEmail"	id="BuyerEmail"	value=""/> <!-- 이메일 -->
            <input type="hidden" name="MallIp"		id="MallIp"		value=""/>
            <input type="hidden" name="VbankExpDate"id="VbankExpDate" value=""/> <!-- 가상계좌 이용 시 필수 -->
            <input type="hidden" name="EncryptData"	id="EncryptData" value=""/> <!-- 위/변조방지 HASH 데이터 -->
            <input type="hidden" name="GoodsCl"		id="GoodsCl"	value="1"/> <!-- 0: 컨텐츠, 1: 실물(가맹점 설정에 따라 셋팅, 휴대폰 결제 시 필수)-->
            <input type="hidden" name="EdiDate"		id="EdiDate"	value=""/> <!-- 설정한 EdiDate -->
            <input type="hidden" name="TaxAmt"		id="TaxAmt"		value=""/> <!-- 과세 : 부가세 직접계산 가맹점 필수, 숫자만 가능, 문장부호 제외  -->
            <input type="hidden" name="TaxFreeAmt"	id="TaxFreeAmt"	value=""/> <!-- 비과세 : 부가세 직접계산 가맹점 필수,숫자만 가능, 문장부호 제외 -->
            <input type="hidden" name="VatAmt"		id="VatAmt"		value=""/> <!-- 부가세 : 부가세 직접계산 가맹점 필수,숫자만 가능, 문장부호 제외 -->
            <input type="hidden" name="MallReserved"id="MallReserved"	value=""/> <!-- 상점예비필드 -->

        </form>

        <input type="hidden" id="mode" name="mode"/>
        <input type="hidden" id="isAuth" name="isAuth"/>
        <input type="hidden" id="isSmsAuth" name="isSmsAuth"/>
        <input type="hidden" id="isPassAuth" name="isPassAuth"/>
        <input type="hidden" id="operType" name="operType" value=""/>
        <input type="hidden" id="onOffType" name="onOffType" value=""/>

        <input type="hidden" id="requestNo" name="requestNo"/>
        <input type="hidden" id="resUniqId" name="resUniqId"/>
        <input type="hidden" id="selfDlvryIdx" name="selfDlvryIdx" value=""/>


        <input type="hidden" id="userEmail" name="userEmail" value="<c:out value="${not empty userSession ? userSession.email : 'guest@ktmmobile.com'}"/>" />

        <input type="hidden" name="usimProdId" id="usimProdId" value="${reqSelfDlvry.usimProdId}"/>
        <input type="hidden" name="dlvryName" id="dlvryName" value="${reqSelfDlvry.dlvryName}"/>
        <input type="hidden" name="dlvryTelFn" id="dlvryTelFn" value="${reqSelfDlvry.dlvryTelFn}"/>
        <input type="hidden" name="dlvryTelMn" id="dlvryTelMn" value="${reqSelfDlvry.dlvryTelMn}"/>
        <input type="hidden" name="dlvryTelRn" id="dlvryTelRn" value="${reqSelfDlvry.dlvryTelRn}"/>
        <input type="hidden" name="dlvryPost" id="dlvryPost" value="${reqSelfDlvry.dlvryPost}"/>
        <input type="hidden" name="dlvryAddr" id="dlvryAddr" value="${reqSelfDlvry.dlvryAddr}"/>
        <input type="hidden" name="dlvryAddrDtl" id="dlvryAddrDtl" value="${reqSelfDlvry.dlvryAddrDtl}"/>
        <input type="hidden" name="dlvryMemo" id="dlvryMemo" value="${reqSelfDlvry.dlvryMemo}"/>
        <input type="hidden" name="bizOrgCd" id="bizOrgCd" value="${reqSelfDlvry.bizOrgCd}"/>
        <input type="hidden" name="dlvryKind" id="dlvryKind" value="${reqSelfDlvry.dlvryKind}"/>
        <input type="hidden" name="usimAmt" id="usimAmt" value="${reqSelfDlvry.usimAmt}"/>
        <input type="hidden" name="usimUcost" id="usimUcost" value="${reqSelfDlvry.usimUcost}"/>
        <input type="hidden" name="acceptTime" id="acceptTime" value="${reqSelfDlvry.acceptTime}"/>
        <input type="hidden" name="entY" id="entY" value="${reqSelfDlvry.entY}"/>
        <input type="hidden" name="entX" id="entX" value="${reqSelfDlvry.entX}"/>
        <input type="hidden" name="dlvryJibunAddr" id="dlvryJibunAddr" value="${reqSelfDlvry.dlvryJibunAddr}"/>
        <input type="hidden" name="dlvryJibunAddrDtl" id="dlvryJibunAddrDtl" value="${reqSelfDlvry.dlvryJibunAddrDtl}"/>
        <input type="hidden" name="homePw" id="homePw" value="${reqSelfDlvry.homePw}"/>
        <input type="hidden" name="exitTitle" id="exitTitle" value="${reqSelfDlvry.exitTitle}"/>
        <input type="hidden" name="reqBuyQnty" id="reqBuyQnty" value="${reqSelfDlvry.reqBuyQnty}"/>

        <input type="hidden" name="smartroReturnUrl" id="smartroReturnUrl" value="${smartroReturnUrl}">
        <input type="hidden" name="completeUrl" id="completeUrl" value="${directUsimCompleteUrl}">

        <input type="hidden" name="targetTerms" id="targetTerms"/>

        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    유심구매
                    <button class="header-clone__gnb"></button>
                </h2>
                <div class="c-indicator">
                    <span style="width: 100%"></span>
                </div>
            </div>
            <div class="u-pb--150">
                <h3 class="c-heading c-heading--type1 u-mt--48">본인확인 및 약관동의</h3>

                <h4 class="c-heading c-heading--type5">실명 및 본인인증</h4>
                <hr class="c-hr c-hr--type2">
                <div class="c-form">
                    <div class="c-form__input">
                        <input class="c-input" name="cstmrName" id="cstmrName" type="text" placeholder="이름 입력" maxlength="20" autocomplete="off" onkeyup="certInit();">
                        <label class="c-form__label" for="cstmrName">이름</label>
                    </div>
                    <div class="c-form__input-group" id="rrnDiv">
                        <div class="c-form__input c-form__input-division">
                            <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" type="tel" name="cstmrNativeRrn" maxlength="6" pattern="[0-9]*" placeholder="주민번호 앞자리" value="" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');certInit();"> <span>-</span>
                            <!-- <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" type="password" name="cstmrNativeRrn" maxlength="7" pattern="[0-9]*" placeholder="주민번호 뒷자리" value="" onkeyup="certInit();"> -->
                            <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" type="number" inputmode="numeric" name="cstmrNativeRrn" maxlength="7" pattern="[0-9]*" style="-webkit-text-security: disc;" placeholder="주민번호 뒷자리" value="" onkeyup="certInit();">
                            <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                        </div>
                    </div>
                </div>

                <!-- 본인인증 방법 선택 -->
                <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                    <jsp:param name="controlYn" value="N"/>
                    <jsp:param name="reqAuth" value="CNKAT"/>
                </jsp:include>

                <div class="c-form">
                    <!-- [2021-11-29] 간격조정 유틸클래스 추가-->
                    <span class="c-form__title u-mb--0">약관동의</span>
                    <div class="c-agree">
                        <input class="c-checkbox" id="checkAll" type="checkbox" name="chkAgree">
                        <label class="c-label" for="checkAll">개인정보 수집/이용 항목에 모두 동의합니다.</label>
                        <c:set var="termsFormList" value="${nmcp:getCodeList('TERMSELF')}" />
                        <c:forEach var="item" items="${termsFormList}" varStatus="status">
                             <div class="c-agree__item">
                                 <input class="c-checkbox c-checkbox--type2" id="terms${status.index}" name="terms" data-dtl-cd="${item.dtlCd}" type="checkbox" data-mand-yn="${item.expnsnStrVal1}" onclick="setChkbox()" />
                                 <label class="c-label" for="terms${status.index}">${item.dtlCdNm}
                                     <span class="u-co-gray"><c:out value="${item.expnsnStrVal1 eq 'Y' ? '(필수)' : '(선택)'}"/></span>
                                 </label>
                                 <button class="c-button c-button--xsm" type="button" onclick="viewTerms('terms${status.index}', 'cdGroupId1=TERMSELF&cdGroupId2=${item.dtlCd}');">
                                     <span class="c-hidden">상세보기</span>
                                     <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                 </button>
                             </div>
                        </c:forEach>
                    </div>
                </div>
                <!-- [2021-11-29] 버튼 클래스 추가(디자인 변경 적용) 시작=====-->
                <div class="c-button-wrap">
                    <a class="c-button c-button--gray c-button--lg u-width-120" href="/m/appForm/reqSelfDlvry.do">이전</a>
                    <a class="c-button c-button--full c-button--primary is-disabled" href="javascript:void(0);" onclick="btnSave();" id="saveBtn">결제 후 신청완료</a>
                </div>
                <!-- [$2021-11-29] 버튼 클래스 추가(디자인 변경 적용) 끝=====-->
            </div>
            <!-- 하단 bottom-sheet trigger  영역 시작 (bottom-trigger 작동 시 is-active class 부여해주세요.)-->
            <div class="c-button-wrap c-button-wrap--b-floating is-active" id="bs_floating_button" data-floating-bs-trigger="#bsSample2">
                <button class="c-button c-button--bs-openner">
                    <p class="c-text c-text--type3 u-fw--regular">결제금액</p>
                    <div>
                        <span class="fs-26"><fmt:formatNumber value="${reqSelfDlvry.usimAmt}" pattern="#,###"/></span> <span class="fs-14 fw-normal">원</span>
                    </div>
                    <i class="c-icon c-icon--arrow-up" aria-hidden="true"></i>
                </button>
            </div>
        </div>

        <div class="c-modal c-modal--bs" id="bsSample2" role="dialog" tabindex="-1" aria-describedby="#bsSampleDesc2" aria-labelledby="#bsSampletitle2">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="bsSampletitle2">
                            <span class="c-hidden">결제 상세</span>
                        </h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--arrow-down" aria-hidden="true"></i>
                            <span class="c-hidden">바텀시트 닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body u-pt--0" id="bsSampleDesc2">
                        <h3 class="c-heading c-heading--type1 u-mt--0 u-mb--0">결제상세</h3>
                        <p class="c-text c-text--type3 u-mt--12">결제상세 내용을 확인하세요</p>
                        <hr class="c-hr c-hr--type3 u-mt--24">
                        <div class="c-addition-wrap">
                            <dl class="c-addition c-addition--type2">
                                <dt>상품명</dt>
                                <dd class="u-ta-right u-fw--medium">${reqSelfDlvry.prodNm}</dd>
                            </dl>
                            <dl class="c-addition c-addition--type2">
                                <dt>금액</dt>
                                <dd class="u-ta-right u-fw--medium"><fmt:formatNumber value="${reqSelfDlvry.usimUcost}" pattern="#,###"/> 원</dd>
                            </dl>
                            <dl class="c-addition c-addition--type2">
                                <dt>수량</dt>
                                <dd class="u-ta-right u-fw--medium">${reqSelfDlvry.reqBuyQnty}개</dd>
                            </dl>
                            <hr class="c-hr c-hr--type2">
                            <dl class="c-addition c-addition--type1 c-addition--sum">
                                <dt>총 결제금액</dt>
                                <dd class="u-ta-right">
                                    <b><fmt:formatNumber value="${reqSelfDlvry.usimAmt}" pattern="#,###"/></b>원
                                </dd>
                            </dl>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="c-modal" id="accout-inp-dialog" role="dialog2" tabindex="-1" aria-describedby="#accout-inp-title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="accout-inp-title">계좌인증</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-indicator">
                        <span style="width: 33.33%"></span>
                    </div>
                    <div class="c-modal__body" id="divBankAut1" style="display:block;">
                        <h3 class="c-heading c-heading--type1">계좌정보 입력</h3>
                        <p class="c-text c-text--type2 u-co-gray">본인인증에 필요한 정보를 입력해 주세요. PASS 인증을 받은 실명과 계좌 명의자가 동일해야 인증이 가능합니다.</p>
                        <div class="c-form u-mt--0">
                            <span class="c-form__title">은행/증권사</span>
                            <div class="c-form__group" role="group" aria-labeledby="inpBankNum">
                                <div class="c-form__select">
                                    <c:set var="bankList" value="${nmcp:getCodeList(Constants.BANK_CD)}" />
                                    <select class="c-select" id="reqBankAut" onchange="accountChk();">
                                        <option value="">전체</option>
                                        <c:forEach var="item" items="${bankList}" varStatus="status">
                                            <option value="${item.dtlCd}">${item.dtlCdNm }</option>
                                        </c:forEach>
                                    </select>
                                    <label class="c-form__label">은행명</label>
                                </div>
                                <div class="c-form__input">
                                    <input class="c-input onlyNum" id="reqAccountAut" maxlength="30" type="tel" name="" pattern="[0-9]*" placeholder="'-'없이 입력" onkeyup="accountChk();">
                                    <label class="c-form__label" for="reqAccountAut">계좌번호</label>
                                </div>
                                <div class="c-form__input" id="accountName">
                                    <input class="c-input" id="cstmrNameTemp" type="text" placeholder="" readonly="readonly">
                                    <label class="c-form__label" for="cstmrNameTemp">이름</label>
                                </div>
                            </div>
                        </div>
                        <!-- 비활성화시 버튼에 .is-disabled 클래스 추가-->
                        <div class="c-button-wrap">
                            <button class="c-button c-button--gray c-button--lg u-width-120" data-dialog-close>취소</button>
                            <button class="c-button c-button--full c-button--primary is-disabled" id="btnAutAccount">인증요청</button>
                        </div>
                    </div>
                    <div class="c-modal__body" id="divBankAut2" style="display:none;">
                        <h3 class="c-heading c-heading--type1">인증번호 입력</h3>
                        <p class="c-text c-text--type2 u-co-gray">
                            입력하신 계좌로 1원을 보내드렸습니다. <br>계좌 거래내역에서 1원의 입금자로 표시된 <br>숫자 6자리를 입력해 주시기 바랍니다.
                        </p>
                        <div class="c-form u-mt--32">
                            <!--[2022-01-26] 클래스 및 이미지 파일명 변경-->
                            <div class="c-box u-mb--24">
                                <img class="center-img" src="/resources/images/mobile/content/img_account_certification.png" alt="">
                            </div>
                            <div class="c-form__input">
                                <input class="c-input" id="textOpt" maxlength="6" type="tel" name="" pattern="[0-9]*" placeholder="입금자 명에 표시된 숫자 6자리 입력">
                                <label class="c-form__label" for="inpBankNum">인증번호</label>
                            </div>
                        </div>
                        <!-- 비활성화시 버튼에 .is-disabled 클래스 추가-->
                        <div class="c-button-wrap">
                            <!--[2021-12-02] 유틸클래스명 변경 -->
                            <button class="c-button c-button--gray c-button--lg u-width--120" data-dialog-close>취소</button>
                            <button class="c-button c-button--full c-button--primary" id="btnCheckAccountOtpConfirm">인증하기</button>
                        </div>
                    </div>
                    <div class="c-modal__body" id="divBankAut3" style="display:none;">
                        <!-- [2022-02-14] 디자인 수정 반영(마크업 수정) 시작-->
                        <div class="complete">
                            <div class="c-icon c-icon--complete" aria-hidden="true">
                                <span></span>
                            </div>
                            <p class="complete__heading">
                                <b> 계좌 인증이 <br>완료되었습니다.</b>
                            </p>
                            <p class="complete__text">
                                확인 버튼을 누른 뒤 신청서를 계속 <br>작성해주시기 바랍니다.
                            </p>
                        </div>
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                        </div>
                        <!-- [$2022-02-14] 디자인 수정 반영(마크업 수정) 끝-->
                    </div>
                </div>
            </div>
        </div>
        <a href="javascript:void(0);" id="modalDirectBtn" data-dialog-trigger="#modalDirect" ></a>
        <div class="c-modal" id="modalDirect" role="dialog" tabindex="-1" aria-describedby="#modalDirectNotTime">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header u-hide">
                        <h1 class="c-modal__title" id="modalDirectNotTime">바로배송 가능시간이 아닙니다.</h1>
                    </div>
                    <div class="c-modal__body u-ta-center">
                        <div class="c-alert--time">
                            <p class="c-text-label c-text-label--type2 c-text-label--red">바로배송 가능시간</p>
                            <p>10:00 ~ 2200</p>
                        </div>
                        <p class="c-text c-text--type3 u-mt--16">
                            바로배송 가능시간이 아닙니다. <br>택배를 이용해주세요.
                        </p>
                    </div>
                    <div class="c-modal__footer u-pt--0 u-pb--24">
                        <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>