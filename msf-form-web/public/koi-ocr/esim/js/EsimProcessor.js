self.APP_VERSION = "20260422_ESIM"; // 버전 관리용

class EsimProcessor {
  constructor() {
    this.Module = null;
    this.isInitialized = false;
  }

  // 모듈 초기화 및 라이선스 체크
  async initModule(wasmDirectory, targetCount = 3) {
    try {
      // 1. .js 스크립트 중복 로드 방지
      if (typeof koiEsimModule === "undefined") {
        console.log("Wasm 스크립트 최초 로드...");
        await new Promise((resolve, reject) => {
          const script = document.createElement("script");
          script.src = `${wasmDirectory}koiEsim.js?v=${self.APP_VERSION}`;
          script.onload = resolve;
          script.onerror = reject;
          document.head.appendChild(script);
        });
      }

      // 2. 핵심: .wasm 인스턴스 중복 생성 방지
      // 이미 this.Module이 있다면 .wasm 파일을 다시 요청하지 않습니다.
      if (!this.Module) {
        console.log("Wasm 인스턴스 신규 생성..."); // 처음 한 번만 찍혀야 함
        this.Module = await koiEsimModule({
          locateFile: (path) => (path.endsWith(".wasm") ? `${wasmDirectory}${path}` : path),
        });
      } else {
        console.log("기존 Wasm 인스턴스 유지 및 재사용"); // 두 번째부터는 이게 찍혀야 함
      }

      // 3. C++ 데이터만 초기화 (매번 호출)
      const result = this.Module.ccall("initialize", "number", ["number"], [targetCount]);
      this.isInitialized = result === 1;

      return { success: this.isInitialized ? 1 : 0 };
    } catch (error) {
      console.error(`[EsimProcessor] Init failed`, error);
      return { success: 0, error: error.message };
    }
  }

  // 프레임 분석
  scanFrame(imageData, width, height) {
    if (!this.isInitialized) throw new Error("Wasm Module not initialized");

    let imgBufferPtr = null;
    let resultPtr = null;

    try {
      // 1. 이미지 메모리 할당 및 복사 (RGBA 데이터)
      const bufferSize = width * height * 4;
      imgBufferPtr = this.Module._malloc(bufferSize);
      this.Module.HEAPU8.set(imageData.data, imgBufferPtr);

      // 2. C++ processFrame 호출
      // 반환값은 JSON 문자열 포인터 (const char*)
      resultPtr = this.Module.ccall("processFrame", "number", ["number", "number", "number"], [imgBufferPtr, width, height]);

      // 3. 결과 문자열 읽기 및 파싱
      const jsonString = this.Module.UTF8ToString(resultPtr);
      const result = JSON.parse(jsonString);

      // 4. C++에서 strdup으로 할당된 메모리 해제 (중요)
      if (this.Module._freeString) {
        this.Module._freeString(resultPtr);
      } else {
        this.Module._free(resultPtr);
      }

      return result;
    } catch (error) {
      console.error("Scan error:", error);
      return { resultCode: "9999", error: error.message };
    } finally {
      // 5. 이미지 버퍼 메모리 해제
      if (imgBufferPtr !== null) this.Module._free(imgBufferPtr);
    }
  }

  unload() {
    this.isInitialized = false;
    console.log("Wasm 상태만 비활성화 (인스턴스는 유지)");
  }
}

// self.EsimProcessor = EsimProcessor;
const esimProcessorInstance = new EsimProcessor();
