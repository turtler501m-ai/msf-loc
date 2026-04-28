<!--
  Step이 있는 경우 화면의 레이아웃
-->
<template>
  <MsfButton variant="text" @click="onClickTempCompleteBtn">접수 완료</MsfButton>
  <div v-if="!isComplete" class="step-wrapper-layout">
    <!-- 왼쪽 스텝 표현 -->
    <MsfStepIndicator :currentStep="currentStepIndex" />
    <!-- 스텝 컨텐츠 -->
    <MsfCustomScroll
      :ref="
        (el) => {
          mainScrollRef = el
        }
      "
      class="main-layout-scroll"
    >
      <div class="step-content-wrap">
        <section class="msf-step-content">
          <!-- 타이틀 -->
          <MsfTitleBar>
            <template #title>
              <slot name="title">{{ domainTitle }}</slot>
            </template>
          </MsfTitleBar>
          <!-- 스텝 컨텐츠 내용 -->
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
        <!-- 하단 버튼 영역 -->
        <div class="msf-step-actions">
          <div class="step-action-items left">
            <MsfPrevPayCostPop
              v-if="showPrevPayCostBtn"
              v-model:is-open="openMcpPrevPayCostPop"
              @triggerClick="onClickPrevPayCostBtn"
            />
          </div>
          <div class="step-action-items right">
            <MsfButton variant="secondary" v-if="showClearBtn" @click="onClickClearBtn">
              초기화
            </MsfButton>
            <MsfButton
              variant="primary"
              v-if="!isLastStep"
              :disabled="!isStepComplete"
              @click="onClickNextBtn"
            >
              다음
            </MsfButton>
            <MsfButton
              variant="accent2"
              v-if="isLastStep"
              :disabled="!isStepComplete"
              @click="onClickCompelteBtn"
            >
              작성완료
            </MsfButton>
          </div>
        </div>
      </div>
    </MsfCustomScroll>
  </div>
  <MsfRequestComplete v-else :form-type="formType" :form-data="formData" />
</template>

<script setup>
import {
  computed,
  onBeforeMount,
  onBeforeUpdate,
  onMounted,
  onUpdated,
  ref,
  shallowRef,
  watch,
  nextTick,
} from 'vue'
import { useRoute } from 'vue-router'
import { getFormTypeCode } from '@/libs/utils/comn.utils'
import { getFormComponent, showAlert, showConfirm } from '@/libs/utils/comp.utils'
import { useMsfMenuStore } from '@/stores/msf_menu'
import { useMsfStepStore } from '@/stores/msf_step'
import { mainScrollRef } from '@/hooks/useGlobalScroll'

const route = useRoute()
const menuStore = useMsfMenuStore()
const stepStore = useMsfStepStore()

const domain = ref(route.params?.domain)
const tempCode = ref()

// 도메인타이틀 정의
const domainTitle = computed(() => {
  return menuStore.getParentMenu(`/form/${route.params?.domain}`)?.name || ''
})

// 페이지 이동 여부를 체크하는 플래그
const isRouteChange = ref(false)

// 1. 전체 스텝 정의 (shallowRef를 써야 성능 이슈가 없습니다)
const allSteps = ref([])

// [추가] 자식 컴포넌트 접근용 배열 및 취합용 데이터 객체
const stepRefs = ref([])

const isComplete = ref(false)
const formType = ref('') // 신청서 유형 (신규, 번호이동, 기변 등)
const formData = ref({}) // 신청서 데이터 (각 스텝에서 입력한 내용들을 최종적으로 취합할 객체)

// 2. 상태 관리
const currentStepIndex = ref(0) // 현재 진행 중인 스텝 인덱스
const isCurrentStepComp = ref(stepStore.steps[route.params?.domain]) // '현재' 스텝의 유효성 통과 여부
const openMcpPrevPayCostPop = ref(false)

// 현재 인덱스까지만 잘라서 화면에 보여줄 배열을 계산합니다. (밑으로 쌓이는 효과)
const visibleSteps = computed(() => allSteps.value.slice(0, currentStepIndex.value + 1))
const isLastStep = computed(
  () => allSteps.value.length > 0 && currentStepIndex.value === allSteps.value.length - 1,
)
const showPrevPayCostBtn = computed(() => route.params?.domain === 'newchange')
const showClearBtn = computed(() => route.params?.domain === 'newchange')

const isStepComplete = computed(() =>
  isCurrentStepComp.value.find((v, i) => i <= currentStepIndex.value && v === false) === false
    ? false
    : true,
)

const initAllSteps = async () => {
  isComplete.value = false
  formData.value = {}
  allSteps.value = stepStore.steps[route.params?.domain].map((v) => ({
    component: shallowRef(getFormComponent(route.params?.domain, v)),
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
  showConfirm('모두 초기화됩니다. 계속할까요?', async () => {
    if (stepRefs.value[currentStepIndex.value]?.reset) {
      await stepRefs.value[currentStepIndex.value].reset()
    } else if (stepRefs.value[0]?.reset) {
      await stepRefs.value[0].reset()
    }

    initAllSteps()
  })
}

const onClickNextBtn = async () => {
  const result = await stepRefs.value[currentStepIndex.value]?.save()
  if (!result) {
    return
  }

  // 다음 스텝으로 넘어가고, 새로운 스텝은 아직 검증 전이므로 버튼을 다시 비활성화합니다.
  currentStepIndex.value++
  isCurrentStepComp.value[currentStepIndex.value] = false
}

const onClickCompelteBtn = async () => {
  showConfirm('신청서를 등록하시겠습니까?', async () => {
    // result 형식: { name: '홍길동', phone: '010-1234-5678', formKey: 'some-key' }
    const result = await stepRefs.value[currentStepIndex.value]?.save()
    if (!result) {
      showAlert('신청서 등록이 실패하였습니다. 다시 시도해 주세요.')
      return false
    }

    showAlert('신청서 등록이 완료되었습니다.', () => {
      // 최종 신청 완료화면으로 이동
      formData.value = { type: getFormTypeCode(route.path), ...result }
      isComplete.value = true
    })
  })
}

const onClickTempCompleteBtn = () => {
  formData.value = {
    type: getFormTypeCode(route.path),
    name: '홍길동',
    phone: '010-1234-5678',
    formKey: '1111',
  }
  isComplete.value = true
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

  isRouteChange.value = true // 페이지 이동 중임을 표시
  domain.value = route.params?.domain
  await initAllSteps()
})
onUpdated(async () => {
  if (domain.value === route.params?.domain) {
    return
  }

  await initRouterParams()
})

// 해당하는 스텝의 컨텐츠 상단으로 스크롤 이동시킴
watch(currentStepIndex, async (newIndex) => {
  // 페이지 이동(도메인 변경)에 의한 인덱스 변화라면 스크롤 로직 실행 안 함
  if (isRouteChange.value) {
    isRouteChange.value = false // 플래그 초기화 후 종료
    return
  }

  await nextTick()

  // 실제 스텝 이동 시에만 실행되는 로직
  setTimeout(() => {
    const stepComponent = stepRefs.value[newIndex]
    if (!stepComponent) return

    const targetEl = stepComponent.$el?.querySelector('.page-step-panel') || stepComponent.$el
    const container = document.querySelector('.step-content-wrap')

    if (container && targetEl) {
      const containerRect = container.getBoundingClientRect()
      const targetRect = targetEl.getBoundingClientRect()

      // 좌표 계산 최적화 (상대 좌표 + 현재 스크롤 위치)
      const scrollTarget = container.scrollTop + (targetRect.top - containerRect.top) - 40

      // MsfCustomScroll 또는 일반 div 처리
      const scrollHandler = mainScrollRef.value?.scrollTo ? mainScrollRef.value : container

      scrollHandler.scrollTo({
        top: scrollTarget,
        behavior: 'smooth',
      })

      console.log(`${newIndex}번 스텝으로 사용자 유도 스크롤 완료`)
    }
  }, 100)
})
</script>

<style lang="scss" scoped>
.step-wrapper-layout {
  display: block;
  @include flex();
  height: 100%;
}
.step-content-wrap {
  padding-block: rem(32px) rem(40px);
  padding-inline: rem(24px);
  flex: 1;
  // overflow-y: auto;
  .msf-step-content {
    // padding-block: var(--layout-padding-y) var(--layout-padding-y2);
    // padding-inline: var(--layout-padding-x);
  }
}
// msf-step-actions
.msf-step-actions {
  margin-top: rem(40px);
  border: var(--border-width-base) solid var(--color-gray-150);
  border-radius: var(--border-radius-m);
  padding: rem(16px);
  @include flex($h: space-between);
  .step-action-items {
    @include flex() {
      gap: var(--spacing-x2);
    }
  }
}
</style>
