class ConvertModule {
  constructor() {
    this.Module = null;
    this.Lapl = null;
    this.buffer = null;
    this.isModelLoaded = false;
    this.isLaplLoad = false;
    this.dataPtr = null;
    this.convertMode = null;
  }

  async initModule(wasmDirectory, modelType, convertTo, jpegQuality) {
    if (this.modelType != modelType) {
      if (modelType == 14 || modelType == 15 || modelType == 16 || modelType == 27) {
        if (convertTo == 1) {
          importScripts(`${wasmDirectory}tiff/koi_tiff.js?v=202511191038`);
          this.Module = await koiTiff({ wasmDirectory });
        }
        if (convertTo == 2) {
          importScripts(`${wasmDirectory}pdf/koi_pdf.js?v=202511191038`);
          this.Module = await koiPdf({ wasmDirectory });
        }
        this.convertMode = convertTo;
      } else {
        this.Module = null;
      }
    }
  }

  async loadModel(modelType, convertTo, jpegQuality) {
    if (this.modelType == modelType && this.isModelLoaded) {
      return 1; // 이미 해당 모델이 로드됨
    }

    try {
      // if (convertTo == 2) {
      //pdf
      this.Module.ccall("pdf_quality", null, ["number"], [jpegQuality]);
      // }
      // const result = this.Module.ccall(functionName, "number", [], []);
      const result = 1;
      if (result == 1) {
        this.isModelLoaded = true;
        this.modelType = modelType;
        return 1;
      } else {
        return 0;
      }
    } catch (error) {
      this.Module = null; // 초기화 실패 시 메모리 할당 해제
      postMessage({ type: "unload" });
      return 0;
    }
  }

  sendAllImagesToWasm(imageDataList, width, height) {
    const imageSize = width * height * 3;
    const totalSize = imageSize * imageDataList.length;

    const dataPtr = this.Module._malloc(totalSize);
    const heap = new Uint8Array(this.Module.HEAPU8.buffer, dataPtr, totalSize);

    imageDataList.forEach((img, i) => {
      heap.set(img, i * imageSize);
    });

    const lengthPtr = this.Module._malloc(4);
    let convertFunction = null;
    if (this.convertMode == 1) {
      convertFunction = "generate_tiff_from_images";
    } else if (this.convertMode == 2) {
      convertFunction = "generate_pdf_from_images";
    }

    const convertPtr = this.Module.ccall(convertFunction, "number", ["number", "number", "number", "number", "number"], [dataPtr, imageDataList.length, width, height, lengthPtr]);
    // this.dataPtr = this.Module.ccall("generate_pdf_from_images", "number", ["number", "number", "number", "number", "number"], [dataPtr, imageDataList.length, width, height, lengthPtr]);

    const tiffLength = this.Module.HEAP32[lengthPtr >> 2];

    let convertDataCopy = null;
    if (convertPtr && tiffLength > 0) {
      const convertData = new Uint8Array(this.Module.HEAPU8.buffer, convertPtr, tiffLength);
      // 메인 스레드로 넘기려면 복사본을 만들어 넘겨야 안전함
      convertDataCopy = new Uint8Array(convertData);
    }

    this.Module._free(dataPtr);
    this.Module._free(lengthPtr);

    return convertDataCopy; // TIFF 바이너리 Uint8Array 반환 (워커면 postMessage로 넘겨주세요)
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
self.convertModule = ConvertModule;
