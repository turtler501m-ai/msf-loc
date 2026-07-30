importScripts("../../lib/koi-crypto-util.min.js");

let controller = new AbortController();
const OCR_TYPE = {
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

const serverUrls = {};
serverUrls[OCR_TYPE.IDCARD] = "/kt_m/api/ocr/idcard";
serverUrls[OCR_TYPE.CREDITCARD] = "/sample/credit-card";
serverUrls[OCR_TYPE.QRCODE] = "/sample/account";
serverUrls[OCR_TYPE.ACCOUNT] = "/sample/account";
serverUrls[OCR_TYPE.DUALSCAN] = "/sample/account";
serverUrls[OCR_TYPE.GIRO] = "/poc/giro";
serverUrls[OCR_TYPE.IDCLS] = "/sample/id-cls";
serverUrls[OCR_TYPE.GIROEPN] = "/poc/giro2";
serverUrls[OCR_TYPE.DOCS] = "/sample/document";
serverUrls[OCR_TYPE.FULLPAGE] = "/sample/fullpage";
serverUrls[OCR_TYPE.CROP] = "/sample/document";
serverUrls[OCR_TYPE.CROPLIVE] = "/sample/document";
serverUrls[OCR_TYPE.PASSPORT] = "/sample/passport";
serverUrls[OCR_TYPE.DATASET] = "/sample/id_card_gethering";
serverUrls[OCR_TYPE.BIRIDGE] = "/biridge/pageOCR";
serverUrls[OCR_TYPE.IDCARDFULL] = "/sample/id-card-full";
serverUrls[OCR_TYPE.DSCAN] = "/sample/ocr-api/request"; //문서스캔
serverUrls[OCR_TYPE.MDS] = "/sample/ocr-api/request"; //멀티문서스캔
serverUrls[OCR_TYPE.REGISBACK] = "/kt_m/api/ocr/idcard"; //외국인등록증후면

const BASE_URL = "https://demo.koiware.co.kr:19444";
// const BASE_URL = "http://demo.koiware.co.kr:19442";

const getApiUrl = (ocrType, isExternal) => {
  const internalUrl = serverUrls[ocrType];
  return isExternal ? `${BASE_URL}${internalUrl}` : internalUrl;
};

// 지정된 URL(API 주소)로 이미지를 전송하고, 요청에 대한 응답 반환
const attemptFetch = async (url, data) => {
  const result = await fetch(url, {
    method: "POST",
    signal: controller.signal, // AbortController의 시그널을 사용하여 요청을 취소
    headers: {},
    body: data,
  });
  return result;
};

let validCheck = null;
let encryption = null;
let regisClass = null;
let userId = null;
let fileData = null;
let imageKey = null;
let docInfoList = null;

self.onmessage = async (e) => {
  if (e?.data) {
    if (e.data.ocrType && e.data.base64Data) {
      const ocrType = e.data.ocrType;
      const isExternalRequest = true; // 이 값을 변경하여 내부/외부 요청을 선택
      const apiUrl = getApiUrl(ocrType, isExternalRequest);
      const base64Data = e.data.base64Data;

      // 암호화 옵션 값 확인
      if (e.data.ocrEncryption) {
        encryption = e.data.ocrEncryption;
      }

      // true일때 암호화 모듈 로드
      if (encryption) {
        await KoiCryptoJS.init(1, "testserial");
      }
      const payload = new FormData();

      // if (e.data.qrcode) {
      //   const qrcode = e.data.qrcode;
      //   const qrValue = getQrCodeValue(qrcode);
      //   payload.append("docCode", qrValue);
      // }

      const timestamp = Date.now();
      if (ocrType == 16 || ocrType == 27) {
        fileData = base64Data;
        payload.append("srcFile", fileData, timestamp + ".tiff");
      } else {
        fileData = base64toBlob(base64Data, "image/jpeg");
        payload.append("srcFile", fileData, timestamp + ".jpg");
      }

      payload.append("saveOption", true);

      try {
        const result = await Promise.race([attemptFetch(apiUrl, payload)]);
        let response = await result.json();
        const status = result.status;
        let resultJSON = null;
        if (encryption) {
          // 인식 결과 복호화
          const decryptedResult = decrypted(response);
          resultJSON = { resultJSON: decryptedResult };
        } else {
          resultJSON = { resultJSON: response };
        }
        self.postMessage({ type: "ocrResult", status: status, message: resultJSON });
      } catch (e) {
        const status = e?.status ?? 0;
        controller?.abort(); // AbortController를 사용하여 이전 요청을 취소
        self.postMessage({ type: "ocrResult", status: status, message: e.message || e.toString(), isError: true });
      }
    } else if (e.data == "new") {
      controller = new AbortController();
      self.postMessage({ type: "ocrResult", message: null });
    }
  }
};

function extractDocInfo(qrcode) {
  return qrcode.map((item) => {
    return {
      docCode: item.docCode,
      docVersion: item.docVersion,
    };
  });
}

const base64toBlob = (base64Data, mimeString) => {
  var byteString = atob(base64Data);
  var ab = new ArrayBuffer(byteString.length);
  var ia = new Uint8Array(ab);

  for (var i = 0; i < byteString.length; i++) {
    ia[i] = byteString.charCodeAt(i);
  }

  let blobData = null;
  if (encryption) {
    blobData = KoiCryptoJS.encryptBinary(ia);
  } else {
    blobData = ia;
  }

  return new Blob([blobData], { type: mimeString });
};

const decrypted = (ocrResult) => {
  const result = JSON.parse(JSON.stringify(ocrResult));

  // 필드 값 복호화 (formResult가 존재하고 fieldResults가 배열인지 확인)
  if (result.formResult && Array.isArray(result.formResult.fieldResults)) {
    const fieldResults = result.formResult.fieldResults;

    for (let i = 0; i < fieldResults.length; i++) {
      let value = fieldResults[i].value;

      if (typeof value === "string" && value.startsWith("enc://")) {
        const encryptedData = value.split("enc://")[1]; // "enc://" 이후의 암호화된 데이터 추출
        try {
          const decryptedData = KoiCryptoJS.decrypt(encryptedData);
          fieldResults[i].value = decryptedData; // 복호화된 값으로 대체
        } catch (error) {
          logError(`복호화 오류 (fieldId: ${fieldResults[i].fieldId}):`, error);
        }
      }
    }
  } else {
    logError("fieldResults가 없거나 잘못된 응답입니다.", result);
  }

  // 이미지 필드 복호화
  const imageFields = ["cropImage", "photoImage", "maskImage"];
  for (let key of imageFields) {
    if (typeof result[key] == "string" && result[key].startsWith("enc://")) {
      const encryptedData = result[key].split("enc://")[1]; // "enc://" 이후 데이터 추출
      try {
        const decryptedData = KoiCryptoJS.decrypt(encryptedData); // base64

        result[key] = decryptedData; // 복호화된 값으로 대체
      } catch (error) {
        logError(`이미지 복호화 오류 (${key}):`, error);
      }
    }
  }

  return result;
};

function logError(...args) {
  if (!isDevMode) return;

  if (args.length === 1) {
    console.error(args[0]);
  } else if (args.length === 2) {
    console.error(args[0], args[1]);
  } else if (args.length > 2) {
    console.error(...args);
  }
}

function getTodayUserID() {
  const now = new Date();

  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, "0");
  const day = String(now.getDate()).padStart(2, "0");

  return `data_${year}${month}${day}`;
}

function getQrCodeValue(qrcodeData) {
  const value = qrcodeData?.[0]?.results?.[0]?.result?.formResult?.fieldResults?.find((f) => f.fieldId === "401")?.value;

  if (!value) return null;

  return value.includes("^^") ? value.split("^^")[0] : value;
}
