/*
  useOptionalValidationAlert
  - MsfForm 선택입력 검증 결과를 alert/focus 처리로 연결
  - 페이지/스텝 컴포넌트에서 validateOptionalWithAlert()만 호출하도록 반복 코드 정리
  - 상위 MsfForm 컨텍스트가 주입되지 않은 상황(MsfForm 미사용)에서도 rules 자체 검증을 지원하도록 폴백 내장
*/
import { inject, onBeforeUnmount, onMounted, provide, unref } from 'vue'
import { showAlertWithId } from '@/libs/utils/comp.utils'
import { focusField } from '@/libs/utils/focus.utils'

export function useOptionalValidationAlert(options = {}) {
  // 상위 MsfForm 선택입력 검증 컨텍스트
  const msfFormContext = inject('msf-form-context', null)

  // option 값 또는 ref 값을 모두 사용할 수 있도록 정규화
  const getOption = (key, fallback) => {
    const value = options[key]
    return value === undefined ? fallback : unref(value)
  }

  const scope = getOption('scope')

  if (scope) {
    provide('msf-form-scope', scope)
  }

  const registeredRuleKeys = []

  // options.rules에 등록된 업무별 선택입력 규칙 목록
  const getRules = () => {
    const rules = getOption('rules', [])
    if (typeof rules === 'function') return rules()
    if (Array.isArray(rules)) return rules

    return []
  }

  // 페이지/스텝 전용 선택입력 규칙 등록
  const registerRules = () => {
    if (!msfFormContext) return // 컨텍스트가 없으면 전역 등록 생략

    getRules().forEach((rule) => {
      const key = msfFormContext?.registerOptionalRule?.({
        scope,
        ...rule,
      })

      if (key) registeredRuleKeys.push(key)
    })
  }

  // 페이지/스텝 unmount 시 등록한 선택입력 규칙 제거
  const unregisterRules = () => {
    if (!msfFormContext) return

    registeredRuleKeys.forEach((key) => {
      msfFormContext?.unregisterOptionalRule?.(key)
    })
    registeredRuleKeys.length = 0
  }

  onMounted(() => {
    registerRules()
  })

  onBeforeUnmount(() => {
    unregisterRules()
  })

  // 선택입력 검증 실패 메시지 목록
  const getMessage = (missed) => {
    if (typeof options.message === 'function') return options.message(missed)

    return missed
      .map((item) => item.message)
      .filter(Boolean)
      .join('\n')
  }

  // 첫 번째 실패 항목으로 focus 이동
  const focusFirstMissed = (first) => {
    const target = first?.focusElement || first?.focusTarget || first?.key
    if (!target) return

    focusField(target, {
      root: getOption('root', document),
      delay: getOption('focusDelay', 200),
    })
  }

  // 선택입력 검증 실행 후 실패 시 alert 표시
  const validateOptionalWithAlert = (validateOptions = {}) => {
    let result

    // 1. MsfForm 컨텍스트가 존재하는 경우 (전체 폼 수집 방식)
    if (msfFormContext) {
      result = msfFormContext.validateOptionalConditions?.({
        scope: getOption('scope'),
        ...validateOptions,
      })
    } else {
      // 2. 컨텍스트가 존재하지 않는 경우 (훅 자체 rules 단독 검증 기능 활성화)
      const rules = getRules()
      const missed = []

      rules.forEach((rule) => {
        // validate 커스텀 함수 우선 처리
        if (typeof rule.validate === 'function') {
          const res = rule.validate()
          if (res !== true && res !== undefined && res !== null) {
            missed.push({
              key: rule.key || rule.label,
              focusTarget: rule.focusTarget,
              message: typeof res === 'string' ? res : (rule.message || `${rule.label} 항목을 입력해주세요.`),
            })
          }
          return
        }

        // values 기반 선택입력 자릿수 자동 검사
        if ('values' in rule) {
          const values = typeof rule.values === 'function' ? rule.values() : rule.values
          const isEmpty = (v) => v === undefined || v === null || String(v).trim() === ''

          const hasAnyInput = values.some((val) => !isEmpty(val))
          const isAllFilled = values.every((val) => !isEmpty(val))

          // 일부 필드만 채워진 불완전한 상태
          if (hasAnyInput && !isAllFilled) {
            const emptyIndex = values.findIndex((val) => isEmpty(val))
            const targets = Array.isArray(rule.focusTargets) ? rule.focusTargets : [rule.focusTarget]
            missed.push({
              key: rule.key || rule.label,
              focusTarget: targets[emptyIndex] || rule.focusTarget,
              message: rule.message || `${rule.label || '입력 항목'}을 끝까지 입력해주세요.`,
            })
            return
          }

          // 자릿수 유효성 검사
          if (hasAnyInput && Array.isArray(rule.lengths)) {
            const invalidLengthIndex = values.findIndex((val, idx) => {
              const lengthRule = rule.lengths[idx]
              const valueLength = String(val ?? '').trim().length
              if (Array.isArray(lengthRule)) {
                const [min = 0, max = Infinity] = lengthRule
                return valueLength < min || valueLength > max
              }
              const minLength = Number(lengthRule || 0)
              return !!minLength && valueLength < minLength
            })

            if (invalidLengthIndex > -1) {
              const targets = Array.isArray(rule.focusTargets) ? rule.focusTargets : [rule.focusTarget]
              missed.push({
                key: rule.key || rule.label,
                focusTarget: targets[invalidLengthIndex] || rule.focusTarget,
                message: rule.message || `${rule.label || '입력 항목'}을 정확히 입력해주세요.`,
              })
            }
          }
        }
      })

      result = {
        valid: missed.length === 0,
        first: missed[0] || null,
        missed,
      }
    }

    if (!result || result.valid) return true

    const missed = result.missed || []
    const first = result.first || missed[0]

    showAlertWithId(
      getOption('alertId', 'optional-validation'),
      getMessage(missed),
      () => {
        focusFirstMissed(first)
      },
      getOption('subMessage', undefined),
    )

    return false
  }

  return {
    validateOptionalWithAlert,
  }
}
