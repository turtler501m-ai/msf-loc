<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<t:insertDefinition name="layoutNoGnb">

    <t:putAttribute name="titleAttr">작업안내</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<input type="hidden" id="prmtId" name="prmtId" value="${prmtId}" />
		<input type="hidden" id="amountLimit" name="amountLimit" value="${promotionDto.amountLimit}">
		<div id="content">
			<div class="content_main">
			    <div class="notice_wrap">
					<div class="notice_new">					
						<div class="card_section" style="display: block;">
							<div class="notice_tit_pc">
								<img class="notice_img" src="/resources/images/portal/notice/notice_tit_pc.jpg" class="notice_img" alt="공지사항 이미지 ">				
							</div>			
<!--
 							<div class="notice_tit_mo">
								<img class="notice_img" src="/resources/images/portal/notice/notice_tit_mo.jpg" class="notice_img" alt="공지사항 이미지 ">				
							</div>																							
 -->							<div class="notice_box align_left">
								<div class="notice_txt_wrap">
									<span class="notice_tit_img">작업일시</span>
									<p class="box_txt">2022년 4월 8일(금) 20:00 ~ 4월 9일(토) 12:00</p>
									<ul>
										<li>※ 작업시간은 진행 상황에 따라 변경될 수 있습니다.</li>
										<li>※ 분실정지 등 급한 문의사항은 고객센터(1899-5000/09시~12시)를 이용바랍니다.</li>
									</ul>									
								</div>							
							</div>
							<p class="notice_txt">홈페이지 이용에 불편을 드려 죄송합니다.<br/>감사합니다.</p>	
						</div>						
					</div>
				</div>
			</div>
		</div>
	</t:putAttribute>	

<%-- 
	<t:putAttribute name="contentAttr">
    	<div class="ly-content" id="main-content">
			<div class="c-row c-row--lg">
				<div class="error u-ta-center">
					<strong class="c-heading c-heading--fs24 u-block u-mt--48">
						${nmcp:convertHtmlchars(alertTitle)}
					</strong>
					<p class="c-text c-text--fs17 u-co-gray u-mt--16">
						${nmcp:convertHtmlchars(alertMsg)}
					</p>
				</div>
			</div>
		</div>
	</t:putAttribute>
 --%>
 
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/validator.js"></script>

		<style>
			.countdown {
				color: #e31f26;
				margin-top: 10px
			}
			.countdown span {
				color: #000
			}
			.timeover {
				color: #e31f26;
				margin-top: 10px
			}
			#footer {
				display: none;
			}			
			html, body {
			    min-width: auto !important;			    
			}							
			/* 공지사항 뉴*/
			.content_main {width:100%;position:relative}				
			.notice_new {
				display: flex;
				justify-content: center;
				align-items: center;
				height: 100vh;	   
			}
			.notice_new .card_section {width:100%; background-color:#fff; color:#222222; font-size:0.9231rem; line-height:1.231rem; margin: 0 auto;text-align: center;border-radius: 0;box-shadow: none;}
			.notice_new .notice_txt {color:#6a6a6a; font-size:0.85rem; font-weight: bold; padding:1.3rem 1.1rem; margin: 0; letter-spacing: -0.05rem; line-height: 1.6;}
			.notice_new .notice_box {background-color: #f5f5f5; padding: 1.1rem 1.1rem;display: flex; justify-content: center;}
			.notice_new .notice_box .box_txt {color:#222222; font-size:0.9rem; font-weight:bold; margin: 0; letter-spacing: -0.075em;line-height: 1.6;}
			.notice_tit_img {
			    display: inline-block;
			    width: 4.5rem;
			    height: 1.6rem;
			    margin-right: 0.75rem;
			    border-radius: 25px;
			    background-color: #535c97;
			    color: #fff;
			    font-size: 0.8rem;
			    text-align: center;
			    line-height: 1.6rem;
			    position: absolute;
			    left: -5rem;
			    top: 0;
			}
			.notice_wrap {
					background-color: #fff;
				}
			.notice_new .notice_box ul {
				margin: 0;
				margin-top:0.25rem;
			    padding: 0;
			}
			.notice_new .notice_box ul li {
			    list-style: none;
			    color:#888888;  
			    font-size: 0.7rem;   
			    line-height: 1.0rem;
			    letter-spacing: -0.05em; 
			}
			.notice_txt_wrap {
				margin-left: 5rem;
				position: relative;
    			text-align: left;
			}
			.notice_img {
				width: 100%;
				image-rendering: -webkit-optimize-contrast;
				transform: translateZ(0);
				backface-visibility: hidden;
			}
			.notice_tit_pc {
				display: block;
			}
			.notice_tit_mo {
				display: none;
			}			
			@media(min-width:769px){	
				.notice_tit_pc {
					display: block;			
					padding-top: 4.375rem;				
				}
				.notice_tit_mo {
					display: none;
				}				
				.notice_wrap {
					background-color: #efefef;
				}				
				.notice_new .card_section {
				    width: 940px;
				    height:873px;	    
				    border-radius: 25px;
				    box-shadow: 0 0 20px 5px rgb(0 0 0 / 20%);
				}
				.notice_new .notice_box {
					height: 145px;
				}
				.notice_tit_img	 {
				    display: inline-block;
				    width: 120px;
				    height: 40px;
				    margin-right: 0.75rem;
				    border-radius: 25px;
				    background-color: #535c97;
				    color: #fff;
				    font-size: 1.25rem;
				    text-align: center;
				    line-height: 2;
				    position: absolute;
				    left: -135px;
				    top: 0;
				}
				.notice_new .notice_box ul {
					margin: 0;
				    margin-top: 0.75rem;
				    padding: 0;
				}
				.notice_new .notice_box ul li {
			        list-style: none;
				    color: #888888;
				    font-size: 1rem;
				    line-height: 1.3rem;
				    letter-spacing: -0.075em;
				}
				.notice_txt_wrap {
					margin-left: 5rem;
					position: relative;
    				text-align: left;
				}
				.notice_txt_wrap {
					margin-left: 10rem;
					margin-top: 0.7rem;				
				}	
				.notice_new .card_section h2 br {
					display:none !important;
				}			
				.notice_new .notice_txt {
					font-size:1.25rem;
					font-weight: bold;
				    letter-spacing: -0.075em;
				}
				.notice_new .notice_box .box_txt {
					font-size:1.438rem;
				}
			}
		</style>

		<script language="javascript">
			function noEvent() {
				if (event.keyCode == 116) {
					event.keyCode = 2;
					return false;
				} else if (event.ctrlKey
						&& (event.keyCode == 78 || event.keyCode == 82)) {
					return false;
				}
			}
			document.onkeydown = noEvent;
		</script>
	</t:putAttribute>
</t:insertDefinition>
