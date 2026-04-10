<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault"> 
	<t:putAttribute name="scriptHeaderAttr">
	</t:putAttribute>
    <t:putAttribute name="contentAttr">
    	<div class="kttmobileinfo">
    		<div class="info">
    			<img src="/resources/images/front/mobile/ktmmobileinfomobile.png">
    		</div>
    		<div class="youtube">
    			<div class="title">
    			가격파괴 유심요금제
    			</div>
    			<div class="script">
    			통신비를 알뜰하게 쓰고 싶다면 통신비는 내리고 실용은 챙기는 케이티 엠모바일로 시작하세요!
    			</div>
    			<iframe title="[케이티 엠모바일] 가격파괴 유심요금제 광고영상" width="320" height="180" src="https://www.youtube.com/embed/-Qob6mE1Nno?rel=0" frameborder="0" allowfullscreen></iframe>
           		<a target="_blank" href="https://m.youtube.com/channel/UCSvOg4rSu395lPz6uMqBekw">
              <div class="youtubelink">
           			<img src="/resources/images/front/mobile/youtubeicon.png" alt="youtube_icon" class="youtube_icon">
           			<span class="text">
           			공식 유투브 채널에서 더 많은<br>영상을 만나보세요!<br>
           			</span>
           			<span class="arrow">
           			  <img src="/resources/images/front/mobile/youtubearrow.png" alt="바로가기">
           			</span>
           		</div>
              </a>
           		
    		
    		</div>
    		<div class="sns">
           		<img src="/resources/images/front/mobile/ktmmobile_sns.png" alt="sns_bg">
           		<div class="icon">
           			<a target="_blank" href="https://www.facebook.com/ktmmobile" title="새창"><img src="/resources/images/front/mobile/iconFacebook.png" alt="facebook"></a>
           			<a target="_blank" href="https://twitter.com/ktmmobile " title="새창"><img src="/resources/images/front/mobile/iconTwitter.png" alt="twitter"></a>
           			<a target="_blank" href="https://story.kakao.com/ch/kt_mmobile" title="새창"><img src="/resources/images/front/mobile/iconKakao.png" alt="kakaoStory"></a>
           		</div>
           	</div>
    	</div>
    	
	</t:putAttribute>
</t:insertDefinition>
