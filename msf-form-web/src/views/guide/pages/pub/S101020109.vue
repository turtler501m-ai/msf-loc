<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신분증 목록 조회"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfDataTable
      :columns="colDefs"
      :datas="datas"
      :total="datas.length"
      show-single-check
      @selected="onSelected"
      rows="5.2"
    >
      <template #count-prepend>스캔 목록</template>
    </MsfDataTable>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// 테이블 샘플
const colDefs = ref([
  { field: 'make', width: 80, cellStyle: { textAlign: 'center' } },
  { field: 'model', flex: 1 },
  { field: 'price', flex: 1, cellStyle: { textAlign: 'center' } },
])
const datas = ref([
  { make: 'Tesla', model: 'Model Y', price: 64950 },
  { make: 'Ford', model: 'F-Series', price: 33850 },
  { make: 'Toyota', model: 'Corolla', price: 29600 },
  { make: 'Tesla', model: 'Model Y', price: 64950 },
  { make: 'Ford', model: 'F-Series', price: 33850 },
  { make: 'Toyota', model: 'Corolla', price: 29600 },
])
const selectedRow = ref()
const onSelected = (data) => {
  selectedRow.value = data
}
</script>

<style scoped></style>
