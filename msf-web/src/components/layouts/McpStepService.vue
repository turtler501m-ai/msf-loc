<template>
  <div class="step-service-layout">
    <section class="step-service-content">
      <div v-for="(step, index) in visibleSteps" :key="index">
        <component
          :is="step.component"
          :isActive="index <= currentStepIndex"
          :ref="
            (el) => {
              if (el) stepRefs[index] = el
            }
          "
          @update:complete="(isComp) => handleComplete(isComp, index)"
        />
      </div>
    </section>
    <div class="step-service-actions">
      <Button v-if="isShowPrevPayCostBtn()" variant="outline" @click="clickPrevPayCostBtn">
        예상 납부금액
      </Button>
      <Button v-if="isShowClearBtn()" variant="secondary" @click="clickClearBtn">초기화</Button>
      <!-- 테스트용: 체크 없이 다음/작성완료 클릭 가능 (임시) -->
      <Button v-if="!isLastStep" @click="clickNextBtn"> 다음 </Button>
      <Button v-if="isLastStep" variant="outline" @click="clickCompelteBtn">작성완료</Button>
      <mcp-prev-pay-cost-pop
        v-if="isShowPrevPayCostBtn()"
        v-model:is-open="openMcpPrevPayCostPop"
      />
    </div>
  </div>
</template>

<script setup>
import { computed, markRaw, onBeforeMount, onBeforeUpdate, ref, shallowRef } from 'vue'
import { useRoute } from 'vue-router'
import { getStepComponent } from '@/utils/mcp_components'
import { useMsfMenuStore } from '@/stores/msf_menu'
import McpPrevPayCostPop from '@/components/commons/McpPrevPayCostPop.vue'

const route = useRoute()
const menuStore = useMsfMenuStore()

// 1. 전체 스텝 정의 (shallowRef를 써야 성능 이슈가 없습니다)
const allSteps = ref([])

// [추가] 자식 컴포넌트 접근용 배열 및 취합용 데이터 객체
const stepRefs = ref([])
const masterFormData = ref({})

// 2. 상태 관리
const currentStepIndex = ref(0) // 현재 진행 중인 스텝 인덱스
const isCurrentStepComp = ref([]) // '현재' 스텝의 유효성 통과 여부

// 현재 인덱스까지만 잘라서 화면에 보여줄 배열을 계산합니다. (밑으로 쌓이는 효과)
const visibleSteps = computed(() => allSteps.value.slice(0, currentStepIndex.value + 1))
const isLastStep = computed(
  () => currentStepIndex.value === allSteps.value.length - 1 && allSteps.value.length > 0,
)

const openMcpPrevPayCostPop = ref(false)

const isShowPrevPayCostBtn = () => {
  const currentMenu = menuStore.getCurrentParentMenu(route.path)
  return currentMenu?.id === '01'
}

const isShowClearBtn = () => {
  const currentMenu = menuStore.getCurrentParentMenu(route.path)
  return currentMenu?.id === '01'
}

// 3. 자식으로부터 유효성 상태를 전달받는 함수
const handleComplete = (isComp, index) => {
  // 과거 스텝의 값이 변경되어 이벤트가 올라올 수도 있으므로,
  // 오직 "현재 진행 중인 마지막 스텝"의 이벤트만 '다음' 버튼 상태에 반영합니다.
  if (index <= currentStepIndex.value) {
    isCurrentStepComp.value[index] = isComp
  }
}

const clickPrevPayCostBtn = () => {
  openMcpPrevPayCostPop.value = true
}

const clickClearBtn = () => {
  if (confirm('모두 초기화됩니다. 계속할까요?')) {
    changeAllSteps(route.path)
  }
}

const isValidComponent = async () => {
  const trueStepsIndex = isCurrentStepComp.value.filter((v, i) => i <= currentStepIndex.value)
  for (let i = 0; i < trueStepsIndex.length; i++) {
    const currentChild = stepRefs.value[i]
    if (!currentChild || !currentChild.validate) {
      continue
    }
    if (!currentChild.validate) {
      continue
    }
    const result = await currentChild.validate()
    if (!result.valid) {
      alert(result.message)
      return false
    }
  }
  return true
}

/** 테스트용: true 시 다음/작성완료 시 검증(validate) 생략 */
const SKIP_VALIDATION_FOR_TEST = true

const clickNextBtn = async () => {
  if (!SKIP_VALIDATION_FOR_TEST) {
    const ok = await isValidComponent()
    if (!ok) return
  }
  currentStepIndex.value++
  isCurrentStepComp.value[currentStepIndex.value] = false
}

const clickCompelteBtn = async () => {
  if (!SKIP_VALIDATION_FOR_TEST) {
    const ok = await isValidComponent()
    if (!ok) return
  }
  const lastRef = stepRefs.value[currentStepIndex.value]
  if (lastRef?.save) {
    await lastRef.save()
  } else {
    alert('신청서 등록이 완료되었습니다.')
  }
}

const changeAllSteps = (routePath) => {
  const currentMenu = menuStore.getCurrentParentMenu(routePath)
  allSteps.value = currentMenu?.children
    ? currentMenu.children.map((sub) => ({
        component: shallowRef(markRaw(getStepComponent(sub.url))),
      }))
    : []
  currentStepIndex.value = 0
  isCurrentStepComp.value = allSteps.value.map(() => false) || []
  stepRefs.value = []
  masterFormData.value = {}
}

const isStepComplete = computed(() => {
  return isCurrentStepComp.value.find((v, i) => i <= currentStepIndex.value && v === false) ===
    false
    ? false
    : true
})

onBeforeMount(() => {
  console.log('onBeforeMount - McpStepService')
  changeAllSteps(route.path)
})
onBeforeUpdate(() => {
  console.log('onBeforeUpdate - McpStepService')
})
</script>

<style scoped>
@reference "tailwindcss";

.step-service-layout {
  @apply flex flex-col max-h-[calc(100vh-4rem)] justify-between gap-2 bg-(--p-teal-50);
}

.step-service-content {
  @apply h-[calc(100vh-6rem)] bg-white overflow-y-auto p-4 flex flex-col gap-4;
}

.step-service-actions {
  @apply flex justify-end gap-1 px-4;
}
</style>
