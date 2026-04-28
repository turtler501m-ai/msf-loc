<template>
  <div :class="rootClasses">
    <slot></slot>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  variant: {
    type: String,
    default: 'filled', // 'outline' | 'filled'
  },
  align: {
    type: String,
    default: 'left', // 'left' | 'center' | 'right'
  },
  /** 내부 여백 (유틸클래스로 연결됨)*/
  padding: {
    type: [String, Number],
    default: 24,
  },
  /** 상단 여백 (현재 1(16px), 2(24px), 3(32px) 사용중)*/
  margin: {
    type: [String, Number],
    default: 1,
  },
  /** 배경컬러 지정시 */
  bgColor: {
    type: String, // 'gray' | 'gray2' |'white'
  },
})

const rootClasses = computed(() => [
  'box-root',
  props.variant,
  props.align,
  `ut-p-${props.padding}`,
  `mt-${props.margin}`,
  props.bgColor && `bg-${props.bgColor}`,
])
</script>

<style lang="scss" scoped>
.box-root {
  border-radius: var(--border-radius-base);
  font-size: var(--font-size-16);
  margin-top: rem(16px);
  &:first-child {
    margin-top: 0;
  }
  // 박스 내부의 text-list 폰트사이즈 기본지정
  :deep([class^='text-list-root']) {
    li {
      font-size: var(--font-size-16);
    }
  }
  /* 형태 스타일 */
  &.outline {
    border: var(--border-width-base) solid var(--color-gray-150);
    background-color: transparent;
  }
  &.filled {
    border: none;
    background-color: var(--color-gray-25);
    color: var(--color-gray-600);
  }

  /* 정렬 스타일 */
  &.left {
    text-align: left;
  }
  &.center {
    text-align: center;
  }
  &.right {
    text-align: right;
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
  &:where(p) {
    margin-top: 0;
  }

  // Background Color
  &.bg-gray {
    background-color: var(--color-gray-25);
  }
  &.bg-gray2 {
    background-color: var(--color-gray-50);
  }
  &.bg-white {
    background-color: var(--color-background);
  }
}
</style>
