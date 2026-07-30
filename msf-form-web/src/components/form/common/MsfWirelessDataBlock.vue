<script setup>
import { computed, defineModel, onMounted, ref, watch } from 'vue'
import { useMsfUserStore } from '@/stores/msf_user'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'

const model = defineModel({ type: Object, required: true })
const emit = defineEmits(['ready'])
const userStore = useMsfUserStore()

const wirelessBlockInUse = ref(null)
const isConfirmCompleted = ref(false)
const isLoading = ref(false)

const hasSelection = computed(() => !!model.value.blockService)
const confirmButtonLabel = computed(() => (isConfirmCompleted.value ? '확인완료' : '확인'))

const chipData = computed(() => [
  {
    value: 'blockService1',
    label: '무선데이터 이용',
    disabled: wirelessBlockInUse.value !== true,
  },
  {
    value: 'blockService2',
    label: '무선데이터차단 서비스 이용',
    disabled: wirelessBlockInUse.value !== false,
  },
])

const setConfirmState = (completed) => {
  isConfirmCompleted.value = completed
  model.value.wirelessBlockConfirmCompleted = completed
}

const isSelectionAvailable = () => {
  if (model.value.blockService === 'blockService1') {
    return wirelessBlockInUse.value === true
  }
  if (model.value.blockService === 'blockService2') {
    return wirelessBlockInUse.value === false
  }
  return false
}

const getWirelessStatusMessage = () => {
  if (wirelessBlockInUse.value === null) return ''
  return wirelessBlockInUse.value
    ? '현재 무선데이터차단 서비스 이용 중입니다.'
    : '현재 무선데이터 이용 중입니다.'
}

const findResponseField = (source, fieldNames, depth = 0) => {
  if (!source || typeof source !== 'object' || depth > 5) return ''
  for (const fieldName of fieldNames) {
    if (source[fieldName] !== undefined && source[fieldName] !== null && source[fieldName] !== '') {
      return source[fieldName]
    }
  }
  for (const value of Object.values(source)) {
    const found = findResponseField(value, fieldNames, depth + 1)
    if (found !== '') return found
  }
  return ''
}

const normalizeFailedServiceIds = (value) => {
  if (Array.isArray(value)) return value.map((id) => String(id)).filter(Boolean)
  if (typeof value === 'string') {
    return value
      .split(',')
      .map((id) => id.trim())
      .filter(Boolean)
  }
  return value ? [String(value)] : []
}

const getPreCheckFailureMessage = (res) => {
  const code = String(res?.code || '')
  const formResponse = res?.data || {}
  const resCode = String(formResponse?.resCode || '')
  const preCheckData = formResponse?.resData || {}
  const rsltCd = String(preCheckData?.rsltCd || '')
  const resultCode = String(preCheckData?.resultCode || '')
  const sbscYn = String(preCheckData?.sbscYn || '').toUpperCase()
  const failedServiceIds = [
    ...normalizeFailedServiceIds(preCheckData?.preCheckFailedPrdcCdList),
    ...normalizeFailedServiceIds(preCheckData?.onlineCancelUnavailablePrdcCdList),
    ...normalizeFailedServiceIds(preCheckData?.prdcCdList),
    ...normalizeFailedServiceIds(preCheckData?.prdcCd || preCheckData?.soc),
  ]

  if (code === '0000' && !res?.data) {
    return '무선데이터차단 서비스 가입 가능 여부를 확인할 수 없습니다.'
  }
  if (code && code !== '0000') {
    return findResponseField(res, ['message', 'resMessage', 'resltMsg']) || '무선데이터차단 서비스 가입이 불가합니다.'
  }
  if (resCode && resCode !== '0000') {
    return formResponse?.resMessage || findResponseField(res, ['resltMsg', 'svcMsg', 'message']) || '무선데이터차단 서비스 가입이 불가합니다.'
  }
  if (rsltCd && rsltCd !== '0000') {
    return preCheckData?.resltMsg || preCheckData?.svcMsg || formResponse?.resMessage || '무선데이터차단 서비스 가입이 불가합니다.'
  }
  if (resultCode && resultCode !== '0000') {
    return preCheckData?.resltMsg || preCheckData?.svcMsg || formResponse?.resMessage || '무선데이터차단 서비스 가입이 불가합니다.'
  }
  if (sbscYn && sbscYn !== 'Y') {
    return preCheckData?.resltMsg || preCheckData?.svcMsg || formResponse?.resMessage || '무선데이터차단 서비스 가입이 불가합니다.'
  }
  if (failedServiceIds.length > 0) {
    return preCheckData?.resltMsg || preCheckData?.svcMsg || formResponse?.resMessage || '무선데이터차단 서비스 가입이 불가합니다.'
  }
  return ''
}

const getWirelessPreCheckTreatmentCode = () => {
  if (model.value.blockService === 'blockService2') return 'A'
  if (model.value.blockService === 'blockService1') return 'C'
  return ''
}

const getPhoneNo = () =>
  `${model.value.deviceChgTel1 || ''}${model.value.deviceChgTel2 || ''}${model.value.deviceChgTel3 || ''}`

const getNcn = () => model.value.ncn || model.value.contractNum || ''

const getApiRequestOptions = (body) => {
  const baseUrl = `${import.meta.env.VITE_MSF_BASE_URL || ''}`.replace(/\/$/, '')
  const headers = { 'Content-Type': 'application/json', Accept: 'application/json' }
  if (userStore.token) headers.Authorization = `Bearer ${userStore.token}`

  return {
    baseUrl,
    options: {
      method: 'POST',
      headers,
      credentials: 'include',
      body: JSON.stringify(body),
    },
  }
}

const postWirelessPreCheck = async () => {
  const phoneNo = getPhoneNo()
  const ncn = getNcn()
  const prdcSbscTrtmCd = getWirelessPreCheckTreatmentCode()

  if (!ncn || !phoneNo || !prdcSbscTrtmCd) {
    return { isSuccess: false, message: '서비스계약번호 또는 휴대폰번호가 없어 처리할 수 없습니다.' }
  }

  const payload = {
    ncn,
    ctn: phoneNo,
    custId: model.value.custId || '',
    actCode: 'SRG',
    prdcList: [
      {
        prdcCd: 'WIRELESSC',
        prdcSbscTrtmCd,
        prdcTypeCd: 'R',
        prdcSeqNo: '',
        ftrNewParam: '',
      },
    ],
  }
  const { baseUrl, options } = getApiRequestOptions(payload)

  try {
    const response = await fetch(`${baseUrl}/api/form/servicechange/moscPrdcTrtmPreChk`, options)

    const data = await response.json().catch(() => null)
    const result = response.ok ? data : data || { code: String(response.status), message: response.statusText }
    const failureMessage = getPreCheckFailureMessage(result)

    return {
      isSuccess: !failureMessage,
      message: failureMessage,
      result,
    }
  } catch (e) {
    console.error('[무선데이터차단] 사전체크 실패', e)
    return {
      isSuccess: false,
      message: '무선데이터차단 서비스 가입 가능 여부 확인 중 오류가 발생했습니다.',
    }
  }
}

const fetchAvailableList = async () => {
  const phoneNo = getPhoneNo()
  const ncn = getNcn()
  if (!ncn) return null

  try {
    const { baseUrl, options } = getApiRequestOptions({ ncn, ctn: phoneNo, custId: model.value.custId || '' })
    const response = await fetch(`${baseUrl}/api/form/servicechange/availablelist`, options)
    const data = await response.json().catch(() => null)
    const resData = data?.data?.resData
    if (resData != null) {
      wirelessBlockInUse.value = resData.wirelessBlockInUse === true
      setConfirmState(false)
      return resData
    }
  } catch (e) {
    console.error('[무선데이터차단] availablelist 조회 실패', e)
  }

  return null
}

const handleConfirm = async () => {
  if (!hasSelection.value) return

  if (isConfirmCompleted.value) {
    setConfirmState(false)
    return
  }

  isLoading.value = true
  try {
    console.log('[무선데이터차단] 확인 요청', {
      currentBlockState: wirelessBlockInUse.value,
      selectedBlockService: model.value.blockService,
    })

    if (!isSelectionAvailable()) {
      showAlert('가입이 불가한 무선데이터차단 서비스입니다.', () => {
        model.value.blockService = null
        setConfirmState(false)
      })
      return
    }

    const preCheckResult = await postWirelessPreCheck()
    if (!preCheckResult.isSuccess) {
      showAlert(preCheckResult.message || '무선데이터차단 서비스 가입이 불가합니다.')
      setConfirmState(false)
      return
    }

    setConfirmState(true)
    console.log('[무선데이터차단] 확인 완료', {
      selectedBlockService: model.value.blockService,
      wirelessBlockConfirmCompleted: model.value.wirelessBlockConfirmCompleted,
    })
  } finally {
    isLoading.value = false
  }
}

watch(
  () => model.value.blockService,
  () => {
    if (isConfirmCompleted.value) {
      setConfirmState(false)
    }
  },
)

// 외부(서비스선택 초기화)에서 wirelessBlockConfirmCompleted=false로 리셋될 때 로컬 상태 동기화
watch(
  () => model.value.wirelessBlockConfirmCompleted,
  (val) => {
    if (!val && isConfirmCompleted.value) {
      isConfirmCompleted.value = false
    }
  },
)

onMounted(async () => {
  model.value.blockService = null
  setConfirmState(false)
  try {
    await fetchAvailableList()
    // 현재 상태에 따라 자동 선택: 이미 차단 중이면 해지(blockService1), 미차단이면 가입(blockService2)
    if (wirelessBlockInUse.value === true) {
      model.value.blockService = 'blockService1'
    } else if (wirelessBlockInUse.value === false) {
      model.value.blockService = 'blockService2'
    }
  } finally {
    emit('ready')
  }

  const statusMessage = getWirelessStatusMessage()
  if (statusMessage) {
    showConfirm(statusMessage)
  }
})
</script>

<template>
  <MsfLoadingComp v-if="isLoading" />
  <MsfTitleArea title="무선데이터차단 서비스" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="무선데이터차단<br/>이용 여부" tag="div" required>
      <MsfChip
        v-model="model.blockService"
        name="inp-blockService"
        :data="chipData"
      >
        <template #endSlot>
          <MsfButton
            variant="toggle"
            :active="isConfirmCompleted"
            :disabled="!hasSelection || isLoading"
            @click="handleConfirm"
          >
            {{ confirmButtonLabel }}
          </MsfButton>
        </template>
      </MsfChip>
    </MsfFormGroup>
  </MsfStack>
</template>
