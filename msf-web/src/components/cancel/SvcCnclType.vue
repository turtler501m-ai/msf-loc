<template>
  <div class="page-step-panel">
    <h3 class="text-lg font-semibold mb-4">서비스해지 - 가입유형 선택</h3>
    <p class="text-sm text-gray-600 mb-4">대리점을 선택한 후 다음 단계로 진행해 주세요.</p>

    <div class="mb-4">
      <label class="block text-sm font-medium mb-2">대리점 <span class="text-red-500">*</span></label>
      <select
        v-model="form.agencyCode"
        class="w-full max-w-xs rounded border border-gray-300 px-3 py-2"
      >
        <option value="">대리점 선택</option>
        <option v-for="a in agencyOptions" :key="a.value" :value="a.value">
          {{ a.label }}
        </option>
      </select>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useCancelFormStore } from '@/stores/cancel_form'

const emit = defineEmits(['update:complete'])
const formStore = useCancelFormStore()

const form = ref({ agencyCode: '' })

const agencyOptions = ref([
  { value: 'AG001', label: '대리점 A' },
  { value: 'AG002', label: '대리점 B' },
  { value: 'AG003', label: '대리점 C' },
])

const isComplete = computed(() => !!form.value.agencyCode)

watch(
  () => form.value.agencyCode,
  (v) => {
    formStore.setAgencyCode(v)
  },
)
watch(isComplete, (v) => emit('update:complete', v))
onMounted(() => {
  if (formStore.agencyCode) form.value.agencyCode = formStore.agencyCode
  emit('update:complete', isComplete.value)
})

const validate = async () => {
  if (!form.value.agencyCode) {
    return { valid: false, message: '대리점을 선택해 주세요.' }
  }
  return { valid: true, message: '' }
}

defineExpose({ validate })
</script>

<style scoped></style>
