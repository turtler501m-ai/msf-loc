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
<t:insertDefinition name="mlayoutDefault">
  <t:putAttribute name="titleAttr">자녀요금조회</t:putAttribute>
  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/mobile/mypage/childinfo/childInfoView.js"></script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">
          자녀요금조회
          <button class="header-clone__gnb"></button>
        </h2>
      </div>
      <div class="myinfo__tit u-mt--40">
        <h3 class="user-info__tit">
          <b>${childInfo.name}</b>님 회선정보
        </h3>
      </div>

      <%@ include file="/WEB-INF/views/mobile/mypage/childinfo/childCommCtn.jsp" %>

      <div class="join-info__list">
        <dl>
          <dt>개통일자</dt>
          <dd>
            <span id="initActivationDate"></span>
          </dd>
        </dl>
        <dl>
          <dt>모델명</dt>
          <dd>
            <span>${modelName}</span>
          </dd>
        </dl>
      </div>
      <div class="myinfo__tit">
        <h3>당월 청구요금</h3>
      </div>
      <div class="month_bill">
        <div class="month_bill c-accordion c-accordion--type2">
          <div class="c-accordion__box" id="accordion_month_bill">
            <div class="c-accordion__item">
              <div class="c-accordion__head" data-acc-header="#month_bill" aria-expanded="false">
                <button class="c-accordion__button" type="button" aria-expanded="false">
                  <dl>
                    <dt>사용기간</dt>
                    <dd id="billInfoPeriod">
                      <c:if test="${mMonthpaymentdto ne null}"><span>${mMonthpaymentdto.billStartDate}~${mMonthpaymentdto.billEndDate}</span></c:if>
                    </dd>
                  </dl>
                </button>
              </div>
              <div class="c-accordion__panel expand" id="month_bill">
                <div class="c-accordion__inside">
                  <ul class="bill-wrap" id="billInfo">
                    <li class="bill-item" id="billNoData" style="display: none;">
                      <div class="bill-nodata">조회된 결과가 없습니다.</div>
                    </li>
                    <li class="bill-item" id="thisMonthInfo">
                    </li>
                    <li class="bill-item" id="realTimeInfo">
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="myinfo__tit ">
        <h3>사용중 요금제</h3>
      </div>
      <div class="myrate">
        <div class="myrate__box">
          <p class="myrate__tit">${prvRateGrpNm}</p>
          <ul class="myrate__list">
            <li class="myrate__item">
              <i class="c-icon c-icon--payment-data" aria-hidden="true"></i>
              <span>${rateAdsvcLteDesc}</span>
            </li>
            <li class="myrate__item">
              <i class="c-icon c-icon--payment-call" aria-hidden="true"></i>
              <span>${rateAdsvcCallDesc}</span>
            </li>
            <li class="myrate__item">
              <i class="c-icon c-icon--payment-sms" aria-hidden="true"></i>
              <span>${rateAdsvcSmsDesc}</span>
              <a href="javascript:;" url="/m/mypage/childCallView.do" onclick="openNewDetailPopup(this);">이용량 조회하기</a>
            </li>
          </ul>
        </div>
      </div>
      <div class="myinfo__tit">
        <h3>부가서비스</h3>
      </div>
      <div class="c-accordion" data-ui-accordion>
        <ul class="c-accordion__box">
          <li class="c-accordion__item">
            <div class="c-accordion__head my-addservice">
              <button class="c-accordion__button" id="payAddSvcHeader" type="button" aria-expanded="false" aria-controls="payAddSvcContent">
                <dl class="u-w100p">
                  <dt>유료 부가서비스</dt>
                  <dd class="u-mr--24">
                    <span id="payCtn">0</span> 건
                  </dd>
                </dl>
              </button>
            </div>
            <div class="c-accordion__panel expand" id="payAddSvcContent" aria-labelledby="payAddSvcHeader">
              <div class="c-accordion__inside c-table">
                <table>
                  <caption>부가서비스 정보</caption>
                  <colgroup>
                    <col style="width: 60%">
                    <col style="width: 40%">
                  </colgroup>
                  <thead>
                  <tr>
                    <th scope="col">부가서비스명</th>
                    <th scope="col">
                      <p>이용요금</p>
                      <p>(VAT포함)</p>
                    </th>
                  </tr>
                  </thead>
                  <tbody id="payAddSvcList">

                  </tbody>
                </table>
              </div>
            </div>
          </li>
          <li class="c-accordion__item">
            <div class="c-accordion__head my-addservice" style="margin-top:0 !important;">
              <button class="c-accordion__button" id="freeAddSvcHeader" type="button" aria-expanded="false" aria-controls="freeAddSvcContent">
                <dl class="u-w100p">
                  <dt>무료 부가서비스</dt>
                  <dd class="u-mr--24">
                    <span id="freeCtn">0</span> 건
                  </dd>
                </dl>
              </button>
            </div>
            <div class="c-accordion__panel expand" id="freeAddSvcContent" aria-labelledby="freeAddSvcHeader">
              <div class="c-accordion__inside c-table">
                <table>
                  <caption>부가서비스 정보</caption>
                  <colgroup>
                    <col style="width: 60%">
                    <col style="width: 40%">
                  </colgroup>
                  <thead>
                  <tr>
                    <th scope="col">부가서비스명</th>
                    <th scope="col">
                      <p>이용요금</p>
                      <p>(VAT포함)</p>
                    </th>
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
      <div class="myinfo__tit">
        <h3>납부정보</h3>
      </div>
      <div class="charge_info">
        <table>
          <caption>청구/납부 정보 테이블</caption>
          <colgroup>
            <col style="width: 7.5rem;">
            <col>
          </colgroup>
          <tbody id="payArea">
          <tr>
            <td colspan="2" class="u-ta-center">조회 중 입니다.</td>
          </tr>
          </tbody>
        </table>
      </div>

    </div>

  </t:putAttribute>
</t:insertDefinition>