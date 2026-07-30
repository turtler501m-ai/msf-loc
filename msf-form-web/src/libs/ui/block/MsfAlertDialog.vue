<script setup>
import { ref, onMounted, onUnmounted, nextTick, computed } from 'vue'
import { useScrollLock } from '@/hooks/useScrollLock'

const props = defineProps({
  id: [String, Number],
  title: String,
  message: [String, Object],
  onConfirm: Function,
  onCancel: Function,
  showCancel: {
    type: Boolean,
    default: true,
  },
  labelProps: {
    type: Object,
    default: () => ({ confirm: '확인', cancel: '취소' }),
  },
  isLast: Boolean, // 현재 이 알럿이 가장 위에 있는지 여부
})

// alertdialog와 제목/본문을 연결하기 위한 기준 ID
const alertId = computed(() => props.id ?? 'msf-alert')
// aria-labelledby로 연결할 제목 요소 ID
const titleId = computed(() => `${alertId.value}-title`)
// aria-describedby로 연결할 메시지 요소 ID
const messageId = computed(() => `${alertId.value}-message`)

const { lock, unlock } = useScrollLock()
const isOpen = ref(false)
// 콜백 포커스가 다이얼로그 밖으로 이동했는지 확인할 루트
const dialogRootRef = ref(null)

/** 부모 요소 포커스 책갈피 */
const returnElement = ref(null)
// 닫힘 콜백에서 지정한 포커스 대상
const callbackFocusElement = ref(null)

/** 상태 변화 순서 제어 */
const handleClose = async (callback) => {
  callbackFocusElement.value = null
  isOpen.value = false // 먼저 닫아서 FocusTrap이 인지하게 함

  // DOM 업데이트와 애니메이션 시작을 위한 미세한 지연
  await nextTick()
  setTimeout(() => {
    unlock() // 콜백 에러 여부와 관계없이 스크롤 잠금은 먼저 해제

    try {
      if (callback) callback()
    } catch (error) {
      console.error(error)
    }

    // 콜백 실행 후 현재 포커스된 요소
    const activeElement = document.activeElement
    // 콜백이 다이얼로그 밖 요소로 포커스를 옮겼는지 확인
    const isFocusedOutsideDialog =
      activeElement &&
      activeElement !== document.body &&
      !dialogRootRef.value?.contains(activeElement)

    // 복원 대상이 아닌 유효한 외부 포커스만 저장
    if (
      isFocusedOutsideDialog &&
      activeElement !== returnElement.value &&
      document.contains(activeElement)
    ) {
      // 기본 복원 이후 다시 적용할 콜백 포커스 대상 저장
      callbackFocusElement.value = activeElement
    }
  }, 100)
}

const handleConfirm = () => handleClose(props.onConfirm)
const handleCancel = () => handleClose(props.onCancel)

// 확인 버튼만 있는 알럿에서 Enter로 확인 처리
const handleDialogEnter = (event) => {
  const hasCancelButton = props.showCancel && typeof props.onCancel === 'function'

  // 취소 버튼이 있는 선택형 알럿에서는 Enter 확인을 처리하지 않음
  if (hasCancelButton) return

  event.preventDefault()
  handleConfirm()
}

// isOpen 상태 감시 (열릴 때만 실행)
onMounted(async () => {
  // nextTick을 기다려서 브라우저가 포커스 정리를 마친 후의 요소를 저장
  await nextTick()
  // 열리기 직전의 포커스 요소(부모 버튼)를 저장
  returnElement.value = document.activeElement

  isOpen.value = true
  lock()
})

onUnmounted(() => {
  unlock()

  // 컴포넌트가 파괴될 때 저장해둔 부모 요소로 포커스 강제 복구
  nextTick(() => {
    if (returnElement.value && document.contains(returnElement.value)) {
      returnElement.value.focus()
    }

    // 콜백에서 지정한 포커스 대상
    const focusElement = callbackFocusElement.value
    if (focusElement && document.contains(focusElement)) {
      // 기본 포커스 복원 후 콜백 포커스를 적용
      setTimeout(() => {
        focusElement.focus()
      }, 0)
    }
  })
})
</script>

<template>
  <Teleport to="body">
    <Transition name="fade" appear>
      <div v-if="isOpen" class="alert-overlay" :id="`${props.id}-overlay`">
        <FocusTrap :isActive="isOpen && props.isLast" :autoFocus="true" :restoreFocus="false">
          <div
            ref="dialogRootRef"
            class="alert-root"
            role="alertdialog"
            aria-modal="true"
            tabindex="0"
            @keydown.enter.self="handleDialogEnter"
            :aria-labelledby="props.title ? titleId : undefined"
            :aria-label="props.title ? undefined : '알림'"
            :aria-describedby="
              typeof props.message === 'string' && props.message ? messageId : undefined
            "
          >
            <div class="alert-message">
              <h2 v-if="props.title" :id="titleId" class="msg-title" v-html="props.title"></h2>
              <template v-if="typeof props.message === 'string'">
                <div :id="messageId" class="msg-content" v-html="props.message"></div>
              </template>
              <component v-else :is="props.message" />
            </div>
            <div class="alert-footer">
              <MsfButtonGroup align="center">
                <MsfButton
                  variant="secondary"
                  v-if="props.onCancel && props.showCancel"
                  @click="props.onCancel"
                >
                  {{ props.labelProps?.cancel || '취소' }}
                </MsfButton>
                <MsfButton variant="primary" @click="handleConfirm">
                  {{ props.labelProps?.confirm || '확인' }}
                </MsfButton>
              </MsfButtonGroup>
            </div>
          </div>
        </FocusTrap>
      </div>
    </Transition>
  </Teleport>
</template>

<style lang="scss" scoped>
// 타이틀, 컨텐츠 텍스트설정 믹스인
@mixin text-wrap-safe {
  /* \n 또는 소스상의 줄바꿈을 실제 줄바꿈으로 렌더링 */
  white-space: pre-line;
  /* 긴 단어도 영역 안에서 줄바꿈되도록 설정 */
  word-break: keep-all;
  overflow-wrap: break-word;
  overflow-wrap: anywhere;
  min-width: 0; /* 레이아웃 깨짐 방지 */
}
/* Alert Dialog */
.alert-overlay {
  width: 100%;
  height: var(--msf-app-height, 100vh);
  @include position($p: fixed, $l: 0, $b: 0, $i: 1001);
  @include flex($h: center, $v: center);
  background-color: var(--color-alpha-dim);

  &:focus-visible {
    outline: none;
  }
}
.alert-message {
  @include flex($h: space-around, $d: column, $v: center) {
    gap: var(--spacing-x4);
  }
  width: 100%;
  min-height: rem(90px);
  padding-block: var(--spacing-x6);
  overflow-y: auto;
  .msg-title {
    @include text-wrap-safe;
    margin: 0;
    font-size: var(--font-size-20);
    font-weight: var(--font-weight-bold);
    line-height: var(--line-height-base);
  }
  .msg-content {
    @include text-wrap-safe;
    font-size: var(--font-size-16);
    line-height: var(--line-height-heading);
    color: var(--color-gray-600);
  }
}
.alert-root {
  width: calc(100% - var(--layout-padding-x) * 2);
  max-width: rem(380px);
  max-height: 50%;
  position: relative;
  padding: var(--spacing-x6);
  @include flex($d: column);
  text-align: center;
  border-radius: var(--border-radius-l);
  background-color: var(--color-background);
  overflow-y: auto;
  &:focus,
  &:focus-visible {
    outline: none;
  }
}

.alert-footer {
  margin-top: var(--spacing-x4);
  .btn-root {
    flex: 1;
  }
}

/* 페이드 애니메이션 (Vue Transition) */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.1s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
