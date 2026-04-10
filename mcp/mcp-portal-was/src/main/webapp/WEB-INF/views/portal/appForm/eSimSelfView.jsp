<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">eSIM 개통신청</t:putAttribute>
    <t:putAttribute name="googleTagScript">
        <!-- Event snippet for 가입신청시작_PC (1) conversion page -->
        <script>
            gtag('event', 'conversion', {'send_to': 'AW-862149999/7VhHCKzjxLYZEO-6jZsD'});
        </script>
    </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/appForm/eSimSelfView.js?version=23-11-17"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/appFormOcr.js?version=24.01.11"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">휴대폰 EID 등록</h2>
            </div>
            <div class="c-row c-row--lg">
                <h4 class="c-heading c-heading--fs20 c-heading--regular" id="eSimePhoneInfo">휴대폰정보 입력</h4>
                <hr class="c-hr c-hr--title">
                <div class="c-box c-box--type1">
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item u-co-gray">eSIM 개통을 위해서는 EID등록이 선행되어야 하며, IMEI 및 32자리 EID 캡쳐화면 이미지 업로드가 필요합니다.</li>
                        <li class="c-text-list__item u-co-gray">이미지 업로드 시 자동으로 입력됩니다.<a class="c-text-link--bluegreen" data-dialog-trigger="#uploadguide-modal">이미지 가이드 보기</a></li>
                    </ul>
                </div>
                <div class="c-form-wrap u-mt--32">
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
                <div class="c-button-wrap u-mt--40">
                	<input type="file" class="c-hidden ocrImg" id="image" name="image" accept="image/jpg, image/png, image/jpeg, image/tif, image/tiff, image/bmp" >
                    <label for="image" class="c-button c-button-round--md c-button--mint u-width--460">이미지 등록</label>
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


                <div class="c-button-wrap u-mt--56">
                    <a class="c-button c-button--lg c-button--gray u-width--220" href="/appForm/eSimInfom.do">취소</a>
                    <button class="c-button c-button--lg c-button--primary u-width--220" onclick="javascript:;" id="nextStep">다음</button>
                </div>
            </div>
        </div>

        <!-- 이미지 등록 후 -->
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
                                    <col width="30%">
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
                                </tbody>
                            </table>
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



<input type="hidden" id="uploadPhoneSrlNo" name="uploadPhoneSrlNo" value="">
<input type="hidden" id="modelId" name="modelId" value="">
<input type="hidden" id="intmSrlNo" name="intmSrlNo" value="">
<input type="hidden" id="resultCode" name="resultCode" value="">
<input type="hidden" id="moveTlcmIndCd" name="moveTlcmIndCd" value="">
<input type="hidden" id="moveCmncGnrtIndCd" name="moveCmncGnrtIndCd" value="">
<input type="hidden" id="moveCd" name="moveCd" value="">


    </t:putAttribute>
</t:insertDefinition>
