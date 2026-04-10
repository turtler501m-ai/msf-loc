<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<un:useConstants var="Constants"
                 className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/mobile/review/mcashReviewForm.js"></script>
  </t:putAttribute>
  <t:putAttribute name="contentAttr">
    <input type="hidden" id="makeLength" value="${fn:length(makeReviewList)}" />
    <input type="hidden" id="InfoYn" value="" />
    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">
          M쇼핑할인 후기 작성하기
          <button class="header-clone__gnb"></button>
        </h2>
      </div>

      <input type="hidden" id="menuType" value="mcashReviewReg" />
      <div class="c-tabs c-tabs--type2" data-tab-active="1">
        <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
          <button class="c-tabs__button" onclick="javascript:location.href='/m/mcash/review/mcashReview.do';" data-tab-header="#tabA1-panel">사용후기 보기</button>
          <button class="c-tabs__button" onclick="javascript:location.href='/m/mcash/review/mcashReviewForm.do';" data-tab-header="#tabA2-panel">후기 작성하기</button>
        </div>
        <div class="c-tabs__panel" id="tabA1-panel"></div>
        <div class="c-tabs__panel" id="tabA2-panel">
          <div class="c-box c-box--type3 mcash">
            <strong class="c-box c-box--type3__title">고객님의 솔직하고 리얼한</span><br>리뷰를 남겨주세요.</strong>
            <p class="c-box c-box--type3__text u-co-white">
              고객님들의 소중한 의견을 통해 보다 나은<br>M모바일로 보답할게요!
            </p>
            <div class="c-box c-box--type3__image">
              <img src="/resources/images/mobile/product/mcash_review_banner.png" alt="">
            </div>
          </div>
          <div class="c-form">
            <span class="c-form__title" id="chkRecomTypeType">M쇼핑할인의 사용후기를 공유해주세요.</span>
          </div>
          <div class="c-form">
            <span class="c-form__title" id="selPostType">후기 작성</span>
            <div class="c-form__group" role="group" aria-labelledby="inpG">
              <input type="hidden" id="phone" name="phone" value="${phone}" />
              <input type="hidden" id="cntrNum" name="cntrNum" value="${cntrNum}" />

              <div class="c-form__select has-value">
                <select class="c-select" name="eventCd">
                  <c:forEach var="evList" items="${eventList}">
                    <option value="${evList.dtlCd}">${evList.dtlCdNm}</option>
                  </c:forEach>
                </select>
                <label class="c-form__label">이벤트 선택</label>
              </div>
              <p class="c-bullet c-bullet--dot u-co-gray">월 중 프로모션은
                <button class="c-text-link--bluegreen" id="eventLink">이벤트페이지</button>를 확인해주세요.
              </p>

              <div class="c-textarea-wrap u-mt--0">
                <span class="c-form__title" id="reviewSbst">사용후기</span>
                <textarea class="c-textarea" placeholder="${eventHolder.docContent}" name="reviewContent"></textarea>
                <div class="c-textarea-wrap__sub">
                  <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span> <span><span id="txtCnt">0</span>/800</span>
                </div>
              </div>
              <ul class="c-text-list c-bullet c-bullet--dot">
                <li class="c-text-list__item u-co-gray">M쇼핑할인 사용후기는 M쇼핑할인에 가입된 고객만 가능합니다.</li>
                <li class="c-text-list__item u-co-gray">M쇼핑할인 사용후기는 1인 1회 작성가능합니다.<br/>(한 회선당 후기 1회 작성 가능)</li>
                <li class="c-text-list__item u-co-gray">욕설/비방/광고성 글이나 부적절한 내용의 경우 삭제 될 수 있습니다.</li>
              </ul>
            </div>
          </div>
          <div class="c-form">
            <span class="c-form__title" id="inpShrType">SNS공유<span class="u-co-gray">(선택)</span></span>
            <div class="c-form__group" role="group" aria-labelledby="inpG">
              <input class="c-input" type="text" name="snsInfo" placeholder="사용 후기의 URL을 입력해주세요" title="URL 입력" maxlength="80">
            </div>
          </div>
          <div class="c-form" id="fileContainer">
            <span class="c-form__title" id="fileUpload">이미지 업로드<span class="u-co-gray">(선택)</span></span>
            <div class="c-form__group" role="group" aria-labelledby="imageUploadGroup" id="imageUploadGroup">
              <div class="upload-image" id="imageDiv">
                <input type="hidden" id="maxImage" value="4">
                <label class="upload-image__label" index="1">
                  <span class='c-hidden'>파일선택</span>
                  <input class="upload-image__hidden" key="file1" name="file" type="file" id="fileTarget1" accept=".jpg, .gif, .png">
                  <i class="c-icon c-icon--photo" aria-hidden="true"></i>
                  <span class="u-co-gray">사진 0 / 4</span>
                  <button class="upload-image__delete" style="display: none;">
                    <i class="c-icon c-icon--delete" aria-hidden="true"></i>
                    <span class="c-hidden">삭제</span>
                  </button>
                  <div class="upload-complete" id="file1"></div>
                </label>
              </div>
              <div id="altDiv" style="margin-top: 0.5rem;"></div>
            </div>
          </div>
          <p class="c-bullet c-bullet--fyr u-co-gray">1장당 2MB 이내의 jpg, gif, png 형식으로 최대4장까지 등록가능</p>
          <div class="c-button-wrap">
            <button class="c-button c-button--lg c-button--full c-button--primary" id="certifyPopBtn">후기 작성하기</button>
          </div>
        </div>
      </div>
    </div>



    <script src="../../resources/js/mobile/vendor/swiper.min.js"></script>

  </t:putAttribute>
</t:insertDefinition>