<template>
  <div v-if="customerModel.product === 'MM'">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="휴대폰 일련번호" required>
        <MsfStack type="field">
          <MsfInput v-model="model.imei" placeholder="휴대폰 일련번호 입력" class="ut-w-300" />
          <MsfButton variant="subtle">스캔하기</MsfButton>
          <MsfButton variant="toggle" v-if="imeiAuth.status.value === 'none'" disabled>일련번호 유효성 체크</MsfButton>
          <MsfButton variant="toggle" v-else-if="imeiAuth.status.value === 'ready'" @click="imeiAuth.verify()">일련번호 유효성 체크</MsfButton>
          <MsfButton variant="toggle" v-else-if="imeiAuth.status.value === 'verified'" active>일련번호 유효성 체크 완료</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'

const props = defineProps({
  title: { type: String, default: '단말기 일련번호' }
})
const model = defineModel('modelValue', { type: Object, required: true })
const customerModel = defineModel('customerData', { type: Object, required: true })
const store = useMsfFormNewChgStore()

const imeiAuth = useAuthButton(
  () => [model.value?.imei],
  {
    get value() { return store.authFlags?.imei || false },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.imei = v
      }
    }
  }
)
</script>
