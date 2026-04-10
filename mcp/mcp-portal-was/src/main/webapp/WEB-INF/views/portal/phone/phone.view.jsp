<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />
<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">
        <c:choose>
            <c:when test="${phoneProdBas.prodType eq '04'}">
                중고폰
            </c:when>
            <c:otherwise>
                새 휴대폰 구매하기
            </c:otherwise>
        </c:choose>
        kt M모바일 공식 다이렉트몰
    </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/sns.js"></script>
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script type="text/javascript" src="/resources/js/portal/phone/phone.view.js?version=25.11.14"></script>
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
        <input type="hidden" name="prdtSctnCd" id="prdtSctnCd" value="${phoneProdBas.prodCtgId}" />
        <input type="hidden" name="isEvntCdPrmtAuth" id="isEvntCdPrmtAuth" value="" />
        <input type="hidden" name="evntCdPrmtSoc" id="evntCdPrmtSoc" value="" />
        <input type="hidden" name="evntCdPrmtSave" id="evntCdPrmtSave" value="" />
     <input type="hidden" name="evntCdSeq" id="evntCdSeq" value="" />
     <input type="hidden" name="eventExposedText" id="eventExposedText" value="" />
     <input type="hidden" name="eventCdName" id="eventCdName" value="" />

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <span class="c-label--Network__detail u-mr--8">${phoneProdBas.prodCtgIdLabel}</span>
                <h2 class="c-page--title">${phoneProdBas.prodNm}</h2>
            </div>

            <div class="c-row c-row--xlg">
                <div class="phone-detail--type3">
                    <div class="phone-detail--left-col">
                        <div class="swiper-thumbs" id="swiperThumbs">
                            <div class="swiper-container">
                                <div class="swiper-wrapper"></div>
                            </div>
                            <button class="swiper-button-next c-hidden" type="button" tabindex="-1" aria-hidden="true"></button>
                            <button class="swiper-button-prev c-hidden" type="button" tabindex="-1" aria-hidden="true"></button>
                        </div>
                        <div class="swiper-detail" id="swiperDetail">
                            <div class="swiper-container">
                                <div class="swiper-wrapper"></div>
                            </div>
                        </div>
                    </div>
                    <div class="phone-detail--right-col">
                        <div class="phone-detail__price">
                            <dl>
                                <dt>용량</dt>
                                <dd>
                                    <label class="c-label c-hidden" for="atribValCd2">용량 선택</label>
                                    <select class="c-select" id="atribValCd2" name="atribValCd2">
                                        <option label="용량 선택" disabled value="">용량 선택</option>
                                    </select>
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    색상
                                </dt>
                                <dd class="phone-color-wrap">
                                    <ul class="c-checktype-row color-group clearfix" id="colorGroupDiv">
                                    </ul>
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    가입유형
                                    <button data-tpbox="#phonViewTp1" aria-describedby="#phonViewTp1__title">
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </button>
                                    <div class="c-tooltip-box" id="phonViewTp1" role="tooltip">
                                        <div class="c-tooltip-box__title c-hidden" id="phonViewTp1__title">가입유형 상세설명</div>
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
                                    </div>
                                </dt>
                                <dd>
                                    <div class="c-form-wrap">
                                        <div class="c-form-group" role="group" aira-labelledby="radJoinType">
                                            <h3 class="c-hidden" id="radJoinType">가입유형</h3>
                                            <div class="c-checktype-row c-col2">
                                                <c:if test="${mspSaleDto.mspSalePlcyMstDtoSimbol.mnpYn == 'Y'}">
                                                    <input class="c-radio c-radio--button" id="radRegistType1" type="radio" operName="번호이동" name="operType" <c:if test="${operType == PhoneConstant.OPER_PHONE_NUMBER_TRANS }"> checked </c:if> value="${PhoneConstant.OPER_PHONE_NUMBER_TRANS }" >
                                                    <label class="c-label" for="radRegistType1">번호이동</label>
                                                </c:if>
                                                <c:if test="${mspSaleDto.mspSalePlcyMstDtoSimbol.newYn == 'Y'}">
                                                    <input class="c-radio c-radio--button" id="radRegistType2" type="radio" operName="신규가입" name="operType" <c:if test="${operType == PhoneConstant.OPER_NEW }"> checked </c:if> value="${PhoneConstant.OPER_NEW }" >
                                                    <label class="c-label" for="radRegistType2">신규가입</label>
                                                </c:if>
                                                <c:if test="${mspSaleDto.mspSalePlcyMstDtoSimbol.hdnYn == 'Y'}">
                                                    <input class="c-radio c-radio--button" id="radRegistType3" type="radio" operName="기기변경" name="operType" <c:if test="${operType == Constants.OPER_TYPE_EXCHANGE }"> checked </c:if> value="${Constants.OPER_TYPE_EXCHANGE}" >
                                                    <label class="c-label" for="radRegistType3">기기변경</label>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </dd>
                            </dl>

                            <c:if test="${phoneProdBas.prodCtgId eq '45'}">
                                <dl>
                                    <dt>
                                        서비스 유형
                                    </dt>
                                    <dd>
                                        <div class="c-form-wrap">
                                            <div class="c-form-group" role="group" aira-labelledby="radServType">
                                                <h3 class="c-hidden" id="radServType">서비스 유형</h3>
                                                <div class="c-checktype-row c-col2">
                                                    <input class="c-radio c-radio--button" id="dataType_01" type="radio" name="dataType" value="LTE" <c:if test="${ProdCommendDto.prdtSctnCd eq 'LTE'}"> checked </c:if>>
                                                    <label class="c-label" for="dataType_01">LTE</label>
                                                    <input class="c-radio c-radio--button" id="dataType_02" type="radio" name="dataType" value="5G" <c:if test="${ProdCommendDto.prdtSctnCd eq '5G'}"> checked </c:if> >
                                                    <label class="c-label" for="dataType_02">5G</label>
                                                </div>
                                            </div>
                                        </div>
                                    </dd>
                                </dl>
                            </c:if>


                            <dl>
                                <dt>
                                    요금제 약정기간
                                    <button data-tpbox="#phonViewTp2" aria-describedby="#phonViewTp2__title">
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </button>
                                    <div class="c-tooltip-box" id="phonViewTp2" role="tooltip">
                                        <div class="c-tooltip-box__title c-hidden" id="phonViewTp2__title">요금제 약정기간 상세설명</div>
                                        <div class="c-tooltip-box__title">요금제 약정기간</div>
                                        <div class="c-tooltip-box__content">
                                            <ul class="c-text-list c-bullet c-bullet--dot">
                                                <li class="c-text-list__item">선택한 할부 기간에 따라 월 납부금액이 달라질 수 있습니다.</li>
                                                <li class="c-text-list__item">무약정 선택 시 하단 할인유형을 선택할 수 없습니다.</li>
                                                <li class="c-text-list__item">의무사용기간을 조건으로 보조금을 지급받은 고객은 의무사용기간 종료 전에 계약을 해지(요금미납, 단말기 파손 등으로 해지하는 경우 포함)할 경우 회사가 별도로 정하는 위약금을 납부하여야 합니다.</li>
                                            </ul>
                                        </div>
                                    </div>
                                </dt>
                                <dd>
                                    <div class="c-form-wrap">
                                        <div class="c-form-group" role="group" aira-labelledby="radContTermHead">
                                            <h3 class="c-hidden" id="radContTermHead">요금제 약정기간</h3>



                                            <c:forEach var="item" items="${mspSaleAgrmMstList}" varStatus="status">

                                                <c:if test="${(status.index+1) % 2 eq 0}">
                                                    <div class="c-checktype-row c-col2">
                                                </c:if>

                                                <c:if test="${status.index eq 0}">
                                                 <div class="c-checktype-row c-col2">
                                                     <input class="c-radio c-radio--button" id="radPeriodType99" type="radio" name="instNom" value="0" <c:if test="${instNom == '0'}"> checked </c:if>>
                                                     <label class="c-label u-width--225" for="radPeriodType99">무약정</label>
                                                </c:if>
                                                     <input class="c-radio c-radio--button" id="radPeriodType${status.index}" type="radio" name="instNom" value="${item.instNom}" <c:if test="${instNom == item.instNom}"> checked </c:if>>
                                                     <label class="c-label u-width--225" for="radPeriodType${status.index}">${item.instNom}<c:if test="${item.instNom ne '0'}">개월</c:if></label>

                                                <c:if test="${(status.index) % 2 eq 0 || fn:length(mspSaleAgrmMstList) - 1 eq status.index}">
                                                </div>
                                                </c:if>
                                            </c:forEach>




                                        </div>
                                    </div>
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    단말기 할부기간
                                    <button data-tpbox="#phonViewTp3" aria-describedby="#phonViewTp3__title">
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </button>
                                    <div class="c-tooltip-box" id="phonViewTp3" role="tooltip">
                                        <div class="c-tooltip-box__title c-hidden" id="phonViewTp3__title">단말기 할부기간 상세설명</div>
                                        <div class="c-tooltip-box__title">단말기 할부기간</div>
                                        <div class="c-tooltip-box__content">
                                            <ul class="c-text-list c-bullet c-bullet--dot">
                                                <li class="c-text-list__item">요금제 무약정 선택 시 일시납부(신규가입), 24개월, 30개월, 36개월 중 선택할 수 있습니다.</li>
                                                <li class="c-text-list__item">단말기 할부기간은 요금제 약정기간과 동일한 기간만 선택할 수 있습니다.</li>
                                            </ul>
                                        </div>
                                    </div>
                                </dt>
                                <dd>
                                    <div class="c-form">
                                        <label class="c-label c-hidden" for="agrmTrm">단말기 할부기간</label>
                                        <select class="c-select" name="agrmTrm" id="agrmTrm"></select>
                                    </div>
                                </dd>
                            </dl>

               <c:if test="${discountType eq 'Y'}">
                            <dl>
                                <dt>
                                    할인유형
                                    <button data-tpbox="#phonViewTp4" aria-describedby="#phonViewTp4__title">
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </button>
                                    <div class="c-tooltip-box" id="phonViewTp4" role="tooltip">
                                        <div class="c-tooltip-box__title c-hidden" id="phonViewTp4__title">할인 유형 상세설명</div>
                                        <div class="c-tooltip-box__title">할인 유형</div>
                                        <div class="c-tooltip-box__content">
                                            <ul class="c-text-list c-bullet c-bullet--dot">
                                                <li class="c-text-list__item">단말할인 : 공통지원금을 지원받는 경우</li>
                                                <li class="c-text-list__item">요금할인 : 공통지원금을 받지 않고 요금에 대한 지원을 받는 경우</li>
                                            </ul>
                                        </div>
                                    </div>
                                </dt>
                                <dd>
                                    <div class="c-form-wrap">
                                        <div class="c-form-group" role="group" aira-labelledby="chkSaleTypeHead">
                                            <h3 class="c-hidden	" id="chkSaleTypeHead">할인 유형</h3>
                                            <div class="c-checktype-row c-col2">
                                                <input class="c-radio c-radio--button" id="chkDiscountType1" type="radio" name="saleTy" value="KD" <c:if test="${sprtTp eq PhoneConstant.PHONE_DISCOUNT_FOR_MSP or empty sprtTp}">checked="checked"</c:if>>
                                                <label class="c-label" for="chkDiscountType1">단말 할인</label>
                                                <input class="c-radio c-radio--button" id="chkDiscountType2" type="radio" name="saleTy" value="PM" <c:if test="${sprtTp eq PhoneConstant.CHARGE_DISCOUNT_FOR_MSP }">checked="checked"</c:if>>
                                                <label class="c-label" for="chkDiscountType2">요금 할인</label>
                                            </div>
                                        </div>
                                    </div>
                                </dd>
                            </dl>
                  </c:if>
                            <dl class=" c-flex--item-center">
                                <dt class="u-mt--0">
                                    요금제 선택
                                    <button class="c-text-link--bluegreen _paymentPop" >요금제 선택 하기<i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i></button>
                                </dt>
                                <dd>
                                    <!-- 요금제 선택 전-->
                                    <div class="sel-fee-wrap" id="radPayTypeA">
                                        <div class="c-box c-box--dotted u-width--460">
                                            <button class="c-button _paymentPop" >
                                                <i class="c-icon c-icon--plus" aria-hidden="true"></i>
                                                <span class="u-co-gray fs-16">요금제를 선택해주세요.</span>
                                            </button>
                                        </div>
                                    </div>
                                    <!-- 요금제 선택 후 -->
                                    <div class="sel-fee-wrap__item" id="radPayTypeB" style="display:none;">
                                        <dl>
                                            <dt class="sel-fee-wrap__title" id="choicePay">
                                                <!--5G 단말 (2GB/200분)-->
                                            </dt>
                                            <dd class="sel-fee-wrap__text" id="choicePayTxt">
                                                <!--데이터 2GB | 음성 200분 | 문자 100건-->
                                            </dd>
                                             <dd class="sel-fee-wrap__text u-co-red" id="choicePayTxt2">
                                                <!--신규 가입 시 매월 1GB 추가 제공(기기변경 제외)-->
                                            </dd>
                                        </dl>
                                    </div>
                                </dd>
                            </dl>

                            <dl id="evntCdPrmtWrap" style="display:none;">
                                <dt>이벤트 코드</dt>
                                <dd>
                                    <p class="u-mb--10 u-fs-15">해당 요금제는 이벤트 코드 적용이 가능합니다. 이벤트에서 확인한 코드가 있다면 입력 해 주세요.<br/>※ 지급 조건은 프로모션별로 상이합니다.</p>
                                        <div class="c-input-wrap u-mt--0">
                                        <input class="c-input u-width--225" name="evntCdPrmt" id="evntCdPrmt" type="text" placeholder="이벤트 코드 입력" maxlength="50" onkeyup="nextStepBtnChk();">
                                        <label class="c-form__label sr-only" for="evntCdPrmt">이벤트 코드</label>
                                        <button id="evntCdPrmtBtn" class="c-button c-button--lg c-button--mint u-width--85 u-fs-15 u-ml--6" onclick="checkEvntCdPrmt();">확인</button>
                                        <button id="evntCdTryBtn" class="c-button c-button--lg c-button--mint u-width--85 u-fs-15 u-ml--4 is-disabled" onclick="tryEvntCdPrmt();">재입력</button>
                                    </div>
                                </dd>
                            </dl>

                            <dl class="_ktTripleDcCss" style="display:none;">
                                <dt class="u-mt--4">
                                    KT 인터넷<br />트리플할인<span class="c-form__sub">(선택)</span>
                                    <button data-tpbox="#phonViewTp5" aria-describedby="#phonViewTp5__title">
                                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                                    </button>
                                    <div class="c-tooltip-box" id="phonViewTp5" role="tooltip">
                                        <div class="c-tooltip-box__title c-hidden" id="phonViewTp5__title">KT 인터넷 트리플할인 상세설명</div>
                                        <div class="c-tooltip-box__title">KT 인터넷 트리플할인</div>
                                        <div class="c-tooltip-box__content">KT인터넷을 신규로 가입하실 경우 추가로 받을 수 있는 요금할인 혜택<br />(트리플할인 페이지에서 KT인터넷 상담신청 및 개통 후 마이페이지에서 신청 시 적용)</div>
                                    </div>
                                </dt>
                                <dd>
                                    <div class="c-form-wrap">
                                        <div class="c-form-group " role="group" aira-labelledby="ktDcRateForm">
                                            <h3 class="c-hidden" id="ktDcRateForm"><span>KT인터넷 트리플할인<span class="c-form__sub u-fs-16">(선택)</span></span></h3>
                                            <div class="c-checktype-row c-col2">
                                                <input class="c-radio c-radio--button" id="ktTripleDcAmt_01" type="radio" name="ktTripleDcAmt" value="" checked="checked">
                                                <label class="c-label" for="ktTripleDcAmt_01">선택 안함</label>
                                                <input class="c-radio c-radio--button" id="ktTripleDcAmt_02" type="radio" name="ktTripleDcAmt" value=""  >
                                                <label class="c-label" for="ktTripleDcAmt_02"  id="laKtTripleDcAmt_02">요금할인 0개월(0원)</label>
                                            </div>
                                        </div>
                                    </div>
                                </dd>
                            </dl>
                            <c:if test="${cardList ne null and !empty cardList}">
                            <dl class=" c-flex--item-center">
                                <dt class="u-mt--0">
                                       제휴카드 할인<span class="c-form__sub">(선택)</span>
                                    <button class="c-text-link--bluegreen" onclick="javascript:openPage('pullPopup', '/event/cprtCardBnfitLayer.do?cprtCardCtgCd=PCARD00011', '', 0);" >혜택 비교하기<i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i></button>
                                </dt>
                                <dd>
                                    <div class="c-form-field">
                                        <div class="c-form__select" >
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
                                            <label id="chkCardSelectLable" class="c-form__label" for="chkCardSelect" >
                                                <img src="/resources/images/portal/common/ico_card_no.svg" alt="제휴카드 이미지">
                                                <div class="c-form__label-wrap">
                                                    <p class="c-form__label-title">제휴카드 할인 선택</p>
                                                </div>
                                            </label>
                                        </div>
                                    </div>
                                </dd>
                            </dl>
                            </c:if>
                            <dl>
                                <dt class="u-mt--4">
                                        가입 사은품
                                    <button id="btnGift" class="c-text-link--bluegreen" >가입 사은품 보기<i class="c-icon c-icon--arrow-bluegreen" aria-hidden="true"></i></button>
                                </dt>
                                <dd>
                                    <div class="c-box c-box--type1">
                                        <p class="c-text c-text--fs14">선택 사은품이 있는 경우 신청 마지막 단계에서 선택 가능</p>
                                    </div>
                                </dd>
                            </dl>
                        </div>
                        <div class="c-button-wrap u-mt--48">
                            <button class="c-button c-button--share" data-dialog-trigger="#modalShareAlert">
                                <span class="c-hidden">공유하기</span>
                                <i class="c-icon c-icon--share" aria-hidden="true"></i>
                            </button>
                            <button type="button" class="c-button c-button--full c-button--red _btnJoinPage" >가입하기</button>
                        </div>
                    </div>
                </div>

                <div class="c-tabs c-tabs--type2 col-3">
                    <div class="c-tabs__list" role="tablist">
                        <button class="c-tabs__button" id="tabMain1" role="tab" aria-controls="tabC1panel" aria-selected="false" tabindex="-1">상품정보</button>
                        <button class="c-tabs__button" id="tabMain2" role="tab" aria-controls="tabC2panel" aria-selected="false" tabindex="-1">상품스펙</button>
                        <button class="c-tabs__button" id="tabMain3" role="tab" aria-controls="tabC3panel" aria-selected="false" tabindex="-1">사용후기</button>
                    </div>
                </div>

                <div class="c-tabs__panel" id="tabMainC1panel" role="tabpanel" aria-labelledby="tabMain1" tabindex="-1">
                    <div class="c-row c-row--lg">
                            ${phoneProdBas.sntyDetailSpec}
                    </div>
                </div>
                <div class="c-tabs__panel" id="tabMainC2panel" role="tabpanel" aria-labelledby="tabMain2" tabindex="-1">
                    <div class="c-row c-row--lg">
                        <div class="c-table u-mt--48">
                            <table>
                                <caption>휴대폰 명, 휴대폰 설명, 모델번호, 네트워크, 브랜드/제조사, 출시일, 색상, 디스플레이, 크기, 무게, 메모리, 배터리, OS(운영체제), 대기시간, 카메라, 영상통화를 포함하는 상품스펙 정보</caption>
                                <colgroup>
                                    <col style="width: 166px">
                                    <col>
                                </colgroup>
                                <tbody>
                                <tr>
                                    <th scope="row">휴대폰 명</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyProdNm}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">휴대폰 설명</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyProdDesc}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">모델번호</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyModelId}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">네트워크</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyNet}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">브랜드/제조사</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyMaker}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">출시일</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyRelMonth}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">색상</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyColor}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">디스플레이</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyDisp}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">크기</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntySize}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">무게</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyWeight}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">메모리</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyMemr}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">배터리</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyBtry}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">OS(운영체제)</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyOs}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">대기시간</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyWaitTime}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">카메라</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyCam}" default="-" /></td>
                                </tr>
                                <tr>
                                    <th scope="row">영상통화</th>
                                    <td class="u-ta-left"><c:out value="${phoneProdBas.sntyVideTlk}" default="-" /></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="c-tabs__panel" id="tabMainC3panel" role="tabpanel" aria-labelledby="tabMain3" tabindex="-1">
                    <div class="c-row c-row--lg">
                        <div id="phoneViewReviewNodataDiv" class="c-nodata u-mt--48"><i class="c-icon c-icon--nodata" aria-hidden="true"></i><p class="c-noadat__text">조회 결과가 없습니다.</p></div>
                        <div class="c-accordion review u-mt--40 u-bt--0" id="accordionReview" data-ui-accordion>
                            <ul id="reviewBoard" class="c-accordion__box"></ul>
                            <nav class="c-paging" aria-label="검색결과">
                            </nav>
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

                                    <img id="smallPhoneImg" class="usim-img" src="/resources/images/portal/content/img_phone_noimage.png" alt="${phoneProdBas.prodNm} 실물 사진 앞면" >


                                    <div class="inner-box">
                                        <p class="c-text c-text--fs18 u-fw--medium" id="bottomTitleInfo" ></p>
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

                            <div class="c-row c-col2-row c-scrolly-auto">
                                <div class="c-col2-left">
                                    <div class="c-addition-wrap">
                                        <!-- [2022-01-20] 마크업 수정-->
                                        <strong class="c-addition c-addition--type1">가입정보</strong>
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

                                        <dl class="c-addition c-addition--type2 _noSelf" >
                                            <dt>USIM(최초 1회)</dt>
                                            <dd class="u-ta-right" id="usimPriceTxt">0 원</dd>
                                        </dl>



                                        <dl class="c-addition c-addition--type2 bottomMiddle">
                                            <dt id="move_01" style="display:none;">번호이동 수수료</dt>
                                            <dd id="move_02" style="display:none;" class="u-ta-right">800 원</dd>
                                            <input type="hidden" id="moveYn"/>
                                        </dl>


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
                                                    kt M mobile에서 제공한 유심으로 개통을 진행하지 않으실 경우, 유심비용이 청구될 수 있습니다.
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
                                        <button class="c-button c-button--full c-button--red _btnJoinPage" >가입하기</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>






        </div>


        <!--
        요금제 선택 팝업 /  운영 > 셀프개통 > 요금제 선택 팝업
        src/main/webapp/WEB-INF/views/portal/appForm/appFormDesignView.jsp
        소스 그대로 가져옴
        -->
        <div class="c-modal c-modal--xlg rate-modal" id="paymentSelect" role="dialog" aria-labelledby="#paymentSelectTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header c-flex--between">
                        <h2 class="c-modal__title" id="paymentSelectTitle">요금제 선택</h2>
                        <div class="c-form c-form--search u-pr--50">
                            <div class="c-form__input">
                                <input class="c-input" type="text" placeholder="검색어를 입력해주세요" id="searchNm" name="searchNm" value=""  maxlength="20" autocomplete="off" >
                                <label class="c-form__label c-hidden" for="searchNm">검색어 입력</label>
                                <button class="c-button c-button--reset" id="searchClear">
                                    <span class="c-hidden">초기화</span>
                                    <i class="c-icon c-icon--reset" id="searchClear" aria-hidden="true"></i>
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






        <!--
        가입 사은품 팝업  /  운영 > 휴대폰 > 갤럭시 A35 5G/LTE > 가입하기 > 하단에 생기는 사은품 영역
        src/main/webapp/WEB-INF/views/portal/appForm/appFormDesignView.jsp
        소스 그대로 가져옴
        -->
        <div class="c-modal c-modal--xlg" id="giftSelect" role="dialog" aria-labelledby="#giftSelectTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="giftSelectTitle">가입 사은품</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body u-pt--32">
                        <section id="divGift" class="gift-section" style="display:none;"></section>
                        <!--
                        <ul class="c-text-list c-bullet c-bullet--dot " >
                            <li class="c-text-list__item u-co-gray u-mt--2">가입조건 선택에 따른 선택 사은품을 확인하세요.</li>
                            <li class="c-text-list__item u-co-gray">실제 선택 사은품은 구매처에 따라 달라질 수 있습니다.</li>
                            <li class="c-text-list__item u-co-gray">가입 서식지 작성 시 사은품을 선택할 수 있습니다.</li>
                        </ul>
                        -->
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
                                <span class="c-hidden">카카오</span>
                                <i class="c-icon c-icon--kakao" aria-hidden="true"></i>
                            </a>
                            <a class="c-button" href="javascript:void(0);" onclick="lineShare(); return false;">
                                <span class="c-hidden">line</span>
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
