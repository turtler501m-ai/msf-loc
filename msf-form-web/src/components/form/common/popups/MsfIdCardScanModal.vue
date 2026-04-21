<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신분증 스캔"
    @open="onOpen"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <p class="ut-text-caution ut-weight-medium">촬영 버튼을 선택하신 후, 신분증을 촬영해주세요.</p>
    <div class="doc-list-wrap">
      <ul class="doc-list">
        <li>
          <p>
            신분증 원본
            <MsfFlag v-if="docFile" data="완료" color="accent2" size="small" />
          </p>
          <MsfStack type="field">
            <img
              v-if="previewUrl"
              :src="previewUrl"
              alt="미리보기"
              class="preview-img"
              @click="openCamera"
            />
            <MsfButton variant="subtle" @click="openCamera">
              {{ docFile ? '재촬영' : '촬영하기' }}
            </MsfButton>
          </MsfStack>
        </li>
      </ul>
    </div>

    <!-- 숨겨진 파일 입력 (카메라 호출용) -->
    <input
      type="file"
      ref="fileInput"
      accept="image/*"
      capture="environment"
      style="display: none"
      @change="handleFileChange"
    />

    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const fileInput = ref(null)
const docFile = ref(null)
const previewUrl = ref(null)

const onOpen = () => {
  emit('open')
}

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// 촬영 버튼 클릭 시 카메라(파일 입력) 호출
const openCamera = () => {
  if (fileInput.value) {
    fileInput.value.click()
  }
}

// 파일(사진) 선택 시 처리
const handleFileChange = (event) => {
  const file = event.target.files[0]
  if (file) {
    docFile.value = file

    // 미리보기 URL 생성 (기존 URL이 있으면 해제)
    if (previewUrl.value) {
      URL.revokeObjectURL(previewUrl.value)
    }
    previewUrl.value = URL.createObjectURL(file)
  }
  // 입력 필드 초기화 (같은 파일 재선택 가능하게 함)
  if (fileInput.value) {
    fileInput.value.value = ''
  }
}

const onConfirm = () => {
  // 촬영된 파일 전송
  emit('confirm', docFile.value)
  onClose()
}
</script>

<style lang="scss" scoped>
.doc-list-wrap {
  width: 100%;
  margin-top: rem(16px);
  border-top: var(--border-width-base) solid var(--color-gray-150);
  border-bottom: var(--border-width-base) solid var(--color-gray-75);
  .doc-list {
    & > li {
      padding-block: rem(16px);
      padding-inline: rem(24px);
      @include flex($v: center, $h: space-between) {
        gap: rem(16px);
      }
      border-top: var(--border-width-base) solid var(--color-gray-75);
      & > p:first-child {
        flex: 1;
        @include flex($v: center, $w: wrap) {
          gap: rem(4px);
        }
      }
      & > :last-child {
        flex-shrink: 0;
        flex-grow: 0;
      }
      .preview-img {
        width: rem(40px);
        height: rem(40px);
        object-fit: cover;
        border-radius: var(--border-radius-base);
        border: var(--border-width-base) solid var(--color-gray-150);
        cursor: pointer;
      }
    }
  }
}
</style>
