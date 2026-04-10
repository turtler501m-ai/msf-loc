<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">쉐어링</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js?version=20.07.07"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=21.09.30"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js?version=21.12.22"></script>
        <script type="text/javascript" src="/resources/js/portal/content/reqSharingView.js?version=26.03.03"></script>
        <script type="text/javascript">
          history.pushState(null, null,location.href);
          window.onpopstate = function (event){
            location.href = "/mySharingCntrInfo.do";
           }
        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">쉐어링</h2>
      </div>
      <form name="frm" id="frm">
          <input type="hidden" name="contractNum" id="contractNum" value="${myShareDataReqDto.ncn}"/>
          <input type="hidden" name="realContractNum" id="realContractNum" value="${myShareDataReqDto.contractNum}"/>
          <input type="hidden" name="opmdSvcNo"   id="opmdSvcNo" value=""/>
          <input type="hidden" name="authYn" 	    id="authYn" value=""/>
          <input type="hidden" name="isAuth" 	    id="isAuth" value=""/>
          <input type="hidden" id="iccYn" name="iccYn" value=""/>
          <input type="hidden" id="selfShareYn" name="selfShareYn" value="Y">
          <input type="hidden" id="selfShareSuccesYn" name="selfShareSuccesYn" value="">
          <input type="hidden" id="menuType" name="menuType" value="shareData">
       </form>

      <!-- PASS 인증 추가 -->
      <input type="hidden" id="isPassAuth" name="isPassAuth">
      <!-- 본인인증 default -->
      <input type="hidden" id="defaultAuth" name="defaultAuth" value= "C">
      
      
      <input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv" > <!--안면인증 결과 -->
      <input type="hidden" id="fathTrgYn" name="fathTrgYn" > <!--안면인증 대상여부 -->

      <div class="c-row c-row--lg">
        <h3 class="c-heading c-heading--fs20">가입신청 정보</h3>
        <hr class="c-hr c-hr--title">
        <div class="c-form-row">
          <div class="c-form-field u-width--460">
            <div class="c-form__input-group is-readonly u-mt--0">
              <!-- [2022-01-17] has-value 클래스추가-->
              <div class="c-form__input c-form__input-division has-value">
                <input class="c-input--div3" id="inpPhoneNum1" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰번호 첫자리" value="${fn:substring(myShareDataReqDto.ctn,0,3)}" readonly>
                <span>-</span>
                <input class="c-input--div3" id="inpPhoneNum2" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 중간자리" value="${fn:substring(myShareDataReqDto.ctn,4,8)}" readonly>
                <span>-</span>
                <input class="c-input--div3" id="inpPhoneNum3" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 뒷자리" value="${fn:substring(myShareDataReqDto.ctn,9,14)}" readonly>
                <label class="c-form__label" for="inpPhoneNum">대표회선 번호</label>
              </div>
            </div>
          </div>
        </div>

        <!-- 본인인증 방법 선택 -->
        <jsp:include page="/WEB-INF/views/portal/popup/niceAuthForm.jsp">
            <jsp:param name="controlYn" value="N"/>
            <jsp:param name="reqAuth" value="CNAT"/>
        </jsp:include>

        <h3 class="c-heading c-heading--fs20 u-mt--64">유심 정보</h3>
        <hr class="c-hr c-hr--title">
        <div class="c-form-row">
          <div class="c-form-field u-width--460">
            <div class="c-form__input iccidChk"> <!-- has-error -->
              <input class="c-input numOnly" id="iccId" type="text"   placeholder="'-'없이 입력" aria-invalid="false" aria-describedby="msgA1" maxlength="19" >
              <label class="c-form__label" for="iccId">유심정보 입력</label>
              <button class="c-button c-button--sm c-button--underline" id="iccBtn"    onclick="btn_usimChk();">유심번호 유효성 체크</button>
            </div>
            <p class="c-form__text" id="msgA1" style="display: none;">올바른 유심번호를 입력해 주세요.</p>
          </div>
        </div>
        <div class="c-box c-box--type1 u-mt--20">
          <p class="c-bullet c-bullet--dot u-co-gray">유심 카드를 미리 구매하신 고객님께서는 유심번호 19자리를 입력해 주세요</p>
        </div>
        <h3 class="c-heading c-heading--fs20 u-mt--64">약관동의</h3>
        <input type="hidden" name="targetTerms" id="targetTerms" value=""/>

        <hr class="c-hr c-hr--title">
        <div class="c-agree c-agree--type2">
          <div class="c-agree__item">
            <input class="c-checkbox" id="chkD1"  type="checkbox" name="chkAgree">
            <label class="c-label" for="chkD1">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.</label>
          </div>
          <div class="c-agree__inside">
              <div class="c-chk-wrap">
                <button class="c-button c-button--xsm"  onclick="viewTerms('clausePriCollectFlag','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}');" >
                 <span class="c-hidden">개인정보 수집 &middot; 이용 동의 상세팝업 보기</span>
                 <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
               </button>
               <input class="c-checkbox c-checkbox--type2 _agree" name="terms" id="clausePriCollectFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_COLLECT_CODE}"  cdGroupId1=FORMREQUIRED data-dtl-cd="${item.dtlCd}" data-mand-yn="Y" >
               <label class="c-label" for="clausePriCollectFlag">개인정보 수집 &middot; 이용 동의<span class="u-co-gray">(필수)</span>
               </label>
             </div>
              <div class="c-chk-wrap">
                <button class="c-button c-button--xsm" onclick="viewTerms('clausePriOfferFlag','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}');">
                 <span class="c-hidden">개인정보의 제공 동의 상세팝업 보기</span>
                 <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
               </button>
               <input class="c-checkbox c-checkbox--type2 _agree" name="terms"  id="clausePriOfferFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_OFFER_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  data-dtl-cd="${item.dtlCd}" data-mand-yn="Y">
               <label class="c-label" for="clausePriOfferFlag">개인정보의 제공 동의<span class="u-co-gray">(필수)</span>
               </label>
             </div>
              <div class="c-chk-wrap">
                 <button class="c-button c-button--xsm"  onclick="viewTerms('clauseEssCollectFlag','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_ESS_COLLECT_CODE}');">
                  <span class="c-hidden">고유식별정보의 수집 &middot; 이용 동의 상세팝업 보기</span>
                  <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                </button>
                <input class="c-checkbox c-checkbox--type2 _agree" name="terms"  id="clauseEssCollectFlag" type="checkbox"  cdGroupId1=FORMREQUIRED docVer="0" type="checkbox" data-dtl-cd="${item.dtlCd}" data-mand-yn="Y"/>
                <label class="c-label" for="clauseEssCollectFlag">고유식별정보의 수집 &middot; 이용 동의<span class="u-co-gray">(필수)</span>
                </label>
               </div>
            
            
              <div class="c-chk-wrap">
                 <button class="c-button c-button--xsm"  onclick="viewTerms('clauseFathFlag01','cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag01');">
                  <span class="c-hidden">민감정보(생체인식정보)수집 및 이용동의(필수) 상세팝업 보기</span>
                  <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                </button>
                <input class="c-checkbox c-checkbox--type2 _agree" name="terms"  id="clauseFathFlag01" type="checkbox"  cdGroupId1=FORMREQUIRED docVer="0" type="checkbox" data-dtl-cd="${item.dtlCd}" data-mand-yn="Y"/>
                <label class="c-label" for="clauseFathFlag01">민감정보(생체인식정보)수집 및 이용동의<span class="u-co-gray">(필수)</span>
                </label>
               </div>
              <div class="c-chk-wrap">
                 <button class="c-button c-button--xsm"  onclick="viewTerms('clauseFathFlag02','cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag02');">
                  <span class="c-hidden">민감정보(생체인식정보)조희 및 이용 / 3자 제공에 대한 동의(필수)  상세팝업 보기</span>
                  <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                </button>
                <input class="c-checkbox c-checkbox--type2 _agree" name="terms"  id="clauseFathFlag02" type="checkbox"  cdGroupId1=FORMREQUIRED docVer="0" type="checkbox" data-dtl-cd="${item.dtlCd}" data-mand-yn="Y"/>
                <label class="c-label" for="clauseFathFlag02">민감정보(생체인식정보)조희 및 이용 / 3자 제공에 대한 동의<span class="u-co-gray">(필수)</span>
                </label>
               </div>
            
            
               <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm"  onclick="viewTerms('clausePriTrustFlag','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_TRUST_CODE}');" >
                   <span class="c-hidden">개인정보의 처리 업무 위탁 동의 상세팝업 보기</span>
                   <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                 </button>
                 <input class="c-checkbox c-checkbox--type2 _agree" name="terms"  id="clausePriTrustFlag" type="checkbox"  code="${Constants.CLAUSE_PRI_TRUST_CODE}" cdGroupId1=FORMREQUIRED  docVer="0"  type="checkbox"  data-dtl-cd="${item.dtlCd}" data-mand-yn="Y">
                 <label class="c-label" for="clausePriTrustFlag">개인정보의 처리 업무 위탁 동의<span class="u-co-gray">(필수)</span>
                 </label>
               </div>
               <div class="c-chk-wrap">
                     <button class="c-button c-button--xsm"  onclick="viewTerms('clauseConfidenceFlag','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_CONFIDENCE_CODE}');">
                   <span class="c-hidden">신용정보 조회 &middot; 이용 &middot; 제공에 대한 동의서 상세팝업 보기</span>
                   <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                 </button>
                 <input class="c-checkbox c-checkbox--type2 _agree" name="terms"  id="clauseConfidenceFlag" type="checkbox"  code="${Constants.CLAUSE_CONFIDENCE_CODE}" cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  data-dtl-cd="${item.dtlCd}" data-mand-yn="Y">
                 <label class="c-label" for="clauseConfidenceFlag">신용정보 조회 &middot; 이용 &middot; 제공에 대한 동의서<span class="u-co-gray">(필수)</span>
                 </label>
               </div>
                <div class="c-chk-wrap">
                    <button class="c-button c-button--xsm"  onclick="viewTerms('clausePriAdFlag','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_PRI_AD_CODE}');">
                    <span class="c-hidden">정보/광고 전송을 위한 개인정보 이용, 처리위탁 및   정보/광고 수신동의서 상세팝업 보기</span>
                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                  </button>
                  <input class="c-checkbox c-checkbox--type2 _agree" name="terms"  id="clausePriAdFlag" type="checkbox"  code="S${Constants.CLAUSE_PRI_AD_CODE}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  data-dtl-cd="${item.dtlCd}" data-mand-yn="N">
                  <label class="c-label" for="clausePriAdFlag">정보/광고 전송을 위한 개인정보 이용, 처리위탁 및   정보/광고 수신동의서<span class="u-co-gray">(선택)</span>
                  </label>
                </div>
                <div class="c-chk-wrap">
                  <button class="c-button c-button--xsm"  onclick="viewTerms('clauseFinanceFlag','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_FINANCE_FLAG}');">
                    <span class="c-hidden">개인(신용)정보 처리 및 보험가입 동의 상세팝업 보기</span>
                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                  </button>
                  <input class="c-checkbox c-checkbox--type2 _agree" name="terms" id="clauseFinanceFlag" type="checkbox"  code="S${Constants.CLAUSE_FINANCE_FLAG}" cdGroupId1=FORMSELECT docVer="0" type="checkbox"  data-dtl-cd="${item.dtlCd}" data-mand-yn="N">
                  <label class="c-label" for="clauseFinanceFlag">개인(신용)정보 처리 및 보험가입 동의<span class="u-co-gray">(선택)</span>
                  </label>
                </div>
                 <div class="c-chk-wrap">
                  <button class="c-button c-button--xsm"  onclick="viewTerms('notice','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_16}');" >
                     <span class="c-hidden">서비스 이용약관 상세팝업 보기</span>
                     <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                   </button>
                   <p class="u-ml--36 u-fs-15 u-co-black">서비스 이용약관<span class="u-co-gray u-ml--4">(고지)</span>
                   </p>
                 </div>
               </div>
             </div>
             <div class="c-form-wrap u-mt--48 _self">
              <h5 class="c-form__title">셀프개통 안내사항</h5>
              <div class="c-agree c-agree--type1">
                <div class="c-agree__item">
                  <div class="c-agree__inner">
                       <button class="c-button c-button--xsm" id="btnClauseSimple" >
                        <span class="c-hidden">셀프개통 안내사항 상세팝업 보기</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                      </button>
                    <input class="c-checkbox c-checkbox--type2" name="terms"  id="clauseSimpleOpen" type="checkbox" data-mand-yn="Y">
                    <label class="c-label" for="clauseSimpleOpen"> 본인은 안내사항을 확인하였습니다<span class="u-co-gray">(필수)</span>
                    </label>
                  </div>
                </div>
              </div>
            </div>

        <div class="c-form-wrap" id="selfCertSection1" >
          <div class="c-form-group" role="group" aira-labelledby="radIdCardHead">
            <h5 class="c-form__title--type2" id="radIdCardHead">신분증 확인</h5>
            <div class="c-checktype-row c-col3 u-mt--0">
              <input class="c-radio c-radio--button" id="selfCertType1" value="01" type="radio" name="selfCertType">
              <label class="c-label" for="selfCertType1">주민등록증</label>
              <input class="c-radio c-radio--button" id="selfCertType2" value="02" type="radio" name="selfCertType">
              <label class="c-label" for="selfCertType2">운전면허증</label>
              <input class="c-radio c-radio--button" id="selfCertType3" value="03" type="radio" name="selfCertType">
              <label class="c-label" for="selfCertType3">장애인 등록증</label>
            </div>
            <div class="c-checktype-row c-col3">
              <input class="c-radio c-radio--button" id="selfCertType4" value="04" type="radio" name="selfCertType">
              <label class="c-label" for="selfCertType4">국가 유공자증</label>
              <input class="c-radio c-radio--button" id="selfCertType6" value="06" type="radio" name="selfCertType" <c:if test='${Constants.CSTMR_TYPE_FN ne cstmrType }'> disabled </c:if>  >
              <label class="c-label" for="selfCertType6">외국인 등록증</label>
              <input class="c-radio c-radio--button c-hidden" id="selfCertType5" value="05" type="radio" name="selfCertType" disabled >
              <label class="c-label c-hidden" for="selfCertType5">여권</label>
            </div>
          </div>
        </div>

        <div class="c-form-wrap _isFathCert" style="display:none;">
           <div class="c-button-wrap u-mt--40">
            <button id="btnFathUrlRqt" class="c-button c-button--md c-button--mint c-button--w460">안면인증 URL 받기</button>
          </div>

          <div class="c-button-wrap u-mt--40">
              <button id="btnFathTxnRetv" class="c-button c-button--md c-button--mint c-button--w220" disabled>안면인증 결과 확인</button>
              <button id="btnFathSkip" class="c-button c-button--md c-button--gray c-button--w220" onclick="requestFathSkip();" disabled>안면인증 SKIP</button>
          </div>
        </div>

        <div class="c-form-wrap u-mt--32 _chkIdCardTitle" id="selfCertSection2" style="display:none;">
          <div class="c-form-group" role="group" aira-labelledby="chkIdCardTitle">

            <h3 class="c-group--head c-hidden u-mb--0" id="chkIdCardTitle">신분증 확인 검증</h3>

            <div class="u-box--w460 u-margin-auto">

              <!--[2022-02-07] aria-hidden 속성 및 케이스별 신분증 이미지 추가-->
              <div class="c-img-wrap" aria-hidden="true">
                <!--주민등록증 선택 시-->
                <img id="imgRadIdCard" src="/resources/images/portal/content/img_jumin_card.png" alt="">
              </div>

              <div class="c-form__select _cstmrForeignerNation" style="display:none;">
                <select class="c-select" id="cstmrForeignerNation" name="cstmrForeignerNation">

                </select>
                <label class="c-form__label" for="cstmrForeignerNation">국가</label>
                <input type="hidden" id="cstmrForeignerNationTmp" name="cstmrForeignerNationTmp" value="${AppformReq.cstmrForeignerNation}">
              </div>

              <div class="c-form-row c-col2 u-mt--0 _selfIssuNumF" style="display:none;">
                <div class="c-form-field">
                  <div class="c-form__input">
                    <input class="c-input" id="selfIssuNum" type="text" placeholder="보훈번호 입력" value="">
                    <label class="c-form__label" for="selfIssuNum">보훈번호</label>
                  </div>
                </div>
              </div>

              <!-- 운전면허증 선택 시 노출-->
              <div class="c-form-row c-col2 _driverSelfIssuNumF" style="display:none;">
                <div class="c-form-field">
                  <div class="c-form__input">
                    <input class="c-input" id="driverSelfIssuNum" type="text" placeholder="면허번호 입력" value="${AppformReq.selfIssuNumDesc}">
                    <label class="c-form__label" for="driverSelfIssuNum">면허번호</label>
                  </div>
                </div>
                <input  type="hidden" id="driverSelfIssuNum1">
                <input type="hidden"  id="driverSelfIssuNum2">
              </div>

              <div class="c-form-field c-form-field--datepicker">
                <div class="c-form__input has-value">
                  <input class="c-input flatpickr" id="selfIssuExprDt" type="text" placeholder="YYYY-MM-DD" value="" maxlength="10">
                  <label class="c-form__label" for="selfIssuExprDt">발급일자</label>
                  <span class="c-button c-button--calendar">
                                        <span class="c-hidden">날짜선택</span>
                                        <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                                    </span>
                </div>
              </div>

              <div class="c-form-wrap u-mt--20">
                <div class="c-checktype-row">
                  <input class="c-checkbox" id="selfInqryAgrmYnFlag" type="checkbox">
                  <label class="c-label u-mb--20" for="selfInqryAgrmYnFlag">본인 조회에 동의합니다.</label>
                </div>
              </div>
              <div class="c-box" id="selfCertSection3">
                <p class="c-bullet c-bullet--dot u-co-gray u-fs-16">입력하신 정보는 한국정보통신진흥협회(KAIT)에서 제공하는 부정가입방지 시스템을 통해 신분증 진위여부 판단에 이용됩니다.</p>
              </div>
            </div>
          </div>
        </div>


        <div class="c-button-wrap u-mt--56">
          <a class="c-button c-button--lg c-button--gray u-width--220" href="javascript:void(0);"  onclick="sharing_cancel();">취소</a>
          <a class="c-button c-button--lg c-button--primary u-width--220 is-disabled" id="sharing_reg" href="javascript:void(0);"  onclick="sharing_reg();">개통 요청</a>
        </div><!-- [2022-01-14] 페이지 하단 유의사항 마크업 수정-->
        <h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          <span class="u-ml--4">알려드립니다</span>
        </h3>
        <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
          <!-- [$2022-01-14] 페이지 하단 유의사항 마크업 수정-->
          <li class="c-text-list__item u-co-gray">데이터 쉐어링으로 가입하는 회선은 통화가 불가능한 데이터 전용 회선으로 가입되기 때문에 회선 번호는 무작위로 부여됩니다.</li>
        </ul>
      </div>
    </div>


    <!-- PASS 인증 2단계 -->
    <c:set var="bankList" value="${nmcp:getCodeList('BNK')}" />
    <input type="hidden" id="requestNo" name="requestNo" >
    <input type="hidden" id="resUniqId" name="resUniqId" >

    <div class="c-modal c-modal--xlg" id="bankAutDialog" role="dialog" aria-labelledby="#bankAutDialog__title">
            <div class="c-modal__dialog" role="document">
              <div class="c-modal__content">
                <div class="c-modal__header">
                  <h2 class="c-modal__title" id="#bankAutDialog__title">계좌인증</h2>
                  <div class="c-stepper-wrap">
                    <div class="c-hidden">총 2단계 중 현재 2단계(계좌정보 입력)</div>
                    <ul class="c-stepper">
                      <li class="c-stepper__item c-stepper__item--active">
                        <span class="c-stepper__num">1</span>
                        <span class="c-stepper__title">계좌정보 입력</span>
                      </li>
                      <li class="c-stepper__item">
                        <span class="c-stepper__num">2</span>
                      </li>
                    </ul>
                  </div>
                  <button class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                  </button>
                </div>
                <div class="c-modal__body">
                  <h3 class="c-heading c-heading--fs20" id="AccountInfoHead">계좌정보 입력</h3>
                  <p class="c-text c-text--fs17 u-co-gray u-mt--12">본인인증에 필요한 정보를 입력해 주세요.
                    <br>PASS 인증을 받은 실명과 계좌 명의자가 동일해야 인증이 가능합니다.
                  </p>
                  <div class="c-form-wrap u-mt--48 u-mb--56">
                    <div class="c-form-group" role="group" aria-labelledby="AccountInfoHead">
                      <div class="c-form-row c-col2">
                        <div class="c-form-field">
                          <div class="c-form__select u-mt--0">
                            <select id="reqBankAut" class="c-select">
                                <option value="">선택</option>
                                <c:forEach items="${bankList}" var="itemBank" >
                                  <option value="${itemBank.dtlCd }">${itemBank.dtlCdNm }</option>
                                </c:forEach>
                              </select>
                            <label class="c-form__label" for="reqBankAut">은행/증권사</label>
                          </div>
                        </div>
                        <div class="c-form-field">
                          <div class="c-form__input u-mt--0">
                            <input class="c-input onlyNum" id="reqAccountAut" type="number" placeholder="'-'없이 입력">
                            <label class="c-form__label" for="reqAccountAut">계좌번호</label>
                          </div>
                        </div>
                      </div>
                      <div class="c-form-row c-col2 u-width--460">
                        <div class="c-form-field">
                          <div class="c-form__input">
                            <input class="c-input" id="cstmrNameTemp" type="text" placeholder="이름 입력" value="" readonly >
                            <label class="c-form__label" for="cstmrNameTemp">이름</label>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="c-modal__footer u-flex-center">
                  <div class="u-box--w460 c-button-wrap">
                    <button class="c-button c-button--full c-button--gray"  data-dialog-close>취소</button>
                    <button class="c-button c-button--full c-button--primary" id="btnAutAccount">인증요청</button>
                  </div>
                </div>
              </div>
            </div>
        </div>

        <div class="c-modal c-modal--xlg" id="bankAutDialog2" role="dialog" aria-labelledby="#bankAutDialog2__title">
            <div class="c-modal__dialog" role="document">
              <div class="c-modal__content">
                <div class="c-modal__header">
                  <h2 class="c-modal__title" id="bankAutDialog2__title">계좌인증</h2>
                  <div class="c-stepper-wrap">
                    <div class="c-hidden">총 2단계 중 현재 2단계(계좌정보 입력)</div>
                    <ul class="c-stepper">
                      <li class="c-stepper__item">
                        <span class="c-stepper__num">1</span>
                      </li>
                      <li class="c-stepper__item c-stepper__item--active">
                        <span class="c-stepper__num">2</span>
                        <span class="c-stepper__title">인증번호 입력</span>
                      </li>
                    </ul>
                  </div>
                  <button class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                  </button>
                </div>
                <div class="c-modal__body">
                  <h3 class="c-heading c-heading--fs20">인증번호 입력</h3>
                  <p class="c-text c-text--fs17 u-co-gray u-mt--12">입력하신 계좌로 1원을 보내드렸습니다.
                    <br>계좌 거래내역에서 1원의 입금자로 표시된 숫자 6자리를 입력해 주시기 바랍니다.
                  </p>
                  <div class="c-form-wrap u-mt--48">
                    <div class="c-form-group" role="group" aira-labelledby="chkValUsimTitle">
                      <h3 class="c-group--head c-hidden" id="chkValUsimTitle">유심번호 유효성 체크</h3>
                      <div class="u-box--w460 u-margin-auto">
                        <div class="c-form">
                          <!--[2022-02-07] aria-hidden 속성 추가 및 이미지 변경-->
                          <div class="c-img-wrap" aria-hidden="true">
                            <img src="/resources/images/portal/content/img_account_certification.png" alt="">
                          </div><!-- 인증 성공 시 .has-safe, 인증 실패 시 .has-error 추가-->
                          <div class="c-form-field">
                            <div class="c-form__input">
                              <input class="c-input onlyNum" id="textOpt" type="text" placeholder="숫자 6자리" maxlength="6">
                              <label class="c-form__label" for="textOpt">인증번호</label>
                            </div><!-- 에러 설명 필요할때 -->
                            <!-- p.c-bullet.c-bullet--dot.u-co-gray 설명                 -->
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="c-modal__footer u-flex-center">
                  <div class="u-box--w460 c-button-wrap">
                    <button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
                    <button class="c-button c-button--full c-button--primary" id="btnCheckAccountOtpConfirm">인증요청</button>
                  </div>
                </div>
              </div>
            </div>
        </div>

        <div class="c-modal c-modal--xlg" id="bankAutDialog3" role="dialog" aria-labelledby="#bankAutDialog3__title">
            <div class="c-modal__dialog" role="document">
              <div class="c-modal__content">
                <div class="c-modal__header">
                  <h2 class="c-modal__title" id="bankAutDialog3__title">계좌인증</h2>
                  <button class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                  </button>
                </div>
                <div class="c-modal__body">
                  <!-- [2022-01-20] 간격조정 유틸 클래스 추가-->
                  <div class="complete u-mt--80 u-mb--64 u-ta-center">
                    <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
                    <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
                      <b>인증 완료</b>
                    </h3>
                    <p class="c-text c-text--fs20 u-co-gray u-mt--16"> 계좌 인증이 완료되었습니다.
                      <br>확인 버튼을 누른 뒤 신청서를 계속 작성해주시기 바랍니다.
                    </p>
                  </div><!-- [2022-01-20] .u-flex-center 클래스 삭제-->
                </div>
                <div class="c-modal__footer">
                  <div class="c-button-wrap">
                    <button class="c-button c-button--lg c-button--primary c-button--w460" data-dialog-close>확인</button>
                  </div>
                </div>
              </div>
            </div>
        </div>

      
      
      


    </t:putAttribute>
</t:insertDefinition>
