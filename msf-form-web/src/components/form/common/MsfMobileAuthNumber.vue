<template>
  <MsfFormGroup :label="props.label" required>
    <MsfStack type="field">
      <MsfMobileInput
        ref="mobileRef"
        v-model:number1="phone.phone1"
        v-model:number2="phone.phone2"
        v-model:number3="phone.phone3"
        :readonly="isStatusVerified"
        :label="props.label"
        @verify="onVerifyPhoneInput"
      />
      <MsfButton
        variant="toggle"
        v-if="status === 'none' || status === 'ready'"
        :disabled="isDisabledSendBtn"
        @click="onClickSendAuthNumber()"
        >인증번호 발송</MsfButton
      >
      <MsfButton variant="toggle" v-else-if="status === 'sent'" @click="onClickSendAuthNumber()">
        인증번호 재발송
      </MsfButton>
      <MsfButton variant="toggle" v-else-if="status === 'verified'" active>인증 완료</MsfButton>
    </MsfStack>
    <MsfStack type="field" class="mt-2" v-if="status === 'sent'">
      <MsfNumberInput
        ref="authNumberRef"
        v-model="authNumber"
        id="inp-repPhoneAuth"
        maxlength="6"
        placeholder="인증번호 입력"
      />
      <span class="remain-time"
        >남은시간 <em>{{ countdownFormat }}</em></span
      >
      <MsfButton
        variant="toggle"
        :disabled="isDisabledConfirmBtn"
        @click="onClickVerifyAuthNumber()"
        >인증번호 확인</MsfButton
      >
    </MsfStack>
  </MsfFormGroup>
</template>

<script setup>
import { computed, ref, shallowRef, watch, onBeforeMount, onBeforeUpdate, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useCountdown } from '@vueuse/core'
import { showAlert } from '@/libs/utils/comp.utils'
import { post } from '@/libs/api/msf.api'
import { isEmpty, validateMobile } from '@/libs/utils/string.utils'

const route = useRoute()

const nameModel = defineModel('name', { type: String, default: '' })
const phone1Model = defineModel('phone1', { type: String, default: '010' })
const phone2Model = defineModel('phone2', { type: String, default: '' })
const phone3Model = defineModel('phone3', { type: String, default: '' })

const props = defineProps({
  formType: {
    type: String,
    required: true,
    // validator: (v) => ['F-1-VDP', 'F-2-VDP', 'F-3-VDP', 'F-4-VDP'].includes(v),
  },
  label: { type: String, default: '연락처(휴대폰)' },
  beforeSend: { type: Function, default: null },
})

const emit = defineEmits(['complete'])

const mobileRef = ref(null)
const authNumberRef = ref(null)

const loginKey = ref(props.loginKey)
const phone = ref({
  phone1: phone1Model.value || '010',
  phone2: phone2Model.value,
  phone3: phone3Model.value,
})
const authNumber = ref('')
const status = ref('none')
const valid = ref(false)
const sendedKey = ref('')

onMounted(() => {
  // 초기 로드 시 (임시저장 데이터 등) 번호가 이미 있다면 유효성 체크하여 버튼 활성화
  const fullPhone = `${phone.value.phone1}-${phone.value.phone2}-${phone.value.phone3}`
  if (validateMobile(fullPhone)) {
    valid.value = true
    status.value = 'ready'
  }
})

const isDisabledSendBtn = computed(() => status.value === 'none')
const isDisabledConfirmBtn = computed(() => authNumber.value?.length !== 6)
const isStatusVerified = computed(() => status.value === 'verified')

const countdownFormat = computed(
  () =>
    `${String(Math.floor(remaining.value / 60)).padStart(2, '0')}:${String(remaining.value % 60).padStart(2, '0')}`,
)

const countTime = 180
const countdown = shallowRef(countTime)
const { remaining, start, stop, reset } = useCountdown(countdown, {
  onComplete() {
    showAlert(
      '인증번호 유효시간이 종료되었습니다.\n[인증번호 재발송] 버튼을 클릭하시면,\n인증번호가 재발송 됩니다.',
    )
    status.value = 'sent'
  },
})

const validateSendingMobile = () => {
  if (isEmpty(phone.value.phone1) || isEmpty(phone.value.phone2) || isEmpty(phone.value.phone3)) {
    showAlert('휴대폰 번호를 입력해주세요.')
    return false
  }
  if (isEmpty(nameModel.value)) {
    showAlert('이름을 입력해주세요.')
    return false
  }
  return true
}
const validateVerifyMobile = () => {
  if (authNumber.value.length !== 6) {
    showAlert('인증번호는 6자리 숫자로 입력해 주세요.')
    return false
  }
  return true
}

const onClickSendAuthNumber = async () => {
  if (props.beforeSend && !(await props.beforeSend())) {
    return false
  }

  if (!validateSendingMobile()) {
    return false
  }
  const result = await post(
    '/api/shared/common/auth/sms/otp/send',
    {
      name: nameModel.value,
      phone: phone.value.phone1 + phone.value.phone2 + phone.value.phone3,
      type: props.formType,
      path: route.path,
    },
    { skipAlert: true },
  )
  if (result?.code !== '0000') {
    showAlert('[인증번호 발송] 버튼을 클릭하시면,\n인증번호가 등록된 휴대폰으로 발송됩니다.')
    return false
  }
  showAlert('인증번호가 발송되었습니다.')
  sendedKey.value = result.data.sendedKey
  authNumber.value = ''
  // authNumber.value = ['loc', 'dev'].includes(import.meta.env.MODE)
  //   ? result.data.authNumber || ''
  //   : ''
  // if (import.meta.env.MODE !== 'prd') {
  //   console.log('authNumber:', result.data.authNumber)
  // }
  // FIXME: 실제 운영하기 위해서 제거 필요
  authNumber.value = result.data.authNumber || ''

  status.value = 'sent'
  reset(countTime)
  start()
}

const onClickVerifyAuthNumber = async () => {
  if (!validateVerifyMobile()) {
    return false
  }
  const result = await post('/api/shared/common/auth/sms/otp/verify', {
    value: authNumber.value,
    type: props.formType,
    path: route.path,
    token: sendedKey.value,
  })
  if (result?.code !== '0000') {
    showAlert(result?.message)
    return false
  }
  if (!result?.data) {
    showAlert('인증번호가 일치하지 않습니다.')
    return false
  }
  stop()
  status.value = result.data ? 'verified' : status.value
  emit('complete', result.data)
}

const onVerifyPhoneInput = (result) => {
  valid.value = result
  if (!valid.value) {
    status.value = 'none'
  } else {
    status.value = 'ready'
  }
}

watch(
  () => props.loginKey,
  (newVal) => {
    loginKey.value = newVal
  },
  { immediate: true },
)
watch(
  () => phone1Model.value,
  (newVal) => {
    if (!newVal) {
      phone.value.phone1 = '010'
      phone1Model.value = phone.value.phone1
    } else {
      phone.value.phone1 = newVal
    }
  },
  { immediate: true },
)
watch(
  () => phone2Model.value,
  (newVal) => {
    phone.value.phone2 = newVal
  },
  { immediate: true },
)
watch(
  () => phone3Model.value,
  (newVal) => {
    phone.value.phone3 = newVal
  },
  { immediate: true },
)
watch(
  () => phone.value,
  (newVal) => {
    phone1Model.value = newVal.phone1
    phone2Model.value = newVal.phone2
    phone3Model.value = newVal.phone3
  },
  {
    immediate: true,
    deep: true,
  },
)
onBeforeMount(() => {
  if (!isEmpty(props.phone)) {
    status.value = 'ready'
  }
})
onBeforeUpdate(() => {
  if (status.value === 'none' && !isEmpty(props.phone)) {
    status.value = 'ready'
  }
})

defineExpose({
  focus: () => {
    if (status.value === 'sent' && !authNumber.value) authNumberRef.value?.focus()
    else mobileRef.value?.focus()
  },
})
</script>

<style scoped></style>
