<template>
  <div class="msf-date-input-container">
    <VueDatePicker
      v-bind="$attrs"
      :model-value="parsedDate"
      auto-apply
      year-first
      model-type="yyyy-MM-dd"
      class="msf-date-input-value"
      :formats="{ input: 'yyyy-MM-dd' }"
      :text-input="{ format: 'yyyyMMdd' }"
      :locale="ko"
      :week-start="WeekStart.Sunday"
      :year-range="[1900, 9999]"
      :time-config="{ enableTimePicker: false }"
      @update:model-value="onDatePickerSelect"
    >
      <template #input-icon>
        <i class="fa-regular fa-calendar msf-date-icon dp__icon dp__input_icon dp__input_icons"></i>
      </template>
      <template #dp-input="{ value, onInput, onEnter, onTab }">
        <input
          v-bind="$attrs"
          :value="value"
          maxlength="8"
          data-test-id="dp-input"
          class="dp__input dp__input_icon_pad"
          inputmode="text"
          autocomplete="off"
          aria-label="Datepicker input"
          @input="onInput"
          @keydown.enter="onEnter"
          @keydown.tab="onTab"
        />
      </template>
    </VueDatePicker>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { VueDatePicker, WeekStart } from '@vuepic/vue-datepicker'
import { ko } from 'date-fns/locale'
import { formatDate } from '@/libs/utils/date.utils'

defineOptions({
  inheritAttrs: false,
})

const props = defineProps({
  modelValue: String,
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

<style scoped>
@reference "tailwindcss";

.msf-date-input-container {
  @apply flex items-stretch;
}
.msf-date-icon {
  @apply h-[1.5rem];
}
.msf-date-input-value {
  @apply block min-w-0 grow;
}
</style>
