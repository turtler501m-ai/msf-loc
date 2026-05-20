export const appDeviceType = async () => {
  if (window.ktMmobile) {
    localStorage.setItem('deviceType', 'A')
  } else if (window.webkit) {
    localStorage.setItem('deviceType', 'I')
  } else {
    localStorage.setItem('deviceType', 'PC')
  }
}

export const getDeviceInfo = async () => {
  const deviceType = localStorage.getItem('deviceType')
  const data = {
    callback: 'appDeviceInfo',
  }
  if (deviceType === 'A') {
    window.ktMmobile.getDeviceInfo(data)
  } else if (deviceType === 'I') {
    window.webkitURL.messageHandlers.getDeviceInfo.postMessage(data)
  } else {
    console.log('getDeviceInfo -- PC')
  }
}

export const appDeviceInfo = (data) => {
  // 앱에서 전달된 디바이스 정보를 처리하는 로직을 여기에 작성
  // 예: 앱 스토어에 디바이스 정보 저장, UI 업데이트 등
  localStorage.setItem('appOsVersion', data.appOsVersion)
  localStorage.setItem('appVersion', data.appVersion)
  localStorage.setItem('MSF_DEVICE_UUID', data.uuid)
}

export const exportBrowser = async (exporturl) => {
  const deviceType = localStorage.getItem('deviceType')
  const data = {
    url: exporturl,
  }
  if (deviceType === 'A') {
    window.ktMmobile.exportBrowser(data)
  } else if (deviceType === 'I') {
    window.webkitURL.messageHandlers.exportBrowser.postMessage(data)
  } else {
    console.log('exportBrowser -- PC')
  }
}
