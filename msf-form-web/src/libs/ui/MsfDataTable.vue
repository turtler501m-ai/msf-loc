<template>
  <div
    class="msf-grid-container"
    :class="{
      'is-hideHeader': props.hideHeader,
      'is-hideCount': props.hideCount,
      'is-setMaxHeight': !props.showPaging,
    }"
  >
    <div v-if="!props.hideHeader" class="msf-grid-header">
      <div class="msf-grid-header-left">
        <slot name="header-left"></slot>
        <span class="total-count" v-if="!props.hideCount"
          ><slot name="count-prepend"></slot> <span v-if="!props.hideParentheses">(</span>총
          <em>{{ totalNo }}</em
          >건<span v-if="!props.hideParentheses">)</span> <slot name="count-append"></slot>
        </span>
      </div>
      <div class="msf-grid-header-right">
        <slot name="buttons"></slot>
      </div>
    </div>
    <div class="msf-grid-content">
      <AgGridVue
        :gridOptions="gridOptions"
        :localeText="localeText"
        :row-data="rowDatas"
        :column-types="columnTypes"
        :column-defs="props.columns"
        :rowSelection="props.showMulti ? multiOptions : singleOptions"
        class="msf-grid-table"
        :class="{ 'is-single-check': props.showSingleCheck }"
        @grid-ready="onGridReady"
        @selection-changed="onSelectionChanged"
        @row-double-clicked="onRowDoubleClicked"
        :domLayout="props.showPaging ? 'autoHeight' : 'normal'"
      />
      <MsfPagination
        v-if="showPaging"
        v-model:page="pageNo"
        v-model:total="totalNo"
        v-model:items-per-page="rowsNo"
      />
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch, reactive } from 'vue'
import { themeAlpine } from 'ag-grid-community'
import { AG_GRID_LOCALE_KR } from './ag-grid.locale.kr'
import { AgGridVue } from 'ag-grid-vue3'
import { formatDate, formatDatetime } from '@/libs/utils/date.utils'
import { formatCurrency } from '@/libs/utils/string.utils'
import { post } from '@/libs/api/msf.api'

const props = defineProps({
  columns: {
    type: Array,
    required: true,
  },
  datas: {
    types: Array,
    default: () => [],
  },
  url: String,
  method: {
    type: String,
    default: 'GET',
  },
  params: [String, Number, Array, Object],
  showSingleCheck: {
    type: Boolean,
    default: false,
  },
  showMulti: {
    type: Boolean,
    default: false,
  },
  showPaging: {
    type: Boolean,
    default: false,
  },
  page: {
    type: Number,
    default: 1,
  },
  total: {
    type: Number,
    default: 0,
  },
  rows: {
    type: Number,
    default: 10, //페이징모드의 기본값 10 || 페이징아닐때는 rows로 설정하여 최대 높이값지정하여 스크롤지정
  },
  // 테이블 헤더 숨기기
  hideHeader: {
    type: Boolean,
    default: false,
  },
  // 카운트영역 숨기기
  hideCount: {
    type: Boolean,
    default: false,
  },
  //스크롤바 사이즈
  sbSize: { type: Number, default: 4 },
  // 카운트영역의 괄호삭제
  hideParentheses: {
    type: Boolean,
    default: false,
  },
  isSearch: {
    type: Boolean,
    default: false,
  },
})

const emits = defineEmits(['success', 'failed', 'selected', 'movePage', 'row-double-click'])

const localeText = ref(AG_GRID_LOCALE_KR)
const singleOptions = ref({
  mode: 'singleRow',
  checkboxes: props.showSingleCheck,
  enableClickSelection: true,
})
const multiOptions = ref({ mode: 'multiRow' })
const columnTypes = ref({
  currency: {
    valueFormatter: (params) => formatCurrency(params.value),
  },
  date: {
    valueFormatter: (params) => formatDate(params.value),
  },
  datetime: {
    valueFormatter: (params) => formatDatetime(params.value),
  },
})

const gridApi = ref()
const columnApi = ref()
const selectedData = ref()
const rowDatas = ref(props.datas)
const pageNo = ref(props.page)
const totalNo = ref(props.total)
const rowsNo = ref(props.rows)

// 테마 스타일 커스텀
const myTheme = themeAlpine.withParams({
  spacing: 9,
  accentColor: '#191A1B', // active 컬러
  backgroundColor: '#FFFFFF',
  // 헤더
  headerHeight: '52px',
  headerBackgroundColor: '#F8F9FA',
  headerColumnBorder: { color: '#EEEFF2' },
  headerColumnBorderHeight: '100%',
  headerColumnResizeHandleColor: '#EEEFF2',
  headerColumnResizeHandleHeight: '80%',
  headerColumnResizeHandleWidth: '1px',
  // 폰트설정
  fontFamily: '"Pretendard", sans-serif',
  headerFontFamily: '"Pretendard", sans-serif',
  cellFontFamily: '"Pretendard", sans-serif',
  // 보더설정
  rowBorder: { style: 'solid', width: 1, color: '#EEEFF2' },
  columnBorder: { style: 'solid', width: 1, color: '#EEEFF2' },
  rowHeight: '68px',
  // row설정
  selectedRowBackgroundColor: '#F3F4F6', // 선택된 row
  oddRowBackgroundColor: '#FFFFFF', // 짝수 row
  // 아이콘 사이즈
  iconSize: '24px',
})

// Grid 옵션 기본정의할 값
const gridOptions = reactive({
  // 위에서 만든 테마 적용
  theme: myTheme,
  scrollbarWidth: props.sbSize,
  // 모든 컬럼에 기본으로 적용될 속성 (중앙 정렬 등)
  defaultColDef: {
    headerClass: 'ag-center-header', //기본 헤더 중앙정렬
    cellClass: 'ag-center-cell',
  },
  // 선택(체크박스) 컬럼의 기본 설정
  selectionColumnDef: {
    width: 60, // 아이콘 24px + 여백을 고려한 너비
    minWidth: 60,
    maxWidth: 60,
  },
  // 데이터 없는경우 디자인 설정
  overlayNoRowsTemplate: `
  <div class="ag-overlay-no-rows-wrapper">
    <div class="nodata-wrap">검색 결과가 없습니다.</div>
  </div>
  `,
})

const searchInternal = () => {
  if (props.url) {
    gridApi.value.setGridOption('loading', true)
    post(props.url, { ...props.params, page: { pageNum: pageNo.value, rowSize: rowsNo.value } })
      .then((res) => {
        totalNo.value = res.meta.page.totalCount
        rowDatas.value = res.data
        emits('success', res.data)
        gridApi.value.setGridOption('loading', false)
      })
      .catch((err) => {
        console.error(err)
        emits('failed', err)
        gridApi.value.setGridOption('loading', false)
      })
  }
}

const onGridReady = (params) => {
  gridApi.value = params.api
  columnApi.value = params.columnApi
}

const onSelectionChanged = () => {
  if (props.showMulti) {
    selectedData.value = gridApi.value.getSelectedRows()
  } else {
    selectedData.value = gridApi.value.getSelectedRows()[0]
  }
  emits('selected', selectedData.value)
}

const onRowDoubleClicked = (params) => {
  emits('row-double-click', params?.data)
}

watch(
  () => props.datas,
  (newVal) => {
    rowDatas.value = newVal
  },
  { immediate: true },
)
watch(
  () => props.page,
  (newVal) => {
    pageNo.value = newVal
  },
  { immediate: true },
)
watch(
  () => props.total,
  (newVal) => {
    totalNo.value = newVal
  },
  { immediate: true },
)
watch(
  () => props.rows,
  (newVal) => {
    rowsNo.value = newVal
    pageNo.value = 1
  },
  { immediate: true },
)
watch(
  () => pageNo.value,
  (newVal) => {
    searchInternal()
    emits('movePage', newVal)
  },
)

onMounted(() => {
  if (props.isSearch) {
    searchInternal()
  }
})

const search = () => {
  if (pageNo.value === 1) {
    searchInternal()
    return
  }
  pageNo.value = 1
}

defineExpose({
  search,
})
</script>

<style lang="scss" scoped>
.msf-grid-container {
  // 영역높이
  --ag-header-height: #{rem(52px)};
  --ag-body-height: #{rem(68px)};
  // 최대높이
  --ag-max-height: calc((var(--ag-body-height) * v-bind('rows')) + var(--ag-header-height));

  width: 100%;
  margin-top: var(--layout-gutter-y);
  &:first-child {
    margin-top: 0;
  }
  &.is-hideHeader {
    margin-top: 0;
  }
  /* 중앙 정렬 클래스 */
  :deep(.ag-center-header) {
    .ag-header-cell-label {
      justify-content: center;
    }
  }
  /* 왼쪽|오른쪽 정렬이 필요한 경우
    (덮어쓰기용: 화면에서 그릴때 colDefs 에 headerClass:'ag-left-header' 를 설정) */
  :deep(.ag-left-header) .ag-header-cell-label {
    .ag-header-cell-label {
      justify-content: flex-start !important;
    }
  }
  :deep(.ag-right-header) {
    .ag-header-cell-label {
      justify-content: flex-end !important;
    }
  }
  .msf-grid-header {
    @include flex($h: space-between, $v: center);
    margin-bottom: rem(8px);
    .msf-grid-header-left {
      flex: 1;
    }
    .msf-grid-header-right {
      flex-shrink: 0;
      flex-grow: 0;
      @include flex() {
        gap: var(--spacing-x2);
      }
    }
  }
  .msf-grid-content {
    width: 100%;
  }
  .msf-grid-table {
    width: 100%;
  }
  // 페이징이아닌경우 max-height를 rows수에 맞게 지정하여 나타낼수있다.
  &.is-setMaxHeight {
    .msf-grid-table {
      height: var(--ag-max-height);
    }
  }
  :deep(.ag-checkbox-input-wrapper) {
    border-radius: var(--border-radius-xxs);
  }
  // ag-grid 내부 클래스 스타일 제어
  :deep(.ag-root-wrapper) {
    --ag-header-font-weight: var(--font-weight-bold);
    --ag-header-font-size: var(--font-size-16);
    --ag-header-text-color: var(--color-gray-900);
    --ag-data-font-size: var(--font-size-16);
    --ag-cell-text-color: var(--color-gray-600);
    --ag-row-hover-color: var(--color-gray-25); // row 호버
    // 내부체크박스
    --ag-checkbox-border-width: var(--border-width-base);
    --ag-checkbox-border-radius: rem(2px);
    // 체크박스 아이콘 크기
    --ag-icon-size: #{rem(24px)};
    --ag-checkbox-size: #{rem(24px)};
    // 포커스 쉐도우 삭제
    --ag-focus-shadow: none;
    border-left: none;
    border-right: none;
    border-color: var(--color-gray-150);
    border-radius: 0;
  }
  // 상단 정보 영역
  .total-count {
    font-size: var(--font-size-16);
    font-weight: var(--font-weight-bold);
    color: var(--color-foreground);
    & > em {
      color: var(--color-accent-base);
    }
  }
  // 체크박스 스타일
  :deep(.ag-checkbox-input-wrapper) {
    border-radius: var(--border-radius-max);
  }
  // 라디오 스타일
  :deep(.ag-radio-button-input-wrapper) {
    border-radius: var(--border-radius-max);
  }
  // singleSelection 라디오버튼 모양만들기!
  .is-single-check {
    // 첫번쨰 선택컬럼에 가상선택자로 '선택' 텍스트
    :deep(.ag-column-first) {
      .ag-header-cell-text {
        position: relative;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        &::before {
          content: '선택';
        }
      }
    }
    // 체크박스 모양을 라디오 모양으로 변경
    :deep(.ag-checkbox-input-wrapper) {
      --ag-checkbox-checked-background-color: var(--color-primary-base);
      --ag-checkbox-checked-border-color: var(--color-primary-base);

      border-radius: 100%;
      &::after {
        content: '';
        width: rem(8px);
        height: rem(8px);
        background: #fff;
        border-radius: 100%;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        mask-image: none;
      }
    }
  }
  :deep(.ag-aria-description-container) {
    font-size: var(--font-size-16);
  }

  // 스크롤바 테마
  :deep(.ag-body-vertical-scroll-viewport) {
    @include scrollbar();
    margin-right: 0;
    padding-right: 0;
  }

  // 스크롤바 msfCustomScroll 스타일로 비슷하게 맞춤
  /* 1. 스크롤바 전체 영역 (크기 설정은 데스크탑용) */
  :deep(.ag-body-viewport::-webkit-scrollbar) {
    width: 4px !important;
    height: 4px !important;
  }
  /* 2. 스크롤바 트랙 (바탕 - .cs-bar 배경과 통일) */
  :deep(.ag-body-viewport::-webkit-scrollbar-track) {
    background: rgba(139, 143, 151, 0.1) !important;
  }
  /* 3. 스크롤바 핸들 (Thumb - .cs-thumb와 통일) */
  :deep(.ag-body-viewport::-webkit-scrollbar-thumb) {
    background-color: var(--color-gray-400) !important; /* 핸들 색상 */
    border-radius: 1px !important; /* 곡률 */
  }
  /* 4. 마우스 호버 시 색상 변경 */
  :deep(.ag-body-viewport::-webkit-scrollbar-thumb:hover) {
    background-color: var(--color-gray-400) !important;
  }
  /* 5. iOS에서 스크롤 시 부드러운 움직임 유지 */
  :deep(.ag-body-viewport) {
    -webkit-overflow-scrolling: touch;
  }
}
</style>
