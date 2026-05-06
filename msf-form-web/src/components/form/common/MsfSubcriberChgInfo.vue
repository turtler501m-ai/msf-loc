<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput
            id="inp-cstmrNm"
            v-model="model.cstmrNm"
            placeholder="이름"
            class="ut-w-300"
            :readonly="isVerified"
        />
      </MsfFormGroup>

      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfNumberInput
              id="inp-userBirthDate"
              v-model="model.userBirthDate"
              placeholder="생년월일 8자리 (예: 19901231)"
              maxlength="8"
              class="ut-w-300"
              :readonly="isVerified"
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
              :disabled="isVerified"
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
              :readonly="isVerified"
              @maxlength="moveFocus(deviceChgTel2Ref)"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
              id="inp-deviceChgTel2"
              ref="deviceChgTel2Ref"
              v-model="model.deviceChgTel2"
              placeholder="가운데 4자리"
              maxlength="4"
              :readonly="isVerified"
              @maxlength="moveFocus(deviceChgTel3Ref)"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
              id="inp-deviceChgTel3"
              ref="deviceChgTel3Ref"
              v-model="model.deviceChgTel3"
              placeholder="뒤 4자리"
              maxlength="4"
              :readonly="isVerified"
          />
          <MsfButton
              id="btn-deviceChgTelAuth"
              variant="toggle"
              v-if="deviceChgAuth.status.value === 'none'"
              disabled
          >
            인증
          </MsfButton>
          <MsfButton
              id="btn-deviceChgTelAuth"
              variant="toggle"
              v-else-if="deviceChgAuth.status.value === 'ready'"
              @click="handleDeviceChgVerify"
          >
            인증
          </MsfButton>
          <MsfButton
              id="btn-deviceChgTelAuth"
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
import { computed, nextTick, ref } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'

defineProps({
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

const isTerminationForm = computed(() => model.value?.formType === 'TERMINATION')
const isServiceChangeForm = computed(() => model.value?.formType === 'SERVICECHANGE')

const getLogPrefix = (task) => `${isTerminationForm.value ? '[서비스해지]' : '[서비스변경]'}[${task}]`

const moveFocus = (targetRef) => {
  if (targetRef?.value?.focus) {
    nextTick(() => {
      targetRef.value.focus()
    })
  }
}

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
      model.value?.userBirthDate,
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
    const res = await post('/api/form/ktmmember/auth', {
      subscriberNo: phoneNo,
      customerLinkName,
    })
    console.log(`${getLogPrefix('휴대폰인증')} 응답 수신`, {
      code: res?.code,
      message: res?.message,
      data: res?.data,
    })

    const contractNum = res?.data?.contractNum || res?.data?.contract_num || res?.data?.ncn
    const lstComActvDate =
        res?.data?.lstComActvDate || res?.data?.lst_com_actv_date || res?.data?.initActivationDate || ''

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
    model.value.lstComActvDate = lstComActvDate

    let myinfoResult = null
    if (isTerminationForm.value) {
      console.log(`${getLogPrefix('휴대폰인증')} 계약정보 셋팅`, { contractNum })
      terminationStore.setTerminationContract(contractNum, 'MsfSubcriberChgInfo')
      model.value.ncn = contractNum
      console.log(`${getLogPrefix('휴대폰인증')} 가입정보조회 호출`, {
        ncn: model.value.ncn,
        ctn: phoneNo,
      })
      myinfoResult = await terminationStore.apiGetMyinfoView()
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

    showAlert('휴대폰번호 인증이 완료되었습니다.')
    deviceChgAuth.status.value = 'verified'
    updateAuthFlag(true)
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
  if (!model.value.userBirthDate) {
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
