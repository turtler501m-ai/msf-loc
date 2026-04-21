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
            {{ doc.name }}
            <MsfFlag v-if="doc.file" data="완료" color="accent2" size="small" />
          </p>
          <MsfStack type="field">
            <img
              v-if="doc.preview"
              :src="doc.preview"
              alt="미리보기"
              class="preview-img"
              @click="openCamera(doc.id)"
            />
            <MsfButton variant="subtle" @click="openCamera(doc.id)">
              {{ doc.file ? '재촬영' : '촬영하기' }}
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
import { ref, computed, watch } from 'vue'

const props = defineProps({
  modelValue: Boolean,
  formData: {
    type: Object,
    default: () => ({}),
  },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const fileInput = ref(null)
const currentDocId = ref(null)

// 모든 가능한 서류 마스터 데이터 (상태 저장용)
const docState = ref({
  family: { name: '가족관계증명서', file: null, preview: null },
  foreigner: { name: '외국인등록증/거소신고증', file: null, preview: null },
  biz: { name: '사업자등록증', file: null, preview: null },
  seal: { name: '법인인감증명서', file: null, preview: null },
  power: { name: '위임장(법인인감 날인 포함)', file: null, preview: null },
  agentId: { name: '대리인 신분증', file: null, preview: null },
  employment: { name: '대리인 재직증명서', file: null, preview: null },
})

// 조건에 따른 노출 서류 목록 계산
const docList = computed(() => {
  const list = []
  const { cstmrTypeCd, cstmrVisitTypeCd, minorAgentNm, productType, joinType } = props.formData

  // 1. 외국인 및 외국인 미성년자: 외국인등록증/거소신고증
  if (['FN', 'FM'].includes(cstmrTypeCd)) {
    list.push({ id: 'foreigner', ...docState.value.foreigner })
  }

  // 미성년자(내국인/외국인 공통) 케이스: 가족관계증명서 (코드 NM, FM)
  if (['NM', 'FM'].includes(cstmrTypeCd)) {
    list.push({ id: 'family', ...docState.value.family })
  }

  // 사업자등록번호가 입력되어 있는지 확인 (신규가입/번호이동, 명의변경 양도/양수)
  const hasBizNo =
    (props.formData.cstmrJuridicalBizNo1 &&
      props.formData.cstmrJuridicalBizNo2 &&
      props.formData.cstmrJuridicalBizNo3) ||
    (props.formData.tr_bizNo1 && props.formData.tr_bizNo2 && props.formData.tr_bizNo3) ||
    (props.formData.te_bizNo1 && props.formData.te_bizNo2 && props.formData.te_bizNo3)

  // 2. 개인사업자/법인/관공서 이거나 사업자등록번호가 입력된 경우: 사업자등록증
  if (['PP', 'JP', 'GO'].includes(cstmrTypeCd) || hasBizNo) {
    list.push({ id: 'biz', ...docState.value.biz })
  }

  // 3. 법인/관공서: 법인인감증명서
  if (['JP', 'GO'].includes(cstmrTypeCd)) {
    list.push({ id: 'seal', ...docState.value.seal })
  }

  // 4. 대리인 방문 시
  if (cstmrVisitTypeCd === 'V2') {
    list.push({ id: 'agentId', ...docState.value.agentId })

    // 법인/관공서 대리인인 경우 추가 서류
    if (['JP', 'GO'].includes(cstmrTypeCd)) {
      list.push({ id: 'power', ...docState.value.power })
      list.push({ id: 'employment', ...docState.value.employment })
    }
  }

  // 5. 미성년자(법정대리인) 케이스 (가입자 정보 등에 법정대리인 정보가 있을 때)
  if (!['NM', 'FM'].includes(cstmrTypeCd) && (minorAgentNm || props.formData.repRelation)) {
    list.push({ id: 'family', ...docState.value.family })
  }

  // 상품(productType)이나 가입유형(joinType)에 따른 특수 로직이 필요한 경우 여기에 추가
  // 예: 번호이동(MNP3)이면서 특정 상품일 때 등...

  return list
})

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
const openCamera = (id) => {
  // 현재 선택된 서류의 ID를 저장 (docList의 index가 아닌 마스터 데이터 ID 사용이 안전)
  currentDocId.value = id
  if (fileInput.value) {
    fileInput.value.click()
  }
}

// 파일(사진) 선택 시 처리
const handleFileChange = (event) => {
  const file = event.target.files[0]
  if (file && currentDocId.value) {
    const doc = docState.value[currentDocId.value]
    doc.file = file

    // 미리보기 URL 생성 (기존 URL이 있으면 해제)
    if (doc.preview) {
      URL.revokeObjectURL(doc.preview)
    }
    doc.preview = URL.createObjectURL(file)
  }
  // 입력 필드 초기화 (같은 파일 재선택 가능하게 함)
  if (fileInput.value) {
    fileInput.value.value = ''
  }
  currentDocId.value = null
}

const onConfirm = () => {
  // 현재 노출된 서류 중 촬영된 파일 목록 추출
  const completedDocs = docList.value
    .filter((doc) => doc.file)
    .map((doc) => ({ name: doc.name, file: doc.file }))

  // 모든 필수 서류가 촬영되었는지 여부
  const isAllUploaded = docList.value.length > 0 && docList.value.every((doc) => doc.file !== null)

  emit('confirm', { completedDocs, isAllUploaded })
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
