import { createRouter, createWebHistory } from 'vue-router'
// import { isAuthenticated } from '@/libs/utils/auth.utils'

// 레이아웃
import MsfBaseLayout from '@/layouts/MsfBaseLayout.vue'
// 메인 View
import MsfMainView from '@/views/MsfMainView.vue'
// 404 View
import MsfNotFoundView from '@/views/MsfNotFoundView.vue'
// 로그인
import MsfLoginView from '@/views/MsfLoginView.vue'
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
          component: () => import('@/views/MsfFormView.vue'),
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
        // 퍼블리싱 화면보기용
        {
          path: '/pub/:screenId',
          name: 'PubPage',
          component: PubPage,
          props: true,
        },
      ],
    },
    {
      path: '/login',
      name: 'Login',
      component: MsfLoginView, // 로그인
    },
    {
      path: '/deviceAuth',
      name: 'deviceAuth',
      component: MsfDeviceAuthView, // 단말 사용 인증
    },
    {
      path: '/deviceRegist',
      name: 'deviceRegist',
      component: MsfDeviceRegisterView, // 단말 사용 등록
    },
    {
      path: '/passwordChange',
      name: 'passwordChange',
      component: MsfPwChangeView, // 비밀번호 변경
    },
    // ===== 퍼블리싱용 라우팅(실제화면에서 사용안함) ===== //
    {
      path: '/guide',
      name: 'Guide',
      component: PubGuide, // 퍼블리싱 가이드 (작성중)
    },
    {
      path: '/publishing',
      name: 'PubList',
      component: PubList, // 퍼블리싱 목록
    },
    {
      path: '/test',
      name: 'GuideTest',
      component: GuideView, // 퍼블리싱 테스트
    },
    {
      path: '/form-common-guide',
      name: 'FormCommonGuide',
      component: FormCommonGuideView, // 퍼블리싱 테스트
    },
    {
      path: '/form-guide',
      name: 'FormGuideView',
      component: FormGuideView, // 퍼블리싱 반복 폼 정리
    },
    {
      path: '/404',
      name: 'not-found-explicit',
      component: MsfNotFoundView, // 404 컴포넌트
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'not-found',
      component: MsfNotFoundView, // 404 컴포넌트
    },
  ],
})

// router.beforeEach((to, from, next) => {
//   if (!isAuthenticated() && to.path !== '/login') {
//     next('/login')
//   } else {
//     next()
//   }
// })

// router.afterEach((to) => {
//   const title = to.meta.title || 'MSF'
//   document.title = title
// })

export default router
