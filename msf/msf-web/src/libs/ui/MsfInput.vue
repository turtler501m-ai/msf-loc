<template>
  <div :class="rootClasses">
    <input
      v-bind="$attrs"
      v-model="model"
      :id="inputId"
      class="input-inner"
      :aria-invalid="props.error"
    />
    <button
      v-if="showClearBtn"
      type="button"
      class="btn-clear"
      aria-label="입력내용 지우기"
      @mousedown.prevent="onClear"
    >
      <span class="icon-x">✕</span>
    </button>
    <slot name="right"></slot>
  </div>
</template>

<script setup>
import { inject, computed, useId } from 'vue'

defineOptions({
  inheritAttrs: false,
})

const model = defineModel()

const emit = defineEmits(['clear'])

const props = defineProps({
  /** 지우기 버튼을 표시 여부 설정 */
  id: String,
  clearable: Boolean,
  error: Boolean,
})

const injectedId = inject('form-group-id', null)
/**
 * 우선순위:
 * 1. 내가 직접 받은 props.id
 * 2. 부모(FormGroup)가 내려준 injectedId
 * 3. 둘 다 없으면 새로 생성(useId)
 */
const inputId = props.id || injectedId || useId()

// 클래스 조립
const rootClasses = computed(() => [
  'input-root',
  `is-${props.size}`,
  {
    'is-disabled': props.disabled,
    'is-readonly': props.readonly,
    'is-error': props.error,
  },
])

// 지우기 버튼 노출 조건
const showClearBtn = computed(() => {
  return props.clearable && model.value && !props.disabled && !props.readonly
})

const onClear = () => {
  emit('clear')
}
</script>

<style lang="scss" scoped>
.input-root {
  position: relative;
  @include flex(flex, flex-start, center) {
    gap: var(--spacing-x2);
  }
  width: 100%;
  height: rem(52px);
  padding: 0 var(--spacing-x4);
  font-size: var(--font-size-16);
  line-height: var(--line-height-20);
  border: var(--border-width-base) solid var(--color-gray-150);
  border-radius: var(--border-radius-base);
  background-color: var(--color-background);
  cursor: text;
  .input-inner {
    flex: 1;
    width: 1%;
    height: 100%;
    outline: 0;
    font-weight: var(--font-weight-medium);
    &:focus {
      touch-action: pan-x;
    }
  }

  // size
  &.is-small .input-inner {
    font-size: var(--font-size-14);
    height: rem(48px);
  }
  &.is-medium .input-inner {
    height: rem(52px);
  }
  &.is-large .input-inner {
    font-size: var(--font-size-18);
    height: rem(56px);
  }

  // state
  &.is-disabled {
    background-color: var(--color-bg-disabled);
    cursor: not-allowed;
  }
  &.is-readonly {
    background-color: var(--color-bg-disabled);
    border-color: var(--color-gray-400);
  }
  &.is-error {
    border-color: var(--color-red-500);
    border-width: rem(2px);
  }
}
</style>
