<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신분증 목록 조회"
    @open="onOpen"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <div v-if="loading" class="ut-flex ut-justify-center ut-py-20">
      <MsfLoadingComp />
    </div>
    <MsfDataTable
      v-else
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
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  modelValue: Boolean,
  agentCd: { type: String, default: '' },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const datas = ref([])
const selectedRow = ref()
const loading = ref(false)

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

/**
 * 1. 신분증 목록 조회 (K-NOTE)
 */
const fetchIdList = async () => {
  loading.value = true
  try {
    const res = await post('/api/form/knote/scaninfo/list', { agentCd: props.agentCd })
    if (res && res.code === '0000') {
      // res.data.resData.list 구조에 맞게 수정
      datas.value = res.data?.resData?.list || []
    } else {
      datas.value = []
    }
  } catch (error) {
    console.error('Failed to fetch K-NOTE ID list:', error)
    datas.value = []
  } finally {
    loading.value = false
  }
}

/**
 * 2. 신분증 상태 체크 및 확인 처리
 */
const onConfirm = async () => {
  if (!selectedRow.value) {
    showAlert('선택된 신분증이 없습니다.')
    return
  }

  const frmpapId = selectedRow.value.frmpapId || selectedRow.value.id

  try {
    const res = await post('/api/form/knote/scaninfo/check', {
      frmpapId,
      agentCd: props.agentCd || '',
    })
    if (res && res?.data?.resCode === '0000') {
      // API 응답의 resData를 포함하여 부모에게 전달
      emit('confirm', {
        ...selectedRow.value,
        ...res.data?.resData,
      })
      onClose()
    } else {
      showAlert(res.message || '신분증 상태 확인에 실패했습니다.')
    }
  } catch (error) {
    console.error('Check ID status error:', error)
  }
}

// 팝업 열릴 때 목록 조회
const onOpen = () => {
  emit('open')
  fetchIdList()
}

// 테이블 정의 (JSON 필드에 맞게 수정: wapplRegDate, custNm, custIdntNoIndCd, custIdntNo)
const colDefs = ref([
  { field: 'wapplRegDate', headerName: '스캔일시', width: 240, cellStyle: { textAlign: 'center' } },
  { field: 'custNm', headerName: '이름', flex: 1, cellStyle: { textAlign: 'left' } },
  {
    field: 'custIdntNoIndCd',
    headerName: '신분증 유형',
    width: 120,
    cellStyle: { textAlign: 'center' },
    valueFormatter: (params) =>
      params.value === '1' ? '주민등록증' : params.value === '5' ? '운전면허증' : params.value,
  },
  { field: 'custIdntNo', headerName: '생년월일', width: 100, cellStyle: { textAlign: 'center' } },
])

const onSelected = (data) => {
  selectedRow.value = data
}
</script>

<style scoped></style>
