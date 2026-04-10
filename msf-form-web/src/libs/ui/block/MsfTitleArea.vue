<!--
  (MsfTitleArea)
  컨텐츠에서 사용하는 타이틀 영역 컴퍼넌트입니다. 
 -->
<template>
  <div :class="rootClasses">
    <div class="heading-area">
      <div class="title">
        <h4 class="page-title" :class="colorClass">
          <slot name="title">{{ props.title }}</slot>
        </h4>
        <div v-if="desc" class="ut-text-desc">{{ props.desc }}</div>
      </div>
      <div v-if="$slots.actions" class="action-slot">
        <slot name="actions"></slot>
      </div>
    </div>
    <!-- 타이틀 하단영역 자유로운 커스텀 -->
    <div v-if="$slots.content" class="content-slot">
      <slot name="content"></slot>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  // 타이틀 레벨 (기본: 1(14px) / 2(16px) / 3(18px))
  level: {
    type: String,
    default: '1',
    validator: (v) => ['1', '2', '3'].includes(v),
  },
  // 타이틀
  title: {
    type: String,
    default: '',
  },
  // 밑에 안내문구 있는경우
  desc: {
    type: String,
    default: '',
  },
  // 타이틀 하단 라인 숨김 여부
  noline: {
    type: Boolean,
    default: false,
  },
  // 타이틀 볼드처리
  bold: {
    type: Boolean,
    default: false,
  },
  // 컬러지정(유틸클래스로 연결됨)
  color: {
    type: String,
    default: undefined,
  },
})

const rootClasses = computed(() => [
  'title-area-root',
  `level-${props.level}`,
  {
    'is-noline': props.noline,
    'is-bold': props.bold,
  },
])

// 타이틀 유틸클래스 지정
const colorClass = computed(() => {
  return props.color ? `ut-color-${props.color}` : null
})
</script>

<style lang="scss" scoped>
.title-area-root {
  --title-area-font-size: var(--font-size-20);
  --title-area-font-weight: var(--font-weight-medium);
  --title-area-line-height: var(--line-height-heading);
  --title-area-text-color: var(--color-foreground);
  --title-area-border-color: var(--color-foreground);
  --title-area-margin-top: #{rem(40px)};
  --title-area-margin-bottom: #{rem(24px)};

  margin-block: var(--title-area-margin-top) var(--title-area-margin-bottom);

  @include flex($d: column) {
    gap: rem(4px);
  }
  .heading-area {
    @include flex($h: space-between, $v: center);
    border-bottom: var(--border-width-base) solid var(--title-area-border-color);
    .title {
      flex: 1;
      @include flex($d: column) {
        gap: var(--spacing-x1);
      }
      padding-bottom: var(--spacing-x3);
      .page-title {
        margin: 0;
        font-size: var(--title-area-font-size);
        font-weight: var(--title-area-font-weight);
        line-height: var(--title-area-line-height);
        color: var(--title-area-text-color);
      }
    }
  }
  .content-slot {
    padding-block: rem(4px);
  }
  // 타이틀 레벨
  &.level-2 {
    --title-area-font-size: var(--font-size-16);
    --title-area-text-color: var(--color-gray-600);
    --title-area-border-color: var(--color-gray-150);

    --title-area-margin-top: #{rem(24px)};
    --title-area-margin-bottom: #{rem(16px)};
  }
  &.level-3 {
    --title-area-font-size: var(--font-size-18);
    --title-area-text-color: var(--color-gray-600);
    --title-area-border-color: var(--color-gray-150);
    --title-area-font-weight: var(--font-weight-bold);
    --title-area-text-color: var(--color-foreground);

    --title-area-margin-top: #{rem(24px)};
    --title-area-margin-bottom: #{rem(16px)};
  }
  // 하단 라인 숨김
  &.is-noline {
    .heading-area {
      border-bottom: none;
      .title {
        padding-bottom: 0;
      }
    }
  }
  // 타이틀 볼드처리
  &.is-bold {
    --title-area-font-weight: var(--font-weight-bold);
  }
  // 첫번째 자식일경우 상단 공간 초기화
  &:first-child {
    margin-top: 0;
  }
  // 부모가 StepView 레이아웃 클래스를 가지고있는경우에는 무조건 상단마진 부여
  .page-step-panel & {
    margin-block: var(--title-area-margin-top) var(--title-area-margin-bottom);
  }
}
</style>
