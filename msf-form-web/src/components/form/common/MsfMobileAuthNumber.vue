<template>
  <MsfFormGroup :label="props.label" required>
    <MsfStack type="field">
      <MsfMobileInput
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
})

const emit = defineEmits(['complete'])

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

const onClickSendAuthNumber = async () => {
  const result = await post('/api/shared/common/sms/otp/send', {
    name: import.meta.env.MODE === 'loc' ? nameModel.value || '홍길동' : nameModel.value,
    phone: phone.value.phone1 + phone.value.phone2 + phone.value.phone3,
    type: props.formType,
    path: route.path,
  })
  if (result?.code !== '0000') {
    showAlert('[인증번호 발송] 버튼을 클릭하시면,\n인증번호가 등록된 휴대폰으로 발송됩니다.')
    return false
  }
  showAlert('인증번호가 발송되었습니다.')
  sendedKey.value = result.data.sendedKey
  authNumber.value = result.data.authNumber || ''
  status.value = 'sent'
  reset(countTime)
  start()
}

const onClickVerifyAuthNumber = async () => {
  const result = await post('/api/shared/common/sms/otp/verify', {
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
</script>

<style scoped></style>
