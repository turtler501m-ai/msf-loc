importScripts("../../processor/passport/passportProcessor.js");

let detector = null;
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

async function initializeModel(modelType, url) {
  if (!detector) {
    detector = new passportProcessor();
  }

  try {
    await detector.initModule("../../detect/", modelType);
    loadModelResult = await detector.loadModel(modelType, url);
    if (loadModelResult == 1) {
      isModelLoaded = true;
      postMessage({ type: "initComplete" });
    } else {
      postMessage({
        type: "initFailed",
        code: loadModelResult,
      });
    }
  } catch (error) {
    postMessage({
      type: "initFailed",
      code: loadModelResult,
    });
  }
}

self.onmessage = async (event) => {
  if (event.data.type == "init") {
    const { ocrType, url } = event.data;
    await initializeModel(ocrType, url);
  }

  if (event.data.type == "detect" && isModelLoaded) {
    const { imageData, width, height } = event.data;
    // 이미지 리스트 전달

    const passportResult = await detector.ocr(imageData, width, height);
    if (passportResult) {
      // ArrayBuffer로 변환해서 transferable 전송 (빠른 전송용)
      const resultCode = passportResult.resultJSON.resultCode;
      if (resultCode == "0000") {
        self.postMessage({ type: "passportResult", resultCode: passportResult, continuousSuccess: 1 });
      } else {
        const failCode = passportResult.resultJSON;
        self.postMessage({ type: "passportResult", resultCode: failCode, continuousSuccess: 0 });
      }
    } else {
      self.postMessage({ type: "error", message: "MRZ 인식 실패", continuousSuccess: 0 });
    }
    `  `;
  }

  if (event.data.type == "unload") {
    detector.unload();
    isModelLoaded = false;
    postMessage({ type: "unloadComplete" });
  }
};
