<template>
  <!-- 검색박스 -->
  <MsfBox margin="0">
    <MsfStack vertical>
      <MsfStack type="field" class="ut-w100p">
        <MsfDateRange v-model:from="formData.startDate" v-model:to="formData.endDate" class="" />
        <MsfSelect
          title="신청서 구분"
          v-model="formData.formTypeCd"
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
    ref="pagingRef"
    :columns="colDefsPaging"
    url="/api/receiptpage/list"
    :params="formData"
    show-paging
    :is-search="false"
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
    field: 'rowNum',
    headerName: '등록번호',
    width: 100,
    cellStyle: { textAlign: 'center' },
    headerClass: 'ag-center-header',
  },
  {
    field: 'formTypeCd',
    headerName: '신청서 구분',
    flex: 1,
    minWidth: 200,
  },
  {
    field: 'shopCd',
    headerName: '매장코드',
    flex: 1,
    minWidth: 200,
  },
  {
    field: 'shopNm',
    headerName: '매장명',
    flex: 1,
    minWidth: 200,
  },
  {
    field: 'userId',
    headerName: '사용자ID',
    flex: 1,
    minWidth: 150,
  },
  {
    field: 'userNm',
    headerName: '사용자명',
    flex: 1,
    minWidth: 150,
  },
  {
    field: 'macAdr',
    headerName: 'MAC ID',
    width: 120,
    cellStyle: { textAlign: 'center' },
    headerClass: 'ag-center-header',
  },
  {
    field: 'uuid',
    headerName: '단말기 고유 ID',
    width: 120,
    cellStyle: { textAlign: 'center' },
    headerClass: 'ag-center-header',
  },
  {
    field: 'cretDt',
    headerName: '등록일자',
    width: 150,
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
  // receiptPageStore.openUpdatePopup(result?.data ?? null, selectedScriptSeq.value)

  if (!receiptPageStore.formDtlData?.uuid) {
    showAlert('수정할 항목이 존재하지 않습니다.')
    // receiptPageStore.closeScriptPopup()
  }
}

const pushFormTypeCd = async () => {
  const res = await getCommonCodeList('FORM_TYPE_CD')
  formTypeCd.value = [
    ...res.map((item) => ({ value: item.code, label: item.title })),
    { value: '0', label: '공통' },
  ]
}

onBeforeMount(() => {
  setRange({ months: 0, days: 7 })
  pushFormTypeCd()
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

  formData.value.startDate = formatDate(start)
  formData.value.endDate = formatDate(end)
}
</script>

<style scoped></style>
