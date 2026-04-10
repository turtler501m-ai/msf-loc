<script setup>
/** step 정의 */
const STEP_LIST = [
  { label: '주문접수', icon: 'order' },
  { label: '결제완료', icon: 'payment' },
  { label: '배송중', icon: 'delivery' },
  { label: '배송완료', icon: 'complete' },
]

/** props */
const props = defineProps({
  currentStep: {
    type: Number,
    required: true,
  },
})

/** 상태 반환 (핵심) */
const getStatus = (index) => {
  if (index < props.currentStep) return 'completed'
  if (index === props.currentStep) return 'on'
  return 'off'
}

/** 클래스 바인딩 */
const getClass = (index) => ({
  'is-completed': getStatus(index) === 'completed',
  'is-on': getStatus(index) === 'on',
  'is-off': getStatus(index) === 'off',
})

/** 헬퍼 */
const isCompleted = (index) => index < props.currentStep
</script>

<template>
  <ul class="step-indicator">
    <li v-for="(step, index) in STEP_LIST" :key="index" :class="getClass(index)">
      <!-- completed면 아이콘 숨김 -->
      <span v-if="!isCompleted(index)" class="icon">
        <Icon :name="step.icon" />
      </span>

      <!-- 숫자 / 완료 -->
      <span class="num">
        <template v-if="isCompleted(index)">✔</template>
        <template v-else>{{ index + 1 }}</template>
      </span>

      <span class="label">{{ step.label }}</span>
    </li>
  </ul>
</template>

<style scoped lang="scss">
.step-indicator {
  display: flex;

  li {
    position: relative;
    flex: 1;
    text-align: center;

    .icon {
      margin-bottom: 4px;
    }

    .num {
      display: inline-flex;
      align-items: center;
      justify-content: center;
      width: 24px;
      height: 24px;
      border-radius: 50%;
      background: #ddd;
      font-size: 12px;
    }

    .label {
      display: block;
      margin-top: 4px;
      font-size: 12px;
    }

    /* 선 */
    &::after {
      content: '';
      position: absolute;
      top: 12px;
      left: 50%;
      width: 100%;
      height: 2px;
      background: #eee;
      z-index: -1;
    }

    &:last-child::after {
      display: none;
    }

    /* completed */
    &.is-completed {
      .num {
        background: #000;
        color: #fff;
      }

      &::after {
        background: #000;
      }
    }

    /* on (현재) */
    &.is-on {
      .num {
        background: #007aff;
        color: #fff;
      }
    }

    /* off (기본) */
    &.is-off {
      .num {
        background: #ddd;
        color: #666;
      }
    }
  }
}
</style>
