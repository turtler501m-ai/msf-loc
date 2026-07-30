<!--
  Step이 있는 경우 화면의 레이아웃
-->
<template>
  <MsfWorkNotice v-if="!isWorkNoticeLoading && isWork" v-model="workNotice" />
  <div v-else-if="!isWorkNoticeLoading && !isComplete" class="step-wrapper-layout">
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
      @scroll="onScroll"
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
          <div class="step-section" v-for="(s, i) in visibleSteps" :key="i">
            <component
              :is="s.component"
              :isActive="i <= currentStepIndex"
              :ref="(el) => setStepRef(el, i)"
              @complete="(r) => onComplete(r, i)"
              @final-complete="onFinalComplete"
            />
          </div>
        </section>
        <!-- 하단 버튼 영역 -->
        <div class="msf-step-actions">
          <div class="step-action-items left">
            <MsfPrevPayCostModal
              v-if="showPrevPayCostBtn"
              v-model:is-open="openMcpPrevPayCostPop"
              @triggerClick="onClickPrevPayCostBtn"
            />
          </div>
          <div class="step-action-items right">
            <MsfButton variant="secondary" v-if="showClearBtn" @click="onClickClearBtn">
              다시쓰기
            </MsfButton>
            <div v-if="!isLastStep" class="step-btn-wrap-local" @click.stop="handleNextBtnClick">
              <MsfButton variant="primary" :disabled="!isStepComplete" class="step-btn-local">
                다음
              </MsfButton>
            </div>
            <div v-if="isLastStep" class="step-btn-wrap-local" @click.stop="handleCompleteBtnClick">
              <MsfButton
                variant="accent2"
                :disabled="isCompleteButtonDisabled"
                class="step-btn-local"
              >
                작성완료
              </MsfButton>
            </div>
          </div>
        </div>
      </div>
    </MsfCustomScroll>
  </div>
  <MsfRequestComplete v-else-if="!isWorkNoticeLoading && isComplete" :request-key="requestKey" />
  <!-- 플로팅 영역: MsfStepIndicator 하단으로 사용시-->
  <div v-if="!isWorkNoticeLoading && !isWork" class="step-bottom">
    <MsfButton variant="step" prefixIcon="newWrite" @click="onClickNewWriteBtn"
      >신규 작성</MsfButton
    >
  </div>
  <!-- // 플로팅 영역: MsfStepIndicator 하단으로 사용시 -->
  <!-- 플로팅 영역 : 우측상단으로 사용시 -->
  <!-- <div class="step-floating" v-if="!isWork && !isComplete">
    <MsfNumberInput v-model="tempRequestKey" placeholder="임시신청번호" class="ut-w-120" />
    <MsfButton variant="subtle" @click="onClickTempCompleteBtn">완료 이동</MsfButton>

    <MsfButton
      v-if="!isWorkNoticeLoading && !isWork"
      variant="primary"
      :iconOnly="isScrollDown ? 'newWrite' : undefined"
      :prefixIcon="isScrollDown ? undefined : 'newWrite'"
      :rounded="isScrollDown"
      :class="['fab-btn', { 'is-down': isScrollDown }]"
      >신규 작성
    </MsfButton>
  </div> -->
  <!-- // 플로팅 영역 : 우측상단으로 사용시 -->
</template>

<script setup>
import {
  computed,
  onBeforeMount,
  onUnmounted,
  ref,
  shallowRef,
  watch,
  nextTick,
  onBeforeUnmount,
} from 'vue'
import { useRoute } from 'vue-router'
import { storeToRefs } from 'pinia'
import { useMsfMenuStore } from '@/stores/msf_menu'
import { useMsfStepStore } from '@/stores/msf_step'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange'
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { useMsfFormOwnChgStore } from '@/stores/msf_ownerChange'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'
import { mainScrollRef, isLayoutLocked } from '@/hooks/useGlobalScroll'
import { getFormTypeCode } from '@/libs/utils/comn.utils'
import { getFormComponent, showAlert, showConfirm } from '@/libs/utils/comp.utils'

const route = useRoute()
const menuStore = useMsfMenuStore()
const stepStore = useMsfStepStore()
const newChgStore = useMsfFormNewChgStore()
const svcChgStore = useMsfFormSvcChgStore()
const ownChgStore = useMsfFormOwnChgStore()
const terminationStore = useMsfFormTerminationStore()
const { workNotice } = storeToRefs(stepStore)

const domain = ref(route.params?.domain)
const tempCode = ref()
const isWorkNoticeLoading = ref(true)

// 스크롤 감지에 따른 버튼 노출 제어(스크롤 방향에 따라서 제어)
const prevTop = ref(0)
const scrollDir = ref('up') // 'up' | 'down'
// const isScrollDown = computed(() => scrollDir.value === 'down')
const onScroll = (data) => {
  const top = data.top
  scrollDir.value = top > prevTop.value ? 'down' : 'up'
  prevTop.value = top
}

const getCurrentFormStore = (targetDomain = route.params?.domain) => {
  if (targetDomain === 'newchange') return newChgStore
  if (targetDomain === 'servicechange') return svcChgStore
  if (targetDomain === 'ownerchange') return ownChgStore
  if (targetDomain === 'termination') return terminationStore
  return null
}

// 도메인에 따른 스토어 인스턴스 반환 및 초기화 유틸
const resetCurrentStore = (storeDomain = domain.value || route.params?.domain) => {
  // console.log(`[MsfFormView] Resetting form and step stores`)
  getCurrentFormStore(storeDomain)?.resetAll?.(true)

  // 폼 간 이동 시 스텝 스토어는 이동 대상 도메인 기준으로 초기화한다.
  const stepDomain = route.params?.domain
  if (stepDomain) {
    stepStore.initSteps(stepDomain)
  }
}

// 뷰의 모든 로컬 상태를 초기화하는 함수
const resetViewState = () => {
  currentStepIndex.value = 0
  allSteps.value = []
  stepRefs.value = []
  isCurrentStepComp.value = []
  completeData.value = {}
  isComplete.value = false
  tempCode.value = null
  // console.log(`[MsfFormView] View state reset completed`)
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

const isWork = computed(() => stepStore.isWorkNotice())

const isComplete = ref(false)
const formType = ref('') // 신청서 유형 (신규, 번호이동, 기변 등)
const completeData = ref({}) // 신청서 데이터 (각 스텝에서 입력한 내용들을 최종적으로 취합할 객체)

// 2. 상태 관리
const currentStepIndex = ref(0) // 현재 진행 중인 스텝 인덱스
const isCurrentStepComp = ref(stepStore.steps[route.params?.domain]) // '현재' 스텝의 유효성 통과 여부
const openMcpPrevPayCostPop = ref(false)

const requestKey = computed(
  () =>
    completeData.value.requestKey ||
    (formType.value === '1'
      ? newChgStore.applicationKey
      : formType.value === '2'
        ? svcChgStore.requestKey
        : formType.value === '3'
          ? ownChgStore.applicationKey
          : formType.value === '4'
            ? terminationStore.requestKey
            : ''),
)

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

const isCompleteButtonDisabled = computed(() => {
  if (!isStepComplete.value) return true
  return route.params?.domain === 'servicechange' && svcChgStore.completeFinalFailed === true
})

const initAllSteps = async (targetDomain = route.params?.domain) => {
  isComplete.value = false
  completeData.value = {}
  allSteps.value = stepStore.steps[targetDomain].map((v) => ({
    component: shallowRef(getFormComponent(targetDomain, v)),
  }))

  currentStepIndex.value = 0
  isCurrentStepComp.value = allSteps.value.map(() => false) || []
}

const prepareFormView = async (targetDomain = route.params?.domain) => {
  if (stepStore.isWorkNotice()) {
    return
  }

  await initAllSteps(targetDomain)

  if (stepStore.activePath !== route.path) {
    stepStore.createParentScanId(route.path)
  }

  isWorkNoticeLoading.value = false
  await nextTick()
  await initRouterParams()
}

const initRouterParams = async () => {
  await nextTick()
  if (isWork.value) {
    return
  }

  const historyState = window.history.state
  // console.log('>>> [MsfFormView] initRouterParams - historyState:', historyState)

  if (historyState) {
    tempCode.value = historyState.requestKey || historyState.requestkey || historyState.code

    // 사용한 history.state 정보 청소 (다른 메뉴 이동 후 복귀 시 오작동 방지)
    const cleanState = { ...historyState }
    delete cleanState.requestKey
    delete cleanState.requestkey
    delete cleanState.code
    window.history.replaceState(cleanState, '')
  }

  // 첫 번째 스텝 컴포넌트가 준비될 때까지 최대 10번(1초) 시도하며 대기
  let retryCount = 0
  while (!stepRefs.value[0]?.data && retryCount < 10) {
    // console.log(`>>> [MsfFormView] Waiting for first step ref... (attempt ${retryCount + 1})`)
    await new Promise((resolve) => setTimeout(resolve, 100))
    await nextTick()
    retryCount++
  }

  // if (stepRefs.value[0]?.data) {
  //   console.log('>>> [MsfFormView] Initializing first step with key:', tempCode.value)
  //   const result = await stepRefs.value[0].data(tempCode.value)

  //   if (result && result !== '0') {
  //     const index = Math.max(0, parseInt(result) - 1)
  //     currentStepIndex.value = index
  //     isCurrentStepComp.value = allSteps.value.map((_, i) => i <= index) || []
  //     stepStore.setActiveIndex(index) // 스텝 인디케이터 동기화
  //     await nextTick()
  //   }
  // } else {
  //   console.warn('>>> [MsfFormView] First step data() method still not ready after retries')
  // }

  if (stepRefs.value[0]?.data) {
    // console.log('>>> [MsfFormView] Initializing first step with key:', tempCode.value)

    // ────────────────────────────────────────────────────────
    // 🔥 [수정] setTimeout을 사용해 모든 자식 컴포넌트의 watch와 마운트
    // 생명주기가 완전히 종료된 "가장 늦은 타이밍"에 실행되도록 보장합니다.
    // ────────────────────────────────────────────────────────
    setTimeout(async () => {
      // console.log('>>> [MsfFormView] 태스크 큐 맨 뒤에서 자식 data() 실행')

      const result = await stepRefs.value[0].data(tempCode.value)

      if (result && result !== '0') {
        const index = Math.max(0, parseInt(result) - 1)
        currentStepIndex.value = index
        isCurrentStepComp.value = allSteps.value.map((_, i) => i <= index) || []
        stepStore.setActiveIndex(index) // 스텝 인디케이터 동기화
        await nextTick()
      }
    }, 50) // 50ms 정도만 밀어주면 자식 폼의 초기화 난동이 완전히 끝난 후 안전하게 데이터를 주입합니다.
  } else {
    // console.warn('>>> [MsfFormView] First step data() method still not ready after retries')
  }
}

const getCompleteDataNewChg = () => {
  return {
    requestKey: newChgStore.applicationKey,
  }
}
const getCompleteDataSvcChg = () => {
  return {
    requestKey: svcChgStore.requestKey,
  }
}
const getCompleteDataOwnChg = () => {
  return {
    requestKey: ownChgStore.applicationKey,
  }
}
const getCompleteDataTermination = () => {
  return {
    requestKey: terminationStore.requestKey,
  }
}

const getCompleteDataByFormType = () => {
  switch (formType.value) {
    case '1':
      return getCompleteDataNewChg()
    case '2':
      return getCompleteDataSvcChg()
    case '3':
      return getCompleteDataOwnChg()
    case '4':
      return getCompleteDataTermination()
    default:
      return {}
  }
}

const completeRequest = (data = getCompleteDataByFormType()) => {
  completeData.value = data || {}
  showAlert('신청서 등록이 완료되었습니다.', () => {
    isComplete.value = true
    resetCurrentStore()
  })
}

const onClickNewWriteBtn = () => {
  showConfirm('신청서를 새로 작성하시겠습니까?', () => {
    window.location.reload() // 새로작성 버튼 클릭 시 페이지를 새로고침하여 초기화
  })
}

// 3. 자식으로부터 유효성 상태를 전달받는 함수
const onComplete = (result, index) => {
  // 과거 스텝의 값이 변경되어 이벤트가 올라올 수도 있으므로,
  // 오직 "현재 진행 중인 마지막 스텝"의 이벤트만 '다음' 버튼 상태에 반영합니다.
  if (index === currentStepIndex.value) {
    isCurrentStepComp.value[index] = result
  }
}

const onFinalComplete = (data) => {
  if (route.params?.domain !== 'servicechange') return
  completeRequest(data)
}

const onClickPrevPayCostBtn = () => {
  openMcpPrevPayCostPop.value = true
}

const onClickClearBtn = async () => {
  if (route.params?.domain === 'newchange') {
    const target = ['고객', '상품', '동의'][currentStepIndex.value] || '동의'
    showConfirm(`${target} 영역이 초기화됩니다. 계속할까요?`, async () => {
      const currentStore = getCurrentFormStore()
      if (currentStore?.resetStep) {
        await currentStore.resetStep(currentStepIndex.value + 1)
      } else if (currentStore?.resetAll) {
        await currentStore.resetAll()
      }

      // 현재 스텝의 완료 여부를 false로 리셋
      isCurrentStepComp.value[currentStepIndex.value] = false

      if (stepRefs.value[currentStepIndex.value]?.resetStep) {
        await stepRefs.value[currentStepIndex.value].resetStep()
      }
    })
    return
  }

  if (route.params?.domain === 'ownerchange') {
    const msg = ['고객', '상품', '동의'][currentStepIndex.value] || '동의'
    showConfirm(`${msg} 영역을 초기화 하시겠습니까?`, async () => {
      if (stepRefs.value[currentStepIndex.value]?.reset) {
        await stepRefs.value[currentStepIndex.value].reset()
      } else if (stepRefs.value[0]?.reset) {
        await stepRefs.value[0].reset()
      }
      // initAllSteps()
    })
    return
  }

  if (route.params?.domain === 'termination') {
    const target = ['고객', '상품', '동의'][currentStepIndex.value] || '동의'
    showConfirm(`${target} 영역이 초기화됩니다. 계속할까요?`, async () => {
      if (stepRefs.value[currentStepIndex.value]?.reset) {
        await stepRefs.value[currentStepIndex.value].reset()
      } else {
        getCurrentFormStore()?.resetStep?.(currentStepIndex.value)
      }
      isCurrentStepComp.value[currentStepIndex.value] = false

      if (currentStepIndex.value > 0) {
        const resetStepIndex = currentStepIndex.value
        const previousStepIndex = Math.max(currentStepIndex.value - 1, 0)
        stepStore.setActiveIndex(previousStepIndex)
        currentStepIndex.value = previousStepIndex
        if (resetStepIndex === 1) {
          isCurrentStepComp.value[previousStepIndex] = false
        }
      }
    })
  }
}

const handleNextBtnClick = async (event) => {
  if (stepRefs.value[currentStepIndex.value]?.checkValidation) {
    if (!stepRefs.value[currentStepIndex.value]?.checkValidation()) {
      return false
    }
  }
  if (route.params?.domain === 'newchange') {
    if (!isStepComplete.value) {
      event.stopPropagation()
      if (stepRefs.value[currentStepIndex.value]?.validateWithAlert) {
        await stepRefs.value[currentStepIndex.value].validateWithAlert()
      }
    } else {
      await onClickNextBtn()
    }
  } else {
    await onClickNextBtn()
  }
}

const handleCompleteBtnClick = async (event) => {
  // 서비스해지/변경은 미완료로 인한 비활성화 상태에서도 유효성 알림을 노출한다.
  const allowsIncompleteValidation =
    ['termination', 'servicechange'].includes(route.params?.domain || '') && !isStepComplete.value
  if (isCompleteButtonDisabled.value && !allowsIncompleteValidation) {
    event.stopPropagation()
    return
  }

  if (stepRefs.value[currentStepIndex.value]?.checkValidation) {
    if (!stepRefs.value[currentStepIndex.value]?.checkValidation()) {
      return false
    }
  }

  if (route.params?.domain === 'newchange') {
    if (!isStepComplete.value) {
      event.stopPropagation()
      if (stepRefs.value[currentStepIndex.value]?.validateWithAlert) {
        await stepRefs.value[currentStepIndex.value].validateWithAlert()
      }
    } else {
      await onClickCompelteBtn()
    }
  } else if (route.params?.domain === 'ownerchange') {
    if (await stepRefs.value[currentStepIndex.value].validateWithAlert()) {
      await onClickCompelteBtn()
    }
  } else if (route.params?.domain === 'termination') {
    if (!isStepComplete.value) {
      event.stopPropagation()
      await stepRefs.value[currentStepIndex.value]?.validateWithAlert?.()
    } else {
      await onClickCompelteBtn()
    }
  } else if (route.params?.domain === 'servicechange') {
    if (!isStepComplete.value) {
      event.stopPropagation()
      await stepRefs.value[currentStepIndex.value]?.validateWithAlert?.()
    } else {
      const valid = await stepRefs.value[currentStepIndex.value]?.validateWithAlert?.()
      if (valid) {
        await onClickCompelteBtn()
      }
    }
  } else {
    await onClickCompelteBtn()
  }
}

const onClickNextBtn = async () => {
  // console.log(`[MsfFormView] Attempting to move to next step from index: ${currentStepIndex.value}`)

  if (stepRefs.value[currentStepIndex.value]?.validateWithAlert) {
    const valid = await stepRefs.value[currentStepIndex.value].validateWithAlert()
    if (!valid) {
      return
    }
  }

  const result = await stepRefs.value[currentStepIndex.value]?.save()
  // console.log(`[MsfFormView] Step save result:`, result)

  if (!result) {
    // console.warn(`[MsfFormView] Step save failed or returned false. Aborting step change.`)
    return
  }

  // 다음 스텝으로 넘어가고, 새로운 스텝은 아직 검증 전이므로 버튼을 다시 비활성화합니다.
  currentStepIndex.value++
  // console.log(`[MsfFormView] Incremented currentStepIndex to: ${currentStepIndex.value}`)
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

    completeRequest()
  })
}

// const tempRequestKey = ref('')
// const onClickTempCompleteBtn = () => {
//   if (!tempRequestKey.value) {
//     showAlert('임시신청번호를 입력해주세요.')
//     return
//   }
//   if (formType.value === '1') {
//     completeData.value = {
//       requestKey: tempRequestKey.value,
//     }
//     isComplete.value = true
//   } else if (formType.value === '2') {
//     completeData.value = {
//       requestKey: tempRequestKey.value,
//     }
//     isComplete.value = true
//   } else if (formType.value === '3') {
//     completeData.value = {
//       requestKey: tempRequestKey.value,
//     }
//     isComplete.value = true
//   } else if (formType.value === '4') {
//     completeData.value = {
//       requestKey: tempRequestKey.value,
//     }
//     isComplete.value = true
//   }
// }

onBeforeMount(async () => {
  formType.value = getFormTypeCode(route.path)
  isWorkNoticeLoading.value = true
  try {
    if (!stepStore.isWorkNotice()) {
      await stepStore.loadWorkNotice(route.path)
    }
    if (stepStore.isWorkNotice()) {
      return
    }
    await prepareFormView()
  } finally {
    isWorkNoticeLoading.value = false
  }
})

watch(
  () => route.params?.domain,
  async (newDomain, oldDomain) => {
    if (!newDomain || newDomain === oldDomain) {
      return
    }

    formType.value = getFormTypeCode(route.path)
    resetCurrentStore(oldDomain) // 이전 도메인 스토어 리셋
    resetViewState() // 뷰 로컬 상태 리셋
    stepStore.clearWorkNotice()
    isWorkNoticeLoading.value = true

    isRouteChange.value = true // 페이지 이동 중임을 표시
    domain.value = newDomain

    try {
      await stepStore.loadWorkNotice(route.path)
      if (stepStore.isWorkNotice()) {
        return
      }
      await prepareFormView(newDomain)
    } finally {
      isWorkNoticeLoading.value = false
    }
  },
  { flush: 'post' },
)
onUnmounted(() => {
  stepStore.clearWorkNotice()
  stepStore.clearParentScanId()
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

watch(currentStepIndex, async () => {
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
  --step-content-layout-x: #{rem(24px)};
  display: block;
  @include flex();
  height: 100%;
  min-height: 0;
  overflow: hidden;
}
.step-content-wrap {
  padding-block: rem(32px) rem(40px);
  padding-inline: var(--step-content-layout-x);
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
.step-btn-wrap-local {
  display: inline-block;
  position: relative;
  :deep(.step-btn-local:disabled) {
    pointer-events: none !important;
  }
}
</style>
