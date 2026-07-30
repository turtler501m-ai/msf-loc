import { useMsfUserStore } from '@/stores/msf_user'

/**
 * 로그인 사용자 ID 조회
 *
 * @returns
 */
export const getLoginId = () => useMsfUserStore().getUserInfo()?.userId

/**
 * 로그인 사용자 이름 조회
 * @returns
 */
export const getLoginName = () => useMsfUserStore().getUserInfo()?.userName

/**
 * 사용자 인증 토큰 파싱
 * - 실패 시, null 반환
 *
 * @param {string} token 사용자 인증 토큰
 * @returns
 */
export const parseUserToken = (token) => {
  if (!token) return null

  try {
    const base64Url = token.split('.')[1]
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map(function (c) {
          return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
        })
        .join(''),
    )
    return JSON.parse(jsonPayload)
  } catch (error) {
    console.error('Failed to parse user token info:', error)
    return null
  }
}

export const isTokenExpired = (token) => {
  if (!token) {
    return true
  }
  const info = parseUserToken(token)
  const currentTime = Date.now() / 1000
  if (info.exp < currentTime) {
    return true
  }
  return false
}
