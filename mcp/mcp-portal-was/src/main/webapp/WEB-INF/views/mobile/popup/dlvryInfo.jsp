<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <script type="text/javascript" charset="UTF-8" src="/resources/js/mobile/popup/dlvryInfo.js"></script>
    <script type="text/javascript" src="/resources/js/proj4.js" ></script>
    <form name="form" id="form" method="post">
        <input type="hidden" name="currentPage" id="currentPage" value="1" />
        <input type="hidden" name="countPerPage" id="countPerPage" value="10" />
        <input type="hidden" name="keyword" id="keyword" value="" />
    </form>
    <div class="c-modal__content">
        <div class="c-modal__header">
            <h1 class="c-modal__title" id="search-info-title">바로배송(당일 퀵) 가능지역</h1>
            <button class="c-button" data-dialog-close>
                <i class="c-icon c-icon--close" aria-hidden="true"></i> <span
                    class="c-hidden">팝업닫기</span>
            </button>
        </div>
        <div class="c-modal__body">
            <div class="c-form c-form--search">
                <div class="c-form__input">
                    <input class="c-input" type="text" id="tmpKey" placeholder="도로명주소, 건물명 또는 지번입력" title="도로명주소, 건물명 또는 지번입력" maxlength="20" autocomplete="off">
                    <!--[2022-03-07] 검색버튼 디자인 변경-->
                    <button class="c-button c-button--search-bk" onclick="goSubmit();">
                        <span class="c-hidden">검색어</span> <i
                            class="c-icon c-icon--search-bk" aria-hidden="true"></i>
                    </button>
                    <button class="c-button c-button--reset">
                        <span class="c-hidden">초기화</span> <i class="c-icon c-icon--reset" aria-hidden="true"></i>
                    </button>
                </div>
            </div>
            <!-- case1 - 주소 검색 초기-->
            <h3 class="c-heading c-heading--type4 step1">검색어 예시</h3>
            <ul class="search-ex step1">
                <li class="search-ex__item">
                    <strong class="search-ex__title">도로명 + 건물번호 입력</strong>
                    <p class="search-ex__desc">예) 반포대로 58</p>
                </li>
                <li class="search-ex__item">
                    <strong class="search-ex__title">건물명 입력</strong>
                    <p class="search-ex__desc">예) 독립기념관</p>
                </li>
                <li class="search-ex__item">
                    <strong class="search-ex__title">지번</strong>
                    <p class="search-ex__desc">예) 삼성동 25</p>
                </li>
            </ul>
            <hr class="c-hr c-hr--type2 step1">
            <b class="c-text c-text--type3 step1">
                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
            </b>
            <ul class="c-text-list c-bullet c-bullet--dot step1">
                <li class="c-text-list__item u-co-gray">건물명은 각 자치단체에서 등록한 경우 조회가 가능합니다.</li>
                <li class="c-text-list__item u-co-gray">화면 상단의 상세검색을 이용하시면 보다 정확한 결과를 얻으실 수 있습니다.</li>
            </ul>
            <!--
            <div class="c-button-wrap">
                <a class="c-button c-button--full c-button--primary" href="javascript:void(0);" onclick="goSubmit();">검색</a>
            </div>
             -->
            <!-- //-->
            <!-- case2 - 주소 검색 후-->
            <h3 class="c-heading c-heading--type4 step2" style="display:none;">
                도로명주소 검색 결과<span class="u-co-mint" id="dlvryInfoCnt">&nbsp;</span>
            </h3>
            <ul class="addr-result step2" id="searchList" style="display:none;"></ul>
            <nav class="c-paging step2" aria-label="검색결과" style="display:none;"></nav>
            <!-- case3 - 상세 주소 입력-->
            <ul class="addr-result step3" style="display:none;">
                <li class="addr-result__item bd-none">
                    <a href="#">
                        <strong class="addr-result__number" id="zipText"></strong>
                        <p class="addr-result__text">
                            <span class="c-text-label c-text-label--mint">도로명</span>
                            <span id="addr1Text"></span>
                        </p>
                    </a>
                </li>
            </ul>
            <div class="c-form step3" style="display:none;">
                <div class="c-form__group" role="group" aria-labeledby="inpG">
                    <input class="c-input" type="text" id="addrDetail" placeholder="상세주소 (예 1202동 101호 / 종로빌딩 1층)" title="상세주소 (예 1202동 101호 / 종로빌딩 1층)" onkeyup="addrInChk();" maxlength="100" autocomplete="off">
                </div>
            </div>

            <p class="c-bullet c-bullet--dot u-co-gray">바로배송(당일 퀵) 특성 상 배송 신청 이후 배송지 변경은 불가합니다.</p>
            <!-- 배송가능 시-->
            <div class="c-text-box c-text-box--blue" id="directTxt1" style="display:none;">
                해당지역은<b>&nbsp;바로배송(당일 퀵)이&nbsp;</b>가능합니다. <br>배송 받으실 상세주소를 입력해주세요.
            </div>
            <!-- 배송불가 시-->
            <div class="c-text-box c-text-box--red" id="directTxt2" style="display:none;">
                해당지역은<b>&nbsp;바로배송(당일 퀵)이&nbsp;</b>불가합니다. <br>주소를 입력하실 경우<b>&nbsp;택배로 변경&nbsp;</b>되어 배송됩니다.
            </div>
            <!-- 버튼 활성화 시 .is-active 클래스 추가-->
            <div class="c-button-wrap c-button-wrap--column setAddr2" id="cButton" style="display:none;">
                <a class="c-button c-button--full c-button--lg c-button--mint is-disabled" href="javascript:void(0);" id="setAddr2">가능 여부 조회</a>
            </div>
            <div class="c-button-wrap c-button-wrap--column setAddr3" style="display:none;">
                <a class="c-button c-button--full c-button--lg c-button--mint is-disabled" href="javascript:void(0);" id="setAddr3">주소입력</a>
            </div>
            <!-- //-->
        </div>
    </div>
    <input type="hidden" name="psblYn" id="psblYn" value="" />
    <input type="hidden" name="bizOrgCd" id="bizOrgCd" value="" />
    <input type="hidden" name="acceptTime" id="acceptTime" value="" />
    <input type="hidden" name="confmKey" id="confmKey" value="<spring:eval expression="@propertiesForJsp['juso.coord.key']" />" />
    <input type="hidden" name="entY" id="entY" value="" />
    <input type="hidden" name="entX" id="entX" value="" />
    <input type="hidden" name="roadFullAddr" id="roadFullAddr" value="" />
    <input type="hidden" name="roadAddrPart1" id="roadAddrPart1" value="" />
    <input type="hidden" name="roadAddrPart2" id="roadAddrPart2" value="" />
    <input type="hidden" name="jibunAddr" id="jibunAddr" value="" />
    <input type="hidden" name="zipNo" id="zipNo" value="" />
    <input type="hidden" name="nfcYn" id="nfcYn" value="${nfcYn}"/>