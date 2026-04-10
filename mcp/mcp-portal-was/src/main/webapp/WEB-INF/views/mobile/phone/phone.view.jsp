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
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/phone/phone.view.js?version=26.01.07"></script>
        <script type="text/javascript" src="/resources/js/mobile/sns.js"></script>
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
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
        <input type="hidden" id="fprodId" value='${phoneProdBas.prodId }'/>
        <input type="hidden" id="prodId" value='${phoneProdBas.prodId }'/>
        <input type="hidden" id="fprodNm" value='${phoneProdBas.prodNm }' />
        <input type="hidden" id="hndsetModelId" value="${hndsetModelId}"/>
        <input type="hidden" id="prodCtgId" name="prodCtgId" value="${phoneProdBas.prodCtgId}">
        <input type="hidden" id="initOrgnId" value="${ProdCommendDto.orgnId}"/>
        <input type="hidden" id="initRateCd" value="${ProdCommendDto.rateCd}"/>
        <input type="hidden" id="initInstNom" value="${instNom}"/>
        <input type="hidden" id="rdoBestRate" name="rdoBestRate" value="1">
        <input type="hidden" id="joinPriceIsPay" name="joinPriceIsPay" value="N">
        <input type="hidden" id="usimPriceIsPay" name="usimPriceIsPay" value="N">
        <input type="hidden" id="joinPrice" name="joinPrice" value="-">
        <input type="hidden" id="usimPrice" name="usimPrice" value="-">
        <input type="hidden" id="socCode" name="socCode"/>
        <input type="hidden" id="salePlcyCd" name="salePlcyCd"/>
        <input type="hidden" id="platFormCd" value='${platFormCd}'/>
        <input type="hidden" name="prdtSctnCd" id="prdtSctnCd" value="${phoneProdBas.prodCtgId}" />

        <input type="hidden" name="isEvntCdPrmtAuth" id="isEvntCdPrmtAuth" value="" />
        <input type="hidden" name="evntCdPrmtSoc" id="evntCdPrmtSoc" value="" />
        <input type="hidden" name="evntCdPrmtSave" id="evntCdPrmtSave" value="" />
       <input type="hidden" name="evntCdSeq" id="evntCdSeq" value="" />
     <input type="hidden" name="eventExposedText" id="eventExposedText" value="" />
     <input type="hidden" name="eventCdName" id="eventCdName" value="" />

        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head c-flex"><span class="c-label--Network u-mr--6">${phoneProdBas.prodCtgIdLabel}</span>${phoneProdBas.prodNm }<button class="header-clone__gnb"></button></h2>
            </div>
            <div class="phone-detail c-expand phone-detail--type2">
                <div id="phoneSwiper" class="swiper-container">
                    <div class="swiper-wrapper"></div>
                    <button class="swiper-prev" type="button">
                        <i class="c-icon c-icon--arrow-left" aria-hidden="true"></i>
                    </button>
                    <button class="swiper-next" type="button">
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                    </button>
                    <div class="swiper-pagination"></div>
                </div>
            </div>

            <div class="c-form u-mt--40">
                <div class="c-form__title u-mt--8">용량</div>
                <select class="c-select" id="atribValCd2" name="atribValCd2">

                </select>
            </div>
            <div class="c-form phone-color-wrap">
                <dl class="u-mt--17 u-mb--12">
                    <dt>
                        <div class="u-fw--medium">색상</div>
                    </dt>
                    <dd>
                        <span id="colorNm"></span>
                    </dd>
                </dl>
                <div class="color-group" id="colorGroupDiv"></div>
            </div>


            <div class="c-form">
                <div class="c-form__title u-block">
                    <a data-tpbox="#phonViewTp1">가입유형<i class="c-icon c-icon--tooltip" aria-hidden="true"></i></a>
                    <div class="c-tooltip-box" id="phonViewTp1">
                        <div class="c-tooltip-box__title">번호이동</div>
                        <div class="c-tooltip-box__content">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item">타 통신사에서 사용하던 번호 그대로 엠모바일로 가입하시는 경우 선택해 주세요.</li>
                            </ul>
                        </div>
                        <div class="c-tooltip-box__title">신규가입</div>
                        <div class="c-tooltip-box__content">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item">새로운 번호로 개통합니다.</li>
                            </ul>
                        </div>
                        <div class="c-tooltip-box__title">기기변경</div>
                        <div class="c-tooltip-box__content">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item">기기변경은 기존 엠모바일 고객님이 휴대폰만 새로 바꾸고 사용하던 정보는 그대로 유지하는 것을 의미합니다.</li>
                                <li class="c-text-list__item">기기변경 신청을 위해 kt M모바일 고객 인증이 필요합니다.</li>
                            </ul>
                        </div>
                        <div class="c-tooltip-box__title">
                            <span class="u-co-sub-4">부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.</span>
                            <br />예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한
                        </div>
                        <button class="c-tooltip-box__close" data-tpbox-close>
                            <span class="c-hidden">툴팁닫기</span>
                        </button>
                    </div>
                </div>
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


            <c:if test="${phoneProdBas.prodCtgId eq '45'}">
                <div class="c-form">
                    <div class="c-form__title u-block">서비스 유형</div>

                    <div class="c-check-wrap c-check-wrap--type3" role="group" aria-labelledby="radServiceType">
                        <input class="c-radio c-radio--button" id="serviceType0" type="radio" name="dataType" value="LTE" <c:if test="${ProdCommendDto.prdtSctnCd eq 'LTE'}"> checked </c:if> />
                        <label class="c-label" for="serviceType0">LTE</label>
                        <input class="c-radio c-radio--button" id="serviceType1" type="radio" name="dataType" value="5G" <c:if test="${ProdCommendDto.prdtSctnCd eq '5G'}"> checked </c:if> />
                        <label class="c-label u-mr--0" for="serviceType1">5G</label>
                    </div>
                </div>
            </c:if>

            <div class="c-form">
                <div class="c-form__title u-block">
                    <a data-tpbox="#phonViewTp2">요금제 약정기간<i class="c-icon c-icon--tooltip" aria-hidden="true"></i></a>
                    <div class="c-tooltip-box" id="phonViewTp2">
                        <div class="c-tooltip-box__title">요금제 약정기간</div>
                        <div class="c-tooltip-box__content">
                            <ul class="c-text-list c-bullet c-bullet--dot">
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
                <div class="c-check-wrap c-check-wrap--type2 c-check-wrap--half" role="group" aria-labelledby="instNom">
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
            <div class="c-form">
                <div class="c-form__title u-block">
                    <a data-tpbox="#phonViewTp3">단말기 할부기간<i class="c-icon c-icon--tooltip" aria-hidden="true"></i></a>
                    <div class="c-tooltip-box" id="phonViewTp3">
                        <div class="c-tooltip-box__title">단말기 할부기간</div>
                        <div class="c-tooltip-box__content">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item">요금제 무약정 선택 시 일시납부(신규가입), 24개월, 30개월, 36개월 중 선택할 수 있습니다.</li>
                                <li class="c-text-list__item">단말기 할부기간은 요금제 약정기간과 동일한 기간만 선택할 수 있습니다.</li>
                            </ul>
                        </div>
                        <button class="c-tooltip-box__close" data-tpbox-close>
                            <span class="c-hidden">툴팁닫기</span>
                        </button>
                    </div>
                </div>
                <select class="c-select sel-paymemnt-plan" name="agrmTrm" id="agrmTrm"></select>
            </div>
            <c:if test="${discountType eq 'Y'}">
                <div class="c-form">
                    <div class="c-form__title u-block">
                        <a data-tpbox="#phonViewTp4">할인유형<i class="c-icon c-icon--tooltip" aria-hidden="true"></i></a>
                        <div class="c-tooltip-box" id="phonViewTp4">
                            <div class="c-tooltip-box__title">할인 유형</div>
                            <div class="c-tooltip-box__content">
                                    <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item">단말할인 : 공통지원금을 지원받는 경우</li>
                                    <li class="c-text-list__item">요금할인 : 공통지원금을 받지 않고 요금에 대한 지원을 받는 경우</li>
                                </ul>
                            </div>
                            <button class="c-tooltip-box__close" data-tpbox-close>
                                <span class="c-hidden">툴팁닫기</span>
                            </button>
                        </div>
                    </div>
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
            </c:if>
            <div class="c-form">
                <!--요금제 선택 이전 태그 시작-->
                <div id="radPayTypeA" >
                    <div>
                        <div class="c-form__title flex-type _paymentPop">요금제 선택</div>
                    </div>
                    <div class="c-box c-box--dotted">
                        <button type="button"  class="c-button c-button--full _paymentPop" >
                            <i class="c-icon c-icon--balloon-wifi" aria-hidden="true"></i>
                            <span class="u-co-gray u-ml--8">요금제를 선택해주세요.</span>
                        </button>
                    </div>
                </div>
                <!--요금제 선택 이전 태그 끝-->
                <!-- 요금제 선택 후 태그 시작-->
                <div id="radPayTypeB" style="display: none;" >
                    <div>
                        <div class="c-form__title flex-type">
                            요금제 선택
                            <button type="button" class="c-button c-button--sm u-co-mint c-expand _paymentPop" >
                                요금제 선택하기<i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>
                    <div class="c-box c-box--mint">
                        <h3 class="c-text c-text--normal" id="choicePay"></h3>
                        <p class="c-text c-text--type4 c-text--regular" id="choicePayTxt"></p>
                        <p class="c-text c-text--type4 c-text--regular" id="choicePayTxt2"></p
                    </div>
                </div>
                <!-- 요금제 선택 후 태그 끝-->
            </div>

            <div class="c-form" id="evntCdPrmtWrap" style="display: none">
                <div class="c-form__title u-block">
                    <a>이벤트 코드</a>
                </div>
                <p class="u-mb--16">해당 요금제는 이벤트 코드 적용이 가능합니다.<br />이벤트에서 확인한 코드가 있다면 입력 해 주세요.<br />※ 지급 조건은 프로모션별로 상이합니다.</p>
                <div class="c-form" style="display: flex">
                    <input class="c-input" name="evntCdPrmt" id="evntCdPrmt" type="text" placeholder="이벤트 코드 입력" maxlength="50" onkeyup="nextStepBtnChk();">
                    <label class="c-form__label sr-only" for="evntCdPrmt">이벤트코드</label>
                    <button id="evntCdPrmtBtn" class="c-button c-button--mint u-width--140 u-ml--8" onclick="checkEvntCdPrmt();">확인</button>
                    <button id="evntCdTryBtn" class="c-button c-button--mint u-width--140 u-ml--8 is-disabled" onclick="tryEvntCdPrmt();">재입력</button>
                </div>
            </div>

            <div class="c-form tripledc _ktTripleDcCss" style="display:none;">
                <div class="c-form__title u-block">
                    <a data-tpbox="#phonViewTp5">KT 인터넷 트리플할인<span class="u-fs-14 u-co-gray">(선택)</span><i class="c-icon c-icon--tooltip" aria-hidden="true"></i></a>
                    <div class="c-tooltip-box" id="phonViewTp5">
                        <div class="c-tooltip-box__title">KT 인터넷 트리플할인</div>
                        <div class="c-tooltip-box__content">KT인터넷을 신규로 가입하실 경우 추가로 받을 수 있는 요금할인 혜택(트리플할인 페이지에서 KT인터넷 상담신청 및 개통 후 마이페이지에서 신청 시 적용)</div>
                        <button class="c-tooltip-box__close" data-tpbox-close>
                            <span class="c-hidden">툴팁닫기</span>
                        </button>
                    </div>
                </div>
                <div class="c-check-wrap">
                    <div class="c-chk-wrap">
                        <input class="c-radio c-radio--button" id="ktTripleDcAmt_01" type="radio" name="ktTripleDcAmt" value="" checked="checked" />
                        <label class="c-label" for="ktTripleDcAmt_01">선택 안함</label>
                    </div>
                    <div class="c-chk-wrap">
                        <input class="c-radio c-radio--button" id="ktTripleDcAmt_02" type="radio" name="ktTripleDcAmt" value="" />
                        <label class="c-label" id="laKtTripleDcAmt_02"  for="ktTripleDcAmt_02">요금할인 0개월<br/>(0원)</label>
                    </div>
                </div>
            </div>
            <div class="c-form">
                <div class="c-form__title flex-type">
                    <div>제휴카드 할인<span class="u-fs-14 u-co-gray">(선택)</span></div>
                    <button type="button" onclick="javascript:openPage('pullPopup', '/m/event/cprtCardBnfitLayer.do?cprtCardCtgCd=PCARD00011', '');" class="c-button c-button--sm u-co-mint c-expand" >
                         혜택 비교하기<i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i>
                    </button>
                </div>
                <div class="c-form chkCard">
                  <div class="c-form__select">
                    <select class="c-select" id="chkCardSelect" name="chkCardSelect">
                      <option label="선택안함" value="" selected>선택안함</option>
                        <c:forEach items="${cardList}" var="cardObj">
                            <option label="${cardObj.cprtCardGdncNm}"  value="${cardObj.cprtCardGdncSeq}"
                                    data-cardname="${cardObj.cprtCardGdncNm}"
                                    data-carddesc1="${cardObj.cprtCardItemNm}"
                                    data-carddesc2="${cardObj.cprtCardItemDesc}"
                                    data-cardimg="${cardObj.cprtCardThumbImgNm}"
                                    data-dc-forml-cd="${cardObj.alliCardDcAmtDtoList[0].dcFormlCd}"
                                    data-dc-amt="${cardObj.alliCardDcAmtDtoList[0].dcAmt}"
                                    data-dc-limit-amt="${cardObj.alliCardDcAmtDtoList[0].dcLimitAmt}"  >'${cardObj.cprtCardGdncNm}
                            </option>
                        </c:forEach>
                    </select>
                    <label id="chkCardSelectLable" class="c-form__label" for="chkCardSelect">
                        <img src="/resources/images/portal/common/ico_card_no.svg" alt="제휴카드 이미지">
                        <div class="c-form__label-wrap">
                            <p class="c-form__label-title">제휴카드 할인 선택</p>
                        </div>
                    </label>
                  </div>
                </div>
            </div>
            <div class="c-form">
                <div class="c-form__title flex-type">
                     가입사은품
                    <button id="btnGift" type="button" class="c-button c-button--sm u-co-mint c-expand" >
                         가입 사은품 보기<i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i>
                    </button>
                </div>
                <div class="c-box c-box--type1 u-pa--16">
                    <p class="c-text c-text--type4">선택 사은품이 있는 경우 신청 마지막 단계에서 선택 가능</p>
                </div>
            </div>

            <div class="c-button-wrap">
                <a class="c-button c-button--share" data-dialog-trigger="#bsSample1">
                    <span class="c-hidden">공유하기</span>
                    <i class="c-icon c-icon--share" aria-hidden="true"></i>
                </a>
                <button type="button" class="c-button c-button--full c-button--red _btnJoinPage" >가입하기</button>
            </div>


            <div class="c-expand">
                <hr class="c-hr c-hr--type1 u-mt--40 u-mb--0">
                <div class="c-tabs c-tabs--type3">
                    <div class="c-tabs__list" data-tab-list="">
                        <button class="c-tabs__button" id="tabMain1" data-tab-header="#tab-panel">상품정보</button>
                        <button class="c-tabs__button" id="tabMain2" data-tab-header="#tab-pane2">상품스펙</button>
                        <button class="c-tabs__button" id="tabMain3" data-tab-header="#tab-pane3">사용후기</button>
                    </div>
                    <div class="c-tabs__panel c-un-expand" id="tab-panel">
                        <div class="phone-info__detail-box">${phoneProdBas.sntyDetailSpec}</div>
                    </div>
                    <div class="c-tabs__panel c-un-expand" id="tab-pane2">
                        <div class="c-table">
                            <table>
                                <caption>테이블 정보</caption>
                                <colgroup>
                                    <col>
                                    <col>
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th class="u-ta-left">휴대폰 명</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyProdNm}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">휴대폰 설명</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyProdDesc}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">모델번호</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyModelId}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">네트워크</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyNet}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">브랜드/제조사</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyMaker}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">출시월</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyRelMonth}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">색상</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyColor}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">디스플레이</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyDisp}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">크기</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntySize}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">무게</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyWeight}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">메모리</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyMemr}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">배터리</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyBtry}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">OS(운영체제)</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyOs}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">대기시간</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyWaitTime}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">카메라</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyCam}" default="-" /></td>
                                    </tr>
                                    <tr>
                                        <th class="u-ta-left">영상통화</th>
                                        <td class="u-ta-left"><c:out value="${phoneProdBas.sntyVideTlk}" default="-" /></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="c-tabs__panel c-un-expand" id="tab-pane3">
                        <div class="u-mt--36" id="reviewBody">
                            <ul class="review" id="reviewBoard"></ul>
                        </div>
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full" type="button" id="moreBtn">
                                더보기
                                <span class="c-button__sub">
                                <span id="reViewCurrent"></span>/
                                <span id="reViewTotal"></span></span>
                                <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
                            </button>
                        </div>
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









    </t:putAttribute>
    <t:putAttribute name="popLayerAttr">

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
                        <div class="c-addition-wrap">
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

                            <dl class="c-addition c-addition--type2 _noSelf" >
                                <dt>USIM(최초 1회)</dt>
                                <dd class="u-ta-right" id="usimPriceTxt">0 원</dd>
                            </dl>
                            <dl class="c-addition c-addition--type2 bottomMiddle">
                                <dt id="move_01" style="display:none;">번호이동 수수료</dt>
                                <dd id="move_02" style="display:none;" class="u-ta-right">800 원</dd>
                                <input type="hidden" id="moveYn"/>
                            </dl>
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
                            <li class="c-text-list__item u-co-gray">kt M mobile에서 제공한 유심으로 개통을 진행하지 않으실 경우, 유심비용이 청구될 수 있습니다</li>
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
                            <button class="c-button c-button--full c-button--red _btnJoinPage" >가입하기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!--
        요금제 선택 팝업 /  운영 > 셀프개통 > 요금제 선택 팝업
        src/main/webapp/WEB-INF/views/mobile/appForm/appFormDesignView.jsp
        소스 그대로 가져옴
        -->
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
                                <i class="c-icon c-icon--reset" id="searchClear" aria-hidden="true"></i>
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
                            <button class="c-button c-button--gray c-button--lg u-width--120" data-dialog-close>취소</button>
                            <button class="c-button c-button--full c-button--primary is-disabled" id="paymentSelectBtn">요금제 선택하기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>



        <!--
        가입 사은품 팝업  /  운영 > 휴대폰 > 갤럭시 A35 5G/LTE > 가입하기 > 하단에 생기는 사은품 영역
        src/main/webapp/WEB-INF/views/mobile/appForm/appFormDesignView.jsp
        소스 그대로 가져옴
        -->
        <div class="c-modal" id="giftSelect" role="dialog" tabindex="-1" aria-describedby="#giftSelectTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="giftSelectTitle">가입 사은품</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <section id="divGift" class="gift-section" style="display:none;"></section>
                        <!--
                        <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2 u-mt--m6 u-mb--48 giftInfoTxt">
                            <li class="c-text-list__item u-co-gray u-mt--0">가입조건 선택에 따른 선택 사은품을 확인하세요.</li>
                            <li class="c-text-list__item u-co-gray">실제 선택 사은품은 구매처에 따라 달라질 수 있습니다.</li>
                            <li class="c-text-list__item u-co-gray">가입 서식지 작성 시 사은품을 선택할 수 있습니다.</li>
                        </ul>
                        -->
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
                            <a class="c-button" href="javascript:void(0);" onclick="kakaoShare(); return false;"> <span class="c-hidden">카카오</span>
                                <i class="c-icon c-icon--kakao" aria-hidden="true"></i>
                            </a>
                            <a class="c-button" href="javascript:void(0);" onclick="lineShare(); return false;"> <span class="c-hidden">line</span>
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