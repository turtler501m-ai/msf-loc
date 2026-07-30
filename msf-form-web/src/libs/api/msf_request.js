const pendingApiRequests = new Map()

export const registerPendingApiRequest = (config) => {
  if (config.skipLoading || config.signal) {
    return
  }

  const controller = new AbortController()
  config.signal = controller.signal
  config.__msfAbortController = controller
  pendingApiRequests.set(controller, config)
}

export const releasePendingApiRequest = (config) => {
  const controller = config?.__msfAbortController
  if (controller) {
    pendingApiRequests.delete(controller)
  }
}

export const cancelPendingApiRequests = () => {
  pendingApiRequests.forEach((config, controller) => {
    config.__msfLongLoadingAbort = true
    controller.abort()
  })
  pendingApiRequests.clear()
}
