<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header>
	<nav id="gnbMenu">
	  <div class="menu-wrap">
	      <div class="menu-logo">
	        <a class="ly-header__logo" href="/company/main.do" title="kt M mobile 바로가기">
	          <img class="default" src="/resources/company/images/logo_default.png" alt="kt M mobile 로고">
	          <img class="white" src="/resources/company/images/logo_white.png" alt="kt M mobile 로고">
	        </a>
	      </div>
	      <div class="menu-logo mobile">
	        <a class="ly-header__logo" href="/company/main.do" title="kt M mobile 바로가기">
	          <img class="default" src="/resources/company/images/logo_default_mo.png" alt="kt M mobile 로고">
	          <img class="white" src="/resources/company/images/logo_white_mo.png" alt="kt M mobile 로고">
	        </a>
	      </div>
	      <button class="menu-button" id="mobileMenuBtn" onclick="toggleMenu()"><i class="c-icon c-icon--gnb-button" aria-hidden="true"></i></button>
	      <button class="menu-button-all--md" onclick="toggleMenuAll()"><i class="c-icon c-icon--gnb-button" aria-hidden="true"></i><span class="c-hidden">전체 메뉴 열어보기</span></button>

	      <!-- PC용 메뉴 -->
	      <ul class="menu-pc">
	          <li class="menu-item">
	              <a href="/company/company.do" title="회사소개 페이지 이동">회사소개</a>
	              <ul class="submenu">
	                <li>
	                  <a href="/company/company.do#subGnb1" title="기업개요 페이지 이동">기업개요</a>
	                </li>
	                <li>
	                  <a href="/company/company.do#subGnb2" title="CEO Message 페이지 이동">CEO Message</a>
	                </li>
	                <li>
	                  <a href="/company/company.do#subGnb3" title="비전/핵심가치 페이지 이동">비전/핵심가치</a>
	                </li>
	                <li>
	                  <a href="/company/company.do#subGnb4" title="연혁 페이지 이동">연혁</a>
	                </li>
	                <li>
	                  <a href="/company/company.do#subGnb5" title="오시는 길 페이지 이동">오시는 길</a>
	                </li>
	              </ul>
	          </li>
	          <li class="menu-item">
                  <a href="/company/prodSvcHistory.do" title="사업소개 페이지 이동">사업소개</a>
                  <ul class="submenu">
                    <li>
                      <a href="/company/prodSvcHistory.do#subGnb1" title="사업소개 페이지 이동">사업소개</a>
                    </li>
                    <li>
                      <a href="/company/prodSvcHistory.do#subGnb2" title="상품/서비스 페이지 이동">상품/서비스</a>
                    </li>
                  </ul>
              </li>
              <li class="menu-item">
                  <a class="u-width--128" href="/company/mediaList.do" title="홍보관 페이지 이동">홍보관</a>
                  <ul class="submenu">
                    <li>
                      <a href="/company/mediaList.do#subGnb1" title="언론보도 페이지 이동">언론보도</a>
                    </li>
                    <li>
                      <a href="/company/awardDetails.do#subGnb1"  title="수상내역 페이지 이동">수상내역</a>
                    </li>
                    <li>
                      <a href="/company/noticeList.do#subGnb1"  title="공지사항 페이지 이동">공지사항</a>
                    </li>
                  </ul>
              </li>
	          <li class="menu-item">
	              <a href="/company/mgtPhilosophy.do" title="경영철학 페이지 이동">경영철학</a>
	              <ul class="submenu">
	                <li>
	                  <a href="/company/mgtPhilosophy.do#subGnb1" title="윤리경영 페이지 이동">윤리경영</a>
	                </li>
	                <li>
	                  <a href="/company/mgtPhilosophy.do#subGnb2" title="고객만족경영 페이지 이동">고객만족경영</a>
	                </li>
	                <li>
                      <a href="/company/mgtPhilosophy.do#subGnb3" title="CI 페이지 이동">CI</a>
                    </li>
	              </ul>
	          </li>
	          <li class="menu-item">
	              <a href="/company/recruit.do">인재채용</a>
	              <ul class="submenu">
	                <li>
	                  <a href="/company/recruit.do#subGnb1" title="인재상 페이지 이동">인재상</a>
	                </li>
	                <li>
	                  <a href="/company/recruit.do#subGnb2" title="인사제도 페이지 이동">인사제도</a>
	                </li>
	                <li>
	                  <a href="/company/recruit.do#subGnb3" title="복리후생 페이지 이동">복리후생</a>
	                </li>
	                <li>
	                  <a href="https://recruit.kt.com" title="지원하러 가기 페이지 새창 이동" target="_blank">지원하러 가기</a>
	                </li>
	              </ul>
	          </li>
	      </ul>
	      <ul class="menu-pc">
              <li class="menu-item">
                  <a class="ktmm-link" href="https://www.ktmmobile.com" title="다이렉트몰 페이지 새창 이동" target="_blank">
                        다이렉트몰 바로가기
                    <i class="c-icon c-icon--arrow-default" aria-hidden="true"></i>
                  </a>
              </li>
              <li class="menu-item gnb">
                 <button class="menu-button-all" onclick="toggleMenuAll()"><i class="c-icon c-icon--gnb-button" aria-hidden="true"></i><span class="c-hidden">전체 메뉴 열어보기</span></button>
              </li>
          </ul>

	      <!-- 모바일 메뉴 -->
	      <div class="menu-mobile-wrap" id="mobileMenu">
	        <div class="menu-wrap">
	          <div class="menu-logo mobile">
	            <a class="ly-header__logo" href="/company/main.do" title="kt M mobile 바로가기">
	              <img src="/resources/company/images/logo_default_mo.png" alt="kt M mobile 로고">
	            </a>
	          </div>
	          <button class="menu-button" id="mobileMenuBtn" onclick="toggleMenu()"><i class="c-icon c-icon--gnb-close" aria-hidden="true"></i></button>
	        </div>
	        <ul class="menu-mobile">
	            <li class="menu-item" tabindex="-1">
	                <a href="javascript:void(0);" onclick="toggleSubmenu(event)" >회사소개</a>
	                <ul class="submenu">
	                  <li>
	                    <a href="/company/company.do#subGnb1" title="기업개요 페이지 이동">기업개요</a>
	                  </li>
	                  <li>
	                    <a href="/company/company.do#subGnb2" title="CEO Message 페이지 이동">CEO Message</a>
	                  </li>
	                  <li>
	                    <a href="/company/company.do#subGnb3" title="비전/핵심가치 페이지 이동">비전/핵심가치</a>
	                  </li>
	                  <li>
	                    <a href="/company/company.do#subGnb4" title="연혁 페이지 이동">연혁</a>
	                  </li>
	                  <li>
	                    <a href="/company/company.do#subGnb5" title="오시는 길 페이지 이동">오시는 길</a>
	                  </li>
	                </ul>
	            </li>
	            <li class="menu-item">
                    <a href="javascript:void(0);" onclick="toggleSubmenu(event)" >사업소개</a>
                    <ul class="submenu">
                      <li>
                        <a href="/company/prodSvcHistory.do#subGnb1" title="사업소개 페이지 이동">사업소개</a>
                      </li>
                      <li>
                        <a href="/company/prodSvcHistory.do#subGnb2" title="상품/서비스 페이지 이동" >상품/서비스</a>
                      </li>
                    </ul>
                </li>
	            <li class="menu-item">
	                <a href="javascript:void(0);" onclick="toggleSubmenu(event)" >홍보관</a>
	                <ul class="submenu">
	                  <li>
	                    <a href="/company/mediaList.do#subGnb1" title="언론보도 페이지 이동">언론보도</a>
	                  </li>
	                  <li>
	                    <a href="/company/awardDetails.do#subGnb1" title="수상내역 페이지 이동" >수상내역</a>
	                  </li>
	                  <li>
	                    <a href="/company/noticeList.do#subGnb1" title="공지사항 페이지 이동" >공지사항</a>
	                  </li>
	                </ul>
	            </li>
	            <li class="menu-item">
                    <a href="javascript:void(0);" onclick="toggleSubmenu(event)" >경영철학</a>
                    <ul class="submenu">
                      <li>
                        <a href="/company/mgtPhilosophy.do#subGnb1" title="윤리경영 페이지 이동">윤리경영</a>
                      </li>
                      <li>
                        <a href="/company/mgtPhilosophy.do#subGnb2" title="고객만족경영 페이지 이동">고객만족경영</a>
                      </li>
                      <li>
                        <a href="/company/mgtPhilosophy.do#subGnb3" title="CI 페이지 이동">CI</a>
                      </li>
                    </ul>
                </li>
	            <li class="menu-item">
	                <a href="javascript:void(0);" onclick="toggleSubmenu(event)" >인재채용</a>
	                <ul class="submenu">
	                  <li>
	                    <a href="/company/recruit.do#subGnb1" title="인재상 페이지 이동">인재상</a>
	                  </li>
	                  <li>
	                    <a href="/company/recruit.do#subGnb2" title="인사제도 페이지 이동">인사제도</a>
	                  </li>
	                  <li>
	                    <a href="/company/recruit.do#subGnb3" title="복리후생 페이지 이동">복리후생</a>
	                  </li>
	                  <li>
	                    <a href="https://recruit.kt.com" title="지원하러 가기 페이지 새창 이동" target="_blank">지원하러 가기</a>
	                  </li>
	                </ul>
	            </li>
	            <li class="menu-item">
	                <a href="https://www.ktmmobile.com" title="다이렉트몰 페이지 새창 이동" target="_blank">다이렉트몰 바로가기</a>
	            </li>
	          </ul>
	          <div class="menu-mobile-bg"></div>
	      </div>
	  </div>
	</nav>

	<!-- PC 전체 메뉴 -->
	<div id="gnbMenuAll">
	    <button class="menu-button-all" onclick="toggleMenuAll()"><i class="c-icon c-icon--gnb-all-close" aria-hidden="true"></i></button>
	    <div class="menu-wrap">
	      <div class="menu-all-logo">
	        <a class="ly-header__logo" href="/company/main.do" title="kt M mobile 바로가기">
	          <img src="/resources/company/images/logo_menu_all.png" alt="kt M mobile 로고">
	        </a>
	      </div>
	      <ul class="menu-pc-all">
	          <li class="menu-item">
	              <a href="/company/company.do" title="회사소개 페이지 이동">회사소개</a>
	              <ul class="submenu">
	                  <li>
	                    <a href="/company/company.do#subGnb1" title="기업개요 페이지 이동">기업개요</a>
	                  </li>
	                  <li>
	                    <a href="/company/company.do#subGnb2" title="CEO Message 페이지 이동">CEO Message</a>
	                  </li>
	                  <li>
	                    <a href="/company/company.do#subGnb3" title="비전/핵심가치 페이지 이동">비전/핵심가치</a>
	                  </li>
	                  <li>
	                    <a href="/company/company.do#subGnb4" title="연혁 페이지 이동">연혁</a>
	                  </li>
	                  <li>
	                    <a href="/company/company.do#subGnb5" title="오시는 길 페이지 이동">오시는 길</a>
	                  </li>
	              </ul>
	          </li>
	          <li class="menu-item">
                  <a href="/company/prodSvcHistory.do" title="사업소개 페이지 이동">사업소개</a>
                  <ul class="submenu">
                      <li>
                        <a href="/company/prodSvcHistory.do#subGnb1" title="사업소개 페이지 이동">사업소개</a>
                      </li>
                      <li>
                        <a href="/company/prodSvcHistory.do#subGnb2" title="상품/서비스 페이지 이동">상품/서비스</a>
                      </li>
                  </ul>
              </li>
	          <li class="menu-item">
	              <a href="/company/mediaList.do" title="홍보관 페이지 이동">홍보관</a>
	              <ul class="submenu">
	                  <li>
	                    <a href="/company/mediaList.do#subGnb1" title="언론보도 페이지 이동">언론보도</a>
	                  </li>
	                  <li>
	                    <a href="/company/awardDetails.do#subGnb1" title="수상내역 페이지 이동">수상내역</a>
	                  </li>
	                  <li>
	                    <a href="/company/noticeList.do#subGnb1" title="공지사항 페이지 이동">공지사항</a>
	                  </li>
	              </ul>
	          </li>
	          <li class="menu-item">
                  <a href="/company/mgtPhilosophy.do" title="경영철학 페이지 이동">경영철학</a>
                  <ul class="submenu">
                      <li>
                        <a href="/company/mgtPhilosophy.do#subGnb1" title="윤리경영 페이지 이동">윤리경영</a>
                      </li>
                      <li>
                        <a href="/company/mgtPhilosophy.do#subGnb2" title="고객만족경영 페이지 이동">고객만족경영</a>
                      </li>
                      <li>
                        <a href="/company/mgtPhilosophy.do#subGnb3" title="CI 페이지 이동">CI</a>
                      </li>
                  </ul>
              </li>
	          <li class="menu-item">
	              <a href="/company/recruit.do" title="인재채용 페이지 이동">인재채용</a>
	              <ul class="submenu">
	                <li>
	                  <a href="/company/recruit.do#subGnb1" title="인재상 페이지 이동">인재상</a>
	                </li>
	                <li>
	                  <a href="/company/recruit.do#subGnb2" title="인사제도 페이지 이동">인사제도</a>
	                </li>
	                <li>
	                  <a href="/company/recruit.do#subGnb3" title="복리후생 페이지 이동">복리후생</a>
	                </li>
	                <li>
	                  <a href="https://recruit.kt.com" title="지원하러 가기 페이지 새창 이동" target="_blank">지원하러 가기</a>
	                </li>
	              </ul>
	          </li>
	          <li class="menu-item">
	              <a href="https://www.ktmmobile.com" title="다이렉트몰 페이지 새창 이동" target="_blank">	다이렉트몰 바로가기</a>
	              <ul class="submenu">
	                <li>
	                  <a href="https://www.ktmmobile.com" title="다이렉트몰 페이지 새창 이동" target="_blank">다이렉트몰 바로가기</a>
	                </li>
	              </ul>
	          </li>
	      </ul>
	    </div>
	</div>
</header>