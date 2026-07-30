<!-- 접고 펼치는 컴퍼넌트 -->
<template>
  <div :class="rootClasses" :style="rootStyle">
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
      <div class="header-side">
        <!-- 헤더우측 열림/닫힘표시 영역 -->
        <div class="side-arrow">
          <span :class="{ 'ut-blind': !showStateText }">{{ isOpen ? '닫기' : '열림' }}</span>
          <MsfIcon :name="isOpen ? 'arrowUp' : 'arrowDown'" :class="{ 'is-open': isOpen }" />
        </div>
      </div>
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
import { ref, computed } from 'vue'

const props = defineProps({
  // 초기 열림상태 지정
  defaultOpen: { type: Boolean, default: false },
  // 타이틀 볼드처리 (default: true)
  bold: {
    type: Boolean,
    default: true,
  },
  // 트리거 상하단 여백
  triggerPaddingBlock: { type: String },
  // 헤더우측 열림/닫힘 표시 텍스트 표시여부 (default: true)
  showStateText: {
    type: Boolean,
    default: true,
  },
})

const rootClasses = computed(() => [
  'collapse-root',
  {
    'collapse-on': isOpen.value,
    'is-bold': props.bold,
  },
])
const rootStyle = computed(() => ({
  '--collapse-trigger-padding-block': props.triggerPaddingBlock,
}))

const isOpen = ref(props.defaultOpen)
</script>

<style lang="scss" scoped>
.collapse-root {
  --collapse-border-color: var(--color-gray-150);
  --collapse-title-weight: var(--font-weight-medium);
  --collapse-trigger-padding-block: #{rem(12px)};

  // 첫번째 자식일경우 헤더상단 공간 초기화
  &:first-child {
    .collapse-header {
      padding-top: 0;
    }
  }
  .collapse-header {
    width: 100%;
    @include flex($v: center, $h: space-between);
    padding-inline: 0 rem(12px);
    padding-block: var(--collapse-trigger-padding-block);
    cursor: pointer;
    border-bottom: 1px solid var(--collapse-border-color);
    .header-content {
      font-size: var(--font-size-16);
      font-weight: var(--collapse-title-weight);
    }
    .header-side {
      // 헤더 우측 열림/닫힘표시 영역
      .side-arrow {
        font-size: var(--font-size-18);
        font-weight: var(--collapse-title-weight);
        line-height: var(--line-height-fit);
        @include flex($v: center) {
          gap: rem(2px);
        }
      }
    }
  }

  /* --- CSS Grid 슬라이드 애니메이션 --- */
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

  // 텍스트 볼드처리
  &.is-bold {
    --collapse-title-weight: var(--font-weight-bold);
    --title-area-text-color: var(--color-foreground); // 볼드처리 기본컬러 블랙지정
  }
}
</style>
