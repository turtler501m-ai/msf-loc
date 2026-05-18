<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="휴대폰 정보 스캔(eSIM)"
    @open="onOpen"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <div class="ocr-container">
      <iframe
          ref="ocrFrame"
          class="ocr-frame"
          src="/koi-ocr/esim/index.html"
          allow="camera *; display-capture *"
      />
    </div>

    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm" :disabled="!docFile">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { onBeforeUnmount, onMounted, ref } from 'vue'
import { post } from '@/libs/api/msf.api.js'

const props = defineProps({
  modelValue: Boolean,
  readonly: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const docFile = ref(null)

const onOpen = () => {
  emit('open')
}

const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const handleMessage = (event) => {
  console.log('message 수신:', event)

  const data = event.data

  if (!data) return

  // 문자열로 오는 경우 대응
  const message = typeof data === 'string'
      ? JSON.parse(data)
      : data

  if (message.type !== 'ESIM_SCAN_RESULT') return

  docFile.value = {
    eid: message.payload?.eid || '',
    imei1: message.payload?.imei1 || '',
    imei2: message.payload?.imei2 || '',
  }

  console.log('docFile 세팅 완료:', docFile.value)
}

// 촬영 버튼 클릭 시 eSIM 스캔 API 호출 (임시 목업 데이터 세팅)
const openCamera = async () => {
  // 실제 환경에서는 카메라 호출 및 OCR API 연동
  // const res = await post('/api/shared/common/esim/scan')
  // if (res && res.data) {
  //   docFile.value = res.data
  // }

  // 테스트를 위한 임의의 값 생성
  const generateRandomDigits = (len) => {
    let res = ''
    for (let i = 0; i < len; i++) {
      res += Math.floor(Math.random() * 10)
    }
    return res
  }

  const mockData = {
    imei1: '35' + generateRandomDigits(13),
    imei2: '35' + generateRandomDigits(13),
    eid: '8904' + generateRandomDigits(28),
    modelNm: 'Mock-iPhone-15',
    serialNo: generateRandomDigits(10),
  }

  console.log('[eSIM 스캔 목업 데이터]:', mockData)
  docFile.value = mockData
}

const onConfirm = () => {
  emit('confirm', docFile.value)
  onClose()
}

onMounted(() => {
  window.addEventListener('message', handleMessage)
})

onBeforeUnmount(() => {
  window.removeEventListener('message', handleMessage)
})
</script>

<style lang="scss" scoped></style>
