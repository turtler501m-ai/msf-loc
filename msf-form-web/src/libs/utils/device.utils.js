// 앱과 웹에서 디바이스 정보를 가져오는 유틸리티 함수

const hasAndroidAppBridge = () => Boolean(window.ktMmobile)
const hasIosAppBridge = () => Boolean(window.webkit?.messageHandlers?.getDeviceInfo)
const isWebViewSimulation = () => import.meta.env.VITE_MSF_WEBVIEW_SIMULATION_ENABLED === 'true'
const isDeviceUuidOverrideAllowed = () =>
  import.meta.env.VITE_MSF_ALLOW_DEVICE_UUID_OVERRIDE === 'true'
let deviceUuid = ''
let deviceInfoRequestPromise = null
let deviceInitializationPromise = null
let resolveDeviceInfoReady
const deviceInfoReady = new Promise((resolve) => {
  resolveDeviceInfoReady = resolve
})

// 앱에서 디바이스 유형을 감지하여 로컬 스토리지에 저장하는 함수
export const appDeviceType = async () => {
  if (hasAndroidAppBridge()) {
    localStorage.setItem('deviceType', 'A')
  } else if (hasIosAppBridge()) {
    localStorage.setItem('deviceType', 'I')
  } else {
    localStorage.setItem('deviceType', 'P')
  }
}

export const isAppWebView = () =>
  isWebViewSimulation() || hasAndroidAppBridge() || hasIosAppBridge()

export const canOverrideDeviceUuid = () =>
  isDeviceUuidOverrideAllowed() && !hasAndroidAppBridge() && !hasIosAppBridge()

export const getDeviceUuid = () => {
  if (canOverrideDeviceUuid()) {
    return localStorage.getItem('MSF_DEVICE_UUID') || ''
  }

  return deviceUuid
}

export const setDeviceUuidOverride = (value) => {
  if (!canOverrideDeviceUuid()) {
    return getDeviceUuid()
  }

  const overrideValue = value?.trim() || ''
  if (overrideValue) {
    localStorage.setItem('MSF_DEVICE_UUID', overrideValue)
  } else {
    localStorage.removeItem('MSF_DEVICE_UUID')
  }
  return overrideValue
}

export const waitForDeviceInfo = () => deviceInfoReady

export const initializeDeviceInfo = () => {
  if (!deviceInitializationPromise) {
    deviceInitializationPromise = (async () => {
      try {
        await appDeviceType()
        await getDeviceInfo()
      } finally {
        resolveDeviceInfoReady()
      }
    })()
  }

  return deviceInitializationPromise
}

// 단말정보요청
export const getDeviceInfo = () => {
  if (!deviceInfoRequestPromise) {
    deviceInfoRequestPromise = new Promise((resolve) => {
      const deviceType = localStorage.getItem('deviceType')
      const data = {
        callback: 'appDeviceInfo',
      }

      window.appDeviceInfo = (jsonStr) => {
        // alert('appDeviceInfo: ' + jsonStr)
        const data = JSON.parse(jsonStr)
        localStorage.setItem('appOsVersion', data.appOsVer)
        localStorage.setItem('appVersion', data.version)
        deviceUuid = data.uuid || ''
        resolve()
      }
      if (deviceType === 'A') {
        window.ktMmobile.getDeviceInfo(JSON.stringify(data))
      } else if (deviceType === 'I') {
        window.webkit.messageHandlers.getDeviceInfo.postMessage(data)
      } else {
        console.log('getDeviceInfo -- PC')
        resolve()
      }
    })
  }

  return deviceInfoRequestPromise
}

// 앱 강제종료
export const appFinish = async () => {
  const deviceType = localStorage.getItem('deviceType')
  if (deviceType === 'A') {
    window.ktMmobile.appFinish('')
  } else if (deviceType === 'I') {
    window.webkit.messageHandlers.appFinish.postMessage('')
  } else {
    console.log('appFinish -- PC')
  }
}

// 업데이트 - 외부 브라우저 열기
export const exportBrowser = async (exporturl) => {
  const deviceType = localStorage.getItem('deviceType')
  const data = {
    url: exporturl,
  }
  if (deviceType === 'A') {
    window.ktMmobile.exportBrowser(JSON.stringify(data))
  } else if (deviceType === 'I') {
    window.webkit.messageHandlers.exportBrowser.postMessage(data)
  } else {
    window.open(exporturl, '_blank')
  }
}

// 업데이트 - 앱내 업데이트
export const appUpdate = async (downloadurl) => {
  const deviceType = localStorage.getItem('deviceType')
  const data = {
    url: downloadurl,
  }
  if (deviceType === 'A') {
    window.ktMmobile.appUpdateUrl(JSON.stringify(data))
  } else if (deviceType === 'I') {
    window.webkit.messageHandlers.appUpdateUrl.postMessage(data)
  } else {
    console.log('appUpdateUrl -- PC')
  }
}

// 생체인증 - 가능여부 조회
export const getBioLoginStatus = async () => {
  const deviceType = localStorage.getItem('deviceType')
  const data = {
    callback: 'appBioLoginStatus',
  }

  window.appBioLoginStatus = (jsonStr) => {
    if (jsonStr) {
      const data = JSON.parse(jsonStr)
      if (data.code === '0000') {
        localStorage.setItem('isBioLoginAvailable', 'Y')
      } else {
        localStorage.setItem('isBioLoginAvailable', 'N')
      }
    }
  }
  if (deviceType === 'A') {
    window.ktMmobile.getBioLoginStatus(JSON.stringify(data))
  } else if (deviceType === 'I') {
    window.webkit.messageHandlers.getBioLoginStatus.postMessage(data)
  } else {
    console.log('getBioLoginStatus -- PC')
  }
}

// 생체인증 - 생체인증 등록요청
export const setBioLoginRegistration = async (key) => {
  return new Promise((resolve) => {
    // alert('setBioLoginRegistration key: ' + key)
    const deviceType = localStorage.getItem('deviceType')
    const data = {
      callback: 'appBioLoginRegistration',
      key: key,
    }

    window.appBioLoginRegistration = async (jsonStr) => {
      if (jsonStr) {
        const data = JSON.parse(jsonStr)
        console.log('appBioLoginRegistration', JSON.stringify(data))
        if (data.code === '0000') {
          console.log('생체인증 등록 성공, appBioLoginRegistration: ', data.key)
          resolve(data.key)
          return
        }
      }
      console.log('생체인증 등록 실패, appBioLoginRegistration: ', jsonStr)
      resolve(null)
    }
    if (deviceType === 'A') {
      window.ktMmobile.setBioLoginRegistration(JSON.stringify(data))
    } else if (deviceType === 'I') {
      window.webkit.messageHandlers.setBioLoginRegistration.postMessage(data)
    } else {
      console.log('setBioLoginRegistration -- PC')
    }
  })
}

// 생체인증 - 생체인증 등록 완료
export const setBioLoginRegistrationSave = async (biokey) => {
  const deviceType = localStorage.getItem('deviceType')
  const data = {
    registration: 'Y',
    key: biokey, // 사용자 구분용 암호화된 키
  }
  if (deviceType === 'A') {
    window.ktMmobile.setBioLoginRegistrationSave(JSON.stringify(data))
  } else if (deviceType === 'I') {
    window.webkit.messageHandlers.setBioLoginRegistrationSave.postMessage(data)
  } else {
    console.log('setBioLoginRegistrationSave -- PC')
  }
}

// 생체인증 - 생체인증 로그인요청
export const bioLogin = async (randomKey) => {
  return new Promise((resolve) => {
    const deviceType = localStorage.getItem('deviceType')
    const data = {
      callback: 'appBioLogin',
      key: randomKey, // 생체인증 로그인 시 필요한 1회용 임의 키
    }

    window.appBioLogin = (jsonStr) => {
      console.log('appBioLogin', jsonStr)
      if (jsonStr) {
        const data = JSON.parse(jsonStr)
        resolve(data)
      }
    }

    if (deviceType === 'A') {
      window.ktMmobile.bioLogin(JSON.stringify(data))
    } else if (deviceType === 'I') {
      window.webkit.messageHandlers.bioLogin.postMessage(data)
    } else {
      console.log('bioLogin -- PC')
    }
  })
}

// 사진촬영 - 사진촬영 요청
export const showCamera = async (cameraKey, callback) => {
  const deviceType = localStorage.getItem('deviceType')

  const data = {
    callback: 'appShowCamera',
    key: cameraKey,
    max: 5,
  }

  window.appShowCamera = (jsonStr) => {
    if (!jsonStr) return

    const images = typeof jsonStr === 'string' ? JSON.parse(jsonStr) : jsonStr

    callback?.(images)
  }

  if (deviceType === 'A') {
    window.ktMmobile.showCamera(JSON.stringify(data))
  } else if (deviceType === 'I') {
    window.webkit.messageHandlers.showCamera.postMessage(data)
  } else {
    console.log('showCamera -- PC')
  }
}

// 단말에 String 값 저장
export const setAppDB = async (jsonstr) => {
  const deviceType = localStorage.getItem('deviceType')
  const data = {
    key: 'bio',
    value: jsonstr, // 저장할 데이터
  }
  if (deviceType === 'A') {
    window.ktMmobile.setAppDB(JSON.stringify(data))
  } else if (deviceType === 'I') {
    window.webkit.messageHandlers.setAppDB.postMessage(data)
  } else {
    console.log('setAppDB -- PC')
  }
}

// 단말에 저장된 String 값 조회
export const getAppDB = async (dbReadKey) => {
  const deviceType = localStorage.getItem('deviceType')
  const data = {
    callback: 'appGetDB',
    key: dbReadKey, // 조회할 데이터의 키값 (예시)
  }

  window.appGetDB = (jsonStr) => {
    if (jsonStr) {
      const data = JSON.parse(jsonStr)
      console.log(`appDB에서 ${data.key}의 값은 ${data.value} 입니다.`)
    }
  }
  if (deviceType === 'A') {
    window.ktMmobile.getAppDB(JSON.stringify(data))
  } else if (deviceType === 'I') {
    window.webkit.messageHandlers.getAppDB.postMessage(data)
  } else {
    console.log('getAppDB -- PC')
  }
}

export const generateRandomString = async (length = 10) => {
  return Math.random()
    .toString(36)
    .substring(2, 2 + length)
}

export const generateHash = async (message) => {
  // 1. 문자열을 바이트 배열(Uint8Array)로 변환
  const encoder = new TextEncoder()
  const data = encoder.encode(message)

  // 2. 암호화 해시 알고리즘 적용 (SHA-256)
  const hashBuffer = await crypto.subtle.digest('SHA-256', data)

  // 3. ArrayBuffer를 16진수 문자열(Hex String)로 변환
  const hashArray = Array.from(new Uint8Array(hashBuffer))
  const hashHex = hashArray.map((b) => b.toString(16).padStart(2, '0')).join('')

  return hashHex
}
