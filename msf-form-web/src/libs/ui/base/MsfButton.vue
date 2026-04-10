<template>
  <button
    v-bind="$attrs"
    :type="buttonType"
    :class="rootClasses"
    :disabled="disabled"
    @click="handleClick"
  >
    <template v-if="iconOnly">
      <MsfIcon :name="iconOnly" :size="mappedIconSize" />
      <span class="ut-blind"><slot></slot></span>
    </template>
    <template v-else>
      <MsfIcon v-if="prefixIcon" :name="prefixIcon" :size="mappedIconSize" />
      <slot v-if="$slots.default"></slot>
      <!-- variant가 detail인 경우에는 link아이콘 고정 -->
      <MsfIcon
        v-if="suffixIcon || props.variant === 'detail'"
        :name="props.variant === 'detail' ? 'link' : suffixIcon"
        :size="mappedIconSize"
      />
    </template>
  </button>
</template>

<script>
export const BTN_VARIANTS = [
  'primary',
  'secondary',
  'tertiary',
  'accent1',
  'accent2',
  'ghost',
  'subtle',
  'detail',
  'toggle',
]
export const BTN_SIZE = ['small', 'medium', 'large']
</script>

<script setup>
import { computed, useAttrs } from 'vue'

// 속성에 접근
const attrs = useAttrs()
// 부모(root)에 바로 상속되지 않도록 설정
defineOptions({ inheritAttrs: false })

const props = defineProps({
  /** 버튼 스타일 */
  variant: {
    type: String,
    default: 'tertiary',
    // validator: (v) => BTN_VARIANTS.includes(v),
  },
  /** 버튼 사이즈 */
  size: {
    type: String,
    default: 'medium',
    // validator: (v) => BTN_SIZE.includes(v),
  },
  /** 아이콘 앞/뒤 설정*/
  prefixIcon: String,
  suffixIcon: String,
  /** 아이콘만 사용 */
  iconOnly: String,
  /** 블럭 넓이 여부 */
  block: Boolean,
  /** 비활성화 여부 */
  disabled: Boolean,
  /** 활성화 여부 */
  active: Boolean,
})

// 이벤트 등록
const emit = defineEmits(['click'])

// 루트 클래스
const rootClasses = computed(() => [
  'btn-root',
  `btn-${props.variant}`,
  `btn-size-${props.size}`,
  {
    'btn-rounded': props.rounded,
    'btn-block': props.block,
    'btn-disabled': props.disabled,
    'btn-iconOnly': props.iconOnly,
    'is-active': props.active,
  },
])

// 버튼크기에 따른 아이콘 사이즈 매핑
const ICON_SIZE_MAP = {
  large: 'large',
  medium: 'large',
  small: 'large',
}
const mappedIconSize = computed(() => {
  return ICON_SIZE_MAP[props.size] || 'small'
})

/** 버튼 type 처리 */
const buttonType = computed(() => attrs.type || 'button')

// 버튼 클릭 핸들러
const handleClick = (event) => {
  if (props.disabled) return
  emit('click', event)
}
</script>

<style lang="scss" scoped>
.btn-root {
  position: relative;
  @include flex(inline-flex, center, center) {
    gap: var(--spacing-x2);
  }
  padding: var(--spacing-x1) var(--spacing-x4);
  // min-width: fit-content;
  min-width: rem(120px);
  height: rem(56px);
  font-size: var(--font-size-18);
  line-height: var(--line-height-fit);
  font-weight: var(--font-weight-bold);
  border: var(--border-width-base) solid transparent;
  border-radius: var(--border-radius-base);
  &:disabled {
    color: var(--color-gray-300) !important;
    border-color: var(--color-gray-50) !important;
    background-color: var(--color-bg-disabled) !important;
  }
  // &:has(> :global(.ut-blind)) {
  //   width: auto;
  //   padding: 0;
  //   aspect-ratio: 1;
  // }
  &.btn-block {
    width: 100%;
  }
  &.btn-iconOnly {
    min-width: fit-content;
  }
}

// size
.btn-size-large {
  height: rem(60px);
  font-size: var(--font-size-18);
}
.btn-size-medium {
  height: rem(56px);
}
.btn-size-small {
  height: rem(52px);
  font-weight: var(--font-weight-medium);
}

// rounded
.btn-rounded {
  width: auto;
  font-weight: var(--font-weight-semibold);
  border-radius: var(--border-radius-max);
  &.btn-size-small {
    padding-inline: var(--spacing-x10);
    font-size: var(--font-size-12);
  }
}

// variant
.btn-primary {
  color: var(--color-background);
  border-color: var(--color-primary-base);
  background-color: var(--color-primary-base);
  &:hover {
    background-color: var(--color-primary-base);
  }
  &:active {
    background-color: var(--color-primary-press);
  }
}
.btn-secondary {
  color: var(--color-foreground);
  border-color: var(--color-secondary-base);
  background-color: var(--color-secondary-base);
  &:hover {
    border-color: var(--color-secondary-base);
    background-color: var(--color-secondary-base);
  }
  &:active {
    background-color: var(--color-secondary-press);
    border-color: var(--color-secondary-press);
  }
}
.btn-tertiary,
.btn-detail {
  border-color: var(--color-gray-400);
  background-color: var(--color-background);
  font-weight: var(--font-weight-medium);
  &:hover {
    background-color: var(--color-background);
  }
  &:active {
    background-color: var(--color-tertiary-press);
  }
  &:disabled {
    color: var(--color-gray-300) !important;
    border-color: var(--color-gray-150) !important;
    background-color: var(--color-gray-50) !important;
  }
}
// variant="detail" 의 아이콘과의 간격스타일
.btn-detail {
  gap: rem(8px);
}
.btn-accent1 {
  color: var(--color-background);
  border-color: var(--color-accent1-base);
  background-color: var(--color-accent1-base);
  &:hover {
    border-color: var(--color-accent1-base);
    background-color: var(--color-accent1-base);
  }
  &:active {
    background-color: var(--color-accent1-press);
  }
}
.btn-accent2 {
  color: var(--color-background);
  border-color: var(--color-accent2-base);
  background-color: var(--color-accent2-base);
  // 일단 스타일 주석처리
  // &:hover {
  //   background-color: var(--color-accent2-hover);
  //   border-color: var(--color-accent2-hover);
  // }
  // &:active {
  //   background-color: var(--color-accent2-press);
  // }
}
// 회색 컨트롤 버튼
.btn-subtle {
  color: var(--color-background);
  font-weight: var(--font-weight-medium);
  border-color: var(--color-subtle-base);
  background-color: var(--color-subtle-base);
  font-weight: var(--font-weight-medium);
  // min-width: fit-content;
  min-width: rem(80px);
  height: rem(52px); // 디자인상 높이고정
  &:hover {
    border-color: var(--color-subtle-base);
    background-color: var(--color-subtle-hover);
  }
  &:active {
    background-color: var(--color-subtle-press);
  }
  &:disabled {
    color: var(--color-text-disabled) !important;
    border-color: var(--color-line-disabled) !important;
    background-color: var(--color-bg-disabled) !important;
  }
}
// 녹색 토글버튼(active props와 함께 사용)
.btn-toggle {
  color: var(--color-accent2-base);
  font-weight: var(--font-weight-medium);
  border-color: var(--color-accent2-base);
  background-color: var(--color-background);
  min-width: rem(80px);
  height: rem(52px); // 디자인상 높이고정
  &.is-active {
    color: var(--color-white);
    border-color: var(--color-accent2-base);
    background-color: var(--color-accent2-base);
  }
  &:disabled {
    color: var(--color-text-disabled) !important;
    border-color: var(--color-line-disabled) !important;
    background-color: var(--color-bg-disabled) !important;
  }
}
</style>
