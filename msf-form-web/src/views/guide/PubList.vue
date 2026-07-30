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

// 비고란은 데이터에 넣은 <br> 또는 줄바꿈을 화면 줄바꿈으로 표시
const getMemoLines = (value) => String(value || '').split(/<br\s*\/?>|\r?\n/gi)

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
            <p class="ut-weight-bold">
              - [2026-07-24] '구비서류' 팝업 - [필수], [선택] 표시 스타일 추가 (필요시 사용)
              <router-link to="/pub/PopList" target="_blank" class="link-txt ut-color-point"
                >예시화면 링크 ('구비서류' 팝업)
              </router-link>
            </p>
            <p class="ut-weight-bold">
              - [2026-07-14] '유효성 검증' 버튼 스타일 반영 (MsfButton variant="validation")
            </p>
            <p class="">- [2026-07-14] 앱 다운로드 - '계정확인' 팝업 - 버튼스타일 구조 수정</p>
            <p class="ut-weight-bold">
              - [2026-07-14] 비밀번호변경 - 비밀번호 필드 reveal 속성 추가
            </p>
            <p class="ut-color-point ut-weight-bold">
              - [2026-07-13] 신규/변경 - 고객(실사용자) 정보 - '개통회선수' 입력항목, 가입조건 조회
              케이스 추가
            </p>
            <p class="ut-weight-bold">
              - [2026-06-30] 명의변경 - (법인/공공) '신분증 스캔' 삭제, 사업자등록번호 '교부일자'
              추가, 실사용자 '성별' 삭제 / 국가유공자증 '유공자번호' 추가
            </p>
            <p class="ut-weight-bold">
              - [2026-06-30] 신규변경 - (법인/공공) '신분증 스캔' 삭제, 사업자등록번호 '교부일자'
              추가, 실사용자 '성별' 삭제 / 국가유공자증 '유공자번호' 추가
            </p>
            <p>- [2026-06-22] 신규변경 - '신청서 열람 비밀번호 입력' 팝업 안내문구 추가</p>
            <p class="ut-weight-bold">
              - [2026-06-22] '계정 확인' 팝업 추가
              <router-link to="/pub/S106010101" target="_blank" class="link-txt ut-color-point"
                >스마트 신청서 App 설치 페이지
              </router-link>
            </p>
            <p class="">
              - [2026-06-10] 컨텐츠 로딩 형태 샘플추가
              <router-link to="/pub/PopList" target="_blank" class="link-txt ut-color-point"
                >예시화면 링크 ('신규번호 검색' 팝업)
              </router-link>
            </p>
            <p class="ut-weight-bold">
              - [2026-06-01] 퍼블리싱 maxLength 누락 추가, 공통 컴퍼넌트 적용
            </p>
            <p class="">
              - [2026-05-27] '비밀번호 입력' 팝업 안내 문구, '생년월일(YYYYMMDD) 8자리'로 수정
            </p>
            <p class="ut-weight-bold ut-color-point">
              - [2026-05-20] 신규/변경, 서비스변경, 명의변경, 서비스해지 - '대리점 선택' 위치
              고객유형 영역으로 이동
            </p>
            <p class="ut-weight-bold ut-color-point">
              - [2026-05-20] 서비스해지 - '가입유형 선택' -> '해지 휴대폰 정보' 타이틀 변경
            </p>
            <p class="ut-weight-bold">- [2026-05-19] '안면인증' 팝업 설계 수정반영</p>
            <p class="">- [2026-05-07] 서비스해지 - 동의 : '고객 안내 사항' 문구 수정</p>
            <p class="ut-weight-bold ut-color-point">
              - [2026-05-06] 서비스변경 - 상품 : 부가서비스 신청/변경 - 설계수정 반영 (체크박스
              형태로 변경)
            </p>
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

                  <template v-else-if="column === '비고'">
                    <template v-for="(line, lineIdx) in getMemoLines(page[column])" :key="lineIdx">
                      <br v-if="lineIdx > 0" />
                      {{ line }}
                    </template>
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
