export const isNull = (s) => {
  return s === undefined || s === null
}
export const isEmpty = (s) => {
  return isNull(s) || s === ''
}

export const formatCurrency = (n) => {
  return String(n).replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
}
