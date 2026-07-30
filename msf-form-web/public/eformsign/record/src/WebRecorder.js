// WebRecorder - 웹 녹음/재생 모듈
// OZMediaRecorder.ts 와 동일한 데이터 포맷(Base64 Data URL) 호환.
// 모든 공개 메서드는 Promise 기반. UI 의존 없음.

import { detectMimeType, normalizeDataUrlMime } from './platform.js';

/**
 * ============================================================
 * 녹음 데이터 크기 가이드 (24kbps 기준, 예상치)
 * ============================================================
 *
 * 녹음 길이        원본 Blob        Base64 Data URL      localStorage 저장
 * -----------------------------------------------------------------------
 * 1분             약 180 KB        약 240 KB            가능
 * 2분 (기본 한도)  약 360 KB        약 480 KB            가능
 * 5분             약 900 KB        약 1.2 MB            가능
 * 10분            약 1.8 MB        약 2.4 MB            가능 (5MB 한도 내)
 * 30분            약 5.4 MB        약 7.2 MB            불가 (5MB 한도 초과)
 *
 * ※ 위 용량은 24kbps 녹음 기준의 예상치이며 실제 용량은
 *    코덱, 브라우저, 녹음 환경에 따라 달라질 수 있습니다.
 * ※ Base64 Data URL은 원본 Blob 대비 약 33% 정도 크기가 증가합니다.
 * ============================================================
 */
const DEFAULT_MAX_DURATION_MS = 10 * 60 * 1000; // 10분

export const STATE = Object.freeze({
  IDLE: 'idle',
  RECORDING: 'recording',
  PAUSED: 'paused',
  STOPPED: 'stopped',
  PLAYING: 'playing',
  PLAYING_PAUSED: 'playing-paused',
});

export class WebRecorder extends EventTarget {
  constructor(options = {}) {
    super();
    this._opts = options;
    this._platform = detectMimeType(options.mimeType);
    this._audioBitsPerSecond = options.audioBitsPerSecond ?? 24000;
    // maxDurationMs: undefined → DEFAULT_MAX_DURATION_MS 적용, 0/null 명시 → 무제한, 양수 → 그 값
    {
      const raw = options.maxDurationMs;
      if (raw === 0 || raw === null) {
        this._maxDurationMs = null;
      } else if (typeof raw === 'number' && raw > 0) {
        this._maxDurationMs = raw;
      } else {
        this._maxDurationMs = DEFAULT_MAX_DURATION_MS;
      }
    }
    this._state = STATE.IDLE;

    this._stream = null;
    this._recorder = null;
    this._chunks = [];
    this._mediaData = '';       // OZ 호환 Base64 Data URL
    this._recordStartTs = 0;    // 현재 세그먼트 시작 시각 (performance.now)
    this._recordAccumMs = 0;    // pause 누적
    this._recordTimer = null;
    this._maxDurationTimer = null;

    this._audio = null;
    this._audioHandlers = null;
    this._playbackBlobUrl = null;
    this._playTimer = null;
  }

  // -------- 상태/이벤트 헬퍼 --------
  _setState(next) {
    if (this._state === next) return;
    this._state = next;
    this.dispatchEvent(new CustomEvent('statechange', { detail: { state: next } }));
  }

  _emit(type, detail) {
    this.dispatchEvent(new CustomEvent(type, { detail }));
  }

  _emitError(code, message, cause) {
    this._emit('error', { code, message, cause });
  }

  getState() { return this._state; }
  getMimeInfo() {
    return {
      ...this._platform,
      audioBitsPerSecond: this._audioBitsPerSecond,
      maxDurationMs: this._maxDurationMs,
    };
  }

  // -------- 녹음 --------
  async start() {
    if (this._state === STATE.RECORDING || this._state === STATE.PAUSED) {
      throw new Error('이미 녹음 중입니다.');
    }
    if (this._state === STATE.PLAYING || this._state === STATE.PLAYING_PAUSED) {
      this.stopPlay();
    }

    // 기존 데이터 초기화 (OZ 와 동일하게 새 녹음은 이전 데이터를 덮어씀)
    this._mediaData = '';
    this._chunks = [];
    this._recordAccumMs = 0;

    let stream;
    try {
      stream = await navigator.mediaDevices.getUserMedia({ audio: true });
    } catch (err) {
      this._emitError('PERMISSION_DENIED', '마이크 권한이 거부되었습니다.', err);
      throw err;
    }
    this._stream = stream;

    let recorder;
    try {
      const mrOpts = {};
      if (this._platform.mimeType) mrOpts.mimeType = this._platform.mimeType;
      if (this._audioBitsPerSecond) mrOpts.audioBitsPerSecond = this._audioBitsPerSecond;
      recorder = new MediaRecorder(stream, mrOpts);
    } catch (err) {
      this._releaseStream();
      this._emitError('RECORDER_INIT_FAILED', 'MediaRecorder 생성 실패', err);
      throw err;
    }
    this._recorder = recorder;

    recorder.ondataavailable = (e) => {
      if (e.data && e.data.size > 0) this._chunks.push(e.data);
    };
    recorder.onerror = (e) => {
      this._emitError('RECORDER_ERROR', 'MediaRecorder 오류', e.error || e);
    };

    return new Promise((resolve) => {
      recorder.onstart = () => {
        this._recordStartTs = performance.now();
        this._startRecordTimer();
        this._startMaxDurationTimer(this._maxDurationMs);
        this._setState(STATE.RECORDING);
        resolve();
      };
      recorder.start();
    });
  }

  async pause() {
    if (this._state !== STATE.RECORDING) {
      throw new Error('녹음 중일 때만 일시정지할 수 있습니다.');
    }
    return new Promise((resolve) => {
      this._recorder.onpause = () => {
        this._recordAccumMs += performance.now() - this._recordStartTs;
        this._stopRecordTimer();
        this._clearMaxDurationTimer();
        this._setState(STATE.PAUSED);
        resolve();
      };
      this._recorder.pause();
    });
  }

  async resume() {
    if (this._state !== STATE.PAUSED) {
      throw new Error('일시정지 상태에서만 재개할 수 있습니다.');
    }
    return new Promise((resolve) => {
      this._recorder.onresume = () => {
        this._recordStartTs = performance.now();
        this._startRecordTimer();
        if (this._maxDurationMs !== null && this._maxDurationMs > 0) {
          const remain = this._maxDurationMs - this._recordAccumMs;
          this._startMaxDurationTimer(remain);
        }
        this._setState(STATE.RECORDING);
        resolve();
      };
      this._recorder.resume();
    });
  }

  async stop() {
    if (this._state !== STATE.RECORDING && this._state !== STATE.PAUSED) {
      throw new Error('녹음 중이 아닙니다.');
    }
    return new Promise((resolve, reject) => {
      const recorder = this._recorder;
      recorder.onstop = async () => {
        if (this._state === STATE.RECORDING) {
          this._recordAccumMs += performance.now() - this._recordStartTs;
        }
        this._stopRecordTimer();
        this._clearMaxDurationTimer();
        this._releaseStream();
        this._recorder = null;

        try {
          const blob = new Blob(this._chunks, { type: this._platform.mimeType || 'audio/webm' });
          const dataUrl = await blobToDataUrl(blob);
          this._mediaData = normalizeDataUrlMime(dataUrl, this._platform.ozMime);
          this._setState(STATE.STOPPED);
          resolve(this._mediaData);
        } catch (err) {
          this._emitError('ENCODE_FAILED', '녹음 데이터 인코딩 실패', err);
          reject(err);
        }
      };
      recorder.stop();
    });
  }

  // -------- 재생 --------
  async play() {
    if (this._state === STATE.RECORDING || this._state === STATE.PAUSED) {
      throw new Error('녹음 중에는 재생할 수 없습니다.');
    }
    if (!this._mediaData) {
      throw new Error('재생할 녹음 데이터가 없습니다.');
    }
    if (this._state === STATE.PLAYING_PAUSED && this._audio) {
      await this._audio.play();
      this._startPlayTimer();
      this._setState(STATE.PLAYING);
      return;
    }
    // 새 재생
    this._releaseAudio();
    // Safari 호환: Data URL 을 Blob URL 로 변환 (audio/m4a → audio/mp4 매핑)
    let playSrc = this._mediaData;
    if (this._mediaData.startsWith('data:')) {
      try {
        const blob = dataUrlToBlob(this._mediaData);
        playSrc = URL.createObjectURL(blob);
        this._playbackBlobUrl = playSrc;
      } catch (err) {
        this._emitError('PLAY_FAILED', '재생 데이터 변환 실패', err);
        throw err;
      }
    }
    const audio = new Audio(playSrc);
    this._audio = audio;
    const onEnded = () => {
      this._stopPlayTimer();
      this._emit('playend', { durationMs: (audio.duration || 0) * 1000 });
      this._setState(STATE.STOPPED);
    };
    const onError = () => {
      this._stopPlayTimer();
      this._emitError('PLAY_FAILED', '재생 실패', audio.error);
      this._setState(STATE.STOPPED);
    };
    audio.addEventListener('ended', onEnded);
    audio.addEventListener('error', onError);
    this._audioHandlers = { onEnded, onError };
    await audio.play();
    this._startPlayTimer();
    this._setState(STATE.PLAYING);
  }

  pausePlay() {
    if (this._state !== STATE.PLAYING || !this._audio) return;
    this._audio.pause();
    this._stopPlayTimer();
    this._setState(STATE.PLAYING_PAUSED);
  }

  stopPlay() {
    if (!this._audio) return;
    this._audio.pause();
    this._audio.currentTime = 0;
    this._stopPlayTimer();
    this._releaseAudio();
    if (this._state === STATE.PLAYING || this._state === STATE.PLAYING_PAUSED) {
      this._setState(this._mediaData ? STATE.STOPPED : STATE.IDLE);
    }
  }

  // -------- 데이터 입출력 --------
  getMediaData() {
    return this._mediaData || '';
  }

  setMediaData(dataUrl) {
    if (this._state === STATE.RECORDING || this._state === STATE.PAUSED) {
      throw new Error('녹음 중에는 외부 데이터를 설정할 수 없습니다.');
    }
    this.stopPlay();
    if (!dataUrl || typeof dataUrl !== 'string') {
      this._mediaData = '';
      this._setState(STATE.IDLE);
      return;
    }
    if (!dataUrl.startsWith('data:')) {
      throw new Error('Data URL 형식이 아닙니다. (data:audio/...;base64,...)');
    }
    this._mediaData = dataUrl;
    this._setState(STATE.STOPPED);
  }

  // -------- 삭제 / 해제 --------
  clear() {
    this.stopPlay();
    if (this._state === STATE.RECORDING || this._state === STATE.PAUSED) {
      // 진행 중이면 onstop/onerror 핸들러 먼저 떼서 강제 stop 후 콜백 흐름 차단
      if (this._recorder) {
        this._recorder.onstop = null;
        this._recorder.onerror = null;
        this._recorder.ondataavailable = null;
        try { this._recorder.stop(); } catch (_) { /* ignore */ }
      }
      this._releaseStream();
      this._recorder = null;
      this._stopRecordTimer();
      this._clearMaxDurationTimer();
    }
    this._mediaData = '';
    this._chunks = [];
    this._recordAccumMs = 0;
    this._setState(STATE.IDLE);
  }

  dispose() {
    this.clear();
    this._releaseAudio();
  }

  // -------- 시간/진행률 --------
  getRecordDurationMs() {
    if (this._state === STATE.RECORDING) {
      return this._recordAccumMs + (performance.now() - this._recordStartTs);
    }
    return this._recordAccumMs;
  }

  getPlayDurationMs() {
    return this._audio ? (this._audio.duration || 0) * 1000 : 0;
  }

  getPlayCurrentMs() {
    return this._audio ? this._audio.currentTime * 1000 : 0;
  }

  // -------- 내부 --------
  _startRecordTimer() {
    this._stopRecordTimer();
    this._recordTimer = setInterval(() => {
      const elapsedMs = this.getRecordDurationMs();
      const maxMs = this._maxDurationMs;
      const ratio = maxMs ? Math.min(1, elapsedMs / maxMs) : 0;
      const remainingMs = maxMs ? Math.max(0, maxMs - elapsedMs) : null;
      this._emit('recordtime', { elapsedMs, maxDurationMs: maxMs, ratio, remainingMs });
    }, 100);
  }

  _stopRecordTimer() {
    if (this._recordTimer) {
      clearInterval(this._recordTimer);
      this._recordTimer = null;
    }
  }

  _startMaxDurationTimer(remainMs) {
    this._clearMaxDurationTimer();
    if (!remainMs || remainMs <= 0) {
      if (this._maxDurationMs && remainMs !== null) {
        // 이미 한도 초과 — 즉시 종료
        Promise.resolve().then(() => {
          if (this._state === STATE.RECORDING || this._state === STATE.PAUSED) {
            this._emit('maxduration', { reachedMs: this._maxDurationMs });
            this.stop().catch(() => {});
          }
        });
      }
      return;
    }
    this._maxDurationTimer = setTimeout(() => {
      this._maxDurationTimer = null;
      if (this._state === STATE.RECORDING) {
        this._emit('maxduration', { reachedMs: this._maxDurationMs });
        this.stop().catch(() => {});
      }
    }, remainMs);
  }

  _clearMaxDurationTimer() {
    if (this._maxDurationTimer) {
      clearTimeout(this._maxDurationTimer);
      this._maxDurationTimer = null;
    }
  }

  _startPlayTimer() {
    this._stopPlayTimer();
    this._playTimer = setInterval(() => {
      const cur = this.getPlayCurrentMs();
      const dur = this.getPlayDurationMs();
      const ratio = dur > 0 ? Math.min(1, cur / dur) : 0;
      this._emit('playprogress', { currentMs: cur, durationMs: dur, ratio });
    }, 100);
  }

  _stopPlayTimer() {
    if (this._playTimer) {
      clearInterval(this._playTimer);
      this._playTimer = null;
    }
  }

  _releaseStream() {
    if (this._stream) {
      this._stream.getTracks().forEach((t) => t.stop());
      this._stream = null;
    }
  }

  _releaseAudio() {
    if (this._audio) {
      // src 비우면 audio 가 'error' 이벤트를 발생시키므로 핸들러를 먼저 제거
      if (this._audioHandlers) {
        this._audio.removeEventListener('ended', this._audioHandlers.onEnded);
        this._audio.removeEventListener('error', this._audioHandlers.onError);
        this._audioHandlers = null;
      }
      this._audio.pause();
      this._audio.removeAttribute('src');
      try { this._audio.load(); } catch (_) { /* ignore */ }
      this._audio = null;
    }
    if (this._playbackBlobUrl) {
      URL.revokeObjectURL(this._playbackBlobUrl);
      this._playbackBlobUrl = null;
    }
  }
}

function blobToDataUrl(blob) {
  return new Promise((resolve, reject) => {
    const fr = new FileReader();
    fr.onload = () => resolve(fr.result);
    fr.onerror = () => reject(fr.error || new Error('FileReader error'));
    fr.readAsDataURL(blob);
  });
}

// Base64 Data URL 을 Blob 으로 변환.
// Safari 는 'audio/m4a' MIME 을 인식 못 하므로 'audio/mp4' 로 매핑.
function dataUrlToBlob(dataUrl) {
  const base64Idx = dataUrl.indexOf(';base64,');
  if (base64Idx < 0) throw new Error('Base64 Data URL 형식이 아닙니다.');
  const head = dataUrl.substring(5, base64Idx); // "audio/xxx" 또는 "audio/xxx;codecs=yyy"
  const semi = head.indexOf(';');
  let mime = semi < 0 ? head : head.substring(0, semi);
  if (mime === 'audio/m4a') mime = 'audio/mp4';
  const base64 = dataUrl.substring(base64Idx + ';base64,'.length);
  const binary = atob(base64);
  // Uint8Array.from + 매핑은 for 루프보다 JIT 최적화에 유리하고 큰 데이터에서 빠름
  const bytes = Uint8Array.from(binary, (ch) => ch.charCodeAt(0));
  return new Blob([bytes], { type: mime });
}
