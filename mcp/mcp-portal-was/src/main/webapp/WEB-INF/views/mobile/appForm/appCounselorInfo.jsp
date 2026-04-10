<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr"/>
    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">상담사 개통 신청<button class="header-clone__gnb"></button></h2>
            </div>
            <div class="upper-banner upper-banner--connect_counselor c-expand">
                <div class="upper-banner__content">
                    <strong class="upper-banner__title">
                        신청서를 작성하시면<br />AI 상담사 또는 개통 상담사가<br />순차적으로 개통을 도와드립니다.
                    </strong>
                    <p>야간에 상담사 개통 신청을 하신 경우 오전 9시 이후 순차적으로 전화드립니다.</p>
                    <a class="c-button c-button--sm upper-banner__anchor" href="/m/appForm/appFormDesignView.do">
                        가입신청서 작성<i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
                    </a>
                </div>
                <div class="upper-banner__image">
                    <img src="/resources/images/mobile/content/img_connect_counselor.png" alt="">
                </div>
            </div>
            <div class="banner-guide__box u-mt--46">
                <p class="ad-text">
                       개통안내전화를 기다릴 필요없이 <span class="c-text--type3 u-co-black c-heading--type2">직접 개통하는 셀프개통</span> 방법도 확인해 보세요.
                    <a class="c-text-link--bluegreen-type2" href="/m/appForm/appSimpleInfo.do">유심 셀프개통 바로가기</a>
                </p>
                <p class="ad-text u-mt--8">
                    eSIM으로 셀프개통이 가능합니다. eSIM 개통 가능 여부를 확인해 보세요.
                    <a class="c-text-link--bluegreen-type2" href="/m/appForm/eSimInfom.do">eSIM 알아보기</a>
                </p>
            </div>
            <p class="c-heading c-heading--type8">셀프개통 가능시간</p>
            <div class="self-opening-box c-expand">
                <p class="c-bullet c-bullet--dot u-co-gray c-text--fs13 u-mt--2"><span class="c-text c-text--fs13 u-co-black u-mr--4">번호이동 10:00 ~ 19:50</span>(일요일, 신정/설/추석 당일 제외)</p>
                <p class="c-bullet c-bullet--dot u-co-gray c-text--fs13 u-mt--8 u-mb--4"><span class="c-text c-text--fs13 u-co-black u-mr--4">신규가입 08:00 ~ 21:50</span></p>
                <p class="c-bullet c-bullet--fyr u-co-gray u-mt--13 u-mb--6">
              외국인,미성년자는 가입신청서 작성완료후, 순차적으로 연락하여 개통안내를 드립니다.
                    <a class="c-text-link--bluegreen" href="/m/appForm/appFormDesignView.do?onOffType=3&operType=MNP3&indcOdrg=1&prdtSctnCd=LTE">가입신청서 작성하기</a>
                </p>
            </div>
            <p class="c-heading c-heading--type2 u-mt--32 u-mb--12">* 통신요금 미납정보 조회 방법 안내</p>
            <p class="c-bullet c-bullet--fyr u-fs-13 u-ml--10">개통을 더욱 빠르고 원활하게 진행하시려면, 아래 사이트에서 통신요금 미납 여부와 단기간 다회선 가입 이력을 사전에 확인 해보시는 것을 권장 드립니다.<br />(통신요금 미납이 있거나, 최근 180일 이내에 3회선 이상을 가입한 이력이 있을 경우, 개통이 어려울 수 있습니다.)</p>
            <hr class="c-hr c-hr--type2 u-mt--20 u-mb--0">
            <div class="c-accordion c-accordion--type1">
			  <ul class="c-accordion__box" id="appCounselorAcc">
			    <li class="c-accordion__item">
			      <div class="c-accordion__head" data-acc-header="#appCounselorAcc_content" id="appCounselorAcc_header" aria-expanded="false">
			        <button class="c-accordion__button u-fw--medium" type="button" aria-expanded="false">통신요금 미납정보 조회 사이트 바로가기</button>
			      </div>
			      <div class="c-accordion__panel expand" id="appCounselorAcc_content">
			        <div class="c-accordion__inside u-pa--16">
			          <ul class="c-text-list c-bullet c-bullet--hyphen">
                          <li class="c-text-list__item">링크 : 방송통신 신용정보 공동관리 (<a href="https://www.credit.or.kr/" title="방송통신 신용정보 공동관리 페이지 새창 열기" target="_blank">credit.or.kr</a>)</li>
                          <li class="c-text-list__item">
                                     접속경로 :
                              <ul class="c-text-list u-mt--8">
                                  <li class="c-text-list__item">1) 사이트 접속 후 ‘본인신용정보 조회하기’ 클릭</li>
                                  <li class="c-text-list__item">2) 공동인증서 또는 휴대폰 인증으로 간편한 본인확인 진행</li>
                                  <li class="c-text-list__item">3) 현재 통신사 미납정보와 단기간 다회선 가입여부 확인 가능</li>
                              </ul>
                          </li>
                      </ul>
			        </div>
			      </div>
			    </li>
			  </ul>
			</div>
            <div class="bottom-link__box c-expand u-mt--48 u-mb--32">
                <a class="link__anchor" href="/m/mmobile/ktmMobileUsimGuidView.do">
                    <b class="c-text c-text--type7 u-mr--8">유심 및 개통 가이드</b>
                    <i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
                    <img src="/resources/images/mobile/content/img_deco_phone.png" alt="">
                </a>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>