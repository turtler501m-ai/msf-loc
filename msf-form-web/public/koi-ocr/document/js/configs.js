// config.js
import { APP_VERSION } from "./lib/version.js";

// koi-module 이벤트 정의
const KOI_OCR_EVENT_DATA = {
  READY: "koi-ready",
  RESULT: "koi-result",
  PROGRESS: "koi-progress",
  CAPTURE: "koi-capture",
  CAPTUREONLY :"koi-capture-only",
  ERROR: "koi-error",
  TIMEOUT: "koi-timeout",
};

export const MSG_TYPE = {
  READY: "moudleReady",
  READY_OCR: "ocrReady",
  START_OCR: "startOcr",
  STOP_OCR: "stopOcr",
  START_CAMERA: "startCamera",
  STOP_CAMERA: "stopCamera",
  RESULT: "ocrResult",
  ERROR: "ocrError",
  FILE_UPLOAD: "fileUpload",
  CAPTURE_SUCCESS : "onlyCapture"
};

// OCR 타입 정의(ocrType)
const OCR_TYPE_DATA = {
  IDCARD: 1, // 주민등록증, 운전면허증, 여권
  PASSPORT: 2, // 여권 MRZ 서버
  CREDITCARD: 3, // 신용카드 API
  QRCODE: 4, // QR/Barcode WASM
  SEALCERT: 5, // 인감증명서
  BIZREGCERT: 6, // 사업자등록증
  CROP: 7, // 문서 crop WASM
  ACCOUNT: 8, // 계좌번호 인식
  CHECK: 9, // 수표 인식
  GIRO: 10, // 지로번호 인식
  IDCLS: 11, // 사본판별
  GIROEPN: 12, // 지로 전자납부번호 인식
  IDFACE: 13, // 안면인증
  DOCS: 14, // 정형문서
  FULLPAGE: 15, // 전문인식
  DSCAN: 16, // 다중 문서 스캔
  INGAM: 17, // 인감대사
  FACEREGIST: 18, // 안면인증
  DATASET: 19, // 데이터셋 생성
  IDCARD_AC: 21, // 신분증인식-웹접근성
  IDFAKE_AC: 22, // 사본판별-웹접근성
  IDCARDFULL: 23, // 신분증 통합
  BIRIDGE: 24, // 바이릿지
  PASSPORTWASM: 25, // 여권 WASM
  DUALSCAN: 26, // QR+계좌인식
  MDS: 27, // 다중 문서 스캔 + QR, MultiDocScan
  SERIAL : 28, // 시리얼 번호
  REGISBACK : 29, // 외국인등록증 후면
  USIM : 30, // usim
};

// 촬영 타입 정의(useCapOcr)
const RTC_TYPE_DATA = {
  AUTO: 1, //자동촬영(자동탐지)
  MANUAL: 2, //수동촬영(버튼으로 촬영)
  FILE: 3, //파일 업로드 테스트
  CAPTURE: 4, //이미지 촬영
  AUTO_MANUAL: 5, //자동촬영 + 수동촬영 버튼
  DATASET: 6, //데이터셋
};

// OCR_TYPE_DATA를 사용하여 buttonsData를 정의
const BUTTONS_DATA = [
  {
    id: "idcard",
    imgSrc: "./images/idcard.svg",
    label: "신분증 인식",
    type: OCR_TYPE_DATA.IDCARD,
    className: "btn ocr-button",
    detail: "주민등록증•운전면허증",
  },
  {
    id: "idcardfull",
    imgSrc: "./images/idcardfull.svg",
    label: "신분증 통합",
    type: OCR_TYPE_DATA.IDCARDFULL,
    className: "btn ocr-button",
    detail: "신분증 OCR•사본판별",
  },
  {
    id: "qrcode",
    imgSrc: "./images/qr.svg",
    label: "QR 인식",
    type: OCR_TYPE_DATA.QRCODE,
    className: "btn ocr-button",
    detail: "QR•바코드",
  },
  // {
  //   id: "dualScan",
  //   imgSrc: "./images/qr.svg",
  //   label: "QR/계좌인식",
  //   type: OCR_TYPE_DATA.DUALSCAN,
  //   className: "btn ocr-button",
  // },
  {
    id: "idcls",
    imgSrc: "./images/idfake.svg",
    label: "신분증 사본판별",
    type: OCR_TYPE_DATA.IDCLS,
  },
  {
    id: "card",
    imgSrc: "./images/creditcard.svg",
    label: "카드 인식",
    type: OCR_TYPE_DATA.CREDITCARD,
    detail: "신용•체크카드",
  },
  {
    id: "account",
    imgSrc: "./images/account.svg",
    label: "계좌번호 인식",
    type: OCR_TYPE_DATA.ACCOUNT,
    detail: "계좌번호",
  },
  {
    id: "giro",
    imgSrc: "./images/giro.svg",
    label: "지로 인식",
    type: OCR_TYPE_DATA.GIRO,
  },
  {
    id: "giroEPN",
    imgSrc: "./images/giro_epn.svg",
    label: "전자납부번호",
    type: OCR_TYPE_DATA.GIROEPN,
    detail: "지로 전자납부번호",
  },
  {
    id: "dscan",
    imgSrc: "./images/docs.svg",
    label: "문서스캔",
    type: OCR_TYPE_DATA.DSCAN,
  },
  {
    id: "mds",
    imgSrc: "./images/multi_docs.svg",
    label: "멀티문서스캔",
    type: OCR_TYPE_DATA.MDS,
    detail: "QR•문서스캔",
  },
  {
    id: "passportWasm",
    imgSrc: "./images/passport.svg",
    label: "여권 인식",
    type: OCR_TYPE_DATA.PASSPORTWASM,
    detail: "여권 MRZ",
  },
  {
    id: "serialNumber",
    imgSrc: "./images/serial.svg",
    label: "시리얼번호",
    type: OCR_TYPE_DATA.SERIAL,
    detail: "시리얼번호",
  },
  {
    id: "regisback",
    imgSrc: "./images/idcard.svg",
    label: "외국인등록증 후면",
    type: OCR_TYPE_DATA.REGISBACK,
    detail: "외국인등록증 후면",
  },
  {
    id: "usim",
    imgSrc: "./images/usim.svg",
    label: "USIM",
    type: OCR_TYPE_DATA.USIM,
    detail: "USIM",
  },
];

const CATEGORY_MAP = [
  {
    title: "신분증 / 여권",
    ids: ["idcardfull", "regisback"],
  },
  {
    title: "문서 / QR-Barcode",
    ids: ["serialNumber", "usim"],
  },
];

const REGIS_CLASS_MAP_DATA = {
  진본: "0",
  디지털: "1",
  종이: "2",
  필름: "3",
  포토카드: "4",
};

const TEXTS_DATA = {
  startMessage: `문서와 이미지 속 정보를 빠르게 인식하고,<br /> 정확하게 판별합니다. <br />지금 데모를 통해 직접 확인해보세요.`,
  consentTitle: `신분증 인식 및 사본판별<br /> 테스트 관련 안내`,
  consentDescription: `본 테스트 페이지에서는 아래와 같이 신분증에 대한 인식 및 사본판별 테스트 목적으로 신분증 이미지를 활용합니다.`,
  consentDetailsList: [
    `이용 목적 : 신분증 인식 및 사본판별 기능 시연`,
    `표시 항목 : 성명, 생년월일, 신분증 이미지 등 결과 화면에 입력되거나 표시되는 정보`,
    `테스트 시 사용된 신분증 이미지와 결과는 결과화면 표시 이후 저장하지 않고 즉시 파기됩니다.`,
  ],
  consentNotice: `※ 코이웨어의 데모시스템은 신분증 정보를 일시적으로 활용하나 별도로 보유하지 않으며, 인식 후 결과 포함 모든 정보는 테스트 이후 <strong style="color: #b22323">즉시 파기</strong>됩니다.`,
  consentWarning: `<strong style="color: #b22323">※ 테스트를 원하지 않으면 닫아주세요.</strong>`,
  startButtonText: "동의 후 시작하기",
  submitButtonText: "인식하기",
  retryButtonText: "재촬영",
  backButtonText: "되돌아가기",
  resultScreenReaderMessage: "인식 결과입니다.",
  uploadViewTitleDefault: "파일 업로드 영역",
};

// OCR 타입별 옵션 정의
const KOI_OPTIONS = {
  [OCR_TYPE_DATA.PASSPORT]: {
    cameraOptions: {
      useCapOcr: RTC_TYPE_DATA.MANUAL,
      useDetect: false,
    },
    useCapOcrOverride: RTC_TYPE_DATA.MANUAL,
  },

  [OCR_TYPE_DATA.QRCODE]: {
    useWasmOcr: true,
    cameraOptions: {},
  },

  [OCR_TYPE_DATA.CROP]: {
    useWasmOcr: true,
    useDemo: false,
    recogTryLimit: 1,
    cameraOptions: {
      totalPages: 1,
    },
  },

  [OCR_TYPE_DATA.ACCOUNT]: {
    cameraOptions: {
      useCapOcr: RTC_TYPE_DATA.FILE,
      useDetect: false,
    },
    useCapOcrOverride: RTC_TYPE_DATA.MANUAL,
  },

  [OCR_TYPE_DATA.GIROEPN]: {
    cameraOptions: {
      useCapOcr: RTC_TYPE_DATA.MANUAL,
      useDetect: false,
    },
    useCapOcrOverride: RTC_TYPE_DATA.MANUAL,
  },

  [OCR_TYPE_DATA.DSCAN]: {
    useWasmOcr: true,
    useDemo: false,
    recogTryLimit: 20,
    cameraOptions: {},
  },

  [OCR_TYPE_DATA.INGAM]: {
    cameraOptions: {
      useDetect: false,
    },
    useCapOcrOverride: RTC_TYPE_DATA.MANUAL,
  },

  [OCR_TYPE_DATA.FACEREGIST]: {
    cameraOptions: {
      useCapOcr: RTC_TYPE_DATA.MANUAL,
      useDetect: false,
    },
    useCapOcrOverride: RTC_TYPE_DATA.MANUAL,
  },

  // 20번 타입은 OCR_TYPE_DATA 상수에 정의되어 있지 않아 그대로 숫자 사용
  20: {
    cameraOptions: {
      useCapOcr: RTC_TYPE_DATA.MANUAL,
      useDetect: false,
    },
    useCapOcrOverride: RTC_TYPE_DATA.MANUAL,
  },

  [OCR_TYPE_DATA.IDCARD_AC]: {
    cameraOptions: {
      accessMode: true,
      rtcMaxRetryCount: 20,
    },
  },

  [OCR_TYPE_DATA.IDFAKE_AC]: {
    cameraOptions: {
      accessMode: true,
      rtcMaxRetryCount: 20,
    },
  },

  [OCR_TYPE_DATA.PASSPORTWASM]: {
    useWasmOcr: true,
    cameraOptions: {
      useDetect: true,
    },
  },

  [OCR_TYPE_DATA.DUALSCAN]: {
    useWasmOcr: true,
    cameraOptions: {
      useCapOcr: RTC_TYPE_DATA.AUTO_MANUAL, // 자동 + 수동
      useDetect: true,
    },
  },

  [OCR_TYPE_DATA.MDS]: {
    useWasmOcr: true,
    useDemo: false,
    recogTryLimit: 20,
    cameraOptions: {
      useCapOcr: RTC_TYPE_DATA.AUTO,
    },
  },

  [OCR_TYPE_DATA.REGISBACK]: {
    cameraOptions: {
      rtcStartDelay: 2000, // 외국인등록증 후면 촬영 가이드 안내시간
    },
  },

  default: {
    cameraOptions: {
      containerId: "#webcamera_container",
      useCapOcr: RTC_TYPE_DATA.AUTO, // 촬영모드(1:자동촬영, 2:수동촬영, 3: 파일업로드, 4: 촬영만)
      rtcRetryDelay: 50, // 캡처 대기시간
      rtcStartDelay: 50, // 시작 대기시간
      rtcDetectTime: 60000, // 촬영시간(50초)
      useDetect: true, // 탐지모듈 사용여부
      accessMode: false, // 웹접근성
      regisClassType: 0,
      albumImage: false,
      recogTryLimit: 5,
      document: {
        totalPages: 3, // 촬영가능한 총 페이지 수
        convertTo: 1, // 문서포맷(1:tiff, 2:pdf)
        jpegQuality: 80, // 문서포맷 JPEG 압축률
      },
    },
    useWebCamera: true,
    ocrType: OCR_TYPE_DATA.IDCARD,
    ocrWorkerJs: `./worker/ocr/ocrWorker.js?v=${APP_VERSION}`,
    isDevMode: true,
    ocrEncryption: false,
  },
};

// 촬영 타입별 카메라 뷰 정의 (상수 키 사용)
export const CAMERA_VIEW_CONFIG = {
  [RTC_TYPE_DATA.AUTO]: { view: "camera", capture: false, runCamera: true },
  [RTC_TYPE_DATA.MANUAL]: { view: "camera", capture: true, runCamera: false },
  [RTC_TYPE_DATA.FILE]: { view: "upload", hideSubmit: true, runCamera: false },
  [RTC_TYPE_DATA.CAPTURE]: { view: "camera", capture: true, runCamera: false },
  [RTC_TYPE_DATA.AUTO_MANUAL]: { view: "camera", switch: true, runCamera: true },
  [RTC_TYPE_DATA.DATASET]: { view: "camera", capture: false, runCamera: true },
};

export const config = Object.freeze({
  KOI_OCR_EVENT: KOI_OCR_EVENT_DATA,
  OCR_TYPE: OCR_TYPE_DATA,
  rtcType: RTC_TYPE_DATA,
  buttonsData: BUTTONS_DATA,
  regisClassMap: REGIS_CLASS_MAP_DATA,
  texts: TEXTS_DATA,
  koiOptions: KOI_OPTIONS,
  viewStack: CAMERA_VIEW_CONFIG,
  MSG_EVENT: MSG_TYPE,
  CATEGORY_MAP: CATEGORY_MAP,
});
