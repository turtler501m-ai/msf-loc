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
      <div v-for="item in list" :key="item.id" class="qna-card" @click="openPopupWithItem(item)">
        <div
          class="status-icon"
          :class="
            item.answer?.status?.code === 'E'
              ? 'is-complete'
              : item.answer?.status?.code === 'C'
                ? 'is-pending'
                : 'is-progress'
          "
        >
          <span class="status-mark"><MsfIcon name="qnaCheck" size="small" /></span>
          <img
            :src="getIconUrl(item.answer?.status?.code)"
            :alt="item.answer?.status?.title"
            aria-hidden="true"
          />
          <span v-if="item.answer?.status?.title" class="status-txt">{{
            item.answer?.status?.title
          }}</span>
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
    :target="selectedTarget"
    @regist="openQnaRegistPopup"
  /><!-- Q&A 팝업 -->
  <MsfMainQnaRegistPop
    v-model="qnaAddPopupOpen"
    @close="onCloseRegistPopup"
  /><!-- Q&A 등록 팝업 -->
</template>

<script setup>
import { onBeforeMount, ref, watch } from 'vue'
import { post } from '@/libs/api/msf.api'
import { formatDatetimeMinutes } from '@/libs/utils/date.utils'
import qnaCSvg from '@/assets/images/qna_C.svg'
import qnaESvg from '@/assets/images/qna_E.svg'
import qnaRSvg from '@/assets/images/qna_R.svg'

const qnaPopupOpen = ref(false) // Q&A 팝업 상태
const qnaAddPopupOpen = ref(false) // Q&A 등록 팝업 상태

const selectedTarget = ref(null)
const currentPage = ref(1)
const itemsPerPage = ref(5)
// Q&A 슬라이더 데이터
const list = ref([])

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
const searchQna = async () => {
  selectedTarget.value = null
  const data = await post('/api/main/qna/list', {
    page: {
      pageNum: currentPage.value,
      rowSize: itemsPerPage.value,
    },
  })
  if (data.code !== '0000') {
    return false
  }
  list.value = data.data
}

const openQnaPopup = () => {
  selectedTarget.value = null
  qnaPopupOpen.value = true // Q&A 팝업열기
}

// Q&A 슬라이더 클릭시 팝업 열기
const openPopupWithItem = (item) => {
  selectedTarget.value = item
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
    selectedTarget.value = null
  }
})

onBeforeMount(async () => {
  await searchQna()
})
</script>

<style lang="scss" scoped></style>
