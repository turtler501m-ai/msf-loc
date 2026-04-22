<script setup>
import { post } from '@/libs/api/msf.api'
import { onMounted, ref } from 'vue'

const props = defineProps({
  modelValue: Boolean,
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
const chargeInfoDatas = ref([
  { make: 'Tesla', model: 'Model Y', price: 64950 },
  { make: 'Ford', model: 'F-Series', price: 33850 },
  { make: 'Toyota', model: 'Corolla', price: 29600 },
  { make: 'Tesla', model: 'Model Y', price: 64950 },
  { make: 'Ford', model: 'F-Series', price: 33850 },
  { make: 'Toyota', model: 'Corolla', price: 29600 },
])
const selectedRow = ref()
const onSelected = (data) => {
  selectedRow.value = data
}

const realtimeChargeInfoList = async () => {
  const res = await post('/api/form/real-time-charge/list', {})
  const { code, data } = res
  const { amntDto } = data
  chargeInfoDatas.value = amntDto.map((obj) => ({
    make: obj?.gubun,
    price: obj?.payMent,
  }))
}

onMounted(realtimeChargeInfoList)

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
