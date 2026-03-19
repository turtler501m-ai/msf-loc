<template>
  <div class="msf-grid-container">
    <div class="msf-grid-header">
      <div class="msf-grid-header-left">
        <span>(총 {{ totalNo }} 건)</span>
      </div>
      <div class="msf-grid-header-right">
        <slot name="buttons"></slot>
      </div>
    </div>
    <div class="msf-grid-content">
      <AgGridVue
        :theme="themeAlpine"
        :locale-text="localeText"
        :row-data="rowDatas"
        :column-types="columnTypes"
        :column-defs="props.columns"
        :row-selection="props.showMulti ? multiOptions : singleOptions"
        class="msf-grid-table"
        @grid-ready="onGridReady"
        @selection-changed="onSelectionChanged"
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
import { onMounted, ref, watch } from 'vue'
import { themeAlpine } from 'ag-grid-community'
import { AG_GRID_LOCALE_KR } from './ag-grid.locale.kr'
import { AgGridVue } from 'ag-grid-vue3'
import { formatDate, formatDatetime } from '@/libs/utils/date.utils'
import { formatCurrency } from '@/libs/utils/string.utils'
import { api } from '@/libs/api'
import { MsfPagination } from '.'

const props = defineProps({
  columns: {
    type: Array,
    required: true,
  },
  datas: Array,
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
    default: 10,
  },
})

const emit = defineEmits(['selected', 'movePage'])

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

const search = () => {
  if (props.url) {
    api
      .get(props.url, {
        params: { ...props.params, skip: (pageNo.value - 1) * rowsNo.value, limit: rowsNo.value },
      })
      .then((res) => {
        rowDatas.value = res.data.products
        totalNo.value = res.data.total
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
  emit('selected', selectedData.value)
}

watch(
  () => props.datas,
  (newVal) => {
    rowDatas.value = newVal
  },
  { immediate: true },
)
watch(
  () => props.url,
  () => {
    if (pageNo.value === 1) {
      search()
    } else {
      pageNo.value = 1
    }
  },
  { immediate: true },
)
watch(
  () => props.params,
  () => {
    if (pageNo.value === 1) {
      search()
    } else {
      pageNo.value = 1
    }
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
    search()
    emit('movePage', newVal)
  },
)

onMounted(() => {
  search()
})
</script>

<style scoped>
@reference "tailwindcss";

.msf-grid-container {
  @apply w-full;
}
.msf-grid-header {
  @apply grid grid-cols-4 gap-1 mb-1;
}
.msf-grid-header-left {
  @apply content-end;
}
.msf-grid-header-right {
  @apply col-span-3 text-end content-end;
}
.msf-grid-content {
  @apply w-full;
}

.msf-grid-table {
  @apply w-full h-48;
}
</style>
