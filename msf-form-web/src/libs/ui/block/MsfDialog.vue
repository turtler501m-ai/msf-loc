<script>
// 전역 변수: 현재 열려있는 모든 모달의 z-index 목록 관리
let UI_MODAL_Z_INDEX_STACK = []
</script>
<template>
  <Teleport :to="props.portalTarget">
    <Transition name="fade">
      <div
        v-if="props.keepAlive || props.isOpen"
        v-show="props.isOpen"
        class="overlay"
        :class="{ 'is-web': isWeb }"
        :style="{ zIndex: currentZIndex }"
      >
        <FocusTrap :isActive="props.isOpen">
          <div
            ref="containerRef"
            :class="[
              'dialog-root',
              props.maximize ? 'maximize' : '',
              props.mode ? `mode-${props.mode}` : '',
              `dialog-size-${props.size}`,
              props.autoHeight ? `is-autoHeight` : '',
              props.dividerFooter ? 'divider-footer' : '',
              props.className,
            ]"
            role="dialog"
            aria-modal="true"
            :aria-labelledby="props.title ? titleId : undefined"
          >
            <div v-if="!props.showClose" ref="initialFocusTarget" tabindex="0" class="blind"></div>
            <div
              class="header-wrap"
              :class="{
                'empty-title': !props.title,
              }"
            >
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
                <MsfFocusScope class="ut-h100p" :disabled="isWeb || !props.useFocusScope">
                  <div class="body-inner">
                    <slot></slot>
                  </div>
                </MsfFocusScope>
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
  dividerFooter: { type: Boolean, default: false }, // footer 위에 보더 설정 시
  className: String,
  bodyClassName: String,
  maximize: Boolean,
  mode: String,
  showClose: Boolean,
  flush: Boolean,
  portalTarget: { type: String, default: '#portal-root' },
  size: {
    type: String,
    default: 'large',
    validator: (v) => ['xlarge', 'large', 'medium', 'small'].includes(v),
  },
  customScroll: { type: Boolean, default: false }, // CustomScroll 사용 여부 결정 (기본값 false)
  autoHeight: { type: Boolean, default: false }, // 컨텐츠의 높이만큼 높이 설정 여부
  useFocusScope: { type: Boolean, default: true }, // MsfFocusScope 사용여부 (기본 true) - false하면 키보드 Enter 포커스 자동이동을 OFF
})

const containerRef = ref(null) // 모달 루트 컨테이너
const initialFocusTarget = ref(null) // 초기 포커스용 가상 요소
const lastFocusedElement = ref(null) // 닫기 후 복원할 이전 포커스 요소

// 이벤트 등록
const emit = defineEmits(['close', 'open'])

const titleId = `modal-label--${useId()}`

// 웹/앱 높이 스타일 분리를 위한 구분값
const isWeb = localStorage.getItem('deviceType') === 'P'

// z-index 관련
const BASE_Z_INDEX = 10 // 현재 인스턴스의 z-index
const currentZIndex = ref(BASE_Z_INDEX) //초기값에 사용
document.documentElement.style.setProperty('--msf-dialog-zindex-base', BASE_Z_INDEX) // CSS 변수설정

// z-index 할당: 현재 모달 중 가장 높은 번호에 +1 하여 등록
const assignZIndex = () => {
  const maxZ =
    UI_MODAL_Z_INDEX_STACK.length > 0 ? Math.max(...UI_MODAL_Z_INDEX_STACK) : BASE_Z_INDEX
  currentZIndex.value = maxZ + 1
  UI_MODAL_Z_INDEX_STACK.push(currentZIndex.value)
}
// z-index 해제: 전역 명단에서 현재 모달의 번호를 삭제
const releaseZIndex = () => {
  UI_MODAL_Z_INDEX_STACK = UI_MODAL_Z_INDEX_STACK.filter((z) => z !== currentZIndex.value)
}

const { lock, unlock } = useScrollLock()

// 스크롤 잠금 및 이벤트 관리
watch(
  () => props.isOpen,
  async (newVal, oldVal) => {
    if (newVal) {
      assignZIndex() // 열릴 때 현재 최댓값 + 1 부여

      // 열릴 때: 현재 포커스된 요소를 저장
      lastFocusedElement.value = document.activeElement
      lock() // 3. 잠금 요청 (카운트 증가)
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
      releaseZIndex() // 닫힐 때 내 번호 반납

      // 이전에 열려있다가 닫히는 경우만 실행
      unlock() // 4. 해제 요청 (카운트 차감)

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
})
</script>

<style lang="scss" scoped>
.overlay {
  @include position($p: fixed, $t: 0, $l: 0, $i: var(--msf-dialog-zindex-base, 10));
  width: 100%;
  height: var(--msf-app-height, 100vh);
  // 웹 viewport 높이 고정
  &.is-web {
    height: 100%;
  }
  @include flex($h: center, $v: center);
  background-color: var(--color-alpha-dim);
}

.dialog-root {
  --dialog-max-width: #{rem(1024px)};
  --dialog-max-height: #{rem(660px)}; // 높이지정 디자인사이즈
  --dialog-border-radius: #{rem(16px)};
  --dialog-inner-padding: #{rem(24px)};
  --dialog-inner-padding-narrow: #{rem(16px)}; // dialog 내부 여백값중 부분적으로 좁게 지정할 경우 사용
  &:has(.footer) {
    .body {
      padding-bottom: 0;
    }
  }
  // 기본 사이즈 100%에서 상하좌우 여백만큼 뺀다.
  width: calc(100% - var(--layout-padding-x) * 2);
  height: calc(100% - var(--layout-padding-y) * 2);
  // 사이즈 최대값 지정은 max-값 변수값을 변경한다
  max-width: var(--dialog-max-width);
  max-height: var(--dialog-max-height);
  position: relative;
  @include flex($d: column);
  border-radius: var(--dialog-border-radius);
  background-color: var(--color-background);
  padding-block: var(--dialog-inner-padding);
  // padding-inline: var(--dialog-inner-padding);
  &:has(.footer) {
    padding-bottom: 0;
  }
  // props : dividerFooter 설정시 보더스타일
  &.divider-footer {
    .footer {
      border-top: 1px solid var(--color-gray-150);
    }
    // 하단에 보더있는경우 eformsign-container 하단여백 설정
    :deep(.eformsign-container) {
      padding-bottom: rem(20px);
    }
  }

  // 사이즈별 크기 지정
  /* xlarge : 신청서열람, 신청서확인 처럼 PC, tablet 영역을 크게 활용하는 팝업의 경우 사용합니다.*/
  &.dialog-size-xlarge {
    --dialog-max-width: #{rem(1400px)};
    --dialog-max-height: 100%;
    --layout-padding-y: #{rem(8px)};
    --layout-padding-x: #{rem(8px)};

    .header-wrap {
      margin-bottom: var(--dialog-inner-padding-narrow);
    }
    .footer {
      padding-top: var(--dialog-inner-padding-narrow);
    }
  }
  /* large */
  &.dialog-size-large {
    // --dialog-max-width: #{rem(752px)}; // 기존 디자인사이즈__20260610_변경
    --dialog-max-width: #{rem(860px)};
  }
  /* medium */
  &.dialog-size-medium {
    --dialog-max-width: #{rem(460px)};
    --dialog-max-height: #{rem(540px)}; // 높이지정 디자인사이즈
  }
  /* small */
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

  // 높이를 컨텐츠의 높이만큼 지정함 (최대높이는 가이드를 따라감)
  &.is-autoHeight {
    height: auto;

    --dialog-max-height: #{rem(660px)};
    &.dialog-size-medium {
      --dialog-max-height: #{rem(540px)};
    }
    &:not(:has(.footer)) {
      padding-block: var(--dialog-inner-padding) calc(var(--dialog-inner-padding) + 8px);
    }
  }
}
.header-wrap {
  flex-shrink: 0;
  flex-grow: 0;
  padding-inline: var(--dialog-inner-padding);
  margin-bottom: var(--dialog-inner-padding);
  .header-inner {
    @include flex($v: center, $h: space-between) {
      gap: rem(16px);
    }
    padding-bottom: rem(14px); // 하단간격 보더포함 총 (16px - 2px)
    border-bottom: rem(2px) solid var(--color-foreground);
  }
  .header {
    flex: 1;
    padding-block: 0;
    .title {
      @include ellipsis(1);
      font-size: var(--font-size-24);
      font-weight: var(--font-weight-bold);
      line-height: var(--line-height-fit);
    }
  }
  // props.title 미지정시 스타일지정
  &.empty-title {
    margin-bottom: rem(12px);
    .header-inner {
      justify-content: end;
      border-bottom: none;
      padding-bottom: 0;
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
  // MsfSelect 필터박스에 navbar 있다면 스타일 지정
  &:has(.pop-search-box) {
    margin-top: 0;
    padding-bottom: rem(12px);
    & + .body {
      .body-inner {
        padding-top: 0;
      }
    }
    &:has(.pop-empty) {
      border-bottom: none;
    }
  }
}
.body {
  flex: 1;
  // @include scrollbar;
  overflow-y: auto;
  min-height: 0;
  // padding-block: var(--dialog-inner-padding);
  // padding-bottom: var(--layout-gutter-y);
  // margin-inline: calc(var(--dialog-inner-padding) * -1);
  // padding-inline: var(--dialog-inner-padding);

  // 커스텀 스크롤바의 높이 100%로 채움
  :deep(.cs-content-inner) {
    height: 100%;
    .body-inner {
      height: 100%;
    }
  }
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
  transition: opacity 0.1s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
.close-btn {
  height: auto;
  padding: 0;
  width: rem(40px);
  height: rem(40px);
  border-radius: 0;
}
</style>
