import axios from 'axios'
import { useMsfUserStore } from '@/stores/msf_user'
import { isTokenExpired } from '@/libs/utils/auth.utils'
import { showAlert } from '@/libs/utils/comp.utils'

axios.defaults.headers['Content-Type'] = 'application/json'
axios.defaults.headers['Accept'] = 'application/json'
axios.defaults.withCredentials = true

const api = axios.create({
  baseURL: `${import.meta.env.VITE_MSF_API_URL}`,
  timeout: import.meta.env.VITE_MSF_API_URL.indexOf('localhost:') > -1 ? 600000 : 5000,
})

const auth = axios.create({
  baseURL: `${import.meta.env.VITE_MSF_API_URL}`,
})

// =========================================================================
// 동시성 제어를 위한 전역 상태 및 큐(Queue) 로직
// =========================================================================
let isRefreshing = false // 현재 토큰 갱신 진행 여부
let refreshQueue = [] // 대기열 (Promise의 resolve, reject를 보관)

// 대기열에 쌓인 요청들을 일괄 처리하는 함수
const processQueue = (error, token = null) => {
  refreshQueue.forEach((prom) => {
    if (error) {
      prom.reject(error)
    } else {
      prom.resolve(token)
    }
  })
  refreshQueue = [] // 처리 후 큐 초기화
}

// ---------------------------------------------------------
// 요청(Request) 인터셉터 설정
// ---------------------------------------------------------
api.interceptors.request.use(
  async (config) => {
    if (config.url.startsWith('/api/n/')) {
      return config
    }

    const msfUserStore = useMsfUserStore()
    // 토큰이 없거나, 이미 만료된 상태라면 갱신 로직 진입
    if (isTokenExpired(msfUserStore.token)) {
      // 이미 다른 API가 갱신을 진행 중이라면 대기열에 탑승
      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          refreshQueue.push({ resolve, reject })
        })
          .then((newToken) => {
            // 갱신 완료 후 새 토큰을 받아 헤더에 교체하고 원래 요청 진행
            config.headers.Authorization = `Bearer ${newToken}`
            return config
          })
          .catch((err) => Promise.reject(err))
      }

      // 갱신 진행 중이 아니라면 직접 갱신 시작
      isRefreshing = true

      try {
        const response = await auth.post('/api/n/auth/refresh')
        if (response.data.code !== '0000') {
          refreshQueue = [] // 대기열 초기화
          msfUserStore.clearUserInfo()
          window.location.href = '/login' // 로그인 페이지로 이동
        }
        // 스토리지에 새 토큰 저장 (구현된 로직에 따라 적용)
        msfUserStore.setUserTokenInfo(response.data.data)

        // 대기열의 다른 요청들에게 새 토큰 발급 완료 알림
        processQueue(null, msfUserStore.token)
      } catch (error) {
        // 갱신 실패 (리프레시 토큰 만료 등)
        processQueue(error, null)
        msfUserStore.clearUserInfo()
        window.location.href = '/login'
        return Promise.reject(error)
      } finally {
        isRefreshing = false
      }
    }

    // 최종적으로 액세스 토큰을 헤더에 담아서 요청 진행
    config.headers.Authorization = `Bearer ${msfUserStore.token}`
    return config
  },
  (error) => {
    console.log('axios.interceptors.request.use.error:', error)
    return Promise.reject(error)
  },
)

// =========================================================================
// 2. 응답 (Response) 인터셉터 : 사후 처리 (안전망)
// =========================================================================
api.interceptors.response.use(
  (response) => {
    return response
  },
  async (error) => {
    // error.config 에는 이전에 실패했던 요청의 모든 정보(url, method, data 등)가 들어있습니다.
    const originalRequest = error.config

    // HTTP 상태 코드가 401(토큰 만료)이고, 한 번도 재시도한 적이 없는 요청인지 확인 (_retry 플래그)
    if (error.response?.status === 401 && !originalRequest._retry) {
      const msfUserStore = useMsfUserStore()

      // 이미 갱신 중이라면 대기열에 탑승
      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          refreshQueue.push({ resolve, reject })
        })
          .then((newToken) => {
            originalRequest.headers.Authorization = `Bearer ${newToken}`
            return api(originalRequest) // 설정(config)을 들고 다시 API 호출
          })
          .catch((err) => Promise.reject(err))
      }

      // 무한 루프 방지용 플래그
      originalRequest._retry = true
      isRefreshing = true

      try {
        const response = await auth.post('/api/n/auth/refresh')
        if (response.data.code !== '0000') {
          refreshQueue = [] // 대기열 초기화
          msfUserStore.clearUserInfo()
          window.location.href = '/login' // 로그인 페이지로 이동
        }
        // 스토리지에 새 토큰 저장 (구현된 로직에 따라 적용)
        msfUserStore.setUserTokenInfo(response.data.data)

        // 대기열 처리
        processQueue(null, msfUserStore.token)

        // 방금 401 에러가 났던 원래 요청을 새 토큰으로 재실행
        originalRequest.headers.Authorization = `Bearer ${msfUserStore.token}`
        return api(originalRequest)
      } catch (refreshError) {
        processQueue(refreshError, null)
        msfUserStore.clearUserInfo()
        window.location.href = '/login'
        return Promise.reject(refreshError)
      } finally {
        isRefreshing = false
      }
    }

    // 401 에러가 아니거나, 이미 재시도를 했던 요청이면 그대로 에러 반환
    return Promise.reject(error)
  },
)

export const post = async (url, params, config = {}) => {
  const isBlob = config.responseType === 'blob'

  return await api
    .post(url, params, {
      validateStatus: (status) => status >= 200 && status < 400,
      headers:
        params instanceof FormData
          ? { 'Content-Type': undefined }
          : { 'Content-Type': 'application/json' },
      ...config,
    })
    .then((res) => {
      if (isBlob) {
        return res
      }
      const resData = res.data

      // 1. 백엔드 시스템 에러 체크
      if (resData.code !== '0000') {
        showAlert(resData.message || '시스템 오류가 발생했습니다.')
        return resData
      }

      // 2. 비즈니스 에러 체크 (resCode가 존재하고 '0000'이 아닌 경우)
      if (resData.data?.resCode === '0000') {
        if (resData.data.resMessage) showAlert(resData.data.resMessage)
      } else if (resData.data?.resCode && resData.data.resCode !== '0000') {
        showAlert(resData.data.resMessage || '업무 처리 중 오류가 발생했습니다.')
      }

      return resData
    })
    .catch((err) => {
      const resData = err.response?.data
      if (resData?.code !== '0000') {
        showAlert(resData?.message || '시스템 오류가 발생했습니다.')
      }
      return err.response?.data ? err.response.data : { code: '9999', message: err.message }
    })
}

export const refreshToken = async () => {
  return await auth
    .post('/api/n/auth/refresh')
    .then((res) => res.data)
    .catch((err) =>
      err.response?.data ? err.response.data : { code: '9999', message: err.message },
    )
}
