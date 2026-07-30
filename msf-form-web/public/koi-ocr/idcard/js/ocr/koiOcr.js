import { loadConfig } from "../lib/configLoader.js";
import { APP_VERSION } from "../lib/version.js";
let WebCamera = null;
let Utils = null;
class KoiOcr extends EventTarget {
  _webCamera;
  _ocrWorker;
  _ocrType = null;
  _rtcType = null;
  _options;
  _useCapOcr;
  _useDetect;
  _points;
  _isOcrRequestSent = false;
  _isProcessing = false;
  _isResultDispatched = false;
  _successCount = 0;
  _validCheckDt = false;
  _ocrFailCount = 0;
  _isChangingOcrType = false;

  constructor() {
    super();
    this.config = null;
    this._webCamera = null;
    this._rtcType = null;

    // 초기화 Promise 외부 노출 가능
    this._ready = this._initModules().then(() => {
      this._webCamera = new WebCamera();

      this._rtcType = this.config?.rtcType?.MANUAL ?? 0;

      this._initEventHandler();
    });
  }

  async _initModules() {
    const [cameraModule, utilsModule, config] = await Promise.all([
      import(`../camera/webCamera.js?v=${APP_VERSION}`),
      import(`../lib/utils.js?v=${APP_VERSION}`),
      loadConfig(), // 유일한 config 입구
    ]);

    WebCamera = cameraModule.default;
    Utils = utilsModule;

    // configLoader에서 받은 단일 인스턴스
    this.config = config;
  }

  async ready() {
    return this._ready;
  }

  async init(options) {
    if (options.ocrWorkerJs && !this._ocrWorker) {
      this._ocrWorker = new Worker(options.ocrWorkerJs);
    }

    const defaultOptions = {
      useWebCamera: true,
      cameraOptions: {},
      useWasmOcr: true,
      ocrType: this.config.OCR_TYPE.IDCARD,
    };
    // 기본 옵션과 제공된 옵션을 병합하여 this._options 설정
    this._options = { ...defaultOptions, ...options };
    // this._ocrType = this._options.ocrType;
    this._useWasmOcr = true;
    this._useDemo = false;
    this._cameraOptions = this._options.cameraOptions;
    this._useCapOcr = this._cameraOptions.useCapOcr;
    this.recogTryLimit = this._cameraOptions.recogTryLimit;

    this._useDetect = this._cameraOptions.useDetect;
    this._regis = this._cameraOptions.regisClassType;
    this._isDevMode = this._options.isDevMode;
    this._ocrEncryption = this._options.ocrEncryption;
    await this._webCamera.ready();
    await this._webCamera.setOptions(this._cameraOptions);
    // this.dispatchCameraEvent();
  }

  get useWebCamera() {
    return this._webCamera != null;
  }

  get useWasmOcr() {
    return this._ocrProcessor != null && this._IdCardOCRProcessor != null;
  }

  _initEventHandler() {
    if (this.useWebCamera) {
      this._webCamera.addEventListener("webcamready", (event) => {
        this.dispatchReadyEvent();
        // return this.config.KOI_OCR_EVENT.CAMERA_STARTED;
      });

      this._webCamera.addEventListener("imagecaptured", async (event) => {
        this.dispatchCaptureEvent(event);
        if (await this.processImage(event.detail)) {
          event.preventDefault();
        }
      });

      this._webCamera.addEventListener("timeout", async (event) => {
        this.dispatchTimeoutEvent(event);
      });

      this._webCamera.addEventListener("workerPass", async (event) => {
        this.dispatchCaptureEvent(event);
        const { cropData, originData, ocrType, rtcToken, detectTime, resultCode, timeout, regisType } = event.detail;
        let eventType = "result";
        const result = resultCode;
        const eventDetail = {
          success: true,
          cropData,
          originData,
          ocrResult: resultCode,
          type: ocrType,
        };
        eventType = await this.processOCRResult(result, eventDetail, cropData, rtcToken, detectTime, timeout);

        this.dispatchEvent(new CustomEvent(eventType, { detail: eventDetail }));
        return eventType == this.config.KOI_OCR_EVENT.RESULT;
      });

      this._webCamera.addEventListener("dscan", async (event) => {
        this.dispatchCaptureEvent(event);
      });
    }

    document.addEventListener(
      "touchmove",
      function (event) {
        if (event.touches.length > 1 || (event.scale && event.scale !== 1)) return;
        if (Math.abs(event.deltaX) > Math.abs(event.deltaY)) {
          event.preventDefault();
        }
      },
      { passive: false },
    );

    // fileUpload 이벤트 리스너 설정
    window.addEventListener("fileUpload", async (event) => {
      this.dispatchCaptureEvent(event);
      if (this._ocrType == 16) {
        const detectResult = await this._webCamera.sendDetectRequest(event.detail.imageData, true);
      } else {
        if (event.detail.ocrType == 4) {
          const uploadData = event.detail.imageData;
          this._webCamera.handleDetection(true, uploadData);
        } else {
          if (await this.processImage(event.detail)) {
            event.preventDefault();
          }
        }
      }
    });

    window.addEventListener("imageProcessed", async (event) => {
      this.dispatchCaptureEvent(event);
      const { cropData, originData, ocrType, rtcToken, detectTime, resultCode, timeout } = event.detail;
      let eventType = "result";
      const eventDetail = {
        success: false,
        cropData,
        originData,
        ocrResult: null,
        type: ocrType,
      };

      if (this._useDemo) {
        if (await this.sendToOcrWorkerDemo(cropData, eventDetail, ocrType, rtcToken, detectTime, resultCode, timeout)) {
          event.preventDefault();
        } // demo
      } else {
        if (await this.sendToOcrWorker(cropData, eventDetail, ocrType, rtcToken, detectTime, resultCode, timeout)) {
          event.preventDefault();
        }
      }
      this.dispatchEvent(new CustomEvent(eventType, { detail: eventDetail }));
      return eventType == this.config.KOI_OCR_EVENT.RESULT;
    });
  }

  async processImage(data) {
    const { cropData, originData, rtcToken, resultCode, detectTime, timeout, convertBlob, base64, totalPages, documentList, qrcode } = data;
    const eventDetail = {
      success: false,
      cropData,
      originData,
      ocrResult: null,
      base64Data: base64,
      totalPages: totalPages,
      documentList: documentList,
      qrcode: qrcode,
    };

    try {
      let eventType;
      let imageData;
      if (this._ocrType == 11 || this._ocrType == 19 || this._ocrType == 23) {
        imageData = originData;
      } else {
        imageData = cropData;
      }

      if (this._useCapOcr == 3) {
        imageData = cropData;
      }
      if (imageData && this._useCapOcr != 4) {
        eventType = await this.processOCR(imageData, eventDetail, this._ocrType, rtcToken, resultCode, detectTime, timeout);
      } else {
        const result = null;
        if (this._ocrType == 14 || this._ocrType == 15 || this._ocrType == 16) {
          eventType = await this.processOCR(imageData, eventDetail, this._ocrType, rtcToken, resultCode, detectTime, timeout, convertBlob, totalPages, documentList);
        } else if (this._ocrType == 27) {
          eventType = await this.processOCR(imageData, eventDetail, this._ocrType, rtcToken, resultCode, detectTime, timeout, convertBlob, totalPages, documentList, qrcode);
        }
        // eventType = await this.processOCRResult(result, eventDetail, imageData, rtcToken, detectTime, timeout);
      }

      this.dispatchEvent(new CustomEvent(eventType, { detail: eventDetail }));
      return eventType == this.config.KOI_OCR_EVENT.RESULT;
    } catch (error) {
      Utils.logError("Error processing image:", error.message);
      // 실패 이벤트 처리
      const failEventType = this.config.KOI_OCR_EVENT.RESULT;
      eventDetail.success = false;
      eventDetail.ocrResult = null;

      this.dispatchEvent(new CustomEvent(failEventType, { detail: eventDetail }));

      // 실패 시에도 false 반환하여 함수 종료
      return false;
    }
  }

  async processOCR(imageData, eventDetail, ocrType, rtcToken, resultCode, detectTime, timeout, convertBlob, totalPages, documentList, qrcode) {
    return await this.sendToOcrWorker(imageData, eventDetail, ocrType, rtcToken, detectTime, resultCode, timeout, convertBlob, totalPages, documentList, qrcode);
  }

  _prepareBase64(imageData, convertBlob, eventDetail) {
    let base64Data = null;

    // 1. ImageData 객체인 경우 (Canvas를 거쳐서 JPEG base64로 변환)
    if (imageData instanceof ImageData) {
      const canvas = document.createElement("canvas");
      canvas.width = imageData.width;
      canvas.height = imageData.height;
      const context = canvas.getContext("2d");
      context.putImageData(imageData, 0, 0);

      // JPEG 품질 1.0으로 변환 후 'data:image/jpeg;base64,' 부분 제거
      base64Data = canvas.toDataURL("image/jpeg", 1.0).split(",")[1];

      // 메모리 정리를 위해 캔버스 비우기
      context.clearRect(0, 0, canvas.width, canvas.height);
    }
    // 2. 이미 문자열(Base64 등)인 경우
    else if (typeof imageData === "string") {
      base64Data = imageData.split(",")[1] || imageData;
    }
    // 3. 데이터가 없는 경우 (특정 OCR 타입에 따른 백업 데이터 사용)
    else {
      if (this._ocrType == 16 || this._ocrType == 27) {
        base64Data = convertBlob;
        eventDetail.convertBlob = convertBlob;
      } else if (this._ocrType == 14 || this._ocrType == 15) {
        base64Data = eventDetail.base64Data;
      }
    }

    return base64Data;
  }

  reinitWorker() {
    if (this._ocrWorker) {
      this._ocrWorker.terminate(); // 기존 워커 강제 종료
      console.log("기존 워커를 종료하고 새로 시작합니다.");
    }
    // 워커 초기화 로직 재실행 (기존에 워커 만들던 코드 호출)
    this._ocrWorker = new Worker(this._options.ocrWorkerJs);
    this._isOcrRequestSent = false;
  }

  _usesVueApiBridge() {
    return window.top && window.top !== window && window.parent !== window.top;
  }

  _requestVueOcrApi({ ocrType, base64Data }) {
    return new Promise((resolve, reject) => {
      const requestId = `koi-ocr-${Date.now()}-${Math.random().toString(36).slice(2)}`;
      const timeoutId = setTimeout(() => {
        window.removeEventListener("message", handleMessage);
        reject(new Error("OCR API 요청 시간이 초과되었습니다."));
      }, 30000);

      const handleMessage = (event) => {
        if (event.origin !== window.location.origin) return;
        if (event.data?.type !== "MSF_KOI_OCR_API_RESPONSE") return;
        if (event.data.requestId !== requestId) return;

        clearTimeout(timeoutId);
        window.removeEventListener("message", handleMessage);

        if (!event.data.ok) {
          reject(new Error(event.data.message || "OCR API 요청에 실패했습니다."));
          return;
        }

        resolve(event.data.data);
      };

      window.addEventListener("message", handleMessage);
      window.top.postMessage(
        {
          type: "MSF_KOI_OCR_API_REQUEST",
          requestId,
          payload: {
            ocrType,
            base64Data,
            saveOption: true,
          },
        },
        window.location.origin,
      );
    });
  }

  async sendToOcrWorker(imageData, eventDetail, ocrType, rtcToken, detectTime, resultCode, timeout, convertBlob, totalPages, documentList, qrcode) {
    if (this._useCapOcr === 1 && ![14, 15, 16, 27].includes(this._ocrType)) {
      if (resultCode !== 0) {
        const result = null; // 워커 요청 없이 result를 null로 설정
        return this.processOCRResult(result, eventDetail, imageData, rtcToken, detectTime, timeout, documentList, qrcode);
      }
    }
    if (this._usesVueApiBridge()) {
      const startTime = performance.now();
      const base64Data = this._prepareBase64(imageData, convertBlob, eventDetail);
      const response = await this._requestVueOcrApi({ ocrType, base64Data });
      const endTime = performance.now();

      eventDetail.recognitionTime = endTime - startTime;
      eventDetail.status = 200;
      eventDetail.ocrType = ocrType;

      const result = { resultJSON: response };
      return this.processOCRResult(result, eventDetail, imageData, rtcToken, detectTime, timeout, documentList, qrcode);
    }

    const result = await new Promise((resolve, reject) => {
      let base64Data = this._prepareBase64(imageData, convertBlob, eventDetail);

      if (!this._ocrWorker) return resolve(null);
      if (this._isOcrRequestSent) {
        console.warn("이전 요청이 아직 처리 중입니다.");
        return resolve(null);
      }

      this._isOcrRequestSent = true;
      const startTime = performance.now();

      const handleMessage = (e) => {
        if (e.data.type === "ocrResult") {
          // 리스너 즉시 제거 (이전 메시지 간섭 방지)
          this._ocrWorker.removeEventListener("message", handleMessage);
          this._isOcrRequestSent = false;

          if (e.data.isError) {
            this.reinitWorker();
            // 서버가 다시 올라와도 워커 내부 세션이 꼬였다면 여기서 에러가 반복됨
            console.error("Worker Response Error:", e.data.message);
            // resolve(null); // 에러 시 null 반환하여 프로세스 중단 방지
            reject(new Error(e.data.message));
            return;
          }

          const endTime = performance.now();
          eventDetail.recognitionTime = endTime - startTime;
          eventDetail.status = e.data.status ?? "";
          eventDetail.ocrType = ocrType;

          resolve(e.data.message);
        }
      };

      this._ocrWorker.addEventListener("message", handleMessage);

      // 4. 워커에 메시지 전송
      this._ocrWorker.postMessage({
        ocrType,
        base64Data,
        ocrEncryption: this._ocrEncryption,
        validCheck: this._validCheckDt,
        regis: this._regis,
        token: rtcToken,
        tiffPages: totalPages,
        qrcode,
        userID: this._userId,
      });

      // 5. 타임아웃 처리 (네트워크 먹통 시 무한 대기 방지)
      setTimeout(() => {
        this._ocrWorker.removeEventListener("message", handleMessage);
        if (this._isOcrRequestSent) {
          this._isOcrRequestSent = false;
          resolve(null); // 타임아웃 시 다음 기회로
        }
      }, 30000); // 30초
    });

    return this.processOCRResult(result, eventDetail, imageData, rtcToken, detectTime, timeout, documentList, qrcode);
  }

  // 시연 서버 요청
  async sendToOcrWorkerDemo(imageData, eventDetail, ocrType, rtcToken, detectTime, resultCode, timeout, convertBlob, totalPages) {
    if (this._useCapOcr == 1 && this._ocrType != 14 && this._ocrType != 15 && this._ocrType != 16) {
      if (resultCode !== 0) {
        const result = null; // 워커 요청 없이 result를 null로 설정
        return this.processOCRResult(result, eventDetail, imageData, rtcToken, detectTime, timeout);
      }
    }
    const result = await new Promise((resolve, reject) => {
      const canvas = document.createElement("canvas");
      let base64Data = null;
      if (imageData instanceof ImageData) {
        const canvas = document.createElement("canvas");
        canvas.width = imageData.width;
        canvas.height = imageData.height;
        const context = canvas.getContext("2d");
        context.putImageData(imageData, 0, 0);
        base64Data = canvas.toDataURL("image/jpeg", 1.0);
        // Utils.downloadImageFromCanvas(canvas);

        if (context) {
          context.clearRect(0, 0, canvas.width, canvas.height);
        }
      } else {
        // 이미 base64 형식이면 그대로 사용
        if (imageData) {
          base64Data = imageData.split(",")[1];
        } else {
          if (this._ocrType == 16) {
            base64Data = convertBlob;
          } else if (this._ocrType == 14 || this._ocrType == 15) {
            base64Data = eventDetail.base64Data;
            base64Data = Utils.base64DataURL(base64Data);
          }
        }
      }

      const urlParams = new URL(location.href).searchParams;
      const paramsObj = Object.fromEntries(urlParams.entries());
      const typeValue = urlParams.get("ocrType");
      const methodValue = urlParams.get("method");
      const licenseKey = urlParams.get("licenseKey");
      const rtcLicenseKey = rtcToken;
      var formData = new FormData();
      let validCheck = this._validCheckDt;
      let maskOption = true;

      const fileName = Utils.generateFileName(); // 기본값으로 호출 (prefix "rtc_", extension ".jpg")
      formData.append("file", Utils.dataURLtoBlob(base64Data), fileName);

      formData.append("maskOption", maskOption);
      formData.append("validCheck", validCheck);
      formData.append("ocrType", typeValue);
      formData.append("method", methodValue);
      formData.append("licenseKey", licenseKey);
      formData.append("rtcTransId", rtcLicenseKey);
      formData.append("tokens", 500);
      formData.append("rawData", true);

      if (this._ocrType == 15) {
        formData.append("base64Image", true);
        formData.append("fullText", false);
      } else if (this._ocrType == 19) {
        const userID = Utils.getTodayUserID();
        formData.append("userID", userID);
        formData.append("regisClass", this._regis);
      } else {
        formData.append("base64Image", false);
        formData.append("fullText", true);
      }

      const requestUrl = "/ocr-api/request";
      const startTime = performance.now();
      const captureTime = Utils.getCurrentTime();
      eventDetail.captureTime = captureTime;
      koi.Ajax.fnOcrFileAjax(requestUrl, formData, function (data) {
        const endTime = performance.now(); // 응답 받은 시간
        const resultTime = Utils.getCurrentTime();
        const recognitionTime = endTime - startTime; // 인식 소요 시간 (ms 단위)
        eventDetail.resultTime = resultTime;
        eventDetail.recognitionTime = recognitionTime;
        if (data) {
          const ocrResult = {
            resultJSON: data.resultData,
          };
          eventDetail.ocrType = ocrType;
          resolve(ocrResult);
        } else {
          resolve(null);
        }
      });
    });
    return this.processOCRResult(result, eventDetail, imageData, rtcToken, detectTime, timeout);
  }

  async processOCRResult(result, eventDetail, imageData, rtcToken, detectTime, timeout, documentList, qrcode) {
    const resultCode = result?.resultJSON?.resultCode;
    const maskImage = result?.resultJSON?.maskImage;

    if (timeout) {
      return this._handleTimeoutResult(result, eventDetail, rtcToken, detectTime, timeout, resultCode, maskImage);
    }

    if (this._isOcrSuccess(resultCode)) {
      return this._handleSuccessResult(result, eventDetail, detectTime, timeout, maskImage);
    }

    if (this._ocrType == 16 || this._ocrType == 27) {
      return this._handleOcrType16Result(eventDetail, resultCode, detectTime, rtcToken, documentList, qrcode);
    }

    if (this._useCapOcr == 1 || this._useCapOcr == 5) {
      return this._handleRetryOnFail(eventDetail);
    }

    if (result || result.resultJSON) {
      eventDetail.ocrResult = result;
    }

    return this.config.KOI_OCR_EVENT.RESULT;
  }

  _handleTimeoutResult(result, eventDetail, rtcToken, detectTime, timeout, resultCode, maskImage) {
    this._webCamera.forceStopTimerDisplay();

    // 기존 정보 세팅
    eventDetail.detectTime = detectTime;
    eventDetail.timeout = timeout;
    eventDetail.validCheckDt = this._validCheckDt;
    eventDetail.rtcToken = rtcToken;
    eventDetail.ocrResult = result || null;

    // [추가] 에러 정보 명시
    eventDetail.error = "timeout";

    if (maskImage) eventDetail.base64Data = maskImage;

    if (result && result.resultJSON) {
      const isSuccess = resultCode === "0000";
      eventDetail.success = isSuccess;
    } else {
      eventDetail.success = false;
    }

    const eventName = this.config.KOI_OCR_EVENT.TIMEOUT;
    const customEvent = new CustomEvent(eventName, { detail: eventDetail });
    document.dispatchEvent(customEvent);

    return eventName;
  }

  _handleSuccessResult(result, eventDetail, detectTime, timeout, maskImage) {
    this._webCamera.forceStopTimerDisplay();

    eventDetail.ocrResult = result;
    // eventDetail.detectTime = detectTime;
    // eventDetail.lapTime = timeout;
    eventDetail.success = true;
    Utils.hideLoading();
    if (maskImage) {
      eventDetail.base64Data = maskImage;
    }

    return this.config.KOI_OCR_EVENT.RESULT;
  }

  _handleOcrType16Result(eventDetail, resultCode, detectTime, rtcToken, documentList, qrcode) {
    eventDetail.validCheckDt = this._validCheckDt;
    eventDetail.rtcToken = rtcToken;
    eventDetail.ocrResult = resultCode;
    eventDetail.success = true;
    eventDetail.documentList = documentList;
    eventDetail.qrcode = qrcode;

    return this.config.KOI_OCR_EVENT.RESULT;
  }

  _handleRetryOnFail(eventDetail) {
    this._ocrFailCount = (this._ocrFailCount || 0) + 1;
    const limit = this.recogTryLimit;
    Utils.hideLoading();
    if (this._ocrFailCount >= limit) {
      eventDetail.success = false;
      this._ocrFailCount = 0;
      return this.config.KOI_OCR_EVENT.RESULT;
    }

    this._webCamera.progress();
    return this.config.KOI_OCR_EVENT.PROGRESS;
  }

  _isOcrSuccess(resultCode) {
    return resultCode === "0000";
  }

  async runCamera() {
    if (this.useWebCamera) {
      this._stopOcr = false;
      this._webCamera.start();
      this._isResultDispatched = false;
      this._isProcessing = false;
    } else {
      throw new Error("WebCamera disabled.");
    }
  }

  async progressCamera() {
    if (this.useWebCamera) {
      this._stopOcr = false;
      this._webCamera.progress();
      this._isResultDispatched = false;
      this._isProcessing = false;
    } else {
      throw new Error("WebCamera disabled.");
    }
  }

  stopCamera() {
    if (this.useWebCamera) {
      this._webCamera.stop();
    } else {
      throw new Error("WebCamera disabled.");
    }
  }

  unloadCamera() {
    if (this.useWebCamera) {
      this._webCamera.unload();
    } else {
      throw new Error("WebCamera disabled.");
    }
  }

  async stopWorker() {
    if (this._wasmWorker) {
      this._wasmWorker.terminate(); // 워커 종료
    }
  }

  dispatchReadyEvent() {
    this.dispatchEvent(new Event(this.config.KOI_OCR_EVENT.READY));
  }

  dispatchCameraEvent() {
    this.dispatchEvent(new Event(this.config.KOI_OCR_EVENT.READY));
  }

  dispatchCaptureEvent(e) {
    const event = new CustomEvent(this.config.KOI_OCR_EVENT.CAPTURE, {
      detail: { ocrResult: e.detail },
    });
    this.dispatchEvent(event);
  }

  dispatchTimeoutEvent(e) {
    const event = new CustomEvent(this.config.KOI_OCR_EVENT.TIMEOUT, {
      detail: e.detail,
    });
    this.dispatchEvent(event);
  }

  dispatchResultEvent(e) {
    const event = new CustomEvent(this.config.KOI_OCR_EVENT.RESULT, {
      e,
    });
    this.dispatchEvent(event);
  }

  async changeOcrType(ocrType) {
    if (ocrType === undefined) return;

    if (this._isChangingOcrType) {
      Utils.logError("[changeOcrType] OCR 변경 중 중복 호출 차단");
      return;
    }

    this._isChangingOcrType = true;

    try {
      this._ocrType = ocrType;

      // WASM 사용하지 않는 경우만 카메라 OCR 타입 변경
      if (this.useWebCamera) {
        const res = await this._webCamera.changeOCR(ocrType);

        // 여기서 실패 감지
        if (!res || res.success !== true) {
          throw new Error(res?.error || "changeOCR failed");
        }
      }
      return { success: true };
    } catch (err) {
      Utils.logError(`[changeOcrType] OCR 타입 변경 실패: ${err.message}`);

      return {
        success: false,
        error: err.message,
      };
    } finally {
      this._isChangingOcrType = false;
    }
  }
}

export { KoiOcr as default };
