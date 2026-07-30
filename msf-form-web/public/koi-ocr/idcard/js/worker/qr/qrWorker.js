importScripts("../../processor/qr/qrProcessor.js");

let koder = null;
let isModelLoaded = false;
let previousDetectResultCode = null;
let detectCount = 0;
let detectResultCode = -1;
let previousPoints = null;
let currentPoints = null;
let iou = null;
let loadModelResult = null;
let idcard_points = { x: 0, y: 0, w: 0, h: 0 };
let failCount = 0;
let endTime = 0;
let loadModelType = null;

async function initializeModel(modelType) {
  if (!koder) {
    koder = new Koder();
  }

  try {
    // initModule
    if (loadModelType !== modelType) {
      const initResult = await koder.initModule("../../detect/", modelType);

      if (!initResult || initResult.success !== 1) {
        postMessage({
          type: "initFailed",
          error: initResult?.error?.message || "모듈 초기화에 실패했습니다.",
        });
        return;
      }
    }

    loadModelType = modelType;

    // loadModel
    const loadResult = await koder.loadModel(modelType);

    if (loadResult && loadResult.success === 1) {
      isModelLoaded = true;
      postMessage({ type: "initComplete" });
    } else {
      postMessage({
        type: "initFailed",
        error: loadResult?.error?.message || "모델 로딩에 실패했습니다.",
      });
    }
  } catch (error) {
    postMessage({
      type: "initFailed",
      error: error?.message || "모델 초기화 중 예외가 발생했습니다.",
    });
  }
}

self.onmessage = async (event) => {
  if (event.data.type == "init") {
    const { ocrType } = event.data;
    await initializeModel(ocrType);
  }

  if (event.data.type == "detect" && isModelLoaded) {
    const { imageData, width, height } = event.data;
    // 이미지 리스트 전달

    const qrResult = await koder.decode(imageData.data, width, height);
    if (qrResult) {
      // ArrayBuffer로 변환해서 transferable 전송 (빠른 전송용)
      const resultCode = qrResult.resultJSON.resultCode;
      if (resultCode == "0000") {
        self.postMessage({ type: "qrResult", resultCode: qrResult, continuousSuccess: 1 });
      } else {
        self.postMessage({ type: "qrResult", resultCode: qrResult, continuousSuccess: 0 });
      }
    } else {
      self.postMessage({ type: "error", message: "QR 인식 실패", continuousSuccess: 0 });
    }
    `  `;
  }

  if (event.data.type == "unload") {
    koder.unload();
    isModelLoaded = false;
    postMessage({ type: "unloadComplete" });
  }
};
