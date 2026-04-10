<template>
  <div class="date-range-root">
    <div class="range-container">
      <MsfDateInput v-model="startDate" :max-date="endDate" placeholder="시작일" />
      <span class="range-separator">~</span>
      <MsfDateInput v-model="endDate" :min-date="startDate" placeholder="종료일" />
    </div>
    <div v-if="props.showQuickButtons" class="range-btns">
      <MsfChip v-model="quickActive" :data="quickButtons" name="period-select" columns="auto" />
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { differenceInCalendarDays, differenceInMonths, format, isToday, toDate } from 'date-fns'

const startDate = defineModel('from', { default: '' })
const endDate = defineModel('to', { default: '' })

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
  { code: '0-0', value: '0-0', label: '당일', days: 0, months: 0 },
  // { code: '0-1', value: '0-1', label: '1일', days: 1, months: 0 },
  { code: '0-7', value: '0-7', label: '1주', days: 7, months: 0 },
  { code: '0-14', value: '0-14', label: '2주', days: 14, months: 0 },
  { code: '1-0', value: '1-0', label: '한달', days: 0, months: 1 },
])

// Date 객체를 'YYYY-MM-DD' 포맷으로 변환하는 헬퍼 함수
const formatDate = (date) => {
  return format(date, 'yyyy-MM-dd')
}

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
)
</script>

<style lang="scss" scoped>
.date-range-root {
  @include flex($v: center);
  gap: rem(12px);
  .range-container {
    @include flex($v: center);
    gap: var(--spacing-x2);
  }
  .range-separator {
    position: relative;
    font-size: var(--font-size-16);
    color: var(--color-gray-600);
  }
  .range-btns {
    position: relative;
  }
  // 유틸 클래스 100% 줄경우 내부 input 의 최소 너비 무효
  &.ut-w100p {
    :deep([class^='dp__input_wrap']) {
      min-width: auto;
    }
  }
}
</style>
