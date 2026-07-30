<script setup>
import { nextTick, ref } from 'vue'

const formData = defineModel({ type: Object, required: true })
const props = defineProps({
  disabled: { type: Boolean, default: false },
})

const afterTel1Ref = ref(null)
const afterTel2Ref = ref(null)
const afterTel3Ref = ref(null)

const focusPostMethod = () => {
  nextTick(() => {
    document.querySelector('input[name="inp-postMethod"]')?.focus()
  })
}
</script>
<template>
  <MsfTitleArea title="해지 후 연락처" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="연락 전화번호" required helpText="※ 개통신청서 발송">
      <MsfStack type="field">
        <MsfNumberInput
          ref="afterTel1Ref"
          v-model="formData.afterTel1"
          id="inp-afterTel1"
          placeholder="010/지역번호"
          maxlength="3"
          :readonly="props.disabled"
          @maxlength="afterTel2Ref?.focus()"
        />
        <span class="unit-sep">-</span>
        <MsfNumberInput
          ref="afterTel2Ref"
          v-model="formData.afterTel2"
          id="inp-afterTel2"
          placeholder="가운데 4자리"
          maxlength="4"
          :readonly="props.disabled"
          @maxlength="afterTel3Ref?.focus()"
        />
        <span class="unit-sep">-</span>
        <MsfNumberInput
          ref="afterTel3Ref"
          v-model="formData.afterTel3"
          id="inp-afterTel3"
          placeholder="뒤 4자리"
          maxlength="4"
          :readonly="props.disabled"
          @maxlength="focusPostMethod"
        />
      </MsfStack>
    </MsfFormGroup>
    <MsfFormGroup label="해지 후 연락 수단" tag="div">
      <MsfChip
        v-model="formData.postMethod"
        name="inp-postMethod"
        :readonly="props.disabled"
        :data="[
          { value: 'P', label: '우편' },
          { value: 'E', label: '이메일' },
        ]"
      />
    </MsfFormGroup>
  </MsfStack>
</template>
