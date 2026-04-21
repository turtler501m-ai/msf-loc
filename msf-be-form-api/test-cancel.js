/**
 * 서비스 해지 API 테스트
 * 엔드포인트:
 *   POST /remainCharge/list    - X18 잔여요금·위약금 조회
 *   POST /api/msf/formTermination/{applicationKey}/complete - 해지 신청
 */
const http = require('http');

const HOST = 'localhost';
const PORT = 8080;

function request(path, body) {
  return new Promise((resolve) => {
    const data = JSON.stringify(body);
    const options = {
      hostname: HOST,
      port: PORT,
      path,
      method: 'POST',
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
        'Content-Length': Buffer.byteLength(data),
      },
    };
    const req = http.request(options, (res) => {
      let raw = '';
      res.on('data', (c) => (raw += c));
      res.on('end', () => {
        let parsed;
        try { parsed = JSON.parse(raw); } catch { parsed = raw; }
        resolve({ status: res.statusCode, body: parsed });
      });
    });
    req.on('error', (e) => resolve({ status: 'ERROR', body: e.message }));
    req.write(data);
    req.end();
  });
}

async function run() {
  console.log('='.repeat(60));
  console.log('서비스 해지 API 테스트');
  console.log('='.repeat(60));

  // ── TEST 1: 잔여요금 조회 — ncn 없음 (필수값 오류)
  console.log('\n[TEST 1] /remainCharge/list — ncn 없음');
  let res = await request('/remainCharge/list', {});
  console.log('Status:', res.status);
  console.log('Response:', JSON.stringify(res.body, null, 2));

  // ── TEST 2: 잔여요금 조회 — ncn 전달 (세션 없음 → 세션 오류 예상)
  console.log('\n[TEST 2] /remainCharge/list — ncn 전달, 세션 없음');
  res = await request('/remainCharge/list', { ncn: 'NCN000001' });
  console.log('Status:', res.status);
  console.log('Response:', JSON.stringify(res.body, null, 2));

  // ── TEST 3: 해지 신청 — 빈 요청 (필수 검증)
  console.log('\n[TEST 3] /api/msf/formTermination/APP-TEST-001/complete — 빈 요청');
  res = await request('/api/msf/formTermination/APP-TEST-001/complete', {});
  console.log('Status:', res.status);
  console.log('Response:', JSON.stringify(res.body, null, 2));

  // ── TEST 4: 해지 신청 — customer 없음
  console.log('\n[TEST 4] /api/msf/formTermination/APP-TEST-001/complete — customer 없음');
  res = await request('/api/msf/formTermination/APP-TEST-001/complete', { product: {}, agreement: {} });
  console.log('Status:', res.status);
  console.log('Response:', JSON.stringify(res.body, null, 2));

  // ── TEST 5: 해지 신청 — 필수 필드 누락 (동의 없음)
  console.log('\n[TEST 5] /api/msf/formTermination/APP-TEST-001/complete — 동의 없음');
  res = await request('/api/msf/formTermination/APP-TEST-001/complete', {
    customer: {
      customerType: 'customerType1',
      userName: '홍길동',
      userBirthDate: '19900101',
      ncn: 'NCN000001',
      cancelPhone1: '010',
      cancelPhone2: '1234',
      cancelPhone3: '5678',
      afterTel1: '010',
      afterTel2: '9876',
      afterTel3: '5432',
      postMethod: 'postMethod1',
      agencyName: 'TEST_AGENCY',
    },
    product: {
      isActive: 'MM',
      usageFee: '15000',
      penaltyFee: '5000',
      finalAmount: '20000',
      remainPeriod: '6',
      remainAmount: '90000',
      memo: '테스트 해지 신청',
    },
    agreement: {
      agreeCheck1: false,
      agreeCheck2: false,
      agreeCheck3: false,
    },
  });
  console.log('Status:', res.status);
  console.log('Response:', JSON.stringify(res.body, null, 2));

  // ── TEST 6: 해지 신청 — 정상 요청 (DB 연동 또는 LOCAL 모드)
  console.log('\n[TEST 6] /api/msf/formTermination/APP-TEST-001/complete — 정상 요청 (전체 필드)');
  res = await request('/api/msf/formTermination/APP-TEST-001/complete', {
    customer: {
      customerType: 'customerType1',
      userName: '홍길동',
      userBirthDate: '19900101',
      ncn: 'NCN000001',
      cancelPhone1: '010',
      cancelPhone2: '1234',
      cancelPhone3: '5678',
      afterTel1: '010',
      afterTel2: '9876',
      afterTel3: '5432',
      postMethod: 'postMethod1',
      agencyName: 'TEST_AGENCY',
    },
    product: {
      isActive: 'MM',
      usageFee: '15000',
      penaltyFee: '5000',
      finalAmount: '20000',
      remainPeriod: '6',
      remainAmount: '90000',
      memo: '테스트 해지 신청',
    },
    agreement: {
      agreeCheck1: true,
      agreeCheck2: true,
      agreeCheck3: false,
    },
  });
  console.log('Status:', res.status);
  console.log('Response:', JSON.stringify(res.body, null, 2));

  console.log('\n' + '='.repeat(60));
  console.log('테스트 완료');
  console.log('='.repeat(60));
}

run();
