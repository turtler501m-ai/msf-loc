<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="My time plan_MVNO 전용"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <!-- 팝업 내용 -->
    <MsfTitleArea title="사용 기간 설정" level="2" color="black" noline bold />
    <MsfStack type="field" vertical>
      <MsfDateRange
        v-model:from="formData.startDate"
        v-model:to="formData.endDate"
        class="ut-w100p"
      />
      <MsfSelect
        title="시작 시간 선택"
        v-model="formData.timeSelect"
        :options="timeOptions"
        placeholder="시작 시간 선택"
        class="ut-w100p"
      />
    </MsfStack>
    <MsfBox>
      <MsfTextList
        :items="[
          '입대일자, 전역일자, 시작시간 순으로 선택해 주세요.',
          '입대일자와 전역일자는 21개월만 가능합니다.',
        ]"
        margin="0"
        level="2"
      />
    </MsfBox>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton v-if="props.settingData?.addSvcSettingCompleted" variant="tertiary" @click="onReset">초기화</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { reactive, ref, computed, watch } from 'vue'
import { differenceInMonths } from 'date-fns'
import { toDate } from '@/libs/utils/date.utils'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  settingData: { type: Object, default: () => ({}) },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

// 폼 데이터
const formData = reactive({
  startDate: '',
  endDate: '',
  timeSelect: '',
})

// 기존 설정값에서 초기값 복원
const initializeFromSettingData = () => {
  const { ftrNewParam, startDate, endDate, timeSelect } = props.settingData

  if (ftrNewParam) {
    // ftrNewParam 형식: 시작시간:입대일자:전역일자 (예: 24:20250516:20270217)
    const parts = String(ftrNewParam).split(':')
    if (parts.length >= 3) {
      const time = parts[0]
      const start = parts[1]
      const end = parts[2]

      formData.timeSelect = String(time).padStart(2, '0')
      if (start && start.length === 8) {
        formData.startDate = `${start.slice(0, 4)}-${start.slice(4, 6)}-${start.slice(6, 8)}`
      }
      if (end && end.length === 8) {
        formData.endDate = `${end.slice(0, 4)}-${end.slice(4, 6)}-${end.slice(6, 8)}`
      }
    }
  } else {
    formData.startDate = startDate || ''
    formData.endDate = endDate || ''
    formData.timeSelect = timeSelect || ''
  }
}

const isFormReset = ref(false)

watch(() => props.modelValue, (isOpen) => {
  if (isOpen) {
    isFormReset.value = false
    initializeFromSettingData()
  }
}, { immediate: true })

// 시간 선택 옵션 (1시간 단위, 0~23시)
const timeOptions = computed(() => {
  const options = []
  for (let i = 0; i < 24; i++) {
    const hour = String(i).padStart(2, '0')
    options.push({
      label: `${hour}:00`,
      value: hour,
    })
  }
  return options
})

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onReset = () => {
  isFormReset.value = true
  formData.startDate = ''
  formData.endDate = ''
  formData.timeSelect = ''
}

// 확인 버튼 클릭
const onConfirm = () => {
  if (isFormReset.value) {
    emit('confirm', { isReset: true })
    onClose()
    return
  }
  const startDate = formData.startDate?.trim()
  const endDate = formData.endDate?.trim()
  const timeSelect = formData.timeSelect?.trim()

  // 입대일 유효성 검증
  if (!startDate) {
    showAlert('입대일자를 선택해 주세요.')
    return
  }

  // 전역일 유효성 검증
  if (!endDate) {
    showAlert('전역일자를 선택해 주세요.')
    return
  }

  // 시작시간 유효성 검증
  if (!timeSelect) {
    showAlert('시작시간을 선택해 주세요.')
    return
  }

  // 시작일이 현재 이후인지 검증
  const startDateObj = toDate(startDate)
  const today = new Date()
  today.setHours(0, 0, 0, 0)

  if (startDateObj < today) {
    showAlert('입대일자는 오늘 이후로 선택해 주세요.')
    return
  }

  // 종료일이 시작일보다 이후인지 검증
  const endDateObj = toDate(endDate)
  if (endDateObj <= startDateObj) {
    showAlert('전역일자는 입대일자보다 이후로 선택해 주세요.')
    return
  }

  // 종료일이 시작일로부터 21개월 이내인지 검증
  const monthDiff = differenceInMonths(endDateObj, startDateObj)
  if (monthDiff > 21) {
    showAlert('입대일자와 전역일자는 21개월만 가능합니다.')
    return
  }

  // H8 기준: ftrNewParam = '시작시간:입대일자:전역일자' (예: 24:20250516:20270217)
  // startDate/endDate는 'YYYY-MM-DD' 형식이므로 하이픈 제거 필요
  const ftrNewParam = `${timeSelect}:${startDate.replace(/-/g, '')}:${endDate.replace(/-/g, '')}`

  emit('confirm', { ftrNewParam })
  onClose()
}
</script>

<style lang="scss" scoped></style>
