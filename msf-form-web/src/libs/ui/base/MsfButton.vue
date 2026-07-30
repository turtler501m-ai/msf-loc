<template>
  <button
    ref="buttonRef"
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
      <MsfIcon
        v-if="loading || prefixIcon"
        :name="loading ? 'loading' : prefixIcon"
        :size="mappedIconSize"
        :class="{ 'btn-loader': loading }"
      />
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
  'validation', // 유효성체크 검증용 버튼스타일 분리
]
export const BTN_SIZE = ['x-small', 'small', 'medium', 'large']
</script>

<script setup>
import { computed, useAttrs, ref } from 'vue'
import { useRouter } from 'vue-router'
import { showAlert } from '@/libs/utils/comp.utils'

// 속성에 접근
const attrs = useAttrs()
// 부모(root)에 바로 상속되지 않도록 설정
defineOptions({ inheritAttrs: false })

const router = useRouter()

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
  /** 메시지가 있으면, 비활성화 스타일이지만 클릭은 된다. */
  readonlyMsg: String,
  /** 최소너비 해제 */
  noMinWidth: Boolean,
  /** 단순 페이지 이동(useRouter)-단순 라우팅 목적 (click과 분리 사용)
   *  <MsfButton to="/login">이동</MsfButton>
   *  <MsfButton :to="{ name: 'Login' }">이동</MsfButton>
   */
  to: [String, Object],
  /** 앞쪽 로딩아이콘 표시 여부 (로딩중 버튼에 필요시 사용) */
  loading: Boolean,
})

// 이벤트 등록
const emit = defineEmits(['click'])

const buttonRef = ref(null)

// 루트 클래스
const rootClasses = computed(() => [
  'btn-root',
  `btn-${props.variant}`,
  `btn-size-${props.size}`,
  {
    'btn-rounded': props.rounded,
    'btn-block': props.block,
    'btn-disabled': props.disabled,
    'btn-readonly': props.readonlyMsg,
    'btn-iconOnly': props.iconOnly,
    'is-active': props.active,
    'no-min-width': props.noMinWidth,
    'is-loading': props.loading,
  },
])

// 버튼크기에 따른 아이콘 사이즈 매핑
const ICON_SIZE_MAP = {
  large: 'large',
  medium: 'large',
  small: 'large',
  xsmall: 'small',
}
const mappedIconSize = computed(() => {
  return ICON_SIZE_MAP[props.size] || 'small'
})

/** 버튼 type 처리 */
const buttonType = computed(() => attrs.type || 'button')

// 버튼 클릭 핸들러
const handleClick = (event) => {
  // 1. disabled이거나 로딩중이면 클릭 방지
  if (props.disabled || props.loading) return
  // 2. readonlyMsg 설정된 문구로 alert를 띄움!!
  if (props.readonlyMsg) {
    showAlert(props.readonlyMsg)
    return
  }
  // 3. 단순 라우팅(to)이 있다면 우선 이동
  if (props.to) {
    router.push(props.to)
    return // 이동 시에는 click 이벤트를 전파하지 않음 (충돌 방지)
  }
  // 4. 나머지 정상 동작 수행
  emit('click', event)
}
defineExpose({
  focus: () => {
    buttonRef.value?.focus()
  },
})
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
  &.no-min-width {
    min-width: rem(80px); //최소 넓이로 지정된 값으로 지정
  }
  height: rem(56px);
  font-size: var(--font-size-18);
  line-height: var(--line-height-fit);
  font-weight: var(--font-weight-bold);
  border: var(--border-width-base) solid transparent;
  border-radius: var(--border-radius-base);
  color: var(--color-primary-base);
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
  /** readonly msg가 들어올경우 disabled 스타일이지만 클릭은 된다! */
  &.btn-readonly {
    color: var(--color-text-disabled) !important;
    border-color: var(--color-bg-disabled) !important;
    background-color: var(--color-bg-disabled) !important;
    &.btn-tertiary,
    &.btn-detail,
    &.btn-subtle,
    &.btn-toggle,
    &.btn-validation {
      border-color: var(--color-line-disabled) !important;
    }
  }
  &.btn-iconOnly {
    min-width: fit-content;
    &:hover {
      background-color: transparent;
    }
    &:active {
      background-color: transparent;
    }
    &:disabled {
      background-color: transparent !important;
      color: var(--color-text-disabled);
    }
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
.btn-size-xsmall {
  width: auto;
  height: rem(24px) !important;
  font-size: var(--font-size-14);
  font-weight: var(--font-weight-regular) !important;
  padding-inline: rem(8px);
  border-radius: rem(4px);
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
  &.no-min-width {
    font-weight: var(--font-weight-medium);
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
    background-color: var(--color-tertiary-hover);
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
// 유효성 체크용 토글버튼 분리(active props와 함께 사용)
.btn-validation {
  --btn-validation-text: var(--color-accent1-hover);
  --btn-validation-border: var(--color-accent1-hover);
  --btn-validation-bg: var(--color-background);
  --btn-validation-active-text: var(--color-accent1-hover);
  --btn-validation-active-border: #efd2d2;
  --btn-validation-active-bg: #fff3f3;

  color: var(--btn-validation-text);
  font-weight: var(--font-weight-semibold);
  border-color: var(--btn-validation-border);
  background-color: var(--btn-validation-bg);
  min-width: rem(80px);
  height: rem(52px); // 디자인상 높이고정
  &.is-active {
    color: var(--btn-validation-active-text);
    border-color: var(--btn-validation-active-border);
    background-color: var(--btn-validation-active-bg);
  }
  &:disabled {
    color: var(--color-text-disabled) !important;
    border-color: var(--color-line-disabled) !important;
    background-color: var(--color-bg-disabled) !important;
  }
}
// 스텝 영역 컨트롤 버튼
.btn-step {
  --btn-step-padding-block: #{rem(10px)};
  --btn-step-padding-inline: #{rem(6px)};

  @include flex($d: column) {
    gap: rem(6px);
  }
  width: 100%;
  min-width: auto;
  height: auto;
  font-size: var(--font-size-15);
  line-height: var(--line-height-heading);
  font-weight: var(--font-weight-medium);
  padding-block: var(--btn-step-padding-block);
  padding-inline: var(--btn-step-padding-inline);
  color: var(--color-background);
  border-color: var(--color-primary-base);
  background-color: var(--color-primary-base);
  border-radius: rem(8px);
  -webkit-box-shadow: 0 0.25rem 0.5rem 0 #00000033;
  box-shadow: 0 0.25rem 0.5rem 0 #00000033;
  -webkit-transition: opacity 0.3s;
  transition: opacity 0.3s;
  :deep(.msf-icon) {
    --icon-size: #{rem(18px)};
  }
}
// props.loading 설정시 prefix 로딩 아이콘 설정함
.is-loading {
  pointer-events: none;
  .btn-loader {
    animation: btnLoadingSpin 0.8s linear infinite;
  }
  @keyframes btnLoadingSpin {
    from {
      transform: rotate(0deg);
    }
    to {
      transform: rotate(360deg);
    }
  }
}
</style>
