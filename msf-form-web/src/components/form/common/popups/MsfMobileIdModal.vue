<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="모바일 신분증 인증"
    size="medium"
    @open="onOpen"
    @close="onClose"
  >
    <div class="mobile-id-container">
      <div class="mobile-id-message">
        <p v-if="isLoading">QR 생성 중입니다.</p>
        <p v-else-if="isError">QR 생성에 실패했습니다.</p>
        <p v-else-if="isVerificationSuccess" class="mobile-id-success-message">
          모바일 신분증 인증이 완료되었습니다.<br />
          확인 버튼을 눌러주세요.
        </p>
        <p v-else-if="isQrExpired" class="mobile-id-processing-message">
          모바일 신분증 인증 결과를 확인하고 있습니다.
        </p>
        <template v-else>
          <p class="mobile-id-title">모바일 신분증 앱으로 QR을 스캔해주세요.</p>
        </template>
      </div>
      <div class="mobile-id-qr-wrap">
        <div v-if="isVerificationSuccess" class="mobile-id-success">
          <p class="mobile-id-success-title">인증 완료</p>
          <p class="mobile-id-success-description">하단의 확인 버튼을 눌러 인증을 완료해주세요.</p>
        </div>
        <!-- 기존 isQrExpired 영역만 아래 코드로 교체 -->
        <div v-else-if="isQrExpired" class="mobile-id-expired">
          <p class="mobile-id-expired-title">인증을 다시 시도하시겠습니까?</p>

          <div class="mobile-id-expired-guide">
            <p>QR코드 유효시간이 지났습니다.</p>
            <p>재시도해 주세요.</p>
          </div>
          <MsfButton
            class="mobile-id-retry-button"
            iconOnly="newWrite"
            variant="secondary"
            :disabled="isLoading"
            @click="onRegenerateQr"
          >
            <span class="mobile-id-retry-icon">↻</span>
          </MsfButton>
          <p class="mobile-id-retry-label">재시도</p>
        </div>
        <canvas v-else ref="qrCanvas"></canvas>
      </div>
      <div v-if="qrData && !isQrExpired && !isVerificationSuccess" class="mobile-time-count">
        <p class="mobile-id-timer" :class="{ danger: remainSeconds <= 10 }">
          남은 시간 {{ remainSeconds }}초
        </p>
      </div>
    </div>
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" :disabled="isLoading" @click="onClose"> 취소 </MsfButton>
        <MsfButton
          variant="primary"
          :disabled="isLoading || (!trxCode && !isVerificationSuccess)"
          @click="onConfirm"
        >
          {{ isVerificationSuccess ? '인증 확인' : '인증 요청' }}
        </MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { nextTick, onBeforeUnmount, ref } from 'vue'
import { post } from '@/libs/api/msf.api.js'
import { toCanvas } from 'qrcode/lib/browser'
import { showConfirm } from '@/libs/utils/comp.utils.js'
import { MsfButton, MsfDialog } from '@/libs/ui/index.js'

const QR_VALID_SECONDS = 60

const INITIAL_POLLING_DELAY = 30_000
const POLLING_INTERVAL = 10_000
const POLLING_TIMEOUT = 3000_000

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const qrCanvas = ref(null)
const qrData = ref('')
const trxCode = ref('')

const isLoading = ref(false)
const isError = ref(false)
const isQrExpired = ref(false)

const isVerificationSuccess = ref(false)
const verificationResult = ref(null)

const remainSeconds = ref(QR_VALID_SECONDS)
const timerId = ref(null)

const pollingTimerId = ref(null)
const pollingStartTime = ref(0)
const isPolling = ref(false)
const isPollingRequest = ref(false)

const isClosing = ref(false)
const isDialogActive = ref(false)

const clearCanvas = () => {
  const canvas = qrCanvas.value
  if (!canvas) return

  const context = canvas.getContext('2d')
  if (!context) return

  context.clearRect(0, 0, canvas.width, canvas.height)
}

const stopTimer = () => {
  if (!timerId.value) return

  clearInterval(timerId.value)
  timerId.value = null
}

const stopPolling = () => {
  isPolling.value = false
  pollingStartTime.value = 0

  if (pollingTimerId.value) {
    clearTimeout(pollingTimerId.value)
    pollingTimerId.value = null
  }
}

const resetVerificationResult = () => {
  isVerificationSuccess.value = false
  verificationResult.value = null
}

const resetState = () => {
  qrData.value = ''
  trxCode.value = ''

  isLoading.value = false
  isError.value = false
  isQrExpired.value = false
  isPollingRequest.value = false

  remainSeconds.value = QR_VALID_SECONDS

  resetVerificationResult()
  clearCanvas()
}

const schedulePolling = (delay = POLLING_INTERVAL) => {
  if (!isDialogActive.value || isClosing.value || !isPolling.value || !trxCode.value) {
    return
  }

  if (pollingTimerId.value) {
    clearTimeout(pollingTimerId.value)
  }

  pollingTimerId.value = setTimeout(requestVerificationResult, delay)
}

const handlePollingTimeout = () => {
  if (!isDialogActive.value || isClosing.value) {
    return
  }
  stopPolling()

  showConfirm('모바일 신분증 인증 시간이 초과되었습니다.', requestQr, 'QR을 다시 생성하시겠습니까?', closeDialog)
}

const handleVerificationSuccess = (data) => {
  if (!isDialogActive.value || isClosing.value) return

  stopTimer()
  stopPolling()

  verificationResult.value = {
    customerNm: data.customerNm,
    customerBirth: data.customerBirth,
    customerRrn: data.customerRrn,
  }

  isVerificationSuccess.value = true
  isQrExpired.value = false
  qrData.value = ''

  clearCanvas()
}

const handleVerificationFail = () => {
  if (!isDialogActive.value || isClosing.value) return

  stopPolling()

  showConfirm('모바일 신분증 인증에 실패했습니다.', requestQr, 'QR을 다시 생성하시겠습니까?', closeDialog)
}

const requestVerificationResult = async () => {
  if (
    !isDialogActive.value ||
    isClosing.value ||
    !isPolling.value ||
    !trxCode.value ||
    isPollingRequest.value ||
    isVerificationSuccess.value
  ) return

  const requestTrxCode = trxCode.value
  const elapsedTime = Date.now() - pollingStartTime.value

  if (elapsedTime >= POLLING_TIMEOUT) {
    handlePollingTimeout()
    return
  }

  isPollingRequest.value = true

  try {
    const res = await post(
      '/api/koi-idcard/verification/result/get',
      {
        trxcode: requestTrxCode,
      },
      {
        skipAlert: true,
        skipLoading: true,
      },
    )

    /*
     * API 호출 도중 팝업이 닫혔거나,
     * QR이 재생성되어 trxCode가 변경된 경우 기존 응답은 무시한다.
     */
    if (
      !isDialogActive.value ||
      isClosing.value ||
      !isPolling.value ||
      trxCode.value !== requestTrxCode
    ) {
      return
    }

    const data = res?.data ?? res
    const status = data?.status?.toUpperCase()

    if (status === 'SUCCESS') {
      handleVerificationSuccess(data)
      return
    }

    if (status === 'FAIL') {
      handleVerificationFail()
      return
    }

    schedulePolling()
  } catch (e) {
    console.error('모바일 신분증 인증 결과 조회 실패', e)

    /*
     * 팝업이 닫힌 이후 발생한 오류이거나,
     * QR 재생성 전 요청에서 발생한 오류라면 아무 처리도 하지 않는다.
     */
    if (
      !isDialogActive.value ||
      isClosing.value ||
      !isPolling.value ||
      trxCode.value !== requestTrxCode
    ) return

    if (Date.now() - pollingStartTime.value >= POLLING_TIMEOUT) {
      handlePollingTimeout()
      return
    }

    schedulePolling()
  } finally {
    isPollingRequest.value = false
  }
}

const startPolling = () => {
  stopPolling()

  if (!isDialogActive.value || isClosing.value || !trxCode.value) return

  isPolling.value = true
  pollingStartTime.value = Date.now()

  schedulePolling(INITIAL_POLLING_DELAY)
}

const startTimer = () => {
  stopTimer()
  remainSeconds.value = QR_VALID_SECONDS

  timerId.value = setInterval(() => {
    if (!isDialogActive.value || isClosing.value) {
      stopTimer()
      return
    }

    remainSeconds.value -= 1

    if (remainSeconds.value > 0) return

    stopTimer()

    qrData.value = ''
    isQrExpired.value = true

    clearCanvas()
  }, 1000)
}

const drawQr = async () => {
  await nextTick()

  if (!isDialogActive.value || isClosing.value || !qrCanvas.value || !qrData.value) return

  await toCanvas(qrCanvas.value, qrData.value, {
    width: 200,
    margin: 1,
    errorCorrectionLevel: 'M',
  })
}

const requestQr = async () => {
  if (!isDialogActive.value || isClosing.value) return

  try {
    stopTimer()
    stopPolling()
    resetVerificationResult()

    isLoading.value = true
    isError.value = false
    isQrExpired.value = false

    qrData.value = ''
    trxCode.value = ''
    remainSeconds.value = QR_VALID_SECONDS

    await nextTick()

    if (!isDialogActive.value || isClosing.value) {
      return
    }

    clearCanvas()

    const res = await post('/api/koi-idcard/qr/request', {
      ifType: 'MPM',
    })

    /*
     * QR 생성 API 호출 중 팝업이 닫힌 경우 응답을 반영하지 않는다.
     */
    if (!isDialogActive.value || isClosing.value) return

    const responseQrData = res?.data?.qrData
    const responseTrxCode = res?.data?.trxcode

    if (!responseQrData || !responseTrxCode) {
      isError.value = true
      return
    }

    qrData.value = responseQrData
    trxCode.value = responseTrxCode

    await drawQr()

    if (!isDialogActive.value || isClosing.value || isError.value) return

    startTimer()
    startPolling()
  } catch (e) {
    console.error('QR 요청 실패', e)

    if (!isDialogActive.value || isClosing.value) return

    qrData.value = ''
    trxCode.value = ''
    isQrExpired.value = false
    isError.value = true
  } finally {
    /*
     * 팝업을 닫고 다시 열었을 때 이전 요청의 finally가
     * 현재 화면의 상태를 건드리지 않도록 활성 상태에서만 변경한다.
     */
    if (isDialogActive.value && !isClosing.value) {
      isLoading.value = false
    }
  }
}

const onRegenerateQr = async () => {
  await requestQr()
}

const onOpen = async () => {
  isClosing.value = false
  isDialogActive.value = true

  emit('open')
  await requestQr()
}

const closeDialog = () => {
  if (isClosing.value || !props.modelValue) return

  /*
   * 실행 중인 API 응답보다 먼저 비활성 상태로 변경한다.
   * 이후 polling 응답이 도착하더라도 시간 초과/실패 컨펌을 띄우지 않는다.
   */
  isClosing.value = true
  isDialogActive.value = false

  stopTimer()
  stopPolling()
  resetState()

  emit('update:modelValue', false)
  emit('close')
}

const onClose = () => {
  /*
   * closeDialog()에서 modelValue를 false로 변경한 뒤
   * MsfDialog의 close 이벤트가 다시 발생하면 무시한다.
   */
  if (isClosing.value || !isDialogActive.value) return

  /*
   * 인증 성공 상태에서는 종료 확인창 없이 바로 닫는다.
   */
  if (isVerificationSuccess.value) {
    closeDialog()
    return
  }

  /*
   * 하단 취소 버튼과 우측 상단 X 버튼 모두
   * 동일한 종료 확인창을 표시한다.
   */
  showConfirm('모바일 신분증 인증을 종료하시겠습니까?', closeDialog, '인증이 진행 중입니다.\n종료하면 현재 인증 진행 상태가 초기화됩니다.', () => {})
}

const onConfirm = async () => {
  if (!isDialogActive.value || isClosing.value) return

  if (isVerificationSuccess.value && verificationResult.value) {
    emit('confirm', verificationResult.value)
    closeDialog()
    return
  }

  if (!trxCode.value || isLoading.value || isPollingRequest.value) return

  if (!isPolling.value) {
    isPolling.value = true
    pollingStartTime.value = Date.now()
  }

  if (pollingTimerId.value) {
    clearTimeout(pollingTimerId.value)
    pollingTimerId.value = null
  }

  await requestVerificationResult()
}

onBeforeUnmount(() => {
  isDialogActive.value = false
  isClosing.value = true

  stopTimer()
  stopPolling()
})
</script>

<style scoped>
/* 기존 style 영역의 expired 관련 스타일을 아래처럼 적용 */

</style>
