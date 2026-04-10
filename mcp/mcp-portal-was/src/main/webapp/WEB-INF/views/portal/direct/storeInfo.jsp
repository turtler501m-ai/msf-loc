<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="layoutGnbDefaultTitle">
    <t:putAttribute name="titleAttr">편의점/마트 구매하기</t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script src="../../resources/js/portal/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="/resources/js/portal/usim/storeInfo.js?version=24-07-01"></script>
        <script type="text/javascript"
            src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e302056d1b213cb21f683504be594f25&libraries=services,clusterer"></script>
        <script>
        	document.addEventListener('UILoaded', function() {
              KTM.swiperA11y('#swiperMarket', {
                autoplay: {
                  delay: 3000,
                  disableOnInteraction: false,
                },
              });
            });

        	setTimeout(function(){
        		$('#swiperMarket .swiper-slide').removeAttr('tabindex');
        	},100);
          </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
          <div class="ly-page--title">
            <h2 class="c-page--title">편의점/마트 구매하기</h2>
          </div>
          <div class="ly-content--wrap">
            <div class="swiper-container" id="swiperMarket" ${empty topBannerList ? 'hidden' : ''}>
              <ul class="swiper-wrapper">
                  <c:forEach var="topList" items="${topBannerList}">
                      <li class="swiper-slide" tabindex="0" aria-label="" style="background-color: ${topList.bgColor}">
                      <div class="u-box--w1100 u-margin-auto" href="javascript:void(0);" onclick="insertBannAccess('${topList.bannSeq}', '${topList.bannCtg}')" style="${empty topList.linkUrlAdr ? 'pointer-events:none;' : ''}">
                        <a href="${empty topList.linkUrlAdr ? 'javascript:void(0)' : topList.linkUrlAdr}" target="${topList.linkTarget eq 'Y' ? '_blank' : '_self'}" title="${topList.linkTarget eq 'Y' ? '새창열림' : ''}">
                          <img src="${topList.bannImg}" alt="${topList.imgDesc}">
                        </a>
                      </div>
                    </li>
                  </c:forEach>
              </ul>
              <div class="swiper-controls-wrap u-box--w1100" ${fn:length(topBannerList) < 2 ? 'hidden' : ''}>
                <div class="swiper-controls">
                  <button class="swiper-button-pause" type="button">
                    <span class="c-hidden">자동재생 정지</span>
                  </button>
                  <div class="swiper-pagination" aria-hidden="true"></div>
                </div>
              </div>
            </div>
          </div>
          <div class="c-row c-row--lg" ${empty midBannerList ? 'hidden' : ''}>
            <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">편의점/마트 유심 판매처</h3>
              <p class="u-mt--16 u-fs-17">※ 편의점 위치를 확인하세요.</p>
              <hr class="c-hr c-hr--title">
            <ul class="market-list">
              <c:forEach var="midList" items="${midBannerList}">
                    <li class="market-list__item" onclick="insertBannAccess('${midList.bannSeq}', '${midList.bannCtg}')">
                    <c:if test="${empty midList.linkUrlAdr }">
                        <span class="market-list__anchor">
                      <img src="${midList.bannImg}" alt="${midList.imgDesc}">
                    </span>
                    </c:if>
                    <c:if test="${not empty midList.linkUrlAdr }">
                        <a class="market-list__anchor" href="${empty midList.linkUrlAdr ? 'javascript:void(0)' : midList.linkUrlAdr}" target="${midList.linkTarget eq 'Y' ? '_blank' : '_self'}" title="${midList.linkTarget eq 'Y' ? '새창열림' : ''}" style="${empty midList.mobileLinkUrl ? 'cursor:auto;' : ''}">
                      <img src="${midList.bannImg}" alt="${midList.imgDesc}">
                    </a>
                    </c:if>

                  </li>
              </c:forEach>
            </ul>
            <div class="c-button-wrap u-mt--40" style="display: none">
              <button class="c-button c-button-round--md c-button--mint c-button--w460" onclick="javascript:searchStorePop();" >판매처 찾기</button>
            </div>
          </div>
          <div class="ly-content--wrap u-mt--64 u-bk--light-mint">
            <div class="c-row c-row--lg">
              <div class="market-box">
                <h4 class="c-heading c-heading--fs20"> 전국 제휴 편의점에서
                  <br>kt M모바일 유심 구매 가능!
                </h4>
                <p class="c-text c-text--fs17 u-co-gray u-mt--16">편의점 유심 구매했다면? 바로 개통해보세요.</p>
                <ul class="c-step-list">
                  <li class="c-step-list__item">
                    <span class="c-step-list__label">STEP 1</span>
                    <i class="c-step-list__image" aria-hidden="true">
                      <img src="../../resources/images/portal/common/ico_market.svg" alt="">
                    </i>
                    <strong class="c-step-list__title">편의점 찾기</strong>
                    <p class="c-step-list__text">가까운 편의점을 찾아주세요.</p>
                  </li>
                  <li class="c-step-list__item">
                    <span class="c-step-list__label">STEP 2</span>
                    <i class="c-step-list__image" aria-hidden="true">
                      <img src="../../resources/images/portal/common/ico_usim_buy.svg" alt="">
                    </i>
                    <strong class="c-step-list__title">유심구매</strong>
                    <p class="c-step-list__text">원하는 유심을 구매하세요</p>
                  </li>
                  <li class="c-step-list__item">
                    <span class="c-step-list__label">STEP 3</span>
                    <i class="c-step-list__image" aria-hidden="true">
                      <img src="../../resources/images/portal/common/ico_phone_usim.svg" alt="">
                    </i>
                    <strong class="c-step-list__title">셀프개통</strong>
                    <p class="c-step-list__text">kt M모바일 홈페이지에서 셀프개통을
                      <br>진행하시면 특별한 혜택을 드립니다.
                    </p>
                  </li>
                </ul>
                <div class="c-button-wrap u-mt--40">
                  <button class="c-button c-button-round--md c-button--mint c-button--w460" onclick="selfTimeChk('${orgnId}')">셀프개통 하기</button>
                  <a class="c-button c-button-round--md c-button--mint c-button--w460" href="/cs/faqList.do" title="자주 묻는 질문 바로가기">자주 묻는 질문 바로가기</a>
                </div>
              </div>
            </div>
          </div>
          <div class="c-row c-row--lg">
            <!-- <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">편의점 유심구매 혜택</h3>
            <hr class="c-hr c-hr--title">
            <ul class="c-benefit-list">
              <li class="c-benefit-list__item">
                <strong class="c-benefit-list__title">KT WI-FI 무료이용</strong>
                <p class="c-benefit-list__text">KT Wi-Fi Zone 어디서든
                  <br>인터넷을 자유롭게
                </p>
                <p class="c-bullet c-bullet--fyr u-mt--8 u-co-sub-4 fs-13">일부 요금제에 한함</p>
                <i class="c-icon c-icon--benefit-wifi" aria-hidden="true"></i>
              </li>
              <li class="c-benefit-list__item">
                <strong class="c-benefit-list__title">휴대폰 안심보험 가입 가능</strong>
                <p class="c-benefit-list__text"> 월 3,600원으로
                  <br>내 휴대폰을 안전하게!
                </p>
                <p class="c-bullet c-bullet--fyr u-mt--8 u-co-sub-4 fs-13"> 위 금액은 USIM형 기준
                  <br>(USIM 고가형은 월 5,500원)
                </p>
                <i class="c-icon c-icon--benefit-secure" aria-hidden="true"></i>
              </li>
              <li class="c-benefit-list__item">
                <strong class="c-benefit-list__title">제휴카드 가입 시 추가 할인</strong>
                <p class="c-benefit-list__text">KB국민/하나/롯데/현대/IBK/우리</p>
                <i class="c-icon c-icon--benefit-card" aria-hidden="true"></i>
              </li>
            </ul> -->

            <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">유의사항</h3>
            <hr class="c-hr c-hr--title u-mb--0">
            <div class="c-accordion c-accordion--type3" data-ui-accordion>
              <ul class="c-accordion__box">
                <li class="c-accordion__item">
                  <div class="c-accordion__head">
                    <strong class="c-accordion__title">셀프개통</strong>
                    <button class="runtime-data-insert c-accordion__button" id="acc_header_c1" type="button" aria-expanded="false" aria-controls="acc_content_c1">

                    </button>
                  </div>
                  <div class="c-accordion__panel expand" id="acc_content_c1" aria-labelledby="acc_header_c1">
                    <div class="c-accordion__inside">
                      ${storeInfoFormDt.docContent}
                    </div>
                  </div>
                </li>
                <li class="c-accordion__item">
                  <div class="c-accordion__head">
                    <strong class="c-accordion__title">공통</strong>
                    <button class="runtime-data-insert c-accordion__button" id="acc_header_c2" type="button" aria-expanded="false" aria-controls="acc_content_c2">

                    </button>
                  </div>
                  <div class="c-accordion__panel expand" id="acc_content_c2" aria-labelledby="acc_header_c2">
                    <div class="c-accordion__inside">
                      ${storeEtcFormDt.docContent}
                    </div>
                  </div>
                </li>
              </ul>
            </div>

            <%-- <div class="c-heading-wrap u-mt--64">
            <input type="hidden" name="noticePageNo" id="noticePageNo"
            value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}" />
            <input type="hidden" id="sbstCtg" value="" />
              <h3 class="c-heading c-heading--fs20 c-heading--regular u-as-flex-end">자주묻는 질문</h3>
              <div class="c-form c-form--search">
                <div class="c-form__input">
                  <input class="c-input" id="searchNm" type="text" placeholder="검색어를 입력해주세요" value="${boardDto.searchNm}"
                  onkeypress="if(event.keyCode==13) {faqSearch(); return false;}" maxlength="20" style="ime-mode:active;">
                  <label class="c-form__label c-hidden" for="searchNm">검색어 입력</label>
                  <button class="c-button c-button--reset">
                    <span class="c-hidden">초기화</span>
                    <i class="c-icon c-icon--reset" aria-hidden="true"></i>
                  </button>
                  <button class="c-button c-button-round--sm c-button--primary" onclick="faqSearch()">검색</button>
                </div>
              </div>
            </div>
            <hr class="c-hr c-hr--title">
            <div class="c-filter">
              <div class="c-filter__inner" id="faqCate">
                <c:set var="sbstCtgList" value="${nmcp:getCodeList(directUsimFaqCtg)}" />
                <fmt:parseNumber var="widthSize" value="${100/(fn:length(sbstCtgList)+1)}" integerOnly="true" />
                <button class="c-button c-button-round--sm c-button--white is-active"
                    sbstCtg="" onclick="selectNoticeCtg('',this)">전체<span class="c-hidden">선택됨</span></button>
                <c:forEach items="${sbstCtgList}" var="sbstCtg" varStatus="stt">
                            <button class="c-button c-button-round--sm c-button--white"
                            type="button" onclick="selectNoticeCtg('${sbstCtg.dtlCd}',this);"
                            sbstCtg="${sbstCtg.dtlCd}">${sbstCtg.dtlCdNm}</button>
                </c:forEach>
             </div>
            </div>
            <div class="c-nodata" style="display: none;">
              <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
              <p class="c-nodata__text">검색 결과가 없습니다.</p>
            </div>
            <div class="c-accordion c-accordion--type2 u-mt--40" id="accordion_runtime_update" data-ui-accordion>
              <ul class="c-accordion__box" id="noticeListTBody">

              </ul>
            </div>
            <hr class="c-hr c-hr--basic">
            <div id="faq-paging">
            </div>--%>



          </div>
        </div>

        <div class="c-modal c-modal--sm" id="join-info-modal" role="dialog" tabindex="-1" aria-labelledby="#join-info-modal__title">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__body">
                        <div class="self-opening">
                            <strong class="self-opening__title">신규 가입만 가능한 시간입니다.</strong>
                            <p class="self-opening__text">
                                번호 이동 셀프 개통은 <br> <a class="u-td-underline u-fw--medium" href="/appForm/appCounselorInfo.do">온라인 가입신청</a>을 이용해 주세요.
                            </p>
                            <span class="c-text-label c-text-label--type2 c-text-label--red rectangle">셀프개통 가능시간</span>
                            <div class="c-table">
                                <table>
                                    <caption>번호이동, 신규가입을 포함한 셀프개통 가능 시간 정보</caption>
                                    <colgroup>
                                        <col style="width: 50%">
                                        <col style="width: 50%">
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th scope="col">번호이동</th>
                                            <th scope="col">신규가입</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <strong class="self-opening__highlight">10:00 ~ 19:00</strong>
                                                <p class="c-bullet c-bullet--fyr u-co-gray">일요일, 신정/설/추석 당일 제외</p></td>
                                            <td>
                                                <strong class="self-opening__highlight">08:00 ~ 19:00</strong>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="c-button-wrap u-mt--48">
                            <a class="c-button c-button--full c-button--primary" href="/appForm/appFormDesignView.do?orgnId=${orgnId}" id="movBtn" style="display:none;">확인</a>
                            <a class="c-button c-button--full c-button--primary" data-dialog-close id="closeBtn">확인</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>

</t:insertDefinition>