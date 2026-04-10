<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="layoutMainDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/mypage/reCommendState.view.js?version=26-02-23"></script>
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>


    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <fmt:formatDate value="${nmcp:getDateToCurrent(0)}" pattern="yyyy.MM" var="nowDateMonth" />
        <c:set var="preDateMonth" value="${nmcp:addMonths(nowDateMonth ,-1,'yyyy.MM')}" />
        <c:set var="prePreDateMonth" value="${nmcp:addMonths(nowDateMonth ,-2,'yyyy.MM')}" />

        <input type="hidden" name="prePreDateMonth" id="prePreDateMonth" value="${prePreDateMonth}"/>
        <input type="hidden" name="preDateMonth" id="preDateMonth" value="${preDateMonth}"/>
        <input type="hidden" name="nowDateMonth" id="nowDateMonth" value="${nowDateMonth}"/>
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">친구초대</h2>
            </div>
            <div class="c-tabs c-tabs--type1" >
                <div class="c-tabs__list" >
                    <button class="c-tabs__button is-active" id="btnReCommendState">친구초대 현황</button>
                    <button class="c-tabs__button" id="btnReCommendMng" >추천 링크 관리</button>
                </div>

            </div>

            <div class="c-row c-row--lg u-mt--64">
                <h3 class="c-heading c-heading--fs20 c-heading--regular">조회할 회선을 선택해 주세요.</h3>
                <%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp"%>

                <hr class="c-hr c-hr--title">
            <h3 class="c-heading c-heading--fs16">친구 초대 현황</h3>
            <div class="c-table u-mt--16">
              <table>
                <caption>단말 가입한 친구, 유심 가입한 친구, 이번 달 가입한 친구 초대 현황</caption>
                <colgroup>
                  <col style="width: 33%">
                  <col style="width: 33%">
                  <col>
                </colgroup>
                <thead>
                  <tr>
                    <th scope="col">요금제 가입</th>
                    <th scope="col">KT인터넷 가입</th>
                    <th scope="col">이번달 가입</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td id="thisMonPhoneCnt">0명</td>
                    <td id="thisMonInterCnt">0명</td>
                    <td id="thisMonCnt">0명</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <h3 class="c-heading c-heading--fs16 u-mt--48">최근 3개월 간 친구 초대 현황</h3>
            <div class="c-table u-mt--16">
              <table>
                <caption>가입 월, 가입한 친구정보를 포함한 최근 3개월 간 친구 초대 현황</caption>
                <colgroup>
                    <col style="width: 25%">
                    <col style="width: 25%">
                    <col style="width: 25%">
                    <col>
                </colgroup>
                <thead>
                <tr>
                      <th scope="col">가입 월</th>
                      <th scope="col">가입한 친구</th>
                      <th scope="col">KT인터넷 가입</th>
                      <th scope="col">총 초대 고객 수</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td id="nowMon">0000.00</td>
                    <td id="nowMonPhoCnt">0명</td>
                    <td id="nowMonIntCnt">0명</td>
                    <td id="nowMonAllCnt">0명</td>
                </tr>
                <tr>
                    <td id="preMon">0000.00</td>
                    <td id="preMonPhoCnt">0명</td>
                    <td id="preMonIntCnt">0명</td>
                    <td id="preMonAllCnt">0명</td>
                </tr>
                <tr>
                    <td id="prePreMon">0000.00</td>
                    <td id="prePreMonPhoCnt">0명</td>
                    <td id="prePreMonIntCnt">0명</td>
                    <td id="prePreMonAllCnt">0명</td>
                </tr>
                </tbody>
              </table>
            </div>
            <h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
              <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
              <span class="u-ml--4">알려드립니다</span>
            </h3>
            <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
              <li class="c-text-list__item u-co-gray">친구 초대 이벤트는 kt M모바일 사용 고객이 가족 및 지인에게 소개하여, 초대 받으신 분(피추천인)이 다이렉트몰을 통해 개통을 완료 할 경우에 한해 참여 가능합니다.<span class="c-bullet c-bullet--fyr u-co-sub-4">피추천인이 기기변경 및 선불요금제, 데이터쉐어링 요금제 가입 일 경우 이벤트 대상에서 제외(이벤트 대상 및 혜택은 프로모션 시점에 따라 상이할 수 있으며 자세한 사항은 당시의 프로모션 내용을 기준합니다.)</span>
              </li>
              <li class="c-text-list__item u-co-gray">피추천인이 추천인 정보(친구 초대ID)를 온라인 가입 신청서에 기입하여 개통한 경우에만 혜택이 지급되며, 지급일 기준 피추천인 또는 추천인이 일시 정지, 해지, 명의변경 시, 혜택 제공이 제한될 수 있습니다.</li>
              <li class="c-text-list__item u-co-gray">혜택은 피추천인 개통월 기준 익월 말 혜택을 제공하며 비정상적이거나 부정한 방법으로 이벤트 참여 시 지급 대상에서 제외 될 수 있습니다.</li>
              <li class="c-text-list__item u-co-gray">추천인과 피추천인이 동일할 경우(명의+생년월일 기준) 혜택 지급 대상에서 제외됩니다.</li>
              <li class="c-text-list__item u-co-gray">프로모션 내용은 당사 사정에 따라 변경될 수 있으며, 프로모션 내용은 이벤트 페이지를 참고 부탁드립니다.</li>
            </ul>
          </div>
        </div>
        <!-- 레이어 팝업 -->
    </t:putAttribute>

</t:insertDefinition>