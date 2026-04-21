<script setup>
import { ref, onMounted, onUnmounted, nextTick } from 'vue'
import { useScrollLock } from '@/hooks/useScrollLock'

const props = defineProps({
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
})

const { lock, unlock } = useScrollLock()
const isOpen = ref(false)

/** 부모 요소 포커스 책갈피 */
const returnElement = ref(null)

// 1. 뷰포트 높이(vh) 계산 및 스크롤 잠금 로직
const updateVh = () => {
  const vh = window.innerHeight * 0.01
  document.documentElement.style.setProperty('--layout-vh', `${vh}px`)
}

/** 상태 변화 순서 제어 */
const handleClose = async (callback) => {
  isOpen.value = false // 먼저 닫아서 FocusTrap이 인지하게 함

  // DOM 업데이트와 애니메이션 시작을 위한 미세한 지연
  await nextTick()
  setTimeout(() => {
    if (callback) callback()
  }, 10)
}

const handleConfirm = () => handleClose(props.onConfirm)
const handleCancel = () => handleClose(props.onCancel)

// 2. isOpen 상태 감시 (열릴 때만 실행)
onMounted(() => {
  // 열리기 직전의 포커스 요소(부모 버튼)를 저장
  returnElement.value = document.activeElement

  isOpen.value = true
  updateVh()
  window.addEventListener('resize', updateVh)
  lock()
})

onUnmounted(() => {
  window.removeEventListener('resize', updateVh)
  unlock()

  // 컴포넌트가 파괴될 때 저장해둔 부모 요소로 포커스 강제 복구
  if (returnElement.value && document.contains(returnElement.value)) {
    nextTick(() => {
      returnElement.value.focus()
    })
  }
})
</script>

<template>
  <Teleport to="body">
    <Transition name="fade">
      <div v-if="isOpen" class="alert-overlay">
        <FocusTrap :isActive="isOpen" :autoFocus="true" :restoreFocus="false">
          <div class="alert-root" role="alertdialog" aria-modal="true">
            <div class="alert-message">
              <h2 v-if="props.title" class="msg-title" v-html="props.title"></h2>
              <template v-if="typeof props.message === 'string'">
                <div class="msg-content" v-html="props.message"></div>
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
/* Alert Dialog */
.alert-overlay {
  width: 100%;
  height: calc(var(--layout-vh, 1vh) * 100);
  @include position($p: fixed, $l: 0, $b: 0, $i: 1000);
  @include flex($h: center, $v: center);
  background-color: var(--color-alpha-dim);

  &:focus-visible {
    outline: none;
  }
}
.alert-message {
  @include flex($h: center, $d: column, $v: center) {
    gap: var(--spacing-x4);
  }
  width: 100%;
  min-height: rem(90px);
  padding-block: var(--spacing-x6);
  .msg-title {
    /* \n 또는 소스상의 줄바꿈을 실제 줄바꿈으로 렌더링 */
    white-space: pre-line;
    word-break: break-all; /* 긴 단어도 영역 안에서 줄바꿈되도록 설정 */
    margin: 0;
    font-size: var(--font-size-20);
    font-weight: var(--font-weight-bold);
    line-height: var(--line-height-base);
  }
  .msg-content {
    /* \n 또는 소스상의 줄바꿈을 실제 줄바꿈으로 렌더링 */
    white-space: pre-line;
    word-break: break-all; /* 긴 단어도 영역 안에서 줄바꿈되도록 설정 */
    font-size: var(--font-size-16);
    line-height: var(--line-height-heading);
    color: var(--color-gray-600);
  }
}
.alert-root {
  width: calc(100% - var(--layout-padding-x) * 2);
  max-width: rem(380px);
  max-height: 80%;
  position: relative;
  padding: var(--spacing-x6);
  @include flex($d: column);
  text-align: center;
  border-radius: var(--border-radius-l);
  background-color: var(--color-background);
  overflow-y: auto;
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
  transition: opacity 0.2s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
