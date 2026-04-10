import * as e2ee from '../../../common/eSonicE2EE.js';
var itemList = [];
var docRcvId = "";
var MAX_ITEMS = 10;

const FILE_RULES = {
    maxFileSize: 5 * 1024 * 1024,
    maxTotalSize: 5 * 1024 * 1024,
    maxImgSize: 10 * 1024 * 1024,
    allowedTypes: [
        "application/pdf",
        "image/png",
        "image/jpeg",
        "image/tiff",
        "image/bmp"
    ],
    allowedExts: [".pdf", ".png", ".jpg", ".jpeg", ".tif", ".tiff", ".bmp"],
    maxFilenameBytes: 200
};

// ========== Utils ==========
function utf8BytesLen(str) { return new Blob([str]).size; }
function pad2(n){ return String(n).padStart(2, "0"); }
function pad3(n){ return String(n).padStart(3, "0"); }
function getExtLower(name) { const i = (name || "").lastIndexOf("."); return i >= 0 ? name.slice(i).toLowerCase() : ""; }
function isAllowedType(mime) { return FILE_RULES.allowedTypes.includes((mime || "").toLowerCase()); }
function isAllowedExt(ext) { return FILE_RULES.allowedExts.includes(ext.toLowerCase()); }
function getAcceptAttr() { return [...FILE_RULES.allowedTypes, ...FILE_RULES.allowedExts].join(','); }

async function sniffMimeByMagic(file) {
    try {
        const head = await file.slice(0, 8).arrayBuffer();
        const u8 = new Uint8Array(head);
        if (u8[0] === 0x25 && u8[1] === 0x50 && u8[2] === 0x44 && u8[3] === 0x46) return "application/pdf";
        if (u8[0] === 0x89 && u8[1] === 0x50 && u8[2] === 0x4E && u8[3] === 0x47) return "image/png";
        if (u8[0] === 0xFF && u8[1] === 0xD8) return "image/jpeg";
        if ((u8[0] === 0x49 && u8[1] === 0x49 && u8[2] === 0x2A) || (u8[0] === 0x4D && u8[1] === 0x4D && u8[2] === 0x00 && u8[3] === 0x2A)) return "image/tiff";
    } catch(_) {}
    return file.type || "";
}

// ========== Server ==========
function getItemList() {
    const varData = ajaxCommon.getSerializedData({ docRcvId: $("#docRcvId").val() });
    ajaxCommon.getItemNoLoading({
        id: 'getDocRcvItemPendingList', async: false, cache: false,
        url: '/document/receive/getDocRcvItemPendingList.do',
        data: varData, dataType: "json"
    }, function(jsonObj){ itemList = jsonObj || []; });
}

// ========== Markup builders ==========
function buildOptionHtml(options, selectedValue) {
    let html = "";
    for (let i = 0; i < options.length; i++) {
        const opt = options[i];
        const sel = (selectedValue ? (opt.itemSeq === selectedValue) : (i === 0)) ? " selected" : "";
        html += `<option value="${opt.itemSeq}"${sel}>${opt.itemNm}</option>`;
    }
    return html;
}

function buildItemHtml(idx, selectedValue) {
    const selectId   = "select"     + pad3(idx);
    const fileNameId = "fileName"   + idx;
    const fileUpId   = "fileUpload" + idx;
    const optionHtml = buildOptionHtml(itemList, selectedValue);

    return {
        html: `
      <div class="file-upload-list__select">
        <div class="file-upload-list__number-wrap">
          <span class="file-upload-list__number">${pad2(idx)}</span>
          <label class="c-label" for="${selectId}">서류선택</label>
        </div>
        <select class="c-select" id="${selectId}">${optionHtml}</select>
      </div>
      <div class="file-upload-list__attachment">
        <div class="file-upload-wrap">
          <label class="c-label">첨부</label>
          <input type="text" class="file-name" id="${fileNameId}" placeholder="" readonly>
          <input type="file" class="file-input" id="${fileUpId}" accept="${getAcceptAttr()}">
          <label for="${fileUpId}" class="file-label">파일선택</label>
          <button type="button" class="file-remove-btn" aria-label="항목 삭제">삭제</button>
        </div>
      </div>
    `
    };
}



// ========== 초기 마크업  ==========
function normalizeItemStructure(li) {
    if (!li.classList.contains('file-upload-list__item')) {
        li.classList.add('file-upload-list__item');
    }
    // --- SELECT 영역 보장 ---
    let selectWrap = li.querySelector('.file-upload-list__select');
    if (!selectWrap) {
        selectWrap = document.createElement('div');
        selectWrap.className = 'file-upload-list__select';
        li.insertBefore(selectWrap, li.firstChild);
    }

    // number-wrap 보장
    let numberWrap = selectWrap.querySelector('.file-upload-list__number-wrap');
    if (!numberWrap) {
        numberWrap = document.createElement('div');
        numberWrap.className = 'file-upload-list__number-wrap';
        // selectWrap 맨 앞에 배치 (select 앞)
        selectWrap.prepend(numberWrap);
    }

    // select 보장
    let selectEl = selectWrap.querySelector('select.c-select');
    if (!selectEl) {
        selectEl = document.createElement('select');
        selectEl.className = 'c-select';
        selectWrap.appendChild(selectEl);
    }
    if (!selectEl.options || selectEl.options.length === 0) {
        selectEl.innerHTML = buildOptionHtml(itemList, null);
    }

    // --- 번호(span) 이동/정리 ---
    // number-wrap 내부 번호
    let numberSpan = numberWrap.querySelector('.file-upload-list__number');

    // 바깥에 떠다니는 번호들(현재 li 안의 모든 .file-upload-list__number 중 wrap 밖인 것)
    const strayNumbers = Array.from(
        li.querySelectorAll('.file-upload-list__number')
    ).filter(el => !el.closest('.file-upload-list__number-wrap'));

    if (!numberSpan) {
        if (strayNumbers.length > 0) {
            // 첫 번째 것을 number-wrap 안으로 이동
            numberSpan = strayNumbers.shift();
            numberWrap.prepend(numberSpan);
        } else {
            // 없으면 새로 생성
            numberSpan = document.createElement('span');
            numberSpan.className = 'file-upload-list__number';
            numberWrap.prepend(numberSpan);
        }
    }
    // 남은 중복 번호들은 제거
    strayNumbers.forEach(el => el.remove());

    // --- 라벨(label.c-label for="selectNNN") 이동/정리 ---
    // number-wrap 내부의 c-label (select용 라벨)
    let selectLabel = numberWrap.querySelector('label.c-label');

    // 파일 라벨(file-label)과 혼동 방지를 위해 select용만 필터링
    const straySelectLabels = Array.from(
        li.querySelectorAll('label.c-label')
    ).filter(el =>
        !el.closest('.file-upload-list__number-wrap') &&
        el.hasAttribute('for') &&
        /^select/i.test(el.getAttribute('for') || '')
    );

    if (!selectLabel) {
        if (straySelectLabels.length > 0) {
            selectLabel = straySelectLabels.shift();
            numberWrap.appendChild(selectLabel);
        } else {
            // 없으면 새로 생성
            selectLabel = document.createElement('label');
            selectLabel.className = 'c-label';
            selectLabel.textContent = '서류선택';
            numberWrap.appendChild(selectLabel);
        }
    }
    // 남은 중복 라벨은 제거
    straySelectLabels.forEach(el => el.remove());

    // 첨부 영역
    let attachWrap = li.querySelector('.file-upload-list__attachment');
    if (!attachWrap) {
        attachWrap = document.createElement('div');
        attachWrap.className = 'file-upload-list__attachment';
        li.appendChild(attachWrap);
    }

    let fileWrap = attachWrap.querySelector('.file-upload-wrap');
    if (!fileWrap) {
        fileWrap = document.createElement('div');
        fileWrap.className = 'file-upload-wrap';
        attachWrap.appendChild(fileWrap);
    }

    let labelWrap = fileWrap.querySelector('label.c-label');
    if(!labelWrap) {
        labelWrap = document.createElement('label');
        labelWrap.className = 'c-label';
        labelWrap.textContent = '첨부'
        fileWrap.appendChild(labelWrap);
    }

    let textInput = fileWrap.querySelector('input.file-name');
    if (!textInput) {
        textInput = document.createElement('input');
        textInput.type = 'text';
        textInput.className = 'file-name';
        textInput.readOnly = true;
        //textInput.placeholder = '첨부';
        fileWrap.appendChild(textInput);
    } else {
        // 보안/일관성
//    textInput.readOnly = true;
//    if (!textInput.placeholder) textInput.placeholder = '첨부';
    }

    let fileInput = fileWrap.querySelector('input.file-input');
    if (!fileInput) {
        fileInput = document.createElement('input');
        fileInput.type = 'file';
        fileInput.className = 'file-input';
        fileWrap.appendChild(fileInput);
    }
    fileInput.setAttribute('accept', getAcceptAttr());

    let fileLabel = fileWrap.querySelector('label.file-label');
    if (!fileLabel) {
        fileLabel = document.createElement('label');
        fileLabel.className = 'file-label';
        fileLabel.textContent = '파일선택';
        fileWrap.appendChild(fileLabel);
    }

    let removeBtn = fileWrap.querySelector('.file-remove-btn');
    if (!removeBtn) {
        removeBtn = document.createElement('button');
        removeBtn.type = 'button';
        removeBtn.className = 'file-remove-btn';
        removeBtn.setAttribute('aria-label', '항목 삭제');
        removeBtn.textContent = '삭제';
        fileWrap.appendChild(removeBtn);
    }

    // 레거시 잘못된 for 값 제거(초기 템플릿에서 들어온 것 방지)
    fileWrap.querySelectorAll('label[for^="filename"]').forEach(lab => {
        if (lab.classList.contains('file-label')) lab.removeAttribute('for'); // reindex에서 올바르게 세팅
    });
}

// ========== 재인덱스(정합화) ==========
function reindexItems(list) {
    const items = list.querySelectorAll(".file-upload-list__item");
    for (let i = 0; i < items.length; i++) {
        const item = items[i];
        const idx  = i + 1;
        const p2   = pad2(idx);
        const p3   = pad3(idx);

        // 번호
        let noSpan = item.querySelector(".file-upload-list__number");
        if (!noSpan) {
            noSpan = document.createElement("span");
            noSpan.className = "file-upload-list__number";
            const selectWrap0 = item.querySelector(".file-upload-list__select") || item.firstChild;
            item.insertBefore(noSpan, selectWrap0);
        }
        noSpan.textContent = p2;

        // select + label(for)
        const selectWrap = item.querySelector(".file-upload-list__select");
        const numberWrap = selectWrap?.querySelector(".file-upload-list__number-wrap");
        const selectEl   = selectWrap?.querySelector("select.c-select");
        const selectLbl  = numberWrap?.querySelector("label.c-label");
        const newSelectId= "select" + p3;
        if (selectEl)  selectEl.id = newSelectId;
        if (selectLbl) selectLbl.setAttribute("for", newSelectId);

        // 파일 영역
        const fileWrap  = item.querySelector(".file-upload-wrap");
        const labelWrap = fileWrap?.querySelector("label.c-label");
        const textInput = fileWrap?.querySelector("input.file-name");
        const fileInput = fileWrap?.querySelector("input.file-input");
        const fileLabel = fileWrap?.querySelector("label.file-label");

        const newTextId = "fileName" + idx;
        const newFileId = "fileUpload" + idx;

        if(labelWrap) labelWrap.setAttribute("for", newTextId);
        if (textInput) textInput.id = newTextId;
        if (fileInput) {
            fileInput.id = newFileId;
            fileInput.setAttribute("accept", getAcceptAttr());
        }
        if (fileLabel) fileLabel.setAttribute("for", newFileId);

        // 혹시 남아있을 레거시 for 교정
        fileWrap?.querySelectorAll('label[for^="filename"]').forEach(lab => {
            if (lab.classList.contains("file-label")) lab.setAttribute("for", newFileId);
        });
    }
}

// ========== 리스트 초기화/추가/삭제 ==========
function hydrateExisting(list) {
    const items = list.querySelectorAll(".file-upload-list__item, li"); // li만 들어온 경우도 커버
    if (!items.length) return;

    // 1) 모든 li를 표준 구조로 변환
    items.forEach(li => normalizeItemStructure(li));

    // 2) 옵션 비어있을 수 있으니 보강
    list.querySelectorAll('select.c-select').forEach(sel => {
        if (!sel.options || sel.options.length === 0) {
            sel.innerHTML = buildOptionHtml(itemList, null);
        }
    });

    // 3) 전체 인덱스/for/id 정합화
    reindexItems(list);
}

function addItem(list) {
    const count = list.querySelectorAll(".file-upload-list__item").length + 1;
    if (count > MAX_ITEMS) { MCP.alert(`최대 ${MAX_ITEMS}개까지만 추가할 수 있습니다.`); return; }

    const built = buildItemHtml(count, null);
    const li = document.createElement("li");
    li.className = "file-upload-list__item";
    li.innerHTML = built.html;
    list.appendChild(li);

    // 새 항목도 혹시 몰라 정규화 → 전체 재인덱스
    normalizeItemStructure(li);
    reindexItems(list);
}

// ========== 이미지 압축 ==========
async function decodeToBitmap(file, bitmapOpts = {}) {
    if (!('createImageBitmap' in window)) throw new Error('createImageBitmap을 지원하지 않는 브라우저입니다.');
    return await createImageBitmap(file, { imageOrientation: 'none', premultiplyAlpha: 'default', colorSpaceConversion: 'default', resizeQuality: 'high', ...bitmapOpts });
}
function drawBitmapToCanvas(bitmap, { maxSize = 1280, flipY = false } = {}) {
    let { width, height } = bitmap;
    if (maxSize && (width > maxSize || height > maxSize)) {
        const scale = (width > height) ? (maxSize / width) : (maxSize / height);
        width  = Math.max(1, Math.floor(width  * scale));
        height = Math.max(1, Math.floor(height * scale));
    }
    const canvas = document.createElement('canvas');
    canvas.width = width; canvas.height = height;
    const ctx = canvas.getContext('2d', { willReadFrequently: false });
    if (flipY) { ctx.translate(0, height); ctx.scale(1, -1); }
    ctx.drawImage(bitmap, 0, 0, width, height);
    return { canvas, width, height };
}
function canvasToBlob(canvas, { type = 'image/jpeg', quality = 0.9 } = {}) {
    return new Promise((resolve, reject) => {
        const mime = /^image\/png$/i.test(type) ? 'image/png' : /^image\/webp$/i.test(type) ? 'image/webp' : 'image/jpeg';
        if (canvas.convertToBlob) { canvas.convertToBlob({ type: mime, quality }).then(resolve).catch(reject); return; }
        canvas.toBlob((blob) => blob ? resolve(blob) : reject(new Error('canvas.toBlob() 실패')), mime, quality);
    });
}
async function compressImageFromFile(file, { maxSize = 1280, quality = 0.9 } = {}) {
    const bitmap = await decodeToBitmap(file);
    try {
        const { canvas } = drawBitmapToCanvas(bitmap, { maxSize });
        const wantType = (/^image\/png$/i.test(file.type)) ? 'image/png'
            : (/^image\/webp$/i.test(file.type)) ? 'image/webp'
                : 'image/jpeg';
        return await canvasToBlob(canvas, { type: wantType, quality });
    } finally {
        if (typeof bitmap.close === 'function') bitmap.close();
    }
}

// ========== 검증 ==========
async function validateOneFile(file) {
    const msgs = [];
    let processedFile = file;
    if (!file) return { ok: true, messages: [] };

    if (!file.size || file.size <= 0) msgs.push(`빈 파일은 업로드할 수 없습니다.`);

    const nameBytes = utf8BytesLen(file.name || "");
    if (nameBytes > FILE_RULES.maxFilenameBytes) msgs.push(`파일명이 너무 깁니다: ${nameBytes} bytes (최대 ${FILE_RULES.maxFilenameBytes} bytes)`);

    const ext = getExtLower(file.name);
    const normExt = (ext || "").replace(/^\./, "").toLowerCase();
    let mime = file.type || await sniffMimeByMagic(file);

    if (!isAllowedExt(ext) || !isAllowedType(mime)) {
        msgs.push("허용되지 않는 파일 형식입니다.");
    } else {
        const isImage = (mime || '').startsWith('image/');
        if (isImage && normExt !== 'tif' && normExt !== 'tiff') {
            if (file.size > FILE_RULES.maxImgSize) {
                msgs.push(`총 전송 용량 5MB를 초과하였습니다. `);
            } else if(file.size <= 500 * 1024) {
                //500KB 미만 압축없이 통과
                processedFile = file;
            }
            else {
                try {
                    const resizedBlob = await compressImageFromFile(file, { maxSize: 1280, quality: 0.9 });
                    if (resizedBlob.size > FILE_RULES.maxFileSize) {
                        msgs.push(`총 전송 용량 5MB를 초과하였습니다. `);
                    } else {
                        const outType = resizedBlob.type || 'image/jpeg';
                        processedFile = new File([resizedBlob], file.name, { type: outType });
                    }
                } catch (e) {
                    msgs.push('미지원 형식입니다.\n관리자에게 문의해주세요.');
                    processedFile = file;
                }
            }
        } else {
            if (file.size > FILE_RULES.maxFileSize) {
                msgs.push(`총 전송 용량 5MB를 초과하였습니다. `);
            }
        }
    }

    return { ok: msgs.length === 0, messages: msgs, file: processedFile };
}

function validateAggregate(listEl) {
    const items = listEl.querySelectorAll(".file-upload-list__item");
    let total = 0;
    const nameCount = new Map();
    const aggMsgs = [];

    for (const li of items) {
        const fileEl = li.querySelector('input.file-input');
        const f = (fileEl && fileEl.files && fileEl.files[0]) ? fileEl.files[0] : null;
        if (f) {
            total += f.size || 0;
            const key = (f.name || '').toLowerCase();
            nameCount.set(key, (nameCount.get(key) || 0) + 1);
        }
    }

    if (total > FILE_RULES.maxTotalSize) {
        const curMB = (total / 1024 / 1024).toFixed(2);
        const maxMB = (FILE_RULES.maxTotalSize / 1024 / 1024);
        aggMsgs.push(`총 전송 용량 5MB를 초과하였습니다.`);
    }
    for (const [name, cnt] of nameCount.entries()) {
        if (cnt > 1) aggMsgs.push(`중복된 파일명입니다 : "${name}" (${cnt}개)`);
    }

    return { ok: aggMsgs.length === 0, messages: aggMsgs };
}

// ========== 수집/암호화/전송 ==========
function collectSelectedItems() {
    const list  = document.getElementById("fileUploadList");
    const items = list.querySelectorAll(".file-upload-list__item");
    const picked = [];

    for (let i = 0; i < items.length; i++) {
        const item     = items[i];
        const selectEl = item.querySelector("select.c-select");
        const fileEl   = item.querySelector("input.file-input");
        const nameEl   = item.querySelector("input.file-name");
        if (!selectEl || !fileEl) continue;

        let hasFile = !!(fileEl.files && fileEl.files.length > 0);
        if (!hasFile && fileEl.value) hasFile = true;

        if (hasFile) {
            const fileObj     = fileEl.files && fileEl.files[0] ? fileEl.files[0] : null;
            const displayName = (nameEl && nameEl.value) ? nameEl.value : (fileObj ? fileObj.name : "");
            picked.push({ docCode: selectEl.value || "", displayName: displayName || "", file: fileObj, filePathValue: fileEl.value });
        }
    }
    return picked;
}

function blobToBase64(blob) {
    return new Promise((resolve, reject) => {
        const r = new FileReader();
        r.onload = () => { const s = String(r.result || ""); const i = s.indexOf(","); resolve(i >= 0 ? s.slice(i + 1) : s); };
        r.onerror = reject;
        r.readAsDataURL(blob);
    });
}

async function buildEncryptedPayload(selectedItems) {
    const items = [];
    for (let i = 0; i < selectedItems.length; i++) {
        const it = selectedItems[i];
        if (it.file) {
            const b64 = await blobToBase64(it.file);
            items.push({ docId: it.docCode, docName: it.displayName, fileName: it.file.name, mimeType: it.file.type || "application/octet-stream", size: it.file.size || 0, dataBase64: b64 });
        } else {
            items.push({ docId: it.docCode, docName: it.displayName, filePath: it.filePathValue || "" });
        }
    }
    return { method: "urlInsertEX", docRcvId: docRcvId || "", items };
}

async function initE2EESession() {
    e2ee.setConfig('/document/upload/init.do', 'ktm');
    await e2ee.initSession();
    console.log('[OK] handshakeId:', e2ee.getSession());
}

async function onClickSend() {
    try {
        KTM.LoadingSpinner.show();

        if (!e2ee.getSession()) { MCP.alert("보안 세션 초기화에 실패했습니다. 잠시 후 다시 시도해 주세요."); return; }

        const selected = collectSelectedItems();
        if (selected.length === 0) { MCP.alert("전송할 파일이 없습니다.\n파일을 선택한 항목만 전송됩니다."); return; }

        const listEl = document.getElementById("fileUploadList");
        const agg = validateAggregate(listEl);
        if (!agg.ok) { MCP.alert(agg.messages.join("\n")); return; }

        const callUrl = "/document/upload/urlInsert.do";
        const plainPayload = await buildEncryptedPayload(selected);

        ajaxCommon.getItemNoLoading({
            id: 'getRequestInfo', async: false, cache: false,
            url: '/document/upload/getRequestInfo.do', dataType: "json"
        }, function(jsonObj){ plainPayload.startTime = jsonObj.startTime; });

        let resp = await e2ee.execute(callUrl, plainPayload);
        if (resp === "eSession expired") { await e2ee.initSession(); resp = await e2ee.execute(callUrl, plainPayload); }

        if (resp && resp.code === "20000") {
            MCP.alert("업로드 성공: " + (resp.message || "업로드가 완료되었습니다."), function() {
                location.replace(location.origin + '/m/document/receive/completeView.do');
            });
        } else {
            MCP.alert("업로드 실패: " + (resp?.message || "알 수 없는 오류가 발생했습니다."));
        }
    } catch (err) {
        console.error("[ERR] request failed:", err);
        MCP.alert("업로드 중 오류가 발생했습니다. \n" + err.message);
    } finally {
        KTM.LoadingSpinner.hide();
    }
}

// ========== 파일 선택(파일 시스템 접근 API) ==========
async function openPickerAndAssign(fileInput) {
    if (!window.showOpenFilePicker) { fileInput.click(); return; }

    const types = [{
        description: '파일 선택',
        accept: {
            'application/pdf': ['.pdf'],
            'image/png': ['.png'],
            'image/jpeg': ['.jpg', '.jpeg'],
            'image/tiff': ['.tif', '.tiff'],
            'image/bmp': ['.bmp']
        }
    }];

    try {
        const handles = await window.showOpenFilePicker({ multiple: false, types, excludeAcceptAllOption: true });
        const file = await handles[0].getFile();
        const dt = new DataTransfer();
        dt.items.add(new File([await file.arrayBuffer()], file.name, { type: file.type }));
        fileInput.files = dt.files;
        fileInput.dispatchEvent(new Event('change', { bubbles: true }));
    } catch (_) {}
}

// ========== UI 초기화 + 이벤트 위임 ==========
function initUploaderUI() {
    const list   = document.getElementById("fileUploadList");
    const btnAdd = document.getElementById("addFileBtn");
    const btnSend= document.getElementById("sendBtn");
    if (!list) return;

    // ★ 초기 마크업 정규화 + 인덱싱
    hydrateExisting(list);

    const agg = validateAggregate(list);
    if (!agg.ok && agg.messages.length) MCP.alert(agg.messages.join("\n"));

    if (btnAdd) btnAdd.addEventListener("click", () => addItem(list));

    // (A) 클릭 위임: 파일 라벨/삭제 버튼
    list.addEventListener("click", (e) => {
        const label = e.target.closest("label.file-label");
        if (label) {
            e.preventDefault();
            const li = label.closest(".file-upload-list__item");
            const fileInput = li?.querySelector("input.file-input");
            if (fileInput) openPickerAndAssign(fileInput);
            return;
        }
        const removeBtn = e.target.closest(".file-remove-btn");
        if (removeBtn) {
            const items = list.querySelectorAll(".file-upload-list__item");
            if (items.length <= 1) { MCP.alert("최소 1개 이상은 남아 있어야 합니다."); return; }
            removeBtn.closest(".file-upload-list__item")?.remove();
            reindexItems(list);
            const agg2 = validateAggregate(list);
            if (!agg2.ok && agg2.messages.length) MCP.alert(agg2.messages.join("\n"));
        }
    });

    // (B) 변경 위임: 파일 input 변경(검증/미러)
    list.addEventListener("change", async (e) => {
        const fileEl = e.target.closest("input.file-input");
        if (!fileEl) return;
        const li = fileEl.closest(".file-upload-list__item");
        if (!li) return;

        const listEl = document.getElementById("fileUploadList");
        const orig = (fileEl.files && fileEl.files[0]) ? fileEl.files[0] : null;

        const v1 = await validateOneFile(orig);
        if (!v1.ok) {
            MCP.alert(v1.messages.join("\n"));
            fileEl.value = "";
            const nameEl = li.querySelector(".file-name");
            if (nameEl) nameEl.value = "";
            li.classList.add("has-file-error");
            return;
        }
        if (v1.file && v1.file !== orig) {
            const dt = new DataTransfer();
            dt.items.add(v1.file);
            fileEl.files = dt.files;
        }

        const v2 = validateAggregate(listEl);
        if (!v2.ok) {
            MCP.alert(v2.messages.join("\n"));
            fileEl.value = "";
            const nameEl = li.querySelector(".file-name");
            if (nameEl) nameEl.value = "";
            li.classList.add("has-file-error");
            return;
        }

        const nameEl = li.querySelector(".file-name");
        if (nameEl) nameEl.value = fileEl.files[0]?.name || "";
        li.classList.remove("has-file-error");
        li.classList.add("has-file-ok");
    });

    if (btnAdd) {
        list.innerHTML  = "";
        btnAdd.click();
    }

    if (btnSend) btnSend.addEventListener("click", onClickSend);
}



// ========== Page init ==========
async function initPage() {
    try {
        docRcvId = $("#docRcvId").val();
        getItemList();
        initUploaderUI();
        await initE2EESession();
    } catch (err) {
        console.error('[ERR] handshake failed:', err);
        MCP.alert("보안 세션 초기화에 실패했습니다. 잠시 후 다시 시도해 주세요.");
    }
}

document.addEventListener('DOMContentLoaded', async () => {
    initPage();
});

