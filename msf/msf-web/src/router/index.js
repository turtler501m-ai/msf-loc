import { createRouter, createWebHistory } from 'vue-router'
import BaseLayout from '@/layouts/BaseLayout.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: BaseLayout,
      children: [
        {
          path: '',
          name: 'home',
          component: () => import('@/views/McpHomeView.vue'),
        },
        {
          // 신규 스텝 방식 (formSvcCncl, formSvcChg, formOwnChg)
          path: 'step/:domain',
          name: 'step',
          component: () => import('@/views/MsfStepView.vue'),
        },
        {
          // 상세 페이지 (부가기능 등)
          path: 'detail/:domain/:service',
          name: 'detail',
          component: () => import('@/views/MsfDetailView.vue'),
        },
        {
          // 완료 화면
          path: 'mobile/complete/:domain',
          name: 'service-complete',
          component: () => import('@/views/ServiceCompleteView.vue'),
        },
      ],
    },
  ],
})

export default router
