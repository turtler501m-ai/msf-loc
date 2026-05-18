<template>
  <div>
    <MsfTitleArea :title="computedTitle" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput
            id="inp-cstmrNm"
            v-model="model.cstmrNm"
            placeholder="이름"
            class="ut-w-300"
            :readonly="isReadonly"
        />
      </MsfFormGroup>

      <MsfFormGroup v-if="!isCorporate" label="생년월일" required>
        <MsfStack type="field">
          <MsfNumberInput
              id="inp-userBirthDate"
              v-model="model.userBirthDate"
              placeholder="생년월일 8자리 (예: 19901231)"
              maxlength="8"
              class="ut-w-300"
              :readonly="isReadonly"
          />
          <MsfRadioGroup
              v-if="isServiceChangeForm"
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

        <MsfFormGroup label="대표자명" required>
        <MsfInput
          id="inp-cstmrJuridicalRepNm"
          v-model="model.cstmrJuridicalRepNm"
          placeholder="대표자명"
          class="ut-w-300"
          maxlength="60"
          readonly
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
              @click="handleDeviceChgVerify"
          >
            인증
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
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'

// 서비스변경/서비스해지 전용 가입자 정보 컴포넌트.
// 다른 가입/명의변경 화면의 가입자 정보는 MsfSubscriberInfo.vue를 사용한다.
const props = defineProps({
  title: { type: String, default: '가입자 정보' },
  phoneLabel: { type: String, default: '휴대폰번호' },
  name: { type: String, default: 'base' },
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
const isCorporate = computed(() => ['JP', 'GO'].includes(model.value?.cstmrTypeCd))
const showsBusinessRegistrationNo = computed(
  () =>
    isCorporate.value ||
    (isServiceChangeForm.value && ['NA', 'FN'].includes(model.value?.cstmrTypeCd)),
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
  console.log(`${getLogPrefix('가입자인증상태')} 셋팅 시작`, {
    formType: model.value?.formType,
    value,
    terminationFlag: terminationStore.authFlags?.cancelPhone,
    serviceChangeFlag: serviceChangeStore.authFlags?.deviceChgTel,
  })

  if (isTerminationForm.value) {
    if (terminationStore.authFlags) terminationStore.authFlags.cancelPhone = value
    console.log(`${getLogPrefix('가입자인증상태')} 셋팅 결과`, {
      target: 'termination.cancelPhone',
      value: terminationStore.authFlags?.cancelPhone,
    })
    return
  }

  if (serviceChangeStore.authFlags) serviceChangeStore.authFlags.deviceChgTel = value
  console.log(`${getLogPrefix('가입자인증상태')} 셋팅 결과`, {
    target: 'serviceChange.deviceChgTel',
    value: serviceChangeStore.authFlags?.deviceChgTel,
  })
}

const deviceChgAuth = useAuthButton(
    () => [
      model.value?.cstmrNm,
      ...(isCorporate.value
        ? [model.value?.cstmrJuridicalRrn1, model.value?.cstmrJuridicalRrn2]
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
)

const isVerified = computed(() => deviceChgAuth.status.value === 'verified')
const isReadonly = computed(() => model.value?.isSaved || isVerified.value)


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

const handleDeviceChgVerify = async () => {
  const phoneNo = `${model.value.deviceChgTel1}${model.value.deviceChgTel2}${model.value.deviceChgTel3}`
  const customerLinkName = (model.value.cstmrNm || '').trim()

  console.log(`${getLogPrefix('휴대폰인증')} 요청 시작`, {
    formType: model.value.formType,
    cstmrNm: model.value.cstmrNm,
    customerLinkName,
    phoneNo,
    authStatus: deviceChgAuth.status.value,
    authFlag: resolveAuthFlag(),
  })

  try {
    const authPayload = {
      subscriberNo: phoneNo,
      customerLinkName,
    }
    const res = await post('/api/form/ktmmember/auth', authPayload, { silentSuccess: true })
    console.log(`${getLogPrefix('휴대폰인증')} 응답 수신`, {
      code: res?.code,
      message: res?.message,
      data: res?.data,
    })

    const authData = res?.data?.resData || res?.data || {}
    const contractNum = authData.contractNum || authData.contract_num || authData.ncn
    const lstComActvDate =
        authData.lstComActvDate || authData.lst_com_actv_date || authData.initActivationDate || ''

    if (res?.code !== '0000' || !contractNum) {
      console.warn(`${getLogPrefix('휴대폰인증')} 진행 중단`, {
        reason: 'invalid auth response',
        code: res?.code,
        message: res?.message,
        contractNum,
      })
      return
    }

    model.value.contractNum = contractNum
    model.value.ncn = contractNum
    model.value.custId = authData.customerId || authData.customer_id || model.value.custId || ''
    model.value.lstComActvDate = lstComActvDate
    if (authData.customerLinkName) model.value.cstmrNm = authData.customerLinkName
    if (isCorporate.value) {
      const representativeName =
        authData.representativeName ||
        authData.repName ||
        authData.cstmrJuridicalRepNm ||
        authData.customerLinkName ||
        ''
      if (representativeName) model.value.cstmrJuridicalRepNm = representativeName
    }
    if (authData.gender) model.value.userGender = authData.gender

    let myinfoResult = null
    if (isTerminationForm.value) {
      console.log(`${getLogPrefix('휴대폰인증')} 계약정보 셋팅`, { contractNum })
      terminationStore.setTerminationContract(contractNum, 'MsfSubscriberChgInfo')
      model.value.ncn = contractNum
      console.log(`${getLogPrefix('휴대폰인증')} 가입정보조회 호출`, {
        ncn: model.value.ncn,
        ctn: phoneNo,
      })
      myinfoResult = await terminationStore.apiGetMyinfoView()
      if (!myinfoResult) {
        deviceChgAuth.status.value = 'ready'
        updateAuthFlag(false)
        console.warn(`${getLogPrefix('휴대폰인증')} 인증완료 처리 중단`, {
          reason: 'myinfo blocked or empty',
          authStatus: deviceChgAuth.status.value,
          authFlag: resolveAuthFlag(),
        })
        return
      }
    } else if (isServiceChangeForm.value) {
      model.value.ncn = contractNum
      console.log(`${getLogPrefix('휴대폰인증')} 계약정보 셋팅`, {
        contractNum: model.value.contractNum,
        ncn: model.value.ncn,
        lstComActvDate: model.value.lstComActvDate,
      })
      console.log(`${getLogPrefix('휴대폰인증')} 가입정보조회 호출`, {
        ncn: model.value.ncn,
        ctn: phoneNo,
      })
      myinfoResult = await serviceChangeStore.apiGetMyinfoView()
    }

    deviceChgAuth.status.value = 'verified'
    updateAuthFlag(true)
    model.value.isVerified = true
    console.log(`${getLogPrefix('휴대폰인증')} 화면 데이터 반영 결과`, {
      contractNum: model.value.contractNum,
      ncn: model.value.ncn,
      custId: model.value.custId,
      lstComActvDate: model.value.lstComActvDate,
      prvRateGrpNm: model.value.prvRateGrpNm,
      zipNo: model.value.zipNo,
      address: model.value.address,
      detailAddress: model.value.detailAddress,
      authStatus: deviceChgAuth.status.value,
      authFlag: resolveAuthFlag(),
      myinfoLoaded: !!myinfoResult,
    })
  } catch (error) {
    console.error(`${getLogPrefix('휴대폰인증')} 예외 발생`, {
      message: error?.message,
      response: error?.response?.data,
    })
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
  } else if (!model.value.userBirthDate) {
    console.warn(`${getLogPrefix('가입자정보검증')} 진행 중단`, { reason: 'userBirthDate' })
    return false
  }
  if (isServiceChangeForm.value && !model.value.userGender) {
    console.warn(`${getLogPrefix('가입자정보검증')} 진행 중단`, { reason: 'userGender' })
    return false
  }
  if (!model.value.deviceChgTel1 || !model.value.deviceChgTel2 || !model.value.deviceChgTel3) {
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
