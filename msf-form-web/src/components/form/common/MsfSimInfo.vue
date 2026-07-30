<script setup>
import { ref, defineModel, defineProps, onMounted, computed, watch } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList, getCommonCodeListAll } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import MsfEsimScanModal from '@/components/form/common/popups/MsfEsimScanModal.vue'
import MsfUsimScanModal from '@/components/form/common/popups/MsfUsimScanModal.vue'
import MsfSerialNumberScanModal from '@/components/form/common/popups/MsfSerialNumberScanModal.vue'

const props = defineProps({
  customerData: { type: Object, default: () => ({}) },
  authFlags: { type: Object, default: () => ({}) },
  disabled: Boolean,
})

const formData = defineModel({ type: Object, required: true })
const store = useMsfFormNewChgStore()
const isEsimScanModalOpen = ref(false)
const isSerialNumberModalOpen = ref(false)
const isUsimScanModalOpen = ref(false)
const isEsimEditable = ref(true)

const simOptionRef = ref(null)
const joinPayMthdCdRef = ref(null)
const joinPayMthdOptions = ref([])

const isJoinFeeRequired = computed(() => {
  return !['HCN3', 'HDN3'].includes(props.customerData?.joinType)
})
const reqUsimSnRef = ref(null)
const reqModelNmRef = ref(null)
const eidRef = ref(null)
const imei1Ref = ref(null)
const imei2Ref = ref(null)
const simAuthReqUsimSnBtnRef = ref(null)
const simPurchaseMethodRef = ref(null)

const toggleEsimEdit = () => {
  if (!isEsimEditable.value) {
    if (!formData.value.reqModelNm && props.customerData?.reqModelNm) {
      formData.value.reqModelNm = props.customerData.reqModelNm
    }
    isEsimEditable.value = true
    esimAuth.requireReauth()
  } else {
    isEsimEditable.value = false
  }
}
const isBulkMode = computed(() => {
  return (
    props.customerData?.canBulkCorporateOpenYn === 'Y' &&
    props.customerData?.cstmrTypeCd === 'JP' &&
    props.customerData?.joinType === 'NAC3'
  )
})

watch(
  () => [formData.value?.simTypeCd, isBulkMode.value],
  ([simType, isBulk]) => {
    if (isBulk && simType === 'ESIM') {
      showAlert('법인 대량 개통은 eSIM은 선택 불가능 합니다.')
      formData.value.simTypeCd = 'USIM'
      formData.value.hasSim = true
    }
  },
  { immediate: true }
)

const simPossessionOptions = computed(() => {
  const isDeviceChange =
    props.customerData?.joinType === 'HDN3' || props.customerData?.joinType === 'HCN3'
  const options = []

  if (isDeviceChange) {
    // 기기변경인 경우
    options.push({ value: 'hasSim1', label: '현재USIM으로 사용' })
  } else {
    // 신규/번호이동인 경우
    options.push({ value: 'hasSim1', label: 'USIM 보유' })
  }
  options.push({ value: 'hasSim2', label: 'USIM 구매' })

  // 상품이 휴대폰(MM)이거나 USIM(UU)일 때 eSIM 옵션 노출
  if (['MM', 'UU'].includes(props.customerData?.productType)) {
    options.push({
      value: 'hasSim3',
      label: 'eSIM',
      disabled: isBulkMode.value,
    })
  }
  return options
})

const simOption = computed({
  get() {
    if (formData.value?.simTypeCd === 'ESIM') return 'hasSim3'
    if (formData.value?.hasSim === false) return 'hasSim2'
    return 'hasSim1'
  },
  set(newVal) {
    if (store.isDraftLoading) return

    if (newVal === 'hasSim1') {
      if (formData.value.hasSim !== true) formData.value.hasSim = true
      if (formData.value.simTypeCd !== 'USIM') formData.value.simTypeCd = 'USIM'
      formData.value.usimPayMthdCd = '1'
      formData.value.usimPriceTypeCd = 'N'
      formData.value.simPurchaseMethod = null

      // eSIM 관련 정보 리셋
      formData.value.eid = ''
      formData.value.imei1 = ''
      formData.value.imei2 = ''
      if (store.authFlags) store.authFlags.esimImei = false
    } else if (newVal === 'hasSim2') {
      if (formData.value.hasSim !== false) formData.value.hasSim = false
      if (formData.value.simTypeCd !== 'USIM') formData.value.simTypeCd = 'USIM'
      if (!formData.value.simPurchaseMethod) formData.value.simPurchaseMethod = 'B'

      // eSIM 관련 정보 리셋
      formData.value.eid = ''
      formData.value.imei1 = ''
      formData.value.imei2 = ''
      if (store.authFlags) store.authFlags.esimImei = false
    } else if (newVal === 'hasSim3') {
      if (formData.value.hasSim !== true) formData.value.hasSim = true
      if (formData.value.simTypeCd !== 'ESIM') formData.value.simTypeCd = 'ESIM'
      formData.value.usimPayMthdCd = '3'
      formData.value.usimPriceTypeCd = 'B'
      formData.value.simPurchaseMethod = null

      // USIM 관련 정보 리셋
      formData.value.reqUsimSn = ''
      if (store.authFlags) store.authFlags.reqUsimSn = false
    }
  }
})

onMounted(async () => {
  // 기기변경(HDN3)인 경우 디폴트 '현재USIM으로 사용' (hasSim1)
  // 번호이동/신규가입인 경우 디폴트 'USIM 보유' (hasSim1)
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

  // 공통코드 조회: 가입비 납부방법 목록
  try {
    const joinPayCodes = await getCommonCodeListAll('JOIN_PAY_MTHD_CD')
    joinPayMthdOptions.value = (joinPayCodes || []).map((item) => ({
      label: item.title,
      value: item.code,
    }))

    if (!formData.value.joinPayMthdCd) {
      const defaultVal = store.formDefaultMap?.['JOIN_PAY_MTHD_CD']
      if (defaultVal) {
        formData.value.joinPayMthdCd = String(defaultVal)
      } else if (joinPayMthdOptions.value.length > 0) {
        formData.value.joinPayMthdCd = joinPayMthdOptions.value[0].value
      }
    }
  } catch (err) {
    console.error('Failed to fetch JOIN_PAY_MTHD_CD:', err)
  }
})



watch(
  () => formData.value.simPurchaseMethod,
  async (newVal) => {
    if (store.isDraftLoading) return

    if (formData.value.simTypeCd === 'ESIM') {
      formData.value.usimPayMthdCd = '3'
      formData.value.usimPriceTypeCd = 'B'
      return
    }

    if (!newVal || newVal === 'N') {
      formData.value.usimPayMthdCd = '1'
      formData.value.usimPriceTypeCd = 'N'
      return
    }

    try {
      const list = await getCommonCodeList('USIM_PYMN_MTHD_CD', true)
      const found = (list || []).find((item) => item.code === newVal)
      if (found) {
        formData.value.usimPriceTypeCd = found.code
        formData.value.usimPayMthdCd = found.detail?.etcValue2 || found.expnsnStrVal2 || '1'
      }
    } catch (err) {
      console.error('USIM_PYMN_MTHD_CD 매핑 에러:', err)
    }
  },
)

watch(
  () => props.customerData?.reqModelNm,
  (newVal) => {
    if (newVal && !formData.value.reqModelNm) {
      formData.value.reqModelNm = newVal
    }
  },
  { immediate: true },
)

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
    },
  },
  ([sn]) => sn && sn.length === 19,
)

const esimAuth = useAuthButton(
  () => [formData.value?.eid, formData.value?.imei1, formData.value?.imei2],
  {
    get value() {
      return store.authFlags?.esimImei || false
    },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.esimImei = v
      }
    },
  },
)

const handleSimVerify = async () => {
  const isEsim = formData.value.simTypeCd === 'ESIM'
  const url = isEsim ? '/api/form/esiminfo/verify' : '/api/form/usiminfo/verify'

  const payload = isEsim
    ? {
        eid: formData.value.eid,
        imei1: formData.value.imei1,
        imei2: formData.value.imei2,
        modelId: props.customerData?.modelId || '',
        handsetProdId: props.customerData?.handsetProdId || '',
        deviceModel: props.customerData?.deviceModel || '',
        deviceModelNm: props.customerData?.deviceModelNm || '',
        prodId: props.customerData?.prodId || '',
        reqModelNm: props.customerData?.reqModelNm || '',
        agentCd: props.customerData?.agentCd || '',
      }
    : {
        iccId: formData.value.reqUsimSn,
        agentCd: props.customerData?.agentCd || '',
        hasSim: formData.value.hasSim,
      }

  try {
    const res = await post(url, payload)
    // 비즈니스 결과(resCode) 체크
    const success = res && res.data?.resCode === '0000'

    if (success) {
      if (isEsim) {
        esimAuth.verify()
        isEsimEditable.value = false
        // 검증 성공 시 반환되는 단말기 정보(예: 단말기명)가 있다면 세팅
        const resData = res.data?.resData || res.data
        if (resData?.reqModelNm || resData?.prodNm) {
          formData.value.reqModelNm = resData.reqModelNm || resData.prodNm
        }
        if (resData?.uploadPhoneSrlNo) {
          formData.value.uploadPhoneSrlNo = resData.uploadPhoneSrlNo
        }
      } else {
        simAuth.verify()
      }
    }
  } catch (error) {
    console.error('Verify SIM info error:', error)
  }
}

const onEsimScanConfirm = (data) => {
  console.log('eSIM 스캔 결과:', data)
  if (data) {
    formData.value.reqModelNm = data.reqModelNm || data.prodNm || formData.value.reqModelNm
    formData.value.eid = data.eid || formData.value.eid
    formData.value.imei1 = data.imei1 || formData.value.imei1
    formData.value.imei2 = data.imei2 || formData.value.imei2
    formData.value.reqUsimSn = data.iccId || formData.value.reqUsimSn
  }
}

const onUsimScanConfirm = (data) => {
  console.log('USIM 스캔 결과:', data)
  if (data?.reqUsimSn) {
    formData.value.reqUsimSn = data.reqUsimSn
  }
}

const validate = () => {
  if (
    formData.value?.hasSim === undefined ||
    formData.value?.hasSim === null ||
    formData.value?.hasSim === ''
  )
    return false

  // 1. eSIM인 경우 (simTypeCd === 'ESIM')
  if (formData.value.simTypeCd === 'ESIM') {
    // EID, IMEI1, IMEI2 정보 입력 여부 확인 (모델명은 스캔 실패 시 없을 수 있으므로 선택적으로 제외 가능성 고려)
    if (!formData.value.eid || !formData.value.imei1 || !formData.value.imei2) return false
    // eSIM은 이미지 등록(인증)이 필수
    if (!props.authFlags?.esimImei) return false
  } else {
    // 2. 현재 USIM으로 사용 (hasSim === true & 기변) 인 경우 -> 검증 제외하고 즉시 통과
    const isCurrentSimUse =
      formData.value.hasSim === true &&
      (props.customerData?.joinType === 'HDN3' || props.customerData?.joinType === 'HCN3')
    if (isCurrentSimUse) {
      return true
    }

    // 3. USIM 보유(hasSim === true) 또는 USIM 구매(hasSim === false)인 경우
    // if (!formData.value.usimKindsCd && formData.value.hasSim === false) return false
    if (!formData.value.reqUsimSn) return false
    // 유심은 유효성 체크가 완료되어야 함
    if (!props.authFlags?.reqUsimSn) return false

    // USIM 구매 시에만 구매 방식 체크
    if (formData.value.hasSim === false && !formData.value.simPurchaseMethod) return false
  }

  if (isJoinFeeRequired.value && !formData.value.joinPayMthdCd) return false

  return true
}

const reset = () => {
  isEsimEditable.value = true

  // 입력값들 '' 처리 및 기본값 복원
  if (formData.value) {
    formData.value.hasSim = true
    formData.value.simTypeCd = 'USIM'
    formData.value.usimKindsCd = ''
    formData.value.reqUsimSn = ''
    formData.value.simPurchaseMethod = null
    formData.value.eid = ''
    formData.value.imei1 = ''
    formData.value.imei2 = ''
    formData.value.reqModelNm = props.customerData?.reqModelNm || ''
  }
  simOption.value = 'hasSim1'

  // 인증 플래그 초기화
  if (store.authFlags) {
    store.authFlags.reqUsimSn = false
    store.authFlags.esimImei = false
  }

  // 인증 훅 상태 초기화
  simAuth.requireReauth()
  esimAuth.requireReauth()
}

const checkValidation = () => {
  if (!simOption.value) {
    showAlert(`SIM 보유를 선택하세요`, () => {
      simOptionRef.value?.focus()
    })
    return false
  }
  if (formData.value.simTypeCd === 'ESIM') {
    if (!formData.value.eid) {
      showAlert(`휴대폰 정보의 EID를 입력하세요`, () => {
        eidRef.value?.focus()
      })
      return false
    }
    if (!formData.value.imei1) {
      showAlert(`휴대폰 정보의 IMEI1을 입력하세요`, () => {
        imei1Ref.value?.focus()
      })
      return false
    }
    if (!formData.value.imei2) {
      showAlert(`휴대폰 정보의 IMEI2를 입력하세요`, () => {
        imei2Ref.value?.focus()
      })
      return false
    }
    // eSIM은 이미지 등록(인증)이 필수
    // if (!props.authFlags?.esimImei) {
    //   return false
    // }
  } else {
    if (
      formData.value.hasSim === true &&
      (props.customerData?.joinType === 'HDN3' || props.customerData?.joinType === 'HCN3')
    ) {
      return true
    }

    // 3. USIM 보유(hasSim === true) 또는 USIM 구매(hasSim === false)인 경우
    /*
    if (!formData.value.usimKindsCd && formData.value.hasSim === false) {
      showAlert(`USIM 선택의 종류를 선택하세요`, () => {
        reqUsimSnRef.value?.focus()
      })
      return false
    }
    */
    if (!formData.value.reqUsimSn) {
      showAlert(`USIM 번호를 입력하세요`, () => {
        reqUsimSnRef.value?.focus()
      })
      return false
    }
    // 유심은 유효성 체크가 완료되어야 함
    if (!props.authFlags?.reqUsimSn) {
      showAlert(`USIM 번호 유효성 체크를 실행하세요`, () => {
        simAuthReqUsimSnBtnRef.value?.focus()
      })
      return false
    }

    // USIM 구매 시에만 구매 방식 체크
    if (formData.value.hasSim === false && !formData.value.simPurchaseMethod) {
      showAlert(`USIM 구매 방식을 선택하세요`, () => {
        simPurchaseMethodRef.value?.focus()
      })
      return false
    }
  }

  if (isJoinFeeRequired.value && !formData.value.joinPayMthdCd) {
    showAlert(`가입비 납부방법을 선택하세요`, () => {
      joinPayMthdCdRef.value?.focus()
    })
    return false
  }

  return true
}

defineExpose({ validate, reset, checkValidation })
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
        :readonly="props.disabled || simAuth.status.value === 'verified' || !!authFlags?.esimImei"
      />
      <p v-if="isBulkMode" class="ut-color-point ut-mt-8" style="font-size: 14px; font-weight: 500;">
        ※ 법인 대량 개통은 eSIM은 선택 불가능 합니다.
      </p>
    </MsfFormGroup>
    <!-- <MsfFormGroup label="USIM 선택" tag="div" required v-if="formData.hasSim === false">
      <MsfUsimKindsSelect
        ref="usimKindsCdRef"
        v-model="formData.usimKindsCd"
        :disabled="props.disabled || simAuth.status.value === 'verified'"
        :product-type="customerData?.productType || ''"
        :prdt-sctn-cd="customerData?.dataType || ''"
      />
    </MsfFormGroup> -->
    <MsfFormGroup
      label="USIM 번호"
      required
      v-if="
        formData.simTypeCd === 'USIM' &&
        !(
          formData.hasSim === true &&
          (customerData?.joinType === 'HDN3' || customerData?.joinType === 'HCN3')
        )
      "
    >
      <MsfStack type="field">
        <MsfInput
          ref="reqUsimSnRef"
          v-model="formData.reqUsimSn"
          placeholder="USIM 번호 19자리"
          maxlength="19"
          class="ut-w-300"
          :disabled="props.disabled || simAuth.status.value === 'verified'"
        />
        <MsfButton
          variant="subtle"
          :disabled="props.disabled || simAuth.status.value === 'verified'"
          @click="isUsimScanModalOpen = true"
          >스캔하기</MsfButton
        >
        <MsfButton variant="validation" v-if="simAuth.status.value === 'none'" disabled>
          유효성 체크
        </MsfButton>
        <MsfButton
          ref="simAuthReqUsimSnBtnRef"
          variant="validation"
          v-else-if="simAuth.status.value === 'ready'"
          :disabled="props.disabled"
          @click="handleSimVerify"
        >
          유효성 체크
        </MsfButton>
        <MsfButton variant="validation" v-else-if="simAuth.status.value === 'verified'" active>
          유효성 체크 완료
        </MsfButton>
      </MsfStack>
    </MsfFormGroup>
    <MsfFormGroup label="USIM 구매 방식" tag="div" required v-if="formData.hasSim === false">
      <MsfSimPurchaseMethodSelect
        ref="simPurchaseMethodRef"
        v-model="formData.simPurchaseMethod"
        :disabled="props.disabled"
      />
    </MsfFormGroup>
    <MsfFormGroup label="휴대폰 정보" required v-if="formData.simTypeCd === 'ESIM'">
      <MsfInput
        v-if="customerData?.productType !== 'UU'"
        ref="reqModelNmRef"
        v-model="formData.reqModelNm"
        placeholder="휴대폰 모델명"
        class="ut-w-300"
        :disabled="props.disabled || !isEsimEditable"
      />
      <MsfInput
        ref="eidRef"
        v-model="formData.eid"
        id="inp-phoneEID"
        placeholder="EID"
        class="ut-w-608"
        maxlength="32"
        :disabled="props.disabled || !isEsimEditable"
      />
      <MsfStack type="field">
        <MsfInput
          ref="imei1Ref"
          v-model="formData.imei1"
          id="inp-phoneIMEI1"
          placeholder="IMEI1"
          class="ut-w-300"
          :disabled="props.disabled || !isEsimEditable"
        />
        <MsfInput
          ref="imei2Ref"
          v-model="formData.imei2"
          id="inp-phoneIMEI2"
          placeholder="IMEI2"
          class="ut-w-300"
          :disabled="props.disabled || !isEsimEditable"
        />
      </MsfStack>
      <MsfStack type="field">
        <MsfButton
          variant="subtle"
          :disabled="props.disabled || esimAuth.status.value === 'verified'"
          @click="isEsimScanModalOpen = true"
          >스캔하기</MsfButton
        >
        <MsfButton variant="validation" v-if="esimAuth.status.value === 'none'" disabled>
          유효성 체크
        </MsfButton>
        <MsfButton
          variant="validation"
          v-else-if="esimAuth.status.value === 'ready'"
          :disabled="props.disabled"
          @click="handleSimVerify"
        >
          유효성 체크
        </MsfButton>
        <MsfButton variant="validation" v-else-if="esimAuth.status.value === 'verified'" active>
          유효성 체크 완료
        </MsfButton>
        <MsfButton
          variant="subtle"
          :disabled="props.disabled"
          @click="toggleEsimEdit"
          v-if="esimAuth.status.value !== 'verified'"
        >
          {{ isEsimEditable ? '수정 완료' : '수정' }}
        </MsfButton>
      </MsfStack>
    </MsfFormGroup>
    <MsfFormGroup v-if="isJoinFeeRequired" label="가입비" tag="div" required>
      <MsfChip
        ref="joinPayMthdCdRef"
        v-model="formData.joinPayMthdCd"
        :data="joinPayMthdOptions"
        name="inp-joinPayMthd"
        :disabled="props.disabled"
      />
    </MsfFormGroup>
  </MsfStack>
  <MsfEsimScanModal
    v-model="isEsimScanModalOpen"
    :readonly="authFlags?.esimImei"
    @confirm="onEsimScanConfirm"
  />
  <MsfUsimScanModal
    v-model="isUsimScanModalOpen"
    :readonly="authFlags?.reqUsimSn"
    @confirm="onUsimScanConfirm"
  />
  <MsfSerialNumberScanModal v-model="isSerialNumberModalOpen" :readonly="authFlags?.reqUsimSn" />
  <!-- // SIM정보 -->
</template>

<style scoped lang="scss"></style>
