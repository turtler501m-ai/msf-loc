<template>
  <div class="agreement-group">
    <div class="all-check-wrapper" @click="onClickAllCheckWrapper">
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
            :key="item.code || idx"
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
import { post } from '@/libs/api/msf.api'
import { isEmpty } from '@/libs/utils/string.utils'

/*
 * FIXME: 기존 M포탈에서 선택 이용약관 목록에서 선택 우선순위가 있는 약관이 존재함. 나중에 선택 이용약관 선택 우선순위 선택 로직 추가 필요.
 */

const props = defineProps({
  policy: { type: String, required: true }, // policy: 불러올 약관 세트의 키(샘플)
  onlyRequired: { type: Boolean, default: false }, // 필수 항목만 모두 체크되었을 때 전체동의로 간주할지 여부
  showTerms: {
    type: Array,
    default: () => [],
  },
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

const emit = defineEmits(['checked'])

const isAllExpanded = ref(false)
const internalTerms = ref([])

// pocliy 에서 불러올 약관들(컴퍼넌트 내부에 지정)
const loadTermsData = async () => {
  const result = await post('/api/shared/form/common/terms/list', { groupCode: props.policy })
  const codes =
    result.data?.codes?.filter(
      (v) => !v.commonStatus || (v.commonStatus && props.showTerms.includes(v.code)),
    ) || []

  const tree = []
  for (const item of codes) {
    if (!isEmpty(item.parentCode)) {
      continue
    }
    if (item.commonStatus && !props.showTerms.includes(item.code)) {
      continue
    }
    tree.push({ ...item, children: [], checked: false })
  }
  for (const item of codes) {
    if (isEmpty(item.parentCode)) {
      continue
    }
    if (item.commonStatus && !props.showTerms.includes(item.code)) {
      continue
    }
    const parent = tree.find((v) => v.code === item.parentCode)
    parent?.children?.push({ ...item, checked: false })
  }

  internalTerms.value = tree

  // const TERMS_DATABASE = {
  //   join: [
  //     {
  //       code: 'term-1',
  //       name: '서비스 이용약관',
  //       required: 'Y',
  //       commonStatus: true,
  //       checked: false,
  //       content: '서비스 이용약관 상세 내용입니다.',
  //     },
  //     {
  //       code: 'term-2',
  //       name: '개인정보 수집 및 이용',
  //       required: 'Y',
  //       commonStatus: false,
  //       checked: false,
  //       children: [
  //         {
  //           name: '수집항목: 연락처, 이메일1',
  //           required: 'Y',
  //           commonStatus: false,
  //           checked: false,
  //           content: '수집항목: 연락처, 이메일1 상세 내용입니다.',
  //         },
  //         {
  //           name: '수집항목: 연락처, 이메일2',
  //           required: 'N',
  //           commonStatus: false,
  //           checked: false,
  //           content: '수집항목: 연락처, 이메일2 상세 내용입니다.',
  //         },
  //       ],
  //     },
  //     {
  //       code: 'term-3',
  //       name: '마케팅 정보 수신 동의',
  //       required: 'N',
  //       commonStatus: false,
  //       checked: false,
  //       content: '마케팅 정보 수신 동의 상세 내용입니다.',
  //     },
  //   ],
  //   marketing: [
  //     { code: 'm-1', name: '이메일 수신 동의', required: 'N', commonStatus: false, checked: false },
  //     { code: 'm-2', name: 'SMS 수신 동의', required: 'N', commonStatus: false, checked: false },
  //   ],
  // }

  // // 복사를 통해 원본 데이터와 분리하여 상태 관리
  // internalTerms.value = JSON.parse(JSON.stringify(TERMS_DATABASE[props.policy] || []))
}

onMounted(loadTermsData)
watch(() => props.policy, loadTermsData)

const generateResult = (data) => {
  const result = []
  for (const item of data) {
    if (
      !isEmpty(item.termsGroupCd) ||
      item.termsGroupCd !== '/' ||
      !isEmpty(item.termsItemCd) ||
      item.termsItemCd !== '/'
    ) {
      result.push({ code: item.code, required: item.required, checked: item.checked })
    }
    if (item.children?.length > 0) {
      for (const sub of item.children) {
        result.push({ code: sub.code, required: item.required, checked: sub.checked })
      }
    }
  }
  return result
}

watch(
  () => internalTerms.value,
  (newVal) => {
    emit('checked', generateResult(newVal))
  },
  {
    immediate: true,
    deep: true,
  },
)

// // 부모 컴포넌트에서 ref를 통해 접근할 수 있는 값
// defineExpose({
//   // 필수 항목 충족 여부 (computed로 감싸 실시간 상태 반영)
//   isReady: computed(() => {
//     const validate = (list) =>
//       list.every(
//         (item) =>
//           (!(item.required === 'Y' || item.required === '2') || item.checked) &&
//           (!item.children || validate(item.children)),
//       )
//     return internalTerms.value.length > 0 && validate(internalTerms.value)
//   }),
//   // 현재 체크된 전체 데이터 객체
//   data: internalTerms,
// })

// 전체 체크박스 계산 로직
const isAllChecked = computed({
  get: () => {
    if (!internalTerms.value.length) return false
    const targetItems = props.onlyRequired
      ? internalTerms.value.filter((item) => item.required === 'Y' || item.required === '2')
      : internalTerms.value
    return targetItems.length > 0 && targetItems.every((item) => item.checked)
  },
  set: (val) => {
    internalTerms.value.every((item) => {
      item.checked = val
      if (item.children?.length) {
        const deepCheck = (children) => {
          children.every((c) => {
            c.checked = val
            if (c.children) deepCheck(c.children)
            return true
          })
        }
        deepCheck(item.children)
      }
      return true
    })
  },
})

// 아코디언 토글 클릭 이벤트
const onClickAllCheckWrapper = (e) => {
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
