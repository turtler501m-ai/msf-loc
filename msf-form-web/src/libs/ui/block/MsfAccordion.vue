<script setup>
import { ref, computed, watch } from 'vue'

const props = defineProps({
  /** v-model로 바인딩되는 현재 열린 아이템의 ID (외부 열림제어 사용시) */
  modelValue: {
    type: [Number, Array, null],
    default: undefined,
  },
  /** 아코디언에 표시할 데이터 배열 [{id, label, content}, ...] */
  data: {
    type: Array,
    required: true,
  },
  /** 초기 렌더링 시 기본으로 열려 있을 아이템 ID */
  defaultId: {
    type: [Number, Array],
    default: () => [],
  },
  /** 여러 아이템을 동시에 열 수 있는지 여부 */
  multiple: {
    type: Boolean,
    default: false,
  },
  /** 디자인 변형 (default, faq, board 등) */
  variant: {
    type: String,
    default: 'default',
  },
  /** 레이블을 감쌀 태그 (h2, h3, span 등 접근성 고려용) */
  labelAs: {
    type: String,
    default: 'span',
  },
  /** 내외부 여백 커스텀 설정 (단위 포함 문자열 또는 숫자) */
  paddingProps: {
    type: Object,
    default: () => ({ x: null, y: null }),
  },
})

const emit = defineEmits(['update:modelValue', 'change'])

// [비제어 방식] 내부적으로 열림 상태를 관리하는 변수
const internalOpenItems = ref([])

// 초기 설정: defaultId에 따라 초기 열림 상태 할당
const init = () => {
  if (Array.isArray(props.defaultId)) {
    internalOpenItems.value = props.multiple ? [...props.defaultId] : props.defaultId.slice(0, 1)
  } else if (props.defaultId !== null && props.defaultId !== undefined) {
    internalOpenItems.value = [props.defaultId]
  }
}
init()

// 현재 컴포넌트가 v-model(외부)에 의해 제어되고 있는지 확인
const isControlled = computed(() => props.modelValue !== undefined)

// 현재 열려 있는 아이템들을 계산하여 반환 (제어/비제어 통합)
const currentOpenItems = computed(() => {
  if (isControlled.value) {
    if (props.modelValue === null) return []
    const val = Array.isArray(props.modelValue) ? props.modelValue : [props.modelValue]
    return props.multiple ? val : val.slice(0, 1)
  }
  return internalOpenItems.value
})

// 아이템의 고유 식별자를 반환 (id가 있으면 id, 없으면 index)
const getIdentity = (item, idx) => {
  return item.id !== undefined ? item.id : idx
}

// 아이템 클릭 시 토글
const toggleItem = (item, idx) => {
  const id = getIdentity(item, idx)
  const isOpen = currentOpenItems.value.includes(id)
  let nextState

  if (props.multiple) {
    nextState = isOpen
      ? currentOpenItems.value.filter((val) => val !== id)
      : [...currentOpenItems.value, id]
  } else {
    nextState = isOpen ? [] : [id]
  }

  const emitValue = props.multiple ? nextState : (nextState[0] ?? null)

  if (isControlled.value) {
    emit('update:modelValue', emitValue)
  } else {
    internalOpenItems.value = nextState
  }
  emit('change', emitValue)
}

// 스타일 클래스
const rootClasses = computed(() => [
  'accordion-root',
  props.variant,
  { 'is-multiple': props.multiple },
])

// CSS 변수를 이용한 동적 패딩 적용
const rootStyle = computed(() => {
  const styles = {}
  const { x, y } = props.paddingProps
  if (x) styles['--acc-px'] = typeof x === 'number' ? `${x}px` : x
  if (y) styles['--acc-py'] = typeof y === 'number' ? `${y}px` : y
  return styles
})

// 초기 설정 및 동적 변경 대응
watch(
  () => props.defaultId,
  (newVal) => {
    if (Array.isArray(newVal)) {
      internalOpenItems.value = props.multiple ? [...newVal] : newVal.slice(0, 1)
    } else if (newVal !== null && newVal !== undefined) {
      internalOpenItems.value = [newVal]
    }
  },
  { immediate: true }, // 초기 렌더링 시에도 실행 (init 대체)
)
</script>

<template>
  <div :class="rootClasses" :style="rootStyle">
    <div
      v-for="(item, idx) in data"
      :key="getIdentity(item, idx)"
      class="accordion-item"
      :class="{ 'is-active': currentOpenItems.includes(getIdentity(item, idx)) }"
    >
      <button
        :id="`acc-trigger-${getIdentity(item, idx)}`"
        type="button"
        class="accordion-control"
        :aria-expanded="currentOpenItems.includes(getIdentity(item, idx))"
        :aria-controls="`acc-panel-${getIdentity(item, idx)}`"
        @click="toggleItem(item, idx)"
      >
        <component :is="labelAs" class="label">
          <slot name="label" :item="item" :index="idx" :id="getIdentity(item, idx)">
            {{ item.label }}
          </slot>
        </component>

        <span class="arrow-wrapper" aria-hidden="true">
          <span class="arrow-icon"></span>
        </span>
      </button>
      <div
        :id="`acc-panel-${getIdentity(item, idx)}`"
        class="accordion-container"
        role="region"
        :aria-labelledby="`acc-trigger-${getIdentity(item, idx)}`"
      >
        <div
          class="accordion-overflow"
          :aria-hidden="!currentOpenItems.includes(getIdentity(item, idx))"
        >
          <div class="accordion-content">
            <slot name="content" :item="item" :index="idx" :id="getIdentity(item, idx)">
              {{ item.content }}
            </slot>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
$acc-transition-speed: 0.3s;
$acc-ease: cubic-bezier(0.4, 0, 0.2, 1);
$acc-border-color: #eee;

.accordion-root {
  --acc-px: #{rem(20px)};
  --acc-py: #{rem(18px)};
  --acc-font-main: #{rem(16px)};
  --acc-font-sub: #{rem(16px)};
  --acc-icon-size: #{rem(24px)};

  width: 100%;
  border-top: 1px solid $acc-border-color;

  .accordion-item {
    background-color: #fff;
    border-bottom: 1px solid $acc-border-color;
    transition: background-color $acc-transition-speed;

    &.is-active {
      .accordion-container {
        grid-template-rows: 1fr; // Grid를 이용한 높이 애니메이션
      }
      .accordion-overflow {
        visibility: visible;
        opacity: 1;
      }
      .arrow-icon {
        transform: rotate(225deg); // 아이콘방향
      }
    }
  }
  .accordion-control {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    min-height: rem(56px);
    padding: var(--acc-py) var(--acc-px);
    background: none;
    border: none;
    cursor: pointer;
    font-size: var(--acc-font-main);
    color: #333;
    transition: background-color 0.2s;
    outline: none;
    &:focus-visible {
      background-color: #f8faff;
      box-shadow: inset 0 0 0 2px #007aff;
    }
    .label {
      flex: 1;
      text-align: left;
      margin-right: rem(12px);
      line-height: 1.4;
      font-weight: inherit;
    }
    .arrow-wrapper {
      display: flex;
      align-items: center;
      justify-content: center;
      width: var(--acc-icon-size);
      height: var(--acc-icon-size);
      flex-shrink: 0;
    }
    .arrow-icon {
      width: rem(8px);
      height: rem(8px);
      border-right: 2px solid #666;
      border-bottom: 2px solid #666;
      transform: rotate(45deg);
      transition: transform $acc-transition-speed $acc-ease;
      margin-top: rem(-4px); // 꺽쇠 위치 미세 조정
    }
  }
  .accordion-container {
    display: grid;
    grid-template-rows: 0fr;
    transition: grid-template-rows $acc-transition-speed $acc-ease;
    overflow: hidden;
    .accordion-overflow {
      min-height: 0;
      visibility: hidden;
      opacity: 0;
      transition:
        visibility $acc-transition-speed,
        opacity $acc-transition-speed;
    }
  }
  .accordion-content {
    padding: 0 var(--acc-px) var(--acc-py);
    font-size: var(--acc-font-sub);
    color: #666;
    line-height: 1.6;
    white-space: pre-wrap;
    word-break: break-all;
  }
}

/* --- Variants --- */
.accordion-root.faq {
  border-top-width: 2px;
  border-top-color: #333;
  .accordion-control {
    font-weight: 700;
  }
}
.accordion-root.board {
  border-top: none;
  .accordion-item {
    margin-bottom: rem(8px);
    border: 1px solid $acc-border-color;
    border-radius: rem(8px);
    &.is-active {
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
    }
  }
}
</style>
