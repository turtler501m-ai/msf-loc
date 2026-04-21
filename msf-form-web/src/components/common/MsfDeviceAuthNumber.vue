<template>
  <MsfFormGroup label="<em class='login-label'>이름</em>" vertical>
    <MsfInput variant="underline" v-model="displayUserName" class="ut-w100p" disabled />
  </MsfFormGroup>
  <MsfFormGroup label="<em class='login-label'>전화번호</em>" vertical>
    <MsfStack type="field" class="ut-w100p">
      <MsfInput variant="underline" v-model="displayUserPhone" class="ut-flex-1" disabled />
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
import { useCountdown } from '@vueuse/core'
import { showAlert } from '@/libs/utils/comp.utils'
import { isEmpty, validateMobile } from '@/libs/utils/string.utils'
import { post } from '@/libs/api/msf.api'

const emit = defineEmits(['complete'])

const props = defineProps({
  name: { type: String, required: true },
  phone: { type: String, required: true },
  showDevice: { type: Boolean, default: false },
})

const userName = ref(props.name)
const userPhone = ref(props.phone)
const authNumber = ref('')
const status = ref('none')

const isDisabledSendBtn = computed(() => status.value === 'none')
const isDisabledConfirmBtn = computed(() => authNumber.value?.length !== 6)

const displayUserName = computed(() => {
  if (isEmpty(userName.value)) return ''
  const length = userName.value.length
  return length <= 3
    ? userName.value.replace(/.$/u, '*')
    : userName.value.substring(0, 2) + '*'.repeat(length - 2)
})

const displayUserPhone = computed(() => {
  if (isEmpty(userPhone.value)) return ''

  return userPhone.value.replace(/(?<=^.{6,7}|^.{9,10})\d/g, '*')
})

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

const onClickSendAuthNumber = async () => {
  const result = await post('/api/shared/common/sms/auth/send', {
    userName: userName.value,
    userPhone: userPhone.value,
    type: 'user-form-login',
  })
  if (result?.code !== '0000') {
    showAlert('[인증번호 발송] 버튼을 클릭하시면,\n인증번호가 등록된 휴대폰으로 발송됩니다.')
    return false
  }
  authNumber.value = ''
  status.value = 'sent'
  reset(countTime)
  start()
}

const onClickVerifyAuthNumber = async () => {
  const result = await post('/api/shared/common/sms/auth/verify', {
    userName: userName.value,
    userPhone: userPhone.value,
    authNumber: authNumber.value,
    type: 'user-form-login',
  })
  if (result?.code !== '0000') {
    showAlert(result.message)
    return false
  }
  stop()
  status.value = 'verified'
}

watch(
  () => props.phone,
  (newVal) => {
    if (!newVal) {
      userPhone.value = ''
    } else {
      userPhone.value = newVal
    }
    if (validateMobile(userPhone.value)) {
      status.value = 'ready'
    }
  },
  { immediate: true },
)
watch(
  () => status.value,
  (newVal) => {
    if (newVal === 'verified') {
      emit('complete', newVal === 'verified')
    }
  },
)

onBeforeMount(() => {
  if (validateMobile(props.phone)) {
    status.value = 'ready'
  }
})
onBeforeUpdate(() => {
  if (status.value === 'none' && validateMobile(props.phone)) {
    status.value = 'ready'
  }
})
</script>

<style scoped></style>
