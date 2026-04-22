<template>
  <div class="file-button-root">
    <input
      :id="inputId"
      ref="fileRef"
      type="file"
      class="ut-blind"
      :accept="accept"
      :multiple="multiple"
      @change="handleFileChange"
    />
    <div class="file-control" :class="{ 'is-disabled': disabled }">
      <MsfButton
        prefixIcon="upload"
        class="select-btn"
        :disabled="disabled"
        @click="openFilePicker"
      >
        {{ btnLabel }}
      </MsfButton>
    </div>
  </div>
</template>

<script setup>
import { ref, useId } from 'vue'

const props = defineProps({
  modelValue: { type: [File, Array, null], default: null },
  /** 외부에서 주입받는 ID (없으면 useId로 자동 생성) */
  id: { type: String, default: null },
  /** 버튼 레이블 */
  btnLabel: { type: String, default: '엑셀업로드' },
  /** 허용 파일 */
  accept: { type: String, default: '*' },
  /** 여러개 선택 가능여부(필요하다면) */
  multiple: { type: Boolean, default: false },
  /** 비활성화 */
  disabled: { type: Boolean, default: false },
})

const emit = defineEmits(['update:modelValue', 'change', 'clear'])

// 외부 id가 있으면 그것을 쓰고, 없으면 고유한 ID를 생성
const inputId = props.id || useId()

const fileRef = ref(null)

// 파일 탐색기 열기
const openFilePicker = () => {
  if (props.disabled) return
  fileRef.value?.click()
}

// 파일 선택 핸들러
const handleFileChange = (e) => {
  const selectedFiles = Array.from(e.target.files)
  if (selectedFiles.length === 0) return

  let result

  if (props.multiple) {
    // 기존에 파일이 있었다면 합치고, 없었다면 새로 생성
    const currentFiles = Array.isArray(props.modelValue) ? props.modelValue : []
    result = [...currentFiles, ...selectedFiles]
  } else {
    // 단일 선택 모드
    result = selectedFiles[0]
  }

  emit('update:modelValue', result)
  emit('change', result)

  // 같은 파일을 다시 선택할 수 있도록 input 초기화
  e.target.value = ''
}
</script>

<style lang="scss" scoped>
.file-button-root {
  position: relative;
}
.is-hidden {
  display: none !important;
}
.file-control {
  @include flex($v: stretch) {
    gap: rem(4px);
  }
  flex: 1;
  overflow: hidden;
  &.is-disabled {
    cursor: not-allowed;
  }
  .file-name-display {
    flex: 1;
    cursor: pointer;
    display: flex; // Input 높이를 꽉 채우기 위함
  }
  .select-btn {
    white-space: nowrap;
  }
}
</style>
