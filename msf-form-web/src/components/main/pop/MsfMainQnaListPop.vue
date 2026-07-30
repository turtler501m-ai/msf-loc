<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="Q&A"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <!-- 검색영역 -->
    <MsfBox margin="0" bgColor="gray2">
      <MsfStack vertical>
        <MsfStack type="field" class="ut-w100p">
          <MsfInput v-model="searchData.value" class="ut-w-347" placeholder="검색어 입력" />
          <MsfSelect
            title="유형"
            v-model="searchData.category"
            group-code="QNA_CTG_CD"
            placeholder="유형"
            class="ut-flex-1"
            all-checked
          />
          <MsfButton variant="primary" noMinWidth @click="searchQna(1)">검색</MsfButton>
        </MsfStack>
        <MsfStack type="field" class="ut-w-347">
          <MsfDateRange
            v-model:from="searchData.startDate"
            v-model:to="searchData.endDate"
            class="ut-w100p"
          />
        </MsfStack>
      </MsfStack>
    </MsfBox>
    <!-- // 검색영역 -->
    <MsfStack type="field" class="ut-w100p ut-jc-between ut-mt-24">
      <div class="list-total-count">
        총
        <em> {{ formatCurrency(totalCount) }} </em>건
      </div>
      <MsfButton variant="subtle" @click="onClickRegistPopup">등록</MsfButton>
    </MsfStack>
    <!-- 아코디언 -->
    <div v-if="!list || list.length === 0" class="nodata-wrap noIcon">데이터가 없습니다.</div>
    <MsfAccordion v-else v-model="openedItems" :data="list" multiple variant="board">
      <template #label="{ item }">
        <div :id="`notice-item-${item.id}`" class="custom-label">
          <span class="text">{{ item.title }}</span>
          <!--
          <span v-if="diffDays(Date.now(), item.writeDate) <= 3" class="flag-new">
            <MsfFlag data="NEW" color="accent2" size="small" />
          </span>
          -->
          <span v-if="item.answer?.status?.code === 'E'" class="flag-done">
            <MsfFlag data="답변완료" color="accent2" size="small" />
          </span>
          <span v-if="item.answer?.status?.code === 'R'" class="flag-done">
            <MsfFlag data="답변대기" size="small" variant="outlined" />
          </span>
          <span v-if="item.answer?.status?.code === 'C'" class="flag-done">
            <MsfFlag data="진행중" size="small" variant="outlined" />
          </span>
        </div>
        <div class="etc-info">
          <span class="info-field" v-if="item.category?.code">{{ item.category?.title }}</span>
          <span class="info-at" v-if="item.writeDate">{{
            formatDatetimeMinutes(item.writeDate)
          }}</span>
        </div>
      </template>
      <template #content="{ item }">
        <div class="board-content" v-if="item.contents">
          <!-- 질문영역 -->
          <div class="board-question">
            <em class="qna-mark">Q.</em>
            <div class="qna-cont">
              <h4 class="board-title" v-if="item.title">{{ item.title }}</h4>
              <div class="board-content">{{ item.contents }}</div>
            </div>
          </div>
          <!-- 답변영역 -->
          <div class="board-answer" v-if="item.answer?.status?.code === 'E'">
            <em class="qna-mark">A.</em>
            <div class="qna-cont">
              {{ item.answer?.contents }}
            </div>
          </div>
        </div>
      </template>
    </MsfAccordion>
    <!-- 페이징 -->
    <MsfPagination
      v-if="list && list.length > 0 && totalCount > itemsPerPage"
      v-model:page="currentPage"
      :total="totalCount"
      :items-per-page="itemsPerPage"
      :page-size="5"
      @change="onPageChange"
    />
  </MsfDialog>
</template>

<script setup>
import { ref, reactive, watch, nextTick } from 'vue'
import { addMonths } from 'date-fns'
import { useMsfMainStore } from '@/stores/msf_main'
import { post } from '@/libs/api/msf.api'
import { formatDate, formatDatetimeMinutes } from '@/libs/utils/date.utils'
import { formatCurrency, isEmpty } from '@/libs/utils/string.utils'

const mainStore = useMsfMainStore()

const props = defineProps({
  modelValue: Boolean,
  target: Object,
})

const emit = defineEmits(['update:modelValue', 'open', 'regist', 'close'])

// 퍼블샘플용
const searchData = reactive({
  category: props.target?.category?.code || '',
  value: props.target?.title || '', //검색어입력 필드
  startDate: formatDate(props.target?.writeDate) || formatDate(addMonths(new Date(), -1)),
  endDate: formatDate(props.target?.writeDate) || formatDate(new Date()),
})

// 상태 관리
const openedItems = ref([]) // 아코디언 열림 상태
const currentPage = ref(1) // 현재 페이지
const itemsPerPage = ref(10) // 한 페이지당 보여줄 개수

// 데이터 계산
// 현재 페이지에 보여줄 데이터만 추출
const totalCount = ref(0)
const list = ref([])

const searchQna = async (page) => {
  await nextTick()

  openedItems.value = []
  currentPage.value = page
  const response = await post('/api/main/qna/list', {
    page: {
      pageNum: currentPage.value,
      rowSize: itemsPerPage.value,
    },
    ...searchData,
  })
  if (response.code !== '0000') {
    return false
  }
  totalCount.value = response.meta?.page?.totalCount || 0
  list.value = response.data || []

  if (props.target) {
    // 2. 특정 ID로 이동해야 하는 경우 로직 수행
    const targetIndex = list.value.findIndex((item) => item.id === props.target.id)

    if (targetIndex !== -1) {
      currentPage.value = Math.ceil((targetIndex + 1) / itemsPerPage.value)

      await nextTick() // 페이지 데이터 렌더링 대기

      // 3. 해당 아코디언만 새로 열기
      openedItems.value = [props.target.id]

      await nextTick() // 아코디언 펼쳐짐 대기

      // 4. 스크롤 이동 (ID 체크 주의: notice-item-...)
      const element = document.getElementById(`notice-item-${props.target.id}`)
      if (element) {
        setTimeout(() => {
          element.scrollIntoView({ behavior: 'smooth', block: 'start' })
          element.focus()
        }, 100)
      }
    }
  }
}

const addHitCountQna = async (ids) => {
  const response = await post('/api/main/qna/hits/add', {
    ids: ids,
  })
  if (response.code !== '0000') {
    return false
  }
  return true
}

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    totalCount.value = 0
    list.value = []
    searchData.category = ''
    searchData.value = ''
    searchData.startDate = formatDate(addMonths(new Date(), -1))
    searchData.endDate = formatDate(new Date())
    emit('update:modelValue', false)
    emit('close')
  }
}

// 페이지 변경 시 아코디언 닫기 (선택 사항)
const onPageChange = (page) => {
  openedItems.value = []
  searchQna(page)
}

const onClickRegistPopup = () => {
  emit('regist')
  onClose()
}

// 팝업이 열릴 때 target 처리
watch(
  () => props.modelValue,
  async (isOpen) => {
    if (isOpen) {
      // 1. 팝업이 열릴 때 열림 상태를 초기화
      openedItems.value = []

      await searchQna(1)
    }
  },
)
watch(
  () => openedItems.value,
  async (newVal, oldVal) => {
    const oVal = newVal.filter((v) => !oldVal.includes(v))
    const nVal = oVal.filter((v) => !mainStore.hittedQnas.includes(v))
    if (!nVal || nVal.length === 0) {
      return
    }
    const result = await addHitCountQna(nVal)
    if (result) {
      mainStore.addHittedQnas(nVal)
    }
  },
  { immediate: true, deep: true },
)
watch(
  () => props.target,
  async (newVal) => {
    if (isEmpty(newVal?.title)) {
      return
    }
    searchData.category = newVal?.category?.code || ''
    searchData.value = newVal?.title || '' //검색어입력 필드
    searchData.startDate = formatDate(newVal?.writeDate) || formatDate(addMonths(new Date(), -1))
    searchData.endDate = formatDate(newVal?.writeDate) || formatDate(new Date())

    await searchQna(1)
  },
  { immediate: true, deep: true },
)
</script>

<style lang="scss" scoped></style>
