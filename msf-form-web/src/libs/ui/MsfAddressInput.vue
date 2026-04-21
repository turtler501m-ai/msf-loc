<template>
  <!-- 주소 -->
  <MsfStack vertical>
    <MsfStack type="field">
      <MsfInput
        v-model="address1"
        placeholder="우편번호"
        ariaLabel="우편번호 입력"
        :error="error"
        :readonly="true"
      />
      <MsfButton variant="subtle" :disabled="disabled" @click="emit('search')">
        우편번호 찾기
      </MsfButton>
    </MsfStack>
    <MsfInput
      v-model="address2"
      placeholder="주소"
      ariaLabel="주소 입력"
      class="ut-w100p"
      :error="error"
      :readonly="true"
    />
    <MsfInput
      v-model="address3"
      placeholder="상세주소"
      ariaLabel="상세주소 입력"
      class="ut-w100p"
      :error="error"
      :readonly="readonly"
      :disabled="disabled"
    />
  </MsfStack>
</template>

<script setup>
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
</script>
