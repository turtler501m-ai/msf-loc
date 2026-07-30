// AffineEditor.js

import * as Utils from "../../lib/utils.js";
import { MaskEditor } from "../masking/maskEditor.js";

export class AffineEditor {
  constructor(context) {
    this._context = context;
    this._points = [];
    this._innerCircles = [];
    this._lines = [];
    this._maskingInstances = [];
    this._masking = null;
  }

  async processDocumentAffine(imageData, points) {
    return new Promise((resolve) => {
      // 1. 초기 데이터 설정 및 유효성 검사
      this._points = points;
      this._context._isEditingScreen = true;
      if (this._points.some((p) => p.x === -1 || p.y === -1)) return resolve(null);

      // 2. 기본 레이아웃 구성 (컨테이너 및 모달 생성)
      const { affineContainer, modalContent } = this._setupAffineLayout();

      // 3. 이미지 및 SVG 레이어 생성
      const guideAreaRect = document.getElementById("kwcGuideArea").getBoundingClientRect();
      this._setupCanvasAndImage(imageData, modalContent);
      const svg = this._createSvgLayer(modalContent);

      // 4. 포인트 및 이벤트 바인딩
      const { affinePoints, innerCircles } = this._createAffinePoints(imageData, guideAreaRect, modalContent, svg);

      // 5. 연결선 및 오버레이 초기화
      const lines = this._drawConnectionLines(innerCircles, svg);
      this.updateOverlayPath(innerCircles, svg, guideAreaRect);

      // 6. 하단 버튼 영역 생성 및 이벤트 설정
      this._setupAffineButtons(resolve, {
        affinePoints,
        guideAreaRect,
        imageData,
        innerCircles, // 리소스 정리 시 필요할 수 있음
      });

      // 7. 최종 레이아웃 업데이트
      this.updateAffineLayout(this._context._getContainerSize(), this._context._calcVideoSize());
    });
  }

  /** 헬퍼 함수: 레이아웃 초기 설정 */
  _setupAffineLayout() {
    const kwcGuideArea = document.getElementById("kwcGuideArea");
    kwcGuideArea.style.border = "0px";
    kwcGuideArea.style.zIndex = "10002";

    const guideAreaRect = kwcGuideArea.getBoundingClientRect();
    const containerRect = this._context._container.getBoundingClientRect();

    const affineContainer = document.createElement("div");
    affineContainer.className = "affine-container";
    Object.assign(affineContainer.style, {
      position: "absolute",
      top: `${guideAreaRect.top - containerRect.top}px`,
      left: `${guideAreaRect.left - containerRect.left}px`,
      width: `${guideAreaRect.width}px`,
      height: `${guideAreaRect.height}px`,
      zIndex: "10090",
      overflow: "visible",
    });

    const modalContent = document.createElement("div");
    modalContent.className = "affineContent";
    Object.assign(modalContent.style, { width: "100%", height: "100%", position: "relative" });

    const modal = document.createElement("div");
    modal.className = "affineDiv";
    Object.assign(modal.style, { width: "100%", height: "100%" });

    modal.appendChild(modalContent);
    affineContainer.appendChild(modal);
    this._context._container.appendChild(affineContainer);

    return { affineContainer, modalContent };
  }

  /** 헬퍼 함수: 캔버스 이미지 생성 */
  _setupCanvasAndImage(imageData, parent) {
    const canvas = document.createElement("canvas");
    canvas.width = imageData.width;
    canvas.height = imageData.height;
    canvas.getContext("2d").putImageData(imageData, 0, 0);

    const guideImage = document.createElement("img");
    guideImage.id = "affine-guideImage";
    guideImage.src = canvas.toDataURL("image/jpeg");
    if (guideImage) {
      // 마우스 오른쪽 클릭(롱프레스 포함) 방지
      guideImage.oncontextmenu = (e) => e.preventDefault();
      // 드래그 시작 방지
      guideImage.ondragstart = (e) => e.preventDefault();
    }
    parent.appendChild(guideImage);
  }

  /** 헬퍼 함수: SVG 레이어 생성 */
  _createSvgLayer(parent) {
    const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svg.setAttribute("width", parent.clientWidth);
    svg.setAttribute("height", parent.clientHeight);
    svg.setAttribute("pointer-events", "none");
    svg.classList.add("affine-overlay-svg");
    parent.appendChild(svg);
    return svg;
  }

  /** 헬퍼 함수: 포인트 생성 및 이벤트 바인딩 */
  _createAffinePoints(imageData, guideAreaRect, parent, svg) {
    const radius = 20;
    const affinePoints = [];
    const innerCircles = [];

    this._points.forEach((point, i) => {
      const relativeX = Utils.canvasX(point.x, guideAreaRect, imageData, radius);
      const relativeY = Utils.canvasY(point.y, guideAreaRect, imageData, radius);
      affinePoints.push({ x: relativeX, y: relativeY });

      const pointDiv = document.createElement("div");
      pointDiv.className = `point-div point-div${i + 1}`;
      Object.assign(pointDiv.style, { left: `${relativeX}px`, top: `${relativeY}px` });

      const innerCircle = document.createElement("div");
      innerCircle.className = "inner-circle";
      pointDiv.appendChild(innerCircle);
      parent.appendChild(pointDiv);

      innerCircles.push({
        x: relativeX + pointDiv.offsetWidth / 2,
        y: relativeY + pointDiv.offsetHeight / 2,
        div: pointDiv,
      });

      this._bindPointEvents(pointDiv, i, affinePoints, innerCircles, svg, guideAreaRect);
    });

    return { affinePoints, innerCircles };
  }

  /** 헬퍼 함수: 포인트 터치 이벤트 바인딩 */
  _bindPointEvents(el, index, affinePoints, innerCircles, svg, rect) {
    el.addEventListener(
      "touchstart",
      (e) => {
        if (e.touches.length > 1) return;
        if (e.touches[0].clientX < 25) e.preventDefault();
        Utils.handleTouchStart(index, el, affinePoints, this._points[index].x, this._points[index].y)(e);
        el._isDragging = true;
      },
      { passive: false },
    );

    el.addEventListener(
      "touchmove",
      (e) => {
        if (e.touches.length > 1) return e.preventDefault();
        if (el._isDragging) e.preventDefault();
        Utils.handleTouchMove(el, affinePoints, innerCircles, this._lines)(e);
        this.updateOverlayPath(innerCircles, svg, rect);
      },
      { passive: false },
    );

    el.addEventListener(
      "touchend",
      () => {
        el._isDragging = false;
        Utils.handleTouchEnd();
      },
      { passive: true },
    );
  }

  /** 헬퍼 함수: 연결선 그리기 */
  _drawConnectionLines(innerCircles, svg) {
    this._lines = [];
    innerCircles.forEach((start, i) => {
      const end = innerCircles[(i + 1) % innerCircles.length];
      const line = Utils.createLine(start.x, start.y, end.x, end.y);
      this._lines.push(line);
      svg.appendChild(line);
    });
    return this._lines;
  }

  /** 헬퍼 함수: 하단 버튼 설정 */
  _setupAffineButtons(resolve, data) {
    const switchBtn = document.getElementById("switch_btn");
    if (switchBtn) switchBtn.style.display = "none";

    const bottomArea = document.querySelector(".background.bottom");
    const buttonArea = document.createElement("div");
    buttonArea.id = "affine-button-area";
    bottomArea.appendChild(buttonArea);

    const backBtn = this._createBtn("재촬영", "affineBackBtn", buttonArea);
    const saveBtn = this._createBtn("문서저장", "affineSaveBtn", buttonArea);

    saveBtn.onclick = () => {
      this.clearAllAffineElements();

      // 데이터가 유효한지 확인 (혹시 모를 undefined 방지)
      const validPoints = data.affinePoints.map((p) => ({
        x: p.x || 0,
        y: p.y || 0,
      }));

      const sortedPoints = this.getOriginalPoints(validPoints, data.guideAreaRect, data.imageData, 20);
      resolve({ sortedPoints, imageData: data.imageData, isSave: true });
    };

    backBtn.onclick = () => {
      this.clearAllAffineElements();

      // 수정: 마지막 촬영한 이미지를 지우기 위해 마지막 인덱스 전달
      // 보통 마지막 찍은 이미지는 currentCaptueCount - 1 위치임
      const lastIdx = this._context._currentCaptueCount - 1;
      this.unifiedDeleteImage(lastIdx >= 0 ? lastIdx : 0);

      resolve(null);
    };
  }

  _createBtn(text, id, parent) {
    const btn = document.createElement("button");
    btn.textContent = text;
    btn.id = id;
    btn.classList.add("affine-button");
    parent.appendChild(btn);
    return btn;
  }

  updateAffineLayout(containerSize, videoSize) {
    const area = document.getElementById("affine-button-area");
    const buttons = document.querySelectorAll("#affine-button-area .affine-button");

    if (!area) return;

    const hspace = containerSize.width - videoSize.width;
    const vspace = containerSize.height - videoSize.height;

    // landscape 모드로 간주
    if (hspace > vspace) {
      area.classList.add("landscape");
      buttons.forEach((btn) => btn.classList.add("landscape"));
    } else {
      area.classList.remove("landscape");
      buttons.forEach((btn) => btn.classList.remove("landscape"));
    }
  }

  updatemodalAffineLayout(containerSize, videoSize) {
    const modalarea = document.getElementById("modal-button-area");
    const modalbuttons = document.querySelectorAll("#modal-button-area .affine-button");

    if (!modalarea) return;

    const hspace = containerSize.width - videoSize.width;
    const vspace = containerSize.height - videoSize.height;

    // landscape 모드로 간주
    if (hspace > vspace) {
      modalarea.classList.add("landscape");
      modalbuttons.forEach((btn) => btn.classList.add("landscape"));
    } else {
      modalarea.classList.remove("landscape");
      modalbuttons.forEach((btn) => btn.classList.remove("landscape"));
    }
  }

  getOriginalPoints(affinePoints, guideAreaRect, imageData, radius) {
    const originalPoints = [];

    for (let i = 0; i < affinePoints.length; i++) {
      const point = affinePoints[i];
      const originalX = Utils.reverseCanvasX(point.x, guideAreaRect, imageData, radius);
      const originalY = Utils.reverseCanvasY(point.y, guideAreaRect, imageData, radius);
      originalPoints.push({ x: originalX, y: originalY });
    }
    const sortedPoints = Utils.sortPoints(originalPoints);

    return sortedPoints;
  }

  updateOverlayPath(innerCircles, svg, guideAreaRect) {
    const existingPath = svg.querySelector("path.overlay-shade");
    if (existingPath) existingPath.remove();

    const existingPolygon = svg.querySelector("polygon.inner-shade");
    if (existingPolygon) existingPolygon.remove();

    // 1. 포인트 문자열 생성 (Polygon용)
    const pointStr = innerCircles.map((p) => `${p.x},${p.y}`).join(" ");

    // 2. 구멍을 뚫기 위한 하위 경로 문자열 생성 (M x1,y1 L x2,y2 L x3,y3 L x4,y4 Z)
    const holePath =
      `M${innerCircles[0].x},${innerCircles[0].y} ` +
      innerCircles
        .slice(1)
        .map((p) => `L${p.x},${p.y}`)
        .join(" ") +
      " Z";

    // 3. 외부 어두운 그림자 (마스크 효과)
    const path = document.createElementNS("http://www.w3.org/2000/svg", "path");
    path.setAttribute(
      "d",
      `
      M0,0 
      H${guideAreaRect.width} 
      V${guideAreaRect.height} 
      H0 
      Z 
      ${holePath} 
    `,
    );

    path.setAttribute("fill", "rgba(0, 0, 0, 0.5)");
    path.setAttribute("fill-rule", "evenodd");
    path.setAttribute("pointer-events", "none");
    path.classList.add("overlay-shade");
    svg.appendChild(path);

    // 4. 내부 폴리곤 (선택 영역 강조)
    const polygon = document.createElementNS("http://www.w3.org/2000/svg", "polygon");
    polygon.setAttribute("points", pointStr);
    polygon.setAttribute("fill", "#4DB6AC1A");
    polygon.setAttribute("stroke", "none");
    polygon.classList.add("inner-shade");
    svg.appendChild(polygon);
  }
  async drawOverlayLines(points, imageData) {
    if (!this._context._guideArea || !this._context._innerGuideBox) return;

    let modalContent = this._context._innerGuideBox.querySelector(".scanContent");

    if (!modalContent) {
      const modal = document.createElement("div");
      modal.classList.add("scanModal");
      modal.style.cssText = `
        display: flex; justify-content: center; align-items: center;
        width: 100%; height: 100%; position: relative;
      `;

      modalContent = document.createElement("div");
      modalContent.classList.add("scanContent");
      modalContent.style.cssText = `
        width: 100%; height: 100%; position: relative;
      `;

      modal.appendChild(modalContent);
      this._context._innerGuideBox.appendChild(modal);
    }

    // 기존 SVG 제거
    const existingSvg = modalContent.querySelector("#lineOverlaySvg");
    if (existingSvg) {
      modalContent.removeChild(existingSvg);
    }

    const guideAreaRect = this._context._guideArea.getBoundingClientRect();
    const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svg.setAttribute("id", "lineOverlaySvg");
    svg.setAttribute("width", modalContent.clientWidth);
    svg.setAttribute("height", modalContent.clientHeight);
    svg.style.cssText = `
      position: absolute; top: 0; left: 0; z-index: 10020;
      width: 100%; height: 100%;
    `;

    const actualImageData = imageData.imageData;

    const transformedPoints = points.map((pt, i) => {
      const x = Utils.canvasX(pt.x, guideAreaRect, actualImageData);
      const y = Utils.canvasY(pt.y, guideAreaRect, actualImageData);

      if (isNaN(x) || isNaN(y)) {
        console.error(`❌ NaN 발생 at point ${i}:`, pt);
      }
      return { x, y };
    });

    const polygon = document.createElementNS("http://www.w3.org/2000/svg", "polygon");
    const pointStr = transformedPoints.map((p) => `${p.x},${p.y}`).join(" ");
    polygon.setAttribute("points", pointStr);
    polygon.setAttribute("fill", "rgba(0, 255, 0, 0.4)");
    polygon.setAttribute("stroke", "lime");
    polygon.setAttribute("stroke-width", "2");
    svg.appendChild(polygon);

    modalContent.appendChild(svg);
  }

  async saveCroppedImage(result) {
    if (result) {
      const base64Data = result.affineResult.resultJSON.base64image;
      const dataUrl = `data:image/png;base64,${base64Data}`;
      this._context._ocrModuleManager.dScanner.addImage(dataUrl, this._context.currentIndex);
      this.updatePreviewModal();
    }
  }

  updatePreviewModal = () => {
    // Utils.logInfo(this._context._ocrModuleManager.dScanner.images[0]);

    const validImages = this._context._ocrModuleManager.dScanner.images.map((img, i) => (img ? { ...img, index: i } : null)).filter(Boolean);
    const count = validImages.length;
    const badge = this._context._previewModal.querySelector("#imageCountBadge");
    const img = this._context._previewModal.querySelector("#previewImage");

    if (count === 0) {
      this._context._previewModal.classList.remove("visible");
      badge.style.display = "none";
      if (img) img.src = ""; // 이전 이미지 제거
      return;
    }

    // 가장 최근 이미지 보여주기
    const latest = [...this._context._ocrModuleManager.dScanner.images].reverse().find(Boolean);
    if (img && latest) img.src = latest.dataUrl;

    // 뱃지 업데이트
    badge.textContent = count;
    badge.style.display = "block";

    // 모달 표시 (opacity로 제어)
    this._context._previewModal.classList.add("visible");
  };

  showAllImagesModal = () => {
    if (document.getElementById("fullPreviewModal")) return;

    this._context._pauseCapture = true;
    const modal = document.createElement("div");
    modal.id = "fullPreviewModal";

    // 1. 기본 구성 요소 생성
    const closeBtn = this._createCloseButton(modal);
    const imageList = this._createImageList();
    const buttonArea = this._createModalButtonArea(); // 하단 버튼 영역 (확인/취소 등)

    // 2. 좌우 화살표 버튼 생성
    const prevBtn = document.createElement("button");
    prevBtn.className = "nav-btn prev";
    prevBtn.innerHTML = "＜";

    const nextBtn = document.createElement("button");
    nextBtn.className = "nav-btn next";
    nextBtn.innerHTML = "＞";

    // 3. 화살표 활성/비활성 상태 업데이트 로직
    const updateArrows = () => {
      if (!imageList) return;
      const { scrollLeft, clientWidth, scrollWidth } = imageList;

      // 첫 페이지면 왼쪽 비활성화, 마지막 페이지면 오른쪽 비활성화
      prevBtn.disabled = scrollLeft <= 10;
      nextBtn.disabled = scrollLeft + clientWidth >= scrollWidth - 10;
    };

    prevBtn.onclick = () => {
      imageList.scrollBy({ left: -imageList.clientWidth, behavior: "smooth" });
    };

    nextBtn.onclick = () => {
      imageList.scrollBy({ left: imageList.clientWidth, behavior: "smooth" });
    };

    // 스크롤 이벤트에 화살표 상태 업데이트 연결
    imageList.addEventListener("scroll", updateArrows);

    // 4. 요소 배치
    modal.appendChild(closeBtn);
    modal.appendChild(prevBtn);
    modal.appendChild(nextBtn);
    modal.appendChild(imageList);

    // 5. 하단 버튼 영역 처리 (기존 영역 제거 후 다시 추가)
    const existingButtonArea = this._context._letterboxBottom.querySelector("#modal-button-area");
    if (existingButtonArea) existingButtonArea.remove();
    this._context._letterboxBottom.appendChild(buttonArea);

    // 6. 모달을 컨테이너에 추가
    this._context._container.appendChild(modal);

    // 7. 초기 상태 업데이트 (렌더링 후 실행되도록 딜레이)
    setTimeout(updateArrows, 150);

    // 8. 비디오 숨김 처리
    const videoWrapper = document.querySelector(".video-wrapper-scan");
    if (videoWrapper) videoWrapper.style.display = "none";

    this.updatemodalAffineLayout(this._context._getContainerSize(), this._context._calcVideoSize());
  };

  _createCloseButton(modal) {
    const btn = document.createElement("button");
    btn.className = "close-btn";
    btn.onclick = () => {
      modal.remove();
      this._context._pauseCapture = false;
      const videoWrapper = document.querySelector(".video-wrapper-scan");
      videoWrapper.style.display = "block";
    };
    return btn;
  }

  // 이미지 리스트 영역 생성
  _createImageList() {
    const imageList = document.createElement("div");
    imageList.className = "image-list";

    const images = this._context._ocrModuleManager.dScanner.images;
    const validImages = images.filter((img) => img !== null);

    if (validImages.length === 0) {
      imageList.appendChild(this._createPlaceholderItem());
    } else {
      validImages.forEach((imgObj, index) => {
        // this._title.innerHTML = "촬영한 문서를 확인해주세요.";
        // this._bottomtitle.innerHTML = "촬영을 끝내시려면 [촬영 종료]를, </br>계속 촬영하시려면 [계속 촬영]을 눌러주세요.";

        imageList.appendChild(this._createImageItem(imgObj, index, validImages.length));
      });
    }

    if (validImages.length > 1) {
      imageList.style.justifyContent = "flex-start";
    }

    return imageList;
  }

  unifiedDeleteImage(index) {
    const ctx = this._context || this;
    index = Number(index);

    Utils.logInfo(`[Delete] Index: ${index}, OCR Type: ${ctx._ocrType}`);

    // 1. 공통: 전체 촬영 카운트 감소 (이게 없어서 초과 오류가 났던 것임)
    if (ctx._currentCaptueCount > 0) {
      ctx._currentCaptueCount--;
    }

    // 2. 공통: dScanner 내부 리스트 삭제 (메모리 내 이미지 데이터 정리)
    if (ctx._ocrModuleManager && ctx._ocrModuleManager.dScanner) {
      ctx._ocrModuleManager.dScanner.deleteImage(index);
    }

    // 3. QR 전용(27): QR 결과 및 인덱스 관리
    if (ctx._ocrType == 27) {
      if (Array.isArray(ctx._qrResults) && ctx._qrResults.length > index) {
        ctx._qrResults.splice(index, 1);
      }
      if (Array.isArray(ctx._qrFind) && ctx._qrFind.length > index) {
        ctx._qrFind.splice(index, 1);
      }
      // 다음 촬영 위치 갱신
      ctx._qrPageIndex = ctx._qrResults.length;
      ctx._qrCheck = true; // 재촬영 플래그
    }

    Utils.logInfo(`[Delete After] Count: ${ctx._currentCaptueCount}, QR List: ${ctx._qrResults?.length || 0}`);
  }

  deleteImageWithQr(index) {
    const ctx = this._context || this;

    index = Number(index);
    if (!Number.isInteger(index) || index < 0) return;

    let removedResults = null;
    let removedFind = null;

    Utils.logInfo("before delete:", JSON.parse(JSON.stringify(ctx._qrResults)));

    // 배열 인덱스 기준으로 제거 (page값은 그대로 둠)
    if (Array.isArray(ctx._qrResults) && ctx._qrResults.length > index) {
      removedResults = JSON.parse(JSON.stringify(ctx._qrResults[index]));
      ctx._qrResults.splice(index, 1);
    }

    if (Array.isArray(ctx._qrFind) && ctx._qrFind.length > index) {
      removedFind = JSON.parse(JSON.stringify(ctx._qrFind[index]));
      ctx._qrFind.splice(index, 1);
    }

    // page 번호는 유지
    // ctx._qrResults.forEach((r, i) => Utils.logInfo(`page remains: ${r.page}`));

    // 다음 QR 촬영 시 index 갱신
    ctx._qrPageIndex = ctx._qrResults.length;

    Utils.logInfo("after delete (no reindex):", JSON.parse(JSON.stringify(ctx._qrResults)));

    return { removedResults, removedFind, newPageIndex: ctx._qrPageIndex };
  }

  // 이미지 아이템 (미리보기 + 재촬영)
  _createImageItem(imgObj, index, totalCount) {
    const item = document.createElement("div");
    item.className = "image-item";

    const img = document.createElement("img");
    img.src = imgObj.dataUrl;

    const indexTag = document.createElement("div");
    indexTag.className = "image-index";
    indexTag.textContent = `Page ${index + 1}`;

    const bottomTag = document.createElement("div");
    bottomTag.className = "image-progress";
    bottomTag.textContent = `${index + 1} / ${totalCount}`;

    const retakeBtn = document.createElement("button");
    retakeBtn.className = "retake-btn";
    // retakeBtn.textContent = "×";
    const icon = document.createElement("img");
    // icon.src = "/images/delete.png";
    icon.className = "icon-svg";

    retakeBtn.appendChild(icon);
    retakeBtn.onclick = () => {
      this._context.currentIndex = imgObj.index;

      // 수정: 공통 삭제 함수 호출
      this.unifiedDeleteImage(imgObj.index);

      this.updatePreviewModal();
      const existingModal = document.getElementById("fullPreviewModal");
      if (existingModal) existingModal.remove();
      this.showAllImagesModal();
    };

    const maskEditBtn = document.createElement("button");
    maskEditBtn.className = "mask-edit-btn";

    maskEditBtn.innerHTML = `
  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" style="flex-shrink: 0;">
    <path d="M11 4H4C3.46957 4 2.96086 4.21071 2.58579 4.58579C2.21071 4.96086 2 5.46957 2 6V20C2 20.5304 2.21071 21.0391 2.58579 21.4142C2.96086 21.7893 3.46957 22 4 22H18C18.5304 22 19.0391 21.7893 19.4142 21.4142C19.7893 21.0391 20 20.5304 20 20V13" 
          stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
    <path d="M18.5 2.5C18.8978 2.10217 19.4374 1.87868 20 1.87868C20.5626 1.87868 21.1022 2.10217 21.5 2.5C21.8978 2.89782 22.1213 3.43739 22.1213 4C22.1213 4.56261 21.8978 5.10217 21.5 5.5L12 15L8 16L9 12L18.5 2.5Z" 
          stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
  </svg>
  <span style="margin-left: 5px;">마스킹 편집</span>
`;
    maskEditBtn.onclick = () => {
      this.showMaskingEditModal(index);
      document.getElementById("fullPreviewModal").style.display = "none";
    };

    item.appendChild(img);
    item.appendChild(indexTag);
    item.appendChild(bottomTag);
    item.appendChild(retakeBtn);
    item.appendChild(maskEditBtn);

    return item;
  }

  showMaskingEditModal(index) {
    new MaskEditor(this._context._ocrModuleManager.dScanner, index, () => {
      // 1) 작은 미리보기 갱신
      this.updatePreviewModal();

      // 2) 전체 모달이 열려있다면 재생성(제거 후 다시 열기)
      const existingModal = document.getElementById("fullPreviewModal");
      if (existingModal) {
        existingModal.remove();
        // 재생성: showAllImagesModal()는 내부에서 maskingInstances 재생성 등 처리하므로 호출
        this.showAllImagesModal();
      }
    });
  }

  // 이미지 없을 때 + 표시
  _createPlaceholderItem() {
    const placeholder = document.createElement("div");
    placeholder.className = "image-placeholder empty";

    const plus = document.createElement("div");
    plus.className = "plus-icon";
    plus.textContent = "+";

    placeholder.appendChild(plus);

    this._context._title.innerHTML = "문서를 촬영해주세요.";
    return placeholder;
  }

  // 하단 버튼 영역
  _createModalButtonArea() {
    const area = document.createElement("div");
    area.id = "modal-button-area";

    const images = this._context._ocrModuleManager.dScanner.images;
    const validImages = images.filter((img) => img !== null);

    const getImagesBtn = document.createElement("button");

    getImagesBtn.id = "getImagesBtn";
    getImagesBtn.classList.add("affine-button");
    getImagesBtn.addEventListener("click", this.handleImageCaptureClick);

    const doneBtn = document.createElement("button");

    doneBtn.id = "btnCaptureDone";
    doneBtn.textContent = "촬영종료";
    doneBtn.classList.add("affine-button");

    doneBtn.addEventListener("click", this.handleDocumentDone);
    if (validImages.length === 0) {
      doneBtn.disabled = true;
      doneBtn.classList.add("disabled");
      getImagesBtn.textContent = "촬영";
    } else {
      getImagesBtn.textContent = "계속촬영";
    }
    area.appendChild(getImagesBtn);
    area.appendChild(doneBtn);

    return area;
  }

  _clearGuideBox() {
    const modalContent = this._context._innerGuideBox.querySelector(".scanContent");
    if (modalContent) modalContent.innerHTML = "";
  }

  clearAllAffineElements() {
    // affine-container 클래스를 가진 모든 요소 삭제
    const targets = document.querySelectorAll(".affine-container, #affine-button-area");
    targets.forEach((el) => el.remove());

    this._context._isEditingScreen = false;
  }

  async _processDocumentCapture(cropImageData, resultCode) {
    // 1. 캡처 UI 초기화 (타이머, 가이드박스, 로딩 숨김)
    this._prepareCaptureUI();

    // 2. 문서 교정(Affine) 진행
    const points = resultCode.resultJSON.points;
    const affineResult = await this.processDocumentAffine(cropImageData.imageData, points);

    // 교정 취소 또는 좌표 오류 시 중단
    if (!affineResult) return null;

    // 3. 문서 이미지 가공 (Worker 통신 및 캔버스 정규화)
    const processedResult = await this._processAffineImage(affineResult);

    // 4. 결과 저장 및 모달 노출 (D-Scan 타입일 경우)
    if (Utils.OcrTypeConfig.dscan.includes(this._context._ocrType)) {
      await this.saveCroppedImage(processedResult);
      await this.showAllImagesModal();
    }

    // 5. 카운트 업데이트 및 상태 복구
    return this._finalizeCapture(processedResult, affineResult.isSave, resultCode);
  }

  /** [Step 1] UI 및 타이머 초기화 */
  _prepareCaptureUI() {
    this._context.resetTimerDisplay();
    this._clearGuideBox();
    Utils.hideLoading();

    this._context._title.innerHTML = "화면의 네 점을 드래그하여 </br>문서의 네 모서리에 맞춰주세요.";
    if (this._context.isLandscape()) {
      this._context._title.classList.add("affine");
    }
  }

  /** [Step 3] 이미지 후처리 로직 (Worker 호출 + 규격화) */
  async _processAffineImage(affineResult) {
    const { sortedPoints, imageData } = affineResult;

    Utils.showLoading();
    // Worker를 통한 교정 이미지 생성
    const result = await this._context._ocrModuleManager.getAffineFromWorker(imageData, sortedPoints);

    // UI 정리
    this._context._title.innerHTML = Utils.getOcrMessage(this._context._ocrType);
    this._context._title.classList.remove("affine");
    this._clearAffineUI();

    // 이미지 규격화 (Doc/ID Card 판별 후 다시 그리기)
    const base64Data = result.affineResult.resultJSON.base64image;
    const dataUrl = `data:image/jpeg;base64,${base64Data}`;
    const rgbImage = await Utils.base64ToRGBUint8Array(dataUrl);

    const docType = Utils.detectDocOrIdCard(sortedPoints);
    const newBase64 = await Utils.drawDocOrIdCardOnCanvasUniform(base64Data, docType, rgbImage.width, rgbImage.height);

    if (newBase64) {
      result.affineResult.resultJSON.base64image = newBase64;
      this._context._base64Docs = newBase64;
    }

    Utils.hideLoading();
    return result; // 가공된 최종 result 반환
  }

  /** [Step 5] 최종 데이터 정리 및 카운트 증가 */
  _finalizeCapture(processedResult, isSave, resultCode) {
    const dataUrl = `data:image/jpeg;base64,${processedResult.affineResult.resultJSON.base64image}`;

    this._context._currentCaptueCount++;
    this._context._successfulCaptures++;

    if (this._context._ocrType == 27) {
      this._context._qrCheck = true;
    }

    return {
      isSuccess: isSave,
      cropImageData: dataUrl,
      resultCode,
    };
  }

  /** UI 정리 헬퍼 함수 */
  _clearAffineUI() {
    if (this._context._ocrType == 27) {
      const qrDiv = document.querySelector(".qr-page-result");
      if (qrDiv) qrDiv.remove();
    }
    document.querySelectorAll(".affineDiv, #affine-button-area").forEach((el) => el.remove());
  }

  // 클래스 안쪽 또는 외부
  handleImageCaptureClick = () => {
    if (this._context._currentCaptueCount >= this._context._totalPages) {
      Utils.showAlert("촬영 가능한 페이지 수를 초과했습니다.");
      return;
    }
    if (this._context._ocrType == 27) {
      this._context._qrCheck = true;
    }

    const modal = document.getElementById("fullPreviewModal");
    if (modal) {
      modal.remove(); // 또는 modal.style.display = "none";
    }

    const modalBtn = document.getElementById("modal-button-area");
    if (modalBtn) {
      modalBtn.remove();
    }

    this._context._letterboxBottom.innerHTML = "";
    // this._letterboxTop.innerHTML = "";

    const captureBtn = document.querySelector("#cap_btn");

    if (captureBtn && this._context._useCapOcr == 2) {
      captureBtn.style.display = "block";
    }
    this._context._pauseCapture = false;
    this._context._captureinProgress = true;

    if (typeof this._context.processDocumentCapture === "function") {
      this._context.processDocumentCapture(true);
    }

    const videoWrapper = document.querySelector(".video-wrapper-scan");
    videoWrapper.style.display = "block";
  };

  handleDocumentDone = async () => {
    this._context.Pages = this._maskingInstances.length;
    this.clearAllAffineElements();
    // 마스킹 이미지 → dScanner 이미지로 교체
    this._maskingInstances.forEach((masked, index) => {
      const maskedBase64 = masked.getMaskedFinalImage();
      this._context._ocrModuleManager.dScanner.setImage(index, maskedBase64);
    });

    // 이제 getAllImages() 하면 이미 마스킹된 이미지가 나옴
    const imageDataUrls = this._context._ocrModuleManager.dScanner.getAllImages();
    // Utils.logInfo("최종 이미지 리스트:", imageDataUrls);
    this._context.Pages = imageDataUrls.length;

    this._context._documentCaptureDone = true;
    const imageDataList = [];
    let imageWidth = null;
    let imageHeight = null;
    for (const base64 of imageDataUrls) {
      const { rgb, width, height } = await Utils.base64ToRGBUint8Array(base64);
      imageDataList.push(rgb); // RGB Uint8Array
      imageWidth = width;
      imageHeight = height;
    }

    const payload = {
      type: "convert",
      width: imageWidth,
      height: imageHeight,
      images: imageDataList,
    };

    const result = await this._context._ocrModuleManager.sendRequest("document", payload, "convertResult");

    const arrayBuffer = result.data;
    if (this._context._convertTo == 1) {
      this._context._convertBlob = new Blob([arrayBuffer], { type: "image/tiff" });
    } else if (this._context._convertTo == 2) {
      this._context._convertBlob = new Blob([arrayBuffer], { type: "application/pdf" });
    }

    this._context._base64Docs = imageDataUrls[imageDataUrls.length - 1];
    this._context.emitCaptureEvent();

    const url = URL.createObjectURL(this._context._convertBlob);
    const a = document.createElement("a");
    a.href = url;
    a.download = this._context._convertTo == 1 ? "scanned.tiff" : "scanned.pdf";

    this._context._ocrModuleManager.dScanner.clearImages();
    const modalButtonArea = document.getElementById("modal-button-area");
    if (modalButtonArea) {
      modalButtonArea.remove();
    }
    const existingModal = document.getElementById("fullPreviewModal");
    if (existingModal) existingModal.remove();

    a.click();
    URL.revokeObjectURL(url);
  };
}
