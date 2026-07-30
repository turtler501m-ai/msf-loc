// maskEditor.js
import ImgMasking from "../../modules/imgMasking.js";
export class MaskEditor {
  constructor(dScanner, index, onFinish) {
    this.dScanner = dScanner;
    this.index = index;
    this.onFinish = onFinish;
    this.original = dScanner.images[index];

    this.undoStack = [];
    this.undoStack.push(this.original.dataUrl); // 최초 상태

    this._renderModal();
  }

  _renderModal() {
    this.modal = document.createElement("div");
    this.modal.id = "maskingEditModal";
    this.modal.className = "masking-modal";

    const closeBtn = document.createElement("div");
    closeBtn.className = "close-btn";
    closeBtn.onclick = () => this.close();

    const wrapper = document.createElement("div");
    wrapper.className = "masking-wrapper";

    this.img = document.createElement("img");
    this.img.src = this.original.dataUrl;
    this.img.className = "masking-edit-image";

    wrapper.appendChild(this.img);
    this.modal.appendChild(wrapper);
    this.modal.appendChild(closeBtn);
    document.body.appendChild(this.modal);

    // ImgMasking은 이미지가 DOM에 반영된 후 생성되어야 함
    setTimeout(() => {
      this.masking = new ImgMasking(this.img);

      // 마스크가 변경될 때만 undo 저장
      this.masking.onMaskChange = (maskBase64) => {
        this.undoStack.push(maskBase64);
      };
    }, 0);

    this._renderButtons();
  }

  _renderButtons() {
    const controls = document.createElement("div");
    controls.className = "masking-controls";

    const maskingMenu = document.createElement("div");
    maskingMenu.className = "masking-menu";

    const undoBtn = document.createElement("button");
    // 텍스트 제거 및 아이콘 클래스 추가
    undoBtn.textContent = "이전";
    undoBtn.className = "icon-button undo-btn";
    undoBtn.onclick = () => this._undo();

    const restoreBtn = document.createElement("button");
    restoreBtn.textContent = "원본 복원";
    restoreBtn.className = "icon-button restore-btn";
    restoreBtn.onclick = () => this._restoreOriginal();

    const cancelBtn = document.createElement("button");
    cancelBtn.textContent = "편집 취소";
    cancelBtn.className = "icon-button cancel-btn";
    cancelBtn.onclick = () => this.close();

    const saveBtn = document.createElement("button");
    saveBtn.id = "maskingSave";
    saveBtn.textContent = "저장";
    saveBtn.className = "icon-button save-btn";
    saveBtn.onclick = () => this._save();

    maskingMenu.appendChild(cancelBtn);
    maskingMenu.appendChild(undoBtn);
    maskingMenu.appendChild(restoreBtn);
    controls.appendChild(maskingMenu);
    controls.appendChild(saveBtn);

    document.body.appendChild(controls);
  }

  // 이전 취소
  _undo() {
    if (this.undoStack.length <= 1) return;

    this.undoStack.pop();
    const prevMask = this.undoStack[this.undoStack.length - 1];

    this.masking.loadMaskedImage(prevMask);
  }

  // 원본 복원
  _restoreOriginal() {
    this.undoStack = [this.original.originalDataUrl];
    this.masking.loadMaskedImage(this.original.originalDataUrl);
  }

  // 저장
  _save() {
    const final = this.masking.getMaskedFinalImage();
    this.dScanner.replaceImage(this.index, final);
    this.close();
    this.onFinish?.();
  }

  // 닫기
  close() {
    // 마스크 모달 제거
    this.modal?.remove();

    // 컨트롤 버튼 제거
    document.querySelector(".masking-controls")?.remove();
    document.getElementById("fullPreviewModal").style.display = "flex";
    // 캔버스 이벤트 / 참조 해제 (메모리 누수 방지용)
    this.maskInstance = null;
  }
}
