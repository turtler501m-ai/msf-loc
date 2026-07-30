// import { config } from "../configs.js";
import { loadConfig } from "./configLoader.js";
export const configPromise = loadConfig();
let previousMessage = null;
export let isDevMode = true;
let startXOffset = 0;
let startYOffset = 0;

/**
 * @section [config]
 * -------------------------------------------------------------------
 */
let config = null;
configPromise.then((cfg) => {
  config = cfg;
});

export const OcrTypeConfig = {
  document: [7, 14, 15, 16, 24, 27],
  _document: [7, 14, 15, 24],
  detect: [1, 2, 3, 4, 7, 10, 11, 14, 15, 16, 19, 21, 22, 23, 24, 25, 26, 27],
  qr: [4, 26],
  passport: [25],
  dscan: [16, 27],
};

export const guideStrategyMap = {
  1: "idcard",
  2: "passport",
  3: "creditcard",
  4: "qrcode",
  7: "crop",
  8: "account",
  9: "check",
  10: "giro",
  11: "idcard",
  12: "giroEpn",
  14: "docs",
  15: "fullpage",
  16: "dscan",
  19: "dataset",
  21: "idcard",
  22: "idcard",
  23: "idcardfull",
  24: "biridge",
  25: "passportWasm",
  26: "dualScan",
  27: "mds",
};

export function getOcrLabelByType(ocrType) {
  const buttonConfig = config.buttonsData.find((btn) => btn.type === ocrType);
  return buttonConfig ? buttonConfig.label : "인식";
}

export function updateBackButtonTitle(parent, ocrType) {
  const label = this.getOcrLabelByType(ocrType);
  const titleElement = parent.querySelector(".back-btn-title");

  if (titleElement) {
    titleElement.textContent = label;
  }
}
/**
 * @section [camera]
 * -------------------------------------------------------------------
 */
export function delay(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

export function _isSuccessCode(result) {
  return result?.resultCode?.resultJSON?.resultCode === "0000";
}

export function isDetectSuccessful(detectSuccess) {
  return detectSuccess;
}

export function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

export function getVideoRatio(ocrType, isRotated) {
  let ratio = { width: 4, height: 3 }; // 기본 비율

  switch (ocrType) {
    case config.OCR_TYPE.IDCARD:
    case config.OCR_TYPE.PASSPORTWASM:
      // ratio = { width: 1.61, height: 1 };
      ratio = { width: 4, height: 3 };
      break;
    case config.OCR_TYPE.PASSPORT:
    case config.OCR_TYPE.PASSPORTWASM:
      ratio = { width: 4, height: 3 };
      break;
    case config.OCR_TYPE.QRCODE:
      // ratio = { width: 4, height: 3 };
      ratio = { width: 1, height: 1 };
      break;
    case config.OCR_TYPE.CREDITCARD:
      ratio = { width: 1, height: 0.65 };
      break;
    case config.OCR_TYPE.SEALCERT:
    case config.OCR_TYPE.BIZREGCERT:
    case config.OCR_TYPE.CROP:
    case config.OCR_TYPE.IDFACE:
    case config.OCR_TYPE.FULLPAGE:
    case config.OCR_TYPE.DSCAN:
    case config.OCR_TYPE.DOCS:
    case config.OCR_TYPE.BIRIDGE:
    case config.OCR_TYPE.MDS:
      ratio = { width: 1, height: 1.4 };
      break;
    case config.OCR_TYPE.DUALSCAN:
    case config.OCR_TYPE.ACCOUNT:
      ratio = { width: 2, height: 1 };
      break;
    case config.OCR_TYPE.CHECK:
      ratio = { width: 40, height: 17 };
      break;
    case config.OCR_TYPE.GIRO:
    case config.OCR_TYPE.GIROEPN:
      ratio = { width: 13, height: 8 };
      break;
    case config.OCR_TYPE.FACEREGIST:
      if (this._imageDiv == "src1") {
        ratio = { width: 1, height: 1.4 };
      } else if (this._imageDiv == "src2") {
        ratio = { width: 4, height: 3 };
      }
      break;
    default:
      ratio = { width: 4, height: 3 };
  }

  // 비율 반전 여부에 따라 width와 height 값을 반전
  if (isRotated) {
    return { width: ratio.height, height: ratio.width };
  }

  return ratio;
}

export function convertGuideAreaCoordinates(guideArea, pictureWidth, pictureHeight, ocrType) {
  var guideAreaRect = guideArea.getBoundingClientRect();

  // 크롭 영역 초기화
  var cropWidth = 0;
  var cropHeight = 0;
  var guideAreaX = 0;
  var guideAreaY = 0;

  var fitWidth = pictureWidth / guideAreaRect.width <= pictureHeight / guideAreaRect.height;

  // 세로가 더 큰 경우에 대한 처리
  if (fitWidth) {
    switch (ocrType) {
      case config.OCR_TYPE.GIROEPN: //
        cropWidth = pictureWidth;
        cropHeight = (pictureWidth * guideAreaRect.height) / guideAreaRect.width;
        cropWidth = cropWidth * 0.6;
        cropHeight = cropHeight * 0.15;
        guideAreaY = (pictureHeight - cropHeight) / 2;
        guideAreaX = pictureWidth * 0.4;
        break;
      case config.OCR_TYPE.INGAM:
        const pct = {
          width: 0.5, // 50%
          height: 0.6, // 60%
          top: 0.2, // 20%
          left: 0.25, // 25%
        };

        const usedAreaX = pictureWidth * pct.left;
        const usedAreaY = pictureHeight * pct.top;
        const usedAreaWidth = pictureWidth * pct.width;
        const usedAreaHeight = pictureHeight * pct.height;
        cropWidth = usedAreaWidth;
        cropHeight = (usedAreaWidth * guideAreaRect.height) / guideAreaRect.width;

        guideAreaX = usedAreaX;
        guideAreaY = usedAreaY + (usedAreaHeight - cropHeight) / 2;
        break;
      case config.OCR_TYPE.PASSPORT:
      case config.OCR_TYPE.PASSPORTWASM:
        cropWidth = pictureWidth;
        cropHeight = (pictureWidth * guideAreaRect.height) / guideAreaRect.width;
        guideAreaY = (pictureHeight - cropHeight) / 2;
        guideAreaX = 0;
        guideAreaY += (cropHeight * 3) / 4;
        cropHeight = cropHeight / 4;
        break;
      case config.OCR_TYPE.QRCODE:
        const pctQR = {
          size: 0.6, // 크기 비율
          top: 0.2,
          left: 0.2,
        };

        const base = Math.min(pictureWidth, pictureHeight);
        cropWidth = base * pctQR.size;
        cropHeight = cropWidth; // 정사각형

        guideAreaX = (pictureWidth - base) / 2 + base * pctQR.left;
        guideAreaY = (pictureHeight - base) / 2 + base * pctQR.top;
        break;
      default:
        cropWidth = pictureWidth;
        cropHeight = (pictureWidth * guideAreaRect.height) / guideAreaRect.width;
        guideAreaY = (pictureHeight - cropHeight) / 2;
        guideAreaX = 0;
        // 다른 config.OCR_TYPE에 대한 처리 추가
        break;
    }
  } else {
    switch (ocrType) {
      case config.OCR_TYPE.GIROEPN:
        cropHeight = pictureHeight;
        cropWidth = (pictureHeight * guideAreaRect.width) / guideAreaRect.height;
        cropWidth = cropWidth * 0.6;
        cropHeight = cropHeight * 0.15;
        guideAreaY = (pictureHeight - cropHeight) / 2;
        guideAreaX = pictureWidth * 0.4;
        break;
      case config.OCR_TYPE.INGAM:
        const pct = {
          width: 0.5, // 50%
          height: 0.6, // 60%
          top: 0.2, // 20%
          left: 0.25, // 25%
        };
        guideAreaX = pictureWidth * pct.left;
        guideAreaY = pictureHeight * pct.top;
        cropWidth = pictureWidth * pct.width;
        cropHeight = pictureHeight * pct.height;
        break;
      case config.OCR_TYPE.PASSPORT:
      case config.OCR_TYPE.PASSPORTWASM:
        cropHeight = pictureHeight;
        cropWidth = (pictureHeight * guideAreaRect.width) / guideAreaRect.height;
        guideAreaY = 0;
        guideAreaX = (pictureWidth - cropWidth) / 2;
        guideAreaY += (cropHeight * 3) / 4;
        cropHeight = cropHeight / 4;
        break;
      case config.OCR_TYPE.QRCODE:
        const pctQR = {
          size: 0.6, // width, height 동일 (60%)
          top: 0.2, // 20%
          left: 0.2, // 20%
        };
        const base = Math.min(pictureWidth, pictureHeight);

        // 크롭 영역 크기
        cropWidth = base * pctQR.size;
        cropHeight = cropWidth; // 정사각형

        // 좌표 계산
        guideAreaX = (pictureWidth - base) / 2 + base * pctQR.left;
        guideAreaY = (pictureHeight - base) / 2 + base * pctQR.top;
        break;
      default:
        cropHeight = pictureHeight;
        cropWidth = (pictureHeight * guideAreaRect.width) / guideAreaRect.height;
        guideAreaY = 0;
        guideAreaX = (pictureWidth - cropWidth) / 2;
        // 다른 config.OCR_TYPE에 대한 처리 추가
        break;
    }
  }

  var coordinates = {
    x: guideAreaX,
    y: guideAreaY,
    width: cropWidth,
    height: cropHeight,
  };

  return coordinates;
}

export function _createDiv(className, styles, id) {
  const el = document.createElement("div");
  if (className) el.className = className;
  if (id) el.id = id;
  if (styles) Object.assign(el.style, styles);
  return el;
}

export function _createBlackPoster() {
  const canvas = document.createElement("canvas");
  canvas.width = 640; // 기본값
  canvas.height = 480;
  const ctx = canvas.getContext("2d");
  ctx.fillStyle = "black";
  ctx.fillRect(0, 0, canvas.width, canvas.height);
  return canvas.toDataURL("image/png");
}

/**
 * @section [document]
 * -------------------------------------------------------------------
 */

export function _checkDuplicate(value) {
  const isDup = this._qrResults?.some((p) => p.results?.some((r) => r?.result?.formResult?.fieldResults?.[1]?.value?.includes(value)));
  if (isDup) console.log(`이미 촬영한 QR (${value})`);
  return isDup;
}
export function canvasX(clientX, guideAreaRect, imageData, radius = 0) {
  var bound = guideAreaRect;
  return Math.round(clientX * (bound.width / imageData.width) - radius);
}

export function canvasY(clientY, guideAreaRect, imageData, radius = 0) {
  var bound = guideAreaRect;
  return Math.round(clientY * (bound.height / imageData.height) - radius);
}

export function reverseCanvasX(relativeX, guideAreaRect, imageData, radius) {
  var bound = guideAreaRect;
  return Math.round((relativeX + radius) / (bound.width / imageData.width));
}

export function reverseCanvasY(relativeY, guideAreaRect, imageData, radius) {
  var bound = guideAreaRect;
  return Math.round((relativeY + radius) / (bound.height / imageData.height));
}

export function detectDocOrIdCard(points) {
  if (!points || points.length !== 4) {
    return "unknown";
  }

  // 실제 width/height 계산
  const widthTop = Math.hypot(points[1].x - points[0].x, points[1].y - points[0].y);
  const widthBottom = Math.hypot(points[2].x - points[3].x, points[2].y - points[3].y);
  const heightLeft = Math.hypot(points[3].x - points[0].x, points[3].y - points[0].y);
  const heightRight = Math.hypot(points[2].x - points[1].x, points[2].y - points[1].y);

  const avgWidth = (widthTop + widthBottom) / 2;
  const avgHeight = (heightLeft + heightRight) / 2;

  const ratio = avgWidth / avgHeight;

  // 기준 비율
  const idCardRatio = 1.58; // 신분증
  const docRatio = 0.71; // 문서(A4 세로 기준)

  // 문서/신분증 중 차이가 적은 쪽 선택
  const diffIdCard = Math.abs(ratio - idCardRatio);
  const diffDoc = Math.abs(ratio - docRatio);

  return diffIdCard < diffDoc ? "idcard" : "document";
}

export function drawDocOrIdCardOnCanvasUniform(base64Data, docType, referenceWidth, referenceHeight) {
  return new Promise((resolve) => {
    const canvas = document.createElement("canvas");
    const ctx = canvas.getContext("2d");

    const img = new Image();
    img.src = `data:image/jpeg;base64,${base64Data}`;

    img.onload = () => {
      // 항상 reference 크기 사용
      canvas.width = referenceWidth;
      canvas.height = referenceHeight;

      ctx.fillStyle = "white";
      ctx.fillRect(0, 0, referenceWidth, referenceHeight);

      if (docType === "document") {
        ctx.drawImage(img, 0, 0, referenceWidth, referenceHeight);
      } else if (docType === "idcard") {
        // 신분증 4:3 비율 유지, 가로 기준
        const cardRatio = 4 / 3;
        let targetWidth = referenceWidth * 0.9; // 여백 10%
        let targetHeight = targetWidth / cardRatio;

        if (targetHeight > referenceHeight * 0.9) {
          targetHeight = referenceHeight * 0.9;
          targetWidth = targetHeight * cardRatio;
        }

        const offsetX = (referenceWidth - targetWidth) / 2;
        const offsetY = (referenceHeight - targetHeight) / 2;

        ctx.drawImage(img, offsetX, offsetY, targetWidth, targetHeight);
      }

      const newBase64 = canvas.toDataURL("image/jpeg", 1.0).split(",")[1];
      resolve(newBase64);
    };

    img.onerror = (err) => {
      console.error("이미지 로딩 실패", err);
      resolve(null);
    };
  });
}

export function createLine(x1, y1, x2, y2) {
  const line = document.createElementNS("http://www.w3.org/2000/svg", "line");
  line.setAttribute("x1", x1);
  line.setAttribute("y1", y1);
  line.setAttribute("x2", x2);
  line.setAttribute("y2", y2);
  line.setAttribute("stroke", "#4DB6AC");
  line.setAttribute("stroke-width", "2");
  return line;
}

export function updateLines(innerCircles, lines) {
  for (let i = 0; i < lines.length; i++) {
    const start = innerCircles[i];
    const end = innerCircles[(i + 1) % innerCircles.length];
    lines[i].setAttribute("x1", start.x);
    lines[i].setAttribute("y1", start.y);
    lines[i].setAttribute("x2", end.x);
    lines[i].setAttribute("y2", end.y);
  }
}

export function handleTouchStart(index, pointDiv, affinePoints, x, y) {
  return function (event) {
    const parentRect = pointDiv.parentElement.getBoundingClientRect();

    // 전역 변수로 값 저장
    startXOffset = event.touches[0].clientX - parentRect.left - pointDiv.offsetLeft;
    startYOffset = event.touches[0].clientY - parentRect.top - pointDiv.offsetTop;

    affinePoints[index] = { x, y };
  };
}

// 터치 이동 처리 함수
export function handleTouchMove(pointDiv, affinePoints, innerCircles, lines) {
  return function (event) {
    if (event.touches.length > 1) return;

    const touchX = event.touches[0].clientX;
    const touchY = event.touches[0].clientY;

    const parent = pointDiv.parentElement;
    const parentRect = parent.getBoundingClientRect();

    const w = pointDiv.offsetWidth;
    const h = pointDiv.offsetHeight;
    const halfW = w / 2;
    const halfH = h / 2;

    // UI 한계치 (성빈님 기존 로직 유지)
    const minX = -halfW;
    const maxX = parentRect.width - halfW;
    const minY = -halfH;
    const maxY = parentRect.height - halfH;

    // [수정] 전역 startXOffset 대신 엘리먼트에 저장된 값 사용 (튐 방지 핵심)
    let nextX = touchX - parentRect.left - (pointDiv._startXOffset || 0);
    let nextY = touchY - parentRect.top - (pointDiv._startYOffset || 0);

    // Clamp
    if (nextX < minX) nextX = minX;
    if (nextX > maxX) nextX = maxX;
    if (nextY < minY) nextY = minY;
    if (nextY > maxY) nextY = maxY;

    // UI 이동
    pointDiv.style.left = nextX + "px";
    pointDiv.style.top = nextY + "px";

    // [데이터 업데이트]
    const index = parseInt(pointDiv.className.split(" ")[1].slice(-1));

    // 성빈님의 reverseCanvasX/Y는 (relativeX + radius)를 하므로
    // 여기서 저장하는 nextX는 반드시 'div의 왼쪽 상단' 값이어야 합니다.
    affinePoints[index - 1] = { x: nextX, y: nextY };

    innerCircles[index - 1].x = nextX + halfW;
    innerCircles[index - 1].y = nextY + halfH;

    updateLines(innerCircles, lines);
  };
}

// 터치 종료 처리 함수
export function handleTouchEnd(event) {
  // 터치 종료 시 필요한 작업 수행
}

export function sortPoints(points) {
  if (points.length !== 4) {
    throw new Error("Invalid number of points. Expected 4 points.");
  }

  // 1. y좌표 기준으로 정렬 (위 2개, 아래 2개)
  points.sort((a, b) => a.y - b.y);

  let topPoints = points.slice(0, 2);
  let bottomPoints = points.slice(2, 4);

  // 2. 위쪽 2개 중에서 x좌표 기준으로 좌/우 결정
  topPoints.sort((a, b) => a.x - b.x);
  // 3. 아래쪽 2개도 동일
  bottomPoints.sort((a, b) => a.x - b.x);

  // 최종: [좌상, 우상, 우하, 좌하]
  return [topPoints[0], topPoints[1], bottomPoints[1], bottomPoints[0]];
}

export function getTopRightQuarter(imageData) {
  const { width, height } = imageData;

  // 잘라낼 비율 (QR이 위치할 범위 조정)
  const cropWidthRatio = 0.3; // 오른쪽 30%
  const cropHeightRatio = 0.25; // 위쪽 25%

  const cropWidth = Math.floor(width * cropWidthRatio);
  const cropHeight = Math.floor(height * cropHeightRatio);

  // 시작점: 오른쪽 상단
  const startX = width - cropWidth;
  const startY = 0;

  // 캔버스 생성
  const canvas = document.createElement("canvas");
  canvas.width = cropWidth;
  canvas.height = cropHeight;
  const ctx = canvas.getContext("2d");

  // 원본 이미지를 임시 캔버스에 그리기
  const tempCanvas = document.createElement("canvas");
  tempCanvas.width = width;
  tempCanvas.height = height;
  const tempCtx = tempCanvas.getContext("2d");
  tempCtx.putImageData(imageData, 0, 0);

  // 원하는 영역만 잘라서 복사
  ctx.drawImage(
    tempCanvas,
    startX,
    startY, // 원본에서 잘라낼 시작점
    cropWidth,
    cropHeight, // 잘라낼 크기
    0,
    0,
    cropWidth,
    cropHeight,
  );

  const cropped = ctx.getImageData(0, 0, cropWidth, cropHeight);

  return {
    imageData: cropped,
    width: cropWidth,
    height: cropHeight,
  };
}

export function getTopLeftQuarter(imageData) {
  const { width, height } = imageData;

  // 잘라낼 비율 (QR/BarCode 위치에 맞게 조정)
  const cropWidthRatio = 0.3; // 왼쪽 30%
  const cropHeightRatio = 0.25; // 위쪽 25%

  const cropWidth = Math.floor(width * cropWidthRatio);
  const cropHeight = Math.floor(height * cropHeightRatio);

  // 시작점: 왼쪽 상단
  const startX = 0;
  const startY = 0;

  // 캔버스 생성
  const canvas = document.createElement("canvas");
  canvas.width = cropWidth;
  canvas.height = cropHeight;
  const ctx = canvas.getContext("2d");

  // 원본 이미지를 임시 캔버스에 그리기
  const tempCanvas = document.createElement("canvas");
  tempCanvas.width = width;
  tempCanvas.height = height;
  const tempCtx = tempCanvas.getContext("2d");
  tempCtx.putImageData(imageData, 0, 0);

  // 원하는 영역만 잘라서 복사
  ctx.drawImage(
    tempCanvas,
    startX,
    startY, // 원본에서 잘라낼 시작점
    cropWidth,
    cropHeight, // 잘라낼 크기
    0,
    0,
    cropWidth,
    cropHeight,
  );

  const cropped = ctx.getImageData(0, 0, cropWidth, cropHeight);

  return {
    imageData: cropped,
    width: cropWidth,
    height: cropHeight,
  };
}

export function zipImagesAndQr(images, qrResults) {
  if (!Array.isArray(images) || !Array.isArray(qrResults)) {
    throw new Error("둘 다 배열이어야 합니다.");
  }

  if (images.length !== qrResults.length) {
    throw new Error(`배열 길이가 다릅니다. images: ${images.length}, qrResults: ${qrResults.length}`);
  }

  // 같은 인덱스끼리 묶어서 객체 배열로 만들기
  const result = images.map((img, idx) => ({
    image: img,
    qrResult: qrResults[idx],
  }));

  return result;
}

export function parseQrResult(qrResult) {
  return qrResult.map((pageItem) => {
    const pageData = { page: pageItem.page };

    // left 정보
    const leftItem = pageItem.results.find((r) => r.position === "left");
    if (leftItem) {
      const value = leftItem.result.formResult.fieldResults.find((f) => f.displayName === "바코드 인식 값").value;
      const parts = value.split("^^");
      const last = parts[parts.length - 1];
      pageData.userId = last.slice(0, 8);
      pageData.imageKey = last;
    }

    // right 정보
    const rightItem = pageItem.results.find((r) => r.position === "right");
    if (rightItem) {
      const value = rightItem.result.formResult.fieldResults.find((f) => f.displayName === "바코드 인식 값").value;
      const parts = value.split("^^");
      pageData.docCode = parts[0];
      pageData.docVersion = parts[1];
    }

    return pageData;
  });
}

export function extractDocInfo(qrcode) {
  return qrcode.map((item) => {
    return {
      docCode: item.docCode,
      docVersion: item.docVersion,
    };
  });
}

export function getQrCodeValue(qrcodeData) {
  const value = qrcodeData?.[0]?.results?.[0]?.result?.formResult?.fieldResults?.find((f) => f.fieldId === "401")?.value;

  if (!value) return null;

  return value.includes("^^") ? value.split("^^")[0] : value;
}

export function preventBackNavigation() {
  history.pushState(null, null, location.href);
  window.onpopstate = function (event) {
    // 사용자가 뒤로가기를 눌렀을 때 실행됨
    history.go(1);
    location.reload();
  };
}

/**
 * @section [UI/UX]
 * -------------------------------------------------------------------
 */
export function getElement(selector) {
  const el = selector.startsWith("#") ? document.getElementById(selector.slice(1)) : document.querySelector(selector);
  if (!el) console.warn(`Element not found: ${selector}`);
  return el;
}
export function get(id) {
  return document.getElementById(id);
}
export function hide(id) {
  this.get(id).style.display = "none";
}
export function show(id) {
  this.get(id).style.display = "block";
}
export function clearCanvas(id) {
  const canvas = this.get(id);
  if (canvas) {
    const ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);
  }
}
export function scrollTop() {
  window.scrollTo({ top: 0, behavior: "smooth" });
}
export function setDevMode(mode) {
  isDevMode = mode;
}

export function logError(...args) {
  if (!isDevMode) return;

  if (args.length === 1) {
    console.error(args[0]);
  } else if (args.length === 2) {
    console.error(args[0], args[1]);
  } else if (args.length > 2) {
    console.error(...args);
  }
}

export function logWarning(...args) {
  if (!isDevMode) return;

  if (args.length === 1) {
    console.warn(args[0]);
  } else if (args.length === 2) {
    console.warn(args[0], args[1]);
  } else if (args.length > 2) {
    console.warn(...args);
  }
}

export function logInfo(...args) {
  if (!isDevMode) return;

  if (args.length === 1) {
    console.log(args[0]);
  } else {
    console.log(...args);
  }
}

export function showAlert(message, onlyInDev = false) {
  if (onlyInDev && !isDevMode) return;
  alert(message);
}

export function addClickListener(selector, handler) {
  const element = document.querySelector(selector);
  if (element) {
    element.addEventListener("click", handler);
    return element; // 버튼 요소 반환
  }
  logError(`Element with selector "${selector}" not found.`); // 디버깅을 위한 로그
  return null;
}

export function getOcrTypeFromButtonId(buttonId) {
  const button = config.buttonsData.find((b) => b.id === buttonId);
  return button ? button.type : null;
}

export function showElements(elements) {
  if (typeof elements === "string") {
    elements = document.querySelectorAll(elements);
  }

  // elements가 NodeList 또는 배열인 경우 처리
  if (elements instanceof NodeList || Array.isArray(elements)) {
    elements.forEach((element) => {
      if (element instanceof HTMLElement) {
        element.style.display = "block";
      }
    });
  }
}

export function hideElement(element) {
  if (element instanceof HTMLElement) element.style.display = "none";
}

export function showMessage(element, message) {
  if (element instanceof HTMLElement) element.innerHTML = message;
}

// 요소 숨기기
export function hideElements(elements) {
  // elements가 문자열인 경우 querySelectorAll로 요소를 선택
  if (typeof elements === "string") {
    elements = document.querySelectorAll(elements);
  }

  // elements가 NodeList 또는 배열인 경우 처리
  if (elements instanceof NodeList || Array.isArray(elements)) {
    elements.forEach((element) => {
      if (element instanceof HTMLElement) {
        element.style.display = "none";
      }
    });
  }
}

export function showLoading(containerSelector = ".camera-section") {
  const container = document.querySelector(containerSelector);
  if (!container) {
    console.warn(`${containerSelector} 요소를 찾을 수 없습니다.`);
    return;
  }

  // 기존 오버레이 제거 (중복 방지)
  const existingOverlay = container.querySelector(".loading-overlay");
  if (existingOverlay) {
    existingOverlay.remove();
  }

  // 오버레이 생성
  const overlay = document.createElement("div");
  overlay.className = "loading-overlay";

  // three-body 로딩 스피너 생성
  const spinner = document.createElement("div");
  spinner.className = "three-body";

  for (let i = 0; i < 3; i++) {
    const dot = document.createElement("div");
    dot.className = "three-body__dot";
    spinner.appendChild(dot);
  }

  overlay.appendChild(spinner);
  container.appendChild(overlay);
}

export function hideLoading() {
  // 1. 현재 화면(document)에서 .loading-overlay 제거
  const pageOverlays = document.querySelectorAll(".loading-overlay");
  if (pageOverlays.length > 0) {
    pageOverlays.forEach((overlay, idx) => {
      // Utils.logInfo(`현재 화면 로딩 오버레이 ${idx + 1} 제거`);
      overlay.remove();
    });
  } else {
    // Utils.logInfo("현재 화면에 .loading-overlay 요소가 없습니다.");
  }

  // 2. iframe 내부에서 .loading-overlay 제거
  const iframe = document.getElementById("koiOcrFrame");
  if (!iframe) {
    // console.warn("ocrFrame iframe을 찾을 수 없습니다.");
    return;
  }

  const iframeDoc = iframe.contentDocument || iframe.contentWindow?.document;
  if (!iframeDoc) {
    // console.warn("iframe 문서에 접근할 수 없습니다.");
    return;
  }

  const iframeOverlays = iframeDoc.querySelectorAll(".loading-overlay");
  if (iframeOverlays.length > 0) {
    iframeOverlays.forEach((overlay, idx) => {
      // Utils.logInfo(`iframe 내부 로딩 오버레이 ${idx + 1} 제거`);
      overlay.remove();
    });
  } else {
    // Utils.logInfo("iframe 내부에 .loading-overlay 요소가 없습니다.");
  }
}

/**
 * @section [messages]
 * -------------------------------------------------------------------
 */
// 토큰&라이선스 체크 결과 처리 함수
export function getErrorMessage(tokenResult) {
  switch (tokenResult) {
    case 1:
      return "Loading Success.";
    case -1:
      return "Token authentication failed. Please check the token.";
    case -2:
      return "License authentication error. Please check your license.";
    case -3:
      return "The model license has expired. Please check the license.";
    default:
      return "Error initializing KoiOcr.";
  }
}

export const guideTexts = ["영역에 맞춤", "빛 반사 주의", "흔들림 주의"];

export function updateTitleCredit(detectResultCode) {
  let message = previousMessage;

  switch (detectResultCode) {
    case 0:
      // message = "";
      break;
    case -1:
      message = "카드를 가까이서 촬영해주세요."; // size_ratio, 원거리
      break;
    case -2:
      message = "카드를 레이아웃 안에 맞춰서 촬영해주세요."; // photo_ratio, 카드는 x
      break;
    case -3:
      message = "카드를 레이아웃 안에 맞춰서 촬영해주세요."; // type_ratio, 가로/세로 비율, 잘리거나 기울어진 경우
      break;
    case -4:
      message = "카드를 가리지 말고 빛반사에 주의하여<br>잘 보이도록 촬영해주세요."; // 필드 영역 일부 없음
      break;
    case -5:
      message = "카드 영역을 찾을 수 없습니다."; // 탐지 안됨
      break;
    case -7: // 라플라시안
      message = "너무 밝거나 어두운 곳에서 촬영은 피하고 <br>카메라 초점을 맞춰주세요.";
      break;
    default:
      message = "카드를 다시 촬영해주세요.";
  }
  previousMessage = message;
  return message;
}

export function updateTitleID(detectResultCode) {
  let message = previousMessage;

  switch (detectResultCode) {
    case 0:
      // message = ""; // 탐지 성공
      break;
    case -1:
      message = "신분증을 인식할 수 없습니다."; //신분증 탐지
      break;
    case -2:
      message = "신분증을 더 가까이 촬영해주세요."; //원거리
      break;
    case -3:
      message = "신분증을 레이아웃 안에 맞춰서 촬영해주세요."; //신분증 비율 체크
      break;
    case -4:
      message = "신분증을 레이아웃 안에 맞춰서 촬영해주세요."; //신분증 좌우 여백 체크
      break;
    case -5:
      // message = "신분증을 레이아웃 안에 맞춰서 촬영해주세요."; //신분증 필수 클래스 체크
      message = "신분증 정보가 가려져 있습니다."; //신분증 필수 클래스 체크
      break;
    case -6:
      message = "신분증을 레이아웃 안에 맞춰서 촬영해주세요."; //신분증 사진 영역 체크
      break;
    case -7: // 라플라시안
      message = "밝은 곳에서 초점을 맞춰 촬영해주세요.";
      break;
    case -8:
      message = "신분증의 사진을 가리지 않도록 촬영해주세요.";
      break;
    default:
      message = "";
  }
  previousMessage = message;
  return message;
}

export function updateTitleIDAC(detectResultCode) {
  let message = previousMessage;

  switch (detectResultCode) {
    case 0:
      break;
    case -1:
      message = "신분증을 인식할 수 없습니다.";
      break;
    case -2:
      message = "신분증을 더 가까이 촬영해주세요.";
      break;
    case -3:
      message = "신분증 비율이 아닙니다. <br>신분증을 촬영해주세요.";
      break;
    case -4:
      message = "신분증을 조금 멀리 두고 촬영해주세요.";
      break;
    case -5:
      message = "신분증 정보가 가려져 있습니다.";
      break;
    case -6:
      message = "얼굴 영역이 보이지 않습니다.";
      break;
    case -7:
      message = "밝은 곳에서 초점을 맞춰 촬영해주세요.";
      break;
    default:
      message = "";
  }
  previousMessage = message;
  return message;
}

export function getOcrMessage(ocrType) {
  switch (ocrType) {
    case 1:
      return "OCR 진행중입니다.";
    case 3:
      return "OCR 진행중입니다.";
    case 10:
      return "OCR 진행중입니다.";
    case 11:
      return "사본판별 진행중입니다.";
    case 16:
    case 27:
      // return "촬영한 문서를 확인한 후, <br>드래그하여 마스킹할 부분을 지워주세요.";
      return "촬영한 문서를 확인해주세요.";

    default:
      return "OCR 진행중입니다.";
  }
}

export function getOcrMessageAC(ocrType) {
  switch (ocrType) {
    case 1:
    case 21:
      return "OCR 진행중";
    case 3:
      return "OCR 진행중";
    case 10:
      return "OCR 진행중";
    case 11:
    case 22:
      return "사본판별 진행중";
    case 16:
    case 27:
      return "";
    default:
      return "OCR 진행중";
  }
}

export function getDetectMessage(ocrType, detectResultCode) {
  switch (ocrType) {
    case 1:
    case 2:
    case 11:
    case 19:
    case 23:
      return updateTitleID(detectResultCode);
    case 3:
      return updateTitleCredit(detectResultCode);
    case 14:
    case 15:
    case 16:
    case 27:
      return updateDocs(detectResultCode);
    // case 16:
    //   return "문서를 가이드 영역에 맞춰주세요.";
    case 21:
    case 22:
      return updateTitleIDAC(detectResultCode);
    case 4:
    case 26:
      return "QR 혹은 바코드를 인식해주세요.";
    default:
      return ""; // 혹은 null
  }
}

const defaultMessages = {
  id: "유효한 신분증을 찾을 수 없습니다. <br>촬영 가이드에 맞춰 다시 촬영해주세요.",
  passport: "유효한 여권을 찾을 수 없습니다. <br>촬영 가이드에 맞춰 다시 촬영해주세요.",
  card: "유효한 신용카드를 찾을 수 없습니다. <br>촬영 가이드에 맞춰 다시 촬영해주세요.",
  qr: "유효한 QR코드를 찾을 수 없습니다. <br>촬영 가이드에 맞춰 다시 촬영해주세요.",
  doc: "유효한 문서를 찾을 수 없습니다. <br>촬영 가이드에 맞춰 다시 촬영해주세요.",
  account: "유효한 계좌번호를 찾을 수 없습니다. <br>촬영 가이드에 맞춰 다시 촬영해주세요.",
  giro: "유효한 지로를 찾을 수 없습니다. <br>촬영 가이드에 맞춰 다시 촬영해주세요.",
};

export function getDefaultMessage(ocrType) {
  if ([1, 11, 23].includes(ocrType)) return defaultMessages.id;
  if (ocrType === 2) return defaultMessages.passport;
  if (ocrType === 3) return defaultMessages.card;
  if (ocrType === 4) return defaultMessages.qr;
  if ([5, 6, 7, 14, 15, 16, 27].includes(ocrType)) return defaultMessages.doc;
  if (ocrType === 8) return defaultMessages.account;
  if ([10, 12].includes(ocrType)) return defaultMessages.giro;
  return defaultMessages.id; // 기본 fallback (신분증)
}

export function updateDocs(detectResultCode) {
  const resultCode = detectResultCode.resultJSON.resultCode;
  switch (resultCode) {
    case 2000:
      return "문서를 찾을 수 없습니다.";
    default:
      return "문서를 가까이서 촬영해주세요.";
  }
}

export async function loadWorkerModule(worker, ocrType, extraData = {}) {
  if (!worker) {
    return {
      success: false,
      error: "Worker not initialized",
    };
  }

  const waitForMessage = (expectedTypes) =>
    new Promise((resolve, reject) => {
      const handler = (event) => {
        if (expectedTypes.includes(event.data.type)) {
          worker.removeEventListener("message", handler);
          resolve(event.data);
        }
      };

      const errorHandler = (error) => {
        worker.removeEventListener("message", handler);
        reject(error);
      };

      worker.addEventListener("message", handler);
      worker.addEventListener("error", errorHandler, { once: true });
    });

  try {
    worker.postMessage({ type: "init", ocrType, ...extraData });

    const result = await waitForMessage(["initComplete", "initFailed"]);

    if (result.type === "initComplete") {
      return {
        success: true,
        result,
      };
    }

    // initFailed
    return {
      success: false,
      error: result.error || "Worker initialization failed",
      modelType: result.modelType,
      result,
    };
  } catch (err) {
    console.error("Worker module error:", err);

    return {
      success: false,
      error: err?.message || String(err),
    };
  }
}

export function handleWorkerError({ module, type, error, result, ctx }) {
  const message = error || result?.error || `Worker load failed (${module}, type=${type})`;

  const normalizedError = {
    module,
    type,
    message,
    rawError: error,
    result,
  };

  console.error("[WorkerError]", normalizedError);

  return normalizedError;
}

export function handlePassportError(result, error, defaultMsg = "WASM module loading failed") {
  let msg = defaultMsg;

  if (result?.code == -1) msg += " : 서비스 url 오류입니다.";
  else if (result?.code == -2) msg += " : 라이선스 오류입니다.";
  else if (result?.code == -3) msg += " : 토큰 오류입니다.";
  else if (result?.code == -4) msg += " : yolo 모델 로드 오류입니다.";
  else if (result?.code == -5) msg += " :  clova 모델 로드 오류입니다.";
  else msg += " : 알 수 없는 오류입니다.";

  showAlert(`Error: ${msg}`);
  logError("WASM module error:", error || msg);
}

/**
 * @section [공통/유틸]
 * -------------------------------------------------------------------
 */
export function getCurrentTimeWithMilliseconds() {
  const now = new Date();

  // Get hours, minutes, seconds, and milliseconds
  const hours = now.getHours().toString().padStart(2, "0");
  const minutes = now.getMinutes().toString().padStart(2, "0");
  const seconds = now.getSeconds().toString().padStart(2, "0");
  const milliseconds = Math.floor(now.getMilliseconds() / 100).toString(); // Extract first digit without decimal

  // Combine into the desired format
  const currentTime = `${hours}:${minutes}:${seconds}.${milliseconds}`;

  return currentTime;
}

export function parseTimeString(timeString) {
  const [hours, minutes, seconds] = timeString.split(":");
  const [secs, millis] = seconds.split(".");

  return parseInt(hours) * 3600000 + parseInt(minutes) * 60000 + parseInt(secs) * 1000 + parseInt(millis) * 10;
}

export const isBase64Image = (imageData) => {
  // 정규 표현식을 사용하여 Base64 이미지 문자열인지 확인
  const base64Pattern = /^data:image\/(jpeg|png|gif);base64,/;
  return base64Pattern.test(imageData);
};

export const getImageFromImageData = (imageData) => {
  if (typeof imageData === "string") {
    const hasPrefix = imageData.startsWith("data:image/");
    return hasPrefix ? imageData : `data:image/jpeg;base64,${imageData}`;
  }
  const canvas = document.createElement("canvas");
  canvas.width = imageData.width;
  canvas.height = imageData.height;
  const context = canvas.getContext("2d");
  context.putImageData(imageData, 0, 0);
  const base64Data = canvas.toDataURL("image/jpeg");
  if (context) {
    context.clearRect(0, 0, canvas.width, canvas.height);
  }
  return base64Data;
};

export function convertImageDataToBase64(imageData) {
  const canvas = document.createElement("canvas");
  const context = canvas.getContext("2d");
  canvas.width = imageData.width;
  canvas.height = imageData.height;
  context.putImageData(imageData, 0, 0);
  return canvas.toDataURL("image/jpeg", 0.9).split(",")[1];
}

export function generateFileName(prefix = "rtc_", extension = ".jpg") {
  const currentDate = new Date();

  const year = currentDate.getFullYear();
  const month = ("0" + (currentDate.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 +1
  const day = ("0" + currentDate.getDate()).slice(-2);
  const hours = ("0" + currentDate.getHours()).slice(-2);
  const minutes = ("0" + currentDate.getMinutes()).slice(-2);
  const seconds = ("0" + currentDate.getSeconds()).slice(-2);

  // 파일 이름 형식: prefix + 년월일시분초 + 확장자
  return `${prefix}${year}${month}${day}${hours}${minutes}${seconds}${extension}`;
}

export const getTimeCheckJSON = (startTime, endTime, detail) => {
  if (startTime && endTime) {
    const startTimeMillis = Utils.parseTimeString(startTime);
    const endTimeMillis = Utils.parseTimeString(endTime);
    const totalTime = (endTimeMillis - startTimeMillis) / 1000;
    const ocrTime = parseFloat(parseFloat(detail.ocrResult.resultJSON.processTime).toFixed(1));
    const requestTime = (totalTime - ocrTime).toFixed(1);

    return { startTime, endTime, totalTime, requestTime, ocrTime };
  }
  return null;
};

export const base64toBlob = (base64Data, mimeString) => {
  var byteString = atob(base64Data);
  var ab = new ArrayBuffer(byteString.length);
  var ia = new Uint8Array(ab);

  for (var i = 0; i < byteString.length; i++) {
    ia[i] = byteString.charCodeAt(i);
  }

  return new Blob([ab], { type: mimeString });
};

export function downloadImageFromCanvas(canvas, filename = "getCropImage.jpeg", quality = 1.0) {
  const imageData = canvas.toDataURL("image/jpeg", quality);
  const link = document.createElement("a");
  link.href = imageData;
  link.download = filename;
  link.click();
}

export const getImageDataURL = ({ base64Data, cropData }) => {
  if (typeof base64Data === "string" && base64Data.length > 0) {
    const hasPrefix = base64Data.startsWith("data:image/");
    return hasPrefix ? base64Data : `data:image/jpeg;base64,${base64Data}`;
  }

  if (cropData) {
    return getImageFromImageData(cropData);
  }

  return null;
};

export const base64DataURL = (base64Data) => {
  if (typeof base64Data === "string" && base64Data.length > 0) {
    const hasPrefix = base64Data.startsWith("data:image/");
    return hasPrefix ? base64Data : `data:image/jpeg;base64,${base64Data}`;
  }
};

export async function base64ToRGBUint8Array(base64Uri) {
  const img = new Image();
  img.src = base64Uri;

  await new Promise((resolve) => {
    img.onload = resolve;
  });

  const width = img.width;
  const height = img.height;

  const canvas = document.createElement("canvas");
  canvas.width = width;
  canvas.height = height;

  const ctx = canvas.getContext("2d");
  ctx.drawImage(img, 0, 0);

  const imageData = ctx.getImageData(0, 0, width, height);
  const rgba = imageData.data; // Uint8ClampedArray

  const rgb = new Uint8Array(width * height * 3);
  for (let i = 0, j = 0; i < rgba.length; i += 4, j += 3) {
    rgb[j] = rgba[i]; // R
    rgb[j + 1] = rgba[i + 1]; // G
    rgb[j + 2] = rgba[i + 2]; // B
  }

  return { rgb, width, height };
}

export function dataURLtoBlob(dataURL) {
  var byteString = atob(dataURL.split(",")[1]);
  var mimeString = dataURL.split(",")[0].split(":")[1].split(";")[0];
  var ab = new ArrayBuffer(byteString.length);
  var ia = new Uint8Array(ab);

  for (var i = 0; i < byteString.length; i++) {
    ia[i] = byteString.charCodeAt(i);
  }

  return new Blob([ab], { type: mimeString });
}

/**
 * File 객체를 ImageData 객체 형태로 변환합니다. (width, height, data 포함)
 * @param {File} file - 앨범에서 선택된 파일 객체
 * @returns {Promise<ImageData>} 캔버스 픽셀 데이터 객체
 */
export function handleFileToImageData(file) {
  return new Promise((resolve, reject) => {
    if (!file) {
      reject(new Error("파일이 존재하지 않습니다."));
      return;
    }

    const reader = new FileReader();
    reader.onload = (e) => {
      const img = new Image();
      img.onload = () => {
        // 1. 임시 캔버스 생성 및 크기 설정
        const canvas = document.createElement("canvas");
        const ctx = canvas.getContext("2d");
        canvas.width = img.width;
        canvas.height = img.height;

        // 2. 이미지를 캔버스에 그리기
        ctx.drawImage(img, 0, 0);

        // 3. 픽셀 데이터(ImageData) 추출
        // 결과값: { data: Uint8ClampedArray, width: n, height: m, colorSpace: "srgb" ... }
        const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);

        resolve(imageData);
      };
      img.onerror = () => reject(new Error("이미지 로드 실패"));
      img.src = e.target.result;
    };
    reader.onerror = () => reject(new Error("파일 읽기 실패"));
    reader.readAsDataURL(file);
  });
}

export function _generateUUIDFallback() {
  return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function (c) {
    const r = (Math.random() * 16) | 0;
    const v = c === "x" ? r : (r & 0x3) | 0x8;
    return v.toString(16);
  });
}

export function getTodayUserID() {
  const now = new Date();

  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, "0");
  const day = String(now.getDate()).padStart(2, "0");

  return `data_${year}${month}${day}`;
}

export function getCurrentTime() {
  const now = new Date();
  const hours = String(now.getHours()).padStart(2, "0");
  const minutes = String(now.getMinutes()).padStart(2, "0");
  const seconds = String(now.getSeconds()).padStart(2, "0");
  const ms = String(now.getMilliseconds()).padStart(3, "0");
  return `${hours}:${minutes}:${seconds}.${ms}`;
}

export function parseTimeToMs(timeStr) {
  // "HH:MM:SS.mmm" → ms 단위
  const [hms, ms = "0"] = timeStr.split(".");
  const [hours, minutes, seconds] = hms.split(":").map(Number);
  return hours * 3600 * 1000 + minutes * 60 * 1000 + seconds * 1000 + Number(ms);
}

export function showImageData(imageData, containerId = "previewContainer") {
  const canvas = document.createElement("canvas");
  canvas.width = imageData.width;
  canvas.height = imageData.height;
  const ctx = canvas.getContext("2d");
  ctx.putImageData(imageData, 0, 0);

  // 표시할 컨테이너에 추가
  let container = document.getElementById(containerId);
  if (!container) {
    container = document.createElement("div");
    container.id = containerId;
    document.body.appendChild(container);
  }
  container.appendChild(canvas);
}

export function downloadImageData(imageData, filename = "crop.png") {
  // 캔버스에 그리기
  const canvas = document.createElement("canvas");
  canvas.width = imageData.width;
  canvas.height = imageData.height;
  const ctx = canvas.getContext("2d");
  ctx.putImageData(imageData, 0, 0);

  // Blob 생성 후 다운로드
  canvas.toBlob((blob) => {
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = filename;
    link.click();
    URL.revokeObjectURL(link.href); // 메모리 해제
  }, "image/png");
}

export function base64ToImageData(dataUrl) {
  return new Promise((resolve, reject) => {
    const img = new Image();
    img.onload = () => {
      const canvas = document.createElement("canvas");
      canvas.width = img.width;
      canvas.height = img.height;
      const ctx = canvas.getContext("2d");
      ctx.drawImage(img, 0, 0);
      const imageData = ctx.getImageData(0, 0, img.width, img.height);
      resolve(imageData);
    };
    img.onerror = (err) => reject(err);
    img.src = dataUrl;
  });
}
