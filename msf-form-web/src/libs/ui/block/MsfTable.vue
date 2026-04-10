<template>
  <div class="base-table-root">
    <div
      class="base-table-wrapper"
      :class="{ 'is-scrollable': isScrollable }"
      :style="wrapperStyle"
    >
      <table ref="tableRef" class="base-table">
        <caption class="ut-blind">
          {{
            customCaption || `${columnNames.join(', ')} 항목으로 구성된 정보 제공 표입니다.`
          }}
        </caption>

        <colgroup v-if="$slots.colgroup">
          <slot name="colgroup"></slot>
        </colgroup>

        <thead v-if="$slots.thead">
          <slot name="thead"></slot>
        </thead>

        <tbody v-if="!isEmpty && $slots.tbody">
          <slot name="tbody" :items="data"></slot>
        </tbody>

        <tbody v-else>
          <tr>
            <td :colspan="computedColspan" class="no-data-cell">
              <div class="no-data-inner">데이터가 없습니다.</div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick, watch } from 'vue'

const props = defineProps({
  data: { type: Array, default: () => [] },
  columns: { type: Array, default: () => [] },
  isEmpty: { type: Boolean, default: false },
  height: { type: [Number, String], default: '' },
  rowHeight: { type: Number, default: 68 },
  headerHeight: { type: Number, default: 52 },
  customCaption: { type: String, default: '' },
})

const tableRef = ref(null)
const columnNames = ref([])

// 스크롤 여부 판단
const isScrollable = computed(() => props.height !== '')

// 5줄 높이 계산 및 스크롤 방지용 +2px 여유분 적용
const wrapperStyle = computed(() => {
  if (!isScrollable.value) return { height: 'auto', overflowY: 'visible' }

  let finalHeight = ''
  if (typeof props.height === 'number') {
    // (줄수 * 행높이) + 헤더높이 + 보더/오차 여유분(2px)
    finalHeight = `${props.height * props.rowHeight + props.headerHeight + 2}px`
  } else {
    finalHeight = props.height
  }
  return { height: finalHeight, overflowY: 'auto' }
})

// 접근성: 모든 th에서 텍스트 추출 (thead + tbody)
const updateColumnNames = async () => {
  await nextTick()
  if (tableRef.value) {
    const allThs = tableRef.value.querySelectorAll('th')
    const names = Array.from(allThs)
      .map((th) => th.innerText.trim())
      .filter((t) => t !== '')
    columnNames.value = [...new Set(names)]
  }
}

const computedColspan = computed(() => {
  if (props.columns.length > 0) return props.columns.length
  if (tableRef.value) {
    return tableRef.value.querySelectorAll('thead th').length || 1
  }
  return 1
})

watch(() => props.data, updateColumnNames, { deep: true })
onMounted(updateColumnNames)
</script>

<style lang="scss" scoped>
.base-table-root {
  --tbl-body-border-top-color: var(--color-gray-150);
  --tbl-body-border-color: var(--color-gray-75);
  --tbl-inner-padding-x: #{rem(16px)};
  --tbl-inner-padding-y: #{rem(8px)};
  --tbl-head-color: var(--color-gray-25);
  --tbl-head-weight: var(--font-weight-bold);
  --tbl-body-bg: var(--color-background);

  width: 100%;
  .base-table-wrapper {
    width: 100%;
    border-top: var(--border-width-base) solid var(--tbl-body-border-top-color);
    border-bottom: var(--border-width-base) solid var(--tbl-body-border-color);
    box-sizing: border-box;
    overflow-x: auto;
    background-color: var(--tbl-body-bg);
    background-repeat: no-repeat;

    // 스크롤바 트랙 배경색 트릭 (하얀 공간 메우기)
    &.is-scrollable {
      overflow-y: auto;
      background-image: linear-gradient(var(--tbl-head-color), var(--tbl-head-color));
      // 헤더 배경색과 일치
      background-size: 100% v-bind('headerHeight + "px"');

      &::-webkit-scrollbar-track {
        background: transparent;
        margin-top: v-bind('headerHeight + "px"');
      }
    }

    &::-webkit-scrollbar {
      width: rem(2px);
      height: rem(2px);
    }
    &::-webkit-scrollbar-thumb {
      background: var(--color-gray-400);
      border-radius: rem(4px);
    }
  }

  .base-table {
    width: 100%;
    border-collapse: separate;
    border-spacing: 0;
    table-layout: fixed;
    /* [추가] 가로 스크롤이 발생하는 임계점 */
    min-width: rem(650px);
    :deep(tbody) {
      tr:first-child {
        th {
          border-top: none;
        }
      }
      tr:last-child {
        th {
          border-bottom: none;
        }
      }
      th {
        border-top: var(--border-width-base) solid var(--tbl-body-border-color);
        border-bottom: none;
      }
    }

    :deep(th) {
      position: sticky;
      top: 0;
      z-index: 2;
      background-color: var(--tbl-head-color);
      height: v-bind('headerHeight + "px"');
      // box-shadow: inset 0 -1px 0 #e0e0e0;
      border-bottom: var(--border-width-base) solid var(--tbl-body-border-color);
      padding: var(--tbl-inner-padding-y) var(--tbl-inner-padding-x);
      text-align: center;
      border-right: var(--border-width-base) solid var(--tbl-body-border-color);
      font-weight: var(--tbl-head-weight);
      &:last-child {
        border-right: none;
      }
    }
    :deep(td) {
      height: v-bind('rowHeight + "px"');
      padding: var(--tbl-inner-padding-y) var(--tbl-inner-padding-x);
      background-color: var(--tbl-body-bg);
      border-top: var(--border-width-base) solid var(--tbl-body-border-color);
      border-right: var(--border-width-base) solid var(--tbl-body-border-color);
      &:last-child {
        border-right: none;
      }
      color: var(--color-gray-600);
    }
    :deep(tbody tr) {
      &:first-child {
        td {
          border-top: none;
        }
      }
    }
    .no-data-cell .no-data-inner {
      @include flex($v: center, $h: center);
      height: rem(200px);
      color: #999;
    }
  }
}
</style>
