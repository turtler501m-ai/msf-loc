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
      <MsfIcon
        v-if="suffixIcon || isDetailBtn"
        :name="isDetailBtn ? 'link' : suffixIcon"
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
]
export const BTN_SIZE = ['small', 'medium', 'large']
</script>

<script setup>
import { computed, useAttrs } from 'vue'

/** attrs 사용 */
const attrs = useAttrs()
defineOptions({
  inheritAttrs: false,
})

const props = defineProps({
  /** 버튼 스타일 */
  variant: {
    type: String,
    default: 'tertiary',
    validator: (v) => BTN_VARIANTS.includes(v),
  },
  /** 버튼 사이즈 */
  size: {
    type: String,
    default: 'medium',
    validator: (v) => BTN_SIZE.includes(v),
  },
  /** 아이콘 관련 props 넣을 예정 */
  prefixIcon: String,
  suffixIcon: String,
  /** 아이콘만 사용 */
  iconOnly: String,
  /** 100%너비 블럭 여부 */
  block: Boolean,
  /** 비활성화 여부 */
  disabled: Boolean,
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
  },
])

// 버튼크기에 따른 아이콘 사이즈 매핑
const ICON_SIZE_MAP = {
  large: 'medium',
  medium: 'small',
  small: 'xsmall',
}
const mappedIconSize = computed(() => {
  return ICON_SIZE_MAP[props.size] || 'small'
})

/** 버튼 type 처리 */
const buttonType = computed(() => attrs.type || 'button')

// '상세보기' 버튼(옆에 오른쪽 arrow) 고정의 경우
const isDetailBtn = props.variant === 'detail'

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
    gap: var(--spacing-x1);
  }
  padding: var(--spacing-x1) var(--spacing-x2);
  min-width: fit-content;
  height: rem(32px);
  font-size: var(--font-size-14);
  line-height: var(--line-height-fit);
  font-weight: var(--font-weight-bold);
  border: var(--border-width-base) solid transparent;
  border-radius: var(--border-radius-base);
  &:disabled {
    color: var(--color-gray-300);
    border-color: var(--color-gray-50);
    background-color: var(--color-bg-disabled);
  }
  &:has(> :global(.ut-blind)) {
    width: auto;
    padding: 0;
    aspect-ratio: 1;
  }
  &.btn-block {
    width: 100%;
  }
}

// size
.btn-size-large {
  height: rem(40px);
  font-size: var(--font-size-18);
}
.btn-size-medium {
  height: rem(32px);
  font-weight: var(--font-weight-medium);
}
.btn-size-small {
  gap: var(--spacing-x2);
  height: rem(28px);
  font-size: var(--font-size-14);
  font-weight: var(--font-weight-regular);
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
  min-width: rem(88px);
  &:hover {
    background-color: var(--color-primary-hover);
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
    background-color: var(--color-secondary-hover);
    border-color: var(--color-secondary-hover);
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
  &:hover {
    background-color: var(--color-tertiary-hover);
  }
  &:active {
    background-color: var(--color-tertiary-press);
  }
  &:disabled {
    color: var(--color-gray-300);
    border-color: var(--color-gray-150);
    background-color: var(--color-gray-10);
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
    background-color: var(--color-accent1-hover);
    border-color: var(--color-accent1-hover);
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
.btn-subtle {
  color: var(--color-background);
  border-color: var(--color-subtle-base);
  background-color: var(--color-subtle-base);
  min-width: rem(56px);
  &:hover {
    background-color: var(--color-subtle-hover);
    border-color: var(--color-subtle-hover);
  }
  &:active {
    background-color: var(--color-subtle-press);
  }
}
</style>
