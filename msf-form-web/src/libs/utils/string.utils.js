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
  if (isEmpty(num)) {
    return ''
  }
  const str = String(num).replace('[^0-9.]', '')
  const number = Number(str)
  return number.toLocaleString()
}

/**
 * 이름 마스킹
 * - 홍길 -> 홍*
 * - 홍길동 -> 홍*동
 * - 남궁길동 -> 남**동
 *
 * @param {string} str
 * @returns
 */
export const maskingName = (str) => {
  if (isEmpty(str)) return ''

  if (str.length === 2) {
    // 2글자: 맨 마지막 한 글자를 '*'로 치환
    return str.replace(/.$/u, '*')
  }

  return str.replace(/(?<=^.).+(?=.$)/u, (match) => '*'.repeat(match.length))
}

/**
 * 휴대폰 번호 마스킹
 * - 010-1234-5678 -> 010-****-5678
 *
 * @param {string} str
 * @returns
 */
export const maskingMobile = (str) => {
  if (isEmpty(str)) return ''

  return str.replace(/-(\d{3,4})-/g, (_, p1) => '-' + '*'.repeat(p1.length) + '-')
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

  return /^01([0|1|6|7|8|9])-?([0-9]{4})-?([0-9]{4})$/.test(str)
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

/**
 * 전화번호에서 숫자만 추출
 * - "010-1234-5678" → "01012345678"
 *
 * @param {string} value
 * @returns {string}
 */
export const normalizePhone = (value) => String(value || '').replace(/\D/g, '')

/**
 * 문자열에서 숫자만 추출
 * - "2025-12-13" → "20251213"
 *
 * @param {string} value
 * @returns {string}
 */
export const extractDigits = (value) => String(value || '').replace(/\D/g, '')

/**
 * 휴대폰번호 유효성 체크 (01X 10~11자리, 하이픈 포함 raw 입력도 허용)
 * - "010-1234-5678" → true, "01012345678" → true
 *
 * @param {string} value
 * @returns {boolean}
 */
export const isValidMobileNumber = (value) => /^01\d{8,9}$/.test(normalizePhone(value))

/**
 * 비밀번호 정합성 체크
 * - 10~15자 이상일 정합성 체크
 * - 영문, 숫자, 특수문자 3가지가 모두 포함된 10~15자 이상일 경우 true 반환, 그렇지 않으면 false 반환
 * - 연속된 숫자 4자리 이상(예: 1234, 4321)이 포함된 경우 false 반환
 *
 * @param {string} str 체크할 비밀번호
 * @returns
 */
export const validatePassword = (str) => {
  if (isNull(str)) {
    return false
  }

  const passwordPattern =
    /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^*+=-])(?!.*(\d)\1\1\1)[a-zA-Z\d!@#$%^*+=-]{10,15}$/
  if (!passwordPattern.test(str)) {
    return false
  }

  for (let i = 0; i < str.length - 3; i++) {
    const char1 = str.charCodeAt(i)
    const char2 = str.charCodeAt(i + 1)
    const char3 = str.charCodeAt(i + 2)
    const char4 = str.charCodeAt(i + 3)

    // 모두 숫자 문자인지 확인
    if (
      char1 >= 48 &&
      char1 <= 57 &&
      char2 >= 48 &&
      char2 <= 57 &&
      char3 >= 48 &&
      char3 <= 57 &&
      char4 >= 48 &&
      char4 <= 57
    ) {
      // 1씩 증가하는 연속 숫자 패턴 (예: 1234)
      if (char2 - char1 === 1 && char3 - char2 === 1 && char4 - char3 === 1) {
        return false
      }
      // 1씩 감소하는 연속 숫자 패턴 (예: 4321)
      if (char2 - char1 === -1 && char3 - char2 === -1 && char4 - char3 === -1) {
        return false
      }
    }
  }
  return true
}

/**
 * 이메일 주소 유효성 체크
 *
 * @param {string} email 체크할 이메일 주소
 * @param {boolean} checkNull null 값 체크 여부
 * @returns {boolean} 유효성 결과
 */
export const validateEmail = (email, checkNull = false) => {
  if (isNull(email)) {
    return checkNull ? false : true
  }

  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
  return emailPattern.test(email)
}

/**
 * 주민등록번호 유효성 체크
 *
 * @param {string} rrn 체크할 주민등록번호
 * @param {boolean} checkNull null 값 체크 여부
 * @returns {boolean} 유효성 결과
 */
export const validateResidentRegNo = (rrn, checkNull = false) => {
  if (isNull(rrn)) {
    return checkNull ? false : true
  }

  const rrnPattern = /^\d{6}-?[1234]\d{6}$/
  if (!rrnPattern.test(rrn)) return false

  return true

  // 2. 검증 번호 계산 (공식 적용)
  // const arrRrn = rrn.replace('-', '').split('').map(Number)
  // const multiplier = [2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5]

  // let sum = 0
  // for (let i = 0; i < 12; i++) {
  //   sum += arrRrn[i] * multiplier[i]
  // }

  // const remainder = (11 - (sum % 11)) % 10

  // // 마지막 번호와 일치하는지 확인
  // return remainder === arrRrn[12]
}

/**
 * 외국인등록번호 유효성 체크
 *
 * @param {string} frn 체크할 외국인등록번호
 * @param {boolean} checkNull null 값 체크 여부
 * @returns {boolean} 유효성 결과
 */
export const validateForeignerRegNo = (frn, checkNull = false) => {
  if (isNull(frn)) {
    return checkNull ? false : true
  }

  const frnPattern = /^\d{6}-?[5678]\d{6}$/
  if (!frnPattern.test(frn)) return false

  return true

  // 2. 검증 번호 계산 (공식 적용)
  // const arrFrn = frn.replace('-', '').split('').map(Number)
  // const multiplier = [2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5]

  // let sum = 0
  // for (let i = 0; i < 12; i++) {
  //   sum += arrFrn[i] * multiplier[i]
  // }

  // const remainder = (11 - (sum % 11)) % 10

  // // 마지막 번호와 일치하는지 확인
  // return remainder === arrFrn[12]
}

/**
 * 법인등록번호 유효성 체크
 *
 * @param {string} crn 체크할 법인등록번호
 * @param {boolean} checkNull null 값 체크 여부
 * @returns {boolean} 유효성 결과
 */
export const validateCorporateRegNo = (crn, checkNull = false) => {
  if (isNull(crn)) {
    return checkNull ? false : true
  }

  const crnPattern = /^\d{6}-?\d{7}$/
  if (!crnPattern.test(crn)) return false

  return true

  // const arrCrn = crn.replace('-', '').split('').map(Number)
  // const checksum = arrCrn.pop()

  // const sum =
  //   [1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2]
  //     .map((n, i) => n * arrCrn[i])
  //     .reduce((sum, n) => {
  //       sum += n
  //       return sum
  //     }, 0) % 10

  // return sum === (10 - checksum) % 10
}

/**
 * 사업자등록번호 유효성 체크
 *
 * @param {string} bizNo 체크할 사업자등록번호
 * @param {boolean} checkNull null 값 체크 여부
 * @returns {boolean} 유효성 결과
 */
export const validateBizRegNo = (bizNo, checkNull = false) => {
  if (isNull(bizNo)) {
    return checkNull ? false : true
  }

  const bizNoPattern = /^\d{3}-?\d{2}-?\d{5}$/
  if (!bizNoPattern.test(bizNo)) return false

  return true

  // const arrBizNo = bizNo.replace(/-/g, '').split('').map(Number)
  // const checksum = arrBizNo.pop()

  // const sum =
  //   [1, 3, 7, 1, 3, 7, 1, 3, 5]
  //     .map((n, i) => n * arrBizNo[i])
  //     .reduce((sum, n) => {
  //       sum += n
  //       return sum
  //     }, 0) + Math.floor((arrBizNo[8] * 5) / 10)

  // return (10 - (sum % 10)) % 10 === checksum
}

/**
 * 전화번호 형식 변환
 * - 하이픈이 이미 포함되어 있거나 변환 실패 시 원본 문자열 반환
 *
 * @param {string} telephone 전화번호 문자열
 * @param {string} separator 구분자 (기본값: '-')
 * @returns {string} 변환된 전화번호 또는 원본 문자열
 */
export const formatTelephone = (telephone, separator = '-') => {
  // 값이 없거나 타입이 문자열이 아니면, 원본 반환
  if (!telephone || typeof telephone !== 'string') return telephone
  /// 이미 하이픈(또는 지정된 구분자)이 포함되어 있다면 그대로 반환
  if (telephone.includes('-') || telephone.includes(separator)) return telephone

  // 숫자와 마스킹 문자(*)만 남기고 정제
  const cleaned = telephone.replace(/[^0-9*]/g, '')
  const len = cleaned.length

  // 8자리: 대표번호 (1588**** -> 1588-****)
  if (len === 8) {
    return cleaned.replace(/^([0-9*]{4})([0-9*]{4})$/, `$1${separator}$2`)
  }

  // 9자리: 서울 지역번호 (02***4567 -> 02-***-4567)
  if (len === 9 && cleaned.startsWith('02')) {
    return cleaned.replace(/^([0-9*]{2})([0-9*]{3})([0-9*]{4})$/, `$1${separator}$2${separator}$3`)
  }

  // 10자리: 서울(02-****-5678) vs 기타(011-***-4567, 031-***-4567)
  if (len === 10) {
    if (cleaned.startsWith('02')) {
      return cleaned.replace(
        /^([0-9*]{2})([0-9*]{4})([0-9*]{4})$/,
        `$1${separator}$2${separator}$3`,
      )
    }
    return cleaned.replace(/^([0-9*]{3})([0-9*]{3})([0-9*]{4})$/, `$1${separator}$2${separator}$3`)
  }

  // 11자리: 휴대폰 및 일반 지역번호 (010-****-5678, 031-****-1234)
  if (len === 11) {
    return cleaned.replace(/^([0-9*]{3})([0-9*]{4})([0-9*]{4})$/, `$1${separator}$2${separator}$3`)
  }

  // 12자리: 안심번호/평생번호 (0505****1234 -> 0505-****-1234)
  if (len === 12) {
    return cleaned.replace(/^([0-9*]{4})([0-9*]{4})([0-9*]{4})$/, `$1${separator}$2${separator}$3`)
  }

  // 변환 실패 시 원본 값 반환
  return telephone
}

/**
 * 주민 번호 YYYYMMDD 생년월일로 변환
 *
 * @param {string} rrn 주민 번호
 * @returns {string} YYYYMMDD 생년월일 반환
 */
export function extractYYYYMMDDRrn(rrn) {
  // 1. 하이픈(-)이 있다면 제거해서 13자리 숫자로 통일
  const cleanRRN = rrn?.replace(/-/g, '')

  // 안전장치: 자릿수가 부족하거나 숫자가 아니면 빈 문자열 반환
  if (cleanRRN.length < 7 || isNaN(cleanRRN.substring(0, 7))) {
    return
  }

  const birth6 = cleanRRN.substring(0, 6) // 앞 6자리 (YYMMDD)
  const genderCode = cleanRRN.charAt(6) // 7번째 자리 (성별 코드)

  // 2. 성별 코드에 따른 연도 접두사(19 또는 20) 판별
  // 1, 2 (내국인 1900년대) / 5, 6 (외국인 1900년대)
  const is1900s = ['1', '2', '5', '6'].includes(genderCode)
  const yearPrefix = is1900s ? '19' : '20'

  return yearPrefix + birth6
}

/**
 * 주민 번호 뒷자리로 성별 추출
 *
 * @param {string} backId 주민 번호 뒷자리
 * @returns {string} 성별 반환
 */
export const getGenderByRrnBack = (backId) => {
  // 예외 처리: 값이 없거나 비어있으면 빈 문자열 반환
  if (!backId) return ''

  const firstDigit = backId.charAt(0)

  // 홀수(1, 3, 5)는 남성, 짝수(2, 4, 6)는 여성
  // (9와 0은 1800년대생)
  if (['1', '3', '5', '7'].includes(firstDigit)) {
    return 'M' // 혹은 '남성'
  } else if (['2', '4', '6', '8'].includes(firstDigit)) {
    return 'F' // 혹은 '여성'
  }

  return null // 잘못된 값이 들어왔을 때
}

/**
 * YYYYMMDD 날짜 유효성 및 성인 여부(만 19세 이상) 검증
 * @param {string} birthDateStr - 'YYYYMMDD' 형태의 8자리 문자열
 * @returns {{ isValidDate: boolean, isAdult: boolean, message: string }}
 */
export const checkBirthAndAdult = (birthDateStr) => {
  // 1. 기본 형식 검증 (숫자 8자리)
  if (!/^\d{8}$/.test(birthDateStr)) {
    return { isValidDate: false, isAdult: false, message: '8자리 숫자로 입력해주세요.' }
  }

  const year = parseInt(birthDateStr.substring(0, 4), 10)
  const month = parseInt(birthDateStr.substring(4, 6), 10)
  const day = parseInt(birthDateStr.substring(6, 8), 10)

  // 2. 실제 존재하는 날짜인지 검증 (Javascript Date 넘침 현상 방지)
  // JS Date는 월이 0부터 시작하므로 month - 1
  const dateObj = new Date(year, month - 1, day)

  if (
    dateObj.getFullYear() !== year ||
    dateObj.getMonth() + 1 !== month ||
    dateObj.getDate() !== day
  ) {
    return { isValidDate: false, isAdult: false, message: '유효하지 않은 날짜입니다.' }
  }

  // 미래 날짜 입력 방지
  const today = new Date()
  if (dateObj > today) {
    return { isValidDate: false, isAdult: false, message: '미래 날짜는 입력할 수 없습니다.' }
  }

  // 3. 만 19세 이상(성인) 계산
  // 생일이 지났는지 정확히 확인
  let age = today.getFullYear() - year
  const monthDiff = today.getMonth() + 1 - month
  const dayDiff = today.getDate() - day

  // 생일이 아직 안 지났다면 만 나이 -1
  if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
    age--
  }

  const isAdult = age >= 19 // 연령 기준에 따라 만 18세/19세 조정 가능

  return {
    isValidDate: true,
    isAdult: isAdult,
    message: isAdult ? '성인 인증 완료' : '만 19세 미만은 이용할 수 없습니다.',
  }
}

/**
 * 문자열 합산
 *
 * @param {Array, string} arr 합치는 문자열 리스트, 구분자
 * @returns {string} 합친 문자열 반환
 */
export const concatStrings = (arr, separator = '') => {
  if (!Array.isArray(arr)) return ''
  return arr.filter(Boolean).join(separator)
}

export const generateUUID = () => {
  return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    const r = (Math.random() * 16) | 0
    const v = c === 'x' ? r : (r & 0x3) | 0x8
    return v.toString(16)
  })
}
