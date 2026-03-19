<template>
  <DataTable :value="datas" striped-rows :row-hover="true" data-key="id" table-class="data-table-inner-class"
    @row-click="clickRow" paginator :loading="isLoading" :rows="10" :rowsPerPageOptions="[10, 20, 50, 100]"
    showGridlines>
    <template #empty>
      <div class="data-table-empty-box">
        <span>조회 결과가 없습니다.</span>
      </div>
    </template>
    <Column field="title" header="제목">
      <template #body="{ data }">
        <div class="flex gap-4">
          <span class="grow max-w-96 truncate">{{ data.title }}</span>
          <Badge v-if="data.status && data.status === '2'" class="flex-none bg-white text-red-500">New</Badge>
        </div>
      </template>
    </Column>
    <Column field="reg_date" header="등록일시" class="w-42"></Column>
  </DataTable>
</template>

<script setup>
import { onMounted, ref } from 'vue'

const isLoading = ref(false)
const datas = ref([
  {
    id: 1,
    title: '[정책] 공지사항 제목 입니다.',
    status: '2',
    reg_date: '2026-07-25 08:00',
  },
  {
    id: 2,
    title:
      '공간의 길이 만큼만 제목이 표시되고 말줄임 표시…공간의 길이 만큼만 제목이 표시되고 말줄임 표시…공간의 길이 만큼만 제목이 표시되고 말줄임 표시…',
    status: '2',
    reg_date: '2026-07-25 08:10',
  },
  {
    id: 3,
    title: '공지사항 3',
    reg_date: '2026-07-25 08:20',
  },
  {
    id: 4,
    title: '공지사항 2',
    reg_date: '2026-07-25 08:30',
  },
  {
    id: 5,
    title: '공지사항 1',
    reg_date: '2026-07-25 08:40',
  },
  {
    id: 6,
    title: '공지사항 4',
    reg_date: '2026-07-25 08:20',
  },
  {
    id: 7,
    title: '공지사항 5',
    reg_date: '2026-07-25 08:30',
  },
  {
    id: 8,
    title: '공지사항 6',
    reg_date: '2026-07-25 08:40',
  },
  {
    id: 9,
    title: '공지사항 7',
    reg_date: '2026-07-25 08:30',
  },
  {
    id: 10,
    title: '공지사항 8',
    reg_date: '2026-07-25 08:40',
  },
])

const clickRow = (e) => {
  console.log(e)
}

onMounted(() => {
  // 실제 API 호출이 필요한 경우, 아래와 같이 isLoading 상태를 활용하여 로딩 상태를 관리할 수 있습니다.
  isLoading.value = true
  setTimeout(() => {
    // API 호출 후 데이터 업데이트
    isLoading.value = false
  }, 1000)
})
</script>

<style scoped></style>
