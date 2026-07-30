<template>
  <!-- DOM에 팝업 상태가 유지되도록 keepAlive 설정 추가 -->
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신청서 확인"
    @open="emit('open')"
    @close="onClose"
    keepAlive
    size="xlarge"
    dividerFooter
  >
    <!-- 팝업 내용 -->
    <div class="eformsign-flex-layout">
      <MsfCollapse defaultOpen>
        <template #header>
          <span class="ut-text-separate">안내 녹취</span>
          <span class="ut-text-info">※ 최대 <em>10</em>분</span>
        </template>
        <!-------------------- (eformsign) msf-wr-bar : 녹취,재생 영역 HTML ---------------------->
        <div class="msf-wr-bar">
          <!-- 1. 플래그(녹취 or 재생) 영역 -->
          <div class="msf-wr-top">
            <span v-if="isRecordingMode" class="msf-wr-flag accent2">녹취</span>
            <span v-if="isPlayingMode" class="msf-wr-flag accent">재생</span>
          </div>
          <!-- 2. 녹취 그룹 -->
          <div class="msf-wr-group" v-if="isRecordingMode">
            <!-- 녹취 버튼들 -->
            <div class="msf-wr-btns">
              <button
                v-if="!isRecording"
                class="msf-wr-btn accent2"
                data-act="start"
                title="녹음 시작"
                @click="startRecording"
              >
                <i class="msf-icon play" />
              </button>
              <button
                v-if="isRecording"
                class="msf-wr-btn"
                :class="{ 'is-active': isRecording }"
                data-act="pause"
                title="일시정지"
                @click="isRecording = false"
              >
                <i class="msf-icon pause" />
              </button>
              <button class="msf-wr-btn" data-act="stop" title="중지" @click="onClickStopBtn">
                <i class="msf-icon stop" />
              </button>
            </div>
            <!-- 녹취 시간 -->
            <span class="wr-time" data-el="recTime">00:00</span>
          </div>
          <!-- 3. 재생 그룹 -->
          <div class="msf-wr-group" v-if="isPlayingMode">
            <!-- 재생 버튼들 -->
            <div class="msf-wr-btns">
              <button
                v-if="!isPlaying"
                class="msf-wr-btn accent"
                data-act="play"
                title="재생"
                @click="startPlaying"
              >
                <i class="msf-icon play" />
              </button>
              <button
                v-if="isPlaying"
                class="msf-wr-btn btn-playing"
                :class="{ 'is-active': isPlaying }"
                data-act="pausePlay"
                title="재생 일시정지"
                @click="isPlaying = false"
              >
                <i class="msf-icon pause" />
              </button>
              <button class="msf-wr-btn" title="초기화" @click="resetAll">
                <i class="msf-icon reset" />
              </button>
            </div>
            <!-- 재생 프로그래스바 -->
            <div class="wr-progress"><div class="wr-progress-fill" data-el="progFill"></div></div>
            <!-- 시간 -->
            <span class="wr-play-time" data-el="playTime">00:00 / 00:00</span>
            <!-- 삭제 버튼 -->
            <button class="msf-wr-btn ghost" data-act="clear" title="삭제">
              <i class="msf-icon trash" />
            </button>
          </div>
        </div>
        <!-------------------- // (eformsign) msf-wr-bar : 녹취,재생 영역 HTML ---------------------->
        <!-- <MsfTextarea placeholder="안내 녹취 스크립트" class="ut-mt-8" /> -->
        <!-- 안내 녹취 스크립트 영역 -->
        <div class="record-script-wrap">
          <MsfCustomScroll class="record-script">
            <template v-for="index in 20" :key="index">안내 녹취 스크립트<br /></template>
          </MsfCustomScroll>
        </div>
        <!-- // 안내 녹취 스크립트 영역 -->
      </MsfCollapse>
      <MsfTitleArea title="신청서" level="2" bold noline margin="4" />
      <!-- 신청서 이미지 퍼블 샘플 -->
      <div class="eformsign-container">
        <div class="iframe-sample" style="width: 100%; height: 100%; border: 0; overflow-y: auto">
          <img
            src="@/assets/images/dummy/@sample_form.png"
            alt="신청서 이미지 샘플"
            style="width: 100%"
          />
        </div>
      </div>
      <!-- // 신청서 이미지 퍼블 샘플 -->
      <!-- 개발에서 사용하는 컴퍼넌트-->
      <!-- <div class="eformsign-container">
        <MsfEformImgTest ref="eformImgRef" class="eform-frame" />
      </div> -->
    </div>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary">수정</MsfButton>
        <MsfButton variant="primary" @click="onClose">신청서 제출</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref, computed } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'

// 상수 정의
const VIEW_MODES = {
  DEFAULT: 'default',
  RECORDING: 'recording',
  PLAYING: 'playing',
}

// 화면 상태
const viewMode = ref('default') // view 모드 (default, recording, playing)
const isRecording = ref(false) // 녹취 중인지
const isPlaying = ref(false) // 재생 중인지

// 조건
const isRecordingMode = computed(() =>
  [VIEW_MODES.DEFAULT, VIEW_MODES.RECORDING].includes(viewMode.value),
)
const isPlayingMode = computed(() => viewMode.value === VIEW_MODES.PLAYING)

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// view 모드 전환
const setMode = (mode) => {
  viewMode.value = mode
}

// 녹음 시작 버튼 클릭
const startRecording = () => {
  setMode('recording')
  isRecording.value = true
}

// 재생 시작 버튼 클릭
const startPlaying = () => {
  setMode('playing')
  isPlaying.value = true
}

// 중지 버튼 클릭
const onClickStopBtn = () => {
  showAlert('녹취가 완료되었습니다.')
  isRecording.value = false
  isPlaying.value = false
  setMode('playing') // 재생 모드로 전환
}

// 전체 상태 초기화
const resetAll = () => {
  viewMode.value = 'default'
  isRecording.value = false
  isPlaying.value = false
}
</script>

<style lang="scss" scoped>
/* 실제 개발 화면은 record.css으로 사용됨 */
@import '/eformsign/record/css/record.css';
// =================================================== //
</style>
