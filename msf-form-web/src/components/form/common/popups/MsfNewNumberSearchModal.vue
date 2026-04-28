<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신규번호 검색"
    @open="onOpen"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <p class="ut-text-desc">번호를 선택해 주세요.</p>
    <div v-if="loading" class="ut-flex ut-justify-center ut-py-20">
      <MsfLoadingComp />
    </div>
    <template v-else>
      <MsfRadioGroup
        v-if="numberOptions.length > 0"
        name="number-select"
        v-model="numberSelect"
        :options="numberOptions"
        grid
      />
      <p v-else class="ut-text-center ut-py-20 ut-text-gray-400">조회된 번호가 없습니다.</p>
    </template>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm" :disabled="!numberSelect">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'
import { post } from '@/libs/api/msf.api'

const props = defineProps({
  modelValue: Boolean,
  searchParams: { type: Object, default: () => ({}) },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

// 신규번호 선택항목
const numberSelect = ref('')
const numberOptions = ref([])
const loading = ref(false)

const onOpen = async () => {
  emit('open')
  await fetchNumbers()
}

const fetchNumbers = async () => {
  loading.value = true
  try {
    const res = await post('/api/form/newchange/searchNumber', props.searchParams)
    if (res && res.code === '0000') {
      const list = res.data || []
      numberOptions.value = list.map((num) => ({
        value: num,
        label: num,
      }))
      if (numberOptions.value.length > 0) {
        numberSelect.value = numberOptions.value[0].value
      }
    } else {
      alert(res.message || '번호 조회에 실패했습니다.')
    }
  } catch (error) {
    console.error('Search number error:', error)
  } finally {
    loading.value = false
  }
}

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onConfirm = () => {
  emit('confirm', numberSelect.value)
  onClose()
}
</script>

<style lang="scss" scoped>
.ut-text-desc {
  padding-bottom: rem(12px);
  margin-bottom: rem(12px);
  border-bottom: var(--border-width-base) solid var(--color-gray-150);
}
</style>
