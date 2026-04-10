<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfFormGroup label="메모" tag="div">
      <MsfTextarea v-model="model.memo" placeholder="메모 입력" />
    </MsfFormGroup>
  </div>
</template>
<script setup>
import { defineModel, defineProps } from 'vue'

const props = defineProps({
  title: { type: String, default: '메모' }
})
const model = defineModel({ type: Object, required: true })
</script>