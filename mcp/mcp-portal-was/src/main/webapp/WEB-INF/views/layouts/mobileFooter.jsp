<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="serverNm" value="${nmcp:getPropertiesVal('SERVER_NAME')}" />
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />

<footer class="ly-footer">
  <div class="u-flex-center u-mb--24">
    <img class="c-logo" src="/resources/images/mobile/common/img_logo_ktm.png" alt="KT M Mobile">
  </div>
  <ul class="footer__link">
    <li class="no-split">
       <c:choose>
        <c:when test="${appFormYn eq 'Y'}">
              <a class="c-text c-text--type4 u-co-gray-8" href="javascript:void(0);" onclick="leaveChkLayer('/company/main.do');" target="_blank" title="새창열림">케이티엠모바일</a>
          </c:when>
          <c:otherwise>
<c:if test = "${platFormCd eq 'A'}">
              <a class="c-text c-text--type4 u-co-gray-8" href="javascript:appOutSideOpen('https://www.ktmmobile.com/company/main.do');" title="새창열림">케이티엠모바일</a>
</c:if>
<c:if test = "${platFormCd ne 'A'}">
              <a class="c-text c-text--type4 u-co-gray-8" href="/company/main.do" target="_blank" title="새창열림">케이티엠모바일</a>
</c:if>
          </c:otherwise>
    </c:choose>
    </li>
    <li>
        <c:choose>
            <c:when test="${appFormYn eq 'Y'}">
                  <a class="c-text c-text--type4 u-co-black" href="javascript:void(0);" onclick="leaveChkLayer('/m/policy/mPrivacyView.do');"><b>개인정보처리방침</b></a>
              </c:when>
              <c:otherwise>
                  <a class="c-text c-text--type4 u-co-black" href="/m/policy/mPrivacyView.do"><b>개인정보처리방침</b></a>
              </c:otherwise>
          </c:choose>
    </li>
    <li>
        <c:choose>
            <c:when test="${appFormYn eq 'Y'}">
                <a class="c-text c-text--type4 u-co-gray-8" href="javascript:void(0);" onclick="leaveChkLayer('/m/policy/ktPolicy.do?detail=footer');">이용약관</a>
            </c:when>
            <c:otherwise>
                <a class="c-text c-text--type4 u-co-gray-8" href="/m/policy/ktPolicy.do?detail=footer">이용약관</a>
            </c:otherwise>
        </c:choose>
    </li>
  </ul>
  <div class="c-accordion c-accordion--type4 u-mt--8">
    <ul class="c-accordion__box u-mb--40" id="accordion1">
      <li class="c-accordion__item no-line">
        <div class="c-accordion__panel expand" id="chkFooter1">
          <div class="c-accordion__inside box-white">
            <ul class="footer__link">
<c:if test = "${platFormCd eq 'A'}">
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="/m/policy/youngList.do" title="새창열림">청소년 보호정책</a>
              </li>
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="javascript:appOutSideOpen('https://www.ktmmobile.com/company/mgtPhilosophy.do#subGnb1');" title="새창열림">윤리상담센터</a>
              </li>
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="javascript:void(0);" onclick="openPage('pullPopup', '/m/jehuSugg/jehuSuggFormView.do');" title="팝업열림">제휴제안</a>
              </li>
              <%--<li>
                <a class="c-text c-text--type4 u-co-gray-8" href="javascript:void(0);" onclick="openPage('pullPopup', '/m/telCounsel/telCounselView.do');" title="팝업열림">법인 가입상담</a>
              </li>--%>
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="javascript:appOutSideOpen('https://msafer.or.kr/index.do');" title="새창열림">명의도용방지서비스</a>
              </li>
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="/m/product/phone/officialNoticeSupportList.do" title="새창열림">공통지원금</a>
              </li>
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="javascript:appOutSideOpen('https://nqi.kt.com/KTCVRG/coverage');" title="새창열림">서비스커버리지</a>
              </li>
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="javascript:appOutSideOpen('https://www.ftc.go.kr/bizCommPop.do?wrkr_no=1338143410');" title="새창열림">사업자정보 확인</a>
              </li>
</c:if>
<c:if test = "${platFormCd ne 'A'}">
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="/m/policy/youngList.do" target="_blank" title="새창열림">청소년 보호정책</a>
              </li>
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="/company/mgtPhilosophy.do#subGnb1" target="_blank" title="새창열림">윤리상담센터</a>
              </li>
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="javascript:void(0);" onclick="openPage('pullPopup', '/m/jehuSugg/jehuSuggFormView.do');" title="팝업열림">제휴제안</a>
              </li>
              <%--<li>
                <a class="c-text c-text--type4 u-co-gray-8" href="javascript:void(0);" onclick="openPage('pullPopup', '/m/telCounsel/telCounselView.do');" title="팝업열림">법인 가입상담</a>
              </li>--%>
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="https://msafer.or.kr/index.do" target="_blank" title="새창열림">명의도용방지서비스</a>
              </li>
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="/m/product/phone/officialNoticeSupportList.do" target="_blank" title="새창열림">공통지원금</a>
              </li>
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="https://nqi.kt.com/KTCVRG/coverage" target="_blank" title="새창열림">서비스커버리지</a>
              </li>
              <li>
                <a class="c-text c-text--type4 u-co-gray-8" href="https://www.ftc.go.kr/bizCommPop.do?wrkr_no=1338143410" target="_blank" title="새창열림">사업자정보 확인</a>
              </li>
</c:if>
            </ul>
            <hr class="c-hr c-hr--type3 u-mt--16">
            <div class="footer-info u-mt--24">
              <p class="c-text c-text--type5 u-co-black u-fw--medium">주식회사 케이티엠모바일</p>
              <p class="c-text c-text--type5 u-co-gray-8">대표자 : 구강본 | 사업자등록번호 : 133-81-43410</p>
              <p class="c-text c-text--type5 u-co-gray-8">서울특별시 강남구 테헤란로 422 KT 선릉타워 12층</p>
              <p class="c-text c-text--type5 u-co-gray-8">Copyright &copy; kt M mobile, inc. Allright reserved.</p>
<c:if test = "${platFormCd eq 'A'}">
              <a class="logo-isms" href="javascript:appOutSideOpen('https://isms-p.kisa.or.kr/main/ispims/issue;jsessionid=00105F051339DBCEB19CD089C85E6288/?certificationMode=list&crtfYear=&searchCondition=2&searchKeyword=%EC%BC%80%EC%9D%B4%ED%8B%B0%EC%97%A0');" >
                <img src="/resources/images/mobile/content/img_isms.png" alt="정보보호 관리체계인증">
              </a>
</c:if>
<c:if test = "${platFormCd ne 'A'}">
              <a class="logo-isms" href="https://isms-p.kisa.or.kr/main/ispims/issue;jsessionid=00105F051339DBCEB19CD089C85E6288/?certificationMode=list&crtfYear=&searchCondition=2&searchKeyword=%EC%BC%80%EC%9D%B4%ED%8B%B0%EC%97%A0" target="_blank">
                <img src="/resources/images/mobile/content/img_isms.png" alt="정보보호 관리체계인증">
              </a>
</c:if>
            </div>
<c:if test = "${platFormCd ne 'A'}">
            <button class="c-button c-button--full c-button--white u-mt--24" onclick="goPcView();">PC보기</button>
</c:if>
          </div>
        </div>
        <!--[2021-12-22] 어코디언 작동 버튼 margin 추가-->
        <div class="c-accordion__head u-mt--24" data-acc-header="#chkFooter1">
          <button class="c-accordion__button c-accordion__button_type1" type="button" aria-expanded="false">
            <i class="c-icon c-icon--arrow-down-open" aria-hidden="true"></i>
          </button>
        </div>
      </li>
    </ul>
  </div>
</footer>


