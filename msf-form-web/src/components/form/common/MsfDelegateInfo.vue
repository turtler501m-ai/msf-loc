<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="위임받은<br/>고객 이름" required>
        <MsfInput
          id="inp-minorAgentNm"
          v-model="model.minorAgentNm"
          placeholder="이름"
          class="ut-w-300"
          :readonly="model.isSaved"
        />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            id="inp-agentBirthDate"
            v-model="model.agentBirthDate"
            length="8"
            class="ut-w-300"
            :readonly="model.isSaved"
          />
          <MsfRadioGroup
            name="agent-gender"
            v-model="model.agentGender"
            :options="[
              { value: 'M', label: '남' },
              { value: 'F', label: '여' },
            ]"
            class="ut-ml-16"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="신청인과의 관계" tag="div" required>
        <MsfSelect
          id="inp-minorAgentRelTypeCd"
          title="신청인과의 관계"
          v-model="model.minorAgentRelTypeCd"
          :options="[
            { label: '관계1', value: '01' },
            { label: '관계2', value: '02' },
          ]"
          placeholder="선택"
          class="ut-w-300"
        />
      </MsfFormGroup>
      <MsfFormGroup label="연락처" required>
        <MsfStack type="field">
          <MsfNumberInput
            ref="input1"
            id="inp-agentPhone1"
            v-model="model.minorAgentTelFnNo"
            placeholder="앞자리"
            maxlength="3"
            @maxlength="input2?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="input2"
            v-model="model.minorAgentTelMnNo"
            id="inp-agentPhone2"
            placeholder="가운데 4자리"
            maxlength="4"
            @maxlength="input3?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="input3"
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
import { defineModel, defineProps, ref, onMounted } from 'vue'

const props = defineProps({
  title: { type: String, default: '대리인 위임정보' },
})
const model = defineModel({ type: Object, required: true })

const input1 = ref(null)
const input2 = ref(null)
const input3 = ref(null)

onMounted(() => {
  if (!model.value.agentGender) model.value.agentGender = 'M'
})

const validate = () => {
  if (!model.value.minorAgentNm) return false
  if (!model.value.agentBirthDate || !model.value.agentGender) return false
  if (!model.value.minorAgentRelTypeCd) return false
  if (
    !model.value.minorAgentTelFnNo ||
    !model.value.minorAgentTelMnNo ||
    !model.value.minorAgentTelRnNo
  )
    return false
  return true
}

defineExpose({ validate })
</script>