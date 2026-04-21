<template>
  <div class="agreement-item" :class="{ 'is-open': isExpanded }">
    <div class="item-header" @click="handleToggle">
      <div class="checkbox-area" @click.stop>
        <MsfCheckbox
          :variant="props.type"
          :model-value="modelValue"
          :label="name"
          @update:model-value="handleUpdateModel"
        >
          <template #label-prepend>
            <em
              v-if="required === 'Y' || required === '2'"
              class="required-mark"
              aria-label="필수 항목"
              >[필수]</em
            >
            <em v-else class="optional-mark" aria-label="선택 항목">[선택]</em>
          </template>
        </MsfCheckbox>
      </div>
      <div class="actions">
        <MsfButton
          v-if="content"
          class="detail-btn"
          @click.stop="showDialog = true"
          iconOnly="agreeArrowRight"
          >약관 상세보기 팝업 열기
        </MsfButton>
        <MsfButton
          v-if="children?.length"
          class="arrow-btn"
          :class="{ 'is-rotated': isExpanded }"
          :aria-expanded="isExpanded"
          @click.stop="handleToggle"
          iconOnly="agreeArrowDown"
        >
          하위 항목 펼치기
        </MsfButton>
      </div>
    </div>
    <MsfDialog :is-open="showDialog" :title="name" show-close @close="showDialog = false">
      <div class="terms-content">
        <template v-if="Array.isArray(content)">
          <ul class="text-list">
            <li v-for="(text, i) in content" :key="i" v-html="text"></li>
          </ul>
        </template>
        <template v-else>
          <div class="plain-text" v-html="content"></div>
        </template>
      </div>
      <template #footer>
        <MsfButtonGroup align="center">
          <MsfButton variant="primary" class="confirm-btn" @click="onClickConfirmBtn()">
            동의 후 닫기
          </MsfButton>
        </MsfButtonGroup>
      </template>
    </MsfDialog>

    <transition name="expand">
      <div v-show="isExpanded && children?.length > 0" class="child-group-container">
        <div class="child-group-inner">
          <MsfAgreementItem
            v-for="(child, idx) in children"
            :key="idx"
            v-model="child.checked"
            v-bind="child"
            :only-required="onlyRequired"
            @change="(checked) => emit('change', checked)"
          />
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'

defineOptions({ name: 'AgreementItem' })

const props = defineProps({
  modelValue: Boolean, // 체크박스 선택 여부
  code: String,
  name: String, // 약관 제목 텍스트
  required: String, // 필수 동의 여부 (true: 필수 / false: 선택)
  content: [String, Array], // 상세보기 팝업 내용 (문자열 또는 배열)
  children: Array, // 하위 약관 항목 리스트
  onlyRequired: {
    // 필수 항목만 체크 시 부모 체크 여부 결정
    type: Boolean,
    default: false,
  },
  type: {
    type: String,
    default: 'lined', // 기본값
    validator: (value) => ['default', 'lined'].includes(value),
  },
})

const emit = defineEmits(['update:modelValue', 'change'])

const isExpanded = ref(false)
const showDialog = ref(false)

// 아코디언 토글 함수
const handleToggle = () => {
  if (props.children?.length) {
    isExpanded.value = !isExpanded.value
  }
}

// 자식들의 체크 상태 변화 감시 (하위 항목 모두 체크 시 부모 체크)
watch(
  () => props.children,
  (newChildren) => {
    if (!newChildren?.length) return

    let isTargetAllChecked
    if (props.onlyRequired) {
      const requiredItems = newChildren.filter(
        (child) => child.required === 'Y' || child.required === '2',
      )
      isTargetAllChecked =
        requiredItems.length > 0 ? requiredItems.every((child) => child.checked) : props.modelValue
    } else {
      isTargetAllChecked = newChildren.every((child) => child.checked)
    }

    if (isTargetAllChecked !== props.modelValue) {
      emit('update:modelValue', isTargetAllChecked)
      emit('change', isTargetAllChecked)
    }
  },
  { deep: true },
)

//  본인 체크박스 조작 시 (부모 체크 시 자식들 일괄 체크)
const handleUpdateModel = (value) => {
  emit('update:modelValue', value)
  emit('change', value)

  if (props.children?.length) {
    props.children.forEach((child) => {
      child.checked = value
    })
  }
}

const onClickConfirmBtn = () => {
  emit('update:modelValue', true)
  emit('change', true)
  showDialog.value = false
}
</script>

<style lang="scss" scoped>
.agreement-item {
  --agreement-item-btn-size: #{rem(52px)}; // 아이콘 사이즈
  min-height: var(--agreement-item-btn-size);

  .item-header {
    height: 100%;
    @include flex($v: center, $h: space-between);
    cursor: pointer;
  }
  .actions {
    @include flex($v: center) {
      gap: rem(8px);
    }
    .detail-btn,
    .arrow-btn {
      width: var(--agreement-item-btn-size);
      height: var(--agreement-item-btn-size);
      background: none;
      border: none;
    }
    .detail-btn {
      color: var(--color-gray-400);
      font-size: var(--font-size-24);
    }
    .arrow-btn {
      transition: transform var(--transition-base);
      &.is-rotated {
        transform: rotate(180deg);
      }
    }
  }
  /* Grid 슬라이드 애니메이션 */
  .child-group-container {
    display: grid;
    grid-template-rows: 1fr;
    transition:
      grid-template-rows 0.3s ease-out,
      opacity 0.3s ease-out;
    background: var(--color-gray-25);
    border-radius: var(--border-radius-base);
    overflow: hidden;
    margin-block: rem(8px) rem(16px);
    padding-block: rem(8px);
    padding-left: rem(24px);

    .child-group-inner {
      min-height: 0;
      @include flex($d: column) {
        gap: rem(8px);
      }
    }
  }
  .expand-enter-from,
  .expand-leave-to {
    grid-template-rows: 0fr;
    opacity: 0;
  }
  .required {
    color: #ff4d4f;
    font-style: normal;
    margin-right: 4px;
    font-weight: bold;
  }
  .optional {
    color: #999;
    font-style: normal;
    margin-right: 4px;
  }
}
// 약관내용을 감싸는 클래스
.terms-content {
  font-size: var(--font-size-16);
  overflow-y: auto;
}
.confirm-btn {
  position: relative;
  cursor: pointer;
}
</style>
