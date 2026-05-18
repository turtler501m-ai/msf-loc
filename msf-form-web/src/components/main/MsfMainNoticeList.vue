<template>
  <div class="main-title">
    <h3
      class="main-tit"
      tabindex="0"
      @click="openNoticePopup"
      @keydown.enter="openNoticePopup"
      @keydown.space.prevent="openNoticePopup"
    >
      공지사항<MsfIcon name="arrowRight" size="small" />
    </h3>
    <div class="main-tit-side">
      <span class="main-badge">{{ formatCurrency(totalCount) }}건</span>
    </div>
  </div>
  <div v-if="!list || list.length === 0" class="nodata-wrap noIcon">데이터가 없습니다.</div>
  <MsfCustomScroll v-else class="main-content">
    <ul class="notice-list">
      <li
        v-for="item in displayList"
        :key="item.id"
        class="notice-item"
        tabindex="0"
        @click="openNoticePopupWithId(item.id)"
        @keydown.enter="openNoticePopupWithId(item.id)"
        @keydown.space.prevent="openNoticePopupWithId(item.id)"
      >
        <div class="notice-title">
          <p class="notice-tit">[{{ item.category?.title }}] {{ item.title }}</p>
          <MsfFlag
            v-if="diffDays(Date.now(), item.writeDate) <= 3"
            data="NEW"
            color="accent2"
            size="small"
          />
        </div>
        <span class="notice-date">{{ formatDatetimeMinutes(item.writeDate) }}</span>
      </li>
    </ul>
  </MsfCustomScroll>

  <MsfMainNoticeListPop
    v-model="noticePopupOpen"
    :search="searchData"
    :targetId="activeNoticeId"
    :data="popList"
    :total="popTotal"
    :currentPage="currentPage"
    :itemsPerPage="itemsPerPage"
  /><!-- 공지사항 팝업 -->
</template>

<script setup>
import { computed, onBeforeMount, ref, watch } from 'vue'
import { addMonths } from 'date-fns'
import { post } from '@/libs/api/msf.api'
import { diffDays, formatDate, formatDatetimeMinutes } from '@/libs/utils/date.utils'
import { formatCurrency } from '@/libs/utils/string.utils'

const searchData = ref({
  category: '',
  value: '',
  startDate: formatDate(addMonths(new Date(), -1)),
  endDate: formatDate(new Date()),
})
const currentPage = ref(1)
const itemsPerPage = ref(10)
const totalCount = ref(0)
// 공지사항 데이터
const list = ref([])
const popList = ref([])
const popTotal = ref(0)
// 팝업의 상태
const noticePopupOpen = ref(false) // 공지사항 팝업 상태
const displayList = computed(() =>
  list.value?.length > 0 ? list.value.slice(0, list.value.length > 5 ? 5 : list.value.length) : [],
)

/**
 * 공지사항 관련
 */
// 공지사항 ID
const activeNoticeId = ref('')
// 공지사항 제목 클릭시 팝업 열기
const openNoticePopup = () => {
  popList.value = []
  popTotal.value = 0
  activeNoticeId.value = null // 열고 싶은 아코디언 ID 세팅
  noticePopupOpen.value = true // Q&A 팝업열기
}
// 공지사항 항목 클릭시 팝업 열기
const openNoticePopupWithId = (id) => {
  popList.value = list.value
  popTotal.value = totalCount.value
  activeNoticeId.value = id // 열고 싶은 아코디언 ID 세팅
  noticePopupOpen.value = true // Q&A 팝업열기
}

watch(noticePopupOpen, (newVal) => {
  if (!newVal) {
    activeNoticeId.value = ''
  }
})

onBeforeMount(async () => {
  const data = await post('/api/main/notice/list', {
    page: {
      pageNum: currentPage.value,
      rowSize: itemsPerPage.value,
    },
    ...searchData.value,
  })
  totalCount.value = data.meta.page.totalCount
  list.value = data.data
})
</script>

<style lang="scss" scoped></style>
