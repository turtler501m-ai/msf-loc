import axios from 'axios'
import { useMsfUserStore } from '@/stores/msf_user'
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

// ---------------------------------------------------------
// 동시성 제어를 위한 변수 선언
// ---------------------------------------------------------
let isTokenRefreshing = false // 현재 토큰 갱신 API가 실행 중인지 여부
let refreshSubscribers = [] // 토큰 갱신을 기다리는 API 요청들의 대기열(Queue)

// 대기 중인 요청들을 대기열에 추가하는 함수
const addRefreshSubscriber = (callback) => {
  refreshSubscribers.push(callback)
}

// 갱신이 완료되면 대기열의 요청들을 순차적으로 실행하고 큐를 비우는 함수
const onTokenRefreshed = (accessToken) => {
  refreshSubscribers.forEach((callback) => callback(accessToken))
  refreshSubscribers = []
}

// ---------------------------------------------------------
// 요청(Request) 인터셉터 설정
// ---------------------------------------------------------
api.interceptors.request.use(
  async (config) => {
    if (config.url.startsWith('/api/auth')) {
      return config
    }

    const msfUserStore = useMsfUserStore()
    if (!msfUserStore.token) {
      // 이미 다른 API 요청에 의해 토큰 갱신이 진행 중이라면?
      if (isTokenRefreshing) {
        // 현재 API 요청을 잠시 멈추고(Promise), 대기열에 콜백 함수를 밀어넣습니다.
        return new Promise((resolve) => {
          addRefreshSubscriber((newToken) => {
            config.headers.Authorization = `Bearer ${newToken}`
            resolve(config) // 토큰이 발급되면 그때 원래 요청을 재개합니다.
          })
        })
      }

      // 토큰 갱신이 진행 중이 아니라면, 갱신 상태를 true로 변경하고 갱신 시작
      isTokenRefreshing = true

      try {
        // 별도의 authClient를 사용하여 토큰 재발급 요청
        const response = await auth.post('/api/auth/refresh')

        // 성공적으로 새 토큰 발급
        if (response.data.code !== '0000') {
          refreshSubscribers = [] // 대기열 초기화
          msfUserStore.clearUserInfo()
          window.location.href = '/login' // 로그인 페이지로 이동
        }
        msfUserStore.setUserTokenInfo(response.data.data)

        // 대기열(Queue)에 쌓여있던 다른 요청들에게 새 토큰을 전달하여 일괄 처리
        onTokenRefreshed(msfUserStore.token)
      } catch (error) {
        // 리프레시 토큰마저 만료되었거나 에러가 발생한 경우 (강제 로그아웃 처리)
        refreshSubscribers = [] // 대기열 초기화
        window.location.href = '/login' // 로그인 페이지로 이동
        return Promise.reject(error)
      } finally {
        // 성공하든 실패하든 토큰 갱신 상태는 해제
        isTokenRefreshing = false
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

api.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    if (error.response?.status === 401) {
      window.location.href = '/login'
    } else if (error.response?.status === 400) {
      return Promise.reject(error)
    } else if (error.response?.status === 403) {
      return Promise.reject(error)
    } else if (error.response?.status === 404) {
      return Promise.reject(error)
    } else if (error.response?.status === 500) {
      return Promise.reject(error)
    }
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
    .post('/api/auth/refresh')
    .then((res) => res.data)
    .catch((err) =>
      err.response?.data ? err.response.data : { code: '9999', message: err.message },
    )
}
