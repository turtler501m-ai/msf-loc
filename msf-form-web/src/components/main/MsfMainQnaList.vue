<template>
  <div class="main-title">
    <h3 class="main-tit">Q&A</h3>
    <div class="main-tit-side">
      <MsfButtonGroup gap="4">
        <MsfButton
          variant="primary"
          suffixIcon="link"
          size="xsmall"
          noMinWidth
          @click="qnaPopupOpen = true"
          >전체보기</MsfButton
        >
        <MsfButton
          variant="toggle"
          active
          suffixIcon="link"
          size="xsmall"
          noMinWidth
          @click="qnaAddPopupOpen = true"
          >등록하기</MsfButton
        >
      </MsfButtonGroup>
    </div>
  </div>
  <div class="main-content">
    <MsfSlider
      :visibleCount="1.2"
      :slidesPerGroup="2"
      :breakpoints="{
        768: { visibleCount: 2.2, slidesPerGroup: 2 }, // 768px 이상 (태블릿)
        1024: { visibleCount: 3.2, slidesPerGroup: 3 }, // 1024px 이상 (PC)
      }"
    >
      <div
        v-for="item in sliderItems"
        :key="item.id"
        class="qna-card"
        @click="openPopupWithId(item.id)"
      >
        <div class="status-icon" :class="`is-${item.status || 'progress'}`">
          <span class="status-mark"><MsfIcon name="qnaCheck" size="small" /></span>
          <img
            :src="getIconUrl(item.status)"
            :alt="QNA_STATUS_META[item.status || 'progress'].text"
          />
          <span class="status-txt">{{ QNA_STATUS_META[item.status || 'progress'].text }}</span>
        </div>
        <div class="card-content">
          <p class="card-tit">{{ item.title }}</p>
          <span class="card-date">{{ item.date }}</span>
        </div>
      </div>
    </MsfSlider>
  </div>
  <MsfMainQnaListPop v-model="qnaPopupOpen" :targetId="activeQnaId" /><!-- Q&A 팝업 -->
  <MsfMainQnaRegistPop v-model="qnaAddPopupOpen" /><!-- Q&A 등록 팝업 -->
</template>

<script setup>
import { computed, ref, watch } from 'vue'

const qnaPopupOpen = ref(false) // Q&A 팝업 상태
const qnaAddPopupOpen = ref(false) // Q&A 등록 팝업 상태

// 메인 QNA 샘플데이터
const QNA_DATA = ref([
  {
    id: 'qna-01',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.1',
    content: `공지사항 내용을 표시합니다1`,
    status: 'complete', //답변완료
    answers: [
      {
        id: 'answer-01',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-02',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.2',
    content: `공지사항 내용을 표시합니다2`,
    status: 'pending', //답변대기
    answers: [
      {
        id: 'answer-02',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-03',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.3',
    content: `공지사항 내용을 표시합니다3`,
    status: 'progress', //진행중
    answers: [
      {
        id: 'answer-03',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-04',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.4',
    content: `공지사항 내용을 표시합니다4`,
    status: 'complete', //진행중
    answers: [
      {
        id: 'answer-04',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-05',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.5',
    content: `공지사항 내용을 표시합니다5`,
    status: 'progress', //진행중
    answers: [
      {
        id: 'answer-05',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-06',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.6',
    content: `공지사항 내용을 표시합니다6`,
    status: 'progress', //진행중
    answers: [
      {
        id: 'answer-06',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-07',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.7',
    content: `공지사항 내용을 표시합니다7`,
    status: 'progress', //진행중
    answers: [
      {
        id: 'answer-07',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-08',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.8',
    content: `공지사항 내용을 표시합니다8`,
    status: 'progress', //진행중
    answers: [
      {
        id: 'answer-08',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-09',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.9',
    content: `공지사항 내용을 표시합니다9`,
    status: 'progress', //진행중
    answers: [
      {
        id: 'answer-09',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-10',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.10',
    content: `공지사항 내용을 표시합니다10`,
    status: 'progress', //진행중
    answers: [
      {
        id: 'answer-10',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-11',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.11',
    content: `공지사항 내용을 표시합니다11`,
    status: 'progress', //진행중
    answers: [
      {
        id: 'answer-11',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-12',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.12',
    content: `공지사항 내용을 표시합니다12`,
    status: 'progress', //진행중
    answers: [
      {
        id: 'answer-12',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-13',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.13',
    content: `공지사항 내용을 표시합니다13`,
    status: 'progress', //진행중
    answers: [
      {
        id: 'answer-13',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-14',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.14',
    content: `공지사항 내용을 표시합니다14`,
    status: 'progress', //진행중
    answers: [
      {
        id: 'answer-14',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
  {
    id: 'qna-15',
    field: '공지',
    date: '2026-07-25 08:10',
    title: 'QNA 타이틀 입니다.15',
    content: `공지사항 내용을 표시합니다15`,
    status: 'progress', //진행중
    answers: [
      {
        id: 'answer-15',
        content:
          '최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다. 최근 발생한 개인정보 유출 피해와 관련하여, 원하시는 kt M모바일 고객님들께 무료로 교체를 지원하기로 하였습니다. 고객님의 불편함이 없도록 교체 시작일자와 다양한 교체 방법을 아래와 같이 알려드립니다.',
      },
    ],
  },
])

// Q&A 슬라이더 데이터
const sliderItems = computed(() => QNA_DATA.value.slice(0, 5)) // 최근 다섯개 자르기

// Q&A 상태별 매핑
const QNA_STATUS_META = {
  complete: { icon: 'qnaIcon1.svg', text: '답변완료' },
  pending: { icon: 'qnaIcon2.svg', text: '답변대기' },
  progress: { icon: 'qnaIcon2.svg', text: '진행중' },
}

// 이미지 경로 유틸
const getIconUrl = (status) => {
  // status가 없으면 'progress'(진행중)을 기본값으로 사용
  const targetStatus = status || 'progress'
  const iconName = QNA_STATUS_META[targetStatus].icon
  return new URL(`../../assets/images/${iconName}`, import.meta.url).href
}

/**
 * Q&A 관련
 */
// Q&A ID
const activeQnaId = ref('')
// Q&A 슬라이더 클릭시 팝업 열기
const openPopupWithId = (id) => {
  activeQnaId.value = id // 열고 싶은 아코디언 ID 세팅
  qnaPopupOpen.value = true // Q&A 팝업열기
}

// 팝업 닫힘 감시 및 qna, notice 열렸던 ID 초기화
watch(qnaPopupOpen, (newVal) => {
  if (!newVal) {
    activeQnaId.value = ''
  }
})
</script>

<style lang="scss" scoped></style>
