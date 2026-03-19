<template>
  <div
    role="group"
    :aria-label="typeof label === 'string' ? label : ariaLabel"
    :class="[rootClasses, $attrs.class]"
  >
    <div :class="['container', { gridColumns: columns !== 'auto' }]" :style="containerStyle">
      <div v-for="(item, i) in data" :key="item.value" class="chip-item">
        <div :class="['chip', item.className]">
          <input
            v-bind="$attrs"
            :type="inputType"
            :id="`${name}-${baseId}-${i}`"
            :name="name"
            :value="item.value"
            :checked="selectedList.includes(item.value)"
            :disabled="item.disabled"
            class="input"
            @change="handleChange(item.value)"
          />
          <label :for="`${name}-${baseId}-${i}`" :class="['label', { disabled: item.disabled }]">
            <slot name="prefix" :item="item" :checked="selectedList.includes(item.value)"></slot>
            <span class="text">{{ item.label }}</span>
            <slot name="suffix" :item="item" :index="i"></slot>
          </label>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, useId } from 'vue'

defineOptions({ inheritAttrs: false })

const model = defineModel({ type: [String, Array], default: undefined })

const props = defineProps({
  /** 출력 방식 결정 */
  type: { type: String, default: 'grid' },
  /** 스타일 */
  variant: {
    type: String,
    default: 'period',
    validator: (v) => ['outlined', 'period'].includes(v),
  },
  /** 칩에 표시할 데이터 목록 (객체 배열 형태: { label, value, disabled 등 }) */
  data: { type: Array, default: () => [] },
  /** input 요소의 고유 name 속성 */
  name: { type: String, required: true },
  /** 컴포넌트 전체를 설명하는 제목 (String 또는 이미지/아이콘 포함 Object) */
  label: [String, Object],
  /** 스크린 리더 등 웹 접근성을 위한 레이블 (label props가 없을 때 대체 사용) */
  ariaLabel: String,
  /** 다중 선택 여부 (true면 체크박스, false면 라디오 버튼으로 동작) */
  multiple: Boolean,
  /** 크기 옵션 */
  size: {
    type: String,
    default: 'medium',
    validator: (v) => ['small', 'medium', 'large'].includes(v),
  },
  /** 그리드 레이아웃 시 보여줄 컬럼 개수 또는 'auto' 설정 */
  columns: {
    type: [Number, String],
    default: 'auto',
  },
})

// 이벤트 등록
const emit = defineEmits(['change'])
// 아이디 자동 생성
const baseId = useId()

// 선택된 값들을 배열로 정규화
const selectedList = computed(() => {
  if (model.value === undefined) return []
  return Array.isArray(model.value) ? model.value : [model.value]
})

// 체크박스/라디오 타입 결정
const inputType = computed(() => (props.multiple ? 'checkbox' : 'radio'))

// 클릭 이벤트 핸들러
const handleChange = (value) => {
  // 1. 다중 선택 모드일 때
  if (props.multiple) {
    if (value === 'all') {
      model.value = ['all'] // '전체'를 클릭하면 다른 모든 선택을 지우고 ['all']만 유지
    } else {
      let current = Array.isArray(model.value) ? [...model.value] : [] // 일반 아이템을 클릭했을 때

      current = current.filter((v) => v !== 'all') // 먼저 'all'이 있다면 제거
      if (current.includes(value)) {
        model.value = current.filter((v) => v !== value) // 이미 있으면 제거 (체크 해제)
      } else {
        model.value = [...current, value] // 없으면 추가 (체크 선택)
      }
    }
  } else {
    // 2. 단일 선택 모드일 때
    model.value = value
  }

  emit('change', model.value) // 부모에게 최종 변경 알림
}

// 스타일 클래스
const rootClasses = computed(() => ['root', props.variant, props.size, { round: props.round }])

// 그리드 컬럼 스타일
const containerStyle = computed(() => {
  if (typeof props.columns === 'number') {
    return { '--chip-grid-columns': props.columns }
  }
  return {}
})
</script>

<style lang="scss" scoped>
.root {
  --chip-gap: var(--spacing-x1);
  --chip-min-width: #{rem(56px)};
  --chip-min-height: #{rem(32px)};
  --chip-border-color: var(--color-gray-150);
  --chip-text-color: var(--color-gray-700);
  --chip-text-align: center;
  --chip-label-font-size: var(--font-size-14);
  --chip-label-line-height: var(--line-height-fit);
  --chip-label-justify-contents: center;
  --chip-label-disabled-text-color: var(--color-gray-300);

  .container {
    @include flex($display: inline-flex, $w: wrap);
    gap: var(--chip-gap);
    // columns 'auto'가 아닌 경우 스타일
    &.gridColumns {
      @include grid($gap: var(--chip-gap)) {
        grid-template-columns: repeat(
          var(--chip-grid-columns, auto-fill),
          minmax(var(--chip-min-width), 1fr)
        );
      }
    }
  }
  .chip {
    position: relative;
    display: inline-block;
    &[aria-hidden='true'] {
      display: none !important;
    }
  }
  .input {
    @include blind;
    &:checked + .label {
      --chip-border-color: var(--color-foreground);
      --chip-text-color: var(--color-foreground);
      font-weight: var(--font-weight-regular);
    }
  }
  .label {
    min-width: var(--chip-min-width);
    min-height: var(--chip-min-height);
    position: relative;
    @include flex(flex, var(--chip-label-justify-contents), center);
    padding-inline: var(--spacing-x4);
    color: var(--chip-text-color);
    font-size: var(--chip-label-font-size);
    font-weight: var(--font-weight-regular);
    line-height: var(--chip-label-line-height);
    text-align: var(--chip-text-align);
    border: var(--border-width-base) solid var(--chip-border-color, transparent);
    border-radius: var(--border-radius-base);
    cursor: pointer;
    white-space: pre-line;
    transition:
      border-color var(--transition-fast),
      background-color var(--transition-fast);

    span {
      display: block;
    }
    &.disabled {
      --chip-text-color: var(--chip-label-disabled-text-color);
      cursor: not-allowed;
    }
  }
}

// variant
.outlined {
  --chip-min-width: #{rem(56px)};
  --chip-text-color: var(--color-gray-700);
  --chip-outlined-checked-text-color: inherit;
  --chip-outlined-checked-font-weight: var(--font-weight-regular);
  --chip-outlined-padding: var(--spacing-x2) var(--spacing-x4);
  --chip-outlined-disabled-text-color: var(--color-gray-300);
  .label {
    padding: var(--chip-outlined-padding);
    min-width: var(--chip-min-width);
    &.disabled {
      --chip-text-color: var(--chip-outlined-disabled-text-color);
      background-color: var(--color-gray-100);
    }
  }
  .input:checked + .label {
    color: var(--chip-outlined-checked-text-color);
    font-weight: var(--chip-outlined-checked-font-weight);
  }
}
// 기간설정
.period {
  --chip-min-width: #{rem(56px)};
  --chip-border-color: var(--color-gray-150);
  --chip-text-color: var(--color-background);
  .label {
    min-height: rem(32px);
    font-size: var(--font-size-14);
    background-color: var(--color-gray-450);
    border-color: var(--font-size-14);
    &.disabled {
      background-color: var(--color-bg-disabled);
      border-color: var(--color-bg-disabled);
    }
  }
  .input:checked + .label {
    color: var(--color-white);
    background-color: var(--color-accent2-base);
    border-color: var(--color-accent2-base);
  }
}

// size
.small {
  .label {
    font-size: var(--font-size-13);
    // line-height: var(--line-height-18);
    min-width: rem(28px);
    min-height: rem(28px);
    height: rem(28px);
    border-radius: var(--border-radius-s);
  }
}
.medium {
  .label {
    font-size: var(--font-size-14);
    // line-height: var(--line-height-20);
    min-height: rem(32px);
    height: rem(32px);
    border-radius: var(--border-radius-base);
  }
}
.large {
  .container {
    gap: var(--spacing-x2);
  }
  .label {
    font-size: var(--font-size-14);
    line-height: var(--line-height-20);
    min-height: rem(40px);
    height: rem(40px);
    border-radius: var(--border-radius-base);
  }
}

//round
.round {
  .label {
    border-radius: var(--border-radius-max);
    padding-inline: var(--spacing-x6);
  }
}
</style>
