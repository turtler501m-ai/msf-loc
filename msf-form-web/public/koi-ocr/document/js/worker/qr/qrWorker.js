importScripts('../../processor/qr/qrProcessor.js')

let koder = null
let isModelLoaded = false
let loadModelType = null

// QR 성공 여부
let isQrDetected = false

async function initializeModel(modelType) {
  if (!koder) {
    koder = new Koder()
  }

  try {
    // 재초기화 시 상태 리셋
    isQrDetected = false

    // initModule
    if (loadModelType !== modelType) {
      const initResult = await koder.initModule('../../detect/', modelType)

      if (!initResult || initResult.success !== 1) {
        postMessage({
          type: 'initFailed',
          error: initResult?.error?.message || '모듈 초기화에 실패했습니다.',
        })
        return
      }
    }

    loadModelType = modelType

    // loadModel
    const loadResult = await koder.loadModel(modelType)

    if (loadResult && loadResult.success === 1) {
      isModelLoaded = true

      postMessage({
        type: 'initComplete',
      })
    } else {
      postMessage({
        type: 'initFailed',
        error: loadResult?.error?.message || '모델 로딩에 실패했습니다.',
      })
    }
  } catch (error) {
    postMessage({
      type: 'initFailed',
      error: error?.message || '모델 초기화 중 예외가 발생했습니다.',
    })
  }
}

self.onmessage = async (event) => {
  /**
   * 초기화
   */
  if (event.data.type === 'init') {
    const { ocrType } = event.data

    await initializeModel(ocrType)
  }

  /**
   * QR detect
   */
  if (event.data.type === 'detect' && isModelLoaded && !isQrDetected) {
    const { imageData, width, height } = event.data

    try {
      const qrResult = await koder.decode(imageData.data, width, height)

      if (!qrResult) {
        self.postMessage({
          type: 'error',
          message: 'QR 인식 실패',
          continuousSuccess: 0,
        })

        return
      }

      const resultCode = qrResult?.resultJSON?.resultCode

      // 성공
      if (resultCode === '0000') {
        // 더 이상 detect 안 돌게 막음
        isQrDetected = true

        self.postMessage({
          type: 'qrResult',
          resultCode: qrResult,
          continuousSuccess: 1,
        })

        return
      }

      // 실패
      self.postMessage({
        type: 'qrResult',
        resultCode: qrResult,
        continuousSuccess: 0,
      })
    } catch (error) {
      self.postMessage({
        type: 'error',
        message: error?.message || 'QR 처리 중 오류 발생',
        continuousSuccess: 0,
      })
    }
  }

  /**
   * unload
   */
  if (event.data.type === 'unload') {
    isQrDetected = false

    if (koder) {
      koder.unload()
    }

    isModelLoaded = false

    postMessage({
      type: 'unloadComplete',
    })
  }
}
