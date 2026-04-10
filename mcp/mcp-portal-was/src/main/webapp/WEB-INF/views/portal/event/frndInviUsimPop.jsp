<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>


<script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
<script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
<script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
<script type="text/javascript" src="/resources/js/common/validator.js"></script>
<script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
<script type="text/javascript" src="/resources/js/portal/event/frndInviUsimPop.js"></script>
<style>
#promoContainer {box-sizing:border-box;width:100%;}
/* .promo_check_info_box {padding:12px 24px 1px;} */
.promo_check_info_box .card_section {padding:20px 30px; background:#f9f9f9; border:1px solid #d3d3d3; color:#222; line-height:28px; font-size:16px;}
.promo_check_info_box .red { color:#ed1c24; margin:15px 0 10px 8px; padding:0;  font-size:14px;}
.promo_check_info_box .grey { color:#555; margin:-5px 0 40px 8px; padding:0; font-size:14px;}
.promo_check_info_box table tr th { width:20%; min-width:120px; background:#f9f9f9;padding: 16px 0 16px 20px;border-bottom:1px solid #d3d3d3;text-align:left;color:#666;vertical-align:top;}
.promo_check_info_box table tr th img .icon_check {margin:0 0 0 -20px; padding:0 0 0 20px; position:absolute;}
.promo_check_info_box table tr td button { vertical-align:middle; border-color: #d3d3d3; margin-left:-5px; }
.promo_check_info_box table .tit {font-size:18px; font-weight:bold; color:#000; border-bottom:1px solid #000; padding:12px 0 8px 0; margin:0; }

/* Form(입력용) table */
.board_detail_table table {width:100%;border-collapse:collapse;margin-bottom:20px}
.board_detail_table table tr {background:white}
.board_detail_table table tr th {min-width:120px;background:#f9f9f9;padding:16px 0 16px 20px;border-bottom:1px solid #d3d3d3;text-align:left;color:#666;vertical-align:top}
.board_detail_table table tr th .icon_check {margin-right:10px}
.board_detail_table table tr th .icon_blank {padding-left:12px;margin-right:10px}
.board_detail_table table tr td {padding:10px 0 10px 20px;border-bottom:1px solid #d3d3d3;vertical-align:middle}
.board_detail_table table tr:last-child th {border-bottom:1px solid #d3d3d3}
.board_detail_table table tr:last-child td {border-bottom:1px solid #d3d3d3}
.board_detail_table table input[type="text"] {padding:5px 10px;line-height:18px;border:1px solid #d3d3d3;background:white;height:30px;width: 100%;}
.board_detail_table table input[type="password"] {padding:5px 10px;line-height:18px;border:1px solid #d3d3d3;background:white;height:30px}
.board_detail_table table input[type="text"]:disabled {background-color:#f9f9f9; color:#333;}
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

<div class="c-modal__content">
	<div class="c-modal__header">
		<h2 class="c-modal__title" id="find-cvs-modal__title">친구초대 유심배송</h2>
		<button class="c-button" data-dialog-close id="closePop">
			<i class="c-icon c-icon--close-black" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">
		<div id="promoContainer">
			<div class="board_detail_table promo_check_info_box">
				<table>
					<caption class="tit text_align_left">추천인 정보<label class="red" style="font-weight:normal;">※필수 사항</label></caption>
					<colgroup>
						<col />
						<col />
					</colgroup>
					<tr>
						<th scope="row" valign="top"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">성명</th>
						<td><input title="이름" id="name" name="name" type="text" class="width_full margin_right_7" maxlength="10"/></td>
					</tr>
					<tr>
						<th scope="row"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">연락처</th>
						<td>
							<input title="연락처" type="text" id="phone" name="phone" class="width_full margin_right_7 numOnly" maxlength="11" />
						</td>
					</tr>
					<tr>
						<th scope="row"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">추천ID</th>
						<td>
							<input title="추천ID" id="commendId" name="commendId" type="text" class="width_full margin_right_7" maxlength="10"/>
						</td>
					</tr>
				</table>
				<table>
					<caption class="tit text_align_left">피추천인 정보<label class="red" style="font-weight:normal;">※필수 사항</label></caption>
					<colgroup>
						<col />
						<col />
					</colgroup>
					<tr>
						<th scope="row" valign="top"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">성명</th>
						<td><input title="이름" id="frndName" name="frndName" type="text" class="width_full margin_right_7" maxlength="10"/></td>
					</tr>
					<tr>
						<th scope="row"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">연락처</th>
						<td>
							<input title="연락처" type="text" id="frndPhone" name="frndPhone" class="width_full margin_right_7 numOnly" maxlength="11" />
						</td>
					</tr>
					<tr>
						<th scope="row"><img src="/resources/images/company/iconCheck.png" class="icon_check" alt="필수입력항목">배송지</th>
						<td>
							<div class="c-form-wrap">
								<div class="c-form-group" role="group" aira-labelledby="inputAddressHead">
									<div class="c-form-row c-col2">
									<div class="c-form">
										<div class="c-input-wrap u-mt--0">
											<label class="c-form__label c-hidden" for="frndPost">우편번호</label>
											<input class="c-input" id="frndPost" name="frndPost" type="text" placeholder="우편번호" readonly>
											<button id="btnAddr" class="c-button c-button--sm c-button--underline _btnAddr" addrFlag="1">우편번호찾기</button>
										</div>
									</div>
									<div class="c-form">
										<div class="c-input-wrap u-mt--0">
											<label class="c-form__label c-hidden" for="frndAddr">주소</label>
											<input class="c-input" id="frndAddr" name="frndAddr" type="text" placeholder="주소" readonly>
										</div>
									</div>
									</div>
									<div class="c-form u-mt--16">
										<label class="c-label c-hidden" for="frndAddrDtl">상세주소</label>
										<input class="c-input" id="frndAddrDtl" name="frndAddrDtl" type="text" placeholder="상세주소" maxlength="100">
									</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>

			<div class="c-form-wrap u-mt--40">
				<div class="c-agree c-agree--type2">
					<div class="c-agree__item">
						<input class="c-checkbox" id="agree1" value="Y" type="checkbox" name="agree1" title="개인정보 수집 및 마케팅 정보 이용에 대한 수신 동의(필수)">
						<label class="c-label" for="agree1">
							개인정보 수집이용 동의<span class="u-co-gray">(필수)</span>
						</label>
					</div>
				${docContent1}
<!-- 					<div class="c-agree__inside"> -->
<!-- 						<ul class="c-text-list c-bullet c-bullet--number"> -->
<!-- 							<li class="c-text-list__item u-mt--0">수집 이용의 목적 <p class="c-bullet c-bullet--dot u-co-gray">제휴신청에 따른 업체확인 및 원활한 의사소통 경로 확보</p></li> -->
<!-- 							<li class="c-text-list__item u-mt--16">수집항목<p class="c-bullet c-bullet--dot u-co-gray">제안자명, 연락처, 이메일</p></li> -->
<!-- 							<li class="c-text-list__item u-mt--16">이용 및 보유기간<p class="c-bullet c-bullet--dot u-co-gray">모든 검토가 완료된 후 3개월 간 이용자의 조회를 위해 보관하며, 이후 해당정보를 지체 없이 파기.</p></li> -->
<!-- 						</ul> -->
<!-- 					</div> -->
				</div>
			</div>
			<div class="c-form-wrap u-mt--40">
				<div class="c-agree c-agree--type2">
					<div class="c-agree__item">
						<input class="c-checkbox" id="agree2" value="Y" type="checkbox" name="agree2" title="고객 혜택정보 및 광고수신을 위한 개인정보 처리위탁 동의(필수)">
						<label class="c-label" for="agree2">
							고객 혜택정보 및 광고수신을 위한 개인정보 처리위탁 동의<span class="u-co-gray">(필수)</span>
						</label>
					</div>
				${docContent2}
				</div>
			</div>
		</div>

	</div>
	<div class="c-modal__footer">
		<div class="c-button-wrap">
			<button class="c-button c-button--lg c-button--white c-button--full c-button--w460" id="btnCancel">취소</button>
			<button class="c-button c-button--lg c-button--primary c-button--full c-button--w460" id="btnApply">확인</button>
		</div>
	</div>
</div>
