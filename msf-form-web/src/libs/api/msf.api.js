import axios from 'axios'
// import { useRouter } from 'vue-router'
import { useMsfUserStore } from '@/stores/msf_user'

axios.defaults.headers['Content-Type'] = 'application/json'
axios.defaults.headers['Accept'] = 'application/json'
axios.defaults.timeout = 5000

axios.interceptors.request.use(
  (config) => {
    const msfUserStore = useMsfUserStore()
    const token = msfUserStore.token
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    } else {
      /*
       * FIXME: Access Token이 없는 경우, Access Token 재발급 요청 로직 추가 필요
       */
      // if (!msfUserStore.isAuthenticated) {
      //   msfUserStore.clearUserInfo()
      //   useRouter().push('/login')
      // }
      delete config.headers.Authorization
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)

axios.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    if (error.response?.status === 401) {
      /*
       * FIXME: Access Token 재발급 요청에 대한 로직 추가 필요
       * Acccess Token 재발급 실패 시, 사용자 세션 초기화 및 로그인 페이지로 리다이렉트
       */
      // useMsfUserStore().clearUserInfo()
      // useRouter().push('/login')
    } else if (error.response?.status === 400) {
      return Promise.reject(error)
    } else if (error.response?.status === 403) {
      return Promise.reject(error)
    }
    return Promise.reject(error)
  },
)

const api = axios.create({
  baseURL: `${import.meta.env.VITE_MSF_API_URL}`,
})

export const post = async (url, params) => {
  return await api
    .post(url, params, {
      validateStatus: (status) => status >= 200 && status < 400,
    })
    .then((res) => res.data)
    .catch((err) =>
      err.response?.data ? err.response.data : { code: '9999', message: err.message },
    )
}
