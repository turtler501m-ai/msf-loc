<!-- 신청서 확인 타이틀 + (메시지 + 박스영역) -->
<template>
  <div>
    <MsfTitleArea title="신청서 확인" />
    <p class="ut-text-desc">{{ guideText }}</p>
    <MsfBox variant="outline">
      <div class="inner-info-wrap">
        <img
          src="@/assets/images/applyConfirmIcon.svg"
          alt="신분증 스캔 가이드: 빛 반사에 주의하세요."
          class="info-img"
        />
        <div class="info-msg">
          <strong class="info-tit">
            <slot name="title">
              <span v-html="props.title"></span>
            </slot>
          </strong>
          <span class="info-tit-desc" v-html="props.description"></span>
        </div>
      </div>
      <MsfButtonGroup
        align="center"
        margin="2"
        class="app-confirm-btn-group"
        :class="{ 'is-disabled': disabled }"
        @click="onClickBtn"
      >
        <MsfButton ref="appConfirmBtnRef" variant="subtle" :disabled="disabled">{{
          props.btnText
        }}</MsfButton>
      </MsfButtonGroup>
    </MsfBox>

    <MsfAppConfirmModal
      ref="modalRef"
      v-model="isModalOpen"
      :form-type-code="props.formTypeCode"
      :request-key="props.requestKey"
      :cstmr-nm="props.cstmrNm"
      :phone-no="props.phoneNo"
      :form-parameters="normalizedFormParameters"
      :device-os="deviceOs"
      :use-new-change-template="props.useNewChangeTemplate"
      :transcription-script-data="props.transcriptionScriptData"
      :defer-upload="isServiceChangeDeferUpload"
      :edit-disabled="serviceChangeEditDisabled"
      :success-only-review="props.successOnlyReview"
      @confirm="onConfirm"
      @edit="onEdit"
      @extract-complete="onExtractComplete"
      @close="onClose"
    />
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import MsfAppConfirmModal from './popups/MsfAppConfirmModal.vue'

const emit = defineEmits(['confirm', 'edit', 'click', 'extract-complete', 'close'])

const props = defineProps({
  title: {
    type: String,
    default: '서명 후,<br/>작성한 내용이 변경된 경우',
  },
  description: {
    type: String,
    default: '서명정보를 변경하여 작성하여 주시기 바랍니다.',
  },
  guideText: {
    type: String,
    default:
      '신청서 확인을 눌러서, 신청서 작성 내용을 확인하신 후 판매자(상담사) 성명, 가입자(대리인) 서명을 등록해 주세요.',
  },
  disabled: { type: Boolean, default: false },
  formTypeCode: { type: String, default: '' },
  requestKey: { type: String, default: '' },
  cstmrNm: { type: String, default: '' },
  phoneNo: { type: String, default: '' },
  formParameters: { type: [Array, Object], default: () => [] },
  useNewChangeTemplate: { type: Boolean, default: false },
  deviceOs: { type: String, default: '' },
  btnText: { type: String, default: '신청서 확인' },
  deferUpload: { type: Boolean, default: false },
  editDisabled: { type: Boolean, default: false },
  successOnlyReview: { type: Boolean, default: false },
  transcriptionScriptData: {
    type: Object,
    default: () => ({
      tgtType: '', // mnp3 | nac3 | hdn3 | icn3
      reqBuyTypeCd: '', // MM | UU
      cstmrVisitTypeCd: 'VMY', // VMY | VCD | VDP
      enggYn: 'N', // 약정/할부 하나라도 있으면 Y
      sprtTypeCd: '', // KD | PM
      rmndYn: 'N', // 잔여할부금 여부
      rateYn: 'N', // 요금제 선택 여부
      insrYn: 'N', // 단말보험 가입 여부
      addYn: 'N', // 부가서비스 가입 여부
    }),
  },
})

const isModalOpen = ref(false)
const isConfirmed = ref(false)
const modalRef = ref(null)

const appConfirmBtnRef = ref(null)

const isServiceChangeDeferUpload = computed(
  () => props.formTypeCode === 'servicechange' && props.deferUpload,
)
const serviceChangeEditDisabled = computed(
  () => props.formTypeCode === 'servicechange' && props.editDisabled,
)

const onClickBtn = (e) => {
  emit('click', e)
  if (!props.disabled && !e.defaultPrevented) {
    isModalOpen.value = true
  }
}

const onConfirm = (result) => {
  isConfirmed.value = true
  emit('confirm', result)
}

const onEdit = () => {
  emit('edit')
}

const onExtractComplete = (payload) => {
  emit('extract-complete', payload)
}

const onClose = () => {
  emit('close')
}

const validate = () => {
  return isConfirmed.value
}

const FORM_PARAMETER_GROUP_KEYS = [
  'newchange',
  'ownerchange',
  'termination',
  'servicechange',
  'ios',
  'android',
]

const isGroupedFormParameters = (value) => {
  if (!value || typeof value !== 'object' || Array.isArray(value)) return false

  return FORM_PARAMETER_GROUP_KEYS.some((key) => Array.isArray(value[key]))
}

const normalizedFormParameters = computed(() => {
  if (Array.isArray(props.formParameters)) {
    return props.formParameters
  }

  if (isGroupedFormParameters(props.formParameters)) {
    return props.formParameters
  }

  if (props.formParameters && typeof props.formParameters === 'object') {
    return [
      {
        name: 'jsondata',
        value: JSON.stringify(props.formParameters),
      },
    ]
  }

  return []
})

const open = () => {
  isModalOpen.value = true
}

// 서비스변경 전용: 작성완료 성공 후 부모에서 호출
const uploadDeferred = () => {
  if (!isServiceChangeDeferUpload.value) return undefined
  return modalRef.value?.uploadDeferred?.()
}

const openDeferredReview = () => {
  if (!isServiceChangeDeferUpload.value) return false
  const canOpen = modalRef.value?.openDeferredReview?.()
  if (canOpen) {
    open()
  }
  return canOpen
}

defineExpose({
  validate,
  open,
  uploadDeferred,
  openDeferredReview,
  focus: () => {
    appConfirmBtnRef.value?.focus()
  },
})
</script>

<style lang="scss" scoped>
.app-confirm-btn-group.is-disabled :deep(button:disabled) {
  pointer-events: none;
}
</style>
