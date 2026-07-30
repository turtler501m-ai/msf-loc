/*
  useMsfFormGroupValidation
  - MsfFormGroup 안의 input 상태를 수집해 선택입력 기본 검증 결과를 생성
  - 필수 FormGroup은 제외하고, 선택 FormGroup만 "전부 비움" 또는 "끝까지 입력" 상태로 판정
  - 같은 label의 optionalRule이 있으면 업무별 규칙을 우선하고 기본 자동검증 생략
  - alert/focus UI는 직접 처리하지 않고 MsfForm이 읽을 수 있는 결과 객체만 반환
*/
import { inject, reactive, watch, onBeforeUnmount } from 'vue'

export function useMsfFormGroupValidation(props, generatedId) {
  // 상위 MsfForm 선택입력 검증 컨텍스트
  const msfForm = inject('msf-form-context', null)
  // 현재 화면/스텝 선택입력 검증 범위
  const optionalScope = inject('msf-form-scope', null)
  // 현재 FormGroup에 등록된 input 상태 Map
  const inputStates = reactive(new Map())

  // HTML 태그와 여분 공백을 제거한 label
  const normalizeLabel = (label) => {
    return String(label || '')
      .replace(/<[^>]*>/g, ' ')
      .replace(/\s+/g, ' ')
      .trim()
  }

  // 같은 label의 optionalRule 등록 여부
  const isOverriddenByOptionalRule = (label) => {
    // MsfForm에 등록된 화면/업무별 선택입력 검증 규칙 목록
    const rules = msfForm?.optionalRules?.value || []

    return rules.some((rule) => {
      const sameLabel = normalizeLabel(rule.label) === label
      const sameScope = !optionalScope || !rule.scope || rule.scope === optionalScope
      return sameLabel && sameScope
    })
  }

  // MsfInput mount 시 FormGroup 검증 대상으로 등록
  const registerInput = (input) => {
    if (!input?.id) return
    inputStates.set(input.id, input)
  }

  // MsfInput 값, 비활성, readonly, 길이 상태 갱신
  const updateInput = (id, input) => {
    if (!inputStates.has(id)) return

    inputStates.set(id, {
      ...inputStates.get(id),
      ...input,
    })
  }

  // MsfInput unmount 시 FormGroup 검증 대상에서 제거
  const unregisterInput = (id) => {
    inputStates.delete(id)
  }

  // disabled 또는 readonly가 아닌 실제 입력 가능 input 목록
  const getEnabledInputs = () => {
    return Array.from(inputStates.values()).filter((input) => {
      return !input.disabled && !input.readonly
    })
  }

  // 현재 FormGroup의 선택입력 검증 결과
  const getCondition = () => {
    if (props.required) return null

    // 현재 FormGroup에서 검증 가능한 활성 input 목록
    const inputs = getEnabledInputs()
    if (inputs.length === 0) return null

    // alert 메시지와 optionalRule 매칭용 정규화 label
    const label = normalizeLabel(props.label)

    // optionalRules에 같은 label로 직접 등록된 항목이면
    // FormGroup 자동검증에서는 제외한다.
    if (isOverriddenByOptionalRule(label)) return null

    // 값이 비어 있는 input 목록
    const emptyInputs = inputs.filter((input) => input.empty)
    // 하나라도 입력된 값이 있는지 여부
    const hasAnyInput = emptyInputs.length !== inputs.length

    if (!hasAnyInput) {
      return {
        key: generatedId,
        scope: optionalScope,
        label,
        state: 'EMPTY',
        conditionMissed: false,
        message: '',
      }
    }

    // 일부 입력이 시작된 그룹에서 가장 먼저 비어 있는 input
    const emptyInput = inputs.find((input) => input.empty)
    // 값은 있지만 자체 유효성 또는 길이 조건을 만족하지 못한 첫 input
    const invalidValueInput = inputs.find((input) => {
      return !input.empty && (input.valid === false || input.validLength === false)
    })
    // alert 닫힘 후 focus를 보낼 첫 번째 실패 input
    const firstInvalidInput = emptyInput || invalidValueInput || null
    // 비어 있거나 유효하지 않은 모든 input 목록
    const invalidInputs = inputs.filter((input) => {
      return input.empty || input.valid === false || input.validLength === false
    })
    // 현재 FormGroup에 미완성 input 존재 여부
    const hasInvalidInput = invalidInputs.length > 0

    if (hasInvalidInput) {
      // FormGroup 또는 MsfForm 별도 메시지가 없을 때 사용할 기본 메시지
      const fallbackMessage = label
        ? `${label} 항목을 끝까지 입력해주세요.`
        : '입력 항목을 끝까지 입력해주세요.'

      return {
        key: generatedId,
        scope: optionalScope,
        focusTarget: firstInvalidInput?.id || generatedId,
        focusElement: firstInvalidInput?.element || null,
        focus: firstInvalidInput?.focus,
        label,
        state: 'INCOMPLETE',
        conditionMissed: true,
        message: props.optionalMessage || msfForm?.optionalMessage?.value || fallbackMessage,
      }
    }

    return {
      key: generatedId,
      scope: optionalScope,
      label,
      state: 'COMPLETE',
      conditionMissed: false,
      message: '',
    }
  }

  msfForm?.registerGroup?.({
    id: generatedId,
    scope: optionalScope,
    getCondition,
  })

  watch(
    () => [props.required, props.label],
    () => {
      msfForm?.updateGroup?.(generatedId, { scope: optionalScope, getCondition })
    },
  )

  onBeforeUnmount(() => {
    msfForm?.unregisterGroup?.(generatedId)
  })

  return {
    registerInput,
    updateInput,
    unregisterInput,
    getCondition,
  }
}
