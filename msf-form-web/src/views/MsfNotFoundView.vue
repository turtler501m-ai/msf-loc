<template>
  <!-- 404 Not Found -->
  <!-- 안내 -->
  <div class="error-wrap">
    <img src="@/assets/images/errorIcon.svg" alt="에러" class="error-icon" />
    <div class="error-msg">
      <strong class="error-tit">Page Not Found</strong>
      <p class="error-info">페이지를 찾을 수 없습니다.</p>
    </div>
    <div class="error-desc">
      존재하지 않는 주소를 입력하셨거나 요청하신 페이지의 주소가 변경, 삭제되어 찾을 수 없습니다.
    </div>
    <MsfButtonGroup align="center" margin="2">
      <MsfButton variant="primary" to="/">홈으로 가기</MsfButton>
    </MsfButtonGroup>
  </div>
  <!-- // 안내 -->
</template>

<script setup></script>

<style scoped></style>
