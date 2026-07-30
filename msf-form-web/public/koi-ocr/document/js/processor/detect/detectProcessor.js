// import { APP_VERSION } from "../../lib/version.js";
self.APP_VERSION = "202511191038";

class detectProcessor {
  constructor() {
    this.Module = null;
    this.Lapl = null;
    // this.buffer 제거: 로컬 변수로 관리하여 메모리 고아 현상 방지
    this.isModelLoaded = false;
    this.isLaplLoad = false;
  }

  async initModule(wasmDirectory, modelType) {
    if (this.modelType === modelType) {
      return { success: 1 };
    }

    this.modelType = modelType;
    const v = `?v=${self.APP_VERSION}`;

    try {
      // 기존 로직 유지
      if ([1, 2, 11, 19, 21, 22, 23, 29].includes(modelType)) {
        importScripts(`${wasmDirectory}idcard/Koi_idcardDt.js${v}`);
        this.Module = await IdDetectModule({ wasmDirectory });
      } else if (modelType == 3) {
        importScripts(`${wasmDirectory}card/Koi_cardDt.js${v}`);
        this.Module = await cardDetectModule({ wasmDirectory });
      } else if (modelType == 10) {
        importScripts(`${wasmDirectory}giro/Koi_giroDt.js${v}`);
        this.Module = await giroDetectModule({ wasmDirectory });
      } else if ([7, 14, 15, 16, 24, 27].includes(modelType)) {
        importScripts(`${wasmDirectory}crop/SegCrop.js${v}`);
        this.Module = await segCropModule({ wasmDirectory });
      } else if (modelType === "focusCheck") {
        if (!this.isLaplLoad) {
          importScripts(`${wasmDirectory}lapl/Lapl_detect.js${v}`);
          this.Lapl = await LaplModule({ wasmDirectory });
          this.isLaplLoad = true;
        }
      } else {
        return { success: 0, error: `Invalid modelType: ${modelType}` };
      }

      return { success: 1 };
    } catch (error) {
      console.error(`[initModule] Failed`, { wasmDirectory, modelType, error });
      this.Module = null;
      return { success: 0, error: error?.message || "Init failed" };
    }
  }

  async loadModel(modelType) {
    // 기존 로직 유지 (단, 가독성을 위해 includes 사용 추천)
    let functionName;
    if ([1, 2, 11, 19, 21, 22, 23, 29].includes(modelType)) functionName = "loadONNXModel";
    else if (modelType == 10) functionName = "loadGiroModel";
    else if (modelType == 3) functionName = "loadcreditCardModel";
    else if ([7, 14, 15, 16, 24, 27].includes(modelType)) functionName = "initializeModel";
    else functionName = null;

    try {
      let result;
      // initializeModel 등은 인자가 없는 경우가 많음. 필요시 인자 확인.
      result = this.Module.ccall(functionName, "number", [], []);

      if (result == 1) {
        this.isModelLoaded = true;
        this.modelType = modelType;
        return { success: 1 };
      } else {
        return { success: 0, error: `${functionName} failed (result=${result})` };
      }
    } catch (error) {
      this.Module = null;
      postMessage({ type: "unload" });
      return { success: 0, error: error?.message || "Load error" };
    }
  }

  async loadLapl(modelType) {
    if (this.isLaplLoad) return 1;
    try {
      const result = this.Lapl.ccall("loadLapl", "number", [], []);
      if (result == 1) {
        this.isLaplLoad = true;
        return 1;
      }
      return 0;
    } catch (error) {
      this.Lapl = null;
      postMessage({ type: "unload" });
      return 0;
    }
  }

  // 신분증 감지
  detect(imageData, width, height, ocrType, lapThreshold) {
    if (!this.isModelLoaded) throw new Error("Model not loaded");

    let imgBufferPtr = null;
    let detectSettingsPtr = null;

    try {
      const bufferSize = width * height * 4;
      imgBufferPtr = this.Module._malloc(bufferSize);
      this.Module.HEAPU8.set(imageData.data, imgBufferPtr);

      const detectSettingsStructSize = 24;
      detectSettingsPtr = this.Module._malloc(detectSettingsStructSize);

      const detectSettings = {
        boxMinRatio: 0.02,
        boxMaxRatio: 0.2,
        sizeRatio: 3.0,
        photoRatio: 0.7,
        isLap: true,
        photoCheck: false,
        lapThreshold: lapThreshold,
      };

      this.Module.HEAPF32.set([detectSettings.boxMinRatio, detectSettings.boxMaxRatio, detectSettings.sizeRatio, detectSettings.photoRatio], detectSettingsPtr >> 2);
      this.Module.HEAP8[detectSettingsPtr + 16] = detectSettings.isLap ? 1 : 0;
      this.Module.HEAP8[detectSettingsPtr + 17] = detectSettings.photoCheck ? 1 : 0;
      this.Module.HEAPF32[(detectSettingsPtr + 20) / 4] = detectSettings.lapThreshold;

      return this.Module.ccall("detectObjects", "number", ["number", "number", "number", "number"], [imgBufferPtr, width, height, detectSettingsPtr]);
    } finally {
      // 에러가 나든 성공하든 무조건 메모리 해제
      if (imgBufferPtr !== null) this.Module._free(imgBufferPtr);
      if (detectSettingsPtr !== null) this.Module._free(detectSettingsPtr);
    }
  }

  // 지로 타입 판별
  giroType(imageData, width, height) {
    if (!this.isModelLoaded) throw new Error("Model not loaded");

    let imgBufferPtr = null;
    try {
      const bufferSize = width * height * 4;
      imgBufferPtr = this.Module._malloc(bufferSize);
      this.Module.HEAPU8.set(imageData.data, imgBufferPtr);

      return this.Module.ccall("giroType", "number", ["number", "number", "number"], [imgBufferPtr, width, height]);
    } finally {
      if (imgBufferPtr !== null) this.Module._free(imgBufferPtr);
    }
  }

  // 지로 감지
  giroDetect(imageData, width, height) {
    if (!this.isModelLoaded) throw new Error("Model not loaded");

    let imgBufferPtr = null;
    try {
      const bufferSize = width * height * 4;
      imgBufferPtr = this.Module._malloc(bufferSize);
      this.Module.HEAPU8.set(imageData.data, imgBufferPtr);

      return this.Module.ccall("giroDetect", "number", ["number", "number", "number"], [imgBufferPtr, width, height]);
    } finally {
      if (imgBufferPtr !== null) this.Module._free(imgBufferPtr);
    }
  }

  // 카드 감지
  cardDetect(imageData, width, height) {
    if (!this.isModelLoaded) throw new Error("Model not loaded");

    let imgBufferPtr = null;
    try {
      const bufferSize = width * height * 4;
      imgBufferPtr = this.Module._malloc(bufferSize);
      this.Module.HEAPU8.set(imageData.data, imgBufferPtr);

      return this.Module.ccall("cardDetect", "number", ["number", "number", "number"], [imgBufferPtr, width, height]);
    } finally {
      if (imgBufferPtr !== null) this.Module._free(imgBufferPtr);
    }
  }

  // 이미지 크롭 (메모리 누수 취약했던 부분)
  crop(imageData, width, height, validCheckDt) {
    if (!this.isModelLoaded) throw new Error("Model not loaded");

    let imgBufferPtr = null;
    try {
      const bufferSize = width * height * 4;
      imgBufferPtr = this.Module._malloc(bufferSize);
      this.Module.HEAPU8.set(imageData.data, imgBufferPtr);

      const resultCharPtr = this.Module.ccall("processImage", "number", ["number", "number", "number", "number"], [imgBufferPtr, width, height, validCheckDt ? 1 : 0]);

      // 반환된 문자열 포인터를 JS 문자열로 변환
      const resultString = this.Module.UTF8ToString(resultCharPtr);

      // JSON 파싱 (여기서 에러나도 finally로 가서 free함)
      const resultJSON = JSON.parse(resultString);
      return { resultJSON: resultJSON };
    } catch (e) {
      console.error("Crop Error:", e);
      throw e;
    } finally {
      // 기존 코드에서는 리턴 전에만 free를 해서, 에러 시 누수 발생했었음
      if (imgBufferPtr !== null) this.Module._free(imgBufferPtr);
    }
  }

  // 포커스 체크 (기존에 free 누락됨)
  focusCheck(imageData, width, height, threshold) {
    if (!this.isLaplLoad) throw new Error("Model not loaded");

    let imgBufferPtr = null;
    try {
      const bufferSize = width * height * 4;
      imgBufferPtr = this.Lapl._malloc(bufferSize);
      this.Lapl.HEAPU8.set(imageData.data, imgBufferPtr);

      return this.Lapl.ccall("detectLapl", "number", ["number", "number", "number", "number"], [imgBufferPtr, width, height, threshold]);
    } finally {
      if (imgBufferPtr !== null) this.Lapl._free(imgBufferPtr);
    }
  }

  unload() {
    // 이미 free를 각 메서드에서 수행하므로 여기서는 모듈 참조만 해제하면 됨
    // 만약 전역으로 재사용하는 버퍼를 도입한다면 여기서 free 해야 함
    if (this.Module) {
      this.Module = null;
      this.isModelLoaded = false;
    }
    if (this.Lapl) {
      this.Lapl = null;
      this.isLaplLoad = false;
    }
  }
}

self.detectProcessor = detectProcessor;
