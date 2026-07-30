<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    :title="dialogTitle"
    @open="emit('open')"
    @close="onClose"
    size="medium"
    class-name="roaming-share-main-dialog"
  >
    <MsfTitleArea title="이용기간 설정(한국시간 기준)" level="2" color="black" noline bold>
      <template #content v-if="isMain2">
        <MsfTextList
          :items="['신청한 시간부터 24시간 동안 적용']"
          type="none"
          margin="0"
          color="mint"
        />
      </template>
    </MsfTitleArea>
    <MsfStack type="field" vertical>
      <MsfDateInput
        v-model="formData.startDate"
        placeholder="날짜선택"
        class="ut-w100p"
        :min-date="formatDateInput(today())"
        :max-date="valMaxDate()"
      />
      <MsfSelect
        v-if="isMain2"
        v-model="formData.startHour"
        :options="timeOptions"
        placeholder="시작시간 선택"
        class="ut-w100p"
      />
      <MsfDateInput
        v-model="formData.endDate"
        placeholder="날짜선택"
        class="ut-w100p"
        :disabled="!isMain2"
        :min-date="formData.startDate"
      />
    </MsfStack>

    <MsfTitleArea title="회선 설정" level="2" color="black" noline bold />
    <MsfStack type="field" vertical>
      <MsfFormGroup label="<em>대표 번호</em>" vertical>
        <MsfNumberInput :model-value="mainPhoneNumber" disabled class="ut-w100p" />
      </MsfFormGroup>
      <MsfFormGroup
        label="<em class='ut-w100p ut-nowrap'>서브 번호 (데이터를 함께 이용하실 추가 고객 등록)</em>"
        vertical
        tag="div"
      >
        <MsfStack type="field" vertical>
          <MsfNumberInput
            v-for="(_, index) in subNumbers"
            :key="`${subNumbersKey}-${index}`"
            :model-value="subNumbers[index]"
            @update:model-value="(val) => updateSubNumber(index, val)"
            :placeholder="getSubNumberPlaceholder(index)"
            maxlength="11"
            class="ut-w100p"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>

    <MsfBox>
      <MsfTextList :items="guideText" margin="0" level="2" />
    </MsfBox>

    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton
          v-if="props.settingData?.showChangeCancel"
          variant="tertiary"
          @click="onReset"
          >변경취소</MsfButton
        >
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { computed, reactive, ref, watch } from 'vue'
import { addMonths, isAfter, isBefore } from 'date-fns'
import { toDate } from '@/libs/utils/date.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { normalizePhone, validateMobile } from '@/libs/utils/string.utils'
import { useMsfUserStore } from '@/stores/msf_user.js'
import { useMsfLoadingStore } from '@/stores/msf_loading'

const loadingStore = useMsfLoadingStore()

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  variant: { type: String, default: 'main1' }, // main1 | main2
  mainPhoneNumber: { type: String, default: '' },
  settingData: { type: Object, default: () => ({}) },
  initialSettingData: { type: Object, default: () => ({}) },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const formData = reactive({
  startDate: '',
  endDate: '',
  startHour: '',
})
const subNumbers = ref([])
const subNumbersKey = ref(0)
const isInitializing = ref(false)

const isMain2 = computed(() => props.variant === 'main2')
const maxSubCount = computed(() => (isMain2.value ? 3 : 4))
const minSubCount = computed(() => (isMain2.value ? 1 : 0))
const dialogTitle = computed(() =>
  isMain2.value ? '하루종일 로밍 베이직 투게더 대표 설정' : '함께쓰는 로밍 대표 설정',
)
const guideText = computed(() => [
  `추가 고객은 kt M모바일 사용 휴대폰 번호에 한해 최대 ${maxSubCount.value}개까지 등록 가능합니다.`,
  '추가 등록된 고객님도 로밍 서브 상품을 별도로 가입하셔야 합니다',
  '데이터/음성 로밍 차단 상품에 가입되어 있으면 해당 상품을 먼저 해지하셔야 합니다.',
  '신청 후 데이터 미 사용 시, 요금은 청구되지 않습니다.',
  '서브회선의 신청/가입 여부는 대표회선에서 확인이 불가합니다. 서브회선 이용 고객에게 확인 바랍니다.',
  //'서브회선 고객님이 데이터 함께ON 서브 상품에 별도로 가입해야 무료 적용이 가능합니다.',
  //'부가서비스 신청과 변경은 한건씩 신청 가능합니다.',
])
const timeOptions = Array.from({ length: 24 }, (_, hour) => {
  const value = String(hour).padStart(2, '0')
  return { value, label: `${value}시` }
})

const today = () => {
  const date = new Date()
  date.setHours(0, 0, 0, 0)
  return date
}

const valMaxDate = () => {
  const date = new Date()
  date.setDate(date.getDate() + 60)
  return formatDateInput(date)
}

const formatYmd = (value) => String(value || '').replace(/\D/g, '')
const formatDateInput = (date) => {
  const yyyy = date.getFullYear()
  const mm = String(date.getMonth() + 1).padStart(2, '0')
  const dd = String(date.getDate()).padStart(2, '0')
  return `${yyyy}-${mm}-${dd}`
}
const getUsePeriodDays = () => {
  const value = Number(props.settingData.usePeriodDays || props.settingData.usePrd || 15)
  return Number.isFinite(value) && value > 0 ? value : 15
}
const getEndDateByStartDate = (startDate) => {
  const date = toDate(startDate)
  if (!date) return ''
  date.setDate(date.getDate() + getUsePeriodDays() - 1)
  return formatDateInput(date)
}
const getSubNumberPlaceholder = (index) =>
  isMain2.value && index === 0 ? '서브 휴대폰번호 입력 (필수)' : '서브 휴대폰번호 입력 (선택)'

const setEmptySubNumbers = () => {
  subNumbers.value = Array.from({ length: maxSubCount.value }, () => '')
}

const updateSubNumber = (index, val) => {
  const copy = [...subNumbers.value]
  copy[index] = val
  subNumbers.value = copy
}

const parseParams = (ftrNewParam) => {
  const paramText = String(ftrNewParam || '')
  formData.endDate = ''
  if (paramText.includes('STRT_DT=')) {
    const params = Object.fromEntries(
      paramText
        .split('|')
        .filter(Boolean)
        .map((item) => {
          const separatorIndex = item.indexOf('=')
          return separatorIndex < 0
            ? [item, '']
            : [item.slice(0, separatorIndex), item.slice(separatorIndex + 1)]
        }),
    )
    const startPart = params.STRT_DT || ''
    if (startPart.length >= 8) {
      formData.startDate = `${startPart.slice(0, 4)}-${startPart.slice(4, 6)}-${startPart.slice(6, 8)}`
      formData.startHour = startPart.length >= 10 ? startPart.slice(8, 10) : '00'
    }
    const endPart = params.END_DT || ''
    if (endPart.length >= 8) {
      formData.endDate = `${endPart.slice(0, 4)}-${endPart.slice(4, 6)}-${endPart.slice(6, 8)}`
    }
    subNumbers.value = Array.from(
      { length: maxSubCount.value },
      (_, index) => params[`SHARE_SUB_CONTID${index + 1}`] || '',
    )
    subNumbersKey.value++
    return
  }

  // 기존 콜론 구분 형식 호환
  const parts = paramText.split(':')
  const startPart = parts[0] || ''
  if (startPart.length >= 8) {
    formData.startDate = `${startPart.slice(0, 4)}-${startPart.slice(4, 6)}-${startPart.slice(6, 8)}`
    formData.startHour = startPart.length >= 10 ? startPart.slice(8, 10) : ''
  }
  const endPart = parts[1] || ''
  const hasEndPart = endPart.length === 8
  if (hasEndPart) {
    formData.endDate = `${endPart.slice(0, 4)}-${endPart.slice(4, 6)}-${endPart.slice(6, 8)}`
  }
  const numberStartIndex = hasEndPart ? 2 : 1
  subNumbers.value = Array.from(
    { length: maxSubCount.value },
    (_, index) => parts[index + numberStartIndex] || '',
  )
  subNumbersKey.value++
}

const applySavedSubNumbers = (settingData = props.settingData) => {
  const savedSubNumbers = Array.isArray(settingData.subNumbers)
    ? settingData.subNumbers
    : Array.isArray(settingData.shareSubCtnList)
      ? settingData.shareSubCtnList
      : []
  if (savedSubNumbers.length > 0) {
    subNumbers.value = Array.from(
      { length: maxSubCount.value },
      (_, i) => normalizePhone(savedSubNumbers[i]) || '',
    )
    subNumbersKey.value++
  }
}

const parseDateFromYmdHms = (ymdHms = '') => {
  const s = String(ymdHms)
  if (s.length < 8) return {}
  return {
    date: `${s.slice(0, 4)}-${s.slice(4, 6)}-${s.slice(6, 8)}`,
    hour: s.length >= 10 ? s.slice(8, 10) : '',
  }
}

const initializeFromSettingData = (settingData = props.settingData) => {
  isInitializing.value = true
  try {
    formData.startDate = settingData.startDate || formatDateInput(today())
    formData.endDate =
      settingData.endDate || (isMain2.value ? '' : getEndDateByStartDate(formData.startDate))
    formData.startHour = settingData.startHour || (isMain2.value ? '' : '00')
    setEmptySubNumbers()

    const ftrNewParam = settingData.ftrNewParam
    if (ftrNewParam) {
      parseParams(ftrNewParam)
      if (!isMain2.value && !formData.endDate) {
        formData.endDate = getEndDateByStartDate(formData.startDate)
      }
      if (!isMain2.value && !formData.startHour) formData.startHour = '00'
      applySavedSubNumbers(settingData)
      return
    }

    // Oracle X97 기존 가입 데이터 (strtDt/endDt 형식)
    const { date: sDate, hour: sHour } = parseDateFromYmdHms(settingData.strtDt)
    const { date: eDate } = parseDateFromYmdHms(settingData.endDt)
    if (sDate) {
      formData.startDate = sDate
      formData.startHour = sHour || (isMain2.value ? '' : '00')
    }
    if (eDate) formData.endDate = eDate
    if (!isMain2.value && !formData.endDate) {
      formData.endDate = getEndDateByStartDate(formData.startDate)
    }

    applySavedSubNumbers(settingData)
  } finally {
    isInitializing.value = false
  }
}

watch(
  () => props.modelValue,
  (isOpen) => {
    if (isOpen) {
      initializeFromSettingData()
    }
  },
  { immediate: true },
)

watch(
  () => props.variant,
  () => {
    if (props.modelValue) initializeFromSettingData()
  },
)

watch(
  () => formData.startDate,
  (startDate) => {
    if (props.modelValue && startDate && !isMain2.value && !isInitializing.value) {
      formData.endDate = getEndDateByStartDate(startDate)
    }
  },
)

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

const validateDates = () => {
  const start = toDate(formData.startDate)
  if (!formData.startDate || !start) return '시작일을 선택해 주세요.'
  if (isBefore(start, today())) return '시작일은 오늘 이후로 선택해 주세요.'
  if (isAfter(start, addMonths(today(), 2))) return '시작일은 2개월 이내로 선택해 주세요.'
  if (!isMain2.value) return ''

  if (!formData.startHour) return '시작시간을 선택해 주세요.'

  start.setHours(parseInt(formData.startHour, 10), 0, 0, 0)
  if (start < new Date()) {
    return '오늘 시작하는 경우 현재 시간 이후로 선택해 주세요.'
  }

  const end = toDate(formData.endDate)
  if (!formData.endDate || !end) return '종료일을 선택해 주세요.'
  if (!isBefore(start, end)) return '종료일은 시작일 이후로 선택해 주세요.'
  if (isAfter(end, addMonths(today(), 6))) return '종료일은 6개월 이내로 선택해 주세요.'
  return ''
}

const getValidSubNumbers = () => {
  const numbers = subNumbers.value.map(normalizePhone)
  const filled = numbers.filter(Boolean)

  if (filled.length < minSubCount.value) {
    return { message: '추가 휴대폰 번호를 1개 이상 입력해 주세요.' }
  }

  const hasGap = numbers.some((number, index) => !number && numbers.slice(index + 1).some(Boolean))
  if (hasGap) {
    return { message: '추가 휴대폰 번호는 1번부터 순서대로 입력하세요.' }
  }

  const invalidLengthIndex = numbers.findIndex((number) => number && number.length !== 11)
  if (invalidLengthIndex >= 0) {
    return { message: `서브번호 ${invalidLengthIndex + 1}번은 휴대폰 번호 11자리를 입력해 주세요.` }
  }

  const invalidFormatIndex = numbers.findIndex((number) => number && !validateMobile(number))
  if (invalidFormatIndex >= 0) {
    return {
      message: `서브번호 ${invalidFormatIndex + 1}번의 형식이 올바르지 않습니다. 010, 011, 016, 017, 018, 019로 시작하는 번호를 입력해 주세요.`,
    }
  }

  if (new Set(filled).size !== filled.length) {
    return { message: '같은 번호를 중복으로 입력할 수 없습니다.' }
  }

  return { numbers: filled }
}

const checkSubNumbers = async (numbers) => {
  const ncn = props.ncn || props.settingData?.ncn || props.settingData?.contractNum || ''

  const baseUrl = `${import.meta.env.VITE_MSF_BASE_URL || ''}`.replace(/\/$/, '')
  const userStore = useMsfUserStore()
  const headers = {
    'Content-Type': 'application/json',
    Accept: 'application/json',
  }
  if (userStore.token) {
    headers.Authorization = `Bearer ${userStore.token}`
  }

  loadingStore.showLoading()
  const response = await fetch(`${baseUrl}/api/form/servicechange/roaming/checkMobileJoin`, {
    method: 'POST',
    headers,
    credentials: 'include',
    body: JSON.stringify({
      ncn,
      joinPhone: [...numbers].join(':'),
    }),
  })
  const res = await response.json().catch(() => null)
  const formResponse = Array.isArray(res?.data) ? res.data[0] : res?.data
  const resData = formResponse?.resData || {}
  loadingStore.hideLoading()

  if (!response.ok || res?.code !== '0000' || formResponse?.resCode !== '0000') {
    const message =
      formResponse?.resMessage ||
      res?.message ||
      '입력한 휴대폰번호의 상품일련번호를 확인할 수 없습니다.'
    throw new Error(message)
  }

  return String(resData.mtProdHstSeq || resData.prodHstSeq || '').replace(/\D/g, '')
}

const createFtrNewParam = (numbers) => {
  const startYmd = formatYmd(formData.startDate)
  if (isMain2.value) {
    // PL2079777 ASIS 화면 형식: 시작일시:종료일:서브휴대폰번호...
    return [`${startYmd}${formData.startHour}`, formatYmd(formData.endDate), ...numbers].join(':')
  }

  // PL199N109 상품 원장은 STRT_DT에 날짜(yyyyMMdd)만 허용한다.
  return [startYmd, ...numbers].join(':')
}

const onConfirm = async () => {
  const dateMessage = validateDates()
  if (dateMessage) {
    showAlert(dateMessage)
    return
  }

  const subResult = getValidSubNumbers()
  if (subResult.message) {
    showAlert(subResult.message)
    return
  }

  if (subResult.numbers && subResult.numbers.length > 0) {
    try {
      await checkSubNumbers(subResult.numbers)
    } catch (error) {
      showAlert(error?.message || '입력한 휴대폰번호의 상품일련번호를 확인할 수 없습니다.')
      return
    }
  }

  emit('confirm', {
    ftrNewParam: createFtrNewParam(subResult.numbers),
    startDate: formData.startDate,
    endDate: formData.endDate,
    startHour: formData.startHour,
    subNumbers: subResult.numbers,
  })
  onClose()
}
</script>

<style lang="scss" scoped>
:global(.roaming-share-main-dialog.dialog-size-medium) {
  max-height: rem(700px);
}
</style>
