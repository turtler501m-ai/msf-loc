<template>
  <iframe
    id="eformsign_iframe"
    ref="iframeRef"
    class="eformsign-frame is-img"
    src="/eformsign/embedding/MsfMulti.html"
    @load="openEformsign"
  />
</template>

<script setup>
import { onBeforeUnmount, ref, toRaw } from 'vue'
import { post, postRaw } from '@/libs/api/msf.api.js'
import { getEformsignTemplateIds } from '@/libs/utils/eformsignTemplate.utils.js'

const SIGNATURE_COMPONENT_CUSTOMER_ID = 'custNmSign1'
const SIGNATURE_COMPONENT_CONSULT_ID = 'consultSign1'
const CUSTOMER_SIGNATURE_ONLY_TEMPLATE_KEYS = new Set(['insurance_ios', 'insurance_android'])
const EFORM_TOKEN_MAX_ATTEMPTS = 3
const EFORM_TOKEN_RETRY_DELAY_MS = 2000
const EFORM_EMBEDDING_ORIGIN = window.location.origin

const props = defineProps({
  formTypeCode: { type: String, default: '' },
  requestKey: { type: String, default: '' },
  cstmrNm: { type: String, default: '' },
  phoneNo: { type: String, default: '' },
  formParameters: { type: [Array, Object], default: () => [] },
  useNewChangeTemplate: { type: Boolean, default: false },
  deviceOs: { type: String, default: '' },
  savedFileData: { type: Array, default: () => [] },
  recordFileData: { type: Object, default: null },
  documentTitleSuffix: { type: String, default: '' },
  skipBackendCreate: { type: Boolean, default: false },
})

const emit = defineEmits(['viewer-ready', 'export-ready', 'save-complete', 'save-fail'])

const iframeRef = ref(null)
const saveResolve = ref(null)
const saveReject = ref(null)
const initRequested = ref(false)
const tokenData = ref(null)
const recordAudioDataUrl = ref(null)

const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms))

const postToEformsign = (message) => {
  const iframeWindow = iframeRef.value?.contentWindow
  if (!iframeWindow) return false

  iframeWindow.postMessage(message, EFORM_EMBEDDING_ORIGIN)
  return true
}

const isPlainObject = (value) =>
  value !== null && typeof value === 'object' && !Array.isArray(value)

const toPostMessageValue = (value) => {
  const rawValue = toRaw(value)
  if (rawValue == null) return rawValue

  const type = typeof rawValue
  if (type === 'string' || type === 'number' || type === 'boolean') return rawValue
  if (type === 'function' || type === 'symbol' || type === 'undefined') return undefined

  if (Array.isArray(rawValue)) {
    return rawValue.map((item) => toPostMessageValue(item)).filter((item) => item !== undefined)
  }

  if (rawValue instanceof Date) return rawValue.toISOString()

  if (type === 'object') {
    return Object.entries(rawValue).reduce((acc, [key, item]) => {
      const nextValue = toPostMessageValue(item)
      if (nextValue !== undefined) acc[key] = nextValue
      return acc
    }, {})
  }

  return rawValue
}

const normalizeFormParameters = (formParameters) => {
  const empty = {
    newchange: [],
    ownerchange: [],
    termination: [],
    servicechange: [],
    ios: [],
    android: [],
  }

  if (isPlainObject(formParameters)) {
    return {
      ...empty,
      newchange: Array.isArray(formParameters.newchange) ? formParameters.newchange : [],
      ownerchange: Array.isArray(formParameters.ownerchange) ? formParameters.ownerchange : [],
      termination: Array.isArray(formParameters.termination) ? formParameters.termination : [],
      servicechange: Array.isArray(formParameters.servicechange)
        ? formParameters.servicechange
        : [],
      ios: Array.isArray(formParameters.ios) ? formParameters.ios : [],
      android: Array.isArray(formParameters.android) ? formParameters.android : [],
    }
  }

  if (Array.isArray(formParameters)) {
    return {
      ...empty,
      [props.formTypeCode]: formParameters,
    }
  }

  return empty
}

const getFormParameters = async () => {
  if (props.formTypeCode !== 'newchange') {
    return normalizeFormParameters(props.formParameters)
  }

  const res = await post('/api/form/common/form-info/get', {
    formTypeCd: props.formTypeCode,
    requestKey: props.requestKey,
  })

  return normalizeFormParameters(res.data?.formParameters || props.formParameters)
}

const getEformToken = async () => {
  const res = await postRaw('/api/form/common/eform-api-token/get', '', {
    skipLoading: true,
  })
  return res.data?.data
}

const getValidToken = async () => {
  if (tokenData.value) return tokenData.value

  let lastError

  for (let attempt = 1; attempt <= EFORM_TOKEN_MAX_ATTEMPTS; attempt += 1) {
    try {
      const token = await getEformToken()

      if (!token?.accessToken) {
        throw new Error('eformsign 토큰 정보가 없습니다.')
      }

      tokenData.value = token
      return token
    } catch (e) {
      lastError = e

      if (attempt < EFORM_TOKEN_MAX_ATTEMPTS) {
        await sleep(EFORM_TOKEN_RETRY_DELAY_MS)
      }
    }
  }

  throw lastError
}

const getPayloadDocumentIds = (payload) => {
  return (payload?.fileData || []).map((item) => item?.id).filter(Boolean)
}
const getSignatureValidationTargets = (payload) => {
  return (payload?.signatureValidationTargets || []).filter((item) => item?.documentId)
}

const getSignatureComponentIds = (templateKey = '') => {
  const isCustomerSignatureOnly = CUSTOMER_SIGNATURE_ONLY_TEMPLATE_KEYS.has(templateKey)

  const componentIds = [
    {
      name: SIGNATURE_COMPONENT_CUSTOMER_ID,
      value: '',
    },
  ]

  if (!isCustomerSignatureOnly) {
    componentIds.push({
      name: SIGNATURE_COMPONENT_CONSULT_ID,
      value: '',
    })
  }

  return componentIds
}

const validateSignature = async (payload) => {
  const accessToken = payload?.accessToken
  const validationTargets = getSignatureValidationTargets(payload)

  if (!accessToken) {
    throw new Error('eformsign 서명 검증 토큰 정보가 없습니다.')
  }

  if (validationTargets.length === 0) {
    throw new Error('서명 검증할 documentId가 없습니다.')
  }

  const results = []

  for (const { documentId, templateKey } of validationTargets) {
    const res = await post(
      '/api/form/common/validate-eform/signature/get',
      {
        accessToken,
        documentId,
        componentIds: getSignatureComponentIds(templateKey),
      },
      { skipAlert: true },
    )

    const data = res?.data || {}

    if (!data.signed) {
      throw new Error('신청서에 서명이 없습니다. 서명 후 다시 확인해 주세요.')
    }

    results.push({
      templateKey,
      documentId,
      ...data,
    })
  }

  return {
    signed: true,
    results,
  }
}

const cancelDocumentsByIds = async (documentIds = []) => {
  const ids = Array.isArray(documentIds) ? documentIds.filter(Boolean) : []
  if (ids.length === 0) return true

  await post('/api/form/common/eform/documents/cancel', { documentIds: ids }, { skipAlert: true })
  return true
}

const cancelEformDocuments = async (payload) => {
  const documentIds = getPayloadDocumentIds(payload)
  return cancelDocumentsByIds(documentIds)
}

const saveFilesOnBackend = async (payload, retryCount) => {
  const accessToken = payload?.accessToken
  const fileData = payload?.fileData || []

  if (!accessToken) {
    throw new Error('eformsign 다운로드 토큰 정보가 없습니다.')
  }

  if (!Array.isArray(fileData) || fileData.length === 0) {
    throw new Error('처리할 eformsign 문서 정보가 없습니다.')
  }

  const eformsignFileData = []

  for (let index = 0; index < fileData.length; index += 1) {
    const item = fileData[index]
    const documentId = item.id
    const title = item.title || `신청서_${index + 1}`
    const fileName = `${title}.pdf`

    const res = await post(
      '/api/form/common/eform/documents/files/create',
      {
        accessToken,
        refreshToken: payload?.refreshToken,
        documentId,
        retryCount,
        fileName,
        fileCategory: props.formTypeCode,
        requestKey: props.requestKey,
        fileType: 'document',
      },
      { skipAlert: true },
    )

    if (!res || res.code !== '0000') {
      throw new Error(res?.message || '파일 저장 실패')
    }

    eformsignFileData.push(res.data)
  }

  return {
    documentIds: eformsignFileData.map((item) => item.documentId),
    eformsignFileData,
  }
}

const retrySaveFilesOnBackend = async (payload) => {
  const maxRetry = 30
  const retryInterval = 1000
  const retryState = { count: 0 }
  let lastError

  for (let attempt = 1; attempt <= maxRetry; attempt += 1) {
    if (attempt > 1) {
      await sleep(retryInterval)
    }

    retryState.count = attempt

    try {
      const result = await saveFilesOnBackend(payload, retryState.count)
      retryState.count = 0
      return result
    } catch (e) {
      lastError = e
    }
  }

  throw lastError
}

const openEformsign = async () => {
  if (initRequested.value) return

  const iframe = iframeRef.value
  if (!iframe?.contentWindow) return

  initRequested.value = true

  try {
    const [formParameters, token] = await Promise.all([getFormParameters(), getValidToken()])

    const basePayload = toPostMessageValue({
      ...token,
      envUrl: import.meta.env.VITE_EFORM_BASE_URL,
      companyId: token.companyId,
      userId: token.memberId,
      userName: token.userName,
      accessToken: token.accessToken,
      refreshToken: token.refreshToken,
      templateIds: getEformsignTemplateIds(),
      requestKey: props.requestKey,
      formTypeCode: props.formTypeCode,
      cstmrNm: props.cstmrNm,
      phoneNo: props.phoneNo,
      formParameters,
      useNewChangeTemplate: props.useNewChangeTemplate,
      deviceOs: props.deviceOs,
      fileData: props.savedFileData,
      documentTitleSuffix: props.documentTitleSuffix,
    })

    postToEformsign({
      type: 'EFORMSIGN_INIT',
      payload: basePayload,
    })
  } catch (e) {
    initRequested.value = false

    emit('save-fail', {
      phase: 'init',
      message:
        e?.response?.data?.message ||
        e?.response?.data?.result?.message ||
        e?.message ||
        'eformsign 초기화에 실패했습니다.',
      error: e,
    })
  }
}

const retryEformsign = () => {
  tokenData.value = null
  initRequested.value = false
  return openEformsign()
}

const postRecordAudioDataToIframe = () => {
  if (!recordAudioDataUrl.value) return false

  return postToEformsign({
    type: 'EFORMSIGN_SET_RECORD_AUDIO',
    payload: {
      recordAudioDataUrl: recordAudioDataUrl.value,
    },
  })
}

const setRecordAudioData = (dataUrl) => {
  recordAudioDataUrl.value = dataUrl || null
  if (!recordAudioDataUrl.value) return false
  return postRecordAudioDataToIframe()
}

const clearSavePromise = () => {
  saveResolve.value = null
  saveReject.value = null
}

const saveEformsign = () => {
  return new Promise((resolve, reject) => {
    saveResolve.value = resolve
    saveReject.value = reject

    const posted = postToEformsign({
      type: 'EFORMSIGN_SAVE_REQUEST',
    })

    if (!posted) {
      clearSavePromise()
      reject(new Error('eformsign iframe을 찾을 수 없습니다.'))
    }
  })
}

const createBackendFiles = async (payload, signatureValidation) => {
  const backendResult = await retrySaveFilesOnBackend(payload)

  return {
    ...payload,
    backendResult,
    signatureValidation,
    documentIds: backendResult.documentIds,
    eformsignFileData: backendResult.eformsignFileData,
    recordAudioDataUrl: recordAudioDataUrl.value,
  }
}

const createDocumentOnlyResult = (payload, signatureValidation) => {
  const documentIds = getPayloadDocumentIds(payload)
  const fileData = Array.isArray(payload?.fileData) ? payload.fileData : []

  return {
    ...payload,
    backendResult: null,
    documentIds,
    eformsignFileData: fileData.map((item) => ({
      documentId: item.id,
      fileName: `${item.title || '신청서'}.pdf`,
      title: item.title || '',
    })),
    signatureValidation,
    recordAudioDataUrl: recordAudioDataUrl.value,
    backendCreateSkipped: true,
  }
}

const handleSaveComplete = async (payload) => {
  try {
    let signatureValidation

    try {
      signatureValidation = await validateSignature(payload)
    } catch (signatureError) {
      await cancelEformDocuments(payload).catch(() => {})
      throw signatureError
    }

    const result = props.skipBackendCreate
      ? createDocumentOnlyResult(payload, signatureValidation)
      : await createBackendFiles(payload, signatureValidation)

    emit('save-complete', result)

    if (saveResolve.value) {
      saveResolve.value(result)
      clearSavePromise()
    }
  } catch (e) {
    emit('save-fail', {
      message: e.message,
      error: e,
      payload,
    })

    if (saveReject.value) {
      saveReject.value(e)
      clearSavePromise()
    }
  }
}

const onMessage = (event) => {
  if (event.origin !== EFORM_EMBEDDING_ORIGIN) return
  if (event.source !== iframeRef.value?.contentWindow) return

  const message = event.data
  if (!message?.type) return

  if (message.type === 'EFORMSIGN_HTML_READY') {
    openEformsign()
    return
  }

  if (message.type === 'EFORMSIGN_VIEWER_READY') {
    emit('viewer-ready')
    return
  }

  if (message.type === 'EFORMSIGN_EXPORT_READY') {
    emit('export-ready', message.payload)
    return
  }

  if (message.type === 'EFORMSIGN_SAVE_COMPLETE') {
    handleSaveComplete(message.payload)
    return
  }

  if (message.type === 'EFORMSIGN_SAVE_FAIL') {
    emit('save-fail', message.payload)

    if (saveReject.value) {
      const error = new Error(message.payload?.message || 'eformsign 저장 실패')
      error.detail = message.payload?.detail || null
      error.payload = message.payload || null
      saveReject.value(error)
      clearSavePromise()
    }
  }
}

window.addEventListener('message', onMessage)

onBeforeUnmount(() => {
  window.removeEventListener('message', onMessage)
})

defineExpose({
  saveEformsign,
  openEformsign,
  retryEformsign,
  setRecordAudioData,
})
</script>

<style scoped></style>
