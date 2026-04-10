/**
 * RosisIT eSonicE2EE ECMAScript Module
 * Copyright 2025 RosisIT, All Rights Reserved.
 * @version 1.0.3
 */

let url;
let clientInfo;
let eSession;

const ENC = new TextEncoder()
const DEC = new TextDecoder();

/*const b64u = {
    enc(ab) {
        return btoa(String.fromCharCode(...new Uint8Array(ab)))
            .replace(/\+/g, '-')
            .replace(/\//g, '_')
            .replace(/=+$/, '');
    },
    dec(s) {
        s = s.replace(/-/g, '+').replace(/_/g, '/');
        s += '==='.slice((s.length + 3) % 4);
        const bin = atob(s);
        const u8 = new Uint8Array(bin.length);
        for (let i = 0; i < bin.length; i++) {
            u8[i] = bin.charCodeAt(i);
        }
        return u8.buffer;
    }
};*/

const b64u = {
    enc(ab) {
        // ab: ArrayBuffer | Uint8Array
        const u8 = ab instanceof Uint8Array
            ? ab
            : (ab instanceof ArrayBuffer ? new Uint8Array(ab) : new Uint8Array(0));

        // 큰 배열을 청크로 나눠 문자열 생성 (스택 안전)
        let bin = '';
        const CHUNK = 0x8000; // 32KB
        for (let i = 0; i < u8.length; i += CHUNK) {
            const end = i + CHUNK > u8.length ? u8.length : i + CHUNK;
            bin += String.fromCharCode.apply(null, u8.subarray(i, end));
        }
        return btoa(bin)
            .replace(/\+/g, '-')
            .replace(/\//g, '_')
            .replace(/=+$/, '');
    },

    dec(s) {
        s = (s || '').replace(/-/g, '+').replace(/_/g, '/');
        s += '==='.slice((s.length + 3) % 4);
        const bin = atob(s);
        const u8 = new Uint8Array(bin.length);
        for (let i = 0; i < bin.length; i++) u8[i] = bin.charCodeAt(i);
        return u8.buffer;
    }
};

function lp(...parts) {
    let len = 0;
    for (const p of parts) {
        len += 4 + (p ? p.byteLength : 0);
    }
    const dv = new DataView(new ArrayBuffer(len));
    let off = 0;
    const put = (ab) => {
        const n = ab ? ab.byteLength : 0;
        dv.setUint32(off, n);
        off += 4;
        if (n) {
            new Uint8Array(dv.buffer, off, n).set(new Uint8Array(ab));
            off += n;
        }
    };
    for (const p of parts) {
        put(p);
    }
    return dv.buffer;
}

async function sha256(ab) {
    return await crypto.subtle.digest('SHA-256', ab);
}

async function genECDH() {
    return await crypto.subtle.generateKey({name: 'ECDH', namedCurve: 'P-256'}, true, ['deriveBits']);
}

async function expPubRawB64u(pub) {
    const raw = await crypto.subtle.exportKey('raw', pub);
    return b64u.enc(raw);
}

async function impPubRawB64u(b) {
    return await crypto.subtle.importKey('raw', b64u.dec(b), {name: 'ECDH', namedCurve: 'P-256'}, true, []);
}

async function deriveBits(priv, peerPub) {
    return await crypto.subtle.deriveBits({name: 'ECDH', public: peerPub}, priv, 256);
}

async function hkdfToAes(sharedBits, saltBuf, infoBuf, extractable = false) {
    const base = await crypto.subtle.importKey('raw', sharedBits, 'HKDF', false, ['deriveKey']);
    return await crypto.subtle.deriveKey({
        name: 'HKDF', hash: 'SHA-256', salt: saltBuf, info: infoBuf
    }, base, {name: 'AES-GCM', length: 256}, extractable, ['encrypt', 'decrypt']);
}


async function deriveChildKey(K_base_raw, reqIdBuf, type, keyUsages) {
    const info = ENC.encode(`e2ee-${type}-v1`).buffer;
    const hkdfBase = await crypto.subtle.importKey('raw', K_base_raw, 'HKDF', false, ['deriveKey']);
    return await crypto.subtle.deriveKey({name: 'HKDF', hash: 'SHA-256', salt: reqIdBuf, info}, hkdfBase, {
        name: 'AES-GCM', length: 256
    }, false, [keyUsages]);
}

/** AAD 생성 (양쪽 동일 바이트로 재구성 가능해야 함) */
function makeAad({protocol, handshakeId, reqIdBuf}) {
    return lp(ENC.encode(protocol).buffer, ENC.encode(handshakeId).buffer, reqIdBuf);
}

/** AES-GCM 암복호 */
async function aesGcmEncrypt(aesKey, plaintextBuf, aadBuf) {
    const iv = await crypto.getRandomValues(new Uint8Array(12));
    const ct = await crypto.subtle.encrypt({
        name: 'AES-GCM', iv, additionalData: aadBuf || undefined
    }, aesKey, plaintextBuf);
    return {iv, ciphertext: new Uint8Array(ct)}; // TAG 포함
}

async function aesGcmDecrypt(aesKey, ivU8, ciphertextU8, aadBuf) {
    const pt = await crypto.subtle.decrypt({
        name: 'AES-GCM', iv: ivU8, additionalData: aadBuf || undefined
    }, aesKey, ciphertextU8);
    return new Uint8Array(pt);
}

export async function initSession() {
    const kp = await genECDH();
    const cPubB64 = await expPubRawB64u(kp.publicKey);
    const cNonce = crypto.getRandomValues(new Uint8Array(16));
    const cNonceB64 = b64u.enc(cNonce.buffer);

    const resp = await fetch(url, {
        method: 'POST',
        mode: 'cors',
        credentials: 'include',
        headers: {'content-type': 'application/json'},
        cache: 'no-cache',
        keepalive: false,
        body: JSON.stringify({clientPubKey: cPubB64, clientNonce: cNonceB64, clientInfo})
    });

    if (!resp.ok) {
        throw new Error(`Handshake HTTP ${resp.status}`);
    }

    const R = await resp.json(); // {handshakeId, serverPubKey, serverNonce, ...}

    const sPub = await impPubRawB64u(R.serverPubKey);
    const shared = await deriveBits(kp.privateKey, sPub);

    // transcript = "e2ee-v1" | cPubRaw | sPubRaw | cNonce | sNonce  (전부 length-prefixed)
    const transcript = lp(ENC.encode('e2ee-v1').buffer, b64u.dec(cPubB64), b64u.dec(R.serverPubKey), b64u.dec(cNonceB64), b64u.dec(R.serverNonce));
    const tHash = await sha256(transcript);

    // K_base = HKDF(shared, salt=serverNonce, info=tHash) → AES-256
    const K_base = await hkdfToAes(shared, b64u.dec(R.serverNonce), tHash, /*extractable=*/true);
    const K_base_raw = await crypto.subtle.exportKey('raw', K_base); // 32B
    eSession = {
        handshakeId: R.handshakeId, K_base_raw: K_base_raw
    };
}

export async function execute(url, data) {
    const reqId = crypto.getRandomValues(new Uint8Array(16));
    const kReq = await deriveChildKey(eSession.K_base_raw, reqId.buffer, 'req', ['encrypt']);
    const aadReq = makeAad({
        protocol: 'e2ee-v1', handshakeId: eSession.handshakeId, reqIdBuf: reqId.buffer
    });

    const plain = new TextEncoder().encode((typeof data === 'string' || typeof data === 'number') ? String(data) : JSON.stringify(data));
    const {iv, ciphertext} = await aesGcmEncrypt(kReq, plain, aadReq);

    const bodyReq = {
        handshakeId: eSession.handshakeId,
        reqId: b64u.enc(reqId.buffer),
        iv: b64u.enc(iv.buffer),
        ct: b64u.enc(ciphertext.buffer)
    };

    const resp = await fetch(url, {
        method: 'POST',
        mode: 'cors',
        credentials: 'include',
        headers: {'content-type': 'application/json'},
        cache: 'no-cache',
        keepalive: false,
        body: JSON.stringify(bodyReq)
    });

    if (resp.status === 401) {
        return "eSession expired";
    }

    if (!resp.ok) {
        throw new Error(`HTTP ${resp}`);
    }

    const bodyResp = await resp.json(); // {handshakeId, reqId, iv, ct, contentType}
    if (!bodyResp?.iv || !bodyResp?.ct) {
        return bodyResp;
    }

    const reqIdBuf = bodyResp.reqId ? b64u.dec(bodyResp.reqId) : reqId.buffer;
    const kResp = await deriveChildKey(eSession.K_base_raw, reqId.buffer, 'resp', ['decrypt']);

    const aadResp = makeAad({
        protocol: 'e2ee-v1', handshakeId: eSession.handshakeId, reqIdBuf: reqIdBuf
    });

    const ivResp = new Uint8Array(b64u.dec(bodyResp.iv));
    const ctResp = new Uint8Array(b64u.dec(bodyResp.ct));
    const ptU8 = await aesGcmDecrypt(kResp, ivResp, ctResp, aadResp);

    const contentType = bodyResp.contentType || 'application/json';
    if (contentType.includes('application/json')) {
        return JSON.parse(new TextDecoder().decode(ptU8));
    } else if (contentType.startsWith('text/')) {
        return new TextDecoder().decode(ptU8);
    } else {
        return ptU8;
    }
}

export function setConfig(u, info) {
    url = u;
    clientInfo = info;
}

export function getSession() {
    return eSession;
}