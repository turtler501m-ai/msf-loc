<template>
  <!-- 스마트신청서 앱 설치: 헤더,하단메뉴 없는 레이아웃입니다 -->
  <div class="app-download-wrap">
    <div class="app-download-header">
      <img src="@/assets/images/logo.svg" alt="kt m mobile 로고" class="logo" />
      <strong class="app-download-name"><span>SMART</span>신청서</strong>
    </div>
    <h4 class="app-download-tit">앱(App) 다운로드</h4>
    <div class="app-download-layout">
      <MsfBox variant="outline" align="center" class="app-download-box">
        <div class="app-icon">
          <p class="app-icon-img">
            <img src="@/assets/images/appIpad.svg" alt="ipad 아이콘" />
          </p>
          <span class="app-icon-tit">ipad</span>
        </div>
        <MsfButton variant="primary">ipad 다운로드</MsfButton>
      </MsfBox>
      <MsfBox variant="outline" align="center" class="app-download-box">
        <div class="app-icon">
          <p class="app-icon-img">
            <img src="@/assets/images/appAndroid.svg" alt="Android 아이콘" />
          </p>
          <span class="app-icon-tit">Android</span>
        </div>
        <MsfButton variant="primary">Android 다운로드</MsfButton>
      </MsfBox>
    </div>
    <MsfBox class="infoBox">
      <MsfTextList
        type="dash"
        :items="[
          'App 설치 전 OS 는 최신 OS 버전으로 업데이트 해주세요.<br/>* 최소 권장사항 : IOS 13.0이상 / Android 12.0 이상',
          '해당되는 디바이스 버튼을 클릭하여 파일을 설치합니다.',
          '패드 등록 후 승인을 받으신 후 사용해 주세요.',
        ]"
      />
      <div class="help-desk">
        <img src="@/assets/images/helpDeskIcon.svg" alt="헬프데스크 이미지" />
        <div class="help-desk-text">
          <span class="help-txt">헬프데스크</span>
          <em class="help-number">1588-3391(6번 K-note)</em>
        </div>
      </div>
    </MsfBox>
    <p class="copyright">Copyright © kt M mobile. All rights reserved.</p>
  </div>
  <!-- // 스마트신청서 앱 설치: 헤더,하단메뉴 없는 레이아웃입니다 -->
</template>
<script setup></script>
<style lang="scss" scoped></style>
