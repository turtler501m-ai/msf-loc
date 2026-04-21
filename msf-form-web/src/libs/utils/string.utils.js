/**
 * Null 여부 체크
 *
 * @param {string} str
 * @returns
 */
export const isNull = (str) => {
  return str === undefined || str === null
}
/**
 * Null 또는 빈 문자열 여부 체크
 * @param {string} s
 * @returns
 */
export const isEmpty = (str) => {
  return isNull(str) || str === ''
}
/**
 * 숫자를 금액 형식으로 변환 (3자리씩 ',' 포함)
 *
 * @param {number | string} num
 * @returns
 */
export const formatCurrency = (num) => {
  if (isNull(num)) {
    return null
  }
  return String(num).replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
}

/**
 * 휴대폰 번호 정합성 체크
 * - 체크할 값 형식: 00000000000 또는 000-0000-0000
 * - null 체크 포함 여부가 true이면 str 값이 null일 경우 false를 반환하고, false이면 str 값이 null이어도 true로 반환한다.
 *
 * @param {string} str 체크할 휴대폰 번호
 * @param {boolean} checkNull null 체크 포함 여부 (기본값: true)
 * @returns
 */
export const validateMobile = (str, checkNull = true) => {
  if (isNull(str)) {
    return checkNull ? false : true
  }

  return /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/.test(str)
}

/**
 * 전화번호 정합성 체크
 * - 체크할 값 형식: 일반번호 = 00000000000 또는 000-0000-0000, 대표번호 = 00000000 또는 0000-0000
 * - null 체크 포함 여부가 true이면 str 값이 null일 경우 false를 반환하고, false이면 str 값이 null이어도 true로 반환한다.
 * - 대표번호 포함 여부가 true이면 대표번호 체크를 포함하고, false이면 대표번호 체크를 제외(대표번호는 false로 반환)한다.
 *
 * @param {string} str 체크할 전화번호
 * @param {boolean} checkNull null 체크 포함 여부 (기본값: true)
 * @param {boolean} inMain 대표번호 포함 여부 (기본값: false)
 * @returns
 */
export const validateTel = (str, checkNull = true, inMain = false) => {
  if (isNull(str)) {
    return checkNull ? false : true
  }

  return (
    /^(01[016789]|02|0[3-9][0-9])-?([0-9]{3,4})-?([0-9]{4})$/.test(str) ||
    (inMain && /^(1[5468][0-9]{2})-?([0-9]{4})$/.test(str))
  )
}
