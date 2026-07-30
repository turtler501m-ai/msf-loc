<template>
  <div class="agreement-item" :class="{ 'is-open': isExpanded }">
    <div class="item-header" @click="handleToggle">
      <div class="checkbox-area" @click.stop>
        <MsfCheckbox
          ref="agreementItemRef"
          :variant="props.type"
          :model-value="modelValue"
          :label="name || label || title"
          @update:model-value="handleUpdateModel"
          :disabled="props.disabled"
        >
          <template #label-prepend>
            <em
              v-if="required === 'Y' || required === '2' || required === true"
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
          v-if="version || content || (termsGroupCd && termsItemCd)"
          class="detail-btn"
          @click.stop="showDialog = true"
          iconOnly="agreeArrowRight"
          :disabled="props.disabled"
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
    <MsfAgreementDetail
      v-model="showDialog"
      :groupCode="groupCode"
      :code="code"
      :id1="termsGroupCd"
      :id2="termsItemCd"
      :version="version"
      :title="popTitle || name || label || title"
      :content="content"
      :spec-terms="specTerms"
      @confirm="onConfirmDetail"
    />

    <transition name="expand">
      <div v-show="isExpanded && children?.length > 0" class="child-group-container">
        <div class="child-group-inner">
          <MsfAgreementItem
            v-for="(child, idx) in children"
            :key="idx"
            v-model="child.checked"
            v-bind="child"
            :spec-terms="
              child.code
                ? props.specTerms?.find?.((v) => v.code === child.code) || props.specTerms
                : props.specTerms
            "
            :only-required="onlyRequired"
            :disabled="props.disabled"
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
  groupCode: String,
  code: String,
  name: String, // 약관 제목 텍스트
  label: String, // name 대신 사용할 수 있는 라벨
  title: String, // name/label 대신 사용할 수 있는 제목
  popTitle: String, // 팝업 제목 (name 대신 사용 가능)
  content: [String, Array], // 직접 전달할 약관 내용
  termsGroupCd: String,
  termsItemCd: String,
  specTerms: Object,
  required: [String, Boolean], // 필수 동의 여부 (true: 필수 / false: 선택)
  version: [String, Array], // 약과내용 버전 (문자열 또는 배열)
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
  disabled: { type: Boolean, required: false }, // 비활성화 처리필요시 사용
})

const emit = defineEmits(['update:modelValue', 'change'])

const agreementItemRef = ref(null)

const isExpanded = ref(false)
const showDialog = ref(false)

// 아코디언 토글 함수
const handleToggle = () => {
  // 하위 항목이 있으면 무조건 토글 (disabled와 상관없이 작동)
  if (props.children?.length) {
    isExpanded.value = !isExpanded.value
    return
  }
  // 하위 항목이 없고, disabled가 아닐 때만 팝업 열기
  if (
    !props.disabled &&
    (props.version || props.content || (props.termsGroupCd && props.termsItemCd))
  ) {
    showDialog.value = true
  }
}

// 자식들의 체크 상태 변화 감시 (하위 항목 모두 체크 시 부모 체크)
watch(
  () => props.children,
  (newChildren) => {
    if (!newChildren?.length) return

    //  자식이 존재하면, 필수/선택 여부와 관계없이 하위 자식들이 '모두' 체크되었을 때만 부모가 체크 상태가 됨
    const isTargetAllChecked = newChildren.every((child) => child.checked)

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

const onConfirmDetail = (result) => {
  handleUpdateModel(result)
}

defineExpose({
  focus: () => {
    agreementItemRef.value?.focus()
  },
})
</script>

<style lang="scss" scoped>
.agreement-item {
  --agreement-item-btn-size: #{rem(52px)}; // 아이콘 사이즈
  min-height: var(--agreement-item-btn-size);

  .item-header {
    height: 100%;
    min-height: var(--agreement-item-btn-size);
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
      &:disabled {
        background-color: transparent !important;
      }
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
