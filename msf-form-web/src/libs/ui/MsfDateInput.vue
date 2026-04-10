<template>
  <div class="date-input-root">
    <VueDatePicker
      v-bind="$attrs"
      :model-value="parsedDate"
      :auto-apply="!centered"
      :centered="centered"
      teleport="body"
      year-first
      :placeholder="props.placeholder ? props.placeholder : '발급 일자 (YYYY.MM.DD)'"
      model-type="yyyy-MM-dd"
      class="msf-date-input-value"
      :formats="{ input: 'yyyy-MM-dd' }"
      :text-input="{ format: 'yyyyMMdd' }"
      :locale="ko"
      :week-start="WeekStart.Sunday"
      :year-range="[1900, 9999]"
      :time-config="{ enableTimePicker: false }"
      @update:model-value="onDatePickerSelect"
      @open="onOpen"
      @closed="onClose"
      :action-row="{
        selectBtnLabel: '확인',
        cancelBtnLabel: '취소',
      }"
    >
      <!-- 년,월 표시 -->
      <template #year="slotProps"> {{ slotProps.year || slotProps.value }}년 </template>
      <template #month="slotProps">
        {{ (slotProps.month !== undefined ? slotProps.month : slotProps.value) + 1 }}월
      </template>
      <template #input-icon>
        <MsfIcon name="calendar" size="large" />
      </template>
      <!-- 클리어 버튼 필요시 사용 -->
      <!-- <template #clear-icon="{ clear }">
        <MsfIcon name="clear" size="large" @click="clear" />
      </template> -->
      <!-- 하단 버튼영역 커스텀 시-->
      <!-- <template #action-buttons>
        <MsfButton variant="secondary" class="btn-cancel" @click="close">취소</MsfButton>
        <MsfButton variant="primary" class="btn-confirm" @click="confirm">확인</MsfButton>
      </template> -->
    </VueDatePicker>
  </div>
</template>

<script setup>
import { computed, onUnmounted } from 'vue'
import { VueDatePicker, WeekStart } from '@vuepic/vue-datepicker'
import { ko } from 'date-fns/locale'
import { formatDate } from '@/libs/utils/date.utils'
import '@vuepic/vue-datepicker/dist/main.css'

// 캘린더 열림
const onOpen = () => {
  if (props.centered) {
    document.body.classList.add('datepicker-open')
  }
}
// 캘린더 닫힘
const onClose = () => {
  if (props.centered) {
    document.body.classList.remove('datepicker-open')
  }
}
// 언마운트
onUnmounted(() => {
  if (props.centered) {
    document.body.classList.remove('datepicker-open')
  }
})

defineOptions({
  inheritAttrs: false,
})

const props = defineProps({
  modelValue: String,
  /** 가운데로 띄울것 인지 여부 (가운데로 띄우면 auto apply false시킴) */
  centered: {
    type: Boolean,
    default: true,
  },
  /** placeholder */
  placeholder: { type: String, default: '발급 일자 (YYYY.MM.DD)' },
})

const emit = defineEmits(['update:modelValue'])

// 💡 수정 3: Proxy 충돌을 피하는 핵심 로직. (필요할 때만 순수 Date 객체를 만들어 반환)
const parsedDate = computed(() => formatDate(props.modelValue))

// 사용자가 값을 입력/수정할 때마다 실행됨
const onDatePickerSelect = (selectedDate /*, maskRef*/) => {
  if (!selectedDate) {
    emit('update:modelValue', '')
    return
  }
  emit('update:modelValue', formatDate(selectedDate))
}
</script>

<style scoped lang="scss">
.date-input-root {
  position: relative;
  width: fit-content;
  // vue-datepicker의 정의된 sass값을 변경
  :deep(.dp__theme_light) {
    --dp-border-color: var(--color-gray-400);
    --dp-border-color-hover: var(--color-gray-400);
    --dp-border-radius: var(--border-radius-base);
    --dp-font-family: var(--font-family-base);
    --dp-input-side-padding: #{rem(16px)};
    --dp-input-side-icon-size: #{rem(24px)}; // 캘린더 디테일 사이즈
    --dp-cell-size: #{rem(40px)};
  }
  :deep(.dp__input_wrap) {
    min-width: rem(300px);
    display: flex;
    flex-direction: row-reverse; /* 아이콘 우측으로 변경 */
    align-items: center;
  }
  /* 입력창 패딩 조정 */
  :deep(.dp__input) {
    padding-inline-start: var(--dp-input-side-padding);
    padding-inline-end: var(--dp-input-side-padding);
    height: rem(52px);
    font-size: var(--font-size-16);
    &.dp__input_focus {
      border-color: var(--color-primary);
    }
    &.dp__disabled {
      border: 1px solid var(--color-gray-150);
      + div {
        .msf-icon {
          color: var(--color-text-disabled);
        }
      }
    }
  }
  /* 아이콘의 여백 조정 */
  :deep(.dp__input_icon) {
    position: absolute;
    right: var(--dp-input-side-padding); /* 우측 끝에서의 간격 */
    left: auto;
    width: var(--dp-input-side-icon-size);
    height: var(--dp-input-side-icon-size);
    color: var(--color-gray-600);
    & i {
      vertical-align: top;
    }
  }
  :deep(.dp--clear-btn) {
    width: var(--dp-input-side-icon-size);
    height: var(--dp-input-side-icon-size);
    right: calc(var(--dp-input-side-padding) + calc(var(--dp-input-side-icon-size) + rem(8px)));
  }
  :deep(.dp__menu) {
    border-radius: var(--border-radius-l);
  }
  :deep(.dp__menu_inner) {
    padding: rem(16px);
  }
}
</style>
