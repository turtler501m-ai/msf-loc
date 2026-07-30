<template>
  <div v-if="hasRequiredDocs">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="구비서류" tag="div" :required="requiredDocsCount > 0">
        <MsfStack type="field" align="center">
          <MsfButton
            ref="scanRequiredDocsBtn"
            variant="subtle"
            :disabled="props.disabled"
            @click="isModalOpen = true"
            >스캔하기</MsfButton
          >
          <MsfFlag v-if="isDocComplete" data="완료" color="accent2" size="small" />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>

    <!-- 구비서류 모달 -->
    <MsfRequiredDocModal
      v-model="isModalOpen"
      :form-data="model"
      :required-docs-list="requiredDocs"
      :file-category="fileCategory"
      @confirm="onConfirm"
    />
  </div>
</template>

<script setup>
import { computed, defineExpose, defineModel, defineProps, ref, watch } from 'vue'
import MsfRequiredDocModal from './popups/MsfRequiredDocModal.vue'
import { getCommonCodeListWithDetail, getFormTypeCode } from '@/libs/utils/comn.utils'
import { showAlert, showConfirm, showConfirmAsync } from '@/libs/utils/comp.utils'
import { useRoute } from 'vue-router'

const route = useRoute()

const props = defineProps({
  title: { type: String, default: '구비서류' },
  authFlags: { type: Object, default: () => null },
  // 임시저장 불러오기 완료 등 외부에서 구비서류 재조회를 강제 트리거할 때 사용
  // 부모에서 숫자 혹은 고유값을 증가시키면 재조회됨
  refreshKey: { type: [Number, String], default: 0 },
  disabled: Boolean,
})
const model = defineModel({ type: Object, required: true })
const isAllUploadedModel = defineModel('isAllUploaded', { type: Boolean, default: false })

const scanRequiredDocsBtn = ref(null)

const requiredDocsCount = computed(() => {
  return requiredDocs.value.filter((doc) => doc.etcValue1 === 'Y').length
})

const isDocComplete = computed(() => {
  if (!hasRequiredDocs.value) return false

  const hasRequiredItems = requiredDocs.value.some((doc) => doc.etcValue1 === 'Y')
  if (!hasRequiredItems) return false

  return validate()
})

const emit = defineEmits(['change', 'update:authFlags'])
const isModalOpen = ref(false)
const requiredDocs = ref([])

const checkBizNoLength = (b1, b2, b3) => {
  return (
    String(b1 || '').length === 3 && String(b2 || '').length === 2 && String(b3 || '').length === 5
  )
}

// 사업자번호가 3-2-5 자리 모두 입력되었는지 여부
const hasFullBizNo = computed(() => {
  if (!model.value) return false
  return (
    checkBizNoLength(
      model.value.cstmrJuridicalBizNo1,
      model.value.cstmrJuridicalBizNo2,
      model.value.cstmrJuridicalBizNo3,
    ) ||
    checkBizNoLength(
      model.value.cstmrPrivateBizNo1,
      model.value.cstmrPrivateBizNo2,
      model.value.cstmrPrivateBizNo3,
    ) ||
    checkBizNoLength(model.value.tr_bizNo1, model.value.tr_bizNo2, model.value.tr_bizNo3) ||
    checkBizNoLength(model.value.te_bizNo1, model.value.te_bizNo2, model.value.te_bizNo3) ||
    (model.value.tr_customer &&
      checkBizNoLength(
        model.value.tr_customer.cstmrJuridicalBizNo1,
        model.value.tr_customer.cstmrJuridicalBizNo2,
        model.value.tr_customer.cstmrJuridicalBizNo3,
      )) ||
    (model.value.te_customer &&
      checkBizNoLength(
        model.value.te_customer.cstmrJuridicalBizNo1,
        model.value.te_customer.cstmrJuridicalBizNo2,
        model.value.te_customer.cstmrJuridicalBizNo3,
      ))
  )
})

/**
 * 폼 유형 코드 매핑 (newchange: 1, servicechange: 2, ownerchange: 3, termination: 4)
 */
const getFormTypeCd = () => {
  return getFormTypeCode(route.path)
}

const fileCategory = computed(() => {
  const typeCd = getFormTypeCd()
  if (typeCd === '2') return 'servicechange'
  if (typeCd === '3') return 'ownerchange'
  if (typeCd === '4') return 'termination'
  return 'newchange'
})

/**
 * 구비서류 목록 동적 조회
 */
const fetchRequiredDocs = async () => {
  if (!model.value.cstmrTypeCd || !model.value.cstmrVisitTypeCd) {
    requiredDocs.value = []
    return
  }

  const typeCd = getFormTypeCd()
  const custType = model.value.cstmrTypeCd
  const isJuridical = ['JP', 'GO'].includes(model.value.cstmrTypeCd)
  const pp = !isJuridical && hasFullBizNo.value ? '_PP' : ''
  const visitType = model.value.cstmrVisitTypeCd

  // 그룹 ID 생성: FORM_DOC_ + FORM_TYPE_CD + _ + 고객유형 + (_PP) + _ + 방문유형
  const groupId = `FORM_DOC_${typeCd}_${custType}${pp}_${visitType}`

  try {
    console.log(`>>> [MsfRequiredDoc] Fetching docs for Group ID: ${groupId}`)
    const list = await getCommonCodeListWithDetail(groupId)
    requiredDocs.value = (list || []).map((item) => ({
      id: item.code,
      name: item.title,
      etcValue1: item.detail?.etcValue1 || '', //  detail 객체 내부 속성에서 파싱
      etcValue2: item.detail?.etcValue2 || '', //  detail 객체 내부 속성에서 파싱
    }))
  } catch (e) {
    console.error(`Failed to fetch docs for ${groupId}:`, e)
    requiredDocs.value = []
  }
}

// 주요 정보 변경 시 구비서류 재조회
watch(
  () => [
    model.value.cstmrTypeCd,
    model.value.cstmrVisitTypeCd,
    hasFullBizNo.value,
    model.value.formType,
  ],
  () => {
    fetchRequiredDocs()
  },
  { immediate: true },
)

// refreshKey 변경 시 강제 재조회
// (cstmrTypeCd/cstmrVisitTypeCd가 초기값과 동일한 경우 watch가 재실행되지 않는 타이밍 버그 방어)
watch(
  () => props.refreshKey,
  () => {
    fetchRequiredDocs()
  },
)

const hasRequiredDocs = computed(() => {
  return requiredDocs.value.length > 0
})

const onConfirm = ({ completedDocs, isAllUploaded }) => {
  // 스토어의 인증 플래그 업데이트
  if (props.authFlags) {
    emit('update:authFlags', {
      ...props.authFlags,
      requiredDocs: isAllUploaded,
    })
  }

  isAllUploadedModel.value = isAllUploaded
  emit('change', isAllUploaded)

  // 업로드된 파일 정보를 모델에 저장 (필요 시)
  model.value.uploadedDocs = completedDocs

  // 백엔드 DTO(ListMsfRequestDocDto) 형태에 맞춰 평탄화된 파일 목록 생성 및 저장
  const msfRequestDocList = []
  completedDocs.forEach((doc) => {
    if (doc.files && Array.isArray(doc.files)) {
      doc.files.forEach((file) => {
        msfRequestDocList.push({
          fileTypeCd:
            file.fileTypeCd && file.fileTypeCd !== 'fallback'
              ? file.fileTypeCd
              : fileCategory.value,
          filePathNm: file.filePathNm || '',
          fileNm: file.fileNm || '',
          filePageNo: file.fileNum !== undefined ? Number(file.fileNum) : 1,
          previewUrl: file.previewUrl || '',
          maskImageFile: file.maskImageFile || '',
        })
      })
    }
  })

  model.value.msfRequestDocList = msfRequestDocList

  console.log('구비서류 확인 완료:', isAllUploaded ? '모두 완료' : '일부 누락')
}

// 외부(부모)에서 호출할 수 있는 유효성 검사 함수
const validate = () => {
  if (hasRequiredDocs.value) {
    // etcValue1 === 'Y' 인 필수 서류들만 필터링
    const requiredItems = requiredDocs.value.filter((doc) => doc.etcValue1 === 'Y')
    if (requiredItems.length === 0) {
      return true
    }

    // 업로드 완료된 fileTypeCd 목록 추출
    const uploadedTypes = (model.value.msfRequestDocList || []).map((doc) => doc.fileTypeCd)

    // 모든 필수 서류 코드가 업로드된 목록에 존재하는지 대조
    return requiredItems.every((item) => uploadedTypes.includes(item.id))
  }
  return true
}

const checkValidation = async () => {
  if (!validate()) {
    const proceed = await showConfirmAsync(
      `구비서류가 미등록 되었습니다.`,
      "개통 후 추가 등록하시려면 '확인'을 누르세요.",
    )
    if (proceed) {
      return true
    } else {
      scanRequiredDocsBtn.value?.focus()
      return false
    }
  }
  return true
}

defineExpose({ validate, fetchRequiredDocs, checkValidation })
</script>
