// koi-module.js
import { loadKoiOcr } from "../lib/koiOcrLoader.js";

class KoiModule extends HTMLElement {
  constructor() {
    super();

    this.koiOcr = null;
    this.koiOcrInstance = null;
    this.config = null;

    // ready Promise는 여기서 '만들기만'
    this._readyResolve = null;
    this.readyPromise = new Promise((resolve) => {
      this._readyResolve = resolve;
    });
  }

  async connectedCallback() {
    // 여기서만 초기화
    this.render();

    const KoiOcr = await loadKoiOcr();
    this.koiOcr = new KoiOcr();
    this.koiOcrInstance = this.koiOcr;

    await this.koiOcr.ready();
    this.config = this.koiOcr.config;
    this.registerEvents();

    // 준비 완료
    this._readyResolve();
    window.dispatchEvent(new CustomEvent("koi-ready"));
  }

  async ready() {
    await this.readyPromise;
  }

  render() {
    if (!this.querySelector("#webcamera_container")) {
      const container = document.createElement("div");
      container.id = "webcamera_container";
      container.className = "camera-section";

      // 1. 뒤로가기 버튼 생성
      const backButton = document.createElement("button");
      backButton.className = "back-btn";

      // 왼쪽 화살표 아이콘 (SVG 또는 유니코드 사용 가능)
      backButton.innerHTML = `
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M15 18L9 12L15 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
      <span class="back-btn-title"></span>
    `;

      backButton.addEventListener("click", () => {
        window.postMessage("close_ocr_iframe", "*");
      });

      container.appendChild(backButton);

      // 4. 최종적으로 컴포넌트에 컨테이너 추가
      this.appendChild(container);
    }
  }

  async startKoiOcr(options) {
    const { ocrType } = options;
    await this.ready();
    await this.koiOcr.ready();

    if (!this.koiOcr.useWebCamera) {
      throw new Error("WebCamera is disabled.");
    }

    // init 실패도 전파
    const initResult = await this.koiOcr.init(options);
    if (initResult?.success === false) {
      throw new Error(initResult.error || "KOI OCR init failed");
    }

    // 여기서 실패하면 반드시 throw
    const changeResult = await this.koiOcr.changeOcrType(ocrType);
    if (changeResult?.success === false) {
      throw new Error(changeResult.error || "OCR type change failed");
    }

    return { success: true };
  }

  async capture() {
    await this.koiOcr.stopCamera();
    await this.koiOcr.runCamera();
  }

  async retry(ocrType) {
    await this.startOcr(ocrType);
  }

  async stop() {
    await this.koiOcr.stopCamera();
  }

  async unload() {
    await this.koiOcr.unloadCamera();
  }

  registerEvents() {
    const events = this.config.KOI_OCR_EVENT;
    this.koiOcr.addEventListener(events.READY, (e) => this.dispatchEvent(new CustomEvent("koi-ready", { detail: e.detail })));
    this.koiOcr.addEventListener(events.PROGRESS, (e) => this.dispatchEvent(new CustomEvent("koi-progress", { detail: e.detail })));
    this.koiOcr.addEventListener(events.RESULT, (e) => {
      this.dispatchEvent(new CustomEvent("koi-result", { detail: e.detail }));
    });
    this.koiOcr.addEventListener(events.CAMERA_STARTED, (e) => this.dispatchEvent(new CustomEvent("koi-camera-started", { detail: e.detail })));
    this.koiOcr.addEventListener(events.CAPTURE, (e) => this.dispatchEvent(new CustomEvent("koi-capture", { detail: e.detail })));
    this.koiOcr.addEventListener(events.TIMEOUT, (e) => this.dispatchEvent(new CustomEvent("koi-timeout", e.detail)));
  }
}

customElements.define("koi-module", KoiModule);
