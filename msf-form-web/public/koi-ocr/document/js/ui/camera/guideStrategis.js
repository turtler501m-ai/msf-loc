// guides/GuideStrategies.js
function createGuideIDcard(context) {
  if (context._guideArea.querySelector(".innerGuideBox")) return;
  const typeClassMap = {
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
    17: "ingam",
    19: "idcard",
    21: "idcard",
    22: "idcard",
    23: "idcardfull",
    24: "biridge",
    25: "passportWasm",
    26: "dualScan",
    27: "mds",
    28: "serialNumber",
    29: "regisback",
    30: "usim",
  };

  const className = typeClassMap[context._ocrType] || "idcard";

  context._innerGuideBox = document.createElement("div");
  context._innerGuideBox.className = "innerGuideBox public";
  context._guideArea.appendChild(context._innerGuideBox);

  context._videoWrapper.classList.add(`video-wrapper-${className}`);
}

const REGIS_CLASS_MAP = {
  0: "진본",
  1: "디지털",
  2: "종이",
  3: "필름",
  4: "포토카드",
};

function createIngam(context) {
  const className = "ingam";
  context._innerGuideBox = document.createElement("div");
  context._innerGuideBox.className = "innerGuideBox ingam";
  context._guideArea.appendChild(context._innerGuideBox);

  context._videoWrapper.classList.add(`video-wrapper-${className}`);
}

function createScan(context) {
  const className = "scan";
  context._scanGuideBox = document.createElement("div");
  context._scanGuideBox.className = "guideBox scan";
  context._innerGuideBox.appendChild(context._scanGuideBox);

  context._videoWrapper.classList.add(`video-wrapper-${className}`);
}

function createQR(context) {
  const className = "QR";
  context._innerGuideBox = document.createElement("div");
  context._innerGuideBox.className = "innerGuideBox QR";
  context._guideArea.appendChild(context._innerGuideBox);

  context._videoWrapper.classList.add(`video-wrapper-${className}`);
}

function createAccount(context) {
  const className = "account";
  context._innerGuideBox = document.createElement("div");
  context._innerGuideBox.className = "innerGuideBox account";
  context._guideArea.appendChild(context._innerGuideBox);

  context._videoWrapper.classList.add(`video-wrapper-${className}`);
}

function createSerial(context) {
  const className = "serial";
  context._innerGuideBox = document.createElement("div");
  context._innerGuideBox.className = "innerGuideBox serial";
  context._guideArea.appendChild(context._innerGuideBox);

  context._videoWrapper.classList.add(`video-wrapper-${className}`);
}

function createRegisBack(context) {
  const className = "regisback";

  context._innerGuideBox = document.createElement("div");
  context._innerGuideBox.className = `innerGuideBox ${className}`;

  context._guideArea.appendChild(context._innerGuideBox);
  context._videoWrapper.classList.add(`video-wrapper-${className}`);

  const sampleBox = document.createElement("div");
  sampleBox.id = "regis-sampleBox";

  // [수정] <img> 태그 대신 <div> 태그로 변경합니다.
  const imgBg = document.createElement("div");
  imgBg.id = "regis-bgImage"; // CSS 지정을 위해 명확한 ID 부여

  const guideBox = document.createElement("div");
  guideBox.id = "regis-guideBox";

  const regisType = document.createElement("div");
  regisType.id = "regisType";
  regisType.innerHTML = "일련번호";

  const regisNumber = document.createElement("div");
  regisNumber.id = "regisNumber";
  regisNumber.innerHTML = "1-012-345-6789";

  guideBox.appendChild(regisType);
  guideBox.appendChild(regisNumber);

  // [수정] 생성한 div 요소를 조립합니다.
  sampleBox.appendChild(imgBg);
  sampleBox.appendChild(guideBox);

  context._innerGuideBox.appendChild(sampleBox);
}

function createUsim(context) {
  const className = "usim";
  context._innerGuideBox = document.createElement("div");
  context._innerGuideBox.className = "innerGuideBox usim";
  context._guideArea.appendChild(context._innerGuideBox);

  context._videoWrapper.classList.add(`video-wrapper-${className}`);
}

function createCornerHighlight(parentElement, character, vertical, horizontal) {
  const cornerHighlight = document.createElement("div");
  cornerHighlight.classList.add("corner-highlight", vertical, horizontal);
  cornerHighlight.textContent = character;
  parentElement.appendChild(cornerHighlight);
}

export const GuideStrategies = {
  idcard(context) {
    context._guideArea.style.zIndex = "10006";
    createGuideIDcard(context);
  },
  idcardfull(context) {
    context._guideArea.style.zIndex = "10006";
    createGuideIDcard(context);
  },
  creditcard(context) {
    context._guideArea.style.zIndex = "10006";
    createGuideIDcard(context);

    context._orientationChangeButton = document.createElement("button");
    context._orientationChangeButton.id = "orientationchange";
    context._orientationChangeButton.className = "orientation-button";

    context._innerGuideBox.appendChild(context._orientationChangeButton);

    context._orientationChangeButton.addEventListener("click", () => {
      context._isReversed = !context._isReversed;
      context._resetUI(); // 클래스 내 메서드 호출
    });
  },
  crop(context) {
    context._guideArea.style.zIndex = "10006";
    context._guideArea.classList.add("default-guide");
    context._guideUI.style.display = "none";

    context._innerGuideBox = document.createElement("div");
    context._innerGuideBox.className = "innerGuideBox dscan-guide-box";
    context._guideArea.appendChild(context._innerGuideBox);
    createScan(context);
  },
  docs(context) {
    context._guideArea.style.zIndex = "10006";
    context._guideArea.classList.add("default-guide");
    context._guideUI.style.display = "none";

    context._innerGuideBox = document.createElement("div");
    context._innerGuideBox.className = "innerGuideBox dscan-guide-box";
    context._guideArea.appendChild(context._innerGuideBox);
    createScan(context);
  },
  fullpage(context) {
    context._guideArea.style.zIndex = "10006";
    context._guideArea.classList.add("default-guide");
    context._guideUI.style.display = "none";

    context._innerGuideBox = document.createElement("div");
    context._innerGuideBox.className = "innerGuideBox dscan-guide-box";
    context._guideArea.appendChild(context._innerGuideBox);
    createScan(context);
  },
  face(context) {
    context._guideArea.style.zIndex = "10006";
  },
  ingam(context) {
    context._guideArea.style.zIndex = "10006";
    createIngam(context);
  },
  qrcode(context) {
    context._guideArea.classList.add("qrcode-guide");
    createQR(context);
  },
  dualScan(context) {
    context._guideArea.classList.add("dual-guide");
    // createQR(context);
    // context._guideArea.style.border = "3px solid yellow";
    context._guideArea.style.zIndex = "10006";
  },
  giro(context) {
    // context._guideArea.style.border = "3px solid yellow";
    context._guideArea.classList.add("yellow");
    context._guideArea.style.zIndex = "10006";
  },
  account(context) {
    // context._guideArea.style.border = "3px solid yellow";
    // context._guideArea.classList.add("yellow");
    context._guideArea.style.zIndex = "10006";
    createAccount(context);
  },
  giroEpn(context) {
    const topPadding = document.createElement("div");
    topPadding.className = "giroepn-top-padding";

    const guideBox = document.createElement("div");
    guideBox.className = "giroepn-guide-box";

    const outguideBox = document.createElement("div");
    outguideBox.className = "giroepn-out-guide-box";

    const bottomPadding = document.createElement("div");
    bottomPadding.className = "giroepn-bottom-padding";

    context._guideArea.appendChild(topPadding);
    context._guideArea.appendChild(bottomPadding);
    context._guideArea.appendChild(outguideBox);
    context._guideArea.appendChild(guideBox);
  },
  passport(context) {
    const uncoveredAreaTop = document.createElement("div");
    const uncoveredAreaBottom = document.createElement("div");
    const mrzFace = document.createElement("div");
    const dashedLine = document.createElement("div"); // 점선용

    uncoveredAreaTop.classList.add("uncovered-area", "uncovered-area-top");
    uncoveredAreaBottom.classList.add("uncovered-area", "uncovered-area-bottom");
    mrzFace.classList.add("mrz-face");
    dashedLine.classList.add("dashed-line");

    context._letterboxTop.style.backgroundColor = "rgba(255, 255, 255, 0)";

    context._uncoveredAreaTop = uncoveredAreaTop;
    context._uncoveredAreaBottom = uncoveredAreaBottom;

    context._guideArea.appendChild(uncoveredAreaTop);
    context._guideArea.appendChild(uncoveredAreaBottom);
    uncoveredAreaTop.appendChild(mrzFace);
    uncoveredAreaBottom.appendChild(dashedLine);
  },
  passportWasm(context) {
    const uncoveredAreaTop = document.createElement("div");
    const uncoveredAreaBottom = document.createElement("div");
    const mrzFace = document.createElement("div");
    const dashedLine = document.createElement("div"); // 점선용

    uncoveredAreaTop.classList.add("uncovered-area", "uncovered-area-top");
    uncoveredAreaBottom.classList.add("uncovered-area", "uncovered-area-bottom");
    mrzFace.classList.add("mrz-face");
    dashedLine.classList.add("dashed-line");

    context._letterboxTop.style.backgroundColor = "rgba(255, 255, 255, 0)";

    context._uncoveredAreaTop = uncoveredAreaTop;
    context._uncoveredAreaBottom = uncoveredAreaBottom;

    context._guideArea.appendChild(uncoveredAreaTop);
    context._guideArea.appendChild(uncoveredAreaBottom);
    uncoveredAreaTop.appendChild(mrzFace);
    uncoveredAreaBottom.appendChild(dashedLine);
    context._videoWrapper.classList.add(`video-wrapper-passport`);
  },
  dataset(context) {
    context._guideArea.style.zIndex = "10006";
    createGuideIDcard(context);
    const regisGuide = document.createElement("div");
    regisGuide.id = "regis-title";

    // regisClassType에 맞는 단어 찾아서 문구 생성
    const regisTypeName = REGIS_CLASS_MAP[context._regisClassType] || "";
    regisGuide.innerHTML = `<span style="color: #b22323;">${regisTypeName}</span>&nbsp;촬영중입니다.`;

    context._letterboxBottom.appendChild(regisGuide);
  },
  dscan(context) {
    context._guideArea.classList.add("default-guide");
    context._guideUI.style.display = "none";

    context._innerGuideBox = document.createElement("div");
    context._innerGuideBox.className = "innerGuideBox dscan-guide-box";
    context._guideArea.appendChild(context._innerGuideBox);

    context._scanButton = document.createElement("button");
    context._scanButton.id = "scan-button";
    context._scanButton.classList.add("btn3");

    const innerCircle = document.createElement("div");
    innerCircle.classList.add("inner-circle");
    context._scanButton.appendChild(innerCircle);
    // context._letterboxBottom.appendChild(context._scanButton);

    context._previewModal = document.createElement("div");
    context._previewModal.id = "previewModal";
    context._previewModal.classList.add("preview-modal");

    const modalContent = document.createElement("div");
    modalContent.classList.add("modal-content");

    const img = document.createElement("img");
    img.id = "previewImage";
    img.alt = "미리보기";
    img.src = "";

    const previewModifyBtn = document.createElement("button");
    previewModifyBtn.id = "previewModifyBtn";
    previewModifyBtn.textContent = "수정";

    modalContent.appendChild(img);
    // modalContent.appendChild(previewModifyBtn);

    const badge = document.createElement("div");
    badge.id = "imageCountBadge";
    badge.textContent = "0";
    modalContent.appendChild(badge);

    context._previewModal.appendChild(modalContent);
    context._innerGuideBox.appendChild(context._previewModal);

    context._previewModal.addEventListener("click", () => {
      context.showAllImagesModal();
    });

    context._scanButton.addEventListener("click", () => {
      context._manualCaptureRequested = true;
    });

    context._pageIndex = document.createElement("div");
    context._pageIndex.id = "page-index";

    context._pageCount = document.createElement("div");
    context._pageCount.id = "page-count";

    context._pageIndex.appendChild(context._pageCount);
    context._letterboxBottom.appendChild(context._pageIndex);
    createScan(context);
  },
  mds(context) {
    context._guideArea.classList.add("default-guide");
    context._guideUI.style.display = "none";

    context._innerGuideBox = document.createElement("div");
    context._innerGuideBox.className = "innerGuideBox dscan-guide-box";
    context._guideArea.appendChild(context._innerGuideBox);

    context._scanButton = document.createElement("button");
    context._scanButton.id = "scan-button";
    context._scanButton.classList.add("btn3");

    const innerCircle = document.createElement("div");
    innerCircle.classList.add("inner-circle");
    context._scanButton.appendChild(innerCircle);
    // context._letterboxBottom.appendChild(context._scanButton);

    context._previewModal = document.createElement("div");
    context._previewModal.id = "previewModal";
    context._previewModal.classList.add("preview-modal");

    const modalContent = document.createElement("div");
    modalContent.classList.add("modal-content");

    const img = document.createElement("img");
    img.id = "previewImage";
    img.alt = "미리보기";
    img.src = "";

    const previewModifyBtn = document.createElement("button");
    previewModifyBtn.id = "previewModifyBtn";
    previewModifyBtn.textContent = "수정";

    modalContent.appendChild(img);
    // modalContent.appendChild(previewModifyBtn);

    const badge = document.createElement("div");
    badge.id = "imageCountBadge";
    badge.textContent = "0";
    modalContent.appendChild(badge);

    context._previewModal.appendChild(modalContent);
    context._innerGuideBox.appendChild(context._previewModal);

    context._previewModal.addEventListener("click", () => {
      context.showAllImagesModal();
    });

    context._scanButton.addEventListener("click", () => {
      context._manualCaptureRequested = true;
    });

    context._pageIndex = document.createElement("div");
    context._pageIndex.id = "page-index";

    context._pageCount = document.createElement("div");
    context._pageCount.id = "page-count";

    context._pageIndex.appendChild(context._pageCount);
    context._letterboxBottom.appendChild(context._pageIndex);

    const qrDiv = document.createElement("div");
    qrDiv.className = "qr-result-display";
    context._letterboxBottom.appendChild(qrDiv);

    //문서 내부 레이아웃 css
    createScan(context);
  },
  serialNumber(context) {
    context._guideArea.style.zIndex = "10006";
    createSerial(context);
  },
  regisback(context) {
    context._guideArea.style.zIndex = "10006";
    createRegisBack(context);
  },
  usim(context) {
    context._guideArea.style.zIndex = "10006";
    createUsim(context);
  }
};
