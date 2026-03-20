/**
 * 명의변경 신청서 API (formOwnChg)
 * X01, X20, MC0 등 AS-IS M포털 스펙 기반 연동
 */

import { msfPost } from './msf'

// X01 가입정보조회 (휴대폰 번호 인증)
export async function getJoinInfo(params) {
  return msfPost('/v1/ident/join-info', params)
}

// X20 현재 부가서비스 조회
export async function getCurrentAddition(params) {
  return msfPost('/v1/ident/addition/current', params)
}

// MC0 명의변경 가능 여부 (stub)
export async function checkIdentEligible(params) {
  return msfPost('/v1/ident/eligible', params)
}

// AS-IS grantorReqChk: 양도인 신청가능여부 체크
export async function checkGrantorAvailable(params) {
  return msfPost('/v1/ident/grantor-req-chk', params)
}

// AS-IS nameChgChkTelNo: 명의변경 회선 vs 연락처 상이 검증
export async function checkIdentTelNo(params) {
  return msfPost('/v1/ident/check-tel-no', params)
}

/**
 * 명의변경 신청서 등록 (작성 완료 시)
 * @param {object} payload - { agencyCode, customerForm, productForm } (store 구조)
 * @returns {Promise<{ success: boolean, applicationNo?: string, message?: string }>}
 */
export async function submitIdentApply(payload) {
  return msfPost('/v1/ident/apply', payload)
}

// X85 USIM 번호 유효성 체크 (공통 엔드포인트)
export async function checkIdentUsim(params) {
  return msfPost('/v1/comm/usim-check', params)
}

// 납부방법 가용 코드 목록 조회
export async function getIdentPayMethodList() {
  return msfPost('/v1/ident/pay-method', {})
}

// 계좌번호 유효성 체크 (형식 검증, IF_0016 연동 예정)
export async function checkIdentAccountNo(params) {
  return msfPost('/v1/ident/account-check', params)
}

// 카드번호 유효성 체크 (형식 검증, IF_0017 연동 예정)
export async function checkIdentCardNo(params) {
  return msfPost('/v1/ident/card-check', params)
}

// 청구계정ID 유효성 체크 (형식/길이 검증)
export async function checkIdentBillingAccountId(params) {
  return msfPost('/v1/ident/billing-account-check', params)
}
