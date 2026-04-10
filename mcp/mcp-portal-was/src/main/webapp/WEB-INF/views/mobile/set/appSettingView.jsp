<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<t:insertDefinition name="mlayoutDefault">

	<t:putAttribute name="contentAttr">

    <div class="ly-content" id="main-content">

    <%-- ${appUuid} /${userId} / ${checkUserType} / ${appVersion} --%>

    <input type="hidden" id="sendYn" value="${appInfoDTO.sendYn}"/>
    <input type="hidden" id="bioLoginYn" value="${appInfoDTO.bioLoginYn}"/>
    <input type="hidden" id="simpleLoginYn" value="${appInfoDTO.simpleLoginYn}"/>

      <div class="ly-page-sticky">
        <h2 class="ly-page__head">설정<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <div class="setting-wrap">
        <h3 class="c-heading c-heading--type5 u-mt--24">로그인 설정</h3>
        <dl class="setting-item">
          <dt>로그인 정보</dt>
          	<dd>
<c:if test="${empty userId}">
            <a href="/m/loginForm.do">로그인 해주세요<i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i></a>
</c:if>
<c:if test="${not empty userId}">
			${nmcp:getMaskedId(USER_SESSION.userId)}
</c:if>
			</dd>
        </dl>
        <dl class="setting-item">
<c:if test="${empty userId}">
          <dt>간편비밀번호 설정</dt>
</c:if>
<c:if test="${not empty userId}">
          <dt>간편비밀번호 설정
	<c:if test="${appInfoDTO.simpleLoginYn eq 'Y'}">
          <button id="resetSimpleBtn" class="c-button c-button--sm c-button--white u-ml--10" onclick="setAppSetCheck('simp');">재설정</button>
    </c:if>
          </dt>
</c:if>
          <dd>
<c:if test="${empty userId}">
            <input class="c-checkbox c-checkbox--switch" id="swtA1" type="checkbox" name="swtA" disabled>
</c:if>
<c:if test="${not empty userId}">
	<c:if test="${appInfoDTO.simpleLoginYn eq 'N'}">
            <input class="c-checkbox c-checkbox--switch" id="swtA1" type="checkbox" name="swtA" onclick="simpleLoginYnChg();" >
	</c:if>
	<c:if test="${appInfoDTO.simpleLoginYn eq 'Y'}">
            <input class="c-checkbox c-checkbox--switch" id="swtA1" type="checkbox" name="swtA" onclick="simpleLoginYnChg();" checked="checked">
	</c:if>
</c:if>
            <label class="c-label" for="swtA1">
              <span class="c-hidden">설정</span>
            </label>
          </dd>
        </dl>
        <dl class="setting-item">
          <dt>생체인증 설정</dt>
          <dd>
<c:if test="${empty userId}">
            <input class="c-checkbox c-checkbox--switch" id="swtB1" type="checkbox" name="swtB" disabled>
</c:if>
<c:if test="${not empty userId}">
	<c:if test="${appInfoDTO.bioLoginYn eq 'N'}">
            <input class="c-checkbox c-checkbox--switch" id="swtB1" type="checkbox" name="swtB" onclick="bioLoginYnChg();" >
	</c:if>
	<c:if test="${appInfoDTO.bioLoginYn eq 'Y'}">
            <input class="c-checkbox c-checkbox--switch" id="swtB1" type="checkbox" name="swtB" onclick="bioLoginYnChg();" checked="checked">
	</c:if>
</c:if>
            <label class="c-label" for="swtB1"></label>
          </dd>
        </dl>
      </div>
      <hr class="c-hr c-hr--type1 c-expand u-mt--20 u-mb--20">
      <div class="setting-wrap">
        <h3 class="c-heading c-heading--type5">사용량 조회 설정</h3>
        <dl class="setting-item">
          <dt>대표번호 설정</dt>
<c:if test="${empty userId}">
          <dd>-</dd>
</c:if>
<c:if test="${not empty userId}">
          <dd>
	<c:if test="${checkUserType eq 'Y'}">
		<c:forEach items="${rtnCntrList}" var="item" varStatus="vs">
			<c:if test = "${item.svcCntrNo eq searchVO.ncn}">
	            <a href="#n" data-dialog-trigger="#bsPhoneNumA">${item.cntrMobileNo }<i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i></a>
			</c:if>
		</c:forEach>
	</c:if>
	<c:if test="${checkUserType eq 'N'}">
            <button class="c-button c-button--sm c-button--white" onclick='location.href = "/m/mypage/multiPhoneLine.do"'>정회원 인증하기</button>
	</c:if>
          </dd>
</c:if>
        </dl>
      </div>
      <hr class="c-hr c-hr--type1 c-expand u-mt--20 u-mb--20">
      <div class="setting-wrap">
        <h3 class="c-heading c-heading--type5">위젯 설정</h3>
        <dl class="setting-item">
          <dt>데이터 갱신주기 설정</dt>
<c:if test="${empty userId}">
          <dd>-</dd>
</c:if>
<c:if test="${not empty userId}">
          <dd>
	<c:if test="${checkUserType eq 'Y'}">
		<c:forEach items="${wdgtCallCyclList}" var="item" varStatus="vs">
			<c:if test = "${item.dtlCd eq appInfoDTO.wdgtUseQntyCallCyclCd}">
	            <a href="#n" data-dialog-trigger="#bsSampleB">${item.dtlCdNm}<i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i></a>
		    </c:if>
		</c:forEach>
	</c:if>
	<c:if test="${checkUserType eq 'N'}">
            <button class="c-button c-button--sm c-button--white" onclick='location.href = "/m/mypage/multiPhoneLine.do"'>정회원 인증하기</button>
	</c:if>
          </dd>
</c:if>
        </dl>
      </div>
      <hr class="c-hr c-hr--type1 c-expand u-mt--20 u-mb--20">
      <div class="setting-wrap">
        <h3 class="c-heading c-heading--type5">알림 설정</h3>
        <dl class="setting-item">
          <dt>PUSH 알림 수신</dt>
          <dd>
<c:if test="${appInfoDTO.sendYn eq 'N'}">
            <input class="c-checkbox c-checkbox--switch" id="swtC1" type="checkbox" name="swtC" onclick="sendYnChg();">
</c:if>
<c:if test="${appInfoDTO.sendYn eq 'Y'}">
            <input class="c-checkbox c-checkbox--switch" id="swtC1" type="checkbox" name="swtC"  onclick="sendYnChg();" checked="checked">
</c:if>
            <label class="c-label" for="swtC1"></label>
          </dd>
        </dl>
      </div>
      <hr class="c-hr c-hr--type1 c-expand u-mt--20 u-mb--20">
      <div class="setting-wrap">
        <h3 class="c-heading c-heading--type5">버전 정보</h3>
        <dl class="setting-item">
          <dt>현재 버전(${appInfoDTO.version })
<%-- appcontroller.java 와 같이 반영
<c:if test="${appDown ne ''}">
          <button class="c-button c-button--sm c-button--white u-ml--10" onclick="appOutSideOpen('${appDown}');">업데이트</button>
</c:if>
 --%>
<c:if test="${appInfoDTO.version ne appVersion}">
          <button class="c-button c-button--sm c-button--white u-ml--10" onclick="appOutSideOpen('${appDown}');">업데이트</button>
</c:if>
          </dt>
          <dd>최신버전(${appVersion })</dd>
        </dl>
      </div>
    </div>

  <div class="c-modal c-modal--bs" id="bsPhoneNumA" role="dialog" tabindex="-1" aria-describedby="#bsPhoneNumADesc" aria-labelledby="#bsPhoneNumAtitle">
    <div class="c-modal__dialog" role="document">
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="bsPhoneNumAtitle">대표번호 설정</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">바텀시트 닫기</span>
          </button>
        </div>
        <div class="c-modal__body" id="bsPhoneNumADesc">
          <div class="item-list">
<c:forEach items="${rtnCntrList}" var="item" varStatus="vs">
	<c:if test = "${item.svcCntrNo eq searchVO.ncn}">
            <input class="c-radio c-radio--list" id="radPhoneNum${vs.count}" type="radio" name="" checked>
            <label class="c-label" for="radPhoneNum${vs.count}">${item.cntrMobileNo }</label>
	</c:if>
	<c:if test = "${item.svcCntrNo ne searchVO.ncn}">
            <input class="c-radio c-radio--list" id="radPhoneNum${vs.count}" type="radio" name="radPhoneNum" value="${item.svcCntrNo }">
            <label class="c-label" for="radPhoneNum${vs.count}">${item.cntrMobileNo }</label>
	</c:if>
</c:forEach>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="c-modal c-modal--bs" id="bsSampleB" role="dialog" tabindex="-1" aria-describedby="#bsSampleBDesc" aria-labelledby="#bsSampleBtitle">
    <div class="c-modal__dialog" role="document">
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="bsSampleBtitle">데이터 갱신주기 설정</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">바텀시트 닫기</span>
          </button>
        </div>
        <div class="c-modal__body" id="bsSampleBDesc">
          <div class="payment-list">
<c:forEach items="${wdgtCallCyclList}" var="item" varStatus="vs">
	<c:if test = "${item.dtlCd eq appInfoDTO.wdgtUseQntyCallCyclCd}">
            <input class="c-radio c-radio--list" id="radInterval${vs.count}" type="radio" name="" checked>
            <label class="c-label" for="radInterval${vs.count}">${item.dtlCdNm}</label>
    </c:if>
	<c:if test = "${item.dtlCd ne appInfoDTO.wdgtUseQntyCallCyclCd}">
            <input class="c-radio c-radio--list" id="radInterval${vs.count}" type="radio" name="radInterval" value="${item.dtlCd}" >
            <label class="c-label" for="radInterval${vs.count}">${item.dtlCdNm}</label>
    </c:if>
</c:forEach>
          </div>
        </div>
      </div>
    </div>
  </div>

	</t:putAttribute>

  	<t:putAttribute name="scriptHeaderAttr">
	<script>
	function sendYnChg() {
        event.preventDefault();
        new KTM.Confirm('PUSH 알림 수신을 변경하시겠습니까?', function(closeHandler) {
			this.close();

			if($("#sendYn").val() == 'N') {
				sendYn = 'Y';
			} else {
				sendYn = 'N';
			}
          	sendYnSettingResult(sendYn);
        });
	}

	function bioLoginYnChg(){
		event.preventDefault();
        new KTM.Confirm('생체인증 설정을 변경하시겠습니까?', function(closeHandler) {
    		if($("#bioLoginYn").val() == 'N') {
    			bioLoginYn = 'Y';
    			setAppSetCheck('bio');
    		} else {
    			bioLoginYn = '{"rsltCd":"N","rsltMsg":""}';
    			bioSettingResult(bioLoginYn);
    		}
        });
	}

	function simpleLoginYnChg(){
		event.preventDefault();
        new KTM.Confirm('간편비밀번호 설정을 변경하시겠습니까?', function(closeHandler) {
			if($("#simpleLoginYn").val() == 'N') {
				simpleLoginYn = 'Y';
				setAppSetCheck('simp');
			} else {
				simpleLoginYn = '{"rsltCd":"N","rsltMsg":""}';
				simpleSettingResult(simpleLoginYn);
			}
        });
	}

	$('input[name=radPhoneNum]').on('change', function (){

		var resetData = $(this).val();
		//alert("PhoneNum:"+resetData);

		if(resetData != null && resetData != undefined) {

			var varData = ajaxCommon.getSerializedData({
				ncn : resetData
		    });

		    ajaxCommon.getItem({
		        id:'setwdgtCallCyclSet'
		        ,cache:false
		        ,url:'/m/set/updateAppRepLineAjax.do'
		        ,data: varData
		        ,dataType:"json"
		        ,async:false
		    }
		    ,function(data){
		        if(data.CODE == '0000'){
					MCP.alert("설정되었습니다.", function (){
						location.href="/m/set/appSettingView.do";
					});
				} else {
					MCP.alert(data.ERRORDESC);
				}
		    });
		}
	})

	//위젯 설정주기 변경
	$('input[name=radInterval]').on('change', function (){

		var resetData = $(this).val();
		//alert("Interval:"+resetData);

		if(resetData != null && resetData != undefined) {

			var varData = ajaxCommon.getSerializedData({
				wdgtUseQntyCallCyclCd : resetData
		    });

		    ajaxCommon.getItem({
		        id:'setwdgtCallCyclSet'
		        ,cache:false
		        ,url:'/m/set/updateAppSetAjax.do'
		        ,data: varData
		        ,dataType:"json"
		        ,async:false
		    }
		    ,function(data){
		        if(data.CODE == '0000'){
					var senddata = '{"wdgtUseQntyCallCyclCd":"'+resetData+'"}';
					if($('#phoneOs').val() == 'I'){
						window.webkit.messageHandlers.wdgtCallSet.postMessage(senddata);
					}else if($('#phoneOs').val() == 'A'){
						window.ktmmobile.wdgtCallSet(senddata);
					}
					MCP.alert("설정되었습니다.", function (){
						location.href="/m/set/appSettingView.do";
					});
				} else {
					MCP.alert(data.ERRORDESC);
				}
		    });
		}
	})

	</script>
  	</t:putAttribute>

</t:insertDefinition>
