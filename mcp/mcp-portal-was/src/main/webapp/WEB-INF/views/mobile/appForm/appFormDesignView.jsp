<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />
<c:set var="jsver" value="${nmcp:getJsver()}" />
<c:set var="eSimIsOpenObj" value="${nmcp:getCodeNmDto('Constant','eSimIsOpen')}" />
<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="metaTagAttr">
        <meta property="fb:app_id" content="1694564987490429">
        <meta property="og:type" content="website" />
        <meta property="og:title" content="가입조건 선택 | kt M모바일 공식 다이렉트몰" id="meta_og_title" />
        <meta property="og:url" content="https://www.ktmmobile.com/appForm/appFormDesignView.do" id="meta_og_url" />
        <meta property="og:description" content="" id="meta_og_description" />
        <meta property="og:image" content="https://${header['host']}/ktMmobile_og.png"id="meta_og_image" />
        <meta name="keywords" content="${nmcp:getCodeDesc('MetaKeywords','01')}">
    </t:putAttribute>

    <t:putAttribute name="titleAttr">가입조건 선택</t:putAttribute>
    <t:putAttribute name="googleTagScript">
        <!-- Event snippet for 가입신청시작_MO conversion page -->
        <script>
            gtag('event', 'conversion', {'send_to': 'AW-862149999/qD77CK_jxLYZEO-6jZsD'});
        </script>
    </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/vendor/lottie.min.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/appFormDesignView.js?version=26.01.14"></script>
        <script type="text/javascript" src="/resources/js/mobile/sns.js"></script>
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/eSimFormDesignView.js?version=23-11-17"></script>
    </t:putAttribute>
    <t:putAttribute name="bottomScript">
        <!-- naver DA script  통계  전환페이지 설정 -->
        <script type="text/javascript">
            if(window.wcs){
                // (5) 결제완료 전환 이벤트 전송
                var _conv = {}; // 이벤트 정보를 담을 object 생성
                _conv.type = "lead";  // object에 구매(purchase) 이벤트 세팅
                wcs.trans(_conv); // 이벤트 정보를 담은 object를 서버에 전송
            }
        </script>
        <!-- naver DA script  통계  전환페이지 end -->
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <input type="hidden" id="platFormCd" value="${nmcp:getPlatFormCd()}"/>

            <input type="hidden" id="serverName" value="${serverName}"/>
            <input type="hidden" id="shareYn" value="${ProdCommendDto.shareYn}"/>
            <input type="hidden" id="requestKey" value="${ProdCommendDto.requestKey}"/>
            <input type="hidden" id="initPrdtId" value="${ProdCommendDto.prdtId}"/>
            <input type="hidden" id="initOperType" value="${ProdCommendDto.operType}"/>
            <input type="hidden" id="initAgrmTrm" value="${ProdCommendDto.agrmTrm}"/>
            <input type="hidden" id="initRateCd" value="${ProdCommendDto.rateCd}"/>
            <input type="hidden" id="initDataType" value="${ProdCommendDto.prdtSctnCd}"/>
            <input type="hidden" id="initOnOffType" value="${ProdCommendDto.onOffType}"/>
            <input type="hidden" id="initOrgnId" value="${ProdCommendDto.orgnId}"/>
            <input type="hidden" id="initIndcOdrg" value="${ProdCommendDto.indcOdrg}"/>
            <input type="hidden" id="initFirst" value="first"/>
            <input type="hidden" id="joinPrice" name="joinPrice" value="-">
            <input type="hidden" id="usimPrice" name="usimPrice" value="-">
            <input type="hidden" id="joinPriceIsPay" name="joinPriceIsPay" value="N">
            <input type="hidden" id="usimPriceIsPay" name="usimPriceIsPay" value="N">
            <input type="hidden" id="rdoBestRate" name="rdoBestRate" value="1">
            <input type="hidden" id="socCode" name="socCode"/>
            <input type="hidden" id="salePlcyCd" name="salePlcyCd"/>
            <input type="hidden" id="onOffType" name="onOffType" value="${ProdCommendDto.onOffType}"/>
            <input type="hidden" id="prodCtgId" name="prodCtgId" value="${phoneProdBas.prodCtgId}">
            <input type="hidden" id="fprodNm" value="${phoneProdBas.prodNm }"/>
            <input type="hidden" name="ftranPrice" id="ftranPrice" value="${nmcp:getFtranPrice()}" />
            <input type="hidden" id="prodId" value="${phoneProdBas.prodId}" />
            <input type="hidden" name="prdtSctnCd" id="prdtSctnCd" value="${phoneProdBas.prodCtgId}" />
            <input type="hidden" name="prodCtgType" id="prodCtgType" value="${phoneProdBas.prodCtgType}" />
            <input type="hidden" name="cardDcCd" id="cardDcCd" value="${phoneProdBas.cardDcCd}" />
            <input type="hidden" name="prdtIndCd" id="prdtIndCd" value="${prdtIndCd}" />
            <input type="hidden" name="uploadPhoneSrlNo" id="uploadPhoneSrlNo" value="${uploadPhoneSrlNo}" />
            <input type="hidden" name="radOpenSelf" id="radOpenSelf" value="${eSimIsOpenObj.dtlCdNm}" />
            <input type="hidden" name="radOpenOff" id="radOpenOff" value="${eSimIsOpenObj.expnsnStrVal1}" />
            <input type="hidden" name="ePhoneRstCd" id="ePhoneRstCd" value="${uploadEPhone.rstCd}" />
            <input type="hidden" name="ePhoneMoveTlcmIndCd" id="ePhoneMoveTlcmIndCd" value="${uploadEPhone.moveTlcmIndCd}" />
            <input type="hidden" name="ePhoneMoveCmncGnrtIndCd" id="ePhoneMoveCmncGnrtIndCd" value="${uploadEPhone.moveCmncGnrtIndCd}" />
            <input type="hidden" name="isEvntCdPrmtAuth" id="isEvntCdPrmtAuth" value="" />
            <input type="hidden" name="evntCdPrmtSoc" id="evntCdPrmtSoc" value="" />
            <input type="hidden" name="evntCdSeq" id="evntCdSeq" value="" />

            <div id="recommendRateforChk" style="display:none;">${phoneProdBas.recommendRate }</div>
            <div id="recommendRateforNoargmChk" style="display:none;">${phoneProdBas.recommendRateNoargm }</div>
            <div class="ly-page-sticky">
                <c:if test="${phoneProdBas.prodCtgType ne '03'}">
                    <h2 class="ly-page__head">가입조건 선택<button class="header-clone__gnb"></button></h2>
                </c:if>
                <c:if test="${phoneProdBas.prodCtgType eq '03'}">
                    <h2 class="ly-page__head">자급제 가입조건 선택<button class="header-clone__gnb"></button></h2>
                </c:if>
            </div>

            <div class="app-form-view-content">
                <div class="app-form-view-wrap">

                    <c:if test="${not empty phoneProdBas.prodId or phoneProdBas.prodCtgType eq '03'}">
                        <div class="app-form-view-group">
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3>상품정보</h3>
                                </div>
                                <div class="app-form-view__form">
                                    <div class="c-item c-item--type1 product-info-box">
                                        <strong class="c-item__title">${phoneProdBas.prodNm}</strong>
                                        <p class="c-item__sub u-mt--4">
                                            <c:forEach var="sntyItem" items="${phoneProdBas.phoneSntyBasDtoListY}" varStatus="status">
                                                <c:if test="${status.index eq 0}">
                                                    <span class="u-co-gray-7">${sntyItem.atribValNm2}</span>
                                                </c:if>
                                                <c:if test="${sntyItem.hndsetModelId == hndsetModelId }">
                                                    <span class="u-co-gray-7">${sntyItem.atribValNm1}</span>
                                                </c:if>
                                            </c:forEach>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${empty phoneProdBas.prodId or phoneProdBas.prodCtgType eq '03'}">
                        <div class="app-form-view-group">
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3 id="radUsimtType">고객유형</h3>
                                </div>
                                <div class="app-form-view__form">
                                   <div class="c-form">
                                        <div class="c-check-wrap">
                                              <div class="c-chk-wrap">
                                                <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType1" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NA}" desc="19세 이상 내국인" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_NA }" > checked </c:if> >
                                                <label class="c-label" for="cstmrType1">
                                                  <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                                                  <span>내국인</span>
                                                </label>
                                              </div>
                                              <div class="c-chk-wrap">
                                                <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType2" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NM}" desc="19세 미만 미성년자" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_NM }" > checked </c:if> <c:if test="${AppformReq.onOffType eq '7' }" > disabled </c:if> >
                                                <label class="c-label" for="cstmrType2">
                                                  <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                                                  <span class="app-form-view__lable">미성년자<br /><span class="u-fs-13">(19세 미만)</span></span>
                                                </label>
                                              </div>
                                              <div class="c-chk-wrap">
                                                <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType3" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_FN}" desc="외국인" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_FN }" > checked </c:if> <c:if test="${AppformReq.onOffType eq '7' }" > disabled </c:if>>
                                                <label class="c-label" for="cstmrType3">
                                                  <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                                                  <span class="app-form-view__lable">외국인<br /><span class="u-fs-13">(Foreigner)</span></span>
                                                </label>
                                              </div>
                                        </div>
                                    </div>
                                    <div class="c-box c-box--type1 _isLocal" style="display:none;">
                                        <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                                            <li class="c-text-list__item">성인(19세 이상 내국인)은 셀프개통과 상담사 개통신청 모두 가능합니다.</li>
                                            <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                                                <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                                            </c:if>
                                        </ul>
                                    </div>
                                    <div class="c-box c-box--type1 _isTeen" style="display:none;">
                                        <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                                            <li class="c-text-list__item">미성년자(19세 미만 내국인)는 상담사 개통 신청만 가능합니다. (셀프개통 불가)</li>
                                            <li class="c-text-list__item">가입신청서 작성 완료 후, 법정대리인에게 순차적으로 연락하여 개통 안내를 드립니다.</li>
                                            <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                                                <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                                            </c:if>
                                        </ul>
                                    </div>
                                    <div class="c-box c-box--type1 _isForeigner" style="display:none;">
                                        <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                                            <li class="c-text-list__item">외국인은 상담사 개통신청만 가능합니다. (셀프개통 불가)</li>
                                            <li class="c-text-list__item">가입신청서 작성 완료 후, 순차적으로 연락하여 개통 안내를 드립니다. (안내는 한국어, 영어, 중국어로 제공됩니다.)</li>
                                            <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                                                <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                                            </c:if>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="app-form-view-group" <c:out value="${phoneProdBas.prodCtgType eq '03' ? 'style=display:none;' : ''}"/> >
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3 data-tpbox="#openTypeTooltip">
                                               개통유형
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </h3>
                                    <div class="c-tooltip-box" id="openTypeTooltip">
                                        <h3 class="c-tooltip-box__title c-hidden">개통유형 설명</h3>
                                        <div class="c-tooltip-box__content">
                                            <c:choose>
                                                <c:when test="${prdtIndCd eq '10'}">
                                                    <h4 class="c-heading c-heading--type6 u-mt--0">셀프개통</h4>
                                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                                        <li class="c-text-list__item">한 휴대폰에서 2회선 이용 시, 메인회선과 서브회선이 동일 명의가 아닐 경우 이용에 제약이 있을 수 있습니다.</li>
                                                        <li class="c-text-list__item">외국인, 미성년자는 셀프개통이 불가하므로 '상담사 개통 신청'을 이용 바랍니다.</li>
                                                        <li class="c-text-list__item">개통이 잘 안되시거나 어려운 부분이 있으시면 평일 09:00 ~ 18:00 고객센터(1899-5000/유료)로 연락 부탁드립니다.</li>
                                                        <li class="c-text-list__item">eSIM 개통을 위해서는 EID 등록이 선행되어야 하며, IMEI 및 32자리 EID 캡쳐화면 이미지 업로드가 필요합니다.</li>
                                                        <li class="c-text-list__item">이미지 업로드 시 자동으로 입력됩니다. <a class="c-text-link--bluegreen" data-dialog-trigger="#uploadguide-dialog">이미지 가이드 보기</a></li>
                                                    </ul>
                                                    <h4 class="c-heading c-heading--type6">상담사 개통 신청</h4>
                                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                                        <li class="c-text-list__item">한 휴대폰에서 2회선 이용 시, 메인회선과 서브회선이 동일 명의가 아닐 경우 이용에 제약이 있을 수 있습니다.</li>
                                                        <li class="c-text-list__item">가입신청서를 작성하시면 순차적으로 상담사가 전화로 본인확인 후 개통이 진행되며 지속 부재 시 자동으로 취소됩니다.</li>
                                                    </ul>
                                                </c:when>
                                                <c:when test="${prdtIndCd eq '11'}">
                                                    <h4 class="c-heading c-heading--type6 u-mt--0">셀프개통</h4>
                                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                                        <li class="c-text-list__item">외국인, 미성년자 신규가입은 셀프개통이 불가하므로 ‘상담사 개통 신청＇을 이용 바랍니다.</li>
                                                        <li class="c-text-list__item">현재 사용중인 회선의 납부 방법이 ‘지로’일 경우 ‘상담사 개통 신청＇으로만 개통 가능합니다.</li>
                                                        <li class="c-text-list__item">개통이 잘 안되시거나 어려운 부분이 있으시면 평일 09:00 ~ 18:00 고객센터(1899-5000/유료)로 연락 부탁드립니다.</li>
                                                    </ul>
                                                    <h4 class="c-heading c-heading--type6">상담사 개통 신청</h4>
                                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                                        <li class="c-text-list__item">가입신청서를 작성하시면 순차적으로 상담사가 전화로 본인확인 후 개통이 진행되며 지속 부재 시 자동으로 취소됩니다.</li>
                                                    </ul>
                                                </c:when>
                                                <c:otherwise>
                                                    <h4 class="c-heading c-heading--type6 u-mt--0">셀프개통</h4>
                                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                                        <li class="c-text-list__item">유심을 보유하고 있으시다면 셀프개통이 가능합니다.</li>
                                                        <li class="c-text-list__item">유심이 없으신 고객님은 다이렉트몰 구매하기를 이용하시거나, 편의점 또는 오픈마켓에서 유심 구매 후 셀프개통을 진행해주세요.</li>
                                                        <li class="c-text-list__item">외국인, 미성년자는 셀프개통이 불가하므로 ‘상담사 개통 신청’을 이용 바랍니다.</li>
                                                        <li class="c-text-list__item">개통이 잘 안되시거나 어려운 부분이 있으시면 평일 09:00 ~ 18:00 고객센터(1899-5000/유료)로 연락 부탁드립니다.</li>
                                                    </ul>
                                                    <h4 class="c-heading c-heading--type6">상담사 개통 신청</h4>
                                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                                        <li class="c-text-list__item">가입신청서를 작성하시면 순차적으로 AI상담사 또는 개통상담사가 전화를 드려 본인확인을 진행합니다.</li>
                                                        <li class="c-text-list__item">3회 이상 통화가 되지 않을 경우 신청정보는 삭제됩니다.</li>
                                                        <li class="c-text-list__item">휴대폰(단말) 요금제는 셀프개통이 불가합니다.</li>
                                                    </ul>
                                                    <h4 class="c-heading c-heading--type6">
                                                        <span class="u-co-sub-4">부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.</span>
                                                        <br />예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한
                                                    </h4>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <button class="c-tooltip-box__close" data-tpbox-close>
                                            <span class="c-hidden">툴팁닫기</span>
                                        </button>
                                    </div>
                                </div>
                                <div class="app-form-view__form">
                                    <div class="c-check-wrap" role="group" aria-labelledby="radOpenType">
                                        <div class="c-chk-wrap">
                                            <input class="c-radio c-radio--button" id="radOpenType1" type="radio" name="radOpenType" value="7">
                                            <label class="c-label" for="radOpenType1">
                                                <c:choose>
                                                    <c:when test="${prdtIndCd eq '10'}"><span class="app-form-view__lable">셀프개통<br /><span class="u-fs-13">(eSIM)</span></span></c:when>
                                                    <c:when test="${prdtIndCd eq '11'}"><span class="app-form-view__lable">셀프개통<br /><span class="u-fs-13">(유심있음)</span></span></c:when>
                                                    <c:otherwise><span class="app-form-view__lable">셀프개통<br /><span class="u-fs-13">(유심있음)</span></span></c:otherwise>
                                                </c:choose>
                                            </label>
                                        </div>
                                        <div class="c-chk-wrap">
                                            <input class="c-radio c-radio--button" id="radOpenType2" type="radio" name="radOpenType" value="3">
                                            <label class="c-label" for="radOpenType2">
                                                <span class="app-form-view__lable">상담사 개통 신청</span>
                                            </label>
                                        </div>
                                    </div>
                                    <c:choose>
                                        <c:when test="${prdtIndCd eq '10'}">
                                        <div class="c-box c-box--type1">
                                            <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                                                <li class="c-text-list__item">유심이 필요하신가요?
                                                    <a class="c-text c-text--underline u-co-sub-3 u-ml--4" href="/m/appForm/reqSelfDlvry.do">유심구매바로가기</a>
                                                </li>
                                            </ul>
                                        </div>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <c:if test="${prdtIndCd eq '10'}">
                            <div class="app-form-view-group">
                                <div class="app-form-view">
                                    <div class="app-form-view__title c-flex--between">
                                        <h3>휴대폰 정보 등록</h3>
                                        <div class="c-button-wrap c-button-wrap--right _card u-mt--0">
                                            <button class="c-button _ocrEidRecord" data-type="eid">
                                                <span class="c-text c-text--type3 u-co-sub-4">스캔하기</span>
                                                <i class="c-icon c-icon--scan" aria-hidden="true"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="c-form u-mt--16">
                                        <div class="c-form__input-group is-readonly">
                                            <div class="c-form__input c-form__input-division" data-function-initialized="initialized">
                                                <input class="c-input--div" type="text" id="modelNm" name="modelNm" placeholder="휴대폰 모델명" readonly="" data-function-initialized="initialized">
                                                <label class="c-form__label" for="modelNm">휴대폰 모델명</label>
                                            </div>
                                        </div>
                                        <div class="c-form__input-group is-readonly">
                                            <div class="c-form__input c-form__input-division" data-function-initialized="initialized">
                                                <input class="c-input--div" type="text" id="eid" name="eid" placeholder="EID" readonly="" data-function-initialized="initialized">
                                                <label class="c-form__label" for="eid">EID</label>
                                            </div>
                                        </div>
                                        <div class="c-form__input-group is-readonly">
                                            <div class="c-form__input c-form__input-division" data-function-initialized="initialized">
                                                <input class="c-input--div" type="text" id="imei1" name="imei1" placeholder="IMEI1" readonly="" data-function-initialized="initialized">
                                                <label class="c-form__label" for="imei1">IMEI1</label>
                                            </div>
                                        </div>
                                        <div class="c-form__input-group is-readonly">
                                            <div class="c-form__input c-form__input-division" data-function-initialized="initialized">
                                                <input class="c-input--div" type="text" id="imei2" name="imei2" placeholder="IMEI2" readonly="" data-function-initialized="initialized">
                                                <label class="c-form__label" for="imei2">IMEI2</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="c-button-wrap u-mt--8">
                                <label for="image" class="c-button c-button--full c-button--mint">이미지 등록</label>
                                <input type="file" id="image" name="image" class="c-hidden ocrImg" accept="image/jpg, image/png, image/jpeg, image/tif, image/tiff, image/bmp">
                            </div>
                        </c:if>

                        <div class="app-form-view-group">
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3>가입유형</h3>
                                </div>
                                <div class="app-form-view__form">
                                   <div class="c-check-wrap" role="group" aria-labelledby="operType" id="regiTypeList"></div>
                                </div>
                            </div>
                        </div>
                        <hr class="c-hr c-hr--type1 c-expand">
                        <div class="app-form-view-group">
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3 id="radUsimtType">요금제 유형</h3>
                                </div>
                                <div class="app-form-view__form">
                                   <div class="c-check-wrap c-check-wrap--type3" role="group" aria-labelledby="radUsimType">
                                        <c:choose>
                                            <c:when test="${prdtIndCd eq '11'}">
                                                <input class="c-radio c-radio--button" id="dataType0" type="radio" name="dataType" value="LTE"/>
                                                <label class="c-label" for="dataType0">LTE</label>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${nmcp:getCodeList('usimProductDataType')}" var="usimProductDataType" varStatus="status">
                                                    <input class="c-radio c-radio--button" id="dataType${status.index}" type="radio" name="dataType" value="${usimProductDataType.dtlCd}"/>
                                                    <label class="c-label" for="dataType${status.index}">${usimProductDataType.dtlCdNm}</label>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="app-form-view-group">
                            <div class="app-form-view">
                                <div class="app-form-view__form">
                                    <div id="modelList" style="display: none;"></div>
                                    <div id="selAgreeAmt" style="display: none;"></div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${not empty phoneProdBas.prodId and phoneProdBas.prodCtgType ne '03'}">
                        <div class="app-form-view-group">
                            <div class="app-form-view">
                                <div class="app-form-view__form">
                                    <select id="hndsetModelId" name="hndsetModelId" title="용량/색상" style="display:none;">
                                        <c:forEach var="sntyItem" items="${phoneProdBas.phoneSntyBasDtoListY}">
                                            <option sdoutYn="${sntyItem.sdoutYn}" value="${sntyItem.hndsetModelId }" colorCd="${sntyItem.atribValCd1 }"
                                                    <c:if test="${sntyItem.hndsetModelId == hndsetModelId }">selected="selected"</c:if>>${sntyItem.atribValNm2 }/${sntyItem.atribValNm1}
                                                <c:if test="${sntyItem.sdoutYn eq 'Y'}">[품절]</c:if>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="app-form-view-group" style="display: none;">
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3 data-tpbox="#openTypeTooltip">
                                               개통유형
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </h3>
                                    <div class="c-tooltip-box" id="openTypeTooltip">
                                        <h3 class="c-tooltip-box__title c-hidden">개통유형 설명</h3>
                                        <div class="c-tooltip-box__content">
                                            <c:choose>
                                                <c:when test="${prdtIndCd eq '10'}">
                                                    <h4 class="c-heading c-heading--type6 u-mt--0">셀프개통</h4>
                                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                                        <li class="c-text-list__item">한 휴대폰에서 2회선 이용 시, 메인회선과 서브회선이 동일 명의가 아닐 경우 이용에 제약이 있을 수 있습니다.</li>
                                                        <li class="c-text-list__item">외국인, 미성년자는 셀프개통이 불가하므로 ‘상담사 개통 신청’을 이용 바랍니다.</li>
                                                        <li class="c-text-list__item">개통이 잘 안되시거나 어려운 부분이 있으시면 평일 09:00 ~ 18:00 고객센터(1899-5000/유료)로 연락 부탁드립니다.</li>
                                                    </ul>
                                                    <h4 class="c-heading c-heading--type6">상담사 개통 신청</h4>
                                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                                        <li class="c-text-list__item">한 휴대폰에서 2회선 이용 시, 메인회선과 서브회선이 동일 명의가 아닐 경우 이용에 제약이 있을 수 있습니다.</li>
                                                        <li class="c-text-list__item">가입신청서를 작성하시면 순차적으로 상담사가 전화로 본인확인 후 개통이 진행되며 지속 부재 시 자동으로 취소됩니다.</li>
                                                    </ul>
                                                </c:when>
                                                <c:when test="${prdtIndCd eq '11'}">
                                                    <h4 class="c-heading c-heading--type6 u-mt--0">셀프개통</h4>
                                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                                        <li class="c-text-list__item">외국인, 미성년자 신규가입은 셀프개통이 불가하므로 ‘상담사 개통 신청＇을 이용 바랍니다.</li>
                                                        <li class="c-text-list__item">현재 사용중인 회선의 납부 방법이 ‘지로’일 경우 ‘상담사 개통 신청＇으로만 개통 가능합니다.</li>
                                                        <li class="c-text-list__item">개통이 잘 안되시거나 어려운 부분이 있으시면 평일 09:00 ~ 18:00 고객센터(1899-5000/유료)로 연락 부탁드립니다.</li>
                                                    </ul>
                                                    <h4 class="c-heading c-heading--type6">상담사 개통 신청</h4>
                                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                                        <li class="c-text-list__item">가입신청서를 작성하시면 순차적으로 상담사가 전화로 본인확인 후 개통이 진행되며 지속 부재 시 자동으로 취소됩니다.</li>
                                                    </ul>
                                                </c:when>
                                                <c:otherwise>
                                                    <h4 class="c-heading c-heading--type6 u-mt--0">셀프개통</h4>
                                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                                        <li class="c-text-list__item">유심을 보유하고 있으시다면 셀프개통이 가능합니다.</li>
                                                        <li class="c-text-list__item">유심이 없으신 고객님은 다이렉트몰 구매하기를 이용하시거나, 편의점 또는 오픈마켓에서 유심 구매 후 셀프개통을 진행해주세요.</li>
                                                        <li class="c-text-list__item">외국인, 미성년자는 셀프개통이 불가하므로 ‘상담사 개통 신청’을 이용 바랍니다.</li>
                                                        <li class="c-text-list__item">개통이 잘 안되시거나 어려운 부분이 있으시면 평일 09:00 ~ 18:00 고객센터(1899-5000/유료)로 연락 부탁드립니다.</li>
                                                    </ul>
                                                    <h4 class="c-heading c-heading--type6">상담사 개통 신청</h4>
                                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                                        <li class="c-text-list__item">가입신청서를 작성하시면 순차적으로 AI상담사 또는 개통상담사가 전화를 드려 본인확인을 진행합니다.</li>
                                                        <li class="c-text-list__item">3회 이상 통화가 되지 않을 경우 신청정보는 삭제됩니다.</li>
                                                        <li class="c-text-list__item">휴대폰(단말) 요금제는 셀프개통이 불가합니다.</li>
                                                    </ul>
                                                    <h4 class="c-heading c-heading--type6">
                                                        <span class="u-co-sub-4">부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.</span>
                                                        <br />예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한
                                                    </h4>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <button class="c-tooltip-box__close" data-tpbox-close>
                                            <span class="c-hidden">툴팁닫기</span>
                                        </button>
                                    </div>
                                </div>
                                <div class="app-form-view__form">
                                    <div class="c-check-wrap" role="group" aria-labelledby="radOpenType">
                                        <div class="c-chk-wrap">
                                            <input class="c-radio c-radio--button"type="radio" name="radOpenType" disabled>
                                            <label class="c-label" for="radOpenType1">
                                                <span class="app-form-view__lable">셀프개통<br/><span class="u-fs-13">(유심있음)</span></span>
                                            </label>
                                        </div>
                                        <div class="c-chk-wrap">
                                            <input class="c-radio c-radio--button" id="radOpenType2" type="radio" name="radOpenType" value="3" checked>
                                            <label class="c-label" for="radOpenType2">
                                                <span class="app-form-view__lable">상담사 개통 신청</span>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="c-box c-box--type1">
                                        <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                                            <li class="c-text-list__item u-co-gray">신청서 작성 후 상담사가 개통을 도와드립니다.</li>
                                            <li class="c-text-list__item u-co-gray">휴대폰 요금제는 셀프개통이 불가합니다.</li>
                                            <li class="c-text-list__item">
                                                <span class="u-co-sub-4">부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.</span>
                                                <br />예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="app-form-view-group">
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3 data-tpbox="#RegistTypeTooltip">
                                               가입유형
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </h3>
                                    <div class="c-tooltip-box" id="RegistTypeTooltip">
                                        <div class="c-tooltip-box__title">번호이동</div>
                                        <div class="c-tooltip-box__content">
                                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                <li class="c-text-list__item">타 통신사 이용 중이신 고객님께서 사용하시는 번호 그대로 kt M 모바일로 통신사를 이동합니다.</li>
                                            </ul>
                                        </div>
                                        <div class="c-tooltip-box__title">신규가입</div>
                                        <div class="c-tooltip-box__content">
                                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                <li class="c-text-list__item">새로운 번호로 개통합니다.</li>
                                            </ul>
                                        </div>
                                        <div class="c-tooltip-box__title">기기변경</div>
                                        <div class="c-tooltip-box__content">
                                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                <li class="c-text-list__item">기존 kt M모바일 고객님이 휴대폰만 새로 바꾸고 사용하던 정보는 그대로 유지하는 것을 의미합니다.</li>
                                            </ul>
                                        </div>
                                        <button class="c-tooltip-box__close" data-tpbox-close>
                                            <span class="c-hidden">툴팁닫기</span>
                                        </button>
                                    </div>
                                </div>
                                <div class="app-form-view__form">
                                   <div class="c-check-wrap col-3" role="group" aria-labelledby="radRegistType">
                                       <div class="c-check-wrap">
                                           <c:if test="${mspSaleDto.mspSalePlcyMstDtoSimbol.mnpYn == 'Y'}">
                                               <div class="c-chk-wrap">
                                                   <input class="c-radio c-radio--button" id="radRegistType1" type="radio" name="operType" <c:if test="${operType == PhoneConstant.OPER_PHONE_NUMBER_TRANS }"> checked </c:if> value="${PhoneConstant.OPER_PHONE_NUMBER_TRANS }">
                                                   <label class="c-label" for="radRegistType1">번호이동</label>
                                               </div>
                                           </c:if>
                                           <c:if test="${mspSaleDto.mspSalePlcyMstDtoSimbol.newYn == 'Y'}">
                                               <div class="c-chk-wrap">
                                                   <input class="c-radio c-radio--button" id="radRegistType2" type="radio" name="operType" <c:if test="${operType == PhoneConstant.OPER_NEW }"> checked </c:if> value="${PhoneConstant.OPER_NEW }">
                                                   <label class="c-label" for="radRegistType2">신규가입</label>
                                               </div>
                                           </c:if>
                                           <c:if test="${mspSaleDto.mspSalePlcyMstDtoSimbol.hdnYn == 'Y'}">
                                               <div class="c-chk-wrap">
                                                   <input class="c-radio c-radio--button" id="radRegistType3" type="radio" name="operType" <c:if test="${operType == Constants.OPER_TYPE_EXCHANGE }"> checked </c:if> value="${Constants.OPER_TYPE_EXCHANGE}">
                                                   <label class="c-label" for="radRegistType3">기기변경</label>
                                               </div>
                                           </c:if>
                                       </div>
                                   </div>
                                </div>
                            </div>
                        </div>

                        <hr class="c-hr c-hr--type1 c-expand">
                        <c:if test="${phoneProdBas.prodCtgId eq '45'}">
                            <div class="app-form-view-group">
                                <div class="app-form-view">
                                    <div class="app-form-view__title">
                                        <h3>요금제 유형</h3>
                                    </div>
                                    <div class="app-form-view__form">
                                        <div class="c-check-wrap c-check-wrap--type3" role="group" aria-labelledby="radServiceType">
                                            <input class="c-radio c-radio--button" id="serviceType0" type="radio" name="dataType" value="LTE"/>
                                            <label class="c-label" for="serviceType0">LTE</label>
                                            <input class="c-radio c-radio--button" id="serviceType1" type="radio" name="dataType" value="5G"/>
                                            <label class="c-label" for="serviceType1">5G</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <div class="app-form-view-group">
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3 data-tpbox="#PeriodTypeTooltip">
                                               요금제 약정기간
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </h3>
                                    <div class="c-tooltip-box" id="PeriodTypeTooltip">
                                        <div class="c-tooltip-box__title">요금제 약정기간</div>
                                        <div class="c-tooltip-box__content">
                                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                <li class="c-text-list__item">선택한 할부 기간에 따라 월 납부금액이 달라질 수 있습니다.</li>
                                                <li class="c-text-list__item">무약정 선택 시 하단 할인유형을 선택할 수 없습니다.</li>
                                                <li class="c-text-list__item">의무사용기간을 조건으로 보조금을 지급받은 고객은 의무사용기간 종료 전에 계약을 해지(요금미납, 단말기 파손 등으로 해지하는 경우 포함)할 경우 회사가 별도로 정하는 위약금을 납부하여야 합니다.</li>
                                            </ul>
                                        </div>
                                        <button class="c-tooltip-box__close" data-tpbox-close>
                                            <span class="c-hidden">툴팁닫기</span>
                                        </button>
                                    </div>
                                </div>
                                <div class="app-form-view__form">
                                    <div class="c-check-wrap c-check-wrap--type2" role="group" aria-labelledby="instNom">
                                        <div class="c-chk-wrap">
                                            <input class="c-radio c-radio--button" id="radPeriodType99" type="radio" name="instNom" value="0" <c:if test="${instNom == '0'}"> checked </c:if>/>
                                            <label class="c-label" for="radPeriodType99">무약정</label>
                                        </div>
                                        <c:forEach var="item" items="${mspSaleAgrmMstList}" varStatus="status">
                                            <div class="c-chk-wrap">
                                                <input class="c-radio c-radio--button" id="radPeriodType${status.index}" type="radio" name="instNom" value="${item.instNom}" <c:if test="${instNom == item.instNom}"> checked </c:if>>
                                                <label class="c-label" for="radPeriodType${status.index}">${item.instNom}<c:if test="${item.instNom ne '0'}">개월</c:if></label>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="app-form-view-group">
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3 data-tpbox="#PaymemnTypeTooltip">
                                               단말기 할부기간
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </h3>
                                    <div class="c-tooltip-box" id="PaymemnTypeTooltip">
                                        <div class="c-tooltip-box__title">단말기 할부기간</div>
                                        <div class="c-tooltip-box__content">
                                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                <!-- 요금제 약정기간 '무약정' 선택 시 노출-->
                                                <li class="c-text-list__item">요금제 무약정 선택 시 일시 납부, 24개월, 30개월, 36개월 중 선택할 수 있습니다.</li>
                                                <!-- 요금제 약정기간 '약정' 선택 시 노출-->
                                                <li class="c-text-list__item">단말기 할부기간은 요금제 약정기간과 동일한 기간만 선택할 수 있습니다.</li>
                                            </ul>
                                        </div>
                                        <button class="c-tooltip-box__close" data-tpbox-close>
                                            <span class="c-hidden">툴팁닫기</span>
                                        </button>
                                    </div>
                                </div>
                                <div class="app-form-view__form">
                                   <select class="c-select sel-paymemnt-plan" name="agrmTrm" id="agrmTrm"></select>
                                </div>
                            </div>
                        </div>
                        <div class="app-form-view-group" id="discountTxtDiv">
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3 data-tpbox="#DiscountTypeTooltip">
                                               할인유형
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </h3>
                                    <div class="c-tooltip-box" id="DiscountTypeTooltip">
                                        <div class="c-tooltip-box__title">할인유형</div>
                                        <div class="c-tooltip-box__content">
                                            <ul class="c-text-list c-bullet c-bullet--hyphen">
                                                <li class="c-text-list__item">단말할인: 공통지원금을 지원받는 경우</li>
                                                <li class="c-text-list__item">요금할인: 공통지원금을 받지 않고 요금에 대한 지원을 받는 경우</li>
                                            </ul>
                                        </div>
                                        <button class="c-tooltip-box__close" data-tpbox-close>
                                            <span class="c-hidden">툴팁닫기</span>
                                        </button>
                                    </div>
                                </div>
                                <div class="app-form-view__form">
                                   <div class="c-check-wrap" role="group" aria-labelledby="chkDiscountType">
                                       <div class="c-chk-wrap">
                                           <input class="c-checkbox c-checkbox--button" id="chkDiscountType1" type="radio" name="saleTy" value="KD" <c:if test="${sprtTp eq PhoneConstant.PHONE_DISCOUNT_FOR_MSP or empty sprtTp}">checked="checked"</c:if>>
                                           <label class="c-label" for="chkDiscountType1">단말 할인</label>
                                       </div>
                                       <div class="c-chk-wrap">
                                           <input class="c-checkbox c-checkbox--button" id="chkDiscountType2" type="radio" name="saleTy" value="PM" <c:if test="${sprtTp eq PhoneConstant.CHARGE_DISCOUNT_FOR_MSP }">checked="checked"</c:if>>
                                           <label class="c-label" for="chkDiscountType2">요금 할인</label>
                                       </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <!-- 요금제 선택 전 -->
                    <div class="app-form-view-group" id="radPayTypeA">
                        <div class="app-form-view">
                            <div class="app-form-view__title">
                                <h3>요금제 선택</h3>
                            </div>
                            <div class="app-form-view__form">
                                <div class="c-box c-box--dotted">
                                    <button type="button" id="radPayTypeBtn" class="c-button c-button--full" >
                                        <i class="c-icon c-icon--plus" aria-hidden="true"></i>
                                        <span class="u-co-gray u-ml--8">요금제를 선택해 주세요.</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 요금제 선택 후 New / id="choicePay", id="choicePayTxt" html 구조가 바껴셔 빼 놓음 -->
                    <div class="app-form-view-group rate-active" id="radPayTypeB" style="display: none;">
                        <div class="app-form-view">
                            <div class="app-form-view__title">
                                <h3>요금제 선택</h3>
                                <button type="button" class="paymentPop" >
                                   <i class="c-icon c-icon--plus-bluegreen" aria-hidden="true"></i>
                                   <span>다른 요금제 고르기</span>
                                   <i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i>
                                </button>
                            </div>
                            <div class="app-form-view__form">
                                <div class="sel-fee-wrap">
                                    <div class="sel-fee-wrap__item">
                                        <dl>
                                            <dt class="sel-fee-wrap__title" id="choicePay">
                                            </dt>
                                            <dd class="sel-fee-wrap__text--type2">
                                                <div class="sel-fee-info">
                                                    <div>
                                                        <i class="c-icon c-icon--data-type6" aria-hidden="true"></i>
                                                    </div>
                                                    <div>
                                                         <span class="max-data-label" >
                                                             <em  id="choicePayTxt10"></em>
                                                         </span>
                                                        <span id="choicePayTxt1"></span>
                                                        <em id="choicePayTxt2"></em>
                                                    </div>
                                                </div>
                                                <div class="sel-fee-info">
                                                    <div>
                                                        <i class="c-icon c-icon--call-type4" aria-hidden="true"></i>
                                                    </div>
                                                    <div>
                                                        <span id="choicePayTxt3"></span>
                                                        <em id="choicePayTxt4"></em>
                                                    </div>
                                                </div>
                                                <div class="sel-fee-info">
                                                    <div>
                                                        <i class="c-icon c-icon--msg-type5" aria-hidden="true"></i>
                                                    </div>
                                                    <div>
                                                        <span id="choicePayTxt5"></span>
                                                        <em id="choicePayTxt6"></em>
                                                    </div>
                                                </div>
                                                <div class="sel-fee-benefit" id="choicePayTxt9">
                                                    <span class="sel-fee-benefit__title" id="choicePayTxt8" ></span>
                                                    <em id="choicePayTxt7" ></em>
                                                </div>
                                            </dd>
                                        </dl>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 요금제 선택 후, 기존 소스 -->
                    <!-- <div id="radPayTypeB" style="display: none;">
                        <div>
                            <div class="c-form__title--type2 flex-type">
                                요금제 선택
                                <a class="c-button c-button--sm u-co-mint c-expand paymentPop" >
                                    요금제 선택하기 <i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i>
                                </a>
                            </div>
                        </div>
                        <div class="c-box c-box--mint">
                            <h3 class="c-text c-text--normal" id="choicePay"></h3>
                            <p class="c-text c-text--type4 c-text--regular" id="choicePayTxt"></p>
                        </div>
                    </div> -->

                    <!-- 이벤트 코드 영역 -->
                    <div class="app-form-view-group" id="evntCdPrmtWrap" style="display: none;">
                        <div class="app-form-view">
                            <div class="app-form-view__title">
                                <h3>이벤트 코드</h3>
                            </div>
                            <div class="app-form-view__form u-mt--14">
                                <p class="u-mb--16">해당 요금제는 이벤트 코드 적용이 가능합니다.<br />이벤트에서 확인한 코드가 있다면 입력 해 주세요.<br />※ 지급 조건은 프로모션별로 상이합니다.</p>
                                <div class="c-form" style="display: flex">
                                    <input class="c-input" name="evntCdPrmt" id="evntCdPrmt" type="text" placeholder="이벤트 코드 입력" maxlength="50" onkeyup="nextStepBtnChk();">
                                    <label class="c-form__label sr-only" for="evntCdPrmt">이벤트코드</label>
                                    <button id="evntCdPrmtBtn" class="c-button c-button--mint u-width--140 u-ml--8" onclick="checkEvntCdPrmt();">확인</button>
                                    <button id="evntCdTryBtn" class="c-button c-button--mint u-width--140 u-ml--8 is-disabled" onclick="tryEvntCdPrmt();">재입력</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 사은품 영역 -->
                    <div class="app-form-view-group" id="divGift" style="display:none;">
                        <div class="app-form-view">
                            <div class="app-form-view__title">
                                <h3>가입 사은품 안내</h3>
                                <p class="u-co-sub-4">선택 사은품이 있는 경우 신청 마지막 단계에서 선택 가능</p>
                            </div>
                            <div class="app-form-view__form u-mt--14">
                                <div class="c-box c-box--type1 u-mt--0" id="divGift2">
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 사은품 리스트 기존 소스, 일단 주석해 놓음, 불필요 시 삭제 -->
                    <!-- <section id="divGift" class="gift-section" style="display:none;"></section> -->

                    <!-- 트리플할인 할인 -->
                    <div class="app-form-view-group _ktTripleDcCss" style="display:none;">
                        <hr class="c-hr c-hr--type1 c-expand">
                        <div class="app-form-view">
                            <div class="app-form-view__title">
                                <h3 id="ktDcRateForm">KT인터넷 트리플할인<span>(선택)</span></h3>
                            </div>
                            <div class="app-form-view__form">
                                <div class="c-check-wrap c-check-wrap--column">
                                    <div class="c-chk-wrap">
                                        <input class="c-radio c-radio--button" id="ktTripleDcAmt_01" type="radio" name="ktTripleDcAmt" value="" checked="checked" />
                                        <label class="c-label" for="ktTripleDcAmt_01">선택 안함</label>
                                    </div>
                                    <div class="c-chk-wrap">
                                        <input class="c-radio c-radio--button"   id="ktTripleDcAmt_02" type="radio" name="ktTripleDcAmt" value="" />
                                        <label class="c-label" id="laKtTripleDcAmt_02"  for="ktTripleDcAmt_02">요금할인 24개월(0원)</label>
                                    </div>
                                </div>
                                <div class="c-box c-box--type1">
                                    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
                                        <li class="c-text-list__item">트리플할인 페이지에서 KT인터넷 상담신청 및 개통 후 마이페이지에서 신청 시 받을 수 있는 요금할인 혜택입니다.</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--  제휴카드 영역 -->
                    <div class="app-form-view-group cprt-card" id="cprtForm" style="display:none;">
                        <hr class="c-hr c-hr--type1 c-expand">
                        <div class="app-form-view">
                            <div class="app-form-view__title">
                                <h3 id="radCardType">제휴카드 할인<span>(선택)</span></h3>
                                <button type="button" onclick="javascript:openPage('pullPopup', '/m/event/cprtCardBnfitLayer.do?cprtCardCtgCd=${cprtCardCtgCd}', '');">
                                   <span>비교하기</span>
                                   <i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i>
                                </button>
                            </div>
                            <p>제휴카드로 통신료 자동 이체 시 받을 수 있는 할인 혜택입니다.</p>
                            <div class="app-form-view__form u-mt--20">
                                <div class="c-check-wrap c-check-wrap--column" id="cprtDiv"></div>
                                <div class="c-button-wrap c-button-wrap--column u-mt--6" id="moreBtnDiv" style="display: none;">
                                    <input type="hidden" id="cprtCardSize"/>
                                    <button class="c-button c-button--full">
                                        <div class="c-block">
                                            <i class="c-icon c-icon--more" aria-hidden="true"></i>
                                        </div>
                                               더보기<span class="c-button__sub" id="moreBtnTxt"></span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-button-wrap">
                        <a class="c-button c-button--share" data-dialog-trigger="#bsSample1">
                            <span class="c-hidden">공유하기</span>
                            <i class="c-icon c-icon--share" aria-hidden="true"></i>
                        </a>
                        <button class="c-button c-button--full c-button--red is-disabled" onclick="nextStep();" id="nextStepBtn">가입하기</button>
                    </div>
                </div>
            </div>


            <!-- 하단 bottom-sheet trigger  영역 시작 (bottom-trigger 작동 시 is-active class 부여해주세요.)-->
            <div class="c-button-wrap c-button-wrap--b-floating is-active" id="bs_floating_button" data-floating-bs-trigger="#bsSample2">
                <!--[2021-12-23] 문구 추가, 배치 조정-->
                <button class="c-button c-button--bs-openner">
                    <p class="c-text c-text--type3 u-fw--regular">
                        월 예상 납부금액
                        <span class="fs-26 u-ml--8" id="totalPriceBottom">0 <span class="fs-14 fw-normal">원</span></span>
                        <input type="hidden" name="settlAmt" id="settlAmt"/>
                    </p>
                    <p class="c-text c-text--type5 u-co-red-sub1">가입비 및 유심비 등 기타요금은 별도 청구됩니다.</p>
                    <i class="c-icon c-icon--arrow-up" aria-hidden="true"></i>
                </button>
                <!--[$2021-12-23] 문구 추가, 배치 조정-->
            </div>
            <!-- 하단 bottom-sheet trigger  영역 끝-->

        </div>

        <form name="frm" id="frm" method="post" action="/m/loginForm.do">
            <input type="hidden" name="redirectUrl" id="uri"/>
        </form>
        <button data-dialog-trigger="#signup-dialog" id="chkModalPop" style="display:none;">가입 전 확인해주세요 popup 호출</button>
        <div class="c-modal" id="signup-dialog" role="dialog" tabindex="-1" aria-describedby="#signup-title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="signup-title">가입 전 확인해주세요</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body overflow-x-hidden">
                        <h3 class="c-heading c-heading--type3">신분증과 <br>계좌/신용카드를 준비해주세요!</h3>
                        <p class="c-text c-text--type2 u-co-gray"> 비대면 가입신청서 작성 시 본인인증과 <br>납부정보 확인이 필요합니다. </p>

                        <c:choose>
                            <c:when test="${prdtIndCd eq '10'}">
                                <div class="c-box u-mt--32">
                                    <img class="center-img" src="/resources/images/mobile/content/img_id_card.png" alt="ars 수신번호">
                                </div>
                                <hr class="c-hr c-hr--type2">
                                <b class="c-text--type3 c-bullet">
                                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>유의사항
                                </b>
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-red">한 휴대폰에서 2회선 이용 시, 메인회선과 서브회선이 동일 명의가 아닐 경우 이용에 제약이 있을 수 있습니다.</li>
                                </ul>
                            </c:when>
                            <c:when test="${prdtIndCd eq '11'}">
                                <div class="c-box u-mt--32">
                                    <img class="center-img" src="/resources/images/mobile/content/img_id_card.png" alt="ars 수신번호">
                                </div>
                                <hr class="c-hr c-hr--type2">
                                <b class="c-text--type3 c-bullet">
                                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>유의사항
                                </b>
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-red">워치는 인증하신 M모바일 회선과 통합 청구됩니다.</li>
                                </ul>

                            </c:when>
                            <c:otherwise>
                                <div class="c-box u-mt--32">
                                    <img class="center-img" src="/resources/images/mobile/content/img_id_card.png" alt="ars 수신번호">
                                </div>
                                <hr class="c-hr c-hr--type2">
                                <b class="c-text--type3 c-bullet">
                                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>선택 가능 본인인증 방법
                                </b>
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-gray" id="authListDesc">신용카드, 네이버인증서, toss 인증서, PASS 인증서, 카카오 본인인증</li>
                                </ul>
                            </c:otherwise>
                        </c:choose>



                        <div class="c-box c-box--type1 d-box--type1 u-ta-center">

                            <c:if test="${!nmcp:hasLoginUserSessionBean()}">
                                <h3 class="c-heading c-heading--type3 u-fw--medium">kt M모바일 회원이신가요?</h3>
                                <p class="c-text c-text--type3 u-fw--regular">
                                    로그인 후 진행해주세요. <br> 등록된 고객정보를 활용한<span class="u-co-red">빠른 가입</span>이 가능합니다.
                                </p>
                                <div class="c-button-wrap c-button-wrap--column">
                                    <button class="c-button c-button--full c-button--primary" onclick="confirmNextStep('noMember');">비회원으로 가입하기</button>
                                    <a class="c-button c-button--underline c-button--sm" href="javascript:void(0);" onclick="confirmNextStep('toMember');">로그인 후 가입하기</a>
                                </div>
                            </c:if>
                            <c:if test="${nmcp:hasLoginUserSessionBean()}">
                                <div class="c-button-wrap c-button-wrap--column">
                                    <button class="c-button c-button--full c-button--primary" onclick="confirmNextStep('member');">가입하기</button>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="c-modal c-modal--bs" id="bsSample1" role="dialog" tabindex="-1" aria-describedby="#bsSampleDesc1" aria-labelledby="#bsSampletitle1">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="bsSampletitle1">공유하기</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">바텀시트 닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body" id="bsSampleDesc1">
                        <div class="c-button-wrap c-flex u-mt--0">
                            <a class="c-button" href="javascript:void(0);" onclick="kakaoShare(); return false;"> <span class="c-hidden">카카오-새 창열림</span>
                                <i class="c-icon c-icon--kakao" aria-hidden="true"></i>
                            </a>
                            <a class="c-button" href="javascript:void(0);" onclick="lineShare(); return false;"> <span class="c-hidden">line-새 창열림</span>
                                <i class="c-icon c-icon--line" aria-hidden="true"></i>
                            </a>
                            <a class="c-button" href="javascript:void(0);" onclick="copyUrl();"> <span class="c-hidden">URL 복사하기</span>
                                <i class="c-icon c-icon--link" aria-hidden="true"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="c-modal c-modal--bs" id="bsSample2" role="dialog" tabindex="-1" aria-describedby="#bsDetailDesc2" aria-labelledby="#bsDetailtitle2">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="bsDetailtitle2"></h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--arrow-down" aria-hidden="true"></i>
                            <span class="c-hidden">바텀시트 닫기</span>
                        </button>
                    </div>

                    <!--[2021-11-23] 모달내 배너 스와이핑 처리 추가변경 시작 (하단 스와이퍼 js 업데이트 확인요망)===================-->
                    <div class="c-modal__body u-pt--0" id="bsDetailDesc2">
                        <div class="top-sticky-banner">
                            <div class="swiper-event-banner swiper-container c-expand" id="swiperCardBanner">
                                <div class="swiper-wrapper u-mb--24" id="O001"></div>
                                <!--[$2021-11-23] 모달내 배너 스와이핑 처리 추가변경 끝 ===================-->
                            </div>
                        </div>

                        <div class="c-addition-wrap">
                            <c:if test="${not empty phoneProdBas.prodId and phoneProdBas.prodCtgType ne '03'}">
                                <dl class="c-addition c-addition--type1">
                                    <dt>월 단말요금</dt>
                                    <dd class="u-ta-right"><b id="payMnthAmt">0</b> 원</dd>
                                </dl>
                                <dl class="c-addition c-addition--type2">
                                    <dt>단말기출고가</dt>
                                    <dd class="u-ta-right" id="hndstAmt">0 원</dd>
                                </dl>
                                <dl class="c-addition c-addition--type2">
                                    <dt>공통지원금</dt>
                                    <dd class="u-ta-right"><em id="subsdAmt">0 원</em></dd>
                                </dl>
                                <dl class="c-addition c-addition--type2">
                                    <dt>추가지원금</dt>
                                    <dd class="u-ta-right"><em id="agncySubsdAmt">0 원</em></dd>
                                </dl>
                                <dl class="c-addition c-addition--type2">
                                    <dt>할부원금</dt>
                                    <dd class="u-ta-right" id="finstAmt">0 원</dd>
                                </dl>
                                <dl class="c-addition c-addition--type2">
                                    <dt>총할부수수료</dt>
                                    <dd class="u-ta-right" id="totalFinstCmsn">0 원</dd>
                                </dl>
                                <dl class="c-addition c-addition--type2" style="display:none;">
                                    <dt>실구매가</dt>
                                    <dd class="u-ta-right" id="hndstPayAmt">0 원</dd>
                                </dl>
                                <hr class="c-hr c-hr--type2">
                            </c:if>


                            <dl class="c-addition c-addition--type1 bottomTop">
                                <dt>월 통신요금</dt>
                                <dd class="u-ta-right"><b id="totalPrice2">0</b> 원</dd>
                            </dl>
                            <dl class="c-addition c-addition--type2 bottomTop">
                                <dt>기본요금</dt>
                                <dd class="u-ta-right" id="baseAmt">0 원</dd>
                            </dl>
                            <dl class="c-addition c-addition--type2 bottomTop" id="bottomDefDc">
                                <dt>
                                    <a data-tpbox="#tp1">기본할인 <!-- [2021-11-24] 'tooltop' > 'tooltip' -->
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </a>
                                    <div class="c-tooltip-box" id="tp1">
                                        <div class="c-tooltip-box__title">기본할인</div>
                                        <div class="c-tooltip-box__content">약정 가입시 기본적으로 제공되는 요금제 할인금액입니다.</div>
                                    </div>
                                </dt>
                                <dd class="u-ta-right">
                                    <b> <em id="dcAmt">0 원</em></b>
                                </dd>
                            </dl>
                            <dl class="c-addition c-addition--type2 bottomTop" id="bottomPriceDc">
                                <dt>
                                    <a data-tpbox="#tp2">요금할인 <!-- [2021-11-24] 'tooltop' > 'tooltip' -->
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </a>
                                </dt>
                                <dd class="u-ta-right">
                                    <b> <em id="addDcAmt">0 원</em></b>
                                </dd>
                            </dl>
                            <div class="c-tooltip-box" id="tp2">
                                <div class="c-tooltip-box__title">요금할인</div>
                                <div class="c-tooltip-box__content">약정 - 할인 선택시 제공되는 요금할인 혜택입니다.</div>
                                <button class="c-tooltip-box__close" data-tpbox-close>
                                    <span class="c-hidden">툴팁닫기</span>
                                </button>
                            </div>
                            <dl class="c-addition c-addition--type2 bottomTop">
                                <dt>프로모션 할인</dt>
                                <dd class="u-ta-right">
                                    <b> <em id="promotionDcAmtTxt">0 원</em></b>
                                </dd>
                            </dl>

                            <hr class="c-hr c-hr--type2 bottomTop">

                            <!-- 트리플할인  할인금액 -->
                            <dl class="c-addition c-addition--type1 u-mb--12 _ktTripleDcCss" style="display:none;">
                                <dt>KT인터넷 트리플할인</dt>
                                <dd class="u-ta-right"><b class="u-co-sub-3" id="ktTripleDcAmtTxt">0 원</b></dd>
                            </dl>

                            <dl class="c-addition c-addition--type1 bottomTop">
                                <dt>
                                    <a data-tpbox="#tp3">제휴카드 할인 <!-- [2021-11-24] 'tooltop' > 'tooltip' -->
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </a>
                                </dt>
                                <dd class="u-ta-right"><b class="u-co-sub-3" id="cprtCardPrice">0 원</b></dd>
                            </dl>

                            <div class="c-tooltip-box" id="tp3">
                                <div class="c-tooltip-box__title">제휴카드 할인</div>
                                <div class="c-tooltip-box__content">
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                        <li class="c-text-list__item">요금 설계 시 선택한 제휴카드에 따른 할인입니다.</li>
                                        <li class="c-text-list__item">제휴카드 할인은 각 카드사별 이용 조건이 반영되지 않은 최대 할인 금액으로 적용되어 있습니다.<br />정확한 할인 조건은 이벤트 페이지의 각 제휴카드 정보를 꼭 확인하시기 바랍니다.</li>
                                        <li class="c-text-list__item">선택한 카드와 다른 카드 혹은 납부 방법 변경 시 실제 할인 금액과 다를 수 있습니다.</li>
                                    </ul>
                                </div>
                                <button class="c-tooltip-box__close" data-tpbox-close>
                                    <span class="c-hidden">툴팁닫기</span>
                                </button>
                            </div>

                            <hr class="c-hr c-hr--type2 bottomMiddle">

                            <dl class="c-addition c-addition--type1 bottomMiddle">
                                <dt>가입비 및 유심비 별도</dt>
                            </dl>
                            <dl class="c-addition c-addition--type2 bottomMiddle">
                                <dt>가입비(3개월 분납)</dt>
                                <dd class="u-ta-right">
                                    <em id="joinPriceTxt"><span class="c-text c-text--strike">0 원</span></em>
                                </dd>
                            </dl>

                            <c:choose>
                                <c:when test="${prdtIndCd eq '10' }">
                                    <dl class="c-addition c-addition--type2 _noSelf" >
                                        <dt>eSIM(1회)</dt>
                                        <dd class="u-ta-right" id="usimPriceTxt">0 원</dd>
                                    </dl>
                                </c:when>
                                <c:when test="${prdtIndCd eq '11'}">
                                    <dl class="c-addition c-addition--type2 _noSelf" >
                                        <dt>eSIM(1회)</dt>
                                        <dd class="u-ta-right" id="usimPriceTxt">0 원</dd>
                                    </dl>
                                </c:when>
                                <c:otherwise>
                                    <dl class="c-addition c-addition--type2 _noSelf" >
                                        <dt>USIM(최초 1회)</dt>
                                        <dd class="u-ta-right" id="usimPriceTxt">0 원</dd>
                                    </dl>
                                </c:otherwise>
                            </c:choose>
                            <dl class="c-addition c-addition--type2 bottomMiddle">
                                <dt id="move_01" style="display:none;">번호이동 수수료</dt>
                                <dd id="move_02" style="display:none;" class="u-ta-right">800 원</dd>
                                <input type="hidden" id="moveYn"/>
                            </dl>


                            <c:if test="${phoneProdBas.prodCtgType eq '03'}">
                                <hr class="c-hr c-hr--type2">
                                <dl class="c-addition c-addition--type1 bottomMiddle">
                                    <dt>자급제</dt>
                                    <dd class="u-ta-right">
                                        <b><fmt:formatNumber value="${phoneProdBas.selPric}" pattern="#,###"/></b> 원
                                    </dd>
                                </dl>
                                <dl class="c-addition c-addition--type2">
                                    <dt>단말기 결제금액</dt>
                                    <dd class="u-ta-right"><fmt:formatNumber value="${phoneProdBas.selPric}" pattern="#,###"/> 원</dd>
                                </dl>
                                <dl class="c-addition c-addition--type2">
                                    <dt>단말기/용량/색상</dt>
                                    <dd class="u-ta-right">
                                            ${phoneProdBas.prodNm}
                                        <c:forEach var="sntyItem" items="${phoneProdBas.phoneSntyBasDtoListY}" varStatus="status">
                                            <c:if test="${status.index eq 0}"> / ${sntyItem.atribValNm2}</c:if>
                                            <c:if test="${sntyItem.hndsetModelId == hndsetModelId }"> / ${sntyItem.atribValNm1}</c:if>
                                        </c:forEach>
                                        <c:if test="${phoneProdBas.prodCtgType eq '03'}">
                                            <input type="hidden" id="hndsetModelId" value="${hndsetModelId}"/>
                                        </c:if>
                                    </dd>
                                </dl>


                            </c:if>
                            <hr class="c-hr c-hr--type2">

                            <dl class="c-addition c-addition--type1 c-addition--sum">
                                <dt>월 납부금액(부가세 포함)</dt>
                                <dd class="u-ta-right"><b id="totalPrice">0</b> 원</dd>
                            </dl>

                            <dl class="c-addition c-addition--type2">
                                <dt id="bottomTitle"></dt>
                            </dl>
                        </div>
                        <hr class="c-hr c-hr--type2">
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item u-co-gray">
                                <c:choose>
                                    <c:when test="${prdtIndCd eq '10'}">eSIM비는 다운로드에 상관없이 최초 개통 시 1회 발생되며, 프로파일 삭제 등으로 재 다운로드 시 추가 발생합니다.</c:when>
                                    <c:when test="${prdtIndCd eq '11'}">eSIM비는 다운로드에 상관없이 최초 개통 시 1회 발생되며, 프로파일 삭제 등으로 재 다운로드 시 추가 발생합니다.</c:when>
                                    <c:otherwise>kt M mobile에서 제공한 유심으로 개통을 진행하지 않으실 경우, 유심비용이 청구될 수 있습니다.</c:otherwise>
                                </c:choose>
                            </li>
                            <li class="c-text-list__item u-co-gray">월 납부금액은 부가서비스 등의 사용에 따라 추가금액이 합산되어 청구 될 수 있습니다.</li>
                            <li class="c-text-list__item u-co-gray">월 납부금액은 부가세 포함 금액입니다.</li>
                            <li class="c-text-list__item u-co-gray">타사향(SK, LG U+ 등) 단말은 일부 서비스(MMS,영상통화, 교통카드 기능 등) 이용이 제한될 수 있습니다.</li>
                            <li class="c-text-list__item u-co-gray">제휴카드 할인은 각 카드사별 이용 조건이 반영되지 않은 최대 할인 금액으로 적용되어 있습니다.<br />정확한 할인 조건은 이벤트 페이지의 각 제휴카드 정보를 꼭 확인하시기 바랍니다.</li>
                        </ul>
                    </div>

                    <div class="c-modal__footer fixed-area">
                        <div class="c-button-wrap u-mt--0">
                            <a class="c-button c-button--share" data-dialog-trigger="#bsSample1">
                                <span class="c-hidden">공유하기</span>
                                <i class="c-icon c-icon--share" aria-hidden="true"></i>
                            </a>
                            <button class="c-button c-button--full c-button--red" onclick="nextStep();">가입하기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="c-modal rate-modal" id="paymentSelect" role="dialog" tabindex="-1" aria-describedby="#paymentSelectTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="paymentSelectTitle">요금제 선택</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close u-mr--0" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-form c-form--search u-plr--20">
                        <div class="c-form__input u-mt--0">
                            <input class="c-input" type="text" placeholder="검색어를 입력해주세요" id="searchNm" title="검색어 입력" value=""  maxlength="20" autocomplete="off" >
                             <button class="c-button c-button--reset" id="searchClear">
                                 <span class="c-hidden">초기화</span>
                                 <i class="c-icon c-icon--reset" aria-hidden="true"></i>
                             </button>
                            <button class="c-button c-button--search-bk" id="searchRatePlan">
                                <span class="c-hidden">검색어</span>
                                <i class="c-icon c-icon--search-bk" aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>
                    <div class="c-modal__body">

                        <div class="rate-filter-wrap">
                            <div class="btn-category-wrap">
                                <div class="btn-category__list">
                                    <div class="btn-category__inner" id="rateBtnCategory">

                                        <c:set var="ctgList" value="${nmcp:getCodeList('GD0004')}" />
                                        <div class="btn-category__group">
                                            <button type="button" class="btn-category _rateTabM1" data-best-rate="1" >전체</button>
                                            <button type="button" class="btn-category _rateTabM1" data-best-rate="0" >추천</button>
                                            <c:forEach items="${ctgList}" var="ctgObj" >
                                                <button type="button" class="btn-category _rateTabM1 _rateCtg" data-best-rate="-2" data-rate-ctg="${ctgObj.dtlCd}" data-rate-ctg-group1="${ctgObj.expnsnStrVal1}" data-rate-ctg-group2="${ctgObj.expnsnStrVal2}">${ctgObj.dtlCdNm} </button>
                                            </c:forEach>
                                        </div>
                                    </div>
                                        <button id="btnCateroryView" type="button" class="btn-category__expand is-active" aria-expanded="false">
                                            <span class="c-hidden">버튼 펼쳐 보기</span>
                                        </button>
                                </div>
                            </div>
                            <div class="rate-btn-group">
                                <div class="btn-category__compare">
                                    <button type="button" class="btn-category _rateTabM1" data-best-rate="-1" id="rateBtnCompar">
                                        <i class="c-icon c-icon--comparison" aria-hidden="true"></i>
                                        <span class="rate-btn__cnt">0</span>
                                    </button>
                                    <c:if test="${empty phoneProdBas.prodId or phoneProdBas.prodCtgType eq '03'}">
                                    <button type="button" class="btn-category" id="rateBtnMyrate">
                                        <i class="c-icon c-icon--question" aria-hidden="true"></i>
                                    </button>
                                    </c:if>
                                </div>
                                <div class="btn-sort-wrap">
                                    <button type="button" class="btn-sort" data-order-value="${not empty ProdCommendDto.orgnId ? 'XML_CHARGE' : 'CHARGE'}" >가격</button>
                                    <button type="button" class="btn-sort" data-order-value="XML_DATA">데이터</button>
                                    <button type="button" class="btn-sort" data-order-value="VOICE">통화</button>
                                    <button type="button" class="btn-sort" data-order-value="CHARGE_NM">이름</button>
                                </div>
                            </div>
                        </div>

                        <div class="rate-content">

                            <%-- [START] 섹션5 : 설문영역 none --%>
                            <section class="survey" style="display:none;">
                                <!--설문 애니메이션(고정 애니메이션)-->
                                <div class="survey__actor">
                                    <!--설문중에는 02.json play-->
                                    <div class="survey__actor-ani" data-ani-url="/resources/animation/mobile/lottie/02.webjson"></div>
                                    <!--설문중에는 02.json play-->
                                    <div class="survey__actor-ani" data-ani-url="/resources/animation/mobile/lottie/04.webjson"></div>
                                </div>
                                <!--설문 타이틀-->
                                <div class="survey__head">
                                    <!--디폴트로 나와있는 타이틀-->
                                    <div class="survey__head-title u-block" id="divQuestionTitle">
                                    </div>
                                </div>

                            </section>

                            <!--첫 번째 설문-->
                            <div class="content__list _survey__content2" style="display: none;">
                                <div class="u-fs-30 u-co-mint u-fw--bold" ></div>
                            </div>
                            <!--두 번째 설문-->
                            <div class="content__list _survey__content2" style="display: none;">
                                <div class="u-fs-30 u-co-mint u-fw--bold"> </div>
                            </div>

                            <ul class="rate-content__list _question" id="rateQuestionResult" style="display: none;">


                            </ul>

                            <div class="c-button-wrap u-mt--24 _question" style="display: none;" >
                                <button id="btnRetryMyrat" class="restart c-button c-button--full c-button--h48 c-button-round c-button--white">다시하기</button>
                            </div>

                            <!-- 요금제 리스트 -->
                            <ul class="rate-content__list" id="rateContentCategory">


                            </ul>
                            <!-- 요금제 리스트 끝 -->

                            <ul class="rate-content__list" id="ulIsEmpty" style="display: none;">
                                <li class="rate-content__item no-compare"  >
                                <span class="inbox--bg">
                                    <i class="c-icon c-icon--badge" aria-hidden="true"></i>
                                </span>
                                    <p>요금제가 없습니다.</p>
                                </li>
                            </ul>


                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <!--[2021-12-02] 유틸클래스명 변경, [2021-12-15] 버튼명 변경-->
                            <button class="c-button c-button--gray c-button--lg u-width--120" data-dialog-close>취소</button>
                            <button class="c-button c-button--full c-button--primary is-disabled" id="paymentSelectBtn">요금제 선택하기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>

    <t:putAttribute name="popLayerAttr">



        <div class="c-modal" id="esim-check-modal" role="dialog" tabindex="-1" aria-describedby="esim-check-modal__title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="esim-check-modal__title">eSIM 사용 슬롯 확인</h1>
                        <button class="c-button" id="popClose" data-dialog-close>
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
                        <div class="c-table esim-slot">
                            <table>
                                <caption>구분, IMEI로 구성된 eSIM 정보</caption>
                                <colgroup>
                                    <col width="15%">
                                    <col>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th scope="col" class="u-pl--10">구분</th>
                                    <th scope="col" class="u-ta-center">IMEI</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="u-pl--10">EID</td>
                                    <td>
                                        <input type="text" class="c-input numOnly" id="eidTxt" name="eidTxt" value="" maxlength="32">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="u-pl--10">IMEI1</td>
                                    <td>
                                        <input type="text" class="c-input numOnly" id="imei1Txt" name="imei1Txt" value="" maxlength="15">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="u-pl--10">IMEI2</td>
                                    <td>
                                        <input type="text" class="c-input numOnly" id="imei2Txt" name="imei2Txt" value="" maxlength="15">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="u-pl--10">등록<br />이미지</td>
                                    <td>
                                        <div class="u-flex-center">
				                            <img id="preview" width="100%" alt="업로드 이미지" style="display:none;">
				                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="esim-slot-box__err" id="errTxt" style="display:none;"></div>

                        <div id="authArea" style="display:none;">
                            <h4 class="c-heading c-heading--type5">명의자 확인</h4>
                            <hr class="c-hr c-hr--type2">
                            <div class="c-form">
                                <div class="c-form__input" data-function-initialized="initialized">
                                    <input class="c-input" id="cstmrName" name="cstmrName" type="text" placeholder="이름 입력" value="" maxlength="60">
                                    <label class="c-form__label" for="cstmrName">이름</label>
                                </div>
                            </div>
                            <div class="c-form-field">
                                <div class="c-form__input-group">
                                    <div class="c-form__input c-form__input-division" data-function-initialized="initialized">
                                        <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" data-function-initialized="initialized">
                                        <span>-</span>
                                        <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" autocomplete="off" type="password" maxlength="7" value="" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" data-function-initialized="initialized">
                                        <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                                    </div>
                                </div>
                            </div>
                            <div class="c-form">
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-gray">eSIM 추가 개통을 원하실 경우 개통하시려는 기기에서 사용중인 휴대폰번호로 인증이 필요합니다.</li>
                                </ul>
                            </div>
                            <div class="c-button-wrap">
                                <button id="btnSmsAuth" class="c-button c-button--full c-button--mint">휴대폰 인증</button>
                            </div>
                        </div>

                        <div id="phoneArea" style="display:none;">
                            <h4 class="c-heading c-heading--type5">휴대폰 모델명 선택</h4>
                            <hr class="c-hr c-hr--type2">
                            <div class="c-form">
                                <div class="c-form__select" >
                                    <select class="c-select" id="phoneNm">
                                        <option value="">휴대폰 선택</option>
                                        <option value="MKAPP">애플</option>
                                        <option value="MKSAM">삼성</option>
                                        <option value="MKECT">기타</option>
                                    </select>
                                    <label class="c-form__label" for="phoneNm">휴대폰 선택</label>
                                </div>
                            </div>
                            <div class="c-form">
                                <div class="c-form__select" >
                                    <select class="c-select" id="phoneModel"></select>
                                    <label class="c-form__label" for="phoneModel">모델명 선택</label>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--gray" data-dialog-close="" id="reSetBtn">다시 등록하기</button>
                            <button class="c-button c-button--full c-button--primary" id="setBtn">확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

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

        <div class="c-modal" id="simpleDialog" role="dialog" tabindex="-1" aria-describedby="#simpleTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title _title" >제휴카드 발급 및 할인 적용 유의사항 안내</h1>
                    </div>
                    <div class="c-indicator">
                        <span style="width: 33.33%"></span>
                    </div>
                    <div class="c-modal__body">
                        <h3 class="c-heading c-heading--type1"></h3>
                        <p class="c-text c-text--type2 u-co-gray _detail">
                            ※ 꼭 확인해주세요.<br/><br/>
                            ① 제휴카드 발급은 휴대폰 개통 후 각 제휴카드 발급 사이트를 통해 별도로 신청 하셔야 합니다.<br/><br/>
                            ② 카드 수령 후  납부방법은 자동으로 등록되지 않습니다.<br/>
                            카드 발급 후 마이페이지 > 요금조회 및 납부 > 납부방법 변경에서 변경 해주셔야만 할인 적용이 가능합니다.
                        </p>
                        <div id="divCheckCaution" class="c-button-wrap _simpleDialogButton" style="flex-direction: column;">
                            <div class="c-agree__item">
                                <input class="c-checkbox" id="cautionFlag" type="checkbox">
                                <label class="c-label" for="cautionFlag">위 사항을 읽고, 이해하였습니다.</label>
                            </div>
                            <button class="c-button c-button--full c-button--primary _btnCheck" id="cautionCheck">확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </t:putAttribute>

</t:insertDefinition>