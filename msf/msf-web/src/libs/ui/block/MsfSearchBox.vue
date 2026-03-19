<template>
  <div class="search-box-root">
    <div class="search-content">
      <slot></slot>
    </div>

    <div class="search-action">
      <MsfButton variant="primary" @click="$emit('search')">검색</MsfButton>
    </div>
  </div>
</template>

<script setup>
defineEmits(['search'])
</script>

<style lang="scss" scoped>
.search-box-root {
  margin-block: var(--spacing-x6);
  @include flex() {
    gap: rem(24px);
  }
  background-color: var(--color-gray-25);
  padding: var(--spacing-x6);
  border-radius: var(--border-radius-m);
  border: var(--border-width-base) solid var(--color-gray-100);

  align-items: flex-start;
}
.search-content {
  flex: 1;
  display: grid;
  /* 12컬럼 그리드 설정 */
  grid-template-columns: repeat(12, 1fr);
  gap: var(--spacing-x4) var(--spacing-x4);
}
/* 기본값: 전체(50%) 차지 */
.search-content :deep(> *) {
  grid-column: span 6;
}
/* ===== 가이드 클래스 (주로 FormGroup 에 넣으심됩니다^^/) ===== */
/* 1개 배치 (100%) */
.search-content :deep(> .grid-1) {
  grid-column: span 12 !important;
}
/* 2개 배치 (50%) */
.search-content :deep(> .grid-2) {
  grid-column: span 6 !important;
}
/* 3개 배치 (33.3%) */
.search-content :deep(> .grid-3) {
  grid-column: span 4 !important;
}
/* 4개 배치 (25%) */
.search-content :deep(> .grid-4) {
  grid-column: span 3 !important;
}

/** 하단라인이 있는 자식 */
.search-content :deep(> .bottom-line) {
  border-bottom: var(--border-width-base) solid var(--color-gray-100);
  padding-bottom: var(--spacing-x4);
}
.search-action {
  flex-shrink: 0;
}

.btn-search {
  background-color: #1a1a1a;
  color: #fff;
  width: 90px;
  height: 44px;
  border-radius: 4px;
  font-weight: 600;
  cursor: pointer;
}
</style>
