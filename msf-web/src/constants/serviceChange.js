/**
 * 서비스변경 업무 코드 및 동시 변경 불가 그룹
 * 개발기능목록 REQ_010201, service-change-ui.md 참고
 */

/** 서비스변경 업무 코드 (옵션 ID) */
export const ServiceChangeCode = {
  RATE_CHANGE: 'RATE_CHANGE',           // 요금상품 변경
  ADDITION: 'ADDITION',                 // 부가서비스 신청/변경
  NUM_CHANGE: 'NUM_CHANGE',             // 번호변경
  LOST_RESTORE: 'LOST_RESTORE',         // 분실복구/일시정지해제 신청
  PHONE_CHANGE: 'PHONE_CHANGE',         // 핸드폰 기변/일반 기변
  USIM_CHANGE: 'USIM_CHANGE',           // USIM 변경
  DATA_SHARING: 'DATA_SHARING',         // 데이터쉐어링 가입/해지
  ANY_SOLO: 'ANY_SOLO',                 // 아무나 SOLO 결합
  WIRELESS_BLOCK: 'WIRELESS_BLOCK',      // 무선데이터차단
  INFO_LIMIT: 'INFO_LIMIT',             // 정보료 상한금액
  INSURANCE: 'INSURANCE',               // 단말보험
}

/** 동시 변경 불가 그룹 (이 그룹 내 2개 이상 선택 시 에러) */
export const CORE_CHANGE = [
  ServiceChangeCode.NUM_CHANGE,
  ServiceChangeCode.LOST_RESTORE,
  ServiceChangeCode.RATE_CHANGE,
  ServiceChangeCode.PHONE_CHANGE,
]

/** UI 옵션 메타데이터 (화면설계서 S102010101 순서: 무선데이터차단 ~ 아무나 SOLO 결합) */
export const ServiceChangeOptions = [
  { id: ServiceChangeCode.WIRELESS_BLOCK, label: '무선데이터차단 서비스' },
  { id: ServiceChangeCode.INFO_LIMIT, label: '정보료 상한금액 설정/변경' },
  { id: ServiceChangeCode.ADDITION, label: '부가서비스 신청/변경' },
  { id: ServiceChangeCode.RATE_CHANGE, label: '요금상품 변경' },
  { id: ServiceChangeCode.NUM_CHANGE, label: '번호변경' },
  { id: ServiceChangeCode.LOST_RESTORE, label: '분실복구/일시정지해제 신청' },
  { id: ServiceChangeCode.INSURANCE, label: '단말보험 가입' },
  { id: ServiceChangeCode.PHONE_CHANGE, label: '핸드폰 기변/일반 기변' },
  { id: ServiceChangeCode.USIM_CHANGE, label: 'USIM 변경' },
  { id: ServiceChangeCode.DATA_SHARING, label: '데이터쉐어링 가입/해지' },
  { id: ServiceChangeCode.ANY_SOLO, label: '아무나 SOLO 결합' },
]
