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

<t:insertDefinition name="layoutGnbDefaultTitle">

    <t:putAttribute name="titleAttr">유심칩 구매</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=${today}"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js?version=${today}"></script>
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=${today}"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js?version=${today}"></script>
        <script type="text/javascript" src="/resources/js/portal/appForm/reqSelfDlvry2.js?version=${today}"></script>
        <script src="${smartroScriptUrl}?version=${today}"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

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

        <!-- PC 연동의 경우에만 아래 승인폼이 필요합니다. (Mobile은 제외)-->
        <form id="approvalForm" name="approvalForm" method="post">
            <input type="hidden" name="Tid" />
            <input type="hidden" name="TrAuthKey" />
            <input type="hidden" name="MallReserved" />
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
            <div class="ly-page--title">
                <h2 class="c-page--title">유심구매</h2>
                <div class="c-stepper-wrap">
                    <div class="c-hidden">유심구매 총 2단계 중 현재 2단계(본인확인·약관동의)</div>
                    <ul class="c-stepper">
                        <li class="c-stepper__item"><span class="c-stepper__num">1</span></li>
                        <li class="c-stepper__item c-stepper__item--active">
                            <span class="c-stepper__num">2</span>
                            <span class="c-stepper__title">본인확인·약관동의</span>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="c-row c-row--lg">
                <h3 class="c-heading--fs22 c-heading--bold">본인확인·약관동의</h3>
                <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--48">실명 및 본인인증</h4>
                <hr class="c-hr c-hr--title">

                <!--remove autocomplete-->
                <input style="display:none" aria-hidden="true">
                <input type="password" style="display:none" aria-hidden="true">
                <!--End remove autocomplete-->


                <div class="c-form-wrap">
                    <div class="c-form-group" role="group" aira-labelledby="inpNameCertiTitle">
                        <div class="c-form-row c-col2 u-mt--0">
                            <div class="c-form-field">
                                <div class="c-form__input">
                                    <input class="c-input" name="cstmrName" id="cstmrName" type="text" placeholder="이름 입력" maxlength="20" autocomplete="off" onkeyup="certInit();">
                                    <label class="c-form__label" for="cstmrName">이름</label>
                                </div>
                            </div>


                            <div class="c-form-field">
                                <div class="c-form__input-group" id="rrnDiv">
                                    <div class="c-form__input c-form__input-division">
                                        <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn" autocomplete="false" type="text" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');certInit();">
                                        <span>-</span>
                                        <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn" autocomplete="new-password" type="password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" onkeyup="certInit();">
                                        <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 본인인증 방법 선택 -->
                <jsp:include page="/WEB-INF/views/portal/popup/niceAuthForm.jsp">
                    <jsp:param name="controlYn" value="N"/>
                    <jsp:param name="reqAuth" value="CNKATX"/>
                </jsp:include>

                <div class="c-form-wrap u-mt--40">
                    <span class="c-form__title">약관동의</span>
                    <div class="c-agree c-agree--type2">
                        <div class="c-agree__item">
                            <input class="c-checkbox" id="checkAll" type="checkbox" name="chkAgree">
                            <label class="c-label" for="checkAll">개인정보 수집/이용 항목에 모두 동의합니다.</label>
                        </div>
                        <div class="c-agree__inside">
                            <c:set var="termsFormList" value="${nmcp:getCodeList('TERMSELF')}" />
                            <c:forEach var="item" items="${termsFormList}" varStatus="status">
                                    <div class="c-chk-wrap">
                                        <button class="c-button c-button--xsm" onclick="viewTerms('terms${status.index}', 'cdGroupId1=TERMSELF&cdGroupId2=${item.dtlCd}');">
                                            <span class="c-hidden">${item.dtlCdNm}<c:out value="${item.expnsnStrVal1 eq 'Y' ? '(필수)' : '(선택)'}"/> 상세 팝업 열기</span>
                                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                        </button>
                                        <input class="c-checkbox c-checkbox--type2" id="terms${status.index}" name="terms" data-dtl-cd="${item.dtlCd}" data-mand-yn="${item.expnsnStrVal1}" href="javascript:void(0);" onclick="setChkbox()" type="checkbox">
                                        <label class="c-label" for="terms${status.index}">${item.dtlCdNm}<span class="u-co-gray"><c:out value="${item.expnsnStrVal1 eq 'Y' ? '(필수)' : '(선택)'}"/></span></label>
                                    </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="c-button-wrap u-mt--56">
                    <!-- 비활성화 시 .is-disabled 클래스 추가-->
                    <a class="c-button c-button--lg c-button--gray u-width--220" href="/appForm/reqSelfDlvry.do">이전</a>
                    <button class="c-button c-button--lg c-button--primary u-width--220 is-disabled" onclick="btnSave();" id="saveBtn">결제 후 신청완료</button>
                </div>
            </div>
            <div class="c-button-wrap c-button-wrap--b-floating is-active" id="bs_floating_button" data-floating-bs-trigger="#bsSample2">
                <button class="c-button c-button--bs-openner">
                    <div class="bs-opener_content">
                        <div class="left-area start-flex">
                            <img class="usim-img" src="/resources/images/portal/content/img_usim@2x.png" alt="">
                            <p>${reqSelfDlvry.prodNm} <br> <span class="c-text c-text--fs16 u-co-gray-7">${reqSelfDlvry.reqBuyQnty}개</span></p>
                        </div>
                        <div class="right-area u-co-red">
                            <span class="c-text c-text--fs14">총 결제금액</span>
                            <span class="c-text c-text--fs32 c-text--bold u-ml--12"><fmt:formatNumber value="${reqSelfDlvry.usimAmt}" pattern="#,###"/></span>
                            <span class="c-text c-text--fs18 c-text--bold">원</span>
                        </div>
                    </div>
                </button>
            </div>
            <div class="c-bs-compare" id="bottom_sheet">
                <button class="c-bs-compare__openner" id="bs_acc_btn" type="button">
                    <span class="sr-only">비교함 상세 열기|비교함 상세 닫기</span>
                </button>
                <div class="c-bs-compare__container">
                    <div class="c-bs-compare__inner">
                        <div class="c-bs-compare__preview" aria-hidden="false">
                            <div class="u-h--100 c-flex c-flex--jfy-between">
                                <div class="c-flex u-ta-left">
                                    <img class="usim-img" src="/resources/images/portal/content/img_usim@2x.png" alt="">
                                    <div class="inner-box">
                                        <p class="c-text c-text--fs18 u-fw--medium">${reqSelfDlvry.prodNm}</p>
                                        <p class="c-text c-text--fs16 u-co-gray">${reqSelfDlvry.reqBuyQnty}개</p>
                                    </div>
                                </div>
                                <div class="fee-compare-wrap">
                                    <div class="fee-compare-wrap__item">
                                        <div class="u-co-red">
                                            <span class="c-text c-text--fs14">총 결제금액</span>
                                            <span class="c-text c-text--fs32 c-text--bold u-ml--12"><fmt:formatNumber value="${reqSelfDlvry.usimAmt}" pattern="#,###"/></span>
                                            <span class="c-text c-text--fs18 c-text--bold">원</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="c-bs-compare__detail" id="acc_content_1" aria-hidden="true">
                            <div class="c-row c-row--lg u-pt--32 u-pb--32">
                                <h5 class="c-heading c-heading--fs20">
                                    <b>결제상세</b>
                                </h5>
                                <p class="c-text c-text--fs17 u-mt--12 u-co-gray">결제상세 내용을 확인하세요.</p>
                                <hr class="c-hr c-hr--title">
                                <div class="c-charge c-charge--type1">
                                    <div class="c-charge__panel-left">
                                        <dl class="c-charge__item">
                                            <dt class="u-co-gray-9">상품명</dt>
                                            <dd class="u-fw--medium">${reqSelfDlvry.prodNm}</dd>
                                        </dl>
                                        <dl class="c-charge__item">
                                            <dt class="u-co-gray-9">금액</dt>
                                            <dd class="u-fw--medium"><fmt:formatNumber value="${reqSelfDlvry.usimUcost}" pattern="#,###"/> 원</dd>
                                        </dl>
                                        <dl class="c-charge__item">
                                            <dt class="u-co-gray-9">수량</dt>
                                            <dd class="u-fw--medium">${reqSelfDlvry.reqBuyQnty} 개</dd>
                                        </dl>
                                    </div>
                                    <div class="c-charge__panel-right">
                                        <dl class="c-charge__item">
                                            <dt>
                                                <b class="u-fs-16">총 결제금액</b>
                                            </dt>
                                            <dd class="u-co-red">
                                                <b class="u-fs-26"><fmt:formatNumber value="${reqSelfDlvry.usimAmt}" pattern="#,###"/></b>원
                                            </dd>
                                        </dl>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="c-modal c-modal--xlg" id="account-check-modal" role="dialog" aria-labelledby="#account-check-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="account-check-modal__title">계좌인증</h2>
                        <div class="c-stepper-wrap divBankAut">
                            <div class="c-hidden divBankAut1">유심구매 총 2단계 중 현재 2단계(계좌정보 입력)</div>
                            <ul class="c-stepper divBankAut1">
                                <li class="c-stepper__item c-stepper__item--active">
                                    <span class="c-stepper__num">1</span> <span class="c-stepper__title">계좌정보 입력</span></li>
                                <li class="c-stepper__item"><span class="c-stepper__num">2</span></li>
                            </ul>
                            <div class="c-hidden divBankAut2" style="display:none;">유심구매 총 2단계 중 현재 2단계(인증번호 입력)</div>
                            <ul class="c-stepper divBankAut2" style="display:none;">
                                <li class="c-stepper__item"><span class="c-stepper__num">1</span></li>
                                <li class="c-stepper__item c-stepper__item--active">
                                    <span class="c-stepper__num">2</span> <span class="c-stepper__title">인증번호 입력</span>
                                </li>
                            </ul>
                        </div>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>

                    <div class="c-modal__body divBankAut1">
                        <h3 class="c-heading c-heading--fs20" id="AccountInfoHead">계좌정보 입력</h3>
                        <p class="c-text c-text--fs17 u-co-gray u-mt--12">
                            본인인증에 필요한 정보를 입력해 주세요. <br>PASS 인증을 받은 실명과 계좌 명의자가 동일해야 인증이 가능합니다.
                        </p>
                        <div class="c-form-wrap u-mt--48 u-mb--56">
                            <div class="c-form-group" role="group" aria-labelledby="AccountInfoHead">
                                <div class="c-form-row c-col2">
                                    <div class="c-form-field">
                                        <div class="c-form__select u-mt--0">
                                            <c:set var="bankList" value="${nmcp:getCodeList(Constants.BANK_CD)}" />
                                            <select class="c-select" id="reqBankAut" onchange="accountChk();">
                                                <option value="">전체</option>
                                                <c:forEach var="item" items="${bankList}" varStatus="status">
                                                    <option value="${item.dtlCd}" label="${item.dtlCdNm}">${item.dtlCdNm }</option>
                                                </c:forEach>
                                            </select>
                                            <label class="c-form__label" for="reqBankAut">은행/증권사</label>
                                        </div>
                                    </div>
                                    <div class="c-form-field">
                                        <div class="c-form__input u-mt--0">
                                            <input class="c-input onlyNum" id="reqAccountAut" maxlength="30" type="number" placeholder="'-'없이 입력" onkeyup="accountChk();">
                                            <label class="c-form__label" for="reqAccountAut">계좌번호</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="c-form-row c-col2 u-width--460">
                                    <div class="c-form-field">
                                        <div class="c-form__input" id="accountName">
                                            <input class="c-input" id="cstmrNameTemp" type="text" placeholder="이름 입력" readonly="readonly">
                                            <label class="c-form__label" for="cstmrNameTemp">이름</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-modal__footer u-flex-center divBankAut1" id="divBankAutFooter">
                        <div class="u-box--w460 c-button-wrap divBankAut1">
                            <button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
                            <button class="c-button c-button--full c-button--primary is-disabled" id="btnAutAccount">인증요청</button>
                        </div>
                    </div>

                    <div class="c-modal__body divBankAut2" style="display:none;" id="">
                        <h3 class="c-heading c-heading--fs20" id="certiInfoHead">인증번호 입력</h3>
                        <p class="c-text c-text--fs17 u-co-gray u-mt--12">
                            입력하신 계좌로 1원을 보내드렸습니다. <br>계좌 거래내역에서 1원의 입금자로 표시된 숫자 6자리를 입력해
                            주시기 바랍니다.
                        </p>
                        <div class="c-form-wrap u-mt--48">
                            <div class="c-form-group" role="group" aira-labelledby="certiInfoHead">
                                <div class="u-box--w460 u-margin-auto">
                                    <div class="c-form">
                                        <div class="c-img-wrap">
                                            <img src="/resources/images/portal/content/img_account_certification.png" alt="계좌 예시 이미지">
                                        </div>
                                        <!-- 인증 성공 시 .has-safe, 인증 실패 시 .has-error 추가-->
                                        <div class="c-form-field">
                                            <div class="c-form__input">
                                                <input class="c-input" id="textOpt" type="text" placeholder="숫자 6자리" maxlength="6">
                                                <label class="c-form__label" for="textOpt">인증번호</label>
                                            </div>
                                            <!-- 에러 설명 필요할때 -->
                                            <!-- p.c-bullet.c-bullet--dot.u-co-gray 설명                 -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-modal__footer u-flex-center divBankAut2" style="display:none !important;" id="divBankAut2Footer">
                        <div class="u-box--w460 c-button-wrap divBankAut2" style="display:none;">
                            <button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
                            <button class="c-button c-button--full c-button--primary" id="btnCheckAccountOtpConfirm">인증요청</button>
                        </div>
                    </div>

                    <div class="c-modal__body divBankAut3" style="display:none;">
                        <div class="complete u-mt--80 u-ta-center">
                            <div class="c-icon c-icon--complete" aria-hidden="true"></div>
                            <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
                                <b>인증 완료</b>
                            </h3>
                            <p class="c-text c-text--fs20 u-co-gray u-mt--16">
                                계좌 인증이 완료되었습니다. <br>확인 버튼을 누른 뒤 신청서를 계속 작성해주시기 바랍니다.
                            </p>
                        </div>
                    </div>
                    <div class="c-modal__footer divBankAut3" style="display:none;">
                        <div class="c-button-wrap u-mt--64">
                            <button class="c-button c-button--lg c-button--primary c-button--w460" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
