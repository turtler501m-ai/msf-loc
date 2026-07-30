<script setup>
import { ref, defineModel, defineProps, onMounted, computed, watch } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { useMsfFormOwnChgStore } from '@/stores/msf_ownerChange'
import MsfUsimScanModal from '@/components/form/common/popups/MsfUsimScanModal.vue'
import MsfSimPurchaseMethodSelect from '@/components/form/common/MsfSimPurchaseMethodSelect.vue'
import { showAlert, showConfirmAsync } from '@/libs/utils/comp.utils'
import { concatStrings } from '@/libs/utils/string.utils'

const props = defineProps({
  customerData: { type: Object, default: () => ({}) },
  authFlags: { type: Object, default: () => ({}) },
  resetKey: { type: Number, default: 0 },
})

const formData = defineModel({ type: Object, required: true })
const dataModel = defineModel('data', { type: Object })
const store = useMsfFormOwnChgStore()
const isUsimScanModalOpen = ref(false)
const formType = computed(() => formData.value?.formType === 'SERVICECHANGE')

const simOptionRef = ref(null)
const reqUsimSnRef = ref(null)
const simAuthBtnRef = ref(null)
const simPurchaseMethodRef = ref(null)
const esimAuthBtnRef = ref(null)

const isEsim = computed(() => (formData.value.simTypeCd === 'ESIM' ? true : false))
const isSim2 = computed(() =>
  formData.value.hasSim === false && formData.value.simTypeCd === 'USIM' ? true : false,
)

const simOption = ref('hasSim1')

watch(
  () => [formData.value.hasSim, formData.value.simTypeCd],
  ([hasSim, simTypeCd]) => {
    if (simTypeCd === 'ESIM') {
      if (simOption.value !== 'hasSim3') simOption.value = 'hasSim3'
    } else if (hasSim === false) {
      if (simOption.value !== 'hasSim2') simOption.value = 'hasSim2'
    } else {
      if (simOption.value !== 'hasSim1') simOption.value = 'hasSim1'
    }
  },
  { immediate: true, deep: true },
)

watch(simOption, (newVal) => {
  if (newVal === 'hasSim1') {
    if (formData.value.hasSim !== true) formData.value.hasSim = true
    if (formData.value.simTypeCd !== 'USIM') formData.value.simTypeCd = 'USIM'
  } else if (newVal === 'hasSim2') {
    if (formData.value.hasSim !== false) formData.value.hasSim = false
    if (formData.value.simTypeCd !== 'USIM') formData.value.simTypeCd = 'USIM'
  } else if (newVal === 'hasSim3') {
    if (formData.value.hasSim !== true) formData.value.hasSim = true
    if (formData.value.simTypeCd !== 'ESIM') formData.value.simTypeCd = 'ESIM'
  }
})



const simPossessionOptions = computed(() => {
  const options = []



  if (formType.value && !isEsim.value) {
    options.push({ value: 'hasSim1', label: 'USIM 보유' })
    options.push({ value: 'hasSim2', label: 'USIM 구매' })
  } else if (!isEsim.value) {
    // eSim이 아닌 경우
    options.push({ value: 'hasSim1', label: '현재USIM으로 사용' })
    options.push({ value: 'hasSim2', label: 'USIM 구매' })
  } else {
    options.push({ value: 'hasSim3', label: 'eSIM' })
  }
  return options
})

onMounted(async () => {
  // 초기값 셋팅
  if (
    formData.value.hasSim === '' ||
    formData.value.hasSim === undefined ||
    formData.value.hasSim === null
  ) {
    formData.value.hasSim = true
    formData.value.simTypeCd = 'USIM'
  }

  // USIM 보유 또는 eSIM인 경우 구매 방식은 null 처리, USIM 구매인 경우만 'B' 디폴트
  if (formData.value.hasSim !== false) {
    formData.value.simPurchaseMethod = null
  } else {
    if (!formData.value.simPurchaseMethod) {
      formData.value.simPurchaseMethod = 'B'
    }
  }

  // 약관(유심구매) 공통코드 조회
  getCommonCodeList('TERMSELF').then((list) => {
    console.log('>>> 약관(유심구매) (TERMSELF):', list)
  })
})

const simAuth = useAuthButton(
  () => [formData.value?.reqUsimSn],
  {
    get value() {
      return store.authFlags?.reqUsimSn || false
    },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.reqUsimSn = v
      }
      formData.value.reqUsimConfirmCompleted = v
    },
  },
  ([sn]) => sn && sn.length === 19,
)

const esimAuth = useAuthButton(() => [], {
  get value() {
    return store.authFlags?.esimImei || false
  },
  set value(v) {
    if (store.authFlags) {
      store.authFlags.esimImei = v
    }
    formData.value.reqUsimConfirmCompleted = v
  },
})

watch(
  () => props.resetKey,
  (val, old) => {
    if (typeof old === 'number') {
      formData.value.reqUsimConfirmCompleted = false
      simAuth.requireReauth()
      esimAuth.requireReauth()
    }
  },
)

watch(
  () => formData.value.hasSim,
  (newVal) => {
    formData.value.usimKindsCd = newVal === false ? '01' : null
    formData.value.simPurchaseMethod = newVal === false ? 'B' : null
  },
)

const handleVerifySubmit = async () => {
  if (!formType.value) {
    if (isEsim.value) {
      await handleEsimVerify()
    } else {
      await handleSimVerify()
    }
    return // 함수 조기 종료 (아래 검증 로직으로 안 내려감)
  }

  // 2. formType이 false일 때만 원래 유심 검증 로직 실행
  await isUsimChangeable()
}

const isUsimChangeable = async () => {
  const url = '/api/form/servicechange/usim/check'
  const payload = {
    ncn: formData.value.ncn,
    ctn: concatStrings([
      formData.value.deviceChgTel1,
      formData.value.deviceChgTel2,
      formData.value.deviceChgTel3,
    ]),
    cstmrTypeCd: formData.value.cstmrTypeCd,
  }
  const res = await post(url, payload, { skipAlert: true })

  if (res.code === '0000') {
    if (res.data.resCode === '0000') {
      if (isEsim.value) {
        await handleEsimVerify()
      } else {
        await handleSimVerify()
      }
    } else {
      showAlert(res.data.resMessage)
    }
  }
}

const handleEsimVerify = async () => {
  const confirm = await showConfirmAsync('Esim 변경을 신청하시겠습니까?')
  if (confirm) {
    esimAuth.verify()
  }
}

const getSimPrice = async () => {
  const soc = dataModel?.value?.planInfo?.planName2
  // const dataType = dataModel?.value?.planInfo?.dataType
  const payload = {
    soc,
    // dataType,
  }

  const res = await post('/api/form/price/get', payload, { skipAlert: true })

  if (res?.data?.resCode === '0000') {
    if (dataModel?.value?.usimInfo) {
      dataModel.value.usimInfo.usimPrice = res.data?.resData?.usimPrice
    }
  }
}

const handleSimVerify = async () => {
  const url = isEsim.value ? '/api/form/esiminfo/verify' : '/api/form/usiminfo/verify'

  const payload = isEsim.value
    ? {
        eid: formData.value.eid,
        imei1: formData.value.imei1,
        imei2: formData.value.imei2,
        modelId: formData.value.modelId || formData.value.deviceModel || '',
        agentCd: props.customerData?.agentCd || formData.value?.agentCd || '',
      }
    : {
        iccId: formData.value.reqUsimSn,
        agentCd: props.customerData?.agentCd || formData.value?.agentCd || '',
        hasSim: formData.value.hasSim,
      }

  try {
    const res = await post(url, payload)
    // 비즈니스 결과(resCode) 체크
    const success = res && res.data?.resCode === '0000'

    if (success) {
      if (isEsim.value) {
        esimAuth.verify()
        // 검증 성공 시 반환되는 단말기 정보(예: 단말기명)가 있다면 세팅
        const resData = res.data?.resData || res.data
        if (resData?.prodNm) {
          formData.value.prodNm = resData.prodNm
        }
      } else {
        formData.value.reqUsimNm = res.data?.resData['REQ_USIM_NM']
        simAuth.verify()
        await getSimPrice()
      }
    }
  } catch (error) {
    console.error('Verify SIM info error:', error)
  }
}

const onUsimScanConfirm = (data) => {
  console.log('USIM 스캔 결과:', data)
  if (data?.reqUsimSn) {
    formData.value.reqUsimSn = data.reqUsimSn
  }
}

const validate = () => {
  // 현재 유심 사용인 경우 체크X, ESIM도 체크 안함
  if (!formType.value && formData.value?.hasSim) {
    return true
  }

  // 유심 구매 & 서비스변경인 경우
  if (!formData.value?.hasSim && formType.value) {
    // if (!formData.value.usimKindsCd) return false
    if (!formData.value.simPurchaseMethod) return false
  }

  // 유심 번호 확인
  if (!formData.value.reqUsimSn) return false
  // 유심은 유효성 체크가 완료되어야 함
  if (!props.authFlags?.reqUsimSn) return false

  return true
}

const checkValidation = () => {
  if (!formType.value && formData.value?.hasSim) {
    return true
  }

  if (!simOption.value) {
    showAlert(`SIM 보유를 선택하세요`, () => {
      simOptionRef.value?.focus()
    })
    return false
  }

  // 유심 구매 & 서비스변경인 경우
  if (!formData.value?.hasSim && formType.value) {
    /*
    if (!formData.value.usimKindsCd) {
      showAlert(`USIM 선택의 종류를 선택하세요`, () => {
        reqUsimSnRef.value?.focus()
      })
      return false
    }
    */
    if (!formData.value.simPurchaseMethod) {
      showAlert(`USIM 구매 방식을 선택하세요`, () => {
        simPurchaseMethodRef.value?.focus()
      })
      return false
    }
  }

  if (!isEsim.value && (formType.value || !formData.value?.hasSim)) {
    // 유심 번호 확인
    if (!formData.value.reqUsimSn) {
      showAlert(`USIM 번호를 입력하세요`, () => {
        reqUsimSnRef.value?.focus()
      })
      return false
    }
    // 유심은 유효성 체크가 완료되어야 함
    if (!props.authFlags?.reqUsimSn) {
      showAlert(`USIM 번호 유효성 체크를 실행하세요`, () => {
        simAuthBtnRef.value?.focus()
      })
      return false
    }
  }

  return true
}

defineExpose({ validate, checkValidation })
</script>

<template>
  <!-- SIM정보 -->
  <MsfTitleArea title="SIM정보" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="SIM 보유" tag="div" required>
      <MsfChip
        ref="simOptionRef"
        v-model="simOption"
        name="inp-hasSim"
        :data="simPossessionOptions"
        :readonly="simAuth.status.value === 'verified' || !!authFlags?.esimImei"
      />

    </MsfFormGroup>
    <!-- <MsfFormGroup label="USIM 선택" tag="div" required v-if="isSim2">
      <MsfUsimKindsSelect
        ref="usimKindsCdRef"
        v-model="formData.usimKindsCd"
        :disabled="simAuth.status.value === 'verified'"
      />
    </MsfFormGroup> -->
    <MsfFormGroup label="USIM 번호" required v-if="!isEsim && (formType || !formData?.hasSim)">
      <MsfStack type="field">
        <MsfInput
          ref="reqUsimSnRef"
          v-model="formData.reqUsimSn"
          id="inp-reqUsimSn"
          placeholder="USIM 번호 19자리"
          maxlength="19"
          class="ut-w-300"
          :disabled="simAuth.status.value === 'verified'"
        />
        <MsfButton
          variant="subtle"
          @click="isUsimScanModalOpen = true"
          :disabled="simAuth.status.value === 'verified'"
          >스캔하기</MsfButton
        >
        <MsfButton variant="validation" v-if="simAuth.status.value === 'none'" disabled>
          유효성 체크
        </MsfButton>
        <MsfButton
          ref="simAuthBtnRef"
          variant="validation"
          v-else-if="simAuth.status.value === 'ready'"
          @click="handleVerifySubmit"
        >
          유효성 체크
        </MsfButton>
        <MsfButton variant="validation" v-else-if="simAuth.status.value === 'verified'" active>
          유효성 체크 완료
        </MsfButton>
      </MsfStack>
    </MsfFormGroup>
    <MsfFormGroup label="USIM 구매 방식" tag="div" required v-if="isSim2">
      <MsfSimPurchaseMethodSelect ref="simPurchaseMethodRef" v-model="formData.simPurchaseMethod" />
    </MsfFormGroup>
    <MsfFormGroup label="휴대폰 정보" required v-if="isEsim">
      <MsfStack type="field">
        <MsfButton variant="toggle" v-if="esimAuth.status.value === 'none'" disabled>
          확인
        </MsfButton>
        <MsfButton
          ref="esimAuthBtnRef"
          variant="toggle"
          v-else-if="esimAuth.status.value === 'ready'"
          @click="handleVerifySubmit"
        >
          확인
        </MsfButton>
        <MsfButton variant="toggle" v-else-if="esimAuth.status.value === 'verified'" active>
          확인 완료
        </MsfButton>
      </MsfStack>
    </MsfFormGroup>
  </MsfStack>
  <!-- <MsfEsimScanModal
    v-model="isEsimScanModalOpen"
    :readonly="authFlags?.esimImei"
    @confirm="onEsimScanConfirm"
  /> -->
  <MsfUsimScanModal
    v-model="isUsimScanModalOpen"
    :readonly="authFlags?.reqUsimSn"
    @confirm="onUsimScanConfirm"
  />
  <!-- // SIM정보 -->
</template>

<style scoped lang="scss"></style>
