<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="비밀번호 입력"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <!-- 팝업 내용 -->
    <p class="ut-text-title3">고객 식별을 위한 비밀번호를 입력해 주세요.</p>
    <MsfBox>
      <MsfTextList type="none">
        <li>개인 고객<span class="ut-colon">:</span>생년월일(YYYYMMDD) 8자리</li>
        <li>
          <div class="ut-d-flex ut-ai-baseline">
            <div class="ut-flex-shrink-0">법인 및 공공기관 고객<span class="ut-colon">:</span></div>
            <div class="ut-flex-1">
              사업자번호 10자리
              <p class="ut-text-body2 ut-mt-2">(사업자번호 없는 경우 법인번호 앞 6자리)</p>
            </div>
          </div>
        </li>
      </MsfTextList>
      <MsfNumberInput
        v-model="passwordValue"
        :maxlength="10"
        :display-mask="true"
        placeholder="비밀번호 입력"
        class="ut-mt-16"
      />
    </MsfBox>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>

  <MsfAppViewerModal v-model="isModalOpen" :document-id="documentIds" />
</template>

<script setup>
import { computed, ref } from 'vue'
import { post } from '@/libs/api/msf.api.js'
import { showAlert } from '@/libs/utils/comp.utils.js'

const passwordValue = ref('')
const isModalOpen = ref(false)

const props = defineProps({
  modelValue: Boolean,
  requestKey: { type: String, default: '' },
  formType: { type: String, default: '' },
  documentId: {
    type: [String, Array],
    default: () => [],
  },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const onClose = () => {
  passwordValue.value = ''

  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const documentIds = computed(() => {
  if (!props.documentId) return []

  return Array.isArray(props.documentId) ? props.documentId : [props.documentId]
})

const validatePassword = () => {
  if (!passwordValue.value?.trim()) {
    showAlert('비밀번호를 입력해주세요.')
    return false
  }

  return true
}

const onConfirm = async () => {
  if (!validatePassword()) {
    return
  }

  try {
    const res = await post('/api/form/common/verifyFormPw/get', {
      requestKey: props.requestKey,
      formType: props.formType,
      password: passwordValue.value,
    })

    const result = res.data

    if (result?.success === true) {
      onPass()
      return
    }

    showAlert('비밀번호가 일치하지 않습니다.')
  } catch (e) {
    console.error('비밀번호 검증 실패', e)
    showAlert('비밀번호 검증 중 오류가 발생했습니다.')
  }
}

const onPass = () => {
  emit('confirm', passwordValue.value)

  onClose()
  isModalOpen.value = true
}
</script>

<style lang="scss" scoped></style>
