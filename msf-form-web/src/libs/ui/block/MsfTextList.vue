<template>
  <ul :class="rootClasses">
    <template v-if="props.items && props.items.length > 0">
      <li v-for="(item, index) in props.items" :key="index" class="text-list-item">
        <!-- <slot :item="item" :index="index">
          {{ item }}
        </slot> -->
        <!-- html로 받으려면 -->
        <slot :item="item" :index="index">
          <span v-html="item"></span>
        </slot>
      </li>
    </template>

    <slot v-else></slot>
  </ul>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  items: {
    type: Array,
    default: () => [],
  },
  // 앞에 붙는 타입 종류
  type: {
    type: String,
    default: 'star', // 기본값: ※(star)
    validator: (value) => ['dot', 'dash', 'star', 'none', 'number'].includes(value),
  },
  // 타이틀 레벨 (기본: 1) - 폰트사이즈 14px(1), 16px(2)
  level: {
    type: String,
    default: '2',
    validator: (v) => ['1', '2'].includes(v),
  },
  // 상단 여백 (현재 0(0), 1(16px), 2(24px), 3(32px) 사용중)
  margin: {
    type: [String, Number],
    default: 1,
  },
  // 하단 구분라인 여부
  bottomDivider: {
    type: Boolean,
    default: false,
  },
  // 색상
  color: {
    type: String,
    default: 'default',
    validator: (v) => ['default', 'mint'].includes(v),
  },
})

// 클래스 지정
const rootClasses = computed(() => [
  'text-list-root',
  `level-${props.level}`,
  `is-type-${props.type}`,
  `mt-${props.margin}`,
  `color-${props.color}`,
  {
    'has-bottomDivider': props.bottomDivider,
  },
])
</script>

<style lang="scss" scoped>
.text-list-root {
  --text-list-font-size: var(--font-size-14);
  --text-list-space-gap: #{rem(8px)};
  --text-list-margin-top: #{rem(16px)};
  --text-list-color: var(--color-gray-600);
  @include flex($d: column) {
    gap: var(--text-list-space-gap);
  }
  margin-top: var(--text-list-margin-top);

  // 슬롯으로 들어오는 li 태그에도 스타일을 적용하기 위해 :deep 사용
  :deep(li),
  .text-list-item {
    position: relative;
    padding-left: 0.9em;
    color: var(--text-list-color);
    font-size: var(--text-list-font-size);
    line-height: var(--line-height-heading);
    & > span {
      display: block;
    }
    // 공통 불렛 스타일 (가상 요소)
    &::before {
      position: absolute;
      left: 0;
      top: 0;
    }
  }
  // 1. 기본 기호 타입들
  &.is-type-dot > :deep(li)::before,
  &.is-type-dot > .text-list-item::before {
    content: '•';
  }
  &.is-type-dash > :deep(li)::before,
  &.is-type-dash > .text-list-item::before {
    content: '-';
  }
  &.is-type-star > :deep(li)::before,
  &.is-type-star > .text-list-item::before {
    content: '※';
    // left: -2px; /* 당구장 표시는 폭이 넓어 위치 미세 조정 */
  }
  &.is-type-none > :deep(li)::before,
  &.is-type-none > .text-list-item::before {
    display: none;
  }
  &.is-type-none {
    &:deep(li),
    &.text-list-item {
      padding-left: 0;
    }
  }

  // 숫자 타입을 위한 카운터 초기화
  &.is-type-number {
    counter-reset: list-counter;
  }

  // 2. 숫자 타입 (Counter 활용)
  &.is-type-number {
    :deep(li),
    .text-list-item {
      padding-left: rem(16px); // 숫자는 너비가 있으므로 여백 추가
      counter-increment: list-counter; // 아이템마다 1씩 증가

      &::before {
        content: counter(list-counter) '.'; // '1.', '2.' 형태
        font-family: inherit;
        font-weight: inherit;
        font-variant-numeric: tabular-nums; // 숫자 폭 고정 (10번대에서도 정렬 유지)
      }
    }
  }

  // 타이틀 레벨
  &.level-1 {
    --text-list-font-size: var(--font-size-14);
    & > li {
      font-size: var(--text-list-font-size) !important;
    }
  }
  &.level-2 {
    --text-list-font-size: var(--font-size-16);
    --text-list-space-gap: #{rem(4px)};
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

  // 하단 보더여부
  &.has-bottomDivider {
    padding-bottom: rem(16px);
    margin-bottom: rem(16px);
    border-bottom: var(--border-width-base) solid var(--color-gray-100);
  }

  // 컬러지정
  &.color-mint {
    --text-list-color: var(--color-accent2-base);
  }
  // 첫번째 자식일경우 상단 공간 초기화
  &:first-child {
    margin-top: 0;
  }
}
</style>
