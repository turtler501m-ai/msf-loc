<template>
  <div class="step-indicator-root">
    <h2 class="ut-blind">현재 단계</h2>
    <ul :class="['step-list', `is-active-${props.status + 1}`]">
      <li
        v-for="(step, index) in STEP_LIST"
        :key="index"
        class="step-item"
        :class="getClass(index)"
      >
        <div class="step-cont">
          <div class="step-icon">
            <MsfIcon v-if="!isCompleted(index)" :name="step.icon" />
            <MsfIcon v-if="isCompleted(index)" name="stepDone" class="icon-done" />
          </div>
          <em v-if="isActive(index)" class="count-mark">0{{ index + 1 }}</em>
          <span class="step-label">{{ step.label }}</span>
          <span class="ut-blind">
            {{ isCompleted(index) ? '완료' : isActive(index) ? '진행 중' : '대기' }}
          </span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script setup>
const props = defineProps({
  currentStep: {
    type: Number,
    required: true,
  },
})

// 스텝 정의
const STEP_LIST = [
  { label: '고객', icon: 'customer' },
  { label: '상품', icon: 'product' },
  { label: '동의', icon: 'agree' },
]

// 상태 반환
const getStatus = (index) => {
  if (index < props.currentStep) return 'completed'
  if (index === props.currentStep) return 'on'
  return 'off'
}

// 상태별 클래스 지정
const getClass = (index) => ({
  'is-completed': getStatus(index) === 'completed',
  'is-on': getStatus(index) === 'on',
  'is-off': getStatus(index) === 'off',
})

// 상태
const isCompleted = (index) => index < props.currentStep
const isActive = (index) => index === props.currentStep
</script>

<style lang="scss" scoped>
.step-indicator-root {
  --step-indecator-background-color: var(--color-gray-25);
  --step-indecator-text-icon-color: var(--color-gray-500);
  --step-indecator-text-weight: var(--font-weight-regular);

  background-color: var(--step-indecator-background-color);
  width: rem(94px);
  height: 100%;
  padding-block: rem(32px);
  position: relative;
  .step-list {
    @include flex($d: column) {
      gap: rem(16px);
    }
    position: relative;
    left: rem(8px);
    & > li {
      position: relative;
      &:not(:last-child) {
        &:before {
          content: '';
          width: rem(1px);
          height: rem(50px);
          background-color: var(--color-gray-100);
          @include position($b: -32px, $l: 24px);
        }
      }
    }
  }
  // 단계 아이템
  .step-item {
    position: relative;
    left: 0;
    width: rem(94px);
    height: rem(58px);
    .step-cont {
      @include flex($v: center) {
        gap: rem(4px);
      }
      width: 100%;
      height: 100%;
      font-weight: var(--step-indecator-text-weight);
      text-align: center;
      @include position($p: relative, $l: rem(0), $t: rem(0), $i: 1);
      padding-left: rem(12px);
      color: var(--step-indecator-text-icon-color);
      .step-icon {
        color: var(--step-indecator-text-icon-color);
      }
    }
    .count-mark {
      width: rem(24px);
      height: rem(24px);
      background: var(--color-accent-base);
      color: #fff;
      @include position($r: rem(3px), $t: rem(-6px));
      @include flex($display: inline-flex, $h: center, $v: center);
      border-radius: var(--border-radius-max);
      font-size: var(--font-size-12);
      font-weight: var(--font-weight-medium);
      line-height: var(--line-height-fit);
    }
    .step-item {
      position: relative;
      display: inline-block;
      padding: rem(20px); /* 배지가 삐져나갈 공간 확보 */
    }
    /* 내부 텍스트 및 아이콘 스타일 */
    .step-text {
      font-size: 24px;
      font-weight: 800;
      color: #333;
    }
    .icon-box {
      font-size: 30px;
    }
  }
  // 스텝 스타일을 지정
  .step-list {
    & > li {
      &.step-item {
        // 1. 활성 스타일
        &.is-on {
          --step-indecator-text-icon-color: var(--color-gray-900);
          --step-indecator-text-weight: var(--font-weight-bold);
          // 활성 배경넣기
          &:after {
            content: '';
            width: rem(94px);
            height: rem(58px);
            background-image: url('@/assets/images/stepBg.svg');
            background-position: center center;
            // background-size: 100%;
            @include position($t: 0, $l: 0);
            filter: drop-shadow(0 4px 8px rgba(0, 0, 0, 0.1));
          }
        }
        // 2. 기본 스타일
        &.is-off {
          --step-indecator-text-icon-color: var(--color-gray-500);
          .step-icon {
            i {
              background: var(--step-indecator-background-color);
            }
          }
        }
        // 3. 완료 스타일
        &.is-completed {
          --step-indecator-text-icon-color: var(--color-gray-900);
          .step-icon {
            background: var(--color-primary-base);
            border-radius: var(--border-radius-max);
            width: rem(24px);
            height: rem(24px);
            display: inline-flex;
            @include flex($display: inline-flex, $h: center, $v: center);
            .icon-done {
              color: var(--color-white);
              font-size: var(--font-size-18);
            }
          }
        }
      }
    }
  }
}
</style>
