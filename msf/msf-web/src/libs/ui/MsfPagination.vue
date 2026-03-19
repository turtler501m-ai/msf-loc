<template>
  <div class="pagination-container" v-if="totalPages > 0">
    <button
      class="page-btn nav-btn"
      :disabled="currentPage === 1"
      @click="changePage(1)"
      aria-label="처음 페이지"
    >
      <i class="fa-solid fa-angles-left"></i>
    </button>

    <button
      class="page-btn nav-btn"
      :disabled="currentGroup === 1"
      @click="goToPrevGroup"
      aria-label="이전 페이지 그룹"
    >
      <i class="fa-solid fa-angle-left"></i>
    </button>

    <div class="page-numbers">
      <button
        v-for="no in pages"
        :key="no"
        type="button"
        :class="['page-btn', { active: no === currentPage }]"
        @click="changePage(no)"
      >
        {{ no }}
      </button>
    </div>

    <button
      class="page-btn nav-btn"
      :disabled="currentGroup === totalGroups"
      @click="goToNextGroup"
      aria-label="다음 페이지 그룹"
    >
      <i class="fa-solid fa-angle-right"></i>
    </button>

    <button
      class="page-btn nav-btn"
      :disabled="currentPage === totalPages"
      @click="changePage(totalPages)"
      aria-label="마지막 페이지"
    >
      <i class="fa-solid fa-angles-right"></i>
    </button>
  </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue'

const page = defineModel('page', { type: Number, required: true })
const total = defineModel('total', { type: Number, required: true })
const itemsPerPage = defineModel('itemsPerPage', { type: Number, default: 10 })
const pageSize = defineModel('pageSize', { type: Number, default: 10 })

const emit = defineEmits(['update:page', 'change'])

const currentPage = ref(page.value)

// 💡 핵심 계산 로직
// 1. 전체 페이지 수 계산 (예: 데이터가 105개면 11페이지)
const totalPages = computed(() => {
  return Math.max(1, Math.ceil(total.value / itemsPerPage.value))
})

// 2. 현재 화면에 보이는 페이지 그룹 (예: 1~10페이지는 1그룹, 11~20페이지는 2그룹)
const currentGroup = computed(() => {
  return Math.ceil(currentPage.value / pageSize.value)
})

// 3. 전체 페이지 그룹 수
const totalGroups = computed(() => {
  return Math.ceil(totalPages.value / pageSize.value)
})

// 4. 화면에 그려질 시작 페이지 번호와 끝 페이지 번호
const startPage = computed(() => {
  return (currentGroup.value - 1) * pageSize.value + 1
})

const endPage = computed(() => {
  const end = startPage.value + pageSize.value - 1
  return end > totalPages.value ? totalPages.value : end
})

// 5. 화면에 렌더링할 실제 페이지 번호 배열 (예: [11, 12, 13, ..., 20])
const pages = computed(() => {
  const pages = []
  for (let i = startPage.value; i <= endPage.value; i++) {
    pages.push(i)
  }
  return pages
})

// 💡 페이지 이동 함수들
const changePage = (no) => {
  if (no === currentPage.value || no < 1 || no > totalPages.value) return
  emit('update:page', no) // v-model 업데이트
  emit('change', no) // 필요시 API 호출을 위해 추가 이벤트 발생
}

// 이전 그룹 클릭 시: 현재 화면의 시작 페이지보다 1 작은 페이지(즉, 이전 그룹의 마지막 페이지)로 이동
const goToPrevGroup = () => {
  changePage(startPage.value - 1)
}

// 다음 그룹 클릭 시: 현재 화면의 끝 페이지보다 1 큰 페이지(즉, 다음 그룹의 첫 페이지)로 이동
const goToNextGroup = () => {
  changePage(endPage.value + 1)
}

watch(
  () => page.value,
  (newVal) => {
    currentPage.value = newVal
  },
)
</script>

<style scoped>
@reference "tailwindcss";

.pagination-container {
  @apply flex items-center justify-center gap-1 mt-6;
}

.page-numbers {
  @apply flex gap-1;
}

.page-btn {
  @apply flex items-center justify-center min-w-8 h-8 py-0 px-2 border border-neutral-200 bg-white text-neutral-700 text-sm rounded-sm cursor-pointer transition-all;
}

.page-btn:hover:not(.active):not(:disabled) {
  @apply bg-neutral-100 border-neutral-300;
}

/* 현재 선택된 페이지 스타일 */
.page-btn.active {
  @apply bg-indigo-600 border-indigo-600 text-white font-bold cursor-default;
}

/* 텍스트가 들어가는 좌우 이동 버튼 스타일 */
.nav-btn {
  @apply py-0 px-2;
}

/* 비활성화(disabled) 상태 스타일 */
.page-btn:disabled {
  @apply bg-neutral-100 text-neutral-300 border-neutral-100 cursor-not-allowed;
}
</style>
