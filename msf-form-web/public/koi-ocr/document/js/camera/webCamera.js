import { APP_VERSION } from "../lib/version.js";
const vh = window.innerHeight * 0.01;
document.documentElement.style.setProperty("--vh", `${vh}px`);
document.documentElement.style.backgroundColor = "#f9eded1f";

let config = null;
let Utils = null;
let OcrModuleManager = null;
let GuideStrategies = null;

export default class WebCamera extends EventTarget {
  _container;
  _videoWrapper;
  _videoRef;
  _mediaStream;
  _canvasRef;
  _guideArea;
  _letterboxTop;
  _letterboxBottom;
  _video = "video";
  _canvas = "canvas";
  _ocrType = null;
  _guideTexts = null;
  _pictureWidth = window.innerWidth;
  _pictureHeight = window.innerHeight;
  _vertical;
  _useCapOcr;
  _currentCaptueCount = 0;
  _captureInProgress = false;
  _detectInProgress = false;
  _isStartInProgress = false;
  _isIOS;
  _isIOS15 = false;
  _isIOS15under = false;
  _title;
  _detectRqCount = 0;
  _key;
  _orientationChangeButton;
  _isReversed = false;
  _uploadImg = null;
  _successfulDetectionCalled = false; // 플래그 추가
  _previousGuideMessage = null;
  _methodValue = null;
  _imageDiv = null;
  _startTime = 0;
  _processTime = 0;
  _isResizing = false;
  _pauseCapture = false;
  _imageCaptured = false;
  _qrFind = [];
  _qrResults = [];
  _isQrValid = false;
  _qrCheck = false;
  _isEditingScreen = false;

  constructor() {
    super();

    // 모듈 로드 준비 (Promise)
    this._ready = this._initModules().then(() => {
      this._ocrModuleManager = new OcrModuleManager(this);

      this._ocrType = config.OCR_TYPE.IDCARD;
      this._guideTexts = Utils.guideTexts;

      this._detectIosEnvironment();
    });
  }

  async ready() {
    return this._ready;
  }

  async _initModules() {
    const [cfg, utils, mgr, guide] = await Promise.all([
      import(`../configs.js?v=${APP_VERSION}`),
      import(`../lib/utils.js?v=${APP_VERSION}`),
      import(`../modules/ocrModuleManager.js?v=${APP_VERSION}`),
      import(`../ui/camera/guideStrategis.js?v=${APP_VERSION}`),
    ]);

    config = cfg.config;
    Utils = utils;
    OcrModuleManager = mgr.default;
    GuideStrategies = guide.GuideStrategies;
  }

  /**
   * iOS 환경 판단
   */
  _detectIosEnvironment() {
    this._isIpad = /Macintosh|iPad/i.test(navigator.userAgent) && navigator.maxTouchPoints && navigator.maxTouchPoints > 1;
    this._isIOS = /Macintosh|iPad|iPhone|iPod/i.test(navigator.userAgent);

    if (this._isIOS) {
      let iosVersion = /(Macintosh|iPad|iPhone|iPod) OS ([0-9]*)/g.exec(navigator.userAgent)?.[2] || 0;
      iosVersion = String(iosVersion);
      this._isSwapWH = iosVersion.startsWith("16");
      this._isIOS15 = iosVersion.startsWith("15");
      this._isIOS15under = parseInt(iosVersion, 10) <= 15;
    }
  }

  async initialize() {
    await this._ready;
  }

  isLandscape() {
    if (screen.orientation && screen.orientation.type) {
      return screen.orientation.type.startsWith("landscape");
    } else if (typeof window.orientation !== "undefined") {
      // Note: window.orientation is deprecated, use with caution
      return Math.abs(window.orientation) == 90; // 90 또는 -90이면 가로 모드
    } else {
      Utils.logWarning("Unable to determine orientation");
      return false;
    }
  }

  get pictureWidth() {
    return this._pictureWidth;
  }

  set pictureWidth(value) {
    //값이 0으로 넘어오는 경우가 있음. 실제 대입하는 값은 0이 아닌데..
    if (value > 0) {
      this._pictureWidth = value;
    }
  }

  get pictureHeight() {
    return this._pictureHeight;
  }

  set pictureHeight(value) {
    if (value > 0) {
      this._pictureHeight = value;
    }
  }

  async setOptions(options) {
    this._options = options;
    // 내부 옵션 구조분해 할당
    const {
      useCapOcr,
      useDetect,
      rtcDetectTime,
      regisClassType,
      accessMode,
      containerId,
      isreloadBtn,
      isreloadMsg,
      recogTryLimit,
      albumImage,

      document: { totalPages, convertTo, jpegQuality },
    } = options;

    Object.assign(this, {
      _useCapOcr: useCapOcr,
      _useDetect: useDetect,
      _rtcDetectTime: rtcDetectTime,
      _regisClassType: regisClassType,
      _accessMode: accessMode,
      _totalPages: totalPages,
      _convertTo: convertTo,
      _jpegQuality: jpegQuality,
      _isreloadBtn: isreloadBtn,
      _isreloadMsg: isreloadMsg,
      recogTryLimit: recogTryLimit,
      _albumImage: albumImage,
    });

    // 탐지 설정 초기화
    Object.assign(this, {
      _takePhoto: false,
      _ioU: false,
      _focusCheck: true, // Laplacian 사용 여부
    });

    try {
      Utils.showLoading();
    } catch (err) {
      Utils.logError("Error requesting camera permission:", err);
      return;
    }

    // UI 요소에서 값 추출
    this._methodValue = document.getElementById("methodId")?.value ?? null;
    this._imageDiv = document.getElementById("imageDiv")?.value ?? null;

    // WebCamera 초기화 (최초 1회)
    if (!this._webCameraOptionsSet) {
      this._initUseCapOcr = this._useCapOcr;
      this._webCameraOptionsSet = true;
      this.initializeComponents(containerId);
    }
  }

  initializeComponents(containerId) {
    this.createElement(containerId);
    this._initEventHandler();
    this.unload();
  }

  _getContainerSize() {
    let oldDisplay = null;
    oldDisplay = this._container.style.display;
    this._container.style.display = "block";
    // Get the container size
    const sizeObj = {
      width: this._container.clientWidth,
      height: this._container.clientHeight, // Use offsetHeight instead of clientHeight
    };

    this._container.style.display = oldDisplay;

    return sizeObj;
  }

  _calcVideoSize() {
    const containerSize = this._getContainerSize();
    const videoRatio = Utils.getVideoRatio(this._ocrType); // 가로 모드 비율 (width > height)

    // [수정] 가로 화면일 때는 기준이 되는 가로폭(availableWidth)을 전체 화면의 40%로 축소
    // 세로 화면일 때는 기존 마진 정책 유지
    const isLandscape = containerSize.width > containerSize.height;
    const sideMargin = this._ocrType === config.OCR_TYPE.QRCODE ? 120 : 40;

    const availableWidth = isLandscape ? (containerSize.width * 0.5) : (containerSize.width - sideMargin);

    const wl = availableWidth / videoRatio.width;
    const hl = containerSize.height / videoRatio.height;
    const minLength = Math.min(wl, hl);

    // 원래 가로 비율(videoRatio)을 유지하면서 크기만 40% 수준으로 축소된 결과 반환
    return {
      width: minLength * videoRatio.width,
      height: minLength * videoRatio.height,
    };
  }

  handleResizeEvent() {
    if (this._isResizing) return;

    // 1. 화면에 보이지 않거나 (display: none)
    // 2. 비디오 객체가 없거나
    // 3. 비디오가 현재 '일시정지(pause)' 상태라면 리사이즈 로직 중단
    const isHidden = !this._videoWrapper || window.getComputedStyle(this._videoWrapper).display === "none" || this._videoWrapper.offsetParent === null;

    const isPaused = !this._videoRef || this._videoRef.paused;

    if (isHidden || isPaused) {
      Utils.logInfo(`Resize ignored: ${isHidden ? "Wrapper hidden" : "Video is paused"}`);
      return;
    }

    this._isResizing = true;

    // 기존 로직 수행 (카메라 재로드 및 시작)
    this.loadVideoSource();

    setTimeout(() => {
      this._resetUI();
      this._isStartInProgress = false;
      this.start();
      this._isResizing = false;
    }, 300);
  }

  _initEventHandler() {
    this._videoRef.addEventListener("loadedmetadata", (event) => {
      this.dispatchEvent(new Event("webcamready"));
    });

    window.addEventListener("beforeunload", (event) => {
      this.unload();
    });
    window.addEventListener("contextmenu", function (e) {
      e.preventDefault();
    });
    window.addEventListener("resize", () => this.handleResizeEvent());
    window.addEventListener("orientationchange", () => this.handleResizeEvent());
  }

  async getUsers() {
    try {
      const constraints = await this.getConstraints();
      const mediaStream = await navigator.mediaDevices.getUserMedia(constraints);

      // this._mediaStream = mediaStream;
      return mediaStream;
    } catch (err) {
      if (err.name === "NotAllowedError") {
        // 기존 오류 메시지에 추가 설명을 덧붙여 새로운 에러 객체를 생성하여 던지기
        throw new Error(`${err.message}. \n\n카메라 사용 권한을 허용해 주세요.`);
      } else if (err.name === "NotReadableError") {
        // 'Device in use' 에러에 대한 추가 메시지 처리
        throw new Error(`${err.message}. \n\n다른 탭에서 카메라를 사용 중입니다.\n카메라를 종료하고 다시 시도하세요.`);
      } else {
        throw new Error(`An error occurred: ${err.message}`);
      }
    }
  }

  async loadVideoSource() {
    if (this.controller) {
      this.controller.abort();
      return;
    }

    this.controller = new AbortController();
    const signal = this.controller.signal;

    try {
      // OCR 타입이 바뀌었으면 초기화하지 않음
      if (this._prevOcrType == this._ocrType) {
        return;
      }

      // 1. 첫 스트림을 받아오기 (초기화 목적)
      const initialStream = await this.getUsers({ signal });
      initialStream.getTracks().forEach((track) => track.stop());

      // 2. 스트림 해제 + 대기
      await this.unload();
      await new Promise((resolve) => setTimeout(resolve, 200));

      // 3. 실제 사용할 스트림 연결
      const mediaStream = await this.getUsers({ signal });
      this._stream_settings = mediaStream.getVideoTracks()[0].getSettings();
      this._mediaStream = mediaStream;

      if ("srcObject" in this._videoRef) {
        this._videoRef.srcObject = this._mediaStream;
      } else {
        this._videoRef.src = URL.createObjectURL(this._mediaStream);
      }

      this._videoRef.addEventListener("playing", () => {
        Utils.logInfo("Video starts playing -> poster removed");
        this._videoRef.removeAttribute("poster");
      }, { once: true });

      await new Promise((resolve) => {
        this._videoRef.addEventListener("canplay", resolve, { once: true });
        Utils.hideLoading();
      });

      const ensureVideoReady = async () => {
        for (let i = 0; i < 10; i++) {
          // 최대 10번 시도
          if (this._videoRef.readyState >= 3 && this._videoRef.videoWidth > 0 && this._videoRef.videoHeight > 0) {
            return true;
          }
          await new Promise((r) => setTimeout(r, 100)); // 100ms 대기 후 재확인
        }
        return false;
      };

      const ready = await ensureVideoReady();
      if (!ready) {
        console.warn("Video dimensions not ready, forcing reload.");
        return this.loadVideoSource(); // 재시도
      }

      this._prevOcrType = this._ocrType; // 변경사항 저장

      await this._videoRef.play().catch((err) => console.error("play() failed:", err));

      requestAnimationFrame(() => {
        this._videoRef.style.transform = "translateZ(0)"; // Safari fix
      });
    } catch (err) {
      if (err.name === "AbortError") {
        Utils.logInfo("Video loading was intentionally cancelled.");
        return;
      }
      Utils.logError(err);
      Utils.showAlert(err);
      throw err;
    } finally {
      // 작업 완료 후 컨트롤러 초기화
      this.controller = null;
    }
  }

  clearVideoAndCanvas() {
    try {
      // 1. 카메라 스트림 트랙 정지
      if (this._mediaStream) {
        this._mediaStream.getTracks().forEach((track) => {
          track.stop();
          Utils.logInfo(`Track ${track.kind} stopped.`);
        });
        this._mediaStream = null;
      }

      // 2. 비디오 엘리먼트 초기화
      if (this._videoRef) {
        this._videoRef.pause();
        this._videoRef.srcObject = null;
        this._videoRef.removeAttribute("src");
        this._videoRef.load(); // 비디오 리소스를 확실히 비우기 위해 로드 호출
      }

      // [추가] 2-1. 비디오 태그 내부(자식 요소들) 삭제
      const videoTag = document.getElementById("kwcVideo");
      if (videoTag) {
        videoTag.innerHTML = ""; // <source> 태그 등 내부 요소 전부 제거
        Utils.logInfo("kwcVideo tag content has been cleared.");
      }

      // 3. 캔버스 화면 지우기
      const canvas = document.getElementById("kwcCanvas");
      if (canvas) {
        const context = canvas.getContext("2d");
        context.clearRect(0, 0, canvas.width, canvas.height);
      }

      // 4. 가이드 영역 초기화
      const guideArea = document.getElementById("kwcGuideArea");
      if (guideArea) {
        const images = guideArea.querySelectorAll("img");
        images.forEach((img) => img.remove());
        Utils.logInfo("Guide area images have been cleared.");
      }

      // 5. 컨트롤러 중단
      if (this.controller) {
        this.controller.abort();
        this.controller = null;
      }

      if (this._title) {
        this._title.innerHTML = "";
      }

      Utils.logInfo("Camera, Canvas, VideoContent, and GuideArea cleared.");
    } catch (err) {
      Utils.logError("Error while clearing video source:", err);
    }
  }

  async getConstraints() {
    const devices = await navigator.mediaDevices.enumerateDevices();
    const filteredDevices = devices.filter((v) => {
      return v.kind == "videoinput";
    });

    for (const device of filteredDevices) {
    }

    const deviceId = filteredDevices[filteredDevices.length - 1].deviceId;

    // 3840*2160 -> 4k
    const constraints = {
      audio: false,
      video: {
        facingMode: { ideal: "environment" },
        zoom: true,
        focusMode: "continuous",
        width: { ideal: 1920 },
        height: { ideal: 1080 },
        resizeMode: "none",
      },
    };

    // 문서촬영 시, QHD 화질 사용
    // if (Utils.OcrTypeConfig.document.includes(this._ocrType)) {
    //   constraints.video.width = { ideal: 2560 };
    //   constraints.video.height = { ideal: 1440 };
    // }

    if (["0014", "0015"].includes(this._methodValue) && this._imageDiv == "src1") {
      constraints.video.facingMode = { ideal: "user" }; // 정면 카메라
    }
    // Android
    if (/Android/i.test(navigator.userAgent)) {
      if (filteredDevices.length > 0) {
        const backCameras = filteredDevices.filter((d) => /back|rear|environment|facing back/i.test(d.label));
        const targetDevices = backCameras.length > 0 ? backCameras : filteredDevices;
        constraints.video.deviceId = targetDevices[targetDevices.length - 1].deviceId;
      }
    }

    if (navigator.userAgent.includes("Firefox")) {
      this._isFireFox = true;
    }
    return constraints;
  }

  async changeOCR(ocrType) {
    if (ocrType === undefined) return { success: false };

    this._ocrType = ocrType;
    this._isReversed = false;
    this._guideArea.style.backgroundColor = "transparent";

    if (this._ocrType == 27) {
      this._qrCheck = true;
    }

    if (this._useCapOcr != 3 && this._useDetect) {
      const res = await this._ocrModuleManager.load(ocrType);

      if (!res.success) {
        Utils.hideLoading();
        return {
          success: false,
          error: res.error,
        };
      }
    }

    this._videoRef.style.objectFit = this._isSwapWH ? "fill" : "cover";
    // this._resetUI();

    if (this._useCapOcr != 3) {
      await this.loadVideoSource();
    }

    this._resetUI();
    return { success: true };
  }

  _resetLetterboxLayout(hspace, vspace, videoSize) {
    const containerSize = this._getContainerSize();

    if (containerSize.width > containerSize.height) {
      this._vertical = 0;
      this._guideArea.appendChild(this._title);

      // 정중앙 배치를 위한 상하좌우 여백 계산
      const totalHSpace = containerSize.width - videoSize.width;
      const totalVSpace = containerSize.height - videoSize.height;

      const eachLeftRightWidth = totalHSpace / 2;
      const eachTopBottomHeight = totalVSpace / 2;

      // 좌측 레터박스 (인라인 배치 대신 absolute로 좌표 잡는 게 더 안전합니다)
      Object.assign(this._letterboxTop.style, {
        display: "block",
        position: "absolute",
        top: "0px",
        left: "0px",
        width: `${eachLeftRightWidth}px`,
        height: "100%",
      });

      // 비디오 래퍼 (가로 비율 유지된 크기 그대로 세팅하고, 정확히 상하좌우 정중앙 배치)
      Object.assign(this._videoWrapper.style, {
        display: "block",
        position: "absolute",
        left: `${eachLeftRightWidth}px`,
        top: `${eachTopBottomHeight}px`, // 상하 정중앙 정렬 추가
        width: `${videoSize.width}px`,
        height: `${videoSize.height}px`, // 100% 대신 실제 비디오 높이 부여
      });

      // 우측 레터박스
      Object.assign(this._letterboxBottom.style, {
        display: "block",
        position: "absolute",
        top: "0px",
        left: null,
        right: "0px",
        width: `${eachLeftRightWidth}px`,
        height: "100%",
      });

    } else {
      this._vertical = 1;
      if (this._title && this._guideArea) {
        this._letterboxTop.appendChild(this._title);
      }

      if (this._timerEl && this._letterboxBottom) {
        this._letterboxBottom.appendChild(this._timerEl);
      }

      const totalHeight = vspace + videoSize.height;
      const actualVSpace = totalHeight - videoSize.height;
      const sideMargin = this._ocrType === config.OCR_TYPE.QRCODE ? 120 : 40;

      Object.assign(this._letterboxTop.style, {
        display: "block",
        width: "100%",
        height: `${actualVSpace / 2}px`,
        top: "0px",
        bottom: null,
        left: "0px",
        right: null,
      });

      Object.assign(this._videoWrapper.style, {
        display: "block",
        width: `${videoSize.width}px`,
        height: `${videoSize.height}px`,
        left: `${sideMargin / 2}px`,
        top: `${actualVSpace / 2}px`,
      });

      Object.assign(this._letterboxBottom.style, {
        display: "block",
        width: "100%",
        height: `${actualVSpace / 2}px`,
        left: "0px",
        right: null,
        top: null,
        bottom: "0px",
      });
    }
  }

  _resetUI() {
    this._guideArea.innerHTML = "";
    this._guideArea.className = "";
    this._title.innerHTML = "";

    [("#variance", "#variance-img", "#regisMsg")].forEach((selector) => {
      document.querySelectorAll(selector).forEach((el) => el.remove());
    });

    this._guideArea.style.backgroundColor = "transparent";
    this._vertical = null;

    const containerSize = this._getContainerSize();
    const videoSize = this._calcVideoSize();
    const hspace = containerSize.width - videoSize.width;
    const vspace = containerSize.height - videoSize.height;

    this._title.innerHTML = "가이드 영역에 맞춰 촬영해주세요.";
    if (Utils.OcrTypeConfig.document.includes(this._ocrType)) {
      this._title.classList.add("document");
      this._title.classList.remove("affine");
    } else {
      this._guideUI.style.display = "flex";
    }

    const oldContainerDisplay = this._container.display;
    this._container.display = "none";
    this._setGuideByOcrType();

    this._resetLetterboxLayout(hspace, vspace, videoSize);

    this._videoRef.style.width = `${videoSize.width}px`;
    this._videoRef.style.height = `${videoSize.height}px`;

    this._container.display = oldContainerDisplay;

    const label = Utils.getOcrLabelByType(this._ocrType);
    const backButton = document.querySelector(".back-btn-title");
    backButton.innerHTML = `${label}`;
  }

  _setGuideByOcrType() {
    if (this._isreloadBtn) {
      this.reloadBtn.style.display = "flex"; // 기본 숨김
      this.reloadMsg.style.display = "block";
    }
    const strategyKey = Utils.guideStrategyMap[this._ocrType];
    const strategy = GuideStrategies[strategyKey];
    if (strategy) strategy(this);
  }

  getCropImage(ocrType) {
    // 비디오 준비 안 됐으면 바로 리턴
    if (!this._videoRef || this._videoRef.readyState < 3) {
      console.warn("getCropImage(): 비디오 준비 안 됨 → 캡쳐 중단");
      this._captureinProgress = false;
      return null;
    }

    this.pictureWidth = this._videoRef.videoWidth;
    this.pictureHeight = this._videoRef.videoHeight;

    if (!this.pictureWidth || !this.pictureHeight) {
      console.warn("getCropImage(): 영상 크기 없음 → 캡쳐 중단");
      this._captureinProgress = false;
      return null;
    }

    let originWidth = this.pictureWidth;
    let originHeight = this.pictureHeight;

    const swapSize = this._isSwapWH && !this.isLandscape();
    if (swapSize) {
      this.pictureWidth = 1080;
      this.pictureHeight = 1920;
    }

    const coordinates = Utils.convertGuideAreaCoordinates(this._guideArea, this.pictureWidth, this.pictureHeight, ocrType);

    let { x: cropX, y: cropY, width: cropWidth, height: cropHeight } = coordinates;

    // 좌표/크기 검증
    if (!Number.isFinite(cropX) || !Number.isFinite(cropY) || !Number.isFinite(cropWidth) || !Number.isFinite(cropHeight) || cropWidth <= 0 || cropHeight <= 0) {
      console.warn("getCropImage(): 잘못된 crop 좌표 → 캡쳐 중단", { cropX, cropY, cropWidth, cropHeight });
      this._captureinProgress = false;
      return null;
    }

    const canvasRef = this._canvasRef;
    canvasRef.width = this.pictureWidth;
    canvasRef.height = this.pictureHeight;

    const ctx = canvasRef.getContext("2d", { willReadFrequently: true });

    ctx.drawImage(this._videoRef, 0, 0, this.pictureWidth, this.pictureHeight);
    const originData = ctx.getImageData(0, 0, originWidth, originHeight);

    const croppedCanvas = document.createElement("canvas");
    const croppedCtx = croppedCanvas.getContext("2d", { willReadFrequently: true });

    croppedCanvas.width = cropWidth;
    croppedCanvas.height = cropHeight;

    croppedCtx.drawImage(canvasRef, cropX, cropY, cropWidth, cropHeight, 0, 0, cropWidth, cropHeight);

    const imageData = croppedCtx.getImageData(0, 0, cropWidth, cropHeight);

    if (ctx) {
      ctx.clearRect(0, 0, canvasRef.width, canvasRef.height);
    }
    if (croppedCtx) {
      croppedCtx.clearRect(0, 0, croppedCanvas.width, croppedCanvas.height);
    }

    return { originData, imageData, originWidth, originHeight };
  }

  createElement(containerId) {
    if (this._isCreated) return;
    this._isCreated = true;

    this._container = document.querySelector(containerId);
    if (!this._container) throw new Error(`Container with selector '${containerId}' not found.`);

    // 1. 상단 레이어박스 (가이드 UI, 타이틀)
    this._createTopLetterbox();

    // 2. 비디오 및 가이드 영역 (메인 화면)
    this._createVideoSection();

    // 3. 하단 레이어박스 (타이머, 재로드 버튼)
    this._createBottomLetterbox();
  }

  /** [Step 1] 상단 Letterbox 및 가이드 메시지 생성 */
  _createTopLetterbox() {
    this._letterboxTop = Utils._createDiv("background top", { display: "block" });

    // 가이드 UI 컨테이너
    this._guideUI = Utils._createDiv(null, null, "guideUI");
    this._letterboxTop.appendChild(this._guideUI);

    // 타이틀
    this._title = Utils._createDiv(null, null, "letterTop-title");
    this._title.setAttribute("role", "alert");
    this._title.setAttribute("aria-live", "assertive");
    this._title.setAttribute("tabindex", "0");
    this._letterboxTop.appendChild(this._title);

    // 가이드 메시지들
    const guideTexts = this._guideTexts || [];
    for (let i = 1; i <= 3; i++) {
      const container = Utils._createDiv(`guide-message guide${i}`);
      const text = document.createElement("p");
      text.textContent = guideTexts[i - 1] || "";
      container.appendChild(text);
      this._guideUI.appendChild(container);
    }

    this._container.appendChild(this._letterboxTop);
  }

  /** [Step 2] 비디오 및 캔버스 영역 생성 */
  _createVideoSection() {
    this._videoWrapper = Utils._createDiv("wrapper", { position: "absolute" });

    // 비디오 태그 설정
    this._videoRef = document.createElement("video");
    this._videoRef.id = "kwcVideo";
    this._videoRef.classList.add("video");
    Object.assign(this._videoRef, {
      autoplay: true,
      WebKitPlaysInline: true,
      muted: true,
    });
    this._videoRef.setAttribute("playsinline", true);
    this._videoRef.setAttribute("aria-hidden", "true");

    // 초기 포스터(검정 배경) 설정
    this._videoRef.setAttribute("poster", Utils._createBlackPoster());

    // 가이드 캔버스 및 영역
    this._canvasRef = document.createElement("canvas");
    this._canvasRef.id = "kwcCanvas";
    this._canvasRef.classList.add("canvas");
    this._canvasRef.style.display = "none";

    this._guideArea = Utils._createDiv(
      null,
      {
        position: "absolute",
        width: "100%",
        height: "100%",
        overflow: "hidden",
      },
      "kwcGuideArea",
    );

    // 조립
    this._videoWrapper.append(this._videoRef, this._canvasRef, this._guideArea);
    this._container.appendChild(this._videoWrapper);
  }

  /** [Step 3] 하단 Letterbox 및 타이머/버튼 생성 */
  _createBottomLetterbox() {
    this._letterboxBottom = Utils._createDiv("background bottom");

    // 타이머 관련
    this._timerEl = Utils._createDiv(null, null, "ocr-timer");
    this._timerdoc = Utils._createDiv(null, null, "ocr-timer-document");

    this._setupProgressRing(); // 타이머 SVG 로직 분리

    // 재로드 버튼 (조건부)
    if (this._isreloadBtn) {
      this._createReloadWrapper();
    }
    // if (this._albumImage) {
    //   this._createAlbumWrapper();
    //   this._bindAlbumEvents();
    // }

    this._letterboxBottom.append(this._timerEl);
    this._container.appendChild(this._letterboxBottom);
  }

  _setupProgressRing() {
    const svgNS = "http://www.w3.org/2000/svg";
    const svg = document.createElementNS(svgNS, "svg");
    svg.classList.add("progress-ring");
    svg.setAttribute("width", "60");
    svg.setAttribute("height", "60");

    const createCircle = (cls, stroke) => {
      const c = document.createElementNS(svgNS, "circle");
      c.classList.add(cls);
      c.setAttribute("stroke", stroke);
      c.setAttribute("stroke-width", "4");
      c.setAttribute("fill", "transparent");
      c.setAttribute("r", "17");
      c.setAttribute("cx", "30");
      c.setAttribute("cy", "30");
      return c;
    };

    svg.append(createCircle("progress-ring__background", "#ccc"), createCircle("progress-ring__circle", "rgb(65, 184, 131)"));

    const textEl = Utils._createDiv("timer-text");
    textEl.textContent = "0:00";

    this._timerdoc.append(svg, textEl);
  }

  _createReloadWrapper() {
    const wrapper = Utils._createDiv("reload-wrapper", null, "reloadWrapper");

    this.reloadMsg = document.createElement("span");
    this.reloadMsg.id = "reloadMsg";
    this.reloadMsg.className = "reload-msg";
    this.reloadMsg.textContent = this._isreloadMsg;

    this.reloadBtn = document.createElement("button");
    this.reloadBtn.id = "reloadCameraBtn";
    this.reloadBtn.className = "camera-reload-btn";

    wrapper.append(this.reloadMsg, this.reloadBtn);
    this._letterboxBottom.appendChild(wrapper);
  }

  _createAlbumWrapper() {
    // 1. 전체를 감싸는 래퍼 생성
    const albumWrapper = Utils._createDiv("album-wrapper", null, "albumWrapper");

    // 2. 앨범 아이콘 이미지 생성 (album.svg)
    this.albumIcon = document.createElement("img");
    this.albumIcon.src = "../../images/album.svg"; // 실제 경로로 수정해주세요
    this.albumIcon.id = "albumIcon";
    this.albumIcon.className = "album-icon";
    this.albumIcon.alt = "앨범 선택";

    // 3. 버튼 생성
    this.albumBtn = document.createElement("button");
    this.albumBtn.id = "albumSelectBtn";
    this.albumBtn.className = "album-select-btn";

    // 버튼 안에 아이콘 삽입
    this.albumBtn.appendChild(this.albumIcon);

    // 4. 문구(span)가 필요하다면 추가 (선택사항)
    this.albumMsg = document.createElement("span");
    this.albumMsg.id = "albumMsg";
    this.albumMsg.className = "album-msg";
    // this.albumMsg.textContent = "앨범에서 선택";

    // 5. 조립 후 하단 레이어박스에 부착
    albumWrapper.append(this.albumBtn, this.albumMsg);
    this._letterboxBottom.appendChild(albumWrapper);
  }

  _bindAlbumEvents() {
    if (!this.albumBtn) return;

    this.albumBtn.onclick = () => {
      // 1. 동적으로 input file 생성 (화면엔 안 보임)
      const fileInput = document.createElement("input");
      fileInput.type = "file";
      fileInput.accept = "image/*"; // 이미지 파일만 선택 가능하게 제한

      // 2. 파일 선택 완료 시 이벤트
      fileInput.onchange = async (e) => {
        const file = e.target.files[0];
        if (!file) return;

        try {
          Utils.showLoading(); // 처리 중 로딩 표시

          // 3. 파일을 데이터(Base64 또는 Image객체)로 변환하여 변수에 담기
          this._selectedAlbumImage = await Utils.handleFileToImageData(file);

          this._handleGuideBoxEffect(this._selectedAlbumImage);

          Utils.logInfo("앨범 이미지 선택 완료:", file.name);

          const eventDetail = {
            cropData: this._selectedAlbumImage,
            imageWidth: this._selectedAlbumImage.width,
            imageHeight: this._selectedAlbumImage.height,
            currentCount: 1,
            totalCount: 1,
            ocrType: this._ocrType,
            resultCode: 0,
          };

          this.dispatchEvent(new CustomEvent("imagecaptured", { detail: eventDetail }));
        } catch (error) {
          Utils.logError("이미지 로드 실패:", error);
        } finally {
          Utils.hideLoading();
        }
      };

      // 5. 강제로 클릭 이벤트 발생시켜서 앨범 열기
      fileInput.click();
    };
  }

  async unload() {
    if (this._mediaStream) {
      this.clearVideoAndCanvas();
      // this._mediaStream.getTracks().forEach((track) => track.stop());
      // this._mediaStream = null;
      // this._videoRef.srcObject = null;
    }
  }

  async stop() {
    this._captureinProgress = false;
    if (this._detectTimeoutId) {
      clearTimeout(this._detectTimeoutId);
      this._detectTimeoutId = null;
    }
    if (this._orientationChangeButton) {
      this._orientationChangeButton.remove(); // DOM에서 버튼 제거
      this._orientationChangeButton = null; // 참조를 null로 설정하여 메모리 해제
    }
  }

  async start() {
    if (this._isStartInProgress) return;
    this._isStartInProgress = true;

    try {
      if (!this._videoRef || this._videoRef.readyState < 2) {
        console.warn("start() 호출됨: 비디오 스트림 없음 → 캡쳐 중단");
        setTimeout(() => {
          this._isStartInProgress = false; // 다음 호출 허용
          this.start();
        }, 500);
        return;
      }
      this._applyManualCaptureMode();
      await this._applyStartDelays();
      this._initCaptureState();

      if (!this._successfulDetectionCalled) {
        await this._handleCaptureByOcrType();
      }
    } finally {
      this._isStartInProgress = false;
    }
  }

  async _handleCaptureByOcrType() {
    if (Utils.OcrTypeConfig._document.includes(this._ocrType)) {
      await this.processDocumentCapture(true);
    } else if (Utils.OcrTypeConfig.dscan.includes(this._ocrType)) {
      await this._handleDScanCapture();
    } else {
      await this.processCapture();
    }
  }

  async _handleDScanCapture() {
    if (this._useCapOcr === 2) {
      await this.processDocumentCapture();
    } else {
      await this.processDocumentCapture(true);
    }
  }

  _applyManualCaptureMode() {
    const options = this._options;

    if ([2, 3, 4].includes(this._useCapOcr) && !Utils.OcrTypeConfig.dscan.includes(this._ocrType)) {
      // this._useDetect = false;
      this._options.recogTryLimit = 1;
      this.forceStopTimerDisplay();
    }
  }

  async _applyStartDelays() {
    if (this._options.rtcStartDelay > 0) {
      await Utils.delay(this._options.rtcStartDelay);
    }

    if (this._accessMode) {
      await Utils.delay(1800);
    }
  }

  _initCaptureState() {
    this._captureinProgress = true;
    this._detectInProgress = false;
    this._successfulDetectionCalled = false;
    // this._currentCaptueCount = 0;
    this._detectRqCount = 0;
    this._timeoutTriggered = false;
    if (this._isIOS && this._isIOS15under) {
      this._key = Utils._generateUUIDFallback();
    } else {
      this._key = crypto.randomUUID();
    }

    this._lastCaptureResult = null;
    this._documentCaptureDone = false;
    this._qrPageIndex = 0;
  }

  async progress() {
    // await this._videoRef.play();
    await Utils.delay(100);
    this._resetUI();
    this._captureinProgress = true;
    const now = Date.now();
    const remainingTime = this._timeoutAt - now; // timeoutAt을 저장해두어야 함
    await this._handleCaptureByOcrType();
  }

  startTimerDisplay() {
    if (this._timerInterval) return;
    this._timerInterval = setInterval(() => {
      const now = Date.now();
      const remaining = this._timeoutAt - now;
      const seconds = Math.ceil(remaining / 1000);

      if (seconds !== this._lastLoggedSecond && remaining > 0) {
        this._lastLoggedSecond = seconds;
        this._timerEl.style.display = "flex";

        const colorClass = seconds <= 10 ? 'class="red-text"' : "";

        // 1. SVG 파일 경로 지정 (성빈님의 이미지 폴더 경로에 맞춰 수정하세요)
        const iconPath = "../images/timer_icon.svg";

        // 2. img 태그로 삽입
        const timerIcon = `
                <img src="${iconPath}" alt="timer">`;

        this._timerEl.innerHTML = `남은시간&nbsp;<span ${colorClass}>${seconds}</span>초`;
      }

      if (remaining <= 0) {
        this.forceStopTimerDisplay(true);
      }
    }, 250);
  }

  startTimerDocDisplay() {
    if (this._timerInterval) return;
    this._timerdoc.classList.remove("hide");
    this._timerdoc.classList.add("show");

    const totalTime = this._rtcDetectTime; // 전체 타이머 길이(ms)
    const circle = document.querySelector(".progress-ring__circle");
    const textEl = document.querySelector(".timer-text");

    if (!circle || !textEl) return;
    const radius = circle.r.baseVal.value;
    const circumference = 2 * Math.PI * radius;

    circle.style.strokeDasharray = `${circumference} ${circumference}`;
    circle.style.strokeDashoffset = 0;

    this._timeoutAt = Date.now() + totalTime;

    this._timerInterval = setInterval(() => {
      const now = Date.now();
      const remaining = this._timeoutAt - now;

      if (remaining <= 0) {
        this.forceStopTimerDisplay();
        textEl.textContent = "0:00";
        circle.style.stroke = "red";
        circle.style.strokeDashoffset = circumference;
        return;
      }

      // 남은 시간 표시
      const seconds = Math.ceil(remaining / 1000);
      const minutes = Math.floor(seconds / 60);
      const displaySeconds = String(seconds % 60).padStart(2, "0");
      textEl.textContent = `${minutes}:${displaySeconds}`;

      // 진행률 계산
      const progress = remaining / totalTime;
      const offset = circumference * (1 - progress);
      circle.style.strokeDashoffset = offset;

      // 색상 변경 (10초 이하일 때 빨간색)
      if (seconds <= 10) {
        circle.style.stroke = "red";
        textEl.style.color = "#b22323";
      } else {
        circle.style.stroke = "rgb(65, 184, 131)";
        textEl.style.color = "#333";
      }
    }, 250);
  }

  async forceStopTimerDisplay(isTimeout = false) {
    // 기본값은 false로 설정
    if (this._timerInterval) {
      clearInterval(this._timerInterval);
      this._timerInterval = null;
    }

    this._timeoutAt = null;
    this._lastLoggedSecond = null;

    // [수정] 인자가 true일 때만 타임아웃 상태로 변경
    if (isTimeout) {
      this._timeoutTriggered = true;
    }

    // 남은 시간 텍스트 즉시 클리어 (공통 UI 정리)
    if (this._timerEl) {
      this._timerEl.innerHTML = "";
      this._timerEl.style.display = "none";
    }

    if (this._timerdoc) {
      this._timerdoc.innerHTML = "";
    }

    // [수정] dscan 전용 타임아웃 이벤트도 인자가 true일 때만 발송
    if (isTimeout && Utils.OcrTypeConfig.dscan.includes(this._ocrType)) {
      this._captureinProgress = false;
      this._letterboxBottom.innerHTML = "";
      this.dispatchEvent(new CustomEvent("timeout", { detail: { timeout: true, ocrType: this._ocrType } }));
    }
  }

  resetTimerDisplay() {
    // 인터벌 해제
    if (this._timerInterval) {
      clearInterval(this._timerInterval);
      this._timerInterval = null;
    }

    // 타이머 상태 초기화
    this._timeoutAt = null;
    this._lastLoggedSecond = null;
    this._timeoutTriggered = false;

    // 표시 초기화
    if (this._timerdoc) {
      this._timerdoc.classList.add("hide");
      const textEl = this._timerdoc.querySelector(".timer-text");
      if (textEl) textEl.textContent = "0:00";

      const circle = this._timerdoc.querySelector(".progress-ring__circle");
      if (circle) {
        circle.style.strokeDashoffset = 0;
        circle.style.stroke = "#41b883"; // 초기 색
      }
    }
  }

  async processDocumentCapture(isRetake = true) {
    if (this._detectInProgress || !this._captureinProgress) {
      // Utils.logWarning("Detection already in progress or capture not active.");
      return;
    }

    if (!isRetake) {
      this._prepareMultiPageCapture();
    }

    let result = null;
    this._timeoutAt = Date.now() + this._rtcDetectTime;
    while (this._captureinProgress) {
      // this._qrCheck = false;
      if (this._useCapOcr == 5 && Utils.OcrTypeConfig.dscan.includes(this._ocrType)) {
        const switchBtn = document.getElementById("switch_btn");
        if (switchBtn) {
          switchBtn.style.display = "block";
        }
      }

      if (this._ocrType == 27 && this._useCapOcr == 2) {
        result = await this._multiCaptureIteration(true);
        Utils.hideLoading();
        this._captureInProgress = true;
        this._useCapOcr = 5;
      } else {
        result = await this._performCaptureIteration(true);
      }
      this._lastCaptureResult = result;

      if (this._useCapOcr != 1 && this._useCapOcr != 5) {
        this._captureinProgress = false;
      }

      if (result?.isSuccess) {
        this._captureinProgress = false;
        await this._finalizeSuccessfulDocumentCapture(result);
        if (!Utils.OcrTypeConfig.dscan.includes(this._ocrType)) {
          await this.applyBlinkEffect();
          this._handleGuideBoxEffect(result.cropImageData);
          // }
        }

        // 반복 계속 진행: break 제거
      }

      // await Utils.delay(30);
    }
  }

  async _handleQrResult(result, isRightShot) {
    // 1. 데이터 추출 및 기본 검증
    const qrData = result?.resultCode?.resultJSON?.formResult;
    if (qrData?.type !== "Bar/QR-Code") {
      console.warn("QR/바코드가 아님, 처리 생략");
      return;
    }

    const barcodeValue = qrData.fieldResults?.[1]?.value?.split("^^")?.[0] || "";
    const pageNum = Number(barcodeValue.slice(-1));

    // 2. 유효성 검사 (중복 및 문서 타입)
    if (Utils._checkDuplicate(barcodeValue)) return;
    this._updateDocStatus(barcodeValue);

    // 3. 페이지 결과 객체 확보 (find 또는 create)
    const pageResult = this._getOrCreatePageResult(pageNum);
    const position = this._firstPage ? (isRightShot ? "right" : "left") : "right";

    // 4. QR 데이터 저장
    const isStored = this._storeQrData(pageResult, position, result.resultCode.resultJSON);
    if (!isStored && this._firstPage) return;

    // 5. 최종 유효성 판단 및 후처리
    this._isQrValid = this._validatePage(pageResult);
    this._finalizeProcess(pageResult, pageNum);
  }

  _updateDocStatus(value) {
    this._docType = value.split("^").pop();
    const firstPagePrefixes = ["06390001", "06340001", "06380001"];
    this._firstPage = firstPagePrefixes.some((prefix) => value.includes(prefix));
  }

  _getOrCreatePageResult(pageNum) {
    let page = this._qrFind.find((p) => p.page === pageNum);
    if (!page) {
      page = { page: pageNum, results: [], valid: false };
      this._qrFind.push(page);
    }
    return page;
  }

  _storeQrData(pageResult, position, qrValue) {
    const existing = pageResult.results.find((r) => r.position === position);
    if (existing) {
      console.warn(`이미 해당 위치(${position})에 QR 존재`);
      return false;
    }
    pageResult.results.push({ position, result: qrValue });
    return true;
  }

  _validatePage(pageResult) {
    if (this._firstPage) {
      const hasRight = pageResult.results.some((r) => r.position === "right");
      const hasLeft = pageResult.results.some((r) => r.position === "left");
      return hasRight && hasLeft;
    }
    return pageResult.results.length > 0;
  }

  _finalizeProcess(pageResult, pageNum) {
    pageResult.valid = this._isQrValid;

    if (this._isQrValid) {
      this._title.innerHTML = "QR 인식 완료";
      this._qrCheck = this._firstPage = false;
      this._qrShotCount = 0;

      // 결과 업데이트
      const idx = this._qrResults.findIndex((p) => p.page === pageNum);
      if (idx !== -1) this._qrResults[idx] = pageResult;
      else this._qrResults.push(pageResult);

      this._qrPageIndex++;
    } else {
      this._title.innerHTML = "QR이 인식되지 않았습니다. 다시 촬영해주세요.";
      Utils.hideLoading();
    }
  }

  async processCapture(remainingTime = this._rtcDetectTime) {
    if (this._detectInProgress || !this._captureinProgress) return;

    this._setupCaptureInitialState(remainingTime);

    if (this._useCapOcr == 4) {
      const cropImageData = this.getCropImage(this._ocrType);
      this._captureinProgress = false;

      // 수동 캡처이므로 validCheckDt는 false, resultCode는 빈 값으로 전달
      await this.emitCaptureEvent(cropImageData, false, null, 0);
      this._cleanupCaptureState();
      return;
    }

    while (this._captureinProgress) {
      this._updateCaptureUI();

      // 1. 타임아웃 또는 강제 촬영 조건 체크
      if (this._shouldTriggerForceCapture()) {
        await this._handleForceCapture();
        break;
      }

      // 2. 촬영 및 분석 실행 (Iteration)
      const result = await this._performCaptureIteration();

      // 3. 결과 처리
      if (result?.isSuccess) {
        await this._handleCaptureSuccess(result);
      } else {
        await this._handleCaptureFailure();
      }

      // 4. 반복 지연 처리
      await this._waitForNextIteration();
    }

    this._cleanupCaptureState();
  }

  _setupCaptureInitialState(remainingTime) {
    if (this._useCapOcr === 2) this._currentCaptueCount = 0;
    this._timeoutAt = Date.now() + remainingTime;
  }

  _updateCaptureUI() {
    // DSCAN 모드일 때 스위치 버튼 노출
    if (this._useCapOcr == 5 && Utils.OcrTypeConfig.dscan.includes(this._ocrType)) {
      const switchBtn = document.getElementById("switch_btn");
      if (switchBtn) switchBtn.style.display = "block";
    }
  }

  _shouldTriggerForceCapture() {
    // 수동 촬영 모드이거나 타임아웃이 트리거된 경우
    if ((this._initUseCapOcr == 5 && this._useCapOcr == 2) || this._initUseCapOcr == 2) {
      this._timeoutTriggered = true;
    }
    return this._timeoutTriggered && this._captureinProgress;
  }

  async _handleForceCapture() {
    this._captureinProgress = false;
    const cropImageData = this.getCropImage(this._ocrType);
    await this.applyBlinkEffect();
    await this.emitCaptureEvent(cropImageData, true, null, null, true);
  }

  async _handleCaptureSuccess(result) {
    this._captureinProgress = false;
    await this._finalizeSuccessfulCapture(result);
    this._currentCaptueCount++;
  }

  async _handleCaptureFailure() {
    if (this._useCapOcr === 2) {
      // 수동 촬영인데 탐지에 실패한 경우 처리
      this._captureinProgress = false;

      if (this._currentCaptueCount === this._options.recogTryLimit) {
        this._timeoutTriggered = true;
        this._captureinProgress = true; // 다음 루프에서 강제 촬영으로 진입
      } else {
        this._title.innerHTML = "가이드 영역에 맞춰 촬영해주세요.";
        this._isStartInProgress = false;
        this.start(); // 재시작
      }
    }
  }

  async _waitForNextIteration() {
    if (!this._captureinProgress) return;
    this._accessMode ? await Utils.delay(1000) : await this.applyMinimumDelay();
  }

  _cleanupCaptureState() {
    this._captureinProgress = false;
    this._prevOcrType = null;
  }

  _prepareMultiPageCapture() {
    // this.dScanner.clearImages();
    this._currentCaptueCount = 0;
    this._successfulCaptures = 0;
    this._captureinProgress = true;
    this._videoRef.play();
  }

  _shouldContinueMultiPageCapture() {
    return this._captureinProgress && this._successfulCaptures < this._captureIndexes.length;
  }

  async _waitIfPaused() {
    while (this._pauseCapture) await Utils.delay(50);
  }

  async _performCaptureIteration(isDocumentMode = false) {
    // 1. 초기 가드 클로즈: 탐지를 사용하지 않으면 즉시 종료
    if (!this._useDetect) {
      this._captureinProgress = false;
      return null;
    }

    // 2. 촬영 모드에 따른 초기 설정 (수동/자동)
    const isValidCheck = this._prepareCaptureMode();

    this._videoRef.play();

    // 3. 타이머 UI 업데이트
    this._updateTimerDisplay();

    // 4. 탐지 실행
    const detectionResult = await this.handleDetection(isValidCheck);
    if (!detectionResult) return null;

    // 5. 탐지 결과 성공 여부 판단 및 후처리
    const isSuccess = this._isDetectionSuccess(detectionResult.resultCode);
    if (isSuccess) {
      return await this._handleSuccessfulDetection(detectionResult, isDocumentMode);
    }

    return null;
  }

  _prepareCaptureMode() {
    // 수동 촬영(2) 모드일 때의 상태 업데이트
    if (this._useCapOcr === 2) {
      this._currentCaptueCount++;
      this._timeoutTriggered = true;
      this._captureinProgress = true;
      return true; // validCheckDt
    }
    return false;
  }

  _updateTimerDisplay() {
    // 자동 촬영 모드(1, 5)일 때만 타이머 표시
    if (this._useCapOcr !== 1 && this._useCapOcr !== 5) return;

    const isDscan = Utils.OcrTypeConfig.dscan.includes(this._ocrType);

    if (isDscan) {
      this._timerdoc.classList.add("show");
      this.startTimerDocDisplay();
    } else {
      this._timerdoc.classList.add("hide");
      this._timerEl.classList.remove("hide");
      this.startTimerDisplay();
    }
  }

  async _handleSuccessfulDetection(detectionResult, isDocumentMode) {
    const { cropImageData, resultCode, detectTime, isRightShot } = detectionResult;

    // 촬영 모드를 초기 모드로 복구
    this._useCapOcr = this._initUseCapOcr;

    // QR 타입(27)이면서 체크가 필요한 경우
    if (this._ocrType === 27 && this._qrCheck) {
      await this._handleQrResult(detectionResult, isRightShot);
      return null; // QR 처리는 별도 흐름이므로 null 반환 (혹은 필요에 따라 조정)
    }

    if (this._videoRef) {
      this._videoRef.pause();
    }

    // 일반 OCR 또는 문서 OCR 처리
    return isDocumentMode ? await this._ocrModuleManager._affineEditor._processDocumentCapture(cropImageData, resultCode) : this._processNormalCapture(cropImageData, resultCode, detectTime);
  }

  async _multiCaptureIteration() {
    try {
      // 1. 프레임 캡처 및 이미지 분할 (좌/우)
      const fullCrop = this.getCropImage(this._ocrType);
      const { left, right } = this._splitImageForQr(fullCrop.imageData);

      // 2. QR 병렬 인식 실행
      this._title.innerHTML = "QR 인식중입니다...";
      const [leftResult, rightResult] = await this._detectQrParallel(left, right);

      // 3. QR 결과 검증 및 처리
      const rightValid = Utils._isSuccessCode(rightResult);
      const leftValid = Utils._isSuccessCode(leftResult);

      // 오른쪽 QR이 없으면 즉시 종료 (비즈니스 로직 유지)
      if (!rightValid) return null;

      // 유효한 QR 결과 반영
      await this._handleQrResults(leftResult, leftValid, rightResult, rightValid);

      // 4. 모든 QR 조건 충족 시 문서 분석 진행
      if (this._isQrValid) {
        return await this._processFinalDocumentCapture(fullCrop);
      }

      return null;
    } catch (err) {
      console.error("_multiCaptureIteration 오류:", err);
      return null;
    } finally {
      Utils.hideLoading();
    }
  }

  _splitImageForQr(imageData) {
    return {
      left: Utils.getTopLeftQuarter(imageData),
      right: Utils.getTopRightQuarter(imageData),
    };
  }

  async _detectQrParallel(left, right) {
    return await Promise.all([this._ocrModuleManager.sendDetect(this._ocrType, left.imageData, false, this), this._ocrModuleManager.sendDetect(this._ocrType, right.imageData, false, this)]);
  }

  async _handleQrResults(leftRes, leftValid, rightRes, rightValid) {
    const pageIndex = this._qrPageIndex;
    if (rightValid) await this._handleQrResult(rightRes, pageIndex);
    if (leftValid) await this._handleQrResult(leftRes, pageIndex);
  }

  async _processFinalDocumentCapture(successImage) {
    this._updateUIForDocCapture();

    // QR 체크를 잠시 끄고 실제 문서 영역 탐지 실행
    this._qrCheck = false;
    const detectResult = await this._ocrModuleManager.sendDetect(
      this._ocrType,
      successImage.imageData,
      true, // 검증 모드 ON
      this,
    );
    this._qrCheck = true;

    if (!detectResult?.resultCode) {
      console.warn("문서 인식 실패 - resultCode 없음");
      return null;
    }

    return await this._ocrModuleManager._affineEditor._processDocumentCapture(successImage, detectResult.resultCode);
  }

  _updateUIForDocCapture() {
    const capBtn = document.getElementById("cap_btn");
    if (capBtn) capBtn.style.display = "none";
    Utils.logInfo("QR 체크 완료");
  }

  _isDetectionSuccess(resultCode) {
    if (this._useCapOcr === 2 && Utils.OcrTypeConfig.dscan.includes(this._ocrType)) {
      return true;
    }
    return resultCode == 0 || resultCode?.resultJSON?.resultCode == 0;
  }

  _processNormalCapture(cropImageData, resultCode, successDt) {
    if (Utils.OcrTypeConfig.dscan.includes(this._ocrType)) {
      resultCode.resultJSON.resultCode = "0000";
    } else {
      this._handleGuideBoxEffect(cropImageData);
    }

    return {
      isSuccess: true,
      cropImageData,
      resultCode,
      successDt,
    };
  }

  async _finalizeSuccessfulDocumentCapture({ cropImageData, validCheckDt, resultCode, successDt }) {
    await this.handleSuccessfulDetection(cropImageData, validCheckDt, resultCode, successDt, false);
  }

  async _finalizeSuccessfulCapture({ cropImageData, validCheckDt, resultCode, successDt }) {
    await this.handleSuccessfulDetection(cropImageData, validCheckDt, resultCode, successDt, this._timeoutTriggered);
    this._successfulDetectionCalled = true;
  }

  // 공통 guide box 효과 처리 함수
  _handleGuideBoxEffect(cropImageData) {
    let dataURL;

    if (typeof cropImageData === "string" && cropImageData.startsWith("data:image/")) {
      // 이미 base64 dataURL 형태인 경우
      dataURL = cropImageData;
    } else if (cropImageData?.base64Data) {
      // base64 문자열만 있는 경우
      dataURL = `data:image/jpeg;base64,${cropImageData.base64Data}`;
    } else if (cropImageData instanceof ImageData) {
      // [추가] Utils.handleFileToImageData에서 리턴된 ImageData 객체인 경우
      const base64 = Utils.convertImageDataToBase64(cropImageData);
      dataURL = `data:image/jpeg;base64,${base64}`;
    } else if (cropImageData?.imageData) {
      // ImageData 객체인 경우
      const base64 = Utils.convertImageDataToBase64(cropImageData.imageData);
      dataURL = `data:image/jpeg;base64,${base64}`;
    } else {
      console.warn("유효한 이미지 데이터가 없습니다.");
      return;
    }

    // 이미지 생성
    const img = document.createElement("img");
    img.src = dataURL;
    img.style.position = "absolute";
    img.style.width = "100%";
    img.style.height = "100%";
    img.onload = () => {
      URL.revokeObjectURL(dataURL); // (실제 URL 객체인 경우만 의미 있음)
      // base64는 revoke 못하니까 그냥 dataURL 변수만 정리
      dataURL = null;
    };

    this._guideArea.appendChild(img);
    this._videoWrapper.border = "5px solid #fbe9e9";
    Utils.showLoading();
  }

  updateBottomAreaClass(passportCode) {
    const bottomArea = document.querySelector(".uncovered-area-bottom");
    if (!bottomArea) return;

    // passportCode가 -2부터 -8 범위 안에 있을 때만 detect 클래스 추가
    if (passportCode <= -2 && passportCode >= -8) {
      bottomArea.classList.add("detect");
    } else {
      bottomArea.classList.remove("detect");
    }
  }

  async handleDetection(validCheckDt, uploadedImageData = null) {
    this._detectInProgress = true;
    this._previousGuideMessage = null;
    this._workerPass = false;

    let cropImageData = null;
    let imageData = null;
    let detectResults = null;
    let resultCode = null;
    let detectTime = null;
    let isRightShot = null;

    // 1. 이미지 소스 확보 (업로드 vs 실시간 캡처)
    if (uploadedImageData) {
      cropImageData = { imageData: uploadedImageData };
    } else {
      this._startTime = performance.now();
      cropImageData = this.getCropImage(this._ocrType);

      if (!cropImageData) {
        Utils.logInfo("Crop 실패 → 다음 프레임에서 다시 시도");
        setTimeout(() => this.start(), 100);
        return;
      }
    }

    // 2. 수동 촬영 모드(2) 시 강제 valid 설정
    if (this._useCapOcr == 2) {
      validCheckDt = true;
    }

    imageData = cropImageData.imageData;

    // 3. QR 검증 모드(27) 전처리: 좌/우 영역 추출
    if (this._qrCheck && this._ocrType == 27) {
      this._title.innerHTML = "QR 인식중입니다.";
      this._qrShotCount = (this._qrShotCount || 0) + 1;

      // 홀수 -> 오른쪽, 짝수 -> 왼쪽
      isRightShot = this._qrShotCount % 2 === 1;
      const qrImageData = isRightShot ? Utils.getTopRightQuarter(imageData) : Utils.getTopLeftQuarter(imageData);

      imageData = qrImageData.imageData;
      cropImageData = qrImageData;
    }

    // 4. 탐지 실행 및 타입별 특수 로직
    if (Utils.OcrTypeConfig.detect.includes(this._ocrType)) {
      // [특수타입] 지로(10) 처리
      if (this._ocrType == 10) {
        const giroResult = await this._ocrModuleManager.giroDetect(this._ocrType, imageData, validCheckDt, this);
        this.setGiroBorder(giroResult.resultCode);
      }

      // 메인 탐지 요청
      detectResults = await this._ocrModuleManager.sendDetect(this._ocrType, imageData, validCheckDt, this);
      resultCode = detectResults.resultCode;
      detectTime = detectResults.detectTime;

      // console.log("detectResults : ", detectResults);

      // [특수타입] 여권(25) 하단 영역 클래스 업데이트
      if (this._ocrType == 25) {
        this.updateBottomAreaClass(resultCode);
      }

      // 5. 가이드 UI 업데이트 (테두리 및 메시지)
      this._updateGuideUI(resultCode);

      // 6. 성공 여부 판단 및 결과 처리
      const detectSuccess = detectResults.continuousSuccess;
      const isSuccess = await Utils.isDetectSuccessful(detectSuccess);

      if (isSuccess) {
        this._finalizeSuccessfulDetection();

        // [특수타입] 특정 타입 워커 패스 설정
        if (Utils.OcrTypeConfig.wokerpass.includes(this._ocrType)) {
          this._workerPass = true;
        }

        // 즉시 캡처 이벤트 발행 모드(3)인 경우
        if (this._useCapOcr == 3) {
          return await this.emitCaptureEvent(cropImageData, validCheckDt, resultCode, detectTime);
        }

        return { cropImageData, resultCode, detectTime, isRightShot };
      }

      // 7. 문서(다중페이지) 인식 중 진행 상태 피드백 (Overlay 등)
      if (Utils.OcrTypeConfig.document.includes(this._ocrType) && resultCode?.resultJSON) {
        this._handleDocumentProgress(resultCode, cropImageData);
        return { cropImageData, resultCode, detectTime };
      }
    }

    this._detectInProgress = false;
    return null;
  }

  updateScanGuideState(isDetected) {
    const guideBox = document.querySelector(".guideBox.scan");

    if (guideBox) {
      if (isDetected) {
        // 감지되었을 때 클래스 추가
        guideBox.classList.add("reg");
        Utils.logInfo("Seal detected! Adding 'detect' class.");
      } else {
        // 감지되지 않았을 때 클래스 제거
        guideBox.classList.remove("reg");
      }
    }
  }

  _updateGuideUI(resultCode) {
    const isDetectableOcr = !Utils.OcrTypeConfig.dscan.includes(this._ocrType) && this._innerGuideBox;
    const detectMessage = Utils.getDetectMessage(this._ocrType, resultCode);

    // [수정] resultCode가 2000일 때만 reg 클래스 추가, 아니면 제거
    if (Utils.OcrTypeConfig.document.includes(this._ocrType)) {
      const detectCode = resultCode.resultJSON.resultCode;
      const isSuccess = detectCode != 2000; // 2000일 때만 true
      this.updateScanGuideState(isSuccess);
    }

    // 기존 가이드 박스 'detect' 클래스 제어 (필요 없으면 제거 가능)
    if (isDetectableOcr) {
      const isDetected = !(resultCode === -1 || resultCode === -3);
      this._innerGuideBox.classList[isDetected ? "add" : "remove"]("detect");
    }

    // 안내 메시지 업데이트
    if (detectMessage !== this._previousGuideMessage) {
      this._title.innerHTML = detectMessage;
      this._previousGuideMessage = detectMessage;
    }
  }

  _finalizeSuccessfulDetection() {
    this._orientationChangeButton?.remove();
    this._orientationChangeButton = null;
    this._detectInProgress = false;
  }

  _handleDocumentProgress(resultCode, cropImageData) {
    if (resultCode.resultJSON.resultCode !== "2000") {
      // const points = resultCode.resultJSON.points;
      // this._affineEditor.drawOverlayLines(points, cropImageData);
    } else {
      const modalContent = this._innerGuideBox?.querySelector(".scanContent");
      if (modalContent) {
        modalContent.innerHTML = "";
      }
    }
  }

  applyMinimumDelay(minDelay = 200) {
    const captureDelay = this._processTime < minDelay ? minDelay - this._processTime : this._options.rtcRetryDelay;
    return new Promise((resolve) => setTimeout(resolve, captureDelay));
  }

  getDetectResultCode(detectResults) {
    return Utils.OcrTypeConfig.dscan.includes(this._ocrType) ? detectResults.resultCode.resultJSON.resultCode : detectResults.resultCode;
  }

  async videoPause() {
    this._videoRef.pause();
  }

  async applyBlinkEffect() {
    // this._videoRef.pause();
    this._guideArea.style.backgroundColor = "rgba(0, 0, 0, 0.7)";
    const delay = this._accessMode ? 2000 : 100;
    await Utils.sleep(delay);
  }

  async handleSuccessfulDetection(cropImageData, validCheckDt, resultCode, detectTime, timeoutTriggered) {
    try {
      let successImgData = null;
      this._detectingSuccess = true;
      successImgData = cropImageData;

      if (Utils.OcrTypeConfig.dscan.includes(this._ocrType)) {
        const formatCode = resultCode.resultJSON.resultCode;
        if (this._documentCaptureDone) await this.emitCaptureEvent(successImgData, validCheckDt, formatCode, detectTime);
      } else {
        await this.emitCaptureEvent(successImgData, validCheckDt, resultCode, detectTime);

        if (this._innerGuideBox) {
          this._innerGuideBox.style.display = "none";
        }
      }

      const detectMessage = Utils.getOcrMessage(this._ocrType, resultCode);
      this._title.innerHTML = detectMessage;

      this._detectRqCount++;
      this._detectInProgress = false;
      this._captureInProgress = false;
      this._prevOcrType = null;
      const switchBtn = document.getElementById("switch_btn");
      if (switchBtn) {
        switchBtn.style.display = "none";
      }
      // this._currentCaptueCount = 0;
    } catch (error) {
      Utils.logError("Error:", error.message, error);
      Utils.showAlert("error: " + error.message);
    }
  }

  async emitCaptureEvent(cropImageData, validCheckDt, resultCode, detectTime, timeoutTriggered) {
    await this.forceStopTimerDisplay();
    const eventDetail = {
      cropData: cropImageData?.imageData || null,
      originData: cropImageData?.originData || null,
      validCheckDt: validCheckDt,
      rtcToken: this._key,
      resultCode: resultCode,
      detectTime: detectTime,
      timeout: this._timeoutTriggered,
      convertBlob: this._convertBlob,
      base64: this._base64Docs,
      totalPages: this.Pages,
      isManualCapture: this._useCapOcr === 4,
      captureMode: this._useCapOcr
      // qrResults: this._qrResults,
    };
    if (this._ocrType === 27) {
      const imageDataUrls = this._ocrModuleManager.dScanner.getAllImages();
      this._documentList = imageDataUrls;
      eventDetail.qrcode = this._qrResults;
      eventDetail.documentList = this._documentList || []; // this._documentList에 문서 배열이 들어 있다고 가정
    }
    if (this._workerPass) {
      this.dispatchEvent(new CustomEvent("workerPass", { detail: eventDetail }));
    } else if (this._useCapOcr === 4) {
      this.dispatchEvent(new CustomEvent("imagecapturedonly", { detail: eventDetail }));
    } else {
      this.dispatchEvent(new CustomEvent("imagecaptured", { detail: eventDetail }));
    }
    this._imageCaptured = true;
    this._qrResults = [];
    this._qrFind = [];
    this._currentCaptueCount = 0;
  }

  setGiroBorder(giroType) {
    const borderColorMap = {
      0: "yellow", // 기본
      1: "red", // ocr
      2: "blue", // 표준 ocr
      3: "green", // MICR ocr
    };

    if (!borderColorMap.hasOwnProperty(giroType)) return;

    if (giroType == 0) {
      this._guideArea.style.border = `3px solid ${borderColorMap[giroType]}`;
    } else if (giroType !== 0) {
      this.currentGiroType = giroType;
      this._guideArea.style.border = `3px solid ${borderColorMap[giroType]}`;
    } else if (this.currentGiroType !== null) {
      this._guideArea.style.border = `3px solid ${borderColorMap[this.currentGiroType]}`;
    }
  }
}
