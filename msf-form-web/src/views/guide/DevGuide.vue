<script setup>
import { ref, computed, watch, markRaw } from 'vue'
import { useRoute, useRouter } from 'vue-router'

// 1. 각 컴포넌트별 가이드 파일 임포트
import {
  MsfDateInputGuide,
  MsfDateRangeGuide,
  MsfBirthdayInputGuide,
  MsfNumberInputGuide,
  MsfTimeInputGuide,
  MsfMobileInputGuide,
  MsfAddressInputGuide,
  MsfRegNoInputGuide,
  MsfEmailInputGuide,
  MsfTelInputGuide,
  MsfBizRegInputGuide,
  FocusFieldGuide,
} from './components'

const route = useRoute()
const router = useRouter()

// 2. 가이드 탭 구성 (컴포넌트 객체를 직접 매핑)
const guideTabs = {
  msfDateInput: { name: 'MsfDateInput', component: markRaw(MsfDateInputGuide) },
  msfDateRange: {
    name: 'MsfDateRange',
    component: markRaw(MsfDateRangeGuide),
  },
  msfBirthdayInput: { name: 'MsfBirthdayInput', component: markRaw(MsfBirthdayInputGuide) },
  msfNumberInput: { name: 'MsfNumberInput', component: markRaw(MsfNumberInputGuide) },
  msfTimeInput: { name: 'MsfTimeInput', component: markRaw(MsfTimeInputGuide) },
  msfMobileInput: { name: 'MsfMobileInput', component: markRaw(MsfMobileInputGuide) },
  msfAddressInput: { name: 'msfAddressInput', component: markRaw(MsfAddressInputGuide) },
  msfRegNoInput: { name: 'msfRegNoInput', component: markRaw(MsfRegNoInputGuide) },
  msfEmailInput: { name: 'msfEmailInput', component: markRaw(MsfEmailInputGuide) },
  msfTelInput: { name: 'msfTelInput', component: markRaw(MsfTelInputGuide) },
  MsfBizRegInput: { name: 'MsfBizRegInput', component: markRaw(MsfBizRegInputGuide) },
  FocusFieldGuide: { name: 'focusField utils', component: markRaw(FocusFieldGuide) },
}

// 3. 현재 선택된 탭 관리
const selectedNav = ref(route.query.tab || 'msfDateInput')

// 4. 현재 화면에 뿌릴 컴포넌트 계산
const activeComponent = computed(() => {
  return guideTabs[selectedNav.value]?.component || guideTabs['msfDateInput'].component
})

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
          <h1 class="title">M모바일 스마트서식지 개발 가이드</h1>
        </div>
      </div>
    </header>

    <nav class="guideNav">
      <div class="guideInner">
        <div class="navTabs">
          <button
            v-for="(tab, key) in guideTabs"
            :key="key"
            :class="['navTab', { active: selectedNav === key }]"
            @click="handleTabChange(key)"
          >
            {{ tab.name }}
          </button>
        </div>
      </div>
    </nav>

    <div class="guideContainer">
      <div class="guideInner">
        <div class="guideContent">
          <component :is="activeComponent" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import './PubList.scss';
</style>
