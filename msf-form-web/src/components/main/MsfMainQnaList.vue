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
          @click="openQnaPopup"
          >전체보기</MsfButton
        >
        <MsfButton
          variant="toggle"
          active
          suffixIcon="link"
          size="xsmall"
          noMinWidth
          @click="openQnaRegistPopup"
          >등록하기</MsfButton
        >
      </MsfButtonGroup>
    </div>
  </div>
  <div class="main-content">
    <div v-if="!list || list.length === 0" class="nodata-wrap noIcon">데이터가 없습니다.</div>
    <MsfSlider
      v-else
      :visibleCount="1.2"
      :slidesPerGroup="2"
      :breakpoints="{
        768: { visibleCount: 2.2, slidesPerGroup: 2 }, // 768px 이상 (태블릿)
        1024: { visibleCount: 3.2, slidesPerGroup: 3 }, // 1024px 이상 (PC)
      }"
    >
      <div
        v-for="item in displayList"
        :key="item.id"
        class="qna-card"
        @click="openPopupWithId(item.id)"
      >
        <div class="status-icon" :class="`is-${item.answer?.status?.code || 'progress'}`">
          <span class="status-mark"><MsfIcon name="qnaCheck" size="small" /></span>
          <img :src="getIconUrl(item.answer?.status?.code)" :alt="item.answer?.status?.title" />
          <span class="status-txt">{{ item.answer?.status?.title }}</span>
        </div>
        <div class="card-content">
          <p class="card-tit">{{ item.title }}</p>
          <span class="card-date">{{ formatDatetimeMinutes(item.writeDate) }}</span>
        </div>
      </div>
    </MsfSlider>
  </div>
  <MsfMainQnaListPop
    v-model="qnaPopupOpen"
    :search="searchData"
    :targetId="activeQnaId"
    :data="popList"
    :total="popTotal"
    :currentPage="currentPage"
    :itemsPerPage="itemsPerPage"
    @regist="openQnaRegistPopup"
  /><!-- Q&A 팝업 -->
  <MsfMainQnaRegistPop
    v-model="qnaAddPopupOpen"
    @close="onCloseRegistPopup"
  /><!-- Q&A 등록 팝업 -->
</template>

<script setup>
import { computed, onBeforeMount, ref, watch } from 'vue'
import { addMonths } from 'date-fns'
import { post } from '@/libs/api/msf.api'
import { formatDate, formatDatetimeMinutes } from '@/libs/utils/date.utils'
import qnaCSvg from '@/assets/images/qna_C.svg'
import qnaESvg from '@/assets/images/qna_E.svg'
import qnaRSvg from '@/assets/images/qna_R.svg'

const qnaPopupOpen = ref(false) // Q&A 팝업 상태
const qnaAddPopupOpen = ref(false) // Q&A 등록 팝업 상태

const searchData = ref({
  category: '',
  value: '',
  startDate: formatDate(addMonths(new Date(), -1)),
  endDate: formatDate(new Date()),
})
const currentPage = ref(1)
const itemsPerPage = ref(10)
const totalCount = ref(0)
// Q&A 슬라이더 데이터
const list = ref([])
const popList = ref([])
const popTotal = ref(0)
const displayList = computed(() =>
  list.value?.length > 0 ? list.value.slice(0, list.value.length > 5 ? 5 : list.value.length) : [],
)

// const list = computed(() => QNA_DATA.value.slice(0, 5)) // 최근 다섯개 자르기
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
// Q&A IDasync
const activeQnaId = ref('')

const searchQna = async () => {
  // list.value = []
  const data = await post('/api/main/qna/list', {
    page: {
      pageNum: currentPage.value,
      rowSize: itemsPerPage.value,
    },
    ...searchData.value,
  })
  if (data.code !== '0000') {
    return false
  }
  totalCount.value = data.meta.page.totalCount
  list.value = data.data
}

const openQnaPopup = () => {
  popList.value = []
  popTotal.value = 0
  activeQnaId.value = null // 열고 싶은 아코디언 ID 세팅
  qnaPopupOpen.value = true // Q&A 팝업열기
}

// Q&A 슬라이더 클릭시 팝업 열기
const openPopupWithId = (id) => {
  popList.value = list.value
  popTotal.value = totalCount.value
  activeQnaId.value = id // 열고 싶은 아코디언 ID 세팅
  qnaPopupOpen.value = true // Q&A 팝업열기
}

const openQnaRegistPopup = () => {
  qnaAddPopupOpen.value = true
}

const onCloseRegistPopup = async (result) => {
  if (result) {
    await searchQna()
  }
}

// 팝업 닫힘 감시 및 qna, notice 열렸던 ID 초기화
watch(qnaPopupOpen, (newVal) => {
  if (!newVal) {
    activeQnaId.value = ''
  }
})

onBeforeMount(async () => {
  await searchQna()
})
</script>

<style lang="scss" scoped></style>
