<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="공지사항"
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
            group-code="SBST_CTG_CD1"
            placeholder="유형"
            class="ut-flex-1"
            all-checked
          />
          <MsfButton variant="primary" noMinWidth @click="searchNotices(1)">검색</MsfButton>
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
    <div class="list-total-count">
      총
      <em> {{ formatCurrency(totalCount) }} </em>건
    </div>
    <!-- 아코디언 -->
    <div v-if="!list || list.length === 0" class="nodata-wrap noIcon">데이터가 없습니다.</div>
    <MsfAccordion v-else v-model="openedItems" :data="list" multiple variant="board">
      <template #label="{ item }">
        <div :id="`notice-item-${item.id}`" class="custom-label">
          <span class="text">{{ item.title }}</span>
          <span v-if="diffDays(new Date(), item.writeDate) <= 3" class="flag-new">
            <MsfFlag data="NEW" color="accent2" size="small" />
          </span>
          <span v-if="item.status === 'done'" class="flag-done">
            <MsfFlag data="답변완료" color="accent2" size="small" />
          </span>
          <span v-if="item.status === 'waiting'" class="flag-done">
            <MsfFlag data="답변대기" color="accent2" size="small" variant="outlined" />
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
        <!-- <h4 class="board-title" v-if="item.title">{{ item.title }}</h4> -->
        <div class="board-content" v-if="item.contents" v-html="item.contents"></div>
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
import { diffDays, formatDate, formatDatetimeMinutes } from '@/libs/utils/date.utils'
import { formatCurrency } from '@/libs/utils/string.utils'

const mainStore = useMsfMainStore()

const props = defineProps({
  modelValue: Boolean,
  search: Object,
  targetId: [String, Number], // 부모에서 넘겨주는 id 값
  data: Array,
  total: [String, Number],
  currentPage: { type: Number, default: 1 },
  itemsPerPage: { type: Number, default: 10 },
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

// 퍼블샘플용
const searchData = reactive(
  props.search || {
    category: '',
    value: '', //검색어입력 필드
    startDate: formatDate(addMonths(new Date(), -1)),
    endDate: formatDate(new Date()),
  },
)

// 상태 관리
const openedItems = ref([]) // 아코디언 열림 상태
const currentPage = ref(props.currentPage) // 현재 페이지
const itemsPerPage = ref(props.itemsPerPage) // 한 페이지당 보여줄 개수

// 데이터 계산
// 현재 페이지에 보여줄 데이터만 추출
const totalCount = ref(props.total || 0)
const list = ref(props.data || [])

const searchNotices = async (page) => {
  openedItems.value = []
  currentPage.value = page
  const response = await post('/api/main/notice/list', {
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
}

const addHitCountNotice = async (ids) => {
  const response = await post('/api/main/notice/hits/add', {
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
  searchNotices(page)
}

// 팝업이 열릴 때 targetId 처리
watch(
  () => props.modelValue,
  async (isOpen) => {
    if (isOpen) {
      if (props.data?.length > 0) {
        currentPage.value = 1
        totalCount.value = props.total
        list.value = props.data
      } else {
        await searchNotices(1)
      }
      // 1. 팝업이 열릴 때 열림 상태를 초기화
      openedItems.value = []

      // 검색 폼 등 다른 상태도 초기화하고 싶다면
      // searchData.searchField = ''
      if (props.targetId) {
        // 2. 특정 ID로 이동해야 하는 경우 로직 수행
        const targetIndex = list.value.findIndex((item) => item.id === props.targetId)

        if (targetIndex !== -1) {
          currentPage.value = Math.ceil((targetIndex + 1) / itemsPerPage.value)

          await nextTick() // 페이지 데이터 렌더링 대기

          // 3. 해당 아코디언만 새로 열기
          openedItems.value = [props.targetId]

          await nextTick() // 아코디언 펼쳐짐 대기

          // 4. 스크롤 이동 (ID 체크 주의: notice-item-...)
          const element = document.getElementById(`notice-item-${props.targetId}`)
          if (element) {
            setTimeout(() => {
              element.scrollIntoView({ behavior: 'smooth', block: 'start' })
              element.focus()
            }, 100)
          }
        }
      }
    }
  },
)
watch(
  () => openedItems.value,
  async (newVal, oldVal) => {
    const oVal = newVal.filter((v) => !oldVal.includes(v))
    const nVal = oVal.filter((v) => !mainStore.hittedNotices.includes(v))
    if (!nVal || nVal.length === 0) {
      return
    }
    const result = await addHitCountNotice(nVal)
    if (result) {
      mainStore.addHittedNotice(nVal)
    }
  },
  { immediate: true, deep: true },
)
</script>

<style lang="scss" scoped></style>
