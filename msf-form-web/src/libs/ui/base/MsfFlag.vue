<script setup>
import { computed } from 'vue'

const props = defineProps({
  variant: {
    type: String,
    default: 'filled', // 'outlined' | 'filled'
  },
  data: {
    type: [Object, Array, String, Number],
    required: true,
  },
  size: {
    type: String,
    default: 'medium',
  },
  radius: {
    type: String,
    default: 'all',
  },
  color: {
    type: String,
    default: 'gray',
  },
  className: {
    type: String,
    default: '',
  },
  type: {
    type: String,
    default: 'block',
  },
})

const flags = computed(() => (Array.isArray(props.data) ? props.data : [props.data]))

// 클래스 분리
const rootClasses = computed(() => [
  'flag-root',
  props.size,
  `is-${props.variant}`,
  props.radius ? `radius-${props.radius}` : '',
  props.type !== 'block' ? props.type : '',
])

const getItemData = (item) => {
  const isObject = typeof item === 'object' && item !== null && 'label' in item
  return {
    label: isObject ? item.label : item,
    color: isObject ? (item.color ?? props.color) : props.color,
    className: isObject ? item.className : '',
  }
}
</script>

<template>
  <div :class="rootClasses">
    <span
      v-for="(item, index) in flags"
      :key="index"
      class="flag-item"
      :class="[getItemData(item).color, getItemData(item).className]"
    >
      {{ getItemData(item).label }}
    </span>
  </div>
</template>

<style lang="scss" scoped>
.flag-root {
  --flag-min-width: #{rem(56px)};
  --flag-height: #{rem(24px)};
  --flag-font-size: var(--font-size-15);
  --flag-text-color: var(--color-gray-600);
  --flag-border-color: var(--color-gray-200);
  --flag-bg-color: var(--color-gray-150);

  @include flex($display: inline-flex) {
    gap: rem(4px);
  }
  &.inline {
    display: inline-flex;
  }
  // 사이즈 정의
  &.small {
    --flag-min-width: #{rem(44px)};
    --flag-height: #{rem(18px)};
    --flag-font-size: var(--font-size-12);
  }
  &.medium {
    --flag-font-size: var(--font-size-15);
  }
  &.large {
    --flag-font-size: var(--font-size-16);
  }
  // 라운드 정의
  &.radius-all {
    .flag-item {
      border-radius: rem(4px);
    }
  }
  &.radius-tl {
    .flag-item {
      border-top-left-radius: rem(4px);
    }
  }
  .flag-item {
    @include flex($display: inline-flex, $v: center, $h: center);
    min-width: var(--flag-min-width);
    padding-inline: rem(8px);
    font-size: var(--flag-font-size);
    font-weight: var(--font-weight-regular);
    height: var(--flag-height);
    line-height: var(--flag-height);
    color: var(--flag-text-color);
    background-color: var(--flag-bg-color);
    border: var(--border-width-base) solid;
    border-color: var(--flag-border-color);

    // 컬러 정의
    &.gray {
      --flag-text-color: var(--color-gray-600);
      --flag-bg-color: var(--color-gray-150);
      --flag-border-color: var(--color-gray-200);
    }
    // 민트
    &.accent {
      --flag-text-color: var(--color-white);
      --flag-bg-color: var(--color-accent2-base);
      --flag-border-color: var(--color-accent2-base);
    }
    // 빨강
    &.accent2 {
      --flag-text-color: var(--color-white);
      --flag-bg-color: var(--color-accent-base);
      --flag-border-color: var(--color-accent-base);
    }
  }

  // 아웃라인 스타일
  &.is-outlined {
    .flag-item {
      --flag-text-color: var(--color-primary-base);
      --flag-bg-color: var(--color-white);
      --flag-border-color: var(--color-gray-400);
    }
  }
}
</style>
