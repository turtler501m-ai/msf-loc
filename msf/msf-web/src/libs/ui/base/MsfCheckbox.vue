<template>
  <span :class="rootClasses">
    <input
      v-bind="$attrs"
      v-model="model"
      type="checkbox"
      class="checkbox-input"
      :id="checkboxId"
      :name="name"
      :value="value"
      :disabled="disabled"
      :aria-invalid="error"
      @change="emit('change', $event)"
    />

    <label v-if="label" :for="checkboxId" class="checkbox-label">
      <span class="checkbox-icon"></span>
      <span :class="{ 'ut-blind': hideLabel }">
        {{ label }}
      </span>
    </label>
  </span>
</template>

<script setup>
import { computed, useId } from 'vue'

defineOptions({ inheritAttrs: false })

const model = defineModel({ type: [Boolean, Array], default: false })

const props = defineProps({
  variant: {
    type: String,
    default: 'default',
    validator: (v) => ['default', 'boxed'].includes(v),
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
})

// 이벤트 등록
const emit = defineEmits(['change'])

// ID 결정
const checkboxId = props.id || useId()

// 스타일 클래스
const rootClasses = computed(() => [
  'checkbox-root',
  props.variant !== 'default' && `is-${props.variant}`,
  props.size !== 'medium' && `is-${props.size}`,
  {
    'is-disabled': props.disabled,
    'is-error': props.error, // 클래스명도 에러로 통일
  },
])
</script>

<style lang="scss" scoped>
.checkbox-root {
  --checkbox-width: #{rem(16px)};
  --checkbox-height: #{rem(16px)};
  --checkbox-border-radius: var(--border-radius-xxs);
  --checkbox-label-font-size: var(--checkbox-font-size, var(--font-size-14));
  --checkbox-label-font-weight: var(--checkbox-font-weight, var(--font-weight-medium));
  --checkbox-label-line-height: 1.2;
  --checkbox-inset-top: calc(
    (var(--checkbox-label-font-size) * var(--checkbox-label-line-height) - var(--checkbox-height)) /
      2
  );
  --checkbox-inset: var(--checkbox-inset-top) 0 0 0;
  --checkbox-label-gap: var(--spacing-x1); // checkbox 아이콘과 라벨 사이의 간격
  --checkbox-border-color: var(--color-gray-400); // checkbox 기본 보더 컬러
  --checkbox-background-color: var(--color-background); // checkbox 기본 배경 컬러
  --checkbox-icon: #{icon('checkbox')};
  --checkbox-icon-color: transparent;
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
  .checkbox-input:disabled + label {
    --checkbox-icon-color: currentColor;
    --checkbox-border-color: var(--color-gray-150);
    --checkbox-background-color: var(--color-bg-disabled);
    color: var(--color-gray-300);
    cursor: default;
  }

  // variants
  &.is-lined {
    .checkbox-icon {
      border: none;
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
}
</style>
