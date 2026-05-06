<script setup>
import { post } from '@/libs/api/msf.api'
import { ref, watch } from 'vue'

const props = defineProps({
  modelValue: Boolean,
  formData: { type: Object, default: () => ({}) },
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// 테이블 샘플
const colDefs = ref([
  { headerName: '요금항목명', field: 'make', flex: 1 },
  { headerName: '사용금액', field: 'price', width: 212, cellStyle: { textAlign: 'right' } },
])
const chargeInfoDatas = ref([])
const selectedRow = ref()
const onSelected = (data) => {
  selectedRow.value = data
}

const getRealtimeChargePayload = () => {
  const formData = props.formData || {}
  const ctn =
    formData.ctn ||
    `${formData.deviceChgTel1 || ''}${formData.deviceChgTel2 || ''}${formData.deviceChgTel3 || ''}`

  return {
    ncn: formData.ncn || formData.contractNum || '',
    ctn,
    custId: formData.custId || '',
    cpPwdInsert1: formData.cpPwdInsert1 || '',
  }
}

const realtimeChargeInfoList = async () => {
  try {
    const payload = getRealtimeChargePayload()
    console.log('[서비스변경][실시간요금조회] 요청 시작', payload)
    const res = await post('/api/form/real-time-charge/list', payload)
    const data = res?.data || {}
    const amntDto = Array.isArray(data?.amntDto)
      ? data.amntDto
      : data?.amntDto
        ? [data.amntDto]
        : []

    console.log('[서비스변경][실시간요금조회] 응답 수신', {
      code: res?.code,
      message: res?.message,
      count: amntDto.length,
      data,
    })

    chargeInfoDatas.value = amntDto.map((obj) => ({
      make: obj?.gubun || '',
      price: obj?.payMent || '',
    }))
  } catch (error) {
    console.error('[서비스변경][실시간요금조회] 예외 발생', {
      message: error?.message,
      response: error?.response?.data,
    })
    chargeInfoDatas.value = []
  }
}

watch(
  () => props.modelValue,
  (isOpen) => {
    if (isOpen) {
      realtimeChargeInfoList()
    }
  },
)

// MsfDataTable 안쓰신다면 MsfTableList
// 테이블 테스트
// const selectedId = ref(null)

// // MsfTableList 컬럼 정의
// const tableColumns = [
//   { label: '요금항목명', key: 'id', width: '100px' },
//   { label: '사용금액', key: 'title' },
// ]

// // MsfTableList 데이터
// const tableData = ref(
//   Array.from({ length: 20 }, (_, i) => ({
//     id: '추천 요금제',
//     title: '700,000 원',
//   })),
// )
</script>

<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="사용 요금"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfTitleArea level="2" color="primary" noline bold>
      <template #title>
        <p><em class="ut-color-accent">2026-01-01 ~ 2026-01-17</em> 사용 요금 입니다.</p>
      </template>
      <template #content>
        <p class="ut-text-caution">
          2026-01-18 부터는 변경 후 요금제 기준으로 사용요금이 부과 예정
        </p>
      </template>
    </MsfTitleArea>
    <MsfDataTable
      :columns="colDefs"
      :datas="chargeInfoDatas"
      :total="chargeInfoDatas.length"
      :rows="5"
      hideHeader
    />
    <!-- ag-grid 안쓰신다면 MsfTablieList -->
    <!-- <MsfTableList
      :data="tableData"
      :columns="tableColumns"
      :row-view="5"
      :height="5"
      @row-click="onRowClick"
    >
      <template #colgroup>
        <col />
        <col style="width: 212px" />
      </template>

      <template #thead>
        <tr>
          <th scope="col">요금항목명</th>
          <th scope="col">사용금액</th>
        </tr>
      </template>

      <template #tbody="{ items }">
        <tr
          v-for="item in items"
          :key="item.id"
          :class="{ 'is-selected': selectedId === item.id }"
          @click="onRowClick(item)"
        >
          <td>{{ item.id }}</td>
          <td class="ut-text-right">{{ item.title }}</td>
        </tr>
      </template>
    </MsfTableList> -->
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="primary">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<style lang="scss" scoped></style>
