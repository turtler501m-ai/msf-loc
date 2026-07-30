<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신분증 스캔"
    @open="onOpen"
    @close="onClose"
  >
    <div class="ocr-container">
      <MsfKoiOcr
        :key="ocrKey"
        ref="ocrRef"
        class="ocr-frame"
        :ocr-type="props.identityTypeCd"
        :return-file="props.returnFile"
        @ready="handleOcrReady"
        @success="handleOcrSuccess"
        @error="handleOcrError"
        @timeout="handleOcrTimeout"
      />
    </div>
  </MsfDialog>
</template>

<script setup>
import { nextTick, ref } from 'vue'
import MsfKoiOcr from '@/components/common/koi-ocr/MsfKoiOcr.vue'
import { showConfirm } from '@/libs/utils/comp.utils.js'

const props = defineProps({
  modelValue: Boolean,
  identityTypeCd: String,
  identityTypeNm: String,
  returnFile: { type: Boolean, default: false },
  fileCategory: { type: String, default: 'newchange' },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const ocrRef = ref(null)
const docFile = ref(null)
const isOcrReady = ref(false)
const ocrKey = ref(0)

const isIOS = () =>
  /iPad|iPhone|iPod/.test(navigator.userAgent) ||
  (navigator.platform === 'MacIntel' && navigator.maxTouchPoints > 1)

const sleep = (ms) => new Promise((r) => setTimeout(r, ms))

const recreateOcr = async () => {
  isOcrReady.value = false

  // iframe 완전 제거 후 재생성
  ocrKey.value += 1

  await nextTick()

  // iOS 카메라 해제 대기
  await sleep(isIOS() ? 1500 : 300)
}

const onOpen = () => {
  emit('open')

  nextTick(() => {
    if (isOcrReady.value) {
      openCamera()
    }
  })
}

const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const openCamera = () => {
  ocrRef.value?.startOCR({ useCapOcr: 1 })
}

const handleOcrReady = () => {
  isOcrReady.value = true

  if (props.modelValue) {
    openCamera()
  }
}

const handleOcrSuccess = (result) => {
  const raw = result?.data || result
  const code = raw?.ocrResultCode || raw?.resultCode

  if (code !== '0000') {
    handleOcrError({
      message: raw?.ocrResultMessage || raw?.message,
      code,
      raw: result,
    })
    return
  }
  docFile.value = raw
  showConfirm('신분증 인증이 완료되었습니다. 적용하시겠습니까?', onConfirm, '', onClose)
}

const handleOcrError = (error) => {
  docFile.value = null

  const errorMessage =
    typeof error === 'string' ? error : error?.message || '알 수 없는 오류가 발생했습니다.'

  showConfirm(
    '신분증 인식 중 오류가 발생했습니다.\n다시 촬영하시겠습니까?',
    async () => {
      await recreateOcr()
      // OCR_READY 수신 시 handleOcrReady에서 자동 재시작
    },
    '사유 : ' + errorMessage,
    onClose,
  )
}

const handleOcrTimeout = () => {
  showConfirm(
    '신분증 인식 시간이 초과되었습니다.\n다시 촬영하시겠습니까?',
    async () => {
      await recreateOcr()
    },
    '',
    onClose,
  )
}

const base64ToBlob = (base64, mimeType = 'image/jpeg') => {
  if (!base64) return null

  const pureBase64 = base64.includes(',') ? base64.split(',')[1] : base64

  const byteCharacters = atob(pureBase64)
  const byteNumbers = new Array(byteCharacters.length)

  for (let i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i)
  }

  return new Blob([new Uint8Array(byteNumbers)], {
    type: mimeType,
  })
}

/**
 * 주민등록증 OCR로 인식한 전체 주소를 기본주소와 상세주소로 분리한다.
 * 주소 형식을 판별할 수 없으면 원문 전체를 기본주소로 반환한다.
 *
 * @param {string} fullAddress
 * @returns {{ baseAddress: string, detailAddress: string }}
 */
const splitAddress = (fullAddress) => {
  if (typeof fullAddress !== 'string' || !fullAddress.trim()) {
    return { baseAddress: '', detailAddress: '' }
  }

  const cleanedAddress = fullAddress.trim().replace(/\s+/g, ' ').replace(/\s*,\s*/g, ', ')
  const addressPatterns = [
    // 도로명 주소: 테헤란로 123, 경수대로 910번길 15, 고북로 123-4, 올림픽로 지하 23
    /^(.+?(?:대로|로|길)(?:\s+\d+번길)?\s+(?:지하\s+)?\d+(?:-\d+)?)(?:,\s*|\s+)(.+)$/,
    // 지번 주소: 역삼동 123-45, 태평로1가 31 번지, 개포동 산 12-3, 개포동 산12-3
    /^(.+?(?:읍|면|동|리|가)\s+(?:산\s*)?\d+(?:-\d+)?(?:\s*번지)?)(?:,\s*|\s+)(.+)$/,
  ]

  for (const pattern of addressPatterns) {
    const match = cleanedAddress.match(pattern)

    if (match) {
      // "31 번지"처럼 번지만 남은 경우에는 상세주소로 취급하지 않는다.
      if (match[2].trim() === '번지') {
        return { baseAddress: cleanedAddress, detailAddress: '' }
      }

      let baseAddress = match[1].trim()
      let detailAddress = match[2].trim()

      // OCR 줄바꿈으로 건물번호가 "231" → "23 1,"처럼 분리된 경우 복원한다.
      const splitBuildingNumber = detailAddress.match(
        /^(\d+)\s*,\s*((?:제?\d+|[A-Za-z가-힣]+)\s*(?:동|층|호|관|상가)(?:\s|$).*)$/,
      )

      if (splitBuildingNumber && /(?:대로|로|길)\s+(?:지하\s+)?\d+$/.test(baseAddress)) {
        baseAddress += splitBuildingNumber[1]
        detailAddress = splitBuildingNumber[2].trim()
      }

      return {
        baseAddress,
        detailAddress,
      }
    }
  }

  return { baseAddress: cleanedAddress, detailAddress: '' }
}

const onConfirm = () => {
  const raw = docFile.value || {}
  const scanRrn = raw.cstmrNativeRrn || raw.cstmrForeignerRrn || raw.rrn || raw.essNo || ''
  const scanDt = raw.scanDt || raw.identityScanDt || ''
  const scanId = raw.scanId || raw.frmpapId || ''
  const maskImageBlob = base64ToBlob(raw.maskImageFile)
  const maskImageFile = maskImageBlob ? URL.createObjectURL(maskImageBlob) : ''
  const parsedAddress = splitAddress(raw.address)

  emit('confirm', {
    scanSource: 'REAL_OCR',
    isRealOcr: true,
    cstmrNm: raw.cstmrNm || '',
    cstmrNativeRrn: raw.cstmrNativeRrn || '',
    identityIssuDate: raw.identityIssuDate || '',
    driveLicnsNo: raw.driveLicnsNo || '',
    cstmrForeignerRrn: raw.cstmrForeignerRrn || '',
    trnsNm: raw.trnsNm || '',
    identityIssuRegion: raw.identityIssuRegion || '',
    zipNo: raw.zipNo || '',
    address: parsedAddress.baseAddress,
    detailAddress: parsedAddress.detailAddress,
    identityTypeCd: props.identityTypeCd || raw.identityTypeCd || '',
    identityTypeNm: props.identityTypeNm || raw.identityTypeNm || '',
    rrn: scanRrn,
    scanDt,
    scanId,
    maskImageFile: maskImageFile,
  })

  onClose()
}
</script>
