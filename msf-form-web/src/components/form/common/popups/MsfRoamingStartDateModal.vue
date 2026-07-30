<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    :title="dialogTitle"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <MsfTitleArea title="이용기간 설정(한국시간 기준)" level="2" color="black" noline bold>
      <template v-if="variant !== 'date8'" #content>
        <MsfTextList
          :items="['신청한 시간부터 24시간 동안 적용']"
          type="none"
          margin="0"
          color="mint"
        />
      </template>
    </MsfTitleArea>
    <MsfStack type="field" vertical>
      <!-- date8 모드: 시작일만 (기존 동작) -->
      <template v-if="variant === 'date8'">
        <MsfDateInput v-model="startDate" placeholder="시작일" class="ut-w100p" />
      </template>

      <!-- dateTimeRange 모드: 시작일~종료일 + 시작시간 -->
      <template v-else>
        <MsfStack type="field" vertical>
          <MsfDateRange
            v-model:from="startDate"
            v-model:to="endDate"
            class="ut-w100p"
          />
          <MsfSelect
            title="시작 시간 선택"
            v-model="startHour"
            :options="timeOptions"
            placeholder="시작 시간 선택"
            class="ut-w100p"
          />
        </MsfStack>
      </template>
    </MsfStack>
    <MsfBox>
      <MsfTextList
        :items="['서비스 신청/변경이 완료되면 문자 메시지가 발송되오니 확인하여 주세요.']"
        margin="0"
        level="2"
      />
    </MsfBox>
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton v-if="props.settingData?.showChangeCancel" variant="tertiary" @click="onReset">변경취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { addMonths, isAfter, isBefore } from 'date-fns'
import { toDate } from '@/libs/utils/date.utils'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  modelValue: Boolean,
  variant: { type: String, default: 'date8' }, // 'date8' | 'dateTimeRange'
  serviceName: { type: String, default: '' },
  settingData: {
    type: Object,
    default: () => ({}),
  },
  initialSettingData: {
    type: Object,
    default: () => ({}),
  },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const startDate = ref()
const startHour = ref('00')
const endDate = ref()
const dialogTitle = computed(() => props.serviceName || '부가서비스명')
const timeOptions = computed(() =>
  Array.from({ length: 24 }, (_, hour) => {
    const value = String(hour).padStart(2, '0')
    return { label: `${value}:00`, value }
  }),
)
const today = () => {
  const date = new Date()
  date.setHours(0, 0, 0, 0)
  return date
}

const parseKeyValueParam = (param = '') =>
  String(param || '')
    .split('|')
    .map((item) => item.split('='))
    .reduce((result, [key, value]) => {
      if (key) result[String(key).trim()] = String(value || '').trim()
      return result
    }, {})

const formatYmd = (value = '') => {
  const digits = String(value || '').replace(/\D/g, '')
  if (digits.length < 8) return ''
  return `${digits.slice(0, 4)}-${digits.slice(4, 6)}-${digits.slice(6, 8)}`
}

const getSavedRoamingParams = (settingData = props.settingData) => {
  const params = parseKeyValueParam(settingData.paramSbst || settingData.paramSbstCtt || '')
  return {
    start: params.STRT_DT || settingData.strtDt || settingData.startDt || settingData.startDateTime || '',
    end: params.END_DT || settingData.endDt || settingData.endDateTime || '',
  }
}

// 기존 설정값에서 초기값 복원
const initializeFromSettingData = (settingData = props.settingData) => {
  const { ftrNewParam, startDate: savedStartDate, startHour: savedStartHour, endDate: savedEndDate } = settingData
  const savedRoamingParams = getSavedRoamingParams(settingData)
  startDate.value = undefined
  startHour.value = '00'
  endDate.value = undefined

  if (props.variant === 'date8') {
    // yyyyMMdd 형식 복원
    if (ftrNewParam) {
      const digits = String(ftrNewParam).replace(/\D/g, '')
      if (digits.length === 8) {
        startDate.value = `${digits.slice(0, 4)}-${digits.slice(4, 6)}-${digits.slice(6, 8)}`
      }
    } else if (savedRoamingParams.start) {
      startDate.value = formatYmd(savedRoamingParams.start)
    } else if (savedStartDate) {
      startDate.value = savedStartDate
    }
  } else {
    // dateTimeRange: yyyyMMddHH:yyyyMMdd 형식 복원
    if (ftrNewParam && String(ftrNewParam).includes(':')) {
      const parts = String(ftrNewParam).split(':')
      const startPart = parts[0] || ''
      const endPart = parts[1] || ''

      if (startPart.length >= 10) {
        const year = startPart.slice(0, 4)
        const month = startPart.slice(4, 6)
        const day = startPart.slice(6, 8)
        const hour = startPart.slice(8, 10)
        startDate.value = `${year}-${month}-${day}`
        startHour.value = String(hour).padStart(2, '0')
      }

      if (endPart.length === 8) {
        const year = endPart.slice(0, 4)
        const month = endPart.slice(4, 6)
        const day = endPart.slice(6, 8)
        endDate.value = `${year}-${month}-${day}`
      }
    } else if (savedRoamingParams.start || savedRoamingParams.end) {
      const startDigits = String(savedRoamingParams.start || '').replace(/\D/g, '')
      startDate.value = formatYmd(startDigits)
      startHour.value = startDigits.length >= 10 ? startDigits.slice(8, 10) : '00'
      endDate.value = formatYmd(savedRoamingParams.end)
    } else if (savedStartDate && savedEndDate) {
      startDate.value = savedStartDate
      startHour.value = savedStartHour || '00'
      endDate.value = savedEndDate
    }
  }
}

watch(() => props.modelValue, (isOpen) => {
  if (isOpen) {
    initializeFromSettingData()
  }
}, { immediate: true })

// 시간 입력값 검증 (0-23)
const validateHour = () => {
  let hour = parseInt(startHour.value, 10)
  if (isNaN(hour) || hour < 0) {
    startHour.value = '00'
  } else if (hour > 23) {
    startHour.value = '23'
  } else {
    startHour.value = String(hour).padStart(2, '0')
  }
}

// ftrNewParam 계산
const ftrNewParam = computed(() => {
  if (props.variant === 'date8') {
    // 기존 동작: yyyyMMdd 형식
    return startDate.value ? startDate.value.replace(/\D/g, '') : ''
  } else {
    // dateTimeRange: yyyyMMddHH:yyyyMMdd 형식
    if (!startDate.value || !endDate.value) return ''
    const startDateFormatted = startDate.value.replace(/\D/g, '')
    const endDateFormatted = endDate.value.replace(/\D/g, '')
    const hour = String(startHour.value || '00').padStart(2, '0')
    return `${startDateFormatted}${hour}:${endDateFormatted}`
  }
})

const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onReset = () => {
  initializeFromSettingData(
    Object.keys(props.initialSettingData).length ? props.initialSettingData : props.settingData,
  )
}

const validateDate8 = () => {
  const start = toDate(startDate.value)
  if (!startDate.value || !start) {
    return '시작일을 선택해 주세요.'
  }
  if (isBefore(start, today())) {
    return '시작일은 오늘 이후로 선택해 주세요.'
  }
  if (isAfter(start, addMonths(today(), 2))) {
    return '시작일은 2개월 이내로 선택해 주세요.'
  }
  return ''
}

const toStartDateTime = () => {
  const start = toDate(startDate.value)
  if (!start) return null
  const hour = Number.parseInt(startHour.value || '00', 10)
  start.setHours(Number.isNaN(hour) ? 0 : hour, 0, 0, 0)
  return start
}

const validateDateTimeRange = () => {
  const start = toDate(startDate.value)
  const end = toDate(endDate.value)
  if (!startDate.value || !start) {
    return '시작일을 선택해 주세요.'
  }
  if (!endDate.value || !end) {
    return '종료일을 선택해 주세요.'
  }
  validateHour()
  const startDateTime = toStartDateTime()
  if (!startDateTime || isBefore(startDateTime, new Date())) {
    return '시작시간은 현재 시간 이후로 선택해 주세요.'
  }
  if (isBefore(start, today())) {
    return '시작일은 오늘 이후로 선택해 주세요.'
  }
  if (isAfter(start, addMonths(today(), 2))) {
    return '시작일은 2개월 이내로 선택해 주세요.'
  }
  if (isAfter(end, addMonths(today(), 6))) {
    return '종료일은 6개월 이내로 선택해 주세요.'
  }
  if (!isBefore(start, end)) {
    return '종료일은 시작일보다 이후로 선택해 주세요.'
  }
  return ''
}

const onConfirm = () => {
  const validationMessage =
    props.variant === 'date8' ? validateDate8() : validateDateTimeRange()

  if (validationMessage) {
    showAlert(validationMessage)
    return
  }

  if (props.variant === 'date8') {
    emit('confirm', { ftrNewParam: ftrNewParam.value, startDate: startDate.value })
  } else {
    emit('confirm', { ftrNewParam: ftrNewParam.value, startDate: startDate.value, startHour: startHour.value, endDate: endDate.value })
  }
  onClose()
}
</script>

<style lang="scss" scoped></style>
