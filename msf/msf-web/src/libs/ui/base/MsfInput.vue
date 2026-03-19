<template>
  <div :class="rootClasses">
    <input
      v-bind="$attrs"
      v-model="value"
      :id="inputId"
      :aria-invalid="props.error"
      class="input-inner"
      @input="onInput"
    />
    <div class="action-slot">
      <button
        v-if="showClearBtn"
        type="button"
        class="btn-clear"
        aria-label="입력내용 지우기"
        @mousedown.prevent="handleClear"
      >
        <MsfIcon name="clear" size="small" />
      </button>
      <slot name="right-slot"></slot>
    </div>
  </div>
</template>

<script setup>
import { useAttrs, inject, computed, useId, ref, watch } from 'vue'

const attrs = useAttrs() // 속성에 접근
defineOptions({ inheritAttrs: false })

const props = defineProps({
  modelValue: {
    type: [String, Number],
    default: '',
  },
  /** 외부 전달 ID */
  id: { type: String, default: undefined },
  /** 지우기 버튼을 표시 여부 설정 */
  clearable: { type: Boolean, default: true },
  /** 에러 */
  error: Boolean,
})

// 부모가 사용할 이벤트선언
const emit = defineEmits(['update:modelValue'])

const injectedId = inject('form-group-id', null)
const value = ref(props.modelValue)

// ID 결정
const inputId = props.id || injectedId || useId()

// 현재 iput의 상태 파악합니다
const isDisabled = attrs.disabled !== undefined && attrs.disabled !== false
const isReadonly = attrs.readonly !== undefined && attrs.readonly !== false

// 스타일 클래스
const rootClasses = computed(() => [
  'input-root',
  `is-${props.size}`,
  {
    'is-disabled': isDisabled,
    'is-readonly': isReadonly,
    'is-error': props.error,
  },
])

const onInput = (event) => {
  value.value = event.target.value
}

// 입력값 초기화
const handleClear = () => {
  value.value = ''
}
// 지우기 버튼 노출 조건
const showClearBtn = computed(() => {
  return props.clearable && value.value && !isDisabled && !isReadonly
})

watch(
  () => props.modelValue,
  (newVal) => {
    if (newVal !== value.value) {
      value.value = newVal
    }
  },
)
watch(
  () => value.value,
  (newVal) => {
    emit('update:modelValue', newVal)
  },
)
</script>

<style lang="scss" scoped>
.btn-clear {
  position: relative;
  line-height: 1;
  color: var(--color-gray-400);
}
.action-slot {
  position: absolute;
  right: rem(8px);
  top: 50%;
  transform: translateY(-50%);
  margin-top: -1px;
  @include flex($v: center) {
    gap: var(--spacing-x1);
  }
}
</style>
