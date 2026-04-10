<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">정책 및 약관</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
       <script type="text/javascript" src="/resources/js/portal/policy/ktPolicyView.js"></script>
    <script>
    $(document).ready(function(){
        setTimeout(function(){
            $('#subbody_loading').hide();
        }, 200);
    });
    </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="pageNo" name="pageNo" value="${pageInfoBean.pageNo}">
        <input type="hidden" id="stpltSeq" name="stpltSeq" value="${viewDto.stpltSeq}">
        <input type="hidden" id="paramStpltCtgCd" name="paramStpltCtgCd" value="${paramStpltCtgCd}">

        <div id="subbody_loading" style="display:flex; width: 100%; height: calc(100vh - 130px); box-sizing: border-box; background: rgb(255, 255, 255); text-align: center; position: relative; align-items: center; justify-content: center; z-index: 10;">
            <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
        </div>

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">정책 및 약관</h2>
            </div>
            <div class="c-row c-row--xlg">
                <div class="c-tabs c-tabs--type2 col-4 c-expand u-mt--m56" data-ui-tab data-tab-active="1">
                    <div class="c-tabs__list" role="tablist">
                        <button class="c-tabs__button" id="tab1" role="tab" aria-controls="tabD1panel" aria-selected="false" tabindex="-1" onclick="location.href = '/policy/privacyList.do'">개인정보 처리방침</button>
                        <button class="c-tabs__button" id="tab2" role="tab" aria-controls="tabD2panel" aria-selected="false" tabindex="-1" onclick="location.href = '/policy/ktPolicy.do'">이용약관</button>
                        <button class="c-tabs__button" id="tab3" role="tab" aria-controls="tabD3panel" aria-selected="false" tabindex="-1" onclick="location.href = '/policy/policyGuideView.do'" >이용약관 주요설명</button>
                        <button class="c-tabs__button" id="tab4" role="tab" aria-controls="tabD4panel" aria-selected="false" tabindex="-1" onclick="location.href = '/policy/youngList.do'" >청소년 보호 정책</button>
                    </div>
                </div>
                <div class="c-tabs__panel" id="tabD1panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1"></div>
                <div class="c-tabs__panel u-block" id="tabD2panel" role="tabpanel" aria-labelledby="tab2" tabindex="-1">
                    <div class="c-row c-row--lg">
                        <div class="c-board">
                            <div class="c-board__head">
                                <strong class="c-board__title policy">${viewDto.stpltTitle}</strong>
                                <div class="c-board__sub">
                                    <span class="c-board__sub__item">
                                        <span class="c-hidden">등록일</span>${viewDto.cretDt}
                                    </span>
                                </div>
                                <a class="c-button" href="javascript:fileDownLoad('${viewDto.attSeq}','2',fileDownCallBack,'${viewDto.stpltSeq}');">
                                    <span class="c-hidden">다운로드</span>
                                    <i class="c-icon c-icon--download" aria-hidden="true"></i>
                                </a>
                            </div>
                            <div class="c-board__content">
                                <article class="c-editor">
                                    ${viewDto.stpltSbst}
                                </article>
                            </div>

                            <div class="c-board__nav">
                                <c:choose>
                                    <c:when test="${viewDto.preSeq ne 0 && viewDto.preSeq ne null}">
                                        <a class="c-button" href="javascript:goNextDetail('${viewDto.preSeq}')">
                                            <i class="c-icon c-icon--triangle-left" aria-hidden="true"></i>
                                            <span>이전글</span>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="c-button is-disabled" href="javascript:;">
                                            <i class="c-icon c-icon--triangle-left" aria-hidden="true"></i>
                                            <span>이전글</span>
                                        </a>
                                    </c:otherwise>
                                </c:choose>

                                <c:choose>
                                    <c:when test="${viewDto.nextSeq ne 0 && viewDto.nextSeq ne null}">
                                        <a class="c-button" href="javascript:goNextDetail('${viewDto.nextSeq}')">
                                            <span>다음글</span>
                                            <i class="c-icon c-icon--triangle-right" aria-hidden="true"></i>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="c-button is-disabled" href="javascript:;">
                                            <span>다음글</span>
                                            <i class="c-icon c-icon--triangle-right" aria-hidden="true"></i>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="c-button-wrap u-mt--56">
                            <a class="c-button c-button--lg c-button--white c-button--w460" href="javascript:;" id="viewList">목록보기</a>
                        </div>
                    </div>
                </div>
                <div class="c-tabs__panel" id="tabD3panel" role="tabpanel" aria-labelledby="tab3" tabindex="-1"></div>
                <div class="c-tabs__panel" id="tabD4panel" role="tabpanel" aria-labelledby="tab4" tabindex="-1"></div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
