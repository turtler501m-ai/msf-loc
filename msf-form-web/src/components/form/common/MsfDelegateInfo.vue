<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="위임받은<br/>고객 이름" required>
        <MsfInput
          v-model="model.minorAgentNm"
          placeholder="이름"
          class="ut-w-300"
          :readonly="model.isSaved"
        />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            v-model="model.agentBirthDate"
            length="8"
            class="ut-w-300"
            :readonly="model.isSaved"
          />
          <MsfRadioGroup
            name="agent-gender"
            v-model="model.agentGender"
            :options="[
              { value: 'agentGender1', label: '남' },
              { value: 'agentGender2', label: '여' },
            ]"
            class="ut-ml-16"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="신청인과의 관계" tag="div" required>
        <MsfSelect
          title="신청인과의 관계"
          v-model="model.minorAgentRelTypeCd"
          :options="[
            { label: '관계1', value: 'agentRelation1' },
            { label: '관계2', value: 'agentRelation2' },
          ]"
          placeholder="선택"
          class="ut-w-300"
        />
      </MsfFormGroup>
      <MsfFormGroup label="연락처" required>
        <MsfStack type="field">
          <MsfNumberInput v-model="model.minorAgentTelFnNo" placeholder="앞자리" maxlength="3" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.minorAgentTelMnNo"
            id="inp-agentPhone2"
            placeholder="가운데 4자리"
            maxlength="4"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.minorAgentTelRnNo"
            id="inp-agentPhone3"
            placeholder="뒤 4자리"
            maxlength="4"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps } from 'vue'

const props = defineProps({
  title: { type: String, default: '대리인 위임정보' },
})
const model = defineModel({ type: Object, required: true })

const validate = () => {
  if (!model.value.minorAgentNm) return false
  if (!model.value.agentBirthDate || !model.value.agentGender) return false
  if (!model.value.minorAgentRelTypeCd) return false
  if (!model.value.minorAgentTelFnNo || !model.value.minorAgentTelMnNo || !model.value.minorAgentTelRnNo)
    return false
  return true
}

defineExpose({ validate })
</script>
