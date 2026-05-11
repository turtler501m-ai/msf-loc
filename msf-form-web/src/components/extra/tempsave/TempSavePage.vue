<template>
  <!-- 검색박스 -->
  <MsfBox margin="0">
    <MsfStack vertical class="ut-ai-center">
      <MsfStack type="field">
        <MsfInput v-model="formData.searchWord" class="ut-w-347" placeholder="검색어 입력" />
        <MsfButton variant="primary" noMinWidth @click="onClickSearch">검색</MsfButton>
      </MsfStack>
    </MsfStack>
  </MsfBox>
  <!-- 그리드 테이블 -->
  <MsfDataTable
    ref="pagingRef"
    :columns="colDefsPaging"
    url="/api/tempsave/list"
    :params="formData"
    show-paging
    :is-search="false"
    show-single-check
    @selected="onSelected"
  >
    <template #buttons>
      <MsfButton variant="toggle" @click="onUpdate">수정</MsfButton>
    </template>
  </MsfDataTable>
</template>

<script setup>
import { onBeforeMount, ref } from 'vue'
// import { post } from '@/libs/api/msf.api'
import { storeToRefs } from 'pinia'
import { showAlert } from '@/libs/utils/comp.utils'
// import { showConfirm } from '@/libs/utils/comp.utils'
import { storeTempSave } from '@/stores/tempsave'

const tempSaveStore = storeTempSave()
const { formData } = storeToRefs(tempSaveStore)

const colDefsPaging = ref([
  {
    field: 'cretDt',
    headerName: '작성일자',
    width: 150,
    cellStyle: { textAlign: 'center' },
    headerClass: 'ag-center-header',
  },
  {
    field: 'serviceTypeNm',
    headerName: '신청서 구분',
    width: 100,
    cellStyle: { textAlign: 'center' },
    headerClass: 'ag-center-header',
  },
  {
    field: 'cstmrTypeNm',
    headerName: '고객 유형',
    width: 100,
    cellStyle: { textAlign: 'center' },
    headerClass: 'ag-center-header',
  },
  {
    field: 'openTypeNm',
    headerName: '가입 유형',
    width: 100,
    cellStyle: { textAlign: 'center' },
    headerClass: 'ag-center-header',
  },
  {
    field: 'cstmrNm',
    headerName: '신청자',
    width: 100,
    cellStyle: { textAlign: 'center' },
    headerClass: 'ag-center-header',
  },
])

const pagingRef = ref()
const selectedRowPaging = ref([])
const selectedScriptSeq = ref(null)

const onClickSearch = () => {
  pagingRef.value.search()
}

const onSelected = (data) => {
  selectedRowPaging.value = data
  selectedScriptSeq.value = data?.uuid ?? null
  console.log('select: ' + selectedScriptSeq.value)
}

const onUpdate = async () => {
  if (!selectedScriptSeq.value) {
    showAlert('수정할 항목을 선택해주세요.')
    return
  }
  const param = {
    uuid: selectedScriptSeq.value,
  }
  console.log(param)

  // const result = await post('/api/agencypadmac/get', param)
  // tempSaveStore.openUpdatePopup(result?.data ?? null, selectedScriptSeq.value)

  if (!tempSaveStore.formDtlData?.uuid) {
    showAlert('수정할 항목이 존재하지 않습니다.')
    // tempSaveStore.closeScriptPopup()
  }
}

onBeforeMount(() => {
  // pushFormTypeCd()
})
</script>

<style scoped></style>
