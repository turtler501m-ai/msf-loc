<template>
  <div :class="rootClasses" v-bind="rootAttrs" @click="props.displayMask && inputRef?.focus()">
    <!-- displayMask 설정 시 레이어 -->
    <div v-if="props.displayMask" class="visual-layer" aria-hidden="true">
      <div class="visual-wrapper" :class="[`is-align-${props.align}`]">
        <template v-if="value">
          <span v-for="i in String(value).length" :key="i" class="dot">
            {{ props.displayMaskChar }}
          </span>
        </template>
        <span v-if="isFocus" class="custom-cursor"></span>
      </div>
    </div>
    <input
      ref="inputRef"
      :style="{ textAlign: props.align }"
      v-bind="inputAttrs"
      v-model="value"
      :id="inputId"
      :aria-invalid="props.error"
      :aria-label="computedAriaLabel"
      class="input-inner"
      @input="onInput"
      @focus="onFocus"
      @blur="onBlur"
      :class="{ 'is-display-masked-input': props.displayMask }"
      @keydown="handleKeyDown"
      @select="handleSelect"
      @mousedown="handleMaskUI"
      @copy="handleSecurity"
      @cut="handleSecurity"
      @paste="handleSecurity"
    />
    <div class="action-slot" v-if="showClearBtn || $slots.rightSlot">
      <button
        v-if="showClearBtn"
        type="button"
        class="btn-clear"
        aria-label="입력내용 지우기"
        :class="{ 'is-visible': isFocus }"
        @click="handleClear"
        @mousedown.prevent
      >
        <MsfIcon name="clear" size="large" />
      </button>
      <slot name="right-slot"></slot>
    </div>
  </div>
</template>

<script setup>
import { useAttrs, inject, computed, useId, ref, watch, nextTick } from 'vue'

const props = defineProps({
  modelValue: {
    type: [String, Number],
    default: '',
  },
  /** 인풋 스타일 */
  variant: {
    type: String,
    default: 'default',
    validator: (v) => ['default', 'underline'].includes(v),
  },
  /** 정렬 */
  align: {
    type: String,
    default: 'left',
    validator: (v) => ['left', 'center', 'right'].includes(v),
  },
  id: { type: String, default: undefined }, // 외부 전달 ID
  clearable: { type: Boolean, default: true }, //지우기 버튼 표시 여부
  error: Boolean, // 에러
  inline: Boolean, // 인라인 스타일 여부
  ariaLabel: { type: String, default: undefined }, //접근성 aria-label설정필요시 사용
  /** 입력값은 그대로 두고, 화면상에서 글자를 가림 (중간수정방지, 복사금지, 단방향 입력 보장, type="text") */
  displayMask: { type: Boolean, default: false },
  /** displayMask가 true일 때 화면에 표시할 문자 (기본 '●') */
  displayMaskChar: { type: String, default: '●' },
})

// 부모가 사용할 이벤트선언
const emit = defineEmits(['input', 'update:modelValue', 'focus', 'blur'])
const value = ref(props.modelValue)

// 속성에 접근
const attrs = useAttrs()
// 부모(root)에 바로 상속되지 않도록 설정
defineOptions({ inheritAttrs: false })

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

// ID 결정
const injectedId = inject('form-group-id', null)
// 1순워: 직접넣은 ID / 2순위: 부모간 준 ID(Label연결용) / 3순위: 고유ID생성
const inputId = computed(() => props.id || injectedId || useId())

// 현재 iput의 상태 파악
const isDisabled = computed(() => attrs.disabled !== undefined && attrs.disabled !== false)
const isReadonly = computed(() => attrs.readonly !== undefined && attrs.readonly !== false)

// 스타일 클래스
const rootClasses = computed(() => [
  'input-root',
  `input-${props.variant}`,
  {
    'is-disabled': isDisabled.value,
    'is-readonly': isReadonly.value,
    'is-error': props.error,
    'is-inline': props.inline,
    'is-focus': isFocus.value,
    'has-display-mask': props.displayMask,
  },
])

const onInput = (event) => {
  const newValue = event.target.value
  value.value = newValue

  emit('update:modelValue', newValue)
  emit('input', event)

  if (props.displayMask) moveCursorToEnd()
}

// 입력값 초기화
const inputRef = ref(null)
const handleClear = () => {
  emit('update:modelValue', '')
  emit('input', { target: { value: '' } })

  // 지운 후 인풋으로 포커스 돌려주기 (키보드 사용자 배려)
  nextTick(() => {
    inputRef.value?.focus()
  })
}

// 지우기 버튼 노출 조건
const showClearBtn = computed(() => {
  return props.clearable && value.value && !isDisabled.value && !isReadonly.value
})

// 포커스 상태 관리용
const isFocus = ref(false)

// 포커스 이벤트
const onFocus = (e) => {
  isFocus.value = true
  if (props.displayMask) moveCursorToEnd() //displayMask
  emit('focus', e)
}

// 블러 이벤트
const onBlur = (e) => {
  // 탭포커스 이동 시 버튼이 사라지기 전에 포커스가 옮겨갈 시간지연
  setTimeout(() => {
    isFocus.value = false
  }, 100)
  emit('blur', e)
}

// 키보드 제어
const handleKeyDown = (e) => {
  if (props.displayMask) {
    const forbidden = ['ArrowLeft', 'ArrowRight', 'ArrowUp', 'ArrowDown', 'Home', 'End']
    const isSelectAll = (e.ctrlKey || e.metaKey) && e.key.toLowerCase() === 'a'
    if (forbidden.includes(e.key) || isSelectAll) e.preventDefault()
  }
}
// 텍스트 선택 제어
const handleSelect = (e) => {
  if (props.displayMask) {
    // 전체 선택이 일어날 때 강제로 커서를 끝으로 이동시켜 선택을 해제함
    const len = String(value.value).length
    e.target.setSelectionRange(len, len)
  }
}

// 내부에서 막아도 외부 이벤트 핸들러는 뒤이어 정상 호출됨
// 마우스 및 UI 제어
const handleMaskUI = (e) => {
  if (!props.displayMask) return
  if (e.type === 'mousedown') {
    e.preventDefault()
    inputRef.value?.focus()
  }
  moveCursorToEnd()
}
// 보안 제어 (copy, cut, paste 통합)
const handleSecurity = (e) => {
  if (props.displayMask) {
    e.preventDefault()
  }
}

// displayMask: 커서를 항상 마지막으로 고정
const moveCursorToEnd = () => {
  if (!props.displayMask) return
  nextTick(() => {
    if (inputRef.value) {
      const len = String(value.value).length
      inputRef.value.setSelectionRange(len, len)
    }
  })
}

watch(
  () => props.modelValue,
  (newVal) => {
    if (newVal !== value.value) {
      value.value = newVal
    }
  },
)

// 접근성 ariaLabel
const computedAriaLabel = computed(() => {
  // 1. 직접 정의한 ariaLabel이 최우선(placeholder와 의미가 맞지않을때 직접디테일하게 지정)
  if (props.ariaLabel) return props.ariaLabel

  // 2. 부모 레이블(ID)과 연결된 본체 인풋이라면 중복 방지를 위해 비움
  if (injectedId && inputId.value === injectedId) return undefined

  // 3. 그 외(연결되지 않은 서브 인풋 등)에는 attrs에 부여한 placeholder를 이름으로 사용하여 대처
  return attrs.placeholder || undefined
})

// 외부 노출 메서드
defineExpose({
  focus: () => {
    inputRef.value?.focus()
  },
})
</script>

<style lang="scss" scoped>
.input-root {
  &.is-inline {
    display: inline-flex;
    width: rem(140px);
  }
  // 언더라인타입 (로그인에서 사용하는 스타일)
  &.input-underline {
    padding-inline: var(--spacing-x2);
    --input-underline-border-color: var(--color-gray-200);
    width: 100%;
    height: rem(52px);
    border: none;
    border-bottom: var(--border-width-base) solid var(--input-underline-border-color);
    font-size: var(--font-size-16);
    border-radius: 0;
    &:focus-within {
      box-shadow: none;
    }
    &.is-disabled {
      background-color: var(--color-bg-disabled);
      color: var(--color-text-disabled);
      border-color: var(--color-gray-150);
      cursor: not-allowed;
      box-shadow: none;
    }
    &.is-readonly {
      background-color: var(--color-bg-disabled);
      border-color: var(--color-gray-400);
      color: var(--color-gray-900);
      box-shadow: none;
    }
    &.is-error {
      border-color: var(--color-accent-base);
      box-shadow: none;
    }
  }
}
.btn-clear {
  position: relative;
  line-height: 1;
  color: var(--color-gray-400);
  display: none;
  // input에 포커스가 있거나, 버튼 자체에 포커스가 갔을 때 노출
  &.is-visible,
  &:focus {
    display: block;
  }
}
.action-slot {
  position: relative;
  @include flex($v: center) {
    gap: var(--spacing-x4);
  }
}
// props: displayMask 설정시 스타일
.input-root.has-display-mask {
  .input-inner {
    color: transparent !important;
    caret-color: transparent !important;
    /* 드래그 및 텍스트 선택 차단 */
    user-select: none;
    -webkit-user-select: none; /* Safari */
    -moz-user-select: none; /* Firefox */
    -ms-user-select: none; /* IE/Edge */
  }
  .visual-layer {
    user-select: none;
    -webkit-user-select: none;

    position: absolute;
    inset: 0 var(--spacing-x2);
    @include flex($v: center);
    pointer-events: none;
    z-index: 0;
    overflow: hidden;
    .visual-wrapper {
      @include flex($v: center) {
        gap: rem(2px);
      }
      width: 100%;
      min-width: max-content;
      line-height: var(--line-height-fit);
      // 텍스트가 길어질 때 왼쪽으로 밀어내는 정렬
      &[style*='justify-content: flex-start'] {
        justify-content: flex-end;
        margin-left: auto;
      }
      &[style*='justify-content: center'] {
        justify-content: center;
      }
      &[style*='justify-content: flex-end'] {
        justify-content: flex-end;
      }
      .dot {
        font-size: var(--font-size-16);
        color: var(--color-gray-900);
        flex-shrink: 0;
      }
      .placeholder-layer {
        color: #ccc;
        white-space: nowrap;
      }
      .custom-cursor {
        flex-shrink: 0;
        width: rem(1px);
        height: rem(16px);
        background-color: var(--color-accent-caret);
        animation: blink 1.1s step-start infinite;
      }
    }
  }
}
// 커서 blink 효과
@keyframes blink {
  50% {
    opacity: 0;
  }
}
</style>
