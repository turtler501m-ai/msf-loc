<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { useAuthButton } from '@/hooks/useAuthButton'
import MsfUsimScanModal from '@/components/form/common/popups/MsfUsimScanModal.vue'
import MsfSerialNumberScanModal from '@/components/form/common/popups/MsfSerialNumberScanModal.vue'

const formData = defineModel({ type: Object, required: true })
const emit = defineEmits(['ready'])
const store = useMsfFormSvcChgStore()

const agreementAllChecked = ref(false)
const agreementItems = ref([])
const isLoadingState = ref(false)
const isCheckingPhone = ref(false)
const isUsimScanModalOpen = ref(false)
const isSerialNumberModalOpen = ref(false)

const SHARE_JOIN = 'shareUseState1'
const SHARE_CANCEL = 'shareUseState2'
// const TEMP_ALLOW_NEXT_AFTER_PHONE_CHECK = true

const isJoin = computed(() => formData.value.shareUseState === SHARE_JOIN)
const isCancel = computed(() => formData.value.shareUseState === SHARE_CANCEL)
const sharePhoneNo = computed(() => String(formData.value.sharePhoneNum || '').replace(/\D/g, ''))
const svcChgPhoneNo = computed(() =>
  `${formData.value.deviceChgTel1 || ''}${formData.value.deviceChgTel2 || ''}${formData.value.deviceChgTel3 || ''}`.replace(/\D/g, ''),
)
const shareUsimNo = computed(() => String(formData.value.shareUsimNum || '').replace(/\D/g, ''))

const usimAuth = useAuthButton(
  () => [formData.value?.shareUsimNum],
  {
    get value() { return formData.value?.dataSharingUsimCheckCompleted || false },
    set value(v) {
      formData.value.dataSharingUsimCheckCompleted = v
      if (store.authFlags) store.authFlags.dataSharingUsim = v
    },
  },
  ([sn]) => sn && String(sn).replace(/\D/g, '').length === 19,
)

const requiredAgreementCompleted = computed(() => {
  const items = Array.isArray(agreementItems.value) ? agreementItems.value : []
  const requiredItems = items.filter((item) => item.required === true || item.required === 'Y' || item.required === '2')
  return requiredItems.length > 0 && requiredItems.every((item) => item.checked === true)
})

const resetJoinChecks = () => {
  formData.value.dataSharingAuthCompleted = false
  formData.value.dataSharingUsimCheckCompleted = false
  formData.value.dataSharingAvailableChecked = false
  formData.value.dataSharingConfirmCompleted = false
  store.authFlags.dataSharingPhone = false
  store.authFlags.dataSharingUsim = false
}

watch(
  () => store.cancelAuthResetKey,
  (val, old) => {
    if (typeof old !== 'number') return
    agreementAllChecked.value = false
    agreementItems.value = agreementItems.value.map((item) => ({ ...item, checked: false }))
    resetJoinChecks()
    usimAuth.requireReauth()
  },
)

const setMessage = (message) => {
  formData.value.dataSharingMessage = message || ''
}

const setIfChanged = (key, value) => {
  if (formData.value[key] !== value) {
    formData.value[key] = value
  }
}

const getAgreementSignature = (items = []) =>
  JSON.stringify(
    (Array.isArray(items) ? items : []).map((item) => ({
      code: item?.code || '',
      required: item?.required || '',
      checked: item?.checked === true,
    })),
  )

const formatPhone = (val) => {
  const clean = String(val || '').replace(/\D/g, '')
  if (clean.length === 11) {
    return `${clean.slice(0, 3)}-${clean.slice(3, 7)}-${clean.slice(7)}`
  }
  if (clean.length === 10) {
    return `${clean.slice(0, 3)}-${clean.slice(3, 6)}-${clean.slice(6)}`
  }
  return clean
}

const applyListResult = (data = {}) => {
  console.log('[dataSharing] applyListResult:', {
    subscribed: data.subscribed,
    parentAvailable: data.parentAvailable,
    socChkYn: data.socChkYn,
    soc: data.soc,
    targetNo: data.targetNo,
    message: data.message,
    ctn: data.ctn,
    ncn: data.ncn,
    changeYn: data.changeYn,
  })
  if (data.ncn) formData.value.ncn = data.ncn
  if (data.contractNum) formData.value.contractNum = data.contractNum
  if (data.custId) formData.value.custId = data.custId
  if (data.subStatus !== undefined) formData.value.subStatus = data.subStatus || ''
  formData.value.dataSharingPlanName = data.opmdSvcSocNm || data.opmdSvcSocName || data.dataSharingPlanName || ''
  const ctn = String(data.ctn || '').replace(/\D/g, '')
  if (ctn.length === 11) {
    formData.value.deviceChgTel1 = ctn.substring(0, 3)
    formData.value.deviceChgTel2 = ctn.substring(3, 7)
    formData.value.deviceChgTel3 = ctn.substring(7)
  } else if (ctn.length === 10) {
    formData.value.deviceChgTel1 = ctn.substring(0, 3)
    formData.value.deviceChgTel2 = ctn.substring(3, 6)
    formData.value.deviceChgTel3 = ctn.substring(6)
  }

  const items = Array.isArray(data.items) ? data.items : []
  const first = items.find((item) => item?.svcNo || item?.targetNo) || null
  const targetNo = String(data.targetNo || first?.svcNo || '').replace(/\D/g, '')

  formData.value.dataSharingSubscribed = data.subscribed === true && !!targetNo
  formData.value.dataSharingTargetNo = targetNo

  if (formData.value.dataSharingSubscribed) {
    formData.value.shareUseState = SHARE_CANCEL
    formData.value.sharePhoneNum = ''
    formData.value.shareUsimNum = ''
    formData.value.dataSharingConfirmCompleted = true
    return
  }

  formData.value.shareUseState = SHARE_JOIN
  formData.value.dataSharingConfirmCompleted = false
  if (svcChgPhoneNo.value.length >= 10) {
    formData.value.sharePhoneNum = svcChgPhoneNo.value
  }
  setMessage(data.message || '')
}

const loadDataSharingState = async () => {
  const ctn = `${formData.value.deviceChgTel1 || ''}${formData.value.deviceChgTel2 || ''}${formData.value.deviceChgTel3 || ''}`
  if (!formData.value.ncn && !formData.value.contractNum && !ctn) {
    emit('ready')
    return
  }

  isLoadingState.value = true
  try {
    const result = await store.apiDataSharingList()
    if (!result.success) {
      setMessage(result.message || '데이터쉐어링 가입 여부를 조회하지 못했습니다.')
      return
    }
    applyListResult(result.data)
  } catch (error) {
    console.error('[dataSharing] list failed', error)
    setMessage('데이터쉐어링 가입 여부 조회 중 오류가 발생했습니다.')
  } finally {
    isLoadingState.value = false
    emit('ready')
  }
}

const requestPhoneAuth = async () => {
  if (sharePhoneNo.value.length !== 11) {
    showAlert('휴대폰번호 11자리를 입력해 주세요.')
    return
  }

  console.log('[dataSharing] requestPhoneAuth: sharePhoneNum=', sharePhoneNo.value,
    'ctn=', `${formData.value.deviceChgTel1||''}${formData.value.deviceChgTel2||''}${formData.value.deviceChgTel3||''}`,
    'ncn=', formData.value.ncn)
  isCheckingPhone.value = true
  try {
    const checkResult = await store.apiDataSharingCheck(sharePhoneNo.value)
    const available = checkResult.success && checkResult.data?.available === true
    console.log('[dataSharing] X69 result: available=', available, 'data=', checkResult.data, 'message=', checkResult.message)
    formData.value.dataSharingAuthCompleted = available
    formData.value.dataSharingAvailableChecked = available
    store.authFlags.dataSharingPhone = available

    const apiMessage = checkResult.data?.message || checkResult.message || ''
    if (available) {
      setMessage('데이터쉐어링 가입이 가능합니다.')
    } else {
      const parts = ['입력하신 휴대폰 번호와 데이터쉐어링 가입이 불가능 합니다.']
      if (apiMessage) parts.push(`- ${apiMessage}`)
      setMessage(parts.join('\n'))
    }
    showAlert(formData.value.dataSharingMessage)
  } catch (error) {
    console.error('[dataSharing] phone check failed', error)
    resetJoinChecks()
    showAlert('데이터쉐어링 가입 가능 여부 확인 중 오류가 발생했습니다.')
  } finally {
    isCheckingPhone.value = false
  }
}

const handleUsimVerify = async () => {
  console.log('[dataSharing] handleUsimVerify: shareUsimNum=', shareUsimNo.value)
  try {
    const result = await store.apiDataSharingUsimCheck(shareUsimNo.value)
    if (result.success) usimAuth.verify()
    showAlert(result.message || (result.success ? '사용 가능한 USIM입니다.' : '사용할 수 없는 USIM입니다.'))
  } catch (error) {
    console.error('[dataSharing] usim check failed', error)
    showAlert('USIM 번호 유효성 체크 중 오류가 발생했습니다.')
  }
}

const onUsimScanConfirm = (data) => {
  if (data?.reqUsimSn) formData.value.shareUsimNum = data.reqUsimSn
}

const onSerialNumberScanConfirm = (data) => {
  if (data?.serialNumber) formData.value.shareUsimNum = data.serialNumber
}

const onAgreementChecked = (items) => {
  const nextItems = Array.isArray(items) ? items : []
  const prevSignature = getAgreementSignature(agreementItems.value)
  const nextSignature = getAgreementSignature(nextItems)
  if (prevSignature !== nextSignature) {
    agreementItems.value = nextItems
  }

  const requiredCompleted = (() => {
    const requiredItems = nextItems.filter((item) => item.required === true || item.required === 'Y' || item.required === '2')
    return requiredItems.length > 0 && requiredItems.every((item) => item.checked === true)
  })()
  setIfChanged('dataSharingAgreementCompleted', requiredCompleted)

  const map = new Map((formData.value.clauses || []).map((item) => [item.code, item]))
  nextItems.forEach((item) => {
    if (item?.code) map.set(item.code, item)
  })
  const nextClauses = Array.from(map.values())
  if (getAgreementSignature(formData.value.clauses || []) !== getAgreementSignature(nextClauses)) {
    formData.value.clauses = nextClauses
  }
}

const syncComplete = () => {
  if (isCancel.value) {
    if (formData.value.dataSharingTargetNo) {
      setIfChanged('dataSharingConfirmCompleted', true)
    } else {
      setIfChanged('dataSharingConfirmCompleted', sharePhoneNo.value.length === 11)
    }
    return
  }
  if (!isJoin.value) {
    setIfChanged('dataSharingConfirmCompleted', false)
    return
  }
  setIfChanged('dataSharingAgreementCompleted', requiredAgreementCompleted.value)
  setIfChanged('dataSharingConfirmCompleted',
    formData.value.dataSharingAuthCompleted === true &&
    formData.value.dataSharingUsimCheckCompleted === true &&
    formData.value.dataSharingAvailableChecked === true &&
    formData.value.dataSharingAgreementCompleted === true,
  )
}

watch(
  () => formData.value.shareUseState,
  (value, oldValue) => {
    if (value === oldValue) return
    if (value === SHARE_CANCEL) {
      formData.value.sharePhoneNum = ''
      formData.value.shareUsimNum = ''
      formData.value.dataSharingAuthCompleted = false
      formData.value.dataSharingUsimCheckCompleted = false
      formData.value.dataSharingAvailableChecked = false
      store.authFlags.dataSharingPhone = false
      store.authFlags.dataSharingUsim = false

      if (!formData.value.dataSharingTargetNo) {
        setMessage('해지할 데이터쉐어링 휴대폰 번호를 입력해 주세요.')
      }
    } else {
      resetJoinChecks()
    }
    syncComplete()
  },
)

watch(
  () => [
    formData.value.dataSharingAuthCompleted,
    formData.value.dataSharingUsimCheckCompleted,
    formData.value.dataSharingAvailableChecked,
    formData.value.dataSharingTargetNo,
    formData.value.sharePhoneNum,
    getAgreementSignature(agreementItems.value),
  ],
  syncComplete,
)

watch(sharePhoneNo, () => {
  if (formData.value.dataSharingAuthCompleted) {
    formData.value.dataSharingAuthCompleted = false
    formData.value.dataSharingAvailableChecked = false
    formData.value.dataSharingConfirmCompleted = false
    store.authFlags.dataSharingPhone = false
  }
})


onMounted(loadDataSharingState)

defineExpose({ validate: () => formData.value.dataSharingConfirmCompleted === true })
</script>

<template>
  <MsfTitleArea title="데이터쉐어링 가입/해지" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="데이터쉐어링<br/>이용 여부" tag="div" required>
      <MsfChip
        v-model="formData.shareUseState"
        name="inp-shareUseState"
        :disabled="isLoadingState"
        :data="[
          { value: SHARE_JOIN, label: '데이터쉐어링 가입', disabled: formData.dataSharingSubscribed },
          { value: SHARE_CANCEL, label: '데이터쉐어링 해지', disabled: formData.shareUseState === SHARE_JOIN },
        ]"
      />
    </MsfFormGroup>

    <MsfFormGroup v-if="isJoin || (isCancel && !formData.dataSharingTargetNo)" label="휴대폰 번호" required>
      <MsfStack type="field">
        <MsfNumberInput
          v-model="formData.sharePhoneNum"
          placeholder="휴대폰번호 입력 (하이픈 제외)"
          maxlength="11"
          class="ut-w-300"
          :disabled="!!svcChgPhoneNo || (isJoin && formData.dataSharingAuthCompleted)"
        />
        <MsfButton
          v-if="isJoin"
          variant="toggle"
          :active="formData.dataSharingAuthCompleted"
          :disabled="sharePhoneNo.length !== 11 || formData.dataSharingAuthCompleted || isCheckingPhone"
          @click="requestPhoneAuth"
        >
          {{ formData.dataSharingAuthCompleted ? '인증 완료' : '인증' }}
        </MsfButton>
      </MsfStack>
    </MsfFormGroup>

    <MsfFormGroup v-if="isJoin" label="USIM 번호" required>
      <MsfStack type="field">
        <MsfNumberInput
          v-model="formData.shareUsimNum"
          placeholder="USIM 번호 19자리"
          maxlength="19"
          class="ut-w-300"
          :disabled="usimAuth.status.value === 'verified'"
        />
        <MsfButton
          variant="subtle"
          :disabled="usimAuth.status.value === 'verified'"
          @click="isUsimScanModalOpen = true"
        >스캔하기</MsfButton>
        <MsfButton
          variant="subtle"
          :disabled="usimAuth.status.value === 'verified'"
          @click="isSerialNumberModalOpen = true"
        >스캔하기(휴대폰 일련번호)</MsfButton>
        <MsfButton variant="validation" v-if="usimAuth.status.value === 'none'" disabled>
          유효성 체크
        </MsfButton>
        <MsfButton
          variant="validation"
          v-else-if="usimAuth.status.value === 'ready'"
          @click="handleUsimVerify"
        >
          유효성 체크
        </MsfButton>
        <MsfButton variant="validation" v-else-if="usimAuth.status.value === 'verified'" active>
          유효성 체크 완료
        </MsfButton>
      </MsfStack>
    </MsfFormGroup>
  </MsfStack>

  <p v-if="formData.dataSharingMessage" class="data-sharing-message">
    {{ formData.dataSharingMessage }}
  </p>

  <template v-if="isJoin">
    <MsfTitleArea title="데이터쉐어링 가입/해지 약관 동의" />
    <MsfAgreementGroup
      v-model="agreementAllChecked"
      policy="CLAUSE_SHARING"
      required
      @checked="onAgreementChecked"
    />
  </template>
  <MsfUsimScanModal
    v-model="isUsimScanModalOpen"
    :readonly="usimAuth.status.value === 'verified'"
    @confirm="onUsimScanConfirm"
  />
  <MsfSerialNumberScanModal
    v-model="isSerialNumberModalOpen"
    :readonly="usimAuth.status.value === 'verified'"
    @confirm="onSerialNumberScanConfirm"
  />
</template>

<style scoped>
.data-sharing-message {
  margin-top: 12px;
  color: #495057;
  font-size: 14px;
  line-height: 1.45;
  white-space: pre-line;
}
</style>
