<script setup>
import { computed } from 'vue'

const props = defineProps({
  // 탭 목록: [{ label: 'Tab 1', value: 'tab1', component: MyComponent }]
  items: {
    type: Array,
    required: true,
  },
  // 현재 선택된 값 (v-model)
  modelValue: {
    type: [String, Number],
    default: '',
  },
  // 스타일 변형 (default 등)
  variant: {
    type: String,
    default: 'default',
    validator: (value) => ['default'].includes(value),
  },
})

const emit = defineEmits(['update:modelValue', 'change'])

// 루트 클래스
const rootClasses = computed(() => ['tabs-root', `is-variant-${props.variant}`])

// 현재 활성화된 탭 계산
const activeTab = computed({
  get: () => props.modelValue || props.items[0]?.value,
  set: (val) => {
    emit('update:modelValue', val)
    emit('change', val)
  },
})

// 현재 활성화된 아이템 객체 찾기
const activeItem = computed(() => props.items.find((item) => item.value === activeTab.value))

const selectTab = (value) => {
  activeTab.value = value
}
</script>

<template>
  <div :class="rootClasses">
    <div class="tabs-header" role="tablist">
      <button
        v-for="item in items"
        :key="item.value"
        :id="`tab-${item.value}`"
        type="button"
        class="tab-button"
        :class="{ 'is-active': activeTab === item.value }"
        role="tab"
        :aria-selected="activeTab === item.value"
        :aria-controls="`panel-${activeTab}`"
        tabindex="0"
        @click="selectTab(item.value)"
        @keydown.enter="selectTab(item.value)"
        @keydown.space.prevent="selectTab(item.value)"
      >
        <span class="tab-label-group">
          <span class="tab-label">{{ item.label }}</span>
          <span v-if="$slots[`suffix-${item.value}`]" class="tab-suffix">
            <slot :name="`suffix-${item.value}`"></slot>
          </span>
          <span v-else-if="item.labelSuffix" class="tab-suffix">
            {{ item.labelSuffix }}
          </span>
        </span>
        <span v-if="variant === 'default' && activeTab === item.value" class="active-line"></span>
      </button>
    </div>
    <div
      :id="`panel-${activeTab}`"
      class="tabs-content"
      role="tabpanel"
      :aria-labelledby="`tab-${activeTab}`"
      tabindex="0"
    >
      <slot v-if="$slots[activeTab]" :name="activeTab"></slot>
      <component :is="activeItem?.component" v-else-if="activeItem?.component" />
      <div v-else class="tabs-empty">내용이 없습니다.</div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.tabs-root {
  --tab-btn-min-width: #{rem(180px)};
  --tab-btn-height: #{rem(54px)};
  --tab-btn-color-active: var(--color-primary-base);
  --tab-btn-font-size: var(--font-size-18);
  --tab-btn-font-weight: var(--font-weight-regular);
  --tab-btn-font-weight-active: var(--font-weight-bold);
  --tab-btn-text-color: var(--color-gray-600);
  --tab-active-line-height: #{rem(4px)};

  width: 100%;

  .tabs-header {
    @include flex() {
      gap: rem(8px);
    }
  }
  .tab-label-group {
    @include flex($v: center);
    gap: rem(4px);
  }
  .tab-suffix {
    font-size: var(--font-size-12);
  }
  .tab-button {
    position: relative;
    background: none;
    border: none;
    cursor: pointer;
    transition: all 0.2s;
    @include flex($v: center, $h: center);
    font-size: var(--tab-btn-font-size);
    min-width: var(--tab-btn-min-width);
    height: var(--tab-btn-height);
  }

  /* --- Variant: Default (밑줄 스타일: 설정화면에서 사용) --- */
  &.is-variant-default {
    .tabs-header {
      border-bottom: var(--border-width-base) solid var(--color-primary-base);
      gap: rem(16px);
    }
    .tab-button {
      padding-inline: rem(16px);
      color: var(--tab-btn-text-color);
      &.is-active {
        color: var(--tab-btn-active-color);
        font-weight: var(--tab-btn-font-weight-active);
      }
    }
    .active-line {
      position: absolute;
      bottom: rem(-1px);
      left: 0;
      width: 100%;
      height: var(--tab-active-line-height);
      background-color: var(--tab-btn-color-active);
    }
  }
  .tabs-content {
    padding-top: rem(40px);
    min-height: rem(50px);
  }
  .tabs-empty {
    padding: rem(20px);
    color: var(--color-gray-450);
    text-align: center;
  }
}
</style>
