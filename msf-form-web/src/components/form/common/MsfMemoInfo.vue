<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfFormGroup label="메모" tag="div">
      <MsfTextarea
        ref="memoRef"
        v-model="model.memo"
        placeholder="메모 입력"
        :disabled="props.disabled"
      />
    </MsfFormGroup>
  </div>
</template>
<script setup>
import { ref } from 'vue'

const props = defineProps({
  title: { type: String, default: '메모' },
  disabled: Boolean,
})
const model = defineModel({ type: Object, required: true })

const memoRef = ref(null)

const checkValidation = () => {
  return true
}

defineExpose({ checkValidation })
</script>
