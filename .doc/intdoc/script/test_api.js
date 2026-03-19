/**
 * MSF API 구현 검증 테스트
 * 실행: node test_api.js
 *
 * 테스트 대상 (SERVER_NAME=LOCAL Mock):
 *   1. 부가서비스 조회 (X20)
 *   2. 번호변경 번호조회 (NU1)
 *   3. USIM 유효성 체크 (X85)
 *   4. 단말보험 상품 목록 (DB링크 Mock)
 *   5. 단말보험 가입가능여부 (Y24)
 *   6. 명의변경 사전체크 (MC0)
 *   7. 서비스해지 잔여요금 조회 (X18)
 *   8. 공통 가입자 인증 (Y04+X01)
 */

const http = require('http');

const BASE = { hostname: 'localhost', port: 8081 };
const DUMMY = { ncn: '123456789', ctn: '01012345678', custId: 'CUST001' };

let pass = 0, fail = 0;

function post(path, body) {
    return new Promise((resolve, reject) => {
        const payload = JSON.stringify(body);
        const req = http.request(
            { ...BASE, path, method: 'POST',
              headers: { 'Content-Type': 'application/json; charset=utf-8',
                         'Content-Length': Buffer.byteLength(payload) } },
            res => {
                let d = '';
                res.on('data', c => d += c);
                res.on('end', () => {
                    try { resolve({ status: res.statusCode, body: JSON.parse(d) }); }
                    catch(e) { resolve({ status: res.statusCode, body: d }); }
                });
            }
        );
        req.on('error', reject);
        req.write(payload);
        req.end();
    });
}

function check(name, res, expectFn) {
    try {
        const ok = expectFn(res);
        if (ok) {
            console.log(`  ✅ PASS  ${name}`);
            pass++;
        } else {
            console.log(`  ❌ FAIL  ${name}`);
            console.log(`         응답: ${JSON.stringify(res.body).substring(0, 200)}`);
            fail++;
        }
    } catch(e) {
        console.log(`  ❌ ERROR ${name}: ${e.message}`);
        fail++;
    }
}

async function run() {
    console.log('=== MSF API 구현 검증 테스트 ===\n');

    // 1. 공통 가입자 인증 (Y04 + X01)
    // 경로: /api/v1/join-info (도메인 없는 공통 엔드포인트)
    console.log('[1] 공통 — 가입자 인증 (join-info)');
    let r = await post('/api/v1/join-info', { name: '홍길동', mobileNo: '01012345678' });
    check('join-info 200 응답', r, r => r.status === 200);
    check('join-info success 필드', r, r => r.body.success !== undefined);

    // 2. 부가서비스 조회 (X20)
    console.log('\n[2] 서비스변경 — 부가서비스 조회 (X20)');
    r = await post('/api/v1/addition/current', DUMMY);
    check('addition/current 200', r, r => r.status === 200);
    check('items 배열 존재', r, r => Array.isArray(r.body.items));

    // 3. 번호변경 번호조회 (NU1)
    console.log('\n[3] 번호변경 — 희망번호 조회 (NU1)');
    r = await post('/api/v1/service-change/number/search',
        { ...DUMMY, wantNo: '1234' });
    check('number/search 200', r, r => r.status === 200);
    check('배열 반환', r, r => Array.isArray(r.body));

    // 4. 번호변경 예약 (NU2)
    console.log('\n[4] 번호변경 — 희망번호 예약 (NU2)');
    r = await post('/api/v1/service-change/number/reserve',
        { ...DUMMY, tlphNo: '01012341234' });
    check('number/reserve 200', r, r => r.status === 200);
    check('success: true', r, r => r.body.success === true);

    // 5. USIM 유효성 체크 (X85)
    console.log('\n[5] USIM 변경 — 유효성 체크 (X85)');
    r = await post('/api/v1/service-change/usim/check',
        { ...DUMMY, usimNo: '8982000012345678901' });
    check('usim/check 200', r, r => r.status === 200);
    check('success: true', r, r => r.body.success === true);
    check('usimNo 반환', r, r => !!r.body.usimNo);

    // 6. USIM 변경 처리 (X85 → UC0)
    console.log('\n[6] USIM 변경 — 변경 처리 (X85→UC0)');
    r = await post('/api/v1/service-change/usim/change',
        { ...DUMMY, newUsimNo: '8982000012345678901', currentUsimNo: '8982000099999999901' });
    check('usim/change 200', r, r => r.status === 200);
    check('success: true', r, r => r.body.success === true);

    // 7. 단말보험 상품 목록 (DB링크 Mock)
    console.log('\n[7] 단말보험 — 상품 목록 (MSP_INTM_INSR_MST Mock)');
    r = await post('/api/v1/service-change/insurance/list',
        { reqBuyType: 'UU' });
    check('insurance/list 200', r, r => r.status === 200);
    check('success: true', r, r => r.body.success === true);
    check('items 배열 존재', r, r => Array.isArray(r.body.items) && r.body.items.length > 0);

    // 8. 단말보험 가입 가능 여부 (Y24)
    console.log('\n[8] 단말보험 — 가입 가능 여부 (Y24)');
    r = await post('/api/v1/service-change/insurance/eligibility',
        { ...DUMMY, soc: 'INSR001' });
    check('insurance/eligibility 200', r, r => r.status === 200);
    check('eligible: true', r, r => r.body.eligible === true);

    // 9. 명의변경 사전체크 (MC0)
    console.log('\n[9] 명의변경 — 사전체크 (MC0)');
    r = await post('/api/v1/ident/eligible', DUMMY);
    check('ident/eligible 200', r, r => r.status === 200);
    check('eligible: true (LOCAL Mock)', r, r => r.body.eligible === true);
    check('success: true', r, r => r.body.success === true);

    // 10. 명의변경 연락처 검증
    // 응답 필드: resultCode("SUCCESS"|"0001"|"0002"|"0003"), resultMsg
    console.log('\n[10] 명의변경 — 연락처 검증 (checkTelNo)');
    r = await post('/api/v1/ident/check-tel-no',
        { chkTelType: 'NOR', grantorPhone: '01012345678', etcMobile: '01099999999' });
    check('check-tel-no 200', r, r => r.status === 200);
    check('동일번호 아닐 때 resultCode=SUCCESS', r, r => r.body.resultCode === 'SUCCESS');

    // 11. 명의변경 연락처 검증 — 동일번호 거부
    console.log('\n[11] 명의변경 — 연락처 동일번호 거부');
    r = await post('/api/v1/ident/check-tel-no',
        { chkTelType: 'NOR', grantorPhone: '01012345678', etcMobile: '01012345678' });
    check('check-tel-no 200', r, r => r.status === 200);
    check('동일번호 → resultCode≠SUCCESS', r, r => r.body.resultCode !== 'SUCCESS');
    check('에러 메시지 포함', r, r => !!r.body.resultMsg);

    // 12. 서비스해지 잔여요금 (X18)
    console.log('\n[12] 서비스해지 — 잔여요금 조회 (X18)');
    r = await post('/api/v1/cancel/remain-charge', DUMMY);
    check('cancel/remain-charge 200', r, r => r.status === 200);
    check('success: true', r, r => r.body.success === true);
    check('remainCharge 숫자 반환', r, r => typeof r.body.remainCharge === 'number');

    // 13. 서비스해지 가능 여부
    console.log('\n[13] 서비스해지 — 해지 가능 여부');
    r = await post('/api/v1/cancel/eligible', DUMMY);
    check('cancel/eligible 200', r, r => r.status === 200);
    check('eligible: true', r, r => r.body.eligible === true);

    // 결과 요약
    console.log('\n' + '='.repeat(40));
    console.log(`결과: ${pass} 통과 / ${fail} 실패 / 총 ${pass + fail}개`);
    if (fail === 0) console.log('✅ 전체 통과');
    else console.log('❌ 일부 실패 — 상세 내용 확인 필요');
}

run().catch(e => { console.error('실행 오류:', e.message); process.exit(1); });
