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
<!-- <script type="text/javascript" src="/resources/js/mobile/mypage/myCsInquirList.js"></script> -->
<script type="text/javascript" src="/resources/js/mobile/cs/inquiry/inquiryIntHist.js"></script>
</t:putAttribute>
    <t:putAttribute name="contentAttr">

<input type="hidden" name="pageNo" id="pageNo" value="${inquiryBoardDto.pageNo}" />
<input type="hidden" name="isHist" id="isHist" value="${isHist}" />
            <div class="ly-content" id="main-content">
                <div class="ly-page-sticky">
                    <h2 class="ly-page__head">
                        1:1 문의 내역
                        <button class="header-clone__gnb"></button>
                    </h2>
                </div>
                <p class="c-text c-text--type2 u-mt--40">답변상태가 처리중인 문의사항은 상담원이 문의를 처리중입니다.</p>
                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                    <li class="c-text-list__item u-co-gray">주말 및 휴일에는 답변 처리가 다소 지연될 수 있습니다.</li>
                </ul>
                <div class="c-button-wrap u-mt--32">
                    <a href="/m/cs/csInquiryInt.do" class="c-button c-button--full c-button--mint c-button--h48">1:1 문의하기</a>
                </div>
                <div class="c-accordion c-accordion--type1 faq-accordion faq-accordion--type2">
                    <div class="c-accordion c-accordion--type1 faq-accordion" id="dataArea">
                        <ol class="c-accordion__box" id="liDataArea">

                        </ol>
                    </div>
                    <div class="c-nodata" style="display:none;">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-noadat__text">조회 결과가 없습니다.</p>
                    </div>
                    <div class="c-button-wrap" display:none; id="addBtnArea">
                        <button class="c-button c-button--full" id="moreBtn">더보기
                            <span class="c-button__sub">
                                <span id="listCount"></span>/<span id="totalCount"></span>
                                <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
                            </span>
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    </t:putAttribute>
</t:insertDefinition>