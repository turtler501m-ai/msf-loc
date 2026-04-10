<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/mobile/mypage/pullData01.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.number.min.js"></script>
</t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    당겨쓰기 이용/조회
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <input type="hidden" name="selTab" id="selTab" value="1" />
            <input type="hidden" name="certifyYn" id="certifyYn" value="" />
            <input type="hidden" name="menuType" id="menuType" value="pull" />
            <input type="hidden" name="totalPull" id="totalPull" value="" />
            <input type="hidden" name="contractNum" id="contractNum" value=${searchVO.contractNum}>

            <div class="c-tabs c-tabs--type2">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button" data-tab-header="#tabB1-panel" onclick="btn_tab('1')">데이터 당겨쓰기</button>
                    <button class="c-tabs__button" data-tab-header="#tabB2-panel" onclick="btn_tab('2')">당겨쓰기 내역조회</button>
                </div>
                <hr class="c-hr c-hr--type3 c-expand">
                <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
                <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp"%>
                <div class="c-tabs__panel border-none" id="tabB1-panel">
                    <div class="banner-balloon u-mt--40 u-mb--24 chekPUllYn">
                        <p>
                            <span class="c-text c-text--type5">이용중인 요금제</span> <br>
                            <b class="c-text c-text--type7" id="svcNm"></b>
                        </p>
                        <img class="deco-img" src="/resources/images/mobile/content/img_bolloon_banner_03.png" alt="">
                    </div>
                    <div class="c-box u-mt--48 chekPUllYn">
                        <div class="c-box__title" id="todayData"></div>
                        <div class="c-item c-item--type1 u-mt--24 u-mb--40" id="chk01" style="display: none;">
                            <div class="c-item__title flex-type--between">
                                <span>
                                    <i class="c-icon c-icon--data" aria-hidden="true"></i>
                                    <span class="u-ml--8" id="totalData"></span>
                                </span>
                                <span class="u-ml--auto c-text c-text--type4" id="outRemain"></span>
                            </div>
                            <div class="c-indicator c-indicator--type1 u-mt--16">
                                <span class="mark-mint" id="totalPercent" style="width: 0%"></span>
                            </div>
                        </div>
                    </div>
                    <div class="u-ta-center u-mt--40" id="chk02" style="display: none;">
                        <i class="c-icon c-icon--notice-sm" aria-hidden="true"></i>
                        <p class="c-text c-text--type3 u-mt--16">안정적인 서비스를 위해 이용량 조회는 <br> <b>120분 내 20회</b>로 제한하고 있습니다. <br>잠시 후 이용해 주시기 바랍니다.</p>
                    </div>
                    <hr class="c-hr c-hr--type3 c-expand cheklabel" style="display: none;">
                    <div class="c-form u-mt--40 chekPUllYn">
                        <div class="c-form__select has-value">
                            <select class="c-select" id="useDataList">
                            </select>
                            <label class="c-form__label" for="useDataList">당겨쓰기 데이터 선택</label>
                        </div>
                    </div>
                    <ul class="c-text-list c-bullet c-bullet--fyr chekPUllYn">
                        <li class="c-text-list__item u-co-gray">SMS 인증 후 데이터 당겨쓰기를 이용 하실 수 있습니다. 데이터 당겨쓰기를 클릭하시면 인증화면으로 이동합니다.</li>
                    </ul>
                    <button class="c-button c-button--full c-button--primary u-mt--48 u-mb--40 chekPUllYn" onclick="btn_pullReg();">데이터 당겨쓰기</button>

                    <div class="complete u-pb--20 chekPUllNn">
                        <div class="c-icon c-icon--notice" aria-hidden="true">
                            <span></span>
                        </div>
                        <p class="complete__heading">
                            <b>당겨쓰기 가능한 요금제</b>에서만 <br> <b>이용</b>가능합니다
                        </p>
                    </div>
                    <div class="u-ta-center u-mt--48 useTimeSvcLimitView" style="display: none;">
                        <i class="c-icon c-icon--notice-sm" aria-hidden="true"></i>
                        <p class="c-text c-text--fs16 u-mt--16">
                            안정적인 서비스를 위해 이용량 조회는 <b>120분 내 20회</b>로 제한하고 있습니다. <br>잠시 후 이용해 주시기 바랍니다.
                        </p>
                    </div>
                    <h3 class="c-heading c-heading--type2 u-mb--12 chekPUllNn">당겨쓰기 가능한 요금제</h3>
                    <div class="chekPUllNn" id="pullYn1">
                        <span class="c-text c-text--type2 u-fw--medium chekPUllNn">M데이터 선택 USIM</span>
                        <span class="c-text c-text--type4 u-co-gray chekPUllNn">300MB/1GB/2GB/3GB/6GB</span>
                    </div>
                    <div class="u-mt--8 chekPUllNn" id="pullYn2">
                        <span class="c-text c-text--type2 u-fw--medium">LTE 통화 맘껏</span>
                        <span class="c-text c-text--type4 u-co-gray">300MB/1GB/3GB/6GB</span>
                    </div>
                    <div class="c-button-wrap u-mt--48 chekPUllNn">
                        <a class="c-button c-button--full c-button--primary" onclick="btn_farReg();">요금제 변경</a>
                    </div>

                    <h3 class="c-text c-text--type3 u-mt--24">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
                    </h3>
                    <ul class="c-text-list c-bullet c-bullet--dot u-mt--16 u-mb--32">
                        <li class="c-text-list__item u-co-gray">데이터 당겨쓰기는 당겨쓰기 가능한 요금제 사용하시는 고객님이 다음 달 기본 제공 데이터를 이번 달에 미리 사용하실 수 있는 서비스입니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 한 데이터 중 남은 데이터는 다음 달 1일 (00시) 기본 제공 데이터로 이월되며, 당겨쓰기 이월 데이터 > 이월 데이터 > 요금제 기본 제공 데이터 순으로 차감됩니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 이용 고객이 당겨쓰기 불가능한 요금제로 변경하거나, 명의변경, 해지 시에는 당겨쓰기 한 데이터가 일할 없이 즉시 취소되며 (제공량 소멸), 데이터 사용량을 재계산하여 기본 제공량을 초과한 사용량은 요금이 부과될 수 있습니다. 요금제 변경 시 다음 사항을 유의해주시기 바랍니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 가능한 요금제로 변경 시, 이미 당겨쓰기한 데이터량이 변경 신청할 요금제의 기본 제공량을 초과할 경우, 초과 데이터 대한 요금이 다음 달 사용분으로 청구됩니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 불가능한 요금제로 변경 시, 이미 당겨쓰기 한 데이터량이 있을 경우, 해당 데이터에 대한 요금이 당월 사용분으로 청구됩니다.</li>
                    </ul>
                </div>
                <div class="c-tabs__panel border-none" id="tabB2-panel">
                    <div class="c-flex c-flex--jfy-between u-mt--40 u-mb--12">
                        <h3 class="c-heading c-heading--type2 u-mt--0 u-mb--0">당월 당겨쓰기 내역</h3>
                        <span class="c-text c-text--type4 u-co-gray" id="tdoayHist"></span>
                    </div>
                    <div class="c-nodata histNoData">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-noadat__text">조회 결과가 없습니다.</p>
                    </div>
                    <div class="c-table histDataList" style="display: none;">
                        <table>
                            <caption>결합 중인 회선</caption>
                            <colgroup>
                                <col style="width: 33.33%">
                                <col style="width: 33.33%">
                                <col style="width: 33.33%">
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
                    <hr class="c-hr c-hr--type2">
                    <b class="c-text c-text--type3">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
                    </b>
                    <ul class="c-text-list c-bullet c-bullet--dot">
                        <li class="c-text-list__item u-co-gray">데이터 당겨쓰기는 당겨쓰기 가능한 요금제 사용하시는 고객님이 다음 달 기본 제공 데이터를 이번 달에 미리 사용하실 수 있는 서비스입니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 한 데이터 중 남은 데이터는 다음 달 1일 (00시) 기본 제공 데이터로 이월되며, 당겨쓰기 이월 데이터 > 이월 데이터 > 요금제 기본 제공 데이터 순으로 차감됩니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 이용 고객이 당겨쓰기 불가능한 요금제로 변경하거나, 명의변경, 해지 시에는 당겨쓰기 한 데이터가 일할 없이 즉시 취소되며 (제공량 소멸), 데이터 사용량을 재계산하여 기본 제공량을 초과한 사용량은 요금이 부과될 수 있습니다. 요금제 변경 시 다음 사항을 유의해주시기 바랍니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 가능한 요금제로 변경 시, 이미 당겨쓰기한 데이터량이 변경 신청할 요금제의 기본 제공량을 초과할 경우, 초과 데이터 대한 요금이 다음 달 사용분으로 청구됩니다.</li>
                        <li class="c-text-list__item u-co-gray">당겨쓰기 불가능한 요금제로 변경 시, 이미 당겨쓰기 한 데이터량이 있을 경우, 해당 데이터에 대한 요금이 당월 사용분으로 청구됩니다.</li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- [START]-->
    </t:putAttribute>
</t:insertDefinition>