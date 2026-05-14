<template>
  <!-- 검색박스 -->
  <MsfBox margin="0">
    <MsfStack vertical>
      <MsfStack type="field" class="ut-w100p">
        <MsfDateRange v-model:from="formData.startDt" v-model:to="formData.endDt" class="" />
        <MsfSelect
          title="신청서 구분"
          v-model="formData.formTypeOne"
          :options="formTypeCd"
          class="ut-flex-1"
        />
        <!-- <MsfFormGroup label="신청서 구분" tag="div" class="grid-1">
          <MsfCheckboxGroup
            v-model="formData.formTypeCd"
            :options="formTypeCd"
            :allChecked="true"
          />
        </MsfFormGroup> -->
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
    url="/api/receiptpage/list"
    :params="formData"
    show-paging
    :is-search="true"
    show-single-check
    @selected="onSelected"
  >
    <template #buttons>
      <MsfButton variant="subtle" active>열람하기</MsfButton>
      <MsfButton variant="toggle" disabled>복사하기</MsfButton>
      <MsfButton variant="toggle" active @click="onUpdate">복사하기</MsfButton>
    </template>
  </MsfDataTable>
</template>

<script setup>
import { onBeforeMount, ref } from 'vue'
// import { post } from '@/libs/api/msf.api'
import { storeToRefs } from 'pinia'
import { showAlert } from '@/libs/utils/comp.utils'
// import { showConfirm } from '@/libs/utils/comp.utils'
import { getCommonCodeList } from '@/libs/utils/comn.utils.js'
import { formatDate } from '@/libs/utils/date.utils'
import { storeReceiptPage } from '@/stores/receiptpage'

const receiptPageStore = storeReceiptPage()
const { formData } = storeToRefs(receiptPageStore)

const formTypeCd = ref([])

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
    field: 'procCd',
    headerName: '진행 상태',
    flex: 1,
    cellStyle: {
      textAlign: 'center',
    },
    cellRenderer: (params) => {
      return params.data.procCd?.title
    },
  },
  {
    field: 'cretNm',
    headerName: '신청자',
    width: 130,
    cellStyle: {
      textAlign: 'center',
    },
  },
])

const isLoaded = ref(false)
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
  // receiptPageStore.openUpdatePopup(result?.data ?? null, selectedScriptSeq.value)

  if (!receiptPageStore.formDtlData?.uuid) {
    showAlert('수정할 항목이 존재하지 않습니다.')
    // receiptPageStore.closeScriptPopup()
  }
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

const pushFormTypeCd = async () => {
  const res = await getCommonCodeList('FORM_TYPE_CD')
  formTypeCd.value = [
    { value: '0', label: '전체' },
    ...res.map((item) => ({ value: item.code, label: item.title })),
  ]
}

onBeforeMount(() => {
  pushFormTypeCd()
  setRange({ months: 0, days: 7 })
  isLoaded.value = true
})

const setRange = (val) => {
  const end = new Date()
  const start = new Date()

  if (val.months > 0) {
    start.setMonth(start.getMonth() - val.months)
  }
  if (val.days > 0) {
    start.setDate(start.getDate() - val.days)
  }

  formData.value.startDt = formatDate(start)
  formData.value.endDt = formatDate(end)
}
</script>

<style scoped></style>
