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

// 레이블 태그일 때만 ID를 공유하고, 아니면 null을 전송
provide('form-group-id', isLabelTag.value ? generatedId : null)
</script>

<template>
  <div
    class="form-group-root"
    :class="{ 'has-error': !!props.error, 'is-vertical': props.vertical }"
  >
    <div v-if="props.label" class="label-area">
      <component :is="props.tag" :for="isLabelTag ? generatedId : undefined" class="group-label">
        <!-- {{ props.label }}
        <span v-if="props.required" class="required-dot">*</span> -->
        <slot name="label">
          <span v-html="props.label"></span>
        </slot>
        <span v-if="props.required" class="required-dot">*</span>
      </component>
    </div>

    <div class="group-content">
      <!-- <slot :id="generatedId"></slot> -->
      <slot :id="isLabelTag ? generatedId : undefined"></slot>
      <div v-if="props.error || props.helpText" class="etc-text">
        <p v-if="props.error" class="error-message">{{ props.error }}</p>
        <p v-else-if="props.helpText" class="help-text">{{ props.helpText }}</p>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.form-group-root {
  --form-group-min-height: #{rem(52px)};
  --form-label-weight: var(--font-weight-regular);

  width: 100%;
  // 가로 정렬로 기본
  @include flex($v: flex-start) {
    gap: var(--spacing-x2);
  }

  // 세로정렬 (vertical로 지정시 : 현재 로그인에서 사용)
  &.is-vertical {
    flex-direction: column;
    .label-area,
    .group-content {
      width: 100%;
    }
    .label-area {
      min-height: auto;
    }
  }
  .label-area {
    flex-shrink: 0;
    flex-grow: 0;
    @include flex($v: center);
    min-height: var(--form-group-min-height);
    .group-label {
      width: rem(108px);
      display: inline-block; // div일 때도 label처럼 보이게 처리
      font-size: var(--font-size-16);
      line-height: var(--line-height-heading);
      font-weight: var(--form-label-weight);
      color: var(--color-foreground);
      &[for] {
        cursor: pointer; // label일 때만 포인터 커서
      }
      .required-dot {
        font-size: var(--font-size-16);
        font-weight: var(--font-weight-regular);
        color: var(--color-accent1-base);
      }
    }
  }

  .group-content {
    min-height: var(--form-group-min-height);
    flex: 1;
    min-width: 0;
    @include flex($d: column, $v: flex-start) {
      gap: var(--spacing-x2);
      justify-content: space-around;
    }
    .etc-text {
      width: 100%;
    }
    .error-message {
      font-size: var(--font-size-16);
      color: var(--color-accent-alert);
      margin: 0;
    }
    .help-text {
      font-size: var(--font-size-16);
      color: var(--color-gray-600);
      margin: 0;
    }
  }

  // 내부 최초 너비 고정값 지정
  :deep([class^='input-root']) {
    flex: 0 0 auto;
    width: var(--formgroup-inner-inline-width);
  }
  :deep([class^='select-root']) {
    flex: 0 0 auto;
    width: rem(300px);
  }
  // 폼그룹 내부에 체크박스,라디오 의 기본높이 레이블과 맞춰서 지정
  :deep([class^='checkbox-group-root']),
  :deep([class^='radio-group-root']) {
    row-gap: 0;
    column-gap: rem(24px);
  }
  :deep([class^='checkbox-root']),
  :deep([class^='radio-root']) {
    min-height: var(--form-group-min-height);
    /* 전체 높이에서 글자 높이를 뺀 절반 */
    padding-top: calc((var(--form-group-min-height) - 1em) / 2);
  }
}
</style>
