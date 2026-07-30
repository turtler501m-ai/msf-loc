import { differenceInCalendarDays, format } from 'date-fns'
import { isEmpty, validateResidentRegNo, validateForeignerRegNo } from './string.utils'

/**
 * 생년월일을 기준으로 만나이 계산
 *
 * @param {string} str 생년월일 (형식: 'yyMMdd' 또는 'yyyyMMdd')
 * @param {boolean} is21St 생년이 2000년대 여부 (기본값: false)
 * @returns {number} 만나이 (-1: 계산 실패, 0~ : 계산된 만나이)
 */
export const calcAgeFromBirth = (str, is21st = false) => {
  if (isEmpty(str)) {
    return -1
  }

  const today = new Date()
  const valid = validateDateInput(str, str.length)
  if (!valid) {
    return -1
  }

  let birth = str
  if (str.length === 6) {
    birth = (is21st ? '20' : '19') + str
  }

  const birthDate = toDate(birth)

  if (isNaN(birthDate.getTime())) {
    return -1
  }

  // 1. 단순히 연도 차이 계산
  let age = today.getFullYear() - birthDate.getFullYear()

  // 2. 월(Month) 차이 계산
  const monthDiff = today.getMonth() - birthDate.getMonth()

  // 3. 생일이 아직 지나지 않았는지 확인
  // - 현재 월이 생일 월보다 이르거나,
  // - 현재 월과 생일 월이 같지만 현재 일이 생일 일보다 이른 경우 -> 아직 생일 전이므로 1살 마이너스
  if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
    age--
  }

  // 만약 미래의 생년월일이 입력되었을 경우 음수가 나오는 것을 방지하기 위해 0 이하 보정
  return age < 0 ? -1 : age
}

/**
 * 주민번호/외국인번호를 기준으로 만나이 계산
 *
 * @param {string} str 주민번호/외국인번호 (형식: '0000000000000' 또는 '000000-0000000')
 * @returns {number} 만나이 (-1: 계산 실패, 0~ : 계산된 만나이)
 */
export const calcAgeFromRrn = (str) => {
  const valid = validateResidentRegNo(str) || validateForeignerRegNo(str)
  if (!valid) {
    return -1
  }
  const rrn = str.replace('-', '')
  const birth = rrn.substring(0, 6)
  const gender = rrn.charAt(6)
  return calcAgeFromBirth(birth, ['3', '4', '7', '8'].includes(gender))
}

/**
 * 6자리 또는 8자리 날짜 문자열 정합성 체크
 *
 * @param {string} value 날짜 문자열
 * @param {number} length 날짜 문자열의 길이 (기본값: 6)
 * @returns
 */
export const validateDateInput = (value, length = 6) => {
  const v = String(value)
  const y = Number(length) === 8 ? 4 : 2,
    m = y + 2,
    d = m + 2

  if (v.length <= y) {
    return true
  }
  if (v.length < m) {
    return /[01]/.test(v.charAt(y))
  }
  if (v.length === m) {
    return /(0[1-9]|1[0-2])/.test(v.substring(y, m))
  }
  if (v.length < d) {
    return /(01[0-3]|02[0-2]|0[3-9][0-3]|1[012][0-3])/.test(v.substring(y))
  }
  return Number(length) === 8 ? validateDate(v) : validateSDate(v)
}

/**
 * 6자리 날짜 문자열 정합성 체크
 *
 * @param {string} value
 * @returns
 */
export const validateSDate = (value) => {
  const s = String(value)
  return validateDate((s.substring(0, 2) > '50' ? '19' : '20') + s)
}

/**
 * 8자리 생년월일 문자열 정합성 체크
 *
 * @param {string} value
 * @returns
 */
export const validateBirthDate = (value) => {
  // 1. 기본 8자리 숫자 형식 체크
  const regex = /^\d{8}$/
  if (!regex.test(value)) return { isValid: false, msg: '숫자 8자리를 입력해 주세요.' }

  const year = parseInt(value.substring(0, 4), 10)
  const month = parseInt(value.substring(4, 6), 10)
  const day = parseInt(value.substring(6, 8), 10)

  // 2. 자바스크립트 Date 객체 변환 및 기본 날짜 존재 여부 체크
  const inputDate = new Date(year, month - 1, day)
  const isRealDate =
    inputDate.getFullYear() === year &&
    inputDate.getMonth() === month - 1 &&
    inputDate.getDate() === day

  if (!isRealDate) return { isValid: false, msg: '존재하지 않는 날짜입니다.' }

  // 3. ─── [여기서부터 한도 체크] ───
  const today = new Date()

  // [상한선] 오늘보다 미래인 경우
  if (inputDate > today) {
    return { isValid: false, msg: '미래의 날짜는 입력할 수 없습니다.' }
  }

  // [하한선] 지나치게 과거인 경우 (예: 1900년 이전)
  const minYear = 1900
  if (year < minYear) {
    return { isValid: false, msg: `${minYear}년 이후 출생자만 입력 가능합니다.` }
  }

  // 모든 조건 통과
  return { isValid: true, msg: '' }
}

/**
 * 8자리 날짜 문자열 정합성 체크
 *
 * @param {string} value
 * @returns
 */
export const validateDate = (value) => {
  if (!value) {
    return true
  }
  const s = String(value).replace(/[^0-9]/g, '')
  let y = parseInt(s.substring(0, 4), 10),
    m = parseInt(s.substring(4, 6), 10),
    d = parseInt(s.substring(6, 8), 10)

  if (m < 1 || m > 12) {
    return false
  }

  const date = new Date(y, m - 1, d)
  return date.getFullYear() === y && date.getMonth() === m - 1 && date.getDate() === d
}

/**
 * 날짜 문자열을 Date 객체로 변환
 * - 변환 실패 시, null 반환
 *
 * @param {string | Date} str 날짜 문자열
 * @returns
 */
export const toDate = (str) => {
  if (isEmpty(str)) {
    return null
  }
  const type = Object.prototype.toString.call(str).slice(8, -1)
  if (type === 'Date') {
    return new Date(str.getTime())
  }
  let result = Date.parse(str)
  if (!Number.isNaN(result)) {
    const d = new Date(result)
    if (!Number.isNaN(d.getTime())) {
      return d
    }
  }

  if (type === 'String') {
    const numStr = str.replace(/[^0-9]/g, '')
    return numStr.length < 4
      ? null
      : new Date(
          Number(numStr.substring(0, 4)),
          numStr.length < 6 ? new Date().getMonth() : Number(numStr.substring(4, 6)) - 1,
          numStr.length < 8 ? new Date().getDate() : Number(numStr.substring(6, 8)),
        )
  }
  return null
}
/**
 * 시간 문자열을 Date 객체로 변환
 * - 반환된 Date 객체의 날짜는 현재날짜로 설정
 * - 변환 실패 시, null 반환
 *
 * @param {string | Date} str 시간 문자열
 * @returns
 */
export const toTime = (str) => {
  if (isEmpty(str)) {
    return null
  }

  const type = Object.prototype.toString.call(str).slice(8, -1)
  if (type === 'Date') {
    return new Date(str.getTime())
  }
  let result = Date.parse(str)
  if (!Number.isNaN(result)) {
    return new Date(result)
  }

  if (type === 'String') {
    const numStr = str.replace(/[^0-9]/g, '')
    const hour = Number(numStr.substring(0, 2))
    const minutes = numStr.length < 4 ? 0 : Number(numStr.substring(2, 4))
    const seconds = numStr.length < 6 ? 0 : Number(numStr.substring(4, 6))
    const date = new Date()
    date.setHours(hour, minutes, seconds)
    return date
  }
  return null
}
/**
 * 날짜/시간 문자열을 Date객체로 반환
 * - 변환 실패 시, null 반환
 *
 * @param {string | Date} str 날짜/시간 문자열
 * @returns
 */
export const toDatetime = (str) => {
  if (isEmpty(str)) {
    return null
  }
  const type = Object.prototype.toString.call(str).slice(8, -1)
  if (type === 'Date') {
    return new Date(str.getTime())
  }
  let result = Date.parse(str)
  if (!Number.isNaN(result)) {
    return new Date(result)
  }

  if (type === 'String') {
    const numStr = str.replace(/[^0-9]/g, '')
    return numStr.length < 4
      ? null
      : new Date(
          Number(numStr.substring(0, 4)),
          numStr.length < 6 ? 0 : Number(numStr.substring(4, 6)) - 1,
          numStr.length < 8 ? 1 : Number(numStr.substring(6, 8)),
          numStr.length < 10 ? 0 : Number(numStr.substring(8, 10)),
          numStr.length < 12 ? 0 : Number(numStr.substring(10, 12)),
          numStr.length < 14 ? 0 : Number(numStr.substring(12, 14)),
        )
  }
  return null
}
/**
 * 날짜 객체를 날짜 문자열로 변환
 * - 변환 실패 시, null 반환
 *
 * @param {Date | string} date 날짜 객체
 * @param {string} seperator 구분자 (기본값: '-')
 * @returns
 */
export const formatDate = (date, seperator = '-') => {
  const dt = toDate(date)
  if (!dt) {
    return null
  }
  return format(dt, `yyyy${seperator}MM${seperator}dd`)
}

/**
 * 날짜 객체를 날짜/사간 문자열로 변환
 * - 시간을 분(Minute)까지만 표시
 * - 변환 실패 시, null 반환
 *
 * @param {Date | string} date 날짜 객체
 * @param {string} dateSeperator 날짜 구분자 (기본값: '-')
 * @param {string} timeSeperator 시간 구분자 (기본값: ':')
 * @returns
 */
export const formatDatetimeMinutes = (date, dateSeperator = '-', timeSeperator = ':') => {
  const dt = toDatetime(date)
  if (!dt) {
    return null
  }
  return format(dt, `yyyy${dateSeperator}MM${dateSeperator}dd HH${timeSeperator}mm`)
}
/**
 * 날짜 객체를 날짜/시간 문자열로 변환
 * - 시간을 초(Second)까지 표시
 * - 변환 실패 시, null 반환
 *
 * @param {Date | string} date 날짜 객체
 * @param {string} dateSeperator 날짜 구분자 (기본값: '-')
 * @param {string} timeSeperator 시간 구분자 (기본값: ':')
 * @returns
 */
export const formatDatetime = (date, dateSeperator = '-', timeSeperator = ':') => {
  const dt = toDatetime(date)
  if (!dt) {
    return null
  }
  return format(
    dt,
    `yyyy${dateSeperator}MM${dateSeperator}dd HH${timeSeperator}mm${timeSeperator}ss`,
  )
}

/**
 * 날짜 객체를 LocalDateTime 문자열로 변환 (ISO 8601: yyyy-MM-ddTHH:mm:ss)
 * - 변환 실패 시, null 반환
 *
 * @param {Date | string} date 날짜 객체
 * @returns {string | null}
 */
export const formatLocalDateTime = (date) => {
  const dt = toDatetime(date)
  if (!dt) {
    return null
  }
  return format(dt, "yyyy-MM-dd'T'HH:mm:ss")
}

/**
 * 날짜 객체를 시간 문자열로 변환
 * - 변환 실패 시, null 반환
 *
 * @param {Date | string} time 날짜 객체
 * @param {string} showSeconds 초(Second) 포함여부 (기본값: true)
 * @param {string} seperator 구분자 (기본값: ':')
 * @returns
 */
export const formatTime = (time, showSeconds = true, seperator = ':') => {
  const tm = toTime(time)
  if (!tm) {
    return null
  }
  return format(tm, 'HH' + seperator + 'mm' + (showSeconds ? seperator + `ss` : ''))
}

/**
 * 기준 날짜와 비교 날짜의 차이 비교
 * - 변환 실패 시, null 반환
 * - 기준 날짜가 비교 날짜보다 이전일 경우, 음수 반환
 *
 * @param {Date | string} source 기준 날짜
 * @param {Date | string} target 비교 날짜
 * @return {number} 일수 차이 값
 */
export const diffDays = (source, target) => {
  if (!source || !target) {
    return null
  }
  const s = toDate(source)
  const t = toDate(target)
  if (!s || !t) {
    return null
  }

  if (s < t) {
    const d = differenceInCalendarDays(t, s)
    return d * -1
  }
  return differenceInCalendarDays(s, t)
}

/**
 * 입력된 날짜(기본값 현재시간)를 한국 표준시(KST, Asia/Seoul) 기준의 Date 객체로 변환하여 반환
 * @param {Date} [date]
 * @returns {Date}
 */
export const getKstDate = (date = new Date()) => {
  const kstString = date.toLocaleString('en-US', { timeZone: 'Asia/Seoul' })
  return new Date(kstString)
}

/**
 * 현재 시간이 지정된 시간 범위 내에 있는지 확인
 * @param {string} startTime 'HH:mm' 형식
 * @param {string} endTime 'HH:mm' 형식
 * @param {Date} [targetDate] 비교할 날짜 객체 (기본값: 현재 KST 시간)
 * @returns {boolean}
 */
export const isTimeBetween = (startTime, endTime, targetDate = getKstDate()) => {
  const [startHour, startMin] = startTime.split(':').map(Number)
  const [endHour, endMin] = endTime.split(':').map(Number)

  const start = new Date(targetDate)
  start.setHours(startHour, startMin, 0, 0)

  const end = new Date(targetDate)
  end.setHours(endHour, endMin, 0, 0)

  return targetDate >= start && targetDate <= end
}

/**
 * 신규개통 가능 시간 체크 (08:00 ~ 21:50)
 * @param {Date} [date]
 * @returns {boolean}
 */
export const checkNewJoinTime = (date = getKstDate()) => {
  return isTimeBetween('08:00', '21:50', getKstDate(date))
}

/**
 * 번호이동 가능 시간 체크 (10:00 ~ 19:50, 일요일, 신정/설/추석 당일 제외)
 * @param {Date} [date]
 * @returns {boolean}
 */
export const checkMnpJoinTime = (date = getKstDate()) => {
  const kstDate = getKstDate(date)
  const day = kstDate.getDay() // 0: 일요일, 6: 토요일
  // 일요일 불가
  if (day === 0) {
    return false
  }
  return isTimeBetween('10:00', '19:50', kstDate)
}

/**
 * 주민번호에서 8자리 생년월일을 추출
 *
 * @param {string} str 주민번호/외국인번호
 * @returns {string} 8자리 생년월일
 */
export const generateBirthDateByRrn = (str) => {
  if (isEmpty(str) || str.length < 7) {
    return ''
  }
  const b6 = str.substring(0, 6)
  const g = str.charAt(6)
  return (['3', '4', '7', '8'].includes(g) ? '20' : '19') + b6
}
