<template>
  <!-- 주소 -->
  <MsfStack vertical class="ut-w-full">
    <MsfStack type="field">
      <MsfInput
        ref="address1Ref"
        v-model="address1"
        placeholder="우편번호"
        ariaLabel="우편번호 입력"
        :error="props.error"
        :readonly="true"
      />
      <MsfButton
        ref="searchAddressBtnRef"
        variant="subtle"
        :disabled="props.disabled"
        @click="emit('search')"
      >
        우편번호 찾기
      </MsfButton>
    </MsfStack>
    <MsfInput
      ref="address2Ref"
      v-model="address2"
      placeholder="주소"
      ariaLabel="주소 입력"
      class="ut-w100p"
      :error="props.error"
      :readonly="true"
    />
    <MsfInput
      ref="address3Ref"
      v-model="address3"
      placeholder="상세주소"
      ariaLabel="상세주소 입력"
      class="ut-w100p"
      :error="props.error"
      :readonly="props.readonly"
      :disabled="props.disabled"
    />
  </MsfStack>
</template>

<script setup>
import { ref } from 'vue'

// v-model 선언
const address1 = defineModel('address1', { default: '' }) // 우편번호
const address2 = defineModel('address2', { default: '' }) // 주소
const address3 = defineModel('address3', { default: '' }) // 상세주소

// Props 정의
const props = defineProps({
  label: { type: String, default: '주소' },
  error: { type: Boolean, default: false },
  readonly: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
})

const emit = defineEmits(['search'])

const address1Ref = ref(null)
const searchAddressBtnRef = ref(null)
const address2Ref = ref(null)
const address3Ref = ref(null)

defineExpose({
  validate: (detailRequired = true) => {
    if (!address1.value || !address2.value) {
      return false
    }
    if (detailRequired && !address3.value) {
      return false
    }
    return true
  },
  focus: () => {
    if (!address1.value || !address2.value) {
      searchAddressBtnRef.value?.focus()
    } else if (!address3.value) {
      address3Ref.value?.focus()
    }
  },
})
</script>
