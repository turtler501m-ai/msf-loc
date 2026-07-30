<template>
  <div class="page-step-panel">
    <MsfLoadingComp v-if="isAgreementSaving" />
    <!-- 구비서류 -->
    <MsfRequiredDoc
      ref="requiredDocRef"
      v-model="formData"
      v-model:authFlags="terminationStore.authFlags"
      :refresh-key="terminationStore.cancelAuthResetKey"
      :disabled="isApplicationConfirmed"
    />
    <!-- 고객 안내 사항 -->
    <MsfTitleArea title="고객 안내 사항" />
    <MsfBox>
      <ul class="agree-list">
        <li>
          <p class="agree-tit">혜택 소멸사항 동의</p>
          <MsfTextList
            :items="[
              '고객님께 제공되었던 각종 할인 등의 혜택이 모두 소멸되어 다양한 혜택을 제공받을 수 없게 됩니다.',
              '해지 시 DB 단체보험, 제주항공, 티머니 등 케이티 엠모바일의 제휴서비스가 제공되지 않습니다.',
            ]"
            type="number"
            level="2"
            margin="0"
            bottomDivider
          />
          <MsfCheckbox
            id="termination-benefit-agree"
            v-model="formData.agreeCheck1"
            :disabled="isApplicationConfirmed"
            label="본인은 위 혜택에 대한 설명을 듣고, 케이티 엠모바일 해지시 혜택 소멸되는 사항에 대해 동의합니다."
          />
        </li>
      </ul>
    </MsfBox>

    <!-- 신청서 확인 -->
    <MsfAppConfirm
      ref="appConfirmRef"
      :disabled="!isAgreeChecked"
      formTypeCode="termination"
      guide-text="신청서 확인을 눌러서, 신청서 작성 내용을 확인하신 후 가입자(대리인) 서명을 등록해 주세요."
      :request-key="terminationStore.requestKey || ''"
      :cstmr-nm="appConfirmCstmrNm"
      :phone-no="appConfirmPhoneNo"
      :form-parameters="eformFormParameters"
      @click="onBeforeConfirmClick"
      @confirm="onConfirmApp"
      @edit="onEditApp"
    />
  </div>
</template>

<script setup>
import { computed, ref, watch, onMounted } from 'vue'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'
import { storeToRefs } from 'pinia'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
import { getCommonCodeList } from '@/libs/utils/comn.utils'

const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { formData } = storeToRefs(terminationStore)

const isAppConfirmed = ref(false)
const isReportSigned = ref(false)
const isAgreementSaving = ref(false)
const appConfirmRef = ref(null)
const requiredDocRef = ref(null)
const relationCodes = ref([])
const isApplicationConfirmed = computed(() => terminationStore.applicationConfirmed)

const isCheckedAgreement = (value) => value === true || value === 'Y'
const isAgreeChecked = computed(() => isCheckedAgreement(formData.value.agreeCheck1))

const isCompleteEffective = computed(
  () => isAgreeChecked.value && isAppConfirmed.value && isReportSigned.value,
)

const validate = () => isCompleteEffective.value

const joinDigits = (...parts) =>
  parts
    .map((part) => part || '')
    .join('')
    .replace(/[^0-9]/g, '')

const appConfirmCstmrNm = computed(() => formData.value.cstmrNm || '')
const appConfirmPhoneNo = computed(() =>
  joinDigits(formData.value.deviceChgTel1, formData.value.deviceChgTel2, formData.value.deviceChgTel3),
)

const pickFirst = (...values) =>
  values.find((value) => value !== undefined && value !== null && String(value) !== '') || ''

const getCodeTitle = (codes, value) => {
  const stringValue = String(value || '')
  if (!stringValue) return ''
  const item = (codes || []).find((code) => String(code.code || code.value || '') === stringValue)
  return item?.title || item?.label || stringValue
}

const blankIfHyphenOnly = (value) => {
  const text = String(value || '').trim()
  return text.replace(/-/g, '') === '' ? '' : text
}

const getTodayYmd = () => {
  const date = new Date()
  const yyyy = date.getFullYear()
  const mm = String(date.getMonth() + 1).padStart(2, '0')
  const dd = String(date.getDate()).padStart(2, '0')
  return `${yyyy}${mm}${dd}`
}

const shouldExposeDelegationInfo = (f) =>
  ['NM', 'FM'].includes(f.cstmrTypeCd) ||
  (['JP', 'GO'].includes(f.cstmrTypeCd) && f.cstmrVisitTypeCd === 'VDP')

const resolveField = (...keys) => {
  const f = formData.value
  const sources = [f, f.payData || {}, f.billData || {}]
  for (const source of sources) {
    for (const key of keys) {
      if (source?.[key] !== undefined && source?.[key] !== null && String(source[key]) !== '') {
        return source[key]
      }
    }
  }
  return ''
}

const deriveGenderFromRrn2 = (rrn2) => {
  const digit = (rrn2 || '').charAt(0)
  if (['1', '3', '5', '7'].includes(digit)) return 'M'
  if (['2', '4', '6', '8'].includes(digit)) return 'F'
  return ''
}

const eformJsonData = computed(() => {
  const f = formData.value
  const cancelMobileNo = joinDigits(f.deviceChgTel1, f.deviceChgTel2, f.deviceChgTel3)
  const receiveTelNo = joinDigits(f.afterTel1, f.afterTel2, f.afterTel3)
  const privateNo = pickFirst(
    joinDigits(f.cstmrNativeRrn1, f.cstmrNativeRrn2),
    joinDigits(f.cstmrForeignerRrn1, f.cstmrForeignerRrn2),
    joinDigits(f.cstmrJuridicalBizNo1, f.cstmrJuridicalBizNo2, f.cstmrJuridicalBizNo3),
    joinDigits(f.cstmrJuridicalRrn1, f.cstmrJuridicalRrn2),
  )
  const minorAgentRrn = blankIfHyphenOnly(pickFirst(
    f.agentBirthDate,
    f.repBirthDate,
    f.minorAgentBirth,
    f.minorAgentRrn,
    joinDigits(f.repRegistrationNo1, f.repRegistrationNo2),
    joinDigits(f.repForeignerNo1, f.repForeignerNo2),
  ))
  const minorAgentTelFn = blankIfHyphenOnly(joinDigits(
    f.minorAgentTelFnNo || f.repPhone1,
    f.minorAgentTelMnNo || f.repPhone2,
    f.minorAgentTelRnNo || f.repPhone3,
  ))
  const email = resolveField('cstmrEmailAdr', 'email', 'emailAdr', 'mailAdr', 'notiEmailAdr')
  const shopCd = f.shopCd || ''
  const shopNm = f.shopNm || ''
  const agencyContactNo = pickFirst(f.telephone, f.representativeTelephone)
  const exposeDelegationInfo = shouldExposeDelegationInfo(f)
  const usesCustomerGender = ['NA', 'NM', 'FN', 'FM'].includes(f.cstmrTypeCd)
  const isCorporateDelegate =
    ['JP', 'GO'].includes(f.cstmrTypeCd) && f.cstmrVisitTypeCd === 'VDP'
  const minorAgentRelationName = isCorporateDelegate
    ? f.minorAgentRelTypeNm || ''
    : getCodeTitle(relationCodes.value, f.minorAgentRelTypeCd)

  return {
    agentCd: shopNm && shopCd ? `${shopNm} / ${shopCd}` : shopNm || shopCd,
    telnum: agencyContactNo,
    saleManagerNm: f.managerNm || '',
    cstmrNm: f.cstmrNm || '',
    cstmrNativeBirth: f.cstmrNativeBirth || f.userBirthDate || '',
    gender: usesCustomerGender
      ? f.userGender ||
        f.cstmrNativeGenderCd ||
        f.cstmrForeignerGenderCd ||
        deriveGenderFromRrn2(f.cstmrNativeRrn2) ||
        deriveGenderFromRrn2(f.cstmrForeignerRrn2) ||
        ''
      : '',
    cstmrPrivateCname: privateNo,
    cstmrEmailAdr: email,
    cancelMobileNo,
    cstmrReceiveTelNo: receiveTelNo,
    cancelUseCompanyCd: f.cancelUseCompanyCd || '',
    payAmt: f.usageFee || '',
    pnltAmt: f.penaltyFee || '',
    lastSumAmt: f.finalAmount || '',
    instamtMnthCnt: f.remainPeriod || '',
    instamtMnthAmt: f.remainAmount || '',
    instamtPayMthdCd: resolveField('instamtPayMthdCd', 'installmentPaymentMethodCode'),
    receiveWayCd: f.postMethod || '',
    cstmrAdr: f.addr || resolveField('cstmrAdr', 'address', 'addr'),
    notiEmailAdr: email,
    reqTermSettleDeviceAgreeYn: f.agreeCheck1 ? 'Y' : 'N',
    reqBankCd: resolveField('reqBankCd', 'bankCd', 'bankCode', 'autoPayOrgNm', 'bankNm'),
    reqAccountNo: resolveField('reqAccountNo', 'accountNo', 'acctNo', 'autoPayAcctCardNo'),
    benefitAgreeYn: f.agreeCheck1 ? 'Y' : 'N',
    delegatorCustNm: exposeDelegationInfo ? f.cstmrNm || '' : '',
    msfRequestAgent: exposeDelegationInfo ? f.minorAgentNm || '' : '',
    minorAgentRelTypeCd: exposeDelegationInfo ? minorAgentRelationName : '',
    minorAgentRrn: exposeDelegationInfo ? minorAgentRrn : '',
    minorAgentTelFn: exposeDelegationInfo ? minorAgentTelFn : '',
    svcChgReqDt: getTodayYmd(),
    shopNm: f.realShopNm || f.shopNm || f.cntpntShopNm || f.cpntNm || f.agentNm || '',
    authInfo: resolveField('authInfo', 'certInfo', 'authenticationInfo'),
  }
})

const lastLoggedEformJsonData = ref('')

const eformFormParameters = computed(() => {
  const jsonStr = JSON.stringify(eformJsonData.value, null, 2)
  if (jsonStr !== lastLoggedEformJsonData.value) {
    lastLoggedEformJsonData.value = jsonStr
    console.log('[TerminationAgreement] eformJsonData:\n' + jsonStr)
  }
  const params = [
    {
      name: 'jsondata',
      value: JSON.stringify(eformJsonData.value),
    },
  ]
  return params
})

const focusField = (id) => {
  setTimeout(() => {
    document.getElementById(id)?.focus()
  }, 0)
}

const validateRequiredWithAlert = () => {
  if (!terminationStore.validateCustomerWithAlert()) return false
  if (!terminationStore.validateProductWithAlert()) return false
  if (!isAgreeChecked.value) {
    showAlert('혜택 소멸사항 동의가 필요합니다.', () => focusField('termination-benefit-agree'))
    return false
  }
  return true
}

const validateWithAlert = () => {
  if (!validateRequiredWithAlert()) return false
  if (!isAppConfirmed.value || !isReportSigned.value) {
    showAlert('신청서 확인 버튼을 클릭하여 서명을 완료해 주세요.')
    return false
  }
  return true
}

const onBeforeConfirmClick = (event) => {
  // 신규/변경과 동일하게 기본 팝업 오픈을 막고 검증 완료 후 수동으로 연다.
  event.preventDefault()
  event.stopPropagation()

  console.log(
    '[TerminationAgreement] onBeforeConfirmClick called. RequestKey:',
    terminationStore.requestKey,
  )
  if (!isAgreeChecked.value) {
    showAlert('해지 확인 동의가 필요합니다.', () => focusField('termination-benefit-agree'))
    return false
  }
  if (!validateRequiredWithAlert()) {
    console.warn('[TerminationAgreement] validateRequiredWithAlert failed')
    return false
  }

  formData.value.agreeCheck2 = formData.value.agreeCheck1

  if (requiredDocRef.value?.validate?.() !== true) {
    showConfirm(
      '구비서류가 미등록 되었습니다.',
      () => appConfirmRef.value?.open(),
      "개통 후 추가 등록하시려면 '확인'을 누르세요.",
    )
    return true
  }

  appConfirmRef.value?.open()
  return true
}

const hasRequiredReportSignatures = (result) => {
  const signatureValidation = result?.rawResult?.signatureValidation
  const validationResults = signatureValidation?.results

  return (
    signatureValidation?.signed === true &&
    Array.isArray(validationResults) &&
    validationResults.length > 0 &&
    validationResults.every(
      (item) =>
        item?.signed === true &&
        Array.isArray(item.signatureValues) &&
        item.signatureValues.length >= 2,
    )
  )
}

const onConfirmApp = (result) => {
  if (!hasRequiredReportSignatures(result)) {
    isReportSigned.value = false
    isAppConfirmed.value = false
    terminationStore.setApplicationConfirmed(false)
    showAlert('가입자(대리인) 서명을 모두 완료해 주세요.')
    checkRequiredFields()
    return
  }

  isReportSigned.value = true
  isAppConfirmed.value = true
  terminationStore.setApplicationConfirmed(true)
  console.log('[해지][동의확인] 신청서 저장 결과 수신', result)
  const docId = result?.uploadResults?.[0]?.documentId || result?.documentIds?.[0] || ''
  if (docId) {
    terminationStore.documentId = docId
    console.log('[해지][동의확인] documentId 스토어 적재 완료', { documentId: docId })
  } else {
    console.warn('[해지][동의확인] documentId 없음 — result 구조 확인 필요', { uploadResults: result?.uploadResults })
  }

  const eformFile = result?.eformsignFileData?.[0]
  const filePath = eformFile?.file?.filePath || eformFile?.filePath || ''
  const fileName =
    eformFile?.file?.fileName ||
    eformFile?.fileName ||
    (filePath ? filePath.split('/').pop() : '')
  terminationStore.fileNm = filePath
  terminationStore.fileMaskNm = fileName
}

const onEditApp = () => {
  isReportSigned.value = false
  isAppConfirmed.value = false
  terminationStore.setApplicationConfirmed(false)
  terminationStore.resetAuthForEdit()
  checkRequiredFields()
}

const checkRequiredFields = () => {
  emit('complete', isCompleteEffective.value)
}

watch(
  isCompleteEffective,
  () => {
    checkRequiredFields()
  },
  { immediate: true },
)

watch(isAgreeChecked, (checked) => {
  if (!checked) {
    isReportSigned.value = false
    isAppConfirmed.value = false
    terminationStore.setApplicationConfirmed(false)
  }
})

onMounted(async () => {
  checkRequiredFields()
  relationCodes.value = (await getCommonCodeList('AGR')) || []
})

const reset = async () => {
  terminationStore.resetStep(2)
  isReportSigned.value = false
  isAppConfirmed.value = false
  terminationStore.setApplicationConfirmed(false)
  checkRequiredFields()
}

const save = async () => {
  if (isAgreementSaving.value) return false

  console.log('[해지][동의정보저장] 요청 시작', {
    isAgreeChecked: isAgreeChecked.value,
    isAppConfirmed: isAppConfirmed.value,
    agreeCheck1: formData.value.agreeCheck1,
    agreeCheck2: formData.value.agreeCheck2,
    agreeCheck3: formData.value.agreeCheck3,
  })

  if (!validateWithAlert()) {
    console.warn('[해지][동의정보저장] 진행 중단', { reason: 'agreement incomplete' })
    return false
  }

  formData.value.agreeCheck2 = formData.value.agreeCheck1
  console.log('[해지][동의정보저장] 신청완료 호출')
  isAgreementSaving.value = true

  try {
    const result = await terminationStore.apiCompleteApplication()
    console.log('[해지][동의정보저장] 화면 데이터 반영 결과', { result })
    return result
  } finally {
    isAgreementSaving.value = false
  }
}

const getCompleteErrorMessage = () => terminationStore.getCompleteErrorMessage()

const joinPhone = (...parts) => (parts.every(Boolean) ? parts.join('-') : '')

const getCompleteData = () => {
  const f = formData.value
  const cancelPhone = joinPhone(f.deviceChgTel1, f.deviceChgTel2, f.deviceChgTel3)
  const afterPhone = joinPhone(f.afterTel1, f.afterTel2, f.afterTel3)

  return {
    // 신청서 키
    requestKey: terminationStore.requestKey || '',

    // 신규/변경, 서비스변경 메뉴일 경우, 가입 고객 이름
    // 명의변경 메뉴일 경우, 양수인 이름
    // 서비스해지 메뉴일 경우, 해지 고객 이름
    name: f.cstmrNm || '',

    mobiles: [
      // 고객이 미성년자일 경우 법정대리인 이름과 법정대리인 휴대폰번호
      // 고객이 미성년자가 아니고 신규/변경, 서비스변경 메뉴일 경우, 가입 고객 이름과 가입 휴대폰번호
      // 고객이 미성년자가 아니고 명의변경 메뉴일 경우, 양수인 이름과 양수받을 휴대폰번호
      // 고객이 미성년자가 아니고 서비스해지 메뉴일 경우, 해지 고객 이름과 해지 휴대폰번호 000-0000-0000
      {
        name: f.cstmrNm || '',
        mobile: cancelPhone,
      },

      // 신규/변경, 서비스변경 메뉴일 경우, 고객 이름과 가입자 연락처의 휴대폰번호
      // 명의변경 메뉴일 경우, 양수인 이름과 고객(양수고객) 연락처의 휴대폰번호
      // 서비스해지 메뉴일 경우, 해지 고객 이름과 해지 후 연락처의 연락 전화번호
      {
        name: f.cstmrNm || '',
        mobile: afterPhone,
      },
    ],
  }
}

defineExpose({
  save,
  validate,
  validateWithAlert,
  reset,
  getCompleteData,
  getCompleteErrorMessage,
})
</script>

<style scoped></style>
