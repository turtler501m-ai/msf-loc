<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="고객 안내 사항" tag="div" required>
        <!-- 동적으로 약관 목록을 렌더링 -->
        <MsfAgreementGroup
          policy="CLAUSE_FORM_01"
          ref="agreementRef"
          :showTerms="props.termsData"
          v-model="model.termsAgreed"
          :description="description"
          :required="required"
          @checked="(result) => console.log('result:', result)"
        />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>

<script setup>
import { ref, defineModel, defineProps, watch } from 'vue'

const props = defineProps({
  title: { type: String, default: '약관 동의' },
  description: { type: String, default: '' },
  required: { type: Boolean, default: false },
  termsData: { type: Array, default: () => [] }, // 동적으로 약관 데이터를 받을 prop
})

const model = defineModel({ type: Object, required: true })
const agreementRef = ref(null)

// termsData의 checked 값이 변경될 때, model.termsAgreed 동기화
watch(
  () => props.termsData,
  (newTermsData) => {
    if (newTermsData) {
      // 약관 데이터가 제공되면, 해당 데이터의 checked 상태를 model에 반영
      // TODO: 중첩된 children 구조까지 처리하는 로직이 필요할 수 있음
      // 현재는 가장 상위 level의 checked만 반영
      const allChecked = newTermsData.every((term) => term.checked)
      model.value.termsAgreed = allChecked
    }
  },
  { deep: true, immediate: true },
)

const validate = () => {
  if (props.required && !model.value.termsAgreed) return false
  return true
}

defineExpose({ validate })
</script>
