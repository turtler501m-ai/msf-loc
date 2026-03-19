/**
 * 스마트서식지(MSF) API 공통 모듈
 * 백엔드 baseURL은 환경변수(VITE_API_BASE_URL) 또는 /api
 */

const BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api'

/**
 * 공통 fetch 래퍼
 * @param {string} path - API 경로 (앞에 / 제외)
 * @param {object} options - fetch options
 * @returns {Promise<object>}
 */
export async function msfFetch(path, options = {}) {
  const url = path.startsWith('http') ? path : `${BASE_URL}${path.startsWith('/') ? '' : '/'}${path}`
  const response = await fetch(url, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  })

  if (!response.ok) {
    const err = new Error(response.statusText || 'API 오류')
    err.status = response.status
    err.response = response
    const contentType = response.headers.get('content-type')
    if (contentType && contentType.includes('application/json')) {
      try {
        const data = await response.json()
        if (data && data.message) err.message = data.message
      } catch (_) {}
    }
    throw err
  }

  const contentType = response.headers.get('content-type')
  if (contentType && contentType.includes('application/json')) {
    return response.json()
  }
  return response.text()
}

/**
 * POST 요청
 */
export function msfPost(path, body) {
  return msfFetch(path, {
    method: 'POST',
    body: body ? JSON.stringify(body) : undefined,
  })
}

/**
 * GET 요청
 */
export function msfGet(path) {
  return msfFetch(path, { method: 'GET' })
}
