<template>
  <main class="main-layout">
    <!-- 차트 영역-->
    <section class="main-box box-status">
      <MsfStack class="status-stack" nowrap>
        <div class="ut-flex-1">
          <div class="main-title">
            <h3
              class="main-tit"
              tabindex="0"
              @click="moveTo('/extra/receipt/ReceiptPage')"
              @keydown.enter="moveTo('/extra/receipt/ReceiptPage')"
              @keydown.space.prevent="moveTo('/extra/receipt/ReceiptPage')"
            >
              처리상태별 건수<MsfIcon name="arrowRight" size="small" />
            </h3>
            <div class="main-tit-side">
              <span class="main-badge">0건</span>
            </div>
          </div>
          <MsfDoughnutChart
            :data="chartData"
            title="처리상태별 건수"
            name-key="nm"
            value-key="cnt"
          />
        </div>
        <div class="ut-flex-1">
          <div class="main-title">
            <h3
              class="main-tit"
              tabindex="0"
              @click="moveTo('/extra/receipt/ReceiptPage')"
              @keydown.enter="moveTo('/extra/receipt/ReceiptPage')"
              @keydown.space.prevent="moveTo('/extra/receipt/ReceiptPage')"
            >
              업무별 건수<MsfIcon name="arrowRight" size="small" />
            </h3>
            <div class="main-tit-side">
              <span class="main-badge">0건</span>
            </div>
          </div>
          <MsfDoughnutChart :data="chartData2" title="업무별 건수" name-key="nm" value-key="cnt" />
        </div>
      </MsfStack>
    </section>
    <!-- // 차트 영역-->
    <!-- Q&A -->
    <section class="main-box box-qna">
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
        <div v-if="!sliderItems || sliderItems.length === 0" class="nodata-wrap noIcon">
          데이터가 없습니다.
        </div>
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
                aria-hidden="true"
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
    </section>
    <!-- // Q&A -->
    <!-- 공지사항 -->
    <section class="main-box box-notice">
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
          <span class="main-badge">{{ noticeData.length }}건</span>
        </div>
      </div>
      <div v-if="!noticeData || noticeData.length === 0" class="nodata-wrap noIcon">
        데이터가 없습니다.
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
    </section>
    <!-- // 공지사항 -->
  </main>
  <!-- 팝업목록: 개발시 새로운 파일명으로 생성하여 사용 -->
  <S106040102 v-model="noticePopupOpen" :targetId="activeNoticeId" /><!-- 공지사항 팝업 -->
  <S106040103 v-model="qnaPopupOpen" :targetId="activeQnaId" /><!-- Q&A 팝업 -->
  <S106040104 v-model="qnaAddPopupOpen" /><!-- Q&A 등록 팝업 -->
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ref, computed, watch } from 'vue'
// ----------- 퍼블 샘플 팝업 : 실제 팝업은 개발규칙에 맞춰서 네이밍 ------------ //
import S106040102 from '@/views/guide/pages/pub/S106040102.vue' /* 공지사항 */
import S106040103 from '@/views/guide/pages/pub/S106040103.vue' /* Q&A */
import S106040104 from '@/views/guide/pages/pub/S106040104.vue' /* Q&A 등록 */
// ---------------------------------------------------------------------- //
// const msfApiUrl = ref(import.meta.env.VITE_MSF_BASE_URL)
import qnaCSvg from '@/assets/images/qna_C.svg'
import qnaESvg from '@/assets/images/qna_E.svg'
import qnaRSvg from '@/assets/images/qna_R.svg'

// 타이틀 클릭 라우터 이동
const router = useRouter()
const moveTo = (path) => {
  router.push(path)
}

// Q&A 슬라이더 데이터
const sliderItems = computed(() => QNA_DATA.slice(0, 0)) // 자르기

// 공지사항 데이터
const noticeData = computed(() => NOTICE_DATA.slice(0, 0)) // 자르기

// 팝업의 상태
const noticePopupOpen = ref(false) // 공지사항 팝업 상태
const qnaPopupOpen = ref(false) // Q&A 팝업 상태
const qnaAddPopupOpen = ref(false) // Q&A 등록 팝업 상태

// 처리상태별 건수 차트
const chartData = ref([
  { nm: '접수처리완료', cnt: 0 },
  { nm: '이미지전송완료', cnt: 0 },
  { nm: '신청중', cnt: 0 },
])
// 업무별 건수 차트
const chartData2 = ref([
  { nm: '신규/변경', cnt: 0 },
  { nm: '서비스변경', cnt: 0 },
  { nm: '명의변경', cnt: 0 },
  { nm: '서비스해지', cnt: 0 },
])

// Q&A 상태별 매핑
const QNA_STATUS_META = {
  complete: { icon: 'qna_C.svg', text: '답변완료' },
  pending: { icon: 'qna_E.svg', text: '답변대기' },
  progress: { icon: 'qna_R.svg', text: '진행중' },
}

const iconList = {
  C: qnaCSvg,
  E: qnaESvg,
  R: qnaRSvg,
}
// 이미지 경로 유틸
const getIconUrl = (status) => {
  const icon = iconList[status]
  if (!icon) {
    return iconList['R']
  }
  // status가 없으면 'progress'(진행중)을 기본값으로 사용
  return icon
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

// 팝업 닫힘 감시 및 qna, notice 열렸던 ID 초기화
watch(qnaPopupOpen, (newVal) => {
  if (!newVal) {
    activeQnaId.value = ''
  }
})
watch(noticePopupOpen, (newVal) => {
  if (!newVal) {
    activeNoticeId.value = ''
  }
})

// 메인 공지사항 샘플데이터
const NOTICE_DATA = [
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
]

// 메인 QNA 샘플데이터
const QNA_DATA = [
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
]
</script>

<style lang="scss" scoped></style>
