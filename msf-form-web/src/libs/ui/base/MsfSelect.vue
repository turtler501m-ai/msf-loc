<template>
  <div ref="selectRef" v-bind="rootAttrs" :class="rootClasses" @keydown="onKeyDown">
    <button
      ref="triggerRef"
      v-bind="targetAttrs"
      type="button"
      class="select-trigger"
      :disabled="disabled"
      :readonly="readonly"
      :error="error"
      aria-haspopup="listbox"
      :aria-expanded="isOpen"
      :aria-labelledby="ariaLabelledby"
      @click.stop="toggleSelect"
    >
      <span :class="['selected-text', { 'is-placeholder': !props.modelValue }]">
        {{ selectedLabel }}
      </span>
      <span class="select-arrow" aria-hidden="true">
        <MsfIcon v-if="!isOpen && !selectPop" name="arrowDown" />
        <MsfIcon v-if="isOpen && !selectPop" name="arrowUp" />
        <MsfIcon v-if="selectPop" name="arrowDown" />
      </span>
    </button>
    <Transition v-if="!selectPop" :name="isDropUp ? 'slide-up' : 'slide-down'">
      <ul v-if="isOpen" class="select-options" role="listbox">
        <li
          v-for="(option, index) in options"
          :key="option.value"
          role="option"
          :tabindex="option.disabled ? -1 : 0"
          :aria-selected="option.value === props.modelValue"
          :class="[
            'select-option',
            {
              'is-selected': option.value === props.modelValue,
              'is-disabled': option.disabled,
              'is-active': activeIndex === index,
            },
          ]"
          @click.stop="handleSelect(option)"
          @mouseenter="activeIndex = index"
          @focus="activeIndex = index"
          @keydown.enter.stop.prevent="handleSelect(option)"
          @keydown.space.stop.prevent="handleSelect(option)"
        >
          {{ option.label }}
        </li>
      </ul>
    </Transition>
    <MsfDialog
      v-if="selectPop"
      size="small"
      :isOpen="isOpen"
      :title="title"
      showClose
      @close="isOpen = false"
      className="select-dialog"
    >
      <ul class="pop-list" role="listbox">
        <li
          v-for="(option, index) in optionList"
          :key="option.value"
          role="option"
          :tabindex="option.disabled ? -1 : 0"
          :aria-selected="option.value === props.modelValue"
          :class="[
            'pop-item',
            {
              'is-selected': option.value === props.modelValue,
              'is-disabled': option.disabled,
              'is-active': activeIndex === index, // 키보드 탐색 시각화용
            },
          ]"
          @click="handleSelect(option)"
          @keydown.enter.prevent="handleSelect(option)"
          @keydown.space.prevent="handleSelect(option)"
          @mouseenter="activeIndex = index"
        >
          <span class="label-text">{{ option.label }}</span>
          <MsfIcon name="arrowRight" />
        </li>
      </ul>
    </MsfDialog>
  </div>
</template>

<script setup>
import {
  ref,
  computed,
  useId,
  onBeforeMount,
  onMounted,
  onUnmounted,
  watch,
  inject,
  nextTick,
  useAttrs,
} from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { isEmpty } from '@/libs/utils/string.utils'

// 속성에 접근
const attrs = useAttrs()
// 부모(root)에 바로 상속되지 않도록 설정
defineOptions({ inheritAttrs: false })

// root에 부여할 속성
const rootAttrs = computed(() => ({
  class: attrs.class,
  style: attrs.style,
}))
// target에 부여할 속성
const targetAttrs = computed(() => {
  const rest = { ...attrs }
  delete rest.class
  delete rest.style
  return rest
})

const props = defineProps({
  modelValue: [String, Number, Object, null],
  options: {
    type: Array,
    default: () => [],
  },
  placeholder: { type: String, default: '선택' }, //설계 기본값
  disabled: Boolean,
  readonly: Boolean,
  error: Boolean,
  name: { type: String, default: () => `select-${useId()}` },
  selectPop: { type: Boolean, default: true }, // 팝업 모드 활성화 여부
  title: { type: String, default: '선택' }, // 팝업 상단 타이틀
  inline: Boolean, // 인라인 스타일 여부
  groupCode: { type: String, default: '' }, // 공통코드 그룹코드
})

// 부모에게 전달할 이벤트
const emit = defineEmits(['update:modelValue', 'change'])

const optionList = ref(props.options)

// [ID & 접근성] FormGroup과의 연결
const injectedId = inject('form-group-id', null)
const ariaLabelledby = computed(() => injectedId || undefined)

const isOpen = ref(false)
const isDropUp = ref(false)
const selectRef = ref(null)
const triggerRef = ref(null)
const activeIndex = ref(-1)

const selectedLabel = computed(() => {
  // props.modelValue를 직접 참조
  const option = optionList.value.find((opt) => opt.value === props.modelValue)
  return option ? option.label : props.placeholder
})

const calculateDirection = async () => {
  if (!isOpen.value || props.selectPop) return // 팝업 모드일 때는 방향 계산 불필요
  await nextTick()
  if (!triggerRef.value) return

  const rect = triggerRef.value.getBoundingClientRect()
  const dropdownHeight = 200
  const margin = 10

  isDropUp.value =
    window.innerHeight - rect.bottom < dropdownHeight + margin && rect.top > dropdownHeight + margin
}

const toggleSelect = () => {
  if (props.disabled) return
  isOpen.value = !isOpen.value
}

const handleSelect = (option) => {
  if (option.disabled) return
  emit('update:modelValue', option.value)
  emit('change', option.value)
  isOpen.value = false

  // 팝업 모드일 때는 포커스 복구가 Dialog 내부 FocusTrap에 의해 처리됨
  if (!props.selectPop) {
    triggerRef.value?.focus()
  }
}

// 키보드 핸들링
const onKeyDown = (e) => {
  if (props.disabled) return

  switch (e.key) {
    case 'ArrowDown':
      e.preventDefault()
      if (!isOpen.value) isOpen.value = true
      activeIndex.value = Math.min(activeIndex.value + 1, optionList.value.length - 1)
      break
    case 'ArrowUp':
      e.preventDefault()
      activeIndex.value = Math.max(activeIndex.value - 1, 0)
      break
    case 'Enter':
      if (isOpen.value && activeIndex.value !== -1) return
      e.preventDefault()
      isOpen.value = !isOpen.value
      break
    case 'Escape':
      isOpen.value = false
      triggerRef.value?.focus()
      break
  }
}

watch(isOpen, (newVal) => {
  if (!newVal) activeIndex.value = -1
  else calculateDirection()
})

const handleClickOutside = (event) => {
  if (props.selectPop) return // 팝업 모드는 Dialog 자체에서 외부클릭 처리
  if (selectRef.value && !selectRef.value.contains(event.target)) {
    isOpen.value = false
  }
}

const getOptionsByGroupCode = (groupCode) => {
  if (props.options?.length > 0) return props.options
  if (isEmpty(groupCode)) return []
  getCommonCodeList(groupCode).then((list) => {
    console.log('list:', list)
    optionList.value = list.map((item) => ({ value: item.code, label: item.title }))
  })
}

watch(
  () => props.options,
  (newOptions) => {
    if (newOptions?.length > 0) {
      optionList.value = newOptions
    } else {
      getOptionsByGroupCode(props.groupCode)
    }
  },
  { immediate: true, deep: true },
)
watch(
  () => props.groupCode,
  (newGroupCode) => {
    if (isEmpty(newGroupCode)) return

    getOptionsByGroupCode(newGroupCode)
  },
  { immediate: true },
)

onBeforeMount(() => {
  getOptionsByGroupCode(props.groupCode)
})

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
  window.addEventListener('scroll', calculateDirection, true)
  window.addEventListener('resize', calculateDirection)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
  window.removeEventListener('scroll', calculateDirection, true)
  window.removeEventListener('resize', calculateDirection)
})

// 현재 Button의 상태 파악
const isDisabled = computed(() => attrs.disabled !== undefined && attrs.disabled !== false)
const isReadonly = computed(() => attrs.readonly !== undefined && attrs.readonly !== false)

// 루트 클래스 설정
const rootClasses = computed(() => [
  'select-root',
  {
    'is-open': isOpen.value,
    'is-disabled': isDisabled.value,
    'is-readonly': isReadonly.value,
    'has-error': props.error,
    'is-drop-up': isDropUp.value,
    'is-select-pop': props.selectPop,
    'is-inline': props.inline,
  },
])
</script>

<style lang="scss" scoped>
.select-root {
  --select-side-padding: #{rem(16px)};
  --select-focus-border-color: var(--color-gray-900); // 포커스 보더컬러
  --select-default-border-color: var(--color-gray-400); // 기본 보더컬러
  --select-row-height: #{rem(52px)};
  --select-option-count: 3;

  position: relative;
  width: 100%;
  min-width: rem(140px);
  font-size: var(--font-size-16);
  font-weight: var(--font-weight-medium);
  line-height: var(--line-height-heading);
  &.is-inline {
    display: inline-flex;
    width: rem(140px);
  }
  .select-trigger {
    @include flex($h: space-between, $v: center);
    width: 100%;
    height: var(--select-row-height);
    padding: 0 var(--select-side-padding);
    border: var(--border-width-base) solid var(--select-default-border-color);
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
      .selected-text.is-placeholder {
        color: var(--color-text-disabled);
      }
      color: var(--color-text-disabled);
      border-color: var(--color-line-disabled);
    }
    &.is-readonly {
      cursor: not-allowed;
      background: var(--color-bg-disabled);
    }
    .selected-text {
      flex: 1;
      min-width: 0;
      text-align: left;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      &.is-placeholder {
        color: var(--color-gray-900);
      }
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
    border: rem(2px) solid var(--select-default-border-color);
    border-top: none;
    border-radius: var(--border-radius-base);
    z-index: 10;
    max-height: rem(164px);
    max-height: calc(var(--select-row-height) * var(--select-option-count));
    overflow-y: auto;
  }

  // 옵션 요소
  .select-option {
    @include flex($v: center);
    padding: rem(4px) var(--select-side-padding);
    min-height: var(--select-row-height);
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
      border-color: var(--select-focus-border-color);
      border-radius: var(--border-radius-base) var(--border-radius-base) 0 0;
    }
    .select-options {
      border-color: var(--select-focus-border-color);
      border-radius: 0 0 var(--border-radius-base) var(--border-radius-base);
    }
    // 선택 팝업의 경
    &.is-select-pop {
      .select-trigger {
        border-radius: var(--border-radius-base);
      }
    }
  }
  &.has-error {
    .select-trigger {
      border-color: var(--color-accent-alert);
      box-shadow: inset 0 0 0 1px var(--color-accent-alert);
    }
    .select-options {
      border-color: var(--color-accent-alert);
    }
  }
}

// Dialog 내부 리스트 스타일
.pop-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.pop-item {
  min-height: rem(52px);
  padding: rem(8px) rem(16px);
  padding-right: rem(14px);
  cursor: pointer;
  @include flex($v: center, $h: space-between) {
    gap: rem(16px);
  }
  .label-text {
    flex: 1;
    color: var(--color-gray-600);
  }
  .msf-icon {
    flex-shrink: 0;
    flex-grow: 0;
    color: var(--color-gray-400);
  }
  border-bottom: var(--border-width-base) solid var(--color-gray-75);
  font-size: var(--font-size-16);
  font-weight: var(--font-weight-medium);
  line-height: var(--line-height-heading);
  &:last-child {
    border-bottom: none;
  }
  &.is-selected {
    color: var(--color-primary-base);
    background-color: var(--color-gray-50);
  }
  &.is-disabled {
    .label-text,
    .msf-icon {
      color: var(--color-text-disabled);
    }
    cursor: not-allowed;
  }
  &:active:not(.is-disabled) {
    background-color: var(--color-gray-100);
  }
}

// 애니메이션
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
