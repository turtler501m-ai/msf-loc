<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />

<t:insertDefinition name="mlayoutDefault">
<t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=23.10.23"></script>
    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js?version=24.11.28"></script>
    <script type="text/javascript" src="/resources/js/common/validator.js?version=22.08.18"></script>
    <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js?version=23.01.02"></script>
    <script src="${smartroScriptUrl}?version=${today}"></script>
    <script type="text/javascript" src="/resources/js/mobile/appForm/eSimWatchForm.js?version=26.03.03"></script>
    <script type="text/javascript" src="/resources/js/mobile/appForm/appFormOcr.js?version=24.01.11"></script>

  <!-- 카카오 광고 분석  -->
    <script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
    <script type="text/javascript">
          kakaoPixel('5981604067138488988')
    </script>
    <!-- 카카오 광고 분석 end-->
</t:putAttribute>

<t:putAttribute name="contentAttr">
    <input type="hidden" id="mode" name="mode"/>

    <input type="hidden" id="isAuth" name="isAuth"  >
    <input type="hidden" id="isSmsAuth" name="isSmsAuth"  >
    <input type="hidden" id="isAuthInsr" name="isAuthInsr"  >
    <input type="hidden" id="isPassAuth" name="isPassAuth"  >
    <input type="hidden" id="isCheckAccount" name="isCheckAccount" >
    <input type="hidden" id="isCheckCard" name="isCheckCard" >
    <input type="hidden" id="isNpPreCheck" name="isNpPreCheck" >
    <input type="hidden" id="isCheckmember" name="isCheckmember" >
    <input type="hidden" id="isUsimNumberCheck" name="isUsimNumberCheck" >
    <input type="hidden" id="userDivision" name="userDivision" value="${userDivision}" />
    <input type="hidden" id="operType" name="operType" value="${AppformReq.operType}" />
    <input type="hidden" id="operTypeNm" name="operTypeNm" value="${AppformReq.operTypeNm}" />
    <input type="hidden" id="onOffType" name="onOffType" value="${AppformReq.onOffType}" />
    <input type="hidden" id="onOffTypeInit" name="onOffTypeInit" value="${AppformReq.onOffType}" />
    <input type="hidden" id="prdtSctnCd" name="prdtSctnCd" value="${AppformReq.prdtSctnCd}">
    <input type="hidden" id="reqBuyType" name="reqBuyType" value="${AppformReq.reqBuyType}">
    <input type="hidden" id="rprsPrdtId" name="rprsPrdtId" value="${AppformReq.rprsPrdtId}">
    <input type="hidden" id="prdtId" name="prdtId" value="${AppformReq.prdtId}">
    <input type="hidden" id="modelId" name="modelId" value="${AppformReq.prdtId}" >
    <input type="hidden" id="sesplsProdId" name=sesplsProdId value="${AppformReq.sesplsProdId}" >
    <input type="hidden" id="serviceType" name="serviceType" value="${AppformReq.serviceType}" >
    <input type="hidden" id="cntpntShopId" name="cntpntShopId" value="${AppformReq.cntpntShopId}" >
    <input type="hidden" id="socCode" name="socCode" value="${AppformReq.socCode}" >
    <input type="hidden" id="prodId" name="prodId" value="${AppformReq.prodId}" >
    <input type="hidden" id="siteReferer" name="siteReferer" value="${AppformReq.siteReferer}" >
    <input type="hidden" id="reqCnt" name="reqCnt" value="" />
    <input type="hidden" id="requestKeyTemp" name="requestKeyTemp" value="${AppformReq.requestKey}">
    <input type="hidden" id="insrProdCdTemp" name="insrProdCdTemp" value="${AppformReq.insrProdCd}">

    <!-- 확인 필요 -->
    <input type="hidden" id="bannerCd" name="bannerCd" value="${AppformReq.bannerCd}" >
    <input type="hidden" id="modelSalePolicyCode" name="modelSalePolicyCode" value="${AppformReq.modelSalePolicyCode}" >
    <input type="hidden" id="sprtTp" name="sprtTp" value="${AppformReq.sprtTp}" >
    <input type="hidden" id="modelMonthly" name="modelMonthly" value="${AppformReq.modelMonthly}" ><!--단말할부개월수  -->
    <input type="hidden" id="reqModelName" name="reqModelName" value="${AppformReq.reqModelName}" >
    <input type="hidden" id="sntyColorCd" name="sntyColorCd" value="${AppformReq.sntyColorCd}" ><!--단말기모델아이디_색상검색용  -->
    <input type="hidden" id="sntyCapacCd" name="sntyCapacCd" value="${AppformReq.sntyCapacCd}" ><!--단품용량코드  -->
    <input type="hidden" id="enggMnthCnt" name="enggMnthCnt" value="${AppformReq.enggMnthCnt}" ><!--유심에서 약정????  -->
    <input type="hidden" name="usimKindsCd" id="usimKindsCd" value="${AppformReq.usimKindsCd}" />
    <input type="hidden" name="prntsContractNo" id="prntsContractNo" value="${AppformReq.prntsContractNo}" /><!--부모 약정정보  -->
    <input type="hidden" name="uploadPhoneSrlNo" id="uploadPhoneSrlNo" value="${AppformReq.uploadPhoneSrlNo}" />
    <input type="hidden" id="requestNo" name="requestNo" >
    <input type="hidden" id="resUniqId" name="resUniqId" >
    <input type="hidden" id="joinPriceIsPay" name="joinPriceIsPay" >
    <input type="hidden" id="usimPriceIsPay" name="usimPriceIsPay" >
    <input type="hidden" id="usimPriceNfcIsPay" name="usimPriceNfcIsPay" >
    <input type="hidden" id="joinPrice" name="joinPrice" >
    <input type="hidden" id="usimPrice" name="usimPrice" >
    <input type="hidden" id="usimPriceBase" name="usimPriceBase" value="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_OBJ_BASE)}">
    <input type="hidden" id="usimPrice5G" name="usimPrice5G" value="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_OBJ_5G)}">
    <input type="hidden" id="usimPriceNfc" name="usimPriceNfc" value="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_USIM_NFC)}">
    <input type="hidden" id="usimPrice3G" name="usimPrice3G" value="${nmcp:getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE,Constants.DTL_CD_OBJ_3G)}">
    <input type="hidden" id="bizOrgCd" name="bizOrgCd" value="" ><!--바로배송 업체코드-->

    <input type="hidden" id="evntCdPrmt" name="evntCdPrmt" value="${AppformReq.evntCdPrmt}" ><!--이벤트코드 프로모션 -->
    <input type="hidden" id="recoUseYn" name="recoUseYn" value="${AppformReq.recoUseYn}" ><!--이벤트코드에 따른 추천인ID 노출여부 -->

    
    <input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv" > <!--안면인증 결과 -->
    <input type="hidden" id="fathTrgYn" name="fathTrgYn" > <!--안면인증 대상여부 -->
    


    <div class="ly-content" id="main-content">


      <div class="ly-page-sticky">

          <h2 class="ly-page__head">워치 가입하기<button class="header-clone__gnb"></button></h2>
          <div class="c-indicator">
              <span id="spIndicator" style="width: 50%"></span>
          </div>
      </div>

      <!-- STEP1 START -->
      <div id="step1" class="_divStep">

        <h3 class="c-heading c-heading--type1 u-mt--48">본인확인 및 약관동의</h3>

        <h4 class="c-heading c-heading--type5">고객유형</h4>
        <hr class="c-hr c-hr--type2">
        <div class="c-form">
          <div class="c-check-wrap">
            <div class="c-chk-wrap">
              <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType1" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NA}" desc="19세 이상 내국인"  disabled >
              <label class="c-label" for="cstmrType1">
                <i class="c-icon c-icon--type-adult" aria-hidden="true"></i>
                <span>19세 이상 내국인</span>
              </label>
            </div>
            <div class="c-chk-wrap">
              <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType2" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_NM}" desc="19세 미만 미성년자" disabled >
              <label class="c-label" for="cstmrType2">
                <i class="c-icon c-icon--type-kid" aria-hidden="true"></i>
                <span>19세 미만 미성년자</span>
              </label>
            </div>
            <div class="c-chk-wrap">
              <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="cstmrType3" type="radio" name="cstmrType" value="${Constants.CSTMR_TYPE_FN}" desc="외국인"  disabled >
              <label class="c-label" for="cstmrType3">
                <i class="c-icon c-icon--type-foreigner" aria-hidden="true"></i>
                <span>외국인</span>
              </label>
            </div>
          </div>


        </div>


        <h4 class="c-heading c-heading--type5">실명 및 본인인증</h4>
        <hr class="c-hr c-hr--type2">
        <div class="c-form">
          <div class="c-form__input ">
            <input class="c-input" id="cstmrName" type="text" placeholder="이름 입력" value=""  maxlength="60" disabled >
            <label class="c-form__label" for="cstmrName">이름</label>
          </div>
        </div>


        <div class="c-form _isForeigner" style="display:none;">
            <div class="c-form__input">
              <input class="c-input onlyNum" id="cstmrForeignerRrn01" name="cstmrForeignerRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="외국인 등록번호 앞자리" title="외국인 등록번호 앞자리" disabled >
              <label class="c-form__label" for="cstmrForeignerRrn01">외국인 등록번호</label>
            </div>
        </div>




        <div class="c-form _isDefault" >
            <div class="c-form__input">
                <input class="c-input onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리"  disabled  >
                <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
            </div>
        </div>

        <div class="c-form _isTeen" style="display:none;">
          <ul class="c-text-list c-bullet c-bullet--dot">
            <li class="c-text-list__item u-co-gray">영/유아(0세~4세 이하)의 경우 가입신청이 제한되므로 타 명의로 진행 바랍니다.</li>
          </ul>
        </div>






        <div class="c-form _isTeen" style="display:none;">
          <span class="c-form__title u-mt--40" id="spMinor">법정대리인 정보</span>
        </div>
        <div class="c-form _isTeen" style="display:none;">
          <div class="c-form__input">
            <input class="c-input" id="minorAgentName" type="text" placeholder="법정대리인 성명"  maxlength=60 >
            <label class="c-form__label" for="minorAgentName">법정대리인 성명</label>
          </div>
        </div>
        <div class="c-form _isTeen" style="display:none;">
          <div class="c-form__select">
            <select id="minorAgentRelation" class="c-select">
              <option value="">선택</option>
              <option value="01">부</option>
              <option value="02">모</option>
              <option value="10">그외</option>
            </select>
            <label class="c-form__label" for="minorAgentRelation" >신청인과의 관계</label>
          </div>
        </div>

        <div class="c-form-field _isTeen" style="display:none;">
          <div class="c-form__input-group">
            <div class="c-form__input c-form__input-division">
              <input class="c-input--div2 onlyNum" id="minorAgentRrn01" name="minorAgentRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'minorAgentRrn02');">
              <span>-</span>
              <input class="c-input--div2 onlyNum" id="minorAgentRrn02" name="minorAgentRrn02" autocomplete="off" type="password" maxlength="7" value="" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
              <label class="c-form__label" for="minorAgentRrn01">법정대리인 주민등록번호</label>
            </div>
          </div>
        </div>

        <div class="c-form u-mt--8 _isTeen" style="display:none;">
          <div class="c-form__input">
            <input class="c-input onlyNum" id="minorAgentTel" type="tel" name="minorAgentTel" value="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력">
            <label class="c-form__label" for="minorAgentTel">법정대리인 연락처</label>
          </div>
        </div>

        <div class="c-form _isTeen" style="display:none;">
          <span class="c-form__title">법정대리인 안내사항 확인 및 동의</span>
          <div class="c-agree__item">
            <input class="c-checkbox c-checkbox--type2" id="minorAgentAgrmYn" type="checkbox" name="minorAgentAgrmYn"  >
            <label class="c-label" for="minorAgentAgrmYn">본인은 안내사항을 확인하였습니다.<span class="u-co-gray">(필수)</span>
            </label>
            <button id="minorAgentAgree" class="c-button c-button--xsm" onclick="fnSetEventId('minorAgentAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_MINOR_AGENT}',0);"  >
              <span class="c-hidden">상세보기</span>
              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
            </button>
          </div>
        </div>
        <!--[$2021-11-29] 미성년자 선택 시 입력 항목 변경추가-->

        <!-- 본인인증 방법 선택 -->
        <c:if test="${AppformReq.onOffType eq '7'}">
            <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                <jsp:param name="controlYn" value="Y"/>
            </jsp:include>
        </c:if>
        <c:if test="${AppformReq.onOffType ne '7'}">
            <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                <jsp:param name="controlYn" value="N"/>
                <jsp:param name="reqAuth" value="CNKAT"/>
            </jsp:include>
        </c:if>

          <div class="c-accordion c-accordion--type5 acc-agree c-accordion--nested">
          <span class="c-form__title--type2">약관동의</span>
          <div class="c-accordion__box">
              <div class="c-accordion__item">
                  <div class="c-accordion__head">
                      <div class="c-accordion__check">
                          <input class="c-checkbox" id="btnStplAllCheck" type="checkbox">
                          <label class="c-label" for="btnStplAllCheck">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.</label>
                      </div>
                      <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#acc_agreeA1">
                          <div class="c-accordion__trigger"> </div>
                      </button>
                  </div>
                  <div class="c-accordion__panel expand" id="acc_agreeA1">
                      <div class="c-accordion__inside">
                          <div class="c-agree__item">
                              <input id="clausePriOfferFlag" code="${Constants.CLAUSE_PRI_OFFER_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                              <label class="c-label" for="clausePriOfferFlag">고유식별정보 수집&middot;이용 동의<span class="u-co-gray">(필수)</span>
                              </label>
                              <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriOfferFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}');">
                                  <span class="c-hidden">상세보기</span>
                                  <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                              </button>
                          </div>
                          <div class="c-agree__item">
                              <input id="clausePriCollectFlag" code="${Constants.CLAUSE_PRI_COLLECT_CODE}"  cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                              <label class="c-label" for="clausePriCollectFlag">개인정보/신용정보 수집·이용 동의<span class="u-co-gray">(필수)</span>
                              </label>
                              <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}');" >
                                  <span class="c-hidden">상세보기</span>
                                  <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                              </button>
                          </div>
                          <div class="c-agree__item">
                              <input id="clauseEssCollectFlag" code="${Constants.CLAUSE_ESS_COLLECT_CODE}" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                              <label class="c-label" for="clauseEssCollectFlag">개인정보 제3자 제공 동의<span class="u-co-gray">(필수)</span>
                              </label>
                              <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseEssCollectFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_ESS_COLLECT_CODE}');">
                                  <span class="c-hidden">상세보기</span>
                                  <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                              </button>
                          </div>

                          <div class="c-agree__item">
                            <input id="clauseFathFlag01" code="clauseFathFlag01" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                            <label class="c-label" for="clauseFathFlag01">민감정보(생체인식정보)수집 및 이용동의<span class="u-co-gray">(필수)</span></label>
                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFathFlag01');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag01');">
                              <span class="c-hidden">상세보기</span>
                              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </button>
                          </div>
                          <div class="c-agree__item">
                            <input id="clauseFathFlag02" code="clauseFathFlag02" cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree" >
                            <label class="c-label" for="clauseFathFlag02">민감정보(생체인식정보)조희 및 이용 / 3자 제공에 대한 동의<span class="u-co-gray">(필수)</span></label>
                            <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFathFlag02');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag02');">
                              <span class="c-hidden">상세보기</span>
                              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </button>
                          </div>
                        
                          <div class="c-agree__item _isTeen" style="display:none;">
                              <input id="nwBlckAgrmYn" code="${Constants.NOTICE_CODE_23}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                              <label class="c-label" for="nwBlckAgrmYn">청소년 유해정보 네트워크차단 동의<span class="u-co-gray">(필수)</span>
                              </label>
                              <button class="c-button c-button--xsm" onclick="fnSetEventId('nwBlckAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.NOTICE_CODE_23}');"  >
                                  <span class="c-hidden">상세보기</span>
                                  <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                              </button>
                          </div>

                          <div class="c-agree__item _isTeen" style="display:none;">
                              <input id="appBlckAgrmYn" code="${Constants.NOTICE_CODE_24}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">
                              <label class="c-label" for="appBlckAgrmYn">청소년 유해정보차단 APP 설치 동의<span class="u-co-gray">(필수)</span>
                              </label>
                              <button class="c-button c-button--xsm" onclick="fnSetEventId('appBlckAgrmYn');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.NOTICE_CODE_24}');"  >
                                  <span class="c-hidden">상세보기</span>
                                  <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                              </button>
                          </div>
                          <div class="c-accordion c-accordion--nested__sub c-agree__item">
                              <div class="c-accordion__box">
                                  <div class="c-accordion__item">
                                      <div class="c-accordion__head">
                                          <div class="c-accordion__check">
                                              <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="agreeWrap5" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
                                              <label class="c-label" for="agreeWrap5">고객 혜택 제공을 위한 정보수집 이용 동의 및 혜택 광고의 수신 위탁 동의<span class="u-co-gray">(선택)</span>
                                              </label>
                                          </div>
                                          <button class="c-accordion__button" type="button" aria-expanded="false" data-acc-header="#agree5">
                                              <div class="c-accordion__trigger"></div>
                                          </button>
                                      </div>
                                      <div class="c-accordion__panel expand" id="agree5">
                                          <div class="c-accordion__inside">
                                              <div class="c-agree__item">
                                                  <button class="c-button c-button--xsm" onclick="fnSetEventId('personalInfoCollectAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.PERSONAL_INFO_COLLECT_AGREE}',0);">
                                                      <span class="c-hidden">고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의(선택) 상세 팝업 열기</span>
                                                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                  </button>
                                                  <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="personalInfoCollectAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.PERSONAL_INFO_COLLECT_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N">
                                                  <label class="c-label" for="personalInfoCollectAgree">고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의<span class="u-co-gray">(선택)</span>
                                                  </label>
                                              </div>
                                              <div class="c-agree__item">
                                                  <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriAdFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_PRI_AD_CODE}');" >
                                                      <span class="c-hidden">개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의(선택) 상세 팝업 열기</span>
                                                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                  </button>
                                                  <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="clausePriAdFlag" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.CLAUSE_PRI_AD_CODE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="personalInfoCollectAgree">
                                                  <label class="c-label" for="clausePriAdFlag">개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의<span class="u-co-gray">(선택)</span>
                                                  </label>
                                              </div>
                                          </div>
                                      </div>
                                  </div>
                              </div>
                          </div>

                          <div class="c-accordion c-accordion--nested__sub c-agree__item">
                              <div class="c-accordion__box">
                                  <div class="c-accordion__item">
                                      <div class="c-accordion__head">
                                          <div class="c-accordion__check">
                                              <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="agreeWrap6" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
                                              <label class="c-label" for="agreeWrap6">혜택 제공을 위한 제3자 제공 및 광고 수신 동의<span class="u-co-gray">(선택)</span>
                                              </label>
                                          </div>
                                          <button class="c-accordion__button" type="button" aria-expanded="false" data-acc-header="#agree6">
                                              <div class="c-accordion__trigger"></div>
                                          </button>
                                      </div>
                                      <div class="c-accordion__panel expand" id="agree6">
                                          <div class="c-accordion__inside">
                                              <div class="c-agree__item">
                                                  <button class="c-button c-button--xsm" onclick="fnSetEventId('othersTrnsAllAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.OTHERS_TRNS_ALL_AGREE}',0);">
                                                      <span class="c-hidden">혜택 제공을 위한 제3자 제공 동의 상세 팝업 열기</span>
                                                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                  </button>
                                                  <input class="c-checkbox c-checkbox--type2 _agree agreeWrap" id="othersTrnsAllAgree" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)" code="S${Constants.OTHERS_TRNS_ALL_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N">
                                                  <label class="c-label" for="othersTrnsAllAgree">혜택 제공을 위한 제3자 제공 동의<span class="u-co-gray">(선택)</span>
                                                  </label>
                                                  <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="formOthersTrnsAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_TRNS_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5" style="display: none;">
                                                  <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="formOthersTrnsKtAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_TRNS_KT_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5" style="display: none;">
                                              </div>
                                              <div class="c-agree__item">
                                                  <button class="c-button c-button--xsm" onclick="fnSetEventId('othersAdReceiveAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.OTHERS_AD_RECEIVE_AGREE}');" >
                                                      <span class="c-hidden">제3자 제공관련 광고 수신 동의(선택) 상세 팝업 열기</span>
                                                      <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                  </button>
                                                  <input class="c-checkbox c-checkbox--type2 _agree agreeCheck" id="othersAdReceiveAgree" type="checkbox" onclick="handleOptionalAgreeClick(this)" code="S${Constants.OTHERS_AD_RECEIVE_AGREE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" required-agree-id="agreeWrap5">
                                                  <label class="c-label" for="othersAdReceiveAgree">제3자 제공관련 광고 수신 동의<span class="u-co-gray">(선택)</span>
                                                  </label>
                                              </div>
                                          </div>
                                      </div>
                                  </div>
                              </div>
                          </div>

            <%--            <div class="c-agree__item">--%>
            <%--              <input id="clausePriTrustFlag" code="${Constants.CLAUSE_PRI_TRUST_CODE}" cdGroupId1=FORMREQUIRED  docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">--%>
            <%--              <label class="c-label" for="clausePriTrustFlag">개인정보의 처리 업무 위탁 동의<span class="u-co-gray">(필수)</span>--%>
            <%--              </label>--%>
            <%--              <button class="c-button c-button--xsm" onclick="fnSetEventId('clausePriTrustFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_TRUST_CODE}');" >--%>
            <%--                <span class="c-hidden">상세보기</span>--%>
            <%--                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>--%>
            <%--              </button>--%>
            <%--            </div>--%>

            <%--            <div class="c-agree__item">--%>
            <%--              <input id="clauseConfidenceFlag" code="${Constants.CLAUSE_CONFIDENCE_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" validityyn="Y" class="c-checkbox c-checkbox--type2 _agree">--%>
            <%--              <label class="c-label" for="clauseConfidenceFlag">신용정보 조회 &middot; 이용 &middot; 제공에 대한 동의서<span class="u-co-gray">(필수)</span>--%>
            <%--              </label>--%>
            <%--              <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseConfidenceFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_CONFIDENCE_CODE}');"  >--%>
            <%--                <span class="c-hidden">상세보기</span>--%>
            <%--                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>--%>
            <%--              </button>--%>
            <%--            </div>--%>

            <%--            <div class="c-agree__item">--%>
            <%--              <input id="clauseFinanceFlag" code="S${Constants.CLAUSE_FINANCE_FLAG}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  value="Y" validityyn="N" class="c-checkbox c-checkbox--type2 _agree"  >--%>
            <%--              <label class="c-label" for="clauseFinanceFlag">개인(신용)정보 처리 및 보험가입 동의<span class="u-co-gray">(선택)</span>--%>
            <%--              </label>--%>
            <%--              <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseFinanceFlag');openPage('termsPop','/termsPop.do','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_FINANCE_FLAG}');" >--%>
            <%--                <span class="c-hidden">상세보기</span>--%>
            <%--                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>--%>
            <%--              </button>--%>
            <%--            </div>--%>

                        <div class="c-agree__item">
                          <input id="notice" code="F${Constants.NOTICE_CODE_16}" cdGroupId1=FORMINFO docVer="0" type="checkbox"  value="Y" validityyn="N" class="c-checkbox c-checkbox--type2 hidden _agree">
                          <label class="c-label" for="notice">서비스 이용약관<span class="u-co-gray">(고지)</span>
                          </label>
                          <button class="c-button c-button--xsm" onclick="fnSetEventId('notice');openPage('termsPop','/termsPop.do','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_16}');" >
                            <span class="c-hidden">상세보기</span>
                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                          </button>
                        </div>
                      </div>
                  </div>
              </div>
          </div>
        </div>

        <c:if test="${AppformReq.onOffType eq '7'}" >
            <div class="c-form _self">
              <span class="c-form__title" id="radC">셀프개통 안내사항</span>
              <div class="c-agree__item">
                <input class="c-checkbox c-checkbox--type2" id="clauseSimpleOpen" type="checkbox" name="clauseSimpleOpen" validityyn="Y" >
                <label class="c-label" for="clauseSimpleOpen">본인은 안내사항을 확인하였습니다.<span class="u-co-gray">(필수)</span>
                </label>
                <button id="btnClauseSimple" class="c-button c-button--xsm"  >
                  <span class="c-hidden">상세보기</span>
                  <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                </button>
              </div>
            </div>
        </c:if>

        <div class="c-form" id="selfCertSection1" >
          <span id="radIdCardHead" class="c-form__title--type2">신분증 확인</span>
          <div class="c-check-wrap c-check-wrap--type2" >
            <div class="c-chk-wrap">
              <input class="c-radio c-radio--button" id="selfCertType1" value="01" type="radio" name="selfCertType">
              <label class="c-label" for="selfCertType1">주민등록증</label>
            </div>
            <div class="c-chk-wrap">
              <input class="c-radio c-radio--button" id="selfCertType2" value="02" type="radio" name="selfCertType" >
              <label class="c-label" for="selfCertType2">운전면허증</label>
            </div>
            <div class="c-chk-wrap">
              <input class="c-radio c-radio--button" id="selfCertType3" value="03" type="radio" name="selfCertType" >
              <label class="c-label" for="selfCertType3">장애인 등록증</label>
            </div>
            <div class="c-chk-wrap">
              <input class="c-radio c-radio--button" id="selfCertType4" value="04" type="radio" name="selfCertType" >
              <label class="c-label" for="selfCertType4">국가 유공자증</label>
            </div>
            <div class="c-chk-wrap">
              <input class="c-radio c-radio--button" id="selfCertType6" value="06" type="radio" name="selfCertType" <c:if test='${Constants.CSTMR_TYPE_FN ne cstmrType }'> disabled </c:if> >
              <label class="c-label" for="selfCertType6">외국인 등록증</label>
            </div>
            <div class="c-chk-wrap">
              <input class="c-radio c-radio--button c-hidden" id="selfCertType5" value="05" type="radio" name="selfCertType" disabled>
              <label class="c-label c-hidden" for="selfCertType5">여권</label>
            </div>
          </div>
        </div>

        <div class="c-form _isFathCert" style="display:none;">
          <button id="btnFathUrlRqt" class="c-button c-button--h48 c-button--mint c-button--full u-mt--32">안면인증 URL 받기</button>
          <div class="c-button-wra u-mt--32">
              <button id="btnFathTxnRetv" class="c-button c-button--h48 c-button--mint c-button--full" disabled>안면인증 결과 확인</button>
              <button id="btnFathSkip" class="c-button c-button--h48 c-button--gray c-button--full u-mt--8" onclick="requestFathSkip();" disabled>안면인증 SKIP</button>
          </div>
        </div>

        <div class="_chkIdCardTitle" style="display:none;">
          <div class="c-form c-scan-wrap" id="selfCertSection2" >
            <div class="c-box u-mt--32">
              <img id="imgRadIdCard" class="center-img" src="/resources/images/mobile/content/img_jumin_card.png" alt="">
            </div>
            <div class="c-button-wrap c-button-wrap--right _divScan" style="display:none;">
              <button class="c-button _ocrRecord" data-type="social" >
                <span class="c-text c-text--type3 u-co-sub-4">스캔하기</span>
                <i class="c-icon c-icon--scan" aria-hidden="true"></i>
              </button>
            </div>
          </div>


          <div class="c-form__select _cstmrForeignerNation" style="display:none;">
            <select class="c-select" id="cstmrForeignerNation" name="cstmrForeignerNation">

            </select>
            <label class="c-form__label" for="cstmrForeignerNation">국가</label>
            <input type="hidden" id="cstmrForeignerNationTmp" name="cstmrForeignerNationTmp" value="">
          </div>

          <!-- 운전면허증 선택 시 노출-->
          <div class="c-form _driverSelfIssuNumF" style="display:none;">
            <div class="c-form__input">
              <input id="driverSelfIssuNum" class="c-input" type="tel" name="driverSelfIssuNum" maxlength="15" placeholder="면허번호 입력"  value="${AppformReq.selfIssuNumDesc}">
              <label class="c-form__label" for="selfIssuNum">면허번호</label>
            </div>
            <input  type="hidden" id="driverSelfIssuNum1">
            <input type="hidden"  id="driverSelfIssuNum2">
          </div>

          <div class="c-form _selfIssuNumF"  style="display:none;">
            <div class="c-form__input">
              <input class="c-input" id="selfIssuNum" type="tel"  pattern="[0-9]*" placeholder="보훈번호 입력" title="보훈번호"  value="" >
              <!--[2021-11-22] 인풋타입 변경-->
              <label class="c-form__label" for="selfIssuNum">보훈번호</label>
            </div>
          </div>

          <div class="c-form c-form--datepicker" id="selfCertSection3" >
            <div class="c-form__input " >
              <input class="c-input date-input" id="selfIssuExprDt" type="text" placeholder="YYYY-MM-DD" title="발급일자"  maxlength="10"   value="" >
              <label class="c-form__label" for="selfIssuExprDt">발급일자</label>
              <button class="c-button">
                <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
              </button>
            </div>
          </div>

          <div id="selfCertSection4" >
            <input class="c-checkbox" id="selfInqryAgrmYnFlag" type="checkbox" >
            <label class="c-label u-mt--20" for="selfInqryAgrmYnFlag">본인 조회에 동의합니다</label>
            <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
              <li class="c-text-list__item">입력하신 정보는 한국정보통신진흥협회(KAIT)에서 제공하는 부정가입방지 시스템을 통해 신분증 진위여부 판단에 이용됩니다.</li>
            </ul>
          </div>
        </div>

      </div>
      
      
      <!-- STEP1 END -->

      <!-- STEP2 START -->
      <div id="step2" style="display:none" class="_divStep" >
          <h3 class="c-heading c-heading--type1 u-mt--48">가입정보 확인</h3>

          <h4 class="c-heading c-heading--type5">가입신청 정보</h4>
          <hr class="c-hr c-hr--type2">
          <div class="c-form">
              <ul class="c-text-list c-bullet c-bullet--dot" >
                  <li class="c-text-list__item u-co-gray">개통 진행 시 변경이 불가하므로 다시 한번 확인 후 진행하시기 바랍니다. </li>
                  <li class="c-text-list__item u-co-gray">납부금액은 하기 모회선 번호로 <span  class="u-co-point-4 u-fs-15">통합 청구</span>됩니다. </li>
                  <li class="c-text-list__item u-co-gray">
                      애플 워치는 원넘버 서비스가 자동 가입되며 <span  class="u-co-point-4 u-fs-15">갤럭시 워치는 원넘버 서비스 이용을 원하실 경우 개통 후 고객센터(114)를 통해 신청 바랍니다.(무료)</span> <br/>
                      ※ 원넘버 부가서비스 : 스마트 워치에서도 휴대폰과 같은 번호로 통화 및 문자메시지를 발신하고 휴대폰
                      번호로 오는 전화를 워치에서도 동시에 수신 가능하게 하는 서비스
                  </li>
              </ul>
          </div>


          <div class="c-form">
              <div class="c-form__input">
                  <input class="c-input" id="eid"  name="eid"  value="${AppformReq.eid}" readonly >
                  <label class="c-form__label" for="eid">EID</label>
              </div>

              <div class="c-form__input">
                  <input class="c-input" id="imei2"  name="imei2" value="${AppformReq.imei1}"  readonly>
                  <label class="c-form__label" for="imei2">IMEI</label>
              </div>

          </div>



          <h4 class="c-heading c-heading--type5" >워치 개통 희망 번호</h4>
          <hr class="c-hr c-hr--type2">
          <c:if test="${AppformReq.onOffType eq '7' }">
              <div class="c-form c-reserv-num _self">
                  <div class="title-wrap">
                      <span class="c-form__title">번호예약</span>
                      <span id="searchCnt"> 조회 가능 횟수<b>20회</b></span>
                  </div>
                  <div class="c-box c-box--type1">
                      <span id="spReqWantNumber" >010 - **** -</span>
                      <input class="c-input onlyNum" id="reqWantNumberN" type="tel" maxlength="4" pattern="[0-9]*" placeholder="" value="${AppformReq.reqWantNumber}" >

                  </div>
                  <p class="c-bullet c-bullet--dot u-co-gray">조회 가능 횟수를 초과할 경우 신청서를 재작성 해야 합니다.</p>
                  <div class="c-button-wrap">
                      <button id="btnSearchNumber" action="RSV" class="c-button c-button--full c-button--mint" >번호조회</button>
                  </div>
              </div>
          </c:if>

          <div class="c-form c-reserv-num _noSelf" >
              <div class="c-box c-box--type1">
                  <span>1순위 010 - **** -</span>
                  <input class="c-input onlyNum" id="reqWantNumber" type="tel" maxlength="4" pattern="[0-9]*" placeholder="" value="${AppformReq.reqWantNumber}" >
              </div>
              <div class="c-box c-box--type1">
                  <span>2순위 010 - **** -</span>
                  <input class="c-input onlyNum" id="reqWantNumber2" type="tel" maxlength="4" pattern="[0-9]*" placeholder="" value="${AppformReq.reqWantNumber2}" >
              </div>
              <div class="c-box c-box--type1">
                  <span>3순위 010 - **** -</span>
                  <input class="c-input onlyNum" id="reqWantNumber3" type="tel" maxlength="4" pattern="[0-9]*" placeholder="" value="${AppformReq.reqWantNumber3}">
              </div>
          </div>


          <hr class="c-hr c-hr--type1 c-expand">

          <!-- 사은품 리스트-->
          <section id="divGift" class="gift-section" style="display:none;"></section>

          <h4 class="c-heading c-heading--type5 recoIdArea">친구초대 추천인ID<small>(선택)</small> </h4>
          <hr class="c-hr c-hr--type2 recoIdArea">
          <div class="c-form__group recoIdArea" role="group" aria-labeledby="inpG">
              <div class="c-form__select">
                  <c:set var="recommendFlagList" value="${nmcp:getCodeList(Constants.RECOMMEND_FLAG_GRUP_CODE)}" />
                  <select id="recommendFlagCd" class="c-select">
                      <c:forEach items="${recommendFlagList}" var="recommendFlag" >
                          <option value="${recommendFlag.dtlCd}" <c:if test="${AppformReq.recommendFlagCd eq recommendFlag.dtlCd }" > selected </c:if> >${recommendFlag.dtlCdNm}</option>
                      </c:forEach>
                  </select>
                  <label class="c-form__label">추천인 정보</label>
              </div>
              <input id="recommendInfo" class="c-input" type="text" placeholder="직접입력" title="직접입력" value="${AppformReq.recommendInfo}"  maxlength="20"  >
              <input type="hidden" name="commendId" id="commendId"  value=""  title="추천 아이디" >
          </div>
      </div>
      <!-- STEP2 END -->
      <div id="divButtonStep1" class="c-button-wrap">
          <button id="btnBefore" class="c-button c-button--full c-button--gray" style="display: none;">이전</button>
          <a id="btnNext" class="c-button c-button--full c-button--primary is-disabled _btnNext" href="javascript:void(0)">다음</a>
      </div>


      <div id="divButtonStep6" class="c-button-wrap" style="display:none" >
        <a class="c-button c-button--full c-button--gray" href="/">메인으로</a>
      </div>


    </div>



</t:putAttribute>

<t:putAttribute name="popLayerAttr">

  <!-- 하단 bottom-sheet trigger  영역 시작 (bottom-trigger 작동 시 is-active class 부여해주세요.)-->
  <div class="c-button-wrap c-button-wrap--b-floating is-active" id="bs_floating_button" data-floating-bs-trigger="#bsSample2">
    <button class="c-button c-button--bs-openner">
      <p class="c-text c-text--type3 u-fw--regular">
          월 예상 납부금액
        <span class="fs-26 u-ml--8" id="paySumMnthTxt">0</span> <span class="fs-14 fw-normal">원</span>
      </p>
      <p class="c-text c-text--type5 u-co-red-sub1">가입비 및 유심비 등 기타요금은 별도 청구됩니다.</p>
      <i class="c-icon c-icon--arrow-up" aria-hidden="true"></i>
    </button>
  </div>
  <!-- 하단 bottom-sheet trigger  영역 끝-->

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
                  <dl class="c-addition c-addition--type1">
                      <dt>가입정보</dt>
                  </dl>
            <dl class="c-addition c-addition--type2">
              <dt>고객구분</dt>
              <dd class="u-ta-right" id="ddCstmrType">-</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>가입유형</dt>
              <dd class="u-ta-right" id="ddOperTypeNm"><!-- 번호이동 --></dd>
            </dl>


            <hr class="c-hr c-hr--type2">
            <dl class="c-addition c-addition--type1">
                <dt>
                    월 통신요금
                </dt>
                <dd class="u-ta-right"><b id="payMnthChargeAmtTxt">-</b> 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
                <dt>기본요금</dt>
                <dd class="u-ta-right" ><b id="baseAmt">-</b> 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
                <dt>
                    <a data-tpbox="#tp1">기본할인 <!-- [2021-11-24] 'tooltop' > 'tooltip' -->
                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                    </a>
                </dt>
                <dd class="u-ta-right" ><b id="dcAmtTxt">-</b> 원</dd>
            </dl>
                  <div class="c-tooltip-box" id="tp1">
                      <div class="c-tooltip-box__title">기본할인</div>
                <div class="c-tooltip-box__content">약정 가입시 기본적으로 제공되는 요금제 할인금액입니다.</div>
                <button class="c-tooltip-box__close" data-tpbox-close>
                    <span class="c-hidden">툴팁닫기</span>
                </button>
            </div>
            <dl class="c-addition c-addition--type2">
                <dt>
                    <a data-tpbox="#tp2">요금할인 <!-- [2021-11-24] 'tooltop' > 'tooltip' -->
                        <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
                    </a>
                </dt>
                <dd class="u-ta-right" ><b id="addDcAmt">-</b> 원</dd>
            </dl>
            <div class="c-tooltip-box" id="tp2">
                <div class="c-tooltip-box__title">요금할인</div>
                <div class="c-tooltip-box__content">약정 - 할인 선택시 제공되는 요금할인 혜택입니다.</div>
                <button class="c-tooltip-box__close" data-tpbox-close>
                    <span class="c-hidden">툴팁닫기</span>
                </button>
            </div>
            <dl class="c-addition c-addition--type2">
                <dt>프로모션 할인</dt>
                <dd class="u-ta-right">
                    <b> <em id="promotionDcAmtTxt">-</em></b> 원
                </dd>
            </dl>

            <hr class="c-hr c-hr--type2">

            <dl class="c-addition c-addition--type1">
                <dt>가입비 및 유심비 별도</dt>
                <!-- <dd class="u-ta-right">
                    <b >0</b> 원
                </dd> -->
            </dl>
            <dl class="c-addition c-addition--type2">
                <dt>가입비(3개월 분납)</dt>
                <dd class="u-ta-right">
                    <em id="joinPriceTxt">- 원</em>
                </dd>
            </dl>

              <dl class="c-addition c-addition--type2">
                  <dt>eSIM(최초 1회)</dt>
                  <dd class="u-ta-right" id="usimPriceTxt"><!-- 6,600 원 --></dd>
              </dl>

            <hr class="c-hr c-hr--type2">

            <dl class="c-addition c-addition--type1 c-addition--sum">
                <dt>
                    <b>월 납부금액</b>(부가세 포함)
                </dt>
                <dd class="u-ta-right"><b id="paySumMnthTxt2">0</b> 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
                <dt id="bottomTitle"></dt>
            </dl>
            <hr class="c-hr c-hr--type2">



            <ul class="c-text-list c-bullet c-bullet--dot">
              <c:if test="${AppformReq.onOffType ne '7'}">
                   <li class="c-text-list__item u-co-gray">
                       <c:choose>
                       <c:when test="${AppformReq.usimKindsCd eq '09'}">eSIM비는 다운로드에 상관없이 최초 개통 시 1회 발생되며, 프로파일 삭제 등으로 재 다운로드 시 추가 발생합니다.</c:when>
                           <c:otherwise>kt M mobile에서 제공한 유심으로 개통을 진행하지 않으실 경우, 유심비용이 청구될 수 있습니다.</c:otherwise>
                       </c:choose>
                   </li>
              </c:if>
              <li class="c-text-list__item u-co-gray">월 납부금액은 부가서비스 등의 사용에 따라 추가금액이 합산되어 청구될 수 있습니다.</li>
                <li class="c-text-list__item u-co-gray">월 납부금액은 부가세가 포함된 금액입니다.</li>
                <li class="c-text-list__item u-co-gray">타사향(SK, LG U+ 등) 단말은 일부 서비스(MMS,영상통화, 교통카드 기능 등) 이용이 제한될 수 있습니다.</li>
            </ul>



          </div>


        </div>

        <div class="c-modal__footer fixed-area">
            <div class="c-button-wrap u-mt--0">
                <button class="c-button c-button--full c-button--red _btnNext" id="btnNext2">다음</button>
            </div>
        </div>
      </div>
    </div>
  </div>


  <!-- 셀프 개통 Layer-->
  <div class="c-modal" id="simpleDialog" role="dialog" tabindex="-1" aria-describedby="#simpleTitle">

    <div class="c-modal__dialog c-modal__dialog--full" role="document">
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title _title" >오류 TITLE</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-indicator">
          <span style="width: 33.33%"></span>
        </div>

          <div class="c-modal__body">
              <h3 class="c-heading c-heading--type1"></h3>
              <p class="c-text c-text--type2 u-co-gray _detail">
                  ※ 신규가입은 명의당 30일이내 1회선만 가입 가능합니다.<br/>
                  추가 가입은 최근 가입하신 KT M모바일 회선 가입일을 기준으로 30일 경과된 시점에 신청 부탁드립니다.
              </p>

          <div id="divChangeButton" class="c-button-wrap _simpleDialogButton">
            <button class="c-button c-button--gray c-button--lg u-width--120 _btnCheck" data-dialog-close>확인</button>
            
            
            <button id="btnOnline" class="c-button c-button--full c-button--primary">해피콜 신청서 작성</button>
          </div>

          <div id="divChangeUsimButton" class="c-button-wrap _simpleDialogButton">
            <button class="c-button c-button--gray c-button--lg u-width--120 _btnCheck" data-dialog-close>확인</button>
            <button onclick="location.href='/m/appForm/reqSelfDlvry.do'"  class="c-button c-button--full c-button--primary">유심구매</button>
          </div>

          <div id="divDefaultButton" class="c-button-wrap _simpleDialogButton">
            <button class="c-button c-button--full c-button--primary _btnCheck" data-dialog-close >확인</button>
          </div>

          <div id="divReqPreButton" class="c-button-wrap _simpleDialogButton">
            <button id="reqPreButton" class="c-button c-button--full c-button--primary"  >개통사전체크확인</button>
          </div>
        </div>


      </div>
    </div>


  </div>



  <!-- 번호예약 Layer  layerSearchNum -->
  <div class="c-modal" id="searchNumDialog" role="dialog" tabindex="-1" aria-describedby="#searchNumTitle">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="searchNumTitle">예약가능 번호 리스트</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <div class="c-form">
            <div class="c-form__title">전화번호 선택</div>
            <div class="c-check-wrap c-check-wrap--column _list">
              <!-- <div class="c-chk-wrap">
                <input class="c-radio" id="radNumber1" type="radio" name="radNumber" >
                <label class="c-label" for="radNumber1">010-1111-1234</label>
              </div>    -->
            </div>
          </div>
            <div class="c-button-wrap">
                <button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
                <button id="btnConfirmNum" class="c-button c-button--full c-button--primary">확인</button>
            </div>
        </div>
      </div>
    </div>
  </div>




    <!-- 이탈방지 -->
  <button class="c-button c-button--full c-button--white c-hidden"  data-dialog-trigger="#waitingConnectDialog" id="chkLayerPopBtn">이탈방지 레이어 호출</button>
  <div class="c-modal" id="waitingConnectDialog" role="dialog" tabindex="-1" aria-describedby="#waitingConnectTitle">
      <div class="c-modal__dialog" role="document">
          <div class="c-modal__content">
              <div class="c-modal__body u-ta-center">
                  <h3 class="c-heading c-heading--type3">
                      앗 ! 신청서 작성이 아직 남아있어요. <br>화면에서 나가시겠습니까?
                  </h3>
                  <p class="c-text c-text--type3 u-co-gray">
                      화면 이탈 시 입력 내용은 '신청조회' 메뉴에서 <br>확인 후 이어할 수 있습니다. <br>(입력 정보는 7일간 보관됩니다.)
                  </p>
              </div>
              <div class="c-modal__footer">
                  <div class="c-button-wrap">
                      <button class="c-button c-button--full c-button--gray" onclick="leaveUrlGo();" data-dialog-close>나가기</button>
                      <button class="c-button c-button--full c-button--primary" data-dialog-close >계속하기</button>
                  </div>
              </div>
          </div>
      </div>
      <div class="swiper-outside-banner" id="swiperOutsideBanner">
        <!--[2022-02-10] 배너 통 이미지 적용-->
        <div class="swiper-wrapper u-mb--24 c-box--sticky">
            <c:set var="O101List" value="${nmcp:getBannerList('O101')}" />
            <c:forEach var="item" items="${O101List}">
                  <c:choose>
                <c:when test="${item.linkTarget eq 'Y' and item.linkUrlAdr ne ''}">
                    <div class="swiper-slide" href="javascript:void(0);" onclick="javascript:insertBannAccess('${item.bannSeq}','${item.bannCtg}');location.href='${item.linkUrlAdr}';">
                        <button class="swiper-event-banner__button">
                            <div class="swiper-event-banner__item">
                               <img src="${item.mobileBannImgNm}" alt="${item.bannNm}">
                            </div>
                        </button>
                     </div>
                </c:when>
                <c:otherwise>
                    <div class="swiper-slide">
                       <button class="swiper-event-banner__button">
                           <div class="swiper-event-banner__item">
                              <img src="${item.mobileBannImgNm}" alt="${item.bannNm}">
                           </div>
                      </button>
                    </div>
                </c:otherwise>
             </c:choose>
          </c:forEach>
      </div>
      <c:if test="${fn:length(O101List) gt 0}">
        <div class="swiper-pagination"></div>
       </c:if>
    </div>


  </div>



</t:putAttribute>

</t:insertDefinition>