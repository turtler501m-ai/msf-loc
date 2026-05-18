<template>
  <MsfBox margin="0">
    <MsfStack vertical>
      <MsfStack type="field" nowrap class="ut-w100p search-row">
        <span class="search-label">접수일자</span>
        <MsfDateRange
          v-model:from="searchForm.startDt"
          v-model:to="searchForm.endDt"
          :disabled="isDateDisabled"
          class="date-range-wrap"
        />
        <MsfSelect
          title="신청서 구분"
          v-model="searchForm.applyTypeCd"
          :options="applyTypeOptions"
          placeholder="신청서 구분"
          class="search-select"
        />
        <MsfSelect
          title="처리상태"
          v-model="searchForm.procCd"
          :options="procCdOptions"
          placeholder="처리상태"
          class="search-select"
        />
        <MsfSelect
          title="검색구분"
          v-model="searchForm.searchGbn"
          :options="searchGbnOptions"
          placeholder="검색구분"
          class="search-select"
          @change="onSearchGbnChange"
        />
        <MsfInput
          v-model="searchForm.searchName"
          placeholder="검색어"
          :disabled="!searchForm.searchGbn"
          class="search-input"
          @keyup.enter="onSearch"
        />
        <MsfButton variant="primary" noMinWidth class="search-btn" @click="onSearch">조회</MsfButton>
      </MsfStack>
    </MsfStack>
  </MsfBox>

  <MsfDataTable
    :columns="colDefs"
    :datas="tableData"
    :total="totalCount"
    show-paging
    :rows="rows"
    :page="page"
    show-single-check
    @selected="onSelected"
    @movePage="onMovePage"
    @row-double-click="onRowDoubleClick"
  >
    <template #buttons>
      <MsfButton variant="subtle" :disabled="!selectedRow" @click="openDetail(selectedRow)">상세보기</MsfButton>
    </template>
  </MsfDataTable>

  <MsfDialog :is-open="detailOpen" title="상세보기" @close="closeDetail">
    <template #default>
      <MsfButtonGroup class="popup-top-buttons">
        <MsfButton variant="subtle" :disabled="!isCancelProcessTarget" @click="onClickCancelCheck">
          상태확인
        </MsfButton>
        <MsfButton variant="subtle" :disabled="!canCompleteCancel" @click="onClickReceiptComplete">
          해지완료
        </MsfButton>
        <MsfButton variant="subtle" :disabled="!canRejectCancel" @click="onClickReject">반려처리</MsfButton>
        <MsfButton variant="subtle" :disabled="!canRevertCancel" @click="onClickRevert">완료취소</MsfButton>
        <MsfButton variant="subtle" @click="closeDetail">닫기</MsfButton>
      </MsfButtonGroup>

      <MsfStack vertical class="detail-wrap">
        <MsfStack type="field">
          <span class="field-label">신청번호</span>
          <MsfInput :model-value="detail.applyNo" readonly class="ut-flex-1" />
          <span class="field-label">접수일자</span>
          <MsfInput :model-value="detail.receiptDt" readonly class="ut-flex-1" />
        </MsfStack>
        <MsfStack type="field">
          <span class="field-label">신청서 구분</span>
          <MsfInput :model-value="detail.applyTypeLabel" readonly class="ut-flex-1" />
          <span class="field-label">처리상태</span>
          <MsfInput :model-value="detail.procCdLabel" readonly class="ut-flex-1" />
        </MsfStack>
        <MsfStack type="field">
          <span class="field-label">고객명</span>
          <MsfInput :model-value="detail.cstmrNm" readonly class="ut-flex-1" />
          <span class="field-label">생년월일</span>
          <MsfInput :model-value="detail.birthDt" readonly class="ut-flex-1" />
        </MsfStack>
        <MsfStack type="field">
          <span class="field-label">대리점명</span>
          <MsfInput :model-value="detail.agencyNm" readonly class="ut-flex-1" />
          <span class="field-label">신청자</span>
          <MsfInput :model-value="detail.applicantNm" readonly class="ut-flex-1" />
        </MsfStack>
        <MsfStack type="field">
          <span class="field-label">메모</span>
          <MsfInput
            v-model="detail.memo"
            :readonly="!canEditMemo"
            placeholder="처리 메모를 입력하세요."
            class="ut-flex-1"
          />
        </MsfStack>
      </MsfStack>
    </template>
  </MsfDialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { post as msfPost } from '@/libs/api/msf.api.js'
import { useMsfAlertStore } from '@/stores/msf_alert.js'
import { getCommonCodeList } from '@/libs/utils/comn.utils.js'

const alertStore = useMsfAlertStore()

const FORM_TYPE_CANCEL = '4' // 서비스해지

const CANCEL_PROC_OPTIONS = [
  { label: '상태 전체', value: '' },
  { label: '접수', value: 'RC' },
  { label: '처리중', value: 'RQ' },
  { label: '완료', value: 'CP' },
  { label: '취소', value: 'BK' },
]

const applyTypeOptions = ref([{ label: '신청서 전체', value: '' }])
const baseProcCdOptions = ref([{ label: '상태 전체', value: '' }])
const cstmrTypeOptions = ref([])
const identityCertTypeOptions = ref([])

onMounted(async () => {
  const [formTypeList, procSttusList, cstmrTypeList, identityCertTypeList] = await Promise.all([
    getCommonCodeList('FORM_TYPE_CD'),
    getCommonCodeList('PROC_STTUS_CD'),
    getCommonCodeList('CSTMR_TYPE_CD'),
    getCommonCodeList('IDENTITY_CERT_TYPE_CD'),
  ])
  applyTypeOptions.value = [
    { label: '신청서 전체', value: '' },
    ...(formTypeList || []).map((item) => ({ label: item.title, value: item.code })),
  ]
  baseProcCdOptions.value = [
    { label: '상태 전체', value: '' },
    ...(procSttusList || []).map((item) => ({ label: item.title, value: item.code })),
  ]
  cstmrTypeOptions.value = (cstmrTypeList || []).map((item) => ({ label: item.title, value: item.code }))
  identityCertTypeOptions.value = (identityCertTypeList || []).map((item) => ({ label: item.title, value: item.code }))
})

const searchGbnOptions = [
  { label: '계약번호', value: 'CONTRACT_NUM' },
  { label: '해지휴대폰번호', value: 'CANCEL_MOBILE_NO' },
  { label: '고객명', value: 'CSTMR_NM' },
]

const searchForm = reactive({
  startDt: '',
  endDt: '',
  applyTypeCd: '',
  searchGbn: '',
  searchName: '',
  procCd: '',
})

const tableData = ref([])
const totalCount = ref(0)
const page = ref(1)
const rows = ref(10)
const selectedRow = ref(null)
const detailOpen = ref(false)
const detail = ref({})
const lastClick = ref({ key: null, at: 0 })

const procCdOptions = computed(() =>
  searchForm.applyTypeCd === FORM_TYPE_CANCEL ? CANCEL_PROC_OPTIONS : baseProcCdOptions.value,
)

const isCancelProcessTarget = computed(() => String(detail.value?.formTypeCd || '') === FORM_TYPE_CANCEL)
const canCompleteCancel = computed(() => isCancelProcessTarget.value && ['RC', 'RQ'].includes(detail.value?.procCd))
const canRejectCancel = computed(() => isCancelProcessTarget.value && ['RC', 'RQ'].includes(detail.value?.procCd))
const canRevertCancel = computed(() => isCancelProcessTarget.value && detail.value?.procCd === 'CP')
const canEditMemo = computed(() => canCompleteCancel.value || canRejectCancel.value)

watch(
  () => searchForm.applyTypeCd,
  () => {
    searchForm.procCd = ''
  },
)

const isDateDisabled = computed(() => !!searchForm.searchGbn)

function onSearchGbnChange() {
  if (searchForm.searchGbn) {
    searchForm.startDt = ''
    searchForm.endDt = ''
  } else {
    searchForm.searchName = ''
  }
}

const COMPLETE_DEFAULT_CODES = {
  itgOderWhyCd: '01',
  aftmnIncInCd: '01',
  apyRelTypeCd: '01',
  custTchMediCd: '01',
  smsRcvYn: 'Y',
}

function summarizeSearchForm() {
  return {
    procCd: searchForm.procCd || null,
    formTypeCd: searchForm.applyTypeCd || null,
    searchGbn: searchForm.searchGbn || null,
    hasSearchName: !!searchForm.searchName,
    startDt: searchForm.startDt || null,
    endDt: searchForm.endDt || null,
    pageNum: page.value,
    rowSize: rows.value,
  }
}

function summarizeRow(row) {
  if (!row) return null
  return {
    requestKey: row.requestKey || null,
    applyNo: row.applyNo || null,
    procCd: row.procCd || null,
    procCdLabel: row.procCdLabel || '',
    formTypeCd: row.formTypeCd || row.applyTypeCd || null,
    applyTypeLabel: row.applyTypeLabel || '',
    cstmrNm: row.cstmrNm || '',
  }
}

function summarizeListResponse(res) {
  return {
    hasData: Array.isArray(res?.data),
    dataCount: Array.isArray(res?.data) ? res.data.length : 0,
    totalCount: Number(res?.meta?.page?.totalCount || 0),
    message: res?.message || '',
  }
}

function onMovePage(nextPage) {
  console.log('[신청서관리][목록조회] 페이지 이동', {
    currentPage: page.value,
    nextPage,
  })
  page.value = nextPage
  fetchList()
}

const colDefs = ref([
  { headerName: '신청번호', field: 'applyNo', width: 120 },
  { headerName: '접수일자', field: 'receiptDt', width: 170 },
  { headerName: '신청서 구분', field: 'applyTypeLabel', width: 240 },
  { headerName: '처리상태', field: 'procCdLabel', width: 150 },
  { headerName: '고객명', field: 'cstmrNm', width: 100 },
  { headerName: '생년월일', field: 'birthDt', width: 120 },
  { headerName: '고객 유형', field: 'cstmrTypeLabel', width: 140 },
  { headerName: '인증', field: 'authLabel', width: 170 },
  { headerName: '대리점코드', field: 'agencyCd', width: 150 },
  { headerName: '대리점명', field: 'agencyNm', width: 200 },
  { headerName: '매장코드', field: 'storeCd', width: 150 },
  { headerName: '매장명', field: 'storeNm', width: 200 },
  { headerName: '신청자', field: 'applicantNm', width: 200 },
])

function findLabel(options, value) {
  return options.find((o) => o.value === value)?.label || value || '-'
}

function buildApplicantNm(row) {
  const base = row.managerNm || '-'
  const tail = String(row.requestKey || '').slice(-4)
  return tail ? `${base}(${tail})` : base
}

function normalizeRequestKey(value) {
  if (value === null || value === undefined || value === '') return null
  const num = Number(value)
  return Number.isFinite(num) && num > 0 ? num : null
}

function mapRow(row) {
  return {
    ...row,
    requestKey: normalizeRequestKey(row.requestKey),
    applyNo: row.requestKey || row.applyNo || '-',
    receiptDt: row.cretDt || row.receiptDt || '-',
    applyTypeLabel: (() => {
      const base = findLabel(applyTypeOptions.value, row.formTypeCd || row.applyTypeCd)
      return row.operTypeCd ? `${base}(${row.operTypeCd})` : base
    })(),
    procCdLabel: findLabel([...baseProcCdOptions.value, ...CANCEL_PROC_OPTIONS], row.procCd),
    cstmrNm: row.cstmrNm || '-',
    birthDt: row.cstmrBirth || '-',
    cstmrTypeLabel: findLabel(cstmrTypeOptions.value, row.cstmrTypeCd),
    authLabel: findLabel(identityCertTypeOptions.value, row.identityCertTypeCd),
    agencyCd: row.agentCd || '-',
    agencyNm: row.agentNm || '-',
    storeCd: row.shopCd || '-',
    storeNm: row.shopNm || '-',
    applicantNm: buildApplicantNm(row),
    memo: row.memo || '',
  }
}

function unwrapCommonResponse(response) {
  return response?.data ?? response
}

function unwrapFormResponse(response) {
  return unwrapCommonResponse(response)
}

function isFormOk(formResponse) {
  return formResponse?.resCode === '0000'
}

async function fetchList() {
  const payload = {
    procCd: searchForm.procCd || null,
    formTypeCd: searchForm.applyTypeCd || null,
    searchGbn: searchForm.searchGbn || null,
    searchName: searchForm.searchName || null,
    startDt: searchForm.startDt || null,
    endDt: searchForm.endDt || null,
    page: { pageNum: page.value, rowSize: rows.value },
  }
  console.log('[신청서관리][목록조회] 요청 시작', summarizeSearchForm())
  try {
    const res = await msfPost('/api/msf/admin/application/list', payload)
    const listResponse = unwrapCommonResponse(res)
    console.log('[신청서관리][목록조회] 응답 수신', summarizeListResponse(listResponse))
    if (!Array.isArray(listResponse?.data)) {
      tableData.value = []
      totalCount.value = 0
      console.warn('[신청서관리][목록조회] 응답 실패', {
        message: res?.message,
        response: res,
      })
      alertStore.openAlert(res?.message || '조회에 실패했습니다.')
      return
    }
    tableData.value = listResponse.data.map(mapRow)
    totalCount.value = Number(listResponse?.meta?.page?.totalCount || 0)
    selectedRow.value = null
    console.log('[신청서관리][목록조회] 화면 데이터 반영 결과', {
      rowCount: tableData.value.length,
      totalCount: totalCount.value,
      page: page.value,
      selectedRow: selectedRow.value,
    })
  } catch (e) {
    tableData.value = []
    totalCount.value = 0
    console.error('[신청서관리][목록조회] 예외 발생', {
      message: e?.message,
      response: e?.response?.data,
    })
    alertStore.openAlert('조회에 실패했습니다.')
  }
}

function onSearch() {
  console.log('[신청서관리][목록조회] 조회 버튼 클릭', summarizeSearchForm())
  page.value = 1
  fetchList()
}

function onSelected(row) {
  if (!row) {
    selectedRow.value = null
    console.log('[신청서관리][목록선택] 선택 해제')
    return
  }
  selectedRow.value = row
  console.log('[신청서관리][목록선택] 화면 데이터 반영 결과', summarizeRow(row))

  const key = normalizeRequestKey(row?.requestKey ?? row?.applyNo)
  const now = Date.now()
  if (key && lastClick.value.key === key && now - lastClick.value.at < 450) {
    console.log('[신청서관리][상세팝업] 더블클릭 감지', { requestKey: key })
    openDetail(row)
  }
  lastClick.value = { key, at: now }
}

async function fetchDetail(requestKey) {
  const payload = { requestKey }
  console.log('[신청서관리][상세조회] 요청 시작', payload)
  const res = await msfPost('/api/msf/admin/application/get', payload)
  const detailResponse = unwrapCommonResponse(res)
  console.log('[신청서관리][상세조회] 응답 수신', detailResponse)
  return mapRow(detailResponse || {})
}

async function openDetail(row = selectedRow.value) {
  const requestKey = normalizeRequestKey(row?.requestKey ?? row?.applyNo)
  if (!requestKey) {
    console.warn('[신청서관리][상세팝업] 진행 중단', { reason: 'requestKey missing', row: summarizeRow(row) })
    alertStore.openAlert('선택된 행이 없습니다.')
    return
  }
  console.log('[신청서관리][상세팝업] 열기 요청', { requestKey, row: summarizeRow(row) })
  try {
    detail.value = await fetchDetail(requestKey)
    detailOpen.value = true
    console.log('[신청서관리][상세팝업] 화면 데이터 반영 결과', {
      detailOpen: detailOpen.value,
      detail: summarizeRow(detail.value),
    })
  } catch (e) {
    console.error('[신청서관리][상세팝업] 예외 발생', {
      requestKey,
      message: e?.message,
      response: e?.response?.data,
    })
    alertStore.openAlert('상세 조회에 실패했습니다.')
  }
}

function onRowDoubleClick(row) {
  console.log('[신청서관리][상세팝업] 행 더블클릭', summarizeRow(row))
  selectedRow.value = row
  openDetail(row)
}

function closeDetail() {
  console.log('[신청서관리][상세팝업] 닫기', {
    requestKey: detail.value?.requestKey || detail.value?.applyNo || null,
  })
  detailOpen.value = false
}

async function onClickReceiptComplete() {
  const requestKey = normalizeRequestKey(detail.value?.requestKey ?? detail.value?.applyNo)
  if (!requestKey) {
    console.warn('[신청서관리][해지완료] 진행 중단', { reason: 'requestKey missing' })
    alertStore.openAlert('선택된 행이 없습니다.')
    return
  }
  console.log('[신청서관리][해지완료] 요청 시작', { requestKey })
  try {
    const statusRes = await msfPost('/api/msf/admin/application/status/check', { requestKey })
    const statusForm = unwrapFormResponse(statusRes)
    console.log('[신청서관리][해지완료][상태확인] 응답 수신', statusForm)
    if (!isFormOk(statusForm)) {
      console.warn('[신청서관리][해지완료][상태확인] 응답 실패', statusForm)
      alertStore.openAlert(statusForm?.resMessage || '해지완료 처리 가능 상태가 아닙니다.')
      return
    }

    const payload = {
      requestKey,
      ...COMPLETE_DEFAULT_CODES,
      memo: detail.value?.memo || '',
    }
    console.log('[신청서관리][해지완료][처리] 요청 시작', {
      requestKey: payload.requestKey,
      memo: payload.memo,
    })
    const res = await msfPost('/api/msf/admin/application/complete', payload)
    const completeForm = unwrapFormResponse(res)
    console.log('[신청서관리][해지완료][처리] 응답 수신', completeForm)

    if (!isFormOk(completeForm)) {
      console.warn('[신청서관리][해지완료][처리] 응답 실패', completeForm)
      alertStore.openAlert(completeForm?.resMessage || '해지완료 처리에 실패했습니다.')
      return
    }

    console.log('[신청서관리][해지완료] 화면 데이터 반영 결과', {
      requestKey,
      detailOpen: false,
      refreshList: true,
    })
    alertStore.openAlert('해지완료 처리되었습니다.', () => {
      detailOpen.value = false
      fetchList()
    })
  } catch (e) {
    console.error('[신청서관리][해지완료] 예외 발생', {
      requestKey,
      message: e?.message,
      response: e?.response?.data,
    })
    alertStore.openAlert('해지완료 처리에 실패했습니다.')
  }
}

async function onClickReject() {
  const requestKey = normalizeRequestKey(detail.value?.requestKey ?? detail.value?.applyNo)
  if (!requestKey) {
    console.warn('[신청서관리][반려처리] 진행 중단', { reason: 'requestKey missing' })
    alertStore.openAlert('선택된 행이 없습니다.')
    return
  }
  console.log('[신청서관리][반려처리] 확인 팝업 열기', { requestKey })
  alertStore.openConfirm('반려 처리하시겠습니까?', async () => {
    try {
      const payload = {
        requestKey,
        memo: detail.value?.memo || '',
      }
      console.log('[신청서관리][반려처리] 요청 시작', payload)
      const res = await msfPost('/api/msf/admin/application/reject', payload)
      const rejectForm = unwrapFormResponse(res)
      console.log('[신청서관리][반려처리] 응답 수신', rejectForm)
      if (!isFormOk(rejectForm)) {
        console.warn('[신청서관리][반려처리] 응답 실패', rejectForm)
        alertStore.openAlert(rejectForm?.resMessage || '반려 처리에 실패했습니다.')
        return
      }
      console.log('[신청서관리][반려처리] 화면 데이터 반영 결과', {
        requestKey,
        detailOpen: false,
        refreshList: true,
      })
      alertStore.openAlert('반려 처리되었습니다.', () => {
        detailOpen.value = false
        fetchList()
      })
    } catch (e) {
      console.error('[신청서관리][반려처리] 예외 발생', {
        requestKey,
        message: e?.message,
        response: e?.response?.data,
      })
      alertStore.openAlert('반려 처리에 실패했습니다.')
    }
  })
}

async function onClickRevert() {
  const requestKey = normalizeRequestKey(detail.value?.requestKey ?? detail.value?.applyNo)
  if (!requestKey) {
    console.warn('[신청서관리][완료취소] 진행 중단', { reason: 'requestKey missing' })
    alertStore.openAlert('선택된 행이 없습니다.')
    return
  }
  console.log('[신청서관리][완료취소] 확인 팝업 열기', { requestKey })
  alertStore.openConfirm('완료취소 처리하시겠습니까?', async () => {
    try {
      console.log('[신청서관리][완료취소] 요청 시작', { requestKey })
      const res = await msfPost('/api/msf/admin/application/revert', { requestKey })
      const revertForm = unwrapFormResponse(res)
      console.log('[신청서관리][완료취소] 응답 수신', revertForm)
      if (!isFormOk(revertForm)) {
        console.warn('[신청서관리][완료취소] 응답 실패', revertForm)
        alertStore.openAlert(revertForm?.resMessage || '완료취소 처리에 실패했습니다.')
        return
      }
      console.log('[신청서관리][완료취소] 화면 데이터 반영 결과', {
        requestKey,
        detailOpen: false,
        refreshList: true,
      })
      alertStore.openAlert('완료취소 처리되었습니다.', () => {
        detailOpen.value = false
        fetchList()
      })
    } catch (e) {
      console.error('[신청서관리][완료취소] 예외 발생', {
        requestKey,
        message: e?.message,
        response: e?.response?.data,
      })
      alertStore.openAlert('완료취소 처리에 실패했습니다.')
    }
  })
}

async function onClickCancelCheck() {
  const requestKey = normalizeRequestKey(detail.value?.requestKey ?? detail.value?.applyNo)
  if (!requestKey) {
    console.warn('[신청서관리][상태확인] 진행 중단', { reason: 'requestKey missing' })
    return
  }
  console.log('[신청서관리][상태확인] 요청 시작', { requestKey })
  try {
    const res = await msfPost('/api/msf/admin/application/status/check', { requestKey })
    const statusForm = unwrapFormResponse(res)
    console.log('[신청서관리][상태확인] 응답 수신', statusForm)
    if (isFormOk(statusForm)) {
      alertStore.openAlert('상태확인: 정상')
      return
    }
    console.warn('[신청서관리][상태확인] 응답 실패', statusForm)
    alertStore.openAlert(statusForm?.resMessage || '상태확인에 실패했습니다.')
  } catch (e) {
    console.error('[신청서관리][상태확인] 예외 발생', {
      requestKey,
      message: e?.message,
      response: e?.response?.data,
    })
    alertStore.openAlert('상태확인에 실패했습니다.')
  }
}
</script>

<style scoped lang="scss">
.search-label {
  font-size: 0.875rem;
  white-space: nowrap;
  line-height: 2.25rem;
  min-width: 54px;
}

.search-row {
  align-items: center;
  gap: 0.875rem;
}

.search-select {
  flex: 0 0 auto;
  width: 150px;
}

.date-range-wrap {
  flex: 0 0 auto;
  width: 350px;

  :deep([class^='dp__input_wrap']) {
    min-width: 72px !important;
  }
}

.search-input {
  flex: 1 1 0;
  min-width: 220px;
  width: auto;
}

.search-btn {
  flex: 0 0 auto;
  margin-left: auto;
}

.popup-top-buttons {
  margin-bottom: 0.75rem;
}

.detail-wrap {
  gap: 0.5rem;
}

.field-label {
  font-size: 0.875rem;
  white-space: nowrap;
  width: 100px;
  flex-shrink: 0;
}
</style>
