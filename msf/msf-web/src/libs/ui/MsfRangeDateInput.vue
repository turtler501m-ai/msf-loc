<template>
  <div class="msf-range-date-input-wrapper">
    <div class="msf-range-date-input-container">
      <MsfDateInput v-model="startDate" :max-date="endDate" placeholder="시작일 (YYYY-MM-DD)" />
      <span class="msf-separator">~</span>
      <MsfDateInput v-model="endDate" :min-date="startDate" placeholder="종료일 (YYYY-MM-DD)" />
    </div>
    <div v-if="props.showQuickButtons" class="msf-range-date-input-btn-group">
      <MsfRadioButtons :values="quickButtons" v-model:active="quickActive" />
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { differenceInCalendarDays, differenceInMonths, isToday, toDate } from 'date-fns'
import { formatDate } from '@/libs/utils/date.utils'

const startDate = defineModel('from', { type: String })
const endDate = defineModel('to', { type: String })

const props = defineProps({
  showQuickButtons: {
    type: Boolean,
    default: false,
  },
})

// 내부 상태 관리용 변수 (props에서 초기값을 객체 키로 가져옴)
const quickActive = ref('')

// 퀵 버튼 설정 데이터
const quickButtons = ref([
  { code: '0-0', name: '당일', days: 0, months: 0 },
  { code: '0-1', name: '1일', days: 1, months: 0 },
  { code: '0-7', name: '1주', days: 7, months: 0 },
  { code: '0-14', name: '2주', days: 14, months: 0 },
  { code: '1-0', name: '한달', days: 0, months: 1 },
])

// 버튼 클릭 시 기간 설정 로직
const setRange = (val) => {
  const end = new Date()
  const start = new Date()

  if (val.months > 0) {
    start.setMonth(start.getMonth() - val.months)
  }
  if (val.days > 0) {
    start.setDate(start.getDate() - val.days)
  }

  startDate.value = formatDate(start)
  endDate.value = formatDate(end)
}

const calcMonthsAndDays = (from, to) => {
  const start = toDate(from)
  const end = toDate(to)
  if (!isToday(end)) {
    return { months: -1, days: -1 }
  }
  const months = differenceInMonths(end, start)
  const days = differenceInCalendarDays(end, start)
  if (months === 0) {
    return { months: 0, days }
  }
  const newStart = new Date(start.getFullYear(), start.getMonth() + months, start.getDate())
  const newDays = differenceInCalendarDays(end, newStart)
  return { months, days: newDays }
}

watch(
  () => startDate.value,
  (newVal) => {
    if (!newVal) {
      quickActive.value = ''
    } else {
      const calc = calcMonthsAndDays(newVal, endDate.value)
      quickActive.value =
        quickButtons.value.find((v) => v.months === calc.months && v.days === calc.days)?.code || ''
    }
  },
  { immediate: true },
)
watch(
  () => endDate.value,
  (newVal) => {
    if (!newVal) {
      quickActive.value = ''
    } else {
      const calc = calcMonthsAndDays(startDate.value, newVal)
      quickActive.value =
        quickButtons.value.find((v) => v.months === calc.months && v.days === calc.days)?.code || ''
    }
  },
  { immediate: true },
)
watch(
  () => quickActive.value,
  (newVal) => {
    if (newVal) {
      const founded = quickButtons.value.find((v) => v.code === newVal)
      if (founded) {
        setRange(founded)
      }
    }
  },
  { immediate: true },
)
</script>

<style scoped>
@reference "tailwindcss";

.msf-range-date-input-wrapper {
  @apply flex gap-2 w-full;
}

.msf-range-date-input-btn-group {
  @apply w-full flex-1 max-w-[14rem];
}

.msf-range-date-input-container {
  @apply flex-1 col-span-6 grid grid-cols-13 items-center gap-2 w-full;
}

.msf-separator {
  @apply font-bold text-neutral-400 text-center;
}

.msf-range-date-input-container > :deep(.msf-date-input-container) {
  @apply col-span-6;
}
</style>
