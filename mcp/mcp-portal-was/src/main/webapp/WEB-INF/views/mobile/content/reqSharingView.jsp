<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
	<script type="text/javascript" src="/resources/js/common/validator.js?version=20.07.07"></script>
	<script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=21.09.30"></script>
	<script type="text/javascript" src="/resources/js/mobile/content/reqSharingViewOcr.js"></script>
	<script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js?version=21.12.22"></script>
	<script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/content/reqSharingView.js?version=26.03.03"></script>
    <script type="text/javascript">
            history.pushState(null, null,location.href);
            window.onpopstate = function (event){
            location.href = "/m/mySharingCntrInfo.do";
         }
    </script>

</t:putAttribute>

 <t:putAttribute name="contentAttr">
	 <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">쉐어링<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <form name="frm" id="frm">
      	<input type="hidden" name="contractNum" id="contractNum" value="${myShareDataReqDto.ncn}"/>
        <input type="hidden" name="realContractNum" id="realContractNum" value="${myShareDataReqDto.contractNum}"/>
      	<input type="hidden" name="opmdSvcNo" id="opmdSvcNo" value=""/>
      	<input type="hidden" name="authYn" 	    id="authYn" value=""/>
      	<input type="hidden" name="isAuth" 	    id="isAuth" value=""/>
    	  <input type="hidden" id="birthday" name="birthday" value="${myShareDataReqDto.birthday}"  >
        <input type="hidden" id="iccYn" name="iccYn" value=""/>
        <input type="hidden" id="selfShareYn" name="selfShareYn" value="Y">
        <input type="hidden" id="selfShareSuccesYn" name="selfShareSuccesYn" value="">
        
        <input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv" > <!--안면인증 결과 -->
        <input type="hidden" id="fathTrgYn" name="fathTrgYn" > <!--안면인증 대상여부 -->
      </form>
      
      <!-- PASS 인증 추가 --> 
      <input type="hidden" id="isPassAuth" name="isPassAuth">
      <!-- 본인인증 default -->
      <input type="hidden" id="defaultAuth" name="defaultAuth" value= "C">

      <h3 class="c-heading c-heading--type5">가입신청정보</h3>
      <hr class="c-hr c-hr--type2">
      <div class="c-form">
        <div class="c-form__input">
          <input class="c-input" id="inpTel" type="text" name="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" value="${myShareDataReqDto.ctn}" readonly>
          <label class="c-form__label" for="inpTel">가입회선 번호</label>
        </div>
      </div>

      <!--  본인인증 방법 선택 -->
      <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
          <jsp:param name="controlYn" value="N"/>
          <jsp:param name="reqAuth" value="CNAT"/>
      </jsp:include>

      <hr class="c-hr c-hr--type1 c-expand">
      <h3 class="c-heading c-heading--type5">유심정보 입력</h3>
      <hr class="c-hr c-hr--type2">
      <div class="c-box u-mt--32">
        <img class="center-img" src="/resources/images/mobile/content/img_usim_card.png" alt="">
      </div>
      <div class="c-form c-scan-wrap">
        <div class="c-button-wrap c-button-wrap--right">
          <button class="c-button _ocrRecord" data-type="usim">
            <!--[2021-11-30] 마크업 순서 변경(디자인 변경 적용)-->
            <!--[2021-12-02] 텍스트 컬러 적용 class 변경-->
            <span class="c-text c-text--type3 u-co-sub-4" >스캔하기</span>
            <i class="c-icon c-icon--scan" aria-hidden="true"></i>
            <!--[$2021-12-02] 텍스트 컬러 적용 class 변경-->
          </button>
        </div>
       </div>
      <div class="c-form">
        <input class="c-input numOnly" type="tel" id="iccId" maxlength="19" pattern="[0-9]*" placeholder="직접입력('-'없이 입력)" title="유심번호 입력">
      </div>
      <p class="c-bullet c-bullet--fyr u-co-gray">유심카드를 미리 구매하신 고객님께서는 유심번호 19자리를 입력해 주세요.</p>
      <div class="c-button-wrap">
        <button class="c-button c-button--full c-button--mint" id="iccBtn"   onclick="btn_usimChk();">유심번호 유효성 체크</button><!-- 유효성 체크 성공 시-->
      </div>
      <hr class="c-hr c-hr--type1 c-expand">
      <h3 class="c-heading c-heading--type5">약관동의</h3>
      <input type="hidden" name="targetTerms" id="targetTerms" value=""/>
      <div class="c-agree u-mt--16">
        <input class="c-checkbox" id="chkD1" type="checkbox" name="chkD">
        <label class="c-label" for="chkD1">이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.</label>
       	<div class="c-agree__item">
          <input id="clausePriCollectFlag" code="${Constants.CLAUSE_PRI_COLLECT_CODE}" name="terms"   cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" data-mand-yn="Y" class="c-checkbox c-checkbox--type2 _agree" >
          <label class="c-label" for="clausePriCollectFlag">개인정보 수집 &middot; 이용 동의<span class="u-co-gray">(필수)</span>
          </label>
          <button class="c-button c-button--xsm" onclick="viewTerms('clausePriCollectFlag','cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_COLLECT_CODE}');" >
            <span class="c-hidden">상세보기</span>
            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
          </button>
        </div>
        <div class="c-agree__item">
           <input id="clausePriOfferFlag" code="${Constants.CLAUSE_PRI_OFFER_CODE}" name="terms"  cdGroupId1=FORMREQUIRED docVer="0"  type="checkbox"  value="Y" data-mand-yn="Y" class="c-checkbox c-checkbox--type2 _agree">
           <label class="c-label" for="clausePriOfferFlag">개인정보의 제공 동의<span class="u-co-gray">(필수)</span>
           </label>
           <button class="c-button c-button--xsm" onclick="viewTerms('clausePriOfferFlag', 'cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_OFFER_CODE}');">
             <span class="c-hidden">상세보기</span>
             <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
           </button>
        </div>
        <div class="c-agree__item">
           <input id="clauseEssCollectFlag" code="${Constants.CLAUSE_ESS_COLLECT_CODE}" name="terms"  cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" data-mand-yn="Y" class="c-checkbox c-checkbox--type2 _agree" >
           <label class="c-label" for="clauseEssCollectFlag">고유식별정보의 수집 &middot; 이용 동의<span class="u-co-gray">(필수)</span>
           </label>
           <button class="c-button c-button--xsm" onclick="viewTerms('clauseEssCollectFlag', 'cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_ESS_COLLECT_CODE}');">
             <span class="c-hidden">상세보기</span>
             <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
           </button>
         </div>

        <div class="c-agree__item">
           <input id="clauseFathFlag01" code="clauseFathFlag01" name="terms"  cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" data-mand-yn="Y" class="c-checkbox c-checkbox--type2 _agree" >
           <label class="c-label" for="clauseFathFlag01">민감정보(생체인식정보)수집 및 이용동의<span class="u-co-gray">(필수)</span>
           </label>
           <button class="c-button c-button--xsm" onclick="viewTerms('clauseFathFlag01', 'cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag01}');">
             <span class="c-hidden">상세보기</span>
             <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
           </button>
         </div>
        <div class="c-agree__item">
           <input id="clauseFathFlag02" code="clauseFathFlag02" name="terms"  cdGroupId1=FORMREQUIRED docVer="0" type="checkbox"  value="Y" data-mand-yn="Y" class="c-checkbox c-checkbox--type2 _agree" >
           <label class="c-label" for="clauseFathFlag02">민감정보(생체인식정보)조희 및 이용 / 3자 제공에 대한 동의<span class="u-co-gray">(필수)</span>
           </label>
           <button class="c-button c-button--xsm" onclick="viewTerms('clauseFathFlag02', 'cdGroupId1=FORMREQUIRED&cdGroupId2=clauseFathFlag02}');">
             <span class="c-hidden">상세보기</span>
             <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
           </button>
         </div>

          <div class="c-agree__item">
             <input id="clausePriTrustFlag" code="${Constants.CLAUSE_PRI_TRUST_CODE}" name="terms"  cdGroupId1=FORMREQUIRED  docVer="0"  type="checkbox"  value="Y" data-mand-yn="Y"class="c-checkbox c-checkbox--type2 _agree">
             <label class="c-label" for="clausePriTrustFlag">개인정보의 처리 업무 위탁 동의<span class="u-co-gray">(필수)</span>
             </label>
             <button class="c-button c-button--xsm" onclick="viewTerms('clausePriTrustFlag', 'cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_PRI_TRUST_CODE}');" >
               <span class="c-hidden">상세보기</span>
               <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
             </button>
           </div>
            <div class="c-agree__item">
             <input id="clauseConfidenceFlag" code="${Constants.CLAUSE_CONFIDENCE_CODE}"  name="terms"  cdGroupId1=FORMREQUIREDdocVer="0"  type="checkbox"  value="Y" data-mand-yn="Y" class="c-checkbox c-checkbox--type2 _agree">
             <label class="c-label" for="clauseConfidenceFlag">신용정보 조회 &middot; 이용 &middot; 제공에 대한 동의서<span class="u-co-gray">(필수)</span>
             </label>
             <button class="c-button c-button--xsm" onclick="viewTerms('clauseConfidenceFlag', 'cdGroupId1=FORMREQUIRED&cdGroupId2=${Constants.CLAUSE_CONFIDENCE_CODE}');"  >
               <span class="c-hidden">상세보기</span>
               <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
             </button>
           </div>
           <div class="c-agree__item">
             <input id="clausePriAdFlag" code="S${Constants.CLAUSE_PRI_AD_CODE}" name="terms"  cdGroupId1=FORMSELECT  docVer="0" type="checkbox"  value="Y" data-mand-yn="N" class="c-checkbox c-checkbox--type2 _agree"  >
             <label class="c-label" for="clausePriAdFlag">정보/광고 전송을 위한 개인정보 이용, 처리위탁 및   정보/광고 수신동의서<span class="u-co-gray">(선택)</span>
             </label>
             <button class="c-button c-button--xsm" onclick="viewTerms('clausePriAdFlag','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_PRI_AD_CODE}');" >
               <span class="c-hidden">상세보기</span>
               <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
             </button>
           </div>
           <div class="c-agree__item">
              <input id="clauseFinanceFlag" code="S${Constants.CLAUSE_FINANCE_FLAG}" name="terms"  cdGroupId1=FORMSELECT  docVer="0" type="checkbox"  value="Y" data-mand-yn="N"class="c-checkbox c-checkbox--type2 _agree"  >
              <label class="c-label" for="clauseFinanceFlag">개인(신용)정보 처리 및 보험가입 동의<span class="u-co-gray">(선택)</span>
              </label>
              <button class="c-button c-button--xsm" onclick="viewTerms('clauseFinanceFlag','cdGroupId1=FORMSELECT&cdGroupId2=${Constants.CLAUSE_FINANCE_FLAG}');" >
                <span class="c-hidden">상세보기</span>
                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
              </button>
            </div>
             <div class="c-agree__item">
              <input id="notice" code="F${Constants.NOTICE_CODE_16}" cdGroupId1=FORMINFO name="terms" docVer="0"  type="checkbox"  value="Y" data-mand-yn="N"class="c-checkbox c-checkbox--type2 hidden _agree">
              <label class="c-label" for="notice">서비스 이용약관<span class="u-co-gray">(고지)</span>
              </label>
              <button class="c-button c-button--xsm" onclick="viewTerms('notice','cdGroupId1=FORMINFO&cdGroupId2=${Constants.NOTICE_CODE_16}');" >
                <span class="c-hidden">상세보기</span>
                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
              </button>
            </div>
    	  </div>
		<div class="c-form _self">
         <span class="c-form__title" id="radC">셀프개통 안내사항</span>
         <div class="c-agree__item">
           <input class="c-checkbox c-checkbox--type2" id="clauseSimpleOpen" name="terms" type="checkbox" name="clauseSimpleOpen" data-mand-yn="Y" >
           <label class="c-label" for="clauseSimpleOpen">본인은 안내사항을 확인하였습니다.<span class="u-co-gray">(필수)</span>
           </label>
           <button id="btnClauseSimple" class="c-button c-button--xsm"  >
             <span class="c-hidden">상세보기</span>
             <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
           </button>
         </div>
       </div>
      <div class="c-button-wrap">
        <a class="c-button c-button--full c-button--gray"    href="javascript:;" onclick="sharing_cancel();">취소</a><!-- 활성화 시 .is-disabled 삭제-->
        <a class="c-button c-button--full c-button--primary is-disabled" href="javascript:;" id="sharing_reg" onclick="sharing_reg();">개통 요청</a>
      </div>
      <hr class="c-hr c-hr--type2">
      <b class="c-text c-text--type3">
        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
      </b>
      <p class="c-bullet c-bullet--dot u-co-gray">데이터 쉐어링으로 가입하는 회선은 통화가 불가능한 데이터 전용 회선으로 가입되기 때문에 회선 번호는 무작위로 부여됩니다.</p>
    </div>

   <div class="c-form">
     <span id="radIdCardHead" class="c-form__title--type2">신분증 확인</span>
     <div class="c-check-wrap c-check-wrap--type2" >
       <div class="c-chk-wrap">
         <input class="c-radio c-radio--button" id="selfCertType1" value="01" type="radio" name="selfCertType" >
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
         <input class="c-radio c-radio--button" id="selfCertType6" value="06" type="radio" name="selfCertType"  >
         <label class="c-label" for="selfCertType6">외국인 등록증</label>
       </div>
       <div class="c-chk-wrap">
         <input class="c-radio c-radio--button c-hidden" id="selfCertType5" value="05" type="radio" name="selfCertType" disabled >
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
     <div class="c-form c-scan-wrap">
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
         <input class="c-input" id="selfIssuNum" type="tel"  pattern="[0-9]*" placeholder="보훈번호 입력" title="보훈번호"  value="${AppformReq.selfIssuNumDesc}" >
         <label class="c-form__label" for="selfIssuNum">보훈번호</label>
       </div>
     </div>

     <div class="c-form c-form--datepicker">
       <div class="c-form__input " >
         <input class="c-input date-input" id="selfIssuExprDt" type="text" placeholder="YYYY-MM-DD" title="발급일자"  maxlength="10"   value="<fmt:formatDate value="${AppformReq.selfIssuExprDate}" pattern="yyyy-MM-dd"   />" >
         <label class="c-form__label" for="selfIssuExprDt">발급일자</label>
         <button class="c-button">
           <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
         </button>
       </div>
     </div>

     <input class="c-checkbox" id="selfInqryAgrmYnFlag" type="checkbox" >
     <label class="c-label u-mt--20" for="selfInqryAgrmYnFlag">본인 조회에 동의합니다</label>
     <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
       <li class="c-text-list__item">입력하신 정보는 한국정보통신진흥협회(KAIT)에서 제공하는 부정가입방지 시스템을 통해 신분증 진위여부 판단에 이용됩니다.</li>
     </ul>
   </div>
    
     <!-- PASS 인증 2단계 -->
    <c:set var="bankList" value="${nmcp:getCodeList('BNK')}" />
    <input type="hidden" id="requestNo" name="requestNo" >
    <input type="hidden" id="resUniqId" name="resUniqId" >
    
    <!--  계좌점유  Layer-->
   <div class="c-modal" id="bankAutDialog" role="dialog" tabindex="-1" aria-describedby="#bankAutTitle">

    <div class="c-modal__dialog c-modal__dialog--full" role="document">
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="bankAutTitle">계좌인증</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-indicator">
          <span style="width: 33.33%"></span>
        </div>

        <div id="divBankAut1" class="c-modal__body">
          <h3 class="c-heading c-heading--type1">계좌정보 입력</h3>
          <p class="c-text c-text--type2 u-co-gray"> 본인인증에 필요한 정보를 입력해 주세요.
            PASS 인증을 받은 실명과 계좌 명의자가 동일해야
            인증이 가능합니다.</p>
          <div class="c-form u-mt--0">
            <span class="c-form__title">은행/증권사</span>
            <div class="c-form__group" role="group" aria-labelledby="inpBankNum">
              <div class="c-form__select">
                <select id="reqBankAut" class="c-select">
                  <option value="">선택</option>
                  <c:forEach items="${bankList}" var="itemBank" >
                    <option value="${itemBank.dtlCd }">${itemBank.dtlCdNm }</option>
                  </c:forEach>
                </select>
                <label class="c-form__label">은행명</label>
              </div>
              <div class="c-form__input">
                <input class="c-input onlyNum" id="reqAccountAut" type="tel" name="" pattern="[0-9]*" placeholder="'-'없이 입력">
                <label class="c-form__label" for="reqAccountAut">계좌번호</label>
              </div>
              <div class="c-form__input has-value">
                <input class="c-input" id="cstmrNameTemp" type="text" placeholder=""  readonly >
                <label class="c-form__label" for="cstmrNameTemp">이름</label>
              </div>
            </div>
          </div><!-- 비활성화시 버튼에 .is-disabled 클래스 추가-->
          <div class="c-button-wrap">
            <!--[2021-12-02] 유틸클래스명 변경 -->
            <button class="c-button c-button--gray c-button--lg u-width--120" data-dialog-close>취소</button>
            <button id="btnAutAccount" class="c-button c-button--full c-button--primary">인증요청</button>
          </div>
        </div>


        <div id="divBankAut2" style="display:none;" class="c-modal__body">
          <h3 class="c-heading c-heading--type1">인증번호 입력</h3>
          <p class="c-text c-text--type2 u-co-gray"> 입력하신 계좌로 1원을 보내드렸습니다.
            <br>계좌 거래내역에서 1원의 입금자로 표시된
            <br>숫자 6자리를 입력해 주시기 바랍니다.
          </p>
          <div class="c-form u-mt--32">
            <div class="c-box u-mb--24">
              <img class="center-img" src="/resources/images/mobile/content/img_account_certification.png" alt="">
            </div>
            <div class="c-form__input">
              <input class="c-input onlyNum" id="textOpt"  maxlength="6" type="tel" name="" pattern="[0-9]*" placeholder="입금자 명에 표시된 숫자 6자리 입력">
              <label class="c-form__label" for="textOpt">인증번호</label>
            </div>
          </div><!-- 비활성화시 버튼에 .is-disabled 클래스 추가-->
          <div class="c-button-wrap">
            <!--[2021-12-02] 유틸클래스명 변경 -->
            <button class="c-button c-button--gray c-button--lg u-width--120" data-dialog-close>취소</button>
            <button id="btnCheckAccountOtpConfirm"  class="c-button c-button--full c-button--primary">인증하기</button>
          </div>
        </div>

        <div id="divBankAut3" style="display:none;" class="c-modal__body">
          <h3 class="c-heading c-heading--type1">인증완료</h3>
          <p class="c-text c-text--type2 u-co-gray"> 계좌 인증이 완료되었습니다.
            <br>확인 버튼을 누른 뒤 신청서를
            <br>계속 작성해주시기 바랍니다.
          </p><!-- 비활성화시 버튼에 .is-disabled 클래스 추가-->
          <div class="c-button-wrap">
            <button class="c-button c-button--full c-button--primary" data-dialog-close >확인</button>
          </div>
        </div>


      </div>
    </div>

   
    
       
</t:putAttribute>
</t:insertDefinition>