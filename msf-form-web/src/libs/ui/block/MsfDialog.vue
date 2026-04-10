<template>
  <Teleport :to="props.portalTarget">
    <Transition name="fade">
      <div v-if="props.keepAlive || props.isOpen" v-show="props.isOpen" class="overlay">
        <FocusTrap :isActive="props.isOpen">
          <div
            ref="containerRef"
            :class="[
              'dialog-root',
              props.maximize ? 'maximize' : '',
              props.mode ? `mode-${props.mode}` : '',
              `dialog-size-${props.size}`,
              props.className,
            ]"
            role="dialog"
            aria-modal="true"
            :aria-labelledby="props.title ? titleId : undefined"
          >
            <div v-if="!props.showClose" ref="initialFocusTarget" tabindex="0" class="blind"></div>
            <div class="header-wrap">
              <div class="header-inner">
                <div v-if="props.title" class="header">
                  <h2 :id="titleId" class="title">{{ props.title }}</h2>
                </div>
                <MsfButton
                  variant="ghost"
                  iconOnly="close"
                  @click="emit('close')"
                  class="close-btn"
                >
                  닫기
                </MsfButton>
              </div>
            </div>
            <div v-if="$slots.navBar" class="nav-bar">
              <slot name="navBar"></slot>
            </div>
            <div
              :class="[
                'body',
                props.divider ? 'divider' : '',
                props.flush ? 'flush' : '',
                props.bodyClassName,
              ]"
            >
              <MsfCustomScroll
                :ref="
                  (el) => {
                    modalScrollRef = el
                  }
                "
                height="100%"
              >
                <div class="body-inner">
                  <slot></slot>
                </div>
              </MsfCustomScroll>
            </div>
            <div v-if="$slots.footer" class="footer">
              <slot name="footer"></slot>
            </div>
          </div>
        </FocusTrap>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import { ref, onUnmounted, watch, useId, nextTick } from 'vue'
import { useScrollLock } from '@/hooks/useScrollLock'
import { modalScrollRef } from '@/hooks/useGlobalScroll'

const props = defineProps({
  isOpen: Boolean,
  keepAlive: { type: Boolean, default: false }, // true일 때 iframe 등 내부 상태 유지를 위해 v-show로 제어 (기본 false)
  title: String,
  divider: { type: Boolean, default: false },
  className: String,
  bodyClassName: String,
  maximize: Boolean,
  mode: String,
  showClose: Boolean,
  flush: Boolean,
  portalTarget: { type: String, default: 'body' },
  size: {
    type: String,
    default: 'large',
    validator: (v) => ['large', 'medium', 'small'].includes(v),
  },
  customScroll: { type: Boolean, default: false }, // CustomScroll 사용 여부 결정 (기본값 false)
})

const containerRef = ref(null) // 모달 루트 컨테이너
const initialFocusTarget = ref(null) // 초기 포커스용 가상 요소
const lastFocusedElement = ref(null) // 닫기 후 복원할 이전 포커스 요소

// 이벤트 등록
const emit = defineEmits(['close', 'open'])

const titleId = `modal-label--${useId()}`

const { lock, unlock } = useScrollLock()
const vh = ref(0)

// 1. 뷰포트 높이(vh) 계산 로직 (컴포넌트 내부로 흡수)
const updateVh = () => {
  vh.value = window.innerHeight * 0.01
  document.documentElement.style.setProperty('--layout-vh', `${vh.value}px`)
}

// 2. 스크롤 잠금 및 이벤트 관리
watch(
  () => props.isOpen,
  async (newVal, oldVal) => {
    if (newVal) {
      // 열릴 때: 현재 포커스된 요소를 저장
      lastFocusedElement.value = document.activeElement
      lock() // 3. 잠금 요청 (카운트 증가)
      updateVh()
      window.addEventListener('resize', updateVh)
      // DOM이 실제로 화면에 그려질 때까지 기다린 후 포커싱
      await nextTick()
      // 포커스 우선순위 결정
      const closeBtn = containerRef.value?.querySelector('.btn-close')
      const target = props.showClose ? closeBtn : initialFocusTarget.value

      if (target) {
        target.focus()
      }

      emit('open') // 모달이 열릴 때 부모에게 알림
    } else if (oldVal) {
      // 이전에 열려있다가 닫히는 경우만 실행
      unlock() // 4. 해제 요청 (카운트 차감)
      window.removeEventListener('resize', updateVh)

      // 닫힐 때: nextTick으로 DOM 업데이트 후 저장해둔 요소로 포커스 강제 이동
      await nextTick()
      if (lastFocusedElement.value) {
        lastFocusedElement.value.focus()
      }

      emit('close') // 모달이 닫히는 시점에 부모에게 알림
    }
  },
  { immediate: true },
)

onUnmounted(() => {
  // 컴포넌트가 파괴될 때 아직 열려있는 상태라면 카운트를 안전하게 차감
  if (props.isOpen) {
    unlock()
  }
  window.removeEventListener('resize', updateVh)
})
</script>

<style lang="scss" scoped>
.overlay {
  @include position($p: fixed, $t: 0, $l: 0, $i: 1000);
  width: 100%;
  height: calc(var(--layout-vh, 1vh) * 100);
  @include flex($h: center, $v: center);
  background-color: var(--color-alpha-dim);
}

.dialog-root {
  --dialog-max-width: #{rem(1024px)};
  --dialog-border-radius: #{rem(16px)};
  --dialog-inner-padding: #{rem(24px)};
  &:has(.footer) {
    .body {
      padding-bottom: 0;
    }
  }

  width: calc(100% - var(--layout-padding-x) * 2);
  max-width: var(--dialog-max-width);
  height: rem(660px); // 높이지정 디자인사이즈
  position: relative;
  @include flex($d: column);
  border-radius: var(--dialog-border-radius);
  background-color: var(--color-background);
  padding-block: var(--dialog-inner-padding);
  // padding-inline: var(--dialog-inner-padding);
  &:has(.footer) {
    padding-bottom: 0;
  }

  // 사이즈별 크기 지정
  &.dialog-size-large {
    --dialog-max-width: #{rem(752px)};
    // @include mobile() {
    //   --dialog-max-width: 95%;
    // }
  }
  &.dialog-size-medium {
    --dialog-max-width: #{rem(460px)};
    max-height: rem(540px); // 높이지정 디자인사이즈
    // @include mobile() {
    //   --dialog-max-width: 95%;
    // }
  }
  &.dialog-size-small {
    --dialog-max-width: #{rem(400px)};
    // @include mobile() {
    //   --dialog-max-width: 95%;
    // }
  }

  &.maximize {
    --dialog-max-width: auto;
    width: 100%;
    height: 100%;
    max-height: none;
    border-radius: unset;
  }
}
.header-wrap {
  padding-inline: var(--dialog-inner-padding);
  margin-bottom: var(--dialog-inner-padding);
  .header-inner {
    @include flex($v: center, $h: space-between) {
      gap: rem(16px);
    }
    padding-bottom: rem(12px);
    border-bottom: rem(2px) solid var(--color-foreground);
  }
  .header {
    padding-block: 0;
    .title {
      @include ellipsis(1);
      font-size: var(--font-size-24);
      font-weight: var(--font-weight-bold);
    }
  }
}
.nav-bar {
  padding-inline: var(--dialog-inner-padding);
  padding-block: 0 var(--spacing-x4);
  margin-top: rem(-4px);
  border-bottom: var(--border-width-base) solid var(--color-gray-100);
  // nav-bar안에 있는 버튼의 최소 너비 고정
  :deep(.btn-root) {
    min-width: rem(88px);
  }
  // nav-bar가 잇으면 body에 상단여백
  & + .body {
    .body-inner {
      padding-top: var(--dialog-inner-padding);
    }
  }
}
.body {
  flex: 1;
  // @include scrollbar;
  overflow-y: auto;
  // padding-block: var(--dialog-inner-padding);
  // padding-bottom: var(--layout-gutter-y);
  // margin-inline: calc(var(--dialog-inner-padding) * -1);
  // padding-inline: var(--dialog-inner-padding);

  .body-inner {
    padding: var(--dialog-inner-padding);
    padding-top: 0;
    padding-bottom: 0;
  }
  &.flush {
    padding: 0;
  }
  &.divider {
    border-top: 1px solid var(--color-border);
  }
}

.footer {
  display: flex;
  margin-top: 0;
  padding: var(--dialog-inner-padding) var(--dialog-inner-padding);
  // border-top: var(--border-width-base) solid var(--color-gray-150);
  :deep(.button-group-root) {
    justify-content: center !important;
  }
}

/* 페이드 애니메이션 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
.close-btn {
  height: auto;
  padding: 0;
}
</style>
