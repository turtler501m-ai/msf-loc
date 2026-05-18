<!--
  Step이 있는 경우 화면의 레이아웃
-->
<template>
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
      :use-lock="isLayoutLocked"
    >
      <MsfButton variant="text" @click="onClickTempCompleteBtn">접수 완료</MsfButton>
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
              :ref="(el) => setStepRef(el, i)"
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
  onBeforeUnmount,
} from 'vue'
import { useRoute } from 'vue-router'
import { getFormTypeCode } from '@/libs/utils/comn.utils'
import { getFormComponent, showAlert, showConfirm } from '@/libs/utils/comp.utils'
import { useMsfMenuStore } from '@/stores/msf_menu'
import { useMsfStepStore } from '@/stores/msf_step'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { useMsfFormTerminationStore } from '@/stores/msf_termination.js'
import { mainScrollRef, isLayoutLocked } from '@/hooks/useGlobalScroll'

const route = useRoute()
const menuStore = useMsfMenuStore()
const stepStore = useMsfStepStore()

const domain = ref(route.params?.domain)
const tempCode = ref()

const getCurrentFormStore = () => {
  if (route.params?.domain === 'newchange') return useMsfFormNewChgStore()
  if (route.params?.domain === 'termination') return useMsfFormTerminationStore()
  return null
}

// 도메인에 따른 스토어 인스턴스 반환 및 초기화 유틸
const resetCurrentStore = () => {
  console.log(`[MsfFormView] Resetting form and step stores`)
  getCurrentFormStore()?.resetAll?.()

  // 스텝 스토어도 초기화 (현재 도메인 기준으로 초기 상태 세팅)
  if (route.params?.domain) {
    stepStore.initSteps(route.params.domain)
  }
}

// 뷰의 모든 로컬 상태를 초기화하는 함수
const resetViewState = () => {
  currentStepIndex.value = 0
  allSteps.value = []
  stepRefs.value = []
  isCurrentStepComp.value = []
  formData.value = {}
  isComplete.value = false
  tempCode.value = null
  console.log(`[MsfFormView] View state reset completed`)
}

onBeforeUnmount(() => {
  resetCurrentStore()
  resetViewState()
})

// 페이지 이동 여부를 체크하는 플래그
const isRouteChange = ref(false)

// 도메인타이틀 정의
const domainTitle = computed(() => {
  return menuStore.getParentMenu(`/form/${route.params?.domain}`)?.name || ''
})

// 1. 전체 스텝 정의 (shallowRef를 써야 성능 이슈가 없습니다)
const allSteps = ref([])

// [추가] 자식 컴포넌트 접근용 배열 및 취합용 데이터 객체
const stepRefs = ref([])
const setStepRef = (el, i) => {
  if (el) {
    stepRefs.value[i] = el
  }
}

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
const showClearBtn = computed(() =>
  ['newchange', 'termination', 'ownerchange'].includes(route.params?.domain || ''),
)

const isStepComplete = computed(() => {
  // 도메인이 newchange일 때만 특수한 계층적 유효성 검사 적용
  if (route.params?.domain === 'newchange') {
    const customerValid = isCurrentStepComp.value[0] || false
    const productValid = isCurrentStepComp.value[1] || false
    const agreementValid = isCurrentStepComp.value[2] || false

    if (currentStepIndex.value === 0) {
      return customerValid
    } else if (currentStepIndex.value === 1) {
      return customerValid && productValid
    } else if (currentStepIndex.value === 2) {
      // Agreement 단계에서는 기본적으로 Customer, Product, Agreement(체크박스)가 모두 완료되어야 함
      // (작성완료 버튼 활성화 기준)
      return customerValid && productValid && agreementValid
    }
  }

  // 기본 로직: 현재 스텝까지 모두 true여야 함
  return isCurrentStepComp.value.slice(0, currentStepIndex.value + 1).every((v) => v === true)
})

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
  await nextTick()

  const historyState = window.history.state
  console.log('>>> [MsfFormView] initRouterParams - historyState:', historyState)

  if (historyState) {
    tempCode.value = historyState.requestKey || historyState.requestkey || historyState.code
  }

  // 첫 번째 스텝 컴포넌트가 준비될 때까지 최대 10번(1초) 시도하며 대기
  let retryCount = 0
  while (!stepRefs.value[0]?.data && retryCount < 10) {
    console.log(`>>> [MsfFormView] Waiting for first step ref... (attempt ${retryCount + 1})`)
    await new Promise((resolve) => setTimeout(resolve, 100))
    await nextTick()
    retryCount++
  }

  if (stepRefs.value[0]?.data) {
    console.log('>>> [MsfFormView] Initializing first step with key:', tempCode.value)
    const result = await stepRefs.value[0].data(tempCode.value)

    if (result && result !== '0') {
      const index = Math.max(0, parseInt(result) - 1)
      currentStepIndex.value = index
      isCurrentStepComp.value = allSteps.value.map((_, i) => i <= index) || []
      stepStore.setActiveIndex(index) // 스텝 인디케이터 동기화
    }
  } else {
    console.warn('>>> [MsfFormView] First step data() method still not ready after retries')
  }
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
  if (route.params?.domain === 'newchange') {
    showConfirm('모두 초기화됩니다. 계속할까요?', async () => {
      if (stepRefs.value[currentStepIndex.value]?.reset) {
        await stepRefs.value[currentStepIndex.value].reset()
      } else if (stepRefs.value[0]?.reset) {
        await stepRefs.value[0].reset()
      }
      initAllSteps()
    })
    return
  }

  if (route.params?.domain === 'termination') {
    const target = ['고객', '상품', '모두'][currentStepIndex.value] || '모두'
    showConfirm(`${target} 영역이 초기화됩니다. 계속할까요?`, async () => {
      const stepRef = stepRefs.value[currentStepIndex.value]
      if (currentStepIndex.value === 1) {
        const store = getCurrentFormStore()
        if (stepRef?.reset) {
          await stepRef.reset()
        } else {
          store?.resetStep?.(1)
        }
        stepStore.setActiveIndex(0)
        currentStepIndex.value = 0
        isCurrentStepComp.value = [false]
        return
      }
      if (currentStepIndex.value >= 2) {
        getCurrentFormStore()?.resetAll?.()
        initAllSteps()
        return
      }
      stepRef?.reset
        ? await stepRef.reset()
        : getCurrentFormStore()?.resetStep?.(currentStepIndex.value)
    })
  }
}

const onClickNextBtn = async () => {
  console.log(`[MsfFormView] Attempting to move to next step from index: ${currentStepIndex.value}`)
  const result = await stepRefs.value[currentStepIndex.value]?.save()
  console.log(`[MsfFormView] Step save result:`, result)

  if (!result) {
    console.warn(`[MsfFormView] Step save failed or returned false. Aborting step change.`)
    return
  }

  // 다음 스텝으로 넘어가고, 새로운 스텝은 아직 검증 전이므로 버튼을 다시 비활성화합니다.
  currentStepIndex.value++
  console.log(`[MsfFormView] Incremented currentStepIndex to: ${currentStepIndex.value}`)
  isCurrentStepComp.value[currentStepIndex.value] = false
}

const onClickCompelteBtn = async () => {
  showConfirm('신청서를 등록하시겠습니까?', async () => {
    if (stepRefs.value[currentStepIndex.value]?.validateWithAlert) {
      const valid = await stepRefs.value[currentStepIndex.value].validateWithAlert()
      if (!valid) {
        return
      }
    }

    // 1. 서버 저장 수행 (Agreement.vue의 save 호출)
    const success = await stepRefs.value[currentStepIndex.value]?.save()

    if (!success) {
      const errorMessage =
        stepRefs.value[currentStepIndex.value]?.getCompleteErrorMessage?.() ||
        '신청서 등록이 실패하였습니다. 다시 시도해 주세요.'
      showAlert(errorMessage)
      return
    }

    // 2. 완료 화면에 표시할 데이터 세팅 (스토어의 실제 데이터 사용)
    const store = useMsfFormNewChgStore()
    formData.value = {
      type: getFormTypeCode(route.path),
      name: store.customer.cstmrNm,
      phone: `${store.customer.mobileNo1}-${store.customer.mobileNo2}-${store.customer.mobileNo3}`,
      formKey: store.applicationKey,
    }

    showAlert('신청서 등록이 완료되었습니다.', () => {
      // 3. 화면 전환
      isComplete.value = true

      // 4. 작성 완료 후 스토어 및 뷰 상태 초기화
      resetCurrentStore()
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

  resetCurrentStore() // 이전 도메인 스토어 리셋
  resetViewState() // 뷰 로컬 상태 리셋

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
watch(
  () => stepStore.activeIndex,
  (newIndex) => {
    currentStepIndex.value = newIndex
    // 강제 이동 시 이전 스텝들도 완료된 것으로 처리 (필요시)
    isCurrentStepComp.value = allSteps.value.map((_, i) => i <= newIndex)
  },
)

watch(currentStepIndex, async (newIndex) => {
  // 페이지 이동(도메인 변경)에 의한 인덱스 변화라면 스크롤 로직 실행 안 함
  if (isRouteChange.value) {
    isRouteChange.value = false // 플래그 초기화 후 종료
    return
  }

  await nextTick()
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
