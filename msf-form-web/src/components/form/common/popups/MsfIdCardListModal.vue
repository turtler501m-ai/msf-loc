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
      rows="5.6"
    >
      <template #count-prepend>스캔 목록</template>
    </MsfDataTable>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onConfirm = () => {
  emit('confirm', selectedRow.value)
  onClose()
}

// 테이블 샘플
const colDefs = ref([
  { field: 'scanDate', headerName: '스캔일시', width: 120, cellStyle: { textAlign: 'center' } },
  { field: 'cstmrNm', headerName: '이름', width: 100, cellStyle: { textAlign: 'center' } },
  { field: 'identityTypeNm', headerName: '신분증 유형', width: 120, cellStyle: { textAlign: 'center' } },
  { field: 'rrn1', headerName: '생년월일', flex: 1, cellStyle: { textAlign: 'center' } },
])

const datas = ref([
  {
    scanDate: '2024-04-20 14:20',
    cstmrNm: '홍길동',
    identityTypeNm: '주민등록증',
    identityTypeCd: 'ID1',
    rrn1: '900101',
    rrn2: '1234567',
  },
  {
    scanDate: '2024-04-21 10:15',
    cstmrNm: '이순신',
    identityTypeNm: '운전면허증',
    identityTypeCd: 'DL1',
    rrn1: '850505',
    rrn2: '2345678',
  },
  {
    scanDate: '2024-04-22 09:30',
    cstmrNm: '강감찬',
    identityTypeNm: '주민등록증',
    identityTypeCd: 'ID1',
    rrn1: '701212',
    rrn2: '1111111',
  },
  {
    scanDate: '2024-04-22 16:45',
    cstmrNm: '유관순',
    identityTypeNm: '여권',
    identityTypeCd: 'PP1',
    rrn1: '050301',
    rrn2: '4444444',
  },
  {
    scanDate: '2024-04-23 11:00',
    cstmrNm: '세종대왕',
    identityTypeNm: '외국인등록증',
    identityTypeCd: 'FR1',
    rrn1: '950815',
    rrn2: '5555555',
  },
  {
    scanDate: '2024-04-23 13:20',
    cstmrNm: '장영실',
    identityTypeNm: '운전면허증',
    identityTypeCd: 'DL1',
    rrn1: '881010',
    rrn2: '1234567',
  },
])
const selectedRow = ref()
const onSelected = (data) => {
  selectedRow.value = data
}
</script>

<style scoped></style>
