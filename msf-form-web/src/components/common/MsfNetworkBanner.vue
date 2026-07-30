<template>
  <Transition name="network-banner">
    <div v-if="isVisible" class="network-banner" :class="statusClass" role="status" aria-live="polite">
      <strong class="network-banner__title">{{ title }}</strong>
      <span class="network-banner__message">{{ message }}</span>
    </div>
  </Transition>
</template>

<script setup>
import { computed } from 'vue'
import { storeToRefs } from 'pinia'
import { useMsfNetworkStore } from '@/stores/msf_network'

const networkStore = useMsfNetworkStore()
const { isUnstable, showRecovered } = storeToRefs(networkStore)

const isVisible = computed(() => isUnstable.value || showRecovered.value)
const statusClass = computed(() =>
  isUnstable.value ? 'network-banner--warning' : 'network-banner--success',
)
const title = computed(() =>
  isUnstable.value ? '네트워크 연결이 불안정합니다.' : '네트워크 연결이 복구되었습니다.',
)
const message = computed(() =>
  isUnstable.value
    ? '입력한 내용이 저장되지 않을 수 있습니다. 연결 상태를 확인해 주세요.'
    : '업무를 계속 진행할 수 있습니다.',
)
</script>

<style scoped lang="scss">
.network-banner {
  position: fixed;
  bottom: var(--layout-bottom-offset);
  left: 50%;
  z-index: 1100;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: rem(10px);
  width: calc(100% - rem(32px));
  max-width: rem(680px);
  min-height: rem(56px);
  padding: rem(14px) rem(16px);
  border-radius: rem(8px);
  backdrop-filter: blur(rem(8px));
  box-shadow:
    0 rem(18px) rem(42px) rgba(15, 23, 42, 0.24),
    0 rem(2px) rem(8px) rgba(15, 23, 42, 0.08),
    inset 0 1px 0 rgba(255, 255, 255, 0.72);
  font-size: rem(16px);
  text-align: center;
  transform: translateX(-50%);
}

.network-banner--warning {
  border: 1px solid #f97316;
  background: linear-gradient(135deg, #fff7ed 0%, #ffedd5 100%);
  color: #9a3412;
  animation: network-warning-float 1.9s ease-in-out infinite;
  box-shadow:
    0 rem(18px) rem(42px) rgba(234, 88, 12, 0.26),
    0 rem(2px) rem(8px) rgba(15, 23, 42, 0.08),
    inset 0 1px 0 rgba(255, 255, 255, 0.75);
}

.network-banner--success {
  border: 1px solid #10b981;
  background: linear-gradient(135deg, #ecfdf5 0%, #d1fae5 100%);
  color: #065f46;
  box-shadow:
    0 rem(18px) rem(42px) rgba(5, 150, 105, 0.22),
    0 rem(2px) rem(8px) rgba(15, 23, 42, 0.08),
    inset 0 1px 0 rgba(255, 255, 255, 0.75);
}

.network-banner__title {
  flex: 0 0 auto;
  font-weight: var(--font-weight-bold);
}

.network-banner__message {
  min-width: 0;
}

.network-banner-enter-active,
.network-banner-leave-active {
  transition:
    opacity 0.24s ease,
    transform 0.24s cubic-bezier(0.2, 0.9, 0.2, 1.15);
}

.network-banner-enter-from,
.network-banner-leave-to {
  opacity: 0;
  transform: translate(-50%, rem(8px));
}

@keyframes network-warning-float {
  0%,
  100% {
    border-color: #f97316;
    transform: translate(-50%, 0);
    box-shadow:
      0 rem(18px) rem(42px) rgba(234, 88, 12, 0.26),
      0 rem(2px) rem(8px) rgba(15, 23, 42, 0.08),
      inset 0 1px 0 rgba(255, 255, 255, 0.75);
  }
  50% {
    border-color: #ea580c;
    transform: translate(-50%, rem(-3px));
    box-shadow:
      0 rem(20px) rem(48px) rgba(234, 88, 12, 0.38),
      0 0 0 rem(3px) rgba(249, 115, 22, 0.16),
      inset 0 1px 0 rgba(255, 255, 255, 0.78);
  }
}

@media (max-width: 640px) {
  .network-banner {
    align-items: center;
    flex-direction: column;
    gap: rem(4px);
    font-size: rem(15px);
  }
}
</style>
