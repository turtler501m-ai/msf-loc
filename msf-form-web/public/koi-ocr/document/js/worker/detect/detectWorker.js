importScripts("../../processor/detect/detectProcessor.js?v=202511191038");

let detector = null;
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

async function initializeModel(modelType) {
  if (!detector) {
    detector = new detectProcessor();
  }

  try {
    // 1. WASM 모듈 초기화
    const initResult = await detector.initModule("../../detect/", modelType);

    if (!initResult || initResult.success !== 1) {
      postMessage({
        type: "initFailed",
        error: initResult?.error || "initModule failed",
        modelType,
      });
      return;
    }

    // 2. 모델 로드
    let loadModelResult;
    if (modelType === "focusCheck") {
      loadModelResult = await detector.loadLapl(modelType);
    } else {
      loadModelResult = await detector.loadModel(modelType);
    }

    if (loadModelResult && loadModelResult.success === 1) {
      isModelLoaded = true;
      postMessage({ type: "initComplete", modelType });
    } else {
      postMessage({
        type: "initFailed",
        error: loadModelResult?.error || "loadModel failed",
        modelType,
      });
    }
  } catch (error) {
    // 예상치 못한 JS / Worker 크래시 계열
    console.error("initializeModel exception:", error);

    postMessage({
      type: "initFailed",
      error: error?.message || String(error),
      modelType,
    });
  }
}

function calculateIOU(points1, points2) {
  // 사각형의 경계를 구하기 위해 각 사각형의 최소/최대 x, y 좌표를 계산
  const rect1 = {
    xMin: Math.min(points1[0].x, points1[1].x, points1[2].x, points1[3].x),
    xMax: Math.max(points1[0].x, points1[1].x, points1[2].x, points1[3].x),
    yMin: Math.min(points1[0].y, points1[1].y, points1[2].y, points1[3].y),
    yMax: Math.max(points1[0].y, points1[1].y, points1[2].y, points1[3].y),
  };

  const rect2 = {
    xMin: Math.min(points2[0].x, points2[1].x, points2[2].x, points2[3].x),
    xMax: Math.max(points2[0].x, points2[1].x, points2[2].x, points2[3].x),
    yMin: Math.min(points2[0].y, points2[1].y, points2[2].y, points2[3].y),
    yMax: Math.max(points2[0].y, points2[1].y, points2[2].y, points2[3].y),
  };

  // 두 사각형의 교차 영역 계산
  const intersectXMin = Math.max(rect1.xMin, rect2.xMin);
  const intersectXMax = Math.min(rect1.xMax, rect2.xMax);
  const intersectYMin = Math.max(rect1.yMin, rect2.yMin);
  const intersectYMax = Math.min(rect1.yMax, rect2.yMax);

  // 교차 영역의 넓이 계산
  const intersectWidth = Math.max(0, intersectXMax - intersectXMin);
  const intersectHeight = Math.max(0, intersectYMax - intersectYMin);
  const intersectArea = intersectWidth * intersectHeight;

  // 각 사각형의 넓이 계산
  const area1 = (rect1.xMax - rect1.xMin) * (rect1.yMax - rect1.yMin);
  const area2 = (rect2.xMax - rect2.xMin) * (rect2.yMax - rect2.yMin);

  // IOU 계산
  const iou = intersectArea / (area1 + area2 - intersectArea);

  return iou;
}

function calculateIDcardIoU(previousPoints, currentPoints) {
  // previousPoints와 currentPoints는 각각 {x, y, w, h} 형태의 객체로 가정합니다.

  // 두 사각형의 좌측 상단과 우측 하단의 좌표를 계산
  const x1 = previousPoints.x;
  const y1 = previousPoints.y;
  const w1 = previousPoints.w;
  const h1 = previousPoints.h;
  const x2 = currentPoints.x;
  const y2 = currentPoints.y;
  const w2 = currentPoints.w;
  const h2 = currentPoints.h;

  // 두 사각형의 겹치는 영역 (intersection)의 좌측 상단과 우측 하단 좌표 계산
  const ix1 = Math.max(x1, x2); // 겹치는 영역의 좌측 상단 X
  const iy1 = Math.max(y1, y2); // 겹치는 영역의 좌측 상단 Y
  const ix2 = Math.min(x1 + w1, x2 + w2); // 겹치는 영역의 우측 하단 X
  const iy2 = Math.min(y1 + h1, y2 + h2); // 겹치는 영역의 우측 하단 Y

  // 겹치는 영역의 넓이가 음수인 경우(겹치지 않는 경우) 0으로 처리
  const intersectionArea = Math.max(0, ix2 - ix1) * Math.max(0, iy2 - iy1);

  // 두 사각형의 면적 (union)을 계산
  const area1 = w1 * h1; // 첫 번째 사각형의 면적
  const area2 = w2 * h2; // 두 번째 사각형의 면적
  const unionArea = area1 + area2 - intersectionArea; // union 면적

  // IoU 계산
  const iou = intersectionArea / unionArea;

  return iou;
}

function handleDetection(ocrType, imageData, width, height, validCheckDt, lapThreshold) {
  switch (ocrType) {
    case 10:
      return detector.giroDetect(imageData, width, height); // 지로
    case 1:
    case 2:
    case 11:
    case 19:
    case 23:
    case 29:
      return detector.detect(imageData, width, height, ocrType, lapThreshold); // 신분증 인식, 사본판별
    case 3:
      return detector.cardDetect(imageData, width, height); // 신용카드
    case 7:
    case 14:
    case 15:
    case 16:
    case 24:
    case 27:
      let result = detector.crop(imageData, width, height, validCheckDt);
      if (validCheckDt) detectCount = 2;
      return result;
    default:
      return -1; // 알 수 없는 타입
  }
}

// 탐지 결과 업데이트
function updateDetectStatus(resultCode, currentPoints, ocrType) {
  if (resultCode == 0) {
    // currentPoints가 객체일 경우 그대로 할당
    if (currentPoints && typeof currentPoints === "object" && !Array.isArray(currentPoints)) {
      currentPoints = { ...currentPoints }; // 객체를 복사
    }

    // detectCount가 0일 때만 previousPoints에 currentPoints를 할당
    if (detectCount == 0) previousPoints = currentPoints ? { ...currentPoints } : null;

    detectCount++;
    // currentPoints가 falsy 값일 경우 빈 객체로 초기화
    currentPoints = currentPoints || {};
  } else {
    detectCount = 0;
    previousPoints = null;
    // currentPoints = null;
  }
}

function canvasX(clientX, guideAreaRect, imageData, radius = 0) {
  var bound = guideAreaRect;
  return Math.round(clientX * (bound.width / imageData.width) - radius);
}

function canvasY(clientY, guideAreaRect, imageData, radius = 0) {
  var bound = guideAreaRect;
  return Math.round(clientY * (bound.height / imageData.height) - radius);
}

function isDocumentClipped(points, guideAreaRect, imageData, marginRatio = 0.02) {
  // 화면 내 가이드 영역 크기
  const frameWidth = guideAreaRect.width;
  const frameHeight = guideAreaRect.height;

  // 경계 오차 범위 (2%)
  const marginX = frameWidth * marginRatio;
  const marginY = frameHeight * marginRatio;

  // 각 포인트를 canvas 기준 좌표로 변환 후 경계 판정
  return points.some((p, i) => {
    // 👇 기존 affine 표시와 동일한 상대좌표 변환
    const relativeX = canvasX(p.x, guideAreaRect, imageData, 0);
    const relativeY = canvasY(p.y, guideAreaRect, imageData, 0);

    const nearLeft = relativeX <= marginX;
    const nearTop = relativeY <= marginY;
    const nearRight = relativeX >= frameWidth - marginX;
    const nearBottom = relativeY >= frameHeight - marginY;

    const nearEdge = nearLeft || nearTop || nearRight || nearBottom;

    if (nearEdge) {
      console.warn(`⚠️ Point ${i + 1} (${p.x.toFixed(1)}, ${p.y.toFixed(1)}) → 상대좌표 (${relativeX.toFixed(1)}, ${relativeY.toFixed(1)}) near edge:`, { nearLeft, nearTop, nearRight, nearBottom });
    }

    return nearEdge;
  });
}

// 흔들림 및 초점 검사 처리
function handleShakeAndFocusCheck(detectionData) {
  const { ocrType, detectResultCode, imageData, width, height, focusCheck, failCount, currentPoints, guideArea } = detectionData;

  let passed = true;

  // 문서
  if (ocrType == 7 || ocrType == 14 || ocrType == 15 || ocrType == 16 || ocrType == 24 || ocrType == 27) {
    // === 1. IoU 체크 ===
    const iou = calculateIOU(previousPoints, currentPoints);

    const iouPassed = iou >= 0.75;

    // === 2. 라플라시안 포커스 체크 ===
    let focusPassed = true;
    if (focusCheck) {
      let threshold = 12;
      if (failCount >= 4) {
        threshold = 5;
      } else if (failCount >= 2) {
        threshold = 10;
      }

      focusPassed = detector.focusCheck(imageData, width, height, threshold);
    }

    // === 3. 화면 경계(잘림) 체크 ===
    const isClipped = isDocumentClipped(currentPoints, guideArea, imageData, 0.02);
    const clipPassed = !isClipped;

    // === 4. 통합 조건 ===
    passed = iouPassed && focusPassed && clipPassed;
    if (!passed) {
      if (detectResultCode?.resultJSON) {
        detectResultCode.resultJSON.resultCode = -10;
      }
      detectCount = 0;
    }

    postMessage({
      type: "detectResult",
      resultCode: detectResultCode,
      continuousSuccess: passed ? 1 : 0,
      points: passed ? currentPoints : undefined,
    });
  } else {
    // 문서 외 타입은 기존대로 처리
    postMessage({
      type: "detectResult",
      resultCode: detectResultCode,
      continuousSuccess: 1,
      points: currentPoints,
    });
  }

  // 초기화
  detectCount = 0;
  previousPoints = null;
  // currentPoints = null;
}

function handleValidation(detectionData) {
  if (detectionData.validCheckDt) {
    postMessage({ type: "detectResult", resultCode: detectionData.detectResultCode, continuousSuccess: 1 });
    detectCount = 0;
    previousPoints = null;
    currentPoints = null;
    detectionData.detectResultCode = -1;
    detectionData.validCheckDt = 0;
  } else {
    handleShakeAndFocusCheck(detectionData);
  }
}

// 라플라시안 값 설정(15 -> 12 -> 5)
function getLapThreshold(failCount) {
  if (failCount >= 4) return 5;
  if (failCount >= 2) return 12;
  return 15;
}

function extractDetectParams(data) {
  const { imageData, width, height, ocrType, useCapOcr, validCheckDt, focusCheck, recogTryLimit, currentCount, isIOS, startTime, guideArea } = data;
  return { imageData, width, height, ocrType, useCapOcr, validCheckDt, focusCheck, recogTryLimit, currentCount, isIOS, startTime, guideArea };
}

function runDetection({ ocrType, imageData, width, height, validCheckDt }) {
  const lapThreshold = getLapThreshold(failCount);
  return handleDetection(ocrType, imageData, width, height, validCheckDt, lapThreshold);
}

function manageFailCount(detectResultCode) {
  if (detectResultCode === -7) {
    failCount++;
  } else {
    failCount = 0;
  }
}

function processDetectionResult(ocrType, detectResultCode) {
  const multiPageOcrTypes = [7, 14, 15, 16, 24, 27];
  let resultCode = multiPageOcrTypes.includes(ocrType) ? detectResultCode.resultJSON.resultCode : detectResultCode;

  let currentPoints = null;
  if (multiPageOcrTypes.includes(ocrType)) {
    currentPoints = detectResultCode.resultJSON.points;
  } else if ([1, 2, 11, 19, 23, 29].includes(ocrType)) {
    currentPoints = idcard_points;
  }

  return { resultCode, currentPoints };
}

function buildDetectionData(params, detectResultCode, currentPoints) {
  return { ...params, detectResultCode, currentPoints };
}

function getRequiredPageCount(ocrType) {
  const twoPageTypes = new Set([7, 14, 15, 16, 24, 27]);
  return twoPageTypes.has(ocrType) ? 2 : 1;
}

self.onmessage = async (event) => {
  if (event.data.type == "init") {
    const { ocrType } = event.data;
    await initializeModel(ocrType);
  }

  if (event.data.type == "focusCheck") {
    await initializeModel("focusCheck");
  }

  if (event.data.type == "detect" && isModelLoaded) {
    const params = extractDetectParams(event.data);

    // 1. 탐지 실행 + 실패 카운터 관리
    detectResultCode = runDetection(params);
    manageFailCount(detectResultCode);

    // 2. 결과코드 & 포인트 처리
    const { resultCode, currentPoints } = processDetectionResult(params.ocrType, detectResultCode);

    // 3. UI 업데이트
    updateDetectStatus(resultCode, currentPoints, params.ocrType);

    // 4. 검증 데이터 준비
    const detectionData = buildDetectionData(params, detectResultCode, currentPoints);

    // 5. 페이지별 처리
    const requiredCount = getRequiredPageCount(params.ocrType);
    if (detectCount >= requiredCount) {
      handleValidation(detectionData);
    } else {
      postMessage({ type: "detectResult", resultCode: detectResultCode, continuousSuccess: 0 });
    }
  }

  if (event.data.type == "giroType" && isModelLoaded) {
    const { imageData, width, height } = event.data;
    giroType = detector.giroType(imageData, width, height);
    postMessage({ type: "giroType", resultCode: giroType });
  }

  if (event.data.type == "unload") {
    detector.unload();
    isModelLoaded = false;
    postMessage({ type: "unloadComplete" });
  }
};
