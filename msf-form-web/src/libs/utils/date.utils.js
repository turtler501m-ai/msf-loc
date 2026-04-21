import { format } from 'date-fns'
import { isEmpty } from './string.utils'

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

  console.log('y:', y, 'm:', m, 'd:', d, 'v.length:', v.length)

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
    return new Date(result)
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
