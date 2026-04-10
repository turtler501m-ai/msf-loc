<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">통신이용자정보 제공내역 조회요청</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
        <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
        <script type="text/javascript" src="/resources/js/portal/mypage/prvCommData.js"></script>
        <script type="text/javascript">
        history.pushState(null, null,"/mypage/prvCommData.do");
        window.onpopstate = function (event){
            history.go("/mypage/prvCommData.do");
        }
        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="isAuth" name="isAuth">  <!-- 본인인증 -->
        <input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">

        <div class="ly-content" id="main-content">
              <div class="ly-page--title">
                  <h2 class="c-page--title">통신이용자정보 제공내역 조회요청</h2>
              </div>
              <div class="callhistory-info">
                  <div class="callhistory-info__list">
                    <h3>통신이용자정보 제공내역 조회요청이란?</h3>
                    <ul>
                      <li>최근 1년이내 수사기관에서 고객님의 정보(성함+주소+핸드폰번호 등)을 제공했는지 여부를 확인할 수 있는 서비스입니다.</li>
                      <li>정보통신망 이용촉진 및 정보보호 관한법률에 의거 통신이용자정보 제공사실은 본인인증 후 열람신청 가능합니다.</li>
                    </ul>
                  </div>
                  <div class="callhistory-guide">
                    <ul class="callhistory-guide__list">
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">1</span>고객님</strong>
                        <p>통신이용자정보 제공내역<br/>조회요청</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">2</span>KT엠모바일</strong>
                        <p>수사기관 제공내역 확인</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">3</span>KT엠모바일→고객님</strong>
                        <p>홈페이지 내 결과 등록<br/> 및 SMS를 통해 안내</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">4</span>고객님</strong>
                        <p>결과확인 및 출력</p>
                      </li>
                    </ul>
                  </div>
              </div>

              <div class="c-row c-row--lg u-mt--60">
                <h3 class="c-heading c-heading--fs20 c-heading--regular">조회할 회선을 선택해 주세요.</h3>
                <%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>

                <hr class="c-hr c-hr--title">

                <h4 class="c-heading c-heading--fs16 u-mt--48">주의사항</h4>
                <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                    <li class="c-text-list__item u-co-gray">WEB/APP을 통한 통신이용자정보 제공사실 열람신청은 사용중인 고객만 제공되며 본인회선만 신청 가능합니다.</li>
                    <li class="c-text-list__item u-co-gray">통신이용자정보 제공사실 열람은 수사기관에 가입자 정보를 제공했는지 여부를 확인하기 위한 서비스입니다.</li>
                    <li class="c-text-list__item u-co-gray">통신이용자정보 제공사실 열람은 정회원 고객님이 본인인증 후 이용가능합니다.</li>
                </ul>
                <div class="c-form-wrap u-mt--32">
                    <div class="c-form-group" role="group" aira-labelledby="inpNameCertiTitle">
                        <div class="c-form-wrap u-mt--48 _self">
                            <h5 class="c-form__title">통신이용자정보 제공내용 조회요청 본인인증 동의</h5>
                            <div class="c-agree u-mt--16">
                                <div class="c-agree__item">
                                    <div class="c-agree__inner">
                                        <input class="c-checkbox" id="clauseSimpleOpen" type="checkbox" validityyn="Y">
                                        <label class="c-label" for="clauseSimpleOpen">본인인증 절차 동의</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
<!-- 					<div class="c-box c-box--type1 u-mt--20"> -->
<!-- 						<ul class="c-text-list c-bullet c-bullet--dot"> -->
<!-- 						<li class="c-text-list__item u-co-gray">명의자 본인 확인을 위해서 본인인증이 필요합니다. 안전한 서비스 이용 및 고객님의 개인정보 보호를 위해 -->
<!-- 							본인인증을 받으신 고객분만 이용이 가능합니다. 유심변경 이용을 위해서 본인확인 절차를 진행해주세요.</li> -->
<!-- 						</ul> -->
<!-- 					</div> -->
                </div>

                <!-- 본인인증 방법 선택 -->
                <jsp:include page="/WEB-INF/views/portal/popup/niceAuthForm.jsp">
                    <jsp:param name="controlYn" value="N"/>
                    <jsp:param name="reqAuth" value="CNATX"/>
                </jsp:include>

                <div id="divDataList" style="display: none;">
                    <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--70">통신이용자정보 제공내역 조회결과</h3>
                    <hr class="c-hr c-hr--title">
                    <h4 class="c-heading c-heading--fs16 u-mt--48">주의사항</h4>
                    <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                        <li class="c-text-list__item u-co-gray">최근 30일이내 조회 및 제공된 내역만 확인 가능합니다.</li>
                        <li class="c-text-list__item u-co-gray">고객님의 소중한 정보가 수사기관에 제공 시, 제공여부만 확인가능하며 수사기관에서 요청한 사유는 해당 기관으로 문의 부탁드립니다.</li>
                    </ul>
                    <div class="c-table u-mt--48">
                      <table class="micropay">
                        <caption>구분, 신청일자, 처리결과, 제공여부, 출력</caption>
                        <colgroup>
                          <col style="width: 10%">
                          <col style="width: 20%">
                          <col style="width: 20%">
                          <col style="width: 20%">
                          <col style="width: 15%">
                        </colgroup>
                        <thead>
                          <tr>
                            <th>구분</th>
                            <th>신청일자</th>
                            <th>처리결과</th>
                            <th>제공여부</th>
                            <th>출력</th>
                          </tr>
                        </thead>
                        <tbody id="dataList">
                            <tr>
                                <td colspan="5">내역이 존재하지 않습니다.</td>
                            </tr>
                        </tbody>
                      </table>
                    </div>
                </div>

                <div class="c-button-wrap u-mt--56" style="display: none;" id="divBtnReq">
                    <button class="c-button c-button--lg c-button--primary c-button--w460" id="btnRequest">조회요청</button>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
