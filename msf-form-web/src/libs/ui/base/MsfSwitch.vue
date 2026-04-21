<template>
  <div v-bind="rootAttrs" :class="rootClasses">
    <label v-if="label" :for="switchId" class="switch-label-text">
      {{ labelText }}
    </label>

    <span class="switch-control" @click="handleClickControl">
      <input
        ref="inputRef"
        v-bind="inputAttrs"
        type="checkbox"
        role="switch"
        class="switch-input"
        :id="switchId"
        :checked="modelValue"
        :aria-checked="modelValue"
        @change="handleChange"
      />
      <span class="switch-bar" role="presentation">
        <span v-if="showInnerLabel" class="switch-inner-text">
          {{ modelValue ? innerLabel[0] : innerLabel[1] }}
        </span>
        <span class="switch-handle"></span>
      </span>
    </span>
  </div>
</template>

<script setup>
import { computed, useId, useAttrs, ref, inject } from 'vue'

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  /** 스타일 */
  variant: {
    type: String,
    default: 'default',
    validator: (v) => ['default', 'point'].includes(v),
  },
  /** 사이즈 지정*/
  size: {
    type: String,
    default: 'medium',
    validator: (v) => ['small', 'medium'].includes(v),
  },
  /** 좌측 라벨 노출 필요 시 */
  label: { type: [String, Array], default: '' },
  /** 스위치 내부 텍스트 설정 */
  innerLabel: { type: Array, default: () => ['ON', 'OFF'] },
  /** 스위치 레이블 노출여부 */
  showInnerLabel: { type: Boolean, default: false },
})

// 부모가 사용할 이벤트선언
const emit = defineEmits(['update:modelValue', 'change'])

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
const switchId = computed(() => attrs.id || injectedId || useId())

const inputRef = ref(null)
// 현재 iput의 상태 파악
const isDisabled = computed(() => attrs.disabled || attrs.disabled === '')

const handleClickControl = () => {
  if (!isDisabled.value) {
    inputRef.value?.click()
  }
}

// 스타일 클래스
const rootClasses = computed(() => [
  'switch-root',
  `is-${props.variant}`,
  `is-${props.size}`,
  {
    'is-disabled': isDisabled.value,
    'is-checked': props.modelValue,
    'has-label': !!props.label,
  },
])

// 이벤트 핸들러
const handleChange = (event) => {
  emit('update:modelValue', event.target.checked)
  emit('change', event)
}

// 레이블 텍스트
const labelText = computed(() => {
  if (Array.isArray(props.label)) {
    return props.modelValue ? props.label[0] : props.label[1]
  }
  return props.label
})
</script>

<style lang="scss" scoped>
.switch-root {
  --switch-width: #{rem(96px)}; // 전체 너비 확장
  --switch-height: #{rem(48px)}; // 높이 확장
  --switch-handle-size: #{rem(48px)}; // 핸들을 바 높이보다 크게 설정
  --switch-handle-border-width: #{rem(2px)}; // 핸들 테두리 두께
  // 컬러 토큰
  --switch-bg: var(--color-gray-150); // OFF 배경색
  --switch-active-bg: var(--color-accent2-base); // ON 배경색
  --switch-handle-bg: var(--color-white);
  --switch-handle-border-color: var(--color-gray-400); // 핸들 테두리색
  // 텍스트 관련 변수
  --switch-inner-color-on: var(--color-white); // ON일 때 텍스트색
  --switch-inner-color-off: var(--color-gray-600); // OFF일 때 텍스트색 (또는 더 연한 회색)
  --switch-label-font-size: #{rem(18px)};
  --switch-inner-font-size: #{rem(18px)};

  position: relative;
  @include flex($display: inline-flex, $v: center) {
    gap: rem(10px);
  }
  z-index: 0;
  vertical-align: middle;
  // 비활성화 상태
  &.is-disabled {
    cursor: not-allowed;
    opacity: 0.6;
    .switch-control,
    .switch-label-text {
      cursor: not-allowed;
    }
  }
  // 외부 라벨 텍스트
  .switch-label-text {
    font-size: var(--switch-label-font-size);
    font-weight: var(--font-weight-medium);
    color: #333;
    cursor: pointer;
    user-select: none;
    // Checked 상태일 때 볼드 처리
    &.is-checked {
      font-weight: var(--font-weight-bold);
    }
  }
  // 스위치 본체 컨트롤
  .switch-control {
    position: relative;
    display: inline-block;
    width: var(--switch-width);
    height: var(--switch-height);
    cursor: pointer;
    flex-shrink: 0;
    .switch-input {
      @include blind();
      // [Checked 상태]
      &:checked + .switch-bar {
        --switch-handle-border-color: var(--color-primary-base);
        background-color: var(--switch-active-bg);
        .switch-handle {
          transform: translateX(calc(var(--switch-width) - var(--switch-handle-size)));
        }
        .switch-inner-text {
          left: rem(16px); // ON 텍스트 왼쪽 배치
          color: var(--switch-inner-color-on);
          font-weight: var(--font-weight-bold);
        }
      }
      &:focus-visible + .switch-bar {
        outline: 2px solid var(--color-primary-base);
        outline-offset: 2px;
      }
    }
    // 스위치 배경(Bar)
    .switch-bar {
      position: absolute;
      inset: 0;
      background-color: var(--switch-bg);
      border-radius: var(--switch-height);
      transition: background-color 0.2s cubic-bezier(0.4, 0, 0.2, 1);
      // 내부 텍스트 스타일 (ON/OFF)
      .switch-inner-text {
        position: absolute;
        top: 50%;
        right: rem(10px); // OFF 텍스트 기본 오른쪽 배치
        transform: translateY(-50%);
        font-size: var(--switch-inner-font-size);
        font-weight: var(--font-weight-medium); // ON/OFF 텍스트 더 굵게
        color: var(--switch-inner-color-off);
        line-height: var(--line-height-fit);
        pointer-events: none;
        user-select: none;
        transition: all 0.2s;
        z-index: 0; // 핸들보다 아래 배치
      }
      // 스위치 핸들(Circle)
      .switch-handle {
        position: absolute;
        // 핸들이 바 높이보다 크므로 위아래 음수 inset으로 배치
        top: calc(((var(--switch-handle-size) - var(--switch-height)) / 2) * -1);
        left: 0; // 기본 왼쪽 배치
        width: var(--switch-handle-size);
        height: var(--switch-handle-size);
        background-color: var(--switch-handle-bg);
        border: var(--switch-handle-border-width) solid var(--switch-handle-border-color);
        border-radius: var(--border-radius-max);
        transition: transform 0.2s cubic-bezier(0.4, 0, 0.2, 1);
        z-index: 1; // 텍스트보다 위에 배치
      }
    }
  }
  // 사이즈 변형 (Small)
  &.is-small {
    --switch-width: #{rem(56px)};
    --switch-height: #{rem(28px)};
    --switch-handle-size: #{rem(32px)};
    --switch-label-font-size: #{rem(14px)};
    --switch-inner-font-size: #{rem(10px)};

    .switch-inner-text {
      display: none;
    } // 스몰 사이즈에선 내부 텍스트 생략 추천
  }
  /* 변형 (Point) */
  &.is-point {
    --switch-active-bg: var(--color-point, #ff4d4f);
  }
}
</style>
