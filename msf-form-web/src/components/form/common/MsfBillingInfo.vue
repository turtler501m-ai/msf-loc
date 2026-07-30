<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="명세서 수신 유형" tag="div" required>
        <MsfChip
          ref="cstmrBillSendTypeCdRef"
          v-model="model.cstmrBillSendTypeCd"
          name="inp-stmtType"
          :data="billSendTypeOptions"
          :readonly="props.disabled"
        />
      </MsfFormGroup>
      <MsfFormGroup label="요금 납부 방법" tag="div" required>
        <MsfChip
          ref="reqPayTypeCdRef"
          v-model="model.reqPayTypeCd"
          name="inp-payMtd"
          :data="paymentMethodOptions"
          :readonly="isAutoAcctAuthLocked || isCardAuthLocked || isCombAuthLocked"
        />
        <template v-if="['AA', 'D'].includes(model.reqPayTypeCd)">
          <hr class="ut-line" />
          <MsfStack type="field" class="ut-w100p">
            <MsfChip
              ref="othersPaymentYnAcctRef"
              v-model="model.othersPaymentYn"
              name="inp-othersPaymentYn"
              :data="[
                { value: 'N', label: '본인납부' },
                { value: 'Y', label: '타인납부' },
              ]"
              :readonly="isAutoAcctAuthLocked"
            />
          </MsfStack>
          <MsfStack type="field">
            <MsfSelect
              ref="reqBankCdRef"
              title="은행 선택"
              v-model="model.reqBankCd"
              id="inp-reqBankCd"
              groupCode="BNK"
              placeholder="은행 선택"
              class="ut-w-300"
              :disabled="isAutoAcctAuthLocked"
              selectPopYn
            />
            <MsfNumberInput
              ref="reqAccountNoRef"
              v-model="model.reqAccountNo"
              id="inp-autoAcctNo"
              placeholder="계좌번호 입력"
              class="ut-w-200"
              :disabled="isAutoAcctAuthLocked"
            />
            <MsfButton variant="validation" v-if="autoAcctAuth.status.value === 'none'" disabled
              >유효성 체크</MsfButton
            >
            <MsfButton
              ref="autoAcctAuthBtnRef"
              variant="validation"
              v-else-if="autoAcctAuth.status.value === 'ready'"
              @click="handleAccountVerify"
              :disabled="!model.isAutoAgree || props.disabled"
              >유효성 체크</MsfButton
            >
            <MsfButton
              variant="validation"
              v-else-if="autoAcctAuth.status.value === 'verified'"
              active
              >유효성 체크 완료</MsfButton
            >
          </MsfStack>
          <MsfStack type="field" v-if="model.othersPaymentYn === 'Y'">
            <MsfInput
              ref="reqAccountNmRef"
              v-model="model.reqAccountNm"
              id="inp-autoPayerName"
              placeholder="납부 고객명"
              class="ut-w-300"
              maxlength="100"
              :disabled="isAutoAcctAuthLocked"
            />
            <MsfBirthdayInput
              ref="reqAccountRrnRef"
              v-model="model.reqAccountRrn"
              id="inp-autoPayerBirth"
              length="8"
              class="ut-w-200"
              :disabled="isAutoAcctAuthLocked"
            />
            <MsfSelect
              ref="reqAccountRelTypeCdRef"
              title="관계"
              v-model="model.reqAccountRelTypeCd"
              id="inp-reqAccountRelTypeCd"
              groupCode="AGR"
              placeholder="관계"
              :disabled="isAutoAcctAuthLocked"
            />
          </MsfStack>
          <MsfCheckbox
            ref="isAutoAcctAgreeRef"
            v-model="model.isAutoAgree"
            id="inp-isAutoAgree"
            label="본인(예금주 또는 가입고객)은 납부해야 할 요금에 대해 위 계좌(카드)에서 지정된 출금(결제)일에 인출(결제)되는 것에 동의합니다."
            :invalid="!model.isAutoAgree"
            class="ut-mt-8"
            :disabled="isAutoAcctAuthLocked"
          />
        </template>
        <template v-if="['C'].includes(model.reqPayTypeCd)">
          <hr class="ut-line" />
          <MsfStack type="field" class="ut-w100p">
            <MsfChip
              ref="othersPaymentYnCardRef"
              v-model="model.othersPaymentYn"
              name="inp-othersPaymentYn"
              :data="[
                { value: 'N', label: '본인납부' },
                { value: 'Y', label: '타인납부' },
              ]"
              :readonly="isCardAuthLocked"
            />
          </MsfStack>
          <MsfStack type="field">
            <MsfSelect
              ref="reqCardCompanyCdRef"
              title="카드사 선택"
              v-model="model.reqCardCompanyCd"
              id="inp-reqCardCompanyCd"
              groupCode="CRD"
              placeholder="카드사 선택"
              class="ut-w-300"
              :disabled="isCardAuthLocked"
              selectPopYn
            />
            <MsfNumberInput
              ref="reqCardNoRef"
              v-model="model.reqCardNo"
              id="inp-cardNo"
              placeholder="카드번호 입력"
              class="ut-w-200"
              maxlength="16"
              :disabled="isCardAuthLocked"
            />
            <MsfButton variant="validation" v-if="cardAuth.status.value === 'none'" disabled
              >유효성 체크</MsfButton
            >
            <MsfButton
              ref="cardAuthBtnRef"
              variant="validation"
              v-else-if="cardAuth.status.value === 'ready'"
              @click="handleCardVerify"
              :disabled="!model.isAutoAgree || props.disabled"
              >유효성 체크</MsfButton
            >
            <MsfButton variant="validation" v-else-if="cardAuth.status.value === 'verified'" active
              >유효성 체크 완료</MsfButton
            >
          </MsfStack>
          <MsfStack type="field">
            <MsfSelect
              ref="reqCardMmRef"
              title="유효기간(MM) 선택"
              v-model="model.reqCardMm"
              id="inp-reqCardMm"
              :options="cardMonthOptions"
              placeholder="MM"
              :disabled="isCardAuthLocked"
            />
            <MsfSelect
              ref="reqCardYyRef"
              title="유효기간(YY) 선택"
              v-model="model.reqCardYy"
              id="inp-reqCardYy"
              :options="cardYearOptions"
              placeholder="YY"
              :disabled="isCardAuthLocked"
            />
          </MsfStack>
          <MsfStack type="field" v-if="model.othersPaymentYn === 'Y'">
            <MsfInput
              ref="reqCardNmRef"
              v-model="model.reqCardNm"
              id="inp-cardPayerName"
              placeholder="납부 고객명"
              class="ut-w-300"
              maxlength="100"
              :disabled="isCardAuthLocked"
            />
            <MsfBirthdayInput
              ref="reqCardRrnRef"
              v-model="model.reqCardRrn"
              id="inp-cardPayerBirth"
              length="8"
              class="ut-w-200"
              :disabled="isCardAuthLocked"
            />
            <MsfSelect
              ref="cardRelationRef"
              title="관계"
              v-model="model.cardRelation"
              id="inp-cardRelation"
              groupCode="AGR"
              placeholder="관계"
              :disabled="isCardAuthLocked"
            />
          </MsfStack>
          <MsfCheckbox
            ref="isAutoCardAgreeRef"
            v-model="model.isAutoAgree"
            id="inp-isAutoAgree"
            label="본인(예금주 또는 가입고객)은 납부해야 할 요금에 대해 위 계좌(카드)에서 지정된 출금(결제)일에 인출(결제)되는 것에 동의합니다."
            :invalid="!model.isAutoAgree"
            class="ut-mt-8"
            :disabled="isCardAuthLocked"
          />
        </template>
        <template v-if="['0'].includes(model.reqPayTypeCd)">
          <MsfStack type="field">
            <MsfInput
              ref="combIdRef"
              v-model="model.combId"
              id="inp-combId"
              placeholder="청구계정ID 입력"
              class="ut-w-300"
              :disabled="isCombAuthLocked"
            />
            <MsfButton variant="validation" v-if="combAuth.status.value === 'none'" disabled
              >청구계정 체크</MsfButton
            >
            <MsfButton
              ref="combAuthBtnRef"
              variant="validation"
              v-else-if="combAuth.status.value === 'ready'"
              :disabled="props.disabled"
              @click="handleCombVerify"
              >청구계정 체크</MsfButton
            >
            <MsfButton variant="validation" v-else-if="combAuth.status.value === 'verified'" active
              >유효성 체크 완료</MsfButton
            >
          </MsfStack>
          <MsfCheckbox
            ref="combAgreeRef"
            v-model="model.combAgree"
            id="inp-combAgree"
            label="본인은 신청한 회선과 통합하여 요금이 청구되는 것에 동의합니다."
            :invalid="!model.combAgree"
            class="ut-mt-8"
            :disabled="isCombAuthLocked"
          />
        </template>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps, computed, onMounted, ref, watch, nextTick } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'
import { getCommonCodeList } from '@/libs/utils/comn.utils.js'
import { checkBirthAndAdult } from '@/libs/utils/string.utils'

const props = defineProps({
  title: { type: String, default: '납부 정보' },
  customerData: { type: Object, default: () => ({}) },
  // authFlags: { type: Object, default: () => ({}) },
  disabled: Boolean,
})
const model = defineModel({ type: Object, required: true })
const authFlags = defineModel('authFlags', { type: Object, default: () => ({}) })
// const emit = defineEmits(['update:authFlags'])

const cstmrBillSendTypeCdRef = ref(null)
const reqPayTypeCdRef = ref(null)
const othersPaymentYnAcctRef = ref(null)
const reqBankCdRef = ref(null)
const reqAccountNoRef = ref(null)
const autoAcctAuthBtnRef = ref(null)
const reqAccountNmRef = ref(null)
const reqAccountRrnRef = ref(null)
const reqAccountRelTypeCdRef = ref(null)
const isAutoAcctAgreeRef = ref(null)
const othersPaymentYnCardRef = ref(null)
const reqCardCompanyCdRef = ref(null)
const reqCardNoRef = ref(null)
const cardAuthBtnRef = ref(null)
const reqCardMmRef = ref(null)
const reqCardYyRef = ref(null)
const reqCardNmRef = ref(null)
const reqCardRrnRef = ref(null)
const cardRelationRef = ref(null)
const isAutoCardAgreeRef = ref(null)
const combIdRef = ref(null)
const combAuthBtnRef = ref(null)
const combAgreeRef = ref(null)

const isCardAuthLocked = computed(() => props.disabled || cardAuth.status.value === 'verified')
const isAutoAcctAuthLocked = computed(
  () => props.disabled || autoAcctAuth.status.value === 'verified',
)
const isCombAuthLocked = computed(() => props.disabled || combAuth.status.value === 'verified')

const paymentMethodOptions = ref([])

const billSendCodes = ref([])
const isMinor = computed(() => {
  return ['NM', 'FM'].includes(props.customerData?.cstmrTypeCd)
})
const billSendTypeOptions = computed(() => {
  return billSendCodes.value.map((item) => ({
    label: item.title,
    value: item.code,
    disabled: item.code === 'MB' && isMinor.value,
  }))
})

watch(isMinor, (newVal) => {
  if (newVal && model.value.cstmrBillSendTypeCd === 'MB') {
    const defaultOpt = billSendTypeOptions.value.find((opt) => !opt.disabled)
    if (defaultOpt) {
      model.value.cstmrBillSendTypeCd = defaultOpt.value
    }
  }
})

const currentYear = new Date().getFullYear() % 100
const currentMonth = new Date().getMonth() + 1

const cardYearOptions = computed(() => {
  const years = []
  for (let i = 0; i < 15; i++) {
    const year = currentYear + i
    years.push({ label: String(2000 + year), value: String(year) })
  }
  return years
})

const cardMonthOptions = computed(() => {
  const months = []
  const startMonth = String(model.value.reqCardYy) === String(currentYear) ? currentMonth : 1
  for (let i = startMonth; i <= 12; i++) {
    const m = String(i).padStart(2, '0')
    months.push({ label: m, value: m })
  }
  return months
})

// 유효기간 자동 초기화
watch(
  () => model.value.reqCardYy,
  (newYy) => {
    if (String(newYy) === String(currentYear) && Number(model.value.reqCardMm) < currentMonth) {
      model.value.reqCardMm = String(currentMonth).padStart(2, '0')
    }
  },
)

onMounted(async () => {
  // 명세서 수신 유형 공통코드 조회 및 기본값 세팅
  const codesList = await getCommonCodeList('BILL_SEND_TYPE_CD')
  if (codesList && codesList.length > 0) {
    billSendCodes.value = codesList
    const isExist = billSendTypeOptions.value.some(
      (opt) => opt.value === model.value.cstmrBillSendTypeCd && !opt.disabled,
    )
    if (!model.value.cstmrBillSendTypeCd || !isExist) {
      const defaultOpt = billSendTypeOptions.value.find((opt) => !opt.disabled)
      if (defaultOpt) {
        model.value.cstmrBillSendTypeCd = defaultOpt.value
      }
    }
  }

  // 요금 납부 방법 공통코드 조회 및 기본값 세팅
  const codes = await getCommonCodeList('PAY_TYPE_CD')
  const baseOptions = (codes || []).map((item) => ({ label: item.title, value: item.code }))

  await nextTick()

  const updateOptions = () => {
    const isTeCustomer = props.customerData?.isTeCustomer

    if (isTeCustomer) {
      paymentMethodOptions.value = baseOptions.filter((opt) => opt.value !== '0')
    } else {
      paymentMethodOptions.value = baseOptions
      if (paymentMethodOptions.value.length > 0) {
        const isExist = paymentMethodOptions.value.some(
          (opt) => opt.value === model.value.reqPayTypeCd,
        )
        if (!model.value.reqPayTypeCd || !isExist) {
          model.value.reqPayTypeCd = paymentMethodOptions.value[0].value
        }
      }
    }
  }

  watch(() => props.customerData?.installmentMonth, updateOptions, { immediate: true })
})

watch(
  () => model.value.reqPayTypeCd,
  (newVal, oldVal) => {
    if (oldVal) {
      if (['AA', 'D'].includes(oldVal) && !['AA', 'D'].includes(newVal)) {
        autoAcctAuth.reset()
      }
      if (oldVal === 'C' && newVal !== 'C') {
        cardAuth.reset()
      }
      if (oldVal === '0' && newVal !== '0') {
        combAuth.reset()
      }
    }
  },
)

watch(
  () => model.value.othersPaymentYn,
  (newVal, oldVal) => {
    if (oldVal !== undefined) {
      // 본인/타인 변경 시 기존 인증 내역만 초기화하여 재인증을 강제합니다. (입력값은 유지)
      autoAcctAuth.reset()
      cardAuth.reset()
      combAuth.reset()
    }
  },
)

const handleAccountVerify = async () => {
  const customer = props.customerData || {}
  const customerSsn = (
    customer.cstmrNativeRrn ||
    (customer.cstmrNativeRrn1 || '') + (customer.cstmrNativeRrn2 || '') ||
    (customer.cstmrForeignerRrn1 || '') + (customer.cstmrForeignerRrn2 || '') ||
    (customer.cstmrJuridicalRrn1 || '') + (customer.cstmrJuridicalRrn2 || '') ||
    ''
  ).replace(/[^0-9]/g, '')

  let reqAccountRrn
  if (model.value.othersPaymentYn === 'N') {
    // 본인 납부인 경우: 가입자 식별코드 유형에 맞춰 생년월일(6자리) 또는 사업자번호(10자리) 추출
    if (['JP', 'GO'].includes(customer.cstmrTypeCd)) {
      reqAccountRrn = (
        customer.cstmrPrivateBizNo ||
        customer.cstmrJuridicalBizNo ||
        customerSsn
      ).replace(/[^0-9]/g, '')
    } else {
      reqAccountRrn = customer.cstmrNativeRrn1 || customerSsn.substring(0, 6) || ''
    }
  } else {
    // 타인 납부인 경우: 입력된 납부자 Rrn 정제
    reqAccountRrn = (model.value.reqAccountRrn || '').replace(/[^0-9]/g, '')
    const isCard = ['C'].includes(model.value?.reqPayTypeCd)
    // 타인납부 생년월일 체크
    const checkResult = checkBirthAndAdult(
      isCard ? model.value.reqCardRrn : model.value.reqAccountRrn,
    )
    if (!checkResult?.isValidDate || !checkResult?.isAdult) {
      model.value.reqCardRrn = null
      model.value.reqAccountRrn = null
      return showAlert(checkResult.message)
    }
  }

  // YYYYMMDD(8자리) 형식인 경우 백엔드 서브스트링 연동 호환(YYMMDD)을 위해 앞 2자리 제거
  if (reqAccountRrn.length === 8) {
    reqAccountRrn = reqAccountRrn.substring(2)
  }

  const payload = {
    operTypeCd: customer.joinType || 'NAC3',
    cstmrTypeCd: customer.cstmrTypeCd || 'NA',
    cstmrNm: customer.cstmrNm || '',
    customerSsn: customerSsn,
    reqBankCd: model.value.reqBankCd,
    reqAccountNo: model.value.reqAccountNo,
    reqAccountNm: model.value.othersPaymentYn === 'N' ? customer.cstmrNm : model.value.reqAccountNm,
    reqAccountRrn: reqAccountRrn,
    othersPaymentYn: model.value.othersPaymentYn || 'N',
    agentCd: customer.agentCd || '',
  }

  try {
    const res = await post('/api/form/payment/account/verify', payload)
    if (res && res.data?.resCode === '0000') {
      autoAcctAuth.verify()
    }
  } catch (e) {
    console.error(e)
  }
}

const handleCardVerify = async () => {
  const customer = props.customerData || {}
  const customerSsn = (
    customer.cstmrNativeRrn ||
    (customer.cstmrNativeRrn1 || '') + (customer.cstmrNativeRrn2 || '') ||
    (customer.cstmrForeignerRrn1 || '') + (customer.cstmrForeignerRrn2 || '') ||
    (customer.cstmrJuridicalRrn1 || '') + (customer.cstmrJuridicalRrn2 || '') ||
    ''
  ).replace(/[^0-9]/g, '')

  let brthDate
  if (model.value.othersPaymentYn === 'N') {
    let rawBirth = (customer.cstmrNativeBirth || customer.userBirthDate || '').replace(
      /[^0-9]/g,
      '',
    )

    if (rawBirth.length !== 8) {
      const rrn1 = (customer.cstmrNativeRrn1 || customerSsn.substring(0, 6) || '').replace(
        /[^0-9]/g,
        '',
      )
      if (rrn1.length === 6) {
        const genderDigit = customerSsn.length >= 7 ? customerSsn.charAt(6) : ''
        if (['1', '2', '5', '6'].includes(genderDigit)) {
          rawBirth = `19${rrn1}`
        } else if (['3', '4', '7', '8'].includes(genderDigit)) {
          rawBirth = `20${rrn1}`
        } else {
          const yearNum = Number(rrn1.substring(0, 2))
          rawBirth = yearNum <= 30 ? `20${rrn1}` : `19${rrn1}`
        }
      }
    }
    brthDate = rawBirth
  } else {
    brthDate = (model.value.reqCardRrn || '').replace(/[^0-9]/g, '')
    const isCard = ['C'].includes(model.value?.reqPayTypeCd)
    // 타인납부 생년월일 체크
    const checkResult = checkBirthAndAdult(
      isCard ? model.value.reqCardRrn : model.value.reqAccountRrn,
    )
    if (!checkResult?.isValidDate || !checkResult?.isAdult) {
      model.value.reqCardRrn = null
      model.value.reqAccountRrn = null
      return showAlert(checkResult.message)
    }
  }

  const payload = {
    crdtCardNo: model.value.reqCardNo,
    crdtCardTermYear: model.value.reqCardYy,
    crdtCardTermMonth: model.value.reqCardMm,
    custNm: model.value.othersPaymentYn === 'N' ? customer.cstmrNm : model.value.reqCardNm,
    brthDate: brthDate,
    ncType: '',
    othersPaymentYn: model.value.othersPaymentYn || 'N',
    agentCd: customer.agentCd || '',
    customerSsn: customerSsn,
    cstmrTypeCd: customer.cstmrTypeCd || 'NA',
    operTypeCd: customer.joinType,
  }

  try {
    const res = await post('/api/form/payment/credit/verify', payload)
    if (res && res.data?.resCode === '0000') {
      const cardKindCd = res.data?.resData?.crdtCardKindCd
      if (cardKindCd) {
        model.value.reqCardCompanyCd = cardKindCd
      }
      // 매핑이 끝난 후 최종 verify 처리 (watch에 의한 'ready' 상태 롤백 현상 방지)
      await nextTick()
      cardAuth.verify()
    }
  } catch (e) {
    console.error(e)
  }
}

const acctValidate = () => {
  if (model.value?.othersPaymentYn === 'Y') {
    return [
      model.value.reqBankCd,
      model.value.reqAccountNo,
      model.value.isAutoAgree,
      model.value.reqAccountNm,
      model.value.reqAccountRrn,
      model.value.reqAccountRelTypeCd,
    ].every((x) => !!x)
  }
  return [model.value.reqBankCd, model.value.reqAccountNo, model.value.isAutoAgree].every(
    (x) => !!x,
  )
}

const cardValidate = () => {
  if (model.value?.othersPaymentYn === 'Y') {
    return [
      model.value.reqCardCompanyCd,
      model.value.reqCardNo,
      model.value.isAutoAgree,
      model.value.reqCardNm,
      model.value.reqCardRrn,
      model.value.cardRelation,
    ].every((x) => !!x)
  }
  return [model.value.reqCardCompanyCd, model.value.reqCardNo, model.value.isAutoAgree].every(
    (x) => !!x,
  )
}

const autoAcctAuth = useAuthButton(
  () => [
    model.value.reqBankCd,
    model.value.reqAccountNo,
    model.value.isAutoAgree,
    model.value.reqAccountNm,
    model.value.reqAccountRrn,
    model.value.reqAccountRelTypeCd,
  ],
  {
    get value() {
      return authFlags.value?.autoAcct || false
    },
    set value(v) {
      if (authFlags.value) {
        authFlags.value.autoAcct = v
      }
    },
  },
  acctValidate,
)

const cardAuth = useAuthButton(
  () => [
    model.value.reqCardCompanyCd,
    model.value.reqCardNo,
    model.value.isAutoAgree,
    model.value.reqCardNm,
    model.value.reqCardRrn,
    model.value.cardRelation,
    model.value.reqCardMm,
    model.value.reqCardYy,
  ],
  {
    get value() {
      return authFlags.value?.reqCardNo || false
    },
    set value(v) {
      if (authFlags.value) {
        authFlags.value.reqCardNo = v
      }
    },
  },
  cardValidate,
)

const combAuth = useAuthButton(() => [model.value?.combId, model.value?.combAgree], {
  get value() {
    return (authFlags.value?.combId && model.value?.combAgree) || false
  },
  set value(v) {
    if (authFlags.value) {
      authFlags.value.combId = v
    }
  },
})

const handleCombVerify = async () => {
  if (!model.value.combAgree) {
    showAlert('통합 청구 동의에 체크해 주세요.')
    return
  }

  const customer = props.customerData || {}
  const payload = {
    cstmrTypeCd: customer.cstmrTypeCd || 'NA',
    ban: model.value.combId,
    customerSsn:
      customer.cstmrNativeRrn1 + customer.cstmrNativeRrn2 ||
      customer.cstmrForeignerRrn1 + customer.cstmrForeignerRrn2 ||
      customer.cstmrJuridicalRrn1 + customer.cstmrJuridicalRrn2 ||
      '',
    customerLinkName: customer.cstmrNm,
    agentCd: customer.agentCd || '',
  }

  try {
    const res = await post('/api/form/payment/bill/verify', payload)
    if (res && res.data?.resCode === '0000') {
      combAuth.verify()
    }
  } catch (error) {
    console.error('Verify bill info error:', error)
  }
}

const validate = () => {
  if (!model.value.cstmrBillSendTypeCd || !model.value.cstmrBillSendTypeCd.trim()) return false
  if (!model.value.reqPayTypeCd) return false

  // 자동이체 (은행)
  if (['AA', 'D'].includes(model.value.reqPayTypeCd)) {
    if (!model.value.othersPaymentYn || !model.value.reqBankCd || !model.value.reqAccountNo)
      return false
    // 계좌 유효성 체크 필수
    if (!authFlags.value?.autoAcct) return false
    // 타인납부인 경우 대리인 정보 및 출금 동의 필수
    if (model.value.othersPaymentYn === 'Y') {
      if (
        !model.value.reqAccountNm ||
        !model.value.reqAccountRrn ||
        !model.value.reqAccountRelTypeCd
      )
        return false
      if (!model.value.isAutoAgree) return false
    }
  }
  // 신용카드
  else if (['C'].includes(model.value.reqPayTypeCd)) {
    if (!model.value.othersPaymentYn || !model.value.reqCardCompanyCd || !model.value.reqCardNo)
      return false
    // 카드 유효성 체크 필수
    if (!authFlags.value?.reqCardNo) return false
    if (!model.value.reqCardMm || !model.value.reqCardYy) return false
    // 타인납부인 경우 대리인 정보 필수
    if (model.value.othersPaymentYn === 'Y') {
      if (!model.value.reqCardNm || !model.value.reqCardRrn || !model.value.cardRelation)
        return false
      // 출금 동의 체크 필수
      if (!model.value.isAutoAgree) return false
    }
  }
  // 지로/가상계좌 등 기타 (추가 입력 없음)
  // 통합청구
  else if (['0'].includes(model.value.reqPayTypeCd)) {
    if (!model.value.combId) return false
    // 청구계정 체크 필수
    if (!authFlags.value?.combId) return false
    // 통합 청구 동의 필수
    if (!model.value.combAgree) return false
  }

  return true
}

const reset = () => {
  // 입력값들 '' 처리 및 기본값 복원
  if (model.value) {
    if (isMinor.value) {
      const defaultOpt = billSendTypeOptions.value.find((opt) => !opt.disabled)
      model.value.cstmrBillSendTypeCd = defaultOpt ? defaultOpt.value : 'EM'
    } else {
      model.value.cstmrBillSendTypeCd = 'MB'
    }

    if (paymentMethodOptions.value.length > 0) {
      model.value.reqPayTypeCd = paymentMethodOptions.value[0].value
    } else {
      model.value.reqPayTypeCd = ''
    }

    model.value.othersPaymentYn = 'N'
    model.value.reqBankCd = ''
    model.value.reqAccountNo = ''
    model.value.reqAccountNm = ''
    model.value.reqAccountRrn = ''
    model.value.reqAccountRelTypeCd = ''
    model.value.isAutoAgree = false
    model.value.reqCardCompanyCd = ''
    model.value.reqCardNo = ''
    model.value.reqCardMm = ''
    model.value.reqCardYy = ''
    model.value.reqCardNm = ''
    model.value.reqCardRrn = ''
    model.value.cardRelation = ''
    model.value.combId = ''
    model.value.combAgree = false
  }

  autoAcctAuth.reset()
  cardAuth.reset()
  combAuth.reset()
}

const checkValidation = () => {
  if (!model.value.cstmrBillSendTypeCd) {
    showAlert(`명세서 수신 유형을 선택하세요`, () => {
      cstmrBillSendTypeCdRef.value?.focus()
    })
    return false
  }
  if (!model.value.reqPayTypeCd) {
    showAlert(`요금 납부 방법을 선택하세요`, () => {
      reqPayTypeCdRef.value?.focus()
    })
    return false
  }

  if (['AA', 'D'].includes(model.value.reqPayTypeCd)) {
    // 자동이체 (은행)

    if (!model.value.othersPaymentYn) {
      showAlert(`본인납부/타인납부 중 하나를 선택하세요`, () => {
        othersPaymentYnAcctRef.value?.focus()
      })
      return false
    }
    if (!model.value.reqBankCd) {
      showAlert(`납부 은행을 선택하세요`, () => {
        reqBankCdRef.value?.focus()
      })
      return false
    }
    if (!model.value.reqAccountNo) {
      showAlert(`납부 은행 계좌번호를 입력하세요`, () => {
        reqAccountNoRef.value?.focus()
      })
      return false
    }
    if (!model.value.isAutoAgree) {
      showAlert(`요금 납부 방법 안내사항에 동의하세요`, () => {
        isAutoAcctAgreeRef.value?.focus()
      })
      return false
    }
    // 타인납부인 경우 대리인 정보 및 출금 동의 필수
    if (model.value.othersPaymentYn === 'Y') {
      if (!model.value.reqAccountNm) {
        showAlert(`납부 고객명을 입력하세요`, () => {
          reqAccountNmRef.value?.focus()
        })
        return false
      }
      if (!model.value.reqAccountRrn) {
        showAlert(`납부 고객 생년월일을 입력하세요`, () => {
          reqAccountRrnRef.value?.focus()
        })
        return false
      }
      if (!model.value.reqAccountRelTypeCd) {
        showAlert(`납부 고객과의 관계를 선택하세요`, () => {
          reqAccountRelTypeCdRef.value?.focus()
        })
        return false
      }
    }
    // 계좌 유효성 체크 필수
    if (!authFlags.value?.autoAcct) {
      showAlert(`납부 은행 계좌번호 유효성 체크를 실행하세요`, () => {
        autoAcctAuthBtnRef.value?.focus()
      })
      return false
    }
  } else if (['C'].includes(model.value.reqPayTypeCd)) {
    // 신용카드

    if (!model.value.othersPaymentYn) {
      showAlert(`본인납부/타인납부 중 하나를 선택하세요`, () => {
        othersPaymentYnCardRef.value?.focus()
      })
      return false
    }
    if (!model.value.reqCardCompanyCd) {
      showAlert(`카드사를 선택하세요`, () => {
        reqCardCompanyCdRef.value?.focus()
      })
      return false
    }
    if (!model.value.reqCardNo) {
      showAlert(`카드번호를 입력하세요`, () => {
        reqCardNoRef.value?.focus()
      })
      return false
    }
    if (!model.value.reqCardMm) {
      showAlert(`카드 유효기간(MM)을 선택하세요`, () => {
        reqCardMmRef.value?.focus()
      })
      return false
    }
    if (!model.value.reqCardYy) {
      showAlert(`카드 유효기간(YY)을 선택하세요`, () => {
        reqCardYyRef.value?.focus()
      })
      return false
    }
    // 출금 동의 체크 필수
    if (!model.value.isAutoAgree) {
      showAlert(`요금 납부 방법 안내사항에 동의하세요`, () => {
        isAutoCardAgreeRef.value?.focus()
      })
      return false
    }
    // 타인납부인 경우 대리인 정보 필수
    if (model.value.othersPaymentYn === 'Y') {
      if (!model.value.reqCardNm) {
        showAlert(`납부 고객명을 입력하세요`, () => {
          reqCardNmRef.value?.focus()
        })
        return false
      }
      if (!model.value.reqCardRrn) {
        showAlert(`납부 고객 생년월일을 입력하세요`, () => {
          reqCardRrnRef.value?.focus()
        })
        return false
      }
      if (!model.value.cardRelation) {
        showAlert(`납부 고객과의 관계를 선택하세요`, () => {
          cardRelationRef.value?.focus()
        })
        return false
      }
    }
    // 카드 유효성 체크 필수
    if (!authFlags.value?.reqCardNo) {
      showAlert(`카드번호 유효성 체크를 실행하세요`, () => {
        cardAuthBtnRef.value?.focus()
      })
      return false
    }
  } else if (['0'].includes(model.value.reqPayTypeCd)) {
    // 지로/가상계좌 등 기타 (추가 입력 없음)
    // 통합청구

    if (!model.value.combId) {
      showAlert(`청구계정ID를 입력하세요`, () => {
        combIdRef.value?.focus()
      })
      return false
    }
    // 통합 청구 동의 필수
    if (!model.value.combAgree) {
      showAlert(`요금 청구 안내사항에 동의하세요`, () => {
        combAgreeRef.value?.focus()
      })
      return false
    }
    // 청구계정 체크 필수
    if (!authFlags.value?.combId) {
      showAlert(`청구계정 체크를 실행하세요`, () => {
        combAuthBtnRef.value?.focus()
      })
      return false
    }
  }

  return true
}

defineExpose({ validate, reset, checkValidation })
</script>
