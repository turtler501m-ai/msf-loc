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
          <MsfInput v-model="formData.searchField" class="ut-w-347" placeholder="검색어 입력" />
          <MsfSelect
            title="유형"
            v-model="formData.type"
            :options="[
              { label: '유형1', value: 'type1' },
              { label: '유형2', value: 'type2' },
            ]"
            placeholder="유형"
            class="ut-flex-1"
          />
          <MsfButton variant="primary" noMinWidth>검색</MsfButton>
        </MsfStack>
        <MsfStack type="field" class="ut-w-347">
          <MsfDateRange
            v-model:from="rangeDatePickerValue.start"
            v-model:to="rangeDatePickerValue.end"
            class="ut-w100p"
          />
        </MsfStack>
      </MsfStack>
    </MsfBox>
    <!-- // 검색영역 -->
    <div class="list-total-count">총 <em>1,200</em>건</div>
    <!-- 아코디언 -->
    <MsfAccordion v-model="openedItems" :data="displayedData" multiple variant="board">
      <template #label="{ item }">
        <div :id="`notice-item-${item.id}`" class="custom-label">
          <span class="text">{{ item.title }}</span>
          <span v-if="item.isNew" class="flag-new">
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
          <span class="info-field" v-if="item.field">{{ item.field }}</span>
          <span class="info-at" v-if="item.date">{{ item.date }}</span>
        </div>
      </template>
      <template #content="{ item }">
        <h4 class="board-title" v-if="item.title">{{ item.title }}</h4>
        <div class="board-content" v-if="item.content">
          {{ item.content }}
        </div>
      </template>
    </MsfAccordion>
    <!-- 페이징 -->
    <MsfPagination
      v-model:page="currentPage"
      :total="NOTICE_DATA.length"
      :items-per-page="itemsPerPage"
      :page-size="5"
      @change="onPageChange"
    />
  </MsfDialog>
</template>

<script setup>
import { ref, reactive, watch, computed, nextTick } from 'vue'
import { NOTICE_DATA } from '@/views/guide/mock'

const props = defineProps({
  modelValue: Boolean,
  targetId: String, // 부모에서 넘겨주는 id 값
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

// 상태 관리
const openedItems = ref([]) // 아코디언 열림 상태
const currentPage = ref(1) // 현재 페이지
const itemsPerPage = ref(10) // 한 페이지당 보여줄 개수

// 데이터 계산
// 현재 페이지에 보여줄 데이터만 추출
const displayedData = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value
  const end = start + itemsPerPage.value
  return NOTICE_DATA.slice(start, end)
})

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// 퍼블샘플용
const formData = reactive({
  searchField: '', //검색어입력 필드
  gubun: '', //신청서 구분
})
const rangeDatePickerValue = ref({ start: '', end: '' }) //기간

// 페이지 변경 시 아코디언 닫기 (선택 사항)
const onPageChange = () => {
  openedItems.value = []
}

// 팝업이 열릴 때 targetId 처리
watch(
  () => props.modelValue,
  async (isOpen) => {
    if (isOpen) {
      // 1. 팝업이 열릴 때 열림 상태를 초기화
      openedItems.value = []

      // 검색 폼 등 다른 상태도 초기화하고 싶다면
      // formData.searchField = ''

      if (props.targetId) {
        // 2. 특정 ID로 이동해야 하는 경우 로직 수행
        const targetIndex = NOTICE_DATA.findIndex((item) => item.id === props.targetId)

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
      } else {
        // targetId가 없을 경우 기본적으로 1페이지를 보여주도록 설정 가능
        currentPage.value = 1
      }
    }
  },
)
</script>

<style lang="scss" scoped></style>
