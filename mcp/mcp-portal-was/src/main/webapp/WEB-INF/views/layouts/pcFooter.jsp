<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<footer class="ly-footer">
    <div class="c-box-download">
        <div class="c-box-inner">
              <div class="download-title">
                <img class="title-download" src="/resources/images/portal/content/title_download.png" alt="앱 다운로드">
                <div class="download-anchor">
                    <a class="c-button-round" href="https://play.google.com/store/apps/details?id=kt.co.ktmmobile" target="_blank" title="구글플레이 kt M모바일 APP 다운로드 바로가기 새창열림">
                          <i class="c-icon c-icon--google-play" aria-hidden="true"></i>Google Play
                    </a>
                    <a class="c-button-round" href="https://itunes.apple.com/us/app/kt-mmobail-gogaegsenteo/id1094611503?mt=8" target="_blank" title="앱스토어 kt M모바일 APP 다운로드 바로가기 새창열림">
                          <i class="c-icon c-icon--apple-white" aria-hidden="true"></i>App Store
                    </a>
                  </div>
              </div>
              <div class="family-site">
                  <div class="c-form dark-theme">
                    <label class="c-label c-hidden" for="select001">패밀리 사이트 바로가기</label>
                    <select class="c-select" id="select001">
                      <option label="패밀리 사이트" disabled selected>패일리 사이트</option>
                      <option value="http://www.ktcloud.com" title="kt cloud 새창">kt cloud</option>
                      <option value="https://www.ktds.com" title="kt ds 새창">kt ds</option>
                      <option value="https://www.ktgdh.com" title="kt gdh 새창">kt gdh</option>
                      <option value="https://www.ktmns.com" title="kt m&s 새창">kt m&s</option>
                      <option value="http://www.ktmmobile.com" title="kt M mobile 새창">kt M mobile</option>
                      <option value="https://www.kttelecop.co.kr" title="kt telecop 새창">kt telecop</option>
                      <option value="http://www.ktsat.com" title="kt sat 새창">kt sat</option>
                      <option value="https://www.ktis.co.kr" title="kt is 새창">kt is</option>
                      <option value="https://www.ktcs.co.kr" title="kt cs 새창">kt cs</option>
                      <option value="https://www.ktservice.co.kr" title="kt service 북부 새창">kt service 북부</option>
                      <option value="http://www.ktservice.net" title="ktservice 남부 새창">kt service 남부</option>
                      <option value="http://www.ktmos.com" title="KT MOS 북부 새창">KT MOS 북부</option>
                      <option value="http://www.ktmos.co.kr" title="KT MOS 남부 새창">KT MOS 남부</option>
                      <option value="https://www.ktengineering.co.kr" title="kt engineering 새창">kt engineering</option>
                      <option value="https://www.ktnetcore.com/" title="kt netcore 새창">kt netcore</option>
                      <option value="https://ktpowerandmw.co.kr/" title="kt p&m 새창">kt p&m</option>
                      <option value="http://www.ktcommerce.co.kr/login.jsp" title="kt commerce 새창">kt commerce</option>
                      <option value="http://www.kthopemate.com/" title="kt 희망지음 새창">kt 희망지음</option>
                      <option value="http://www.kt수련관.com/" title="kt hs 새창">kt hs</option>
                      <option value="https://www.ktgf.or.kr/" title="kt그룹 희망나눔재단 새창">kt그룹 희망나눔재단</option>
                      <option value="http://www.ktskylife.co.kr" title="kt skylife 새창">kt skylife</option>
                      <option value="http://www.hcn.co.kr" title="kt HCN 새창">kt HCN</option>
                      <option value="https://www.geniemusic.co.kr" title="kt genie music 새창">kt genie music</option>
                      <option value="http://www.altimedia.com" title="kt altimedia 새창">kt altimedia</option>
                      <option value="https://www.studiogenie.co.kr" title="kt StudioGenie 새창">kt StudioGenie</option>
                      <option value="https://ena.skylifetv.co.k" title="kt ENA 새창">kt ENA</option>
                      <option value="http://www.millie.co.kr" title="k밀리의 서재 새창">밀리의 서재</option>
                      <option value="http://www.kt-sports.co.kr" title="kt sports 새창">kt sports</option>
                      <option value="http://www.storywiz.co.kr" title="storywiz 새창">storywiz</option>
                      <option value="https://www.ktalpha.com" title="kt alpha 새창">kt alpha</option>
                      <option value="http://www.nasmedia.co.kr" title="nasmedia 새창">nasmedia</option>
                      <option value="https://www.bccard.com" title="비씨카드 새창">비씨카드</option>
                      <option value="http://www.kbanknow.com" title="K bank 새창">K bank</option>
                      <option value="http://www.vp.co.kr" title="브이피㈜ 새창">브이피㈜</option>
                      <option value="http://www.ktinvestment.co.kr" title="kt investment 새창">kt investment</option>
                      <option value="https://www.smartro.co.kr" title="Smartro 새창">Smartro</option>
                      <option value="http://www.hncnet.co.kr" title="H&C Network 새창">H&C Network</option>
                      <option value="https://www.ktestate.com/" title="kt estate 새창">kt estate</option>
                      <option value="http://www.ktamc.com" title="kt 투자운용 새창">kt 투자운용</option>
                      <option value="http://kt-living.com" title="KT Living 새창">KT Living</option>

                    </select>
                    <button class="c-button moveto-btn" onclick="familyOpen();" title="새창열림">이동</button>
                  </div>
              </div>
        </div>
      </div>
      <div class="c-box-footer">
          <div class="c-footer-link">
              <div class="c-box-inner">
                <a class="c-link" href="/company/main.do" target="_blank" title="새창열림">주식회사 케이티엠모바일</a>
                <a class="c-link" href="/policy/privacyView.do" target="_blank" title="새창열림"><em>개인정보처리방침</em></a>
                <a class="c-link" href="/policy/ktPolicy.do?detail=footer" target="_blank" title="새창열림">이용약관</a>
                <a class="c-link" href="/policy/youngList.do" target="_blank" title="새창열림">청소년 보호정책</a>
                <a class="c-link" href="/company/mgtPhilosophy.do#subGnb1" target="_blank" title="새창열림">윤리상담센터</a>
                <a class="c-link" href="javascript:void(0);" onclick="openPage('pullPopup', '/jehuSugg/jehuSuggForm.do');" title="팝업열림">제휴제안</a>
                <%--<a class="c-link" href="javascript:void(0);" onclick="openPage('pullPopup', '/telCounsel/telCounselForm.do');" title="팝업열림">법인 가입 상담</a>--%>
                <a class="c-link" href="/product/phone/officialNoticeSupportList.do" target="_blank" title="새창열림">공통지원금</a>
                <a class="c-link" href="https://msafer.or.kr/index.do" target="_blank" title="새창열림">명의도용방지서비스</a>
                <a class='c-link' href="javascript:openPopup('https://nqi.kt.com/KTCVRG/coverage',1050,800,'coverage','30','500');" title="팝업열림">서비스커버리지</a>
              </div>
        </div>
        <div class="c-footer-company">
              <div class="c-box-inner">
                <div class="left-box">
                      <img class="logo-white" src="/resources/images/portal/content/logo_white.png" alt="kt M mobile 로고">
                       <ul class="company-info">
                          <li><em>주식회사 케이티엠모바일 대표이사 : 구강본</em><em>주소 : 06193 서울특별시 강남구 테헤란로 422 KT 선릉타워 12층</em><em>사업자등록번호 : 133-81-43410</em>통신판매업신고 : 2015-서울강남-01576 <a href="https://www.ftc.go.kr/bizCommPop.do?wrkr_no=1338143410" target="_blank" title="사업자 정보 확인 바로가기 새창열림">&nbsp;사업자 정보 확인</a></li>
                          <li><span>고객센터 : 1899-5000 (무료 kt M모바일 114)</span><span>운영시간 : 09~12시 / 13~18시</span>토요일, 일요일, 공휴일은 분실, 정지신고 및 통화품질 관련 상담만 가능</li>
                          <li>Copyright c kt M mobile. All rights reserved.</li>
                       </ul>
                </div>
              </div>
        </div>
        <div class="c-footer-certification">
              <a href="http://kwacc.or.kr/CertificationSite/WA/2196/Detail?page=1&SearchSiteName=kt" target="_blank" title="한국디지털접근성진흥원 홈페이지 바로가기_새창">
                <dl>
                      <dt>
                        <img src="/resources/images/portal/content/img_kwacc_2025.png" alt="국가공인 정보통신접근성 품질인증마크" style="width: 2.5rem;">
                      </dt>
                      <dd>과학기술정보통신부
                        <br>웹접근성 인증
                      </dd>
                </dl>
              </a>
              <a href="http://www.ips.or.kr/05_06.php" target="_blank" title="2020 국가서비스대상 알뜰폰 부문 대상 바로가기 새창열림">
                   <dl>
                      <dt>
                        <img src="/resources/images/portal/content/img_service_prize.png" alt="2020 국가서비스대상 로고">
                      </dt>
                      <dd>2020 국가서비스대상
                        <br>알뜰폰 부분 대상
                      </dd>
                </dl>
              </a>
              <a href="https://ks-cqi.ksa.or.kr/ks-cqi/index.do" target="_blank" title="2025년 콜센터 품질지수 알뜰폰 부문 우수기업 바로가기 새창열림">
                <dl>
                      <dt>
                        <img src="/resources/images/portal/content/img_callcenter.png" alt="2025년 콜센터 품질지수 알뜰폰 부문 우수기업 인증 로고">
                      </dt>
                      <dd>2025년 콜센터 품질지수
                        <br>알뜰폰 부문 우수기업
                      </dd>
                </dl>
              </a>
              <a href="https://isms-p.kisa.or.kr/main/ispims/issue;jsessionid=00105F051339DBCEB19CD089C85E6288/?certificationMode=list&crtfYear=&searchCondition=2&searchKeyword=%EC%BC%80%EC%9D%B4%ED%8B%B0%EC%97%A0" target="_blank" title="정보보호 관리체계 인증 바로가기 새창열림">
                <dl>
                      <dt>
                        <img src="/resources/images/portal/content/img_isms.png" alt="정보보호 관리체계 인증 로고">
                      </dt>
                      <dd>정보보호
                        <br>관리체계 인증
                      </dd>
                   </dl>
              </a>
              <a href="http://www.wiseuser.go.kr" target="_blank" title="와이즈유저 이용자 피해예방 인증 바로가기 새창열림">
                   <dl>
                      <dt>
                        <img src="/resources/images/portal/content/img_wiseuser.png" alt="와이즈유저 이용자 피해예방 인증 로고">
                      </dt>
                      <dd>와이즈유저
                        <br>이용자 피해예방
                      </dd>
                </dl>
              </a>
        </div>
      </div>
</footer>