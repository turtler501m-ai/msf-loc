import { format } from 'date-fns'
import { isEmpty } from './string.utils'

export const validateDateInput = (val, len = 6) => {
  const v = String(val)
  const y = Number(len) === 8 ? 4 : 2,
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
  return Number(len) === 8 ? validateDate(v) : validateSDate(v)
}

export const validateSDate = (str) => {
  const s = String(str)
  return validateDate((s.substring(0, 2) > '50' ? '19' : '20') + s)
}
export const validateDate = (str) => {
  if (!str) {
    return true
  }
  const s = String(str).replace(/[^0-9]/g, '')
  let y = parseInt(s.substring(0, 4), 10),
    m = parseInt(s.substring(4, 6), 10),
    d = parseInt(s.substring(6, 8), 10)

  if (m < 1 || m > 12) {
    return false
  }

  const date = new Date(y, m - 1, d)
  return date.getFullYear() === y && date.getMonth() === m - 1 && date.getDate() === d
}

// Date 객체로 변환 (변환 실패 시, 현재일자 Date 객체로 리턴)
export const toDate = (s) => {
  if (isEmpty(s)) {
    return null
  }
  const type = Object.prototype.toString.call(s).slice(8, -1)
  if (type === 'Date') {
    return new Date(s.getTime())
  }
  let result = Date.parse(s)
  if (!Number.isNaN(result)) {
    return new Date(result)
  }

  if (type === 'String') {
    const numStr = s.replace(/[^0-9]/g, '')
    return numStr.length < 4
      ? null
      : new Date(
          Number(numStr.substring(0, 4)),
          numStr.length < 6 ? Date.now.getMonth() : Number(numStr.substring(4, 6)) - 1,
          numStr.length < 8 ? Date.now.getDate() : Number(numStr.substring(6, 8)),
        )
  }
  return null
}

export const toTime = (s) => {
  if (isEmpty(s)) {
    return null
  }

  const type = Object.prototype.toString.call(s).slice(8, -1)
  if (type === 'Date') {
    return new Date(s.getTime())
  }
  let result = Date.parse(s)
  if (!Number.isNaN(result)) {
    return new Date(result)
  }

  if (type === 'String') {
    const numStr = s.replace(/[^0-9]/g, '')
    const hour = Number(numStr.substring(0, 2))
    const minutes = numStr.length < 4 ? 0 : Number(numStr.substring(2, 4))
    const seconds = numStr.length < 6 ? 0 : Number(numStr.substring(4, 6))
    const date = new Date()
    date.setHours(hour, minutes, seconds)
    return date
  }
  return null
}

export const toDatetime = (s) => {
  if (isEmpty(s)) {
    return null
  }
  const type = Object.prototype.toString.call(s).slice(8, -1)
  if (type === 'Date') {
    return new Date(s.getTime())
  }
  let result = Date.parse(s)
  if (!Number.isNaN(result)) {
    return new Date(result)
  }

  if (type === 'String') {
    const numStr = s.replace(/[^0-9]/g, '')
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

export const formatDate = (d, s = '-') => {
  const date = toDate(d)
  if (!date) {
    return null
  }
  return format(date, `yyyy${s}MM${s}dd`)
}

export const formatDatetimeMinutes = (d, ds = '-', ts = ':') => {
  const date = toDatetime(d)
  if (!date) {
    return null
  }
  return format(date, `yyyy${ds}MM${ds}dd HH${ts}mm`)
}

export const formatDatetime = (d, ds = '-', ts = ':') => {
  const date = toDatetime(d)
  if (!date) {
    return null
  }
  return format(date, `yyyy${ds}MM${ds}dd HH${ts}mm${ts}ss`)
}

export const formatTime = (t, showSeconds = true, s = ':') => {
  const time = toTime(t)
  if (!time) {
    return null
  }
  return format(time, 'HH' + s + 'mm' + (showSeconds ? s + `ss` : ''))
}
