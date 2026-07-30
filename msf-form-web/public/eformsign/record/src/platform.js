// OS / MIME 분기 유틸
// OZMediaRecorder.ts 의 ozenv.web.iOS/mac 분기와 동일한 결과를 내도록 매핑한다.
// iOS/macOS  -> audio/mp4 (확장자 m4a, OZ 호환 라벨 audio/m4a)
// 그 외       -> audio/webm;codecs=opus  (OZ 호환 라벨 audio/webm)

const APPLE_UA_RE = /iPad|iPhone|iPod|Macintosh/;

function isAppleUA(ua) {
  return APPLE_UA_RE.test(ua || '');
}

function pickSupported(candidates) {
  if (typeof MediaRecorder === 'undefined' || !MediaRecorder.isTypeSupported) {
    return candidates[0] || '';
  }
  for (const t of candidates) {
    if (MediaRecorder.isTypeSupported(t)) return t;
  }
  return '';
}

export function detectMimeType(userOverride) {
  if (userOverride) {
    return {
      mimeType: userOverride,
      ext: userOverride.includes('mp4') || userOverride.includes('m4a') ? 'm4a' : 'webm',
      ozMime: userOverride.includes('mp4') || userOverride.includes('m4a') ? 'audio/m4a' : 'audio/webm',
    };
  }

  const ua = (typeof navigator !== 'undefined' && navigator.userAgent) || '';
  if (isAppleUA(ua)) {
    const mimeType = pickSupported(['audio/mp4', 'audio/mp4;codecs=mp4a.40.2', 'audio/aac']) || 'audio/mp4';
    return { mimeType, ext: 'm4a', ozMime: 'audio/m4a' };
  }

  const mimeType = pickSupported([
    'audio/webm;codecs=opus',
    'audio/webm',
    'audio/ogg;codecs=opus',
  ]) || 'audio/webm';
  return { mimeType, ext: 'webm', ozMime: 'audio/webm' };
}

// Data URL 헤더가 OZ가 기대하는 라벨(audio/webm 또는 audio/m4a)이 되도록 재작성한다.
// 브라우저는 보통 audio/webm;codecs=opus 형태로 헤더를 만들기 때문에 codecs 부분을 제거하고,
// audio/mp4 -> audio/m4a 로 매핑한다.
export function normalizeDataUrlMime(dataUrl, ozMime) {
  if (!dataUrl) return dataUrl;
  const semi = dataUrl.indexOf(';base64,');
  if (semi < 0) return dataUrl;
  const head = dataUrl.substring(0, semi); // ex) data:audio/webm or data:audio/webm;codecs=opus
  const rest = dataUrl.substring(semi);    // ;base64,xxxxx
  // head 의 첫 ';' 이전까지를 잘라 ozMime 으로 교체
  const firstSemi = head.indexOf(';');
  const stripped = firstSemi < 0 ? head : head.substring(0, firstSemi);
  // stripped: data:audio/webm
  return 'data:' + ozMime + rest;
}
