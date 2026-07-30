// WebRecorder 샘플 UI 컴포넌트
// 실 운영에서는 사용하지 않음 (테스트/데모 용도).
// container 에 상단 bar UI 를 주입하고 recorder 이벤트를 구독해 갱신.

import { STATE } from './WebRecorder.js';

const TPL = `
<div class="wr-bar">
  <div class="wr-group">
    <button class="wr-rec" data-act="start" title="녹음 시작">● 시작</button>
    <button data-act="pause" title="일시정지">⏸</button>
    <button data-act="resume" title="재개">▶</button>
    <button data-act="stop" title="중지">■</button>
  </div>
  <div class="wr-group">
    <span class="wr-label">녹음</span>
    <span class="wr-time" data-el="recTime">00:00</span>
  </div>
  <div class="wr-group">
    <button class="wr-play" data-act="play" title="재생">▶ 재생</button>
    <button data-act="pausePlay" title="재생 일시정지">⏸</button>
    <button data-act="stopPlay" title="재생 중지">■</button>
  </div>
  <div class="wr-progress"><div class="wr-progress-fill" data-el="progFill"></div></div>
  <span class="wr-play-time" data-el="playTime">00:00 / 00:00</span>
  <div class="wr-group">
    <button data-act="clear" title="삭제">🗑 삭제</button>
    <button data-act="toggleData" title="데이터 패널">{ } 데이터</button>
  </div>
  <span class="wr-state" data-el="state">IDLE</span>
</div>
<div class="wr-data-panel" data-el="dataPanel">
  <div class="wr-label" style="margin-bottom:4px;">현재 녹음 데이터 (Data URL)</div>
  <textarea data-el="dataText" placeholder="녹음 후 여기에 데이터가 표시됩니다. 외부 Data URL 을 붙여넣고 'set' 을 누르면 재생할 수 있습니다."></textarea>
  <div class="wr-data-actions">
    <button data-act="refreshData">현재 데이터 가져오기</button>
    <button data-act="setData">외부 데이터 set</button>
    <button data-act="copyData">클립보드 복사</button>
    <span class="wr-label" data-el="dataInfo"></span>
  </div>
</div>
`;

const fmt = (ms) => {
  if (!isFinite(ms) || ms < 0) ms = 0;
  const totalSec = Math.floor(ms / 1000);
  const mm = String(Math.floor(totalSec / 60)).padStart(2, '0');
  const ss = String(totalSec % 60).padStart(2, '0');
  return `${mm}:${ss}`;
};

export function mountWebRecorderUI(container, recorder) {
  if (!container) throw new Error('container 가 필요합니다.');
  if (!recorder) throw new Error('recorder 인스턴스가 필요합니다.');

  container.innerHTML = TPL;
  const $ = (sel) => container.querySelector(sel);
  const recTimeEl = $('[data-el="recTime"]');
  const playTimeEl = $('[data-el="playTime"]');
  const progFillEl = $('[data-el="progFill"]');
  const stateEl = $('[data-el="state"]');
  const dataPanel = $('[data-el="dataPanel"]');
  const dataText = $('[data-el="dataText"]');
  const dataInfo = $('[data-el="dataInfo"]');

  const btn = (act) => container.querySelector(`[data-act="${act}"]`);

  // ---- 버튼 상태 갱신 ----
  function refreshButtons(state) {
    const s = state || recorder.getState();
    const hasData = !!recorder.getMediaData();
    const map = {
      start:     s === STATE.IDLE || s === STATE.STOPPED,
      pause:     s === STATE.RECORDING,
      resume:    s === STATE.PAUSED,
      stop:      s === STATE.RECORDING || s === STATE.PAUSED,
      play:      hasData && (s === STATE.STOPPED || s === STATE.IDLE || s === STATE.PLAYING_PAUSED),
      pausePlay: s === STATE.PLAYING,
      stopPlay:  s === STATE.PLAYING || s === STATE.PLAYING_PAUSED,
      clear:     hasData || s === STATE.RECORDING || s === STATE.PAUSED,
    };
    for (const [act, enabled] of Object.entries(map)) {
      const b = btn(act);
      if (b) b.disabled = !enabled;
    }

    stateEl.textContent = s.toUpperCase();
    stateEl.className = 'wr-state';
    if (s === STATE.RECORDING) stateEl.classList.add('is-recording');
    else if (s === STATE.PAUSED) stateEl.classList.add('is-paused');
    else if (s === STATE.STOPPED) stateEl.classList.add('is-stopped');
    else if (s === STATE.PLAYING || s === STATE.PLAYING_PAUSED) stateEl.classList.add('is-playing');
  }

  // progress width 갱신 throttle: 직전 비율과 0.5%p 미만이면 DOM 쓰기 skip (리플로우 절감)
  let lastProgRatio = -1;
  const PROG_DELTA = 0.005;
  const setProg = (ratio) => {
    if (Math.abs(ratio - lastProgRatio) < PROG_DELTA) return;
    progFillEl.style.width = (ratio * 100).toFixed(1) + '%';
    lastProgRatio = ratio;
  };
  const resetProg = () => {
    progFillEl.style.width = '0%';
    lastProgRatio = -1;
  };

  // ---- 이벤트 바인딩 ----
  recorder.addEventListener('statechange', (e) => {
    refreshButtons(e.detail.state);
    if (e.detail.state === STATE.STOPPED) {
      // 녹음 종료 시 데이터 자동 표시
      if (dataPanel.classList.contains('is-open')) loadCurrentData();
    }
  });
  recorder.addEventListener('recordtime', (e) => {
    const { elapsedMs, maxDurationMs, ratio } = e.detail;
    recTimeEl.textContent = fmt(elapsedMs);
    if (maxDurationMs) {
      setProg(ratio);
      playTimeEl.textContent = `${fmt(elapsedMs)} / ${fmt(maxDurationMs)}`;
    } else {
      // 무제한: 진행률 없음. 경과 시간만 표시
      playTimeEl.textContent = `${fmt(elapsedMs)} / --:--`;
    }
  });
  recorder.addEventListener('playprogress', (e) => {
    const { currentMs, durationMs, ratio } = e.detail;
    playTimeEl.textContent = `${fmt(currentMs)} / ${fmt(durationMs)}`;
    setProg(ratio);
  });
  recorder.addEventListener('playend', () => {
    progFillEl.style.width = '100%';
    lastProgRatio = 1;
    setTimeout(resetProg, 400);
  });
  recorder.addEventListener('maxduration', (e) => {
    const sec = Math.round((e.detail.reachedMs || 0) / 1000);
    dataInfo.textContent = `최대 녹음 시간(${sec}s) 도달 - 자동 종료`;
  });
  recorder.addEventListener('error', (e) => {
    console.error('[WebRecorder]', e.detail);
    alert(`오류: ${e.detail.message}`);
  });

  // ---- 액션 바인딩 ----
  const actions = {
    start: async () => {
      recTimeEl.textContent = '00:00';
      playTimeEl.textContent = '00:00 / 00:00';
      resetProg();
      try { await recorder.start(); } catch (_) { /* error 이벤트로 알림 */ }
    },
    pause: async () => {
      try { await recorder.pause(); } catch (_) { /* error 이벤트로 알림 */ }
    },
    resume: async () => {
      try { await recorder.resume(); } catch (_) { /* error 이벤트로 알림 */ }
    },
    stop: async () => {
      try { await recorder.stop(); } catch (_) { /* error 이벤트로 알림 */ }
    },
    play: async () => {
      try { await recorder.play(); } catch (err) { console.error(err); alert(err.message); }
    },
    pausePlay: () => recorder.pausePlay(),
    stopPlay: () => {
      recorder.stopPlay();
      resetProg();
      playTimeEl.textContent = '00:00 / 00:00';
    },
    clear: () => {
      recorder.clear();
      recTimeEl.textContent = '00:00';
      playTimeEl.textContent = '00:00 / 00:00';
      resetProg();
      dataText.value = '';
      dataInfo.textContent = '';
    },
    toggleData: () => {
      dataPanel.classList.toggle('is-open');
      if (dataPanel.classList.contains('is-open')) loadCurrentData();
    },
    refreshData: () => loadCurrentData(),
    setData: () => {
      try {
        recorder.setMediaData(dataText.value.trim());
        dataInfo.textContent = '외부 데이터 set 완료. 재생 가능';
        refreshButtons();
      } catch (err) {
        alert(err.message);
      }
    },
    copyData: async () => {
      const data = recorder.getMediaData();
      if (!data) { dataInfo.textContent = '복사할 데이터가 없습니다.'; return; }
      try {
        await navigator.clipboard.writeText(data);
        dataInfo.textContent = `복사 완료 (${data.length.toLocaleString()} chars)`;
      } catch (err) {
        dataInfo.textContent = '복사 실패: ' + err.message;
      }
    },
  };

  function loadCurrentData() {
    const data = recorder.getMediaData();
    dataText.value = data;
    const mime = recorder.getMimeInfo();
    if (data) {
      const head = data.substring(0, 32);
      dataInfo.textContent = `${head}... (${data.length.toLocaleString()} chars, ${mime.ozMime})`;
    } else {
      dataInfo.textContent = `데이터 없음 (예상 포맷: ${mime.ozMime})`;
    }
  }

  container.addEventListener('click', (e) => {
    const target = e.target.closest('[data-act]');
    if (!target || target.disabled) return;
    const act = target.getAttribute('data-act');
    const fn = actions[act];
    if (fn) fn();
  });

  refreshButtons();

  return {
    refresh: () => refreshButtons(),
    destroy: () => { container.innerHTML = ''; },
  };
}
