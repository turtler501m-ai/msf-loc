import { loadConfig } from "../../lib/configLoader.js";
import { loadUtils } from "../../lib/utilLoader.js";

// Promise 형태로 외부 공개
export const configPromise = loadConfig();
export const utilsPromise = loadUtils();

// 내부 캐시용 변수
let config = null;
let Utils = null;

// 자동 초기화
const ready = Promise.all([loadConfig(), loadUtils()]).then(([cfg, utils]) => {
  config = cfg;
  Utils = utils;
});

export async function textElements() {
  await ready;
  // 일반 텍스트/HTML 설정
  const textMappings = {
    "start-message": config.texts.startMessage,
    "consent-title": config.texts.consentTitle,
    "consent-description": config.texts.consentDescription,
    "consent-notice": config.texts.consentNotice,
    "consent-warning": config.texts.consentWarning,
    start_btn_text: config.texts.startButtonText,
    submit_btn_text: config.texts.submitButtonText,
    retry_btn_text: config.texts.retryButtonText,
    back_btn_text: config.texts.backButtonText,
    "result-message": config.texts.resultScreenReaderMessage,
    "upload-view-title": config.texts.uploadViewTitleDefault,
  };

  for (const id in textMappings) {
    const element = document.getElementById(id);
    if (element) {
      // HTML 태그가 포함될 수 있으므로 innerHTML 사용
      element.innerHTML = textMappings[id];
    } else {
      // Utils.logWarning(`Element with ID '${id}' not found for text population.`);
    }
  }

  // 리스트 항목(ul) 동적 생성
  const detailsListElement = document.getElementById("consent-details-list");
  if (detailsListElement) {
    detailsListElement.innerHTML = ""; // 기존 항목 초기화
    config.texts.consentDetailsList.forEach((itemText) => {
      const listItem = document.createElement("li");
      listItem.innerHTML = itemText; // 리스트 항목도 HTML 포함 가능
      detailsListElement.appendChild(listItem);
    });
  } else {
    Utils.logWarning("Element with ID 'consent-details-list' not found.");
  }
}

// KoiOcr 옵션 생성
export function createKoiOcrOptions(options, baseOptions = {}, useCapOcr) {
  const defaultOptions = options;

  return {
    ...defaultOptions,
    cameraOptions: {
      ...defaultOptions.cameraOptions,
      ...baseOptions.cameraOptions,
      useCapOcr: useCapOcr ?? baseOptions.cameraOptions?.useCapOcr ?? defaultOptions.cameraOptions.useCapOcr,
    },
    ...Object.fromEntries(Object.entries(baseOptions).filter(([key]) => key !== "cameraOptions")),
  };
}

export function resolveUseCapOcr(ocrType, useCapOcrParam) {
  const typeSpecificOptions = config.koiOptions[ocrType] || {};
  const defaultCameraOptions = config.koiOptions.default.cameraOptions;

  return typeSpecificOptions.useCapOcrOverride ?? useCapOcrParam ?? typeSpecificOptions.cameraOptions?.useCapOcr ?? defaultCameraOptions.useCapOcr;
}

// 버튼 생성 함수
export function createButton({ id, imgSrc, label, className = "btn", detail }) {
  const button = document.createElement("button");
  button.id = id;
  button.className = className;

  // 1. 이미지와 텍스트를 감쌀 상위 컨테이너 생성
  const categoryTitle = document.createElement("div");
  categoryTitle.className = "category-subtitle";

  // 2. 이미지 생성 및 컨테이너에 추가
  if (imgSrc) {
    const img = document.createElement("img");
    img.src = imgSrc;
    img.alt = label;
    categoryTitle.appendChild(img);
  }

  // 3. 텍스트 생성 및 컨테이너에 추가
  const overlayText = document.createElement("span");
  overlayText.className = "overlay-text";
  overlayText.innerHTML = label || getButtonText(id);
  categoryTitle.appendChild(overlayText);

  const detailText = document.createElement("span");
  detailText.className = "detail-text";
  detailText.innerHTML = detail || getButtonText(id);

  const openBadge = document.createElement("div");
  openBadge.className = "open-badge";
  openBadge.classList.add(id);
  openBadge.innerHTML = "열기";

  // 4. 최종적으로 컨테이너를 버튼에 추가
  button.appendChild(categoryTitle);
  button.appendChild(detailText);
  button.appendChild(openBadge);

  return button;
}

function getButtonText(id) {
  const texts = {
    idcard: "신분증",
    idcls: "사본판별",
    card: "신용카드",
    giro: "지로",
    crops: "문서",
    account: "계좌번호",
    giroEPN: "전자납부번호",
    passport: "여권",
    dataset: "데이터셋",
    ingam: "인감대사",
    face_regist: "안면인식",
    idcard_accessMode: "신분증<br>(웹접근성)",
    idfake_accessMode: "사본판별<br>(웹접근성)",
    dscan: "다중문서스캔",
    idcardfull: "신분증 통합",
    fullpage: "전문인식",
    docs: "정형문서",
    qrcode: "QR송금",
  };
  return texts[id] || "";
}

export function clickButtonByOcrMethod(ocrMethod) {
  let buttonId;

  switch (ocrMethod) {
    case "0001":
    case "0002":
      buttonId = "idcard";
      break;
    case "0009":
      buttonId = "card";
      break;
    case "0013":
      buttonId = "ingam";
      break;
    case "0016":
      buttonId = "idcls";
      break;
    case "0017":
      buttonId = "giro";
      break;
    case "0018":
      buttonId = "docs";
      break;
    case "0019":
      buttonId = "fullpage";
      break;
    case "0020":
      buttonId = "giroEPN";
      break;
    case "0021":
      buttonId = "account";
      break;
    case "0022":
      buttonId = "docs";
      break;
    case "0025":
      buttonId = "idfake";
      break;
    case "0026":
      buttonId = "passport";
      break;
    case "0014":
    case "0015":
      buttonId = "face_regist";
      break;
    case "0028":
      buttonId = "alldocs";
      break;
    case "0032":
      buttonId = "idcardfull";
      break;
    case "0033":
      buttonId = "biridge";
      break;
    case "0034":
      buttonId = "dscan";
      break;
    case "0036":
      buttonId = "dataset";
      break;
    case "0035":
      buttonId = "qrcode";
      break;
    case "0037":
      buttonId = "dualScan";
      break;
    case "0038":
      buttonId = "passportWasm";
      break;
    case "0039":
    case "0040":
      buttonId = "mds";
      break;
    default:
      Utils.logError(`No button ID configured for ocrMethod: ${ocrMethod}`);
      return; // 버튼 ID가 설정되지 않은 ocrMethod에 대해서는 실행하지 않음
  }

  const button = document.querySelector(`#${buttonId}`);
  if (button) {
    button.click();
  } else {
    Utils.logError(`${buttonId} not found.`);
  }
}

// 개별 이벤트 핸들러
export function handleStartButton(ocrType, firstButton) {
  changeContentViewStack("start", ocrType);
  const buttons = document.querySelectorAll(".btn");

  if (buttons.length == 1) {
    buttons[0].click();
  } else {
    toggleStartButton(buttons);
  }
  if (firstButton) {
    setTimeout(() => {
      firstButton.setAttribute("aria-live", "assertive");
      firstButton.setAttribute("tabindex", "0");
      firstButton.focus();
      firstButton.style.outline = "none";
    }, 10000); // 100ms 후 실행 (필요에 따라 조정 가능)
  }
}

export async function getImageDataFromBase64(imgElement) {
  return new Promise((resolve) => {
    const canvas = document.createElement("canvas");
    const ctx = canvas.getContext("2d");

    const draw = () => {
      canvas.width = imgElement.naturalWidth;
      canvas.height = imgElement.naturalHeight;
      ctx.drawImage(imgElement, 0, 0);
      const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
      // document.querySelector("#srcFile-upload").src = "";
      // document.querySelector("#srcFile-upload").remove();
      resolve(imageData);
    };

    // 이미 로드된 이미지인지 확인
    if (imgElement.complete && imgElement.naturalWidth !== 0) {
      draw();
    } else {
      imgElement.onload = draw;
    }
  });
}

export function handleBackButton(ocrType) {
  window.scrollTo({ top: 0, behavior: "smooth" });
  changeContentViewStack("intro", ocrType);
  const buttons = document.querySelectorAll(".btn");

  resetBackButtonUI(buttons);
}

export function handleMainBack(ocrType) {
  window.close();
  window.scrollTo({ top: 0, behavior: "smooth" });
  changeContentViewStack("intro", ocrType);
  const buttons = document.querySelectorAll(".btn");

  resetBackButtonUI(buttons);
}

export function handleFileInput(event) {
  const fileInput = event.target;
  const file = fileInput.files[0];
  const imageUploadDiv = document.getElementById("imageUpload");

  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      // 아이콘과 텍스트 숨김
      const icon = imageUploadDiv.querySelector(".icon");
      const text = imageUploadDiv.querySelector(".text");
      if (icon) icon.style.display = "none";
      if (text) text.style.display = "none";

      // 새 이미지 추가
      const imgElement = document.createElement("img");
      imgElement.id = "srcFile-upload";
      imgElement.src = e.target.result;
      imgElement.alt = file.name;
      imgElement.style.width = "100%";
      imgElement.style.height = "100%";

      imageUploadDiv.appendChild(imgElement);

      Utils.showElements("#submitBtn");
    };
    reader.readAsDataURL(file);
  }

  fileInput.value = ""; // 같은 파일명 재업로드 허용
}

export function toggleStartButton(buttons) {
  buttons.forEach((button) => (button.style.display = "block"));
}


export function resetBackButtonUI(buttons) {
  const startBtn = document.querySelector("#start_btn");
  const btnArea = document.querySelector(".btn-area");

  startBtn.style.display = "block";
  btnArea.style.alignItems = "";
  btnArea.style.height = "";

  buttons.forEach((button) => (button.style.display = "flex"));
}


/**
 * 에러 섹션 표시 함수
 * @param {string} message - 사용자에게 보여줄 에러 메시지
 */
export const showResultSectionError = (message = "처리에 실패했습니다. \n다시 시도해주세요.") => {
  const sectionList = document.querySelectorAll(".contents-section");
  const resultSection = document.querySelector(".contents-section.result-section");

  if (!resultSection) {
    console.error("result-section not found");
    return;
  }

  // 1. 모든 섹션 숨김 및 결과 섹션 활성화
  sectionList.forEach((s) => (s.style.display = "none"));
  resultSection.style.display = "flex";

  // 2. 컨테이너 제어
  const successContainer = resultSection.querySelector(".success-container");
  const btnArea = resultSection.querySelector(".btn-area");

  if (successContainer) successContainer.style.display = "none";

  // 4. 재시도 버튼 강조 (필요 시)
  if (btnArea) {
    btnArea.style.display = "flex";
    const retryBtn = btnArea.querySelector(".btn-retry");
    if (retryBtn) retryBtn.focus(); // 키보드 접근성 고려
  }
};

export const goToErrorPage = (message) => {
  const encodedMsg = encodeURIComponent(message);
  window.location.href = `error.html?msg=${encodedMsg}`;
};

export const changeContentViewStack = (sectionName, ocrType, useCapOcr) => {
  const sectionList = document.querySelectorAll(".contents-section");
  const Section = document.querySelector(`.${sectionName}-section`);
  const imageUploadDiv = document.getElementById("imageUpload");
  const imgElement = document.querySelector("#srcFile-upload");
  const koiLogo = document.querySelector(".koi-logo");
  const submitBtn = document.getElementById("submitBtn");
  const icon = imageUploadDiv?.querySelector(".icon");
  const text = imageUploadDiv?.querySelector(".text");
  const koiheader = document.querySelector("header");

  const successContainer = Section.querySelector(".success-container");

  for (let section of sectionList) {
    section.style.display = "none";
  }

  let displayType = "block";
  if (sectionName == "intro") {
    displayType = "flex";
    Section.style.display = displayType;
    const subElements = Section.querySelectorAll("div");
    subElements.forEach((element) => {
      element.style.display = "flex";
    });

    // dataset 버튼이 있는지 확인
    const buttonContainer = document.getElementById("buttonContainer");
    const datasetBtn = buttonContainer?.querySelector("#dataset");

    if (datasetBtn) {
      const introSection = document.querySelector(".contents-section.intro-section");
      if (introSection && !introSection.querySelector("#regisClassType")) {
        introSection.appendChild(createRegisClassTypeSelect());
      }
    }
  } else if (sectionName == "start") {
    displayType = "flex";
  } else if (sectionName == "result") {
    displayType = "flex";
    if (successContainer) successContainer.style.display = "flex";
    if (koiheader) koiheader.style.display = "block";
    // 기타 처리
  } else if (sectionName == "camera") {
    if (koiLogo) koiLogo.style.display = "none";
    if (koiheader) koiheader.style.display = "none";
  } else if (sectionName == "upload") {
    if (icon) icon.style.display = "";
    if (text) text.style.display = "";
    submitBtn.style.display = "none";
    if (imgElement) {
      imgElement.remove();
    }
  }

  // camera가 아닐 땐 logo 다시 보이게
  if (sectionName !== "camera" && koiheader) {
    koiheader.style.display = "block";
  }

  Section.style.display = displayType;
};

// regisClassType select 요소 생성 함수
export function createRegisClassTypeSelect() {
  const select = document.createElement("select");
  select.id = "regisClassType";
  select.innerHTML = `
    <option value="진본">진본</option>
    <option value="디지털">디지털</option>
    <option value="종이">종이</option>
    <option value="필름">필름</option>
    <option value="포토카드">포토카드</option>
  `;

  // 생성 시 바로 이벤트 바인딩
  select.addEventListener("change", updateRegisClass);

  return select;
}

export function updateRegisClass() {
  const selectRef = document.getElementById("regisClassType");
  if (!selectRef) return null; // 요소가 없으면 null 반환

  const mappedValue = config.regisClassMap[selectRef.value];
  return mappedValue;
}

export const ocrResultFormat = (ocrResult, recognitionTime, captureTime, resultTime) => {
  // Accessing resultJSON from data
  let resultItems = "";
  let firstImageSkipped = false;
  const formResult = ocrResult.resultJSON.formResult;
  if (formResult) {
    const fieldResults = ocrResult.resultJSON.formResult.fieldResults;
    const type = ocrResult.resultJSON.formResult.type;

    // fieldId로 삭제할 필드 지정
    const fieldIdsToExclude = ["607", "608", "612"];

    for (let i = 0; i < fieldResults.length; i++) {
      const fieldResult = fieldResults[i];
      let value = fieldResult.value;

      // type이 '00600'일 때 지정된 fieldId를 가진 필드 건너뛰기
      if (type === "00600" && fieldIdsToExclude.includes(fieldResult.fieldId)) {
        continue;
      }

      // value = value.replaceAll("<", "&lt;");
      if (typeof value == "string") {
        value = value.replaceAll("<", "&lt;");
      }
      // Check if the field is '카드번호' and if the value is numeric
      if (fieldResult.displayName == "카드번호" && /^\d+$/.test(value)) {
        // Formatting based on the length of the value
        if (value.length == 16) {
          // Splitting the value into groups of 4 digits
          value = value.replace(/(\d{4})(\d{4})(\d{4})(\d{4})/, "$1 $2 $3 $4");
        } else if (value.length == 15) {
          // Splitting the value into groups of 4, 6, and 5 digits
          value = value.replace(/(\d{4})(\d{6})(\d{5})/, "$1 $2 $3");
        }
      }

      if (type == "00190" || type == "00100" || type == "00110" || type == "00150" || type == "00140" || type == "00141" || type == "00142") {
        if (fieldResult.fieldId == "191") {
          if (type == "00150" && fieldResult.fieldId == "191") {
            fieldResults.splice(i, 1); // 해당 항목 제거
            continue; // 다음 loop로
          }
          if (typeof value == "boolean") {
            // value가 boolean인 경우를 먼저 처리
            value = value ? "진본" : "사본";
          } else if (typeof value == "string") {
            // value가 문자열 "true" 또는 "false"인 경우 숫자로 변환
            const numericValue = value == "True" || value == "true" ? 1 : value == "False" || value == "false" ? 0 : parseInt(value, 10);

            if (numericValue == 1) {
              value = "진본";
            } else if (numericValue == 0) {
              value = "사본";
            }
          }
        }
      } else if (type == "00960") {
        if (Utils.isBase64Image(value)) {
          if (!firstImageSkipped) {
            firstImageSkipped = true; // 첫 번째 이미지를 건너뜀
            continue; // 첫 번째 이미지는 무시하고 다음으로 넘어감
          }
          // 두 번째 이미지를 처리하고 테이블을 반환
          value = `<img src="${value}" alt="Base64 이미지" style="width: 90%; height: auto; padding-bottom: 100px;" />`;
          resultItems += `<tr>
                          <td>${value}</td>
                        </tr>`;

          return `<table class="result_content">
                  <tbody>
                    ${resultItems}
                  </tbody>
                </table>`;
        }
      } else if (type == "00800") {
        if (Utils.isBase64Image(value)) {
          value = `<img src="${value}" alt="Base64 이미지" style="width: 90%; height: auto; padding-bottom: 30px;" />`;
          resultItems += `<tr>
                          <td>${value}</td>
                        </tr>`;

          return `<table class="result_content">
                  <tbody>
                    ${resultItems}
                  </tbody>
                </table>`;
        }
      } else {
        // 다른 경우의 value 유지
        value = value;
      }

      resultItems += `<tr>
                              <td>${fieldResult.displayName}</td>
                              <td>${value}</td>
                          </tr>`;
    }
    if (captureTime) {
      //const timeFormatted = (recognitionTime / 1000).toFixed(2); // 초 단위로 변환

      const captureTimeMs = Utils.parseTimeToMs(captureTime);
      const resultTimeMs = Utils.parseTimeToMs(resultTime);

      const recognitionTimeMs = resultTimeMs - captureTimeMs; // ms 단위
      const recognitionTimeSec = (recognitionTimeMs / 1000).toFixed(3); // 초 단위

      resultItems += `
        			<tr>
                      <td>촬영 시각</td>
                      <td>${captureTime}초</td>
                    </tr>
        			<tr>
                      <td>최종 응답 시각</td>
                      <td>${resultTime}초</td>
                    </tr>
    				<tr>
                      <td>총 소요 시간</td>
    				  <td>${recognitionTimeSec}초</td>
                    </tr>`;
    }

    return `<table class="result_content">
                  <tbody>
                      ${resultItems}
                  </tbody>
              </table>`;
  } else {
    return;
  }
};

export const handleResultScreen = (event, ocrType, useCapOcr) => {
  if (event.success) {
    const { ocrResult, base64Data, imageData, startTime, endTime } = event;

    if (ocrResult) {
      // [수정 포인트 1] resultJSON이 있으면 그것을 쓰고, 없으면 ocrResult 자체를 결과로 봅니다.
      const data = ocrResult.resultJSON || ocrResult;

      // [수정 포인트 2] data.resultCode가 존재하는지 안전하게 체크
      if (data && (data.resultCode == "0000" || data.resultCode == "0")) {
        handleSuccess(event, ocrType, useCapOcr);
      } else {
        // resultCode가 "9999"인 경우 여기로 들어옵니다.
        console.warn("OCR 처리 실패 (9999):", data);
        handleFailure(event, ocrType, useCapOcr);
      }
    } else {
      handleFailure(event, ocrType, useCapOcr);
    }
  } else {
    handleFailure(event, ocrType, useCapOcr);
  }

  // 캔버스 정리 로직
  const canvasRef = document.querySelector("#kwcCanvas");
  if (canvasRef) {
    const ctx = canvasRef.getContext("2d");
    ctx.clearRect(0, 0, canvasRef.width, canvasRef.height);
  }
};

export const handleSuccess = (event, ocrType, useCapOcr) => {
  changeContentViewStack("result", ocrType);
  finalizeUI(ocrType, useCapOcr);

  const { ocrResult, base64Data, cropData, startTime, endTime } = event;
  const recognitionTime = event.recognitionTime ?? null;
  const timeCheckJSON = Utils.getTimeCheckJSON(startTime, endTime, event);
  const captureTime = event.captureTime ?? null;
  const resultTime = event.resultTime ?? null;

  const imageDataURL = Utils.getImageDataURL({ base64Data, cropData });
  const formType = ocrResult?.resultJSON?.formResult?.type;

  updateResultUI(ocrResult, recognitionTime, imageDataURL, captureTime, resultTime);
  applyOcrTypeStyles(ocrType, formType);
  applyPortraitStyleIfNeeded(imageDataURL);
};

function updateResultUI(ocrResult, recognitionTime, imageDataURL, captureTime, resultTime) {
  const titleEl = document.querySelector("#title_text");
  const result_text = document.querySelector("#result_text");
  const imageContainer = document.querySelector("#imageContainer");

  result_text.classList.remove("result-portrait");
  imageContainer.classList.remove("imageContainer-portrait");

  result_text.removeAttribute("style");
  // titleEl.innerHTML = "인식된 정보를 <br>확인해주세요";
  titleEl.innerHTML = "";

  result_text.innerHTML = `<div class="custom-style">${ocrResultFormat(ocrResult, recognitionTime, captureTime, resultTime)}</div>`;
  imageContainer.innerHTML = `<img src="${imageDataURL}">`;
}

function applyPortraitStyleIfNeeded(imageDataURL) {
  const result_text = document.querySelector("#result_text");
  const imageContainer = document.querySelector("#imageContainer");

  const imgElement = new Image();
  imgElement.src = imageDataURL;
  imgElement.onload = () => {
    if (imgElement.height > imgElement.width) {
      result_text.removeAttribute("style");
      result_text.classList.add("result-portrait");
      imageContainer.classList.add("imageContainer-portrait");

      setTimeout(() => {
        result_text.scrollIntoView({ behavior: "smooth", block: "center" });
      }, 300);
    }
  };
}

function applyOcrTypeStyles(ocrType, formType) {
  const result_text = document.querySelector("#result_text");

  if (ocrType === 14 || ocrType === 16 || ocrType === 27) {
    result_text.style.paddingTop = "0px";
    result_text.style.paddingBottom = "30%";
  }

  if (formType === "00120") {
    result_text.style.paddingTop = "0";
    result_text.style.paddingBottom = "10%";
  }
}

export const handleFailure = (event, ocrType, useCapOcr) => {
  changeContentViewStack("result", ocrType);
  // UI 설정
  finalizeUI(ocrType, useCapOcr);
  const { ocrResult, base64Data, cropData, startTime, endTime } = event;
  const titleEl = document.querySelector("#title_text");
  const result_text = document.querySelector("#result_text");
  const imageContainer = document.querySelector("#imageContainer");
  let imageDataURL = null;
  let resultJSON;
  let message = null;

  if (ocrType == 14 || ocrType == 15 || ocrType == 16 || ocrType == 27) {
    imageContainer?.classList.add("fail");
  }

  result_text?.classList.add("fail");

  if (ocrResult) {
    resultJSON = event.ocrResult.resultJSON;
  }
  if (event.success) {
    titleEl.innerHTML = "";
    result_text.innerHTML = Utils.getDefaultMessage(ocrType);
  } else {
    message = Utils.getDefaultMessage(ocrType);

    if (ocrType != 7) {
      result_text.innerHTML = message;
    }
  }

  // 약간의 지연 후 스크롤
  if (ocrType == 14) {
    setTimeout(() => {
      result_text.scrollIntoView({ behavior: "smooth", block: "center" });
    }, 300); // 100ms 지연
  }
  if (cropData) {
    imageDataURL = Utils.getImageDataURL({ cropData });
  } else if (base64Data) {
    imageDataURL = Utils.base64DataURL(base64Data);
  }

  imageContainer.innerHTML = `<img src="${imageDataURL}">`;
};

export const handleTimeout = () => {
  // 1. 기존 webCamera_container 숨기기
  const webCamera = document.querySelector("#webcamera_container");
  if (webCamera) {
    webCamera.style.display = "none";
  }

  // 2. result_container가 이미 없으면 생성
  let resultContainer = document.querySelector("#result_container");
  if (!resultContainer) {
    resultContainer = document.createElement("div");
    resultContainer.id = "result_container";
    resultContainer.className = "timeout-message"; // CSS 클래스 사용
    resultContainer.innerText = "타임아웃되어 종료되었습니다.";
    document.body.appendChild(resultContainer);
  } else {
    resultContainer.style.display = "block";
  }
};

export const adjustResultTextStyle = (result_text, ocrType) => {
  if (ocrType == 2) {
    result_text.style.height = "70%";
  } else if (ocrType == 1 || ocrType == 10) {
    result_text.style.paddingTop = "5%";
    // result_text.style.height = "auto";
  } else if (ocrType == 14 || ocrType == 16 || ocrType == 27) {
    result_text.style.paddingTop = "5%";
    result_text.style.paddingBottom = "30%";
  } else {
    result_text.style.paddingTop = "20%";
  }
};

export const finalizeUI = (ocrType, useCapOcr) => {
  // 자주 쓰는 헬퍼 함수
  const getEl = (selector) => document.querySelector(selector);
  const safeShow = (el, display = "block") => el && (el.style.display = display);
  const safeHide = (el) => el && (el.style.display = "none");
  const safeResetMargin = (el) => el && (el.style.marginTop = "");
  const safeClearClass = (el) => el && (el.className = "");

  // 요소 캐싱
  const retryBtn = getEl("#retry_btn");
  const backBtn = getEl("#back_btn");
  const captureBtn = getEl("#cap_btn");
  const resultText = getEl("#result_text");
  const imageContainer = getEl("#imageContainer");
  const title = getEl(".title");
  const element = getEl(".contents-section.result-section");
  const logo = getEl(".koi-logo");

  // 초기 UI 상태 설정
  safeClearClass(imageContainer);
  safeClearClass(resultText);
  safeShow(retryBtn);
  safeShow(backBtn);
  safeHide(captureBtn);

  safeResetMargin(resultText);
  safeResetMargin(imageContainer);

  // 조건별 스타일/클래스 적용
  switch (ocrType) {
    case 1:
    // case 11:
    case 21:
    // case 22:
    case 23:
    case 29:
      imageContainer?.classList.add("idcard");
      break;
    case 12: // giro
      imageContainer?.classList.add("giro_epn");
      break;
    case 3:
      imageContainer?.classList.add("card");
      break;
    case 10:
      imageContainer?.classList.add("giro");
      break;
    case 8: // account
    case 26:
      imageContainer?.classList.add("account");
      break;
    case 4: // qrcode
      imageContainer?.classList.add("qrcode");
      break;

    case 2: // passport
    case 25:
      imageContainer?.classList.add("passport");
      resultText?.classList.add("passport");
      break;
    case 14: // docs
    case 15:
    case 16:
    case 24:
    case 27:
      imageContainer?.classList.add("docs");
      element?.classList.add("docs");
      resultText?.classList.add("docs");
      safeHide(logo);
      break;
    case 28:
      imageContainer?.classList.add("serial");
    case 30:
      imageContainer?.classList.add("usim");
  }

  // 필요하다면 타이틀 초기화
  // if (title) title.innerHTML = "";
};

// 에러 처리
export function handleError(error) {
  Utils.logError("Error occurred: ", error);
  try {
    const errObject = JSON.parse(error.message);
    Utils.showAlert("Error: " + errObject.message);
  } catch (parseError) {
    Utils.logError("JSON parse error: ", parseError);
    Utils.showAlert("An unexpected error occurred: " + error.message);
  }
}
