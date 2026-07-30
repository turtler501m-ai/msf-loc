importScripts("../../processor/affine/affineProcessor.js");
// importScripts("./Utils.js");
let docsAffine = null;
let isModelLoaded = false;

async function initializeModel() {
  if (!docsAffine) {
    docsAffine = new DocsAffine();
  }

  try {
    const result = await docsAffine.loadModel();
    if (result == 1) {
      isModelLoaded = true;
      postMessage({ type: "initComplete" });
    } else {
      postMessage({ type: "initFailed" });
    }
  } catch (err) {
    console.error("[AffineWorker] Model initialization failed", err);
    postMessage({ type: "initFailed" });
  }
}

// 메시지 리스너
self.onmessage = async (e) => {
  const { type, imageData, width, height, sortedPoints } = e.data;

  switch (type) {
    case "init":
      await initializeModel();
      break;

    case "getPoints":
      if (!isModelLoaded || !docsAffine) {
        postMessage({ type: "error", message: "Model not initialized" });
        return;
      }

      const result = docsAffine.getPoints(imageData, width, height);
      const points = result?.resultJSON?.points || [];

      postMessage({
        type: "getPoints",
        result: {
          points,
        },
      });
      break;

    case "getAffine":
      if (!isModelLoaded || !docsAffine) {
        postMessage({ type: "error", message: "Model not initialized" });
        return;
      }
      const affineResult = docsAffine.getAffine(imageData, imageData.width, imageData.height, sortedPoints);
      postMessage({
        type: "getAffine",
        result: {
          affineResult,
        },
      });
      break;

    default:
      postMessage({ type: "error", message: "Unknown message type: " + type });
      break;
  }
};
