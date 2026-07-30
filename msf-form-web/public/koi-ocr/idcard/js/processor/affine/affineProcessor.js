class DocsAffine {
  constructor() {
    this.Module = null;
    this.buffer = null;
    this.isModelLoaded = false;
  }

  async loadModel() {
    try {
      if (!this.isModelLoaded) {
        const wasmDirectory = "../../detect/affine/";

        importScripts(`${wasmDirectory}doc_affine.js?v=202511191038`);
        this.Module = await new Promise((resolve) => {
          affineModule({
            locateFile: (path) => {
              const filename = path.split("/").pop();
              return `${wasmDirectory}${filename}`;
            },
            onRuntimeInitialized() {
              resolve(this); // this는 Module
            },
          });
        });

        if (!this.Module) {
          return 0;
        }
      }
      return 1;
    } catch (error) {
      return 0;
    }
  }

  getAll(imageData, width, height) {
    const bufferSize = width * height * 4 + 1;
    this.buffer = this.Module._malloc(bufferSize);
    const inputBuffer = new Uint8Array(this.Module.HEAPU8.buffer, this.buffer, bufferSize);
    inputBuffer.set(imageData.data);

    const resultCharPtr = this.Module.ccall("doc_affine_function", "number", ["number", "number", "number"], [this.buffer, width, height]);
    resultString = this.Module.AsciiToString(resultCharPtr);
    const result = {
      resultJSON: JSON.parse(resultString),
    };

    this.Module._free(resultCharPtr);
    this.Module._free(this.buffer);
    return result;
  }

  getPoints(imageData, width, height) {
    const bufferSize = width * height * 4 + 1;
    this.buffer = this.Module._malloc(bufferSize);
    const inputBuffer = new Uint8Array(this.Module.HEAPU8.buffer, this.buffer, bufferSize);
    inputBuffer.set(imageData.data);

    const resultCharPtr2 = this.Module.ccall("corner_point_function", "number", ["number", "number", "number"], [this.buffer, width, height]);
    const resultString2 = this.Module.AsciiToString(resultCharPtr2);
    const result2 = {
      resultJSON: JSON.parse(resultString2),
    };

    this.Module._free(resultCharPtr2);
    this.Module._free(this.buffer);
    return result2;
  }

  getAffine(imageData, width, height, points) {
    const bufferSize = width * height * 4 + 1;
    this.buffer = this.Module._malloc(bufferSize);
    const inputBuffer = new Uint8Array(this.Module.HEAPU8.buffer, this.buffer, bufferSize);
    inputBuffer.set(imageData.data);

    // const points = result2.resultJSON.points;
    const resultCharPtr3 = this.Module.ccall(
      "wrap_function",
      "number",
      ["number", "number", "number", "number", "number", "number", "number", "number", "number", "number", "number"],
      [this.buffer, width, height, points[0].x, points[0].y, points[1].x, points[1].y, points[2].x, points[2].y, points[3].x, points[3].y]
    );

    const resultString3 = this.Module.AsciiToString(resultCharPtr3);
    const result3 = {
      resultJSON: JSON.parse(resultString3),
    };

    this.Module._free(resultCharPtr3);
    this.Module._free(this.buffer);
    return result3;
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

// export default affineProcessor;
// export { DocsAffine };
self.DocsAffine = DocsAffine;
