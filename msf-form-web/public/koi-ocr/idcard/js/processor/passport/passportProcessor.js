class passportProcessor {
  constructor() {
    this.Module = null;
    this.buffer = null;
    this.isModelLoaded = false;
  }

  async initModule(wasmDirectory, modelType) {
    if (this.modelType != modelType) {
      importScripts(`${wasmDirectory}passport/Koi_passport.js?v=202511191038`);
      this.Module = await passportModule({ wasmDirectory });
    }
  }

  async loadModel(modelType, url) {
    if (this.modelType == modelType && this.isModelLoaded) {
      return 1; // 이미 해당 모델이 로드됨
    }
    try {
      const result = this.Module.ccall("initialize", "number", ["string"], [url]);
      if (result == 1) {
        this.isModelLoaded = true;
        this.modelType = modelType;
        return 1;
      } else {
        return result;
      }
    } catch (error) {
      this.Module = null; // 초기화 실패 시 메모리 할당 해제
      postMessage({ type: "unload" });
      return 0;
    }
  }

  ocr(imageData, width, height) {
    if (!this.isModelLoaded) {
      throw new Error("Model not loaded. Call loadModel first.");
    }

    const bufferSize = width * height * 4;
    this.buffer = this.Module._malloc(bufferSize);
    const inputBuffer = new Uint8Array(this.Module.HEAPU8.buffer, this.buffer, bufferSize);
    inputBuffer.set(imageData.data);

    const resultCharPtr = this.Module.ccall("mrz_read", "number", ["number", "number", "number"], [this.buffer, width, height]);

    const resultString = this.Module.UTF8ToString(resultCharPtr);

    // this.Module._free(resultCharPtr);

    const result = {
      resultJSON: JSON.parse(resultString),
    };

    this.Module._free(this.buffer);
    return result;
  }

  unload() {
    if (this.Module) {
      this.Module._free(this.buffer);
      this.buffer = null;
      this.Module = null;
      this.isModelLoaded = false;
    }
  }
}

// export default passportProcessor;
self.passportProcessor = passportProcessor;
