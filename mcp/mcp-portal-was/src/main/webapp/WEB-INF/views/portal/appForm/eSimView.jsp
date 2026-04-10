<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">eSIM 개통신청</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/appForm/eSimView.js"></script>
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
                            <!-- <div class="c-form__input-group is-readonly u-mt--0">
                                <div class="c-form__input c-form__input-division">
                                    <input class="c-input--div" id="test_01" name="test_01" type="text" placeholder="휴대폰 모델명" readOnly>
                                    <label class="c-form__label" for="test_01">휴대폰 모델명</label>
                                </div>
                            </div> -->
                            <div class="c-form__input-group is-readonly u-mt--0">
                                <div class="c-form__input c-form__input-division">
                                    <input class="c-input--div" id="eid" name="eid" type="text" placeholder="EID" readOnly>
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
                    <label for="image" class="c-button c-button-round--md c-button--mint u-width--460">이미지 등록</label>
                    <input type="file" class="c-hidden ocrImg" id="image" name="image" accept="image/jpg, image/png, image/jpeg, image/tif, image/tiff, image/bmp" >
                </div>
                <div class="c-button-wrap u-mt--56">
                    <a class="c-button c-button--lg c-button--gray u-width--220" href="/appForm/eSimInfo.do">취소</a>
                    <button class="c-button c-button--lg c-button--primary u-width--220" id="nextStep">다음</button>
                </div>
            </div>
        </div>

        <!-- 이미지 등록 후 -->
        <div class="c-modal c-modal--lg" id="esim-check-modal" role="dialog" aria-labelledby="esim-check-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="esim-check-modal__title">eSIM 사용 슬롯 확인</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <ul class="c-text-list c-bullet c-bullet--dot">
                            <li class="c-text-list__item u-co-red u-mt--0">휴대폰 정보가 올바른지 반드시 확인하시기 바랍니다.</li>
                            <li class="c-text-list__item u-co-gray">입력정보가 다를 경우 취소 후 이미지를 재 등록하시기 바랍니다.</li>
                        </ul>
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
                                        <td id="eidTxt">1</td>
                                    </tr>
                                    <tr>
                                        <td>IMEI1</td>
                                        <td id="imei1Txt">2</td>
                                    </tr>
                                    <tr>
                                        <td>IMEI2</td>
                                        <td id="imei2Txt">3</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="c-text-box c-text-box--red u-mt--28" id="errTxt" style="display:none;"></div>

                        <!-- <div class="c-form-wrap u-mt--48">
                            <div class="c-form-group" role="group" aira-labelledby="PhoneModelHead">
                                <h3 class="c-group--head" id="PhoneModelHead">휴대폰 모델명 선택</h3>
                                <div class="c-form-row c-col2">
                                    <div class="c-form-field">
                                        <div class="c-form__select">
                                            <select class="c-select" id="PhoneNm">
                                                <option value="">휴대폰 선택</option>
                                                <option value="애플">애플</option>
                                                <option value="삼성">삼성</option>
                                                <option value="LG">LG</option>
                                            </select>
                                            <label class="c-form__label" for="PhoneNm">휴대폰 선택</label>
                                        </div>
                                    </div>
                                    <div class="c-form-field">
                                        <div class="c-form__select">
                                            <select class="c-select" id="PhoneModel">
                                                <option value="">모델명 선택</option>
                                                <option value="iPhone 13">iPhone 13</option>
                                                <option value="iPhone 12">iPhone 12</option>
                                            </select>
                                            <label class="c-form__label" for="PhoneModel">모델명 선택</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div> -->

                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--lg c-button--gray u-width--220" data-dialog-close>다시 등록하기</button>
                            <button class="c-button c-button--lg c-button--primary u-width--220" id="setBtn" data-dialog-close>확인</button>
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
    </t:putAttribute>
</t:insertDefinition>
