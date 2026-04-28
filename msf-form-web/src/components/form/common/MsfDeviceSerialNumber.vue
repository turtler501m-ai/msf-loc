<script setup>
import { defineModel, defineProps } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { post } from '@/libs/api/msf.api'

const props = defineProps({
  title: { type: String, default: '단말기 일련번호' },
})
const model = defineModel('modelValue', { type: Object, required: true })
const customerModel = defineModel('customerData', { type: Object, required: true })
const store = useMsfFormNewChgStore()

const imeiAuth = useAuthButton(() => [model.value?.imei], {
  get value() {
    return store.authFlags?.imei || false
  },
  set value(v) {
    if (store.authFlags) {
      store.authFlags.imei = v
    }
  },
})

const handleDeviceVerify = async () => {
  const payload = {
    orgnId: 'V000001105',
    prodSn: model.value.imei,
    prodId: '4993', // 실제 구현 시 상품 정보에서 가져와야 할 수 있음
  }

  try {
    const res = await post('/api/form/verifyPhoneSerialNumberInfo', payload)
    if (res && res.code === '0000') {
      imeiAuth.verify()
      alert('휴대폰 일련번호 유효성 체크가 완료되었습니다.')
    } else {
      alert(res.message || '휴대폰 일련번호 유효성 체크에 실패했습니다.')
    }
  } catch (error) {
    console.error('Verify device serial number error:', error)
    alert('휴대폰 일련번호 유효성 체크 중 오류가 발생했습니다.')
  }
}

const validate = () => {
  if (customerModel.value.product === 'MM') {
    if (!model.value.imei) return false
    if (!store.authFlags?.imei) return false
  }
  return true
}

defineExpose({ validate })
</script>

<template>
  <div v-if="customerModel.product === 'MM'">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="휴대폰 일련번호" required>
        <MsfStack type="field">
          <MsfInput v-model="model.imei" placeholder="휴대폰 일련번호 입력" class="ut-w-300" />
          <MsfButton variant="subtle">스캔하기</MsfButton>
          <MsfButton variant="toggle" v-if="imeiAuth.status.value === 'none'" disabled
            >일련번호 유효성 체크</MsfButton
          >
          <MsfButton
            variant="toggle"
            v-else-if="imeiAuth.status.value === 'ready'"
            @click="handleDeviceVerify"
            >일련번호 유효성 체크</MsfButton
          >
          <MsfButton variant="toggle" v-else-if="imeiAuth.status.value === 'verified'" active
            >일련번호 유효성 체크 완료</MsfButton
          >
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
