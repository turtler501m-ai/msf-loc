<template>
  <main class="main-layout">
    <!-- 차트 영역-->
    <section class="main-box box-status">
      <MsfMainChart />
    </section>
    <!-- // 차트 영역-->
    <!-- Q&A -->
    <section class="main-box box-qna">
      <MsfMainQnaList />
    </section>
    <!-- // Q&A -->
    <!-- 공지사항 -->
    <section class="main-box box-notice">
      <MsfMainNoticeList />
    </section>
    <!-- // 공지사항 -->
  </main>
  <!-- 팝업목록: 개발시 새로운 파일명으로 생성하여 사용 -->
</template>

<script setup></script>

<style lang="scss" scoped></style>
