<template>
  <div class="msf-wr-bar">
    <div class="msf-wr-top">
      <span v-if="isRecordingMode" class="msf-wr-flag accent2">녹취</span>
      <span v-if="isPlayingMode" class="msf-wr-flag accent">재생</span>
    </div>
    <div class="msf-wr-group" v-if="isRecordingMode">
      <div class="msf-wr-btns">
        <button
          v-if="!isRecording"
          class="msf-wr-btn accent2"
          title="녹음 시작"
          :disabled="props.disabled"
          @click="startRecording"
        >
          <i class="msf-icon play" />
        </button>
        <button
          v-if="isRecording"
          class="msf-wr-btn"
          :class="{ 'is-active': isRecording }"
          title="일시정지"
          :disabled="props.disabled"
          @click="pauseRecording"
        >
          <i class="msf-icon pause" />
        </button>
        <button
          class="msf-wr-btn"
          title="중지"
          @pointerdown.prevent="onClickStopBtn"
          :disabled="props.disabled || isStopDisabled || isStopping"
        >
          <i class="msf-icon stop" />
        </button>
      </div>
      <span class="wr-time">{{ recordTime }}</span>
    </div>
    <div class="msf-wr-group" v-if="isPlayingMode">
      <div class="msf-wr-btns">
        <button
          v-if="!isPlaying"
          class="msf-wr-btn accent"
          :disabled="props.disabled"
          @click="startPlaying"
        >
          <i class="msf-icon play" />
        </button>
        <button
          v-if="isPlaying"
          class="msf-wr-btn btn-playing"
          :disabled="props.disabled"
          @click="pausePlaying"
        >
          <i class="msf-icon pause" />
        </button>
        <button class="msf-wr-btn" :disabled="props.disabled" @click="resetAll">
          <i class="msf-icon reset" />
        </button>
      </div>
      <div class="wr-progress">
        <div class="wr-progress-fill" :style="{ width: `${playProgress}%` }" />
      </div>
      <span class="wr-play-time"> {{ playCurrentTime }} / {{ playTotalTime }} </span>
      <button class="msf-wr-btn ghost" :disabled="props.disabled" @click="deleteRecording">
        <i class="msf-icon trash" />
      </button>
    </div>
  </div>
</template>

<script setup>
import { computed, onBeforeUnmount, onMounted, ref } from 'vue'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
import { WebRecorder } from '../../../../public/eformsign/record/src/WebRecorder.js'
import { detectMimeType } from '../../../../public/eformsign/record/src/platform.js'
import { post } from '@/libs/api/msf.api.js'

const props = defineProps({
  formTypeCode: { type: String, default: '' },
  disabled: { type: Boolean, default: false },
})

const emit = defineEmits(['upload-complete', 'upload-fail'])

const VIEW_MODES = {
  DEFAULT: 'default',
  RECORDING: 'recording',
  PLAYING: 'playing',
}

const viewMode = ref('default')

const isRecording = ref(false)
const isPlaying = ref(false)
const hasRecording = ref(false)
const isStopping = ref(false)

const recordFileData = ref(null)

const recordTime = ref('00:00')
const playCurrentTime = ref('00:00')
const playTotalTime = ref('00:00')
const playProgress = ref(0)

const accumulatedMs = ref(0)
let lastStartTime = 0

const isStopDisabled = computed(() => !hasRecording.value)

const isRecordingMode = computed(() =>
  [VIEW_MODES.DEFAULT, VIEW_MODES.RECORDING].includes(viewMode.value),
)

const isPlayingMode = computed(() => viewMode.value === VIEW_MODES.PLAYING)

let recorder = null

const setMode = (m) => (viewMode.value = m)

const formatTime = (ms) => {
  const totalSec = Math.floor(ms / 1000)
  const min = String(Math.floor(totalSec / 60)).padStart(2, '0')
  const sec = String(totalSec % 60).padStart(2, '0')
  return `${min}:${sec}`
}

const getFinalRecordMs = () => {
  const state = recorder?.getState()
  if (state === 'recording') {
    return accumulatedMs.value + (Date.now() - lastStartTime)
  }
  return accumulatedMs.value
}

const startRecording = async () => {
  const state = recorder?.getState()

  if (state === 'paused') {
    await recorder.resume()
    lastStartTime = Date.now()
  } else {
    await recorder.start()
    accumulatedMs.value = 0
    lastStartTime = Date.now()
    hasRecording.value = true
    recordFileData.value = null
  }

  setMode(VIEW_MODES.RECORDING)
  isRecording.value = true
}

const pauseRecording = async () => {
  await recorder.pause()
  accumulatedMs.value += Date.now() - lastStartTime
  isRecording.value = false
}

const onClickStopBtn = async () => {
  if (isStopping.value) return
  isStopping.value = true

  try {
    const state = recorder?.getState()

    if (state === 'recording') {
      await recorder.pause()
    }

    await new Promise(requestAnimationFrame)
    await new Promise((r) => setTimeout(r, 800))

    await recorder.stop()

    setMode(VIEW_MODES.PLAYING)
    isRecording.value = false
    isPlaying.value = false

    const finalTime = formatTime(getFinalRecordMs())
    recordTime.value = finalTime
    playTotalTime.value = finalTime

    showAlert('녹취가 완료되었습니다.')

    updateDuration().catch(console.error)
    uploadRecording().catch(console.error)
  } catch (e) {
    console.error(e)
  } finally {
    isStopping.value = false
  }
}

const startPlaying = async () => {
  setMode(VIEW_MODES.PLAYING)
  isPlaying.value = true
  await recorder.play()
}

const pausePlaying = () => {
  recorder.pausePlay()
  isPlaying.value = false
}

const resetAll = async () => {
  recorder.pausePlay?.()
  isPlaying.value = false
  playProgress.value = 0
  playCurrentTime.value = '00:00'
  playTotalTime.value = recordTime.value
}

const deleteRecording = () => {
  showConfirm(
    '삭제된 녹취 파일은 복구할 수 없습니다. 삭제하시겠습니까?',
    clearRecordingState,
    '',
    () => {},
  )
}

const clearRecordingState = () => {
  recorder?.clear()

  accumulatedMs.value = 0
  lastStartTime = 0

  hasRecording.value = false
  isRecording.value = false
  isPlaying.value = false
  recordFileData.value = null
  recordTime.value = '00:00'
  playCurrentTime.value = '00:00'
  playTotalTime.value = '00:00'
  playProgress.value = 0

  setMode(VIEW_MODES.DEFAULT)
}

const updateDuration = async () => {
  const mediaData = recorder?.getMediaData()
  if (!mediaData) return

  try {
    const { mimeType } = detectMimeType()

    const base64 = mediaData.split(',')[1]
    const binary = atob(base64)
    const bytes = new Uint8Array(binary.length)

    for (let i = 0; i < binary.length; i++) {
      bytes[i] = binary.charCodeAt(i)
    }

    const blob = new Blob([bytes], { type: mimeType })
    const url = URL.createObjectURL(blob)

    const audio = new Audio(url)

    await new Promise((resolve) => {
      audio.addEventListener('loadedmetadata', resolve, { once: true })
      audio.load()
    })

    if (isFinite(audio.duration)) {
      playTotalTime.value = formatTime(audio.duration * 1000)
    }

    URL.revokeObjectURL(url)
  } catch (e) {
    console.error('duration fail', e)
  }
}

const createAudioFile = (mediaData) => {
  const { ext, mimeType } = detectMimeType()

  const base64 = mediaData.split(',')[1]
  const binary = atob(base64)
  const bytes = new Uint8Array(binary.length)

  for (let i = 0; i < binary.length; i++) {
    bytes[i] = binary.charCodeAt(i)
  }

  const blob = new Blob([bytes], { type: mimeType })

  return new File([blob], `record_${Date.now()}.${ext}`, {
    type: mimeType,
  })
}

const getRecordAudioDataUrl = () => {
  return recorder?.getMediaData() || null
}

const uploadRecording = async () => {
  const mediaData = recorder?.getMediaData()
  if (!mediaData) return null

  const file = createAudioFile(mediaData)

  const formData = new FormData()
  formData.append('fileCategory', `record/${props.formTypeCode}`)
  formData.append('file', file)

  try {
    const res = await post('/api/files/local/upload', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })

    recordFileData.value = {
      ...res?.data,
      recordAudioDataUrl: mediaData,
    }

    emit('upload-complete', recordFileData.value)

    return recordFileData.value
  } catch (e) {
    recordFileData.value = null
    emit('upload-fail', e)
    throw e
  }
}

const getRecordFileData = () => {
  return recordFileData.value
}

defineExpose({
  getRecordFileData,
  getRecordAudioDataUrl,
})

onMounted(() => {
  recorder = new WebRecorder({
    audioBitsPerSecond: 24000,
    maxDurationMs: 10 * 60 * 1000,
  })

  recorder.addEventListener('recordtime', (e) => {
    recordTime.value = formatTime(e.detail.elapsedMs)
  })

  recorder.addEventListener('playprogress', (e) => {
    playCurrentTime.value = formatTime(e.detail.currentMs)
    playTotalTime.value = formatTime(e.detail.durationMs)
    playProgress.value = e.detail.ratio * 100
  })

  recorder.addEventListener('playend', () => {
    isPlaying.value = false
    playProgress.value = 100
  })
})

onBeforeUnmount(() => {
  recorder?.dispose?.()
})
</script>

<style scoped>
@import '/eformsign/record/css/record.css';
</style>
