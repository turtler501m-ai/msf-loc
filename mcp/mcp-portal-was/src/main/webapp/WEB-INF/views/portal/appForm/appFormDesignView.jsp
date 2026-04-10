<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<t:insertDefinition name="layoutGnbDefault">
    <t:putAttribute name="metaTagAttr">
        <meta property="fb:app_id" content="1694564987490429">
        <meta property="og:type" content="website" />
        <meta property="og:title" content="가입조건 선택 | kt M모바일 공식 다이렉트몰" id="meta_og_title" />
        <meta property="og:url" content="https://www.ktmmobile.com/appForm/appFormDesignView.do" id="meta_og_url" />
        <meta property="og:description" content="" id="meta_og_description" />
        <meta property="og:image" content="https://${header['host']}/ktMmobile_og.png" />
        <meta name="keywords" content="${nmcp:getCodeDesc('MetaKeywords','01')}">
    </t:putAttribute>

    <t:putAttribute name="titleAttr">가입조건 선택</t:putAttribute>
    <t:putAttribute name="googleTagScript">
        <!-- Event snippet for 가입신청시작_PC (1) conversion page -->
        <script>
            gtag('event', 'conversion', {'send_to': 'AW-862149999/7VhHCKzjxLYZEO-6jZsD'});
        </script>
    </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/vendor/lottie.min.js"></script>
        <script type="text/javascript" src="/resources/js/portal/appForm/appFormDesignView.js?version=26.01.14"></script>
        <script type="text/javascript" src="/resources/js/sns.js"></script>
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script type="text/javascript" src="/resources/js/portal/appForm/eSimFormDesignView.js?version=23-11-17"></script>
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
            <div class="ly-page--title">
                <c:if test="${phoneProdBas.prodCtgType ne '03'}">
                    <h2 class="c-page--title">가입조건 선택</h2>
                </c:if>
                <c:if test="${phoneProdBas.prodCtgType eq '03'}">
                    <h2 class="c-page--title">자급제 가입조건 선택</h2>
                </c:if>
            </div>
            <div class="ly-content--wrap"></div>

                <div class="app-form-view-content">
                    <div class="app-form-view-wrap">
                        <div class="app-form-view-group">
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3 id="customerType">고객유형</h3>
                                </div>
                                <div class="app-form-view__form">
                                    <div class="c-form-wrap">
                                        <div class="c-form-group" role="group" aira-labelledby="customerType">
                                            <div class="c-form-wrap">
                                              <div class="c-form-group" role="group" aira-labelledby="cstmrTitle">
                                                <div class="c-checktype-row c-col3 u-mt--0">
                                                  <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType1" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NA}" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_NA }" > checked </c:if> >
                                                  <label class="c-label" for="cstmrType1">
                                                    <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                                                    <span class="u-ml--4">내국인</span>
                                                  </label>
                                                  <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType2" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NM}" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_NM }" > checked </c:if> <c:if test="${AppformReq.onOffType eq '5' }" > disabled </c:if>>
                                                  <label class="c-label" for="cstmrType2">
                                                    <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                                                    <span class="app-form-view__lable">미성년자<br/><span class="u-fs-14">(19세 미만)</span></span>
                                                  </label>
                                                  <input class="c-radio c-radio--button c-radio--button--type1" id="cstmrType3" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_FN}" <c:if test="${AppformReq.cstmrType eq Constants.CSTMR_TYPE_FN }" > checked </c:if> <c:if test="${AppformReq.onOffType eq '5' }" > disabled </c:if>>
                                                  <label class="c-label" for="cstmrType3">
                                                    <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                                                    <span class="app-form-view__lable">외국인<br/><span class="u-fs-14">(Foreigner)</span></span>
                                                  </label>
                                                </div>
                                              </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="app-form-view--row">
                                <div class="c-box c-box--type1 c-box--bullet _isLocal" style="display:none;">
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                        <li class="c-text-list__item">성인(19세 이상 내국인)은 셀프개통과 상담사 개통신청 모두 가능합니다.</li>
                                        <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                                            <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                                        </c:if>
                                    </ul>
                                </div>
                                <div class="c-box c-box--type1 c-box--bullet _isTeen" style="display:none;">
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                        <li class="c-text-list__item">미성년자(19세 미만 내국인)는 상담사 개통 신청만 가능합니다. (셀프개통 불가)</li>
                                        <li class="c-text-list__item">가입신청서 작성 완료 후, 법정대리인에게 순차적으로 연락하여 개통 안내를 드립니다.</li>
                                        <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                                            <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                                        </c:if>
                                    </ul>
                                </div>
                                <div class="c-box c-box--type1 c-box--bullet _isForeigner" style="display:none;">
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                        <li class="c-text-list__item">외국인은 상담사 개통신청만 가능합니다. (셀프개통 불가)</li>
                                        <li class="c-text-list__item">가입신청서 작성 완료 후, 순차적으로 연락하여 개통 안내를 드립니다. (안내는 한국어, 영어, 중국어로 제공됩니다.)</li>
                                        <c:if test="${AppformReq.cstmrName ne null and !empty AppformReq.cstmrName }">
                                            <li class="c-text-list__item">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <c:if test="${empty phoneProdBas.prodId or phoneProdBas.prodCtgType eq '03'}">
                            <div class="app-form-view-group" <c:out value="${phoneProdBas.prodCtgType eq '03' ? 'style=display:none;' : ''}"/>>
                                <div class="app-form-view">
                                    <div class="app-form-view__title">
                                        <h3 id="radOpenType">개통유형</h3>
                                    </div>
                                    <div class="app-form-view__form">
                                        <div class="c-form-wrap">
                                            <div class="c-form-group" role="group" aira-labelledby="radOpenType">
                                                <div class="c-checktype-row c-col2 u-mt--0 open-type">
                                                    <input class="c-radio c-radio--button" id="radOpenType1" type="radio" name="radOpenType" value="5">
                                                    <label class="c-label" for="radOpenType1">
                                                    <c:choose>
                                                        <c:when test="${prdtIndCd eq '10'}"><span class="app-form-view__lable">셀프개통<br/><span class="u-fs-14">(eSIM)</span></span></c:when>
                                                        <c:when test="${prdtIndCd eq '11'}"><span class="app-form-view__lable">셀프개통<br/><span class="u-fs-14">(유심있음)</span></span></c:when>
                                                        <c:otherwise><span class="app-form-view__lable">셀프개통<br/><span class="u-fs-14">(유심있음)</span></span></c:otherwise>
                                                    </c:choose>
                                                    </label>
                                                    <input class="c-radio c-radio--button" id="radOpenType2" type="radio" name="radOpenType" value="0">
                                                    <label class="c-label" for="radOpenType2"><span class="app-form-view__lable">상담사 개통 신청</span></label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="app-form-view--row" <c:out value="${phoneProdBas.prodCtgType eq '03' ? 'style=display:none;' : ''}"/>>
                                    <div class="c-box c-box--type1 c-box--bullet">
                                        <ul class="c-text-list c-bullet c-bullet--dot" id="selfTxt" style="display:none;">
                                            <c:choose>
                                                <c:when test="${prdtIndCd eq '10'}">
                                                    <li class="c-text-list__item">한 휴대폰에서 2회선 이용 시, 메인회선과 서브회선이 동일 명의가 아닐 경우 이용에 제약이 있을 수 있습니다.</li>
                                                    <li class="c-text-list__item">외국인, 미성년자는 셀프개통이 불가하므로 '상담사 개통 신청'을 이용 바랍니다.</li>
                                                    <li class="c-text-list__item">개통이 잘 안되시거나 어려운 부분이 있으시면 평일 09:00 ~ 18:00 고객센터(1899-5000/유료)로 연락 부탁드립니다.</li>
                                                    <li class="c-text-list__item">eSIM 개통을 위해서는 EID 등록이 선행되어야 하며, IMEI 및 32자리 EID 캡쳐화면 이미지 업로드가 필요합니다.</li>
                                                    <li class="c-text-list__item">이미지 업로드 시 자동으로 입력됩니다. <a class="c-text-link--bluegreen" data-dialog-trigger="#uploadguide-modal">이미지 가이드 보기</a></li>
                                                </c:when>
                                                <c:when test="${prdtIndCd eq '11'}">
                                                    <li class="c-text-list__item">외국인, 미성년자 신규가입은 셀프개통이 불가하므로 ‘상담사 개통 신청＇을 이용 바랍니다.</li>
                                                    <li class="c-text-list__item">현재 사용중인 회선의 납부 방법이 ‘지로’일 경우 ‘상담사 개통 신청’으로만 개통 가능합니다.</li>
                                                    <li class="c-text-list__item">개통이 잘 안되시거나 어려운 부분이 있으시면 평일 09:00 ~ 18:00 고객센터(1899-5000/유료)로 연락 부탁드립니다.</li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="c-text-list__item">유심을 보유하고 있으시다면 셀프개통이 가능합니다.</li>
                                                    <li class="c-text-list__item">유심이 없으신 고객님은 다이렉트몰 구매하기를 이용하시거나, 편의점 또는 오픈마켓에서 유심 구매 후 셀프개통을<br />진행해주세요.</li>
                                                    <li class="c-text-list__item">유심 구매처 안내
                                                        <a class="c-text c-text--underline u-co-sub-3 u-ml--4" href="/appForm/reqSelfDlvry.do">다이렉트몰</a>
                                                        <a class="c-text c-text--underline u-co-sub-3 u-ml--4" href="/direct/storeInfo.do">편의점/마트</a>
                                                        <a class="c-text c-text--underline u-co-sub-3 u-ml--4" href="/direct/openMarketInfo.do">오픈마켓</a>
                                                    </li>
                                                    <li class="c-text-list__item">외국인, 미성년자는 셀프개통이 불가하므로 '상담사 개통 신청'을 이용 바랍니다.</li>
                                                    <li class="c-text-list__item">개통이 잘 안되시거나 어려운 부분이 있으시면 평일 09:00 ~ 18:00 고객센터(1899-5000/유료)로 연락 부탁드립니다.</li>
                                                    <li class="c-text-list__item">
                                                        <span class="u-co-sub-4">부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.</span>
                                                        <br /><span class="u-fs-15">예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한</span>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </ul>
                                        <ul class="c-text-list c-bullet c-bullet--dot" id="counselTxt" style="display: none;">
                                            <c:choose>
                                                <c:when test="${prdtIndCd eq '10'}">
                                                    <li class="c-text-list__item">한 휴대폰에서 2회선 이용 시, 메인회선과 서브회선이 동일 명의가 아닐 경우 이용에 제약이 있을 수 있습니다.</li>
                                                    <li class="c-text-list__item">가입신청서를 작성하시면 순차적으로 AI상담사 또는 개통상담사가 전화를 드려 본인확인을 진행합니다.</li>
                                                </c:when>
                                                <c:when test="${prdtIndCd eq '11'}">
                                                    <li class="c-text-list__item">가입신청서를 작성하시면 순차적으로 AI상담사 또는 개통상담사가 전화를 드려 본인확인을 진행합니다.</li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="c-text-list__item">가입신청서를 작성하시면 순차적으로 AI상담사 또는 개통상담사가 전화를 드려 본인확인을 진행합니다.</li>
                                                    <li class="c-text-list__item">3회 이상 통화가 되지 않을 경우 신청정보는 삭제됩니다.</li>
                                                    <li class="c-text-list__item">휴대폰(단말) 요금제는 셀프개통이 불가합니다.</li>
                                                    <li class="c-text-list__item">
                                                        <span class="u-co-sub-4">부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.</span>
                                                        <br /><span class="u-fs-15">예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한</span>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <c:if test="${prdtIndCd eq '10'}">
                                <div class="app-form-view-group">
                                    <div class="app-form-view align-top">
                                        <div class="app-form-view__title u-mt--18">
                                            <h3>휴대폰 정보 등록</h3>
                                        </div>
                                        <div class="app-form-view__form">
                                            <div class="c-form-group" role="group" aira-labelledby="eSimePhoneInfo">
                                                <div class="c-form-row c-col2">
                                                    <div class="c-form__input-group is-readonly u-mt--0">
                                                        <div class="c-form__input c-form__input-division">
                                                            <input class="c-input--div" id="modelNm" name="modelNm" type="text" placeholder="휴대폰 모델명" value="" readOnly>
                                                            <label class="c-form__label" for="modelNm">휴대폰 모델명</label>
                                                        </div>
                                                    </div>
                                                    <div class="c-form__input-group is-readonly u-mt--0">
                                                        <div class="c-form__input c-form__input-division">
                                                            <input class="c-input--div" id="eid" name="eid" type="text" placeholder="EID" readOnly >
                                                            <label class="c-form__label" for="eid">EID</label>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="c-form-row c-col2">
                                                    <div class="c-form__input-group is-readonly">
                                                        <div class="c-form__input c-form__input-division">
                                                            <input class="c-input--div" id="imei1" name="imei1" type="text" placeholder="IMEI1" readOnly>
                                                            <label class="c-form__label" for="imei1">IMEI1</label>
                                                        </div>
                                                    </div>
                                                    <div class="c-form__input-group is-readonly">
                                                        <div class="c-form__input c-form__input-division">
                                                            <input class="c-input--div" id="imei2" name="imei2" type="text" placeholder="IMEI2" readOnly>
                                                            <label class="c-form__label" for="imei2">IMEI2</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="app-form-view-group u-mt--16">
                                    <div class="app-form-view--row">
                                        <div class="c-button-wrap">
                                            <input type="file" class="c-hidden ocrImg" id="image" name="image" accept="image/jpg, image/png, image/jpeg, image/tif, image/tiff, image/bmp" >
                                            <label for="image" class="c-button c-button-round--md c-button--mint u-width--460">이미지 등록</label>
                                        </div>
                                    </div>
                                </div>
                            </c:if>


                            <div class="app-form-view-group">
                                <div class="app-form-view">
                                    <div class="app-form-view__title">
                                        <h3 id="operType">가입유형</h3>
                                    </div>
                                    <div class="app-form-view__form">
                                        <div class="c-form-wrap">
                                            <div class="c-form-group" role="group" aira-labelledby="operType">
                                                <div class="c-checktype-row c-col2 u-mt--0 open-type" id="regiTypeList"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="app-form-view-group">
                                <div class="app-form-view">
                                    <div class="app-form-view__title">
                                        <h3 id="radServType">요금제 유형</h3>
                                    </div>
                                    <div class="app-form-view__form">
                                        <div class="c-form-wrap">
                                            <div class="c-form-group" role="group" aira-labelledby="radServType">
                                                <div class="c-checktype-row c-col3 u-mt--0">
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
                                        <div id="modelList" style="display: none;"></div>
                                        <div id="selAgreeAmt" style="display: none;"></div>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${not empty phoneProdBas.prodId and phoneProdBas.prodCtgType ne '03'}">
                            <div class="app-form-view-group">
                                <div class="app-form-view--row">
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
                            <div class="app-form-view-group">
                                <div class="app-form-view">
                                    <div class="app-form-view__title">
                                        <h3 id="radOpenType">개통유형</h3>
                                    </div>
                                    <div class="app-form-view__form">
                                        <div class="c-form-group" role="group" aira-labelledby="radOpenType">
                                            <div class="c-checktype-row c-col2 u-mt--0 open-type">
                                                <input class="c-radio c-radio--button" id="radOpenType1" type="radio" name="radOpenType" disabled>
                                                <label class="c-label" for="radOpenType1"><span class="app-form-view__lable">셀프개통<br/><span class="u-fs-14">(유심있음)</span></span></label>
                                                <input class="c-radio c-radio--button" id="radOpenType2" type="radio" name="radOpenType" value="3" checked>
                                                <label class="c-label" for="radOpenType2"><span class="app-form-view__lable">상담사 개통 신청</span></label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="app-form-view--row">
                                    <div class="c-box c-box--type1 c-box--bullet">
                                        <ul class="c-text-list c-bullet c-bullet--dot">
                                            <li class="c-text-list__item">신청서 작성 후 상담사가 개통을 도와드립니다.</li>
                                            <li class="c-text-list__item">휴대폰 요금제는 셀프개통이 불가합니다.</li>
                                            <li class="c-text-list__item">
                                                <span class="u-co-sub-4">부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.</span>
                                                   <br /><span class="u-fs-15">예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한</span>
                                              </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="app-form-view-group">
                                <div class="app-form-view">
                                    <div class="app-form-view__title">
                                        <h3 id="radJoinType">가입유형</h3>
                                    </div>
                                    <div class="app-form-view__form">
                                        <div class="c-form-wrap">
                                            <div class="c-form-group" role="group" aira-labelledby="radJoinType">
                                                <div class="c-checktype-row c-col2 u-mt--0">
                                                    <c:if test="${mspSaleDto.mspSalePlcyMstDtoSimbol.mnpYn == 'Y'}">
                                                        <input class="c-radio c-radio--button" id="radRegistType1" type="radio" operName="번호이동" name="operType" <c:if test="${operType == PhoneConstant.OPER_PHONE_NUMBER_TRANS }"> checked </c:if> value="${PhoneConstant.OPER_PHONE_NUMBER_TRANS }" onchange="operTypeTxtToggle();">
                                                        <label class="c-label" for="radRegistType1">번호이동</label>
                                                    </c:if>
                                                    <c:if test="${mspSaleDto.mspSalePlcyMstDtoSimbol.newYn == 'Y'}">
                                                        <input class="c-radio c-radio--button" id="radRegistType2" type="radio" operName="신규가입" name="operType" <c:if test="${operType == PhoneConstant.OPER_NEW }"> checked </c:if> value="${PhoneConstant.OPER_NEW }" onchange="operTypeTxtToggle();">
                                                        <label class="c-label" for="radRegistType2">신규가입</label>
                                                    </c:if>
                                                    <c:if test="${mspSaleDto.mspSalePlcyMstDtoSimbol.hdnYn == 'Y'}">
                                                        <input class="c-radio c-radio--button" id="radRegistType3" type="radio" operName="기기변경" name="operType" <c:if test="${operType == Constants.OPER_TYPE_EXCHANGE }"> checked </c:if> value="${Constants.OPER_TYPE_EXCHANGE}" onchange="operTypeTxtToggle();">
                                                        <label class="c-label" for="radRegistType3">기기변경</label>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="app-form-view--row">
                                    <!--번호이동 선택 시-->
                                    <div class="c-box c-box--type1 c-box--bullet" id="movTxt">
                                        <ul class="c-text-list c-bullet c-bullet--dot">
                                            <li class="c-text-list__item">쓰던 번호 그대로 통신사를 kt M모바일로 바꿀 수 있어요.</li>
                                        </ul>
                                    </div>
                                    <!--신규가입 선택 시-->
                                    <div class="c-box c-box--type1 c-box--bullet u-mt--20" id="newTxt" style="display:none;">
                                        <ul class="c-text-list c-bullet c-bullet--dot">
                                            <li class="c-text-list__item">새로운 번호로 개통합니다.</li>
                                        </ul>
                                    </div>
                                    <!-- 기기변경 선택 시-->
                                    <div class="c-box c-box--type1 c-box--bullet u-mt--20" id="chgTxt" style="display:none;">
                                        <ul class="c-text-list c-bullet c-bullet--dot">
                                            <li class="c-text-list__item">기기변경은 기존 엠모바일 고객님이 휴대폰만 새로 바꾸고 사용하던 정보는 그대로 유지하는 것을 의미합니다.</li>
                                            <li class="c-text-list__item">기기변경 신청을 위해 kt M모바일 고객 인증이 필요합니다</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${phoneProdBas.prodCtgId eq '45'}">
                                <div class="app-form-view-group">
                                    <div class="app-form-view">
                                        <div class="app-form-view__title">
                                            <h3 id="radServiceType">요금제 유형</h3>
                                        </div>
                                        <div class="app-form-view__form">
                                            <div class="c-form-wrap">
                                                <div class="c-form-group" role="group" aira-labelledby="radServiceType">
                                                    <div class="c-checktype-row c-col3 u-mt--0">
                                                        <input class="c-radio c-radio--button" id="serviceType0" type="radio" name="dataType" value="LTE" <c:if test="${ProdCommendDto.prdtSctnCd == Constants.LTE_FOR_MSP}"> checked </c:if>/>
                                                        <label class="c-label" for="serviceType0">LTE</label>
                                                        <input class="c-radio c-radio--button" id="serviceType1" type="radio" name="dataType" value="5G" <c:if test="${ProdCommendDto.prdtSctnCd == Constants.FIVE_G_FOR_MSP}"> checked </c:if>/>
                                                        <label class="c-label" for="serviceType1">5G</label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <div class="app-form-view-group">
                                <div class="app-form-view">
                                    <div class="app-form-view__title">
                                        <h3 id="radContTermHead">요금제 약정기간</h3>
                                    </div>
                                    <div class="app-form-view__form">
                                        <div class="c-form-wrap">
                                            <div class="c-form-group" role="group" aira-labelledby="radContTermHead">
                                                <c:forEach var="item" items="${mspSaleAgrmMstList}" varStatus="status">
                                                    <c:if test="${(status.index+1) % 2 eq 0}">
                                                        <div class="c-checktype-row c-col2 c-col2--w460">
                                                    </c:if>
                                                    <c:if test="${status.index eq 0}">
                                                        <div class="c-checktype-row c-col2 c-col2--w460">
                                                        <input class="c-radio c-radio--button" id="radPeriodType99" type="radio" name="instNom" value="0" <c:if test="${instNom == '0' or empty instNom}"> checked </c:if>/>
                                                        <label class="c-label" for="radPeriodType99">무약정</label>
                                                    </c:if>
                                                    <input class="c-radio c-radio--button" id="radPeriodType${status.index}" type="radio" name="instNom" value="${item.instNom}" <c:if test="${instNom == item.instNom}"> checked </c:if>>
                                                    <label class="c-label" for="radPeriodType${status.index}">${item.instNom}<c:if test="${item.instNom ne '0'}">개월</c:if></label>
                                                    <c:if test="${(status.index) % 2 eq 0 || fn:length(mspSaleAgrmMstList) - 1 eq status.index}">
                                                        </div>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="app-form-view--row">
                                    <div class="c-box c-box--type1 c-box--bullet u-mt--20">
                                        <ul class="c-text-list c-bullet c-bullet--dot">
                                            <li class="c-text-list__item">선택한 할부 기간에 따라 월 납부금액이 달라질 수 있습니다.</li>
                                            <li class="c-text-list__item">무약정 선택 시 하단 할인유형을 선택할 수 없습니다.</li>
                                            <li class="c-text-list__item">의무사용기간을 조건으로 보조금을 지급받은 고객은 의무사용기간 종료 전에 계약을 해지(요금미납, 단말기 파손 등으로 해지하는 경우 포함)할 경우 회사가 별도로 정하는 위약금을 납부하여야 합니다.</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="app-form-view-group">
                                <div class="app-form-view">
                                    <div class="app-form-view__title">
                                        <h3>단말기 할부기간</h3>
                                    </div>
                                    <div class="app-form-view__form">
                                        <div class="c-form u-width--460">
                                            <label class="c-label c-hidden" for="agrmTrm">단말기 할부기간</label>
                                            <select class="c-select" name="agrmTrm" id="agrmTrm"></select>
                                        </div>
                                    </div>
                                </div>
                                <div class="app-form-view--row">
                                    <div class="c-box c-box--type1 c-box--bullet u-mt--20">
                                        <ul class="c-text-list c-bullet c-bullet--dot">
                                            <li class="c-text-list__item">요금제 무약정 선택 시 일시 납부, 24개월, 30개월, 36개월 중 선택할 수 있습니다.</li>
                                            <li class="c-text-list__item">단말기 할부기간은 요금제 약정기간과 동일한 기간만 선택할 수 있습니다.</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="app-form-view-group">
                                <div class="app-form-view">
                                    <div class="app-form-view__title">
                                        <h3 id="chkSaleTypeHead">할인 유형</h3>
                                    </div>
                                    <div class="app-form-view__form">
                                        <div class="c-form-wrap">
                                            <div class="c-form-group" role="group" aira-labelledby="chkSaleTypeHead">
                                                <div class="c-checktype-row c-col2">
                                                    <input class="c-radio c-radio--button" id="chkDiscountType1" type="radio" name="saleTy" value="KD" <c:if test="${sprtTp eq PhoneConstant.PHONE_DISCOUNT_FOR_MSP or empty sprtTp}">checked="checked"</c:if>>
                                                    <label class="c-label" for="chkDiscountType1">단말 할인</label>
                                                    <input class="c-radio c-radio--button" id="chkDiscountType2" type="radio" name="saleTy" value="PM" <c:if test="${sprtTp eq PhoneConstant.CHARGE_DISCOUNT_FOR_MSP }">checked="checked"</c:if>>
                                                    <label class="c-label" for="chkDiscountType2">요금 할인</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="app-form-view--row">
                                    <div class="c-box c-box--type1 c-box--bullet u-mt--20">
                                        <ul class="c-text-list c-bullet c-bullet--dot">
                                            <li class="c-text-list__item u-co-gray">단말할인: 공통지원금을 지원받는 경우</li>
                                            <li class="c-text-list__item u-co-gray">요금할인: 공통지원금을 받지 않고 요금에 대한 지원을 받는 경우</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <div class="app-form-view-group">
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3>요금제 선택</h3>
                                </div>
                                <div class="app-form-view__form">
                                    <!-- 요금제 선택 전 -->
                                    <div class="sel-fee-wrap" id="radPayTypeA">
                                        <div class="c-box c-box--dotted">
                                            <button class="c-button" id="radPayTypeBtn">
                                                <i class="c-icon c-icon--plus" aria-hidden="true"></i>
                                                <span class="u-co-gray">요금제를 선택해 주세요.</span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- 요금제 선택 후 New / id="choicePay", id="choicePayTxt" html 구조가 바껴셔 빼 놓음 -->
                                    <div class="sel-fee-wrap rate-active" id="radPayTypeB" style="display:none;">
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
                                                          <span class="max-data-label">
                                                                <em  id="choicePayTxt10"></em>
                                                          </span>
                                                            <span id="choicePayTxt1"></span>
                                                            <em id="choicePayTxt2"></em>
                                                        </div>
                                                    </div>
                                                    <div class="sel-fee-info">
                                                        <div class="sel-fee-info__choice-wrap">
                                                            <div class="sel-fee-info__choice">
                                                                <div>
                                                                    <i class="c-icon c-icon--call-type4" aria-hidden="true"></i>
                                                                </div>
                                                                <div>
                                                                    <span  id="choicePayTxt3"></span>
                                                                    <em  id="choicePayTxt4"></em>
                                                                </div>
                                                            </div>
                                                            <div class="sel-fee-info__choice">
                                                                <div>
                                                                    <i class="c-icon c-icon--msg-type5" aria-hidden="true"></i>
                                                                </div>
                                                                <div>
                                                                    <span id="choicePayTxt5"></span>
                                                                    <em id="choicePayTxt6"></em>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="sel-fee-benefit" id="choicePayTxt9">
                                                        <span class="sel-fee-benefit__title"  id="choicePayTxt8" ></span>
                                                        <em id="choicePayTxt7"></em>
                                                    </div>
                                                </dd>
                                            </dl>
                                        </div>
                                        <div class="c-box c-box--dotted rate-active paymentPop" id="radPayTypeC" style="display:none;">
                                            <button class="c-button">
                                                <i class="c-icon c-icon--plus" aria-hidden="true"></i>
                                                <span class="u-co-gray fs-16">다른 요금제 고르기</span>
                                            </button>
                                        </div>
                                    </div>
                                    <!-- 요금제 선택 후, 기존 소스 -->
                                    <!-- <div class="sel-fee-wrap c-flex">
                                        <div class="sel-fee-wrap__item u-width--460" id="radPayTypeB" style="display:none;">
                                            <dl>
                                                <dt class="sel-fee-wrap__title" id="choicePay">

                                                </dt>
                                                <dd class="sel-fee-wrap__text" id="choicePayTxt">

                                                </dd>
                                            </dl>
                                        </div>
                                        <div class="c-box c-box--dotted u-width--460 paymentPop" id="radPayTypeC" style="display:none;" >
                                            <button class="c-button">
                                                <span class="u-co-gray fs-16">요금제 다시 선택하기</span>
                                            </button>
                                        </div>
                                    </div> -->
                                </div>
                            </div>
                        </div>

                        <!-- 이벤트 코드 영역 -->
                        <div class="app-form-view-group" id="evntCdPrmtWrap" style="display: none;">
                            <div class="app-form-view align-top">
                                <div class="app-form-view__title">
                                    <h3>이벤트 코드</h3>
                                </div>
                                <div class="app-form-view__form">
                                    <p class="u-mb--16">해당 요금제는 이벤트 코드 적용이 가능합니다. 이벤트에서 확인한 코드가 있다면 입력 해 주세요.<br />※ 지급 조건은 프로모션별로 상이합니다.</p>
                                    <div class="c-input-wrap u-mt--0">
                                        <input class="c-input u-width--404" name="evntCdPrmt" id="evntCdPrmt" type="text" placeholder="이벤트 코드 입력" maxlength="50" onkeyup="nextStepBtnChk();">
                                        <label class="c-form__label sr-only" for="evntCdPrmt">이벤트 코드</label>
                                        <button id="evntCdPrmtBtn" class="c-button c-button--lg c-button--mint u-width--85 u-fs-16 u-ml--5" onclick="checkEvntCdPrmt();">확인</button>
                                        <button id="evntCdTryBtn" class="c-button c-button--lg c-button--mint u-width--85 u-fs-16 u-ml--5 is-disabled" onclick="tryEvntCdPrmt();">재입력</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- 사은품 영역 -->
                        <div class="app-form-view-group" id="divGift"  style="display:none;">
                            <div class="app-form-view align-top">
                                <div class="app-form-view__title">
                                    <h3>가입 사은품 안내</h3>
                                    <p class="u-co-sub-4">선택 사은품이 있는 경우<br/>신청 마지막 단계에서<br/>선택 가능</p>
                                </div>
                                <div class="app-form-view__form">
                                    <div class="c-box c-box--type1 c-box--bullet u-mt--0" id="divGift2">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 사은품 리스트 기존 소스, 일단 주석해 놓음, 불필요 시 삭제 -->
                        <!-- <section id="divGift" class="gift-section" style="display:none;"></section> -->

                        <!--  트리플할인 영역 -->
                        <div class="app-form-view-group _ktTripleDcCss" aira-labelledby="ktDcRateForm" style="display:none;">
                            <div class="app-form-view">
                                <div class="app-form-view__title">
                                    <h3 id="ktDcRateForm">KT인터넷 트리플<br />할인<span>(선택)</span></h3>
                                </div>
                                <div class="app-form-view__form">
                                    <div class="c-checktype-row c-col2">
                                        <input class="c-radio c-radio--button" id="ktTripleDcAmt_01" type="radio" name="ktTripleDcAmt" value="" checked="checked">
                                        <label class="c-label" for="ktTripleDcAmt_01">선택 안함</label>
                                        <input class="c-radio c-radio--button" id="ktTripleDcAmt_02" type="radio" name="ktTripleDcAmt" value=""  >
                                        <label class="c-label" for="ktTripleDcAmt_02"  id="laKtTripleDcAmt_02">요금할인 24개월(0원)</label>
                                    </div>
                                </div>
                            </div>
                            <div class="app-form-view--row">
                                <div class="c-box c-box--type1 c-box--bullet">
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                        <li class="c-text-list__item">트리플할인 페이지에서 KT인터넷 상담신청 및 개통 후 마이페이지에서 신청 시 받을 수 있는 요금할인 혜택입니다.</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!--  제휴카드 영역 -->
                        <div class="app-form-view-group cprt-card" id="cprtForm" style="display:none;">
                            <div class="app-form-view align-top">
                                <div class="app-form-view__title">
                                    <h3 id="radCardTypeHead">제휴카드 할인<span>(선택)</span></h3>
                                    <p>제휴카드로 통신료 자동 이체 시 받을 수 있는 할인 혜택입니다.</p>
                                    <!--  cprtCardPanel.jsp에서 그대로 가져왔는데 사용해도 문제 없는지 확인이 필요 함, 일단 동작은 됨 -->
                                    <button type="button" onclick="javascript:openPage('pullPopup', '/event/cprtCardBnfitLayer.do?cprtCardCtgCd=${cprtCardCtgCd}', '', 0);">비교하기</button>
                                </div>
                                <div class="app-form-view__form">
                                    <div class="c-form-group" role="group" aira-labelledby="radCardTypeHead" id="cprtDiv"></div>
                                    <div class="c-button-wrap" id="moreBtnDiv" style="display: none;">
                                        <input type="hidden" id="cprtCardSize"/>
                                        <button class="c-button c-button--more">
                                            <i class="c-icon c-icon--more" aria-hidden="true"></i>
                                            <span>더보기</span>
                                            <span class="c-button__sub" id="moreBtnTxt1">
                                                <span class="c-hidden">현재 노출된 항목</span>
                                            </span>
                                            <span class="c-button__sub" id="moreBtnTxt2">
                                                <span class="c-hidden">전체 항목</span>
                                            </span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="app-form-view-group">
                            <div class="app-form-view--row">
                                <div class="c-button-wrap u-mt--56">
                                    <button class="c-button c-button--share" data-dialog-trigger="#modalShareAlert">
                                        <span class="c-hidden">공유하기</span>
                                        <i class="c-icon c-icon--share" aria-hidden="true"></i>
                                    </button>
                                    <button class="c-button c-button--lg c-button--red u-width--460" onclick="nextStep();" id="nextStepBtn">가입하기</button>
                                </div>
                            </div>
                        </div>
                    </div>
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
                                    <c:choose>
                                        <c:when test="${not empty phoneProdBas.prodId or phoneProdBas.prodCtgType eq '03'}">
                                            <c:set var="imgCnt" value="0"/>
                                            <c:forEach items="${phoneProdBas.rprtPhoneProdImgDto.phoneProdImgDetailDtoList}" var="imgItem">
                                                <c:if test="${imgItem.imgTypeCd != '04' and imgCnt eq '0'}">
                                                    <img class="usim-img" src="${imgItem.imgPath}" alt="${phoneProdBas.prodNm} 실물 사진 앞면" onerror="this.src='/resources/images/portal/content/img_phone_noimage.png';">
                                                    <c:set var="imgCnt" value="${imgCnt + 1}"/>
                                                </c:if>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>

                                            <c:choose>
                                                <c:when test="${prdtIndCd eq '10'}">
                                                    <img class="usim-img" src="/resources/images/portal/content/esim_icon.jpg" alt="kt M mobile eSIM 양심">
                                                </c:when>
                                                <c:when test="${prdtIndCd eq '11'}">
                                                    <img class="usim-img" src="/resources/images/portal/content/esim_icon.jpg" alt="kt M mobile eSIM 양심">
                                                </c:when>
                                                <c:otherwise>
                                                    <img class="usim-img" src="/resources/images/portal/content/img_usim@2x.png" alt="유심 실물 사진">
                                                </c:otherwise>
                                            </c:choose>



                                        </c:otherwise>
                                    </c:choose>
                                    <div class="inner-box">
                                        <c:if test="${not empty phoneProdBas.prodId or phoneProdBas.prodCtgType eq '03'}">
                                            <p class="c-text c-text--fs18 u-fw--medium">
                                                ${phoneProdBas.prodNm}
                                                <c:forEach var="sntyItem" items="${phoneProdBas.phoneSntyBasDtoListY}" varStatus="status">
                                                    <c:if test="${status.index eq 0}">
                                                        / ${sntyItem.atribValNm2}
                                                    </c:if>
                                                    <c:if test="${sntyItem.hndsetModelId == hndsetModelId }">
                                                        / ${sntyItem.atribValNm1}
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${phoneProdBas.prodCtgType eq '03'}">
                                                    <input type="hidden" id="hndsetModelId" value="${hndsetModelId}"/>
                                                </c:if>
                                            </p>
                                        </c:if>
                                        <p class="c-text c-text--fs18 u-fw--medium" id="bottomTitle1"></p>
                                        <p class="c-text c-text--fs16 u-co-gray" id="bottomTitle2"></p>
                                    </div>
                                </div>
                                <div class="fee-compare-wrap">
                                    <div class="fee-compare-wrap__item">
                                        <div class="u-co-red">
                                            <span class="c-text c-text--fs14">월 예상 납부금액</span>
                                            <span class="c-text c-text--fs32 c-text--bold u-ml--12" id="totalPriceBottom">0</span>
                                            <span class="c-text c-text--fs18 c-text--bold">원</span>
                                            <input type="hidden" name="settlAmt" id="settlAmt"/>
                                        </div>
                                        <p class="c-text c-text--fs13 u-co-gray">가입비 및 유심비 등 기타요금은 별도 청구됩니다</p>
                                    </div>
                                    <button id="comparePopTrigger" data-dialog-trigger="#comparePop" style="display:none;"></button>

                                </div>
                            </div>
                        </div>
                        <div class="c-bs-compare__detail" id="acc_content_1" aria-hidden="true">
                            <div class="top-sticky-banner">
                                <div class="swiper-banner" id="swiperBanner">
                                    <div class="swiper-container">
                                        <div class="swiper-wrapper" id="O001"></div>
                                    </div>
                                    <button class="swiper-button-next" type="button"></button>
                                    <button class="swiper-button-prev" type="button"></button>
                                </div>
                            </div>
                            <div class="c-row c-col2-row c-scrolly-auto">
                                <div class="c-col2-left">
                                    <div class="c-addition-wrap">
                                        <!-- [2022-01-20] 마크업 수정-->
                                        <strong class="c-addition c-addition--type1">가입정보</strong>
                                        <!--
                                        <dl class="c-addition c-addition--type2">
                                            <dt>고객구분</dt>
                                            <dd class="u-ta-right">내국인</dd>
                                        </dl>
                                        -->
                                        <dl class="c-addition c-addition--type2" id="joinInfoDl1" style="display:none;">
                                            <dt>가입유형</dt>
                                            <dd class="u-ta-right" id="joinInfo1"></dd>
                                        </dl>
                                        <dl class="c-addition c-addition--type2" id="joinInfoDl2" style="display:none;">
                                            <dt>약정기간</dt>
                                            <dd class="u-ta-right" id="joinInfo2"></dd>
                                        </dl>
                                        <dl class="c-addition c-addition--type2" id="joinInfoDl3" style="display:none;">
                                            <dt>할부기간</dt>
                                            <dd class="u-ta-right" id="joinInfo3"></dd>
                                        </dl>
                                        <dl class="c-addition c-addition--type2" id="joinInfoDl4" style="display:none;">
                                            <dt>서비스유형</dt>
                                            <dd class="u-ta-right" id="joinInfo4"></dd>
                                        </dl>
                                        <dl class="c-addition c-addition--type2" id="joinInfoDl5" style="display:none;">
                                            <dt>요금제</dt>
                                            <dd class="u-ta-right" id="joinInfo5"></dd>
                                        </dl>
                                        <c:if test="${not empty phoneProdBas.prodId and phoneProdBas.prodCtgType ne '03'}">
                                            <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
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
                                        </c:if>

                                        <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
                                        <dl class="c-addition c-addition--type1 bottomTop">
                                            <dt>월 통신요금</dt>
                                            <dd class="u-ta-right"><b id="totalPrice2">0</b>원</dd>
                                        </dl>
                                        <dl class="c-addition c-addition--type2 bottomTop">
                                            <dt>기본요금</dt>
                                            <dd class="u-ta-right" id="baseAmt">0 원</dd>
                                        </dl>
                                        <dl class="c-addition c-addition--type2 bottomTop" id="bottomDefDc">
                                            <dt>
                                                <a href="#n" role="button" data-tpbox="#deli_tp1" aria-describedby="#deli-tp1__title">
                                                    기본할인<i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                                </a>
                                                <div class="c-tooltip-box is-active" id="deli_tp1" role="tooltip" style="left: 60px">
                                                    <div class="c-tooltip-box__title c-hidden" id="deli-tp1__title">기본할인 상세설명</div>
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
                                                <a href="#n" role="button" data-tpbox="#deli_tp2" aria-describedby="#deli-tp2__title">
                                                    요금할인<i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                                </a>
                                                <div class="c-tooltip-box is-active" id="deli_tp2" role="tooltip" style="left: 60px">
                                                    <div class="c-tooltip-box__title c-hidden" id="deli-tp2__title">요금할인 상세설명</div>
                                                    <div class="c-tooltip-box__title">요금할인</div>
                                                    <div class="c-tooltip-box__content">약정 - 할인 선택시 제공되는 요금할인 혜택입니다.</div>
                                                </div>
                                            </dt>
                                            <dd class="u-ta-right">
                                                <b> <em id="addDcAmt">0 원</em></b>
                                            </dd>
                                        </dl>
                                        <dl class="c-addition c-addition--type2 bottomTop">
                                            <dt>프로모션 할인</dt>
                                            <dd class="u-ta-right">
                                                <b> <em id="promotionDcAmtTxt">0 원</em></b>
                                            </dd>
                                        </dl>

                                        <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">

                                        <!-- 트리플할인  할인금액 -->
                                        <dl class="c-addition c-addition--type1 _ktTripleDcCss" style="display:none;">
                                            <dt>KT인터넷 트리플할인</dt>
                                            <dd class="u-ta-right"><b class="u-co-mint" id="ktTripleDcAmtTxt">0 원</b></dd>
                                        </dl>

                                        <dl class="c-addition c-addition--type1 bottomTop">
                                            <dt>
                                                <a href="#n" role="button" data-tpbox="#deli_tp3" aria-describedby="#deli-tp3__title">
                                                      제휴카드할인<i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                                </a>
                                                <div class="c-tooltip-box is-active" id="deli_tp3" role="tooltip" style="left: 60px">
                                                    <div class="c-tooltip-box__title c-hidden" id="deli-tp3__title">요금할인 상세설명</div>
                                                    <div class="c-tooltip-box__title">제휴카드할인</div>
                                                    <div class="c-tooltip-box__content">
                                                        <ul class="c-text-list c-bullet c-bullet--dot">
                                                            <li class="c-text-list__item">요금 설계 시 선택한 제휴카드에 따른 할인입니다.</li>
                                                            <li class="c-text-list__item">제휴카드 할인은 각 카드사별 이용 조건이 반영되지 않은 최대 할인 금액으로 적용되어 있습니다.<br />정확한 할인 조건은 이벤트 페이지의 각 제휴카드 정보를 꼭 확인하시기 바랍니다.</li>
                                                            <li class="c-text-list__item">선택한 카드와 다른 카드 혹은 납부 방법 변경 시 실제 할인 금액과 다를 수 있습니다.</li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </dt>
                                            <dd class="u-ta-right"><b class="u-co-mint" id="cprtCardPrice">0 원</b></dd>
                                        </dl>

                                        <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
                                        <dl class="c-addition c-addition--type1 bottomMiddle">
                                            <dt>기타요금</dt>

                                        </dl>
                                        <dl class="c-addition c-addition--type2 bottomMiddle">
                                            <dt>가입비(3개월 분납)</dt>
                                            <dd class="u-ta-right" id="joinPriceTxt">
                                                <span class="c-text ">0 원</span>
                                            </dd>
                                        </dl>

                                        <c:choose>
                                        <c:when test="${prdtIndCd eq '10'}">
                                            <dl class="c-addition c-addition--type2" >
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
                                            <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
                                            <dl class="c-addition c-addition--type1 bottomMiddle">
                                                <dt>자급제</dt>
                                                <dd class="u-ta-right">
                                                    <b><fmt:formatNumber value="${phoneProdBas.selPric}" pattern="#,###"/> </b>원
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
                                                </dd>
                                            </dl>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="c-col2-right">
                                    <div class="c-box c-box--type1">
                                        <div class="c-addition-wrap">
                                            <dl class="c-addition c-addition--type1 c-addition--sum">
                                                <dt>
                                                    <b>월 납부금액</b>
                                                    <span class="c-text c-text--fs14 u-co-gray">(부가세, 포함)</span>
                                                </dt>
                                                <dd class="u-ml--auto u-ta-right">
                                                    <b id="totalPrice">0</b> 원
                                                </dd>
                                            </dl>
                                            <!-- [2022-01-20] 마크업 수정-->
                                            <p class="c-text c-text--fs15 u-co-gray" id="bottomTitle"></p>
                                            <hr class="c-hr c-hr--basic u-mt--32 u-mb--32">
                                            <ul class="c-text-list c-bullet c-bullet--dot">
                                                <li class="c-text-list__item u-co-gray">
                                                    <c:choose>
                                                        <c:when test="${prdtIndCd eq '10'}">eSIM비는 다운로드에 상관없이 최초 개통 시 1회 발생되며, 프로파일 삭제 등으로 재 다운로드 시 추가 발생합니다.</c:when>
                                                        <c:when test="${prdtIndCd eq '11'}">eSIM비는 다운로드에 상관없이 최초 개통 시 1회 발생되며, 프로파일 삭제 등으로 재 다운로드 시 추가 발생합니다.</c:when>
                                                        <c:otherwise>kt M mobile에서 제공한 유심으로 개통을 진행하지 않으실 경우, 유심비용이 청구될 수 있습니다.</c:otherwise>
                                                    </c:choose>
                                                </li>
                                                <li class="c-text-list__item u-co-gray">
                                                    월 납부금액은 부가서비스 등의 사용에 따라 추가금액이 합산되어 청구 될 수 있습니다.
                                                </li>
                                                <li class="c-text-list__item u-co-gray">월 납부금액은 부가세 포함 금액입니다.</li>
                                                <li class="c-text-list__item u-co-gray">
                                                    타사향(SK, LG U+ 등) 단말은 일부 서비스(MMS,영상통화, 교통카드 기능 등) 이용이 제한될 수 있습니다.
                                                </li>
                                                <li class="c-text-list__item u-co-gray">제휴카드 할인은 각 카드사별 이용 조건이 반영되지 않은 최대 할인 금액으로 적용되어 있습니다.<br />정확한 할인 조건은 이벤트 페이지의 각 제휴카드 정보를 꼭 확인하시기 바랍니다.</li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="c-button-wrap u-mt--48">
                                        <button class="c-button c-button--share" data-dialog-trigger="#modalShareAlert">
                                            <span class="c-hidden">공유하기</span>
                                            <i class="c-icon c-icon--share" aria-hidden="true"></i>
                                        </button>
                                        <button class="c-button c-button--full c-button--red" onclick="nextStep();">가입하기</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="c-modal c-modal--xlg rate-modal" id="paymentSelect" role="dialog" aria-labelledby="#paymentPlanSelect__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header c-flex--between">
                        <h2 class="c-modal__title" id="rateSelectTitle">요금제 선택</h2>
                        <div class="c-form c-form--search u-pr--50">
                            <div class="c-form__input">
                                <input class="c-input" type="text" placeholder="검색어를 입력해주세요" id="searchNm" name="searchNm" value=""  maxlength="20" autocomplete="off" >
                                <label class="c-form__label c-hidden" for="searchNm">검색어 입력</label>
                                <button class="c-button c-button--reset" id="searchClear">
                                    <span class="c-hidden">초기화</span>
                                    <i class="c-icon c-icon--reset" aria-hidden="true"></i>
                                </button>
                                <button class="c-button c-button-round--sm" id="searchRatePlan"></button>
                            </div>
                        </div>
                        <button class="c-button u-mt--12" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body c-modal__body--full">

                        <!-- 상단 필터버튼 -->
                        <div class="rate-filter-wrap">
                            <div class="rate-btn-group">
                                <div class="btn-category-wrap">
                                    <div class="btn-category__list ">
                                        <div class="btn-category__inner" id="rateBtnCategory">

                                            <c:set var="ctgList" value="${nmcp:getCodeList('GD0004')}" />
                                            <div class="btn-category__group">
                                                <button type="button" class="btn-category _rateTabM1" data-best-rate="1" >전체</button>
                                                <button type="button" class="btn-category _rateTabM1" data-best-rate="0">추천</button>
                                                <c:forEach items="${ctgList}" var="ctgObj" >
                                                    <button type="button" class="btn-category _rateTabM1 _rateCtg" data-best-rate="-2" data-rate-ctg="${ctgObj.dtlCd}" data-rate-ctg-group1="${ctgObj.expnsnStrVal1}" data-rate-ctg-group2="${ctgObj.expnsnStrVal2}">${ctgObj.dtlCdNm} </button>
                                                </c:forEach>
                                            </div>
                                        </div>
                                       <button type="button" class="btn-category__expand is-active" id="btnCateroryView">
                                            <span class="c-hidden">버튼 펼쳐 보기</span>
                                        </button>
                                    </div>
                                    <div class="btn-category__compare">
                                        <button type="button" class="btn-category _rateTabM1" data-best-rate="-1" id="rateBtnCompar">
                                            비교함
                                            <i class="c-icon c-icon--comparison" aria-hidden="true"></i>
                                            <span class="rate-btn__cnt">0</span>
                                        </button>
                                        <c:if test="${empty phoneProdBas.prodId or phoneProdBas.prodCtgType eq '03'}">
                                        <button type="button" class="btn-category" id="rateBtnMyrate">
                                            내게 맞는 요금제
                                            <i class="c-icon c-icon--question" aria-hidden="true"></i>
                                        </button>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="btn-sort-wrap">
                                    <button type="button" class="btn-sort" data-order-value="${not empty ProdCommendDto.orgnId ? 'XML_CHARGE' : 'CHARGE'}" >가격</button>
                                    <button type="button" class="btn-sort" data-order-value="XML_DATA">데이터</button>
                                    <button type="button" class="btn-sort" data-order-value="VOICE">통화</button>
                                    <button type="button" class="btn-sort" data-order-value="CHARGE_NM">이름</button>
                                </div>
                            </div>
                        </div>
                        <!-- 요금제 영역 시작 -->
                        <div class="rate-content">

                            <section  class="section-survey" style="display: none;">
                                <div class="c-main-row">
                                    <div class="survey">
                                        <div class="survey__box">
                                            <!--설문중에는 02.json play-->
                                            <div class="survey-ani" aria-hidden="true" data-ani-url="/resources/animation/portal/02.webjson"></div>
                                            <!--설문완료에는 04.json play-->
                                            <div class="survey-ani" aria-hidden="true" data-ani-url="/resources/animation/portal/04.webjson"></div>
                                            <!--설문 표지-->
                                            <div class="survey__content sug__rate"	style="display: block;">
                                                <div class="u-fs-12 u-co-gray-9 u-mt--8" id="divQuestionTitle">

                                                </div>

                                            </div>
                                                <%-- 설문 질문, 결과 --%>
                                        </div>
                                    </div>
                                </div>
                            </section>
                            <!--첫 번째 설문-->
                            <div class="survey__content _survey__content2" style="display: none;">
                                <div class="u-fs-30 u-co-mint u-fw--bold" ></div>
                            </div>
                            <!--두 번째 설문-->
                            <div class="survey__content _survey__content2" style="display: none;">
                                <div class="u-fs-30 u-co-mint u-fw--bold"> </div>
                            </div>

                            <!-- 질문 답 요금제 리스트 -->
                            <ul class="rate-content__list _question" id="rateQuestionResult" style="display: none;">



                            </ul>
                            <div class="c-button-wrap _question" style="display: none;">
                                <button id="btnRetryMyrat" class="restart c-button u-width--130 c-button--h48 c-button-round c-button--white u-mt--30">다시하기</button>
                            </div>

                            <!-- 요금제 리스트 -->
                            <ul class="rate-content__list" id="rateContentCategory">



                            </ul>
                            <ul class="rate-content__list" id="ulIsEmpty" style="display: none;">
                                <!-- 리스트가 없을 경우 -->
                                <li class="rate-content__item no-compare">
                                    <i class="c-icon c-icon--inbox" aria-hidden="true"></i>
                                    <p>요금제가 없습니다. </p>
                                </li>
                            </ul>
                            <!-- 요금제 리스트 끝 -->



                        </div>
                        <!-- 요금제 영역 끝 -->


                    </div>
                    <div class="c-modal__footer u-flex-center">
                        <div class="u-box--w460 c-button-wrap">
                            <button class="c-button c-button--full c-button--gray" data-dialog-close>닫기</button>
                            <button class="c-button c-button--full c-button--primary is-disabled"  id="paymentSelectBtn">요금제 선택하기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>





        <div class="c-modal c-modal--sm" id="modalShareAlert" role="dialog" tabindex="-1" aria-labelledby="#modalShareAlertTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body u-ta-center">
                        <h3 class="c-heading c-heading--fs22 u-fw--bold">공유하기</h3>
                        <div class="c-button-wrap c-flex u-mt--32">
                            <a class="c-button" href="javascript:void(0);" onclick="kakaoShare(); return false;">
                                <span class="c-hidden">카카오-새 창열림</span>
                                <i class="c-icon c-icon--kakao" aria-hidden="true"></i>
                            </a>
                            <a class="c-button" href="javascript:void(0);" onclick="lineShare(); return false;">
                                <span class="c-hidden">line-새 창열림</span>
                                <i class="c-icon c-icon--line" aria-hidden="true"></i>
                            </a>
                            <a class="c-button" href="javascript:void(0);" onclick="copyUrl();">
                                <span class="c-hidden">URL 복사하기</span>
                                <i class="c-icon c-icon--link" aria-hidden="true"></i>
                            </a>
                        </div>
                        <div class="c-button-wrap u-mt--48">
                            <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <form name="frm" id="frm" method="post" action="/loginForm.do">
            <input type="hidden" name="redirectUrl" id="uri"/>
        </form>
        <button data-dialog-trigger="#pre-prepare-modal" id="chkModalPop" style="display:none;">가입 전 확인해주세요 popup 호출</button>
        <div class="c-modal c-modal--md" id="pre-prepare-modal" role="dialog" aria-labelledby="#pre-prepare-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="pre-prepare-modal__title">가입 전 확인해주세요</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>





                    <div class="c-modal__body u-pb--0">
                        <h3 class="c-heading c-heading--fs20">신분증과 계좌/신용카드를 준비해주세요!</h3>
                        <p class="c-text c-text--fs17 u-mt--12 u-co-gray">비대면 가입신청서 작성 시 본인인증과 납부정보 확인이 진행됩니다.</p>


                        <c:choose>
                            <c:when test="${prdtIndCd eq '10'}">
                                <div class="certification-wrap u-mt--32 u-mb--32" aria-hidden="true">
                                    <img src="/resources/images/portal/content/img_id_card.png" alt="">
                                </div>
                                <hr class="c-hr c-hr--title">
                                <b class="c-flex c-text c-text--fs15">
                                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                    <span class="u-ml--4">유의사항</span>
                                </b>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-fw--medium u-co-red">한 휴대폰에서 2회선 이용 시, 메인회선과 서브회선이 동일 명의가 아닐 경우 이용에 제약이 있을 수 있습니다.</li>
                                </ul>
                            </c:when>
                            <c:when test="${prdtIndCd eq '11'}">
                                <div class="certification-wrap u-mt--32 u-mb--32" aria-hidden="true">
                                    <img src="/resources/images/portal/content/img_id_card.png" alt="">
                                </div>
                                <hr class="c-hr c-hr--title">
                                <b class="c-flex c-text c-text--fs15">
                                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                    <span class="u-ml--4">유의사항</span>
                                </b>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-fw--medium u-co-red">워치는 인증하신 M모바일 회선과 통합 청구됩니다.</li>
                                </ul>

                            </c:when>
                            <c:otherwise>
                                <div class="certification-wrap u-mt--32 u-mb--32" aria-hidden="true">
                                    <img src="/resources/images/portal/content/img_id_card.png" alt="">
                                </div>
                                <hr class="c-hr c-hr--title">
                                <b class="c-flex c-text c-text--fs15">
                                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                    <span class="u-ml--4">선택 가능 본인인증 방법</span>
                                </b>
                                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                                    <li class="c-text-list__item u-fw--medium u-co-gray" id="authListDesc">신용카드, 네이버인증서, 범용 인증서, toss 인증서, PASS 인증서, 카카오 본인인증</li>
                                </ul>
                            </c:otherwise>
                        </c:choose>


                        <c:if test="${!nmcp:hasLoginUserSessionBean()}">


                            <div class="c-box c-box--type1 c-expand--pop u-ta-center u-mt--40 ">
                                <h3 class="c-heading c-heading--fs20 u-mt--28">kt M모바일 회원이신가요?</h3>
                                <p class="c-text c-text--fs16 u-co-gray u-mt--16">
                                                                                                로그인 후 진행해주세요. <br>등록된 고객정보를 활용한 <span class="u-co-red">빠른가입</span>이 가능합니다.
                                </p>
                                <div class="c-button-wrap u-mt--48">
                                    <a class="c-button c-button--lg c-button--primary c-button--w460" href="javascript:void(0);" onclick="confirmNextStep('noMember');">비회원으로 가입하기</a>
                                </div>
                                <div class="c-button-wrap">
                                    <a class="c-button c-button--underline u-mt--40 u-mb--28 u-co-black u-fw--medium" href="javascript:void(0);" onclick="confirmNextStep('toMember');">로그인 후 가입하기</a>
                                </div>
                            </div>


                        </c:if>
                    </div>

                    <c:if test="${nmcp:hasLoginUserSessionBean()}">
                        <div class="u-mt--32"></div>
                        <div class="c-modal__footer">
                            <div class="c-button-wrap">
                                <a class="c-button c-button--lg c-button--primary c-button--w460" href="javascript:void(0);" onclick="confirmNextStep('member');">가입하기</a>
                            </div>
                        </div>
                    </c:if>




                </div>
            </div>
        </div>
    </t:putAttribute>

    <t:putAttribute name="popLayerAttr">
        <div class="c-modal c-modal--lg" id="esim-check-modal" role="dialog" aria-labelledby="esim-check-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="esim-check-modal__title">eSIM 사용 슬롯 확인</h2>
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
                                <caption>구분, IMEI로 구성된 eSIM 정보</caption>
                                <colgroup>
                                    <col width="20%">
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
                                    <td>EID</td>
                                    <td>
                                        <div class="c-form">
                                            <input type="text" class="c-input numOnly" id="eidTxt" name="eidTxt" value="" maxlength="32">
                                            <label class="c-form__label c-hidden" for="eidTxt">eidTxt</label>
                                        </div>
                                    </td>
                                </tr>
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
                                <tr>
                                    <td class="u-pa--12">등록 이미지</td>
                                    <td>
                                    	<div class="u-flex-center">
				                            <img id="preview" width="300" alt="업로드 이미지" style="display:none;">
				                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>


                        <div id="authArea" style="display:none;">
                            <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--48" id="inpNameCertiTitle">명의자 확인</h4>
                            <hr class="c-hr c-hr--title">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="inpNameCertiTitle">
                                    <div class="c-form-row c-col2">
                                        <div class="c-form-field">
                                            <div class="c-form__input u-mt--0">
                                                <input class="c-input" id="cstmrName" name="cstmrName" type="text" placeholder="이름 입력" value="" maxlength="60">
                                                <label class="c-form__label" for="cstmrName">이름</label>
                                            </div>
                                        </div>
                                        <div class="c-form-field">
                                            <div class="c-form__input-group u-mt--0">
                                                <div class="c-form__input c-form__input-division">
                                                    <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" type="text" autocomplete="false" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="" >
                                                    <span>-</span>
                                                    <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" type="password" autocomplete="new-password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" value="">
                                                    <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="c-box c-box--type1 u-mt--20">
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                        <li class="c-text-list__item u-co-gray">eSIM 추가 개통을 원하실 경우 개통하시려는 기기에서 사용중인 휴대폰번호로 인증이 필요합니다.</li>
                                    </ul>
                                </div>
                                <div class="c-button-wrap u-mt--40">
                                    <button id="btnSmsAuth" class="c-button c-button-round--md c-button--mint c-button--w460">휴대폰 인증</button>
                                </div>
                            </div>
                        </div>



                        <div id="phoneArea" style="display:none;">
                            <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--48" id="PhoneModelHead">휴대폰 모델명 선택</h4>
                            <hr class="c-hr c-hr--title">
                            <div class="c-form-wrap">
                                <div class="c-form-group" role="group" aira-labelledby="PhoneModelHead">
                                    <div class="c-form-row c-col2">
                                        <div class="c-form-field">
                                            <div class="c-form__select u-mt--0">
                                                <select class="c-select" id="phoneNm">
                                                    <option value="">휴대폰 선택</option>
                                                    <c:forEach items="${nmcp:getCodeList('MK0001')}" var="phoneNmList">
                                                        <option value="${phoneNmList.dtlCd}">${phoneNmList.dtlCdNm}</option>
                                                    </c:forEach>
                                                </select>
                                                <label class="c-form__label" for="phoneNm">휴대폰 선택</label>
                                            </div>
                                        </div>
                                        <div class="c-form-field">
                                            <div class="c-form__select u-mt--0">
                                                <select class="c-select" id="phoneModel"></select>
                                                <label class="c-form__label" for="phoneModel">모델명 선택</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="esim-slot-box__err" id="errTxt" style="display:none;"></div>



                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--gray u-width--220" id="reSetBtn" data-dialog-close>다시 등록하기</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220" id="setBtn">확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 가이드 -->
        <div class="c-modal c-modal--lg" id="uploadguide-modal" role="dialog" aria-labelledby="uploadguide-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="uploadguide-modal__title">이미지 업로드 가이드</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="esim-guide-box__pop">
                            <ul class="esim-guide__list">
                                <li class="esim-guide__item">
                                    <span class="Number-label u-ml--m32">1</span>
                                    통화창에 *#06#입력
                                </li>
                                <li class="esim-guide__item">
                                    <span class="Number-label u-ml--32">2</span>
                                    이미지 캡쳐본 업로드
                                </li>
                            </ul>
                            <img src="/resources/images/portal/esim/eSIM_guide_pop.png" alt="1.통화창에 *#06#입력, 2.화면 캡쳐 정보의 가이드 이미지">
                        </div>
                        <div class="c-box c-box--type1 u-mt--16">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">이미지 업로드 시 각 항목이 자동으로 입력됩니다.</li>
                            </ul>
                        </div>
                        <div class="c-button-wrap u-mt--48">
                            <button class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 가이드 끝-->

        <!-- Caution Layer -->
        <div class="c-modal c-modal--xlg" id="simpleDialog" role="dialog" aria-labelledby="#simpleTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title _title" id="simpleTitle">제휴카드 발급 및 할인 적용 유의사항 안내</h2>
                    </div>
                    <div class="c-modal__body _detail">

                    </div>
                    <div class="c-modal__footer">
                        <div id="divCheckCaution" class="c-button-wrap _simpleDialogButton" style="flex-direction: column;">
                            <div class="c-agree__item" style="padding-left:4rem;">
                                <input class="c-checkbox" id="cautionFlag" type="checkbox">
                                <label class="c-label" for="cautionFlag">위 사항을 읽고, 이해하였습니다.</label>
                            </div>
                            <button class="c-button c-button--lg c-button--primary u-width--220 _btnCheck" id="cautionCheck">확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </t:putAttribute>

</t:insertDefinition>
