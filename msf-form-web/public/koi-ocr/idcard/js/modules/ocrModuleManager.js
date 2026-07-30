// OcrModuleManager.js
import { loadUtils } from "../lib/utilLoader.js";
import { APP_VERSION } from "../lib/version.js";

let Utils = null;

// utils 준비 Promise
export const ready = (async () => {
  Utils = await loadUtils();
})();

export default class OcrModuleManager {
  constructor(ctx) {
    this.ctx = ctx;

    this.OcrModuleConfig = {
      detect: [1, 3, 10, 11, 12, 14, 15, 16, 19, 21, 22, 23, 27],
      qr: [4, 26, 27],
      document: [14, 15, 16, 27],
      passport: [2, 25],
    };

    const commonLoader = async (worker, type, extraData = {}) => {
      const { success, error, result } = await Utils.loadWorkerModule(worker, type, extraData);

      if (!success) {
        const errObj = Utils.handleWorkerError({
          module: extraData.module,
          type,
          error,
          result,
        });

        // 여기서 저장 (Manager 책임)
        this._lastError = errObj;
      }

      return success;
    };

    this.modules = {
      detect: {
        types: "all",
        worker: null,
        path: `./worker/detect/detectWorker.js?v=${APP_VERSION}`,
        loader: (worker, type) => commonLoader(worker, type, { module: "detect" }),
      },

      document: {
        types: this.OcrModuleConfig.document,
        worker: null,
        path: `./worker/convert/convertWorker.js?v=${APP_VERSION}`,
        loader: (worker, type, ctx) =>
          commonLoader(worker, type, {
            module: "document",
            convertTo: ctx._convertTo,
            jpegQuality: ctx._jpegQuality,
          }),
      },

      qr: {
        types: this.OcrModuleConfig.qr,
        worker: null,
        path: `./worker/qr/qrWorker.js?v=${APP_VERSION}`,
        loader: (worker, type) => commonLoader(worker, type, { module: "qr" }),
      },

      passport: {
        types: this.OcrModuleConfig.passport,
        worker: null,
        path: `./worker/passport/passportWorker.js?v=${APP_VERSION}`,
        loader: (worker, type) =>
          commonLoader(worker, type, {
            module: "passport",
            url: window.top.location.href,
          }),
      },
    };
  }

  // OcrModuleManager 클래스
  async load(ocrType) {
    this._lastError = null;

    for (const [moduleName, types] of Object.entries(this.OcrModuleConfig)) {
      if (!types.includes(ocrType)) continue;

      const moduleInfo = this.modules[moduleName];
      if (!moduleInfo) continue;

      if (!moduleInfo.worker) {
        moduleInfo.worker = new Worker(moduleInfo.path);
        if (moduleName === "document") {
          await this.loadScannerModule();
        }
      }

      const success = await moduleInfo.loader(moduleInfo.worker, ocrType, this.ctx);

      if (!success) {
        return {
          success: false,
          error: this._lastError?.message,
          detail: this._lastError,
        };
      }
    }

    return { success: true };
  }

  // 모든 모듈 순차 로딩
  async loadAll(ocrType) {
    for (const moduleName of Object.keys(this.modules)) {
      await this.load(moduleName, ocrType);
    }
  }

  async loadScannerModule() {
    // 이미 로드된 경우 재실행 방지
    if (this._scannerModuleLoaded) return;

    try {
      const { DScanner } = await import(`../modules/dScanner.js?v=${APP_VERSION}`);
      const { AffineEditor } = await import(`../ui/affine/affineEditor.js?v=${APP_VERSION}`);

      this._affineWorker = new Worker(`./worker/affine/affineWorker.js?v=${APP_VERSION}`);
      this._affineWorker.postMessage({ type: "init" });

      this._affineWorker.addEventListener("message", (event) => {
        const { type } = event.data;
        if (type === "initComplete") {
          // Utils.logInfo("[Main] Affine 모델 로드 완료");
        } else if (type === "initFailed") {
          // console.error("[Main] Affine 모델 로드 실패");
        }
      });

      this._affineEditor = new AffineEditor(this.ctx);
      this.dScanner = new DScanner({
        maxPages: this._totalPages,
        containerSelector: "#previewArea",
      });

      // 로딩 완료 플래그 설정
      this._scannerModuleLoaded = true;
    } catch (err) {
      console.error("문서 스캐너 모듈 로드 실패:", err);
    }
  }

  getWorker(moduleName) {
    return this.modules[moduleName]?.worker || null;
  }

  async sendDetect(ocrType, imageData, validCheckDt, ctx) {
    let moduleName;

    if (ocrType == 4 || ocrType == 26) {
      ctx._qrCheck = true;
    }
    if (this.OcrModuleConfig.qr.includes(ocrType) && ctx._qrCheck) {
      moduleName = "qr";
    } else if (this.OcrModuleConfig.passport.includes(ocrType)) {
      moduleName = "passport";
    } else {
      moduleName = "detect";
    }

    const rect = ctx._guideArea.getBoundingClientRect();

    const payload = {
      type: "detect",
      imageData,
      width: imageData.width,
      height: imageData.height,
      ocrType,
      validCheckDt,
      guideArea: rect,
    };

    // detect만 추가 옵션이 있음
    if (moduleName == "detect") {
      payload.useCapOcr = ctx._useCapOcr;
      payload.focusCheck = ctx._focusCheckLoaded;
      payload.recogTryLimit = ctx._options.recogTryLimit;
      payload.currentCount = ctx._currentCaptueCount;
      payload.isIOS = ctx._isIOS;
    }

    const resultEvent = moduleName === "qr" ? "qrResult" : moduleName === "passport" ? "passportResult" : "detectResult";

    return this.sendRequest(moduleName, payload, resultEvent);
  }

  async giroDetect(ocrType, imageData, validCheckDt, ctx) {
    let moduleName;

    if (this.OcrModuleConfig.qr.includes(ocrType)) {
      moduleName = "qr";
    } else if (this.OcrModuleConfig.passport.includes(ocrType)) {
      moduleName = "passport";
    } else {
      moduleName = "detect";
    }

    const payload = {
      type: "giroType",
      imageData,
      width: imageData.width,
      height: imageData.height,
      ocrType,
      validCheckDt,
    };

    // detect만 추가 옵션이 있음
    if (moduleName == "detect") {
      payload.useCapOcr = ctx._useCapOcr;
      payload.focusCheck = ctx._focusCheckLoaded;
      payload.recogTryLimit = ctx._options.recogTryLimit;
      payload.currentCount = ctx._currentCaptueCount;
      payload.isIOS = ctx._isIOS;
    }

    const resultEvent = "giroType";

    return this.sendRequest(moduleName, payload, resultEvent);
  }

  async sendRequest(moduleName, payload, resultType) {
    const worker = this.getWorker(moduleName);
    if (!worker) throw new Error(`${moduleName} worker not initialized`);

    worker.postMessage(payload);

    return new Promise((resolve) => {
      const handler = (event) => {
        if (event.data.type == resultType) {
          worker.removeEventListener("message", handler); // 이벤트 중복 방지
          resolve(event.data);
        }
      };
      worker.addEventListener("message", handler);
    });
  }

  async getAffineFromWorker(imageData, sortedPoints) {
    return new Promise((resolve, reject) => {
      const onMessage = (e) => {
        const { type, result, message } = e.data;

        if (type === "getAffine") {
          this._affineWorker.removeEventListener("message", onMessage);

          if (result) {
            resolve(result);
          } else {
            reject(new Error(message || "Affine calculation failed"));
          }
        } else if (type === "error") {
          affineWorker.removeEventListener("message", onMessage);
          reject(new Error(message));
        }
      };

      this._affineWorker.addEventListener("message", onMessage);

      this._affineWorker.postMessage({
        type: "getAffine",
        imageData,
        sortedPoints,
      });
    });
  }

  // 특정 모듈 워커 종료
  terminate(moduleName) {
    const moduleInfo = this.modules[moduleName];
    if (moduleInfo?.worker) {
      moduleInfo.worker.terminate();
      moduleInfo.worker = null;
    }
  }

  // 전체 워커 종료
  terminateAll() {
    for (const moduleName of Object.keys(this.modules)) {
      this.terminate(moduleName);
    }
  }
}
