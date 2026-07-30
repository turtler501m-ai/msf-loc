class DScanner {
  constructor({ onUpdate = () => {}, onOpenPreview = () => {} }) {
    this.onUpdate = onUpdate;
    this.onOpenPreview = onOpenPreview;
    this.images = []; // 이미지 목록
  }

  /**
   * 새로운 이미지 추가
   */
  addImage(imageDataUrl) {
    const index = this.images.length;

    this.images.push({
      dataUrl: imageDataUrl, // 현재 표시되는 이미지
      originalDataUrl: imageDataUrl, // 최초 촬영 원본 이미지 보관
      index,
    });

    this.onUpdate(this.images);
  }

  /**
   * 특정 이미지 수정(원본 유지)
   */
  setImage(index, base64DataUrl) {
    if (index >= 0 && index < this.images.length) {
      this.images[index].dataUrl = base64DataUrl;
      this.onUpdate(this.images);
    }
  }

  /**
   * 이미지 삭제 (index 재정렬 포함)
   */
  deleteImage(index) {
    if (index >= 0 && index < this.images.length) {
      this.images.splice(index, 1);

      // 삭제 후 index 재정렬
      this.images = this.images.map((img, idx) => ({
        ...img,
        index: idx,
      }));

      this.onUpdate(this.images);
    }
  }

  /**
   * 현재 이미지 덮어쓰기 (원본 유지)
   */
  replaceImage(index, newDataUrl) {
    if (index >= 0 && index < this.images.length) {
      this.images[index].dataUrl = newDataUrl; // 원본은 바꾸지 않음
      this.onUpdate(this.images);
    }
  }

  /**
   * 원본 되돌리기
   */
  restoreOriginal(index) {
    if (index >= 0 && index < this.images.length) {
      this.images[index].dataUrl = this.images[index].originalDataUrl;
      this.onUpdate(this.images);
    }
  }

  /**
   * dataUrl만 리턴
   */
  getAllImages() {
    return this.images.map((img) => img.dataUrl);
  }

  /**
   * 전체 초기화
   */
  clearImages() {
    this.images = [];
    this.onUpdate(this.images);
  }

  /**
   * 미리보기 모달 열기
   */
  openPreviewModal() {
    this.onOpenPreview(this.images);
  }
}

export { DScanner };
