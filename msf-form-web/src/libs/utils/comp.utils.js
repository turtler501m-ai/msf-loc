import { defineAsyncComponent, markRaw } from 'vue'
import MsfErrorComp from '@/components/layouts/MsfErrorComp.vue'
import MsfLoadingComp from '@/components/layouts/MsfLoadingComp.vue'

/**
 * 일반 라우팅 컴포넌트 반환
 *
 * @param {string} dir
 * @param {string} file
 * @returns
 */
export const getRoutingComponent = (pathes) => {
  return defineAsyncComponent({
    loader: () => import(`@/components/${pathes.join('/')}.vue`),
    loadingComponent: MsfLoadingComp,
    errorComponent: MsfErrorComp,
  })
}

/**
 * Form 라우팅 컴포넌트 반환
 *
 * @param {string} dir
 * @param {string} file
 * @returns
 */
export const getFormComponent = (dir, file) => {
  if (dir && file) {
    return markRaw(
      defineAsyncComponent({
        loader: () => import(`@/components/form/${dir}/${file}.vue`),
        loadingComponent: MsfLoadingComp,
        errorComponent: MsfErrorComp,
      }),
    )
  }
  return MsfErrorComp
}

/**
 * /components/extra 폴더 내 모든 컴포넌트를 미리 로드하여 경로와 매핑
 */
const modules = Object.fromEntries(
  Object.entries(import.meta.glob('@/components/extra/**/*.vue')).map(([path, fn]) => [
    path.replace('/src', '@'),
    fn,
  ]),
)

/**
 * Extra 라우팅 컴포넌트 반환
 *
 * @param {string[]} pathes
 * @returns
 */
export const getExtraComponent = (pathes) => {
  const targetPath = `@/components/extra/${pathes.join('/')}.vue`
  if (pathes && pathes.length > 0 && modules[targetPath]) {
    return markRaw(
      defineAsyncComponent({
        loader: modules[targetPath],
        loadingComponent: MsfLoadingComp,
        errorComponent: MsfErrorComp,
      }),
    )
  }
  return MsfErrorComp
}
