<template>
  <div>
    <MsfLoadingComp v-if="isSubscriberAuthLoading" />
    <MsfTitleArea :title="computedTitle" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput
          id="inp-cstmrNm"
          v-model="model.cstmrNm"
          placeholder="이름"
          class="ut-w-300"
          maxlength="100"
          :readonly="isCustomerNameReadonly"
        />
      </MsfFormGroup>

      <MsfFormGroup v-if="!isCorporate" label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            id="inp-userBirthDate"
            v-model="model.userBirthDate"
            length="8"
            class="ut-w-300"
            :readonly="isReadonly"
          />
          <MsfRadioGroup
            v-if="showsUserGender"
            :name="`${name}-user-gender`"
            v-model="model.userGender"
            :options="[
              { value: 'M', label: '남' },
              { value: 'F', label: '여' },
            ]"
            class="ut-ml-16"
            :disabled="isReadonly"
          />
        </MsfStack>
      </MsfFormGroup>

      <template v-if="isCorporate">
        <MsfFormGroup label="법인등록번호" required>
          <MsfStack type="field">
            <MsfNumberInput
              id="inp-cstmrJuridicalRrn1"
              ref="cstmrJuridicalRrn1Ref"
              v-model="model.cstmrJuridicalRrn1"
              placeholder="앞 6자리"
              maxlength="6"
              :readonly="isReadonly"
              @maxlength="cstmrJuridicalRrn2Ref?.focus()"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              id="inp-cstmrJuridicalRrn2"
              ref="cstmrJuridicalRrn2Ref"
              v-model="model.cstmrJuridicalRrn2"
              placeholder="뒤 7자리"
              maxlength="7"
              :readonly="isReadonly"
            />
          </MsfStack>
        </MsfFormGroup>

        <!-- 법인, 공공기관인 경우 사업자 교부일자 추가 -->
        <MsfFormGroup v-if="showsBusinessIssuDate" label="사업자 교부일자" required>
          <MsfDateInput
            v-model="businessIssuDate"
            id="inp-bizIssuDate"
            placeholder="사업자 교부일자 선택(YYYYMMDD)"
            class="ut-w-300"
            :max-date="new Date()"
            :readonly="isReadonly"
          />
        </MsfFormGroup>

        <MsfFormGroup label="대표자명" required>
          <MsfInput
            id="inp-cstmrJuridicalRepNm"
            v-model="model.cstmrJuridicalRepNm"
            placeholder="대표자명"
            class="ut-w-300"
            maxlength="100"
            :readonly="isCorporateNameReadonly"
          />
        </MsfFormGroup>
      </template>

      <MsfFormGroup v-if="showsBusinessRegistrationNo" label="사업자등록번호">
        <MsfStack type="field">
          <MsfNumberInput
            id="inp-cstmrJuridicalBizNo1"
            ref="bizNo1Ref"
            v-model="model.cstmrJuridicalBizNo1"
            placeholder="앞 3자리"
            maxlength="3"
            :readonly="isReadonly"
            @maxlength="bizNo2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            id="inp-cstmrJuridicalBizNo2"
            ref="bizNo2Ref"
            v-model="model.cstmrJuridicalBizNo2"
            placeholder="가운데 2자리"
            maxlength="2"
            :readonly="isReadonly"
            @maxlength="bizNo3Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            id="inp-cstmrJuridicalBizNo3"
            ref="bizNo3Ref"
            v-model="model.cstmrJuridicalBizNo3"
            placeholder="뒤 5자리"
            maxlength="5"
            :readonly="isReadonly"
          />
        </MsfStack>
      </MsfFormGroup>

      <MsfFormGroup v-if="hasPersonalBusinessRegistrationNo" label="사업자 발급일자" required>
        <MsfDateInput
          v-model="model.cstmrPrivateBizNoIssuDate"
          id="inp-privateBizIssuDate"
          placeholder="사업자 발급일자 선택(YYYYMMDD)"
          class="ut-w-300"
          :max-date="new Date()"
          :readonly="isReadonly"
        />
      </MsfFormGroup>

      <MsfFormGroup :label="phoneLabel" required>
        <MsfStack type="field">
          <MsfNumberInput
            id="inp-deviceChgTel1"
            ref="deviceChgTel1Ref"
            v-model="model.deviceChgTel1"
            placeholder="앞자리"
            maxlength="3"
            :readonly="isReadonly"
            :disabled="true"
            @maxlength="deviceChgTel2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            id="inp-deviceChgTel2"
            ref="deviceChgTel2Ref"
            v-model="model.deviceChgTel2"
            placeholder="가운데 4자리"
            maxlength="4"
            :readonly="isReadonly"
            @maxlength="deviceChgTel3Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            id="inp-deviceChgTel3"
            ref="deviceChgTel3Ref"
            v-model="model.deviceChgTel3"
            placeholder="뒤 4자리"
            maxlength="4"
            :readonly="isReadonly"
            @maxlength="focusAuthButton"
          />
          <MsfButton
            id="btn-deviceChgTelAuth"
            ref="authButtonRef"
            variant="toggle"
            v-if="deviceChgAuth.status.value === 'none'"
            disabled
          >
            인증
          </MsfButton>
          <MsfButton
            id="btn-deviceChgTelAuth"
            ref="authButtonRef"
            variant="toggle"
            v-else-if="deviceChgAuth.status.value === 'ready'"
            :disabled="props.disabled || isSubscriberAuthLoading"
            @click="handleDeviceChgVerify"
          >
            {{ isSubscriberAuthLoading ? '처리중...' : '인증' }}
          </MsfButton>
          <MsfButton
            id="btn-deviceChgTelAuth"
            ref="authButtonRef"
            variant="toggle"
            v-else-if="deviceChgAuth.status.value === 'verified'"
            active
          >
            인증 완료
          </MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { post, postRaw } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'

// 서비스변경/서비스해지 전용 가입자 정보 컴포넌트.
// 다른 가입/명의변경 화면의 가입자 정보는 MsfSubscriberInfo.vue를 사용한다.
const props = defineProps({
  title: { type: String, default: '가입자 정보' },
  phoneLabel: { type: String, default: '휴대폰번호' },
  name: { type: String, default: 'base' },
  disabled: { type: Boolean, default: false },
})

const model = defineModel({ type: Object, required: true })
const terminationStore = useMsfFormTerminationStore()
const serviceChangeStore = useMsfFormSvcChgStore()

const deviceChgTel1Ref = ref(null)
const deviceChgTel2Ref = ref(null)
const deviceChgTel3Ref = ref(null)
const cstmrJuridicalRrn1Ref = ref(null)
const cstmrJuridicalRrn2Ref = ref(null)
const bizNo1Ref = ref(null)
const bizNo2Ref = ref(null)
const bizNo3Ref = ref(null)
const authButtonRef = ref(null)
const isSubscriberAuthLoading = ref(false)

const focusAuthButton = () => {
  const buttonInstance = authButtonRef.value
  if (typeof buttonInstance?.focus === 'function') {
    buttonInstance.focus()
    return
  }

  const buttonEl = buttonInstance?.$el || document.getElementById('btn-deviceChgTelAuth')
  if (typeof buttonEl?.focus === 'function') {
    buttonEl.focus()
    return
  }

  buttonEl?.querySelector?.('button')?.focus?.()
}

const isTerminationForm = computed(() => model.value?.formType === 'TERMINATION')
const isServiceChangeForm = computed(() => model.value?.formType === 'SERVICECHANGE')
const showsUserGender = computed(() => isServiceChangeForm.value || isTerminationForm.value)
const isCorporate = computed(() => ['JP', 'GO'].includes(model.value?.cstmrTypeCd))
const businessIssuDate = computed({
  get: () =>
    isTerminationForm.value
      ? model.value?.cstmrJuridicalBizNoIssuDate || ''
      : model.value?.identityIssuDate || '',
  set: (value) => {
    if (isTerminationForm.value) {
      model.value.cstmrJuridicalBizNoIssuDate = value
      return
    }
    model.value.identityIssuDate = value
  },
})
const showsBusinessIssuDate = computed(
  () => !isTerminationForm.value && !isServiceChangeForm.value && isCorporate.value,
)
const showsBusinessRegistrationNo = computed(
  () =>
    isCorporate.value ||
    (isServiceChangeForm.value && ['NA', 'FN'].includes(model.value?.cstmrTypeCd)),
)
const hasPersonalBusinessRegistrationNo = computed(
  () =>
    !isServiceChangeForm.value &&
    ['NA', 'FN'].includes(model.value?.cstmrTypeCd) &&
    hasExactLength(model.value?.cstmrJuridicalBizNo1, 3) &&
    hasExactLength(model.value?.cstmrJuridicalBizNo2, 2) &&
    hasExactLength(model.value?.cstmrJuridicalBizNo3, 5),
)
const isMinor = computed(() => ['NM', 'FM'].includes(model.value?.cstmrTypeCd))

const computedTitle = computed(() => {
  if (props.title === '가입자 정보' && isMinor.value) {
    return '가입자 정보(미성년자)'
  }
  return props.title
})

const getLogPrefix = (task) => `${isTerminationForm.value ? '[해지]' : '[변경]'}[${task}]`

const resolveAuthFlag = () => {
  const result = isTerminationForm.value
    ? terminationStore.authFlags?.cancelPhone || false
    : serviceChangeStore.authFlags?.deviceChgTel || false

  console.log(`${getLogPrefix('가입자인증상태')} 조회`, {
    formType: model.value?.formType,
    result,
    terminationFlag: terminationStore.authFlags?.cancelPhone,
    serviceChangeFlag: serviceChangeStore.authFlags?.deviceChgTel,
  })

  return result
}

const updateAuthFlag = (value) => {
  if (isTerminationForm.value) {
    if (terminationStore.authFlags) terminationStore.authFlags.cancelPhone = value
    return
  }

  if (serviceChangeStore.authFlags) serviceChangeStore.authFlags.deviceChgTel = value
}

const hasValue = (value) => value !== undefined && value !== null && String(value).trim().length > 0
const hasExactLength = (value, length) => String(value || '').trim().length === length

const validateDeviceChgAuthReady = (values) => {
  const [cstmrNm, ...rest] = values
  const [deviceChgTel1, deviceChgTel2, deviceChgTel3] = rest.slice(-3)
  const requiredCustomerValues = [cstmrNm, ...rest.slice(0, -3)]

  return (
    requiredCustomerValues.every(hasValue) &&
    hasExactLength(deviceChgTel1, 3) &&
    hasExactLength(deviceChgTel2, 4) &&
    hasExactLength(deviceChgTel3, 4)
  )
}

// 법인/공공기관: 이름 + (법인등록번호 완성 OR 사업자번호 완성) + 전화번호
const validateCorporateAuthReady = (values) => {
  const [cstmrNm, rrn1, rrn2, biz1, biz2, biz3, tel1, tel2, tel3] = values
  const hasJuridicalNo = hasExactLength(rrn1, 6) && hasExactLength(rrn2, 7)
  const hasBizNo = hasExactLength(biz1, 3) && hasExactLength(biz2, 2) && hasExactLength(biz3, 5)
  return (
    hasValue(cstmrNm) &&
    (hasJuridicalNo || hasBizNo) &&
    hasExactLength(tel1, 3) &&
    hasExactLength(tel2, 4) &&
    hasExactLength(tel3, 4)
  )
}

const resolveValidator = (values) =>
  isCorporate.value ? validateCorporateAuthReady(values) : validateDeviceChgAuthReady(values)

const deviceChgAuth = useAuthButton(
  () => [
    model.value?.cstmrNm,
    ...(isCorporate.value
      ? [
          model.value?.cstmrJuridicalRrn1,
          model.value?.cstmrJuridicalRrn2,
          model.value?.cstmrJuridicalBizNo1,
          model.value?.cstmrJuridicalBizNo2,
          model.value?.cstmrJuridicalBizNo3,
        ]
      : [model.value?.userBirthDate]),
    model.value?.deviceChgTel1,
    model.value?.deviceChgTel2,
    model.value?.deviceChgTel3,
  ],
  {
    get value() {
      return resolveAuthFlag()
    },
    set value(value) {
      updateAuthFlag(value)
    },
  },
  resolveValidator,
)

const isVerified = computed(() => deviceChgAuth.status.value === 'verified')
const isReadonly = computed(() => props.disabled || model.value?.isSaved || isVerified.value)
const isCustomerNameReadonly = computed(() => isReadonly.value)
const isCorporateNameReadonly = computed(() => isReadonly.value)

// 인증 플로우를 다시 시도 가능한 상태로 되돌린다.
// 어떤 중간 단계가 실패해도 화면은 "인증" 버튼 상태로 남아야 한다.
const resetAuthProgress = () => {
  deviceChgAuth.status.value = 'ready'
  updateAuthFlag(false)
  model.value.isVerified = false
}

// 휴대폰 인증과 가입정보조회가 모두 성공한 뒤에만 최종 인증 완료로 전환한다.
// 이 함수 외부에서는 verified/authFlag true 처리를 하지 않도록 흐름을 고정한다.
const completeAuthProgress = (myinfoResult) => {
  deviceChgAuth.status.value = 'verified'
  updateAuthFlag(true)
  model.value.isVerified = true
  if (isServiceChangeForm.value) {
    model.value.mobileNo1 = model.value.deviceChgTel1 || ''
    model.value.mobileNo2 = model.value.deviceChgTel2 || ''
    model.value.mobileNo3 = model.value.deviceChgTel3 || ''
  }
  console.log(`${getLogPrefix('휴대폰인증')} 최종 완료`, {
    contractNum: model.value.contractNum,
    ncn: model.value.ncn,
    custId: model.value.custId,
    lstComActvDate: model.value.lstComActvDate,
    authStatus: deviceChgAuth.status.value,
    authFlag: resolveAuthFlag(),
    myinfoLoaded: !!myinfoResult,
  })
}

// 인증 플로우 실패 공통 처리.
// 실패 사유를 로그에 남기고, 필요한 경우에만 사용자 알림을 띄운다.
const failAuthProgress = (reason, details = {}, message = '') => {
  resetAuthProgress()
  console.warn(`${getLogPrefix('휴대폰인증')} 진행 중단`, {
    reason,
    authStatus: deviceChgAuth.status.value,
    authFlag: resolveAuthFlag(),
    ...details,
  })
  if (message) showAlert(message)
}

const getFormResponseMessage = (response) =>
  response?.data?.resMessage || response?.data?.message || response?.message || ''

// 서비스변경/해지 인증에서 같이 비교할 식별번호를 만든다.
// 개인 화면은 현재 생년월일 8자리만 입력받으므로 DOBYYYYMMDD 비교에 사용하고,
// 법인/공공 화면은 법인등록번호를 우선 사용하되 사업자등록번호가 있으면 보조 식별값으로 사용할 수 있게 전달한다.
// 법인: bizNo=사업자번호, cstmrJuridicalRrn=법인등록번호 로 분리하여 전송
const resolveCorporateNos = () => ({
  bizNo: `${model.value?.cstmrJuridicalBizNo1 || ''}${model.value?.cstmrJuridicalBizNo2 || ''}${model.value?.cstmrJuridicalBizNo3 || ''}`,
  cstmrJuridicalRrn: `${model.value?.cstmrJuridicalRrn1 || ''}${model.value?.cstmrJuridicalRrn2 || ''}`,
})

const resolveCustomerSsn = () => {
  if (isCorporate.value) return ''
  return model.value?.userBirthDate || ''
}

// 1단계: 휴대폰 인증 API만 수행한다.
// post()는 resMessage를 자동 alert로 띄우므로, "인증완료" 선표출을 막기 위해 postRaw()를 사용한다.
const requestMemberAuth = async (phoneNo, customerLinkName) => {
  const authResponse = await postRaw('/api/form/ktmmember/servicechange-auth', {
    subscriberNo: phoneNo,
    customerLinkName,
    customerSsn: resolveCustomerSsn(),
    cstmrType: model.value?.cstmrTypeCd,
    ...(isCorporate.value && {
      bizNo: resolveCorporateNos().bizNo,
      cstmrJuridicalRrn: resolveCorporateNos().cstmrJuridicalRrn,
    }),
  })
  const response = authResponse?.data
  const formResponse = response?.data || {}
  const authData = formResponse?.resData || formResponse || {}
  const contractNum = authData.contractNum || authData.contract_num || ''
  const svcCntrNo = authData.svcCntrNo || authData.svc_cntr_no || authData.ncn || ''
  const lstComActvDate =
    authData.lstComActvDate || authData.lst_com_actv_date || authData.initActivationDate || ''
  const isSuccess =
    response?.code === '0000' &&
    (!formResponse?.resCode || formResponse.resCode === '0000') &&
    !!svcCntrNo

  console.log(`${getLogPrefix('휴대폰인증')} 인증 응답`, {
    code: response?.code,
    resCode: formResponse?.resCode,
    message: getFormResponseMessage(response),
    contractNum,
    svcCntrNo,
  })

  if (!isSuccess) {
    return {
      ok: false,
      message: getFormResponseMessage(response) || '휴대폰 인증에 실패했습니다.',
      response,
      contractNum,
      svcCntrNo,
    }
  }

  return { ok: true, authData, contractNum, svcCntrNo, lstComActvDate }
}

// 2단계 준비: 인증 API에서 받은 계약정보를 화면 모델에 반영한다.
// 아직 가입정보조회 전이므로 인증 완료 상태로는 바꾸지 않는다.
const applyAuthData = ({ authData, contractNum, svcCntrNo, lstComActvDate }) => {
  model.value.contractNum = contractNum
  model.value.ncn = svcCntrNo
  model.value.custId = authData.customerId || authData.customer_id || model.value.custId || ''
  model.value.lstComActvDate = lstComActvDate
  model.value.simTypeCd = authData.esimYn === 'Y' ? 'ESIM' : 'USIM'
  if (authData.customerLinkName) model.value.cstmrNm = authData.customerLinkName
  if (authData.gender) model.value.userGender = authData.gender

  if (isCorporate.value) {
    const representativeName =
      authData.representativeName ||
      authData.repName ||
      authData.cstmrJuridicalRepNm ||
      authData.customerLinkName ||
      ''
    if (!model.value.cstmrJuridicalRepNm && representativeName) {
      model.value.cstmrJuridicalRepNm = representativeName
    }
  }
}

// 2단계: 계약번호 기반 가입정보조회.
// 이 결과가 있어야 실제 업무 진행에 필요한 고객/계약 정보가 확보된 것으로 본다.
const loadSubscriptionInfo = async ({ contractNum, svcCntrNo }, phoneNo) => {
  console.log(`${getLogPrefix('가입정보조회')} 요청`, { contractNum, svcCntrNo, phoneNo })

  if (isTerminationForm.value) {
    terminationStore.setTerminationContract(contractNum, svcCntrNo, 'MsfSubscriberChgInfo')
    model.value.ncn = svcCntrNo
    return terminationStore.apiGetMyinfoView()
  }

  if (isServiceChangeForm.value) {
    model.value.ncn = svcCntrNo
    return serviceChangeStore.apiGetMyinfoView()
  }

  return null
}

watch(
  () => model.value?.deviceChgTel1,
  () => {
    // 이 컴포넌트는 서비스변경/해지 전용이므로 휴대폰 앞자리는 010으로 고정한다.
    if (model.value.deviceChgTel1 !== '010') {
      model.value.deviceChgTel1 = '010'
    }
  },
  { immediate: true },
)

// 신청서 확인 팝업 수정 버튼 → 인증 상태를 '인증' 버튼으로 되돌린다.
watch(
  () =>
    isTerminationForm.value
      ? terminationStore.cancelAuthResetKey
      : serviceChangeStore.cancelAuthResetKey,
  (val, old) => {
    if (typeof old === 'number') {
      deviceChgAuth.requireReauth()
    }
  },
)

const handleDeviceChgVerify = async () => {
  if (isSubscriberAuthLoading.value) return

  if (isTerminationForm.value && !terminationStore.validateCustomerAgeWithAlert()) {
    return
  }
  if (isServiceChangeForm.value && !serviceChangeStore.validateCustomerAgeWithAlert()) {
    return
  }

  if (hasPersonalBusinessRegistrationNo.value && !model.value.cstmrPrivateBizNoIssuDate) {
    showAlert('사업자 발급일자를 선택해 주세요.', () =>
      document.getElementById('inp-privateBizIssuDate')?.focus(),
    )
    return
  }

  if (
    !hasExactLength(model.value.deviceChgTel1, 3) ||
    !hasExactLength(model.value.deviceChgTel2, 4) ||
    !hasExactLength(model.value.deviceChgTel3, 4)
  ) {
    showAlert('휴대폰번호를 모두 입력해 주세요.')
    return
  }

  const phoneNo = `${model.value.deviceChgTel1}${model.value.deviceChgTel2}${model.value.deviceChgTel3}`
  const customerLinkName = (model.value.cstmrNm || '').trim()

  isSubscriberAuthLoading.value = true

  updateAuthFlag(false)
  model.value.isVerified = false

  console.log(`${getLogPrefix('휴대폰인증')} 요청 시작`, {
    formType: model.value.formType,
    cstmrNm: model.value.cstmrNm,
    customerLinkName,
    phoneNo,
    authStatus: deviceChgAuth.status.value,
    authFlag: resolveAuthFlag(),
  })

  try {
    if (isTerminationForm.value || isServiceChangeForm.value) {
      // 해지/서비스변경은 동일 번호로 진행 중인 신청서가 있으면 인증 플로우를 중단한다.
      const progressCheck = await post('/api/msf/formTermination/inprogress/get', {
        mobileNo: phoneNo,
      })
      const progressResponse = progressCheck?.data
      if (progressResponse?.resCode !== '0000') {
        failAuthProgress('in-progress application', {
          resCode: progressResponse?.resCode,
          resMessage: progressResponse?.resMessage,
        })
        return
      }
    }

    // 휴대폰 인증 성공만으로는 완료 처리하지 않는다.
    // 다음 가입정보조회까지 통과해야 최종 인증 완료 상태가 된다.
    const authResult = await requestMemberAuth(phoneNo, customerLinkName)
    if (!authResult.ok) {
      failAuthProgress(
        'invalid auth response',
        {
          code: authResult.response?.code,
          message: authResult.message,
          contractNum: authResult.contractNum,
          svcCntrNo: authResult.svcCntrNo,
        },
        authResult.message,
      )
      return
    }

    applyAuthData(authResult)

    // 계약정보를 찾을 수 없거나 가입정보조회가 실패하면 인증 완료로 전환하지 않는다.
    const myinfoResult = await loadSubscriptionInfo(authResult, phoneNo)
    if (!myinfoResult) {
      failAuthProgress('myinfo blocked or empty')
      return
    }

    // 최종 성공 지점. verified/authFlag true 처리는 여기서만 수행한다.
    completeAuthProgress(myinfoResult)
  } catch (error) {
    resetAuthProgress()
    console.error(`${getLogPrefix('휴대폰인증')} 예외 발생`, {
      message: error?.message,
      response: error?.response?.data,
    })
  } finally {
    isSubscriberAuthLoading.value = false
  }
}

const validate = () => {
  console.log(`${getLogPrefix('가입자정보검증')} 시작`, {
    formType: model.value?.formType,
    authStatus: deviceChgAuth.status.value,
    authFlag: resolveAuthFlag(),
  })

  if (!model.value.cstmrNm) {
    console.warn(`${getLogPrefix('가입자정보검증')} 진행 중단`, { reason: 'cstmrNm' })
    return false
  }
  if (isCorporate.value) {
    if (!model.value.cstmrJuridicalRrn1 || !model.value.cstmrJuridicalRrn2) {
      console.warn(`${getLogPrefix('가입자정보검증')} 진행 중단`, {
        reason: 'cstmrJuridicalRrn',
        cstmrJuridicalRrn1: model.value.cstmrJuridicalRrn1,
        cstmrJuridicalRrn2: model.value.cstmrJuridicalRrn2,
      })
      return false
    }
    if (!model.value.cstmrJuridicalRepNm) {
      console.warn(`${getLogPrefix('가입자정보검증')} 진행 중단`, {
        reason: 'cstmrJuridicalRepNm',
      })
      return false
    }
    if (showsBusinessIssuDate.value && !businessIssuDate.value) {
      console.warn(`${getLogPrefix('가입자정보검증')} 진행 중단`, {
        reason: isTerminationForm.value ? 'cstmrJuridicalBizNoIssuDate' : 'identityIssuDate',
      })
      return false
    }
  } else if (!model.value.userBirthDate) {
    console.warn(`${getLogPrefix('가입자정보검증')} 진행 중단`, { reason: 'userBirthDate' })
    return false
  }
  if (showsUserGender.value && !model.value.userGender) {
    console.warn(`${getLogPrefix('가입자정보검증')} 진행 중단`, { reason: 'userGender' })
    return false
  }
  if (hasPersonalBusinessRegistrationNo.value && !model.value.cstmrPrivateBizNoIssuDate) {
    console.warn(`${getLogPrefix('가입자정보검증')} 진행 중단`, {
      reason: 'cstmrPrivateBizNoIssuDate',
    })
    return false
  }
  if (
    !hasExactLength(model.value.deviceChgTel1, 3) ||
    !hasExactLength(model.value.deviceChgTel2, 4) ||
    !hasExactLength(model.value.deviceChgTel3, 4)
  ) {
    console.warn(`${getLogPrefix('가입자정보검증')} 진행 중단`, {
      reason: 'deviceChgTel',
      deviceChgTel1: model.value.deviceChgTel1,
      deviceChgTel2: model.value.deviceChgTel2,
      deviceChgTel3: model.value.deviceChgTel3,
    })
    return false
  }
  if (!resolveAuthFlag()) {
    console.warn(`${getLogPrefix('가입자정보검증')} 진행 중단`, {
      reason: 'deviceChgAuth',
      authStatus: deviceChgAuth.status.value,
      authFlag: resolveAuthFlag(),
    })
    return false
  }

  console.log(`${getLogPrefix('가입자정보검증')} 통과`, {
    formType: model.value?.formType,
    authStatus: deviceChgAuth.status.value,
    authFlag: resolveAuthFlag(),
  })

  return true
}

defineExpose({ validate })
</script>
