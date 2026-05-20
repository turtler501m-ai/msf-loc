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
    v-if="isLoaded"
    ref="pagingRef"
    :columns="colDefsPaging"
    url="/api/tempsave/list"
    :params="formData"
    show-paging
    :is-search="true"
    show-single-check
    @selected="onSelected"
  >
    <template #buttons>
      <MsfButton variant="toggle" @click="onUpdate" :readonlyMsg="alertUpdateMsg">수정</MsfButton>
    </template>
  </MsfDataTable>
</template>

<script setup>
import { onBeforeMount, ref } from 'vue'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'
import { showAlert } from '@/libs/utils/comp.utils'
import { storeTempSave } from '@/stores/tempsave'

// 데이터 테이블 + 페이징처리
const colDefsPaging = ref([
  {
    field: 'requestKey',
    hide: true,
    suppressColumnsToolPanel: true,
  },
  {
    field: 'modifyYn',
    hide: true,
    suppressColumnsToolPanel: true,
  },
  {
    field: 'cretDt',
    headerName: '작성일자',
    width: 250,
    type: 'datetime',
    cellStyle: {
      textAlign: 'center',
    },
  },
  {
    field: 'formTypeCd',
    headerName: '신청서 구분',
    width: 250,
    cellStyle: {
      textAlign: 'center',
    },
    cellRenderer: (params) => {
      return renderFormType(params)
    },
  },
  {
    field: 'cstmrTypeCd',
    headerName: '고객 유형',
    flex: 1,
    cellStyle: {
      textAlign: 'center',
    },
    cellRenderer: (params) => {
      return params.data.cstmrTypeCd?.title
    },
  },
  {
    field: 'cstmrNm',
    headerName: '고객명',
    flex: 1,
    cellStyle: {
      textAlign: 'center',
    },
  },
  {
    field: 'cretNm',
    headerName: '신청자',
    width: 200,
    cellStyle: {
      textAlign: 'center',
    },
  },
])

const router = useRouter()
const tempSaveStore = storeTempSave()
const { formData } = storeToRefs(tempSaveStore)
const isLoaded = ref(false)
const pagingRef = ref()
const selectedRowPaging = ref([])
const selectedScriptSeq = ref(null)
const alertUpdateMsg = ref('수정할 항목을 선택해주세요.')

const onClickSearch = () => {
  pagingRef.value.search()
}

const onSelected = (data) => {
  selectedRowPaging.value = data
  selectedScriptSeq.value = data?.requestKey ?? null
  if (selectedScriptSeq.value) {
    alertUpdateMsg.value = ''
  } else {
    alertUpdateMsg.value = '수정할 항목을 선택해주세요.'
  }
}

const onUpdate = async () => {
  if (!selectedScriptSeq.value) {
    showAlert('수정할 항목을 선택해주세요.')
    return
  }
  // 선택된 행의 데이터 확인
  const selectedRow = selectedRowPaging.value
  if (!selectedRow) return

  await router.push({
    name: 'form',
    params: { domain: 'newchange' },
    state: {
      requestKey: selectedScriptSeq.value,
    },
  })
}

const renderFormType = (params) => {
  if (params.data.formTypeCd?.code === '1') {
    const formTypeCd = params.data.formTypeCd?.title
    const reqBuyTypeCd = params.data.reqBuyTypeCd?.title
    const operTypeCd = params.data.operTypeCd?.title

    return formTypeCd + '(' + reqBuyTypeCd + '/' + operTypeCd + ')'
  } else {
    return params.data.formTypeCd?.title
  }
}

onBeforeMount(() => {
  isLoaded.value = true
})
</script>

<style scoped></style>
