<template>
  <!-- 검색박스 -->
  <MsfBox margin="0">
    <MsfStack vertical class="ut-ai-center">
      <MsfStack type="field">
        <MsfInput v-model="formData.searchField" class="ut-w-347" placeholder="검색어 입력" />
        <MsfButton variant="primary" noMinWidth @click="onClickSearchPaging">검색</MsfButton>
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
    @movePage="onMovePage"
    hideParentheses
  >
    <template #buttons>
      <MsfButton variant="toggle" disabled>수정</MsfButton>
      <MsfButton variant="toggle" active>수정</MsfButton>
    </template>
  </MsfDataTable>
</template>

<script setup>
import { ref, reactive } from 'vue'

const pagingRef = ref()
const onClickSearchPaging = () => {
  pagingRef.value.search()
}

const colDefsPaging = ref([
  { headerName: '작성일자', field: 'id', flex: 1 },
  { headerName: '이름', field: 'title', flex: 1 },
  { headerName: '연락처', field: 'category', flex: 1 },
  { headerName: '가입유형', field: 'brand', flex: 1 },
  { headerName: '신청서작성 여부', field: 'price', flex: 1 },
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
})
</script>

<style scoped></style>
