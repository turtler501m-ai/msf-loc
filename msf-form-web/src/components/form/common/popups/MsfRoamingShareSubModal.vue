<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    :title="dialogTitle"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <!-- sub2 전용: 이용기간 설정 -->
    <template v-if="isSub2">
      <MsfTitleArea title="이용기간 설정(한국시간 기준)" level="2" color="black" noline bold>
        <template #content>
          <MsfTextList
            :items="['신청한 시간부터 24시간 동안 적용']"
            type="none"
            margin="0"
            color="mint"
          />
        </template>
      </MsfTitleArea>
      <MsfStack type="field" vertical>
        <MsfDateRange
          v-model:from="formData.startDate"
          v-model:from-max-date="isSub2Max"
          v-model:to="formData.endDate"
          class="ut-w100p"
        />
        <MsfSelect
          title="시작 시간 선택"
          v-model="formData.startHour"
          :options="timeOptions"
          placeholder="시작 시간 선택"
          class="ut-w100p"
          v-if="!isSub2"
        />
      </MsfStack>
    </template>

    <!-- 대표번호 입력 -->
    <MsfStack vertical type="formgroups" class="ut-mt-base">
      <MsfFormGroup label="<em>대표번호</em>" vertical>
        <MsfStack type="field" class="ut-w100p">
          <MsfNumberInput
            v-model="formData.mainNumber"
            placeholder="대표 휴대폰번호 입력 (필수)"
            maxlength="11"
            class="ut-w100p"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>

    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton v-if="props.settingData?.showChangeCancel" variant="tertiary" @click="onReset"
          >변경취소</MsfButton
        >
        <MsfButton variant="primary" :disabled="isCheckingMainProduct" @click="onConfirm"
          >확인</MsfButton
        >
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { computed, reactive, ref, watch } from 'vue'
import { addMonths, isAfter, isBefore } from 'date-fns'
import { useMsfUserStore } from '@/stores/msf_user'
import { toDate } from '@/libs/utils/date.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { normalizePhone } from '@/libs/utils/string.utils'

const isValidMobileNumber2 = (value) => /^01\d{9}$/.test(normalizePhone(value))

import { useMsfLoadingStore } from '@/stores/msf_loading'
const loadingStore = useMsfLoadingStore()

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  variant: { type: String, default: 'sub1' }, // 'sub1' | 'sub2'
  serviceName: { type: String, default: '' },
  settingData: { type: Object, default: () => ({}) },
  initialSettingData: { type: Object, default: () => ({}) },
  ncn: { type: String, default: '' },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const isSub2 = computed(() => props.variant === 'sub2')
const isSub2Max = computed(() => (isSub2.value ? valMaxDate() : null))
const dialogTitle = computed(() =>
  isSub2.value ? '하루종일 로밍 베이직 투게더(서브)' : props.serviceName || '부가서비스명',
)
const isCheckingMainProduct = ref(false)

const formData = reactive({
  startDate: '',
  endDate: '',
  startHour: '00',
  mainNumber: '',
})

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

const formatYmd = (value) => String(value || '').replace(/\D/g, '')
const formatTodayYmd = () => {
  const date = today()
  const yyyy = date.getFullYear()
  const mm = String(date.getMonth() + 1).padStart(2, '0')
  const dd = String(date.getDate()).padStart(2, '0')
  return `${yyyy}${mm}${dd}`
}
const getFtrNewParamParts = () => String(props.settingData?.ftrNewParam || '').split(':')
const getSavedMainNumber = () => {
  const parts = getFtrNewParamParts()
  return (
    normalizePhone(isSub2.value ? parts[2] : parts[0]) ||
    normalizePhone(props.settingData?.mainNumber)
  )
}
const getSavedMainProductSeq = (phone) => {
  const parts = getFtrNewParamParts()
  const savedPhone = getSavedMainNumber()

  if (savedPhone && savedPhone !== phone) return ''
  if (isSub2.value && parts[3]) return String(parts[3]).replace(/\D/g, '')
  if (!isSub2.value && parts[1]) return String(parts[1]).replace(/\D/g, '')

  if (!savedPhone || savedPhone !== phone) return ''
  return String(
    props.settingData?.mainProductSeq || props.settingData?.shareMainProdHstSeq || '',
  ).replace(/\D/g, '')
}

const SUB_TO_MAIN_PRODUCT_MAP = {
  PL199N117: 'PL199N109',
  PL199N121: 'PL199N120',
  PL199N123: 'PL199N122',
  PL199N127: 'PL199N126',
  PL199N130: 'PL199N129',
  PL199N133: 'PL199N132',
  PL2079778: 'PL2079777',
}

const getMainProductCode = () => {
  const subCode = String(
    props.settingData?.rateCd || props.settingData?.value || props.settingData?.soc || '',
  ).toUpperCase()
  return SUB_TO_MAIN_PRODUCT_MAP[subCode] || (isSub2.value ? 'PL2079777' : '')
}

const getPeriodForMainProductLookup = () => {
  if (isSub2.value) {
    return {
      strtDt: formatYmd(formData.startDate),
      endDt: formatYmd(formData.endDate),
    }
  }
  const ymd = formatTodayYmd()
  return { strtDt: ymd, endDt: ymd }
}

const requestMainProductSeq = async (phone) => {
  const savedSeq = getSavedMainProductSeq(phone)
  if (savedSeq) return savedSeq

  const ncn = props.ncn || props.settingData?.ncn || props.settingData?.contractNum || ''
  const mtCd = getMainProductCode()
  const { strtDt, endDt } = getPeriodForMainProductLookup()

  if (!ncn || !mtCd || !strtDt || !endDt) return ''

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
  const response = await fetch(`${baseUrl}/api/form/servicechange/roaming/mainProdHstSeq`, {
    method: 'POST',
    headers,
    credentials: 'include',
    body: JSON.stringify({
      ncn,
      mtPhone: phone,
      mtCd,
      strtDt,
      endDt,
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

const parseDateFromYmdHms = (ymdHms = '') => {
  const s = String(ymdHms)
  if (s.length < 8) return {}
  return {
    date: `${s.slice(0, 4)}-${s.slice(4, 6)}-${s.slice(6, 8)}`,
    hour: s.length >= 10 ? s.slice(8, 10) : '',
  }
}

const initializeFromSettingData = (settingData = props.settingData) => {
  formData.mainNumber = ''
  formData.startDate = ''
  formData.endDate = ''
  formData.startHour = '00'

  const { ftrNewParam } = settingData
  if (ftrNewParam) {
    const parts = String(ftrNewParam).split(':')

    if (isSub2.value) {
      // sub2: yyyyMMdd:yyyyMMdd:대표번호
      const startPart = parts[0] || ''
      const endPart = parts[1] || ''
      if (startPart.length >= 8) {
        formData.startDate = `${startPart.slice(0, 4)}-${startPart.slice(4, 6)}-${startPart.slice(6, 8)}`
      }
      if (endPart.length === 8) {
        formData.endDate = `${endPart.slice(0, 4)}-${endPart.slice(4, 6)}-${endPart.slice(6, 8)}`
      }
      formData.mainNumber = parts[2] || ''
    } else {
      // sub1: 대표번호
      formData.mainNumber = parts[0] || ''
    }
  } else if (isSub2.value) {
    // Oracle X97 기존 가입 데이터 (strtDt/endDt 형식)
    const { date: sDate, hour: sHour } = parseDateFromYmdHms(settingData.strtDt)
    const { date: eDate } = parseDateFromYmdHms(settingData.endDt)
    if (sDate) {
      formData.startDate = sDate
      formData.startHour = sHour || '00'
    }
    if (eDate) formData.endDate = eDate
  }

  if (!formData.mainNumber) {
    formData.mainNumber = normalizePhone(
      settingData?.mainNumber || settingData?.shareMainCtn,
    )
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
  () => formData.startDate,
  (startDate) => {
    if (props.modelValue && startDate && isSub2.value) {
      formData.endDate = getEndDateByStartDate(startDate)
    }
  },
)

const valMaxDate = () => {
  const date = new Date()
  date.setDate(date.getDate() + 60)
  return formatDateInput(date)
}
const formatDateInput = (date) => {
  const yyyy = date.getFullYear()
  const mm = String(date.getMonth() + 1).padStart(2, '0')
  const dd = String(date.getDate()).padStart(2, '0')
  return `${yyyy}-${mm}-${dd}`
}
const getEndDateByStartDate = (startDate) => {
  const date = toDate(startDate)
  if (!date) return ''
  date.setDate(date.getDate() + 1)
  return formatDateInput(date)
}

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

const validatePhone = () => {
  const phone = normalizePhone(formData.mainNumber)
  if (!phone) return '대표 휴대폰번호를 입력해 주세요.'
  if (!isValidMobileNumber2(phone)) return '유효한 휴대폰번호를 입력해 주세요.'
  return ''
}

const validateDates = () => {
  const start = toDate(formData.startDate)
  const end = toDate(formData.endDate)
  if (!formData.startDate || !start) return '시작일을 선택해 주세요.'
  if (!formData.endDate || !end) return '종료일을 선택해 주세요.'
  if (!isSub2.value && !formData.startHour) return '시작시간을 선택해 주세요.'
  if (isBefore(start, today())) return '시작일은 오늘 이후로 선택해 주세요.'
  if (isAfter(start, addMonths(today(), 2))) return '시작일은 2개월 이내로 선택해 주세요.'
  if (isAfter(end, addMonths(today(), 6))) return '종료일은 6개월 이내로 선택해 주세요.'
  if (!isBefore(start, end)) return '종료일은 시작일보다 이후로 선택해 주세요.'

  if (isSub2.value) {
    return ''
  }

  // 시작일이 현재 이후인지 검증
  start.setHours(parseInt(formData.startHour, 10), 0, 0, 0)
  if (start < new Date()) {
    return '현재 시간 이후로 선택해 주세요.'
  }
  return ''
}

const createFtrNewParam = (phone, mainProductSeq) => {
  if (isSub2.value) {
    // sub2: yyyyMMdd:yyyyMMdd:대표번호:대표번호상품일련번호
    const startYmd = formatYmd(formData.startDate)
    const endYmd = formatYmd(formData.endDate)
    return [startYmd, endYmd, phone, mainProductSeq].join(':')
  }
  // sub1: 대표번호:대표번호상품일련번호
  return [phone, mainProductSeq].join(':')
}

const onConfirm = async () => {
  if (isCheckingMainProduct.value) return

  const dateMessage = isSub2.value ? validateDates() : ''
  if (dateMessage) {
    showAlert(dateMessage)
    return
  }

  const phoneMessage = validatePhone()
  if (phoneMessage) {
    showAlert(phoneMessage)
    return
  }

  const phone = normalizePhone(formData.mainNumber)

  let mainProductSeq
  try {
    isCheckingMainProduct.value = true
    mainProductSeq = await requestMainProductSeq(phone)
  } catch (error) {
    showAlert(error?.message || '입력한 휴대폰번호의 상품일련번호를 확인할 수 없습니다.')
    return
  } finally {
    isCheckingMainProduct.value = false
  }

  if (!mainProductSeq) {
    showAlert('입력한 휴대폰번호의 상품일련번호를 확인할 수 없습니다.')
    return
  }

  const payload = {
    ftrNewParam: createFtrNewParam(phone, mainProductSeq),
    mainNumber: phone,
    mainProductSeq,
  }

  if (isSub2.value) {
    payload.startDate = formData.startDate
    payload.endDate = formData.endDate
    payload.startHour = formData.startHour
  }

  emit('confirm', payload)
  onClose()
}
</script>

<style lang="scss" scoped></style>
