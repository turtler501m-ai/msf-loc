<script setup>
import { ref, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { pageData, navTabNames } from './pageData'

const route = useRoute() //현재주소
const router = useRouter() //이동

// 상태 관리: 현재 탭 선택
const selectedNav = ref(route.query.tab || 'formNewChg')

// 데이터 설정: 현재 탭의 페이지들과 테이블 컬럼명
const currentData = computed(() => pageData[selectedNav.value] || { pages: [] })
const columns = computed(() => {
  const pages = currentData.value?.pages
  return pages?.length > 0 ? Object.keys(pages[0]) : []
})

// 상태 설정 매핑 (SCSS 클래스명, 텍스트와 매칭)
const statusMap = {
  completed: { text: '완료', class: 'statusCompleted', weight: 1 },
  progress: { text: '진행중', class: 'statusProgress', weight: 0.5 },
  waiting: { text: '대기', class: 'statusWaiting', weight: 0 },
  removed: { text: '제외', class: 'statusRemoved', weight: 0 },
}

// 통계 계산 관련
const allPages = computed(() => Object.values(pageData).flatMap((data) => data.pages))

const stats = computed(() => {
  const getCount = (state) => allPages.value.filter((p) => p['상태'] === state).length
  const total = allPages.value.length
  return {
    total,
    waiting: getCount('waiting'),
    progress: getCount('progress'),
    completed: getCount('completed'),
    removed: getCount('removed'),
  }
})

const getPercentage = (count) =>
  stats.value.total ? ((count / stats.value.total) * 100).toFixed(1) : '0.0'

const getCompletionRate = (nav) => {
  const pages = pageData[nav]?.pages || []
  if (pages.length === 0) return '0/0 (0%)'
  const weightedSum = pages.reduce((acc, p) => acc + (statusMap[p['상태']]?.weight || 0), 0)
  return `${weightedSum}/${pages.length} (${((weightedSum / pages.length) * 100).toFixed(1)}%)`
}

// 이벤트: 탭 변경 및 URL 동기화
const handleTabChange = (nav) => {
  selectedNav.value = nav
  router.push({ query: { tab: nav } })
}

watch(
  () => route.query.tab,
  (newTab) => {
    if (newTab) selectedNav.value = newTab
  },
)
</script>

<template>
  <div class="guideWrapper">
    <header class="guideHeader">
      <div class="guideInner">
        <div class="headerContent">
          <h1 class="title">M모바일 스마트서식지 <span>퍼블리싱 목록</span></h1>
          <div class="stats">
            <span class="statItem"
              >전체: <strong>{{ stats.total }}</strong
              >건</span
            >
            <span class="statItem statWaiting"
              >진행대기: <strong>{{ stats.waiting }}</strong
              >건 ({{ getPercentage(stats.waiting) }}%)</span
            >
            <span class="statItem statProgress"
              >진행중: <strong>{{ stats.progress }}</strong
              >건 ({{ getPercentage(stats.progress) }}%)</span
            >
            <span class="statItem statCompleted"
              >완료: <strong>{{ stats.completed }}</strong
              >건 ({{ getPercentage(stats.completed) }}%)</span
            >
            <span class="statItem statRemoved"
              >제외: <strong>{{ stats.removed }}</strong
              >건 ({{ getPercentage(stats.removed) }}%)</span
            >
          </div>
        </div>
      </div>
    </header>

    <nav class="guideNav">
      <div class="guideInner">
        <div class="navTabs">
          <button
            v-for="(name, key) in navTabNames"
            :key="key"
            :class="['navTab', { active: selectedNav === key }]"
            @click="handleTabChange(key)"
          >
            {{ name }}
          </button>
        </div>
      </div>
    </nav>

    <div class="guideContainer">
      <div class="guideInner">
        <div class="guideContent">
          <div class="guideNotice">
            <p>- [2026-04-27] 기타 - '비밀번호 변경' 추가</p>
            <p>- [2026-04-27] 서비스변경 - 부가서비스 팝업들 추가</p>
            <p>- [2026-04-24] 명의변경 - 고객 - 양도인 신분증 인증 삭제</p>
            <p class="ut-weight-bold">
              - [2026-04-24] 서비스변경 - 고객 <span class="ut-color-point">디자인 type01</span>로
              결정, 차후 type02도 활용 가능성 있음
            </p>
            <p>- [2026-04-17] 서비스변경 - 데이터쉐어링 안내문구 추가, 요금제 변경 동의 영역추가</p>
            <p class="ut-weight-bold">- [2026-04-17] 신규/변경 - 안면인증 팝업 (S101020104) 추가</p>
            <p>- [2026-04-17] 신규/변경 - 신규번호 검색 팝업 (S101030105) 데이터 없는 경우 추가</p>
            <p class="ut-color-point">- [2026-04-17] '이메일주소' 영역 필수표시 삭제 (설계수정)</p>
          </div>

          <p class="completionRate">진행률: {{ getCompletionRate(selectedNav) }}</p>

          <table class="table">
            <thead>
              <tr>
                <th class="colNum">No.</th>
                <th
                  v-for="(column, idx) in columns"
                  :key="idx"
                  :class="{
                    colId: column === 'ID',
                    colState: column === '상태',
                    // colFile: column === 'FILE',
                    colDate: column === '완료일' || column === '최종수정일',
                    colMemo: column === '비고',
                  }"
                >
                  {{ column }}
                </th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(page, index) in currentData.pages"
                :key="index"
                :class="{ rowRemoved: page['상태'] === 'removed' }"
              >
                <td>{{ index + 1 }}</td>
                <td
                  v-for="(column, colIdx) in columns"
                  :key="colIdx"
                  :class="{ colFile: column === 'FILE', colMemo: column === '비고' }"
                >
                  <template v-if="column === '상태'">
                    <span :class="['statusBadge', statusMap[page[column]]?.class]">
                      {{ statusMap[page[column]]?.text }}
                    </span>
                  </template>

                  <template v-else-if="column === 'PATH'">
                    <a
                      :href="page[column] || '#'"
                      class="path"
                      target="_blank"
                      rel="noopener noreferrer"
                    >
                      {{ page[column] }}
                    </a>
                  </template>

                  <template v-else>{{ page[column] || '' }}</template>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import './PubList.scss';
</style>
