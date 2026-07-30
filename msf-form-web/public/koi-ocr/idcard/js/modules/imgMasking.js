export default class ImgMasking {
  constructor(imageElement) {
    this.image = imageElement;
    this.container = imageElement.parentElement;
    this.overlay = null;
    this.ctx = null;

    this.isDrawing = false;
    this.startX = 0;
    this.startY = 0;
    this.currentX = 0;
    this.currentY = 0;

    this.savedMask = null; // 이전까지의 마스크 이미지 저장용

    this._initOverlay();
    this._bindEvents();
  }

  _initOverlay() {
    const canvas = document.createElement("canvas");
    canvas.className = "mask-overlay";

    // 1. 원본 해상도는 유지 (드로잉 퀄리티용)
    this.imgWidth = this.image.naturalWidth;
    this.imgHeight = this.image.naturalHeight;
    canvas.width = this.imgWidth;
    canvas.height = this.imgHeight;

    // 2. [중요] 화면에 보이는 이미지의 실제 크기를 측정
    const rect = this.image.getBoundingClientRect();

    Object.assign(canvas.style, {
      position: "absolute",
      // 3. 이미지의 현재 위치와 크기를 그대로 복사
      top: `${this.image.offsetTop}px`,
      left: `${this.image.offsetLeft}px`,
      width: `${rect.width}px` /* 100% 대신 실제 이미지 너비 */,
      height: `${rect.height}px` /* 100% 대신 실제 이미지 높이 */,
      zIndex: "1000",
      touchAction: "none",
      pointerEvents: "auto",
      background: "transparent",
      borderRadius: "12px" /* 이미지와 동일한 곡률 적용 */,
    });

    this.container.appendChild(canvas);
    this.overlay = canvas;
    this.ctx = canvas.getContext("2d");
  }

  _bindEvents() {
    // 마우스
    this.overlay.addEventListener("mousedown", (e) => this._startDraw(e));
    this.overlay.addEventListener("mousemove", (e) => this._draw(e));
    this.overlay.addEventListener("mouseup", () => this._endDraw());
    this.overlay.addEventListener("mouseleave", () => this._endDraw());

    // 터치
    this.overlay.addEventListener("touchstart", (e) => this._startDraw(e.touches[0]));
    this.overlay.addEventListener("touchmove", (e) => {
      e.preventDefault();
      this._draw(e.touches[0]);
    });
    this.overlay.addEventListener("touchend", () => this._endDraw());
  }

  _startDraw(e) {
    const rect = this.overlay.getBoundingClientRect();
    this.isDrawing = true;
    const scaleX = this.imgWidth / rect.width;
    const scaleY = this.imgHeight / rect.height;

    this.startX = (e.clientX - rect.left) * scaleX;
    this.startY = (e.clientY - rect.top) * scaleY;
    this.currentX = this.startX;
    this.currentY = this.startY;
  }

  _draw(e) {
    if (!this.isDrawing) return;

    const rect = this.overlay.getBoundingClientRect();
    const scaleX = this.imgWidth / rect.width;
    const scaleY = this.imgHeight / rect.height;

    this.currentX = (e.clientX - rect.left) * scaleX;
    this.currentY = (e.clientY - rect.top) * scaleY;

    this._clearPreview();
    this._drawPreviewRect();
  }

  _endDraw() {
    if (!this.isDrawing) return;
    this.isDrawing = false;

    const x = Math.min(this.startX, this.currentX);
    const y = Math.min(this.startY, this.currentY);
    const w = Math.abs(this.currentX - this.startX);
    const h = Math.abs(this.currentY - this.startY);

    // 누적 마스크
    this.ctx.fillStyle = "rgba(0, 0, 0)";
    this.ctx.fillRect(x, y, w, h);

    // base64 저장
    this.savedMask = this.overlay.toDataURL("image/png");

    // ⬇ Image 객체 캐싱
    this.savedMaskImage = new Image();
    this.savedMaskImage.src = this.savedMask;

    // undo 스택 push 용
    const maskBase64 = this.savedMask;
    if (this.onMaskChange) this.onMaskChange(maskBase64);
  }

  // 드래그 중: 미리보기용 사각형 (투명 테두리)
  _drawPreviewRect() {
    const x = Math.min(this.startX, this.currentX);
    const y = Math.min(this.startY, this.currentY);
    const w = Math.abs(this.currentX - this.startX);
    const h = Math.abs(this.currentY - this.startY);

    this.ctx.strokeStyle = "rgba(0, 0, 0, 0.6)";
    this.ctx.lineWidth = 4;
    this.ctx.strokeRect(x, y, w, h);
  }

  // 사각형 미리보기만 지우는 함수 (이전 마스크 유지)
  _clearPreview() {
    // 전체 지우기
    this.ctx.clearRect(0, 0, this.overlay.width, this.overlay.height);

    // ↓ savedMaskImage 라는 Image 객체를 캐싱해서 사용
    if (this.savedMaskImage) {
      this.ctx.drawImage(this.savedMaskImage, 0, 0);
    }
  }

  getMaskImage() {
    return this.overlay.toDataURL("image/png");
  }

  getMaskedFinalImage() {
    const finalCanvas = document.createElement("canvas");
    finalCanvas.width = this.imgWidth; // naturalWidth
    finalCanvas.height = this.imgHeight; // naturalHeight
    const finalCtx = finalCanvas.getContext("2d");

    // 원본 이미지(실제 해상도 기준)
    finalCtx.drawImage(this.image, 0, 0, this.imgWidth, this.imgHeight);

    // 누적 마스크도 원본 해상도 기준
    finalCtx.drawImage(this.overlay, 0, 0, this.imgWidth, this.imgHeight);

    return finalCanvas.toDataURL("image/png");
  }

  loadMaskedImage(base64) {
    const img = new Image();
    img.onload = () => {
      this.ctx.clearRect(0, 0, this.overlay.width, this.overlay.height);
      this.ctx.drawImage(img, 0, 0, this.overlay.width, this.overlay.height);

      this.savedMask = base64;
      this.savedMaskImage = img; // 캐싱
    };
    img.src = base64;
  }

  clearAllMasks() {
    this.ctx.clearRect(0, 0, this.overlay.width, this.overlay.height);
    this.savedMask = null;
  }
}
