<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<!-- <meta http-equiv="Content-Security-Policy" content="img-src 'self' data:; default-src 'self' https://local.portal.ktmmobile.com/requestReView/requestReviewForm.do">
-->

<t:insertDefinition name="layoutMainDefault">

  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/portal/review/mcashReviewForm.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mobile/vendor/swiper.min.js"></script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">
    <input type="hidden" id="makeLength" value="${fn:length(makeReviewList)}" />
    <input type="hidden" id="InfoYn" value="" />
    <div id="subbody_loading" style="display: flex; width: 100%; height: calc(100vh - 130px); box-sizing: border-box; background: rgb(255, 255, 255); text-align: center; position: relative; align-items: center; justify-content: center; z-index: 10;">
      <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
    </div>
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">M쇼핑할인 후기 작성하기</h2>
      </div>
      <div class="c-tabs c-tabs--type1">
        <div class="c-tabs__list">
          <a class="c-tabs__button" id="tab1" role="tab" aria-selected="false" href="/mcash/review/mcashReview.do">사용후기 보기</a>
          <a class="c-tabs__button is-active" id="tab2" role="tab" aria-selected="true" href="/mcash/review/mcashReviewForm.do"><h3>후기 작성하기</h3></a>
        </div>
      </div>
      <div class="c-tabs__panel">
        <div></div>
      </div>
      <div class="c-tabs__panel u-block">
        <div>
          <div class="ly-page--deco mcash">
            <div class="ly-avail--wrap">
              <img src="/resources/images/portal/product/mcash_review_banner.png" alt="고객님의 솔직하고 리얼한 리뷰를 남겨주세요. 고객님들의 소중한 의견을 통해 보다 나은 M모바일로 보답할게요! 후기 작성하고 선물받기">
            </div>
          </div>
          <div class="c-row c-row--lg">
            <p class="c-heading c-heading--fs20 u-mt--56">M쇼핑할인의 사용후기를 공유해주세요.</p>
            <hr class="c-hr c-hr--title">
            <div class="c-form-wrap u-mt--48">
              <input type="hidden" id="isLogin" value="Y" />
              <input type="hidden" id="phone" name="phone" value="${phone}" />
              <input type="hidden" id="name" name="name" value="${name}" />
              <input type="hidden" id="cntrNum" name="cntrNum" value="${cntrNum}" />

              <div class="c-form u-width--460">
                <label class="c-label" for="select">이벤트 선택</label>
                <select class="c-select" id="select" name="eventCd">
                  <c:forEach var="evList" items="${eventList}">
                    <option value="${evList.dtlCd}">${evList.dtlCdNm}</option>
                  </c:forEach>
                </select>
              </div>
              <p class="c-bullet c-bullet--dot u-co-gray">월 중 프로모션은
                <button class="c-text-link--bluegreen" id="eventLink">이벤트페이지</button>를 확인해주세요.
              </p>

              <div class="c-form u-mt--48">
                <label class="c-label" for="textareaA">사용후기</label>
                <div class="c-textarea-wrap">
                    <%--									<textarea class="c-textarea" id="textareaA" name="reviewSbst" placeholder="※ 당첨확률 높이는 방법&#10;--%>
                    <%--① 가입/개통과정과 실제 사용경험을 생생하게 들려주세요! (ex. 셀프개통으로 쉽게 개통했어요, 통신요금을 월 3만원 절약했어요 등 사용상의 장점이 구체적으로 잘 드러나도록 작성)&#09;--%>
                    <%--② 관련 사진을 함께 업로드 해주세요! (ex. 구매한 유심 사진, 휴대폰을 개통하는 사진, 월 요금 캡쳐 사진, 가입설명서 사진 등)&#09;--%>
                    <%--③ SNS로 사진과 해시태그를 함께 공유해주시고, 해당 URL을 남겨주세요! (팔로워가 많거나 활성화 되어있는 SNS일수록 우수후기 당첨확률이 높아집니다.)" style="ime-mode: active;"></textarea>--%>
                  <textarea class="c-textarea" id="textareaA" name="reviewContent" placeholder="${eventHolder.docContent}" style="ime-mode: active;"></textarea>

                  <div class="c-textarea-wrap__sub">
                    <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
                    <span><span id="txtCnt">0</span>/800</span>
                  </div>
                </div>
                <ul class="c-text-list c-bullet c-bullet--dot">
                  <li class="c-text-list__item u-co-gray">M쇼핑할인 사용후기는 M쇼핑할인에 가입된 고객만 가능합니다.</li>
                  <li class="c-text-list__item u-co-gray">M쇼핑할인 사용후기는 1인 1회 작성가능합니다. (한 회선당 후기 1회 작성 가능)</li>
                  <li class="c-text-list__item u-co-gray">욕설/비방/광고성 글이나 부적절한 내용의 경우 삭제 될 수 있습니다.</li>
                </ul>
              </div>
              <div class="c-form u-mt--48">
                <div class="c-input-wrap">
                  <label class="c-label" for="inputA">SNS공유 URL<span class="c-form__sub">(선택)</span></label>
                  <input class="c-input" id="inputA" type="text" name="snsInfo" placeholder="사용 후기의 URL을 입력해주세요." title="SNS공유 URL 입력" maxlength="80">
                </div>
              </div>
              <div class="c-form-group u-mt--48" role="group" aria-labelledby="selectGroupHeadB">
                <div class="c-group--head" id="selectGroupHeadB">
                  <span>이미지 업로드<span class="c-form__sub">(선택)</span></span>
                </div>
                <div style="display: flex">
                  <div class="c-file-image">
                    <input class="c-file upload-image__hidden" key="file1" id="file1" name="file" type="file" title="사진 등록" accept='.jpg, .gif, .png' onchange="setThumb(event, 'file1');">
                    <input class="c-file upload-image__hidden" key="file2" id="file2" name="file" type="file" title="사진 등록" accept='.jpg, .gif, .png' onchange="setThumb(event, 'file2');">
                    <input class="c-file upload-image__hidden" key="file3" id="file3" name="file" type="file" title="사진 등록" accept='.jpg, .gif, .png' onchange="setThumb(event, 'file3');">
                    <input class="c-file upload-image__hidden" key="file4" id="file4" name="file" type="file" title="사진 등록" accept='.jpg, .gif, .png' onchange="setThumb(event, 'file4');">
                    <label class="c-label file-label" for="file1"><span class="sr-only">업로드한</span> <span class="c-label__sub">사진 0/4</span></label>
                  </div>
                  <div class="staged_img-list" style="display: flex; flex-wrap: wrap; gap: 20px; margin-left: 20px"></div>
                </div>
                <p class="c-bullet c-bullet--fyr u-co-gray" style="display: block">1장당 2MB 이내의 jpg, gif, png 형식으로 최대 4장까지 등록 가능</p>
              </div>
            </div>
            <div class="c-button-wrap u-mt--56">
              <a class="c-button c-button--lg c-button--primary u-width--460" href="javascript:void(0)" id="certifyPopBtn">후기 작성하기</a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <button id="consentInfos" data-dialog-trigger="#modalAgree" style="display: none;"></button>
  </t:putAttribute>

</t:insertDefinition>