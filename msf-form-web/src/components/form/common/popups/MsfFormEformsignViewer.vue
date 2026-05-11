<template>
  <!-- eformsign iframe 이 삽입될 영역 -->
  <div
      :id="containerId"
      ref="viewerRef"
      class="msf-form-eformsign-viewer"
  />
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, watch, nextTick } from 'vue'

const props = defineProps({
  /**
   * 팝업 open 여부
   */
  modelValue: {
    type: Boolean,
    default: false,
  },

  /**
   * eformsign template 옵션
   */
  options: {
    type: Object,
    required: true,
  },

  /**
   * iframe 삽입 영역 id
   */
  containerId: {
    type: String,
    default: 'eformsign_iframe',
  },
})

const emit = defineEmits([
  'success',
  'error',
  'action',
  'loaded',
])

const viewerRef = ref(null)
let instance = null

/**
 * 외부 script 동적 로드
 */
const loadScript = (src) =>
    new Promise((resolve, reject) => {
      const exist = document.querySelector(`script[src="${src}"]`)

      if (exist) {
        resolve()
        return
      }

      const script = document.createElement('script')
      script.src = src
      script.async = true

      script.onload = () => resolve()
      script.onerror = () =>
          reject(new Error(`스크립트 로드 실패: ${src}`))

      document.head.appendChild(script)
    })

/**
 * eformsign SDK 로드
 * .env
 * VITE_EFORM_API_URL=https://www.eformsign.com/lib/js/efs_embedded_form.js
 */
const loadEformSdk = async () => {
  if (window.EformSignTemplate) return

  const sdkUrl = import.meta.env.VITE_EFORM_API_URL

  if (!sdkUrl) {
    throw new Error(
        'VITE_EFORM_API_URL 환경변수가 없습니다.',
    )
  }

  await loadScript(sdkUrl)
}

/**
 * Viewer 실행
 */
const renderViewer = async () => {
  try {
    await loadEformSdk()
    await nextTick()

    if (!window.EformSignTemplate) {
      console.error(
          'EformSignTemplate 스크립트가 로드되지 않았습니다.',
      )
      return
    }

    // 기존 iframe 제거
    if (viewerRef.value) {
      viewerRef.value.innerHTML = ''
    }

    instance = new window.EformSignTemplate()

    const successCallback = (response) => {
      emit('success', response)
    }

    const errorCallback = (response) => {
      emit('error', response)
    }

    const actionCallback = (response) => {
      emit('action', response)
    }

    instance.template(
        props.options,
        props.containerId,
        successCallback,
        errorCallback,
        actionCallback,
    )

    instance.open()

    emit('loaded')
  } catch (error) {
    console.error(error)
    emit('error', error)
  }
}

/**
 * iframe 정리
 */
const destroyViewer = () => {
  if (viewerRef.value) {
    viewerRef.value.innerHTML = ''
  }

  instance = null
}

/**
 * 팝업 열림/닫힘 감지
 */
watch(
    () => props.modelValue,
    (open) => {
      if (open) {
        renderViewer()
      } else {
        destroyViewer()
      }
    },
)

/**
 * 최초 mount 시 이미 열려있다면 실행
 */
onMounted(() => {
  if (props.modelValue) {
    renderViewer()
  }
})

onBeforeUnmount(() => {
  destroyViewer()
})
</script>

<style scoped lang="scss">
.msf-form-eformsign-viewer {
  width: 100%;
  height: 100%;
  min-height: 468px;
  overflow: hidden;
}
</style>