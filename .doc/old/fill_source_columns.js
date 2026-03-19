/**
 * 개발기능목록_담당_소스제외.md 에서 상세기능별로 기존소스분석용 6컬럼(화면|Controller|Service|M플랫폼|연동|VO/DTO) 채우기
 * 기준: 부가서비스_신청변경_소스구조_분석.md, M플랫폼_X20_처리루트_및_소스.md, M포털_변경처리_프로세스_분석.md, 서비스변경신청서_M포털소스_매핑.md
 */
const fs = require('fs');
const path = require('path');
const srcPath = path.join(__dirname, '개발기능목록_담당_소스제외.md');
const dstPath = path.join(__dirname, '개발기능목록_담당_소스제외.md');

const lines = fs.readFileSync(srcPath, 'utf8').split(/\r?\n/);

// 상세기능 패턴 → [화면, Controller, Service, M플랫폼, 연동, VO/DTO]
function getMapping(detail) {
  const d = detail || '';
  // 조회_현재 가입된 부가서비스 조회 (X20)
  if (d.includes('조회_현재 가입된 부가서비스 조회')) {
    return ['regServiceView.do', 'RegSvcController.java - myAddSvcListAjax (이용중 부가서비스 목록)', 'RegSvcServiceImpl.java - selectmyAddSvcList(ncn, ctn, custId)', 'MplatFormService.java - getAddSvcInfoDto(ncn, ctn, custId); getParamMap(..., "X20"); callService', 'X20', 'MpAddSvcInfoDto; MpSocVO'];
  }
  // 조회_가입 가능 부가서비스 목록 조회
  if (d.includes('조회_가입 가능 부가서비스 목록 조회')) {
    return ['regServiceView.do', 'RegSvcController.java - addSvcListAjax', 'RegSvcServiceImpl.selectAddSvcInfoDto; MypageService.selectRegService', 'MplatFormService.getAddSvcInfoDto; mcp-api selectRegService', 'X20+DB', 'MpAddSvcInfoDto; McpRegServiceDto'];
  }
  // 처리_부가서비스 신청/변경/해지 (이미 채워진 행은 덮어쓰지 않음)
  if (d.includes('처리_부가서비스 신청/변경/해지')) {
    return ['regServiceView.do', 'RegSvcController - myAddSvcListAjax, regSvcChgAjax, moscRegSvcCanChgAjax', 'RegSvcServiceImpl - selectmyAddSvcList, regSvcChg, moscRegSvcCanChg', 'MplatFormService - getAddSvcInfoDto, regSvcChg, moscRegSvcCanChg', 'X20/X21/X38', 'MpAddSvcInfoDto; MpSocVO; MpRegSvcChgVO; RegSvcChgRes'];
  }
  // X01 가입정보 조회 / 휴대폰번호 인증
  if (d.includes('휴대폰번호 가입정보 조회') || d.includes('조회_kt m모바일 휴대폰 번호 인증')) {
    return ['appForm.do / mypage', 'MyinfoController; AppformController', 'MyinfoServiceImpl.perMyktfInfo(ncn, ctn, custId)', 'MplatFormService.perMyktfInfo; getParamMap(..., "X01"); callService', 'X01', 'MpPerMyktfInfoVO'];
  }
  // 동시 처리 불가 체크
  if (d.includes('동시 처리 불가 체크')) {
    return ['appForm.do', 'AppformController.conPreCheckAjax.do', 'AppformSvcImpl (conPreCheck)', 'm전산DB+m포탈DB', 'm전산DB+m포탈DB', '-'];
  }
  // 서비스변경 업무 정보 조회
  if (d.includes('서비스변경 업무 정보 조회')) {
    return ['appForm', 'AppformController (getMcpAdditionList 등)', 'AppformSvcImpl; AppformDaoImpl', 'm전산DB+m포탈DB', 'm전산DB+m포탈DB', '-'];
  }
  // 요금상품 변경 관련
  if (d.includes('조회_변경 가능 요금제 목록 조회')) {
    return ['farPricePlanView.do', 'FarPricePlanController', 'FarPricePlanServiceImpl', 'm전산DB+m포탈DB', 'm전산DB+m포탈DB', '-'];
  }
  if (d.includes('조회_현재 설정된 요금제 예약변경 조회')) {
    return ['farPricePlanView.do', 'FarPricePlanController', 'FarPricePlanServiceImpl', 'MplatFormService.moscFarRsvChgInqr; getParamMap(..., "X89")', 'X89', '-'];
  }
  if (d.includes('처리_요금상품 변경')) {
    return ['farPricePlanView.do', 'FarPricePlanController', 'FarPricePlanServiceImpl.farPricePlanChgNeTrace', 'MplatFormService - X90/X19/X84/X88', 'X90/X19/X84/X88', '-'];
  }
  // 번호변경
  if (d.includes('희망번호 조회 가능 횟수 조회') || d.includes('희망번호 조회 가능 횟수 증가')) {
    return ['appForm / 번호변경 화면', 'AppformController', 'AppformSvcImpl; DB 조회/갱신', '스마트서식지DB 또는 m전산DB', 'm전산DB+m포탈DB', '-'];
  }
  if (d.includes('처리_번호변경')) {
    return ['appForm / 번호변경', 'AppformController', 'AppformSvcImpl', 'MplatFormService; getParamMap(..., "X32")', 'X32', '-'];
  }
  // 일시정지/분실복구
  if (d.includes('조회_일시정지 이력 조회')) {
    return ['appForm / 서비스변경', 'AppformController 또는 전용 Controller', 'MplatFormService; getParamMap(..., "X26")', 'MplatFormService - 일시정지이력조회', 'X26', '-'];
  }
  if (d.includes('일시정지 해제 가능 여부 조회')) {
    return ['appForm', '-', 'MplatFormService; getParamMap(..., "X28")', 'MplatFormService - 일시정지해제가능여부조회', 'X28', '-'];
  }
  if (d.includes('처리_일시정지 해제')) {
    return ['appForm', '-', 'MplatFormService; getParamMap(..., "X30")', 'MplatFormService - 일시정지해제신청', 'X30', '-'];
  }
  if (d.includes('처리_분실복구')) {
    return ['appForm', '-', 'MplatFormService (미확정)', 'M플랫폼 연동 API 미확정', '미확정', '-'];
  }
  // 단말보험
  if (d.includes('단말보험')) {
    return ['appForm / 단말보험', '-', 'MplatFormService (미확정)', 'M플랫폼 연동 API 미확정', '미확정', '-'];
  }
  // 약관 상세
  if (d.includes('조회_약관 상세 내용 조회')) {
    return ['regServiceView / addSvcViewPop', 'RegSvcController.getContRateAdditionAjax', 'RegSvcService.selectAddSvcDtl; RateAdsvcGdncService', 'm포탈DB', 'm포탈DB', '-'];
  }
  // 핸드폰 기변/USIM
  if (d.includes('조회_IMEI 유효성 체크') || d.includes('처리_핸드폰 기변')) {
    return ['appForm / 기변 화면', '-', 'MplatFormService (미확정)', 'M플랫폼 연동 API 미확정', '미확정', '-'];
  }
  if (d.includes('조회_유심번호 유효성 체크')) {
    return ['appForm / USIM 화면', 'UsimController 등', 'UsimServiceImpl', 'MplatFormService; getParamMap(..., "X85")', 'X85', '-'];
  }
  if (d.includes('처리_USIM 변경')) {
    return ['appForm / USIM', '-', 'UsimServiceImpl (미확정)', 'M플랫폼 연동 API 미확정', '미확정', '-'];
  }
  // 데이터쉐어링
  if (d.includes('데이터쉐어링 결합 중인 대상 조회')) {
    return ['myCombinationView.do', 'MyCombinationController', 'MplatFormService; getParamMap(..., "X71")', 'MplatFormService - X71', 'X71', '-'];
  }
  if (d.includes('데이터쉐어링 사전체크 및 가입 가능 대상 조회')) {
    return ['myCombinationView.do', 'MyCombinationController', 'MplatFormService; getParamMap(..., "X69")', 'MplatFormService - X69', 'X69', '-'];
  }
  if (d.includes('처리_데이터쉐어링 가입/해지')) {
    return ['myCombinationView.do', 'MyCombinationController', 'MplatFormService; getParamMap(..., "X70")', 'MplatFormService - X70', 'X70', '-'];
  }
  // 아무나 SOLO
  if (d.includes('아무나 SOLO')) {
    return ['appForm', '-', 'MplatFormService (미확정)', 'M플랫폼 연동 API 미확정', '미확정', '-'];
  }
  // 명의변경/서비스해지 - 가입유형·약관·인증
  if (d.includes('가입유형 정보 조회')) {
    return ['appForm / marketRequest.do', 'AppformController; marketRequest', 'AppformSvcImpl; DB', 'm전산DB+m포탈DB', 'm전산DB+m포탈DB', '-'];
  }
  if (d.includes('법정대리인 안내') || d.includes('이용약관 목록/내용 조회')) {
    return ['appForm', 'AppformController', 'm포탈DB 조회', 'm포탈DB', 'm포탈DB', '-'];
  }
  if (d.includes('명의변경 가능 여부 사전 체크') || d.includes('서비스해지 가능 여부 사전 체크')) {
    return ['appForm', '-', 'MplatFormService (미확정)', 'M플랫폼 연동 API 미확정', '미확정', '-'];
  }
  // 명의변경 - 부가서비스/납부/신용
  if (d.includes('가입중 부가서비스 정보 조회') && d.includes('명의변경')) {
    return ['regServiceView.do', 'RegSvcController.myAddSvcListAjax', 'RegSvcServiceImpl.selectmyAddSvcList', 'MplatFormService.getAddSvcInfoDto; X20', 'X20', 'MpAddSvcInfoDto; MpSocVO'];
  }
  if (d.includes('USIM 유형 정보 조회') || d.includes('모바일 요금제 정보 조회') || d.includes('요금 납부 방법 정보 조회')) {
    return ['appForm', 'AppformController', 'AppformSvcImpl; DB', 'm전산DB+m포탈DB', 'm전산DB+m포탈DB', '-'];
  }
  if (d.includes('신용정보 조회') || d.includes('계좌번호 유효성') || d.includes('신용카드 유효성') || d.includes('청구계정ID 유효성')) {
    return ['appForm', 'AppformController', 'NICE 연동 모듈', 'NICE', 'IF_0016/IF_0017 등', '-'];
  }
  if (d.includes('eSIM 유효성')) {
    return ['appForm', '-', 'MplatFormService (미확정)', 'M플랫폼 연동 API 미확정', '미확정', '-'];
  }
  // 서비스해지 - 잔여요금/위약금/분납금
  if (d.includes('잔여요금 조회') || d.includes('위약금 조회') || d.includes('단말기 분납금 조회')) {
    return ['서비스해지 화면', '-', 'MplatFormService', 'MplatFormService - X22/X67', 'X22/X67', '-'];
  }
  // 서명/신청서 등록/전송/이미징
  if (d.includes('가입자(대리인) 성명/서명 정보')) {
    return ['appForm / eFormSign', '포시에스 eFormSign 연동', 'eFormSign API', '포시에스 eFormSign', 'eFormSign', '-'];
  }
  if (d.includes('처리_신청서 정보 등록')) {
    return ['appForm', 'AppformController.saveAppform 등', 'AppformSvcImpl; DB 등록', '스마트서식지DB', '스마트서식지DB', '-'];
  }
  if (d.includes('처리_신청서 정보 전송') || d.includes('처리_신청서 전송')) {
    return ['appForm', 'AppformController', 'AppformSvcImpl; MplatFormService 또는 M전산/K-NOTE', 'm플랫폼 또는 M전산/K-NOTE', 'm플랫폼/미확정', '-'];
  }
  if (d.includes('처리_신청서 리포트 이미징 처리')) {
    return ['appForm', 'eFormSign 연동', '포시에스 eFormSign', '포시에스 eFormSign', 'eFormSign', '-'];
  }
  if (d.includes('처리_신청서 이미징 시스템 전송')) {
    return ['appForm', 'JC1/M모바일 연동', 'IF_0035/IF_0038', 'JC1/M모바일', 'IF_0035/IF_0038', '-'];
  }
  // 신청서 작성 내용 조회
  if (d.includes('신청서 작성 내용 조회') || d.includes('서비스변경 신청서 작성 내용') || d.includes('명의변경 신청서 작성 내용') || d.includes('서비스해지 신청서 작성 내용')) {
    return ['appForm / 완료화면', 'AppformController', 'AppformSvcImpl; eFormSign 조회', 'm전산DB+m포탈DB 또는 eFormSign', 'm전산DB+m포탈DB', '-'];
  }
  // SMS/카톡 발송
  if (d.includes('처리_SMS 발송') || d.includes('처리_카톡 발송') || d.includes('처리_m모바일App 사용 안내 발송')) {
    return ['appFormComplete', 'AppformController', 'IF_0030/IF_0031 연동', '카카오/M모바일/포시에스 eFormSign', 'IF_0030/IF_0031', '-'];
  }
  // 고객 안내사항
  if (d.includes('고객 안내사항 목록/내용 조회') || d.includes('고객 안내 사항')) {
    return ['appForm', 'AppformController', 'm포탈DB 조회', 'm포탈DB', 'm포탈DB', '-'];
  }
  // 녹취
  if (d.includes('안내 녹취 정보 조회') || d.includes('처리_녹취 등록')) {
    return ['appForm', '포시에스 eFormSign', 'eFormSign 녹취 API', '포시에스 eFormSign', 'eFormSign', '-'];
  }
  // 화면 전용 (Pg/P 화면만, 조회/처리 아님)
  if (d.includes('화면') && !d.includes('조회_') && !d.includes('처리_') && (d.includes('선택 화면') || d.includes('고객 화면') || d.includes('상품 화면') || d.includes('동의 화면') || d.includes('신청완료 화면') || d.includes('추가/삭제 화면') || d.includes('화면 구성') || d.trim().endsWith('화면'))) {
    return ['appForm.do / regServiceView.do 등', 'AppformController / RegSvcController', '-', '-', '-', '-'];
  }
  return ['-', '-', '-', '-', '-', '-'];
}

function hasSixColumns(line) {
  return line.includes('RegSvcController.java') || line.includes('MplatFormService.java - getAddSvcInfoDto');
}

const out = [];
for (let i = 0; i < lines.length; i++) {
  let line = lines[i];
  if (!line.startsWith('|서식지App|')) {
    out.push(line);
    continue;
  }
  const parts = line.split('|');
  // parts[0]='', 1=구분.. 8=상세 기능, ... 19=요구사항ID. 있으면 20~25=화면,Controller,Service,M플랫폼,연동,VO/DTO
  const detail = (parts[8] || '').trim();
  const six = getMapping(detail);

  if (parts.length >= 25) {
    // 이미 6컬럼 있음 → 마지막 6개를 매핑값으로 교체
    const newParts = parts.slice(0, 20).concat(six);
    line = '|' + newParts.slice(1).join('|') + '|';
  } else {
    // 6컬럼 없음 → 추가
    const suffix = '|' + six.join('|') + '|';
    line = (line.endsWith('|') ? line.slice(0, -1) : line) + suffix;
  }
  out.push(line);
}

fs.writeFileSync(dstPath, out.join('\n'), 'utf8');
console.log('Done. Wrote', dstPath);
