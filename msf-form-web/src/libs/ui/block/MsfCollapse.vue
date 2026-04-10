<!-- 접고 펼치는 컴퍼넌트 -->
<template>
  <div class="collapse-root">
    <button
      type="button"
      class="collapse-header"
      :class="{ 'is-active': isOpen }"
      :aria-expanded="isOpen"
      aria-controls="msf-collapse-content"
      @click="isOpen = !isOpen"
    >
      <div class="header-content">
        <slot name="header">Title</slot>
      </div>
      <MsfIcon name="collapseArrow" :class="{ 'is-open': isOpen }" />
    </button>
    <div class="msf-body-wrapper" :class="{ 'is-open': isOpen }">
      <div class="msf-body-inner">
        <div class="msf-content">
          <slot></slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  // 초기 열림상태 지정
  defaultOpen: { type: Boolean, default: false },
})

const isOpen = ref(props.defaultOpen)
</script>

<style lang="scss" scoped>
.collapse-root {
  border-bottom: var(--border-width-base) solid var(--color-gray-100);
}
.collapse-header {
  width: 100%;
  @include flex($v: center, $h: space-between);
  padding-inline: 0 rem(12px);
  padding-block: rem(12px);
  cursor: pointer;
  transition: border-bottom var(--transition-base);
  /* 타이틀 하단 보더 초기값 */
  border-bottom: 1px solid transparent;
  .header-content {
    font-size: var(--font-size-16);
    font-weight: var(--font-weight-medium);
  }
}

/* 활성화(열림) 상태일 때 헤더 하단 보더 표시 */
.collapse-header.is-active {
  border-bottom-color: #f0f0f0;
}

.msf-arrow {
  width: 7px;
  height: 7px;
  border-right: 2px solid #666;
  border-bottom: 2px solid #666;
  transform: rotate(45deg);
  transition: transform 0.3s ease;
}
.msf-arrow.is-open {
  transform: rotate(-135deg);
}

/* --- 핵심: CSS Grid 슬라이드 애니메이션 --- */
.msf-body-wrapper {
  display: grid;
  grid-template-rows: 0fr; /* 높이 0 */
  transition: grid-template-rows 0.3s ease-in-out;
  overflow: hidden;
}

.msf-body-wrapper.is-open {
  grid-template-rows: 1fr; /* 콘텐츠 높이만큼 자동으로 펼침 */
}

.msf-body-inner {
  min-height: 0; /* Grid 애니메이션 작동을 위한 필수 속성 */
}

.msf-content {
  padding-top: rem(16px);
}
</style>
