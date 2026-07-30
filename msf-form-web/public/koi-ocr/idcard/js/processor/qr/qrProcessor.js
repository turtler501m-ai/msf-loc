class Koder {
  constructor() {
    this.mod = null;
    this.api = null;
    this.decodedResult = null;
    this.ocrType = null;
  }

  async initModule(wasmDirectory, modelType) {
    try {
      if (this.modelType === modelType) {
        return { success: 1 };
      }

      importScripts(`${wasmDirectory}qr/koiQr.js?v=202511191038`);

      this.mod = await ZXing({ wasmDirectory });

      return { success: 1 };
    } catch (error) {
      console.error("[QR initModule] failed", error);

      this.mod = null;

      return {
        success: 0,
        error: {
          code: "QR_INIT_FAILED",
          message: error?.message || "QR 모듈 초기화에 실패했습니다.",
        },
      };
    }
  }

  // 초기화 함수 호출s
  async loadModel(modelType) {
    try {
      if (!this.mod) {
        return {
          success: 0,
          error: {
            code: "QR_MODULE_NOT_INITIALIZED",
            message: "QR 모듈이 초기화되지 않았습니다.",
          },
        };
      }

      await this.mod.ready;

      const result = this.mod.ccall("initialize", "number");

      if (result === 1) {
        this.modelType = modelType;
        return { success: 1 };
      }

      return {
        success: 0,
        error: {
          code: "QR_MODEL_INIT_FAILED",
          message: "QR 모델 initialize 호출이 실패했습니다.",
        },
      };
    } catch (error) {
      console.error("[QR loadModel] failed", error);

      this.mod = null;
      postMessage({ type: "unload" });

      return {
        success: 0,
        error: {
          code: "QR_LOAD_EXCEPTION",
          message: error?.message || "QR 모델 로딩 중 오류가 발생했습니다.",
        },
      };
    }
  }

  splitBusanBankValue(resultJson) {
    const field401Index = resultJson.resultJSON.formResult.fieldResults.findIndex((f) => f.fieldId === "401");
    if (field401Index === -1) return resultJson;

    const field401 = resultJson.resultJSON.formResult.fieldResults[field401Index];
    const value = field401.value;

    // "busanbank" 포함 여부 체크
    if (value && value.includes("busanbank")) {
      try {
        const decoded = decodeURIComponent(value);
        const url = new URL(decoded);
        const params = new URLSearchParams(url.search);

        // 파라미터를 fieldResults로 분리
        const newFieldResults = [];
        let fieldIdCounter = 401; // 401부터 시작

        for (const [key, val] of params.entries()) {
          newFieldResults.push({
            fieldId: String(fieldIdCounter),
            displayName: key,
            value: val,
          });
          fieldIdCounter++;
        }

        // 기존 401 필드 제거 후 새 배열로 교체
        const otherFields = resultJson.resultJSON.formResult.fieldResults.filter((f) => f.fieldId !== "401");
        resultJson.resultJSON.formResult.fieldResults = [...newFieldResults, ...otherFields];
      } catch (e) {
        console.error("BusanBank URL 디코딩/파싱 실패:", e);
      }
    }

    return resultJson;
  }

  parseLocalTexQRCode(resultJson) {
    // fieldId가 "401"인 필드를 찾습니다.
    const field401Index = resultJson.resultJSON.formResult.fieldResults.findIndex((f) => f.fieldId === "401");
    if (field401Index === -1) {
      return resultJson;
    }

    const field401 = resultJson.resultJSON.formResult.fieldResults[field401Index];
    const value = field401.value;

    // value가 없거나 'http'로 시작하면 파싱하지 않고 반환합니다.
    if (!value || value.startsWith("http")) return resultJson;

    try {
      // 문자열 양 끝의 공백을 제거하여 파싱 오류를 방지합니다.
      const trimmedValue = value.trim();

      // 1. 이름 (Name): 문자열의 맨 끝에 있는 한글을 찾습니다.
      const nameMatch = trimmedValue.match(/[가-힣]+$/);

      // 2. 납기내금액 (Amount Due)과 납부기한 (Payment Due Date):
      //    '000000' 뒤에 이어지는 5자리 숫자(금액)와 8자리 숫자(납부기한)를 하나의 정규식으로 파싱합니다.
      const amountDateMatch = trimmedValue.match(/000000(\d{5})(\d{8})/);

      // 지방세 패턴 규칙이 아닐 경우(이름 또는 금액/날짜 패턴이 없는 경우) 원본 JSON을 그대로 반환합니다.
      if (!nameMatch || !amountDateMatch) {
        return resultJson;
      }

      const nameValue = nameMatch[0];
      const electronicNumber = trimmedValue.slice(nameMatch.index - 19, nameMatch.index);
      const amountValue = parseInt(amountDateMatch[1], 10).toString();
      let dateValue = "";
      if (amountDateMatch && amountDateMatch[2].startsWith("202")) {
        dateValue = amountDateMatch[2];
      }

      // 파싱한 정보를 담을 새로운 필드 배열을 생성합니다.
      const newFields = [
        { fieldId: "401", displayName: "전자납부번호", value: electronicNumber },
        { fieldId: "402", displayName: "납기내금액", value: amountValue },
        { fieldId: "403", displayName: "납부기한", value: dateValue },
        { fieldId: "404", displayName: "이름", value: nameValue },
      ];

      // 기존의 fieldId "401" 필드를 제거하고, 새로운 필드들을 추가합니다.
      const otherFields = resultJson.resultJSON.formResult.fieldResults.filter((f) => f.fieldId !== "401");
      resultJson.resultJSON.formResult.fieldResults = [...newFields, ...otherFields];
    } catch (e) {
      // 파싱 중 에러 발생 시 콘솔에 출력합니다.
      console.error("지방세 QR 파싱 실패:", e);
    }

    return resultJson;
  }

  decode(imageData, width, height) {
    if (this.mod == null) {
      throw new Error("Model not loaded. Call loadModel first.");
    }

    const sourceBuffer = imageData;
    const buffer = this.mod._malloc(sourceBuffer.byteLength);
    this.mod.HEAPU8.set(sourceBuffer, buffer);

    // C API 함수 wrap
    // ReadResult* readBarcodesFromPixmap(int bufferPtr, int imgWidth, int imgHeight, bool tryHarder, const char* format, int* outCount)
    const outCountPtr = this.mod._malloc(4);
    const tryHarder = 1;

    const resultPtr = this.mod.ccall("readBarcodesFromPixmap", "number", ["number", "number", "number", "number", "string", "number"], [buffer, width, height, tryHarder, "", outCountPtr]);

    // 읽은 바코드 수
    const resultCount = this.mod.HEAP32[outCountPtr >> 2];

    let type = "";
    let code = "";

    if (resultCount > 0) {
      const result = resultPtr; // 포인터
      // result는 ReadResult* 배열 구조체
      // 각 필드 오프셋에 맞춰 읽기
      const formatOffset = 0; // char format[64]
      const textOffset = 64; // char text[512]

      type = this.mod.UTF8ToString(result + formatOffset);
      code = this.mod.UTF8ToString(result + textOffset);
    }

    // 결과 JSON 생성
    let resultCode = "1004"; // 실패
    if (type && code) resultCode = "0000";

    const resultJson = {
      resultJSON: {
        resultCode: resultCode,
        formResult: {
          type: "Bar/QR-Code",
          fieldResults: [
            { fieldId: "400", displayName: "바코드 타입", value: type },
            { fieldId: "401", displayName: "바코드 인식 값", value: code },
          ],
        },
      },
    };

    if (this.ocrType === 26 && code && code.includes("busanbank")) {
      this.decodedResult = this.splitBusanBankValue(resultJson);
    }

    // 메모리 해제
    this.mod._free(outCountPtr);
    this.mod._free(buffer);

    return resultJson;
  }
}
// export default Koder;
self.koder = Koder;
