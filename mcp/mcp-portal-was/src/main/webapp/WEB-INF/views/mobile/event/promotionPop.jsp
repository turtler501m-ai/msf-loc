<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="proPopEventNm" value="${nmcp:getCodeNm('Constant','proPopEventNm')}" />

<script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
<script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
<script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
<script type="text/javascript" src="/resources/js/common/validator.js"></script>
<script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
<script type="text/javascript" src="/resources/js/mobile/event/promotionPop.js"></script>
<style>
#promoContainer {box-sizing:border-box;width:100%;}
/* .promo_check_info_box {padding:12px 24px 1px;} */
.promo_check_info_box .card_section {padding:20px 30px; background:#f9f9f9; border:1px solid #d3d3d3; color:#222; line-height:28px; font-size:16px;}
.promo_check_info_box .red { color:#ed1c24; margin:15px 0 10px 8px; padding:0;  font-size:14px;}
.promo_check_info_box .grey { color:#555; margin:-5px 0 40px 8px; padding:0; font-size:14px;}
.promo_check_info_box table tr th { width:45%; min-width:120px; background:#f9f9f9;padding: 16px 0 16px 20px;border-bottom:1px solid #d3d3d3;text-align:left;color:#666;vertical-align:top;}
.promo_check_info_box table tr th img .icon_check {margin:0 0 0 -20px; padding:0 0 0 20px; position:absolute;}
.promo_check_info_box table tr td button { vertical-align:middle; border-color: #d3d3d3; margin-left:-5px; }
.promo_check_info_box table .tit {font-size:18px; font-weight:bold; color:#000; border-bottom:1px solid #000; padding:12px 0 8px 0; margin:0; }

/* Form(입력용) table */
.board_detail_table table {width:100%;border-collapse:collapse;margin-bottom:20px}
.board_detail_table table tr {background:white}
.board_detail_table table tr th {min-width:120px;background:#f9f9f9;padding:16px 20px 16px 20px;border-bottom:1px solid #d3d3d3;text-align:left;color:#666;vertical-align:top}
.board_detail_table table tr th .icon_check {margin-right:10px}
.board_detail_table table tr th .icon_blank {padding-left:12px;margin-right:10px}
.board_detail_table table tr td {padding:10px 0 10px 20px;border-bottom:1px solid #d3d3d3;vertical-align:middle}
.board_detail_table table tr:last-child th {border-bottom:1px solid #d3d3d3}
.board_detail_table table tr:last-child td {border-bottom:1px solid #d3d3d3}
.board_detail_table table input[type="text"] {padding:5px 10px;line-height:18px;border:1px solid #d3d3d3;background:white;height:30px;width: 100%;}
.board_detail_table table input[type="password"] {padding:5px 10px;line-height:18px;border:1px solid #d3d3d3;background:white;height:30px;width: 100%;}
.board_detail_table table input[type="text"]:disabled {background-color:#f9f9f9; color:#333;width: 100%;}
/* .board_detail_table table input[type="text"]:focus {border-color:#e31f26;color:#e31f26} */
.board_detail_table table select {padding:5px 25px 5px 10px;line-height:18px;border:1px solid #d3d3d3;width: 100%;}
.board_detail_table table tr .mixed_input select {float:left}
.board_detail_table table tr .mixed_input input[type=text] {float:left}
.board_detail_table table tr .mixed_input > div {float:left;line-height:30px;vertical-align:middle}
.board_detail_table:after {content:"";display:block;clear:both}

.text_align_left {text-align:left}

select {-webkit-appearance: auto;-moz-appearance: auto;appearance: auto;}

</style>
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/front/common_new.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/front/style_new2.css"> -->

<div class="c-modal__content" id="promotionPopup">
    <div class="c-modal__header">
        <h2 class="c-modal__title" id="find-cvs-modal__title">${proPopEventNm}</h2>
        <button class="c-button" data-dialog-close id="closePop">
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
        </button>
    </div>
    <div class="c-modal__body">
        <div id="promoContainer">
            <div class="board_detail_table promo_check_info_box">
                <input type="hidden" id="eventCode" name="eventCode" value="${eventCode}">
                <table>
                    <caption class="tit text_align_left">등록정보<label class="red" style="font-weight:normal;">※필수 사항</label></caption>
                    <colgroup>
                        <col />
                        <col />
                    </colgroup>
                    <tr>
                        <th scope="row" valign="top"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">성명</th>
                        <td>
                            <input title="이름" id="name" name="name" type="text" value="" class="width_full margin_right_7"/>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">연락처</th>
                        <td>
                            <input title="연락처" type="text" id="phone" name="phone" value=""  class="width_full margin_right_7 numOnly" maxlength="11" />
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">생년월일</th>
                        <td>
                            <input title="생년월일" id="birthDate" name="birthDate" vale="" type="text" class="width_full margin_right_7 numOnly" maxlength="8" placeholder="8자리숫자만 입력"/>
                        </td>
                    </tr>
                </table>
                <table>
                    <caption class="tit text_align_left">기타정보
                        <label class="red" style="font-weight:normal;">※선택 사항</label>
                    </caption>
                    <colgroup>
                        <col />
                        <col />
                    </colgroup>
                    <tr>
                        <th scope="row">현재 이용중인 통신사</th>
                        <td>
                            <select id="useTelCode" name="useTelCode" title="현재 이용중인 통신사" class="width_full">
                                <option value="">통신사 선택</option>
                                <option value="SKT">SKT</option>
                                <option value="KT">KT</option>
                                <option value="LGU">LG U+</option>
                                <option value="ETC">알뜰폰</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">현재 요금제의 금액대</th>
                        <td>
                            <select  id="usePayAmt" name="usePayAmt" title="이용중인 요금제의 금액대" class="width_full">
                                <option value="1~3만원대">1~3만원대</option>
                                <option value="4~6만원대">4~6만원대</option>
                                <option value="7만원 이상">7만원 이상</option>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="c-accordion c-accordion--type5 acc-agree">
              <span class="c-form__title--type2 u-fs-18 u-fw--bold u-mt--28">약관동의</span>
              <div class="c-accordion__box">
                <div class="c-accordion__item u-pt--8 u-pb--0">
                  <div class="c-accordion__head">
                    <div class="c-accordion__check">
                      <input class="c-checkbox" id="btnStplAllCheck" type="checkbox">
                      <label class="c-label" for="btnStplAllCheck">이벤트 참여를 위한 이용약관, 개인정보 수집/이용 및 선택 동의 항목에 모두 동의합니다.<br /><span class="u-co-red _allcheck">※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?</span></label>
                    </div>
                    <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#acc_agreeA1">
                      <div class="c-accordion__trigger"> </div>
                    </button>
                  </div>
                  <div class="c-accordion__panel expand" id="acc_agreeA1">
                    <div class="c-accordion__inside">
                        <div class="c-accordion c-accordion--type5 promotion">
                          <div class="c-accordion__box">
                            <div class="c-accordion__item">
                              <div class="c-accordion__head">
                                <div class="c-accordion__check">
                                  <input class="c-checkbox c-checkbox--type2" id="priAgree" type="checkbox" name="terms" onclick="setChkbox(this)">
                                  <label class="c-label" for="priAgree">본인확인을 위한 개인정보 수집 및 이용 동의<span class="u-co-gray">(필수)</span></label>
                                </div>
                                <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#acc_agreeB1">
                                  <div class="c-accordion__trigger"> </div>
                                </button>
                              </div>
                              <div class="c-accordion__panel expand" id="acc_agreeB1">
                                <div class="c-accordion__inside">
                                    ${docContent1}
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="c-accordion c-accordion--type5 promotion">
                          <div class="c-accordion__box">
                            <div class="c-accordion__item">
                              <div class="c-accordion__head">
                                <div class="c-accordion__check">
                                  <input class="c-checkbox c-checkbox--type2" id="priEventAgree" type="checkbox" name="terms" onclick="setChkbox(this)">
                                     <label class="c-label" for="priEventAgree">이벤트 참여를 위한 개인정보 수집 및 이용 동의<span class="u-co-gray">(필수)</span></label>
                                </div>
                                <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#acc_agreeB2">
                                  <div class="c-accordion__trigger"> </div>
                                </button>
                              </div>
                              <div class="c-accordion__panel expand" id="acc_agreeB2">
                                <div class="c-accordion__inside">
                                    ${docContent2}
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="c-accordion c-accordion--type5 promotion">
                          <div class="c-accordion__box">
                            <div class="c-accordion__item">
                              <div class="c-accordion__head">
                                <div class="c-accordion__check">
                                   <input class="c-checkbox c-checkbox--type2" id="priMarketingAgree" type="checkbox" name="terms" onclick="setChkbox(this)">
                                    <label class="c-label" for="priMarketingAgree">마케팅활용을 위한 개인정보 수집 및 이용 동의<span class="u-co-gray">(선택)</span></label>
                                </div>
                                <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#acc_agreeB3">
                                  <div class="c-accordion__trigger"> </div>
                                </button>
                              </div>
                              <div class="c-accordion__panel expand" id="acc_agreeB3">
                                <div class="c-accordion__inside">
                                    ${docContent3}
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="c-accordion c-accordion--type5 promotion">
                          <div class="c-accordion__box">
                            <div class="c-accordion__item">
                              <div class="c-accordion__head">
                                <div class="c-accordion__check">
                                   <input class="c-checkbox c-checkbox--type2" id="priAdAgree" type="checkbox" name="terms" onclick="setChkbox(this)">
                                     <label class="c-label" for="priAdAgree">광고성 정보 수신 동의<span class="u-co-gray">(선택)</span></label>
                                     <p class="u-fs-14 u-co-gray u-mt--4 u-pl--32">※ 본 선택 동의는 거부 하실수 있으나, 미동의시 이벤트 응모에 참여 하실수 없습니다.</p>
                                </div>
                                <button class="c-accordion__button u-ta-right" type="button" aria-expanded="false" data-acc-header="#acc_agreeB4">
                                  <div class="c-accordion__trigger"> </div>
                                </button>
                              </div>
                              <div class="c-accordion__panel expand" id="acc_agreeB4">
                                <div class="c-accordion__inside">
                                    ${docContent4}
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
        </div>
    </div>
    <div class="c-modal__footer">
        <div class="c-button-wrap">
            <button class="c-button c-button--lg c-button--primary c-button--full" id="prePromotionBtn">등록하기</button>
        </div>
    </div>
</div>

<div class="c-modal__content" id="promotionModal" style="display:none;">
  <div class="c-modal__header">
      <h2 class="c-modal__title" id="find-cvs-modal__title">프로모션 신청하기</h2>
      <button class="c-button" id="promotionCancelPop">
                <i class="c-icon c-icon--close" aria-hidden="true"></i>
                <span class="c-hidden">팝업닫기</span>
      </button>
  </div>
  <div class="c-modal__body">
      <div class="u-ta-center u-mt--12">
           <p class="u-fw--medium">본 이벤트 참여를 위해서는<br/>마케팅활용을 위한 개인정보 수집 및 이용 동의, 광고성 정보 수신 동의가 필요합니다.</p>
      </div>
      <div class="c-button-wrap u-mt--32">
          <button class="c-button c-button--full c-button--gray" id="promotionCancelbtn">취소</button>
          <button class="c-button c-button--full c-button--primary" id="promotionNiceAuthBtn">휴대폰인증</button>
      </div>
  </div>
</div>

