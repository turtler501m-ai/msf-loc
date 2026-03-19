<script setup>
import { provide, useId, computed } from 'vue'

const props = defineProps({
  /** 외부에서 직접 지정할 ID (없으면 자동 생성) */
  id: { type: String, default: undefined },
  /** 레이블 텍스트 */
  label: { type: String, default: '' },
  /** label 태그 대신 다른태그 사용여부 */
  tag: {
    type: String,
    default: 'label',
    validator: (v) => ['label', 'div', 'fieldset'].includes(v),
  },
  /** 필수 입력 여부 (*) */
  required: Boolean,
  /** 에러 메시지 */
  error: { type: String, default: '' },
  /** 헬프 텍스트 */
  helpText: { type: String, default: '' },
  /** 그룹 여부 (레이블태그 미사용 여부) */
  isGroup: Boolean,
  /** 세로 방향 */
  vertical: {
    type: Boolean,
    default: false,
  },
})

// label 태그 미사용 태그 렌더링
const isLabelTag = computed(() => props.tag === 'label')

// [ID 결정 로직]
const generatedId = props.id || useId()

// 'form-group-id'라는 이름으로 ID를 전송
provide('form-group-id', generatedId)
</script>

<template>
  <div
    class="form-group-root"
    :class="{ 'has-error': !!props.error, 'is-vertical': props.vertical }"
  >
    <div v-if="props.label" class="label-area">
      <component :is="props.tag" :for="isLabelTag ? generatedId : undefined" class="group-label">
        {{ props.label }}
        <span v-if="props.required" class="required-dot">*</span>
      </component>
    </div>

    <div class="group-content">
      <slot :id="generatedId"></slot>
      <div v-if="props.error || props.helpText" class="etc-text">
        <p v-if="props.error" class="error-message">{{ props.error }}</p>
        <p v-else-if="props.helpText" class="help-text">{{ props.helpText }}</p>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.form-group-root {
  @include flex($v: center) {
    gap: var(--spacing-x2);
  }
  .etc-text {
    width: 100%;
    margin-top: var(--spacing-x1);
  }
  &.is-vertical {
    flex-direction: column;
  }
  .label-area {
    @include flex($v: center);
    .group-label {
      width: rem(90px);
      display: inline-block; // div일 때도 label처럼 보이게 처리
      font-size: var(--font-size-14);
      font-weight: var(--font-weight-bold);
      line-height: var(--line-height-18);
      color: var(--color-foreground);
      &[for] {
        cursor: pointer; // label일 때만 포인터 커서
      }
      .required-dot {
        font-size: var(--font-size-14);
        font-weight: var(--font-weight-bold);
        color: var(--color-accent1-base);
      }
    }
  }

  .group-content {
    @include flex($d: column) {
      column-gap: var(--spacing-x1);
    }
    .error-message {
      font-size: var(--font-size-12);
      color: var(--color-accent-alert);
      margin: 0;
    }
    .help-text {
      font-size: var(--font-size-12);
      color: #909399;
      margin: 0;
    }
  }
}
</style>
