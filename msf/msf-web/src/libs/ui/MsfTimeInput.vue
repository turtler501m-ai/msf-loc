<template>
  <div class="msf-time-input-container">
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
    >
      <template #input-icon>
        <i class="fa-regular fa-clock msf-time-icon dp__icon dp__input_icon dp__input_icons"></i>
      </template>
      <template
        #dp-input="{ value, onInput, onEnter, onTab, onClear, onBlur, onFocus, onKeypress }"
      >
        <input
          v-bind="$attrs"
          :value="value"
          :maxlength="props.showSeconds ? 6 : 4"
          :placeholder="props.showSeconds ? 'HH:mm:ss' : 'HH:mm'"
          data-test-id="dp-input"
          class="dp__input dp__input_icon_pad"
          inputmode="numeric"
          autocomplete="off"
          aria-label="Datepicker input"
          @input="onInput"
          @keydown.enter="onEnter"
          @keydown.tab="onTab"
          @blur="onBlur"
          @focus="onFocus"
          @keypress="onKeypress"
        />
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

<style scoped>
@reference "tailwindcss";

.msf-time-input-container {
  @apply flex items-stretch;
}
.msf-time-icon {
  @apply h-[1.5rem];
}
.msf-time-input-value {
  @apply block min-w-0 grow;
}
</style>
