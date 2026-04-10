<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<t:insertDefinition name="layoutGnbDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/bluebird.core.js"></script>
		<script type="text/javascript" src="/resources/js/portal/vendor/swiper.min.js"></script>
		<script type="text/javascript" src="/resources/js/common/validationCheck.js"></script>
		<script type="text/javascript" src="/resources/js/sns.js"></script>
		<script type="text/javascript" src="/resources/js/portal/rate/adsvcGdncList.js"></script>
	</t:putAttribute>


	<t:putAttribute name="contentAttr">
		<div class="ly-loading" id="subbody_loading">
			<img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
		</div>

		<input type="hidden" id="userDivision" value="${userDivision}">
		<div class="ly-content" id="main-content">
			<div class="ly-page--title">
				<h2 class="c-page--title">부가서비스</h2>
			</div>
			<div class="c-row c-row--lg">
				<div class="c-filter">
					메인 탭
					<div id="mainTab" class="c-filter__inner">
					</div>
					<div class="c-form-wrap" style="display:flex; justify-content:space-between;">
					  <div id="totalNum" class="c-form__title u-ml--32">
					  </div>
					  <div class="c-form-group" role="group" aira-labelledby="chk_gh003">
					    <div class="c-checktype-row">
					      <input class="c-checkbox c-checkbox--type3" id="chkFree" type="checkbox">
					      <label class="c-label u-mr--36" for="chkFree">무료만 보기</label>
					      <input class="c-checkbox c-checkbox--type3" id="chkSelf" type="checkbox">
					      <label class="c-label u-mr--24" for="chkSelf">셀프가입 가능</label>
					    </div>
					  </div>
					</div>
				</div>
			</div>
			
			<%-- 목록 --%>
			<div id="listPanel" class="c-row c-row--lg">
			</div>
		</div>

		<%-- main 탭 구분값 --%>
	    <input type="hidden" id="mainTabRateAdsvcCtgCd" name="mainTabRateAdsvcCtgCd">
	    <input type="hidden" id="paramRateAdsvcCtgCd" name="paramRateAdsvcCtgCd" value="${paramRateAdsvcCtgCd}">
	    <input type="hidden" id="paramRateAdsvcGdncSeq" name="paramRateAdsvcGdncSeq" value="${paramRateAdsvcGdncSeq}">
	</t:putAttribute>
	
	<t:putAttribute name="popLayerAttr">


        <!-- 회선 번호 입력 팝업  -->
        <!-- <div class="c-modal c-modal--md" id="divCtnInput" role="dialog" aria-labelledby="divCtnInputTitle">
            <div class="c-modal__dialog" role="document">

                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <div class="c-modal__title" id="divCtnInputTitle">가입할 회선 입력</div>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
						<div class="c-form-field">
			                <div class="c-form__input-group u-mt--0">
			                  	<div class="c-form__input c-form__input-division">
			                    	<input class="c-input--div3 onlyNum" id="txtPhoneF" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰 첫자리" onkeyup="nextFocus(this, 3, 'txtPhoneM');">
			                    	<span>-</span>
			                    	<input class="c-input--div3 onlyNum" id="txtPhoneM" type="text" maxlength="4" pattern="[0-9]" placeholder="숫자입력" title="휴대폰 중간자리" onkeyup="nextFocus(this, 4, 'txtPhoneL');">
			                    	<span>-</span>
			                    	<input class="c-input--div3 onlyNum" id="txtPhoneL" type="text" maxlength="4" pattern="[0-9]" placeholder="숫자입력" title="휴대폰 뒷자리">
			                    	<label class="c-form__label" for="inpRecieverPhone">휴대폰 번호</label>
			                  	</div>
			                </div>
			            </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--gray c-button--w220"  data-dialog-close>취소</button>
                            <button id="btnCtnInput" class="c-button c-button--lg c-button--primary c-button--w220" data-dialog-close>확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div> -->
        
        

    </t:putAttribute>
    
</t:insertDefinition>
