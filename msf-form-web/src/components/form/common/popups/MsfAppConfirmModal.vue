<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신청서 확인"
    size="xlarge"
    @open="onOpen"
    @close="onClose"
    dividerFooter
  >
    <div class="eformsign-flex-layout">
      <MsfCollapse v-if="showRecordSection" defaultOpen>
        <template #header>
          <span class="ut-text-separate">안내 녹취</span>
          <span class="ut-text-info">※ 최대 <em>10</em>분</span>
        </template>
        <MsfEformRecorder
          :key="recorderKey"
          ref="eformRecorderRef"
          :form-type-code="formTypeCode"
          :disabled="!isViewerReady || isInitFailed"
          @upload-complete="onRecordUploadComplete"
        />
        <div class="record-script-wrap">
          <MsfCustomScroll class="record-script">
            {{ transcriptionScript }}
          </MsfCustomScroll>
        </div>
      </MsfCollapse>
      <MsfTitleArea title="신청서" level="2" bold noline margin="4" />
      <MsfLoadingComp :isOpen="isLoading" />
      <div class="eformsign-container">
        <MsfEformImg
          v-show="props.modelValue"
          :key="eformImgKey"
          ref="eformImgRef"
          class="eform-frame"
          :form-type-code="props.formTypeCode"
          :request-key="props.requestKey"
          :cstmr-nm="props.cstmrNm"
          :phone-no="props.phoneNo"
          :form-parameters="props.formParameters"
          :use-new-change-template="props.useNewChangeTemplate"
          :device-os="props.deviceOs"
          :saved-file-data="props.savedFileData"
          :record-file-data="recordFileData"
          :document-title-suffix="documentTitleSuffix"
          :skip-backend-create="skipBackendCreate"
          @viewer-ready="onViewerReady"
          @save-fail="onEformFail"
        />
      </div>
    </div>
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" :disabled="isEditDisabled" @click="onEdit"> 수정 </MsfButton>
        <MsfButton variant="primary" :disabled="isConfirmDisabled" @click="onConfirmClick">
          {{ isInitFailed ? '재시도' : '신청서 제출' }}
        </MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>
<script setup>
import { computed, ref } from 'vue'
import { MsfCollapse, MsfDialog, MsfTitleArea } from '@/libs/ui/index.js'
import { pageScrollToTop } from '@/hooks/useGlobalScroll'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils.js'
import { postRaw } from '@/libs/api/msf.api.js'
import MsfEformImg from '@/components/common/eformsign/MsfEformImg.vue'

const props = defineProps({
  modelValue: Boolean,
  formTypeCode: { type: String, default: '' },
  requestKey: { type: String, default: '' },
  cstmrNm: { type: String, default: '' },
  phoneNo: { type: String, default: '' },
  formParameters: { type: [Array, Object], default: () => [] },
  useNewChangeTemplate: { type: Boolean, default: false },
  deviceOs: { type: String, default: '' },
  savedFileData: { type: Array, default: () => [] },
  transcriptionScriptData: {
    type: Object,
    default: () => ({
      requestKey: '',
      formTypeCd: '',
      tgtType: '',
      reqBuyTypeCd: '',
      cstmrVisitTypeCd: 'VMY',
      enggYn: 'N',
      sprtTypeCd: '',
      rmndYn: 'N',
      rateYn: 'N',
      insrYn: 'N',
      addYn: 'N',
      cntpntCdNm: '',
      userNm: '',
      nflCustNm: '',
      mobilePriceNm: '',
      mobileMntcntAmtFee: '',
    }),
  },
  editDisabled: { type: Boolean, default: false },
  deferUpload: { type: Boolean, default: false },
  successOnlyReview: { type: Boolean, default: false },
})

const emit = defineEmits([
  'update:modelValue',
  'open',
  'close',
  'confirm',
  'edit',
  'extract-complete',
  'save-complete',
  'save-fail',
])

const eformImgRef = ref(null)
const eformRecorderRef = ref(null)
const transcriptionScript = ref('')
const recordFileData = ref(null)
const isSaving = ref(false)
const isLoading = ref(false)
const isViewerReady = ref(false)
const isInitFailed = ref(false)
const reviewReloadKey = ref(0)
const forceBackendCreate = ref(false)

const recorderKey = ref(0)
const recordedFormSignature = ref(null)
const showRecordSection = props.formTypeCode === 'newchange' || props.formTypeCode === 'ownerchange'
const isServiceChange = computed(() => props.formTypeCode === 'servicechange')
const isEditDisabled = computed(
  () => props.editDisabled || !isViewerReady.value || isInitFailed.value,
)
const isConfirmDisabled = computed(
  () => isSaving.value || (!isViewerReady.value && !isInitFailed.value),
)

const currentFormSignature = computed(() => {
  return JSON.stringify({
    formTypeCode: props.formTypeCode,
    cstmrNm: props.cstmrNm,
    phoneNo: props.phoneNo,
    formParameters: props.formParameters,
    useNewChangeTemplate: props.useNewChangeTemplate,
    deviceOs: props.deviceOs,
    transcriptionScriptData: props.transcriptionScriptData,
  })
})
const skipBackendCreate = computed(() => {
  return (
    isServiceChange.value &&
    props.deferUpload &&
    !props.successOnlyReview &&
    !forceBackendCreate.value
  )
})

const eformImgKey = computed(() => {
  return JSON.stringify({
    formTypeCode: props.formTypeCode,
    requestKey: props.requestKey,
    reviewReloadKey: reviewReloadKey.value,
  })
})

const documentTitleSuffix = computed(() => {
  if (!isServiceChange.value || reviewReloadKey.value === 0) return ''
  return `_review_${reviewReloadKey.value}`
})

const oncancel = () => {}

const resetRecordData = () => {
  recordFileData.value = null
  recordedFormSignature.value = null

  // MsfEformRecorder를 재생성하여 내부에 남은 녹취 파일도 초기화
  recorderKey.value += 1
}

const onClose = () => {
  isLoading.value = false
  isSaving.value = false
  isViewerReady.value = false
  isInitFailed.value = false

  if (showRecordSection) {
    resetRecordData()
  }

  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const getErrorMessage = (e) => {
  return (
    e?.response?.data?.message ||
    e?.response?.data?.result?.message ||
    e?.message ||
    '파일 다운로드 중 오류가 발생했습니다.'
  )
}

const getRecordFileData = () => {
  return eformRecorderRef.value?.getRecordFileData?.() || recordFileData.value || null
}

const getRecordAudioDataUrl = (data) => {
  return (
    eformRecorderRef.value?.getRecordAudioDataUrl?.() ||
    data?.recordAudioDataUrl ||
    recordFileData.value?.recordAudioDataUrl ||
    null
  )
}

const hasRecordFileData = (data) => {
  if (!data) return false
  if (Array.isArray(data)) return data.length > 0
  return true
}

const getConfirmMessage = () => {
  const needRecord = props.formTypeCode === 'newchange' || props.formTypeCode === 'ownerchange'

  if (needRecord) {
    return {
      title: '신청서에 녹취 및 서명을 하셨습니까?',
      message: '녹취 및 서명 완료 후 확인을 눌러주세요.',
    }
  }

  return {
    title: '신청서에 서명을 하셨습니까?',
    message: '서명 완료 후 확인을 눌러주세요.',
  }
}

const onConfirmClick = () => {
  if (isConfirmDisabled.value) return

  if (isInitFailed.value) {
    showConfirm(
      '토큰 발급을 다시 시도하시겠습니까?',
      retryEformsign,
      '확인을 누르면 토큰 발급을 다시 요청합니다.',
      onClose,
    )
    return
  }

  const { title, message } = getConfirmMessage()
  showConfirm(title, onConfirm, message, oncancel)
}

const retryEformsign = () => {
  isInitFailed.value = false
  isViewerReady.value = false
  eformImgRef.value?.retryEformsign?.()
}

const saveAndCreateFiles = async () => {
  if (isSaving.value) return null

  const needRecordValidation =
    props.formTypeCode === 'newchange' || props.formTypeCode === 'ownerchange'

  const currentRecordFileData = getRecordFileData()

  if (needRecordValidation && !hasRecordFileData(currentRecordFileData)) {
    showAlert('녹취 파일이 없습니다. 녹취 완료 후 다시 확인해 주세요.', oncancel)
    return null
  }

  if (needRecordValidation && recordedFormSignature.value !== currentFormSignature.value) {
    resetRecordData()

    showAlert(
      '신청서 내용이 변경되었습니다. 변경된 내용을 기준으로 녹취를 다시 진행해 주세요.',
      oncancel,
    )
    return null
  }

  try {
    isLoading.value = true
    isSaving.value = true

    const recordAudioDataUrl = getRecordAudioDataUrl(currentRecordFileData)
    if (recordAudioDataUrl) {
      eformImgRef.value?.setRecordAudioData?.(recordAudioDataUrl)
    }

    const result = await eformImgRef.value?.saveEformsign()

    return {
      documentIds: result?.documentIds || [],
      eformsignFileData: result?.eformsignFileData || [],
      recordFileData: currentRecordFileData || [],
      rawResult: result,
    }
  } catch (e) {
    const message = getErrorMessage(e)
    showAlert(message)
    emit('save-fail', e)

    return null
  } finally {
    isSaving.value = false
    isLoading.value = false
  }
}

const onConfirm = async () => {
  const confirmData = await saveAndCreateFiles()
  if (!confirmData) return

  emit('save-complete', confirmData.rawResult)
  emit('confirm', confirmData)
  onClose()
}

const uploadDeferred = async () => {
  forceBackendCreate.value = true

  try {
    const confirmData = await saveAndCreateFiles()
    if (!confirmData) return null

    emit('save-complete', confirmData.rawResult)
    emit('confirm', confirmData)
    return confirmData
  } finally {
    forceBackendCreate.value = false
  }
}

const openDeferredReview = () => {
  reviewReloadKey.value += 1
  isLoading.value = true
  isSaving.value = false
  isViewerReady.value = false
  return true
}

const onViewerReady = () => {
  isLoading.value = false
  isViewerReady.value = true
  isInitFailed.value = false
}

const onEformFail = (failure) => {
  isLoading.value = false
  isSaving.value = false
  isViewerReady.value = false

  if (failure?.phase === 'init') {
    isInitFailed.value = true
    return
  }

  reviewReloadKey.value += 1
}

const onEdit = () => {
  if (isEditDisabled.value) return

  emit('edit')
  onClose()
  pageScrollToTop()
}

const onRecordUploadComplete = (data) => {
  recordFileData.value = data

  // 녹취 완료 당시 신청서 내용을 저장
  recordedFormSignature.value = currentFormSignature.value
  const recordAudioDataUrl = data?.recordAudioDataUrl || null

  if (recordAudioDataUrl) {
    eformImgRef.value?.setRecordAudioData?.(recordAudioDataUrl)
  }
}

const onOpen = async () => {
  emit('open')
  isViewerReady.value = false
  isInitFailed.value = false

  if (showRecordSection) {
    await getTranscriptionScript()
  }
}

const getTranscriptionScript = async () => {
  const { transcriptionScriptData } = props

  const requestKey = transcriptionScriptData.requestKey
  const formTypeCd = transcriptionScriptData.formTypeCd
  const mnp3TgtYn = transcriptionScriptData.tgtType === 'mnp3' ? 'Y' : 'N'
  const nac3TgtYn = transcriptionScriptData.tgtType === 'nac3' ? 'Y' : 'N'
  const hdn3TgtYn = transcriptionScriptData.tgtType === 'hdn3' ? 'Y' : 'N'
  const icn3TgtYn = transcriptionScriptData.tgtType === 'icn3' ? 'Y' : 'N'
  const reqBuyTypeCd = transcriptionScriptData.reqBuyTypeCd
  const cstmrSelf = transcriptionScriptData.cstmrVisitTypeCd === 'VMY' ? 'Y' : 'N'
  const cstmrAgent = transcriptionScriptData.cstmrVisitTypeCd === 'VMY' ? 'N' : 'Y'
  const enggY = transcriptionScriptData.enggYn === 'Y' ? 'Y' : 'N'
  const enggN = transcriptionScriptData.enggYn === 'Y' ? 'N' : 'Y'
  const enggKd = transcriptionScriptData.sprtTypeCd === 'KD' ? 'Y' : 'N'
  const enggPm = transcriptionScriptData.sprtTypeCd === 'PM' ? 'Y' : 'N'
  const enggSm = transcriptionScriptData.sprtTypeCd === 'SM' ? 'Y' : 'N'
  const rmndY = transcriptionScriptData.rmndYn === 'Y' ? 'Y' : 'N'
  const rateY = transcriptionScriptData.rateYn === 'Y' ? 'Y' : 'N'
  const insrY = transcriptionScriptData.insrYn === 'Y' ? 'Y' : 'N'
  const addY = transcriptionScriptData.addYn === 'Y' ? 'Y' : 'N'
  const cntpntCdNm = transcriptionScriptData.cntpntCdNm
  const userNm = transcriptionScriptData.userNm
  const nflCustNm = transcriptionScriptData.nflCustNm
  const mobilePriceNm = transcriptionScriptData.mobilePriceNm
  const mobileMntcntAmtFee = transcriptionScriptData.mobileMntcntAmtFee

  const payload = {
    requestKey,
    formTypeCd,
    mnp3TgtYn,
    nac3TgtYn,
    hdn3TgtYn,
    icn3TgtYn,
    reqBuyTypeCd,
    cstmrSelf,
    cstmrAgent,
    enggN,
    enggY,
    enggKd,
    enggPm,
    enggSm,
    rmndY,
    rateY,
    insrY,
    addY,
    cntpntCdNm,
    userNm,
    nflCustNm,
    mobilePriceNm,
    mobileMntcntAmtFee,
  }

  const res = await postRaw('/api/form/common/transcription-script/get', payload)
  transcriptionScript.value = (res?.data?.data || []).join('\n\n')
}

defineExpose({
  openDeferredReview,
  uploadDeferred,
})
</script>

<style lang="scss" scoped>
.eform-frame {
  width: 100%;
}
</style>
