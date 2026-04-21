<template>
  <!-- 검색박스 -->
  <MsfBox margin="0">
    <MsfStack vertical>
      <MsfStack type="field" class="ut-w100p">
        <MsfInput v-model="formData.searchField" class="ut-w-347" placeholder="검색어 입력" />
        <MsfSelect
          title="신청서 구분1"
          v-model="formData.gubun"
          :options="[
            { label: '신청서 구분1', value: 'gubun1' },
            { label: '신청서 구분2', value: 'gubun2' },
          ]"
          placeholder="신청서 구분"
          class="ut-flex-1"
        />
        <MsfButton variant="primary" noMinWidth @click="onClickSearchPaging">검색</MsfButton>
      </MsfStack>
      <MsfStack type="field" class="ut-w-347">
        <MsfDateRange
          v-model:from="rangeDatePickerValue.start"
          v-model:to="rangeDatePickerValue.end"
          class="ut-w100p"
        />
      </MsfStack>
    </MsfStack>
  </MsfBox>
  <!-- 그리드 테이블 -->
  <MsfDataTable
    ref="pagingRef"
    :columns="colDefsPaging"
    url="https://dummyjson.com/products/search"
    :params="paramsPaging"
    show-paging
    :is-search="false"
    :rows="rows"
    show-single-check
    @selected="onSelected"
    @movePage="onMovePage"
  >
    <template #buttons>
      <MsfButton variant="subtle" active>열람하기</MsfButton>
      <MsfButton variant="toggle" disabled>복사하기</MsfButton>
      <MsfButton variant="toggle" active>복사하기</MsfButton>
    </template>
  </MsfDataTable>
</template>

<script setup>
import { ref, reactive } from 'vue'

const pagingRef = ref()
const onClickSearchPaging = () => {
  pagingRef.value.search()
}

const selectedRow = ref()
const onSelected = (data) => {
  selectedRow.value = data
}
const colDefsPaging = ref([
  { headerName: '작성일자', field: 'id', width: 180 },
  { headerName: '신청서구분', field: 'title', width: 165 },
  { headerName: '고객유형', field: 'category', flex: 1 },
  { headerName: '신청자', field: 'brand', flex: 1 },
  { headerName: '진행상태', field: 'price', width: 165 },
  { headerName: '안심백업 여부', field: 'backup', width: 120 },
])
const paramsPaging = ref({})

const onMovePage = (data) => {
  page.value = data
}
const page = ref(3)
const rows = ref(5)

// 퍼블샘플용
const formData = reactive({
  searchField: '', //검색어입력 필드
  gubun: '', //신청서 구분
})
const rangeDatePickerValue = ref({ start: '', end: '' }) //기간
</script>

<style scoped></style>
