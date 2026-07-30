<template>
  <iframe
    :key="iframeKey"
    id="eformsign_iframe"
    ref="iframeRef"
    class="eformsign-frame is-preview"
    src="/eformsign/embedding/MsfPreview.html"
  />
</template>

<script setup>
import { computed, nextTick, onBeforeUnmount, ref, watch } from 'vue'
import { postRaw } from '@/libs/api/msf.api.js'

const EFORM_EMBEDDING_ORIGIN = window.location.origin

const props = defineProps({
  modelValue: Boolean,
  documentId: {
    type: Array,
    default: () => [],
  },
})

const emit = defineEmits(['viewer-ready', 'init-fail'])

const iframeRef = ref(null)
const htmlReady = ref(false)
const initRequested = ref(false)
const tokenData = ref(null)
const iframeKey = ref(0)

const postToEformsign = (message) => {
  const iframeWindow = iframeRef.value?.contentWindow
  if (!iframeWindow) return false

  iframeWindow.postMessage(message, EFORM_EMBEDDING_ORIGIN)
  return true
}

const normalizedDocumentIds = computed(() => {
  if (Array.isArray(props.documentId)) {
    return props.documentId.filter(Boolean)
  }

  if (props.documentId) {
    return [props.documentId]
  }

  return []
})

const getEformToken = async () => {
  const res = await postRaw('/api/form/common/eform-api-token/get')
  return res.data?.data
}

const getValidToken = async () => {
  if (tokenData.value) return tokenData.value

  const token = await getEformToken()

  if (!token?.accessToken) {
    throw new Error('eformsign 토큰 정보가 없습니다.')
  }

  tokenData.value = token
  return token
}

const resetInitState = () => {
  htmlReady.value = false
  initRequested.value = false
}

const reloadIframe = () => {
  resetInitState()
  iframeKey.value += 1
}

const openEformsign = async () => {
  if (initRequested.value) return
  if (!htmlReady.value) return

  const documentIds = normalizedDocumentIds.value

  if (documentIds.length === 0) {
    return
  }

  await nextTick()

  const iframe = iframeRef.value

  if (!iframe?.contentWindow) {
    return
  }

  initRequested.value = true

  try {
    const token = await getValidToken()

    postToEformsign({
      type: 'EFORMSIGN_INIT',
      payload: {
        envUrl: import.meta.env.VITE_EFORM_BASE_URL,
        companyId: token.companyId,
        userId: token.memberId,
        userName: token.userName,
        accessToken: token.accessToken,
        refreshToken: token.refreshToken,
        documentId: documentIds,
      },
    })
  } catch (e) {
    initRequested.value = false

    emit('init-fail', {
      message:
        e?.response?.data?.message ||
        e?.response?.data?.result?.message ||
        e?.message ||
        'eformsign 미리보기 초기화에 실패했습니다.',
      error: e,
    })
  }
}

const onMessage = (event) => {
  if (event.origin !== EFORM_EMBEDDING_ORIGIN) return
  if (event.source !== iframeRef.value?.contentWindow) return

  const message = event.data
  if (!message?.type) return

  if (message.type === 'EFORMSIGN_HTML_READY') {
    htmlReady.value = true
    openEformsign()
    return
  }

  if (message.type === 'EFORMSIGN_VIEWER_READY') {
    emit('viewer-ready')
    return
  }

  if (message.type === 'EFORMSIGN_PREVIEW_FAIL') {
    initRequested.value = false

    emit('init-fail', {
      message: message.payload?.message || 'eformsign 미리보기에 실패했습니다.',
      error: message.payload,
    })
  }
}

window.addEventListener('message', onMessage)

watch(
  () => normalizedDocumentIds.value.join(','),
  (next, prev) => {
    if (!next) return

    if (prev && next !== prev) {
      reloadIframe()
      return
    }

    openEformsign()
  },
)

watch(
  () => props.modelValue,
  (isOpen) => {
    if (!isOpen) return

    if (normalizedDocumentIds.value.length > 0) {
      openEformsign()
    }
  },
)

onBeforeUnmount(() => {
  window.removeEventListener('message', onMessage)
})

defineExpose({
  openEformsign,
  reloadIframe,
})
</script>

<style scoped></style>
