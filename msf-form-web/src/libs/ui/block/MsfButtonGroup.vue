<script setup>
import { computed } from 'vue'

const props = defineProps({
  /** 정렬 */
  align: {
    type: String,
    default: 'right',
    validator: (v) => ['left', 'center', 'right'].includes(v),
  },
  /** 간격 설정 */
  gap: {
    type: [String, Number],
    default: 1,
  },
  /** 수직 정렬 여부  */
  vertical: Boolean,
  /** 상단 여백 (현재 1(16px), 2(24px), 3(32px) 사용중)*/
  margin: {
    type: [String, Number],
    default: 0,
  },
  /** 상단 보더여부 */
  borderTop: {
    type: Boolean,
    default: false,
  },
})

const rootClasses = computed(() => [
  'button-group-root',
  `align-${props.align}`,
  `gap-${props.gap}`,
  `mt-${props.margin}`,
  { 'is-vertical': props.vertical },
  { 'is-border-top': props.borderTop },
])
</script>

<template>
  <div :class="rootClasses">
    <slot></slot>
  </div>
</template>

<style lang="scss" scoped>
.button-group-root {
  @include flex($v: center, $h: flex-end, $w: wrap) {
    gap: var(--spacing-x2);
  }
  width: 100%;
  box-sizing: border-box;
  &:global(.ut-align-vertical) {
    align-items: flex-start;
  }

  // 기본 가로 정렬
  &:not(.is-vertical) {
    flex-direction: row;
    align-items: flex-start;
    &.align-left {
      justify-content: flex-start;
    }
    &.align-center {
      justify-content: center;
    }
    &.align-right {
      justify-content: flex-end;
    }
  }
  // 세로 정렬
  &.is-vertical {
    flex-direction: column;
    align-items: flex-start;
    & > * {
      width: 100%;
    }
    &.align-left {
      align-items: flex-start;
    }
    &.align-center {
      align-items: center;
    }
    &.align-right {
      align-items: flex-end;
    }
  }
  // 간격(Gap)
  &.gap-1 {
    gap: rem(8px);
  }
  &.gap-2 {
    gap: rem(12px);
  }
  &.gap-3 {
    gap: rem(16px);
  }
  &.gap-4 {
    gap: rem(4px);
  }

  // Margin Top
  &.mt-0 {
    margin-top: 0;
  }
  &.mt-1 {
    margin-top: rem(16px);
  }
  &.mt-2 {
    margin-top: rem(24px);
  }
  &.mt-3 {
    margin-top: rem(32px);
  }
  :where(p) {
    margin-top: 0;
  }
  // ButtonGroup 다음에 위치한 테이블의 마진상단값 조절
  & + :deep(.base-table-root) {
    margin-top: var(--spacing-x2);
  }

  // 보더 상단을 가지는 경우
  &.is-border-top {
    border-top: var(--border-width-base) solid var(--color-gray-100);
    padding-top: rem(24px);
    margin-top: rem(24px);
  }
}
</style>
