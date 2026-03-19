<template>
  <div ref="selectRef" :class="rootClasses" @keydown="onKeyDown">
    <button
      type="button"
      class="select-trigger"
      :disabled="disabled"
      aria-haspopup="listbox"
      :aria-expanded="isOpen"
      :aria-labelledby="ariaLabelledby"
      @click="isOpen = !isOpen"
    >
      <span :class="['selected-text', { 'is-placeholder': !model }]">
        {{ selectedLabel }}
      </span>
      <span class="select-arrow" aria-hidden="true">
        <MsfIcon v-if="!isOpen" name="arrowDown" />
        <MsfIcon v-if="isOpen" name="arrowUp" />
      </span>
    </button>

    <Transition name="fade">
      <ul v-if="isOpen" class="select-options" role="listbox" tabindex="-1">
        <li
          v-for="(option, index) in options"
          :key="option.value"
          role="option"
          :aria-selected="option.value === model"
          :class="[
            'select-option',
            {
              'is-selected': option.value === model,
              'is-disabled': option.disabled,
              'is-active': activeIndex === index,
            },
          ]"
          @click="handleSelect(option)"
          @mouseenter="activeIndex = index"
        >
          {{ option.label }}
        </li>
      </ul>
    </Transition>
  </div>
</template>

<script setup>
import { ref, computed, useId, onMounted, onUnmounted, watch, inject } from 'vue'

// 부모 v-model과 자동으로 동기화
const model = defineModel({
  type: [String, Number, Object, null],
  default: '',
})

const props = defineProps({
  name: { type: String, default: () => `select-${useId()}` },
  options: {
    type: Array,
    default: () => [],
  },
  placeholder: { type: String, default: '선택해주세요' },
  disabled: Boolean,
  error: Boolean,
})

// 이벤트 등록
const emit = defineEmits(['change'])

/** [ID & 접근성] FormGroup과의 연결 */
const injectedId = inject('form-group-id', null)
const ariaLabelledby = computed(() => injectedId || undefined)

const isOpen = ref(false)
const selectRef = ref(null)
const activeIndex = ref(-1)

const selectedLabel = computed(() => {
  // props.modelValue 대신 model.value를 참조합니다.
  const option = props.options.find((opt) => opt.value === model.value)
  return option ? option.label : props.placeholder
})

const handleSelect = (option) => {
  if (option.disabled) return

  // 수동으로 emit('update:modelValue') 할 필요 없이 값만 변경하면 끝!
  model.value = option.value
  emit('change', option.value)
  isOpen.value = false
}

/** 키보드 핸들링 */
const onKeyDown = (e) => {
  if (props.disabled) return

  switch (e.key) {
    case 'ArrowDown':
      e.preventDefault()
      if (!isOpen.value) isOpen.value = true
      activeIndex.value = Math.min(activeIndex.value + 1, props.options.length - 1)
      break
    case 'ArrowUp':
      e.preventDefault()
      activeIndex.value = Math.max(activeIndex.value - 1, 0)
      break
    case 'Enter':
      e.preventDefault()
      if (isOpen.value && activeIndex.value !== -1) {
        handleSelect(props.options[activeIndex.value])
      } else {
        isOpen.value = !isOpen.value
      }
      break
    case 'Escape':
    case 'Tab':
      isOpen.value = false
      break
  }
}

watch(isOpen, (newVal) => {
  if (!newVal) activeIndex.value = -1
})

const handleClickOutside = (event) => {
  if (selectRef.value && !selectRef.value.contains(event.target)) {
    isOpen.value = false
  }
}

onMounted(() => document.addEventListener('click', handleClickOutside))
onUnmounted(() => document.removeEventListener('click', handleClickOutside))

const rootClasses = computed(() => [
  'select-root',
  { 'is-open': isOpen.value, 'is-disabled': props.disabled, 'has-error': props.error },
])
</script>

<style lang="scss" scoped>
.select-root {
  --select-side-padding: #{rem(8px)};
  --select-focus-border-color: var(--color-gray-900);

  position: relative;
  width: 100%;
  min-width: rem(130px);
  font-size: var(--font-size-14);
  font-weight: var(--font-weight-medium);
  line-height: var(--line-height-20);
  .select-trigger {
    @include flex($h: space-between, $v: center);
    width: 100%;
    height: rem(32px);
    padding: 0 var(--select-side-padding);
    border: var(--border-width-base) solid var(--color-gray-400);
    background: var(--color-background);
    border-radius: var(--border-radius-base);
    cursor: pointer;

    &:focus {
      border-color: var(--select-focus-border-color);
      // border-bottom-left-radius: 0;
      // border-bottom-right-radius: 0;
    }
    &:disabled {
      cursor: not-allowed;
      background: var(--color-bg-disabled);
    }
    .selected-text.is-placeholder {
      color: var(--color-gray-900);
    }
  }
  .select-options {
    position: absolute;
    top: 100%;
    left: 0;
    width: 100%;
    margin-top: rem(-2px);
    padding: 0;
    list-style: none;
    background: var(--color-background);
    border: rem(2px) solid var(--color-gray-400);
    border-top: none;
    border-radius: var(--border-radius-base);
    z-index: 10;
    max-height: rem(164px);
    overflow-y: auto;
    border-color: var(--select-focus-border-color);
  }

  // 옵션 요소
  .select-option {
    @include flex($v: center);
    padding: rem(4px) var(--select-side-padding);
    min-height: rem(32px);
    cursor: pointer;

    &:hover:not(.is-disabled),
    &.is-active:not(.is-disabled) {
      background: var(--color-bg-disabled);
    }

    &.is-selected {
      background: var(--color-bg-gray);
    }
    &.is-disabled {
      color: var(--color-gray-150);
      cursor: not-allowed;
    }
  }

  &.is-open {
    .select-trigger {
      border-width: rem(2px);
      border-color: var(--color-gray-900);
      border-radius: var(--border-radius-base) var(--border-radius-base) 0 0;
    }
    .select-options {
      border-color: var(--color-gray-900);
      border-radius: 0 0 var(--border-radius-base) var(--border-radius-base);
    }
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
