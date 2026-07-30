<template>
  <div class="main-container">
    <div class="sample-box">
      <div class="sample-inner-box">
        <div class="sample-left">
          <label>데이터 그리드(빈 데이터):</label>
          <MsfDataTable :columns="colDefs" :datas="[]" @selected="onSelected" />
          선택: {{ selectedRowEmpty }}
        </div>
        <div class="sample-right">
          <label>데이터 그리드:</label>
          <MsfDataTable
            :columns="colDefs"
            :datas="datas"
            :total="datas.length"
            @selected="onSelected"
          />
          선택: {{ selectedRow }}
        </div>
      </div>
    </div>
    <div class="sample-box">
      <div class="sample-inner-box">
        <div class="sample-left">
          <label>데이터 그리드(싱글 선택):</label>
          <MsfDataTable
            :columns="colDefs"
            :datas="datas"
            :total="datas.length"
            show-single-check
            @selected="onSelected"
          />
          선택: {{ selectedRow }}
        </div>
        <div class="sample-right">
          <label>데이터 그리드(멀티 선택):</label>
          <MsfDataTable
            :columns="colDefs"
            :datas="datas"
            :total="datas.length"
            show-multi
            @selected="onSelectedMulti"
          />
          선택: {{ selectedRowMulti }}
        </div>
      </div>
    </div>
    <div class="sample-box">
      <label>데이터 그리드(페이징):</label>
      <MsfDataTable
        ref="pagingRef"
        :columns="colDefsPaging"
        url="http://localhost:7180/list.json"
        :params="paramsPaging"
        show-paging
        :is-search="false"
        @success="onSuccessPaging"
        @failed="onFailedPaging"
        @selected="onSelectedPaging"
        @movePage="onMovePage"
      >
        <template #buttons>
          <input type="text" v-model="searchValuePaging" />
          <button @click="onClickSearchPaging">검색</button>
          <button @click="() => (page = 15)">15페이지 이동</button>
        </template>
      </MsfDataTable>
      페이지: {{ page }}, 선택: {{ selectedRowPaging }}
    </div>
    <div class="sample-box">
      <label>데이터 그리드(제목 그룹):</label>
      <MsfDataTable :columns="multiColDefsPaging" :datas="[]" @selected="onSelected" />
      선택: {{ selectedRowEmpty }}
    </div>
    <div class="sample-box">
      <label>페이징:</label>
      <MsfPagination v-model:page="page" v-model:total="total" v-model:items-per-page="rows" />
      선택: {{ page }}
    </div>
    <!-- <div class="sample-box">
      <label>웹에디터:</label>
      <MsfWebEditor v-model="webEditorContent" class="h-48!" />
      입력: {{ webEditorContent }}
    </div> -->
    <div class="sample-box">
      <div class="sample-inner-box">
        <div class="sample-left">
          <label>
            <span>트리 목록 선택:</span>
            <button class="" @click="() => (expanded = !expanded)">
              {{ expanded ? '숨김' : '펼침' }}
            </button>
          </label>
          <MsfTreeRoot
            ref="treeRef"
            :data="menuStore.menus"
            v-model="selectedTreeNode"
            :default-expanded="expanded"
            class="h-96"
          />
          결과: {{ selectedTreeNode }}
        </div>
        <div class="sample-right">
          <label>
            <span>트리 목록 선택(다중 선택):</span>
            <button class="" @click="() => (expandedMulti = !expandedMulti)">
              {{ expandedMulti ? '숨김' : '펼침' }}
            </button>
            <button class="" @click="onClickSelectTreeNodeMulti">
              {{
                selectedTreeMultiNode && selectedTreeMultiNode.length > 0 ? '전체해제' : '전체선택'
              }}
            </button>
          </label>
          <MsfTreeRoot
            ref="treeMultiRef"
            :data="menuStore.menus"
            v-model="selectedTreeMultiNode"
            :default-expanded="expandedMulti"
            mode="multi"
            class="h-96"
          />
          결과: {{ selectedTreeMultiNode }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useMsfMenuStore } from '@/stores/msf_menu'

const datas = ref([
  { make: 'Tesla', telephone: '01012345678', price: 64950000, electric: true },
  { make: 'Ford', telephone: '010-1234-5678', price: 33850000, electric: false },
  { make: 'Toyota', telephone: '0212345678', price: 29600000, electric: false },
  { make: 'Tesla', telephone: '02-1234-5678', price: 64950000, electric: true },
  { make: 'Ford', telephone: '15881234', price: 33850000, electric: false },
  { make: 'Toyota', telephone: '1588-1234', price: 29600000, electric: false },
  { make: 'Tesla', telephone: '02-1234-5678', price: 64950000, electric: true },
  { make: 'Ford', telephone: '02-****-5678', price: 33850000, electric: false },
  { make: 'Toyota', telephone: '02-123-4567', price: 29600000, electric: false },
  { make: 'Tesla', telephone: '02-***-4567', price: 64950000, electric: true },
  { make: 'Ford', telephone: '03156781234', price: 33850000, electric: false },
  { make: 'Toyota', telephone: '031****1234', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '0505-5678-1234', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '0505-****-1234', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '070-5678-1234', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '070-****-1234', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '080-5678-1234', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '080-****-1234', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '1588-1234', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '1588-****', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '010-1234-5678', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '010-****-5678', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '011-123-4567', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '011-***-4567', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '01012345678', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '010****5678', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '0111234567', price: 29600000, electric: false },
  { make: 'Toyota', telephone: '011***4567', price: 29600000, electric: false },
])
const colDefs = ref([
  { field: 'make' },
  { field: 'telephone', type: 'telephone' }, // 컬럼 타입 추가(전화번호)_20260522
  { field: 'price', type: 'currency' },
  { field: 'electric' },
])
const selectedRow = ref()
const onSelected = (data) => {
  selectedRow.value = data
}
const selectedRowMulti = ref([])
const onSelectedMulti = (data) => {
  selectedRowMulti.value = data
}
const colDefsPaging = ref([
  { headerName: '아이디', field: 'id' },
  { headerName: '제목', field: 'title' },
  { headerName: '분류', field: 'category' },
  { headerName: '상표', field: 'brand' },
  { headerName: '가격', field: 'price', type: 'currency' },
  { headerName: '등록일자', field: 'meta.createdAt', type: 'date' },
  { headerName: '수정일시', field: 'meta.updatedAt', type: 'datetime' },
])
const multiColDefsPaging = ref([
  { headerName: '아이디', field: 'id' },
  { headerName: '제목', field: 'title' },
  { headerName: '분류', field: 'category' },
  { headerName: '상표', field: 'brand' },
  { headerName: '가격', field: 'price', type: 'currency' },
  { headerName: '등록일자', field: 'meta.createdAt', type: 'date' },
  { headerName: '수정일시', field: 'meta.updatedAt', type: 'datetime' },
])

const pagingRef = ref()
const searchValuePaging = ref('')
const paramsPaging = reactive({})
const selectedRowPaging = ref([])
const onSuccessPaging = (data) => {
  console.log('데이터 로드 성공:', data)
}
const onFailedPaging = (error) => {
  console.error('데이터 로드 실패:', error)
}
const onClickSearchPaging = () => {
  Object.assign(paramsPaging, { value: searchValuePaging.value })
  pagingRef.value.search()
}
const onSelectedPaging = (data) => {
  selectedRowPaging.value = data
}
const onMovePage = (data) => {
  page.value = data
}
const selectedRowEmpty = ref()

const page = ref(3)
const total = ref(1000)
const rows = ref(10)

const menuStore = useMsfMenuStore()
const treeRef = ref(null)
const selectedTreeNode = ref()
const expanded = ref(false)

const treeMultiRef = ref(null)
const selectedTreeMultiNode = ref()
const expandedMulti = ref(false)
const onClickSelectTreeNodeMulti = () => {
  if (treeMultiRef.value) {
    if (selectedTreeMultiNode.value && selectedTreeMultiNode.value.length > 0) {
      treeMultiRef.value.deselectAll()
    } else {
      treeMultiRef.value.selectAll()
    }
  }
}

const webEditorContent = ref('')
</script>

<style lang="scss" scoped>
.main-container {
  padding: 32px;
  label {
    font-weight: bold;
    font-size: 16px;
    margin-block: 40px 10px;
    display: block;
  }
  .sample-box {
    margin-block: 10px 40px;
  }
}
</style>
