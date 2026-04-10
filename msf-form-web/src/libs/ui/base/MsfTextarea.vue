<template>
  <div
    v-bind="rootAttrs"
    class="textarea-root"
    :class="{
      'is-disabled': disabled,
      'is-readonly': readonly,
      'is-error': error,
      'has-count': props.maxLength && props.showCount,
    }"
    :style="{ height: props.height }"
  >
    <textarea
      ref="textareaRef"
      class="textarea-inner"
      v-bind="textareaAttrs"
      :id="inputId"
      :name="name"
      :value="modelValue"
      :maxlength="maxLength"
      :placeholder="placeholder"
      :disabled="disabled"
      :readonly="readonly"
      :aria-invalid="error"
      @input="onInput"
    ></textarea>

    <div v-if="maxLength && showCount" class="textarea-count">
      <span class="current">{{ currentLength }}</span>
      <span class="separator">/</span>
      <span class="total">{{ maxLength }}</span>
    </div>
  </div>
</template>

<script setup>
import {
  inject,
  useId,
  ref,
  onMounted,
  onUnmounted,
  watch,
  nextTick,
  useAttrs,
  computed,
} from 'vue'

// 부모(root)에 바로 상속되지 않도록 설정
defineOptions({
  inheritAttrs: false,
})

const props = defineProps({
  modelValue: { type: [String, Number], default: '' },
  id: { type: String, default: undefined },
  name: { type: String, default: undefined },
  height: { type: String, default: undefined },
  resize: Boolean,
  maxLength: { type: [Number, String], default: '1000' }, // 글자 수 제한
  showCount: { type: Boolean, default: true }, // 카운터 표시 여부 옵션
  placeholder: { type: String, default: '' },
  disabled: Boolean,
  readonly: Boolean,
  error: Boolean,
})

// 부모가 사용할 이벤트선언
const emit = defineEmits(['update:modelValue'])

// 속성에 접근
const attrs = useAttrs()

// 현재 글자 수 계산
const currentLength = computed(() => {
  return String(props.modelValue || '').length
})

const rootAttrs = computed(() => ({
  class: attrs.class,
  style: attrs.style,
}))

const textareaAttrs = computed(() => {
  const rest = { ...attrs }
  delete rest.class
  delete rest.style
  return rest
})

const textareaRef = ref(null)
const injectedId = inject('form-group-id', null)
const inputId = computed(() => props.id || injectedId || useId())

const adjustHeight = () => {
  if (!props.resize || !textareaRef.value) return
  const el = textareaRef.value
  el.style.height = 'auto'
  el.style.height = `${el.scrollHeight}px`
}

const onInput = (event) => {
  emit('update:modelValue', event.target.value)
  if (props.resize) {
    adjustHeight()
  }
}

// 초기값 대응 및 외부에서 modelValue 변경 시 대응
watch(
  () => props.modelValue,
  () => {
    nextTick(adjustHeight)
  },
)

onMounted(() => {
  if (props.resize) {
    adjustHeight()
    window.addEventListener('resize', adjustHeight)
  }
})

onUnmounted(() => {
  window.removeEventListener('resize', adjustHeight)
})
</script>

<style lang="scss" scoped>
.textarea-root {
  --textarea-default-height: #{rem(240px)}; // textarea 기본높이

  position: relative;
  width: 100%;
  min-height: rem(100px);
  height: var(--textarea-default-height);
  padding: var(--spacing-x4) var(--spacing-x4);
  border: var(--border-width-base) solid var(--color-gray-400);
  border-radius: var(--border-radius-base);
  background-color: var(--color-background);
  transition: border-color 0.2s;
  overflow: hidden; // 자동 높이 시 스크롤바 깜빡임 방지
  @include flex($d: column) {
    gap: rem(16px);
  }
  &:focus-within {
    border-color: var(--color-primary-base);
  }
  .textarea-inner {
    width: 100%;
    height: 100%;
    border: none;
    outline: 0;
    padding: 0;
    background: transparent;
    font-family: inherit;
    font-size: var(--font-size-16);
    font-weight: var(--font-weight-medium);
    color: var(--color-text-main);
    resize: none;
    &::placeholder {
      color: var(--color-gray-450);
    }
    @include scrollbar();
  }
  &.is-disabled {
    background-color: var(--color-bg-disabled);
    color: var(--color-text-disabled);
    cursor: not-allowed;
    .textarea-inner {
      cursor: not-allowed;
    }
  }
  &.is-readonly {
    background-color: var(--color-bg-disabled);
    border-color: var(--color-gray-400);
    color: var(--color-gray-900);
  }
  &.is-error {
    border-color: var(--color-accent-alert);
    box-shadow: inset 0 0 0 1px var(--color-accent-alert);
  }
  // 카운터 스타일
  .textarea-count {
    align-self: flex-end;
    font-size: var(--font-size-16);
    font-weight: var(--font-weight-medium);
    color: var(--color-gray-450);
    user-select: none;
    .current {
      color: var(--color-gray-450);
    }
    .separator {
      margin: 0 rem(2px);
    }
  }
}
</style>
