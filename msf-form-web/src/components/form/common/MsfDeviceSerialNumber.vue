<script setup>
import { defineModel, defineProps, ref } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  title: { type: String, default: '휴대폰 정보' },
  customerData: { type: Object, default: () => ({}) },
  disabled: Boolean,
})
const model = defineModel({ type: Object, required: true })
const store = useMsfFormNewChgStore()
const isBarCodeScanModalOpen = ref(false)

const serialNumberRef = ref(null)
const imeiAuthBtnRef = ref(null)

const imeiAuth = useAuthButton(() => [model.value?.serialNumber], {
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
    prodSn: model.value.serialNumber,
    modelId: props.customerData.modelId || props.customerData.deviceModel || '',
    agentCd: props.customerData.agentCd || '',
    uploadPhoneSrlNo: model.value.uploadPhoneSrlNo || '',
  }

  try {
    const res = await post('/api/form/phoneinfo/verify', payload, { timeout: 30000 })
    if (res && res.code === '0000' && res.data?.resCode === '0000') {
      imeiAuth.verify()
    }
  } catch (error) {
    console.error('Verify device serial number error:', error)
  }
}

const validate = () => {
  if (props.customerData?.productType === 'MM') {
    if (!model.value.serialNumber) return false
    if (!store.authFlags?.imei) return false
  }
  return true
}

const handleSerialNumberConfirm = (result) => {
  model.value.serialNumber = result?.serialNumber || ''
}

const reset = () => {
  // 입력값들 '' 처리 및 기본값 복원
  if (model.value) {
    model.value.serialNumber = ''
  }

  imeiAuth.requireReauth()
}

const checkValidation = () => {
  if (props.customerData?.productType !== 'MM') {
    return true
  }

  if (!model.value.serialNumber) {
    showAlert(`휴대폰 일련번호를 입력하세요`, () => {
      serialNumberRef.value?.focus()
    })
    return false
  }

  if (!store.authFlags?.imei) {
    showAlert(`휴대폰 일련번호 유효성 체크를 실행하세요`, () => {
      imeiAuthBtnRef.value?.focus()
    })
    return false
  }

  return true
}

defineExpose({ validate, reset, checkValidation })
</script>

<template>
  <div v-if="customerData?.productType === 'MM'">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="휴대폰 일련번호" required>
        <MsfStack type="field">
          <MsfInput
            ref="serialNumberRef"
            v-model="model.serialNumber"
            placeholder="휴대폰 일련번호 입력"
            maxlength="20"
            class="ut-w-300"
            :readonly="
              props.disabled ||
              imeiAuth.status.value === 'verified' ||
              model.isSaved ||
              (model.simTypeCd === 'ESIM' && !store.authFlags.esimImei)
            "
          />
          <MsfButton
            variant="subtle"
            @click="isBarCodeScanModalOpen = true"
            :disabled="
              props.disabled ||
              imeiAuth.status.value === 'verified' ||
              model.isSaved ||
              (model.simTypeCd === 'ESIM' && !store.authFlags.esimImei)
            "
            >스캔하기</MsfButton
          >
          <MsfButton
            variant="validation"
            v-if="
              imeiAuth.status.value === 'none' ||
              (model.simTypeCd === 'ESIM' && !store.authFlags.esimImei)
            "
            disabled
            >유효성 체크</MsfButton
          >
          <MsfButton
            ref="imeiAuthBtnRef"
            variant="validation"
            v-else-if="imeiAuth.status.value === 'ready'"
            :disabled="props.disabled"
            @click="handleDeviceVerify"
            >유효성 체크</MsfButton
          >
          <MsfButton variant="validation" v-else-if="imeiAuth.status.value === 'verified'" active
            >유효성 체크 완료</MsfButton
          >
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <MsfSerialNumberScanModal
      v-model="isBarCodeScanModalOpen"
      @confirm="handleSerialNumberConfirm"
    />
  </div>
</template>
