<template>
  <div class="msf-step-layout">
    <section class="msf-step-content">
      <div v-for="(s, i) in visibleSteps" :key="i">
        <component
          :is="s.component"
          :isActive="i <= currentStepIndex"
          :ref="
            (el) => {
              if (el) stepRefs[i] = el
            }
          "
          @complete="(r) => onComplete(r, i)"
        />
      </div>
    </section>
    <div class="msf-step-actions">
      <MsfButton v-if="showPrevPayCostBtn" @click="onClickPrevPayCostBtn">
        예상 납부금액
      </MsfButton>
      <MsfButton v-if="showClearBtn" @click="onClickClearBtn">초기화</MsfButton>
      <MsfButton v-if="!isLastStep" @click="onClickNextBtn">
        다음
      </MsfButton>
      <MsfButton v-if="isLastStep" @click="onClickCompelteBtn">
        작성완료
      </MsfButton>
      <MsfPrevPayCostPop v-if="showPrevPayCostBtn" v-model:is-open="openMcpPrevPayCostPop" />
    </div>
  </div>
</template>

<script setup>
import {
  computed,
  markRaw,
  onBeforeMount,
  onBeforeUpdate,
  onMounted,
  onUpdated,
  ref,
  shallowRef,
} from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getStepComponent } from '@/libs/utils/comp.utils'
import { useMsfStepStore } from '@/stores/msf_step'

const route = useRoute()
const router = useRouter()
const stepStore = useMsfStepStore()

const domain = ref(route.params?.domain)
const tempCode = ref()

// 1. 전체 스텝 정의 (shallowRef를 써야 성능 이슈가 없습니다)
const allSteps = ref([])

// [추가] 자식 컴포넌트 접근용 배열 및 취합용 데이터 객체
const stepRefs = ref([])

// 2. 상태 관리
const currentStepIndex = ref(0) // 현재 진행 중인 스텝 인덱스
const isCurrentStepComp = ref(stepStore.steps[route.params?.domain]) // '현재' 스텝의 유효성 통과 여부
const openMcpPrevPayCostPop = ref(false)

// 현재 인덱스까지만 잘라서 화면에 보여줄 배열을 계산합니다. (밑으로 쌓이는 효과)
const visibleSteps = computed(() => allSteps.value.slice(0, currentStepIndex.value + 1))
const isLastStep = computed(
  () => allSteps.value.length > 0 && currentStepIndex.value === allSteps.value.length - 1,
)
const showPrevPayCostBtn = computed(() => route.params?.domain === 'formNewChg')
const showClearBtn = computed(() => route.params?.domain === 'formNewChg')

const isStepComplete = computed(() =>
  isCurrentStepComp.value.find((v, i) => i <= currentStepIndex.value && v === false) === false
    ? false
    : true,
)

const initAllSteps = async () => {
  allSteps.value = stepStore.steps[route.params?.domain].map((v) => ({
    component: markRaw(getStepComponent(route.params?.domain, v)),
  }))

  currentStepIndex.value = 0
  isCurrentStepComp.value = allSteps.value.map(() => false) || []
}

const initRouterParams = async () => {
  /*
      router 전달 방식
      router.push({
        path: '/step/formNewChg',
        state: {
          code: 'xxxxxx',
        }
      })
   */
  // 💡 핵심: 라우터(useRoute)가 아니라, 브라우저의 기본 window 객체에서 꺼냅니다.
  // Vue Router가 전달한 state는 history.state 안에 고스란히 들어있습니다.
  const historyState = window.history.state
  if (historyState && historyState.code) {
    tempCode.value = historyState.code
  }

  if (!tempCode.value) {
    return
  }

  // 임시저장 내역에서 넘어왔을 경우에 초기 설정할 내용 (입력 중이거나 입력 완료한 다음 Step까지 표시 로직) 로직 추가 필요
  if (!stepRefs.value[currentStepIndex.value]?.data) {
    return
  }
  const result = await stepRefs.value[currentStepIndex.value]?.data(tempCode.value)
  if (!result || result === '0') {
    return
  }

  const index = parseInt(result)
  currentStepIndex.value = index
  isCurrentStepComp.value = allSteps.value.map((_, i) => i <= index) || []
}

// 3. 자식으로부터 유효성 상태를 전달받는 함수
const onComplete = (result, index) => {
  // 과거 스텝의 값이 변경되어 이벤트가 올라올 수도 있으므로,
  // 오직 "현재 진행 중인 마지막 스텝"의 이벤트만 '다음' 버튼 상태에 반영합니다.
  if (index === currentStepIndex.value) {
    isCurrentStepComp.value[index] = result
  }
}

const onClickPrevPayCostBtn = () => {
  openMcpPrevPayCostPop.value = true
}

const onClickClearBtn = async () => {
  if (confirm('모두 초기화됩니다. 계속할까요?')) {
    initAllSteps()
  }
}

const onClickNextBtn = async () => {
  // TODO: 테스트용 - 유효성 검사 없이 다음 스텝으로 이동
  // const result = await stepRefs.value[currentStepIndex.value]?.save()
  // if (!result) {
  //   return
  // }

  // 다음 스텝으로 넘어가고, 새로운 스텝은 아직 검증 전이므로 버튼을 다시 비활성화합니다.
  currentStepIndex.value++
  isCurrentStepComp.value[currentStepIndex.value] = false
}

const onClickCompelteBtn = async () => {
  if (!confirm('신청서를 등록하시겠습니까?')) {
    return false
  }

  const result = await stepRefs.value[currentStepIndex.value]?.save()
  if (!result) {
    alert('신청서 등록이 실패하였습니다. 다시 시도해 주세요.')
    return false
  }

  router.push({ path: `/mobile/complete/${route.params?.domain}` })
}

onBeforeMount(async () => {
  await initAllSteps()
})

onMounted(async () => {
  await initRouterParams()
})

onBeforeUpdate(async () => {
  if (domain.value === route.params?.domain) {
    return
  }

  domain.value = route.params?.domain
  await initAllSteps()
})
onUpdated(async () => {
  if (domain.value === route.params?.domain) {
    return
  }

  await initRouterParams()
})
</script>

<style scoped>
@reference "tailwindcss";

.msf-step-layout {
  @apply flex flex-col max-h-[calc(100vh-4rem)] justify-between gap-2 bg-(--p-teal-50);
}

.msf-step-content {
  @apply h-[calc(100vh-6rem)] bg-white overflow-y-auto p-4 flex flex-col gap-4;
}

.msf-step-actions {
  @apply flex justify-end gap-1 px-4;
}
</style>
