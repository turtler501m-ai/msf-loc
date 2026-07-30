import { createRouter, createWebHistory } from 'vue-router'
import { useMsfStepStore } from '@/stores/msf_step'
import { showConfirmAsync } from '@/libs/utils/comp.utils'
import { useMsfUserStore } from '@/stores/msf_user'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange'
import { useMsfFormOwnChgStore } from '@/stores/msf_ownerChange'
import { getEnvName } from '@/libs/utils/env.utils'

// 레이아웃
import MsfBaseLayout from '@/layouts/MsfBaseLayout.vue'
// 메인 View
import MsfMainView from '@/views/MsfMainView.vue'
// 404 View
import MsfNotFoundView from '@/views/MsfNotFoundView.vue'
// APP 다운로드 안내
import MsfAppDownloadView from '@/views/MsfAppDownloadView.vue'
// 로그인
import MsfLoginView from '@/views/MsfLoginView.vue'
// 신청서
import MsfFormView from '@/views/MsfFormView.vue'
// 단말 사용 인증
import MsfDeviceAuthView from '@/views/MsfDeviceAuthView.vue'
// 단말 사용 등록
import MsfDeviceRegisterView from '@/views/MsfDeviceRegisterView.vue'
// 비밀번호 변경
import MsfPwChangeView from '@/views/MsfPwChangeView.vue'
// 설정
import MsfSettingView from '@/views/MsfSettingView.vue'

// 퍼블리싱 샘플
import GuideView from '@/views/guide/GuideView.vue'
import PubGuide from '@/views/guide/PubGuide.vue'
import PubList from '@/views/guide/PubList.vue'
import PubPage from '@/views/guide/PubPage.vue'
import FormCommonGuideView from '@/views/guide/FormCommonGuideView.vue'
import FormGuideView from '@/views/guide/FormGuideView.vue'
import GridGuideView from '@/views/guide/GridGuideView.vue'
// 개발 샘플
import DevGuide from '@/views/guide/DevGuide.vue'
import DeviceGuideView from '@/views/guide/DeviceGuideView.vue'

const DEFAULT_TITLE = 'SMART 신청서'
const ENV_NAME = getEnvName()

const router = createRouter({
  scrollBehavior: () => ({ y: 0 }),
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: MsfBaseLayout, // 레이아웃 전용 부모
      children: [
        { path: '', component: MsfMainView },
        { path: 'main', component: MsfMainView },
        {
          path: 'form/:domain',
          name: 'form',
          component: MsfFormView,
        },
        {
          path: 'extra/:pathes+',
          name: 'extra',
          component: () => import('@/views/MsfExtraView.vue'),
        },
        {
          path: '/setting',
          name: 'setting',
          component: MsfSettingView, // 설정
        },
      ],
    },
    {
      path: '/download',
      name: 'download',
      meta: { skipAuth: true },
      component: MsfAppDownloadView, // 단말 사용 인증
    },
    {
      path: '/login',
      name: 'Login',
      meta: { skipAuth: true },
      component: MsfLoginView, // 로그인
    },
    {
      path: '/deviceAuth',
      name: 'deviceAuth',
      meta: { skipAuth: true },
      component: MsfDeviceAuthView, // 단말 사용 인증
    },
    {
      path: '/deviceRegist',
      name: 'deviceRegist',
      meta: { skipAuth: true },
      component: MsfDeviceRegisterView, // 단말 사용 등록
    },
    {
      path: '/passwordChange',
      name: 'passwordChange',
      meta: { skipAuth: true },
      component: MsfPwChangeView, // 비밀번호 변경
    },
    // ===== 퍼블리싱, 화면확인용 라우팅(실제화면에서 사용안함) ===== //
    {
      path: '/guide/dev',
      name: 'DevGuide',
      meta: { skipAuth: true },
      component: DevGuide, // 개발 가이드
    },
    {
      path: '/guide/device',
      name: 'DeviceGuide',
      meta: { skipAuth: true },
      component: DeviceGuideView, // 앱 통신 가이드
    },
    {
      path: '/pub/:screenId',
      name: 'PubPage',
      component: PubPage, // 퍼블리싱 화면보기용
      props: true,
      meta: { skipAuth: true },
    },
    {
      path: '/guide',
      name: 'Guide',
      meta: { skipAuth: true },
      component: PubGuide, // 퍼블리싱 가이드 (작성중)
    },
    {
      path: '/publishing',
      name: 'PubList',
      meta: { skipAuth: true },
      component: PubList, // 퍼블리싱 목록
    },
    {
      path: '/test',
      name: 'GuideTest',
      meta: { skipAuth: true },
      component: GuideView, // 퍼블리싱 테스트
    },
    {
      path: '/guide/grid',
      name: 'GridGuide',
      meta: { skipAuth: true },
      component: GridGuideView, // 퍼블리싱 테스트
    },
    {
      path: '/guide/form-common',
      name: 'FormCommonGuide',
      meta: { skipAuth: true },
      component: FormCommonGuideView, // 퍼블리싱 테스트
    },
    {
      path: '/form-guide',
      name: 'FormGuideView',
      meta: { skipAuth: true },
      component: FormGuideView, // 퍼블리싱 반복 폼 정리
    },
    // ================================================== //
    {
      path: '/404',
      name: 'not-found-explicit',
      meta: { skipAuth: true },
      component: MsfNotFoundView, // 404 컴포넌트
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'not-found',
      meta: { skipAuth: true },
      component: MsfNotFoundView, // 404 컴포넌트
    },
  ],
})

router.beforeEach(async (to, from) => {
  if (to.path === '/login') {
    return true
  }

  const msfUserStore = useMsfUserStore()

  // 1. 인증이 필요한 페이지인데 인증이 안되었다면
  // if (!to.meta.skipAuth && !msfUserStore.token) {
  if (!to.meta.skipAuth && !msfUserStore.isAuthenticated) {
    const success = await msfUserStore.loadUserInfo()
    if (!success) return { name: 'Login' }
  }

  if (to.path === '/' && from.path === '/') {
    return true
  }

  // 2. 기존 이탈 방지 로직 유지
  if (from.path === '/form/newchange' || from.path.startsWith('/form/')) {
    const stepStore = useMsfStepStore()
    const newChgStore = useMsfFormNewChgStore()
    const ownChgStore = useMsfFormOwnChgStore()
    let result = true
    if (!stepStore.isWorkNotice()) {
      const message =
        from.path === '/form/newchange'
          ? newChgStore.applicationKey
            ? '신청서 작성 중에 화면 이탈시 입력하신 내용은 ‘임시저장’ 메뉴에서 확인 후 이어서 작성하실수 있습니다.\n(입력 정보는 7일간 보관됩니다.)\n화면을 이동하시겠습니까?'
            : '신청서 작성 중에 화면 이탈시 입력 내용은 재사용이 불가합니다.\n화면을 이동하시겠습니까?'
          : '신청서 작성 중에 화면 이탈시 입력 내용은 재사용이 불가합니다.\n화면을 이동하시겠습니까?'

      // 2. await 사용 (이제 정상적으로 true/false를 반환받습니다)
      result = await showConfirmAsync(message)

      if (result) {
        ownChgStore.$reset()
      }
    }

    stepStore.clearWorkNotice()

    // 3. 반환값으로 라우팅 제어 (경고 발생 안 함)
    return result
  }
  // 4. 그 외에는 그냥 통과
  return true
})

router.afterEach((to) => {
  const title = to.meta.title || DEFAULT_TITLE
  document.title = ENV_NAME ? `[${ENV_NAME}] ${title}` : title
})

export default router
