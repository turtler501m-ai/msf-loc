<template>
  <div
    class="textarea-root"
    :class="{ 'is-disabled': disabled, 'is-readonly': readonly, 'is-error': error }"
    :style="{ height: props.height }"
  >
    <textarea
      ref="textareaRef"
      class="textarea-inner"
      :id="inputId"
      :name="name"
      :value="modelValue"
      :maxlength="maxLength"
      :placeholder="placeholder"
      :disabled="disabled"
      :readonly="readonly"
      :aria-invalid="error"
      v-bind="$attrs"
      @input="onInput"
    ></textarea>
  </div>
</template>

<script setup>
import { inject, useId, ref, watch } from 'vue'

defineOptions({
  name: 'CustomTextarea',
  inheritAttrs: false,
})

const props = defineProps({
  modelValue: { type: [String, Number], default: '' },
  id: { type: String, default: undefined },
  name: { type: String, default: undefined },
  /** 고정 높이값 설정시 */
  height: { type: String, default: '200px' },
  maxLength: { type: [Number, String], default: undefined },
  placeholder: { type: String, default: '' },
  disabled: Boolean,
  readonly: Boolean,
  error: Boolean,
})

// 부모가 사용할 이벤트선언
const emit = defineEmits(['update:modelValue'])

const textareaRef = ref(null)
const injectedId = inject('form-group-id', null)

// ID 결정
const inputId = props.id || injectedId || useId()

const onInput = (e) => {
  emit('update:modelValue', e.target.value)
}

// 초기값 대응 및 외부에서 modelValue 변경 시 대응
watch(
  () => props.modelValue,
  () => {},
)
</script>

<style lang="scss" scoped>
.textarea-root {
  --textarea-default-height: #{rem(200px)}; // textarea 기본높이

  position: relative;
  width: 100%;
  min-height: rem(100px);
  height: var(--textarea-default-height);
  padding: var(--spacing-x2) var(--spacing-x2);
  border: var(--border-width-base) solid var(--color-gray-400);
  border-radius: var(--border-radius-base);
  background-color: var(--color-background);
  transition: border-color 0.2s;
  overflow: hidden; // 자동 높이 시 스크롤바 깜빡임 방지
  &:focus-within {
    border-color: var(--color-primary);
  }
  .textarea-inner {
    width: 100%;
    height: 100%;
    border: none;
    outline: 0;
    padding: 0;
    background: transparent;
    font-family: inherit;
    font-size: var(--font-size-14);
    color: var(--color-text-main);
    resize: none;
    &::placeholder {
      color: var(--color-gray-450);
    }
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
    border-color: var(--color-error);
  }
}
</style>
