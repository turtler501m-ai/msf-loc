import { defineAsyncComponent } from 'vue'
import MsfErrorComp from '@/components/layouts/MsfErrorComp.vue'
import MsfLoadingComp from '@/components/layouts/MsfLoadingComp.vue'

/**
 * 일반 라우팅 컴포넌트 반환
 *
 * @param {*} route
 * @returns
 */
export const getRoutingComponent = (route) => {
  return defineAsyncComponent({
    loader: () => import(`@/components/${route.params.domain}/${route.params.service}.vue`),
    loadingComponent: MsfLoadingComp,
    errorComponent: MsfErrorComp,
  })
}

/**
 * 일반 라우팅 컴포넌트 반환
 *
 * @param {*} route
 * @returns
 */
export const getStepComponent = (dir, file) => {
  if (dir && file) {
    return defineAsyncComponent({
      loader: () => import(`@/components/${dir}/${file}.vue`),
      loadingComponent: MsfLoadingComp,
      errorComponent: MsfErrorComp,
    })
  }
  return null
}
