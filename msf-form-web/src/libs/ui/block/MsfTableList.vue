<template>
  <div class="table-list-container">
    <MsfTable
      v-bind="$attrs"
      :data="displayData"
      :columns="columns"
      :selected-id="selectedId"
      :is-empty="data.length === 0"
    >
      <template #colgroup>
        <slot v-if="$slots.colgroup" name="colgroup" />
        <template v-else>
          <col v-for="(col, i) in columns" :key="i" :style="{ width: col.width || 'auto' }" />
        </template>
      </template>
      <template #thead>
        <slot v-if="$slots.thead" name="thead" />
        <tr v-else>
          <th v-for="(col, i) in columns" :key="i" scope="col">{{ col.label }}</th>
        </tr>
      </template>
      <template #tbody="{ items }">
        <slot v-if="$slots.tbody" name="tbody" :items="items" />
        <template v-else>
          <tr
            v-for="(item, idx) in items"
            :key="item.id || idx"
            :class="{
              'is-selected': Number(selectedId) === Number(item.id),
              'is-clickable': $attrs.onRowClick !== undefined,
            }"
            @click="handleRowClick(item)"
          >
            <td v-for="(col, i) in columns" :key="i">
              {{ item[col.key] }}
            </td>
          </tr>
        </template>
      </template>
    </MsfTable>
    <div v-if="mode === 'paging' && data.length > 0" class="pagination-area">
      <MsfPagination
        v-model:page="currentPage"
        :total="data.length"
        :items-per-page="pageSize"
        :page-size="10"
      />
    </div>
    <div v-if="mode === 'more' && hasNextPage && data.length > 0" class="more-btn-area ut-mt-16">
      <MsfButton class="btn-more-ui" @click="handleMore" block>
        더보기 ({{ displayData.length }} / {{ data.length }})
      </MsfButton>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'

const props = defineProps({
  data: { type: Array, default: () => [] },
  columns: { type: Array, default: () => [] },
  mode: { type: String, default: 'none' },
  rowView: { type: [Number, String], default: 10 },
  selectedId: { type: [String, Number], default: '' },
})

const emit = defineEmits(['row-click', 'update:selectedId'])

const currentPage = ref(1)
const pageSize = computed(() => Number(props.rowView) || 10)
const currentLimit = ref(pageSize.value)

// Pagination 컴포넌트와 연동될 데이터 슬라이싱 로직
const displayData = computed(() => {
  if (props.mode === 'paging') {
    const start = (currentPage.value - 1) * pageSize.value
    return props.data.slice(start, start + pageSize.value)
  }
  if (props.mode === 'more') {
    return props.data.slice(0, currentLimit.value)
  }
  return props.data
})

const hasNextPage = computed(() => displayData.value.length < props.data.length)

const handleRowClick = (item) => {
  emit('update:selectedId', item.id)
  emit('row-click', item)
}

const handleMore = () => {
  currentLimit.value += pageSize.value
}

// 데이터 원본(props.data)이 바뀌면 상태 초기화
watch(
  () => props.data,
  () => {
    currentPage.value = 1
    currentLimit.value = pageSize.value
  },
  { deep: true },
)
</script>

<style lang="scss" scoped>
.pagination-area {
  @include flex($h: center);
}

:deep(.is-clickable) {
  cursor: pointer;
  &:hover {
    background-color: var(--color-gray-25);
  }
}

:deep(.is-selected) {
  background-color: #e6f7ff !important;
  td {
    color: #1890ff;
  }
}
</style>
