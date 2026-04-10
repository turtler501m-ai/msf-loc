<!-- 작성완료 : 신청서 열람 박스영역 -->
<template>
  <MsfBox variant="outline" margin="0" class="application-box">
    <div class="box-title">
      <img src="@/assets/images/applyOpenIcon.svg" alt="신청서 열람 아이콘" />
      <em class="tit">신청서 열람</em>
    </div>
    <MsfButton variant="subtle">신청서 열람</MsfButton>
  </MsfBox>
</template>

<script setup></script>

<style lang="scss" scoped>
.application-box {
  .box-title {
    @include flex($d: column, $v: center) {
      gap: rem(8px);
    }
    .tit {
      font-size: var(--font-size-16);
      font-weight: var(--font-weight-medium);
      color: var(--color-gray-600);
    }
  }
}
</style>
