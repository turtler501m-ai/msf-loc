<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="위임받은<br/>고객 이름" required>
        <MsfInput
          ref="minorAgentNmRef"
          id="inp-minorAgentNm"
          v-model="model.minorAgentNm"
          placeholder="이름"
          class="ut-w-300"
          :readonly="props.disabled"
          maxlength="100"
        />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            ref="agentBirthDateRef"
            id="inp-agentBirthDate"
            v-model="model.agentBirthDate"
            length="8"
            class="ut-w-300"
            :readonly="props.disabled"
          />
          <MsfRadioGroup
            ref="agentGenderRef"
            name="agent-gender"
            v-model="model.agentGender"
            :options="[
              { value: 'M', label: '남' },
              { value: 'F', label: '여' },
            ]"
            class="ut-ml-16"
            :disabled="props.disabled"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="신청인과의 관계" tag="div" required>
        <MsfSelect
          ref="minorAgentRelTypeCdRef"
          id="inp-minorAgentRelTypeCd"
          title="신청인과의 관계"
          groupCode="RCP0021"
          v-model="model.minorAgentRelTypeCd"
          placeholder="선택"
          class="ut-w-300"
          :disabled="props.disabled"
          @select="onRelationSelect"
        />
      </MsfFormGroup>
      <MsfFormGroup label="연락처" required>
        <MsfStack type="field">
          <MsfTelInput
            ref="minorAgentTelNoRef"
            id="inp-agentPhone"
            v-model:telNo1="model.minorAgentTelFnNo"
            v-model:telNo2="model.minorAgentTelMnNo"
            v-model:telNo3="model.minorAgentTelRnNo"
            :readonly="props.disabled"
          />
          <!-- <MsfNumberInput
            ref="input1"
            id="inp-agentPhone1"
            v-model="model.minorAgentTelFnNo"
            placeholder="앞자리"
            maxlength="3"
            :readonly="model.isSaved || props.disabled"
            @maxlength="input2?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="input2"
            v-model="model.minorAgentTelMnNo"
            id="inp-agentPhone2"
            placeholder="가운데 4자리"
            maxlength="4"
            :readonly="model.isSaved || props.disabled"
            @maxlength="input3?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="input3"
            v-model="model.minorAgentTelRnNo"
            id="inp-agentPhone3"
            placeholder="뒤 4자리"
            maxlength="4"
            :readonly="model.isSaved || props.disabled"
          /> -->
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps, ref, onMounted } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  title: { type: String, default: '대리인 위임정보' },
  disabled: Boolean,
  captureRelationName: Boolean,
})
const model = defineModel({ type: Object, required: true })

// const input1 = ref(null)
// const input2 = ref(null)
// const input3 = ref(null)

const minorAgentNmRef = ref(null)
const agentBirthDateRef = ref(null)
const agentGenderRef = ref(null)
const minorAgentRelTypeCdRef = ref(null)
const minorAgentTelNoRef = ref(null)

const onRelationSelect = (option) => {
  if (props.captureRelationName) {
    model.value.minorAgentRelTypeNm = option?.label || ''
  }
}

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

const checkNameAndBirth = () => {
  if (!model.value.minorAgentNm) {
    showAlert(`${props.title} 이름을 입력하세요`, () => {
      minorAgentNmRef.value?.focus()
    })
    return false
  }
  if (!model.value.agentBirthDate) {
    showAlert(`${props.title} 생년월일을 입력하세요`, () => {
      agentBirthDateRef.value?.focus()
    })
    return false
  }
  return true
}

const checkValidation = () => {
  if (!model.value.minorAgentNm) {
    showAlert(`${props.title} 이름을 입력하세요`, () => {
      minorAgentNmRef.value?.focus()
    })
    return false
  }
  if (!model.value.agentBirthDate) {
    showAlert(`${props.title} 생년월일을 입력하세요`, () => {
      agentBirthDateRef.value?.focus()
    })
    return false
  }
  if (!model.value.agentGender) {
    showAlert(`${props.title} 성별을 선택하세요`, () => {
      agentGenderRef.value?.focus()
    })
    return false
  }
  if (!model.value.minorAgentRelTypeCd) {
    showAlert(`${props.title} 신청인과의 관계를 선택하세요`, () => {
      minorAgentRelTypeCdRef.value?.focus()
    })
    return false
  }
  if (!minorAgentTelNoRef.value?.isValid) {
    showAlert(`${props.title} 연락처를 입력하세요`, () => {
      minorAgentTelNoRef.value?.focus()
    })
    return false
  }

  return true
}

defineExpose({ validate, checkValidation, checkNameAndBirth })
</script>
