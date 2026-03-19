<script setup>
import { ref, onUnmounted, watch, useId } from 'vue'

const props = defineProps({
  isOpen: Boolean,
  title: String,
  divider: { type: Boolean, default: false },
  className: String,
  bodyClassName: String,
  maximize: Boolean,
  mode: String, // 'gray' | 'black' | 'dimmed'
  showClose: Boolean,
  flush: Boolean,
  portalTarget: { type: String, default: 'body' },
  size: {
    type: String,
    default: 'medium',
    validator: (v) => ['large', 'medium', 'small'].includes(v),
  },
})

// 이벤트 등록
const emit = defineEmits(['close', 'open'])

const titleId = `modal-label--${useId()}`
const vh = ref(0)

// 1. 뷰포트 높이(vh) 계산 로직 (컴포넌트 내부로 흡수)
const updateVh = () => {
  vh.value = window.innerHeight * 0.01
  document.documentElement.style.setProperty('--layout-vh', `${vh.value}px`)
}

// 2. 스크롤 잠금 및 이벤트 관리
watch(
  () => props.isOpen,
  (newVal) => {
    if (newVal) {
      updateVh()
      window.addEventListener('resize', updateVh)
      document.documentElement.classList.add('no-scroll')
      emit('open') // 모달이 열릴 때 부모에게 알림
    } else {
      window.removeEventListener('resize', updateVh)
      document.documentElement.classList.remove('no-scroll')
      emit('close') // 모달이 닫히는 시점에 부모에게 알림
    }
  },
  { immediate: true },
)

onUnmounted(() => {
  window.removeEventListener('resize', updateVh)
  // 컴포넌트가 파괴될 때 혹시 남아있을지 모를 클래스 확실히 제거
  document.documentElement.classList.remove('no-scroll')
})
</script>

<template>
  <Teleport :to="props.portalTarget">
    <Transition name="fade">
      <FocusTrap :isActive="props.isOpen">
        <div v-if="props.isOpen" class="overlay">
          <div
            :class="[
              'root',
              props.maximize ? 'maximize' : '',
              props.mode ? `mode-${props.mode}` : '',
              `dialog-size-${props.size}`,
              props.className,
            ]"
            role="dialog"
            aria-modal="true"
            :aria-labelledby="props.title ? titleId : undefined"
          >
            <div class="header-wrap">
              <div v-if="props.title" class="header">
                <h2 :id="titleId" class="title">{{ props.title }}</h2>
              </div>
              <MsfButton variant="ghost" iconOnly="close" @click="emit('close')">닫기</MsfButton>
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
              <slot></slot>
            </div>
            <div v-if="$slots.footer" class="footer">
              <slot name="footer"></slot>
            </div>
          </div>
        </div>
      </FocusTrap>
    </Transition>
  </Teleport>
</template>

<style lang="scss" scoped>
.overlay {
  @include position($p: fixed, $t: 0, $l: 0, $i: 1000);
  width: 100%;
  /* 훅 없이도 내부 vh 변수로 계산 가능 */
  height: calc(var(--layout-vh, 1vh) * 100);
  @include flex($h: center, $v: center);
  background-color: var(--color-alpha-dim);
}

.root {
  --dialog-max-width: #{rem(1024px)};
  --dialog-border-radius: #{rem(16px)};
  --dialog-inner-padding: #{rem(24px)};

  width: calc(100% - var(--layout-padding-x) * 2);
  max-width: var(--dialog-max-width);
  max-height: 80%;
  position: relative;
  @include flex($d: column);
  border-radius: var(--dialog-border-radius);
  background-color: var(--color-background);
  padding-block: var(--dialog-inner-padding);
  padding-inline: var(--dialog-inner-padding);
  &:has(.footer) {
    padding-bottom: 0;
  }

  &.dialog-size-large {
    --dialog-max-width: #{rem(1200px)};
  }
  &.dialog-size-medium {
    --dialog-max-width: #{rem(1024px)};
  }
  &.dialog-size-small {
    --dialog-max-width: #{rem(800px)};
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
  @include flex($v: center, $h: space-between);
  padding-bottom: rem(16px);
  border-bottom: rem(2px) solid var(--color-foreground);
  .header {
    padding-block: 0;
    padding-right: rem(32px);
    .title {
      @include ellipsis(1);
      font-size: var(--font-size-24);
    }
  }
}
.nav-bar {
  border-bottom: var(--border-width-base) solid var(--color-gray-100);
  // nav-bar안에 있는 버튼의 최소 너비 고정
  :deep(.btn-root) {
    min-width: rem(88px);
  }
}
.body {
  flex: 1;
  // @include scrollbar;
  overflow-y: auto;
  padding-block: var(--dialog-inner-padding);
  // padding-bottom: var(--layout-gutter-y);
  // margin-inline: calc(var(--dialog-inner-padding) * -1);
  // padding-inline: var(--dialog-inner-padding);

  &.flush {
    padding: 0;
  }
  &.divider {
    border-top: 1px solid var(--color-border);
  }
}
.nav-bar {
  padding-block: var(--spacing-x4);
}
.footer {
  display: flex;
  margin-top: 0;
  margin-inline: calc(var(--dialog-inner-padding) * -1);
  padding: var(--dialog-inner-padding) var(--dialog-inner-padding);
  // border-top: var(--border-width-base) solid var(--color-gray-150);
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
</style>
