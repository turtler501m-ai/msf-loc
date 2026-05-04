<template>
  <div class="main-title">
    <h3
      class="main-tit"
      tabindex="0"
      @click="noticePopupOpen = true"
      @keydown.enter="noticePopupOpen = true"
      @keydown.space.prevent="noticePopupOpen = true"
    >
      공지사항<MsfIcon name="arrowRight" size="small" />
    </h3>
    <div class="main-tit-side">
      <span class="main-badge">240건</span>
    </div>
  </div>
  <MsfCustomScroll class="main-content">
    <ul class="notice-list">
      <li
        v-for="item in noticeData"
        :key="item.id"
        class="notice-item"
        tabindex="0"
        @click="openPopupWithIdNotice(item.id)"
        @keydown.enter="openPopupWithIdNotice(item.id)"
        @keydown.space.prevent="openPopupWithIdNotice(item.id)"
      >
        <div class="notice-title">
          <p class="notice-tit">[{{ item.field }}]{{ item.title }}</p>
          <MsfFlag v-if="item.isNew" data="NEW" color="accent2" size="small" />
        </div>
        <span class="notice-date">{{ item.date }}</span>
      </li>
    </ul>
  </MsfCustomScroll>
  <MsfMainNoticeListPop
    v-model="noticePopupOpen"
    :targetId="activeNoticeId"
  /><!-- 공지사항 팝업 -->
</template>

<script setup>
import { computed, ref, watch } from 'vue'

const NOTICE_DATA = ref([
  {
    id: 'notice-01',
    field: '공지',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내1',
    content: `공지사항 내용을 표시합니다1`,
    isNew: true, //New 플래그 표시
  },
  {
    id: 'notice-02',
    field: '정책',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내2',
    content: `공지사항 내용을 표시합니다2`,
  },
  {
    id: 'notice-03',
    field: '도움말',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내3',
    content: `공지사항 내용을 표시합니다3`,
  },
  {
    id: 'notice-04',
    field: '공지',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내4',
    content: `공지사항 내용을 표시합니다4`,
  },
  {
    id: 'notice-05',
    field: '도움말',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내5',
    content: `공지사항 내용을 표시합니다5`,
  },
  {
    id: 'notice-06',
    field: '공지',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내6',
    content: `공지사항 내용을 표시합니다6`,
  },
  {
    id: 'notice-07',
    field: '공지',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내7',
    content: `공지사항 내용을 표시합니다7`,
  },
  {
    id: 'notice-08',
    field: '공지',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내8',
    content: `공지사항 내용을 표시합니다8`,
  },
  {
    id: 'notice-09',
    field: '공지',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내9',
    content: `공지사항 내용을 표시합니다9`,
  },
  {
    id: 'notice-10',
    field: '공지',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내10',
    content: `공지사항 내용을 표시합니다10`,
  },
  {
    id: 'notice-11',
    field: '공지',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내11',
    content: `공지사항 내용을 표시합니다11`,
  },
  {
    id: 'notice-12',
    field: '공지',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내12',
    content: `공지사항 내용을 표시합니다12`,
  },
  {
    id: 'notice-13',
    field: '공지',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내13',
    content: `공지사항 내용을 표시합니다13`,
  },
  {
    id: 'notice-14',
    field: '공지',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내14',
    content: `공지사항 내용을 표시합니다14`,
  },
  {
    id: 'notice-15',
    field: '공지',
    date: '2026-07-25 08:10',
    title: '개인정보처리방침 변경 안내15',
    content: `공지사항 내용을 표시합니다15`,
  },
])

// 공지사항 데이터
const noticeData = computed(() => NOTICE_DATA.value)
// 팝업의 상태
const noticePopupOpen = ref(false) // 공지사항 팝업 상태

/**
 * 공지사항 관련
 */
// 공지사항 ID
const activeNoticeId = ref('')
// 공지사항 목록 클릭시 팝업 열기
const openPopupWithIdNotice = (id) => {
  activeNoticeId.value = id // 열고 싶은 아코디언 ID 세팅
  noticePopupOpen.value = true // Q&A 팝업열기
}

watch(noticePopupOpen, (newVal) => {
  if (!newVal) {
    activeNoticeId.value = ''
  }
})
</script>

<style lang="scss" scoped></style>
