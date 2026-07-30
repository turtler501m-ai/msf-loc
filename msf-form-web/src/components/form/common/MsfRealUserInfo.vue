<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="실사용자 이름" required>
        <MsfInput
          ref="realUserNameRef"
          v-model="model.realUserName"
          placeholder="이름"
          class="ut-w-300"
          maxlength="100"
          :readonly="props.disabled"
        />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            ref="realUserBirthDateRef"
            v-model="model.realUserBirthDate"
            length="8"
            class="ut-w-300"
            :readonly="props.disabled"
          />
          <MsfRadioGroup
            ref="userGenderRef"
            v-if="!['JP', 'GO'].includes(model.cstmrTypeCd)"
            :name="`${name}-user-gender`"
            v-model="model.userGender"
            :options="[
              { value: 'M', label: '남' },
              { value: 'F', label: '여' },
            ]"
            class="ut-ml-16"
            :disabled="props.disabled"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup
        v-if="
          model.canBulkCorporateOpenYn === 'Y' &&
          model.cstmrTypeCd === 'JP' &&
          model.joinType === 'NAC3'
        "
        label="개통회선수"
        required
      >
        <MsfNumberInput
          v-model="model.bulkActivationCnt"
          placeholder="개통회선수 입력"
          class="ut-w-300"
          maxlength="8"
          :readonly="props.disabled"
        />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps, ref } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  title: { type: String, default: '고객(실사용자) 정보' },
  name: { type: String, default: 'basic' },
  disabled: Boolean,
})
const model = defineModel({ type: Object, required: true })

const realUserNameRef = ref(null)
const realUserBirthDateRef = ref(null)
const userGender = ref(null)

const validate = () => {
  if (!model.value.realUserName) return false
  if (!model.value.realUserBirthDate) return false
  if (!['JP', 'GO'].includes(model.value.cstmrTypeCd) && !model.value.userGender) return false

  if (
    model.value.canBulkCorporateOpenYn === 'Y' &&
    model.value.cstmrTypeCd === 'JP' &&
    model.value.joinType === 'NAC3'
  ) {
    if (!model.value.bulkActivationCnt || model.value.bulkActivationCnt < 1) {
      return false
    }
  }
  return true
}

const checkValidation = () => {
  if (!model.value.realUserName) {
    showAlert(`${props.title} 이름을 입력하세요`, () => {
      realUserNameRef.value?.focus()
    })
    return false
  }

  if (!model.value.realUserBirthDate) {
    showAlert(`${props.title} 생년월일을 입력하세요`, () => {
      realUserBirthDateRef.value?.focus()
    })
    return false
  }

  if (!['JP', 'GO'].includes(model.value.cstmrTypeCd) && !model.value.userGender) {
    showAlert(`${props.title} 성별을 선택하세요`, () => {
      userGender.value?.focus()
    })
    return false
  }
  return true
}

defineExpose({ validate, checkValidation })
</script>
