<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="/resources/js/wire/wireDefaultFooter.js"></script>
<script type="text/javascript">
function visit(){
        var url = $("#family_site option:selected").val();
        if(url != 'FAMILYSITE') {
        	window.open().location.href = url;
        }else{
        	return false;
        }
}
</script>

<!-- 간편가입신청영역 -->
<div class="wireJoinBar MoveBar hidden">
	<h2>가입상담</h2>
	<div class="JoinBarInner">
		<p>
			<strong>간편 가입문의 & 상담신청</strong>
			<span>인터넷, TV 1899-0851 /	CCTV 1899-4059</span>
		</p>
		<div class="input-area">
			<div class="left">
				<div class="JoinBarTxt">
					<input type="text" class="name" id="counselNm" name="counselNm" placeholder="성명" maxlength="15" title="이름 입력칸" value="">
					<input type="text" class="phone" id="counselCtn"	name="counselCtn" placeholder="연락처( - 없이 입력)" value="" class="onlyNum" maxlength="12">
					<input type="text" class="txt" id="counselMemo" name="counselMemo" placeholder="요청사항 기재  ex)오후 2시 통화 가능" value="" maxlength="50">
				</div>
				<div class="JoinBarCheck">
					<input type="checkbox" id="acceptTxt" name="acceptTxt"><label for="acceptTxt">&nbsp;&nbsp; 상품 상담을 위하여 상기 개인정보 수집에 동의합니다. (상담완료 시 24시간 이내 파기)</label>
					<!-- <button id="btnDetail" class="detailView" title="새창-자세히보기">자세히보기</button> -->
				</div>
			</div>
			<div class="right">
				<a href="javascript:;" id="counselBtn" class="btnJoinbar">상담신청</a>
			</div>
		</div>
	</div>
</div>
<!-- 간편가입신청영역 끝 -->

<div id="wireFooter" style="margin-bottom:100px;">
		<div class="foot_link">
			<div class="link_grp">
				<a href="/company/main.do" target="_blank" title="회사소개 새창열림">주식회사 케이티엠모바일</a>
				<span>|</span>
				<a href="/wire/privacyView.do" class="point_ft" target="_blank" title="개인정보처리방침 새창열림">개인정보처리방침</a>
			</div>

			<span class="comm_sel">
				<label for="family_site" class="hidden">FAMILY SITE</label>
				<select id="family_site" title="패밀리 사이트 바로가기" name="family_site" onchange="visit()">
					<option value="FAMILYSITE">FAMILY SITE</option>
					<option value="http://www.kt.com" title="kt 새창">kt</option>
                	<option value="http://kt-sports.co.kr" title="kt sports 새창">kt sports</option>
					<option value="http://www.ktcommerce.co.kr" title="ktcommerce 새창">kt commerce</option>
					<option value="http://www.ktestate.com" title="ktestate 새창">kt estate</option>
					<option value="http://service.vp.co.kr" title="VP 새창">VP</option>
					<option value="http://www.smartro.co.kr" title="Smartro 새창">Smartro</option>
					<option value="https://www.bccard.com" title="BC Card 새창">BC Card</option>
					<option value="https://tv.skylife.co.kr" title="skyLifeTV 새창">skyLifeTV</option>
					<option value="http://www.kthcorp.com" title="kt alpha 새창">kt alpha</option>
					<option value="http://www.ktinnoedu.com" title="ktinnoedu 새창">kt innoedu</option>
					<option value="http://www.nasmedia.co.kr" title="nasmedia 새창">nasmedia</option>
					<option value="http://www.ktservice.net" title="ktservice 남부 새창">kt service 남부</option>
					<option value="http://www.ktservice.co.kr" title="ktservice 북부 새창">kt service 북부</option>
					<option value="http://www.ktds.com" title="ktds 새창">kt ds</option>
					<option value="http://www.telecop.co.kr" title="kttelecop 새창">kt telecop</option>
					<option value="http://www.ktpowertel.co.kr" title="ktpowertel 새창">kt powertel</option>
					<option value="http://www.ktlinkus.co.kr" title="ktlinkus 새창">kt linkus</option>
					<option value="http://www.ktsubmarine.co.kr" title="ktsubmarine 새창">kt submarine</option>
					<option value="http://www.ktsat.net" title="ktsat 새창">kt sat</option>
					<option value="http://www.ktcs.co.kr" title="ktcs 새창">kt cs</option>
					<option value="http://www.ktis.co.kr" title="ktis 새창">kt is</option>
					<option value="http://www.ktmns.com" title="ktmns 새창">kt m&amp;s</option>
					<option value="https://www.ktengcore.com/main.jsp" title="engineering 새창">kt engineering</option>
					<option value="http://www.ktnexr.com" title="ktNexR 새창">kt NexR</option>
					<option value="http://www.initech.com/www/html/index.html" title="initech 새창">initech</option>
					<option value="http://www.skylife.co.kr/index.html" title="kt skylife 새창">kt skylife</option>
				</select>
			</span>
		</div>

		<div class="comp_info">

			<div class="foot_wrap">

				<div class="info_txt">
					<p>주식회사 케이티엠모바일 대표이사 : 구강본 | 06193 서울특별시 강남구 테헤란로 422 KT 선릉타워 12층 고객센터: 1899-0851 평일: 09시~18시까지</p>
					<p>사업자등록번호: 133-81-43410 | 통신판매업신고 : 2015-서울강남-01576 |<a href="http://www.ftc.go.kr/bizCommPop.do?wrkr_no=1338143410" target="_blank" title="새창열림">사업자 정보 확인</a></p>
					<p>Copyright © kt M mobile. All rights reserved.</p>
				</div>

				<div class="logo_grp">
					<div class="logo_grp_mark">
						<!-- <a href="http://www.i-award.or.kr/Web/Assess/FinalCandidateView.aspx?REG_SEQNO=6144" target="_blank" title="웹어워드 새창열림">
							<span class="img_fgr"><img src="/resources/images/wire/icon_certification_01.png" alt="웹어워드코리아 2016  통신서비스분야 대상"></span>
							<span>웹어워드코리아 2016  통신서비스분야 대상</span>
						</a>
						<br>
	     				<a href="http://www.kwacc.or.kr/WACertificate/WAMark" class="margin_right_15" target="_blank" title="kwacc 새창열림">
	     					<span class="img_fgr"><img src="/resources/images/wire/icon_certification_02.png" alt="미래창조과학부 웹접근성 인증"></span>
	     					<span>미래창조과학부 웹접근성 인증</span>
	     				</a>
	     				<br>
						<a href="http://ks-cqi.ksa.or.kr/ks-cqi/2435/subview.do" target="_blank" title="콜센터 품질지수 새창열림">
							<span class="img_fgr"><img src="/resources/images/wire/icon_certification_03.png" alt=""></span>
							<span>콜센터 품질지수 MVNO부문 1위</span>
						</a>
						<br> -->
						<a href="https://www.ktmmobile.com/cs/csNoticeView.do?BoardSeq=660">
							<span class="img_fgr"><img src="/resources/images/wire/icon_certification_04_re.png" alt="정보보호 관리체계 인증"></span>
							<span style="display:inline-block; width:80px;line-height: 15px;">정보보호<br>관리체계 인증</span>
						</a>
					</div>
				</div>

				<div class="foot_logo">
					<img src="/resources/images/wire/ci_footer.png" alt="모바일 실용주의 kt M mobile 다이렉트">
				</div>

			</div>
		</div>
	</div>

</div>