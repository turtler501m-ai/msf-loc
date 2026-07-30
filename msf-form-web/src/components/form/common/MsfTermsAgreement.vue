<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="" tag="div" required>
        <!-- 동적으로 약관 목록을 렌더링 -->
        <MsfAgreementGroup
          :policy="props.policy"
          ref="agreementRef"
          :specTerms="props.specTerms"
          v-model="model.termsAgreed"
          :description="description"
          :required="required"
          :only-required="true"
          @checked="handleChecked"
          :disabled="props.disabled"
        />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>

<script setup>
import { ref, defineModel, defineProps, watch } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  title: { type: String, default: '약관 동의' },
  description: { type: String, default: '' },
  required: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
  isSaved: { type: Boolean, default: false },
  policy: { type: String, default: 'CLAUSE_FORM_01' },
  specTerms: {
    type: Array,
    default: () => [
      // FIXME: termsData를 통해 받아오는 약관 코드 목록의 구조 변경 및 로직 변경 필요. 현재는 단순히 code만 전달받는 형태로 가정하고 있음. (예시 데이터)
      { code: 'CLAUSE_MOVE_01' },
    ],
  }, // 동적으로 약관 데이터를 받을 prop
  termsData: { type: Array, default: () => [] },
})

const model = defineModel({ type: Object, required: true })
const emit = defineEmits(['checked'])
const agreementRef = ref(null)
const lastCheckedResult = ref([])

// 필수 여부 확인 유틸
const isRequiredField = (val) => val === true || val === '2' || val === 'Y'
// 체크 여부 확인 유틸
const isCheckedField = (val) => val === true || val === 'Y'

const handleChecked = (result) => {
  lastCheckedResult.value = result

  // result가 있으면 (약관 동의 이벤트 등) model 객체에 값 반영
  if (result && Array.isArray(result)) {
    result.forEach((item) => {
      const code = item.code
      const termData = props.termsData?.find((t) => t.code === code)
      const targetKey = termData?.id || code

      // 만약 model 객체에 해당 targetKey와 일치하는 키가 있다면 값 업데이트
      if (Object.prototype.hasOwnProperty.call(model.value, targetKey)) {
        if (typeof model.value[targetKey] === 'boolean') {
          model.value[targetKey] = item.checked
        } else {
          model.value[targetKey] = item.checked ? 'Y' : 'N'
        }
      }
    })
  }

  emit('checked', result)
}

// termsData의 checked 값이 변경될 때, model.termsAgreed 동기화
watch(
  () => props.termsData,
  (newTermsData) => {
    if (newTermsData && newTermsData.length > 0) {
      lastCheckedResult.value = newTermsData
    }
  },
  { deep: true, immediate: true },
)

const validate = () => {
  const targetData = lastCheckedResult.value.length > 0 ? lastCheckedResult.value : props.termsData

  if (!targetData || targetData.length === 0) {
    return !props.required
  }

  // 필수 조건(true, '2' 또는 'Y')인 row는 'Y' 또는 true 여야 함
  const isValid = targetData.every((term) => {
    if (isRequiredField(term.required)) {
      return isCheckedField(term.checked)
    }
    return true
  })

  return isValid
}

const setAllChecked = () => {
  if (!props.specTerms?.length) return
  const allChecked = props.specTerms.map((term) => ({
    code: term.code || term.value,
    required: term.required,
    checked: true,
  }))
  handleChecked(allChecked)
}

const reset = () => {
  lastCheckedResult.value = []
  model.value.termsAgreed = false
}

const checkValidation = () => {
  if (!validate()) {
    showAlert(`${props.title}가 필요합니다`, () => {
      agreementRef.value?.focus()
    })
    return false
  }
  return true
}

defineExpose({ validate, setAllChecked, reset, checkValidation })
</script>
