<template>
  <div :class="rootClasses" v-bind="rootAttrs" @click="props.displayMask && inputRef?.focus()">
    <!-- displayMask 설정 시 레이어 -->
    <div v-if="props.displayMask" class="visual-layer" aria-hidden="true">
      <div class="visual-wrapper" :class="[`is-align-${props.align}`]">
        <template v-if="model">
          <span v-for="i in String(model).length" :key="i" class="dot">
            {{ props.displayMaskChar }}
          </span>
        </template>
        <span v-if="isFocus" class="custom-cursor"></span>
      </div>
    </div>
    <div class="left-slot" v-if="$slots['left-slot']"><slot name="left-slot"></slot></div>
    <input
      ref="inputRef"
      :style="{ textAlign: props.align }"
      v-bind="inputAttrs"
      v-model="model"
      :id="inputId"
      :aria-invalid="props.error"
      :aria-label="computedAriaLabel"
      :data-focus-scope-skip-enter="hasExternalKeyHandler ? '' : undefined"
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
    <div class="action-slot" v-if="showClearBtn || showRevealBtn || $slots['right-slot']">
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
      <button
        v-if="showRevealBtn"
        type="button"
        class="btn-reveal"
        :aria-label="isRevealed ? '비밀번호 숨기기' : '비밀번호 보이기'"
        :aria-pressed="isRevealed"
        :class="{ 'is-visible': isFocus }"
        @click="handleReveal"
      >
        <MsfIcon :name="isRevealed ? 'eyeOff' : 'eyeOn'" size="large" />
      </button>
      <slot name="right-slot"></slot>
    </div>
  </div>
</template>

<script setup>
import {
  useAttrs,
  inject,
  computed,
  useId,
  ref,
  nextTick,
  onMounted,
  onBeforeUnmount,
  watch,
} from 'vue'
import { useMsfFormControlLabel } from '@/hooks/useMsfFormGroupLabel'

const model = defineModel({ type: [String, Number], default: '' })

const props = defineProps({
  /** 스타일 */
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
  /** 외부 전달 ID */
  id: { type: String, default: undefined }, //
  /** 지우기 버튼 표시 여부 */
  clearable: { type: Boolean, default: true },
  /** type="password"일 때 비밀번호 보이기/숨기기 버튼 사용 여부 */
  reveal: {
    type: Boolean,
    default: false,
  },
  /** 에러 */
  error: Boolean,
  /** FormGroup 자동 검증용 유효성 */
  isValid: { type: Boolean, default: true },
  /** 인라인 스타일 여부 */
  inline: Boolean,
  /** 접근성 aria-label설정필요시 사용 */
  ariaLabel: { type: String, default: undefined },
  /** 입력값은 그대로 두고, 화면상에서 글자를 가림 (중간수정방지, 복사금지, 단방향 입력 보장, type="text") */
  displayMask: { type: Boolean, default: false },
  /** displayMask가 true일 때 화면에 표시할 문자 (기본 '●') */
  displayMaskChar: { type: String, default: '●' },
})

// 부모가 사용할 이벤트선언
const emit = defineEmits(['input', 'focus', 'blur'])

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
  const rest = {
    // 기본값은 off로 두고, password 타입만 new-password로 설정하며 부모 autocomplete가 있으면 우선 적용됨
    autocomplete: attrs.type === 'password' ? 'new-password' : 'off',
    ...attrs,
  }
  delete rest.class
  delete rest.style

  // reveal은 password 전용 옵션으로, 클릭 상태에 따라 실제 input type을 변경
  if (props.reveal && attrs.type === 'password') {
    rest.type = isRevealed.value ? 'text' : 'password'
  }

  return rest
})

// 외부에서 전달된 키 이벤트가 있으면 MsfFocusScope의 Enter 자동 이동을 건너뛴다.
const hasExternalKeyHandler = computed(() => {
  return Object.keys(attrs).some((key) => /^on(Keydown|Keyup|Keypress|Enter)/.test(key))
})

// ID 결정
const injectedId = inject('form-group-id', null) // MsfInput의 실제 id 연결은 useMsfFormControlLabel(inputId)가 담당
const fallbackId = useId()

// 1순위: 직접 넣은 ID / 2순위: 고유 ID 생성
// 부모가 준 injectedId는 중복 id 방지를 위해 inputId로 직접 사용하지 않음
// FormGroup label은 useMsfFormControlLabel에 등록된 실제 inputId를 따라감
const inputId = computed(() => props.id || fallbackId)

// FormGroup에 실제 input id 등록
const { formGroupLabelContext } = useMsfFormControlLabel(inputId)

// 현재 iput의 상태 파악
const isDisabled = computed(() => attrs.disabled !== undefined && attrs.disabled !== false)
const isReadonly = computed(() => attrs.readonly !== undefined && attrs.readonly !== false)

// FormGroup 검증 컨텍스트
const formGroupContext = inject('msf-form-group-context', null)

// MsfFormGroup에 전달할 현재 input 상태
const getInputState = () => {
  const value = String(model.value ?? '').trim()
  const maxlength = Number(attrs.maxlength || attrs.maxLength || 0)

  return {
    id: inputId.value,
    value,
    empty: value === '',
    disabled: isDisabled.value,
    readonly: isReadonly.value,
    valid: props.isValid,
    validLength: maxlength ? value.length >= maxlength : true,
  }
}

// FormGroup에 현재 input 상태 반영
const syncInputState = () => {
  formGroupContext?.updateInput?.(inputId.value, getInputState())
}
// FormGroup에 input 등록
onMounted(() => {
  formGroupContext?.registerInput?.(getInputState())
})
// FormGroup에서 input 해제
onBeforeUnmount(() => {
  formGroupContext?.unregisterInput?.(inputId.value)
})
// 외부 v-model 변경, maxlength 변경 등도 반영
watch(
  () => [
    model.value,
    props.isValid,
    attrs.maxlength,
    attrs.maxLength,
    isDisabled.value,
    isReadonly.value,
  ],
  () => {
    // 외부에서 값이 초기화된 경우 다시 숨김
    if (!model.value) {
      isRevealed.value = false
    }
    syncInputState()
  },
)

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
  model.value = newValue

  emit('input', event)
  syncInputState()

  if (props.displayMask) moveCursorToEnd()
}

// 입력값 초기화
const inputRef = ref(null)
const handleClear = () => {
  model.value = ''
  isRevealed.value = false // 값을 지우면 비밀번호도 다시 숨김

  emit('input', { target: { value: '' } })

  // 지운 후 인풋으로 포커스 돌려주기 (키보드 사용자 배려)
  nextTick(() => {
    syncInputState()
    inputRef.value?.focus()
  })
}

// 지우기 버튼 노출 조건
const showClearBtn = computed(() => {
  return props.clearable && model.value && !isDisabled.value && !isReadonly.value
})

// 비밀번호 숨김/보임 버튼 노출 조건
const showRevealBtn = computed(() => {
  return props.reveal && attrs.type === 'password' && !isDisabled.value && !isReadonly.value
})

// 비밀번호 표시 상태
const isRevealed = ref(false)
// 비밀번호 보기/숨기기
const handleReveal = () => {
  isRevealed.value = !isRevealed.value
}

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
    const len = String(model.value).length
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
      const len = String(model.value).length
      inputRef.value.setSelectionRange(len, len)
    }
  })
}

// 접근성 ariaLabel
const computedAriaLabel = computed(() => {
  // 1. 직접 정의한 ariaLabel이 최우선(placeholder와 의미가 맞지않을때 직접디테일하게 지정)
  if (props.ariaLabel) return props.ariaLabel

  // 2. FormGroup label과 실제 연결된 input이면 중복 방지
  if (formGroupLabelContext?.labelFor?.value === inputId.value) return undefined

  // 3. 기존 방식 호환
  if (!formGroupLabelContext && injectedId && inputId.value === injectedId) return undefined

  // 4. placeholder 처리
  const placeholder = attrs.placeholder
  if (!placeholder) return undefined

  // "입력"이라는 단어가 이미 들어가 있는지 확인 (포함되어 있으면 그대로 사용)
  const hasInputKeyword = placeholder.includes('입력')
  return hasInputKeyword ? placeholder : `${placeholder} 입력`
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
    &.is-focus {
      border-color: var(--color-primary-base);
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

.btn-reveal {
  position: relative;
  display: block;
  line-height: 1;
  color: var(--color-gray-400);
}
// input, X, 눈 버튼 중 하나에 포커스가 있으면 X 표시
.input-root:focus-within {
  .btn-clear {
    display: block;
  }
}
// 필드 오른쪽 슬롯
.action-slot {
  position: relative;
  @include flex($v: center) {
    gap: var(--spacing-x4);
  }
}
// 필드 왼쪽 슬롯
.left-slot {
  position: relative;
  @include flex($v: center) {
    gap: var(--spacing-x4);
  }
}

// props: displayMask 설정시 스타일
.input-root.has-display-mask {
  --display-mask-inset-x: #{rem(16px)}; // 입력폼 좌우 패딩

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
    inset: 0 var(--display-mask-inset-x);
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
  &.input-underline {
    --display-mask-inset-x: rem(8px);
  }
}
// 커서 blink 효과
@keyframes blink {
  50% {
    opacity: 0;
  }
}
</style>
