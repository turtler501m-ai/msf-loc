importScripts("../../processor/convert/convertProcessor.js");

let convertModule = null;
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

async function initializeModel(modelType, convertTo, jpegQuality) {
  if (!convertModule) {
    convertModule = new ConvertModule();
  }

  try {
    await convertModule.initModule("../../detect/", modelType, convertTo);

    loadModelResult = await convertModule.loadModel(modelType, convertTo, jpegQuality);

    if (loadModelResult == 1) {
      isModelLoaded = true;
      postMessage({ type: "initComplete" });
    } else {
      postMessage({ type: "initFailed" });
    }
  } catch (error) {
    postMessage({ type: "initFailed" });
  }
}

self.onmessage = async (event) => {
  if (event.data.type == "init") {
    const { ocrType, convertTo, jpegQuality } = event.data;
    await initializeModel(ocrType, convertTo, jpegQuality);
  }

  if (event.data.type == "convert" && isModelLoaded) {
    const { images, width, height } = event.data;
    // 이미지 리스트 전달

    const convertBinary = await convertModule.sendAllImagesToWasm(images, width, height);
    if (convertBinary) {
      // ArrayBuffer로 변환해서 transferable 전송 (빠른 전송용)
      self.postMessage({ type: "convertResult", data: convertBinary.buffer }, [convertBinary.buffer]);
    } else {
      self.postMessage({ type: "error", message: "포맷 생성 실패" });
    }
  }

  if (event.data.type == "unload") {
    convertModule.unload();
    isModelLoaded = false;
    postMessage({ type: "unloadComplete" });
  }
};
