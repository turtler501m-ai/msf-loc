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
      <MsfButton variant="toggle" @click="onReplicate" :readonlyMsg="alertReplicateMsg"
        >복사하기</MsfButton
      >
      <MsfButton variant="subtle" @click="onView" :readonlyMsg="alertViewMsg">열람하기</MsfButton>
    </template>
  </MsfDataTable>
  <!-- 비밀번호 확인 모달 -->
  <MsfPasswordInputModal
    v-model="isModalOpen"
    :form-type="appFormType"
    :request-key="appFormKey"
    :document-id="documentIds"
  />
</template>

<script setup>
import { onBeforeMount, onUnmounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { post } from '@/libs/api/msf.api'
import { storeToRefs } from 'pinia'
import { showAlert } from '@/libs/utils/comp.utils'
// import { showConfirm } from '@/libs/utils/comp.utils'
import { getCommonCodeList } from '@/libs/utils/comn.utils.js'
import { formatDate } from '@/libs/utils/date.utils'
import { storeReceiptPage } from '@/stores/receiptpage'
import { useMsfUserStore } from '@/stores/msf_user'

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
    field: 'procCd',
    headerName: '진행 상태',
    flex: 1,
    cellStyle: {
      textAlign: 'center',
    },
    valueFormatter: (params) => {
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

const router = useRouter()
const receiptPageStore = storeReceiptPage()
const { formData } = storeToRefs(receiptPageStore)
const formTypeCd = ref([])
const isLoaded = ref(false)
const pagingRef = ref()
const selectedRowPaging = ref([])
const selectedScriptSeq = ref(null)
const selectedFormType = ref(null)
const alertReplicateMsg = ref('복사할 항목을 선택해주세요.')
const alertViewMsg = ref('열람할 항목을 선택해주세요.')
const userStore = useMsfUserStore()

const onClickSearch = () => {
  pagingRef.value.search()
}

const onSelected = (data) => {
  selectedRowPaging.value = data
  selectedFormType.value = data.formTypeCd?.code
  selectedScriptSeq.value = data?.requestKey ?? null
  if (
    selectedScriptSeq.value &&
    data?.cretId === userStore.userInfo?.userId &&
    (selectedFormType.value === '1' || selectedFormType.value === '3')
  ) {
    alertReplicateMsg.value = ''
  } else {
    alertReplicateMsg.value = '복사할 항목을 선택해주세요.'
  }
  if (selectedScriptSeq.value) {
    alertViewMsg.value = ''
  } else {
    alertViewMsg.value = '열람할 항목을 선택해주세요.'
  }
}

const onReplicate = async () => {
  if (!selectedScriptSeq.value) {
    showAlert('복사할 항목을 선택해주세요.')
    return
  }
  if (selectedFormType.value === '2' || selectedFormType.value === '4') {
    showAlert('복사할 수 없는 신청서입니다.')
    return
  }
  const param = {
    requestKey: selectedScriptSeq.value,
  }
  if (selectedFormType.value === '1') {
    const res = await post('/api/form/newchange/copyform', param, { skipAlert: true })
    if (res && res.code === '0000') {
      // showAlert('복사한 requestKey: ' + res.data?.resData?.requestKey)
      await router.push({
        name: 'form',
        params: { domain: 'newchange' },
        state: {
          requestKey: res.data?.resData?.requestKey,
        },
      })
    }
  } else if (selectedFormType.value === '3') {
    // showAlert('선택한 requestKey: ' + selectedScriptSeq.value)
    await router.push({
      name: 'form',
      params: { domain: 'ownerchange' },
      state: {
        requestKey: selectedScriptSeq.value,
      },
    })
  }
}

const onView = () => {
  onClikViewBtn()
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

const pushFormTypeCd = async () => {
  const res = await getCommonCodeList('FORM_TYPE_CD')
  formTypeCd.value = [
    { value: '0', label: '전체' },
    ...res.map((item) => ({ value: item.code, label: item.title })),
  ]
}

onBeforeMount(() => {
  pushFormTypeCd()
  formData.value.searchWord = ''
  if (!formData.value.startDt || !formData.value.endDt) {
    setRange({ months: 0, days: 7 })
  }
  isLoaded.value = true
})

onUnmounted(() => {
  formData.value.startDt = ''
  formData.value.endDt = ''
  formData.value.formTypeOne = '0'
  formData.value.searchWord = ''
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

const isModalOpen = ref(false)
const appFormType = ref('')
const appFormKey = ref('')
const documentIds = ref('')

const onClikViewBtn = () => {
  isModalOpen.value = true
  appFormType.value = selectedRowPaging.value.formTypeCd.code
  appFormKey.value = String(selectedRowPaging.value.requestKey)
  documentIds.value = selectedRowPaging.value.scanId ?? ''
}
</script>

<style scoped></style>
