<template>
  <div class="agreement-group">
    <div class="all-check-wrapper" @click="handleOnlyAccordion">
      <div class="checkbox-area" @click.stop>
        <MsfCheckbox v-model="isAllChecked" :label="checkboxLabel" class="all-check-custom">
          <template #label-prepend>
            <em v-if="props.required" class="required-mark" aria-label="필수 항목">[필수]</em>
          </template>
          <template #label-append>
            <p v-if="description" class="desc-msg">
              {{ description }}
            </p>
          </template>
        </MsfCheckbox>
      </div>
      <MsfButton
        class="all-expand-btn"
        :class="{ 'is-rotated': isAllExpanded }"
        :aria-expanded="isAllExpanded"
        iconOnly="agreeArrowDown"
      >
        약관 목록 토글
      </MsfButton>
    </div>
    <transition name="expand">
      <div v-show="isAllExpanded" class="agreement-list-container">
        <div class="agreement-list-inner">
          <MsfAgreementItem
            v-for="(item, idx) in internalTerms"
            :key="item.id || idx"
            v-model="item.checked"
            v-bind="item"
            :only-required="onlyRequired"
          />
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'

const props = defineProps({
  policy: { type: String, required: true }, // policy: 불러올 약관 세트의 키(샘플)
  onlyRequired: { type: Boolean, default: false }, // 필수 항목만 모두 체크되었을 때 전체동의로 간주할지 여부
  required: Boolean, // 필수 동의 여부 (true: 필수 / false: 선택)
  checkboxLabel: {
    type: String, // 체크박스 레이블 설정 필요시 사용
    default: '이용약관, 개인정보 수집/이용 및 선택동의 항목에 모두 동의합니다.',
  },
  description: {
    type: String, // 안내 문구 설정 필요시 사용
    default: '※ 고객님의 편의를 위한 모든 약관(선택약관 포함)에 일괄동의 하시겠습니까?',
  },
})

const isAllExpanded = ref(false)
const internalTerms = ref([])

// pocliy 에서 불러올 약관들(컴퍼넌트 내부에 지정)
const loadTermsData = () => {
  const TERMS_DATABASE = {
    join: [
      {
        id: 'term-1',
        label: '서비스 이용약관',
        required_yn: 'Y',
        required: true,
        checked: false,
        content: '서비스 이용약관 상세 내용입니다.',
      },
      {
        id: 'term-2',
        label: '개인정보 수집 및 이용',
        required: true,
        checked: false,
        children: [
          {
            label: '수집항목: 연락처, 이메일1',
            required: true,
            checked: false,
            content: '수집항목: 연락처, 이메일1 상세 내용입니다.',
          },
          {
            label: '수집항목: 연락처, 이메일2',
            required: false,
            checked: false,
            content: '수집항목: 연락처, 이메일2 상세 내용입니다.',
          },
        ],
      },
      {
        id: 'term-3',
        label: '마케팅 정보 수신 동의',
        required: false,
        checked: false,
        content: '마케팅 정보 수신 동의 상세 내용입니다.',
      },
    ],
    marketing: [
      { id: 'm-1', label: '이메일 수신 동의', required: false, checked: false },
      { id: 'm-2', label: 'SMS 수신 동의', required: false, checked: false },
    ],
  }

  // 복사를 통해 원본 데이터와 분리하여 상태 관리
  internalTerms.value = JSON.parse(JSON.stringify(TERMS_DATABASE[props.policy] || []))
}

onMounted(loadTermsData)
watch(() => props.policy, loadTermsData)

// 부모 컴포넌트에서 ref를 통해 접근할 수 있는 값
defineExpose({
  // 필수 항목 충족 여부 (computed로 감싸 실시간 상태 반영)
  isReady: computed(() => {
    const validate = (list) =>
      list.every(
        (item) => (!item.required || item.checked) && (!item.children || validate(item.children)),
      )
    return internalTerms.value.length > 0 && validate(internalTerms.value)
  }),
  // 현재 체크된 전체 데이터 객체
  data: internalTerms,
})

// 전체 체크박스 계산 로직
const isAllChecked = computed({
  get: () => {
    if (!internalTerms.value.length) return false
    const targetItems = props.onlyRequired
      ? internalTerms.value.filter((item) => item.required)
      : internalTerms.value
    return targetItems.length > 0 && targetItems.every((item) => item.checked)
  },
  set: (val) => {
    internalTerms.value.forEach((item) => {
      item.checked = val
      if (item.children?.length) {
        const deepCheck = (children) => {
          children.forEach((c) => {
            c.checked = val
            if (c.children) deepCheck(c.children)
          })
        }
        deepCheck(item.children)
      }
    })
  },
})

// 아코디언 토글 핸들러
const handleOnlyAccordion = (e) => {
  // 체크박스 클릭 시에는 접히지 않도록 방지
  if (e.target.closest('.checkbox-area')) return
  isAllExpanded.value = !isAllExpanded.value
}
</script>

<style lang="scss" scoped>
.agreement-group {
  --agreement-item-btn-size: #{rem(52px)}; // 아이콘 사이즈
  --agreement-group-border-color: var(--color-gray-100);

  width: 100%;

  .all-check-wrapper {
    @include flex($h: space-between, $v: baseline);
    cursor: pointer;
    .all-check-custom {
      :deep(.checkbox-label-text) {
        font-weight: 700;
        font-size: 16px;
        color: #212529;
      }
    }
    .all-expand-btn {
      width: var(--agreement-item-btn-size);
      height: var(--agreement-item-btn-size);
      background: none;
      border: none;
      cursor: pointer;
      @include flex($v: center);
      transition: transform var(--transition-base);
      &.is-rotated {
        transform: rotate(180deg);
      }
    }
  }

  // 목록 슬라이드 애니메이션
  .agreement-list-container {
    display: grid;
    grid-template-rows: 1fr;
    transition:
      grid-template-rows var(--transition-base),
      opacity var(--transition-base);
    overflow: hidden;
    border-top: var(--border-width-base) solid var(--color-gray-100);
    border-bottom: var(--border-width-base) solid var(--color-gray-100);
    margin-top: rem(24px);
    padding-block: rem(16px) rem(24px);
  }
  .agreement-list-inner {
    min-height: 0;
  }
  .desc-msg {
    font-size: var(--font-size-14);
    line-height: var(--line-height-fit);
    color: var(--color-accent-base);
    margin-top: rem(14px);
  }
  // Transition Classes
  .expand-enter-from,
  .expand-leave-to {
    grid-template-rows: 0fr;
    opacity: 0;
  }
}
</style>
