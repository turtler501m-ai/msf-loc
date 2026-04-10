<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>



<!-- 대리점 푸터 -->
<footer class="ly-footer" >
  	<div class="c-box-footer">
    	<div class="c-footer-company">
      		<div class="c-box-inner">
        		<div class="left-box">
          			<img class="logo-white" src="/resources/images/portal/content/logo_white.png" alt="kt M mobile 로고">
           			<ul class="company-info">
		              	<li><em>주식회사 케이티엠모바일 대표이사 : 구강본</em><em>주소 : 06193 서울특별시 강남구 테헤란로 422 KT 선릉타워 12층</em><em>사업자등록번호 : 133-81-43410</em>통신판매업신고 : 2015-서울강남-01576 <a href="https://www.ftc.go.kr/bizCommPop.do?wrkr_no=1338143410" target="_blank" title="사업자 정보 확인 바로가기 새창열림">&nbsp;사업자 정보 확인</a></li>
		              	<li><span>고객센터 : 1899-5000 (무료 kt M모바일 114)</span><span>운영시간 : 09~12시 / 13~18시</span>토요일, 일요일, 공휴일은 분실, 정지신고 및 통화품질 관련 상담만 가능</li>
		              	<li>Copyright c kt M mobile. All rights reserved.</li>
		              	<li class="outform"><em>대리점명 : <span>${agentNM}</span></em>주소 : <span>${agentAddr}</span></li>
           			</ul>
        		</div>
      		</div>
    	</div>
  	</div>
</footer>