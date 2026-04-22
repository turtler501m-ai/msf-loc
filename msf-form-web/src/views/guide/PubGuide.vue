<script setup>
import { ref, computed, watch, markRaw } from 'vue'
import { useRoute, useRouter } from 'vue-router'

// 1. 각 컴포넌트별 가이드 파일 임포트
import {
  ButtonGuide,
  ButtonGroupGuide,
  CheckboxGuide,
  CheckboxGroupGuide,
  IconGuide,
  InputGuide,
  FormGroupGuide,
  RadioGuide,
  RadioGroupGuide,
  ChipGuide,
  DialogGuide,
  AlertGuide,
  TextareaGuide,
  SelectGuide,
  TableGuide,
  SearchBoxGuide,
  ClassGuide,
  FileButtonGuide,
} from './components'

const route = useRoute()
const router = useRouter()

// 2. 가이드 탭 구성 (컴포넌트 객체를 직접 매핑)
const guideTabs = {
  button: { name: 'Button', component: markRaw(ButtonGuide) },
  buttonGroup: { name: 'ButtonGroup', component: markRaw(ButtonGroupGuide) },
  FileButton: { name: 'FileButton', component: markRaw(FileButtonGuide) },
  icon: { name: 'Icon', component: markRaw(IconGuide) },
  input: { name: 'input', component: markRaw(InputGuide) },
  textarea: { name: 'Textarea', component: markRaw(TextareaGuide) },
  select: { name: 'Select', component: markRaw(SelectGuide) },
  checkbox: { name: 'Checkbox', component: markRaw(CheckboxGuide) },
  checkboxGroup: { name: 'CheckboxGroup', component: markRaw(CheckboxGroupGuide) },
  radio: { name: 'Radio', component: markRaw(RadioGuide) },
  radioGroup: { name: 'RadioGroup', component: markRaw(RadioGroupGuide) },
  chip: { name: 'Chip', component: markRaw(ChipGuide) },
  formGroup: { name: 'FormGroup', component: markRaw(FormGroupGuide) },
  searchBox: { name: 'SearchBox', component: markRaw(SearchBoxGuide) },
  table: { name: 'Table', component: markRaw(TableGuide) },
  alert: { name: 'Alert', component: markRaw(AlertGuide) },
  dialog: { name: 'Dialog', component: markRaw(DialogGuide) },
  class: { name: 'Class', component: markRaw(ClassGuide) },
}

// 3. 현재 선택된 탭 관리
const selectedNav = ref(route.query.tab || 'button')

// 4. 현재 화면에 뿌릴 컴포넌트 계산
const activeComponent = computed(() => {
  return guideTabs[selectedNav.value]?.component || guideTabs['button'].component
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
          <h1 class="title">M모바일 스마트서식지 퍼블리싱 가이드</h1>
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
