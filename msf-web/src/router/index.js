import { createRouter, createWebHistory } from 'vue-router'
import McpMainLayout from '@/components/layouts/McpMainLayout.vue'
import { useMsfCompLoadingStore } from '@/stores/msf_comp_loading'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'mcp-main',
      component: McpMainLayout,
      children: [
        {
          path: '',
          name: 'home',
          component: () => import('@/views/McpHomeView.vue'),
        },
        {
          path: 'mobile/:domain/:service',
          name: 'service-detail',
          component: () => import('@/views/McpServiceDetailView.vue'),
        },
        {
          path: 'mobile/complete/:domain',
          name: 'service-complete',
          component: () => import('@/views/ServiceCompleteView.vue'),
        },
      ],
    },
  ],
})

router.beforeEach(() => {
  useMsfCompLoadingStore().showLoadingComponent()
})
router.afterEach(() => {
  useMsfCompLoadingStore().hideLoadingComponent()
})

export default router
