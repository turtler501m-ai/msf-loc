<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">
    <t:putAttribute name="titleAttr">당겨쓰기 이용/조회</t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
           <script type="text/javascript" src="/resources/js/portal/mypage/pullData01.js"></script>
        <script type="text/javascript" src="/resources/js/jquery.number.min.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">당겨쓰기 이용/조회</h2>
            </div>
            <div class="c-tabs c-tabs--type1" data-ui-tab>
                <div class="c-tabs__list" role="tablist">
                    <button class="c-tabs__button" id="tab1" role="tab" aria-controls="tabB1panel" aria-selected="false" onclick="btn_tab('1')">데이터 당겨쓰기</button>
                    <button class="c-tabs__button" id="tab2" role="tab" aria-controls="tabB2panel" aria-selected="false" onclick="btn_tab('2')">당겨쓰기 내역조회</button>
                </div>
            </div>
            <input type="hidden" name="selTab" id="selTab" value="1" />
            <input type="hidden" name="certifyYn" id="certifyYn" value="" />
            <input type="hidden" name="menuType" id="menuType" value="pull" />
            <input type="hidden" name="totalPull" id="totalPull" value="" />
            <input type="hidden" name="contractNum" id="contractNum" value=${searchVO.contractNum}>

            <div class="c-row c-row--lg">
                <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">조회할 회선을 선택해 주세요.</h3>
                <%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp"%>
                <hr class="c-hr c-hr--title">
            </div>
            <div class="c-tabs__panel" id="tabB1panel">
                <div class="c-row c-row--lg">
                    <div class="complete u-mt--80 u-ta-center chekPUllNn" style="display: none;">
                        <div class="c-icon c-icon--notice" aria-hidden="true"></div>
                        <strong class="u-block c-heading c-heading--fs24 c-heading--regular u-mt--32">
                            <b>당겨쓰기 가능한 요금제</b>에서만 <br> <b>이용</b> 가능합니다.
                        </strong>
                    </div>
                    <div class="product-guide chekPUllNn">
                        <div class="c-balloon c-balloon--left">당겨쓰기 가능한 요금제를 알려드려요!</div>
                        <dl class="product-guide__item">
                            <dt class="product-guide__title">M데이터 선택 USIM</dt>
                            <dd class="product-guide__text">300MB/1GB/2GB/3GB/6GB</dd>
                        </dl>
                        <dl class="product-guide__item">
                            <dt class="product-guide__title">LTE 통화 맘껏</dt>
                            <dd class="product-guide__text">300MB/1GB/3GB/6GB</dd>
                        </dl>
                    </div>
                    <div class="c-button-wrap u-mt--40 chekPUllNn">
                        <a class="c-button c-button--lg c-button--primary c-button--w460" href="javascript:void(0);" onclick="btn_farReg();">요금제 변경</a>
                    </div>

                    <div class="chekPUllYn" style="display: none;">
                        <strong class="u-block c-heading c-heading--fs24 c-heading--regular u-mt--32 u-ta-center" id="svcNm"> </strong>
                    </div>
                    <div class="data-info u-mt--56 chekPUllYn">
                        <div class="data-info--left u-width--460">
                            <p class="c-text c-text--fs17 u-fw--medium" id="todayData"></p>
                            <div class="c-form-field u-mt--24">
                                <div class="c-form__select">
                                    <select class="c-select" id="useDataList" name="selPullData">	</select>
                                    <label class="c-form__label" for="useDataList">당겨쓰기 데이터 선택</label>
                                </div>
                            </div>
                            <p class="c-bullet c-bullet--fyr u-mt--16 u-co-sub-4">SMS 인증 후 데이터 당겨쓰기를 이용 하실 수 있습니다. 데이터 당겨쓰기를 클릭하시면 인증화면으로 이동합니다.</p>
                        </div>
                        <div class="data-info--right chekPUllYn1" style="display: none;">
                            <div class="c-indicator-arc">
                                <div class="arcOverflow">
                                    <div class="arc arc-mint" id="totalPercent"></div>
                                </div>
                                <span class="indicator-state"> <br>
                                    <i class="c-icon c-icon--data" aria-hidden="true"></i>
                                </span>
                                <span id="totalData"></span>
                                <div class="indicator-text">
                                    <span id="data1Str"> </span>
                                    <strong id="data2Str"> </strong>
                                </div>
                            </div>
                        </div>
                        <div class="data-info--right callView" style="display: none;">
                            <div class="c-indicator-arc">
                                <div class="u-ta-center u-mt--48">
                                    <i class="c-icon c-icon--notice-sm" aria-hidden="true"></i>
                                    <p class="c-text c-text--fs16 u-mt--16">	안정적인 서비스를 위해 이용량 조회는 <b>120분 내 20회</b>로 제한하고 있습니다.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="c-button-wrap u-mt--56 chekPUllYn">
                        <a class="c-button c-button--lg c-button--primary c-button--w460" href="javascript:void(0);" onclick="btn_pullReg();">데이터 당겨쓰기</a>
                    </div>
                    <b class="c-flex c-text c-text--fs15 u-mt--48">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                        <span class="u-ml--4">알려드립니다</span>
                    </b>
                    <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                        <li class="c-text-list__item u-co-gray">데이터 당겨쓰기는 당겨쓰기 가능한 요금제 사용하시는 고객님이 다음 달 기본 제공 데이터를 이번 달에 미리 사용하실 수 있는 서비스 입니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 한 데이터 중 남은 데이터는 다음 달 1일(00시) 기본 제공 데이터로 이월되며, 당겨쓰기 이월 데이터 > 이월 데이터 > 요금제 기본 제공 데이터 순으로 차감됩니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 이용 고객이 당겨쓰기 불가능한 요금제로 변경하거나, 명의변경, 해지 시에는 당겨쓰기 한 데이터가 일할 없이 즉시 취소되며(제공량 소멸), 데이터 사용량을 재계산하여 기본 제공량을 초과한 사용량은 요금이 부과될 수 있습니다. 요금제 변경 시 다음 사항을 유의해주시기 바랍니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 가능한 요금제로 변경 시, 이미 당겨쓰기 한 데이터량이 변경 신청할 요금제의 기본 제공량을 초과할 경우, 초과 데이터 대한 요금이 다음 달 사용분으로 청구됩니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 불가능한 요금제로 변경 시, 이미 당겨쓰기 한 데이터량이 있을 경우, 해당 데이터에 대한 요금이 당월 사용분으로 청구됩니다.</li>
                    </ul>
                </div>
            </div>

            <div class="c-tabs__panel" id="tabB2panel">
                <div class="c-row c-row--lg">
                    <div class="u-flex-between">
                        <h4 class="c-heading c-heading--fs16">당월 당겨쓰기 내역</h4>
                        <span class="c-text c-text--fs14 u-co-gray" id="tdoayHist"></span>
                    </div>
                    <div class="c-nodata histNoData">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-nodata__text">조회 결과가 없습니다.</p>
                    </div>
                    <div class="c-table u-mt--16 histDataList" style="display: none;">
                        <table>
                            <caption>신청일자, 종료일자, 당겨쓰기 데이터로 구성된 당월 당겨쓰기 내역 정보</caption>
                            <colgroup>
                                <col style="width: 33.33%">
                                <col style="width: 33.33%">
                                <col>
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">신청일자</th>
                                    <th scope="col">종료일자</th>
                                    <th scope="col">당겨쓰기 데이터</th>
                                </tr>
                            </thead>
                            <tbody id="dataHistView">
                            </tbody>
                        </table>
                    </div>
                    <b class="c-flex c-text c-text--fs15 u-mt--48">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                        <span class="u-ml--4">알려드립니다</span>
                    </b>
                    <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                        <li class="c-text-list__item u-co-gray">데이터 당겨쓰기는 당겨쓰기 가능한 요금제 사용하시는 고객님이 다음 달 기본 제공 데이터를 이번 달에 미리 사용하실 수 있는 서비스 입니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 한 데이터 중 남은 데이터는 다음 달 1일(00시) 기본 제공 데이터로 이월되며, 당겨쓰기 이월 데이터 > 이월 데이터 > 요금제 기본 제공 데이터 순으로 차감됩니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 이용 고객이 당겨쓰기 불가능한 요금제로 변경하거나, 명의변경, 해지 시에는 당겨쓰기 한 데이터가 일할 없이 즉시 취소되며(제공량 소멸), 데이터 사용량을 재계산하여 기본 제공량을 초과한 사용량은 요금이 부과될 수 있습니다. 요금제 변경 시 다음 사항을 유의해주시기 바랍니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 가능한 요금제로 변경 시, 이미 당겨쓰기 한 데이터량이 변경 신청할 요금제의 기본 제공량을 초과할 경우, 초과 데이터 대한 요금이 다음 달 사용분으로 청구됩니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 불가능한 요금제로 변경 시, 이미 당겨쓰기 한 데이터량이 있을 경우, 해당 데이터에 대한 요금이 당월 사용분으로 청구됩니다.</li>
                    </ul>
                </div>
            </div>
        </div>
        <script>
            function arcProgress() {
              var progressBars = document.querySelectorAll('.c-indicator-arc');
              if (progressBars.length > 0) {
                progressBars.forEach(function(arcProgress) {
                  var bar = arcProgress.querySelector('.arc');
                  if(bar) {
                      var val = bar.dataset.percent;
                      var perc = parseInt(val, 10);
                      var bar_text = arcProgress.querySelector('.indicator-text');
                      //ie 11 경우 체크
                      var agent = navigator.userAgent.toLowerCase();
                      if ((navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || agent.indexOf('msie') != -1) {
                        // ie일 경우
                        bar.style['msTransform'] = 'rotate(' + (45 + perc * 1.8) + 'deg)';
                      } else {
                        // ie가 아닐 경우
                        bar.style['WebkitTransform'] = 'rotate(' + (45 + perc * 1.8) + 'deg)';
                      }
                  }
                });
              }
            }
      </script>
    </t:putAttribute>
</t:insertDefinition>
