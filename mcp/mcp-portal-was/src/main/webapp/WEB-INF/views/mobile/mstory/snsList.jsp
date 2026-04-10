<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<%@ taglib prefix="un"
    uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<un:useConstants var="Constants"
    className="com.ktmmobile.mcp.common.constants.Constants" />
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="metaTagAttr">
        <meta property="og:image" content="https://${header['host']}/ktMmobile_og.png" />
        <meta property="og:title" content="M Story I kt M모바일 공식 다이렉트몰"/>
    </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/mstory/snsList.js"></script>
        <script type="text/javascript" src="/resources/js/jquery.placeholder.js"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <input type="hidden" name="yy" value="" />
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                 <!--[2022-02-04] 네이밍 변경 M storage -> M Story-->
              <h2 class="ly-page__head c-flex">M Story<a class="header-share-button" href="#n">

                  <i class="c-icon c-icon--share" aria-hidden="true" id="snsShareBut"></i>
                </a>
                <button class="header-clone__gnb"></button>
              </h2>
            </div>
            <div class="c-tabs c-tabs--type2">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="" style="display:none;">
                    <button class="c-tabs__button" id="monthlyM">월간M</button>
                    <button class="c-tabs__button is-active" id="cust">고객소통</button>
                </div>
                <img src="/resources/images/portal/common/share_logo.png" alt="shareImg" hidden/>
                <input type="hidden" value="${status}" id="status"/>
                <div class="c-tabs__panel border-none" id="tab2_panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
                    <div class="m-storage-customer">
                        <string class="fs-22 u-fw--bold u-block">kt M모바일과 함께하는
                          <br>스마트한 통신생활!
                        </string>
                        <span class="u-mt--12 u-co-gray-7 u-block">공식 SNS 채널을 통해 kt M모바일만의
                          <br>새로운 소식을 지금 바로 만나보세요!
                        </span>
                      </div>
                      <c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
                        <c:forEach var="list" items="${snsList}">
                             <c:choose>
                                 <c:when test="${appFormYn eq 'Y'}">
                                    <div class="m-storage-customer" onclick="javascript:appOutSideOpen('${list.mobileLinkUrl}');" ${list.mobileLinkUrl != '' && list.mobileLinkUrl!= null ? '' : 'hidden' } target="_blank">
                                      <div class="m-storage-customer__link c-flex">
                                  </c:when>
                                  <c:otherwise>
                                    <c:if test = "${platFormCd eq 'A'}">
                                        <div class="m-storage-customer" onclick="javascript:appOutSideOpen('${list.mobileLinkUrl}');" ${list.mobileLinkUrl != '' && list.mobileLinkUrl!= null ? '' : 'hidden' } target="_blank">
                                        <div class="m-storage-customer__link c-flex">
                                    </c:if>
                                    <c:if test = "${platFormCd ne 'A'}">
                                        <div class="m-storage-customer" onclick="window.open('about:blank').location.href='${list.mobileLinkUrl}'" ${list.mobileLinkUrl != '' && list.mobileLinkUrl!= null ? '' : 'hidden' } target="_blank">
                                        <div class="m-storage-customer__link c-flex">
                                    </c:if>
                                   </c:otherwise>
                             </c:choose>


                                    <c:choose>
                                        <c:when test="${list.snsCntpntCd == 'NAVERBLOG'}">
                                            <i class="c-icon c-icon--naver-blog" aria-hidden="true"></i>
                                        </c:when>
                                        <c:when test="${list.snsCntpntCd == 'YOUTUBE'}">
                                            <i class="c-icon c-icon--youtube" aria-hidden="true"></i>
                                        </c:when>
                                        <c:when test="${list.snsCntpntCd == 'INSTAGRAM'}">
                                            <i class="c-icon c-icon--insta" aria-hidden="true"></i>
                                        </c:when>
                                        <c:when test="${list.snsCntpntCd == 'FACEBOOK'}">
                                            <i class="c-icon c-icon--facebook-3" aria-hidden="true"></i>
                                        </c:when>
                                  </c:choose>
                                  <i class="c-icon c-icon--anchor" aria-hidden="true"></i>
                                </div>
                                <hr class="c-hr c-hr--type2">
                                <div class="m-storage-customer__img">
                                  <img src="${list.mobileListImgNm}" alt="${list.mobileListImgDesc }" onerror="this.src='/resources/images/portal/content/img_review_noimage.png'">
                                </div>
                                <string class="m-storage-customer__title">${list.ntcartTitle}</string>
                                <span class="m-storage-customer__date">
                                    <fmt:parseDate value="${list.ntcartDate}" var="regDt" pattern="yyyyMMdd"/>
                                    <fmt:formatDate value="${regDt}" pattern="yyyy.MM.dd"/>

                                </span>
                              </div>
                        </c:forEach>

                </div>
            </div>
        </div>
        <script src="../../resources/js/mobile/vendor/swiper.min.js"></script>


    </t:putAttribute>
</t:insertDefinition>