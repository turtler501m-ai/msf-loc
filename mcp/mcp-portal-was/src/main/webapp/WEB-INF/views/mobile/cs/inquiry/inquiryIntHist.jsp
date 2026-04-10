<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/cs/inquiry/inquiryIntHist.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

<input type="hidden" name="pageNo" id="pageNo" value="${inquiryBoardDto.pageNo}" />
<input type="hidden" name="isHist" id="isHist" value="${isHist}" />
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    1:1 상담문의
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>

            <div class="c-tabs c-tabs--type2" data-ignore="true">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button" id="prvTab">1:1 문의작성</button>
                    <button class="c-tabs__button is-active" >내 문의 확인하기</button>
                </div>

                <div class="c-tabs__panel">
                	<c:if test="${empty maskingSession}">
                      <div class="masking-badge-wrap">
                          <div class="masking-badge" style="top: -2rem;">
                                 <a class="masking-badge__button" href="/m/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
                                <i class="masking-badge__icon" aria-hidden="true"></i>
                                   <p>가려진(*) 정보보기</p>
                              </a>
                          </div>
                      </div>
                    </c:if>
                    <c:choose>
                        <c:when test="${userSession eq null and authSmsDto eq null}">
                            <p class="c-bullet c-bullet--dot u-mt--40">kt M모바일 고객센터에서는 별도의 회원가입 없이 휴대폰 본인인증 후 1:1 상담 문의 및 문의 확인이 가능합니다.</p>
                            <p class="c-bullet c-bullet--fyr u-co-gray">회원가입 후 kt M모바일의 다양한 맞춤 서비스를 경험해보세요.</p>
                            <%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
                            <input type="hidden" name="certifyYn" id="certifyYn" value="">
                            <div class="c-agree">
                                <div class="c-agree__item">
                                    <input class="c-checkbox" id="chkA1" type="checkbox" name="chkA1">
                                    <label class="c-label" for="chkA1">개인정보 수집이용 동의</label>
                                    <button class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=TERMSINF&cdGroupId2=TERMSINF03');">
                                        <span class="c-hidden">상세보기</span>
                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="c-button-wrap">
                                <a class="c-button c-button--xsm" href="/m/loginForm.do">kt M모바일계정 로그인</a> <a
                                    class="c-button c-button--xsm" href="/m/join/fstPage.do"> <b>회원가입</b>
                                </a>
                            </div>
                            <div class="c-button-wrap">
                                <button class="c-button c-button--full c-button--primary" id="applyBtn" disabled="disabled">확인</button>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p class="c-text c-text--type2 u-mt--40">답변상태가 처리중인 문의사항은 상담원이 문의를 처리중입니다.</p>
                            <p class="c-bullet c-bullet--dot u-co-gray">주말 및 휴일에는 답변 처리가 다소 지연될 수 있습니다.</p>
                            <div class="c-accordion c-accordion--type1 faq-accordion" id="dataArea">
                                <ol class="c-accordion__box" id="liDataArea">

                                </ol>
                            </div>
                            <div class="c-nodata" style="display:none;">
                                <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                <p class="c-nodata__text">조회 결과가 없습니다.</p>
                            </div>

                            <div class="c-button-wrap" display:none; id="addBtnArea">
                                <button class="c-button c-button--full" id="moreBtn">더보기
                                    <span class="c-button__sub">
                                        <span id="listCount"></span>/<span id="totalCount"></span>
                                        <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
                                    </span>
                                </button>
                            </div>

                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>