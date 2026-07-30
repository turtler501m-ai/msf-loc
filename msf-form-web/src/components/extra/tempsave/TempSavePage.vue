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
// import { showAlert } from '@/libs/utils/comp.utils'
import { storeTempSave } from '@/stores/tempsave'
import { useMsfUserStore } from '@/stores/msf_user'

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
    valueFormatter: (params) => {
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
    valueFormatter: (params) => {
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
const selectedModUserID = ref(null)
const alertUpdateMsg = ref('수정할 항목을 선택해주세요.')

const userStore = useMsfUserStore()

const onClickSearch = () => {
  pagingRef.value.search()
}

const onSelected = (data) => {
  selectedRowPaging.value = data
  selectedScriptSeq.value = data?.requestKey ?? null
  selectedModUserID.value = data?.modUserId ?? null

  if (selectedScriptSeq.value && data?.cretId === userStore.userInfo?.userId) {
    alertUpdateMsg.value = ''
  } else {
    alertUpdateMsg.value = '수정할 항목을 선택해주세요.'
  }
}

const onUpdate = async () => {
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

function renderFormType(params) {
  if (!params?.data) return ''

  const formType = params.data.formTypeCd
  const reqBuyType = params.data.reqBuyTypeCd
  const operType = params.data.operTypeCd

  if (formType?.code === '1') {
    // 내부 속성이 없을 경우를 대비해 기본값('-') 처리
    const formTitle = formType?.title ?? ''
    const reqBuyTitle = reqBuyType?.title ?? '-'
    const operTitle = operType?.title ?? '-'

    return `${formTitle}(${reqBuyTitle}/${operTitle})`
  }

  return formType?.title ?? ''
}

onBeforeMount(() => {
  formData.value.searchWord = ''
  isLoaded.value = true
})
</script>

<style scoped></style>
