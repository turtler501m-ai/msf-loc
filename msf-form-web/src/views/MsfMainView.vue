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
              <span class="main-badge">90건</span>
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
              <span class="main-badge">114건</span>
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
import { ref, computed, watch, defineAsyncComponent } from 'vue'
// 샘플데이터 가져오기
import { NOTICE_DATA, QNA_DATA } from '@/views/guide/mock'
// ----------- 퍼블 샘플 팝업 : 실제 팝업은 개발규칙에 맞춰서 네이밍 생성하시면됩니다 ------------ //
const S106040102 = defineAsyncComponent(() => import('@/views/guide/pages/pub/S106040102.vue'))
const S106040103 = defineAsyncComponent(() => import('@/views/guide/pages/pub/S106040103.vue'))
const S106040104 = defineAsyncComponent(() => import('@/views/guide/pages/pub/S106040104.vue'))
// ---------------------------------------------------------------------- //
// const msfApiUrl = ref(import.meta.env.VITE_MSF_API_URL)

// 타이틀 클릭 라우터 이동
const router = useRouter()
const moveTo = (path) => {
  router.push(path)
}

// Q&A 슬라이더 데이터
const sliderItems = computed(() => QNA_DATA.slice(0, 5)) // 최근 다섯개 자르기
// 공지사항 데이터
const noticeData = computed(() => NOTICE_DATA)

// 팝업의 상태
const noticePopupOpen = ref(false) // 공지사항 팝업 상태
const qnaPopupOpen = ref(false) // Q&A 팝업 상태
const qnaAddPopupOpen = ref(false) // Q&A 등록 팝업 상태

// 처리상태별 건수 차트
const chartData = ref([
  { nm: '접수처리완료', cnt: 60 },
  { nm: '이미지전송완료', cnt: 10 },
  { nm: '신청중', cnt: 20 },
])
// 업무별 건수 차트
const chartData2 = ref([
  { nm: '신규/변경', cnt: 60 },
  { nm: '서비스변경', cnt: 30 },
  { nm: '명의변경', cnt: 14 },
  { nm: '서비스해지', cnt: 10 },
])

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
  return new URL(`../assets/images/${iconName}`, import.meta.url).href
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
</script>

<style lang="scss" scoped></style>
