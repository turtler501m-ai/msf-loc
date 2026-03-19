import { defineAsyncComponent } from 'vue'
import McpErrorComponent from '@/components/McpErrorComponent.vue'
import McpPageLoadingComponent from '@/components/McpPageLoadingComponent.vue'

/**
 * 일반 라우팅 컴포넌트 반환
 *
 * @param {*} route
 * @returns
 */
export const getRoutingComponent = (route) => {
  return defineAsyncComponent({
    loader: () => import(`@/components/${route.params.domain}/${route.params.service}.vue`),
    loadingComponent: McpPageLoadingComponent,
    errorComponent: McpErrorComponent,
  })
}

/**
 * 스텝 컴포넌트 반환
 *
 * @param {*} subMenuUrl
 * @param {*} route
 * @returns
 */
export const getStepComponent = (subMenuUrl, route) => {
  let urls = []
  console.log('getStepComponent - subMenuUrl:', subMenuUrl)
  if (subMenuUrl) {
    urls = subMenuUrl.replace(/^\/?mobile\/?(.*)$/g, '$1').split('/')
    if (urls && urls.length > 1) {
      return defineAsyncComponent({
        loader: () => import(`@/components/${urls[0]}/${urls[1]}.vue`),
        loadingComponent: McpPageLoadingComponent,
        errorComponent: McpErrorComponent,
      })
    }
    return getRoutingComponent(route)
  }
  return getRoutingComponent(route)
}
