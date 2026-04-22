<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="실사용자 이름" required>
        <MsfInput
          v-model="model.realUserName"
          placeholder="이름"
          class="ut-w-300"
          :readonly="model.isSaved"
        />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            v-model="model.userBirthDate"
            length="8"
            class="ut-w-300"
            :readonly="model.isSaved"
          />
          <MsfRadioGroup
            name="user-gender"
            v-model="model.userGender"
            :options="[
              { value: 'userGender1', label: '남' },
              { value: 'userGender2', label: '여' },
            ]"
            class="ut-ml-16"
            :disabled="model.isSaved"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps } from 'vue'

const props = defineProps({
  title: { type: String, default: '고객(실사용자) 정보' },
})
const model = defineModel({ type: Object, required: true })

const validate = () => {
  if (!model.value.realUserName) return false
  if (!model.value.userBirthDate || !model.value.userGender) return false
  return true
}

defineExpose({ validate })
</script>
