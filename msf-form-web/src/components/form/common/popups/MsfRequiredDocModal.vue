<template>
  <MsfDialog v-bind="$attrs" :is-open="modelValue" title="구비서류" @open="onOpen" @close="onClose">
    <!-- 팝업 내용 -->
    <p class="ut-text-caution ut-weight-medium">
      촬영 버튼을 선택하신 후, 구비서류를 촬영해주세요.
    </p>
    <div class="doc-list-wrap">
      <ul class="doc-list">
        <li v-for="doc in docList" :key="doc.id">
          <p>
            <em v-if="doc.etcValue1 === 'Y'" class="required-text">[필수]</em>
            <em v-else class="optional-text">[선택]</em>
            {{ doc.name }}
            {{ doc.etcValue2 ? doc.etcValue2 : '' }}
            <MsfFlag
              v-if="doc.files && doc.files.length > 0"
              data="완료"
              color="accent2"
              size="small"
            />
          </p>
          <MsfStack type="field" align="center">
            <!-- 미니뷰 컴포넌트 (미리보기 n개 표시) -->
            <div v-if="doc.files && doc.files.length > 0" class="mini-view-container">
              <img
                v-for="(file, idx) in doc.files"
                :key="idx"
                :src="file.previewUrl"
                alt="미리보기"
                class="mini-preview-img"
                @click="openImagePreview(file.previewUrl)"
              />
            </div>

            <!-- 촬영 / 재촬영 버튼 -->
            <MsfButton variant="subtle" @click="openCaptureModal(doc.id)">
              {{ doc.files && doc.files.length > 0 ? '재촬영' : '촬영하기' }}
            </MsfButton>
          </MsfStack>
        </li>
      </ul>
    </div>

    <!-- 구비서류 촬영 모달 -->
    <MsfDocCaptureModal
      :key="docCaptureKey"
      ref="docCaptureRef"
      v-model="isDocCaptureModalOpen"
      :file-category="fileCategory"
      :docId="currentDocId"
      @confirm="handleDocConfirm"
      @close="handleDocCaptureClose"
    />

    <!-- 이미지 전체화면 미리보기 모달 -->
    <MsfImagePreviewModal v-model="isImagePreviewOpen" :image-url="previewImageUrl" />

    <!-- 숨겨진 파일 입력 (예비 카메라 호출용) -->
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
import { ref, computed, nextTick } from 'vue'
import MsfDocCaptureModal from '@/components/form/common/popups/MsfDocCaptureModal.vue'
import MsfImagePreviewModal from '@/components/form/common/popups/MsfImagePreviewModal.vue'

const props = defineProps({
  modelValue: Boolean,
  formData: {
    type: Object,
    default: () => ({}),
  },
  requiredDocsList: {
    type: Array,
    default: () => [],
  },
  fileCategory: {
    type: String,
    default: '',
  },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const fileInput = ref(null)
const docCaptureRef = ref(null)
const docCaptureKey = ref(0)
const currentDocId = ref(null)
const isDocCaptureModalOpen = ref(false)

// 이미지 전체화면 미리보기 팝업 상태
const isImagePreviewOpen = ref(false)
const previewImageUrl = ref('')

// 구비서류별 파일/미리보기 상태 관리 (ID를 키로 사용, 다중 파일 지원)
const docState = ref({})

const isApp = computed(() => {
  const deviceType = localStorage.getItem('deviceType')
  return deviceType === 'A' || deviceType === 'I'
})

// prop으로 받은 목록을 기반으로 현재 노출할 서류 목록 계산 및 상태 초기화
const docList = computed(() => {
  return props.requiredDocsList.map((doc) => {
    if (!docState.value[doc.id]) {
      docState.value[doc.id] = { files: [] }
    }
    return {
      ...doc,
      files: docState.value[doc.id].files || [],
    }
  })
})

const onOpen = () => {
  if (props.formData?.msfRequestDocList && Array.isArray(props.formData.msfRequestDocList)) {
    const newState = { ...docState.value }
    props.formData.msfRequestDocList.forEach((doc) => {
      if (doc.fileTypeCd) {
        newState[doc.fileTypeCd] = {
          files: [
            {
              fileTypeCd: doc.fileTypeCd,
              fileNm: doc.fileNm || '',
              filePathNm: doc.filePathNm || '',
              fileNum: doc.filePageNo || 1,
              previewUrl: doc.previewUrl || doc.maskImageFile || doc.filePathNm || '',
              maskImageFile: doc.maskImageFile || '',
            },
          ],
        }
      }
    })
    docState.value = newState
  }
  emit('open')
}

// 닫힘 이벤트
const onClose = () => {
  isDocCaptureModalOpen.value = false
  currentDocId.value = null
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// 촬영 버튼 클릭 시 촬영 모달 활성화
const openCaptureModal = async (id) => {
  currentDocId.value = id

  // APP이면 자식 Dialog를 열지 않고 브릿지 카메라만 실행
  // 매번 key를 증가시켜 브릿지 컴포넌트를 재생성해야 X로 닫은 뒤 재촬영이 가능함
  if (isApp.value) {
    isDocCaptureModalOpen.value = false
    docCaptureKey.value += 1
    await nextTick()
    docCaptureRef.value?.startCameraBridge?.()
    return
  }

  // PC / WEB만 자식 OCR 모달 오픈
  isDocCaptureModalOpen.value = true
}

const handleDocCaptureClose = () => {
  isDocCaptureModalOpen.value = false
  currentDocId.value = null

  if (isApp.value) {
    docCaptureKey.value += 1
  }
}

// 촬영 결과 콜백 처리
const handleDocConfirm = (result) => {
  console.log('OCR CONFIRM RESULT:', result)

  if (result?.fileList?.length > 0 && currentDocId.value) {
    docState.value[currentDocId.value] = {
      files: result.fileList,
    }
  }

  isDocCaptureModalOpen.value = false
  currentDocId.value = null

  if (isApp.value) {
    docCaptureKey.value += 1
  }
}

// 이미지 미리보기 팝업 트리거
const openImagePreview = (url) => {
  previewImageUrl.value = url
  isImagePreviewOpen.value = true
}

// 예비 카메라 이미지 처리
const handleFileChange = (event) => {
  const file = event.target.files[0]
  if (file && currentDocId.value) {
    if (!docState.value[currentDocId.value]) {
      docState.value[currentDocId.value] = { files: [] }
    }

    const previewUrl = URL.createObjectURL(file)

    docState.value[currentDocId.value].files = [
      {
        fileTypeCd: currentDocId.value,
        fileNm: file.name,
        filePathNm: '',
        fileNum: 1,
        previewUrl,
        fileRaw: file,
      },
    ]
  }
  if (fileInput.value) {
    fileInput.value.value = ''
  }
  currentDocId.value = null
}

const onConfirm = () => {
  const completedDocs = docList.value
    .filter((doc) => doc.files && doc.files.length > 0)
    .map((doc) => ({
      name: doc.name,
      files: doc.files,
      id: doc.id,
    }))

  // etcValue1 === 'Y' 인 필수 서류들만 체크
  const requiredItems = docList.value.filter((doc) => doc.etcValue1 === 'Y')
  const isAllUploaded =
    requiredItems.length > 0
      ? requiredItems.every((doc) => doc.files && doc.files.length > 0)
      : true

  emit('confirm', { completedDocs, isAllUploaded })
  onClose()
}
</script>

<style lang="scss" scoped>
.mini-view-container {
  display: flex;
  gap: rem(6px);
  margin-right: rem(8px);
}

.mini-preview-img {
  width: rem(40px);
  height: rem(40px);
  object-fit: cover;
  border-radius: rem(6px);
  border: 1px solid var(--border-color, #e2e8f0);
  cursor: pointer;
  transition:
    transform 0.2s ease-in-out,
    box-shadow 0.2s ease-in-out;

  &:hover {
    transform: scale(1.08);
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
  }
}
</style>
