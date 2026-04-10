<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<t:insertDefinition name="layoutGnbDefault">
  <t:putAttribute name="titleAttr">자녀요금조회</t:putAttribute>
  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/portal/mypage/childinfo/childInfoView.js"></script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">자녀요금조회</h2>
      </div>
      <div class="myinfo-wrap">
        <h3 class="user-info__tit">
          <b>${childInfo.name}</b> 님 회선정보
        </h3>
        <%@ include file="/WEB-INF/views/portal/mypage/childinfo/childCommCtn.jsp" %>
        <div class="join-info">
          <ul class="join-info__list">
            <li class="join-info__item">
              <i class="c-icon c-icon--calendar-black"></i>
              <span class="join-info__tit">개통일자</span>
              <span class="join-info__text" id="initActivationDate"></span>
            </li>
            <li class="join-info__item">
              <i class="c-icon c-icon--my_phone"></i>
              <span class="join-info__tit">모델명</span>
              <span class="join-info__text">${modelName}</span>
            </li>
          </ul>
        </div>
        <ul class="myinfo-list">
          <li class="myinfo__item" id="billItem">
            <div class="myinfo__tit" id="billTitle">
              <h3>당월 청구요금</h3>
            </div>
            <div class="myinfo__info">
              <div class="month_bill" id="billInfo">
                <div class="month_bill__nodata" style="display: none;">
                  <p>조회결과가 없습니다.</p>
                </div>
                <div class="month_bill__item" id="thisMonthInfo">
                </div>
                <div class="month_bill__item" id="realTimeInfo">
                </div>
              </div>
            </div>
          </li>

          <li class="myinfo__item">
            <div class="myinfo__tit">
              <h3>사용중 요금제</h3>
              <div class="myinfo__button-wrap">
                <a href="javascript:;" url="/mypage/childCallView.do" title="이용량 조회 이동하기" onclick="openNewDetailPopup(this);"> 이용량 조회 <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i></a>
              </div>
            </div>
            <div class="myinfo__info">
              <div class="myrate">
                <div class="myrate__title">${prvRateGrpNm}</div>
                <ul class="myrate__list">
                  <li class="myrate__item">
                    <i class="c-icon c-icon--payment-data" aria-hidden="true"></i>
                    <span class="c-hidden">데이터</span>
                    <span>${rateAdsvcLteDesc}</span>
                  </li>
                  <li class="myrate__item">
                    <i class="c-icon c-icon--my-call" aria-hidden="true"></i>
                    <span class="c-hidden">음성</span>
                    <span>${rateAdsvcCallDesc}</span>
                  </li>
                  <li class="myrate__item">
                    <i class="c-icon c-icon--my-sms" aria-hidden="true"></i>
                    <span class="c-hidden">문자</span>
                    <span>${rateAdsvcSmsDesc}</span>
                  </li>
                </ul>
              </div>
            </div>
          </li>

          <li class="myinfo__item">
            <div class="myinfo__tit">
              <h3>부가서비스</h3>
            </div>
            <div class="c-accordion" data-ui-accordion>
              <ul class="c-accordion__box">
                <li class="c-accordion__item">
                  <div class="c-accordion__head u-pa--16">
                    <div class="my-addservice__item">
                      <p>유료 부가서비스</p>
                      <p class="u-mr--24">
                        <b id="payCtn">0</b>건
                      </p>
                    </div>
                    <button class="c-accordion__button" id="payAddSvcHeader" type="button" aria-expanded="false" aria-controls="payAddSvcContent"></button>
                  </div>
                  <div class="c-accordion__panel expand" id="payAddSvcContent" aria-labelledby="payAddSvcHeader">
                    <div class="c-accordion__inside c-table">
                      <table>
                        <caption>부가서비스명, 이용요금 (VAT포함)으로 구성된 현재 사용중인 부가서비스 정보</caption>
                        <colgroup>
                          <col style="width: 60%">
                          <col style="width: 40%">
                        </colgroup>
                        <thead>
                          <tr>
                            <th scope="col">부가서비스명</th>
                            <th scope="col">이용요금 (VAT포함)</th>
                          </tr>
                        </thead>
                        <tbody id="payAddSvcList">
                        </tbody>
                      </table>
                    </div>
                  </div>
                </li>

                <li class="c-accordion__item">
                  <div class="c-accordion__head u-pa--16">
                    <div class="my-addservice__item">
                      <p>무료 부가서비스</p>
                      <p class="u-mr--24">
                        <b id="freeCtn">0</b>건
                      </p>
                    </div>
                    <button class="c-accordion__button" id="freeAddSvcHeader" type="button" aria-expanded="false" aria-controls="freeAddSvcContent"></button>
                  </div>
                  <div class="c-accordion__panel expand" id="freeAddSvcContent" aria-labelledby="freeAddSvcHeader">
                    <div class="c-accordion__inside c-table">
                      <table>
                        <caption>부가서비스명, 이용요금 (VAT포함)으로 구성된 현재 사용중인 부가서비스 정보</caption>
                        <colgroup>
                          <col style="width: 60%">
                          <col style="width: 40%">
                        </colgroup>
                        <thead>
                          <tr>
                            <th scope="col">부가서비스명</th>
                            <th scope="col">이용요금 (VAT포함)</th>
                          </tr>
                        </thead>
                        <tbody id="freeAddSvcList">
                        </tbody>
                      </table>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </li>

          <li class="myinfo__item">
            <div class="myinfo__tit">
              <h3>납부정보</h3>
            </div>
            <div class="myinfo__info">
              <div class="charge_info">
                <table>
                  <caption>납부방법, 납부계정정보, 명세서유형, 납부기준일로 구성된 납부방법 정보</caption>
                  <colgroup>
                    <col style="width: 10rem;">
                    <col>
                  </colgroup>
                  <tbody id="payArea">
                  <tr>
                    <td colspan="2" class="u-ta-center u-height--80">조회 중 입니다.</td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>

  </t:putAttribute>
</t:insertDefinition>