<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신분증 목록 조회"
    @open="onOpen"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfDataTable
      :columns="colDefs"
      :datas="datas"
      :total="datas.length"
      show-single-check
      @selected="onSelected"
      :rows="6"
      flexGrid
    >
      <template #count-prepend>스캔 목록</template>
    </MsfDataTable>

    <!-- 임시 신분증 추가 폼 -->
    <div
      v-if="isLocal"
      class="temp-add-form ut-mt-15 ut-p-10"
      style="border: 1px dashed #ccc; border-radius: 4px"
    >
      <p style="font-weight: bold; font-size: 13px; margin-bottom: 8px">임시 신분증 데이터 추가</p>
      <MsfStack type="field" style="gap: 10px; align-items: flex-end">
        <div class="ut-flex-1">
          <label style="font-size: 11px; display: block; margin-bottom: 4px"
            >서류ID (frmpapId)</label
          >
          <MsfInput v-model="inputFrmpapId" placeholder="예: test-id" />
        </div>
        <div class="ut-flex-1">
          <label style="font-size: 11px; display: block; margin-bottom: 4px">고객명 (custNm)</label>
          <MsfInput v-model="inputCustNm" maxlength="100" placeholder="예: 홍길동" />
        </div>
        <div>
          <label style="font-size: 11px; display: block; margin-bottom: 4px"
            >가입유형 (operTypeCd)</label
          >
          <MsfSelect
            v-model="inputOperTypeCd"
            title="가입유형"
            :options="[
              { label: '신규(NAC3)', value: 'NAC3' },
              { label: '번호이동(MNP3)', value: 'MNP3' },
              { label: '기변(HDN3)', value: 'HDN3' },
              { label: '기변(HCN3)', value: 'HCN3' },
              { label: '명의변경(MCN3)', value: 'MCN3' },
            ]"
            class="ut-w-120"
          />
        </div>
        <MsfButton variant="subtle" @click="addTempId">임시추가</MsfButton>
      </MsfStack>
    </div>

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
import { isLocal } from '@/libs/utils/env.utils'
import { formatDatetime } from '@/libs/utils/date.utils.js'
import { useMsfStepStore } from '@/stores/msf_step'

const stepStore = useMsfStepStore()
const props = defineProps({
  modelValue: Boolean,
  agentCd: { type: String, default: '' },
  identityTypeCode: { type: String, default: '' },
  operTypeCd: { type: String, default: '' },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const datas = ref([])
const selectedRow = ref()

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const addedDummyIds = ref([])
const inputFrmpapId = ref('')
const inputCustNm = ref('')
const inputOperTypeCd = ref('NAC3')

const addTempId = () => {
  if (!inputFrmpapId.value.trim()) {
    showAlert('서류ID를 입력해주세요.')
    return
  }
  if (!inputCustNm.value.trim()) {
    showAlert('고객명을 입력해주세요.')
    return
  }

  const newId = {
    frmpapId: inputFrmpapId.value.trim(),
    wapplRegDate: new Date()
      .toISOString()
      .replace(/[^0-9]/g, '')
      .slice(0, 14),
    custNm: inputCustNm.value.trim(),
    custIdntNoIndCd: '1',
    custIdntNo: '900101',
    realIssuDate: '20200101',
    knoteIdentityEssNo: '9001011234567',
    operTypeCd: inputOperTypeCd.value,
  }

  if (props.identityTypeCode === '01') {
    newId.custIdntNoIndCd = '1'
  } else if (props.identityTypeCode === '02') {
    newId.custIdntNoIndCd = '5'
    newId.driveLicnsNo = '121234567890'
    newId.identityIssuRegion = '서울'
  } else if (props.identityTypeCode === '06') {
    newId.custIdntNoIndCd = '4'
  }

  addedDummyIds.value.push(newId)
  inputFrmpapId.value = ''
  inputCustNm.value = ''

  fetchIdList()
}

/**
 * 1. 신분증 목록 조회 (K-NOTE)
 */
const fetchIdList = async () => {
  try {
    const res = await post(
      '/api/form/knote/scaninfo/list',
      {
        agentCd: props.agentCd,
        operTypeCd: props.operTypeCd,
        parentScanId: stepStore.parentScanId,
      },
      { skipAlert: true },
    )

    let list = []
    if (res && res.code === '0000' && res.data?.resData?.list?.length > 0) {
      list = [...res.data.resData.list]
    }

    // 임시 추가한 데이터를 결과 목록 하단에 추가
    list = [...list, ...addedDummyIds.value]

    if (props.identityTypeCode) {
      datas.value = list.filter((item) => {
        if (props.identityTypeCode === '01') {
          return item.custIdntNoIndCd === '1'
        } else if (props.identityTypeCode === '02') {
          return item.custIdntNoIndCd === '5'
        } else if (props.identityTypeCode === '06') {
          return item.custIdntNoIndCd === '4'
        }
        return false
      })
    } else {
      datas.value = list
    }
  } catch (error) {
    console.error('Failed to fetch K-NOTE ID list:', error)
    if (props.identityTypeCode) {
      datas.value = addedDummyIds.value.filter((item) => {
        if (props.identityTypeCode === '01') {
          return item.custIdntNoIndCd === '1'
        } else if (props.identityTypeCode === '02') {
          return item.custIdntNoIndCd === '5'
        } else if (props.identityTypeCode === '06') {
          return item.custIdntNoIndCd === '4'
        }
        return false
      })
    } else {
      datas.value = addedDummyIds.value
    }
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
    const res = await post(
      '/api/form/knote/scaninfo/check',
      {
        frmpapId,
        agentCd: props.agentCd || '',
      },
      { skipAlert: true },
    )
    if (res && res?.data?.resCode === '0000') {
      // API 응답의 resData를 포함하여 부모에게 전달
      emit('confirm', {
        ...selectedRow.value,
        ...res.data?.resData,
      })
      onClose()
    } else {
      onClose()
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
  {
    field: 'wapplRegDate',
    headerName: '스캔일시',
    width: 240,
    cellStyle: { textAlign: 'center' },
    valueFormatter: (params) => {
      if (!params.value) return ''
      return formatDatetime(params.value) || params.value
    },
  },
  { field: 'custNm', headerName: '이름', flex: 1, cellStyle: { textAlign: 'left' } },
  {
    field: 'custIdntNoIndCd',
    headerName: '신분증 유형',
    width: 120,
    cellStyle: { textAlign: 'center' },
    valueFormatter: (params) =>
      params.value === '1'
        ? '주민등록증'
        : params.value === '5'
          ? '운전면허증'
          : params.value === '4'
            ? '외국인등록증'
            : params.value,
  },
  { field: 'custIdntNo', headerName: '생년월일', width: 100, cellStyle: { textAlign: 'center' } },
])

const onSelected = (data) => {
  selectedRow.value = data
}
</script>

<style scoped></style>
