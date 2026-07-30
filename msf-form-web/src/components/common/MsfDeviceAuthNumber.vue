<template>
  <MsfFormGroup label="<em class='login-label'>이름</em>" vertical>
    <MsfInput variant="underline" v-model="userName" class="ut-w100p" disabled />
  </MsfFormGroup>
  <MsfFormGroup label="<em class='login-label'>전화번호</em>" vertical>
    <MsfStack type="field" class="ut-w100p">
      <MsfInput variant="underline" v-model="userPhone" class="ut-flex-1" disabled />
      <MsfButton variant="toggle" :disabled="isDisabledSendBtn" @click="onClickSendAuthNumber()">{{
        status === 'sent' ? '인증번호 재발송' : '인증번호 발송'
      }}</MsfButton>
    </MsfStack>
    <MsfStack type="field" v-if="statusSent">
      <MsfNumberInput
        v-model="authNumber"
        id="inp-repPhoneAuth"
        placeholder="인증번호 입력"
        ariaLabel="인증번호 입력"
        maxlength="6"
      />
      <span class="remain-time"
        >남은시간 <em>{{ countdownFormat }}</em></span
      >
    </MsfStack>
  </MsfFormGroup>
  <MsfButton
    v-if="statusSent"
    variant="primary"
    block
    :disabled="isDisabledConfirmBtn"
    @click="onClickVerifyAuthNumber()"
    >{{ props.showDevice ? '단말 사용 인증' : '로그인' }}</MsfButton
  >
</template>

<script setup>
import { computed, onBeforeMount, onBeforeUpdate, ref, shallowRef, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useCountdown } from '@vueuse/core'
import { showAlert } from '@/libs/utils/comp.utils'
import { isEmpty, formatTelephone } from '@/libs/utils/string.utils'
import { post } from '@/libs/api/msf.api'
import { isProduction } from '@/libs/utils/env.utils'

const route = useRoute()
const router = useRouter()

const props = defineProps({
  loginKey: { type: String, required: true },
  name: { type: String, required: true },
  phone: { type: String, required: true },
  showDevice: { type: Boolean, default: false },
})

const emit = defineEmits(['complete'])

const loginKey = ref(props.loginKey)
const userName = ref(props.name)
const userPhone = ref(formatTelephone(props.phone))
const authNumber = ref('')
const status = ref('none')
const sendedKey = ref('')

const isDisabledSendBtn = computed(() => status.value === 'none')
const isDisabledConfirmBtn = computed(() => authNumber.value?.length !== 6)

const countdownFormat = computed(
  () =>
    `${String(Math.floor(remaining.value / 60)).padStart(2, '0')}:${String(remaining.value % 60).padStart(2, '0')}`,
)
const statusSent = computed(() => status.value === 'sent')

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

const validateVerifyMobile = () => {
  if (authNumber.value.length !== 6) {
    showAlert('인증번호는 6자리 숫자로 입력해 주세요.')
    return false
  }
  return true
}

const onClickSendAuthNumber = async () => {
  const result = await post(
    '/api/n/auth/sms/otp/send',
    {
      type: 'F-0-OTP',
      path: route.path,
      token: loginKey.value,
    },
    { skipAlert: true },
  )
  if (result?.code === '6000') {
    showAlert(result.message, () => {
      router.push('/login')
    })
    return
  } else if (result?.code !== '0000') {
    showAlert('[인증번호 발송] 버튼을 클릭하시면,\n인증번호가 등록된 휴대폰으로 발송됩니다.')
    return false
  } else {
    showAlert('인증번호가 발송되었습니다.')
  }

  sendedKey.value = result.data.sendedKey

  if (isProduction() && false) {  // FIXME: 통합테스트 기간에 임시로 인증번호 노출
    authNumber.value = '';
  } else {
    authNumber.value = result.data.authNumber || '';
    console.log(`authNumber: ${result.data.authNumber}`);
  }
  status.value = 'sent'
  reset(countTime)
  start()
}

const onClickVerifyAuthNumber = async () => {
  if (!validateVerifyMobile()) {
    return false
  }

  const result = await post('/api/n/auth/sms/otp/verify', {
    type: 'F-0-OTP',
    path: route.path,
    value: authNumber.value,
    token: sendedKey.value,
  })
  if (result?.code !== '0000') {
    showAlert(result.message)
    return false
  }
  stop()
  status.value = result.data ? 'verified' : status.value
  emit('complete', result.data)
}

watch(
  () => props.loginKey,
  (newVal) => {
    loginKey.value = newVal
  },
  { immediate: true },
)
watch(
  () => props.name,
  (newVal) => {
    if (!newVal) {
      userName.value = ''
    } else {
      userName.value = newVal
    }
  },
  { immediate: true },
)
watch(
  () => props.phone,
  (newVal) => {
    if (!newVal) {
      userPhone.value = ''
    } else {
      userPhone.value = formatTelephone(newVal)
    }
    if (!isEmpty(userPhone.value)) {
      status.value = 'ready'
    }
  },
  { immediate: true },
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
