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
      :aria-label="triggerAriaLabel"
      @click.stop="toggleSelect"
    >
      <span :class="['selected-text', { 'is-placeholder': !props.modelValue }]">
        {{ selectedLabel }}
      </span>
      <span class="select-arrow" aria-hidden="true">
        <MsfIcon v-if="!isOpen && !props.selectPop" name="arrowDown" />
        <MsfIcon v-if="isOpen && !props.selectPop" name="arrowUp" />
        <MsfIcon v-if="props.selectPop" name="arrowDown" />
      </span>
    </button>
    <Transition v-if="!props.selectPop" :name="isDropUp ? 'slide-up' : 'slide-down'">
      <ul v-if="isOpen" class="select-options" role="listbox">
        <li
          v-for="(option, index) in filteredOptions"
          :key="option.value || 'guide-key'"
          role="option"
          :tabindex="option.disabled ? -1 : 0"
          :aria-selected="
            option.value === props.modelValue || (option.isGuide && !props.modelValue)
          "
          :class="[
            'select-option',
            {
              'is-selected':
                option.value === props.modelValue || (option.isGuide && !props.modelValue),
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
          <template
            v-for="(part, partIndex) in getHighlightedLabel(option.label)"
            :key="`${index}-${partIndex}`"
          >
            <mark v-if="part.highlight" class="search-highlight">
              {{ part.text }}
            </mark>
            <span v-else>{{ part.text }}</span>
          </template>
        </li>
      </ul>
    </Transition>
    <MsfDialog
      v-if="props.selectPop"
      size="small"
      :isOpen="isOpen"
      :title="title"
      showClose
      @close="isOpen = false"
      className="select-dialog"
    >
      <!-- <template #navBar> : 상단 고정 필요시 -->
      <!-- selectPopYn : 검색필터 -->
      <div v-if="props.selectPopYn" class="pop-search-box" @click.stop @keydown.stop>
        <MsfInput
          v-model="searchQuery"
          :placeholder="searchPlaceholder"
          @keydown.enter="handleSearchEnter"
        >
          <template #left-slot>
            <MsfIcon name="searchIcon" size="medium" class="search-icon" />
          </template>
        </MsfInput>
        <!-- 필터 검색결과없음 -->
        <div
          v-if="searchQuery.trim() && filteredOptions.length === 0"
          class="pop-empty"
          role="presentation"
        >
          <div class="pop-empty-tit">
            <em class="ut-weight-inherit">일치하는 검색 결과가 없어요</em>
            <p>다른 키워드를 입력해 주세요</p>
          </div>
        </div>
      </div>
      <!-- // selectPopYn : 검색필터 -->
      <!-- </template> : 상단 고정 필요시 -->

      <!-- 옴션 목록 -->
      <ul class="pop-list" role="listbox">
        <li
          v-for="(option, index) in filteredOptions"
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
          <span class="label-text">
            <template
              v-for="(part, partIndex) in getHighlightedLabel(option.label)"
              :key="`${index}-${partIndex}`"
            >
              <mark v-if="part.highlight" class="search-highlight">
                {{ part.text }}
              </mark>
              <span v-else>{{ part.text }}</span>
            </template>
          </span>
          <!-- <MsfIcon name="arrowRight" /> -->
        </li>
      </ul>
      <!-- // 옴션 목록 -->
    </MsfDialog>
  </div>
</template>

<script setup>
import { ref, computed, useId, onMounted, onUnmounted, watch, nextTick, useAttrs } from 'vue'
import { getCommonCodeListWithDetail } from '@/libs/utils/comn.utils'
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
  selectPop: { type: Boolean, default: true }, // 팝업 모드 활성화 여부 원복
  selectPopYn: { type: Boolean, default: false }, // 옵션 검색창 활성화 여부 추가
  title: { type: String, default: '선택' }, // 팝업 상단 타이틀
  inline: Boolean, // 인라인 스타일 여부
  groupCode: { type: String, default: '' }, // 공통코드 그룹코드
  isFull: { type: Boolean, default: false }, // 전체 라인 스타일 여부
  allChecked: { type: [Boolean, String], default: false }, // allChecked의 값이 있으면 옵션의 첫번째 전체 항목으로 노출함
  disabledItems: { type: Array, default: () => [] }, // 비활성화할 옵션의 value 배열
  hiddenItems: { type: Array, default: () => [] }, // 숨김할 옵션의 value 배열
  ariaLabel: { type: String, default: undefined }, // 버튼 접근성 레이블 직접 설정
})

// 부모에게 전달할 이벤트
const emit = defineEmits(['update:modelValue', 'change', 'select'])

const optionList = ref(props.options)

// 현재 선택된 option 존재 여부
const hasSelectedOption = computed(() => {
  return displayOptions.value.some((option) => option.value === props.modelValue)
})

// trigger 접근성 레이블 설정
const triggerAriaLabel = computed(() => {
  // 1. 외부에서 직접 지정한 ariaLabel이 최우선
  if (props.ariaLabel) return props.ariaLabel

  // 2. 기본 제목 설정
  const title = props.title || props.placeholder || '선택'

  // 3. 선택된 항목이 없을 때: title이 이미 '선택'으로 끝나면 그대로 사용
  if (!hasSelectedOption.value) {
    return title.endsWith('선택') ? title : `${title} 선택`
  }

  // 4. 선택된 항목이 있을 때: '업종 선택' → '업종: 제조업' 형태로 읽히도록 처리
  const labelTitle = title.endsWith('선택') ? title.replace(/선택$/, '').trim() : title
  return `${labelTitle}: ${selectedLabel.value}`
})

const isOpen = ref(false)
const isDropUp = ref(false)
const selectRef = ref(null)
const triggerRef = ref(null)
const activeIndex = ref(-1)

// allChecked 시 '전체' 항목 값 정의(또는 null, 'all' 등 프로젝트 규칙에 맞춰 설정)
const ALL_VALUE = ''

// 텍스트 끝에 '전체'가 없으면 추가하는 헬퍼
const formatAllText = (text) => {
  if (!text) return '전체'
  return text.endsWith('전체') ? text : `${text} 전체`
}

const displayOptions = computed(() => {
  const options = optionList.value
  if (props.allChecked) {
    // 헬퍼를 사용하여 어떤 값이 오든 '전체' 텍스트를 보장합니다.
    const guideLabel =
      typeof props.allChecked === 'string'
        ? formatAllText(props.allChecked)
        : formatAllText(props.placeholder)

    options.unshift({
      label: guideLabel,
      value: ALL_VALUE,
      isGuide: true,
    })
  }
  return options
})

const searchQuery = ref('')

// 검색 필터용 placeholder 생성
const searchPlaceholder = computed(() => {
  const label = (props.title || props.placeholder).replace(/\s*선택$/, '').trim()

  return label ? `${label} 검색` : '검색어 입력'
})

const filteredOptions = computed(() => {
  if (!props.selectPopYn) return displayOptions.value

  const query = searchQuery.value.trim().toLowerCase()
  if (!query) return displayOptions.value

  return displayOptions.value.filter((option) => {
    return (option.label || '').toLowerCase().includes(query)
  })
})

const escapeRegExp = (text) => {
  return text.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')
}

const getHighlightedLabel = (label) => {
  const text = String(label ?? '')
  const query = searchQuery.value.trim()

  if (!query) {
    return [
      {
        text,
        highlight: false,
      },
    ]
  }

  const regex = new RegExp(`(${escapeRegExp(query)})`, 'gi')

  return text
    .split(regex)
    .filter(Boolean)
    .map((part) => ({
      text: part,
      highlight: part.toLowerCase() === query.toLowerCase(),
    }))
}

const handleSearchEnter = (e) => {
  if (e.isComposing) {
    e.preventDefault()
    return
  }
  e.preventDefault()
}

const selectedLabel = computed(() => {
  // displayOptions를 참조하여 '전체' 가이드 항목까지 포함해서 검색합니다.
  if (!Array.isArray(displayOptions.value)) return props.placeholder

  const option = displayOptions.value.find((opt) => opt.value === props.modelValue)

  // 값이 ALL_VALUE('')일 때 '전체' 라벨을 정상적으로 반환하거나, 없으면 placeholder를 반환합니다.
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
  // disabled, readonly 상태일 때 열리지 않도록 설정
  if (props.disabled || props.readonly) return
  isOpen.value = !isOpen.value
}

const handleSelect = (option) => {
  if (option.disabled) return
  emit('update:modelValue', props.isFull ? option : option.value)
  emit('change', props.isFull ? option : option.value)
  emit('select', option)
  isOpen.value = false

  // 팝업 모드일 때는 포커스 복구가 Dialog 내부 FocusTrap에 의해 처리됨
  if (!props.selectPop) {
    triggerRef.value?.focus()
  }
}

// 키보드 핸들링
const onKeyDown = (e) => {
  if (props.disabled) return

  // 기준 리스트를 filteredOptions로 변경
  const targetList = filteredOptions.value
  const maxIdx = targetList.length - 1

  switch (e.key) {
    case 'ArrowDown':
      e.preventDefault()
      if (!isOpen.value) isOpen.value = true
      activeIndex.value = Math.min(activeIndex.value + 1, maxIdx)
      break
    case 'ArrowUp':
      e.preventDefault()
      activeIndex.value = Math.max(activeIndex.value - 1, 0)
      break
    case 'Enter':
      if (isOpen.value && activeIndex.value !== -1) {
        handleSelect(targetList[activeIndex.value]) // 현재 활성화된 옵션 선택
      } else {
        isOpen.value = !isOpen.value
      }
      e.preventDefault()
      break
    case 'Escape':
      isOpen.value = false
      triggerRef.value?.focus()
      break
  }
}

watch(isOpen, (newVal) => {
  if (!newVal) {
    activeIndex.value = -1
    searchQuery.value = ''
  } else {
    calculateDirection()
  }
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
  getCommonCodeListWithDetail(groupCode).then((list) => {
    optionList.value = list
      ?.filter((v) => (props.hiddenItems ? !props.hiddenItems.includes(v.code) : true))
      .map((item) => {
        const val =
          groupCode === 'CRD' && item.detail?.etcValue1 ? item.detail.etcValue1 : item.code
        return {
          value: val,
          label: item.title,
          ...item,
          disabled: props.disabledItems ? props.disabledItems.includes(val) : false,
        }
      })
    console.table(optionList.value)
  })
}

watch(
  () => [props.options, props.groupCode],
  ([newOptions, newGroupCode]) => {
    if (newOptions && newOptions.length > 0) {
      optionList.value = newOptions
    } else if (!isEmpty(newGroupCode)) {
      getOptionsByGroupCode(newGroupCode)
    } else {
      optionList.value = []
    }
  },
  { immediate: true, deep: true },
)

onMounted(() => {
  if (props.allChecked && !props.modelValue) {
    emit('update:modelValue', ALL_VALUE)
  }
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
const isDisabled = computed(() => props.disabled)
const isReadonly = computed(() => props.readonly)

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

const focus = () => {
  triggerRef.value?.focus()
}

defineExpose({
  focus,
})
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
  // 버튼의 readonly 스타일 설정
  &.is-readonly {
    .select-trigger {
      background: var(--color-bg-disabled);
      &:focus {
        border-color: var(--select-default-border-color);
      }
    }
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
      .selected-text,
      .selected-text.is-placeholder {
        color: var(--color-text-disabled);
      }
      color: var(--color-text-disabled);
      border-color: var(--color-line-disabled);
    }
    .selected-text {
      flex: 1;
      min-width: 0;
      text-align: left;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      color: var(--color-gray-900);
      font-weight: var(--font-weight-medium);
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
      border-width: 1px;
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
  padding: rem(8px) rem(12px);
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

// 검색창 관련 스타일 추가
.pop-search-box {
  & + ul.pop-list {
    padding-top: rem(8px);
    .pop-item {
      padding-inline: rem(12px);
    }
  }
  .search-icon {
    --icon-color: var(--color-gray-400);
  }
}
// 검색어 하이라이트
.search-highlight {
  padding: 0;
  margin: 0;
  background: transparent;
  color: var(--color-accent2-base);
  font-weight: var(--font-weight-medium);
}
// 필터 검색결과없음
.pop-empty {
  width: 100%;
  min-height: rem(180px);
  text-align: center;
  font-size: var(--font-size-16);
  font-weight: var(--font-weight-medium);
  color: var(--color-gray-600);
  @include flex($d: column, $h: center) {
    gap: rem(16px);
  }
}
</style>
