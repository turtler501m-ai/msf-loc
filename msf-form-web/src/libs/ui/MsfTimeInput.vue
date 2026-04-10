<template>
  <div class="time-input-root msf-time-input-container">
    <VueDatePicker
      v-bind="$attrs"
      :model-value="parsedTime"
      :model-type="props.showSeconds ? 'HH:mm:ss' : 'HH:mm'"
      class="msf-time-input-value"
      :formats="{ input: props.showSeconds ? 'HH:mm:ss' : 'HH:mm' }"
      :text-input="{ format: props.showSeconds ? 'HHmmss' : 'HHmm' }"
      :locale="ko"
      :time-config="{ enableSeconds: props.showSeconds }"
      @update:model-value="onTimePickerSelect"
      time-picker
      :action-row="{
        selectBtnLabel: '확인',
        cancelBtnLabel: '취소',
      }"
    >
      <!-- 시간아이콘 있다면 지정(현재 없음) -->
      <template #input-icon>
        <!-- <MsfIcon name="print" size="small" /> -->
      </template>
      <template #clear-icon="{ clear }">
        <MsfIcon name="clear" @click="clear" />
      </template>
    </VueDatePicker>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { VueDatePicker } from '@vuepic/vue-datepicker'
import { ko } from 'date-fns/locale'
import { formatTime } from '@/libs/utils/date.utils'

defineOptions({
  inheritAttrs: false,
})

const props = defineProps({
  modelValue: String,
  showSeconds: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits(['update:modelValue'])

// 💡 수정 3: Proxy 충돌을 피하는 핵심 로직. (필요할 때만 순수 Date 객체를 만들어 반환)
const parsedTime = computed(() => formatTime(props.modelValue, props.showSeconds))

// 사용자가 값을 입력/수정할 때마다 실행됨
const onTimePickerSelect = (selectedTime /*, maskRef*/) => {
  if (!selectedTime) {
    emit('update:modelValue', '')
    return
  }
  emit('update:modelValue', formatTime(selectedTime, props.showSeconds))
}
</script>

<style lang="scss" scoped>
.time-input-root {
  max-width: rem(130px);
  position: relative;
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
    // max-width: rem(132px);
    width: 100%;
    display: flex;
    flex-direction: row-reverse; /* 아이콘 우측으로 변경 */
    align-items: center;
  }
  /* 입력창 패딩 조정 */
  :deep(.dp__input) {
    padding-inline-start: var(--dp-input-side-padding);
    padding-inline-end: calc(var(--dp-input-side-padding) + var(--dp-input-side-icon-size));
    height: rem(52px);
    // padding-left: rem(28px);
    font-size: var(--font-size-16);
    &.dp__input_focus {
      border-color: var(--color-primary);
    }
  }
  /* 아이콘의 여백 조정 */
  // :deep(.dp__input_icon) {
  //   position: absolute;
  //   left: var(--dp-input-side-padding);
  //   right: auto;
  //   width: var(--dp-input-side-icon-size);
  //   height: var(--dp-input-side-icon-size);
  //   & i {
  //     vertical-align: top;
  //   }
  // }
  :deep(.dp--clear-btn) {
    width: var(--dp-input-side-icon-size);
    height: var(--dp-input-side-icon-size);
    // right: calc(var(--dp-input-side-padding) + calc(var(--dp-input-side-icon-size) + rem(4px)));
    right: var(--dp-input-side-padding);
  }
}
</style>
