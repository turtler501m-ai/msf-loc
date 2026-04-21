<template>
  <div class="container-root">
    <div class="page-content">
      <h2 class="ut-blind">메인 콘텐츠 영역</h2>
      <slot></slot>
    </div>
  </div>
</template>

<script setup></script>

<style lang="scss" scoped>
.container-root {
  margin: 0 auto;
  width: 100%;
  box-sizing: border-box;
  overflow-y: auto;
  // 컨텐츠 내부
  .page-content {
    width: 100%;
    height: 100%;
    padding-inline: var(--layout-padding-x);
    padding-block: var(--layout-padding-y) var(--layout-padding-y2);
    overflow: auto;
    // 스텝이있는경우 스텝 레이아웃 내부에서 패딩을 줄거다
    &:has(.step-wrapper-layout) {
      padding: 0;
      overflow: unset;
    }
    &:has(.top-btns) {
      padding-top: 0px;
    }
    :deep(.top-btns) {
      padding-block: var(--spacing-x4);
      border-bottom: var(--border-width-base) solid var(--color-gray-100);
      margin-bottom: var(--spacing-x6);
      .btn-root {
        min-width: rem(88px);
      }
    }
  }
}
</style>
