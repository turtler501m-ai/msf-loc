<template>
  <MsfFocusScope> <RouterView /></MsfFocusScope>
  <MsfNetworkBanner />
  <!-- 전역적으로 사용되는 포탈 영역(Dialog, Popover 등을 띄우는 영역) -->
  <div id="portal-root" class="portal"></div>
  <MsfAlertDialog
    v-for="(item, index) in alerts"
    :key="item.id"
    :id="item.id"
    :title="item.title"
    :message="item.message"
    :labelProps="item.labelProps"
    :showCancel="item.showCancel"
    :isLast="index === alerts.length - 1"
    :onConfirm="
      () => {
        // item(alert 객체) 안에 담긴 onConfirm이 함수인지 확인하고 실행
        if (typeof item.onConfirm === 'function') {
          item.onConfirm()
        }
        removeAlert(item.id) // 실행 후 해당 ID의 알럿만 닫기
      }
    "
    :onCancel="
      item.showCancel
        ? () => {
            item.onCancel?.() // 혹시 취소 시 할 일이 따로 정의되어 있다면 실행
            removeAlert(item.id) // 그리고 해당 ID의 알럿만 닫기
          }
        : undefined
    "
  />
  <MsfLoadingComp :isOpen="showLoading" />
</template>

<script setup>
import { computed, onBeforeMount, onMounted, onUnmounted, ref, watch } from 'vue'
import { RouterView, useRoute } from 'vue-router'
import { storeToRefs } from 'pinia'
import MsfNetworkBanner from '@/components/common/MsfNetworkBanner.vue'
import { useMsfAlertStore } from '@/stores/msf_alert'
import { useMsfLoadingStore } from '@/stores/msf_loading'
import { useMsfNetworkStore } from '@/stores/msf_network'
import { post } from '@/libs/api/msf.api'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
import {
  appUpdate,
  getDeviceUuid,
  initializeDeviceInfo,
  isAppWebView,
} from '@/libs/utils/device.utils'
import { useAppHeight } from '@/hooks/useAppHeight'
import { isNonProduction } from '@/libs/utils/env.utils'

useAppHeight() // 전역 레이아웃 기기높이 기준값(--msf-app-height) 생성

const route = useRoute()

const { alerts, removeAlert } = useMsfAlertStore()

const { loadings } = storeToRefs(useMsfLoadingStore())
const networkStore = useMsfNetworkStore()
const NETWORK_CHECK_FORM_DOMAINS = ['newchange', 'servicechange', 'ownerchange', 'termination']
const isAppWebViewDetected = ref(false)
const isAppWebViewMode = ref(false)

const showLoading = computed(() => loadings.value)
const shouldCheckNetwork = computed(() => {
  if (!isAppWebViewDetected.value || !isAppWebViewMode.value) return false
  if (!route.path.startsWith('/form/')) return false

  const domain = route.path.split('/')[2]

  return NETWORK_CHECK_FORM_DOMAINS.includes(domain)
})

const syncNetworkMonitoring = () => {
  if (shouldCheckNetwork.value) {
    networkStore.startMonitoring('form')
    return
  }

  networkStore.stopMonitoring({ scope: 'form', resetStatus: true })
}

// // 화면 보안 처리 여부를 결정하는 반응형 상태
// const isSecureHidden = ref(false)
// const secureAlertId = ref('')

// // 1. 마우스 우클릭(컨텍스트 메뉴) 방지
// const preventContextMenu = (e) => {
//   e.preventDefault() // 기본 메뉴가 뜨는 것을 막음
//   return false
// }

// // 2. 프린트 스크린 및 단축키 방지
// const preventKeyEvents = async (e) => {
//   // PrintScreen 키 감지 (key 또는 keyCode 사용)
//   if (e.key === 'PrintScreen' || e.keyCode === 44) {
//     try {
//       // 브라우저 클립보드 API를 사용해 복사된 이미지를 빈 문자열로 덮어씌움
//       await navigator.clipboard.writeText('')
//       showAlert('보안 정책상 화면 캡처가 금지되어 있습니다.')
//     } catch (err) {
//       console.error('클립보드 비우기 실패', err)
//     }
//   }

//   // (추가 팁) F12(개발자 도구) 및 복사(Ctrl+C) 방지
//   if (e.key === 'F12' || (e.ctrlKey && e.key === 'c')) {
//     e.preventDefault()
//   }

//   return false
// }

// // 3. 브라우저 창이 포커스를 잃었을 때 (캡처 도구 실행, 다른 프로그램 클릭 등)
// const handleBlur = () => {
//   isSecureHidden.value = true
//   secureAlertId.value = showAlert('보안 정책에 의해 화면이 보호되고 있습니다.')
// }

// // 4. 브라우저 창에 다시 포커스가 돌아왔을 때
// const handleFocus = () => {
//   isSecureHidden.value = false
//   hideAlert(secureAlertId.value)
//   secureAlertId.value = ''
// }

// // 5. 탭을 이동하거나 브라우저가 최소화되었을 때
// const handleVisibilityChange = () => {
//   isSecureHidden.value = document.hidden
// }

// onMounted(() => {
//   if (import.meta.env.MODE !== 'loc') {
//     // 컴포넌트가 화면에 마운트될 때 이벤트 등록
//     window.addEventListener('contextmenu', preventContextMenu)
//     window.addEventListener('keyup', preventKeyEvents)
//     window.addEventListener('keydown', preventKeyEvents)
//     window.addEventListener('blur', handleBlur)
//     window.addEventListener('focus', handleFocus)
//     document.addEventListener('visibilitychange', handleVisibilityChange)
//   }
// })

// onUnmounted(() => {
//   if (import.meta.env.MODE !== 'loc') {
//     // 컴포넌트가 파괴될 때 이벤트 해제 (메모리 누수 방지)
//     window.removeEventListener('contextmenu', preventContextMenu)
//     window.removeEventListener('keyup', preventKeyEvents)
//     window.removeEventListener('keydown', preventKeyEvents)
//     window.removeEventListener('blur', handleBlur)
//     window.removeEventListener('focus', handleFocus)
//     document.removeEventListener('visibilitychange', handleVisibilityChange)
//   }
// })

onBeforeMount(() => {
  // 앱이 실행될 때마다 디바이스 유형과 UUID를 초기화
})

// const baseUri = window.location.origin

const downloadUrl = ref('')

onMounted(async () => {
  try {
    await initializeDeviceInfo()
    isAppWebViewMode.value = isAppWebView()
    isAppWebViewDetected.value = true
    console.log('appDeviceType:', localStorage.getItem('deviceType'))
    console.log(
      'getDeviceInfo:',
      localStorage.getItem('appOsVersion'),
      localStorage.getItem('appVersion'),
      getDeviceUuid(),
    )
    await delay(500)
    const param = {
      os: localStorage.getItem('deviceType'), // 운영체제 정보
      appOsVer: localStorage.getItem('appOsVersion'), // 앱 운영체제 버전 정보 (예시)
      version: localStorage.getItem('appVersion'), // 앱 버전 정보
      uuid: getDeviceUuid(),
    }
    if (localStorage.getItem('deviceType') !== 'P') {
      param.uuid = getDeviceUuid()
      if (route.path !== '/download') {
        const result = await post('/api/n/app/intro', param)
        console.log(result.message)
        if (result.code === '0000') {
          // showAlert(JSON.stringify(result))
          console.log('env no product:' + isNonProduction())
          if (result.data.update === 'Y') {
            if (localStorage.getItem('deviceType') === 'I') {
              // if (isNonProduction()) {
              //   downloadUrl.value =
              //     `itms-services://?action=download-manifest&url=` + result.data.updateUrl
              // } else {
              //   downloadUrl.value = `itms-apps://itunes.apple.com/app/id6792940961`
              // }
              downloadUrl.value =
                `itms-services://?action=download-manifest&url=` + result.data.updateUrl
            }
            if (localStorage.getItem('deviceType') === 'A') {
              downloadUrl.value = result.data.updateUrl
            }
            if (result.data.mustUpCd === 'Y') {
              showAlert(
                '현재 설치된 App이 최신 버전이 아닙니다.\n최신 버전으로 업데이트 해주세요.',
                () => {
                  appUpdate(downloadUrl.value)
                },
              )
            } else {
              showConfirm(
                '최신 출시된 App이 있습니다.\n최신 버전으로 업데이트 하시겠습니까?',
                () => {
                  appUpdate(downloadUrl.value)
                },
                '',
                () => {},
              )
            }
          }
        } else {
          showAlert('앱 버전 정보를 가져오는 데 실패하였습니다.\n앱을 재실행 해주세요.')
        }
      }
    }
  } catch (err) {
    console.error('디바이스 정보 초기화 실패:', err)
  }
})
onUnmounted(() => {
  networkStore.stopMonitoring({ scope: 'form', resetStatus: true })
})

watch(
  shouldCheckNetwork,
  () => {
    syncNetworkMonitoring()
  },
  { immediate: true },
)

const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms))
</script>

<style scoped></style>
