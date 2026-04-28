<template>
  <span v-bind="rootAttrs" :class="rootClasses">
    <input
      v-bind="inputAttrs"
      type="checkbox"
      class="checkbox-input"
      :id="checkboxId"
      :name="name"
      :value="value"
      :checked="isChecked"
      :disabled="disabled"
      :aria-invalid="error"
      @change="handleChange"
    />
    <label v-if="label" :for="checkboxId" class="checkbox-label">
      <span class="checkbox-icon"></span>
      <span :class="{ 'ut-blind': hideLabel }">
        <slot name="label-prepend"></slot>
        {{ label }}
        <slot name="label-append"></slot>
      </span>
    </label>
  </span>
</template>

<script setup>
import { computed, useId, useAttrs } from 'vue'

// 부모(root)에 바로 상속되지 않도록 설정
defineOptions({ inheritAttrs: false })
// 속성 분리
const attrs = useAttrs()
// root에 부여할 속성
const rootAttrs = computed(() => ({
  class: attrs.class,
  style: attrs.style,
}))
// input에 부여할 속성
const inputAttrs = computed(() => {
  const rest = { ...attrs }
  delete rest.class
  delete rest.style
  return rest
})

const props = defineProps({
  modelValue: { type: [Boolean, Array, String, Number], default: false },
  variant: {
    type: String,
    default: 'default',
    validator: (v) => ['default', 'lined'].includes(v),
  },
  size: {
    type: String,
    default: 'medium',
    validator: (v) => ['small', 'medium'].includes(v),
  },
  label: { type: String, default: '' },
  id: { type: String, default: undefined },
  name: { type: String, default: undefined },
  value: { type: [String, Number, Boolean], default: undefined },
  disabled: Boolean,
  error: Boolean,
  hideLabel: Boolean,
  blockPadding: { type: Boolean, default: false },
  // 부모가 전달하는 { true: 'Y', false: 'N' } 객체를 받음
  returndata: Object,
})

// 이벤트 등록
const emit = defineEmits(['update:modelValue', 'change'])

// ID 결정
const checkboxId = props.id || useId()

// 상태 관리
const isChecked = computed(() => {
  if (Array.isArray(props.modelValue)) {
    return props.modelValue.includes(props.value)
  }
  // 부모의 modelValue가 returndata.true 값('Y')과 일치하는지 확인
  return props.modelValue === props.returndata?.true
})

// 체크박스 상태값 업데이트 핸들러
const handleChange = (event) => {
  const checked = event.target.checked
  let nextValue

  if (Array.isArray(props.modelValue)) {
    // 1. 다중 선택(배열)일 때
    const current = [...props.modelValue]
    if (checked) {
      nextValue = [...current, props.value]
    } else {
      nextValue = current.filter((v) => v !== props.value)
    }
  } else {
    // 2. 단일 선택(불리언)일 때
    // 체크 시 'Y', 해제 시 'N'을 방출
    nextValue = props.returndata ? props.returndata[checked] : checked
  }

  emit('update:modelValue', nextValue)
  emit('change', event)
}

// 스타일 클래스
const rootClasses = computed(() => [
  'checkbox-root',
  props.variant !== 'default' && `is-${props.variant}`,
  props.size !== 'medium' && `is-${props.size}`,
  {
    'is-disabled': props.disabled,
    'is-error': props.error, // 클래스명도 에러로 통일
    'is-checked': isChecked.value,
    'is-blockPadding': props.blockPadding,
  },
])
</script>

<style lang="scss" scoped>
.checkbox-root {
  --checkbox-width: #{rem(24px)};
  --checkbox-height: #{rem(24px)};
  --checkbox-border-radius: var(--border-radius-max);
  --checkbox-label-font-size: var(--checkbox-font-size, var(--font-size-16));
  --checkbox-label-font-weight: var(--checkbox-font-weight, var(--font-weight-medium));
  --checkbox-label-line-height: 1.2;
  --checkbox-inset-top: calc(
    (var(--checkbox-label-font-size) * var(--checkbox-label-line-height) - var(--checkbox-height)) /
      2
  );
  --checkbox-inset: var(--checkbox-inset-top) 0 0 0;
  --checkbox-label-gap: var(--spacing-x2); // checkbox 아이콘과 라벨 사이의 간격
  --checkbox-border-color: var(--color-gray-400); // checkbox 기본 보더 컬러
  --checkbox-background-color: var(--color-white); // checkbox 기본 배경 컬러
  // --checkbox-icon: #{icon('checkbox')};
  --checkbox-icon: var(--icon-checkbox-set);
  --checkbox-icon-color: var(--color-gray-500);
  --checkbox-icon-position: 50% 0;

  position: relative;
  display: inline-flex;
  align-items: flex-start;
  font-size: var(--checkbox-label-font-size);
  font-weight: var(--checkbox-label-font-weight);
  line-height: var(--checkbox-label-line-height);
  vertical-align: middle;
  z-index: 0;

  .checkbox-label {
    width: 100%;
    display: var(--checkbox-label-display, inline-flex);
    align-items: inherit;
    gap: var(--checkbox-label-gap);
    color: var(--color-gray-450);
    word-break: break-all;
    cursor: pointer;
    .checkbox-icon {
      flex: 0 0 auto;
      display: inline-flex;
      position: relative;
      inset: var(--checkbox-inset);
      width: var(--checkbox-width);
      height: var(--checkbox-height);
      border: var(--border-width-base) solid var(--checkbox-border-color);
      border-radius: var(--checkbox-border-radius);
      background-color: var(--checkbox-background-color);
      &::before {
        content: '';
        position: absolute;
        inset: -1px;
        mask-image: var(--checkbox-icon);
        mask-position: var(--checkbox-icon-position);
        mask-repeat: no-repeat;
        mask-size: contain;
        background-color: var(--checkbox-icon-color);
      }
    }
    :where(p) {
      margin-top: 0;
      line-height: var(--checkbox-height);
    }
  }

  .checkbox-input {
    flex: 0 0 auto;
    position: relative;
    inset: var(--checkbox-inset);
    width: var(--checkbox-width);
    height: var(--checkbox-height);
    margin-right: calc(var(--checkbox-width) * -1);
    z-index: -1;
  }
  .checkbox-input:checked + label {
    --checkbox-icon-color: var(--color-background);
    --checkbox-border-color: currentColor;
    --checkbox-background-color: currentColor;
    color: var(--color-gray-900);
    .checkbox-icon::before {
      content: '';
    }
  }
  // 비활성화 스타일
  .checkbox-input:disabled + label {
    --checkbox-icon-color: currentColor;
    --checkbox-border-color: var(--color-gray-150);
    --checkbox-background-color: var(--color-bg-disabled);
    color: var(--color-text-disabled);
    cursor: default;
  }
  .checkbox-input:checked:disabled + label {
    --checkbox-icon-color: var(--color-text-disabled);
    --checkbox-border-color: var(--color-bg-disabled);
    --checkbox-background-color: var(--color-bg-disabled);
    color: var(--color-gray-900);
  }

  // variants
  &.is-lined {
    --checkbox-width: #{rem(18px)};
    --checkbox-height: #{rem(18px)};
    // --checkbox-icon: #{icon('check2')};
    --checkbox-icon: var(--icon-checkbox-set2);
    --checkbox-border-color: transparent;
    --checkbox-background-color: transparent;
    --checkbox-border-radius: 0;
    .checkbox-icon {
      &::before {
        position: absolute;
        inset: -1px;
        mask-image: var(--checkbox-icon);
        mask-position: var(--checkbox-icon-position);
        mask-repeat: no-repeat;
        mask-size: contain;
        background-color: var(--checkbox-icon-color);
      }
    }
    .checkbox-input:checked + label {
      --checkbox-icon-color: var(--color-accent2-base);
      --checkbox-border-color: transparent;
      --checkbox-background-color: transparent;
      color: var(--color-gray-900);
    }
  }

  // sizes
  &.is-small {
    /* ... */
  }

  // states
  &.is-disabled {
    pointer-events: none;
    // opacity: 0.5;
  }

  &.is-blockPadding {
    padding-block: rem(14px);
  }
}
</style>
