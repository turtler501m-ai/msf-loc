/**
 * [useMsfFormGroupLabel]
 *
 * 목적
 * - MsfFormGroup의 label for와 하위 입력 컴포넌트의 실제 id 연결
 * - label 미연결/중첩/중복 id로 인한 접근성 콘솔 경고 방지
 *
 * 규칙
 * - FormGroup 안에서 가장 먼저 등록된 control id를 label for로 사용
 * - 자식 input에 id가 직접 있으면 그 id를 사용
 * - 자식 input에 id가 없으면 input 자체 자동 id를 사용
 * - tag가 label이 아니면 for 연결 안 함
 *
 * 주의
 * - MsfInput, MsfTextarea 등 실제 입력 컨트롤에서 필요시 등록하여 사용
 */

import { computed, inject, onBeforeUnmount, provide, ref, watch } from 'vue'

const MSF_FORM_GROUP_LABEL_KEY = Symbol('msf-form-group-label-context')

// FormGroup에서 사용
export const useMsfFormGroupLabelProvider = (isLabelTag, generatedId) => {
  // FormGroup 하위 입력 컨트롤 id 목록
  const controlIds = ref([])

  // 입력 컨트롤 등록
  const registerControlId = (id) => {
    if (!id) return

    // 중복 등록 방지
    if (!controlIds.value.includes(id)) {
      controlIds.value.push(id)
    }
  }

  // 입력 컨트롤 해제
  const unregisterControlId = (id) => {
    controlIds.value = controlIds.value.filter((item) => item !== id)
  }

  // label for에 사용할 id
  const labelFor = computed(() => {
    // label 태그가 아니면 for 미사용
    if (!isLabelTag.value) return undefined

    // 첫 번째 입력 컨트롤 id 우선
    // 아직 등록 전이면 기존 generatedId 사용
    return controlIds.value[0] || generatedId
  })

  provide(MSF_FORM_GROUP_LABEL_KEY, {
    labelFor,
    registerControlId,
    unregisterControlId,
  })

  return {
    labelFor,
  }
}

// Input 쪽에서 context만 먼저 확인할 때 사용
export const useMsfFormGroupLabelContext = () => {
  return inject(MSF_FORM_GROUP_LABEL_KEY, null)
}

// Input / Select 등 실제 control에서 사용
export const useMsfFormControlLabel = (inputId, context = useMsfFormGroupLabelContext()) => {
  watch(
    inputId,
    (newId, oldId) => {
      if (!context) return

      // id가 바뀐 경우 이전 id 제거
      if (oldId) {
        context.unregisterControlId?.(oldId)
      }

      // 현재 id 등록
      context.registerControlId?.(newId)
    },
    { immediate: true },
  )

  onBeforeUnmount(() => {
    context?.unregisterControlId?.(inputId.value)
  })

  return {
    formGroupLabelContext: context,
  }
}
